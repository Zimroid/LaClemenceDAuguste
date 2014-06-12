function gameCreate(arg)
{
	var gameName = htmlspecialchars($("#gameName").val());

	if(gameName != "")
    {
    	if (arg == 'defaut')
    	{
	        var type = "fast";
        }
        else
    	{
	        var type = "normal";
        }
		var json = JSON.stringify(
		{
	        "command": "ROOM_CREATE",
	        "game_name": gameName,
	        "game_type": type
	    });
        $("#message").val("");
        last_command = json;
        sendText(json);
        $('#chatMessagesGame').html("Chat de la partie " + gameName + "<br>Pour participer à de meilleures conversations, merci de rester courtois et d'éviter le langage SMS.");
    }
}

function gameStart(room_id)
{
	var json = JSON.stringify(
	{
        "command": "GAME_START",
        "room_id": room_id
        
    });
	sitePage = 'gameStart';
	last_command = json;
    sendText(json);
}

function gameConfig()
{
	// String à changer en JSON
	var stringJSON = '';
    // paramètres globaux de la partie
    var roomId = '';
    var gameName = '';
    var boardSize = 5;
    var turnDuration = 30;
    if ((typeof htmlspecialchars($("#room_id").val()) != 'undefined') && (htmlspecialchars($("#room_id").val()) != ''))
    {
    	roomId = htmlspecialchars($("#room_id").val());
    }
    if ((typeof htmlspecialchars($("#game_name").html()) != 'undefined') && (htmlspecialchars($("#game_name").html()) != ''))
    {
    	gameName = htmlspecialchars($("#game_name").html());
    }
    if ((typeof htmlspecialchars($("#board_size").val()) != 'undefined') && (htmlspecialchars($("#board_size").val()) != ''))
    {
    	boardSize = htmlspecialchars($("#board_size").val());
    }
	if ((typeof htmlspecialchars($("#turn_duration").val()) != 'undefined') && (htmlspecialchars($("#turn_duration").val()) != ''))
    {
    	turnDuration = htmlspecialchars($("#turn_duration").val());
    }
    turnDuration = turnDuration * 1000;
    
    stringJSON = '{"command": "GAME_CONFIGURATION","room_id": ' + roomId + ',"game_name": "' + gameName + '","game_mode": "normal","game_board_size": ' + boardSize + ',"game_turn_duration": ' + turnDuration + ',"teams":[';
    
	// numéro de la team courante
	var nbrTeam = 0;
	// numéro du joueur courant
	var nbrPlayer = 0;
	// nombre de légions contrôlées par un joueur
	var nbrLegion = 0;
	// parcours des teams
	for (nbrTeam = 1 ; nbrTeam <= $('[name="team"]').length ; nbrTeam++)
	{
		stringJSON += '{"players":[';
		// parcours des joueurs
		for (nbrPlayer = 1 ; nbrPlayer <= $('[name="player"]').length ; nbrPlayer++)
		{
			// si le joueur existe
			if ($('#player' + nbrTeam + '_' + nbrPlayer).length == 1)
			{
				// si un nom de joueur est détecté
				if ($('#player' + nbrTeam + '_' + nbrPlayer).children("[name='playerName']").val() != null)
				{
					// id du joueur
					if (parseInt($('#player' + nbrTeam + '_' + nbrPlayer).children("[name='playerName']").val(),10) == 0)
					{
						stringJSON += '{"player_user_id":0,"bot":"distributed","legions":[';
					}
					else
					{
						stringJSON += '{"player_user_id":' + parseInt($('#player' + nbrTeam + '_' + nbrPlayer).children("[name='playerName']").val(),10) + ',"legions":[';
					}
					// nombre de légions du joueur
					nbrLegion = $('#player' + nbrTeam + '_' + nbrPlayer).children("[name='legion']").length;
					for (var i = 0 ; i < nbrLegion ; i++)
					{
						// forme des pions du joueur
						stringJSON += '{"legion_shape": "' + $('#player' + nbrTeam + '_' + nbrPlayer).children('#legion' + nbrTeam + '_' + nbrPlayer + '_' + (i + 1)).children(".pawn").val() + '",';
						// couleur des pions du joueur
						stringJSON += '"legion_color": "' + $('#player' + nbrTeam + '_' + nbrPlayer).parent().children(".color").val() + '",';
						// position sur le plateau du joueur
						stringJSON += '"legion_position": "' + $('#player' + nbrTeam + '_' + nbrPlayer).children('#legion' + nbrTeam + '_' + nbrPlayer + '_' + (i + 1)).children(".position").val() + '"}';
						if ((i + 1) < nbrLegion)
						{
							stringJSON += ',';
						}
					}
					stringJSON += ']}';
				}
				else
				{
					stringJSON += '{"player_user_id": -1,"legions": []}';
				}
				if ((nbrPlayer + 1) <= $('#team' + nbrTeam).children('[name="player"]').length)
				{
					stringJSON += ',';
				}
			}
			/*// il n'y a plus de joueurs dans cette team
			else
			{
				if ((nbrPlayer + 1) <= $('#player' + nbrTeam + '_' + nbrPlayer).children('[name="player"]').length)
				{
					stringJSON += ',';
				}
				break;
			}*/
			else if ((nbrPlayer + 1) <= $('#player' + nbrTeam + '_' + nbrPlayer).children('[name="player"]').length)
			{
				stringJSON += ',';
				break;
			}
		}
		stringJSON += ']}';
		if ((nbrTeam + 1) <= $('[name="team"]').length)
		{
			stringJSON += ',';
		}
		// on remet le compte de joueurs à 0 pour la prochaine team
		nbrPlayer = 0;
	}
	stringJSON += ']}';
   
    // envoi de la config
    // alert(stringJSON);
	var json = stringJSON;
    last_command = json;
    sendText(json);
}

