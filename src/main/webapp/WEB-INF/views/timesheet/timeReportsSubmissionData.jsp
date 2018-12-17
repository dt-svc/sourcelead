<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:forEach var="subDetails" items="${timesheetReportsDTO.timeReportsSubmissionDetails}" varStatus="subDetailsStatus">
<tr id="row${subDetails.projectMemberId}" role="row" class="odd">
	<td class="sorting_1">
		<div class="lg clearfix">
		     <c:if test="${subDetailsStatus.index == 0}">
		       <input type="hidden" id="totalRows" name="totalRows" value="${timesheetReportsDTO.totalRows}">
		       <input type="hidden" id="pageCount" name="totalRows" value="${timesheetReportsDTO.pageCount}">
		    </c:if>
			<div class="uimg">
				<img id="userImage" onerror="predefineProfileImage(this);" src="${subDetails.fileUrl}" alt="Profile Image" class="img-circle img-responsive">
			</div>
			<div class="Utext">
				<i title="${subDetails.fullName}">${subDetails.fullName}</i>
				<i title="${subDetails.email}">${subDetails.email}</i>
				<i title="Emp Type ${subDetails.designationType}">${subDetails.designationType}</i>
			</div>
			<div></div>
		</div>
	</td>
	<td><div class="md">
			<i title="${subDetails.projectName},${subDetails.vendorName}">${subDetails.projectName}<br>${subDetails.vendorName}</i></div></td>
	<td><div class="sm">${subDetails.frequrncy}</div></td>
	<td><div class="sm">
			<i title="${subDetails.rate}">${subDetails.rate}</i>
		</div></td>
	
	<c:forEach var="dateFrequency" items="${timesheetReportsDTO.dataTableDateList}"> 
	  <c:forEach var="reportTime" items="${subDetails.reportsTimeMap}" >
	  <c:if test="${reportTime.key == dateFrequency}">
			<td>
				<div class="week">
					<span class="${reportTime.value.approvalClass}"><d title="week-Total-Hours:${reportTime.value.weekTotalHours}">${reportTime.value.weekTotalHours}</d></span>
					<div class="progress">
						<div
							class="progress-bar progress-bar-striped active progress-bar-info"
							role="progressbar" style="width: 60%">
							<d title="Billable:${reportTime.value.weekBillabeHours}">${reportTime.value.weekBillabeHours}</d>
						</div>
						<div
							class="progress-bar progress-bar-striped active progress-bar-warning"
							role="progressbar" style="width: 40%">
							<d title="Non-Billable:${reportTime.value.weekNonBillableHours}">${reportTime.value.weekNonBillableHours}</d>
						</div>
					</div>
				</div>
			</td>
		</c:if>
		</c:forEach>
	</c:forEach> 
	<td>
		<div class="week">
			<span class="${subDetails.combinedApprovalClass}"><d title="Month-Total-Hours:${subDetails.monthTotalHours}">${subDetails.monthTotalHours}</d></span>
			<div class="progress">
				<div
					class="progress-bar progress-bar-striped active progress-bar-info"
					role="progressbar" style="width: 60%">
					<d title="Month-Billable-Hours:${subDetails.monthBillabeHours}">${subDetails.monthBillabeHours}</d>
				</div>
				<div
					class="progress-bar progress-bar-striped active progress-bar-warning"
					role="progressbar" style="width: 40%">
					<d title="Month-NonBillable-Hours:${subDetails.monthNonBillableHours}">${subDetails.monthNonBillableHours}</d>
				</div>
			</div>
		</div>
	</td>
	<td><div class="sm">
	<%-- <input type="text" value="${subDetails.approvalStatus}"> --%>
		<c:if test="${subDetails.approvalStatus == 'Action Required'}">
			<b class="action" title="ACTION REQUIRED">ACTION REQUIRED</b>
		</c:if>
		<c:if test="${subDetails.approvalStatus == 'NEW'}">
			<b class="bgGReen" title="NEW">NEW</b>
		</c:if>
		<c:if test="${subDetails.approvalStatus == 'Approved'}">
			<b class="proj_active" title="APPROVED">APPROVED</b>
		</c:if>
		<c:if test="${subDetails.approvalStatus == 'Rejected'}">
			<b class="proj_closed" title="REJECTED">REJECTED</b>
		</c:if>
		<c:if test="${subDetails.approvalStatus == 'Submitted'}">
			<b class="proj_process" title="SUBMITTED FOR APPROVAL">SUBMITTED FOR APPROVAL</b>
		</c:if>
		</div></td>
	<td>
		<div class="week">
			<span class="draft"><d title="ProjectToDate-Total-Hours:${subDetails.projectToDateTotal}">${subDetails.projectToDateTotal}</d></span>
			<div class="progress">
				<div
					class="progress-bar progress-bar-striped active progress-bar-info"
					role="progressbar" style="width: 60%">
					<d title="ProjectToDate-Billable-Hours:${subDetails.projectToDateBillableTotal}">${subDetails.projectToDateBillableTotal}</d>
				</div>
				<div
					class="progress-bar progress-bar-striped active progress-bar-warning"
					role="progressbar" style="width: 40%">
					<d title="ProjectToDate-NonBillable-Hours:${subDetails.projectToDateNonBillableTotal}">${subDetails.projectToDateNonBillableTotal}</d>
				</div>
			</div>
		</div>
	</td>
	<td>
		<div class="week">
			<span class="draft"><d title="PreMonth-Total-Hours:${subDetails.lastMonthTotal}">${subDetails.lastMonthTotal}</d></span>
			<div class="progress">
				<div
					class="progress-bar progress-bar-striped active progress-bar-info"
					role="progressbar" style="width: 60%">
					<d title="PreMonth-Billable-Hours:${subDetails.lastMonthBillableTotal}">${subDetails.lastMonthBillableTotal}</d>
				</div>
				<div
					class="progress-bar progress-bar-striped active progress-bar-warning"
					role="progressbar" style="width: 40%">
					<d title="PreMonth-NonBillable-Hours:${subDetails.lastMonthNonBillableTotal}">${subDetails.lastMonthNonBillableTotal}</d>
				</div>
			</div>
		</div>
	</td>
</tr>
</c:forEach>

<script>
  $(document).ready(function () {
	  if('${AWKWARD}' == "true"){
		   window.location.href =  "/errorPage";
	  }
	});
</script>






