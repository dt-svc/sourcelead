 <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

 <jsp:include page="requirementAssignedTo.jsp"></jsp:include>
 
 <script>
 vmsUrl = '${SourceLeadWeb_vmsUrl}'
 </script>
 <script>
		$(document).ready(function() {
			 <c:forEach var="section" items="${sourceleadSectionList}" varStatus="sectionOrder">
			  var sectionId = '${section.id}';
			  getCustomDependentField(sectionId);
			 </c:forEach>
		});
 </script> 
 
 
<div class="profile_details requirement_details clearfix reqrementsummary">
	 <a href="#" onClick="openTopDiv();">
	    <h4 id="id1">
			<span title="${requirement.jobTitle} ${!empty requirement.jobId ? ' - ' : '' } ${!empty requirement.jobId ? requirement.jobId : ''}${!empty requirement.jobType ? ' - ' : '' }${!empty requirement.jobType  ?  (json.jobType == 'Other' ? requirement.otherJobType : requirement.jobType ) : ''}">${requirement.jobTitle} ${!empty requirement.jobId ? ' - ' : '' } ${!empty requirement.jobId ? requirement.jobId : ''}${!empty requirement.jobType ? ' - ' : '' }${!empty requirement.jobType  ?  (json.jobType == 'Other' ? requirement.otherJobType : requirement.jobType ) : ''}</span><i></i>
		</h4>
	</a>
	
	<div class="hide-row">
		<div class="col-md-12">
			<div class="row">
				<div class="col-md-3">
					<div class="company-logo jobComLogo">
						<div class="imageParent">
							<img onerror="predefineOrgImage(this);" data-id="orgLogoImage" src="${requirement.orgLocationId.org.fileUrl}"
								alt="Company" title="Company Logo" class="img-responsive">
						</div>
				</div>
				</div>
				<div class="col-md-9">
					<div class="canDetails clearfix">
						<div class="row">
							<div class="col-md-5">
								<ul class="list-unstyled can-perdt">
									<li><i class="fa fa-black-tie" title="Experience"></i><span>${!empty requirement.minExp  ? requirement.minExp :' '}${!empty requirement.minExp && !empty requirement.maxExp ? '-':' '}${!empty requirement.maxExp  ? requirement.maxExp :''}${empty requirement.minExp && empty requirement.maxExp ? ' --- ':' years '}</span></li>
									<li>
									  <i class="fa ${!empty requirement.currencyCode && requirement.currencyCode == 'INR'  ? 'fa-rupee' :!empty requirement.currencyCode && requirement.currencyCode == ('USD' || 'AUD' || 'SGD' ) ? 'fa-dollar': !empty requirement.currencyCode && requirement.currencyCode == 'EUR' ? 'fa-eur' : !empty requirement.currencyCode && requirement.currencyCode == 'GBP' ? 'fa-gbp' : 'fa-rupee' } " title="Job Rate"></i>
								      <span>${!empty requirement.rate ? requirement.rate : '---'}${!empty requirement.maxRate ? ' - ':''}${!empty requirement.maxRate ? requirement.maxRate : ''}  ${!empty requirement.currencyCode ? requirement.currencyCode : ''}${!empty requirement.rateType ? ' / ':''}${!empty requirement.rateType ? requirement.rateType : ''} </span>
									</li>
									<%-- <li><i class="fa fa-map-marker" title="Location"></i>
									<c:forEach var ="reqLocs" items="${locationsList}">
									 <span title="${reqLocs.location}">${reqLocs.location}</span>
									</c:forEach>
									</li> --%>
									
									<li class="clearfix"><i class="fa fa-map-marker pull-left mt5" title="Locations"></i>
									<ul class="list-unstyled joblocations-public col-md-10">
									<c:forEach var ="reqLocs" items="${locationsList}">
									 <li title="${reqLocs.location}">${reqLocs.location}</li>
									</c:forEach>
									</ul>
									</li>
									
									
									<li><i class="fa fa-cab" title="Travel allowance"></i><span title="${!empty requirement.travelAllowance ? requirement.travelAllowance : ''}">${!empty requirement.travelAllowance ? requirement.travelAllowance : ''}</span></li>
									<li><i class="fa fa-users pull-left mt5" title="Job Fair(s)"></i>
										<ul class="list-unstyled joblocations-public col-md-10">
											<c:choose>
												<c:when test="${requirement.jobFairList.size() > 0}">
													<c:forEach var ="jobFairs" items="${requirement.jobFairList}">
													 	<li title="${jobFairs.name}">${jobFairs.name}</li>
													</c:forEach>												
												</c:when>
												<c:otherwise>
													---
												</c:otherwise>
											</c:choose>
										</ul>
									</li>
								</ul>
							</div>
							<div class="col-md-7">
								<ul class="list-unstyled can-perdt">
									<li><i class="fa fa-user" title="Manager"></i><span id="canName">${!empty requirement.manager ? requirement.manager : '---'}</span></li>
									<li><i class="fa fa-clock-o" title="Job Posted Date"></i><span>${!empty requirement.requirementCreatedDateToString ? requirement.requirementCreatedDateToString : '---'}</span></li>
									<li><i class="fa fa-clock-o" title="Job Closed Date"></i><span>${!empty requirement.closedDateStringFormat ? requirement.closedDateStringFormat : '---'}</span></li>
									<li><i class="fa fa-clock-o" title="ProjectDuration"></i><span>${!empty requirement.projectDurationYears ? requirement.projectDurationYears : ''}${!empty requirement.projectDurationYears ? ' Years ': ''}${!empty requirement.projectDurationMonths ? requirement.projectDurationMonths : ''}${!empty requirement.projectDurationMonths ? ' Months ': ''}${empty requirement.projectDurationYears && empty requirement.projectDurationMonths ? ' --- ':''}</span>
									<li><i class="fa fa-flag" title="Priority"></i><span>${!empty requirement.priority ? requirement.priority : ' --- '}</span>
									<li><i class="fa fa-bell" title="Notice Period"></i><span>${!empty requirement.noticePeriod ? requirement.noticePeriod : ' --- '}</span></li>
								</ul>
							</div>
						</div>
					</div>
					<c:set var="skills" value="${fn:split(requirement.skills, ',')}" />
					<div class="pro-detials">
						<div class="professinal-details">
							<ul class="list-inline list-unstyled">
							 <c:forEach var ="skill" items="${skills}" begin="0"  end = "4">
						        <li>${skill}</li>
						     </c:forEach>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="proj_close"></div>
