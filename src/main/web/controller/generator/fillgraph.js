/**
 * Affiche une valeur JSON pour une clé donnée.
 * @param {Object} data - Les données JSON.
 * @param {string} key - La clé à rechercher dans les données JSON.
 * @returns {*} - La valeur associée à la clé.
 */
function afficherJSON(data, key) {
    try {
        return data[key];
    } catch (error) {
        console.error('JSON reading error:', error.message);
    }
}

/**
 * Formate le nom du générateur en supprimant "Strategy" et en ajoutant un espace avant les chiffres.
 * @param {string} name - Le nom du générateur.
 * @returns {string} - Le nom formaté.
 */
function formatGeneratorName(name) {
    if (name.match(/\d+\.\d+$/)) {
        return name.replace('Strategy', '').replace(/(\d+\.\d+)$/, ' $1');
    }
    
    return name.replace('Strategy', '');
}

/**
 * Crée une carte avec des menus déroulants pour un générateur donné.
 * @param {string} elt - Le nom du générateur.
 * @param {Object} config - La configuration contenant les comptes.
 * @returns {HTMLElement} - La carte créée.
 */
function createCard(elt,config) {
    // Create card container
    const card = document.createElement('div');
    card.className = 'card';
    
    // Create card header
    const header = document.createElement('div');
    header.className = 'card-header';
    
    // Create card title (convert camelCase to readable text)
    const title = document.createElement('h2');
    title.className = 'card-title';
    title.textContent = formatGeneratorName(elt);

    // Create dropdowns
    const sortValueDropdown = createDropdown({
        width: '120px',
        buttonId: 'dropdownSortValue',
        defaultText: 'correct',
        options: ["correct", "arrayAccess", "comparaison", "set", "swap", "delay"],
        onSelect: () => updateDatasets(elt)
    });

    const countDropdown = createDropdown({
        width: '100px',
        buttonId: 'dropdownCount',
        defaultText: '10000',
        options: config.Count,
        onSelect: () => updateDatasets(elt)
    });

    // Create dropdowns container
    const dropdownContainer = document.createElement('div');
    dropdownContainer.className = 'dropdownsligne';
    dropdownContainer.append(sortValueDropdown, countDropdown);
    
    // Assemble header
    header.append(title, dropdownContainer);
    
    // Create canvas
    const canvas = document.createElement('canvas');
    canvas.id = `chart-${elt}`;
    
    // Assemble card
    card.append(header, canvas);
    
    return card;
}

/**
 * Crée un menu déroulant avec les options fournies.
 * @param {Object} params - Les paramètres pour créer le menu déroulant.
 * @returns {HTMLElement} - Le menu déroulant créé.
 */
function createDropdown({ width, buttonId, defaultText, options, onSelect }) {
    const dropdown = document.createElement('div');
    dropdown.className = 'dropdown';
    dropdown.style.width = width;
    
    const dropdownButton = document.createElement('button');
    dropdownButton.className = 'dropdown-button';
    dropdownButton.id = buttonId;
    dropdownButton.textContent = defaultText;
    
    const dropdownContent = document.createElement('div');
    dropdownContent.className = 'dropdown-content';
    
    options.forEach(value => {
        const option = document.createElement('div');
        option.href = '#';
        option.className = 'dropdown-item';
        option.textContent = value;
        option.addEventListener('click', (e) => {
            e.preventDefault();
            dropdownButton.textContent = value;
            updateDatasets(dropdownButton.closest('.card').querySelector('canvas').id.replace('chart-', ''));
        });
        dropdownContent.appendChild(option);
    });
    
    dropdown.append(dropdownButton, dropdownContent);
    return dropdown;
}

/**
 * Met à jour les ensembles de données pour un générateur donné.
 * @param {string} GeneratorName - Le nom du générateur.
 */
async function updateDatasets(GeneratorName) {
    try {
        const count = document.getElementById('dropdownCount').textContent;
        const sortValue = document.getElementById('dropdownSortValue').textContent;
        const config = await openJson('../../setting/config.json');
        const chartId = `chart-${GeneratorName}`;
        const chart = Chart.getChart(chartId);

        const dataName = [];
        const data = [];

        for (const eltSort of config.SortName) {
            const jsonSort = await openJson(`../resources/data/json/${GeneratorName}/${count}/${eltSort}.json`);
            dataName.push(eltSort);

            const minidata = [];
            for (let i = 0; i < 10; i++) {
                minidata.push(jsonSort.history[(1 + i) * 10][sortValue]);
            }
            data.push(minidata);
        }

        chart.data.datasets = createList(dataName, data);
        chart.update();
    } catch (error) {
        console.error('Dataset update error:', error);
    }
}
