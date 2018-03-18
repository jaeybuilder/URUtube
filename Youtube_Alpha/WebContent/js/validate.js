$ = (id) => {
    return document.getElementById(id);
}

validate = () => {
    var first = $('first').value;
    var last = $('last').value;
    var user = $('user').value;
    var email = $('email').value;
    var pass = $('pass').value;
    if (first == '' || last == '' || email == '' || user == '' || pass == '' ) {
        alert('Fill all spaces');
        return false;
    } else if (pass.length < 8){
        alert('Password has to be at least 8 characters');
        return false;
    } else {
        return true;
    }
}

validateLog = () => {
    var user = $('logUser').value;
    var pass = $('logPass').value;
    if (user == '' || pass == '') {
        alert('Fill all spaces');
        return false;
    } else if (pass.length < 8) {
        alert('Password has to be at least 8 characters');
        return false;
    } else {
        return true;
    }
}

validateFile = () => {
    var file = $('file');
    if (file.files.length == 0) {
        alert("No files selected");
        return false;
    } else {
        return false;
    }
}