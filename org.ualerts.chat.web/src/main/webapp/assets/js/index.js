$(document).ready(function() {
    /**
     * Make sure forms don't submit GET requests.
     */
    $('body').on('submit', 'form', function() {
        return false;
    });
    var remoteService = new RemoteService();
    var chatRoomService = new ChatRoomService(remoteService);
    var globalMessageIndicator = new GlobalMessageIndicator(remoteService);
    var pageController = new PageController(remoteService, chatRoomService);
    
    pageController.init();
    remoteService.addListener(new Callback(chatRoomService.onMessage, chatRoomService));
    remoteService.addListener(new Callback(globalMessageIndicator.onMessage, globalMessageIndicator));
    
    /**
     * Enable typeWatch on #usernameField
     */
    function enableTypeWatch() {
        $('#usernameField').typeWatch({
        callback: function() { pageController.validateUsername(); },
        wait: 0,
        captureLength: 1
        });
    }
        
    /**
     * Enable rearrangement of ChatRooms
     */
    function onResizeHandlers() {
      $(window).bind('resize', function() {
          chatRoomService.getChatRoomViewControllerManager().redraw();
      });
    }
    
    function usernameChangeHandler() {
    	$('#usernameField').keyup(function(e){
    		if ($(this).val() == "") {
    			pageController.nameSubmitDisable();
    			$('#username-validity').empty();
    		};
    	});
    }
    
    enableTypeWatch();
    onResizeHandlers();
    usernameChangeHandler();
});
