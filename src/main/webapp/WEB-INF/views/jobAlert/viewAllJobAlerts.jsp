<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="icon" type="image/png" href="resources/images/fav.png" />
<title>Welcome to Source Lead :: Job Alerts</title>
<!-- Bootstrap -->
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<link href="resources/css/font-awesome.min.css" rel="stylesheet">
<link href="resources/css/sourcelead-main.css" rel="stylesheet">
<link href="resources/css/timesheet.css" rel="stylesheet">
<script src="resources/js/InterViewFormBuilder.js" type="text/javascript" ></script>
<script src="resources/js/interViewForm-html-generator.js" type="text/javWascript" ></script> 
<script src="resources/js/jquery-1.11.1.min.js"></script>
<script src="resources/js/bootstrap.min.js"></script> 
</head>
 
<script src="resources/js/formvalidator.js"></script>
<link rel="stylesheet" href="resources/css/style.css" />
<script src="resources/js/jquery-ui.js"></script>
<link rel="stylesheet" href="resources/css/jquery-ui.css" type="text/css" >
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style type="text/css">
.jobAlerts {
margin-top: 65px;
}
#jobAlerts {
min-height: 50px;
    max-height: 525px;
    overflow-y: auto;
    width: 900px;
    margin: 0 auto; 
}

</style>

<script type="text/javascript">
 var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
</script>


<script type="text/javascript">
		window.onload = function () {
			getAllJobAlertsList();
		 };
</script>

<body>
<div class="clearfix"> 
  <!-- Header Area -->
  <header class="main-header">
   <span class="logo">
   	 	<a href="javascript:void(0)" title="Source Lead"> <i> <img src="resources/images/SourceLead-logo.png" alt="Source Lead"> </i>
    </a>
    </span>
    <nav class="header-nav navbar">
    </nav>
   </header>
   
   <div class="jobAlerts">
   <h4 class="text-center">Your Matching Jobs</h4>
   <p id="jobAlerts"></p>
   </div>
   
   <footer class="clearfix stickyfooter mt">
   <div class="container">
    <div class="pull-left"> @ Copyright : Source Lead, All right reserved </div>
    <div class="pull-right"> Powered by: businessintelli.com </div>
    </div>
  </footer>
</div>
</body>



<script>
 function getAllJobAlertsList(){
	 var jobAlertUUID = '${jobAlertUUID}'; 
	
	 $.ajax({
	    	type: "POST",
	    	url: sourceleadUrl+"/getAllJobsAlertList?${_csrf.parameterName}=${_csrf.token}&jobAlertUUID="+jobAlertUUID,
	        success: function(data) {
		        $('#jobAlerts').html(data);
	        },
	    });
	 
 }
 
 
 </script>