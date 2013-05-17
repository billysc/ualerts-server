function PositionHintService() {
  this.positionHints = null;
}

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
