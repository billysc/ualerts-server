function AddFixtureController() {
}

AddFixtureController.prototype.modalReady = function(source, $modal) {
  var controller = this;
  $modal.find(".btn-primary").click(function() {
    controller.submitForm(this, $modal);
  });
  $modal.find("form").submit(function() {
    controller.submitForm(this, $modal);
  });
};

AddFixtureController.prototype.submitForm = function(source, $modal) {
  var controller = this;
  var successCallback = function(data) {
    controller.onSuccess(this, data, $modal);
  };
  var errorCallback = function(request, status, ex) {
    controller.onError(this, request, status, ex, $modal);
  };
  var $form = $modal.find("form");
  var url = $form.attr("action");
  var requestType = "POST";
  var responseType = "json";
  submitForm($form, url, requestType, responseType, successCallback, 
      errorCallback);
};

AddFixtureController.prototype.onSuccess = function(source, data, $modal) {
  if (data.success) {
    var fixture = data.fixture;
    displayFixture(fixture);
    $modal.modal('hide');
  }
  else {
    var $form = $modal.find("form");
    displayErrorsOnForm($form, data.errors);
    $(".modal-body").scrollTop(0);
  }
};

AddFixtureController.prototype.onError = function(source, request, 
    status, ex, $modal) {
  alert("Something happened: " + status + ": " + ex);
};
