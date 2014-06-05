<header id="smallHeader">
	<span onclick="reloadContent(sitePath + '/index.php?script=1');"><img id="smallLogo" src="favicon.png" alt="Logo"></span>
</header>

<!-- MENU -->
<nav>
	<div class="navDivider"></div>
	<ul class="navNav">
		<li class="liHome">
			<a id='homeScriptSmall' onclick="sitePage = 'home';" href='<?php echo $sitePath; ?>/index.php?page=news'><img src="css/images/home.png" alt="H"></a>
		</li>
		<li class="liRules">
			<a id='rulesScriptSmall' onclick="sitePage = 'rules';" href="<?php echo $sitePath; ?>/index.php?page=rules"><img src="css/images/book.png" alt="R"></a>
		</li>
		<li class="liFAQ">
			<a id='faqScriptSmall' onclick="sitePage = 'faq';" href="<?php echo $sitePath; ?>/index.php?page=faq"><img src="css/images/question.png" alt="Q"></a>
		</li>
		<li class="liContact">
			<a id='contactScriptSmall' onclick="sitePage = 'contact';" href="<?php echo $sitePath; ?>/index.php?page=contact"><img src="css/images/envelope.png" alt="C"></a>
		</li>
		<li class="liDownload">
			<a id='downloadScriptSmall' onclick="sitePage = 'download';" href='<?php echo $sitePath; ?>/index.php?page=download'><img src="css/images/download.png" alt="D"></a>
		</li>
	</ul>
	<div class="navDivider"></div>
	<ul class="navGame">
		<li class="liCreate">
			<a id='newGameScriptSmall' onclick="sitePage = 'newGame';" href="<?php echo $sitePath; ?>/index.php?page=newGame"><img src="css/images/file.png" alt="N"></a>
		</li>
		<li class="liJoin">
			<a id='joinGameScriptSmall' onclick="sitePage = 'joinGame'; gameList('');" href="<?php echo $sitePath; ?>/index.php?page=joinGame"><img src="css/images/stack.png" alt="J"></a>
		</li>
	</ul>
</nav>