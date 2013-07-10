function ChatRoom(chatRoomName, username, remoteService) {
	this.name = chatRoomName;
	this.username = username;
	this.remoteService = remoteService;
	
	if (chatRoomName == 'all') {
		this.$uiDom = $('<div id="chatbox"></div>');
		$('#messageForm').before(this.$uiDom);
	}
	
	if (chatRoomName != 'all') {
		this.$uiDom = $('<div class="chatroom-container container-fluid">'
	   		+ '<div class="chatroom-title-wrapper row-fluid"><p class="chatroom-title span12"><i class="icon-user"></i>&nbsp;&nbsp;' + this.name + '<i class="icon-minus pull-right"></i></p></div>'
	   		+ '<div class="chatroom-chat"></div>'
	   		+ '<div class="row-fluid"><form action=""><input class="chatRoomMessageField span9" type="text"><input class="chatroomMessageButton btn btn-success span3" type="submit" value="Send" /></form></div>'
		+ '</div>');
		
		var chatR = this;
		$(".chat-holder").append(chatR.$uiDom);
		
	    // On click submit messages
	    $chatRoomMessageField = this.$uiDom.find('.chatRoomMessageField');
	    this.$uiDom.find('.chatroomMessageButton').click(function() {
	    	if ($.trim($chatRoomMessageField.val()) != "") {
	    		var clientMessage = $chatRoomMessageField.val();

	    		chatR.remoteService.sendMessage(chatR.username, chatR.name, "chat", clientMessage);
	    		console.log('sent a message to: ' + chatR.name);
	    		$chatRoomMessageField.val('');
	    	}
	    });
	}
}

ChatRoom.prototype.displayChatMessage = function(message) {	
	  console.log("displayChatMessage was called");
	  var date = new Date(message.messageDate);
	  var minutes = date.getMinutes();
	  if (minutes < 10) {
	    minutes = "0" + minutes;
	  }
	  var dateString = date.getHours() + ":" + minutes;
	  
	  if (message.to == "all") {
		  var $chatbox = $('#chatbox');
		  $chatbox.append('<p>' + '(' + dateString + ')' + ' ' +
				  message.from + ': ' + message.text + '</p>');
		  $chatbox.scrollTop($chatbox[0].scrollHeight);
		  return;
	  }
	  
	  var $chatbox = this.$uiDom.find(".chatroom-chat");
	  $chatbox.append('<p>' + '(' + dateString + ')' + ' ' +
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
		console.log('up was clicked');
    	var titleText = $this.parent().text();
    	var title = $('.chatroom-container > div > .chatroom-title:contains("' + titleText + '")');
    	var icon = $('.chatroom-container > div > .chatroom-title:contains("' + titleText + '") > i:eq(1)');
    	title.parent().siblings().show();
    	icon.removeClass('icon-plus');
    	icon.addClass('icon-minus').click(function() { downClickHandler($(this)); });

    	title.parent().siblings().show();
    };
    
    function downClickHandler($this) {
		console.log('down was clicked');
    	var titleText = $this.parent().text();
    	var title = $('.chatroom-container > div > .chatroom-title:contains("' + titleText + '")');
    	var icon = $('.chatroom-container > div > .chatroom-title:contains("' + titleText + '") > i:eq(1)');
    	title.parent().siblings().css('display', 'none');
    	icon.removeClass('icon-minus');
    	icon.addClass('icon-plus');

    	title.parent().siblings().hide();
    };
};