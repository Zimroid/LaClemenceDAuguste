<?php
	if (isset($_GET['mode']) && ($_GET['mode'] == 'normal'))
	{
?>
<form autocomplete="off" onsubmit="return false;" id="gameConfigForm">
	<fieldset>
		<legend><h2>Panneau de configuration de la partie <span id="game_name"><?php if (isset($_GET['name'])) echo $_GET['name']; ?></span></h2></legend>
		<input title='Type de partie' type="hidden" id="type_game" value='normal'>
		<input title='Identifiant de la salle' type="hidden" id="room_id" value="<?php if (isset($_GET['id'])) echo $_GET['id']; ?>">
		<label for="board_size">Taille du plateau :</label>
		<input type="number" id="board_size" name="board_size" onchange="gameConfig();" value="5" />
		<label for="turn_duration">Durée d'un tour (en secondes) :</label>
		<input type="number" id="turn_duration" name="turn_duration" onchange="gameConfig();" value="30" />
		<button class='newTeamButton' onclick="newTeam()">Nouvelle team</button>
		<div id="allTeams" name="allTeams">
			<div id="team1" name="team">
				<span id="title1" name="title1">Team 1</span>
				<label>Couleur du pion</label>
				<select class="color" class="1" onchange="gameConfig();">
					<option value="#FF0000" selected>Rouge</option>
					<option value="#FFFF00">Jaune</option>
					<option value="#00FF00">Vert</option>
					<option value="#00FFFF">Cyan</option>
					<option value="#0000FF">Bleu</option>
					<option value="#FF00FF">Magenta</option>
				</select>
				<button class='newPlayerButton' id="button1" name="button1" onclick="newPlayer(1,2)">Nouveau joueur</button>
				<div name="player" id="player1_1">
					<span>Joueur 1</span>
					<select name="playerName" class="1" onchange="gameConfig();">
						<option value="0">ROBOT</option>
					</select>
					<button class='newLegionButton' id="legion1_1" onclick="newLegion(1,1,2)">Nouvelle légion</button>
					<div name="legion" id="legion1_1_1">
						<span>Légion 1</span>
						<label>Forme du pion</label>
						<select class="pawn" class="1" onchange="gameConfig();">
							<option value="square" selected>Carré</option>
							<option value="circle">Cercle</option>
							<option value="triangle">Triangle</option>
						</select>
						<label>Position sur le plateau</label>
						<select class="position" class="1" onchange="gameConfig();">
							<option value="5" selected>Gauche</option>
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
				<span id="title2" name="title2">Team 2</span>
				<label>Couleur du pion</label>
				<select class="color" class="1" onchange="gameConfig();">
					<option value="#FF0000">Rouge</option>
					<option value="#FFFF00" selected>Jaune</option>
					<option value="#00FF00">Vert</option>
					<option value="#00FFFF">Cyan</option>
					<option value="#0000FF">Bleu</option>
					<option value="#FF00FF">Magenta</option>
				</select>
				<button class='newPlayerButton' id="button2" name="button2" onclick="newPlayer(2,2)">Nouveau joueur</button>
				<div name="player" id="player2_1">
					<span>Joueur 1</span>
					<select name="playerName" class="2" onchange="gameConfig();">
						<option value="0">ROBOT</option>
					</select>
					<button class='newLegionButton' id="legion2_1" onclick="newLegion(2,1,2)">Nouvelle légion</button>
					<div name="legion" id="legion2_1_1">
						<span>Légion 1</span>
						<label>Forme du pion</label>
						<select class="pawn" class="2" onchange="gameConfig();">
							<option value="square">Carré</option>
							<option value="circle" selected>Cercle</option>
							<option value="triangle">Triangle</option>
						</select>
						<label>Position sur le plateau</label>
						<select class="position" class="2" onchange="gameConfig();">
							<option value="5">Gauche</option>
							<option value="4" selected>Bas gauche</option>
							<option value="3">Bas droit</option>
							<option value="2">Droit</option>
							<option value="1">Haut droit</option>
							<option value="0">Haut gauche</option>
						</select>
					</div>
				</div>
			</div>
		</div>
		<div id="noTeam" name="noTeam">
			<h3>Présents dans la salle :</h3>
		</div>
		<input title='Lancer la partie' type="button" id="valid" value="GO !" onclick="gameStart(<?php if (isset($_GET['id'])) echo $_GET['id']; ?>)"/>
	</fieldset>
</form>
<?php
	}
	else if (isset($_GET['mode']) && ($_GET['mode'] == 'fast'))
	{
?>
<h2>En attente d'un joueur ...</h2>
<input title='Type de partie' type="hidden" id="type_game" value='fast'>
<?php
	}
?>