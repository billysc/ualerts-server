<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<form:form cssClass="form-horizontal" commandName="fixture" action="${pageContext.request.contextPath}/ui/fixtures/enrollment.json">
  <div id="globalErrorContainer">
    <div class="error" data-for="_global"></div>
  </div>

  <div class="control-group" id="ipAddressContainer">
    <form:label path="ipAddress" cssClass="control-label">IP Address</form:label>
    <div class="controls">
      <form:input path="ipAddress" />
      <br /><div class="error" data-for="ipAddress"></div>
    </div>
  </div>
  <div class="control-group" id="macAddressContainer">
    <form:label path="macAddress" cssClass="control-label">MAC Address</form:label>
    <div class="controls">
      <form:input path="macAddress" />
      <br /><div class="error" data-for="macAddress"></div>
    </div>
  </div>
  <div class="control-group" id="serialNumberContainer">
    <form:label path="serialNumber" cssClass="control-label">Serial Number</form:label>
    <div class="controls">
      <form:input path="serialNumber" />
      <br /><div class="error" data-for="serialNumber"></div>
    </div>
  </div>
  <div class="control-group" id="inventoryNumberContainer">
    <form:label path="inventoryNumber" cssClass="control-label">Inventory Number</form:label>
    <div class="controls">
      <form:input path="inventoryNumber" />
      <br /><div class="error" data-for="inventoryNumber"></div>
    </div>
  </div>

  <div class="control-group" id="buildingContainer">
    <form:label path="building" cssClass="control-label">Building</form:label>
    <div class="controls">
      <input id="building" type="text" />
      <form:hidden path="buildingId" />
      <br /><div class="error" data-for="building"></div>
    </div>
  </div>
  <div class="control-group" id="roomContainer">
    <form:label path="room" cssClass="control-label">Room</form:label>
    <div class="controls">
      <form:input path="room" />
      <br /><div class="error" data-for="room"></div>
    </div>
  </div>
  <div class="control-group" id="positionHintContainer">
    <form:label path="positionHint" cssClass="control-label">Position Hint</form:label>
    <div class="controls">
      <form:input path="positionHint" />
      <br /><div class="error" data-for="positionHint"></div>
    </div>
  </div>
  
</form:form>