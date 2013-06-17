/**
 * The controller that subscribes listeners to the ChatService.
 */
function ChatController(chatService) {
    this.service = chatService;
}

/**
 * The ChatController's onMessage() function. This will be added as a listener
 * to the ChatService.
 */
ChatController.prototype.onMessage = function(message) {
    $('#chatbox').append('<p>' + userName + ': ' + message + '</p>');
};

/**
 * Display the username
 */
ChatController.prototype.acknowledgeUser = function() {
    $('#user-welcome').text('Welcome, ' + userName + ".");
};