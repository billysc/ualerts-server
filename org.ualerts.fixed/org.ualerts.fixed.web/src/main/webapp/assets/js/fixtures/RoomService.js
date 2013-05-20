function RoomService() {
  this.rooms = {};
}

RoomService.prototype.getRooms = function(callback) {
  if (typeof callback != "function") return;
  
  var buildingId = $("#buildingId").val();
  if ($.trim(buildingId) == "") return;
  
  if (this.rooms[buildingId] == null) {
    var controller = this;
    var ajaxCallback = function(responseBody) {
      controller.onFetchRooms(buildingId, responseBody);
      callback(controller.rooms[buildingId]);
    };
    $.get(CONTEXT_URL + "/api/buildings/" + buildingId + "/rooms", ajaxCallback, 
        'json');
  }
  else {
    callback(this.rooms[buildingId]);
  }
};

RoomService.prototype.onFetchRooms = function(buildingId, responseBody) {
  this.rooms[buildingId] = responseBody.rooms;
};
