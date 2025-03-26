const CONFIG_PATH = '../../setting/config.json';
const COMPLEX_PATH = '../../setting/complexity.json';

async function openJson(path) {
    const response = await fetch(path);
    tmp = await response.json();
    return tmp;
}

async function initBottomDashboard() {
    const config = await openJson(CONFIG_PATH);
    const complexity = await openJson(COMPLEX_PATH);
   
    const sortData = {};
   
    for (let i = 0; i < config.GeneratorName.length; i++) {
        const generator = config.GeneratorName[i];
       
        if (!sortData[generator]) {
            sortData[generator] = {};
        }
       
        for (let j = 0; j < config.SortName.length; j++) {
            const sort = config.SortName[j];
           
            try {
                const data = await openJson(`../resources/data/json/${generator}/10000/${sort}.json`);
               
                sortData[generator][sort] = data.global;
            } catch (error) {
                console.error(`Erreur lors du chargement des données pour ${generator}/${sort}:`, error);
                sortData[generator][sort] = {
                    arrayAccess: 0,
                    comparaisons: 0,
                    set: 0,
                    swap: 0,
                    delay: 0
                };
            }
        }
    }
   
    createComparisonTable(sortData, config, complexity);
}

initBottomDashboard();