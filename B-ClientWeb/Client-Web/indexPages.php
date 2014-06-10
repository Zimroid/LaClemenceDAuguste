<?php
	// boutons pour masquer les colonnes
	if(isset($_GET['page']) && ($_GET['page'] != 'connect' && $_GET['page'] != 'deconnect'))
	{
		echo '<div id="buttonsReduct">
				<span id="buttonReductMenu" class="bReduct" onclick="slideColumn(\'menu\')">Menu</span>
				<span id="buttonReductChat" class="bReduct" onclick="slideColumn(\'chat\')">Chat</span>
			</div>';
	}

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
		if (isset($_SESSION['username']))
		{
			$pageCtr->newGame();
		}
		else
		{
			$pageCtr->subscribe();
		}
	}
	else if(isset($_GET['page']) && $_GET['page'] == 'joinGame')
	{
		if (isset($_SESSION['username']))
		{
			$pageCtr->joinGame();
		}
		else
		{
			$pageCtr->subscribe();
		}
	}
	else if(isset($_GET['page']) && $_GET['page'] == 'gameConfig')
	{
		if (isset($_SESSION['username']))
		{
			$pageCtr->gameConfig();
		}
		else
		{
			$pageCtr->subscribe();
		}
	}
	else if(isset($_GET['page']) && $_GET['page'] == 'gameConfigViewer')
	{
		if (isset($_SESSION['username']))
		{
			$pageCtr->gameConfigViewer();
		}
		else
		{
			$pageCtr->subscribe();
		}
	}
	
	else if(isset($_GET['page']) && $_GET['page'] == 'game')
	{
		if (isset($_SESSION['username']))
		{
			$pageCtr->game();
		}
		else
		{
			$pageCtr->subscribe();
		}
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
	
	// Page Compte
	else if(isset($_GET['page']) && $_GET['page'] == 'account')
	{
		$pageCtr->account();
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
	
	// Page par défaut
	else
	{
		echo '<div id="buttonsReduct">
				<span id="buttonReductMenu" class="bReduct" onclick="slideColumn(\'menu\')">Menu</span>
				<span id="buttonReductChat" class="bReduct" onclick="slideColumn(\'chat\')">Chat</span>
			</div>';
		$pageCtr->news();
	}
?>