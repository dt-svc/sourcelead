<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script>
$(document).ready(function() {
	 var requirementUUID = '${requirementUUID}'; 
	 requirementProfile(requirementUUID);
});	
</script>

<section class="content-wraper">
  <section class="content-header clearfix">
    <div class="pull-left">
      <h2>Job Details</h2>
    </div>
    <c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_PROFESSIONAL' || SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_RECRUITER'}">
    <div class="pull-right brud-crum"><a href="${SourceLeadWeb_atsUrl}/professionalRequirements">Jobs List </a> &raquo; Job Details</div>
    </c:if>
    
    <c:if test="${SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_PROFESSIONAL' && SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_RECRUITER'}">
    <div class="pull-right brud-crum"><a href="${SourceLeadWeb_atsUrl}/jobsList">Jobs List </a> &raquo; <a href="${SourceLeadWeb_atsUrl}/professionalRequirements">Crowdsourcing Jobs List </a> &raquo; Job Details</div>
    </c:if>
  
  </section>
  
  <div class="side_mask"><div class="proj_close"></div></div>
	 <div class="new_side_wrap">
	 <div class="new_side_wrap_inn">
		<div class="new_side_wrap_cta">
			<div id="applytoJobDiv"></div>
			<div id="submitDetailsDiv"></div>
		</div></div>
	 </div>   
            
    <div id="requirementDetailsDiv"></div>
    <div id="professionalRequirementProfileDiv"></div> 
  

  </section>
