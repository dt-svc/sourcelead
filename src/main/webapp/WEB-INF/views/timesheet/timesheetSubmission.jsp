<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script src="/resources/js/jquery.maskedinput-1.3.min.js"></script>
<div class="time_pop" id="timeSheetErrorMsg"></div>

<c:if test="${employeeTimesheetEntryDetailsDTO.saveButtonVisible}">
	<div id="timesheetSubmissionStatusDiv" class="timeNewSe timBtnWidth">
					<b class="proj_new Cursor" onClick='addTimeSheetRow();'>Add Row</b>
	</div>
</c:if>
 <style>
 .commentValidation p{
    padding: 0px 0px 0px 0px !important;
    font-size: 10px;
    line-height: 10px;
    margin-left: -14px;
 }
 
 </style>
<div class="clearfix"></div>
<div id="timesheetNewTabs" class="timesheetNewTabs">
	<div class="project_side_wrap_inn_tabs">
		<ul class="project-tabs-menu">
		   <c:forEach var="tsf" items="${employeeTimesheetEntryDetailsDTO.timeSubFrequencies}">
		       <input type='hidden' name='tabDates'  id='tabDates' value='${tsf.key}' >
		       <c:if test="${tsf.key ==  employeeTimesheetEntryDetailsDTO.currentTimeSubFrequency}">
		          <input type='hidden' name='currentTimeSubFrequency'  id='currentTimeSubFrequency' value='${tsf.key}' >
		       </c:if>
			   <li class="${tsf.key ==  employeeTimesheetEntryDetailsDTO.currentTimeSubFrequency ? 'current' : ''}">
					<a href="#" onclick="timesheetEntriesDivByTimeSubFrequency('${tsf.key}');">${tsf.value}</a>
				</li>
		   </c:forEach>
		   <div class="clearfix"></div>
		</ul>
		<div id="timesheetDivByTab">
				<div class="project_tabs">
				   <div id="tab-1" class="project_tabs_inn" style="display: block;">
				    <%-- <input type='hidden' name='maxRowNumber'  class='initiateTimesheetMax' id='maxRowNumber'  value='${employeeTimesheetEntryDetailsDTO.maxRowCount > 0 ? employeeTimesheetEntryDetailsDTO.maxRowCount : 1}' /> --%>
				                <div class="timesheet_tableNew">     
						       	  <div class="tabFixed">            
						        	<table id="timesheet" cellpadding="0" cellspacing="0">
						            	<tbody>
						                	<tr>
												<th>Projects</th>
						                       	<th>Tasks </th>
						                       	<th>Task Type</th>
						                       	<th>Hourly Type</th>
						                       	<th>Ref/Ticket No.</th>
						                        <c:forEach var="weekDays" items="${employeeTimesheetEntryDetailsDTO.weekDays}">
												   <th>${weekDays.dayOfTheDate}<i>${weekDays.date}</i></th>
												</c:forEach>
						                        <th class="timeTabFix">Status</th>              
						                    </tr>  
						                     <c:set var="leaveIndex" value="0"/>
						                     <c:if test="${!empty employeeTimesheetEntryDetailsDTO.empLeaveMap}">
						                      <c:forEach var="empLeaveMap" items="${employeeTimesheetEntryDetailsDTO.empLeaveMap}" varStatus="empLeaveMapCount">
						                      <c:set var="leaveIndex" value="${leaveIndex+1}"/>
							                      <tr id="timesheetRow${leaveIndex}">
							                          <td>
								                         <div style="position: relative;">--
								                          <c:if test="${(empty timeSheetSubmissionStatus || timeSheetSubmissionStatus == 'NEW' || timeSheetSubmissionStatus == 'DRAFT') && employeeTimesheetEntryDetailsDTO.saveButtonVisible}">
									                          <c:forEach var="empL" items="${empLeaveMap.value}">
									                              <c:if test="${empL.holiday eq true}">
									                                 <input type="button" value="" onClick="alertHolidayDropMessage('${leaveIndex}','${empL.holidayId}','${empL.holidayUserId}','${empL.holidayUserLocationId}')" class="timeRemoveBtn"/>
									                              </c:if>
									                          </c:forEach>
									                       </c:if>
								                         </div>
								                      </td>
								                      <c:set var="leaveMapArr" value="${fn:split(empLeaveMap.key,'##')}"/>
							                          <td>
								                            <div>${leaveMapArr[0]}</div>  
								                      </td>
								                       <td>
								                         <div><span id="${leaveIndex}task1IdBillableTypeId">${leaveMapArr[1]}</span></div>
								                      </td>
								                      <td>
								                         <div>--</div>
								                      </td> 
								                      <td>
								                         <div>--</div>
								                      </td>
								                      <c:forEach var="empLeave" items="${empLeaveMap.value}" varStatus="empLeaveCount">
								                        <td>
								                              <input disabled="disabled"  class="inline_field" value="${empLeave.hours}" placeholder="HH:MM" type="text" id="${leaveIndex}timeSheetDay${empLeaveCount.index+1}Id" >
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
								                      <td  class="timeTabFix" >
								                        <div>--</div>
								                      </td>
							                       </tr>
						                       </c:forEach>
						                    </c:if>
						                    <input type='hidden' name='maxRowNumber'  class='initiateTimesheetMax' id='maxRowNumber'  value='${leaveIndex+employeeTimesheetEntryDetailsDTO.maxRowCount > 0 ? leaveIndex+employeeTimesheetEntryDetailsDTO.maxRowCount : 1}' />
							                <c:set var="entryIndex" value="0"/>
							                <c:set var="timeSheetSubmissionStatus"/> 
							                <c:set var="modifyButtonVisible"/>   
										    <c:forEach var="entry" items="${employeeTimesheetEntryDetailsDTO.submissionList}" varStatus="entryCount">
									            <c:set var="isMonEditable"/>
								                <c:set var="isTueEditable"/>     
								                <c:set var="isWedEditable"/>
								                <c:set var="isThuEditable"/>
								                <c:set var="isFriEditable"/>
								                <c:set var="isSatEditable"/>
								                <c:set var="isSunEditable"/> 
								                <c:set var="isTaskCommentsRequired"/> 
								                <c:set var="isTicketNumberRequired"/> 
							                    <c:forEach var="statusEntry" items="${employeeTimesheetEntryDetailsDTO.statusMap}" varStatus="statusEntryCount">
									               <c:if test="${!empty entry.projectMemberId && entry.projectMemberId == statusEntry.key}">
									                     <c:set var="timeSheetSubmissionStatus" value="${statusEntry.value.timesheetStatus}"/>
									                     <c:set var="modifyButtonVisible" value="${statusEntry.value.modifyButtonVisible == true ? 'Y' : 'N'}"/>
									                     <c:set var="isMonEditable" value="${statusEntry.value.monEditable == true ? 'Y' : 'N'}"/>
									                     <c:set var="isTueEditable" value="${statusEntry.value.tueEditable == true ? 'Y' : 'N'}"/>
									                     <c:set var="isWedEditable" value="${statusEntry.value.wedEditable == true ? 'Y' : 'N'}"/>
									                     <c:set var="isThuEditable" value="${statusEntry.value.thuEditable == true ? 'Y' : 'N'}"/>
									                     <c:set var="isFriEditable" value="${statusEntry.value.friEditable == true ? 'Y' : 'N'}"/>
									                     <c:set var="isSatEditable" value="${statusEntry.value.satEditable == true ? 'Y' : 'N'}"/>
									                     <c:set var="isSunEditable" value="${statusEntry.value.sunEditable == true ? 'Y' : 'N'}"/>
									                     <c:set var="isTaskCommentsRequired" value="${statusEntry.value.taskCommentsRequired == true ? 'Y' : 'N'}"/>
									                     <c:set var="isTicketNumberRequired" value="${statusEntry.value.ticketNumberRequired == true ? 'Y' : 'N'}"/>
									               </c:if>
									            </c:forEach>
									            <c:if test="${empty entry.projectMemberId}">
									                     <c:set var="timeSheetSubmissionStatus" value="New"/>
									                     <c:set var="modifyButtonVisible" value="N"/>
									                     <c:set var="isMonEditable" value="${employeeTimesheetEntryDetailsDTO.monEditable == true ? 'Y' : 'N'}"/>
									                     <c:set var="isTueEditable" value="${employeeTimesheetEntryDetailsDTO.tueEditable == true ? 'Y' : 'N'}"/>
									                     <c:set var="isWedEditable" value="${employeeTimesheetEntryDetailsDTO.wedEditable == true ? 'Y' : 'N'}"/>
									                     <c:set var="isThuEditable" value="${employeeTimesheetEntryDetailsDTO.thuEditable == true ? 'Y' : 'N'}"/>
									                     <c:set var="isFriEditable" value="${employeeTimesheetEntryDetailsDTO.friEditable == true ? 'Y' : 'N'}"/>
									                     <c:set var="isSatEditable" value="${employeeTimesheetEntryDetailsDTO.satEditable == true ? 'Y' : 'N'}"/>
									                     <c:set var="isSunEditable" value="${employeeTimesheetEntryDetailsDTO.sunEditable == true ? 'Y' : 'N'}"/>
									                     <c:set var="isTaskCommentsRequired" value="N"/>
									                     <c:set var="isTicketNumberRequired" value="N"/>
									               </c:if>
											    <tr id="timesheetRow${leaveIndex+entryCount.index+1}">
											        <td id="${leaveIndex+entryCount.index+1}projectTd">
														<div class="timeAdd" style="position: relative;">
														    <c:if test="${timeSheetSubmissionStatus != 'APPROVED' && timeSheetSubmissionStatus != 'SUBMITTED' && employeeTimesheetEntryDetailsDTO.saveButtonVisible}">
														        <select  id="${leaveIndex+entryCount.index+1}projectMember1Id" title="--select project--" data-live-search="true" data-dropup-auto="false" data-size="8" class='selectpicker smfield existedProjectMembers' name="${leaveIndex+entryCount.index+1}projectMember1Id" onchange="enableOrDisableHours('${entryIndex}','${leaveIndex+entryCount.index+1}projectMember1Id','${leaveIndex+entryCount.index+1}ticketNumber1Id');flushtasks('${leaveIndex+entryCount.index+1}task1Id','${leaveIndex+entryCount.index+1}'); addOrRemoveTimeSheetCommentRow('${leaveIndex+entryCount.index+1}projectMember1Id');getProjectTasks('${leaveIndex+entryCount.index+1}task1Id','${leaveIndex+entryCount.index+1}projectMember1Id');" data-validation="required" field-name="Project">
																	<!-- <option value=''>--select project--</option>  -->
															        <c:forEach var="pm" items="${employeeTimesheetEntryDetailsDTO.projectMembersList}">
															               <c:if test="${!empty entry.projectMemberId && entry.projectMemberId == pm.id}">
															                   <option value='${pm.id}' selected>${pm.projectName}</option> 
															               </c:if>
																	        <c:if test="${empty entry.projectMemberId || !empty entry.projectMemberId && entry.projectMemberId != pm.id}">
																	            <option value='${pm.id}'>${pm.projectName} </option> 
																	        </c:if>
																	 </c:forEach>
															    </select>
															     <c:if test="${entryCount.index > 0}">
															       <input value="" onclick="removeTimeSheetRow('${leaveIndex+entryCount.index+1}');" class="timeRemoveBtn" type="button">
															      </c:if>
															 </c:if>
															 <c:if test="${timeSheetSubmissionStatus == 'APPROVED' || timeSheetSubmissionStatus == 'SUBMITTED' ||  !employeeTimesheetEntryDetailsDTO.saveButtonVisible}">
															         <c:set var="projectCheck"/>
															         <c:forEach var="pm" items="${employeeTimesheetEntryDetailsDTO.submittedProjectMembersList}">
															               <c:if test="${!empty entry.projectMemberId && entry.projectMemberId == pm.id}">
															                  <input type="hidden" class='existedProjectMembers' id="${leaveIndex+entryCount.index+1}projectMember1Id" name="${leaveIndex+entryCount.index+1}projectMember1Id" value="${pm.id}"/>
															                   <p>${pm.projectName}</p> 
															                   <c:set var="projectCheck" value="1"/>
															               </c:if>
																	 </c:forEach>
																	 <c:if test="${empty projectCheck}">
											                           <p>--</p>
											                          </c:if>
															 </c:if>
														</div>
												      </td>	
										             <td id="${leaveIndex+entryCount.index+1}taskTd">
														<div class="taskDropDown">
														  <c:set var="taskType" value=""/>
														  <c:set var="taskId" value=""/>
														  <c:set var="taskName" value=""/>
														  <c:if test="${timeSheetSubmissionStatus != 'APPROVED' && timeSheetSubmissionStatus != 'SUBMITTED' && employeeTimesheetEntryDetailsDTO.saveButtonVisible}">
															   <select id="${leaveIndex+entryCount.index+1}task1Id" title="--select task--"  data-dropup-auto="false" data-size="8" data-live-search="true" name="${leaveIndex+entryCount.index+1}task1Id"  class="selectpicker smfield existedProjectTaskId${entry.projectMemberId}"   onchange='prepareProjectTaskDetails("${entryIndex}","${leaveIndex+entryCount.index+1}task1Id","${leaveIndex+entryCount.index+1}projectMember1Id");' data-validation="required" field-name="Task">
																	 <c:if test="${!empty entry.projectTaskList}">
																	   <c:forEach var="pt" items="${entry.projectTaskList}">
																	        <c:if test="${pt.id == entry.projectTaskId}">
																	          <c:set var="taskId" value="${pt.id}" />
																	          <c:set var="taskName" value="${pt.taskName}" />
																	           <c:set var="taskType" value="${entry.projectTaskType}" />
																	           <c:if test="${not empty pt.generatedId}" >
																		       		<option value='${pt.id},${pt.billableType},${pt.taskName}' selected> ${pt.generatedId} - ${pt.taskName} </option>
																		       </c:if>
																		        <c:if test="${empty pt.generatedId}" >
																		       		<option value='${pt.id},${pt.billableType},${pt.taskName}' selected>${pt.taskName} </option>
																		       </c:if>
																	        </c:if>
																	       <c:if test="${pt.id != entry.projectTaskId}">
																		       <c:if test="${not empty pt.generatedId}" >
																		       		<option value='${pt.id},${pt.billableType},${pt.taskName}'> ${pt.generatedId} - ${pt.taskName} </option>
																		       </c:if>
																		        <c:if test="${empty pt.generatedId}" >
																		       		<option value='${pt.id},${pt.billableType},${pt.taskName}'>${pt.taskName} </option>
																		       </c:if>
																	       </c:if>
																	   </c:forEach>
																	</c:if>
																</select>
														  </c:if>
														   <c:if test="${timeSheetSubmissionStatus == 'APPROVED' || timeSheetSubmissionStatus == 'SUBMITTED' ||  !employeeTimesheetEntryDetailsDTO.saveButtonVisible}">
														            <c:if test="${!empty entry.projectTaskList}">
																	   <c:forEach var="pt" items="${entry.projectTaskList}">
																	        <c:if test="${pt.id == entry.projectTaskId}">
																	          <c:set var="taskId" value="${pt.id}" />
																	          <c:set var="taskName" value="${pt.taskName}" />
																	           <c:set var="taskType" value="${entry.projectTaskType}" />
																	           <c:if test="${not empty pt.generatedId}" >
																		       		<p> ${pt.generatedId} - ${pt.taskName} </p>
																		       </c:if>
																		        <c:if test="${empty pt.generatedId}" >
																		       		<p>${pt.taskName} </p>
																		       </c:if>
																	          </c:if>
																	   </c:forEach>
																	</c:if>
																	<c:if test="${empty taskId}">
											                           <p>--</p>
											                        </c:if>
											               </c:if>
											               
														</div>
												      </td>
												      <td>
												        <div>
												           <span id="${leaveIndex+entryCount.index+1}task1IdBillableTypeId">${!empty taskType ? taskType : '--'}</span>
												        </div>
												      </td>
												      <td>
												          <c:if test="${timeSheetSubmissionStatus != 'APPROVED' && timeSheetSubmissionStatus != 'SUBMITTED' &&  employeeTimesheetEntryDetailsDTO.saveButtonVisible}">
														       <div>
														          <select id="${leaveIndex+entryCount.index+1}hourlyType1Id" style="width:220px;" data-dropup-auto="false" data-size="8"  class="selectpicker" name="${leaveIndex+entryCount.index+1}hourlyType1Id"  field-name="Hourly Type" onchange='setHourlyType("${entryIndex}","${leaveIndex+entryCount.index+1}hourlyType1Id");' >  
													                  <option value='Regular' ${!empty entry.hourlyType && entry.hourlyType == 'Regular' ? 'selected' : ''}>Regular</option>
													                  <option value='Overtime' ${!empty entry.hourlyType && entry.hourlyType == 'Overtime' ? 'selected' : ''}>Overtime</option>
										                          </select>
														       </div>
													       </c:if>
													        <c:if test="${timeSheetSubmissionStatus == 'APPROVED' || timeSheetSubmissionStatus == 'SUBMITTED' ||  !employeeTimesheetEntryDetailsDTO.saveButtonVisible}">
														         <c:if test="${!empty entry.hourlyType}">
														             <div>${entry.hourlyType}</div>
														         </c:if>
														         <c:if test="${empty entry.hourlyType}">
														             <div>--</div>
														         </c:if>
													       </c:if>
												      </td>
										              <td>
										                  <c:if test="${timeSheetSubmissionStatus != 'APPROVED' && timeSheetSubmissionStatus != 'SUBMITTED' &&  employeeTimesheetEntryDetailsDTO.saveButtonVisible}">
													         <div><input type="text" ${!empty isTicketNumberRequired && isTicketNumberRequired == 'Y' ? 'data-validation="required validate_Space validate_Alphanumeric validate_length length1-100"' : ''}  id="${leaveIndex+entryCount.index+1}ticketNumber1Id" name="${leaveIndex+entryCount.index+1}ticketNumber1Id"  value="${entry.ticketNumber}" field-name="Ref/Ticket No" onblur='setTicketNumber("${entryIndex}","${leaveIndex+entryCount.index+1}ticketNumber1Id");' class="timeTick"></div>
													      </c:if>
													      <c:if test="${timeSheetSubmissionStatus == 'APPROVED' || timeSheetSubmissionStatus == 'SUBMITTED' ||  !employeeTimesheetEntryDetailsDTO.saveButtonVisible}">
													         <c:if test="${!empty entry.ticketNumber}">
													             <div>${entry.ticketNumber}</div>
													         </c:if>
													         <c:if test="${empty entry.ticketNumber}">
													             <div>--</div>
													         </c:if>
													          
													      </c:if>
												     </td>
												     <c:forEach var="te" items="${entry.timesheetEntries}" varStatus="teStatus">
													      <td class="${modifyButtonVisible == 'Y' ? 'timeModifyBtn':''}"> 
													           <div class="timeEntry">
													               <input type="hidden" name="timesheetSubmission.timesheetEntries[${entryIndex}].timesheetTaskDTO.id" value="${taskId}"/>
																   <input type="hidden" name="timesheetSubmission.timesheetEntries[${entryIndex}].timesheetTaskDTO.taskName" value="${taskName}"/>
																   <input type="hidden" name="timesheetSubmission.timesheetEntries[${entryIndex}].timesheetTaskDTO.taskType" value="${taskType}"/>
																   <input type="hidden" name="timesheetSubmission.timesheetEntries[${entryIndex}].hourlyType" value="${empty entry.hourlyType ? 'Regular' : entry.hourlyType}" /> 
			                                                       <input type="hidden" name="timesheetSubmission.timesheetEntries[${entryIndex}].ticketNumber" value="${entry.ticketNumber}" /> 
			                                                       <input type="hidden" name="timesheetSubmission.timesheetEntries[${entryIndex}].projectMemberId" value="${entry.projectMemberId}"/>
													               <input type="hidden" name="timesheetSubmission.timesheetEntries[${entryIndex}].id" value="${te.id}"/>
													               <input type="hidden" name="timesheetSubmission.timesheetEntries[${entryIndex}].dayOfTheDate" value="${te.dayOfTheDate}"/>
													               <input type="hidden" name="timesheetSubmission.timesheetEntries[${entryIndex}].date" value="${te.date}"/>
													               <input type="hidden" name="timesheetSubmission.timesheetEntries[${entryIndex}].timesheetStatus" value="${timeSheetSubmissionStatus}"/> 
													               
														                 <c:set var="isEditable" value="Y"/>
														                  <c:if test="${te.dayOfTheDate == 'Mon' && !empty isMonEditable && isMonEditable == 'N'}">
														                      <c:set var="isEditable" value="N"/>
														                  </c:if>
														                  <c:if test="${te.dayOfTheDate == 'Tue' && !empty isTueEditable && isTueEditable == 'N'}">
														                      <c:set var="isEditable" value="N"/>
														                  </c:if>
														                  <c:if test="${te.dayOfTheDate == 'Wed' && !empty isWedEditable && isWedEditable == 'N'}">
														                      <c:set var="isEditable" value="N"/>
														                  </c:if>
														                  <c:if test="${te.dayOfTheDate == 'Thu' && !empty isThuEditable && isThuEditable == 'N'}">
														                      <c:set var="isEditable" value="N"/>
														                  </c:if>
														                  <c:if test="${te.dayOfTheDate == 'Fri' && !empty isFriEditable && isFriEditable == 'N'}">
														                      <c:set var="isEditable" value="N"/>
														                  </c:if>
														                  <c:if test="${te.dayOfTheDate == 'Sat' && !empty isSatEditable && isSatEditable == 'N'}">
														                      <c:set var="isEditable" value="N"/>
														                  </c:if>
														                  <c:if test="${te.dayOfTheDate == 'Sun' && !empty isSunEditable && isSunEditable == 'N'}">
														                      <c:set var="isEditable" value="N"/>
														                  </c:if>
													                   <c:if test="${empty entry.projectMemberId && !employeeTimesheetEntryDetailsDTO.saveButtonVisible}">
													                       <c:set var="isEditable" value="N"/>
													                   </c:if>
													                <input onblur="editMe(this);" ${(!empty isEditable && isEditable == 'Y') ? '' : 'disabled="disabled"'}  ${isTaskCommentsRequired == 'Y' ? 'data-validation-comment="required"':''}
													                id="${leaveIndex+entryCount.index+1}timeSheetDay${teStatus.index+1}Id" name="timesheetSubmission.timesheetEntries[${entryIndex}].hours" class="inline_field" value="${te.hours}" placeholder="HH:MM" type="text">
													                   <%-- <c:if test="${!empty te.comments || (empty te.comments && (!empty isEditable && isEditable == 'Y'))}"> --%>
                                                                         <div class="timesheet_comm" id="commentDiv${entryIndex}">                
													                       <i id="commentI${entryIndex}" class='glyphicon ${(!empty te.comments || (empty te.comments && (!empty isEditable && isEditable == 'Y'))) ? "glyphicon-comment":""} ${!empty te.comments ? "com_active":""}' onclick='timesheetComment("${leaveIndex+entryCount.index+1}comment${teStatus.index+1}Id");'></i>
													                       <span class='teskcomment' id='${leaveIndex+entryCount.index+1}comment${teStatus.index+1}Id'>
													                             <textarea  ${(!empty isEditable && isEditable == 'Y') ? '' : 'disabled="disabled"'} onblur="makeActive(this,'commentI${entryIndex}','${leaveIndex+entryCount.index+1}CommentValidation${teStatus.index+1}Id');" name='timesheetSubmission.timesheetEntries[${entryIndex}].comments' >${te.comments}</textarea>
			                                                               </span>
			                                                             </div>
													                  <%--  </c:if> --%>
													                   <div class="commentValidation">
														                     <input type="hidden" field-name="Comment" ${!empty te.hours && te.hours != 'HH:MM' && te.hours != '00:00' && isTaskCommentsRequired == 'Y' ? 'data-validation="required"':''} id="${leaveIndex+entryCount.index+1}CommentValidation${teStatus.index+1}Id" value="${te.comments}"/>
														              </div>
													               </div>
													              <c:set var="entryIndex" value="${entryIndex+1}"/>
													      </td>
											         </c:forEach>
												     <td class="timeTabFix "> 
												        <c:if test="${!empty entry.projectMemberId}">
												             <div>
													             <c:if test="${timeSheetSubmissionStatus == 'NEW'}">
													                <b>${timeSheetSubmissionStatus}</b>
													             </c:if>
													             <c:if test="${timeSheetSubmissionStatus == 'DRAFT'}">
													                <b class='projDraft'>${timeSheetSubmissionStatus}</b>
													             </c:if>
													             <c:if test="${timeSheetSubmissionStatus == 'SUBMITTED'}">
													               <b class='projSubmited'>${timeSheetSubmissionStatus}</b>
													             </c:if>
													             <c:if test="${timeSheetSubmissionStatus == 'REJECTED'}">
													                <b class='projReject'>${timeSheetSubmissionStatus}</b>
													             </c:if>
													             <c:if test="${timeSheetSubmissionStatus == 'APPROVED'}">
													                <b class='projActive'>${timeSheetSubmissionStatus}</b>
													             </c:if>
													             <c:if test="${modifyButtonVisible == 'Y'}">
													                <b class="proj_withdraw Cursor"  onClick="withdrawMyTimesheet('${entry.projectMemberId}');">MODIFY</b>
													              </c:if>
												             </div>
												        </c:if>
												         <c:if test="${empty entry.projectMemberId && employeeTimesheetEntryDetailsDTO.saveButtonVisible}">
												            <div><b>New</b></div> 
												        </c:if> 
												        <c:if test="${empty entry.projectMemberId && !employeeTimesheetEntryDetailsDTO.saveButtonVisible}">
												            <div>--</div> 
												        </c:if> 
						                  		    </td> 
										        </tr>
										     </c:forEach>
											
						                    
							               <tr class="projTotal">
											<td rowspan="2">Total</td>
											<td> <div></div></td>
					                 		<td> <div></div></td>
					                 		<td> <div></div></td>
					                 		<td> <div></div></td>
											<td>
												<div >
												<input type="hidden" id="entryIndexValue" value="${entryIndex}"/>
													<input disabled id="rowTotal1Id" name="rowTotal1Id"
														class="inline_field noBorder" value="HH:MM" placeholder="00:00"
														type="text">
												</div>
											</td>
											<td>
												<div >
													<input disabled id="rowTotal2Id" name="rowTotal2Id"
														class="inline_field noBorder" value="HH:MM" placeholder="00:00"
														type="text">
												</div>
											</td>
											<td>
												<div >
													<input disabled id="rowTotal3Id" name="rowTotal3Id"
														class="inline_field noBorder" value="HH:MM" placeholder="00:00"
														type="text">
												</div>
											</td>
											<td>
												<div >
													<input disabled id="rowTotal4Id" name="rowTotal4Id"
														class="inline_field noBorder" value="HH:MM" placeholder="00:00"
														type="text">
												</div>
											</td>
											<td>
												<div >
													<input disabled id="rowTotal5Id" name="rowTotal5Id"
														class="inline_field noBorder" value="HH:MM" placeholder="00:00"
														type="text">
												</div>
											</td>
											<td>
												<div >
													<input disabled id="rowTotal6Id" name="rowTotal6Id"
														class="inline_field noBorder" value="HH:MM" placeholder="00:00"
														type="text">
												</div>
											</td>
											<td>
												<div >
													<input disabled id="rowTotal7Id" name="rowTotal7Id"
														class="inline_field noBorder" value="HH:MM" placeholder="00:00"
														type="text">
												</div>
											</td>
											<td class="timeTabFix"> <div></div></td> 
										</tr>                                                                                          
						                </tbody>
						            </table>
						            </div>
						            </div>
									    <div class="timesheet_bot">
									   <c:if test="${employeeTimesheetEntryDetailsDTO.submitButtonVisible || !empty employeeTimesheetEntryDetailsDTO.timsheetAttachments}">
							            <div class="commentFile">
							                  <h2>
											    Attachmets & Comments 
											    <i class="fa fa-info-circle" title="Allowed File Formats: ppt,pptx,pps,ppsx,doc,docx,pdf,rtf,odt,xls,xlsx,csv,txt,jpg,jpeg,bmp,png,gif,eps,tiff,TGA,psd,svg,msg,wpd,wps - Allowed Size : 2MB "></i>
											 </h2>
											<div class="">
											    <c:set var="attMaxRowNumber" value="${!empty employeeTimesheetEntryDetailsDTO.timsheetAttachments ? fn:length(employeeTimesheetEntryDetailsDTO.timsheetAttachments) : 0}"/>
												<table id="attTimesheet">
												 <c:if test="${!empty employeeTimesheetEntryDetailsDTO.timsheetAttachments}">
												   <c:forEach var="attachment" items="${employeeTimesheetEntryDetailsDTO.timsheetAttachments}" varStatus="attCount">
											   	      <c:set var="timeSheetSubmissionStatus" value="New"/> 
												     <tr id="timesheetAttRow${attCount.index}">
													   <td>
													       <div class="timeAdd" style="position: relative;">
															          <c:forEach var="pm" items="${employeeTimesheetEntryDetailsDTO.attProjectMembersList}">
															             <c:if test="${pm.id == attachment.projectMemberId}">
															                  <p>${pm.projectName}</p> 
															                  <input type="hidden" data-rowId="${attCount.index}"  class="existedAttProjectMembers" id='existedAttProjectMember${pm.id}' name="timsheetAttachments[${attCount.index}].projectMemberId" value="${attachment.projectMemberId}" />
															             </c:if>
																	 </c:forEach>
														    </div> 
														</td>
														<td>
	                                                         <div id="attachmentDiv${attCount.index}Id">
	                                                              <c:forEach var="statusEntry" items="${employeeTimesheetEntryDetailsDTO.statusMap}" varStatus="statusEntryCount">
	                                                                       <c:if test="${statusEntry.key == attachment.projectMemberId}">
	                                                                        <c:set var="timeSheetSubmissionStatus" value="${statusEntry.value.timesheetStatus}"/>
	                                                                         <input  id="dbIsTimesheetAttachmentRequired${attCount.index}" value="${statusEntry.value.timesheetDocumentRequired == true  ? 1 : 0}" type="hidden">
	                                                                          <c:if test="${!empty attachment.fileInfo || !statusEntry.value.timesheetDocumentRequired}">
																	            <input class="timesheetAttValidation" data-rowId="${attCount.index}" name="isTimesheetAttachmentRequired${attCount.index}" id="isTimesheetAttachmentRequired${attCount.index}" value="N" type="hidden">
																		     </c:if>
																		     <c:if test="${empty attachment.fileInfo && statusEntry.value.timesheetDocumentRequired}"> 
																		         <input class="timesheetAttValidation" data-rowId="${attCount.index}" name="isTimesheetAttachmentRequired${attCount.index}" id="isTimesheetAttachmentRequired${attCount.index}" value="Y" type="hidden">
																		     </c:if>  
	                                                                       </c:if> 
	                                                               </c:forEach>
	                                                               <input type='hidden' name='${attCount.index}maxAttDivRowNumber' id='${attCount.index}maxAttDivRowNumber'  value='${!empty attachment.fileInfo ? fn:length(attachment.fileInfo)+1 : 1}' />
			                                                        <c:set var="attNumber" value="0"/>
			                                                        <c:if test="${!empty attachment.fileInfo}">
			                                                         <input type='hidden' name='${attCount.index}attachMentCount' id='${attCount.index}attachMentCount'  value='${!empty attachment.fileInfo ? fn:length(attachment.fileInfo) : 0}' />
			                                                         <c:forEach var="finfo" items="${attachment.fileInfo}">
																		<div id='${finfo.fileDbId}document' class="timeAtt">
																		    <input type="hidden" name="timsheetAttachments[${attCount.index}].fileInfo[${attNumber}].fileDbId" value="${finfo.fileDbId}">
			                                                                <input type="hidden" name="timsheetAttachments[${attCount.index}].fileInfo[${attNumber}].fileURL"  value="${finfo.fileURL}">
																		    <input type="hidden" name="timsheetAttachments[${attCount.index}].fileInfo[${attNumber}].fileName"  value="${finfo.fileName}">
			                                                               <a  href='${finfo.fileURL}' download><div class="attFile"><i class="fa fa-download"></i>${finfo.fileName}</div></a>
										                                    <div class="timeAttBtn">
										                                        <i class="fa fa-eye" onClick="viewFile('${finfo.fileURL}','${finfo.fileName}','${finfo.contentType}');"></i>
										                                       <c:if test="${timeSheetSubmissionStatus != 'SUBMITTED' && timeSheetSubmissionStatus != 'APPROVED'}">
										                                            <i class="fa fa-trash" onClick='deleteSubmissionDocument("${finfo.fileDbId}","${attCount.index}");'></i>
										                                       </c:if>
										                                    </div>
			                                                            </div>
			                                                            <c:set var="attNumber" value="${attNumber+1}"/>
										                              </c:forEach>
										                              </c:if>
										                              <c:if test="${empty attachment.fileInfo && !employeeTimesheetEntryDetailsDTO.saveButtonVisible}">
										                                  <p> No Attachments Found</p>
										                              </c:if>
			                                                         <c:if test="${timeSheetSubmissionStatus != 'SUBMITTED' && timeSheetSubmissionStatus != 'APPROVED' }">
				                                                             <div id="attachmentInnerDiv${attCount.index}Id">
							                                                          <div id="${attCount.index}AttchmentDiv${attNumber}Id" class="addRowBtn">
																						  <div class="input-group col-md-12" style="margin:0 auto">
																							<label class="input-group-btn pull-left"> <span class="btn btn-primary input-sm"><i class="fa fa-folder-open-o"></i>
																							  <input style="display: none;"  id="${attCount.index}docFile${attNumber}" multiple  onchange="validateUploadFile('${attCount.index}','${attNumber}',this);" class="upload" type="file">
																							  </span></label>
																							<input class="form-control input-sm pull-left" style="width:130px;" id="${attCount.index}uploadFile${attNumber}"   placeholder="Attachment" type="text">
																						  </div>
																						  <input type="button" value="" onclick="addAttachmentRow('${attCount.index}');" class="addRowIcon"/>
																						  <input type="hidden" id="timesheetAttValidationInput${attCount.index}" field-name="Attachment"/>
																					 </div>
																			</div>
			                                                          </c:if>
															 </div>
														</td>
														<td>
															<input type="text" name="timsheetAttachments[${attCount.index}].weekComment" ${timeSheetSubmissionStatus != 'SUBMITTED' && timeSheetSubmissionStatus != 'APPROVED' ? '' : 'readonly="readonly"'}  class="form-control  input-sm" value="${attachment.weekComment}" placeholder="Comment"  title ="'${attachment.weekComment}'"/>
														</td>
													    </tr>
												    </c:forEach>
												 </c:if>
												  <c:set var="attCount" value="${!empty employeeTimesheetEntryDetailsDTO.timsheetAttachments ? fn:length(employeeTimesheetEntryDetailsDTO.timsheetAttachments) : 0}"/>
												  <c:forEach var="pMember" items="${employeeTimesheetEntryDetailsDTO.attProjectMembersList}" >
												      <c:set var="projAttAvailable" value="0"/>
												      <c:if test="${!empty employeeTimesheetEntryDetailsDTO.timsheetAttachments}">
													      <c:forEach var="attachment" items="${employeeTimesheetEntryDetailsDTO.timsheetAttachments}">
													            <c:if test="${pMember.id == attachment.projectMemberId}">
													               <c:set var="projAttAvailable" value="1"/>
													            </c:if>
													      </c:forEach>
													  </c:if>
												      <c:if test="${projAttAvailable == 0}">
												      <c:set var="timeSheetSubmissionStatus" value="NEW"/>
									                  <c:forEach var="statusEntry" items="${employeeTimesheetEntryDetailsDTO.statusMap}" varStatus="statusEntryCount">
                                                           <c:if test="${statusEntry.key == pMember.id && (statusEntry.value.timesheetStatus != 'SUBMITTED' && statusEntry.value.timesheetStatus != 'APPROVED' )}">
															     <tr id="timesheetAttRow${attCount}">
																    <td>
																       <div class="timeAdd" style="position: relative;">
															                  <p>${pMember.projectName}</p> 
															                  <input type="hidden" data-rowId="${attCount}"  class="existedAttProjectMembers" id='existedAttProjectMember${pMember.id}' name="timsheetAttachments[${attCount}].projectMemberId" value="${pMember.id}" />
																		            
																	    </div> 
																	</td>
																	<td>
	                                                                   <div id="attachmentDiv${attCount}Id">
	                                                                        <c:set var="timeSheetSubmissionStatus" value="${statusEntry.value.timesheetStatus}"/>
			                                                                       <input  id="dbIsTimesheetAttachmentRequired${attCount}" value="${statusEntry.value.timesheetDocumentRequired == true  ? 1 : 0}" type="hidden">
			                                                                  <c:if test="${!statusEntry.value.timesheetDocumentRequired}">
																	              <input class="timesheetAttValidation" data-rowId="${attCount}" name="isTimesheetAttachmentRequired${attCount}" id="isTimesheetAttachmentRequired${attCount}" value="N" type="hidden">
																		     </c:if>
																		     <c:if test="${statusEntry.value.timesheetDocumentRequired}"> 
																		         <input class="timesheetAttValidation" data-rowId="${attCount}" name="isTimesheetAttachmentRequired${attCount}" id="isTimesheetAttachmentRequired${attCount}" value="Y" type="hidden">
																		     </c:if> 
																		      <input type='hidden' name='${attCount}maxAttDivRowNumber' id='${attCount}maxAttDivRowNumber'  value='1' />
														                      <div id="attachmentInnerDiv${attCount}Id">
							                                                          <div id="${attCount}AttchmentDiv0Id" class="addRowBtn">
																						  <div class="input-group col-md-12" style="margin:0 auto">
																							<label class="input-group-btn pull-left"> <span class="btn btn-primary input-sm"><i class="fa fa-folder-open-o"></i>
																							  <input style="display: none;"  id="${attCount}docFile0" multiple  onchange="validateUploadFile('${attCount}','0',this);" class="upload" type="file">
																							  </span></label>
																							<input class="form-control input-sm pull-left"  style="width:130px;" id="${attCount}uploadFile0"   placeholder="Attachment" type="text">
																						  </div>
																						  <input type="button" value="" onclick="addAttachmentRow('${attCount}');" class="addRowIcon"/>
																						  <input type="hidden" id="timesheetAttValidationInput${attCount}" field-name="Attachment"/>
																					 </div>
																			</div>
																		</div>
														             </td>
														             <td>
															             <input type="text" name="timsheetAttachments[${attCount}].weekComment"   class="form-control  input-sm" value="" placeholder="Comment" />
																    </td>
															    </tr>
															     <c:set var="attCount" value="${attCount+1}"/>
                                                           </c:if> 
                                                       </c:forEach>
                                                       <c:set var="attMaxRowNumber" value="${attCount}"/>
                                                      </c:if>
												  </c:forEach>
												</table>
												 <input type='hidden' name='maxAttRowNumber' id='maxAttRowNumber'  value='${!empty attMaxRowNumber ? attMaxRowNumber : 0}' />
											</div>
							            </div>  
							          </c:if> 
							    	   <div class="timesheet_week_comm">
							            <div class="totalWrap">
							                 <ul>
												<li><span>Billable Hours</span><input
													id="grandBillableTotal"
													value="" placeholder="HH/HH" readonly="" type="text"></li>
												<li><span>Non-Billable Hours</span><input
													id="grandNonBillableTotal"
													value="" placeholder="HH/HH" readonly="" type="text"></li>
												<li><span>Total Hours</span><input id="grandTotal" 
													value="" placeholder="HH/HH" readonly="" type="text"></li>
												<div class="clearfix"></div>
											</ul>
							            </div>
							            <div class="clearfix"></div>
							    	    <div class="timesheet_button clearfix">
							    	    <c:if test="${employeeTimesheetEntryDetailsDTO.saveButtonVisible}">
							    	        <input onclick="newSaveTimesheet('Draft');" value="Save" class="time_btn time_dra" type="button">
							    	         <c:if test="${employeeTimesheetEntryDetailsDTO.submitButtonVisible}">
									           <input onclick="newSaveTimesheet('Submitted');" value="Submit" class="time_btn" type="button">
									         </c:if>
							    	    </c:if>
							         </div>              
							        </div>
							    	<div class="clearfix"></div>
							        </div>
								          </div>
								        </div>        	
								   </div>
								 </div>
					<input type="hidden" name="_ignore">
			 </div>
	        <!-- AddRow Divs  ####-->
	        <div id="divadttTd0" style="display:none;">   
	             <div class="timeAdd" style="position: relative;"> 
	              <select id="REPLACEROWIDprojectMember1Id" title="--select Project--"  name="REPLACEROWIDprojectMember1Id"  class='existedProjectMembers' onchange="flushtasks('REPLACEROWIDtask1Id');enableOrDisableHours('ENTRYINDEX','REPLACEROWIDprojectMember1Id','REPLACEROWIDticketNumber1Id','REPLACEROWID');addOrRemoveTimeSheetCommentRow('REPLACEROWIDprojectMember1Id');getProjectTasks('REPLACEROWIDtask1Id','REPLACEROWIDprojectMember1Id');"  field-name="Project">  
	               <c:forEach var="pMember" items="${employeeTimesheetEntryDetailsDTO.projectMembersList}">
		                   <option value='${pMember.id}'>${pMember.projectName} </option> 
		            </c:forEach>
	              </select>
	             <input type="button" value="" onClick="removeTimeSheetRow('REPLACEROWID')" class="timeRemoveBtn"/>
	             </div>
	        </div>
	         <div id="divadttTd1" style="display:none;">   
	             <div class="taskDropDown"> 
	              <select id="REPLACEROWIDtask1Id"  title="--select task--"  name="REPLACEROWIDtask1Id" class="smfield"  field-name="Task" onchange="prepareProjectTaskDetails('ENTRYINDEX','REPLACEROWIDtask1Id','REPLACEROWIDprojectMember1Id')" >  
	              </select>
	             </div>
	        </div>
	        <div id="divadttTd2" style="display:none;">
		    	   <span id="REPLACEROWIDtask1IdBillableTypeId" name="REPLACEROWIDtask1IdBillableTypeId" ></span>	
	   		</div>
	   		 <div id="divadttTd10" style="display:none;">
		    	  <div><input type="text" id="REPLACEROWIDticketNumber1Id" name="REPLACEROWIDticketNumber1Id" onblur="setTicketNumber('ENTRYINDEX','REPLACEROWIDticketNumber1Id');" class="timeTick" field-name="Ref/Ticket No"></div>	
	   		</div>
	   		<div id="divadttTd12" style="display:none;">
	             <div>
	               <select id="REPLACEROWIDhourlyType1Id" name="REPLACEROWIDhourlyType1Id"  field-name="Hourly Type" onchange="setHourlyType('ENTRYINDEX','REPLACEROWIDhourlyType1Id');">  
	                      <option value='Regular'>Regular</option>
		                  <option value='Overtime'>Overtime</option>
	               </select>
	             </div>
   		    </div>
   		
	   		<div id="divadttTd11" style="display:none;">
		    	  <div><b>New</b></div>	
	   		</div>
	        <div id="divadttTd3" style="display:none;">
	               <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.id" />
				   <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.taskName" />
				   <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.taskType"/>
				   <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].projectMemberId"/>
	               <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].ticketNumber"/> 
	               <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].hourlyType" value="Regular"/> 
	               <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetStatus" /> 
	               <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].dayOfTheDate" value="DAYOFTHEDATE"/>
	               <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].date" value="DATEVALUE"/>
		    	   <input type="text" disabled onblur='editMe(this);'   id="REPLACEROWIDtimeSheetDay1Id" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].hours" class="inline_field" placeholder="HH:MM" />	
	               <div class="timesheet_comm" id="commentDivENTRYINDEX">
	                <i class="glyphicon glyphicon-comment" id="commentIENTRYINDEX" onclick="timesheetComment('REPLACEROWIDcomment1Id');"></i>
	                <span class="teskcomment" id="REPLACEROWIDcomment1Id" ><textarea disabled  onblur="makeActive(this,'commentIENTRYINDEX','REPLACEROWIDCommentValidation1Id');" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].comments" id="REPLACEROWIDtextArea1Id" ></textarea></span>
	               </div>
	                <div class="commentValidation">
	                     <input type="hidden" id="REPLACEROWIDCommentValidation1Id" field-name='Comment'/>
	                </div>
	   		</div>
	        <div id="divadttTd4" style="display:none;">
		               <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.id" />
					   <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.taskName" />
					   <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.taskType"/>
					   <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].hourlyType" value="Regular"/> 
	                   <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].projectMemberId"/>
		               <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].dayOfTheDate" value="DAYOFTHEDATE"/>
		               <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetStatus" /> 
		               <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].date" value="DATEVALUE"/>
	                   <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].ticketNumber"/> 
					  <input type="text" disabled onblur='editMe(this);'   id="REPLACEROWIDtimeSheetDay2Id" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].hours" class="inline_field" placeholder="HH:MM" />	
	                  <div class="timesheet_comm" id="commentDivENTRYINDEX">
	                      <i class="glyphicon glyphicon-comment" id="commentIENTRYINDEX" onclick="timesheetComment('REPLACEROWIDcomment2Id');"></i>
	                  <span class="teskcomment" id="REPLACEROWIDcomment2Id" ><textarea onblur="makeActive(this,'commentIENTRYINDEX','REPLACEROWIDCommentValidation2Id');" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].comments" id="REPLACEROWIDtextArea2Id" ></textarea></span></div>
	                   <div class="commentValidation">
	                     <input type="hidden" id="REPLACEROWIDCommentValidation2Id" field-name='Comment'/>
	                  </div>
	        </div>
	        <div id="divadttTd5" style="display:none;">
	                   <input type="hidden"  disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.id" />
					   <input type="hidden"  disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.taskName" />
					   <input type="hidden"  disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.taskType"/>
					   <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].hourlyType" value="Regular"/> 
	                   <input type="hidden"  disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].projectMemberId"/>
		               <input type="hidden"  disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].dayOfTheDate" value="DAYOFTHEDATE"/>
		               <input type="hidden"  disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetStatus" /> 
		               <input type="hidden"  disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].date" value="DATEVALUE"/>
	                   <input type="hidden"  disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].ticketNumber"/> 
					  <input type="text" onblur='editMe(this);' disabled  id="REPLACEROWIDtimeSheetDay3Id" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].hours" class="inline_field" placeholder="HH:MM" />	
	                  <div class="timesheet_comm" id="commentDivENTRYINDEX">
	                  <i class="glyphicon glyphicon-comment" id="commentIENTRYINDEX" onclick="timesheetComment('REPLACEROWIDcomment3Id');"></i>
	                  <span class="teskcomment" id="REPLACEROWIDcomment3Id" ><textarea onblur="makeActive(this,'commentIENTRYINDEX','REPLACEROWIDCommentValidation3Id');" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].comments" id="REPLACEROWIDtextArea3Id"></textarea></span></div>
	                  <div class="commentValidation">
	                     <input type="hidden" id="REPLACEROWIDCommentValidation3Id" field-name='Comment'/>
	                  </div>
	        </div>
	        <div id="divadttTd6" style="display:none;">
	                   <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.id" />
					   <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.taskName" />
					   <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.taskType"/>
					   <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].hourlyType" value="Regular"/> 
	                   <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].projectMemberId"/>
		               <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].dayOfTheDate" value="DAYOFTHEDATE"/>
		               <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetStatus" /> 
		               <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].date" value="DATEVALUE"/>
		               <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].ticketNumber"/> 
					  <input type="text" onblur='editMe(this);' disabled id="REPLACEROWIDtimeSheetDay4Id" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].hours" class="inline_field" placeholder="HH:MM" />	
	                  <div class="timesheet_comm" id="commentDivENTRYINDEX">
	                  <i class="glyphicon glyphicon-comment" id="commentIENTRYINDEX" onclick="timesheetComment('REPLACEROWIDcomment4Id');"></i>
	                  <span class="teskcomment" id="REPLACEROWIDcomment4Id" ><textarea onblur="makeActive(this,'commentIENTRYINDEX','REPLACEROWIDCommentValidation4Id');" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].comments" id="REPLACEROWIDtextArea4Id"></textarea></span></div>           
	                   <div class="commentValidation">
	                     <input type="hidden" id="REPLACEROWIDCommentValidation4Id" field-name='Comment'/>
	                  </div>
	        </div>
	        <div id="divadttTd7" style="display:none;">
	                   <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.id" />
					   <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.taskName" />
					   <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.taskType"/>
	                   <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].projectMemberId"/>
	                   <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].hourlyType" value="Regular"/> 
		               <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].dayOfTheDate" value="DAYOFTHEDATE"/>
		               <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetStatus" /> 
		               <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].date" value="DATEVALUE"/>
		               <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].ticketNumber"/> 
					  <input type="text" onblur='editMe(this);' disabled id="REPLACEROWIDtimeSheetDay5Id" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].hours" class="inline_field" placeholder="HH:MM" />	
	                  <div class="timesheet_comm" id="commentDivENTRYINDEX">
	                  <i class="glyphicon glyphicon-comment" id="commentIENTRYINDEX" onclick="timesheetComment('REPLACEROWIDcomment5Id');"></i>
	                  <span class="teskcomment" id="REPLACEROWIDcomment5Id" ><textarea  onblur="makeActive(this,'commentIENTRYINDEX','REPLACEROWIDCommentValidation5Id');" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].comments" id="REPLACEROWIDtextArea5Id"></textarea></span></div>  
	    	          <div class="commentValidation">
	                     <input type="hidden" id="REPLACEROWIDCommentValidation5Id" field-name='Comment'/>
	                  </div>
	    	 </div>
	         <div id="divadttTd8" style="display:none;">
	                   <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.id" />
					   <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.taskName" />
					   <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.taskType"/>
	                   <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].projectMemberId"/>
	                   <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].hourlyType" value="Regular"/> 
		               <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].dayOfTheDate" value="DAYOFTHEDATE"/>
		               <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetStatus" /> 
		               <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].date" value="DATEVALUE"/>
		               <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].ticketNumber"/> 
				     <input type="text" onblur='editMe(this);' disabled id="REPLACEROWIDtimeSheetDay6Id" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].hours" class="inline_field" placeholder="HH:MM" />	
	                 <div class="timesheet_comm" id="commentDivENTRYINDEX">
	                 <i class="glyphicon glyphicon-comment" id="commentIENTRYINDEX" onclick="timesheetComment('REPLACEROWIDcomment6Id');"></i>
	                 <span class="teskcomment" id="REPLACEROWIDcomment6Id" ><textarea onblur="makeActive(this,'commentIENTRYINDEX','REPLACEROWIDCommentValidation6Id');" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].comments" id="REPLACEROWIDtextArea6Id"></textarea></span></div>
	                 <div class="commentValidation">
	                     <input type="hidden" id="REPLACEROWIDCommentValidation6Id" field-name='Comment'/>
	                  </div>
	         </div>
	         <div id="divadttTd9" style="display:none;">
	                 <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.id" />
					 <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.taskName" />
					 <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.taskType"/>
	                 <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].projectMemberId"/>
	                 <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].hourlyType" value="Regular"/> 
	                 <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].dayOfTheDate" value="DAYOFTHEDATE"/>
	                 <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetStatus" /> 
	                 <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].date" value="DATEVALUE"/>
		             <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].ticketNumber"/> 
				     <input type="text" onblur='editMe(this);' disabled id="REPLACEROWIDtimeSheetDay7Id" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].hours" class="inline_field" placeholder="HH:MM" />	
	                 <div class="timesheet_comm" id="commentDivENTRYINDEX">
	                 <i class="glyphicon glyphicon-comment" id="commentIENTRYINDEX" onclick="timesheetComment('REPLACEROWIDcomment7Id');"></i>
	                 <span class="teskcomment" id="REPLACEROWIDcomment7Id" ><textarea onblur="makeActive(this,'commentIENTRYINDEX','REPLACEROWIDCommentValidation7Id');" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].comments" id="REPLACEROWIDtextArea7Id"></textarea></span></div>            
	    	          <div class="commentValidation">
	                     <input type="hidden" id="REPLACEROWIDCommentValidation7Id" field-name='Comment'/>
	                  </div>
	    	 </div>
	    	 
	    	  <!--  Add Attachments Divs  ####-->
            <div id="divComentTd0" style="display:none;">  
                      <div class="timeAdd" style="position: relative;">
                          <p>ATTACHMENTPROJECTNAME</p> 
	                      <input type="hidden" disabled data-rowId="REPLACEROWID" class="ATTACHMENTEXISTEDCLASS"  id='existedAttProjectMemberATTACHMENTPMID' name="timsheetAttachments[REPLACEROWID].projectMemberId" value="ATTACHMENTPMID" />
                      </div> 
		             
	          </div>
	          <div id="divComentTd1" style="display:none;">  
	                <div id="attachmentInnerDivREPLACEROWIDId">
	                    <input  id="dbIsTimesheetAttachmentRequiredREPLACEROWID" disabled type="hidden">
	                    <input class="timesheetAttValidation" data-rowId="REPLACEROWID" disabled name="isTimesheetAttachmentRequiredREPLACEROWID" id="isTimesheetAttachmentRequiredREPLACEROWID" type="hidden">
	                    <input type='hidden' name='REPLACEROWIDmaxAttDivRowNumber' disabled id='REPLACEROWIDmaxAttDivRowNumber'  value='1' />
	                     <div id="REPLACEROWIDAttchmentDiv0Id" class="addRowBtn">
						  <div class="input-group col-md-12" style="margin:0 auto">
							<label class="input-group-btn pull-left"> <span class="btn btn-primary input-sm"><i class="fa fa-folder-open-o"></i>
							  <input style="display: none;" multiple  id="REPLACEROWIDdocFile0"  onchange="validateUploadFile('REPLACEROWID','0',this);"  class="upload" type="file">
							  </span></label>
							<input class="form-control input-sm pull-left" style="width:130px;" id="REPLACEROWIDuploadFile0" placeholder="Attachment" type="text">
						  </div>
						  <input type="button" value="" onclick="addAttachmentRow('REPLACEROWID');" class="addRowIcon"/>
						   <input type="hidden" disabled id="timesheetAttValidationInputREPLACEROWID" field-name="Attachment"/>
					   </div>	
				   </div>
	          </div>
	          <div id="divComentTd2" style="display:none;">   
	                   <input type="text" name="timsheetAttachments[REPLACEROWID].weekComment" disabled class="form-control input-sm" placeholder="Comment"  />
	          </div>
	          
	          
	          <div id="divAttachmentTd0" style="display:none;">
		           <div id="COMMENTROWIDAttchmentDivREPLACEROWIDId" class="addRowBtn">
						   <div class="input-group col-md-12" style="margin:0 auto">
							<label class="input-group-btn pull-left"> <span class="btn btn-primary input-sm"><i class="fa fa-folder-open-o"></i>
							  <input style="display: none;" multiple  id="COMMENTROWIDdocFileREPLACEROWID"  onchange="validateUploadFile('COMMENTROWID','REPLACEROWID',this);"  class="upload" type="file">
							  </span></label>
							<input class="form-control input-sm pull-left" style="width:130px;" id="COMMENTROWIDuploadFileREPLACEROWID" placeholder="Attachment" type="text">
						   </div>
						   <input type="button" value="" onclick="removeAttRow('COMMENTROWID','REPLACEROWID');" class="addRowIcon removeIcon"/>
					</div>	
		    </div>	
   <script>
	function timesheetEntriesDivByTimeSubFrequency(currentTimeSubFrequency){ 
	               var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
	               var invoiceFrequency = $("#invoiceFrequency").val();
	               var currentTimeFrequency = $("#currentTimeFrequency").val();
	               $("#loadTime").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:60% center; background-repeat:no-repeat; background-size:75px;');
	        	   $.ajax({
		      	          type : "POST",
	        	      	  url: sourceleadUrl+"/getEmployeeTimesheetEntryDetails?invoiceFrequency="+invoiceFrequency+"&currentTimeFrequency="+currentTimeFrequency+"&currentTimeSubFrequency="+currentTimeSubFrequency+"&timeSheetsFor=user",		
	        		      success :function(data) {
	        		    	  $("#timesheetEntriesDiv").empty();
	        		    	  $("#timesheetEntriesDiv").append(data);
	        		    	  $("#loadTime").removeAttr('style');
	        		    	  prepareChosenScripts();
	        		    	  setTotals();
	        		      }
	        	    }); 
	}
