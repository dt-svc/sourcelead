<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div id="timesheetSubmissionStatusDiv" class="timeNewSe timBtnWidth">
			   <!--  <input type="hidden" name="timesheetSubmissionStatus" id="timesheetSubmissionStatus" value="new">
				<input type="hidden" name="isSuperUser" id="isSuperUser" value="false">
				<input type="hidden" name="totalHours" id="totalHours" value="0"> -->
				<c:if 	test="${timesheetSubmissionDTO.timesheetStatus == 'REJECTED'}">
					<b class="proj_closed">${timesheetSubmissionDTO.timesheetStatus}</b>
					<i class="glyphicon glyphicon-comment rejectedHover"> 
					<div class="app_error_mes" id="rejectedMessage">${timesheetSubmissionDTO.rejectedReason}</div> </i>
				</c:if>
				<c:if 	test="${timesheetSubmissionDTO.timesheetStatus == 'APPROVED'}">
					<b class="proj_active">${timesheetSubmissionDTO.timesheetStatus}</b>
				</c:if>
				<c:if 	test="${timesheetSubmissionDTO.timesheetStatus == 'NEW'}">
					<b class="proj_new">${timesheetSubmissionDTO.timesheetStatus}</b>
				</c:if>
				<c:if 	test="${timesheetSubmissionDTO.timesheetStatus == 'DRAFT'}">
					<b class="proj_draft">${timesheetSubmissionDTO.timesheetStatus}</b>
				</c:if>
				<c:if 	test="${timesheetSubmissionDTO.timesheetStatus == 'SUBMITTED'}">
					<b class="proj_process">${timesheetSubmissionDTO.timesheetStatus}</b>
					<b class="proj_withdraw">MODIFY</b>
				</c:if>
				
