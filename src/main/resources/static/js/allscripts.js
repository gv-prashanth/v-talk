function encodeAndUploadFile() {
	var file = document.querySelector('input[type=file]')['files'][0];
	EXIF.getData(file, function() {
		myData = loadEXIFData(this);
		var reader = new FileReader();
		var baseString;
		reader.onloadend = function() {
			baseString = reader.result;
			console.log(baseString);
			sendImage(baseString, myData);
		};
		reader.readAsDataURL(file);
	});
}

function loadEXIFData(myData) {
	try {
		// Calculate latitude decimal
		var latDegree = myData.exifdata.GPSLatitude[0].numerator;
		var latMinute = myData.exifdata.GPSLatitude[1].numerator;
		var latSecond = myData.exifdata.GPSLatitude[2].numerator;
		var latDirection = myData.exifdata.GPSLatitudeRef;
		var latFinal = ConvertDMSToDD(latDegree, latMinute, latSecond, latDirection);
		myData.exifdata.latFinal = latFinal;

		// Calculate longitude decimal
		var lonDegree = myData.exifdata.GPSLongitude[0].numerator;
		var lonMinute = myData.exifdata.GPSLongitude[1].numerator;
		var lonSecond = myData.exifdata.GPSLongitude[2].numerator;
		var lonDirection = myData.exifdata.GPSLongitudeRef;
		var lonFinal = ConvertDMSToDD(lonDegree, lonMinute, lonSecond, lonDirection);
		myData.exifdata.lonFinal = lonFinal;

		// Construct geo Url in google maps
		var geoFinal = 'http://www.google.com/maps/place/' + myData.exifdata.latFinal + ',' + myData.exifdata.lonFinal;
		myData.exifdata.geoFinal = geoFinal;
	} catch (err) {

	}

	// Log all info
	console.log(myData);

	// Return updated object
	return myData;
}

function ConvertDMSToDD(degrees, minutes, seconds, direction) {
	var dd = degrees + (minutes / 60) + (seconds / 3600);
	if (direction == "S" || direction == "W") {
		dd = dd * -1;
	}
	return dd;
}

function sendImage(imageBase64, myData) {
	if (imageBase64 != '') {
		document.getElementById("send").disabled = true;
		var requestObj = {
			"message": imageBase64,
			"sender": globalSender,
			"receiver": globalReceiver,
			"messageMeta": JSON.stringify(myData)
		};
		var xmlhttp = new XMLHttpRequest(); // new HttpRequest
		// instance
		xmlhttp.open("POST", "/v2/chats");
		xmlhttp.setRequestHeader("Content-Type", "application/json");
		xmlhttp.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {
				window.stop();
				document.getElementById("send").disabled = false;
				populateChatContainer();
			}
		};
		xmlhttp.send(JSON.stringify(requestObj));
	}
}

function checkAndSend(event) {
	if (event.keyCode == 13) {
		send();
	}
};

function populateChatContainer() {
	var xmlhttp = new XMLHttpRequest(); // new HttpRequest
	// instance
	if(globalLastPullChatId == null)
		xmlhttp.open("POST", "/v2/get/chats");
	else
		xmlhttp.open("POST", "/v2/get/chats?lastPullChatId="+globalLastPullChatId);
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
		//document.getElementById("catalog").innerHTML = "";
		if(document.getElementById("extraSpaceBelow")!=null)
			document.getElementById("extraSpaceBelow").remove();
		for (var i = 0; i < resp.length; i++) {
			if(resp[i].id <= globalLastPullChatId)
				continue;
			var styleString = 'style="float: left;"';
			if (globalSender.toLowerCase() == resp[i].sender.toLowerCase())
				styleString = 'style="float: right;background-color: #bbbbbb;"';
			if (resp[i].message.includes("base64"))
				document.getElementById("catalog").innerHTML += '<div class="square" ' + styleString + '> <div class="repoDesc"> <p> <b>' + resp[i].sender.toUpperCase() + '</b>: <img src=' + resp[i].message + ' style="width:100%;"> </p> </div> <div style="float:right;"><p>' + timeSince(Date.parse(resp[i].createdOn)) + '</p></div> </div>';
			else{
				constructedString = '<div class="square" ' + styleString + '> <div class="repoDesc"> <p>' + postProcess(resp[i].message, resp[i].sender.toUpperCase()) + '</p> </div> <div style="float:right;"><p>' + timeSince(Date.parse(resp[i].createdOn));
				if (globalSender.toLowerCase() != resp[i].sender.toLowerCase())
					constructedString += '&nbsp;&nbsp;&nbsp;<img width="25" onClick="replyTo(\''+resp[i].sender.toUpperCase()+'\', \''+encodeURIComponent(resp[i].message)+'\')" src= "/img/reply2.png"/>';
				constructedString += '</p></div> </div>';
				document.getElementById("catalog").innerHTML += constructedString;
			}
			globalLastPullChatId = resp[i].id;
		}
		document.getElementById("catalog").innerHTML += '<div class="square" style="float: left;background-color: white" id="extraSpaceBelow"> <div class="repoDesc"> <p> <b> </b> <br> </p> </div> <div style="float:right;"><p></p></div> </div>';
	}
}

function replyTo(usr, mssg){
	mssg = decodeURIComponent(mssg);
	const regex = /\|\|.*\|\|->/i;
	mssg = mssg.replace(regex, '');
	globalReply = '|| '+usr+': '+mssg+' ||-> ';
	document.getElementById("search-container-id").innerHTML = '<div id = "needToRemoveLater" class="square" style="float: left;color: black;font-weight: normal;"> <div class="repoDesc"> <p>'+usr+': '+mssg+'</p> </div> </div>' + document.getElementById("search-container-id").innerHTML;
	document.getElementById("send").focus();
	//document.getElementById("send").value = '|| '+usr+': '+mssg+' ||-> ';
}

function postProcess(mssg, sender){
	if(mssg.includes(' ||-> ')){
		mssg = mssg.replace('|| ', '<span style=\'background-color:white;border-radius: 50px;padding: 2px 10px 2px 10px;\'>');
		mssg = mssg.replace(' ||-> ', '</span><br><br>'+'<b>' + sender + '</b>: ');
	}else{
		mssg = '<b>' + sender + '</b>: ' + mssg;
	}
	return mssg;
}

window.setInterval(function() {
	populateChatContainer();
}, 3000);

function send() {
	if (document.getElementById("send").value != '') {
		document.getElementById("send").disabled = true;
		var requestObj = {
			"message": globalReply+document.getElementById("send").value,
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
				document.getElementById("send").focus();
				globalReply = '';
				if(document.getElementById("needToRemoveLater")!=null)
					document.getElementById("needToRemoveLater").remove();
			}
		};
		xmlhttp.send(JSON.stringify(requestObj));
	}
}

function login() {
	var requestObj = {
		"geoInfo": "TODO",
		"sender": globalSender,
		"receiver": globalReceiver
	};
	var xmlhttp = new XMLHttpRequest(); // new HttpRequest
	// instance
	xmlhttp.open("POST", "/v2/login");
	xmlhttp.setRequestHeader("Content-Type", "application/json");
	xmlhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			console.log("login succesful");
		}
	};
	xmlhttp.send(JSON.stringify(requestObj));
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
	//return Math.floor(seconds) + "< minute ago";
	return "< a minute ago";
}

document.addEventListener("visibilitychange", event => {
	if (document.visibilityState == "visible") {
		console.log("tab is activate")
	} else {
		console.log("tab is inactive");
		window.location.href = "https://www.google.com/search?tbm=isch&q=ibmwebsphere";
	}
})