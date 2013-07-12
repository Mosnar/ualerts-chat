function ChatRoomManager() {
	this.chatRoomList = new Array();
}

ChatRoomManager.prototype.addChatRoom = function(chatRoomName, username, remoteService) {
	var room = new ChatRoom(chatRoomName, username, remoteService);
	
	function displayChatRoom() {
		if (chatRoomName != "all") {
			$(".chat-holder").append(room.$uiDom);
		}
	}
	
	this.chatRoomList.push(room);
	displayChatRoom();
	this.focusOnChatRoom(room.name);
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
	
};

ChatRoomManager.prototype.focusOnChatRoom = function(chatRoomName) {
	console.log("chatRoomName: " + chatRoomName);
	var selectedIndex = null;
	
	for (var i = 0; i < this.chatRoomList.length; i++) {
		console.log('for reached');
		if (this.chatRoomList[i].name == chatRoomName) {
			selectedIndex = i;
			console.log("selected index: " + selectedIndex);
			console.log("i: " +  i);
			console.log(this.chatRoomList[i]);
		}
	}
	console.log("chatroom before html" + this.chatRoomList[selectedIndex].$uiDom.text());

	removeDom();
	console.log("chatroom after html" + this.chatRoomList[selectedIndex].$uiDom.text());


	this.chatRoomList.move(selectedIndex, 0);	
	$('.chatroom-holder').prepend(this.chatRoomList[selectedIndex].$uiDom);
	console.log(this.chatRoomList[selectedIndex].$uiDom);
		
	function removeDom() {
		console.log("selectedIndex: " + selectedIndex);
		console.log('chatRoomName: ' + chatRoomName);
		$('#chatroom-container  .chatroom-title:contains(' + chatRoomName + ')').remove();
//		self.chatRoomList[selectedIndex].$uiDom.find('#chatroom-container').remove();
//		self.chatRoomList[selectedIndex].$uiDom.find('#chatroom-container').css('border', '1px solid black');
	}
	

//	var room = this.getChatRoom(name);
//	room.$uiDom.remove();
//	this.removeChatRoom(room);
};

Array.prototype.move = function (from, to) {
	this.splice(to, 0, this.splice(from, 1)[0]);
};