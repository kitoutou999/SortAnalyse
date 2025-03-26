/**
 * Crée un tableau de comparaison avec les données de tri, la configuration et la complexité.
 * @param {Object} sortData - Les données de tri.
 * @param {Object} config - La configuration contenant les noms des algorithmes de tri.
 * @param {Object} complexity - Les données de complexité des algorithmes de tri.
 */
function createComparisonTable(sortData, config, complexity) {
    
    let tabcontainer = document.getElementsByClassName('Tabcontainer');
    if (!tabcontainer || tabcontainer.length === 0) {
        console.error("Conteneur de tableau non trouvé");
        return;
    }
    
    let table = document.createElement('table');
    let thead = document.createElement('thead');
    thead.className = 'theadTab';
    
    let headerRow1 = document.createElement('tr');
    
    let emptyTh = document.createElement('th');
    
    emptyTh.style.textAlign = 'center';
    emptyTh.align = 'center';
    headerRow1.appendChild(emptyTh);
    
    const generatorCategories = [
        { name: "Random Start", colspan: 2 }, 
        { name: "Random", colspan: 2 }, 
        { name: "Reverse", colspan: 2 }, 
        { name: "Random End", colspan: 2 }
    ];
    
    for (const category of generatorCategories) {
        let th = document.createElement('th');
        th.textContent = category.name;
        th.colSpan = category.colspan;
        
        th.style.textAlign = 'center';
        th.align = 'center';
        headerRow1.appendChild(th);
    }
    
    let headerRow2 = document.createElement('tr');
    
    let algoTh = document.createElement('th');
    algoTh.textContent = "Algorithme";
    
    algoTh.style.textAlign = 'center';
    algoTh.align = 'center';
    headerRow2.appendChild(algoTh);
    
    for (let i = 0; i < 4; i++) {
        let compTh = document.createElement('th');
        compTh.textContent = "Comp.";
        compTh.className = 'comp-header';
        
        compTh.style.textAlign = 'center';
        compTh.align = 'center';
        headerRow2.appendChild(compTh);
        
        let assigTh = document.createElement('th');
        assigTh.textContent = "Assig.";
        assigTh.className = 'assig-header';
        
        assigTh.style.textAlign = 'center';
        assigTh.align = 'center';
        
        if (i < 3) {
            assigTh.className += ' group-end';
        }
        headerRow2.appendChild(assigTh);
    }
    
    thead.appendChild(headerRow1);
    thead.appendChild(headerRow2);
    
    let tbody = document.createElement('tbody');
    tbody.className = 'tbodyTab';
    
    
    const generatorOrder = ["RandomStart", "Random", "Reverse", "RandomEnd"];
    const generatorMapping = {
        "RandomStart": "FirstHalfChunkGeneratorStrategy",
        "Random": "RandomGeneratorStrategy",
        "Reverse": "ReverseGeneratorStrategy",
        "RandomEnd": "SecondHalfChunkGeneratorStrategy"
    };
    
    
    for (const sort of config.SortName) {
        let tr = document.createElement('tr');
        
        
        let tdName = document.createElement('td');
        tdName.textContent = sort;
        if (complexity[sort]["Type"] == "Quadratic") {
            tdName.style.color = "red";
        } else if (complexity[sort]["Type"] == "Logarithmic") {
            tdName.style.color = "orange";
        } else {
            tdName.style.color = "green";
        }
        tr.appendChild(tdName);
        
        
        for (let i = 0; i < generatorOrder.length; i++) {
            const category = generatorOrder[i];
            const generatorStrategy = generatorMapping[category];
            
            
            const data = sortData[generatorStrategy]?.[sort];
            
            if (!data) {
                
                let tdComp = document.createElement('td');
                tdComp.textContent = "N/A";
                tdComp.className = 'comp-cell';
                tr.appendChild(tdComp);
                
                let tdAssig = document.createElement('td');
                tdAssig.textContent = "N/A";
                tdAssig.className = 'assig-cell';
                if (i < 3) {
                    tdAssig.className += ' group-end';
                }
                tr.appendChild(tdAssig);
                continue;
            }
            
            
            let tdComp = document.createElement('td');
            tdComp.textContent = data.comparaisons.toLocaleString();
            tdComp.align = "center";
            tdComp.className = 'comp-cell';
            tr.appendChild(tdComp);
            
            
            let tdAssig = document.createElement('td');
            tdAssig.textContent = data.set.toLocaleString();
            tdAssig.align = "center";
            tdAssig.className = 'assig-cell';
            if (i < 3) {
                tdAssig.className += ' group-end';
            }
            tr.appendChild(tdAssig);
        }
        
        
        tbody.appendChild(tr);
    }
    
    
    table.appendChild(thead);
    table.appendChild(tbody);
    
    
    tabcontainer[0].innerHTML = '';
    
    
    tabcontainer[0].appendChild(table);
    

}

