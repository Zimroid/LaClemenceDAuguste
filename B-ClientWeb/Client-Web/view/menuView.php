<?php require_once("view/siteGlobalVar.php"); ?>
<!DOCTYPE html>
<html>
	<head>
        <title><?php echo $siteName; ?></title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width">
        <script type="text/javascript" src="<?php echo $sitePath; ?>/jquery/js/jquery-1.11.0.min.js"></script>
        <script type="text/javascript" src="<?php echo $sitePath; ?>/jquery/js/jquery-ui-1.10.4.custom.min.js"></script>
        <script type="text/javascript" src="<?php echo $sitePath; ?>/js/AJAX-reloadContent.js"></script>
		<!--script type="text/javascript" src="< ?php echo $sitePath; ?>/js/websocket.js"></script-->
        <script type="text/javascript" src="<?php echo $sitePath; ?>/js/chat.js"></script>
        <link rel="stylesheet" href="<?php echo $sitePath; ?>/css/chat.css" type="text/css">
    </head>

	<body>
		<a href="<?php echo $sitePath; ?>">
			<h2><?php echo $siteName; ?></h2>
		</a>

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
		<p>-----------------------------------</p>
	</body>
</html>