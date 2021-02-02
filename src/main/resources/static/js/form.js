function goToOnlineChat(){
	var f = document.createElement('form');
	f.action='/online';
	f.method='POST';
	
	var i=document.createElement('input');
	i.type='hidden';
	i.name='sender';
	i.value=prompt("What is your nick?");
	
	var j=document.createElement('input');
	j.type='hidden';
	j.name='receiver';
	j.value=prompt("What is your friend's nick?");
	
	f.appendChild(i);
	f.appendChild(j);
	
	document.body.appendChild(f);
	f.submit();
}