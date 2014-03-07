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
    public function showNews()
	{
		$newsList = $this->newsMan->getNewsList();
		include 'view/newsView.php';
	}

	// Page faq
	public function showFaq()
	{
		$faqList = $this->faqMan->getFaqList();
		include 'view/faqView.php';
	}
}
?>