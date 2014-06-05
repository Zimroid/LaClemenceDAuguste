/**
 * Load page into url
 * @param url - The url to load
 */
function loadPage(url) {
    var xmlhttp = new XMLHttpRequest();

    // Callback function when XMLHttpRequest is ready
    xmlhttp.onreadystatechange=function()
	{
        if (xmlhttp.readyState === 4 && xmlhttp.status === 200)
		{
			document.getElementById('container').innerHTML = xmlhttp.responseText;
			
			// Si présence d'un canevas "board" -> Lancement système de jeu
			if($("#board").length != 0)
			{				
				console.log(localStorage.save_game_turn);
				$("#board").initBoard(JSON.parse(localStorage.save_game_turn));
			}
        }
    };
	
    xmlhttp.open("GET", url , true);
    xmlhttp.send();
}

/**
 * Function called when page has finished loading.
 */
function initSwitch() {

    // Load first page into container
    loadPage("home_onlineChoiceView.html");
}