var fixturesListTable;


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

function postModalDisplay_enrollFixture($modal) {
  var submitEnrollFixture = function() {
    var $form = $modal.find("form");
    var url = $form.attr("action");
    var requestType = "POST";
    var responseType = "json";
    var successCallback = function(data) {
      if (data.success) {
        var fixture = data.fixture;
        displayFixture(fixture);
        $modal.modal('hide');
      }
      else {
        displayErrorsOnForm($form, data.errors);
        $(".modal-body").scrollTop(0);
      }
    };
    var errorCallback = function(request, status, ex) {
      alert("Something happened: " + status + ": " + ex);
    };
    submitForm($form, url, requestType, responseType, successCallback, 
        errorCallback);
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
  var currentColor = $newRow.css("backgroundColor");
  $newRow.addClass("updated");
  
  // Wait, fade out, then remove css styles and updated class
  $newRow.find("td").delay(4000)
    .animate({backgroundColor: currentColor}, 1000, function() {
    	$(this).css("backgroundColor", "").parent().removeClass("updated");
    });
  
}
