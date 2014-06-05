// Traitement des données reçues
function process(evt)
{
	var dataString = evt.data;
	var data = JSON.parse(dataString);
	var command = data.command;
	
	// Erreur
	if(command == "message_error")
	{
		// Déconnexion
		if(data.type == "logged_out")
		{
			localStorage.myId = '';
			localStorage.myName = '';
			loadPage("home_onlineChoiceView.html");
		}
		
		else if (data.type == "must_be_logged")
		{
			loadPage('home_serverSelectView.html');
		}
		
		else if (data.type == "not_owner_of_this_room")
		{
			alert("Vous n'êtes pas autorisé à modifier la configuration d'une partie dont vous n'êtes pas l'hôte.");
		}
		
		else
		{
			alert(data.type);
		}
	}
	
	// Reussite connexion utilisateur
	else if(command == "log_confirm")
	{
		localStorage.myId = data.user_id;
		localStorage.myName = data.user_name;
		loadPage("chatView.html");
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
		
		// Connexion auto à la création de compte
		if(type == "account_create")
		{
			connexion(false);
		}
	}

	// Si on nous confirme la création d'une partie
	// ATTENTION NOM DE COMMANDE NON DEFINITIVE !!!
	else if(command == "game_confirm")
	{	
		localStorage.save_game_config = dataString;
		localStorage.mode = data.configuration.game_mode;
		
		localStorage.roomId = data.configuration.room_id;
		alert(localStorage.roomId);
		alert(data.configuration);
		
		// Mode de jeu : rapide
		if (data.configuration.game_mode == 'fast')
		{
			/*
			if ( ( data.teams[0].players[0].player_user_id != -1) && (data.teams[1].players[0].player_user_id != -1) 
			&& ( ( data.teams[0].players[0].player_user_id == 0 ) || ( data.teams[1].players[0].player_user_id == 0) ) )
			{
				gameStart(data.room_id);
			}
			*/
		}
		// Mode de jeu : normal
		else if (data.configuration.game_mode == 'normal')
		{	
			// Configuration de partie
			if (localStorage.sitePage != 'gameConfig')
			{
				alert("Partie Normale --> Game_confirm");
				
				/*
				localStorage.sitePage = 'gameConfig';
				//reloadContent(sitePath + "/index.php?script=1&page=gameConfig&mode=" + data.configuration.game_mode + "&name=" + data.configuration.game_name + "&id=" + data.room_id);
				loadPage('home_subscribeView.html');
				*/
			}
		}
		// Mode de jeu incorrect
		else
		{
			alert("Mode de jeu incorrect !");
		}
	}

	// Si on nous envoie un plateau de jeu
	// ATTENTION NOM DE COMMANDE NON DEFINITIVE !!!
	else if(command == "game_turn")
	{
		localStorage.save_game_turn = dataString;
		
		// Qu'on soit n'importe où, ou qu'on recharge la page principale pour arriver en jeu (cas pour un nouveau tour, un début de partie voir une reconnexion)
		// reloadContent(sitePath + "/index.php?script=1&page=game");
		
		// Chargement page jeu ...
		loadPage('gameView.html');
	}
	
	// Si on demande la liste des parties
	else if(command == "list_games")
	{
		$(".game").remove();
		var text = '';
		for (var i = 0 ; i < data.games.length ; i++)
		{
			text += "<li class='game' onclick='gameJoin(" + data.games[i].room_id + ")'>" + data.games[i].game_name + "</li>";
		}
		$("#joinGameView").append(text);
		if (sitePage == 'joinGame')
		{
			gameList("on_update");
		}
	}
	
	// Réception liste des joueurs d'une partie
	else if(command == "game_users")
	{
		var mode = localStorage.mode;		
		
		/*
		
			PARTIE NORMALE
			
		
		if (mode == 'normal')
		{
			// copie du tableau d'utilisateurs précédent (pour identifier les départs de joueurs)
			var save_game_users_prec = new Array();
			for (var usp = 0 ; usp < save_game_users.length ; usp++)
			{
				save_game_users_prec[usp] = save_game_users[usp];
			}
			// copie du nouveau tableau d'utilisateurs
			for (var us = 0 ; us < data.users.length ; us++)
			{
				save_game_users[us] = data.users[us];
			}
			// suppression des anciens joueurs
			while (save_game_users[us])
			{
				us++;
			}
			save_game_users.splice(data.users.length,us);
			var text1 = '';
			var text2 = '<option value="0">ROBOT</option>';
			// parcours des utilisateurs du panneau de config
			for (var i = 0 ; i < save_game_users.length ; i++)
			{
				$.each($("[name='playerName']"),function(key,value)
				{
					// parcours des select des utilisateurs
					if ($(this).children('option').length)
					{
						// utilisateur déjà ajouté
						var userOK = false;
						$.each($($(this).children('option')),function(key,value)
						{
							// parcours des options du select
							if ($(this).html() == save_game_users[i].user_name)
							{
								userOK = true;
								// confirmation du joueur
								for (var j = 0 ; j < save_game_users_prec.length ; j++)
								{
									if (save_game_users[i].user_name == save_game_users_prec[j].user_name)
									{
										save_game_users_prec.splice(save_game_users_prec.indexOf(save_game_users_prec[j]),1);
										break;
									}
								}
							}
						});
						// ajout de l'utilisateur si non trouvé
						if (!userOK)
						{
							if (text1.indexOf("<p class='viewers'>" + save_game_users[i].user_name + "</p>") == -1)
							{
								text1 += "<p class='viewers'>" + save_game_users[i].user_name + "</p>";
							}
							if (text2.indexOf("<option value='" + save_game_users[i].user_id + "'>" + save_game_users[i].user_name + "</option>") == -1)
							{
								text2 += "<option value='" + save_game_users[i].user_id + "'>" + save_game_users[i].user_name + "</option>";
							}
						}
					}
					// si la liste est vide
					else
					{
						text1 += "<p class='viewers'>" + save_game_users[i].user_name + "</p>";
						text2 += "<option value='" + save_game_users[i].user_id + "'>" + save_game_users[i].user_name + "</option>";
						return false;
					}
				});
			}
			// identification d'un départ de joueur
			if (save_game_users_prec.length != 0)
			{
				$("#noTeam").find("p.viewers").remove(":contains('" + save_game_users_prec[0].user_name + "')");
				$("[name='playerName']").find("option").remove(":contains('" + save_game_users_prec[0].user_name + "')");
			}
			if (text1 != '' && text2 != '')
			{
				if ($("#noTeam").length && $("[name='playerName']").length)
				{
					$("#noTeam").append(text1);
					$("[name='playerName']").append(text2);
				}
			}
		}
		
		else 
		
		*/
		
		if (mode == 'fast' && data.users[0] && typeof(localStorage.save_game_config) != "undefined" && localStorage.save_game_config != "")
		{
			var gameDataTemp = JSON.parse(localStorage.save_game_config);
			
			gameDataTemp.command = "GAME_CONFIGURATION";
			gameDataTemp.teams[0].players[0].player_user_id = 9; //data.users[0].user_id;
			gameDataTemp.teams[1].players[0].player_user_id = 0;							// ------------- CECI EST UN BOT !!!!
			gameDataTemp.teams[1].players[0].bot = "pseudoRandom";
			gameDataTemp.game_turn_duration = 	gameDataTemp.configuration.game_turn_duration;
			gameDataTemp.game_mode = 			gameDataTemp.configuration.game_mode;
			gameDataTemp.game_board_size = 		gameDataTemp.configuration.game_board_size;
			gameDataTemp.game_name = 			gameDataTemp.configuration.game_name;
			
			var json = JSON.stringify(gameDataTemp);
			sendText(json);
		}
		/**/
	}
	
	else
	{
		// Partie rapide -> Configuration (Ajout d'un bot)
		alert(dataString);
	}
}