<div class="project_side_wrap_inn_tabs">

	 <%-- var type = '${type}'; --%>
	<!--  isDetailsTabActive
	 isApplicantsTabActive -->
	<ul class="project-tabs-menu">
		<li class="${empty type ? 'current' : ''}" id="detailsTab"><a href="#tab-1" onclick="sideBar('${requirement.id}');">Details</a></li>
		<c:if test="${requirement.requirementSubmissionStatus != 'DRAFT' && requirement.requirementSubmissionStatus != 'SUBMITTED' && requirement.requirementSubmissionStatus != 'REJECTED'}">
			<sec:authorize access="hasAnyRole('ROLE_ADD_APPLICANT','ROLE_VIEW_APPLICANT','ROLE_ADD_INTERVIEW','ROLE_VIEW_INTERVIEW','ROLE_VIEW_CANCEL_INTERVIEW','ROLE_RATE_NEGOTIATION','ROLE_SHORTLIST','ROLE_SUBMIT_TO_CLIENT','ROLE_CREAE_OFFER','ROLE_VIEW_OFFER','ROLE_VIEW_ACCEPT_OFFER','ROLE_VIEW_REJECT_OFFER')">
			    <li class="${!empty type ? 'current' : ''}" name="applicantNotification" id="${requirement.requirementSubmissionStatus == 'CLOSED' && requirement.isreceivingApplicants == 'Y' ? 'applicantsTab':''}"><a  href="#tab-2" onclick="requirementPipeline('${requirement.id}','all');requirementWorkflowStatusBar('${requirement.id}');">Pipeline</a></li>
			</sec:authorize>
			<sec:authorize access="hasAnyRole('ROLE_RECOMMENDATIONS')">
			    <li id="${requirement.requirementSubmissionStatus == 'CLOSED' && requirement.isreceivingApplicants == 'Y' ? 'recommendationsTab':''}"><a  href="#tab-3" onclick="requirementRecommendations('${requirement.id}', '${requirement.requirementSubmissionStatus}');">Recommendations</a></li>
			</sec:authorize>
			<sec:authorize access="hasAnyRole('ROLE_SHARE_PROFILE','ROLE_ADD_PROFILE')">
			 <li id=""><a  href="#tab-5" onclick="globalDataRecommendations();">Global Data Recommendations</a></li>
			 </sec:authorize>
			<li><a  href="#tab-4" onclick="requirementNotes('${requirement.id}');">Notes</a></li>
		</c:if>
	</ul>
	<div class="clearfix"></div>
	<div class="project_tabs">
      <div id="tab-1" class="project_tabs_inn">
        <div id="details-div" class="require_wrap_details paddingTop">
            	<c:if test="${requirement.requirementSubmissionStatus == 'REJECTED' }">
            	    <div class="anchor-inline">
            	     <h5>Rejected Reason:</h5> 
	            	    <div class="rejected_Reason"> 
		            	    <div class="mb">${!empty requirement.rejectReasonForRequirement ? requirement.rejectReasonForRequirement : ' No DATA '}</div>
	            	    </div> 
            	    </div> 
          	   </c:if>
			   <div id="rejectRequirementAlert" ></div>   
		       <div class="clearfix"> 
		           <div class="pull-right mt10">
		           
						<c:set var="contains" value="false"></c:set>
						<c:set var="isAllow" value="false"></c:set>
						<c:set var="approver" value="false"></c:set>

						<c:if test="${requirement.requirementSubmissionStatus != ''}">
							<div class="pull-left mr">
							
								<c:if test="${(requirement.level1ApproverUserId != null && requirement.level1ApproverUserId != '' && requirement.level1ApproverUserId != 'undefined') || (requirement.level2ApproverUserId != null && requirement.level2ApproverUserId !='' && requirement.level2ApproverUserId != 'undefined')}">
									<c:if test="${requirement.level2ApproverUserId != null && requirement.level2ApproverUserId !=''}">
											${SourceLeadWeb_userId} ${requirement.level2ApproverUserId}
										<c:choose>
											<c:when test="${SourceLeadWeb_userId == requirement.level2ApproverUserId}">
												<c:set var="approver" value="true"></c:set>
											</c:when>
											<c:otherwise>
												<c:forEach items="${requirement.level1ApproverUserId}" var="a">
													<c:if test="${SourceLeadWeb_userId == requirement.level1ApproverUserId[a]}">
														<c:set var="approver" value="true"></c:set>
													</c:if>
												</c:forEach>
											</c:otherwise>
										</c:choose>
									</c:if>
								</c:if>
								<c:if test="${requirement.managerUserId != null && requirement.managerUserId != ''}">
									<c:if test="${requirement.managerUserId.id == SourceLeadWeb_userId}">
										<c:set var="contains" value="true"></c:set>
									</c:if>
								</c:if>
								<c:if test="${approver == true || contains == true || SourceLeadWeb_loginUserRole == 'ROLE_ADMIN' || SourceLeadWeb_loginUserRole == 'ROLE_SOURCELEAD_ADMIN' || requirement.isLoginUserAsApprover == true}">
									<c:set var="isAllow" value="true"></c:set>
								</c:if>
								<select class="sel" name="changeStatusId${requirement.id}" ${isAllow ? "" : "disabled"} onchange="changeStatus(${requirement.id});" id="changeStatusId${requirement.id}">
									<option disabled="disabled" selected="selected">${requirement.requirementSubmissionStatus}</option>
									
									<c:if test="${requirement.requirementSubmissionStatus == 'DRAFT'}">
										<sec:authorize access="hasAnyRole(\'ROLE_ONHOLD_REQUIREMENT\')">
											<option value='ONHOLD'>ONHOLD</option>
										</sec:authorize>
										<sec:authorize access="hasAnyRole(\'ROLE_CANCEL_REQUIREMENT\')">
											<option value='CANCEL'>CANCEL</option>
										</sec:authorize>
									</c:if>
									
									<c:if test="${requirement.requirementSubmissionStatus == 'CLOSED'}">
										<option value='OPEN'>OPEN</option>
									</c:if>
									
									<c:if test="${requirement.requirementSubmissionStatus == 'CANCEL'}">
										
									</c:if>
									
									<c:if test="${requirement.requirementSubmissionStatus == 'OPEN'}">
										<sec:authorize access="hasAnyRole(\'ROLE_CLOSE_FILLED_REQUIREMENT\')">
											<option value='FILLEDCLOSED'>FILLEDCLOSED</option>
										</sec:authorize>
										<sec:authorize access="hasAnyRole(\'ROLE_CLOSE_UN_FILLED_REQUIREMENT\')">
											<option value='UNFILLEDCLOSED'>UNFILLEDCLOSED</option>
										</sec:authorize>
										<sec:authorize access="hasAnyRole(\'ROLE_ONHOLD_REQUIREMENT\')">
											<option value='ONHOLD'>ONHOLD</option>
										</sec:authorize>
										<sec:authorize access="hasAnyRole(\'ROLE_CANCEL_REQUIREMENT\')">
											<option value='CANCEL'>CANCEL</option>
										</sec:authorize>
									</c:if>
									
									<c:if test="${requirement.requirementSubmissionStatus == 'NEW'}">
										<option value='OPEN'>OPEN</option>
										<sec:authorize access="hasAnyRole(\'ROLE_ONHOLD_REQUIREMENT\')">
											<option value='ONHOLD'>ONHOLD</option>
										</sec:authorize>
										<sec:authorize access="hasAnyRole(\'ROLE_CLOSE_FILLED_REQUIREMENT\')">
											<option value='FILLEDCLOSED'>FILLEDCLOSED</option>
										</sec:authorize>
										<sec:authorize access="hasAnyRole(\'ROLE_CLOSE_UN_FILLED_REQUIREMENT\')">
											<option value='UNFILLEDCLOSED'>UNFILLEDCLOSED</option>
										</sec:authorize>
										<sec:authorize access="hasAnyRole(\'ROLE_CANCEL_REQUIREMENT\')">
											<option value='CANCEL'>CANCEL</option>
										</sec:authorize>
									</c:if>
									
									<c:if test="${requirement.requirementSubmissionStatus == 'APPROVED'}">
										
									</c:if>
									
									<c:if test="${requirement.requirementSubmissionStatus == 'REJECTED'}">
										
									</c:if>
									
									<c:if test="${requirement.requirementSubmissionStatus == 'SUBMITTED'}">
										<sec:authorize access="hasAnyRole(\'ROLE_ONHOLD_REQUIREMENT\')">
											<option value='ONHOLD'>ONHOLD</option>
										</sec:authorize>
										<sec:authorize access="hasAnyRole(\'ROLE_CANCEL_REQUIREMENT\')">
											<option value='CANCEL'>CANCEL</option>
										</sec:authorize>
									</c:if>
									
									<c:if test="${requirement.requirementSubmissionStatus == 'ONHOLD'}">
									onHOld : ${requirement.requirementSubmissionPreviousStatus}
										<c:if test="${requirement.requirementSubmissionPreviousStatus == 'OPEN'
										 || requirement.requirementSubmissionPreviousStatus == null 
										 || requirement.requirementSubmissionPreviousStatus == ''}">
											<option value='OPEN'>OPEN</option>
											<sec:authorize access="hasAnyRole(\'ROLE_CLOSE_FILLED_REQUIREMENT\')">
												<option value='FILLEDCLOSED'>FILLEDCLOSED</option>
											</sec:authorize>
											<sec:authorize access="hasAnyRole(\'ROLE_CLOSE_UN_FILLED_REQUIREMENT\')">
												<option value='UNFILLEDCLOSED'>UNFILLEDCLOSED</option>
											</sec:authorize>
											<sec:authorize access="hasAnyRole(\'ROLE_CANCEL_REQUIREMENT\')">
												<option value='CANCEL'>CANCEL</option>
											</sec:authorize>
										</c:if>
										<c:if test="${requirement.requirementSubmissionPreviousStatus == 'DRAFT'}">
											<option value='DRAFT'>DRAFT</option>
											<sec:authorize access="hasAnyRole(\'ROLE_CANCEL_REQUIREMENT\')">
												<option value='CANCEL'>CANCEL</option>
											</sec:authorize>
										</c:if>
										<c:if test="${requirement.requirementSubmissionPreviousStatus == 'SUBMITTED'}">
											<option value='SUBMITTED'>SUBMITTED</option>
											<sec:authorize access="hasAnyRole(\'ROLE_CANCEL_REQUIREMENT\')">
												<option value='CANCEL'>CANCEL</option>
											</sec:authorize>
										</c:if>
										<c:if test="${requirement.requirementSubmissionPreviousStatus == 'NEW'}">
											<option value='OPEN'>OPEN</option>
											<sec:authorize access="hasAnyRole(\'ROLE_ONHOLD_REQUIREMENT\')">
												<option value='ONHOLD'>ONHOLD</option>
											</sec:authorize>
											<sec:authorize access="hasAnyRole(\'ROLE_CLOSE_FILLED_REQUIREMENT\')">
												<option value='FILLEDCLOSED'>FILLEDCLOSED</option>
											</sec:authorize>
											<sec:authorize access="hasAnyRole(\'ROLE_CLOSE_UN_FILLED_REQUIREMENT\')">
												<option value='UNFILLEDCLOSED'>UNFILLEDCLOSED</option>
											</sec:authorize>
											<sec:authorize access="hasAnyRole(\'ROLE_CANCEL_REQUIREMENT\')">
												<option value='CANCEL'>CANCEL</option>
											</sec:authorize>
										</c:if>
									</c:if>
									<c:if test="${requirement.requirementSubmissionStatus == 'UNFILLEDCLOSED'}">
										<option value='OPEN'>OPEN</option>
									</c:if>
									<c:if test="${requirement.requirementSubmissionStatus == 'FILLEDCLOSED'}">
										<option value='OPEN'>OPEN</option>
									</c:if>
								</select>
							</div>
						</c:if>

			            <c:if test="${ (requirement.requirementSubmissionStatus =='NEW'  || requirement.requirementSubmissionStatus =='DRAFT' || requirement.requirementSubmissionStatus =='SUBMITTED'  || requirement.requirementSubmissionStatus  == 'APPROVED'  || requirement.requirementSubmissionStatus =='REJECTED'  ||  requirement.requirementSubmissionStatus  == 'OPEN' ||  requirement.requirementSubmissionStatus  == 'ONHOLD' ) && (isAdmin == 'true' || requirement.isLoginUserAsApprover  == 'true' || isManager  == 'true' || requirement.isLoginUserAsInitiator == 'true') }">
			            	<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_REQUIREMENT\')">  
			            		  <a href="${SourceLeadWeb_atsUrl}/requirement?requirementUUID=${requirementUUID}"><button  class="btn btn-primary" type="button">Edit</button></a> 
			            	</sec:authorize>
			            </c:if>

			            <c:if test="${(isAdmin == 'true' || requirement.isLoginUserAsApprover == 'true') && !approvedByLoginUser && requirement.requirementSubmissionStatus  != 'DRAFT' && requirement.requirementSubmissionStatus != 'ONHOLD' && requirement.requirementSubmissionStatus != 'CLOSED'  && requirement.requirementSubmissionStatus != 'CANCEL' &&  requirement.requirementSubmissionStatus != 'APPROVED' &&  requirement.requirementSubmissionStatus != 'REJECTED'  &&  requirement.requirementSubmissionStatus != 'OPEN' && requirement.requirementSubmissionStatus  !='NEW' && requirement.requirementSubmissionStatus  != 'UNFILLEDCLOSED' && requirement.requirementSubmissionStatus  != 'FILLEDCLOSED'}">
			            	  <button  class="btn btn-success mr ml btn-sm" type="button" id = "approverbut" onClick ="updateReuqirementStatus('APPROVED');hideButtonOnClick();"  >Approve</button> 

         		              <button  class="btn btn-danger btn-sm" id = "rejectedbut" type="button" onClick ="rejectRequirementAlertMessage('REJECTED');hideButtonOnClick();">Reject</button>
			            </c:if>
					</div></div>
					<div class="collpasePannel clearfix mt10"> 
		              <a href="javascript:void(0)" class="assignedGrid1"><h4 class="pull-left">Assigned Members:</h4><div class="pull-left paddingLeft"><strong class="ml pull-left mt10" id="forAssignedToCount1">${requirementAssignToListCount}</strong></div><i title="view List" class="fa fa-sort-down pull-right"></i></a>  
		           	  <a href="javascript:void(0)" class="assignedGrid2"><h4 class="pull-left">Assigned Members:</h4><div class="pull-left paddingLeft"><strong class="ml pull-left mt10" id="forAssignedToCount2">${requirementAssignToListCount}</strong></div><i title="view List" class="fa fa-sort-down pull-right"></i></a>
		           	  <sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_REQUIREMENT\')">
		           	  	<span class="pull-right cursorPointer edit-assignedMembers" title="Edit Assigned Members"><a href="javascript:void(0)" data-toggle="modal" data-target="#myModal" onclick="getAssignedMembersListForEdit(${requirement.id});"><i class="fa fa-edit"></i></a></span>
		           	  </sec:authorize>
		           </div> 
		          
			            
                 <div class="clearfix"> 
                 	<div class="clearfix row">
                 	<div class="col-md-12">
                 	<div id="assignedMemberList" class="assignedMemberList"></div>
                 	</div>
              			<div class="col-md-12 divskill divsAssigned pull-left"> 
          				
          				
          				
				        <h1>Description:</h1> 
				        <div class="RDdetails">${!empty requirement.description ? requirement.description : ' No DATA '}</div>
			            <div class="clearfix"></div>
			            <h1>Skills:</h1>
			            <div class="RDdetails">${!empty requirement.skills ? requirement.skills : ' No DATA '}</div>
			            <div class="clearfix"></div>
			            <h2 class="RPadT"> Job Details:</h2> 
			            <div class="require_one">
			                <div class="require_wrap_details_cta"> <b>Profile %:</b><span>${requirement.profilePercentage} %</span>
				            	<div class="clearfix"></div> 
				            </div> 
				            
				            <div class="require_wrap_details_cta"> <b>No. of positions:</b><span>${!empty requirement.positions ? requirement.positions : ' -- '}</span>
				            	<div class="clearfix"></div> 
				            </div> 
				            <div class="require_wrap_details_cta"> <b>Industry:</b><span>${!empty requirement.industryName ? requirement.industryName : ' -- '}</span>
				            	<div class="clearfix"></div> 
					        </div> 
					        <c:if test="${!empty requirement.projectName}">
					         <div class="require_wrap_details_cta"> <b>Project Name:</b><span>${!empty requirement.projectName ? requirement.projectName : ' -- '}</span>
				            	<div class="clearfix"></div> 
					        </div> 
					        </c:if>
					        <div class="require_wrap_details_cta require_wrap_details_view require_uploadfile"> <b>Attachment:</b><span>${!empty requirement.fileName ? requirement.fileName : ' -- '}</span>
					            <c:if test="${!empty requirement.fileName}">
					            	<a download class="glyphicon glyphicon-download-alt" target = "_blank" href="${requirement.fileURL}"></a><a class="fa fa-eye ml10" onClick="viewFile('${requirement.fileURL}','${requirement.fileName}','${requirement.fileContentType}');"></a>
					            </c:if>
					            <div class="clearfix"></div> 
				            </div> 
				            
				            <div class="require_wrap_details_cta"> <b>Education:</b><span>${!empty requirement.education ? requirement.education : ' -- '}</span>
					            <div class="clearfix"></div> 
				             </div> 
				            
				            <div class="require_wrap_details_cta"> <b>Cooling Off Period:</b><span>${!empty requirement.reqCoolingOffPeriod ? requirement.reqCoolingOffPeriod : ' -- '}</span>
				            	<div class="clearfix"></div> 
				            </div> 
				           
				            <div class="require_wrap_details_cta"> <b>Created By:</b><span>${!empty requirement.createdByName ? requirement.createdByName : ' -- '}</span>
				            	<div class="clearfix"></div> 
				            </div>
				            
				            <div class="require_wrap_details_cta"> <b>Visa:</b><span>${!empty requirement.visaType ? requirement.visaType : ' -- '}</span>
				            	<div class="clearfix"></div> 
				            </div> 	
				            <c:if test="${requirement.medicalClearence == 'Y'}">
				            <div class="require_wrap_details_cta"> <b>Medical Clearance:</b><span>Yes</span>
				            	<div class="clearfix"></div> 
				            </div> 	
				            </c:if>
				              <c:if test="${requirement.medicalClearence == 'N'}">
				            <div class="require_wrap_details_cta"> <b>Medical Clearance:</b><span>No</span>
				            	<div class="clearfix"></div> 
				            </div> 	
				            </c:if>
				            <c:if test="${empty requirement.medicalClearence}">
				            <div class="require_wrap_details_cta"> <b>Medical Clearance:</b><span>--</span>
				            	<div class="clearfix"></div> 
				            </div> 	
				            </c:if>
				             <c:if test="${requirement.securityClearance == 'Y'}">
				            <div class="require_wrap_details_cta"> <b>Security Clearance:</b><span>Yes</span>
				            	<div class="clearfix"></div> 
				            </div> 
				            </c:if>
				               <c:if test="${requirement.securityClearance == 'N'}">
				            <div class="require_wrap_details_cta"> <b>Security Clearance:</b><span>No</span>
				            	<div class="clearfix"></div> 
				            </div> 
				            </c:if>
				              <c:if test="${empty requirement.securityClearance}">
				            <div class="require_wrap_details_cta"> <b>Security Clearance:</b><span>--</span>
				            	<div class="clearfix"></div> 
				            </div> 
				            </c:if>
				              <c:if test="${requirement.overTime == 'Y'}">
				             <div class="require_wrap_details_cta"> <b>Over Time:</b><span>Yes</span>
				            	<div class="clearfix"></div> 
				            </div>  
				            </c:if>
				            <c:if test="${requirement.overTime == 'N'}">
				             <div class="require_wrap_details_cta"> <b>Over Time:</b><span>No</span>
				            	<div class="clearfix"></div> 
				            </div>  
				            </c:if>
				             <c:if test="${empty requirement.overTime}">
				             <div class="require_wrap_details_cta"> <b>Over Time:</b><span>--</span>
				            	<div class="clearfix"></div> 
				            </div>  
				            </c:if>
				             <c:if test="${requirement.backgroundCheck == 'Y'}">
				              <div class="require_wrap_details_cta"> <b>Background Check:</b><span>Yes</span>
				            	<div class="clearfix"></div> 
				            </div>  
				            </c:if>
				             <c:if test="${requirement.backgroundCheck == 'N'}">
				              <div class="require_wrap_details_cta"> <b>Background Check:</b><span>No</span>
				            	<div class="clearfix"></div> 
				            </div>  
				            </c:if>
				             <c:if test="${empty requirement.backgroundCheck}">
				              <div class="require_wrap_details_cta"> <b>Background Check:</b><span>--</span>
				            	<div class="clearfix"></div> 
				            </div>  
				            </c:if>
				           
				            <div class="require_wrap_details_cta"> <b>Gender:</b><span>${!empty requirement.gender ? requirement.gender : ' -- '}</span>
				            	<div class="clearfix"></div> 
				            </div> 	
				              <div class="require_wrap_details_cta"> <b>Experience Level:</b><span>${!empty requirement.experienceLevel.name ? requirement.experienceLevel.name : ' -- '}</span>
				            	<div class="clearfix"></div> 
				            </div>
				             <div class="require_wrap_details_cta"> <b>Process:</b><span>${!empty requirement.process ? requirement.process : ' -- '}</span>
				            	<div class="clearfix"></div> 
				            </div>
				            <c:if test="${!empty sourceleadSectionList}">
						            <c:forEach var="section" items="${sourceleadSectionList}" varStatus="sectionOrder">
						               <c:if test="${sectionOrder.index == 0}">
						                  <div id="customDiv${section.id}"></div>
						               </c:if>
						            </c:forEach>
						   	</c:if>
				            
				             			            
				            <div class="clearfix"></div> 
			            </div> 

			      <c:if test="${requirement.isClientJob == 'Y'}">
		                <h2>Client Details :</h2> 
		                <div class="require_one"> 
		            	<div class="require_wrap_details_cta"> <b>Client Contact:</b>
		            		<span class="tbldata-dotdot-big">
		            			<c:choose>
								    <c:when test="${!empty requirement.spocName}">
					                	<%-- <a href="<c:url value="/clientDashboard?orgSpocUUID=${requirement.spocUUID}"/>">${requirement.spocName}</a> --%>
						          	${requirement.spocName}
						          	</c:when>
								    <c:otherwise>
								    	' -- '
								    </c:otherwise>
							    </c:choose>
		            		</span>
			            	<div class="clearfix"></div> 
		                </div>
		              
		               <sec:authorize access="hasAnyRole(\'ROLE_VIEW_CLIENT\',\'ROLE_VIEW_EDIT_CLIENT\')">
			                <div class="require_wrap_details_cta"> <b>Client Name:</b>
				                <span class="tbldata-dotdot-big">
				                <c:choose>
								    <c:when test="${!empty requirement.clientName}">
					                	<a href="${SourceLeadWeb_vmsUrl}/clientDashboard?orgRelationUUID=${requirement.orgRelationUUID}">${requirement.clientName}</a>
						          	</c:when>
								    <c:otherwise>
								    	' -- '
								    </c:otherwise>
							    </c:choose>
				                </span>
				            	<div class="clearfix"></div> 
				            </div>  
			            </sec:authorize>
			            
			             <sec:authorize access="!hasAnyRole(\'ROLE_VIEW_CLIENT\',\'ROLE_VIEW_EDIT_CLIENT\')">
			                <div class="require_wrap_details_cta"> <b>Client Name:</b>
			                	<span class="tbldata-dotdot-big">
			                		<c:choose>
									    <c:when test="${!empty requirement.clientName}">
						                	${requirement.clientName}
							          	</c:when>
									    <c:otherwise>
									    	' -- '
									    </c:otherwise>
								    </c:choose>
			                	</span>
				            	<div class="clearfix"></div> 
				            </div>  
			            </sec:authorize>
			            
			            <c:choose>
						    <c:when test="${!empty requirement.parentRequirement}">
						    	<c:choose>
						    		<c:when test="${requirement.clientCurrencyCode == 'INR' && !empty requirement.clientRate && !empty requirement.clientRateType}">
						            	<div class="require_wrap_details_cta"> <b>Bill rate/salary:</b> <span><i class="fa fa-inr"></i> ${requirement.clientRate} - ${requirement.clientMaxRate}  ${requirement.clientRateType}</span>
							            	<div class="clearfix"></div> 
					               		</div> 
							    	</c:when>
							    	<c:when test="${requirement.clientCurrencyCode == 'EUR' && !empty requirement.clientRate && !empty requirement.clientRateType}">
						            	<div class="require_wrap_details_cta"> <b>Bill rate/salary:</b> <span><i class="fa fa-eur"></i> ${requirement.clientRate} - ${requirement.clientMaxRate}   ${requirement.clientRateType}</span>
							            	<div class="clearfix"></div> 
					               		</div> 
							    	</c:when>
							    	<c:when test="${requirement.clientCurrencyCode == 'GBP' && !empty requirement.clientRate && !empty requirement.clientRateType}">
						            	<div class="require_wrap_details_cta"> <b>Bill rate/salary:</b> <span><i class="fa fa-gbp"></i> ${requirement.clientRate} - ${requirement.clientMaxRate}  ${requirement.clientRateType}</span>
							            	<div class="clearfix"></div> 
					               		</div> 
							    	</c:when>
							    	<c:when test="${requirement.clientCurrencyCode == 'USD' && !empty requirement.clientRate && !empty requirement.clientRateType}">
						            	<div class="require_wrap_details_cta"> <b>Bill rate/salary:</b> <span><i class="fa fa-dollar"></i> ${requirement.clientRate} - ${requirement.clientMaxRate}  ${requirement.clientRateType}</span>
							            	<div class="clearfix"></div> 
					               		</div> 
							    	</c:when>
							    	<c:when test="${requirement.clientCurrencyCode == 'AUD' && !empty requirement.clientRate && !empty requirement.clientRateType}">
						            	<div class="require_wrap_details_cta"> <b>Bill rate/salary:</b> <span><i class="fa fa-dollar"></i> ${requirement.clientRate} - ${requirement.clientMaxRate}  ${requirement.clientRateType}</span>
							            	<div class="clearfix"></div> 
					               		</div> 
							    	</c:when>
							    	<c:when test="${requirement.clientCurrencyCode == 'SGD' && !empty requirement.clientRate && !empty requirement.clientRateType}">
						            	<div class="require_wrap_details_cta"> <b>Bill rate/salary:</b> <span><i class="fa fa-dollar"></i> ${requirement.clientRate} - ${requirement.clientMaxRate}  ${requirement.clientRateType}</span>
							            	<div class="clearfix"></div> 
					               		</div> 
							    	</c:when>
								    <c:otherwise>
									      <div class="require_wrap_details_cta"> <b>Bill rate/salary:</b> <span> --- </span>
										      <div class="clearfix"></div> 
					               		  </div>
									</c:otherwise>
							    </c:choose>
				          	</c:when>
						    <c:otherwise>
						    	<c:choose>
						    		<c:when test="${requirement.clientCurrencyCode == 'INR' && !empty requirement.clientRate}">
						    			<div class="require_wrap_details_cta"> <b>Bill rate/salary:</b> <span><i class="fa fa-inr"></i> ${requirement.clientRate} - ${requirement.clientMaxRate}  ${requirement.clientRateType}</span>
							    			<div class="clearfix"></div> 
							            </div>
						    		</c:when>
						    		<c:when test="${requirement.clientCurrencyCode == 'EUR' && !empty requirement.clientRate}">
						    			<div class="require_wrap_details_cta"> <b>Bill rate/salary:</b> <span><i class="fa fa-eur"></i> ${requirement.clientRate} - ${requirement.clientMaxRate}  ${requirement.clientRateType}</span>
							    			<div class="clearfix"></div> 
							            </div>
						    		</c:when>
						    		<c:when test="${requirement.clientCurrencyCode == 'GBP' && !empty requirement.clientRate}">
						    			<div class="require_wrap_details_cta"> <b>Bill rate/salary:</b> <span><i class="fa fa-gbp"></i> ${requirement.clientRate} - ${requirement.clientMaxRate}  ${requirement.clientRateType}</span>
							    			<div class="clearfix"></div> 
							            </div>
						    		</c:when>
						    		<c:when test="${requirement.clientCurrencyCode == 'USD' && !empty requirement.clientRate}">
						    			<div class="require_wrap_details_cta"> <b>Bill rate/salary:</b> <span><i class="fa fa-dollar"></i> ${requirement.clientRate} - ${requirement.clientMaxRate}  ${requirement.clientRateType}</span>
							    			<div class="clearfix"></div> 
							            </div>
						    		</c:when>
						    		<c:when test="${requirement.clientCurrencyCode == 'AUD' && !empty requirement.clientRate}">
						    			<div class="require_wrap_details_cta"> <b>Bill rate/salary:</b> <span><i class="fa fa-dollar"></i> ${requirement.clientRate}  ${requirement.clientRateType}</span>
							    			<div class="clearfix"></div> 
							            </div>
						    		</c:when>
						    		<c:when test="${requirement.clientCurrencyCode == 'SGD' && !empty requirement.clientRate}">
						    			<div class="require_wrap_details_cta"> <b>Bill rate/salary:</b> <span><i class="fa fa-dollar"></i> ${requirement.clientRate}  ${requirement.clientRateType}</span>
							    			<div class="clearfix"></div> 
							            </div>
						    		</c:when>
						    		<c:otherwise>
									      <div class="require_wrap_details_cta"> <b>Bill rate/salary:</b> <span> --- </span>
										      <div class="clearfix"></div> 
					               		  </div>
									</c:otherwise>
						       </c:choose>  	
				          	</c:otherwise>              
						</c:choose>
				          <div class="require_wrap_details_cta"> <b>Submission Form:</b><span>${!empty requirement.clientSubmissionTemplate ? requirement.clientSubmissionTemplate : ' -- '}</span>
			            		<div class="clearfix"></div> 
			              </div> 
			              <div class="clearfix"></div>
			              <c:if test="${isAdmin == 'true' || requirement.isLoginUserAsApprover  == 'true' || isManager  == 'true' || requirement.isLoginUserAsInitiator == 'true'}">
				              <div class="require_wrap_details_cta"> <b>Job Rating:</b><span>${!empty requirement.jobRate ? requirement.jobRate : ' -- '}</span>
				            		<div class="clearfix"></div> 
				              </div> 
				              <div class="require_wrap_details_cta"> <b>Pay out Period (Days):</b><span>${!empty requirement.payOutPeriod ? requirement.payOutPeriod : ' -- '}</span>
				            		<div class="clearfix"></div> 
				              </div> 
				              <div class="require_wrap_details_cta"> <b>Incentivized:</b><span>${!empty requirement.incentivized ? requirement.incentivized : ' -- '}</span>
				            		<div class="clearfix"></div>
				            		<c:if test="${!empty requirement.incentivized && requirement.incentivized == 'Y'}">
					            		<table class="table table-bordered">
					            			<thead>
					            			<tr>
					            				<th>No.of Joiners</th><th>Type</th><th>Amount</th>
					            			</tr>
					            			</thead>
					            			<tbody>
					            			<c:if test="${!empty requirementIncentiveList}">
									            <c:forEach var="inc" items="${requirementIncentiveList}" varStatus="rowCount">
									              	<tr>
							            				<td><div class="tblminwidth-75">${inc.joiners}</div></td>
							            				<td><div class="tblminwidth-75 text-capitalize">${inc.joinersAmountType}</div></td>
							            				<td><div class="tblminwidth-175">${inc.joinersAmount}${inc.joinersAmountType == 'percentage' ? '%' : ''}</div></td>
							            			</tr>
									            </c:forEach>
									         </c:if>
					            			</tbody>
					            		</table>
				            		</c:if>
				              </div>
				              <div class="clearfix"></div>
			              </c:if>
			              <c:if test="${!empty sourceleadSectionList}">
						            <c:forEach var="section" items="${sourceleadSectionList}" varStatus="sectionOrder">
						               <c:if test="${sectionOrder.index == 1}">
						                  <div id="customDiv${section.id}"></div>
						               </c:if>
						            </c:forEach>
						   	</c:if>
			              </div>
			         </c:if>
			         <div class="clearfix"></div> 
			          
			         <h2>Internal Details:</h2> 
			         <div class="require_one clearfix"> 
			         	 <c:if test="${requirement.isClientJob  == 'N'}">
			         		 <div class="require_wrap_details_cta"> <b>Application form:</b><span>${!empty requirement.clientSubmissionTemplate ? requirement.clientSubmissionTemplate : ' -- '}</span></div>
			         	 </c:if>
					     <div class="clearfix"></div> 
					           
			            <c:if test="${!empty requirement.approvalMechanismType}">
			           	<c:choose>
						   <c:when test="${requirement.approvalMechanismType == 'PARALLEL'}">
						   	 <div class="require_wrap_details_cta"> <b>Approvers:</b> <span> 
				            	<c:forEach var ="req" items="${requirement.requirementApprover}" varStatus="reqStatus">
							      ${reqStatus.index >= 1 ? ' , ':''}${req.approverUserId.firstName} ${req.approverUserId.lastName}
							     </c:forEach>
				            	 </span> 
				            	 </div> 
						   </c:when>
						   <c:otherwise>
						   		<c:forEach var ="req" items="${requirement.requirementApprover}" varStatus="reqStatus">
							        <div class="require_wrap_details_cta"> <b>Approvers ${req.level}:</b> <span> ${req.approverUserId.firstName} ${req.approverUserId.lastName}</span></div>
							     </c:forEach>
						   </c:otherwise>
						</c:choose>
			            </c:if>  
			            
                    <div class="clearfix"></div>
                    
                    <div class="require_wrap_details_cta"> <b>Assign to all :</b><span>${requirement.releaseJobToAllUsers  == 'Y' ? 'YES' : 'NO'}</span></div>
                    <div class="clearfix"></div>
                    <%-- // below lines is commented for to move in next release build added by SRINIVAS SABBU.--%>
                    <div class="require_wrap_details_cta"> <b>Workflow :</b><span>${requirement.workflowId.workFlowName}</span></div>
                    <c:if test="${!empty sourceleadSectionList}">
						            <c:forEach var="section" items="${sourceleadSectionList}" varStatus="sectionOrder">
						               <c:if test="${sectionOrder.index == 2}">
						                  <div id="customDiv${section.id}"></div>
						               </c:if>
						            </c:forEach>
					</c:if>
					<c:if test="${requirement.walkIn}">
                    	<div class="clearfix"></div>
                    	<div class="require_wrap_details_cta">
                    		<b>WalkIn Day(s) :</b><span>${requirement.walkInDays}</span>
	                    	<div class="clearfix"></div>
                    	</div>
                    	<div class="clearfix"></div> 
                    </c:if>
                    <c:if test="${requirement.primaryRecruiterUserId!=null}">
                    	<div class="clearfix"></div>
                    	<div class="require_wrap_details_cta">
                    		<b>Primary Recruiter :</b><span>${requirement.primaryRecruiterUserId.fullName}</span>
	                    	<div class="clearfix"></div>
                    	</div>
                    	<div class="clearfix"></div> 
                    </c:if>
                </div> 			
                    <c:if test="${requirement.requirementJobBoardInfoList != null && !requirement.requirementJobBoardInfoList.isEmpty()}">
                        <div class="clearfix"></div> 
                        <h2>Job Board(s)</h2> 
                        <div class="require_one"> 
                            <c:forEach items="${requirement.requirementJobBoardInfoList}" var="r">
                                <span><a target="_blank" href="${r.jobUrl}"><img width="70px" src="${r.jobBoard.logoUrl}" alt="${r.jobBoard.name}"/></a></span>
                            </c:forEach>
                                <div class="clearfix"></div> 
                        </div>
                    </c:if>
                   <div class="clearfix"></div> 
                 </div> 
                 <div class="clearfix"></div> 
           
                 </div> 	
               </div> 
           </div>
               </div> 
             </div> 
      </div>
       <div id="detailsLoading"></div>
       <div id="tab-2" class="project_tabs_inn"></div>
       <div id="pipelineLoading"></div>
       <div id="status-div"></div>
       <div class="new_side_wrap">
            <div class="new_side_wrap_inn">
             <div class="new_side_wrap_cta">
                 <div id="addApplicantDiv"></div>
             </div>
         </div>
      </div>
      <div class="add_inter_wrap">
         <div class="new_side_wrap_inn">
             <div id="addInterviewDiv"></div>
         </div>
      </div>
	 <div class="add_offer_wrap">
			<div class="new_side_wrap_inn">
				<div id="addOfferDiv"></div>
			</div>
	 </div>
	 <div class="submitnew-wrap">
			<div class="new_side_wrap_inn">
				<div class="add_inter">
					<div id="submitToClientDiv"></div>
					<div id="emailTemplateDiv"></div>
				</div>
			</div>
	 </div>
	 <div class="shortlist-wrap">
			<div class="new_side_wrap_inn">
				<divsubStatus class="add_inter">
				<div id="shortlistDiv"></div>
			</div>
	   </div>
	</div>
	<div class="clients_side_wrap quickviewTabs">
		<ul class="nav nav-tabs">
			<li class="active" id="pipelineQuickviewTabsqv"><a
				data-toggle="tab" href="#quickview">Quickview</a></li>
			<li id="pipelineQuickviewTabsdetails"><a data-toggle="tab"
				id="detailedView" href="#detailview">Detailed View</a></li>
		</ul>
		<div class="tab-content">
			<div id="quickLoading"></div>
			<div id="quickview" class="tab-pane fade in active">
				<div id="applicantQuickViewDiv"></div>
			</div>
			<div id="detailViewLoading"></div>
			<div id="detailview" class="tab-pane fade">
				<div class="clients_side_wrap_inn">
					<div class="project_side_wrap_inn_tabs">
						<div id="viewCandidateDiv"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="actionsDiv" class="timesheet_head timesheetHead minHeight candidateHead recruitmentHead headersummary clearfix zindex" style="display: none;">
		<ul class="alignUlList mt10">
		<!-- <li><a href="javascript:void(0)" title="Add Applicants" onclick="submitCandidatesToJob();" class="fa fa-user-plus cursorPointer mt5"></a></li> -->
		  <li class="li-vendor">
		   <c:if test="${requirement.requirementSubmissionStatus != 'SUBMITTED' && requirement.requirementSubmissionStatus != 'CLOSED' && requirement.requirementSubmissionStatus != 'REJECTED' &&  requirement.requirementSubmissionStatus != 'CANCEL' && requirement.requirementSubmissionStatus != 'DRAFT' && requirement.requirementSubmissionStatus != 'ONHOLD' && requirement.requirementSubmissionStatus != 'FILLEDCLOSED'  && requirement.requirementSubmissionStatus != 'UNFILLEDCLOSED' }">
		        <a onclick="getActions();" href="javascript:void(0)" class="action"> Action <i  class="fa fa-caret-down" ></i></a>
		   </c:if>
			    <div class="action-pop">
					<span class="width175">
							<sec:authorize access="hasAnyRole('ROLE_ADD_APPLICANT')">
								<div class="immSub">
									<a href="javascript:void(0)"><i class="fa fa-plus"></i>&nbsp;Add Applicant</a>
							    	<div class="immSubMenu immSubMenu-custome">
										<a href="javascript:void(0)" title="" onclick="getAddApplicant('${requirement.id}');">Add Applicant Form</a>
									    <a href="javascript:void(0)" title="" onclick="submitCandidatesToJob();">Add Bulk Applicants</a>
									    <a href="javascript:void(0)" title="" onclick="importApplicantsToJob('${requirement.id}');">Import Bulk Applicants</a>
									    <a href="javascript:void(0)" title="" onclick="bulkResumeParsing('${requirement.requirementUUID}');">Bulk Resume Parsing</a>
									</div>
								</div>
							</sec:authorize>
							<sec:authorize access="hasAnyRole('ROLE_SHORTLIST')">
							      <a href="javascript:void(0)" title="" onclick="candidateShortList('${requirement.id}');" class="shortlistClick"><i class="fa fa-plus"></i>&nbsp;Shortlist</a>
							</sec:authorize>
						    <sec:authorize access="hasAnyRole('ROLE_SUBMIT_TO_CLIENT')">
						          <c:if test="${clientSubmissions == 'Y' && isSpocActive == 'Y'}">
							        <a href="javascript:void(0)" title="" onclick="submitToClient('${requirement.id}');" class="submitTo"><i class="fa fa-plus"></i>&nbsp;Submit to client</a>
							      </c:if>
							</sec:authorize>
							<sec:authorize access="hasAnyRole('ROLE_ADD_INTERVIEW')">
							       <a href="javascript:void(0)" title="" onclick="addInterview('${requirement.id}');" class="add-interview"><i class="fa fa-plus"></i>&nbsp;Schedule Interview</a>
							</sec:authorize>
							<sec:authorize access="hasAnyRole('ROLE_CREAE_OFFER')">
							       <a href="javascript:void(0)" title=""  onclick="addOffer('${requirement.id}');" class="add-offer"><i class="fa fa-plus"></i>&nbsp;Generate Offer</a>
							</sec:authorize>
				  </span>
              </div>
          </li>
		  <div class="clearfix"></div>
	     </ul>
	</div>
	<div id="status-data" class="datatable_filter"></div>
	<div id="tab-3" class="project_tabs_inn overflow-data datatable_filter"></div>
	<div id="recommendationsLoading"></div>
	<div id="tab-4" class="project_tabs_inn overflow-data datatable_filter"></div>
	<div id="globalRecommendationsLoading"></div>
	<div id="tab-5" class="project_tabs_inn overflow-data datatable_filter"></div>
	
	
	<input type="hidden" name="assignedTeamId" id="assignedTeamId" value=""/>
