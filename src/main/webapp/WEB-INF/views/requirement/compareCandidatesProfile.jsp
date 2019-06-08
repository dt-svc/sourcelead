<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style>
.new_side_wrap {
	z-index:100000 !important; 
}
</style>

<script>
function compareRecommendedUsers(requirementId){
	var sourceleadUrl = '${sourceleadUrl}';
	var locationId = '${SourceLeadWeb_orgLocationId}';
	var candidateIds = new Array();
	 for(var e in compareCandidates) {
		 if(!e || !compareCandidates[e] || compareCandidates[e] === "")
			   continue;
		 candidateIds.push(compareCandidates[e]);
	 }
	if(candidateIds.length >= 2 && candidateIds.length <= 4){
		var s = "";
		for(var e in compareCandidates) {
			if(s !== "")
				s += ",";
			s += compareCandidates[e];	
		}
		$("#loading").attr('style','position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+"/getCandidatesToCompare?"+tokenParamName+"="+tokenValue+"&requirementId="+requirementId+"&orgLocationId="+locationId+"&candidatesSelected="+s,
		        success: function(data) {
		        	if (data.response === "FAILED") {
			    		showError(data.responseMessage);
			    	} else if (data.response === "AWKWARD") {
			    		location.href = "/errorPage";
			    	} else if (data.response === "SUCCESS") {
		        		compareRecommendedUsersdiv(data.object,requirementId);
			    	}else if (data.indexOf('Sign In') !== -1) {
			    		location.replace(sourceleadUrl);
			    	} else {
			    		showError("Operation failed, please try again");
			    	}
		        	$("#loading").removeAttr('style');
		        }
		 });
	}else{
		recommendationAlertMessage(candidateIds);
	}
}
</script>

<script>
function recommendationAlertMessage(candidateIds){ 
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>"; 
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	if(candidateIds.length <= 2){
		result = result +"		<p>Please select Minimum 2 Candidates to Compare</p>";
	}else{
		result = result +"		<p>Please select Maximum 4 Candidates to Compare</p>";
	}
	result = result + ' <div id="reasonForInvoiceDiv"/>';
	result = result +'		<b href="javascript:void(0)"><input id="confirm-id" type="button" value="Ok" onclick="cancelTermination();"><input class="u_can_color" type="button"  value="Cancel" onClick="cancelTermination();"></b>';
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}

function cancelTermination(){
	$("#popUp").empty();
	 $(".project_inn_mask").fadeOut(200);
}
</script>


