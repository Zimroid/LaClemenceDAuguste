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

    divTeam.setAttribute("id","team" + (numberTeams.length + 1));
    divTeam.setAttribute("name","team");
    ptitle.setAttribute("id","title" + (numberTeams.length + 1));
    ptitle.setAttribute("name",ptitle.id);
    ptitle.innerHTML = "Team " + (numberTeams.length + 1);
    NPButton.setAttribute("id","button" + (numberTeams.length + 1));
    NPButton.setAttribute("name","button" + (numberTeams.length + 1));
    NPButton.setAttribute("onclick","newPlayer(" + (numberTeams.length + 1) + ",1)");
    NPButton.innerHTML = "Nouveau joueur";

    divTeam.appendChild(ptitle);
    divTeam.appendChild(NPButton);
    divTeams.appendChild(divTeam);
}

function newPlayer(team,joueur)
{
	var NPButton = document.getElementById("button" + team);
    var divTeam = document.getElementById("team" + team);
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

	NPButton.setAttribute("onclick","newPlayer(" + team + "," + (joueur + 1) + ")");
	selPlayer.setAttribute("id","player" + team + '_' + joueur);
    selPlayer.setAttribute("name","player" + team + '_' + joueur);
    optPlayerDefault.setAttribute("selected","selected");
    optPlayerDefault.innerHTML = "Joueur";
    lblLegionNbr.setAttribute('for','legion_number' + team + '_' + joueur);
    lblLegionNbr.innerHTML = "Nombre de légions";
    legionNbr.setAttribute("type","number");
    legionNbr.setAttribute("min","1");
    legionNbr.setAttribute("step","1");
    legionNbr.value = "1";
    legionNbr.setAttribute("id","legion_number" + team + '_' + joueur);
    legionNbr.setAttribute("name","legion_number" + team + '_' + joueur);
    selPawn.setAttribute("id","pawn" + team + '_' + joueur);
    selPawn.setAttribute("name","pawn" + team + '_' + joueur);
    optPawnDefault.setAttribute("selected","selected");
    optPawnDefault.innerHTML = "Forme du pion";
    optPawn1.innerHTML = "Carré";
    optPawn2.innerHTML = "Cercle";
    optPawn3.innerHTML = "Triangle";
    lblSelColor.setAttribute('for','color' + team + '_' + joueur);
    lblSelColor.innerHTML = "Couleur du pion";
    color.setAttribute("id","color" + team + '_' + joueur);
    color.setAttribute("name","color" + team + '_' + joueur);
    color.setAttribute("class","color");
    selPosition.setAttribute("id","position" + team + '_' + joueur);
    selPosition.setAttribute("name","position" + team + '_' + joueur);
    optPositionDefault.setAttribute("selected","selected");
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
    
    jscolor.init();
}