<script>
var atsUrl = '${SourceLeadWeb_atsUrl}';
function requirementProfile(requirementUUID){
	var candidateId = '${SourceLeadWeb_candidateId}'
    var loginUserRole = '${SourceLeadWeb_loginUserRole}';
    var referUUID = '${referUUID}';
    $.ajax({
	   	type: "GET",
	    url: atsUrl+"/getRequirementProfessionalProfileDetails?requirementUUID="+requirementUUID+"&referUUID="+referUUID, 	   
		    success: function(data) {
		    	var json = JSON.parse(data);
		    	var result = "";
		    	if(json.minExp != null && json.minExp != '' && json.minExp != 'undefined'){
		    		minExp = json.minExp;
		    	}else{
		    		minExp = "";
		    	}
		    	if(json.maxExp != null && json.maxExp != '' && json.maxExp != 'undefined'){
		    		maxExp = json.maxExp;
		    	}else{
		    		maxExp = "";
		    	}
		    	if(json.rate != null && json.rate != '' && json.rate != 'undefined'){
		    		rate = json.rate;
		    	}else{
		    		rate = "";
		    	}if(json.maxRate != null && json.maxRate != '' && json.maxRate != 'undefined'){
		    		maxRate = json.maxRate;
		    	}else{
		    		maxRate = "";
		    	}
		    	if(json.rateType != null && json.rateType != '' && json.rateType != 'undefined'){
		    		rateType = json.rateType;
		    	}else{
		    		rateType = "";
		    	}
		    	
		    	if(json.currencyCode != null && json.currencyCode != '' && json.currencyCode != 'undefined'){
		    		currencyCode = ' / '+json.currencyCode;
		    	}else{
		    		currencyCode = "";
		    	}
		    	if(json.industryName != null && json.industryName != '' && json.industryName != 'undefined'){
		    		industryName = json.industryName;
		    	}else{
		    		industryName = "--";
		    	}
		    	if(json.city != null && json.city != '' && json.city != 'undefined'){
		    		city = json.city +' , ';
		    	}else{
		    		city = "";
		    	}
		    	if(json.state != null && json.state != '' && json.state != 'undefined'){
		    		state = json.state + ' , ';
		    	}else{
		    		state = "";
		    	}
		    	if(json.country != null && json.country != '' && json.country != 'undefined'){
		    		country = json.country;
		    	}else{
		    		country = "";
		    	}
		    	if(json.description != null && json.description != '' && json.description != 'undefined'){
		    		description = json.description;
		    	}else{
		    		description = "--";
		    	}
		    	if(json.positions != null && json.positions != '' && json.positions != 'undefined'){
		    		positions = json.positions;
		    	}else{
		    		positions = "";
		    	}
		    	
		    	if(json.education != null && json.education != '' && json.education != 'undefined'){
		    		education = json.education;
		    	}else{
		    		education = "--";
		    	}
		    	if(json.noticePeriod != null && json.noticePeriod != '' && json.noticePeriod != 'undefined'){
		    		 noticePeriod = json.noticePeriod;
	    		}else{
	    		   noticePeriod = "";
	    		}
		    	if(json.travelAllowance != null && json.travelAllowance != '' && json.travelAllowance != 'undefined'){
		    		travelAllowance = json.travelAllowance;
	    		}else{
	    			travelAllowance = "";
	    		}
		    	
		    	var location = '';
		    	if(json.location != null && json.location != '' && json.location != 'undefined'){
		    		location = json.location
		    	}
		    	var visaType ='';
		    	if(json.visaType != null && json.visaType != '' && json.visaType != 'undefined'){
 					visaType = json.visaType;
 				}else{
 					visaType = '--';
 				}

		    	result = result +'	<div class="timesheet_out">';
				 result = result +'    <div class="clients_side_wrap_inn no_pad">';
				 result = result +'      <div class="profile_details profile_detailsh4 requirement_details requirement_details_a clearfix">';
				
				 
				 if(json.jobId != '' && json.jobId != null && json.jobId != 'undefined' && json.jobType != '' && json.jobType != null && json.jobType != 'undefined' && json.jobType != 'Other'){
					 result = result +'<h4 id="id1">'+json.jobTitle+'-'+json.jobId+'-'+json.jobType+'</h4>';
				 }
				 else if(json.jobId != '' && json.jobId != null && json.jobId != 'undefined' && json.jobType != 'Other'){
				     result = result +'<h4 id="id1">'+json.jobTitle+'-'+json.jobId+'</h4>';
       			  }
       			 else if(json.jobType != '' && json.jobType != null && json.jobType != 'undefined' && json.jobType != 'Other'){
			    	 result = result+'<h4 id="id1">'+json.jobTitle+'-'+json.jobType+'</h4>';
       			 }else if(json.jobType != '' && json.jobType != null && json.jobType != 'undefined' && json.jobId != '' && json.jobId != null && json.jobId != 'undefined' && json.jobType == 'Other' &&  json.otherJobType != '' && json.otherJobType != null && json.otherJobType != 'undefined'){
   			     	result = result+'<h4 id="id1">'+json.jobTitle+'-'+json.jobId+'-'+json.jobType+'-'+json.otherJobType+'</h4>';
       			 }else if(json.jobType != '' && json.jobType != null && json.jobType != 'undefined' && json.jobType == 'Other' &&  json.otherJobType != '' && json.otherJobType != null && json.otherJobType != 'undefined'){
      			    result = result+'<h4 id="id1">'+json.jobTitle+'-'+json.jobType+'-'+json.otherJobType+'</h4>';
       			 }else if(json.jobType != '' && json.jobType != null && json.jobType != 'undefined' && json.jobType == 'Other'){
         		    result = result+'<h4 id="id1">'+json.jobTitle+'-'+json.jobType+'</h4>';
       			}else{
       			 	result = result+'<h4 id="id1">'+json.jobTitle+'</h4>';
       			}
				 
				 
				 result = result +'        <div class="hide-row" style="display: block;">';
				 result = result +'          <div class="col-md-9">';
				 result = result +'            <div class="row">';
				 result = result +'              <div class="col-md-3">';
				 result = result +'                <div class="company-logo">';
				 if(json.orgImageUrl != null && json.orgImageUrl != '' && json.orgImageUrl != 'undefined'){
				 result = result +'                  <div class="orgImage"> <img onerror="predefineOrgProfileImage(this);" data-id="orgLogoImage" src="'+json.orgImageUrl+'" alt="Company" title="Company Logo" class="img-responsive"> </div>';
				 }
				 result = result +'                 </div>';
				 
				 result = result +'              </div>';
				 result = result +'              <div class="col-md-9">';
				 result = result +'                <div class="canDetails clearfix">';
				 result = result +'                  <div class="row">';
				 result = result +'                    <div class="col-md-12">';
				 result = result +'                      <ul class="list-unstyled can-perdt canPerdt">';
				 
				 result = result +'                        <li><i class="fa fa-building" title="Company"></i><span>'+json.postedOrgName+'</span></li>';
				 if((json.maxExp != null && json.maxExp != '' && json.maxExp !='undefined' && (json.minExp != null) && json.minExp != '' && json.minExp != 'undefined')){
					 result = result +'                        <li><i class="fa fa-briefcase" title="Experience"></i><span>'+json.minExp+'-'+json.maxExp+' years</span></li>';
					 }else if(json.minExp != null && json.minExp != '' && json.minExp != 'undefined'){
					 result = result +'                        <li><i class="fa fa-briefcase" title="Experience"></i><span>'+json.minExp+' years</span></li>';
					 }else if(json.maxExp != null && json.maxExp != '' && json.maxExp !='undefined'){
					 result = result +'                        <li><i class="fa fa-briefcase" title="Experience"></i><span>'+json.maxExp+' years</span></li>';
					 }else{
						 result = result+'						<li><i class="fa fa-briefcase" title="Experience"></i><span> -- </span></li>';
					 }
				 
				 result = result +'                        <li><i class="fa fa-rupee" title="Job Rate"></i><span>'+rate+' - '+maxRate+' '+rateType+' '+currencyCode+'</span></li>';
				 
				 
				 if(json.locationsList != null){
					 result = result +'<li><i class="fa fa-map-marker pull-left"></i>';
					 result = result +'<ul class="list-unstyled list-inline joblocations pull-left">';
	 					for(var k=0; k<json.locationsList.length; k++){
	 						result = result +'<li>'+json.locationsList[k]+'</li>';
	 					}
	 					result = result +'</ul>';
	 					result = result +'</li>';
	 				}
				 
				 
// 				 result = result +'                        <li><i class="fa fa-map-marker" title="Location"></i><span>'+location+'</span></li>';
				 result = result +'                        <li><i class="fa fa-calendar" title="Notice Period"></i><span>'+noticePeriod+'</span>';
				 result = result+' 						<li><i class="fa fa-cab" title="Travel allowance"></i><span title="'+json.travelAllowance+'">'+json.travelAllowance+'</span></li>';
				 result = result +'                      </ul>';
				 result = result +'                    </div>';
				 result = result +'                  </div>';
				 result = result +'                </div>';
				 result = result +'                <div class="pro-detials">';
				 result = result +'                  <div class="professinal-details">';
				 result = result +'                    <ul class="list-inline list-unstyled">';
				 if(json.skills != null  && json.skills != '' && json.skills != 'undefiend'){
			    		var requirmentSkills = json.skills;
				    	var skills = new Array();
				    	skills = requirmentSkills.split(",");
				        for (var i = 0; i < skills.length; i++) { 
				        	 var customSkill = skills[i];
				        	 result = result +'<li>'+customSkill+'</li>';
				        }
			    	}else{
	 					result = result +'<li>--</li>';
	 				}
				 result = result +'                    </ul>';
				 result = result +'                  </div>';
				 result = result +'                </div>';
				 result = result +'              </div>';
				 result = result +'            </div>';
				 result = result +'          </div>';
				 result = result +'          <div class="col-md-3">';
				 result = result +'         <div class="openPostBtns">';
				 
				 if(loginUserRole == 'ROLE_CROWDSOURCING_PROFESSIONAL'){
					 if(json.isJobApplied == 'N'){
						 result = result +'<a href="javascript:void(0)" onclick="cancelReferPopUp();applyToJob(\''+json.jobTitle+'\','+json.id+','+json.referId+');"  class="jobApply">Apply</a>';
					 }else{
						 result = result +'<a href="javascript:void(0)" onclick="cancelReferPopUp();getSubmissionDetailsSideBar(\''+json.jobTitle+'\','+json.submissionId+','+candidateId+','+json.id+');" class="jobApply">Applied</a>';
					 }
				 }
				 
				 if(loginUserRole == 'ROLE_CROWDSOURCING_RECRUITER'){
					 result = result +'<a href="javascript:void(0)" onclick="cancelReferPopUp();applyToJob(\''+json.jobTitle+'\','+json.id+','+json.referId+');"  class="jobApply">Submit</a>';
				 }
				 
			     result = result +'<a class="refer" onclick="referToJob('+json.id+');getUserContats('+json.id+');" href="javascript:void(0)">Refer</a>';
			     result = result +'<div class="referWrap" id="refer">';
 				 result = result +'<div class="referWrapInn">';
 				 result = result +'<div id="refertoJobDiv"></div>';  
 				 result = result +'</div>';
 				 result = result +'</div>';
	 				
				 result = result +'          </div>';
				 result = result +'          </div>';
				 result = result +'          <div class="clearfix"></div>';
				 result = result +'        </div>';
				 result = result +'      </div>';
				 
			     result = result+'    <ul class="nav nav-tabs mt">';
			     result = result+'      <li class="active"><a data-toggle="tab" href="javascript:void(0)" onclick="requirementDetails(\''+requirementUUID+'\');" >Details</a></li>';
			     result = result+'      <li><a data-toggle="tab" href="javascript:void(0)" onclick="getReferalDetails(\''+requirementUUID+'\');" >Referrals</a></li>';
			     '<sec:authorize access="hasRole(\'ROLE_CROWDSOURCING_CANDIDATES\')">'; 
			     result = result+'      <li><a data-toggle="tab" href="javascript:void(0)" onclick="getSubmissionDetails(\''+requirementUUID+'\');">Submissions</a></li>';
			     '</sec:authorize>';
			    
			     if(json.isJobApplied != 'N'){
			     	result = result+'      <li><a data-toggle="tab" href="javascript:void(0)" onclick="getRateNegotiationDetails(\''+json.submissionId+'\', '+candidateId+');" >Rate Negotiation</a></li>';
			     }
			     
			     result = result+'      <div class="clearfix"></div>';
			     result = result+'    </ul>';
				 
			     $("#requirementDetailsDiv").empty();
				 $("#requirementDetailsDiv").append(result); 
				 
			     requirementDetails(requirementUUID);
			     
				 
		    }
	});
}

