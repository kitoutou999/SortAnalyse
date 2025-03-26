// Couleurs flashy pour les graphiques
const FLASHY_COLORS = [
  'rgb(255, 0, 0)',     // Rouge vif
  'rgb(0, 255, 0)',     // Vert flashy
  'rgb(0, 0, 255)',     // Bleu électrique
  'rgb(255, 0, 255)',   // Magenta
  'rgb(255, 255, 0)',   // Jaune vif
  'rgb(0, 255, 255)',   // Cyan
  'rgb(255, 128, 0)',   // Orange vif
  'rgb(128, 0, 255)',   // Violet
  'rgb(255, 0, 128)',   // Rose flashy
  'rgb(0, 255, 128)'    // Vert lime
];

// Fonction pour créer un graphique à barres
function createBarChart(ctxId, data) {
    const canvas = document.getElementById(`chart-${ctxId}`);
    canvas.height = 330; 
    const ctx = canvas.getContext('2d');
    
    new Chart(ctx, {
      type: 'bar',
      data: {
        labels: data.labels,  
        datasets: [{
          label: data.metric,  
          data: data.values, 
          backgroundColor: data.colors, 
          borderColor: data.borderColors,
          borderWidth: 1
        }]
      },
      options: {
        responsive: false,
        maintainAspectRatio: false,
        scales: {
          x: {
            grid: {
              color: 'rgba(255, 255, 255, 0.2)' 
            },
            ticks: {
              color: '#d5d8dc',
              maxRotation: 90,
              minRotation: 45
            }
          },
          y: {
            grid: {
              color: 'rgba(255, 255, 255, 0.2)' 
            },
            ticks: {
              color: '#d5d8dc' 
            },
            beginAtZero: true
          }
        },
        plugins: {
          legend: {
            display: false,
          },
          
        }
      }
    });
}

// Fonction pour créer les données pour un graphique à barres
function createBarData(labels, values, metric) {
  const colors = [];
  const borderColors = [];
  
  for (let i = 0; i < labels.length; i++) {
    const color = FLASHY_COLORS[i % FLASHY_COLORS.length];
    colors.push(color.replace('rgb', 'rgba').replace(')', ', 0.7)'));
    borderColors.push(color);
  }
  
  return {
    labels,
    values,
    colors,
    borderColors,
    metric
  };
}
