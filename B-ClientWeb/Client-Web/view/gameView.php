<div id="board_players"></div>
<button id="turn_finish" onclick="gameTurnFinish(save_game_turn.room_id)">Terminer</button>
<progress id="timer"></progress>

<div id="canvas">
	<canvas id="board">
		Votre navigateur ne supporte pas le jeu, veuillez le mettre à jour.
	</canvas>
</div>

<button onclick='$("#board").zoom();'>+</button>
<button onclick='$("#board").dezoom();'>-</button>