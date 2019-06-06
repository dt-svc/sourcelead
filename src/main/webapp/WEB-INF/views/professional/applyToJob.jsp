<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

 <c:if test="${staticTime == null || staticTime == 0 }">
    <c:set var='staticTime' value="<%=new java.util.Date().getTime() %>" scope="application"></c:set>
 </c:if>
  
<link rel="stylesheet" href="/resources/css/style.css?staticTime=${staticTime}" />
<script src="/resources/js/submissionFormBuilder.js?staticTime=${staticTime}" type="text/javascript" ></script> <!-- form builder -->
<script src="/resources/js/submissionForm-html-generator.js?staticTime=${staticTime}" type="text/javascript" ></script> <!-- form generator -->

<script type="text/javascript">
   var sourceleadUrl ='${sourceleadUrl}';
   var tokenParamName = "${_csrf.parameterName}";
   var tokenValue = "${_csrf.token}";
</script>


<div id="popUp"></div>
<script>
function applyToJob(jobTitle,requirementId,referId){
	$(".new_side_wrap").addClass("new_side_wrap_active");
	$(".side_mask").fadeIn(200);
	$("body").css('overflow-y', 'hidden');
	getRequirementSubmissionTemaplate(jobTitle,requirementId,referId);
	ApplicantNotifyRequiremnentProRecruter();
}
</script>

<script>
function getSubmissionDetailsSideBar(jobTitle,submissionId,candidateId,requirementId){
	$(".new_side_wrap").addClass("new_side_wrap_active");
	$(".side_mask").fadeIn(200);
	$("body").css('overflow-y', 'hidden');
	getRequirementSubmissionDetails(jobTitle,submissionId,candidateId,requirementId);
}
</script>

<script type="text/javascript">
 function validateSubmissionCandidateEmail() {
	 var email =   $("input[name=EmailId]").val();
	$('#emailDiv').find('p.jquery_form_error_message').remove();
	 var requirementId = $("#requirementId").val();
	 if(email != null && email != ""){
		 $("#save_button").attr("disabled", "disabled");
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/valdateRecruiterSubmittedCandidateEmail?${_csrf.parameterName}=${_csrf.token}&email="+email+"&requirementId="+requirementId, 
		        success: function(data) {
		         	if(data == 'true'){
			        	  $("#save_button" ).addClass('btn btn-sm btn-primary').val('SUBMIT');
			        	  $('.jquery_form_error_message').remove();
		        		  $("input[name=EmailId]").after("<p class='jquery_form_error_message'>"+email+" is Already Existed</span>");
		        		  $("input[name=EmailId]").setAttribute('record-exist','yes');
		        		  $("input[name=EmailId]").setAttribute('record-exist-errorMsg',email+' is Already Existed'); 
		        	}
		        	else{
		        		$("#save_button").removeAttr("disabled");
		        		$("input[name=EmailId]").removeAttr( "record-exist record-exist-errormsg");
		        		$('.jquery_form_error_message').remove();
		        		
		        	} 
		        },
		    });
	 }
 }
 </script>   


<script>
function getRequirementSubmissionDetails(jobTitle,submissionId,candidateId,requirementId){
	var loginUserId = '${SourceLeadWeb_userId}';
	$.ajax({ 
	    	type: "POST",
	    	  url: sourceleadUrl+"/getProfessionalSubmissionDetails?${_csrf.parameterName}=${_csrf.token}&submissionId="+submissionId, 
	    			
	    	  success: function(data) {
    		  var result = "";
    		   var json = JSON.parse(data);
    		   
	           var submissionDetails = "";
	    	   var details = eval(json.submissionTemplateJsonStringValue);
	    	   
	    	   $.each(details, function( k, v ) {
	    		   var value = '';
	    		   if(v['value'] == null || v['value'] == '' || v['value'] == 'undefined'){
	    			   var value = '-----';
	    		   }else{
	    			   var value = v['value'];
	    		   }
	    		   
	    		   submissionDetails = submissionDetails + '<div class="clearfix formgroup">';
	    		   submissionDetails = submissionDetails + '<div class="col-md-5">'+v['label'];
	    		   submissionDetails = submissionDetails + '</div>';
	    		   submissionDetails = submissionDetails +'<div class="col-md-7">'+value;
	    		   submissionDetails = submissionDetails + '</div>';
	    		   submissionDetails = submissionDetails + '<div class="clearfix">';
	    		   submissionDetails = submissionDetails + '</div>';
	    		   submissionDetails = submissionDetails + '</div>';
	    	   }); 
			  
	   			//Submission Details
	   			 result = result + '<div class="row">';
	   			 result = result + '<div class="col-md-6">';
	   			 result = result + '<h3>'+jobTitle+'</h3>';
	  			 result = result + '<div class="release-jobs submissionDetails">';
			     result = result + '<div class="clearfix sub-details">';
			     result = result + ' <h2 class="active">Submission Details</h2>';
			     result = result + ' <div class="content clearfix" style="display: block;">';
			     result = result + submissionDetails;
			     result = result + ' </div>';
			     result = result + ' </div>';
			   
			     result = result + ' </div>';
			     result = result + ' </div>';
			     
			     result = result + '<div class="col-md-6">';
			     result = result+'<div id="rejectOfferAlert" ></div>  ';
			     
			     result  = result + '   <div id="offerDetails" ></div>';
			     result  = result + '   <div id="internalInterviewsList" ></div>';
			     result = result + ' </div>';
			     
			     result = result + ' </div>';
			     getInternalInterviewsList(submissionId);
			     getOfferDetails(submissionId);   
			     
			     $("#submitDetailsDiv").empty();
			     $("#applytoJobDiv").empty();
			 	 $("#submitDetailsDiv").append(result);

	    	  }
	 });
				   
  
}
</script>



<script>
function getInternalInterviewsList(submissionId){
	var parentRequirement = '${parentRequirement}';
	var loginUserRole = '${SourceLeadWeb_loginUserRole}';
	 $.ajax({  
         type : "POST",
         url: sourceleadUrl+"/getAllCrowdSourcingInterviews?${_csrf.parameterName}=${_csrf.token}&submissionId="+submissionId,        
         success :function(data) {
              if(data != null && data != 'undefined' && data != "" && data != '[]'){
            	
	                	 $("#internalInterviewsList").empty();
	                	 var result = "";
                    	 result = result + '<div class="clearfix">';
   					     result = result + '<div class="content clearfix" style="display: block;">';
	                     var json = JSON.parse(data);
	                     
	                     for (var i = 0; i < json.length; i++) { 
	                    	 
	                    	 var report = json[i]; 
	                    	 var notifiedDetails ="";
	                    	 var interviewTypeHeader = "";
	                    	 var interviewType = "---";
	                    	 var location = "---"; 
	                    	
	                    	 if(report.notificationToApplicant != null && report.notificationToApplicant != 'undefined' && report.notificationToApplicant != "" && report.notificationToApplicant == "Y"){
	                    		 notifiedDetails = 'Applicant';
				             }
	                    	 if(report.notificationToApplicant != null && report.notificationToApplicant != 'undefined' && report.notificationToApplicant != "" && report.notificationToApplicant == "Y" 
	                    			 && report.notificationToSpoc != null && report.notificationToSpoc != 'undefined' && report.notificationToSpoc != "" && report.notificationToSpoc == "Y"){
	                    		 notifiedDetails = notifiedDetails + ' , ';
	                    	 }
	                    	 
	                    	 if(report.notificationToSpoc != null && report.notificationToSpoc != 'undefined' && report.notificationToSpoc != "" && report.notificationToSpoc == "Y"){
	                    		 notifiedDetails = notifiedDetails + 'Vendor';
				             }
	                    	 
	                    	 if(notifiedDetails == ""){
	                    		notifiedDetails = '---';
	                    	 }
	                    	 
	                    	 
	                    	 if(report.interviewId.interviewType != null && report.interviewId.interviewType != 'undefined' && report.interviewId.interviewType != ""){
	                    		 interviewType = report.interviewId.interviewType;
	                    		 interviewTypeHeader = report.interviewId.interviewType;
	                    	 }
	                    	 
	                    	 
	                    	 if(report.interviewId.location != null && report.interviewId.location != 'undefined' && report.interviewId.location != ""){
	                    		 location = report.interviewId.location;
	                    	 }
	                    	 
	   					     result = result + '<div id="accordion-sl" class="release-jobs positionRelative">';
	   					     result = result + '<h2>Interview: <span>'+interviewTypeHeader+' '+report.interviewTimezoneDateStr+' '+report.interviewTimeZoneStartTimeStr+' </span></h2>';
	                    	 result = result + '<div class="content clearfix">';
	                    	
	                    	 result = result + '<div id="feedbackdiv'+report.interviewId.id+'" class="require_wrap_table demo" style="display:none;">';
		                     result = result + '</div>';
	                    	 result = result + '<div class="interview-type" id="interviewtypediv'+report.id+'" style="display:block;">';
	                    	 
	                    	 result = result + '<div class="row clearfix">';
	                    	 result = result + '<div class="action-nav" >';
	                    	 result = result + '<span id="'+report.interviewId.id+'actionDiv">';
		                    	 if(report.interviewId.interviewStatus == null || report.interviewId.interviewStatus == '' || report.interviewId.interviewStatus =='undefined'){
		                    		 if(report.submissionStatus == "REJECTED"){
		                    			 
		                    		 }else{
		                    			 if(report.feedbackStartTime != "feedbackStartTime" || report.feedbackEndTime != "feedbackEndTime"){
				                    		 result = result + '<a href="javascript:void(0)" onclick="changeInterviewStatus('+submissionId+','+report.interviewId.id+',\'Attending\');" title="Attending"><i class="fa fa-check text-success"></i></a>';
					                    	 result = result + '<a href="javascript:void(0)" onclick="changeInterviewStatus('+submissionId+','+report.interviewId.id+',\'Not_Attending\');" title="Not Attending">&nbsp;&nbsp;<i class="fa fa-close text-danger"></i></a>';
					                    	 if(report.proposeTimeStatus=="Y"){
					                    		 result = result + '<a href="javascript:void(0)"  class="brder-int"><i class="fa fa-clock-o text-muted" onclick="proposeTime(\''+report.interviewId.id+'\',\''+submissionId+'\');"></i>';
						                    	 result = result + '			<div class="proposeTimePop" id="proposeTimediv'+report.interviewId.id+'" >';
													result = result + '			</div>';
													result = result + ' </a>';	 
					                    	 }
					                    	 
					                    	 
				                    	 }
		                    		 }
		                    		
		                    }
	                    	 result = result + '</span>';
	                    	 result = result + '<span id="'+report.interviewId.id+'showStatus"></span>';
	                    	 if(report.interviewId.interviewStatus == "Attending"){
	                    		 if(report.interviewId.interviewPhase == "Selected"){
	                    			 result = result +' <font color="green" class="brder-int">Selected</font>';
	                    		 }else if(report.interviewId.interviewPhase == "Rejected"){
	                    			 result = result +' <font color="red" class="brder-int">Rejected</font>';
	                    		 }else{
	                    			 result = result +' <font color="green" class="brder-int">Attending</font>';
	                    		 }
	                    	 }
	                    	 if(report.interviewId.interviewStatus == "Not Attending"){
	                    		 result = result +' <font color="red" class="brder-int">Not Attending</font>';
	                    	 }
	                    	 if(report.interviewId.interviewStatus == "Maybe"){
	                    		 if(report.submissionStatus == "REJECTED"){
	                    			 result = result + '<a href="javascript:void(0)"> <i  class="brder-int">ProposedTime</i>';	 
	                    		 }else{
	                    			 result = result + '<a href="javascript:void(0)"> <i  class="brder-int" onclick="viewProposeTime(\''+report.interviewId.id+'\',\''+submissionId+'\');">ProposedTime</i>';
	                    		 }
	                    		 
		                    	 result = result + '			<div class="proposeTimePop" id="viewProposeTimediv'+report.interviewId.id+'" >';
									result = result + '			</div>';
									result = result + ' </a>';
	                    	 }
	                    	 
	                    	 if(loginUserRole == 'ROLE_CROWDSOURCING_RECRUITER'){
		                    	 if(report.notificationToApplicant == null || report.notificationToApplicant == '' || report.notificationToApplicant =='undefined' || report.notificationToApplicant == 'N'){
		                    		 result = result + '<span id="'+report.id+'applicantDiv">';
		                    		 result = result + '<a href="javascript:void(0)"class="brder-int-left" onclick="sendMailToapplicant('+submissionId+','+report.interviewId.id+','+report.id+');" title="Notify to Applicant">&nbsp;&nbsp;<i class="fa fa-user text-muted"></i></a>';
		                    		 result = result + '</span>';
		                    	 }
	                    	 }
	                    	 
	                    	 
	                    	 result = result + '</div>';
	                    	 result = result + '</div>';
	                    	 
	                    	 result = result + '<div class="row clearfix">';
	                    	 result = result + '<label class="col-md-5">';
	                    	 result = result + '<strong>Interview Details</strong>';
	                    	 result = result + '</label>';
	                    	 result = result + '<p class="col-md-7">'+location+'</p>';
	                    	 result = result + '</div>';

	                    	 result = result + '<div class="row clearfix">';
	                    	 result = result + '<label class="col-md-5">';
	                    	 result = result + '<strong>Interview Type</strong>';
	                    	 result = result + '</label>';
	                    	 result = result + '<p class="col-md-7 tbldata-dotdot-big">'+interviewType+'</p>';
	                    	 result = result + '</div>';
	                    	 
	                    	 result = result + '<div class="row clearfix">';
	                    	 result = result + '<label class="col-md-5">';
	                    	 result = result + '<strong>Date</strong>';
	                    	 result = result + '</label>';
	                    	 result = result + '<p class="col-md-7 tbldata-dotdot-big">'+report.dateOfInterviewStr+'</p>';
	                    	 result = result + '</div>';
	                    	 
	                    	 result = result + '<div class="row clearfix">';
	                    	 result = result + '<label class="col-md-5">';
	                    	 result = result + '<strong>Time</strong>';
	                    	 result = result + '</label>';
                             if(report.endTimeStr != null && report.endTimeStr != 'undefined' && report.endTimeStr != ""){
                            	 result = result + '<p class="col-md-7 tbldata-dotdot-big">'+report.startTimeStr+' to '+report.endTimeStr+'</p>';
	                    	 }else{
	                    		 result = result + '<p class="col-md-7 tbldata-dotdot-big">'+report.startTimeStr+'</p>';
	                    	 }
	                    	 result = result + '</div>';
	                    	 
	                    	 if(report.interviewersList != null && report.interviewersList != 'undefined' && report.interviewersList != ""){
	                    		 result = result + '<div class="row clearfix">';
		                    	 result = result + '<label class="col-md-5">';
		                    	 result = result + '<strong>Interviewers</strong>';
		                    	 result = result + '</label>';
		                    	 result = result + '<p class="col-md-7 tbldata-dotdot-big">'+report.interviewersList+'</p>';
		                    	 result = result + '</div>';
	                    	 }
	                    	 
	                    	 if(report.remainderTime != null && report.remainderTime != 'undefined' && report.remainderTime != ""){
	                    		 result = result + '<div class="row clearfix">';
		                         result = result + '<label class="col-md-5">';
		                         result = result + '<strong>Reminder details</strong>';
		                         result = result + '</label>';
		                         result = result + '<p class="col-md-7 tbldata-dotdot-big">'+report.remainderTime+'</p>';
		                         result = result + '</div>';
	                    	 }
	                        
	                    	 if(loginUserRole == 'ROLE_CROWDSOURCING_RECRUITER'){
		                         result = result + '<div class="row clearfix">';
		                         result = result + '<label class="col-md-5">';
		                         result = result + '<strong>Notified to</strong>';
		                         result = result + '</label>';
		                         result = result + '<p class="col-md-7 tbldata-dotdot-big"><span id="'+report.id+'notifiedDiv">'+notifiedDetails+'<span></p>';
		                         result = result + '</div>';
	                    	  }
	                        
                             if(report.clientName != null && report.clientName != "" && report.clientName != 'undefined' && report.interviewSourceType !="INTERVIEW"){
	                        	 result = result + '<div class="row clearfix">';
		                    	 result = result + '<label class="col-md-5">';
		                    	 result = result + '<strong>Client Name:</strong>';
		                    	 result = result + '</label>';
		                    	 result = result + '<p class="col-md-7 tbldata-dotdot-big">'+report.clientName+'</p>';
		                    	 result = result + '</div>';
	                         }
	                         
	                         result = result + '</div>';
	                         result = result + '</div>';
	                         result = result + '</div>';
	                      }  
	                     result = result + '</div>';
	                     result = result + '</div>';
	                    
	                     $("#internalInterviewsList").append(result); 
	                     $('#accordion-sl .content').hide();
	                    // $('#accordion-sl').find('.test2'+interviewId+'').addClass('active').next().slideDown('slow');
	                     $('#accordion-sl h2:first').addClass('active').next().slideDown('slow'); 
	                     $('#accordion-sl h2').click(function(id) {
	                    	 $('.demo').hide();
	                    	 $(".interview-type").show();
	                         if($(this).next().is(':hidden')) {
	                             $('#accordion-sl h2').removeClass('active').next().slideUp('slow');
	                             $(this).toggleClass('active').next().slideDown('slow');
	                         }
	                     });
              }
       },
         
    }); 
	 
	 
}
</script>


