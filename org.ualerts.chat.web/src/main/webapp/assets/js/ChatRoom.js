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
		var cleanName = removeSpaces(self.name);
		self.$uiDom = $(
			'<div class="chatroom-container" id="' + cleanName + '">'
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
	
	/**
	 * Returns the supplied string with spaces replaced with underscores
	 * @param string to clean
	 * @returns clean string
	 */
	function removeSpaces(string) {
		return string.replace(/ /g,"_");
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
	console.log('attempting onMessageSend');
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
			message.from + ': ' + MessageUtils.prepareMessage(message.text) + '</p>');
	$chatbox.scrollTop($chatbox[0].scrollHeight);
};
