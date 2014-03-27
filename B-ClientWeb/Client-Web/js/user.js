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
		user = $("#user").val();
		pass = $("#password").val();
		pass = CryptoJS.SHA1(pass)+'';
		$.ajax('controller/pass.php?pass='+pass);
	}
	// reconnexion au chargement
	else {
		user = us;
		pass = pa;
	}
	
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
function inscription(bool)
{
	var user = $("#userSubscribe").val();
	var pass = $("#passwordSubscribe").val();
	var conf = $("#confirmSubscribe").val();
	if (bool)
	{
		pass = CryptoJS.SHA1(pass)+'';
		conf = CryptoJS.SHA1(conf)+'';
	}
	
    if(user != "" && pass != "" && conf != "" && pass == conf)
    {
        var json = JSON.stringify(
		{
			"command": "ACCOUNT_CREATE",
			"name": user,
			"password": pass
        });

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