</script> 
<script>
/* this script is for calculate total hours */
$(document).ready(function() {
	bodyClick();
	var invalidDate = "${INVALID_DATA}";
	var emptyData = "${INVALID_DATA_EMPTY}"
		var outPut;
		if(invalidDate != "" && invalidDate != null && invalidDate != 'undefined'){
			outPut = "<p>"+invalidDate+" hours should not be more than 24<b>X</b></p>";
		}
		if(emptyData != "" && emptyData != null && emptyData != 'undefined'){
			outPut = "<p>"+emptyData+"<b>X</b></p>";
		}
	if((invalidDate != "" && invalidDate != null && invalidDate != 'undefined')|| (emptyData != "" && emptyData != null && emptyData != 'undefined')){
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
		
function setTotals(){ 
	 var total;
	 var billableTotal;
	 var billableSum = 0;
	 var nonBillableTotal;
	 var nonBillableSum = 0;
	 var rowNum = $("#maxRowNumber").val();
	 var jValue = parseInt(rowNum)+1
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
				     
				     var billableType = $("#"+j+"task1IdBillableTypeId").text();
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
				     
				     if (billableType !== undefined && billableType !=='####' && billableType !=='' && (billableType == 'Non-Billable' || billableType == 'Non Billable'|| billableType == 'NonBillable')) {
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
function makeActive(local,commentI,commentValidationId){ 
	var comment = local.value;
	 $("#"+commentI).removeClass('com_active');
	if(comment != null && comment != 'undefined' && comment != ''){
		 $("#"+commentValidationId).val(comment);
		 $("#"+commentValidationId).next('p').remove();
		 $("#"+commentValidationId).removeClass('error');
		 $("#"+commentI).addClass('com_active');
	}else{
		 $("#"+commentValidationId).val('');
		 $("#"+commentValidationId).next('p').remove();
		 $("#"+commentValidationId).removeClass('error');
	}
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
<script type="text/javascript">
function withdrawMyTimesheet(projectMemberId){ 
	 var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
	 var timesheetFrequency  = $("#currentTimeFrequency").val();
	var dateFormat ='${SourceLeadWeb_orgLocations.dateFormat}';
	  $("#loadTime").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:60% center; background-repeat:no-repeat; background-size:75px;');
	$.ajax({
	      type : "POST",
     	  url: sourceleadUrl+"/withDrawTimesheet?${_csrf.parameterName}=${_csrf.token}&projectMemberId="+projectMemberId+"&timesheetFrequency="+timesheetFrequency+"&dateFormat="+dateFormat,		
	      success :function(result) {
	    	  $("#loadTime").removeAttr("style"); 
	    	  timesheetEntriesDivByTimeFrequency();
	    	  $("html").trigger('click');
	    	  maskme();
	      }
   }); 
}
</script>

<script>
  function addAttachmentRow(rowNum,initAttRowNum){
	  var attRowNum = $("#"+rowNum+"maxAttDivRowNumber").val();
	  var maxRowNum = parseInt(attRowNum);
	  $("#"+rowNum+"maxAttDivRowNumber").val(maxRowNum+1);
	  var divadAttStr =  document.getElementById("divAttachmentTd0").innerHTML;
	  divadAttStr = divadAttStr.replace(/COMMENTROWID/g, ( parseInt(rowNum)));
	  divadAttStr = divadAttStr.replace(/REPLACEROWID/g, (maxRowNum));
	  $("#attachmentInnerDiv"+parseInt(rowNum)+"Id").append(divadAttStr); 
  }
</script>
<script>
  function removeAttRow(comNum,rowNum){
	 $("#"+comNum+"AttchmentDiv"+rowNum+"Id").remove();
	 $("#timesheet_form input[data-id*='"+comNum+"docFile"+rowNum+"']").remove();
  }
</script>
<script>
  function removeTimeSheetCommentRow(rowId){
		 document.getElementById("timesheetAttRow"+rowId).style.display = 'none';
		 document.getElementById("timesheetAttRow"+rowId).value= '';
		 var  rId = "timesheetAttRow"+rowId;
		  var r = document.getElementById("attTimesheet").rows[rId];
          var count = (r.cells.length);
	       for(i=0;i<count;i++){
	        r.deleteCell(0);
	      } 
  }
</script>
<script>
   function addOrRemoveTimeSheetCommentRow(pmId) { 
	   var projectMemberId = $("#"+pmId).val();
	   if(projectMemberId != null && projectMemberId != '' && projectMemberId != 'undefined' && ($("#existedAttProjectMember"+projectMemberId).val() == '' || $("#existedAttProjectMember"+projectMemberId).val() == null ||  $("#existedAttProjectMember"+projectMemberId).val() == 'undefined')){
		    var tbl = document.getElementById("attTimesheet");
		    if(tbl != null && tbl !='' && tbl != 'undefined'){
		    	var rowNum = $("#maxAttRowNumber").val();
				var maxRowNum = parseInt(rowNum);
			   
				$("#maxAttRowNumber").val(maxRowNum+1); 
				var row = tbl.insertRow(maxRowNum);
				row.setAttribute("id", "timesheetAttRow"+maxRowNum);
				
				var projectMemberId = $("#"+pmId).val();
				var projectName = $("#"+pmId+" :selected").text();
				
				var cell0 = row.insertCell(0);
				var divadcmTd0Str = document.getElementById("divComentTd0").innerHTML;
				divadcmTd0Str = divadcmTd0Str.replace(/REPLACEROWID/g, (maxRowNum));
				divadcmTd0Str = divadcmTd0Str.replace(/ATTACHMENTPROJECTNAME/g,projectName);
				divadcmTd0Str = divadcmTd0Str.replace(/ATTACHMENTEXISTEDCLASS/g,"existedAttProjectMembers");
				divadcmTd0Str = divadcmTd0Str.replace(/ATTACHMENTPMID/g,projectMemberId);
				divadcmTd0Str = divadcmTd0Str.replace(/disabled/g, ''); 
				cell0.innerHTML = divadcmTd0Str;
				
				var cell1 = row.insertCell(1);
				var divadcmTd1Str = document.getElementById("divComentTd1").innerHTML;
				divadcmTd1Str = divadcmTd1Str.replace(/REPLACEROWID/g, (maxRowNum));
				divadcmTd1Str = divadcmTd1Str.replace(/disabled/g, ''); 
				cell1.innerHTML = divadcmTd1Str;
				
				var cell2 = row.insertCell(2);
				var divadcmTd2Str = document.getElementById("divComentTd2").innerHTML;
				divadcmTd2Str = divadcmTd2Str.replace(/REPLACEROWID/g, (maxRowNum));
				divadcmTd2Str = divadcmTd2Str.replace(/disabled/g, ''); 
				cell2.innerHTML = divadcmTd2Str;
				
				
			    <c:forEach var="statusEntry" items="${employeeTimesheetEntryDetailsDTO.statusMap}" varStatus="statusEntryCount">
				    if('${statusEntry.key}' == projectMemberId){
				    	var timesheetDocumentRequired = '${statusEntry.value.timesheetDocumentRequired}';
				    	if(timesheetDocumentRequired == "true"){
				    		$("#dbIsTimesheetAttachmentRequired"+maxRowNum).val('1'); 
				    		$("#isTimesheetAttachmentRequired"+maxRowNum).val('Y'); 
				    		$('#timesheetAttValidationInput'+maxRowNum).attr("data-validation","required"); 
				    	}else{
				    		$("#dbIsTimesheetAttachmentRequired"+maxRowNum).val('0');
				    		$("#isTimesheetAttachmentRequired"+maxRowNum).val('N'); 
				    		$('#timesheetAttValidationInput'+maxRowNum).removeAttr("data-validation"); 
				    	}
				    }
	             </c:forEach>
		    }
	   }
	   var existedProjectMembers = [];
	   $('.existedProjectMembers').each(function() {
		     var id =this.id;
			 var localId = this.value;
			 if(localId != null &&  localId !='undefined' && localId != '' && id !='REPLACEROWIDprojectMember1Id'){
				 existedProjectMembers.push(localId);
			 }
	   }); 
	   var deletedAttProjectMembers = [];
	   $('.existedAttProjectMembers').each(function() {
		      var localId = this.value;
		      var id = this.id;
		      if($.inArray(localId, existedProjectMembers) == -1){
		    	  var delRowId = $("#"+id).attr('data-rowId');
		    	  removeTimeSheetCommentRow(delRowId);
		      }
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

function addTimeSheetRow() {
	  if ($('#timesheet_form').validate(false, validationSettings)&&!$("#timeSheetErrorMsg").is(':visible')){   
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
			var divadttTd00Str = document.getElementById("divadttTd0").innerHTML;
			divadttTd00Str = divadttTd00Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd00Str = divadttTd00Str.replace(/ENTRYINDEX/g, (entryIndex)); 
			divadttTd00Str = divadttTd00Str.replace(/disabled/g, ''); 
			
			cell0.innerHTML = divadttTd00Str;
			$("#"+maxRowNum+"projectMember1Id").attr('data-validation','required');
			$("#"+maxRowNum+"projectMember1Id").addClass('selectpicker');
			$("#"+maxRowNum+"projectMember1Id").attr('data-live-search','true');
			$("#"+maxRowNum+"projectMember1Id").attr('data-size',8);
			
			
			
			var cell1 = row.insertCell(1);
			var divadttTd0Str = document.getElementById("divadttTd1").innerHTML;
			divadttTd0Str = divadttTd0Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd0Str = divadttTd0Str.replace(/ENTRYINDEX/g, (entryIndex)); 
			divadttTd0Str = divadttTd0Str.replace(/disabled/g, ''); 
			cell1.innerHTML = divadttTd0Str;
			$("#"+maxRowNum+"task1Id").attr('data-validation','required');
			$("#"+maxRowNum+"task1Id").addClass('selectpicker');
			$("#"+maxRowNum+"task1Id").attr('data-live-search','true');
			$("#"+maxRowNum+"task1Id").attr('data-size',8);
			
			
			var cell2 = row.insertCell(2);
			var divadttTd1Str = document.getElementById("divadttTd2").innerHTML;
			divadttTd1Str = divadttTd1Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd1Str = divadttTd1Str.replace(/ENTRYINDEX/g, (entryIndex)); 
			divadttTd1Str = divadttTd1Str.replace(/disabled/g, ''); 
			cell2.innerHTML = divadttTd1Str;
			
			var cell3 = row.insertCell(3);
			var divadttTd12Str = document.getElementById("divadttTd12").innerHTML;
			divadttTd12Str = divadttTd12Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd12Str = divadttTd12Str.replace(/ENTRYINDEX/g, (entryIndex)); 
			divadttTd12Str = divadttTd12Str.replace(/disabled/g, ''); 
			cell3.innerHTML = divadttTd12Str;
			$("#"+maxRowNum+"hourlyType1Id").attr('data-validation','required');
			$("#"+maxRowNum+"hourlyType1Id").addClass('selectpicker');
			$("#"+maxRowNum+"hourlyType1Id").attr('style','width:220px;');
			$("#"+maxRowNum+"hourlyType1Id").attr('data-size',8);
			
			
			
			var cell4 = row.insertCell(4);
			var divadttTd10Str = document.getElementById("divadttTd10").innerHTML;
			divadttTd10Str = divadttTd10Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd10Str = divadttTd10Str.replace(/ENTRYINDEX/g, (entryIndex)); 
			divadttTd10Str = divadttTd10Str.replace(/disabled/g, ''); 
			cell4.innerHTML = divadttTd10Str;
			
			
			
		    var cell5 = row.insertCell(5);
			var isCell1Disabled = $("#1timeSheetDay1Id").prop('disabled');
			var divadttTd2Str = document.getElementById("divadttTd3").innerHTML;
			divadttTd2Str = divadttTd2Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd2Str = divadttTd2Str.replace(/ENTRYINDEX/g, (entryIndex)); 
			
			var dateValue = $("[name='timesheetSubmission.timesheetEntries[0].date']").val();
			var dayOfDate =  $("[name='timesheetSubmission.timesheetEntries[0].dayOfTheDate']").val();
			divadttTd2Str = divadttTd2Str.replace(/DATEVALUE/g, (dateValue)); 
			divadttTd2Str = divadttTd2Str.replace(/DAYOFTHEDATE/g, (dayOfDate)); 
			divadttTd2Str = divadttTd2Str.replace(/disabled/g, ''); 
			cell5.innerHTML = divadttTd2Str;
			
			entryIndex = parseInt(entryIndex)+1;
			var cell6 = row.insertCell(6);
			var divadttTd3Str = document.getElementById("divadttTd4").innerHTML;
			divadttTd3Str = divadttTd3Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd3Str = divadttTd3Str.replace(/ENTRYINDEX/g, (entryIndex)); 
			divadttTd3Str = divadttTd3Str.replace(/disabled/g, ''); 
			
		    dateValue = $("[name='timesheetSubmission.timesheetEntries[1].date']").val();
		    dayOfDate =  $("[name='timesheetSubmission.timesheetEntries[1].dayOfTheDate']").val();
			divadttTd3Str = divadttTd3Str.replace(/DATEVALUE/g, (dateValue)); 
			divadttTd3Str = divadttTd3Str.replace(/DAYOFTHEDATE/g, (dayOfDate)); 
			cell6.innerHTML = divadttTd3Str;
			
			entryIndex = parseInt(entryIndex)+1;
			var cell7 = row.insertCell(7);
			var divadttTd4Str = document.getElementById("divadttTd5").innerHTML;
			divadttTd4Str = divadttTd4Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd4Str = divadttTd4Str.replace(/ENTRYINDEX/g, (entryIndex)); 
			divadttTd4Str = divadttTd4Str.replace(/disabled/g, '');
			dateValue = $("[name='timesheetSubmission.timesheetEntries[2].date']").val();
		    dayOfDate =  $("[name='timesheetSubmission.timesheetEntries[2].dayOfTheDate']").val();
			divadttTd4Str = divadttTd4Str.replace(/DATEVALUE/g, (dateValue)); 
			divadttTd4Str = divadttTd4Str.replace(/DAYOFTHEDATE/g, (dayOfDate)); 
			cell7.innerHTML = divadttTd4Str;
			
			entryIndex = parseInt(entryIndex)+1;
			var cell8= row.insertCell(8);
			var divadttTd5Str = document.getElementById("divadttTd6").innerHTML;
			divadttTd5Str = divadttTd5Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd5Str = divadttTd5Str.replace(/ENTRYINDEX/g, (entryIndex));
			divadttTd5Str = divadttTd5Str.replace(/disabled/g, '');
			dateValue = $("[name='timesheetSubmission.timesheetEntries[3].date']").val();
		    dayOfDate =  $("[name='timesheetSubmission.timesheetEntries[3].dayOfTheDate']").val();
			divadttTd5Str = divadttTd5Str.replace(/DATEVALUE/g, (dateValue)); 
			divadttTd5Str = divadttTd5Str.replace(/DAYOFTHEDATE/g, (dayOfDate)); 
			cell8.innerHTML = divadttTd5Str;
			
			entryIndex = parseInt(entryIndex)+1;
			var cell9 = row.insertCell(9);
			var divadttTd6Str = document.getElementById("divadttTd7").innerHTML; 
			divadttTd6Str = divadttTd6Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd6Str = divadttTd6Str.replace(/ENTRYINDEX/g, (entryIndex));
			divadttTd6Str = divadttTd6Str.replace(/disabled/g, '');
			dateValue = $("[name='timesheetSubmission.timesheetEntries[4].date']").val();
		    dayOfDate =  $("[name='timesheetSubmission.timesheetEntries[4].dayOfTheDate']").val();
			divadttTd6Str = divadttTd6Str.replace(/DATEVALUE/g, (dateValue)); 
			divadttTd6Str = divadttTd6Str.replace(/DAYOFTHEDATE/g, (dayOfDate)); 
			cell9.innerHTML = divadttTd6Str;
			
			entryIndex = parseInt(entryIndex)+1;
			var cell10 = row.insertCell(10);
			var divadttTd7Str = document.getElementById("divadttTd8").innerHTML;
			divadttTd7Str = divadttTd7Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd7Str = divadttTd7Str.replace(/ENTRYINDEX/g, (entryIndex)); 
			divadttTd7Str = divadttTd7Str.replace(/disabled/g, '');
			dateValue = $("[name='timesheetSubmission.timesheetEntries[5].date']").val();
		    dayOfDate =  $("[name='timesheetSubmission.timesheetEntries[5].dayOfTheDate']").val();
			divadttTd7Str = divadttTd7Str.replace(/DATEVALUE/g, (dateValue)); 
			divadttTd7Str = divadttTd7Str.replace(/DAYOFTHEDATE/g, (dayOfDate)); 
			cell10.innerHTML = divadttTd7Str;
			
			entryIndex = parseInt(entryIndex)+1;
			var cell11 = row.insertCell(11);
			var divadttTd8Str = document.getElementById("divadttTd9").innerHTML;
			divadttTd8Str = divadttTd8Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd8Str = divadttTd8Str.replace(/ENTRYINDEX/g, (entryIndex)); 
			divadttTd8Str = divadttTd8Str.replace(/disabled/g, '');
			dateValue = $("[name='timesheetSubmission.timesheetEntries[6].date']").val();
		    dayOfDate =  $("[name='timesheetSubmission.timesheetEntries[6].dayOfTheDate']").val();
			divadttTd8Str = divadttTd8Str.replace(/DATEVALUE/g, (dateValue)); 
			divadttTd8Str = divadttTd8Str.replace(/DAYOFTHEDATE/g, (dayOfDate));
			cell11.innerHTML = divadttTd8Str;
			
			entryIndex = parseInt(entryIndex)+1;
			$("#entryIndexValue").val(entryIndex);
			
			var cell12 = row.insertCell(12);
			cell12.className='timeTabFix';
			var divadttTd11Str = document.getElementById("divadttTd11").innerHTML;
			divadttTd11Str = divadttTd11Str.replace(/disabled/g, '');
			cell12.innerHTML = divadttTd11Str;
		    maskme();
		    $('.selectpicker').selectpicker('refresh');
		    $('.selectpicker').selectpicker({dropupAuto: false});
	     } 	
	 }	
        
        function disableInput(divId){
        	$("#"+divId+" :input").attr("disabled", "disabled");
        } 
       
        function prepareProjectTaskDetails(entryIndex,projectTaskId,projectMemberId){
        	 if($("#" + projectTaskId).val() != null && $("#" + projectTaskId).val() != "" && $("#" + projectTaskId).val() !="undefined"){
        		   var pmId =   $("#" + projectMemberId).val();
        		   var taskId = $("#" + projectTaskId).val().split(",");
    			   $("#" + projectTaskId + "BillableTypeId").html(taskId[1]);
    			  setTotals(); 
	  			   var taskDetails = $("#" + projectTaskId).val().split(",");
	  			   var taskId = taskDetails[0];
	        	   var taskType   = taskDetails[1];
	          	   var taskName = taskDetails[2]; 
	          	   var end = parseInt(entryIndex)+7;
	          	  for (i = entryIndex; i < end; i++) {
	          		   $("[name='timesheetSubmission.timesheetEntries["+i+"].projectMemberId']").val(pmId);
		          	   $("[name='timesheetSubmission.timesheetEntries["+i+"].timesheetTaskDTO.id']").val(taskId);
		          	   $("[name='timesheetSubmission.timesheetEntries["+i+"].timesheetTaskDTO.taskName']").val(taskName);
		          	   $("[name='timesheetSubmission.timesheetEntries["+i+"].timesheetTaskDTO.taskType']").val(taskType);
	              } 
        	 }
        }
        
        function setTicketNumber(entryIndex,taskNumberId){
        	if($("#" + taskNumberId).val() != null && $("#" + taskNumberId).val() !="undefined"){
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
        
        
        
        //flush project tasks
        function flushtasks(projectTaskId){
        	 $("#"+projectTaskId).next('i').remove();
        	 $("#"+projectTaskId).empty();
        	 $("#"+projectTaskId).append("<option value=''>--select task--</option>");
        }
        
       function enableOrDisableHours(entryIndex,pmId,ticketNumberId,rowId){
    	   var projectMemberId = $("#"+pmId).val();
		     if(projectMemberId != null && projectMemberId != '' && projectMemberId != 'undefined'){
                    <c:forEach var="statusEntry" items="${employeeTimesheetEntryDetailsDTO.statusMap}" varStatus="statusEntryCount">
                         if('${statusEntry.key}' == projectMemberId){
                        	   var monEditable = '${statusEntry.value.monEditable}';
	      	          		   var tueEditable = '${statusEntry.value.tueEditable}';
	      	                   var wedEditable = '${statusEntry.value.wedEditable}';
	      	                   var thuEditable = '${statusEntry.value.thuEditable}';
	      	                   var friEditable = '${statusEntry.value.friEditable}';
	      	                   var satEditable = '${statusEntry.value.satEditable}';
	      	                   var sunEditable = '${statusEntry.value.sunEditable}';
	      	                   var taskCommentsRequired = '${statusEntry.value.taskCommentsRequired}';
	      	                   var ticketNumberRequired = '${statusEntry.value.ticketNumberRequired}';
                        	  var end = parseInt(entryIndex)+7;
                        	  $("#"+ticketNumberId).removeAttr("data-validation");
                        	  if(ticketNumberRequired != null && ticketNumberRequired != '' && ticketNumberRequired != 'undefined' && ticketNumberRequired == "true"){
                        		  $("#"+ticketNumberId).attr("data-validation","required");
                        	  }
                        	  var count = 1;
            	          	  for (i = entryIndex; i < end; i++) {
            	          		  $("[name='timesheetSubmission.timesheetEntries["+i+"].hours']").removeAttr("disabled");
            	          		  $("[name='timesheetSubmission.timesheetEntries["+i+"].hours']").val("");
            	          		  $("#commentDiv"+i).find('i').removeAttr('class');
            	          		  $("#commentDiv"+i).find('i').attr('class','glyphicon glyphicon-comment');
            	          		  
            	          		  $("#"+rowId+"CommentValidation"+i+"Id").removeAttr("data-validation");
            	          		  $("#"+rowId+"CommentValidation"+i+"Id").next('p').remove();
            	          		  $("[name='timesheetSubmission.timesheetEntries["+i+"].hours']").removeAttr("data-validation-comment");
            	          		  if(taskCommentsRequired != null && taskCommentsRequired != '' && taskCommentsRequired != 'undefined' && taskCommentsRequired == "true"){
            	          			$("[name='timesheetSubmission.timesheetEntries["+i+"].hours']").attr("data-validation-comment","required");
                          	      }
            	          		
            	          		  if(count == 1 && monEditable != "true"){
            	          			 $("#commentDiv"+i).find('i').removeAttr('class');
            	          			 $("#commentDiv"+i).find('i').attr('class','glyphicon');
            	          			 $("#commentDiv"+i+" textarea").val('')
            	          			 $("[name='timesheetSubmission.timesheetEntries["+i+"].hours']").attr("disabled","disabled");
            	          		  }else if(count == 2 && tueEditable != "true"){
            	          			 $("#commentDiv"+i+" textarea").val('')
            	          			 $("#commentDiv"+i).find('i').removeAttr('class');
            	          			 $("#commentDiv"+i).find('i').attr('class','glyphicon');
            	          			 $("[name='timesheetSubmission.timesheetEntries["+i+"].hours']").attr("disabled","disabled");
            	          		  } else if(count == 3 && wedEditable != "true"){
            	          			 $("#commentDiv"+i+" textarea").val('')
            	          			 $("#commentDiv"+i).find('i').removeAttr('class');
            	          			 $("#commentDiv"+i).find('i').attr('class','glyphicon');
            	          		 	 $("[name='timesheetSubmission.timesheetEntries["+i+"].hours']").attr("disabled","disabled");
            	          		  } else if(count == 4 && thuEditable != "true"){
            	          			 $("#commentDiv"+i+" textarea").val('')
            	          			 $("#commentDiv"+i).find('i').removeAttr('class');
            	          			 $("#commentDiv"+i).find('i').attr('class','glyphicon');
            	          		 	 $("[name='timesheetSubmission.timesheetEntries["+i+"].hours']").attr("disabled","disabled");
            	          		  } else if(count == 5 && friEditable != "true"){
            	          			 $("#commentDiv"+i+" textarea").val('')
            	          			 $("#commentDiv"+i).find('i').removeAttr('class');
            	          			 $("#commentDiv"+i).find('i').attr('class','glyphicon');
            	          		 	 $("[name='timesheetSubmission.timesheetEntries["+i+"].hours']").attr("disabled","disabled");
            	          		  } else if(count == 6 && satEditable != "true"){
            	          			 $("#commentDiv"+i+" textarea").val('')
            	          			 $("#commentDiv"+i).find('i').removeAttr('class');
            	          			 $("#commentDiv"+i).find('i').attr('class','glyphicon');
            	          		 	 $("[name='timesheetSubmission.timesheetEntries["+i+"].hours']").attr("disabled","disabled");
            	          		  } else if(count == 7 && sunEditable != "true"){
            	          			 $("#commentDiv"+i+" textarea").val('')
            	          			 $("#commentDiv"+i).find('i').removeAttr('class');
            	          			 $("#commentDiv"+i).find('i').attr('class','glyphicon');
            	          		 	 $("[name='timesheetSubmission.timesheetEntries["+i+"].hours']").attr("disabled","disabled");
            	          		  }
            	          		 count++;
            	          		$("[name='timesheetSubmission.timesheetEntries["+i+"].timesheetStatus']").val('${statusEntry.value.timesheetStatus}');
            	              } 
                         }
		                </c:forEach>
		     }
       }
	   //get project tasks 
	   function getProjectTasks(projectTaskId,pmId){
		   $("#"+projectTaskId).next('i').remove();
		   var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
		   var projectMemberId = $("#"+pmId).val();
		   if(projectMemberId != null && projectMemberId != '' && projectMemberId != 'undefined'){
			   $("#"+projectTaskId).after('<i class="fa fa-spinner fa-pulse"></i>');
			   if($("#" + projectTaskId).val() != null && $("#" + projectTaskId).val() != "" && $("#" + projectTaskId).val() !="undefined"){
				   var taskId = $("#" + projectTaskId).val().split(",");
				   $("#" + projectTaskId + "BillableTypeId").html(taskId[1]);
				   setTotals(); 
			   }else{
				   $("#" + projectTaskId + "BillableTypeId").html(""); 
				  setTotals(); 
			   }
			   $("#"+projectTaskId).removeAttr('class');
			   $("#"+projectTaskId).attr('class','selectpicker smfield existedProjectTaskId'+projectMemberId);
			   
			   var avilableProjectTaskIds = [];
			  /*  $('.existedProjectTaskId'+projectMemberId).each(function() {
					 var localId = this.value;
					 if(localId != null &&  localId !='undefined' && localId != ''){
						 var taskIdArray = localId.split(",");
						 avilableProjectTaskIds.push(taskIdArray[0]);
					 }
			   });  */
			   $.ajax({
	   	          type : "POST",
	 	      	  url: sourceleadUrl+"/projectTasksByMemberId?projectMemeberId="+projectMemberId+"&avilableProjectTaskIds="+avilableProjectTaskIds,		
	 		      success :function(data) {
	 		    	    var json = JSON.parse(data);
	 		    	    $('.existedProjectTaskId'+projectMemberId).each(function() {
	 			   				 var localId = this.id;
	 			   				 var localValue = this.value;
	 			   				 
		 			   		    var result = ""; 
		 			   		    if(localValue != null && localValue != '' && localValue!= 'undefined'){
		 			   			    $('option', this).not(':eq(0), :selected').remove();
		 			   		    }else{ 
		 			   		    	 $("#"+localId).empty();
		 			   		   		// result = result + "<option value=''>--select task--</option>";
		 			   		    }
		  		    	    	for (var i=0; i<json.length; i++)
		  					 	{
		  			        		var opt = json[i];
		  			        		if(opt.generatedId != null && opt.generatedId !='undefined' && opt.generatedId !=''){
		  			        			result = result + "<option value='" +opt.Id+","+opt.billableType+","+opt.taskName+"'>"+opt.generatedId+"-"+opt.taskName+"</option>";
		  			        		}else{
		  			        			result = result + "<option value='" +opt.Id+","+opt.billableType+","+opt.taskName+"'>"+opt.taskName+"</option>";
		  			        		}
		  			        		
		  						}
		  		    	    	
	 				    	 	$("#"+localId).append(result);
	 				    	 	$("#"+localId).next('i').remove();
	 			   	   }); 
	 		    	   $("body").css('overflow-y', 'scroll');
	 		    	   $('.selectpicker').selectpicker('refresh');
	 		    	   $('.selectpicker').selectpicker({dropupAuto: false});
	 		    		    
	 		    		
	 		      }
	 	      	  
	 	       });  
		   }
	   }
</script>

<!-- this script is for remove timesheet -->
<script type="text/javascript">	
function removeTimeSheetRow(rowId) { 
			 document.getElementById("timesheetRow"+rowId).style.display = 'none';
			 document.getElementById("timesheetRow"+rowId).value= '';
			 var  rId = "timesheetRow"+rowId;
			  var r = document.getElementById("timesheet").rows[rId];
	          var count = (r.cells.length);
		      for(i=0;i<count;i++){
		        r.deleteCell(0);
		      } 
		      addOrRemoveTimeSheetCommentRow('');
		      setTotals(); 
		}
		
</script>	
<script type="text/javascript">	
   	    function alertHolidayDropMessage(rowId,holidayId,holidayUserId,holidayUserLocationId){
   		    var message = "<b>Are you sure to remove the holiday<b>";
   		    $("#timesheetDivByWeekFrequency").append("<div id='choicepopUp'></div>");
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
		    

<script type="text/javascript">	
function removeHolidayRow(rowId,holidayId,holidayUserId,holidayUserLocationId) { 
	         var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
			 document.getElementById("timesheetRow"+rowId).style.display = 'none';
			 document.getElementById("timesheetRow"+rowId).value= '';
			 var  rId = "timesheetRow"+rowId;
			  var r = document.getElementById("timesheet").rows[rId];
	          var count = (r.cells.length);
		      for(i=0;i<count;i++){
		        r.deleteCell(0);
		      } 
		      addOrRemoveTimeSheetCommentRow('');
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
		 
		var localValue = $("#"+id).val();
		 var commentId = id.replace("timeSheetDay","CommentValidation");
	     $("#"+commentId).next('p').remove();
		 $("#"+commentId).removeAttr("data-validation");
		 if(localValue != null && localValue != 'undefined' && localValue != '' && localValue != 'HH:MM' && localValue != '00:00'){
			 var attr = $("#"+id).attr("data-validation-comment");
			 if (typeof attr !== typeof undefined && attr !== false && attr == "required") {
				$("#"+commentId).attr("data-validation","required")
			 }	
		 } 
		 /* else{
			  $("#"+id).val("00"+":"+"00");
			 setTotals();
		 } */
		 
	
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
		//showError(result+" hours should not be more than 24");
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
<script>
   function rowValidation(){ 
	   rowRemoveError();
	   if ($('#timesheet_form').validate(false, validationSettings)){ 
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
					 $("#"+j+"projectMember1Id").find('p.jquery_form_error_message').remove(); 
					 $("#"+j+"projectMember1Id").after("<p class='jquery_form_error_message'>Atleat One Date Should Be Filled</span>");
					 $("#"+j+"projectMember1Id").attr('record-exist','yes');
					 $("#"+j+"projectMember1Id").attr('record-exist-errorMsg','Atleat One Date Should Be Filled');
				 }else{
					 $("#"+j+"projectMember1Id").removeAttr( "record-exist record-exist-errormsg");
					 $("#"+j+"projectMember1Id").find('p.jquery_form_error_message').remove();  
				 }
			 }
	   }
	     
	   
   }

</script>

<script>
   function rowRemoveError(){ 
	     var rowNum = $("#maxRowNumber").val();
		 var jValue = parseInt(rowNum)+1;
		 for (var j = 1; j < jValue; j++){
			 $("#"+j+"projectMember1Id").removeAttr( "record-exist record-exist-errormsg");
			 $("#"+j+"projectMember1IdS").find('p.jquery_form_error_message').remove();  
		 }
   }
</script>
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
			
			/* timesheetAttachmentValidationFun(); */
			
}			
</script>

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
function validateUploadFile(rowId,attNumber,item)
{
	  $('#'+rowId+'uploadFile'+attNumber).val(item.value.replace(/C:\\fakepath\\/i, ''));
	  var selectedFile = $('#'+rowId+'docFile'+attNumber).val();
	  if(selectedFile){
		  if(validate_fileupload(selectedFile)){
			   var docSize = $('#'+rowId+'docFile'+attNumber)[0].files[0].size;
		      if(docSize > 2097152){
		    	  $("#"+rowId+"AttchmentDiv"+attNumber+"Id").find('p.jquery_form_error_message').remove();
		    	  $('#'+rowId+'uploadFile'+attNumber).after("<p class='jquery_form_error_message'>Limit is exceeded than 2MB </p>");
		    	  $('#'+rowId+'uploadFile'+attNumber).attr('record-exist','yes');
		    	  $('#'+rowId+'uploadFile'+attNumber).attr('record-exist-errorMsg','Limit is exceeded than 2MB'); 
		      }else{
		    	 $('#timesheetAttValidationInput'+rowId).removeAttr( "record-exist record-exist-errormsg");
		    	 $("#"+rowId+"AttchmentDiv"+attNumber+"Id").find('p.jquery_form_error_message').remove();
		    	 $('#timesheetAttValidationInput'+rowId).val('1');
		    	 fileUpload(''+rowId+'docFile'+attNumber, "timesheetDocuments", "timesheet_form","timsheetAttachments["+rowId+"].fileInfo","","");
		      }
		  }
		  else{
			      $("#"+rowId+"AttchmentDiv"+attNumber+"Id").find('p.jquery_form_error_message').remove();
			      $('#'+rowId+'uploadFile'+attNumber).after("<p class='jquery_form_error_message' >Invalid Format</p>");  
			      $('#'+rowId+'uploadFile'+attNumber).attr('record-exist','yes');
			      $('#'+rowId+'uploadFile'+attNumber).attr('record-exist-errorMsg','Invalid Format'); 
		  }
	  }else{
		  $("#"+rowId+"AttchmentDiv"+attNumber+"Id").find('p.jquery_form_error_message').remove();
	  }
	
}
</script>
<script type="text/javascript">
   function alertMessage(message,status,savingType){
   $("#timesheetDivByWeekFrequency").append("<div id='choicepopUp'></div>");
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
<!-- this script is for delete document -->
<script type="text/javascript">
function deleteSubmissionDocument(submissionDocumentId,attDivIndex){  
    var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
    var projectExistingValue = $("#dbIsTimesheetAttachmentRequired"+attDivIndex).val();
    var strongTagCount = $("#"+attDivIndex+"attachMentCount").val();
    strongTagCount = parseInt(strongTagCount);
    $("#"+submissionDocumentId+"document").remove();
    strongTagCount = parseInt(strongTagCount)-1;
    $("#"+attDivIndex+"attachMentCount").val(strongTagCount);
    if(projectExistingValue != null && projectExistingValue != "" && projectExistingValue !="undefined" && projectExistingValue==1
		  && strongTagCount != null &&  strongTagCount==0){
	  $("#isTimesheetAttachmentRequired"+attDivIndex).val('Y');
    }
}
</script>
<script type="text/javascript">
function viewSubmissionDocument(submissionDocumentId,title){
	 var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
		window.open(sourceleadUrl+'/viewDocument/'+title+'?submissionDocumentId='+submissionDocumentId,title,"height=600,width=600");
}
</script>
<script>
function dropDownExport(){
	$("#exporrtEntries").slideToggle();
}
</script>

<!-- this script is for Timesheet Entries Export -->
<script>
function timesheetEntriesExport(exportType){
	 var currentTimeFrequency = $("#currentTimeFrequency").val();
	var dateFrequency = currentTimeFrequency.split('To');
	var projectMemberId = $("#projectMemberId").val();
	var startDate = dateFrequency[0];
	var endDate = dateFrequency[1];
	 var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
	var status = '${timesheetSubmissionDTO.timesheetStatus}';
	 var tableFor = $("#tableFor").val();
	 var requestFrom = "employee";
	 if(tableFor != null && tableFor != 'undefined' && tableFor != ''){
		 requestFrom = tableFor;
	 }
	if(status != null && status != 'undefined' && status != '' && status != 'NEW' && status != 'new'){
		window.location.href = sourceleadUrl+"/exportTimeSheet?${_csrf.parameterName}=${_csrf.token}&projectMemberId="+projectMemberId+"&startDate="+startDate+"&endDate="+endDate+"&exportType="+exportType+"&requestFrom="+requestFrom;
	}else{
		alert("Please fill the TimeSheets..");
	}
}
</script>
<script type="text/javascript">
function newSaveTimesheet(status){ 
	 var savingType = 'user';
	 $(".time_btn").attr("disabled", "disabled");
	 $("#loadTime").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:60% center; background-repeat:no-repeat; background-size:75px;');
     checkHours();
     validateTimesheetHours(status);
	 var validationSettings = {
		        errorMessagePosition : 'element',
		       borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd' 
	 }; 
	  $('.timesheetAttValidation').each(function() {
		     var id = this.id;
			 var localValue = this.value;
			 var dataRowId = $("#"+id).attr('data-rowId');
			 $('#timesheetAttValidationInput'+dataRowId).removeAttr('data-validation');
			 if(localValue != null &&  localValue !='undefined' && localValue != '' && localValue == 'Y'){
				 $('#timesheetAttValidationInput'+dataRowId).attr("data-validation","required"); 
				 $(".time_btn").removeAttr("disabled");
				 $("#loadTime").removeAttr("style"); 
			 }
	   }); 
	 if ($('#timesheet_form').validate(false, validationSettings) && !$("#timeSheetErrorMsg").is(':visible')){ 
		     if(status != null && status != 'undefined' && status != '' && ((status == 'Submitted') || (status == 'Draft'))){
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
              
	 } else{ 
	 		 $(".time_btn").removeAttr("disabled");
	 		 $("#loadTime").removeAttr("style"); 
			return false;
	   }  
}
</script>
<script>
function validateTimesheetHours(status){
	 var timesheetAvgHoursValidation ='${timesheetAvgHoursValidation}'; 
	 if(status != null && status != 'undefined' && status != '' && status == 'Submitted' && timesheetAvgHoursValidation == 'Y'){
		 var total = $("#grandTotal").val();
		 var wokingHoursPerWk ='${minimumWorkingHoursPerWeek}'; 
		 if(total != "" && total != null && total != 'undefined'){
			 total = total.replace(":",".")
			 var grandTotal = parseFloat(total);
			 if(grandTotal < wokingHoursPerWk){
				    var outPut = "<p> Week Total Hours Should be Minimum "+wokingHoursPerWk+" <b>X</b></p>";
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
	 }
}
</script>
<script type="text/javascript">
function timesheetApprovalTableRowByProjectMemberId(timesheetFrequency,projectMembersId,from){
	    $('#notifyLink').val('');
	    $('#hedderAcceptRejectButton').hide();
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
    	
    	  <c:forEach var="oldSubPm" items="${employeeTimesheetEntryDetailsDTO.oldSubmittedProjectMembersList}" varStatus="oldSubPmCount">
    	      formData.append("oldSubmittedProjectMembersList["+'${oldSubPmCount.index}'+"]", '${oldSubPm}');
          </c:forEach>
    	 $("#loadTime").removeAttr('style');
    	 $("#loadTime").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:60% center; background-repeat:no-repeat; background-size:75px;');
	   $.ajax({
		    type : "POST",
   	        data: formData,
	        processData: false,
	        contentType:false,
	      	  url: sourceleadUrl+"/saveEmployeeTimesheetEntries?timesheetSubmission.timesheetStatus="+status+"&timesheetFor="+savingType,		
		      success :function(data) {
		  		     $("#timesheetEntriesDiv").empty();
 		    	     $("#timesheetEntriesDiv").append(data);
 		    	     $("#loadTime").removeAttr('style');
  		    	     prepareChosenScripts();
  		    	     setTotals();
		      }
	      	  
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
	        		    		  $("#currentTimeSubFrequency").val(currentTimeSubFrequency);
		   	            	      $("#timeSubFrequency").val(currentTimeSubFrequency);
	        		    	  }
	        		    	  $("#loadTime").removeAttr('style');
	        		      }
	        	    }); 
	}
</script> 
<script type="text/javascript">
$(".content-wraper").click(function() {
	bodyClick();
});
$('.selectpicker').selectpicker('refresh');
</script>
<!-- Tab Switching -->
<script type="text/javascript"	src='/resources/js/slead-common.js?staticTime=${staticTime}'></script>

