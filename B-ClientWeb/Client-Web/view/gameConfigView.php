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
		<button onClick="newTeam()">Nouvelle team</button>
		<div id="allTeams" name="allTeams">
			<div id="team1" name="team">
				<p id="title1" name="title1">Team 1</p>
				<button onClick="newPlayer(1)">Nouveau joueur</button>
				<select name="team1" id="player1">
					<option selected="selected">Joueur</option>
					<option>créateur de la partie</option>
				</select>
				<label for="legion_number">Nombre de légions</label>
				<input type="number" min="1" step="1" value="1" name="legion_number" id="legion_number" />
				<select name="team1" id="pawn1">
					<option selected="selected">Forme du pion</option>
					<option>Carré</option>
					<option>Cercle</option>
					<option>Triangle</option>
				</select>
				<select name="team1" id="color1">
					<option selected="selected">Couleur du pion</option>
					<option>Bleu</option>
					<option>Rouge</option>
					<option>Vert</option>
				</select>
				<select name="team1" id="position1">
					<option selected="selected">Position sur le plateau</option>
					<option>Bas droit</option>
					<option>Bas gauche</option>
					<option>Haut droit</option>
					<option>Haut gauche</option>
				</select>
			</div>
			<div id="team2" name="team">
				<p id="title2" name="title2">Team 2</p>
				<button onClick="newPlayer(2)">Nouveau joueur</button>
			</div>
		</div>
		<form id="noTeam" name="noTeam">
			<p>A affecter</p>
		</form>
	</fieldset>
	<input type="submit" id="valid" value="GO !" />
</form>