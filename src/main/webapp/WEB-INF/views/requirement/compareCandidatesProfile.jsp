<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- these files for autocomplete with tagit -->
<link href="resources/css/jquery-tagit.css" rel="stylesheet">
<script src="resources/js/csc.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/tag-it.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/tag-it.min.js" type="text/javascript" charset="utf-8"></script>
<!-- end files for autocomplete with tagit -->

<style>
.new_side_wrap {
	z-index:100000 !important; 
}
.side_mask {
z-index:99999 !important;
}

</style>

<script>
function compareRecommendedUsers(requirementId){
	var sourceleadUrl = '${sourceleadUrl}';
	var locationId = '${SourceLeadWeb_orgLocationId}';
	 $.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getCandidatesToCompare?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId+"&orgLocationId="+locationId,
	        success: function(data) {
	        	compareRecommendedUsersdiv(data,requirementId);
	        }
	 });
	
}
</script>


<script>
function compareRecommendedUsersdiv(data,requirementId){
	var sourceleadUrl = '${sourceleadUrl}';
	var result = '';
    result = result +'<div class="comMask" style="display: block;"></div>';
    result = result +'<div class="compare_resume">';
    result = result +'    <a onclick="closeCompare();" class="close_btn text-danger pull-right btn btn-danger btn-sm ComClos">X</a>';
    result = result +'    <div class="row">';
    result = result +'        <div class="col-md-2" id="compareJobDiv">';
    result = result +'                    <h4 class="text-primary">Candidate Compare</h4>';
    result = result +'            <div class="listHeadr text-center clearfix">';
    result = result +'                <div class="form-group clearfix mt40">';
    result = result +'                    <label>'+data.jobTitile+'</label>';
    result = result +'                </div>';
    result = result +'                <div class="form-group clearfix">';
    if(data.jobId != null && data.jobId != '' && data.jobId != 'undefiend'){
    	result = result +'                    <label>'+data.jobId+'</label>';
    }else{
    	result = result +'                    <label>---</label>';
    }
    
    result = result +'                </div>';
    result = result +'            </div>';
    result = result +'                <div class="resumeFields">';
    result = result +'                <ul class="list-unstyled">';
    
    if((data.jobRate != null && data.jobRate != '' && data.jobRate != 'undefiend') && (data.jobRatePer != null && data.jobRatePer != '' && data.jobRatePer != 'undefiend')){
    	result = result +'                    <li><i class="fa fa-usd" title="Rate / Salary"></i><span class="resumeValues">'+data.jobRate+' / '+data.jobRatePer+'</span></li>';
    }else{
    	result = result +'                    <li><i class="fa fa-usd" title="Rate / Salary"></i><span class="resumeValues">---</span></li>';
    }
    
    var minexp = "";
    var maxexp = "";
    if(data.jobMinExp != null && data.jobMinExp != '' && data.jobMinExp != 'undefiend'){
    	minexp = data.jobMinExp;
    }
    
    if(data.jobMaxExp != null && data.jobMaxExp != '' && data.jobMaxExp != 'undefiend'){
    	maxexp = " to "+data.jobMaxExp;
    }
    
    var jobLocation = "";
    if(data.jobLocation != null && data.jobLocation != '' && data.jobLocation != 'undefiend'){
    	jobLocation = data.jobLocation;
    }else{
    	jobLocation = "---";
    }
    
    var jobNoticePeriod = "";
    if(data.jobNoticePeriod != null && data.jobNoticePeriod != '' && data.jobNoticePeriod != 'undefiend'){
    	jobNoticePeriod = data.jobNoticePeriod;
    }else{
    	jobNoticePeriod = "---";
    }
    
    var jobEducation = "";
    if(data.jobEducation != null && data.jobEducation != '' && data.jobEducation != 'undefiend'){
    	jobEducation = data.jobEducation;
    }else{
    	jobEducation = "---";
    }
    
    var jobVisa = "";
    if(data.jobVisa != null && data.jobVisa != '' && data.jobVisa != 'undefiend'){
    	
 		var visa = data.jobVisa;
 		var res = [];
 		res = visa.split(',');
 		var minvisa = res.slice(0,2);
    	
 		for(i=0; i<minvisa.length;i++){
 			if(i==0){
 				jobVisa = jobVisa + res[i];
 			}else{
 				jobVisa = jobVisa + ", " + res[i];
 			}
 		}
    }
    
    var jobSkills = "";
    if(data.jobSkills != null && data.jobSkills != '' && data.jobSkills != 'undefiend'){
    	
    	var skill = data.jobSkills;
 		var res = [];
 		res = skill.split(',');
 		var minSkills = res.slice(0,5);
 		
 		for(i=0; i<minSkills.length;i++){
 			if(i==0){
 				jobSkills = jobSkills + res[i];
 			}else{
 				jobSkills = jobSkills + ", " + res[i];
 			}
 		}
    }
    
    result = result +'                    <li><i class="fa fa-briefcase" title="Experience"></i><span class="resumeValues">100%</span></li>';
   	result = result +'                    <li><i class="fa fa-briefcase" title="Experience"></i><span class="resumeValues">'+minexp+''+maxexp+'</span></li>';
    
    result = result +'                    <li><i class="fa fa-map-marker" title="Location"></i><span class="resumeValues">'+jobLocation+'</span></li>';
    result = result +'                    <li><i class="fa fa-calendar" title="Notice Period"></i><span class="resumeValues">'+jobNoticePeriod+'</span></li>';
    result = result +'                    <li><i class="fa fa-graduation-cap" title="Notice Period"></i><span class="resumeValues">'+jobEducation+'</span></li>';
    result = result +'                    <li><i class="fa fa-cc-visa" title="Visa"></i><span class="resumeValues">'+jobVisa+'</span></li>';
    result = result +'                    <li><i class="fa fa-code" title="Skills"></i><span class="resumeValues">'+jobSkills+'</span></li>';
    result = result +'                </ul>';
    result = result +'            </div>';
    result = result +'        </div>';
    
    result = result +'     <div class="profilecompare-view col-md-10">';
    result = result +'         <div class="row">';
    result = result +'               <!-- Profile view -->';
    
    
    result = result +'                <div class="profilecompare-widget col-md-3">';
    result = result +'                <!-- Profile Header -->';
    result = result +'                    <div class="clearfix mb10">';
    result = result +'                        <select class="chosen-select form-control" id="compareCandidateList1" onchange="getCandidatetoCompare(1,'+requirementId+');">';
    result = result +'                        </select>';
    result = result +'                    </div>';
    result = result +'                   <div id="profileDiv1"></div>';
    result = result +'                </div>';
    
    
    result = result +'                <div class="profilecompare-widget col-md-3">';
    result = result +'                <!-- Profile Header -->';
    result = result +'                    <div class="clearfix mb10">';
    result = result +'                        <select class="chosen-select form-control" id="compareCandidateList2" onchange="getCandidatetoCompare(2,'+requirementId+');">';
    result = result +'                        </select>';
    result = result +'                    </div>';
    result = result +'                   <div id="profileDiv2"></div>';
    result = result +'                </div>';
    
    
    result = result +'                <div class="profilecompare-widget col-md-3">';
    result = result +'                    <div class="clearfix mb10">';
    result = result +'                        <select class="chosen-select form-control" id="compareCandidateList3" onchange="getCandidatetoCompare(3,'+requirementId+');">';
    result = result +'                        </select>';
    result = result +'                    </div>';
    result = result +'                   <div id="profileDiv3"></div>';
    result = result +'                </div>';
    
    
    result = result +'                <div class="profilecompare-widget col-md-3">';
    result = result +'                    <div class="clearfix mb10">';
    result = result +'                        <select class="chosen-select form-control" id="compareCandidateList4" onchange="getCandidatetoCompare(4,'+requirementId+');">';
    result = result +'                        </select>';
    result = result +'                    </div>';
    result = result +'                   <div id="profileDiv4"></div>';
    result = result +'                </div>';
    
   
    
    result = result +'        </div>';
    result = result +'    </div>';
    result = result +'</div></div>';
    
	$("#candidateCompare").empty();
	$("#candidateCompare").append(result);
	prepareCandidateGrid(data,requirementId);
	prepareCompareCandidateList(data.candidateList);
	
	$("#compareCandidateList1").chosen();
	$("#compareCandidateList2").chosen();
	$("#compareCandidateList3").chosen();
	$("#compareCandidateList4").chosen();
	$("#compareCandidateList1").trigger('chosen:update');
	$("#compareCandidateList2").trigger('chosen:update');
	$("#compareCandidateList3").trigger('chosen:update');
	$("#compareCandidateList4").trigger('chosen:update');
	}
