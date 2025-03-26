function afficherJSON(data, key) {
    try {
        return data[key];
    } catch (error) {
        console.error('Erreur de lecture JSON:', error.message);
    }
}

function formatSortName(name) {
    return name.replace(/([A-Z])/g, ' $1').trim();
}

function createSortCard(sortName, config) {
    const card = document.createElement('div');
    card.className = 'card';
    
    const header = document.createElement('div');
    header.className = 'card-header';
    
    const title = document.createElement('h2');
    title.className = 'card-title';
    title.textContent = formatSortName(sortName);

    const metricDropdown = createDropdown({
        width: '120px',
        buttonId: 'dropdownSortValue',
        defaultText: 'comparaisons', 
        options: ["comparaisons", "arrayAccess", "set", "swap", "delay"], 
        onSelect: () => updateSortDatasets(sortName)
    });

    const countDropdown = createDropdown({
        width: '100px',
        buttonId: 'dropdownCount',
        defaultText: '1000',
        options: config.Count,
        onSelect: () => updateSortDatasets(sortName)
    });

    const dropdownContainer = document.createElement('div');
    dropdownContainer.className = 'dropdownsligne';
    dropdownContainer.append(metricDropdown, countDropdown);
    
    header.append(title, dropdownContainer);
    
    const canvas = document.createElement('canvas');
    canvas.id = `chart-${sortName}`;
    
    card.append(header, canvas);
    
    return card;
}

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
            updateSortDatasets(dropdownButton.closest('.card').querySelector('canvas').id.replace('chart-', ''));
        });
        dropdownContent.appendChild(option);
    });
    
    dropdown.append(dropdownButton, dropdownContent);
    return dropdown;
}

async function updateSortDatasets(sortName) {
    try {
        const card = document.getElementById(`chart-${sortName}`).closest('.card');
        const dropdowns = card.querySelectorAll('.dropdown-button');
        const count = dropdowns[1].textContent;
        const metric = dropdowns[0].textContent;

        const config = await openJson('../../setting/config.json');
        const chartId = `chart-${sortName}`;
        const chart = Chart.getChart(chartId);

        const generatorNames = [];
        const metricValues = [];

        for (const generator of config.GeneratorName) {
            const jsonData = await openJson(`../resources/data/json/${generator}/${count}/${sortName}.json`);
            generatorNames.push(formatGeneratorName(generator));
            metricValues.push(jsonData.global[metric]);
        }

        chart.data.labels = generatorNames;
        chart.data.datasets[0].label = metric;
        chart.data.datasets[0].data = metricValues;
        chart.update();
    } catch (error) {
        console.error('Erreur de mise à jour des données:', error);
    }
}

function formatGeneratorName(name) {
    if (name.match(/\d+\.\d+$/)) {
        return name.replace('Strategy', '').replace(/(\d+\.\d+)$/, ' $1');
    }
    return name.replace('Strategy', '');
}