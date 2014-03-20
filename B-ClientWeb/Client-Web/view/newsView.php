<h2>News</h2>

<?php	
	if(isset($newsList) && $newsList != null)
	{
		echo '<ul>';
		foreach($newsList as $news)
		{
			echo '<li>';
				echo '<h3>' . $news->getNom() . '</h3>';
				echo 'Date : ' . $news->getDate();
				//echo '<br/>' . $news->getImage();
				echo '<br/>' . $news->getContenu();
			echo '</li>';
		}
		echo '</ul>';
	}
?>