</script>

<script>
function prepareCompareCandidateList(candidateList){
	var output = "";
    output = output + "<option value=''>--Select Candidate--</option>"; 
    	for (var i=0; i<candidateList.length; i++){
    		var opt = candidateList[i];
    		if(opt.isCandidateSubmitted == 'N'){
    			var firstName ="";
    	    	if(opt.firstName != null && opt.firstName != '' && opt.firstName != 'undefiend'){
    		    	 firstName = opt.firstName;
    		    } 
    	    	var lastName = "";
    		    if(opt.lastName != null && opt.lastName != '' && opt.lastName != 'undefiend'){
    		    	lastName = opt.lastName;
    		    }
       			output = output + "<option value='" +opt.candidateId+ "'>"+firstName+" "+lastName+"</option>";
    		}
		}
    	
    	$("#compareCandidateList1").empty();
    	$("#compareCandidateList2").empty();
    	$("#compareCandidateList3").empty();
    	$("#compareCandidateList4").empty();
    	$("#compareCandidateList1").append(output);
    	$("#compareCandidateList2").append(output);
    	$("#compareCandidateList3").append(output);
    	$("#compareCandidateList4").append(output);
    	
    	
}
</script>

<script>
function prepareCandidateGrid(data,requirementId){
	var count = 0;
	 for(i = 0; i<data.candidateList.length; i++){
		 if(count < 4){
			 report = data.candidateList[i];
		    	if(report.isCandidateSubmitted == 'N'){
		    		count++;
		    		var firstName ="";
			    	if(report.firstName != null && report.firstName != '' && report.firstName != 'undefiend'){
				    	 firstName = report.firstName;
				    }else{
				    	 firstName = "---";
				    } 
			    	var lastName = "";
				    if(report.lastName != null && report.lastName != '' && report.lastName != 'undefiend'){
				    	lastName = report.lastName;
				    }else{
				    	lastName = "---";
				    } 
			    	var primarySkill = "";
				    if(report.primarySkill != null && report.primarySkill != '' && report.primarySkill != 'undefiend'){
				    	primarySkill = report.primarySkill;
				    }else{
				    	primarySkill = "---";
				    }
			    	var rate ="";
				    if((report.expectedRate != null && report.expectedRate != '' && report.expectedRate != 'undefiend') && (report.rateType != null && report.rateType != '' && report.rateType != 'undefiend')){
				    	rate = report.expectedRate + ' ' + report.rateType;
				    }else if(report.expectedRate != null && report.expectedRate != '' && report.expectedRate != 'undefiend'){
				    	rate = report.expectedRate;
				    }else{
				    	rate = "---";
				    }
				    var totalExp = "";
				    if((report.experienceYears != null && report.experienceYears != '' && report.experienceYears != 'undefiend') && (report.experienceMonths != null && report.experienceMonths != '' && report.experienceMonths != 'undefiend')){
				    	totalExp = report.experienceYears + "." + report.experienceMonths + " Year(s)";
				    }else if(report.experienceYears != null && report.experienceYears != '' && report.experienceYears != 'undefiend'){
				    	totalExp = report.experienceYears + " Year(s)";
				    }else{
				    	totalExp = "---";
				    }
			    	var location ="";
				    if(report.location != null && report.location != '' && report.location != 'undefiend'){
				    	location = report.location;
				    }else{
				    	location = "---";
				    } 
			    	var noticePriod ="";
				    if(report.noticePriod != null && report.noticePriod != '' && report.noticePriod != 'undefiend'){
				    	noticePriod = report.noticePriod;
				    }else{
				    	noticePriod = "---";
				    }
			    	var candieducation = "---";
				    if(report.education != null && report.education != '' && report.education != 'undefiend'){
				    	var edu = report.education;
				    	var keyword = edu.toString();
			    	  	var educ = keyword.split(",");
			    	  	var minEdu = educ.slice(0,2);
			    	  	candieducation = minEdu; 
				    } 
			    	var candivisaType = "---";
				    if(report.visaType != null && report.visaType != '' && report.visaType != 'undefiend'){
				    	var visa = report.visaType;
				    	var keyword = visa.toString();
			    	  	var visaType = keyword.split(",");
			    	  	var minvisa = visaType.slice(0,2);
			    	  	candivisaType = minvisa; 
				    }
			    	
			    	var skillsName = "";
				    if(report.skillsName != null && report.skillsName != '' && report.skillsName != 'undefiend'){
				    	
				    	var skill = report.skillsName;
				 		var res = [];
				 		res = skill.split(',');
				 		var minSkills = res.slice(0,5);
				    	/* alert(minSkills.length)
				    	var cvSkills = "";
				    	for(i = 0; i<minSkills.length ; i++){
				    		cvSkills += minSkills[i] + ", ";
				    	}
				    	alert(cvSkills) */
				    	skillsName = minSkills;
				    }
				    
				   var opt = "";
			    	 	opt = opt +'                <div class="profilecompare-widget_hdr clearfix">';
			    	    opt = opt +'                <div class="profileImg text-center clearfix mt10 positionRelative">';
			    	    opt = opt+'<img  id="userImage" onerror="predefineProfileImage(this);" src="<c:url value="/displayCandidateImage/'+report.candidateId+'"/>" alt="Profile Image" class="media-object" style="width:65px">';
// 			     	    opt = opt +'                    <img src="resources/images/userprofileImg.jpg" alt="Profile Image">';
			    	    opt = opt +'                    <i class="valid-profile fa fa-check-square text-success"></i>';
			    	    opt = opt +'                </div>';
			    	    
			    	    
			    	    opt = opt+'  <div class="mb10 UsProBar CenBarCom">';                                                                                                                                        
						opt = opt+' <div class="progress nomr">  ';
						if(report.profilePercentage>=50){ 
							opt = opt+'   <div class="progress-bar  progress-bar-success  ';
						}
						else{
							opt = opt+'   <div class="progress-bar  progress-bar-danger  ';
						}
						opt = opt+' progress-bar-striped active" role="progressbar" id="perVal" aria-valuenow="'+report.profilePercentage+'" aria-valuemin="0" aria-valuemax="100" style="width:'+report.profilePercentage+'%">  ';
						opt = opt+'     <d id="disVal">'+report.profilePercentage+'%</d>                                                                                                                                                                      ';
						opt = opt+'   </div>  ';
						opt = opt+' </div>                 ';                                                                                                                                                      
						opt = opt+' </div> ';		    	    
			    	    
			    	    

			    	    /* opt = opt +'                     <!-- Progress Bar -->';
			    	    opt = opt +'            <div class="progress">';
			    	    opt = opt +'            <div class="progress-bar progress-bar-striped active" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width:80%">';
			    	    opt = opt +'            80%';
			    	    opt = opt +'            </div>';
			    	    opt = opt +'            </div> ';
			    	    opt = opt +'                     <!-- Progress Bar -->';
			    	    opt = opt +'                 </div>'; */

			    	    
			    	    
			    	    
			    	    opt = opt +'                <div class="text-center clearfix">';
			    	    opt = opt +'                <p>';
			    	    opt = opt +'                    <span>';
			    	    opt = opt +'                    <strong>'+firstName+' '+lastName+'</strong>';
			    	    opt = opt +'                    </span>';
			    	    opt = opt +'                </p>';
			    	    opt = opt +'                <p>Primary:<span class="paddingLeft">'+primarySkill+'</span></p>';
			    	    if(report.email != null && report.email !='' && report.email !='undefined'){
			    	    	opt = opt +'                <p>Email:<span class="paddingLeft">'+report.email+'</span></p>';
			    	    }else{
			    	    	opt = opt +'                <p>Email:<span class="paddingLeft">---</span></p>';
			    	    }
			    	    if(report.mobilePhone != null && report.mobilePhone !='' && report.mobilePhone !='undefined'){
			    	    	opt = opt +'                <p>Ph:<span class="paddingLeft">'+report.mobilePhone+'</span></p>';
			    	    }else{
			    	    	opt = opt +'                <p>Ph:<span class="paddingLeft">---</span></p>';
			    	    }
			    	    opt = opt +'                </div>';
			    	    opt = opt +'                </div>';
			    	    opt = opt +'                <!-- Profile Header -->';
			    	    opt = opt +'                <!-- profile_personalInfo -->';
			    	    opt = opt +'                <div class="profile_personalInfo">';
			    	    opt = opt +'                <ul class="list-unstyled mb clearfix">';
			    	    opt = opt +'                <li>'+report.finalScore+'%</li>';
			    	    opt = opt +'                <li>'+rate+'</li>';
			    	    opt = opt +'                <li>'+totalExp+'</li>';
			    	    opt = opt +'                <li>'+location+'</li>';
			    	    opt = opt +'                <li>'+noticePriod+'</li>';
			    	    opt = opt +'                <li>'+candieducation+'</li>';
			    	    opt = opt +'                <li>'+candivisaType+'</li>';
			    	    opt = opt +'                <li>'+skillsName+'</li>';	    	    
			    	    opt = opt +'                </ul>';
			    	    opt = opt +'                <div class="clearfix"></div>';	
			    	    opt = opt +'                <div class="mt10 comBtns"><button type="button" onClick="getCandiResume(\''+report.currentResumeUUID+'\',\''+report.currentResumeName+'\');" class="btn btn-primary btn-sm">View Resume</button>';
			    	    opt = opt +'                <button type="button" class="btn btn-success btn-sm " onClick="submitRecomendedApplicant('+requirementId+','+report.candidateId+');">Add</button></div>';
			    	    opt = opt +'                </div>';
			    	    opt = opt +'                <!-- profile_personalInfo -->';
			    	    if(count==1){
			    	    	$("#profileDiv1").empty();
			    	    	$("#profileDiv1").append(opt);
			    	    }
			    	    if(count==2){
			    	    	$("#profileDiv2").empty();
			    	    	$("#profileDiv2").append(opt);
			    	    }
			    	    if(count==3){
			    	    	$("#profileDiv3").empty();
			    	    	$("#profileDiv3").append(opt);
			    	    }
			    	    if(count==4){
			    	    	$("#profileDiv4").empty();
			    	    	$("#profileDiv4").append(opt);
			    	    }
		    	}
		 }else{
			 break;
		 }
	    }
}
</script>

