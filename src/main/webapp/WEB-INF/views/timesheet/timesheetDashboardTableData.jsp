<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

				<c:forEach var="submission" items="${timesheetApprovalDTO.timesheetSubmission}">
									<tr id="row${submission.dashboardDateList}" role="row" class="odd">
										<td class="sorting_1">
											<div class="time_appr_user">
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
											<div>${submission.clientName}</div>
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
											<div class="sm" id="status${submission.projectMemberId}">${submission.submissionStatus}</div>
										</td>
										<td>
											<div class="sm text-center">
												<h3>${submission.submissionApprovalStatus}</h3>
												<b class="Rname" title="${submission.actionPerformedBy}">By : ${submission.actionPerformedBy}</b>
											</div>
										</td>
									</tr>
								</c:forEach>