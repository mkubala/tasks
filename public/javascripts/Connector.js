var maku = maku || {};
maku.tasks = maku.tasks || {};
maku.tasks.Connector = function() {

	function doAjax(params) {
		$.ajax(params.url, {
			async : params.async || false,
			type : params.type || "GET",
			data : params.data,
			success : function(data, textStatus, jqXHR) {
				params.callback(data, textStatus, jqXHR);
			}
		});
	}

	this.get = function(params) {
		doAjax(params);
	};

	this.post = function(params) {
		params.type = "POST";
		params.async = false;
		doAjax(params);
	};

}