function gameConfigViewer()
{
    var nbrTeamsConfig;
    var nbrPlayersConfig = new Array();
    var nbrLegionsConfig = new Array();
    var k = 0;
    nbrTeamsConfig = save_game_config.teams.length;

    for(i = 0; i < save_game_config.teams.length; i++)
    {
        nbrPlayersConfig[i] = save_game_config.teams[i].players.length;
        for(j = 0; j < nbrPlayersConfig[i]; j++)
        {
            nbrLegionsConfig[k] = save_game_config.teams[i].players[j].legions.length;
            k++;
        }
    }

    var nbrTeamsWeb = $('div [name="team"]').length;
    var nbrPlayersWeb = new Array();
    var nbrLegionsWeb = new Array();
    k = 0;

    for(i = 0; i < nbrTeamsWeb; i++)
    {
        nbrPlayersWeb[i] = $('#team' + (i + 1)).children('div [name="player"]').length;
        for(j = 0; j < nbrPlayersWeb[i]; j++)
        {
            nbrLegionsWeb[k] = $('#team' + (i + 1)).children('#player' + (i + 1) + '_' + (j + 1)).children('div [name="legion"]').length;
            k++;
        }
    }

    $('#board_size').val(save_game_config.configuration.game_board_size);
    $('#turn_duration').val((save_game_config.configuration.game_turn_duration/1000));
    var couleur;
    var forme;
    var position;

    if(nbrTeamsWeb == nbrTeamsConfig)
    {
        for(i = 0; i < nbrTeamsWeb; i++)
        {
            if(nbrPlayersWeb[i] == nbrPlayersConfig[i])
            {
                for(j = 0; j < nbrPlayersWeb[i]; j++)
                {
                    if(nbrLegionsWeb[j] == nbrLegionsConfig[j])
                    {
                        for(k = 0; k < nbrLegionsWeb[j]; k++)
                        {
                            switch(save_game_config.teams[i].players[j].legions[k].legion_color)
                            {
                                case "#FF0000" :
                                    couleur = "Rouge";
                                    break;
                                case "#FFFF00" :
                                    couleur = "Jaune";
                                    break;
                                case "#00FF00" :
                                    couleur = "Vert";
                                    break;
                                case "#00FFFF" :
                                    couleur = "Cyan";
                                    break;
                                case "#0000FF" :
                                    couleur = "Bleu";
                                    break;
                                case "FF00FF" :
                                    couleur = "Magenta";
                                    break;
                            }

                            switch(save_game_config.teams[i].players[j].legions[k].legion_shape)
                            {
                                case "square" :
                                    forme = "Carré";
                                    break;
                                case "circle" :
                                    forme = "Hexagone";
                                    break;
                                case "triangle" :
                                    forme = "Triangle";
                                    break;
                            }

                            switch(save_game_config.teams[i].players[j].legions[k].legion_position)
                            {
                                case 0 :
                                    position = "Haut Gauche";
                                    break;
                                case 1 :
                                    position = "Haut Droit";
                                    break;
                                case 2 :
                                    position = "Droit";
                                    break;
                                case 3 :
                                    position = "Bas Droit";
                                    break;
                                case 4 :
                                    position = "Bas Gauche";
                                    break;
                                case 5 :
                                    position = "Gauche";
                                    break;
                            }

                            $('#team' + (i + 1)).children('input.color').val(couleur);
                            $('#team' + (i + 1)).children('#player' + (i + 1) + '_' + (j + 1)).children('#legion' + (i + 1) + '_' + (j + 1) + '_' + (k + 1)).children('input.pawn').val(forme);
                            $('#team' + (i + 1)).children('#player' + (i + 1) + '_' + (j + 1)).children('#legion' + (i + 1) + '_' + (j + 1) + '_' + (k + 1)).children('input.position').val(position);
                        }
                    }

                    if(save_game_config.teams[i].players[j].player_user_id == 0)
                    {
                        $('#team' + (i + 1)).children('#player' + (i + 1) + '_' + (j + 1)).children('input.playerName').val("ROBOT Pseudo-Random");
                    }
                    else
                    {
                        var nom;
                        for(z = 0; z < save_game_users.length; z++)
                        {
                            if(save_game_users[z].user_id == save_game_config.teams[i].players[j].player_user_id)
                            {
                                nom = save_game_users[z].user_name;
                            }
                        }
                        $('#team' + (i + 1)).children('#player' + (i + 1) + '_' + (j + 1)).children('input.playerName').val(nom);
                    }
                }
            }
        }
    }
}

