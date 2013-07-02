/**
 * The controller that subscribes listeners to the ChatService
 *
 * @param chatService The ChatService to work with
 */
function PageController(chatService) {
    this.service = chatService;
    this.username = "";
    this.connectedUsers = new Array();
}

/**
 * Set up the chat.
 */
PageController.prototype.init = function() {
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
PageController.prototype.setUpListeners = function() {
	var controller = this;
	this.service.addListener(new Callback(controller.onMessage, controller));
};

/**
 * Disable the message field.
 */
PageController.prototype.messageDisable = function() {
    var $field = $('#messageField');
    var $button = $('#messageButton');
    $field.attr('readonly', 'readonly');
    $field.attr('placeholder', 'Please enter a username');
    $button.attr('disabled', 'disabled');
};

/**
 * Disable the submit name button.
 */
PageController.prototype.nameSubmitDisable = function() {
	$('#nameButton').attr('disabled', 'disabled');
};

/**
 * Encode HTML characters to sanitize HTML input.
 * 
 * Create an in-memory div, set it's inner text, and return it.
 */
PageController.prototype.htmlEncode = function(string) {
	return $('<div />').text(string).html();
};

/**
 * Call updateUserName, provided with the username field's value
 * Call acknowledgeUser() to display a welcome message
 * Enable the message input field
 */
PageController.prototype.handleNameSubmit = function() {
    var pageC = this;
    
    $('#nameButton').click(function() {
        var $username = $('#usernameField').val();
        $username = pageC.htmlEncode($username);
    	
        if ($.trim($username) != "") {
        	pageC.acknowledgeUser();
        	pageC.prepareMessageField();
            $('#messageField').attr('placeholder', 'Type a message...').focus();
            $("#nameForm").hide();
        }
        pageC.service.submitName();
    });
    
    this.service.connect();
};

/**
 * Set the username property for the PageController class
 *
 * @param name The username
 */
PageController.prototype.updateUsername = function(name) {
    this.username = name;
};

/**
 * Display a welcome message on the view.
 */
PageController.prototype.acknowledgeUser = function() {
    var pageC = this;
    $('#user-welcome').text('Welcome, ' + pageC.username + ".");
};

/**
 * Make the message field ready for typing.
 */
PageController.prototype.prepareMessageField = function() {
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
PageController.prototype.handleMessageSubmit = function() {
    var pageC = this;
    $messageField = $('#messageField');
    $('#messageButton').click(function() {
        if ($.trim($messageField.val()) != "") {
            var clientMessage = $messageField.val();
            pageC.service.sendMessage("<b>" + pageC.username + "</b>", "all", "chat", clientMessage);
            $messageField.val('');
        }
    });
};

/**
 * Make HTML to append the user to the connected users table
 * 
 * @param user The user to be enrolled on the connected users table
 */
PageController.prototype.addToRoster = function(user) {
	this.connectedUsers.push(user);
    var htmlString = '<tr><td class="online"><i class="icon-user"></i>&nbsp;&nbsp;' + user + '</td><i class="icon-comment"></i></tr>';
	$('#connected-users tbody').append(htmlString);
};

/**
 * Perform an action when called by the ChatService object
 *
 * @param message The message object received
 */
PageController.prototype.onMessage = function(message) {
    var $chatbox = $('#chatbox');
    var date = new Date(message.messageDate);
    
    var minutes = date.getMinutes();
    if (minutes < 10) {
    	minutes = "0" + minutes;
    }
    
    switch(message.type) {
    case "ROSTER_ADDED":
        var dateString = date.getHours() + ":" + minutes;
        $chatbox.append('<p>' + '(' + dateString + ') ' + message.from + ' has entered the chat.</p>');
        
        this.addToRoster(message.from);
    	break;
    case "ROSTER_REPLY":
    	if (message.from === message.to) {
    		console.log('Ignoring updating the DOM for receiving a ROSTER_CONTENT from myself.');
    	}
    	else {
    		this.addToRoster(message.from);
    	}
    	break;
    case "chat":
        var dateString = date.getHours() + ":" + minutes;
        $chatbox.append('<p>' + '(' + dateString + ')' + ' ' +
            message.from + ': ' + message.text + '</p>');
        $chatbox.scrollTop($chatbox[0].scrollHeight);
    	break;
    }
};

/**
 * Have ChatService validate the username by calling checkUsername method on it
 */
PageController.prototype.validateUsername = function() {
	var pageC = this;
	this.updateUsername($.trim($('#usernameField').val()));
    this.service.checkUsername(pageC.htmlEncode(pageC.username), this.handleValidity);
};

/**
 * Perform a DOM operation if the username is valid or invalid, based on the boolean value
 * returned from the ChatService.checkUsername method
 *
 * @param jsonObj The JSON object returned by the ChatService.checkUsername
 * 		  method, which has a "result" property that is either "valid" or
 * 		  "invalid"
 */
PageController.prototype.handleValidity = function(jsonObj, storedUsername) {	
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