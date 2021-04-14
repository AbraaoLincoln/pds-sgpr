async function createListOfPass() {
    document.getElementById("tableOfDivs").innerHTML = "";
    createHeaders();

    for(let pass of listOfPass) {
        let viagem = await getResource("http://localhost:8080/viagem/" + pass.viagem); 
        viagens.push(viagem);
        createRow({
            codVal: pass.codValidacao,
            origem: viagem.origem,
            destino: viagem.destino,
            data: viagem.data,
            horaSaida: viagem.horaSaida
        });
    }
}

function createHeaders() {
    let header = document.createElement("DIV");
    header.className = "listOfPass-row";

    let spanCodVal = document.createElement("SPAN");
    spanCodVal.className = "listOfPass-span listOfPass-divBtn";
    spanCodVal.innerText = "Cod";
    header.appendChild(spanCodVal);

    let spanOrigem = document.createElement("SPAN");
    spanOrigem.className = "listOfPass-span listOfPass-divBtn";
    spanOrigem.innerText = "Origem";
    header.appendChild(spanOrigem);

    let spanDestino = document.createElement("SPAN");
    spanDestino.className = "listOfPass-span listOfPass-divBtn";
    spanDestino.innerText = "Destino";
    header.appendChild(spanDestino);

    let spanData = document.createElement("SPAN");
    spanData.className = "listOfPass-span listOfPass-divBtn";
    spanData.innerText = "Data";
    header.appendChild(spanData);

    let spanHoraSaida = document.createElement("SPAN");
    spanHoraSaida.className = "listOfPass-span listOfPass-divBtn";
    spanHoraSaida.innerText = "Hora de Saida";
    header.appendChild(spanHoraSaida);

    let spanCancelar = document.createElement("SPAN");
    spanCancelar.className = "listOfPass-span listOfPass-divBtn";
    spanCancelar.innerText = "";
    header.appendChild(spanCancelar);

    document.getElementById("tableOfDivs").appendChild(header);
}

function createRow(pass) {
    let row = document.createElement("DIV");
    row.className = "listOfPass-row";

    let spanCodVal = document.createElement("SPAN");
    spanCodVal.className = "listOfPass-span listOfPass-divBtn";
    spanCodVal.innerText = pass.codVal;
    row.appendChild(spanCodVal);

    let spanOrigem = document.createElement("SPAN");
    spanOrigem.className = "listOfPass-span listOfPass-divBtn";
    spanOrigem.innerText = pass.origem;
    row.appendChild(spanOrigem);

    let spanDestino = document.createElement("SPAN");
    spanDestino.className = "listOfPass-span listOfPass-divBtn";
    spanDestino.innerText = pass.destino;
    row.appendChild(spanDestino);

    let spanData = document.createElement("SPAN");
    spanData.className = "listOfPass-span listOfPass-divBtn";
    spanData.innerText = pass.data;
    row.appendChild(spanData);

    let spanHoraSaida = document.createElement("SPAN");
    spanHoraSaida.className = "listOfPass-span listOfPass-divBtn";
    spanHoraSaida.innerText = pass.horaSaida;
    row.appendChild(spanHoraSaida);

    let divBtn = document.createElement("DIV");
    divBtn.className = "listOfPass-span listOfPass-divBtn";
    let cancelBtn = document.createElement("BUTTON");
    cancelBtn.innerText = "Cancelar";
    cancelBtn.addEventListener("click", deletePass);
    divBtn.appendChild(cancelBtn);
    row.appendChild(divBtn);

    document.getElementById("tableOfDivs").appendChild(row);
}