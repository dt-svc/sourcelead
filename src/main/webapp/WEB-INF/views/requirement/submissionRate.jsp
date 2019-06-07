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

<title>Welcome to Source Lead :: Account List</title>
	<!-- Bootstrap -->
	<link href="/resources/css/bootstrap.min.css?staticTime=${staticTime}" rel="stylesheet">
	<link href="/resources/css/font-awesome.min.css?staticTime=${staticTime}" rel="stylesheet">
	<link href="/resources/css/sourcelead-main.css?staticTime=${staticTime}" rel="stylesheet">
	<link href="/resources/css/timesheet.css?staticTime=${staticTime}" rel="stylesheet">
	<script src="/resources/js/jquery-1.11.1.min.js?staticTime=${staticTime}"></script>
	<script src="/resources/js/bootstrap.min.js?staticTime=${staticTime}"></script> 
	<script src="/resources/js/formvalidator.js?staticTime=${staticTime}"></script>
	<script src="/resources/js/jquery.dataTables.min.js?staticTime=${staticTime}"></script>
</head>

<script>
		$(document).ready(function() { 
			var rateStatus = '${param.rateStatus}';
			if(rateStatus == 'Accepted'){           
					$("#accept").removeAttr("style");
			}
			if(rateStatus == 'Rejected'){
				$("#reject").removeAttr("style");	
			}
			if(rateStatus == 'RejectedApplicant'){
				$("#rejectApplicant").removeAttr("style");
			}
			if(rateStatus == 'Negotiate'){
				$("#negotiation").removeAttr("style");		
			}
			getNegotiationHistory();
		});
</script>

