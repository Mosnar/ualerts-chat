function ChatRoomService(remoteService) {
  this.username = null;
  this.remoteService = remoteService;
  this.chatRoomViewControllerManager = new ChatRoomViewControllerManager();
}

ChatRoomService.prototype.onMessage = function(message) {
	var chatRoomName = "";
	var room = "";
	
	if (message.type != "chat" || this.chatRoomViewControllerManager.chatRoomList.length == 0) {
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
	
	room = this.getChatRoomViewController(chatRoomName);
	if (room == false) {
		room = this.createChatRoomViewController(chatRoomName);
	}
	room.displayChatMessage(message);
};

ChatRoomService.prototype.setUsername = function(username) {
	this.username = username;
};

ChatRoomService.prototype.createChatRoomViewController = function(chatRoomName) {
	return this.chatRoomViewControllerManager.addChatRoomViewController(chatRoomName, this.username, this.remoteService);
};

ChatRoomService.prototype.removeChatRoomByName = function(name) {
	this.chatRoomViewControllerManager.removeChatRoomViewController(name);
};

ChatRoomService.prototype.getChatRoomViewController = function(chatRoomName) {
	return this.chatRoomViewControllerManager.getChatRoomViewController(chatRoomName);
};

ChatRoomService.prototype.getChatRoomViewControllerManager = function() {
	return this.chatRoomViewControllerManager;
};