function ChatRoomService(remoteService) {
  this.username = null;
  this.domain = null;
  this.remoteService = remoteService;
  this.hiddenChatRoomViewController = new HiddenChatRoomViewController();
  this.chatRoomViewControllerManager = new ChatRoomViewControllerManager(this.hiddenChatRoomViewController);
  
  this.hiddenChatRoomViewController.addListener(new Callback(this.chatRoomViewControllerManager.focusOnChatRoom, this.chatRoomViewControllerManager));
}

const ALL_ATCHAR = 'all@';

ChatRoomService.prototype.onMessage = function(message) {
	var chatRoomName = "";
	var room = "";
	
	if (message.type != "chat" || this.chatRoomViewControllerManager.size() == 0) {
		return;
	}
	var allChat = ALL_ATCHAR+this.domain;
	switch(message.to) {
	case this.username: // to me, from <User1>
		chatRoomName = message.from;
		break;
	case allChat:
		chatRoomName = allChat;
		break;
	default:
		chatRoomName = message.to;
	}

	room = this.getChatRoomViewController(chatRoomName);
	if (room == false) {
		room = this.createChatRoomViewController(chatRoomName);
	}
	if(this.hiddenChatRoomViewController.contains(chatRoomName)) {
		this.chatRoomViewControllerManager.focusOnChatRoom(chatRoomName);
	}
	room.displayChatMessage(message);
};

ChatRoomService.prototype.setUsername = function(username) {
	this.username = username;
};

ChatRoomService.prototype.setDomain = function(domain) {
	this.domain = domain;
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