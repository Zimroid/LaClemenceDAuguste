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
		alert("connexion réussie : rechargé le chat");
		//reloadChat();
	}
	else if(command == "chat_message")
	{
		var messagesList = document.getElementById("chatMessages");
		messagesList.innerHTML += "<br />" + data.author +  " [" + data.time + "] : " + data.text;
		messagesList.scrollTop = messagesList.scrollHeight;
	}
	else
	{
		alert(evt.data);
	}
}