</script>


<script>
function requirementDetails(requirementUUID){
	$.ajax({
	   	type: "GET",
	    url: atsUrl+"/getRequirementProfessionalProfileDetails?requirementUUID="+requirementUUID, 	   
		    success: function(data) {
		    	
		    	var json = JSON.parse(data);
		    	var result = "";
		    	 result = result +'      <div class="project_side_wrap_inn_tabs" id="new-id">';
				 result = result +'        <div class="timesheetHead">';
				 result = result +'          <div id="tab-1" class="project_tabs_inn" style="display: block;">';
				 result = result +'            <div class="require_wrap_details require_textarea"> ';
				 result = result +'              <h2>Descriptions:</h2>';
				 result = result +'              <div class="RDdetails">'+description+'</div>';
				 result = result +'              <h2>Other details:</h2>';
				 result = result +'              <div class="require_one">';
				 result = result +'                <div class="require_wrap_details_cta require_wrap_details_ctaB150"> <b>No. of positions:</b> <span>'+positions+'</span>';
				 result = result +'                  <div class="clearfix"></div>';
				 result = result +'                </div>';
				 result = result +'                <div class="require_wrap_details_cta require_wrap_details_ctaB150"> <b>Education:</b> <span title="'+education+'">'+education+'</span>';
				 result = result +'                  <div class="clearfix"></div>';
				 result = result +'                </div>';
				 result = result +'                <div class="require_wrap_details_cta require_wrap_details_ctaB150"> <b>Industry:</b> <span title="'+industryName+'">'+industryName+'</span>';
				 result = result +'                  <div class="clearfix"></div>';
				 result = result +'                </div>';

				 if(json.backgroundCheck != null && json.backgroundCheck != '' && json.backgroundCheck != 'undefined'){
					 if(json.backgroundCheck == "Y"){
						 backgroundCheck = "Yes" 
					 }else{
						 backgroundCheck = "No" 
					 }
				 }else{
					 backgroundCheck = "--";
				 }
				 
				 if(json.overTime != null && json.overTime != '' && json.overTime != 'undefined') {
					 if(json.overTime == "Y"){
						 overTime = "Yes";
					 }else{
						 overTime = "No"
					 }
				}else{
					overTime = "--";
				}
				 
				 if(json.medicalClearence != null && json.medicalClearence != '' && json.medicalClearence != 'undefined') {
					 if(json.medicalClearence == "Y"){
						 medicalClearence = "Yes";
					 }else{
						 medicalClearence = "No";
					 }
				 }else{
					 medicalClearence = "--";
				 }
				 
				 if(json.securityClearance != null && json.securityClearance != '' && json.securityClearance != 'undefined') {
					 if(json.securityClearance == "Y"){
						 securityClearance = "Yes";
					 }else{
						 securityClearance = "No";
					 }
				}else{
						securityClearance = "--";
				}
				 
				
				 
				 if(json.fileName != null && json.fileName != '' && json.fileName != 'undefined'){
		            	result = result +'<div class="require_wrap_details_cta require_wrap_details_view require_uploadfile require_wrap_details_ctaB150"><b>Attachment:</b><span>'+json.fileName+'</span><a download class="glyphicon glyphicon-download-alt ml10" href=\"'+json.fileURL+'\"></a><a class="fa fa-eye ml10" onClick="viewFile(\''+json.fileURL+'\',\''+json.fileName+'\',\''+json.fileContentType+'\');"></a> ';
		            	result = result +'                  <div class="clearfix"></div>';
						 result = result +'                </div>';   
				 }
		            else{
		            result = result +'<div class="require_wrap_details_cta require_wrap_details_ctaB150"> <b>Attachment:</b> <span> -- </span>';
		            result = result +'                  <div class="clearfix"></div>';
					 result = result +'                </div>';
		            }
				 if(json.visaType != null && json.visaType != '' && json.visaType != 'undefined')
					{
					 result = result +'                <div class="require_wrap_details_cta require_wrap_details_ctaB150"> <b>Immigration Type:</b> <span title="'+json.visaType+'">'+json.visaType+'</span>';
					 result = result +'                  <div class="clearfix"></div>';
					 result = result +'                </div>';
					}
				 else{
					 result = result +'                <div class="require_wrap_details_cta require_wrap_details_ctaB150"> <b>Immigration Type:</b> <span title="--">--</span>';
					 result = result +'                  <div class="clearfix"></div>';
					 result = result +'                </div>';
				 }
				  
				 result = result +'                <div class="require_wrap_details_cta require_wrap_details_ctaB150"> <b>Medical Clearence:</b> <span title="'+medicalClearence+'">'+medicalClearence+'</span>';
				 result = result +'                  <div class="clearfix"></div>';
				 result = result +'                </div>';
					 
				 
				 result = result +'                <div class="require_wrap_details_cta require_wrap_details_ctaB150"> <b>Security Clearance:</b> <span title="'+securityClearance+'">'+securityClearance+'</span>';
				 result = result +'                  <div class="clearfix"></div>';
				 result = result +'                </div>';
					 
				 
				 result = result +'                <div class="require_wrap_details_cta require_wrap_details_ctaB150"> <b>Over Time:</b> <span title="'+overTime+'">'+overTime+'</span>';
				 result = result +'                  <div class="clearfix"></div>';
				 result = result +'                </div>';
					 
				 
				 result = result +'                <div class="require_wrap_details_cta require_wrap_details_ctaB150"> <b>Background Check:</b> <span title="'+backgroundCheck+'">'+backgroundCheck+'</span>';
				 result = result +'                  <div class="clearfix"></div>';
				 result = result +'                </div>';
					 
				 if(json.gender != null && json.gender != '' && json.gender != 'undefined')
					{
				 result = result +'                <div class="require_wrap_details_cta require_wrap_details_ctaB150"> <b>Gender:</b> <span title="'+json.gender+'">'+json.gender+'</span>';
				 result = result +'                  <div class="clearfix"></div>';
				 result = result +'                </div>';
					}
				 else{
					 result = result +'                <div class="require_wrap_details_cta require_wrap_details_ctaB150"> <b>Gender:</b> <span title="--">--</span>';
					 result = result +'                  <div class="clearfix"></div>';
					 result = result +'                </div>'; 
				 }
				 if(json.experienceLevel != null && json.experienceLevel != '' && json.experienceLevel != 'undefined')
					{
				 result = result +'                <div class="require_wrap_details_cta require_wrap_details_ctaB150"> <b>Experience Level:</b> <span title="'+json.experienceLevel.name+'">'+json.experienceLevel.name+'</span>';
				 result = result +'                  <div class="clearfix"></div>';
				 result = result +'                </div>';
					}
				 else{
					 result = result +'                <div class="require_wrap_details_cta require_wrap_details_ctaB150"> <b>Experience Level:</b> <span title="--">--</span>';
					 result = result +'                  <div class="clearfix"></div>';
					 result = result +'                </div>'; 
				 }
				 result = result +'                  <div class="clearfix"></div>';
				 result = result +'                </div>';
				 result = result +'                  <div class="clearfix"></div>';
				 result = result +'                </div>';
				 result = result +'                <div class="clearfix"></div>';
				 result = result +'              </div>';
				 result = result +'            </div>';
				 result = result +'          </div>';
				 result = result +'        </div>';
				 result = result +'      </div>';
				 result = result +'    </div>';
				 result = result +'  </div>';
				 $("#professionalRequirementProfileDiv").empty();
				 $("#professionalRequirementProfileDiv").append(result); 
		    }
	    });
   }
