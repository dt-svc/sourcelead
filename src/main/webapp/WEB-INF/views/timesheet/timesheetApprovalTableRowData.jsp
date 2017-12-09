<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


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
	<td><span><b title="${timesheetSubmission.designation}">${timesheetSubmission.designation}</b></span></td>
	<td><div class="tbldata-dotdot-sm">
			<span><b title="${timesheetSubmission.project}">${timesheetSubmission.project}</b></span>
		</div></td>
	<td><div title="${timesheetSubmission.clientName}">${timesheetSubmission.clientName}</div></td>
	
	<c:forEach var="weekTot" items="${timesheetSubmission.weeekTotal}" varStatus="status"> 
			<c:if test="${!empty timesheetSubmission.submissionTotal}">
				<td><div class="sm">${timesheetSubmission.submissionTotal[status.index]}/${weekTot}</div></td>
			</c:if>
			<c:if test="${empty timesheetSubmission.submissionTotal}">
				<td><div class="sm">0.0/${weekTot}</div></td>
			</c:if>
	</c:forEach>	
	<td>
	<input type="hidden" name="totalHours" id="totalHours" value="${timesheetSubmission.totalHours}">
		<div class="sm">
			<a href="#" onclick="timesheetApprovalPopUpByProjectMemberId('${timesheetApprovalDTO.weekFrequency}','${timesheetSubmission.projectId}','${timesheetSubmission.projectMemberId}','${timesheetSubmission.masterCandidateId}','approver');">${timesheetSubmission.totalHours}/${timesheetSubmission.estimatedHours}</a>
		</div>
	</td>
	<td><div class="sm" title="New" id="status${timesheetSubmission.projectMemberId}">${timesheetSubmission.submissionStatus}</div></td>
<c:if test="${!empty timesheetSubmission.submissionStatus && timesheetSubmission.submissionStatus == 'submitted for approval'}">
<td>
	<div class="sm text-center" id="action${timesheetSubmission.projectMemberId}">
		<i class="fa fa-check" title="Approve" alt="Approve" onclick="approveSubmissionByProjectMemberId('${timesheetSubmission.projectMemberId}','${timesheetSubmission.actualStartDate}','${timesheetSubmission.actualEndDate}','${timesheetSubmission.tdSpanLength}','approval');"></i>|<i
			class="fa fa-times" title="Reject" alt="Rejected" onclick="rejecteSubmissionByProjectMemberId('${timesheetSubmission.projectMemberId}','${timesheetSubmission.actualStartDate}','${timesheetSubmission.actualEndDate}','${timesheetSubmission.tdSpanLength}','approval');"></i>
	</div>
</td>
</c:if>

<c:if  test="${!empty timesheetSubmission.submissionStatus && timesheetSubmission.submissionStatus == 'approved'}">
	<td>
		<div class="sm text-center">
			<h3>${timesheetSubmission.submissionStatus}</h3>
			<b class="Rname" title="${timesheetSubmission.actionPerformedBy}">By : ${timesheetSubmission.actionPerformedBy}</b>
		</div>
	</td>
</c:if>

<c:if  test="${!empty timesheetSubmission.submissionStatus && timesheetSubmission.submissionStatus == 'rejected'}">
	<td>
		<div class="sm text-center">
			<h3 class="reje_color">${timesheetSubmission.submissionStatus}</h3>
			<i class="glyphicon glyphicon-comment" id="928"	onclick="rejectedMessage();">
				<div class="app_error_mes errorOverightDiv"	id="rejectedMessage${timesheetSubmission.projectMemberId}">${timesheetSubmission.rejectedReason}</div>
			</i> 
			<b class="Rname" title="${timesheetSubmission.actionPerformedBy}">By : ${timesheetSubmission.actionPerformedBy}</b>
		</div>
	</td>
</c:if>
<c:if  test="${!empty timesheetSubmission.submissionStatus && timesheetSubmission.submissionStatus == 'new'}">	
	<td>
		<div  class="sm">--</div>
	</td>
</c:if>
