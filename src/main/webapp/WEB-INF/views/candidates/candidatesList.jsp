<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<meta name="format-detection" content="telephone=no">
<%-- 
     @author : p.sarath kumar
     this script is for candidate Activation
--%>

  <div id="shareEmptypopUp"></div>
  
  
<style type="text/css">
.trashbackground{
    background-color: #ffff !important;
    }
</style>
  
<script>
  function noAccess(id){ 	
	 
	    var result = "";
		result = result + '	  <div class="blank-pages-tech">';
		result = result + '   <p class="text-center text-danger permissondenied mb">';
		result = result + '       <img src="/resources/images/sourcelead-load.png" alt="Source Lead" title="Source Lead">';
		result = result + '   </p>';
		result = result + '   <p class="big-txt mt">PERMISSION DENIED</p>';
		result = result + '   <p class="text-muted">You dont have permission to access this page.</p>';
		result = result + '   <p class="positionRelative move-btn btn-group mt">';
		result = result + '   <i class="fa fa-home btn btn-primary"></i>';
		result = result + '   <a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}" type="button" class="btn btn-primary">Take me home<span>&nbsp;&nbsp;<i class="fa fa-angle-double-right"></i>';
		result = result + '   </span></a>';
		result = result + '   </p>';
		result = result + ' </div>';
		    
	   $("#tab-"+id).empty();
	   $("#tab-"+id).append(result);
  }
</script>

<jsp:include page="candidatesProfile.jsp"></jsp:include>
<jsp:include page="candidatesResumes.jsp"></jsp:include>
<jsp:include page="candidatesExperience.jsp"></jsp:include>
<jsp:include page="candidatesEducation.jsp"></jsp:include>
<jsp:include page="candidatesCertificates.jsp"></jsp:include>
<jsp:include page="candidateDocuments.jsp"></jsp:include>
<jsp:include page="candidateReferences.jsp"></jsp:include>
 <jsp:include page="requirementHistory.jsp"></jsp:include>
 <sec:authorize access="hasAnyRole(\'ROLE_POST_COMMENTS\',\'ROLE_CROWDSOURCING_CANDIDATES\')">
 <jsp:include page="candidatesCommentsList.jsp"></jsp:include>
 </sec:authorize>
 <jsp:include page="candidatesQuickView.jsp"></jsp:include>
 <jsp:include page="candidatesTimelineActivity.jsp"></jsp:include>
 <jsp:include page="requirementListForSubmission.jsp"></jsp:include>
 <%-- <jsp:include page="candidatesAssessment.jsp"></jsp:include> --%>
 <div class="new_side_wrap">
	<div class="new_side_wrap_inn">
		<div class="new_side_wrap_cta">
		 <div class="project_tabs_inn_table vendor_wrap_inn_table dataFilterNew pdtop datatable_filter">
		    <div id="addApplicantDivLoading"></div>
			<div id="addApplicantDiv"></div>
			</div>
		</div>
	</div>
</div>


<div id="candidateSubmittedJobsDiv" class="project_tabs clearfix"></div> 
<div id="candidateRejectedDiv" class="project_tabs clearfix"></div> 

	<script>
	var isAssociate = 'N';
	var candidateIDsArr=[];
	var _csrfParam = '${_csrf.parameterName}';
	var _csrfToken = '${_csrf.token}';
	var associateFlag = false;
	'<sec:authorize access="hasAnyRole(\'ROLE_ADD_CANDIDATE_HOTLIST\',\'ROLE_CROWDSOURCING_CANDIDATES\')">';
		associateFlag = true;
	'</sec:authorize>';
	var name1Flag = false;
	'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CANDIDATE\',\'ROLE_VIEW_EDIT_CANDIDATE\',\'ROLE_VIEW_DELETE_CANDIDATE\',\'ROLE_CROWDSOURCING_CANDIDATES\')">'; 
	name1Flag = true;
	'</sec:authorize>';
	var name2Flag = false;
	'<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_CANDIDATE\',\'ROLE_VIEW_EDIT_CANDIDATE\',\'ROLE_VIEW_DELETE_CANDIDATE\',\'ROLE_CROWDSOURCING_CANDIDATES\')">'; 
	name2Flag = true;
	'</sec:authorize>';
	 window.onload = function() {
		 candidateListCheck("${serachValue}","${universalCanddateList}","${candidateKeyword}","${candidateIdSearchId}");
		 if('${candidateUUID}' != null && '${candidateUUID}' != '' && '${candidateUUID}' != 'undefined'){
				candidateQuickViewProfile('${candidateId}');
			}
	};
	</script>
	
	
<script>
function closeCandiatesSubmitedList() {
	$("#candidateRejectedDiv").empty();  
    $(".modal.paymentaccount-modal").fadeOut();
    $(".comMask").fadeOut();
}
</script>

<script>
  var atsUrl = '${SourceLeadWeb_atsUrl}';
</script>
<script type="text/javascript" src="${SourceLeadWeb_atsUrl}/resources/js/candidateTable.js"></script>

<input type="hidden" id="noCandidates" name="noCandidates" />

<%-- 
     @author : sarath kumar.
     this script is for show side bar when you click on the candidate name.
     1.usage     : called at candidateList() function.
     2.parameter : csrf token , candidateId .
     3.ajax url implementation in CandidateListRestController.
--%>

