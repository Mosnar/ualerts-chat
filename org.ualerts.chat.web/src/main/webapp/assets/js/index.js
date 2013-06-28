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
        wait: 1,
        captureLength: 1
        });
    }
    
    enableTypeWatch();
    
    /**
     * Make the HTML for the users table
     */
    function makeUsers(users) {
        string = "";
        for (var i = 0; i < users.length; i++) {
            string += '<tr><td class="online">' + users[i] + '</td></tr>';
        }
        
        //$('#connected-users tbody').append(string);
    }
    
    /**
     * Sort the username list by online status
     */
    function sortUsers(users) {
        //var numUsers = $('#connected-users tbody tr td').length;

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
        $('.online').prepend('<i class="icon-user"></i>&nbsp;');
        $('.offline').prepend('<i class="icon-minus-sign"></i>&nbsp;');
    }
    
    var users = new Array("Brandon", "Billy", "Michael", "Ransom", "Carl", "Mathew", "Brian", "Phil", "Joe");
    
    makeUsers(users);
    sortUsers(users);
    //placeUserIcons();
});