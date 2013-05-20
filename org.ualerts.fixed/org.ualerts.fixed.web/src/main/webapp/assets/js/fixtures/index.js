var buildingService = new BuildingService();
var positionHintService = new PositionHintService();
var roomService = new RoomService();
var fixturesViewController = new FixturesViewController(buildingService, 
    positionHintService, roomService);

$(function() {
  fixturesViewController.documentReady(this);
});