<script>
var candidateStatusGlobal ="";
function candidateSideBar(candidateId,candType){
	var loginUserId = '${SourceLeadWeb_userId}';
	  var candidateType = "Candidates";
	  var locationCode = '${SourceLeadWeb_locationCode}';
	  var candidateUrl = "";
	  if(candType != null && candType != 'undefined' && candType != '' && candType == 'candidateGlobal'){
		  candidateUrl = atsUrl+"/getGlobalCandidateInfo?candidateId="+candidateId+"&locationCode="+locationCode+"&candidateType="+candidateType;
	  }else{
		  candidateUrl = atsUrl+"/getProfessionalInfo?candidateId="+candidateId+"&locationCode="+locationCode;
	  }
	  
	  $.ajax({
		  type: "GET",
		    url: candidateUrl,
           success :function(json) { 
                if(json != null && json != 'undefined' && json != ""){  
              			 candidateStatusGlobal = json.status;
	                	 $("#candidateSideDiv").empty();
	                	 var skills = [];
	                	 var skillsList ='';
	                	 if(json.skillsList != null && json.skillsList != '' && json.skillsList != 'undefined'){
	                		 skillsList = json.skillsList;
				             skills = skillsList.split(",");
	                	 }
			            var experienceMonths = '';
			            var designationType = '';
			            var country = '';
			            var state = '';
			            var city = '';
			            var currencyCodes = '';
			            var generatedId = '';
			            var middleName = '';
         			 	if(json.experienceMonths != null && json.experienceMonths != '' && json.experienceMonths != 'undefined'){
         			 		experienceMonths = json.experienceMonths+'Month(s)';            			 		
         			 	}else{
         			 		experienceMonths = ' ';  
         			 	 }
         			 	
         			 	if(json.designation != null && json.designation != '' && json.designation != 'undefined'){
             			 	designation = json.designation;
             			 	}else{
             			 		designation = '---';
             			 } 
         			 	
         			 	if(json.designationType != null && json.designationType != '' && json.designationType != 'undefined'){
         			 	designationType = json.designationType;
         			 	}else{
         			 		designationType = '---';
         			 	}

         			 	if(json.lastName != null && json.lastName !='' && json.lastName != 'undefined'){
         			 		lastName = json.lastName;
         			 	}else{
         			 		lastName = "";
         			 	}
         			 	if(json.middelName != null && json.middelName !='' && json.middelName != 'undefined'){
         			 		middleName = json.middelName;
         			 	}else{
         			 		middleName = "";
         			 	}

         			 	
         			 	if(json.country != null && json.country != '' && json.country != 'undefined'){
         			 		country =json.country;
             			 	}else{
             			 		country = '';
             			 	}
         			 	
         			 	if(json.state != null && json.state != '' && json.state != 'undefined'){
         			 		state =json.state+',';
             			 	}else{
             			 		state = '';
             			 	}
         			 	
         			 	if(json.city != null && json.city != '' && json.city != 'undefined'){
         			 		city =json.city+',';
             			 	}else{
             			 		city = '';
             			 	}
         			 	
         			 	 if(json.currentRate != null && json.currentRate != '' && json.currentRate != 'undefined'){
         			 		currentRate = json.currentRate;
         			 	 }else{
         			 		currentRate = '';
         			 	 }
                           
         			 	if(json.currentRateType != null && json.currentRateType != '' && json.currentRateType != 'undefined'){
         			 		currentRateType = json.currentRateType;
         			 	}else{
         			 		currentRateType = '';
         			 	}
         			 	
         			 	if(json.expectedRate != null && json.expectedRate != '' && json.expectedRate != 'undefined'){
         			 		expectedRate = json.expectedRate;
         			 	}else{
         			 		expectedRate = '';
         			 	}
         			 	
         			 	if(json.rateType != null && json.rateType != '' && json.rateType != 'undefined'){
         			 		rateType = json.rateType;
         			 	}else{
         			 		rateType = '';
         			 	}
         			 	
         			 	if(json.currentRateCurrency != null && json.currentRateCurrency != '' && json.currentRateCurrency != 'undefined'){
         			 		currentRateCurrency = '/'+json.currentRateCurrency;
         			 	}else{
         			 		currentRateCurrency = '';
         			 	}
         			 	
         			 	if(json.expectedRateCurrencey != null && json.expectedRateCurrencey != '' && json.expectedRateCurrencey != 'undefined'){
         			 		expectedRateCurrencey = '/'+json.expectedRateCurrencey;
         			 	}else{
         			 		expectedRateCurrencey = '';
         			 	}
         			 	
         			 	if(json.alternateMobilePhone != null && json.alternateMobilePhone != '' && json.alternateMobilePhone != 'undefined'){
         			 		alternateMobilePhone = ' , ' +json.alternateMobilePhone;
         			 	}else{
         			 		alternateMobilePhone = '';
         			 	}
         			 	
         			 	 if(json.mobilePhone != null && json.mobilePhone != '' && json.mobilePhone != 'undefined'){
         			 		mobilePhone =json.mobilePhone;
         			 	 }else{
         			 		 
         			 		mobilePhone = '';
         			 	 }
         			 	 
         			 	  if(json.generatedId != null && json.generatedId != '' && json.generatedId != 'undefined'){
         			 		generatedId = json.generatedId;
          			 	 }else{
          			 		generatedId = '';
          			 	 } 
         			 	 
	                	 var result = "";
	                	 result = result+'<div id="popUp" ></div>  ';
	                	 
	                	 result = result+'<form id="candidate_form" name="candidate_form" method="post" >';
			             result = result+'  <div class="profile_details clearfix mb">';
			             result = result+'               <div class="col-md-6">';
			             result = result+'                   <div class="media">';
			             
			             result = result+'<div class="col-xs-12 col-sm-5 col-md-5">';
			             result = result+'<div id="candidateImageId">';
			             result = result+'<div class="well well-sm text-center clearfix nomr">';
						 result = result+' <div class="profile-img-container profile-image text-center" style="position:relative;">';
						 if(json.fileUrl != null && json.fileUrl !='' && json.fileUrl !="undefined"){
						 result = result+'   <img  id="userImage" data-id="profileImage" src="'+json.fileUrl+'" alt="Profile Image" class=" img-responsive center-block img-circle">';
						 }else{
						result = result+'<img data-id="profileImage" id="userImage" src="/resources/images/userprofileImg.jpg" alt="Profile Image" class=" img-responsive center-block img-circle">';

						 }
						 result = result+'  <div class="btn-group uploadfile uploadfile-candidate">';
						 //result = result+'  <button type="button" class="btn btn-sm btn-primary">';
						 //result = result+'  <i class="fa fa-upload"></i></button>';
						 result = result+'  <label class="btn btn-sm btn-primary" ">';
					     result = result+'   <i class="fa fa-upload paddingRight"></i> Upload / Change<input type="file" id="uploadedImage" accept="image/*" onchange="cropImage(this,\'candidate_form\','+candidateId+',false);" style="display: none;">';
					     result = result+'  </label>';
					     if(json.fileContentType != null && json.fileContentType != '' && json.fileContentType != 'undefined'){
						     result = result+'  <button type="button" class="btn btn-sm btn-danger trashbackground" onclick="deleteImage('+candidateId+')">';
							 result = result+'  <i class="fa fa-trash"></i></button>';
					     } 
						 result = result+'  </div>';
						 result = result+'  </div>';
						 /*  result = result+'  <div class="pull-right" style="position:relative; top:10px; right:-10px;">';
						 result = result+'  <a href="#" title="Save"><i class="fa fa-check-circle" onClick="saveCandidateImage('+candidateId+');" ></i></a>';
						 result = result+'  <a href="#" title="Close"><i class="fa fa-close text-danger"></i></a>';
						 result = result+'  </div>'; */
						 result = result+'  </div>';
						 
						 result = result+'  <div class="mb mt5 UsProBar">';                                                                                                                                        
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
						result = result+'<div class="socialnetwork-details socialnetworkIcons">';
			             result = result+'<ul class="list-inline list-unstyled">';
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
			             result = result+'</ul>';
			             result = result+'                 </div>';
						/* else{ 
							result = result+'  <div class="mb mt5 UsProBar">';                                                                                                                                        
							result = result+' <div class="progress nomr">                                                                                                                                                       ';
							result = result+'   <div class="progress-bar progress-bar-danger progress-bar-striped active" role="progressbar" id="perVal" aria-valuenow="'+json.profilePercentage+'" aria-valuemin="0" aria-valuemax="100" style="width:'+json.profilePercentage+'%">  ';
							result = result+'    <p id="disVal"> '+json.profilePercentage+'%</p>                                                                                                                                                                      ';
							result = result+'   </div>                                                                                                                                                                     ';
							result = result+' </div>                                                                                                                                                                       ';
							result = result+' </div> ';
						} */
						 
						 result = result+' <p class="text-danger small" style="font-size: 11px; display: none;" id="errorImage" >Invalid Image/Size</p>';
						 result = result+' </div>';
						 result = result+' </div>';
						 result = result+'</form>';
						 
							
						
						  
			             /*  result = result+'                     <div class="media-left image-parent">';
			             result = result+'                     <div class="imageParent">'; */
			             /* result = result+'                     	<img src="./images/24.jpg" alt="candidate" title="Candidate">'; */
			             /* result = result+'<img  id="userImage" onerror="predefineProfileImage(this);" src="<c:url value="/displayUserImage/${SourceLeadWeb_userId}"/>"  alt="Profile Image" class="img-circle img-responsive">';
			             result = result+'                     </div>';
			             result = result+'                     </div>'; */
			             result = result+'<div class="media-body candidate-details candidateDetails padding_left">';
			            /*  result = result+'<h4 class="media-heading" title = "">'+json.generatedId+'</h4>'; */
			             result = result+'<h4 class="media-heading" title = "'+json.firstName+' '+middleName+' '+lastName+'">'+json.firstName+' '+middleName+' '+lastName+'</h4>';
			            
			             result = result+'<ul class="list-unstyled">';
			             
// 			             result = result+'<li>Type - '+json.candidateType+'</li>';  
			             
                         
                         
						 result = result+'<li><span class="col-md-5 padding_left paddingRight text-muted">Generated ID</span><span class="col-md-7 padding_left padding_right">'+generatedId+'</span></li>';
			             
						 result = result+'<li><span class="col-md-5 padding_left paddingRight text-muted">Designation</span><span class="col-md-7 padding_left padding_right tbldata-dotdot-sm" title= '+designation+'> '+designation+'</span></li>';
						 
						 result = result+'<li><span class="col-md-5 padding_left paddingRight text-muted">Employment Type</span><span class="col-md-7 padding_left padding_right">'+designationType+'</span></li>';
						 
						 
						 
	         			 if(json.vendorName != null && json.vendorName != '' && json.vendorName != 'undefined'){

						 result = result+'<li><span class="col-md-5 padding_left paddingRight text-muted">Vendor</span><span class="col-md-7 padding_left padding_right tbldata-dotdot-sm" title='+json.vendorName+'>'+json.vendorName+'</span></li>';
	         			 }
	         			 
	         			if(json.vendorContactName != null && json.vendorContactName != '' && json.vendorContactName != 'undefined'){

						result = result+'<li><span class="col-md-5 padding_left paddingRight text-muted">Vendor Contact</span><span class="col-md-7 padding_left padding_right tbldata-dotdot-sm" title = '+json.vendorContactName+'>'+json.vendorContactName+'</span></li>';
		         		}
			             
			              if(json.experienceYears != null && json.experienceYears != '' && json.experienceYears != 'undefined'){
			            	 result = result+'<li><span class="col-md-5 padding_left paddingRight text-muted">Experience</span><span class="col-md-7 padding_left padding_right">'+json.experienceYears+' Year(s) '+experienceMonths+'</span></li>';
              		       }else{
                		   		 result = result+'<li><span class="col-md-5 padding_left paddingRight text-muted">Experience</span><span class="col-md-7 padding_left padding_right">---</span></li>';  
              		       }
			            
			             
			              if(json.noticePeriod != null && json.noticePeriod != '' && json.noticePeriod != 'undefined' && json.noticePeriod != 'Other'){
			                    	 
			               result = result+'<li><span class="col-md-5 padding_left paddingRight text-muted">Notice Period</span><span class="col-md-7 padding_left padding_right">'+json.noticePeriod+'(s)'+'</span></li>';
			             
             		       }else if(json.noticePeriod != null && json.noticePeriod != '' && json.noticePeriod != 'undefined' && json.noticePeriod == 'Other'){
             		                                                	  
                   		     result = result+'<li><span class="col-md-5 padding_left paddingRight text-muted">Notice Period</span><span class="col-md-7 padding_left padding_right">'+json.noticePeriod+"---"+json.otherNoticePeriod+'</span></li>';  
             		     
             		       }else{
             		    	  result = result+'<li><span class="col-md-5 padding_left paddingRight text-muted">Notice Period</span><span class="col-md-7 padding_left padding_right">---</span></li>';    
             		       }
			             result = result+'</ul>';
			             
			             result = result+'<div class="clearfix">';
			             result = result+'<div class="">';
			             
			            
			             result = result+'<ul class="list-inline list-unstyled">';
			            /*  if(json.skillsList != null && json.skillsList != '' && json.skillsList != 'undefined'){
			              for (i=0;i < skills.length;i++)
			             {
			            	  result = result+'<li>'+skills[i]+'</li>';
			             } 
			             } */
			             
			             if(json.primarySkill != null && json.primarySkill != '' && json.primarySkill != 'undefined'){
			            	 result = result+'<li><span class="col-md-5 padding_left paddingRight text-muted">Primary Skill</span><span class="padding_left padding_right">'+json.primarySkill+'</span></li>';
			             }else{
			            	 result = result+'<li><span class="col-md-5 padding_left paddingRight text-muted">Primary Skill</span><span class="col-md-7 padding_left padding_right">---</span</li>';  
			             }
			             
			             if(json.gender != null && json.gender != '' && json.gender != 'undefined'){
			            	 result = result+'<li><span class="col-md-5 padding_left paddingRight text-muted">Gender</span><span class="padding_left padding_right">'+json.gender+'</span></li>';
			             }else{
			            	 result = result+'<li><span class="col-md-5 padding_left paddingRight text-muted">Gender</span><span class="col-md-7 padding_left padding_right">---</span</li>';  
			             }
			             
			             if(json.maritalStatus != null && json.maritalStatus != '' && json.maritalStatus != 'undefined'){
			            	 result = result+'<li><span class="col-md-5 padding_left paddingRight text-muted">Marital Status</span><span class="padding_left padding_right">'+json.maritalStatus+'</span></li>';
			             }
			             if(json.dateofBirthString != null && json.dateofBirthString != '' && json.dateofBirthString != 'undefined'){
			            	 result = result+'<li><span class="col-md-5 padding_left paddingRight text-muted">DateOfBirth</span><span class="padding_left padding_right">'+json.dateofBirthString+'</span></li>';
			             }else{
			            	 result = result+'<li><span class="col-md-5 padding_left paddingRight text-muted">DateOfBirth</span><span class="col-md-7 padding_left padding_right">---</span</li>';  
			             }
			             
			             result = result+'</ul>';
			             result = result+'</div>';
			             result = result+'                 </div>';
			             result = result+'               </div>';
			             result = result+'                   </div>';
			             result = result+'                 </div>';
			             result = result+'               <div class="col-md-6">';
			             result = result+'                 <ul class="list-unstyled can-perdt">';
			             result = result+'                   <li><i class="fa fa-envelope-o" title= "Email"></i><a href="#" title="'+json.email+'">'+json.email+'</a></li>';
			             result = result+'<li><i class="fa fa-phone" title= "Mobile"></i>'+mobilePhone+''+alternateMobilePhone+'</li>';
			             result = result+' <li><i class="fa fa-location-arrow" title= "Location"></i>'+city+''+state+''+country+'</li>';
			             
			             result = result+'<li><i class="fa fa-money" title= "Current Rate"></i><b>Current Rate:</b>';
			             
			             result = result+''+currentRate+' '+currentRateType+''+currentRateCurrency+'';
			             
			             result = result+'</li>';
			             
			             result = result+'<li><i class="fa fa-money" title= "Expected Rate"></i><b>Expected Rate:</b>';
			             
			             result = result+''+expectedRate+' '+rateType+''+expectedRateCurrencey+'';
			             
			             result = result+'</li>';
			             
			             result = result+'</ul>';
			             
			            /* New comments div creation Nov 8th, 2016 */ 
			            if(json.status == '1' ){
			            '<sec:authorize access="hasAnyRole(\'ROLE_POST_COMMENTS\',\'ROLE_CROWDSOURCING_CANDIDATES\')">'; 
			            result = result+'<div class="comments-div clearfix" id="latestMessage">';
			            result = result+'    <div class="commentsimg">';
			            result = result+'    <i class="fa fa-comments"></i>';
			            result = result+'        <div class="commentsimg-inner">';
			            
			            result = result+'        	<img data-id="userProfileImage" onerror="predefineProfileImage(this);" src="${SourceLeadWeb_userImgUrl}"/>';
			            result = result+'        </div>';
			            result = result+'    </div>';
			            result = result+'     <div class="comments-content">';
			            result = result+'         <div class="pull-left">';
			            result = result+'         <p class="comment-by"></p>';
			            result = result+'         <p class="comment-text"></p>';
			            result = result+'         </div>';
			            result = result+'         <div class="pull-right cmt-button">';
			            result = result+'          <span class="comments-button">';
			            
			            result = result+'          	<button type="button" class="btn btn-sm btn-primary" onClick="showComment('+candidateId+')">';
			            result = result+'            	<i class="fa fa-plus"></i>&nbsp;Comments';
			            result = result+'            </button>';
			            result = result+'            <div class="add-comment">';
			            result = result+'            </div>';
			            result = result+'          </span>';
			            result = result+'          <a href="javascript:void(0)" class="comments-view" onclick="candidateCommentsList('+candidateId+');">View more <span class="label label-warning">'+json.commentsCount+'</span></a>';
			 			result = result+'		</div>';
			            result = result+'     </div>';
			            result = result+'</div>';
			            '</sec:authorize>';
			            }
			 		    /* New comments div creation Nov 8th, 2016 */
			             
			             
			             result = result+'               </div>';
			             result = result+'			</div>';
			             
			             
			             result = result+'  <div class="cearfix">';
				         result = result+'	  <div class="newTagsWrap">';
				         result = result+'		    <ul class="clearfix tagsWrap tagsWrapBor">';
				         result = result+'			<select multiple class="chosen-select form-control" name="tagName" id="candidateTagName" data-placeholder="Add Tags" data-validation="validate_Space" data-validation-optional="true">';
				         result = result+'			</select>';
				         result = result+'			</ul>';
				         result = result+'			<div class="clearfix"></div>';
				         result = result+'	  </div>';
				         result = result+' </div>';
	
				        
			            /*  result = result+' <div class="newTagsWrap">';
							result = result+'    <ul class="clearfix tagsWrap tagsWrapBor">';
							result = result + '<select multiple class="chosen-select form-control" name="tagName" id="tagName" data-placeholder="add tags" data-validation="validate_Space" data-validation-optional="true">';
							// here get lookup data tags and manually enter skills 
							$.each( json.tagsList, function( i, tags ) {
							  var existed = 0;
				                if(json.candidateTags != null && json.candidateTags != 'undefined' && json.candidateTags != '') {
					                var res = [];
					                res = json.candidateTags.split(",");
					                res.forEach(function(item) {
					                	if(tags == item){
					                		existed = 1;
					                		result = result + ' <option selected value="'+tags+'">'+tags+'</option>';
					                	}
					                });
				                }
				                if(existed == 0){
				                	result = result + ' <option value="'+tags+'">'+tags+'</option>';
				                }
							});
			                result = result + '</select>';
							result = result+'        <div class="clearfix"></div>';
							result = result+'    </ul>';
							result = result+'</div>'; */
							
							
							
			             result = result+'   <div class="proj_close"></div>';
			             result = result+'   <div class="project_side_wrap_inn_tabs">';
			             result = result+'     <ul class="project-tabs-menu">'; 
			             '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CANDIDATE\',\'ROLE_VIEW_EDIT_CANDIDATE\',\'ROLE_VIEW_DELETE_CANDIDATE\',\'ROLE_CROWDSOURCING_CANDIDATES\')">';

			              result = result+'<li id="candidateProfileTab" class="current"><a href="#tab-1" onclick="candidateProfile('+candidateId+',\''+candidateType+'\',\''+json.isAssociate+'\',\''+candType+'\');">Profile</a></li>';

			             '</sec:authorize>';
			             
			             '<sec:authorize access="hasAnyRole(\'ROLE_ADD_CANDIDATE_RESUMES\',\'ROLE_VIEW_CANDIDATE_RESUMES\',\'ROLE_VIEW_EDIT_CANDIDATE_RESUMES\',\'ROLE_VIEW_DELETE_CANDIDATE_RESUMES\',\'ROLE_CROWDSOURCING_CANDIDATES\')">';
			               result = result+'<li id="candidateResumeTab"><a href="#tab-2" onclick="candidateResumesList('+candidateId+',\''+candType+'\'); candidateResume('+candidateId+',\''+candType+'\');" >Resume</a></li>';
			             '</sec:authorize>';
			             
			             '<sec:authorize access="hasAnyRole(\'ROLE_ADD_EMPLOYEER\',\'ROLE_VIEW_EMPLOYEER\',\'ROLE_VIEW_EDIT_EMPLOYEER\',\'ROLE_VIEW_DELETE_EMPLOYEER\',\'ROLE_CROWDSOURCING_CANDIDATES\')">';
			               result = result+'<li id="candidateEmployersTab"><a href="#tab-3" onclick="candidateExperience('+candidateId+',\''+candType+'\');">Employers</a></li>';
			             '</sec:authorize>';
			             
			             '<sec:authorize access="hasAnyRole(\'ROLE_ADD_CANDIDATE_EDUCATION\',\'ROLE_VIEW_CANDIDATE_EDUCATION\',\'ROLE_VIEW_EDIT_CANDIDATE_EDUCATION\',\'ROLE_DELETE_CANDIDATE_EDUCATION\',\'ROLE_CROWDSOURCING_CANDIDATES\')">';
			              result = result+'<li id="candidateEducationTab"><a href="#tab-4" onclick="candidateEducation('+candidateId+',\''+candType+'\');">Education</a></li>';
			             '</sec:authorize>';
			             
			             '<sec:authorize access="hasAnyRole(\'ROLE_ADD_CANDIDATE_CERTIFICATION\',\'ROLE_VIEW_CANDIDATE_CERTIFICATION\',\'ROLE_VIEW_EDIT_CANDIDATE_CERTIFICATION\',\'ROLE_VIEW_DELETE_CANDIDATE_CERTIFICATION\',\'ROLE_CROWDSOURCING_CANDIDATES\')">';
			               result = result+'<li id="candidateCertificationsTab"><a href="#tab-5" onclick="candidateCertificate('+candidateId+',\''+candType+'\');">Certifications</a></li>';
			             '</sec:authorize>';
			             
			             <%--- This is for Documents(tab-9) Tab in Candidate Summary -----%>
			             '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_ASSOCIATE_DOCUMENT\',\'ROLE_VIEW_EDIT_ASSOCIATE_DOCUMENT\',\'ROLE_DELETE_ASSOCIATE_DOCUMENT\',\'ROLE_CREATE_ASSOCIATE_DOCUMENT\',\'ROLE_CROWDSOURCING_CANDIDATES\')">';
			             result = result+'<li id="candidateDocumentsTab"><a href="#tab-9" onclick="candidateDocuments('+candidateId+',\''+candType+'\');">Documents</a></li>';
			             '</sec:authorize>';
			             
			            result = result+'<li id="candidateReferencesTab"><a href="#tab-10" onclick="candidateReferences('+candidateId+');">References</a></li>';
						 
			             '<sec:authorize access="hasAnyRole(\'ROLE_POST_COMMENTS\',\'ROLE_CROWDSOURCING_CANDIDATES\')">';
			                result = result+'<li id="candidateCommentTab"><a href="#tab-7" onclick="candidateCommentsList('+candidateId+');">Comments</a></li>';
			              '</sec:authorize>';
			              
			              '<sec:authorize access="hasAnyRole(\'ROLE_CANDIDATE_REQUIREMENT_HISTORY\',\'ROLE_CROWDSOURCING_CANDIDATES\')">';
			              result = result+'<li id="candidateHistoryTab"><a href="#tab-8" onclick="requirementHistory('+candidateId+');">Job History</a></li>';
			              '</sec:authorize>';
			              
			             /*  result = result+'<li id="candidateAssessmentTab"><a href="#tab-10" onclick="candidateAssessment('+candidateId+');">Assessment</a></li>'; */
			              
			             /* result = result+'       <li><a href="#tab-6" onclick="candidateActivities('+candidateId+');">Activities</a></li>'; */
			             result = result+'       <div class="clearfix"></div>';
			             result = result+'     </ul>';
			             result = result+'     <div class="project_tabs">';
				         result = result + ' <div id="tab-1" class="project_tabs_inn">';
						 result = result + '</div>';
						 
						 result = result + '<div id="tab-2" class="project_tabs_inn">';
						 result = result + '</div>';
						 
						 result = result + '<div id="tab-3" class="project_tabs_inn">';
						 result = result + '</div>';
						 
						 result = result + ' <div id="tab-4" class="project_tabs_inn">';
						 result = result + '</div>';
						 
						 result = result + '<div id="tab-5" class="project_tabs_inn">';
						 result = result + '</div>';
						 
						 result = result + '<div id="tab-9" class="project_tabs_inn">';
			             result = result+'     </div>';
			             
			             result = result + '<div id="tab-10" class="project_tabs_inn">';
			             result = result+'     </div>';
						 
						 result = result + '<div id="tab-8" class="project_tabs_inn">';
						 result = result + '</div>';
						 
						 result = result + '<div id="tab-6" class="project_tabs_inn">';
						 result = result + '</div>';
						 result = result + '<div id="tab-7" class="project_tabs_inn">';
						 result = result + '</div>';
			             result = result+'   </div>';
			             
			             $("#candidateSideDiv").empty();  
			 		     $("#candidateSideDiv").append(result);  
				 		 $(".clients_side_wrap").addClass("clients_side_wrap_active");
						 $(".side_mask").fadeIn(200);
						 $("body").css('overflow-y', 'hidden');
						 getCandidateTagsList(candidateId,candType);
						 
						 
						 if("${candidateKeyword}" == "requestedDocumentNotification"){
						    	$("#quickview").removeClass("active in");
						    	$("#timelineview").removeClass("active in");
						    	$("#detailview").addClass("active in");
						    	$("#candQuickView").removeClass("active");
						    	$("#candTimelineView").removeClass("active");
						    	$("#candDetailView").addClass("active");
					 	    	candidateDocuments(candidateId);
						    }else{
						    	candidateProfile(candidateId,candidateType,json.isAssociate,candType);
						    }
						 
						 var output = "";
						 output = output + '<a data-toggle="tab" href="#timelineview" onclick="getActivityDiv('+candidateId+')">Timeline</a>';
						 $("#candTimelineView").empty();  
			 		     $("#candTimelineView").append(output);
			 		     
			 		     
			 		    // Tags relatd code
			 		    var select, chosen;
			 		   select = $("#candidateTagName");
			 		   select.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add Keyword:' });
			 		   chosen = select.data('chosen');
			 		   chosen.search_field.on('keyup', function(e)
			 		   {
			 		   	var anyKeyword = this.value.replace(",","");
			 		       if ((e.which == 13 || e.which == 188) && (anyKeyword != null  && anyKeyword != '' && anyKeyword != 'undefined'))
			 		       {
			 		       	var par = this.parent;
			 		           var option = $("<option>").val(anyKeyword).text(anyKeyword);
			 		           select.append(option);
			 		           select.find(option).prop('selected', true);
			 		           $("#candidateTagName option").each(function(){
			 		                 $(this).siblings("[value='"+ this.value+"']").remove();
			 		           });
			 		   		$("#candidateTagName").trigger("chosen:updated");
			 		   		saveCandidateTags("add",candidateId);
			 		       }
			 		   });

			 		   $('#candidateTagName').chosen().on('change', function(evt, params) {
			 		   	if (params.selected && params.selected != undefined) {
			 		   		saveCandidateTags("add",candidateId);
			 		   	}
			 		   	if (params.deselected && params.deselected != undefined) {
			 		   		saveCandidateTags("delete",candidateId);
			 		   	}
			 		   	
			 		   });
			 		   $("#candidateTagName_chosen").find('ul.chosen-choices > li input').attr("placeholder", "Add Tag")

			 		   
                }else{
               	 $("#candidateSideDiv").removeAttr("style");
 	    	         var result ="";
		    	     result = result+"   <div class='row'>"; 
					 result = result+"	<div class='col-lg-12'>"; 
					 result = result+"		<div class='content-area clearfix'>"; 
					 result = result+"			<div class='blank-pages'>";
					 result = result+"			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
					 result = result+"			        <p>No Candidates Found</p>";
					 result = result+"			 </div>";
					 result = result+"		</div>"; 
					 result = result+"	</div>"; 
					 result = result+"  </div> "; 
					$("#candidateSideDiv").empty();
				    $("#candidateSideDiv").append(result); 
                }
         },
           
      }); 
	  
	  savedCommentAndCount(candidateId);
				
}
</script>

