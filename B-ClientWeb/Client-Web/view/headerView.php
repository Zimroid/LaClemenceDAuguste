<?php require_once("view/siteGlobalVar.php"); ?>
<!DOCTYPE html>
<html lang="fr">
	<head>
        <title><?php echo $siteName; ?></title>
		<meta charset="utf-8">
        <meta name="viewport" content="width=device-width">
        <meta name="description" content="Application <?php echo $siteName; ?> réalisée par le Conseil Sept">
        
        <link rel="icon" type="image/png" href="favicon.png">
		<link rel="stylesheet" href="css/chat.css" type="text/css">
		<link rel="stylesheet" href="css/main.css" type="text/css">
		<link rel="stylesheet" href="css/menu.css" type="text/css">
    </head>
	<body>
		<!--<?php if(!isset($_SESSION['username'])) echo '<div id="couvercle" onclick="this.style.height=\'0%\';"></div>'; ?>-->
		<!--[if IE]>
			<b style="border:1px solid black;background-color:orange;margin:5px;padding:5px">ATTENTION : Vous utilisez un navigateur assez ancien.<br/><br/> Votre navigation sur le site risque de ne pas être optimale.<br><br>
			Téléchargez gratuitement Mozilla Firefox<br/><br/> pour bénéficier d'un navigateur récent et performant.<br/><br/> <a href="http://affiliates.mozilla.org/link/banner/52667"><img src="http://affiliates.mozilla.org/media/uploads/banners/6035509d7f9cbdbb0db48a1e5046305a0d00f6c9.png" alt="Mozilla Firefox" /></a></b>
		<![endif]-->