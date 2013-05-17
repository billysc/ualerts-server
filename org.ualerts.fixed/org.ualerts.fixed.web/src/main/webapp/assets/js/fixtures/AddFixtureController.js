function AddFixtureController() {
}

AddFixtureController.prototype.modalReady = function(source, $modal) {

  var $building = $("#building");
  $building.typeahead({
    source: function(query, process) {
      if (allBuildings != null && buildingNames != null) {
        return buildingNames;
      }
      var getCallback = function(data) {
        allBuildings = data.buildings;
        buildingNames = Array();
        for (key in allBuildings) {
          var building = allBuildings[key];
          var display = autoComplete_buildingFormat.format(building.abbreviation, building.name); 
          buildingNames.push(display);
          allBuildings[key].display = display;
        }
        process(buildingNames);
      };
      $.get(CONTEXT_URL + "/api/buildings", getCallback, 'json');
    }
  });
  
  $building.blur(function() {
    var value = $(this).val();
    if (!buildingNames.contains(value)) {
      $("#buildingId").val('');
      return $(this).val('');
    }
    for (key in allBuildings) {
    if (allBuildings[key].display == value) {
        return $("#buildingId").val( allBuildings[key].id );
      }
    }
  });
  
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