<script>
function getOfferDetails(submissionId){
	var parentRequirement = '${parentRequirement}';
	var loginUserRole = '${SourceLeadWeb_loginUserRole}';
	var locationCode ='${SourceLeadWeb_locationCode}'; 
	 $.ajax({  
         type : "POST",
         url: sourceleadUrl+"/getCrowdSourcingOfferDetails?${_csrf.parameterName}=${_csrf.token}&submissionId="+submissionId+"&locationCode="+locationCode,        
         success :function(data) {
         if(data != null && data != 'undefined' && data != "" && data != '{}'){
            		
            			 $("#offerDetails").empty();
            			 var json = JSON.parse(data);
            			 for (var i = 0; i < json.length; i++) { 
	                    	 
	                    	 var report = json[i]; 
	                    	 
	                    	 var result = '';
	                	     result = result+'<div id="rejectOfferAlert" ></div>  ';
	                	 
	                	     var notifiedDetails ="";
	                    	
	                    	 if(report.notifyToApplicant != null && report.notifyToApplicant != 'undefined' && report.notifyToApplicant != "" && report.notifyToApplicant == "Y"){
	                    		 notifiedDetails = 'Applicant';
				             }
	                    	 if(report.notifyToApplicant != null && report.notifyToApplicant != 'undefined' && report.notifyToApplicant != "" && report.notifyToApplicant == "Y" 
	                    			 && report.notifyToVendor != null && report.notifyToVendor != 'undefined' && report.notifyToVendor != "" && report.notifyToVendor == "Y"){
	                    		 notifiedDetails = notifiedDetails + ' , ';
	                    	 }
	                    	 
	                    	 if(notifiedDetails == ""){
	                    		notifiedDetails = '---';
	                    	 }
	                    
	                	 
	                    	 result = result + '<div class="clearfix">';
	   					     result = result + '<div class="content clearfix" style="display: block;">';
		                  
	   					     result = result + '<div id="accordion-sl" class="release-jobs">';
	                    	 result = result + '<h2  class="active">Offer: <span>'+report.offerSourceType+'</span></h2>';
	                    	 result = result + '<div class="content clearfix">';
	                    	 result = result + '<div class="interview-type">';
	
	                         var accept = "OFFERACCEPTED";
	                    	 var reject = "OFFERREJECTED";
	                    	 
	                    	 result = result + '<div class="row clearfix">';
	                    	 result = result + '<div class="action-nav">';
	                    	 result = result + '<span id="'+report.offerId.id+'offerActionDiv">';
	                    	 if(report.submissionStatusStr != "REJECTED" && report.offerId.offerStatus != "OFFERACCEPTED"  && report.offerId.offerStatus != "OFFERREJECTED" ){
	                    		 	result = result + '<a  href="javascript:void(0)" title="Accept" onClick="acceptingOffer(\''+accept+'\' ,\''+submissionId+'\',\''+report.offerId.id+'\');"   >&nbsp;&nbsp;<i class="fa fa-check text-success"></i></a>';
	                        	 	result = result + '<a  href="javascript:void(0)"  title="Reject"  onClick="rejectOffer( \''+reject+'\' ,\''+submissionId+'\',\''+report.offerId.id+'\');"  >&nbsp;&nbsp;<i class="fa fa-close text-danger"></i></a>';
	                    	 }
	                    	 result = result + '</span>';
	                    	 
	                    	 result = result + '<span id="'+report.offerId.id+'showOfferStatus"></span>';
	                    	 if(report.offerId.offerStatus == "OFFERACCEPTED"){
	                    		 result = result +' <font color="green" class="brder-int">OFFER ACCEPTED</font>';
	                    	 }
	                    	 if(report.offerId.offerStatus == "OFFERREJECTED"){
	                    		 result = result +' <font color="red" class="brder-int">OFFER REJECTED</font>';
	                    	 }
	                    	 if(report.offerId.offerStatus == "Maybe"){
	                    		 result = result +' <font color="orange" class="brder-int">Maybe</font>';
	                    	 }
	                    	 
	                    	 if(loginUserRole == 'ROLE_CROWDSOURCING_RECRUITER'){
		                    	 if(report.notifyToApplicant == null || report.notifyToApplicant == '' || report.notifyToApplicant =='undefined' || report.notifyToApplicant == 'N'){
		                    		 result = result + '<span id="'+report.id+'offerApplicantDiv">';
		                    		 result = result + '<a href="javascript:void(0)" class="brder-int-left" onClick ="notifyToApplicant(\''+ submissionId+ '\',\''+ report.offerId.id+ '\',\''+ report.id+ '\');" title="Notify to Applicant"><i class="fa fa-user text-muted"></i></a>';
		                    		 result = result + '</span>';
		                    	 }
	                    	 }
	                    	
	                    	 result = result + '</div>';
	                    	 result = result + '</div>';
	                    	 
	                    	 if(report.dateOfJoiningStr !=null && report.dateOfJoiningStr !='' && report.dateOfJoiningStr !='undefined'){
		                    	 result = result + '<div class="row clearfix">';
		                    	 result = result + '<label class="col-md-5">';
		                    	 result = result + '<strong>Joining Date</strong>';
		                    	 result = result + '</label>';
		                    	 result = result + '<p class="col-md-7">'+report.dateOfJoiningStr+'</p>';
		                    	 result = result + '</div>';
	                    	 }else{
	                    		 result = result + '<div class="row clearfix">';
		                    	 result = result + '<label class="col-md-5">';
		                    	 result = result + '<strong>Joining Date</strong>';
		                    	 result = result + '</label>';
		                    	 result = result + '<p class="col-md-7">-----</p>';
		                    	 result = result + '</div>';
	                    	 }
	                    	 
	                    	 if(report.offerId.positionType !=null && report.offerId.positionType !='' && report.offerId.positionType !='undefined'){
	                    		 result = result + '<div class="row clearfix">';
	                        	 result = result + '<label class="col-md-5">';
	                        	 result = result + '<strong>Position Type</strong>';
	                        	 result = result + '</label>';
	                        	 result = result + '<p class="col-md-7 tbldata-dotdot-big">'+report.offerId.positionType+'</p>';
	                        	 result = result + '</div>';
	                    	 }else{
	                    		 result = result + '<div class="row clearfix">';
	                        	 result = result + '<label class="col-md-5">';
	                        	 result = result + '<strong>Position Type</strong>';
	                        	 result = result + '</label>';
	                        	 result = result + '<p class="col-md-7 tbldata-dotdot-big">-----</p>';
	                        	 result = result + '</div>';
	                    	 }
	                    	 
	                    	 if(report.clientName !=null && report.clientName !='' && report.clientName !='undefined'){
	                    		 result = result + '<div class="row clearfix">';
	                        	 result = result + '<label class="col-md-5">';
	                        	 result = result + '<strong>Client Name</strong>';
	                        	 result = result + '</label>';
	                        	 result = result + '<p class="col-md-7 tbldata-dotdot-big">'+report.clientName+'</p>';
	                        	 result = result + '</div>';
	                    	 }
	                    	 
	                    	 if(report.offerId.amount !=null && report.offerId.amount !='' && report.offerId.amount !='undefined'){
	                    		 result = result + '<div class="row clearfix">';
	                        	 result = result + '<label class="col-md-5">';
	                        	 result = result + '<strong>Rate</strong>';
	                        	 result = result + '</label>';
	                        	 result = result + '<p class="col-md-7 tbldata-dotdot-big">'+report.offerId.paymentType+' '+report.offerId.amount+' / '+report.offerId.paymentMode+'</p>';
	                        	 result = result + '</div>';
	                    	 }else{
	                    		 result = result + '<div class="row clearfix">';
	                        	 result = result + '<label class="col-md-5">';
	                        	 result = result + '<strong>Rate</strong>';
	                        	 result = result + '</label>';
	                        	 result = result + '<p class="col-md-7 tbldata-dotdot-big">------</p>';
	                        	 result = result + '</div>';
	                    	 }
	                    	
	                    	 if(report.offerId.docFile !=null && report.offerId.docFile !='' && report.offerId.docFile !='undefined'){
	                    		 
	                    		 result = result + '<div class="row clearfix">';
	                        	 result = result + '<label class="col-md-5">';
	                        	 result = result + '<strong>Attachment</strong>';
	                        	 result = result + '</label>';
	                             result = result + '<p class="col-md-7 tbldata-dotdot-big">'+report.offerId.fileName+'<a download class="glyphicon glyphicon-download-alt" href=\"'+sourceleadUrl+'/ws/downloadOfferAttachment?offerId='+report.offerId.id+'\"></a></p>';
	                        	 result = result + '</div>';
	                    	 }else{
	                    		 result = result + '<div class="row clearfix">';
	                        	 result = result + '<label class="col-md-5">';
	                        	 result = result + '<strong>Attachment</strong>';
	                        	 result = result + '</label>';
	                             result = result + '<p class="col-md-7 tbldata-dotdot-big">No Document</p>';
	                        	 result = result + '</div>';
	                    	 }
                    		 
	                    	 if(loginUserRole == 'ROLE_CROWDSOURCING_RECRUITER'){
		                    	 result = result + '<div class="row clearfix">';
		                         result = result + '<label class="col-md-5">';
		                         result = result + '<strong>Notified to</strong>';
		                         result = result + '</label>';
		                         result = result + '<p class="col-md-7 tbldata-dotdot-big"><span id="'+report.id+'offerNotifiedDiv">'+notifiedDetails+'<span></p>';
		                         result = result + '</div>';
	                    	 }
                        	 
                    	
	                         result = result + '</div>';
	                         result = result + '</div>';
	                         result = result + '</div>';
	                    	 
			                     
		                     result = result + '</div>';
		                     result = result + '</div>';
		                     $("#offerDetails").append(result); 
		                     $('#accordion-sl .content').hide();
		                     $('#accordion-sl h2:first').addClass('active').next().slideDown('slow');
		                     $('#accordion-sl h2').click(function() {
		                         if($(this).next().is(':hidden')) {
		                             $('#accordion-sl h2').removeClass('active').next().slideUp('slow');
		                             $(this).toggleClass('active').next().slideDown('slow');
		                         }
		                     });
			    	} 
         } else{
				  $("#offerDetails").empty();
               }
       },
         
    }); 
	 
	 
}

</script>


<script>
function changeInterviewStatus(submissionId,interviewId,interviewStatus){
	 $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/ws/acceptOrRejectInterview?${_csrf.parameterName}=${_csrf.token}&interviewStatus="+interviewStatus+"&requirementInterviewId="+interviewId,	    	   
		    success: function(data) {
		    	 jQuery("#"+(interviewId)+"actionDiv").css("display","none");
		    	 if(interviewStatus == "Attending"){
           		    $("#"+(interviewId)+"showStatus").html("<font color='green' >Attending</font>");
	           	 }
	           	 if(interviewStatus == "Not_Attending"){
	           		$("#"+(interviewId)+"showStatus").html("<font color='red' >Not Attending</font>");
	           	 }
	           	 if(interviewStatus == "Maybe"){
	           		$("#"+(interviewId)+"showStatus").html("<font color='orange' >Maybe</font>");
	           	 }
		    }
	   });
}
</script>

