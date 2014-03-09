<body>
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
</body>