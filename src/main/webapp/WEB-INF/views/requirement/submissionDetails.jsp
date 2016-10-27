<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 
 <jsp:include page="employeeSubmissionDetails.jsp" />
 <jsp:include page="educationSubmissionDetails.jsp" />
 <jsp:include page="certificationSubmissionDetails.jsp" />
 <jsp:include page="submissionCandidateProfile.jsp" />
 <jsp:include page="submissionCandidateResume.jsp" />
 
 <style type="text/css">
 .brder-int {padding:2px 10px; margin-right:5px;}
 .brder-int-left {border-left:1px solid #c1c1c1; margin-left: -5px;}
 </style>
 
<script>
function getSubmissionDetailsSideBar(submissionId,candidateId){
	var sourceleadUrl = '${sourceleadUrl}';
	
	$.ajax({ 
	    	type: "POST",
	    	url: sourceleadUrl+"/getSubmissionDetails?${_csrf.parameterName}=${_csrf.token}&submissionId="+submissionId,
	    			
	    	  success: function(data) {
		       
	    	   var json = JSON.parse(data);
	    	   var skillsList = json.skillsList;
	           var skills = [];
	           skills = skillsList.split(",");
	    	   
	           var submissionDetails = "";
	    	   var details = eval(json.submissionTemplateJsonStringValue);
	    	   $.each(details, function( k, v ) {
	    		   var value = '';
	    		   if(v['value'] == null || v['value'] == '' || v['value'] == 'undefined'){
	    			   var value = 'NO DATA';
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
	    	   
	    	   var designation = '';
	    	   var experience = '';
	    	   
	    	   if(json.candidate.designation != null && json.candidate.designation != '' && json.candidate.designation !='undefined'){
	    		   designation = json.candidate.designation;
	    	   }
	    	   
	    	   if(json.candidate.experienceYears != null && json.candidate.experienceYears != '' && json.candidate.experienceYears !='undefined'){
	    		   experience = 'Experience -'+json.candidate.experienceYears+ 'Years';
	    	   }
	    	   
	    	   var result = "";
          	   result = result + ' <div class="req_hide">';
               result = result + '<div class="profile_details clearfix">';
               result = result + '<div class="col-md-6">';
               result = result + ' <div class="media">';
               result = result + '<div class="media-left image-parent">';
               result = result + '<div class="imageParent"><img onerror="predefineProfileImage(this);" src="<c:url value="/displayCandidateImage/'+json.candidate.id+'"/>" alt="candidate" title="Candidate"></div>';
               result = result + ' </div>';
               result = result + ' <div class="media-body candidate-details">';
               result = result + ' <h4 class="media-heading">'+json.candidate.firstName+' '+json.candidate.lastName+'</h4>';
               result = result + ' <ul class="list-unstyled">';
               result = result + ' <li><strong>'+designation+'</strong></li>';
               result = result + ' <li>'+experience+'</li>';
               result = result + ' </ul>';
               result = result + '</div>';
               result = result + '</div>';
               result = result + '<div class="other-detials">';
               result = result + '<div class="professinal-details">';
               result = result+'<ul class="list-inline list-unstyled">';
               if(json.skillsList != null && json.skillsList != '' && json.skillsList != 'undefined'){
	             for (i=0;i < skills.length;i++)
	             {
	            	 result = result+'<li>'+skills[i]+'</li>';
	             }
	            }
	           result = result+'</ul>';
               result = result + '</div>';
               result = result + '<div class="socialnetwork-details">';
               result = result + '<ul class="list-inline list-unstyled">';
               result = result + '<li class="bg-facebook"><a href="javascript:void(0)"> <i class="fa fa-facebook-square"></i></a></li>';
               result = result + '<li class="bg-twitter"><a href="javascript:void(0)"> <i class="fa fa-twitter-square"></i></a></li>';
               result = result + '<li class="bg-google "><a href="javascript:void(0)"> <i class="fa fa-google-plus-square"></i></a></li>';
               result = result + '<li class="bg-linked"><a href="javascript:void(0)"> <i class="fa fa-linkedin-square"></i></a></li>';
               result = result + ' <li class="bg-web"><a href="javascript:void(0)"> <i class="fa fa-globe"></i></a></li>';
               result = result + ' </ul>';
               result = result + ' </div>';
               result = result + ' </div>';
               result = result + '</div>';
		       result = result + ' <div class="col-md-6">';
		       result = result + '<ul class="list-unstyled can-perdt">';
		       result = result + ' <li><i class="fa fa-envelope-o"></i><a href="#" title="click here">'+json.candidate.email+'</a></li>';
		       result = result + '<li><i class="fa fa-phone"></i>'+json.candidate.mobilePhone+'</li>';
		       result = result + '<li><i class="fa fa-location-arrow"></i>Hyderabad, TS, India</li>';
		       result = result + '<li><i class="fa fa-rupee"></i>200/Per Hour - Current Rate / Salary</li>';
		       result = result + '<li><i class="fa fa-rupee"></i>300/Per Hour - Expected Rate / Salary</li>';
		       result = result + '</ul>';
		       result = result + '</div>';
		       result = result + '</div>';
       		
		       result = result + ' <div class="project_side_wrap_inn_tabs">';
		       result = result + '<ul class="new-tabs">';
		       result = result + ' <li class="current"><a href="#tabb-1" onClick="getSubmissionDetailsSideBar('+submissionId+','+candidateId+');">Time Line</a></li>';
		       result = result + ' <li><a href="#tabb-2" onClick="getCandidateProfile('+candidateId+');">Profile</a></li>';
		       result = result + ' <li><a href="#tabb-3" onClick="getCandidateResume('+candidateId+');">Resume</a></li>';
		       result = result + '<li><a href="#tabb-4" onclick="employeeSubmissionDetails('+candidateId+');">Employer</a></li>';
		       result = result + '<li><a href="#tabb-5" onclick="educationSubmissionDetails('+candidateId+');">Education</a></li>';
		       result = result + '<li><a href="#tabb-6" onclick="certificationSubmissionDetails('+candidateId+');">Certifications</a></li>';
		       result = result + '<li><a href="#tabb-7">Activities</a></li>';
		       result = result + '<div class="clearfix"></div>';
		       result = result + '</ul>';
		       result = result + '<div class="project_tabs">';
		       
		       result = result + '<div id="tabb-4" class="new-tabs_inn" style="display: block;">';
		       result = result + '</div>';
		       
		       result = result + '<div id="tabb-5" class="new-tabs_inn" style="display: block;">';
		       result = result + '</div>';
		       
		       result = result + '<div id="tabb-6" class="new-tabs_inn" style="display: block;">';
		       result = result + '</div>';
		       
		       result = result + '<div id="tabb-2" class="new-tabs_inn" style="display: block;">';
		       result = result + '</div>';
		       
		       result = result + '<div id="tabb-3" class="new-tabs_inn" style="display: block;">';
		       result = result + '</div>';
		       
		       result = result + '<div id="tabb-1" class="new-tabs_inn" style="display: block;">';

		       result = result + '<div class="require_wrap_details">';
		       result = result + '<div class="clearfix">';
			   result = result + '</div> ';        
				   
			   result = result + ' <div class="row">';
			  
			   			//Submission Details
			   			 result = result + '<div class="col-md-6">';
			  			 result = result + '<div class="release-jobs submissionDetails">';
					     result = result + '<div class="clearfix sub-details">';
					     result = result + ' <h2 class="active">Submission Details</h2>';
					     result = result + ' <div class="content clearfix" style="display: block;">';
					     result = result + submissionDetails;
					     result = result + ' </div>';
					     result = result + ' </div>';
		                 result = result + ' </div>';
	                     result = result + ' </div>';
	                    
	                    
	                  //Internal Interviews List
	                  result = result + '<div class="col-md-6">';
	     			  result  = result + '   <div id="internalInterviewsList" ></div>';
	     			  
	     			//Customer Interviews List
	     			  result  = result + '   <div id="customerInterviewsList" ></div>';
	     			 result = result + ' </div>';      
       
          result = result + '</div>';
          
getInternalInterviewsList(submissionId);
getCustomerInterviewsList(submissionId);

$("#viewCandidateDiv").empty();  
$("#viewCandidateDiv").append(result);

$("#tabb-1").show()
$(".new-tabs a").click(function(event) {
    event.preventDefault();
    $(this).parent().addClass("current");
    $(this).parent().siblings().removeClass("current");
    var tab = $(this).attr("href");
    $(".new-tabs_inn").not(tab).css("display", "none");
    $(tab).fadeIn();
}); 

	    	  }
	 });
				   
	 			 
  
 $(".clients_side_wrap").addClass("clients_side_wrap_active");
 $(".side_mask").fadeIn(200);
 $("body").css('overflow-y', 'hidden');
  
 
	

}
</script>


<script>
    $(".viewCandidate_close, .side_mask").click(function() {
    	$(".clients_side_wrap").removeClass("clients_side_wrap_active");
		$(".side_mask").fadeOut(200);
		$("body").css('overflow-y', 'auto');
	});	
</script>


<!-- <script>
$(document).ready(function() {
	$("#tabb-1").show()
    $(".new-tabs a").click(function(event) {
        event.preventDefault();
        $(this).parent().addClass("current");
        $(this).parent().siblings().removeClass("current");
        var tab = $(this).attr("href");
        $(".new-tabs_inn").not(tab).css("display", "none");
        $(tab).fadeIn();
    });
});

</script>
 -->


<script>
function getInternalInterviewsList(submissionId){
	var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({  
         type : "POST",
         url: sourceleadUrl+"/getAllInternalInterviews?${_csrf.parameterName}=${_csrf.token}&submissionId="+submissionId,        
         success :function(data) {
              if(data != null && data != 'undefined' && data != "" && data != '[]'){
            		 if( data.indexOf('Sign In') >= 0){
			    		location.replace(sourceleadUrl);
			    	 }
            		 else if (data.indexOf('exception') >= 0) {
            			 location.replace(sourceleadUrl+"/errorPage");
            		 }
            		 else{
	                	 $("#internalInterviewsList").empty();
	                	 var result = "";
                    	 result = result + '<div class="clearfix">';
   					     result = result + '<div class="content clearfix" style="display: block;">';
	                     var json = JSON.parse(data);
	                     
	                     for (var i = 0; i < json.length; i++) { 
	                    	 
	                    	 var report = json[i]; 
	                    	 
	                    	 var notifiedDetails ="";
	                    	 var interviewers = "---";
	                    	 var interviewTypeHeader = "";
	                    	 var interviewType = "---";
	                    	 
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
	                    	 
	                    	 if(report.interviewersList != null && report.interviewersList != 'undefined' && report.interviewersList != ""){
	                    		 interviewers = report.interviewersList;
	                    	 }
	                    	 
	                    	 if(report.interviewType != null && report.interviewType != 'undefined' && report.interviewType != ""){
	                    		 interviewType = report.interviewType;
	                    		 interviewTypeHeader = report.interviewType;
	                    	 }
	                    	 
	   					     result = result + '<div id="accordion-sl" class="release-jobs">';
	                    	 result = result + '<h2>Interview: <span>'+interviewTypeHeader+' '+report.dateOfInterviewStr+' '+report.startTime+' </span></h2>';
	                    	 result = result + '<div class="content clearfix">';
	                    	 result = result + '<div class="interview-type">';
	                    	 
	                    	 result = result + '<div class="row clearfix">';
	                    	 result = result + '<div class="action-nav" >';
	                    	 result = result + '<span id="'+report.id+'actionDiv">';
		                    	 if(report.interviewStatus == null || report.interviewStatus == '' || report.interviewStatus =='undefined'){
		                    		 result = result + '<a href="javascript:void(0)" onclick="changeInterviewStatus('+submissionId+','+report.id+',\'Attending\');" title="Attending"><i class="fa fa-check text-success"></i></a>';
			                    	 result = result + '<a href="javascript:void(0)" onclick="changeInterviewStatus('+submissionId+','+report.id+',\'Not Attending\');" title="Not Attending"><i class="fa fa-close text-danger"></i></a>';
			                    	 result = result + '<a href="javascript:void(0)" onclick="changeInterviewStatus('+submissionId+','+report.id+',\'Maybe\');" title="Maybe"  class="brder-int"><i class="fa fa-question-circle text-muted"></i></a>';
		                    	 }
	                    	 result = result + '</span>';
	                    	 result = result + '<span id="'+report.id+'showStatus"></span>';
	                    	 if(report.interviewStatus == "Attending"){
	                    		 result = result +' <font color="green" class="brder-int">Attending</font>';
	                    	 }
	                    	 if(report.interviewStatus == "Not Attending"){
	                    		 result = result +' <font color="red" class="brder-int">Not Attending</font>';
	                    	 }
	                    	 if(report.interviewStatus == "Maybe"){
	                    		 result = result +' <font color="orange" class="brder-int">Maybe</font>';
	                    	 }
	                    	 
	                    	 
	                    	 result = result + '<a href="javascript:void(0)" class="brder-int-left" onclick="changeInterviewStatus('+report.id+',\'Not Attending\');" title="Notify to Applicant"><i class="fa fa-user text-muted"></i></a>';
	                    	 result = result + '<a href="javascript:void(0)" onclick="changeInterviewStatus('+report.id+',\'Maybe\');" title="Notify to Vednor"><i class="fa fa-suitcase text-muted"></i></a>';
	                    	 
	                    	 result = result + '</div>';
	                    	 result = result + '</div>';
	                    	 
	                    	 result = result + '<div class="row clearfix">';
	                    	 result = result + '<label class="col-md-5">';
	                    	 result = result + '<strong>Interview Location</strong>';
	                    	 result = result + '</label>';
	                    	 result = result + '<p class="col-md-7">'+report.location+'</p>';
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
                             if(report.endTime != null && report.endTime != 'undefined' && report.endTime != ""){
                            	 result = result + '<p class="col-md-7 tbldata-dotdot-big">'+report.startTime+' to '+report.endTime+'</p>';
	                    	 }else{
	                    		 result = result + '<p class="col-md-7 tbldata-dotdot-big">'+report.startTime+'</p>';
	                    	 }
	                    	 result = result + '</div>';
	                    	 
	                    	 result = result + '<div class="row clearfix">';
	                    	 result = result + '<label class="col-md-5">';
	                    	 result = result + '<strong>Interviewers</strong>';
	                    	 result = result + '</label>';
	                    	 result = result + '<p class="col-md-7 tbldata-dotdot-big">'+interviewers+'</p>';
	                    	 result = result + '</div>';
	                    	 
	                    	 result = result + '<div class="row clearfix">';
	                         result = result + '<label class="col-md-5">';
	                         result = result + '<strong>Reminder details</strong>';
	                         result = result + '</label>';
	                         result = result + '<p class="col-md-7 tbldata-dotdot-big">'+report.remainderTime+'</p>';
	                         result = result + '</div>';
	                         
	                         result = result + '<div class="row clearfix">';
	                         result = result + '<label class="col-md-5">';
	                         result = result + '<strong>Notified to</strong>';
	                         result = result + '</label>';
	                         result = result + '<p class="col-md-7 tbldata-dotdot-big">'+notifiedDetails+'</p>';
	                         result = result + '</div>';
	                         
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
	                     $('#accordion-sl h2').click(function() {
	                         if($(this).next().is(':hidden')) {
	                             $('#accordion-sl h2').removeClass('active').next().slideUp('slow');
	                             $(this).toggleClass('active').next().slideDown('slow');
	                         }
	                     });
			    	} 
              }/* else{
	    	         var result ="";
					 $("#internalInterviewsList").empty();
				     $("#internalInterviewsList").append(result); 
              } */
       },
         
    }); 
	 
	 
}
</script>

<script>
function changeInterviewStatus(submissionId,interviewId,interviewStatus){
	var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/acceptOrRejectInterview?${_csrf.parameterName}=${_csrf.token}&interviewStatus="+interviewStatus+"&requirementInterviewId="+interviewId,	    	   
		    success: function(data) {
		    	 jQuery("#"+(interviewId)+"actionDiv").css("display","none");
		    	 if(interviewStatus == "Attending"){
           		    $("#"+(interviewId)+"showStatus").html("<font color='green'>Attending</font>");
	           	 }
	           	 if(interviewStatus == "Not Attending"){
	           		$("#"+(interviewId)+"showStatus").html("<font color='red'>Not Attending</font>");
	           	 }
	           	 if(interviewStatus == "Maybe"){
	           		$("#"+(interviewId)+"showStatus").html("<font color='orange'>Maybe</font>");
	           	 }
		    }
	   });
}
</script>

<script>
function getCustomerInterviewsList(submissionId){
	var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({  
         type : "POST",
         url: sourceleadUrl+"/getAllInternalInterviews?${_csrf.parameterName}=${_csrf.token}&submissionId="+submissionId,        
         success :function(data) {
              if(data != null && data != 'undefined' && data != "" && data != '[]'){
            		 if( data.indexOf('Sign In') >= 0){
			    		location.replace(sourceleadUrl);
			    	 }
            		 else if (data.indexOf('exception') >= 0) {
            			 location.replace(sourceleadUrl+"/errorPage");
            		 }
            		 else{
	                	 $("#customerInterviewsList").empty();
	                	 var result = "";
                    	 result = result + '<div class="clearfix">';
   					     result = result + '<div class="content clearfix" style="display: block;">';
	                     var json = JSON.parse(data);
	                     
	                     for (var i = 0; i < json.length; i++) { 
	                    	 
	                    	 var report = json[i]; 
	                    	 
	                    	 var notifiedDetails ="";
	                    	 var interviewers = "---";
	                    	 var interviewTypeHeader = "";
	                    	 var interviewType = "---";
	                    	 
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
	                    	 
	                    	 if(report.interviewersList != null && report.interviewersList != 'undefined' && report.interviewersList != ""){
	                    		 interviewers = report.interviewersList;
	                    	 }
	                    	 
	                    	 if(report.interviewType != null && report.interviewType != 'undefined' && report.interviewType != ""){
	                    		 interviewType = report.interviewType;
	                    		 interviewTypeHeader = report.interviewType;
	                    	 }
	                    	 
	   					     result = result + '<div id="accordion-sl" class="release-jobs">';
	                    	 result = result + '<h2>Interview: <span>'+interviewTypeHeader+' '+report.dateOfInterviewStr+' '+report.startTime+' </span></h2>';
	                    	 result = result + '<div class="content clearfix">';
	                    	 result = result + '<div class="interview-type">';
	                    	 
	                    	 result = result + '<div class="row clearfix">';
	                    	 result = result + '<div class="action-nav" >';
	                    	 result = result + '<span id="'+report.id+'actionDiv">';
		                    	 if(report.interviewStatus == null || report.interviewStatus == '' || report.interviewStatus =='undefined'){
		                    		 result = result + '<a href="javascript:void(0)" onclick="changeInterviewStatus('+submissionId+','+report.id+',\'Attending\');" title="Attending"><i class="fa fa-check text-success"></i></a>';
			                    	 result = result + '<a href="javascript:void(0)" onclick="changeInterviewStatus('+submissionId+','+report.id+',\'Not Attending\');" title="Not Attending"><i class="fa fa-close text-danger"></i></a>';
			                    	 result = result + '<a href="javascript:void(0)" onclick="changeInterviewStatus('+submissionId+','+report.id+',\'Maybe\');" title="Maybe"><i class="fa fa-question-circle text-muted"></i></a>';
		                    	 }
	                    	 result = result + '</span>';
	                    	 result = result + '<span id="'+report.id+'showStatus"></span>';
	                    	 if(report.interviewStatus == "Attending"){
	                    		 result = result +' <font color="green">Attending</font>';
	                    	 }
	                    	 if(report.interviewStatus == "Not Attending"){
	                    		 result = result +' <font color="red">Not Attending</font>';
	                    	 }
	                    	 if(report.interviewStatus == "Maybe"){
	                    		 result = result +' <font color="orange">Maybe</font>';
	                    	 }
	                    	 result = result + '</div>';
	                    	 result = result + '</div>';
	                    	 
	                    	 result = result + '<div class="row clearfix">';
	                    	 result = result + '<label class="col-md-5">';
	                    	 result = result + '<strong>Interview Location</strong>';
	                    	 result = result + '</label>';
	                    	 result = result + '<p class="col-md-7">'+report.location+'</p>';
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
                             if(report.endTime != null && report.endTime != 'undefined' && report.endTime != ""){
                            	 result = result + '<p class="col-md-7 tbldata-dotdot-big">'+report.startTime+' to '+report.endTime+'</p>';
	                    	 }else{
	                    		 result = result + '<p class="col-md-7 tbldata-dotdot-big">'+report.startTime+'</p>';
	                    	 }
	                    	 result = result + '</div>';
	                    	 
	                    	 result = result + '<div class="row clearfix">';
	                    	 result = result + '<label class="col-md-5">';
	                    	 result = result + '<strong>Interviewers</strong>';
	                    	 result = result + '</label>';
	                    	 result = result + '<p class="col-md-7 tbldata-dotdot-big">'+interviewers+'</p>';
	                    	 result = result + '</div>';
	                    	 
	                    	 result = result + '<div class="row clearfix">';
	                         result = result + '<label class="col-md-5">';
	                         result = result + '<strong>Reminder details</strong>';
	                         result = result + '</label>';
	                         result = result + '<p class="col-md-7 tbldata-dotdot-big">'+report.remainderTime+'</p>';
	                         result = result + '</div>';
	                         
	                         result = result + '<div class="row clearfix">';
	                         result = result + '<label class="col-md-5">';
	                         result = result + '<strong>Notified to</strong>';
	                         result = result + '</label>';
	                         result = result + '<p class="col-md-7 tbldata-dotdot-big">'+notifiedDetails+'</p>';
	                         result = result + '</div>';
	                         
	                         result = result + '</div>';
	                         result = result + '</div>';
	                         result = result + '</div>';
	                      }  
	                     result = result + '</div>';
	                     result = result + '</div>';
	                    
	                     $("#customerInterviewsList").append(result); 
	                     
	                     $('#accordion-sl .content').hide();
	                    // $('#accordion-sl').find('.test2'+interviewId+'').addClass('active').next().slideDown('slow');
	                     $('#accordion-sl h2').click(function() {
	                         if($(this).next().is(':hidden')) {
	                             $('#accordion-sl h2').removeClass('active').next().slideUp('slow');
	                             $(this).toggleClass('active').next().slideDown('slow');
	                         }
	                     });
			    	} 
              }/* else{
	    	         var result ="";
					 $("#customerInterviewsList").empty();
				     $("#customerInterviewsList").append(result); 
              } */
       },
         
    }); 
	 
	 
}
</script>

