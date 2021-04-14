var viagensSerach = [];
let empColors = ['emp1', 'emp2', 'emp3', 'emp4', 'emp5'];

function createNewRow(){
    let row = document.createElement('DIV');
    row.className = 'rowSearchResponse';

    return row;
}

function createPassElement(passInfo){
    let container = document.createElement('DIV');
    container.className = 'passContainer';

    //create empresa container
    let empContainer = document.createElement('DIV');
    empContainer.className = 'empName';
    empContainer.classList.add(empColors[Math.floor(Math.random() * empColors.length)]);
    let spanEmpName = document.createElement('SPAN');
    spanEmpName.innerText = passInfo.empresa;
    empContainer.appendChild(spanEmpName);

    container.appendChild(empContainer);

    //create passInfo container
    let passInfoContainer = document.createElement('DIV');
    passInfoContainer.className = 'passInfo';

    //info
    let infoContainer = document.createElement('DIV');
    infoContainer.className = 'passInfoContainer';

    //passInfoCol1
    let col1 = document.createElement('DIV');
    col1.className = 'passInfoCol';

    //span that have the info
    let spanDest = document.createElement('SPAN');
    spanDest.innerText = passInfo.destino;
    let spanHoraSaida = document.createElement('SPAN');
    spanHoraSaida.innerText = 'Saida as ' + passInfo.horaSaida;
    let spanAssentos = document.createElement('SPAN');
    // spanAssentos.innerText = 'Assentos Disponives: ' + passInfo.assentos;
    spanAssentos.innerText = 'Assentos Disponives: ' + passInfo.id;

    col1.appendChild(spanDest);
    col1.appendChild(spanHoraSaida);
    col1.appendChild(spanAssentos);
    infoContainer.appendChild(col1);

    passInfoContainer.appendChild(infoContainer);

    //final add the info container
    container.appendChild(passInfoContainer);

    //create buttons container
    let precoContainer = document.createElement('DIV');
    precoContainer.className = 'passPrice';

    //span that have the info
    let spanPreco = document.createElement('SPAN');
    spanPreco.innerText = 'R$ ' + passInfo.preco;

    //create buttons
    let btnComprar = document.createElement('BUTTON');
    btnComprar.type = 'button';
    btnComprar.className = 'btnPass';
    btnComprar.innerText = 'Comprar';

    precoContainer.appendChild(spanPreco);
    precoContainer.appendChild(btnComprar);

    container.appendChild(precoContainer);

    return container;
}

async function buildResponseOfSearch(){
    // let response = getResponse();
    let lastIndex = 0;
    let row = {};

    let qtyOfRows = viagensSerach.length % 3 == 0 ? viagensSerach.length / 3 : Math.floor((viagensSerach.length / 3)) + 1;
    for(let i = 0; i < qtyOfRows; i++){
        row = createNewRow();
        
        for(let j = 0; j < 3; j++){
            if(lastIndex >= viagensSerach.length){
                break;
            }else{
                row.appendChild(createPassElement(viagensSerach[lastIndex]));
                lastIndex++;
            }
        }
        
        document.getElementById('bkTrips').appendChild(row);
    }

    if(viagensSerach.length % 3 !== 0){
        for(let i = viagensSerach.length; i < (viagensSerach.length + 2); i++){
            if(i % 3 == 0) break;
            row.appendChild(createFakePassContainer());
        }
    }
}

function createFakePassContainer(){
    let container = document.createElement('DIV');
    container.className = 'passContainer';
    container.style.visibility = 'hidden';

    return container;
}

function createViewPass(pass) {
    document.getElementById("passPage").innerHTML = "";
    let backOfPass = document.createElement("DIV");
    backOfPass.className = "backOfPass";

    //add empName
    backOfPass.appendChild(createEmpName(pass));

    //add passInfo
    backOfPass.appendChild(createPassInfo(pass));

    document.getElementById("passPage").appendChild(backOfPass);
}

function createEmpName(pass) {
    let divEmpName = document.createElement("DIV");
    divEmpName.className = "empName-pass";

    let spanEmpName = document.createElement("SPAN");
    spanEmpName.innerText = pass.empresa;
    divEmpName.appendChild(spanEmpName);
    
    
    let spanPass = document.createElement("SPAN");
    spanPass.innerText = "Passagem";
    divEmpName.appendChild(spanPass);

    return divEmpName;
}

function createPassInfo(pass) {
    let backPassInfo = document.createElement("DIV");
    backPassInfo.className = "backPassInfo";

    //create first row
    let row1 = createPassRow();
    row1.appendChild(createPassCol("Nome do Passageiro", pass.nome, null));
    row1.appendChild(createPassCol("CPF", pass.cpf, null));
    row1.appendChild(createPassCol("Assento", pass.assento, null));
    backPassInfo.appendChild(row1);

    //create second row
    let row2 = createPassRow();
    row2.appendChild(createPassCol("", pass.origem, pass.destino));
    row2.appendChild(createPassCol("Data", pass.data, null));
    row2.appendChild(createPassCol("Hora de Saida", pass.horaSaida, null));
    backPassInfo.appendChild(row2);

    //create third row
    let row3 = createPassRow();
    row3.classList.add("rowCod");
    let spanCod = document.createElement("SPAN");
    spanCod.innerText = pass.cod;
    row3.appendChild(spanCod);
    backPassInfo.appendChild(row3);

    return backPassInfo;
}

function createPassRow() {
    let divPassRow = document.createElement("DIV");
    divPassRow.className = "passRow";
    return divPassRow;
}

function createPassCol(infoName, infoValue, infoValue2) {
    let divPassCol = document.createElement("DIV");
    divPassCol.className = "passCol";

    let spanInfoName = document.createElement("SPAN");
    spanInfoName.innerText = !infoValue2 ? infoName + ":" : "Origem: " + infoValue;
    divPassCol.appendChild(spanInfoName);

    let spanInfoValue = document.createElement("SPAN");
    spanInfoValue.innerText = !infoValue2 ? infoValue : "Destino: " + infoValue2;
    divPassCol.appendChild(spanInfoValue);

    return divPassCol;
}