</script>

<script>
function viewProfessionalRequirementProfile(jobId,title){
		if(jobId != null && jobId != ""){
			window.open(atsUrl+'/viewJobSummaryDocumentDocView/'+title+'?jobId='+jobId,title,"height=600,width=600");
		}  
}
</script>


<script>
function getReferalDetails(requirementUUID){
	
	$.ajax({
	   	type: "GET",
	    url: atsUrl+"/getReferalListByRequirementId?requirementUUID="+requirementUUID, 	   
		    success: function(data) {
		    	 var json = JSON.parse(data);
		    	 var result =""; 
		    	 
		    	   result = result + '<div class="require_wrap_table overflow-data filter-position mt25 timesheetHead">';
		    	   result = result + '<div class="clerfix mt">';
		    	   result = result + '<table id="referListPagenation" class="display" cellspacing="0" width="100%"">';
		           result = result + '<thead>';
		           result = result + '<tr>';
		           result = result + '<th>Name</th>';
		           result = result + '<th>Email</th>';
		           result = result + '<th>Phone</th>';
	        	   result = result + '<th>Stage</th>';
		           result = result + '</tr>';
		           result = result + '</thead>';
		           result = result + '<tbody>';
		           
		           if(json.referByMeJobsList.length > 0){
		        	   for (var i=0; i<json.referByMeJobsList.length; i++)
		  				{
			        	   var report = json.referByMeJobsList[i];
			        	   
			        	
			        	   if(report.requirementSubmissionStatus != null && report.requirementSubmissionStatus != '' && report.requirementSubmissionStatus != 'undefined'){
			        		   requirementSubmissionStatus = report.requirementSubmissionStatus;
			        	   }else{
			        		   requirementSubmissionStatus = "--"
			        	   }
			        	   
			        	   if(report.userName != null && report.userName != '' && report.userName != 'undefined'){
			        		   userName = report.userName;
			        	   }else{
			        		   if(report.referedUserLastName != null && report.referedUserLastName != '' && report.referedUserLastName != 'undefined'){
			        			   userName = report.referedUserFirstName+" "+report.referedUserLastName;
			        		   }else{
			        			   userName = report.referedUserFirstName;
			        		   }
			        	   }
			        	   
			        	   if(report.phoneNumber != null && report.phoneNumber != '' && report.phoneNumber != 'undefined'){
			        		   phoneNumber = report.phoneNumber;
			        	   }else{
			        		   phoneNumber= "--";
			        	   }
			        	   
			        	   
			        	   result = result +'         <tr>';
			        	   result = result +'         <td><div class="tbldata-dotdot-big">'+userName+'</div></td>';
			        	   result = result +'         <td><div class="tbldata-dotdot-me">'+report.referToEmail+'</div></td>';
			        	   result = result +'         <td><div class="tbldata-dotdot">'+phoneNumber+'</div></td>';
			        	   
			        	   result = result + '<td><div class="tblwidth-150"><span>'+requirementSubmissionStatus+'</span>';
			        	   if(report.rejectedReason != null && report.rejectedReason != '' && report.rejectedReason != 'undefined'){
			        	 		  result = result + '<i class="glyphicon glyphicon-comment ml10 edit-reason"><div class="edit-status"><p>'+report.rejectedReason+'</p></div></i></div></td>';
			        	   }	
			        	   result = result + '</div></td>';
			        	   
			        	   result = result +'         </tr>';
			        	   
		  				}
		          
		           }
		           
		           result = result + '       </tbody>';
		           result = result + '     </table>';
		           result = result + '   </div>';
		           $("#professionalRequirementProfileDiv").empty();
		           $("#professionalRequirementProfileDiv").append(result);
		           referListPagenation();
		    }
	});
	
	 
}
</script>


