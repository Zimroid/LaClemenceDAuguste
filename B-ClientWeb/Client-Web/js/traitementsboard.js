function traitementsBoard()
{
	var symbol = '';
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
			for (var i = 0 ; i < save_game_config.teams.length ; i++)
			{
				for (var j = 0 ; j < save_game_config.teams[i].players.length ; j++)
				{
					for (var k = 0 ; k < save_game_config.teams[i].players[j].legions.length ; k++)
					{
						for (var l = 0 ; l < save_game_users.length ; l++)
						{
							if (save_game_users[l].user_id == save_game_config.teams[i].players[j].player_user_id)
							{
								switch (save_game_config.teams[i].players[j].legions[k].legion_shape)
								{
									case 'circle':
										symbol = '⬢';
										break;
									case 'triangle':
										symbol = '▲';
										break;
									case 'square':
										symbol = '■';
										break;
								}
								$("#board_players").append($("<span style='color:" + save_game_config.teams[i].players[j].legions[k].legion_color + "'>" + ' ' + symbol +  ' ' + save_game_users[l].user_name + ' ' + "</span>"));
							}
						}
					}
				}
			}
			//$("#board_players").append($("<span>Ici, la liste des joueurs.</span>"));
		}
	}
	$("#board").initBoard(save_game_turn);
}