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
				<button id="button1" name="button1" onClick="newPlayer(1,2)">Nouveau joueur</button>
				<div name="player" id="player1">
					<select name="player" class="1">
						<option selected="selected">Joueur</option>
						<option>Admin</option>
					</select>
					<label for="legion_number">Nombre de légions</label>
					<input type="number" min="1" step="1" value="1" name="legion_number" class="1" />
					<select name="pawn" class="1">
						<option selected="selected">Forme du pion</option>
						<option>Carré</option>
						<option>Cercle</option>
						<option>Triangle</option>
					</select>
					<label for="color">Couleur du pion</label>
					<input name="color" class="1 color" />
					<select name="position" id="1">
						<option selected="selected">Position sur le plateau</option>
						<option>Bas droit</option>
						<option>Bas gauche</option>
						<option>Haut droit</option>
						<option>Haut gauche</option>
					</select>
				</div>
			</div>
			<div id="team2" name="team">
				<p id="title2" name="title2">Team 2</p>
				<button id="button2" name="button2" onClick="newPlayer(2,1)">Nouveau joueur</button>
			</div>
		</div>
		<form id="noTeam" name="noTeam">
			<p>À affecter</p>
		</form>
	</fieldset>
	<input type="submit" id="valid" value="GO !" />
</form>