<script type="text/javascript" src="<?php echo $sitePath; ?>/jquery/js/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="<?php echo $sitePath; ?>/jquery/js/jquery-ui-1.10.4.custom.min.js"></script>

<script type="text/javascript" src="<?php echo $sitePath; ?>/js/chat.js"></script>
<script type="text/javascript" src="<?php echo $sitePath; ?>/js/websocket.js"></script>

<div id="chatBox">
    <div id="chatInfo">
        Chat Auguste v0.5
    </div>
    <div id="chatMessages">
        Bonjour et bienvenue sur Auguste ...<?php echo $sitePath; ?>
    </div>

    <br/>

    <div id="chatSend">
        <form onSubmit="addMessage(); return false;" autocomplete="off" >
            <input type="text" id="message" name="message" />
            <input type="submit" id="valid" value="Envoyer">
        </form>
    </div>
</div>