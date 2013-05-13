var fixturesListTable;


$(function() {
  fixturesListTable = $("#fixturesList").dataTable({
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

      $("#fixturesListEmpty").remove();

    	//Add the row. Result has index value to retrieve row from dataTables
    	var row = $("#fixturesList").show().dataTable().fnAddData([
    	  fixture.buildingAbbreviation + " " + fixture.room,
    	  fixture.positionHint,
    	  fixture.ipAddress,
    	  fixture.macAddress,
    	  fixture.inventoryNumber,
    	  "<a href='#'>Details</a>"
    	]);
    	
    	var $newRow = $( fixturesListTable.fnGetNodes( row[0] ) );
    	var currentColor = $newRow.css("backgroundColor");
    	$newRow.addClass("updated");
    	
    	// Scope this section so variables can't be changed
    	with ({$row: $newRow, originalColor : currentColor}) {
    		setTimeout(function() {
    			$row.find("td").animate({backgroundColor: originalColor}, 1000, 
    		      function() {
    				// Remove the class and clear out css attributes for bgColor
    				$row.removeClass("updated").find("td")
    				  .css("backgroundColor", "");
    			  }
    			);
    		}, 4000);
    	}
        $modal.modal('hide');
      }
      else {
        displayErrorsOnForm($form, data.errors);
        $(".modal-body").scrollTop(0);
      }
    };
    var errorCallback = function() {
      alert("Something happened");
    };
    submitForm($form, url, requestType, responseType, successCallback, errorCallback)
  };
  
  $modal.find(".btn-primary").click(submitEnrollFixture);
  $modal.find("form").submit(submitEnrollFixture);
}
