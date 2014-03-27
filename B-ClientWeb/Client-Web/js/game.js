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
    var json = JSON.stringify(
    {
        "command": "GAME_CONFIG"
    });
    
    sendText(json);
}

function newTeam()
{
    //div contenant toutes les team (sans les observateurs)
    var divTeams = $("#allTeams");
    //toutes les div de nom team 
    var allTeams = $(".team");
    //numéro de la nouvelle team
    var numberTeam = allTeams.length + 1;
    //div pour la team avec son numéro
    var divTeam = $("<div id='team" + numberTeam + "' name='team'>");
    //titre de la div
    var ptitle = $("<p id='title" + numberTeam + "' name='title" + numberTeam + "'>Team" + numberTeam + "</p>");
    //bouton pour la création d'un nouveau joueur dans la team
    var NPButton = $("<button id='button" + numberTeam + "' name='button" + numberTeam + "' onClick='newPlayer(" + numberTeam + ",1)>Nouveau joueur</button>");

    divTeams.append(ptitle, NPButton, divTeam);
}

function newPlayer(team,joueur)
{
	//div de la team du nouveau joueur
    var divTeam = $("#team" + team);
    //div de config pour le nouveau player
    var divPlay = $("<div name='player' id='player'" + player);
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
    var optForm3 = $("<option>Triangle</option>");
    //label pour la color
    var lblColor = $("<label for='color'>Couleur du pion</label>");
    //input pour la color
    var inpColor = $("<input name='color' class='" + team + " color' />");
    //select pour la position sur le plateau
    var selPosit = $("<select name='position' id='" + team = "'></select>");
    //options pour le selPosit
    var optPosit = $("<option selected='selected'>Position sur le plateau</option>");
    var optPosit2 = $("<option name='0'>Haut gauche</option>");
    var optPosit3 = $("<option name='1'>Haut droit</option>");

    selPlay.append(optPlay, optPlay2);
    selForm.append(optForm, optForm2, optForm3);
    selPosit.append(optPosit, optPosit2, optPosit3);
    divPlay.append(selPlay, lblLegion, inpLegion, selForm, lblColor, inpColor, selPosit);
    divTeam.append(divPlay);
    
    jscolor.init();
}