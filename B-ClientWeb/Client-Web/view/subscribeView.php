<h2>Inscription au jeu :</h2>

<!-- Inscription -->
<label for="userSubscribe">Pseudo : </label>
<input type="text" name="userSubscribe" id="userSubscribe" placeholder="Pseudo" onkeypress="if (event.keyCode == 13) inscription()">
<br>
<label for="passwordSubscribe">Mot de passe : </label>
<input type="password" name="passwordSubscribe" id="passwordSubscribe" placeholder="Mot de passe" onkeypress="if (event.keyCode == 13) inscription()">
<br>
<label for="confirmSubscribe">Confirmation : </label>
<input type="password" name="confSubscribe" id="confirmSubscribe" placeholder="Confirmer" onkeypress="if (event.keyCode == 13) inscription()">
<br>
<input title='Inscription' type="button" value="M'inscrire" onclick="inscription();">