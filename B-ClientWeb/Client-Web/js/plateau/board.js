//le canvas en jquery
var t;
//le tableau de pions
var pawns;
//le tableau des légions
var legions;
//la taille du plateau
var size;
//les coordonnées en pixel des cases -> tableau de ligne -> tableau de case -> tableau de coordonnées
var virtual;
// le rayon d'une case
var rayon;
//Pour savoir si on viens de créer le board
var first = true;
//Le numéro du joueur
var playerGroup;
//la liste des armures [pawnX, pawnY, equipé(true/false)]
var armor;
//clavier
var keys = new Array();
//Deplacement départ
var depStart;
//Déplacement arrivé
var depEnd;
//Le groupe du pions cliqué
var actuGroup;
//l'id de la légion actuellement selctionné
var actuLegionId;
//Pour savoir ou se situe les armures vide
var armorComp;
//Pour placer les tentes
var tent;
var tentComp;
//Booleen pour savoir si on est dans la version web ou la version android (false par defaut)
var android = false;

// choppe toutes les coordonnées des cases adjacente à la case donné
function getProx(pawnX, pawnY, isHard)
{
	var res = new Array();
	var hard = new Array();

	if(isHard) {
		var x;
		var y;
		var coordX;
		var coordY;

		//on remplis hard avec toutes les coordonnées des armures non équipé
		for(var i=0;i<armor.length;i++)
		{
			if(!armor[i][2]) {
				x = armor[i][0]+size-1;
				y = armor[i][1]+size-1;
				coordX = virtual[x][y][0];
				coordY = virtual[x][y][1];
				hard.push(coordX + ',' + coordY);
			}
		}
	}

	//Si on est pas au bord gauche on choppe la case de gauche
	if(pawnY > 0) {
		if((isHard && jQuery.inArray(virtual[pawnX][pawnY-1][0] +','+ virtual[pawnX][pawnY-1][1], hard) == -1) || !isHard)
		res.push([virtual[pawnX][pawnY-1][0], virtual[pawnX][pawnY-1][1]]);
	}

	//Si on est pas au bord droit on choppe la case de droite
	if(pawnY < virtual[pawnX].length-1) {
		if((isHard && jQuery.inArray(virtual[pawnX][pawnY+1][0] +','+ virtual[pawnX][pawnY+1][1], hard) == -1) || !isHard)
		res.push([virtual[pawnX][pawnY+1][0], virtual[pawnX][pawnY+1][1]]);
	}

	//Si on est pas tout en haut on choppe la case du haut
	if(pawnX > 0) {
		//et si la ligne du dessus à moins de case que la notre et qu'on est en fin de ligne on ne prend pas la case
		if(!(virtual[pawnX-1].length < virtual[pawnX].length && pawnY == virtual[pawnX].length-1)) {
			if((isHard && jQuery.inArray(virtual[pawnX-1][pawnY][0] +','+ virtual[pawnX-1][pawnY][1], hard) == -1) || !isHard)
			res.push([virtual[pawnX-1][pawnY][0], virtual[pawnX-1][pawnY][1]]);
		}
	}

	//Si on est pas tout en bas on choppe la case du bas
	if(pawnX < virtual.length-1) {
		//et si la ligne du dessous à moins de case que la notre et qu'on est en fin de ligne on ne prend pas la case
		if(!(virtual[pawnX+1].length < virtual[pawnX].length && pawnY == virtual[pawnX].length-1)) {
			if((isHard && jQuery.inArray(virtual[pawnX+1][pawnY][0] +','+ virtual[pawnX+1][pawnY][1], hard) == -1) || !isHard)
			res.push([virtual[pawnX+1][pawnY][0], virtual[pawnX+1][pawnY][1]]);
		}
	}

	//Si on est au dessus de la ligne du milieu (ligne comprise) et qu'on est ni tout à gauche ni tout en haut on choppe la case haut gauche
	if(pawnX < size && pawnX > 0 && pawnY > 0) {
		if((isHard && jQuery.inArray(virtual[pawnX-1][pawnY-1][0] +','+ virtual[pawnX-1][pawnY-1][1], hard) == -1) || !isHard)
		res.push([virtual[pawnX-1][pawnY-1][0], virtual[pawnX-1][pawnY-1][1]]);
	}

	//Si on est au dessus de la ligne du milieu (ligne non comprise) on ajoute forcément la case bas droite
	if(pawnX < size-1) {
		if((isHard && jQuery.inArray(virtual[pawnX+1][pawnY+1][0] +','+ virtual[pawnX+1][pawnY+1][1], hard) == -1) || !isHard)
		res.push([virtual[pawnX+1][pawnY+1][0], virtual[pawnX+1][pawnY+1][1]]);
	}

	//Si on est en dessous de la ligne du milieu (ligne comprise) et qu'on est ni tout à gauche ni tout en bas on choppe la case bas gauche
	if(pawnX >= size-1 && pawnX < virtual.length-1 && pawnY > 0) {
		if((isHard && jQuery.inArray(virtual[pawnX+1][pawnY-1][0] +','+ virtual[pawnX+1][pawnY-1][1], hard) == -1) || !isHard)
		res.push([virtual[pawnX+1][pawnY-1][0], virtual[pawnX+1][pawnY-1][1]]);
	}

	//Si on est en dessous de la ligne du milieu (ligne non comprise) on ajoute forcément la case haut droite
	if(pawnX > size-1) {
		if((isHard && jQuery.inArray(virtual[pawnX-1][pawnY+1][0] +','+ virtual[pawnX-1][pawnY+1][1], hard) == -1) || !isHard)
		res.push([virtual[pawnX-1][pawnY+1][0], virtual[pawnX-1][pawnY+1][1]]);
	}

	return res;
}

