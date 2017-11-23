<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script src="resources/js/jquery.maskedinput-1.3.min.js"></script>
<div class="time_pop" id="timeSheetErrorMsg"></div>
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
					<c:if 	test="${timesheetSubmissionDTO.modifyButtonVisible}">
					    <b class="proj_withdraw"  onClick='withdrawMyTimesheet();'>MODIFY</b>
					</c:if>
				</c:if>
				
</div>
<div class="clearfix"></div>
<div class="timesheetNewTabs">
	<div class="project_side_wrap_inn_tabs">
		<ul class="project-tabs-menu">
		   <c:forEach var="tsf" items="${timesheetEntryDetailsDTO.timeSubFrequencies}">
			   <li class="${tsf.key ==  timesheetSubmissionDTO.timeSubFrequency ? 'current' : ''} ${fn:contains(timesheetEntryDetailsDTO.filledTimeSubFrequencies, tsf.key) ? 'subColor' : ''}">
					<a href="javascript:timesheetDivByFrequency('${tsf.key}');" >${tsf.value}</a>
				</li>
		   </c:forEach>
			<div class="export dropdown">
				<p class="dropdown-toggle" data-toggle="dropdown">
					<i class="glyphicon glyphicon-export"></i>Export<i
						class="fa fa-caret-down"></i>
				</p>
				<ul id="exporrtEntries" class="dropdown-menu dropdown-menu-right">
					<li onclick='timesheetEntriesExport("pdf");'>
						<i class="fa fa-file-pdf-o text-danger"></i>&nbsp;PDF
					</li>
					<li onclick='timesheetEntriesExport("excel");'>
						<i class="fa fa-file-excel-o text-primary"></i>&nbsp;Excel</li>
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
						   <input type='hidden' name='maxRowNumber'  class='initiateTimesheetMax' id='maxRowNumber'  value='${timesheetSubmissionDTO.maxRowCount > 0 ? timesheetSubmissionDTO.maxRowCount : 1}' />
							<table id="timesheet" cellspacing="0" cellpadding="0">
								<tbody>
									<tr>
										<th>Tasks</th>
										<th>Type</th>
										<c:forEach var="weekDays" items="${timesheetSubmissionDTO.weekDays}">
										   <th>${weekDays.dayOfTheDate}<i>${weekDays.date}</i></th>
										</c:forEach>
									</tr>
									<c:forEach var="entry" items="${entriesMap}" varStatus="entryCount">
									    <tr id="timesheetRow${entryCount.index+1}">
									      <td>
											<div>
											  <c:set var="taskType" value=""/>
											  <c:if test="${timesheetSubmissionDTO.submitButtonVisible ||  timesheetSubmissionDTO.saveButtonVisible}">
												   <select id="${entryCount.index+1}task1Id" name="${entryCount.index+1}task1Id" class="existedProjectTaskId${timesheetEntryDetailsDTO.projectId}" onchange='getProjectTasks("${entryCount.index+1}task1Id","${timesheetEntryDetailsDTO.projectId}")' data-validation="required" field-name="Task">
														<option value=''>--select task--</option>
														 <c:if test="${!empty timesheetSubmissionDTO.timesheetTaskList}">
														   <c:forEach var="pt" items="${timesheetSubmissionDTO.timesheetTaskList}">
														       <c:if test="${pt.id == entry.key}">
														         <c:set var="taskType" value="${pt.taskType}" />
														         <option value='${pt.id}' selected>${pt.taskName}</option>
														       </c:if>
														       <c:if test="${pt.id != entry.key}">
														          <option value='${pt.id},${pt.taskType}'>${pt.taskName}</option>
														       </c:if>
														   </c:forEach>
														</c:if>
													</select>
													 <c:if test="${entryCount.index == 0}">
													    <input value="" onclick="addTimeSheetRow('${timesheetEntryDetailsDTO.projectId}')" class="addtaskfield" type="button">
													 </c:if>
													 <c:if test="${entryCount.index > 0}">
													    <input value="" onclick="removeTimeSheetRow('${entryCount.index+1}');" class="addtaskfield removetaskfield" type="button">
													 </c:if>
											  </c:if>
											   <c:if test="${!timesheetSubmissionDTO.submitButtonVisible}">
											          <c:forEach var="pt" items="${timesheetSubmissionDTO.timesheetTaskList}">
														       <c:if test="${pt.id == entry.key}">
														          <c:set var="taskType" value="${pt.taskType}" />
														          <p>${pt.taskName}</p>
														       </c:if>
												       </c:forEach>
											   </c:if>
											</div>
									      </td>
									      <td>
									        <div>
									           <span id="${entryCount.index+1}task1IdBillableTypeId"><c:if test="${!empty taskType}">${taskType}</c:if></span>
									        </div>
									      </td>
									       <c:forEach var="te" items="${entry.value}">
									          <c:if test="${te.dayOfTheDate == 'Mon'}">
										          <td> 
										           <div>
										               <input onblur="editMe(this);"  ${te.editable ? '' : 'disabled="disabled"'} id="${entryCount.index+1}timeSheetDay1Id" name="${entryCount.index+1}timeSheetDay1Id" class="inline_field" value="${te.hours}" placeholder="HH:MM" type="text">
										               <div class='timesheet_comm'>
										                   <c:if test="${!empty te.comments || (empty te.comments && te.editable)}">
										                       <i class='glyphicon glyphicon-comment ${!empty te.comments ? "com_active":""}' onclick='timesheetComment("${entryCount.index+1}comment1Id");'></i>
										                       <span class='teskcomment' id='${entryCount.index+1}comment1Id'>
										                             <textarea ${te.editable ? '' : 'disabled="disabled"'}  name='${entryCount.index+1}comment1Id' >${te.comments}</textarea>
                                                               </span>
										                   </c:if>
										                   <c:if test="${empty te.comments && !te.editable}">
										                      <i class='blankDiv'></i>
										                   </c:if>
										               </div>
										            </div>
										           </td>
									          </c:if>
									          <c:if test="${te.dayOfTheDate == 'Tue'}">
									              <td> 
									               <div>
									                 <input onblur="editMe(this);" ${te.editable ? '' : 'disabled="disabled"'} id="${entryCount.index+1}timeSheetDay2Id" name="${entryCount.index+1}timeSheetDay2Id" class="inline_field" value="${te.hours}" placeholder="HH:MM" type="text">
									                  <div class='timesheet_comm'>
										                   <c:if test="${!empty te.comments || (empty te.comments && te.editable)}">
										                       <i class='glyphicon glyphicon-comment ${!empty te.comments ? "com_active":""}' onclick='timesheetComment("${entryCount.index+1}comment2Id");'></i>
										                       <span class='teskcomment' id='${entryCount.index+1}comment2Id'>
										                             <textarea ${te.editable ? '' : 'disabled="disabled"'}  name='${entryCount.index+1}comment2Id' >${te.comments}</textarea>
                                                               </span>
										                   </c:if>
										                    <c:if test="${empty te.comments && !te.editable}">
										                      <i class='blankDiv'></i>
										                   </c:if>
										               </div>
									                 </div>
									               </td>
									          </c:if>
									          <c:if test="${te.dayOfTheDate == 'Wed'}">
									             <td> 
									                <div>
									                   <input onblur="editMe(this);" ${te.editable ? '' : 'disabled="disabled"'} id="${entryCount.index+1}timeSheetDay3Id" name="${entryCount.index+1}timeSheetDay3Id" class="inline_field" value="${te.hours}" placeholder="HH:MM" type="text">
									                   <div class='timesheet_comm'>
										                   <c:if test="${!empty te.comments || (empty te.comments && te.editable)}">
										                       <i class='glyphicon glyphicon-comment ${!empty te.comments ? "com_active":""}' onclick='timesheetComment("${entryCount.index+1}comment3Id");'></i>
										                       <span class='teskcomment' id='${entryCount.index+1}comment3Id'>
										                             <textarea ${te.editable ? '' : 'disabled="disabled"'}  name='${entryCount.index+1}comment3Id' >${te.comments}</textarea>
                                                               </span>
										                   </c:if>
										                    <c:if test="${empty te.comments && !te.editable}">
										                      <i class='blankDiv'></i>
										                   </c:if>
										               </div>
									               </div>
									            </td>
									          </c:if>
									          <c:if test="${te.dayOfTheDate == 'Thu'}">
									               <td> 
									                <div>
									                   <input onblur="editMe(this);" ${te.editable ? '' : 'disabled="disabled"'} id="${entryCount.index+1}timeSheetDay4Id" name="${entryCount.index+1}timeSheetDay4Id" class="inline_field" value="${te.hours}" placeholder="HH:MM" type="text">
									                    <div class='timesheet_comm'>
										                   <c:if test="${!empty te.comments || (empty te.comments && te.editable)}">
										                       <i class='glyphicon glyphicon-comment ${!empty te.comments ? "com_active":""}' onclick='timesheetComment("${entryCount.index+1}comment4Id");'></i>
										                       <span class='teskcomment' id='${entryCount.index+1}comment4Id'>
										                             <textarea ${te.editable ? '' : 'disabled="disabled"'}  name='${entryCount.index+1}comment4Id' >${te.comments}</textarea>
                                                               </span>
										                   </c:if>
										                    <c:if test="${empty te.comments && !te.editable}">
										                      <i class='blankDiv'></i>
										                   </c:if>
										               </div>
									                 </div>
									             </td>
									          </c:if>
									          <c:if test="${te.dayOfTheDate == 'Fri'}">
									              <td> 
									                <div>
									                   <input onblur="editMe(this);" ${te.editable ? '' : 'disabled="disabled"'} id="${entryCount.index+1}timeSheetDay5Id" name="${entryCount.index+1}timeSheetDay5Id" class="inline_field" value="${te.hours}" placeholder="HH:MM" type="text">
									                    <div class='timesheet_comm'>
										                   <c:if test="${!empty te.comments || (empty te.comments && te.editable)}">
										                       <i class='glyphicon glyphicon-comment ${!empty te.comments ? "com_active":""}' onclick='timesheetComment("${entryCount.index+1}comment5Id");'></i>
										                       <span class='teskcomment' id='${entryCount.index+1}comment5Id'>
										                             <textarea ${te.editable ? '' : 'disabled="disabled"'}  name='${entryCount.index+1}comment5Id' >${te.comments}</textarea>
                                                               </span>
										                   </c:if>
										                    <c:if test="${empty te.comments && !te.editable}">
										                      <i class='blankDiv'></i>
										                   </c:if>
										               </div>
									                </div>
									             </td>
									          </c:if>
									          <c:if test="${te.dayOfTheDate == 'Sat'}">
									              <td> 
									                 <div>
									                    <input onblur="editMe(this);" ${te.editable ? '' : 'disabled="disabled"'} id="${entryCount.index+1}timeSheetDay6Id" name="${entryCount.index+1}timeSheetDay6Id" class="inline_field" value="${te.hours}" placeholder="HH:MM" type="text">
									                     <div class='timesheet_comm'>
										                   <c:if test="${!empty te.comments || (empty te.comments && te.editable)}">
										                       <i class='glyphicon glyphicon-comment ${!empty te.comments ? "com_active":""}' onclick='timesheetComment("${entryCount.index+1}comment6Id");'></i>
										                       <span class='teskcomment' id='${entryCount.index+1}comment6Id'>
										                             <textarea ${te.editable ? '' : 'disabled="disabled"'}  name='${entryCount.index+1}comment6Id' >${te.comments}</textarea>
                                                               </span>
										                   </c:if>
										                    <c:if test="${empty te.comments && !te.editable}">
										                      <i class='blankDiv'></i>
										                   </c:if>
										               </div>
									                </div>
									            </td>
									          </c:if>
									          <c:if test="${te.dayOfTheDate == 'Sun'}">
									               <td> 
									                 <div>
									                   <input onblur="editMe(this);" ${te.editable ? '' : 'disabled="disabled"'} id="${entryCount.index+1}timeSheetDay7Id" name="${entryCount.index+1}timeSheetDay7Id" class="inline_field" value="${te.hours}" placeholder="HH:MM" type="text">
									                    <div class='timesheet_comm'>
										                   <c:if test="${!empty te.comments || (empty te.comments && te.editable)}">
										                       <i class='glyphicon glyphicon-comment ${!empty te.comments ? "com_active":""}' onclick='timesheetComment("${entryCount.index+1}comment7Id");'></i>
										                       <span class='teskcomment' id='${entryCount.index+1}comment7Id'>
										                             <textarea ${te.editable ? '' : 'disabled="disabled"'}  name='${entryCount.index+1}comment7Id' >${te.comments}</textarea>
                                                               </span>
										                   </c:if>
										                    <c:if test="${empty te.comments && !te.editable}">
										                      <i class='blankDiv'></i>
										                   </c:if>
										               </div>
									                 </div>
									              </td>
									          </c:if> 
									       </c:forEach>
									    </tr>
									</c:forEach>
								    <tr class="last_child">
										<td class="total_field">Total</td>
										<td></td>
										<td>
											<div class="alignDiv">
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
							 <div class="timesheet_bot">
								<div class="timesheet_week_comm">
									<input name="attcahmentMaxRowNumber" id="attcahmentMaxRowNumber" value="1" type="hidden">
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
										     <input name="isTimesheetAttachmentRequired" id="isTimesheetAttachmentRequired" value="N" type="hidden">
										   </c:if>
										   <c:forEach var="finfo" items="${timesheetSubmissionDTO.fileInfo}">
										         <strong id='${finfo.fileDbId}document'>
												     <a class='downicon' href='${finfo.fileURL}' download>
												        <i class='glyphicon glyphicon-save'></i>
												        <input type='button' class='padIcon' value='${finfo.fileName}'>
												      </a>
												       <c:if test="${timesheetSubmissionDTO.submitButtonVisible}">
												          <input class='dele' type='button' onClick='deleteSubmissionDocument("${finfo.fileDbId}");' >
												       </c:if>
												       <input class='deleApp' type='button' onClick="viewFile('${finfo.fileURL}','${finfo.fileName}','${finfo.contentType}');">
												 </strong>
										   </c:forEach>
									</div>
									  <c:if test="${timesheetSubmissionDTO.weekCommentRequired}">
										<div class="commentTime pull-right">
											<textarea name="weekComment" ${timesheetSubmissionDTO.submitButtonVisible ? '' : 'disabled="disabled"'} id="weekComment" placeholder="comment here">${timesheetSubmissionDTO.weekComment}</textarea>
										</div>
									   </c:if>
								</div>
							</div> 
						</div>
						<div class="timesheet_bot">
							<div class="timesheet_button">
							   <div class="totalWrap pull-left">
									<ul>
										<li><span>Billable Hours</span><input
											name="grandBillableTotal" id="grandBillableTotal"
											value="${timesheetSubmissionDTO.billableHours}" placeholder="HH/HH" readonly="" type="text"></li>
										<li><span>Non-Billable Hours</span><input
											name="grandNonBillableTotal" id="grandNonBillableTotal"
											value="${timesheetSubmissionDTO.nonBillableHours}" placeholder="HH/HH" readonly="" type="text"></li>
										<li><span>Total Hours</span><input id="grandTotal"
											value="${timesheetSubmissionDTO.totalHours}" placeholder="HH/HH" readonly="" type="text"></li>
										<div class="clearfix"></div>
									</ul>
								</div>
								 <c:if test="${timesheetSubmissionDTO.saveButtonVisible}">
								    <input onclick="newSaveTimesheet('Draft','user');;" value="Save" class="time_btn time_dra" type="button">
								 </c:if>
								 <c:if test="${timesheetSubmissionDTO.submitButtonVisible}">
								     <input onclick="newSaveTimesheet('Submitted','user');" value="Submit" class="time_btn" type="button">
							    </c:if>
							     <c:if test="${timesheetSubmissionDTO.cancelButtonVisible}">
							       <input type='button' value='Clear' onClick='clearTimesheet();' class='time_btn time_cancel' />
							     </c:if>
							   
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


