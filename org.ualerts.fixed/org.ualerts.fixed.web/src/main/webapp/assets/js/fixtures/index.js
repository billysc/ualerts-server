$(function() {
  var buildingService = new BuildingService();
  var fixturesViewController = new FixturesViewController(buildingService);
  fixturesViewController.documentReady(this);
});
