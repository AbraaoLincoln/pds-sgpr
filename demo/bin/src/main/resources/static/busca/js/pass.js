var viagens = [];
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

    let qtyOfRows = viagens.length % 3 == 0 ? viagens.length / 3 : Math.floor((viagens.length / 3)) + 1;
    for(let i = 0; i < qtyOfRows; i++){
        row = createNewRow();
        
        for(let j = 0; j < 3; j++){
            if(lastIndex >= viagens.length){
                break;
            }else{
                row.appendChild(createPassElement(viagens[lastIndex]));
                lastIndex++;
            }
        }
        
        document.getElementById('bkTrips').appendChild(row);
    }

    if(viagens.length % 3 !== 0){
        for(let i = viagens.length; i < (viagens.length + 2); i++){
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
