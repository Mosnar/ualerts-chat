function ChatRoomManager() {
	this.chatRoomList = new Array();
}

ChatRoomManager.prototype.addChatRoom = function(chatRoomName, username, remoteService) {
	var room = new ChatRoom(chatRoomName, username, remoteService);
	this.chatRoomList.push(room);
	this.redraw();
	return room;
};

ChatRoomManager.prototype.removeChatRoom = function(chatRoomName) {
	for (var i = 0; i < this.chatRoomList.length; i++) {
		if (this.chatRoomList[i].name == chatRoomName) {
			this.chatRoomList.splice(i, 1);
			return;
		}
	}
};

ChatRoomManager.prototype.getChatRoom = function(chatRoomName) {
	var list = this.chatRoomList;
	for (var i = 0; i < list.length; i++) {
		if (list[i].name == chatRoomName) {
			return list[i];
		};
	}
	return false;
};

ChatRoomManager.prototype.redraw = function() {
	var windowWidth = $(window).width() - 40;
	var chatRoomWidth = $('.chatroom-container:visible').last().width();
	var count = 0;
	var maxNum = Math.floor(windowWidth/chatRoomWidth);
	
	// start from the end of the list
	// stop at index 1, because index 0 is the "all" chatroom
	for (var i = this.chatRoomList.length - 1; i >= 1; i--) {		
		if(count < maxNum) {
			$('.chatroom-container:eq(' + (i - 1) + ')').show();
			count++;
		}
		else {
			$('.chatroom-container:eq(' + (i - 1) + ')').hide();
		}
	}
};

ChatRoomManager.prototype.focusOnChatRoom = function(chatRoomName) {
	//find this chatRoom in the array
	var selectedIndex = null;
	for (var i = 0; i < this.chatRoomList.length; i++) {
		if (this.chatRoomList[i].name == chatRoomName) {
			selectedIndex = i;
			break;
		}
	}
	
	$('.chatroom-container:contains(' + chatRoomName + ')').insertAfter($('.chatroom-container').last());
	
	//set this chatRoom's array position to be last
	this.chatRoomList.move(selectedIndex, this.chatRoomList.length - 1);
};

Array.prototype.move = function (from, to) {
	this.splice(to, 0, this.splice(from, 1)[0]);
};
