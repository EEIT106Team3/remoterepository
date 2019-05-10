var request = null;
function sendRequest(id, url) {
	request = new XMLHttpRequest();
	request.onreadystatechange=callback;
	request.open("POST", url);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send("id="+id);
}
function callback() {
	if(request.readyState==4) {
		if(request.status==200) {
			var json = JSON.parse(request.responseText);
			
			document.getElementById("data").appendChild(document.createTextNode(json[0].text));
			if(json[0].hasMoreData) {
				document.forms[0].id.value = json[1].id;
				document.forms[0].name.value = json[1].name;
				document.forms[0].price.value = json[1].price;
				document.forms[0].make.value = json[1].make;
				document.forms[0].expire.value = json[1].expire;
			}
		} else {
			console.log("Opps! Somthing went wrong. "+request.statusText);
		}
	}
}
