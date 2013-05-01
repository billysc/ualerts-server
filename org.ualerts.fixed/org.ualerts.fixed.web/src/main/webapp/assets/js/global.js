
$(function() {
	$(".modalTrigger").click(function(evt) {
		evt.stopPropagation();
		var $this = $(this);
		var $target = $("" + $this.data("target"));
		var remote = $this.attr("href");
		var title = $this.data("modaltitle");
		var primaryButtonText = $this.data("primarybuttontext");
		var cancelButtonText = $this.data("cancelbuttontext");
		
		var opts = {};
		if (typeof remote != "undefined")
			opts.remote = remote;
		
		//Remove any lingering click handlers
		var $primaryButton = $target.find(".btn-primary").unbind("click");
		var $cancelButton = $target.find(".btn.cancel").unbind("click");
		
		$target.modal(opts);
		$target.find(".modal-header h3").text(title);
		if (typeof primaryButtonText == "string")
			$primaryButton.removeClass("hide").text(primaryButtonText);
		else
			$primaryButton.addClass("hide");
		
		if (typeof cancelButtonText == "string")
			$cancelButton.removeClass("hide").text(cancelButtonText)
		else
			$cancelButton.addClass("hide");
		
		if (typeof $this.data("postmodalcallback") == "string") {
			var fn = $this.data("postmodalcallback");
			window[fn]($target);
		}
		
		return false;
	});
});

function submitForm($form, url, requestType, responseType, successCallback, errorCallback) {
	$.ajax({
		url: url,
		data: $form.serialize(),
		type: requestType,
		dataType: responseType,
		success: successCallback,
		error: errorCallback
	})
}

