function gameCreate()
{
	var gameName = $("#gameName").val();

	if(gameName != "")
    {
        var json = JSON.stringify(
		{
            "command": "GAME_CREATE",
            "game_name": gameName
        });

        $("#message").val("");
        sendText(json);
    }
}

function gameStart()
{
	var json = JSON.stringify(
	{
        "command": "GAME_START"
    });

    sendText(json);
}

function gameConfig()
{
    //nombre de joueurs dont il faut envoyer la configuration
    var playNbr = $("[name='player']");

    var test ='{"command": "GAME_CONFIG","room_id": "1"}';

    var json = JSON.stringify({
        "command": "GAME_CONFIG",
        "room_id": "1"
    });
    
    sendText(json);
}

function gameList(argument)
{
    if (argument == 'on_update')
    {
        var json = JSON.stringify(
        {
            "command": "GAME_LIST",
            "on_update": true
        });
    }
    else
    {
        $(".game").remove();
        var json = JSON.stringify(
        {
            "command": "GAME_LIST",
            "on_update": false
        });
    }
    sendText(json);
}

function gameJoin(game)
{
	var json = JSON.stringify(
    {
        "command": "GAME_JOIN",
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
}

function newPlayer(team, player)
{
    //button de création du nouveau joueur
    var NPButton = $("#button" + team).attr("onclick","newPlayer(" + team + "," + (player + 1) + ")");
    //div de la team du nouveau joueur
    var divTeam = $("#team" + team);
    //div de config pour le nouveau player
    var divPlay = $("<div name='player' id='player" + team + "_" + player + "'></div>");
    //select du joueur dont on fait la config
    var selPlay = $("<select name='player' class='" + team + "'></select>");
    //options pour selPl
    var optPlay = $("<option selected='selected'>Joueur</option>");
    var optPlay2 = $("<option>Admin</option>");
    //label pour le legion_number
    var lblLegion = $("<label for='legion_number'>Nombre de légions</label>");
    //input pour le legion_number
    var inpLegion = $("<input type='number' min='1' step='1' value='1' name='legion_number' class='" + team + "' />");
    //select pour la forme du pion
    var selForm = $("<select name='pawn' class='" + team + "'></select>");
    //options pour le selForm
    var optForm = $("<option selected='selected'>Forme du pion</option>");
    var optForm2 = $("<option>Carré</option>");
    var optForm3 = $("<option>Cercle</option>");
    var optForm4 = $("<option>Triangle</option>");
    //label pour la color
    var lblColor = $("<label for='color'>Couleur du pion</label>");
    //select pour la color
    var selColor = $("<select name='color' class='" + team + "'></select>");
    //options pour la color
    var optColor = $("<option selected='selected'>Couleur du pion</option>");
    var optColor2 = $("<option name='1'>Rouge</option>");
    var optColor3 = $("<option name='2'>Vert</option>");
    var optColor4 = $("<option name='3'>Bleu</option>");
    //select pour la position sur le plateau
    var selPosit = $("<select name='position' class='" + team + "'></select>");
    //options pour le selPosit
    var optPosit = $("<option selected='selected'>Position sur le plateau</option>");
    var optPosit2 = $("<option name='0'>Bas droit</option>");
    var optPosit3 = $("<option name='1'>Bas gauche</option>");
    var optPosit4 = $("<option name='2'>Haut droit</option>");
    var optPosit5 = $("<option name='3'>Haut gauche</option>");

    selPlay.append(optPlay, optPlay2);
    selForm.append(optForm, optForm2, optForm3, optForm4);
    selColor.append(optColor, optColor2, optColor3, optColor4);
    selPosit.append(optPosit, optPosit2, optPosit3, optPosit4, optPosit5);
    divPlay.append(selPlay, lblLegion, inpLegion, selForm, lblColor, selColor, selPosit);
    divTeam.append(divPlay);
}