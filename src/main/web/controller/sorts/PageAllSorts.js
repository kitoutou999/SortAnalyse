const CONFIG_PATH = '../../setting/config.json';
const JSON_BASE_PATH = '../resources/data/json';

async function openJson(path) {
    const response = await fetch(path);
    return await response.json();
}

async function loadGeneratorData(sortAlgorithm, generators) {
    const dataName = [];
    const data = [];

    for (const generator of generators) {
        const jsonData = await openJson(`${JSON_BASE_PATH}/${generator}/10000/${sortAlgorithm}.json`);

        dataName.push(formatGeneratorName(generator));
        const points = [];
        for (let i = 0; i < 10; i++) {
            const index = (1 + i) * 10;
            points.push(jsonData.history[index].correct);
        }
        data.push(points);
    }

    return { names: dataName, data };
}

async function loadGlobalGeneratorData(sortAlgorithm, generators, metric = 'comparaisons', count = '1000') {
    const generatorNames = [];
    const metricValues = [];

    for (const generator of generators) {
        try {
            const jsonData = await openJson(`${JSON_BASE_PATH}/${generator}/${count}/${sortAlgorithm}.json`);
            generatorNames.push(formatGeneratorName(generator));
            metricValues.push(jsonData.global[metric]);
        } catch (error) {
            console.error(`Erreur lors du chargement des données pour ${generator}/${sortAlgorithm}:`, error);
        }
    }

    return { names: generatorNames, values: metricValues, metric };
}

async function initSortsPage() {
    try {
        const config = await openJson(CONFIG_PATH);
        const mainContent = document.querySelector('.main-content');

        for (const sortAlgorithm of config.SortName) {
            const card = createSortCard(sortAlgorithm, config);
            mainContent.appendChild(card);

            const { names, values, metric } = await loadGlobalGeneratorData(
                sortAlgorithm, 
                config.GeneratorName,
                'comparaisons',  
                '1000'          
            );
            
            createBarChart(
                sortAlgorithm, 
                createBarData(names, values, metric)
            );
        }

        initButtonDropDown();
    } catch (error) {
        console.error('Erreur lors de l\'initialisation de la page:', error);
    }
}

initSortsPage();