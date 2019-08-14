<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script src="/resources/js/jquery.maskedinput-1.3.min.js"></script>

<!-- hidden div for timesheet-entry 24 hour's validation  -->
<div class="time_pop" id="timeSheetErrorMsg"></div>

<!-- hidden div for loading -->
<div class='data_loading' id="loadTime"></div>

<!-- ######################################## projectmember(employee) information section ######################################## -->
<div class="pull-left timeAppUse-show col-md-6">
	    <span class="pull-left">
	      <img id="userImage" onerror="predefineProfileImage(this);" src="${timesheetEntryDetailsDTO.profileImageFileUrl}" alt="Profile Image"> 
	     </span>
		<ul class="col-md-9 margin-top0 list-unstyled padding_left">
			<li>
			<c:if test="${not empty timesheetEntryDetailsDTO.employeeName}">
				<input class="timeUsName padding_left" type="text" readonly="readonly" value="${timesheetEntryDetailsDTO.employeeName}">
			</c:if>
			<c:if test="${empty timesheetEntryDetailsDTO.employeeName}">
				<input class="timeUsName padding_left" type="text" readonly="readonly" value="--">
			</c:if>
			</li>
			<li>
			<c:if test="${not empty timesheetEntryDetailsDTO.employeeEmail}">
				<i class="fa fa fa-envelope" title="Email"></i><input type="text" readonly="readonly" value="${timesheetEntryDetailsDTO.employeeEmail}">
			</c:if>
			<c:if test="${empty timesheetEntryDetailsDTO.employeeEmail}">
				<i class="fa fa fa-envelope" title="Email"></i><input type="text" readonly="readonly" value="--">
			</c:if>
			</li>
			<li>
			<c:if test="${not empty timesheetEntryDetailsDTO.projectNameForSubmissionPageDisplay}">
				<i class="fa fa-bar-chart" title="Project"></i><input type="text" readonly="readonly" value="${timesheetEntryDetailsDTO.projectNameForSubmissionPageDisplay}">
			</c:if>
			<c:if test="${empty timesheetEntryDetailsDTO.projectNameForSubmissionPageDisplay}">
				<i class="fa fa-bar-chart" title="Project"></i><input type="text" readonly="readonly" value="--">
			</c:if>
			</li>
			<li>
			<c:if test="${not empty timesheetEntryDetailsDTO.employeePhoneNo}">
				<i class="fa fa-phone" title="Phone No"></i><input type="text" readonly="readonly" value="${timesheetEntryDetailsDTO.employeePhoneNo}">
			</c:if>
			<c:if test="${empty timesheetEntryDetailsDTO.employeePhoneNo}">
				<i class="fa fa-phone" title="Phone No"></i><input type="text" readonly="readonly" value="--">
			</c:if>
			</li>
			<li>
			<c:if test="${not empty timesheetEntryDetailsDTO.employmentTypeForSubmissionPageDisplay}">
				<i class="fa fa-briefcase" title="Employment Type"></i><input type="text" readonly="readonly" value="${timesheetEntryDetailsDTO.employmentTypeForSubmissionPageDisplay}">
			</c:if>
			<c:if test="${empty timesheetEntryDetailsDTO.employmentTypeForSubmissionPageDisplay}">
				<i class="fa fa-briefcase" title="Employment Type"></i><input type="text" readonly="readonly" value="--">
			</c:if>
			</li>
			<li>
			<c:if test="${not empty timesheetEntryDetailsDTO.clientNameForDisplay}">
				<i class="fa fa-user-plus" title="Client"></i><input type="text" readonly="readonly" value="${timesheetEntryDetailsDTO.clientNameForDisplay}">
			</c:if>
			<c:if test="${empty timesheetEntryDetailsDTO.clientNameForDisplay}">
				<i class="fa fa-user-plus" title="Client"></i><input type="text" readonly="readonly" value="--">
			</c:if>
			</li>
		</ul>
	<div class="clearfix"></div>
</div>

<!-- ######################################## timesheet-status section ######################################## -->
<div class="col-md-6 paddingRight">
 <input type="hidden" id="notificationCurrentFrequency" value="${notificationCurrentFrequency}">
  <div id="timesheetSubmissionStatusDiv" class="timeNewSe clearfix ${!timesheetSubmissionDTO.reopenAcceptRejectButtonVisible ? 'timeNewStatus':'timesheetApproved-status'}">
				<c:if 	test="${timesheetSubmissionDTO.timesheetStatus == 'REJECTED'}">
					<b class="proj_closed pull-right">${timesheetSubmissionDTO.timesheetStatus}</b>
					<i class="glyphicon glyphicon-comment rejectedHover"> 
					<div class="app_error_mes" id="rejectedMessage">${timesheetSubmissionDTO.rejectedReason}</div> </i>
				</c:if>
				<c:if 	test="${timesheetSubmissionDTO.timesheetStatus == 'APPROVED'}">
				    <c:if test="${!empty timesheetSubmissionDTO.reopenRejectedReason}">
					    <i class='glyphicon glyphicon-comment rejectedHover'>
					       <div class="app_error_mes" id="rejectedMessage${timesheetEntryDetailsDTO.projectMemberId}">${timesheetSubmissionDTO.reopenRejectedReason}</div>
					    </i>
					 </c:if>
			 	     <c:if test="${timesheetSubmissionDTO.reopenButtonVisible}">
					     <b class="proj_withdraw pull-right" style="width:40%"  onClick='reOpenMyTimesheet();'>Re-Open</b> 
					</c:if>
					<c:if test="${!timesheetSubmissionDTO.reopenAcceptRejectButtonVisible && timesheetSubmissionDTO.reopenRequestSent}">
					    <i class="pull-right glyphicon glyphicon-comment rejectedHover">
					    <div class="app_error_mes" id="rejectedMessage${timesheetEntryDetailsDTO.projectMemberId}">${timesheetSubmissionDTO.reopenReason}</div>
					    </i><p class="pull-right ml" >Re-Open<br>Request sent</p> 
					</c:if>
					<c:if test="${timesheetSubmissionDTO.reopenAcceptRejectButtonVisible && timesheetSubmissionDTO.reopenRequestSent}">
						<i class="pull-right  glyphicon glyphicon-comment rejectedHover">
						<div class="app_error_mes" id="rejectedMessage${timesheetEntryDetailsDTO.projectMemberId}">${timesheetSubmissionDTO.reopenReason}</div></i>
						<div class="pull-right  sm text-center reopen"> 
						    <label class="clearfix reopen-label">RE-OPEN</label>
						    <i class="reopen-text-success fa fa-check" title="Accept" alt="Accept" onclick="acceptReopenTimeSheetBySuperUser('${timesheetEntryDetailsDTO.projectMemberId}','${timesheetSubmissionDTO.reopenUserId}')"></i>|<i class="reopen-text-danger fa fa-times" title="Reject" alt="Reject" onclick="rejectReopenTimeSheetBySuperUser('${timesheetEntryDetailsDTO.projectMemberId}','${timesheetSubmissionDTO.reopenUserId}')"></i>
						</div>
					</c:if>
					<b class="proj_active pull-right mr" style="width:40%">${timesheetSubmissionDTO.timesheetStatus}</b>
				</c:if>
				
				
				<c:if 	test="${timesheetSubmissionDTO.timesheetStatus == 'NEW'}">
					<b class="proj_new pull-right">${timesheetSubmissionDTO.timesheetStatus}</b>
				</c:if>
				<c:if 	test="${timesheetSubmissionDTO.timesheetStatus == 'DRAFT'}">
					<b class="proj_draft pull-right">${timesheetSubmissionDTO.timesheetStatus}</b>
				</c:if>
				<c:if 	test="${timesheetSubmissionDTO.timesheetStatus == 'SUBMITTED'}">
					<b class="proj_process pull-right">${timesheetSubmissionDTO.timesheetStatus}</b>
				</c:if>
				
</div>

<div class="clearfix"></div>

<!-- ######################################## Previous/Current/Project to Date Approved Hours ######################################## -->
<div class="mt pull-right mr10">
	<table width="100%" class="table table-bordered">
		<thead>
		<tr>
			<th>Previous month<br/>(Approved Hrs)</th><th>Current month Hrs<br/>(${timesheetSubmissionDTO.currentMonthName})</th><th>Project to Date<br/>(Approved Hrs)</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>
				<div class="week">
					<span class="draft"><d title="Total-Approved-Hours:${timesheetSubmissionDTO.previousMonthTotalHours}">${timesheetSubmissionDTO.previousMonthTotalHours}</d></span>
					<div class="progress mb0">
						<div class="progress-bar progress-bar-striped active progress-bar-info" role="progressbar" style="width: 60%">
							<d title="Billable:${timesheetSubmissionDTO.previousMonthBillableHours}">${timesheetSubmissionDTO.previousMonthBillableHours}</d>
						</div>
						<div class="progress-bar progress-bar-striped active progress-bar-warning" role="progressbar" style="width: 40%">
							<d title="Non-Billable:${timesheetSubmissionDTO.previousMonthNonBillableHours}">${timesheetSubmissionDTO.previousMonthNonBillableHours}</d>
						</div>
					</div>
				</div>
				</td>
				<td>
				<div class="week">
					<span class="draft">
					    <span id="monthTotal">
					       <input type="hidden" id="mTotal" value="${timesheetSubmissionDTO.currentMonthTotalHours}"/>
					      <d title="Total-Hours:${timesheetSubmissionDTO.currentMonthTotalHours}">${timesheetSubmissionDTO.currentMonthTotalHours}</d>
					    </span>
					</span>
					<div class="progress mb0">
						<div class="progress-bar progress-bar-striped active progress-bar-info" role="progressbar" style="width: 60%">
							<span id="monthBillableTotal">
							   <input type="hidden" id="mBillableTotal" value="${timesheetSubmissionDTO.currentMonthBillableHours}"/>
							   <d title="Billable:${timesheetSubmissionDTO.currentMonthBillableHours}">${timesheetSubmissionDTO.currentMonthBillableHours}</d>
							</span>
						</div>
						<div class="progress-bar progress-bar-striped active progress-bar-warning" role="progressbar" style="width: 40%">
						    <span id="monthNonBillableTotal">
						       <input type="hidden" id="mNonBillableTotal" value="${timesheetSubmissionDTO.currentMonthNonBillableHours}"/>
						       <d title="Non-Billable:${timesheetSubmissionDTO.currentMonthNonBillableHours}">${timesheetSubmissionDTO.currentMonthNonBillableHours}</d>
						    </span>
							
						</div>
					</div>
				</div>
				</td>
				<td>
				<div class="week">
					<span class="draft"><d title="Total-Approved-Hours:${timesheetSubmissionDTO.projectToDateTotalHours}">${timesheetSubmissionDTO.projectToDateTotalHours}</d></span>
					<div class="progress mb0">
						<div class="progress-bar progress-bar-striped active progress-bar-info" role="progressbar" style="width: 60%">
							<d title="Billable:${timesheetSubmissionDTO.projectToDateBillableHours}">${timesheetSubmissionDTO.projectToDateBillableHours}</d>
						</div>
						<div class="progress-bar progress-bar-striped active progress-bar-warning" role="progressbar" style="width: 40%">
							<d title="Non-Billable:${timesheetSubmissionDTO.projectToDateNonBillableHours}">${timesheetSubmissionDTO.projectToDateNonBillableHours}</d>
						</div>
					</div>
				</div>
				</td>
			</tr>
		</tbody>
	</table>
  </div>
</div>

<div class="clearfix"></div>


