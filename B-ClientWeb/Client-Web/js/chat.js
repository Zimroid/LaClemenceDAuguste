/*
 * Système de chat ...
 * 
 */

// Envoi d'un message
function addMessage()
{
    var message = $("#message").val();
    
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

// Affichage d'un message
function showMessage(jsonData)
{
    var json = JSON.parse(jsonData);
    
    var messagesList = $("#chatMessages");
    messagesList.html(messagesList.html() + "<br />" /*+ json.author*/ +  " [" /*+ json.time*/ + "] : " + json.text);
    messagesList.scrollTop = messagesList.scrollHeight;
}