// Traitement des données reçues
function process(evt)
{
	var dataString = evt.data;
	var data = JSON.parse(dataString);
	var command = data.command;
	
	// Erreur connexion utilisateur
	if(command == "message_error")
	{
		console.log("Erreur connexion utilisateur ...");
		alert(data.type);
	}
	
	// Reussite connexion utilisateur
	else if(command == "log_confirm")
	{
		reloadChat(sitePath + "/index.php?script=1&page=connect&user=" + data.user_name);
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
		messagesList.innerHTML += "<br />" + data.user_name +  " [" + h + ":" + m + "] : " + message;
		messagesList.scrollTop = messagesList.scrollHeight;
	}
	
	// Réussite action
	else if(command == "message_confirm")
	{
		var type = data.type;
		
		// Déconnexion
		if(type == "log_out")
		{
			reloadChat(sitePath + "/index.php?script=1&page=deconnect");
		}
		// Connexion auto à la création de compte
		else if(type == "account_create")
		{
			connexion(true,'','');
			reloadContent(sitePath + "/index.php?script=1&page=news");
		}
	}

	// Si on nous confirme la création d'une partie
	// ATTENTION NOM DE COMMANDE NON DEFINITIVE !!!
	else if(command == "game_confirm")
	{
		reloadContent(sitePath + "/index.php?script=1&page=gameConfig&name=" + data.configuration.game_name + "&id=" + data.room_id);
	}

	// Si on nous envoie un plateau de jeu
	// ATTENTION NOM DE COMMANDE NON DEFINITIVE !!!
	else if(command == "game_turn")
	{
		// Qu'on soit n'importe où, ou qu'on recharge la page principale pour arriver en jeu (cas pour un nouveau tour, un début de partie voir une reconnexion)
		reloadContent(sitePath + "/index.php?script=1&page=game");
	}
	
	// Si on demande la liste des parties
	else if(command == "list_games")
	{
		$(".game").remove();
		for (var i = 0 ; i < data.games.length ; i++)
		{
			$("#joinGameView").append("<li class='game' onclick='gameJoin(" + data.games[i].room_id + ")'>" + data.games[i].game_name + "</li>");
		}
		if (sitePage == 'joinGame')
		{
			gameList("on_update");
		}
	}
	
	// Réception liste des joueurs d'une partie
	else if(command == "game_users")
	{
		$("#noTeam").html('');
		var pViewers = $("<p>Spectateurs</p>");
		$("#noTeam").append(pViewers);
		for (var i = 0 ; i < data.users.length ; i++)
		{
			$("#noTeam").append("<span class='viewers'>" + data.users[i].user_name + "</span><br>");
			$("[name='playerName']").append("<option value='" + data.users[i].user_id + "'>" + data.users[i].user_name + "</option>");
		}
	}
	
	else
	{
		alert(dataString);
	}
}