<script>
function sendMailToapplicant(submissionId,interviewId,interviewHistoryId){
	 $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/sendMailToapplicant?${_csrf.parameterName}=${_csrf.token}&submissionId="+submissionId+"&requirementInterviewId="+interviewId+"&interviewHistoryId="+interviewHistoryId,	    	   
		    success: function(data) {
		    	 jQuery("#"+(interviewHistoryId)+"applicantDiv").css("display","none");
           		 var existingVal = $("#"+(interviewHistoryId)+"notifiedDiv").text();
 		    	
 		    	if(existingVal == "" || existingVal == null || existingVal =="---" ){
 		    		 $("#"+(interviewHistoryId)+"notifiedDiv").html("Applicant");
 		    	}
 		    	else{
 		    		 $("#"+(interviewHistoryId)+"notifiedDiv").html(existingVal+",Applicant");
 		    	}
	         }
	   });
}
</script>


<script type="text/javascript">
function canceloffer(){ 
	$("#remove_user_pop, .project_inn_mask").fadeOut(200);
};
</script>

<script type="text/javascript">
function rejectOffer(offerStatus,submissionId,offerId){
	
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	    <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<textarea id='rejectReason' class='form-control' field-name='Reject Reason' data-validation-optional='true' data-validation='validate_Space validate_Description validate_length length2-250' name='rejectReason' value='' placeholder='Terminate Reason' rows='2'></textarea>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onClick ='acceptingOffer(\""+ offerStatus+ "\" ,\""+ submissionId+ "\",\""+ offerId+ "\");'><input class='u_can_color' type='button'  value='Cancel' onClick='canceloffer()'></b>";
	result = result +"	</div>";
	$("#rejectOfferAlert").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
</script>




<script>
function acceptingOffer(offerStatus,submissionId,offerId){
	 var reason;
	 if ($('#rejectReason').length)
	 {
		 reason = document.getElementById("rejectReason").value; 
	 }
	 else
	 {
		 reason = '';
	 }
	 $.ajax({  
         type : "POST",
         url: sourceleadUrl+"/acceptingOffer?${_csrf.parameterName}=${_csrf.token}&submissionId="+submissionId+"&offerStatus="+offerStatus+"&reason="+reason+"&OfferId="+offerId,        
         success :function(data) {

        	 $("#rejectOfferAlert").empty();
           	 
           	 jQuery("#"+(offerId)+"offerActionDiv").css("display","none");
	    	 if(offerStatus == "OFFERACCEPTED"){
      		    $("#"+(offerId)+"showOfferStatus").html("<font color='green' >OFFERACCEPTED</font>");
          	 }
          	 if(offerStatus == "OFFERREJECTED"){
          		$("#"+(offerId)+"showOfferStatus").html("<font color='red' >OFFERREJECTED</font>");
          	 }
 
        	 $("#remove_user_pop, .project_inn_mask").fadeOut(200);
        	 
       },
         
    }); 
	 
	 
}

</script>


<script>
function notifyToApplicant(submissionId,offerId,offerHistoryId){
	
	  $.ajax({  
        type : "POST",
        url: sourceleadUrl+"/sendNotificationAboutRequirementOfferMade?${_csrf.parameterName}=${_csrf.token}&submissionId="+submissionId+"&offerId="+offerId+"&notificationtype=Applicant&offerHistoryId="+offerHistoryId,        
        success :function(data) {
        	    jQuery("#"+(offerHistoryId)+"offerApplicantDiv").css("display","none");
        	    var existingVal = $("#"+(offerHistoryId)+"offerNotifiedDiv").text();
		    	if(existingVal == "" || existingVal == null || existingVal =="---" ){
		    		 $("#"+(offerHistoryId)+"offerNotifiedDiv").html("Applicant");
		    	}
		    	else{
		    		 $("#"+(offerHistoryId)+"offerNotifiedDiv").html(existingVal+",Applicant");
		    	}
      },
        
   });  
}
</script>
<script>
function uploadAddApplicantProfissionalDoc() {
	fileUpload("professionaluploadBtn", "addApplicants", "applyToJob_form");
}
</script>

<script>
function uploadAddApplicantRecrutierDoc() {
	fileUpload("uploadBtn", "addApplicants", "submitCandidateToJob_form");
}
</script>

<script>
function getRequirementSubmissionTemaplate(jobTitle,requirementId,referId){
	 var candidateId = '${candidateId}';
	 var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
	  var finalDateFormatScript = '';
	  if(locationDate.match("yyyy-MM-dd")){
		  finalDateFormatScript = 'yy-mm-dd';
	  }if(locationDate.match("dd-MM-yyyy")){
		  finalDateFormatScript = 'dd-mm-yy'; 
	  }if(locationDate.match("MM-dd-yyyy")){
		  finalDateFormatScript = 'mm-dd-yy'; 
	  }if(locationDate.match("yyyy-dd-MM")){
		  finalDateFormatScript = 'yy-dd-mm'; 
	  }
	 generateOrgSubmissionForm(sourceleadUrl,requirementId,finalDateFormatScript); 
	 
	 var loginUserRole = '${SourceLeadWeb_loginUserRole}';
	 var result = "";
	
	if(loginUserRole == 'ROLE_CROWDSOURCING_PROFESSIONAL'){
		result = result + '<form id="applyToJob_form" name="applyToJob_form" method="post">';
		result = result + '<div class="form-horizontal clearfix select-field applicant-label">';
		result = result + '<input type="hidden" id="requirementId" name="requirementId" value='+requirementId+'>';
		result = result + '<input type="hidden" id="candidateId" name="candidateId" value='+candidateId+'>';
		if(referId != null && referId != '' && referId != 'undefined'){
			result = result + '<input type="hidden" id="referId" name="referId" value='+referId+'>';
		}
		
		result = result + '<div class="row">';
		result = result + '<h3>&nbsp;&nbsp;&nbsp;'+jobTitle+'</h3>';
		result = result + '<div class="venk clearfix">';
		
		if('${resumesCount}' > 0){
		result = result + '<div class="col-md-6">';
		result = result + '<label class="col-md-2 mt10">Resume</label>';
		result = result + '<div class="panel-border_a col-md-8 mt10" id="resumesDiv">';
		result = result + '<select id="candidateResumesList" data-validation="required" name="candidateResume" field-name="Resume" class="form-control block">';
		result = result + '</select>';
		result = result + '</div>';
		result = result + '</div>';
		}
		else{
			result = result + '<label class="mr"><input type="radio" onClick="parseProfessionalResume();" data-validation="required" field-name="Import Resume" id="importResume">&nbsp;Import Resume</label>';
			
			result = result + '<div class="col-md-5 pull-right">';
			result = result + '<div class="col-md-12" id="browseDiv"  style="display: none;">';
			result = result + '<div class="input-group" id="uploadFileGroup">';
			result = result + '<label class="input-group-btn" onchange="uploadAddApplicantProfissionalDoc();"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File';
			result = result + '<input type="file" style="display: none;" name="locationMapFile" id="professionaluploadBtn"  class="upload">';
			result = result + '</span> </label>';
			result = result + '<input type="text" class="form-control" data-validation="validatedoc" data-validation-optional="true" field-name="Resume" readonly id="uploadFile" placeholder="Upload Resume">';
			result = result + '</div>';
			result = result + '</div>';	
			result = result + '</div>';
		}
		
		result = result + '</div>';
		
		result = result + '<div id="sjfb-fields" class="viewTemplate backgroundWhite"> <div id="loading"></div><div id="detailsLoading"></div></div>';
		result = result + '</div>';
		
		result = result + '</div>'; 
		
		result = result + '<div class="row">';
	    result = result + '	<div class="col-md-6 upresume mb" id="skillsDiv" style="display: none;">';
		result = result + '<label class="col-md-4">Skills</label>';
		result = result + '<div class="col-md-8">';
		result = result + '<select multiple data-placeholder="select Skills" class="chosen-select form-control" name="candidateSkills" id="candidateSkills"  data-validation-optional="true">';
		result = result + '<c:forEach var="skill" items="${skillsList}">';
		result = result + "<option value='${skill.name}'>${skill.name}</option>";
		result = result + ' </c:forEach>';
		result = result + '</select>';
		result = result + '		 </div>';
		result = result + '	 </div>';
		result = result + '	 </div>';
		
	    result = result + '<div class="row">';
	    result = result + '	<div class="col-md-6 upresume mb" id="primarySkillDiv" style="display: none;">';
		result = result + '<label class="col-md-4">Primary Skill</label>';
		result = result + '<div class="col-md-8">';
		result = result + '<input type="text" class="form-control" id="primarySkill" name="primarySkill"  field-name="Primary Skill">';
		result = result + '</div>';
		result = result + '</div>';
		result = result + '</div>'; 
	                        
		
		result = result + '<div class="form-footer clearfix">';
		result = result + '<div class="pull-right mr">';
		result = result + '<button type="submit" id="save_button"  class="btn btn-sm btn-primary ">SUBMIT</button>';
		result = result + '</div>';
		result = result + '</div>';
		result = result + '</div>';  
		result = result + '</form>';
	}
	
	else if(loginUserRole == 'ROLE_CROWDSOURCING_RECRUITER'){
		
		result = result + '<form id="submitCandidateToJob_form" name="submitCandidateToJob_form" enctype="multipart/form-data" method="post">';
		result = result + '<div class="form-horizontal clearfix select-field applicant-label">';
		result = result + '<input type="hidden" id="requirementId" name="requirementId" value='+requirementId+'>';
				
		result = result + '<div class="row">';
		result = result + '<h3>&nbsp;&nbsp;&nbsp;'+jobTitle+'</h3>';
		result = result + '<div class="venk clearfix">';
		result = result + '<div class="col-md-7 radioInline">';
		result = result + '<label class="mr"><input type="radio" checked="checked" value="Candidates" name="candidateType" onClick="getCandidatesList('+requirementId+',\'Candidate\');">Candidates</label>';
		result = result + '<label class="mr"><input type="radio" value="Consultants" name="candidateType" onClick="getCandidatesList('+requirementId+',\'FavCandidates\');">Favourite Candidates</label>';
		result = result + '<label class="mr"><input type="radio" value="Parese candidate" name="candidateType" onClick="getCandidatesList('+requirementId+',\'parseCandidate\');">Import Candidate</label>';
		result = result + '</div>';	
		
		result = result + '<div class="col-md-5 pull-right">';
		result = result + '<div class="col-md-12" id="browseDiv"  style="display: none;">';
		result = result + '<div class="input-group" id="uploadFileGroup">';
		result = result + '<label class="input-group-btn" onchange="uploadAddApplicantRecrutierDoc();"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File';
		result = result + '<input type="file" style="display: none;" name="locationMapFile" id="uploadBtn"  class="upload">';
		result = result + '</span> </label>';
		result = result + '<input type="text" class="form-control" data-validation="validatedoc" data-validation-optional="true" field-name="Resume" readonly id="uploadFile" placeholder="Upload Resume">';
		result = result + '</div>';
		result = result + '</div>';	
		result = result + '</div>';
		
		result = result + '<div class="clearfix"></div>';
		result = result + '<div class="col-md-6 mt10">';
		result = result + '<div id="selectCandidateDiv" style="display: block;">';
		result = result + '<select class="form-control chosen-select" onchange="getRecruiterCandidateDetails(); getCandidateResumes();"  name="candidateId" id="candidateId" data-validation="required" field-name="Applicant" class="form-control  block mt10">';
		result = result + '<option value="">--Select Candidate--</option>';
		result = result + '</select>';
		result = result + '</div>';	
		result = result + '</div>';	
		
		result = result + '<div class="panel-border_a col-md-5 mt10" id="resumesDiv"  style="display: none;">';
		result = result + '<select id="candidateResumesList" name="candidateResume" field-name="Resume" class="form-control block">';
		result = result + '</select>';
		result = result + '</div>';
		
		result = result + '<div id="orConditionDiv" class="text-center text-info col-md-2" style="display: none;">';
		result = result + '------- ( OR ) -------';
		result = result + '</div>';
		
		
		result = result + '</div>';
		
		result = result + '<div id="sjfb-fields" class="viewTemplate"> <div id="loading"></div> <div id="emailDiv"></div>';
		
		result = result + '</div>';
		
	                        
		result = result + '</div>';
		
		
		result = result + '<div class="row">';
	    result = result + '	<div class="col-md-6 upresume mb" id="skillsDiv" style="display: none;">';
		result = result + '<label class="col-md-4">Skills</label>';
		result = result + '<div class="col-md-8">';
		result = result + '<select multiple data-placeholder="select Skills" class="chosen-select form-control" name="candidateSkills" id="candidateSkills"  data-validation-optional="true">';
		result = result + '<c:forEach var="skill" items="${skillsList}">';
		result = result + "<option value='${skill.name}'>${skill.name}</option>";
		result = result + ' </c:forEach>';
		result = result + '</select>';
		result = result + '		 </div>';
		result = result + '	 </div>';
		result = result + '	 </div>';
		
	    result = result + '<div class="row">';
	    result = result + '	<div class="col-md-6 upresume mb" id="primarySkillDiv" style="display: none;">';
		result = result + '<label class="col-md-4">Primary Skill</label>';
		result = result + '<div class="col-md-8">';
		result = result + '<input type="text" class="form-control" id="primarySkill" name="primarySkill"  field-name="Primary Skill">';
		result = result + '</div>';
		result = result + '</div>';
		result = result + '</div>'; 
		
		result = result + '<div class="row">';
		result = result + '	<div class="col-md-6 upresume mb" id="newResumeDiv" style="display: none;">';
		result = result + '<label class="col-md-4">Add Resume &nbsp;*</label>';
		result = result + '		<div class="col-md-8">';
		result = result + '			    <div class="input-group col-md-12 attachfile-error" id="uploadFileGroup1" style="margin:0 auto">';
		result = result + '			        <label class="input-group-btn"> <span class="btn btn-danger"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File';
		result = result + '                   <input type="file" style="display: none;" name="newUploadBtn" id="newUploadBtn"  class="upload">';
		result = result + '			          </span> </label>';
		result = result + '			          <input type="text" class="form-control" readonly id="newUploadFile" placeholder="Upload Resume ..." field-name="Upload Resume" data-validation="required validatedoc "  >';
		result = result + '			    </div>';
		result = result + '		 </div>';
		result = result + '	 </div>';
		
	
		
		
		
		result = result + '</div>';
		
		result = result + '	<div class="col-md-6 upresume mb">';
		result = result + '<label class="col-md-4">&nbsp;</label>';
		result = result + '<input type="checkbox" name="needApplicantPermit" id="needApplicantPermit" onClick="ApplicantPermitProRecruter();" >&nbsp;&nbsp;<span><strong>Need applicant permission</strong></span><br>';
		result = result + '<input type="checkbox" checked="checked" name="notifyToApplicant" id="notifyToApplicantId" onClick="ApplicantNotifyRequiremnentProRecruter();" >&nbsp;&nbsp;<span><strong>Notify to applicant</strong></span> ';
		result = result + '</div>';
		result = result + '</div>';
		
		
		result = result + '<div class="form-footer clearfix mt">';
		result = result + '<div class="pull-right mr">';
		result = result + '<button type="submit" id="save_button"  class="btn btn-sm btn-primary ">SUBMIT</button>';
		result = result + '</div>';
		result = result + '</div>';
		result = result + '</div>';  
		result = result + '</form>';
		
		getCandidatesList(requirementId,'Candidate');
	}
	
	
	$("#submitDetailsDiv").empty();
	$("#applytoJobDiv").empty();
	$("#applytoJobDiv").append(result);
	
	if(loginUserRole == 'ROLE_CROWDSOURCING_PROFESSIONAL'){
		$("#detailsLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		    setTimeout(function () {
			getResumesList();
			getCandidateDetails();
		    $("#detailsLoading").removeAttr('style');
 			}, 3000);
	
	}

	   var config = {
				  '.chosen-select'           : {},
				  '.chosen-select-deselect'  : {allow_single_deselect:true},
				  '.chosen-select-no-single' : {disable_search_threshold:10},
				  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
				  '.chosen-select-width'     : {width:"95%"}
				}
				for (var selector in config) {
				  $(selector).chosen(config[selector]);
				}
				$('.chosen-select').each(function() {
					var $this = $(this);
					$this.next().css({'width': '100%'});
					})
			
			
	  var select, chosen;
	    select = $("#candidateSkills");
	    select.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add New Skill:' });
	    chosen = select.data('chosen');
	    
	    
	    $("#candidateSkills").chosen().change(function(event) {
	    	var allSkills = $(event.target).val();
	   
	    	
	    	$.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/profileBySkillSet?${_csrf.parameterName}=${_csrf.token}&allSkills="+allSkills, 
		        success: function(response) {
		        	if (response.response === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (response.response === "SUCCESS") {
		        		var primarySkill = response.object;
			        	$("#primarySkill").val(primarySkill);
		        	} else if (response.indexOf('Sign In') !== -1) {
		        		location.replace(sourceleadUrl);
		        	} else {
		        	    showError("Operation failed, please try again");
		        	}
		        },
		    });
	    });
	    chosen.search_field.on('keyup', function(e)
			    {
			    	   var skillsArray = this.value.split(",");
				    	 if(skillsArray.length > 1){
			        		 for (var i=0; i < skillsArray.length; i++) {
			        			 var option = $("<option>").val(skillsArray[i].replace(",","")).text(skillsArray[i].replace(",",""));
			        			 select.append(option);
			 		             select.find(option).prop('selected', true);
			 		             select.find(option).siblings("[value='"+ skillsArray[i].replace(",","")+"']").remove();
			 	        		 $("#candidateSkills").trigger("chosen:updated");
			                 }
				    	 }else{
			    			var newSkill = this.value.replace(","," ");
			    			 if ((e.which == 13 || e.which == 188) && (newSkill != null  && newSkill != '' && newSkill != 'undefined'))
			                {
			    				 var option = $("<option>").val(newSkill).text(newSkill);
			                     select.append(option);
			                     select.find(option).prop('selected', true);
			                     $("#candidateSkills option").each(function(){
			                         $(this).siblings("[value='"+ this.value+"']").remove();
			                   });
			                   $("#candidateSkills").trigger("chosen:updated");
			                }
					       
		    	         }
	    	});
	    //Adding Customized Skills to the list
			
	$("#candidateSkills").val('').trigger("chosen:updated");
	
	$(".new_side_wrap").addClass("new_side_wrap_active");
	$(".side_mask").fadeIn(200);
	$("body").css('overflow-y', 'hidden');
	
	var selectedFile = document.getElementById("newUploadBtn");
    selectedFile.addEventListener("change", function() {
 	 $("#newUploadFile").focus();
 	 if(validate_fileupload(this.value)){
 		 var docSize = this.files[0].size;
	      $("#uploadFileGroup1").find('p.jquery_form_error_message').remove();
	      if(docSize > 2097152){
	    	  document.getElementById("newUploadFile").value = '';
	    	  $('#uploadFileGroup1').find('p.jquery_form_error_message').remove();
			  $("input#newUploadFile").after("<p class='jquery_form_error_message'>Limit is exceeded than 2MB </span>");
			  document.getElementById("newUploadFile").setAttribute('record-exist','yes');
			  document.getElementById("newUploadFile").setAttribute('record-exist-errorMsg','Limit is exceeded than 2MB'); 
	      }else{
	    	 $('input#newUploadFile').removeAttr( "record-exist record-exist-errormsg");
   		 $('#uploadFileGroup1').find('p.jquery_form_error_message').remove(); 
 	    }
 	 }
 });
	
	document.getElementById("newUploadBtn").onchange = function () {
		$('#newUploadFile').val($(this).val().replace(/C:\\fakepath\\/i, ''));
	};
	
}
</script>



