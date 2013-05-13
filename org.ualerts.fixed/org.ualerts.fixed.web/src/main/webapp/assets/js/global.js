
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
    
    var opts = {};
    if (typeof remote != "undefined") {
      opts.remote = remote;
    }
    
    // Remove any lingering click handlers
    var $primaryButton = $target.find(".btn-primary").unbind("click");
    var $cancelButton = $target.find(".btn.cancel").unbind("click");
    
    $target.modal(opts);
    $target.find(".modal-header h3").text(title);
    if (typeof primaryButtonText == "string")
      $primaryButton.removeClass("hide").text(primaryButtonText);
    else
      $primaryButton.addClass("hide");
    
    if (typeof cancelButtonText == "string")
      $cancelButton.removeClass("hide").text(cancelButtonText)
    else
      $cancelButton.addClass("hide");
    
    if (typeof $this.data("postmodalcallback") == "string") {
      var fn = $this.data("postmodalcallback");
      window[fn]($target);
    }
    
    // Destroy the modal when closed to force URL fetch
    $target.on('hidden', function() {
      $(this).removeData('modal');
    });
    
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


function submitForm($form, url, requestType, responseType, successCallback, errorCallback) {
  $.ajax({
    url: url,
    cache: false,
    data: $form.serialize(),
    type: requestType,
    dataType: responseType,
    success: successCallback,
    error: errorCallback
  })
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
