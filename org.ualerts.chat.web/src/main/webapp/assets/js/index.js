$(document).ready(function() {
    /**
     * Make sure forms don't submit GET requests.
     */
    $('body').on('submit', 'form', function() {
        return false;
    });
            
    var remoteService = new RemoteService();
    chatRoomService = new ChatRoomService(remoteService);
    var pageController = new PageController(remoteService, chatRoomService);
    
    pageController.init();
    remoteService.addListener(new Callback(chatRoomService.onMessage, chatRoomService));
    
    /**
     * Enable typeWatch on #usernameField 
     */
    function enableTypeWatch() {
        $('#usernameField').typeWatch({
        callback: function() { pageController.validateUsername();},
        wait: 0,
        captureLength: 1
        });
    }
    
    /**
     * Enable rearrangement of ChatRooms
     */
    function onResizeHandlers() {
        var nextChatContainer = 0;
        $(window).bind('resize', function() {
            var sumChatWidth = $('div.chat-holder').width();
            if ($(window).width() < sumChatWidth + 20) {
            	$('.chatroom-container:eq(' + nextChatContainer + ')').css('display', 'none');
            	nextChatContainer++;
            }
        });
    }
    
    enableTypeWatch();
    onResizeHandlers();
});
