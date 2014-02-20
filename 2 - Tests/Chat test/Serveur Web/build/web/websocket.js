var wsUri = "ws://" + document.location.host + document.location.pathname + "endpoint";
var websocket = new WebSocket(wsUri);

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
    showMessage(evt.data);
}