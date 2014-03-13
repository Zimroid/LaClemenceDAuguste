//var websocket = new WebSocket("ws://localhost:47135");
var websocket = new WebSocket("ws://130.79.214.172:47135");

websocket.onmessage = function(evt) { onMessage(evt) };
websocket.onerror   = function(evt) { onError(evt)   };
websocket.onopen    = function(evt) { onOpen(evt)    };

function onOpen(evt)
{
	console.log("Web socket opened !");
}

function onError(evt)
{
    console.log(evt.data);
}

function sendText(json)
{
	console.log("sending text: " + json);
    websocket.send(json);
}

function onMessage(evt)
{
    console.log("received: " + evt.data);
    
	// Traitement des données reçues
    process(evt);
}