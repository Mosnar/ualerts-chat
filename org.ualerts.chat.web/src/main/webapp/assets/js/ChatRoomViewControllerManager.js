function ChatRoomViewControllerManager() {
	this.chatRoomList = new Array();
}

ChatRoomViewControllerManager.prototype.addChatRoomViewController = function(chatRoomName, username, remoteService) {
	var room = new ChatRoomViewController(chatRoomName, username, remoteService);
	this.chatRoomList.push(room);
	this.redraw();
	return room;
};

ChatRoomViewControllerManager.prototype.removeChatRoomViewController = function(chatRoomName) {
	for (var i = 0; i < this.chatRoomList.length; i++) {
		if (this.chatRoomList[i].name == chatRoomName) {
			this.chatRoomList.splice(i, 1);
			return;
		}
	}
};

ChatRoomViewControllerManager.prototype.getChatRoomViewController = function(chatRoomName) {
	var list = this.chatRoomList;
	for (var i = 0; i < list.length; i++) {
		if (list[i].name == chatRoomName) {
			return list[i];
		};
	}
	return false;
};

ChatRoomViewControllerManager.prototype.redraw = function() {
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

ChatRoomViewControllerManager.prototype.focusOnChatRoom = function(chatRoomName) {
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
