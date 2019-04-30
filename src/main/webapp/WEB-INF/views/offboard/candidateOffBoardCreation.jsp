<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<link rel="stylesheet" href="/resources/css/style.css" />

<script src="/resources/js/offboardCheckListBuilder.js" type="text/javascript" ></script> <!-- form builder -->
<script src="/resources/js/offboardCheckList-html-generator.js" type="text/javascript" ></script> <!-- form generator -->

<script> 
	$(document).ready(function(){
		 
		 document.getElementById("templateDocs").disabled = true;
		 document.getElementById("checkList").disabled = true;
		 document.getElementById("templateTasks").disabled = true; 
		
		
		var offBoardingId = '${offBoardingId}';
		if(offBoardingId != null && offBoardingId != '' && offBoardingId != 'undefined'){
			
			$("#candidateOffBoardId").val(offBoardingId);
  	        document.getElementById("templateDocs").disabled = false;
  	        document.getElementById("templateTasks").disabled = false;
  	        document.getElementById("checkList").disabled = false;
  	        $("#candidateOffBoardUUID").val('${offBoardingUUID}');
  	        $("#documentstemplateId").val(offBoardingId);
  	        $("#offboardTaskDiv").val('Y');
  	        
  	        loadOffboardingUser('${offBoardingUUID}');
		}else{
			loadCandidates();
			loadOffBoardingTemplates();
		}
		
		
		
		
	});
</script>