//choppe toutes les coordonnées des case autour d'un groupe (d'une case donné)
function getZoneProx(pawnX, pawnY, group, isHard)
{
	//Chercher les cases adjacentes à la case
	var res = getProx(pawnX, pawnY, isHard);

	// On met dans pawn les coordonnées des pions pour ne pas selectioner leurs cases
	var pawn = new Array();
	var pawnX;
	var pawnY;
	var coordX;
	var coordY;

	for(var i=0;i<pawns.length;i++)
	{
		pawnX = pawns[i][0]+size-1;
		pawnY = pawns[i][1]+size-1;
		coordX = virtual[pawnX][pawnY][0];
		coordY = virtual[pawnX][pawnY][1];
		pawn.push(coordX + ',' + coordY);
	}

	//pour toutes les cases adjacente à la case clické
	for(var i = 0; i < res.length; i++)
	{
		comp = res[i][0] + ',' + res[i][1];
		if(jQuery.inArray(comp, pawn) != -1) {
			if(jQuery.inArray(comp, group) == -1) {
				if(getPawns(getXVirtual(res[i][0], res[i][1])-size+1, getYVirtual(res[i][0], res[i][1], getXVirtual(res[i][0], res[i][1]))-size+1)[2] == actuGroup) {
					group.push(res[i].join());
					var xVirtual = getXVirtual(res[i][0], res[i][1]);
					var yVirtual = getYVirtual(res[i][0], res[i][1], xVirtual);
					//alert(xVirtual+", "+yVirtual);
					var resTmp = getZoneProx(xVirtual, yVirtual, group, isHard);
					for(j = 0; j < resTmp.length; j++)
					{
						res.push(resTmp[j]);
					}
				}
			}
			res[i][0] = -4*rayon;
			res[i][1] = -4*rayon;
		}
	}

	return res;
	//Faire de même avec toutes les cases occupé adjacente
}

//choppe toutes les coordonnées des case ou le laurier peut se déplacer
function getZoneLaurel(pawnX, pawnY)
{
	var res = new Array();
	//On cherche toutes les cases ou le laurier leut se déplacer (le laurier ne peut se déplacer sur les armures on le concidère donc comme un pions en armure)
	var prox = getProx(pawnX, pawnY, true);
	//On cherche tous les pions à coté du laurier (on regarde aussi sur les cases armures car les pions venant de séquiper d'une armure sont encore concidéré comme étant une armure)
	var detect = getProx(pawnX, pawnY, false);
	var proxyPawn = new Array();

	// On met dans pawn les coordonnées des pions pour trouver lesquels sont à proximité
	var pawn = new Array();
	var pawnX;
	var pawnY;
	var coordX;
	var coordY;

	for(var i = 0; i < pawns.length; i++)
	{
		pawnX = pawns[i][0]+size-1;
		pawnY = pawns[i][1]+size-1;
		coordX = virtual[pawnX][pawnY][0];
		coordY = virtual[pawnX][pawnY][1];
		pawn.push(coordX + ',' + coordY);
	}

	for (var i = 0; i < detect.length; i++)
	{
		if(jQuery.inArray(detect[i][0] + ',' + detect[i][1], pawn) != -1) {
			proxyPawn.push(detect[i]);
		}
	}

	for (var i = 0; i < prox.length; i++)
	{
		if(jQuery.inArray(prox[i][0] + ',' + prox[i][1], pawn) == -1) {
			res.push(prox[i]);
		}
	}

	//On liste les légions autour du laurier
	var legionsDisp = new Array();
	var proxy = new Array();
	for (var i = 0; i < proxyPawn.length; i++)
	{
		//alert("ok => " + proxyPawn[i][2]);
		/*pawnX = getXVirtual(proxyPawn[i][0], proxyPawn[i][1]);
		pawnY = getYVirtual(proxyPawn[i][0], proxyPawn[i][1], pawnX);
		prox = getProx(pawnX, pawnY);
		for (var j = 0; j < prox.length; j++)
		{
			//On vérifie que les cases ne sont pas des pions
			if(jQuery.inArray(prox[j][0] + ',' + prox[j][1], pawn) == -1) {
				res.push(prox[j]);
			}
		}*/
		pawnX = getXVirtual(proxyPawn[i][0], proxyPawn[i][1]);
		pawnY = getYVirtual(proxyPawn[i][0], proxyPawn[i][1], pawnX);
		var tmp = getPawns(pawnX-size+1, pawnY-size+1);
		proxy.push(tmp);
		if(jQuery.inArray(tmp[2], legionsDisp) == -1) {
			legionsDisp.push(tmp[2]);
		}
	}
	// Si on a trouvé qu'un seul type de légion autour du laurier on renvois directement les cases dispo et on assigne la légionId pour le déplacement
	if(legionsDisp.length == 1) {
		actuLegionId = legions[legionsDisp[0]][2];
		return res;
	}
	// Si 2 ou plus
	else if(legionsDisp.length > 1) {
		panelSelectLegion(proxy, res);
	}
	// Si 0
	return new Array();
}

