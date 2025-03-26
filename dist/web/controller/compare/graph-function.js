// Variables globales
let chart = null;
let currentTargetValue = 0.6;
let xValues = [0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9];

function f(x) {
    const log2x = Math.log2(x);
    const log2_1_minus_x = Math.log2(1 - x);
    
    return -x * (log2x - log2_1_minus_x) - log2_1_minus_x;
}

function generatePoints(numPoints = 500) {
    const points = [];
    const targetLine = [];
    
    points.push({ x: 0, y: 0 });
    targetLine.push({ x: 0, y: currentTargetValue });
    
    const step = 0.999 / numPoints;
    for (let i = 1; i <= numPoints; i++) {
        const x = i * step;
        const y = f(x);
        
        if (isFinite(y) && !isNaN(y)) {
            points.push({ x, y });
            targetLine.push({ x, y: currentTargetValue });
        }
    }

    
    return { functionPoints: points, targetLine };
}

function findSolutions(targetValue) {
    if(targetValue==1) {
        return [0.5];
    }else if(targetValue==0) {
        return [0,1];
    }

    const solutions = [];
    const intervals = [
        [0.001, 0.1],
        [0.1, 0.49],
        [0.51, 0.9],
        [0.9, 0.999]
    ];
    
    for (const [a, b] of intervals) {
        const solution = findRoot(x => f(x) - targetValue, a, b);
        if (solution && !solutions.some(s => Math.abs(s - solution) < 0.001) && solutions.length<2) {
            solutions.push(solution);
        }
    }
    return solutions.sort((a, b) => a - b);
}

function findRoot(func, a, b, tolerance = 1e-7) {
    let fa = func(a);
    let fb = func(b);
    
    if (fa * fb > 0) {
        if (Math.abs(fa) < tolerance) return a;
        if (Math.abs(fb) < tolerance) return b;
        
        const step = (b - a) / 100;
        
        for (let i = 1; i < 100; i++) {
            const x = a + i * step;
            const fx = func(x);
            
            if (fx * fa <= 0) {
                b = x;
                fb = fx;
                break;
            }
            a = x;
            fa = fx;
        }
        if (fa * fb > 0) return null;
    }
    
    let c, fc;
    let iterations = 0;
    const maxIterations = 100;
    
    while ((b - a) > tolerance && iterations < maxIterations) {
        c = (a + b) / 2;
        fc = func(c);
        
        if (Math.abs(fc) < tolerance) break;
        
        if (fc * fa < 0) {
            b = c;
            fb = fc;
        } else {
            a = c;
            fa = fc;
        }
        
        iterations++;
    }
    
    return iterations < maxIterations ? c : null;
}

