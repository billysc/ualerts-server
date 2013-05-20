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

var modalViewController = new ModalViewController();

$(function() {
  
  $.ajaxSetup({
    cache: false
  });

  modalViewController.whenDocumentReady(this);
});

/**
 * Function that removes all empty strings or null values from Array
 */
Array.prototype.clean = function() {
  for (var i = 0; i < this.length; i++) {
    if (this[i] == null || this[i] == "") {         
      this.splice(i, 1);
      i--;
    }
  }
  return this;
};

/**
 * Function to test if an array contains a value
 */
Array.prototype.contains = function(needle) {
  for (var i = 0; i < this.length; i++) {
    if (this[i] === needle) {
      return true;
    }
  }
  return false;
};


/**
 * Allows formatting of strings such as "Hello {0}".format("world")
 */
String.prototype.format = function() {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function(match, number) { 
    return typeof args[number] != 'undefined' ? args[number] : match;
  });
};