</div>
<div class="clearfix"></div>
<div class="timesheetNewTabs">
	<div class="project_side_wrap_inn_tabs">
		<ul class="project-tabs-menu">
		   <c:forEach var="tsf" items="${timesheetEntryDetailsDTO.timeSubFrequencies}">
			   <li class="${tsf.key ==  timesheetSubmissionDTO.timeSubFrequency ? 'current' : ''}">
					<a href="#" >${tsf.value}</a>
				</li>
		   </c:forEach>
			<div class="export dropdown">
				<p class="dropdown-toggle" data-toggle="dropdown">
					<i class="glyphicon glyphicon-export"></i>Export<i
						class="fa fa-caret-down"></i>
				</p>
				<ul id="exporrtEntries" class="dropdown-menu dropdown-menu-right">
					<li
						onclick="timesheetEntriesExport(&quot;04-12-2017&quot;,&quot;10-12-2017&quot;,928,&quot;pdf&quot;,&quot;NEW&quot;);"><i
						class="fa fa-file-pdf-o text-danger"></i>&nbsp;PDF</li>
					<li
						onclick="timesheetEntriesExport(&quot;04-12-2017&quot;,&quot;10-12-2017&quot;,928,&quot;excel&quot;,&quot;NEW&quot;);"><i
						class="fa fa-file-excel-o text-primary"></i>&nbsp;Excel</li>
					<li class="hidden">Word<i class="fa fa-file-word-o"></i></li>
				</ul>
			</div>
			<div class="clearfix"></div>
		</ul>
		<div id="timesheetDivByTab">
			<div id="timesheetDivByTab">
				<div class="project_tabs">
					<div id="tab-1" class="project_tabs_inn" style="display: block;">
						<div class="timesheet_tableNew">
							<input type="hidden" name="tabStartDate" id="tabStartDate"
								value="04-12-2017"><input type="hidden"
								name="tabEndDate" id="tabEndDate" value="10-12-2017">
							<table id="timesheet" cellpadding="0" cellspacing="0">
								<tbody>
									<tr>
										<th>Tasks</th>
										<th>Type</th>
										<th>Mon<i>04-12-2017</i></th>
										<th>Tue<i>05-12-2017</i></th>
										<th>Wed<i>06-12-2017</i></th>
										<th>Thu<i>07-12-2017</i></th>
										<th>Fri<i>08-12-2017</i></th>
										<th>Sat<i>09-12-2017</i></th>
										<th>Sun<i>10-12-2017</i></th>
									</tr>
									<input type="hidden" name="submissionId"
										class="initiateTimesheetMax" id="submissionId" value="16384">
									<input type="hidden" name="maxRowNumber"
										class="initiateTimesheetMax" id="maxRowNumber" value="1">
									<input type="hidden" name="1timesheetEntriesId"
										id="1timesheetEntriesId" value="10763">
									<tr id="timesheetRow1">
										<td>
											<div>
												<p>Work From Home</p>
											</div>
										</td>
										<td>
											<div>
												<span id="1task1IdBillableTypeId">Billable</span>
											</div>
										</td>
										<td>
											<div>
												<input type="text" disabled="" onblur="editMe(this);"
													id="1timeSheetDay1Id" name="1timeSheetDay1Id"
													class="inline_field" value="00:00" placeholder="HH:MM">
												<div class="timesheet_comm">
													<i class="glyphicon glyphicon-comment"
														onclick="timesheetComment(&quot;1comment1Id&quot;);"></i>
													<span class="teskcomment" id="1comment1Id"><textarea
															disabled="" name="1comment1Id" id="1textArea1Id"></textarea></span>
												</div>
											</div>
										</td>
										<td>
											<div>
												<input type="text" disabled="" onblur="editMe(this);"
													id="1timeSheetDay2Id" name="1timeSheetDay2Id"
													class="inline_field" value="00:00" placeholder="HH:MM">
												<div class="timesheet_comm">
													<i class="glyphicon glyphicon-comment opactiy_0"
														onclick="timesheetComment(&quot;1comment2Id&quot;);"></i>
													<span class="teskcomment" id="1comment2Id"><textarea
															disabled="" name="1comment2Id" id="1textArea2Id"></textarea></span>
												</div>
											</div>
										</td>
										<td>
											<div>
												<input type="text" disabled="" onblur="editMe(this);"
													id="1timeSheetDay3Id" name="1timeSheetDay3Id"
													class="inline_field" value="00:00" placeholder="HH:MM">
												<div class="timesheet_comm">
													<i class="glyphicon glyphicon-comment opactiy_0"
														onclick="timesheetComment(&quot;1comment3Id&quot;);"></i>
													<span class="teskcomment" id="1comment3Id"><textarea
															disabled="" name="1comment3Id" id="1textArea3Id"></textarea></span>
												</div>
											</div>
										</td>
										<td>
											<div>
												<input type="text" disabled="" onblur="editMe(this);"
													id="1timeSheetDay4Id" name="1timeSheetDay4Id"
													class="inline_field" value="00:00" placeholder="HH:MM">
												<div class="timesheet_comm">
													<i class="glyphicon glyphicon-comment"
														onclick="timesheetComment(&quot;1comment4Id&quot;);"></i>
													<span class="teskcomment" id="1comment4Id"><textarea
															disabled="" name="1comment4Id" id="1textArea4Id"></textarea></span>
												</div>
											</div>
										</td>
										<td>
											<div>
												<input type="text" disabled="" onblur="editMe(this);"
													id="1timeSheetDay5Id" name="1timeSheetDay5Id"
													class="inline_field" value="00:00" placeholder="HH:MM">
												<div class="timesheet_comm">
													<i class="glyphicon glyphicon-comment"
														onclick="timesheetComment(&quot;1comment5Id&quot;);"></i>
													<span class="teskcomment" id="1comment5Id"><textarea
															disabled="" name="1comment5Id" id="1textArea5Id"></textarea></span>
												</div>
											</div>
										</td>
										<td>
											<div>
												<input type="text" disabled="" onblur="editMe(this);"
													id="1timeSheetDay6Id" name="1timeSheetDay6Id"
													class="inline_field" value="00:00" placeholder="HH:MM">
												<div class="timesheet_comm">
													<i class="glyphicon glyphicon-comment"
														onclick="timesheetComment(&quot;1comment6Id&quot;);"></i>
													<span class="teskcomment" id="1comment6Id"><textarea
															disabled="" name="1comment6Id" id="1textArea6Id"></textarea></span>
												</div>
											</div>
										</td>
										<td>
											<div>
												<input type="text" disabled="" onblur="editMe(this);"
													id="1timeSheetDay7Id" name="1timeSheetDay7Id"
													class="inline_field" placeholder="HH:MM" value="00:00">
												<div class="timesheet_comm">
													<i class="glyphicon glyphicon-comment"
														onclick="timesheetComment(&quot;1comment7Id&quot;);"></i>
													<span class="teskcomment" id="1comment7Id"><textarea
															disabled="" name="1comment7Id" id="1textArea7Id"></textarea></span>
												</div>
											</div>
										</td>
									</tr>
									<tr class="last_child">
										<td class="total_field">Total</td>
										<td></td>
										<td>
											<div class="alignDiv">
												<input type="text" disabled="" id="rowTotal1Id"
													name="rowTotal1Id" class="inline_field" value="HH:MM"
													placeholder="00:00">
											</div>
										</td>
										<td>
											<div class="alignDiv">
												<input type="text" disabled="" id="rowTotal2Id"
													name="rowTotal2Id" class="inline_field" value="HH:MM"
													placeholder="00:00">
											</div>
										</td>
										<td>
											<div class="alignDiv">
												<input type="text" disabled="" id="rowTotal3Id"
													name="rowTotal3Id" class="inline_field" value="HH:MM"
													placeholder="00:00">
											</div>
										</td>
										<td>
											<div class="alignDiv">
												<input type="text" disabled="" id="rowTotal4Id"
													name="rowTotal4Id" class="inline_field" value="HH:MM"
													placeholder="00:00">
											</div>
										</td>
										<td>
											<div class="alignDiv">
												<input type="text" disabled="" id="rowTotal5Id"
													name="rowTotal5Id" class="inline_field" value="HH:MM"
													placeholder="00:00">
											</div>
										</td>
										<td>
											<div class="alignDiv">
												<input type="text" disabled="" id="rowTotal6Id"
													name="rowTotal6Id" class="inline_field" value="HH:MM"
													placeholder="00:00">
											</div>
										</td>
										<td>
											<div class="alignDiv">
												<input type="text" disabled="" id="rowTotal7Id"
													name="rowTotal7Id" class="inline_field" value="HH:MM"
													placeholder="00:00">
											</div>
										</td>
									</tr>
								</tbody>
							</table>
							<div class="timesheet_bot">
								<div class="timesheet_week_comm">
									<input type="hidden" name="attcahmentMaxRowNumber"
										id="attcahmentMaxRowNumber" value="1">
									<div id="timesheetDocumentsId" class="timesheetDocs">
										<input type="hidden" name="isTimesheetAttachmentRequired"
											id="isTimesheetAttachmentRequired" value="N">
									</div>
									<div class="commentTime pull-right">
										<textarea name="weekComment" id="weekComment"
											placeholder="comment here"></textarea>
									</div>
								</div>
							</div>
						</div>
						<div class="timesheet_bot">
							<div class="timesheet_button">
								<div class="totalWrap pull-left">
									<ul>
										<li><span>Billable Hours</span><input type="text"
											name="grandBillableTotal" id="grandBillableTotal"
											value="00:00" placeholder="HH/HH" readonly=""></li>
										<li><span>Non-Billable Hours</span><input type="text"
											name="grandNonBillableTotal" id="grandNonBillableTotal"
											value="00:00" placeholder="HH/HH" readonly=""></li>
										<li><span>Total Hours</span><input type="text"
											id="grandTotal" value="00:00" placeholder="HH/HH" readonly=""></li>
										<div class="clearfix"></div>
									</ul>
								</div>
								<div class="clearfix"></div>
							</div>
							<div class="clearfix"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<input type="hidden" name="_ignore"
		style="border-color: rgb(51, 51, 51);">
</div>




<script>
$(document).ready(function() {
});
</script>

<script type="text/javascript"	src='resources/js/slead-common.js?staticTime=${staticTime}'></script>
