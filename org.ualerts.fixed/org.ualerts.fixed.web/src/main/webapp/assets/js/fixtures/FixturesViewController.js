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

function FixturesViewController() {
  FormViewController.call(this);
  this.controlStrategies = {};
  this.controlFunction = null;
  this.fixturesListTable = null;
}

FixturesViewController.prototype = new FormViewController();
FixturesViewController.prototype.constructor = FixturesViewController;

FixturesViewController.prototype.addControlStrategy = function(
    controlFunction, strategy) {
  this.controlStrategies[controlFunction] = strategy;
};

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
  $("div.container").on('modalLoaded', '.modal-trigger', function(event, $modal) {
    controller.whenModalReady(this, $modal);
  });
};

FixturesViewController.prototype.whenModalReady = function(source, $modal) {
  FormViewController.prototype.whenModalReady.call(this, source, $modal);
  
  var $source = $(source);
  this.controlFunction = $source.data("control-function");
  this.controlStrategies[this.controlFunction].whenModalReady(source, $modal);
};

FixturesViewController.prototype.whenFormAccepted = function(responseBody) {
  this.controlStrategies[this.controlFunction].whenFormAccepted(responseBody);
};

FixturesViewController.prototype.addFixtureToView = function(fixture) {
  $("#fixturesListEmpty").remove();
  $("#fixturesList").parent().show();

  var rowControls = this.replaceIdPlaceholder(fixture.id, 
      $("#rowControls").html());
  
  // Add the row. Return value has index value to retrieve row from dataTables
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
  $newRow.attr("data-entity-id", fixture.id);
  
  // Wait, fade out, then remove CSS styles and updated class
  this.highlightRow($newRow);
};

FixturesViewController.prototype.replaceIdPlaceholder = function(id, html) {
  var placeholder = "{id}";
  var length = placeholder.length;
  var i = html.indexOf(placeholder);
  while (i != -1) {
    html = html.substring(0, i) + id + html.substring(i + length);
    i = html.indexOf(placeholder); 
  }
  return html;
};

FixturesViewController.prototype.updateFixtureInView = function(fixture) {
  var $row = $("#fixturesList tr[data-entity-id='" + fixture.id + "']");
  this.fixturesListTable.fnUpdate(
    [ 
      fixture.buildingAbbreviation + " " + fixture.room,
      fixture.positionHint,
      fixture.ipAddress,
      fixture.macAddress,
      fixture.inventoryNumber,
      $row.find("td:last").html()
    ],
    $row.get(0)
  );
  this.highlightRow($row);
};

FixturesViewController.prototype.removeFixtureFromView = function(fixture) {
  var controller = this;
  var row = $("#fixturesList tr[data-entity-id='" + fixture.id + "']").get(0);
  $(row).addClass("removeHighlight").delay(1000).fadeOut(1000, function() {
    controller.fixturesListTable.fnDeleteRow(row);
  });
};

FixturesViewController.prototype.highlightRow = function($row) {
  var currentColor = $row.addClass("updated").css("backgroundColor");
  $row.find("td").delay(4000).animate({backgroundColor: currentColor}, 
      1000, function() {
    $(this).css("backgroundColor", "").parent().removeClass("updated");
  });
};