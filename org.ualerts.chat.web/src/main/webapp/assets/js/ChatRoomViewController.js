function ChatRoomViewController(chatRoomName, username, remoteService, uniqueId, chatRoomViewControllerManager) {
	this.name = chatRoomName;
	this.username = username;
	this.remoteService = remoteService;
	this.uniqueId = uniqueId;
	this.chatRoomViewControllerManager = chatRoomViewControllerManager;
	this.$uiDom = "";
	
	var self = this;
	
	/**
	 * Assign the $uiDom property a string of HTML markup, and append it to the
	 * .chat-holder
	 */
	function setUpUi(self) {
		self.$uiDom = $(
			'<div class="chatroom-container" id="' + self.uniqueId + '">'
		   		+ '<div class="chatroom-title-wrapper">'
		   		+ 	'<p class="chatroom-title"><i class="icon-user"></i>&nbsp;&nbsp;' + self.name + '<i class="icon-minus pull-right"></i></p>'
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
			
		$(".chat-holder").append(self.$uiDom);
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
	
	if (chatRoomName == 'all') {
		this.$uiDom = $('<div id="chatbox"></div>');
		$('#messageForm').before(this.$uiDom);
	}

	if (chatRoomName != 'all') {
		setUpUi(self);
		onMessageSend(self);
	}
}


/**
 * Display the message received in a one-on-one chat room
 * 
 * @param message The message passed from ChatRoomService.onMessage
 */
ChatRoomViewController.prototype.displayChatMessage = function(message) {
	var self = this;
	if ($('#overflow-chatroom-button .dropdown-menu li:contains(' + this.name + ')').length == 1) {
		self.chatRoomViewControllerManager.focusOnChatRoom(self.name);
	}
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
		var dateString = date.getHours() + ": " + minutes;
		return dateString;
	}
	
	if (message.to == "all") {
		$chatbox = $('#chatbox');
	}
	else {
		$chatbox = this.$uiDom.find(".chatroom-chat");
	}
	$chatbox.append('<p>' + '(' + buildDateString() + ')' + ' ' +
			message.from + ': ' + MessageUtils.prepareMessage(message.text) + '</p>');
	$chatbox.scrollTop($chatbox[0].scrollHeight);
};

ChatRoomViewController.prototype.getWidth = function() {
	return $(".chatroom-container#" + this.uniqueId).width();
};

ChatRoomViewController.prototype.show = function() {
	$(".chatroom-container#" + this.uniqueId).show();
};