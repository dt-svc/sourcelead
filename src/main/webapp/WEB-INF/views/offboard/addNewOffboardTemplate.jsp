<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/formvalidator.js"></script>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<link rel="stylesheet" href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/style.css" />

<script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/offboardCheckListBuilder.js" type="text/javascript" ></script> <!-- form builder -->
<script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/offboardCheckList-html-generator.js" type="text/javascript" ></script> <!-- form generator -->

<script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/formvalidator.js"></script>

<section class="content-wraper">
    <div id="loading"></div>
    <section class="content-header clearfix">
          <div class="pull-left">
            <h2>Offboard Template</h2>
          </div>
          <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo; <a href="${SourceLeadWeb_sourceleadUrl}/offboardingTemplatesList'/>">Offboard Templates List</a> &raquo; Add Offboard Template </div>
    </section>
    
    <div class="content-area content-new clearfix">
      <div class="applicant_accept">
        <div class="tabbable">
          <ul class="nav nav-tabs" id="tab_bar">
            <li class="active" onclick="getTemplateDetails();"> <a href="#details" data-toggle="tab"> <i class="fa fa-th-list paddingRight"></i> <span>Details</span> <br>
              <small>Template details</small> </a> </li>
            <li onclick="getTemplateResignationDocumentsDetails();getTemplateOffboardPackDocumentsDetails();"><a href="#documents" data-toggle="tab" id="templateDocs"> <i class="fa fa-files-o paddingRight"></i> <span>Documents</span> <br>
              <small>Resignation / Termination &amp; Offboard pack Documents</small> </a> </li>
            <li onclick="getOffboardingCheckList();"> <a href="#offboardCheckList" data-toggle="tab" id="checkList"> <i class="fa fa-check-square-o paddingRight"></i> <span>Check List</span> <br>
              <small>Check List</small> </a></li>
            <li onclick="getOffboardingTasksList();"> <a href="#tasks" data-toggle="tab" id="templateTasks"> <i class="fa fa-check-square-o paddingRight"></i> <span>Tasks</span> <br>
              <small>List of tasks</small> </a></li>
          </ul>
          <input type="hidden" name="templateId" id="templateId" value=""/>
          <input type="hidden" id="offboardTaskDiv" value="N"/>
          <div class="tab-content"> 
            
            <!-- Employee Details -->
            <div class="tab-pane active" id="details">
              <div class="form-horizontal assignTemplate">
                <h4 class="clearfix">Template Details</h4>
                <form:form class="login-form clearfix"  id="offboardTemplate_form"  name="offboardTemplate_form" method="post" > 
                <div class="form-group">
                  <div class="col-md-6" id="templateNameDiv">
                    <label>Template Name <span class="text-danger paddingLeft">*</span></label>
                    <input type="text" class="form-control input-sm" id="templateName" name="templateName" onblur="validateTemplateName();" field-name="Template Name" data-validation="required validate_Space validate_length length2-100">
                    <input type="hidden" name="templateId" id="offboardtemplateId" value=""/>
                  </div>
                  <div class="col-md-6">
                    <label>Description <span class="text-danger paddingLeft">*</span></label>
                    <textarea rows="3" class="form-control input-sm" name="description" field-name="Description" data-validation="required validate_length length2-500"></textarea>
                  </div>
                </div>
                <div class="form-footer clearfix mt">
                  <div class="pull-right mr">
                    <input type="button" value="Cancel" class="btn btn-sm btn-default" onClick="self.location='${SourceLeadWeb_sourceleadUrl}/offboardingTemplatesList'">
                    <input type="button" value="Save" id="save_button"  onclick="createTemplate('save');" class="btn btn-sm btn-success">
                    <input type="button" value="Save and Continue" id="save_continue_button" onclick="createTemplate('saveAndContinue');" class="btn btn-sm btn-primary">
                  </div>
                </div>
                </form:form>
              </div>
            </div>
            
            <!-- TAB 2 -->
            <div class="tab-pane" id="documents">
              <div class="form-horizontal">
                <form id='templateDocuments_form' name='templateDocuments_form' >
                 <input type="hidden" name="templateId" id="documentstemplateId" value=""/>
               
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
                  <label class="col-md-12"> <strong>Offboard Pack</strong> <br>
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
                     <input type="button" value="Cancel" class="btn btn-sm btn-default" onClick="self.location='/app/offboardingTemplatesList'">
                    <input type="button" value="Save" id="save_doc_button"  onclick="createTemplateDocs('save');" class="btn btn-sm btn-success ml mr">
                    <input type="button" value="Save and Continue" id="save_continue_doc_button" onclick="createTemplateDocs('saveAndContinue');" class="btn btn-sm btn-primary">
                  </div>
                </div>
                </form>
              </div>
            </div>
            
            
            
             <!-- Check List -->
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
                  <button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#addTask" onclick="getAssignToUsersList();"><i class="fa fa-plus paddingRight"></i>Add Task</button>
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
                             <input type="hidden" name="templateId" id="taskstemplateId" value=""/>
                              <div class="row">
                              
                              <div class="col-md-6">
                                <div class="row">
                                
                                <div class="col-md-12 mb10" id="taskNameDiv">
                                  <label>Subject <span class="text-danger paddingLeft">*</span></label>
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
                                  <label>Assign To <span class="text-danger paddingLeft">*</span></label>
                                  
                                  <div class="">
                                    <label class="paddingRight">
                                      <input type="radio"  name="assignedToType" checked="checked" onclick="validateAssignToType('assingToCandidate');">
                                      Offboard User </label>
                                    <label class="paddingLeft">
                                      <input type="radio" name="assignedToType"  onclick="validateAssignToType('assingToUser');">
                                      Associate </label>
                                  </div>
                                  
                                  <div id="assignToUserDiv" style="display: none;" class="mt10">
	                                  <select class="chosen-select" multiple data-placeholder="Select User" id="onboardAssignedTo" name="onboardAssignedTo" field-name="AssignTo"  data-validation-optional="true" >
	                                  </select>
                                  </div>
                                </div>
	                                
	                                
	                               <div class="col-md-12 mb10">
                                  <div class="row">
                                    <div class="col-md-12">
                                      <label><strong>Due date:</strong><span class="text-danger paddingLeft">*</span></label>
                                                                            
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
                                      
                                      <!-- Form inline Due after --> 
                                      
                                    </div>
                                    
                                  </div>
                                </div>
	                               <div class="clearfix mb10">
                                  <label>Description</label>
                                  <textarea rows="2" placeholder="Description" id="taskdescription" name="taskdescription" class="form-control"></textarea>
                                </div>
	                                
	                                </div>
                                </div>
                                
                                <div class="clearfix"></div>
                                <div class="clearfix mt col-md-12 form-footer">
                                  <div class="pull-right">
                                    <input type="button" id="onboardtask_submit" class="btn btn-primary btn-sm" onclick="saveOffboardTask();"  value="Save">
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
          </div>
          <!-- STYLED IN FORMS.CSS --> 
        </div>
      </div>
    </div>
  </section>
  
  
  <script>
  function validateTaskEmail(){
	  var taskEmail = document.getElementById("taskEmail").checked;
	  if(taskEmail == 1){
		  document.getElementById("taskEmail").value = 'Y';
	  }else{
		  document.getElementById("taskEmail").value = 'N';
	  }
	  
  }
  </script>
  
  <script>
  function validateTaskNotification(){
	  var taskEmail = document.getElementById("taskNotification").checked;
	  if(taskEmail == 1){
		  document.getElementById("taskNotification").value = 'Y';
	  }else{
		  document.getElementById("taskNotification").value = 'N';
	  }
	  
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
  function validateAssignToType(assignToType){
	  if(assignToType == 'assingToUser'){
		  document.getElementById("assignToUserDiv").style.display="";
		  $('#onboardAssignedTo').removeAttr('data-validation-optional');
		  $("#onboardAssignedTo").attr("data-validation", "required");
		 
	  }else{
		  document.getElementById("assignToUserDiv").style.display="none";
		  $('#onboardAssignedTo').removeAttr('data-validation');
		  $("#onboardAssignedTo").attr("data-validation-optional", "true");
		  getAssignToUsersList();
	  }
  }
  </script>
  
  
  <script>
  function validateDueAfter(){
	  var taskMonths = document.getElementById("taskMonths").value;
	  var taskDays = document.getElementById("taskDays").value;
	  
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
	$(document).ready(function(){
		document.getElementById("templateDocs").disabled = true;
		document.getElementById("templateTasks").disabled = true;
		document.getElementById("checkList").disabled = true;
		
		
		var templateId = '${templateId}';
		if(templateId != null && templateId != '' && templateId != 'undefined'){
			$("#templateId").val(templateId);
  	        $("#offboardtemplateId").val(templateId);
  	        $("#documentstemplateId").val(templateId);
  	        
  	      document.getElementById("templateDocs").disabled = false;
  	      document.getElementById("templateTasks").disabled = false;
  	      document.getElementById("checkList").disabled = false;
  	      $("#offboardTaskDiv").val('Y');
		}
		
		getTemplateDetails();
	});
</script>


<div id="divResignationAttachmentTd1" style="display: none; float: left;">
			<div id='REPLACEROWIDResignationdocFileuploadFileGroup'>
	  			<input type='file'  id='REPLACEROWIDresignationdocFile' class="timesheetAttachmentValidation"  data-validation="required validatedoc" field-name="Document" onchange='uploadResignationTerminationDoc(REPLACEROWID)'> 
	  			<i class="fa fa-times" onclick="removeResignationAttachmentRow(REPLACEROWID);"></i>
	  		</div>
</div>

<div id="divOffboardPackAttachmentTd1" style="display: none; float: left;">
			<div id='REPLACEROWIDOffboardPackdocFileuploadFileGroup'>
	  			<input type='file'  id='REPLACEROWIDoffboardPackdocFile' class="timesheetAttachmentValidation" data-validation="required validatedoc"  field-name="Document" onchange='uploadOffboardPackDoc(REPLACEROWID)'> 
	  			<i class="fa fa-times" onclick="removeOffboardPackAttachmentRow(REPLACEROWID);"></i>
	  		</div>
</div>




<script>
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
	fileUpload(""+id+"resignationdocFile", "Offboard-Template-Resignation-Documents", "templateDocuments_form","resignationFileInfo");
}
function uploadOffboardPackDoc(id){
	fileUpload(""+id+"offboardPackdocFile", "Offboard-Template-OffboardPack-Documents", "templateDocuments_form","offboardPackFileInfo");
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



<!-- <script type="text/javascript">
function validateResignationUploadFile(id)
{
	 var selectedFile = $('#'+id+'resignationdocFile').val();
	  if(validate_fileupload(selectedFile)){
		   var docSize = $('#'+id+'resignationdocFile')[0].files[0].size;
	       $("#"+id+"ResignationdocFileuploadFileGroup").find('p.jquery_form_error_message').remove();
	      if(docSize > 2097152){
	    	  $('#'+id+'ResignationdocFileuploadFileGroup').find('p.jquery_form_error_message').remove();
	    	  $('#'+id+'ResignationdocFileuploadFileGroup').find('i').after("<p class='jquery_form_error_message'>Limit is exceeded than 2MB </span>");
			  $('#'+id+'resignationdocFile').attr('record-exist','yes');
			  $('#'+id+'resignationdocFile').attr('record-exist-errorMsg','Limit is exceeded than 2MB'); 
	      }else{
	    	 $('input#'+id+'resignationdocFile').removeAttr( "record-exist record-exist-errormsg");
   		 $('#'+id+'ResignationdocFileuploadFileGroup').find('p.jquery_form_error_message').remove(); 
	      }
	  }
	  else{
		      $('#'+id+'ResignationdocFileuploadFileGroup').find('p.jquery_form_error_message').remove();
		      $('#'+id+'ResignationdocFileuploadFileGroup').find('i').after("<p class='jquery_form_error_message'>Invalid Format</span>");
			  $('#'+id+'resignationdocFile').attr('record-exist','yes');
			  $('#'+id+'resignationdocFile').attr('record-exist-errorMsg','Invalid Format'); 
	  }
	
}
</script> -->



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

<script>
function getAssignToUsersList(){
	var templateId = document.getElementById("templateId").value;
    $("#taskstemplateId").val(templateId);
    
	
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
	        	 $("#onboardAssignedTo").empty();
	           	 $("#onboardAssignedTo").append(result); 
	           	 $('#onboardAssignedTo').trigger("chosen:updated");
	    	} else if (response.indexOf('Sign In') !== -1) {
	    		location.replace(sourceleadUrl);
	    	} else {
	    	    showError("Operation failed, please try again");
	    	}
	    }
   }); 
}
</script>

