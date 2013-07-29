function ChatRoomViewController(chatRoomName, username, remoteService, uniqueId) {
	this.name = chatRoomName;
	this.username = username;
	this.remoteService = remoteService;
	this.uniqueId = uniqueId;
	this.$uiDom = "";
	this.windowFocus = true;
	this.missedMessage = true;
	
	var self = this;
	
	
	// On page focus
	$(window).focus(function() {
		self.windowFocus = true;
	})
	// On page blur
	.blur(function() {
		self.windowFocus = false;
	});
	
	/**
	 * Assign the $uiDom property a string of HTML markup, and append it to the
	 * .chat-holder
	 */
	function setUpUi(self) {
		self.$uiDom = $(
			'<div class="chatroom-container" id="' + self.uniqueId + '">'
		   		+ '<div class="chatroom-title-wrapper">'
		   		+ 	'<p class="chatroom-title unread"><i class="icon-user"></i>&nbsp;&nbsp;' + parseUserName(self.name) + '<i class="icon-minus pull-right"></i></p>'
		   		+ '</div>'
		   		+ '<div class="chatroom-chat"></div>'
		   		+ '<div>'
		   		+	'<form action="">'
		   		+		'<div class="row-fluid">'
		   		+			'<div class="span12">'
		   		+				'<input class="chatRoomMessageField" type="text">'
		   		+			'</div>'
		   		+		'</div>'
		   		+		'<div class="row-fluid">'
		   		+			'<div class="span12">'
		   		+				'<input class="chatroomMessageButton btn btn-success" type="submit" value="Send" />'
		   		+			'</div>'
		   		+		'</div>'
		   		+	'</form>'
		   		+ '</div>'
		   	+ '</div>');
	    self.$uiDom.click(function() {
	    	handleMessageReadClick();
	    });	
	    self.$uiDom.find(".chatRoomMessageField").focus(function() {
	    	handleMessageReadClick();
	    });	
	    
		$(".chat-holder").append(self.$uiDom);
	}
	
	function parseUserName(name){
		var idx = name.indexOf('@');
		if(idx != -1) {
			return name.substring(0,idx);
		}
		else{
			return name;
		}
	}
	
	/**
	 * This function will check if there are unread messages and modify the
	 * one-on-one chat boxes accordingly
	 */
    function handleMessageReadClick()
    {
    	if (self.missedMessage) {
    		self.$uiDom.find(".chatroom-title").removeClass("unread");
    		self.missedMessage = false;
    	}
    }
	
	function onMessageSend(self) {
	    var $chatRoomMessageField = self.$uiDom.find('.chatRoomMessageField');
	    self.$uiDom.find('.chatroomMessageButton').click(function() {
	    	if ($.trim($chatRoomMessageField.val()) != "") {
	    		var clientMessage = $chatRoomMessageField.val();
	    		self.remoteService.sendMessage(self.username, self.name, "chat", clientMessage);
	    		$chatRoomMessageField.val('');
	    	}
	    });
	}

	setUpUi(self);
	onMessageSend(self);	
}

/**
 * Display the message received in a one-on-one chat room
 * 
 * @param message The message passed from ChatRoomService.onMessage
 */
ChatRoomViewController.prototype.displayChatMessage = function(message) {

	var $chatbox = "";
	
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
		var dateString = date.getHours() + ":" + minutes;
		return dateString;
	}
	
	
		$chatbox = this.$uiDom.find(".chatroom-chat");
		if (message.from != this.username && !this.windowFocus && !this.missedMessage)
		{
			this.missedMessage = true;
			this.$uiDom.find(".chatroom-title").addClass("unread");
		}

	$chatbox.append('<p>' + '(' + buildDateString() + ')' + ' ' +
			message.from + ': ' + MessageUtils.prepareMessage(message.text) + '</p>');
	$chatbox.scrollTop($chatbox[0].scrollHeight);
};

ChatRoomViewController.prototype.getWidth = function() {
	return this.$uiDom.width();
};

ChatRoomViewController.prototype.show = function() {
	this.$uiDom.show();
};

ChatRoomViewController.prototype.hide = function() {
	this.$uiDom.hide();
};

ChatRoomViewController.prototype.focus = function() {
	this.$uiDom.find($('.chatRoomMessageField')).focus();
};