<script>
function getSubmissionDetails(requirementUUID){
	
	$.ajax({
	   	type: "GET",
	    url: atsUrl+"/getRecruiterRequirementSubmissionsList?requirementUUID="+requirementUUID, 	   
		    success: function(data) {
		    	 var json = JSON.parse(data);
		    	 var result =""; 
		    	 
		    	   result = result + '<div class="require_wrap_table overflow-data filter-position mt25 timesheetHead">';
		    	   result = result + '<div class="clerfix mt">';
		    	   result = result + '<table id="submissionListPagenation" class="display" cellspacing="0" width="100%"">';
		           result = result + '<thead>';
		           result = result + '<tr>';
		           result = result + '<th>Name</th>';
		           result = result + '<th>Email</th>';
		           result = result + '<th>Phone</th>';
	        	   result = result + '<th>Stage</th>';
		           result = result + '</tr>';
		           result = result + '</thead>';
		           result = result + '<tbody>';
		           
		           if(json.requirementSubmissionList.length > 0){
		        	   for (var i=0; i<json.requirementSubmissionList.length; i++)
		  				{
			        	   var report = json.requirementSubmissionList[i];
			        	   
			        	   
			        	   result = result +'         <tr>';
			        	   result = result +'         <td><div class="tbldata-dotdot-big proj_show" onclick="cancelReferPopUp();getSubmissionDetailsSideBar(\''+report.requirementName+'\','+report.id+','+report.candidateId+','+report.requirementId+');" ><div class="tbldata-dotdot-me pull-left">'+report.candidateName+'</div><i class="fa fa-binoculars pull-right"></i></div></td>';
			        	   result = result +'         <td><div class="tbldata-dotdot-me">'+report.candidateEmail+'</div></td>';
			        	   result = result +'         <td><div class="tbldata-dotdot-vsm">'+report.phoneNumber+'</div></td>';
			        	   
			        	   result = result + '<td><div class="tblwidth-150"><span>'+report.submissionStatus+'</span>';
			        	   if(report.submissionStatus == "REJECTED" && report.rejectedReason != null && report.rejectedReason != '' && report.rejectedReason != 'undefined'){
			        	 		  result = result + '<i class="glyphicon glyphicon-comment ml10 edit-reason"><div class="edit-status"><p>'+report.rejectedReason+'</p></div></i></div></td>';
			        	   }	
			        	   result = result + '</div></td>';
			        	   result = result +'         </tr>';
			        	   
		  				}
		          
		           }
		           
		           result = result + '       </tbody>';
		           result = result + '     </table>';
		           result = result + '   </div>';
		           $("#professionalRequirementProfileDiv").empty();
		           $("#professionalRequirementProfileDiv").append(result);
		           submissionListPagenation();
		    }
	});
	
	 
}
</script>

