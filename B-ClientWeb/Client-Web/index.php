<?php
	// Compression gzip
	ob_start('ob_gzhandler');
	register_shutdown_function('ob_end_flush');
	header('Content-Type: text/html; charset=utf-8');
	session_start();
	ob_start();
	
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
		include_once("view/headerView.php");
		
		// Récupération du menu
		echo "<div id='menu'>";
		include_once("view/menuView.php");
		echo "</div>";
		
		// Récupération du petit menu
		echo "<div id='smallMenu'>";
		include_once("view/smallMenuView.php");
		echo "</div>";

		// Récupération de la page
		echo "<div id='mainPage'>";
		include_once("indexPages.php");
		echo "</div>";
		
		// Affichage chat
		echo "<div id='chatBox'>";
		include_once("view/chatView.php");
		echo "</div>";
		
		// Affichage pied-de-page
		include_once("view/footerView.php");
	}
	ob_end_flush();
?>