<body>
<div class="wraper"> 
  <!-- Header Area -->
  <header class="main-header">
   <span class="logo">
   	 	<a href="javascript:void(0)" title="Source Lead"> <i> <img src="/resources/images/SourceLead-logo.png" alt="Source Lead" class="img-responsive mt5"></i>
    </a>
    </span>
    <nav class="header-nav navbar">
    </nav>
   </header>
  <!-- Header Area --> 

  <!-- Content Area -->
  <section class="container marTop">
		<div class="row">
		  		<div id="accept" style="display:none">
					          <c:choose>
							    <c:when test="${permission=='YES'}">
								    <div class="col-md-8 col-md-offset-2 inner-widget success-user">
						          		<i class="fa fa-check-circle fa-3x"></i>
							        	<p class="reason-msg">You have accepted the job for <B>${requirementSubmission.requirement.jobTitle}</B></p>
							 			<p class="reason-msg"><center><h3>Thank You !!!</h3></center></p>
							    </c:when>    
							    <c:otherwise>
							    	<div class="col-md-8 col-md-offset-2 inner-widget pending-user">
						          		<i class="fa fa-clock-o fa-3x"></i>
							        	<p class="reason-msg">Your decision is already submitted for the job<br> <B>${requirementSubmission.requirement.jobTitle}</B></p>
							    </c:otherwise>
							  </c:choose>
						  </div>
		  		</div>
		  		
		  		
		  		<div id="rejectApplicant" style="display:none">
			  		<c:choose>
					    <c:when test="${permission=='YES'}">
						    <form id="reject_form_applicant" name="reject_form_applicant" method="post" >
					          <div class="col-md-8 col-md-offset-2 inner-widget reject-user">
								  <div class="col-md-6">
									  <i class="fa fa-ban fa-3x"></i>
									  <p class="reason-msg">You are not interested for the position <B> ${requirementSubmission.requirement.jobTitle}</B></p>
								  </div>
								  <div class="col-md-6" id="rejectApplicantIdDiv">
									  <div class="col-md-12">
										  <label>Reason for Reject</label>
										  <textarea class="form-control" rows="2" placeholder="Reason" id="rejectApplicantId"></textarea>	
									  </div>
									  <div class="col-md-12 mt10"><button type="button" id="reject_Applicant" class="btn btn-sm btn-danger pull-right" onclick="storeApplicantRejectReason();">Submit</button></div>
								  	
								  </div>
							  </div>
							</form>
					    </c:when>
					     <c:otherwise>
							    <div class="col-md-8 col-md-offset-2 inner-widget pending-user">
						          	<i class="fa fa-clock-o fa-3x"></i>
							        <p class="reason-msg">Your decision is already submitted for the job<br> <B>${requirementSubmission.requirement.jobTitle}</B></p>
							     </div>
							    </c:otherwise>    
					  </c:choose>
		  		</div>
		  		
		  		
		  		<div id="reject" style="display:none">
			  		<c:choose>
					    <c:when test="${permission=='YES'}">
						    <form id="reject_form" name="reject_form" method="post" >
					          <div class="col-md-8 col-md-offset-2 inner-widget reject-user">
								  <div class="col-md-6">
									  <i class="fa fa-ban fa-3x"></i>
									  <p class="reason-msg">You are not interested for the position <B> ${requirementSubmission.requirement.jobTitle}</B></p>
								  </div>
								  <div class="col-md-6" id="rejectIdDiv">
									  <div class="col-md-12" id="rejectReasonDiv">
										  <label>Reason for Reject</label>
										  <textarea class="form-control" rows="2" placeholder="Reason" id="rejectId"></textarea>	
									  </div>
									  <div class="col-md-12 mt10"><button type="button" id="reject_Rate" class="btn btn-sm btn-danger pull-right" onclick="storeReject();">Submit</button></div>
								  	
								  </div>
							  </div>
							</form>
					    </c:when>    
					    <c:otherwise>
					    	<div class="col-md-8 col-md-offset-2 inner-widget pending-user">
				          		<i class="fa fa-clock-o fa-3x"></i>
					        	<p class="reason-msg">Your decision is already submitted for the job<br> <B>${requirementSubmission.requirement.jobTitle}</B></p>
					        </div>
					    </c:otherwise>
					  </c:choose>
		  		</div>
		  		<div id="negotiation" style="display:none">
		  			 <c:choose>
					    <c:when test="${permission=='YES'}">
						    <form id="negotiation_form" name="negotiation_form" method="post" >
						          <div class="col-md-8 rate-negotiation project_wrap_table projectwrap_Table" id="negotiationDivId">
									  <div class="clearfix">
									  <h4>Rate Negotiation</h4>
									  </div>
									  <div id="ratehistoryDiv"></div>
									  <div class="clearfix mt" id="fieldsDiv" style="display:none">
										  <div class="col-md-12 mb10 hidden">
										   Job Rate : ${requirementSubmission.requirement.rate}
										  </div>
										  
										   <div class="clearfix">
										  <div class="row">
										  <div class="col-md-3">
										  <input type="text" class="form-control input-sm" field-name=" ApplicantRate rate" placeholder="Rate" id="rateProposal" name="rateProposal" data-validation="required validate_Space validate_float validate_length length1-10">
										  </div> 
										  <input type="hidden" name="candidateId" id="candidateId" value="${requirementSubmission.candidate.id}"/> 
										  <div class="col-md-3 select-field">
										  <select  class="form-control input-sm" id="rateType" name="rateType" field-name="Rate Type" data-validation="required">
												<option value="">Rate Per</option>
							                    <option>Hourly</option>
							                    <option>Weekly</option>
							                    <option>Bi-weekly</option>
							                    <option>Semi-Monthly</option>
							                    <option>Monthly</option>
							                    <option>Bi-Monthly</option>
							                    <option>Yearly</option>
										  </select>
										  </div>
										  <div class="clearfix"></div>
										  <div class="col-md-6 mt">
											  <textarea class="form-control" rows="2" name="comment" placeholder="Comment" id="comment" field-name="Comment"></textarea>	
										  </div>
										  </div>
										  </div>
										  <div class="col-md-6 mt10"><input type="button" class="btn btn-sm btn-primary pull-right" id="negotiationButtonId" onclick="storeNegotiation();" value="Submit"></div>
									  </div>
								 </div>
							</form>
					    </c:when>    
					    <c:otherwise>
					    	<div class="col-md-8 col-md-offset-2 inner-widget pending-user">
				          		<i class="fa fa-clock-o fa-3x"></i>
					        	<p class="reason-msg">Your decision is already submitted for the Job<br> <B>${requirementSubmission.requirement.jobTitle}</B></p>
					        </div>
					    </c:otherwise>
					  </c:choose>
		  		</div>
		 <div>
  </section>
  <!-- Footer Area -->
  <footer class="footer-publicpages clearfix">
  <div class="container">
    <div class="copyright"> @ Copyright : Source Lead, All right reserved </div>
    <div class="powered-by"> Powered by: businessintelli.com </div></div>
  </footer>
  <!-- Footer Area --> 
</div>
<script type="text/javascript" src='/resources/js/slead-common.js'></script>
</body>
</html>