<script>
function getRateNegotiationDetails(submissionId, candidateId){
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	$.ajax({
	   	type: "POST",
	   	url: atsUrl+"/getRateHistoryBySubmissionId?${_csrf.parameterName}=${_csrf.token}&submissionId="+submissionId+"&candidateId="+candidateId+"&orgLocationId="+orgLocationId, 	   
		    success: function(data) {
		    	if (data.response === "FAILED") {
		    		showError(data.responseMessage);
		    	} else if (data.response === "AWKWARD") {
		    		location.href = "/errorPage";
		    	} else if (data.response === "SUCCESS") {
			    	if(data.data != null && data.data != 'undefined' && data.data != ""){
		            	 var json = data.data;
	          			    var result = "";
	          			   	result = result + '<div class="timesheetHead proposeNewRate clearfix">';
	          			   	 
	          			   	 
			          		result = result +'   <div class="panel-group" id="accordion">';
			          		result = result +'  <div class="clearfix">';
			          		result = result +'    <div class="panel-heading clearfix">';
			          		result = result +'      <h4 class="panel-title pull-right">';
			          		result = result +'        <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" onClick="iconFun(this);">';
			          		result = result +'          Propose New Rate';
			          		result = result +'        <i class="indicator glyphicon glyphicon-chevron-down"></i></a>';
			          		result = result +'      </h4>';
			          		result = result +'    </div>';
			          		result = result +'    <div id="collapseOne" class="panel-collapse collapse in clearfix">';
			          		result = result +'      <div class="panel-body">';
			          		
			          		
			          		result = result + '<div class="clearfix row">';
		                    result = result + '<form name="rate_form" id="rate_form" method="post">';
		                    
		                    result = result + '<div class="col-md-6">';
		                    result = result + '<label class="col-md-12">Rate<span class="paddingLeft text-danger">*</span></label>';
		                    result = result + '<div class="col-md-12">';
		                    result = result + '<input type="text" class="form-control input-sm" placeholder="Rate" id="rateProposal" name="rateProposal" field-name="Rate" data-validation="required  validate_Space validate_float validate_length length1-8">';
		                    result = result + '</div>';
		                    result = result + '</div>';
		                    
			          		result = result + '<div class="col-md-6">';
			          		result = result + '<label class="col-md-12">Rate Per<span class="paddingLeft text-danger">*</span></label>';
			          		result = result + '<div class="col-md-12 dropdown-select">';
			                result = result + ' <select class="form-control input-sm" id="rateType" name="rateType" field-name="Rate Per" data-validation="required">';
			                result = result + '	<option value="">Rate Per</option>';
			                result = result + '    <option>Hourly</option>';
			                result = result + '    <option>Weekly</option>';
			                result = result + '    <option>Bi-weekly</option>';
			                result = result + '    <option>Semi-Monthly</option>';
			                result = result + '    <option>Monthly</option>';
			                result = result + '    <option>Bi-Monthly</option>';
			                result = result + '    <option>Yearly</option>';
			                result = result + ' </select>';
			                result = result + '</div>';
			                result = result + '</div>';
		                    
		                    result = result + '  <div class="col-md-12 mt">';
		                    result = result + '  <label class="col-md-12">Comment</label>';
		                    result = result + '  <div class="col-md-12">';
		                    result = result + '    <textarea rows="2" class="form-control" placeholder="Comment" id="comment" name="comment" data-validation-optional="true" data-validation="validate_length length2-500" field-name="Reason"></textarea>';
		                    result = result + '  </div>';
		                    result = result + '  </div>';
		                    
		                    result = result + '  <div class="col-md-12 mt">';
		                    result = result + '    <div class="pull-right mr">';
		                    result = result + '      <input type="button" value="Submit" class="btn btn-primary btn-sm mr" id="add_rate" onClick="proposeNewRate('+submissionId+','+candidateId+');">';
		                    result = result + '      <input type="button" value="Clear" class="btn btn-default btn-sm" onClick="clearRatediv();">';
		                    result = result + '    </div>';
		                    result = result + '  </div>';
		                    result = result + '</form>';
		                    result = result + '</div>';
			          		
		                    
			          		result = result +'      </div>';
			          		result = result +'    </div>';
			          		result = result +'  </div>';
			          		result = result +'</div> ';
	          			   	 
	          			   	 
	          			   	 result = result +' <div class="require_wrap_table col-md-12 mb">';
	  						 result = result +' <h4>Rate Negotiation History</h4>';
		                     result = result + '  <table id="candidatePagination" class="display" cellspacing="0" width="90%" >';
		                     result = result + '    <thead>';
		                     result = result + '      <tr>';
		                     result = result + '        <th>By</th>';
		                     result = result + '        <th>Rate</th>';
		                     result = result + '        <th>Comment</th>';
		                     result = result + '        <th>Date</th>';
		                     result = result + '        <th>Status</th>';
		                     result = result + '      </tr>';
		                     result = result + '    </thead>';
		                     result = result + '    <tbody>';
		                     var divState = "";
		                     var isLastNegotiator = "";
		                     for (var i=0; i<json.length; i++)
				   				{
						    	 var report = json[i];
						    	 if(i == json.length-1){
			                    	 divState = report.rateInitiatorType;
			                    	 /* if((report.rateInitiatorType != "ORG" && report.rateInitiatorType != "CANDIDATE") && (report.initiatorOrgLocationIdStr==orgLocationId)){
			                    		 isLastNegotiator = "true";
			                    	 }else{
			                    		 isLastNegotiator = "false";
			                    	 } */
			                    	 
			                    	 if(report.isLastProposedUser == "Y"){
			                    		 isLastNegotiator = "true";
			                    	 }else if(report.isLastProposedUser == "N"){
			                    		 isLastNegotiator = "false";
			                    	 }
		                    	 }
			                     result = result + '<tr>';
			                     if(report.proposeByName != null && report.proposeByName != '' && report.proposeByName != 'undefined'){
			                     result = result + '<td><div class="tbldata-dotdot-sm" title="'+report.proposeByName+'">'+report.proposeByName+'</div></td>';
			                     }
			                     else{
			                    	 result = result + ' <td><div class="tbldata-dotdot-sm">----</div></td>'; 
			                     }
			                    
			                     if(report.rateAndType != null && report.rateAndType != '' && report.rateAndType != 'undefined'){
			                         result = result + '<td><div class="tblminwidth-75" title="'+report.rateAndType+'">'+report.rateAndType+'</div></td>';
			                     } 
			                     else{
			                    	 result = result + ' <td><div class="tblminwidth-75">----</div></td>'; 
			                     }
			                     
			                     if(report.rateReason != null && report.rateReason != '' && report.rateReason != 'undefined'){
			                         result = result + '<td><div class="tbldata-dotdot" title="'+report.rateReason+'">'+report.rateReason+'</div></td>';
			                     }
			                     else{
			                         result = result + '<td><div class="tbldata-dotdot">----</div></td>';
			                     }
			                     
			                     if(report.createdDateStr != null && report.createdDateStr != '' && report.createdDateStr != 'undefined'){
			                         result = result + '<td><div class="tblminwidth-75">'+report.createdDateStr+'</div></td>';
			                     }
			                     else{
			                         result = result + '<td><div class="tblminwidth-75">----</div></td>'; 
			                     }
			                     result = result + '    <td><div class="tblminwidth-75" id="rateActionDiv">';
			                     
			                     if(i == json.length-1 && report.rateStage == 'NEWRATE' && isLastNegotiator=="false"){
			                    	 var Accepted = 'Accepted';
			                    	 var Rejected = 'Rejected';
			                    	 if(null != report.submissionRateStatus && '' != report.submissionRateStatus && 'undefined' != report.submissionRateStatus && 'REJECTED' != report.submissionRateStatus){
			                    		 if(report.rateAndType != null && report.rateAndType != 'undefined' && report.rateAndType !=''){
			                    			 result = result + '<a href="javascript:void(0)" class="text-primary" title="Accept" onclick="acceptRejectRate('+submissionId+',\''+Accepted+'\',\''+candidateId+'\');"><i class="fa fa-check"></i></a> <a href="javascript:void(0)" class="text-danger" title="Reject" onclick="acceptRejectRate('+submissionId+',\''+Rejected+'\',\''+candidateId+'\');"><i class="fa fa-times"></i></a>';	 
			                    		 }else{
			                    			 result = result + '----';
			                    		 }
			                    	 }
			                     }else{
			                    	 if(report.rateStage == 'ACCEPTED'){
				                    	 result = result + ' Accepted';
				                     	}else if(report.rateStage == 'REJECTED'){
				                     		result = result + 'Rejected';
				                     	}else{
				                     		result = result + '----';
				                     	}
			                     }
			                     result = result + '		</div></td>';
			                     result = result + '      </tr>';
				   				}
		                     result = result + '    </tbody>';
		                     result = result + '  </table>';
		                     result = result+"  </div> ";
		                     result = result+" </div> ";
		                     $("#professionalRequirementProfileDiv").empty();
		  		           	 $("#professionalRequirementProfileDiv").append(result);
		        	 	}else{
		        	 		$("#professionalRequirementProfileDiv").removeAttr("style");
				    	     var result ="";
				    	     result = result + '<div class="require_wrap_table overflow-data filter-position mt25 timesheetHead">';
	    		    	   	 result = result +' <div class="require_wrap_details require_textarea">';
	    					 result = result +' <h2>Rate Negotiation History</h2>';
				    	     result = result+"   <div class='row'>"; 
							 result = result+"	<div class='col-lg-12'>"; 
							 result = result+"		<div class='clearfix'>"; 
							 result = result+"			<div class='blank-data'>";
							 result = result+"			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
							 result = result+"			        <p>No rate history found</p>";
							 result = result+"			 </div>";
							 result = result+"		</div>"; 
							 result = result+"	</div>"; 
							 result = result+"  </div> ";
							 result = result+"  </div> ";
		                     result = result+" </div> ";
		                     $("#professionalRequirementProfileDiv").empty();
		  		           	 $("#professionalRequirementProfileDiv").append(result);
		            	}
		    	}else if (data.indexOf('Sign In') !== -1) {
		    		location.replace(sourceleadUrl);
		    	} else {
		    		showError("Operation failed, please try again");
		    	}
		    }
	});
}


function toggleChevron(e) {
    $(e.target)
        .prev('.panel-heading')
        .find("i.indicator")
        .toggleClass('glyphicon-chevron-down glyphicon-chevron-up');
}
$('#accordion').on('hidden.bs.collapse', toggleChevron);
$('#accordion').on('shown.bs.collapse', toggleChevron);
function iconFun(el){
	$(el).find("i").toggleClass('glyphicon-chevron-down glyphicon-chevron-up');
	$("#rateProposal").val('');
	$("#rateType option:selected").removeAttr("selected");
	$("#comment").val('');
}

function clearRatediv(){
	$("#rateProposal").val('');
	$("#rateType option:selected").removeAttr("selected");
	$("#comment").val('');
}


