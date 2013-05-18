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
}

FixturesViewController.prototype = new FormViewController();
FixturesViewController.prototype.constructor = FixturesViewController;

/**
 * Displays the provided fixture in the fixtures table view.
 * @param fixture the fixture to display
 */
FixturesViewController.prototype.displayFixture = function(fixture) {
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
  var $newRow = $(fixturesListTable.fnGetNodes(row[0]));
  $newRow.data("entity-id", fixture.id);
  var currentColor = $newRow.css("backgroundColor");
  $newRow.addClass("updated");
  
  // Wait, fade out, then remove CSS styles and updated class
  $newRow.find("td").delay(4000).animate({backgroundColor: currentColor}, 
      1000, function() {
    $(this).css("backgroundColor", "").parent().removeClass("updated");
  });
  
};

FixturesViewController.prototype.submitForm = function(source, $modal) {
  var controller = this;
  var successCallback = function(data) {
    controller.onSuccess(this, data, $modal);
  };
  var errorCallback = function(request, status, ex) {
    controller.onError(this, request, status, ex, $modal);
  };

  var $form = $modal.find("form");
  this.ajaxSubmit($form, $form.attr("action"), "POST", "json", successCallback, 
      errorCallback);
};

FixturesViewController.prototype.onSuccess = function(source, data, $modal) {
  if (data.success) {
    var fixture = data.fixture;
    this.displayFixture(fixture);
    $modal.modal('hide');
  }
  else {
    var $form = $modal.find("form");
    this.displayErrorsOnForm($form, data.errors);
    $(".modal-body").scrollTop(0);
  }
};

FixturesViewController.prototype.onError = function(source, request, 
    status, ex, $modal) {
  alert("Something happened: " + status + ": " + ex);
};

