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
	
	xhr.open("POST", url, false);
	
	// Execution au retour
	xhr.onreadystatechange = function()
	{
		if(xhr.readyState == 4 && xhr.status == 200)
		{
			pageContent = document.getElementById("mainPage");
			pageContent.innerHTML = xhr.responseText;
			if($("#board").length != 0)
			{
				traitementsBoard();
			}
		}
	};
	
	// Paramètres
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	
	// Envoi
	xhr.send();
}