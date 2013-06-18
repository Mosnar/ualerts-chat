/**
 * The service facade that handles messages from the controller.
 */
function ChatService() {
    this.listeners = new Array();
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
    console.log('you pushed a callback to position ' + (this.listeners.length - 1)); // for checking purposes
};

/**
 * Remove an object's callback function from the array of callback functions.
 */
ChatService.prototype.removeListener = function(callback) {
    var position = this.listeners.indexOf(callback); // for checking purposes
    
    this.listeners.splice(this.listeners.indexOf(callback), 1);
    console.log('you removed a callback from position ' + position); // for checking purposes
};

/**
 * Loop through the array of callback functions and pass the message as an
 * argument to them
 *
 * @param message The message to pass into each of the listeners' callback
 *                functions.
 */
ChatService.prototype.sendMessage = function(payload, username) {
    var message = {
        payload : payload,
        username : username
    }
    for (var i = 0; i < this.listeners.length; i++) {
        this.listeners[i](message);
    }
};

/**
 * Connect to the service
 */
ChatService.prototype.connect = function() {
    console.log("You have connected");
};

/**
 * Disconnect from the service
 */
ChatService.prototype.disconnect = function() {
    console.log("You have disconnected");
};