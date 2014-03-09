<?php
	require_once("controller/pageController.php");
	
	$pageCtr = new pageController();
	
	// Rechargement partiel de la page via JS
	if(isset($_GET['script']) && $_GET['script'] == '1')
	{
		// Récupération de la page pour remplissage
		include_once("indexPages.php");
	}
	
	// Requete complète
	else
	{
		// Affichage menu
		include 'view/menuView.php';
		
		// Si JavaScript
		if(false)
		{
			// Affichage page centrale (à remplir en JS)
			include_once("controller/pageController.php");
		}
		
		// Si pas-JavaScript
		else
		{
			// Récupération de la page pour remplissage
			include_once("indexPages.php");
			
			// + Information Jeu et Chat non-disponible sans JS !!!
		}
		
		// Affichage chat
		include 'view/chatView.php';
	}
?>