<?php
	if($faqList != null)
	{
		echo '<ul>';
		
		foreach($faqList as $faq)
		{
			echo '<li>';
			echo $faq->getId();
			echo ' - ';
			echo $faq->getQuestion();
			echo ' - ';
			echo $faq->getReponse();
			echo '</li>';
		}
		
		echo '</ul>';
	}
?>