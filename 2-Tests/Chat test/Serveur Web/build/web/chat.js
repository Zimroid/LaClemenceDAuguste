/*
 * Système de chat ...
 * Utilisation de la fonction "checkTime" de clock.js
 * Utilisation d'un script pour la récupération de l'IP
 */

// Récupération adresse ip
ip = null;

// Envoi d'un message
function addMessage() {
    var message = document.getElementById("message");
    
    if(message != null && message.value != "")
    {
        // Récupération de la date
        var today = new Date();
        var h = checkTime(today.getHours());
        var m = checkTime(today.getMinutes());
        var time = h + ":" + m;
        
        // Récupération du message
        var stringMessage = message.value;
        message.value = "";
        
        var json = JSON.stringify({
            "ipClient" : ip,
            "auteur": null,
            "time": time,
            "message": stringMessage
        });
        
        sendText(json);
    }
}

// Affichage d'un message
function showMessage(jsonData) {
    console.log("Affichage de données");
    var json = JSON.parse(jsonData);
    
    var messagesList = document.getElementById("showMessage");
    messagesList.innerHTML += "<br />" + json.auteur +  " [" + json.time + "] : " + json.message;
    messagesList.scrollTop = messagesList.scrollHeight;
}