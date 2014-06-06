function traitementsBoard()
{
	if(($("#board_players").length != 0) && ($("#timer").length != 0))
	{
		if (save_game_config.configuration.game_mode == 'fast')
		{
			if (save_game_config.teams[0].players[0].player_user_id == myId)
			{
				$("#board_players").append($("<span>Vous avez les pions rouges.</span>"));
			}
			else if (save_game_config.teams[1].players[0].player_user_id == myId)
			{
				$("#board_players").append($("<span>Vous avez les pions verts.</span>"));
			}
		}
		else
		{
			$("#board_players").append($("<span>Ici, la liste des joueurs.</span>"));
		}
	}
	$("#board").initBoard(save_game_turn);
}