<?php
	if (isset($_GET['mode']) && ($_GET['mode'] == 'normal'))
	{
?>
<h2>Panneau de configuration de la partie <span id="game_name"><?php if (isset($_GET['name'])) echo $_GET['name']; ?></span></h2>
<form autocomplete="off" onsubmit="return false;" >
	<input title='Type de partie' type="hidden" id="type_game" value='normal'>
	<input title='Identifiant de la salle' type="hidden" id="room_id" value="<?php if (isset($_GET['id'])) echo $_GET['id']; ?>">
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
					<label>Couleur du pion</label>
					<select class="color" class="1" onselectionchange="gameConfig();">
						<option value="#FF0000">Rouge</option>
						<option value="#00FF00">Vert</option>
						<option value="#0000FF">Bleu</option>
					</select>
					<button id="legion1_1" onclick="newLegion(1,1,2)">Nouvelle légion</button>
					<div name="legion" id="legion1_1_1">
						<label>Forme du pion</label>
						<select class="pawn" class="1" onselectionchange="gameConfig();">
							<option value="square">Carré</option>
							<option value="circle">Cercle</option>
							<option value="triangle">Triangle</option>
						</select>
						<label>Position sur le plateau</label>
						<select class="position" class="1" onselectionchange="gameConfig();">
							<option value="5">Gauche</option>
							<option value="4">Bas gauche</option>
							<option value="3">Bas droit</option>
							<option value="2">Droit</option>
							<option value="1">Haut droit</option>
							<option value="0">Haut gauche</option>
						</select>
					</div>
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
	<input title='Lancer la partie' type="button" id="valid" value="GO !" onclick="gameStart(<?php if (isset($_GET['id'])) echo $_GET['id']; ?>)"/>
</form>
<?php
	}
	else if (isset($_GET['mode']) && ($_GET['mode'] == 'fast'))
	{
?>
<h2>En attente d'un joueur ...</h2>
<input title='Type de partie' type="hidden" id="type_game" value='fast'>
<input title='Lancer la partie' type="button" id="start" value="Lancer la partie" onclick="gameStart(<?php if (isset($_GET['id'])) echo $_GET['id']; ?>)">
<?php
	}
?>