//Pour déplacer le laurier quand il y a plusieurs légion autour, met en subbriance ProxyPawn, quand un proxyPawn est cliqué on met en subriance go avec la légion du proxyPawn
function panelSelectLegion(proxyPawn, go)
{
	//alert(proxyPawn);
	for (var i = 0 ; i < proxyPawn.length ; i++)
	{
		//alert(proxyPawn[i][0] +','+ proxyPawn[i][1]);
		t.setLayer((proxyPawn[i][0]+size-1)+','+(proxyPawn[i][1]+size-1)+'fond', {
			fillStyle: '#FFFFC0',
			click:function(layer){
				//alert("okok");//@todo faire une fonction qui va mettre en subriance les cases go et qui ensuite va remetre le style et les fonction par défaut des cases proxyPawn
				actuLegionId = layer.legionId;
				//laurelGo(proxyPawn, go, layer.legionId);
				laurelGo(proxyPawn, go);

			}
		});
		t.setLayer((proxyPawn[i][0]+size-1)+','+(proxyPawn[i][1]+size-1), {
			click:function(layer){
				//alert("cool2" + layer.x);//@todo dupliquer la fonction au dessus
				actuLegionId = layer.legionId;
				//laurelGo(proxyPawn, go, layer.legionId);
				laurelGo(proxyPawn, go);
			}
		});

	}
}

//function laurelGo(proxyPawn, go, legion)
function laurelGo(proxyPawn, go)
{
	for (var i = 0 ; i < proxyPawn.length ; i++)
	{
		t.setLayer((proxyPawn[i][0]+size-1)+','+(proxyPawn[i][1]+size-1)+'fond', {
			fillStyle: '#FFFFFF',
			click:function(layer){
				//actuLegionId = layer.legionId;
				t.setLayer(layer.pawnX+','+layer.pawnY+'fond', {
					fillStyle: '#FFFFFF',
					click:function(layer){
						actuGroup = layer.group;
						depStart = [(layer.pawnX-size+1), (layer.pawnY-size+1)];
						depEnd = null;
						actuLegionId = layer.legionId;
						t.restoreCanvas();
						var tab = getZoneProx(layer.pawnX, layer.pawnY, new Array(), false);
						for(i = 0; i < tab.length; i++)
						{
							t.drawPolygon({
								fillStyle: 'rgba(200, 200, 255, 0.3)',
								strokeStyle: 'black',
								strokeWidth: 2,
								x: tab[i][0],
								y: tab[i][1],
								radius: rayon,
								sides: 6,
								rotate: 90
							});
						}
						//harden();
					}
				});
			}
		});
		t.setLayer((proxyPawn[i][0]+size-1)+','+(proxyPawn[i][1]+size-1), {
			click:function(layer){
				//actuLegionId = layer.legionId;
				t.setLayer(layer.pawnX+','+layer.pawnY, {
					click:function(layer){
						actuGroup = layer.group;
						depStart = [(layer.pawnX-size+1), (layer.pawnY-size+1)];
						depEnd = null;
						actuLegionId = layer.legionId;
						t.restoreCanvas();
						var tab = getZoneProx(layer.pawnX, layer.pawnY, new Array(), false);
						for(i = 0; i < tab.length; i++)
						{
							t.drawPolygon({
								fillStyle: 'rgba(200, 200, 255, 0.3)',
								strokeStyle: 'black',
								strokeWidth: 2,
								x: tab[i][0],
								y: tab[i][1],
								radius: rayon,
								sides: 6,
								rotate: 90
							});
						}
						//harden();
					}
				});
			}
		});
	}

	//actuLegionId = legion;
	//t.restoreCanvas();
	for(var i = 0; i < go.length; i++)
	{
		t.drawPolygon({
			fillStyle: 'rgba(200, 200, 255, 0.3)',
			strokeStyle: 'black',
			strokeWidth: 2,
			x: go[i][0],
			y: go[i][1],
			radius: rayon,
			sides: 6,
			rotate: 90
		});
	}
	//harden();
}


