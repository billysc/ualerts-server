var buildingService = new BuildingService();
var positionHintService = new PositionHintService();
var fixturesViewController = new FixturesViewController(buildingService, 
    positionHintService);

$(function() {
  fixturesViewController.whenDocumentReady(this);
});
