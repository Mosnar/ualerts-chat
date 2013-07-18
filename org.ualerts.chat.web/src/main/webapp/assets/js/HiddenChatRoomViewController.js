function HiddenChatRoomViewController() {
	var self = this;
	
	this.listeners = new Array();
	this.$uiDom = $("#overflow-chatroom-button");
	this.$uiDom.on('click', '.dropdown-menu li', function() {
		var name = $(this).text();
		self.notifyListeners(name);
	});
}

HiddenChatRoomViewController.prototype.addListener = function(callback) {
	this.listeners.push(callback);
};

HiddenChatRoomViewController.prototype.notifyListeners = function(name) {
	for (var i = 0; i < this.listeners.length; i++) {
		this.listeners[i].execute(name);
	}
};

HiddenChatRoomViewController.prototype.clear = function() {
	this.$uiDom.find(".dropdown-menu").html('');
};

HiddenChatRoomViewController.prototype.hide = function() {
	var numItems = this.$uiDom.find('.dropdown-menu li').length;
	if (numItems == 0) {
		this.$uiDom.hide();
	}
};

HiddenChatRoomViewController.prototype.show = function() {
	var numItems = this.$uiDom.find('.dropdown-menu li').length;
	if (numItems > 0) {
		this.$uiDom.show();
	}
};

HiddenChatRoomViewController.prototype.addElement = function(name, uniqueId) {
	if (name == 'all') {
		return;
	}
	var listEntry = $('<li id="hidden-' + uniqueId + '">' + name + '</li>');
	var listEntryId = this.$uiDom.find('.dropdown-menu li:contains(' + name + ')').attr('id');
	if (listEntryId != "hidden-" + uniqueId) {
		this.$uiDom.find('.dropdown-menu').append(listEntry);
		this.updateButtonCount();
	}
	this.show();
};

HiddenChatRoomViewController.prototype.removeElement = function(name, uniqueId) {
	if (name == 'all') {
		return;
	}
	this.$uiDom.find('.dropdown-menu li#hidden-' + uniqueId).remove();
	this.updateButtonCount();
	this.hide();
};

HiddenChatRoomViewController.prototype.updateButtonCount = function() {
	var numItems = this.$uiDom.find('.dropdown-menu li').length;
	$('#hidden-chatroom-button .button-count').text(numItems);
};


HiddenChatRoomViewController.prototype.contains = function(name) {
	if (this.$uiDom.find('.dropdown-menu li:contains(' + name + ')').length == 1) {
		return true;
	}
	else {
		return false;
	}
};
