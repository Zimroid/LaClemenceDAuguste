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
    var divTeam = document.createElement("form");
    var numberTeams = document.getElementsByName("team");
    var ptitle = document.createElement("p");
    var NPButton = document.createElement("button");

    divTeam.id = "team" + (numberTeams.length + 1);
    divTeam.name = "team";
    ptitle.id = "title" + (numberTeams.length + 1);
    ptitle.name = ptitle.id;
    ptitle.innerText = "Team " + (numberTeams.length + 1);
    NPButton.onClick = "newPlayer(" + (numberTeams.length + 1) + ")";
    NPButton.innerText = "Nouveau Joueur";

    divTeam.appendChild(ptitle);
    divTeam.appendChild(NPButton);
    divTeams.appendChild(formTeam);
}

function newPlayer(nbr)
{
    var divTeam = document.getElementById("team" + nbr);
    var divTeams = document.getElementsByName("team");
    var selPlayer = document.createElement("select");
    var lblLegionNbr = document.createElement("label");
    var legionNbr = document.createElement("input");
    var selPawn = document.createElement("select");
    var selColor = document.createElement("select");
    var selPosition = document.createElement("select");

    
}