<script>
function compareRecommendedUsersdiv(data,requirementId){
	var sourceleadUrl = '${sourceleadUrl}';
	
	var jobId = "";
	if(data.jobId != null && data.jobId != '' && data.jobId != 'undefined'){
		jobId = data.jobId + " - ";
    }
	var rate = "";
	if((data.jobRate != null && data.jobRate != '' && data.jobRate != 'undefined') && (data.jobRatePer != null && data.jobRatePer != '' && data.jobRatePer != 'undefined')){
		rate = data.jobRate +' / '+data.jobRatePer;
	}
	
	var minexp = "";
    var maxexp = "";
    if(data.jobMinExp != null && data.jobMinExp != '' && data.jobMinExp != 'undefined'){
    	minexp = data.jobMinExp;
    }
    
    if(data.jobMaxExp != null && data.jobMaxExp != '' && data.jobMaxExp != 'undefined'){
    	maxexp = " to "+data.jobMaxExp;
    }
    
    var jobLocation = "";
    if(data.jobLocation != null && data.jobLocation != '' && data.jobLocation != 'undefined'){
    	jobLocation = data.jobLocation;
    }else{
    	jobLocation = "NA";
    }
    
    var jobNoticePeriod = "";
    if(data.jobNoticePeriod != null && data.jobNoticePeriod != '' && data.jobNoticePeriod != 'undefined'){
    	jobNoticePeriod = data.jobNoticePeriod;
    }else{
    	jobNoticePeriod = "NA";
    }

    var jobEducation = "";
    if(data.jobEducation != null && data.jobEducation != '' && data.jobEducation != 'undefined'){
    	jobEducation = data.jobEducation;
    }else{
    	jobEducation = "NA";
    }
    
    var jobVisa = "";
    if(data.jobVisa != null && data.jobVisa != '' && data.jobVisa != 'undefined'){
    	
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
    if(data.jobSkills != null && data.jobSkills != '' && data.jobSkills != 'undefined'){
    	
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
    result = result +'<div class="comMask" style="display: block; z-index:9999;"></div>';
    result = result +'<div class="compare_resume">';
    result = result +'    <a onclick="closeCompare();" class="close_btn danger pull-right btn btn-danger btn-sm">X</a>';
    
    result = result +'<div class="profilecompareLeft">';
    result = result +'<div class="listHeadr clearfix">';
    result = result +'<h2>Candidate Compare</h2>';
    result = result +'<h3><i class="fa fa-briefcase"></i>';
    result = result +''+jobId+''+data.jobTitile+'</h3>';
    result = result +'</div>';
    result = result +'<div class="resumeFields">';
    result = result +'<ul>';
    result = result +'<li><label>Score : </label><span>100%</span></li>';
	result = result +'<li><label>Rate : </label><span title="'+rate+'">'+rate+'</span></li>';
	result = result +'<li><label>Experience : </label><span title="'+minexp+'-'+maxexp+'">'+minexp+''+maxexp+'Year(s)</span></li>';
	result = result +'<li><label>Notice Period : </label><span title="'+jobNoticePeriod+'">'+jobNoticePeriod+'</span></li>';
	result = result +'<li class="locationLi"><label>Location : </label>';
	
	result = result +'<div class="scrollDiv">';
	for(var e in data.jobLocationList) {
		result = result +'<span title="'+data.jobLocationList[e]+'">'+data.jobLocationList[e]+'</span><br/>';
	}
	result = result +'</div>';
	result = result +'</li>';
	result = result +'<li class="MultLi"><label>Education : </label><span title="'+jobEducation+'">'+jobEducation+'</span></li>';
	result = result +'<li class="MultLi"><label>Visa : </label><span title="'+jobVisa+'">'+jobVisa+'</span></li>';
	result = result +'<li class="MultLi"><label>Skills : </label><span title="'+jobSkills+'">'+jobSkills+'</span></li>';
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
	result = result +'<div id="profileDiv1"><span id="noRecords1" class="compNoRecords"></span></div>';
	result = result +'</div>';
    
    result = result +'<div class="profilecompare-widget">';
    result = result +'<div id="loading2"></div>';
    result = result +'<div class="clearfix">';
    result = result +'<select class="chosen-select form-control" id="compareCandidateList2" onchange="getCandidatetoCompare(2,'+requirementId+');">';
	result = result +'</select>';
	result = result +'</div>';
	result = result +'<div id="profileDiv2"><span id="noRecords2" class="compNoRecords"></span></div>';
	result = result +'</div>';
    
    result = result +'<div class="profilecompare-widget">';
    result = result +'<div id="loading3"></div>';
    result = result +'<div class="clearfix">';
    result = result +'<select class="chosen-select form-control" id="compareCandidateList3" onchange="getCandidatetoCompare(3,'+requirementId+');">';
	result = result +'</select>';
	result = result +'</div>';
	result = result +'<div id="profileDiv3"><span id="noRecords3" class="compNoRecords"></span></div>';
	result = result +'</div>';
    
    result = result +'<div class="profilecompare-widget">';
    result = result +'<div id="loading4"></div>';
    result = result +'<div class="clearfix">';
    result = result +'<select class="chosen-select form-control" id="compareCandidateList4" onchange="getCandidatetoCompare(4,'+requirementId+');">';
	result = result +'</select>';
	result = result +'</div>';
	result = result +'<div id="profileDiv4"><span id="noRecords4" class="compNoRecords"></span></div>';
	result = result +'</div>';
    
    result = result +'    </div>';
    result = result +'</div></div>';
    
	$("#candidateCompare").empty();
	$("#candidateCompare").append(result);
	prepareCompareCandidateList();
	prepareCandidateGrid(data,requirementId);
	
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
function prepareCompareCandidateList(){
	var candiList = JSON.parse($("input[name='compareCandListMap']").val());
	var output = "";
    output = output + "<option value=''>--Select Candidate--</option>"; 
      for (var key in candiList) {
		 var value=candiList[key];
		output = output + "<option value='" +key+ "'>"+value+" </option>"; 
	  } 
    
   /*  for(i=0;i<candiList.length;i++){
    	opt = candiList[i];
    	var firstName = "";
    	if(opt.lastName !=null && opt.lastName != '' && opt.lastName != "undefined"){
    		firstName = opt.firstName +""+ opt.lastName ;
    	}else{
    		firstName = opt.firstName;
    	}
    	output = output + "<option value='" +opt.candidateId+ "'>"+firstName+" </option>"; 
    } */
 
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
		    		count++;
		    		var firstName ="";
			    	if(report.firstName != null && report.firstName != '' && report.firstName != 'undefined'){
				    	 firstName = report.firstName;
				    }else{
				    	 firstName = "";
				    } 
			    	var lastName = "";
				    if(report.lastName != null && report.lastName != '' && report.lastName != 'undefined'){
				    	lastName = report.lastName;
				    }else{
				    	lastName = "";
				    } 
			    	var primarySkill = "";
				    if(report.primarySkill != null && report.primarySkill != '' && report.primarySkill != 'undefined'){
				    	primarySkill = report.primarySkill;
				    }else{
				    	primarySkill = "NA";
				    }
			    	var rate ="";
				    if((report.expectedRate != null && report.expectedRate != '' && report.expectedRate != 'undefined') && (report.rateType != null && report.rateType != '' && report.rateType != 'undefined')){
				    	rate = report.expectedRate + ' / ' + report.rateType;
				    }else if(report.expectedRate != null && report.expectedRate != '' && report.expectedRate != 'undefined'){
				    	rate = report.expectedRate;
				    }else{
				    	rate = "NA";
				    }
				    var totalExp = "";
				    if((report.experienceYears != null && report.experienceYears != '' && report.experienceYears != 'undefined') && (report.experienceMonths != null && report.experienceMonths != '' && report.experienceMonths != 'undefined')){
				    	totalExp = report.experienceYears + "." + report.experienceMonths + " Year(s)";
				    }else if(report.experienceYears != null && report.experienceYears != '' && report.experienceYears != 'undefined'){
				    	totalExp = report.experienceYears + " Year(s)";
				    }else{
				    	totalExp = "NA";
				    }
			    	var location ="";
				    if(report.location != null && report.location != '' && report.location != 'undefined'){
				    	location = report.location;
				    }else{
				    	location = "NA";
				    } 
			    	var noticePriod ="";
				    if(report.noticePriod != null && report.noticePriod != '' && report.noticePriod != 'undefined'){
				    	noticePriod = report.noticePriod;
				    }else{
				    	noticePriod = "NA";
				    }
			    	var candieducation = "---";
				    if(report.education != null && report.education != '' && report.education != 'undefined'){
				    	var edu = report.education;
				    	var keyword = edu.toString();
			    	  	var educ = keyword.split(",");
			    	  	var minEdu = educ.slice(0,2);
			    	  	candieducation = minEdu; 
				    } 
			    	var candivisaType = "NA";
				    if(report.visaType != null && report.visaType != '' && report.visaType != 'undefined'){
				    	var visa = report.visaType;
				    	var keyword = visa.toString();
			    	  	var visaType = keyword.split(",");
			    	  	var minvisa = visaType.slice(0,2);
			    	  	candivisaType = minvisa; 
				    }
			    	
			    	var skillsName = "";
				    if(report.skillsName != null && report.skillsName != '' && report.skillsName != 'undefined'){
				    	
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
				    
				    var profilePercentage = '${requirementProfilePercentage}';
				    
				    var opt = "";
				    
				    opt = opt +'<div class="profilecompare-widget_hdr clearfix">';
				    opt = opt +'<div class="profileImg">';
				    opt = opt +'<img  id="userImage" onerror="predefineProfileImage(this);" src="<c:url value="/displayCandidateImage/'+report.candidateId+'"/>" alt="Profile Image" class="media-object" >';
				    if(report.finalScore >= 50){
				    	opt = opt +'<i class="valid-profile fa fa-check-square text-success"></i>';
				    }
				    opt = opt +'</div>';
				    opt = opt +'<div class="comUseDeta">';
				    opt = opt +'<p title="'+firstName+' '+lastName+'">';
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
		
				    opt = opt +'<p><i class="fa fa-briefcase" title="Primary Skill"></i><span title="'+primarySkill+'">'+primarySkill+'</span></p>';
				    opt = opt +'<p><i class="fa fa-envelope" title="Email"></i><span title="'+email+'">'+email+'</span></p>';
				    opt = opt +'<p><i class="fa fa-phone-square" title="Mobile"></i><span title="'+mobile+'">'+mobile+'</span></p>';
				    opt = opt +'</div>';
				    opt = opt +'</div>';
				    
		    	    opt = opt +'<!-- Profile Header -->';
		    	    opt = opt +'<!-- profile_personalInfo -->';
		    	    opt = opt +'<div class="profile_personalInfo">';
		    	    opt = opt +'<ul class="list-unstyled clearfix">';
		    	    opt = opt +'<li title="'+report.finalScore+'%">'+report.finalScore+'%</li>';
    	    		opt = opt +'<li title="'+rate+'">'+rate+'</li>';
    	    		opt = opt +'<li title="'+totalExp+'">'+totalExp+'</li>';
    	    		opt = opt +'<li title="'+noticePriod+'">'+noticePriod+'</li>';
    	    		opt = opt +'<li title="'+location+'" class="locationLi">'+location+'</li>';
    	    		opt = opt +'<li title="'+candieducation+'" class="MultLi">'+candieducation+'</li>';
    	    		opt = opt +'<li title="'+candivisaType+'" class="MultLi">'+candivisaType+'</li>';
    	    		opt = opt +'<li title="'+skillsName+'" class="MultLi">'+skillsName+'</li>';
	    	    	opt = opt +'</ul>';
	    	    	opt = opt +'<div class="comBtns mt10">';
	    	    	if(report.currentResumeUUID != null && report.currentResumeUUID != '' && report.currentResumeUUID != 'undefined'){
	    	    		opt = opt +'<button type="button" onClick="viewFile(\''+report.fileUrl+'\',\''+report.currentResumeName+'\',\''+report.fileContentType+'\');" class="btn btn-sm btn-primary">View Resume</button>';
	    	    	}
	    	    	if(report.isCandidateSubmitted == 'N'){
	    	    		if(report.candidateProfilePercentage >= profilePercentage){
	    	    			opt = opt +'<button type="button" id="recSumbmitbtn'+report.candidateId+'" onClick="submitRecomendedApplicant('+requirementId+','+report.candidateId+',\'compare\');" class="btn btn-sm btn-success"><span id="recCandidate'+report.candidateId+'">Add</button>';
	    	    		}else{
	    	    			 opt = opt +'<span class="colorRed small">Profile % not matched</span>';
	    	    		}
	    	    		
	    	    	}else{
	    	    		opt = opt +'<button type="button" class="btn btn-sm btn-success">Added</button>';
	    	    	}
	    	    	opt = opt +'</div>';
		    	    opt = opt +'</div>';
		    	    if(count==1){
		    	    	$("#profileDiv1").empty();
// 		    	    	$("#noRecords1").hide();
		    	    	$("#profileDiv1").append(opt);
		    	    	$("#compareCandidateList1").val(report.candidateId);
		    	    	$("#compareCandidateList1").trigger('chosen:update');
		    	    }
		    	    if(count==2){
		    	    	$("#profileDiv2").empty();
// 		    	    	$("#noRecords2").hide();
		    	    	$("#profileDiv2").append(opt);
		    	    	$("#compareCandidateList2").val(report.candidateId);
		    	    	$("#compareCandidateList2").trigger('chosen:update');
		    	    }
		    	    if(count==3){
		    	    	$("#profileDiv3").empty();
// 		    	    	$("#noRecords3").hide();
		    	    	$("#profileDiv3").append(opt);
		    	    	$("#compareCandidateList3").val(report.candidateId);
		    	    	$("#compareCandidateList3").trigger('chosen:update');
		    	    }
		    	    if(count==4){
		    	    	$("#profileDiv4").empty();
// 		    	    	$("#noRecords4").hide();
		    	    	$("#profileDiv4").append(opt);
		    	    	$("#compareCandidateList4").val(report.candidateId);
		    	    	$("#compareCandidateList4").trigger('chosen:update');
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
		$("#loading1").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	}
	if(div == 2){
		candidateId = $("#compareCandidateList2").val();
		$("#loading2").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	}
	if(div == 3){
		candidateId = $("#compareCandidateList3").val();
		$("#loading3").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	}
	if(div == 4){
		candidateId = $("#compareCandidateList4").val();
		$("#loading4").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	}
	if(candidateId != null && candidateId != '' && candidateId != 'undefined'){
		var orgLocationId = '${SourceLeadWeb_orgLocationId}';
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+"/getCandidateDetails?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId+"&orgLocationId="+orgLocationId+"&requirementId="+requirementId, 
		        success: function(data) {
		        	if (data.response === "FAILED") {
			    		showError(data.responseMessage);
			    	} else if (data.response === "AWKWARD") {
			    		location.href = "/errorPage";
			    	} else if (data.response === "SUCCESS") {
			    		candidateDiv(data.object,div,requirementId);
			    	}else if (data.indexOf('Sign In') !== -1) {
			    		location.replace(sourceleadUrl);
			    	} else {
			    		showError("Operation failed, please try again");
			    	}
		        }
		 });
	}else{
		$("#loading"+div).removeAttr("style");
		$("#profileDiv"+div).empty();
		$("#profileDiv"+div).append('<span id="noRecords'+div+'" class="compNoRecords"></span>');
	}
	
}
</script>

<script>
function candidateDiv(report,div,requirementId){
  		var firstName ="";
	   	if(report.firstName != null && report.firstName != '' && report.firstName != 'undefined'){
	    	 firstName = report.firstName;
	    }else{
	    	 firstName = "";
	    } 
	   	
	   	var lastName = "";
	    if(report.lastName != null && report.lastName != '' && report.lastName != 'undefined'){
	    	lastName = report.lastName;
	    }else{
	    	lastName = "";
	    } 
	    
	   	var primarySkill = "";
	    if(report.primarySkill != null && report.primarySkill != '' && report.primarySkill != 'undefined'){
	    	primarySkill = report.primarySkill;
	    }else{
	    	primarySkill = "NA";
	    }
	    
	   	var rate ="";
	    if((report.expectedRate != null && report.expectedRate != '' && report.expectedRate != 'undefined') && (report.rateType != null && report.rateType != '' && report.rateType != 'undefined')){
	    	rate = report.expectedRate + ' / ' + report.rateType;
	    }else if(report.expectedRate != null && report.expectedRate != '' && report.expectedRate != 'undefined'){
	    	rate = report.expectedRate;
	    }else{
	    	rate = "NA";
	    }
	   
	    var totalExp = "";
	    if((report.experienceYears != null && report.experienceYears != '' && report.experienceYears != 'undefined') && (report.experienceMonths != null && report.experienceMonths != '' && report.experienceMonths != 'undefined')){
	    	totalExp = report.experienceYears + "."+report.experienceMonths+ " Year(s)";
	    }else if(report.experienceYears != null && report.experienceYears != '' && report.experienceYears != 'undefined'){
	    	totalExp = report.experienceYears + " Year(s)";
	    }else if(report.experienceMonths != null && report.experienceMonths != '' && report.experienceMonths != 'undefined'){
	    	totalExp = report.experienceMonths + " Months";
	    }else{
	    	totalExp ="NA";
	    } 
	    
	   	var location ="";
	    if(report.location != null && report.location != '' && report.location != 'undefined'){
	    	location = report.location;
	    }else{
	    	location = "NA";
	    } 
	    
	   	var noticePriod ="";
	    if(report.noticePeriod != null && report.noticePeriod != '' && report.noticePeriod != 'undefined'){
	    	noticePriod = report.noticePeriod;
	    }else{
	    	noticePriod = "NA";
	    }
	    
	   	var candieducation = "";
	    if(report.candidateEdu != null && report.candidateEdu != '' && report.candidateEdu != 'undefined'){
	    	var edu = report.candidateEdu;
	    	var keyword = edu.toString();
    	  	var educ = keyword.split(",");
    	  	var minEdu = educ.slice(0,2);
    	  	candieducation = minEdu; 
	    }else{
	    	candieducation = "NA";
	    } 
	    
	   	var candivisaType = "";
	    if(report.visaType != null && report.visaType != '' && report.visaType != 'undefined'){
	    	var visa = report.visaType;
	    	var keyword = visa.toString();
    	  	var visaType = keyword.split(",");
    	  	var minvisa = visaType.slice(0,2);
    	  	candivisaType = minvisa; 
	    }else{
	    	candivisaType = "NA";
	    } 
	    
	   	var skillsName = "";
	    if(report.skillsName != null && report.skillsName != '' && report.skillsName != 'undefined'){
	    	
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
	   var profilePercentage = '${requirementProfilePercentage}';
	    
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
	   opt = opt +'<p title="'+firstName+' '+lastName+'">';
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
		
	   opt = opt +'<p><i class="fa fa-briefcase" title="Primary Skill"></i><span title="'+primarySkill+'">'+primarySkill+'</span></p>';
	   opt = opt +'<p><i class="fa fa-envelope" title="Email"></i><span title="'+email+'">'+email+'</span></p>';
	   opt = opt +'<p><i class="fa fa-phone-square" title="Mobile"></i><span title="'+mobile+'">'+mobile+'</span></p>';
	   opt = opt +'</div>';
	   opt = opt +'</div>';
	   
	   opt = opt +'<!-- profile_personalInfo -->';
	   opt = opt +'<div class="profile_personalInfo">';
	   opt = opt +'<ul class="list-unstyled clearfix">';
	   if(report.candidateScore != null && report.candidateScore !='' && report.candidateScore !='undefined'){
	   		opt = opt +'                <li title="'+parseFloat(report.candidateScore)+'%">'+parseFloat(report.candidateScore)+'%</li>';
	   	 }else{
	   		opt = opt +'                <li>0%</li>';
	   	 }
	   opt = opt +'<li title="'+rate+'">'+rate+'</li>';
	   opt = opt +'<li title="'+totalExp+'">'+totalExp+'</li>';
	   opt = opt +'<li title="'+noticePriod+'">'+noticePriod+'</li>';
	   opt = opt +'<li title="'+location+'" class="MultLi">'+location+'</li>';
	   opt = opt +'<li title="'+candieducation+'" class="MultLi">'+candieducation+'</li>';
	   opt = opt +'<li title="'+candivisaType+'" class="MultLi">'+candivisaType+'</li>';
	   opt = opt +'<li title="'+skillsName+'" class="MultLi">'+skillsName+'</li>';
	   opt = opt +'</ul>';
	   opt = opt +'<div class="comBtns mt10">';
	   if(report.resumeUUID != null && report.resumeUUID != '' && report.resumeUUID != 'undefined'){
		   opt = opt +'<button type="button" onClick="viewFile(\''+report.fileUrl+'\',\''+report.candidateResumeName+'\',\''+report.fileContentType+'\');" class="btn btn-sm btn-primary">View Resume</button>';   
	   }
	   if(report.isCandidateSubmitted == 'N'){
			if(report.candidateProfilePercentage >= profilePercentage){
				 opt = opt +'<button type="button" onClick="submitRecomendedApplicant('+requirementId+','+report.id+',\'compare\');" class="btn btn-sm btn-success">Add</button>';
    		}else{
    			 opt = opt +'<span class="colorRed small">Profile % not matched</span>';
    		}
		  
	   }else{
		   opt = opt +'<button type="button" class="btn btn-sm btn-success">Added</button>';
	   }
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

<div class="sideMask" style="z-index:99999 !important;">
  <div class="proj_close" onClick="removeCompareTab();"></div>
</div>

<script>
function removeCompareTab(){
	$(".project_inn_mask").fadeOut(200);
	$(".sideMask").fadeOut();
	$(".new_side_wrap").removeClass("new_side_wrap_active");
}
</script>
