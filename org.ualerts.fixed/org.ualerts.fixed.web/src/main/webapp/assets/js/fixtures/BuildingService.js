function BuildingService() {
  this.allBuildings = null;
  this.buildingNames = null;
}

BuildingService.prototype.getAllBuildings = function(callback) {
  if (typeof callback != "function") return;
  if (this.allBuildings == null) {
    var controller = this;
    var ajaxCallback = function(responseBody) {
      controller.onFetchBuildings(responseBody);
      callback(controller.buildingNames);
    };
    $.get(CONTEXT_URL + "/api/buildings", ajaxCallback, 'json');
  }
  else {
    callback(this.buildingNames);
  }
};

BuildingService.prototype.findMatchingBuilding = function(value) {
  if (this.allBuildings != null) {
    for (var i = 0; i < this.allBuildings.length; i++) {
      var building = this.allBuildings[i];
      if (building.name == value) {
        return building;
      }      
    }
  }
  return null;
};

BuildingService.prototype.onFetchBuildings = function(responseBody) {
  this.allBuildings = responseBody.buildings;
  this.buildingNames = new Array();
  for (var i = 0; i < this.allBuildings.length; i++) {
    this.buildingNames.push(this.allBuildings[i].name);
  }
};