<!--  ============================= Candidate tags related scripts written by Laxman Start ================================ -->
<script>
function getCandidateTagsList(candidateId,candidateType){
	
	 if(candidateType != null && candidateType != 'undefined' && candidateType != '' && candidateType == 'candidateGlobal'){
		  candidateUrl =  atsUrl+"/getCandidatesGlobalTags?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId;
	  }else{
		  candidateUrl =  atsUrl+"/getCandidatesTags?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId;
	  }
	 
	$.ajax({  
        type : "POST",
        url: candidateUrl,        
        success :function(json) {
        	var result = "";
			$.each( json.tagsList, function( i, tags ) {
			  var existed = 0;
                if(json.candidateTags != null && json.candidateTags != 'undefined' && json.candidateTags != '') {
	                var res = [];
	                res = json.candidateTags.split(",");
	                res.forEach(function(item) {
	                	if(tags == item){
	                		existed = 1;
	                		result = result + ' <option selected value="'+tags+'">'+tags+'</option>';
	                	}
	                });
                }
                if(existed == 0){
                	result = result + ' <option value="'+tags+'">'+tags+'</option>';
                }
			});
            $("#candidateTagName").empty();
            $("#candidateTagName").append(result);
            $("#candidateTagName").chosen().trigger("chosen:updated")

        },
	});
}
function saveCandidateTags(type,candidateId){
	 var tagName = $("#candidateTagName").val();
	 var jsonData = encodeURIComponent(tagName);
	 var loginUserId = '${SourceLeadWeb_userId}';
	 var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	 if(tagName != null && tagName != '' && tagName !='undefined') {
		 Urlcheck = atsUrl+"/saveOrUpdateTagsBycandidateId?"+tokenParamName+"="+tokenValue+"&loginUserId="+loginUserId+"&orgLocationId="+orgLocationId+"&candidateId="+candidateId+"&tagName="+jsonData
	 } else {
		 Urlcheck = atsUrl+"/saveOrUpdateTagsBycandidateId?"+tokenParamName+"="+tokenValue+"&loginUserId="+loginUserId+"&orgLocationId="+orgLocationId+"&candidateId="+candidateId+"&tagName="+""
	 }
	 $.ajax({  
         type : "POST",
         url: Urlcheck,        
         success :function(result) {
			if(result && (result.response === "INVALID_DATA" || result.response === "INVALID_USER")) {
        		showError(result.responseMessage);
        	} else if (result.response === "AWKWARD") {
        	    location.href = "/errorPage";
        	} else if (result.response === "SUCCESS") {
        		if(type == "add"){
        			showSuccessStatusPopup1("Tags added successfully");
        		}else if(type == "delete"){
        			showDeleteStatusPopup("Tags deleted successfully");
        		}
        		getCandidateTagsList(candidateId);
        	} else if (result.indexOf('Sign In') !== -1) {
        		location.replace(sourceleadUrl);
        	} else {
        	    showError("Operation failed, please try again");
        	}
       },
    }); 
}

function showSuccessStatusPopup1(status) {
	$("#addCandidateTagAlert").css("display","block");
	$("#addCandidateTagAlert span").html(status);
    $('#addCandidateTagAlert').delay(2000).fadeOut('slow');
}
function showDeleteStatusPopup(status) {
	$("#deleteCandidateTagAlert").css("display","block");
	$("#deleteCandidateTagAlert span").html(status);
    $('#deleteCandidateTagAlert').delay(2000).fadeOut('slow');
}
</script>

<!--  ============================= Candidate tags related scripts written by Laxman Start ================================ -->

<%-- 
     @author : sarath kumar.

     this script is for show side bar when you click on the candidate name.
     1.usage     : called at saveOrCancel() function. line number 595.
     2.parameter : csrf token , candidateId .
     3.ajax url implementation in CandidateListRestController.
--%>


<script> 
$(document).ready(function() {
    $(".proj_show").click(function(){  
		$(".clients_side_wrap").addClass("clients_side_wrap_active");
		$(".side_mask").fadeIn(200);
		$("body").css('overflow-y', 'hidden');
	});
    $(".proj_close, .side_mask").click(function(){
		$(".clients_side_wrap").removeClass("clients_side_wrap_active");
		$(".side_mask").fadeOut(200);
		$("body").css('overflow-y', 'auto');
		
		clearJobAdvSearch();
		$(".new_side_wrap").removeClass("new_side_wrap_active");
		$(".req_side_mask").fadeOut(200);
		$("body").css('overflow-y', 'auto');
	});	
}); 
</script> 

<%-- 
     @author : sarath kumar.
     this script is for load predefined image for candidate.
     1.usage     : called at candidateSideBar() function. line number 166.
--%>

<script>
	function predefineProfileImage(imageId){   
		$(imageId).attr('src','/resources/images/userprofile-img.png');
	}
</script>



<%-- 
     @author : sarath kumar.
     this script is for delete image for candidate.
     1.usage     : this script is used to delete image
