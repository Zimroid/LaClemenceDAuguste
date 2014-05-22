/*
 * Fonctions utilitaires à utiliser si besoin
 */

function htmlspecialchars(text) {
	return text
		.replace(/&/g, "&amp;")
		.replace(/</g, "&lt;")
		.replace(/>/g, "&gt;")
		.replace(/"/g, "&quot;")
		.replace(/'/g, "&#039;");
}

function existStr(obj) {
	if(typeof(obj) != 'undefined' && obj != "")
	{
		return true;
	}
	else
	{
		return false;
	}
}

function exist(obj) {
	if(typeof(obj) != 'undefined')
	{
		return true;
	}
	else
	{
		return false;
	}
}