/**
 * The controller that subscribes listeners to the ChatService.
 */
function ChatController(chatService) {
    this.service = chatService;
}

/**
 * Set up listeners to the service facade
 */
ChatController.prototype.setUpListeners = function() {
    this.service.addListener(this.onMessage);
};

/**
 * Disable the message field.
 */
ChatController.prototype.messageDisable = function() {
    $('#messageField').attr('disabled', 'disabled');
};

/**
 * The ChatController's onMessage() function. This will be added as a listener
 * to the ChatService.
 */
ChatController.prototype.onMessage = function(message) {
    console.log("onMessage was called");
    var chatC = this;
    $('#chatbox').append('<p>' + ChatController.prototype.username + ': ' + message.payload + '</p>');
    console.log("this.username: " + this.username);
    console.log("chatC.username: " + chatC.username);
    console.log("ChatController.prototype.username: " + ChatController.prototype.username);
};

ChatController.prototype.updateUserName = function(name) {
    console.log("updateUserName was called");  
    ChatController.prototype.username = name;
    console.log("this.username: " + this.username);
    console.log("ChatController.prototype.username: " + ChatController.prototype.username);
};

/**
 * When name is submited, display a welcome message and enable the message
 * field.
 */
ChatController.prototype.handleNameSubmit = function() {
    var chatC = this; // variable to reference the ChatController instance
    
    $('#nameButton').click(function() {
        console.log('the submit button was clicked'); // for checking purposes
        console.log("handleNameSubmit was called");
        
        if ($('#usernameField').val() != "") {
            var name = $('#usernameField').val();
            chatC.updateUserName(name);
            chatC.acknowledgeUser();
            $('#messageField').removeAttr('disabled');
        }
    });
};

/**
 * Display the username
 */
ChatController.prototype.acknowledgeUser = function() {
    console.log("acknowledgeUser was called");
    var chatC = this;
    $('#user-welcome').text('Welcome, ' + chatC.username + ".");
    console.log("this.username: " + this.username);
    console.log("chatC.username: " + chatC.username);
    console.log("ChatController.prototype.username: " + ChatController.prototype.username);
};

/**
 * Handle message submit
 */
ChatController.prototype.handleMessageSubmit = function() {
    var chatC = this;
    $('#messageButton').click(function() {
        console.log("the send button was clicked"); // for checking purposes
        console.log("handleMessageSubmit was called");
        
        var clientMessage = $('#messageField').val();
        chatC.service.sendMessage(clientMessage, chatC.username);
        console.log("service.sendMessage has completed");
        console.log(chatC.username);
        $('#messageField').val('');
    });
};