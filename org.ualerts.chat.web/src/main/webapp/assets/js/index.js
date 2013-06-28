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
        callback: function() { chatController.validateUsername(); },
        wait: 0,
        captureLength: 1
        });
    }
       
    /**
     * Sort the username list by online status
     */
    function sortUsers(users) {
        for (var i = 0; i < users.length; i ++) {
            var $user = $('#connected-users tbody tr td:eq(' + i + ')');
            if ($user.hasClass('offline')) {
                console.log('The user ' + $user.text() + ' is offline');
            }
            else {
                console.log('The user ' + $user.text() + ' is online');
            }
        }
    }
    
    /**
     * Place the approprite icons for td elements of the class .online or .offline
     */
    function placeUserIcons () {
        $('.online').append('<i class="icon-user"></i>&nbsp;');
        $('.offline').prepend('<i class="icon-minus-sign"></i>&nbsp;');
    }
    
    enableTypeWatch();
    //makeUsers(users);
    //sortUsers(users);
    placeUserIcons();
});