</div>


<div class="import_submission_side_wrap">
	<div class="new_side_wrap_inn">
		<div class="new_side_wrap_cta">
		 <div class="dataFilterNew pdtop datatable_filter">
		    <div id="importApplicantsSideDivDivLoading"></div>
			<div id="importApplicantsSideDiv"></div>
			</div>
		</div>
	</div>
</div>

<script>
function importApplicantsToJob(requirementId){
	$.ajax({
		  type: "GET",
		    url: atsUrl+"/getRequirementSubmissionTemplateForBulkImprtApplicants?requirementId="+requirementId,
		     success :function(response) {
		    	    $("#importApplicantsSideDiv").empty();  
		    	    $("#importApplicantsSideDiv").append(response);
		    	    
		    	    $('.action-pop').slideUp();
		    	    $(".import_submission_side_wrap").addClass("import_submission_side_wrap_active");
		    	    $(".side_mask").fadeIn(200);
		    	    $("body").css('overflow-y', 'hidden');
		    	    getSubmissionsUploadedHistory();
				},
			});
}

function bulkResumeParsing(requirementUUID) {
	window.open(sourceleadUrl + "/bulk-resume-import.html?requirementUUID=" + requirementUUID, "_blank");
}
</script>


<script>
function getCustomDependentField(sectionId){
	var requirementId = '${requirement.id}';
	var type= 'view';
	 $.ajax({
			type : "POST" , 
				 url: atsUrl+"/getAllASectionCustomFields?sectionId="+sectionId+"&requirementId="+requirementId+"&requestFrom=requirement&requestType="+type,
				 success: function(data) {
		        	$('#customDiv'+sectionId).empty();
		        	$('#customDiv'+sectionId).append(data);
				 }
		  });
}
</script>

