function GlobalMessageIndicator(remoteService) {
	var numMessages = 0;
	var originalTitle = document.title;
	var timer = null;
	var windowFocus = true;
	var soundFile = CONTEXT_PATH + '/assets/sounds/notification';
	
	GlobalMessageIndicator.prototype.onMessage = function(message) {
		if (message.type != "chat" || windowFocus == false) {
			return;
		}
		numMessages++;
		clearTimeout(timer);
		startTitlePulse(numMessages);
		playSound(soundFile);
	};
	
	$(window).focus(function() {
		windowFocus = true;
		stopTitlePulse();
	})
	.blur(function() {
			windowFocus = false;
	});
	
	function startTitlePulse(numMessages) {
		var msgGrammar = "s";
		if (numMessages == 1) {
			msgGrammar = "";
		}
		
		document.title = "(" + numMessages + " unread message"+ msgGrammar +") " + originalTitle;
		timer = setTimeout(function(){resetTitlePulse(numMessages);}, 1200);
	}

	function resetTitlePulse(num) {
		document.title = originalTitle;
		timer = setTimeout(function(){startTitlePulse(numMessages);}, 1200);	
	};
	
	function stopTitlePulse() {
		document.title = originalTitle;
		clearTimeout(timer);
		numMessages = 0;
	};
	
	/**
	 * Play a sound file in the browser using HTML5 audio tags or fallback to
	 * embed tags.
	 * @param fileName file to play (function will assume a .mp3 & .ogg exist
	 * 	of the same name.
	 */
	function playSound(fileName) {   
		console.log('Playing: ' + fileName);
        document.getElementById("sound").innerHTML='<audio autoplay="autoplay"><source src="' + fileName + '.mp3" type="audio/mpeg" /><source src="' + fileName + '.ogg" type="audio/ogg" /><embed hidden="true" autostart="true" loop="false" src="' + fileName +'.mp3" /></audio>';
    };

}