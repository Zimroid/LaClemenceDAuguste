<?php
	session_start();
	if (isset($_GET['pass']) && ($_GET['pass'] != ''))
	{
		$_SESSION['userpass'] = $_GET['pass'];
	}
?>
