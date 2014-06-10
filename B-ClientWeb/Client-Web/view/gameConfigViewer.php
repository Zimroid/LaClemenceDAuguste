<?php
	if (isset($_GET['mode']) && ($_GET['mode'] == 'normal'))
	{
?>
<form autocomplete="off" onsubmit="return false;" id="gameConfigForm">
	<fieldset>
		<legend><h2>Panneau de configuration de la partie <span id="game_name"><?php if (isset($_GET['name'])) echo $_GET['name']; ?></span></h2></legend>
		<input title='Type de partie' type="hidden" id="type_game" value='normal' disabled>
		<input title='Identifiant de la salle' type="hidden" id="room_id" value="<?php if (isset($_GET['id'])) echo $_GET['id']; ?>" disabled>
		<label for="board_size">Taille du plateau :</label>
		<input type="number" id="board_size" name="board_size" onchange="gameConfig();" value="5" disabled/>
		<label for="turn_duration">Durée d'un tour (en secondes) :</label>
		<input type="number" id="turn_duration" name="turn_duration" onchange="gameConfig();" value="30" disabled/>
		<button class='newTeamButton' onclick="newTeam()" disabled>Nouvelle team</button>
		<div id="allTeams" name="allTeams">
			<div id="team1" name="team">
				<span id="title1" name="title1">Team 1</span>
				<label>Couleur du pion</label>
				<input type="text" class="color" class="1" onchange="gameConfig();" value="Rouge" disabled/>
				<button class='newPlayerButton' id="button1" name="button1" onclick="newPlayer(1,2)" disabled>Nouveau joueur</button>
				<div name="player" id="player1_1">
					<span>Joueur 1</span>
					<input type="text" class="playerName" class="1" onchange="gameConfig();" value="ROBOT Pseudo-Random" disabled/>
					<button class='newLegionButton' id="legion1_1" onclick="newLegion(1,1,2)" disabled>Nouvelle légion</button>
					<div name="legion" id="legion1_1_1">
						<span>Légion 1</span>
						<label>Forme du pion</label>
						<input type="text" class="pawn" class="1" onchange="gameConfig();" value="Carré" disabled/>
						<label>Position sur le plateau</label>
						<input type="text" class="position" class="1" onchange="gameConfig();" value="Gauche" disabled/>
					</div>
				</div>
			</div>
			<div id="team2" name="team">
				<span id="title2" name="title2">Team 2</span>
				<label>Couleur du pion</label>
				<input type="text" class="color" class="1" onchange="gameConfig();" value="Jaune" disabled/>
				<button class='newPlayerButton' id="button2" name="button2" onclick="newPlayer(2,2)" disabled>Nouveau joueur</button>
				<div name="player" id="player2_1">
					<span>Joueur 1</span>
					<input type="text" class="playerName" class="2" onchange="gameConfig();" value="ROBOT Pseudo-Random" disabled/>
					<button class='newLegionButton' id="legion2_1" onclick="newLegion(2,1,2)" disabled>Nouvelle légion</button>
					<div name="legion" id="legion2_1_1">
						<span>Légion 1</span>
						<label>Forme du pion</label>
						<input type="text" class="pawn" class="2" onchange="gameConfig();" value="Cercle" disabled/>
						<label>Position sur le plateau</label>
						<input type="text" class="position" class="2" onchange="gameConfig();" value="Bas gauche" disabled/>
					</div>
				</div>
			</div>
		</div>
		<div id="noTeam" name="noTeam">
			<h3>Présents dans la salle :</h3>
		</div>
		<input title='Lancer la partie' type="button" id="valid" value="GO !" onclick="gameStart(<?php if (isset($_GET['id'])) echo $_GET['id']; ?>)" disabled/>
	</fieldset>
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