function updateChart(solutions = []) {
    const { functionPoints, targetLine } = generatePoints(500);
    const solutionPoints = solutions.map(x => ({ x, y: currentTargetValue }));
    
    if (chart) {
        chart.data.datasets[0].data = functionPoints;
        chart.data.datasets[1].data = targetLine;
        chart.data.datasets[2].data = solutionPoints;
        chart.options.scales.y.min = Math.max(0, currentTargetValue - 0.3);
        chart.options.scales.y.max = Math.min(1.2, currentTargetValue + 0.3);
        chart.data.datasets[1].label = `y = ${currentTargetValue.toFixed(2)}`;
        chart.update();
    } else {
        const ctx = document.getElementById('functionGraph').getContext('2d');
        chart = new Chart(ctx, {
            type: 'line',
            data: {
                datasets: [{
                    label: 'f(x)',
                    data: functionPoints,
                    borderColor: '#4bc0c0',
                    borderWidth: 2,
                    pointRadius: 0,
                    tension: 0.1,
                    fill: false,
                    cubicInterpolationMode: 'monotone'
                }, {
                    label: `y = ${currentTargetValue.toFixed(2)}`,
                    data: targetLine,
                    borderColor: '#ff6384',
                    borderWidth: 1.5,
                    pointRadius: 0,
                    borderDash: [3, 3],
                    fill: false
                }, {
                    label: 'Solutions',
                    data: solutionPoints,
                    backgroundColor: '#4bc0c0',
                    borderColor: '#36a2a2',
                    pointRadius: 5,
                    pointHoverRadius: 7,
                    showLine: false
                }]
            },
            options: {
                maintainAspectRatio: false,
                elements: { line: { tension: 0.4 } },
                scales: {
                    x: {
                        type: 'linear',
                        ticks: { color: '#fff', font: { size: 10 } },
                        grid: { color: '#2c2e31' },
                        min: 0,
                        max: 1
                    },
                    y: {
                        type: 'linear',
                        ticks: { color: '#fff', font: { size: 10 } },
                        grid: { color: '#2c2e31' },
                        min: Math.max(0, currentTargetValue - 0.3),
                        max: Math.min(1.2, currentTargetValue + 0.3)
                    }
                },
                plugins: {
                    legend: {
                        display: true,
                        position: 'top',
                        labels: {
                            color: '#e5e5e5',
                            boxWidth: 12,
                            font: { size: 10 }
                        }
                    },
                    tooltip: {
                        callbacks: {
                            label: function(context) {
                                const x = context.parsed.x.toFixed(4);
                                const y = context.parsed.y.toFixed(4);
                                return `${context.dataset.label || ''}: (${x}, ${y})`;
                            }
                        }
                    }
                }
            }
        });
    }
}

function updateSolutionsInfo(solutions) {
    const solution1Element = document.getElementById('solution1');
    const solution2Element = document.getElementById('solution2');
    const solution1Card = document.getElementById('solution-card-1');
    const solution2Card = document.getElementById('solution-card-2');
    
    solution1Card.style.display = solutions.length >= 1 ? 'block' : 'none';
    if (solutions.length >= 1) {
        solution1Element.textContent = solutions[0].toFixed(6);
    }
    
    solution2Card.style.display = solutions.length >= 2 ? 'block' : 'none';
    if (solutions.length >= 2) {
        solution2Element.textContent = solutions[1].toFixed(6);
    }
}

function updateFunctionValuesTable() {
    const tbody = document.getElementById('function-values-body');
    tbody.innerHTML = ''; 
    
    const row = document.createElement('tr');
    const labelCell = document.createElement('td');
    labelCell.textContent = "f(x)";
    row.appendChild(labelCell);
    
    const allXValues = [...xValues, 1];
    
    for (const x of allXValues) {
        const cell = document.createElement('td');
        // Cas spécial pour x = 1
        const value = x === 1 ? 0.5 : f(x);
        cell.textContent = value.toFixed(4);
        
        if (Math.abs(value - currentTargetValue) < 0.02) {
            cell.className = 'highlight';
        }
        
        row.appendChild(cell);
    }
    
    tbody.appendChild(row);
}

function updateTargetValue(value) {
    currentTargetValue = value;
    
    document.getElementById('target-value').textContent = value.toFixed(2);
    document.getElementById('target-input').value = value.toFixed(2);
    document.getElementById('target-slider').value = value;
    
    const solutions = findSolutions(value);
    console.log(solutions);
    updateChart(solutions);
    updateSolutionsInfo(solutions);
    updateFunctionValuesTable();
}

document.addEventListener('DOMContentLoaded', function() {
    const slider = document.getElementById('target-slider');
    slider.addEventListener('input', function() {
        document.getElementById('target-input').value = parseFloat(this.value).toFixed(2);
    });
    
    slider.addEventListener('change', function() {
        updateTargetValue(parseFloat(this.value));
    });
    
    const input = document.getElementById('target-input');
    input.addEventListener('change', function() {
        let value = Math.max(0, Math.min(1, parseFloat(this.value)));
        this.value = value.toFixed(2);
        document.getElementById('target-slider').value = value;
        updateTargetValue(value);
    });
    
    const initialSolutions = findSolutions(currentTargetValue);
    updateChart(initialSolutions);
    updateSolutionsInfo(initialSolutions);
    updateFunctionValuesTable();
});