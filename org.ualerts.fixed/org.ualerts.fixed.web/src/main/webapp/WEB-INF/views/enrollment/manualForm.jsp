<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<sf:form cssClass="form-horizontal" commandName="fixture" action="${pageContext.request.contextPath}/ui/enrollment">
  <div id="globalErrorContainer">
    <div class="error" data-for="_global"></div>
  </div>

  <div class="control-group" id="ipAddressContainer">
    <sf:label path="ipAddress" cssClass="control-label">IP Address</sf:label>
    <div class="controls">
      <sf:input path="ipAddress" />
      <br /><div class="error" data-for="ipAddress"></div>
    </div>
  </div>
  <div class="control-group" id="macAddressContainer">
    <sf:label path="macAddress" cssClass="control-label">MAC Address</sf:label>
    <div class="controls">
      <sf:input path="macAddress" />
      <br /><div class="error" data-for="macAddress"></div>
    </div>
  </div>
  <div class="control-group" id="serialNumberContainer">
    <sf:label path="serialNumber" cssClass="control-label">Serial Number</sf:label>
    <div class="controls">
      <sf:input path="serialNumber" />
      <br /><div class="error" data-for="serialNumber"></div>
    </div>
  </div>
  <div class="control-group" id="inventoryNumberContainer">
    <sf:label path="inventoryNumber" cssClass="control-label">Inventory Number</sf:label>
    <div class="controls">
      <sf:input path="inventoryNumber" />
      <br /><div class="error" data-for="inventoryNumber"></div>
    </div>
  </div>

  <div class="control-group" id="buildingContainer">
    <sf:label path="building" cssClass="control-label">Building</sf:label>
    <div class="controls">
      <sf:input path="building" />
      <br /><div class="error" data-for="building"></div>
    </div>
  </div>
  <div class="control-group" id="roomContainer">
    <sf:label path="room" cssClass="control-label">Room</sf:label>
    <div class="controls">
      <sf:input path="room" />
      <br /><div class="error" data-for="room"></div>
    </div>
  </div>
  <div class="control-group" id="positionHintContainer">
    <sf:label path="positionHint" cssClass="control-label">Position Hint</sf:label>
    <div class="controls">
      <sf:input path="positionHint" />
      <br /><div class="error" data-for="positionHint"></div>
    </div>
  </div>
  
</sf:form>