<script type="text/javascript">
function ApplicantPermitProRecruter(){
	 if($("#needApplicantPermit").is(':checked')){
		  $("#needApplicantPermit").val("Y");
	 }else{
		 $("#needApplicantPermit").val("N");
	 }
}
</script>


<script type="text/javascript">
function ApplicantNotifyRequiremnentProRecruter(){
	 if($("#notifyToApplicantId").is(':checked')){
		  $("#notifyToApplicantId").val("Y");
	 }else{
		 $("#notifyToApplicantId").val("N");
	 }
}
</script>


<script type="text/javascript">
 function getCandidatesList(requirementId,candidateType) {
	 $('#sjfb-fields').find('p.jquery_form_error_message').remove();
	 $('#uploadFileGroup').find('p.jquery_form_error_message').remove();
	 $('#uploadFileGroup1').find('p.jquery_form_error_message').remove();
	 
	 $('#candidateId').removeAttr('data-validation-optional');
	 $("#candidateId").attr("data-validation", "required");
	 
	 var candidateTypeSelect = "";
	 if(candidateType == 'FavCandidates'){
		 candidateTypeSelect = "Favourite Candidates";
	 }else{
		 candidateTypeSelect = candidateType;
	 }
	 
	 if(candidateType != null && candidateType != "" && candidateType != 'undefined' && candidateType != 'parseCandidate'){
		 $("#selectCandidateDiv").css("display","block");
		
		 var skillsDiv = document.getElementById('skillsDiv'); 
		 if(skillsDiv != null){
			 document.getElementById("skillsDiv").style.display="none";
			 $("#candidateSkills").val('').trigger("chosen:updated");
			 
			 document.getElementById("primarySkillDiv").style.display="none";
			 $("#primarySkill").val('');
		 }
		 
		 var newResumeDiv =  document.getElementById('newResumeDiv'); 
		 if(newResumeDiv != null){
			 document.getElementById("newResumeDiv").style.display="none";
		 }
		 
		 if(requirementId != null && requirementId != "" && requirementId != 'undefined'){
			 $.ajax({
			    	type: "POST",
			        url: sourceleadUrl+"/getRecruiterCandidatesList?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId+"&candidateType="+candidateType, 
			        success: function(data) {
			        	var json = JSON.parse(data);
			        	var output = "";
			        	output = output + "<option value=''>-- Select "+candidateTypeSelect+" --</option>"; 
			        	for (var i=0; i<json.length; i++)
						{
				        		var opt = json[i];
				        		var lastName = "";
				        		if(opt.lastName != null && opt.lastName != "" && opt.lastName != 'undefined'){
				        			lastName = opt.lastName;
				        		}
				        		output = output + "<option value='" +opt.candidateId+ "'>"+opt.firstName+" "+lastName+"</option>";
						}
			        	$("#candidateId").empty();
				        $("#candidateId").append(output);
				        $('#candidateId').trigger("chosen:updated");
				        
				        getCandidateResumes();
			        },
			    }); 
		 }else{
			 var output = "";
	     	 output = output + "<option value=''>-- Select Candidate --</option>"; 
	     	 $("#candidateId").empty();
		     $("#candidateId").append(output);
		     $('#candidateId').trigger("chosen:updated");
		 }
	 }else{
		 var output = "";
     	 $("#candidateId").empty();
	     $("#candidateId").append(output);
	     $('#candidateId').trigger("chosen:updated");
	     $("#selectCandidateDiv").css("display","none");
	     
	     $("#candidateId").removeAttr("data-validation");
	     $('#candidateId').attr("data-validation-optional","true"); 
	     
	     $("#candidateResumesList").removeAttr("data-validation");
	     $('#candidateResumesList').attr("data-validation-optional","true"); 
	     
	     if(candidateType != null && candidateType != "" && candidateType != 'undefined' && candidateType == 'parseCandidate'){
	    	 document.getElementById("browseDiv").style.display="";
	    	 $('#uploadFile').removeAttr('data-validation-optional');
			 $('#uploadFile').attr("data-validation","required validatedoc "); 
			 
			 document.getElementById("skillsDiv").style.display="";
			 document.getElementById("primarySkillDiv").style.display="";
			 document.getElementById("newResumeDiv").style.display="none";
			 
			 $("#candidateSkills").val('').trigger("chosen:updated");
			 $('#newUploadFile').removeAttr('data-validation');
			 $('#newUploadFile').attr("data-validation-optional","true"); 
	     }
	     document.getElementById("resumesDiv").style.display="none";
		 document.getElementById("orConditionDiv").style.display="none";
		
	 }
	 
	 $("input[name=FirstName]").val('');
	 $("input[name=LastName]").val('');
	 $("input[name=EmailId]").val('');
	 $("input[name=EmailId]").removeAttr("readonly");
	 
	 
	 $("input[name=PhoneNumber]").val('');
	 $("input[name=Designation]").val('');
	 $("select[name=state]").val('');
	 $("select[name=city]").val('');
	 $("select[name=NoticePeriod]").val('');
	 $("select[name=WorkExperience]").val(0);
	 $("input[name=ExpectedCTC]").val('');
	 $("input[name=CurrentCTC]").val('');
	 $("input[name=Education]").val('');
	 
	 $("#newUploadFile").val('');
	 $("#uploadFile").val('');
	
 }
</script>

<script type="text/javascript">
 function getCandidateResumes() {
	 $("#save_button").removeAttr("disabled");
	 $('.jquery_form_error_message').remove();
	 var candidateId = document.getElementById("candidateId").value
	 if(candidateId != null && candidateId != "" && candidateId != 'undefined'){
		
		 document.getElementById("resumesDiv").style.display="";
		 document.getElementById("browseDiv").style.display="none";
		 document.getElementById("orConditionDiv").style.display="none";
		 $("#uploadFile").removeAttr("data-validation");
		 $("#uploadFile").val('');
		 $('.jquery_form_error_message').remove();
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getCandidateResumes?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId, 
		        success: function(data) {
		        	var json = data.data;
		        	if (data.response === "FAILED") {
			    		showError(data.responseMessage);
			    	} else if (data.response === "AWKWARD") {
			    		location.href = "/errorPage";
			    	} else if (data.response === "SUCCESS") {
			        	if(json!=null){
				        	var output = "";
				        	
				        	if(json.length == 1){
				        		var opt = json[0];
				        		if(opt.title != null && opt.title != 'undefined' && opt.title != ""){
				        			output = output + "<option value='" +opt.id+ "'>"+opt.title+"</option>";
				            	}else{
				            		output = output + "<option value='" +opt.id+ "'>"+opt.fileName+"</option>";
				            	}
				        	}else{
				        		output = output + "<option value=''>-- Select Resume --</option>"; 
					        	for (var i=0; i<json.length; i++)
								{
						        		var opt = json[i];
						        		if(opt.title != null && opt.title != 'undefined' && opt.title != ""){
						        			output = output + "<option value='" +opt.id+ "'>"+opt.title+"</option>";
						            	}else{
						            		output = output + "<option value='" +opt.id+ "'>"+opt.fileName+"</option>";
						            	}
								}
				        	}
				        	
				        	$("#candidateResumesList").empty();
					        $("#candidateResumesList").append(output);
					        
					        document.getElementById("skillsDiv").style.display="none";
					        document.getElementById("primarySkillDiv").style.display="none";
					        document.getElementById("newResumeDiv").style.display="none";
					        
					        $('#newUploadFile').removeAttr('data-validation');
			        		$('#newUploadFile').attr("data-validation-optional","true"); 
			        		
			        		 $('#candidateResumesList').removeAttr('data-validation-optional');
			    			 $("#candidateResumesList").attr("data-validation", "required");
			    			
			        	}else{
			        		
			        		 $('#candidateResumesList').removeAttr('data-validation');
			        		 $('#candidateResumesList').attr("data-validation-optional","true"); 
			        		
			        		 document.getElementById("resumesDiv").style.display="none";
			        		 document.getElementById("browseDiv").style.display="none";
			        		 document.getElementById("orConditionDiv").style.display="none";
			        		
			        		 document.getElementById("newResumeDiv").style.display="";
			        		 $('#newUploadFile').removeAttr('data-validation-optional');
			        		 $("#newUploadFile").attr("data-validation", "required validatedoc");
			        		 
			        		 $('#uploadFile').removeAttr('data-validation');
			        		 $('#uploadFile').attr("data-validation-optional","true"); 
			        	}
			    	}else if (data.indexOf('Sign In') !== -1) {
			    		location.replace(sourceleadUrl);
			    	} else {
			    		showError("Operation failed, please try again");
			    	}
		        },
		    });
	 }else{
		 var output = "";
     	 output = output + "<option value=''>-- Select Resume --</option>"; 
     	 $("#candidateResumesList").empty();
     	 $("#candidateResumesList").removeAttr("data-validation");
		 document.getElementById("resumesDiv").style.display="none";
		 document.getElementById("browseDiv").style.display="none";
		 document.getElementById("orConditionDiv").style.display="none";
		 $('#uploadFile').removeAttr('data-validation');
		 $('#uploadFile').attr("data-validation-optional","true"); 
	     $("#candidateResumesList").append(output);
	 }
	
 }
</script>


