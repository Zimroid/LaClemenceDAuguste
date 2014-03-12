/*
 * Gestion des utilisateurs
 * -> Connexion
 * -> Inscription (+fonction vérif disponibilité username ?)
 * -> Déconnexion
 */

// Envoi d'une chaîne de connexion
function connexion()
{
    var login = $("#login").val();
	var password = $("#password").val();
    
    if(login != "" && password != "")
    {
        var json = JSON.stringify(
		{
            // Faire le JSON ...
			//"command": "USER_CONNEXION",
            
        });

        sendText(json);
    }
}

// Inscription au jeu
function inscription()
{
}

// Deconnexion du jeu
function deconnexion()
{
}