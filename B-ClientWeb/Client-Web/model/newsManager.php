<?php 
require_once("model/config.php");
require_once("model/news.php"); 

class newsManager 
{
    protected $_db; 

	// Constructeur
    public function __construct()
	{ 
        $this->_db = new PDO(SQL_DSN, SQL_USERNAME, SQL_PASSWORD);
    }
	
	// Récupère toutes les news
	public function getNewsList() 
    { 
        $newsList = array();
        $q = $this->_db->query('SELECT id, nom, image, contenu FROM news ORDER BY date'); 

        if($q != NULL)
		{
			while ($data = $q->fetch(PDO::FETCH_ASSOC))
			{ 
				$newsList[] = new news($data); 
			} 
		}
		else
		{
			$newsList = NULL;
		}

		return $newsList;
    }
}
?>