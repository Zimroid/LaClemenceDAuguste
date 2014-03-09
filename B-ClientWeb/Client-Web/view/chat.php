<script type="text/javascript" src="../jquery/js/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="../jquery/js/jquery-ui-1.10.4.custom.min.js"></script>

<script type="text/javascript" src="../js/chat.js"></script>
<script type="text/javascript" src="../js/websocket.js"></script>

<style>
html
{
    height: 100%;
}

body
{
    height: 100%;
    margin: 0px;
}

#chatBox
{
    overflow:   none;
    position:   absolute;
    width:      30%;
    min-width:  200px;
    height:     100%;
    top:        0px;
    right:      0px;
}

#chatMessages
{
    overflow:   auto;
    position:   relative;
    bottom:     0;
    max-height: calc(100% - 8em);
}

#chatInfo
{
    height: 5em;
}

#chatSend
{
    height:     2em;
    position :  absolute;
    bottom:     0px;
}
</style>

<div id="chatBox">
    <div id="chatInfo">
        Chat Auguste v0.5
    </div>
    <div id="chatMessages">
        Bonjour et bienvenue sur Auguste ...
    </div>

    <br/>

    <div id="chatSend">
        <form onSubmit="addMessage(); return false;" autocomplete="off" >
            <input type="text" id="message" name="message" />
            <input type="submit" id="valid" value="Envoyer">
        </form>
    </div>
</div>