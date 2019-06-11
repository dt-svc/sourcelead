
<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 
 <jsp:include page="employeeSubmissionDetails.jsp" />
 <jsp:include page="educationSubmissionDetails.jsp" />
 <jsp:include page="certificationSubmissionDetails.jsp" />
 <jsp:include page="documentsSubmissionDetails.jsp"></jsp:include>
 <jsp:include page="submissionReferencesDetails.jsp"/>
 <jsp:include page="submissionCandidateProfile.jsp" />
 <jsp:include page="submissionCandidateResume.jsp" />
 <jsp:include page="../candidates/candidatesCommentsList.jsp"></jsp:include>
 
<script>
var sourceleadUrl ='${sourceleadUrl}';
function getSubmissionDetailsSideBar(submissionId,candidateId,requirementId){
	var atsUrl ='${SourceLeadWeb_atsUrl}';
	var loginUserId = '${SourceLeadWeb_userId}';
	$("#detailViewLoading").attr('style','position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	$.ajax({ 
	    	type: "POST",
	    	url: atsUrl+"/getSubmissionDetails?"+tokenParamName+"="+tokenValue+"&submissionId="+submissionId,
	    			
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
	    	   var designationType = '';
	    	   var experience = '';
	    	   var noticeperiod = '';
	    	   
	    	   if(json.requirementSubmissionTemplateFields.noticePeriod != null && json.requirementSubmissionTemplateFields.noticePeriod != '' && json.requirementSubmissionTemplateFields.noticePeriod !='undefined'){
	    		   noticeperiod = 'Notice Period -'+json.requirementSubmissionTemplateFields.noticePeriod;
	    	   }
	    	   if(json.requirementSubmissionTemplateFields.designation != null && json.requirementSubmissionTemplateFields.designation != '' && json.requirementSubmissionTemplateFields.designation !='undefined'){
	    		   designation ='Designation -'+json.requirementSubmissionTemplateFields.designation;
	    	   }
	    	   
	    	   if(json.requirementSubmissionTemplateFields.workExperience != null && json.requirementSubmissionTemplateFields.workExperience != '' && json.requirementSubmissionTemplateFields.workExperience !='undefined'){
	    		   experience = 'Experience -'+json.requirementSubmissionTemplateFields.workExperience+ 'Years';
	    	   }
	    	   
	    	   if(json.designationType != null && json.designationType != '' && json.designationType !='undefined'){
	    		   designationType = 'Employment Type -'+json.designationType;
	    	   }
	    	   
	    	   
	    	   
	    	   /* if(json.candidate.rateType != null && json.candidate.rateType != '' && json.candidate.rateType !='undefined'){
	    		   rateType = json.candidate.rateType; 
	    	   }else{
	    		   rateType = " ";
	    	   } */
	    	   
	    	   if(json.requirementSubmissionTemplateFields.expectedCTC != null && json.requirementSubmissionTemplateFields.expectedCTC != '' && json.requirementSubmissionTemplateFields.expectedCTC !='undefined'){
	    		   expectedRate = json.requirementSubmissionTemplateFields.expectedCTC; 
	    	   }else{
	    		   expectedRate = " ";
	    	   }
	    	   
	    	   if(json.requirementSubmissionTemplateFields.currentRateType != null && json.requirementSubmissionTemplateFields.currentRateType != '' && json.requirementSubmissionTemplateFields.currentRateType !='undefined'){
	    		   currentRateType = json.requirementSubmissionTemplateFields.currentRateType; 
	    	   }else{
	    		   currentRateType = " ";
	    	   }
	    	   
	    	   if(json.requirementSubmissionTemplateFields.currentRatePer != null && json.requirementSubmissionTemplateFields.currentRatePer != '' && json.requirementSubmissionTemplateFields.currentRatePer !='undefined'){
	    		   currentRatePer = json.requirementSubmissionTemplateFields.currentRatePer; 
	    	   }else{
	    		   currentRatePer = " ";
	    	   }
	    	   
	    	   
	    	   if(json.requirementSubmissionTemplateFields.expectedRateType != null && json.requirementSubmissionTemplateFields.expectedRateType != '' && json.requirementSubmissionTemplateFields.expectedRateType !='undefined'){
	    		   expectedRateType = json.requirementSubmissionTemplateFields.expectedRateType; 
	    	   }else{
	    		   expectedRateType = " ";
	    	   }
	    	   
	    	   if(json.requirementSubmissionTemplateFields.expectedRatePer != null && json.requirementSubmissionTemplateFields.expectedRatePer != '' && json.requirementSubmissionTemplateFields.expectedRatePer !='undefined'){
	    		   expectedRatePer = json.requirementSubmissionTemplateFields.expectedRatePer; 
	    	   }else{
	    		   expectedRatePer = " ";
	    	   }
	    	   
	    	   /* if(json.candidate.currentRateCurrency != null && json.candidate.currentRateCurrency != '' && json.candidate.currentRateCurrency !='undefined'){
	    		   currentRateCurrency = json.candidate.currentRateCurrency; 
	    	   }else{
	    		   currentRateCurrency  = " ";
	    	   } */
	    	   
	    	   if(json.requirementSubmissionTemplateFields.currentCTC != null && json.requirementSubmissionTemplateFields.currentCTC != '' && json.requirementSubmissionTemplateFields.currentCTC !='undefined'){
	    		   currentRate = json.requirementSubmissionTemplateFields.currentCTC; 
	    	   }else{
	    		   currentRate = " ";
	    	   }
	    	   if(json.requirementSubmissionTemplateFields.state != null && json.requirementSubmissionTemplateFields.state != '' && json.requirementSubmissionTemplateFields.state !='undefined'){
	    		   state = ' , ' +json.requirementSubmissionTemplateFields.state;
	    	   }else{
	    		   state = " ";
	    	   }
	    	   
	    	   if(json.requirementSubmissionTemplateFields.city != null && json.requirementSubmissionTemplateFields.city != '' && json.requirementSubmissionTemplateFields.city !='undefined'){
	    		   city = json.requirementSubmissionTemplateFields.city; 
	    	   }else{
	    		   city = " ";
	    	   }
	    	   
	    	   if(json.requirementSubmissionTemplateFields.lastName != null && json.requirementSubmissionTemplateFields.lastName != '' && json.requirementSubmissionTemplateFields.lastName != 'undefined'){
	    		   lastName = json.requirementSubmissionTemplateFields.lastName;
	    	   }else{
	    		   lastName = "";
	    	   }
	    	   if(json.requirementSubmissionTemplateFields.country != null && json.requirementSubmissionTemplateFields.country != null && json.requirementSubmissionTemplateFields.country != 'undefined'){
	    		   country = ' , ' +json.requirementSubmissionTemplateFields.country;
	    	   }else{
	    		   country = "";
	    	   }
	    	   
	    	   
	    	   var result = "";
	    	   result = result + '<div id="loading"></div>';
          	   result = result + ' <div class="req_hide">';
               result = result + '<div class="profile_details clearfix">';
               result = result + '<div class="col-md-6">';
               result = result + ' <div class="media">';
               result = result + '<div class="media-left image-parent positionRelative">';
               if(json.candidate.fileUrl != null && json.candidate.fileUrl !='' && json.candidate.fileUrl !="undefined"){
               result = result + '<div class="imageParent"><img data-id="profileImage" onerror="predefineProfileImage(this);" src="'+json.candidate.fileUrl+'"  alt="candidate" title="Candidate"></div>';
               }else{
               result = result + '<div class="imageParent"><img data-id="profileImage" onerror="predefineProfileImage(this);" src="${SourceLeadWeb_sourceleadDomainUrl}/resources/images/userprofileImg.jpg" alt="candidate" title="Candidate"></div>';
   
               }
               if(json.candidateScore != null && json.candidateScore != '' && json.candidateScore != 'undefined'){
					  if(json.candidateScore >=50){ 
					 	 result = result +'                    <i class="valid-profile fa fa-check-square text-success"></i>';        
					  }
				  }
               result = result + ' </div>';
               result = result + ' <div class="media-body candidate-details">';
               result = result + ' <h4 class="media-heading">'+json.requirementSubmissionTemplateFields.firstName+' '+lastName+'</h4>';
               result = result + ' <ul class="list-unstyled">';
               result = result + ' <li>'+designation+'</li>';
               result = result + ' <li>'+designationType+'</li>';
               result = result + ' <li>'+experience+'</li>';
               result = result + ' <li>'+noticeperiod+'</li>';
               result = result + ' </ul>';
               result = result + '</div>';
               result = result + '</div>';
               result = result+'  <div class="UsProBar progViPro1">';                                                                                                                                        
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
               if(json.facebookUrl != null && json.facebookUrl != '' && json.facebookUrl != 'undefined'){
            	   result = result + '<li class="bg-facebook"><a href="'+json.facebookUrl+'"target="_blank"> <i class="fa fa-facebook-square"></i></a></li>';
	            }
               
               if(json.linkedInUrl != null && json.linkedInUrl != '' && json.linkedInUrl != 'undefined'){
            	   result = result + '<li class="bg-linked"><a href="'+json.linkedInUrl+'"target="_blank"> <i class="fa fa-linkedin-square"></i></a></li>';
  	            }
               
               if(json.twitterUrl != null && json.twitterUrl != '' && json.twitterUrl != 'undefined'){
            	   result = result + '<li class="bg-twitter"><a href="'+json.twitterUrl+'"target="_blank"> <i class="fa fa-twitter-square"></i></a></li>';
	            }
               
               result = result + ' </ul>';
               result = result + ' </div>';
               result = result + ' </div>';
               result = result + '</div>';
		       result = result + ' <div class="col-md-6">';
		       
		       result = result + ' <div class="row">';
		       result = result + ' <div class="col-md-8 paddingRight">';
		       result = result + '<ul class="list-unstyled can-perdt">';
		       result = result + ' <li><i class="fa fa-envelope-o"></i><a href="#" title="click here">'+json.requirementSubmissionTemplateFields.emailId+'</a></li>';
		       result = result + '<li><i class="fa fa-phone"></i>'+json.requirementSubmissionTemplateFields.phoneNumber+'</li>';
		       result = result + '<li><i class="fa fa-location-arrow"></i>';
		       result = result +city +''+ state +''+ country
		       result = result +'</li>';
		       result = result + '<li><i class="fa fa-money"></i><b>Current Rate:&nbsp;</b>'+currentRate+'   '+currentRatePer+'/'+currentRateType+'</li>';
		       result = result + '<li><i class="fa fa-money"></i><b>Expected Rate:&nbsp;</b>'+expectedRate+'  '+expectedRatePer+'/'+expectedRateType+'</li>';
		       result = result + '</ul>';
		       result = result + ' </div>';
		       
		       
		       result = result + ' <div class="col-md-4">';
			   
			    result = result + '<div class="acceptreject-actions">';
			    if(json.needApplicantPermit == 'Y'){
			    	result = result + '		<p><strong>Applicant Authorization</strong></p>';
			    	if(json.submissionRateStatus != 'REJECTED'){
					result = result + ' <a href="#assignComment" class="btn commtn_btn btn-sm pull-right btn-info" title="Applicant Authorization Details">Details</a> ';
			    	}
			    	    <c:if test="${isManager == true || isAdmin == true}">
			    	   		result = result + '<ul class="list-unstyled list-inline">';
			    	   		result = result + '<span id="statusDiv">';
			    	   		if(json.submissionRateStatus != 'ACCEPTED' && json.submissionRateStatus != 'REJECTED' && json.submissionStatus != 'SUBMISSION' && json.submissionStatus != 'REJECTED' && json.submissionStatus != 'SHORTLIST' && json.submissionStatus != 'OFFERMADE' && json.submissionStatus != 'INTERVIEW' && json.submissionStatus != 'SUBMIT_TO_CLIENT' && json.submissionStatus != 'OFFERREJECTED' && json.submissionStatus != 'OFFERACCEPTED' && json.submissionStatus != 'JOINED'){   
								result = result + '	<li><i class="fa fa-check labelsuccess" title="Accept Applicant Authorization" onClick="acceptCandidate(\''+submissionId+'\',\'accept\');"></i>';
								result = result + '		<div class="acceptreject-pop" id="accpet-div">';
								result = result + '		</div>';
								result = result + '	</li>';
								
								result = result + '	<li><i class="fa fa-close labeldanger" title="Reject Applicant Authorization" onClick="rejectFun('+submissionId+','+requirementId+');"></i>';
								result = result + '		<div class="acceptreject-pop" id="reject">';
								result = result + '		</div>';
								result = result + '	</li>';
			    	    	}
			    	    	result = result + '</span>';
			    	    	result = result + '</ul>';
			    	    	
			    	    	result = result + '<ul class="list-unstyled">';
			    	   		result = result + '<span id="statusDiv">';
			    	    	
			    	    	result = result + '</span>';
			    	    	result = result + '</ul>';
			    	    	
						</c:if>
						    result = result + '<ul class="list-unstyled">';
							if(json.submissionRequestComment != null && json.submissionRequestComment != '' && json.submissionRequestComment != 'undefined'){
									result = result + '	<li>';
										//result = result + "Request sent to Manager";
									result = result + '	</li>';
									result = result + '	<li>';
										//result = result + '<a href="downloadRequestDocumentBySubmissionId?submissionId='+submissionId+'">'+json.submissionRequestFileName+'</a>';
									result = result + '	</li>';
									result = result + '	<li>';
										//result = result + "Comment: "+json.submissionRequestComment;
									result = result + '	</li>';
									if(json.submissionRateStatus != null && json.submissionRateStatus != '' && json.submissionRateStatus != 'undefined'){
										result = result + '<li>';
										result = result +"Candidate status: "+ json.submissionRateStatus;
										result = result + '</li>';
									}
						   }else{
							   
							   <c:if test="${isTeamMember == true && isManager == false && isAdmin == false}">
							   		result = result +'<span id= "requestsDiv">';
							   		if(json.submissionStatus != 'REJECTED' && json.submissionStatus != 'SHORTLIST' && json.submissionStatus != 'OFFERMADE' && json.submissionStatus != 'INTERVIEW' && json.submissionStatus != 'SUBMIT_TO_CLIENT' && json.submissionStatus != 'SUBMISSION' && json.submissionStatus != 'OFFERREJECTED' && json.submissionStatus != 'OFFERACCEPTED' && json.submissionStatus != 'JOINED'){
							   			result = result + '	<li><i class="glyphicon glyphicon-share-alt labelsuccess" title="Request" onClick="requestFun('+submissionId+');"></i>';
							   }
									result = result + '		<div class="acceptreject-pop" id="request">';
									result = result + '		</div>';
									result = result + '	</li>';
									result = result + '	</span>';
								</c:if>		
						   }
						result = result + '</ul>';
						result = result + '</div>';
					  
			    } 
				result = result + '</div>';
				result = result + '</div>';
				result = result + '<div class="clearfix"></div>';
				/* New comments div creation Nov 8th, 2016 */ 
	            result = result+'<div class="comments-div clearfix mr" id="latestMessage">';
	            result = result+'    <div class="commentsimg">';
	            result = result+'    <i class="fa fa-comments"></i>';
	            result = result+'        <div class="commentsimg-inner">';
	            
	            result = result+'        	<img data-id= "userProfileImage" onerror="predefineProfileImage(this);" src="${SourceLeadWeb_userImgUrl}"/>';
	            result = result+'        </div>';
	            result = result+'    </div>';
	            result = result+'     <div class="comments-content">';
	            result = result+'         <div class="pull-left">';
	            result = result+'         <p class="comment-by"></p>';
	            result = result+'         <p class="comment-text"></p>';
	            result = result+'         </div>';
	            result = result+'         <div class="pull-right cmt-button">';
	            result = result+'          <span class="comments-button">';
	            
	            result = result+'          	<button type="button" class="btn btn-sm btn-primary" onClick="showComment('+candidateId+','+submissionId+')">';
	            result = result+'            	<i class="fa fa-plus"></i>&nbsp;Comments';
	            result = result+'            </button>';
	            result = result+'            <div class="add-comment">';
	            result = result+'            </div>';
	            result = result+'          </span>';
	            result = result+'          <a href="javascript:void(0)" class="comments-view" onclick="candidateCommentsList('+candidateId+','+submissionId+');">View more <span class="label label-warning">'+json.candidateCommentsCount+'</span></a>';
	 			result = result+'		</div>';
	            result = result+'     </div>';
	            result = result+'</div>';
	 		    /* New comments div creation Nov 8th, 2016 */
			
		       result = result + '</div>';
		       result = result + '</div>';
		       result = result + '<div class="clearfix"></div>';
		       result = result + ' <div class="project_side_wrap_inn_tabs">';
		       result = result + '<ul class="new-tabs">';
		       result = result + ' <li class="current"><a href="#tabb-1" onClick="getSubmissionDetailsSideBar('+submissionId+','+candidateId+','+requirementId+');">Time Line</a></li>';
		       result = result + ' <li><a href="#tabb-2" onClick="getCandidateProfile('+candidateId+');">Profile</a></li>';
		       result = result + ' <li><a href="#tabb-3" onClick="getCandidateResume('+candidateId+');">Resume</a></li>';
		       result = result + '<li><a href="#tabb-4" onclick="employeeSubmissionDetails('+candidateId+');">Employer</a></li>';
		       result = result + '<li><a href="#tabb-5" onclick="educationSubmissionDetails('+candidateId+');">Education</a></li>';
		       result = result + '<li><a href="#tabb-6" onclick="certificationSubmissionDetails('+candidateId+');">Certifications</a></li>';
		       result = result + '<li><a href="#tabb-8" onclick="documentsSubmissionDetails('+candidateId+');">Documents</a></li>';
		       result = result + '<li><a href="#tabb-9" onclick="submissionReferencesDetails('+candidateId+');">References</a></li>';
               result = result + '<li id="candidateCommentTab"><a href="#tab-7" onclick="candidateCommentsList('+candidateId+','+submissionId+');">Comments</a></li>';
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
		       
		       result = result + '<div id="tab-7" class="new-tabs_inn">';
			   result = result + '</div>';
			   
			   result = result + '<div id="tabb-8" class="new-tabs_inn">';
			   result = result + '</div>';
			   
			   result = result + '<div id="tabb-9" class="new-tabs_inn">';
			   result = result + '</div>';
		       
		       result = result + '<div id="tabb-1" class="new-tabs_inn" style="display: block;">';
		       if("${candidateKeyword}" == "rescheduleInterviewNotification"){
		    	   $("#detailsTab").removeClass("current");
		    	   $('li[name=applicantNotification]').addClass("current");
// 		    	   $('testId').addClass("current");
		    	   requirementPipeline(requirementId,'all');
		    	   requirementStatusBar(requirementId);
		    	   $("#quickview").removeClass("active in");
			    	$("#timelineview").removeClass("active in");
			    	$("#detailview").addClass("active in");
			    	$("#pipelineQuickviewTabsqv").removeClass("active");
			    	$("#candTimelineView").removeClass("active");
			    	$("#pipelineQuickviewTabsdetails").addClass("active");
			    }
		       result = result + '<div class="require_wrap_details">';
		       if(json.submissionStatus == 'REJECTED'){
		    	   result = result + '<div class="clearfix" id="reactiveApplicantBtnDiv"><a class="pull-right btn btn-primary btn-sm color_white" id="reactiveApplicant" href="#" onClick="reActivateApplicant('+submissionId+','+candidateId+','+requirementId+',\'rejected\');"><i class="fa fa-undo" aria-hidden="true"></i>&nbsp; ReActivate</a> </div>';
		       }
		       if(json.submissionStatus == 'BACKOUT'){
		    	   result = result + '<div class="clearfix" id="reactiveApplicantBtnDiv"><a class="pull-right btn btn-primary btn-sm color_white" id="reactiveApplicant" href="#" onClick="reActivateApplicant('+submissionId+','+candidateId+','+requirementId+',\'backout\');"><i class="fa fa-undo" aria-hidden="true"></i>&nbsp; ReActivate</a> </div>';
		       }
		       result = result + '<div class="clearfix">';
			   result = result + '</div> ';        
				   
			   result = result + ' <div class="row positionRelative">';
			  
			   			//Submission Details
			   			 result = result + '<div class="col-md-6">';
			  			 result = result + '<div class="release-jobs submissionDetails">';
					     result = result + '<div class="clearfix sub-details">';
					     result = result + ' <h2 class="active">Submission Details ';
					     if(json.submissionStatus == 'SUBMISSION'){
					    	 result = result + '<i class="fa fa-pencil-square ml" title="Edit Submission" onClick="editSubmittedApplicant('+requirementId+','+submissionId+','+candidateId+','+json.resumeId+')"></i>';
					     }
					     result = result + '</h2>';
					     result = result + ' <div class="content clearfix" style="display: block;">';
					     result = result + submissionDetails;
					     result = result + ' </div>';
					     result = result + ' </div>';
		                 

		                 result = result + ' <div id="viewfeedbackdiv" class="showfeedback"></div>';
		                 result = result + ' <div id="noviewfeedbackdiv" class="shownofeedback"></div>';
		                 
	                     result = result + ' <div class="bg_mask"></div>';
	                     result = result + ' </div>';
	                     result = result + ' </div>';
	                    
	                    
	                  
	                  result = result + '<div class="col-md-6">';
	     			  
	                  
	                  
	                  result = result+'<div class="mask_inner" id="mask_inner"><div class="feedback_close" onClick="feedback_close();">&times;</div></div> ';
	     			  
	                  //Customer Interviews List
	     			/* '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_INTERVIEW\',\'ROLE_VIEW_CANCEL_INTERVIEW\')">';
	     			  result  = result + '   <div id="customerInterviewsList" ></div>';
	     			 '</sec:authorize>';  */
	     			 
	     			 //Reject Offer Popup
	     			  result = result+'<div id="rejectOfferAlert" ></div>  ';
	     			 
	     			 //Internal Offer List
	     			 '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_OFFER\',\'ROLE_VIEW_ACCEPT_OFFER\',\'ROLE_VIEW_REJECT_OFFER\')">';
	     			  result  = result + '   <div id="offerDetails" ></div>';
	     			 '</sec:authorize>';
	     			  
	     			 '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_INTERVIEW\',\'ROLE_VIEW_CANCEL_INTERVIEW\')">';
	                  result  = result + '   <div id="internalInterviewsList" ></div>';
	                  '</sec:authorize>';
	     			 
	     			 
	     			 result = result + ' </div>';      

       
          result = result + '</div>';
          
        //Assignmemeber Comment
          if(json.needApplicantPermit == 'Y'){
        	  result = result + ' <div class="row positionRelative">';
	          result = result + ' <div class="col-sm-12 col-md-12">';
		          //if(json.submissionRateStatus != '' && json.submissionStatus != '' && json.submissionRateStatus != null ){
			          result = result + ' <div class="panel panel-default" id="assignComment">';
			          result = result + ' <div class="panel-heading">Applicant Authorization</div>';
			          //result = result + ' <div class="panel-body" id="assignComment">'+json.submissionRequestComment+'</div>';
			          result = result + ' <div class="panel-body row">';
			          result = result + ' <div class="clearfix"><label class="col-md-2 text-right paddingRight">Requested by:</label><span class="col-md-9">'+json.submittedBy+'</span></div>';
			          if(json.submissionRateStatus == 'ACCEPTED'){
				        	  if(json.submissionRequestUser != null && json.submissionRequestUser != 'undefined'){
				        		  result = result + ' <div class="clearfix mt10"><label class="col-md-2 text-right paddingRight">Accepted by:</label><span class="col-md-9">'+json.submissionRequestUser+'</span></div>';
				        	  }else{
				        		  result = result + ' <div class="clearfix mt10"><label class="col-md-2 text-right paddingRight">Accepted by:</label><span class="col-md-9">'+json.requirementSubmissionTemplateFields.firstName+' '+lastName+'</span></div>';
				        	  }
				        	 }
				          if(json.submissionRateStatus == 'REJECTED'){
				        	  if(json.submissionRequestUser != null && json.submissionRequestUser != 'undefined'){
				        		  result = result + ' <div class="clearfix mt10"><label class="col-md-2 text-right paddingRight">Rejected by:</label><span class="col-md-9">'+json.submissionRequestUser+'</span></div>';
				        	  }else{
				        		  result = result + ' <div class="clearfix mt10"><label class="col-md-2 text-right paddingRight">Rejected by:</label><span class="col-md-9">'+json.requirementSubmissionTemplateFields.firstName+' '+lastName+'</span></div>';
				        	  }
				        	 }
				          if(json.submissionRequestComment != null && json.submissionRequestComment != '' && json.submissionRequestComment != 'undefined'){
					          result = result + ' <div class="clearfix mt10"><label class="col-md-2 text-right paddingRight">Attachment:</label><span class="col-md-9"><a href=\"'+json.docURL+'\" class="pull-left">'+json.submissionRequestFileName+'</a></span></div>';
					          result = result + ' <div class="clearfix mt10"><label class="col-md-2 text-right paddingRight">Comment:</label><span class="col-md-9">'+json.submissionRequestComment+'</span></div>';
					          }
		          }
	          
	          result = result + ' </div>';
	          result = result + ' </div>';
		         
	        result = result + ' </div>';

	        result = result + ' </div>';
       
          //Reject Reason
          result = result + '<div class="row positionRelative">';
          result = result + '<div class="col-sm-12 col-md-12">';
          if(json.rejectedReason != null && json.rejectedReason !='' && json.rejectedReason !='undefined'){
        	  if(json.submissionRateStatus == 'REJECTED'){ 
        	  result = result + '<div class="clearfix">';
				 result = result + '<div class="release-jobs alert alert-danger">';
			     result = result + '<div class="clearfix sub-details">';
			     result = result + ' <p><strong>Rejected Reason</strong></p>';
			     result = result + ' <div class="clearfix">';
			     result = result + ' '+json.rejectedReason+' ';
			     result = result + ' </div>';
			     result = result + ' </div>';
          		 result = result + ' </div>';
	          	 result = result + ' </div>';
      	  }          
          result = result + '</div>';
          result = result + '</div>';
          }
        
'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_INTERVIEW\',\'ROLE_VIEW_CANCEL_INTERVIEW\')">';
     getInternalInterviewsList(submissionId);
'</sec:authorize>';
          

//getCustomerInterviewsList(submissionId,requirementId);


'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_OFFER\',\'ROLE_VIEW_ACCEPT_OFFER\',\'ROLE_VIEW_REJECT_OFFER\')">';
	getOfferDetails(submissionId);
'</sec:authorize>';

$("#viewCandidateDiv").empty();  
$("#viewCandidateDiv").append(result);

$("#tabb-1").show()
$("#detailViewLoading").removeAttr("style");
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
	var atsUrl ='${SourceLeadWeb_atsUrl}';
	var parentRequirement = '${parentRequirement}';
    
	 $.ajax({  
         type : "POST",
         url: atsUrl+"/getAllInternalInterviews?"+tokenParamName+"="+tokenValue+"&submissionId="+submissionId,        
         success :function(data) {
              
        	 if(data != null && data != 'undefined' && data != "" && data != '[]'){
            	
	                	 $("#internalInterviewsList").empty();
	                	 var result = "";
                    	 result = result + '<div class="clearfix">';
   					     result = result + '<div class="content clearfix" style="display: block;">';
	                     var json = JSON.parse(data);
	                     
	                     
	                     for (var i = 0; i < json.length; i++) { 
	                    	 
	                    	 var report = json[i]; 
	                    	 var notifiedDetails ="";
	                    	                     	                     	 
	                    	// var interviewers = "---";
	                    	 var interviewTypeHeader = "";
	                    	 var interviewType = "---";
	                    	// var timeZone = "---";
	                    	 var location = "---"; 
	                    	
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
	                    	 if(report.endTimeStr != null && report.endTimeStr != '' && report.endTimeStr != 'undefined'){
	                    		 endTime = report.endTimeStr;
	                    	 }else{
	                    		 endTime = "";
	                    	 }
	                    	 
	                    	 
	                    	 /* if(report.interviewersList != null && report.interviewersList != 'undefined' && report.interviewersList != ""){
	                    		 interviewers = report.interviewersList;
	                    	 } */
	                    	 
	                    	 if(report.interviewId.interviewType != null && report.interviewId.interviewType != 'undefined' && report.interviewId.interviewType != ""){
	                    		 interviewType = report.interviewType;
	                    		 interviewTypeHeader = report.interviewId.interviewType;
	                    	 }
	                    	 
	                    	 /* if(report.timeZone != null && report.timeZone != 'undefined' && report.timeZone != ""){
	                    		 timeZone = report.timeZone;
	                    	 } */
	                    	 if(report.interviewId.location != null && report.interviewId.location != 'undefined' && report.interviewId.location != ""){
	                    		 location = report.interviewId.location;
	                    	 }
	                    	 result = result + '<div id="feedbackdiv'+report.interviewId.id+'" class="require_wrap_table feedbackdivPostion" style="display:none;">';
		                     result = result + '</div>';
		                     
	   					     result = result + '<div id="accordion-sl" class="accordion-sldiv release-jobs positionRelative">';
	   					   
	   					   //This condition  is to enable the feedback  and reschedule icon for Own Interviews
	   					     if(submissionId == report.interviewSubmissionId){
	   					    	 if(report.interviewersList != null && report.interviewersList != 'undefined' && report.interviewersList != ""){
			   						  if(report.interviewTemplateid != null && report.interviewTemplateid != '' && report.interviewTemplateid != 'undefined'){
			   							result = result + '<i style="color:#fff" class="fa fa-handshake-o pull-right" title="FeedBack" onclick="interviewFeedback(\''+report.interviewId.id+'\',\''+report.interviewTemplateUUId+'\',\''+report.id+'\',\''+submissionId+'\');"></i>';  
			   						  }
			   					  }
	   					     }
		   					 
		   					  result = result + '<h2>Interview: <span>'+interviewTypeHeader+' '+report.interviewTimezoneDateStr+' '+report.interviewTimeZoneStartTimeStr+' </span></h2>';

	   					     result = result + '<div class="content clearfix">';
	                    	 result = result + '<div class="interview-type" id="interviewtypediv'+report.id+'" style="display:block;">';
	                    	 
	                    	 result = result + '<div class="row clearfix">';
	                    	 result = result + '<div class="action-nav" >';
	                    	 result = result + '<span id="'+report.interviewId.id+'actionDiv">';
	                    	
		                    	 if(report.interviewId.interviewStatus == null || report.interviewId.interviewStatus == '' || report.interviewId.interviewStatus =='undefined'){
		                    		 /* if(report.feedbackStartTime != "feedbackStartTime" || report.feedbackEndTime != "feedbackEndTime"){ */
		                    	   //Latest interview for Reschedule  start
		                    	   //This condition  is to enable the feedback  and reschedule icon for Own Interviews
		                    	   if(report.submissionStatus == "REJECTED"){
		                    	   }else if(report.submissionStatus == "SELECTED_ON_INTERVIEW"){
		                    		   
		                    	   }else{
				                    	//Latest interview for Reschedule  ends
			                    		 result = result + '<a href="javascript:void(0)" onclick="changeInterviewStatus('+submissionId+','+report.interviewId.id+',\'Attending\');" title="Attending"><i class="fa fa-check text-success"></i></a>';
			                    		 '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CANCEL_INTERVIEW\')">';
				                    	 result = result + '<a href="javascript:void(0)" onclick="changeInterviewStatus('+submissionId+','+report.interviewId.id+',\'Not_Attending\');" title="Not Attending"><i class="fa fa-close text-danger"></i></a>';
				                    	 '</sec:authorize>';
				                    	 if(report.proposeTimeStatus=="Y"){
				                    		 result = result + '<a href="javascript:void(0)"  class="brder-int" title="Propose New Time"><i class="fa fa-clock-o text-muted" onclick="proposeTime(\''+report.interviewId.id+'\',\''+submissionId+'\');"></i>';
					                    	 result = result + '			<div class="proposeTimePop" id="proposeTimediv'+report.interviewId.id+'" >';
												result = result + '			</div>';
												result = result + ' </a>';
				                    	 }
		                    	   }
		                    	  
			                    	 
// 			                    	 result = result + '<a href="javascript:void(0)" onclick="changeInterviewStatus('+submissionId+','+report.interviewId.id+',\'Maybe\');" title="Maybe"  class="brder-int"><i class="fa fa-question-circle text-muted"></i></a>';
		                    	/*  } */
		                    }
		                    	 if(submissionId == report.interviewSubmissionId){
		                    		   if(i == 0){
		                    			   if(report.interviewId.interviewPhase != "Selected" && report.interviewId.interviewPhase != "Rejected"  && report.submissionStatus != "REJECTED"){
					                    		result = result + '		  <a title="Reschedule"><i  class="fa fa-retweet text-warning" onclick="rescheduleInterView(\''+report.interviewId.id+'\',\''+report.timezone+'\',\''+submissionId+'\');"></i>';
												result = result + '			<div class="reSchPop" id="rescheduleInterViewdiv" >';
												result = result + '			</div>';
												result = result + '			 </a> ';
		                    			   }		                    		
				                    	}
		                    	   }
	                    	 result = result + '</span>';
	                    	 result = result + '<span id="'+report.interviewId.id+'showStatus"></span>';
	                    	 if(report.interviewId.interviewStatus == "Attending"){
	                    		 if(report.interviewId.interviewPhase == "Selected"){
	                    			 result = result +' <font color="green" class="brder-int">Selected</font>';
	                    		 }else if(report.interviewId.interviewPhase == "Rejected"){
	                    			 result = result +' <font color="red" class="brder-int">Rejected</font>';
	                    		 }else{
	                    			 result = result +' <font color="green" class="brder-int">Attending</font>';
	                    		 }
	                    	 }
	                    	 if(report.interviewId.interviewStatus == "Not_Attending"){
	                    		 result = result +' <font color="red" class="brder-int">Not Attending</font>';
	                    	 }
	                    	 if(report.interviewId.interviewStatus == "Maybe"){
	                    		 if(report.submissionStatus == "REJECTED"){
	                    			 result = result + '<a href="javascript:void(0)"> <i  class="brder-int">ProposedTime</i>';	 
	                    		 }else{
	                    			 result = result + '<a href="javascript:void(0)"> <i  class="brder-int" onclick="viewProposeTime(\''+report.interviewId.id+'\',\''+submissionId+'\');">ProposedTime</i>';
	                    		 }
	                    		
		                    	 result = result + '			<div class="proposeTimePop" id="viewProposeTimediv'+report.interviewId.id+'" >';
									result = result + '			</div>';
									result = result + ' </a>';
	                    		
	                    	 }
	                    	 
	                    	 if(report.notificationToApplicant == null || report.notificationToApplicant == '' || report.notificationToApplicant =='undefined' || report.notificationToApplicant == 'N'){
	                    		 result = result + '<span id="'+report.id+'applicantDiv">';
	                    		 result = result + '<a href="javascript:void(0)" class="brder-int-left" onclick="sendMailToapplicant('+submissionId+','+report.interviewId.id+','+report.id+');" title="Notify to Applicant"><i class="fa fa-user text-muted"></i></a>';
	                    		 result = result + '</span>';
	                    	 }
	                    	 if((report.notificationToSpoc == null || report.notificationToSpoc == '' || report.notificationToSpoc =='undefined' || report.notificationToSpoc == 'N') && (parentRequirement == 'Y') && (report.isNotifyToVendorRequired == 'Y')){
	                    		 result = result + '<span id="'+report.id+'vendorDiv">';
	                    		 result = result + '<a href="javascript:void(0)" class="brder-int-left" onclick="sendMailToVendor('+submissionId+','+report.interviewId.id+','+report.id+');" title="Notify to Vednor"><i class="fa fa-suitcase text-muted"></i></a>';
	                    		 result = result + '</span>';
	                    	 }
	                    	 
	                    	 
	                    	 result = result + '</div>';
	                    	 result = result + '</div>';
	                    	 
	                    	 result = result + '<div class="row clearfix">';
	                    	 result = result + '<label class="col-md-5">';
	                    	 result = result + '<strong>Interview Details</strong>';
	                    	 result = result + '</label>';
	                    	 result = result + '<p class="col-md-7" tbldata-dotdot-me" title= "'+location+'">'+location+'</p>';
	                    	 result = result + '</div>';

	                    	 result = result + '<div class="row clearfix">';
	                    	 result = result + '<label class="col-md-5">';
	                    	 result = result + '<strong>Interview Type</strong>';
	                    	 result = result + '</label>';
	                    	 result = result + '<p class="col-md-7 tbldata-dotdot-me" title= '+interviewType+'>'+interviewType+'</p>';
	                    	 result = result + '</div>';
	                    	 
	                    	 result = result + '<div class="row clearfix">';
	                    	 result = result + '<label class="col-md-5">';
	                    	 result = result + '<strong>Date</strong>';
	                    	 result = result + '</label>';
	                    	 result = result + '<p class="col-md-7 tbldata-dotdot-me" title = '+report.dateOfInterviewStr+'>'+report.dateOfInterviewStr+'</p>';
	                    	 result = result + '</div>';
	                    	 
	                    	 result = result + '<div class="row clearfix">';
	                    	 result = result + '<label class="col-md-5">';
	                    	 result = result + '<strong>Local Time</strong>';
	                    	 result = result + '</label>';
                             if(report.endTimeStr != null && report.endTimeStr != 'undefined' && report.endTimeStr != ""){
                            	 result = result + '<p class="col-md-7 tbldata-dotdot-me" title = "'+report.startTimeStr+' to '+report.endTimeStr+'">'+report.startTimeStr+' to '+report.endTimeStr+' </p>';
	                    	 }else{
	                    		 result = result + '<p class="col-md-7 tbldata-dotdot-me" title = '+report.startTimeStr+'>'+report.startTimeStr+'</p>';
	                    	 }
	                    	 result = result + '</div>';
	                    	 
	                    	 if(report.interviewersList != null && report.interviewersList != 'undefined' && report.interviewersList != ""){
	                    		 result = result + '<div class="row clearfix">';
		                    	 result = result + '<label class="col-md-5">';
		                    	 result = result + '<strong>Interviewers</strong>';
		                    	 result = result + '</label>';
		                    	 result = result + '<p class="col-md-7 tbldata-dotdot-me" title = "'+report.interviewersList+'">'+report.interviewersList+'</p>';
		                    	 result = result + '</div>';
	                    	 }
	                    	 if(report.remainderTime != null && report.remainderTime != 'undefined' && report.remainderTime != ""){
	                    		 result = result + '<div class="row clearfix">';
		                         result = result + '<label class="col-md-5">';
		                         result = result + '<strong>Reminder details</strong>';
		                         result = result + '</label>';
		                         result = result + '<p class="col-md-7 tbldata-dotdot-me" title = "'+report.remainderTime+'">'+report.remainderTime+'</p>';
		                         result = result + '</div>';
	                    	 }
	                         
	                         result = result + '<div class="row clearfix">';
	                         result = result + '<label class="col-md-5">';
	                         result = result + '<strong>Notified to</strong>';
	                         result = result + '</label>';
	                         result = result + '<p class="col-md-7 tbldata-dotdot-me"><span id="'+report.id+'notifiedDiv">'+notifiedDetails+'<span></p>';
	                         result = result + '</div>';
	                         
	                         if(report.rescheduleComment != null && report.rescheduleComment != '' && report.rescheduleComment != 'undefined'){
	                        	 result = result + '<div class="row clearfix">';
		                         result = result + '<label class="col-md-5">';
		                         result = result + '<strong>Reschedule Comment</strong>';
		                         result = result + '</label>';
		                         result = result + '<p class="col-md-7 tbldata-dotdot-me"><span title="'+report.rescheduleComment+'">'+report.rescheduleComment+'<span></p>';
		                         result = result + '</div>';
	                         }
	                        
                             if(report.clientName != null && report.clientName != "" && report.clientName != 'undefined' && report.interviewSourceType !="INTERVIEW"){
	                        	 result = result + '<div class="row clearfix">';
		                    	 result = result + '<label class="col-md-5">';
		                    	 result = result + '<strong>Client Name:</strong>';
		                    	 result = result + '</label>';
		                    	 result = result + '<p class="col-md-7 tbldata-dotdot-me" title = '+report.clientName+'>'+report.clientName+'</p>';
		                    	 result = result + '</div>';
	                         }
	                         
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
	                     $('#accordion-sl h2').click(function(id) {
	                    	 $(".newMaxRowNum").val('1');
	                    	 $(".maxRowNum").val('1');
	                    	 
	                    	 $(".reSchPop").removeClass("reSchPopActive");
	                    	 $(".proposeTimePop").removeClass("proposeTimePopActive");
	                    	 $('.feedbackdivPostion').hide();
	                    	 $(".interview-type").show();
	                         if($(this).next().is(':hidden')) {
	                             $('#accordion-sl h2').removeClass('active').next().slideUp('slow');
	                             $(this).toggleClass('active').next().slideDown('slow');
	                         }
	                     });
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
	var atsUrl ='${SourceLeadWeb_atsUrl}';
	 $.ajax({
		   	type: "POST",
		    url: atsUrl+"/ws/acceptOrRejectInterview?"+tokenParamName+"="+tokenValue+"&interviewStatus="+interviewStatus+"&requirementInterviewId="+interviewId,	    	   
		    success: function(data) {
		    	 jQuery("#"+(interviewId)+"actionDiv").css("display","none");
		    	 if(interviewStatus == "Attending"){
           		    $("#"+(interviewId)+"showStatus").html("<font color='green' >Attending</font>");
	           	 }
	           	 if(interviewStatus == "Not_Attending"){
	           		$("#"+(interviewId)+"showStatus").html("<font color='red' >Not Attending</font>");
	           	 }
	           	 if(interviewStatus == "Maybe"){
	           		$("#"+(interviewId)+"showStatus").html("<font color='orange' >Maybe</font>");
	           	 }
		    }
	   });
}
</script>

<script>
function sendMailToapplicant(submissionId,interviewId,interviewHistoryId){
	var atsUrl ='${SourceLeadWeb_atsUrl}';
	 $.ajax({
		   	type: "POST",
		    url: atsUrl+"/sendMailToapplicant?"+tokenParamName+"="+tokenValue+"&submissionId="+submissionId+"&requirementInterviewId="+interviewId+"&interviewHistoryId="+interviewHistoryId,	    	   
		    success: function(data) {
		    	 jQuery("#"+(interviewHistoryId)+"applicantDiv").css("display","none");
           		 var existingVal = $("#"+(interviewHistoryId)+"notifiedDiv").text();
 		    	
 		    	if(existingVal == "" || existingVal == null || existingVal =="---" ){
 		    		 $("#"+(interviewHistoryId)+"notifiedDiv").html("Applicant");
 		    	}
 		    	else{
 		    		 $("#"+(interviewHistoryId)+"notifiedDiv").html(existingVal+",Applicant");
 		    	}
	         }
	   });
}
</script>

<script>
function sendMailToVendor(submissionId,interviewId,interviewHistoryId){
	var atsUrl ='${SourceLeadWeb_atsUrl}';
	 $.ajax({
		   	type: "POST",
		    url: atsUrl+"/sendMailToVendor?"+tokenParamName+"="+tokenValue+"&submissionId="+submissionId+"&requirementInterviewId="+interviewId+"&interviewHistoryId="+interviewHistoryId,	    	   
		    success: function(data) {
		    	jQuery("#"+(interviewHistoryId)+"vendorDiv").css("display","none");
		    	var existingVal = $("#"+(interviewHistoryId)+"notifiedDiv").text();
		    	
		    	if(existingVal == "" || existingVal ==null){
		    		 $("#"+(interviewHistoryId)+"notifiedDiv").html("Vendor");
		    	}
		    	else{
		    		 $("#"+(interviewHistoryId)+"notifiedDiv").html(existingVal+",Vendor");
		    	}
       		   
           	  } 
	   });
}
</script>
<script>
function getCustomerInterviewsList(submissionId,requirementId){
	var atsUrl ='${SourceLeadWeb_atsUrl}';
	 $.ajax({  
         type : "POST",
         url: atsUrl+"/getAllCustomerInterviews?"+tokenParamName+"="+tokenValue+"&submissionId="+submissionId+"&requirementId="+requirementId,        
         success :function(data) {
              if(data != null && data != 'undefined' && data != "" && data != '[]'){
            		
	                	 $("#customerInterviewsList").empty();
	                	 var result = "";
                    	 result = result + '<div class="clearfix">';
   					     result = result + '<div class="content clearfix" style="display: block;">';
	                     var json = JSON.parse(data);
	                     
	                     for (var i = 0; i < json.length; i++) { 
	                    	 
	                    	 var report = json[i]; 
	                    	 var interviewTypeHeader = "";
	                    	 var interviewType = "---";
	                    	//var timeZone = "---";
	                    	 var location = "---";
	                    	 
	                    	 if(report.interviewType != null && report.interviewType != 'undefined' && report.interviewType != ""){
	                    		 interviewType = report.interviewType;
	                    		 interviewTypeHeader = report.interviewType;
	                    	 }
	                    	 
	                    	 if(report.timeZone != null && report.timeZone != 'undefined' && report.timeZone != ""){
	                    		 timeZone = report.timeZone;
	                    	 }
	                    	 
	                    	 if(report.location != null && report.location != 'undefined' && report.location != ""){
	                    		 location = report.location;
	                    	 }
	                    	 
	   					     result = result + '<div id="accordion-sl" class="release-jobs positionRelative">';
	                    	 result = result + '<h2>Interview: <span>'+interviewTypeHeader+' '+report.dateOfInterviewStr+' '+report.startTimeStr+' </span></h2>';
	                    	 result = result + '<div class="content clearfix">';
	                    	 result = result + '<div class="interview-type">';
	                    	 
	                    	 result = result + '<div class="row clearfix">';
	                    	 result = result + '<div class="action-nav" >';
	                    	 result = result + '<span id="'+report.id+'actionDiv">';
		                    	 if(report.interviewStatus == null || report.interviewStatus == '' || report.interviewStatus =='undefined'){
		                    		 result = result + '<a href="javascript:void(0)" onclick="changeInterviewStatus('+submissionId+','+report.id+',\'Attending\');" title="Attending"><i class="fa fa-check text-success"></i></a>';
		                    		 '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CANCEL_INTERVIEW\')">';
			                    	 result = result + '<a href="javascript:void(0)" onclick="changeInterviewStatus('+submissionId+','+report.id+',\'Not_Attending\');" title="Not Attending"><i class="fa fa-close text-danger"></i></a>';
			                    	 '</sec:authorize>';
			                    	 result = result + '<a href="javascript:void(0)" onclick="changeInterviewStatus('+submissionId+','+report.id+',\'Maybe\');" title="Maybe"><i class="fa fa-question-circle text-muted">111</i></a>';
		                    	 }
	                    	 result = result + '</span>';
	                    	 result = result + '<span id="'+report.id+'showStatus"></span>';
	                    	 if(report.interviewStatus == "Attending"){
	                    		 if(report.interviewId.interviewPhase == "Selected"){
	                    			 result = result +' <font color="green">Selected</font>';
	                    		 }else if(report.interviewId.interviewPhase == "Rejected"){
	                    			 result = result +' <font color="red">Rejected</font>';
	                    		 }else{
	                    			 result = result +' <font color="green">Attending</font>';
	                    		 }
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
	                    	 result = result + '<strong>Interview Details</strong>';
	                    	 result = result + '</label>';
	                    	 result = result + '<p class="col-md-7">'+location+'</p>';
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
                             if(report.endTimeStr != null && report.endTimeStr != 'undefined' && report.endTimeStr != ""){
                            	 result = result + '<p class="col-md-7 tbldata-dotdot-big">'+report.startTimeStr+' to '+report.endTimeStr+'</p>';
	                    	 }else{
	                    		 result = result + '<p class="col-md-7 tbldata-dotdot-big">'+report.startTimeStr+'</p>';
	                    	 }
	                    	 result = result + '</div>';
	                    	 
	                    	 /* result = result + '<div class="row clearfix">';
	                    	 result = result + '<label class="col-md-5">';
	                    	 result = result + '<strong>Time Zone</strong>';
	                    	 result = result + '</label>';
                             result = result + '<p class="col-md-7 tbldata-dotdot-big">'+timeZone+'</p>';
	                    	 result = result + '</div>'; */
	                    	 
	                    	 result = result + '<div class="row clearfix">';
	                    	 result = result + '<label class="col-md-5">';
	                    	 result = result + '<strong>Client Name:</strong>';
	                    	 result = result + '</label>';
	                    	 result = result + '<p class="col-md-7 tbldata-dotdot-big">'+report.clientName+'</p>';
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
              }/* else{
	    	         var result ="";
					 $("#customerInterviewsList").empty();
				     $("#customerInterviewsList").append(result); 
              } */
       },
         
    }); 
	 
	 
}
</script>


<script>
function getOfferDetails(submissionId){
	var atsUrl ='${SourceLeadWeb_atsUrl}';
	var parentRequirement = '${parentRequirement}';
	 $.ajax({  
         type : "POST",
         url: atsUrl+"/getOfferDetails?"+tokenParamName+"="+tokenValue+"&submissionId="+submissionId,        
         success :function(data) {
         if(data != null && data != 'undefined' && data != "" && data != '{}'){
            		
            			 $("#offerDetails").empty();
            			 var json = JSON.parse(data);
            			 for (var i = 0; i < json.length; i++) { 
	                    	 
	                    	 var report = json[i]; 
	                    	 
	                    	 var result = '';
	                	     result = result+'<div id="rejectOfferAlert" ></div>  ';
	                	 
	                	     var notifiedDetails ="";
	                    	
	                    	 if(report.notifyToApplicant != null && report.notifyToApplicant != 'undefined' && report.notifyToApplicant != "" && report.notifyToApplicant == "Y"){
	                    		 notifiedDetails = 'Applicant';
				             }
	                    	 if(report.notifyToApplicant != null && report.notifyToApplicant != 'undefined' && report.notifyToApplicant != "" && report.notifyToApplicant == "Y" 
	                    			 && report.notifyToVendor != null && report.notifyToVendor != 'undefined' && report.notifyToVendor != "" && report.notifyToVendor == "Y"){
	                    		 notifiedDetails = notifiedDetails + ' , ';
	                    	 }
	                    	 
	                    	 if(report.notifyToVendor != null && report.notifyToVendor != 'undefined' && report.notifyToVendor != "" && report.notifyToVendor == "Y"){
	                    		 notifiedDetails = notifiedDetails + 'Vendor';
				             }
	                    	 
	                    	 if(notifiedDetails == ""){
	                    		notifiedDetails = '---';
	                    	 }
	                    
	                	 
	                    	 result = result + '<div class="clearfix">';
	   					     result = result + '<div class="content clearfix" style="display: block;">';
	   					  	 result = result + '<div id="offerLoading"></div>';
	   					     result = result + '<div id="accordion-sl" class="release-jobs positionRelative">';
	   					     if(report.templateUUID != null && report.templateUUID != "" && report.templateUUID != "undefined"){
		   					  	 result = result + '<i class="fa fa-eye pull-right viewEyeicon" style="color:#fff !important;" onclick="viewOfferLetter(\''+report.offerUUID+'\');"></i>';	
	   					     }
	                    	 result = result + '<h2  class="active">Offer: <span>'+report.offerSourceType+'</span></h2>';
	                    	 result = result + '<div class="content clearfix">';
	                    	 result = result + '<div class="interview-type">';
	
	                         var accept = "OFFERACCEPTED";
	                    	 var reject = "OFFERREJECTED";
	                    	 result = result + '<div class="row clearfix">';
	                    	 result = result + '<div class="action-nav">';
	                    	 result = result + '<span id="'+report.offerId.id+'offerActionDiv">';
	                    	 
	                    	 if(report.submissionStatusStr != "REJECTED" && report.offerId.offerStatus != "OFFERACCEPTED"  && report.offerId.offerStatus != "OFFERREJECTED" &&  report.offerId.offerStatus != "CLIENT_OFFERACCEPTED" &&  report.offerId.offerStatus != "CLIENT_OFFERREJECTED"){
	                    		 '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_ACCEPT_OFFER\')">';
	                    		 	result = result + '<a  href="javascript:void(0)" title="Accept" onClick="acceptingOffer(\''+accept+'\' ,\''+submissionId+'\',\''+report.offerId.id+'\');"   ><i class="fa fa-check text-success"></i></a>';
	                    		 '</sec:authorize>';
	                    		 '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_REJECT_OFFER\')">';
	                        	 	result = result + '<a  href="javascript:void(0)"  title="Reject"  onClick="rejectOffer( \''+reject+'\' ,\''+submissionId+'\',\''+report.offerId.id+'\');"  ><i class="fa fa-close text-danger"></i></a>';
	                        	 '</sec:authorize>';
	                    	 }
	                    	 result = result + '</span>';
	                    	 result = result + '<span id="'+report.offerId.id+'showOfferStatus"></span>';
	                    	 if(report.offerId.offerStatus == "OFFERACCEPTED"){
	                    		 result = result +' <font color="green" class="brder-int">Offer Accepted</font>';
	                    	 }
	                    	 else if(report.offerId.offerStatus == "OFFERREJECTED"){
	                    		 result = result +' <font color="red" class="brder-int">Offer Rejected</font>';
	                    	 }
	                    	 else if(report.offerId.offerStatus == "Maybe"){
	                    		 result = result +' <font color="orange" class="brder-int">Maybe</font>';
	                    	 }
	                    	 
	                    	 else if(report.offerId.offerStatus == "CLIENT_OFFERACCEPTED"){
	                    		 result = result +' <font color="green" class="brder-int">Client Offer Accepted</font>';
	                    	 }
	                    	 else if(report.offerId.offerStatus == "CLIENT_OFFERREJECTED"){
	                    		 result = result +' <font color="red" class="brder-int">Client Offer Rejected</font>';
	                    	 }
	                    	 
	                    	 if(report.notifyToApplicant == null || report.notifyToApplicant == '' || report.notifyToApplicant =='undefined' || report.notifyToApplicant == 'N'){
	                    		 result = result + '<span id="'+report.id+'offerApplicantDiv">';
	                    		 result = result + '<a href="javascript:void(0)" class="brder-int-left" onClick ="notifyToApplicant(\''+ submissionId+ '\',\''+ report.offerId.id+ '\',\''+ report.id+ '\');" title="Notify to Applicant"><i class="fa fa-user text-muted"></i></a>';
	                    		 result = result + '</span>';
	                    	 }
	                    	 if((report.notifyToVendor == null || report.notifyToVendor == '' || report.notifyToVendor =='undefined' || report.notifyToVendor == 'N') && (parentRequirement == 'Y') && (report.isNotifyToVendorRequired == 'Y')){
	                    		 result = result + '<span id="'+report.id+'offerVendorDiv">';
	                    		 result = result + '<a href="javascript:void(0)" class="brder-int-left" onclick="notifyToVendor('+submissionId+','+report.offerId.id+','+report.id+');" title="Notify to Vednor"><i class="fa fa-suitcase text-muted"></i></a>';
	                    		 result = result + '</span>';
	                    	 }
	                    	 result = result + '</div>';
	                    	 result = result + '</div>';
	                    	 
	                    	 if(report.dateOfJoiningStr !=null && report.dateOfJoiningStr !='' && report.dateOfJoiningStr !='undefined'){
		                    	 result = result + '<div class="row clearfix">';
		                    	 result = result + '<label class="col-md-5">';
		                    	 result = result + '<strong>Joining Date</strong>';
		                    	 result = result + '</label>';
		                    	 result = result + '<p class="col-md-7">'+report.dateOfJoiningStr+'</p>';
		                    	 result = result + '</div>';
	                    	 }else{
	                    		 result = result + '<div class="row clearfix">';
		                    	 result = result + '<label class="col-md-5">';
		                    	 result = result + '<strong>Joining Date</strong>';
		                    	 result = result + '</label>';
		                    	 result = result + '<p class="col-md-7">No Data</p>';
		                    	 result = result + '</div>';
	                    	 }
	                    	 
	                    	 if(report.offerId.positionType !=null && report.offerId.positionType !='' && report.offerId.positionType !='undefined'){
	                    		 result = result + '<div class="row clearfix">';
	                        	 result = result + '<label class="col-md-5">';
	                        	 result = result + '<strong>Position Type</strong>';
	                        	 result = result + '</label>';
	                        	 result = result + '<p class="col-md-7 tbldata-dotdot-big">'+report.offerId.positionType+'</p>';
	                        	 result = result + '</div>';
	                    	 }else{
	                    		 result = result + '<div class="row clearfix">';
	                        	 result = result + '<label class="col-md-5">';
	                        	 result = result + '<strong>Position Type</strong>';
	                        	 result = result + '</label>';
	                        	 result = result + '<p class="col-md-7 tbldata-dotdot-big">No Data</p>';
	                        	 result = result + '</div>';
	                    	 }
	                    	 
	                    	 if(report.clientName !=null && report.clientName !='' && report.clientName !='undefined'){
	                    		 result = result + '<div class="row clearfix">';
	                        	 result = result + '<label class="col-md-5">';
	                        	 result = result + '<strong>Client Name</strong>';
	                        	 result = result + '</label>';
	                        	 result = result + '<p class="col-md-7 tbldata-dotdot-big">'+report.clientName+'</p>';
	                        	 result = result + '</div>';
	                    	 }
	                    	 if(report.offerId.amount !=null && report.offerId.amount !='' && report.offerId.amount !='undefined'){
	                    		 result = result + '<div class="row clearfix">';
	                        	 result = result + '<label class="col-md-5">';
	                        	 result = result + '<strong>Rate</strong>';
	                        	 result = result + '</label>';
	                        	 result = result + '<p class="col-md-7 tbldata-dotdot-big">'+report.offerId.paymentType+'  '+report.offerId.amount+'  '+report.offerId.paymentMode+'</p>';
	                        	 result = result + '</div>';
	                    	 }else{
	                    		 result = result + '<div class="row clearfix">';
	                        	 result = result + '<label class="col-md-5">';
	                        	 result = result + '<strong>Rate</strong>';
	                        	 result = result + '</label>';
	                        	 result = result + '<p class="col-md-7 tbldata-dotdot-big">No Data</p>';
	                        	 result = result + '</div>';
	                    	 }
	                    	
	                    	 if(report.offerId.docFile !=null && report.offerId.docFile !='' && report.offerId.docFile !='undefined'){
	                    		 
	                    		 result = result + '<div class="row clearfix">';
	                        	 result = result + '<label class="col-md-5">';
	                        	 result = result + '<strong>Attachment</strong>';
	                        	 result = result + '</label>';
	                             result = result + '<p class="col-md-7 tbldata-dotdot-big">'+report.offerId.fileName+'<a download class="glyphicon glyphicon-download-alt" href=\"'+atsUrl+'/ws/downloadOfferAttachment?offerId='+report.offerId.id+'\"></a><a class="fa fa-eye ml10" onclick="viewOfferDocument(\''+report.offerId.id+'\',\''+report.offerId.fileName+'\')"></a></p>';
	                        	 result = result + '</div>';
	                    	 }else{
	                    		 result = result + '<div class="row clearfix">';
	                        	 result = result + '<label class="col-md-5">';
	                        	 result = result + '<strong>Attachment</strong>';
	                        	 result = result + '</label>';
	                             result = result + '<p class="col-md-7 tbldata-dotdot-big">No Document</p>';
	                        	 result = result + '</div>';
	                    	 }
	                    	 
	                    	 if(report.offerExpireDate != null && report.offerExpireDate != "" && report.offerExpireDate  != "undefined"){
		                    	 result = result + '<div class="row clearfix">';
	                        	 result = result + '<label class="col-md-5">';
	                        	 result = result + '<strong>Offer Expire Date</strong>';
	                        	 result = result + '</label>';
	                             result = result + '<p class="col-md-7 tbldata-dotdot-big">'+report.offerExpireDate+'</p>';
	                        	 result = result + '</div>';
	                    	 }
	                    	 
                    		 result = result + '<div class="row clearfix">';
	                         result = result + '<label class="col-md-5">';
	                         result = result + '<strong>Notified to</strong>';
	                         result = result + '</label>';
	                         result = result + '<p class="col-md-7 tbldata-dotdot-big"><span id="'+report.id+'offerNotifiedDiv">'+notifiedDetails+'<span></p>';
	                         result = result + '</div>';
                        	 
                    	
	                         result = result + '</div>';
	                         result = result + '</div>';
	                         result = result + '</div>';
	                    	 
			                     
		                     result = result + '</div>';
		                     result = result + '</div>';
		                     $("#offerDetails").append(result); 
		                     $('#accordion-sl .content').hide();
		                     $('#accordion-sl h2:first').addClass('active').next().slideDown('slow');
		                     $('#accordion-sl h2').click(function() {
		                         if($(this).next().is(':hidden')) {
		                             $('#accordion-sl h2').removeClass('active').next().slideUp('slow');
		                             $(this).toggleClass('active').next().slideDown('slow');
		                         }
		                     });
			    	} 
         } else{
				  $("#offerDetails").empty();
               }
       },
         
    }); 
	 
	 
}

</script>

<script>
function viewOfferDocument(offerId,title){
	var atsUrl ='${SourceLeadWeb_atsUrl}';
	if(offerId != null && offerId != ""){
		window.open(atsUrl+'/viewOfferDocument/'+title+'?offerId='+offerId,title,"height=600,width=600");
	}  
}
</script>


<%-- 
     @author : David Raju Sammeta
     this script is for Reject Offer alert
     1.usage     : used in saveOrCancel() / alertMessage removeAlertMessage()
--%>

<script type="text/javascript">
function canceloffer(){ 
	$("#remove_user_pop, .comMask").fadeOut(200);
};
</script>

<script type="text/javascript">
function rejectOffer(offerStatus,submissionId,offerId){
	
	var result = "";
	result = result +"	<div class='comMask'></div>";
	result = result +"	    <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<textarea id='rejectReason' class='form-control' field-name='Reject Reason' data-validation-optional='true' data-validation='validate_Space validate_Description validate_length length2-250' name='rejectReason' value='' placeholder='Terminate Reason' rows='2'></textarea>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onClick ='acceptingOffer(\""+ offerStatus+ "\" ,\""+ submissionId+ "\",\""+ offerId+ "\");'><input class='u_can_color' type='button'  value='Cancel' onClick='canceloffer()'></b>";
	result = result +"	</div>";
	$("#rejectOfferAlert").append(result);
	$("#remove_user_pop").slideDown(200).css({"z-index":"170000"});
	$(".comMask").fadeIn(200);
}
</script>




<script>
function acceptingOffer(offerStatus,submissionId,offerId){
	
	 jQuery("#"+(offerId)+"offerActionDiv").css("display","none");
	 $("#offerLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	
	 var atsUrl ='${SourceLeadWeb_atsUrl}';;
	 var reason;
	 if ($('#rejectReason').length)
	 {
		 reason = document.getElementById("rejectReason").value; 
	 }
	 else
	 {
		 reason = '';
	 }
	 var formData = new FormData();
     formData.append("tokenParamName", tokenValue);
     formData.append("submissionId", submissionId);
     formData.append("offerStatus", offerStatus);
     formData.append("reason", reason);
     formData.append("OfferId", offerId);
	 $.ajax({  
		  type : "POST",
          data: formData,
          processData: false,
          contentType:false, 
     	  url: atsUrl+"/acceptingOffer",
         //url: atsUrl+"/acceptingOffer?"+tokenParamName+"="+tokenValue+"&submissionId="+submissionId+"&offerStatus="+offerStatus+"&reason="+reason+"&OfferId="+offerId,        
         success :function(data) {

        	 $("#rejectOfferAlert").empty();
           	 
	    	 if(offerStatus == "OFFERACCEPTED"){
      		    $("#"+(offerId)+"showOfferStatus").html("<font color='green' >OFFERACCEPTED</font>");
          	 }
          	 if(offerStatus == "OFFERREJECTED"){
          		$("#"+(offerId)+"showOfferStatus").html("<font color='red' >OFFERREJECTED</font>");
          	 }
             $("#offerLoading").removeAttr('style');
             $("#remove_user_pop, .comMask").fadeOut(200);
        	 getOfferDetails(submissionId);
        	 
       },
         
    });  
	 
	 
}

</script>


<script>
function notifyToApplicant(submissionId,offerId,offerHistoryId){
	
	var atsUrl ='${SourceLeadWeb_atsUrl}';
	$("#offerLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	  $.ajax({  
        type : "POST",
        url: atsUrl+"/sendNotificationAboutRequirementOfferMade?"+tokenParamName+"="+tokenValue+"&submissionId="+submissionId+"&offerId="+offerId+"&notificationtype=Applicant&offerHistoryId="+offerHistoryId,        
        success :function(data) {
        	    jQuery("#"+(offerHistoryId)+"offerApplicantDiv").css("display","none");
        	    var existingVal = $("#"+(offerHistoryId)+"offerNotifiedDiv").text();
		    	if(existingVal == "" || existingVal == null || existingVal =="---" ){
		    		 $("#"+(offerHistoryId)+"offerNotifiedDiv").html("Applicant");
		    	}
		    	else{
		    		 $("#"+(offerHistoryId)+"offerNotifiedDiv").html(existingVal+",Applicant");
		    	}
		    	$("#offerLoading").removeAttr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
      },
        
   });  
}
</script>

<script>
function notifyToVendor(submissionId,offerId,offerHistoryId){
	var atsUrl ='${SourceLeadWeb_atsUrl}';
	  $.ajax({  
        type : "POST",
        url: atsUrl+"/sendNotificationAboutRequirementOfferMade?"+tokenParamName+"="+tokenValue+"&submissionId="+submissionId+"&offerId="+offerId+"&notificationtype=Vendor&offerHistoryId="+offerHistoryId,        
        success :function(data) {
        	jQuery("#"+(offerHistoryId)+"offerVendorDiv").css("display","none");
	    	var existingVal = $("#"+(offerHistoryId)+"offerNotifiedDiv").text();
	    	
	    	if(existingVal == "" || existingVal ==null){
	    		 $("#"+(offerHistoryId)+"offerNotifiedDiv").html("Vendor");
	    	}
	    	else{
	    		 $("#"+(offerHistoryId)+"offerNotifiedDiv").html(existingVal+",Vendor");
	    	}
      },
        
   });  
}
</script>

<script>
		// Accept function
	/* function acceptFun(submissionId){
		$('#accpet-div').empty();
		$('#reject').empty();
		var result = "";	
		result = result + '	        <form id="accept_form" name="accept_form" action="upload" enctype="multipart/form-data" method="post">';
		result = result + '			<div class="row">';
		result = result + '				<div class="col-md-12">';
		result = result + '					<textarea rows="2" class="form-control" placeholder="Comment *" id="comment" name="comment" data-validation="required validate_Space validate_length length2-250" field-name="Comment"></textarea>';
		result = result + '				</div>';
		result = result + '				</div>';
		result = result + '				<div class="row">';
		result = result + '				<div class="col-md-8">';
		result = result + '					<input type="file" multiple id="docFile" name="docFile" class="mt10" data-validation="required validate_Space validatedoc" field-name="Attachment">';
		result = result + '				</div>';
		result = result + '			<div class="col-md-2"><input type="button" id="accept_candidate" value="Submit" title="Sibmit" class="btn btn-primary btn-sm mt10 ml10" onclick="acceptCandidate(\''+submissionId+'\',\'accept\');">';
		result = result + '			</div>';
		result = result + '			</div>';
		result = result + '	        </form>';
		$('#accpet-div').toggle();
		$('#accpet-div').append(result);
		$('#reject').fadeOut();
		$('#request').fadeOut();
		
	}; */
	/* $("#accpet-div, .clients_side_wrap, .clients_side_wrap_active, .fa, .fa-check, .labelsuccess").click(function(e){
		 e.stopPropagation();
	 });
	$("html").click(function(e){
		 $('#accpet-div').fadeOut();
	 }); */
	 function rejectFun(submissionId,requirementId){
			$('#reject').empty();
			$('#accpet-div').empty();
			var result = "";
			result = result + '	        <form id="reject_form" name="reject_form" action="upload" enctype="multipart/form-data" method="post">';
	        result = result +"    <div class='dropdown-select mb10' id='rejectedCategorytDiv'>";
	        result = result +"<select id='rejectedCategory' name='rejectedCategory' class='form-control' onchange='getApplicantOtherizationRejectedCategory()' data-validation='required' field-name='Category'>";
	        result = result +"<option selected value=''>--Select Category--</option>";
	        result = result +"<option value='Wrong Skill Sets'>Wrong Skill Sets</option>";
	        result = result +"<option value='Unsuitable Personality'>Unsuitable Personality</option>";
	        result = result +"<option value='Bad Fit'>Bad Fit</option>";
	        result = result +"<option value='Salary'>Salary</option>";
	        result = result +"<option value='Interview Rejection'>Interview Rejection</option>";
	        result = result +"<option value='Other'>Other</option>";
	        result = result +"</select>";
	        result = result +"    </div>";
	        
	        result = result +" <div id='rejectOtherCategoryDiv' style='display: none' class='mb10'>";
	        result = result +" <input type='text' id='rejectedCategoryOther' name='rejectedCategoryOther' class='form-control nomr' data-validation-optional='true' field-name='Other Type '>";
	        result = result +" </div>";
	        
			result = result + '			<div class="row">';
			result = result + '				<div class="col-md-12">';
			result = result + '					<textarea rows="2" class="form-control" placeholder="Comment *" id="comment" name="comment" onkeypress="submissionDetailsremoveSpaces();" data-validation="required validate_Space validate_length length2-250" field-name="Comment"></textarea>';
			result = result + '				</div>';
			result = result + '			</div>';
			result = result + '			<div class="row">';
			result = result + '				<div class="col-md-12"><input type="button" id="reject_candidate" value="Submit" title="Sibmit" class="pull-right btn btn-primary mt10 btn-sm ml10" onclick="rejectCandidate(\''+submissionId+'\',\'reject\','+requirementId+');">';
			result = result + '				</div>';
			result = result + '			</div>';
			result = result + '	        </form>';
			$('#reject').toggle();
			$('#reject').append(result);
			$('#accpet-div').fadeOut();
			$('#request').fadeOut();
		};
		$("#reject, .acceptreject-pop, .labelsuccess").click(function(e){
			 e.stopPropagation();
		});
	
	function requestFun(submissionId){
		$('#reject').empty();
		$('#accpet-div').empty();
		$('#request').empty();
		var result = "";
		result = result + '<form id="request_form" name="request_form" action="upload"  method="post">';
		result = result + '			<div class="row">';
		result = result + '				<div class="col-md-12">';
		result = result + '					<textarea rows="2" class="form-control" placeholder="Comment *" id="comment" name="comment" onkeypress="submissionDetailsremoveSpaces();" data-validation="required validate_Space validate_length length2-250" field-name="Comment"></textarea>';
		result = result + '				</div>';
		result = result + '		    </div>';
		/* result = result + '		    <div class="row">';
		result = result + '				<div class="col-md-8" id="docFileuploadFileGroup">';
		result = result + '					<input type="file"  id="docFile" name="docFile"  style="display: none;"  multiple="" class="mt10" data-validation="required validate_Space validatedoc" field-name="Attachment">';

		result = result + '				</div>';
		result = result + '			</div>'; */
		
		result = result+'	                  <div class="clearfix mb10 row mt" id="docFileuploadFileGroup">';
		result = result+'	                    <div class="col-md-12">';
		result = result+'							<div class="input-group positionRelative" id="uploadFileGroup">';
		result = result+'	                          <label class="input-group-btn" onchange = "submissionDocFile();"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File <span class="text-danger">&nbsp;*</span>';
		result = result+'	                            <input type="file" name="docFile" id="docFile" style="display: none;" class="upload">';
		result = result+'	                            </span> </label>';
		result = result+'	                          <input type="text" class="form-control margin-top0" readonly id="subUploadFile" name="subUploadFile" placeholder="Attachment" field-name="Attachment" data-validation="required validate_Space validatedoc">';
		result = result+'	                        </div>';
		result = result+'	                    </div>  ';  
		result = result+'	                  </div>   '; 
		result = result + '			<div class="row">';
		result = result + '				<div class="col-md-12"><input type="button" id="request_candidate" value="Submit" title="Sibmit" class="pull-right btn btn-primary mt10 btn-sm ml10" onclick="requestCandidate(\''+submissionId+'\',\'request\');">';
		result = result + '				</div>';
		result = result + '			</div>';
		result = result + '</form>';
		$('#request').toggle();
		$('#request').append(result);
		$('#accpet-div').fadeOut();
		$('#reject').fadeOut();
		//attachmentValidationFun();
		 
		/* upload file script for size validation 2MB*/
	    
	    document.getElementById("docFile").onchange = function () {
			   //$("#uploadFile").dir(this.files[0]);
		    	//document.getElementById("uploadFile").value = this.value;
		    	$('#subUploadFile').val($(this).val().replace(/C:\\fakepath\\/i, ''))
		  
		   }; 
	};
	$("#request, .acceptreject-pop, .labelsuccess").click(function(e){
		 e.stopPropagation();
	});
	
	
</script>
<script type="text/javascript">
function submissionDocFile() {
	fileUpload("docFile", "requestDocuments", "request_form");
}
</script>
<script>
   function submissionDetailsremoveSpaces() {
	    var output = document.getElementById("comment").value;
	    output = output.replace(/\s/g, "") 
	    if(output.length != 0){
	    $("#comment").removeAttr("data-validation");
	    }
	}
</script>
<%-- 
     @author : sarath kumar punnana 
     this script is for validate document
     1.usage     : request rate
     2.parameter : .
     3.ajax url implementation in CandidateResumeRestController.
--%>
<script type="text/javascript">
function attachmentValidationFun(){
	  	          var el = document.getElementById("docFile");
	  	    	  el.addEventListener('change', function(event) {
		        	     	var attach_id = this.id;
		        	     	var size = $('#'+attach_id)[0].files[0].size;
		        	     	if(size > 2000000){  
		        		    	  $("#docFile").val('');
		        		    	  $("html #docFileuploadFileGroup").append("<p class='jquery_form_error_message'>Limit is exceeded than 2MB </p>");
		        	     	}
		           });
}   
</script>

<script>
function getApplicantOtherizationRejectedCategory(){
	var rejectedCategory = document.getElementById("rejectedCategory").value;
	if(rejectedCategory == 'Other'){
		 document.getElementById("rejectOtherCategoryDiv").style.display="block";
	    $("#rejectedCategoryOther").removeAttr("data-validation-optional");
		$('#rejectedCategoryOther').attr("data-validation","required"); 
		$('#rejectOtherCategoryDiv').find('p.jquery_form_error_message').remove(); 
	}else{
		$("#rejectedCategoryOther").attr("data-validation-optional","true");
		$("#rejectedCategoryOther").removeAttr("data-validation");
		$("#rejectedCategoryOther").val('');
		document.getElementById("rejectOtherCategoryDiv").style.display="none";
		$('#rejectOtherCategoryDiv').find('p.jquery_form_error_message').remove(); 
	}
	
}
</script>
<%-- 
     @author : Venkat Kesav
     this script is for accept Candidate
     1.usage     : onclick
     2.parameter : .
     3.ajax url implementation in CandidateResumeRestController.
--%>
<script type="text/javascript">
	function acceptCandidate(submissionId, decision){
			var validationSettings = {
			        errorMessagePosition : 'element',
			        borderColorOnError : '',
					scrollToTopOnError : true,
					dateFormat : 'yyyy/mm/dd' 
		         }; 
			 if ($('#accept_form').validate(false, validationSettings)){
	      		var formData = getFormData('accept_form'); 
				$("#accept_candidate").attr("disabled", "disabled");
         		$("#accept_candidate" ).addClass('button').val('Processing..');
				
				$.ajax({
						type : "POST",
		        	    data: formData,
						url : atsUrl+"/acceptRejectCandidate?"+tokenParamName+"="+tokenValue+"&submissionId="+submissionId+"&decision="+decision,
						success : function(result) {
							 if( result.indexOf('Sign In') >= 0){
					    		location.replace(sourceleadUrl);
					    	 }
		            		 else if (result.indexOf('exception') >= 0) {
		            			 location.replace(atsUrl+"/errorPage");
		            		 }
		            		 else{
						 		/* $("#popUpDivResume").removeClass("edit-details-active");
						 		candidateResumesList(candidateId); */
						 		 
		            			 $('#accpet-div').fadeOut();
		            			 $('#reject').fadeOut();
		            			 $("#statusDiv").empty();
		            			 $("#statusDiv").append("ACCEPTED");
		            				
		            		 }
						},
					});
					return true;
			 }else{ 
					return false;
			 }
		}
		$('body').on('blur', '#accept_form', function() {
		$("#accept_form").showHelpOnFocus();
		$("#accept_form").validateOnBlur(false, validationSettings);
	});
</script>



<%-- 
     @author : sarath kumar punnana
     this script is for request Candidate
     1.usage     : onclick
     2.parameter : .
     3.ajax url implementation in CandidateResumeRestController.
--%>
<script type="text/javascript">
	function requestCandidate(submissionId, decision){
			var validationSettings = {
			        errorMessagePosition : 'element',
			        borderColorOnError : '',
					scrollToTopOnError : true,
					dateFormat : 'yyyy/mm/dd' 
		         }; 
			 if ($('#request_form').validate(false, validationSettings)){
				 var formData = getFormData('request_form');
				$("#request_candidate").attr("disabled", "disabled");
         		$("#request_candidate" ).addClass('button').val('Processing..');
				//It will escape all the special characters
				$.ajax({
						type : "POST",
		        	    data: formData,
						url : atsUrl+"/acceptRejectCandidate?"+tokenParamName+"="+tokenValue+"&submissionId="+submissionId+"&decision="+decision,
						success : function(result) {
							 if( result.indexOf('Sign In') >= 0){
					    		location.replace(sourceleadUrl);
					    	 }
		            		 else if (result.indexOf('exception') >= 0) {
		            			 location.replace(atsUrl+"/errorPage");
		            		 }
		            		 else{
		            			 $('#accpet-div').fadeOut();
		            			 $('#reject').fadeOut();
		            			 $('#request').fadeOut();
		            			 $("#requestsDiv").empty();
		            			 $("#requestsDiv").append("Request Sent");
		            		 }
						},
					});
					return true;
			 }else{ 
					return false;
			 }
		}
		$('body').on('blur', '#request_form', function() {
		$("#request_form").showHelpOnFocus();
		$("#request_form").validateOnBlur(false, validationSettings);
	});
</script>


<script type="text/javascript">
	function rejectCandidate(submissionId, decision,requirementId){
		var validationSettings = {
			        errorMessagePosition : 'element',
			        borderColorOnError : '',
					scrollToTopOnError : true,
					dateFormat : 'yyyy/mm/dd' 
		         }; 
			 if ($('#reject_form').validate(false, validationSettings)){
				
	      		var formData = getFormData('reject_form'); 
				
				$("#accept_candidate").attr("disabled", "disabled");
         		$("#accept_candidate" ).addClass('button').val('Processing..');
				//It will escape all the special characters
				$.ajax({
						type : "POST",
		        	    data: formData,
						url : atsUrl+"/acceptRejectCandidate?"+tokenParamName+"="+tokenValue+"&submissionId="+submissionId+"&decision="+decision,
						success : function(result) {
							 if( result.indexOf('Sign In') >= 0){
					    		location.replace(sourceleadUrl);
					    	 }
		            		 else if (result.indexOf('exception') >= 0) {
		            			 location.replace(atsUrl+"/errorPage");
		            		 }
		            		 else{
						 		/* $("#popUpDivResume").removeClass("edit-details-active");
						 		candidateResumesList(candidateId); */
		            			 $('#accpet-div').fadeOut();
		            			 $('#reject').fadeOut();
		            			 
		            			 $("#statusDiv").empty();
		            			 $("#statusDiv").append("REJECTED");
		            			 
		            				
		            		 }
							 pipelineList(requirementId);
						},
					});
					return true;
			 }else{ 
					return false;
			 }
		}
		$('body').on('blur', '#reject_form', function() {
		$("#reject_form").showHelpOnFocus();
		$("#reject_form").validateOnBlur(false, validationSettings);
	});
</script>

<script>
function showComment(candidateId,submissionId){
	var result = "";
		result = result+'	        <form role="form" id="candidateComments_form" name="candidateComments_form" method="post">';
	    result = result+'              <textarea rows="1" class="form-control clearfix mb10" placeholder="Comments here..." id="comment" name="comment" data-validation="required validate_length length2-500" field-name="Comment"></textarea>';
	    result = result+'              <span class="clearfix pull-right">';
	    result = result+'              		<input type="button" id="submit_comment" value="Add" class="btn btn-sm btn-success" onClick="addComment('+candidateId+','+submissionId+')">';
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
	function addComment(candidateId,submissionId){
			var validationSettings = {
			        errorMessagePosition : 'element',
			        borderColorOnError : '',
					scrollToTopOnError : true,
					dateFormat : 'yyyy/mm/dd' 
		         }; 
			 if ($('#candidateComments_form').validate(false, validationSettings)){
				var frm = $('#candidateComments_form').serializeFormJSON();
				var con = JSON.stringify(frm);
				con = con.replace(/[{}]/g, "");
				var form = $('#candidateComments_form')[0];
	      		var formData = new FormData(form); 
	      		var atsUrl ='${SourceLeadWeb_atsUrl}';
				$("#submit_comment").attr("disabled", "disabled");
         		$("#submit_comment" ).addClass('button').val('Processing..');
				//It will escape all the special characters
		        var jsonData = encodeURIComponent(con);
				$.ajax({
						type : "POST",
		        	    data: formData,
		        	    contentType : 'application/json; charset=utf-8',
		        	    enctype: 'multipart/form-data',
		    	        processData: false,
		    	        contentType:false,
		        	    dataType : 'json',
						url : atsUrl+"/saveCandidateComment?"+tokenParamName+"="+tokenValue+"&json="+jsonData+"&candidateId="+candidateId+"&submissionId="+submissionId,
						success : function(result) {
							if (result && result.response === "INVALID_USER") {
				    		    showError(result.responseMessage);
				    		} else if (result.response === "AWKWARD") {
				    		    location.href = "/errorPage";
				    		} else if (result.response === "SUCCESS") {
				    			savedCommentAndCount(candidateId,submissionId);
				    		} else if (result.indexOf('Sign In') !== -1) {
								location.replace(sourceleadUrl);
							} else {
							    showError("Operation failed, please try again");
							}
						},
				});
				$.post(atsUrl+"/saveCandidateComment?"+tokenParamName+"="+tokenValue+"&json="+jsonData+"&candidateId="+candidateId+"&submissionId="+submissionId,{},function(result){
					if (result && result.response === "INVALID_USER") {
		    		    showError(result.responseMessage);
		    		} else if (result.response === "AWKWARD") {
		    		    location.href = "/errorPage";
		    		} else if (result.response === "SUCCESS") {
		    			savedCommentAndCount(candidateId,submissionId);
		    		} else if (result.indexOf('Sign In') !== -1) {
						location.replace(sourceleadUrl);
					} else {
					    showError("Operation failed, please try again");
					}
				});
				$('.add-comment').toggle(200);
				return true;
			 }else{ 
					return false;
			 }
		}
		$('body').on('blur', '#candidateComments_form', function() {
		$("#candidateComments_form").showHelpOnFocus();
		$("#candidateComments_form").validateOnBlur(false, validationSettings);
		
	});
</script>

<script>
function savedCommentAndCount(candidateId,submissionId) {
	var atsUrl ='${SourceLeadWeb_atsUrl}';
	  $.ajax({
        type : "POST",
         url: atsUrl+"/getAllCommentsOnCandidateByCandidateIdAndOrgLocationAndSubmissionId?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId+"&submissionId="+submissionId,        
         success :function(data) {
        	 if (data && (data.response === "INVALID_DATA" || data.response === "INVALID_USER")) {
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
    		            
    		            result = result+'        	<img onerror="predefineProfileImage(this);" src="'+report.commentAuthorUserImage+'"/>';
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
    		            
    		            result = result+'          	<button type="button" class="btn btn-sm btn-primary" onClick="showComment('+candidateId+','+submissionId+')">';
    		            result = result+'            	<i class="fa fa-plus"></i>&nbsp;Comments';
    		            result = result+'            </button>';
    		            result = result+'            <div class="add-comment">';
    		            result = result+'            </div>';
    		            result = result+'          </span>';
    		            result = result+'          <a href="javascript:void(0)" class="comments-view" onclick="candidateCommentsList('+candidateId+','+submissionId+');">View more <span class="label label-warning">'+totalComments+'</span></a>';
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

<!-- this script using for interview feedback Start -->
<script type="text/javascript">
function interviewFeedback(interviewId,templateId,id,submissionId) {
	$("#mask_inner").fadeIn();
	$("#feedbackdiv"+interviewId+"").show();
	$(".reSchPop").removeClass("reSchPopActive");
	$(".proposeTimePop").removeClass("proposeTimePopActive");
	var locationId = '${SourceLeadWeb_orgLocationId}';
	var atsUrl ='${SourceLeadWeb_atsUrl}';
	$.ajax({
		type: "GET",
		url: atsUrl+"/getInterviewrsFeedbackList?"+tokenParamName+"="+tokenValue+"&interviewId="+interviewId+"&locationId="+locationId+"&submissionId="+submissionId,
		success: function(json) {
			var result = "";
			result = result +"<strong>Interview Feedback</strong>";
			result = result +"<table id='requirementApproverPagination' cellspacing='0' width='100%' class='mt10'>"; 
		    result = result +"<thead>"; 
		    result = result +"  <tr>";  
		    result = result +"    <th>Interviewer Name</th>"; 
		    result = result +"    <th>Date</th>"; 
		    result = result +"    <th>Action</th>"; 
		    result = result +"  </tr>"; 
		    result = result +"</thead>";
		    result = result +"<tbody>"; 
			for (var i=0; i<json.length; i++) {
				var report = json[i];
				if(report.feedbackDate != null && report.feedbackDate != '' && report.feedbackDate != 'undefined'){
					feedbackDate  = report.feedbackDate;
				}else{
					feedbackDate = "--";
				}
				 result = result +"  <tr>";  
				    result = result +'<td><div class="tbldata-dotdot-me">'+report.interviewerName+'</div></td>'; 
				    result = result +' <td><div class="tblminwidth-100">'+feedbackDate+'</div></td>'; 
				    if(report.feedbackDate != null && report.feedbackDate != '' && report.feedbackDate != 'undefined'){
				    	result = result +' <td><div class="tblminwidth-50"><span class="text-primary"><a onclick="viewfeedback(\''+report.uuid+'\',\''+templateId+'\',\''+report.feedbackStatus+'\');">View</a></span></div></td>';
				    }else{
				    	result = result +'<td><div class="tblminwidth-50"> &nbsp;<span class="text-danger">Notfilled</span></div></td>';
				    }
				     
				    result = result +'</tr>'; 
			}
			result = result +"</tbody>"; 
		    result = result +"</table>"; 
		    $("#feedbackdiv"+interviewId+"").empty();
		    $("#feedbackdiv"+interviewId+"").append(result);
			
		},
	});
}

</script>
<script type="text/javascript">
function viewfeedback(requirementInterviewerId,templateId,feedbackStatus){
	var atsUrl ='${SourceLeadWeb_atsUrl}';
		if(feedbackStatus == "Y"){
			$('.shownofeedback').hide();
			$('.showfeedback').show();
			$(".bg_mask").fadeIn(200);
			$.ajax({
		    	type: "GET",
		    	url: atsUrl+"/ws/getPublicInterviewFeedbackTemplate?templateId="+templateId+"&requirementInterviewid="+requirementInterviewerId, 
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	 var submissionDetails = "";
			    	 var details = eval(json.interviewTemplateJsonString);
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
				  		 result = result + ' <div class="close-btn2" onClick="closefeedback()"><i class="fa fa-close"></i></div>';
				  		 result = result + '<div class="row">';
				  		 result = result + '<div class="col-md-12">';
				  		 result = result + '<div class="release-jobs submissionDetails">';
				  	     result = result + '<div class="clearfix sub-details">';
				  	     result = result + ' <h2 class="active">Interview Feedback Details</h2>';
				  	     result = result + ' <div class="content clearfix" style="display: block;">';
				  	     result = result + submissionDetails;
				  	     result = result + ' </div>';
				  	     result = result + ' </div>';
				         result = result + ' </div>';
				         result = result + ' </div>';
	                   
	                     $('#viewfeedbackdiv').empty();
	             		 $('#viewfeedbackdiv').append(result).show();
		        }
			});
		}else{
			$('.shownofeedback').show();
			$('.showfeedback').hide();
			$(".bg_mask").fadeIn(200);
			$.ajax({
		    	type: "GET",
		    	url: atsUrl+"/ws/getPublicInterviewFeedbackTemplate?templateId="+templateId+"&requirementInterviewid="+requirementInterviewerId, 
		        success: function(data) {
		        	var json = JSON.parse(data);
			        	if(json.comment != null && json.comment != '' && json.comment != 'undefined'){
			        		comment = json.comment;
			        	}else{
			        		comment = "";
			        	}
			        	if(json.interviewStatus != null && json.interviewStatus != '' && json.interviewStatus != 'undefined'){
			        		interviewStatus = json.interviewStatus;
			        	}else{
			        		interviewStatus = "";
			        	}
			        	
			        	
			        	 var result = "";
				  		 
				  		 result = result + '<div class="row">';
				  		 result = result + '<div class="col-md-12">';
				  		 result = result + '<div class="release-jobs submissionDetails">';
				  		result = result + ' <div class="close-btn2" onClick="closefeedback()"><i class="fa fa-close"></i></div>';
				  	     result = result + '<div class="clearfix sub-details">';
				  	     result = result + ' <h2 class="active">Interview Feedback Details</h2>';
				  	     result = result + ' <div class="content clearfix" style="display: block;">';
				  	       result = result +  '&nbsp &nbsp Comment :- &nbsp;'+comment;
				  	     result = result + ' </div>';
				  	        result = result + ' <div class="content clearfix">'; 
					     result = result +  '&nbsp &nbsp Selected for Interview :- &nbsp;'+interviewStatus;
					     result = result + ' </div>';
				  	     result = result + ' </div>';
				         result = result + ' </div>';
				         result = result + ' </div>';
				         
				         
			    	     /* var result = "";
			    	     result = result + '<div class="clearfix positionRelative">';
			    	     result = result + ' <div class="close-btn2" onClick="closefeedback()"><i class="fa fa-close"></i></div>';
			  			 result = result + '<div class="release-jobs submissionDetails">';
					     result = result + '<div class="clearfix sub-details">';
					     result = result + ' <h2 class="active">Interview Feedback Details</h2>';
					     result = result + ' <div class="content clearfix style="display: block;"">'; 
					     result = result +  '&nbsp &nbsp Comment :- &nbsp;'+comment;
					     result = result + ' </div>';
					     result = result + ' <div class="content clearfix">'; 
					     result = result +  '&nbsp &nbsp Selected for Interview :- &nbsp;'+interviewStatus;
					     result = result + ' </div>';
					     result = result + ' </div>';
		                 result = result + ' </div>';
	                     result = result + ' </div>';  */
	                   
	                   $("#noviewfeedbackdiv").empty();
	                   $("#noviewfeedbackdiv").append(result);
		        }
			});
		}
		

}
function closefeedback(){
	$(".bg_mask, #viewfeedbackdiv").fadeOut(200);
	$("#noviewfeedbackdiv").fadeOut(200);
}
</script>
<script>
function rescheduleInterView(interviewId,timezone,submissionId){
	$(".reSchPop").toggleClass("reSchPopActive");
	$(".proposeTimePop").removeClass("proposeTimePopActive");
	var result ='';
	var parentRequirement = '${parentRequirement}';
	result = result	+ ' <form id="rescheduleInterview_form"  name="rescheduleInterview_form" method="post">';
	result = result + '			  <div class="reSchPopInn">';
	result = result + '			   <input type="hidden" id="interviewId" name="interviewId" value='+interviewId+' >';
	result = result + '			   <input type="hidden" id="scheduleType" name="scheduleType" value="reschedule" >';
	result = result + '				  <h3>Reschedule</h3>';
	result = result + '				  <div class="reSchPopCta">';									  	
	result = result + '						<span id="rescheduleDateDiv">';
	result = result + '						<input type="text" class="form-control input-sm calendar" placeholder="Date" id="rescheduleDate" name="rescheduleDate" data-validation="required" field-name="Interview Date">';
	//result = result + '						<i class="fa fa-calendar"></i>';
	result = result + '						</span>	';									  	
	result = result + '						<span class="timeDiv" id="rescheduleTimeDiv">';
	result = result + '						<input type="text" class="timeicon form-control input-sm" placeholder="Start Time" id="feedBackStartTime" name="startTime" onBlur="validateEndTime();" data-validation="required" field-name="Start Time">';
	result = result + '						<input type="text" class="timeicon form-control input-sm" placeholder="End Time" name="endTime" id="feedBackEndTime" onBlur="validateEndTime();">';
	result = result + '						</span>';
	result = result + '				<div class="clearfix"></div> ';
	result = result + '			<div class="form-group Pmess clearfix">';
	result = result + '			          <label class="col-md-3 no_pad">Timezone <b class="text-danger">*</b></label> ';
	result = result + '			          <div class="col-md-9 no_pad chosenWidth"> ';
	result = result + '			            <select class="form-control chosen-select" id="rescheduleTimeZone" name="rescheduleTimeZone" onclick="validateTimeZone();" field-name="Timezone" data-validation="required">  ';
	result = result + '			            </select> ';
	result = result + '			          </div> ';
	result = result + '				<div class="clearfix"></div> ';
	result = result + '			        </div> ';
	 
	result = result + '			<div class="form-group Pmess">';
	result = result + '			          <label class="col-md-3 no_pad">Comment <b class="text-danger">*</b></label> ';
	result = result + '					  <div class="col-md-9 no_pad custom-textarea">';
	result = result + '						  <textarea class="form-control reSchtextarea" placeholder="Enter Message"  id="reschedulComment" name="reschedulComment" data-validation="required  validate_length length2-500" field-name="Comment"></textarea> ';
	result = result + '					  </div>  ';
	result = result + '			</div>';
	result = result + '			<div class="ne-ch">  ';
	result = result + '				<label>  ';
	result = result + '				  <input type="checkbox" name="notificationToApplicant" value="Y" checked="checked" > ';
	result = result + '				  <b>Notify to Applicants</b></label>    ';
	if(parentRequirement == 'Y'){
		result = result + '				<label><input type="checkbox" name="notificationToSpoc" value="Y" checked="checked"><b>Notify to Vendors</b></label>';
	}
	result = result + '<label><input type="checkbox" name="notificationToOthers" id="notificationToOthers" onClick="getNotifyToOtersVal();showOthersDiv();"><b>Notify to Others</b></label>';
	//result = result + '				<label id="notMail"><input type="checkbox" onClick="getNotifyToOtersVal();showOthersDiv();"><b>Notify to Others</b></label>';
	result = result + '				<span class="Pmess" id="notifyOthersDivFeedBack" style="display: none;">';
	result = result + '			    <input type="hidden" class="form-control input-sm" placeholder="mail id" id="notificationToOthersEmailsFeedBack" name="notificationToOthersEmails"> ';
	result = result + '<ul id="mailsFeedBack" class="form-control" ></ul>';
	result = result + '				</span>  ';
	result = result + '			  </div>  ';
	result = result + '						<div class="clearfix"></div> ';
	result = result + '			<div class="clearfix mt5"> ';
	result = result + '				<div class="pull-right"> ';
	result = result + '				  <button type="button" id="save_rescheduleIntervie" class="btn btn-sm btn-primary" onclick="rescheduleInterviewUpdate('+submissionId+')">Reschedule</button> ';
	result = result + '				  <button type="button" class="btn btn-sm btn-danger" onclick="rescheduleInterClose();">Cancel</button> ';
	result = result + '				</div> ';
	result = result + '			  </div> ';
	
	result = result + '				  </div>   ';
	result = result + '			  </div> ';
	result = result	+ ' </form>';

 TimezonesRescheduleList(timezone);
	$("#rescheduleInterViewdiv").empty();
    $("#rescheduleInterViewdiv").append(result);

    
	$("#feedBackStartTime").timepicker({
		'showDuration': true,
		'disableTextInput': true
	}).on('changeTime', function() {
	    $("#feedBackEndTime").timepicker('option', 'minTime', $(this).val());
	});
	
	$("#feedBackEndTime").timepicker({
		'showDuration': true,
		 'maxTime': '23:30:00',
		'disableTextInput': true
	}).on('changeTime', function() {
	    $("#feedBackStartTime").timepicker('option', 'maxTime', $(this).val());
	}); 

	
var config = {
		  '.chosen-select'           : {},
		  '.chosen-select-deselect'  : {allow_single_deselect:true},
		  '.chosen-select-no-single' : {disable_search_threshold:10},
		  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
		  '.chosen-select-width'     : {width:"95%"}
		}
$('.chosen-select').each(function() {
	var $this = $(this);
	$this.next().css({'width':'100%'});
})
$('.chosen-container').css({'width': '100%'});
$("#rescheduleTimeZone").chosen();
$("#rescheduleTimeZone").trigger('chosen:update');

var locationDate ='${SourceLeadWeb_dateFormat}'; 
var finalDateFormatRescheduleDate = '';
if(locationDate.match("yyyy-MM-dd")){
	finalDateFormatRescheduleDate = 'yy-mm-dd';
}if(locationDate.match("dd-MM-yyyy")){
	finalDateFormatRescheduleDate = 'dd-mm-yy'; 
}if(locationDate.match("MM-dd-yyyy")){
	finalDateFormatRescheduleDate = 'mm-dd-yy'; 
}if(locationDate.match("yyyy-dd-MM")){
	finalDateFormatRescheduleDate = 'yy-dd-mm'; 
}



$('#rescheduleDate').datepicker({
	dateFormat : finalDateFormatRescheduleDate,
	 changeMonth: true,
	changeYear: true, 
	/* minDate : 0, */
	onSelect : function(selected) {
		var dt = new Date(selected);
		dt.setDate(dt.getDate() + 1);
		addValidClass();
	}
	});
	
}
function rescheduleInterClose(){
	$(".reSchPop").removeClass("reSchPopActive");
}
</script>

<script type="text/javascript"> 
	function addValidClass(){
		 var StartDate = document.getElementById("rescheduleDate").value;
		 if(StartDate){
			 $('#rescheduleDateDiv').find('p.jquery_form_error_message').remove(); 
		 }
}
</script>

 <script>
 function rescheduleInterviewUpdate(submissionId){
	 	if($("#notificationToOthers").prop('checked') == true){
	 		validateOtherMail();
		}
		
		if ($('#rescheduleInterview_form').validate(false, validationSettings)) {
			var frm = $('#rescheduleInterview_form').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, "");
			//It will escape all the special characters
			var jsonData = encodeURIComponent(con);
			var atsUrl ='${SourceLeadWeb_atsUrl}';
			$("#save_rescheduleIntervie").attr("disabled", "disabled");
	        $("#save_rescheduleIntervie" ).addClass('button').val('Processing..');
			$.ajax({
						type : "POST",
						url: atsUrl+"/updateRescheduleInterview?"+tokenParamName+"="+tokenValue+"&jsonData="+jsonData,
						success : function(result) {
							getInternalInterviewsList(submissionId);
						},
					});
			return true;
		} else {
			return false;
		}
	 
 }
 $('body').on('blur', '#rescheduleInterview_form', function() {
		$("#rescheduleInterview_form").showHelpOnFocus();
		$("#rescheduleInterview_form").validateOnBlur(false, validationSettings);
	});
 </script>
 <script>
   function validateTimeZone(){
	var timezoneVal = $("#rescheduleTimeZone").val();
	if(timezoneVal !=null && timezoneVal != '' && timezoneVal != 'undefined'){
		$('#rescheduleTimeZone_chosen').next("p").remove();
	}
	else{
		$('#rescheduleTimeZone_chosen').next("p").remove();
		$('#rescheduleTimeZone_chosen').after('<p class="jquery_form_error_message">Timezone is Required</p>');
	}
}
</script>
 
<script>
function getNotifyToOtersVal(){
	var checked = $("input[Id=notificationToOthers]:checked").val();
    if(checked == 'on') {
        $("#notificationToOthers").val('Y');
        $("#notifyOthersDivFeedBack").find('p.jquery_form_error_message').remove();
    }else{
        $("#notificationToOthers").val('N');
        $("#notifyOthersDivFeedBack").find('p.jquery_form_error_message').remove();
        $("#add_interview").removeAttr("disabled");
    }
}

</script>
<script>
function showOthersDiv(){
	var isNofityToOthers = $("input[Id=notificationToOthers]:checked").val();
	if (isNofityToOthers || isNofityToOthers == 'on') {
		document.getElementById("notifyOthersDivFeedBack").style.display = 'block';
		 $('#mailsFeedBack').tagit({
		       singleField: true,
		       singleFieldNode: $('#notificationToOthersEmailsFeedBack')
		       ,placeholderText:"Enter emails"
		       ,allowDuplicates: false
		    	,afterTagAdded: function(evt, ui){
			              checkEmails();
			              return false;
			    }
		       ,afterTagRemoved: function(evt, ui){
	              checkEmails();
	              return false;
	         }
		   });
	}else {
		document.getElementById("notifyOthersDivFeedBack").style.display = 'none';
		$('#mailsFeedBack').tagit('removeAll');
	}
}
</script>
<script language="javascript">
function checkEmail(email) {
   var regExp = /^.+@.+\..{2,3}$/
   return regExp.test(email);
}

function checkEmails(){
	var emails = document.getElementById("notificationToOthersEmailsFeedBack").value;
	var emailArray = emails.split(",");
	var invEmails = "";
	for(i = 0; i <= (emailArray.length - 1); i++){
		if(checkEmail(emailArray[i])){
			//Do what ever with the email.
		}else{
			invEmails += "'" +emailArray[i]+ "'"+ "\n";
		}
	}
	
	
	if(invEmails != "" && invEmails.length != 3 && invEmails != null && invEmails !="undefined" && invEmails !="\n"){
	      $("#add_interview").attr("disabled", "disabled");
		  $('#notifyOthersDivFeedBack').find('p.jquery_form_error_message').remove();
		  $("input#notificationToOthersEmailsFeedBack").after("<p class='jquery_form_error_message'>"  +invEmails+" Invalid emails</span>");
		  document.getElementById("notificationToOthersEmailsFeedBack").setAttribute('record-exist','yes');
		  document.getElementById("notificationToOthersEmailsFeedBack").setAttribute('record-exist-errorMsg',invEmails+' Invalid emails'); 
	}else{
		$('input#notificationToOthersEmailsFeedBack').removeAttr( "record-exist record-exist-errormsg");
		$('#notifyOthersDivFeedBack').find('p.jquery_form_error_message').remove(); 
        $("#add_interview").removeAttr("disabled");
	}
}
</script> 
<script>
function validateOtherMail(){
var dataNotify =document.getElementById("notificationToOthersEmailsFeedBack").value;
var isNofityToOthers = document.getElementById("notificationToOthers").value;
if((dataNotify == '' || dataNotify == 0)){
	  $("#add_interview").attr("disabled", "disabled");
	  $('#notifyOthersDivFeedBack').find('p.jquery_form_error_message').remove();
	  $("input#notificationToOthersEmailsFeedBack").after("<p class='jquery_form_error_message'>Please enter Email</span>");
	  document.getElementById("notificationToOthersEmailsFeedBack").setAttribute('record-exist','yes');
	  document.getElementById("notificationToOthersEmailsFeedBack").setAttribute('record-exist-errorMsg','Please enter Email'); 
}else{
	$('input#notificationToOthersEmailsFeedBack').removeAttr( "record-exist record-exist-errormsg");
	$('#notifyOthersDivFeedBack').find('p.jquery_form_error_message').remove(); 
    $("#add_interview").removeAttr("disabled");
}
}
</script>
<script>
function TimezonesRescheduleList(timezone){
	var atsUrl ='${SourceLeadWeb_atsUrl}';
	 $.ajax({
	    	type: "POST",
	    	url : atsUrl+"/getTimezonesList?"+tokenParamName+"="+tokenValue+"", 
	        success: function(data) {
	        var json = JSON.parse(data);
	        var output = "";
	        output = output + "<option value=''>--Select--</option>"; 
	        	for (var i=0; i<json.length; i++){
	        		var opt = json[i];
	        		if(opt.timeZoneId == timezone){
	        			output = output + "<option value='" +opt.id+ "' selected>"+opt.timeZoneId+"</option>";
	        		}else{
	        			output = output + "<option value='" +opt.id+ "'>"+opt.timeZoneId+"</option>";
	        		}
	        		
				}
	        	$("#rescheduleTimeZone").empty();
	        	$("#rescheduleTimeZone").append(output);
	        	$("#rescheduleTimeZone").trigger("chosen:updated");
	        }
	 });
}
</script>
 
 <script type="text/javascript">
	function validateEndTime() {
		 var allStartTimes = [];
		 var allEndTimes = [];
		 $("#rescheduleTimeDiv").find('p.jquery_form_error_message').remove();
			var startTime = $("#feedBackStartTime").val();
			 var endTime = $("#feedBackEndTime").val();
			if(endTime != null && endTime != '' && endTime != 'undefined'){
				if(startTime == endTime){
					$("#rescheduleTimeDiv").find('.text-danger').remove();
			        $("#add_interview").removeAttr("disabled");
			        $("#add_interview").attr("disabled", "disabled");
			        $("#rescheduleTimeDiv").append("<small class='text-danger'>End Time should greater than Start Time</small>");
			    }else{
			         $("#rescheduleTimeDiv").find('.text-danger').remove();
			         $("#add_interview").removeAttr("disabled");
			   	}
			}
			var startTime1 = $("#feedBackStartTime").val();
			 var endTime1 = $("#feedBackEndTime").val();
			if(endTime1 != null && endTime1 != '' && endTime1 != 'undefined'){
				if(startTime1 == endTime1){
					 $("#add_interview").attr("disabled", "disabled");
				}
			}
		
	}
</script>
 
<script>
		
/* $("#cli11").click(function(){
	alert("hello"); 
	$(".reSchPop").toggleClass("reSchPopActive");
});		
$("#CPop").click(function(){
	$(".reSchPop").removeClass("reSchPopActive");
});			
		 */
$("#notMail").click(function(){
	$(this).next(".Pmess").slideToggle(300);
});	
	</script>	
	
<!-- this script using for interview feedback Ends -->
<script type="text/javascript">
function reActivateApplicant(submissionId,candidateId,requirementId,type){
	$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	$("#reactiveApplicantBtnDiv").hide();
	var result = "";
	var atsUrl ='${SourceLeadWeb_atsUrl}';
	//$("#reactiveApplicant").attr("disabled","disabled");
// 	var message = confirm("You are choosing to ReActivate the Applicant");
// 	if (message == true) {
		
		$.ajax({
	        type : "POST",
	         url: atsUrl+"/reActivateRejectedApplicant?"+tokenParamName+"="+tokenValue+"&submissionId="+submissionId+"&type="+type,        
	         success :function(data) {
	               if(data != null && data != 'undefined' && data != ""){
	            	   getSubmissionDetailsSideBar(submissionId,candidateId,requirementId);
	            	   pipelineList(requirementId);
	                } 
	                 },
	              }); 
		
// 	}
	
}
</script>


<script>
function feedback_close() {
	$(".bg_mask, #viewfeedbackdiv").fadeOut(200);
	$("#mask_inner").fadeOut();
	$('.feedbackdivPostion').fadeOut();
	$("#noviewfeedbackdiv").fadeOut(200);
}
</script>

<script type="text/javascript">
function pipelineList(requirementId){
	var submissionType = "all";
	$("#duplicataplctli").removeClass("pipeline-active");
	$("#requestSentli").removeClass("pipeline-active");
	$("#shortListli").removeClass("pipeline-active");
	$("#submitToClientli").removeClass("pipeline-active");
	$("#interviewli").removeClass("pipeline-active");
	$("#offerMadeli").removeClass("pipeline-active");
	$("#joinedli").removeClass("pipeline-active");
	$("#rejectedli").removeClass("pipeline-active");
	$("#applicantli").addClass("pipeline-active");
// 	filterReq(requirementId,submissionType);
}
</script>
<script>
function proposeTime(interviewId,submissionId){
	$(".proposeTimePop").toggleClass("proposeTimePopActive");
	$(".reSchPop").removeClass("reSchPopActive");
	
	var result ='';
	var parentRequirement = '${parentRequirement}';
	result = result	+ ' <form id="proposeTime_form"  name="proposeTime_form" method="post">';
	result = result + '			  <div class="reSchPopInn">';
	result = result + '			   <input type="hidden" id="requirementInterviewId" name="requirementInterviewId" value='+interviewId+' >';
	result = result + '				  <h3>Propose New Time</h3>';
	result = result + '				  <div class="reSchPopCta">';	
	result = result + '         <input class="maxRowNum" type="hidden" name="proposeTimeMaxRowNumber'+interviewId+'"  id="proposeTimeMaxRowNumber'+interviewId+'" value="1" />';
	result = result + '	         <input type="hidden" class="newMaxRowNum" name="proposeNewTimeRowsNum'+interviewId+'"  id="proposeNewTimeRowsNum'+interviewId+'" value="1" />';
	result = result + '			<table width="100%" class="table table-responsive table-bordered table-hover" id="proposeTimeTbl'+interviewId+'">';
	result = result + '			<tbody>';
	result = result + '				<tr id="divProposeTimeTsRow0">';
	result = result + '					<td>';
	result = result + '					  <div id="errMsg0">';
	result = result + '					  		<input type="text" class="form-control calendar input-sm" id="interviewDate0" name="interviewDate0" value="" placeholder="Interview Date" readonly data-validation="required" field-name="Interview Date">';
	result = result + '					  </div>';
	result = result + '					</td>';
	result = result + '					<td>';
	result = result + '						<div>';
	result = result + '							  <input type="text" class="form-control timeicon timepicker input-sm" id="time0" name="time0" placeholder="Select Time" data-validation="required" field-name="Time">';
	result = result + '						</div>		';								
	result = result + '					</td>';
	result = result + '					<td>';
	result = result + '						<div>';
	result = result + '					  		<button type="button" class="btn btn-primary btn-sm" onClick="addProposeTime('+interviewId+');"><i class="fa fa-plus"></i></button>';
	result = result + '				  		</div>';
	result = result + '					</td>';
	result = result + '				</tr>';
	result = result + '			</tbody>';
	result = result + '			</table>';
						
	
	result = result + '			<div class="clearfix mt"> ';
	result = result + '				<div class="pull-right"> ';
	result = result + '				  <button type="button" class="btn btn-sm btn-primary" id="save_proposeTime" onclick="saveProposeTime('+submissionId+')">Propose Time</button> ';
	result = result + '				  <button type="button" class="btn btn-sm btn-danger" onclick="timePopUp();">Cancel</button> ';
	result = result + '				</div> ';
	result = result + '			  </div> ';
	
	result = result + '				  </div>   ';
	result = result + '			  </div> ';
	result = result	+ ' </form>';

	$("#proposeTimediv"+interviewId+"").empty();
    $("#proposeTimediv"+interviewId+"").append(result);

    
	$("#time0").timepicker({
		'showDuration': true,
		'disableTextInput': true
	})
	
	
	 var locationDate ='${SourceLeadWeb_dateFormat}'; 
	  var finalDateFormatInterviewDate = '';
	  if(locationDate.match("yyyy-MM-dd")){
		  finalDateFormatInterviewDate = 'yy-mm-dd';
	  }if(locationDate.match("dd-MM-yyyy")){
		  finalDateFormatInterviewDate = 'dd-mm-yy'; 
	  }if(locationDate.match("MM-dd-yyyy")){
		  finalDateFormatInterviewDate = 'mm-dd-yy'; 
	  }if(locationDate.match("yyyy-dd-MM")){
		  finalDateFormatInterviewDate = 'yy-dd-mm'; 
	  }
	  
	
$('#interviewDate0').datepicker({
	dateFormat : finalDateFormatInterviewDate,
	changeMonth: true,
	changeYear: true, 
	minDate:0,
	onSelect : function(selected) {
			errInvoiceDate("0");
	}
});
	
}
</script>
<script>
function errInvoiceDate(rowId){
	$('#errMsg'+rowId+'').find('p.jquery_form_error_message').remove(); 
}
</script>
<script>
function timePopUp(){
	$(".proposeTimePop").removeClass("proposeTimePopActive");
}
</script>

<script>
  function addProposeTime(interviewId){
	  var checkMaxTsRowNum = $("#proposeTimeMaxRowNumber"+interviewId+"").val();
		var tbl = document.getElementById("proposeTimeTbl"+interviewId+"");
		var maxRowNum = parseInt($("input[name = 'proposeTimeMaxRowNumber"+interviewId+"']").val());
		var row = tbl.insertRow(maxRowNum);
		row.setAttribute("id", "divProposeTimeRow" + maxRowNum +interviewId);
		
		var rowNum = parseInt($("input[name = 'proposeNewTimeRowsNum"+interviewId+"']").val());
		if(rowNum >= 5){
			var result = "";
     		result = result +"	<div class='project_inn_mask'></div>";
     		result = result +"	   <div id='invoice_pop' class='remove_user_pop remove_user_pop_a'>";
     		result = result +"		<p>Only can Add 5 Rows</p>";
     		result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
     		result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='cancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
     		result = result +"	</div>";
     		$("#popUp").append(result);
     		$("#invoice_pop").slideDown(200);
     		$(".project_inn_mask").fadeIn(200);
		}else{
			$("input[name = 'proposeTimeMaxRowNumber"+interviewId+"']").val(maxRowNum + 1);
			$("input[name = 'proposeNewTimeRowsNum"+interviewId+"']").val(rowNum + 1);
			var text = "";
			var cell0 = row.insertCell(0);
			text = text+'<div id="errMsg'+maxRowNum+'">';
			text = text+'  <input type="text" class="form-control calendar" id="interviewDate'+maxRowNum+'" name="interviewDate'+maxRowNum+'" value="" placeholder="Interview Date"  readonly data-validation="required" field-name="Invoice Date">';
			text = text+'</div>';
			cell0.innerHTML = text;
			
			$('#interviewDate'+maxRowNum+'').datepicker({
				dateFormat : 'dd-mm-yy',
				changeMonth: true,
				changeYear: true, 
				minDate:0,
				onSelect : function(selected) {
						errInvoiceDate(maxRowNum);
				}
			});
			
			var text = "";
		    var cell1 = row.insertCell(1);
			text = text+'<div>';
			text = text+'  <input type="text" class="form-control timeicon timepicker input-sm ui-timepicker-input" id="time'+maxRowNum+'" name="time'+maxRowNum+'" placeholder="Select Time" data-validation="required" field-name="Time">';
			text = text+'</div>	';
			cell1.innerHTML = text;
			
			var text = "";
		    var cell2 = row.insertCell(2);
			text = text+'<div>';
			text = text+'<button type="button" class="btn btn-danger btn-sm" id="remove"  onclick="removeRow('+maxRowNum+','+interviewId+')"><i class="fa fa-remove"></i></button>';
			text = text+'</div>';
			cell2.innerHTML = text;
		}
		
		  $("#time"+maxRowNum+"").timepicker({
				'showDuration': true,
				'disableTextInput': true
			}); 
		 
  }
  </script>
  <script>
  function removeRow(rowId,interviewId){
	  document.getElementById('divProposeTimeRow' + rowId+interviewId).style.display = 'none';
	  
	  var rowNum = parseInt($("input[name = 'proposeNewTimeRowsNum"+interviewId+"']").val());
	  $("input[name = 'proposeNewTimeRowsNum"+interviewId+"']").val(rowNum - 1);
		
		var r = document.getElementById('proposeTimeTbl'+interviewId+'').rows[rowId];
		var count = 2 * (r.cells.length);
		for (i = 0; i <= count; i++) {
			r.deleteCell(0);
		}
  }
  
  </script>
  
   <script>
function saveProposeTime(submissionId){
	if ($('#proposeTime_form').validate(false, validationSettings)){ 
		var atsUrl ='${SourceLeadWeb_atsUrl}';
		var frm = $('#proposeTime_form').serializeFormJSON();
		var con = JSON.stringify(frm);
		con = con.replace(/[{}]/g, ""); 
		//It will escape all the special characters
		$("#save_proposeTime").attr("disabled", "disabled");
        $("#save_proposeTime" ).addClass('button').val('Processing..');
	    var jsonData = encodeURIComponent(con);
		$.ajax({
					type : "POST",
					url : atsUrl+"/saveProposeTime?"+tokenParamName+"="+tokenValue+"&jsonData="+jsonData+"&submissionId"+submissionId,
					success : function(result) {
						getInternalInterviewsList(submissionId);
					},
				});
		return true;
	 }else{ 
			return false;
	 } 
}	 
$('body').on('blur', '#proposeTime_form', function() {
	$("#proposeTime_form").showHelpOnFocus();
	$("#proposeTime_form").validateOnBlur(false, validationSettings);  
});
</script>

<script>
function viewProposeTime(interviewId,submissionId){
	$(".proposeTimePop").toggleClass("proposeTimePopActive");
	$(".reSchPop").removeClass("reSchPopActive");
	var atsUrl ='${SourceLeadWeb_atsUrl}';
	var locationId = '${SourceLeadWeb_orgLocationId}';
	$.ajax({
		type : "POST",
		url : atsUrl+"/getProposeTime?"+tokenParamName+"="+tokenValue+"&interviewId="+interviewId+"&locationId="+locationId,
		success : function(data) {
			var json = JSON.parse(data);
	        var result = "";
// 	        result = result + '<button type="button" class="" onClick="naidu(this);">&times;</button>';
			result = result	+ ' <form id="acceptProposeTime_form"  name="acceptProposeTime_form" method="post">';
			result = result + '			   <input type="hidden" id="requirementInterviewId" name="requirementInterviewId" value='+interviewId+' >';
	        result = result + '			  <div class="reSchPopInn">';
	        result = result + '				  <h3>Propose New Time</h3>';
    		result = result + '				  <div class="reSchPopCta">';	
    		result = result + '			<table width="100%" class="table table-responsive table-bordered	">';
    		result = result + '			<tbody>';
        	for (var i=0; i<json.length; i++){
        		var report = json[i];
        		
        		result = result + '				<tr>';
        		result = result + '					<td>';
        		result = result + '					  <div id="errMsg0">';
        		result = result + '					  		<input type="text" class="form-control calendar input-sm" id="proposeDate" name="proposeDate" value="'+report.proposeDateStr+'" readonly>';
        		result = result + '					  </div>';
        		result = result + '					</td>';
        		result = result + '					<td>';
        		result = result + '						<div>';
        		result = result + '							  <input type="text" class="form-control timeicon timepicker input-sm" id="proposeTime" name="proposeTime" value="'+report.proposeTime+'" readonly>';
        		result = result + '						</div>		';								
        		result = result + '					</td>';
        		result = result + '					<td>';
        		result = result + '						<div class="mt5">';
        		if(report.interviewTimeStatus == "Accepted"){
        			result = result +' <font color="orange" class="brder-int">Accepted</font>';
        		}else{
        			result = result + '                         <a href="javascript:void(0)"  onclick="acceptProposeTime(\''+report.id+'\',\''+report.proposeDateStr+'\',\''+report.proposeTime+'\',\''+submissionId+'\');"><i class="fa fa-check text-success"></i></a>';
        		}
        		
        		result = result + '				  		</div>';
        		result = result + '					</td>';
        		result = result + '				</tr>';
        		
        	}
        	result = result + '			</tbody>';
    		result = result + '			</table>';
    		result = result + '			<div class="clearfix mt"> ';
    		result = result + '				<div class="pull-right"> ';
    		result = result + '				  <button type="button" class="btn btn-sm btn-danger" onclick="timePopUp();">Cancel</button> ';
    		result = result + '				</div> ';
    		result = result + '				  </div>   ';
    		result = result + '			  </div> ';
    		result = result	+ ' </form>';
        	$("#viewProposeTimediv"+interviewId+"").empty();
    	    $("#viewProposeTimediv"+interviewId+"").append(result);
		},
	});
}
</script>
<script>
function acceptProposeTime(id,proposeDate,proposeTime,submissionId){
	var atsUrl ='${SourceLeadWeb_atsUrl}';
	if ($('#acceptProposeTime_form').validate(false, validationSettings)){ 
		var atsUrl ='${SourceLeadWeb_atsUrl}';
		var frm = $('#acceptProposeTime_form').serializeFormJSON();
		var con = JSON.stringify(frm);
		con = con.replace(/[{}]/g, ""); 
		//It will escape all the special characters
	    var jsonData = encodeURIComponent(con);
		$.ajax({
					type : "POST",
					url : atsUrl+"/acceptProposeTime?"+tokenParamName+"="+tokenValue+"&jsonData="+jsonData+"&proposeTimeId="+id+"&proposeDate="+proposeDate+"&proposeTime="+proposeTime,
					success : function(result) {
						getInternalInterviewsList(submissionId);
					},
				});
		return true;
	 }else{ 
			return false;
	 } 
}

</script>

<script>
function viewOfferLetter(offerUUID){
	var sourceleadUrl ='${sourceleadUrl}';
	window.open(sourceleadUrl+'/candidateOfferLetterTempleateView?offerUUID='+offerUUID+'','_newtab');
}

</script>