--%>
<script type="text/javascript">
function deleteImage(candidateId){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Are you sure you want to delete this image ?</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='deleteProfileImage("+candidateId+");this.disabled=true'><input class='u_can_color' type='button'  value='Cancel' onClick='imageCancel("+candidateId+");'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
</script>

<%-- 
     @author : sarath kumar.
     this script is for delete image for candidate.
     1.usage : this script is used to delete image
--%>
<script type="text/javascript">
function deleteProfileImage(candidateId){   
	var loginUserId = '${SourceLeadWeb_userId}';
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	 $.ajax({
		    type: "GET",
            url: atsUrl+"/deleteCandidateImage?candidateId="+candidateId+"&loginUserId="+loginUserId+"&orgLocationId="+orgLocationId,
	        success: function(response) {
	        	if (response.response === "AWKWARD") {
	        	    location.href = "/errorPage";
	        	} else if (response.response === "SUCCESS") {
	        		var result = response.object;
		        	 $("#popUp").empty();
		        	 $(".project_inn_mask").fadeOut(200);
		        	 $("[data-id=profileImage]").attr('src', '/resources/images/userprofileImg.jpg');
		        	 candidateQuickViewProfile(candidateId);
		        	 refreshImageBlock(candidateId);
	        	} else if (response.indexOf('Sign In') !== -1) {
	        		location.replace(sourceleadUrl);
	        	} else {
	        	    showError("Operation failed, please try again");
	        	}
	       },
     }); 
}


</script>

<%-- 
     @author : sarath kumar.
     this script is for refresh image block
     1.usage     : 
--%>
<script>
function refreshImageBlock(candidateId){   
	  $.ajax({
          type : "POST",
           url: atsUrl+"/getCandidateById?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId,        
           success :function(result) { 
        	   var json = result.object;
        	   if(result.response === "SESSION_TIMEDOUT") {
					location.replace(sourceleadUrl);
				} else if (result.response === "AWKWARD") {
					location.href = "/errorPage";
				} else if (result.response === "SUCCESS") {
					if(json != null){
	                	 $("#candidateImageId").empty();
	                	 var result = '';
			             result = result+'<div class="well well-sm text-center clearfix">';
						 result = result+' <div class="profile-img-container profile-image text-center" style="position:relative;">';
						 result = result+'   <img  id="userImage" data-id="profileImage" onerror="predefineProfileImage(this);" src="'+json.fileUrl+'" alt="Profile Image" class=" img-responsive center-block img-circle">';
						 result = result+'  <div class="btn-group uploadfile uploadfile-candidate">';
						 //result = result+'  <button type="button" class="btn btn-sm btn-primary">';
						 //result = result+'  <i class="fa fa-upload"></i></button>';
						 result = result+'  <label class="btn btn-sm btn-primary">';
					     result = result+'  <i class="fa fa-upload paddingRight"></i>Upload / Change<input type="file" name="file" id="uploadedImage" accept="image/*"  onchange="cropImage(this,\'candidate_form\','+candidateId+',false);" style="display: none;">';
					     result = result+'  </label>';
					     if(json.fileName != null && json.fileName != '' && json.fileName != 'undefined'){
						     result = result+'  <button type="button" class="btn btn-sm btn-danger" onclick="deleteImage('+candidateId+')">';
							 result = result+'  <i class="fa fa-trash"></i></button>';
					     } 
						 result = result+'  </div>';
						 result = result+'  </div>';
						 
						 result = result+'  </div>';
						 
						 result = result+' <div class="mb mt5 UsProBar">';
						 result = result+' <div class="progress nomr"> ';
						 if(json.profilePercentage>=50){
						 result = result+' <div class="progress-bar progress-bar-success ';
						 }
						 else{
						 result = result+' <div class="progress-bar progress-bar-danger ';
						 }
						 result = result+' progress-bar-striped active" role="progressbar" id="perVal" aria-valuenow="'+json.profilePercentage+'" aria-valuemin="0" aria-valuemax="100" style="width:'+json.profilePercentage+'%"> ';
						 result = result+' <p id="disVal">'+json.profilePercentage+'%</p> ';
						 result = result+' </div> ';
						 result = result+' </div> ';
						 
						 result = result+' <p class="text-danger small" style="font-size: 11px; display: none;" id="errorImage" >Invalid Image/Size</p>';
						 result = result+' <p class="text-muted small" style="font-size: 11px;"><i class="fa fa-info-circle"></i>&nbsp;png, svg and gif images are not supported</p>';
						 result = result+' <p class="text-muted small" style="font-size: 11px;"><i class="fa fa-info-circle"></i>&nbsp;Image size should not more than 200 KB</p>';
						 result = result+' </div>';
						 $("#candidateImageId").append(result);
                	}
				} else if (result.indexOf('Sign In') !== -1) {
					location.replace(sourceleadUrl);
				} else {
					showError("Operation failed, please try again");
				}
         	},
      }); 
   }
</script>


<script>
function addFavourite(candidateId){
	
	var checked = document.getElementById("fav"+candidateId).checked;
	 	$.ajax({
			type : "POST",
			url : atsUrl+"/saveCandidateAsFavourite?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId,
			success : function(response) {
				if(checked){
					 document.getElementById("favourite"+candidateId).removeAttribute("class");
					 document.getElementById("favourite"+candidateId).className = 'iconClass pull-left star-rating activestar-rating';
					 document.getElementById("fav"+candidateId).setAttribute("title","Change to un Favourite");
				} else {
					 document.getElementById("favourite"+candidateId).classList.remove("activestar-rating");
// 					 document.getEmentById("favourite"+candidateId).className = 'iconClass pull-left star-rating';
					 $("#fav"+candidateId).removeAttr("title");
					 document.getElementById("fav"+candidateId).setAttribute("title","Add as Favourite");
				}
				if (response && response.response === "INVALID_DATA") {
				    showError(response.responseMessage);
				} else if (response.response === "AWKWARD") {
				    location.href = "/errorPage";
				} else if (response.response === "SUCCESS") {
					var td = cacheLastJson.data;
					for(var e in td) {
						if(td[e].candidateId === candidateId) {
							td[e]
							if(isAssociate == "N"){
								td[e].isFavourite = checked ? 1 : 0;
			            	} else if(td[e].isFavouriteAssociate == '1' && isAssociate == "Y") {
			            		td[e].isFavouriteAssociate = checked ? 1 : 0;
			            	}
							break;
						}
					}

					cacheLastJson.data = td;
						

				} else if (response.indexOf('Sign In') !== -1) {
					location.replace(sourceleadUrl);
				} else {
				    showError("Operation failed, please try again");
				}
			},
		}); 
}
</script>


<%-- 
     @author : sarath kumar.
     this script is for validate image for candidate.
     1.usage     : used in candidateSideBar() function line number :171.
--%>
<script type="text/javascript">
function loadFile(event,candidateId){
	    var output = document.getElementById('userImage');
	    var uploadedImage = document.getElementById('uploadedImage').value;
	    var sizeinbytes = document.getElementById('uploadedImage').files[0].size;
	    document.getElementById("errorImage").style.display="none"; 
	    
	    var extension = uploadedImage.substring(uploadedImage.lastIndexOf(".")+1).toLowerCase();
	    
	    if(extension != 'jpg'){
	    	
	    	var extensionName = uploadedImage.substring(uploadedImage.lastIndexOf(".")+1);
	 		var msg = "."+extensionName+" extension not supported";
	 		alertMessage(msg,candidateId);
	    }else if(sizeinbytes > 200000){
	    	output.src = URL.createObjectURL(event.target.files[0]);
	 		var msg = "Image size should be less than 200 KB";
	 		alertMessage(msg,candidateId);
	    }else{
	    	output.src = URL.createObjectURL(event.target.files[0]);
	    	var msg = "Do you want to save the picture.";
	    	saveOrCancel(msg,candidateId);
	   } 
}
</script>

<%-- 
     @author : sarath kumar.
     this script is for show  alert message for candidate image format.
     1.usage     : used in loadFile() function line number :422.
--%>

<script type="text/javascript">
function alertMessage(message,candidateId){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>"+message+"</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='cancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
	refreshImageBlock(candidateId);

}
</script>

<%-- 
     @author : sarath kumar.
     this script is for conformation message to save file / not .
     1.usage     : used in loadFile() function line number :439.
--%>
<script type="text/javascript">
function saveOrCancel(message,candidateId){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>"+message+"</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onClick='saveCandidateImage("+candidateId+");' ><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
</script>

<%-- 
     @author : sarath kumar.
     this script is for close alert popup.
     1.usage     : used in saveOrCancel() / alertMessage function line number : 435 / 439.
--%>
<script type="text/javascript">
function cancel(candidateId){ 
	$("#popUp").empty();
	$(".project_inn_mask").fadeOut(200);
	refreshImageBlock(candidateId);
}
function imageCancel(){
	$("#popUp").empty();
	$(".project_inn_mask").fadeOut(200);
}

</script>


<!--HTML CONTENT -->

<section class="content-wraper">
  <section class="content-header clearfix">
  
   <div class="success_pop" id="candSelector" style="display: none;"> 
     <i class="fa fa-check-circle"></i><span id="candMsgDiv"></span> 
   </div>
   
   <c:if test="${!empty msg}">
	        <div class="success_pop" id="sucesspop"  style="display: block;">
		         <i class="fa fa-check-circle"></i><span>${msg}</span>
			</div>
	     </c:if>
    <div class="pull-left">
      <h2 id="headerLbl">Candidate List</h2>
    </div>
    
    <c:if test="${SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_RECRUITER'}">
    <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home </a> &raquo; <label id="brudCrumLbl">Candidate List </label> </div>
    </c:if> 
    
    <c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_RECRUITER'}">
    <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/recruiterDashboard">Home </a> &raquo; Candidate List  </div>
    </c:if> 
    
     <%-- <div class=" pull-left reportsH">
			<a title="Reports"><i class="fa fa-pie-chart"></i>Reports</a>
			<div class="RePop">
				<ul>
					<li><a href="${SourceLeadWeb_atsUrl}/candidateListForReports"><span><i class="fa fa-briefcase"></i></span>Added Candidates</a></li>
				</ul>
			</div>
		</div>   --%> 
		
  </section>
  <div class="clearfix">
	<ul class="project-tabs-menu projecttabs-custom clearfix">
         <li id="" class="current"><a href="javascript:void(0)" onclick="javascript:goToCandidateList();">Candidate List</a></li>
         <sec:authorize access="hasAnyRole('ROLE_SHARE_PROFILE','ROLE_ADD_PROFILE')">
         <li id=""><a href="javascript:void(0)" onclick="javascript:goToOrgMarketPlaceList();">Marketplace</a></li>
          <li id=""><a href="javascript:void(0)" onclick="javascript:goToGlobalMarketPlaceList();">Global Marketplace</a></li>
          <li id=""><a href="javascript:void(0)" onclick="javascript:goToCartList();">Cart</a></li>
           <li id=""><a href="javascript:void(0)" onclick="javascript:goToPurchaseOrderList();">Purchase Orderlist</a></li>
          <li id=""><a href="javascript:void(0)" onclick="javascript: goToSalesOrderList();">Sales Orderlist</a></li>
          <li id=""><a href="javascript:void(0)" onclick="javascript:goToWalletList();">Wallet</a></li>
         <div class="clearfix"></div>
         </sec:authorize>
     </ul>
</div>	
  		  <form:form id="candidatelist_form" name="candidatelist_form"  enctype='multipart/form-data' >
				  <!-- <div class="cand-advsrch-popup cand-advsrchPopup"> -->
				  <div class="cand-advsrch-popup advancedsearch-popup">
					  <h5 class="headerAdsrch">Advanced Search
					  <a href="javascript:void(0)" class="close-div" onclick="closePop();clearCandidateSearchKeys();"><i class="fa fa-close"></i></a>
					  </h5>
					  <div class="addInner">
					  <!-- <div class="candAdvsrchPop"> -->
					  <div class="row">
						  <div class="col-md-4">
							  <label>Any of Keyword</label>
							  <div class="clearfix">
							        <select class="chosen-select form-control input-sm" multiple data-placeholder="Any Keyword" id="anyOfKeyword" name="anyOfKeyword">
                                    </select>
							  	<!-- <input type="text" class="form-control input-sm" placeholder="Any of Keyword"> -->
							  </div>
						  </div>
						  <div class="col-md-4">
							  <label>All Keywords</label>
							  <div class="clearfix">
							  	 <select class="chosen-select form-control input-sm" multiple data-placeholder="All Keywords" id="allKeywords" name="allKeywords">
                                 </select>
							  </div>
						  </div>
						  <div class="col-md-4">
							  <label>Exclude Keyword</label>
							  <div class="clearfix">
							  	 <select class="chosen-select form-control input-sm" multiple data-placeholder="Exclude Keyword" id="excludeKeyWord" name="excludeKeyWord">
                                 </select>
							  </div>
						  </div>
					  </div>
					  <div class="row">
					  <div class="clearfix">
					  
					  
					  <div class="col-md-8">
						<label>Tags</label>
							<div class="clearfix">
								<select class="chosen-select form-control input-sm" multiple data-placeholder="Tags" id="canTags" name="candidateTags">
								</select>
							 </div>
						</div>
						
						
						<div class="col-md-8">
						<label>Immigration Status</label>
							<div class="clearfix">
								<select class="chosen-select form-control input-sm" multiple data-placeholder="Visa" id="canVisa" name="candidateVisa">
								<c:forEach var="visa" items="${visaList}">
								<option value="${visa.name}">${visa.name}</option>
								</c:forEach>
								</select>
							 </div>
						</div>
					  
					  <div class="clearfix">
						<div class="col-md-8 mt5">
						<label>Skills</label>
							<div class="clearfix">
								<select class="chosen-select form-control input-sm" multiple data-placeholder="Skills" id="canSkills" name="candidateSkills">
								<c:forEach var="skill" items="${skillsList}">
								<option value="${skill.name}">${skill.name}</option>
								</c:forEach>
								</select>
							 </div>
						</div>
						
						<div class="col-md-4">
						<label>Primary Skill</label>
							<div class="clearfix">
								<select class="chosen-select form-control input-sm" multiple data-placeholder="Primary Skill" id="primarySkill" name="primarySkill">
                                 </select>
							 </div>
						</div>
						
						 
					 </div>
						<c:set var="currency" value=""></c:set>
						<div class="col-md-8">
							<div class="row">
								<div class="col-md-6 payrate_p">
									<label>Rate</label>
									 <div class="input-group error-field">
										<div id="currencyDiv" class="input-group-btn selp-custom-min custommin">
										    <select class="selectpicker" name="currency" id="currency" field-name="Currency"  >
										        <option value="">Currency</option>
										         <option ${currency  == 'INR' ? 'selected' : ''}>INR</option>
								                 <option ${currency  == 'AUD' ? 'selected' : ''}>AUD</option>
								                 <option ${currency  == 'USD' ? 'selected' : ''}>USD</option>
								                 <option ${currency  == 'GBP' ? 'selected' : ''}>GBP</option>
								                 <option ${currency  == 'SGD' ? 'selected' : ''}>SGD</option>
								                 <option ${currency  == 'EUR' ? 'selected' : ''}>EUR</option>
				                            </select>
										</div>
										 <div id="minRateDiv">
									      <input type="text" id="minRate" name="minRate" field-name="Min Rate" data-validation="validate_Space validate_float" data-validation-optional="true"  class="form-control input-sm" placeholder="Min Rate">
								         </div>
								    </div>
								</div> 
							<div class="col-md-6 payrate_p">
								<label>&nbsp;</label> 
								<div class="input-group error-field">
								 <div id="maxRateDiv">
									  <input type="text" class="form-control input-sm" id="maxRate"  name="maxRate"  field-name="Max Rate"  data-validation="validate_Space validate_float" data-validation-optional="true"  placeholder="Max Rate">
								</div>
								<div id="rateTypeDiv" class="input-group-btn selp-custom-min custommin">
									      <select class="selectpicker" name="rateType" id="rateType" field-name="Rate Type"  >
									            <option value="">Rate Type</option>
										        <option value="Hourly">Hourly</option>
												<option value="Weekly">Weekly</option>
												<option value="Bi-weekly">Bi-weekly</option>
												<option value="Semi-Monthly">Semi-Monthly</option>
												<option value="Monthly">Monthly</option>
												<option value="Bi-Monthly">Bi-Monthly</option>
												<option value="Yearly">Yearly</option>
				                            </select> 
									</div>
								</div>
							</div>
						 </div>
						 </div>
						 
						 <div class="clearfix">
						  <div class="col-md-4">
						    <label>Designation</label>
						     <div class="clearfix">
						             <select class="chosen-select form-control input-sm" multiple data-placeholder="Designation" id="designation" name="designation">
                                     </select>
						    </div>
							</div>
						 </div>
						 
						  <div class="clearfix">
						  <div class="col-md-4">
							  <label>Experience</label>
							  <div class="clearfix row">
							  <div class="col-md-6">
								  <select class="chosen-select"  id="minExpYears" name="minExpYears" data-placeholder="Years" onblur="minMaxExperience();">
									  <option  selected value="">Min Years</option>
									 <c:forEach begin="0" end="24" varStatus="loop">
									    <option value="${loop.index}">${loop.index} Years</option>
									</c:forEach>
								  </select>
							  </div>
							  <div class="col-md-6">
								  <select class="chosen-select" id="maxExpYears" name="maxExpYears" data-placeholder="Months" onblur="minMaxExperience();">
									  <option  value="">Max Years</option>
									  <c:forEach begin="0" end="25" varStatus="loop">
									    <option value="${loop.index}">${loop.index} Years</option>
									</c:forEach>
								  </select>
							  </div>
							  </div>
							  <div class="row" id="expId">
				                  <input type="hidden" name="expValidation" id="expValidation">
				              </div>
						  </div>
						  
						  <div class="col-md-4">
							  <label>Notice Period</label>
							  <div class="clearfix">
								 <select class="chosen-select" id="noticePeriod1" name="noticePeriod" data-placeholder="Notice Period" multiple>
						                 <option value="Immediately available">Immediately available</option>
						                 <option value="15 Days">15 Days</option>
						                 <option value="1 Month">1 Month</option>
						                 <option value="2 Months">2 Months</option>
						                 <option value="3 Months">3 Months</option>
				                </select>
							  </div>
						  </div>
						    <div class="col-md-4">
						    <label>Employment Type</label>
						     <div class="clearfix">
						             <select class="chosen-select form-control" id="designationType" name="designationType">
						                <option value="">--Select--</option>
						                 <option>C2C</option>
						                 <option>W-2</option>
						                 <option>C2H</option>
						                 <option>Part Time</option>
						                 <option>Full Time</option>
						                 <option>Other</option>
						             </select>
						    </div>
						  </div>
						  </div>
						  <div class="clearfix">
						   <div class="col-md-4">
							  <label>Country</label>
							  <div class="clearfix">
								 <select class="chosen-select" name="country" id="country" field-name="Country"  >
								    <option value="">--Select country--</option>
								     <c:forEach var="cntry" items="${countryList}">
							                      <option value="${cntry.name}">${cntry.name}</option>
						              </c:forEach>
				                 </select>
							  </div>
						  </div>
						  
						  <div class="col-md-4"> 
							  <label>State</label>
							  <div class="clearfix">
								  <select  class="chosen-select" name="state" id="state" field-name="State"  >
								        <option value="">---select state---</option>
								        <%-- <c:forEach var="state" items="${statesList}">
							                     <option value="${state.stateName}">${state.stateName}</option>
						                </c:forEach> --%>
                                 </select>
							  </div>
						  </div>
						  <div class="col-md-4">
							  <label>City</label>
							  <div class="clearfix">
								  <select  class="chosen-select" name="city" id="citySearch" field-name="City"  >
								      <option value="">---select city---</option>
                                  </select>
							  </div>
						  </div>
						  </div>
						  <div class="clearfix">
					  	<div class="col-md-4">
							  <label>Education</label>
							  <div class="clearfix"> 
								  <select class="chosen-select form-control input-sm" multiple data-placeholder="Education" id="education" name="education">
                                       <c:forEach var="edu" items="${educationsList}">
							                     <option value="${edu.name}">${edu.name}</option>
						                </c:forEach>
                                  </select>
							  </div>
						  </div> 
						  <div class="col-md-4">
							  <label>Exclude Education</label>
							  <div class="clearfix">
								   <select class="chosen-select form-control input-sm" multiple data-placeholder="Exclude Education" id="excludeEducation" name="excludeEducation">
                                       <c:forEach var="edu" items="${educationsList}">
							                     <option value="${edu.name}">${edu.name}</option>
						                </c:forEach>
                                   </select>
							  </div>
						  </div>
						  <div class="col-md-4">
							  <label>Certification</label>
							  <div class="clearfix">
								  <select class="chosen-select form-control input-sm" multiple data-placeholder="Certification" id="certification" name="certification">
                                  </select>
							  </div>
						  </div>
						  </div>
						  <div class="clearfix">
						  <div class="col-md-4">
							  <label>Employers</label>
							  <div class="clearfix">
								  <select class="chosen-select form-control input-sm" multiple data-placeholder="Employers" id="employers" name="employers">
                                  </select>
							  </div>
						  </div>
						  <div class="col-md-4">
							  <label>Exclude Employers</label>
							  <div class="clearfix">
								 <select class="chosen-select form-control input-sm" multiple data-placeholder="Exclude Employers" id="excludeEmployers" name="excludeEmployers">
                                  </select>
							  </div>
						  </div>
						  
						   <!-- <div class="col-md-4">
							  <label>Applicant Type</label>
							  <div class="clearfix">
								   <select class="chosen-select" name="applicantType" id="applicantType" field-name="Applicant Type"  >
								      <option value="">---select Applicant Type---</option>
								      <option value="candidate">Candidate</option>
									  <option value="consultant">Consultant</option> 
				                   </select>
							  </div>
						  </div> -->
						  </div>
					  </div>
					  </div>
				  </div>
				  <div class="clearfix advancedsearch-footer">
						  <div class="pull-right mr">
							   <input type="button" value="Search" id="search_candidate" onclick="candidateList(); closePop();minMaxExperience();" class="btn btn-sm btn-success"/>
                               <input type="button"  value="Reset"  onClick="clearCandidateSearchKeys();"class="btn btn-sm btn-primary"/>
                               <!--  <input type="button" value="Cancel" onClick="closePop();clearCandidateSearchKeys();" class="btn btn-sm btn-danger"/> -->
						  </div>
					  </div>
				  </div>
			  </form:form>
  
  <div class="timesheet_out" id="candidatesInfoDiv">
    
 <div class="positionRelative reportHead clearfix">
      <span class="pull-left refreIcon" id="advanceSearchDiv">
      		<a href="javascript:void(0)" class="refeIcon" id="resetVendor" title="Refresh" onclick="resetCandidateFilter();"></a>
      		<a href="javascript:void(0)" class="newAdBtn pull-left" title="Advanced Search" onClick="openPop();getSourceleadTags();"> Advanced Search &nbsp; <i class="fa fa-search"></i> </a>
      </span>
      
      <ul class="pull-right list-unstyled list-inline">
      
	  <li class="tableShare" style="display: none;">
	   	<sec:authorize access="hasAnyRole('ROLE_SHARE_PROFILE','ROLE_ADD_PROFILE')">
	  	<a class="marketplaceIcon mr5" title="Market Place" onclick="callAddToMarket()"></a>
	  	</sec:authorize>
	  	<a class="shareIcon mr5" title="Share" href="javascript:void(0)" onclick="shareJob();"></a>
	  	<a class="sendIcon mr5" title="Submit" href="javascript:void(0)" onclick="submitCandidatesToJob();"></a>
	  </li>        
 
           <sec:authorize access="hasAnyRole('ROLE_CREATE_CANDIDATE','ROLE_CROWDSOURCING_CANDIDATES')">
          <!-- <li><b id="addmore"><a onClick="addCandidate();" title="Add Candidate" class="newAdBtn mr5" href="javascript:void(0)">+  Candidate</a></b>
          		<div id="addmorepop" class="approved_pop"></div>
          </li> -->
          
          <li>
				<a class="action newAdBtn mr5" onclick="getActions();" href="javascript:void(0)">+ Candidate <i class="fa fa-caret-down"></i></a>
				<div class="action-pop">
					<span> 
						<a href="${SourceLeadWeb_atsUrl}/candidate">Parse Resume</a>
                        <a href="${SourceLeadWeb_atsUrl}/addCandidate">Add Manual</a>
                        <a href="${SourceLeadWeb_sourceleadUrl}/bulk-resume-import.html">Bulk Resume Parsing</a>
                        <a href="${SourceLeadWeb_sourceleadUrl}/uploadCandidateForm">Import XLS</a>
					</span>
				</div>
			</li>
          
          
          </sec:authorize>
      
				<li><a href="javascript:void(0)" class="filtIcon" title="Filters" onclick="openCandidateFilterPop();"></a></li>
				
				<li><a href="javascript:void(0)" class="overIcon" title="Actions" onclick="newActions();"></a>
					<div class="newActPop">
						<span>
         				 <sec:authorize access="hasAnyRole('ROLE_EXPORT_CANDIDATES','ROLE_CROWDSOURCING_CANDIDATES')">
          					<a href="javascript:void(0)" onclick="exportToPdf();" title="PDF">Export PDF</a>
							<a href="javascript:void(0)" onclick="exportToExcel();" title="Excel">Export Excel</a>
          				</sec:authorize>		           
						</span>
					</div>
				</li>
				
		  <li class=""><input type="text" class="repSearchBar" placeholder='Search' id='dataTableSearch'/><span class="repSearchIcon" onClick="candidateList();"></span></li>
        </ul>
      </div>   
    
      <div id="emptyPopup"></div>
      <div class="candidate_wrap_table datatable_filter">
        <div id="candidateList"></div>
      </div>
    </div>
    
    <!-- Compnay details -->
    <div class="side_mask">
       <div class="proj_close" onclick="activeTab();"></div>
    </div>
    <div class="success_pop" id="statusAlert">
		<i class="fa fa-check-circle"></i>
   		<span></span>
   </div>
   <div class="success_pop tagStyles" id="addCandidateTagAlert" style="z-index: 99999;">
		<i class="fa fa-check-circle"></i>
   		<span></span>
   </div>
   <div class="remove_pop tagStyles" id="deleteCandidateTagAlert"  style="z-index: 99999;">
		<i class="fa fa-times"></i>
   		<span></span>
   </div>
   
 <div class="clients_side_wrap quickviewTabs">
    
 <ul class="nav nav-tabs">
    <li class="active" id="candQuickView"><a data-toggle="tab" href="#quickview">Quickview</a></li>
    <li id="candDetailView"><a data-toggle="tab" href="#detailview">Detailed View</a></li>
    <li id="candTimelineView"></li>
  </ul>

  <div class="tab-content">
    <div id="quickview" class="tab-pane fade in active">
	    <!-- resumeView Starts -->
	    <div id="candidateQuickViewDiv"></div>
	    <!-- resumeView -->
    </div>
    <div id="detailview" class="tab-pane fade">
	    <div class="clients_side_wrap_inn">
	             <div id="candidateSideDiv">
			     </div>  
	    </div>
	     </div>
				<div id="timelineview" class="tab-pane fade">
					<div class="clients_side_wrap_inn">
						<div id="candidateTimelineDiv"></div>
					</div>
				</div>
			</div>			     
    </div>
    <!-- Compnay details Ends --> 
</section>

<!--   Error Message   -->
  <div class="vendor_wrap_mask"></div>
  <div class="vendor_error">
  <p>Delete conformation message....</p>
  <textarea placeholder="Enter message"></textarea>
  <a class="close_color" href="javascript:void(0)">Yes</a><a href="javascript:void(0)">No</a> </div>
<!--   Error Message   --> 

   
   <script>
   var candidateIdsMark = [];
   function appendIdsToMarketPlace(obj){
	   if($(obj).is(":checked")){
		   candidateIdsMark.push($(obj).attr("id"));
	   }else if($(obj).not(":checked")){
		   candidateIdsMark = jQuery.grep(candidateIdsMark, function(value) {
			   return value != $(obj).attr("id");
			});
	   }
   }
   
   function callAddToMarket(){
	 	 $.ajax({
		    	type: "POST",
		        url: atsUrl+"/getAddedMarketplaceCandidates",
		        data : {candidateIdsVal : candidateIdsMark},
		        success: function(json) {
		        	candidateIDsArr = [];
		        	popupDisplay(json['candidatesInMarketplace'].length, json['candidatesNotInMarketplace'].length, json['candidatesNotInMarketplace'] );
		        }
	 	}); 
 }
  
   function getSourceleadTags(){
 		 $.ajax({
		    	type: "GET",
		        url: atsUrl+"/getSourceleadTagsList?"+tokenParamName+"="+tokenValue, 
		        success: function(json) {
		        	var output = "";
		        	for (var i=0; i<json.length; i++)
					{
			        		var opt = json[i];
			        		output = output + "<option value='" +opt+ "'>"+opt+"</option>";
			        		
					}
		        	$("#canTags").empty();
			        $("#canTags").append(output);
			        $('#canTags').trigger("chosen:updated");
		        },
		    }); 
   }
   </script>
   
   
   <script>
	   function msgFadein() {
			$("#candSelector").fadeIn(200);
		    $('#candSelector').delay(2000).fadeOut('slow');
		}
   </script>
   
<script>
	function activeTab() {
		$("#candDetailView").removeClass("active");
		$("#candQuickView").addClass("active");
		$("#quickview").addClass("active in");
		$("#detailview").removeClass("active in");
		$("#candTimelineView").removeClass("active");
		$("#timelineview").removeClass("active in");
		$(".location-alert").fadeOut();
		
		$(".new_side_wrap").removeClass("new_side_wrap_active");
		$(".req_side_mask").fadeOut(200);
		$("body").css('overflow-y', 'auto');
 		candidateList();
	}
</script>

 <script>
  $('#selector').delay(2000).fadeOut('slow');
</script> 
<!-- BELOW SCRIPTS ARE USED TO SHOW / HIDE POPUP'S --> 
<script>
// Error pop
$('#sucesspop').delay(2000).fadeOut('slow');
$(document).ready(function() {
    $(".glyphicon-trash").click(function(){
		$(".vendor_error").slideToggle(200);
		$(".vendor_wrap_mask").fadeIn(200);
	});
	$(".vendor_error a").click(function(){
		$(".vendor_error").slideUp(200);
		$(".vendor_wrap_mask").fadeOut(200);
	});
});	

// Approved Popup
    $("#appro-pop").click(function(){
		$(".approved_pop").toggle(200);
		
	});
    $("html").click(function() {
        $(".approved_pop").hide(200);
    });
    $(".approved_pop, #appro-pop").click(function(e) {
        e.stopPropagation();
    });	

// Export Popup
    $("#candidate-export").click(function(){
		$(".export_pop").toggle();
		$("#filter2ApprovedPop, #filter3ApprovedPop, #filter1ApprovedPop, #addmorepop").slideUp();
	});
    $("html").click(function() {
        $(".export_pop").hide(200);
    });
    $(".export_pop, #candidate-export").click(function(e) {
        e.stopPropagation();
    });	
//add more popu

$("#addmore").click(function(){
	$(this).next(".approved_pop").toggle();
		//$(".approved_pop").toggle(200);
	$("#filter2ApprovedPop, #filter3ApprovedPop, #filter1ApprovedPop, #filter4ApprovedPop").slideUp();
	});
$("html").click(function() {
    $(".approved_pop").hide(200);
});
$(".approved_pop, #addmore").click(function(e) {
    e.stopPropagation();
});	
    
// project side bar tabs
$(document).ready(function() {
	$("#tab-1").show()
    $(".project-tabs-menu a").click(function(event) {
        event.preventDefault();
        $(this).parent().addClass("current");
        $(this).parent().siblings().removeClass("current");
        var tab = $(this).attr("href");
       // $(".project_tabs_inn").not(tab).css("display", "none"); // commented due to script error in UI.
       // $(tab).fadeIn();
    }); 

	
});
</script>	

<script>
$(document).ready(function() {
	
	
    $("#filter1ApprovePop").click(function(){
		$("#filter1ApprovedPop").slideToggle(200);
		$(".export_pop, #filter3ApprovedPop, #addmorepop, #filter2ApprovedPop").slideUp();
	});
    $("html").click(function() {
        $("#filter1ApprovedPop").slideUp(200);
    });
    $("#filter1ApprovedPop, #filter1ApprovePop").click(function(e) {  
        e.stopPropagation();
    });	
    
    
    $("#filter2ApprovePop").click(function(){
		$("#filter2ApprovedPop").slideToggle(200);
		$(".export_pop, #filter3ApprovedPop, #filter1ApprovedPop, #addmorepop").slideUp();
	});
    $("html").click(function() {
        $("#filter2ApprovedPop").slideUp(200);
    });
    $("#filter2ApprovedPop, #filter2ApprovePop").click(function(e) {  
        e.stopPropagation();
    });	
    
    $("#filter3ApprovePop").click(function(){
		$("#filter3ApprovedPop").slideToggle(200);
		$(".export_pop, #filter2ApprovedPop, #filter1ApprovedPop, #addmorepop").slideUp();
	});
    $("html").click(function() {
        $("#filter3ApprovedPop").slideUp(200);
    });
    $("#filter3ApprovedPop, #filter3ApprovePop").click(function(e) {  
        e.stopPropagation();
    });	
    
  
    
}); 

</script>

<script>
function toggle(source) {
    var checkboxes = document.querySelectorAll('input[type="checkbox"]');
    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i] != source)
            checkboxes[i].checked = source.checked;
        
    }
