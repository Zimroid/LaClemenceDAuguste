/*
 * adaptation de tous les éléments de la page si JavaScript est activé
 */

$("#titleScript").html("<a id='titleScript' href='' onclick='reloadContent(\"" + sitePath + "/index.php?script=1&page=news\"); return false;' ><h1 id='title'>" + siteName + "</h1></a>");
$("#homeScript").html("<a id='homeScript' href='' onclick='reloadContent(\"" + sitePath + "/index.php?script=1&page=news\"); return false;' >Accueil</a>");
$("#rulesScript").html("<a id='rulesScript' href='' onclick='reloadContent(\"" + sitePath + "/index.php?script=1&page=rules\"); return false;' >Règles</a>");
$("#faqScript").html("<a id='faqScript' href='' onclick='reloadContent(\"" + sitePath + "/index.php?script=1&page=faq\"); return false;' >FAQ</a>");
$("#contactScript").html("<a id='contactScript' href='' onclick='reloadContent(\"" + sitePath + "/index.php?script=1&page=contact\"); return false;' >Contact</a>");
$("#downloadScript").html("<a id='downloadScript' href='' onclick='reloadContent(\"" + sitePath + "/index.php?script=1&page=download\"); return false;' >Téléchargements</a>");
$("#newGameScript").html("<a id='newGameScript' href='' onclick='reloadContent(\"" + sitePath + "/index.php?script=1&page=newGame\"); return false;' >Créer une partie</a>");
$("#joinGameScript").html("<a id='joinGameScript' href='' onclick='reloadContent(\"" + sitePath + "/index.php?script=1&page=joinGame\"); return false;' >Rejoindre une partie</a>");
$("#subscribeScript").html("<a id='subscribeScript' href='' onclick='reloadContent(\"" + sitePath + "/index.php?script=1&page=subscribe\"); return false;' >M'inscrire au jeu</a>");