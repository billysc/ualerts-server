function AddFixtureController(buildingService) {
  this.buildingService = buildingService;
}

AddFixtureController.prototype.modalReady = function(source, $modal) {
  var controller = this;
  var $building = $("#building");
  $building.typeahead({
    source: function(query, process) {
      controller.buildingService.getAllBuildings(process);
    }
  });
  
  $building.blur(function() {
    controller.whenBuildingSelected(this);
  });
  
  $modal.find(".btn-primary").click(function() {
    controller.submitForm(this, $modal);
  });
  
  $modal.find("form").submit(function() {
    controller.submitForm(this, $modal);
  });
};

AddFixtureController.prototype.whenBuildingSelected = function(buildingElement) {
  var value = $(buildingElement).val();
  var building = this.buildingService.findMatchingBuilding(value);
  if (building == null) {
    $('#buildingId').val('');
    $(buildingElement).val('');
  }
  else {
    $("#buildingId").val(building.id);
    $(buildingElement).val(building.name);
  }
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
  submitForm($form, $form.attr("action"), "POST", "json", successCallback, 
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
