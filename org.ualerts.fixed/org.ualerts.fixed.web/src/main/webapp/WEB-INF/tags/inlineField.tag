<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="containerId" required="false" rtexprvalue="true" %>
<%@ attribute name="label" required="true" rtexprvalue="true" %>
<%@ attribute name="path" required="true" rtexprvalue="true" %>

<c:if test="${empty containerId}">
  <c:set var="containerId" value="${path}Container" />
</c:if>

<div class="control-group" id="${containerId}">
  <form:label path="${path}" cssClass="control-label">${label}</form:label>
  <div class="controls">
    <form:input path="${path}" />
    <br /><div class="error" data-for="${path}"></div>
  </div>
</div>
