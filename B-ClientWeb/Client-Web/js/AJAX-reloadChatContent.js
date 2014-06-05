// Script AJAX
//
// Rechargement partiel de la page (zone chat)
//---------------------------------------

function reloadChat(url)
{
	var xhr = null;
	try //(Firefox, Chrome, Opera, Safari)
	{
		xhr = new XMLHttpRequest();
	}
	catch(e)
	{
		alert("Your browser does not support AJAX");
		return;
	}
	
	xhr.open("GET", url, false);
	
	// Execution au retour
	xhr.onreadystatechange = function()
	{
		if(xhr.readyState == 4 && xhr.status == 200)
		{
			pageChat = document.getElementById("chatBox");
			pageChat.innerHTML = xhr.responseText;
		}
	};
	
	// Paramètres
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	
	// Envoi
	xhr.send();
	
	$('#chatTabs').tabs();
	load();
}