/*
 * Système de chat ...
 * 
 */

// Envoi d'un message
function addMessage(id)
{
    var message = $("#message").val();
    message = htmlspecialchars(message);
    
    if(message != "")
    {
        var json = JSON.stringify(
		{
            "command": "CHAT_SEND",
            "game_id": id,
            "message": message
        });

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
		addMessage(0);
		event.preventDefault();
	}
}
