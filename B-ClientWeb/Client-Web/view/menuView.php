<header>
	<script>
		document.write("<a href='' onclick='reloadContent(\"<?php echo $sitePath; ?>/index.php?script=1&page=news\"); return false;' ><h1 id='title'><?php echo $siteName; ?></h1></a>");
	</script>
	<noscript>
		<a href='<?php echo $sitePath; ?>'><h1 id="title"><?php echo $siteName; ?></h1></a>
	</noscript>
</header>

<!-- MENU -->
<nav>
	<div class="navDivider"></div>
	<ul class="navNav">
		<li class="liHome">
			<script>
				document.write("<a href='' onclick='reloadContent(\"<?php echo $sitePath; ?>/index.php?script=1&page=news\"); return false;' >Accueil</a>");
			</script>
			<noscript>
				<a href='<?php echo $sitePath; ?>'>Accueil</a>
			</noscript>
		</li>
		<li class="liRules">
			<script>
				document.write("<a href='' onclick='reloadContent(\"<?php echo $sitePath; ?>/index.php?script=1&page=rules\"); return false;' >Règles</a>");
			</script>
			<noscript>
				<a href="<?php echo $sitePath; ?>/rules">Règles</a>
			</noscript>
		</li>
		<li class="liFAQ">
			<script>
				document.write("<a href='' onclick='reloadContent(\"<?php echo $sitePath; ?>/index.php?script=1&page=faq\"); return false;' >FAQ</a>");
			</script>
			<noscript>
				<a href="<?php echo $sitePath; ?>/faq">FAQ</a>
			</noscript>
		</li>
		<li class="liContact">
			<script>
				document.write("<a href='' onclick='reloadContent(\"<?php echo $sitePath; ?>/index.php?script=1&page=contact\"); return false;' >Contact</a>");
			</script>
			<noscript>
				<a href="<?php echo $sitePath; ?>/contact">Contact</a>
			</noscript>
		</li>
		<li class="liDownload">
			<script>
				document.write("<a href='' onclick='reloadContent(\"<?php echo $sitePath; ?>/index.php?script=1&page=download\"); return false;' >Téléchargement</a>");
			</script>
			<noscript>
				<a href='<?php echo $sitePath; ?>/download'>Téléchargement</a>
			</noscript>
		</li>
	</ul>
	<div class="navDivider"></div>
	<ul class="navGame">
		<li class="liCreate">
			<script>
				document.write("<a href='' onclick='reloadContent(\"<?php echo $sitePath; ?>/index.php?script=1&page=newGame\"); return false;' >Créer une partie</a>");
			</script>
			<noscript>
				<a href="<?php echo $sitePath; ?>/newGame">Créer une partie</a>
			</noscript>
		</li>
		<li class="liJoin">
			<script>
				document.write("<a href='' onclick='reloadContent(\"<?php echo $sitePath; ?>/index.php?script=1&page=joinGame\"); return false;' >Rejoindre une partie</a>");
			</script>
			<noscript>
				<a href="<?php echo $sitePath; ?>/joinGame">Rejoindre une partie</a>
			</noscript>
		</li>
	</ul>
</nav>