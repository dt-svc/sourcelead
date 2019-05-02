<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%-- 
     @author : p.Laxman
     this script is for candidate Activation
--%>
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
		result = result + '   <a href="<c:url value='${appPath}${appPath}/dashboard'/>" type="button" class="btn btn-primary">Take me home<span>&nbsp;&nbsp;<i class="fa fa-angle-double-right"></i>';
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
 <sec:authorize access="hasAnyRole(\'ROLE_ASSOCIATE_COMMENTS\',\'ROLE_CROWDSOURCING_CANDIDATES\')">
<jsp:include page="candidatesCommentsList.jsp"></jsp:include>
 </sec:authorize> 
 <jsp:include page="candidatesQuickView.jsp"></jsp:include>
 <input type="hidden" id="noCandidates" name="noCandidates" />
<script>
var isAssociate = 'Y';
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
};
</script>
<script>
$(document).ready(function() {
	$(".candidate_wrap_table table tr td:first-child").css({
	"padding":"0", "text-align":"center", "width":"200px", "padding-right":"5px"
	});
});
</script>
<script type="text/javascript" src="/resources/js/candidateTable.js"></script>
<%-- 
     @author : Laxman.
     this script is for show side bar when you click on the candidate name.
     1.usage     : called at candidateList() function.
     2.parameter : csrf token , candidateId .
     3.ajax url implementation in CandidateListRestController.
--%>

