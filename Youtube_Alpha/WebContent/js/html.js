function search_results(name, mediaid) {
    var p = document.createElement('p');
    var a = document.createElement('a');
    var div = document.createElement('div');
    p.innerHTML = name;
    p.classList.add('h5');
    p.classList.add('text-center');
    a.href = './stream.html?media_id='+mediaid;
    a.appendChild(p);
    div.classList.add('form-group');
    div.appendChild(a);
    return div;
}
	