<!-- ######################################## timesheet_entries section ######################################## -->
<div id="timesheetNewTabs" class="timesheetNewTabs">
	<div class="project_side_wrap_inn_tabs">
	   <input type="hidden" name="timesheetSubmission.id" value="${timesheetSubmissionDTO.id}"/>
	   <input type="hidden" id="currentTimeSubFrequency" name="currentTimeSubFrequency" value="${timesheetSubmissionDTO.timeSubFrequency}"/>
		<input type="hidden" id="timeSubFrequency" name="timesheetSubmission.timeSubFrequency" value="${timesheetSubmissionDTO.timeSubFrequency}"/>
		
		<!-- ######################################## timesheet_sub_frequency tabs section ######################################## -->
		<ul class="project-tabs-menu">
		   <c:forEach var="tsf" items="${timesheetEntryDetailsDTO.timeSubFrequencies}">
		       <input type='hidden' name='tabDates'  id='tabDates' value='${tsf.key}' >
			   <li class="${tsf.key ==  timesheetSubmissionDTO.timeSubFrequency ? 'current' : ''} ${fn:contains(timesheetEntryDetailsDTO.filledTimeSubFrequencies, tsf.key) ? 'subColor' : ''}">
					<a href="#" onclick="timesheetDivByFrequency('${tsf.key}');">${tsf.value}</a>
				</li>
		   </c:forEach>
		  <div class="clearfix"></div>
		</ul>
		
		<!-- ######################################## timesheet_entry_information per one single sub-frequency ######################################## -->
	    <div id="timesheetDivByTab">
				<div class="project_tabs">
					<div id="tab-1" class="project_tabs_inn" style="display: block;">
						<div class="timesheet_tableNew">
							<table id="timesheet" cellspacing="0" cellpadding="0" style="display: inline-table;">
								<tbody>
									<tr>
										<th>Tasks</th>
										<th>Type</th>
										<th>Hourly Type</th>
										<th>Ref/Ticket No.</th>
										<c:forEach var="weekDays" items="${timesheetSubmissionDTO.weekDays}">
										   <th>${weekDays.dayOfTheDate}<i>${weekDays.date}</i></th>
										</c:forEach>
									</tr>
									
									 <!-- ######################################## leaves & holidays section ######################################## -->
									 <c:set var="leaveIndex" value="0"/>
						             <c:if test="${!empty timesheetSubmissionDTO.empLeaveMap}">
						                <c:forEach var="empLeaveMap" items="${timesheetSubmissionDTO.empLeaveMap}" varStatus="empLeaveMapCount">
						                      <c:set var="leaveIndex" value="${leaveIndex+1}"/>
							                      <tr id="timesheetRow${leaveIndex}">
								                      <c:set var="leaveMapArr" value="${fn:split(empLeaveMap.key,'##')}"/>
							                          <td>
								                         <div style="position: relative;">${leaveMapArr[0]}
								                          <c:if test="${empty timeSheetSubmissionStatus || timeSheetSubmissionStatus == 'NEW' || timeSheetSubmissionStatus == 'DRAFT'}">
									                          <c:forEach var="empL" items="${empLeaveMap.value}">
									                              <c:if test="${empL.holiday eq true}">
									                                 <input type="button" value="" onClick="alertHolidayDropMessage('${leaveIndex}','${empL.holidayId}','${empL.holidayUserId}','${empL.holidayUserLocationId}')" class="timeRemoveBtn"/>
									                              </c:if>
									                          </c:forEach>
									                       </c:if>
								                         </div>
								                      </td>
								                       <td>
								                         <span>${leaveMapArr[1]}</span>
								                          <input type="hidden" id="${leaveIndex}task1IdBillableTypeId" name="${leaveIndex}task1IdBillableTypeId" value="${leaveMapArr[1]}"/>
								                      </td>
								                      <td>
								                         <p>--</p>
								                      </td>
								                      <td>
								                         <p>--</p>
								                      </td>
								                      <c:forEach var="empLeave" items="${empLeaveMap.value}" varStatus="empLeaveCount">
								                        <td>
								                              <input readonly="readonly" class="inline_field"   value="${empLeave.hours}" placeholder="HH:MM" type="text" id="${leaveIndex}timeSheetDay${empLeaveCount.index+1}Id" >
											                   <c:if test="${!empty empLeave.leaveReason}">
	                                                              <div class=" timesheet_comm">                
											                       <i id="leaveReason${empLeaveCount.index}" class='glyphicon glyphicon-comment com_active' onclick='timesheetComment("leaveReason${empLeaveCount.index}Id");'></i>
											                       <span class='teskcomment' id='leaveReason${empLeaveCount.index}Id'>
											                             <textarea  disabled="disabled">${empLeave.leaveReason}</textarea>
	                                                               </span>
	                                                             </div>
											                   </c:if>
											              </td>
								                      </c:forEach>
							                       </tr>
						                </c:forEach>
						              </c:if>
						              
						              <!-- ######################################## timesheet-entries filled information ######################################## -->
						            <input type='hidden' name='maxRowNumber'  class='initiateTimesheetMax' id='maxRowNumber'  value='${fn:length(timesheetSubmissionDTO.empLeaveMap)+fn:length(entriesMap)}' />
									<c:set var="entryIndex" value="0"/>
									<c:forEach var="entry" items="${entriesMap}" varStatus="entryCount">
									    
									    <tr id="timesheetRow${leaveIndex+entryCount.index+1}">
									      <!--###################################### project-task section  ######################################-->
									      <td id="${leaveIndex+entryCount.index+1}taskTd"> 
											<div class="taskDropDown">
											  <c:set var="taskType" value=""/>
											  <c:set var="taskId" value=""/>
											  <c:set var="taskName" value=""/>
											  <c:set var="ticketNumber" value=""/>
											   <c:set var="hourlyType" value=""/>
											  <c:if test="${timesheetSubmissionDTO.submitButtonVisible ||  timesheetSubmissionDTO.saveButtonVisible}">
												   <select id="${leaveIndex+entryCount.index+1}task1Id" name="${leaveIndex+entryCount.index+1}task1Id" title="--select task--"  data-dropup-auto="false" data-size="8" data-live-search="true" class="selectpicker existedProjectTaskId${timesheetEntryDetailsDTO.projectId} timSelWid"  onchange='prepareProjectTaskDetails("${entryIndex}","${leaveIndex+entryCount.index+1}task1Id");' data-validation="required" field-name="Task">
														 <c:if test="${!empty timesheetSubmissionDTO.timesheetTaskList}">
														   <c:forEach var="pt" items="${timesheetSubmissionDTO.timesheetTaskList}">
														       <c:set var="taskId" value="${fn:split(entry.key, '##')[0]}"/>
														       <c:set var="ticketNumber" value="${fn:split(entry.key, '##')[1]}" />
														       <c:if test="${pt.id == taskId}">
															       <c:forEach var="ted" items="${entry.value}">
														                  <c:set var="taskType" value="${ted.timesheetTaskDTO.taskType}" />
														           </c:forEach>
														          <c:set var="taskId" value="${pt.id}" />
														          <c:set var="taskName" value="${pt.taskName}" />
														          <c:if test="${not empty pt.generatedId}" >
															       		<option value='${pt.id},${!empty taskType ? taskType : pt.taskType},${pt.taskName}' selected>${pt.generatedId} -${pt.taskName}</option>
															       </c:if>
															        <c:if test="${empty pt.generatedId}" >
															       		<option value='${pt.id},${!empty taskType ? taskType : pt.taskType},${pt.taskName}' selected>${pt.taskName}</option>
															       </c:if>
														       </c:if>
														       <c:if test="${pt.id != taskId}">
															       <c:if test="${not empty pt.generatedId}" >
															       		<option value='${pt.id},${pt.taskType},${pt.taskName}'> ${pt.generatedId} - ${pt.taskName} </option>
															       </c:if>
															        <c:if test="${empty pt.generatedId}" >
															       		<option value='${pt.id},${pt.taskType},${pt.taskName}'>${pt.taskName} </option>
															       </c:if>
														       </c:if>
														   </c:forEach>
														</c:if>
													</select>
													 <c:if test="${entryCount.index == 0}">
													    <input value="" onclick="addTimeSheetRow('${timesheetEntryDetailsDTO.projectId}','${leaveIndex}')" class="addtaskfield" type="button">
													 </c:if>
													 <c:if test="${entryCount.index > 0}">
													    <input value="" onclick="removeTimeSheetRow('${leaveIndex+entryCount.index+1}');" class="addtaskfield removetaskfield" type="button">
													 </c:if>
											  </c:if>
											  
											  
											  
											   <c:if test="${!timesheetSubmissionDTO.saveButtonVisible}">
											         <c:if test="${!empty entry.key}">
											             <c:forEach var="pt" items="${timesheetSubmissionDTO.timesheetTaskList}">
											                     <c:set var="taskId" value="${fn:split(entry.key, '##')[0]}"/>
											                     <c:set var="ticketNumber" value="${fn:split(entry.key, '##')[1] == 'NA' ? '' :fn:split(entry.key, '##')[1]}" />
											                      <c:set var="hourlyType" value="${fn:split(entry.key, '##')[2] == 'NA' ? '' : fn:split(entry.key, '##')[2]}" />
															       <c:if test="${pt.id == taskId}">
															       <c:if test="${timesheetSubmissionDTO.timesheetStatus == 'APPROVED'}">
																	       <c:forEach var="ted" items="${entry.value}">
																                  <c:set var="taskType" value="${ted.timesheetTaskDTO.taskType}" />
																           </c:forEach>
															       </c:if>
															        <c:if test="${timesheetSubmissionDTO.timesheetStatus != 'APPROVED'}">
															             <c:forEach var="ted" items="${entry.value}">
															                 <c:set var="taskType" value="${ted.timesheetTaskDTO.taskType}" />
									                                      </c:forEach>
															        </c:if> 
														           <c:set var="taskId" value="${pt.id}" />
														           <c:set var="taskName" value="${pt.taskName}" />
														          <p>${!empty pt.taskName ? pt.taskName : '--'}</p>
															       </c:if>
												         </c:forEach>
												       </c:if>
											          <c:if test="${empty entry.key}">
											              <p>--</p>
											          </c:if>
											          
											   </c:if>
											</div>
									      </td>
									      <!--###################################### billable-type section  ######################################-->
									      <td>
									        <div class='taskDropDown'>
									          <c:if test="${timesheetSubmissionDTO.timesheetStatus == 'APPROVED'}">
									               <span>${!empty taskType ? taskType : '--'}</span>
									               <input type="hidden" id="${leaveIndex+entryCount.index+1}task1IdBillableTypeId" name="${leaveIndex+entryCount.index+1}task1IdBillableTypeId" value="${taskType}"/>
									          </c:if>
									         <c:if test="${timesheetSubmissionDTO.timesheetStatus != 'APPROVED'}">
			                                      <select id="${leaveIndex+entryCount.index+1}task1IdBillableTypeId" style="width:220px;" data-dropup-auto="false" data-size="8"  class="selectpicker" name="${leaveIndex+entryCount.index+1}task1IdBillableTypeId"  field-name="Billble Type" onchange="prepareBillableDetails('${entryIndex}','${leaveIndex+entryCount.index+1}task1IdBillableTypeId')">  
									                  <option value='Billable' ${!empty taskType && taskType == 'Billable' ?'selected':''}>Billable</option>
									                  <option value='Non-Billable' ${!empty taskType && taskType == 'Non-Billable' ?'selected':''}>Non-Billable</option>
								                  </select>
							                  </c:if>
									        </div>
									      </td>
									       <!--###################################### Hourly-type section  ######################################-->
									       <td>
									        <c:set var="hourlyType" value="${fn:split(entry.key, '##')[2] == 'NA' ? 'Regular' : fn:split(entry.key, '##')[2]}" />
									         <c:if test="${timesheetSubmissionDTO.timesheetStatus == 'APPROVED'}">
						                           <span>${!empty hourlyType ? hourlyType : '--'}</span>
									          </c:if>
									          <c:if test="${timesheetSubmissionDTO.timesheetStatus != 'APPROVED'}">
									               <div>
											          <select id="${leaveIndex+entryCount.index+1}hourlyType1Id" style="width:220px;" data-dropup-auto="false" data-size="8"  class="selectpicker" name="${leaveIndex+entryCount.index+1}hourlyType1Id"  field-name="Hourly Type" onchange='setHourlyType("${entryIndex}","${leaveIndex+entryCount.index+1}hourlyType1Id");' >  
										                  <option value='Regular' ${!empty hourlyType && hourlyType == 'Regular' ? 'selected' : ''}>Regular</option>
										                  <option value='Overtime' ${!empty hourlyType && hourlyType == 'Overtime' ? 'selected' : ''}>Overtime</option>
							                          </select>
											       </div>
									         </c:if>
									      </td>	
									       <!--###################################### tickNumber section  ######################################-->
									      <td>
									       <c:set var="ticketNumber" value="${fn:split(entry.key, '##')[1] == 'NA' ? '' : fn:split(entry.key, '##')[1]}" />
									         <c:if test="${timesheetSubmissionDTO.timesheetStatus == 'APPROVED'}">
									               <span>${!empty ticketNumber ? ticketNumber : '--'}</span>
									          </c:if>
									          <c:if test="${timesheetSubmissionDTO.timesheetStatus != 'APPROVED'}">
									          <div>
									        	<input type="text"   id="${leaveIndex+entryCount.index+1}ticketNumber1Id" name="${leaveIndex+entryCount.index+1}ticketNumber1Id" value="${ticketNumber}" field-name="" onblur="setTicketNumber('${entryIndex}','${leaveIndex+entryCount.index+1}ticketNumber1Id');" class="timeTick" data-validation-optional="true" data-validation="validate_Space validate_Alphanumeric validate_length length1-10" >
									          </div>
									         </c:if>
									      </td>	
									       <!--###################################### day-wise hours  section  ######################################-->
									       <c:forEach var="te" items="${entry.value}" varStatus="teStatus">
										          <td> 
										           <div>
										               <input type="hidden" name="timesheetSubmission.timesheetEntries[${entryIndex}].timesheetTaskDTO.id" value="${taskId}"/>
													   <input type="hidden" name="timesheetSubmission.timesheetEntries[${entryIndex}].timesheetTaskDTO.taskName" value="${taskName}"/>
													   <input type="hidden" name="timesheetSubmission.timesheetEntries[${entryIndex}].timesheetTaskDTO.taskType" value="${taskType}"/>
													    <input type="hidden" name="timesheetSubmission.timesheetEntries[${entryIndex}].hourlyType" value="${hourlyType}"/>
                                                       <input type="hidden" name="timesheetSubmission.timesheetEntries[${entryIndex}].ticketNumber" value="${ticketNumber}" />
										               <input type="hidden" name="timesheetSubmission.timesheetEntries[${entryIndex}].id" value="${te.id}"/>
										               <input type="hidden" name="timesheetSubmission.timesheetEntries[${entryIndex}].dayOfTheDate" value="${te.dayOfTheDate}"/>
										               <input type="hidden" name="timesheetSubmission.timesheetEntries[${entryIndex}].date" value="${te.date}"/>
										               <input onblur="editMe(this);"  ${te.editable ? '' : 'disabled="disabled"'} id="${leaveIndex+entryCount.index+1}timeSheetDay${teStatus.index+1}Id" name="timesheetSubmission.timesheetEntries[${entryIndex}].hours" class="inline_field" value="${te.hours}" placeholder="HH:MM" type="text">
										               <div class='timesheet_comm'>
										                   <c:if test="${!empty te.comments || (empty te.comments && te.editable)}">
										                       <i class='glyphicon glyphicon-comment ${!empty te.comments ? "com_active":""}' onclick='timesheetComment("${leaveIndex+entryCount.index+1}comment${teStatus.index+1}Id");'></i>
										                       <span class='teskcomment' id='${leaveIndex+entryCount.index+1}comment${teStatus.index+1}Id'>
										                             <textarea ${te.editable ? '' : 'disabled="disabled"'}  name='timesheetSubmission.timesheetEntries[${entryIndex}].comments' >${te.comments}</textarea>
                                                               </span>
										                   </c:if>
										                   <c:if test="${empty te.comments && !te.editable}">
										                      <i class='blankDiv'></i>
										                   </c:if>
										               </div>
										            </div>
										              <c:set var="entryIndex" value="${entryIndex+1}"/>
										           </td>
								        </c:forEach>
									    </tr>
									</c:forEach>
									
									 <!--###################################### column-wise totals  ######################################-->
								    <tr class="last_child">
										<td class="total_field">Total</td>
										<td></td>
										<td></td>
										<td></td>
										<td>
											<div class="alignDiv">
											<input type="hidden" id="entryIndexValue" value="${entryIndex}"/>
												<input disabled="" id="rowTotal1Id" name="rowTotal1Id"
													class="inline_field" value="HH:MM" placeholder="00:00"
													type="text">
											</div>
										</td>
										<td>
											<div class="alignDiv">
												<input disabled="" id="rowTotal2Id" name="rowTotal2Id"
													class="inline_field" value="HH:MM" placeholder="00:00"
													type="text">
											</div>
										</td>
										<td>
											<div class="alignDiv">
												<input disabled="" id="rowTotal3Id" name="rowTotal3Id"
													class="inline_field" value="HH:MM" placeholder="00:00"
													type="text">
											</div>
										</td>
										<td>
											<div class="alignDiv">
												<input disabled="" id="rowTotal4Id" name="rowTotal4Id"
													class="inline_field" value="HH:MM" placeholder="00:00"
													type="text">
											</div>
										</td>
										<td>
											<div class="alignDiv">
												<input disabled="" id="rowTotal5Id" name="rowTotal5Id"
													class="inline_field" value="HH:MM" placeholder="00:00"
													type="text">
											</div>
										</td>
										<td>
											<div class="alignDiv">
												<input disabled="" id="rowTotal6Id" name="rowTotal6Id"
													class="inline_field" value="HH:MM" placeholder="00:00"
													type="text">
											</div>
										</td>
										<td>
											<div class="alignDiv">
												<input disabled="" id="rowTotal7Id" name="rowTotal7Id"
													class="inline_field" value="HH:MM" placeholder="00:00"
													type="text">
											</div>
										</td>
									</tr>
								</tbody>
							</table>
							</div>
							<div class="clearfix"></div>
							
							 <!--###################################### timesheet attachements section  ######################################-->
							 <div class="timesheet_bot clearfix">
								<div class="commentFile">
									<input name="attcahmentMaxRowNumber" id="attcahmentMaxRowNumber" value="1" type="hidden">
									<input name="fileCounter" id="fileCounter" value="0" type="hidden">
									<div id="timesheetDocumentsId" class="timesheetDocs">
									  <c:if test="${timesheetSubmissionDTO.submitButtonVisible}">
											<div class="commentFile">
												<span>
												   <table class="timesheetAttachment" id="attachmentTable" cellspacing="0" cellpadding="0">
												       <tbody>
														   <tr id="divAttachmentTsRow0">
															   <td>
															     <input name="dbIsTimesheetAttachmentRequired" id="dbIsTimesheetAttachmentRequired" value="${timesheetSubmissionDTO.attachmentRequired ? 1 : 0}" type="hidden">
															   <div id="0docFileuploadFileGroup">
																<input name="0docFile" id="0docFile" field-name="Attachment" onchange="validateUploadFile(0)" data-validation-optional="true" class="timesheetAttachmentValidation" type="file">
																   <i class="fa fa-plus" onclick="addAttachment()"></i>				
																</div>				
															   </td>					
																 	 		
															</tr>
														</tbody>
													</table>
												 </span>
												 <i class="fa fa-info-circle" title="Allowed File Formats: ppt,pptx,pps,ppsx,doc,docx,pdf,rtf,odt,xls,xlsx,csv,  zip,txt,jpg,jpeg,bmp,png,gif,eps,tiff,TGA,psd,svg,msg,wpd,wps      Allowed Size : 2MB "></i>
											 </div>
										 </c:if>
										  <c:if test="${!empty timesheetSubmissionDTO.fileInfo || !timesheetSubmissionDTO.attachmentRequired}">
										        <input name="isTimesheetAttachmentRequired" id="isTimesheetAttachmentRequired" value="N" type="hidden">
										  </c:if>
										  <c:if test="${empty timesheetSubmissionDTO.fileInfo && timesheetSubmissionDTO.attachmentRequired}"> 
										     <input name="isTimesheetAttachmentRequired" id="isTimesheetAttachmentRequired" value="Y" type="hidden">
										   </c:if>
										   <c:forEach var="finfo" items="${timesheetSubmissionDTO.fileInfo}">
										         <strong id='${finfo.fileDbId}document' class="clearfix">
												     <a class='downicon downloadInput mb maxWid mr' href='${finfo.fileURL}' download>
												        <div class="attFile"><i class="fa fa-download"></i>${finfo.fileName}</div>
												      </a>
												       <c:if test="${timesheetSubmissionDTO.submitButtonVisible}">
												          <i title="Delete" class="fa fa-trash cursorPointer text-danger mr" onClick='deleteSubmissionDocument("${finfo.fileDbId}");'></i>
												       </c:if>
												       <i title="View" class="fa fa-eye cursorPointer text-primary" onClick="viewFile('${finfo.fileURL}','${finfo.fileName}','${finfo.contentType}');"></i>
												 </strong>
										   </c:forEach>
									</div>
									  <c:if test="${timesheetSubmissionDTO.weekCommentVisible}">
										<div class="commentTime pull-right">
											<textarea class="form-controls" name="timesheetSubmission.weekComment" ${timesheetSubmissionDTO.submitButtonVisible ? '' : 'disabled="disabled"'} id="weekComment" placeholder="comment here">${timesheetSubmissionDTO.weekComment}</textarea>
										</div>
									   </c:if>
								</div>
								
								<div class="timesheet_week_comm">
							<div class="timesheet_button">
							   <!--###################################### total billable/non-billable hours display  section  ######################################-->
							   <div class="totalWrap pull-left timesheethours-li">
									<ul>
										<li><span>Billable Hours</span><input
											id="grandBillableTotal"
											value="${timesheetSubmissionDTO.billableHours}" placeholder="HH/HH" readonly="" type="text"></li>
										<li><span>Non-Billable Hours</span><input
											id="grandNonBillableTotal"
											value="${timesheetSubmissionDTO.nonBillableHours}" placeholder="HH/HH" readonly="" type="text"></li>
										<li><span>Total Hours</span><input id="grandTotal" 
											value="${timesheetSubmissionDTO.totalHours}" placeholder="HH/HH" readonly="" type="text"></li>
										<div class="clearfix"></div>
									</ul>
								</div>
								
								 <!--###################################### save/submit/approve/reject/cancel buttons section  ######################################-->
								 <c:if test="${!timesheetSubmissionDTO.submitButtonVisible}">
								   <c:if 	test="${timesheetSubmissionDTO.timesheetStatus == 'NEW' || timesheetSubmissionDTO.timesheetStatus == 'DRAFT' || timesheetSubmissionDTO.timesheetStatus == 'SUBMITTED'}">
								        <input onclick="saveTimesheet('Draft');" value="Save" class="time_btn time_dra" type="button">
								   </c:if>
								 </c:if>
								
								 <c:if test="${timesheetSubmissionDTO.submitButtonVisible}">
								    <c:if 	test="${timesheetSubmissionDTO.timesheetStatus == 'NEW' || timesheetSubmissionDTO.timesheetStatus == 'DRAFT'}">
								        <input onclick="saveTimesheet('Draft');" value="Save" class="time_btn time_dra" type="button">
								     </c:if>
								     <input onclick="saveTimesheet('Submitted');" value="Submit" class="time_btn" type="button">
								     <c:if 	test="${timesheetSubmissionDTO.timesheetStatus == 'SUBMITTED'}">
								          <input onclick="saveTimesheet('approve');" value="Approve" class="time_btn time_appr" type="button">
								          <input onclick="rejectTimesheet();" value="Reject" class="time_btn time_rej" type="button">
								     </c:if>
								     
							    </c:if>
							     <c:if test="${timesheetSubmissionDTO.cancelButtonVisible}">
							       <input type='button' value='Clear' onClick='clearTimesheet();' class='time_btn time_cancel' />
							     </c:if>
							</div>
							</div>
							<div class="clearfix"></div>								
								
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
	var invalidData = "${INVALID_DATA}";
	var emptyData = "${INVALID_DATA_EMPTY}"
		var outPut;
		if(invalidData != "" && invalidData != null && invalidData != 'undefined'){
			outPut = "<p>"+invalidData+" hours should not be more than 24<b>X</b></p>";
		}
		if(emptyData != "" && emptyData != null && emptyData != 'undefined'){
			outPut = "<p>"+emptyData+"<b>X</b></p>";
		}
	if((invalidData != "" && invalidData != null && invalidData != 'undefined')|| (emptyData != "" && emptyData != null && emptyData != 'undefined')){
		outPut = outPut;
		$("#timeSheetErrorMsg").removeAttr('style');
		$("#timeSheetErrorMsg").attr('style','display:block;');
		$("#timeSheetErrorMsg").empty();
		$("#timeSheetErrorMsg").append(outPut);
		var error = "<input  type='hidden' name='timesheetHiddenError' id='timesheetHiddenError'  data-validation='required' />";
		$("#hideError").empty();
		$("#hideError").append(error);
	 }else{
	    $("#timeSheetErrorMsg").removeAttr('style');
	    $("#timeSheetErrorMsg").attr('style','display:none;');
		$("#timeSheetErrorMsg").empty();
		$("#hideError").empty();
	} 
	
	$(".time_pop b").click(function(){
		$("#timeSheetErrorMsg").removeAttr('style');
		$("#timeSheetErrorMsg").attr('style','display:none;');
	    $("#timeSheetErrorMsg").empty();
	}); 
	
	
	maskme();
	if($("#timesheet_form input[data-id*=docFile]").length > 0) {
		$("#timesheet_form input[data-id*=docFile]").remove();
	}
});

