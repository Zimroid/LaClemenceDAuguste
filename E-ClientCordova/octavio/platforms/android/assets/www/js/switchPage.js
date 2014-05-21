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
        }
    };
	
    xmlhttp.open("GET", url , true);
    xmlhttp.send();
}

/**
 * Function called when page has finished loading.
 */
function init() {

    // Load first page into container
    loadPage("panda.html");
}