<!-- ####  this div is for dummy timesheets entries ( useful for add rows )   below   ####-->
				         <div id="divadttTd1" style="display:none;" >   
				             <div> 
				              <select id="REPLACEROWIDtask1Id" name="REPLACEROWIDtask1Id" class="existedProjectTaskIdprojectId"  field-name="Task"  onclick="getProjectTasks('REPLACEROWIDtask1Id','projectId')">  
				              <option value=''>--select task--</option>
				              </select>
				             <input type="button" value="" onClick="removeTimeSheetRow('REPLACEROWID')" class="addtaskfield removetaskfield"/>
				             </div>
				        </div>
				        
				        <div id="divadttTd2" style="display:none;">
					    	   <span id="REPLACEROWIDtask1IdBillableTypeId" name="REPLACEROWIDtask1IdBillableTypeId" ></span>	
				   		</div>
				        
				        
				        <div id="divadttTd3" style="display:none;">
					    	   <input type="text" onblur='editMe(this);'   id="REPLACEROWIDtimeSheetDay1Id" name="REPLACEROWIDtimeSheetDay1Id" class="inline_field" placeholder="HH:MM" />	
				               <div class="timesheet_comm">
				                <i class="glyphicon glyphicon-comment" onclick="timesheetComment('REPLACEROWIDcomment1Id');"></i>
				                <span class="teskcomment" id="REPLACEROWIDcomment1Id" ><textarea name="REPLACEROWIDcomment1Id" id="REPLACEROWIDtextArea1Id" ></textarea></span>
				               </div>
				   		</div>
				        <div id="divadttTd4" style="display:none;">
								  <input type="text" onblur='editMe(this);'   id="REPLACEROWIDtimeSheetDay2Id" name="REPLACEROWIDtimeSheetDay2Id" class="inline_field" placeholder="HH:MM" />	
				                  <div class="timesheet_comm">
				                      <i class="glyphicon glyphicon-comment" onclick="timesheetComment('REPLACEROWIDcomment2Id');"></i>
				                  <span class="teskcomment" id="REPLACEROWIDcomment2Id" ><textarea name="REPLACEROWIDcomment2Id" id="REPLACEROWIDtextArea2Id" ></textarea></span></div>
				        </div>
				        <div id="divadttTd5" style="display:none;">
								  <input type="text" onblur='editMe(this);'    id="REPLACEROWIDtimeSheetDay3Id" name="REPLACEROWIDtimeSheetDay3Id" class="inline_field" placeholder="HH:MM" />	
				                  <div class="timesheet_comm">
				                  <i class="glyphicon glyphicon-comment" onclick="timesheetComment('REPLACEROWIDcomment3Id');"></i>
				                  <span class="teskcomment" id="REPLACEROWIDcomment3Id" ><textarea name="REPLACEROWIDcomment3Id" id="REPLACEROWIDtextArea3Id"></textarea></span></div>
				        </div>
				        <div id="divadttTd6" style="display:none;">
								  <input type="text" onblur='editMe(this);'    id="REPLACEROWIDtimeSheetDay4Id" name="REPLACEROWIDtimeSheetDay4Id" class="inline_field" placeholder="HH:MM" />	
				                  <div class="timesheet_comm">
				                  <i class="glyphicon glyphicon-comment" onclick="timesheetComment('REPLACEROWIDcomment4Id');"></i>
				                  <span class="teskcomment" id="REPLACEROWIDcomment4Id" ><textarea name="REPLACEROWIDcomment4Id" id="REPLACEROWIDtextArea4Id"></textarea></span></div>           
				        </div>
				        <div id="divadttTd7" style="display:none;">
								  <input type="text" onblur='editMe(this);'    id="REPLACEROWIDtimeSheetDay5Id" name="REPLACEROWIDtimeSheetDay5Id" class="inline_field" placeholder="HH:MM" />	
				                  <div class="timesheet_comm">
				                  <i class="glyphicon glyphicon-comment" onclick="timesheetComment('REPLACEROWIDcomment5Id');"></i>
				                  <span class="teskcomment" id="REPLACEROWIDcomment5Id" ><textarea name="REPLACEROWIDcomment5Id" id="REPLACEROWIDtextArea5Id"></textarea></span></div>  
				    	 </div>
				         <div id="divadttTd8" style="display:none;">
							  <input type="text" onblur='editMe(this);' id="REPLACEROWIDtimeSheetDay6Id" name="REPLACEROWIDtimeSheetDay6Id" class="inline_field" placeholder="HH:MM" />	
				                 <div class="timesheet_comm">
				                 <i class="glyphicon glyphicon-comment" onclick="timesheetComment('REPLACEROWIDcomment6Id');"></i>
				                 <span class="teskcomment" id="REPLACEROWIDcomment6Id" ><textarea name="REPLACEROWIDcomment6Id" id="REPLACEROWIDtextArea6Id"></textarea></span></div>
				         </div>
				         <div id="divadttTd9" style="display:none;">
							  <input type="text" onblur='editMe(this);' id="REPLACEROWIDtimeSheetDay7Id" name="REPLACEROWIDtimeSheetDay7Id" class="inline_field" placeholder="HH:MM" />	
				                 <div class="timesheet_comm">
				                 <i class="glyphicon glyphicon-comment" onclick="timesheetComment('REPLACEROWIDcomment7Id');"></i>
				                 <span class="teskcomment" id="REPLACEROWIDcomment7Id" ><textarea name="REPLACEROWIDcomment7Id" id="REPLACEROWIDtextArea7Id"></textarea></span></div>            
				    	 </div>
						<!-- #### this div is for dummy timesheets entries ( useful for add rows ) above  ####-->  
						
						<div id="divAttachmentTd1" style="display: none; float: left;">
								<div id='REPLACEROWIDdocFileuploadFileGroup'>
						  			<input type='file' name='REPLACEROWIDdocFile' id='REPLACEROWIDdocFile' class="timesheetAttachmentValidation" onchange='validateUploadFile(REPLACEROWID)'> 
						  			<i class="fa fa-times" onclick="removeAttachmentRow(REPLACEROWID);"></i>
						  		</div>
					    </div>



