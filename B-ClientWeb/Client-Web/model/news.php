<?php
class news
{
	// Attributs
	private $_id;
	private $_nom;
	private $_image;
	private $_contenu;
	private $_date;
	
	// Constructeur
	public function __construct(array $data) { 
        $this->_id = 	  $data['id']; 
        $this->_nom =     $data['nom']; 
        $this->_image =   $data['image'];
		$this->_contenu = $data['contenu'];
		$this->_date = 	  $data['date'];
    }
	
	// Méthodes de lecture
    public function getId() { 
        return $this->_id; 
    } 

    public function getNom() { 
        return $this->_nom; 
    } 

    public function getImage() { 
        return $this->_image; 
    }
	
	public function getContenu() { 
        return $this->_contenu; 
    }
	
	public function getDate() { 
        return $this->_date; 
    }
}
?>