function HiddenChatRoomViewController() {
	this.listeners = new Array();
}

HiddenChatRoomViewController.prototype.addListener = function(callback) {
	this.listeners.push(callback);
};

HiddenChatRoomViewController.prototype.notifyListeners = function(name) {
	for (var i = 0; i < this.listeners.length; i++) {
		this.listeners[i].execute(name);
	}
};

HiddenChatRoomViewController.prototype.hideOverflowButton = function() {
	var numItems = $('#overflow-chatroom-button .dropdown-menu li').length;
	if (numItems == 0) {
		$('#overflow-chatroom-button').hide();
	}
};

HiddenChatRoomViewController.prototype.bindListClickHandler = function(name) {
	var self = this;
	$('#overflow-chatroom-button .dropdown-menu li:contains(' + name + ')').click(function() {
		self.notifyListeners(name);
	});
};

HiddenChatRoomViewController.prototype.addHiddenOverflow = function(name, uniqueId) {
	if (name != 'all') {
		var listEntry = $('<li id="' + uniqueId + '">' + name + '</li>');
		var listEntryId = $('#overflow-chatroom-button .dropdown-menu li:contains(' + name + ')').attr('id');
		if (listEntryId != uniqueId) {
			$('#overflow-chatroom-button .dropdown-menu').append(listEntry);
			this.bindListClickHandler(name);
			this.updateButtonCount();
		}
	}
};

HiddenChatRoomViewController.prototype.removeHiddenOverflow = function(name, uniqueId) {
	if (name != 'all') {
		$('#overflow-chatroom-button .dropdown-menu li#' + uniqueId).remove();
		this.updateButtonCount();
	}
};

HiddenChatRoomViewController.prototype.updateButtonCount = function() {
	var numItems = $('#overflow-chatroom-button .dropdown-menu li').length;
	$('#hidden-chatroom-button .button-count').text(numItems);
};

HiddenChatRoomViewController.prototype.hide = function(name, uniqueId) {
	if (name != 'all') {
		$('.chatroom-container#' + uniqueId).hide();
		$('#overflow-chatroom-button').show();
	}
};