$('input:checkbox:not(#checkAll)').click(function() {
    if(!this.checked) {
        $('#checkAll').removeAttr('checked');
    }
});
}
</script>

<script>
function exportToPdf(){
	var isCandidatesAvailable= $("#noCandidates").val();
	  var candFilterRadio = $('input[name=candidateStatus]');
	  var candidateStatus = candFilterRadio.filter(':checked').val();
	  if(candidateStatus == 1){
        $("#canDiv").text("Active Candidates");
    }else if(candidateStatus == 0){
  	  $("#canDiv").text("In Active Candidates");
    }else if(candidateStatus == 'ALL'){
  	  $("#canDiv").text("All Candidates");
    }else{
        $("#canDiv").removeAttr("onClick");
    }
	  
    var candidateFilterSkills = [];
	 $('input:checkbox[name=candidateFilterSkills]:checked').each(function() 
     {
		 candidateFilterSkills.push($(this).val());
	 });
	 
	 var availability = []
	 $('input:checkbox[name=availability]:checked').each(function() 
	     {
	       availability.push($(this).val());
		 });
	 
	 var candidateTypeFilter = []
	 $('input:checkbox[name=candidateTypeFilter]:checked').each(function() 
	     {
		 candidateTypeFilter.push($(this).val());
		 });
	 
	 var createdByRadio = $('input[name=createdByFilter]');
	  var createdByFilter = createdByRadio.filter(':checked').val();
	 
	  var candTypeRadio = $('input[name=candidateType]');
	  var candidateType = candTypeRadio.filter(':checked').val();
	  if(isCandidatesAvailable > 0){
	  window.location.href = atsUrl+"/getCandidatesExportList?&candidateStatus="+candidateStatus+"&exportType=pdf"+"&candidateType="+candidateType+"&candidateFilterSkills="+candidateFilterSkills+"&availability="+availability+"&candidateTypeFilter="+candidateTypeFilter+"&createdByFilter="+createdByFilter+"&isAssociate=N";	
	  }else{
		  var result = "";
			result = result +"	<div class='project_inn_mask'></div>";
			result = result +"	   <div id='candidate_user_pop' class='remove_user_pop remove_user_pop_a'>";
			result = result +"		<p>Candidates List is Empty</p>";
			result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
			result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='candidtaeCancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='candidtaeCancel();'></b>";
			result = result +"	</div>";
			$("#emptyPopup").append(result);
			$("#candidate_user_pop").slideDown(200);
			$(".project_inn_mask").fadeIn(200);
	  }
}

/* Dont remove ... value */
function popupDisplay(candidatesInMarketplace, noCandidateNotInMarketplace, ...candidatesNotInMarketplace){
	$(".project_inn_mask").remove();
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_immigration_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"<div class='modal-body'><p id='popupMsg'></p></div>";
	result = result + ' <div id="reasonForTemplateDiv"></div>';
	result = result +"<div class='clearfix'><input class='u_can_color width100percnt' type='button'  value='Ok' onClick='cancelMarketplacePopup("+candidatesNotInMarketplace+");'></div>";
	result = result +"	</div>";
	$("#emptyPopup").empty().append(result);
	$("#popupMsg").empty().append(candidatesInMarketplace+" has already processed for the Marketplace, <br/> "+noCandidateNotInMarketplace+" will process for the marketplace.");
	$("#remove_immigration_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}

/* Dont remove ... value */
function cancelMarketplacePopup(...candidatesNotInMarketplace){
	$("#remove_immigration_pop").fadeOut(200);
	$(".project_inn_mask").fadeOut(200);
	if(candidatesNotInMarketplace.length != 0){
		$.ajax({
	    	type: "POST",
	        url: atsUrl+"/navigateMarketPlaceAjax",
	        data : {candidateIdsVal : candidatesNotInMarketplace},
	        success: function(json) {
	        	location.href = atsUrl+"/navigateMarketPlaceList";
	        	//candidatesNotInMarketplace = [];
	        }
 	});
	}
}
</script>
<script type="text/javascript">
function candidtaeCancel(){
	$("#emptyPopup").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>
<script>
function exportToExcel(){
	var isCandidatesAvailable= $("#noCandidates").val();
	  var candFilterRadio = $('input[name=candidateStatus]');
	  var candidateStatus = candFilterRadio.filter(':checked').val();
	  if(candidateStatus == 1){
       $("#canDiv").text("Active Candidates");
   }else if(candidateStatus == 0){
 	  $("#canDiv").text("In Active Candidates");
   }else if(candidateStatus == 'ALL'){
 	  $("#canDiv").text("All Candidates");
   }else{
       $("#canDiv").removeAttr("onClick");
   }
	  
	  var candidateFilterSkills = [];
		 $('input:checkbox[name=candidateFilterSkills]:checked').each(function() 
	     {
			 candidateFilterSkills.push($(this).val());
		 });
		 
		 var availability = []
		 $('input:checkbox[name=availability]:checked').each(function() 
		     {
		       availability.push($(this).val());
			 });
		 
		 var candidateTypeFilter = []
		 $('input:checkbox[name=candidateTypeFilter]:checked').each(function() 
		     {
			 candidateTypeFilter.push($(this).val());
			 });
		 
		 var createdByRadio = $('input[name=createdByFilter]');
		  var createdByFilter = createdByRadio.filter(':checked').val();
	  
	  var candTypeRadio = $('input[name=candidateType]');
	  var candidateType = candTypeRadio.filter(':checked').val();
	  if(isCandidatesAvailable > 0){
	  window.location.href = atsUrl+"/getCandidatesExportList?&candidateStatus="+candidateStatus+"&exportType=xls"+"&candidateType="+candidateType+"&candidateFilterSkills="+candidateFilterSkills+"&availability="+availability+"&candidateTypeFilter="+candidateTypeFilter+"&createdByFilter="+createdByFilter+"&isAssociate=N";	
	  }else{
		  var result = "";
			result = result +"	<div class='project_inn_mask'></div>";
			result = result +"	   <div id='candidate_user_pop' class='remove_user_pop remove_user_pop_a'>";
			result = result +"		<p>Candidates List is Empty</p>";
			result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
			result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='candidtaeCancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='candidtaeCancel();'></b>";
			result = result +"	</div>";
			$("#emptyPopup").append(result);
			$("#candidate_user_pop").slideDown(200);
			$(".project_inn_mask").fadeIn(200);
	  }
}
</script>

<script>
function showComment(candidateId){
	var result = "";
		result = result+'	        <form role="form" id="candidateComments_form" name="candidateComments_form" method="post">';
	    result = result+'              <textarea rows="1" class="form-control clearfix mb10" placeholder="Comments here..." id="comment" name="comment" data-validation="required validate_length length2-500" field-name="Comment"></textarea>';
	    result = result+'              <span class="clearfix pull-right">';
	    result = result+'              		<input type="button" id="submit_comment" value="Add" class="btn btn-sm btn-success" onClick="addComment('+candidateId+')">';
	    result = result+'              		<input type="button" value="Cancel" class="btn btn-sm btn-default" onClick="closeComment()">';
	    result = result+'              </span>';
		result = result+'	        </form>';
	$('.add-comment').empty();
	$('.add-comment').append(result);
	$('.add-comment').toggle(200);
}
function closeComment(){
	$('.add-comment').toggle(200);
}
</script>


<%-- 
     @author : Venkat Kesav
     this script is for saving Comment
     1.usage     : onclick
     2.parameter : candidateId.
     3.ajax url implementation in CandidateResumeRestController.
--%>
<script type="text/javascript">
	function addComment(candidateId){
			var validationSettings = {
			        errorMessagePosition : 'element',
			        borderColorOnError : '',
					scrollToTopOnError : true,
					dateFormat : 'yyyy/mm/dd' 
		         }; 
			 if ($('#candidateComments_form').validate(false, validationSettings)) {
	      		 var formData = getFormData("candidateComments_form");
				$("#submit_comment").attr("disabled", "disabled");
         		$("#submit_comment" ).addClass('button').val('Processing..');
				
				$.post(atsUrl+"/saveCandidateComment?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId,formData,function(result){
					if (result && result.response === "INVALID_USER") {
		    		    showError(result.responseMessage);
		    		} else if (result.response === "AWKWARD") {
		    		    location.href = "/errorPage";
		    		} else if (result.response === "SUCCESS") {
		    			savedCommentAndCount(candidateId);
		    		} else if (result.indexOf('Sign In') !== -1) {
						location.replace(sourceleadUrl);
					} else {
					    showError("Operation failed, please try again");
					}
				});
				$('.add-comment').toggle(200);
				return true;
			 } else { 
					return false;
			 }
		}
		$('body').on('blur', '#candidateComments_form', function() {
		$("#candidateComments_form").showHelpOnFocus();
		$("#candidateComments_form").validateOnBlur(false, validationSettings);
		
	});
</script>




<script>
function savedCommentAndCount(candidateId){
	  $.ajax({
        type : "POST",
         url: atsUrl+"/getAllCommentsOnCandidateByCandidateIdAndOrgLocation?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId,        
         success :function(data) {
        	 if (data && data.response === "INVALID_DATA") {
        		    showError(data.responseMessage);
        		} else if (data.response === "AWKWARD") {
        		    location.href = "/errorPage";
        		} else if (data.response === "SUCCESS") {
        			var result = "";
                    if(data.data.length > 0) {
                    	var json = data.data;
                  		var totalComments = 0;
                  		if(json.length>0) {
                  			totalComments = json.length;
            	            var report = json[0];
   	 			            
   	 			            result = result+'    <div class="commentsimg">';
   				            result = result+'    <i class="fa fa-comments"></i>';
   				            result = result+'        <div class="commentsimg-inner">';
   				            
   				            result = result+'        	<img data-id="userProfileImage" onerror="predefineProfileImage(this);" src="'+report.commentAuthorUserImage+'"/>';
   				            result = result+'        </div>';
   				            result = result+'    </div>';
   				            result = result+'     <div class="comments-content">';
   				            result = result+'         <div class="pull-left" id="latestMessage">';
   				            result = result+'         <p class="comment-by">';
   				            result = result+'            By <span class="text-danger">'+report.commentAuthor+'</span>';
   				            result = result+'            <span class="small">('+report.commentTime+')</span></p>';
   				            result = result+'         <p class="comment-text">'+report.comment+'</p>';
   				            result = result+'         </div>';
   				            result = result+'         <div class="pull-right cmt-button">';
   				            result = result+'          <span class="comments-button">';
   				            
   				            result = result+'          	<button type="button" class="btn btn-sm btn-primary" onClick="showComment('+candidateId+')">';
   				            result = result+'            	<i class="fa fa-plus"></i>&nbsp;Comments';
   				            result = result+'            </button>';
   				            result = result+'            <div class="add-comment">';
   				            result = result+'            </div>';
   				            result = result+'          </span>';
   				            result = result+'          <a href="javascript:void(0)" class="comments-view" onclick="candidateCommentsList('+candidateId+');">View more <span class="label label-warning">'+totalComments+'</span></a>';
   				 			result = result+'		</div>';
   				            result = result+'     </div>';
   	 			           
   	 			           	$("#latestMessage").empty();
   	 			           	$("#latestMessage").append(result);
                  		}
					}
        		} else if (data.indexOf('Sign In') !== -1) {
        			location.replace(sourceleadUrl);
        		} else {
        		    showError("Operation failed, please try again");
        		}
			},
      });
}
</script>

    <script>
	// Close Advanced Search
		function closePop(){
			 $("div.cand-advsrch-popup").hide();
			 $('#expId').find('p.jquery_form_error_message').remove();
			 $(".advancedsearch-mask").fadeOut();
		}
		function openPop(){
		  $("#dataTableSearch").val('');
		  $("div.cand-advsrch-popup").toggle();
		  $(".advancedsearch-mask").fadeIn();
		  $("#candidateFilterPopup").toggle();
		};
		/* function closeAdsrch(){
			$("div.cand-advsrch-popup").fadeOut();
			$(".advancedsearch-mask").fadeOut();
		}; */
	</script>
<script>
function getStates(){
	var countryName = $("#country").val();
	if(countryName != null && countryName != "" && countryName !="undefined"){
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: atsUrl +"/getStatesByCountry?"+tokenParamName+"="+tokenValue+"&countryName="+countryName, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#state').empty();
	        	$('#state').append('<option value="">---select state---</option>');
	            for(var i=0;i<json.length;i++){
	            	$('#state').append('<option value="'+json[i]+'">'+json[i]+'</option>');
	            }
	            $('#state').trigger("chosen:updated");
	            
	            $('#citySearch').empty();
	            $('#citySearch').append('<option value="">---select city---</option>');
	            $('#citySearch').trigger("chosen:updated");
	        },
	        error: function(e){
	        }
	    });
	}else{
		$('#state').empty();
    	$('#state').append('<option value="">---select state---</option>');
    	$('#state').trigger("chosen:updated");
    	$('#citySearch').empty();
    	$('#citySearch').append('<option value="">---select city---</option>');
        $('#citySearch').trigger("chosen:updated");
	}
}
</script> 


