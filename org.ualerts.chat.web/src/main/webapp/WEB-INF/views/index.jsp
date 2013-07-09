<!DOCTYPE html>
<html>
<head>
    <title>Chat</title>
    <!-- <meta name="viewport" content="width=device-width, initial-scale=1.0"> -->
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/bootstrap/css/bootstrap.min.css" />
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/bootstrap/css/bootstrap-responsive.min.css" />
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/chat.css" />
    
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery-2.0.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/typewatch.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/sockjs-0.3.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/Callback.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/RemoteService.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/PageController.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/ChatRoomService.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/ChatRoom.js"></script>
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
	 </div>
<!--      <div class="footer">
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
    </div> -->
    
<%--     <div id="pusherChat">
		<div id="membersContent">
			<span id="expand">
				<span class="close">&#x25BC;</span>
				<span class="open">&#x25B2;</span>
			</span>
			<h2>
				<span id="count">0</span> online
			</h2>
			<div class="scroll">
				<div id="members-list"></div>
			</div>
		</div>    
		
		<!-- chat box template -->
		<div id="templateChatBox">
			<div class="pusherChatBox">
				<span class="state">
					<span class="pencil">
						<img src="${pageContext.request.contextPath}/assets/pusher-chat/assets/pencil.gif" />
					</span>
					<span class="quote">
						<img src="${pageContext.request.contextPath}/assets/pusher-chat/assets/quote.gif" />
					</span>
				</span>
				<span class="expand">
					<span class="close">&#x25BC;</span>
					<span class="open">&#x25B2;</span>
				</span>
				<span class="closeBox">&times;</span>
				<h2>
					<a href="#" title="go to profile"><img src="" class="imgFriend" /></a>
					<span class="userName"></span>
				</h2>
				<div class="slider">
					<div class="logMsg">
						<div class="msgTxt"></div>
					</div>
				</div>
				<form method="post" name="#123">
					<textarea name="msg" rows="3"></textarea>
					<input type="hidden" name="from" class="from" />
					<input type="hidden" name="to" class="to" />
					<input type="hidden" name="typing" class="typing" value="false" />
				</form>
			</div>
		</div>
    </div>
	<!-- #pusherChat end -->
	
	<div class="chatBoxWrap">
		<div class="chatBoxslide"></div>
		<span id="slideLeft">
			<img src="${pageContext.request.contextPath}/assets/pusher-chat/assets/quote.gif" />&#x25C0;
		</span>
		<span id="slideRight">
			&#x25B6; <img src="${pageContext.request.contextPath}/assets/pusher-chat/assets/quote.gif" />
		</span>
	</div>
 --%>
	</body>
</html>
