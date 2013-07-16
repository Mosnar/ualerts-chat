function ChatRoomManager() {
	this.chatRoomList = new Array();
}

ChatRoomManager.prototype.addChatRoom = function(chatRoomName, username, remoteService) {
	this.redraw();
	var room = new ChatRoom(chatRoomName, username, remoteService);

	this.chatRoomList.push(room);

	return room;
};

ChatRoomManager.prototype.removeChatRoom = function(chatRoomName) {
	for (var i = 0; i < this.chatRoomList.length; i++) {
		if (this.chatRoomList[i].name == chatRoomName) {
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
	var chatRoomWidth = $('.chatroom-container:last').width();
	var numChatRooms = $('.chatroom-container').length;
	var sumChatRoomWidth = chatRoomWidth * (numChatRooms + 1 );
	console.log('sumChatRoomWidth: ' + sumChatRoomWidth);
	console.log('$(window).width(): ' + $(window).width());
	if ($(window).width() < sumChatRoomWidth) {
		console.log('if reached');
		$('.chatroom-container:visible').first().hide();
		console.log($('.chatroom-container[display="inline-block"]'));
	}
};

ChatRoomManager.prototype.focusOnChatRoom = function(chatRoomName) {
	
	//find this chatRoom in the array
	var selectedIndex = null;	
	for (var i = 0; i < this.chatRoomList.length; i++) {
		if (this.chatRoomList[i].name == chatRoomName) {
			selectedIndex = i;
			break;
		}
	}

	//if chatRoom's DOM entry exists, remove it in
	//preparation for prepending it 
	var chatId = "#" + chatRoomName + ".chatroom-container";
	$(chatId).remove();
	
	//prepend this chatRoom's DOM entry before any others
	$('.chat-holder').prepend(this.chatRoomList[selectedIndex].$uiDom);
	
	//set this chatRoom's array position to be first
	if (selectedIndex >= 0 && selectedIndex < this.chatRoomList.length) {
		this.chatRoomList.move(selectedIndex, 0);
	}
};

Array.prototype.move = function (from, to) {
	this.splice(to, 0, this.splice(from, 1)[0]);
};