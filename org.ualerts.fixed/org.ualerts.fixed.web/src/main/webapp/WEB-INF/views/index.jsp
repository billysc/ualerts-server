<!DOCTYPE html>
<html>
<head>
	<title>Fixtures Dashboard</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap-responsive.min.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styles.css" />
</head>
<body>
	<div class="navbar navbar-fixed-top navbar-inverse">
		<div class="navbar-inner">
			<div class="container">
				<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
					<span class="icon-bar"></span> 
					<span class="icon-bar"></span> <span class="icon-bar"></span>
				</a> 
				<a class="brand" href="#">Fixed Provisioning</a>
				<div class="nav-collapse collapse">
					<ul class="nav">
						
					</ul>
				</div>
			</div>
		</div>
	</div>
	
	<div class="container">
		<div class="row">
			<div class="span-12">
				<a id="addFixture" class="btn modalTrigger" href="${pageContext.request.contextPath}/ui/enrollment" data-target="#modal" data-title="Add Fixture" data-primarybuttontext="Submit New Fixture" data-cancelbuttontext="Cancel" data-postmodalcallback="postModalDisplay_enrollFixture">
					<i class="icon-plus"></i> Add Fixture
				</a>
			</div>
		</div>
	</div>
	
	
	<div id="modal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-header">
	    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
	    <h3 id="myModalLabel">Enroll Fixture</h3>
	  </div>
	  <div id="modalBody" class="modal-body"></div>
	  <div class="modal-footer">
	    <button class="btn" data-dismiss="modal" aria-hidden="true">Cancel</button>
	    <button class="btn btn-primary"></button>
	  </div>
	</div>
		
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/global.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/index.js"></script>
</body>
</html>