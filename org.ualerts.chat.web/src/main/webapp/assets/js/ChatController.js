/**
 * The controller that subscribes listeners to the ChatService
 *
 * @param chatService The ChatService to work with
 */
function ChatController(chatService) {
    this.service = chatService;
}

/**
 * Add listeners to the ChatService object.
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
 * Perform an action when called by the ChatService object
 *
 * @param message The message object received
 */
ChatController.prototype.onMessage = function(message) {
    var chatC = this;
    $('#chatbox').append('<p>' + ChatController.prototype.username + ': ' + message.payload + '</p>');
};

/**
 * Create a username prototype property for the ChatController class
 *
 * @param name The username
 */
ChatController.prototype.updateUserName = function(name) {
    ChatController.prototype.username = name;
};

/**
 * Call updateUserName, provided with the username field's value
 * Call acknowledgeUser() to display a welcome message
 * Enable the message input field
 */
ChatController.prototype.handleNameSubmit = function() {
    var chatC = this;
    
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
 * Display a welcome message on the view.
 */
ChatController.prototype.acknowledgeUser = function() {
    var chatC = this;
    $('#user-welcome').text('Welcome, ' + chatC.username + ".");
};

/**
 * Pass the message field text and username to the ChatService object's
 * sendMessage method
 */
ChatController.prototype.handleMessageSubmit = function() {
    var chatC = this;
    $('#messageButton').click(function() {
        var clientMessage = $('#messageField').val();
        chatC.service.sendMessage(clientMessage, chatC.username);
        $('#messageField').val('');
    });
};