<div id="chatHeader">
<?php 
	// Si connecté
	if(isset($_SESSION['username']) AND $_SESSION['username'] != null) 
	{
?>
		<div><p>Nom d'utilisateur : <?php echo $_SESSION['username'].' '; ?><button id="deconnect" onclick="deconnexion();">Déconnexion</button></p></div>
<?php
	}
	
	// Si déconnecté
	else
	{
?>
		<!-- Connexion -->
		<form onsubmit="return false;" autocomplete="off">
			<input title='Pseudo' type="text" name="user" id="user" placeholder="Pseudo" onkeypress="if (event.keyCode == 13) connexion(true,'','')" />
			<input title='Mot de passe' type="password" name="password" id="password" placeholder="Mot de passe" onkeypress="if (event.keyCode == 13) connexion(true,'','')" />
			<input id="connect" title='Connexion' type="button" value="Me connecter" onclick="connexion(true,'','');" /><br>
			<!-- Lien vers page d'inscription -->
			<a id='subscribeScript' href='<?php echo $sitePath; ?>/index.php?page=subscribe'>M'inscrire au jeu</a>
		</form>
		
<?php
	}
?>
</div>
<div class="navDivider"></div>
<div id="chatTabs">
	<ul id ="ulTabs">
		<li class="tabs"><a href="#normalChat">Chat général</a></li>
		<li class="tabs"><a href="#gameChat">Chat de partie</a></li>
	</ul>
	<!-- Chat général -->
	<div id="normalChat">
		<!-- Div d'affichage des messages -->
		<div id="chatMessages">
			Bonjour et bienvenue sur le chat général du site ! Pour participer à de meilleures conversations, merci de rester courtois et d'éviter le langage SMS.<br>
		</div>
		
		<!-- Div d'envoi d'un message-->
		<div id="chatSend">
			<form id="formChat" onsubmit="addMessage(false); return false;" autocomplete="off">
				<textarea title='Message' id="message" name="message" onkeypress="toucheEntree(event,false)"></textarea>
				<input title='Envoyer un message' type="submit" id="valid" value="Envoyer">
			</form>
		</div>
	</div>
	<!-- Chat de partie -->
	<div id="gameChat">
		<!-- Div d'affichage des messages -->
		<div id="chatMessagesGame">
			Veuillez intégrer une partie pour activer ce chat.
		</div>
		
		<!-- Div d'envoi d'un message-->
		<div id="chatSendGame">
			<form id="formChatGame" onsubmit="addMessage(true); return false;" autocomplete="off">
				<textarea title='Message' id="messageGame" name="message" onkeypress="toucheEntree(event,true)"></textarea>
				<input title='Envoyer un message' type="submit" id="validGame" value="Envoyer">
			</form>
		</div>
	</div>
</div>
