<!DOCTYPE html>
<html>
<head>
    <title>Chat</title>
    <link type="text/css" rel="stylesheet" href="css/chat.css" />
    <script type="text/javascript" src=""></script>
    <script type="text/javascript" src=""></script>
</head>
<body>
    <div id="wrapper">
    	<div id="menu">
            <form method="POST" action="">
                    Username: &nbsp; <input name="username" type="text" id="usernameField" size="40"/>
                    <input name="submitmsg" type="submit" id="submitbutton" />
            </form>
            <div style="clear: both"></div>
    	</div>
    	
    	<div id="chatbox"></div>
    	
    	<form name="message" action="">
    		<input name="userMessage" type="text" id="userMessage" size="70" />
    		<input name="submitMessage" type="submit" id="submitMessage" value="Send" />
    	</form>
    </div>
</body>
</html>