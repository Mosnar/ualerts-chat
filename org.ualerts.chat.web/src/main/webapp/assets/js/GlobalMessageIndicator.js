function GlobalMessageIndicator(remoteService) {
	var numMessages = 0;
	var originalTitle = document.title;
	var timer = null;
	var windowFocus = true;
	var soundFile = CONTEXT_PATH + '/assets/sounds/notification';
	var flashState = true;
	
	/**
	 * Called automatically on message receive. Checks to see if notifications
	 * are necessary.
	 * @param message message object received
	 */
	GlobalMessageIndicator.prototype.onMessage = function(message) {
		if (message.type != "chat" || windowFocus == true) {
			return;
		}
		numMessages++;
		clearInterval(timer);
		startTitlePulse();
		playSound(soundFile);
	};
	
	// On page focus
	$(window).focus(function() {
		windowFocus = true;
		stopTitlePulse();
	})
	// On page blur
	.blur(function() {
		windowFocus = false;
	});
	
	/**
	 * Sets a timer to call the title notification pulsing until focus
	 */
	function startTitlePulse() {
		timer = setInterval(function(){doPulse();}, 1200);
	}

	/**
	 * Begins flashing the page title with notifications of messages
	 */
	function doPulse() {
		if (flashState) {
			document.title = originalTitle;
		} else {
			var msgGrammar = "s";
			if (numMessages == 1) {
				msgGrammar = "";
			}
			
			document.title = "(" + numMessages + " unread message"+ msgGrammar +") " + originalTitle;
		}
		flashState = !flashState;
	}
	
	/**
	 * Stops the title notifications
	 */
	function stopTitlePulse() {
		document.title = originalTitle;
		clearInterval(timer);
		numMessages = 0;
	}
	
	/**
	 * Play a sound file in the browser using HTML5 audio tags or fallback to
	 * embed tags.
	 * @param fileName file to play (function will assume a .mp3 & .ogg exist
	 * 	of the same name.
	 */
	function playSound(fileName) {   
        document.getElementById("sound").innerHTML='<audio autoplay="autoplay"><source src="' + fileName + '.mp3" type="audio/mpeg" /><source src="' + fileName + '.ogg" type="audio/ogg" /><embed hidden="true" autostart="true" loop="false" src="' + fileName +'.mp3" /></audio>';
    }

}