<?php require_once("view/siteGlobalVar.php"); ?>
<!DOCTYPE html>
<html>
	<head>
        <title><?php echo $siteName; ?></title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta name="viewport" content="width=device-width">
        
		<script>
			sitePath = "<?php echo $sitePath; ?>";
		</script>
		
		<script type="text/javascript" src="<?php echo $sitePath; ?>/jquery/js/jquery-1.11.0.min.js"></script>
        <script type="text/javascript" src="<?php echo $sitePath; ?>/jquery/js/jquery-ui-1.10.4.custom.min.js"></script>
        <script type="text/javascript" src="<?php echo $sitePath; ?>/js/AJAX-reloadPageContent.js"></script>
		<script type="text/javascript" src="<?php echo $sitePath; ?>/js/AJAX-reloadChatContent.js"></script>
		
		<script type="text/javascript" src="<?php echo $sitePath; ?>/js/websocket.js"></script>
		<script type="text/javascript" src="<?php echo $sitePath; ?>/js/websocketIndex.js"></script>
		
		<script type="text/javascript" src="<?php echo $sitePath; ?>/js/chat.js"></script>
		<script type="text/javascript" src="<?php echo $sitePath; ?>/js/user.js"></script>
		
		<link rel="stylesheet" href="<?php echo $sitePath; ?>/css/chat.css" type="text/css">	
    </head>

	<body>
		<header>
			<script>
				document.write("<a href='' onclick='reloadContent(\"<?php echo $sitePath; ?>/index.php?script=1&page=news\"); return false;' >");
			</script>
			<noscript>
				<a href='<?php echo $sitePath; ?>'>
			</noscript>
				<h2><?php echo $siteName; ?></h2>
			</a>
		</header>
		
		<!-- MENU -->
		<nav>
			<ul>
				<li>
					<script>
						document.write("<a href='' onclick='reloadContent(\"<?php echo $sitePath; ?>/index.php?script=1&page=news\"); return false;' >");
					</script>
					<noscript>
						<a href='<?php echo $sitePath; ?>'>
					</noscript>
						Accueil
					</a>
				</li>
				<li>
					<script>
						document.write("<a href='' onclick='reloadContent(\"<?php echo $sitePath; ?>/index.php?script=1&page=rules\"); return false;' >");
					</script>
					<noscript>
						<a href="<?php echo $sitePath; ?>/rules">
					</noscript>
						Règles
					</a>
				</li>
				<li>
					<script>
						document.write("<a href='' onclick='reloadContent(\"<?php echo $sitePath; ?>/index.php?script=1&page=faq\"); return false;' >");
					</script>
					<noscript>
						<a href="<?php echo $sitePath; ?>/faq">
					</noscript>
						FAQ
					</a>
				</li>
				<li>
					<script>
						document.write("<a href='' onclick='reloadContent(\"<?php echo $sitePath; ?>/index.php?script=1&page=contact\"); return false;' >");
					</script>
					<noscript>
						<a href="<?php echo $sitePath; ?>/contact">
					</noscript>
						Contact
					</a>
				</li>
				<br/>
				<li>
					<script>
						document.write("<a href='' onclick='reloadContent(\"<?php echo $sitePath; ?>/index.php?script=1&page=newGame\"); return false;' >");
					</script>
					<noscript>
						<a href="<?php echo $sitePath; ?>/newGame">
					</noscript>
						Créer une partie
					</a>
				</li>
				<li>
					<script>
						document.write("<a href='' onclick='reloadContent(\"<?php echo $sitePath; ?>/index.php?script=1&page=joinGame\"); return false;' >");
					</script>
					<noscript>
						<a href="<?php echo $sitePath; ?>/joinGame">
					</noscript>
						Rejoindre une partie
					</a>
				</li>
				<br/>
				<li>
					<script>
						document.write("<a href='' onclick='reloadContent(\"<?php echo $sitePath; ?>/index.php?script=1&page=download\"); return false;' >");
					</script>
					<noscript>
						<a href='<?php echo $sitePath; ?>/download'>
					</noscript>
						Téléchargement
					</a>
				</li>
			</ul>
		</nav>
		
		<p>-----------------------------------</p>
	</body>
</html>