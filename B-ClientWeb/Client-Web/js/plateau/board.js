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
//la liste des armures
var armor;
//clavier
var keys = new Array();
//Deplacement départ
var depStart;
//Déplacement arrivé
var depEnd;

// choppe toutes les coordonnées des cases adjacente à la case donné
function getProx(pawnX, pawnY)
{
	var res = new Array();

	//Si on est pas au bord gauche on choppe la case de gauche
	if(pawnY > 0) {
		res.push([virtual[pawnX][pawnY-1][0], virtual[pawnX][pawnY-1][1]]);
	}

	//Si on est pas au bord droit on choppe la case de droite
	if(pawnY < virtual[pawnX].length-1) {
		res.push([virtual[pawnX][pawnY+1][0], virtual[pawnX][pawnY+1][1]]);
	}

	//Si on est pas tout en haut on choppe la case du haut
	if(pawnX > 0) {
		//et si la ligne du dessus à moins de case que la notre et qu'on est en fin de ligne on ne prend pas la case
		if(!(virtual[pawnX-1].length < virtual[pawnX].length && pawnY == virtual[pawnX].length-1)) {
			res.push([virtual[pawnX-1][pawnY][0], virtual[pawnX-1][pawnY][1]]);
		}
	}

	//Si on est pas tout en bas on choppe la case du bas
	if(pawnX < virtual.length-1) {
		//et si la ligne du dessous à moins de case que la notre et qu'on est en fin de ligne on ne prend pas la case
		if(!(virtual[pawnX+1].length < virtual[pawnX].length && pawnY == virtual[pawnX].length-1)) {
			res.push([virtual[pawnX+1][pawnY][0], virtual[pawnX+1][pawnY][1]]);
		}
	}

	//Si on est au dessus de la ligne du milieu (ligne comprise) et qu'on est ni tout à gauche ni tout en haut on choppe la case haut gauche
	if(pawnX < size && pawnX > 0 && pawnY > 0) {
		res.push([virtual[pawnX-1][pawnY-1][0], virtual[pawnX-1][pawnY-1][1]]);
	}

	//Si on est au dessus de la ligne du milieu (ligne non comprise) on ajoute forcément la case bas droite
	if(pawnX < size-1) {
		res.push([virtual[pawnX+1][pawnY+1][0], virtual[pawnX+1][pawnY+1][1]]);
	}

	//Si on est en dessous de la ligne du milieu (ligne comprise) et qu'on est ni tout à gauche ni tout en bas on choppe la case bas gauche
	if(pawnX >= size-1 && pawnX < virtual.length-1 && pawnY > 0) {
		res.push([virtual[pawnX+1][pawnY-1][0], virtual[pawnX+1][pawnY-1][1]]);
	}

	//Si on est en dessous de la ligne du milieu (ligne non comprise) on ajoute forcément la case haut droite
	if(pawnX > size-1) {
		res.push([virtual[pawnX-1][pawnY+1][0], virtual[pawnX-1][pawnY+1][1]]);
	}

	return res;
}

