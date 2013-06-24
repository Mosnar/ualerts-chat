<!DOCTYPE html>
<html>
<head>
    <title>Chat</title>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/bootstrap/css/bootstrap.min.css" />
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/bootstrap/css/bootstrap-responsive.min.css" />
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/chat.css" />
    
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery-2.0.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/ChatService.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/ChatController.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/sockjs-0.3.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/index.js"></script>
    </head>
    <body>
    <div class="container">
	<div id="login" class="modal hide fade" style="display: none;">
	    <div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		<h3 id="loginLabel">Login</h3>
	    </div>
	    <div class="modal-body">
		<form>
		    <input type="text" placeholder="Username" id="usernameField"></input>
		    <a href="#" class="btn" data-dismiss="modal">Close</a>
		    <input class="btn btn-primary" type="submit" id="nameButton" value="Submit" data-dismiss="modal"></input>
		</form>
	    </div>
	</div>
    	<div class="row">
    		<div class="span3">&nbsp;</div>
	        <div id="wrapper" class="span6">
	        	<div id="menu">
	                <div style="clear: both"></div>
	        	</div>
	        	
	    	<p id="user-welcome">	
	    	</p>
	    	
	    	<br />
	        	<div id="chatbox"></div>
	        	
	        	<form id="messageForm" name="message" action="">
	        		<div class="row">
	        			<div class="span4">
	        				<input name="userMessage" type="text" id="messageField"/>
	        			</div>
	        			<div class="span2 text-center">
		        			<input type="submit" class="btn btn-success" id="messageButton" value="Send" />
		        		</div>
	        		</div>
	        	</form>
	        </div>
        </div>
     </div>
    </body>
    </html>
