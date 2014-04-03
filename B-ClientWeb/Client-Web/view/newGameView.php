<h2>Création d'une nouvelle partie</h2>

<label for="gameName">Nom de la partie : </label>
<input type="text" name="gameName" id="gameName" onkeypress="if (event.keyCode == 13) gameCreate()" />
<button onclick="gameCreate()" >Créer</button>