/* this script is for to calculate total hours */		
function setTotals(){ 
	 var total;
	 var billableTotal;
	 var billableSum = 0;
	 var nonBillableTotal;
	 var nonBillableSum = 0;
	 var rowNum = $("#maxRowNumber").val();
	 var jValue = parseInt(rowNum)+1;
	 for (var i = 1; i < 8; i++){
		 var bsum = 0;
		 var nbsum = 0;
		 var total = 0;
		 for (var j = 1; j < jValue; j++){
			    var btemp = $("#"+j+"timeSheetDay"+i+"Id").val();
			    if (btemp !== undefined && btemp !=='####' && btemp !=='') {
			    	 var hours = btemp;
			    	 var split = hours.split(':');
			    	 var first = split[0];
			    	 var second = split[1];
					 
					 var minute = first * 60;
					 var minute1 = second;
					 var finalmin = parseInt(minute) + parseInt(minute1);
			    	 
				     bsum = parseFloat(bsum) + parseInt(finalmin);
				     
				     var billableType = $("#"+j+"task1IdBillableTypeId").val();
				     if (billableType !== undefined && billableType !=='####' && billableType !=='' && billableType == 'Billable') {
				    	 var billableHours = btemp;
				    	 var billableSplit = billableHours.split(':');
				    	 var billableFirst = billableSplit[0];
				    	 var billableSecond = billableSplit[1];
						 
						 var billableMinute = billableFirst * 60;
						 var billableMinute1 = billableSecond;
						 var billableFinalmin1 = parseInt(billableMinute) + parseInt(billableMinute1);
				    	 
						 billableSum = parseFloat(billableSum) + parseInt(billableFinalmin1);
				    }
				     
				     if (billableType !== undefined && billableType !=='####' && billableType !=='' && (billableType == 'Non Billable' || billableType == 'Non-Billable' || billableType == 'NonBillable')) {
				    	 var nonBillableHours = btemp;
				    	 var nonBillableSplit = nonBillableHours.split(':');
				    	 var nonBillableFirst = nonBillableSplit[0];
				    	 var nonBillableSecond = nonBillableSplit[1];
						 
						 var nonBillableMinute = nonBillableFirst * 60;
						 var nonBillableMinute1 = nonBillableSecond;
						 var nonBillableFinalmin1 = parseInt(nonBillableMinute) + parseInt(nonBillableMinute1);
				    	 
						 nonBillableSum = parseFloat(nonBillableSum) + parseInt(nonBillableFinalmin1);
				    }
				}
			    
		 }
		 
	    var t = bsum; 
		var hours = Math.floor(t / 60); 
		var minutes = t % 60;
		
		if (minutes < 10) {
			minutes = '0' + minutes;
		}
		if (hours < 10) {
			hours = '0' + hours;
		}
		if(!isNaN(hours) && !isNaN(minutes)){
			total = hours +":"+ minutes;
		}else{
			total =  "00:00";
		}
	    $("#rowTotal"+i+"Id").val(total);
       }	
	   //for calculate billable count
	    if(!isNaN(billableSum)){
	        var t1 = billableSum; 
	 		var billableHours = Math.floor(t1 / 60); 
	 		var billableMinutes = t1 % 60;
	 		
	 		if (billableMinutes < 10) {
	 			billableMinutes = '0' + billableMinutes;
	 		}
	 		if (billableHours < 10) {
	 			billableHours = '0' + billableHours;
	 		}
	 		if(!isNaN(billableHours) && !isNaN(billableMinutes)){
	 			billableTotal = billableHours +":"+ billableMinutes;
	 		}else{
	 			billableTotal =  "00:00";
	 		}
	 	    $("#grandBillableTotal").val(billableTotal);
	    }else{
	    	$("#grandBillableTotal").val("00:00");
	    }
	   
	    
	    
	  //for calculate non-billable count
	   if(!isNaN(nonBillableSum)){
		    var t2 = nonBillableSum; 
			var nonBillableHours = Math.floor(t2 / 60); 
			var nonBillableMinutes = t2 % 60;
			
			if (nonBillableMinutes < 10) {
				nonBillableMinutes = '0' + nonBillableMinutes;
			}
			if (nonBillableHours < 10) {
				nonBillableHours = '0' + nonBillableHours;
			}
			if(!isNaN(nonBillableHours) && !isNaN(nonBillableMinutes)){
				nonBillableTotal = nonBillableHours +":"+ nonBillableMinutes;
			}else{
				nonBillableTotal =  "00:00";
			}
		    $("#grandNonBillableTotal").val(nonBillableTotal);
	   }else{
		   $("#grandNonBillableTotal").val("00:00");
	   }
	   
	    
	 grandTotal();		
 }

