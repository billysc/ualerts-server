<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="global" tagdir="/WEB-INF/tags" %>

<form:form cssClass="form-horizontal" commandName="fixture" 
    action="${pageContext.request.contextPath}/ui/fixtures/enrollment.json">
    
  <div id="globalErrorContainer">
    <div class="error" data-for="_global"></div>
  </div>

  <global:inlineField label="Building" path="building" />
  <form:hidden path="buildingId" />

  <global:inlineField label="Room" path="room" />
  <global:inlineField label="Position Hint" path="positionHint" />
  <global:inlineField label="Inventory Number" path="inventoryNumber" />
  <global:inlineField label="Serial Number" path="serialNumber" />
  <global:inlineField label="MAC Address" path="macAddress" />
  <global:inlineField label="IP Address" path="ipAddress" />
  
</form:form>