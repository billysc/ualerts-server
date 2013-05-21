<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fixtures" tagdir="/WEB-INF/tags/fixtures" %>
<%@ attribute name="fixtures" required="true" rtexprvalue="true" 
    type="java.util.Collection" %>

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
          <fixtures:rowControls contextPath="${pageContext.request.contextPath}"
              fixtureId="${fixture.id}"/>
        </td>
      </tr>
    </c:forEach>
  </tbody>
</table>
<%-- this provides the controls needed when adding rows dynamically --%>
<div id="rowControls" class="hide">
  <fixtures:rowControls contextPath="${pageContext.request.contextPath}"
      fixtureId="{id}"/>
</div>
    

