function ChatRoomService(remoteService) {
  this.username = null;
  this.remoteService = remoteService;
  this.chatRoomManager = new ChatRoomManager();
}

ChatRoomService.prototype.onMessage = function(message) {
	var chatRoomName = "";
	var room = "";
	
	if (message.type != "chat" || this.chatRoomManager.chatRoomList.length == 0) {
		return;
	}
	
	switch(message.to) {
	case this.username: // to me, from <User1>
		chatRoomName = message.from;
		break;
	case "all":
		chatRoomName = "all";
		break;
	default:
		chatRoomName = message.to;
	}
	
	room = this.getChatRoom(chatRoomName);
	if (room == false) {
		room = this.createChatRoom(chatRoomName);
	}
	room.displayChatMessage(message);
};

ChatRoomService.prototype.setUsername = function(username) {
	this.username = username;
};

ChatRoomService.prototype.createChatRoom = function(chatRoomName) {
	return this.chatRoomManager.addChatRoom(chatRoomName, this.username, this.remoteService);
};


ChatRoomService.prototype.removeChatRoomByName = function(name) {
	this.chatRoomManager.removeChatRoom(name);
};

ChatRoomService.prototype.getChatRoom = function(chatRoomName) {
	return this.chatRoomManager.getChatRoom(chatRoomName);
};

ChatRoomService.prototype.getChatRoomManager = function() {
	return this.chatRoomManager;
};