/**
 * The controller that subscribes listeners to the ChatService
 *
 * @param chatService The ChatService to work with
 */
function ChatController(chatService) {
    this.service = chatService;
    this.username = "";
    this.connectedUsers = new Array();
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
	var controller = this;
	this.service.addListener(new Callback(controller.onMessage, controller));
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
 * Set the username property for the ChatController class
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
        if ($.trim($messageField.val()) != "") {
            var clientMessage = $messageField.val();
            chatC.service.sendMessage("<b>" + chatC.username + "</b>", "all", "chat", clientMessage);
            $messageField.val('');
        }
    });
};

/**
 * Make HTML to append the user to the connected users table
 * 
 * @param user The user to be enrolled on the connected users table
 */
ChatController.prototype.addToRoster = function(user) {
	this.connectedUsers.push(user);
    var htmlString = '<tr><td class="online"><i class="icon-user"></i>&nbsp;&nbsp;' + user + '</td></tr>';
	$('#connected-users tbody').append(htmlString);
};

/**
 * Perform an action when called by the ChatService object
 *
 * @param message The message object received
 */
ChatController.prototype.onMessage = function(message) {
    var $chatbox = $('#chatbox');
    var date = new Date(message.messageDate);
    
    var minutes = date.getMinutes();
    if (minutes < 10) {
    	minutes = "0" + minutes;
    }
    
    if (message.type === "ROSTER_ADDED") {
        var dateString = date.getHours() + ":" + minutes;
        $chatbox.append('<p>' + '(' + dateString + ') ' + message.from + ' has entered the chat.</p>');
        
        this.addToRoster(message.from);
    }
    
    if (message.type === "ROSTER_REPLY") {	    
	    this.addToRoster(message.from);
    }
    
    if (message.type === "chat") {
        var dateString = date.getHours() + ":" + minutes;
        $chatbox.append('<p>' + '(' + dateString + ')' + ' ' +
            message.from + ': ' + message.text + '</p>');
        $chatbox.scrollTop($chatbox[0].scrollHeight);
    }
    
	if (this.connectedUsers.length === 0)
	{
	    var htmlString = '<tr><td class="online">' + user + '</td></tr>';
		$('#connected-users tbody').append(htmlString);
	}
};

/**
 * Have ChatService validate the username by calling checkUsername method on it
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