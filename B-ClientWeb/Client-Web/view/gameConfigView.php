<h2>Panneau de configuration de la partie</h2>
<form onSubmit="gameStart(); return false;" autocomplete="off" >
	<!--<textarea type="text" id="message" name="message" onKeyPress="toucheEntree(event)"></textarea>-->
	<input type="hidden" id="game_id" />
	<label for="player_number">Nombre de joueurs :</label>
	<input type="number" id="player_number" name="player_number" onfocusout="gameConfig();" />
	<label for="board_size">Taille du plateau :</label>
	<input type="number" id="board_size" name="board_size" onfocusout="gameConfig();" />

	<fieldset>
		<legend>Nom de la partie</legend>
		<label>Team 1</label>
		<label>Team 2</label>
		<label>A affecter</label>
	</fieldset>

	<input type="submit" id="valid" value="GO !" />
</form>