<script>
/* this script is for calculate total hours */
$(document).ready(function() {
	setTotals();
	maskme();
});
		
function setTotals(){ 
	 var total;
	 var billableTotal;
	 var billableSum = 0;
	 var nonBillableTotal;
	 var nonBillableSum = 0;
	 for (var i = 1; i < 8; i++){
		 var bsum = 0;
		 var nbsum = 0;
		 var total = 0;
		 for (var j = 1; j < 50; j++){
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
	    
	    
	  //for calculate non-billable count
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
	maskme();    
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
<script type="text/javascript">
function withdrawMyTimesheet(){ 
	/* var frm = $('#timesheet_form').serializeFormJSON(); */
	 var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
	 var projectMemberId = $("#projectMemberId").val();
	 var timesheetFrequency  = $("#timesheetFrequency").val();
	var dateFormat ='${SourceLeadWeb_orgLocations.dateFormat}';
	  $("#loadTime").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:60% center; background-repeat:no-repeat; background-size:75px;');
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


<!-- this script is for add row functionality at Timesheet screen -->
<script>
var validationSettings = {
        errorMessagePosition : 'element',
       borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd' 
}; 
function addTimeSheetRow(projectId) {
	 $('#0docFile').removeAttr('data-validation');
	 $('#0docFile').attr("data-validation-optional","true"); 
	 /* $('#0docFile').removeClass('error'); */
	/*  $('#0docFileuploadFileGroup').find('p.jquery_form_error_message').remove(); */ 
	 if ($('#timesheet_form').validate(false, validationSettings)&&!$("#timeSheetErrorMsg").is(':visible')){   
		    var tbl = document.getElementById("timesheet");
			var rowNum = $("#maxRowNumber").val();
			var maxRowNum = parseInt(rowNum);
			maxRowNum = maxRowNum +1;
			$("#maxRowNumber").val(maxRowNum); 
			var row = tbl.insertRow(maxRowNum);
			row.setAttribute("id", "timesheetRow"+maxRowNum);
			
			  
		    var previousMaxRowNum =parseInt(rowNum-1);
			var cell0 = row.insertCell(0);
			var divadttTd0Str = document.getElementById("divadttTd1").innerHTML;
			divadttTd0Str = divadttTd0Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd0Str = divadttTd0Str.replace(/projectId/g, (projectId)); 
			/* cell0.setAttribute("class", "pname");*/
			cell0.innerHTML = divadttTd0Str;
			$("#"+maxRowNum+"task1Id").attr('data-validation','required');
			
			
			var cell1 = row.insertCell(1);
			var divadttTd1Str = document.getElementById("divadttTd2").innerHTML;
			divadttTd1Str = divadttTd1Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd1Str = divadttTd1Str.replace(/projectId/g, (projectId)); 
			cell1.innerHTML = divadttTd1Str;
			
			
		    var cell2 = row.insertCell(2);
			var isCell1Disabled = $("#1timeSheetDay1Id").prop('disabled');
			if(isCell1Disabled){
				$("#divadttTd3 :input").attr('disabled','disabled');
				$('.timesheet_comm','#divadttTd3').find('i').attr('class','glyphicon');
			}
			else{
				$("#divadttTd3 :input").removeAttr('disabled');
				$('.timesheet_comm','#divadttTd3').find('i').attr('class','glyphicon glyphicon-comment');
			} 
			var divadttTd2Str = document.getElementById("divadttTd3").innerHTML;
			divadttTd2Str = divadttTd2Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd2Str = divadttTd2Str.replace(/projectId/g, (projectId));
			cell2.innerHTML = divadttTd2Str;
			
			
			var cell3 = row.insertCell(3);
			var isCell2Disabled = $("#1timeSheetDay2Id").prop('disabled');
			if(isCell2Disabled){
				$("#divadttTd4 :input").attr('disabled','disabled');
				$('.timesheet_comm','#divadttTd4').find('i').attr('class','glyphicon');
			}
			else{
				$("#divadttTd4 :input").removeAttr('disabled');
				$('.timesheet_comm','#divadttTd4').find('i').attr('class','glyphicon glyphicon-comment');
			} 
			var divadttTd3Str = document.getElementById("divadttTd4").innerHTML;
			divadttTd3Str = divadttTd3Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd2Str = divadttTd3Str.replace(/projectId/g, (projectId));
			cell3.innerHTML = divadttTd3Str;
			
			
			var cell4 = row.insertCell(4);
			var isCell3Disabled = $("#1timeSheetDay3Id").prop('disabled');
			if(isCell3Disabled){
				$("#divadttTd5 :input").attr('disabled','disabled');
				$('.timesheet_comm','#divadttTd5').find('i').attr('class','glyphicon');
			}
			else{
				$("#divadttTd5 :input").removeAttr('disabled');
				$('.timesheet_comm','#divadttTd5').find('i').attr('class','glyphicon glyphicon-comment');
			} 
			var divadttTd4Str = document.getElementById("divadttTd5").innerHTML;
			divadttTd4Str = divadttTd4Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd4Str = divadttTd4Str.replace(/projectId/g, (projectId));
			cell4.innerHTML = divadttTd4Str;
			
			
			var cell5= row.insertCell(5);
			 var isCell4Disabled = $("#1timeSheetDay4Id").prop('disabled');
			if(isCell4Disabled){
				$("#divadttTd6 :input").attr("disabled", "disabled");
				$('.timesheet_comm','#divadttTd6').find('i').attr('class','glyphicon');
			}else{
				$("#divadttTd6 :input").removeAttr('disabled');
				$('.timesheet_comm','#divadttTd6').find('i').attr('class','glyphicon glyphicon-comment');
			} 
			var divadttTd5Str = document.getElementById("divadttTd6").innerHTML;
			divadttTd5Str = divadttTd5Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd5Str = divadttTd5Str.replace(/projectId/g, (projectId)); 
			cell5.innerHTML = divadttTd5Str;
			
			var cell6 = row.insertCell(6);
			var isCell5Disabled = $("#1timeSheetDay5Id").prop('disabled'); 
			 if(isCell5Disabled){
				$("#divadttTd7 :input").attr("disabled", "disabled");
				$('.timesheet_comm','#divadttTd7').find('i').attr('class','glyphicon');
			}
			else{
				$("#divadttTd7 :input").removeAttr('disabled');
				$('.timesheet_comm','#divadttTd7').find('i').attr('class','glyphicon glyphicon-comment');
			}
			var divadttTd6Str = document.getElementById("divadttTd7").innerHTML; 
			divadttTd6Str = divadttTd6Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd6Str = divadttTd6Str.replace(/projectId/g, (projectId)); 
			cell6.innerHTML = divadttTd6Str;
			
			var cell7 = row.insertCell(7);
			var isCell6Disabled = $("#1timeSheetDay6Id").prop('disabled');
			if(isCell6Disabled){
				$("#divadttTd8 :input").attr("disabled", "disabled");
				$('.timesheet_comm','#divadttTd8').find('i').attr('class','glyphicon');
			}
			else{
				$("#divadttTd8 :input").removeAttr('disabled');
				$('.timesheet_comm','#divadttTd8').find('i').attr('class','glyphicon glyphicon-comment');
			} 
			var divadttTd7Str = document.getElementById("divadttTd8").innerHTML;
			divadttTd7Str = divadttTd7Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd7Str = divadttTd7Str.replace(/projectId/g, (projectId));
			cell7.innerHTML = divadttTd7Str;
			
			var cell8 = row.insertCell(8);
		    var isCell7Disabled = $("#1timeSheetDay7Id").prop('disabled');
			if(isCell7Disabled){
				$("#divadttTd9 :input").attr("disabled", "disabled");
				$('.timesheet_comm','#divadttTd9').find('i').attr('class','glyphicon');
			}else{
				$("#divadttTd9 :input").removeAttr('disabled');
				$('.timesheet_comm','#divadttTd9').find('i').attr('class','glyphicon glyphicon-comment');
			} 
			var divadttTd8Str = document.getElementById("divadttTd9").innerHTML;
			divadttTd8Str = divadttTd8Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd8Str = divadttTd8Str.replace(/projectId/g, (projectId));
			cell8.innerHTML = divadttTd8Str;
			
		    maskme();
		    
			//var projectTaskId = maxRowNum+"task1Id"; 
			
			//getProjectTasks(projectTaskId,projectId); 
			
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
       
	   //get project tasks 
	   function getProjectTasks(projectTaskId,projectId){
		   var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
		   if($("#" + projectTaskId).val() != null && $("#" + projectTaskId).val() != "" && $("#" + projectTaskId).val() !="undefined"){
			   var taskId = $("#" + projectTaskId).val().split(",");
			   $("#" + projectTaskId + "BillableTypeId").html(taskId[1]);
		   }else{
			   $("#" + projectTaskId + "BillableTypeId").html(""); 
		   }
		   
		   var avilableProjectTaskIds = [];
		   $('.existedProjectTaskId'+projectId).each(function() {
				 var localId = this.value;
				 var taskIdArray = localId.split(",");
				 avilableProjectTaskIds.push(taskIdArray[0]);
		   }); 
		   $.ajax({
   	          type : "POST",
 	      	  url: sourceleadUrl+"/projectTasksById?${_csrf.parameterName}=${_csrf.token}&projectId="+projectId+"&avilableProjectTaskIds="+avilableProjectTaskIds,		
 		      success :function(data) {
 		    	    var json = JSON.parse(data);
 		    	    $('.existedProjectTaskId'+projectId).each(function() {
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
	  			        		result = result + "<option value='" +opt.Id+","+opt.billableType+"'>"+opt.taskName+"</option>";
	  						}
 				    	 	$("#"+localId).append(result);
 			   	   }); 
 		    	   
 		      }
 	      	  
 	       }); 
		   
	   }
</script>

<!-- this script is for remove timesheet -->
<script type="text/javascript">	

function removeTimeSheetRow(rowId) { 
	
			var rowNum = $("#maxRowNumber").val();
			var maxRowNum = parseInt(rowNum);
			/* $("#maxRowNumber"+submissionId).val(maxRowNum - 1);  */
			
			 document.getElementById("timesheetRow"+rowId).style.display = 'none';
			 document.getElementById("timesheetRow"+rowId).value= '';
			 var  rId = "timesheetRow"+rowId;
			  var r = document.getElementById("timesheet").rows[rId];
	          var count = 2*(r.cells.length);
			    for(i=0;i<=count;i++){
			        r.deleteCell(0);
			    } 
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
	 }
	 if(isNaN(hours1) && !isNaN(hours2) && isNaN(min1) && isNaN(min2)){
		 $("#"+id).val("0"+hours2+":00");
	 }
	 else if(isNaN(hours)  && !isNaN(min1) && isNaN(min2)){
		 $("#"+id).val("00:"+min1+"0");
	 }
	 else if(isNaN(hours)  && isNaN(min1) && !isNaN(min2)){
		 $("#"+id).val("00:0"+min2);
	 }
	 else if(!isNaN(hours)  && isNaN(min1) && isNaN(min2)){
		 $("#"+id).val(hours+":00");
	 }
	 else if(!isNaN(hours)&& !isNaN(min1) && isNaN(min2)){
		 $("#"+id).val(hours+":"+"0"+min1);
	 }
	 else if(!isNaN(hours)&& isNaN(min1) && !isNaN(min2)){
		 $("#"+id).val(hours+":"+"0"+min2);
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
	 }
	 setTotals();
	 checkHours();
}

function checkHours(){ 
	 var result = "";		
	 var total; 
	 for (var i = 1; i < 8; i++){
		 var bsum = 0;
		 var nbsum = 0;
		 var total = 0;
		 for (var j = 1; j < 50; j++){  
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
	    		result = result+"Monday ,";
	    	}
	    	if(i == '2'){  
	    		result = result+"Tuesday ,";
	    	}
	    	if(i == '3'){  
	    		result = result+"Wednesday ,";
	    	}
	    	if(i == '4'){  
	    		result = result+"Thursday ,";
	    	}
	    	if(i == '5'){
	    		result = result+"Friday ,";
	    	}
	    	if(i == '6'){
	    		result = result+"Saturday ,";
	    	}
	    	if(i == '7'){
	    		result = result+"Sunday ,";
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
   		 	 fileUpload(""+id+"docFile", "timesheetDocuments", "timesheet_form","","","");
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
<script type="text/javascript">
   function alertMessage(message,status,savingType){
   $("#timesheetEntriesDiv").append("<div id='choicepopUp'></div>");
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
    var projectExistingValue = $("#dbIsTimesheetAttachmentRequired").val();
    
    var strongTagCount = document.getElementById('timesheetDocumentsId').getElementsByTagName('strong').length;
    /*  var startdate; */
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
	var currentTimeFrequency = $("#timesheetFrequency").val();
	var dateFrequency = currentTimeFrequency.split('To');
	var projectMemberId = $("#projectMemberId").val();
	var startDate = dateFrequency[0];
	var endDate = dateFrequency[1];
	 var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
	var status = '${timesheetSubmissionDTO.timesheetStatus}';
	if(status != null && status != 'undefined' && status != '' && status != 'NEW' && status != 'new'){
		window.location.href = sourceleadUrl+"/exportTimeSheet?${_csrf.parameterName}=${_csrf.token}&projectMemberId="+projectMemberId+"&startDate="+startDate+"&endDate="+endDate+"&exportType="+exportType;
	}else{
		alert("Please fill the TimeSheets..");
	}
}
</script>
<script type="text/javascript"	src='resources/js/slead-common.js?staticTime=${staticTime}'></script>
