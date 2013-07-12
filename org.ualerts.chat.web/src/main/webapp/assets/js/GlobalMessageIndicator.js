function GlobalMessageIndicator(remoteService) {
	var numMessages = 0;
	var originalTitle = document.title;
	var timer;
	var windowFocus = true;
	GlobalMessageIndicator.prototype.onMessage = function(message) {
		
		$(window).focus(function() {
			windowFocus = true;
			stopTitlePulse();
			console.log('Got focus');
		})
			.blur(function() {
				windowFocus = false;
				console.log('Lost focus');
		});
		
		if (message.type == "chat") {
			if (windowFocus == false) {
				numMessages++;
				startTitlePulse(numMessages);

				console.log('Received message while out of focus. Timer started');
			}
		}
	}
	
	function startTitlePulse(numMessages) {
		var msgGrammar = "s";
		if (numMessages == 1) {
			msgGrammar = "";
		}
		
		document.title = "(" + numMessages + " unread message"+ msgGrammar +") " + originalTitle;
		timer = setTimeout(function(){resetTitlePulse(numMessages);},1200);
	}

	function resetTitlePulse(num) {
		document.title = originalTitle;
		timer = setTimeout(function(){startTitlePulse(numMessages);},1200);	
	}
	
	function stopTitlePulse() {
		document.title = originalTitle;
		clearTimeout(timer);
		numMessages = 0;
	}

}