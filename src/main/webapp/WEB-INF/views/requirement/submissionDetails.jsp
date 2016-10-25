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
 .sub-details .formgroup {
 padding:5px 0;
 }
 .sub-details .formgroup:nth-child(odd){
 background: #fff;
 }
 .sub-details .col-md-7 {
 word-break: break-all;
 }
 .sub-details .content{
 padding: 10px 0px !important;
 }
 </style>
 
<script>
function getSubmissionDetailsSideBar(submissionId,candidateId){
	var sourceleadUrl = '${sourceleadUrl}';
	
	$.ajax({ 
	    	type: "POST",
	    	url: sourceleadUrl+"/getSubmissionDetails?${_csrf.parameterName}=${_csrf.token}&submissionId="+submissionId,
	    			
	    	  success: function(data) {
		       
	    	   var json = JSON.parse(data);
	    	   
	    	  
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
	    	   
	    	   var result = "";
          	   result = result + ' <div class="req_hide">';
               result = result + '<div class="profile_details clearfix">';
               result = result + '<div class="col-md-6">';
               result = result + ' <div class="media">';
               result = result + '<div class="media-left image-parent">';
               result = result + '<div class="imageParent"><img src="./images/24.jpg" alt="candidate" title="Candidate"></div>';
               result = result + ' </div>';
               result = result + ' <div class="media-body candidate-details">';
               result = result + ' <h4 class="media-heading">Jaden Smith</h4>';
               result = result + ' <ul class="list-unstyled">';
               result = result + ' <li><strong>Business Analyst - Full Time</strong></li>';
               result = result + ' <li>Experience - 4 years</li>';
               result = result + ' </ul>';
               result = result + '</div>';
               result = result + '</div>';
               result = result + '<div class="other-detials">';
               result = result + '<div class="professinal-details">';
               result = result + ' <ul class="list-inline list-unstyled">';
               result = result + '<li>BA</li>';
               result = result + ' <li>BRS</li>';
               result = result + '<li>SRS</li>';
               result = result + ' <li>Agile</li>';
               result = result + '<li>SDLC</li>';
               result = result + ' </ul>';
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
		       result = result + ' <li><i class="fa fa-envelope-o"></i><a href="#" title="click here">jadensmith@hotmail.com</a></li>';
		       result = result + '<li><i class="fa fa-phone"></i>+91 9900223355</li>';
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
			  			 result = result + '<div id="accordion-sl" class="release-jobs">';
					     result = result + '<div class="clearfix sub-details">';
					     result = result + ' <h2 class="active">Submission Details</h2>';
					     result = result + ' <div class="content clearfix" style="display: block;">';
					     result = result + submissionDetails;
					     result = result + ' </div>';
					     result = result + ' </div>';
		                 result = result + ' </div>';
	                     result = result + ' </div>';
	                    
	                    
	                    
	     			        result = result + '<div class="col-md-6">';
	     			         result = result + '<div id="accordion-sl" class="release-jobs">';
	     		                    
	     					   // Accordion First Tab 
	     					   result = result + '<div class="clearfix">';
	     					   result = result + ' <h2 class="active">Interview</h2>';
	     					   result = result + ' <div class="content clearfix" style="display: block;">';
	     					   result = result + 'xvcxzvxzv';
	     					   result = result + ' </div>';
	     					   result = result + ' </div>';
	     					   
		       result = result + '</div>';
		       result = result + '</div>';
       
          result = result + '</div>';
          


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






