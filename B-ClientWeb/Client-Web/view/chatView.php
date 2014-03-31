<div id="chatHeader">
<?php 
	// Si connecté
	if(isset($_SESSION['username']) AND $_SESSION['username'] != null) 
	{
?>
		<div>Nom d'utilisateur : <?php echo $_SESSION['username']; ?></div>
		<button onclick="deconnexion();">Déconnexion</button>
<?php
	}
	
	// Si déconnecté
	else
	{
?>
		<!-- Connexion -->
		<form onsubmit="return false;" autocomplete="off">
			<input type="text" name="user" id="user" placeholder="Pseudo" onkeypress="if (event.keyCode == 13) connexion(true,'','')" />
			<input type="password" name="password" id="password" placeholder="Mot de passe" onkeypress="if (event.keyCode == 13) connexion(true,'','')" />
			<input type="button" value="Me connecter" onclick="connexion(true,'','');" /><br>
		</form>
		<!-- Lien vers page d'inscription -->
		<a id='subscribeScript' href='<?php echo $sitePath; ?>/subscribe'>M'inscrire au jeu</a>
		
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
		<textarea id="message" name="message" onkeypress="toucheEntree(event)"></textarea>
		<input type="submit" id="valid" value="Envoyer" />
	</form>
</div>
