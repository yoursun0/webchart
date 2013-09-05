function xmlhttpPost(strURL) {
	var self = this;
	self.xmlHttpReq = newXMLHttpRequest();
	self.xmlHttpReq.open('POST', strURL, true);
	self.xmlHttpReq.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
	self.xmlHttpReq.onreadystatechange = function() {
		if (self.xmlHttpReq.readyState == 4) {
			updatepage(self.xmlHttpReq.responseText);
		}
	};
	self.xmlHttpReq.send(getquerystring());
}

function getquerystring() {
	var form = document.forms['f1'];
	var word = form.word.value;
	
	// Running the following code before any other code will create String.trim if it's not natively available.
	if(!String.prototype.trim) {
		  String.prototype.trim = function () {
		    return this.replace(/^\s+|\s+$/g,'');
		  };
		}
	
	// Escape and trim the search keyword
	var keyword= escape(word).trim();
	qstr = 'customer=' + keyword; // NOTE: no '?' before querystring

	return qstr;
}

function updatepage(str){
	document.getElementById("result").innerHTML = str;
}