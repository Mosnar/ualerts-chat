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

    var chatC = this;
    $('#chatbox').append('<p>' + ChatController.prototype.username + ': ' + message.payload + '</p>');
};

ChatController.prototype.updateUserName = function(name) {
    ChatController.prototype.username = name;
};

/**
 * When name is submited, display a welcome message and enable the message
 * field.
 */
ChatController.prototype.handleNameSubmit = function() {
    var chatC = this; // variable to reference the ChatController instance
    
    $('#nameButton').click(function() {        
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
    var chatC = this;
    $('#user-welcome').text('Welcome, ' + chatC.username + ".");
};

/**
 * Handle message submit
 */
ChatController.prototype.handleMessageSubmit = function() {
    var chatC = this;
    $('#messageButton').click(function() {
        var clientMessage = $('#messageField').val();
        chatC.service.sendMessage(clientMessage, chatC.username);
        $('#messageField').val('');
    });
};