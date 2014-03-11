<?php
require_once("model/newsManager.php");
require_once("model/faqManager.php");

class pageController
{
	// Constructeur
	public function __construct()   
    {   
        $this->newsMan = new newsManager();
		$this->faqMan  = new faqManager ();
    }
	
	// Page de news
    public function news()
	{
		$newsList = $this->newsMan->getNewsList();
		include 'view/newsView.php';
	}
	
	// Page des règles
    public function rules()
	{
		include 'view/rulesView.php';
	}
	
	// Page FAQ
	public function faq()
	{
		$faqList = $this->faqMan->getFaqList();
		include 'view/faqView.php';
	}
	
	// Page de contact
	public function contact()
	{
		include 'view/contactView.php';
	}
	
	// Page de création de partie
	public function newGame()
	{
		include 'view/newGameView.php';
	}
	
	// Liste des parties disponibles
	public function joinGame()
	{
		include 'view/joinGameView.php';
	}
	
	// Téléchargement
	public function download()
	{
		include 'view/downloadView.php';
	}
}
?>