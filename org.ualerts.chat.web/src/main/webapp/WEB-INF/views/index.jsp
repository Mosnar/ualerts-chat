<!DOCTYPE html>
<html>
<head>
    <title>Chat</title>
    <!-- <meta name="viewport" content="width=device-width, initial-scale=1.0"> -->
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/bootstrap/css/bootstrap.min.css" />
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/bootstrap/css/bootstrap-responsive.min.css" />
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/chat.css" />
    
    <script type="text/javascript">
      var CONTEXT_PATH = "${pageContext.request.contextPath}";
    </script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery-2.0.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/typewatch.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/sockjs-0.3.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/Callback.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/RemoteService.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/PageController.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/ChatRoomViewControllerManager.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/ChatRoomService.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/ChatRoomViewController.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/GlobalMessageIndicator.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/MessageUtils.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/index.js"></script>
    </head>
    <body>
    <div class="container">
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
	        	
	        	<form id="messageForm" name="message" action="">
	        		<div class="row">
	        			<div class="span4">
	        				<input type="text" id="messageField"/>
	        			</div>
	        			<div class="span2 text-center">
		        			<input type="submit" class="btn btn-success" id="messageButton" value="Send" />
		        		</div>
	        		</div>
	        	</form>
	        </div>
        </div>
    </div>
   	<div class="chat-holder">
   		<div id="overflow-wrapper">
			<div id="overflow-chatroom-button" class="btn-group dropup">
			    <button class="btn dropdown-toggle" data-toggle="dropdown">
				<span class="icon-plus-sign"></span>
			    </button>
			    <ul class="dropdown-menu">
			    </ul>
			</div>
		</div>
	</div>
	<div id="sound"></div>
	</body>
</html>
