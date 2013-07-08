function ChatRoomService() {
  this.username = null;
  this.chatRoomList = new Array();
}

ChatRoomService.prototype.onMessage = function(message) {
  if (message.type != "chat" || this.chatRoomList.length == 0) {
    return;
  }
  
  var chatRoomName = "";
  
  switch(message.to) {
  case this.username:	// to me, from <User1>
	  chatRoomName = message.from;
	  break;
  case "all":
	  chatRoomName = "all";
	  console.log('case "all" executed');
	  break;
  default:	// to <User1>, from me
	  chatRoomName = message.to;
  }
  var room = this.getChatRoom(chatRoomName);
  console.log('This is what this.getChatRoom("all") returned: ' + room);
  if (room == false) {
	  room = this.createChatRoom(message.to);
	  console.log('This is what the value of this.createChatRoom(message.to) is: ' + room);
	  console.log('This is what the value of message.to is: ' + message.to);

	  room.displayChatMessage(message);
	  return;
  }
  room.displayChatMessage(message);
};

ChatRoomService.prototype.setUsername = function(username) {
  this.username = username;
};

ChatRoomService.prototype.createChatRoom = function(chatRoomName) {
	var room = new ChatRoom(chatRoomName, this.username);
	console.log('this is what the value of new ChatRoom(chatRoomName, this.username) is: ' + room);
	this.chatRoomList.push(room);
};

ChatRoomService.prototype.getChatRoom = function(chatRoomName) {
	var list = this.chatRoomList;
	for (var i = 0; i < list.length; i++) {
		if (list[i].name == chatRoomName) {
			return list[i];
		}
	}
	return false;
};