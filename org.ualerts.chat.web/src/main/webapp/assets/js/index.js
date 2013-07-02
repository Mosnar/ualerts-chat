$(document).ready(function() {
    /**
     * Make sure forms don't submit GETs while in development.
     */
    $('form').submit(function() {
        return false;
    });
            
    var remoteService = new RemoteService();
    var pageController = new PageController(remoteService);
    
    pageController.init();
    
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
});