<script>
 $(document.body).on('change', '#uploadBtn' ,function(){
	 $('#uploadFile').val(this.value.split( '\\' ).pop());
	 $("#uploadFile").focus();
 	 if(validate_fileupload(this.value)){
 		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		var form = $('#submitCandidateToJob_form')[0];
  		var formData = new FormData(form); 
		   $.ajax({
				    type: "POST",
			    	data: formData,
		   	        contentType : 'application/json; charset=utf-8',
		   	        enctype: 'multipart/form-data',
			        processData: false,
			        contentType:false,
		   	        dataType : 'json',
					url : sourceleadUrl+"/parseResume?${_csrf.parameterName}=${_csrf.token}",
					success : function(response) {
						if (response.response === "AWKWARD") {
						    location.href = "/errorPage";
						} else if (response.response === "SUCCESS") {
							if(response.object !== null) {
								var json = response.object;
								var name = json.name;
								var firstName = '';
								var lastName = '';
								if(name != null && name != 'undefined' && name != ''){
									 firstName = name.split(".")[0]; 
									 lastName = name.split(".")[1]; 
									 if(firstName == null || firstName ==  'undefined' || firstName == '' ||
											 lastName ==  null || lastName ==  'undefined'|| lastName ==  ''){
										 firstName = name;
									 }
								}
								
								
								 $("input[name=FirstName]").val(firstName);
					        	 $("input[name=LastName]").val(lastName);
					        	 $("input[name=EmailId]").val(json.email);
					        	 $("input[name=PhoneNumber]").val(json.phoneNumbers);
					        	 
					        	 $("#candidateSkills").empty();
					        	 var internalOutput = "";
		 						 <c:forEach var="skill" items="${skillsList}">
		 						    internalOutput = internalOutput+"<option value='${skill.name}'>${skill.name}</option>";
		 	                     </c:forEach>
		 						var skills = json.skills;
		 						if(skills != null && skills != 'undefined' && skills != ''){
		 							var str_array = skills.split(',');
		 							for(var i = 0; i < str_array.length; i++) {
		 							   str_array[i] = str_array[i].replace(/^\s*/, "").replace(/\s*$/, "");
		 							   internalOutput = internalOutput+"<option value='" +str_array[i]+ "' selected>"+str_array[i]+"</option>";
		 							}
		 						}
		 						$("#candidateSkills").append(internalOutput);
		 		        		$("#candidateSkills").trigger("chosen:updated");
		  						
					        	 
								 validateCandidateEmail();
								 getAllSubmissionCandidateCountries();
								 setRateTypesByLocation();
								 dobDatePicker();
							} else {
								showError("Resume Parsing Failed");
							}
							 $("#loading").removeAttr("style");
						} else if (response.indexOf('Sign In') !== -1) {
							location.replace(sourceleadUrl);
						} else {
						    showError("Operation failed, please try again");
						}
					},
				});
 	 }
 });

</script> 


<script type="text/javascript">
 function validateCandidateEmail() {
	 var email =   $("input[name=EmailId]").val();
	$('#emailDiv').find('p.jquery_form_error_message').remove();
	 var requirementId = $("#requirementId").val();
	 if(email != null && email != ""){
		 $("#save_button").attr("disabled", "disabled");
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/validateRequirementRecruiterCandidateEmail?${_csrf.parameterName}=${_csrf.token}&email="+email+"&requirementId="+requirementId, 
		        success: function(data) {
		         	if(data == 'true'){
			        	  $("#save_button" ).addClass('btn btn-sm btn-primary').val('SUBMIT');
			        	  $('.jquery_form_error_message').remove();
		        		  $("input[name=EmailId]").after("<p class='jquery_form_error_message'>"+email+" is Already Existed</span>");
		        		  $("input[name=EmailId]").setAttribute('record-exist','yes');
		        		  $("input[name=EmailId]").setAttribute('record-exist-errorMsg',email+' is Already Existed'); 
		        	}
		        	else{
		        		$("#save_button").removeAttr("disabled");
		        		$("input[name=EmailId]").removeAttr( "record-exist record-exist-errormsg");
		        		$('.jquery_form_error_message').remove();
		        		
		        	} 
		        },
		    });
	 }
 }
 </script>

<script type="text/javascript">
function validate_fileupload(fileName)
{
    var allowed_extensions = new Array("docx","doc","pdf","rtf");
    var file_extension = fileName.split('.').pop(); // split function will split the filename by dot(.), and pop function will pop the last element from the array which will give you the extension as well. If there will be no extension then it will return the filename.

    for(var i = 0; i <= allowed_extensions.length; i++)
    {
        if(allowed_extensions[i]==file_extension)
        {
            return true; // valid file extension
        }
    }

    return false;
}
</script>



<script>
function getResumesList(){
	
	 var candidateId = '${candidateId}';
	 $.ajax({
	    	type: "POST",
	        url: sourceleadUrl+"/getCandidateResumes?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId, 
	        success: function(data) {
	        	var json = data.data;
	        	if (data.response === "FAILED") {
		    		showError(data.responseMessage);
		    	} else if (data.response === "AWKWARD") {
		    		location.href = "/errorPage";
		    	} else if (data.response === "SUCCESS") {
		        	if(json!=null){
			        	var output = "";
			        	if(json.length > 1){
			        		output = output + "<option value=''>-- Select Resume --</option>";
			        	}
			        	
			        	for (var i=0; i<json.length; i++)
						{
				        		var opt = json[i];
				        		if(opt.title != null && opt.title != 'undefined' && opt.title != ""){
				        			output = output + "<option value='" +opt.id+ "'>"+opt.title+"</option>";
				            	}else{
				            		output = output + "<option value='" +opt.id+ "'>"+opt.fileName+"</option>";
				            	}
						}
			        	$("#candidateResumesList").empty();
				        $("#candidateResumesList").append(output);
				        
		        	}else{
		        		 document.getElementById("noProfilesFound").style.display="";
		        	}
		    	}else if (data.indexOf('Sign In') !== -1) {
		    		location.replace(sourceleadUrl);
		    	} else {
		    		showError("Operation failed, please try again");
		    	}
	        },
	    });
}
</script>

<script type="text/javascript">
    function getRecruiterCandidateDetails(){
	
	    var candidateId = document.getElementById("candidateId").value
	    var requirementId = $("#requirementId").val();
		var orgLocationId ='${SourceLeadWeb_orgLocationId}';
	
	if(candidateId != null && candidateId != "" && candidateId != 'undefined'){
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getCandidateDetails?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId+"&requirementId="+requirementId+"&orgLocationId="+orgLocationId,		
		        success: function(data) {
		        	if (data.response === "FAILED") {
			    		showError(data.responseMessage);
			    	} else if (data.response === "AWKWARD") {
			    		location.href = "/errorPage";
			    	} else if (data.response === "SUCCESS") {
			    		var json = data.object;
		        	
			        	 $("input[name=FirstName]").val(json['firstName']);
			        	 $("input[name=EmailId]").val(json['email']);
			        	 $("input[name=EmailId]").attr('readonly', 'readonly');
			        	 
			        	 if(json['lastName'] != null && json['lastName'] != "" && json['lastName'] != 'undefined'){
			        		 $("input[name=LastName]").val(json['lastName']);
			        	 }else{
			        		 $("input[name=LastName]").val(''); 
			        	 }
			        	
			        	 if(json['mobilePhone'] != null && json['mobilePhone'] != "" && json['mobilePhone'] != 'undefined'){
			        		 $("input[name=PhoneNumber]").val(json['mobilePhone']);
			        	 }else{
			        		 $("input[name=PhoneNumber]").val(''); 
			        	 }
			        	 
			        	 if(json['alternateMobilePhone'] != null && json['alternateMobilePhone'] != "" && json['alternateMobilePhone'] != 'undefined'){
			        		 $("input[name=AlternateNumber]").val(json['alternateMobilePhone']);
			        	 }else{
			        		 $("input[name=AlternateNumber]").val(''); 
			        	 }
			        	 
			        	 if(json['designation'] != null && json['designation'] != "" && json['designation'] != 'undefined'){
			        		 $("input[name=Designation]").val(json['designation']);
			        	 }else{
			        		 $("input[name=Designation]").val(''); 
			        	 }
			        	 
			        	 if(json['candidateEdu'] != null && json['candidateEdu'] != "" && json['candidateEdu'] != 'undefined'){
			        		 $("input[name=Education]").val(json['candidateEdu']);
			        	 }else{
			        		 $("input[name=Education]").val(''); 
			        	 }
			        	 
			        	 if(json['location'] != null && json['location'] != "" && json['location'] != 'undefined'){
			        		 $("#submissionLocation").val(json['location']);
			        	 }else{
			        		 $("#submissionLocation").val('');
			        	 }
	
			        	 if(json['country'] != null && json['country'] != "" && json['country'] != 'undefined'){
			        		 $("#submissionCountry").val(json['country']);
			        	 }else{
			        		 $("#submissionCountry").val('');
			        	 }
			        	 
			        	 if(json['state'] != null && json['state'] != "" && json['state'] != 'undefined'){
			        		 $("#submissionState").val(json['state']);
			        	 }else{
			        		 $("#submissionState").val('');
			        	 }
			        	 
			        	 
			        	 if(json['city'] != null && json['city'] != "" && json['city'] != 'undefined'){
			        		 $("#submissionCity").val(json['city']);
			        	 }else{
			        		 $("#submissionCity").val('');
			        	 }
			        	 
			        	 if(json['zipCode'] != null && json['zipCode'] != "" && json['zipCode'] != 'undefined'){
			        		 $("#submissionZipCode").val(json['zipCode']);
			        	 }else{
			        		 $("#submissionZipCode").val('');
			        	 }
			        	 if(json['noticePeriod'] != null && json['noticePeriod'] != "" && json['noticePeriod'] != 'undefined'){
			        		 $("select[name=NoticePeriod]").val(json['noticePeriod']);
			        	 }else{
			        		 $("select[name=NoticePeriod]").val('');
			        	 }
			        	 
			        	 if(json['experienceYears'] != null && json['experienceYears'] != "" && json['experienceYears'] != 'undefined'){
			        		 $("select[name=WorkExperience]").val(json['experienceYears']);
			        		 if(json['experienceYears'] == "25+"){
			        			 $("#monthExp").attr("disabled", "disabled");
			        		 }else{
			        			 $("#monthExp").removeAttr("disabled");
			        		 }
			        	 }else{
			        		 $("select[name=WorkExperience]").val(0);
			        	 }
			        	 
			        	 if(json['experienceMonths'] != null && json['experienceMonths'] != "" && json['experienceMonths'] != 'undefined'){
			        		 $("select[name=monthExp]").val(json['experienceMonths']);
			        	 }else{
			        		 $("select[name=monthExp]").val(0);
			        	 }
			        	 
			        	 if(json['expectedRate'] != null && json['expectedRate'] != "" && json['expectedRate'] != 'undefined'){
			        		 $("input[name=ExpectedCTC]").val(json['expectedRate']);
			        	 }else{
			        		 $("input[name=ExpectedCTC]").val(''); 
			        	 }
			        	 
			        	 
			        	 if(json['rateType'] != null && json['rateType'] != "" && json['rateType'] != 'undefined'){
			        		 $("select[name=expectedCTCRatePer]").val(json['rateType']);
			        		 validateExpectedCTCRatePer();
			        	 }else{
			        		 $("select[name=expectedCTCRatePer]").val('');
			        		 validateExpectedCTCRatePer();
			        	 }
			        	 
			        	 if(json['expectedRateCurrencey'] != null && json['expectedRateCurrencey'] != "" && json['expectedRateCurrencey'] != 'undefined'){
			        		 $("select[name=expectedCTCRateType]").val(json['expectedRateCurrencey']);
			        	 }
			        	 
			        	 if(json['currentRate'] != null && json['currentRate'] != "" && json['currentRate'] != 'undefined'){
			        		 $("input[name=CurrentCTC]").val(json['currentRate']);
			        		 validateCurrentCTC();
			        	 }else{
			        		 $("input[name=CurrentCTC]").val(''); 
			        		 validateCurrentCTC();
			        	 }
			        	 
			        	 if(json['currentRateType'] != null && json['currentRateType'] != "" && json['currentRateType'] != 'undefined'){
			        		 $("select[name=currentCTCRatePer]").val(json['currentRateType']);
			        		 validateExpectedCTC();
			        		 validateCurrentCTCRatePer();
			        	 }else{
			        		 $("select[name=currentCTCRatePer]").val(''); 
			        		 validateExpectedCTC();
			        		 validateCurrentCTCRatePer();
			        	 }
			        	 
			        	 if(json['currentRateCurrency'] != null && json['currentRateCurrency'] != "" && json['currentRateCurrency'] != 'undefined'){
			        		 $("select[name=currentCTCRateType]").val(json['currentRateCurrency']);
			        	 }
			    	}else if (data.indexOf('Sign In') !== -1) {
			    		location.replace(sourceleadUrl);
			    	} else {
			    		showError("Operation failed, please try again");
			    	}
		        },
		    });
	 }else{
		 $("input[name=FirstName]").val('');
    	 $("input[name=LastName]").val('');
    	 $("input[name=EmailId]").val('');
    	 $("input[name=EmailId]").removeAttr("readonly");
    	 
    	 
    	 $("input[name=PhoneNumber]").val('');
    	 $("input[name=Designation]").val('');
    	 $("select[name=country]").val('');
    	 $("select[name=state]").val('');
    	 $("select[name=city]").val('');
    	 $("select[name=NoticePeriod]").val('');
    	 $("select[name=WorkExperience]").val('');
    	 $("input[name=ExpectedCTC]").val('');
    	 $("input[name=CurrentCTC]").val('');
    	 $("input[name=Education]").val('');
	 }
	
	 getAllSubmissionCandidateCountries();
	 getCandidateStates();
	 getCandidateCities();
	 
	 setRateTypesByLocation();
     
     dobDatePicker();
    
     $('.chosen-select').each(function() {
			var $this = $(this);
			$this.next().css({'width': '100%'});
	  })
}
</script>


