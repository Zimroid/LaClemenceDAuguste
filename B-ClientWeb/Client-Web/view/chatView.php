<div id="chatHeader">
<?php 
	// Si connecté
	if(isset($_SESSION['username']) AND $_SESSION['username'] != null) 
	{
?>
		<div>Nom d'utilisateur : <?php echo $_SESSION['username']; ?></div>
		<button id="deconnect" onclick="deconnexion();">Déconnexion</button>
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

<!-- Div d'affichage des messages -->
<div id="chatMessages">
	Bonjour et bienvenue sur le chat Auguste ! Pour participer à de meilleures conversations, merci de rester courtois et d'éviter le langage SMS.
</div>

<!-- Div d'envoi d'un message-->
<div id="chatSend">
	<form onsubmit="addMessage(); return false;" autocomplete="off" >
		<textarea title='Message' id="message" name="message" onkeypress="toucheEntree(event)"></textarea>
		<input title='Envoyer un message' type="submit" id="valid" value="Envoyer" />
	</form>
</div>
