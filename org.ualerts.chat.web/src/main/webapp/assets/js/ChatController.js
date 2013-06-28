/**
 * The controller that subscribes listeners to the ChatService
 *
 * @param chatService The ChatService to work with
 */
function ChatController(chatService) {
    this.service = chatService;
    this.username = "";
}

/**
 * Set up the chat.
 */
ChatController.prototype.init = function() {
    this.setUpListeners();
    this.nameSubmitDisable();
    this.messageDisable();
    this.handleNameSubmit();
    this.handleMessageSubmit();
    $('#usernameField').focus();
};

/**
 * Add listeners to the ChatService object.
 */
ChatController.prototype.setUpListeners = function() {
    this.service.addListener(this.onMessage);
};

/**
 * Disable the message field.
 */
ChatController.prototype.messageDisable = function() {
    var $field = $('#messageField');
    var $button = $('#messageButton');
    $field.attr('readonly', 'readonly');
    $field.attr('placeholder', 'Please enter a username');
    $button.attr('disabled', 'disabled');
};

/**
 * Disable the submit name button.
 */
ChatController.prototype.nameSubmitDisable = function() {
	$('#nameButton').attr('disabled', 'disabled');
};

/**
 * Call updateUserName, provided with the username field's value
 * Call acknowledgeUser() to display a welcome message
 * Enable the message input field
 */
ChatController.prototype.handleNameSubmit = function() {
    var chatC = this;
    
    $('#nameButton').click(function() {
        var $username = $('#usernameField').val();
    	
        if ($.trim($username) != "") {
            //chatC.updateUserName($username);
            chatC.acknowledgeUser();
            chatC.prepareMessageField();
            $('#messageField').attr('placeholder', 'Type a message...').focus();
            $("#nameForm").hide();
        }
        chatC.service.submitName();
    });
    
    this.service.connect();
};

/**
 * Create a username prototype property for the ChatController class
 *
 * @param name The username
 */
ChatController.prototype.updateUsername = function(name) {
    this.username = name;
};

/**
 * Display a welcome message on the view.
 */
ChatController.prototype.acknowledgeUser = function() {
    var chatC = this;
    $('#user-welcome').text('Welcome, ' + chatC.username + ".");
};

/**
 * Make the message field ready for typing.
 */
ChatController.prototype.prepareMessageField = function() {
    var $field = $('#messageField');
    var $button = $('#messageButton');
    
    $field.removeAttr('readonly');
    $field.removeAttr('value');
    $button.removeAttr('disabled');
};

/**
 * Pass the message field text and username to the ChatService object's
 * sendMessage method
 */
ChatController.prototype.handleMessageSubmit = function() {
    var chatC = this;
    $messageField = $('#messageField');
    $('#messageButton').click(function() {
        if ($messageField.val() != "") {
            var clientMessage = $messageField.val();
            chatC.service.sendMessage("<b>" + chatC.username + "</b>", "all", "chat", clientMessage);
            $messageField.val('');
        }
    });
};

// The console logs "Uncaught TypeError: Object [object Array] has not method 'addToRoster'"
//
///**
// * Make HTML to append to the connected users table
// * 
// * @param user The user to be enrolled on the connected users table
// */
//ChatController.prototype.addToRoster = function(message) {
//	var htmlString = '<tr><td class="online">' + message.from + '</td></tr>';
//	$('#connected-users tbody').append(htmlString);
//};

/**
 * Perform an action when called by the ChatService object
 *
 * @param message The message object received
 */
ChatController.prototype.onMessage = function(message) {
	//var chatC = this;
    var $chatbox = $('#chatbox');
    var date = new Date(message.messageDate);
    var rosterAddedMessages = new Array();
    
    var minutes = date.getMinutes();
    if (minutes < 10) {
    	minutes = "0" + minutes;
    }
    
    if (message.type === "ROSTER_ADDED") {
    	rosterAddedMessages.push(message);
        var dateString = date.getHours() + ":" + minutes;
        $chatbox.append('<p>' + '(' + dateString + ') ' + message.text + '</p>');
        
        // Add the user to the connected users table
        var htmlString = "";
        for (var i = 0; i < rosterAddedMessages.length; i++) {
        	htmlString += '<tr><td class="online">' + rosterAddedMessages[i].from + '</td></tr>';
        	$('#connected-users tbody').append(htmlString);
        }
    }
    
    if (message.type === "chat") {
        var dateString = date.getHours() + ":" + minutes;
        $chatbox.append('<p>' + '(' + dateString + ')' + ' ' +
            message.from + ': ' + message.text + '</p>');
    }
};

/**
 * 
 */
ChatController.prototype.validateUsername = function() {
	var chatC = this;
	this.updateUsername($.trim($('#usernameField').val()));
    this.service.checkUsername(chatC.username, this.handleValidity);
};

/**
 * Perform a DOM operation if the username is valid or invalid, based on the boolean value
 * returned from the ChatService.checkUsername method
 *
 * @param jsonObj The JSON object returned by the ChatService.checkUsername
 * 		  method, which has a "result" property that is either "valid" or
 * 		  "invalid"
 */
ChatController.prototype.handleValidity = function(jsonObj, storedUsername) {	
    if ((new String(JSON.parse(jsonObj).result).valueOf() == new String("valid").valueOf())
    		&& $('#usernameField').val() === storedUsername) {
        console.log("The username " + storedUsername + " is valid");
        $('#username-validity').html('<div class="check"></div>&nbsp;<span> ' + storedUsername + ' </span>');
        $('#nameButton').removeAttr('disabled');
    }
    else if (new String(JSON.parse(jsonObj).result).valueOf() == new String("invalid").valueOf()) {
        console.log("The username " + storedUsername + "  is not valid");
        $('#username-validity').html('<div class="cancel"></div>&nbsp<span> ' + storedUsername + ' </span>');
        $('#nameButton').attr('disabled', 'disabled');

    }
};