<script>
var candidateStatusGlobal ="";
function candidateSideBar(candidateId){
	  var loginUserId = '${SourceLeadWeb_userId}';
	  var candidateType = "Associates";
	  var locationCode = '${SourceLeadWeb_locationCode}';
	  $.ajax({
		  type: "GET",
		    url: sourceleadUrl+"/getProfessionalInfo?candidateId="+candidateId+"&locationCode="+locationCode,
           success :function(json) {  
                if(json != null && json != 'undefined' && json != ""){  
                	 //var json = JSON.parse(data);
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
			            
			            var currencyCode = '';
         			 	if(json.experienceMonths != null && json.experienceMonths != '' && json.experienceMonths != 'undefined'){
         			 		experienceMonths = json.experienceMonths+'-'+'Month/s';            			 		
         			 	}else{
         			 		experienceMonths = ' ';  
         			 	 }
         			 	
         			 	if(json.designation != null && json.designation != '' && json.designation != 'undefined'){
             			 	designation = 'Designation'+'-'+json.designation;
             			 	}else{
             			 		designation = '';
             			 	}
         			 	
         			 	if(json.designationType != null && json.designationType != '' && json.designationType != 'undefined'){
         			 	designationType = 'Employment Type'+ ' - '+json.designationType;
         			 	}else{
         			 		designationType = '';
         			 	}

         			 	if(json.lastName != null && json.lastName !='' && json.lastName != 'undefined'){
         			 		lastName = json.lastName;
         			 	}else{
         			 		lastName = "";
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
         			 		 
         			 		mobilePhone = '--';
         			 	 }
         			 	 
	                	 var result = "";
	                	 result = result+'<div id="popUp" ></div>  ';
	                	
	                	 
	                	 result = result+'<form id="candidate_form" name="candidate_form" method="post" >';
			             result = result+'  <div class="profile_details clearfix mb">';
			             result = result+'               <div class="col-md-6">';
			             result = result+'                   <div class="media">';
			             
			             result = result+'<div class="col-xs-12 col-sm-5 col-md-5">';
			             result = result+'<div id="candidateImageId">';
			             result = result+'<div class="well well-sm text-center clearfix">';
						 result = result+' <div class="profile-img-container profile-image text-center" style="position:relative;">';
						 if(json.fileUrl != null && json.fileUrl !='' && json.fileUrl !="undefined"){
						 result = result+'   <img  id="userImage" data-id="profileImage" src="'+json.fileUrl+'" alt="Profile Image" class=" img-responsive center-block img-circle">';
						 }else{
						 result = result+'<img data-id="profileImage" id="userImage" src="/resources/images/userprofileImg.jpg" alt="Profile Image" class=" img-responsive center-block img-circle">';

						 }
						 result = result+'  <div class="btn-group uploadfile uploadfile-candidate">';
						 //result = result+'  <button type="button" class="btn btn-sm btn-primary">';
						 //result = result+'  <i class="fa fa-upload"></i></button>';
						 result = result+'  <label class="btn btn-sm btn-primary">';
					     result = result+'  <i class="fa fa-upload paddingRight"></i>Upload / Change<input type="file" name="file" id="uploadedImage"  accept="image/*" onchange="cropImage(this,\'candidate_form\','+candidateId+',false);"  style="display: none;">';
					     result = result+'  </label>';
					     if(json.fileContentType != null && json.fileContentType != '' && json.fileContentType != 'undefined'){
						     result = result+'  <button type="button" class="btn btn-sm btn-danger" onclick="deleteImage('+candidateId+')">';
							 result = result+'  <i class="fa fa-trash"></i></button>';
					     } 
						 result = result+'  </div>';
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
						 
						 /*  result = result+'  <div class="pull-right" style="position:relative; top:10px; right:-10px;">';
						 result = result+'  <a href="#" title="Save"><i class="fa fa-check-circle" onClick="saveCandidateImage('+candidateId+');" ></i></a>';
						 result = result+'  <a href="#" title="Close"><i class="fa fa-close text-danger"></i></a>';
						 result = result+'  </div>'; */
						 result = result+'  </div>';
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
			             result = result+'                     <div class="media-body candidate-details">';
			             
			             result = result+'                       <h4 class="media-heading" title = "'+json.firstName+' '+lastName+'">'+json.firstName+' '+lastName+'</h4>';
			            
			             result = result+'                       <ul class="list-unstyled">';
			             
// 			             result = result+'                        <li>Type - '+json.candidateType+'</li>';  
			             
                         
			             result = result+'                       </ul>';

			             result = result+' <ul class="list-unstyled mrng0">';
						 result = result+'<li title="'+designation+'" class="tbldata-dotdot">'+designation+'</li>';
						 result = result+'</ul>';
						 
			             result = result+' <ul class="list-unstyled">';
						 result = result+'<li><i class="fa fa-clock-o" title="Employment Type"></i>'+designationType+'</li>';
						 result = result+'</ul>';
			             
			             result = result+'<ul class="list-unstyled">';
			              if(json.experienceYears != null && json.experienceYears != '' && json.experienceYears != 'undefined' && json.experienceYears != '0'){
			                    	 
			             result = result+'<li>Experience -'+json.experienceYears+' Year/s '+experienceMonths+'</li>';
			             
              		       }else{
              		                                                	  
              		     result = result+'<li>Experience ----</li>';  
              		     
              		       }
			             result = result+'</ul>';
			             
			             result = result+'<ul class="list-unstyled">';
			              if(json.noticePeriod != null && json.noticePeriod != '' && json.noticePeriod != 'undefined'){
			                    	 
			             result = result+'<li>Notice Period -'+json.noticePeriod+' </li>';
			             
             		       }else{
             		                                                	  
             		     result = result+'<li><i class="fa fa-calendar" title="Notice Period"></i> Notice Period ----</li>';  
             		     
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
			            	 result = result+'<li>Primary Skill -'+json.primarySkill+' </li>';
			             }else{
			            	 result = result+'<li><i class="fa fa-briefcase" title="Primary Skill"> Primary Skill ----</li>';  
			             }
			             
			             result = result+'</ul>';
			             
			             result = result+'</div>';
			             result = result+'<div class="socialnetwork-details">';
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
			            '<sec:authorize access="hasAnyRole(\'ROLE_ASSOCIATE_COMMENTS\',\'ROLE_CROWDSOURCING_CANDIDATES\')">'; 
			            result = result+'<div class="comments-div clearfix" id="latestMessage">';
			            result = result+'    <div class="commentsimg">';
			            result = result+'    <i class="fa fa-comments"></i>';
			            result = result+'        <div class="commentsimg-inner">';
			            
			            result = result+'        	<img data-id="userProfileImage" onerror="predefineProfileImage(this);" src="${SourceLeadWeb_user.fileUrl}"/>';
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
			             result = result+'   <div class="proj_close"></div>';
			             result = result+'   <div class="project_side_wrap_inn_tabs">';
			             result = result+'     <ul class="project-tabs-menu custom_tab">'; 
			             if(json.isAssociate == "Y"){
			            	 
			             '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_ASSOCIATE\',\'ROLE_VIEW_EDIT_ASSOCIATE\',\'ROLE_VIEW_DELETE_ASSOCIATE\',\'ROLE_CROWDSOURCING_CANDIDATES\')">';
			              result = result+'<li id="candidateProfileTab" class="current"><a href="#tab-1" onclick="candidateProfile('+candidateId+',\''+candidateType+'\');">Profile</a></li>';
			             '</sec:authorize>';
			             
			             '<sec:authorize access="hasAnyRole(\'ROLE_ADD_ASSOCIATE_RESUMES\',\'ROLE_VIEW_ASSOCIATE_RESUMES\',\'ROLE_VIEW_EDIT_ASSOCIATE_RESUMES\',\'ROLE_VIEW_DELETE_ASSOCIATE_RESUMES\',\'ROLE_CROWDSOURCING_CANDIDATES\')">';
			               result = result+'<li id="candidateResumeTab"><a href="#tab-2" onclick="candidateResumesList('+candidateId+'); candidateResume('+candidateId+','+json.status+');" >Resume</a></li>';
			             '</sec:authorize>';
			             
			             '<sec:authorize access="hasAnyRole(\'ROLE_ADD_ASSOCIATE_EMPLOYER\',\'ROLE_VIEW_ASSOCIATE_EMPLOYER\',\'ROLE_VIEW_EDIT_ASSOCIATE_EMPLOYER\',\'ROLE_VIEW_DELETE_ASSOCIATE_EMPLOYER\',\'ROLE_CROWDSOURCING_CANDIDATES\')">';
			               result = result+'<li id="candidateEmployersTab"><a href="#tab-3" onclick="candidateExperience('+candidateId+');">Employers</a></li>';
			             '</sec:authorize>';
			             
			             '<sec:authorize access="hasAnyRole(\'ROLE_ADD_ASSOCIATE_EDUCATION_DETAILS\',\'ROLE_VIEW_ASSOCIATE_EDUCATION_DETAILS\',\'ROLE_VIEW_EDIT_ASSOCIATE_EDUCATION_DETAILS\',\'ROLE_VIEW_DELETE_ASSOCIATE_EDUCATION_DETAILS\',\'ROLE_CROWDSOURCING_CANDIDATES\')">';
			              result = result+'<li id="candidateEducationTab"><a href="#tab-4" onclick="candidateEducation('+candidateId+');">Education</a></li>';
			             '</sec:authorize>';
			             
			             '<sec:authorize access="hasAnyRole(\'ROLE_ADD_ASSOCIATE_CERTIFICATION_DETAILS\',\'ROLE_VIEW_ASSOCIATE_CERTIFICATION_DETAILS\',\'ROLE_VIEW_EDIT_ASSOCIATE_CERTIFICATION_DETAILS\',\'ROLE_VIEW_DELETE_ASSOCIATE_CERTIFICATION_DETAILS\',\'ROLE_CROWDSOURCING_CANDIDATES\')">';
			               result = result+'<li id="candidateCertificationsTab"><a href="#tab-5" onclick="candidateCertificate('+candidateId+');">Certifications</a></li>';
			             '</sec:authorize>';
			             
			             <%--- This is for Documents(tab-9) Tab in Candidate Summary -----%>
			             '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_ASSOCIATE_DOCUMENT\',\'ROLE_VIEW_EDIT_ASSOCIATE_DOCUMENT\',\'ROLE_DELETE_ASSOCIATE_DOCUMENT\',\'ROLE_CREATE_ASSOCIATE_DOCUMENT\')">';
			             result = result+'<li id="candidateDocumentsTab"><a href="#tab-9" onclick="candidateDocuments('+candidateId+');">Documents</a></li>';
			             '</sec:authorize>';
			             
			             
			             result = result+'<li id="candidateReferencesTab"><a href="#tab-10" onclick="candidateReferences('+candidateId+');">References</a></li>';
			             
			             '<sec:authorize access="hasAnyRole(\'ROLE_ASSOCIATE_COMMENTS\',\'ROLE_CROWDSOURCING_CANDIDATES\')">';
			                result = result+'<li id="candidateCommentTab"><a href="#tab-7" onclick="candidateCommentsList('+candidateId+');">Comments</a></li>';
			              '</sec:authorize>';
			              
			              
			              '<sec:authorize access="hasAnyRole(\'ROLE_ASSOCIATE_REQUIREMENT_HISTORY\')">';
			              result = result+'<li id="candidateHistoryTab"><a href="#tab-8" onclick="requirementHistory('+candidateId+');">Job History</a></li>';
			              '</sec:authorize>';
			             }
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
						 
						 result = result + '<div id="tab-8" class="project_tabs_inn" style="min-height:450px">';
						 result = result + '</div>';
						 
						 result = result + '<div id="tab-6" class="project_tabs_inn">';
						 result = result + '</div>';
						 result = result + '<div id="tab-7" class="project_tabs_inn">';
						 result = result + '</div>';
			             result = result+'     </div>';
			             result = result+'   </div>';
			             
			             $("#candidateSideDiv").empty();  
			 		     $("#candidateSideDiv").append(result);  
				 		 $(".clients_side_wrap").addClass("clients_side_wrap_active");
						 $(".side_mask").fadeIn(200);
						 $("body").css('overflow-y', 'hidden');
						 candidateProfile(candidateId,candidateType);
			 			 
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



<%-- 
     @author : Laxman.
     this script is for hide side bar when you click on the cross button on popup.
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
	});	
}); 
</script> 

<%-- 
     @author : Laxman.
     this script is for load predefined image for candidate.
     1.usage     : called at candidateSideBar() function. line number 166.
--%>

<script>
	function predefineProfileImage(imageId){   
		$(imageId).attr('src','/resources/images/userprofile-img.png');
	}
</script>



<%-- 
     @author : Laxman.
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
	result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='deleteProfileImage("+candidateId+");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel("+candidateId+");'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
</script>

<%-- 
     @author : Laxman.
     this script is for delete image for candidate.
     1.usage : this script is used to delete image
--%>
<script type="text/javascript">
function deleteProfileImage(candidateId){   
	var loginUserId = '${SourceLeadWeb_userId}';
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	 $.ajax({
		    type: "GET",
            url: sourceleadUrl+"/deleteCandidateImage?candidateId="+candidateId+"&loginUserId="+loginUserId+"&orgLocationId="+orgLocationId,
	        success: function(response) {
	        	if (response.response === "AWKWARD") {
	        	    location.href = "/errorPage";
	        	} else if (response.response === "SUCCESS") {
	        		 var result = response.object;
		        	 $("#popUp").empty();
		        	 $(".project_inn_mask").fadeOut(200);
		        	 $("[data-id=profileImage]").attr('src', '/resources/images/userprofileImg.jpg');
		        	 //refreshImageBlock(candidateId);
		        	 //candidateQuickViewProfile(candidateId);
	        	} else if (response.indexOf('Sign In') !== -1) {
	        		location.replace(sourceleadUrl);
	        	} else {
	        	    showError("Operation failed, please try again");
	        	}
	        	
	       },
	        
     }); 
}
</script>
s')
<%-- 
     @author : Laxman.
     this script is for refresh image block
     1.usage     : 
--%>
<script>
function refreshImageBlock(candidateId){   
	  $.ajax({
          type : "POST",
           url: sourceleadUrl+"/getCandidateById?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId,        
           success :function(result) {
        	   var json = result.object;
          	   if(result.response === "SESSION_TIMEDOUT") {
    					location.replace(sourceleadUrl);
    				} else if (result.response === "AWKWARD") {
    					location.href = "/errorPage";
    				} else if (result.response === "SUCCESS") {
		                	 $("#candidateImageId").empty();
		                	 var result = '';
				             result = result+'<div class="well well-sm text-center clearfix">';
							 result = result+' <div class="profile-img-container profile-image text-center" style="position:relative;">';
							 result = result+'   <img  id="userImage"  data-id="profileImage" onerror="predefineProfileImage(this);" src="'+json.fileUrl+'" alt="Profile Image" class=" img-responsive center-block img-circle">';
							 result = result+'  <div class="btn-group uploadfile uploadfile-candidate">';
							 //result = result+'  <button type="button" class="btn btn-sm btn-primary">';
							 //result = result+'  <i class="fa fa-upload"></i></button>';
							 result = result+'  <label class="btn btn-sm btn-primary">';
						     result = result+'  <i class="fa fa-upload paddingRight"></i>Upload / Change<input type="file" name="file" id="uploadedImage"  accept="image/*" onchange="cropImage(this,\'candidate_form\','+candidateId+',false);"  style="display: none;">';
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
   				}else if (result.indexOf('Sign In') !== -1) {
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
			url : sourceleadUrl+"/saveAssociateAsFavourite?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId,
			success : function(response) {
				if (response && response.response === "INVALID_DATA") {
				    showError(response.responseMessage);
				} else if (response.response === "AWKWARD") {
				    location.href = "/errorPage";
				} else if (response.response === "SUCCESS") {
					var td = cacheLastJson.response.object;
					for(var e in td) {
						if(td[e].candidateId === candidateId) {
							td[e]
							if(isAssociate == "N"){
								td[e].isFavourite = checked ? 1 : 0;
			            	} else if(isAssociate == "Y") {
			            		td[e].isFavouriteAssociate = checked ? 1 : 0;
			            	}
							break;
						}
					}
					cacheLastJson.response.object = td;
					if(checked){
						 document.getElementById("favourite"+candidateId).removeAttribute("class")
						 document.getElementById("favourite"+candidateId).className = 'star-rating activestar-rating' 
					}else{
						 //document.getElementById("favourite"+candidateId).removeAttribute("class")
						 document.getElementById("favourite"+candidateId).classList.remove("activestar-rating");
						 document.getElementById("favourite"+candidateId).className = 'star-rating' 
					}
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
     @author : Laxman.
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
	    	output.src = URL.createObjectURL(event.target.files[0]);
	    	
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
     @author : Laxman.
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
     @author : Laxman.
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
     @author : Laxman.
     this script is for close alert popup.
     1.usage     : used in saveOrCancel() / alertMessage function line number : 435 / 439.
--%>
<script type="text/javascript">
function cancel(candidateId){ 
	$("#popUp").empty();
	$(".project_inn_mask").fadeOut(200);
	refreshImageBlock(candidateId);
}
</script>


<!--HTML CONTENT -->

<section class="content-wraper">
  <section class="content-header clearfix">
   <c:if test="${!empty msg}">
	        <div class="success_pop" id="sucesspop"  style="display: block;">
		         <i class="fa fa-check-circle"></i><span>${msg}</span>
			</div>
	     </c:if>
    <div class="pull-left">
      <h2>Associates List</h2>
    </div>
    <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home </a> &raquo; Associates List</div>
    
    <%-- <div class=" pull-left reportsH">
			<a title="Reports"><i class="fa fa-pie-chart"></i>Reports</a>
			<div class="RePop">
				<ul>
					<li><a href="<c:url value='/candidateListForReports'/>"><span><i class="fa fa-briefcase"></i></span>Added Candidates</a></li>
				</ul>
			</div>
		</div> --%>  
		
  </section>
  <div class="timesheet_out">
    <div class="vendor_wrap minHeight_225">
     <div class="pull-left cand-advsrche">
			  <a href="javascript:void(0)" class="btn btn-primary btn-sm" title="Advanced Search" onClick="openPop();loadChosenScripts();">
			  Advanced Search&nbsp;&nbsp;
			  <i class="fa fa-search"></i>
			  </a> 
			  <form:form id="candidatelist_form" name="candidatelist_form"  enctype='multipart/form-data' >
				  <div class="cand-advsrch-popup advancedsearch-popup">
				  <h5 class="headerAdsrch">Advanced Search
					  <a href="javascript:void(0)" class="close-div" onclick="closePop();"><i class="fa fa-close"></i></a>
					  </h5>
					  <div class="addInner">
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
						<label>Immigration Status</label>
							<div class="clearfix">
								<select class="chosen-select form-control input-sm" multiple data-placeholder="Visa" id="candidateVisa" name="candidateVisa">
								<c:forEach var="visa" items="${visaList}">
								<option value="${visa.name}">${visa.name}</option>
								</c:forEach>
								</select>
							 </div>
						</div>
					  
					  <div class="clearfix">
						<div class="col-md-8">
						<label>Skills</label>
							<div class="clearfix">
								<select class="chosen-select form-control input-sm" multiple data-placeholder="Skills" id="candidateSkills" name="candidateSkills">
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
						
						<div class="clearfix">
						  
						<div class="col-md-8">
							<div class="row">
								<div class="col-md-6 payrate_p">
									<label>Rate</label>
									 <div class="input-group error-field">
										<div id="currencyDiv" class="input-group-btn selp-custom-min custommin">
										    <select class="selectpicker" name="currency" id="currency" field-name="Currency"  >
										        <option value="" selected="selected">Currency</option>
										         <option>INR</option>
								                 <option>AUD</option>
								                 <option>USD</option>
								                 <option>GBP</option>
								                 <option>SGD</option>
								                 <option>EUR</option>
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
								 <select class="chosen-select" id="noticePeriod" name="noticePeriod" data-placeholder="Notice Period" multiple>
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
								        <c:forEach var="state" items="${statesList}">
							                     <option value="${state.stateName}">${state.stateName}</option>
						                </c:forEach>
                                 </select>
							  </div>
						  </div>
						  <div class="col-md-4">
							  <label>City</label>
							  <div class="clearfix">
								  <select  class="chosen-select" name="city" id="city" field-name="City"  >
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
							   <input type="button" value="Search" id="search_candidate" onclick="candidateList();minMaxExperience();" class="btn btn-sm btn-success"/>
                               <input type="button"  value="Clear"  onClick="clearCandidateSearchKeys();"class="btn btn-sm btn-primary"/>
                               <!--  <input type="button" value="Cancel" onClick="closePop();" class="btn btn-sm btn-danger"/> -->
						  </div>
					  </div>
				 
				  </div>
			  </form:form>
      </div>
      <!-- <div class="timesheet_head  customtimesheet-head hdr-styles pull-right"> -->
      <div class="timesheet_head topfilters topfilters2 pull-right hdrFilter">
        <ul>
           <li id="filter2ApprovePop" style="display: none;"><i class="fa fa-filter"></i><span id="canDiv"> Status </span><i class="glyphicon glyphicon-play" ></i>|
              	<div id="filter2ApprovedPop" class="approved_pop">
                  	<ul>
	                    <li><input id="candidateAll" type="radio" name="candidateStatus"   value ="2" onclick="candidateList();"  ><label for="candidateAll">All</label></li>
						<li><input id="candidateActive" type="radio" name="candidateStatus" checked value ="1"  onclick="candidateList();"><label for="candidateActive">Active</label></li>
						<li><input id="candidateInActive" name="candidateStatus" type="radio" value ="0"  onclick="candidateList();"><label for="candidateInActive">In Active</label></li>
                    </ul>
                  </div>                	
          </li>
          
          <sec:authorize access="!hasRole('ROLE_CROWDSOURCING_CANDIDATES')">
          <li id="filter3ApprovePop"><i class="fa fa-filter"></i><span id="typeDiv"> Type </span><i class="glyphicon glyphicon-play" ></i>|
              	<div id="filter3ApprovedPop" class="approved_pop">
                  	<ul>
	                    <li><input id="candidateTypeAll" type="radio" name="candidateType" checked value ="All" onclick="candidateList();"  ><label for="candidateTypeAll">All</label></li>
						<li><input id="consultant" type="radio"  name="candidateType" value ="1"  onclick="candidateList();"><label for="favourite">Favourite</label></li>
                    </ul>
                  </div>                	
          </li>
          </sec:authorize>
          
            <li id="filter1ApprovePop"><i class="fa fa-filter"></i><span id="candidateUpdiv">Updated</span><i class="glyphicon glyphicon-play" ></i>|
              	<div id="filter1ApprovedPop" class="approved_pop">
                  	<ul>
                         <li><input id="candidateDayAgo" type="radio" name="upcandidateStatus" value ="all"  onclick="candidateList();"  ><label for="candidateDayAgo">All</label></li>
                           <li><input id="candidateToDay" type="radio" name="upcandidateStatus" value ="0"  onclick="candidateList();"  ><label for="candidateDayAgo">Today</label></li>
                         <li><input id="candidateDayAgo" type="radio" name="upcandidateStatus" value ="1"  onclick="candidateList();"  ><label for="candidateDayAgo">1 Day ago</label></li>
						<li><input id="candidate3DayAgo" type="radio" name="upcandidateStatus" value ="3"  onclick="candidateList();"><label for="candidate3DayAgo">3 Days ago</label></li>
						<li><input id="candidate1WeekAgo" name="upcandidateStatus" type="radio" value ="7"  onclick="candidateList();"><label for="candidate1WeekAgo">1 Week ago</label></li>
						<li><input id="candidate3WeekAgo" name="upcandidateStatus" type="radio" value ="14"  onclick="candidateList();"><label for="candidate3WeekAgo">2 Weeks ago</label></li>
						<li><input id="candidateMonthAgo" name="upcandidateStatus" type="radio" value ="30"  onclick="candidateList();"><label for="candidateMonthAgo">1 Month ago</label></li>
	                    <li><input id="candidate3Months"  name="upcandidateStatus" type="radio" value ="90"  onclick="candidateList();"><label for="candidate3Months">3 Months ago</label></li>
                    </ul>
                  </div>                	
          </li>  
          
          <li>
             <sec:authorize access="hasAnyRole('ROLE_CREATE_ASSOCIATE','ROLE_CROWDSOURCING_CANDIDATES')">
          	<b id="addmore"><i class="fa fa-plus"></i>&nbsp;<span>Add</span>&nbsp;<i class="glyphicon glyphicon-play"></i></b>
          	</sec:authorize>
          	<div id="addmorepop" class="approved_pop">
	          <ul>
	          <sec:authorize access="hasAnyRole('ROLE_CREATE_ASSOCIATE','ROLE_CROWDSOURCING_CANDIDATES')">
	          <li><a onClick="addCandidate();"  title="Add Associate"><i class="fa fa-plus"></i>Add Associate</a></li>
	          </sec:authorize>
	          
	          <sec:authorize access="hasAnyRole('ROLE_ASSOCIATE_ASSIGN_HOTLIST_TO_TEAM')">
	              <li><a href="<c:url value='${appPath}/assignHotListToTeam'/>" title=""><i class="fa fa-plus"></i>Assign To Team</a></li>
	           </sec:authorize>
	          </ul>
           </div>
          </li>
          
          <!-- <li title="Import"><a href="uploadCandidateForm"><i class="glyphicon glyphicon-import expIcon"></i>&nbsp;</a></li>  -->
          
          <sec:authorize access="hasAnyRole('ROLE_EXPORT_ASSOCIATE')">
          <li title="Export" id="candidate-export" class="mrPOP"> | <i class="glyphicon glyphicon-export expIcon"></i>
            <div class="export_pop">
              <ul>
                <li onclick="exportToPdf();"><i class="fa fa-file-pdf-o"></i>&nbsp; PDF</li>
                <li onclick="exportToExcel();"><i class="fa fa-file-excel-o"></i>&nbsp; Excel</li>
              </ul>
            </div>
          </li>
          </sec:authorize>
          
          <div class="clearfix"></div>
        </ul>
        
        <div class="clearfix"></div>
        <div class='remove_search'>
        		<input type='text' placeholder='search' id='dataTableSearch'/>
        </div>
      </div>
      <div class="candidate_wrap_table datatable_filter">
        <div id="candidateList"></div>
        <div id="emptyPopup"></div>
      </div>
    </div>
    
    <!-- Compnay details -->
    <div class="side_mask">
       <div class="proj_close" onclick="activeTab();"></div>
    </div>
 <div class="clients_side_wrap quickviewTabs">
    
 <ul class="nav nav-tabs">
    <li class="active" id="candQuickView"><a data-toggle="tab" href="#quickview">Quick view</a></li>
    <li id="candDetailView"><a data-toggle="tab" href="#detailview">Detailed view</a></li>
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
  </div>			     
    </div>
    <!-- Compnay details Ends --> 
  </div>
</section>

<!--   Error Message   -->
  <div class="vendor_wrap_mask"></div>
  <div class="vendor_error">
  <p>Delete conformation message....</p>
  <textarea placeholder="Enter message"></textarea>
  <a class="close_color" href="javascript:void(0)">Yes</a><a href="javascript:void(0)">No</a> </div>
<!--   Error Message   --> 
 
 
 <script>
function activeTab(){
	$("#candDetailView").removeClass("active");
	$("#candQuickView").addClass("active");
	$("#quickview").addClass("active in");
	$("#detailview").removeClass("active in");
}
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
	$("#filter2ApprovedPop, #filter3ApprovedPop, #filter1ApprovedPop, #filter4ApprovedPop, .export_pop").slideUp();
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
        $(".project_tabs_inn").not(tab).css("display", "none");
        $(tab).fadeIn();
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
  	  $("#canDiv").text("All Associates");
    }else{
        $("#canDiv").removeAttr("onClick");
    }
	  
	  var candTypeRadio = $('input[name=candidateType]');
	  var candidateType = candTypeRadio.filter(':checked').val();
	  if(isCandidatesAvailable > 0){
	  window.location.href = sourceleadUrl+"/getCandidatesExportList?&candidateStatus="+candidateStatus+"&exportType=pdf"+"&candidateType="+candidateType+"&isAssociate=Y";	
	  }else{
		  var result = "";
			result = result +"	<div class='project_inn_mask'></div>";
			result = result +"	   <div id='candidate_user_pop' class='remove_user_pop remove_user_pop_a'>";
			result = result +"		<p>Associates List is Empty</p>";
			result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
			result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='candidtaeCancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='candidtaeCancel();'></b>";
			result = result +"	</div>";
			$("#emptyPopup").append(result);
			$("#candidate_user_pop").slideDown(200);
			$(".project_inn_mask").fadeIn(200);
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
 	  $("#canDiv").text("All Associates");
   }else{
       $("#canDiv").removeAttr("onClick");
   }
	  
	  var candTypeRadio = $('input[name=candidateType]');
	  var candidateType = candTypeRadio.filter(':checked').val();
	  if(isCandidatesAvailable > 0){
	  window.location.href = sourceleadUrl+"/getCandidatesExportList?&candidateStatus="+candidateStatus+"&exportType=xls"+"&candidateType="+candidateType+"&isAssociate=Y";	
	  }else{
		  var result = "";
			result = result +"	<div class='project_inn_mask'></div>";
			result = result +"	   <div id='candidate_user_pop' class='remove_user_pop remove_user_pop_a'>";
			result = result +"		<p>Associates List is Empty</p>";
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
	    result = result+'              <textarea rows="1" class="form-control clearfix mb10" placeholder="Comments here..." id="comment" name="comment" data-validation="required validate_length length2-500" field-name="Comment" onkeypress="associateListCommentRemoveSpaces();"></textarea>';
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

<script>
   function associateListCommentRemoveSpaces() {
	    var output = document.getElementById("comment").value;
	    output = output.replace(/\s/g, "") 
	    if(output.length != 0){
	    $("#comment").removeAttr("data-validation");
	    }
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
				var frm = $('#candidateComments_form').serializeFormJSON();
				var con = JSON.stringify(frm);
				con = con.replace(/[{}]/g, "");
				var form = $('#candidateComments_form')[0];
	      		var formData = new FormData(form); 
				$("#submit_comment").attr("disabled", "disabled");
         		$("#submit_comment" ).addClass('button').val('Processing..');
				//It will escape all the special characters
		        var jsonData = encodeURIComponent(con);
				/* $.ajax({
						type : "POST",
		        	    data: formData,
		        	    contentType : 'application/json; charset=utf-8',
		        	    enctype: 'multipart/form-data',
		    	        processData: false,
		    	        contentType:false,
		        	    dataType : 'json',
						url : sourceleadUrl+"/saveCandidateComment?"+tokenParamName+"="+tokenValue+"&json="+jsonData+"&candidateId="+candidateId,
						success : function(result) {
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
						},
					}); */
				$.post(sourceleadUrl+"/saveCandidateComment?"+tokenParamName+"="+tokenValue+"&json="+jsonData+"&candidateId="+candidateId,{},function(result){
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
         url: sourceleadUrl+"/getAllCommentsOnCandidateByCandidateIdAndOrgLocation?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId,        
         success :function(data) {
				if (data && data.response === "INVALID_DATA") {
        		    showError(data.responseMessage);
        		} else if (data.response === "AWKWARD") {
        		    location.href = "/errorPage";
        		} else if (data.response === "SUCCESS") {
        			var result = "";
					var json = data.data;
	       			var totalComments = 0;
	       			if(json.length > 0) {
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
			 clearCandidateSearchKeys();
			 $('#expId').find('p.jquery_form_error_message').remove();
			 $(".advancedsearch-mask").fadeOut();
		}
		function openPop(){
		  $("div.cand-advsrch-popup").toggle();
		  $(".advancedsearch-mask").fadeIn();
		}
		function closeAdsrch(){
			$("div.cand-advsrch-popup").fadeOut();
			$(".advancedsearch-mask").fadeOut();
		};
	</script>
<script>
function getStates(){
	var countryName = $("#country").val();
	if(countryName != null && countryName != "" && countryName !="undefined"){
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: '${sourceleadUrl}'+"/getStatesByCountry?"+tokenParamName+"="+tokenValue+"&countryName="+countryName, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#state').empty();
	        	$('#state').append('<option value="">---select state---</option>');
	            for(var i=0;i<json.length;i++){
	            	$('#state').append('<option value="'+json[i]+'">'+json[i]+'</option>');
	            }
	            $('#state').trigger("chosen:updated");
	            
	            $('#city').empty();
	            $('#city').append('<option value="">---select city---</option>');
	            $('#city').trigger("chosen:updated");
	        },
	        error: function(e){
	        }
	    });
	}else{
		$('#state').empty();
    	$('#state').append('<option value="">---select state---</option>');
    	$('#state').trigger("chosen:updated");
    	$('#city').empty();
    	$('#city').append('<option value="">---select city---</option>');
        $('#city').trigger("chosen:updated");
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
	    	 url: '${sourceleadUrl}'+"/getCitiesByStateAndCountry?"+tokenParamName+"="+tokenValue+"&countryName="+countryName+"&stateName="+stateName, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#city').empty();
	        	$('#city').append('<option value="">---select city---</option>');
	            for(var i=0;i<json.length;i++){
	            	$('#city').append('<option value="'+json[i]+'">'+json[i]+'</option>');
	            }
	            $('#city').trigger("chosen:updated");
	        },
	        error: function(e){
	        }
	    });
	}else{
		$('#city').empty();
    	$('#city').append('<option value="">---select city---</option>');
    	$('#city').trigger("chosen:updated");
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
	    
	   /*  $('#country').val(''); 
	    $('#state').val(''); 
	    $('#city').val('');  */
	    $("#city").empty();
	    $("#city").val('').trigger("chosen:updated");
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
	    $("#candidateSkills").val('').trigger("chosen:updated");
	    $("#candidateVisa").val('').trigger("chosen:updated");
	    $("#noticePeriod").val('').trigger("chosen:updated");
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
		   tagIt("candidateSkills", 'Press Enter-Key Or Comma(,) Or Tab To Add Skills:', 'tagItBykeyForSkills','skills','Skills');
		 
		   //Adding Customized Visa list this method is slead-common.js
		   tagIt("candidateVisa", 'Press Enter-Key Or Comma(,) Or Tab To Add Visa:', 'tagItBykeyForSkills','skills','Visa');
		   
		   //Adding Customized Visa list this method is slead-common.js
		   tagIt("education", 'Press Enter-Key Or Comma(,) Or Tab To Add Education:', 'tagItBykeyForSkills','skills','Education');
		   
		   //Adding Customized Visa list this method is slead-common.js
		   tagIt("excludeEducation", 'Press Enter-Key Or Comma(,) Or Tab To Add Exclude Education:', 'tagItBykeyForSkills','skills','Exclude Education');
		 
		   
		   
  });
  </script>
<script type="text/javascript">
    	  /*  var config = {
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
		    selectone.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add Keyword:' });
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
		    selecttwo.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add Keyword:' });
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
		    selectthree.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add Designation:' });
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
		    selectsix.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add Certification:' });
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
		    selectseven.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add Employer:' });
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
		    selecteight.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add Employer:' });
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
	        selectnine = $("#candidateSkills");
	        selectnine.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) Or Space To Add New Skills:' });
		    chosennine = selectnine.data('chosen');
		    chosennine.search_field.bind(('keydown keyup'), function(e11)
		    { 
		    	 
		    	 var enterSkillsArray = this.value.split(",");
		    	 if(enterSkillsArray.length > 1){
	        		 for (var i=0; i < enterSkillsArray.length; i++) {
	        			 var option = $("<option>").val(enterSkillsArray[i].replace(",","")).text(enterSkillsArray[i].replace(",",""));
	        			 selectnine.append(option);
	        			 selectnine.find(option).prop('selected', true);
	 	        		 $("#candidateSkills").trigger("chosen:updated");
	                 }
		    	 }else{
			    	var newSkill = this.value.replace(","," ");
			    	
			    	 
			        if (tagItBykeyForSkills(e11) && (newSkill != null  && newSkill != '' && newSkill != 'undefined'))
			        {
			        	preventTabkey(e11); 
			        	var par = this.parent;
			        		 var option = $("<option>").val(newSkill.replace(",","")).text(newSkill.replace(",",""));
			        		 selectnine.append(option);
			        		 selectnine.find(option).prop('selected', true);
				        		$("#candidateSkills").trigger("chosen:updated");
				        		
				        		
			        }
		    	 }
		    });
		    
		     */
		    
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
	    $("#noticePeriod").val('').trigger("chosen:updated");
	    $("#minExpYears").val('').trigger("chosen:updated");
	    $("#maxExpYears").val('').trigger("chosen:updated");
	  /*   $("#country").val('').trigger("chosen:updated"); */
	    $("#state").val('').trigger("chosen:updated");
	    $("#city").val('').trigger("chosen:updated");
	    $("#education").val('').trigger("chosen:updated");
	    $("#excludeEducation").val('').trigger("chosen:updated");
	    $("#certification").val('').trigger("chosen:updated");
	    $("#employers").val('').trigger("chosen:updated");
	    $("#excludeEmployers").val('').trigger("chosen:updated");
	    $("#applicantType").val('').trigger("chosen:updated");
	    $("#candidateSkills").val('').trigger("chosen:updated");
	    $("#candidateVisa").val('').trigger("chosen:updated");
	    $("#primarySkill").val('').trigger("chosen:updated");
	    
	    $("#designationType").val('').trigger("chosen:updated");
	    $('#candidatelist_form').find('p.jquery_form_error_message').remove();
	    
	     $("#anyOfKeyword").empty();
	     $("#allKeywords").empty();
	     $("#excludeKeyWord").empty();
	     $("#designation").empty();
	      $("#minRate").val(''); 
	      $("#country").val(''); 
	      $("#state").val('');
	      $("#city").val('');
	      
	     $("#currency").val('');
	     $('.selectpicker').selectpicker('refresh');
	     
	     $("#maxRate").val(''); 
	     
	     $("#rateType").val(''); 
	     $('.selectpicker').selectpicker('refresh');
	     
	     $("#minExpYears").val(''); 
	     $("#maxExpYears").val(''); 
	     $('#expId').find('p.jquery_form_error_message').remove();
	     
	     $("#noticePeriod").val(''); 
	     
	     $("#country").val(''); 
	     $("#state").val(''); 
	     $("#city").val(''); 
	     
	     $("#designationType").val(''); 
	     
	     
	     $("#education").val(''); 
	     $("#excludeEducation").val('');
	     $("#certification").val(''); 
	     
	     $("#employers").val(''); 
	     $("#excludeEmployers").val(''); 
	     $("#applicantType").val('');
	     $("#candidateSkills").val('');
	     $("#primarySkill").val('');
	     $("#country").val(''); 
	     $("#state").val(''); 
	     $("#city").val(''); 
	    
	}
</script>

<!-- End  clearCandidateSearchKeys() method for clear search data-->


			<div class="addcandidate-popup">
				  <div class="row">
                      <div class="col-md-offset-1 col-md-10">
				       <a href="javascript:void(0)" class="close-popdiv" onClick="closeCandidate()"><i class="fa fa-close"></i></a>
					       
					       <div class="col-md-4 mt25">
	                          <div class="small-widget bg-yellow">
	                              <a href="<c:url value='${appPath}/associate?type=associateFromCandidate'/>" title="Add from Candidates">
	                                  <div class="clearfix mb10">
	                                      <i class="fa fa-user-circle fa-4x"></i>
	                                      <span class="fa fa-plus"></span>
	                                  </div>
	                                  <strong class="clearfix">Add from Candidates</strong>
	                                </a>
	                                
	                          </div>
						  </div>
						  
						  <div class="col-md-4 mt25">
	                          <div class="small-widget bgGreen">
	                               <a href="<c:url value='${appPath}/associate?type=addAssociate'/>"  title="Add Manually">
	                                  <div class="clearfix mb10">
	                                    <i class="fa fa-black-tie fa-4x"></i>
	                                      <span class="fa fa-plus"></span>
	                                  </div>
	                                  <strong class="clearfix">Add Manually</strong>
	                                </a>
	                          </div>
	                      </div>
                          <div class="col-md-4 mt25">
                           <div class="small-widget bg-purple">
                            <a href="<c:url value='${appPath}/associate?type=importAssociate'/>"  title="Import Associate">
                                <div class="clearfix mb10">
                                <i class="fa fa-file-text-o fa-4x"></i>
                                    <span class="fa fa-arrow-right"></span>
                                </div>
                                <strong class="clearfix">Import Associate</strong>
                                </a>
                            </div>
					      </div>
					      
                      
                      </div>
                          
				  </div>
			  </div>
      <div class="pop_mask"></div>
      <div class="advancedsearch-mask" onClick="closeAdsrch()"></div>
      
      
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

<jsp:include page="../masterFooter.jsp" /> 