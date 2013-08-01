function HiddenChatRoomViewController() {
	var self = this;
	
	this.listeners = new Array();
	this.$uiDom = $("#overflow-chatroom-button");
	this.$uiDom.on('click', '.dropdown-menu li', function() {
		var name = $(this).text();
		var fullyQualified = $(this).attr('fully-qualified');
		self.notifyListeners(fullyQualified);
	});
}
const ATCHAR = '@';
const ALL_ATCHAR = 'all@';

HiddenChatRoomViewController.prototype.addListener = function(callback) {
	this.listeners.push(callback);
};

HiddenChatRoomViewController.prototype.notifyListeners = function(fullyQualified) {
	for (var i = 0; i < this.listeners.length; i++) {
		this.listeners[i].execute(fullyQualified);
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
	var allChat = ALL_ATCHAR + this.getDomain(name);
	if (name == allChat) {
		return;
	}
	var listEntry = $('<li fully-qualified="' + name +'" id="hidden-' + uniqueId + '">' + this.getName(name) + '</li>');
	var listEntryId = this.$uiDom.find('.dropdown-menu li:contains(' + this.getName(name) + ')').attr('id');
	if (listEntryId != "hidden-" + uniqueId) {
		this.$uiDom.find('.dropdown-menu').append(listEntry);
		this.updateButtonCount();
	}
	this.show();
};

HiddenChatRoomViewController.prototype.removeElement = function(name, uniqueId) {
	var allChat = ALL_ATCHAR + this.getDomain(name);
	if (name == allChat) {
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
	
	if (this.$uiDom.find('.dropdown-menu li[fully-qualified="' + name + '"]').length == 1) {
		return true;
	}
	else {
		return false;
	}
};

HiddenChatRoomViewController.prototype.getDomain = function(fullyQualifiedName) {
	var idx = fullyQualifiedName.indexOf(ATCHAR);
	return fullyQualifiedName.substring(idx+1);
};

HiddenChatRoomViewController.prototype.getName = function(fullyQualifiedName) {
	var idx = fullyQualifiedName.indexOf(ATCHAR);
	return fullyQualifiedName.substring(0,idx);
};
