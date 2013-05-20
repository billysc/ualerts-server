var buildingService = new BuildingService();
var fixturesViewController = new FixturesViewController(buildingService);
$(function() {
  fixturesViewController.documentReady(this);
});
