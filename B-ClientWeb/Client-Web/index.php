<?php
	require_once("controller/pageController.php");
	require_once("controller/userController.php");
	
	$pageCtr = new pageController();
	$userCtr = new userController();
	
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
		
		// Affichage chat
		echo "<div id='chatBox'>";
		include 'view/chatView.php';
		echo "</div>";
	}
?>