<script>
function getCities(){
	var countryName = $("#country").val();
	var stateName = $("#state").val();
	if(countryName != null && countryName != "" && countryName !="undefined" && stateName != null && stateName != "" && stateName !="undefined" ){
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: atsUrl+"/getCitiesByStateAndCountry?"+tokenParamName+"="+tokenValue+"&countryName="+countryName+"&stateName="+stateName, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#citySearch').empty();
	        	$('#citySearch').append('<option value="">---select city---</option>');
	            for(var i=0;i<json.length;i++){
	            	$('#citySearch').append('<option value="'+json[i]+'">'+json[i]+'</option>');
	            }
	            $('#citySearch').trigger("chosen:updated");
	        },
	        error: function(e){
	        }
	    });
	}else{
		$('#citySearch').empty();
    	$('#citySearch').append('<option value="">---select city---</option>');
    	$('#citySearch').trigger("chosen:updated");
	}
}
</script>


<!-- Country,State,City Script Ends Here -->	
<script type="text/javascript">	
	function loadChosenScripts(){
	    
	    $('#minRate').val(''); 
	    $('#maxRate').val(''); 
	    $('#currency').val(''); 
	    $('#rateType').val(''); 
	    
	    /* $('#country').val(''); 
	    $("#country").val('').trigger("chosen:updated"); */
	    /* $("#state").empty();
	    $("#state").val('').trigger("chosen:updated"); */
	    $("#citySearch").empty();
	    $("#citySearch").val('').trigger("chosen:updated");
	    $('#rateType').val(''); 

	    $('#emailDiv').find('p.jquery_form_error_message').remove();
	    $('#search_candidate').removeAttr('disabled');
	    $('#minRateDiv').find('p.jquery_form_error_message').remove();
	    $('#maxRateDiv').find('p.jquery_form_error_message').remove();
	    $('#currencyDiv').find('p.jquery_form_error_message').remove();
	    $('#rateTypeDiv').find('p.jquery_form_error_message').remove();
	    
	    
	    $("#anyOfKeyword").val('').trigger("chosen:updated");
	    $("#allKeywords").val('').trigger("chosen:updated");
	    $("#excludeKeyWord").val('').trigger("chosen:updated");
	    $("#designation").val('').trigger("chosen:updated");
	    $("#education").val('').trigger("chosen:updated");
	    $("#excludeEducation").val('').trigger("chosen:updated");
	    $("#certification").val('').trigger("chosen:updated");
	    $("#employers").val('').trigger("chosen:updated");
	    $("#excludeEmployers").val('').trigger("chosen:updated");
	    $("#canSkills").val('').trigger("chosen:updated");
	    $("#canVisa").val('').trigger("chosen:updated");
	    $("#canTags").val('').trigger("chosen:updated");
	    $("#noticePeriod1").val('').trigger("chosen:updated");
	    $("#primarySkill").val('').trigger("chosen:updated");
	    
	    $("#anyOfKeyword").empty();
	    $("#allKeywords").empty();
	    $("#excludeKeyWord").empty();
	    $("#designation").empty();
	    $("#certification").empty();
	    $("#employers").empty();
	    $("#excludeEmployers").empty();
	}
</script>



<script>
  $(function() { 
	       //Adding Customized Any KeyWord Title list this method is slead-common.js
		   tagIt("anyOfKeyword", 'Press Enter-Key Or Comma(,) Or Tab To Add Keyword:', 'tagItBykeyForSkills','skills','Any Keyword');
	       
		   //Adding Customized All KeyWord Title list this method is slead-common.js
		   tagIt("allKeywords", 'Press Enter-Key Or Comma(,) Or Tab To Add Keyword:', 'tagItBykeyForSkills','skills','All Keywords');
		   
		   //Adding Customized Exclude KeyWord Title list this method is slead-common.js
		   tagIt("excludeKeyWord", 'Press Enter-Key Or Comma(,) Or Tab To Add Keyword:', 'tagItBykeyForSkills','skills','Exclude Keyword');
		   
		   //Adding Customized Designation list this method is slead-common.js
		   tagIt("designation", 'Press Enter-Key Or Comma(,) Or Tab To Add Designation:', 'tagItBykeyForSkills','skills','Designation');
		   
		  //Adding Customized Certification list this method is slead-common.js
		   tagIt("certification", 'Press Enter-Key Or Comma(,) Or Tab To Add Certification:', 'tagItBykeyForSkills','skills','Certification');
		  
		   //Adding Customized Employers list this method is slead-common.js
		   tagIt("employers", 'Press Enter-Key Or Comma(,) Or Tab To Add Employer:', 'tagItBykeyForSkills','skills','Employers');
		   
		   //Adding Customized ExcludeEmployers list this method is slead-common.js
		   tagIt("excludeEmployers", 'Press Enter-Key Or Comma(,) Or Tab To Add Employer:', 'tagItBykeyForSkills','skills','Exclude Employers');
		   
		   //Adding Customized PrimarySkill list this method is slead-common.js
		   tagIt("primarySkill", 'Press Enter-Key Or Comma(,) Or Tab To Add Peimary Skills:', 'tagItBykeyForSkills','skills','Primary Skill');
		   
		 //Adding Customized Skill list this method is slead-common.js
		   tagIt("canSkills", 'Press Enter-Key Or Comma(,) Or Tab To Add Skills:', 'tagItBykeyForSkills','skills','Skills');
		 
		   //Adding Customized Visa list this method is slead-common.js
		   tagIt("canVisa", 'Press Enter-Key Or Comma(,) Or Tab To Add Visa:', 'tagItBykeyForSkills','skills','Visa');
		   
		   tagIt("canTags", 'Press Enter-Key Or Comma(,) Or Tab To Add Tags:', 'tagItBykeyForSkills','skills','Visa');
		   
		   //Adding Customized Visa list this method is slead-common.js
		   tagIt("education", 'Press Enter-Key Or Comma(,) Or Tab To Add Education:', 'tagItBykeyForSkills','skills','Education');
		   
		   //Adding Customized Visa list this method is slead-common.js
		   tagIt("excludeEducation", 'Press Enter-Key Or Comma(,) Or Tab To Add Exclude Education:', 'tagItBykeyForSkills','skills','Exclude Education');
		 
		   
		   
  });
  </script>
	
<script type="text/javascript">
    	   /* var config = {
    				  '.chosen-select'           : {},
    				  '.chosen-select-deselect'  : {allow_single_deselect:true},
    				  '.chosen-select-no-single' : {disable_search_threshold:10},
    				  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
    				  '.chosen-select-width'     : {width:"95%"}
    	   }
    	   
    	   
			//Adding Customized Any Of Keyword to the list 
		    var select, chosen;
		    select = $("#anyOfKeyword");
		    select.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) Or Tab To Add Keyword:' });
		    chosen = select.data('chosen');
		    chosen.search_field.on('keydown keyup', function(e)
		    {
		    	var anyKeyword = this.value.replace(",","");
		        if (tagItBykeyForSkills(e) && (anyKeyword != null  && anyKeyword != '' && anyKeyword != 'undefined'))
		        {
		        	preventTabkey(e); 
		        	var par = this.parent;
		            var option = $("<option>").val(anyKeyword).text(anyKeyword);
		            select.append(option);
		            select.find(option).prop('selected', true);
                    $("#anyOfKeyword option").each(function(){
                          $(this).siblings("[value='"+ this.value+"']").remove();
                    });
	        		$("#anyOfKeyword").trigger("chosen:updated");
	        		
		        }
		    });
		    
		  //Adding Customized Any Of Keyword to the list
		    var selectone, chosenone;
		    selectone = $("#allKeywords");
		    selectone.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) Or Tab To Add Keyword:' });
		    chosenone = selectone.data('chosen');
		    chosenone.search_field.on('keydown keyup', function(e1)
		    {
		    	var allKeyword = this.value.replace(",","");
		        if (tagItBykeyForSkills(e1) && (allKeyword != null  && allKeyword != '' && allKeyword != 'undefined'))
		        {
		        	preventTabkey(e1); 
		        	var par = this.parent;
		            var option = $("<option>").val(allKeyword).text(allKeyword);
		            selectone.append(option);
		            selectone.find(option).prop('selected', true);
                    $("#allKeywords option").each(function(){
                          $(this).siblings("[value='"+ this.value+"']").remove();
                    });
	        		$("#allKeywords").trigger("chosen:updated");
	        		
		        }
		    });
		    
		    
		    var selecttwo, chosentwo;
		    selecttwo = $("#excludeKeyWord");
		    selecttwo.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) Or Tab To Add Keyword:' });
		    chosentwo = selecttwo.data('chosen');
		    chosentwo.search_field.on('keydown keyup', function(e2)
		    {
		    	var excludeKeyword = this.value.replace(",","");
		        if (tagItBykeyForSkills(e2) && (excludeKeyword != null  && excludeKeyword != '' && excludeKeyword != 'undefined'))
		        {
		        	preventTabkey(e2);
		        	var par = this.parent;
		            var option = $("<option>").val(excludeKeyword).text(excludeKeyword);
		            selecttwo.append(option);
		            selecttwo.find(option).prop('selected', true);
                    $("#excludeKeyWord option").each(function(){
                          $(this).siblings("[value='"+ this.value+"']").remove();
                    });
	        		$("#excludeKeyWord").trigger("chosen:updated");
	        		
		        }
		    });
		    
		    var selectthree, chosenthree;
		    selectthree = $("#designation");
		    selectthree.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) Or Tab To Add Designation:' });
		    chosenthree = selectthree.data('chosen');
		    chosenthree.search_field.on('keydown keyup', function(e3)
		    {
		    	var desg = this.value.replace(",","");
		        if (tagItBykeyForSkills(e3) && (desg != null  && desg != '' && desg != 'undefined'))
		        {
		        	preventTabkey(e3);
		        	var par = this.parent;
		            var option = $("<option>").val(desg).text(desg);
		            selectthree.append(option);
		            selectthree.find(option).prop('selected', true);
                    $("#designation option").each(function(){
                          $(this).siblings("[value='"+ this.value+"']").remove();
                    });
	        		$("#designation").trigger("chosen:updated");
	        		
		        }
		    });
		    
		   	    
		    var selectsix, chosensix;
		    selectsix = $("#certification");
		    selectsix.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) Or Tab To Add Certification:' });
		    chosensix = selectsix.data('chosen');
		    chosensix.search_field.on('keydown keyup', function(e6)
		    {
		    	var cert = this.value.replace(",","");
		        if (tagItBykeyForSkills(e6) && (cert != null  && cert != '' && cert != 'undefined'))
		        {
		        	preventTabkey(e6);
		        	var par = this.parent;
		            var option = $("<option>").val(cert).text(cert);
		            selectsix.append(option);
		            selectsix.find(option).prop('selected', true);
                    $("#certification option").each(function(){
                          $(this).siblings("[value='"+ this.value+"']").remove();
                    });
	        		$("#certification").trigger("chosen:updated");
	        		
		        }
		    });
		    
		    var selectseven, chosenseven;
		    selectseven = $("#employers");
		    selectseven.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) Or Tab To Add Employer:' });
		    chosenseven = selectseven.data('chosen');
		    chosenseven.search_field.on('keydown keyup', function(e7)
		    {
		    	var emp = this.value.replace(",","");
		        if (tagItBykeyForSkills(e7) && (emp != null  && emp != '' && emp != 'undefined'))
		        {
		        	preventTabkey(e7);
		        	var par = this.parent;
		            var option = $("<option>").val(emp).text(emp);
		            selectseven.append(option);
		            selectseven.find(option).prop('selected', true);
                    $("#employers option").each(function(){
                          $(this).siblings("[value='"+ this.value+"']").remove();
                    });
	        		$("#employers").trigger("chosen:updated");
	        		
		        }
		    });
		    
		    var selectzero, chosenzero;
		    selectzero = $("#primarySkill");
		    selectzero.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add Primary Skill:' });
		    chosenzero = selectzero.data('chosen');
		    chosenzero.search_field.on('keydown keyup', function(e10)
		    {
		    	var prmSkill = this.value.replace(",","");
		        if (tagItBykeyForSkills(e10) && (prmSkill != null  && prmSkill != '' && prmSkill != 'undefined'))
		        {
		        	preventTabkey(e10);
		        	var par = this.parent;
		            var option = $("<option>").val(prmSkill).text(prmSkill);
		            selectzero.append(option);
		            selectzero.find(option).prop('selected', true);
                    $("#primarySkill option").each(function(){
                          $(this).siblings("[value='"+ this.value+"']").remove();
                    });
	        		$("#primarySkill").trigger("chosen:updated");
	        		
		        }
		    });
		    
		    var selecteight, choseneight;
		    selecteight = $("#excludeEmployers");
		    selecteight.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) Or Tab To Add Employer:' });
		    choseneight = selecteight.data('chosen');
		    choseneight.search_field.on('keydown keyup', function(e8)
		    {
		    	var excEmp = this.value.replace(",","");
		        if (tagItBykeyForSkills(e8) && (excEmp != null  && excEmp != '' && excEmp != 'undefined'))
		        {
		        	preventTabkey(e8);
		        	var par = this.parent;
		            var option = $("<option>").val(excEmp).text(excEmp);
		            selecteight.append(option);
		            selecteight.find(option).prop('selected', true);
                    $("#excludeEmployers option").each(function(){
                          $(this).siblings("[value='"+ this.value+"']").remove();
                    });
	        		$("#excludeEmployers").trigger("chosen:updated");
	        		
		        }
		    });
		    
		    
		    //Adding Customized Skills to the list
	        var selectnine, chosennine;
	        selectnine = $("#canSkills");
	        selectnine.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) Or Space To Add New Skills:' });
		    chosennine = selectnine.data('chosen');
		    chosennine.search_field.bind(('keydown keyup'), function(e9)
		    { 
		    	 
		    	 var enterSkillsArray = this.value.split(",");
		    	 if(enterSkillsArray.length > 1){
	        		 for (var i=0; i < enterSkillsArray.length; i++) {
	        			 var option = $("<option>").val(enterSkillsArray[i].replace(",","")).text(enterSkillsArray[i].replace(",",""));
	        			 selectnine.append(option);
	        			 selectnine.find(option).prop('selected', true);
	 	        		 $("#canSkills").trigger("chosen:updated");
	                 }
		    	 }else{
			    	var newSkill = this.value.replace(","," ");
			    	
			    	 
			        if (tagItBykeyForSkills(e9) && (newSkill != null  && newSkill != '' && newSkill != 'undefined'))
			        {
			        	preventTabkey(e9); 
			        	var par = this.parent;
			        		 var option = $("<option>").val(newSkill.replace(",","")).text(newSkill.replace(",",""));
			        		 selectnine.append(option);
			        		 selectnine.find(option).prop('selected', true);
				        		$("#canSkills").trigger("chosen:updated");
				        		
				        		
			        }
		    	 }
		    });
		    
		    
		  //Adding Customized Visa to the list
			var selectten, chosenten;
			selectten = $("#canVisa");
			selectten
					.chosen({
						no_results_text : 'Press Enter-Key Or Comma(,) Or Space To Add New Visa:'
					});
			chosenten = selectten.data('chosen');
			chosenten.search_field
					.bind(
							('keydown keyup'),
							function(e10) {

								var enterVisaArray = this.value.split(",");
								if (enterVisaArray.length > 1) {
									for (var i = 0; i < enterVisaArray.length; i++) {
										var option = $("<option>").val(
												enterVisaArray[i].replace(",", ""))
												.text(
														enterVisaArray[i].replace(",",
																""));
										selectten.append(option);
										selectten.find(option).prop('selected', true);
										$("#canVisa").trigger("chosen:updated");
									}
								} else {
									var newVisa = this.value.replace(",", " ");

									if (tagItBykeyForVisa(e10)
											&& (newVisa != null && newVisa != '' && newVisa != 'undefined')) {
										preventTabkey(e10);
										var par = this.parent;
										var option = $("<option>").val(
												newVisa.replace(",", "")).text(
												newVisa.replace(",", ""));
										selectten.append(option);
										selectten.find(option).prop('selected', true);
										$("#canVisa").trigger("chosen:updated");

									}
								}
							}); */
		    
		    
		      $("#country").on('change', function (e) {
		    	   getStates();
		       });
		       $("#state").on('change', function (e) {
		    	   getCities();
		       });
		       
		       // here call experience validation
		       $("#maxExpYears").on('change', function (e) {
		    	   minMaxExperience();
		       });
		       
		       $("#minExpYears").on('change', function (e) {
		    	   minMaxExperience();
		       });
		       
				/* for (var selector in config) {
				  $(selector).chosen(config[selector]);
				}
				$('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});
				}) */
