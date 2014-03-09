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
		$pageCtr->contact();
	}
	else if(isset($_GET['page']) && $_GET['page'] == 'joinGame')
	{
		$pageCtr->contact();
	}
	
	// Page DL
	else if(isset($_GET['page']) && $_GET['page'] == 'download')
	{
		$pageCtr->download();
	}
?>