/**
 * The controller that subscribes listeners to the ChatService.
 */
function ChatController(chatService) {
    this.service = chatService;
    this.userName = "";
}

/**
 * The ChatController's onMessage() function. This will be added as a listener
 * to the ChatService. 
 */
ChatController.prototype.onMessage = function(message) {
    $('#chatbox').append('<p>' + this.userName + ": " + message + '</p>');
};

/**
 * Display the username
 */
ChatController.prototype.acknowledgeUser = function() {
    var name = $('#usernameField').val();

    $('#user-welcome').text('Welcome, ' + name);
};