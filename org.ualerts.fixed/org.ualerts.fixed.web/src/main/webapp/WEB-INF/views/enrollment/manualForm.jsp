<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<sf:form cssClass="form-horizontal" commandName="fixture" action="${pageContext.request.contextPath}/ui/enrollment">
	<div class="control-group">
		<sf:label path="ipAddress" cssClass="control-label">IP Address</sf:label>
		<div class="controls">
			<sf:input path="ipAddress" />
			<br /><sf:errors path="ipAddress" cssClass="error" />
		</div>
	</div>
	<div class="control-group">
		<sf:label path="macAddress" cssClass="control-label">MAC Address</sf:label>
		<div class="controls">
			<sf:input path="macAddress" />
			<br /><sf:errors path="macAddress" cssClass="error" />
		</div>
	</div>
	<div class="control-group">
		<sf:label path="serialNumber" cssClass="control-label">Serial Number</sf:label>
		<div class="controls">
			<sf:input path="serialNumber" />
			<br /><sf:errors path="serialNumber" cssClass="error" />
		</div>
	</div>
	<div class="control-group">
		<sf:label path="inventoryNumber" cssClass="control-label">Inventory Number</sf:label>
		<div class="controls">
			<sf:input path="inventoryNumber" />
			<br /><sf:errors path="inventoryNumber" cssClass="error" />
		</div>
	</div>

	<div class="control-group">
		<sf:label path="building" cssClass="control-label">Building</sf:label>
		<div class="controls">
			<sf:input path="building" />
			<br /><sf:errors path="building" cssClass="error" />
		</div>
	</div>
	<div class="control-group">
		<sf:label path="room" cssClass="control-label">Room</sf:label>
		<div class="controls">
			<sf:input path="room" />
			<br /><sf:errors path="room" cssClass="error" />
		</div>
	</div>
	<div class="control-group">
		<sf:label path="positionHint" cssClass="control-label">Position Hint</sf:label>
		<div class="controls">
			<sf:input path="positionHint" />
			<br /><sf:errors path="positionHint" cssClass="error" />
		</div>
	</div>
	
</sf:form>
