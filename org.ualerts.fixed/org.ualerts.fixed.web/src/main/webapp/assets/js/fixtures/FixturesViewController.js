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

function FixturesViewController(buildingService, positionHintService) {
  FormViewController.call(this);
  this.buildingService = buildingService;
  this.positionHintService = positionHintService;
  this.fixturesListTable = null;
}

FixturesViewController.prototype = new FormViewController();
FixturesViewController.prototype.constructor = FixturesViewController;

FixturesViewController.prototype.whenDocumentReady = function(source) {
  var fixturesTable = $("#fixturesList");
  var emptyTable = (fixturesTable.find("tbody tr").size() == 0);
  this.fixturesListTable = fixturesTable.dataTable({
    aaSorting: [ [0, 'asc'], [1, 'asc'] ],
    bPaginate: false,
    bInfo: false,
    aoColumns: [ null, null, null, null, null, { bSortable: false }]
  });
  
  if (emptyTable) {
    fixturesTable.parent().hide();
  }
  
  var controller = this;
  $("#addFixture").on("modalLoaded", function(event, $modal) { 
    controller.whenModalReady(this, $modal);
  });
};

FixturesViewController.prototype.whenModalReady = function(source, $modal) {
  FormViewController.prototype.whenModalReady.call(this, source, $modal);
  
  var controller = this;

  var $building = $("#building");
  $building.typeahead({
    source: function(query, process) {
      controller.buildingService.getAllBuildings(process);
    }
  });
  
  var $positionHint = $("#positionHint");
  $positionHint.typeahead({
    source: function(query, process) {
      controller.positionHintService.getAllPositionHints(process);
    } 
  });
  
  $building.blur(function() {
    controller.whenBuildingSelected(this);
  });
};

FixturesViewController.prototype.whenBuildingSelected = function(buildingElement) {
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
FixturesViewController.prototype.whenFormAccepted = function(responseBody) {
  var fixture = responseBody.fixture;
  $("#fixturesListEmpty").remove();
  $("#fixturesList").parent().show();

  //Add the row. Return value has index value to retrieve row from dataTables
  var rowControls = $("#rowControls").html();
  var row = $("#fixturesList").show().dataTable().fnAddData([
    fixture.buildingAbbreviation + " " + fixture.room,
    fixture.positionHint,
    fixture.ipAddress,
    fixture.macAddress,
    fixture.inventoryNumber,
    rowControls
  ]);
  
  // Get new row and wrap as jQuery object
  var $newRow = $(this.fixturesListTable.fnGetNodes(row[0]));
  $newRow.data("entity-id", fixture.id);
  var currentColor = $newRow.css("backgroundColor");
  $newRow.addClass("updated");
  
  // Wait, fade out, then remove CSS styles and updated class
  $newRow.find("td").delay(4000).animate({backgroundColor: currentColor}, 
      1000, function() {
    $(this).css("backgroundColor", "").parent().removeClass("updated");
  });
  
};

