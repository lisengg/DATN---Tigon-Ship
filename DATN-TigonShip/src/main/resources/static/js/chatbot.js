
    (function(d, m){

    var kommunicateSettings =
        {"appId":"201517d7ed96459cbdfec1b5b649a10d9",
    	"popupWidget":true,
    	//"automaticChatOpenOnNavigation":true,
    	//"openConversationOnNewMessage":false,
    	// "voiceOutput":true,
        
         "voiceRate":1,
          "voiceInput":true,
           "voiceInputTimeout": 5, //in sec 
         "language": "vi-VN",
         "emojilibrary": true,
        // "attachment": true,
    	"quickReplies":["Giá vé của một tuyến là bao nhiêu?","Quy trình đặt vé?","Thanh toán", "Quy trình hủy vé?", "Quy tắc đi tàu cao tốc?"]};

	
    var s = document.createElement("script"); 
    s.type = "text/javascript"; s.async = true;
    s.src = "https://widget.kommunicate.io/v2/kommunicate.app";
    var h = document.getElementsByTagName("head")[0]; h.appendChild(s);
    window.kommunicate = m; 
    m._globals = kommunicateSettings;
})(document, window.kommunicate || {});