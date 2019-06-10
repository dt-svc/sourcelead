<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:if test="${staticTime == null || staticTime == 0 }">
    <c:set var='staticTime' value="<%=new java.util.Date().getTime() %>" scope="application"></c:set>
</c:if>
 
<link rel="icon" type="image/png" href="/resources/images/fav.png" />
<title>Welcome to Source Lead :: Account List</title>
<!-- Bootstrap -->
<link rel="stylesheet" href="/resources/css/jquery.timepicker.css?staticTime=${staticTime}">
<link href="/resources/css/jquery-ui.css?staticTime=${staticTime}" rel="stylesheet">
<link href="/resources/css/bootstrap.min.css?staticTime=${staticTime}" rel="stylesheet">
<link href="/resources/css/font-awesome.min.css?staticTime=${staticTime}" rel="stylesheet">
<link href="/resources/css/sourcelead-main.css?staticTime=${staticTime}" rel="stylesheet">
<link href="/resources/css/timesheet.css?staticTime=${staticTime}" rel="stylesheet">
</head>

 <script src="/resources/js/jquery.dataTables.min.js?staticTime=${staticTime}"></script>
 
<script src="/resources/js/jquery-1.11.1.min.js?staticTime=${staticTime}"></script>
<script src="/resources/js/bootstrap.min.js?staticTime=${staticTime}"></script>  
<script src="/resources/js/chosen.jquery.min.js?staticTime=${staticTime}"></script>
<script src="/resources/js/jquery-ui.js?staticTime=${staticTime}"></script>
<script src="/resources/js/jquery-fun.js?staticTime=${staticTime}"></script>
<script src="/resources/js/jquery.timepicker.js?staticTime=${staticTime}"></script>
<script src="/resources/js/bootstrap-select.min.js?staticTime=${staticTime}"></script>
<script src="/resources/js/themeswitcher.js?staticTime=${staticTime}"></script>
<script src="/resources/js/moment.min.js?staticTime=${staticTime}"></script>
<script src="/resources/js/datetime-moment.js?staticTime=${staticTime}"></script>

<script>
		$(document).ready(function() { 
			var interviewStatus  = '${interviewStatus}';
			var intervewStartTime = '${interviewStartTime}';
			var intervewEndTime = '${interviewEndTime}';
			if(interviewStatus == 'Action_Done'){
				$("#actionDone").removeAttr("style");
			}else{
				if(intervewStartTime != "interviewStartTime" && intervewEndTime != "interviewEndTime"){
					$("#expire").removeAttr("style");
				}else{
					if(interviewStatus == 'Attending'){ 
						$("#accept").removeAttr("style");
					}else if(interviewStatus == 'Not_Attending'){
						$("#reject").removeAttr("style");
					}else if(interviewStatus == 'Maybe'){
						$("#tentative").removeAttr("style");
					}
				}
			}
		});
</script>


<body>
<div class="wraper"> 
  <!-- Header Area -->
  <header class="main-header">
   <span class="logo">
    <h1>Source Lead</h1>
   	 	<a href="javascript:void(0)" title="Source Lead"> <i> <img src="/resources/images/logo-sourceld.png" alt="Source Lead"> </i>
    </a>
    </span>
    <nav class="header-nav navbar">
    </nav>
   </header>
  <!-- Header Area --> 

  <!-- Content Area -->
  <section class="container">
		<div class="col-md-12">
		  		<div id="accept" style="display:none">
				          <div class="col-md-8 col-md-offset-2 inner-widget success-user">
					          <i class="fa fa-check-circle fa-3x"></i>
							  <p class="reason-msg">You Have accepted the Interview for ${requirementInterview.submissionId.requirement.jobTitle} </p>
							  <p class="reason-msg"><center><h3>Thank You !!!</h3></center></p>
						  </div>
		  		</div>
		  		
		  		<div id="expire" style="display:none">
				          <div class="col-md-8 col-md-offset-2 inner-widget reject-user">
					          <i class="fa fa-ban fa-3x"></i>
							  <p class="reason-msg">Sorry !! Interview time is expired  </p>
							  <p class="reason-msg"><center><h3>Thank You !!!</h3></center></p>
						  </div>
		  		</div>
		  		<div id="reject" style="display:none">
					          <div class="col-md-8 col-md-offset-2 inner-widget reject-user">
									  <i class="fa fa-ban fa-3x"></i>
									  <p class="reason-msg">You are Rejecting the Interview for position <B>${requirementInterview.submissionId.requirement.jobTitle} </B></p>
							  </div>
		  		</div>
		  		<div id="actionDone" style="display:none">
				          <div class="col-md-8 col-md-offset-2 inner-widget pending-user">
					          <i class="fa fa-ban fa-3x"></i>
							  <p class="reason-msg">Your decision has already submitted</p>
						  </div>
		  		</div>
		 </div>
  </section>
  <div id="popUp"></div>

<script src="/resources/js/formvalidator.js"></script>
  <!-- Footer Area -->
  <footer class="footer-publicpages clearfix">
  <div class="container">
    <div class="copyright"> @ Copyright : Source Lead, All right reserved </div>
    <div class="powered-by"> Powered by: businessintelli.com </div></div>
  </footer>
  <!-- Footer Area --> 
</div>
</body>
</html>
