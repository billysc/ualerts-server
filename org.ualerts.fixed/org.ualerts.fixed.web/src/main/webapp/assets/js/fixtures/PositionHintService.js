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

function PositionHintService() {
  this.positionHints = null;
}

PositionHintService.prototype.reset = function() {
  this.positionHints = null;
};

PositionHintService.prototype.getAllPositionHints = function(callback) {
  if (typeof callback != "function") return;
  if (this.positionHints == null) {
    var controller = this;
    var ajaxCallback = function(responseBody) {
      controller.onFetchPositionHints(responseBody);
      callback(controller.positionHints);
    };
    $.get(CONTEXT_URL + "/api/positionHints", ajaxCallback, 'json');
  }
  else {
    callback(this.positionHints);
  }
};

PositionHintService.prototype.onFetchPositionHints = function(responseBody) {
  this.positionHints = responseBody.positionHints;
};
