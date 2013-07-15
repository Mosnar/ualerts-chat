function MessageUtils() {};

MessageUtils.prepareMessage = function(text) {
    var message = MessageUtils.addLinks(text);
    return message;
};

/**
 * Parse a block of text to detect URLs and convert them to hyperlinks
 * @param text string to parse
 * @return text with converted links
 */
MessageUtils.addLinks = function(text) {
    var replacedText, replacePattern1;

	//URLs starting with http://, https://, ftps://, ftp://
	replacePattern1 = /(\b(https?|ftps?):\/\/[-A-z0-9+&amp;@#\/%?=~_|!:,.;]*[-A-Z0-9+&amp;@#\/%=~_|])/ig;
	replacedText = text.replace(replacePattern1, '<a title="$1" href="$1" target="_blank">$1</a>');

	//URLs starting with "www."
	replacePattern2 = /(^|[^\/])(www\.[\S]+(\b|$))/gim;
	replacedText = replacedText.replace(replacePattern2, '$1<a href="http://$2" target="_blank">$2</a>');
	return replacedText;
};