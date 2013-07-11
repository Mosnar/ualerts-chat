function ChatRoomManager() {
	this.chatRoomList = new Array();
}

ChatRoomManager.prototype.addChatRoom = function(chatRoomName, username, remoteService) {
	var room = new ChatRoom(chatRoomName, username, remoteService);
	
	function displayChatRoom() {
		if (chatRoomName != "all") {
			$(".chat-holder").append(room.$uiDom);
		}
	}
	
	this.chatRoomList.push(room);
	displayChatRoom();
	return room;
};

ChatRoomManager.prototype.removeChatRoom = function() {
	for (var i = 0; i < this.chatRoomList.length; i++) {
		if (this.chatRoomList[i].name == name) {
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

ChatRoomManager.prototype.focusOnChatRoom = function(name) {
	
};