<h1>FAQ</h1>

<?php	
	if($faqList != null)
	{
		echo '<ul>';
		
		foreach($faqList as $faq)
		{
			echo '<li>';
			echo '<b>' . $faq->getQuestion() . '</b><br/>';
			echo $faq->getReponse();
			echo '</li><br/>';
		}
		
		echo '</ul>';
	}
?>