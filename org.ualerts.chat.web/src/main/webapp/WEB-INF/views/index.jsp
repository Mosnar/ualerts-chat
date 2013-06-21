<!DOCTYPE html>
<html>
<head>
    <title>Chat</title>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/chat.css" />
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/bootstrap/css/bootstrap.css" />
    
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery-2.0.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/ChatService.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/ChatController.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/sockjs-0.3.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/index.js"></script>
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
