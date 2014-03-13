// Traitement des données reçues
function process(evt)
{
	var data = JSON.parse(evt.data);
	var command = data.command;
	
	if(command == "log_error")
	{
		console.log("Erreur connexion utilisateur ...");
	}
	else if(command == "log_confirm")
	{
		reloadChat(sitePath + "/index.php?script=1&page=connect&user=" + data.login, null);
	}
	else if(command == "chat_message")
	{
		var messagesList = document.getElementById("chatMessages");
		messagesList.innerHTML += "<br />" + data.author +  /*" [" + data.time + "]" +*/ " : " + data.text;
		messagesList.scrollTop = messagesList.scrollHeight;
	}
	else
	{
		alert(evt.data);
	}
}