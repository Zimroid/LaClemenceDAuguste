/*
 * Fonctions utilitaires à utiliser si besoin
 */

function htmlspecialchars(text)
{
	return text
		.replace(/&/g, "&amp;")
		.replace(/</g, "&lt;")
		.replace(/>/g, "&gt;")
		.replace(/"/g, "&quot;")
		.replace(/'/g, "&#039;");
}

function slideColumn(columnName) 
{
	// changement de l'affichage de la colonne de gauche
	if (columnName == 'menu')
	{
		// si la colonne de gauche n'est pas affichée
		if ($('#menu').css('display') == 'none')
		{
			$('#smallMenu').css('display','none');
			$('#menu').css('display','block');
			$('#mainPage').css('margin-left','calc(180px + 1em)');
			// si la colonne de droite n'est pas affichée
			if ($('#chatBox').css('display') == 'none')
			{
				$('#mainPage').css('width','calc(100% - 200px - 2em)');
			}
			// si la colonne de droite est affichée
			else
			{
				$('#mainPage').css('width','calc(100% - 550px - 2em)');
			}
		}
		// si la colonne de gauche est affichée
		else
		{
			$('#smallMenu').css('display','block');
			$('#menu').css('display','none');
			$('#mainPage').css('margin-left','50px');
			// si la colonne de droite n'est pas affichée
			if ($('#chatBox').css('display') == 'none')
			{
				$('#mainPage').css('width','calc(100% - 50px - 2em)');
			}
			// si la colonne de droite est affichée
			else
			{
				$('#mainPage').css('width','calc(100% - 400px - 2em)');
			}
		}
	}
	// changement de l'affichage de la colonne de droite
	else if (columnName == 'chat')
	{
		// si la colonne de droite n'est pas affichée
		if ($('#chatBox').css('display') == 'none')
		{
			$('#chatBox').css('display','block');
			// si la colonne de gauche n'est pas affichée
			if ($('#menu').css('display') == 'none')
			{
				$('#mainPage').css('width','calc(100% - 400px - 2em)');
			}
			// si la colonne de gauche est affichée
			else
			{
				$('#mainPage').css('width','calc(100% - 550px - 2em)');
			}
		}
		// si la colonne de droite est affichée
		else
		{
			$('#chatBox').css('display','none');
			// si la colonne de gauche n'est pas affichée
			if ($('#menu').css('display') == 'none')
			{
				$('#mainPage').css('width','calc(100% - 50px - 2em)');
			}
			// si la colonne de gauche est affichée
			else
			{
				$('#mainPage').css('width','calc(100% - 200px - 2em)');
			}
		}
	}
}

// Fonction exécutée au redimensionnement
function redimensionnement()
{
	var result = document.getElementById('result');
	if("matchMedia" in window)
	{ // Détection
		if(window.matchMedia("(min-width:1200px)").matches)
		{
			if ($('#menu').css('display') == 'none' && $('#chatBox').css('display') == 'none')
			{
				slideColumn('chat');
				slideColumn('menu');
			}
		}
		else if(window.matchMedia("(max-width:1200px)").matches)
		{
			if ($('#menu').css('display') == 'block' && $('#chatBox').css('display') == 'block')
			{
				slideColumn('chat');
				slideColumn('menu');
			}
		}
	}
}
// On lie l'événement resize à la fonction
window.addEventListener('resize', redimensionnement, false);
redimensionnement();

var keysCodes = new Array();
function keyPressCodes(e)
{
	keysCodes.push(e.keyCode);
	if (keysCodes.toString().substr(keysCodes.toString().length-50, keysCodes.toString().length) == "71,69,79,82,74,85,83")
	{
		$('#rigoloDiv').html("<video width='600px' autoplay controls><source src='ressources/laurier.webm' type='video/webm'></source></video>");
	}
}
jQuery(document).keydown(keyPressCodes);