function grandTotal(){
    var total;
    var bsum = 0;
    for(var i=1 ;i<8;i++){
   	     var hours = $("#rowTotal"+i+"Id").val();

	   	 var split = hours.split(':');
	   	 var first = split[0];
	   	 var second = split[1];
		 
		 var minute = first * 60;
		 var minute1 = second;
		 var finalmin = parseInt(minute) + parseInt(minute1);
   	 
	     bsum = parseFloat(bsum) + parseInt(finalmin);
   	 
    }
    var t = bsum; 
	 var hours = Math.floor(t / 60); 
	 var minutes = t % 60;
	 if (minutes < 10) {
		minutes = '0' + minutes;
	 }
	 if (hours < 10) {
		hours = '0' + hours;
	 }
	 if(!isNaN(hours) && !isNaN(minutes)){
		 total = hours +":"+ minutes;
	 }else{
		 total = "00:00"; 
	 }
	 $("#grandTotal").val(total);
	 currentMonthHours();
}
function currentMonthHours(){
	 var total;
	 var billableTotal;
	 var billableSum = 0;
	 var nonBillableTotal;
	 var nonBillableSum = 0;
	 var rowNum = $("#maxRowNumber").val();
	 var jValue = parseInt(rowNum)+1;
	  var iValue = '${timesheetSubmissionDTO.incrStartsFrom}';
	 for (var i = parseInt(iValue); i < 8; i++){
		 var bsum = 0;
		 var nbsum = 0;
		 var total = 0;
		 for (var j = 1; j < jValue; j++){
			    var btemp = $("#"+j+"timeSheetDay"+i+"Id").val();
			    if (btemp !== undefined && btemp !=='####' && btemp !=='') {
			    	 var hours = btemp;
			    	 var split = hours.split(':');
			    	 var first = split[0];
			    	 var second = split[1];
					 
					 var minute = first * 60;
					 var minute1 = second;
					 var finalmin = parseInt(minute) + parseInt(minute1);
			    	 
				     bsum = parseFloat(bsum) + parseInt(finalmin);
				     
				     var billableType = $("#"+j+"task1IdBillableTypeId").val();
				     if (billableType !== undefined && billableType !=='####' && billableType !=='' && billableType == 'Billable') {
				    	 var billableHours = btemp;
				    	 var billableSplit = billableHours.split(':');
				    	 var billableFirst = billableSplit[0];
				    	 var billableSecond = billableSplit[1];
						 
						 var billableMinute = billableFirst * 60;
						 var billableMinute1 = billableSecond;
						 var billableFinalmin1 = parseInt(billableMinute) + parseInt(billableMinute1);
				    	 
						 billableSum = parseFloat(billableSum) + parseInt(billableFinalmin1);
				    }
				     
				     if (billableType !== undefined && billableType !=='####' && billableType !=='' && (billableType == 'Non Billable' || billableType == 'Non-Billable' || billableType == 'NonBillable')) {
				    	 var nonBillableHours = btemp;
				    	 var nonBillableSplit = nonBillableHours.split(':');
				    	 var nonBillableFirst = nonBillableSplit[0];
				    	 var nonBillableSecond = nonBillableSplit[1];
						 
						 var nonBillableMinute = nonBillableFirst * 60;
						 var nonBillableMinute1 = nonBillableSecond;
						 var nonBillableFinalmin1 = parseInt(nonBillableMinute) + parseInt(nonBillableMinute1);
				    	 
						 nonBillableSum = parseFloat(nonBillableSum) + parseInt(nonBillableFinalmin1);
				    }
				}
			    
		 }
       }	
	   //for calculate billable count
	    if(!isNaN(billableSum)){
	        var t1 = billableSum; 
	 		var billableHours = Math.floor(t1 / 60); 
	 		var billableMinutes = t1 % 60;
	 		
	 		if (billableMinutes < 10) {
	 			billableMinutes = '0' + billableMinutes;
	 		}
	 		if (billableHours < 10) {
	 			billableHours = '0' + billableHours;
	 		}
	 		if(!isNaN(billableHours) && !isNaN(billableMinutes)){
	 			billableTotal = billableHours +":"+ billableMinutes;
	 		}else{
	 			billableTotal =  "00:00";
	 		}
	 		
	 		
	 		 var mhm = '${timesheetSubmissionDTO.currentMonthBillableHours}';
		   	 var msplit = mhm.split('.');
		   	 var mHours     = msplit[0];
		   	 var mMinutes   = msplit[1];
		   	 
		 	
		   	 var asplit = billableTotal.split(':');
		   	 var aHours     = asplit[0];
		   	 var aMinutes   = asplit[1];
		   	 
		   	 var first = parseInt(mHours)+parseInt(aHours);
		   	 var second = parseInt(mMinutes)+parseInt(aMinutes);
		     
		     
		   	 var minute2 = first * 60;
			 var minute3 = second;
			 
			 var finalmin1 = parseInt(minute2) + parseInt(minute3);
			
		     var t1 = parseInt(finalmin1);
			 var hours1 = Math.floor(t1 / 60); 
			 var minutes1 = t1 % 60;
			 if (minutes1 < 10) {
				minutes1 = '0' + minutes1;
			 }
			 if (hours1 < 10) {
				hours1 = '0' + hours1;
			 }
		
			 var total1;
			 if(!isNaN(hours1) && !isNaN(minutes1)){
				 if(parseInt(minutes1) <10){
					 minutes1 = parseInt(minutes1);
				 }
				 if(parseInt(hours1) <10){
					 hours1 = parseInt(hours1);
				 }
				 total1 = hours1 +"."+ minutes1;
			 }else{
				 total1 = "0.0"; 
			 }
			 
	 		$("#monthBillableTotal").empty();
	 		$("#monthBillableTotal").html('<input type="hidden" id="mBillableTotal" value="'+total1+'"/><d title="Billable: '+total1+'">'+total1+'</d>');
	    }
	    
	  //for calculate non-billable count
	   if(!isNaN(nonBillableSum)){
		    var t2 = nonBillableSum; 
			var nonBillableHours = Math.floor(t2 / 60); 
			var nonBillableMinutes = t2 % 60;
			
			if (nonBillableMinutes < 10) {
				nonBillableMinutes = '0' + nonBillableMinutes;
			}
			if (nonBillableHours < 10) {
				nonBillableHours = '0' + nonBillableHours;
			}
			if(!isNaN(nonBillableHours) && !isNaN(nonBillableMinutes)){
				nonBillableTotal = nonBillableHours +":"+ nonBillableMinutes;
			}else{
				nonBillableTotal =  "00:00";
			}
			
			 var mhm = '${timesheetSubmissionDTO.currentMonthNonBillableHours}';
		   	 var msplit = mhm.split('.');
		   	 var mHours     = msplit[0];
		   	 var mMinutes   = msplit[1];
		 	
		   	 var asplit = nonBillableTotal.split(':');
		   	 var aHours     = asplit[0];
		   	 var aMinutes   = asplit[1];
		  
		   	 var first = parseInt(mHours)+parseInt(aHours);
		   	 var second = parseInt(mMinutes)+parseInt(aMinutes);
			 
		   	 var minute2 = first * 60;
			 var minute3 = second;
			 
			 var finalmin1 = parseInt(minute2) + parseInt(minute3);
			
		     var t1 = parseInt(finalmin1);
			 var hours1 = Math.floor(t1 / 60); 
			 var minutes1 = t1 % 60;
			 if (minutes1 < 10) {
				minutes1 = '0' + minutes1;
			 }
			 if (hours1 < 10) {
				hours1 = '0' + hours1;
			 }
			 var total1;
			 if(!isNaN(hours1) && !isNaN(minutes1)){
				 if(parseInt(minutes1) <10){
					 minutes1 = parseInt(minutes1);
				 }
				 if(parseInt(hours1) <10){
					 hours1 = parseInt(hours1);
				 }
				 total1 = hours1 +"."+ minutes1;
			 }else{
				 total1 = "0.0"; 
			 }
			
			 
			 $("#monthNonBillableTotal").empty();
			 $("#monthNonBillableTotal").html('<input type="hidden" id="mNonBillableTotal" value="'+total1+'"/><d title="Non-Billable: '+total1+'">'+total1+'</d>');
	   }
	   var timesheetFor = '${timesheetEntryDetailsDTO.timesheetFor}';
	   if(timesheetFor != null && timesheetFor != 'undefined' && timesheetFor != '' && timesheetFor == 'approver'){
		   currentMonthTotalHours();
	   }
	  
}

function currentMonthTotalHours(){
    var total;
    var bsum = 0;
    var iValue = '${timesheetSubmissionDTO.incrStartsFrom}';
    for(var i=parseInt(iValue);i<8;i++){
  	     var hours = $("#rowTotal"+i+"Id").val();
	   	 var split = hours.split(':');
	   	 var first = split[0];
	   	 var second = split[1];
		 
		 var minute = first * 60;
		 var minute1 = second;
		 var finalmin = parseInt(minute) + parseInt(minute1);
	     bsum = parseFloat(bsum) + parseInt(finalmin);
    }
     var t = bsum; 
	 var hours = Math.floor(t / 60); 
	 var minutes = t % 60;
	 if (minutes < 10) {
		minutes = '0' + minutes;
	 }
	 if (hours < 10) {
		hours = '0' + hours;
	 }
	 if(!isNaN(hours) && !isNaN(minutes)){
		 total = hours +":"+ minutes;
	 }else{
		 total = "00:00"; 
	 }
		
  	 var mhm = '${timesheetSubmissionDTO.currentMonthTotalHours}';
  	 var msplit = mhm.split('.');
  	 var mHours     = msplit[0];
  	 var mMinutes   = msplit[1];
	
  	 var asplit = total.split(':');
  	 var aHours     = asplit[0];
  	 var aMinutes   = asplit[1];
 
  	 var first = parseInt(mHours)+parseInt(aHours);
  	 var second = parseInt(mMinutes)+parseInt(aMinutes);
	 
  	 var minute2 = first * 60;
	 var minute3 = second;
	 
	 var finalmin1 = parseInt(minute2) + parseInt(minute3);
	
    var t1 = parseInt(finalmin1);
	 var hours1 = Math.floor(t1 / 60); 
	 var minutes1 = t1 % 60;
	 if (minutes1 < 10) {
		minutes1 = '0' + minutes1;
	 }
	 if (hours1 < 10) {
		hours1 = '0' + hours1;
	 }

	 var total1;
	 if(!isNaN(hours1) && !isNaN(minutes1)){
		 if(parseInt(minutes1) <10){
			 minutes1 = parseInt(minutes1);
		 }
		 if(parseInt(hours1) <10){
			 hours1 = parseInt(hours1);
		 }
		 total1 = hours1 +"."+ minutes1;
	 }else{
		 total1 = "0.0"; 
	 }
	 $("#monthTotal").empty();
	 $("#monthTotal").html('<input type="hidden" id="mTotal" value="'+total1+'"/><d title="Total-Hours:'+total1+'">'+total1+'</d>');
}

</script>

<!-- this script is for mask for each entity  -->	
<script>
function maskme(){
	$.mask.definitions['H'] = "[0-9]";
    $.mask.definitions['h'] = "[0-9]";
    $.mask.definitions['M'] = "[0-9]";
    $.mask.definitions['m'] = "[0-9]";
    $.mask.definitions['P'] = "[AaPp]";
    $.mask.definitions['p'] = "[Mm]";
    $(".inline_field").mask("Hh:Mm"); 
}
</script>

<script>
$(document).ready(function() {  
	if('${AWKWARD}' == "true"){
		   window.location.href =  "/errorPage";
	 }else{
		 maskme(); 
	 }
 });
</script>

<!-- this script is for add row functionality at inline edit screen -->
<script>
function callinlineedit(local){     
       var id = local.id;
	   $("#"+id).addClass("inline_field");
       $("#"+id).editInPlace({
		
		saving_animation_color: "#ECF2F8",
		callback: function(idOfEditor, enteredText, orinalHTMLContent, settingsParams, animationCallbacks) {
			return enteredText;
		}
	});
}
</script>

	
<!-- this script is for timesheet comment  shown and hide  -->	      
<script type="text/javascript">
function timesheetComment(local){ 
	$('.teskcomment').each(function() {
	    var currentId = this.id;
	    var localId = local;
	    if(localId.trim() == currentId.trim()){
       	 		$("#"+local).slideToggle(200); 
        }else{
       	    $("#"+currentId).slideUp(200); 
        }
	});
} 
</script>	

<script type="text/javascript">
		function bodyClick(){
		    $(".teskcomment").slideUp(200);   
		    $("html .timesheet_comm").click(function(event){
		        event.stopPropagation();
		    }); 
		 }
</script>	



