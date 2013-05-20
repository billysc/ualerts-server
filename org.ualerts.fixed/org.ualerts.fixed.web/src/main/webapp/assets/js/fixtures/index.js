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
  
  $("#addFixture").on("modalLoaded", function() { 
	  addFixtureController.modalReady(this, $(this));
  });
  
});

/**
 * Display the provided fixture in the fixtures display
 * @param fixture The fixture to display
 */
function displayFixture(fixture) {
  $("#fixturesListEmpty").remove();
  $("#fixturesList").parent().show();

  //Add the row. Return value has index value to retrieve row from dataTables
  var rowControls = $("#rowControls").html();
  var row = $("#fixturesList").show().dataTable().fnAddData([
    fixture.buildingAbbreviation + " " + fixture.room,
    fixture.positionHint,
    fixture.ipAddress,
    fixture.macAddress,
    fixture.inventoryNumber,
    rowControls
  ]);
  
  // Get new row and wrap as jQuery object
  var $newRow = $( fixturesListTable.fnGetNodes( row[0] ) );
  $newRow.data("entity-id", fixture.id);
  var currentColor = $newRow.css("backgroundColor");
  $newRow.addClass("updated");
  
  // Wait, fade out, then remove css styles and updated class
  $newRow.find("td").delay(4000)
    .animate({backgroundColor: currentColor}, 1000, function() {
    	$(this).css("backgroundColor", "").parent().removeClass("updated");
    });
  
}