<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Employee offboarding</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>  &raquo; <a href="<c:url value='${appPath}/candidateOnBoardingList'/>">Onboard & Offboard List</a> &raquo; Employee Offboarding </div>
    </section>
    <div class="content-area content-new clearfix">
      <div class="applicant_accept">
        <div class="tabbable">
          <ul class="nav nav-tabs" id="tab_bar">
            <li class="active" onclick="loadOffboardingUser();"> <a href="#details" data-toggle="tab"> <i class="fa fa-th-list paddingRight"></i> <span>Details</span> <br>
              <small>Employee details &amp; Position details</small> </a> </li>
            <li onclick="getTemplateResignationDocumentsDetails();getTemplateOffboardPackDocumentsDetails();"><a href="#documents" data-toggle="tab" id="templateDocs"> <i class="fa fa-files-o paddingRight"></i> <span>Documents</span> <br>
              <small>Acceptance &amp; Policy Documents</small> </a> </li>
             <li onclick="getOffboardingCheckList();"> <a href="#offboardCheckList" data-toggle="tab" id="checkList"> <i class="fa fa-check-square-o paddingRight"></i> <span>Check List</span> <br>
              <small>Check List</small> </a></li>
            <li onclick="getOffboardingTasksList();"> <a href="#tasks" data-toggle="tab" id="templateTasks"> <i class="fa fa-check-square-o paddingRight"></i> <span>Tasks</span> <br>
              <small>List of tasks</small> </a></li>
          </ul>
          <input type="hidden" name="candidateOffBoardId" id="candidateOffBoardId" value=""/>
          <input type="hidden" name="candidateOffBoardUUID" id="candidateOffBoardUUID" value=""/>
          <input type="hidden" id="offboardTaskDiv" value="N"/>
          <div class="tab-content"> 
            
            <!-- Employee Details -->
            <div class="tab-pane active" id="details">
             <form:form class="login-form clearfix"  id="offboardCandidate_form"  name="offboardCandidate_form" method="post" >
             <input type="hidden" name="savedoffboardId" id="savedoffboardId" value=""/>
              <div class="form-horizontal">
                <p><strong>Employee Details</strong></p>
               
                <div class="form-group">
                  <label class="col-md-3 control-label">Select User <span class="text-danger paddingLeft">*</span></label>
                  <div class="col-md-6">
                    <select class="chosen-select" id="offboardUser" readonly="readonly" name="offboardUser" data-validation="required" onchange="getUserDetails();" field-name="User">
                    </select>
                  </div>
                </div>
                
                <div class="form-group">
                  <label class="col-md-3 control-label">First Name</label>
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm" readonly="readonly" id="firstName" name="firstName" filed-name="First Name" readonly="readonly">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-md-3 control-label">Last Name</label>
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm" readonly="readonly" id="lastName" name="lastName"  filed-name="Last Name">
                  </div>
                </div>
                
                <div class="form-group">
                  <label class="col-md-3 control-label">Mobile Number</label>
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm" id="mobileNumber" name="mobileNumber"  readonly="readonly">
                  </div>
                </div>

                  
                <div class="form-group">
                  <label class="col-md-3 control-label">Email</label>
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm" id="email" name="email" readonly="readonly">
                  </div>
                </div>
                
                <div class="form-group">
                  <label class="col-md-3 control-label">Date of Joining</label>
                  <div class="col-md-6">
                  
                    <input type="text" class="form-control input-sm calendar" id="dateOfJoin" name="dateOfJoin" readonly="readonly">
                   
                  </div>
                </div>
                
                <div class="form-group">
                  <label class="col-md-3 control-label">Role</label>
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm" id="role" name="role" readonly="readonly">
                  </div>
                </div>
                
                <div class="form-group">
                  <label class="col-md-3 control-label">Employment Type</label>
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm" id="employmentType" name="employmentType" readonly="readonly">
                  </div>
                </div>
                  
                <div class="clearfix"></div>
                <p><strong>Offboard Details</strong></p>
				
                  <div class="form-group">
                      <label class="col-md-3 control-label">Reason<span class="paddingLeft text-danger">*</span></label>
                  <div class="col-md-6">
                      <select class="chosen-select" data-validation="required"  field-name="Reason" id="reason" name="reason">
                          <option selected disabled>- Select Reason -</option>
                          <option>Resignation</option>
                          <option>Termination</option>
                          <option>End of the contract</option>
                      </select>
                  </div>
                </div>

            <div class="form-group">
                  <label class="col-md-3 control-label">Explanation</label>
                  <div class="col-md-6">
                    <textarea rows="3" class="form-control" id="explanation" name="explanation" data-validation-optional="true"></textarea>
                 </div>
            </div>

               <div class="form-group" id="effectiveDateDiv">
                  <label class="col-md-3 control-label">Effective Date<span class="text-danger paddingLeft">* </span><i class='fa fa-info-circle' title='The start date for the offboardong of the candidate'></i></label>
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm calendar" data-validation="required" field-name="Effective Date" id="effectiveDate" name="effectiveDate" readonly="readonly" >
                </div>
                 </div>
                 
                  <div class="form-group">
                  <label class="col-md-3 control-label">Notice Period</label>
                  <div class="col-md-6 select-field">
                    <select class="chosen-select"  id="noticePeriod" name="noticePeriod" onchange="getNoticePeriod();getEndDate();">
                      <option  selected="selected" value=''>- Select Notice Period -</option>
                      <option value='Relieve Immediately'>Relieve Immediately</option>
                      <option value='15 Days'>15 Days</option>
                      <option value='1 Month'>1 Month</option>
                      <option value='2 Months'>2 Months</option>
                      <option value='3 Months'>3 Months</option>
                    </select>
                  </div>
                </div>
                  
                <div class="form-group" id="endDateDiv">
                  <label class="col-md-3 control-label">End date<span class="text-danger paddingLeft">* </span><i class='fa fa-info-circle' title='The employment end date for the employee'></i></label>
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm calendar" data-validation="required" field-name="End Date" id="endDate" name="endDate" readonly="readonly">
                  </div>
                </div>
                <div class="form-group" id="workInNotice" style="display: none;">
                  <label class="col-md-3 control-label">Have to work in notice period ?<span class="text-danger paddingLeft">*</span></label>
                  <div class="col-md-6">
                      <label class="radio-inline">
                    <input type="radio" value="Y" checked id="checkedY" name="workInNoticePeriod"> Yes
                          </label>
                      <label class="radio-inline">
                    <input type="radio" value="N" id="checkedN" name="workInNoticePeriod"> No
                    </label>
                  </div>
                </div>
                
                 <div class="form-group">
                       <label class="col-md-3 control-label">Offboard Template</label>
                  <div class="col-md-6 chooRadius chosenmbNone">
                  
				<div class="input-group col-md-10 pull-left">
						<select class="chosen-select" multiple data-placeholder="Select Template" data-validation-optional="true" id="offboardingTemplate" name="offboardingTemplate" >
                       </select>
                        <span class="input-group-addon cursorPointer bg-blue" onclick="loadOffBoardingTemplates();"><i class="fa fa-refresh "></i></span>
                        </div>
 <sec:authorize access="hasAnyRole('ROLE_CREATE_OFF_BOARD_TEMPLATE')">
                  <span class="col-md-2 noPad clearfix"><a href="<c:url value="/app/addNewOffboardTemplate" />" target="_blank"><i class="fa fa-plus btn btn-default pull-right minHeBtn"></i></a> </span>	
               </sec:authorize></div>                </div>
                
                
                  <div class="form-footer">
                  <div class="pull-right mr">
                     <input type="button" value="Cancel" class="btn btn-sm btn-default" onClick="self.location='/app/candidateOnBoardingList'">
                    <input type="button" value="Save" id="save_offboardUser" onclick="saveOffboardingCandidateDetails('save');" class="btn btn-sm btn-success">
                    <input type="button" value="Save and Continue" id="saveAndContinue_offboardUser" onclick="saveOffboardingCandidateDetails('saveAndContinue');"  class="btn btn-sm btn-primary">
                  </div>
                </div>
                 </form:form>
              </div>
            </div>
            
            <!-- TAB 2 -->
            <div class="tab-pane" id="documents">
              <div class="form-horizontal">
                <form id='templateDocuments_form' name='templateDocuments_form' >
                 <input type="hidden" name="candidateoffboardingId" id="documentstemplateId" value=""/>
                 
               
                <div class="form-group">
                  <label class="col-md-12"> <strong>Resignation / Termination Letter</strong> <br>
                  </label>
                     <div id="timesheetDocumentsId" class="timesheetDocs">
                      <div class="col-md-7 commentFile timesheet_week_comm onboardDocs">
				        <span class="uploadAttachments"> 
			              <div id="savedResignationAttachments"></div>
				        </span>
                      </div>
                      </div>
                </div>
                
                
                <div class="form-group">
                  <label class="col-md-12"> <strong>Offboard Package</strong> <br>
                  </label>
                     <div id="timesheetDocumentsId" class="timesheetDocs">
                      <div class="col-md-7 commentFile timesheet_week_comm onboardDocs">
				        <span class="uploadAttachments"> 
			              <div id="savedOffboardPackAttachments"></div>
				        </span>
                      </div>
                      </div>
                </div>
                
               
                
                <div class="clearfix"></div>
               
                <div class="form-footer">
                  <div class="pull-right mr">
                     <input type="button" value="Cancel" class="btn btn-sm btn-default" onClick="self.location='/app/candidateOnBoardingList'">
                    <input type="button" value="Save" id="save_docs_button"  onclick="createTemplateDocs('save');" class="btn btn-sm btn-success">
                    <input type="button" value="Save and Continue" id="save_continue_docs_button" onclick="createTemplateDocs('saveAndContinue');" class="btn btn-sm btn-primary">
                  </div>
                </div>
                </form>
              </div>
            </div>
            
              <div class="tab-pane" id="offboardCheckList">
              <div class="form-horizontal assignTemplate">
                <h4 class="clearfix">Check List</h4>
                 <div id="offboardingCheckList"></div>
              </div>
            </div>
            
            
            
            <div class="tab-pane" id="tasks">
              <div class="clearfix">
                <h4 class="pull-left">Task List</h4>
                <div class="pull-right">
                  <button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#addTask" onclick="getAssignToUsersList();flushOffboardTask();"><i class="fa fa-plus paddingRight"></i>Add Task</button>
                  <!-- Modal -->
                  <div id="addTask" class="modal fade add_Task" role="dialog">
                    <div class="modal-dialog modal-lg">
                      <div class="modal-content">
                        <div class="modal-header">
                          <button type="button" class="close" data-dismiss="modal" onclick="flushOffboardTask()">&times;</button>
                          <h4 class="modal-title">Add Tasks</h4>
                        </div>
                        
                        <div class="success_pop" id="selector"  style="display: none;">
	         				<i class="fa fa-check-circle"></i><span>Task Saved Successfully.</span>
					    </div>
					    
                        <div class="modal-body clearfix"> 
                          
                          <!-- Add New Task Widget -->
                          <div class="col-md-12 clearfix">
                          <div id="offboardtasksLoading"></div>
                             <form:form class="login-form clearfix"  id="addoffboardTask_form"  name="addoffboardTask_form" method="post" > 
                             <input type="hidden" name="offboardTaskId" id="candidateOffboardTaskId" value=""/>
                              <div class="row">
                              
                              <div class="col-md-6">
                                <div class="row">
                                
                                <div class="col-md-12 mb10" id="taskNameDiv">
                                  <label>Subject <span class="text-danger">*</span></label>
                                  <input type="text" class="form-control input-sm" placeholder="Subject" field-name="Subject" id="taskName" name="taskName"  data-validation="required validate_Space validate_length length2-100">
                                </div>
                                
                                <div class="col-md-12 dropdown-select mb10">
                                  <label>Priority</label>
                                  <select class="form-control" id="taskPriority" name="taskPriority">
                                    <option selected disabled>-- Select Priority --</option>
                                    <option>High</option>
                                    <option>Low</option>
                                    <option>Medium</option>
                                  </select>
                                </div>
                                
                                <div class="col-md-12 dropdown-select mb10">
                                  <label>Repeat</label>
                                  <select class="form-control input-sm" id="taskRepeat" name="taskRepeat">
                                    <option selected disabled>-- Select Repeat --</option>
                                    <option>Never</option>
                                    <option>Daily</option>
                                    <option>Weekly</option>
                                    <option>Monthly</option>
                                    <option>Yearly</option>
                                  </select>
                                </div>
                                
                                <div class="col-md-12 mb10">
                                  <div class="mt">
                                    <label class="paddingRight">
                                      <input type="checkbox" id="taskEmail" name="taskEmail" value="N" onclick="validateTaskEmail();">
                                      Email </label>
                                    <label class="paddingLeft">
                                      <input type="checkbox" id="taskNotification" name="taskNotification" value="N" onclick="validateTaskNotification();">
                                      Notification </label>
                                  </div>
                                </div>
                                
                                </div>
                                </div>
                                
                                
                                <div class="col-md-6">
	                                <div class="row">
	                                
	                                <div class="col-md-12 mb10">
                                  <label>Assign To <span class="text-danger">*</span></label>
                                  
                                  <div class="">
                                    <label class="paddingRight">
                                      <input type="radio"  name="assignedToType" checked="checked" onclick="validateAssignToType('assingToCandidate');">
                                      Offboard User </label>
                                    <label class="paddingLeft">
                                      <input type="radio" name="assignedToType"  onclick="validateAssignToType('assingToUser');">
                                      Associate </label>
                                  </div>
                                  
                                  <div id="assignToUserDiv" style="display: none;" class="mt10">
	                                  <select class="chosen-select" multiple data-placeholder="Select User" id="offboardAssignedTo" name="offboardAssignedTo" field-name="AssignTo"  data-validation-optional="true" >
	                                  </select>
                                  </div>
                                </div>
	                                
	                                
	                               <div class="col-md-12 mb10">
                                  <div class="row">
                                    <div class="col-md-12">
                                      <label><strong>Due date:</strong><span class="text-danger">*</span></label>
                                                                            
                                      <!-- Form inline Due after -->
                                      
                                      <div class="row" id="dueAfterDiv">
										   <div class="col-md-6 paddingRight">
											<label class="small">Months</label> <br>
												<div class="select-field">
													<select class="form-control input-sm" id="taskMonths" data-validation="required" field-name="Due date" name="taskMonths" onchange="validateDueAfter();">
														<option selected value=''>--Select Month--</option>
														<option value="1">1</option>
														<option value="2">2</option>
														<option value="3">3</option>
														<option value="4">4</option>
														<option value="5">5</option>
														<option value="6">6</option>
														<option value="7">7</option>
														<option value="8">8</option>
														<option value="9">9</option>
														<option value="10">10</option>
														<option value="11">11</option>
														<option value="12">12</option>
														</select>
												</div>
											</div> 
                                      
                                        
                                        <div class="col-md-6 paddingLeft">
										<label class="small">Days</label>
											<div class="select-field">
												<select class="form-control input-sm" id="taskDays" name="taskDays" onchange="validateDueAfter();">
													<option selected value=''>--Select Days--</option>
													    <option value="1">1</option>
														<option value="2">2</option>
														<option value="3">3</option>
														<option value="4">4</option>
														<option value="5">5</option>
														<option value="6">6</option>
														<option value="7">7</option>
														<option value="8">8</option>
														<option value="9">9</option>
														<option value="10">10</option>
														<option value="11">11</option>
														<option value="12">12</option>
														<option value="13">13</option>
														<option value="14">14</option>
														<option value="15">15</option>
														<option value="16">16</option>
														<option value="17">17</option>
														<option value="18">18</option>
														<option value="19">19</option>
														<option value="20">20</option>
														<option value="21">21</option>
														<option value="22">22</option>
														<option value="23">23</option>
														<option value="24">24</option>
														<option value="25">25</option>
														<option value="26">26</option>
														<option value="27">27</option>
														<option value="28">28</option>
														<option value="29">29</option>
														<option value="30">30</option>
												</select>
											</div>
                                      </div>
                                    </div>
                                   </div>
                                  </div>
                                </div>
                                <div class="clearfix"></div>   
                                <div class="col-md-12 mb10">
		                               <div class="clearfix mb10">
	                                   <label>Description</label>
	                                  <textarea rows="2" placeholder="Description" id="taskdescription" name="taskdescription" class="form-control"></textarea>
	                                </div>
	                            </div>   
	                           </div>
	                          </div>
                             </div>
                                
                                <div class="clearfix"></div>
                                <div class="clearfix mt col-md-12 form-footer">
                                  <div class="pull-right">
                                    <input type="button" id="offboardtask_submit" class="btn btn-primary btn-sm" onclick="saveOffboardTask();"  value="Save">
                                    <input type="button" class="btn btn-default btn-sm ml10" value="Cancel" data-dismiss="modal" onclick="flushOffboardTask();">
                                  </div>
                                </div>
                              </div>
                            </form:form>
                          </div>
                          <!-- Add New Task Widget --> 
                          
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div id="offboardTasksList"></div>
              
              
          </div>
          <!-- STYLED IN FORMS.CSS --> 
        </div>
      </div>
    </div>
  </section>

  <jsp:include page="../masterFooter.jsp" />
  
  <div id="divResignationAttachmentTd1" style="display: none; float: left;">
			<div id='REPLACEROWIDResignationdocFileuploadFileGroup'>
	  			<input type='file'  id='REPLACEROWIDresignationdocFile' class="timesheetAttachmentValidation" data-validation="required validatedoc" field-name="Document" onchange='uploadResignationTerminationDoc(REPLACEROWID)'> 
	  			<i class="fa fa-times" onclick="removeResignationAttachmentRow(REPLACEROWID);"></i>
	  		</div>
