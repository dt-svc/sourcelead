<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

    <!-- Showing checkbox for submitted timesheets -->
	<c:if test="${timesheetSubmission.submissionStatus eq 'SUBMITTED FOR APPROVAL'}">
		<td>
			<input class="checkBoxForSubmitForApproval"  name="checkBoxForSubmitForApproval" type="checkbox" value="${timesheetSubmission.projectMemberId}!${timesheetSubmission.actualStartDate}!${timesheetSubmission.actualEndDate}!${timesheetSubmission.tdSpanLength}!'approval'" onclick="displayAcceptRejectButtonOnHedder();">
		</td>
	</c:if>
	
	<!-- disable checkbox for non-submitted timesheets -->
	<c:if test="${timesheetSubmission.submissionStatus ne 'SUBMITTED FOR APPROVAL'}">
		<td>
		     <small class="positionRelative">
			<i class="fa fa-ban text-danger"></i>
			</small>
		</td>
	</c:if>
	<td class="sorting_1">
		<div class="time_appr_user">
			<img id="userImage" onerror="predefineProfileImage(this);" src="${timesheetSubmission.profileImageFileUrl}" alt="Profile Image" class="img-circle img-responsive"> 
			<span>
				<b title="${timesheetSubmission.employeeName}">${timesheetSubmission.employeeName}</b>
				<b title="${timesheetSubmission.employeeEmail}">${timesheetSubmission.employeeEmail}</b>
				<b title="${timesheetSubmission.employeePhoneNo}">${timesheetSubmission.employeePhoneNo}</b>
			</span>
		</div>
	</td>
	<c:if test="${!empty timesheetSubmission.tableFor && timesheetSubmission.tableFor == 'approver'}">
	   <td><span><b title="${timesheetSubmission.designation}">${timesheetSubmission.designation}</b></span></td>
    </c:if>
	<td><div class="tbldata-dotdot-sm">
			<span><b title="${timesheetSubmission.project}">${timesheetSubmission.project}</b></span>
		</div></td>
		
	<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CLIENT\',\'ROLE_VIEW_EDIT_CLIENT\')">	
		<c:if test="${not empty timesheetSubmission.orgRelationUUID}">
			<td><div title="${timesheetSubmission.clientName}"><a class="aLink" href="${SourceLeadWeb_vmsUrl}/clientDashboard?orgRelationUUID=${timesheetSubmission.orgRelationUUID}">${timesheetSubmission.clientName}</a></div></td>
		</c:if>
		<c:if test="${empty timesheetSubmission.orgRelationUUID}">
			<td><div title="${timesheetSubmission.clientName}">${timesheetSubmission.clientName}</div></td>
		</c:if>
	</sec:authorize>
	
	<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_CLIENT\',\'ROLE_VIEW_EDIT_CLIENT\')">	
	<td><div title="${timesheetSubmission.clientName}">${timesheetSubmission.clientName}</div></td>
	</sec:authorize>
	
	
    <c:if test="${!empty timesheetSubmission.tableFor && timesheetSubmission.tableFor == 'approver'}">
		<c:forEach var="weekTot" items="${timesheetSubmission.weeekTotal}" varStatus="status"> 
				<c:if test="${!empty timesheetSubmission.submissionTotal}">
					<td><div class="sm">${timesheetSubmission.submissionTotal[status.index]}/${weekTot}</div></td>
				</c:if>
				<c:if test="${empty timesheetSubmission.submissionTotal}">
					<td><div class="sm">0.0/${weekTot}</div></td>
				</c:if>
		</c:forEach>
	</c:if>	 
	<c:if test="${!empty timesheetSubmission.tableFor && timesheetSubmission.tableFor == 'dashboard'}">
	         <td> <div>${timesheetSubmission.dashboardInvoiceFrequency}</div></td>
	         <td> <div id="datefrequency${timesheetSubmission.projectMemberId}"><span class="res_date"><i class="fa fa-calendar"></i>${timesheetSubmission.dashboardDateList}</span></div></td>
	        <td>
				<div class="sm">
					<a href="#" onclick="timesheetApprovalPopUpByProjectMemberId('${timesheetSubmission.currentTimeFrequency}','${timesheetSubmission.projectId}','${timesheetSubmission.projectMemberId}','${timesheetSubmission.masterCandidateId}','${timesheetSubmission.totalHours}','approver');">${timesheetSubmission.totalHours}/${timesheetSubmission.estimatedHours}</a>
				</div>
		    </td>
	</c:if>
    <c:if test="${!empty timesheetSubmission.tableFor && timesheetSubmission.tableFor == 'approver'}">
		<td>
			<div class="sm">
				<a href="#" id="popuplink" onclick="timesheetApprovalPopUpByProjectMemberId('${timesheetApprovalDTO.weekFrequency}','${timesheetSubmission.projectId}','${timesheetSubmission.projectMemberId}','${timesheetSubmission.masterCandidateId}','${timesheetSubmission.totalHours}','approver');">${timesheetSubmission.totalHours}/${timesheetSubmission.estimatedHours}</a>
			</div>
		</td>
	</c:if>
	<td><div class="sm" title="${timesheetSubmission.submissionStatus}" id="status${timesheetSubmission.projectMemberId}">${timesheetSubmission.submissionStatus}</div></td>
