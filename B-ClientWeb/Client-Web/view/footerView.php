		<script>
			var sitePath = "<?php echo $sitePath; ?>";
			var siteName = "<?php echo $siteName; ?>";
		</script>
		<script type="text/javascript" src="<?php echo $sitePath; ?>/js/global.js"></script>
		<script type="text/javascript" src="<?php echo $sitePath; ?>/jquery/js/jquery-1.11.0.min.js"></script>
        <script type="text/javascript" src="<?php echo $sitePath; ?>/jquery/js/jquery-ui-1.10.4.custom.min.js"></script>
        <script type="text/javascript" src="<?php echo $sitePath; ?>/js/load.js"></script>
        <script type="text/javascript" src="<?php echo $sitePath; ?>/js/traitementsboard.js"></script>
        <script type="text/javascript" src="<?php echo $sitePath; ?>/js/AJAX-reloadPageContent.js"></script>
		<script type="text/javascript" src="<?php echo $sitePath; ?>/js/AJAX-reloadChatContent.js"></script>
		<?php
			// Reconnexion si rechargement de page
			if(isset($_SESSION['username']) && isset($_SESSION['userpass']))
			{
				echo '<script>var username = "'.$_SESSION['username'].'"; var userpass = "'.$_SESSION['userpass'].'";</script>';
			}
		?>
		
		<script type="text/javascript" src="<?php echo $sitePath; ?>/js/util.js"></script>
		<script type="text/javascript" src="<?php echo $sitePath; ?>/js/user.js"></script>
		<script type="text/javascript" src="<?php echo $sitePath; ?>/js/websocket.js"></script>
		<script type="text/javascript" src="<?php echo $sitePath; ?>/js/websocketIndex.js"></script>
		<script type="text/javascript" src="<?php echo $sitePath; ?>/js/sha1.js"></script>
		
		<script type="text/javascript" src="<?php echo $sitePath; ?>/js/chat.js"></script>
		<script type="text/javascript" src="<?php echo $sitePath; ?>/js/game.js"></script>
		
		<script type="text/javascript" src="<?php echo $sitePath; ?>/js/plateau/jcanvas.min.js"></script>
		<script type="text/javascript" src="<?php echo $sitePath; ?>/js/plateau/board.js"></script>
	</body>
</html>