// Traitement des données reçues
function process(evt)
{
	var dataString = evt.data;
	var data = JSON.parse(dataString);
	var command = data.command;
	
	// Erreur connexion utilisateur
	if(command == "error_log")
	{
		console.log("Erreur connexion utilisateur ...");
	}
	
	// Reussite connexion utilisateur
	else if(command == "confirm_log")
	{
		reloadChat(sitePath + "/index.php?script=1&page=connect&user=" + data.login);
	}
	
	// Réception message
	else if(command == "chat_message")
	{
		var messagesList = document.getElementById("chatMessages");
		var d = new Date(data.date);
		var h = d.getHours();
		var m = d.getMinutes();
    
		// Add a zero in front of numbers < 10
		if (h < 10) { h = "0" + h; }
		if (m < 10) { m = "0" + m; }

		messagesList.innerHTML += "<br />" + data.author +  " [" + h + ":" + m + "] : " + data.text;
		messagesList.scrollTop = messagesList.scrollHeight;
	}
	
	// Réussite action
	else if(command == "confirm")
	{
		var type = data.type;
		
		// Déconnexion
		if(type == "log_out")
		{
			reloadChat(sitePath + "/index.php?script=1&page=deconnect");
		}
	}
	
	else
	{
		alert(dataString);
	}
}