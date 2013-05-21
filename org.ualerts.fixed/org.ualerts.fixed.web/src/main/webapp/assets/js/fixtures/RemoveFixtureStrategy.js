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

function RemoveFixtureStrategy(controller) {
  this.controller = controller;
  this.clickHandlers = null;
}

RemoveFixtureStrategy.prototype.whenModalReady = function(source, $modal) {
  var controller = this;
  
  var $button = $modal.find(".btn-primary");
  this.copyAndClearClickHandlers($button, $modal);

  $button.click(function(event) {
    event.stopPropagation();
    event.preventDefault();
    controller.displayConfirmation(this, $modal);
  });
};

RemoveFixtureStrategy.prototype.displayConfirmation = function(source, $modal) {
  var $button = $modal.find(".btn-primary");
  this.restoreClickHandlers($button);
  $modal.find("#deleteConfirmation").show();
};

RemoveFixtureStrategy.prototype.copyAndClearClickHandlers = function($button, $modal) {
  var elem = $button[0];
  
  // Make a deep copy of the already-bound click events
  var data = $.hasData(elem) && $._data(elem);
  this.clickHandlers = $.extend(true, {}, data.events.click);
  $button.unbind("click");
};

RemoveFixtureStrategy.prototype.restoreClickHandlers = function($button, $modal) {
  for (key in this.clickHandlers) {
    $button.on('click', this.clickHandlers[key].handler);
  }
  this.clickHandlers = null;
};

RemoveFixtureStrategy.prototype.whenFormAccepted = function(responseBody) {
  this.controller.removeFixtureFromView(responseBody.fixture);
};