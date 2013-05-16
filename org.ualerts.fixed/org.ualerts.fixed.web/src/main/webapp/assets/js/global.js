var CONTEXT_URL = "/fixed";


$(function() {
  
  $.ajaxSetup({
    cache: false
  });
  
  $(".modalTrigger").click(function(event) {
    event.stopPropagation();
    var $this = $(this);
    var $target = $("" + $this.data("target"));
    var remote = $this.attr("href");
    var title = $this.data("modaltitle");
    var primaryButtonText = $this.data("primarybuttontext");
    var cancelButtonText = $this.data("cancelbuttontext");
    
    // Remove any lingering click handlers
    var $primaryButton = $target.find(".btn-primary").unbind("click");
    var $cancelButton = $target.find(".btn.cancel").unbind("click");
    
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
    
    // Destroy the modal when closed to force URL fetch
    $target.on('hidden', function() {
      $(this).removeData('modal');
    });
    
    if (typeof $this.data("postmodalcallback") == "string") {
      var fn = $this.data("postmodalcallback");
      if (typeof window[fn] == "function") {
        $target.on('loaded', window[fn]);
      }
    }
    
    return false;
  });
});

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


function submitForm($form, url, requestType, responseType, successCallback, errorCallback) {
  $.ajax({
    url: url,
    cache: false,
    data: $form.serialize(),
    type: requestType,
    dataType: responseType,
    success: successCallback,
    error: errorCallback
  });
}

function displayErrorsOnForm($form, errors) {
  $form.find("[data-for]").html('');
  if (typeof errors != "object")
    return;
  if (typeof errors.global == "object") {
    $form.find("[data-for='_global']").html(errors.global.clean().join("<br />"));
  }
  if (typeof errors.fields == "object") {
    for (fieldName in errors.fields) {
      var fieldErrors = errors.fields[fieldName];
      $form.find("[data-for='" + fieldName + "']").html(fieldErrors.clean().join("<br />"));
    }
  }
}
