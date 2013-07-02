function ChatRoomService() {
  this.chatRoom = null;
  this.username = null;
}

ChatRoomService.prototype.onMessage = function(message) {
  if (message.type != "chat" || this.chatRoom == null) {
    return;
  }
  
  this.chatRoom.displayChatMessage(message);
};

ChatRoomService.prototype.setUsername = function(username) {
  this.username = username;
};

ChatRoomService.prototype.createChatRoom = function() {
  this.chatRoom = new ChatRoom();
};