<script type="text/javascript">
	function storeReject() {
		$('#reject_Rate').attr("disabled", true);
			var sourceleadUrl = '${sourceleadUrl}';
			var submissionId = '${submissionId}';
			var type = '${type}';
			var rejectreason = document.getElementById("rejectId").value;
			if(rejectreason != null && rejectreason != ''){
				var frm = $('#reject_form').serializeFormJSON();
				var con = JSON.stringify(frm);
				con = con.replace(/[{}]/g, "");
				
				$.ajax({
							type : "POST",
							url : sourceleadUrl+"/ws/storeReject?${_csrf.parameterName}=${_csrf.token}&submissionId="+submissionId+"&rejectreason="+rejectreason+"&type="+type,
							success : function(data) {
								if(data == "success"){
									$('#rejectIdDiv').empty();
									$('#rejectIdDiv').append("<span>Your Rejected Reason is submitted successfully !!</span>");	
								}else if(data == "failure"){
									$('#rejectIdDiv').empty();
									$('#rejectIdDiv').append("<span>Your decision is already submitted !!</span>");	
								}
							},
						});
			}else{
				$("#rejectReasonDiv").find('p.jquery_form_error_message').remove();
				$("#rejectReasonDiv").after("<p class='jquery_form_error_message'>Rejected reason is required</p>");
			}
	} 
</script>

