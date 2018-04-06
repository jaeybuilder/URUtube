$ = (id) => {
    return document.getElementById(id);
}

validate = () => {
    var first = $('first').value.trim();
    var last = $('last').value.trim();
    var user = $('user').value.trim();
    var email = $('email').value.trim();
    var pass = $('pass').value.trim();
    if (first == '' || last == '' || email == '' || user == '' || pass == '' ) {
        $('reg-fail').innerHTML = 'Inputs cannot be blank';
        return false;
    } else if (pass.length < 8){
        $('reg-fail').innerHTML = 'Password is too short (minimun 8 characters)';
        return false;
    } else {
        return true;
    }
}

validateLog = () => {
    var user = $('logUser').value.trim();
    var pass = $('logPass').value.trim();
    if (user == '' || pass == '') {
        $('log-fail').innerHTML = 'Inputs cannot be blank';
        return false;
    } else if (pass.length < 8) {
        $('log-fail').innerHTML = 'Password has to be at least 8 characters';
        return false;
    } else {
        return true;
    }
}

validateFile = () => {
    var file = $('file');
    var name = $('name').value.trim();
    var descrip = $('descrip').value.trim();
    if (name == '' || descrip == '') {
    	$('file-fail').innerHTML = 'Inputs cannot be blanks';
        return false;
    } else if (file.files.length == 0) {
        $('file-fail').innerHTML = 'No file selected';
        return false;
    } else {
        return true;
    }
}

