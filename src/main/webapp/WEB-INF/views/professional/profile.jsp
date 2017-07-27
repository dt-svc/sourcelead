<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <link href="resources/css/jquery-ui.css" rel="stylesheet">
 <link href="resources/css/crowdsourcingstyles.css" rel="stylesheet">
<!-- <script src="resources/js/formvalidator.js"></script>
<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script> -->

<style>
.margin-down{
    margin-bottom: 100px;
}
</style>
<script> 
$(document).ready(function(){
	getProfessionalInfo();
});
</script>

<script>
	function predefineProfileImage(imageId){   
		$(imageId).attr('src','resources/images/userprofile-img.png');
	}
</script>

<section class="content-wraper">
        
     <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Profile</h2>
      </div>
       <div class="pull-right brud-crum"><a href="<c:url value='/recruiterDashboard'/>">Home </a> &raquo; Update Profile</div>
    </section>
    
  <!-- Profile Content Start-->
    <div id="professionalInfoDiv"></div>
     <!-- Profile Content End -->
     
     
</section>

<script>
function getProfessionalInfo(){ 
	var sourceleadUrl ='${sourceleadUrl}';
	var candidateType= "Professional";
	var userId = '${SourceLeadWeb_userId}';
	var candidateId = '${SourceLeadWeb_candidateId}';
	$.ajax({
	   	type: "GET",
	    url: sourceleadUrl+"/getProfessionalInfo?candidateStatus="+candidateType, 	   
		    success: function(data) {
		    	 var json = JSON.parse(data);
		    	 if(json.firstName != null && json.firstName != '' && json.firstName !='undefined'){
		    		 firstName = json.firstName;
		    	 }else{
		    		 firstName = "";
		    	 }
		    	 if(json.lastName != null && json.lastName != '' && json.lastName !='undefined'){
		    		 lastName = json.lastName;
		    	 }else{
		    		 lastName = "";
		    	 }
		    	 
		    	 if(json.noticePeriod != null && json.noticePeriod != '' && json.noticePeriod !='undefined'){
		    		 noticePeriod = json.noticePeriod;
		    	 }else{
		    		 noticePeriod = "";
		    	 }
		    	 
		    	 if(json.designation != null && json.designation != '' && json.designation !='undefined'){
		    		 designation = json.designation;
		    	 }else{
		    		 designation = "---";
		    	 }
		    	 if(json.designationType != null && json.designationType != '' && json.designationType !='undefined'){
		    		 designationType = json.designationType;
		    	 }else{
		    		 designationType = "";
		    	 }
		    	 
		    	 if(json.designationOtherType != null && json.designationOtherType != '' && json.designationOtherType !='undefined'){
		    		 designationOtherType = ' : '+json.designationOtherType;
		    	 }else{
		    		 designationOtherType = "";
		    	 }
		    	 
		    	 if(json.email != null && json.email != '' && json.email !='undefined'){
		    		 email = json.email;
		    	 }else{
		    		 email = "";
		    	 }
		    	 if(json.mobilePhone != null && json.mobilePhone != '' && json.mobilePhone !='undefined'){
		    		 mobilePhone = json.mobilePhone;
		    	 }else{
		    		 mobilePhone = "";
		    	 }
		    	 
		    	 if(json.alternateMobilePhone != null && json.alternateMobilePhone != '' && json.alternateMobilePhone !='undefined'){
		    		 alternateMobilePhone = ' , '+json.alternateMobilePhone;
		    	 }else{
		    		 alternateMobilePhone = "";
		    	 }
		    	
		    	 if(json.country != null && json.country != '' && json.country !='undefined'){
		    		 country = json.country;
		    	 }else{
		    		 country = "";
		    	 }
		    	 
		    	 if(json.state != null && json.state != '' && json.state !='undefined'){
		    		 state = ' , '+json.state;
		    	 }else{
		    		 state = "";
		    	 }
		    	 
		    	 if(json.city != null && json.city != '' && json.city !='undefined'){
		    		 city = ' , '+json.city;
		    	 }else{
		    		 city = "";
		    	 }
		    	 
		    	 if(json.primarySkill != null  && json.primarySkill != '' && json.primarySkill != 'undefiend'){
		    		 primarySkill = json.primarySkill;
		    	 }else{
		    		 primarySkill = " --- ";
		    	 }
		    	 
		    	 if(json.skillsName != null  && json.skillsName != '' && json.skillsName != 'undefiend'){
		    		 skillsName = json.skillsName;
		    	 }else{
		    		 skillsName = " --- ";
		    	 }
		    	 if(json.facebook != null  && json.facebook != '' && json.facebook != 'undefiend'){
		    		 facebook = json.facebook;
		    	 }else{
		    		 facebook = "";
		    	 }
		    	 if(json.linkedIn != null  && json.linkedIn != '' && json.linkedIn != 'undefiend'){
		    		 linkedIn = json.linkedIn;
		    	 }else{
		    		 linkedIn = "";
		    	 }
		    	 if(json.twitter != null  && json.twitter != '' && json.twitter != 'undefiend'){
		    		 twitter = json.twitter;
		    	 }else{
		    		 twitter = "";
		    	 }
		    	 if(json.googlePlus != null  && json.googlePlus != '' && json.googlePlus != 'undefiend'){
		    		 googlePlus = json.googlePlus;
		    	 }else{
		    		 googlePlus = "";
		    	 }
		    	 
		    	 if(json.experienceYears != null  && json.experienceYears != '' && json.experienceYears != 'undefiend'){
		    		 experienceYears = json.experienceYears +' Years';
		    	 }else{
		    		 experienceYears = "";
		    	 }
		    	 
		    	 if(json.experienceMonths != null  && json.experienceMonths != '' && json.experienceMonths != 'undefiend'){
		    		 experienceMonths = json.experienceMonths +' Months';
		    	 }else{
		    		 experienceMonths = "";
		    	 }
		    	 
		    	 if(json.currentRate != null  && json.currentRate != '' && json.currentRate != 'undefiend'){
		    		 currentRate = json.currentRate;
		    	 }else{
		    		 currentRate = "";
		    	 }
		    	 if(json.currentRateType != null  && json.currentRateType != '' && json.currentRateType != 'undefiend'){
		    		 currentRateType = json.currentRateType;
		    	 }else{
		    		 currentRateType = "";
		    	 }
		    	 
		    	 if(json.currentRateCurrency != null  && json.currentRateCurrency != '' && json.currentRateCurrency != 'undefiend'){
		    		 currentRateCurrency = json.currentRateCurrency+' / ';
		    	 }else{
		    		 currentRateCurrency = "";
		    	 }
		    	 
		    	 if(json.expectedRateCurrencey != null  && json.expectedRateCurrencey != '' && json.expectedRateCurrencey != 'undefiend'){
		    		 expectedRateCurrencey = json.expectedRateCurrencey+' / ';
		    	 }else{
		    		 expectedRateCurrencey = "";
		    	 }
		    	 if(json.expectedRate != null  && json.expectedRate != '' && json.expectedRate != 'undefiend'){
		    		 expectedRate = json.expectedRate;
		    	 }else{
		    		 expectedRate = "";
		    	 }
		    	 
		    	 if(json.rateType != null  && json.rateType != '' && json.rateType != 'undefiend'){
		    		 rateType = json.rateType;
		    	 }else{
		    		 rateType = "";
		    	 }
		    	 var location = '';
		    	 if(json.location != null && json.location != '' && json.location != 'undefined'){
		    		 location = json.location;
		    	 }
		    	 var result = "";
				 result = result +'	<div class="content-area clearfix panelBody"> ';
				 result = result +'	<section class="row pro_info">';
				 result = result +'	    <div class="col-md-5">';
				 result = result +'	        <h5>Professional Info</h5>';
				 result = result +'	        <div class="chip">';
				 result = result +'	        <img onerror="predefineProfileImage(this);"  src="<c:url value="'+sourceleadUrl+'/displayUserImage/'+userId+'"/>" alt="Profile Image" width="96" height="96">';
				 
				 result = result +'	        <span>';
				  result = result +'	        <strong>'+firstName+' '+lastName+'</strong> <br>'; 
				 result = result +'	        </span>';
				 result = result +'	        </div>';
				 
				 result = result+'  <div class="UsProBar progViPro">';                                                                                                                                        
				 result = result+' <div class="progress nomr">  ';
					if(json.profilePercentage>=50){ 
						result = result+'   <div class="progress-bar  progress-bar-success  ';
					}
					else{
						result = result+'   <div class="progress-bar  progress-bar-danger  ';
					}
				result = result+' progress-bar-striped active" role="progressbar" id="perVal" aria-valuenow="'+json.profilePercentage+'" aria-valuemin="0" aria-valuemax="100" style="width:'+json.profilePercentage+'%">  ';
				result = result+'     <p id="disVal">'+json.profilePercentage+'%</p>                                                                                                                                                                      ';
				result = result+'   </div>  ';
				result = result+' </div>                 ';                                                                                                                                                      
				result = result+' </div> ';
				
				 result = result +'	        <ul class="list-unstyled professional-details">';
				 result = result +'	            <li><i class="fa fa-magnet" title="Designation"></i><strong>'+designation+'</strong></li>';
				 result = result +'	            <li><i class="fa fa-clock-o" title="Employment Type"></i><span class="text-muted">'+designationType+' '+designationOtherType+'</strong></li>';
				 result = result +'	            <li><i class="fa fa-briefcase" title="Experience"></i>'+experienceYears+' &nbsp; '+experienceMonths+'</li>';
				 result = result +'	            <li><i class="fa fa-calendar" title="Notice Period"></i>'+noticePeriod+'</li>';
				 result = result +'	            <li> <i class="fa fa-rupee" title="Current Rate"></i>';
				 result = result +'	                <span><strong>Current Rate: </strong>'+currentRate+'&nbsp;'+currentRateCurrency+' '+currentRateType+'</span>';
				 result = result +'	            </li>';
				 result = result +'	            <li> <i class="fa fa-rupee" title="Expected Rate"></i>';
				 result = result +'	                <span><strong>Expected Rate:</strong> '+expectedRate+' &nbsp;'+expectedRateCurrencey+' '+rateType+'</span>';
				 result = result +'	            </li>';
				 result = result +'	        </ul>';
				 result = result +'	    </div>';
				 result = result +'	    <div class="col-md-3">';
				 result = result +'	        <h5>Can be Reached at</h5>';
				 result = result +'	        <ul class="list-inline list-unstyled reachout-links mt10">';
				 result = result +'	            <li><a href="'+facebook+'"  title="Facebook" class="btn-fb" target="_blank"><i class="fa fa-facebook-square fa-2x" target="_blank"></i></a></li>';
				 result = result +'	            <li><a href="'+linkedIn+'" title="LinkedIn" class="btn-li" target="_blank"><i class="fa fa-linkedin-square fa-2x" target="_blank"></i></a></li>';
				 result = result +'	            <li><a href="'+twitter+'" title="Twitter" class="btn-tw" target="_blank"><i class="fa fa-twitter-square fa-2x" target="_blank"></i></a></li>';
				 result = result +'	            <li><a href="'+googlePlus+'" title="Google" class="btn-go" target="_blank"><i class="fa fa-google-plus-square fa-2x" target="_blank"></i></a></li>';
				 result = result +'	        </ul>';
				 result = result +'	        <div class="reachout">';
				 result = result +'	        <p><i class="fa fa-at" title="Mail me"></i>'+email+'</p>';
				 result = result +'	        <p><i class="fa fa-phone" title="Call us"></i>'+mobilePhone+' '+alternateMobilePhone+'</p>';
				 result = result +'	        <p><i class="fa fa-map-marker" title="Location"></i>'+location+'</p>';
				 result = result +'	        </div>';
				 result = result +'	    </div>';
				 
				 result = result +'	    <div class="col-md-4">';
				 result = result +'	        <h5>Primary Skill</h5>';
				 result = result +'	        <div class="skills mt10 mb10">';
				 result = result +'	            <p><strong>';
				 result = result +'	        '+primarySkill+'</strong>';
				 result = result +'	        </p>';
				 result = result +'	            </div>';
				 result = result +'	    </div>';
				 
				 result = result +'	    <div class="col-md-4">';
				 result = result +'	        <h5>Skills</h5>';
				 result = result +'	        <div class="skills mt10">';
				 result = result +'	            <p><strong>';
				 result = result +'	        '+skillsName+'</strong>';
				 result = result +'	        </p>';
				 result = result +'	            </div>';
				 result = result +'	    </div>';
				 
				 result = result +'	</section>';
				 result = result +'	</div>';
				 
				 
				 result = result +' <section class="clearfix mt25 margin-down">';
				 result = result +'<div class="project_side_wrap_inn_tabs">';
			     result = result +'   <ul class="project-tabs-menu">';
			     result = result +'     <li class="current"><a href="#tab-1" onclick="professionalProfile('+json.id+');">Profile</a></li>';
			     result = result +'     <li><a href="#tab-2" onclick="candidateResumesList('+json.id+'); candidateResume('+json.id+');">Resume</a></li>';
			     result = result +'     <li><a href="#tab-3" onclick="professionalCandidateEmployers('+json.id+');">Employers</a></li>';
			     result = result +'     <li><a href="#tab-4" onclick="professionalCandidateEducation('+json.id+');">Education</a></li>';
			     result = result +'     <li><a href="#tab-5" onclick="professionalCandidateCertificate('+json.id+');">Certifications</a></li>';
			     result = result+'<li><a href="#tab-6" onclick="professionalCandidateDocuments('+json.id+');">Documents</a></li>';
			     result = result+'<li><a href="#tab-7" onclick="professionalCandidateReferences('+json.id+');">References</a></li>';
			     
			     result = result +'     <div class="clearfix"></div>';
			     result = result +'   </ul>';
			     
			     result = result+'     <div class="project_tabs candiateInfo">';
		         result = result + ' <div id="tab-1" class="project_tabs_inn">';
				 result = result + '</div>';

				 result = result + '<div id="tab-2" class="project_tabs_inn">';
				 result = result + '</div>';
				 
				 result = result + '<div id="tab-3" class="project_tabs_inn addPop">';
				 result = result + '</div>';
				 
				 result = result + ' <div id="tab-4" class="project_tabs_inn addPop">';
				 result = result + '</div>';
				 
				 result = result + '<div id="tab-5" class="project_tabs_inn addPop">';
				 result = result + '</div>';
				 
				 result = result + '<div id="tab-6" class="project_tabs_inn addPop">';
				 result = result + '</div>';
				 
				 result = result + '<div id="tab-7" class="project_tabs_inn addPop">';
				 result = result + '</div>';
				 
				 result = result + '<div id="tab-8" class="project_tabs_inn addPop">';
				 result = result + '</div>';
				 
				 result = result + '<div id="tab-9" class="project_tabs_inn addPop">';
				 result = result + '</div>';
				 
			     result = result +'	</div>';
			     result = result +' </section>';
				 
				 $("#professionalInfoDiv").empty();
				 $("#professionalInfoDiv").append(result); 
				 professionalProfile(json.id);
		    },
	});
            
}

</script>


<jsp:include page="professionalCandidatesProfile.jsp"></jsp:include>
<jsp:include page="professionalCandidateResumes.jsp"></jsp:include>
<jsp:include page="professionalCandidateEmployers.jsp"></jsp:include>
<jsp:include page="professionalCandidateEducation.jsp"></jsp:include>
<jsp:include page="professionalCandidateCertificates.jsp"></jsp:include>
<jsp:include page="professionalCandidateDocuments.jsp"></jsp:include>
<jsp:include page="professionalCandidateReferences.jsp"></jsp:include>

<jsp:include page="../masterFooter.jsp" />