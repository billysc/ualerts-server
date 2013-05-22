/*
 * File created on May 20, 2013
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

function EditFixtureStrategy(controller,
    buildingService, positionHintService, roomService) {
  this.controller = controller;
  this.buildingService = buildingService;
  this.positionHintService = positionHintService;
  this.roomService = roomService;
}

EditFixtureStrategy.prototype.whenModalReady = function(source, $modal) {
  var strategy = this;
  
  this.buildingService.reset();
  this.positionHintService.reset();
  this.roomService.reset();

  var $building = $("#building");
  $building.typeahead({
    source: function(query, process) {
      strategy.buildingService.getAllBuildings(process);
    }
  });
  
  var $room = $("#room");
  $room.typeahead({
    source: function(query, process) {
      strategy.roomService.getRooms(process);
    }
  });
  
  var $positionHint = $("#positionHint");
  $positionHint.typeahead({
    source: function(query, process) {
      strategy.positionHintService.getAllPositionHints(process);
    } 
  });
  
  $building.blur(function() {
    strategy.whenBuildingSelected(this);
  });

};

EditFixtureStrategy.prototype.whenBuildingSelected = function(buildingElement) {
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

/**
 * Displays the provided fixture in the fixtures table view.
 * @param fixture the fixture to display
 */
EditFixtureStrategy.prototype.whenFormAccepted = function(responseBody) {
  var fixture = responseBody.fixture;
  this.controller.updateFixtureInView(fixture);
};

