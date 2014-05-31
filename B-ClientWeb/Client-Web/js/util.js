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
	if (columnName == 'menu')
	{
		if ($('#menu').css('display') == 'none')
		{
			$('#smallMenu').css('display','none');
			$('#menu').css('display','block');
			$('#mainPage').css('margin-left','calc(180px + 1em)');
			if ($('#chatBox').css('display') == 'none')
			{
				$('#mainPage').css('width','calc(100% - 200px - 2em)');
			}
			else
			{
				$('#mainPage').css('width','calc(100% - 550px - 2em)');
			}
		}
		else
		{
			$('#smallMenu').css('display','block');
			$('#menu').css('display','none');
			$('#mainPage').css('margin-left','50px');
			if ($('#chatBox').css('display') == 'none')
			{
				$('#mainPage').css('width','96%');
			}
			else
			{
				$('#mainPage').css('width','calc(100% - 400px - 2em)');
			}
		}
	}
	else if (columnName == 'chat')
	{
		if ($('#chatBox').css('display') == 'none')
		{
			$('#chatBox').css('display','block');
			if ($('#menu').css('display') == 'none')
			{
				$('#mainPage').css('width','calc(100% - 350px - 2em)');
			}
			else
			{
				$('#mainPage').css('width','calc(100% - 550px - 2em)');
			}
		}
		else
		{
			$('#chatBox').css('display','none');
			if ($('#menu').css('display') == 'none')
			{
				$('#mainPage').css('width','98%');
			}
			else
			{
				$('#mainPage').css('width','calc(100% - 200px - 2em)');
			}
		}
	}
}

var keysCodes = new Array();
function keyPressCodes(e)
{
	keysCodes.push(e.keyCode);
	if (keysCodes.toString().substr(keysCodes.toString().length-50, keysCodes.toString().length) == "79,77,70,71,68,79,71,83")
	{
		$('#rigoloDiv').html("<iframe src='http://omfgdogs.com/'></iframe>");
	}
}
jQuery(document).keydown(keyPressCodes);