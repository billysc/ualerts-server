var fixturesListTable;
var allBuildings = null;
var buildingNames = null;
var autoComplete_buildingFormat = "({0}) - {1}";

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
});

function postModalDisplay_enrollFixture() {
  var $modal = $(this);
  
  $("#building").typeahead({
	source: function(query, process) {
      if (allBuildings != null && buildingNames != null) {
    	return buildingNames;
      }
      $.get(CONTEXT_URL + "/api/buildings", function(data) {
    	allBuildings = data.buildings;
    	buildingNames = Array();
    	for (key in allBuildings) {
          var building = allBuildings[key];
          var display = autoComplete_buildingFormat.format(building.abbreviation, building.name); 
          buildingNames.push(display);
          allBuildings[key].display = display;
    	}
    	process(buildingNames);
      }, 'json');
    }
  }).blur(function() {
  	var value = $(this).val();
    if (!buildingNames.contains(value)) {
      $("#buildingId").val('');
      return $(this).val('');
    }
    for (key in allBuildings) {
	  if (allBuildings[key].display == value) {
        return $("#buildingId").val( allBuildings[key].id );
      }
    }
  });
  
  var submitEnrollFixture = function(event) {
	event.stopPropagation();
    var $form = $modal.find("form");
    var successCallback = function(response) {
      if (response.success) {
        displayFixture(response.fixture);
        $modal.modal('hide');
      }
      else {
        displayErrorsOnForm($form, response.errors);
        $(".modal-body").scrollTop(0);
      }
    };
    
    var errorCallback = function(request, status, ex) {
      alert("Something happened: " + status + ": " + ex);
    };
    
    submitForm($form, $form.attr("action"), "POST", "json", successCallback, 
        errorCallback);
    return false;
  };
  
  $modal.find(".btn-primary").click(submitEnrollFixture);
  $modal.find("form").submit(submitEnrollFixture);
}

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
  $newRow.attr("data-entity-id", fixture.id);
  var currentColor = $newRow.css("backgroundColor");
  $newRow.addClass("updated");
  
  // Wait, fade out, then remove css styles and updated class
  $newRow.find("td").delay(4000)
    .animate({backgroundColor: currentColor}, 1000, function() {
    	$(this).css("backgroundColor", "").parent().removeClass("updated");
    });
  
}
