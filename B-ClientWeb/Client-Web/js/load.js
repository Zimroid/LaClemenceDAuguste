/*
 * adaptation de tous les éléments de la page si JavaScript est activé
 */
function load() {
	$("#titleScript").html("<a id='titleScript' href='' onclick='reloadContent(\"" + sitePath + "/index.php?script=1&page=news\"); return false;' ><h1 id='title'>" + siteName + "</h1></a>");
	$("#homeScript").html("<a id='homeScript' href='' onclick='reloadContent(\"" + sitePath + "/index.php?script=1&page=news\"); return false;' ><img src='css/images/home.png' alt='H'> Accueil</a>");
	$("#rulesScript").html("<a id='rulesScript' href='' onclick='reloadContent(\"" + sitePath + "/index.php?script=1&page=rules\"); return false;' ><img src='css/images/book.png' alt='R'> Règles</a>");
	$("#faqScript").html("<a id='faqScript' href='' onclick='reloadContent(\"" + sitePath + "/index.php?script=1&page=faq\"); return false;' ><img src='css/images/question.png' alt='Q'> FAQ</a>");
	$("#contactScript").html("<a id='contactScript' href='' onclick='reloadContent(\"" + sitePath + "/index.php?script=1&page=contact\"); return false;' ><img src='css/images/envelope.png' alt='C'> Contact</a>");
	$("#downloadScript").html("<a id='downloadScript' href='' onclick='reloadContent(\"" + sitePath + "/index.php?script=1&page=download\"); return false;' ><img src='css/images/download.png' alt='D'> Téléchargements</a>");
	$("#newGameScript").html("<a id='newGameScript' href='' onclick='reloadContent(\"" + sitePath + "/index.php?script=1&page=newGame\"); return false;' ><img src='css/images/file.png' alt='N'> Créer une partie</a>");
	$("#joinGameScript").html("<a id='joinGameScript' href='' onclick='reloadContent(\"" + sitePath + "/index.php?script=1&page=joinGame\"); return false;' ><img src='css/images/stack.png' alt='J'> Rejoindre une partie</a>");
	$("#subscribeScript").html("<a id='subscribeScript' href='' onclick='reloadContent(\"" + sitePath + "/index.php?script=1&page=subscribe\"); return false;' >M'inscrire au jeu</a>");
	//$("#accountScript").html("<a id='accountScript' href='' onclick='reloadContent(\"" + sitePath + "/index.php?script=1&page=account\"); return false;' >Mon compte</a>");
	
	$("#homeScriptSmall").html("<a id='homeScript' href='' onclick='reloadContent(\"" + sitePath + "/index.php?script=1&page=news\"); return false;' ><img src='css/images/home.png' alt='H'></a>");
	$("#rulesScriptSmall").html("<a id='rulesScript' href='' onclick='reloadContent(\"" + sitePath + "/index.php?script=1&page=rules\"); return false;' ><img src='css/images/book.png' alt='R'></a>");
	$("#faqScriptSmall").html("<a id='faqScript' href='' onclick='reloadContent(\"" + sitePath + "/index.php?script=1&page=faq\"); return false;' ><img src='css/images/question.png' alt='Q'></a>");
	$("#contactScriptSmall").html("<a id='contactScript' href='' onclick='reloadContent(\"" + sitePath + "/index.php?script=1&page=contact\"); return false;' ><img src='css/images/envelope.png' alt='C'></a>");
	$("#downloadScriptSmall").html("<a id='downloadScript' href='' onclick='reloadContent(\"" + sitePath + "/index.php?script=1&page=download\"); return false;' ><img src='css/images/download.png' alt='D'></a>");
	$("#newGameScriptSmall").html("<a id='newGameScript' href='' onclick='reloadContent(\"" + sitePath + "/index.php?script=1&page=newGame\"); return false;' ><img src='css/images/file.png' alt='N'></a>");
	$("#joinGameScriptSmall").html("<a id='joinGameScript' href='' onclick='reloadContent(\"" + sitePath + "/index.php?script=1&page=joinGame\"); return false;' ><img src='css/images/stack.png' alt='J'></a>");
}

load();