<script type="text/javascript">
function validate_fileupload(fileName) {
    var allowed_extensions = new Array("ppt", "pptx", "pps", "ppsx", "doc", "docx", "pdf", "rtf", "odt", "xls", "xlsx", "csv", "zip", "txt", "jpg","jpeg","bmp","png","gif","eps","tiff","TGA","psd","svg","msg","wpd","wps");
    var file_extension = fileName.split('.').pop(); 
    for(var i = 0; i <= allowed_extensions.length; i++) {
        if(allowed_extensions[i] != null && allowed_extensions[i] != '' && allowed_extensions[i] != 'undefined' && allowed_extensions[i]==file_extension.toLowerCase()) {
            return true; // valid file extension
        }
    }
    return false;
}
</script>

<script>
function createTemplate(saveType) {
	var validationSettings = {
	        errorMessagePosition : 'element',
	       borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
         }; 
	
	  if ($('#offboardTemplate_form').validate(false, validationSettings)) { 
		    
		  var atsUrl ='${SourceLeadWeb_atsUrl}';
	        
	    	$("#save_button").attr("disabled", "disabled");
    		$("#save_continue_button").attr("disabled", "disabled");
    		var formData = getFormData("offboardTemplate_form");
	        $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	        $.ajax({
		    	type: "POST",
		    	data: formData,
		    	 url: atsUrl+"/createNewOffBoardingTemplate?${_csrf.parameterName}=${_csrf.token}", 
		        success: function(response) {
		        	if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
	        		    showError(response.responseMessage);
	        	  	}else if (response.response === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (response.response === "SUCCESS") {
		        		var json = response.object;
			        	if(saveType == 'saveAndContinue'){
			        		var $tabs = $('.tabbable li');
			     	        $tabs.filter('.active').next('li').find('a[data-toggle="tab"]').tab('show');
			     	        document.getElementById("templateDocs").disabled = false;
			     	       	getTemplateResignationDocumentsDetails(json.id);
			     	       	getTemplateOffboardPackDocumentsDetails(json.id);
			        	}
		     	        $("#templateId").val(json.id);
		     	        $("#offboardtemplateId").val(json.id);
		     	        $("#documentstemplateId").val(json.id);
		     	        $("#loading").removeAttr("style");		     	        
		     	        $("#save_button").removeAttr("disabled");
		        	    $("#save_continue_button").removeAttr("disabled");
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

$('body').on('blur', '#offboardTemplate_form', function() {
	$("#offboardTemplate_form").showHelpOnFocus();
	$("#offboardTemplate_form").validateOnBlur(false, validationSettings);  
});
</script>

<script>
function createTemplateDocs(saveType){
	var validationSettings = {
	        errorMessagePosition : 'element',
	       borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
    }; 
	
	var atsUrl ='${SourceLeadWeb_atsUrl}';
	  alert("docs"+atsUrl)
	if ($('#templateDocuments_form').validate(false, validationSettings)) {
		$("#save_doc_button").attr("disabled", "disabled");
		$("#save_continue_doc_button").attr("disabled", "disabled");
		var formData = getFormData("templateDocuments_form");
	   	$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		$.ajax({
	        type : "POST",
		      data: formData,
	    	  url: atsUrl+"/saveOffboardingTemplateDocuments?${_csrf.parameterName}=${_csrf.token}",		
		      success :function(response) {
		    	  	if (response.response === "AWKWARD") {
		    		    location.href = "/errorPage";
		    		} else if (response.response === "SUCCESS") {
		    			if(saveType == 'saveAndContinue'){
			        		var $tabs = $('.tabbable li');
			     	        $tabs.filter('.active').next('li').find('a[data-toggle="tab"]').tab('show');
			     	        document.getElementById("checkList").disabled = false;
			     	        getOffboardingCheckList();
			        	} else{
			        		getTemplateResignationDocumentsDetails();
			     	        getTemplateOffboardPackDocumentsDetails();
			        	}
			    	  
			    	  $("#save_doc_button").removeAttr("disabled");
		      	      $("#save_continue_doc_button").removeAttr("disabled");
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
function saveOffboardTask(){
	
	var validationSettings = {
	        errorMessagePosition : 'element',
	       borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
         }; 
	var atsUrl ='${SourceLeadWeb_atsUrl}';
	  if ($('#addoffboardTask_form').validate(false, validationSettings)){ 
	        var sourceleadUrl ='${sourceleadUrl}';
	        var formData = getFormData1("addoffboardTask_form");
	        $("#offboardtasksLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	        $.ajax({
		    	 type: "POST",
		    	 data: formData,
		    	 processData: false,
			     contentType:false,
		    	 url: atsUrl+"/saveOffBoardTasks?${_csrf.parameterName}=${_csrf.token}", 
		        success: function(response) {
		        	if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
	        		    showError(response.responseMessage);
	        		    $("#offboardtasksLoading").removeAttr('style');
	        	  	}else  if (response.response === "AWKWARD") {
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


	

<script type="text/javascript">
(function ($) {
   $.fn.serializeFormJSON = function () {

       var o = {};
       var a = this.serializeArray();
       $.each(a, function () {
           if (o[this.name]) {
               if (!o[this.name].push) {
                   o[this.name] = [o[this.name]];
               }
               o[this.name].push(this.value || '');
           } else {
               o[this.name] = this.value || '';
           }
       });
       return o;
   };
})(jQuery);
</script>

<script type="text/javascript">
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };

        
</script> 


<script>
function getTemplateDetails(){
	var templateId = document.getElementById("templateId").value;
	if(templateId){
		var sourceleadUrl ='${sourceleadUrl}';
		var atsUrl ='${SourceLeadWeb_atsUrl}';
        $.ajax({
	    	type: "POST",
	    	 url: atsUrl+"/getOffboardTemplateById?${_csrf.parameterName}=${_csrf.token}&templateId="+templateId, 
	        success: function(response) {
	        	if (response.response === "AWKWARD") {
	        	    location.href = "/errorPage";
	        	} else if (response.response === "SUCCESS") {
	        		var json = response.object;
		        	 $("input[name=templateName]").val(json['templateName']);
		        	 $("textarea[name=description]").val(json['description']);
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
function getTemplateResignationDocumentsDetails(templateId){
	var atsUrl ='${SourceLeadWeb_atsUrl}';
	$("#templateDocuments_form input[name*='resignationFileInfo']").remove();
	$("#templateDocuments_form input[name*='offboardPackFileInfo']").remove();
	
	if(templateId == '' || templateId == null || templateId =='undefined'){
		templateId = document.getElementById("templateId").value;
	}
	var documentType = "Resignation"
	if(templateId){
		var sourceleadUrl ='${sourceleadUrl}';
	     $.ajax({
		    	type: "POST",
		    	 url: atsUrl+"/getOffboardTemplateDocumentByTemplateId?${_csrf.parameterName}=${_csrf.token}&templateId="+templateId+"&documentType="+documentType, 
		        success: function(response) {
		        	if (response.response === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (response.response === "SUCCESS") {
		        		 var json = response.data;
		        		 var result = "";
	                	 for (var i = 0; i < json.length; i++) { 
	                		 var opt = json[i]; 
	                		 result = result +"<strong id='resignationdocument"+opt.id+"'>";
	                		 result = result +"<i class=''><a class='glyphicon glyphicon-save' target='_blank'  href=\""+opt.fileURL+"\"></a></i>";
	           				 result = result +"<input type='button' value='"+opt.fileName+"'>";
	           				 result = result +"</a>";
	           				 result = result +"<input class='dele' type='button' onClick='deleteOffBoardingDocument(\""+opt.documentUUID+"\","+opt.id+",\""+documentType+"\");' >";
	           				 result = result +"</strong>";
	                	 }
	                	 result = result +"<input type='hidden' name='resignationAttcahmentMaxRowNumber'  id='resignationAttcahmentMaxRowNumber' value='1' />";
	                	 result = result +"<table class='timesheetAttachment' id='resignationAttachmentTable' cellpadding='0' cellspacing='0'>";
	                	 result = result +"<tr id='divAttachmentTsRow0'>";
	                	 result = result +"<td><div id='0ResignationdocFileuploadFileGroup'><input type='file' id='0resignationdocFile' field-name='Attachment' data-validation='validatedoc' data-validation-optional='true' onchange='uploadResignationTerminationDoc(0)'  class='timesheetAttachmentValidation'><i class='fa fa-plus' onClick='addResignationAttachment()'></i></div></td>";
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
function getTemplateOffboardPackDocumentsDetails(templateId){
	var atsUrl ='${SourceLeadWeb_atsUrl}';
	$("#templateDocuments_form input[name*='resignationFileInfo']").remove();
	$("#templateDocuments_form input[name*='offboardPackFileInfo']").remove();
	if(templateId == '' || templateId == null || templateId =='undefined'){
		templateId = document.getElementById("templateId").value;
	}
	var documentType = "Offboard Pack"
	if(templateId){
		var sourceleadUrl ='${sourceleadUrl}';
	     $.ajax({
		    	type: "POST",
		    	 url: atsUrl+"/getOffboardTemplateDocumentByTemplateId?${_csrf.parameterName}=${_csrf.token}&templateId="+templateId+"&documentType="+documentType, 
		        success: function(response) {
		        	if (response && response.response === "INVALID_DATA") {
		        	    showError(response.responseMessage);
		        	} else if (response.response === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (response.response === "SUCCESS") {
		        		 var result = "";
			        	 var json = response.data;
	                	 for (var i = 0; i < json.length; i++) { 
	                		 var opt = json[i]; 
	                		 result = result +"<strong id='offboardPackdocument"+opt.id+"'>";
	                		 result = result +"<i class=''><a class='glyphicon glyphicon-save'  target='_blank' href=\""+opt.fileURL+"\"></a></i>";
	           				 result = result +"<input type='button' value='"+opt.fileName+"'>";
	           				 result = result +"</a>";
	           				 result = result +"<input class='dele' type='button' onClick='deleteOffBoardingDocument(\""+opt.documentUUID+"\","+opt.id+",\""+documentType+"\");' >";
	           				 result = result +"</strong>";
	                	 }
	                	 result = result +"<input type='hidden' name='offboardPackAttcahmentMaxRowNumber'  id='offboardPackAttcahmentMaxRowNumber' value='1' />";
	                	 result = result +"<table class='timesheetAttachment' id='offboardPackAttachmentTable' cellpadding='0' cellspacing='0'>";
	                	 result = result +"<tr id='divAttachmentTsRow0'>";
	                	 result = result +"<td><div id='0OffboardPackdocFileuploadFileGroup'><input type='file' data-validation='validatedoc' data-validation-optional='true' id='0offboardPackdocFile' field-name='Attachment' onchange='uploadOffboardPackDoc(0)'  class='timesheetAttachmentValidation'><i class='fa fa-plus' onClick='addOffboardPackAttachment();'></i></div></td>";
	                	 result = result +"</tr>";
	                	 result = result +"</table>";
	                	 $("#savedOffboardPackAttachments").empty();
	                	 $("#savedOffboardPackAttachments").append(result);		        		
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
function deleteOffBoardingDocument(documentUUID,documentId,documentType){
	var atsUrl ='${SourceLeadWeb_atsUrl}';
   	$.ajax({
    	type: "POST",
    	url: atsUrl+"/deleteOffBoardingDocumentByDocumentUUID?${_csrf.parameterName}=${_csrf.token}&documentUUID="+documentUUID, 
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
function validateTemplateName(){
	var templateId = document.getElementById("templateId").value;
	var templateName = document.getElementById("templateName").value;
	var atsUrl ='${SourceLeadWeb_atsUrl}';
	if(templateName){
		
		$.ajax({
	    	type: "POST",
	    	url : atsUrl+"/validateOffboardingTemplateName?${_csrf.parameterName}=${_csrf.token}&templateName="+templateName+"&templateId="+templateId, 
	        success: function(response) {
	        	if (response.response === "AWKWARD") {
	        	    location.href = "/errorPage";
	        	} else if (response.response === "SUCCESS") {
		        	if (response.object == 'invalid') {
		        		$("#save_button").attr("disabled", "disabled");
		        		$("#save_continue_button").attr("disabled", "disabled");
		        		$('#templateNameDiv').find('p.jquery_form_error_message').remove();
		        		$("input#templateName").after( "<p class='jquery_form_error_message'>" + templateName + " is Already Existed</span>");
						document.getElementById("templateName").setAttribute( 'record-exist', 'yes');
						document.getElementById("templateName").setAttribute( 'record-exist-errorMsg', templateName +"  already existed");
		        	} else {
		        		$('input#templateName').removeAttr("class record-exist record-exist-errormsg");
						$('input#templateName').attr('class','valid form-control');
						$('#templateNameDiv').find('p.jquery_form_error_message').remove();
						
		        		$("#save_button").removeAttr("disabled")
		        		$("#save_continue_button").removeAttr("disabled")
		        	}
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
function getOffboardingTasksList(){
	var atsUrl ='${SourceLeadWeb_atsUrl}';
	var offboardTaskDiv = document.getElementById("offboardTaskDiv").value;
	if(offboardTaskDiv == 'Y'){
	
		var templateId = document.getElementById("templateId").value;
		
		var locationId = '${SourceLeadWeb_orgLocationId}';
		
		 $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		$.ajax({
		   	type: "POST",
		    url: atsUrl+"/getOffboardTasksListByTemplateId?${_csrf.parameterName}=${_csrf.token}&templateId="+templateId,	    	   
		    success: function(response) {
		    	if (response.response === "AWKWARD") {
		    	    location.href = "/errorPage";
		    	} else if (response.response === "SUCCESS") {
		    		var json = response.data;
		        	var result = "";
		        	if(json.length > 0) { 
			        	for (var i=0; i<json.length; i++) {
			        		var report = json[i];
			        		var prority = '';
			        		var repeat = '';
			        		if(report.priority != null && report.priority != '' && report.priority != 'undefined'){
			        			prority = report.priority;
			        		} else {
			        			prority = '----';
			        		}
			        		
			        		if(report.taskRepeat != null && report.taskRepeat != '' && report.taskRepeat != 'undefined'){
			        			repeat = report.taskRepeat;
			        		} else {
			        			repeat = '----';
			        		}
			        		
			        		result = result +'<div class="clearfix taskwidget_inner" id="onboardTask'+report.id+'">';
			        		result = result +'<div class="media-body">';
			        		result = result +'<div class="clearfix taskheder">';
			        		result = result +'<label> <span class="paddingRight">Subject:</span> <strong class="text-primary">'+report.taskName+'</strong><a href="#"><i class="fa fa-trash text-danger paddingLeft" onClick="deleteTask('+report.id+');"></i></a></label>';
			        		result = result +'<label class="small pull-right text-muted"> <span>'+report.dueAfterString+'</span> </label>';
			        		result = result +'</div>';
			        		result = result +'<ul class="list-inline list-description mt10">';
			        		
			        		result = result +'<li><span>Assign To:</span>';
			        		
			        		if(report.usersList.length > 0){
			        			for(var j=0; j<report.usersList.length; j++){
				 					result = result +'<label>'+report.usersList[j]+'</label>';
				 				}
			        		} else {
			 					result = result +'Offboarding Candidate';
			 				}
			        		result = result +'</li>';
			        		
			        		result = result +'<li><span>Priority:</span>'+prority+'</li>';
			        		result = result +'<li><span>Repeat:</span> '+repeat+'</li>';
			        		result = result +'</ul>';
			        		result = result +'</div>';
			        		result = result +'</div>';
						}
		        	} else {
		        		result = result +'<div><h4>No Tasks Found</h4></div>';
		        	}
		        	
		        	result = result +'<div class="form-footer clearfix mt">';
		    		result = result +'<div class="pull-right mr">';
		    		result = result +'<input type="button" value="Cancel" id="finish_task" onClick="redirect();" class="btn btn-sm btn-default mr">';
		    		result = result +'<input type="button" value="Finish" id="finish_task" onclick="saveAndFinishOffboardTemplate('+templateId+');" class="btn btn-sm btn-primary">';
		    		result = result +'</div>';
		    		result = result +'</div>';
		    		
		    		$("#offboardTasksList").empty();
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
function redirect(){
	window.location.href = atsUrl +  "/offboardingTemplatesList";
}
</script>



<script>
function deleteTask(taskId){
	
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
	        		document.getElementById("onboardTask"+taskId).remove();
	        		if($("#offboardTasksList div[id*=onboardTask]").length === 0) {
	        			$("#offboardTasksList").prepend("<div><h4>No Tasks Found</h4></div>");
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
function saveAndFinishOffboardTemplate(templateId){
	
	var atsUrl ='${SourceLeadWeb_atsUrl}';
   $("#finish_task").attr("disabled", "disabled");
	$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	$.ajax({
	   	type: "POST",
	    url: atsUrl+"/saveAndFinishOffboardTemplate?${_csrf.parameterName}=${_csrf.token}&templateId="+templateId,	    	   
	    success: function(response) {
	    	if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
    		    showError(response.responseMessage);
    	  	}else if (response.response === "AWKWARD") {
	    	    location.href = "/errorPage";
	    	} else if (response.response === "SUCCESS") {
	    		$("#finish_task").removeAttr("disabled");
		    	$("#loading").removeAttr("style");
		    	location.replace(atsUrl+"/offboardingTemplatesList"); 
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
function getOffboardingCheckList(){
	var atsUrl ='${SourceLeadWeb_atsUrl}';
	var result = "";
	var templateId = document.getElementById("templateId").value;
	var sourceleadUrl ='${sourceleadUrl}';
	var locationId = '${SourceLeadWeb_orgLocationId}';
	
	
	result = result + '<div class="clearfix">';
	        result = result + '</div>';
	        result = result + '<div id="sjfb-wrap">';
	        result = result + '<div id="offboardcheckListLoading"></div>';
	           result = result + '<form id="sjfb" novalidate>';
	           result = result + '<input type="hidden" id="checkListTemplateId" name="checkListTemplateId"  value="'+templateId+'">';
	           
		    	
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
	           result = result + '<div class="form-footer clearfix  mt">';
	           result = result + '<div class="clearfix"></div>';
	           result = result + '<div class="pull-right mr">';
	           
	           result = result +"<input type='button' value='Save' id='save_checkList_button'  onclick='createTemplateCheckList(\"save\");' class='btn btn-sm btn-success'>";
	           result = result +"<input type='button' value='Save and Continue' id='save_continue_checkList_button'  onclick='createTemplateCheckList(\"saveAndContinue\");'  class='btn btn-sm btn-primary'>";
	           
	           result = result + '</div>';

	           result = result + '</div>';
	           result = result + '</form>';

	      
	           result = result + '</section>';
	           
	           result = result + '<div id="popUp"></div>'; 
	           result = result + '<div id="choicepopUp"></div>'; 
	           
	           $("#offboardingCheckList").empty();
	           $("#offboardingCheckList").append(result); 
	           $("#offboardTasksList").empty();
	           if(templateId){
	           		customizeFields(templateId,atsUrl,"template");
				}    
	
}
</script>

<script type="text/javascript">
function alertMessage(message){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>"+message+"</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' class='nomr' type='button' value='Ok' onclick='cancel();'><input class='u_can_color nomr' type='button'  value='Cancel' onClick='cancel();'></b>";
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
function createTemplateCheckList(saveType){
	
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
	        var formData = getFormData("offboardTemplate_form");
	        var atsUrl ='${SourceLeadWeb_atsUrl}';
	        if(submitTemplate){
	        	var checkListTemplateId = $("#checkListTemplateId").val();
	    	    $("#save_checkList_button").attr("disabled", "disabled");
	    		$("#save_continue_checkList_button").attr("disabled", "disabled");
	           
	        	 $("#offboardcheckListLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	        	  $.ajax({
	      	    	type: "POST",
	      	    	data: formData,
	      	        url: atsUrl+"/saveOffboardTemplateCheckList?${_csrf.parameterName}=${_csrf.token}&checkListTemplateId="+checkListTemplateId+"&jsonStringData="+jsonStringData, 
	      	        success: function(response) {
	      	        	if (response.response === "AWKWARD") {
	      	        	    location.href = "/errorPage";
	      	        	} else if (response.response === "SUCCESS") {
	      	        		if((response.object == 'home')) {
		      	        		 if(saveType == 'saveAndContinue'){
		      		        		var $tabs = $('.tabbable li');
		      		     	        $tabs.filter('.active').next('li').find('a[data-toggle="tab"]').tab('show');
		      		     	        document.getElementById("templateTasks").disabled = false;
		      		     	        $("#offboardTaskDiv").val('Y');
		      		     	        getOffboardingTasksList();
		      		        	} else {
		      		        		getOffboardingCheckList();
		      		        	}
		      		        } 
		      	        	$("#save_checkList_button").removeAttr("disabled")
		      	      	    $("#save_continue_checkList_button").removeAttr("disabled")
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




  
  
  <script>
  $(function() {
    $( "#startDate" ).datepicker({
	  changeMonth: true,
      changeYear: true,
    });
  });
    $(function() {
    $( "#endDate" ).datepicker({
	  changeMonth: true,
      changeYear: true,
    });
  });
    $(function() {
    $( "#dueDate" ).datepicker({
	  changeMonth: true,
      changeYear: true,
    });
  });
    
    
    // upload file script
    document.getElementById("uploadBtn").onchange = function () {
    document.getElementById("uploadFile").value = this.value;
	};
  </script> 
<script type="text/javascript">
			var config = {
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			}
			for (var selector in config) {
			  $(selector).chosen(config[selector]);
			}
			$('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});
				})
				$(document).ready(function(){
					$("#cd").click(function(){
						$(".clientDetails").toggleClass('hidden', 500);
					});
	
	});
 		</script> 
<script>
    $("#uasrch").selectpicker('refresh');
</script> 


<!-- <script>
    // Script for tabs next pre button
var $tabs = $('.tabbable li');

$('#prevtab').on('click', function() {
    $tabs.filter('.active').prev('li').find('a[data-toggle="tab"]').tab('show');
});

$('#nexttab').on('click', function() {
    $tabs.filter('.active').next('li').find('a[data-toggle="tab"]').tab('show');
});
</script> --> 

<!-- <script>
    //File upload
    $('#file-fr').fileinput({
        language: 'fr',
        uploadUrl: '#',
        allowedFileExtensions: ['jpg', 'png', 'gif']
    });
    $('#file-es').fileinput({
        language: 'es',
        uploadUrl: '#',
        allowedFileExtensions: ['jpg', 'png', 'gif']
    });
    $("#file-0").fileinput({
        'allowedFileExtensions': ['jpg', 'png', 'gif']
    });
    $("#file-1").fileinput({
        uploadUrl: '#', // you must set a valid URL here else you will get an error
        allowedFileExtensions: ['jpg', 'png', 'gif'],
        overwriteInitial: false,
        maxFileSize: 1000,
        maxFilesNum: 10,
        //allowedFileTypes: ['image', 'video', 'flash'],
        slugCallback: function (filename) {
            return filename.replace('(', '_').replace(']', '_');
        }
    });
    /*
     $(".file").on('fileselect', function(event, n, l) {
     alert('File Selected. Name: ' + l + ', Num: ' + n);
     });
     */
    $("#file-3").fileinput({
        showUpload: false,
        showCaption: false,
        browseClass: "btn btn-primary btn-lg",
        fileType: "any",
        previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
        overwriteInitial: false,
        initialPreviewAsData: true,
        initialPreview: [
            "http://lorempixel.com/1920/1080/transport/1",
            "http://lorempixel.com/1920/1080/transport/2",
            "http://lorempixel.com/1920/1080/transport/3",
        ],
        initialPreviewConfig: [
            {caption: "transport-1.jpg", size: 329892, width: "120px", url: "{$url}", key: 1},
            {caption: "transport-2.jpg", size: 872378, width: "120px", url: "{$url}", key: 2},
            {caption: "transport-3.jpg", size: 632762, width: "120px", url: "{$url}", key: 3},
        ],
    });
    $("#file-4").fileinput({
        uploadExtraData: {kvId: '10'}
    });
    $(".btn-warning").on('click', function () {
        var $el = $("#file-4");
        if ($el.attr('disabled')) {
            $el.fileinput('enable');
        } else {
            $el.fileinput('disable');
        }
    });
    $(".btn-info").on('click', function () {
        $("#file-4").fileinput('refresh', {previewClass: 'bg-info'});
    });
    /*
     $('#file-4').on('fileselectnone', function() {
     alert('Huh! You selected no files.');
     });
     $('#file-4').on('filebrowse', function() {
     alert('File browse clicked for #file-4');
     });
     */
    $(document).ready(function () {
        $("#test-upload").fileinput({
            'showPreview': false,
            'allowedFileExtensions': ['jpg', 'png', 'gif'],
            'elErrorContainer': '#errorBlock'
        });
        $("#kv-explorer").fileinput({
            'theme': 'explorer',
            'uploadUrl': '#',
            overwriteInitial: false,
            initialPreviewAsData: true,
            initialPreview: [
                "http://lorempixel.com/1920/1080/nature/1",
                "http://lorempixel.com/1920/1080/nature/2",
                "http://lorempixel.com/1920/1080/nature/3",
            ],
            initialPreviewConfig: [
                {caption: "nature-1.jpg", size: 329892, width: "120px", url: "{$url}", key: 1},
                {caption: "nature-2.jpg", size: 872378, width: "120px", url: "{$url}", key: 2},
                {caption: "nature-3.jpg", size: 632762, width: "120px", url: "{$url}", key: 3},
            ]
        });
        /*
         $("#test-upload").on('fileloaded', function(event, file, previewId, index) {
         alert('i = ' + index + ', id = ' + previewId + ', file = ' + file.name);
         });
         */
    });

    </script> -->
    

  <jsp:include page="../masterFooter.jsp" />
  <div class="backtotop" style="display: none;"><img src="${SourceLeadWeb_sourceleadDomainUrl}/resources/images/backtotop.png"></div>  
  
  
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