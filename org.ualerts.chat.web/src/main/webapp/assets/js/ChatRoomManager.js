function ChatRoomManager() {
	this.chatRoomList = new Array();
}

ChatRoomManager.prototype.addChatRoom = function(chatRoomName, username, remoteService) {
	var room = new ChatRoom(chatRoomName, username, remoteService);
	this.chatRoomList.push(room);
	this.redraw();
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

	var windowWidth = $(window).width();
	console.log('windowWidth: ' + windowWidth);

	var chatRoomWidth = $('.chatroom-container:visible').last().width();
	console.log('chatRoomWidth: ' + chatRoomWidth);
	var count = 0;
	var maxNum = windowWidth/chatRoomWidth;
	console.log('maxNum: ' + maxNum);

	for (var i = this.chatRoomList.length - 1; i >= 1; i--) {		
		if(count < maxNum) {
			this.chatRoomList[i].$uiDom.find('.chatroom-container').show();
			console.log('show');
			count++;
			console.log('count: ' + count);
		}
		else {
			this.chatRoomList[i].$uiDom.find('.chatroom-container').hide();
			console.log('this.chatRoomList: ' + this.chatRoomList);
			console.log(this.chatRoomList[i].$uiDom.find('.chatroom-container'));
			console.log('hide');
		}
	}

//	var chatRoomWidth = $('.chatroom-container:visible').last().width();
//	var numChatRooms = $('.chatroom-container').length;
//	var sumChatRoomWidth = chatRoomWidth * (numChatRooms + 1 );
//	console.log('sumChatRoomWidth: ' + sumChatRoomWidth);
//	console.log('$(window).width(): ' + $(window).width());
//	if ($(window).width() < sumChatRoomWidth) {
//		console.log('if reached');
//		$('.chatroom-container:visible').first().hide();
//	}
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
        
        $('.chatroom-container:contains('+chatRoomName+')').insertAfter($('.chatroom-container').last());


        //set this chatRoom's array position to be last
        this.chatRoomList.move(selectedIndex, this.chatRoomList.length-1);
};

Array.prototype.move = function (from, to) {
	this.splice(to, 0, this.splice(from, 1)[0]);
};