<?php 
require_once("model/config.php");
require_once("model/faq.php"); 

class faqManager 
{
    protected $_db; 

	// Constructeur
    public function __construct()
	{ 
        $this->_db = new PDO(SQL_DSN, SQL_USERNAME, SQL_PASSWORD);
    }
	
	// Récupère toutes les news
	public function getFaqList() 
    { 
        $faqList = array();
        $q = $this->_db->query('SELECT id, question, reponse FROM faq'); 

        if($q != NULL)
		{
			while ($data = $q->fetch(PDO::FETCH_ASSOC))
			{ 
				$faqList[] = new faq($data); 
			} 
		}
		else
		{
			$faqList = NULL;
		}

		return $faqList;
    }
}
?>