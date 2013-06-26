$(document).ready(function() {
    /**
     * Make sure forms don't submit GETs while in development.
     */
    $('form').submit(function() {
        return false;
    });
            
    var chatService = new ChatService();
    var chatController = new ChatController(chatService);
    
    chatController.init();
    
    /**
     * Enable typeWatch on #usernameField 
     */
    function enableTypeWatch() {
        $('#usernameField').typeWatch({
        callback: function() { sendUsername(); },
        wait: 300,
        captureLength: 1
        });
    }
    
    enableTypeWatch();
    
    /**
     * Call the ChatService's checkUsername method and pass in the
     * ChatController's handleBoolean method as a callback.
     */
    function sendUsername() {
        var $username = $('#usernameField').val();
        chatService.checkUsername($username, chatController.handleValidity);
    }
});