<!-- this script is for add row functionality at Timesheet screen -->
<script>
var validationSettings = {
        errorMessagePosition : 'element',
       borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd' 
}; 
function addTimeSheetRow(projectId,leaveIndex) {
	 $('#0docFile').removeAttr('data-validation');
	 $('#0docFile').attr("data-validation-optional","true"); 
	 if ($('#timesheet_form').validate(false, validationSettings)&&!$("#timeSheetErrorMsg").is(':visible')){   
		    var nonLeaveRowId = 0;
		    var tbl = document.getElementById("timesheet");
			var rowNum = $("#maxRowNumber").val();
			var maxRowNum = parseInt(rowNum);
			maxRowNum = maxRowNum +1;
			$("#maxRowNumber").val(maxRowNum); 
			var row = tbl.insertRow(maxRowNum);
			row.setAttribute("id", "timesheetRow"+maxRowNum);
			
		    var previousMaxRowNum =parseInt(rowNum-1);
		    
		    var entryIndex = $("#entryIndexValue").val();
			var cell0 = row.insertCell(0);
			var divadttTd0Str = document.getElementById("divadttTd1").innerHTML;
			divadttTd0Str = divadttTd0Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd0Str = divadttTd0Str.replace(/projectId/g, (projectId)); 
			divadttTd0Str = divadttTd0Str.replace(/ENTRYINDEX/g, (entryIndex)); 
			/* cell0.setAttribute("class", "pname");*/
			cell0.innerHTML = divadttTd0Str;
			$("#"+maxRowNum+"task1Id").attr('data-validation','required');
			$("#"+maxRowNum+"task1Id").addClass('selectpicker');
			$("#"+maxRowNum+"task1Id").attr('data-live-search','true');
			$("#"+maxRowNum+"task1Id").attr('title','--select task--');
			$("#"+maxRowNum+"task1Id").attr('data-size',8);
			
			var cell1 = row.insertCell(1);
			var divadttTd1Str = document.getElementById("divadttTd2").innerHTML;
			divadttTd1Str = divadttTd1Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd1Str = divadttTd1Str.replace(/projectId/g, (projectId)); 
			divadttTd1Str = divadttTd1Str.replace(/ENTRYINDEX/g, (entryIndex)); 
			cell1.innerHTML = divadttTd1Str;
			$("#"+maxRowNum+"task1IdBillableTypeId").attr('data-validation','required');
			$("#"+maxRowNum+"task1IdBillableTypeId").addClass('selectpicker');
			$("#"+maxRowNum+"task1IdBillableTypeId").attr('title','BillableType');
			$("#"+maxRowNum+"task1IdBillableTypeId").attr('data-size',8);
			
			
			var cell2 = row.insertCell(2);
			var divadttTd12Str = document.getElementById("divadttTd12").innerHTML;
			divadttTd12Str = divadttTd12Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd12Str = divadttTd12Str.replace(/ENTRYINDEX/g, (entryIndex)); 
			divadttTd12Str = divadttTd12Str.replace(/disabled/g, ''); 
			cell2.innerHTML = divadttTd12Str;
			$("#"+maxRowNum+"hourlyType1Id").attr('data-validation','required');
			$("#"+maxRowNum+"hourlyType1Id").addClass('selectpicker');
			$("#"+maxRowNum+"hourlyType1Id").attr('style','width:220px;');
			$("#"+maxRowNum+"hourlyType1Id").attr('data-size',8);
			
			
			var cell3 = row.insertCell(3);
			var divadttTd10Str = document.getElementById("divadttTd10").innerHTML;
			divadttTd10Str = divadttTd10Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd10Str = divadttTd10Str.replace(/projectId/g, (projectId)); 
			divadttTd10Str = divadttTd10Str.replace(/ENTRYINDEX/g, (entryIndex)); 
			cell3.innerHTML = divadttTd10Str;
			
			
		    var cell4 = row.insertCell(4);
			var isCell1Disabled = $("#1timeSheetDay1Id").prop('disabled');
			if(isCell1Disabled){
				$("#divadttTd3 :input[type=text]").attr('disabled','disabled');
				$('.timesheet_comm','#divadttTd3').find('i').attr('class','glyphicon');
			}
			else{
				$("#divadttTd3 :input").removeAttr('disabled');
				$('.timesheet_comm','#divadttTd3').find('i').attr('class','glyphicon glyphicon-comment');
			} 
			var divadttTd2Str = document.getElementById("divadttTd3").innerHTML;
			divadttTd2Str = divadttTd2Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd2Str = divadttTd2Str.replace(/projectId/g, (projectId));
			divadttTd2Str = divadttTd2Str.replace(/ENTRYINDEX/g, (entryIndex)); 
			
			var dateValue = $("[name='timesheetSubmission.timesheetEntries[0].date']").val();
			var dayOfDate =  $("[name='timesheetSubmission.timesheetEntries[0].dayOfTheDate']").val();
			divadttTd2Str = divadttTd2Str.replace(/DATEVALUE/g, (dateValue)); 
			divadttTd2Str = divadttTd2Str.replace(/DAYOFTHEDATE/g, (dayOfDate)); 
			cell4.innerHTML = divadttTd2Str;
			
			entryIndex = parseInt(entryIndex)+1;
			var cell5 = row.insertCell(5);
			var isCell2Disabled = $("#1timeSheetDay2Id").prop('disabled');
			if(isCell2Disabled){
				$("#divadttTd4 :input[type=text]").attr('disabled','disabled');
				$('.timesheet_comm','#divadttTd4').find('i').attr('class','glyphicon');
			}
			else{
				$("#divadttTd4 :input").removeAttr('disabled');
				$('.timesheet_comm','#divadttTd4').find('i').attr('class','glyphicon glyphicon-comment');
			} 
			var divadttTd3Str = document.getElementById("divadttTd4").innerHTML;
			divadttTd3Str = divadttTd3Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd3Str = divadttTd3Str.replace(/projectId/g, (projectId));
			divadttTd3Str = divadttTd3Str.replace(/ENTRYINDEX/g, (entryIndex)); 
			
		    dateValue = $("[name='timesheetSubmission.timesheetEntries[1].date']").val();
		    dayOfDate =  $("[name='timesheetSubmission.timesheetEntries[1].dayOfTheDate']").val();
			divadttTd3Str = divadttTd3Str.replace(/DATEVALUE/g, (dateValue)); 
			divadttTd3Str = divadttTd3Str.replace(/DAYOFTHEDATE/g, (dayOfDate)); 
			cell5.innerHTML = divadttTd3Str;
			
			entryIndex = parseInt(entryIndex)+1;
			var cell6 = row.insertCell(6);
			var isCell3Disabled = $("#1timeSheetDay3Id").prop('disabled');
			if(isCell3Disabled){
				$("#divadttTd5 :input[type=text]").attr('disabled','disabled');
				$('.timesheet_comm','#divadttTd5').find('i').attr('class','glyphicon');
			}
			else{
				$("#divadttTd5 :input").removeAttr('disabled');
				$('.timesheet_comm','#divadttTd5').find('i').attr('class','glyphicon glyphicon-comment');
			} 
			var divadttTd4Str = document.getElementById("divadttTd5").innerHTML;
			divadttTd4Str = divadttTd4Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd4Str = divadttTd4Str.replace(/projectId/g, (projectId));
			divadttTd4Str = divadttTd4Str.replace(/ENTRYINDEX/g, (entryIndex)); 
			
			dateValue = $("[name='timesheetSubmission.timesheetEntries[2].date']").val();
		    dayOfDate =  $("[name='timesheetSubmission.timesheetEntries[2].dayOfTheDate']").val();
			divadttTd4Str = divadttTd4Str.replace(/DATEVALUE/g, (dateValue)); 
			divadttTd4Str = divadttTd4Str.replace(/DAYOFTHEDATE/g, (dayOfDate)); 
			cell6.innerHTML = divadttTd4Str;
			
			entryIndex = parseInt(entryIndex)+1;
			var cell7= row.insertCell(7);
			 var isCell4Disabled = $("#1timeSheetDay4Id").prop('disabled');
			if(isCell4Disabled){
				$("#divadttTd6 :input[type=text]").attr("disabled", "disabled");
				$('.timesheet_comm','#divadttTd6').find('i').attr('class','glyphicon');
			}else{
				$("#divadttTd6 :input").removeAttr('disabled');
				$('.timesheet_comm','#divadttTd6').find('i').attr('class','glyphicon glyphicon-comment');
			} 
			var divadttTd5Str = document.getElementById("divadttTd6").innerHTML;
			divadttTd5Str = divadttTd5Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd5Str = divadttTd5Str.replace(/projectId/g, (projectId)); 
			divadttTd5Str = divadttTd5Str.replace(/ENTRYINDEX/g, (entryIndex));
			
			dateValue = $("[name='timesheetSubmission.timesheetEntries[3].date']").val();
		    dayOfDate =  $("[name='timesheetSubmission.timesheetEntries[3].dayOfTheDate']").val();
			divadttTd5Str = divadttTd5Str.replace(/DATEVALUE/g, (dateValue)); 
			divadttTd5Str = divadttTd5Str.replace(/DAYOFTHEDATE/g, (dayOfDate)); 
			cell7.innerHTML = divadttTd5Str;
			
			entryIndex = parseInt(entryIndex)+1;
			var cell8 = row.insertCell(8);
			var isCell5Disabled = $("#1timeSheetDay5Id").prop('disabled'); 
			 if(isCell5Disabled){
				$("#divadttTd7 :input[type=text]").attr("disabled", "disabled");
				$('.timesheet_comm','#divadttTd7').find('i').attr('class','glyphicon');
			}
			else{
				$("#divadttTd7 :input").removeAttr('disabled');
				$('.timesheet_comm','#divadttTd7').find('i').attr('class','glyphicon glyphicon-comment');
			}
			var divadttTd6Str = document.getElementById("divadttTd7").innerHTML; 
			divadttTd6Str = divadttTd6Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd6Str = divadttTd6Str.replace(/projectId/g, (projectId)); 
			divadttTd6Str = divadttTd6Str.replace(/ENTRYINDEX/g, (entryIndex));
			
			dateValue = $("[name='timesheetSubmission.timesheetEntries[4].date']").val();
		    dayOfDate =  $("[name='timesheetSubmission.timesheetEntries[4].dayOfTheDate']").val();
			divadttTd6Str = divadttTd6Str.replace(/DATEVALUE/g, (dateValue)); 
			divadttTd6Str = divadttTd6Str.replace(/DAYOFTHEDATE/g, (dayOfDate)); 
			cell8.innerHTML = divadttTd6Str;
			
			entryIndex = parseInt(entryIndex)+1;
			var cell9 = row.insertCell(9);
			var isCell6Disabled = $("#1timeSheetDay6Id").prop('disabled');
			if(isCell6Disabled){
				$("#divadttTd8 :input[type=text]").attr("disabled", "disabled");
				$('.timesheet_comm','#divadttTd8').find('i').attr('class','glyphicon');
			}
			else{
				$("#divadttTd8 :input").removeAttr('disabled');
				$('.timesheet_comm','#divadttTd8').find('i').attr('class','glyphicon glyphicon-comment');
			} 
			var divadttTd7Str = document.getElementById("divadttTd8").innerHTML;
			divadttTd7Str = divadttTd7Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd7Str = divadttTd7Str.replace(/projectId/g, (projectId));
			divadttTd7Str = divadttTd7Str.replace(/ENTRYINDEX/g, (entryIndex)); 
			
			dateValue = $("[name='timesheetSubmission.timesheetEntries[5].date']").val();
		    dayOfDate =  $("[name='timesheetSubmission.timesheetEntries[5].dayOfTheDate']").val();
			divadttTd7Str = divadttTd7Str.replace(/DATEVALUE/g, (dateValue)); 
			divadttTd7Str = divadttTd7Str.replace(/DAYOFTHEDATE/g, (dayOfDate)); 
			cell9.innerHTML = divadttTd7Str;
			
			entryIndex = parseInt(entryIndex)+1;
			var cell10 = row.insertCell(10);
		    var isCell7Disabled = $("#1timeSheetDay7Id").prop('disabled');
			if(isCell7Disabled){
				$("#divadttTd9 :input[type=text]").attr("disabled", "disabled");
				$('.timesheet_comm','#divadttTd9').find('i').attr('class','glyphicon');
			}else{
				$("#divadttTd9 :input").removeAttr('disabled');
				$('.timesheet_comm','#divadttTd9').find('i').attr('class','glyphicon glyphicon-comment');
			} 
			var divadttTd8Str = document.getElementById("divadttTd9").innerHTML;
			divadttTd8Str = divadttTd8Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd8Str = divadttTd8Str.replace(/projectId/g, (projectId));
			divadttTd8Str = divadttTd8Str.replace(/ENTRYINDEX/g, (entryIndex)); 
			
			dateValue = $("[name='timesheetSubmission.timesheetEntries[6].date']").val();
		    dayOfDate =  $("[name='timesheetSubmission.timesheetEntries[6].dayOfTheDate']").val();
			divadttTd8Str = divadttTd8Str.replace(/DATEVALUE/g, (dateValue)); 
			divadttTd8Str = divadttTd8Str.replace(/DAYOFTHEDATE/g, (dayOfDate));
			cell10.innerHTML = divadttTd8Str;
			
			entryIndex = parseInt(entryIndex)+1;
			$("#entryIndexValue").val(entryIndex);
		    maskme();
		    
			var projectTaskId = maxRowNum+"task1Id"; 
			getProjectTasks(projectTaskId);  
			
			$('.selectpicker').selectpicker('refresh');
		    $('.selectpicker').selectpicker({dropupAuto: false});
		   var isTimesheetAttachmentRequired = $("#isTimesheetAttachmentRequired").val();
		   if(isTimesheetAttachmentRequired != null && isTimesheetAttachmentRequired != "" && isTimesheetAttachmentRequired !="undefined" && isTimesheetAttachmentRequired == "Y"){
			 $('#0docFile').removeAttr('data-validation-optional');
			 $('#0docFile').attr("data-validation","required"); 
			 $(".time_btn").removeAttr("disabled");
			 $("#loadTime").removeAttr("style"); 
		   }
	    }	
	}	
        
        function disableInput(divId){
        	$("#"+divId+" :input").attr("disabled", "disabled");
        } 
       
        function prepareProjectTaskDetails(entryIndex,projectTaskId){
        	 if($("#" + projectTaskId).val() != null && $("#" + projectTaskId).val() != "" && $("#" + projectTaskId).val() !="undefined"){
        		   var taskId = $("#" + projectTaskId).val().split(",");
    			   $('select[name="'+ projectTaskId +'BillableTypeId"]').selectpicker('val', taskId[1]);
    			   setTotals(); 
	  			   var taskDetails = $("#" + projectTaskId).val().split(",");
	  			   var taskId = taskDetails[0];
	        	   var taskType   = taskDetails[1];
	          	   var taskName = taskDetails[2]; 
	          	   var end = parseInt(entryIndex)+7;
	          	  for (i = entryIndex; i < end; i++) {
		          	   $("[name='timesheetSubmission.timesheetEntries["+i+"].timesheetTaskDTO.id']").val(taskId)
		          	   $("[name='timesheetSubmission.timesheetEntries["+i+"].timesheetTaskDTO.taskName']").val(taskName)
		          	   $("[name='timesheetSubmission.timesheetEntries["+i+"].timesheetTaskDTO.taskType']").val(taskType)
	              } 
        	 }
        }
        function prepareBillableDetails(entryIndex,billableTypeId){
        	  var end = parseInt(entryIndex)+7;
         	  for (i = entryIndex; i < end; i++) {
         		 $("[name='timesheetSubmission.timesheetEntries["+i+"].timesheetTaskDTO.taskType']").val($("#"+ billableTypeId).val());
         	  }
         	 setTotals(); 
        }
	   //get project tasks 
	   function getProjectTasks(projectTaskId){
		   var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
		   if($("#" + projectTaskId).val() != null && $("#" + projectTaskId).val() != "" && $("#" + projectTaskId).val() !="undefined"){
			   var taskId = $("#" + projectTaskId).val().split(",");
			   $("#" + projectTaskId + "BillableTypeId").val(taskId[1]);
			   setTotals(); 
		   }else{
			   $("#" + projectTaskId + "BillableTypeId").val(""); 
			   setTotals(); 
		   }
		   var result = "";
		   <c:forEach var="pt" items="${timesheetSubmissionDTO.timesheetTaskList}">
		      var taskId = '${pt.id}';
		      var taskType = '${pt.taskType}';
		      var taskName = '${fn:replace(pt.taskName, "'", "`")}';
		      var generatedId = '${pt.generatedId}';
		     
		      if(generatedId != null && generatedId !='undefined' && generatedId !=''){
        			result = result + "<option value='" +taskId+","+taskType+","+taskName+"'>"+generatedId+"-"+taskName+"</option>";
              }else{
        			result = result + "<option value='" +taskId+","+taskType+","+taskName+"'>"+taskName+"</option>";
        	  }
		   </c:forEach>
		   $("#"+projectTaskId).append(result);
	   }
</script>

<!-- this script is for remove timesheet -->
<script type="text/javascript">	
function removeTimeSheetRow(rowId) { 
			var rowNum = $("#maxRowNumber").val();
			var maxRowNum = parseInt(rowNum);
			 document.getElementById("timesheetRow"+rowId).style.display = 'none';
			 document.getElementById("timesheetRow"+rowId).value= '';
			 var  rId = "timesheetRow"+rowId;
			  var r = document.getElementById("timesheet").rows[rId];
	          var count = (r.cells.length);
		      for(i=0;i<count;i++){
		        r.deleteCell(0);
		      } 
		      setTotals(); 
		}
		
</script>	
<!-- this script is for alert message of dropping holiday -->
<script type="text/javascript">	
   	    function alertHolidayDropMessage(rowId,holidayId,holidayUserId,holidayUserLocationId){
   		    var message = "<b>Are you sure to remove the holiday<b>";
   		    $("#timesheetDivByFrequency").append("<div id='choicepopUp'></div>");
   			var result = "";
   			result = result +"	<div class='project_inn_mask'></div>";
   			result = result +"	  <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
   			result = result +"		<p>"+message+"</p>";
   			result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
   			result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='removeHolidayRow("+rowId+","+holidayId+","+holidayUserId+","+holidayUserLocationId+");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
   			result = result +"	</div>";
   			$("#choicepopUp").append(result);
   			$("#remove_user_pop").slideDown(200);
   			$(".project_inn_mask").fadeIn(200);
   		 }
</script>	    	 
		    
 <!-- this script is for  dropping holiday for an particular employee -->
<script type="text/javascript">	
function removeHolidayRow(rowId,holidayId,holidayUserId,holidayUserLocationId) { 
	         var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
	         var rowNum = $("#maxRowNumber").val();
		     var maxRowNum = parseInt(rowNum);
				
			 document.getElementById("timesheetRow"+rowId).style.display = 'none';
			 document.getElementById("timesheetRow"+rowId).value= '';
			 var  rId = "timesheetRow"+rowId;
			  var r = document.getElementById("timesheet").rows[rId];
	          var count = (r.cells.length);
	         
		      for(i=0;i<count;i++){
		        r.deleteCell(0);
		      } 
		      setTotals();
		 	  $("#loadTime").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:60% center; background-repeat:no-repeat; background-size:75px;');
		  	  $.ajax({
		 	      type : "POST",
		      	  url: sourceleadUrl+"/dropHoliday?${_csrf.parameterName}=${_csrf.token}&holidayId="+holidayId+"&holidayUserId="+holidayUserId+"&holidayUserLocationId="+holidayUserLocationId,		
		 	      success :function(result) {
		 	    	   $("#choicepopUp").empty();
		 	    	  $(".project_inn_mask").fadeOut(200);
		 	    	  $("#loadTime").removeAttr("style"); 
		 	      }
		    }); 
		 	
		}
		
</script>

<!-- this script is for clear timesheet -->
<script>
function clearTimesheet(){ 
		  timesheetDivByFrequency('${timesheetSubmissionDTO.timeSubFrequency}');
}
</script>
<!-- this script is for to set hours while filling timesheet  -->
<script>
function editMe(local){
	     var id = local.id;
	     var inputValue =  $("#"+id).val();
		 var split = inputValue.split(':');
		 var hours = split[0];
		 var minutes = split[1];
		 var hours1 = hours.charAt(0);
		 var hours2 = hours.charAt(1);
		 var min1 = minutes.charAt(0);
		 var min2 = minutes.charAt(1);
		 if(!isNaN(hours1) && isNaN(hours2) && isNaN(min1) && isNaN(min2)){
			 $("#"+id).val("0"+hours1+":00");
			 setTotals();
			 checkHours(); 
		 }
		 if(isNaN(hours1) && !isNaN(hours2) && isNaN(min1) && isNaN(min2)){
			 $("#"+id).val("0"+hours2+":00");
			 setTotals();
			 checkHours(); 
		 }
		 else if(isNaN(hours)  && !isNaN(min1) && isNaN(min2)){
			 $("#"+id).val("00:"+min1+"0");
			 setTotals();
			 checkHours(); 
		 }
		 else if(isNaN(hours)  && isNaN(min1) && !isNaN(min2)){
			 $("#"+id).val("00:0"+min2);
			 setTotals();
			 checkHours(); 
		 }
		 else if(!isNaN(hours)  && isNaN(min1) && isNaN(min2)){
			 $("#"+id).val(hours+":00");
			 setTotals();
			 checkHours(); 
		 }
		 else if(!isNaN(hours)&& !isNaN(min1) && isNaN(min2)){
			 $("#"+id).val(hours+":"+"0"+min1);
			 setTotals();
			 checkHours(); 
		 }
		 else if(!isNaN(hours)&& isNaN(min1) && !isNaN(min2)){
			 $("#"+id).val(hours+":"+"0"+min2);
			 setTotals();
			 checkHours(); 
		 }
		 else if(!isNaN(hours)&& !isNaN(minutes)){
			  var hrmin= hours * 60;
			  var totalMin = parseInt(hrmin) + parseInt(minutes);
			  hours = Math.floor(totalMin / 60); 
			  minutes = totalMin % 60;
			  if(hours<=9){
				  hours = "0"+hours;
			  }
			  if(minutes<=9){
				  minutes = "0"+minutes;
			  }
			  $("#"+id).val(hours+":"+minutes);
			  setTotals();
			  checkHours(); 
		 }else if(isNaN(hours1) && isNaN(hours2) && isNaN(min1) && isNaN(min2)){
		     $("#"+id).val("00:00");
		     setTotals();
		     checkHours(); 
		 }
}

function checkHours(){ 
	rowRemoveError();
	 if ($('#timesheet_form').validate(false, validationSettings)){ 
		 rowValidation();
	 }
	 var result = "";		
	 var total; 
	 var rowNum = $("#maxRowNumber").val();
	 var jValue = parseInt(rowNum)+1;
	 for (var i = 1; i < 8; i++){
		 var bsum = 0;
		 var nbsum = 0;
		 var total = 0;
		 for (var j = 1; j < jValue; j++){  
			    var btemp = $("#"+j+"timeSheetDay"+i+"Id").val();
			    if (btemp !== undefined && btemp !=='####' && btemp !=='') {
			    	 var hours = btemp;
			    	 var split = hours.split(':');
			    	 var first = split[0];
			    	 var second = split[1];
					 var minute = first * 60;
					 var minute1 = second;
					 var finalmin = parseInt(minute) + parseInt(minute1);
			    	 
				     bsum = parseFloat(bsum) + parseInt(finalmin);
				}
			    
		 }
	    var t = bsum; 
		var hours = Math.floor(t / 60); 
		var minutes = t % 60;
		total = hours +":"+ minutes;
	 var time = total.split(':'); 
	 if(!isNaN(time[0])){
	 if(time.length === 2 && parseInt(time[0],10)>=0 && parseInt(time[0],10)<=23  && parseInt(time[1],10)>=0  && parseInt(time[1],10)<=59){
	    	 
	 }else{
	    	if(i == '1'){
	    		result = result+"Mon ,";
	    	}
	    	if(i == '2'){  
	    		result = result+"Tue ,";
	    	}
	    	if(i == '3'){  
	    		result = result+"Wed ,";
	    	}
	    	if(i == '4'){  
	    		result = result+"Thu ,";
	    	}
	    	if(i == '5'){
	    		result = result+"Fri ,";
	    	}
	    	if(i == '6'){
	    		result = result+"Sat ,";
	    	}
	    	if(i == '7'){
	    		result = result+"Sun ,";
	    	}
	    	
	 }
   }
}   
	if(result != "" && result != null && result != 'undefined'){
		var outPut = "<p>"+result+" hours should not be more than 24<b>X</b></p>";
		$("#timeSheetErrorMsg").removeAttr('style');
		$("#timeSheetErrorMsg").attr('style','display:block;');
		$("#timeSheetErrorMsg").empty();
		$("#timeSheetErrorMsg").append(outPut);
		var error = "<input  type='hidden' name='timesheetHiddenError' id='timesheetHiddenError'  data-validation='required' />";
		$("#hideError").empty();
		$("#hideError").append(error);
	 }else{
	    $("#timeSheetErrorMsg").removeAttr('style');
	    $("#timeSheetErrorMsg").attr('style','display:none;');
		$("#timeSheetErrorMsg").empty();
		$("#hideError").empty();
	} 
	
	$(".time_pop b").click(function(){
		$("#timeSheetErrorMsg").removeAttr('style');
		$("#timeSheetErrorMsg").attr('style','display:none;');
	    $("#timeSheetErrorMsg").empty();
	}); 
	
	
}
</script>

<!--this script is for rowvalidation,whether the row is filled with hours or not -->
<script>
   function rowValidation(){ 
	     var rowNum = $("#maxRowNumber").val();
		 var jValue = parseInt(rowNum)+1;
		 for (var j = 1; j < jValue; j++){
			 var isEmpty = true;
			 for (var i = 1; i < 8; i++){
				 var btemp = $("#"+j+"timeSheetDay"+i+"Id").val();
				 if (btemp !== undefined && btemp !=='####' && btemp !=='') {
					 isEmpty = false;
					 break;
				 }
			 }
			 if(isEmpty == true){
				 $("#"+j+"taskTd").find('p.jquery_form_error_message').remove(); 
				 $("#"+j+"task1Id").after("<p class='jquery_form_error_message'>Atleat One Date Should Be Filled</span>");
				 $("#"+j+"task1Id").attr('record-exist','yes');
				 $("#"+j+"task1Id").attr('record-exist-errorMsg','Atleat One Date Should Be Filled');
			 }else{
				 $("#"+j+"task1Id").removeAttr( "record-exist record-exist-errormsg");
				 $("#"+j+"taskTd").find('p.jquery_form_error_message').remove();  
			 }
		 }
   }

</script>
<!-- this script is for  remove error -->
<script>
   function rowRemoveError(){ 
	     var rowNum = $("#maxRowNumber").val();
		 var jValue = parseInt(rowNum)+1;
		 for (var j = 1; j < jValue; j++){
			 $("#"+j+"task1Id").removeAttr( "record-exist record-exist-errormsg");
			 $("#"+j+"taskTd").find('p.jquery_form_error_message').remove();  
		 }
   }
</script>
<!--this script is for add attachment,for an empty row  -->
<script>
function addAttachment(){  
			var checkMaxTsRowNum = $("#attcahmentMaxRowNumber").val();
			var tbl = document.getElementById("attachmentTable");
			var maxRowNum = parseInt($("input[name = 'attcahmentMaxRowNumber']").val());
			$("input[name = 'attcahmentMaxRowNumber']").val(maxRowNum + 1);
			
			
			var row = tbl.insertRow(maxRowNum);
			row.setAttribute("id", "divAttachmentTsRow" + maxRowNum);
			
			
			var cell0 = row.insertCell(0);
			var divAttachmentTdStr = document.getElementById("divAttachmentTd1").innerHTML;
			divAttachmentTdStr = divAttachmentTdStr.replace(/REPLACEROWID/g, (maxRowNum));
			cell0.innerHTML = divAttachmentTdStr;
}			
</script>
<!--this script is for remove attachment,from an empty row  -->
<script type="text/javascript">
	function removeAttachmentRow(rowId) {
		 document.getElementById("divAttachmentTsRow"+rowId).style.display = 'none';
		 document.getElementById("divAttachmentTsRow"+rowId).value= '';
		 var  rId = "divAttachmentTsRow"+rowId;
		  var r = document.getElementById("attachmentTable").rows[rId];
          var count = 2*(r.cells.length);
		    for(i=0;i<=count;i++){
		        r.deleteCell(0);
		    } 
		 
	}
</script>

<!-- this script is for delete document from db-->
<script type="text/javascript">
function deleteSubmissionDocument(submissionDocumentId){  
	 var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
    var projectExistingValue = $("#dbIsTimesheetAttachmentRequired").val();
    
    var strongTagCount = document.getElementById('timesheetDocumentsId').getElementsByTagName('strong').length;
	var navigation;
    $.ajax({
          type : "POST",
      	  url: sourceleadUrl+"/deleteSubmissionDocument?${_csrf.parameterName}=${_csrf.token}&submissionDocumentId="+submissionDocumentId,		
	      success :function(result) {
	    	  $("#"+submissionDocumentId+"document").remove();
	    	  if(projectExistingValue != null && projectExistingValue != "" && projectExistingValue !="undefined" && projectExistingValue == "1"
	    			  && strongTagCount != null && strongTagCount != "" && strongTagCount !="undefined" && strongTagCount == 1){
	    		  $("#isTimesheetAttachmentRequired").val('Y');
	    	  }
	      }
      	  
    }); 
	   
}
</script>
<!-- this script is for view document-->
<script type="text/javascript">
function viewSubmissionDocument(submissionDocumentId,title){
	 var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
		window.open(sourceleadUrl+'/viewDocument/'+title+'?submissionDocumentId='+submissionDocumentId,title,"height=600,width=600");
}
</script>

<!--this script is for to validate a uploaded file, with in the existing list of extensions  -->
<script type="text/javascript">
function validate_fileupload(fileName)
{

    var allowed_extensions = new Array("ppt", "pptx", "pps", "ppsx", "doc", "docx", "pdf", "rtf", "odt", "xls", "xlsx", "csv", "zip", "txt", "jpg","jpeg","bmp","png","gif","eps","tiff","TGA","psd","svg","msg","wpd","wps");
    var file_extension = fileName.split('.').pop(); 

    for(var i = 0; i <= allowed_extensions.length; i++)
    {
        if(allowed_extensions[i] != null && allowed_extensions[i] != '' && allowed_extensions[i] != 'undefined' && allowed_extensions[i]==file_extension.toLowerCase())
        {
            return true; // valid file extension
        }
    }

    return false;
}
</script>

<script type="text/javascript">
function validateUploadFile(id)
{
	  var selectedFile = $('#'+id+'docFile').val();
	  if(validate_fileupload(selectedFile)){
		   var docSize = $('#'+id+'docFile')[0].files[0].size;
	       $("#"+id+"docFileuploadFileGroup").find('p.jquery_form_error_message').remove();
	      if(docSize > 2097152){
	    	  $('#'+id+'docFileuploadFileGroup').find('p.jquery_form_error_message').remove();
	    	  $('#'+id+'docFileuploadFileGroup').find('i').after("<p class='jquery_form_error_message'>Limit is exceeded than 2MB </span>");
			  $('#'+id+'docFile').attr('record-exist','yes');
			  $('#'+id+'docFile').attr('record-exist-errorMsg','Limit is exceeded than 2MB'); 
	      }else{
	    	 $('input#'+id+'docFile').removeAttr( "record-exist record-exist-errormsg");
   		 	 $('#'+id+'docFileuploadFileGroup').find('p.jquery_form_error_message').remove();
   		 	 fileUpload(""+id+"docFile", "timesheetDocuments", "timesheet_form","timesheetSubmission.fileInfo","","");
	      }
	  }
	  else{
		      $('#'+id+'docFileuploadFileGroup').find('p.jquery_form_error_message').remove();
		      $('#'+id+'docFileuploadFileGroup').find('i').after("<p class='jquery_form_error_message'>Invalid Format</span>");
			  $('#'+id+'docFile').attr('record-exist','yes');
			  $('#'+id+'docFile').attr('record-exist-errorMsg','Invalid Format'); 
	  }
	
}
</script>

<!--this script is for alert message,if there is any unfilled tabs are there(used in bi-weekly,semi-monthly,monthly cases)  -->
<script type="text/javascript">
   function alertMessage(message,status,savingType){
   $("#timesheetDivByFrequency").append("<div id='choicepopUp'></div>");
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	  <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>"+message+"</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='timesheetDbSave(\""+status+"\",\""+savingType+"\");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
	result = result +"	</div>";
	$("#choicepopUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);

  }
</script>

<script type="text/javascript">
function cancel(){
	$("#choicepopUp").remove(); 
	$(".project_inn_mask").fadeOut(200);
    $(".time_btn").removeAttr("disabled");
	$("#loadTime").removeAttr("style"); 
}
</script>

<script>
function dropDownExport(){
	$("#exporrtEntries").slideToggle();
}
</script>

<!--this script is for saving timesheet using status like submit,approve -->
<script type="text/javascript">
function saveTimesheet(status){ 
	 var savingType = $("#timesheetFor").val();
	 $(".time_btn").attr("disabled", "disabled");
	 $("#loadTime").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:60% center; background-repeat:no-repeat; background-size:75px;');
	 checkHours();
	 var isTimesheetAttachmentRequired = $("#isTimesheetAttachmentRequired").val();
	 if(isTimesheetAttachmentRequired != null && isTimesheetAttachmentRequired != "" && isTimesheetAttachmentRequired !="undefined" && isTimesheetAttachmentRequired == "Y"){
		 $('#0docFile').removeAttr('data-validation-optional');
		 $('#0docFile').attr("data-validation","required"); 
		 $(".time_btn").removeAttr("disabled");
		 $("#loadTime").removeAttr("style"); 
	 }
	 var unfilledDates = [];
	 $('ul.project-tabs-menu li').each(function() {
		 var attr = $(this).attr('class');
		 if (attr!= null && attr != "" && attr != " " && attr !='undefined' && attr != 'hidden') {}
		 else{
			 var currentInputValue = $(this).prev("input").val();
			 if(currentInputValue != null && currentInputValue !='undefined' && currentInputValue != ''){
				 unfilledDates.push(currentInputValue);
			 }
			
		 }	
     });
	
	 var validationSettings = {
		        errorMessagePosition : 'element',
		       borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd' 
	 }; 
	 if ($('#timesheet_form').validate(false, validationSettings)&&!$("#timeSheetErrorMsg").is(':visible')){ 
		 
		     if(status != null && status != 'undefined' && status != '' && ((status == 'Submitted' && unfilledDates.length === 0) || (status == 'approve')|| (status == 'Draft'))){
		    	   timesheetDbSave(status,savingType)
	        	   return true;
		     }
		     else{
		    	 $("#loadTime").removeAttr("style"); 
		    	 var message = "<b>Are you sure to submit the timesheets, the following frequencies haven't updated<b>";
		    	 for(var i=0;i<unfilledDates.length;i++){
		    		 message = message+"<br/>"+unfilledDates[i];
		    	 }
		    	 alertMessage(message,status,savingType);
		    	 return false;
		     }
              
	 	}else{ 
	 		 $(".time_btn").removeAttr("disabled");
	 		 $("#loadTime").removeAttr("style"); 
			return false;
	   } 
}
</script>
<script>
 function timesheetDbSave(status,savingType){
	     $("#choicepopUp").empty(); 
	     $("#choicepopUp").remove(); 
		 $(".project_inn_mask").fadeOut(200);
	    
		 var form = $('#timesheet_form')[0];
    	 var formData = new FormData(form); 
    	 
    	 var currentTimeFrequency = $("#currentTimeFrequency").val();
    	 var tableFor = $("#tableFor").val();
    	 var currentTimeSubFrequency = $("#currentTimeSubFrequency").val();
    	 var projectMemberId = $("#projectMemberId").val();
    	 var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
    	 $("#loadTime").removeAttr('style');
    	 $("#loadTime").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:60% center; background-repeat:no-repeat; background-size:75px;');
	      var actualStatus = status;
	      if(actualStatus == 'approve'){
	    	  status = 'Submitted';
	      }
    	 $.ajax({
		    type : "POST",
   	        data: formData,
	        processData: false,
	        contentType:false,
	      	  url: sourceleadUrl+"/saveTimesheetEntries?timesheetSubmission.timesheetStatus="+status+"&timesheetFor="+savingType,		
		      success :function(data) {
		    	  if(actualStatus == 'approve'){
		    		  status = actualStatus;
		    	  }
		    	  if($("#timesheet_form input[data-id*=docFile]").length > 0) {
		  			 $("#timesheet_form input[data-id*=docFile]").remove();
		  		   }
		    	   
		    	  if(savingType != null && savingType != '' && savingType != 'undefined'&& savingType == 'approver'){
		    		  if(status != null && status != 'undefined' && status != '' && status != 'Draft' && status != 'Submitted' && status != 'approve'){
			    		  timesheetApprovalTableRowByProjectMemberId(currentTimeFrequency,projectMemberId,tableFor);
			    	  }else if(status == 'approve'){
			    			 var tdSpan = '';
			    			 if(tableFor != null && tableFor != '' && tableFor != 'undefined' && tableFor =='dashboard'){
			    				 tableFor = 'approval';
			    				 tdSpan ='8' 
			    			 }else {
			    				 tableFor = 'approval';
			    				 tdSpan = '6';
			    			 }
			    			 var dates = currentTimeFrequency.split("To");
			    		     approveSubmissionByProjectMemberId(projectMemberId,dates[0],dates[1],tdSpan,tableFor+"Summary");
			    		     $(".time_appr_side_wrap").removeClass("time_appr_side_wrap_active");
			    			 $(".side_mask").fadeOut(200); 
			    			 $("body").css('overflow-y', 'scroll');
			    			 $("#dynamic").removeAttr('style');
			    	  }
			    	  else{
			    		  $("#timesheetDivByFrequency").empty();
				    	  $("#timesheetDivByFrequency").append(data);
				    	  setTotals();
        		    	  maskme();
				    	  if(currentTimeSubFrequency != null && currentTimeSubFrequency != '' && currentTimeSubFrequency != 'undefined'){
				    		  $("#currentTimeSubFrequency").val('');
				    		  $("#timeSubFrequency").val('');
				    		  $("#currentTimeSubFrequency").val(currentTimeSubFrequency);
			            	  $("#timeSubFrequency").val(currentTimeSubFrequency)
				    	  }
				    	  $("#loadTime").removeAttr('style');
			    	  } 
		    	  }else{
		    		  if(status == 'approve'){
		    			     var  tdSpan ='8';
			    			 var dates = currentTimeFrequency.split("To");
			    		     approveSubmissionByProjectMemberId(projectMemberId,dates[0],dates[1],tdSpan,tableFor);
		    		  }else{
		    			  $("#timesheetDivByFrequency").empty();
				    	  $("#timesheetDivByFrequency").append(data);
				    	  setTotals();
	    		    	  maskme();
				    	  if(currentTimeSubFrequency != null && currentTimeSubFrequency != '' && currentTimeSubFrequency != 'undefined'){
				    		  $("#currentTimeSubFrequency").val('');
				    		  $("#timeSubFrequency").val('');
				    		  $("#currentTimeSubFrequency").val(currentTimeSubFrequency);
			            	  $("#timeSubFrequency").val(currentTimeSubFrequency);
				    	  }
				    	  $("#loadTime").removeAttr('style');
		    		  }
		    		  
		    	  }
		      }
	      	  
	    });
 }
</script>

<!--this script is for reject timesheet-->
<script>
   function rejectTimesheet(){
		 var tableFor = $("#tableFor").val();
		 var tdSpan = '';
		 if(tableFor != null && tableFor != '' && tableFor != 'undefined' && tableFor =='dashboard'){
			 tableFor = 'approval';
			 tdSpan ='8' 
		 }else {
			 tableFor = 'approval';
			 tdSpan = '6';
		 }
		 
	    var currentTimeFrequency = $("#currentTimeFrequency").val();
		var dates = currentTimeFrequency.split("To");
	    var projectMemberId = $("#projectMemberId").val();
	  	 
	
	  	var result = "";
		result = result +"	<div class='project_inn_mask'></div>";
		result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
		result = result +"		<p>Reject Reason</p>";
		result = result +"		<textarea  id='reOpenReason' name='reOpenReason'  placeholder='Comments here'></textarea>";
		result = result + ' <div id="reOpenReasonDiv"/>';
		result = result +'		<b href="javascript:void(0)"><input id="confirm-id" type="button" value="Submit" onclick="validateRejectReason(\''+projectMemberId+'\',\''+dates[0]+'\',\''+dates[1]+'\','+tdSpan+',\''+tableFor+'\');"><input class="u_can_color" type="button"  value="Cancel" onClick="cancelReOpen();"></b>';
		result = result +"	</div>";
		$("#reOpenPopup").append(result);
		$("#remove_user_pop").slideDown(200);
		$(".project_inn_mask").fadeIn(200); 
   }
   
   function validateRejectReason(projectMemberId,startDate,endDate,tdSpanSize,from){
	   var reason = document.getElementById("reOpenReason").value;
	   if(reason != ""){
		    $(".time_appr_side_wrap").removeClass("time_appr_side_wrap_active");
		    $(".side_mask").fadeOut(200); 
		    $("body").css('overflow-y', 'scroll');
		    $("#dynamic").removeAttr('style');
		    cancelReOpen();
		    saveRejectSubmissionByProjectMemberId(projectMemberId,startDate,endDate,tdSpanSize,from,reason);
	   }
	  	else{
	  		$("#reOpenReasonDiv").empty();
	   		$("#reOpenReasonDiv").empty().append("<font color='red' style='padding: 5px 10px; '>Reason is required</font>") ; 
	   	}
   }
</script>
<script>
function timesheetDivAfterSaving(){
	var currentTimeFrequency = ''; 
	var projectMemberId = '';
	var currentTimeSubFrequency='';
	var notificationCurrentFrequency ='${notificationCurrentFrequency}';
	var notificationProMemberId ='${notificationProMemberId}';
	var notificationCurrentSubTimeFrequency ='${notificationCurrentSubTimeFrequency}';
	var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
	
	if(notificationCurrentFrequency != null && notificationCurrentFrequency !='' && notificationCurrentFrequency !='undefined'){
		currentTimeFrequency = notificationCurrentFrequency;
	}else{
		currentTimeFrequency = $("#currentTimeFrequency").val();
	}
	if(notificationProMemberId != null && notificationProMemberId !='' && notificationProMemberId !='undefined'){
		projectMemberId = notificationProMemberId;
	}else{
		projectMemberId = $("#projectMemberId").val();
	}
	if(notificationCurrentSubTimeFrequency != null && notificationCurrentSubTimeFrequency !='' && notificationCurrentSubTimeFrequency !='undefined'){
		currentTimeSubFrequency = notificationCurrentSubTimeFrequency;
	}else{
		currentTimeSubFrequency= $("#currentTimeSubFrequency").val();
	}
	
	
	var timeSheetsFor = $("#timesheetFor").val();
    if(currentTimeSubFrequency != null && currentTimeSubFrequency != '' && currentTimeSubFrequency != 'undefined'){
 	   subFrParam = "&currentTimeSubFrequency="+currentTimeSubFrequency;
    }
    $("#loadTime").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:60% center; background-repeat:no-repeat; background-size:75px;');
    $.ajax({
	          type : "POST",
	      	  url: sourceleadUrl+"/getTimesheetSubmissionDetails?projectMembersId="+projectMemberId+"&currentTimeFrequency="+currentTimeFrequency+"&timeSheetsFor="+timeSheetsFor+subFrParam,		
		      success :function(data) {
		    	  $("#timesheetDivByFrequency").empty();
		    	  $("#timesheetDivByFrequency").append(data);
		    	  if(currentTimeSubFrequency != null && currentTimeSubFrequency != '' && currentTimeSubFrequency != 'undefined'){
		    		  $("#currentTimeSubFrequency").val(currentTimeSubFrequency);
            	      $("#timeSubFrequency").val(currentTimeSubFrequency);
		    	  }
		    	  $("#loadTime").removeAttr('style');
		      }
	    }); 
}
</script>
<script>
function cancelReOpen(){
$("#reOpenPopup").empty();
$(".project_inn_mask").fadeOut(200);
}
</script>


<!-- Re-Open Timesheet Scripts Start -->
<script type="text/javascript">
function reOpenMyTimesheet(){
	var projectMemberId = $("#projectMemberId").val();
	var timesheetFrequency= $("#currentTimeFrequency").val();
	var totalHours = $("#totalHours").val();
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Re-open message here</p>";
	result = result +"		<textarea  id='reOpenReason' name='reOpenReason'  placeholder='Comments here'></textarea>";
	result = result + ' <div id="reOpenReasonDiv"/>';
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Submit' onclick='removeEmptyLines($(\"#reOpenReason\"));notifyReopenTimeSheet("+projectMemberId+",\""+timesheetFrequency+"\","+totalHours+");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelReOpen();'></b>";
	result = result +"	</div>";
	$("#reOpenPopup").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
</script>

<script>
function notifyReopenTimeSheet(projectMemberId,timesheetFrequency,totalHours){
    var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
	var reason = document.getElementById("reOpenReason").value;
	if(reason !== ""){
		$("#reOpenReasonDiv").empty();
		$.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/notifyReopenTimeSheet?${_csrf.parameterName}=${_csrf.token}&projectMemberId="+projectMemberId+"&timesheetFrequency="+timesheetFrequency+"&reason="+(reason.replace(/\n/g,'<br/>').replace(/\r/g,'<br/>'))+"&totalHours="+totalHours,	    	   
		    success: function(data) {
		    	if(data.response === "SESSION_TIMEDOUT") {
		    		location.replace(sourceleadUrl);
		    	 } else if (data.response === "AWKWARD") {
                        location.href = "/errorPage";
		    	 } else if (data.response === "SUCCESS") {
			    		cancelReOpen();
	   			    	timesheetDivAfterSaving();
		    	 }else if (result.indexOf('Sign In') !== -1) {
	  					location.replace(sourceleadUrl);
				 }
		    }
	   });
	}else{
		$("#reOpenReasonDiv").empty().append("<font color='red' style='padding: 5px 10px; '>Reason is required</font>") ; 
	}
	  
}
</script>
<script>
function rejectReopenTimeSheetBySuperUser(projectMemberId,reOpenedUserId){
	var timesheetFrequency = $("#currentTimeFrequency").val(); 
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Re-open message here</p>";
	result = result +"		<textarea  id='reOpenRejectedReason' name='reOpenRejectedReason'  placeholder='Comments here'></textarea>";
	result = result + ' <div id="reOpenRejectedReasonDiv"/>';
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Submit' onclick='removeEmptyLines($(\"#reOpenRejectedReason\"));storeReopenRejectedReason("+projectMemberId+","+reOpenedUserId+",\""+timesheetFrequency+"\");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelReOpenRejected();'></b>";
	result = result +"	</div>";
	$("#reOpenRejectedPopup").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
</script>

<script>
function storeReopenRejectedReason(projectMemberId,reOpenedUserId,timesheetFrequency){
	var sourceleadUrl = '${SourceLeadWeb_sourceleadUrl}';
	var superUserId = '${SourceLeadWeb_superUserId}';
	var reason = document.getElementById("reOpenRejectedReason").value;
	if(reason != ""){
		$("#reOpenRejectedReasonDiv").empty();
		$.ajax({
		   	type: "POST",
		   	url : sourceleadUrl+"/ws/acceptOrRejectReopenTimeSheetBySuperUser?${_csrf.parameterName}=${_csrf.token}&projectMemberId="+projectMemberId+"&reOpenedUserId="+reOpenedUserId+"&timesheetFrequency="+timesheetFrequency+"&type=reject"+"&reason="+(reason.replace(/\n/g,'<br/>').replace(/\r/g,'<br/>')),	    	   
		    success: function(data) {
		    	$("#reOpenRejectedPopup").empty();
   			 	$(".project_inn_mask").fadeOut(200);
   			    timesheetDivAfterSaving();
	    	    $("html").trigger('click');
	    	    maskme();
		    }
	   });
	}else{
			$("#reOpenRejectedReasonDiv").empty().append("<font color='red' style='padding: 5px 10px; '>Reason is required</font>") ; 
		}
	}
	  

</script>

<script>
function cancelReOpenRejected(){
$("#reOpenRejectedPopup").empty();
$(".project_inn_mask").fadeOut(200);
}
</script>

<script>
function acceptReopenTimeSheetBySuperUser(projectMemberId,reOpenedUserId){
	
	var sourceleadUrl = '${SourceLeadWeb_sourceleadUrl}';
	var timesheetFrequency = '';
		var notificationCurrentFrequency =  $("#notificationCurrentFrequency").val();
		if(notificationCurrentFrequency != null && notificationCurrentFrequency !='' && notificationCurrentFrequency !='undefined'){
		 	 timesheetFrequency = notificationCurrentFrequency;
		 }else{
			 timesheetFrequency = $("#currentTimeFrequency").val();			 
		 }
	
	
	 
	$.ajax({
		type : "POST",
		url : sourceleadUrl+"/ws/acceptOrRejectReopenTimeSheetBySuperUser?${_csrf.parameterName}=${_csrf.token}&projectMemberId="+projectMemberId+"&reOpenedUserId="+reOpenedUserId+"&timesheetFrequency="+timesheetFrequency+"&type=accept",
		success : function(data) {
			timesheetDivAfterSaving();
		},
	});
}
</script>
<!-- Re-Open Timesheet Script End -->




<!-- Tab Switching -->
 <script>
	function timesheetDivByFrequency(currentTimeSubFrequency){ 
	               var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
	               var projectMemberId = $("#projectMemberId").val();
	               var currentTimeFrequency = $("#currentTimeFrequency").val();
	               if(currentTimeFrequency ==  null || currentTimeFrequency == '' || currentTimeFrequency == 'undefined'){
	            	   currentTimeFrequency = $("#timesheetFrequency").val();
	               }
	               var timeSheetsFor = $("#timesheetFor").val();
	               var subFrParam = '';
	               if(currentTimeSubFrequency != null && currentTimeSubFrequency != '' && currentTimeSubFrequency != 'undefined'){
	            	   subFrParam = "&currentTimeSubFrequency="+currentTimeSubFrequency;
	               }
	               $("#loadTime").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:60% center; background-repeat:no-repeat; background-size:75px;');
	               $.ajax({
		      	          type : "POST",
	        	      	  url: sourceleadUrl+"/getTimesheetSubmissionDetails?projectMembersId="+projectMemberId+"&currentTimeFrequency="+currentTimeFrequency+"&timeSheetsFor="+timeSheetsFor+subFrParam,		
	        		      success :function(data) {
	        		    	  $("#timesheetDivByFrequency").empty();
	        		    	  $("#timesheetDivByFrequency").append(data);
	        		    	  setTotals();
	        		    	  maskme();
	        		    	  if(currentTimeSubFrequency != null && currentTimeSubFrequency != '' && currentTimeSubFrequency != 'undefined'){
	        		    		  $("#currentTimeSubFrequency").val(currentTimeSubFrequency)
		   	            	      $("#timeSubFrequency").val(currentTimeSubFrequency)
	        		    	  }
	        		    	  $("#loadTime").removeAttr('style');
	        		      }
	        	    }); 
	}
</script> 
<script>
 function setTicketNumber(entryIndex,taskNumberId){
	if($("#" + taskNumberId).val() != null && $("#" + taskNumberId).val() != "" && $("#" + taskNumberId).val() !="undefined"){
		   var end = parseInt(entryIndex)+7;
      	  for (i = entryIndex; i < end; i++) {
      		   $("[name='timesheetSubmission.timesheetEntries["+i+"].ticketNumber']").val($("#"+taskNumberId).val());
          } 
	}
 }
 
 function setHourlyType(entryIndex,hourlyTypeId){
 	if($("#" + hourlyTypeId).val() != null && $("#" + hourlyTypeId).val() !="undefined"){
 		   var end = parseInt(entryIndex)+7;
       	  for (i = entryIndex; i < end; i++) {
       		   $("[name='timesheetSubmission.timesheetEntries["+i+"].hourlyType']").val($("#"+hourlyTypeId).val());
           } 
 	}
 }
</script>


<script type="text/javascript">
$(".content-wraper").click(function() {
	bodyClick();
});
$('.selectpicker').selectpicker('refresh');
</script>


<!--this script is for redirecting to timesheetapproval page with saving the timesheet-->
<script type="text/javascript">
function timesheetApprovalTableRowByProjectMemberId(timesheetFrequency,projectMembersId,from){
	    $('#notifyLink').val('');
	    $('#hedderAcceptRejectButton').hide();
	    $(".checkBoxForSubmitForApproval").each(function () {
	    	$(this).removeProp('checked');
 	    }); 
	    $('#selectAllTimeSheetCheckBoxForSubmitForApproval').removeProp('checked');
		 if(from != null && from != '' && from != 'undefined' && from == 'dashboard'){
			 $(".time_appr_side_wrap").removeClass("time_appr_side_wrap_active");
	    	 $(".side_mask").fadeOut(200); 
     		 $("body").css('overflow-y', 'scroll');
			 var currentPageNumber =  $("#currentPageNumber").val();
   		     getStatusListData(""+currentPageNumber+"",0,"");
		 }
		 else if(from != null && from != '' && from != 'undefined' && (from == 'approver' || from == 'approverClose' ||  from == 'dashboardClose')){
			 var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
			 var dates = timesheetFrequency.split("To");
				$.ajax({
			   	type: "POST",
			    url: sourceleadUrl+"/timesheetApprovalRowDetails?projectMembersId="+projectMembersId+"&frequencyStartDate="+dates[0]+"&frequencyEndDate="+dates[1]+"&tableRowFor="+from, 	   
					    success: function(data) {
					    	
						    	 $(".time_appr_side_wrap").removeClass("time_appr_side_wrap_active");
		     		    		 $(".side_mask").fadeOut(200); 
		     	        		 $("body").css('overflow-y', 'scroll');
		     	        		 $("#dynamic").removeAttr('style');
		     	        		 if(from == 'approverClose' || from == 'approver' ){
		     	        			 $("#row"+projectMembersId).empty();
							         $("#row"+projectMembersId).html(data);
		     	        		 }else{
		     	        			 $("#row"+projectMembersId+timesheetFrequency).empty();
							         $("#row"+projectMembersId+timesheetFrequency).html(data);
		     	        		 }
					        
					    }
			    }); 
		 }
}
</script>

<script type="text/javascript"	src='/resources/js/slead-common.js?staticTime=${staticTime}'></script>
