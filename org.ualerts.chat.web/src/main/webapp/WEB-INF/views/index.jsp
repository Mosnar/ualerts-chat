<!DOCTYPE html>
<html>
<head>
    <title>Chat</title>
    <link type="text/css" rel="stylesheet" href="css/chat.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery-2.0.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/chat-service.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/chat-controller.js"></script>

    <script type="text/javascript">
        $(document).ready(function() {
            
            function messageDisabling($nameField, $messageField) {
                if ($nameField.value == null) {
                    $messageField.attr('disabled', 'disabled');
                }
                
                $nameField.blur(function() {
                    if ($nameField.val().length > 0) {
                        $messageField.removeAttr('disabled');
                    }
                });
            }
            
            messageDisabling($('#usernameField'), $('#userMessage'));
            
            /***********/
            
            var chatService;
            var chatController;
            
            function init() {
                chatService = new ChatService();
                chatController = new ChatController(chatService);
                setUpListeners();
            }
            
            function setUpListeners() {
                chatService.addListener(chatController.onMessage);
            }
            
            function handleMessageData() {
                $('#userMessage').blur(function() {
                    var clientMessage = $('#userMessage').val();
                    chatService.notifyListeners(clientMessage);
                })
            }
            
            init();
            handleMessageData();
        });
    </script>
</head>
<body>
    <div id="wrapper">
    	<div id="menu">
            <form id="nameForm" action="" method="GET">
                    Username: &nbsp; <input name="userame" type="text" id="usernameField" size="40"/>
                    <input name="submitUsername" type="submit" id="submitButton" />
            </form>
            <div style="clear: both"></div>
    	</div>
    	
    	<div id="chatbox"></div>
    	
    	<form id="messageForm" name="message" action="">
    		<input name="userMessage" type="text" id="userMessage" size="70" />
    		<input name="submitMessage" type="submit" id="submitMessage" value="Send" />
    	</form>
    </div>
</body>
</html>

