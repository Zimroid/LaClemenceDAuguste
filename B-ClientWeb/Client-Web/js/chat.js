/*
 * Système de chat ...
 * Utilisation d'un script pour la récupération de l'IP
 */

// Récupération adresse ip
ip = null;

$(function(){
    $("#h1Chat").draggable();
});

// Envoi d'un message
function addMessage()
{
    var message = $("#message").val();
    
    if(message != "")
    {
        var json = JSON.stringify({
            "command": "CHAT_SEND",
            "text": message
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