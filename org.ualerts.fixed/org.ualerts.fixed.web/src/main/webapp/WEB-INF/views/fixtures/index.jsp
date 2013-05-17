<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fixtures" tagdir="/WEB-INF/tags/fixtures" %>
<!DOCTYPE html>
<html>
	<head>
	  <title>Enrolled Fixtures</title>
	  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" />
	  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap-responsive.min.css" />
	  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styles.css" />
	</head>
	<body>
	  <div class="navbar navbar-fixed-top navbar-inverse">
	    <div class="navbar-inner">
	      <div class="container">
	        <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
	          <span class="icon-bar"></span> 
	          <span class="icon-bar"></span> <span class="icon-bar"></span>
	        </a> 
	        <a class="brand" href="#">Fixed Provisioning</a>
	        <div class="nav-collapse collapse">
	          <ul class="nav">
	            
	          </ul>
	        </div>
	      </div>
	    </div>
	  </div>
	  
	  <div class="container">
	    <div class="row">
	      <div class="span12">
	        <div class="text-right actionButtons">
	          <a id="addFixture" class="btn modal-trigger" 
	             href="${pageContext.request.contextPath}/ui/fixtures/enrollment.html" 
	             data-target="#modal" 
	             data-title="Add Fixture" 
	             data-primary-button-text="Submit New Fixture" 
	             data-cancel-button-text="Cancel">
	            <i class="icon-plus"></i> Add Fixture
	          </a>
	        </div>
	        <c:set var="tableClasses">table table-striped table-bordered table-hover table-condensed</c:set>
	        <c:if test="${empty fixtures}">
	          <c:set var="tableClasses">${tableClasses} hide</c:set>
	          <div id="fixturesListEmpty">There are currently no fixtures to display</div>
	        </c:if>
	        <table id="fixturesList" class="${tableClasses}">
	          <thead>
	            <tr>
	              <th>Location</th>
	              <th>Position</th>
	              <th>IP Address</th>
	              <th>MAC Address</th>
	              <th>Inventory Number</th>
	              <th>&nbsp;</th>
	            </tr>
	          </thead>
	          <tbody>
	            <c:forEach items="${fixtures}" var="fixture">
	              <tr data-entity-id="${fixture.id}">
	                <td>${fixture.buildingAbbreviation} ${fixture.room}</td>
	                <td>${fixture.positionHint}</td>
	                <td>${fixture.ipAddress}</td>
	                <td>${fixture.macAddress}</td>
	                <td>${fixture.inventoryNumber}</td>
	                <td>
	                  <fixtures:rowControls/>
	                </td>
	              </tr>
	            </c:forEach>
	          </tbody>
	        </table>
	      </div>
	    </div>
	  </div>
	  
	  
	  <div id="modal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	    <div class="modal-header">
	      <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
	      <h3 id="myModalLabel">Title Here</h3>
	    </div>
	    <div id="modalBody" class="modal-body"></div>
	    <div class="modal-footer">
	      <button class="btn" data-dismiss="modal" aria-hidden="true">Cancel</button>
	      <button class="btn btn-primary"></button>
	    </div>
	  </div>
	    
	  <div id="rowControls" class="hide">
      <fixtures:rowControls/>
	  </div>
	  
    <script type="text/javascript">
      var CONTEXT_URL = '${pageContext.request.contextPath}';
    </script>
	  <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery.min.js"></script>
	  <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery-ui.custom.min.js"></script>
	  <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
	  <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery.dataTables.min.js"></script>
	  <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/global.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/fixtures/BuildingService.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/fixtures/PositionHintService.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/fixtures/AddFixtureController.js"></script>
	  <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/fixtures/index.js"></script>
	  
	</body>
</html>
