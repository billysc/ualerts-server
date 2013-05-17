<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="global" tagdir="/WEB-INF/tags" %>
<%@ attribute name="javascriptBase" required="true" rtexprvalue="true" %>
<global:scripts javascriptBase="${javascriptBase}"/>
<script type="text/javascript" src="${javascriptBase}/fixtures/BuildingService.js"></script>
<script type="text/javascript" src="${javascriptBase}/fixtures/AddFixtureController.js"></script>
<script type="text/javascript" src="${javascriptBase}/fixtures/index.js"></script>