function gameList(argument)
{
    if (argument == 'on_update')
    {
        var json = JSON.stringify(
        {
            "command": "QUERY_ROOMS",
            "on_update": "true"
        });
    }
    else
    {
        $(".game").remove();
        var json = JSON.stringify(
        {
            "command": "QUERY_ROOMS"
        });
    }
    last_command = json;
    sendText(json);
}

function gameJoin(game)
{
	var json = JSON.stringify(
    {
        "command": "ROOM_JOIN",
        "room_id": game
    });
    last_command = json;
    sendText(json);
}

function gameTurnFinish(game)
{
	var json = JSON.stringify(
    {
        "command": "FINISH_TURN",
        "room_id": game
    });
    last_command = json;
    sendText(json);
}

function gameLeave(game)
{
	var json = JSON.stringify(
    {
        "command": "ROOM_LEAVE",
        "room_id": game
    });
    last_command = json;
    sendText(json);
}

function newTeam()
{
    //div contenant toutes les team (sans les observateurs)
    var divTeams = $("#allTeams");
    //toutes les div de nom team 
    var allTeams = $("[name='team']");
    //numéro de la nouvelle team
    var numberTeam = allTeams.length + 1;
    //div pour la team avec son numéro
    var divTeam = $("<div id='team" + numberTeam + "' name='team'>");
    //titre de la div
    var ptitle = $("<span id='title" + numberTeam + "' name='title" + numberTeam + "'>Team " + numberTeam + "</span>");
    //label pour la color
    var lblColor = $("<label>Couleur du pion</label>");
    //select pour la color
    var selColor = $("<select class='color' class='" + numberTeam + "' onchange='gameConfig();'></select>");
    //options pour la color
    var optColor = $("<option value='#FF0000'>Rouge</option>");
    var optColor2 = $("<option value='#FFCC00'>Jaune</option>");
    if (allTeams.length == 2)
    {
    	var optColor3 = $("<option value='#00FF00'selected>Vert</option>");
    }
    else
    {
    	var optColor3 = $("<option value='#00FF00'>Vert</option>");
    }
    if (allTeams.length == 3)
    {
    	var optColor4 = $("<option value='#00FFFF' selected>Cyan</option>");
    }
    else
    {
    	var optColor4 = $("<option value='#00FFFF'>Cyan</option>");
    }
    if (allTeams.length == 4)
    {
    	var optColor5 = $("<option value='#0000FF' selected>Bleu</option>");
    }
    else
    {
    	var optColor5 = $("<option value='#0000FF'>Bleu</option>");
    }
    if (allTeams.length == 5)
    {
    	var optColor6 = $("<option value='#FF00FF' selected>Magenta</option>");
    }
    else
    {
    	var optColor6 = $("<option value='#FF00FF'>Magenta</option>");
    }
    //bouton pour la création d'un nouveau joueur dans la team
    var NPButton = $("<button class='newPlayerButton' id='button" + numberTeam + "' name='button" + numberTeam + "' onclick='newPlayer(" + numberTeam + ",1)'>Nouveau joueur</button>");
    //bouton de suppression de la team
    var DTButton = $("<button class='dropTeamButton' onclick='dropTeam(\"" + numberTeam + "\")'>Supprimer la team</button>");

	selColor.append(optColor, optColor2, optColor3, optColor4, optColor5, optColor6);
	divTeam.append(ptitle, lblColor, selColor, NPButton, DTButton);
    divTeams.append(divTeam);
    newPlayer(numberTeam,1);
}

function dropTeam(idTeam)
{
	$("#team"+idTeam).remove();
	gameConfig();
}

