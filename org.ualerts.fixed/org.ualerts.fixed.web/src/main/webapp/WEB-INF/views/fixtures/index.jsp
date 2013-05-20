<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="global" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fixtures" tagdir="/WEB-INF/tags/fixtures" %>
<!DOCTYPE html>
<html>
	<head>
	  <title>Enrolled Fixtures</title>
	  <global:styles base="${pageContext.request.contextPath}/assets/css"/>
	</head>
	<body>
	  <global:topnavbar/>	  
	  <div class="container">
	    <div class="row">
	      <div class="span12">
	        <fixtures:actionButtons/>
	        <fixtures:tableView fixtures="${fixtures}"/>
	      </div>
	    </div>
	  </div>	  
    <global:modalView/>    	    
	  <fixtures:scripts base="${pageContext.request.contextPath}/assets/js"/>  
	</body>
</html>
