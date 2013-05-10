<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <title>Fixtures Dashboard</title>
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
          <a id="addFixture" class="btn modalTrigger" href="${pageContext.request.contextPath}/ui/enrollment" data-target="#modal" data-title="Add Fixture" data-primarybuttontext="Submit New Fixture" data-cancelbuttontext="Cancel" data-postmodalcallback="postModalDisplay_enrollFixture">
            <i class="icon-plus"></i> Add Fixture
          </a>
        </div>
        <c:choose>
          <c:when test="${empty fixtures}">
            <div id="fixturesList">There are currently no fixtures to display</div>
          </c:when>
          <c:otherwise>
            <table id="fixturesList" class="table table-striped table-bordered table-hover table-condensed">
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
                  <tr>
                    <td>${fixture.buildingAbbreviation} ${fixture.room}</td>
                    <td>${fixture.positionHint}</td>
                    <td>${fixture.ipAddress}</td>
                    <td>${fixture.macAddress}</td>
                    <td>${fixture.inventoryNumber}</td>
                    <td><a href="#">Details</a></td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          </c:otherwise>
        </c:choose>
      </div>
    </div>
  </div>
  
  
  <div id="modal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
      <h3 id="myModalLabel">Enroll Fixture</h3>
    </div>
    <div id="modalBody" class="modal-body"></div>
    <div class="modal-footer">
      <button class="btn" data-dismiss="modal" aria-hidden="true">Cancel</button>
      <button class="btn btn-primary"></button>
    </div>
  </div>
    
  
  <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery.min.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/global.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/index.js"></script>
</body>
</html>