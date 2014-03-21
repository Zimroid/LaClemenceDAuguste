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
	else if(command == "log_confirm")
	{
		reloadChat(sitePath + "/index.php?script=1&page=connect&user=" + data.name);
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

		message = data.text.replace("\n","<br>");
		messagesList.innerHTML += "<br />" + data.author +  " [" + h + ":" + m + "] : " + message;
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

	// Si on nous confirme la création d'une partie
	// ATTENTION NOM DE COMMANDE NON DEFINITIVE !!!
	else if(command == "game_confirm")
	{
		reloadContent(sitePath + "/index.php?script=1&page=gameConfig");
	}

	// Si on nous envois un plateau de jeu
	// ATTENTION NOM DE COMMANDE NON DEFINITIVE !!!
	else if(command == "game_turn")
	{
		// Qu'on soit n'importe ou on recharge la page principal pour arriver en jeu (cas pour un nouveau tour, un début de partie voir une reconnexion)
		reloadContent(sitePath + "/index.php?script=1&page=game");
	}

	
	else
	{
		alert(dataString);
	}
}