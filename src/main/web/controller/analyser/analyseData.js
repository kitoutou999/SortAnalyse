// Chemin de configuration
const CONFIG_PATH = '../../setting/complexity.json';

/**
 * Ouvre un fichier JSON à partir d'un chemin donné.
 * @param {string} path - Le chemin du fichier JSON.
 * @returns {Promise<Object>} - Le contenu du fichier JSON.
 */
async function openJson(path) {
    const response = await fetch(path);
    tmp = await response.json();
    return tmp;
}

/**
 * Initialise le tableau de bord supérieur en chargeant la configuration et en créant des cartes.
 */
async function initTopDashboard() {
    const config = await openJson(CONFIG_PATH);
    const main = document.querySelector('.main-content');

    for (const [key, value] of Object.entries(config)) {
        const card = createCard(key, value);
        main.appendChild(card);
    }
}

initTopDashboard();
