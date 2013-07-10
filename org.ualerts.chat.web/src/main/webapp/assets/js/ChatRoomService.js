function ChatRoomService(remoteService) {
  this.username = null;
  this.chatRoomList = new Array();
  this.remoteService = remoteService;
}

ChatRoomService.prototype.removeChatRoomByName = function(name) {
	for (var i = 0; i < this.chatRoomList.length; i++) {
		if (this.chatRoomList[i].name == name) {
			this.chatRoomList.splice(i, 1);
			return;
		}
	}
};

ChatRoomService.prototype.onMessage = function(message) {
	var chatRoomName = "";
	var room = "";
	
	if (message.type != "chat" || this.chatRoomList.length == 0) {
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
	var room = new ChatRoom(chatRoomName, this.username, this.remoteService);
	
	function displayChatRoom() {
		if (chatRoomName != "all") {
			$(".chat-holder").append(room.$uiDom);
		}
	}
	
	this.chatRoomList.push(room);
	displayChatRoom();
	return room;
};

ChatRoomService.prototype.getChatRoom = function(chatRoomName) {
	var list = this.chatRoomList;
	for (var i = 0; i < list.length; i++) {
		if (list[i].name == chatRoomName) {
			return list[i];
		};
	}
	return false;
};