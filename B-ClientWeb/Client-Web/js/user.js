/*
 * Gestion des utilisateurs
 * -> Connexion
 * -> Inscription (+fonction vérif disponibilité username ?)
 * -> Déconnexion
 */

// Envoi d'une chaîne de connexion
function connexion(bool,us,pa)
{
	var user = '';
	var pass = '';
	// connexion normale
	if (bool)
	{
		user = htmlspecialchars($("#user").val());
		pass = htmlspecialchars($("#password").val());
		pass = CryptoJS.SHA1(pass)+'';
		$.ajax('controller/pass.php?pass='+pass);
	}
	// reconnexion au chargement
	else {
		user = htmlspecialchars(us);
		pass = htmlspecialchars(pa);
	}
	
    if(user != "" && pass != "")
    {
        var json = JSON.stringify(
		{
			"command": "LOG_IN",
			"user_name": user,
			"user_password": pass
        });

        sendText(json);
    }
    else
    {
    	alert("Un des champs n'est pas rempli.");
    }
}

// Inscription au jeu
function inscription()
{
	var user = htmlspecialchars($("#userSubscribe").val());
	var pass = htmlspecialchars($("#passwordSubscribe").val());
	var conf = htmlspecialchars($("#confirmSubscribe").val());
	
    if(user != "" && pass != "" && conf != "" && pass == conf)
    {
        pass = CryptoJS.SHA1(pass)+'';
		
        var json = JSON.stringify(
		{
			"command": "ACCOUNT_CREATE",
			"user_name": user,
			"user_password": pass
        });

        sendText(json);
    }
    else if (pass != conf)
    {
    	alert("Les deux champs de mot de passe ne sont pas identiques.");
    }
    else
    {
    	alert("Un des champs n'est pas rempli.");
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