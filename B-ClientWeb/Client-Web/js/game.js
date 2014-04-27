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
        sendText(json);
    }
}

function gameStart(room_id)
{
	var json = JSON.stringify(
	{
        "command": "GAME_START",
        "room_id": room_id
        
    });

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
					stringJSON += '{"player_user_id":' + parseInt($('#player' + nbrTeam + '_' + nbrPlayer).children("[name='playerName']").val(),10) + ',"legions":[';
					// nombre de légions du joueur
					nbrLegion += $('#player' + nbrTeam + '_' + nbrPlayer).children("[name='legion']").length;
					for (var i = 0 ; i < nbrLegion ; i++)
					{
						// forme des pions du joueur
						stringJSON += '{"legion_shape": "' + $('#player' + nbrTeam + '_' + nbrPlayer).children('#legion' + nbrTeam + '_' + nbrPlayer + '_' + (i + 1)).children(".pawn").val() + '",';
						// couleur des pions du joueur
						stringJSON += '"legion_color": "' + $('#player' + nbrTeam + '_' + nbrPlayer).children(".color").val() + '",';
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
					stringJSON += '{"player_user_id": 0,"legions": []}';
				}
				if ((nbrPlayer + 1) <= $('#team' + nbrTeam).children('[name="player"]').length)
				{
					stringJSON += ',';
				}
			}
			// il n'y a plus de joueurs dans cette team
			else
			{
				if ((nbrPlayer + 1) <= $('#player' + nbrTeam + '_' + nbrPlayer).children('[name="player"]').length)
				{
					stringJSON += ',';
				}
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
    
    sendText(json);
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
    sendText(json);
}

function gameUsers(game)
{
	var json = JSON.stringify(
    {
        "command": "QUERY_USERS",
        "room_id": game
    });
    
    sendText(json);
}

function gameJoin(game)
{
	var json = JSON.stringify(
    {
        "command": "ROOM_JOIN",
        "room_id": game
    });
    
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
    var ptitle = $("<p id='title" + numberTeam + "' name='title" + numberTeam + "'>Team " + numberTeam + "</p>");
    //bouton pour la création d'un nouveau joueur dans la team
    var NPButton = $("<button id='button" + numberTeam + "' name='button" + numberTeam + "' onclick='newPlayer(" + numberTeam + ",1)'>Nouveau joueur</button>");

    divTeams.append(ptitle, NPButton, divTeam);
    gameConfig();
}

function newPlayer(team, player)
{
    //button de création du nouveau joueur
    var NPButton = $("#button" + team).attr("onclick","newPlayer(" + team + "," + (player + 1) + ")");
    //div de la team du nouveau joueur
    var divTeam = $("#team" + team);
    //div de config pour le nouveau player
    var divPlay = $("<div name='player' id='player" + team + "_" + player + "'></div>");
    //label du joueur dont on fait la config
    var lblPlay = $("<label>Joueur</label>");
    //select du joueur dont on fait la config
    var selPlay = $("<select name='playerName' class='" + team + "'></select>");
    //label pour la color
    var lblColor = $("<label>Couleur du pion</label>");
    //select pour la color
    var selColor = $("<select class='color' class='" + team + "'></select>");
    //options pour la color
    var optColor = $("<option value='#FF0000'>Rouge</option>");
    var optColor2 = $("<option value='#00FF00'>Vert</option>");
    var optColor3 = $("<option value='#0000FF'>Bleu</option>");
    //bouton pour la création d'un nouveau joueur dans la team
    var NLButton = $("<button id='legion"+ team + '_' + player +"' onclick='newLegion(" + team + "," + player + ",1)'>Nouvelle légion</button>");

    selColor.append(optColor, optColor2, optColor3);
    divPlay.append(lblPlay, selPlay, lblColor, selColor, NLButton);
    divTeam.append(divPlay);
    gameConfig();
}

function newLegion(team, player, legion)
{
	//button de création de la nouvelle légion
    var NLButton = $("#legion" + team + '_' + player).attr("onclick","newLegion(" + team + "," + player + "," + (legion + 1) + ")");
    //div du joueur auquel appartient la légion
    var divPlay = $("#player" + team + '_' + player);
    //div de config pour le nouveau player
    var divLegion = $("<div name='legion' id='legion" + team + "_" + player + "_" + legion + "'></div>");
	//label pour la forme du pion
    var lblForm = $("<label>Forme du pion</label>");
    //select pour la forme du pion
    var selForm = $("<select class='pawn' class='" + team + "'></select>");
    //options pour le selForm
    var optForm = $("<option value='square'>Carré</option>");
    var optForm2 = $("<option value='circle'>Cercle</option>");
    var optForm3 = $("<option value='triangle'>Triangle</option>");
    //label pour la position sur le plateau
    var lblPosit = $("<label>Position sur le plateau</label>");
    //select pour la position sur le plateau
    var selPosit = $("<select class='position' class='" + team + "'></select>");
    //options pour le selPosit
    var optPosit = $("<option value='5'>Gauche</option>");
	var optPosit2 = $("<option value='4'>Bas gauche</option>");
	var optPosit3 = $("<option value='3'>Bas droit</option>");
	var optPosit4 = $("<option value='2'>Droit</option>");
	var optPosit5 = $("<option value='1'>Haut droit</option>");
	var optPosit6 = $("<option value='0'>Haut gauche</option>");
	
	selForm.append(optForm, optForm2, optForm3);
    selPosit.append(optPosit, optPosit2, optPosit3, optPosit4, optPosit5, optPosit6);
    divLegion.append(lblForm, selForm, lblPosit, selPosit);
    divPlay.append(divLegion);
    gameConfig();
}
