// Traitement des données reçues
function process(evt)
{
	var dataString = evt.data;
	var data = JSON.parse(dataString);
	var command = data.command;
	
	// Erreur
	if(command == "message_error")
	{
		switch (data.type)
		{
			case 'already_in_this_room':
				alert("Vous avez déjà rejoint cette partie.");
				break;
			case 'inexistant_room':
				alert("Cette salle n'existe pas.");
				break;
			case 'json_error':
				alert("Paramètres de commande incorrects.");
				break;
			case 'log_error':
				alert("Erreur d'identification.");
				break;
			case 'logged_out':
				myId = '';
				myName = '';
				loadPage("home_connectionView.html");
				break;
			case 'must_be_logged':
				loadPage("home_connectionView.html");
				break;
			case 'not_owner_of_this_room':
				alert("Vous n'êtes pas autorisé à modifier la configuration d'une partie dont vous n'êtes pas l'hôte.");
				break;
			case 'rule_error':
				alert("Ce coup n'est pas autorisé.");
				break;
			case 'server_error':
				alert("Erreur serveur.");
				break;
			case 'unknown_command':
				alert("Cette commande n'existe pas.");
				break;
			default:
				alert(data.type);
				break;
		}
	}
	
	// Reussite connexion utilisateur
	else if(command == "log_confirm")
	{
		localStorage.myId = data.user_id;
		localStorage.myName = data.user_name;
		loadPage("lobbyView.html");
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
		localStorage.roomId = data.room_id;
		localStorage.gameName = data.configuration.game_name;
		
		// Mode de jeu : rapide
		if (data.configuration.game_mode == 'fast')
		{
			try
			{
				// Vérification joueurs
				if ( ( data.teams[0].players[0].player_user_id != -1) && (data.teams[1].players[0].player_user_id != -1) 
				
				// Vérification présence bot
				&& ( ( data.teams[0].players[0].player_user_id == 0 ) 					|| ( data.teams[1].players[0].player_user_id == 0) ) 
				
				// Vérification présence joueur
				&& ( ( data.teams[0].players[0].player_user_id == localStorage.myId ) 	|| ( data.teams[1].players[0].player_user_id == localStorage.myId) )
				
				// Vérification bot configuré
				&& ( ( typeof(data.teams[0].players[0].bot) != "undefined" ) 			|| ( typeof(data.teams[1].players[0].player_user_id) != "undefined") ) )
				
				// Si partie rapide prete -> lancement
				{
					sendText('{"command":"GAME_START","room_id": ' + localStorage.roomId + '}');
				}
				
				// Sinon -> configuration
				else
				{
					sendText('{"command": "GAME_CONFIGURATION","room_id": ' + localStorage.roomId + ',"game_name": "' + localStorage.gameName + '","game_mode": "fast","game_board_size": 5,"game_turn_duration": 30000,"teams":[{"players":[{"player_user_id":' + localStorage.myId + ',"legions":[{"legion_shape": "square","legion_color": "#0000FF","legion_position": "5"},{"legion_shape": "circle","legion_color": "#0000FF","legion_position": "1"},{"legion_shape": "triangle","legion_color": "#0000FF","legion_position": "3"}]}]},{"players":[{"player_user_id":0,"bot":"pseudoRandom","legions":[{"legion_shape": "square","legion_color": "#FF0000","legion_position": "2"},{"legion_shape": "circle","legion_color": "#FF0000","legion_position": "4"},{"legion_shape": "triangle","legion_color": "#FF0000","legion_position": "0"}]}]}]}');
				}
			}
			
			// Si erreur -> configuration
			catch(err)
			{
				sendText('{"command": "GAME_CONFIGURATION","room_id": ' + localStorage.roomId + ',"game_name": "' + localStorage.gameName + '","game_mode": "fast","game_board_size": 5,"game_turn_duration": 30000,"teams":[{"players":[{"player_user_id":' + localStorage.myId + ',"legions":[{"legion_shape": "square","legion_color": "#0000FF","legion_position": "5"},{"legion_shape": "circle","legion_color": "#0000FF","legion_position": "1"},{"legion_shape": "triangle","legion_color": "#0000FF","legion_position": "3"}]}]},{"players":[{"player_user_id":0,"bot":"pseudoRandom","legions":[{"legion_shape": "square","legion_color": "#FF0000","legion_position": "2"},{"legion_shape": "circle","legion_color": "#FF0000","legion_position": "4"},{"legion_shape": "triangle","legion_color": "#FF0000","legion_position": "0"}]}]}]}');
			}			
		}
		// Mode de jeu : normal
		else if (data.configuration.game_mode == 'normal')
		{	
			if(typeof(localStorage.sgu) != "undefined")
			{
				var save_game_users = JSON.parse(localStorage.sgu);
				var owner = false;

				// Parcours des utilisateurs
				for (i = 0; i < save_game_users.length; i++)
				{
					if (save_game_users[i].user_id == localStorage.myId && save_game_users[i].is_owner)
					{
						owner = true;
					}
				}
				
				// Si owner
				if (owner)
				{
					if(localStorage.sitePage != 'gameConfig')
					{
						localStorage.sitePage = 'gameConfig';
						loadPage('gameConfigView.html');
					}
				}
				
				// Si pas owner
				else
				{
					if (localStorage.sitePage != 'gameConfigViewer')
					{
						sitePage = 'gameConfigViewer';
						loadPage('gameConfigViewer.html');
					}
					else
					{
						gameConfigViewer();
					}
				}
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
		var save_game_config = JSON.parse(localStorage.save_game_config);
		
		// Chargement page jeu ...
		loadPage('gameView.html');
				
		// Gestion timer
		clearInterval(localStorage.inter);
		
		if (data.winner_team && data.winner_legion)
		{
			if (data.winner_team == -1)
			{
				alert("Match nul.");
			}
			else
			{
				var nomteam = '';
				if (data.winner_legion == -1)
				{
					for (var i = 0; i < save_game_users; i++)
					{
						if (save_game_users[i].user_id == data.winner_team)
						{
							nomteam += save_game_users[i].user_name+', ';
						}
					}
					alert("Il n'y a plus d'adversaires. La team de " + nomteam + "a gagné.");
				}
				else
				{
					for (var i = 0; i < save_game_users; i++)
					{
						if (save_game_users[i].user_id == data.winner_team)
						{
							nomteam += save_game_users[i].user_name+', ';
						}
					}
					alert("Le laurier est dans une tente. La team de " + nomteam + "a gagné.");
				}
			}
		}
		else
		{
			var timer = save_game_config.configuration.game_turn_duration/1000;
			$("#timer").val(timer/2);
			$("#timer").attr('max',timer);
			
			localStorage.inter = setInterval(function()
			{
				if (timer != 0)
				{
					timer--;
					$("#timer").val(timer);
				}
			},1000);
		}
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
		
		if (localStorage.sitePage == 'joinGame')
		{
			gameList("on_update");
		}
	}
	
	// Réception liste des joueurs d'une partie
	else if(command == "game_users")
	{
		var mode = localStorage.mode;		
		var save_game_users;
		
		// Récupération liste users
		if( typeof(localStorage.sgu) != 'undefined' )
		{
			save_game_users = JSON.parse(localStorage.sgu);
		}
		else
		{
			save_game_users = [];
		}
		
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
			
			save_game_users.splice(data.users.length, us);
			
			var text1 = '';
			var text2 = '';
			
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
			if (save_game_users_prec.length > 0)
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
		
		localStorage.sgu = JSON.stringify(save_game_users);
	}
	
	// Commande non-traitée
	else
	{
		// Partie rapide -> Configuration (Ajout d'un bot)
		alert("Commande non-traitée :\n" + dataString);
	}
}