</script> 
     
      <div class="pop_mask"></div>
    
    <div class="advancedsearch-mask"></div>
    
    <script>
    //addcandidate-popup script
    function addCandidate(){
        $('.addcandidate-popup').fadeIn(200);
        $('.pop_mask').fadeIn(200);
        $('#addmorepop').slideUp(200);
    };
       function closeCandidate(){
        $('.addcandidate-popup').fadeOut(200);
        $('.pop_mask').fadeOut(200);
       }
    </script>

<script>
//Function to validate if maxExpYears  value is more the minExpYears  value
function minMaxExperience(){
     var maxExpYears = ($('#maxExpYears').val());
     var minExpYears = ($('#minExpYears').val());
    
    if (parseFloat(maxExpYears) <= parseFloat(minExpYears)){
        $('#expId').find('p.jquery_form_error_message').remove(); 
		$('input#expValidation').attr('class','error signin_fild');
		$("input#expValidation").after("<p class='jquery_form_error_message'>Max Experience is greater than Min Experience</p>");
		$('input#expValidation').attr('record-exist','yes');
		$('input#expValidation').attr('record-exist-errorMsg','Max Experience is greater than Min Experience'); 
    }  else{
    	$('#expId').find('p.jquery_form_error_message').remove(); 
    	$('input#expValidation').removeAttr( "class record-exist record-exist-errormsg");
		$('#expId').find('p.jquery_form_error_message').remove();
    } 
}

</script>

<!-- This  clearCandidateSearchKeys() method for clear search data-->
<script type="text/javascript">	
	function clearCandidateSearchKeys(){
		
	    $("#anyOfKeyword").val('').trigger("chosen:updated");
	    $("#allKeywords").val('').trigger("chosen:updated");
	    $("#excludeKeyWord").val('').trigger("chosen:updated");
	    $("#designation").val('').trigger("chosen:updated");
	    $("#noticePeriod1").val('').trigger("chosen:updated");
	    $("#minExpYears").val('').trigger("chosen:updated");
	    $("#maxExpYears").val('').trigger("chosen:updated");
	    $("#country").val('').trigger("chosen:updated");
	    $("#state").val('').trigger("chosen:updated");
	    $("#citySearch").val('').trigger("chosen:updated");
	    $("#education").val('').trigger("chosen:updated");
	    $("#excludeEducation").val('').trigger("chosen:updated");
	    $("#certification").val('').trigger("chosen:updated");
	    $("#employers").val('').trigger("chosen:updated");
	    $("#excludeEmployers").val('').trigger("chosen:updated");
	    $("#applicantType").val('').trigger("chosen:updated");
	    $("#canSkills").val('').trigger("chosen:updated");
	    $("#canVisa").val('').trigger("chosen:updated");
	    $("#canTags").val('').trigger("chosen:updated");
	    $("#primarySkill").val('').trigger("chosen:updated");
	    
	    $("#designationType").val('').trigger("chosen:updated");
	    
	    $('#candidatelist_form').find('p.jquery_form_error_message').remove();
	    
	     $("#anyOfKeyword").empty();
	     $("#allKeywords").empty();
	     $("#excludeKeyWord").empty();
	     $("#designation").empty();
	      $("#minRate").val(''); 
	     
	      
	     $("#currency").val('');
	     $('.selectpicker').selectpicker('refresh');
	     
	     $("#maxRate").val(''); 
	     
	     $("#rateType").val(''); 
	     $('.selectpicker').selectpicker('refresh');
	     
	     $("#minExpYears").val(''); 
	     $("#maxExpYears").val(''); 
	     $('#expId').find('p.jquery_form_error_message').remove();
	     
	     $("#noticePeriod1").val(''); 
	     
	     $("#country").val(''); 
	     $("#state").val(''); 
	     $("#citySearch").val(''); 
	     
	     $("#designationType").val(''); 
	     
	     
	     $("#education").val(''); 
	     $("#excludeEducation").val('');
	     $("#certification").val(''); 
	     
	     $("#employers").val(''); 
	     $("#excludeEmployers").val(''); 
	     $("#applicantType").val('');
	     $("#canSkills").val('');
	     $("#canVisa").val('');
	     $("#canTags").val('');
	     $("#primarySkill").val('');
	    
	}
</script>

<!-- End  clearCandidateSearchKeys() method for clear search data-->




<!-- =========================================== share scriptin By Laxman ======================================== -->
<script>
function toggleShareCandidates(source){
	  var checkboxes = document.querySelectorAll('input[class="contactscheckbox"]');
	  if($(source).is(":checked")){
		  $('input[class="contactscheckbox"]').prop('checked', 'checked');
	  	  $(".tableShare").show();
	  	$('input:checkbox[name=sharedcheckbox]:checked').each(function(){
	  		candidateIdsMark.push($(this).attr("id"));
	 	}); 
	  }
	  else{ 
		  $('input[class="contactscheckbox"]').removeProp('checked');
	      $(".tableShare").hide();
	      $('input:checkbox[name=sharedcheckbox]not(:checked)').each(function(){
	    	  candidateIdsMark = jQuery.grep(candidateIdsMark, function(value) {
				   return value != $(this).attr("id");
				});
		 	});
	      
	  }
	  $('input[class="contactscheckbox"]').each(function(i) {
		  if($(this).is(":checked")) {
		      shareCandidates[$(this).data("id")] = $(this).val();
		  } else {
			  shareCandidates[$(this).data("id")] = "";
		  }
	  });
}

function checkOrUncheckParent(tableId, selectedItems, checkboxId) {
	$("#"+checkboxId).removeProp("checked");
	   var count = 0;
	   var data = $("#"+tableId).DataTable().data();
	 for(var e in selectedItems) {
	   if(!e || !selectedItems[e] || selectedItems[e] === "")
		   continue;
	   
	   for(var f in data) {
 	   if(e === data[f].id+"") {
 		count ++;
 	   }
		}
	 }
	 if(count == data.count()) {
		$("#"+checkboxId).prop("checked", "checked");
	 }
}
</script>
<script>
var shareCandidates = {};
function checkShareCandidates(elem) {
	if($(elem).is(":checked")) {
		 $(".tableShare").show();
		shareCandidates[$(elem).data("id")] = $(elem).val();
	} else{
		 var candidateIds = new Array();
		 for(var e in shareCandidates) {
			 if(!e || !shareCandidates[e] || shareCandidates[e] === "")
				   continue;
			 candidateIds.push(shareCandidates[e]);
		 } 
		 if(candidateIds.length - 1 == 0){
			 $(".tableShare").hide();
		 }
		$("#sharedcheckbox").removeProp('checked');
		shareCandidates[$(elem).data("id")] = "";
	}
}
</script>

<!-- Modal share job -->
<div class="sideMask"></div>
  <div class="modal sharejob-pop" id="sharejob-pop" style="z-index: 10000 !important;">
  </div>
<script>
function shareJob() {
	$("#sharejob-pop").fadeIn();
	$(".sideMask").fadeIn();
	var candidateIds = new Array();
	 for(var e in shareCandidates) {
		 if(!e || !shareCandidates[e] || shareCandidates[e] === "")
			   continue;
		 candidateIds.push(shareCandidates[e]);
	 }
	 if(candidateIds.length > 0){
		 sharejobpopup(candidateIds.length);
		getRequirements();
	 }else{
		 $("#sharejob-pop").hide();
			$(".sideMask").hide();
		    var result = "";
			result = result +"	<div class='project_inn_mask'></div>"; 
			result = result +"	   <div id='shareCandidate' class='remove_user_pop remove_user_pop_a'>";
			result = result +"		<p>Please select at least one Candidate</p>";
			result = result + ' <div id="reasonForInvoiceDiv"/>';
			result = result +'		<b href="javascript:void(0)"><input id="confirm-id" type="button" value="Ok" onclick="cancelShareCandidate();"><input class="u_can_color" type="button"  value="Cancel" onClick="cancelShareCandidate();"></b>';
			result = result +"	</div>";
			$("#shareEmptypopUp").empty();
			$("#shareEmptypopUp").append(result);
			$("#shareCandidate").slideDown(200);
			$(".project_inn_mask").fadeIn(200); 
	 }
	
	}
function cancelShareCandidate(){
	$("#shareCandidate").fadeOut();
	$(".project_inn_mask").fadeOut(); 
}
function closejobpop() {
// 	shareCandidates = {};
// 	 $('input[class="contactscheckbox"]').removeProp('checked');
	$("#sharejob-pop").fadeOut();
	$(".sideMask").fadeOut();
	}
function sharejobpopup(candidateCount) {
	
	 $("#sharejob-pop").empty();
		var buttonDisabled = '';
		var buttonDisplay = '';
		if(candidateCount > 50){
			buttonDisabled = 'disabled';
			buttonDisplay = 'style="display:block"';
		}else{
			buttonDisplay = 'style="display:none"';
		}
		
	var result = "";
	result = result+ '<form method="post">';
	 result = result+ '<div  id="shareJob-form" name="shareJob-form" class="modal-dialog">';
	 result = result+ '<div class="modal-content">';
	 result = result+ '  <div class="modal-header positionRelative">';
	 result = result+ '    <button type="button" class="close" data-dismiss="modal" onclick="closejobpop();">&times;</button>';
	 result = result+ '    <h4 class="modal-title col-md-3">Share Job</h4>';
	 result = result+ '    <h5 class="modal-title col-md-8">Selected Candidates:<span id="selectedcandidatescount" class="paddingLeft">'+candidateCount+'</span></h5>';
	 result = result+ '  </div>';
	 result = result+ '  <div class="modal-body">';
	 result = result+ '    <div class="clearfix mb">';
	 result = result+ '    <label class="col-md-3">Posted from <span class="text-danger">&nbsp;*</span></label>';
	 result = result+ '    <div class="dropdown-select col-md-9">';
	 result = result+ '    <select class="form-control" id="requirementsTimeRange" onchange="getRequirements();rolecustomedates();">';
	 result = result+ '    <option value="today">Today</option>';
	 result = result+ '    <option value="yesterday">1 Day Ago</option>';
	 result = result+ '    <option value="1_week" selected="">1 week</option>';
	 result = result+ '    <option value="2_weeks">2 weeks</option>';
	 result = result+ '    <option value="1_month">1 month</option>';
	 result = result+ '    <option value="2_months">2 months</option>';
	 result = result+ '    <option value="custom">Custom</option>';
	 result = result+ '    </select>';
	 result = result+ '    </div>';
	 result = result+ '  </div>';
	 result = result+ '  <div class="clearfix mb" id="customedatesDiv" style="display:none">';
	 result = result+ '    <label class="col-md-3">Custom Dates <span class="text-danger">&nbsp;*</span></label>';
	 result = result+ '    <div class="col-md-9">';
	 result = result+ '    <div class="row">';
	 result = result+ '    <div class="col-md-6" id="StartErrMsg">';
	 result = result+ '    <input type="text" class="form-control calendar" id="shareStartDate" placeholder="Start Date" field-name="Start Date" readonly>';
	 result = result+ '    </div>';
	 result = result+ '    <div class="col-md-6" id="EndErrMsg">';
	 result = result+ '    <input type="text" class="form-control calendar" id="shareEndDate" placeholder="End Date" field-name="End Date" readonly>';
	 result = result+ '    </div>';
	 result = result+ '    </div>';
	 result = result+ '    </div>';
	 result = result+ '  </div>';
	 
	 result = result+ '  <div class="clearfix mb">';
	 result = result+ '    <label class="col-md-3">Managers</label>';
	 result = result+ '    <div class="dropdown-select col-md-9">';
	 result = result+ '    <select class="form-control chosen-select" id="requirementManagerlist" onchange="getRequirements();">';
	 result = result + '    <option value=""> -- Select Manager -- </option>';
	 result = result+ '        <c:forEach var="report" items="${requirementApproverList}">';
	 result = result+ '	         	<option value="${report.id}">${report.firstName}&nbsp;${report.lastName}</option>';
	 result = result+ '        </c:forEach>  ';
	 result = result+ '    </select>';
	 result = result+ '    </div>';
	 result = result+ '  </div>';
	 
	 result = result+ '  <div class="clearfix mb">';
	 result = result+ '    <label class="col-md-3">Job<span class="text-danger">&nbsp;*</span></label>';
	 result = result+ '    <div class="dropdown-select col-md-9" id="ErrMsgJob">';
	 result = result+ '    <select data-live-search="true" class="form-control selectpicker" id="shareRequirementsList" field-name="Job" data-validation="required" onchange="removeErrMsg();">';
	 result = result+ '    </select>';
	 result = result+ '    </div>';
	 result = result+ '  </div>';
	 result = result+ '  </div>';
	 result = result+ '  <div class="modal-footer">';
	 result = result+ '    <span class="fa fa-exclamation-triangle text-danger pull-left mt10" '+buttonDisplay+'><strong>&nbsp;Selected Candidates are not more than 50</strong></span>';
	 result = result+ '    <button type="button" class="btn btn-primary" data-dismiss="modal" '+buttonDisabled+' onclick="selectedJobSharing();">Share Job</button>';
	 result = result+ '    <button type="button" class="btn btn-default" data-dismiss="modal" onclick="closejobpop();">Cancel</button>';
	 result = result+ '  </div>';
	 result = result+ '</div>';
	 result = result+ '</div>';
	 result = result+ '</form>';
	 $("#sharejob-pop").append(result);
	 
	 $("#shareRequirementsList").selectpicker('refresh');
	 
	 $("#requirementManagerlist").chosen();
	 $("#requirementManagerlist").trigger("chosen:updated");
	 
	 
	 var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
	  var finalDateFormatProjectSave = '';
	  if(locationDate.match("yyyy-MM-dd")){
		  finalDateFormatProjectSave = 'yy-mm-dd';
	  }if(locationDate.match("dd-MM-yyyy")){
		  finalDateFormatProjectSave = 'dd-mm-yy'; 
	  }if(locationDate.match("MM-dd-yyyy")){
		  finalDateFormatProjectSave = 'mm-dd-yy'; 
	  }if(locationDate.match("yyyy-dd-MM")){
		  finalDateFormatProjectSave = 'yy-dd-mm'; 
	  }
	 
	  
	  $('#shareStartDate').datepicker({
			dateFormat : finalDateFormatProjectSave,
			changeMonth: true,
			changeYear: true, 
			onSelect : function(selected) {
				var dt = $(this).datepicker('getDate', '+1d');
				dt.setDate(dt.getDate());
				$("#shareEndDate").datepicker("option", "minDate", dt);
				removeErrsgStartDate();
				getRequirements();
			}
		});
	  
	  $('#shareEndDate').datepicker({
			dateFormat : finalDateFormatProjectSave,
			changeMonth: true,
			changeYear: true, 
			onSelect : function(selected) {
				var dt = $(this).datepicker('getDate', '-1d');
				dt.setDate(dt.getDate());
				$("#shareStartDate").datepicker("option", "maxDate", dt);
				removeErrEndDate();
				getRequirements();
			}
		});
}
</script>
<script>
function selectedJobSharing(){
	if ($('#shareJob-form').validate(false, validationSettings)) {
		var requirementId = $("#shareRequirementsList").val();
		var candidateIds = new Array();
		 for(var e in shareCandidates) {
			 if(!e || !shareCandidates[e] || shareCandidates[e] === "")
				   continue;
			 candidateIds.push(shareCandidates[e]);
		 }
		 $("#recommendationsLoading").attr('style','position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		 $.ajax({
	          type : "POST",
	          url: atsUrl+"/shareAllRecommendations?"+tokenParamName+"="+tokenValue+"&candidateIds="+candidateIds+"&requirementId="+requirementId,    
	           success :function(data) {
	        	   closejobpop();
	        	    $("#recommendationsLoading").removeAttr("style");
	        	    $("#selector4").removeAttr("style");
	       			$("#selector4").css({ display: "block" });
	       			$("#eduMsgDiv").html("Great ! you have successfully shared this job");
	       			shareMsgFadeinFunction();
	       			toggleShareCandidates();
	           },
	   }); 
      return true;
		
	}else {
		return false;
	}
}
$('body').on('blur', '#shareJob-form', function() {
	$("#shareJob-form").showHelpOnFocus();
	$("#shareJob-form").validateOnBlur(false, validationSettings);
});
</script>
<script>
	function shareMsgFadeinFunction() {
		$("#selector4").fadeIn(200);
        $('#selector4').delay(2000).fadeOut('slow');
	}
