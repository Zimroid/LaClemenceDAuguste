<?php
	if($newsList != null)
	{
		echo '<ul>';
		
		foreach($newsList as $news)
		{
			echo '<li>';
			echo $news->getId();
			echo ' - ';
			echo $news->getQuestion();
			echo ' - ';
			echo $news->getReponse();
			echo '</li>';
		}
		
		echo '</ul>';
	}
?>