/*
 * Système de chat ...
 * 
 */

// Envoi d'un message
function addMessage(varIsGameChat)
{
  	isGameChat = false;
	
	if(exist(varIsGameChat))
	{
		isGameChat = varIsGameChat;
	}
	
	if (!isGameChat)
   	{
		var message = $("#message").val();
   		message = htmlspecialchars(message);
   		if(message != "")
   		{
    		var json = JSON.stringify(
			{
	            "command": "CHAT_SEND",
	            "message": message
	        });
			
	        $("#message").val("");
        	sendText(json);
       	}
   	}
	
	else if (exist(save_game_config) && exist(save_game_config.room_id) && save_game_config.room_id != 0)
	{
		var message = $("#messageGame").val();
		message = htmlspecialchars(message);
		if(message != "")
		{
			var json = JSON.stringify(
			{
				"command": "CHAT_SEND",
				"room_id": save_game_config.room_id,
				"message": message
			});
			
			$("#messageGame").val("");
			sendText(json);
		}
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

function toucheEntree(event,isGameChat)
{
	if ((event.keyCode == 13) && (event.shiftKey)) this.value += '\n';
	else if (event.keyCode == 13) {
		addMessage(isGameChat);
		event.preventDefault();
	}
}

$('#chatTabs').tabs();