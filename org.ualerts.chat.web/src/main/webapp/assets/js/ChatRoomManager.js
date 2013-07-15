function ChatRoomManager() {
	this.chatRoomList = new Array();
}

ChatRoomManager.prototype.addChatRoom = function(chatRoomName, username, remoteService) {
	var room = new ChatRoom(chatRoomName, username, remoteService);

	this.chatRoomList.push(room);
	
	if (chatRoomName != "all") {
		this.focusOnChatRoom(chatRoomName);
	}

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

	//if chatRoom's DOM entry exists, remove it in
	//preparation for prepending it 
	var chatId = "#" + chatRoomName + ".chatroom-container";
	$(chatId).remove();
	
	//prepend this chatRoom's DOM entry before any others
	$('.chat-holder').prepend(this.chatRoomList[selectedIndex].$uiDom);
	
	//set this chatRoom's array position to be first
	this.chatRoomList.move(selectedIndex, 0);
};

Array.prototype.move = function (from, to) {
	if (from >= 0 && from < this.chatRoomList.length
			&& to >= 0 && to < this.chatRoomList.length) {
		this.splice(to, 0, this.splice(from, 1)[0]);
	}
};