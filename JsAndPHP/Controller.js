function createIndex() {
    let pageData = getPageData();

    let reqData =
    {
        name: pageData.name,
        password: pageData.password,
        secret: pageData.secret,
        isAdmin: false,
        command: 'createIndex'
    }
    let reqString = JSON.stringify(reqData);

    let answer = requestToServer(reqString);

    showAnswer(answer);
}

function readIndex() {
    let pageData = getPageData();

    let reqData =
    {
        name: pageData.name,
        password: pageData.password,
        secret: pageData.secret,
        isAdmin: false,
        command: 'readIndex'
    }
    let reqString = JSON.stringify(reqData);

    let answer = requestToServer(reqString);

    showAnswer(answer);

    if (answer !== 'wrong name or password') {
        if (answer === 'Admin page open') {
            localStorage.setItem("name", document.getElementById("writeName").value);
            localStorage.setItem("password", document.getElementById("writePassword").value);
            document.location.href = "Admin.html";
            return;
        }
        else {
            localStorage.setItem("name", document.getElementById("writeName").value);
            localStorage.setItem("password", document.getElementById("writePassword").value);
            document.location.href = "User.html";
        }
    }
}


function readUser() {
    let reqData =
    {
        name: localStorage.getItem("name"),
        password: localStorage.getItem("password"),
        secret: document.getElementById("writeText").value,
        isAdmin: 'false',
        command: 'readUser'
    }
    let reqString = JSON.stringify(reqData);

    let answer = requestToServer(reqString);
    if (answer === 'wrong name or password') {
        document.location.href = "Index.html";
    }
    showAnswer(answer);
}

function updateUser() {
    let reqData =
    {
        name: localStorage.getItem("name"),
        password: localStorage.getItem("password"),
        secret: document.getElementById("writeText").value,
        isAdmin: 'false',
        command: 'updateUser'
    }
    let reqString = JSON.stringify(reqData);

    let answer = requestToServer(reqString);
    showAnswer(answer);
}

function deleteUser() {
    let reqData =
    {
        name: localStorage.getItem("name"),
        password: localStorage.getItem("password"),
        secret: document.getElementById("writeText").value,
        isAdmin: 'false',
        command: 'deleteUser'
    }
    let reqString = JSON.stringify(reqData);

    let answer = requestToServer(reqString);
    showAnswer(answer);
}














function readAdmin() {
    let reqData =
    {
        name: localStorage.getItem("name"),
        password: localStorage.getItem("password"),
        secret: document.getElementById("writeText").value,
        isAdmin: true,
        command: 'readAdmin'
    }
    let reqString = JSON.stringify(reqData);

    let answer = requestToServer(reqString);
    let arr = answer.split(',');

    deleteTableRows();
    for (let i = 0; i < arr[0]; i++) {
        let res = arr[i + 1].split(' ');
        createTtableRow(res[1], res[2], res[3], res[4]);
    }
}

function createAdmin() {
    let reqData =
    {
        name: localStorage.getItem("name"),
        password: localStorage.getItem("password"),
        secret: document.getElementById("writeText").value,
        isAdmin: true,
        command: 'createAdmin'
    }
    let reqString = JSON.stringify(reqData);

    let answer = requestToServer(reqString);
    readAdmin();
    showAnswer(answer);
}

function updateAdmin() {
    let reqData =
    {
        name: localStorage.getItem("name"),
        password: localStorage.getItem("password"),
        secret: document.getElementById("writeText").value,
        isAdmin: true,
        command: 'updateAdmin'
    }
    let reqString = JSON.stringify(reqData);

    let answer = requestToServer(reqString);
    readAdmin();
    showAnswer(answer);
}

function deleteAdmin() {
    let reqData =
    {
        name: localStorage.getItem("name"),
        password: localStorage.getItem("password"),
        secret: document.getElementById("writeText").value,
        isAdmin: true,
        command: 'deleteAdmin'
    }
    let reqString = JSON.stringify(reqData);

    let answer = requestToServer(reqString);
    readAdmin();
    showAnswer(answer);
}

function createTtableRow(userId, userName, userPassword, userSecret) {
    var table = document.getElementById("table");

    var row = table.insertRow(1);
    var id = row.insertCell(0);
    var name = row.insertCell(1);
    var password = row.insertCell(2);
    var secret = row.insertCell(3);
    id.innerHTML = userId;
    name.innerHTML = userName;
    password.innerHTML = userPassword;
    secret.innerHTML = userSecret;
}

function deleteTableRows() {
    let table = document.getElementById("table");
    while (table.rows.length > 1) {
        table.deleteRow(1);
    }
}

function getPageData() {
    let name = document.getElementById("writeName").value;
    let password = document.getElementById("writePassword").value;
    let secret = document.getElementById("writeText").value;

    let pageData =
    {
        name: name,
        password: password,
        secret: secret
    }

    return pageData
}

function requestToServer(reqString) {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', 'index.php?data=' + reqString, false);
    xhr.send();
    let answer = xhr.responseText;

    return answer;
}

function showAnswer(answer) {
    document.getElementById('result').innerHTML = answer;
}



