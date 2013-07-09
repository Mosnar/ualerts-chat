function ChatRoomService(remoteService) {
  this.username = null;
  this.chatRoomList = new Array();
  this.remoteService = remoteService;
}

ChatRoomService.prototype.onMessage = function(message) {
  if (message.type != "chat" || this.chatRoomList.length == 0) {
    return;
  }
  
  var chatRoomName = "";
  
  // to me, from <User1>
//  if (message.from == this.username) {
//	  chatRoomName = message.to;
//  }
//  if ((message.to == this.username) && (message.from != this.username)) {
//	  chatRoomName == message.to;
//  }
//  if (message.to == "all") {
//	  chatRoomName = message.to;
//  }
  
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
  if (room == false) {
	  room = this.createChatRoom(chatRoomName);
	  console.log('this.createChatRoom(chatRoomName) is: ' + room);
	  console.log('message.to is: ' + message.to);
  }
  room.displayChatMessage(message);
  console.log('sending a messge to ' + message.to + ', from ' + message.from);
};

ChatRoomService.prototype.setUsername = function(username) {
  this.username = username;
};

ChatRoomService.prototype.createChatRoom = function(chatRoomName) {
	var room = new ChatRoom(chatRoomName, this.username, this.remoteService);
	this.chatRoomList.push(room);
	if (chatRoomName != "all") {
		$(".chat-holder").append(room.$uiDom);
	}
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