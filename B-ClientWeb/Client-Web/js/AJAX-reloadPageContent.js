// Script AJAX
//
// Rechargement partiel de la page
//---------------------------------------

function reloadContent(url)
{
	var xhr = null;
	try //(Firefox, Chrome, Opera, Safari)
	{
		xhr = new XMLHttpRequest();
	}
	catch(e)
	{
		alert("Your browser does not support AJAX");
		return;
	}
	
	xhr.open("POST", url, true);
	
	// Execution au retour
	xhr.onreadystatechange = function()
	{
		if(xhr.readyState == 4 && xhr.status == 200)
		{
			pageContent = document.getElementById("mainPage");
			pageContent.innerHTML = xhr.responseText;
			if($("#board").length != 0)
			{
				if($("#board_players").length != 0)
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
					/*var player_1 = $("<span>" + save_game_config.teams[0].players[0].player_user_id + " : Pions rouges. </span>");
					var player_2 = $("<span>" + save_game_config.teams[1].players[0].player_user_id + " : Pions verts. </span>");
					$("#board_players").append(player_1);
					$("#board_players").append(player_2);*/
				}
				$("#board").initBoard(save_game_turn);
			}
		}
	};
	
	// Paramètres
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	
	// Envoi
	xhr.send();
}