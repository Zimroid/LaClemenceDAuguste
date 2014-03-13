/*
 * Gestion des utilisateurs
 * -> Connexion
 * -> Inscription (+fonction vérif disponibilité username ?)
 * -> Déconnexion
 */

// Envoi d'une chaîne de connexion
function connexion()
{
	var user = $("#user").val();
	var pass = $("#password").val();
	
    if(user != "" && pass != "")
    {
        var json = JSON.stringify(
		{
			"command": "LOG_IN",
			"name": user,
			"password": pass
        });

        sendText(json);
    }
}

// Inscription au jeu
function inscription()
{
	var user = $("#userSubscribe").val();
	var pass = $("#passwordSubscribe").val();
	var conf = $("#confirmSubscribe").val();
	
	alert(user+"-"+pass+"-"+conf);
	
    if(user != "" && pass != "" && conf != "" && pass == conf)
    {
        var json = JSON.stringify(
		{
			"command": "CREATE_ACCOUNT",
			//"command": "ACCOUNT_CREATE",
			"name": user,
			"password": pass
        });
		
		alert(json);

        sendText(json);
    }
}

// Deconnexion du jeu
function deconnexion()
{
	var json = JSON.stringify(
	{
		"command": "LOG_OUT"
	});

	sendText(json);
}