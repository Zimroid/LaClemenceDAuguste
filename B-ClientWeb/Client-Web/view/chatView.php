<?php 
	// Si connecté
	if(isset($_SESSION['username']) AND $_SESSION['username'] != null) 
	{
?>
		<div>Nom d'utilisateur : <?php echo $_SESSION['username']; ?></div>
		<button onclick="alert('Envoi déconnexion');">Déconnexion</button>
<?php
	}
	
	// Si déconnecté
	else
	{
?>
		<!-- Connexion -->
		<label for="user">Pseudo : </label>
		<input type="text" name="user" id="user" placeholder="Pseudo" />
		
		<label for="password">Mot de passe : </label>
		<input type="password" name="password" id="password" placeholder="Mot de passe" />
		
		<input type="button" value="Me connecter" onclick="connexion(); " />
			
		<br/>	
		
		<!-- Lien vers page d'inscription -->
		<script>
			document.write("<a href='' onclick='reloadContent(\"<?php echo $sitePath; ?>/index.php?script=1&page=subscribe\"); return false;' >");
		</script>
		<noscript>
			<a href='<?php echo $sitePath; ?>/subscribe'>
		</noscript>
		<button>M'inscrire au jeu</button></a>
<?php
	}
?>
<br/>
<br/>

<!-- Div d'affichage des messages -->
<div id="chatMessages">
	Bonjour et bienvenue sur Auguste ...
</div>

<br/>

<!-- Div d'envoi d'un message-->
<div id="chatSend">
	<form onSubmit="addMessage(); return false;" autocomplete="off" >
		<input type="text" id="message" name="message" />
		<input type="submit" id="valid" value="Envoyer" />
	</form>
</div>
