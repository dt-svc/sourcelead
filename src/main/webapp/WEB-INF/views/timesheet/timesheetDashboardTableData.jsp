<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

				<c:forEach var="submission" items="${timesheetApprovalDTO.timesheetSubmission}" varStatus="approvalStatus">
									<tr id="row${submission.dashboardDateList}" role="row" class="odd">
										<td class="sorting_1">
											<div class="time_appr_user">
												<c:if test="${approvalStatus.index == 0}">
													<input type="hidden" id="totalRows" name="totalRows" value="${timesheetApprovalDTO.totalRows}">
					       							<input type="hidden" id="pageCount" name="pageCount" value="${timesheetApprovalDTO.pageCount}">
				       							</c:if>
												<img id="userImage" onerror="predefineProfileImage(this);"	src="${submission.profileImageFileUrl}" alt="Profile Image" class="img-circle img-responsive">
													<span><b title="${submission.employeeName}">${submission.employeeName}</b>
														<b title="${submission.employeeEmail}">${submission.employeeEmail}</b>
														<b title="${submission.employeePhoneNo}">${submission.employeePhoneNo}</b></span>
											</div>
										</td>
										<td>
											<div class="tbldata-dotdot-sm">
												<span title="${submission.project}">${submission.project}</span>
											</div>
										</td>
										<td>
											<div><a href="<c:url value="/clientDashboard?orgRelationUUID=${requirement.orgRelationUUID}"/>"> ${submission.clientName} </a></div>
										</td>
										<td>
											<div>${submission.dashboardInvoiceFrequency}</div>
										</td>
										<td>
											<div id="datefrequency${submission.projectMemberId}">
												<span class="res_date"><i class="fa fa-calendar">
												</i>${submission.dashboardDateList}</span>
											</div>
										</td>
										<td>
											<div class="sm">
												<a href="#"	onclick="timesheetApprovalPopUpByProjectMemberId();">${submission.totalHours}/${submission.estimatedHours}</a>
											</div>
										</td>
										<td>
										<c:if test="${!empty submission.submissionStatus && submission.submissionStatus == 'DRAFT'}">
										
										<div class="sm" id="status${submission.projectMemberId}">NEW</div>
										</c:if>
										
										<c:if test="${!empty submission.submissionStatus && submission.submissionStatus != 'DRAFT'}">
										
										<div class="sm" id="status${submission.projectMemberId}">${submission.submissionStatus}</div>
										</c:if>
										
										</td>
										<td>
											<div class="sm text-center">
											<c:if test="${!empty submission.submissionApprovalStatus && submission.submissionApprovalStatus == 'SUBMITTED FOR APPROVAL'}">
											<i class="fa fa-check" title="Approve" alt="Approve" onclick=""></i>|<i
											class="fa fa-times" title="Reject" alt="Rejected" onclick=";"></i>
											</c:if>
												<%-- <h3 title = "${submission.submissionApprovalStatus}">${submission.submissionApprovalStatus}</h3> --%>
												<c:if  test="${!empty submission.submissionApprovalStatus && submission.submissionApprovalStatus == 'APPROVED'}">
												<h3 title = "${submission.submissionApprovalStatus}">${submission.submissionApprovalStatus}</h3>
												<b class="Rname" title="${submission.actionPerformedBy}">By : ${submission.actionPerformedBy}</b>
												</c:if>
												<c:if  test="${!empty submission.submissionApprovalStatus && submission.submissionApprovalStatus == 'REJECTED'}">
												<h3 class ="reje_color" title = "${submission.submissionApprovalStatus}">${submission.submissionApprovalStatus}</h3>
												<i class="glyphicon glyphicon-comment" id=""	onclick="rejectedMessage();">
												</i>
												<b class="Rname" title="${submission.actionPerformedBy}">By : ${submission.actionPerformedBy}</b>
												</c:if>
												<c:if  test="${!empty submission.submissionApprovalStatus && submission.submissionApprovalStatus == 'NEW'}">
												<b class="Rname" title="">---</b>
												</c:if>
												
											</div>
										</td>
									</tr>
								</c:forEach>