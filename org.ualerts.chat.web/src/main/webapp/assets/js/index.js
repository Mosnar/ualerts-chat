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
     * Establish a connection with the SockJS server
     */
    function connection() {
        var sock = new SockJS('');
        sock.onopen = function() {
            console.log('opened');
        };
        
        sock.onmessage = function(event) {
            var data = $.parseJSON(evt.data);
            chatService.sendMessage(data);
        };
        
        sock.onclose = function() {
            console.log('closed');
        };
    }
});