<script>
function getCandidateDetails(){
	
     var candidateId = '${SourceLeadWeb_candidateId}'; 
     var requirementId = $("#requirementId").val();
 	 var orgLocationId ='${SourceLeadWeb_orgLocationId}';
 	 
     $.ajax({
	    	type: "POST",
	        url: sourceleadUrl+"/getCandidateDetails?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId+"&requirementId="+requirementId+"&orgLocationId="+orgLocationId,		   
	        success: function(data) {
	        	if (data.response === "FAILED") {
		    		showError(data.responseMessage);
		    	} else if (data.response === "AWKWARD") {
		    		location.href = "/errorPage";
		    	} else if (data.response === "SUCCESS") {
		    		var json = data.object;
		        	 $("input[name=FirstName]").val(json['firstName']);
		        	 $("input[name=EmailId]").val(json['email']);
		        	 $("input[name=EmailId]").attr('readonly', 'readonly');
		        	 
		        	 if(json['lastName'] != null && json['lastName'] != "" && json['lastName'] != 'undefined'){
		        		 $("input[name=LastName]").val(json['lastName']);
		        	 }else{
		        		 $("input[name=LastName]").val(''); 
		        	 }
		        	 
		        	 if(json['mobilePhone'] != null && json['mobilePhone'] != "" && json['mobilePhone'] != 'undefined'){
		        		 $("input[name=PhoneNumber]").val(json['mobilePhone']);
		        	 }else{
		        		 $("input[name=PhoneNumber]").val(''); 
		        	 }
		        	 
		        	 if(json['alternateMobilePhone'] != null && json['alternateMobilePhone'] != "" && json['alternateMobilePhone'] != 'undefined'){
		        		 $("input[name=AlternateNumber]").val(json['alternateMobilePhone']);
		        	 }else{
		        		 $("input[name=AlternateNumber]").val(''); 
		        	 }
		        	 
		        	 
		        	 if(json['designation'] != null && json['designation'] != "" && json['designation'] != 'undefined'){
		        		 $("input[name=Designation]").val(json['designation']);
		        	 }else{
		        		 $("input[name=Designation]").val(''); 
		        	 }
		        	 
		        	 if(json['candidateEdu'] != null && json['candidateEdu'] != "" && json['candidateEdu'] != 'undefined'){
		        		 $("input[name=Education]").val(json['candidateEdu']);
		        	 }else{
		        		 $("input[name=Education]").val(''); 
		        	 }
		        	 
		        	 if(json['country'] != null && json['country'] != "" && json['country'] != 'undefined'){
		        		 $("#submissionCountry").val(json['country']);
		        	 }else{
		        		 $("#submissionCountry").val('');
		        	 }
		        	 
		        	 if(json['state'] != null && json['state'] != "" && json['state'] != 'undefined'){
		        		 $("#submissionState").val(json['state']);
		        	 }else{
		        		 $("#submissionState").val('');
		        	 }
		        	 
		        	 if(json['city'] != null && json['city'] != "" && json['city'] != 'undefined'){
		        		 $("#submissionCity").val(json['city']);
		        	 }else{
		        		 $("#submissionCity").val('');
		        	 }
		        
		        	 if(json['location'] != null && json['location'] != "" && json['location'] != 'undefined'){
		        		 $("#submissionLocation").val(json['location']);
		        	 }
	
		        	 if(json['zipCode'] != null && json['zipCode'] != "" && json['zipCode'] != 'undefined'){
		        		 $("#submissionZipCode").val(json['zipCode']);
		        	 }else{
		        		 $("#submissionZipCode").val('');
		        	 }
		        	 
		        	 if(json['noticePeriod'] != null && json['noticePeriod'] != "" && json['noticePeriod'] != 'undefined'){
		        		 $("select[name=NoticePeriod]").val(json['noticePeriod']);
		        	 }else{
		        		 $("select[name=NoticePeriod]").val('');
		        	 }
		        	 
		        	 if(json['experienceYears'] != null && json['experienceYears'] != "" && json['experienceYears'] != 'undefined'){
		        		 $("select[name=WorkExperience]").val(json['experienceYears']);
		        		 if(json['experienceYears'] == "25+"){
		        			 $("#monthExp").attr("disabled", "disabled");
		        		 }else{
		        			 $("#monthExp").removeAttr("disabled");
		        		 }
		        	 }else{
		        		 $("select[name=WorkExperience]").val(0);
		        	 }
		        	 
		        	 if(json['experienceMonths'] != null && json['experienceMonths'] != "" && json['experienceMonths'] != 'undefined'){
		        		 $("select[name=monthExp]").val(json['experienceMonths']);
		        	 }else{
		        		 $("select[name=monthExp]").val(0);
		        	 }
		        	 
		        	 if(json['expectedRate'] != null && json['expectedRate'] != "" && json['expectedRate'] != 'undefined'){
		        		 $("input[name=ExpectedCTC]").val(json['expectedRate']);
		        	 }else{
		        		 $("input[name=ExpectedCTC]").val(''); 
		        	 }
		        	 
		        	 
		        	 if(json['rateType'] != null && json['rateType'] != "" && json['rateType'] != 'undefined'){
		        		 $("select[name=expectedCTCRatePer]").val(json['rateType']);
		        		 validateExpectedCTCRatePer();
		        	 }else{
		        		 $("select[name=expectedCTCRatePer]").val('');
		        		 validateExpectedCTCRatePer();
		        	 }
		        	 
		        	 if(json['expectedRateCurrencey'] != null && json['expectedRateCurrencey'] != "" && json['expectedRateCurrencey'] != 'undefined'){
		        		 $("select[name=expectedCTCRateType]").val(json['expectedRateCurrencey']);
		        	 }
		        	 
		        	 if(json['currentRate'] != null && json['currentRate'] != "" && json['currentRate'] != 'undefined'){
		        		 $("input[name=CurrentCTC]").val(json['currentRate']);
		        		 validateCurrentCTC();
		        	 }else{
		        		 $("input[name=CurrentCTC]").val(''); 
		        		 validateCurrentCTC();
		        	 }
		        	 
		        	 if(json['currentRateType'] != null && json['currentRateType'] != "" && json['currentRateType'] != 'undefined'){
		        		 $("select[name=currentCTCRatePer]").val(json['currentRateType']);
		        		 validateExpectedCTC();
		        		 validateCurrentCTCRatePer();
		        	 }else{
		        		 $("select[name=currentCTCRatePer]").val(''); 
		        		 validateExpectedCTC();
		        		 validateCurrentCTCRatePer();
		        	 }
		        	 
		        	 if(json['currentRateCurrency'] != null && json['currentRateCurrency'] != "" && json['currentRateCurrency'] != 'undefined'){
		        		 $("select[name=currentCTCRateType]").val(json['currentRateCurrency']);
		        	 }
		        }else if (data.indexOf('Sign In') !== -1) {
		    		location.replace(sourceleadUrl);
		    	} else {
		    		showError("Operation failed, please try again");
		    	} 
	        },
	    });
     
     getAllSubmissionCandidateCountries();
	 getCandidateStates();
	 getCandidateCities();
	 
	 setRateTypesByLocation();
     
     dobDatePicker();
    
     $('.chosen-select').each(function() {
			var $this = $(this);
			$this.next().css({'width': '100%'});
	  })
}
</script>


<script>
function getExpYears(){
	var expYears = $("select[name=WorkExperience]").val();
	 if(expYears == "25+"){
		 $("#monthExp").attr("disabled", "disabled");
	 }else{
		 $("#monthExp").removeAttr("disabled");
	 }
}
</script>

 <script>
 function validateCurrentCTC(){
	 var currentCTC = $("input[name=CurrentCTC]").val();
	 if(currentCTC){
		 $("#currentCTCRatePer").removeAttr("data-validation-optional");
		 $("#currentCTCRatePer").attr("data-validation", "required");
	 }else{
		 $("#currentCTCRatePer").removeAttr("data-validation");
		 $("#currentCTCRatePer").attr("data-validation-optional", "true");
	 }
 }
 </script>
 
  <script>
 function validateExpectedCTC(){
	 var expectedCTC = $("input[name=ExpectedCTC]").val();
	 if(expectedCTC){
		 $("#expectedCTCRatePer").removeAttr("data-validation-optional");
		 $("#expectedCTCRatePer").attr("data-validation", "required");
	 }else{
		 $("#expectedCTCRatePer").removeAttr("data-validation");
		 $("#expectedCTCRatePer").attr("data-validation-optional", "true");
	 }
 }
 </script>
 
 <script>
 function validateExpectedCTCRatePer(){
	 var expectedCTCRatePer = document.getElementById("expectedCTCRatePer").value;
	 if(expectedCTCRatePer){
		 $("input[name=ExpectedCTC]").removeAttr("data-validation-optional");
		 $("input[name=ExpectedCTC]").attr("data-validation", "required validate_Space validate_float validate_length length1-8");
	 }else{
		 $("input[name=ExpectedCTC]").removeAttr("data-validation");
		 $("input[name=ExpectedCTC]").attr("data-validation-optional", "true");
		 $("input[name=ExpectedCTC]").attr("data-validation", "validate_Space validate_float validate_length length1-8");
	 }
 }
 </script>
 
 <script>
 function validateCurrentCTCRatePer(){
	 var currentCTCRatePer = document.getElementById("currentCTCRatePer").value;
	 if(currentCTCRatePer){
		 $("input[name=CurrentCTC]").removeAttr("data-validation-optional");
		 $("input[name=CurrentCTC]").attr("data-validation", "required validate_Space validate_float validate_length length1-8");
	 }else{
		 $("input[name=CurrentCTC]").removeAttr("data-validation");
		 $("input[name=CurrentCTC]").attr("data-validation-optional", "true");
		 $("input[name=CurrentCTC]").attr("data-validation", "validate_Space validate_float validate_length length1-8");
	 }
 }
 </script>
 
<script type="text/javascript">
function getAllSubmissionCandidateCountries(){
	$.ajax({
    	type: "POST",
    	dataType: "json",
    	 url: '${sourceleadUrl}'+"/countryAutoComplete?${_csrf.parameterName}=${_csrf.token}",
        success: function(data) {
        	var json = JSON.parse(data);
        	$('#submissionCountry').empty();
        	$('#submissionCountry').append('<option value="">--Select Country--</option>');
            for(var i=0;i<json.length;i++){
            	if(json[i] == '${SourceLeadWeb_user.country}'){
            		$('#submissionCountry').append('<option value="'+json[i]+'" selected>'+json[i]+'</option>');
            	}
            	else{
            		$('#submissionCountry').append('<option value="'+json[i]+'">'+json[i]+'</option>');
            	}
            	
            }
            $('#submissionCountry').trigger("chosen:updated");
            getCandidateStates();
        },
        error: function(e){
        }
    }); 
} 
</script>

<script>
function getCandidateStates(stateName){
	
	var countryName = $("#submissionCountry").val();
	
	if(countryName != null && countryName != "" && countryName !="undefined"){
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: '${sourceleadUrl}'+"/getStatesByCountry?${_csrf.parameterName}=${_csrf.token}&countryName="+countryName, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#submissionState').empty();
	        	$('#submissionState').append('<option value="">--Select State--</option>');
	            for(var i=0;i<json.length;i++){
	            	var selected = '';
	            	if(stateName != null && stateName != "" && stateName !="undefined" && json[i] == stateName){
	            		selected = 'selected';
	            	}
	            	$('#submissionState').append('<option value="'+json[i]+'" '+selected+'>'+json[i]+'</option>');
	            }
	            $('#submissionState').trigger("chosen:updated");
	            
	            $('#submissionCity').empty();
	            $('#submissionCity').trigger("chosen:updated");
	        },
	        error: function(e){
	        }
	    });
	}else{
		$('#submissionState').empty();
    	$('#submissionState').append('<option value="">--Select State--</option>');
    	$('#submissionState').trigger("chosen:updated");
    	$('#submissionCity').empty();
    	$('#submissionCity').append('<option value="">--Select City--</option>');
        $('#submissionCity').trigger("chosen:updated");
	}
}
</script> 


<script>
function getCandidateCities(stateName,city){
	
	var countryName = $("#submissionCountry").val();
	
	if(stateName == null || stateName == "" || stateName =="undefined"){
		stateName = document.getElementById("submissionState").value;
	}
	
	if(countryName != null && countryName != "" && countryName !="undefined" && stateName != null && stateName != "" && stateName !="undefined" ){
		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: '${sourceleadUrl}'+"/getCitiesByStateAndCountry?${_csrf.parameterName}=${_csrf.token}&countryName="+countryName+"&stateName="+stateName, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#submissionCity').empty();
	        	$('#submissionCity').append('<option value="">--Select City--</option>');
	            for(var i=0;i<json.length;i++){
	            	var selected = '';
	            	if(city != null && city != "" && city !="undefined" && json[i] == city){
	            		selected = 'selected';
	            	}
	            	$('#submissionCity').append('<option value="'+json[i]+'" '+selected+'>'+json[i]+'</option>');
	            }
	            $('#submissionCity').trigger("chosen:updated");
	            $("#loading").removeAttr("style"); 
	        },
	        error: function(e){
	        }
	    });
	}else{
		$('#submissionCity').empty();
    	$('#submissionCity').append('<option value="">--Select City--</option>');
    	$('#submissionCity').trigger("chosen:updated");
	}
}
</script>


<script>	
   
    $(".side_mask").click(function(){
		$(".new_side_wrap").removeClass("new_side_wrap_active");
		$(".side_mask").fadeOut(200);
		$("body").css('overflow-y', 'auto');
	});	
	
// Refer job	
    $(".refer").click(function(){
		$(this).parent(".openPostBtns").find(".referWrap").toggleClass("referWrapActive");
	});
    $(".canBtn").click(function(){
		$(".referWrap").removeClass("referWrapActive");
	});		
	
</script> 


