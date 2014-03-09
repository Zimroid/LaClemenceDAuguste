var websocket = new WebSocket("ws://localhost:9000");
//var websocket = new WebSocket("130.79.214.171:9000");

websocket.onmessage = function(evt) { onMessage(evt) };
websocket.onerror = function(evt) { onError(evt) };

function onError(evt) {
    console.log(evt.data);
}

function sendText(json) {
    console.log("sending text: " + json);
    websocket.send(json);
}
                
function onMessage(evt) {
    console.log("received: " + evt.data);
    // Traitement des données reçues
    // On utilise pas ça pour l'instant (le serveur doit envoyer autre chose que ":)" du JSON par exemple...)
    //showMessage(evt.data);
    showMessage('{"command":"CHAT_SEND","text":"' + evt.data + '"}');
}