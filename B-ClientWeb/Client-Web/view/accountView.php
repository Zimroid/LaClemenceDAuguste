<h2>Mon compte :</h2>

<!-- Modification du nom -->
<h3>Modifier mon nom :</h3>
<label for="userAccount2">Nouveau nom : </label>
<input type="text" name="userAccount2" id="userAccount2" placeholder="Pseudo" onkeypress="if (event.keyCode == 13) modifier_nom()">
<br>
<input title='Modifier mon nom' type="button" value="Modifier mon nom" onclick="modifier_nom()">

<!-- Modification du mot de passe -->
<h3>Modifier mon mot de passe :</h3>
<label for="passwordModif">Nouveau mot de passe : </label>
<input type="text" name="passwordModif" id="passwordModif" placeholder="Mot de passe" onkeypress="if (event.keyCode == 13) modifier_pass()">
<br>
<label for="passwordModif2">Répéter le nouveau mot de passe : </label>
<input type="password" name="passwordModif2" id="passwordModif2" placeholder="Mot de passe" onkeypress="if (event.keyCode == 13) modifier_pass()">
<br>
<input title='Modifier mon mot de passe' type="button" value="Modifier mon mot de passe" onclick="modifier_pass()">