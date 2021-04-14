function createTableHead(table, headersNames){
    let tr = document.createElement('TR');
    // let cellWidth = page == 3 ? getCellNewWidth(headersNames.length + 1) : getCellNewWidth(headersNames.length);

    for(let i = 0; i < headersNames.length;i++){
        let th = document.createElement('TH');
        th.innerText = headersNames[i];
        // th.width = cellWidth;
        tr.appendChild(th);
    }

    if(page == 3 || page == 4){
        let th = document.createElement('TH');
        th.innerText = page == 3 ? 'Ver Rota' : 'Passagens';
        // th.width = cellWidth;
        tr.appendChild(th);
    }

    table.appendChild(tr);
}

function createRows(table, headersNames, resouces){
    // let cellWidth = page == 3 ? getCellNewWidth(headersNames.length + 1) : getCellNewWidth(headersNames.length);

    for(let i = 0; i < resouces.length; i++){
        let tr = document.createElement('TR');

        for(let j = 0; j < headersNames.length; j++){
            let td = document.createElement('TD');
            td.innerText = resouces[i][headersNames[j]];
            // td.width = cellWidth;
            tr.appendChild(td);
        }

        if(page == 3 || page == 4){
            let td = document.createElement('TD');
            let i = document.createElement('I');
            i.className = 'fas fa-eye';
            // i.addEventListener('click', showTableRoute);
            i.addEventListener('click', showSubTable);
            td.appendChild(i);
            // td.width = cellWidth;
            tr.appendChild(td);
        }

        tr.addEventListener('dblclick', loadUpdateDeleteForm);
        table.appendChild(tr);
    }
}

function getCellNewWidth(amountOfHeaders){
    let width = 100 / amountOfHeaders;
    let final_width = width + '%';
    return final_width;
}

function createMainTable(headers, resouces){
    let table = document.getElementById('tableMainContent');
    createTableHead(table, headers);
    createRows(table, headers, resouces);
}

function createSubTable(headers, resouces){
    if(page != 31){
        let table = getSubTable();
        createTableHead(table, headers);
        createRows(table, headers, resouces);
    }else{
        createRouteTable(resouces);
    }
}

function clearTable(){
    document.getElementById('tableMainContent').innerHTML = '';
    if(document.getElementById('main').children[2].id == 'subTable'){
        document.getElementById('main').removeChild(document.getElementById('main').children[2]);
    }
}

async function showSubTable(event){
    updatePage();
    let rowToShow = event.target.parentNode.parentNode;
    let tableRotas = document.getElementById('tableMainContent').children;
    globalResouces.dependence = await getRightResources(rowToShow);

    if(globalResouces.dependence.length > 0){
        for(let i = 1; i < tableRotas.length; i++){
            if(tableRotas[i] !== rowToShow){
                tableRotas[i].style.display = 'none';
            }
        }
    
        event.target.className = 'fas fa-eye-slash'
        event.target.removeEventListener('click', showSubTable);
        event.target.addEventListener('click', hideSubTable);
        createSubTable(Object.keys(globalResouces.dependence[0]), globalResouces.dependence);
    }else{
        alert("Nehuma passagem foi encontrada para essa viagem!");
    }
}

function hideSubTable(event){
    let tableRotas = document.getElementById('tableMainContent').children;

    for(let i = 1; i < tableRotas.length; i++){
        tableRotas[i].style.display = 'table-row';
    }

    event.target.className = 'fas fa-eye'
    event.target.removeEventListener('click', hideSubTable);
    event.target.addEventListener('click', showSubTable);

    document.getElementById('main').removeChild(document.getElementById('main').children[2]);

    //Atualiza a pagina para a pagina principal
    globalResouces.dependence = null;
    updatePage();
}

function updatePage(){
    switch(page){
        case 3:
            page = 31;
            break;
        case 31: //Esta mostrando a subTable de rotas
            page = 3;
            break;
        case 4:
            page = 41;
            break;
        case 41: //Esta mostrando a subTable de viagens
            page = 4;
            break;
        default:
            page = 0;
    }
}

async function getRightResources(rowToShow){
    switch(page){
        case 31: //Esta mostrando a subTable de rotas
            return await getResources(`http://localhost:8080/rota/${rowToShow.children[0].innerText}/cidades`);
        case 41: //Esta mostrando a subTable de viagens
            return await getResources(`http://localhost:8080/passagens?viagemId=${rowToShow.children[0].innerText}`);
        default:
            return [];
    }
}

function getSubTable(){
    let table = document.createElement('TABLE');
    table.className = 'tableMainContent';
    table.id = 'subTable';
    document.getElementById('main').insertBefore(table, document.getElementById('main').children[2]);
    return table;
}

function createRouteTable(citys){
    console.log(citys);
    let table = getSubTable();

    let row = document.createElement('TR');
    let th = document.createElement('TH');
    th.innerText = 'Rota';
    row.appendChild(th);

    table.appendChild(row);

    citys.sort((c1, c2) => {
        if(c1.numSeq < c2.numSeq){
            return -1
        }else if(c1.numSeq > c2.numSeq){
            return 1;
        }else{
            return 0;
        }
    })
    for(let i = 0; i < citys.length; i++){
        let tr = document.createElement('TR');

        let td = document.createElement('TD');
        td.innerText = citys[i].nome;
        tr.appendChild(td);

        // tr.addEventListener('dblclick', loadUpdateDeleteForm);
        table.appendChild(tr);
    }
};