</div>

<div id="divOffboardPackAttachmentTd1" style="display: none; float: left;">
			<div id='REPLACEROWIDOffboardPackdocFileuploadFileGroup'>
	  			<input type='file' id='REPLACEROWIDoffboardPackdocFile' class="timesheetAttachmentValidation" data-validation="required validatedoc" field-name="Document" onchange='uploadOffboardPackDoc(REPLACEROWID)'> 
	  			<i class="fa fa-times" onclick="removeOffboardPackAttachmentRow(REPLACEROWID);"></i>
	  		</div>
</div>


<script>
function validateTaskEmail(){
	  var taskEmail = document.getElementById("taskEmail").checked;
	  if(taskEmail == 1){
		  document.getElementById("taskEmail").value = 'Y';
	  }else{
		  document.getElementById("taskEmail").value = 'N';
	  }
}

function addResignationAttachment(){  
			var checkMaxTsRowNum = $("#resignationAttcahmentMaxRowNumber").val();
			var tbl = document.getElementById("resignationAttachmentTable");
			var maxRowNum = parseInt($("input[name = 'resignationAttcahmentMaxRowNumber']").val());
			$("input[name = 'resignationAttcahmentMaxRowNumber']").val(maxRowNum + 1);
			
			
			var row = tbl.insertRow(maxRowNum);
			row.setAttribute("id", "divAttachmentResignationRow" + maxRowNum);
			
			
			var cell0 = row.insertCell(0);
			var divAttachmentTdStr = document.getElementById("divResignationAttachmentTd1").innerHTML;
			divAttachmentTdStr = divAttachmentTdStr.replace(/REPLACEROWID/g, (maxRowNum));
			cell0.innerHTML = divAttachmentTdStr;
			
			
}	
function uploadResignationTerminationDoc(id){
	fileUpload(""+id+"resignationdocFile", "Employee-Offboard-Resignation-Documents", "templateDocuments_form","resignationFileInfo");
}
function uploadOffboardPackDoc(id){
	fileUpload(""+id+"offboardPackdocFile", "Employee-Offboard-OffboardPack-Documents", "templateDocuments_form","offboardPackFileInfo");
}
</script>


<script>
function addOffboardPackAttachment(){  
			var checkMaxTsRowNum = $("#offboardPackAttcahmentMaxRowNumber").val();
			var tbl = document.getElementById("offboardPackAttachmentTable");
			var maxRowNum = parseInt($("input[name = 'offboardPackAttcahmentMaxRowNumber']").val());
			$("input[name = 'offboardPackAttcahmentMaxRowNumber']").val(maxRowNum + 1);
			
			
			var row = tbl.insertRow(maxRowNum);
			row.setAttribute("id", "divAttachmentOffboardPackRow" + maxRowNum);
			
			
			var cell0 = row.insertCell(0);
			var divAttachmentTdStr = document.getElementById("divOffboardPackAttachmentTd1").innerHTML;
			divAttachmentTdStr = divAttachmentTdStr.replace(/REPLACEROWID/g, (maxRowNum));
			cell0.innerHTML = divAttachmentTdStr;
			
			
}			
</script>
	
<script type="text/javascript">
	function removeResignationAttachmentRow(rowId) {
		 document.getElementById("divAttachmentResignationRow"+rowId).style.display = 'none';
		 document.getElementById("divAttachmentResignationRow"+rowId).value= '';
		 $("#templateDocuments_form input[data-id='"+rowId+"resignationdocFile']").remove();
		 var  rId = "divAttachmentResignationRow"+rowId;
		  var r = document.getElementById("resignationAttachmentTable").rows[rId];
          var count = 2*(r.cells.length);
		    for(i=0;i<=count;i++){
		        r.deleteCell(0);
		    } 
		 
	}
</script>


<script>
function addOffboardPackAttachment(){  
			var checkMaxTsRowNum = $("#offboardPackAttcahmentMaxRowNumber").val();
			var tbl = document.getElementById("offboardPackAttachmentTable");
			var maxRowNum = parseInt($("input[name = 'offboardPackAttcahmentMaxRowNumber']").val());
			$("input[name = 'offboardPackAttcahmentMaxRowNumber']").val(maxRowNum + 1);
			
			
			var row = tbl.insertRow(maxRowNum);
			row.setAttribute("id", "divAttachmentOffboardPackRow" + maxRowNum);
			
			
			var cell0 = row.insertCell(0);
			var divAttachmentTdStr = document.getElementById("divOffboardPackAttachmentTd1").innerHTML;
			divAttachmentTdStr = divAttachmentTdStr.replace(/REPLACEROWID/g, (maxRowNum));
			cell0.innerHTML = divAttachmentTdStr;
			
			
}			
</script>
	
<script type="text/javascript">
	function removeOffboardPackAttachmentRow(rowId) {
		 document.getElementById("divAttachmentOffboardPackRow"+rowId).style.display = 'none';
		 document.getElementById("divAttachmentOffboardPackRow"+rowId).value= '';
		 $("#templateDocuments_form input[data-id='"+rowId+"offboardPackdocFile']").remove();
		 var  rId = "divAttachmentOffboardPackRow"+rowId;
		  var r = document.getElementById("offboardPackAttachmentTable").rows[rId];
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
  
  
  <script>
  function loadCandidates(){
	  var sourceleadUrl = '${sourceleadUrl}';
		$.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/getUsersForOffboard?${_csrf.parameterName}=${_csrf.token}",	    	   
		    success: function(response) {
		    	if (response.response === "AWKWARD") {
		    	    location.href = "/errorPage";
		    	} else if (response.response === "SUCCESS") {
		    		var json = JSON.parse(response.object);
		    	    var result = "";
			        result = result +'<option disabled selected>--Select User--</option>';
		        	for (var i=0; i<json.length; i++) {
		        		var report = json[i];
		        		result = result +'<option value='+report.id+' >'+report.userName+'</option>';
					}
		        	$("#offboardUser").empty();
		           	$("#offboardUser").append(result); 
		           	$('#offboardUser').trigger("chosen:updated");
		    	} else if (response.indexOf('Sign In') !== -1) {
		    		location.replace(sourceleadUrl);
		    	} else {
		    	    showError("Operation failed, please try again");
		    	}
		    }
	   });
  }
  </script>
  
  
  <script>
  function getUserDetails(){
	  var offboardUserId = document.getElementById("offboardUser").value;
	  if(offboardUser){
		  var sourceleadUrl = '${sourceleadUrl}';
			$.ajax({
			   	type: "POST",
			    url: sourceleadUrl+"/getOffboardUserDetailsById?${_csrf.parameterName}=${_csrf.token}&offboardUserId="+offboardUserId,	    	   
			    success: function(response) {
			    	if (response.response === "AWKWARD") {
			    	    location.href = "/errorPage";
			    	} else if (response.response === "SUCCESS") {
			    		var json = JSON.parse(response.object);
				    	 $("#firstName").val(json['firstName']);
				    	 $("#lastName").val(json['lastName']);
				    	 $("#mobileNumber").val(json['mobilePhone']);
				    	 $("#email").val(json['email']);
				    	 $("#mobilePhone").val(json['mobilePhone']);
				    	 $("#dateOfJoin").val(json['dateOfJoin']);
				    	 $("#role").val(json['role']);
				    	 
				    	 if(json['employmentType'] != null && json['employmentType'] != '' && json['employmentType'] != 'undefined'){
				    		 $("#employmentType").val(json['employmentType']);
				    	 }
				    	 
				    	 if(json['noticePeriod'] != null && json['noticePeriod'] != '' && json['noticePeriod'] != 'undefined'){
				    		 $("#noticePeriod").val(json['noticePeriod']);
					    	 $('#noticePeriod').trigger("chosen:updated");
					    	 document.getElementById("workInNotice").style.display="";
				    	 }else{
				    		 $("#noticePeriod").val('');
					    	 $('#noticePeriod').trigger("chosen:updated");
					    	 document.getElementById("workInNotice").style.display="none";
				    	 }
			        	
				    	 $("#reason").val('');
				    	 $('#reason').trigger("chosen:updated");
				    	 
				    	 $("#endDate").val('');
				    	 $("#effectiveDate").val('');
				    	 $("#explanation").val('');
				    	 $("#offboardingTemplate").val('');
				    	 $('#offboardingTemplate').trigger("chosen:updated");
			    	} else if (response.indexOf('Sign In') !== -1) {
			    		location.replace(sourceleadUrl);
			    	} else {
			    	    showError("Operation failed, please try again");
			    	}
			    }
		   });
	  }
  }
  </script>
  
  
  <script>
