<%@ attribute name="contextPath" required="true" rtexprvalue="true" %>
<%@ attribute name="fixtureId" required="true" rtexprvalue="true" %>
<a class="btn btn-link no-padding modal-trigger" 
  title="Edit this fixture" 
  href="${contextPath}/ui/fixtures/${fixtureId}/edit.html"
  data-target="#modal" data-title="Edit Fixture" 
  data-primary-button-text="Save Changes" 
  data-cancel-button-text="Cancel"
  data-control-function="edit">  
    <i class="icon-pencil"></i>
</a>  

<a class="btn btn-link no-padding modal-trigger" 
  title="Remove this fixture" 
  href="${contextPath}/ui/fixtures/${fixtureId}/remove.html"
  data-target="#modal" data-title="Remove Fixture" 
  data-primary-button-text="Remove Fixture" 
  data-cancel-button-text="Cancel"
  data-control-function="remove">  
    <i class="icon-remove-circle"></i>
</a>  