</script>
<script>
//Role custome Dates
function rolecustomedates(){
	var roleval = $("#requirementsTimeRange").val();
	if(roleval == "custom"){
		document.getElementById("customedatesDiv").style.display ="block";
		 $('#shareStartDate').attr("data-validation","required"); 
		 $('#shareEndDate').attr("data-validation","required"); 
	}else {
		document.getElementById("customedatesDiv").style.display ="none";
		$('#shareStartDate').removeAttr("data-validation","required");
		$('#shareEndDate').removeAttr("data-validation","required");
	}
}

function removeErrsgStartDate(){
	$("#StartErrMsg").find("p.jquery_form_error_message").remove();
}
function removeErrEndDate(){
	$("#EndErrMsg").find("p.jquery_form_error_message").remove();
}
function removeErrMsg(){
	$("#ErrMsgJob").find("p.jquery_form_error_message").remove();
}
</script>
<script>
function getRequirements(){
	var postedDateRange =$("#requirementsTimeRange").val();
	var startDate = $("#shareStartDate").val();
	var endDate = $("#shareEndDate").val();
	var managerId = $("#requirementManagerlist").val();
	$.ajax({
        type : "GET",
        url: sourceleadUrl+"/getAllRequirementsForJobSharing?postedDateRange="+postedDateRange+"&startDate="+startDate+"&endDate="+endDate+"&managerId="+managerId,    
         success :function(json) {
              if(json != null && json != 'undefined' && json != "" && json != '[]'){
            	  var result = "";
            	  result = result + '<option value="">--- Select Job ---</option>';
                  for (var i = 0; i < json.length; i++) {
                 	 var opt = json[i]; 
                 	 result = result +  '<option value="'+opt.id+'"> '+opt.generatedId+' - '+opt.jobTitle+' ('+opt.postedDateStr+')</option>';
                  }
                  $("#shareRequirementsList").empty();
                  $("#shareRequirementsList").append(result);
                  $("#shareRequirementsList").selectpicker('refresh');
              }else{
            	  var result = "";
            	  result = result + '<option value="">--- Select Job ---</option>';
            	  $("#shareRequirementsList").empty();
                  $("#shareRequirementsList").append(result);
                  $("#shareRequirementsList").selectpicker('refresh');
              }
           
       },
    });

}
</script>





<!-- ------------------------------------ Filter Chnages By Laxman -------------------------- -->

<script>
function closeCandidateFilterPop(){
	$("#candidateFilterPopup").hide();
	$(".advancedsearch-mask").fadeOut();
}
function openCandidateFilterPop(){
	$("#candidateFilterPopup").toggle();
	 $(".advancedsearch-mask").fadeIn();
}
function resetCandidateFilter(){
	clearCandidateSearchKeys();
	$("ul#TypeFilter > li input:radio").prop("checked",false);
	$("ul#AllTypeFilter > li input:radio").prop("checked",false);
	$("ul#UpdatedFilter > li input:radio").prop("checked",false);
	$("ul#employmentFilter > li input:checkbox").prop("checked",false);
	$("ul#availabilityFilter > li input:checkbox").prop("checked",false);
	$("ul#skillsUL > li input:checkbox").prop("checked",false);
	$("ul#candidateTypeFilter > li input:checkbox").prop("checked",false);
	$("ul#createdByTypeFilter > li input:radio").prop("checked",false);
	$("#mySkillsInput").val('');
	
	
	$("#dataTableSearch").val('');
	mySkillsFunction();
	document.getElementById("candidateTypeAll").checked = true;
	document.getElementById("candidateAll").checked = true;
	document.getElementById("candidateCheck").checked = true;
	document.getElementById("myCandidatesRadio").checked = true;
	candidateList();
	mySkillsFunction();
}
</script>		


  <div class="cand-advsrch-popup advancedsearch-popup filterDynamic-Search" id="candidateFilterPopup">
	<h5 class="headerAdsrch">Filters
		<a href="javascript:void(0)" class="close-div" onClick="closeCandidateFilterPop()"><i
			class="fa fa-close"></i></a>
	</h5>
	<div class="addInner">
		<div class="row">
			<div class="col-md-2">
			<div class="row">
			
			<div class="col-md-12 mb">
				<label><strong>All Candidates</strong></label>
				 <div class="clearfix">
					<ul class="list-unstyled" id="AllTypeFilter">
					    <li class="radioIput positionRelative"><input id="candidateAll" type="radio" name="candidateStatus" checked  value ="2"   ><label for="candidateAll">All</label></li>
						<li class="radioIput positionRelative"><input id="candidateActive" type="radio" name="candidateStatus" value ="1"  ><label for="candidateActive">Active</label></li>
						<li class="radioIput positionRelative" ><input id="candidateInActive" name="candidateStatus" type="radio" value ="0" ><label for="candidateInActive">In Active</label></li>
                    </ul>
				 </div>
				 </div> 
				 
				 
			    <div class="col-md-12 mb">
				<label><strong>Type</strong></label>
				 <div class="clearfix">
					<ul class="list-unstyled" id="TypeFilter">
					     <li class="radioIput positionRelative" ><input id="candidateTypeAll" type="radio" name="candidateType" checked value ="All" ><label for="candidateTypeAll">All</label></li>
						 <li class="radioIput positionRelative" ><input id="onlycandidate" type="radio" name="candidateType" value ="Favourite" ><label for="onlycandidate">Favourite</label></li>
                    </ul>
				 </div>
				 </div>
				 
				 
				 <div class="col-md-12 mb">
				<label><strong>Candidate Type</strong></label>
				 <div class="clearfix">
					<ul class="list-unstyled" id="candidateTypeFilter">
					    <li class="checkBox positionRelative" ><input  type="checkbox" id="candidateCheck" name="candidateTypeFilter" checked value ="Candidates"  ><label>Candidates</label></li>
						 <c:if test="${SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_RECRUITER'}">
						<li class="checkBox positionRelative" ><input  type="checkbox" id="associateCheck"  name="candidateTypeFilter"  value ="Associates" ><label>Associates</label></li>
                   </c:if>
                    </ul>
				 </div>
				 </div>  
			  </div>
			</div>
			
			<div class="col-md-2">
				<div class="row">
			       <div class="col-md-12 mb">
				     <label><strong>Updated</strong></label>
					 <div class="clearfix">
						<ul class="list-unstyled" id="UpdatedFilter">
						<li class="radioIput positionRelative" ><input id="allCandidates" type="radio" name="upcandidateStatus" value ="all" ><label for="allCandidates">All</label></li>
                        <li class="radioIput positionRelative" ><input id="candidateToDay" type="radio" name="upcandidateStatus" value ="0"  ><label for="candidateDayAgo">Today</label></li>
                        <li class="radioIput positionRelative" ><input id="candidateDayAgo" type="radio" name="upcandidateStatus" value ="1" ><label for="candidateDayAgo">1 Day ago</label></li>
						<li class="radioIput positionRelative" ><input id="candidate3DayAgo" type="radio" name="upcandidateStatus" value ="3" ><label for="candidate3DayAgo">3 Days ago</label></li>
						<li class="radioIput positionRelative" ><input id="candidate1WeekAgo" name="upcandidateStatus" type="radio" value ="7" ><label for="candidate1WeekAgo">1 Week ago</label></li>
						<li class="radioIput positionRelative" ><input id="candidate3WeekAgo" name="upcandidateStatus" type="radio" value ="14" ><label for="candidate3WeekAgo">2 Weeks ago</label></li>
						<li class="radioIput positionRelative" ><input id="candidateMonthAgo" name="upcandidateStatus" type="radio" value ="30" ><label for="candidateMonthAgo">1 Month ago</label></li>
	                    <li class="radioIput positionRelative" ><input id="candidate3Months"  name="upcandidateStatus" type="radio" value ="90"><label for="candidate3Months">3 Months ago</label></li>
	                    
                        </ul>
					 </div>
				 </div>
				 
				 <div class="col-md-12 mb">
				<label><strong>Created By</strong></label>
				 <div class="clearfix">
					<ul class="list-unstyled" id="createdByTypeFilter">
					    <li class="radioIput positionRelative" ><input  type="radio" id="myCandidatesRadio" name="createdByFilter"  value ="myCandidates" checked ><label>My Candidates</label></li>
						<li class="radioIput positionRelative" ><input  type="radio" id="allCandidatesRadio"  name="createdByFilter"  value ="allCandidates"  ><label>All Candidates</label></li>
                    </ul>
				 </div>
				 </div>  
			 </div>
			</div> 
			
			<div class="col-md-2">
				<div class="row">
					<div class="col-md-12 mb">
						<label><strong>Employment Type</strong></label>
						 <div class="clearfix">
							<ul class="list-unstyled" id="employmentFilter">
	                    	    <li class="checkBox positionRelative" ><input  type="checkbox"  name="employmentType"  value ="C2C"  ><label>C2C</label></li>
								<li class="checkBox positionRelative" ><input  type="checkbox"  name="employmentType"  value ="C2C-1099" ><label>C2C-1099</label></li>
								<li class="checkBox positionRelative" ><input  type="checkbox"  name="employmentType"  value ="W-2" ><label>W-2</label></li>
	               				<li class="checkBox positionRelative" ><input  type="checkbox"  name="employmentType"  value ="C2H"  ><label>C2H</label></li>
	               				<li class="checkBox positionRelative" ><input  type="checkbox"  name="employmentType"  value ="C2H -1099" ><label>C2H -1099</label></li>
	               			    <li class="checkBox positionRelative" ><input  type="checkbox"  name="employmentType"  value ="Part Time" ><label>Part Time</label></li>
	               				<li class="checkBox positionRelative" ><input  type="checkbox"  name="employmentType"  value ="Full Time" ><label>Full Time</label></li>
	                        </ul>
						 </div>
						 </div> 
				     </div>
			</div>
			
			
			<div class="col-md-2">
				<div class="row">
					<div class="col-md-12 mb">
						<label><strong>Availability</strong></label>
						 <div class="clearfix">
							<ul class="list-unstyled" id="availabilityFilter">
	                    	    <li class="checkBox positionRelative" ><input  type="checkbox"  name="availability"  value ="Immediately available"  ><label>Immediately available</label></li>
								<li class="checkBox positionRelative" ><input  type="checkbox"  name="availability"  value ="15 Days" ><label>15 Days</label></li>
								<li class="checkBox positionRelative" ><input  type="checkbox"  name="availability"  value ="1 Month" ><label>1 Month</label></li>
	               				<li class="checkBox positionRelative" ><input  type="checkbox"  name="availability"  value ="2 Months"  ><label>2 Months</label></li>
	               				<li class="checkBox positionRelative" ><input  type="checkbox"  name="availability"  value ="3 Months" ><label>3 Months</label></li>
	                        </ul>
						 </div>
						 </div> 
				     </div>
			</div>
			
			<div class="col-md-3">
					<div class="clearfix dynamic_search filterDynamicsearch">
						<input type="text" id="mySkillsInput" onkeyup="mySkillsFunction()" placeholder="Skills" title="Type in a skills">
						<span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span>
					</div>
					 <div class="clearfix filterSearch-ul">
						<ul class="list-unstyled positionRelative filterSearch mt5" id="skillsUL">
							<c:forEach var="skill" items="${skillsList}">
							<li class="checkBox positionRelative"><input  type="checkbox" name="candidateFilterSkills" value ="${skill.name}" ><label>${skill.name}</label></li>
							</c:forEach>
						</ul>
					 </div> 
		 </div> 
			
		</div>
		</div>
		<div class="clearfix advancedsearch-footer">
			<div class="pull-right mr">
				<input type="button" value="Apply" id="search_requiremet" onclick="closeCandidateFilterPop();candidateList();" class="btn btn-sm btn-success" />
				<input type="button" value="Reset" id="clear_search" onclick="resetCandidateFilter();" class="btn btn-sm btn-primary" />
			</div>
		</div>
</div>
	
<script>
function mySkillsFunction() {
    var input, filter, ul, li, a, i;
    input = document.getElementById("mySkillsInput");
    filter = input.value.toUpperCase();
    ul = document.getElementById("skillsUL");
    li = ul.getElementsByTagName("li");
    for (i = 0; i < li.length; i++) {
        a = li[i].getElementsByTagName("label")[0];
        if (a.innerHTML.toUpperCase().indexOf(filter) > -1) {
            li[i].style.display = "";
        } else {
            li[i].style.display = "none";

        }
    }
}

$(document).keyup(function(e) {
	  if (e.keyCode === 27) {
		  closeCandidateFilterPop();
	  }
	});
</script>

	
<script>
function getActions(){
	$(".action-pop").slideToggle(200);
	$(".newActPop").slideUp(200);
}	
$("html").click(function(){
	$(".action-pop").slideUp(200);
});
$(".action, .action-pop").click(function(e){
 	e.stopPropagation();
});	

function newActions(){
	$(".newActPop").slideToggle(200);
	$(".action-pop").slideUp(200);
}
$("html").click(function(){
	$(".newActPop").slideUp(200);
});
$(".overIcon, .newActPop").click(function(e){
 	e.stopPropagation();
});	

</script>
 <script type="text/javascript">
	/* function goToGlaobalMarketList() {
		alert("in global function");
		 $.ajax({
			type : "GET",
			url : atsUrl + "/globalMarketplace?" + tokenParamName + "=" + tokenValue,
			success : function(result) {
				$('#candidatesInfoDiv').html(result);
				$('#brudCrumLbl').html("Global Marketplace");
				$('#headerLbl').html("Global Marketplace List");
			},
		}); 
	}  */
function goToCandidateList(){
	location.href = "${SourceLeadWeb_atsUrl}/candidatesList";
}
	
function goToGlobalMarketPlaceList(){
	location.href = "${SourceLeadWeb_atsUrl}/globalMarketplacePage";
}

function goToOrgMarketPlaceList(){
	location.href = "${SourceLeadWeb_atsUrl}/OrgMarketplace";
}
function goToPurchaseOrderList(){
	location.href ="${SourceLeadWeb_atsUrl}/PurchaseOrderList";
	}
function goToSalesOrderList(){
	location.href = "${SourceLeadWeb_atsUrl}/SalesOrderList";
}
function goToCartList(){
	location.href = "${SourceLeadWeb_atsUrl}/candidateMarketplaceCart";
}
function goToWalletList(){
	location.href = "${SourceLeadWeb_atsUrl}/walletList";
}

</script> 
<jsp:include page="../masterFooter.jsp" /> 
