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
	
	var jobId = "";
	if(data.jobId != null && data.jobId != '' && data.jobId != 'undefiend'){
		jobId = data.jobId + " - ";
    }
	var rate = "";
	if((data.jobRate != null && data.jobRate != '' && data.jobRate != 'undefiend') && (data.jobRatePer != null && data.jobRatePer != '' && data.jobRatePer != 'undefiend')){
		rate = data.jobRate +' / '+data.jobRatePer;
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
	
	var result = '';
    result = result +'<div class="comMask" style="display: block;"></div>';
    result = result +'<div class="compare_resume">';
    result = result +'    <a onclick="closeCompare();" class="close_btn text-danger pull-right btn btn-danger btn-sm">X</a>';
    
    result = result +'<div class="profilecompareLeft">';
    result = result +'<div class="listHeadr clearfix">';
    result = result +'<h2>Candidate Compare</h2>';
    result = result +'<h3><i class="fa fa-briefcase"></i>';
    result = result +''+jobId+''+data.jobTitile+'</h3>';
    result = result +'</div>';
    result = result +'<div class="resumeFields">';
    result = result +'<ul>';
    result = result +'<li><label>Score : </label><span>100%</span></li>';
	result = result +'<li><label>Rate : </label><span>'+rate+'</span></li>';
	result = result +'<li><label>Experience : </label><span>'+minexp+''+maxexp+'Year(s)</span></li>';
	result = result +'<li><label>Notice Period : </label><span>'+jobNoticePeriod+'</span></li>';
	result = result +'<li class="MultLi"><label>Location : </label><span>'+jobLocation+'</span></li>';
	result = result +'<li class="MultLi"><label>Education : </label><span>'+jobEducation+'</span></li>';
	result = result +'<li class="MultLi"><label>Visa : </label><span>'+jobVisa+'</span></li>';
	result = result +'<li class="MultLi"><label>Skills : </label><span>'+jobSkills+'</span></li>';
    result = result +'</ul>';
    result = result +'</div>';
    result = result +'</div>';
    
    
    result = result +'     <div class="profilecompare-view">';
    
    result = result +'<div class="profilecompare-widget">';
    result = result +'<div id="loading1"></div>';
    result = result +'<div class="clearfix">';
    result = result +'<select class="chosen-select form-control" id="compareCandidateList1" onchange="getCandidatetoCompare(1,'+requirementId+');">';
	result = result +'</select>';
	result = result +'</div>';
	result = result +'<div id="profileDiv1"></div>';
	result = result +'</div>';
    
    result = result +'<div class="profilecompare-widget">';
    result = result +'<div id="loading2"></div>';
    result = result +'<div class="clearfix">';
    result = result +'<select class="chosen-select form-control" id="compareCandidateList2" onchange="getCandidatetoCompare(2,'+requirementId+');">';
	result = result +'</select>';
	result = result +'</div>';
	result = result +'<div id="profileDiv2"></div>';
	result = result +'</div>';
    
    result = result +'<div class="profilecompare-widget">';
    result = result +'<div id="loading3"></div>';
    result = result +'<div class="clearfix">';
    result = result +'<select class="chosen-select form-control" id="compareCandidateList3" onchange="getCandidatetoCompare(3,'+requirementId+');">';
	result = result +'</select>';
	result = result +'</div>';
	result = result +'<div id="profileDiv3"></div>';
	result = result +'</div>';
    
    result = result +'<div class="profilecompare-widget">';
    result = result +'<div id="loading4"></div>';
    result = result +'<div class="clearfix">';
    result = result +'<select class="chosen-select form-control" id="compareCandidateList4" onchange="getCandidatetoCompare(4,'+requirementId+');">';
	result = result +'</select>';
	result = result +'</div>';
	result = result +'<div id="profileDiv4"></div>';
	result = result +'</div>';
    
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
				    	skillsName = minSkills;
				    }
				    
				    var email = "";
				    if(report.email != null && report.email !='' && report.email !='undefined'){
				    	email = report.email;
				    }
				    var mobile = "";
				    if(report.mobilePhone != null && report.mobilePhone !='' && report.mobilePhone !='undefined'){
				    	mobile = report.mobilePhone;
				    }
				    
				    
				    var opt = "";
				    
				    opt = opt +'<div class="profilecompare-widget_hdr clearfix">';
				    opt = opt +'<div class="profileImg">';
				    opt = opt +'<img  id="userImage" onerror="predefineProfileImage(this);" src="<c:url value="/displayCandidateImage/'+report.candidateId+'"/>" alt="Profile Image" class="media-object" >';
				    if(report.finalScore >= 50){
				    	opt = opt +'<i class="valid-profile fa fa-check-square text-success"></i>';
				    }
				    opt = opt +'</div>';
				    opt = opt +'<div class="comUseDeta">';
				    opt = opt +'<p>';
				    opt = opt +'<strong>'+firstName+' '+lastName+'</strong>';
				    opt = opt +'</p>';
				    
					opt = opt+' <div class="progress compProg">  ';
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
		
				    opt = opt +'<p><i class="fa fa-briefcase" title="Primary Skill"></i><span>'+primarySkill+'</span></p>';
				    opt = opt +'<p><i class="fa fa-envelope" title="Email"></i><span>'+email+'</span></p>';
				    opt = opt +'<p><i class="fa fa-phone-square" title="Mobile"></i><span>'+mobile+'</span></p>';
				    opt = opt +'</div>';
				    opt = opt +'</div>';
				    
		    	    opt = opt +'<!-- Profile Header -->';
		    	    opt = opt +'<!-- profile_personalInfo -->';
		    	    opt = opt +'<div class="profile_personalInfo">';
		    	    opt = opt +'<ul class="list-unstyled clearfix">';
		    	    opt = opt +'<li>'+report.finalScore+'%</li>';
    	    		opt = opt +'<li>'+rate+'</li>';
    	    		opt = opt +'<li>'+totalExp+'</li>';
    	    		opt = opt +'<li>'+noticePriod+'</li>';
    	    		opt = opt +'<li class="MultLi">'+location+'</li>';
    	    		opt = opt +'<li class="MultLi">'+candieducation+'</li>';
    	    		opt = opt +'<li class="MultLi">'+candivisaType+'</li>';
    	    		opt = opt +'<li class="MultLi">'+skillsName+'</li>';
	    	    	opt = opt +'</ul>';
	    	    	opt = opt +'<div class="comBtns mt10">';
	    	    	if(report.currentResumeUUID != null && report.currentResumeUUID != '' && report.currentResumeUUID != 'undefined'){
	    	    		opt = opt +'<button type="button" onClick="getCandiResume(\''+report.currentResumeUUID+'\',\''+report.currentResumeName+'\');" class="btn btn-sm btn-primary">View Resume</button>';
	    	    	}
	    	    	opt = opt +'<button type="button" onClick="submitRecomendedApplicant('+requirementId+','+report.candidateId+',\'compare\');" class="btn btn-sm btn-success">Add</button>';
	    	    	opt = opt +'</div>';
		    	    opt = opt +'</div>';
		    	    
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
		$("#loading1").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	}
	if(div == 2){
		candidateId = $("#compareCandidateList2").val();
		$("#loading2").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	}
	if(div == 3){
		candidateId = $("#compareCandidateList3").val();
		$("#loading3").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	}
	if(div == 4){
		candidateId = $("#compareCandidateList4").val();
		$("#loading4").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
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
	    	 firstName = "NA";
	    } 
	   	
	   	var lastName = "";
	    if(report.lastName != null && report.lastName != '' && report.lastName != 'undefiend'){
	    	lastName = report.lastName;
	    }else{
	    	lastName = "NA";
	    } 
	    
	   	var primarySkill = "";
	    if(report.primarySkill != null && report.primarySkill != '' && report.primarySkill != 'undefiend'){
	    	primarySkill = report.primarySkill;
	    }else{
	    	primarySkill = "NA";
	    }
	    
	   	var rate ="";
	    if((report.expectedRate != null && report.expectedRate != '' && report.expectedRate != 'undefiend') && (report.rateType != null && report.rateType != '' && report.rateType != 'undefiend')){
	    	rate = report.expectedRate + ' ' + report.rateType;
	    }else if(report.expectedRate != null && report.expectedRate != '' && report.expectedRate != 'undefiend'){
	    	rate = report.expectedRate;
	    }else{
	    	rate = "NA";
	    }
	   
	    var totalExp = "";
	    if((report.experienceYears != null && report.experienceYears != '' && report.experienceYears != 'undefiend') && (report.experienceMonths != null && report.experienceMonths != '' && report.experienceMonths != 'undefiend')){
	    	totalExp = report.experienceYears + "."+report.experienceMonths+ " Year(s)";
	    }else if(report.experienceYears != null && report.experienceYears != '' && report.experienceYears != 'undefiend'){
	    	totalExp = report.experienceYears + " Year(s)";
	    }else if(report.experienceMonths != null && report.experienceMonths != '' && report.experienceMonths != 'undefiend'){
	    	totalExp = report.experienceMonths + " Months";
	    }else{
	    	totalExp ="NA";
	    } 
	    
	   	var location ="";
	    if(report.location != null && report.location != '' && report.location != 'undefiend'){
	    	location = report.location;
	    }else{
	    	location = "NA";
	    } 
	    
	   	var noticePriod ="";
	    if(report.noticePeriod != null && report.noticePeriod != '' && report.noticePeriod != 'undefiend'){
	    	noticePriod = report.noticePeriod;
	    }else{
	    	noticePriod = "NA";
	    }
	    
	   	var candieducation = "";
	    if(report.candidateEdu != null && report.candidateEdu != '' && report.candidateEdu != 'undefiend'){
	    	var edu = report.candidateEdu;
	    	var keyword = edu.toString();
    	  	var educ = keyword.split(",");
    	  	var minEdu = educ.slice(0,2);
    	  	candieducation = minEdu; 
	    }else{
	    	candieducation = "NA";
	    } 
	    
	   	var candivisaType = "";
	    if(report.visaType != null && report.visaType != '' && report.visaType != 'undefiend'){
	    	var visa = report.visaType;
	    	var keyword = visa.toString();
    	  	var visaType = keyword.split(",");
    	  	var minvisa = visaType.slice(0,2);
    	  	candivisaType = minvisa; 
	    }else{
	    	candivisaType = "NA";
	    } 
	    
	   	var skillsName = "";
	    if(report.skillsName != null && report.skillsName != '' && report.skillsName != 'undefiend'){
	    	
	    	var skill = report.skillsName;
	 		var res = [];
	 		res = skill.split(',');
	 		var minSkills = res.slice(0,5);
	    	skillsName = minSkills;
	    }else{
	    	skillsName = "NA";
	    } 
	    
	    var email = "";
	    if(report.email != null && report.email !='' && report.email !='undefined'){
	    	email = report.email;
	    }else{
	    	email = "NA";
	    } 
	    
	    var mobile = "";
	    if(report.mobilePhone != null && report.mobilePhone !='' && report.mobilePhone !='undefined'){
	    	mobile = report.mobilePhone;
	    }else{
	    	mobile = "NA";
	    } 
	   
	   var opt = "";
	   opt = opt +'<div class="profilecompare-widget_hdr clearfix">';
	   opt = opt +'<div class="profileImg">';
	   opt = opt +'<img  id="userImage" onerror="predefineProfileImage(this);" src="<c:url value="/displayCandidateImage/'+report.id+'"/>" alt="Profile Image" class="media-object" >';
	   if(report.candidateScore != null && report.candidateScore !='' && report.candidateScore !='undefined'){
		   if(report.candidateScore >= 50){
			   opt = opt +'<i class="valid-profile fa fa-check-square text-success"></i>';
		   }
	   }
	   
	   opt = opt +'</div>';
	   opt = opt +'<div class="comUseDeta">';
	   opt = opt +'<p>';
	   opt = opt +'<strong>'+firstName+' '+lastName+'</strong>';
	   opt = opt +'</p>';
	   
		opt = opt+' <div class="progress compProg">  ';
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
		
	   opt = opt +'<p><i class="fa fa-briefcase" title="Primary Skill"></i><span>'+primarySkill+'</span></p>';
	   opt = opt +'<p><i class="fa fa-envelope" title="Email"></i><span>'+email+'</span></p>';
	   opt = opt +'<p><i class="fa fa-phone-square" title="Mobile"></i><span>'+mobile+'</span></p>';
	   opt = opt +'</div>';
	   opt = opt +'</div>';
	   
	   opt = opt +'<!-- profile_personalInfo -->';
	   opt = opt +'<div class="profile_personalInfo">';
	   opt = opt +'<ul class="list-unstyled clearfix">';
	   if(report.candidateScore != null && report.candidateScore !='' && report.candidateScore !='undefined'){
	   		opt = opt +'                <li>'+parseFloat(report.candidateScore)+'%</li>';
	   	 }else{
	   		opt = opt +'                <li>0%</li>';
	   	 }
	   opt = opt +'<li>'+rate+'</li>';
	   opt = opt +'<li>'+totalExp+'</li>';
	   opt = opt +'<li>'+noticePriod+'</li>';
	   opt = opt +'<li class="MultLi">'+location+'</li>';
	   opt = opt +'<li class="MultLi">'+candieducation+'</li>';
	   opt = opt +'<li class="MultLi">'+candivisaType+'</li>';
	   opt = opt +'<li class="MultLi">'+skillsName+'</li>';
	   opt = opt +'</ul>';
	   opt = opt +'<div class="comBtns mt10">';
	   if(report.resumeUUID != null && report.resumeUUID != '' && report.resumeUUID != 'undefined'){
		   opt = opt +'<button type="button" onClick="getCandiResume(\''+report.resumeUUID+'\',\''+report.candidateResumeName+'\');" class="btn btn-sm btn-primary">View Resume</button>';   
	   }
	   opt = opt +'<button type="button" onClick="submitRecomendedApplicant('+requirementId+','+report.id+',\'compare\');" class="btn btn-sm btn-success">Add</button>';
	   opt = opt +'</div>';
	   opt = opt +'</div>';
	   opt = opt +'<!-- profile_personalInfo -->';
	   
   	    if(div==1){
   	    	$("#profileDiv1").empty();
   	    	$("#profileDiv1").append(opt);
   	    	$("#loading1").removeAttr('style');
   	    }
   	    if(div==2){
   	    	$("#profileDiv2").empty();
   	    	$("#profileDiv2").append(opt);
   	    	$("#loading2").removeAttr('style');
   	    }
   	    if(div==3){
   	    	$("#profileDiv3").empty();
   	    	$("#profileDiv3").append(opt);
   	    	$("#loading3").removeAttr('style');
   	    }
   	    if(div==4){
   	    	$("#profileDiv4").empty();
   	    	$("#profileDiv4").append(opt);
   	    	$("#loading4").removeAttr('style');
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
