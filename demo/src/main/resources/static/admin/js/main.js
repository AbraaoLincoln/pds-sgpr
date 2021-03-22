var page = 0;
var globalResouces = {
    main: [],
    dependence: null,
    id: null
};

function loadLoginPage(){
    window.location.href = 'http://localhost:8080/logout';
}

async function createTableUsers(){   
    page = 1;
    globalResouces.main = await getResources("http://localhost:8080/passageiro");
    let headers = Object.keys(globalResouces.main[0]);

    //load table usuarios
    clearTable();
    createMainTable(headers, globalResouces.main);
    //load serach input and add button
    showSearchAndAdd();
    hideWarnig();
}

async function createTableBus(){
    page = 2;
    globalResouces.main = await getResources("http://localhost:8080/onibus");
    let headers = Object.keys(globalResouces.main[0]);

    //load table onibus
    clearTable();
    createMainTable(headers, globalResouces.main);
    //load serach input and add button
    showSearchAndAdd();
    hideWarnig();
}

async function createTableViagem(){
    page = 4;
    globalResouces.main = await getResources("http://localhost:8080/viagem");
    // globalResouces.main = getFakeViagens();
    let headers = Object.keys(globalResouces.main[0]);

    //load table viagem
    clearTable();
    createMainTable(headers, globalResouces.main);
    //load serach input and add button
    showSearchAndAdd();
    hideWarnig();
}

async function createTableMotorista(){
    page = 5;
    globalResouces.main = await getResources("http://localhost:8080/motorista");
    let headers = Object.keys(globalResouces.main[0]);

    //load table motorista
    clearTable();
    createMainTable(headers, globalResouces.main);
    //load serach input and add button
    showSearchAndAdd();
    hideWarnig();
}

async function createTableRota(){
    page = 3;
    globalResouces.main = await getResources("http://localhost:8080/rota");
    // globalResouces.main = getFakeRota();
    let headers = Object.keys(globalResouces.main[0]);

    //load table rota
    clearTable();
    createMainTable(headers, globalResouces.main);
    //load serach input and add button
    showSearchAndAdd();
    hideWarnig();
}

async function createTableCid(){
    page = 6;
    // globalResouces.main = getFakeCidades();
    globalResouces.main = await getResources("http://localhost:8080/cidades");
    let headers = Object.keys(globalResouces.main[0]);

    //load table cidades   
    clearTable();
    createMainTable(headers, globalResouces.main);
    //load serach input and add button
    showSearchAndAdd();
    hideWarnig();
}

//forms manipulaing

function showForm(){
    document.getElementById('divForms').style.display = 'flex';
}

function hideForm(event){
    event.target.parentNode.parentNode.style.display = 'none';
}

function loadUpdateDeleteForm(event){
    createFormUpdateDelete(event.target.parentNode, event.target.innerText);
    showForm();
}

function showSearchAndAdd(){
    document.getElementById('searchDiv').style.visibility = 'visible';
}

function hideSearchAndAdd(){
    document.getElementById('searchDiv').style.visibility = 'hidden';
    clearTable();
    showWarnig();
}

// waring manipulaing
function showWarnig(){
    document.getElementById('warnig1').style.display = 'flex';
}

function hideWarnig(){
    document.getElementById('warnig1').style.display = 'none';
}

async function addResource(event){
    switch(page){
    	case 2: 
    		let resOnibus = await postResource('http://localhost:8080/onibus', buildJSO(event.target.parentNode.id));
            if(resOnibus.status == "Error") {
                alert(resOnibus.erros);
            }else{
            	alert('Ônibus cadastrado.');
            	createTableBus();
            }
            break;	
        case 3:
            let newRoute = createRotaToPost(event.target.parentNode.id);

            if(newRoute == null) {
                alert("Cidade de Origem ou Destino não é a mesma da rota");
            }else {
                let {rota, cidades} = newRoute;
                let res = await postResource('http://localhost:8080/rota', rota);
                if(res.status == "Sucesso") {
                    res = await postResource('http://localhost:8080/rota/cidades', cidades);
                    if(res.status == "Error") alert(res.erros);
                }else {
                    alert(res.erros);
                }
            }
            break;
        case 4:
            let res = await postResource('http://localhost:8080/viagem', buildJSO(event.target.parentNode.id));
            if(res.status == "Error") {
                alert(res.erros);
            }
            break;
        case 5: 
            let resMotorista = await postResource('http://localhost:8080/motorista', buildJSO(event.target.parentNode.id));
            if(resMotorista.status == "Error") {
                alert(resMotorista.erros);
            }else{
            	alert('Motorista cadastrado.');
            	createTableMotorista();
            }
            break;
        case 6:
            await postResource('http://localhost:8080/cidades', buildJSO(event.target.parentNode.id));
            break;
    }
}

function buildJSO(formId){
    let form = document.getElementById(formId);
    let obj = {};

    for(let e of form.children){
        if(e.nodeName == 'INPUT'){
            if(e.value == ''){
                obj[e.placeholder] = null;
            }else{
                obj[e.placeholder] = e.value;
            }
        }
    }

    return obj;
}

function createRotaToPost(formId) {
    let idRota  = document.getElementById(formId).children[1].value;
    let nome    = document.getElementById(formId).children[2].value;
    let cidades = document.getElementById(formId).children[5].value;
    cidades = cidades.split(",");

    let resourceToPost = {
        // rota: {idRota: idRota, nome: nome, nomeOrigem: cidades[0].trim(), nomeDestino: cidades[cidades.length - 1].trim()},
        rota: {idRota: idRota, nome: nome, nomeOrigem: document.getElementById(formId).children[3].value, nomeDestino: document.getElementById(formId).children[4].value},
        cidades: {
            rotaId: idRota,
            cidades: cidades.map((c, index) => {
            return {
                nome: c.trim(),
                numSeq: index
            }
        })}
    }

    if(resourceToPost.rota.nomeOrigem == resourceToPost.cidades.cidades[0].nome && resourceToPost.rota.nomeDestino == resourceToPost.cidades.cidades[resourceToPost.cidades.cidades.length -1].nome) {
        return resourceToPost;
    }else {
        return null;
    }
} 

async function dltResource(){
    console.log(globalResouces.id); 
    let res = "";
    switch(page){
    	case 2:
            res = await deleteResource(`http://localhost:8080/onibus/${globalResouces.id}`);
            break;
        case 3:
            res = await deleteResource(`http://localhost:8080/rota/${globalResouces.id}`);
            break;
        case 4:
            res = await deleteResource(`http://localhost:8080/viagem/${globalResouces.id}`);
            break;
        case 41:
            res = await deleteResource(`http://localhost:8080/passagens/${globalResouces.id}`);
            break;
		case 5:
			res = await deleteResource(`http://localhost:8080/motorista/${globalResouces.id}`);
			break;
        case 6:
            res = await deleteResource(`http://localhost:8080/cidades/${globalResouces.id}`);
            break;
    }

    if(res.status == "Error") alert(res.erros);
}
