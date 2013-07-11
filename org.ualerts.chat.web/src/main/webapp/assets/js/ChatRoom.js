function ChatRoom(chatRoomName, username, remoteService) {
	this.name = chatRoomName;
	this.username = username;
	this.remoteService = remoteService;
	this.$uiDom = "";
	
	var self = this;
	
	/**
	 * Assign the $uiDom property a string of HTML markup, and append it to the
	 * .chat-holder
	 */
	function setUpUi(self) {
		self.$uiDom = $(
			'<div class="chatroom-container">'
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
	    $chatRoomMessageField = self.$uiDom.find('.chatRoomMessageField');
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
 * Parse a block of text to detect URLs and convert them to hyperlinks
 * @param text string to parse
 * @return text with converted links
 */
function parseUrls(text) {
	var replacedText, replacePattern1;
	
	//URLs starting with http://, https://, ftps://, ftp://
	replacePattern1 = /(\b(https?|ftps?):\/\/[-A-z0-9+&amp;@#\/%?=~_|!:,.;]*[-A-Z0-9+&amp;@#\/%=~_|])/ig;
	replacedText = text.replace(replacePattern1, '<a title="$1" href="$1" target="_blank">$1</a>');
	 
	//URLs starting with "www."
	replacePattern2 = /(^|[^\/])(www\.[\S]+(\b|$))/gim;
	replacedText = replacedText.replace(replacePattern2, '$1<a href="http://$2" target="_blank">$2</a>');
	return replacedText;
}

/**
 * Display the message received in a one-on-one chat room
 * 
 * @param message The message passed from ChatRoomService.onMessage
 */
ChatRoom.prototype.displayChatMessage = function(message) {
	
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
			message.from + ': ' + parseUrls(message.text) + '</p>');
	$chatbox.scrollTop($chatbox[0].scrollHeight);
};