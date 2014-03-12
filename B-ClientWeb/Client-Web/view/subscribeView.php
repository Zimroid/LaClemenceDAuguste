<h1>Inscription au jeu :</h1>

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

<p>
Ajouter vérification :
- disponibilité username
- compléxité mot de passe ...
</p>