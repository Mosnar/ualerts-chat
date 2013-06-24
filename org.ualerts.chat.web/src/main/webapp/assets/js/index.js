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
    $('#usernameField').focus();
    
    
    /**
     * Launch modal
     */
    function doModal() {
        $('#login').modal();
    }
    
    doModal();
});