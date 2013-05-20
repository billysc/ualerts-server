/*
 * File created on May 18, 2013
 *
 * Copyright 2008-2013 Virginia Polytechnic Institute and State University
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

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
