<p>Hello World</p>

<?php
	// Récupération modèles
	require_once("controller/pageController.php");
	$pageCtr = new pageController();
	
	echo'News :<br/>';
	$pageCtr->showNews();
	echo'<br/>FAQ :<br/>';
	$pageCtr->showFaq();
?>