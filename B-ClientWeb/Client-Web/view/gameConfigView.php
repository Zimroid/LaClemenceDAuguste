<?php
	if (isset($_GET['mode']) && ($_GET['mode'] == 'normal'))
	{
?>
<h2>Panneau de configuration de la partie <span id="game_name"><?php if (isset($_GET['name'])) echo $_GET['name']; ?></span></h2>
<form autocomplete="off" onsubmit="return false;" >
	<input type="hidden" id="type_game" value='normal'>
	<input type="hidden" id="room_id" value="<?php if (isset($_GET['id'])) echo $_GET['id']; ?>">
	<label for="player_number">Nombre de joueurs :</label>
	<input type="number" id="player_number" name="player_number" onfocusout="gameConfig();" />
	<label for="board_size">Taille du plateau :</label>
	<input type="number" id="board_size" name="board_size" onfocusout="gameConfig();" value="5" />
	<label for="turn_duration">Durée d'un tour (en secondes) :</label>
	<input type="number" id="turn_duration" name="turn_duration" onfocusout="gameConfig();" value="30" />

	<fieldset>
		<legend>Nom de la partie</legend>
		<button onclick="newTeam()">Nouvelle team</button>
		<div id="allTeams" name="allTeams">
			<div id="team1" name="team">
				<p id="title1" name="title1">Team 1</p>
				<button id="button1" name="button1" onClick="newPlayer(1,2)">Nouveau joueur</button>
				<div name="player" id="player1_1">
					<label>Joueur</label>
					<select name="playerName" class="1" onselectionchange="gameConfig();">
					</select>
					<label>Nombre de légions</label>
					<input type="number" min="1" step="1" value="1" name="legion_number" class="1" onfocusout="gameConfig();" />
					<label>Forme du pion</label>
					<select name="pawn" class="1" onselectionchange="gameConfig();">
						<option name="square">Carré</option>
						<option name="circle">Cercle</option>
						<option name="triangle">Triangle</option>
					</select>
					<label>Couleur du pion</label>
					<select name="color" class="1" onselectionchange="gameConfig();">
						<option name="#FF0000">Rouge</option>
						<option name="#00FF00">Vert</option>
						<option name="#0000FF">Bleu</option>
					</select>
					<label>Position sur le plateau</label>
					<select name="position" class="1" onselectionchange="gameConfig();">
						<option name="5">Gauche</option>
						<option name="4">Bas gauche</option>
						<option name="3">Bas droit</option>
						<option name="2">Droit</option>
						<option name="1">Haut droit</option>
						<option name="0">Haut gauche</option>
					</select>
				</div>
			</div>
			<div id="team2" name="team">
				<p id="title2" name="title2">Team 2</p>
				<button id="button2" name="button2" onclick="newPlayer(2,1)">Nouveau joueur</button>
			</div>
		</div>
		<div id="noTeam" name="noTeam">
			<p>Spectateurs</p>
		</div>
	</fieldset>
	<input type="button" id="valid" value="GO !" onclick="gameStart(<?php if (isset($_GET['id'])) echo $_GET['id']; ?>)"/>
</form>
<?php
	}
	else if (isset($_GET['mode']) && ($_GET['mode'] == 'fast'))
	{
?>
<h2>En attente d'un joueur ...</h2>
<input type="hidden" id="type_game" value='fast'>
<input type="button" id="start" value="Lancer la partie" onclick="gameStart(<?php if (isset($_GET['id'])) echo $_GET['id']; ?>)">
<?php
	}
?>