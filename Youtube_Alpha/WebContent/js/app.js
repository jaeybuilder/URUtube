//Utilities 
$ = (id) => {
	return document.getElementById(id);
}
getParam = (param) => {
	var params = (new URL(document.location)).searchParams;
	var value = params.get(param); 
	return value;
}
redirect = (param) => {
	window.location.href = param;
}
refresh = () => {
	var url = new URL(document.location);
	window.location.href = url;
}
//Variables definition
var media_name = [];
var file_name = [];
var media_id = [];
var comment = [];
var xhr = new XHR();
var ls = window.localStorage;
//
function session() {
	$('showUsername').innerHTML = ls.getItem('username');
	if (ls.getItem('username') === null) {
		$('regBtn').classList.remove("d-none");
		$('loginBtn').classList.remove("d-none");
		$('logoutBtn').classList.add("d-none");
		$('userpanel').classList.add("d-none");
		return false;
	} else {
		$('regBtn').classList.add("d-none");
		$('loginBtn').classList.add("d-none");
		$('logoutBtn').classList.remove("d-none");
		$('userpanel').classList.remove("d-none");
		return true;
	}   
}
window.addEventListener("load", init, true);
function init () {
	session();
	if (window.location.href.indexOf('fileup.html') > -1 && !session()) {
		window.location.href = './index.html';
	}
	if (window.location.href.indexOf('search.html') > -1) {
		getRes();
	}
	if (window.location.href.indexOf('stream.html') > -1) {
		stream();
		show_likes();
	}
}
//Sign up - all
function register() {   
	if (validate()) {
		var first = $('first').value;
		var last = $('last').value;
		var user = $('user').value;
		var email = $('email').value;
		console.log($('pass').value);
		var pass = $('pass').value;
		xhr.post('./Register', {first: first, last: last, user: user, email: email, pass: pass}, {'Content-Type':'application/json'}).then(function (data) {
			if(data.res === 'succes') {
				$('reg-success').innerHTML = 'Register successfull';
				$('reg-fail').innerHTML = null;
			} else {
				$('reg-fail').innerHTML = 'An error has occured';	
			}
		});
		setTimeout(function() {$('reg-success').innerHTML = null;}, 5000);
	}	
}
//Log in - all
function login() {
	if (validateLog()) {
		var user = $('logUser').value;
		var pass = $('logPass').value;
		xhr.post('./Login', {user: user, pass: pass}, {'Content-Type':'application/json'}).then(function (data) {
			if (data.status === 404) {
				$('log-fail').innerHTML = 'Wrong username or password';
			} else {
				ls.setItem('username', data.res);
				$('log-succes').innerHTML = 'Welcome '+data.res;
				$('logbtn').hidden = true;
				session();
			}
		});
	}
}
//Destroy session 
function destroy() {
	xhr.get('./Logout', {}, {}).then(function (data){
		ls.clear();
		refresh();
	});
}
//Search 
function search () {
	var value = $('search').value;
	window.location.href = './search.html?search_query='+value;
	$('search').value = value; 
}
//
function getRes () {
	var value = getParam('search_query');
	xhr.get('./Result?search_query='+value, {}, {}).then( function (data){
		if (data.res === 'null') {
			console.log('No results');
		} else {
			for (var key in data) {
				$('video-list').appendChild(search_results(data[key]['media_name'], data[key]['media_id']));
			}
		}
	});
}
//Fileup - fileup.html
function fileup() {
	if (validateFile()) {
		var XHR = new XMLHttpRequest();
		var formData = new FormData();
		formData.append('file', $('file').files[0]);
		formData.append('name', $('name').value);
		formData.append('descrip', $('descrip').value);
		XHR.onreadystatechange = function () {  
			if (XHR.status === 200 && XHR.readyState === 4) {
				$("uploadStatus").textContent = XHR.responseText + "\nFile uploaded";
			}
		}
		XHR.open('POST', './FileUp', true);
		XHR.send(formData);
	}
}
//Video download - stream
function download() {
	var media_id = getParam('media_id');
	xhr.get('./Download?', {'media_id': media_id}, {}).then(function (data){
		console.log(data);  
	});
}
//Video Streaming 
function stream() {
	var media_id = getParam('media_id');
	$("video").src = './Stream?media_id='+media_id;
}
//Likes 
function show_likes() {
	var media_id = getParam('media_id');
	xhr.get('./Likes', {'media_id': media_id}, {}).then(function (data){
		$('likes').innerHTML = data.res1;
		if (data.res0 > 0) {
			$('likebtn').disabled = true;
			show_comment();
			
			
			
		}
	});
}
//
function give_like () {
	var media_id = getParam('media_id');
	xhr.post('./Likes', {'media_id': media_id}, {'Content-Type':'application/json'}).then(function (data){
		console.log(data);
		show_likes();
	});
}
//Comments
function show_comment () {
	var media_id = getParam('media_id');
	xhr.get('./Comment', {'media_id': media_id}, {'Content-Type':'application/json'}).then( function (data) {
		if (data.comment === 0) {
			console.log('No comments');
		} else {
			for (var key in data) {
				console.log(data[key]['comment_text']);
			}
		}
	});
}
//
function post_comment () {
	var media_id = getParam('media_id');
	var comment_text = $('comment_text').value;
	xhr.post('./Comment', {'media_id': media_id, 'comment_text': comment_text}, {'Content-Type':'application/json'}).then(function () {
		$('comment_text').innerHTML = '';
	});
}