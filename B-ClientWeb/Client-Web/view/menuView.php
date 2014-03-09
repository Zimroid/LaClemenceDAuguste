<?php require_once("view/siteGlobalVar.php"); ?>

<html>
	<head>
        <title><?php echo $siteName; ?></title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width">
        <script type="text/javascript" src="<?php echo $sitePath; ?>/jquery/js/jquery-1.11.0.min.js"></script>
        <script type="text/javascript" src="<?php echo $sitePath; ?>/jquery/js/jquery-ui-1.10.4.custom.min.js"></script>
        <!--script type="text/javascript" src="< ?php echo $sitePath; ?>/js/websocket.js"></script-->
        <script type="text/javascript" src="<?php echo $sitePath; ?>/js/chat.js"></script>
    </head>

	<body>
		<a href="<?php echo $sitePath; ?>">
			<h2><?php echo $siteName; ?></h2>
		</a>

		<ul>
			<li>
				<a href="<?php echo $sitePath; ?>">Accueil</a>
			</li>
			<li>
				<a href="<?php echo $sitePath; ?>/regles">Règles</a>
			</li>
			<li>
				<a href="<?php echo $sitePath; ?>/faq">FAQ</a>
			</li>
			<li>
				<a href="<?php echo $sitePath; ?>/contact">Contact</a>
			</li>
		</ul>
		<p>-----------------------------------</p>
	</body>
</html>