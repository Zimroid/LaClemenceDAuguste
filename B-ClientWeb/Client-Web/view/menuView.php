<header>
	<a id='titleScript' href='<?php echo $sitePath; ?>'><h1 id="title"><?php echo $siteName; ?></h1></a>
</header>

<!-- MENU -->
<nav>
	<div class="navDivider"></div>
	<ul class="navNav">
		<li class="liHome">
			<a id='homeScript' href='<?php echo $sitePath; ?>'>Accueil</a>
		</li>
		<li class="liRules">
			<a id='rulesScript' href="<?php echo $sitePath; ?>/index.php?page=rules">Règles</a>
		</li>
		<li class="liFAQ">
			<a id='faqScript' href="<?php echo $sitePath; ?>/index.php?page=faq">FAQ</a>
		</li>
		<li class="liContact">
			<a id='contactScript' href="<?php echo $sitePath; ?>/index.php?page=contact">Contact</a>
		</li>
		<li class="liDownload">
			<a id='downloadScript' href='<?php echo $sitePath; ?>/index.php?page=download'>Téléchargements</a>
		</li>
	</ul>
	<div class="navDivider"></div>
	<ul class="navGame">
		<li class="liCreate">
			<a id='newGameScript' href="<?php echo $sitePath; ?>/index.php?page=newGame">Créer une partie</a>
		</li>
		<li class="liJoin">
			<a id='joinGameScript' href="<?php echo $sitePath; ?>/index.php?page=joinGame">Rejoindre une partie</a>
		</li>
	</ul>
</nav>