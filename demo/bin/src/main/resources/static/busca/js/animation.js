function activeAnimation(){
    document.getElementById('searchMenu').children[0].style.display = 'none';
    document.getElementById('searchMenu').style.backgroundColor = 'transparent';
    document.getElementById('searchMenu').children[2].className = 'divBtnSearchUp';
    document.getElementById('searchMenu').children[2].children[0].className = 'btnSearchFormsUp';

    document.getElementById('bkForms').style.height = '10vh';
    setTimeout(changeFormsToUp, 1000);
}

function changeFormsToUp(){
    document.getElementById('searchMenu').className = 'formContainerUp';
    document.getElementById('searchMenu').children[1].className = 'formsUp';
    setTimeout(hideImg, 1000);
}

function hideImg(){
    document.getElementById('bkImg').style.display = 'none';
}

function activeAnimation2(){
    document.getElementById('searchMenu').children[0].style.display = 'none';
    document.getElementById('searchMenu').style.backgroundColor = 'transparent';
    document.getElementById('searchMenu').children[2].className = 'divBtnSearchUp';
    document.getElementById('searchMenu').children[2].children[0].className = 'btnSearchFormsUp';
    document.getElementById('searchMenu').className = 'formContainerUp';
    document.getElementById('searchMenu').children[1].className = 'formsUp';
    document.getElementById('bkForms').style.height = '10vh';
    document.getElementById('bkImg').style.display = 'none';
}

function loadHomepageSearch(){
    document.getElementById('searchMenu').children[0].style.display = 'inline';
    document.getElementById('searchMenu').style.backgroundColor = 'rgba(37, 109, 27, .9)';
    document.getElementById('searchMenu').children[2].className = 'divBtnSearch';
    document.getElementById('searchMenu').children[2].children[0].className = 'btnSearch';
    document.getElementById('searchMenu').className = 'formContainer';
    document.getElementById('searchMenu').children[1].className = 'forms';
    document.getElementById('bkForms').style.height = '100%';
    document.getElementById('bkImg').style.display = 'inline';
    document.getElementById('bkTrips').innerHTML = "";
}