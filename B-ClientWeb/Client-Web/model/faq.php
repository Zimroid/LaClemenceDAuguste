<?php
class faq
{
	// Attributs
	private $_id;
	private $_question;
	private $_reponse;
	
	// Constructeur
	public function __construct(array $data) { 
        $this->_id = 	   $data['id']; 
        $this->_question = $data['question']; 
        $this->_reponse =  $data['reponse'];
    }
	
	// Méthodes de lecture
    public function getId() { 
        return $this->_id; 
    } 

    public function getQuestion() { 
        return $this->_question; 
    } 

    public function getReponse() { 
        return $this->_reponse; 
    }
}
?>