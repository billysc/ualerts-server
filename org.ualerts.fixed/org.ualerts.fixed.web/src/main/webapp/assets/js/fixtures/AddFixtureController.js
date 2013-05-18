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

function AddFixtureController(buildingService) {
  FixturesViewController.call(this);
  this.buildingService = buildingService;
}

AddFixtureController.prototype = new FixturesViewController();
AddFixtureController.prototype.constructor = AddFixtureController;

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
