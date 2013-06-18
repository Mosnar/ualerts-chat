/**
 * The controller that subscribes listeners to the ChatService
 *
 * @param chatService The ChatService to work with
 */
function ChatController(chatService) {
    this.service = chatService;
    this.username = "";
}

/**
 * Set up the chat.
 */
ChatController.prototype.init = function() {
    this.setUpListeners();
    this.messageDisable();
    this.handleNameSubmit();
    this.handleMessageSubmit();
};

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
    $('#chatbox').append('<p>' + /*ChatController.prototype.username*/ message.from + ': ' + message.text + '</p>');
    $('#chatbox').scrollTop($('#chatbox')[0].scrollHeight);
};

/**
 * Create a username prototype property for the ChatController class
 *
 * @param name The username
 */
ChatController.prototype.updateUserName = function(name) {
    //ChatController.prototype.username = name;
    this.username = name;
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
        chatC.service.sendMessage(chatC.username, null, "chat", null, clientMessage);
        $('#messageField').val('');
    });
};