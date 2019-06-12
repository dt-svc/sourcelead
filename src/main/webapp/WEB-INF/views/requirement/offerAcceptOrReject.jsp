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

<link rel="icon" type="image/png" href="${SourceLeadWeb_sourceleadDomainUrl}/resources/images/fav.png" />
<title>Welcome to Source Lead :: Account List</title>
<!-- Bootstrap -->
<link href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/bootstrap.min.css?staticTime=${staticTime}" rel="stylesheet">
<link href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/font-awesome.min.css?staticTime=${staticTime}" rel="stylesheet">
<link href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/sourcelead-main.css?staticTime=${staticTime}" rel="stylesheet">
<link href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/timesheet.css?staticTime=${staticTime}" rel="stylesheet">
<script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/jquery-1.11.1.min.js?staticTime=${staticTime}"></script>
<script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/bootstrap.min.js?staticTime=${staticTime}"></script> 
<script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/formvalidator.js?staticTime=${staticTime}"></script>
 <script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js?staticTime=${staticTime}"></script>

</head>

<script>
		$(document).ready(function() { 
			var offerStatus = '${param.offerStatus}';
			if(offerStatus == 'Accepted'){     
					$("#accept").removeAttr("style");
			}
			if(offerStatus == 'Rejected'){
				$("#reject").removeAttr("style");		
			}
		});
</script>

<body>
<div class="wraper"> 
  <!-- Header Area -->
  <header class="main-header">
   <span class="logo">
   	 	<a href="javascript:void(0)" title="Source Lead"> <i> <img src="${SourceLeadWeb_sourceleadDomainUrl}/resources/images/SourceLead-logo.png" alt="Source Lead"> </i>
    </a>
    </span>
    <nav class="header-nav navbar">
    </nav>
   </header>
  <!-- Header Area --> 

  <!-- Content Area -->
  <section class="container">
		<div class="row">
		  		<div id="accept" style="display:none">
		  				<c:choose>
						 <c:when test="${permission=='YES'}">
				          <div class="col-md-8 col-md-offset-2 inner-widget success-user">
					          <i class="fa fa-check-circle fa-3x"></i>
							  <p class="reason-msg">You Have accepted the Offer for ${requirementOffers.submissionId.requirement.jobTitle}</p>
							  <p class="reason-msg"><center><h3>Thank You !!!</h3></center></p>
							  </c:when> 
							  <c:otherwise>
							    	<div class="col-md-8 col-md-offset-2 inner-widget pending-user">
						          		<i class="fa fa-clock-o fa-3x"></i>
							        	<p class="reason-msg">Your decision is already submitted for the Job<br> <B>${requirementOffers.submissionId.requirement.jobTitle}</B></p>
							  </c:otherwise>
							  </c:choose>
						  </div>
		  		</div>
		  		<div id="reject" style="display:none">
		  		<c:choose>
					   <c:when test="${permission=='YES'}">
		  			<form id="reject_form" name="reject_form" method="post" >
					          <div class="col-md-8 col-md-offset-2 inner-widget reject-user">
								  <div class="col-md-6">
									  <i class="fa fa-ban fa-3x"></i>
									  <p class="reason-msg">You are Rejecting the Offer for position <B> ${requirementOffers.submissionId.requirement.jobTitle}</B></p>
								  </div>
								  <div class="col-md-6" id="rejectIdDiv">
									  <div class="col-md-12">
										  <label>Reason for Reject</label>
										  <textarea class="form-control" rows="2" placeholder="Reason" id="rejectId"></textarea>	
									  </div>
									  <div class="col-md-12 mt10"><button type="button" class="btn btn-sm btn-danger pull-right" onclick="storeReject();">Submit</button></div>
								  	
								  </div>
							  </div>
					</form>		
					 </c:when>    
					 <c:otherwise>
					    	<div class="col-md-8 col-md-offset-2 inner-widget pending-user">
				          		<i class="fa fa-clock-o fa-3x"></i>
					        	<p class="reason-msg">Your decision is already submitted for the Job<br> <B>${requirementOffers.submissionId.requirement.jobTitle}</B></p>
					        </div>
					    </c:otherwise>
					  </c:choose>					  
		  		</div>
		 </div>
  </section>
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


<script type="text/javascript">
	function storeReject() {
			var sourceleadUrl = '${sourceleadUrl}';
			var offerId = '${offerId}';
			var offerStatus = '${param.offerStatus}';
			var rejectedReason = document.getElementById("rejectId").value;
			var frm = $('#reject_form').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, "");
			
			$.ajax({
						type : "POST",
						url : sourceleadUrl+"/ws/acceptOrRejectOffer?${_csrf.parameterName}=${_csrf.token}&offerStatus="+offerStatus+"&offerId="+offerId+"&rejectedReason="+rejectedReason,
						success : function(data) {
							if("success"){
								$('#rejectIdDiv').empty();
								$('#rejectIdDiv').append("<span>Your Rejected Reason is submitted successfully !!</span>");	
							}
							
						},
					});
			       
	} 
</script>
<script type="text/javascript">
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };
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

