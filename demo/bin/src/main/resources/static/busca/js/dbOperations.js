async function getViagens(urlToGetViagens){
    try {
        let response = await fetch(urlToGetViagens);
        let listOfViagens = await response.json();
        console.log(listOfViagens);
        return listOfViagens;
    } catch (error) {
        console.log(error)
    }
}