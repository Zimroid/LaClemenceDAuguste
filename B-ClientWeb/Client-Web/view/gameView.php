<div id="board_players"></div>
<button onclick='$("#board").zoom();'>+</button>
<button onclick='$("#board").dezoom();'>-</button>
<progress id="timer"></progress>
<button id="turn_finish" onclick="gameTurnFinish(save_game_turn.room_id)">Finir le tour</button>
<button id="turn_finish" onclick="reloadContent(sitePath + '/index.php?script=1&page=news');">Quitter la partie</button>

<div id="canvas">
	<canvas id="board">
		Votre navigateur ne supporte pas le jeu, veuillez le mettre à jour.
	</canvas>
</div>