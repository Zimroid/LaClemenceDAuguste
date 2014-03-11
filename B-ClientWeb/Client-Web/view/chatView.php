<body>
	<div id="chatBox">
<?php 
		// Si déconnecté
		if(false)
		{
?>
			<div>Nom d'utilisateur : ?</div>
			<button onclick="alert('Envoi déconnexion');">Déconnexion</button>
			
			<!-- Div d'affichage des messages -->
			<div id="chatMessages">
				Bonjour et bienvenue sur Auguste ...
			</div>

			<br/>
			<!-- Div d'envoi d'un message-->
			<div id="chatSend">
				<form onSubmit="addMessage(); return false;" autocomplete="off" >
					<input type="text" id="message" name="message" />
					<input type="submit" id="valid" value="Envoyer">
				</form>
			</div>
<?php
		}
		
		// Si connecté
		else
		{
?>
			<!-- Connexion -->
			<fieldset style="display:inline-block;">
				<legend>Connexion</legend>
				<form onSubmit="alert('Envoi Connexion'); return false;" autocomplete="off" >
					<label for="user">Pseudo : </label>
					<input type="text" name="user" id="user" placeholder="Pseudo">
					<label for="password">Mot de passe : </label>
					<input type="password" name="pass" id="password" placeholder="Mot de passe">
					<button type="submit">Me connecter</button>
				</form>
			</fieldset>

			<!-- Inscription -->
			<fieldset style="display:inline-block;">
				<legend>Inscription</legend>
				<form onSubmit="alert('Envoi Inscription'); return false;" autocomplete="off" >
					<label for="user">Pseudo : </label>
					<input type="text" name="user" id="user" placeholder="Pseudo">
					<label for="password">Mot de passe : </label>
					<input type="password" name="pass" id="password" placeholder="Mot de passe">
					<label for="configrm">Confirmation : </label>
					<input type="password" name="conf" id="confirm" placeholder="Confirmer">
					<button type="submit">M'inscrire</button>
				</form>
			</fieldset>
<?php
		}
?>
	</div>
</body>