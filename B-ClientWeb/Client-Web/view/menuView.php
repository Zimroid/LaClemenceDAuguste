<header id="header">
	<span onclick="reloadContent(sitePath + '/index.php?script=1');"><img id="logo" src="css/images/augusteLogo.png" alt="Logo"></span>
	<a id='titleScript' onclick="sitePage = 'home';" href='<?php echo $sitePath; ?>'><h1 id="title"><?php echo $siteName; ?></h1></a>
</header>

<!-- MENU -->
<nav>
	<div class="navDivider"></div>
	<ul class="navNav">
		<li class="liHome">
			<a id='homeScript' onclick="sitePage = 'home';" href='<?php echo $sitePath; ?>/index.php?page=news'><img src="css/images/home.png" alt="H">Accueil</a>
		</li>
		<li class="liRules">
			<a id='rulesScript' onclick="sitePage = 'rules';" href="<?php echo $sitePath; ?>/index.php?page=rules"><img src="css/images/book.png" alt="R">Règles</a>
		</li>
		<li class="liFAQ">
			<a id='faqScript' onclick="sitePage = 'faq';" href="<?php echo $sitePath; ?>/index.php?page=faq"><img src="css/images/question.png" alt="Q">FAQ</a>
		</li>
		<li class="liContact">
			<a id='contactScript' onclick="sitePage = 'contact';" href="<?php echo $sitePath; ?>/index.php?page=contact"><img src="css/images/envelope.png" alt="C">Contact</a>
		</li>
		<li class="liDownload">
			<a id='downloadScript' onclick="sitePage = 'download';" href='<?php echo $sitePath; ?>/index.php?page=download'><img src="css/images/download.png" alt="D">Téléchargements</a>
		</li>
	</ul>
	<div class="navDivider"></div>
	<ul class="navGame">
		<li class="liCreate">
			<a id='newGameScript' onclick="sitePage = 'newGame';" href="<?php echo $sitePath; ?>/index.php?page=newGame"><img src="css/images/file.png" alt="N">Créer une partie</a>
		</li>
		<li class="liJoin">
			<a id='joinGameScript' onclick="sitePage = 'joinGame'; gameList('');" href="<?php echo $sitePath; ?>/index.php?page=joinGame"><img src="css/images/stack.png" alt="J">Rejoindre une partie</a>
		</li>
	</ul>
</nav>