<script type="text/javascript">
	function storeApplicantRejectReason() {
		$('#reject_Applicant').attr("disabled", true);
			var sourceleadUrl = '${sourceleadUrl}';
			var submissionId = '${submissionId}';
			var rejectreason = document.getElementById("rejectApplicantId").value;
			var frm = $('#reject_form_applicant').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, "");
			$.ajax({
						type : "POST",
						url : sourceleadUrl+"/ws/storeApplicantRejectReason?${_csrf.parameterName}=${_csrf.token}&submissionId="+submissionId+"&rejectreason="+rejectreason,
						success : function(data) {
							if(data == "success"){
								$('#rejectApplicantIdDiv').empty();
								$('#rejectApplicantIdDiv').append("<span>Your Rejected Reason is submitted successfully !!</span>");	
							}else if(data == "failure"){
								$('#rejectApplicantIdDiv').empty();
								$('#rejectApplicantIdDiv').append("<span>Your decision is already submitted !!</span>");	
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
	function storeNegotiation() {
		var sourceleadUrl = '${sourceleadUrl}';
			var submissionId = '${submissionId}';
			var type = '${type}';
			if ($('#negotiation_form').validate(false, validationSettings)){
			$('#negotiationButtonId').addClass('button').val('Processing..').attr('disabled','disabled');
		    var formData = getFormData1("negotiation_form");
			$.ajax({
						type : "POST",
						data: formData,
						processData: false,
					    contentType:false, 
						url : sourceleadUrl+'/ws/storeNegotiation?'+tokenParamName+'='+tokenValue+'&submissionId='+submissionId+'&type='+type,
						success : function(data) {
							if (data.response === "FAILED") {
					    		showError(data.responseMessage);
					    	} else if (data.response === "AWKWARD") {
					    		location.href = "/errorPage";
					    	} else if (data.response === "SUCCESS") {
					    		$('#rateProposal').val('');
								$('#rateType').val('');
								$('#negotiationButtonId').addClass('button').val('submit').removeAttr("disabled");
								$("#fieldsDiv").css({ display: "none" });
								getNegotiationHistory();
					    	}else {
					    		showError("Operation failed, please try again");
					    	}
						},
			});   
		}else{
			    $('body').on('blur', '#negotiation_form', function() {
				$("#negotiation_form").showHelpOnFocus();
				$("#negotiation_form").validateOnBlur(false, validationSettings);
			    });
		}
	} 
	    
	
</script>
<script type="text/javascript">
var tokenParamName = "${_csrf.parameterName}";
var tokenValue = "${_csrf.token}";

function tagItBykeyForEmails(e){
	if(e.which == 0 || e.which == 13 || e.which == 9 || e.which == 32 || e.which == 188){
		 $(document).click();
		 return true;
	}
}
</script>

<script type="text/javascript">
	function getNegotiationHistory() {
			var sourceleadUrl = '${sourceleadUrl}';
			var submissionId = '${submissionId}';
			var actionUsertype = '${type}';
			var candidateId = '${candidateId}';
			$.ajax({
						type : "POST",
						url : sourceleadUrl+"/ws/getRateHistoryBySubmissionIdPublic?&submissionId="+submissionId+"&candidateId="+candidateId,
						success : function(data) {
							 if (data.response === "FAILED") {
						    		showError(data.responseMessage);
						    	} else if (data.response === "AWKWARD") {
						    		location.href = "/errorPage";
						    	} else if (data.response === "SUCCESS") {
									var json = data.data;
									var result = "";
									result = result +"<html>";
				                	 result = result +"<table id='rateNegotiationTable' width='100%' cellpadding='0' border='2' cellspacing='0'>"; 
				                     result = result +"<thead>"; 
				                     result = result +"  <tr>"; 
				                     result  = result + '<th> Job Title </th>';
	// 			                 	 result  = result + '<th> Job Rate </th>';
				                 	 result  = result + '<th> Proposed Rate </th>';
				                 	 result  = result + '<th> Proposed By </th>';
				                     result = result +"  </tr>"; 
				                     result = result +"</thead>";
				                     result = result +"<tbody>"; 
				                     for (var i = 0; i < json.length; i++) { 
				                    	 var opt = json[i]; 
				                    	 if(opt.expectedRatePer != null && opt.expectedRatePer != '' && opt.expectedRatePer != 'undefined'){
				                    		 expectedRatePer = opt.expectedRatePer;
				                    	 }else{
				                    		 expectedRatePer ="";
				                    	 }
				                    	 
				                    	 if(i==0){
				                    		 result  = result + '<input type="hidden" id="lastProposedBy" value='+opt.previouslyProposedBy+'>';
				                    	 }
				                    	 result  = result + '         <tr>';
				                    	 if(opt.requirementName != null && opt.requirementName != '' && opt.requirementName != 'undefined'){
				                    	    result  = result + '<td><div><span>'+opt.requirementName+'</span></div></td>';
			                    	     }else{
			                    	    	 result  = result + ' <td><div><span>--</span></div></td>';
			                    	     }
				                    	 if(opt.expectedRate != null && opt.expectedRate != '' && opt.expectedRate != 'undefined' && opt.expectedRateType != null && opt.expectedRateType != '' && opt.expectedRateType != 'undefined'){
				                    	 result  = result + ' <td><div><span>'+opt.expectedRate+' '+expectedRatePer+'/'+opt.expectedRateType+' </span></div></td>';
				                    	 }else if(opt.expectedRate != null && opt.expectedRate != '' && opt.expectedRate != 'undefined'){
				                    		 result  = result + ' <td><div><span>'+opt.expectedRate+'</span></div></td>';
				                    	 }else{
				                    		 result  = result + ' <td><div><span>---</span></div></td>';
				                    	 }
				                    	 /* if(opt.expectedRate != null && opt.expectedRate != '' && opt.expectedRate != 'undefined'){
					                    	 result  = result + '<td><div><span>'+opt.expectedRate+'</span></div></td>';
				                    	 }else{
				                    		 result  = result + '<td><div><span>--</span></div></td>'; 
				                    	 } */
				                    	 
				                    	 if(opt.proposeByName != null && opt.proposeByName != '' && opt.proposeByName != 'undefined'){
					                    	 result  = result + '<td><div><span>'+opt.proposeByName+'</span></div></td>';
				                    	 }else{
				                    		 result  = result + '<td><div><span>--</span></div></td>';
				                    	 }
				                    	
				                    	 result  = result + '</tr>';
				                      } 
				                     result = result +" </tbody>";
				                     result = result +"</table>";
				                     result = result +"</html>";
				                 
				                     $("#ratehistoryDiv").empty(); 
				                     $("#ratehistoryDiv").append(result); 
				                     isShowForm(actionUsertype);
				                     pagination();
						    	}else {
						    		showError("Operation failed, please try again");
						    	}
						},
					});
			       
	} 
</script>

<script>
function pagination(){  
    $('#rateNegotiationTable').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[6, 15, 20, -1], [6, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
}
</script>

<script type="text/javascript">
function isShowForm(actionUsertype){
	 var lastProposedBy = $("#lastProposedBy").val();
     if(lastProposedBy != null && lastProposedBy != "" && lastProposedBy !='undefined' && lastProposedBy == actionUsertype){
    	 $('#negotiationDivId').append("<b>Rate submitted successfully !! Kindly wait for the Response...</b>");
    	 document.getElementById("fieldsDiv").style.display="none";
     }else{
    	 document.getElementById("fieldsDiv").style.display="";
     }
     
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

