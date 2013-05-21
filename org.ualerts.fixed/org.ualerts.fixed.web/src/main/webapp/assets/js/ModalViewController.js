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

function ModalViewController() {
  
}

ModalViewController.prototype.whenDocumentReady = function(source) {
  var controller = this;
  $("body").on('click', '.modal-trigger', function(event) {
    controller.whenModalTriggered(this, event);
  });  
};

ModalViewController.prototype.whenModalTriggered = function(source, event) {
//  event.stopPropagation();
  event.preventDefault();
  var $source = $(source);
  var $target = $("" + $source.data("target"));
  var remote = $source.attr("href");
  var title = $source.data("title");
  var primaryButtonText = $source.data("primary-button-text");
  var cancelButtonText = $source.data("cancel-button-text");
  
  // Remove any lingering click handlers
  var $primaryButton = $target.find(".btn-primary").unbind("click");
  var $cancelButton = $target.find(".btn.cancel").unbind("click");
  
  $target.unbind("hidden");
  $target.unbind("loaded").on('loaded', function() {
    $source.trigger('modalLoaded', [$target]);
  });
    
  var opts = {};
  if (typeof remote != "undefined") {
    opts.remote = remote;
  }

  $target.modal(opts);

  $target.find(".modal-header h3").text(title);
  if (typeof primaryButtonText == "string")
    $primaryButton.removeClass("hide").text(primaryButtonText);
  else
    $primaryButton.addClass("hide");
  
  if (typeof cancelButtonText == "string")
    $cancelButton.removeClass("hide").text(cancelButtonText);
  else
    $cancelButton.addClass("hide");
  
  
  $target.on('hidden', function() {
    // remove so modal content will be fetched next time
    $(this).removeData('modal');  
  });

};