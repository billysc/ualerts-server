<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="javascriptBase" required="true" rtexprvalue="true" %>
<script type="text/javascript">
  var CONTEXT_URL = '${pageContext.request.contextPath}';
</script>
<script type="text/javascript" src="${javascriptBase}/jquery.min.js"></script>
<script type="text/javascript" src="${javascriptBase}/jquery-ui.custom.min.js"></script>
<script type="text/javascript" src="${javascriptBase}/bootstrap.min.js"></script>
<script type="text/javascript" src="${javascriptBase}/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="${javascriptBase}/global.js"></script>
