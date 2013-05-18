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

FormViewController.prototype.ajaxSubmit = function($form, url, requestType, 
    responseType, successCallback, errorCallback) {
  $.ajax({
    url: url,
    cache: false,
    data: $form.serialize(),
    type: requestType,
    dataType: responseType,
    success: successCallback,
    error: errorCallback
  });
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