<script type="text/javascript">
$('body').on('submit', '#applyToJob_form', function(e){
	  if ($(this).validate(false, validationSettings)){
      e.preventDefault();
      var frm = $('#applyToJob_form').serializeFormJSON();
      var requirementId = $("#requirementId").val();
      var frm = $('#applyToJob_form').serializeFormJSON();
      var con1 = JSON.stringify(frm);
      con1= con1.replace(/[{}]/g, "");
      var jsonData = encodeURIComponent(con1);
      var form = $('#applyToJob_form')[0];
      var formData = new FormData(form); 
      //Loop through fields and save field data to array
      var fields = [];
      $('.sjfb-field').each(function() { 
      	  var $this = $(this); 
      	  var divId = $(this).attr('id');  
      	  var fieldLabel = $this.find('label:first').text().replace("*","");
      	  var inputValue = $this.find('input[type="radio"]:checked').val();
      	  
      	 if(inputValue == null || inputValue == '' || inputValue == 'undefined'){
    		   inputValue = $this.find('input[type="checkbox"]:checked').val();
    		   if(inputValue != null){
    			   var selected = [];
               	$this.find('input[type="checkbox"]:checked').each(function() {
      		        selected.push($(this).val()); 
      		       });
               	inputValue = selected.toString()
    		   }
    		   
      	 }
      	 
      	
      	  if(inputValue == null || inputValue == '' || inputValue == 'undefined'){
      		  inputValue = $this.find('input').val();
      	  }
      	  
      	   
    	  
     	      if(inputValue == null || inputValue == '' || inputValue == 'undefined'){
     	    	 inputValue = $this.find('select').val();
     	      }
     	      
     	     if(inputValue == null || inputValue == '' || inputValue == 'undefined'){
     	    	 inputValue = $this.find('textarea').val();
     	      }
     	      
     	    
     	     
     	      if(inputValue == null || inputValue == '' || inputValue == 'undefined'){
     	    	inputValue = '';
     	      }
     	      
     	      
     	      
	       	  var name = $this.find('input[type="radio"]:checked').attr('name');
	       	  var id = $this.find('input[type="radio"]:checked').attr('id');
	     	  if(name == null || name == '' || name == 'undefined'){
	     		  name = $this.find('input').attr('name');
	     		  id = $this.find('input').attr('id');
	     	  }
	     	  if(name == null || name == '' || name == 'undefined'){
	     		 name = $this.find('select').attr('name');
	     		 id = $this.find('select').attr('id');
     	      }
	     	  
	     	  if(name == "WorkExperience"){
	     		 var monthExp = document.getElementById("monthExp").value; 
	     		  if(null != inputValue){
	     			  if(inputValue == "25+"){
	     				 inputValue = inputValue+" Years";
	     			  }else{
	     				 inputValue = inputValue+" Years"+" "+monthExp+" Months" ;
	     			  }
	     			 
	     		  }
	     		  
	     		  if(null == inputValue && monthExp != '0'){
	     			 inputValue = monthExp+" Months" ;
	     		  }
	     	  }
	     	  
	     	  
	     	 if(name == "CurrentCTC"){
	     		 inputValue = $this.find('input').val();
	     		 if(inputValue){
	     			 var currentCTCRatePer = document.getElementById("currentCTCRatePer").value; 
	     			 var currentCTCRateType = document.getElementById("currentCTCRateType").value; 
	     			 inputValue = currentCTCRateType+" "+inputValue+" /"+" "+currentCTCRatePer ;
	     		  }
	     	  }
	     	 
	     	if(name == "ExpectedCTC"){
	     		 inputValue = $this.find('input').val();
	     		  if(inputValue){
	     			 var expectedCTCRatePer = document.getElementById("expectedCTCRatePer").value; 
	     			 var expectedCTCRateType = document.getElementById("expectedCTCRateType").value; 
	     			 inputValue = expectedCTCRateType+" "+inputValue+" /"+" "+expectedCTCRatePer ;
	     		  }
	     	  }
	     	  
	     	  
      	  fields.push({
                label: fieldLabel,
                id :id,
                name :name,
                value: inputValue,
            });
       });
      var con = JSON.stringify(fields);
      
      con = encodeURIComponent(con);
     
      var requirementUUID = '${requirementUUID}';
		
       $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
      
  	    $.ajax({
	    	type: "POST",
	    	data: formData,
 	        contentType : 'application/json; charset=utf-8',
	        processData: false,
	        contentType:false,
 	        dataType : 'json',
 	    	url : sourceleadUrl+"/applyToJobFromCrowdSourcing?${_csrf.parameterName}=${_csrf.token}&jsonData="+jsonData+"&con="+con,
	        success: function(result) {
	        	
	        	
	        	if('${resumesCount}' > 0){
	        		$("#loading").removeAttr("style");
		        	$(".new_side_wrap").removeClass("new_side_wrap_active");
		    		$(".side_mask").fadeOut(200);
		    		$("body").css('overflow-y', 'auto');
		    		
		        	
		    		if(requirementUUID == '' || requirementUUID == null || requirementUUID == 'undefined' ){
		    			document.getElementById('requirementType').value = 'AppliedJobs';
			    		document.getElementById('appliedjobs').checked = true;
			    		document.getElementById('appliedActive').className = 'activeBackground'
			    		//document.getElementById("crowdActive").removeAttribute("class")
			       		document.getElementById("openActive").removeAttribute("class");
			        	
			       		requirementsList(0);
		    		}else{
		    			location.replace(sourceleadUrl+"/requirementProfileDetails?requirementUUID="+requirementUUID); 
		    		}
		    		
	        	}else{
	        		location.replace(sourceleadUrl+"/professionalRequirements"); 
	        	}
	        },
	    });  
      return true; 
    }
    else{
    	return false;	
       }
  });

    
   	$('body').on('blur', '#applyToJob_form', function() {
		$("#applyToJob_form").showHelpOnFocus();
		$("#applyToJob_form").validateOnBlur(false, validationSettings);
	});
  
  </script>
  
  
  
  <script type="text/javascript">
$('body').on('submit', '#submitCandidateToJob_form', function(e){
	  if ($(this).validate(false, validationSettings)){
      e.preventDefault();
      var frm = $('#submitCandidateToJob_form').serializeFormJSON();
      var requirementId = $("#requirementId").val();
      var frm = $('#submitCandidateToJob_form').serializeFormJSON();
      var con1 = JSON.stringify(frm);
      con1= con1.replace(/[{}]/g, "");
      var jsonData = encodeURIComponent(con1);
      var form = $('#submitCandidateToJob_form')[0];
      var formData = new FormData(form); 
      //Loop through fields and save field data to array
      var fields = [];
      $('.sjfb-field').each(function() { 
      	  var $this = $(this); 
      	  var divId = $(this).attr('id');  
      	  var fieldLabel = $this.find('label:first').text().replace("*","");
      	  var inputValue = $this.find('input[type="radio"]:checked').val();
      	  
      	 var inputtype = $(this).find('input').attr('type');
	   	  if(inputtype == null || inputtype == '' || inputtype == 'undefined'){
	   		  if($(this).find('select').val() != null){
	   			  inputtype = 'select';
	   		  }else{
	   			  inputtype = 'textarea';
	   		  }
	   	  }
      	  
      	 if(inputValue == null || inputValue == '' || inputValue == 'undefined'){
    		   inputValue = $this.find('input[type="checkbox"]:checked').val();
    		   if(inputValue != null){
    			   var selected = [];
               	$this.find('input[type="checkbox"]:checked').each(function() {
      		        selected.push($(this).val()); 
      		       });
               	inputValue = selected.toString()
    		   }
    		   
      	 }
      	 
      	
      	  if(inputValue == null || inputValue == '' || inputValue == 'undefined'){
      		  inputValue = $this.find('input').val();
      	  }
      	  
      	   
    	  
     	      if(inputValue == null || inputValue == '' || inputValue == 'undefined'){
     	    	 inputValue = $this.find('select').val();
     	      }
     	      
     	     if(inputValue == null || inputValue == '' || inputValue == 'undefined'){
     	    	 inputValue = $this.find('textarea').val();
     	      }
     	      
     	    
     	     
     	      if(inputValue == null || inputValue == '' || inputValue == 'undefined'){
     	    	inputValue = '';
     	      }
     	      
     	      
     	      
	       	  var name = $this.find('input[type="radio"]:checked').attr('name');
	       	  var id = $this.find('input[type="radio"]:checked').attr('id');
	     	  if(name == null || name == '' || name == 'undefined'){
	     		  name = $this.find('input').attr('name');
	     		  id = $this.find('input').attr('id');
	     	  }
	     	  
	     	  if(name == null || name == '' || name == 'undefined'){
	     		 name = $this.find('select').attr('name');
	     		 id = $this.find('select').attr('id');
     	      }
	     	  
	     	  if(name == null || name == '' || name == 'undefined'){
	     		 name = $this.find('textarea').attr('name');
     	      }
	     	  
	     	  if(name == "WorkExperience"){
	     		 var monthExp = document.getElementById("monthExp").value; 
	     		  if(null != inputValue){
	     			  if(inputValue == "25+"){
	     				 inputValue = inputValue+" Years";
	     			  }else{
	     				 inputValue = inputValue+" Years"+" "+monthExp+" Months" ;
	     			  }
	     			 
	     		  }
	     		  
	     		  if(null == inputValue && monthExp != '0'){
	     			 inputValue = monthExp+" Months" ;
	     		  }
	     	  }
	     	  
	     	  
	     	 if(name == "CurrentCTC"){
	     		 inputValue = $this.find('input').val();
	     		 if(inputValue){
	     			 var currentCTCRatePer = document.getElementById("currentCTCRatePer").value; 
	     			 var currentCTCRateType = document.getElementById("currentCTCRateType").value; 
	     			 inputValue = currentCTCRateType+" "+inputValue+" /"+" "+currentCTCRatePer ;
	     		  }
	     	  }
	     	 
	     	if(name == "ExpectedCTC"){
	     		 inputValue = $this.find('input').val();
	     		  if(inputValue){
	     			 var expectedCTCRatePer = document.getElementById("expectedCTCRatePer").value; 
	     			 var expectedCTCRateType = document.getElementById("expectedCTCRateType").value; 
	     			 inputValue = expectedCTCRateType+" "+inputValue+" /"+" "+expectedCTCRatePer ;
	     		  }
	     	  }
	     	  
	     	  
      	  fields.push({
                label: fieldLabel,
                type : inputtype,
                id :id,
                name :name,
                value: inputValue,
            });
       });
      var con = JSON.stringify(fields);
      
      con = encodeURIComponent(con);
      
        $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
      
  	     $.ajax({
	    	type: "POST",
	    	data: formData,
 	        contentType : 'application/json; charset=utf-8',
 	        enctype: 'multipart/form-data',
	        processData: false,
	        contentType:false,
 	        dataType : 'json',
 	    	url : sourceleadUrl+"/saveAddApplicant?${_csrf.parameterName}=${_csrf.token}&jsonData="+jsonData+"&con="+con,
	        success: function(result) {
	        	
				$("#loading").removeAttr("style");
				if(result.response === "SESSION_TIMEDOUT") {
					location.replace(sourceleadUrl);
				} else if (result.response === "AWKWARD") {
					location.href = "/errorPage";
				} else if (result.response === "SUCCESS") {
					$(".new_side_wrap").removeClass("new_side_wrap_active");
		    		$(".side_mask").fadeOut(200);
		    		$("body").css('overflow-y', 'auto');
				} else if (result.indexOf('Sign In') !== -1) {
					location.replace(sourceleadUrl);
				} else {
					showError("Operation failed, please try again");
				}
	        },
	    });  
      return true; 
    }
    else{
    	return false;	
       }
  });

    
   	$('body').on('blur', '#submitCandidateToJob_form', function() {
		$("#submitCandidateToJob_form").showHelpOnFocus();
		$("#submitCandidateToJob_form").validateOnBlur(false, validationSettings);
	});
  
  </script>
  
  <script type="text/javascript">
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };
</script> 

<script>
  function dobDatePicker() {
	 /*  $('#DOB').datepicker({
	      dateFormat: 'yy-mm-dd',
	      changeMonth: true,
	  	  changeYear: true, 
	  }); */
	  
  }
</script>



<script type="text/javascript">
function setRateTypesByLocation(){
	var country = '${SourceLeadWeb_country}';
	if(country == 'India'){
		$('#currentCTCRateType').val('INR');
		$('#expectedCTCRateType').val('INR');
	}else if(country == 'Australia'){
		$('#currentCTCRateType').val('AUD');
		$('#expectedCTCRateType').val('AUD');
	}else if(country == 'United States'){
		$('#currentCTCRateType').val('USD');
		$('#expectedCTCRateType').val('USD');
	}else if(country == 'Singapore'){
		$('#currentCTCRateType').val('SGD');
		$('#expectedCTCRateType').val('SGD');
	}else if(country == 'United Kingdom'){
		$('#currentCTCRateType').val('GBP');
		$('#expectedCTCRateType').val('GBP');
	}else if(country == 'France'){
		$('#currentCTCRateType').val('EUR');
		$('#expectedCTCRateType').val('EUR');
	}
}
</script>


<script>
 $(document.body).on('change', '#professionaluploadBtn' ,function(){
	 $('#uploadFile').val(this.value.split( '\\' ).pop());
	 $("#uploadFile").focus();
 	 if(validate_fileupload(this.value)){
 		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		var form = $('#applyToJob_form')[0];
  		var formData = new FormData(form); 
		   $.ajax({
				    type: "POST",
			    	data: formData,
		   	        contentType : 'application/json; charset=utf-8',
		   	        enctype: 'multipart/form-data',
			        processData: false,
			        contentType:false,
		   	        dataType : 'json',
					url : sourceleadUrl+"/parseResume?${_csrf.parameterName}=${_csrf.token}",
					success : function(response) {
						if (response.response === "AWKWARD") {
						    location.href = "/errorPage";
						} else if (response.response === "SUCCESS") {
							if(response.object !== null) {
								var json = response.object;
								var name = json.name;
								var firstName = '';
								var lastName = '';
								if(name != null && name != 'undefined' && name != ''){
									 firstName = name.split(".")[0]; 
									 lastName = name.split(".")[1]; 
									 if(firstName == null || firstName ==  'undefined' || firstName == '' ||
											 lastName ==  null || lastName ==  'undefined'|| lastName ==  ''){
										 firstName = name;
									 }
								}
								
								
								 $("input[name=FirstName]").val('${SourceLeadWeb_candidate.firstName}');
					        	 $("input[name=LastName]").val('${SourceLeadWeb_candidate.lastName}');
					        	 $("input[name=EmailId]").val('${SourceLeadWeb_candidate.email}');
					        	 $("input[name=PhoneNumber]").val('${SourceLeadWeb_user.mobilePhone}');
					        	 
					        	 $("input[name=FirstName]").attr("readonly", "readonly");
					        	 $("input[name=LastName]").attr("readonly", "readonly");
					        	 $("input[name=EmailId]").attr("readonly", "readonly");
					        	 
					        	 $("#candidateSkills").empty();
					        	 var internalOutput = "";
		 						 <c:forEach var="skill" items="${skillsList}">
		 						    internalOutput = internalOutput+"<option value='${skill.name}'>${skill.name}</option>";
		 	                     </c:forEach>
		 						var skills = json.skills;
		 						if(skills != null && skills != 'undefined' && skills != ''){
		 							var str_array = skills.split(',');
		 							for(var i = 0; i < str_array.length; i++) {
		 							   str_array[i] = str_array[i].replace(/^\s*/, "").replace(/\s*$/, "");
		 							   internalOutput = internalOutput+"<option value='" +str_array[i]+ "' selected>"+str_array[i]+"</option>";
		 							}
		 						}
		 						$("#candidateSkills").append(internalOutput);
		 		        		$("#candidateSkills").trigger("chosen:updated");
		  						
					        	 
								 validateCandidateEmail();
								 getAllSubmissionCandidateCountries();
								 setRateTypesByLocation();
								 dobDatePicker();
							} else {
								showError("Resume Parsing Failed");
							}
							 $("#loading").removeAttr("style");
						} else if (response.indexOf('Sign In') !== -1) {
							location.replace(sourceleadUrl);
						} else {
						    showError("Operation failed, please try again");
						}
					},
				});
 	 }
 });

