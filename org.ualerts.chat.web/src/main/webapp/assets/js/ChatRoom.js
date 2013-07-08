function ChatRoom(chatRoomName, username) {
	this.name = chatRoomName;
	this.username = username;
	this.UIDom = '<div class="chatroom-container">' +
   		+ '<div style="position: relative"><p class="chatroom-title"><i class="icon-user"></i>&nbsp;&nbsp;' + this.name + '<i class="icon-minus pull-right"></i></p></div>'
   		+ '<div id="' + this.name + '" class="chatroom-chat"></div>'
   		+ '<div><input class="chatroom-message-field" type="text"></div>'
	+ '</div>';
}

ChatRoom.prototype.displayChatMessage = function(message) {	
  if (message.to == "all") {
	  console.log("displayChatMessage was called");
	  var $chatbox = $('#chatbox');
	  var date = new Date(message.messageDate);
	  
	  var minutes = date.getMinutes();
	  if (minutes < 10) {
	    minutes = "0" + minutes;
	  }
	  
	  var dateString = date.getHours() + ":" + minutes;
	  $chatbox.append('<p>' + '(' + dateString + ')' + ' ' +
	      message.from + ': ' + message.text + '</p>');
	  $chatbox.scrollTop($chatbox[0].scrollHeight);
  }
};

/**
 * Bind methods to chatroom related actions
 */
ChatRoom.prototype.bindChatroomActions = function() {
	var chatRoom = this;
	
	/**
	 * Create a new chatroom for the user whose plus sign is clicked
	 */
	$('table').on('click', '.add-chat', function() {
		console.log('the add-chat was clicked');
		console.log(chatRoom.UIDom);
		$('.chat-holder').append(chatRoom.UIDom);
	});

	
    $('.chat-holder').on('click', '.icon-minus', function() {
    	console.log('icon minus was clicked');
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