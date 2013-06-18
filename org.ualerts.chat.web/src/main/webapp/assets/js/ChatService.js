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
};

/**
 * Remove an object's callback function from the array of callback functions.
 *
 * @param callback The callback function to remove from the listeners array
 */
ChatService.prototype.removeListener = function(callback) {
    var position = this.listeners.indexOf(callback); // for checking purposes
    
    this.listeners.splice(this.listeners.indexOf(callback), 1);
};

/**
 * Loop through the array of callback functions and pass the message as an
 * argument to them
 *
 * @param from The username of the message's author
 * @param to The person receiving the message
 * @param type The type of the message
 * @param messageDate The date of the message
 * @param text The text of the message
 */
ChatService.prototype.sendMessage = function(from, to, type, messageDate, text) {
    var message = {
        from : from,
        to: to,
        type: type,
        messageDate: messageDate,
        text: text
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