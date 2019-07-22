<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<link href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/crowdsourcingstyles.css?staticTime=${staticTime}" rel="stylesheet">

<script> 
$(document).ready(function(){
	getProfessionalInfo();
	if('${candidateResumeCount}' < 1 ){
		addCandidate();
	}
});
</script>

<script>
	function predefineProfileImage(imageId){   
		$(imageId).attr('src','/resources/images/userprofile-img.png');
	}
</script>

<section class="content-wraper">
        
     <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Profile</h2>
      </div>
       
       <!-- Redirect to main dashboard -->
       <div class="pull-right brud-crum"><a href="${appPath}/dashboard/${SourceLeadWeb_orgLocationId}">Home </a> &raquo; Profile</div>
   
    </section>
    
  <!-- Profile Content Start-->
    <div id="professionalInfoDiv"></div>
     <!-- Profile Content End -->
    
     <!-- New jsp's added here -->
     
</section>


<script>
function getProfessionalInfo(){ 
	var candidateType= "Professional";
	var userId = '${SourceLeadWeb_userId}';
	var candidateId = '${SourceLeadWeb_candidateId}';
	var locationCode = '${SourceLeadWeb_locationCode}';
	var atsUrl = '${SourceLeadWeb_atsUrl}';
	$.ajax({
	   	type: "GET",
	    url: atsUrl+"/getProfessionalInfo?candidateId="+candidateId+"&locationCode="+locationCode, 	   
		    success: function(json) {
		    	/*  var json = JSON.parse(data); */
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
		    	 if(json.middelName != null && json.middelName != '' && json.middelName !='undefined'){
		    		 middelName = json.middelName;
		    	 }else{
		    		 middelName = "";
		    	 }
		    	 
		    	 if(json.noticePeriod != null && json.noticePeriod != '' && json.noticePeriod !='undefined'){
		    		 noticePeriod = json.noticePeriod;
		    	 }else{
		    		 noticePeriod = "---";
		    	 }
		    	 
		    	 if(json.designation != null && json.designation != '' && json.designation !='undefined'){
		    		 designation = json.designation;
		    	 }else{
		    		 designation = "---";
		    	 }
		    	 if(json.designationType != null && json.designationType != '' && json.designationType !='undefined'){
		    		 designationType = json.designationType;
		    	 }else{
		    		 designationType = "---";
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
		    	 
		    	 if(json.experienceYears != null  && json.experienceYears != '' && json.experienceYears != 'undefiend'){
		    		 experienceYears = json.experienceYears +' Years';
		    	 }else{
		    		 experienceYears = "---";
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
				 result = result +'	        <h5>Profile Info</h5>';
				 result = result +'	        <div class="chip">';
				 result = result +'	        <img data-id="userProfileImage" onerror="predefineProfileImage(this);"  src="${SourceLeadWeb_userImgUrl}" alt="Profile Image" width="96" height="96">';
				 
				 result = result +'	        <span>';
				  result = result +'	        <strong>'+firstName+' '+middelName+' '+lastName+'</strong> <br>'; 
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
				 result = result+'<li><span class="col-md-5 padding_left paddingRight text-muted">Designation</span><span class="col-md-7 padding_left padding_right tbldata-dotdot-sm" title= '+designation+'> '+designation+'</span></li>';
				 result = result+'<li><span class="col-md-5 padding_left paddingRight text-muted">Employment Type</span><span class="col-md-7 padding_left padding_right">'+designationType+'</span></li>';
            	 if(json.gender != null && json.gender !="" && json.gender !='undefined'){
				 result = result+'<li><span class="col-md-5 padding_left paddingRight text-muted">Gender</span><span class="col-md-7 padding_left padding_right">'+json.gender+'</span></li>';
            	 }
            	 if(json.maritalStatus != null && json.maritalStatus !="" && json.maritalStatus !='undefined'){
    				 result = result+'<li><span class="col-md-5 padding_left paddingRight text-muted">Marital Status</span><span class="col-md-7 padding_left padding_right">'+json.maritalStatus+'</span></li>';
                	 }
				 result = result+'<li><span class="col-md-5 padding_left paddingRight text-muted">Experience</span><span class="col-md-7 padding_left padding_right">'+experienceYears+' '+experienceMonths+'</span></li>';
	             result = result+'<li><span class="col-md-5 padding_left paddingRight text-muted">Notice Period</span><span class="col-md-7 padding_left padding_right">'+noticePeriod+'</span></li>';
				 
				 result = result +'	        </ul>';
				 result = result +'	    </div>';
				 result = result +'	    <div class="col-md-3">';
				 result = result +'	        <h5>Contact information</h5>';
				 result = result +'<div class="socialnetwork-details">';
				 result = result +'<ul class="list-inline list-unstyled">';
				 if(json.consultantUser !=null && json.consultantUser.id != null && json.consultantUser.facebook != null && json.consultantUser.facebook !="undefined" && json.consultantUser.facebook != ""){
		             result = result+'<li class="bg-facebook" title="Facebook"><a target="_blank" href=\"'+json.consultantUser.facebook+'\" > <i class="fa fa-facebook-square" ></i></a></li>';
		             }
		             if(json.consultantUser !=null && json.consultantUser.id != null && json.consultantUser.twitter != null && json.consultantUser.twitter !="undefined" && json.consultantUser.twitter != ""){
		             result = result+'<li class="bg-twitter" title="Twitter"><a target="_blank" href=\"'+json.consultantUser.twitter+'\"> <i class="fa fa-twitter-square" ></i></a></li>';
		             }
		             if(json.consultantUser !=null && json.consultantUser.id != null && json.consultantUser.googlePlus != null && json.consultantUser.googlePlus !="undefined" && json.consultantUser.googlePlus != ""){
		             result = result+'<li class="bg-google " title="Google-Plus"><a target="_blank" href=\"'+json.consultantUser.googlePlus+'\"> <i class="fa fa-google-plus-square" ></i></a></li>';
		             }
		             if(json.consultantUser !=null && json.consultantUser.id != null && json.consultantUser.linkedIn != null && json.consultantUser.linkedIn !="undefined" && json.consultantUser.linkedIn != ""){
		             result = result+'<li class="bg-linked" title="Linkedin"><a target="_blank" href=\"'+json.consultantUser.linkedIn+'\"> <i class="fa fa-linkedin-square" ></i></a></li>';
		             } 
		         result = result +'	        </ul>';
		         result = result +'                 </div>';
				 result = result +'	        <div class="reachout">';
				 result = result +'	        <p><i class="fa fa-at" title="Mail me"></i>'+email+'</p>';
				 result = result +'	        <p><i class="fa fa-phone" title="Call us"></i>'+mobilePhone+' '+alternateMobilePhone+'</p>';
				 result = result +'	        <p><i class="fa fa-map-marker" title="Location"></i>'+location+'</p>';
				 if(json.dateofBirthString !=null && json.dateofBirthString !="" && json.dateofBirthString !="undefined"){
				 result = result +'	        <p><i class="fa fa-clock-o" title="Date of Birth"></i>'+json.dateofBirthString+'</p>';
				 }
				 result = result +'	        </div>';
				 result = result +'	    </div>';
				 
				 result = result +'	    <div class="col-md-4">';
				 
				
				 result = result +'	         <div class="reportsH profileanchor pull-right"><a title="Account Settings" href="<c:url value='${sourceleadUrl}/userProfile'/>"><i class="userSettingImages"><img src="${SourceLeadWeb_sourceleadDomainUrl}/resources/images/usersetting-img.png" alt="User settings image"></i></a>';
				 result = result +'	            </div>';
				 
				 result = result +'	        <h5>Primary Skill</h5>';
				 result = result +'	        <div class="skills mt10 mb10">';
				 result = result +'	            <p><strong>';
				 result = result +'	        '+primarySkill+'</strong>';
				 result = result +'	        </p>';
				 result = result +'	            </div>';
				 result = result +'	    </div>';
				 
				 result = result +'	    <div class="col-md-4">';
				 result = result +'	        <h5>Remuneration</h5>';
				 result = result +'	        <div class="skills mt10">';
				 result = result +'<ul class ="list-unstyled">';
				 result = result +'	            <li> <i class="fa fa-money" title="Current Rate"></i>';
				 result = result +'	                <span><strong>Current: </strong>'+currentRate+'&nbsp;'+currentRateCurrency+' '+currentRateType+'</span>';
				 result = result +'	            </li>';
				 result = result +'	            <li> <i class="fa fa-money" title="Expected Rate"></i>';
				 result = result +'	                <span><strong>Expected:</strong> '+expectedRate+' &nbsp;'+expectedRateCurrencey+' '+rateType+'</span>';
				 result = result +'	            </li>';
				 result = result +'</ul>';
				 result = result +'	            </div>';
				 result = result +'	    </div>';
				 
				
				 
				 result = result +'	</section>';
				 // account settings icon code
				 
				 result = result +'	</div>';
				
				 if('${candidateResumeCount}' > 0){
				 result = result +' <section class="clearfix mt25 margin-down100">';
				 result = result +'<div class="project_side_wrap_inn_tabs">';
			     result = result +'   <ul class="project-tabs-menu">';
			     result = result +'     <li class="current"><a href="#tab-1" onclick="professionalProfile('+candidateId+');">Profile</a></li>';
			     result = result +'     <li><a href="#tab-2" onclick="candidateResumesList('+candidateId+'); candidateResume('+candidateId+');">Resume</a></li>';
			     result = result +'     <li><a href="#tab-3" onclick="professionalCandidateEmployers('+candidateId+');">Employers</a></li>';
			     result = result +'     <li><a href="#tab-4" onclick="professionalCandidateEducation('+candidateId+');">Education</a></li>';
			     result = result +'     <li><a href="#tab-5" onclick="professionalCandidateCertificate('+candidateId+');">Certifications</a></li>';
			     result = result+'<li><a href="#tab-6" onclick="professionalCandidateDocuments('+candidateId+');">Documents</a></li>';
			     result = result+'<li><a href="#tab-7" onclick="professionalCandidateReferences('+candidateId+');">References</a></li>';
			     
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
				 }
				 $("#professionalInfoDiv").empty();
				 $("#professionalInfoDiv").append(result); 
				 professionalProfile(candidateId);
		    },
	});
            
}

</script>

<jsp:include page="updateProfessionalProfile.jsp"></jsp:include>
<jsp:include page="professionalCandidatesProfile.jsp"></jsp:include>
<jsp:include page="professionalCandidateResumes.jsp"></jsp:include>
<jsp:include page="professionalCandidateEmployers.jsp"></jsp:include>
<jsp:include page="professionalCandidateEducation.jsp"></jsp:include>
<jsp:include page="professionalCandidateCertificates.jsp"></jsp:include>
<jsp:include page="professionalCandidateDocuments.jsp"></jsp:include>
<jsp:include page="professionalCandidateReferences.jsp"></jsp:include>
<jsp:include page="../masterFooter.jsp" />