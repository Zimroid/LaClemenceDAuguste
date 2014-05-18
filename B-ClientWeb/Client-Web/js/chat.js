/*
 * Système de chat ...
 * 
 */

// Envoi d'un message
function addMessage()
{
    var message = $("#message").val();
    message = htmlspecialchars(message);
    
    if(message != "")
    {
    	if (save_game_config.room_id != 0)
    	{
    		var json = JSON.stringify(
			{
	            "command": "CHAT_SEND",
	            "room_id": save_game_config.room_id,
	            "message": message
	        });
    	}
    	else
    	{
    		var json = JSON.stringify(
			{
	            "command": "CHAT_SEND",
	            "message": message
	        });
    	}

        $("#message").val("");
        sendText(json);
    }
}

// Affichage d'un message
function showMessage(jsonData)
{
    var json = JSON.parse(jsonData);
    
    var messagesList = $("#chatMessages");
    messagesList.html(messagesList.html() + "<br />" +  " [" + "] : " + json.text);
    messagesList.scrollTop = messagesList.scrollHeight;
}

function toucheEntree(event)
{
	if ((event.keyCode == 13) && (event.shiftKey)) this.value += '\n';
	else if (event.keyCode == 13) {
		addMessage();
		event.preventDefault();
	}
}
