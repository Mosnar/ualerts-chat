function ChatRoomViewControllerManager(hiddenChatRoomViewController) {
	this.chatRoomList = new Array();
	this.uniqueIdNum = 0;
	this.hiddenChatRoomViewController = hiddenChatRoomViewController;
}

ChatRoomViewControllerManager.prototype.addChatRoomViewController = function(chatRoomName, username, remoteService, roomType) {
	var uniqueId = 'room-' + this.uniqueIdNum;
	var domain = this.getDomain(chatRoomName);
	var room = new ChatRoomViewController(chatRoomName, username, remoteService, uniqueId, domain, roomType);
	this.uniqueIdNum++;
	this.chatRoomList.push(room);
	this.redraw();
	return room;
};

ChatRoomViewControllerManager.prototype.removeChatRoomViewController = function(chatRoomName) {
	for (var i = 0; i < this.chatRoomList.length; i++) {
		if (this.chatRoomList[i].name == chatRoomName) {
			this.chatRoomList.splice(i, 1);
			return;
		}
	}
};

ChatRoomViewControllerManager.prototype.getChatRoomViewController = function(chatRoomName) {
	var list = this.chatRoomList;
	for (var i = 0; i < list.length; i++) {
		if (list[i].name == chatRoomName) {
			return list[i];
		};
	}
	return false;
};

ChatRoomViewControllerManager.prototype.redraw = function() {
	var windowWidth = $(window).width() - 75;
	var lastIndex = this.chatRoomList.length - 1;
	var referenceChatRoom = this.chatRoomList[lastIndex];
	var chatRoomWidth = referenceChatRoom.getWidth();
	var count = 0;
	var maxNum = Math.floor(windowWidth/chatRoomWidth);
	
	this.hiddenChatRoomViewController.clear();
	for (var i = this.chatRoomList.length - 1; i >= 0; i--) {		
		if(count < maxNum) {
			this.chatRoomList[i].show();
			this.hiddenChatRoomViewController.removeElement(this.chatRoomList[i].name, this.chatRoomList[i].uniqueId);
			count++;
		}
		else {
			this.chatRoomList[i].hide();
			this.hiddenChatRoomViewController.addElement(this.chatRoomList[i].name, this.chatRoomList[i].uniqueId);
		}
	}
};

ChatRoomViewControllerManager.prototype.focusOnChatRoom = function(chatRoomName) {
	//find this chatRoom in the array
	var selectedIndex = null;
	for (var i = 0; i < this.chatRoomList.length; i++) {
		if (this.chatRoomList[i].name == chatRoomName) {
			selectedIndex = i;
			break;
		}
	}
	
	$('.chatroom-container:contains(' + chatRoomName + ')').insertAfter($('.chatroom-container').last());
	
	//set this chatRoom's array position to be last
	this.chatRoomList.move(selectedIndex, this.chatRoomList.length - 1);
	this.redraw();
	this.chatRoomList[selectedIndex].focus();
};

Array.prototype.move = function (from, to) {
	this.splice(to, 0, this.splice(from, 1)[0]);
};

ChatRoomViewControllerManager.prototype.size = function() {
	return this.chatRoomList.length;
};

ChatRoomViewControllerManager.prototype.getDomain = function(fullyQualifiedName) {
	idx = fullyQualifiedName.indexOf('@');
	return fullyQualifiedName.substring(idx+1);
};
