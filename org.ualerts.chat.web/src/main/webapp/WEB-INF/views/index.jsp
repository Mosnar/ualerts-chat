<!DOCTYPE html>
<html>
<head>
    <title>Chat</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/bootstrap/css/bootstrap.min.css" />
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/bootstrap/css/bootstrap-responsive.min.css" />
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/chat.css" />
    
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery-2.0.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/typewatch.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/Callback.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/RemoteService.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/PageController.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/ChatRoomService.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/ChatRoom.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/sockjs-0.3.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/index.js"></script>
    </head>
    <body>
    <div class="container-responsive">
    	<div class="row">
    		<div class="span3">
				<table id="connected-users" class="table table-bordered table-hover table-condensed">
				    <thead>
					<tr class="table-header">
					    <th>Online</th>
					</tr>
				    </thead>
				    <tbody>
				    </tbody>
				</table>
			</div>
	        <div id="wrapper" class="span6">
	        	<div id="menu">
	                <form id="nameForm" action="" method="GET" autocomplete="off">
                		<div class="row">
                			<div class="span4">
		                        <input placeholder="Username" name="username" type="text" id="usernameField"/>
								<div id="username-validity"></div>
                			</div>
                			<div class="span2 text-center">
		                        <input type="submit" class="btn btn-primary" id="nameButton" value="Submit"/>
                			</div>
                		</div>
	                </form>
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
   	<div class="chatroom">
   		&nbsp;
   	</div>
   	<div class="chat-holder">
		   	<div class="chatroom-container">
		   		<p class="chatroom-title">Brandon0</p>
		   		<div class="chatroom-chat"></div>
		   		<input class="chatroom-message-field" type="text">
		   	</div>
		   	<div class="chatroom-container">
		   		<p class="chatroom-title">Brandon1</p>
		   		<div class="chatroom-chat"></div>
		   		<input class="chatroom-message-field" type="text">
		   	</div>
		   	<div class="chatroom-container">
		   		<p class="chatroom-title">Brandon2</p>
		   		<div class="chatroom-chat"></div>
		   		<input class="chatroom-message-field" type="text">
		   	</div>
		   	<div class="chatroom-container">
		   		<p class="chatroom-title">Brandon3</p>
				<div class="chatroom-chat">
					<p>Brandon chat chat chat chat chat chat chat chat chat 
					chat chat chat chat chat chat chat chat chat 
					chat chat chat chat chat chat chat chat chat 
					chat chat chat chat chat chat chat chat chat 
					chat chat chat chat chat chat chat chat chat 
					chat chat chat chat chat chat chat chat chat 
					chat chat chat chat chat chat chat chat chat 
					chat chat chat chat chat chat chat chat chat 
					chat chat chat chat chat chat chat chat chat </p>
				</div>
				<input class="chatroom-message-field" type="text" />
		   	</div>
	 </div>
     <div class="footer">
     	<div class="conversation-bar">
		    <div class="btn-group dropup pull-right">
			<button class="btn dropdown-toggle" data-toggle="dropdown">
			    <span class="icon-plus-sign"></span>
			</button>
			<ul class="dropdown-menu">
			    <li><a href="#">Link 1</a></li>
			</ul>
    	</div>
	</div>
    </div>
	</body>
</html>
