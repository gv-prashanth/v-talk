function checkAndSend(event) {
    if (event.keyCode == 13) {
        send();
    }
};

function populateChatContainer() {
	var xmlhttp = new XMLHttpRequest(); // new HttpRequest
	// instance
	xmlhttp.open("POST", "/v2/get/chats");
	xmlhttp.setRequestHeader("Content-Type", "application/json");
	xmlhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var resp = JSON.parse(this.responseText);
			printResp(resp);
		}
	};
	var requestObj = {
		"sender": globalSender,
		"receiver": globalReceiver
	};
	xmlhttp.send(JSON.stringify(requestObj));
}

function printResp(resp) {
	if (resp.length > 0) {
		document.getElementById("catalog").innerHTML = "";
		for (var i = 0; i < resp.length; i++) {
			var styleString = 'style="float: left;"';
			if(globalSender.toLowerCase() == resp[i].sender.toLowerCase())
				styleString = 'style="float: right;background-color: #bbbbbb;"';
			document.getElementById("catalog").innerHTML += '<div class="square" '+styleString+'> <div class="repoDesc"> <p> <b>'+resp[i].sender.toUpperCase()+'</b>: '+resp[i].message+'</p> </div> <div class="repoDesc" style="float:right;"><p>'+timeSince(Date.parse(resp[i].createdOn))+'</p></div> </div>';
		}
	}
}

window.setInterval(function() {
	populateChatContainer();
}, 10000);

function send() {
    if(document.getElementById("send").value !='') {
		document.getElementById("send").disabled = true;
		var requestObj = {
			"message": document.getElementById("send").value,
			"sender": globalSender,
			"receiver": globalReceiver
		};
		var xmlhttp = new XMLHttpRequest(); // new HttpRequest
		// instance
		xmlhttp.open("POST", "/v2/chats");
		xmlhttp.setRequestHeader("Content-Type", "application/json");
		xmlhttp.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {
				window.stop();
				populateChatContainer();
				document.getElementById("send").disabled = false;
				document.getElementById("send").value = '';
			}
		};
		xmlhttp.send(JSON.stringify(requestObj));
    }
}

function timeSince(date) {

  var seconds = Math.floor((new Date() - date) / 1000);

  var interval = seconds / 31536000;

  if (interval > 1) {
    return Math.floor(interval) + " years ago";
  }
  interval = seconds / 2592000;
  if (interval > 1) {
    return Math.floor(interval) + " months ago";
  }
  interval = seconds / 86400;
  if (interval > 1) {
    return Math.floor(interval) + " days ago";
  }
  interval = seconds / 3600;
  if (interval > 1) {
    return Math.floor(interval) + " hours ago";
  }
  interval = seconds / 60;
  if (interval > 1) {
    return Math.floor(interval) + " minutes ago";
  }
  return Math.floor(seconds) + " seconds ago";
}