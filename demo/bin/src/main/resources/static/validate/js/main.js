var viagens = [];
var page = 0;
var passToValidate = {
    viagem: "",
    codValidacao: ""
};
var pageStack = [];
pageStack.push(1);

async function login() {
    //http://localhost:8080/viagem/motorista/29306192100
    try {
        let response = await fetch(`http://localhost:8080/viagem/motorista/${document.getElementById("cpfMoto").value}`);
        viagens = await response.json();
        pageStack.push(2);
        loadPage();
    } catch (error) {
        console.log(error);
    }
}

function loadPage() {
    switch(pageStack[pageStack.length - 1]) {
        case 1:
            document.getElementById("bkForms").style.display = "flex";
            document.getElementById("bkList").style.display = "none";
            document.getElementById("bkImgValidate").style.display = "none";
            break;
        case 2:
            document.getElementById("bkForms").style.display = "none";
            document.getElementById("bkList").style.display = "flex";
            document.getElementById("bkImgValidate").style.display = "none";
            loadList();
            break;
        case 3:
            document.getElementById("bkForms").style.display = "none";
            document.getElementById("bkList").style.display = "none";
            document.getElementById("bkImgValidate").style.display = "flex";
            break;
    }
}

function loadList() {
    let listContainer = document.getElementById("bkList");
    listContainer.innerHTML = "";
    listContainer.appendChild(createBackHeader());

    for(let viagem of viagens) {
        let d = new Date(viagem.data)
        viagem.dataMile = d.getTime();
    }

    viagens.sort((d1, d2) => {
        if(d1.dataMile == d2.dataMile) {
            return 0;
        }else if(d1.dataMile < d2.dataMile) {
            return -1;
        }else {
            return 1;
        }
    })

    for(let viagem of viagens) {
        listContainer.appendChild(createPass(viagem));
    }
}

function createBackHeader() {
    let upBar = document.createElement("DIV");
    upBar.className = "header-list";

    let arrowLeftIcon = document.createElement("I");
    arrowLeftIcon.className = "fas fa-arrow-left";
    arrowLeftIcon.addEventListener("click", returnLastPage);
    upBar.appendChild(arrowLeftIcon);
    
    let menuIcon = document.createElement("I");
    menuIcon.className = "fas fa-bars";
    upBar.appendChild(menuIcon);

    return upBar;
}

function createPass(viagem) {
    let backViagem = document.createElement("DIV");
    backViagem.className = "backViagem";
    backViagem.addEventListener('click', loadValidate);

    let viagemName = document.createElement("H3");
    viagemName.innerText = "Viagem-" + viagem.id;
    backViagem.appendChild(viagemName);

    let row = document.createElement("DIV");
    row.className = "row-viagem";
    backViagem.appendChild(row);

    let rightCol = document.createElement("DIV");
    rightCol.className = "col-viagem";
    row.appendChild(rightCol);

    let spanOrigem = document.createElement("SPAN");
    spanOrigem.innerText = "Origem: " + viagem.origem;
    rightCol.appendChild(spanOrigem);

    let spanDestino = document.createElement("SPAN");
    spanDestino.innerText = "Destino: " + viagem.destino;
    rightCol.appendChild(spanDestino);

    let leftCol = document.createElement("DIV");
    leftCol.className = "col-viagem";
    row.appendChild(leftCol);

    let spanData = document.createElement("SPAN");
    spanData.innerText = "Data: " + viagem.data;
    leftCol.appendChild(spanData);

    let spanHoraSaida = document.createElement("SPAN");
    spanHoraSaida.innerText = "Hora da Saida: " + viagem.horaSaida;
    leftCol.appendChild(spanHoraSaida);

    let shadowRow = document.createElement("DIV");
    shadowRow.className = "shadow-row";
    backViagem.appendChild(shadowRow);

    return backViagem;
}

function loadValidate(event) {
    page = 3;
    pageStack.push(3);
    passToValidate.viagem = event.target.parentNode.children[0].innerText;
    document.getElementById("spanValidateViagem").innerText = passToValidate.viagem;
    document.getElementById("passCod").value = "";
    loadPage();
}

function returnLastPage() {
    if(pageStack.length > 0) {
        page = pageStack.pop();
        loadPage();
    }
}

async function validatePass() {
    passToValidate.codValidacao = document.getElementById("passCod").value;
    document.getElementById("passCod").value = "";

    // console.log(passToValidate);
    passToValidate.viagem = passToValidate.viagem.split("-");
    passToValidate.viagem = passToValidate.viagem[1];
    // console.log(passToValidate);

    try {
        let response = await fetch("http://localhost:8080/passagens/validate", {
            method: "PUT",
            headers: {
                "Accept": "application/json",
                "Content-type": "application/json"
            },
            body: JSON.stringify(passToValidate)
        })
        let resObj = await response.json();

        if(resObj.status == "Sucesso") {
            alert("Passagem Validade com sucesso");
        }else {
            alert(resObj.erros);
        }
    } catch (error) {
        console.log(error);
    }
}