function proposeNewRate(submissionId, candidateId) {
	 if ($('#rate_form').validate(false, validationSettings)){
		 
		    var type = "Candidate";
		    var formData = getFormData1("rate_form");
		    $("#add_rate").attr("disabled", "disabled");
		    
			$.ajax({
			type: "POST",
			processData: false,
		    contentType:false, 
			data: formData,
			url : atsUrl+'/ws/storeNegotiation?'+tokenParamName+'='+tokenValue+'&submissionId='+submissionId+'&type='+type,
				success: function(output) {
					if (output.response === "FAILED") {
			    		showError(output.responseMessage);
			    	} else if (output.response === "AWKWARD") {
			    		location.href = "/errorPage";
			    	} else if (output.response === "SUCCESS") {
			    		getRateNegotiationDetails(submissionId, candidateId);
			    	}else {
			    		showError("Operation failed, please try again");
			    		$("#add_rate").removeAttr("disabled");
			    	}
				},
			});
			return true;
		 }else{
		  	return false;
		 }
	}
</script>

<script type="text/javascript">
	function acceptRejectRate(submissionId, status, candidateId) {
		$("#rateActionDiv").empty();
		$("#rateActionDiv").prepend('<i class="fa fa-cog fa-spin"></i>');
			$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			$.ajax({
			type: "POST",
			url: atsUrl+"/acceptRejectRate?${_csrf.parameterName}=${_csrf.token}&submissionId="+submissionId+"&status="+status,
				success: function(data) {
					if (data.response === "FAILED") {
			    		showError(data.responseMessage);
			    	} else if (data.response === "AWKWARD") {
			    		location.href = "/errorPage";
			    	} else if (data.response === "SUCCESS") {
			    		getRateNegotiationDetails(submissionId, candidateId);
			    	}else if (data.indexOf('Sign In') !== -1) {
			    		location.replace(sourceleadUrl);
			    	} else {
			    		showError("Operation failed, please try again");
			    	}
				},
			});
			  return true;
	}
</script>


