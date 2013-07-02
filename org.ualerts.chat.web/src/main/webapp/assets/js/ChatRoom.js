function ChatRoom() {
}

ChatRoom.prototype.displayChatMessage = function(message) {
  var $chatbox = $('#chatbox');
  var date = new Date(message.messageDate);
  
  var minutes = date.getMinutes();
  if (minutes < 10) {
    minutes = "0" + minutes;
  }
  
  var dateString = date.getHours() + ":" + minutes;
  $chatbox.append('<p>' + '(' + dateString + ')' + ' ' +
      message.from + ': ' + message.text + '</p>');
  $chatbox.scrollTop($chatbox[0].scrollHeight);
};