//choppe toutes les coordonnées des case autour d'un groupe (d'une case donné)
function getZoneProx(pawnX, pawnY, group)
{
	//Chercher les cases adjacentes à la case
	var res = getProx(pawnX, pawnY);

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
				if(getPawns(getXVirtual(res[i][0], res[i][1])-size+1, getYVirtual(res[i][0], res[i][1], getXVirtual(res[i][0], res[i][1]))-size+1)[2] == playerGroup) {
					group.push(res[i].join());
					var xVirtual = getXVirtual(res[i][0], res[i][1]);
					var yVirtual = getYVirtual(res[i][0], res[i][1], xVirtual);
					//alert(xVirtual+", "+yVirtual);
					var resTmp = getZoneProx(xVirtual, yVirtual, group);
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
	//On cherche tous les pions de notre groupe à proximité du laurié
	var prox = getProx(pawnX, pawnY);
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

	for (var i =0; i < prox.length; i++)
	{
		if(jQuery.inArray(prox[i][0] + ',' + prox[i][1], pawn) != -1) {
			proxyPawn.push(prox[i]);
		}
	}

	//On trouve toutes les cases vide qui touchent directement uniquement les pions trouvé
	for (var i = 0; i < proxyPawn.length; i++)
	{
		pawnX = getXVirtual(proxyPawn[i][0], proxyPawn[i][1]);
		pawnY = getYVirtual(proxyPawn[i][0], proxyPawn[i][1], pawnX);
		prox = getProx(pawnX, pawnY);
		for (var j = 0; j < prox.length; j++)
		{
			//On vérifie que les cases ne sont pas des pions
			if(jQuery.inArray(prox[j][0] + ',' + prox[j][1], pawn) == -1) {
				res.push(prox[j]);
			}
		}
	}
	return res;
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

	if(first) {
		$( window ).resize(function() {
			t.boardCreate(size, pawns, legions, playerGroup, armor);
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

			//Si l'armure n'est placer sur aucun pion
			if(jQuery.inArray(coordX + ',' + coordY, pawn) == -1) {
				pawnX = getXVirtual(coordX, coordY);
				pawnY = getYVirtual(coordX, coordY, pawnX);
				t.drawPolygon({
					pawnX: pawnX,
					pawnY: pawnY,
					layer: true,
					fillStyle: '#FFFFFF',
					strokeStyle: 'black',
					strokeWidth: 8,
					x: coordX,
					y: coordY,
					radius: rayon/2,
					sides: 4,
					rotate: 0,
					click: function(layer) {
						depEnd = [layer.pawnX-size+1, layer.pawnY-size+1];
						alert(depStart + ' -> ' + depEnd);
					}
				});
			}
			else {
				pawnX = getXVirtual(coordX, coordY);
				pawnY = getYVirtual(coordX, coordY, pawnX);
				var p = getPawns(pawnX-size+1, pawnY-size+1);
				var group = p[2];
				var color = legions[group][0];
				var shape;
				if(pawns[i][2] >= 0) {
					if(legions[group][1] == 'square') {
						shape = 4;
					}
					else {
						shape = 6;
					}
				}
				if(p[2] >= 0) {
					t.drawPolygon({
						pawnX: pawnX,
						pawnY: pawnY,
						group: group,
						layer: true,
						fillStyle: color,
						strokeStyle: 'black',
						strokeWidth: 8,
						x: coordX,
						y: coordY,
						radius: rayon/2,
						sides: shape,
						rotate: 90,
						click: function(layer) {
							if(layer.group == playerGroup) {
								depStart = [(layer.pawnX-size+1), (layer.pawnY-size+1)];
								depEnd = null;
								t.restoreCanvas();
								var tab = getZoneProx(layer.pawnX, layer.pawnY, new Array());
								for(i = 0; i < tab.length; i++)
								{
									t.drawPolygon({
										fillStyle: '#BBBBFF',
										strokeStyle: 'black',
										strokeWidth: 2,
										x: tab[i][0],
										y: tab[i][1],
										radius: rayon,
										sides: 6,
										rotate: 90
									});
								}
								harden();
							}
						}
					});
				}
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
		t = $(this);
		//Ce tableau nous servira à savoir à quelle légion appartient un pions
		var tabTypeLegion = new Array();
		// tableau des pions
		var pions = new Array();
		// tableau des armures
		var armor = new Array();
		// tableau des legions
		var legions = new Array();
		for (var i = 0 ; i < data.board.length ; i++)
		{
			// soldat
			if (data.board[i].type == "auguste.engine.entity.pawn.soldier")
			{
				var nLegion = jQuery.inArray(data.board[i].legion_shape + data.board[i].legion_color, tabTypeLegion);
				//Dans se cas ce type de légion n'a pas encore été rencontré on l'ajoute au tableau
				if(nLegion == -1) {
					tabTypeLegion.push(data.board[i].legion_shape + data.board[i].legion_color);
					nLegion = tabTypeLegion.length-1;

					legions.push([data.board[i].legion_color, data.board[i].legion_shape]);
				}
				pions.push([data.board[i].u, data.board[i].w, nLegion]);
			}
			// armure
			else if (data.board[i].type == "auguste.engine.entity.pawn.armor")
			{
				armor.push([data.board[i].u, data.board[i].w]);
			}
			// laurier
			else if (data.board[i].type == "auguste.engine.entity.pawn.laurel")
			{
				pions.push([data.board[i].u, data.board[i].w, -1]);
			}
		}
		this.boardCreate(5, pions, legions, 0, armor);
	},
	boardCreate: function(s, pawn, legion, nGroup, arm) {
		oneHundredPercent(first);
		first = false;

		size = s;
		pawns = pawn;
		legions = legion;
		playerGroup = nGroup;
		armor = arm;
		
		rayon = (t.width()-2)/(Math.sqrt(3)*(2*size-1));
		if(rayon > t.height()/(3*size-1)) {
			rayon = t.height()/(3*size-1);
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

		//@TODO faire une fonction de zomm et de déplacement

		var xStart = (t.width()-Math.sqrt(3)*rayon*(size*2-1))/2+(Math.sqrt(3)*rayon/2*size-1)+2;
		var yStart = (t.height()-(size*rayon*2+(size-1)*rayon))/2+rayon;

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

					t.drawPolygon({
						coordX: i,
						coordY: j,
						layer: true,
						fillStyle: '#FFFFFF',
						strokeStyle: 'black',
						strokeWidth: 2,
						x: virtual[i][j][0],
						y: virtual[i][j][1],
						radius: rayon,
						sides: 6,
						rotate: 90,
						click: function(layer) {
							depEnd = [layer.coordX-size+1, layer.coordY-size+1];
							alert(depStart + ' -> ' + depEnd);
						}
					});
				}
			}
			else {
				for(j=0; j < (size*2-1) - (i+1)%size; j++)
				{
					virtual[i][j][1] = yStart + i*(rayon*2 - rayon/2);

					t.drawPolygon({
						coordX: i,
						coordY: j,
						layer: true,
						fillStyle: '#FFFFFF',
						strokeStyle: 'black',
						strokeWidth: 2,
						x: virtual[i][j][0],
						y: virtual[i][j][1],
						radius: rayon,
						sides: 6,
						rotate: 90,
						click: function(layer) {
							depEnd = [layer.coordX-size+1, layer.coordY-size+1];
							alert(depStart + ' -> ' + depEnd);
						}
					});
				}
			}
		}
		this.resPawn();
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
			if(pawns[i][2] >= 0) {
				color = legions[pawns[i][2]][0];

			 	if(legions[pawns[i][2]][1] == 'square') {
					shape = 4;
				}
				else {
					shape = 6;
				}
			}

			var group = pawns[i][2];

			//le fond du pion (pour qu'on puisse activer la même fonction peu importe à quelle endroit on a cliqué sur la case du pion)
			t.drawPolygon({
				pawnX: pawnX,
				pawnY: pawnY,
				group: group,
				layer: true,
				fillStyle: '#FFFFFF',
				strokeStyle: 'black',
				strokeWidth: 2,
				x: coordX,
				y: coordY,
				radius: rayon,
				sides: 6,
				rotate: 90,
				click: function(layer) {
		/*var start = new Date().getTime();*/
					//t.removeLayers();
					/*t.clearCanvas();
					t.boardCreate(size, pawns, legions);*/
					//Rajouter cette condition pour ne controler qu'une légion
					//if(layer.group == playerGroup) {
						depStart = [(layer.pawnX-size+1), (layer.pawnY-size+1)];
						depEnd = null;
						t.restoreCanvas();
						var tab = getZoneProx(layer.pawnX, layer.pawnY, new Array());
						for(i = 0; i < tab.length; i++)
						{
							t.drawPolygon({
								fillStyle: '#BBBBFF',
								strokeStyle: 'black',
								strokeWidth: 2,
								x: tab[i][0],
								y: tab[i][1],
								radius: rayon,
								sides: 6,
								rotate: 90
							});
						}
						harden();
					//}
		/*var end = new Date().getTime();
		alert(end - start);*/
				}
			});
			//Dans le cas d'un pion
			if(pawns[i][2] >= 0) {
				t.drawPolygon({
					pawnX: pawnX,
					pawnY: pawnY,
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
							depStart = [(layer.pawnX-size+1), (layer.pawnY-size+1)];
							depEnd = null;
							t.restoreCanvas();
							var tab = getZoneProx(layer.pawnX, layer.pawnY, new Array());
							for(i = 0; i < tab.length; i++)
							{
								t.drawPolygon({
									fillStyle: '#BBBBFF',
									strokeStyle: 'black',
									strokeWidth: 2,
									x: tab[i][0],
									y: tab[i][1],
									radius: rayon,
									sides: 6,
									rotate: 90
								});
							}
							harden();
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
				    source = 'js/plateau/138.png'
				}
				else {
					source = 'js/plateau/laurel.png';
				}
				t.drawImage({
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
								fillStyle: '#BBBBFF',
								strokeStyle: 'black',
								strokeWidth: 2,
								x: tab[i][0],
								y: tab[i][1],
								radius: rayon,
								sides: 6,
								rotate: 90
							});
						}
						harden();
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
		return [depStart, depEnd];
	}
});