function getTemplateResignationDocumentsDetails(offboardId){
	$("#templateDocuments_form input[name*='resignationFileInfo']").remove();
	$("#templateDocuments_form input[name*='offboardPackFileInfo']").remove();
	if(offboardId == '' || offboardId == null || offboardId =='undefined'){
		offboardId = document.getElementById("candidateOffBoardId").value;
	}
	var documentType = "Resignation"
	if(offboardId){
		var sourceleadUrl ='${sourceleadUrl}';
	     $.ajax({
		    	type: "POST",
		    	 url: sourceleadUrl+"/getCandidateOffboardDocumentByOffboardId?${_csrf.parameterName}=${_csrf.token}&offboardId="+offboardId+"&documentType="+documentType, 
		        success: function(response) {
		        	if (response.response === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (response.response === "SUCCESS") {
		        		var json = response.data;
			        	var result = "";
	                	for (var i = 0; i < json.length; i++) { 
	                		 var opt = json[i]; 
	                		 result = result +"<strong id='resignationdocument"+opt.id+"'>";
	                		 result = result +"<i class=''><a class='glyphicon glyphicon-save' href=\""+opt.fileURL+"\"></a></i>";
	           				 result = result +"<input type='button' value='"+opt.fileName+"'>";
	           				 result = result +"</a>";
	           				 
	           				 if(opt.offboardDocumentType != 'Candidate Documents') {
	         					result = result +"<input class='dele' type='button' onClick='deleteTemplateDocument("+opt.id+",\""+documentType+"\");' >"; 
	        				 } else {
	        					result = result +"<input class='dele' type='button' onClick='deleteOffBoardingDocument(\""+opt.documentUUID+"\","+opt.id+",\""+documentType+"\");' >";
	        				 }
	           				 if(opt.offboardDocumentType != 'Candidate Documents') {
	            					result = result +"<input type='hidden' name='savedResignationAttcahments' id='resignationAttachment"+opt.id+"' value='"+opt.id+"' />";
	           				 }
	           				 result = result +"</strong>";
	                	 }
	                	 result = result +"<input type='hidden' name='resignationAttcahmentMaxRowNumber'  id='resignationAttcahmentMaxRowNumber' value='1' />";
	                	 result = result +"<table class='timesheetAttachment' id='resignationAttachmentTable' cellpadding='0' cellspacing='0'>";
	                	 result = result +"<tr id='divAttachmentTsRow0'>";
	                	 result = result +"<td><div id='0ResignationdocFileuploadFileGroup'><input type='file' name='0resignationdocFile' id='0resignationdocFile' field-name='Attachment' data-validation='validatedoc' data-validation-optional='true' onchange='uploadResignationTerminationDoc(0)'  class='timesheetAttachmentValidation'><i class='fa fa-plus' onClick='addResignationAttachment()'></i></div></td>";
	                	 result = result +"</tr>";
	                	 result = result +"</table>";
	                	 $("#savedResignationAttachments").empty();
	                	 $("#savedResignationAttachments").append(result);
	                	 $("#offboardTasksList").empty();
		        	} else if (response.indexOf('Sign In') !== -1) {
		        		location.replace(sourceleadUrl);
		        	} else {
		        	    showError("Operation failed, please try again");
		        	}
		        },
		    });
	}
}
</script>



<script>
function getTemplateOffboardPackDocumentsDetails(offboardId){
	$("#templateDocuments_form input[name*='resignationFileInfo']").remove();
	$("#templateDocuments_form input[name*='offboardPackFileInfo']").remove();
	if(offboardId == '' || offboardId == null || offboardId =='undefined'){
		offboardId = document.getElementById("candidateOffBoardId").value;
	}
	
	var documentType = "Offboard Pack"
	if(offboardId){
		var sourceleadUrl ='${sourceleadUrl}';
	     $.ajax({
		    	type: "POST",
		    	 url: sourceleadUrl+"/getCandidateOffboardDocumentByOffboardId?${_csrf.parameterName}=${_csrf.token}&offboardId="+offboardId+"&documentType="+documentType, 
		        success: function(response) {
		        	if (response.response === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (response.response === "SUCCESS") {
		        		 var json = response.data;
			        	 var result = "";
	                	 for (var i = 0; i < json.length; i++) { 
	                		 var opt = json[i]; 
	                		 result = result +"<strong id='offboardPackdocument"+opt.id+"'>";
	                		 result = result +"<i class=''><a class='glyphicon glyphicon-save' href=\""+opt.fileURL+"\"></a></i>";
	           				 result = result +"<input type='button' value='"+opt.fileName+"'>";
	           				 result = result +"</a>";

	           				 if(opt.offboardDocumentType != 'Candidate Documents'){
	           					result = result +"<input class='dele' type='button' onClick='deleteTemplateDocument("+opt.id+",\""+documentType+"\");' >"; 
	           				 }else{
	           					result = result +"<input class='dele' type='button' onClick='deleteOffBoardingDocument(\""+opt.documentUUID+"\","+opt.id+",\""+documentType+"\");' >";
	           				 }
	           				 
	           				 if(opt.offboardDocumentType != 'Candidate Documents'){
	         					result = result +"<input type='hidden' name='savedOffboardPackAttcahments' id='offboardPackAttachment"+opt.id+"' value='"+opt.id+"' />";
	        				  }
	           				 
	           				 result = result +"</strong>";
	                	 }
	                	 
	                	 result = result +"<input type='hidden' name='offboardPackAttcahmentMaxRowNumber'  id='offboardPackAttcahmentMaxRowNumber' value='1' />";
	                	 result = result +"<table class='timesheetAttachment' id='offboardPackAttachmentTable' cellpadding='0' cellspacing='0'>";
	                	 result = result +"<tr id='divAttachmentTsRow0'>";
	                	 result = result +"<td><div id='0OffboardPackdocFileuploadFileGroup'><input type='file'  id='0offboardPackdocFile' field-name='Attachment' data-validation='validatedoc' data-validation-optional='true' onchange='uploadOffboardPackDoc(0)'  class='timesheetAttachmentValidation'><i class='fa fa-plus' onClick='addOffboardPackAttachment();'></i></div></td>";
	                	 result = result +"</tr>";
	                	 result = result +"</table>";
	                	 $("#savedOffboardPackAttachments").empty();
	                	 $("#savedOffboardPackAttachments").append(result);
	                	 $("#offboardTasksList").empty();
				        
		        	} else if (response.indexOf('Sign In') !== -1) {
		        		location.replace(sourceleadUrl);
		        	} else {
		        	    showError("Operation failed, please try again");
		        	}
		        },
		    });
	}
}
</script>
  
  
  
  
<script>
  $( function() {
	  
	  var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
	  var finalDateFormat = '';
	  if(locationDate.match("yyyy-MM-dd")){
		  finalDateFormat = 'yy-mm-dd';
	  }if(locationDate.match("dd-MM-yyyy")){
		  finalDateFormat = 'dd-mm-yy'; 
	  }if(locationDate.match("MM-dd-yyyy")){
		  finalDateFormat = 'mm-dd-yy'; 
	  }if(locationDate.match("yyyy-dd-MM")){
		  finalDateFormat = 'yy-dd-mm'; 
	  }
	  
	  $('#endDate').datepicker({
	      dateFormat: finalDateFormat,
	      changeMonth: true,
	  	  changeYear: true, 
	  	  minDate: 0 ,
	  	  onSelect : function(selected) {
	  		addValidClass();
	  	 }
	  });
	  
	  $('#effectiveDate').datepicker({
	      dateFormat: finalDateFormat,
	      changeMonth: true,
	  	  changeYear: true, 
	  	  minDate: 0  ,
	  	  onSelect : function(selected) {
			addValidClass();
			getEndDate();
			}
	  });
	  
  } );
</script>


<script type="text/javascript"> 
	function addValidClass(){
		
		 var endDate = document.getElementById("endDate").value;
		 if(endDate){
			 $('#endDateDiv').find('p.jquery_form_error_message').remove(); 
		 }
		 
		 var effectiveDate = document.getElementById("effectiveDate").value;
		 if(effectiveDate){
			 $('#effectiveDateDiv').find('p.jquery_form_error_message').remove(); 
		 }
}
</script>

<script>
function toDate(selector) {
    var from = $(selector).val().split("-");
    return new Date(from[2], from[1] - 1, from[0]);
}
</script>
  

<script>
function loadOffBoardingTemplates(offboardTemplateId){
	
	var sourceleadUrl = '${sourceleadUrl}';
	$.ajax({
	   	type: "POST",
	    url: sourceleadUrl+"/getOffboardingTemplatesForCandidateOffBoarding?${_csrf.parameterName}=${_csrf.token}",	    	   
	    success: function(response) {
	    	if (response.response === "AWKWARD") {
	    	    location.href = "/errorPage";
	    	} else if (response.response === "SUCCESS") {
	    		var json = JSON.parse(response.object);
	        	var result = "";
	        	for (var i=0; i<json.length; i++) {
	        		var report = json[i];
	        		if(offboardTemplateId != null && offboardTemplateId != "" && offboardTemplateId !="undefined") {
	        			var templateId = offboardTemplateId.split(",");
	        			var selected = '';
	        			for(var j=0; j<templateId.length; j++) {
	        				if(templateId[j] == report.id) {
	        					selected = 'selected';
	        				}
	        			}
	        		}
	        		result = result +'<option value='+report.id+' '+selected+'>'+report.templateName+'</option>';
				}
	        	$("#offboardingTemplate").empty();
	           	$("#offboardingTemplate").append(result); 
	           	$('#offboardingTemplate').trigger("chosen:updated");
	    	} else if (response.indexOf('Sign In') !== -1) {
	    		location.replace(sourceleadUrl);
	    	} else {
	    	    showError("Operation failed, please try again");
	    	}
	    }
   });
}
</script>



<script>
 function saveOffboardingCandidateDetails(saveType){
	 
	 var validationSettings = {
		        errorMessagePosition : 'element',
		       borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd' 
	         }; 
	 
	 if ($('#offboardCandidate_form').validate(false, validationSettings)){ 
		 var formData = getFormData1("offboardCandidate_form");
	        var sourceleadUrl ='${sourceleadUrl}';
	        $("#save_offboardUser").attr("disabled", "disabled");
	        $("#saveAndContinue_offboardUser").attr("disabled", "disabled");
	       
	        $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	         $.ajax({
		    	type: "POST",
		    	data: formData,
		 		processData: false, 
		 		contentType: false,
		    	 url: sourceleadUrl+"/saveOffboardingCandidateDetails?${_csrf.parameterName}=${_csrf.token}", 
		        success: function(response) {
		        	if (response.response === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (response.response === "SUCCESS") {
		        		var json = response.object;
		        		if(saveType == 'saveAndContinue') {
			        		var $tabs = $('.tabbable li');
			     	        $tabs.filter('.active').next('li').find('a[data-toggle="tab"]').tab('show');
			     	        document.getElementById("templateDocs").disabled = false;
			     	        getTemplateResignationDocumentsDetails(json.id);
			     	        getTemplateOffboardPackDocumentsDetails(json.id);
			     	        $("#documentstemplateId").val(json.id);
			        	}
			        	$("#savedoffboardId").val(json.id);
		     	        $("#candidateOffBoardId").val(json.id);
		     	        $("#save_offboardUser").removeAttr("disabled");
		     	        $("#saveAndContinue_offboardUser").removeAttr("disabled");
		     	        $("#loading").removeAttr("style");
		        	} else if (response.indexOf('Sign In') !== -1) {
		        		location.replace(sourceleadUrl);
		        	} else {
		        	    showError("Operation failed, please try again");
		        	}
		        },
		    }); 
	        return true;
	  } else {
		return false;
	  }
 }
 
 $('body').on('blur', '#offboardCandidate_form', function() {
		$("#offboardCandidate_form").showHelpOnFocus();
		$("#offboardCandidate_form").validateOnBlur(false, validationSettings);  
	});
 </script> 
 
 
 <script>
 function getNoticePeriod(){
	 var noticePeriod = document.getElementById("noticePeriod").value;
	 if(noticePeriod != '' && noticePeriod != null && noticePeriod != 'undefined' && noticePeriod != "Relieve Immediately"){
		 document.getElementById("workInNotice").style.display="";
	 }else{
		 document.getElementById("workInNotice").style.display="none";
	 }
 }
 </script>
 
 
 <script>
 function loadOffboardingUser(offBoardingUUID){
	 if(offBoardingUUID == null && offBoardingUUID == "" && offBoardingUUID =="undefined"){
		 offBoardingUUID = document.getElementById("candidateOffBoardUUID").value;
	 }
	 if(offBoardingUUID) {
	 var sourceleadUrl ='${sourceleadUrl}';
	 var locationCode ='${SourceLeadWeb_locationCode}';
	 
	 $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	    $.ajax({
	    	type: "POST",
	    	 url: sourceleadUrl+"/loadOffboardingCandidateDetails?${_csrf.parameterName}=${_csrf.token}&offBoardingUUID="+offBoardingUUID+"&locationCode="+locationCode, 
	        success: function(response) {
	        	if (response.response === "AWKWARD") {
	        	    location.href = "/errorPage";
	        	} else if (response.response === "SUCCESS") {
        		 	var json = response.object;
	        	 	var result = "";
	        	 	var selected = 'selected';
	        	 	result = result +'<option disabled selected>--Select User--</option>';
	        	 	result = result +'<option value='+json.offboardUser+' '+selected+' >'+json['candidateName']+'</option>';
	        	 	$("#offboardUser").empty();
	           	 	$("#offboardUser").append(result); 
	           	 	$('#offboardUser').trigger("chosen:updated");
	        	 	$("#firstName").val(json['firstName']);
	        	 	if(json['lastName'] != null && json['lastName'] != "" && json['lastName'] !="undefined"){
	        			$("#lastName").val(json['lastName']);
	        	 	}
		        	$("#mobileNumber").val(json['mobileNumber']);
			    	$("#email").val(json['email']);
			    	$("#role").val(json['role']);
			    	if(json['employmentType'] != null && json['employmentType'] != "" && json['employmentType'] !="undefined"){
			    		$("#employmentType").val(json['employmentType']);
			    	}
			    	$("#dateOfJoin").val(json['dateOfJoinStr']);
			    	if(json['endDateStr'] != null && json['endDateStr'] != "" && json['endDateStr'] !="undefined"){
			    		$("#endDate").val(json['endDateStr']);
			    	}
			    	if(json['reason'] != null && json['reason'] != "" && json['reason'] !="undefined"){
			    		$("#reason").val(json['reason']);
				    	$('#reason').trigger("chosen:updated");
			    	}
			    	if(json['noticePeriod'] != null && json['noticePeriod'] != "" && json['noticePeriod'] !="undefined"){
			    		$("#noticePeriod").val(json['noticePeriod']);
				    	$('#noticePeriod').trigger("chosen:updated");
				    	if(json['noticePeriod']  != 'Relieve Immediately'){
				    		document.getElementById("workInNotice").style.display="";
				    	}else{
				    		document.getElementById("workInNotice").style.display="none";
				    	}
				    	if(json['haveToWorkInNoticePeriod'] == 'Y'){
				    		$("#checkedY").val(json['haveToWorkInNoticePeriod']);
				    		document.getElementById('checkedY').checked = true;
				    	}else{
				    		$("#checkedN").val(json['haveToWorkInNoticePeriod']);
				    		document.getElementById('checkedN').checked = true;
				    	}
			    	}else{
			    		document.getElementById("workInNotice").style.display="none";
			    	}
			    	if(json['explanation'] != null && json['explanation'] != "" && json['explanation'] !="undefined"){
			    		$("#explanation").val(json['explanation']);
			    	}
			    	if(json['effectiveDate'] != null && json['effectiveDate'] != "" && json['effectiveDate'] !="undefined"){
			    		$("#effectiveDate").val(json['effectiveDateStr']);
			    	}
			    	/* if(json['offboardTemplateId'] != null && json['offboardTemplateId'] != "" && json['offboardTemplateId'] !="undefined"){
			    		loadOffBoardingTemplates(json['offboardTemplateId']);
			    	} */
			    	loadOffBoardingTemplates(json['offboardTemplateId']);
			    	$("#savedoffboardId").val(json['savedoffboardId']);
			    	$("#loading").removeAttr("style");
	        	} else if (response.indexOf('Sign In') !== -1) {
	        		location.replace(sourceleadUrl);
	        	} else {
	        	    showError("Operation failed, please try again");
	        	}
	        },
	    });
	 }
	 $("#offboardTasksList").empty();
 }
 </script>
 
 <script>
function deleteOffBoardingDocument(documentUUID,documentId,documentType){
	var sourceleadUrl ='${sourceleadUrl}';
   	$.ajax({
    	type: "POST",
    	url: sourceleadUrl+"/deleteOffBoardingDocumentByDocumentUUID?${_csrf.parameterName}=${_csrf.token}&documentUUID="+documentUUID, 
        success: function(response) {
        	if (response.response === "AWKWARD") {
        	    location.href = "/errorPage";
        	} else if (response.response === "SUCCESS") {
        		if(documentType == 'Resignation'){
        			document.getElementById("resignationdocument"+documentId).remove();
        		}else if(documentType == 'Offboard Pack'){
        			document.getElementById("offboardPackdocument"+documentId).remove();
        		}
        	} else if (response.indexOf('Sign In') !== -1) {
        		location.replace(sourceleadUrl);
        	} else {
        	    showError("Operation failed, please try again");
        	}
        },
    });
}
</script>

<script>
function deleteTemplateDocument(documentId,documentType){
	
	if(documentType == 'Resignation'){
		document.getElementById("resignationdocument"+documentId).remove();
		document.getElementById("resignationAttachment"+documentId).remove();
	}
	else if(documentType == 'Offboard Pack'){
		document.getElementById("offboardPackdocument"+documentId).remove();
		document.getElementById("offboardPackAttachment"+documentId).remove();
	}
	 
}
</script>


<script>
function createTemplateDocs(saveType){
	var validationSettings = {
	        errorMessagePosition : 'element',
	       borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
         }; 
	
	  var sourceleadUrl ='${sourceleadUrl}';
	  
	  if ($('#templateDocuments_form').validate(false, validationSettings)){ 
		   $("#save_docs_button").attr("disabled", "disabled");
		   $("#save_continue_docs_button").attr("disabled", "disabled");
		   var formData = getFormData1("templateDocuments_form");
	   $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		$.ajax({
	        type : "POST",
		      data: formData,
		      processData: false,
		      contentType:false, 
	    	  url: sourceleadUrl+"/saveCandidatedOffboardingDocuments?${_csrf.parameterName}=${_csrf.token}",		
		      success :function(response) {
		    	  	if (response.response === "AWKWARD") {
		    		    location.href = "/errorPage";
		    		} else if (response.response === "SUCCESS") {
		    			if(saveType == 'saveAndContinue'){
			        		var $tabs = $('.tabbable li');
			     	        $tabs.filter('.active').next('li').find('a[data-toggle="tab"]').tab('show');
			     	        document.getElementById("checkList").disabled = false;
			     	        getOffboardingCheckList();
			        	} else {
			        		getTemplateResignationDocumentsDetails();
			     	        getTemplateOffboardPackDocumentsDetails();
			        	}
			   	     $("#save_docs_button").removeAttr("disabled");
			   	     $("#save_continue_docs_button").removeAttr("disabled");
			   	   
			    	  $("#loading").removeAttr("style");
		    		} else if (response.indexOf('Sign In') !== -1) {
		    			location.replace(sourceleadUrl);
		    		} else {
		    		    showError("Operation failed, please try again");
		    		}
		      }
	  });
		return true;
	  } else {
		  return false;
	  }
	  $('body').on('blur', '#templateDocuments_form', function() {
			$("#templateDocuments_form").showHelpOnFocus();
			$("#templateDocuments_form").validateOnBlur(false, validationSettings);  
	   });
}
</script>

<script>
function getOffboardingCheckList(){
	
	var result = "";
	var candidateOffBoardId = document.getElementById("candidateOffBoardId").value;
	var sourceleadUrl ='${sourceleadUrl}';
	var locationId = '${SourceLeadWeb_orgLocationId}';
	
	if(candidateOffBoardId){
	result = result + '<div class="clearfix">';
	        result = result + '</div>';
	        result = result + '<div id="sjfb-wrap">';
	        result = result + '<div id="offboardcheckListLoading"></div>';
	           result = result + '<form id="sjfb" novalidate>';
	           result = result + '<input type="hidden" id="checkListoffboardId" name="checkListoffboardId"  value="'+candidateOffBoardId+'">';
	           
		    	
		    	result = result + '<div class="clearfix mb mt">';
		    	
		    	result = result + '<div class="col-md-10">';
		    	result = result + '<p>Add Check List</p>';
		    	result = result + '</div>';
		    	
		    	result = result + '<div class="col-md-2">';
		        result = result + '<div class="dropdown">';
		        result = result + '<button class="btn btn-success dropdown-toggle btn-block" type="button" data-toggle="dropdown" id="addFiled-dropdown">Add Field &nbsp;&nbsp;';
		        result = result + '<span class="caret"></span></button>';
		        result = result + '<ul id="add-field" class="dropdown-menu pull-right">';
		        result = result + ' <li><a id="add-checkbox" data-type="checkbox" onclick="scrollToBottom();" href="javascript:void(0)">Check Box</a></li>';
		        result = result + '<li><a id="add-radio" data-type="radio" onclick="scrollToBottom();"  href="javascript:void(0)">Radio Button</a></li>';
		        result = result + '<li><a id="add-textarea" data-type="textarea" onclick="scrollToBottom();" href="javascript:void(0)">Text Area</a></li>';
		        result = result + '</ul>';
		        result = result + '</div>';
		        result = result + '</div>';
		        result = result + '</div>';
		       
	           
	           result = result + '<div id="form-fields">';
	           result = result + '</div>';
	           result = result + '<div class="form-footer clearfix mt">';
	           result = result + '<div class="clearfix"></div>';
	           result = result + '<div class="pull-right mr">';
	           
	           result = result +"<input type='button' value='Save' id='save_checkList_button'  onclick='createOffboardCheckList(\"save\");' class='btn btn-sm btn-success'>";
	           result = result +"<input type='button' value='Save and Continue' id='save_continue_checkList_button'  onclick='createOffboardCheckList(\"saveAndContinue\");'  class='btn btn-sm btn-primary'>";
	           
	           result = result + '</div>';

	           result = result + '</div>';
	           result = result + '</form>';

	      
	           result = result + '</section>';
	           
	           result = result + '<div id="popUp"></div>'; 
	           result = result + '<div id="choicepopUp"></div>'; 
	           
	           $("#offboardingCheckList").empty();
	           $("#offboardingCheckList").append(result); 
	           $("#offboardTasksList").empty();
	           customizeFields(candidateOffBoardId,sourceleadUrl,"offboarding");
	}
	           
	
}
</script>
  
  
 <script>
function createOffboardCheckList(saveType){
	
	var validationSettings = {
	        errorMessagePosition : 'element',
	       borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
         }; 
	
	  if ($('#sjfb').validate(false, validationSettings)){ 
	        var isRemoved = '';
	        var isLabelModified = 'N';
	        //Loop through fields and save field data to array
	        var fields = [];
	        var submitTemplate = true;
	        var fieldLabel = '';
	        var myArray = new Array();
	        $('.field').each(function() {

	            var $this = $(this);

	            //field type
	            var fieldType = $this.data('type');

	            //field label
	            fieldLabel = $this.find('.field-label').val();

	            //field required
	            var fieldReq = $this.hasClass('required') ? 1 : 0;
	            
	            
	            if(fieldType == 'checkbox' || fieldType == 'radio' ||  fieldType == 'select'){
	            	if($this.find('.choices li').length == 0) {
	            		submitTemplate = false;
	            		alertMessage("Provide choice for "+fieldLabel)
	            	}
	            }
	            
	           
	            myArray.push(fieldLabel);
	            
	            

	            //check if this field has choices
	            if($this.find('.choices li').length >= 1) {

	                var choices = [];

	                $this.find('.choices li').each(function() {

	                    var $thisChoice = $(this);

	                    //choice label
	                    var choiceLabel = $thisChoice.find('.choice-label').val();

	                    //choice selected
	                    var choiceSel = $thisChoice.hasClass('selected') ? 1 : 0;

	                    if($thisChoice.find('.choice-label').val() != null && $thisChoice.find('.choice-label').val() != '' && $thisChoice.find('.choice-label').val() != 'undefined'){
		                    choices.push({
		                        label: choiceLabel,
		                        sel: choiceSel
		                    });
	                    }

	                });
	            }
	            
	            
	            
	            /* if(fieldLabel == 'First Name'  || fieldLabel == 'Phone Number'  
	        		|| fieldLabel == 'Email Id' || fieldLabel == 'DOB' || fieldLabel == 'Current / Last Company' || fieldLabel == 'Designation' 
	        		|| fieldLabel == 'Education' || fieldLabel == 'Work Experience' || fieldLabel == 'Current CTC' || fieldLabel == 'Expected CTC' 
	        		|| fieldLabel == 'Notice Period'){
	        	
	            	isRemoved = 'N';
	            }else{
	            	isRemoved = 'Y';
	            } */
	            
	            isRemoved = 'Y';
	            
	            fields.push({
	                type: fieldType,
	                label: fieldLabel,
	                req: fieldReq,
	                choices: choices,
	                isRemoved : isRemoved,
	                isLabelModified : isLabelModified
	            });

	        });
	        
	        for (var i = 0; i < myArray.length; i++) 
	        {
	            for (var j = 0; j < myArray.length; j++) 
	            {
	                if (i != j) 
	                {
	                    if (myArray[i].toLowerCase() == myArray[j].toLowerCase()) 
	                    {
	                    	submitTemplate = false;
	                    	alertMessage(myArray[i]+" label duplicated")
	                    }
	                }
	            }
	        }
	        
	        
	        var sourceleadUrl ='${sourceleadUrl}';
	        //Save form to database
	        //Demo doesn't actually save. Download project files for save
	        var con2 = JSON.stringify(fields);
	        var jsonStringData = encodeURIComponent(con2);
	        
	        if(submitTemplate){
	        	
	        	$("#save_checkList_button").attr("disabled", "disabled");
	        	$("#save_continue_checkList_button").attr("disabled", "disabled");
	        	var checkListoffboardId = $("#checkListoffboardId").val();
	        	 $("#offboardcheckListLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	        	  $.ajax({
	      	    	type: "POST",
	      	        url: sourceleadUrl+"/saveCandidateOffboardCheckList?${_csrf.parameterName}=${_csrf.token}&checkListoffboardId="+checkListoffboardId+"&jsonStringData="+jsonStringData, 
	      	        success: function(response) {
	      	        	if (response.response === "AWKWARD") {
	      	        	    location.href = "/errorPage";
	      	        	} else if (response.response === "SUCCESS") {
	      	        		if((response.object == 'home')){
		      	        		 if(saveType == 'saveAndContinue'){
		      		        		var $tabs = $('.tabbable li');
		      		     	        $tabs.filter('.active').next('li').find('a[data-toggle="tab"]').tab('show');
		      		     	        document.getElementById("templateTasks").disabled = false;
		      		     	        $("#offboardTaskDiv").val('Y');
		      		     	        getOffboardingTasksList();
		      		        	}else{
		      		        		getOffboardingCheckList();
		      		        	}
		      		        } 
		      	        	$("#save_checkList_button").removeAttr("disabled");
		    	        	$("#save_continue_checkList_button").removeAttr("disabled");
		      	        	$("#offboardcheckListLoading").removeAttr("style");
	      	        	} else if (response.indexOf('Sign In') !== -1) {
	      	        		location.replace(sourceleadUrl);
	      	        	} else {
	      	        	    showError("Operation failed, please try again");
	      	        	}
	      	        },
	      	    });  
	              return true;
	        } else {
	        	alertMessage("Provide choice for "+fieldLabel)
	        }
	  } else {
		return false;
	  }
}

$('body').on('blur', '#sjfb', function() {
    $("#sjfb").showHelpOnFocus();
    $("#sjfb").validateOnBlur(false, validationSettings);  
});
</script> 



<script type="text/javascript">
function alertMessage(message){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>"+message+"</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' class='nomr' value='Ok' onclick='cancel();'><input class='u_can_color nomr' type='button'  value='Cancel' onClick='cancel();'></b>";
	result = result +"	</div>";
	$("#choicepopUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);

}
</script>

<script type="text/javascript">
function cancel(){
	$("#choicepopUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>




<script>
function getOffboardingTasksList(){
	var offboardTaskDiv = document.getElementById("offboardTaskDiv").value;
	if(offboardTaskDiv == 'Y'){
		var offboardId = document.getElementById("candidateOffBoardId").value;
		var sourceleadUrl = '${sourceleadUrl}';
		var locationId = '${SourceLeadWeb_orgLocationId}';
		if(offboardId != null && offboardId != '' && offboardId!= 'undefined') {
			 $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			 $.ajax({
			   	type: "POST",
			    url: sourceleadUrl+"/getCandidateOffboardTasksList?${_csrf.parameterName}=${_csrf.token}&offboardId="+offboardId,	    	   
			    success: function(response) {
			    	if (response.response === "AWKWARD") {
			    		location.href = "/errorPage";
			    	} else if (response.response === "SUCCESS") {
			    		var json = response.data;
			        	var result = "";
			        	if(json.length > 0){ 
				        	for (var i=0; i<json.length; i++) {
				        		var report = json[i];
				        		var prority = '';
				        		var repeat = '';
				        		if(report.priority != null && report.priority != '' && report.priority != 'undefined'){
				        			prority = report.priority;
				        		}else{
				        			prority = '----';
				        		}
				        		if(report.taskRepeat != null && report.taskRepeat != '' && report.taskRepeat != 'undefined'){
				        			repeat = report.taskRepeat;
				        		}else{
				        			repeat = '----';
				        		}
				        		result = result +'<div class="clearfix taskwidget_inner" id="offboardTask'+report.id+'">';
				        		result = result +'<div class="media-body">';
				        		result = result +'<div class="clearfix taskheder">';
				        		result = result +"<label> <span class='paddingRight'>Subject:</span> <strong class='text-primary'>"+report.taskName+"</strong><a href='#'><i class='fa fa-trash text-danger paddingLeft' onClick='deleteTask("+report.id+",\""+report.offboardTaskType+"\");'></i></a></label>";
				        		result = result +'<label class="small pull-right text-muted"> <span>'+report.dueAfterString+'</span> </label>';
				        		result = result +'</div>';
				        		result = result +'<ul class="list-inline list-description mt10">';
				        		result = result +'<li><span>Assign To:</span>';
				        		if(report.usersList.length > 0){
				        			for(var j=0; j<report.usersList.length; j++){
					 					result = result +'<label>'+report.usersList[j]+'</label>';
					 				}
				        		}else{
				 					result = result +'Offboarding Candidate';
				 				}
				        		result = result +'</li>';
				        		result = result +'<li><span>Priority:</span>'+prority+'</li>';
				        		result = result +'<li><span>Repeat:</span> '+repeat+'</li>';
				        		result = result +'</ul>';
				        		result = result +'</div>';
				        		result = result +'</div>';
				        		if(report.offboardTaskType != 'Candidate Task'){
			       					result = result +"<input type='hidden' name='savedTemplateTask' id='templateTask"+report.id+"' value='"+report.id+"' />";
			       				}
							}
			        	}else{
			        		result = result +'<div><h4>No Tasks Found</h4></div>';
			        	}
			        	result = result +'<div class="form-footer clearfix mt">';
			    		result = result +'<div class="pull-right mr">';
			    		result = result +'<input type="button" value="Send" id="finish_task" onclick="saveAndFinishOffboardTemplate('+offboardId+');" class="btn btn-sm btn-primary">';
			    		result = result +'</div>';
			    		result = result +'</div>';
			    		$("#offboardTasksList").empty();
			    		$("#offboardingCheckList").empty();
			           	$("#offboardTasksList").append(result); 
			    	} else if (response.indexOf('Sign In') !== -1) {
			    		location.replace(sourceleadUrl);
			    	} else {
			    	    showError("Operation failed, please try again");
			    	}
			    }
		   }); 
	  	$("#loading").removeAttr("style");
		}
	}
}
</script>


<script>
function saveAndFinishOffboardTemplate(offboardId) {
	var tasksIds = document.getElementsByName("savedTemplateTask");
	var tasks = '';
	for(var i=0; i < tasksIds.length; i++) {
		tasks = tasks+tasksIds[i].value+",";
	}
	tasks = tasks.substring(0, tasks.length - 1);
	var sourceleadUrl = '${sourceleadUrl}';
	$("#finish_task").attr("disabled", "disabled");
	$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	$.ajax({
	   	type: "POST",
	    url: sourceleadUrl+"/saveAndFinishCandidateOffboard?${_csrf.parameterName}=${_csrf.token}&offboardId="+offboardId+"&tasks="+tasks, 	    	   
	    success: function(response) {
	    	if (response.response === "AWKWARD") {
	    	    location.href = "/errorPage";
	    	} else if (response.response === "SUCCESS") {
	    		$("#finish_task").removeAttr("disabled");
		    	 location.replace(sourceleadUrl+"/candidateOnBoardingList"); 
	    	} else if (response.indexOf('Sign In') !== -1) {
	    		location.replace(sourceleadUrl);
	    	} else {
	    	    showError("Operation failed, please try again");
	    	}
	    }
  });   
}
</script>

  <script>
  function flushOffboardTask(){
	  document.getElementById("addoffboardTask_form").reset();
	  $('#taskMonths').removeAttr('data-validation-optional');
	  $("#taskMonths").attr("data-validation", "required");
	  
	  $('#dueAfterDiv').find('p.jquery_form_error_message').remove();	
	  $('#taskNameDiv').find('p.jquery_form_error_message').remove();
	  
	  document.getElementById("assignToUserDiv").style.display="none";
	  $('#onboardAssignedTo').removeAttr('data-validation');
	  $("#onboardAssignedTo").attr("data-validation-optional", "true");
	  $('#assignToUserDiv').find('p.jquery_form_error_message').remove();
  }
  </script>
  
  <script>
function deleteTask(taskId,taskType){
	if(taskType != 'Candidate Task'){
		document.getElementById("offboardTask"+taskId).remove();
		document.getElementById("templateTask"+taskId).remove()
	} else {
		var sourceleadUrl ='${sourceleadUrl}';
		   $.ajax({
		    	type: "POST",
		    	 url: sourceleadUrl+"/deleteOffboardTaskByTaskId?${_csrf.parameterName}=${_csrf.token}&taskId="+taskId, 
		        success: function(response) {
		        	if (response && response.response === "INVALID_DATA") {
		        	    showError(response.responseMessage);
		        	} else if (response.response === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (response.response === "SUCCESS") {
		        		document.getElementById("offboardTask"+taskId).remove();
		        	} else if (response.indexOf('Sign In') !== -1) {
		        		location.replace(sourceleadUrl);
		        	} else {
		        	    showError("Operation failed, please try again");
		        	}
		        },
		   });
	}
}
</script>


<script>
  function validateAssignToType(assignToType){
	  if(assignToType == 'assingToUser'){
		  document.getElementById("assignToUserDiv").style.display="";
		  $('#offboardAssignedTo').removeAttr('data-validation-optional');
		  $("#offboardAssignedTo").attr("data-validation", "required");
		 
	  }else{
		  document.getElementById("assignToUserDiv").style.display="none";
		  $('#offboardAssignedTo').removeAttr('data-validation');
		  $("#offboardAssignedTo").attr("data-validation-optional", "true");
		  getAssignToUsersList();
	  }
  }
  </script>
  
  
  <script>
  function validateDueAfter(){
	  
	  var taskMonths = document.getElementById("taskMonths").value;
	  var taskDays = document.getElementById("taskDays").value;
	  /* var taskHours = document.getElementById("taskHours").value;
	  var taskMinutes = document.getElementById("taskMinutes").value; */
	  
	  
	  if(taskMonths == '' && taskDays == '' ){
			  $('#taskMonths').removeAttr('data-validation-optional');
			  $("#taskMonths").attr("data-validation", "required");
		    
	  }else{
		  $('#taskMonths').removeAttr('data-validation');
		  $("#taskMonths").attr("data-validation-optional", "true");
		    
	  }  
	  
  }
  </script>

<script>
function getAssignToUsersList(){
	var candidateOffBoardId = document.getElementById("candidateOffBoardId").value;
    $("#candidateOffboardTaskId").val(candidateOffBoardId);
    
	var sourceleadUrl = '${sourceleadUrl}';
	$.ajax({
	   	type: "POST",
	    url: sourceleadUrl+"/getOnboardTasksAssignToUsersList?${_csrf.parameterName}=${_csrf.token}",	    	   
	    success: function(response) {
	    	if (response.response === "AWKWARD") {
	    	    location.href = "/errorPage";
	    	} else if (response.response === "SUCCESS") {
	    		var result = "";
	        	 var json = response.data;
	        	for (var i=0; i<json.length; i++) {
	        		var report = json[i];
	        		result = result +'<option value='+report.userId+' >'+report.userName+'</option>';
				}
	        	 $("#offboardAssignedTo").empty();
	           	 $("#offboardAssignedTo").append(result); 
	           	 $('#offboardAssignedTo').trigger("chosen:updated");
	    	} else if (response.indexOf('Sign In') !== -1) {
	    		location.replace(sourceleadUrl);
	    	} else {
	    	    showError("Operation failed, please try again");
	    	}
	    }
   }); 
}
</script>


<script>
function saveOffboardTask(){
	var validationSettings = {
	        errorMessagePosition : 'element',
	       borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
	}; 
	  if ($('#addoffboardTask_form').validate(false, validationSettings)) {
	        var sourceleadUrl ='${sourceleadUrl}';
	        var tasksIds = document.getElementsByName("savedTemplateTask");
	    	var tasks = '';
	    	for(var i=0; i < tasksIds.length; i++){
	    		tasks = tasks+tasksIds[i].value+",";
	    	}
	    	tasks = tasks.substring(0, tasks.length - 1);
	    	var formData = getFormData1("addoffboardTask_form");
	        $("#offboardtasksLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	        $.ajax({
		    	type: "POST",
		    	data: formData,
		    	processData: false,
		        contentType:false, 
		    	url: sourceleadUrl+"/saveCandidateOffBoardTasks?${_csrf.parameterName}=${_csrf.token}&tasks="+tasks,
		        success: function(response) {
		        	if (response.response === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (response.response === "SUCCESS") {
		        		$("#offboardtasksLoading").removeAttr("style");
			        	flushOffboardTask();
			        	getAssignToUsersList();
			        	getOffboardingTasksList();
			        	document.getElementById("selector").style.display = 'block';
			        	$("#selector").fadeIn(150);
			        	$('#selector').delay(1000).fadeOut('slow');
		        	} else if (response.indexOf('Sign In') !== -1) {
		        		location.replace(sourceleadUrl);
		        	} else {
		        	    showError("Operation failed, please try again");
		        	}
		        },
		    });  
	        return true;
	  } else {
		return false;
	  }
}
$('body').on('blur', '#addoffboardTask_form', function() {
	$("#addoffboardTask_form").showHelpOnFocus();
	$("#addoffboardTask_form").validateOnBlur(false, validationSettings);  
});
</script>

<script>
function getEndDate(){
	var effectiveDate = document.getElementById("effectiveDate").value;
	if(effectiveDate){
		 var noticePeriod = document.getElementById("noticePeriod").value;
		 if(noticePeriod && noticePeriod != 'Relieve Immediately'){
			 var sourceleadUrl ='${sourceleadUrl}';
			 var dateFormat ='${SourceLeadWeb_orgLocations.dateFormat}';
			 $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			    $.ajax({
					type : "GET",
					url: sourceleadUrl+"/getEndDateByEffectiveDateAndNoticePeriod?${_csrf.parameterName}=${_csrf.token}&noticePeriod="+noticePeriod+"&effectiveDate="+effectiveDate+"&dateFormat="+dateFormat,
					success :function(response) {
						if (response.response === "AWKWARD") {
						    location.href = "/errorPage";
						} else if (response.response === "SUCCESS") {
							$("#endDate").val(endDateStr);
							$("#loading").removeAttr("style");
							addValidClass();
						} else if (response.indexOf('Sign In') !== -1) {
							location.replace(sourceleadUrl);
						} else {
						    showError("Operation failed, please try again");
						}
					}
				});
		 } else {
			 $("#endDate").val(effectiveDate);
			 addValidClass();
		 }
	}
}
</script>
<div class="backtotop" style="display: none;"><img src="/resources/images/backtotop.png"></div>  
<script>
function scrollToBottom(){
	var documentHeight = document.documentElement.offsetHeight;
	var viewportHeight = window.innerHeight;
	window.scrollTo(0,documentHeight-viewportHeight);
}
</script>
  <script>
// Back To Top 
    $(window).scroll(function() {
        if ($(this).scrollTop() > 200) {
            $('.backtotop').fadeIn('slow');
        } else {
            $('.backtotop').fadeOut('slow');
        }
    });
    $('.backtotop').click(function() {
        $("html, body").animate({scrollTop: 0}, 500);
        return false;
    });
</script>

