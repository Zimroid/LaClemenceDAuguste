<header>
	<script>
		document.write("<a href='' onclick='reloadContent(\"<?php echo $sitePath; ?>/index.php?script=1&page=news\"); return false;' >");
	</script>
	<noscript>
		<a href='<?php echo $sitePath; ?>'>
	</noscript>
		<h1 id="title"><?php echo $siteName; ?></h1>
	</a>
</header>

<!-- MENU -->
<nav>
	<div class="navDivider"></div>
	<ul class="navNav">
		<li class="liHome">
			<script>
				document.write("<a href='' onclick='reloadContent(\"<?php echo $sitePath; ?>/index.php?script=1&page=news\"); return false;' >");
			</script>
			<noscript>
				<a href='<?php echo $sitePath; ?>'>
			</noscript>
				Accueil
			</a>
		</li>
		<li class="liRules">
			<script>
				document.write("<a href='' onclick='reloadContent(\"<?php echo $sitePath; ?>/index.php?script=1&page=rules\"); return false;' >");
			</script>
			<noscript>
				<a href="<?php echo $sitePath; ?>/rules">
			</noscript>
				Règles
			</a>
		</li>
		<li class="liFAQ">
			<script>
				document.write("<a href='' onclick='reloadContent(\"<?php echo $sitePath; ?>/index.php?script=1&page=faq\"); return false;' >");
			</script>
			<noscript>
				<a href="<?php echo $sitePath; ?>/faq">
			</noscript>
				FAQ
			</a>
		</li>
		<li class="liContact">
			<script>
				document.write("<a href='' onclick='reloadContent(\"<?php echo $sitePath; ?>/index.php?script=1&page=contact\"); return false;' >");
			</script>
			<noscript>
				<a href="<?php echo $sitePath; ?>/contact">
			</noscript>
				Contact
			</a>
		</li>
		<li class="liDownload">
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
	<div class="navDivider"></div>
	<ul class="navGame">
		<li class="liCreate">
			<script>
				document.write("<a href='' onclick='reloadContent(\"<?php echo $sitePath; ?>/index.php?script=1&page=newGame\"); return false;' >");
			</script>
			<noscript>
				<a href="<?php echo $sitePath; ?>/newGame">
			</noscript>
				Créer une partie
			</a>
		</li>
		<li class="liJoin">
			<script>
				document.write("<a href='' onclick='reloadContent(\"<?php echo $sitePath; ?>/index.php?script=1&page=joinGame\"); return false;' >");
			</script>
			<noscript>
				<a href="<?php echo $sitePath; ?>/joinGame">
			</noscript>
				Rejoindre une partie
			</a>
		</li>
	</ul>
</nav>