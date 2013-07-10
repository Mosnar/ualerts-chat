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
		self.$uiDom = $('<div class="chatroom-container container-fluid">'
		   		+ '<div class="chatroom-title-wrapper row-fluid"><p class="chatroom-title span12"><i class="icon-user"></i>&nbsp;&nbsp;' + self.name + '<i class="icon-minus pull-right"></i></p></div>'
		   		+ '<div class="chatroom-chat"></div>'
		   		+ '<div class="row-fluid"><form action=""><input class="chatRoomMessageField span9" type="text"><input class="chatroomMessageButton btn btn-success span3" type="submit" value="Send" /></form></div>'
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
			message.from + ': ' + message.text + '</p>');
	$chatbox.scrollTop($chatbox[0].scrollHeight);
};

/**
 * Bind methods to chatroom related actions
 */
ChatRoom.prototype.bindChatroomActions = function() {
    $('.chat-holder').on('click', '.icon-minus', function() {
		var titleText = $(this).parent().text();
		var icon = $('.chatroom-container > div > .chatroom-title:contains("' + titleText + '") > i:eq(1)');
		var title = $('.chatroom-container > div > .chatroom-title:contains("' + titleText + '")');
		icon.removeClass('icon-minus');
		icon.addClass('icon-chevron-up').click(function() { upClickHandler($(this)); });
		
		title.parent().siblings().hide();
    });
    
    function upClickHandler($this) {
    	var titleText = $this.parent().text();
    	var title = $('.chatroom-container > div > .chatroom-title:contains("' + titleText + '")');
    	var icon = $('.chatroom-container > div > .chatroom-title:contains("' + titleText + '") > i:eq(1)');
    	title.parent().siblings().show();
    	icon.removeClass('icon-plus');
    	icon.addClass('icon-minus').click(function() { downClickHandler($(this)); });

    	title.parent().siblings().show();
    };
    
    function downClickHandler($this) {
    	var titleText = $this.parent().text();
    	var title = $('.chatroom-container > div > .chatroom-title:contains("' + titleText + '")');
    	var icon = $('.chatroom-container > div > .chatroom-title:contains("' + titleText + '") > i:eq(1)');
    	title.parent().siblings().css('display', 'none');
    	icon.removeClass('icon-minus');
    	icon.addClass('icon-plus');

    	title.parent().siblings().hide();
    };
};