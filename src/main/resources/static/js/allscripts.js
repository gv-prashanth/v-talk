function validateSenderReceiver(){
	var sender;
	var receiver;
	if((new URLSearchParams(window.location.search)).get('sender')==null || (new URLSearchParams(window.location.search)).get('sender')=="")
		sender = prompt("What is your nick?");
	if((new URLSearchParams(window.location.search)).get('receiver')==null || (new URLSearchParams(window.location.search)).get('receiver')=="")
		receiver = prompt("What is your friend's nick?");
	if(sender!=null || receiver!=null)
		window.location.href = '/testi.html' + '?sender='+sender+'&receiver='+receiver;
}

function populateChatContainer() {
	var xmlhttp = new XMLHttpRequest(); // new HttpRequest
	// instance
	xmlhttp.open("GET", "/v2/chats?sender="+(new URLSearchParams(window.location.search)).get('sender')+"&receiver="+(new URLSearchParams(window.location.search)).get('receiver'));
	xmlhttp.setRequestHeader("Content-Type", "application/json");
	xmlhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var resp = JSON.parse(this.responseText);
			printResp(resp);
		}
	};
	xmlhttp.send();
}

function printResp(resp) {
	if (resp.length > 0) {
		document.getElementById("catalog").innerHTML = "";
		for (var i = 0; i < resp.length; i++) {
			var styleString = 'style="float: left;"';
			if((new URLSearchParams(window.location.search)).get('sender').toLowerCase() == resp[i].sender.toLowerCase())
				styleString = 'style="float: right;background-color: #bbbbbb;"';
			document.getElementById("catalog").innerHTML += '<div class="square" '+styleString+'> <div class="repoDesc"> <p> <b>'+resp[i].sender.toUpperCase()+'</b>: '+resp[i].message+'</p> </div> </div>';
		}
	}
}

window.setInterval(function() {
	populateChatContainer();
}, 5000);

function send(ele) {
    if(ele.keyCode == 13 && document.getElementById("send").value !='') {
		window.stop();
		var requestObj = {
			"message": ele.value,
			"sender": (new URLSearchParams(window.location.search)).get('sender'),
			"receiver": (new URLSearchParams(window.location.search)).get('receiver')
		};
		var xmlhttp = new XMLHttpRequest(); // new HttpRequest
		// instance
		xmlhttp.open("POST", "/v2/chats");
		xmlhttp.setRequestHeader("Content-Type", "application/json");
		xmlhttp.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {
				populateChatContainer();
				document.getElementById("send").value = '';
			}
		};
		xmlhttp.send(JSON.stringify(requestObj));
    }
}