/**
 * The service facade that handles messages from the controller.
 */
function ChatService() {
    this.listeners = new Array();
    this.ws = null;
}

/**
 * Add an object's callback function to the array of callback functions.
 *
 * @param callback A subscriber's callback function that the ChatService will
 *                 when it loops through the array in
 *                 ChatService.notifyListeners()
 */
ChatService.prototype.addListener = function(callback) {
    this.listeners.push(callback);
};

/**
 * Remove an object's callback function from the array of callback functions.
 *
 * @param callback The callback function to remove from the listeners array
 */
ChatService.prototype.removeListener = function(callback) {    
    this.listeners.splice(this.listeners.indexOf(callback), 1);
};

/**
 * Loop through the array of callback functions and pass the message as an
 * argument to them
 *
 * @param from The username of the message's author
 * @param to The person receiving the message
 * @param type The type of the message
 * @param text The text of the message
 */
ChatService.prototype.sendMessage = function(from, to, type, text) {
    var message = {
        from : from,
        to: to,
        type: type,
        text: text
    };

    this.ws.send( JSON.stringify(message) );
};

/**
 * Connect to the service
 */
ChatService.prototype.connect = function() {
	var chatS = this;
    this.ws = new SockJS("sockjs/connector");
    this.ws.onmessage = function(event) {
    	var message = $.parseJSON(event.data);
        for (var i = 0; i < chatS.listeners.length; i++) {
        	chatS.listeners[i](message);
        }
    };
};

/**
 * Disconnect from the service
 */
ChatService.prototype.disconnect = function() {
    console.log("You have disconnected");
};

/**
 * Send a POST request to check if the username is valid
 *
 * @param username The username to check validity for
 * @param callback The method to call when the Ajax call is done
 */
ChatService.prototype.checkUsername = function(username, callback) {
    $.ajax({
        type: "POST",
        url: "",
        data: { name: username }
    }).done(function(result) {
        callback(true, result);
    });
};