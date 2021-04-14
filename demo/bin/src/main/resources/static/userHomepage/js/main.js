var page = 0;
var listOfPass = getListOfPass();
var viagens = []

function testeMenu() {
    let menuDropDown = document.getElementById("dropMenu");
    
    if(menuDropDown.style.display == "none") {
        menuDropDown.style.display = "flex";
    }else {
        menuDropDown.style.display = "none";
    }
}

function loadPassPage() {
    page = 1;
    loadPage();
}

function loadlistOfPassPage() {
    page = 2;
    loadPage();
}

function loadSearchPage() {
    page = 3;
    loadPage();
}

async function loadPage() {
    // console.log(document.getElementsByTagName("main"));
    switch(page){
        case 1:
            console.log("loading page 1")
            document.getElementById("bkForms").style.display = "none";
            document.getElementById("bkTrips").style.display = "none";
            document.getElementById("tableOfDivs").style.display = "none";

            document.getElementById("passPage").style.display = "flex";
            createViewPass({
                nome: "Fulano",
                cpf: "12345678901",
                assento: 12,
                origem: "Da saida",
                destino: "Na chegada",
                data: "2021-03-20",
                horaSaida: "13:00",
                cod: "1234567890120210320130012",
                empresa: "Fuguete"
            });
            break;
        case 2:
            console.log("loading page 2")
            document.getElementById("passPage").style.display = "none";
            document.getElementById("bkForms").style.display = "none";
            document.getElementById("bkTrips").style.display = "none";

            document.getElementById("tableOfDivs").style.display = "Flex";
            listOfPass = await getResource("http://localhost:8080/passagens/40811470784");
            createListOfPass();
            break;
        case 3:
            console.log("loading page 3")
            document.getElementById("passPage").style.display = "none";
            document.getElementById("tableOfDivs").style.display = "none";

            document.getElementById("bkForms").style.display = "flex";
            document.getElementById("bkTrips").style.display = "flex";
            break;
    }
}

function loadLoginPage(){
    window.location.href = 'http://localhost:8080/login/';
}

async function startBusca(){
    let org = document.getElementById('origem').value;
    let des = document.getElementById('destino').value;

    if(org == ''){
        alert('Escolha uma origem(local de partida)!');
    }else{
        document.getElementById('bkTrips').innerHTML = "";

        if(des) {
            viagensSerach = await getResource(`http://localhost:8080/viagem/busca?origem=${org}&destino=${des}`);
        }else {
            viagensSerach = await getResource(`http://localhost:8080/viagem/busca?origem=${org}`);
        }
        // viagens = getFakeResponse();

        if(viagensSerach.length == 0){
            loadWaringNotFoundViagens();
        }else{
            buildResponseOfSearch();
        }
    }
}

function makeUrlWithOrgDes(origem, destino){
    return `http://localhost:8080/viagens/busca?origem=${origem}&destino=${destino}`;
}

function makeUrlWithOrg(origem){
    return `http://localhost:8080/viagens/busca?origem=${origem}`;
}

function loadWaringNotFoundViagens(){
    let div = document.createElement("DIV");
    div.className = "divWarginViagem";
    let span = document.createElement("SPAN");
    span.innerText = "Nenhuma viagem encontrada :(";
    div.appendChild(span);
    document.getElementById('bkTrips').appendChild(div);
}

async function deletePass(event) {
    let passId = event.target.parentNode.parentNode.children[0].innerText;
    let res = await deletePassOnDB(passId);
    console.log(res);
    if(res.status == "Error") {
        alert(res.erros);
    }else {
        alert("Passagem Cancelada");
    }
}