//Detemine le X (la ligne) de la case grace à sa position en px
function getXVirtual(x, y)
{
	//alert("XY  "+x+", "+y);
	var virtualComp = new Array();
	for(var i = 0; i < virtual.length; i++)
	{
		virtualComp[i] = new Array();
		for(var j = 0; j < virtual[i].length; j++)
		{
			//alert("X:"+i+" : "+virtual[i][j].join());
			virtualComp[i].push(virtual[i][j].join());
		}
	}

	for(var i = 0; i < virtual.length; i++)
	{
		if(jQuery.inArray(x+','+y, virtualComp[i]) != -1) {
			return i;
		}
	}
	return -1;
}

//Determine le Y (la colone) de la case grace à sa position en px et son numéro de ligne
function getYVirtual(x, y, virtualX)
{
	if(virtualX == -1) {
		return -1;
	}
	var virtualComp = new Array();
	for(var i = 0; i < virtual[virtualX].length; i++)
	{
		virtualComp.push(virtual[virtualX][i].join());
	}

	for(var i = 0; i < virtual[virtualX].length; i++)
	{
		if(x+','+y == virtualComp[i]) {
			return i;
		}
	}
	return -1;
}

function getPawns(u, v)
{
	for(var i = 0; i < pawns.length; i++)
	{
		if(pawns[i][0] == u && pawns[i][1] == v) {
			return pawns[i];
		}
	}
}

function oneHundredPercent(first)
{
	t.removeLayers();
	t.clearCanvas();
	t.attr('width', t.parent().width());
	t.attr('height', t.parent().height());

	if($("#divZoom").length == 0) {
		var divZoom = t.parent().clone();
		divZoom.html('');
		divZoom.attr('id', "divZoom");
		divZoom.appendTo(t.parent());
		t.appendTo(divZoom);
		//divZoom.parent().css('overflow', 'scroll');
	}

	if(first) {
		$( window ).resize(function() {
			t.boardCreate(size, pawns, legions, playerGroup, armor, tent, new Array());
			//harden();
		});
	}
}

function harden()
{
	for (var i = 0; i < armor.length; i++)
	{
		var pawn = new Array();
		var pawnX;
		var pawnY;
		var coordXPawn;
		var coordYPawn;

		for(var j = 0; j < pawns.length; j++)
		{
			pawnX = pawns[j][0]+size-1;
			pawnY = pawns[j][1]+size-1;
			coordXPawn = virtual[pawnX][pawnY][0];
			coordYPawn = virtual[pawnX][pawnY][1];
			pawn.push(coordXPawn + ',' + coordYPawn);
		}

		var armorX = armor[i][0]+size-1;
		var armorY = armor[i][1]+size-1;
		var coordX = virtual[armorX][armorY][0];
		var coordY = virtual[armorX][armorY][1];

		var roomId;
		if(android) {
			roomId = localStorage.roomId;
		}
		else {
			roomId = save_game_config.room_id;
		}
		
		//Si l'armure n'est placer sur aucun pion
		if(jQuery.inArray(coordX + ',' + coordY, pawn) == -1) {
			pawnX = getXVirtual(coordX, coordY);
			pawnY = getYVirtual(coordX, coordY, pawnX);
			t.drawPolygon({
				name: pawnX+','+pawnY+'armor',
				pawnX: pawnX,
				pawnY: pawnY,
				layer: true,
				fillStyle: '#FFFFFF',
				strokeStyle: 'black',
				strokeWidth: rayon/12,
				x: coordX,
				y: coordY,
				radius: rayon/5,
				sides: 6,
				rotate: 90,
				click: function(layer) {
					depEnd = [layer.pawnX-size+1, layer.pawnY-size+1];
					var json = JSON.stringify(
					{
				        "command": "GAME_MOVE",
				        "room_id": roomId,
				        "start_u": depStart[0],
				        "start_w": depStart[1],
				        "end_u": depEnd[0],
				        "end_w": depEnd[1],
				        "legion_id": actuLegionId
				    });
					sendText(json);
				}
			});
		}
		else {
			pawnX = getXVirtual(coordX, coordY);
			pawnY = getYVirtual(coordX, coordY, pawnX);
			//var color = t.getLayer((pawnX+','+pawnY)).fillStyle;
			t.setLayer(pawnX+','+pawnY, {
				strokeWidth: rayon/4,
				//strokeStyle: color, 
				//fillStyle: 'black',
				hard: true
			}).drawLayers();
		}
	}
}

function keyPress(e)
{
	keys.push(e.keyCode);
	if (keys.toString().substr(keys.toString().length-29, keys.toString().length) == "38,38,40,40,37,39,37,39,66,65") {
		t.resPawn();
	}
}

