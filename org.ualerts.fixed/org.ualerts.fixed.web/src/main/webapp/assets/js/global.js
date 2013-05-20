var modalViewController = new ModalViewController();

$(function() {
  
  $.ajaxSetup({
    cache: false
  });

  modalViewController.whenDocumentReady(this);
});
