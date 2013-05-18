var fixturesListTable;
var buildingService = new BuildingService();
var addFixtureController = new AddFixtureController(buildingService);

$(function() {
  var fixturesTable = $("#fixturesList");
  var emptyTable = (fixturesTable.find("tbody tr").size() == 0);
  fixturesListTable = fixturesTable.dataTable({
    aaSorting: [ [0, 'asc'], [1, 'asc'] ],
    bPaginate: false,
    bInfo: false,
    aoColumns: [
      null,
      null,
      null,
      null,
      null,
      { bSortable: false }
    ]
  });
  
  if (emptyTable) {
    fixturesTable.parent().hide();
  }
  
  $("#addFixture").data("post-modal-callback", function() { 
	  addFixtureController.modalReady(this, $(this));
  });
  
});
