//0 = add form
//1 = update form
var selectForm = 0;

function showAddForm(){
    selectForm = 0;
    clearForm();
    switch(page){
        case 1:
            createAddForm(Object.keys(globalResouces.main[0]), 'Usuario');
            break;
        case 2:
            createAddForm(Object.keys(globalResouces.main[0]), 'Onibus');
            break;
        case 3:
            createAddRoute(Object.keys(globalResouces.main[0]));
            break;
        case 4:
            let atrVig = Object.keys(globalResouces.main[0]);
            atrVig = atrVig.filter((viagemAtr) => {
                if(viagemAtr == "assentosDisponiveis") {
                    return false;
                }else {
                    return true;
                }
            })
            createAddForm(atrVig.slice(3, atrVig.length), 'Viagem');
            break;
        case 41:
            let atrPass = Object.keys(globalResouces.dependence[0]);
            createAddForm(atrPass.slice(0, atrPass.length-1), 'Passagem');
            break;
        case 5:
            createAddForm(Object.keys(globalResouces.main[0]), 'Motorista');
            break;
        case 6:
            createAddForm([Object.keys(globalResouces.main[0])[1]], 'Cidade');
            break;
    }
    showForm();
}

async function createFormUpdateDelete(row){
    selectForm = 1;
    clearForm();
    document.getElementById('mainForm').className = 'forms ' + "formUp" + page;

    switch(page){
        case 1:
            createHeadForm('Atualizar Usuario');
            break;
        case 2:
            createHeadForm('Atualizar Onibus');
            break;
        case 3:
            createHeadForm('Atualizar Rota');
            break;
        case 4:
            createHeadForm('Atualizar Viagem');
            break;
        case 41:
            createHeadForm('Atualizar Passagem');
            break;
        case 5:
            createHeadForm('Atualizar Motorista');
            break;
        case 6:
            createHeadForm('Atualizar Cidade');
            break;
    }
    
    if(page == 3 || page == 31){
        globalResouces.dependence = await getResources(`http://localhost:8080/rota/${row.children[0].innerText}/cidades`);
        globalResouces.dependence.sort((c1, c2) => {
            if(c1.numSeq < c2.numSeq){
                return -1
            }else if(c1.numSeq > c2.numSeq){
                return 1;
            }else{
                return 0;
            }
        })
        let cityString = "";
        for(let c of globalResouces.dependence){
            cityString += c.nome + ", ";
        }
        let temp = Object.keys(globalResouces.main[0]);
        temp.push("rota");
        createInputForm(temp);
        putValueIntoInput(0, 4, row);
        document.getElementById("mainForm").children[5].value = cityString;
    }else if(page == 4){
        let atrVig = Object.keys(globalResouces.main[0]);
        createInputForm(atrVig.slice(3, atrVig.length));
        putValueIntoInput(3, Object.keys(globalResouces.main[0]).length, row);
    }else if(page == 41){
        let atrPass = Object.keys(globalResouces.dependence[0]);
        createInputForm(atrPass.slice(0, atrPass.length - 1));
        putValueIntoInput(0, Object.keys(globalResouces.dependence[0]).length - 1, row);
    }else{
        createInputForm(Object.keys(globalResouces.main[0]));
        putValueIntoInput(0, Object.keys(globalResouces.main[0]).length, row);
    }
    createBtnsUpdateForm();
        
}

function createAddForm(listOfAtributes, tableName){
    document.getElementById('mainForm').className = 'forms ' + "formAdd" + page;
    createHeadForm('Adicionar Novo ' + tableName);
    createInputForm(listOfAtributes);
    createBtnsAddForm();
}

function createHeadForm(nameForm){
    let h2 = document.createElement('H2');
    h2.innerText = nameForm;
    document.getElementById('mainForm').appendChild(h2);
}

function createInputForm(namesForInput){
    let btnInputPrefix = selectForm ? "heightBtnInputUp" : "heightBtnInputAdd";
    let form = document.getElementById('mainForm');

    for(let prop of namesForInput){
        let input = document.createElement('INPUT');
        input.type = 'text';
        input.placeholder = prop;
        input.className = 'formsInput ' + btnInputPrefix + page;
        form.appendChild(input);
    }
}

function createBtnsAddForm(){
    let classBtnAdd = 'formsButton confirBtn ' + 'heightBtnInputAdd' + page;
    let btn = createButton('Adicionar', classBtnAdd);
    btn.addEventListener('click', addResource);

    document.getElementById('mainForm').appendChild(btn);
    createCancelButton();
}

function createBtnsUpdateForm(){
    let classBtnUp = 'formsButton confirBtn ' + 'heightBtnInputUp' + page;
    let classBtnDel = 'formsButton deleteBtn ' + 'heightBtnInputUp' + page;
    let btnDelete = page == 41 ? createButton('Cancelar', classBtnDel) : createButton('Deletar', classBtnDel);
    btnDelete.addEventListener("click", dltResource);

    document.getElementById('mainForm').appendChild(createButton('Atualizar', classBtnUp));
    document.getElementById('mainForm').appendChild(btnDelete);
    createCancelButton();
}

function createButton(name, classForTheButton){
    let btn = document.createElement('BUTTON');

    btn.innerText = name;

    btn.type = 'button';

    btn.className = classForTheButton;

    return btn;
}

function createCancelButton(){
    let btnInputPrefix = selectForm ? "heightBtnInputUp" : "heightBtnInputAdd";
    let btnCancel = document.createElement('BUTTON');

    btnCancel.innerText = 'Cancelar';

    btnCancel.type = 'button';

    btnCancel.className = 'formsButton ' + btnInputPrefix + page;

    btnCancel.addEventListener('click', hideForm);

    document.getElementById('mainForm').appendChild(btnCancel);
}

function clearForm(){
    document.getElementById('mainForm').innerHTML = '';
}

function createAddRoute(inputNames){
    inputNames.push("origem, nome_cidade1, nome_cidade2, ..., destino");
    document.getElementById('mainForm').className = 'forms ' + "formAdd" + page;
    createHeadForm("Adicionar Rota");
    createInputForm(inputNames);
    createBtnsAddForm();
}

function createUpdateCitytoRoute(cityName){
    let classBtnDel = 'formsButton deleteBtn ' + 'heightBtnInputUp' + page;

    createHeadForm('Atualizar Rota');
    createInputForm([cityName]);
    document.getElementById('mainForm').appendChild(createButton('Deletar', classBtnDel));
    createCancelButton();
}

function calHeightOfForm(numOfEleInForm){
    return (70 * numOfEleInForm) / 9;
}

function putValueIntoInput(starIndex, endIndex, row){
    let rowTds = row.children;
    let form = document.getElementById('mainForm').children;
    let inputs = [];

    for(let e of form){
        if(e.nodeName == "INPUT"){
            inputs.push(e);
        }
    }

    let i = 0;
    while(starIndex < endIndex){
        inputs[i].value = rowTds[starIndex].innerText;
        i++;
        starIndex++;
    }

    globalResouces.id = rowTds[0].innerText;
}
