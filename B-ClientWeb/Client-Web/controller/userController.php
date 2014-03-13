<?php
require_once("model/newsManager.php");
require_once("model/faqManager.php");

class userController
{
	// Constructeur
	public function __construct()   
    {
	
    }
	
	// Connexion
	public function connect()
	{
		$_SESSION["username"] = $_GET["user"];
		include 'view/chatView.php';
	}
	
	// Deconnexion
	public function deconnect()
	{
		$_SESSION["username"] = null;
		include 'view/chatView.php';
	}
}
?>