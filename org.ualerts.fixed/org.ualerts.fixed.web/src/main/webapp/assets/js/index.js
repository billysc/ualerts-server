
function postModalDisplay_enrollFixture($modal) {
	var submitEnrollFixture = function() {
		var $form = $modal.find("form");
		var url = $form.attr("action");
		var requestType = "POST";
		var responseType = "json";
		var successCallback = function(data) {
			if (data.success)
				$modal.modal('hide');
			else
				$modal.find(".modal-body").html(data.html);
		};
		var errorCallback = function() {
			alert("Something happened");
		};
		submitForm($form, url, requestType, responseType, successCallback, errorCallback)
	};
	
	$modal.find(".btn-primary").click(submitEnrollFixture);
	$modal.find("form").submit(submitEnrollFixture);
}
