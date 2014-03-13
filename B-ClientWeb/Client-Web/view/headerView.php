<?php require_once("view/siteGlobalVar.php"); ?>
<!DOCTYPE html>
<html>
	<head>
        <title><?php echo $siteName; ?></title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta name="viewport" content="width=device-width">
        
		<script>
			sitePath = "<?php echo $sitePath; ?>";
		</script>
		
		<script type="text/javascript" src="<?php echo $sitePath; ?>/jquery/js/jquery-1.11.0.min.js"></script>
        <script type="text/javascript" src="<?php echo $sitePath; ?>/jquery/js/jquery-ui-1.10.4.custom.min.js"></script>
        <script type="text/javascript" src="<?php echo $sitePath; ?>/js/AJAX-reloadPageContent.js"></script>
		<script type="text/javascript" src="<?php echo $sitePath; ?>/js/AJAX-reloadChatContent.js"></script>
		
		<script type="text/javascript" src="<?php echo $sitePath; ?>/js/websocket.js"></script>
		<script type="text/javascript" src="<?php echo $sitePath; ?>/js/websocketIndex.js"></script>
		
		<script type="text/javascript" src="<?php echo $sitePath; ?>/js/chat.js"></script>
		<script type="text/javascript" src="<?php echo $sitePath; ?>/js/user.js"></script>
		
		<link rel="stylesheet" href="<?php echo $sitePath; ?>/css/main.css" type="text/css">	
		<link rel="stylesheet" href="<?php echo $sitePath; ?>/css/chat.css" type="text/css">	
    </head>

</html>