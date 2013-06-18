<!DOCTYPE html>
<html>
<head>
    <title>Chat</title>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}css/chat.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}js/jquery-2.0.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}js/ChatService.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}js/ChatController.js"></script>
    <script type="text/javascript">
    $(document).ready(function() {
    	
    	/**
    	 * Make sure forms don't submit GETs while in development.
    	 */
    	$('form').submit(function() {
    	    return false;
    	});
    	
    	init();
    	
    	/**
    	 * Create references for a ChatService and a ChatController. Set up the
    	 * chat.
    	 */
    	function init() {
    	    var chatService = new ChatService();
    	    var chatController = new ChatController(chatService);
    	    chatController.setUpListeners();
    	    chatController.messageDisable();
    	    chatController.handleNameSubmit();
    	    chatController.handleMessageSubmit();
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