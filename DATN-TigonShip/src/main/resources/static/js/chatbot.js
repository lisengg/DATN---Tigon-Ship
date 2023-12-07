
    (function(d, m){

    var kommunicateSettings =
        {"appId":"201517d7ed96459cbdfec1b5b649a10d9",
    	"popupWidget":true,
    	//"automaticChatOpenOnNavigation":true,
    	//"openConversationOnNewMessage":false,
    	 "voiceOutput":true,
         "voiceName":"Google Deutsch", // Replace Google Deutsch with the voiceName or an array of voiceNames from Voice Names and Language.
         "voiceRate":1,
          "voiceInput":true,
           "voiceInputTimeout": 5, //in sec 
         "language": "vi-VN",
         "emojilibrary": true,
        // "attachment": true,
    	"quickReplies":["Speak with an Agent","Book a Demo","Sample Bots"]};

	
    var s = document.createElement("script"); 
    s.type = "text/javascript"; s.async = true;
    s.src = "https://widget.kommunicate.io/v2/kommunicate.app";
    var h = document.getElementsByTagName("head")[0]; h.appendChild(s);
    window.kommunicate = m; 
    m._globals = kommunicateSettings;
})(document, window.kommunicate || {});