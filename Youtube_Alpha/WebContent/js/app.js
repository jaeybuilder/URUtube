$ = (id) => {
    return document.getElementById(id);
}

var xhr = new XHR();

//Sign up
register = () => {
    if (validate()) {
        var first = $('first').value;
        var last = $('last').value;
        var user = $('user').value;
        var email = $('email').value;
        var pass = $('pass').value;
        xhr.post('./Register', {first: first, last: last, user: user, email: email, pass: pass}, {'Content-Type':'application/x-www-form-urlencoded'}).then(function (data) {
            console.log(data);
        });
    } 
}
//log in
login = () => {
    if (validateLog()) {
        var user = $('logUser').value;
        var pass = $('logPass').value;
        xhr.post('./Login', {user: user, pass: pass}, {'Content-Type':'application/x-www-form-urlencoded'}).then(function (data) {
            console.log(data);
        });
    }
}
//Destroy session
destroy = () => {
    xhr.get('./SessionDestroy', {}, {'Content-Type':'application/x-www-form-urlencoded'}).then(function (data) {
        console.log(data);
    });
}
//Upload 
upload = () => {
    if (validateFile()) {
        var name = $('name').value;
        var descrip = $('descrip').value;
        var xhr2 = new XMLHttpRequest();
        var formData = new FormData();
        formData.append("file", $("file").files[0]);
        xhr2.onreadystatechange = function () {
            if (xhr2.status === 200 && xhr2.readyState === 4) {
                $("uploadStatus").textContent = xhr2.responseText + "\nFile uploaded";
            }
        }
        xhr2.open("post", "./FileUp", true);
        xhr2.send(formData);   
        xhr.post('./FileUp', { name: name, descrip: descrip}, { 'Content-Type': 'application/x-www-form-urlencoded' }).then(function (data) {
            console.log(data);
        });
    }
}