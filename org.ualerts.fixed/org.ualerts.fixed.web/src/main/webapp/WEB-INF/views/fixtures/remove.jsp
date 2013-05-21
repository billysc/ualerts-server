<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="global" tagdir="/WEB-INF/tags" %>

<form:form cssClass="form-horizontal" commandName="fixture" 
    action="${contextPath}/ui/fixtures/${fixture.id}/remove.json">
    
  <global:inlineFieldDisplay label="Building" value="${fixture.building}" />
  <global:inlineFieldDisplay label="Room" value="${fixture.room}" />
  <global:inlineFieldDisplay label="Position Hint" value="${fixture.positionHint}" />
  <global:inlineFieldDisplay label="Inventory Number" value="${fixture.inventoryNumber}" />
  <global:inlineFieldDisplay label="Serial Number" value="${fixture.serialNumber}" />
  <global:inlineFieldDisplay label="MAC Address" value="${fixture.macAddress}" />
  <global:inlineFieldDisplay label="IP Address" value="${fixture.ipAddress}" />
  
  <div id="deleteConfirmation" class="hide text-center text-error">
    <strong>Are you SURE you want to remove this fixture? <br />
    This operation CANNOT be undone!</strong>
  </div>
</form:form>