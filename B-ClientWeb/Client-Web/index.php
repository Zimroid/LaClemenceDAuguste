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
		// Affichage en-tête
		include 'view/headerView.php';
		
		// Récupération de la page
		echo "<div id='mainPage'>";
		include_once("indexPages.php");
		echo "</div>";
		
		// + Information Jeu et Chat non-disponible sans JS !!!
		
		// Affichage chat
		include 'view/chatView.php';
	}
?>