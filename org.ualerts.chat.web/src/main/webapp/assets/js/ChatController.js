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
    var $field = $('#messageField');
    var $button = $('#messageButton');
    $field.attr('readonly', 'readonly');
    $field.attr('value', 'Please enter a username');
    $button.attr('disabled', 'disabled');
    
};

/**
 * Perform an action when called by the ChatService object
 *
 * @param message The message object received
 */
ChatController.prototype.onMessage = function(message) {
    var $chatbox = $('#chatbox');
    var date = new Date(message.messageDate);
    var dateString = date.getHours() + ":" + date.getMinutes();
    $chatbox.append('<p>' + '(' + dateString + ')' + ' ' +
        message.from + ': ' + message.text + '</p>');
    $chatbox.scrollTop($chatbox[0].scrollHeight);
};

/**
 * Create a username prototype property for the ChatController class
 *
 * @param name The username
 */
ChatController.prototype.updateUserName = function(name) {
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
            chatC.prepareMessageField();
            chatC.service.sendMessage("<b>" + chatC.username + "</b>", "all", "chat", "<b>" + name + "</b>" + " has entered the chat.");
        }
    });
    
    this.service.connect();
};

/**
 * Make the message field ready for typing.
 */
ChatController.prototype.prepareMessageField = function() {
    var $field = $('#messageField');
    var $button = $('#messageButton');
    
    $field.removeAttr('readonly');
    $field.removeAttr('value');
    $button.removeAttr('disabled');
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
    $messageField = $('#messageField');
    $('#messageButton').click(function() {
        if ($messageField.val() != "") {
            var clientMessage = $messageField.val();
            chatC.service.sendMessage("<b>" + chatC.username + "</b>", "all", "chat", clientMessage);
            $messageField.val('');
        }
    });
};
