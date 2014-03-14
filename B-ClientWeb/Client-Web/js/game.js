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