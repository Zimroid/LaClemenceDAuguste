<h2>Le jeu</h2>

<div style="width:90%;height:90%;margin:auto;margin-top:2%;">
<canvas id="board" style="border:1px solid #d3d3d3;">
Your browser does not support the HTML5 canvas tag.</canvas>
</div>
<button onclick='alert($("#board").getDeplacement());'>Finir le tour</button>

<label for="posDep">Position de départ</label>
<input type="text" id="posDep" name="posDep">
<br />
<label for="posArr">Position d'arrivée</label>
<input type="text" id="posArr" name="posArr">
<br />
<button onclick='$("#board").initBoard(save_game_turn);'>GO !</button>