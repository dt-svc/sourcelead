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
				    <input type='hidden' name='maxRowNumber'  class='initiateTimesheetMax' id='maxRowNumber'  value='${employeeTimesheetEntryDetailsDTO.maxRowCount > 0 ? employeeTimesheetEntryDetailsDTO.maxRowCount : 1}' />
				                <div class="timesheet_tableNew">     
						       	  <div class="tabFixed">            
						        	<table id="timesheet" cellpadding="0" cellspacing="0">
						            	<tbody>
						                	<tr>
												<th>Projects</th>
						                       	<th>Tasks </th>
						                       	<th>Task Type</th>
						                       	<th>Ref/Ticket No.</th>
						                        <c:forEach var="weekDays" items="${employeeTimesheetEntryDetailsDTO.weekDays}">
												   <th>${weekDays.dayOfTheDate}<i>${weekDays.date}</i></th>
												</c:forEach>
						                        <th class="timeTabFix">Status</th>              
						                    </tr>  
							                <c:set var="entryIndex" value="0"/>
							                <c:set var="timeSheetSubmissionStatus"/>  
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
												            
											    <tr id="timesheetRow${entryCount.index+1}">
											        <td id="${entryCount.index+1}projectTd">
														<div class="timeAdd">
														    <c:if test="${timeSheetSubmissionStatus != 'APPROVED' && timeSheetSubmissionStatus != 'SUBMITTED'}">
														        <select id="${entryCount.index+1}projectMember1Id" class='existedProjectMembers' name="${entryCount.index+1}projectMember1Id" onchange="enableOrDisableHours('${entryIndex}','${entryCount.index+1}projectMember1Id','${entryCount.index+1}ticketNumber1Id');flushtasks('${entryCount.index+1}task1Id');" data-validation="required" field-name="Project">
																	<option value=''>--select project--</option> 
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
															       <input value="" onclick="removeTimeSheetRow('${entryCount.index+1}');" class="timeRemoveBtn" type="button">
															      </c:if>
															 </c:if>
															 <c:if test="${timeSheetSubmissionStatus == 'APPROVED' || timeSheetSubmissionStatus == 'SUBMITTED'}">
															         <c:set var="projectCheck"/>
															         <c:forEach var="pm" items="${employeeTimesheetEntryDetailsDTO.submittedProjectMembersList}">
															               <c:if test="${!empty entry.projectMemberId && entry.projectMemberId == pm.id}">
															                  <input type="hidden" class='existedProjectMembers' id="${entryCount.index+1}projectMember1Id" name="${entryCount.index+1}projectMember1Id" value="${pm.id}"/>
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
										             <td id="${entryCount.index+1}taskTd">
														<div class="taskDropDown">
														  <c:set var="taskType" value=""/>
														  <c:set var="taskId" value=""/>
														  <c:set var="taskName" value=""/>
														  <c:if test="${timeSheetSubmissionStatus != 'APPROVED' && timeSheetSubmissionStatus != 'SUBMITTED'}">
															   <select id="${entryCount.index+1}task1Id" name="${entryCount.index+1}task1Id"  class="smfield existedProjectTaskId${entry.projectMemberId}"  onclick='getProjectTasks("${entryCount.index+1}task1Id","${entryCount.index+1}projectMember1Id");' onchange='prepareProjectTaskDetails("${entryIndex}","${entryCount.index+1}task1Id","${entryCount.index+1}projectMember1Id");' data-validation="required" field-name="Task">
																	<option value=''>--select task--</option>
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
														   <c:if test="${timeSheetSubmissionStatus == 'APPROVED' || timeSheetSubmissionStatus == 'SUBMITTED'}">
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
												           <span id="${entryCount.index+1}task1IdBillableTypeId">${!empty taskType ? taskType : '--'}</span>
												        </div>
												      </td>
										              <td>
										                  <c:if test="${timeSheetSubmissionStatus != 'APPROVED' && timeSheetSubmissionStatus != 'SUBMITTED'}">
													         <div><input type="text" ${!empty isTicketNumberRequired && isTicketNumberRequired == 'Y' ? 'data-validation="required"' : ''}  id="${entryCount.index+1}ticketNumber1Id" name="${entryCount.index+1}ticketNumber1Id"  value="${entry.ticketNumber}" field-name=" " onblur='setTicketNumber("${entryIndex}","${entryCount.index+1}ticketNumber1Id");' class="timeTick"></div>
													      </c:if>
													      <c:if test="${timeSheetSubmissionStatus == 'APPROVED' || timeSheetSubmissionStatus == 'SUBMITTED'}">
													         <c:if test="${!empty entry.ticketNumber}">
													             <div>${entry.ticketNumber}</div>
													         </c:if>
													         <c:if test="${empty entry.ticketNumber}">
													             <div>--</div>
													         </c:if>
													          
													      </c:if>
												     </td>
												     <c:forEach var="te" items="${entry.timesheetEntries}" varStatus="teStatus">
													      <td> 
													           <div class="timeEntry">
													               <input type="hidden" name="timesheetSubmission.timesheetEntries[${entryIndex}].timesheetTaskDTO.id" value="${taskId}"/>
																   <input type="hidden" name="timesheetSubmission.timesheetEntries[${entryIndex}].timesheetTaskDTO.taskName" value="${taskName}"/>
																   <input type="hidden" name="timesheetSubmission.timesheetEntries[${entryIndex}].timesheetTaskDTO.taskType" value="${taskType}"/>
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
						                                             
													               <input onblur="editMe(this);" ${(!empty isEditable && isEditable == 'Y') ? '' : 'disabled="disabled"'} 
													                id="${entryCount.index+1}timeSheetDay${teStatus.index+1}Id" name="timesheetSubmission.timesheetEntries[${entryIndex}].hours" class="inline_field" value="${te.hours}" placeholder="HH:MM" type="text">
													                   <c:if test="${!empty te.comments || (empty te.comments && (!empty isEditable && isEditable == 'Y'))}">
                                                                         <div class="timesheet_comm" id="commentDiv${entryIndex}">                
													                       <i id="commentI${entryIndex}" class='glyphicon glyphicon-comment ${!empty te.comments ? "com_active":""}' onclick='timesheetComment("${entryCount.index+1}comment${teStatus.index+1}Id");'></i>
													                       <span class='teskcomment' id='${entryCount.index+1}comment${teStatus.index+1}Id'>
													                             <textarea  ${(!empty isEditable && isEditable == 'Y') ? '' : 'disabled="disabled"'} onblur="makeActive(this,'commentI${entryIndex}');" name='timesheetSubmission.timesheetEntries[${entryIndex}].comments' >${te.comments}</textarea>
			                                                               </span>
			                                                             </div>
													                   </c:if>
													               </div>
													              <c:set var="entryIndex" value="${entryIndex+1}"/>
													      </td>
											         </c:forEach>
												     <td class="timeTabFix"> 
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
												             </div>
												        </c:if>
												        <c:if test="${empty entry.projectMemberId}">
												            <div><b>New</b></div> 
												        </c:if> 
						                  		    </td> 
										        </tr>
										     </c:forEach>
											
						                    
							               <tr class="projTotal">
											<td rowspan="2">Total</td>
											<td> <div></div></td>
					                 		<td> <div></div></td>
					                 		<td> <div></div></td>
											<td>
												<div >
												<input type="hidden" id="entryIndexValue" value="${entryIndex}"/>
													<input disabled="" id="rowTotal1Id" name="rowTotal1Id"
														class="inline_field noBorder" value="HH:MM" placeholder="00:00"
														type="text">
												</div>
											</td>
											<td>
												<div >
													<input disabled="" id="rowTotal2Id" name="rowTotal2Id"
														class="inline_field noBorder" value="HH:MM" placeholder="00:00"
														type="text">
												</div>
											</td>
											<td>
												<div >
													<input disabled="" id="rowTotal3Id" name="rowTotal3Id"
														class="inline_field noBorder" value="HH:MM" placeholder="00:00"
														type="text">
												</div>
											</td>
											<td>
												<div >
													<input disabled="" id="rowTotal4Id" name="rowTotal4Id"
														class="inline_field noBorder" value="HH:MM" placeholder="00:00"
														type="text">
												</div>
											</td>
											<td>
												<div >
													<input disabled="" id="rowTotal5Id" name="rowTotal5Id"
														class="inline_field noBorder" value="HH:MM" placeholder="00:00"
														type="text">
												</div>
											</td>
											<td>
												<div >
													<input disabled="" id="rowTotal6Id" name="rowTotal6Id"
														class="inline_field noBorder" value="HH:MM" placeholder="00:00"
														type="text">
												</div>
											</td>
											<td>
												<div >
													<input disabled="" id="rowTotal7Id" name="rowTotal7Id"
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
							            <div class="commentFile">
							                  <h2>
							                  <c:if test="${employeeTimesheetEntryDetailsDTO.submitButtonVisible || !empty employeeTimesheetEntryDetailsDTO.timsheetAttachments}">
											    Attachmets & Comments 
											     <c:if test="${employeeTimesheetEntryDetailsDTO.submitButtonVisible}">
											         <i class="fa fa-plus Cursor" onclick="addTimeSheetCommentRow()"></i> 
											      </c:if>
											  </c:if>
											 </h2>
											<div class="">
											   <input type='hidden' name='maxAttRowNumber' id='maxAttRowNumber'  value='${!empty employeeTimesheetEntryDetailsDTO.timsheetAttachments ? fn:length(employeeTimesheetEntryDetailsDTO.timsheetAttachments) : 1}' />
												<table id="attTimesheet">
												<c:if test="${!empty employeeTimesheetEntryDetailsDTO.timsheetAttachments}">
												   <c:forEach var="attachment" items="${employeeTimesheetEntryDetailsDTO.timsheetAttachments}" varStatus="attCount">
												     <tr id="timesheetAttRow${attCount.index}">
													   <td>
													       <div class="timeAdd">
													              <c:if test="${employeeTimesheetEntryDetailsDTO.submitButtonVisible}">
	                                                                    <select id="${attCount.index}attProjMemberId" class='existedAttProjectMembers form-control input-sm' onclick="prepareAttachmentProjectMembers(${attCount.index},${attachment.projectMemberId});" name="timsheetAttachments[${attCount.index}].projectMemberId"  field-name="Project">  
		                                                                  <option value=''>--select Project--</option>
															               <c:forEach var="pm" items="${employeeTimesheetEntryDetailsDTO.attProjectMembersList}">
															                      <c:if test="${pm.id == attachment.projectMemberId}">
															                            <option value='${pm.id}' selected>${pm.projectName} </option> 
															                       </c:if>
															                       <c:if test="${pm.id != attachment.projectMemberId}">
															                           <option value='${pm.id}'>${pm.projectName} </option>
															                       </c:if>
																            </c:forEach>
															            </select>
														         </c:if>
														         <c:if test="${!employeeTimesheetEntryDetailsDTO.submitButtonVisible}">
															          <c:forEach var="pm" items="${employeeTimesheetEntryDetailsDTO.attProjectMembersList}">
															             <c:if test="${pm.id == attachment.projectMemberId}">
															                  <p>${pm.projectName}</p> 
															                  <input type="hidden" class='existedAttProjectMembers' name="timsheetAttachments[${attCount.index}].projectMemberId" value="${attachment.projectMemberId}" />
															             </c:if>
																	 </c:forEach>
														         </c:if>
														    </div> 
														</td>
														<td>
	                                                         <div id="attachmentInnerDiv${attCount.index}Id">
	                                                               <input type='hidden' name='${attCount.index}maxAttDivRowNumber' id='${attCount.index}maxAttDivRowNumber'  value='1' />
			                                                        <c:set var="attNumber" value="0"/>
			                                                        <c:if test="${!empty attachment.fileInfo}">
			                                                         <c:forEach var="finfo" items="${attachment.fileInfo}">
			                                                           <c:set var="attNumber" value="${attNumber+1}"/>
																		<div id='${finfo.fileDbId}document' class="timeAtt">
			                                                                <a  href='${finfo.fileURL}' download><div class="attFile"><i class="fa fa-download"></i>${finfo.fileName}</div></a>
										                                    <div class="timeAttBtn">
										                                        <i class="fa fa-eye" onClick="viewFile('${finfo.fileURL}','${finfo.fileName}','${finfo.contentType}');"></i>
										                                       <c:if test="${employeeTimesheetEntryDetailsDTO.submitButtonVisible}">
										                                            <i class="fa fa-trash" onClick='deleteSubmissionDocument("${finfo.fileDbId}");'></i>
										                                       </c:if>
										                                    </div>
			                                                            </div>
										                              </c:forEach>
										                              </c:if>
										                              <c:if test="${empty attachment.fileInfo}">
										                                  <p> No Attachments Found</p>
										                              </c:if>
			                                                          <c:if test="${employeeTimesheetEntryDetailsDTO.submitButtonVisible}">
				                                                             <div id="attachmentInnerDiv0Id">
					                                                                  <input type='hidden' name='0maxAttDivRowNumber' id='0maxAttDivRowNumber'  value='1' />
							                                                          <div id="0AttchmentDiv0Id" class="addRowBtn">
																						  <div class="input-group col-md-12" style="margin:0 auto">
																							<label class="input-group-btn"> <span class="btn btn-primary input-sm"><i class="fa fa-folder-open-o"></i>
																							  <input style="display: none;" id="0docFile0" multiple  onchange="validateUploadFile('0','0',this);" class="upload" type="file">
																							  </span></label>
																							<input class="form-control input-sm" id="0uploadFile0"   placeholder="Attachment" type="text">
																						  </div>
																						  <input type="button" value="" onclick="addAttachmentRow('0');" class="addRowIcon"/>
																					</div>
																			</div>
			                                                          </c:if>
															 </div>
														</td>
														<td>
															<input type="text" name="timsheetAttachments[${attCount.index}].weekComment" readonly="readonly" class="form-control  input-sm" value="${attachment.weekComment}" placeholder="Comment" />
														</td>
													    </tr>
												   </c:forEach>
												</c:if>
												<c:if test="${empty employeeTimesheetEntryDetailsDTO.timsheetAttachments && employeeTimesheetEntryDetailsDTO.submitButtonVisible}">
												      <tr id="timesheetAttRow0">
													   <td>
													       <div class="timeAdd">
														        <select id="0attProjMemberId"  name="timsheetAttachments[0].projectMemberId"  class='existedAttProjectMembers form-control input-sm' onclick="prepareAttachmentProjectMembers(0,'');"  field-name="Project">
																	<option value=''>--select project--</option> 
															        <c:forEach var="pm" items="${employeeTimesheetEntryDetailsDTO.attProjectMembersList}">
																	     <option value='${pm.id}'>${pm.projectName} </option> 
																	 </c:forEach>
															    </select>
														    </div> 
														</td>
														<td>
	                                                         <div id="attachmentInnerDiv0Id">
	                                                                  <input type='hidden' name='0maxAttDivRowNumber' id='0maxAttDivRowNumber'  value='1' />
			                                                          <div id="0AttchmentDiv0Id" class="addRowBtn">
																		  <div class="input-group col-md-12" style="margin:0 auto">
																			<label class="input-group-btn"> <span class="btn btn-primary input-sm"><i class="fa fa-folder-open-o"></i>
																			  <input style="display: none;" id="0docFile0" multiple  onchange="validateUploadFile('0','0',this);" class="upload" type="file">
																			  </span></label>
																			<input class="form-control input-sm" id="0uploadFile0"   placeholder="Attachment" type="text">
																		  </div>
																		  <input type="button" value="" onclick="addAttachmentRow('0');" class="addRowIcon"/>
																	</div>
															</div>
														</td>
														<td>
															<input type="text" name="timsheetAttachments[0].weekComment" class="form-control  input-sm" placeholder="Comment" />
														</td>
													</tr>	
												 </c:if>
												</table>
											</div>
							            </div>  
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
	             <div class="timeAdd"> 
	              <select id="REPLACEROWIDprojectMember1Id"  name="REPLACEROWIDprojectMember1Id"  class='existedProjectMembers' onchange="flushtasks('REPLACEROWIDtask1Id');enableOrDisableHours('ENTRYINDEX','REPLACEROWIDprojectMember1Id','REPLACEROWIDticketNumber1Id');"  field-name="Project">  
	              <option value=''>--select Project--</option>
	               <c:forEach var="pMember" items="${employeeTimesheetEntryDetailsDTO.projectMembersList}">
		                   <option value='${pMember.id}'>${pMember.projectName} </option> 
		            </c:forEach>
	              </select>
	             <input type="button" value="" onClick="removeTimeSheetRow('REPLACEROWID')" class="timeRemoveBtn"/>
	             </div>
	        </div>
	         <div id="divadttTd1" style="display:none;">   
	             <div class="taskDropDown"> 
	              <select id="REPLACEROWIDtask1Id"  name="REPLACEROWIDtask1Id" class="smfield"  field-name="Task" onchange="prepareProjectTaskDetails('ENTRYINDEX','REPLACEROWIDtask1Id','REPLACEROWIDprojectMember1Id')"  onclick="getProjectTasks('REPLACEROWIDtask1Id','REPLACEROWIDprojectMember1Id')">  
	              <option value=''>--select task--</option>
	              </select>
	             </div>
	        </div>
	        <div id="divadttTd2" style="display:none;">
		    	   <span id="REPLACEROWIDtask1IdBillableTypeId" name="REPLACEROWIDtask1IdBillableTypeId" ></span>	
	   		</div>
	   		 <div id="divadttTd10" style="display:none;">
		    	  <div><input type="text" id="REPLACEROWIDticketNumber1Id" name="REPLACEROWIDticketNumber1Id" onblur="setTicketNumber('ENTRYINDEX','REPLACEROWIDticketNumber1Id');" class="timeTick" field-name=" "></div>	
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
	               <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetStatus" /> 
	               <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].dayOfTheDate" value="DAYOFTHEDATE"/>
	               <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].date" value="DATEVALUE"/>
		    	   <input type="text" disabled onblur='editMe(this);'   id="REPLACEROWIDtimeSheetDay1Id" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].hours" class="inline_field" placeholder="HH:MM" />	
	               <div class="timesheet_comm" id="commentDivENTRYINDEX">
	                <i class="glyphicon glyphicon-comment" id="commentIENTRYINDEX" onclick="timesheetComment('REPLACEROWIDcomment1Id');"></i>
	                <span class="teskcomment" id="REPLACEROWIDcomment1Id" ><textarea disabled  onblur="makeActive(this,'commentIENTRYINDEX');" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].comments" id="REPLACEROWIDtextArea1Id" ></textarea></span>
	               </div>
	   		</div>
	        <div id="divadttTd4" style="display:none;">
		               <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.id" />
					   <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.taskName" />
					   <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.taskType"/>
	                   <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].projectMemberId"/>
		               <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].dayOfTheDate" value="DAYOFTHEDATE"/>
		               <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetStatus" /> 
		               <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].date" value="DATEVALUE"/>
	                   <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].ticketNumber"/> 
					  <input type="text" disabled onblur='editMe(this);'   id="REPLACEROWIDtimeSheetDay2Id" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].hours" class="inline_field" placeholder="HH:MM" />	
	                  <div class="timesheet_comm" id="commentDivENTRYINDEX">
	                      <i class="glyphicon glyphicon-comment" id="commentIENTRYINDEX" onclick="timesheetComment('REPLACEROWIDcomment2Id');"></i>
	                  <span class="teskcomment" id="REPLACEROWIDcomment2Id" ><textarea onblur="makeActive(this,'commentIENTRYINDEX');" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].comments" id="REPLACEROWIDtextArea2Id" ></textarea></span></div>
	        </div>
	        <div id="divadttTd5" style="display:none;">
	                   <input type="hidden"  disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.id" />
					   <input type="hidden"  disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.taskName" />
					   <input type="hidden"  disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.taskType"/>
	                   <input type="hidden"  disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].projectMemberId"/>
		               <input type="hidden"  disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].dayOfTheDate" value="DAYOFTHEDATE"/>
		               <input type="hidden"  disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetStatus" /> 
		               <input type="hidden"  disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].date" value="DATEVALUE"/>
	                   <input type="hidden"  disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].ticketNumber"/> 
					  <input type="text" onblur='editMe(this);' disabled  id="REPLACEROWIDtimeSheetDay3Id" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].hours" class="inline_field" placeholder="HH:MM" />	
	                  <div class="timesheet_comm" id="commentDivENTRYINDEX">
	                  <i class="glyphicon glyphicon-comment" id="commentIENTRYINDEX" onclick="timesheetComment('REPLACEROWIDcomment3Id');"></i>
	                  <span class="teskcomment" id="REPLACEROWIDcomment3Id" ><textarea onblur="makeActive(this,'commentIENTRYINDEX');" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].comments" id="REPLACEROWIDtextArea3Id"></textarea></span></div>
	        </div>
	        <div id="divadttTd6" style="display:none;">
	                   <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.id" />
					   <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.taskName" />
					   <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.taskType"/>
	                   <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].projectMemberId"/>
		               <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].dayOfTheDate" value="DAYOFTHEDATE"/>
		               <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetStatus" /> 
		               <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].date" value="DATEVALUE"/>
		               <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].ticketNumber"/> 
					  <input type="text" onblur='editMe(this);' disabled id="REPLACEROWIDtimeSheetDay4Id" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].hours" class="inline_field" placeholder="HH:MM" />	
	                  <div class="timesheet_comm" id="commentDivENTRYINDEX">
	                  <i class="glyphicon glyphicon-comment" id="commentIENTRYINDEX" onclick="timesheetComment('REPLACEROWIDcomment4Id');"></i>
	                  <span class="teskcomment" id="REPLACEROWIDcomment4Id" ><textarea onblur="makeActive(this,'commentIENTRYINDEX');" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].comments" id="REPLACEROWIDtextArea4Id"></textarea></span></div>           
	        </div>
	        <div id="divadttTd7" style="display:none;">
	                   <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.id" />
					   <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.taskName" />
					   <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.taskType"/>
	                   <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].projectMemberId"/>
		               <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].dayOfTheDate" value="DAYOFTHEDATE"/>
		               <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetStatus" /> 
		               <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].date" value="DATEVALUE"/>
		               <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].ticketNumber"/> 
					  <input type="text" onblur='editMe(this);' disabled id="REPLACEROWIDtimeSheetDay5Id" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].hours" class="inline_field" placeholder="HH:MM" />	
	                  <div class="timesheet_comm" id="commentDivENTRYINDEX">
	                  <i class="glyphicon glyphicon-comment" id="commentIENTRYINDEX" onclick="timesheetComment('REPLACEROWIDcomment5Id');"></i>
	                  <span class="teskcomment" id="REPLACEROWIDcomment5Id" ><textarea  onblur="makeActive(this,'commentIENTRYINDEX');" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].comments" id="REPLACEROWIDtextArea5Id"></textarea></span></div>  
	    	 </div>
	         <div id="divadttTd8" style="display:none;">
	                   <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.id" />
					   <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.taskName" />
					   <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.taskType"/>
	                   <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].projectMemberId"/>
		               <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].dayOfTheDate" value="DAYOFTHEDATE"/>
		               <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetStatus" /> 
		               <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].date" value="DATEVALUE"/>
		               <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].ticketNumber"/> 
				     <input type="text" onblur='editMe(this);' disabled id="REPLACEROWIDtimeSheetDay6Id" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].hours" class="inline_field" placeholder="HH:MM" />	
	                 <div class="timesheet_comm" id="commentDivENTRYINDEX">
	                 <i class="glyphicon glyphicon-comment" id="commentIENTRYINDEX" onclick="timesheetComment('REPLACEROWIDcomment6Id');"></i>
	                 <span class="teskcomment" id="REPLACEROWIDcomment6Id" ><textarea onblur="makeActive(this,'commentIENTRYINDEX');" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].comments" id="REPLACEROWIDtextArea6Id"></textarea></span></div>
	         </div>
	         <div id="divadttTd9" style="display:none;">
	                 <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.id" />
					 <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.taskName" />
					 <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.taskType"/>
	                 <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].projectMemberId"/>
	                 <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].dayOfTheDate" value="DAYOFTHEDATE"/>
	                 <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetStatus" /> 
	                 <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].date" value="DATEVALUE"/>
		             <input type="hidden" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].ticketNumber"/> 
				     <input type="text" onblur='editMe(this);' disabled id="REPLACEROWIDtimeSheetDay7Id" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].hours" class="inline_field" placeholder="HH:MM" />	
	                 <div class="timesheet_comm" id="commentDivENTRYINDEX">
	                 <i class="glyphicon glyphicon-comment" id="commentIENTRYINDEX" onclick="timesheetComment('REPLACEROWIDcomment7Id');"></i>
	                 <span class="teskcomment" id="REPLACEROWIDcomment7Id" ><textarea onblur="makeActive(this,'commentIENTRYINDEX');" disabled name="timesheetSubmission.timesheetEntries[ENTRYINDEX].comments" id="REPLACEROWIDtextArea7Id"></textarea></span></div>            
	    	 </div>
	    	 
	    	  <!--  Add Attachments Divs  ####-->
            <div id="divComentTd0" style="display:none;">  
                      <div class="timeAdd">
                       <select id="REPLACEROWIDattProjMemberId"  class="EXISTEDATTCLASS form-control input-sm" onclick="prepareAttachmentProjectMembers(REPLACEROWID,'');" disabled name="timsheetAttachments[REPLACEROWID].projectMemberId"  field-name="Project">  
			               <option value=''>--select Project--</option>
		                </select>
                        <input type="button" value="" onClick="removeTimeSheetCommentRow('REPLACEROWID')" class="timeRemoveBtn"/>
                      </div> 
		             
	          </div>
	          <div id="divComentTd1" style="display:none;">  
	                <div id="attachmentInnerDivREPLACEROWIDId">
	                    <input type='hidden' name='REPLACEROWIDmaxAttDivRowNumber' id='REPLACEROWIDmaxAttDivRowNumber'  value='1' />
	                     <div id="REPLACEROWIDAttchmentDiv0Id" class="addRowBtn">
						  <div class="input-group col-md-12" style="margin:0 auto">
							<label class="input-group-btn"> <span class="btn btn-primary input-sm"><i class="fa fa-folder-open-o"></i>
							  <input style="display: none;" multiple  id="REPLACEROWIDdocFile0"  onchange="validateUploadFile('REPLACEROWID','0',this);"  class="upload" type="file">
							  </span></label>
							<input class="form-control input-sm" id="REPLACEROWIDuploadFile0" placeholder="Attachment" type="text">
						  </div>
						  <input type="button" value="" onclick="addAttachmentRow('REPLACEROWID');" class="addRowIcon"/>
					   </div>	
				   </div>
	          </div>
	          <div id="divComentTd2" style="display:none;">   
	                   <input type="text" name="timsheetAttachments[REPLACEROWID].weekComment" disabled class="form-control input-sm" placeholder="Comment"  />
	          </div>
	          
	          
	          <div id="divAttachmentTd0" style="display:none;">
		           <div id="COMMENTROWIDAttchmentDivREPLACEROWIDId" class="addRowBtn">
						   <div class="input-group col-md-12" style="margin:0 auto">
							<label class="input-group-btn"> <span class="btn btn-primary input-sm"><i class="fa fa-folder-open-o"></i>
							  <input style="display: none;" multiple  id="COMMENTROWIDdocFileREPLACEROWID"  onchange="validateUploadFile('COMMENTROWID','REPLACEROWID',this);"  class="upload" type="file">
							  </span></label>
							<input class="form-control input-sm" id="COMMENTROWIDuploadFileREPLACEROWID" placeholder="Attachment" type="text">
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
				     
				     if (billableType !== undefined && billableType !=='####' && billableType !=='' && billableType == 'Non-Billable') {
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
				     
				     if (billableType !== undefined && billableType !=='####' && billableType !=='' && billableType == 'Non-Billable') {
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
function makeActive(local,commentI){ 
	var comment = local.value;
	 $("#"+commentI).removeClass('com_active');
	if(comment != null && comment != 'undefined' && comment != ''){
		 $("#"+commentI).addClass('com_active');
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
function withdrawMyTimesheet(){ 
	/* var frm = $('#timesheet_form').serializeFormJSON(); */
	 var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
	 var projectMemberId = $("#projectMemberId").val();
	 var timesheetFrequency  = $("#currentTimeFrequency").val();
	var dateFormat ='${SourceLeadWeb_orgLocations.dateFormat}';
	  $("#loadTime").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:60% center; background-repeat:no-repeat; background-size:75px;');
	$.ajax({
	      type : "POST",
     	  url: sourceleadUrl+"/withDrawTimesheet?${_csrf.parameterName}=${_csrf.token}&projectMemberId="+projectMemberId+"&timesheetFrequency="+timesheetFrequency+"&dateFormat="+dateFormat,		
	      success :function(result) {
	    	  $("#loadTime").removeAttr("style"); 
	    	  timesheetDivByFrequency();
	    	  $("html").trigger('click');
	    	  maskme();
	      }
   }); 
}
</script>

<script>
  function addAttachmentRow(rowNum){
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
   function addTimeSheetCommentRow() { 
	    var tbl = document.getElementById("attTimesheet");
		var rowNum = $("#maxAttRowNumber").val();
		var maxRowNum = parseInt(rowNum);
		$("#maxAttRowNumber").val(maxRowNum+1); 
		var row = tbl.insertRow(maxRowNum);
		row.setAttribute("id", "timesheetAttRow"+maxRowNum);
		 
		  
		var cell0 = row.insertCell(0);
		var divadcmTd0Str = document.getElementById("divComentTd0").innerHTML;
		divadcmTd0Str = divadcmTd0Str.replace(/REPLACEROWID/g, (maxRowNum));
		divadcmTd0Str = divadcmTd0Str.replace(/EXISTEDATTCLASS/g,'existedAttProjectMembers');
		divadcmTd0Str = divadcmTd0Str.replace(/disabled/g, ''); 
		cell0.innerHTML = divadcmTd0Str;
		
		var cell1 = row.insertCell(1);
		var divadcmTd1Str = document.getElementById("divComentTd1").innerHTML;
		divadcmTd1Str = divadcmTd1Str.replace(/REPLACEROWID/g, (maxRowNum));
		cell1.innerHTML = divadcmTd1Str;
		
		var cell2 = row.insertCell(2);
		var divadcmTd2Str = document.getElementById("divComentTd2").innerHTML;
		divadcmTd2Str = divadcmTd2Str.replace(/REPLACEROWID/g, (maxRowNum));
		divadcmTd2Str = divadcmTd2Str.replace(/disabled/g, ''); 
		cell2.innerHTML = divadcmTd2Str;
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
			
			var cell1 = row.insertCell(1);
			var divadttTd0Str = document.getElementById("divadttTd1").innerHTML;
			divadttTd0Str = divadttTd0Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd0Str = divadttTd0Str.replace(/ENTRYINDEX/g, (entryIndex)); 
			divadttTd0Str = divadttTd0Str.replace(/disabled/g, ''); 
			cell1.innerHTML = divadttTd0Str;
			$("#"+maxRowNum+"task1Id").attr('data-validation','required');
			
			var cell2 = row.insertCell(2);
			var divadttTd1Str = document.getElementById("divadttTd2").innerHTML;
			divadttTd1Str = divadttTd1Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd1Str = divadttTd1Str.replace(/ENTRYINDEX/g, (entryIndex)); 
			divadttTd1Str = divadttTd1Str.replace(/disabled/g, ''); 
			cell2.innerHTML = divadttTd1Str;
			
			var cell3 = row.insertCell(3);
			var divadttTd10Str = document.getElementById("divadttTd10").innerHTML;
			divadttTd10Str = divadttTd10Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd10Str = divadttTd10Str.replace(/ENTRYINDEX/g, (entryIndex)); 
			divadttTd10Str = divadttTd10Str.replace(/disabled/g, ''); 
			cell3.innerHTML = divadttTd10Str;
			
			
		    var cell4 = row.insertCell(4);
			var isCell1Disabled = $("#1timeSheetDay1Id").prop('disabled');
			var divadttTd2Str = document.getElementById("divadttTd3").innerHTML;
			divadttTd2Str = divadttTd2Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd2Str = divadttTd2Str.replace(/ENTRYINDEX/g, (entryIndex)); 
			
			var dateValue = $("[name='timesheetSubmission.timesheetEntries[0].date']").val();
			var dayOfDate =  $("[name='timesheetSubmission.timesheetEntries[0].dayOfTheDate']").val();
			divadttTd2Str = divadttTd2Str.replace(/DATEVALUE/g, (dateValue)); 
			divadttTd2Str = divadttTd2Str.replace(/DAYOFTHEDATE/g, (dayOfDate)); 
			divadttTd2Str = divadttTd2Str.replace(/disabled/g, ''); 
			cell4.innerHTML = divadttTd2Str;
			
			entryIndex = parseInt(entryIndex)+1;
			var cell5 = row.insertCell(5);
			var divadttTd3Str = document.getElementById("divadttTd4").innerHTML;
			divadttTd3Str = divadttTd3Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd3Str = divadttTd3Str.replace(/ENTRYINDEX/g, (entryIndex)); 
			divadttTd3Str = divadttTd3Str.replace(/disabled/g, ''); 
			
		    dateValue = $("[name='timesheetSubmission.timesheetEntries[1].date']").val();
		    dayOfDate =  $("[name='timesheetSubmission.timesheetEntries[1].dayOfTheDate']").val();
			divadttTd3Str = divadttTd3Str.replace(/DATEVALUE/g, (dateValue)); 
			divadttTd3Str = divadttTd3Str.replace(/DAYOFTHEDATE/g, (dayOfDate)); 
			cell5.innerHTML = divadttTd3Str;
			
			entryIndex = parseInt(entryIndex)+1;
			var cell6 = row.insertCell(6);
			var divadttTd4Str = document.getElementById("divadttTd5").innerHTML;
			divadttTd4Str = divadttTd4Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd4Str = divadttTd4Str.replace(/ENTRYINDEX/g, (entryIndex)); 
			divadttTd4Str = divadttTd4Str.replace(/disabled/g, '');
			dateValue = $("[name='timesheetSubmission.timesheetEntries[2].date']").val();
		    dayOfDate =  $("[name='timesheetSubmission.timesheetEntries[2].dayOfTheDate']").val();
			divadttTd4Str = divadttTd4Str.replace(/DATEVALUE/g, (dateValue)); 
			divadttTd4Str = divadttTd4Str.replace(/DAYOFTHEDATE/g, (dayOfDate)); 
			cell6.innerHTML = divadttTd4Str;
			
			entryIndex = parseInt(entryIndex)+1;
			var cell7= row.insertCell(7);
			var divadttTd5Str = document.getElementById("divadttTd6").innerHTML;
			divadttTd5Str = divadttTd5Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd5Str = divadttTd5Str.replace(/ENTRYINDEX/g, (entryIndex));
			divadttTd5Str = divadttTd5Str.replace(/disabled/g, '');
			dateValue = $("[name='timesheetSubmission.timesheetEntries[3].date']").val();
		    dayOfDate =  $("[name='timesheetSubmission.timesheetEntries[3].dayOfTheDate']").val();
			divadttTd5Str = divadttTd5Str.replace(/DATEVALUE/g, (dateValue)); 
			divadttTd5Str = divadttTd5Str.replace(/DAYOFTHEDATE/g, (dayOfDate)); 
			cell7.innerHTML = divadttTd5Str;
			
			entryIndex = parseInt(entryIndex)+1;
			var cell8 = row.insertCell(8);
			var divadttTd6Str = document.getElementById("divadttTd7").innerHTML; 
			divadttTd6Str = divadttTd6Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd6Str = divadttTd6Str.replace(/ENTRYINDEX/g, (entryIndex));
			divadttTd6Str = divadttTd6Str.replace(/disabled/g, '');
			dateValue = $("[name='timesheetSubmission.timesheetEntries[4].date']").val();
		    dayOfDate =  $("[name='timesheetSubmission.timesheetEntries[4].dayOfTheDate']").val();
			divadttTd6Str = divadttTd6Str.replace(/DATEVALUE/g, (dateValue)); 
			divadttTd6Str = divadttTd6Str.replace(/DAYOFTHEDATE/g, (dayOfDate)); 
			cell8.innerHTML = divadttTd6Str;
			
			entryIndex = parseInt(entryIndex)+1;
			var cell9 = row.insertCell(9);
			var divadttTd7Str = document.getElementById("divadttTd8").innerHTML;
			divadttTd7Str = divadttTd7Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd7Str = divadttTd7Str.replace(/ENTRYINDEX/g, (entryIndex)); 
			divadttTd7Str = divadttTd7Str.replace(/disabled/g, '');
			dateValue = $("[name='timesheetSubmission.timesheetEntries[5].date']").val();
		    dayOfDate =  $("[name='timesheetSubmission.timesheetEntries[5].dayOfTheDate']").val();
			divadttTd7Str = divadttTd7Str.replace(/DATEVALUE/g, (dateValue)); 
			divadttTd7Str = divadttTd7Str.replace(/DAYOFTHEDATE/g, (dayOfDate)); 
			cell9.innerHTML = divadttTd7Str;
			
			entryIndex = parseInt(entryIndex)+1;
			var cell10 = row.insertCell(10);
			var divadttTd8Str = document.getElementById("divadttTd9").innerHTML;
			divadttTd8Str = divadttTd8Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd8Str = divadttTd8Str.replace(/ENTRYINDEX/g, (entryIndex)); 
			divadttTd8Str = divadttTd8Str.replace(/disabled/g, '');
			dateValue = $("[name='timesheetSubmission.timesheetEntries[6].date']").val();
		    dayOfDate =  $("[name='timesheetSubmission.timesheetEntries[6].dayOfTheDate']").val();
			divadttTd8Str = divadttTd8Str.replace(/DATEVALUE/g, (dateValue)); 
			divadttTd8Str = divadttTd8Str.replace(/DAYOFTHEDATE/g, (dayOfDate));
			cell10.innerHTML = divadttTd8Str;
			
			entryIndex = parseInt(entryIndex)+1;
			$("#entryIndexValue").val(entryIndex);
			
			var cell11 = row.insertCell(11);
			cell11.className='timeTabFix';
			var divadttTd11Str = document.getElementById("divadttTd11").innerHTML;
			divadttTd11Str = divadttTd11Str.replace(/disabled/g, '');
			cell11.innerHTML = divadttTd11Str;
		    maskme();
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
        	if($("#" + taskNumberId).val() != null && $("#" + taskNumberId).val() != "" && $("#" + taskNumberId).val() !="undefined"){
        		   var end = parseInt(entryIndex)+7;
	          	  for (i = entryIndex; i < end; i++) {
	          		   $("[name='timesheetSubmission.timesheetEntries["+i+"].ticketNumber']").val($("#"+taskNumberId).val());
	              } 
        	}
        }
        
        //flush project tasks
        function flushtasks(projectTaskId){
        	 $("#"+projectTaskId).next('i').remove();
        	 $("#"+projectTaskId).empty();
        	 $("#"+projectTaskId).append("<option value=''>--select task--</option>");
        }
        
       function enableOrDisableHours(entryIndex,pmId,ticketNumberId){
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
            	          			 $("#commentDiv"+i).find('i').attr('class','glyphicon glyphicon-comment');
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
			   $("#"+projectTaskId).attr('class','smfield existedProjectTaskId'+projectMemberId);
			   
			   var avilableProjectTaskIds = [];
			   $('.existedProjectTaskId'+projectMemberId).each(function() {
					 var localId = this.value;
					 if(localId != null &&  localId !='undefined' && localId != ''){
						 var taskIdArray = localId.split(",");
						 avilableProjectTaskIds.push(taskIdArray[0]);
					 }
			   }); 
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
		 			   		   		 result = result + "<option value=''>--select task--</option>";
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
	 		    	   
	 		      }
	 	      	  
	 	       });  
		   }
	   }
</script>
<script >
    function prepareAttachmentProjectMembers(rowId,existedPmId){
		   var existedProjectMembersMap = {};
		   $('.existedProjectMembers').each(function() {
			   var pmId = this.value;
			   <c:forEach var="pm" items="${employeeTimesheetEntryDetailsDTO.allProjectMembersList}">
			       if(pmId != null &&  pmId !='undefined' && pmId != '' && pmId == '${pm.id}'){
			    	   existedProjectMembersMap[pmId] = '${pm.projectName}';
			       }
			   </c:forEach>
		   }); 
		   var avilableAttProjectMembers = [];
		   $('.existedAttProjectMembers').each(function() {
				 var localId = this.value;
				 avilableAttProjectMembers.push(localId);
		   }); 
		   $('.existedAttProjectMembers').each(function() {
  				 var localId = this.id;
  				 var localValue = this.value;
  				 var currentId = $("#"+rowId+"attProjMemberId").attr('id');
  				 var result = ""; 
  				/*  if(currentId == localId){ */
  					 /* $("#"+localId).empty();
	   		   		 result = result + "<option value=''>--select Project--</option>"; */
  					var result = ""; 
		   		    if(localValue != null && localValue != '' && localValue!= 'undefined'){
		   			    $('option', this).not(':eq(0), :selected').remove();
		   		     }else{ 
		   		    	 $("#"+localId).empty();
		   		   		 result = result + "<option value=''>--select project--</option>";
		   		     }
	   		   		 
		   		   	 for(key in existedProjectMembersMap){
			   		      var pmId = key;
			   			  var pmName = existedProjectMembersMap[key];
			   			  if(avilableAttProjectMembers == null  || avilableAttProjectMembers == '' || avilableAttProjectMembers == 'undefined' ||
			   			      (avilableAttProjectMembers != null && avilableAttProjectMembers != '' && avilableAttProjectMembers != 'undefined' && !avilableAttProjectMembers.includes(pmId)))
			   			  {
			   				result = result + "<option value='"+pmId+"' >"+pmName+"</option>";
			   			  }
		   		    }
		    	 	$("#"+localId).append(result);
		    	 	$("#"+localId).next('i').remove();
	   		     /* if(localValue != null && localValue != '' && localValue!= 'undefined'){
	   			    $('option', this).not(':eq(0), :selected').remove();
	   		     }else{ 
	   		    	 $("#"+localId).empty();
	   		   		 result = result + "<option value=''>--select Project--</option>";
	   		     } */
	   		    
  	       }); 
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
		      setTotals(); 
		}
		
</script>	

<!-- this script is for clear timesheet -->
<script>
function clearTimesheet(){ 
		  timesheetDivByFrequency('${timesheetSubmissionDTO.timeSubFrequency}');
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
	  //var selectedFile = $('#'+rowId+'uploadFile'+attNumber).val();
	 /*  if(validate_fileupload(selectedFile)){ */
		 //  var docSize = $('#'+rowId+'docFile'+attNumber)[0].files[0].size;
		   fileUpload(''+rowId+'docFile'+attNumber, "timesheetDocuments", "timesheet_form","timsheetAttachments["+rowId+"].fileInfo","","");
	      // $("#"+id+"docFileuploadFileGroup").find('p.jquery_form_error_message').remove();
	     /*  if(docSize > 2097152){ 
	    	  $('#'+id+'docFileuploadFileGroup').find('p.jquery_form_error_message').remove();
	    	  $('#'+id+'docFileuploadFileGroup').find('i').after("<p class='jquery_form_error_message'>Limit is exceeded than 2MB </span>");
			  $('#'+id+'docFile').attr('record-exist','yes');
			  $('#'+id+'docFile').attr('record-exist-errorMsg','Limit is exceeded than 2MB'); 
	     }else{
	    	 $('input#'+id+'docFile').removeAttr( "record-exist record-exist-errormsg");
   		 	 $('#'+id+'docFileuploadFileGroup').find('p.jquery_form_error_message').remove();
   		 	 fileUpload(""+id+"docFile", "timesheetDocuments", "timesheet_form","timesheetSubmission.fileInfo","","");
	      } */
	 // }
	 /*  else{
		      $('#'+id+'docFileuploadFileGroup').find('p.jquery_form_error_message').remove();
		      $('#'+id+'docFileuploadFileGroup').find('i').after("<p class='jquery_form_error_message'>Invalid Format</span>");
			  $('#'+id+'docFile').attr('record-exist','yes');
			  $('#'+id+'docFile').attr('record-exist-errorMsg','Invalid Format'); 
	  }  */
	
}
</script>
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
<!-- this script is for delete document -->
<script type="text/javascript">
function deleteSubmissionDocument(submissionDocumentId){  
	 var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
    $.ajax({
          type : "POST",
      	  url: sourceleadUrl+"/deleteSubmissionDocument?${_csrf.parameterName}=${_csrf.token}&submissionDocumentId="+submissionDocumentId,		
	      success :function(result) {
	    	  $("#"+submissionDocumentId+"document").remove();
	      }
      	  
    }); 
	   
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
	// checkHours();
	/*  var isTimesheetAttachmentRequired = $("#isTimesheetAttachmentRequired").val();
	 if(isTimesheetAttachmentRequired != null && isTimesheetAttachmentRequired != "" && isTimesheetAttachmentRequired !="undefined" && isTimesheetAttachmentRequired == "Y"){
		 $('#0docFile').removeAttr('data-validation-optional');
		 $('#0docFile').attr("data-validation","required"); 
		 $(".time_btn").removeAttr("disabled");
		 $("#loadTime").removeAttr("style"); 
	 } */
	 /* var unfilledDates = [];
	 $('ul.project-tabs-menu li').each(function() {
		 var attr = $(this).attr('class');
		 if (attr!= null && attr != "" && attr != " " && attr !='undefined' && attr != 'hidden') {}
		 else{
			 var currentInputValue = $(this).prev("input").val();
			 if(currentInputValue != null && currentInputValue !='undefined' && currentInputValue != ''){
				 unfilledDates.push(currentInputValue);
			 }
			
		 }	
     }); */
     checkHours();
     validateTimesheetHours(status);
	 var validationSettings = {
		        errorMessagePosition : 'element',
		       borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd' 
	 }; 
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
<!-- if(result != "" && result != null && result != 'undefined'){
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
	}  -->
<script>
function validateTimesheetHours(status){
	 if(status != null && status != 'undefined' && status != '' && status == 'Submitted'){
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
		 if(from != null && from != '' && from != 'undefined' && from == 'dashboard'){
			 $(".time_appr_side_wrap").removeClass("time_appr_side_wrap_active");
	    	 $(".side_mask").fadeOut(200); 
     		 $("body").css('overflow-y', 'scroll');
			 var currentPageNumber =  $("#currentPageNumber").val();
   		     getStatusListData(""+currentPageNumber+"","");
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
		    		  $("#currentTimeSubFrequency").val(currentTimeSubFrequency)
            	      $("#timeSubFrequency").val(currentTimeSubFrequency)
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
<script type="text/javascript">
$(".content-wraper").click(function() {
	bodyClick();
});
$('.selectpicker').selectpicker('refresh');
</script>
<!-- Tab Switching -->
<script type="text/javascript"	src='/resources/js/slead-common.js?staticTime=${staticTime}'></script>

