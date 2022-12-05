function goProduct(id){
    console.log(id);
      window.location.href = "product?id=" + id;
}

function deleteProduct(id){
    var http = new XMLHttpRequest();
    http.open('DELETE', 'product?id=' + id, true);

    http.setRequestHeader('Access-Control-Allow-Origin', '*');
    http.setRequestHeader('Access-Control-Allow-Methods', 'GET, PUT, POST, DELETE, HEAD, OPTIONS');
    http.setRequestHeader('Content-Type', 'application/json');
    http.setRequestHeader('Access-Control-Allow-Headers', 'Content-Type, key,Access-Control-Allow-Headers,Access-Control-Allow-Origin, X-Requested-With');

    http.send();
    window.location.href = "index";
}