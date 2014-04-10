function initBoard(data)
{
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
			pions[i] = new Array();
			pions[i][0] = data.board[i].u;
			pions[i][1] = data.board[i].w;
			pions[i][2] = data.board[i].pawn_legion + (data.board.pawn_legion * data.board.pawn_team);
			if (!legions[pions[i][2]])
			{
				legions[pions[i][2]] = new Array();
			}
			legions[pions[i][2]][0] = data.board[i].legion_color;
			legions[pions[i][2]][1] = data.board[i].legion_shape;
		}
		// armure
		else if (data.board[i].type == "auguste.engine.entity.pawn.armor")
		{
			armor[i] = new Array();
			armor[i][0] = data.board[i].u;
			armor[i][1] = data.board[i].w;
		}
		// laurier
		else if (data.board[i].type == "auguste.engine.entity.pawn.laurel")
		{
			pions[i] = new Array();
			pions[i][0] = data.board[i].u;
			pions[i][1] = data.board[i].w;
			pions[i][2] = -1;
		}
	}
}