function newPlayer(team, player)
{
    //button de création du nouveau joueur
    var NPButton = $("#button" + team).attr("onclick","newPlayer(" + team + "," + (player + 1) + ")");
    //div de la team du nouveau joueur
    var divTeam = $("#team" + team);
    //toutes les div de nom player
    var allPlayers = $("[name='player']");
    //div de config pour le nouveau player
    var divPlay = $("<div name='player' id='player" + team + "_" + player + "'></div>");
    //span du joueur dont on fait la config
    var pPlay = $("<span>Joueur " + player + "</span>");
    //select du joueur dont on fait la config
    var selPlay = $("<select name='playerName' class='" + team + "' onchange='gameConfig();'><option value='0'>ROBOT</option></select>");
    //bouton pour la création d'un nouveau joueur dans la team
    var NLButton = $("<button class='newLegionButton' id='legion"+ team + '_' + player +"' onclick='newLegion(" + team + "," + player + ",1)'>Nouvelle légion</button>");
    //bouton de suppression du player
    var DPButton = $("<button class='dropPlayerButton' onclick='dropPlayer(\"" + team + "_" + player + "\")'>Supprimer le joueur</button>");

	var users_options = '';
	for (var i = 0 ; i < save_game_users.length ; i++)
	{
		users_options += "<option value='" + save_game_users[i].user_id + "'>" + save_game_users[i].user_name + "</option>";
	}
	selPlay.append(users_options);
    divPlay.append(pPlay, selPlay, NLButton);
    if (player != 1) divPlay.append(DPButton);
    divTeam.append(divPlay);
    newLegion(team,player,1);
}

function dropPlayer(idPlayer)
{
	$("#player"+idPlayer).remove();
	gameConfig();
}

function newLegion(team, player, legion)
{
	//button de création de la nouvelle légion
    var NLButton = $("#legion" + team + '_' + player).attr("onclick","newLegion(" + team + "," + player + "," + (legion + 1) + ")");
    //div du joueur auquel appartient la légion
    var divPlay = $("#player" + team + '_' + player);
    //toutes les div de nom legion 
    var allLegions = $("[name='legion']");
    //div de config pour le nouveau player
    var divLegion = $("<div name='legion' id='legion" + team + "_" + player + "_" + legion + "'></div>");
    //span de la légion dont on fait la config
    var pLegion = $("<span>Légion " + legion + "</span>");
	//label pour la forme du pion
    var lblForm = $("<label>Forme du pion</label>");
    //select pour la forme du pion
    var selForm = $("<select class='pawn' class='" + team + "' onchange='gameConfig();'></select>");
    //options pour le selForm
    var optForm = $("<option value='square'>Carré</option>");
    var optForm2 = $("<option value='circle'>Hexagone</option>");
    if (allLegions.length == 2)
	{
    	var optForm3 = $("<option value='triangle' selected>Triangle</option>");
	}
	else
	{
    	var optForm3 = $("<option value='triangle'>Triangle</option>");
	}
    //label pour la position sur le plateau
    var lblPosit = $("<label>Position sur le plateau</label>");
    //select pour la position sur le plateau
    var selPosit = $("<select class='position' class='" + team + "' onchange='gameConfig();'></select>");
    //options pour le selPosit
    var optPosit = $("<option value='5'>Gauche</option>");
	if (allLegions.length == 2)
	{
		var optPosit2 = $("<option value='4' selected>Bas gauche</option>");
	}
	else
	{
		var optPosit2 = $("<option value='4'>Bas gauche</option>");
	}
	if (allLegions.length == 3)
	{
		var optPosit3 = $("<option value='3' selected>Bas droit</option>");
	}
	else
	{
		var optPosit3 = $("<option value='3'>Bas droit</option>");
	}
	var optPosit4 = $("<option value='2'>Droit</option>");
	if (allLegions.length == 4)
	{
		var optPosit5 = $("<option value='1' selected>Haut droit</option>");
	}
	else
	{
		var optPosit5 = $("<option value='1'>Haut droit</option>");
	}
	if (allLegions.length == 5)
	{
		var optPosit6 = $("<option value='0' selected>Haut gauche</option>");
	}
	else
	{
		var optPosit6 = $("<option value='0'>Haut gauche</option>");
	}
	//bouton de suppression de la légion
    var DLButton = $("<button class='dropLegionButton' onclick='dropLegion(\"" + team + "_" + player + "_" + legion + "\")'>Supprimer la légion</button>");
	
	selForm.append(optForm, optForm2, optForm3);
    selPosit.append(optPosit, optPosit2, optPosit3, optPosit4, optPosit5, optPosit6);
    divLegion.append(pLegion, lblForm, selForm, lblPosit, selPosit);
    if (legion != 1) divLegion.append(DLButton);
    divPlay.append(divLegion);
    gameConfig();
}

function dropLegion(idLegion)
{
	$("#legion"+idLegion).remove();
	gameConfig();
}

// correction de l'input type number de taille de plateau
function verifSize()
{
	if (($("#board_size").val() != 5) && ($("#board_size").val() != 7) && ($("#board_size").val() != 9) && ($("#board_size").val() != 11))
	{
		$("#board_size").val(5);
	}
	gameConfig();
}