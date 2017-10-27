 <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 
 <jsp:include page="recommendationListTable.jsp"></jsp:include>
 
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
					<div class="company-logo">
						<div class="imageParent">
							<img onerror="predefineOrgImage(this);"
								src="${sourceleadUrl}/displayOrgImage/${requirement.orgLocationId.org.orgCode}"
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
									<li><i class="fa fa-map-marker" title="Location"></i><span title="${!empty requirement.city ? requirement.city : ''} ${!empty requirement.city ? ',' : ''} ${!empty requirement.state ? requirement.state : ''} ${!empty requirement.state ? ',' : ''}${!empty requirement.country ? requirement.country : ''}">${!empty requirement.city ? requirement.city : ''} ${!empty requirement.city ? ',' : ''} ${!empty requirement.state ? requirement.state : ''} ${!empty requirement.state ? ',' : ''}${!empty requirement.country ? requirement.country : ''}</span></li>
									<li><i class="fa fa-cab" title="Travel allowance"></i><span title="${!empty requirement.travelAllowance ? requirement.travelAllowance : ''}">${!empty requirement.travelAllowance ? requirement.travelAllowance : ''}</span></li>
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
			    <li class="${!empty type ? 'current' : ''}" id="${requirement.requirementSubmissionStatus == 'CLOSED' && requirement.isreceivingApplicants == 'Y' ? 'applicantsTab':''}"><a href="#tab-2" onclick="requirementPipeline('${requirement.id}','all');requirementStatusBar('${requirement.id}');">Applicants</a></li>
			</sec:authorize>
			<sec:authorize access="hasAnyRole('ROLE_RECOMMENDATIONS')">
			    <li id="${requirement.requirementSubmissionStatus == 'CLOSED' && requirement.isreceivingApplicants == 'Y' ? 'recommendationsTab':''}"><a href="#tab-3" onclick="requirementRecommendations('${requirement.id}');">Recommendations</a></li>
			</sec:authorize>
			<li><a href="#tab-4" onclick="requirementNotes('${requirement.id}');">Notes</a></li>
		</c:if>
	</ul>
	<div class="clearfix"></div>
	<div class="project_tabs">
      <div id="tab-1" class="project_tabs_inn">
        <div id="details-div" class="require_wrap_details">
            	<c:if test="${requirement.requirementSubmissionStatus == 'REJECTED' }">
            	    <div class="anchor-inline">
	            	    <div class="rejected_Reason"> 
		            	    <h5>Rejected Reason:</h5>  
		            	    <div class="mb">${!empty requirement.rejectReasonForRequirement ? requirement.rejectReasonForRequirement : ' No DATA '}</div>
	            	    </div> 
            	    </div> 
          	   </c:if>
			   <div id="rejectRequirementAlert" ></div>   
		       <div class="clearfix"> 
		           <div class="pull-left"> 
		              <h2>Assigned Members</h2>  
		           </div> 
		           <div class="pull-right"> 
			            <c:if test="${ (requirement.requirementSubmissionStatus =='NEW'  || requirement.requirementSubmissionStatus =='DRAFT' || requirement.requirementSubmissionStatus =='SUBMITTED'  || requirement.requirementSubmissionStatus  == 'APPROVED'  || requirement.requirementSubmissionStatus =='REJECTED'  ||  requirement.requirementSubmissionStatus  == 'OPEN' ||  requirement.requirementSubmissionStatus  == 'ONHOLD' ) && (isAdmin == 'true' || requirement.isLoginUserAsApprover  == 'true' || isManager  == 'true' || requirement.isLoginUserAsInitiator == 'true') }">
			            	<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_REQUIREMENT\')">  
			            		  <a href="<c:url value="/requirement?requirementUUID=${requirementUUID}"/>"><button  class="btn btn-primary btn-sm" type="button">Edit</button></a> 
			            	</sec:authorize>
			            </c:if>
			            <c:if test="${(isAdmin == 'true' && requirement.requirementSubmissionStatus  != 'DRAFT' && requirement.requirementSubmissionStatus != 'ONHOLD' && requirement.requirementSubmissionStatus != 'CLOSED'  && requirement.requirementSubmissionStatus != 'CANCEL' &&  requirement.requirementSubmissionStatus != 'APPROVED' &&  requirement.requirementSubmissionStatus != 'REJECTED'  &&  requirement.requirementSubmissionStatus != 'OPEN' && requirement.requirementSubmissionStatus  !='NEW' && requirement.requirementSubmissionStatus  != 'UNFILLEDCLOSED' && requirement.requirementSubmissionStatus  != 'FILLEDCLOSED' )  || (  !empty requirement.requirementApproverHierarchyStatus && requirement.requirementSubmissionStatus  !='DRAFT' && requirement.requirementSubmissionStatus != 'ONHOLD'  && requirement.requirementSubmissionStatus != 'CANCEL' &&  requirement.requirementApproverHierarchyStatus !='APPROVED' &&  requirement.requirementApproverHierarchyStatus != 'REJECTED'  &&  requirement.requirementApproverHierarchyStatus != 'OPEN') }">
			            	  <button  class="btn btn-success mr ml btn-sm" type="button" id = "approverbut" onClick ="updateReuqirementStatus('APPROVED');hideButtonOnClick();"  >Approve</button> 
         		              <button  class="btn btn-danger btn-sm" id = "rejectedbut" type="button" onClick ="rejectRequirementAlertMessage('REJECTED');hideButtonOnClick();">Reject</button>
			            </c:if>
					</div> 
			     </div> 
			            
                 <div class="clearfix"> 
                 	<div class="clearfix row"> 
              			<div class="col-md-12 divskill divsAssigned pull-left"> 
          				<div id="assignedMemberList"></div> 
          				
          				<%-- <div class="clearfix mt5 mb">
						    <table class="table table-responsive table-hover clearfix mb">
							    <thead>
								    <tr>
									    <th>Name</th>
									    <th>Role</th>
									    <th>Team</th>
								    </tr>
							    </thead>
							    <tbody>
							    	<tr>
								    	<c:forEach var ="assignTo" items="${requirement.requirementAssignTo}" varStatus="reqStatus">
									        <td><div class="tbldata-dotdot">${assignTo.assignedUserName}</div></td>
									    </c:forEach>
						    			<td><div class="tbldata-dotdot">'+report.assignedUserName+'</div></td>
						    			<td><div class="tbldata-dotdot">'+report.assignedUserName+'</div></td>
						    		</tr>
							    <tbody>
							</table>
          				 </div> --%>
          				
          				
          				
				        <h1>Description:</h1> 
				        <div class="RDdetails">${!empty requirement.description ? requirement.description : ' No DATA '}</div>
			            <h1>Skills:</h1>
			            <div class="RDdetails">${!empty requirement.skills ? requirement.skills : ' No DATA '}</div>
			            <h2 class="RPadT"> Job Details:</h2> 
			            <div class="require_one">
				            <div class="require_wrap_details_cta"> <b>No. of positions:</b><span>${!empty requirement.positions ? requirement.positions : ' -- '}</span>
				            	<div class="clearfix"></div> 
				            </div> 
				            <div class="require_wrap_details_cta"> <b>Industry:</b><span>${!empty requirement.industryName ? requirement.industryName : ' -- '}</span>
				            	<div class="clearfix"></div> 
					        </div> 
					        
					        <div class="require_wrap_details_cta require_wrap_details_view require_uploadfile"> <b>Attachment:</b><span>${!empty requirement.fileName ? requirement.fileName : ' -- '}</span>
					            <c:if test="${!empty requirement.fileName}">
					            	<a class="glyphicon glyphicon-download-alt" target = "_blank" href="${requirement.fileURL}"></a><a class="fa fa-eye ml10" onClick="viewFile('${requirement.fileURL}','${requirement.fileName}','${requirement.fileContentType}');"></a>
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
				            <div class="clearfix"></div> 
			            </div> 

			      <c:if test="${requirement.isClientJob == 'Y'}">
		                <h2>Client Details:</h2> 
		                <div class="require_one"> 
		            	<div class="require_wrap_details_cta"> <b>Client Contact:</b><span>${!empty requirement.spocName ? requirement.spocName : ' -- '}</span>
			            	<div class="clearfix"></div> 
		                </div>
		                
		                <div class="require_wrap_details_cta"> <b>Client Name:</b><span>${!empty requirement.clientName ? requirement.clientName : ' -- '}</span>
			            	<div class="clearfix"></div> 
			            </div>  
			            
			            <c:choose>
						    <c:when test="${!empty requirement.parentRequirement}">
						    	<c:choose>
						    		<c:when test="${requirement.clientCurrencyCode == 'INR' && !empty requirement.clientRate && !empty requirement.clientRateType}">
						            	<div class="require_wrap_details_cta"> <b>Bill rate/salary:</b> <span><i class="fa fa-inr"></i> ${requirement.clientRate}  ${requirement.clientRateType}</span>
							            	<div class="clearfix"></div> 
					               		</div> 
							    	</c:when>
							    	<c:when test="${requirement.clientCurrencyCode == 'EUR' && !empty requirement.clientRate && !empty requirement.clientRateType}">
						            	<div class="require_wrap_details_cta"> <b>Bill rate/salary:</b> <span><i class="fa fa-eur"></i> ${requirement.clientRate}  ${requirement.clientRateType}</span>
							            	<div class="clearfix"></div> 
					               		</div> 
							    	</c:when>
							    	<c:when test="${requirement.clientCurrencyCode == 'GBP' && !empty requirement.clientRate && !empty requirement.clientRateType}">
						            	<div class="require_wrap_details_cta"> <b>Bill rate/salary:</b> <span><i class="fa fa-gbp"></i> ${requirement.clientRate}  ${requirement.clientRateType}</span>
							            	<div class="clearfix"></div> 
					               		</div> 
							    	</c:when>
							    	<c:when test="${requirement.clientCurrencyCode == 'USD' && !empty requirement.clientRate && !empty requirement.clientRateType}">
						            	<div class="require_wrap_details_cta"> <b>Bill rate/salary:</b> <span><i class="fa fa-dollar"></i> ${requirement.clientRate}  ${requirement.clientRateType}</span>
							            	<div class="clearfix"></div> 
					               		</div> 
							    	</c:when>
							    	<c:when test="${requirement.clientCurrencyCode == 'AUD' && !empty requirement.clientRate && !empty requirement.clientRateType}">
						            	<div class="require_wrap_details_cta"> <b>Bill rate/salary:</b> <span><i class="fa fa-dollar"></i> ${requirement.clientRate}  ${requirement.clientRateType}</span>
							            	<div class="clearfix"></div> 
					               		</div> 
							    	</c:when>
							    	<c:when test="${requirement.clientCurrencyCode == 'SGD' && !empty requirement.clientRate && !empty requirement.clientRateType}">
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
				          	</c:when>
						    <c:otherwise>
						    	<c:choose>
						    		<c:when test="${requirement.clientCurrencyCode == 'INR' && !empty requirement.rate}">
						    			<div class="require_wrap_details_cta"> <b>Bill rate/salary:</b> <span><i class="fa fa-inr"></i> ${requirement.clientRate}  ${requirement.clientRateType}</span>
							    			<div class="clearfix"></div> 
							            </div>
						    		</c:when>
						    		<c:when test="${requirement.clientCurrencyCode == 'EUR' && !empty requirement.rate}">
						    			<div class="require_wrap_details_cta"> <b>Bill rate/salary:</b> <span><i class="fa fa-eur"></i> ${requirement.clientRate}  ${requirement.clientRateType}</span>
							    			<div class="clearfix"></div> 
							            </div>
						    		</c:when>
						    		<c:when test="${requirement.clientCurrencyCode == 'GBP' && !empty requirement.rate}">
						    			<div class="require_wrap_details_cta"> <b>Bill rate/salary:</b> <span><i class="fa fa-gbp"></i> ${requirement.clientRate}  ${requirement.clientRateType}</span>
							    			<div class="clearfix"></div> 
							            </div>
						    		</c:when>
						    		<c:when test="${requirement.clientCurrencyCode == 'USD' && !empty requirement.rate}">
						    			<div class="require_wrap_details_cta"> <b>Bill rate/salary:</b> <span><i class="fa fa-dollar"></i> ${requirement.clientRate}  ${requirement.clientRateType}</span>
							    			<div class="clearfix"></div> 
							            </div>
						    		</c:when>
						    		<c:when test="${requirement.clientCurrencyCode == 'AUD' && !empty requirement.rate}">
						    			<div class="require_wrap_details_cta"> <b>Bill rate/salary:</b> <span><i class="fa fa-dollar"></i> ${requirement.clientRate}  ${requirement.clientRateType}</span>
							    			<div class="clearfix"></div> 
							            </div>
						    		</c:when>
						    		<c:when test="${requirement.clientCurrencyCode == 'SGD' && !empty requirement.rate}">
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
			              </div>
			         </c:if>
			         <div class="clearfix"></div> 
			          
			         <h2>Internal Details:</h2> 
			         <div class="require_one"> 
			         	 <c:if test="${requirement.isClientJob  == 'N'}">
			         		 <div class="require_wrap_details_cta"> <b>Application form:</b><span>${!empty requirement.clientSubmissionTemplate ? requirement.clientSubmissionTemplate : ' -- '}</span></div>
			         	 </c:if>
					     <div class="clearfix"></div> 
					           
			         	 <div class="require_wrap_details_cta"> <b>Release to all Users:</b><span>${requirement.releaseJobToAllUsers  == 'Y' ? 'YES' : 'NO'}</span></div>
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
                </div> 							            
           
                   <div class="clearfix"></div> 
                 </div> 
                 <div class="clearfix"></div> 
           
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
		  <li class="li-vendor">
		   <c:if test="${requirement.requirementSubmissionStatus != 'SUBMITTED' && requirement.requirementSubmissionStatus != 'CLOSED' && requirement.requirementSubmissionStatus != 'REJECTED' &&  requirement.requirementSubmissionStatus != 'CANCEL' && requirement.requirementSubmissionStatus != 'DRAFT' && requirement.requirementSubmissionStatus != 'ONHOLD' && requirement.requirementSubmissionStatus != 'FILLEDCLOSED'  && requirement.requirementSubmissionStatus != 'UNFILLEDCLOSED' }">
		        <a onclick="getActions();" href="javascript:void(0)" class="action"> Action <i  class="fa fa-caret-down" ></i></a>
		   </c:if>
			    <div class="action-pop">
					<span class="width175">
							<sec:authorize access="hasAnyRole('ROLE_ADD_APPLICANT')">
							      <a href="javascript:void(0)" title="" onclick="getAddApplicant('${requirement.id}');" class="add-applicant"><i class="fa fa-plus"></i>&nbsp;Add Applicant</a>
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
</div>

<script>
function hideButtonOnClick(){
		$('#approverbut').hide();
		$('#rejectedbut').hide();
}
</script>
