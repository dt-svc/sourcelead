<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Welcome to Source Lead :: Account List</title>
<!-- Bootstrap -->
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<link href="resources/css/font-awesome.min.css" rel="stylesheet">
<link href="resources/css/sourcelead-main.css" rel="stylesheet">
<link href="resources/css/timesheet.css" rel="stylesheet">
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
          <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) --> 
<script src="resources/js/jquery-1.11.1.min.js"></script>
<script src="resources/js/bootstrap.min.js"></script> 
</head>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script>
		$(document).ready(function() { 
			var rateStatus = '${param.rateStatus}';
			if(rateStatus == 'Accepted'){           
					$("#accept").removeAttr("style");
			}
			if(rateStatus == 'Rejected'){
				$("#reject").removeAttr("style");		
			}
			if(rateStatus == 'Negotiate'){
				$("#negotiation").removeAttr("style");		
			}
		});
</script>

<body>
<div class="wraper"> 
  <!-- Header Area -->
  <header class="main-header">
   <span class="logo">
   	 	<a href="javascript:void(0)" title="Source Lead"> <i> <img src="resources/images/logo-sourceld.png" alt="Source Lead"> </i>
    <h1>Source Lead</h1>
    </a>
    </span>
    <nav class="header-nav navbar">
    </nav>
   </header>
  <!-- Header Area --> 

  <!-- Content Area -->
  <section class="success-wraper container">
		<div class="row">
		  		<div id="accept" style="display:none">
				          <div class="col-md-8 col-md-offset-2 inner-widget success-user">
					          <i class="fa fa-check-circle fa-3x"></i>
							  <p class="reason-msg">User Succefully logged</p>
						  </div>
		  		</div>
		  		<div id="reject" style="display:none">
		  			<form id="reject_form" name="reject_form" method="post" >
					          <div class="col-md-8 col-md-offset-2 inner-widget reject-user">
								  <div class="col-md-6">
									  <i class="fa fa-ban fa-3x"></i>
									  <p class="reason-msg">User Succefully Rejected</p>
								  </div>
								  <div class="col-md-6">
									  <div class="col-md-12">
										  <label>Reason for Reject</label>
										  <textarea class="form-control" rows="2" placeholder="Reason"></textarea>	
									  </div>
									  <div class="col-md-12 mt10"><button type="button" class="btn btn-sm btn-danger pull-right" onclick="storeReject();">Submit</button></div>
								  	
								  </div>
							  </div>
					</form>							  
		  		</div>
		  		<div id="negotiation" style="display:none">
		  			<form id="negotiation_form" name="negotiation_form" method="post" >
				          <div class="col-md-8 col-md-offset-2 inner-widget negotiation-user">
							  <div class="col-md-6">
							  	<i class="fa fa-handshake-o fa-3x"></i>
							  <p class="reason-msg">Rate Negotiation</p>
							  </div>
							  <div class="col-md-6">
								  <div class="col-md-12 mb10">
								  <input type="text" class="form-control input-sm" placeholder="Field One">
								  </div>
								  <div class="col-md-12  mb10">
								  <input type="text" class="form-control input-sm" placeholder="Field One">
								  </div>
								  <div class="col-md-12  mb10">
								  <input type="text" class="form-control input-sm" placeholder="Field One">
								  </div>
								  <div class="col-md-12 mb10">
									  <textarea row="1" class="form-control" placeholder="Comments"></textarea>
								  </div>
								  <div class="col-md-12 mt10"><button type="button" class="btn btn-sm btn-default" onclick="storeNegotiation();">Submit</button></div>
							  	
							  </div>
						 </div>
					</form>
		  		</div>
		 <div>
  </section>
  <!-- Footer Area -->
  <footer class="footer-area footer-area-active clearfix">
    <div class="copyright"> @ Copyright : Source Lead, All right reserved </div>
    <div class="powered-by"> Powered by: businessintelli.com </div>
  </footer>
  <!-- Footer Area --> 
</div>
</body>
</html>


<script type="text/javascript">
	function storeReject() {
			var sourceleadUrl = '${sourceleadUrl}';
			
			var frm = $('#reject_form').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, "");
			alert("con"+con);
			
			$.ajax({
						type : "POST",
						url : sourceleadUrl+"/storeReject?${_csrf.parameterName}=${_csrf.token}&json="+con,
						success : function(data) {
							     alert("success");
						},
					});
			       
	} 
</script>

<script type="text/javascript">
	function storeNegotiation() {
			var sourceleadUrl = '${sourceleadUrl}';
			
			var frm = $('#negotiation_form').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, "");
			
			$.ajax({
						type : "POST",
						url : sourceleadUrl+"/storeNegotiation?${_csrf.parameterName}=${_csrf.token}&json="+con,
						success : function(result) {
							     alert("success");
						},
			});
	} 
</script>


<script type="text/javascript">
	(function($) {
		$.fn.serializeFormJSON = function() {

			var o = {};
			var a = this.serializeArray();
			$.each(a, function() {
				if (o[this.name]) {
					if (!o[this.name].push) {
						o[this.name] = [ o[this.name] ];
					}
					o[this.name].push(this.value || '');
				} else {
					o[this.name] = this.value || '';
				}
			});
			return o;
		};
	})(jQuery);
</script>