<script>
function referListPagenation(){  
    $('#referListPagenation').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[5, 10, 15, -1], [5, 10, 15, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
}
</script>


<script>
function submissionListPagenation(){  
    $('#submissionListPagenation').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[5, 10, 15, -1], [5, 10, 15, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
}
</script>



<script>
function referToJob(requirementId){
	   appendReferToJob(requirementId); 
	    
	   var result = ''
	    
	    result = result +'<div class="clearfix refercontacts">';
	    result = result + '<ul class="nav nav-tabs">';
	    result = result + '<li class="active"><a data-toggle="tab" href="#contacts" onclick="getUserContats('+requirementId+')">Contacts</a></li>';
	    result = result + '<li><a data-toggle="tab" href="#addnew" onclick="flushContactDetails();">Refer New</a></li>';
	    result = result + '</ul>';
	    result = result +'</div>';
	    
	    result = result +'<div class="tab-content refercontacts_content">';
	    result = result +'<div id="loading"></div>';
	    result = result +'<div id="contacts" class="tab-pane fade in active">';
	    result = result + '<form id="referToContacts" name="referToContacts" method="post">';
	    result = result +'<input type="hidden" name="contactReferRequirementId" id="contactReferRequirementId" value='+requirementId+' >';
	    result = result +'<div id="contactsDiv">';
	    result = result +' <select  multiple data-placeholder="Select Contacts" onchange="getContactsCount()" class="chosen-select form-control" data-validation="required" field-name="Contacts"  name="userContacts" id="userContacts" data-placeholder="Select Concatts" multiple >';
	    result = result +'</select>';
	    result = result +'</div>';
	    result = result + '<div class="pull-left mt">';
	    result = result +'<span style="font-size:13px;">No Of Contacts Selected: <b id="contactsCount">0</b>&nbsp;&nbsp;&nbsp;</span>';
	    result = result +'</div>';
	    result = result +'<div class="pull-right">';
		result = result +'<input type="submit" value="Refer" id="submit_contacts"  class="btn btn-primary">';
		result = result +'<input type="button" value="Cancel" onclick="cancelReferPopUp()" class="btn btn-default ml10 canBtn">';
		result = result +'</div>';  
	    result = result + '</form>';
	    result = result +'</div>';
	    result = result +'<div class="clearfix"></div>'; 
	    
	    result = result +'<div id="addnew" class="tab-pane fade">';
	    result = result + '<form id="referToJob_form" name="referToJob_form" method="post">';
		result = result +'<div class="form-group col-md-6" id="referFirstName">';
		result = result +'<input type="hidden" name="referRequirementId" id="referRequirementId" value='+requirementId+' >';
		result = result +'<label class="col-md-12" for="Project Id">First Name *</label>';
		result = result +'<div class="col-md-12">';
		result = result +'<input type="text" class="form-control" name="firstName" id="firstName" field-name="First Name" data-validation="required validate_Space validate_Alphanumeric validate_length length1-100">';
		result = result +'</div>';
		result = result +'</div>'; 
		result = result +'<div class="form-group col-md-6" id="referLastName">';
		result = result +'<label class="col-md-12" for="Project Id">Last Name</label>';
		result = result +'<div class="col-md-12">';
		result = result +'<input type="text" class="form-control" name="lastName" id="lastName" field-name="Last Name" data-validation-optional="true" data-validation="validate_Space validate_Alphanumeric validate_length length1-100">';
		result = result +'</div>';
		result = result +'</div>'; 
		result = result +'<div class="form-group col-md-6" id="emailDiv">';
		result = result +'<label class="col-md-12" for="Project Id">Email Id *</label>';
		result = result +'<div class="col-md-12">';
		result = result +'<input type="text" class="form-control" name="email" id="email" onblur="validateProfessionalEmail('+requirementId+')"  field-name="Email" data-validation="required validate_Space validate_length length2-100 validate_email">';
		result = result +'<span class="hidden">Enter Project Id</span> </div>';
		result = result +'</div>'; 
		result = result +'<div class="form-group col-md-6" id="referPhone">';
		result = result +'<label class="col-md-12" for="Project Id">Phone</label>';
		result = result +'<div class="col-md-12">';
		result = result +'<input type="text" class="form-control" name="phone" id="phone" field-name="Phone" data-validation-optional="true"  data-validation="validate_Space validate_phone validate_length length9-20">';
		result = result +'</div>';
		result = result +'</div>'; 
		result = result +'<div class="col-md-12 mt5 clearfix">';            
		result = result +'<div class="pull-right">';
		result = result +'<input type="submit" value="Refer" id="submit_refer"  class="btn btn-primary">';
		result = result +'<input type="button" value="Cancel" onclick="cancelReferPopUp()" class="btn btn-default ml10 canBtn">';
		result = result +'</div>';    
		result = result +'</div>'; 
		result = result +'</div>'; 
		result = result +'<div class="clearfix"></div>'; 
		result = result + '</form>';
		
		$("#refertoJobDiv").empty(); 
	    $("#refertoJobDiv").append(result); 
}
</script>


<script>
function getUserContats(requirementId){
	 
	 $.ajax({
	    	type: "POST",
	        url: atsUrl+"/getCrowdSourcingUserContacts?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId,  
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	var output = "";
	        	
	        	for (var i=0; i<json.length; i++)
				{
		        		var opt = json[i];
		        		output = output + "<option value='" +opt.id+ "'>"+opt.contactUserName+" ("+opt.contactUserEmail+")</option>";
		        		
				}
	        	
	        	
	        	$("#userContacts").empty();
	        	$("#userContacts").append(output);
	        	
	        	$("#userContacts").chosen();
	    		$("#userContacts").trigger("chosen:updated");
		       
	        },
	    });
}
</script>


<script>
function flushContactDetails(){
	$("#firstName").val('');
	$("#lastName").val('');
	$("#email").val('');
	$("#phone").val('');

    $('#referFirstName').find('p.jquery_form_error_message').remove(); 
    $('#referLastName').find('p.jquery_form_error_message').remove(); 
    $('#emailDiv').find('p.jquery_form_error_message').remove(); 
    $('#referPhone').find('p.jquery_form_error_message').remove(); 
    
    $('#contactsDiv').find('p.jquery_form_error_message').remove(); 
    $("#contactsCount").html(0);
}
</script>

<script>
function getContactsCount(){
	var contactsCount =  $('#userContacts_chosen  span').size();
	$("#contactsCount").html(contactsCount);
	$('#userContacts_chosen').next("p").remove();
	
	$('.search-choice-close').click(function() {
		var contactsCount =  $('#userContacts_chosen  span').size();
		$("#contactsCount").html(contactsCount);
	});
}
</script>


<script type="text/javascript">
$('body').on('submit', '#referToContacts', function(e){
	  if ($(this).validate(false, validationSettings)){
      e.preventDefault();
      var formData = $($('#referToContacts')[0]).serialize();
     
      
        $("#submit_contacts").attr("disabled", "disabled");
		$( "#submit_contacts" ).addClass('button').val('Processing..');
	 	$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 	
	 	  $.ajax({
		    	type: "POST",
		    	data: formData,
		    	processData :false,
		    	 url: atsUrl+"/submitUserContactsToRequirement?${_csrf.parameterName}=${_csrf.token}", 
		        success: function(result) {
		        	cancelReferPopUp();
		        },
		    }); 
      return true; 
    }
    else{
    	return false;	
       }
  });

	$('body').on('blur', '#referToContacts', function() {
		$("#referToContacts").showHelpOnFocus();
		$("#referToContacts").validateOnBlur(false, validationSettings);
	});
  </script>
  
  

<script type="text/javascript">
$('body').on('submit', '#referToJob_form', function(e){
	  if ($(this).validate(false, validationSettings)){
      e.preventDefault();
      
        $("#submit_refer").attr("disabled", "disabled");
		$( "#submit_refer" ).addClass('button').val('Processing..');
		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		var formData = getFormData1("referToJob_form");
    		
		       $.ajax({
    					type : "POST",
    					 data: formData,
    	  		         processData: false,
    	  		         contentType:false, 
    					url : atsUrl+"/submitReferToJob?"+tokenParamName+"="+tokenValue+"",
    					success : function(result) {
    						cancelReferPopUp();
    					},
    				});
      return true; 
    }
    else{
    	return false;	
       }
  });

	$('body').on('blur', '#referToJob_form', function() {
		$("#referToJob_form").showHelpOnFocus();
		$("#referToJob_form").validateOnBlur(false, validationSettings);
	});
  </script>


<script>	
function appendReferToJob(requirementId){
		$("#refer").toggleClass("referWrapActive");
		
}
</script> 


<script>
  function validateProfessionalEmail(requirementId){
	  
	  var email = document.getElementById('email').value; 
	  if(email != null && email != ''){
		  $("#submit_refer").attr("disabled", "disabled");
		$.ajax({
		   	type: "GET",
		       url: atsUrl+"/validateReferedEmail?${_csrf.parameterName}=${_csrf.token}&email="+email+"&requirementId="+requirementId,
		       success: function(data) {
		    		if(data != null && data != ''){
					     if(data == 'Invalid'){
					    	    $('#emailDiv').find('p.jquery_form_error_message').remove(); 
					    	    $("input#email").after("<p class='jquery_form_error_message'>"+email+" is already refered</span>");
			    				$('input#email').attr('record-exist','yes');
			    				$('input#email').attr('record-exist-errorMsg',email+' is already refered'); 
					     } 
					     
					     else if(data == 'Invalid Email'){
					    	    $('#emailDiv').find('p.jquery_form_error_message').remove(); 
					    	    $("input#email").after("<p class='jquery_form_error_message'> you can't refer user self</span>");
			    				$('input#email').attr('record-exist','yes');
			    				$('input#email').attr('record-exist-errorMsg'," you can't refer user self"); 
					     } 
					     
				       	else{
				       		 $("#submit_refer").removeAttr("disabled");
							 $('input#email').removeAttr( "record-exist record-exist-errormsg");
			        		 $('#emailDiv').find('p.jquery_form_error_message').remove(); 
							 $( "#submit_refer" ).removeAttr("style");
							 $( "#submit_refer" ).click(function() {
							 if ($(this).validate(false, validationSettings)){
							 }
							});
				       	}
			       	}
		    	   
		      },
		       
		}); 
	   }
  }
  
  </script>

<script>
function cancelReferPopUp(){
	$("#refer").removeClass("referWrapActive");
	$("#refertoJobDiv").empty(); 
	
}
</script>
<script>
	function predefineOrgProfileImage(imageId){   
		$(imageId).attr('src','/resources/images/office-image.jpg');
	}
</script>

<script>
$(document).ready(function() {
	$("#tab-1").show()
    $(".project-tabs-menu a").click(function(event) {
        event.preventDefault();
        $(this).parent().addClass("current");
        $(this).parent().siblings().removeClass("current");
        var tab = $(this).attr("href");
        $(".project_tabs_inn").not(tab).css("display", "none");
        $(tab).fadeIn();
    });
	
});
</script>


<jsp:include page="applyToJob.jsp" />
<jsp:include page="../masterFooter.jsp" />