<script>
function getCandidatetoCompare(div,requirementId){
	var sourceleadUrl = '${sourceleadUrl}';
	var candidateId = "";
	if(div == 1){
		candidateId = $("#compareCandidateList1").val();
	}
	if(div == 2){
		candidateId = $("#compareCandidateList2").val();
	}
	if(div == 3){
		candidateId = $("#compareCandidateList3").val();
	}
	if(div == 4){
		candidateId = $("#compareCandidateList4").val();
	}
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	 $.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getCandidateDetails?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId+"&orgLocationId="+orgLocationId+"&requirementId="+requirementId, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	candidateDiv(json,div,requirementId);
	        }
	 });
}
</script>

<script>
function candidateDiv(report,div,requirementId){
  		var firstName ="";
	   	if(report.firstName != null && report.firstName != '' && report.firstName != 'undefiend'){
	    	 firstName = report.firstName;
	    }else{
	    	 firstName = "---";
	    } 
	   	
	   	var lastName = "";
	    if(report.lastName != null && report.lastName != '' && report.lastName != 'undefiend'){
	    	lastName = report.lastName;
	    }else{
	    	lastName = "---";
	    } 
	   	
	   	var primarySkill = "";
	    if(report.primarySkill != null && report.primarySkill != '' && report.primarySkill != 'undefiend'){
	    	primarySkill = report.primarySkill;
	    }else{
	    	primarySkill = "---";
	    }
	   	
	   	var rate ="";
	    if((report.expectedRate != null && report.expectedRate != '' && report.expectedRate != 'undefiend') && (report.rateType != null && report.rateType != '' && report.rateType != 'undefiend')){
	    	rate = report.expectedRate + ' ' + report.rateType;
	    }else if(report.expectedRate != null && report.expectedRate != '' && report.expectedRate != 'undefiend'){
	    	rate = report.expectedRate;
	    }else{
	    	rate = "---";
	    }
	   	
	    if(report.totalExp != null && report.totalExp != '' && report.totalExp != 'undefiend'){
	    	var totalExp = report.totalExp + " Year(s)";
	    }else{
	    	var totalExp = "---";
	    } 
	   	
	   	var location ="";
	    if(report.location != null && report.location != '' && report.location != 'undefiend'){
	    	location = report.location;
	    }else{
	    	location = "---";
	    } 
	   	
	   	var noticePriod ="";
	    if(report.noticePriod != null && report.noticePriod != '' && report.noticePriod != 'undefiend'){
	    	noticePriod = report.noticePriod;
	    }else{
	    	noticePriod = "---";
	    }
	   	
	   	var candieducation = "---";
	    if(report.education != null && report.education != '' && report.education != 'undefiend'){
	    	for(i=0;i<=education.length;i++){
	    		candieducation = candieducation + education[i];
	    	}
	    } 
	   	
	   	var candivisaType = "---";
	    if(report.visaType != null && report.visaType != '' && report.visaType != 'undefiend'){
	    	for(i=0;i<=visaType.size;i++){
	    		candivisaType = candivisaType + visaType[i];
	    	}
	    }
	   	
	   	var skillsName = "";
	    if(report.skillsName != null && report.skillsName != '' && report.skillsName != 'undefiend'){
	    	
	    	var skill = report.skillsName;
	 		var res = [];
	 		res = skill.split(',');
	 		var minSkills = res.slice(0,5);
	    	/* alert(minSkills.length)
	    	var cvSkills = "";
	    	for(i = 0; i<minSkills.length ; i++){
	    		cvSkills += minSkills[i] + ", ";
	    	}
	    	alert(cvSkills) */
	    	skillsName = minSkills;
	    }
	   var opt = "";
	   	 	opt = opt +'                <div class="profilecompare-widget_hdr clearfix">';
	   	    opt = opt +'                <div class="profileImg text-center clearfix mt10 positionRelative">';
	    	    opt = opt +'                    <img src="resources/images/userprofileImg.jpg" alt="Profile Image">';
	   	    opt = opt +'                    <i class="valid-profile fa fa-check-square text-success"></i>';
	   	    opt = opt +'                </div>';
	   	    /* opt = opt +'                 <div class="text-center clearfix col-md-12 profile-progress">';
	   	    opt = opt +'                     <!-- Progress Bar -->';
	   	    opt = opt +'            <div class="progress">';
	   	    opt = opt +'            <div class="progress-bar progress-bar-striped active" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width:80%">';
	   	    opt = opt +'            80%';
	   	    opt = opt +'            </div>';
	   	    opt = opt +'            </div> ';
	   	    opt = opt +'                     <!-- Progress Bar -->';
	   	    opt = opt +'                 </div>'; */
	   	    
	   	 	opt = opt+'  <div class="mb10 UsProBar CenBarCom">';                                                                                                                                        
			opt = opt+' <div class="progress nomr">  ';
			if(report.profilePercentage>=50){ 
				opt = opt+'   <div class="progress-bar  progress-bar-success  ';
			}
			else{
				opt = opt+'   <div class="progress-bar  progress-bar-danger  ';
			}
			opt = opt+' progress-bar-striped active" role="progressbar" id="perVal" aria-valuenow="'+report.profilePercentage+'" aria-valuemin="0" aria-valuemax="100" style="width:'+report.profilePercentage+'%">  ';
			opt = opt+'     <d id="disVal">'+report.profilePercentage+'%</d>                                                                                                                                                                      ';
			opt = opt+'   </div>  ';
			opt = opt+' </div>                 ';                                                                                                                                                      
			opt = opt+' </div> ';
	   	    
	   	    opt = opt +'                <div class="text-center clearfix">';
	   	    opt = opt +'                <p>';
	   	    opt = opt +'                    <span>';
	   	    opt = opt +'                    <strong>'+firstName+' '+lastName+'</strong>';
	   	    opt = opt +'                    </span>';
	   	    opt = opt +'                </p>';
	   	    opt = opt +'                <p>Primary:<span class="paddingLeft">'+primarySkill+'</span></p>';
	   	    if(report.email != null && report.email !='' && report.email !='undefined'){
	   	    	opt = opt +'                <p>Email:<span class="paddingLeft">'+report.email+'</span></p>';
	   	    }else{
	   	    	opt = opt +'                <p>Email:<span class="paddingLeft">---</span></p>';
	   	    }
	   	    if(report.mobilePhone != null && report.mobilePhone !='' && report.mobilePhone !='undefined'){
	   	    	opt = opt +'                <p>Ph:<span class="paddingLeft">'+report.mobilePhone+'</span></p>';
	   	    }else{
	   	    	opt = opt +'                <p>Ph:<span class="paddingLeft">---</span></p>';
	   	    }
	   	    opt = opt +'                </div>';
	   	    opt = opt +'                </div>';
	   	    opt = opt +'                <!-- Profile Header -->';
	   	    opt = opt +'                <!-- profile_personalInfo -->';
	   	    opt = opt +'                <div class="profile_personalInfo">';
	   	    opt = opt +'                <ul class="list-unstyled mb clearfix">';
		   	 if(report.candidateScore != null && report.candidateScore !='' && report.candidateScore !='undefined'){
		   		opt = opt +'                <li>'+report.candidateScore+'%</li>';
		   	 }else{
		   		opt = opt +'                <li>0%</li>';
		   	 }
	   	    opt = opt +'                <li>'+rate+'</li>';
	   	    opt = opt +'                <li>'+totalExp+'</li>';
	   	    opt = opt +'                <li>'+location+'</li>';
	   	    opt = opt +'                <li>'+noticePriod+'</li>';
	   	    opt = opt +'                <li>'+candieducation+'</li>';
	   	    opt = opt +'                <li>'+candivisaType+'</li>';
	   	    opt = opt +'                <li>'+skillsName+'</li>';
	   	    opt = opt +'                </ul>';
	   	 opt = opt +'                <div class="clearfix"></div>';	   	    
 	    opt = opt +'                <div class="mt10 comBtns"><button type="button" onClick="getCandiResume(\''+report.resumeUUID+'\',\''+report.candidateResumeName+'\');" class="btn btn-primary btn-sm">View Resume </button>';
	    opt = opt +'                <button type="button" class="btn btn-success btn-sm " onClick="submitRecomendedApplicant('+requirementId+','+report.id+');">Add</button></div>';
	    opt = opt +'                </div>';
	   	    opt = opt +'                </div>';
	   	    opt = opt +'                <!-- profile_personalInfo -->';
	   	    
	   	    if(div==1){
	   	    	$("#profileDiv1").empty();
	   	    	$("#profileDiv1").append(opt);
	   	    }
	   	    if(div==2){
	   	    	$("#profileDiv2").empty();
	   	    	$("#profileDiv2").append(opt);
	   	    }
	   	    if(div==3){
	   	    	$("#profileDiv3").empty();
	   	    	$("#profileDiv3").append(opt);
	   	    }
	   	    if(div==4){
	   	    	$("#profileDiv4").empty();
	   	    	$("#profileDiv4").append(opt);
	   	    }
}
</script>

 <script>
    function closeCompare(){
         $(".compare_resume, .comMask").fadeOut(200);
    }
    </script>
    
 <script type="text/javascript">
$(function(){
	       var config = {
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			}
	       
	      
	       $('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});
		  })
});
</script>

<script type="text/javascript">
function getCandiResume(uuid,title){
    var sourceleadUrl ='${sourceleadUrl}';
	if(uuid != null && uuid != ""){
		window.open(sourceleadUrl+'/viewResume/'+title+'?resumeUUID='+uuid,title,"height=600,width=600");
	}  
}
</script>
