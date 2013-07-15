/**
 * The controller that subscribes listeners to the RemoteService
 *
 * @param remoteService The RemoteService to work with
 */
function PageController(remoteService, chatRoomService) {
    this.service = remoteService;
    this.username = "";
    this.connectedUsers = new Array();
    this.chatRoomService = chatRoomService;
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
 * Add listeners to the RemoteService object.
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
    var self = this;
    
    function setUpUi() {
    	self.acknowledgeUser();
    	self.prepareMessageField();
        $('#messageField').attr('placeholder', 'Type a message...').focus();
        $("#nameForm").hide();
    }
    
    $('#nameButton').click(function() {    	
        var $username = $('#usernameField').val();
        $username = self.htmlEncode($username);
        
        if ($.trim($username) != "") {
        	setUpUi();
        }
        
        self.service.submitName();
        self.chatRoomService.setUsername($username);
        self.chatRoomService.createChatRoom("all");
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
    var self = this;
    $('#user-welcome').text('Welcome, ' + self.username + ".");
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
 * Pass the message field text and username to the RemoteService object's
 * sendMessage method
 */
PageController.prototype.handleMessageSubmit = function() {
    var self = this;
    $messageField = $('#messageField');
    $('#messageButton').click(function() {
        if ($.trim($messageField.val()) != "") {
            var clientMessage = $messageField.val();
            self.service.sendMessage("<b>" + self.username + "</b>", "all", "chat", clientMessage);
            $messageField.val('');
        }
    });
};

/**
 * Make HTML to append the user to the connected users table, if the user is not
 * already a connected user
 * 
 * @param user The user to be enrolled on the connected users table
 */
PageController.prototype.addToRoster = function(user) {
	var self = this;
	function userExists() {
		for (var i = 0; i < self.connectedUsers.length; i++) {
			if (user == self.connectedUsers[i]) {
				return true;
			}
			return false;
		}
	}
	
	function addUser() {
		self.connectedUsers.push(user);
		var htmlString = '<tr><td class="online"><i class="icon-user"></i>&nbsp;&nbsp;' + user
			+ '<span class="add-chat pull-right"><i class="icon-plus"></i></span></td></tr>';
		$('#connected-users > tbody').prepend(htmlString);
	}
	
	function addChatClickHandler() {
		$('#connected-users > tbody tr:first .add-chat').click(function() {
			var contact = $.trim($(this).parent().text());
			if (self.chatRoomService.getChatRoom(contact) == false) {
				self.chatRoomService.createChatRoom(contact, this.username, self.service);
			}
			self.chatRoomService.getChatRoom(contact).$uiDom.find($('.chatRoomMessageField')).focus();
		});
	}
	
	if (userExists()) {
		return;
	};
	addUser();
	addChatClickHandler();

};

/**
 * Perform an action when called by the RemoteService object
 *
 * @param message The message object received
 */
PageController.prototype.onMessage = function(message) {
	var $chatbox = $('#chatbox');
	
	/**
	 * Build a string to be displayed with the chat message text
	 * 
	 * @returns dateString The messageDate property of the message in the form
	 * 			hh:mm
	 */
	function buildDateString() {
		var date = new Date(message.messageDate);
		var minutes = date.getMinutes();
		if (minutes < 10) {
			minutes = "0" + minutes;
		}
		var dateString = date.getHours() + ": " + minutes;
		return dateString;
	}
    
    if (message.type == "ROSTER") {
    	switch(message.subType) {
    	case "ADDED":
    		$chatbox.append('<p>' + '(' + buildDateString() + ') ' + message.from + ' has entered the chat.<p>');
    		this.addToRoster(message.from);
    		break;
    	case "REMOVED":
    		this.chatRoomService.removeChatRoomByName(message.from);
    		$('#connected-users tbody tr td').each(function() {
    			if ($.trim($(this).text()) == message.from) {
    				$(this).parent().remove();
    			}
    		});
    		break;
    	case "CONTENT":
    		this.addToRoster(message.from);
    	}
    }
};

/**
 * Have RemoteService validate the username by calling checkUsername method on it
 */
PageController.prototype.validateUsername = function() {
	var self = this;
	var name = $.trim($('#usernameField').val());
	this.updateUsername(name);
	if (name == "") {
		return;
	}
    this.service.checkUsername(self.htmlEncode(self.username), this.handleValidity);
};

/**
 * Perform a DOM operation if the username is valid or invalid, based on the boolean value
 * returned from the RemoteService.checkUsername method
 *
 * @param jsonObj The JSON object returned by the RemoteService.checkUsername
 * 		  method, which has a "result" property that is either "valid" or
 * 		  "invalid"
 */
PageController.prototype.handleValidity = function(jsonObj, storedUsername) {
    if (JSON.parse(jsonObj).result == "valid"
		&& $.trim($('#usernameField').val()) === storedUsername) {
        $('#username-validity').html('<div class="check"></div>&nbsp;<span> ' + storedUsername + ' </span>');
        $('#nameButton').removeAttr('disabled');
    }
    else if (JSON.parse(jsonObj).result == "invalid") {
        $('#username-validity').html('<div class="cancel"></div>&nbsp<span> ' + storedUsername + ' </span>');
        $('#nameButton').attr('disabled', 'disabled');

    };
};
