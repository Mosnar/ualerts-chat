<!DOCTYPE html>
<html>
<head>
    <title>Chat</title>
    <link type="text/css" rel="stylesheet" href="css/chat.css" />
    <script type="text/javascript" src="js/jquery-2.0.1.min.js"></script>
    <script type="text/javascript" src="js/chat-service.js"></script>
    <script type="text/javascript" src="js/chat-controller.js"></script>
    <script type="text/javascript">
    $(document).ready(function() {
	$('form').submit(function() {
	    return false;
	});
	
	init();
	messageDisable();
	handleNameSubmit();
	handleMessageData();
	
	function messageDisable() {
	    $('#messageField').attr('disabled', 'disabled');
	}
	
	function handleNameSubmit() {
	    $('#nameButton').click(function() {
		if ($('#usernameField').val() != "") {
		    userName = $('#usernameField').val();
		    chatController.acknowledgeUser();
		    $('#messageField').removeAttr('disabled');
		}
		console.log('the submit button was clicked'); // for checking purposes
	    });
	}
	
	/***** Begin Observer Pattern ******/
	
	var chatService;
	var chatController;
	
	function init() {
	    var username = "";
	    chatService = new ChatService();
	    chatController = new ChatController(chatService);
	    
	    setUpListeners();
	}
	
	function setUpListeners() {
	    chatService.addListener(chatController.onMessage);
	}
	
	function handleMessageData() {
	    $('#messageButton').click(function() {
		var clientMessage = $('#messageField').val();
		
		chatService.sendMessage(clientMessage);
		console.log("The send button was clicked"); // for checking purposes
	    })
	}
    });
    </script>
</head>
<body>
    <div id="wrapper">
    	<div id="menu">
            <form id="nameForm" action="" method="GET">
                    Username: &nbsp; <input name="username" type="text" id="usernameField" size="40"/>
                    <input type="submit" id="nameButton" value="Submit"/>
            </form>
            <div style="clear: both"></div>
    	</div>
    	
	<p id="user-welcome">	
	</p>
	
	<br />
    	<div id="chatbox"></div>
    	
    	<form id="messageForm" name="message" action="">
    		<input name="userMessage" type="text" id="messageField" size="70" />
    		<input type="submit" id="messageButton" value="Send" />
    	</form>
    </div>
</body>
</html>

