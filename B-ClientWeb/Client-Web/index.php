<?php
	require_once("controller/pageController.php");
	
	$pageCtr = new pageController();
	
	// Rechargement partiel de la page via JS
	if(isset($_GET['script']) && $_GET['script'] == '1')
	{
		// Mise à jour du corps de la page
		if(isset($_GET['page']) && $_GET['page'] == 'news')
		{
			$pageCtr->news();
		}
		else if(isset($_GET['page']) && $_GET['page'] == 'regles')
		{
			$pageCtr->regles();
		}
		else if(isset($_GET['page']) && $_GET['page'] == 'faq')
		{
			$pageCtr->faq();
		}
		else if(isset($_GET['page']) && $_GET['page'] == 'contact')
		{
			$pageCtr->contact();
		}
	}
	
	// Requete complète
	else
	{
		// Affichage menu
		include 'view/menuView.php';
		
		// Mise à jour du corps de la page
		if(false) // Si JavaScript
		{
			// Affichage page centrale (à remplir en JS)
			include 'view/mainView.php';
		}
		
		else // Si pas-JavaScript
		{
			if(isset($_GET['page']) && $_GET['page'] == 'news')
			{
				$pageCtr->news();
			}
			else if(isset($_GET['page']) && $_GET['page'] == 'regles')
			{
				$pageCtr->regles();
			}
			else if(isset($_GET['page']) && $_GET['page'] == 'faq')
			{
				$pageCtr->faq();
			}
			else if(isset($_GET['page']) && $_GET['page'] == 'contact')
			{
				$pageCtr->contact();
			}
			
			// + Information Jeu et Chat non-disponible sans JS !!!
		}
		
		// Affichage chat
		include 'view/chatView.php';
	}
?>