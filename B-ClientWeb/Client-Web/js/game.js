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
    var divTeams = document.getElementById("allTeams");
    var divTeam = document.createElement("div");
    var numberTeams = document.getElementsByName("team");
    var ptitle = document.createElement("p");
    var NPButton = document.createElement("button");
    var br = document.createElement("br");

    divTeam.id = "team" + (numberTeams.length + 1);
    divTeam.name = "team";
    ptitle.id = "title" + (numberTeams.length + 1);
    ptitle.name = ptitle.id;
    ptitle.innerHTML = "Team " + (numberTeams.length + 1);
    NPButton.setAttribute("onclick","newPlayer(" + (numberTeams.length + 1) + ")");
    NPButton.innerHTML = "Nouveau joueur";

    divTeam.appendChild(ptitle);
    divTeam.appendChild(NPButton);
    divTeam.appendChild(br);
    divTeams.appendChild(divTeam);
}

function newPlayer(nbr)
{
    var divTeam = document.getElementById("team" + nbr);
    var selPlayer = document.createElement("select");
    var optPlayerDefault = document.createElement("option");
    // TODO gérer les joueurs connectés
    var lblLegionNbr = document.createElement("label");
    var legionNbr = document.createElement("input");
    var selPawn = document.createElement("select");
    var optPawnDefault = document.createElement("option");
    var optPawn1 = document.createElement("option");
    var optPawn2 = document.createElement("option");
    var optPawn3 = document.createElement("option");
    var lblSelColor = document.createElement("label");
    var color = document.createElement("input");
    var selPosition = document.createElement("select");
    var optPositionDefault = document.createElement("option");
    var optPosition1 = document.createElement("option");
    var optPosition2 = document.createElement("option");
    var optPosition3 = document.createElement("option");
    var optPosition4 = document.createElement("option");
    var br = document.createElement("br");

	// TODO gérer la non-réplication des id
	selPlayer.id = "player" + nbr;
    selPlayer.name = "player" + nbr;
    optPlayerDefault.selected = "selected";
    optPlayerDefault.innerHTML = "Joueur";
    lblLegionNbr.setAttribute('for','legion_number' + nbr);
    lblLegionNbr.innerHTML = "Nombre de légions";
    legionNbr.type = "number";
    legionNbr.min = "1";
    legionNbr.step = "1";
    legionNbr.value = "1";
    legionNbr.id = "legion_number" + nbr;
    legionNbr.name = "legion_number" + nbr;
    selPawn.id = "pawn" + nbr;
    selPawn.name = "pawn" + nbr;
    optPawnDefault.selected = "selected";
    optPawnDefault.innerHTML = "Forme du pion";
    optPawn1.innerHTML = "Carré";
    optPawn2.innerHTML = "Cercle";
    optPawn3.innerHTML = "Triangle";
    lblSelColor.setAttribute('for','color' + nbr);
    lblSelColor.innerHTML = "Couleur du pion";
    color.id = "color" + nbr;
    color.name = "color" + nbr;
    color.setAttribute("class","color");
    selPosition.id = "position" + nbr;
    selPosition.name = "position" + nbr;
    optPositionDefault.selected = "selected";
    optPositionDefault.innerHTML = "Position sur le plateau";
    optPosition1.innerHTML = "Bas droit";
    optPosition2.innerHTML = "Bas gauche";
    optPosition3.innerHTML = "Haut droit";
    optPosition4.innerHTML = "Haut gauche";
    
    selPlayer.appendChild(optPlayerDefault);
    selPawn.appendChild(optPawnDefault);
    selPawn.appendChild(optPawn1);
    selPawn.appendChild(optPawn2);
    selPawn.appendChild(optPawn3);
    selPosition.appendChild(optPositionDefault);
    selPosition.appendChild(optPosition1);
    selPosition.appendChild(optPosition2);
    selPosition.appendChild(optPosition3);
    selPosition.appendChild(optPosition4);
    divTeam.appendChild(selPlayer);
    divTeam.appendChild(lblLegionNbr);
    divTeam.appendChild(legionNbr);
    divTeam.appendChild(selPawn);
    divTeam.appendChild(lblSelColor);
    divTeam.appendChild(color);
    divTeam.appendChild(selPosition);
    divTeam.appendChild(br);
    
    jscolor.init();
}