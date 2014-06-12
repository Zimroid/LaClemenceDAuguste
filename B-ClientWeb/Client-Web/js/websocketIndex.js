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
				reloadChat(sitePath + "/index.php?script=1&page=deconnect");
				reloadContent(sitePath + "/index.php?script=1&page=news");
				break;
			case 'must_be_logged':
				reloadContent(sitePath + "/index.php?script=1&page=subscribe");
				break;
			case 'name_unavailable':
				alert("Ce nom est déjà pris.");
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
		myId = data.user_id;
		myName = data.user_name;
		reloadChat(sitePath + "/index.php?script=1&page=connect&user=" + data.user_name);
		reloadContent(sitePath + "/index.php?script=1&page=news");
	}
	
	// Réception message
	else if(command == "chat_message")
	{
		if (typeof(data.room_id) != 'undefined' && data.room_id != 0)
		{
			var messagesList = document.getElementById("chatMessagesGame");
		}
		else
		{
			var messagesList = document.getElementById("chatMessages");
		}
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
			connexion(true,'','');
			reloadContent(sitePath + "/index.php?script=1&page=news");
		}
		else if(type == "room_left")
		{
			reloadContent(sitePath + '/index.php?script=1&page=news');
		}
	}

	// Si on nous confirme la création d'une partie
	// ATTENTION NOM DE COMMANDE NON DEFINITIVE !!!
	else if(command == "game_confirm")
	{
		save_game_config = data;
		// si une partie rapide est lancée
		if (data.configuration.game_mode == 'fast' && (data.teams[0].players[0].player_user_id != 0) && (data.teams[1].players[0].player_user_id != 0))
		{
			gameStart(data.room_id);
		}
		// si une partie normale est lancée
		else if (sitePage != 'gameConfig' && sitePage != 'gameConfigViewer')
		{
			var lc = JSON.parse(last_command);
			if (lc.command == "ROOM_CREATE")
			{
				sitePage = 'gameConfig';
				reloadContent(sitePath + "/index.php?script=1&page=gameConfig&mode=" + data.configuration.game_mode + "&name=" + data.configuration.game_name + "&id=" + data.room_id);
				gameConfig();
			}
			else if (lc.command == "ROOM_JOIN")
			{
				sitePage = 'gameConfigViewer';
				reloadContent(sitePath + "/index.php?script=1&page=gameConfigViewer&mode=" + data.configuration.game_mode + "&name=" + data.configuration.game_name + "&id=" + data.room_id);
				gameConfigViewer();
			}
		}
		else if(sitePage == 'gameConfigViewer')
		{
			var owner = false;
		
			for (i = 0; i < save_game_users.length; i++)
			{
				if (save_game_users[i].user_id == myId && save_game_users[i].is_owner)
				{
					owner = true;
				}
			}
		
			if (!owner)
			{
				gameConfigViewer();
			}
			else
			{
				sitePage = 'gameConfig';
				reloadContent(sitePath + "/index.php?script=1&page=gameConfig&mode=" + data.configuration.game_mode + "&name=" + data.configuration.game_name + "&id=" + data.room_id);
			}
		}
	}

	// Si on nous envoie un plateau de jeu
	// ATTENTION NOM DE COMMANDE NON DEFINITIVE !!!
	else if(command == "game_turn")
	{
		save_game_turn = data;
		// Qu'on soit n'importe où, ou qu'on recharge la page principale pour arriver en jeu (cas pour un nouveau tour, un début de partie voir une reconnexion)
		reloadContent(sitePath + "/index.php?script=1&page=game");
		clearInterval(inter);
		if ((typeof(data.winner_team) != "undefined") && (typeof(data.winner_legion) != "undefined"))
		{
			var messagewin = '';
			if (data.winner_team == -1)
			{
				messagewin = "Match nul.";
			}
			else
			{
				var nomteam = ''; // noms des joueurs de la team gagnante
				if (data.winner_legion == -1)
				{
					var tabwinners = new Array();
					for (var i = 0; i < save_game_config.teams[data.winner_team].players.length; i++)
					{
						tabwinners.push(save_game_config.teams[data.winner_team].players[i].player_user_id);
					}
					for (var i = 0; i < save_game_users.length; i++)
					{
						for (var j = 0; j < tabwinners.length; j++)
						{
							if (save_game_users[i].user_id == tabwinners[j])
							{
								if ((j+1) == tabwinners.length)
								{
									nomteam += save_game_users[i].user_name+' ';
								}
								else if ((j+2) == tabwinners.length)
								{
									nomteam += save_game_users[i].user_name+' et ';
								}
								else
								{
									nomteam += save_game_users[i].user_name+', ';
								}
							}
						}
					}
					if (nomteam != '')
					{
						messagewin = "Il n'y a plus d'adversaires. La team de " + nomteam + "a gagné.";
					}
					else
					{
						messagewin = "Il n'y a plus d'adversaires. Une team de bots a gagné.";
					}
				}
				else
				{
					var tabwinners = new Array();
					for (var i = 0; i < save_game_config.teams[data.winner_team].players.length; i++)
					{
						tabwinners.push(save_game_config.teams[data.winner_team].players[i].player_user_id);
					}
					for (var i = 0; i < save_game_users.length; i++)
					{
						for (var j = 0; j < tabwinners.length; j++)
						{
							if (save_game_users[i].user_id == tabwinners[j])
							{
								if ((j+1) == tabwinners.length)
								{
									nomteam += save_game_users[i].user_name+' ';
								}
								else if ((j+2) == tabwinners.length)
								{
									nomteam += save_game_users[i].user_name+' et ';
								}
								else
								{
									nomteam += save_game_users[i].user_name+', ';
								}
							}
						}
					}
					if (nomteam != '')
					{
						messagewin = "Le laurier est dans une tente. La team de " + nomteam + "a gagné.";
					}
					else
					{
						messagewin = "Le laurier est dans une tente. Une team de bots a gagné.";
					}
				}
			}
			alert(messagewin);
		}
		else
		{
			var timer = save_game_config.configuration.game_turn_duration/1000;
			$("#timer").val(timer);
			$("#timer").attr('max',timer);
			inter = setInterval(function()
			{
				if (timer != 0)
				{
					$("#timer").val(timer-1);
					timer--;
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
		if (sitePage == 'joinGame')
		{
			gameList("on_update");
		}
	}
	
	// Réception liste des joueurs d'une partie
	else if(command == "game_users")
	{
		var mode = $("#type_game").val();
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

			var owner = false;
			for (i = 0; i < save_game_users.length; i++)
			{
				if (save_game_users[i].user_id == myId && save_game_users[i].is_owner)
				{
					owner = true;
				}
			}
			console.log(sitePage);
			if (!owner && sitePage == 'gameConfigViewer')
			{
				gameConfigViewer();
			}
		}
		else if (mode == 'fast' && data.users[1] && myName == data.users[0].user_name)
		{
			save_game_config.command = "GAME_CONFIGURATION";
			save_game_config.teams[0].players[0].player_user_id = data.users[0].user_id;
			save_game_config.teams[1].players[0].player_user_id = data.users[1].user_id;
			save_game_config.game_turn_duration = save_game_config.configuration.game_turn_duration;
			save_game_config.game_mode = save_game_config.configuration.game_mode;
			save_game_config.game_board_size = save_game_config.configuration.game_board_size;
			save_game_config.game_name = save_game_config.configuration.game_name;
			var json = JSON.stringify(save_game_config);
			sendText(json);
		}
		
	}
	
	else
	{
		alert(dataString);
	}
}