</script> 

<script type="text/javascript">
function init(){
	getAllSubmissionCandidateCountries();
	initLocationAuto('submissionLocation','submissionCountry','submissionState','submissionCity','submissionZipCode','','','');
	var country = '${SourceLeadWeb_country}';
	$("#submissionCountry").val(country)
	      
	     /*   $("#submissionCountry").on('change', function (e) {
	    	   getCandidateStates();
	      	   getCandidateCities();
	       });
	       $("#submissionState").on('change', function (e) {
	    	   getCandidateCities();
	       });
	       */
	       $('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});
		  })
		  
    setRateTypesByLocation();
}
</script>
 <script>
 function changeRateLabels(){
	 
	 var currentRate = document.getElementById("currentRate").textContent;
	 var currentstringLength = currentRate.length; 
	 var currentRatelastChar = currentRate.charAt(currentstringLength - 1);
	 
	 if(currentRatelastChar == "*"){
		 $("#currentRate").html("Current Rate *"); 
	 }else{
		 $("#currentRate").html("Current Rate");
	 }
	 
	 var expectedRate = document.getElementById("expectedRate").textContent;
	 var expectedstringLength = expectedRate.length; 
	 var expectedRatelastChar = expectedRate.charAt(expectedstringLength - 1);
	 
	 if(expectedRatelastChar == "*"){
		 $("#expectedRate").html("Expected Rate *"); 
	 }else{
		 $("#expectedRate").html("Expected Rate");
	 }
	 
 }
 </script>


<script>
function parseProfessionalResume(){
	 document.getElementById("browseDiv").style.display="";
	 $('#uploadFile').removeAttr('data-validation-optional');
	 $('#uploadFile').attr("data-validation","required validatedoc "); 
	 
	 document.getElementById("skillsDiv").style.display="";
	 $("#candidateSkills").val('').trigger("chosen:updated");
	 document.getElementById("primarySkillDiv").style.display="";
	 $("#primarySkill").val('');
	 
	 $('#importResume').removeAttr('data-validation');
	 $('#importResume').attr("data-validation-optional","true"); 
	 $('#importResume').find('p.jquery_form_error_message').remove(); 
}
</script>
<script>
function proposeTime(interviewId,submissionId){
	$(".proposeTimePop").toggleClass("proposeTimePopActive");
	$(".reSchPop").removeClass("reSchPopActive");
	
	var result ='';
	var parentRequirement = '${parentRequirement}';
	result = result	+ ' <form id="proposeTime_form"  name="proposeTime_form" method="post">';
	result = result + '			  <div class="reSchPopInn">';
	result = result + '			   <input type="hidden" id="requirementInterviewId" name="requirementInterviewId" value='+interviewId+' >';
	result = result + '				  <h3>Propose New Time</h3>';
	result = result + '				  <div class="reSchPopCta">';	
	result = result + '         <input class="maxRowNum" type="hidden" name="proposeTimeMaxRowNumber'+interviewId+'"  id="proposeTimeMaxRowNumber'+interviewId+'" value="1" />';
	result = result + '	         <input type="hidden" class="newMaxRowNum" name="proposeNewTimeRowsNum'+interviewId+'"  id="proposeNewTimeRowsNum'+interviewId+'" value="1" />';
	result = result + '			<table width="100%" class="table table-responsive table-bordered table-hover" id="proposeTimeTbl'+interviewId+'">';
	result = result + '			<tbody>';
	result = result + '				<tr id="divProposeTimeTsRow0">';
	result = result + '					<td>';
	result = result + '					  <div id="errMsg0">';
	result = result + '					  		<input type="text" class="form-control calendar input-sm" id="interviewDate0" name="interviewDate0" value="" placeholder="Interview Date" readonly data-validation="required" field-name="Interview Date">';
	result = result + '					  </div>';
	result = result + '					</td>';
	result = result + '					<td>';
	result = result + '						<div>';
	result = result + '							  <input type="text" class="form-control timeicon timepicker input-sm" id="time0" name="time0" placeholder="Select Time" data-validation="required" field-name="Time">';
	result = result + '						</div>		';								
	result = result + '					</td>';
	result = result + '					<td>';
	result = result + '						<div>';
	result = result + '					  		<button type="button" class="btn btn-primary btn-sm" onClick="addProposeTime('+interviewId+');"><i class="fa fa-plus"></i></button>';
	result = result + '				  		</div>';
	result = result + '					</td>';
	result = result + '				</tr>';
	result = result + '			</tbody>';
	result = result + '			</table>';
						
	
	result = result + '			<div class="clearfix mt"> ';
	result = result + '				<div class="pull-right"> ';
	result = result + '				  <button type="button" class="btn btn-sm btn-primary" onclick="saveProposeTime('+submissionId+')">Propose Time</button> ';
	result = result + '				  <button type="button" class="btn btn-sm btn-danger" onclick="timePopUp();">Cancel</button> ';
	result = result + '				</div> ';
	result = result + '			  </div> ';
	
	result = result + '				  </div>   ';
	result = result + '			  </div> ';
	result = result	+ ' </form>';

	$("#proposeTimediv"+interviewId+"").empty();
    $("#proposeTimediv"+interviewId+"").append(result);

    
	$("#time0").timepicker({
		'showDuration': true,
		'disableTextInput': true
	})
	
$('#interviewDate0').datepicker({
	dateFormat : 'dd-mm-yy',
	changeMonth: true,
	changeYear: true, 
	minDate:0,
	onSelect : function(selected) {
			errInvoiceDate("0");
	}
});
	
}
</script>
<script>
function errInvoiceDate(rowId){
	$('#errMsg'+rowId+'').find('p.jquery_form_error_message').remove(); 
}
</script>
<script>
function timePopUp(){
	$(".proposeTimePop").removeClass("proposeTimePopActive");
}
</script>

<script>
  function addProposeTime(interviewId){
	  var checkMaxTsRowNum = $("#proposeTimeMaxRowNumber"+interviewId+"").val();
		var tbl = document.getElementById("proposeTimeTbl"+interviewId+"");
		var maxRowNum = parseInt($("input[name = 'proposeTimeMaxRowNumber"+interviewId+"']").val());
		var row = tbl.insertRow(maxRowNum);
		row.setAttribute("id", "divProposeTimeRow" + maxRowNum +interviewId);
		
		var rowNum = parseInt($("input[name = 'proposeNewTimeRowsNum"+interviewId+"']").val());
		if(rowNum >= 5){
			var result = "";
     		result = result +"	<div class='project_inn_mask'></div>";
     		result = result +"	   <div id='invoice_pop' class='remove_user_pop remove_user_pop_a'>";
     		result = result +"		<p>Only can Add 5 Rows</p>";
     		result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
     		result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='timeCancelPopUp();'><input class='u_can_color' type='button'  value='Cancel' onClick='timeCancelPopUp();'></b>";
     		result = result +"	</div>";
     		$("#popUp").append(result);
     		$("#invoice_pop").slideDown(200);
     		$(".project_inn_mask").fadeIn(200);
		}else{
			$("input[name = 'proposeTimeMaxRowNumber"+interviewId+"']").val(maxRowNum + 1);
			$("input[name = 'proposeNewTimeRowsNum"+interviewId+"']").val(rowNum + 1);
			var text = "";
			var cell0 = row.insertCell(0);
			text = text+'<div id="errMsg'+maxRowNum+'">';
			text = text+'  <input type="text" class="form-control calendar" id="interviewDate'+maxRowNum+'" name="interviewDate'+maxRowNum+'" value="" placeholder="Interview Date"  readonly data-validation="required" field-name="Invoice Date">';
			text = text+'</div>';
			cell0.innerHTML = text;
			
			$('#interviewDate'+maxRowNum+'').datepicker({
				dateFormat : 'dd-mm-yy',
				changeMonth: true,
				changeYear: true, 
				minDate:0,
				onSelect : function(selected) {
						errInvoiceDate(maxRowNum);
				}
			});
			
			var text = "";
		    var cell1 = row.insertCell(1);
			text = text+'<div>';
			text = text+'  <input type="text" class="form-control timeicon timepicker input-sm ui-timepicker-input" id="time'+maxRowNum+'" name="time'+maxRowNum+'" placeholder="Select Time" data-validation="required" field-name="Time">';
			text = text+'</div>	';
			cell1.innerHTML = text;
			
			var text = "";
		    var cell2 = row.insertCell(2);
			text = text+'<div>';
			text = text+'<button type="button" class="btn btn-danger btn-sm" id="remove"  onclick="removeRow('+maxRowNum+','+interviewId+')"><i class="fa fa-remove"></i></button>';
			text = text+'</div>';
			cell2.innerHTML = text;
		}
		
		  $("#time"+maxRowNum+"").timepicker({
				'showDuration': true,
				'disableTextInput': true
			}); 
		 
  }
  </script>
  <script>
  function removeRow(rowId,interviewId){
	  document.getElementById('divProposeTimeRow' + rowId+interviewId).style.display = 'none';
	  
	  var rowNum = parseInt($("input[name = 'proposeNewTimeRowsNum"+interviewId+"']").val());
	  $("input[name = 'proposeNewTimeRowsNum"+interviewId+"']").val(rowNum - 1);
		
		var r = document.getElementById('proposeTimeTbl'+interviewId+'').rows[rowId];
		var count = 2 * (r.cells.length);
		for (i = 0; i <= count; i++) {
			r.deleteCell(0);
		}
  }
  
  </script>
  
   <script>
function saveProposeTime(submissionId){
	if ($('#proposeTime_form').validate(false, validationSettings)){ 
		var frm = $('#proposeTime_form').serializeFormJSON();
		var con = JSON.stringify(frm);
		con = con.replace(/[{}]/g, ""); 
		//It will escape all the special characters
	    var jsonData = encodeURIComponent(con);
		$.ajax({
					type : "POST",
					url : sourceleadUrl+"/saveProposeTime?${_csrf.parameterName}=${_csrf.token}&jsonData="+jsonData+"&submissionId"+submissionId,
					success : function(result) {
						getInternalInterviewsList(submissionId);
					},
				});
		return true;
	 }else{ 
			return false;
	 } 
}	 
$('body').on('blur', '#proposeTime_form', function() {
	$("#proposeTime_form").showHelpOnFocus();
	$("#proposeTime_form").validateOnBlur(false, validationSettings);  
});
</script>

<script>
function viewProposeTime(interviewId,submissionId){
	$(".proposeTimePop").toggleClass("proposeTimePopActive");
	$(".reSchPop").removeClass("reSchPopActive");
	var locationId = '${SourceLeadWeb_orgLocationId}';
	$.ajax({
		type : "POST",
		url : sourceleadUrl+"/getProposeTime?${_csrf.parameterName}=${_csrf.token}&interviewId="+interviewId+"&locationId="+locationId,
		success : function(data) {
			var json = JSON.parse(data);
	        var result = "";
// 	        result = result + '<button type="button" class="" onClick="naidu(this);">&times;</button>';
			result = result	+ ' <form id="acceptProposeTime_form"  name="acceptProposeTime_form" method="post">';
			result = result + '			   <input type="hidden" id="requirementInterviewId" name="requirementInterviewId" value='+interviewId+' >';
	        result = result + '			  <div class="reSchPopInn">';
	        result = result + '				  <h3>Propose New Time</h3>';
    		result = result + '				  <div class="reSchPopCta">';	
    		result = result + '			<table width="100%" class="table table-responsive table-bordered	">';
    		result = result + '			<tbody>';
        	for (var i=0; i<json.length; i++){
        		var report = json[i];
        		
        		result = result + '				<tr>';
        		result = result + '					<td>';
        		result = result + '					  <div id="errMsg0">';
        		result = result + '					  		<input type="text" class="form-control calendar input-sm" id="proposeDate" name="proposeDate" value="'+report.proposeDateStr+'" readonly>';
        		result = result + '					  </div>';
        		result = result + '					</td>';
        		result = result + '					<td>';
        		result = result + '						<div>';
        		result = result + '							  <input type="text" class="form-control timeicon timepicker input-sm" id="proposeTime" name="proposeTime" value="'+report.proposeTime+'" readonly>';
        		result = result + '						</div>		';								
        		result = result + '					</td>';
        		result = result + '					<td>';
        		result = result + '						<div class="mt5">';
        		if(report.interviewTimeStatus == "Accepted"){
        			result = result +' <font color="orange" class="brder-int">Accepted</font>';
        		}else{
        			result = result + '                         <a href="javascript:void(0)"  onclick="acceptProposeTime(\''+report.id+'\',\''+report.proposeDateStr+'\',\''+report.proposeTime+'\',\''+submissionId+'\');"><i class="fa fa-check text-success"></i></a>';
        		}
        		
        		result = result + '				  		</div>';
        		result = result + '					</td>';
        		result = result + '				</tr>';
        		
        	}
        	result = result + '			</tbody>';
    		result = result + '			</table>';
    		result = result + '				  </div>   ';
    		result = result + '			  </div> ';
    		result = result	+ ' </form>';
        	$("#viewProposeTimediv"+interviewId+"").empty();
    	    $("#viewProposeTimediv"+interviewId+"").append(result);
		},
	});
}
</script>
<script>
function acceptProposeTime(id,proposeDate,proposeTime,submissionId){
	if ($('#acceptProposeTime_form').validate(false, validationSettings)){ 
		var frm = $('#acceptProposeTime_form').serializeFormJSON();
		var con = JSON.stringify(frm);
		con = con.replace(/[{}]/g, ""); 
		//It will escape all the special characters
	    var jsonData = encodeURIComponent(con);
		$.ajax({
					type : "POST",
					url : sourceleadUrl+"/acceptProposeTime?${_csrf.parameterName}=${_csrf.token}&jsonData="+jsonData+"&proposeTimeId="+id+"&proposeDate="+proposeDate+"&proposeTime="+proposeTime,
					success : function(result) {
						getInternalInterviewsList(submissionId);
					},
				});
		return true;
	 }else{ 
			return false;
	 } 
}

</script>
<script>
function timeCancelPopUp(){
	$("#invoice_pop").slideUp(200);
    $(".project_inn_mask").remove();
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