jQuery.fn.extend({
	initBoard: function(data)
	{
		//Pour savoir si on est dans la version android il suffis de regarder d'autres éléments dans la page qui diffèrent selon les versions
		if($("#buttonReductMenu").length == 0 && $("#buttonReductChat").length == 0) {
			android = true;
		}
		else {
			$("#buttonReductMenu").click(function() {
				t.boardCreate(size, pawns, legions, playerGroup, armor, tent, new Array());
			});
			$("#buttonReductChat").click(function() {
				t.boardCreate(size, pawns, legions, playerGroup, armor, tent, new Array());
			});
		}
		
		t = $(this);
		//Ce tableau nous servira à savoir à quelle légion appartient un pions
		var tabTypeLegion = new Array();
		// tableau des pions
		var pions = new Array();
		// tableau des armures
		var armor = new Array();
		// tableau des legions
		var legions = new Array();
		// tableau des tentes
		tent = new Array();
		// tableau des mouvements
		var move = new Array();
		
		armorComp = new Array();
		
		tentComp = new Array();
		for (var i = 0 ; i < data.board.length ; i++)
		{
			// soldat
			if (data.board[i].type == "soldier")
			{
				var nLegion = jQuery.inArray(data.board[i].legion_shape + data.board[i].legion_color, tabTypeLegion);
				//Dans se cas ce type de légion n'a pas encore été rencontré on l'ajoute au tableau
				if(nLegion == -1) {
					tabTypeLegion.push(data.board[i].legion_shape + data.board[i].legion_color);
					nLegion = tabTypeLegion.length-1;

					legions.push([data.board[i].legion_color, data.board[i].legion_shape, data.board[i].legion_id]);
				}
				pions.push([data.board[i].u, data.board[i].w, nLegion]);
				if(data.board[i].legion_armor) {
					armor.push([data.board[i].u, data.board[i].w, true]);
				}
			}
			// armure
			else if (data.board[i].type == "armor")
			{
				armor.push([data.board[i].u, data.board[i].w, false]);
				armorComp.push(data.board[i].u +','+ data.board[i].w);
			}
			// laurier
			else if (data.board[i].type == "laurel")
			{
				pions.push([data.board[i].u, data.board[i].w, -1]);
			}
			
			if(typeof data.board[i].tent_color != 'undefined') {
				tent.push([data.board[i].u, data.board[i].w, data.board[i].tent_color]);
				tentComp.push(data.board[i].u +','+ data.board[i].w);
			}
		}
		if(typeof data.moves != 'undefined') {
			for (var i = 0 ; i < data.moves.length ; i++)
			{
				move.push([data.moves[i].destroyed, data.moves[i].start_u, data.moves[i].start_w, data.moves[i].end_u, data.moves[i].end_w]);
			}
		}
		if(typeof data.battles != 'undefined') {
			for (var i = 0 ; i < data.battles.length ; i++)
			{
				move.push([true, data.battles[i].loser_u, data.battles[i].loser_w, data.battles[i].loser_u, data.battles[i].loser_w]);
			}
		}
		if(typeof data.tenailles != 'undefined') {
			for (var i = 0 ; i < data.tenailles.length ; i++)
			{
				var start;
				var end;
				if(data.tenailles[i].front1_w <= data.tenailles[i].front2_w) {
					start = [data.tenailles[i].front1_u, data.tenailles[i].front1_w];
					end = [data.tenailles[i].front2_u, data.tenailles[i].front2_w];
				}
				else
				{
					start = [data.tenailles[i].front2_u, data.tenailles[i].front2_w];
					end = [data.tenailles[i].front1_u, data.tenailles[i].front1_w];
				}
				while((start[0]+','+start[1]) != (end[0]+','+end[1])) {
					
					if(start[0] > end[0]) {
						start[0]--;
					}
					else if(start[0] < end[0]) {
						start[0]++;
					}
					if(start[1] > end[1]) {
						start[1]--;
					}
					else if(start[1] < end[1]) {
						start[1]++;
					}
					//alert(start[0] +', '+ start[1]);
					if((start[0]+','+start[1]) != (end[0]+','+end[1])) {
						move.push([true, start[0], start[1], start[0], start[1]]);
					}
				}
			}
		}
		this.boardCreate(data.informations.board_size, pions, legions, 0, armor, tent, move);
	},
	boardCreate: function(s, pawn, legion, nGroup, arm, tent, move)
	{
		oneHundredPercent(first);
		first = false;

		size = s;
		pawns = pawn;
		legions = legion;
		playerGroup = nGroup;
		armor = arm;
		
		rayon = (t.width()-2)/(Math.sqrt(3)*(2*size-1));
		if(rayon > (t.height()-2)/(3*size-1)) {
			rayon = (t.height()-2)/(3*size-1);
		}
		// diametre d'une case = la taille du canvas / le nombre de case sur la plus grande ligne (size*2-1)

		//création de plateau abstrait (il contiendra les coordonnés des cases en x, y)
		virtual = new Array();
		for(i = 0; i < size*2-1; i++)
		{
			virtual[i] = new Array();
			if(i < size) {
				for(j = 0; j < size + i; j++)
				{
					virtual[i][j] = new Array();
				}
			}
			else {
				for(j=0; j < (size*2-1) - (i+1)%size; j++)
				{
					virtual[i][j] = new Array();
				}
			}
		}

		//@TODO faire une fonction de zoom et de déplacement

		var xStart = (t.width()-Math.sqrt(3)*rayon*(size*2-1))/2+(Math.sqrt(3)*rayon/2*size-1)+2;
		var yStart = (t.height()-2-(size*rayon*2+(size-1)*rayon))/2+rayon+2;

		// Utilisation du clavier pour un futur zoom ?
		jQuery(document).keydown(keyPress);

		//boucle de création du plateau
		for(i = 0; i < size*2-1; i++)
		{
			if(i < size) {
				for(j = 0; j < size + i; j++)
				{
					virtual[i][j][0] = xStart + j*(rayon*2 - (rayon - Math.sqrt((rayon*rayon)-((rayon/2)*(rayon/2))))*2) - i*(rayon-(rayon-Math.sqrt(rayon*rayon-(rayon/2)*(rayon/2))));
					virtual[i][j][1] = yStart + i*(rayon*2 - rayon/2);

					virtual[size*2-2-i][j][0] = virtual[i][j][0];

					var caseColor;
					var caseBig;
					if(jQuery.inArray((i-size+1) +','+ (j-size+1), tentComp) != -1) {
						caseColor = tent[jQuery.inArray((i-size+1) +','+ (j-size+1), tentComp)][2];
						caseBig = rayon*2/3;
					}
					else {
						caseColor = 'black';
						caseBig = rayon;
					}
					
					var roomId;
					if(android) {
						roomId = localStorage.roomId;
					}
					else {
						roomId = save_game_config.room_id;
					}
					
					t.drawPolygon({
						coordX: i,
						coordY: j,
						layer: true,
						fillStyle: '#FFFFFF',
						strokeStyle: caseColor,
						strokeWidth: 2,
						x: virtual[i][j][0],
						y: virtual[i][j][1],
						radius: caseBig,
						sides: 6,
						rotate: 90,
						click: function(layer) {
						depEnd = [layer.coordX-size+1, layer.coordY-size+1];
						var json = JSON.stringify(
						{
					        "command": "GAME_MOVE",
					        "room_id": roomId,
					        "start_u": depStart[0],
					        "start_w": depStart[1],
					        "end_u": depEnd[0],
					        "end_w": depEnd[1],
					        "legion_id": actuLegionId
					    });
					    //alert(depStart+" -> "+depEnd);
						sendText(json);
						}
					});
				}
			}
			else {
				for(j=0; j < (size*2-1) - (i+1)%size; j++)
				{
					virtual[i][j][1] = yStart + i*(rayon*2 - rayon/2);
					
					var caseColor;
					var caseBig;
					if(jQuery.inArray((i-size+1) +','+ (j-size+1), tentComp) != -1) {
						caseColor = tent[jQuery.inArray((i-size+1) +','+ (j-size+1), tentComp)][2];
						caseBig = rayon*2/3;
					}
					else {
						caseColor = 'black';
						caseBig = rayon;
					}
					
					var roomId;
					if(android) {
						roomId = localStorage.roomId;
					}
					else {
						roomId = save_game_config.room_id;
					}
					
					t.drawPolygon({
						coordX: i,
						coordY: j,
						layer: true,
						fillStyle: '#FFFFFF',
						strokeStyle: caseColor,
						strokeWidth: 2,
						x: virtual[i][j][0],
						y: virtual[i][j][1],
						radius: caseBig,
						sides: 6,
						rotate: 90,
						click: function(layer) {
							depEnd = [layer.coordX-size+1, layer.coordY-size+1];
							var json = JSON.stringify(
						{
					        "command": "GAME_MOVE",
					        "room_id": roomId,
					        "start_u": depStart[0],
					        "start_w": depStart[1],
					        "end_u": depEnd[0],
					        "end_w": depEnd[1],
					        "legion_id": actuLegionId
					    });
					    //alert(depStart+" -> "+depEnd);
						sendText(json);
						}
					});
				}
			}
		}
		this.resPawn();
		this.move(move);
		t.saveCanvas();
	},

	resPawn: function()
	{
		for(var i = 0; i < pawns.length; i++)
		{
			var pawnX = pawns[i][0]+size-1;
			var pawnY = pawns[i][1]+size-1;
			var coordX = virtual[pawnX][pawnY][0];
			var coordY = virtual[pawnX][pawnY][1];
			
			var color;
			var shape;
			var legionId;
			if(pawns[i][2] >= 0) {
				color = legions[pawns[i][2]][0];

			 	if(legions[pawns[i][2]][1] == 'square') {
					shape = 4;
				}
				else if(legions[pawns[i][2]][1] == 'triangle') {
					shape = 3;
				}
				else {
					shape = 6;
				}
				legionId = legions[pawns[i][2]][2];
			}


			var group = pawns[i][2];
			

			//le fond du pion (pour qu'on puisse activer la même fonction peu importe à quelle endroit on a cliqué sur la case du pion)
			t.drawPolygon({
				name: pawnX+','+pawnY+'fond',
				hard:false,
				pawnX: pawnX,
				pawnY: pawnY,
				legionId: legionId,
				group: group,
				layer: true,
				fillStyle: 'rgba(255, 255, 255, 0.05)',
				//strokeStyle: caseColor,
				//strokeWidth: 2,
				x: coordX,
				y: coordY,
				radius: rayon-2,
				sides: 6,
				rotate: 90,
				click: function(layer) {
		/*var start = new Date().getTime();*/
					//t.removeLayers();
					/*t.clearCanvas();
					t.boardCreate(size, pawns, legions);*/
					//Rajouter cette condition pour ne controler qu'une légion
					//if(layer.group == playerGroup) {
						actuGroup = layer.group;
						depStart = [(layer.pawnX-size+1), (layer.pawnY-size+1)];
						depEnd = null;
						actuLegionId = layer.legionId;
						t.restoreCanvas();
						var tab = getZoneProx(layer.pawnX, layer.pawnY, new Array(), layer.hard);
						for(i = 0; i < tab.length; i++)
						{
							t.drawPolygon({
								fillStyle: 'rgba(200, 200, 255, 0.3)',
								strokeStyle: 'black',
								strokeWidth: 2,
								x: tab[i][0],
								y: tab[i][1],
								radius: rayon,
								sides: 6,
								rotate: 90
							});
						}
						//harden();
					//}
		/*var end = new Date().getTime();
		alert(end - start);*/
				}
			});
			//Dans le cas d'un pion
			if(pawns[i][2] >= 0) {
				t.drawPolygon({
					name: pawnX+','+pawnY,
					hard:false,
					pawnX: pawnX,
					pawnY: pawnY,
					legionId: legionId,
					group: group,
					layer: true,
					fillStyle: color,
					strokeStyle: 'black',
					strokeWidth: 2,
					x: coordX,
					y: coordY,
					radius: rayon/2,
					sides: shape,
					rotate: 90,
					click: function(layer) {
		/*var start = new Date().getTime();*/
						//t.removeLayers();
						/*t.clearCanvas();
						t.boardCreate(size, pawns, legions);*/
						//if(layer.group == playerGroup) {
							actuGroup = layer.group;
							depStart = [(layer.pawnX-size+1), (layer.pawnY-size+1)];
							depEnd = null;
							actuLegionId = layer.legionId;
							t.restoreCanvas();
							var tab = getZoneProx(layer.pawnX, layer.pawnY, new Array(), layer.hard);
							for(i = 0; i < tab.length; i++)
							{
								t.drawPolygon({
									fillStyle: 'rgba(200, 200, 255, 0.3)',
									strokeStyle: 'black',
									strokeWidth: 2,
									x: tab[i][0],
									y: tab[i][1],
									radius: rayon,
									sides: 6,
									rotate: 90
								});
							}
							//harden();
						//}
		/*var end = new Date().getTime();
		alert(end - start);*/
					}
				});
			}
			//Dans le cas du laurier
			else {
				var source;
				if (keys.toString().substr(keys.toString().length-29, keys.toString().length) == "38,38,40,40,37,39,37,39,66,65") {
				    if(android) {
						source = 'script/plateau/138.png';
					}
					else {
						source = 'js/plateau/138.png';
					}
				}
				else {
					if(android) {
						source = 'script/plateau/laurel.png';
					}
					else {
						source = 'js/plateau/laurel.png';
					}
				}
				t.drawImage({
					name: pawnX+','+pawnY,
					pawnX: pawnX,
					pawnY: pawnY,
					source: source,
					layer: true,
					x: coordX,
					y: coordY,
					width: rayon*3/2,
 					height: rayon*3/2,
					click: function(layer) {
		/*var start = new Date().getTime();*/
						//t.removeLayers();
						/*t.clearCanvas();
						t.boardCreate(size, pawns, legions);*/
						depStart = [(layer.pawnX-size+1), (layer.pawnY-size+1)];
						depEnd = null;
						t.restoreCanvas();
						var tab = getZoneLaurel(layer.pawnX, layer.pawnY);
						for(i = 0; i < tab.length; i++)
						{
							t.drawPolygon({
								fillStyle: 'rgba(200, 200, 255, 0.3)',
								strokeStyle: 'black',
								strokeWidth: 2,
								x: tab[i][0],
								y: tab[i][1],
								radius: rayon,
								sides: 6,
								rotate: 90
							});
						}
						//harden();
		/*var end = new Date().getTime();
		alert(end - start);*/
					}
				});
			}
		}
		harden();
	},

	getDeplacement: function()
	{
		return [depStart, depEnd, actuLegionId];
	},

	move: function(move)
	{
		for(var i = 0; i < move.length; i++)
		{
			var coord = virtual[move[i][3]+size-1][move[i][4]+size-1];
			//alert((move[i][1])+','+(move[i][2]));
			t.moveLayer((move[i][1]+size-1)+','+(move[i][2]+size-1)+'fond', -1);
			//si le pion est détruit
			if(move[i][0]) {
				t.animateLayer((move[i][1]+size-1)+','+(move[i][2]+size-1)+'fond', {
				  x: coord[0], y: coord[1], pawnX: move[i][3]+size-1, pawnY: move[i][4]+size-1, radius: 0
				}, 0);
			}
			else {
				t.animateLayer((move[i][1]+size-1)+','+(move[i][2]+size-1)+'fond', {
				  x: coord[0], y: coord[1], pawnX: move[i][3]+size-1, pawnY: move[i][4]+size-1
				}, 0);
			}
			
			t.moveLayer((move[i][1]+size-1)+','+(move[i][2]+size-1), -1);
			//si on se déplace sur une armure vide
			/*alert(armorComp);
			alert(move[i][3] +','+ move[i][4]);*/
			if(jQuery.inArray(move[i][3] +','+ move[i][4], armorComp) != -1) {

				//On supprime l'armure
				var coliDetect = 0;
				for(var k = 0; k < armor.length; k++)
				{
					if(move[i][3]+','+move[i][4] == armor[k][0]+','+armor[k][1]) {
						coliDetect++;
					}
				}
				//alert([move[i][3], move[i][4], false] + ' => ' + armor);
				if(coliDetect == 1) {
					t.removeLayer((move[i][3]+size-1)+','+(move[i][4]+size-1+'armor'));
				}
				//si le pion est détruit
				if(move[i][0]) {
					//var color = t.getLayer((move[i][1]+size-1)+','+(move[i][2]+size-1)).fillStyle;
					t.animateLayer((move[i][1]+size-1)+','+(move[i][2]+size-1), {
					  x: coord[0], y: coord[1], pawnX: move[i][3]+size-1, pawnY: move[i][4]+size-1, strokeWidth: rayon/4, radius: 0//, strokeStyle: color, fillStyle: 'black'
					}, 1000,
					function(layer) {
						//harden();
					});
				}
				else {
					//var color = t.getLayer((move[i][1]+size-1)+','+(move[i][2]+size-1)).fillStyle;
					t.animateLayer((move[i][1]+size-1)+','+(move[i][2]+size-1), {
					  x: coord[0], y: coord[1], pawnX: move[i][3]+size-1, pawnY: move[i][4]+size-1, strokeWidth: rayon/4//, strokeStyle: color, fillStyle: 'black'
					}, 1000,
					function(layer) {
						//harden();
					});
				}
				t.setLayer((move[i][1]+size-1)+','+(move[i][2]+size-1), {
				  hard: true
				}, 1000,
				function(layer) {
					//harden();
				});
			}
			else {
				//si le pion est détruit
				if(move[i][0]) {
					t.animateLayer((move[i][1]+size-1)+','+(move[i][2]+size-1), {
					  x: coord[0], y: coord[1], pawnX: move[i][3]+size-1, pawnY: move[i][4]+size-1, radius: 0
					}, 1000,
					function(layer) {
						//harden();
					});
				}
				else {
					t.animateLayer((move[i][1]+size-1)+','+(move[i][2]+size-1), {
					  x: coord[0], y: coord[1], pawnX: move[i][3]+size-1, pawnY: move[i][4]+size-1
					}, 1000,
					function(layer) {
						//harden();
					});
				}

			}

			for(var j = 0; j < pawns.length; j++)
			{
				if(pawns[j][0] == move[i][1] && pawns[j][1] == move[i][2]) {
					//si le pion est détruit
					if(move[i][0]) {
						pawns.splice(j, 1);
					}
					else {
						//pawns[j] = [move[i][3], move[i][4], pawns[j][2]];
						pawns[j][0] = move[i][3];
						pawns[j][1] = move[i][4];

						t.setLayer((move[i][1]+size-1)+','+(move[i][2]+size-1)+'fond', {
							name: (move[i][3]+size-1)+','+(move[i][4]+size-1)+'fond'
						});
						t.setLayer((move[i][1]+size-1)+','+(move[i][2]+size-1), {
							name: (move[i][3]+size-1)+','+(move[i][4]+size-1)
						});
					}
				}
			}
			for(var j = 0; j < armor.length; j++)
			{
				if(armor[j][0] == move[i][1] && armor[j][1] == move[i][2]) {
					//si le pion est détruit
					if(move[i][0]) {
						//on supprime l'armure que si elle est prise
						if(armor[i][2]) {
							armor.splice(j, 1);
						}
					}
					else {
						armor[j] = [move[i][3], move[i][4], armor[j][2]];
					}
				}
			}
		}
	},

	zoom: function()
	{
		t.parent().width(t.parent().width()*1.5);
		t.parent().height(t.parent().height()*1.5);
		t.parent().parent().scrollTop((t.parent().height() - t.parent().parent().height()) / 2);
		t.parent().parent().scrollLeft((t.parent().width() - t.parent().parent().width()) / 2);
		t.boardCreate(size, pawns, legions, playerGroup, armor, tent, new Array());
	},

	dezoom: function()
	{
		t.parent().width(t.parent().width()/1.5);
		t.parent().height(t.parent().height()/1.5);
		t.parent().parent().scrollTop((t.parent().height() - t.parent().parent().height()) / 2);
		t.parent().parent().scrollLeft((t.parent().width() - t.parent().parent().width()) / 2);
		t.boardCreate(size, pawns, legions, playerGroup, armor, tent, new Array());
	}
});