<c:if test="${!empty timesheetSubmission.submissionStatus && timesheetSubmission.submissionStatus == 'SUBMITTED FOR APPROVAL'}">
<td>
	<div class="sm text-center" id="action${timesheetSubmission.projectMemberId}">
		<i class="fa fa-check" title="Approve" alt="Approve" onclick="approveSubmissionByProjectMemberId('${timesheetSubmission.projectMemberId}','${timesheetSubmission.actualStartDate}','${timesheetSubmission.actualEndDate}','${timesheetSubmission.tdSpanLength}','approval');"></i>|<i
			class="fa fa-times" title="Reject" alt="Rejected" onclick="rejecteSubmissionByProjectMemberId('${timesheetSubmission.projectMemberId}','${timesheetSubmission.actualStartDate}','${timesheetSubmission.actualEndDate}','${timesheetSubmission.tdSpanLength}','approval');"></i>
	</div>
</td>
</c:if>

<c:if  test="${!empty timesheetSubmission.submissionStatus && timesheetSubmission.submissionStatus == 'APPROVED'}">
	<td>
		<div class="sm text-center">
			<h3>${timesheetSubmission.submissionStatus}</h3>
			<b class="Rname" title="${timesheetSubmission.actionPerformedBy}">By : ${timesheetSubmission.actionPerformedBy}</b>
		</div>
	</td>
</c:if>

<c:if  test="${!empty timesheetSubmission.submissionStatus && timesheetSubmission.submissionStatus == 'REJECTED'}">
	<td>
		<div class="sm text-center">
			<h3 class="reje_color">${timesheetSubmission.submissionStatus}</h3>
			<i class="glyphicon glyphicon-comment" id="${timesheetSubmission.projectMemberId}"	onclick="rejectedMessage();">
				<div class="app_error_mes errorOverightDiv"	id="rejectedMessage${timesheetSubmission.projectMemberId}">${timesheetSubmission.rejectedReason}</div>
			</i> 
			<b class="Rname" title="${timesheetSubmission.actionPerformedBy}">By : ${timesheetSubmission.actionPerformedBy}</b>
		</div>
	</td>
</c:if>
<c:if  test="${!empty timesheetSubmission.submissionStatus && timesheetSubmission.submissionStatus == 'NEW'}">	
	<td>
		<div  class="sm">--</div>
	</td>
</c:if>

<script>
  $(document).ready(function () {
	  if('${AWKWARD}' == "true"){
		   window.location.href =  "/errorPage";
	  }
	});
</script>
