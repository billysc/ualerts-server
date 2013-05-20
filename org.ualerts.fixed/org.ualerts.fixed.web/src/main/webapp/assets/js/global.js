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
