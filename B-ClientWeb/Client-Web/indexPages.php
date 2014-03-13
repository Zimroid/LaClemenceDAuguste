<?php
	// Pages basiques
	if(isset($_GET['page']) && $_GET['page'] == 'news')
	{
		$pageCtr->news();
	}
	else if(isset($_GET['page']) && $_GET['page'] == 'rules')
	{
		$pageCtr->rules();
	}
	else if(isset($_GET['page']) && $_GET['page'] == 'faq')
	{
		$pageCtr->faq();
	}
	else if(isset($_GET['page']) && $_GET['page'] == 'contact')
	{
		$pageCtr->contact();
	}
	
	// Pages de jeu
	else if(isset($_GET['page']) && $_GET['page'] == 'newGame')
	{
		$pageCtr->newGame();
	}
	else if(isset($_GET['page']) && $_GET['page'] == 'joinGame')
	{
		$pageCtr->joinGame();
	}
	
	// Page DL
	else if(isset($_GET['page']) && $_GET['page'] == 'download')
	{
		$pageCtr->download();
	}
	
	// Page Inscription
	else if(isset($_GET['page']) && $_GET['page'] == 'subscribe')
	{
		$pageCtr->subscribe();
	}
	
	// Page Chat : gestion utilisateur
	else if(isset($_GET['page']) && $_GET['page'] == 'connect')
	{
		$userCtr->connect();
	}
	else if(isset($_GET['page']) && $_GET['page'] == 'deconnect')
	{
		$userCtr->deconnect();
	}
?>