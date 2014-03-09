<html>   
    <body>
        <p>-----------------------------------</p>
		<h3 id="h1Chat">Chat Auguste v0.5</h3>

        <div>
            <div class="chatStruct" id="showMessage">
                Bonjour et bienvenue sur Auguste ...
            </div>

            <br/>

            <div class="chatStruct" id="sendMessage">
                <form onSubmit="addMessage(); return false;" autocomplete="off" >
                    <input type="text" id="message" name="message" />
                    <input type="submit" id="valid" value="Envoyer">
                </form>
            </div>
        </div>
    </body>
</html>