<script>
function hideButtonOnClick(){
		$('#approverbut').hide();
		$('#rejectedbut').hide();
}
</script>
<script>
$(function(){
	$('.assignedGrid2').hide();
	$('.assignedGrid1').click(function(){
		$("#assignedMemberList").slideDown();
		$(".assignedGrid1").hide();
		$(".assignedGrid2").show(); 
		 assignedMemberList("${requirement.id}");
	});
	$('.assignedGrid2').click(function(){
		$("#assignedMemberList").slideUp();
		$(".assignedGrid2").hide();
		 $(".assignedGrid1").show();
	});
});
</script>
<script type="text/javascript">
function changeStatus(requirementId, requirementStatus){
	var status = document.getElementById('changeStatusId' + requirementId).value;
	 var userId ='${SourceLeadWeb_userId}';
     if(status == "DRAFT"){
        	 $.ajax({
             type : "POST",
        	 url: atsUrl+"/saveChangedStatusFromDraft?"+tokenParamName+"="+tokenValue+"&requirementId="+requirementId+"&status="+status+"&userId="+userId,
	        	 success :function(result) {
	        		 	if (result.response === "SESSION_TIMEDOUT") {
			  				location.replace(sourceleadUrl);
		                } else if (result.response === "AWKWARD") {
		                    location.href = "/errorPage";
		                } else if (result.response === "SUCCESS") {
		                	$("#changeStatusId" +requirementId).val(status);
		                	$(".success_pop").show();
		                	$(".success_pop span").html("Changed Job status to " + status);
		                	 setTimeout(function(){
				                	$(".success_pop").hide();
				                	location.reload();
		                	 }, 1500);
		                } else if (result.indexOf('Sign In') !== -1) {
							location.replace(sourceleadUrl);
						} else {
		                    showError("Operation failed, please try again");
		                }
	         	}
        	});
         }else{
        	 $.ajax({
             type : "POST",
        	 url: atsUrl+"/saveChangedStatus?"+tokenParamName+"="+tokenValue+"&requirementId="+requirementId+"&status="+status,
	        	 success :function(result) {
	        		 if (result.response === "SESSION_TIMEDOUT") {
			  				location.replace(sourceleadUrl);
		                } else if (result.response === "AWKWARD") {
		                    location.href = "/errorPage";
		                } else if (result.response === "SUCCESS") {
		                	$("#changeStatusId" +requirementId).val(status);
		                	$(".success_pop").show();
		                	$(".success_pop span").html("Changed Job status to " + status);
		                	 setTimeout(function(){
				                	$(".success_pop").hide();
				                	location.reload();
		                	 }, 1500);
		                } else if (result.indexOf('Sign In') !== -1) {
							location.replace(sourceleadUrl);
						} else {
		                    showError("Operation failed, please try again");
		                }
	         	}
        	});
         }
	}
</script>