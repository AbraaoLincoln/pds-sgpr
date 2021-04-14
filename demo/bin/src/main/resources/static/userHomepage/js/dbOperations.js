async function getResource(urlToGetResource){
    try {
        let response = await fetch(urlToGetResource);
        let resources = await response.json();
        // console.log(resources);
        return resources;
    } catch (error) {
        console.log(error)
    }
}

async function deletePassOnDB(passId) {
    try {
        let response = await fetch(`http://localhost:8080/passagens/${passId}`, {
            method: "DELETE"
        });
        let responseObj = await response.json();
        // console.log(responseObj);
        return responseObj;
    } catch (error) {
        console.log(error);
    }
}