function ChatRoomService() {
  this.username = null;
  this.chatRoomList = new Array();
}

ChatRoomService.prototype.onMessage = function(message) {
  if (message.type != "chat" || this.chatRoomList.length == 0) {
    return;
  }
  
  if(message.to == "all") {
	  var room = this.getChatRoom("all");
	  room.displayChatMessage(message);
  }
};

ChatRoomService.prototype.setUsername = function(username) {
  this.username = username;
};

ChatRoomService.prototype.createChatRoom = function(chatRoomName) {
	var room = new ChatRoom(chatRoomName, this.username);
	this.chatRoomList.push(room);
};

ChatRoomService.prototype.getChatRoom = function(chatRoomName) {
	var list = this.chatRoomList;
	for (var i = 0; i < list.length; i++) {
		if (list[i].name == chatRoomName) {
			return list[i];
		}
	}
};