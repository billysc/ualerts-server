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
function FormViewController() {
}

FormViewController.prototype.modalReady = function(source, $modal) {
  var controller = this;

  $modal.find(".btn-primary").click(function() {
    controller.submitForm(this, $modal);
  });
  
  $modal.find("form").submit(function() {
    controller.submitForm(this, $modal);
  });
};

FormViewController.prototype.submitForm = function(source, $modal) {
  var controller = this;
  var successCallback = function(responseBody) {
    controller.onSuccess(this, responseBody, $modal);
  };
  var errorCallback = function(request, status, ex) {
    controller.onError(this, request, status, ex, $modal);
  };

  var $form = $modal.find("form");
  $.ajax({
    url: $form.attr("action"),
    cache: false,
    data: $form.serialize(),
    type: "POST",
    dataType: "json",
    success: successCallback,
    error: errorCallback
  });
};

FormViewController.prototype.onSuccess = function(source, responseBody, $modal) {
  if (responseBody.success) {
    this.whenFormAccepted(responseBody);
    $modal.modal('hide');
  }
  else {
    var $form = $modal.find("form");
    this.displayFormErrors($form, responseBody.errors);
    $(".modal-body").scrollTop(0);
  }
};

FormViewController.prototype.onError = function(source, request, 
    status, ex, $modal) {
  alert("Something happened: " + status + ": " + ex);
};

FormViewController.prototype.whenFormAccepted = function(responseBody) {
};

FormViewController.prototype.displayFormErrors = function($form, errors) {
  $form.find("[data-for]").html('');
  if (typeof errors != "object")
    return;
  if (typeof errors.global == "object") {
    $form.find("[data-for='_global']").html(errors.global.clean()
        .join("<br />"));
  }
  if (typeof errors.fields == "object") {
    for (fieldName in errors.fields) {
      var fieldErrors = errors.fields[fieldName];
      $form.find("[data-for='" + fieldName + "']")
        .html(fieldErrors.clean().join("<br />"));
    }
  }
};
