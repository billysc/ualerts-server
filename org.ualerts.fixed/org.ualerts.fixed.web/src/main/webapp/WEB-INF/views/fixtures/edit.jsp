<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="global" tagdir="/WEB-INF/tags" %>

<form:form cssClass="form-horizontal" commandName="fixture" 
    action="${pageContext.request.contextPath}/ui/fixtures/${fixture.id}/edit.json">
    
  <div id="globalErrorContainer">
    <div class="error" data-for="_global"></div>
  </div>

  <global:inlineField label="Building" path="building" />
  <form:hidden path="buildingId" />
  
  <global:inlineField label="Room" path="room" />
  <global:inlineField label="Position Hint" path="positionHint" />
  <global:inlineField label="IP Address" path="ipAddress" />
  <global:inlineFieldDisplay label="MAC Address" value="${fixture.macAddress}" />
  <global:inlineFieldDisplay label="Inventory Number" value="${fixture.inventoryNumber}" />
  <global:inlineFieldDisplay label="Serial Number" value="${fixture.serialNumber}" />
  <global:hiddenSubmitField />
  
</form:form>