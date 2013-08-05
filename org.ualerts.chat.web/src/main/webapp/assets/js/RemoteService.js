/**
 * The service facade that handles messages from the controller.
 */
function RemoteService() {
    this.listeners = new Array();
    this.ws = null;
}

/**
 * Add an object's callback function to the array of callback functions.
 *
 * @param callback A subscriber's callback function that the RemoteService will
 *                 when it loops through the array in 
 *                 RemoteService.notifyListeners(). It must be a Callback object,
 *                 which has an execute method
 */
RemoteService.prototype.addListener = function(callback) {
	if (typeof callback != "object" || typeof callback.execute != "function")
		console.log('The callback ' + callback + ' is not an object of the Callback class');
	  this.listeners.push(callback);
};

/**
 * Remove an object's callback function from the array of callback functions.
 *
 * @param callback The callback function to remove from the listeners array
 */
RemoteService.prototype.removeListener = function(callback) {    
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
RemoteService.prototype.sendMessage = function(from, to, type, text) {
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
RemoteService.prototype.connect = function() {
	var remoteS = this;
    this.ws = new SockJS(CONTEXT_PATH + "/ui/sockjs/connector");
    this.ws.onmessage = function(event) {
    	console.log('The event passed to the SockJS onmessage(event) is: ' + event);
    	var message = $.parseJSON(event.data);
        for (var i = 0; i < remoteS.listeners.length; i++) {
        	remoteS.listeners[i].execute(message);
        }
    };
};

/**
 * Disconnect from the service
 */
RemoteService.prototype.disconnect = function() {
    console.log("You have disconnected");
};

/**
 * Send a POST request to check if the username is valid
 *
 * @param username The username to check validity for
 * @param callback The method to call when the Ajax call is done
 */
RemoteService.prototype.checkUsername = function(username, callback) {
	var storedUsername = username;
	
    $.ajax({
        type: "POST",
        url: window.location.href + "checkName",
        data: { name: username }
    }).done(function(jsonObj) {
        callback(jsonObj, storedUsername);
    });
};

/**
 * Send a POST request for the service to create a participant
 */
RemoteService.prototype.login = function(pageControllerReference,callback) {
    $.ajax({
        type: "POST",
        url: window.location.href + "login",
        data: {}
    }).done(function(data) {
        callback(pageControllerReference,data);
    });
};

RemoteService.prototype.createChatRoom = function(chatRoomName, username,pageControllerReference,callback) {
	$.ajax({
		type: "POST",
		url: window.location.href + "createNewConversation",
		data: {conversationName: chatRoomName, username: username}
	}).done(function(jsonObj) {
		 if (JSON.parse(jsonObj).result == "valid" ){
			 callback(pageControllerReference,chatRoomName);
		 };		
	});
};