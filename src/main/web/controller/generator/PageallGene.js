// Chemins de configuration
const CONFIG_PATH = '../../setting/config.json';
const JSON_BASE_PATH = '../resources/data/json';

/**
 * Ouvre un fichier JSON à partir d'un chemin donné.
 * @param {string} path - Le chemin du fichier JSON.
 * @returns {Promise<Object>} - Le contenu du fichier JSON.
 */
async function openJson(path) {
    const response = await fetch(path);
    return await response.json();
}

/**
 * Charge les données de tri pour un générateur donné.
 * @param {string} generator - Le nom du générateur.
 * @param {string[]} sortAlgorithms - Les algorithmes de tri à charger.
 * @returns {Promise<Object>} - Les noms et les données des algorithmes de tri.
 */
async function loadSortingData(generator, sortAlgorithms) {
    const dataName = [];
    const data = [];

    for (const algorithm of sortAlgorithms) {
        const jsonSort = await openJson(`${JSON_BASE_PATH}/${generator}/10000/${algorithm}.json`);

        dataName.push(algorithm);
        const minidata = [];
        for (let i = 0; i < 10; i++) {
            const index = (1 + i) * 10;
            minidata.push(jsonSort.history[index].correct );
        }
        data.push(minidata);
    }

    return { names: dataName, data };
}

/**
 * Initialise la page en chargeant la configuration et en créant les cartes et les graphiques.
 */
async function initPage() {
    const config = await openJson(CONFIG_PATH);

    const mainContent = document.querySelector('.main-content');

    for (const generator of config.GeneratorName) {
        const card = createCard(generator,config);
        mainContent.appendChild(card);

        const { names, data } = await loadSortingData(generator, config.SortName);
        createLineChart(generator, createList(names, data));
    }

    initButtonDropDown();
}

initPage();
