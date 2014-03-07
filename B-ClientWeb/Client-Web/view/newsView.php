<?php
	if($newsList != null)
	{
		echo '<ul>';
		
		foreach($newsList as $news)
		{
			echo $news->getId();
			echo ' - ';
			echo $news->getNom();
			echo '<br/>';
			echo $news->getImage();
			echo '<br/>';
			echo $news->getContenu();
		}
		
		echo '</ul>';
	}
?>