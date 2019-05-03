<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="/resources/js/formvalidator.js"></script>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script src="/resources/js/formvalidator.js"></script>

<section class="content-wraper">
    <div id="loading"></div>
    <div id="choicepopUp"></div>
    <section class="content-header clearfix">
          <div class="pull-left">
            <h2>Vendor Onboard Template</h2>
          </div>
          <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo;  <a href="<c:url value='${appPath}/settings'/>">Settings</a>&raquo; <a href="<c:url value='${appPath}/vendorOnboardTemplateList'/>">Onboard Templates List</a> &raquo; Add Onboard Template </div>
    </section>
    
    <div class="content-area content-new clearfix">
      <div class="applicant_accept">
        <div class="tabbable">
          <ul class="nav nav-tabs" id="tab_bar">
            <li class="active" onclick="getTemplateDetails();"> <a href="#details" data-toggle="tab"> <i class="fa fa-th-list paddingRight"></i> <span>Details</span> <br>
              <small>Template details</small> </a> </li>
            <li onclick="getTemplateVendorDocumentsDetails();"><a href="#documents" data-toggle="tab" id="templateDocs"> <i class="fa fa-files-o paddingRight"></i> <span>Documents</span> <br>
              <small>Vendor Documents</small> </a> </li>
            <li onclick="getVendorOnboardingTasksList();"> <a href="#tasks" data-toggle="tab" id="templateTasks"> <i class="fa fa-check-square-o paddingRight"></i> <span>Tasks</span> <br>
              <small>List of tasks</small> </a></li>
          </ul>
          <input type="hidden" name="templateId" id="templateId" value=""/>
          <input type="hidden" id="onboardTaskDiv" value="N"/>
          <div class="tab-content"> 
            
            <!-- Employee Details -->
            <div class="tab-pane active" id="details">
              <div class="form-horizontal assignTemplate">
                <h4 class="clearfix">Template Details</h4>
                <form:form class="login-form clearfix"  id="onboardTemplate_form"  name="onboardTemplate_form" method="post" > 
                <div class="form-group">
                  <div class="col-md-6" id="templateNameDiv">
                    <label>Template Name <span class="text-danger paddingLeft">*</span></label>
                    <input type="text" class="form-control input-sm" id="templateName" name="templateName" onblur="validateTemplateName();" field-name="Template Name" data-validation="required validate_Space validate_length length2-100">
                    <input type="hidden" name="templateId" id="onboardtemplateId" value=""/>
                  </div>
                  <div class="col-md-6">
                    <label>Template Description <span class="text-danger paddingLeft">*</span></label>
                      <textarea rows="3" class="form-control input-sm" name="description" field-name="Template Description"  data-validation="required validate_length length2-500"></textarea>
 
                  </div>
                </div>
                <div class="form-footer clearfix mt">
                  <div class="pull-right mr">
                    <input type="button" value="Cancel" class="btn btn-sm btn-default" onClick="self.location='/app/vendorOnboardTemplateList'">
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
                <p><strong>Vendor Acceptance Documents</strong> <br>

                  <small class="text-muted">These documents need to be upload by the new vendor as part of acceptance step  </small> </p>
              
               
                <div class="form-group">
                  <label class="col-md-12"> <strong>Documents</strong> <br>
                  </label>
                  <label class="col-md-12 mt10">Required documents for onboarding :</label>
                     <div id="timesheetDocumentsId" class="timesheetDocs timesheetDocs-custom">
                      <div class="col-md-8 commentFile timesheet_week_comm onboardDocs">
				        <span class="uploadAttachments"> 
			              <div id="savedContractsAttachments"></div>
				        </span>
                      </div>
                      </div>
                </div>
                
               
                <div class="clearfix"></div>
         
                
                <div class="form-footer">
                  <div class="pull-right mr">
                     <input type="button" value="Cancel" class="btn btn-sm btn-default" onClick="self.location='/app/vendorOnboardTemplateList'">
                    <input type="button" value="Save" id="save_doc_button"  onclick="createTemplateDocs('save');" class="btn btn-sm btn-success">
                    <input type="button" value="Save and Continue" id="save_continue_doc_button" onclick="createTemplateDocs('saveAndContinue');" class="btn btn-sm btn-primary">
                  </div>
                </div>
                </form>
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
                          <button type="button" class="close" data-dismiss="modal" onclick="flushOnboardTask()">&times;</button>
                          <h4 class="modal-title">Add Tasks</h4>
                        </div>
                        
                        <div class="success_pop" id="selector"  style="display: none;">
	         				<i class="fa fa-check-circle"></i><span>Task Saved Successfully.</span>
					    </div>
                        
                        <div class="modal-body clearfix"> 
                          <!-- Add New Task Widget -->
                          <div class="col-md-12 clearfix">
                          <div id="onboardtasksLoading"></div>
                             <form:form class="login-form clearfix"  id="addonboardTask_form"  name="addonboardTask_form" method="post" > 
                             <input type="hidden" name="templateId" id="taskstemplateId" value=""/>
                              <div class="row">
                              
                              <div class="col-md-6">
                                <div class="row">
                                
                                <div class="col-md-12 mb10" id="taskNameDiv">
                                  <label>Subject<span class="text-danger">*</span></label>
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
                                  <label>Assign To<span class="text-danger">*</span></label>
                                  
                                  <div class="">
                                    <label class="paddingRight">
                                      <input type="radio"  name="assignedToType" checked="checked" onclick="validateAssignToType('assingToVendor');">
                                      Vendor</label>
                                    <label class="paddingLeft">
                                      <input type="radio" name="assignedToType" value="Associate" onclick="validateAssignToType('assingToUser');">
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
                                      <label><strong>Due date:<span class="text-danger">*</span></strong></label>
                                                                            
                                      <!-- Form inline Due after -->
                                      
                                      <div class="row" id="dueAfterDiv">
										   <div class="col-md-6 paddingRight">
											<label class="small">Months</label> <br>
												<div class="select-field">
													<select class="form-control input-sm" id="taskMonths" data-validation="required" field-name="Due date" name="taskMonths" onchange="validateDueAfter();">
														<option selected value=''>--Select Month--</option>
														<c:forEach begin="1" end="12" varStatus="loop">
															<option>${loop.index}</option>
														</c:forEach>
														</select>
												</div>
											</div> 
                                      
                                        
                                        <div class="col-md-6 paddingLeft">
										<label class="small">Days</label>
											<div class="select-field">
												<select class="form-control input-sm" id="taskDays" name="taskDays" onchange="validateDueAfter();">
													<option selected value=''>--Select Days--</option>
													<c:forEach begin="1" end="30" varStatus="loop">
															<option>${loop.index}</option>
													</c:forEach>
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
                                    <input type="button" id="onboardtask_submit" class="btn btn-primary btn-sm" onclick="saveOnboardTask();"  value="Save">
                                    <input type="button" class="btn btn-default btn-sm ml10" value="Cancel" data-dismiss="modal" onclick="flushOnboardTask();">
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
              
              <div id="onboardTasksList"></div>
            
            
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
  function flushOnboardTask(){
	  document.getElementById("addonboardTask_form").reset();
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
		
		var templateId = '${templateId}';
		if(templateId != null && templateId != '' && templateId != 'undefined'){
			$("#templateId").val(templateId);
  	        $("#onboardtemplateId").val(templateId);
  	        $("#documentstemplateId").val(templateId);
  	        $("#taskstemplateId").val(templateId);
  	      
  	      document.getElementById("templateDocs").disabled = false;
  	      document.getElementById("templateTasks").disabled = false;
  	      $("#onboardTaskDiv").val('Y');
		}
		
		getTemplateDetails();
	});
</script>

<div id="divContractsNameTd1" style="display: none; float: left;">
	  			<input type="text"  placeholder='Document Name' onblur='validateDocumentName(REPLACEROWID)' class='form-control input-sm vendorinput' id='REPLACEROWIDcontractsdocFileName' name="vendorDocumentsFileInfo[REPLACEROWID].docFileName"  data-validation="required" field-name="Document" > 
</div>

<!-- <div id="divContractsAttachmentTd1" style="display: none; float: left;">
			<div id='REPLACEROWIDContractsdocFileuploadFileGroup'>
	  			<input type='file' id='REPLACEROWIDcontractsdocFile' class="timesheetAttachmentValidation" data-validation="validatedoc" data-validation-optional="true" field-name="Contracts Document" onchange='uploadContractDocument(REPLACEROWID)'> 
	  		</div>
</div> -->



<div id="divDocsTd3" style="display: none; float: left;">
<label class="switch pull-left ml-mr switchvendor-custom">
       <input type="checkbox" checked id="REPLACEROWIDchecked" name="vendorDocumentsFileInfo[REPLACEROWID].checked" onclick="checkDocument(REPLACEROWID)" value="Y">
       <div class="slider round"></div>
       </label>
</div>   

<div id="divDocsTd4" style="display: none; float: left;">
   <i class="fa fa-times" onclick="removeContractsAttachmentRow(REPLACEROWID);"></i> 
</div> 


<script>
function checkDocument(id){
	var checked = document.getElementById(id+"checked").checked
	if(checked == true){
		$("#"+id+"checked").val("Y");
	}else{
		$("#"+id+"checked").val("N");
	}
}
</script>

<script>
function addContractsAttachment(){  
			var checkMaxTsRowNum = $("#contractsAttcahmentMaxRowNumber").val();
			var tbl = document.getElementById("contractsAttachmentTable");
			var maxRowNum = parseInt($("input[name = 'contractsAttcahmentMaxRowNumber']").val());
			$("input[name = 'contractsAttcahmentMaxRowNumber']").val(maxRowNum + 1);
			
			
			var row = tbl.insertRow(maxRowNum);
			row.setAttribute("id", "divAttachmentContractsRow" + maxRowNum);
			
			$("input[id = 'addedDocumentsRowCount']").val(maxRowNum + 1);
			 var documentsRow = document.getElementById("addedDocumentsRowCount").value;
			 if(documentsRow > 1){
				 $("#0contractsdocFileName").removeAttr("data-validation-optional");
				 $("#0contractsdocFileName").removeAttr("data-validation");
				 $("#0contractsdocFileName").attr("data-validation", "required");
			 }
			
			var cell0 = row.insertCell(0);
			var divDocTypeTdStr = document.getElementById("divContractsNameTd1").innerHTML;
			divDocTypeTdStr = divDocTypeTdStr.replace(/REPLACEROWID/g, (maxRowNum));
			cell0.innerHTML = divDocTypeTdStr;
			
			/* var cell1 = row.insertCell(1);
			var divAttachmentTdStr = document.getElementById("divContractsAttachmentTd1").innerHTML;
			divAttachmentTdStr = divAttachmentTdStr.replace(/REPLACEROWID/g, (maxRowNum));
			cell1.innerHTML = divAttachmentTdStr; */
			
			var cell2 = row.insertCell(1);
			var divDocCheckTdStr = document.getElementById("divDocsTd3").innerHTML;
			divDocCheckTdStr = divDocCheckTdStr.replace(/REPLACEROWID/g, (maxRowNum));
			cell2.innerHTML = divDocCheckTdStr;
			
			var cell3 = row.insertCell(2);
			var divDocRemoveTdStr = document.getElementById("divDocsTd4").innerHTML;
			divDocRemoveTdStr = divDocRemoveTdStr.replace(/REPLACEROWID/g, (maxRowNum));
			cell3.innerHTML = divDocRemoveTdStr;
			
			
			
			
}	

var sourceleadUrl = '${sourceleadUrl}';
function uploadContractDocument(id){
	fileUpload(""+id+"contractsdocFile", "Vendor-Onboard-Template-Documents", "templateDocuments_form","vendorDocumentsFileInfo");
}

</script>
	
<script type="text/javascript">
	function removeContractsAttachmentRow(rowId) {
		
		var addedDocumentsRowCount = document.getElementById("addedDocumentsRowCount").value;
		 $("input[id = 'addedDocumentsRowCount']").val(parseInt(addedDocumentsRowCount) - 1);
		 
		 var documentsRow = document.getElementById("addedDocumentsRowCount").value;
		 if(documentsRow == 1){
			 $("#0contractsdocFileName").removeAttr("data-validation");
			 $("#0contractsdocFileName").attr("data-validation-optional", "true");
		 }
		 
		 document.getElementById("divAttachmentContractsRow"+rowId).style.display = 'none';
		 document.getElementById("divAttachmentContractsRow"+rowId).value= '';
		 $("#templateDocuments_form input[data-id='"+rowId+"contractsdocFile']").remove();
		 var  rId = "divAttachmentContractsRow"+rowId;
		  var r = document.getElementById("contractsAttachmentTable").rows[rId];
          var count = 2*(r.cells.length);
		    for(i=0;i<=count;i++){
		        r.deleteCell(0);
		    } 
		 
	}
</script>


<script>
function getAssignToUsersList(){
	var templateId = document.getElementById("taskstemplateId").value;
    $("#taskstemplateId").val(templateId);
    
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
function createTemplate(saveType){
	var validationSettings = {
	        errorMessagePosition : 'element',
	       borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
         }; 
	
	  if ($('#onboardTemplate_form').validate(false, validationSettings)){ 
	        var sourceleadUrl ='${sourceleadUrl}';
	    	$("#save_button").attr("disabled", "disabled");
    		$("#save_continue_button").attr("disabled", "disabled");
    		var formData = getFormData("onboardTemplate_form");
	        $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	        $.ajax({
	        	type : "POST",
  	    	    data: formData,
		    	url: sourceleadUrl+"/createNewVendorOnBoardingTemplate?${_csrf.parameterName}=${_csrf.token}", 
		        success: function(response) {
		        	if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
		    		    showError(response.responseMessage);
		    	  	} else if (response.response === "AWKWARD") {
		    		    location.href = "/errorPage";
		    		} else if (response.response === "SUCCESS") {
		    			if(saveType == 'saveAndContinue'){
			        		var $tabs = $('.tabbable li');
			     	        $tabs.filter('.active').next('li').find('a[data-toggle="tab"]').tab('show');
			     	        document.getElementById("templateDocs").disabled = false;
			     	        getTemplateVendorDocumentsDetails(response.object.id);
			        	}
			        	
		     	        $("#templateId").val(response.object.id);
		     	        $("#onboardtemplateId").val(response.object.id);
		     	        $("#documentstemplateId").val(response.object.id);
		     	        $("#taskstemplateId").val(response.object.id);
		     	       
		     	        $("#save_button").removeAttr("disabled")
		        	    $("#save_continue_button").removeAttr("disabled")
		        	    
		     	        $("#loading").removeAttr("style");
		    			
		    		}else if (response.indexOf('Sign In') !== -1) {
		    			location.replace(sourceleadUrl);
		    		} else {
		    		    showError("Operation failed, please try again");
		    		}
		        	
		        	
		        },
		    }); 
	        return true;
		
	  }else{
		return false;
	  }
}

$('body').on('blur', '#onboardTemplate_form', function() {
	$("#onboardTemplate_form").showHelpOnFocus();
	$("#onboardTemplate_form").validateOnBlur(false, validationSettings);  
});
</script>
<script>
$(document).on("keypress", 'form', function (e) {
    var code = e.keyCode || e.which;
    if (code == 13) {
        e.preventDefault();
        return false;
    }
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
	
	  var sourceleadUrl ='${sourceleadUrl}';
	  
	  if ($('#templateDocuments_form').validate(false, validationSettings)){ 
		   $("#save_doc_button").attr("disabled", "disabled");
		   $("#save_continue_doc_button").attr("disabled", "disabled");
		   var formData = getFormData("templateDocuments_form");
		   
	   $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		$.ajax({
		      type : "POST",
    	      data: formData,
	    	  url: sourceleadUrl+"/saveVendorOnboardingTemplateDocuments?${_csrf.parameterName}=${_csrf.token}",		
		      success :function(response) {
		    	  if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
		    		    showError(response.responseMessage);
		    	  	} else if (response.response === "AWKWARD") {
		    		    location.href = "/errorPage";
		    		} else if (response.response === "SUCCESS") {
		    			if(saveType == 'saveAndContinue'){
			        		var $tabs = $('.tabbable li');
			     	        $tabs.filter('.active').next('li').find('a[data-toggle="tab"]').tab('show');
			     	        document.getElementById("templateTasks").disabled = false;
			     	        $("#onboardTaskDiv").val('Y');
			     	        getVendorOnboardingTasksList();
			        	}else{
			        		getTemplateVendorDocumentsDetails();
			        		
			        	}
			    	  
			    	  $("#save_doc_button").removeAttr("disabled");
		      	      $("#save_continue_doc_button").removeAttr("disabled");
			    	  $("#loading").removeAttr("style");
		    		}else if (response.indexOf('Sign In') !== -1) {
		    			location.replace(sourceleadUrl);
		    		} else {
		    		    showError("Operation failed, please try again");
		    		}
		    	  
		      }
	    	  
	  });
		 return true
	  }else{
		  return false
	  }
	  
	  $('body').on('blur', '#templateDocuments_form', function() {
			$("#templateDocuments_form").showHelpOnFocus();
			$("#templateDocuments_form").validateOnBlur(false, validationSettings);  
	   });
	
	
}
</script>



<script>
function saveOnboardTask(){
	
	var validationSettings = {
	        errorMessagePosition : 'element',
	       borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
         }; 
	
	  if ($('#addonboardTask_form').validate(false, validationSettings)){ 
		  	
	        var sourceleadUrl ='${sourceleadUrl}';
	        var formData = getFormData1("addonboardTask_form");
	        
	        $("#onboardtasksLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	        $.ajax({
	        	type : "POST",
  	    	    data: formData,
  	    	    processData: false,
		        contentType:false,
	    	    url: sourceleadUrl+"/saveVendorOnBoardTemplateTasks?${_csrf.parameterName}=${_csrf.token}", 
		        success: function(response) {
		        	 if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
			    		    showError(response.responseMessage);
			    	  	} else if (response.response === "AWKWARD") {
			    		    location.href = "/errorPage";
			    		} else if (response.response === "SUCCESS") {
				        		$("#onboardtasksLoading").removeAttr("style");
					        	flushOnboardTask();
					        	getAssignToUsersList();
					        	getVendorOnboardingTasksList();
					        	
					        	document.getElementById("selector").style.display = 'block';
					        	$("#selector").fadeIn(150);
					        	$('#selector').delay(1000).fadeOut('slow');
			    		}else if (response.indexOf('Sign In') !== -1) {
			    			location.replace(sourceleadUrl);
			    		} else {
			    		    showError("Operation failed, please try again");
			    		}
		        },
		    });  
	        return true;
		
	  }else{
		return false;
	  }
}

$('body').on('blur', '#addonboardTask_form', function() {
	$("#addonboardTask_form").showHelpOnFocus();
	$("#addonboardTask_form").validateOnBlur(false, validationSettings);  
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
        $.ajax({
	    	type: "POST",
	    	 url: sourceleadUrl+"/getVendorOnboardTemplateById?${_csrf.parameterName}=${_csrf.token}&templateId="+templateId, 
	        success: function(response) {
	        	
	        	if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
	    		    showError(response.responseMessage);
	    	  	} else if (response.response === "AWKWARD") {
	    		    location.href = "/errorPage";
	    		} else if (response.response === "SUCCESS") {
	    			 $("input[name=templateName]").val(response.object.templateName);
		        	 $("textarea[name=description]").val(response.object.description);
		        	 
		        	 $("#onboardTasksList").empty();
	    		}else if (response.indexOf('Sign In') !== -1) {
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
function getTemplateVendorDocumentsDetails(templateId){
	$("#templateDocuments_form input[name*='vendorDocumentsFileInfo']").remove();
	if(templateId == '' || templateId == null || templateId =='undefined'){
		templateId = document.getElementById("templateId").value;
	}
	var documentType = "Documents"
	if(templateId){
		var sourceleadUrl ='${sourceleadUrl}';
	     $.ajax({
		    	type: "POST",
		    	 url: sourceleadUrl+"/getVendorOnboardTemplateDocumentByTemplateId?${_csrf.parameterName}=${_csrf.token}&templateId="+templateId+"&documentType="+documentType, 
		        success: function(response) {
		        	if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
		    		    showError(response.responseMessage);
		    	  	} else if (response.response === "AWKWARD") {
		    		    location.href = "/errorPage";
		    		} else if (response.response === "SUCCESS") {
		    			var result = "";
			        	 
	                	 for (var i = 0; i < response.data.length; i++) { 
	                		 var opt = response.data[i]; 
	                		 var checked = '';
	                		 if(opt.isRequired == 'Y'){
	                			 checked = "checked";
	                		 }
	                		 result = result +"<strong id='contractsdocument"+opt.id+"'>";
	                		 result = result +"<input type='text' id='"+i+"savedDoc' value='"+opt.documentName+"' title='"+opt.documentName+"' readonly class='form-control input-sm vendorinput'></input>";
	                		 
	                	/* 	 result = result +"<div class='positionRelative inputpadding'>";
	                		 if(opt.fileUrl != null){
	                			 result = result +"<i class=''><a class='glyphicon glyphicon-save' href=\""+opt.fileUrl+"\"></a></i>";
	               				 result = result +"<input type='button' value='"+opt.fileName+"'>";
	               				 result = result +"</a>";
	                		 }else{
	                			 result = result +"<i class=''></i>";
	               				 result = result +"<input type='button' value='No document uploaded'>";
	                		 }
	                		 
	           				 result = result +"</div>"; */
	           				 
	           				 result = result +"<label class='switch switchvendor-custom pull-left ml-mr'>";
	                   	     result = result +"<input type='checkbox' "+checked+" id='"+opt.id+"docChecked' onclick='alertRequiredMessage(\""+opt.documentUUID+"\"\,"+opt.id+")'>"
	                   	     result = result +'<div class="slider round"></div>';
	                  		 result = result +'</label>';
	           				
	           				 result = result +"<input class='dele' type='button' onClick='deleteTemplateDocument(\""+opt.documentUUID+"\","+opt.id+",\""+documentType+"\");' >";
	           				 result = result +"</strong>";
	                	 }
	                	 
	                	 result = result +"<input type='hidden' name='contractsAttcahmentMaxRowNumber'  id='contractsAttcahmentMaxRowNumber' value='1' />";
	                	 result = result +"<input type='hidden' id='addedDocumentsRowCount' value='1' />";
	                	 result = result +"<table class='timesheetAttachment'  id='contractsAttachmentTable' cellpadding='0' cellspacing='0'>";
	                	 result = result +"<tr id='divAttachmentTsRow0'>";
	                	 result = result +"<td><input type='text' placeholder='Document Name' onblur='validateDocumentName(0)' class='form-control input-sm vendorinput'  data-validation-optional='true' field-name='Document' name='vendorDocumentsFileInfo[0].docFileName' id='0contractsdocFileName'></td>";
	                	 result = result +"<td>"; 
	                	 result = result +"<label class='switch pull-left ml-mr switchvendor-custom'>";
	                	 result = result +"<input type='checkbox' checked id='0checked' name='vendorDocumentsFileInfo[0].checked' onclick='checkDocument(0)' value='Y'>"
	                	 result = result +'<div class="slider round"></div>';
	               		 result = result +'</label>';
	                	 result = result +"</td>";
	                	 result = result +"<td><i class='fa fa-plus' id='addDocRow' onClick='addContractsAttachment()'></i></td>";
	                	 result = result +"</tr>";
	                	 result = result +"</table>";
	                	 $("#savedContractsAttachments").empty();
	                	 $("#savedContractsAttachments").append(result);
	                	 $("#onboardTasksList").empty();
		    			
		    		}else if (response.indexOf('Sign In') !== -1) {
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
function validateDocumentName(id){
	var checkMaxTsRowNum = $("#contractsAttcahmentMaxRowNumber").val();
	var flag = true;
	var j = "";
	 var documentName = $("#"+id+"contractsdocFileName").val();
	 for (var i = 0; i < checkMaxTsRowNum; i++) { 
		
		 if(id != i){
			 if(documentName != "undefined" && documentName != '' && documentName != null){
				 var docName = $("#"+i+"contractsdocFileName").val();
				 if(documentName == docName){
					 flag = false;
					 break;
				 } 
			 }
		 }
	 }
	 
	 
	 var savedDoc = $("#savedContractsAttachments strong[id*=contractsdocument]").length;
	 for (var i = 0; i <= savedDoc; i++) { 
		 var docName = $("#"+i+"savedDoc").val();
		 if(documentName == docName){
			 flag = false;
			 break;
		 }
	 }
	 
	 if(flag == false){
		  alertMessage(documentName);
		  var addDocRow = document.getElementById("addDocRow");
		  $("#addDocRow").removeAttr("onclick");
		  $("#save_continue_doc_button").attr("disabled", "disabled");
	 }else{
		  $("#save_continue_doc_button").removeAttr("disabled"); 
		  var addDocRow = document.getElementById("addDocRow");
		  $("#addDocRow").attr("onclick","addContractsAttachment()");
	 }
	
	
}
</script>




<script type="text/javascript">
function alertMessage(documentName){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Duplicate Document <strong>"+documentName+"</strong></p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='cancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
	result = result +"	</div>";
	$("#choicepopUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);

}
</script>


<script>
function deleteTemplateDocument(documentUUID,documentId,documentType){
	   $.ajax({
	    	type: "POST",
	    	 url: sourceleadUrl+"/deleteVendorTemplateDocumentByDocumentUUID?${_csrf.parameterName}=${_csrf.token}&documentUUID="+documentUUID, 
	        success: function(data) {
	        	if(data == 'success'){
	        		if(documentType == 'Documents'){
	        			document.getElementById("contractsdocument"+documentId).remove();
	        		}
	        		
	        	}
	        },
	    });
	 
}
</script>


<script type="text/javascript">
function alertRequiredMessage(documentUUID,id){
	var checked = document.getElementById(id+"docChecked").checked
	var message = '';
	if(checked == true){
		message = "Are you sure to make required?";
	}else{
		message = "Are you sure to make not required?";
	}
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>"+message+"</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='docIsRequiredOrNot(\""+documentUUID+"\");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel(\""+id+"\");'></b>";
	result = result +"	</div>";
	$("#choicepopUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);

}
</script>

<script>
function docIsRequiredOrNot(documentUUID){
	
	var sourceleadUrl ='${sourceleadUrl}';
	$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	   $.ajax({
	    	type: "POST",
	    	 url: sourceleadUrl+"/requiredOrNotTemplateDocumentByDocumentUUID?${_csrf.parameterName}=${_csrf.token}&documentUUID="+documentUUID, 
	        success: function(data) {
	        	if(data == 'success'){
        			$("#loading").removeAttr("style");
	        	}
	        },
	    });
	   
	   $("#choicepopUp").empty();
	   $(".project_inn_mask").fadeOut(200);
}
</script>

<script type="text/javascript">
function cancel(id){
	$("#choicepopUp").empty();
	$(".project_inn_mask").fadeOut(200);
	var checked = document.getElementById(id+"docChecked").checked
	if(checked == false){
		document.getElementById(id+"docChecked").checked = true;
	}else{
		document.getElementById(id+"docChecked").checked = false;
	}
	
}
</script>


<script>
function validateTemplateName(){
	var templateId = document.getElementById("templateId").value;
	var templateName = document.getElementById("templateName").value;
	
	if(templateName){
		var sourceleadUrl = '${sourceleadUrl}';
		$.ajax({
	    	type: "POST",
	    	url : sourceleadUrl+"/validateVendorOnboardingTemplateName?${_csrf.parameterName}=${_csrf.token}&templateName="+templateName+"&templateId="+templateId, 
	        success: function(result) {
	        	if (result == 'invalid') {
	        		$("#save_button").attr("disabled", "disabled");
	        		$("#save_continue_button").attr("disabled", "disabled");
	        		$('#templateNameDiv').find('p.jquery_form_error_message').remove();
	        		$("input#templateName").after( "<p class='jquery_form_error_message'>" + templateName + " is Already Existed</span>");
					document.getElementById("templateName").setAttribute( 'record-exist', 'yes');
					document.getElementById("templateName").setAttribute( 'record-exist-errorMsg', templateName +"  already existed");
	        	}else{
	        		$('input#templateName').removeAttr("class record-exist record-exist-errormsg");
					$('input#templateName').attr('class','valid form-control');
					$('#templateNameDiv').find('p.jquery_form_error_message').remove();
					
	        		$("#save_button").removeAttr("disabled")
	        		$("#save_continue_button").removeAttr("disabled")
	        		
	        	}
	        }
		 });
	}
}
</script>


<script>
function getVendorOnboardingTasksList(){
	var templateId = document.getElementById("documentstemplateId").value;
	var onboardTaskDiv = document.getElementById("onboardTaskDiv").value;
	if(onboardTaskDiv == 'Y'){
	var sourceleadUrl = '${sourceleadUrl}';
	var locationId = '${SourceLeadWeb_orgLocationId}';
	
	 $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	$.ajax({
	   	type: "GET",
	    url: sourceleadUrl+"/getVendorOnboardTemplateTasksListByTemplateId?${_csrf.parameterName}=${_csrf.token}&templateId="+templateId,	    	   
	    success: function(response) {
	    	if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
    		    showError(response.responseMessage);
    	  	} else if (response.response === "AWKWARD") {
    		    location.href = "/errorPage";
    		} else if (response.response === "SUCCESS") {
    			 var result = "";
    	        	if(response.data.length > 0){ 
    		        	for (var i=0; i<response.data.length; i++)
    					{
    		        		var report = response.data[i];
    		        		
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
    		        		}else{
    		 					result = result +'Onboarding Vendor';
    		 				}
    		        		result = result +'</li>';
    		        		
    		        		result = result +'<li><span>Priority:</span>'+prority+'</li>';
    		        		result = result +'<li><span>Repeat:</span> '+repeat+'</li>';
    		        		result = result +'</ul>';
    		        		result = result +'</div>';
    		        		result = result +'</div>';
    					}
    	        	}else{
    	        		result = result +'<div><h4>No Tasks Found</h4></div>';
    	        	}
    	        	
    	        	result = result +'<div class="form-footer clearfix mt">';
    	    		result = result +'<div class="pull-right mr">';
    	    		result = result +'<input type="button" value="Finish" id="finish_task" onclick="saveAndFinishOnboardTemplate('+templateId+');" class="btn btn-sm btn-primary">';
    	    		result = result +'</div>';
    	    		result = result +'</div>';
    	    		
    	    		 $("#onboardTasksList").empty();
    	           	 $("#onboardTasksList").append(result); 
    		}else if (response.indexOf('Sign In') !== -1) {
    			location.replace(sourceleadUrl);
    		} else {
    		    showError("Operation failed, please try again");
    		}
	    	
	    }
   }); 
	
  $("#loading").removeAttr("style");
	
 }
}
</script>



<script>
function deleteTask(taskId){
	
	var sourceleadUrl ='${sourceleadUrl}';
	   $.ajax({
	    	type: "POST",
	    	 url: sourceleadUrl+"/deleteVendorOnboardTaskByTaskId?${_csrf.parameterName}=${_csrf.token}&taskId="+taskId, 
	        success: function(response) {
	        	
	        	if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
	    		    showError(response.responseMessage);
	    	  	} else if (response.response === "AWKWARD") {
	    		    location.href = "/errorPage";
	    		} else if (response.response === "SUCCESS") {
		        		document.getElementById("onboardTask"+taskId).remove();
		        		if($("#onboardTasksList div[id*=onboardTask]").length === 0) {
		        			$("#onboardTasksList").prepend("<div><h4>No Tasks Found</h4></div>");
		        		}
	    		}else if (response.indexOf('Sign In') !== -1) {
	    			location.replace(sourceleadUrl);
	    		} else {
	    		    showError("Operation failed, please try again");
	    		}
	        	
	        },
	    });
}
</script>


<script>
function saveAndFinishOnboardTemplate(templateId){
	
	var sourceleadUrl = '${sourceleadUrl}';
	$("#finish_task").attr("disabled", "disabled");
	 $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	$.ajax({
	   	type: "POST",
	    url: sourceleadUrl+"/saveAndFinishVendorOnboardTemplate?${_csrf.parameterName}=${_csrf.token}&templateId="+templateId,	    	   
	    success: function(response) {
	    	
	    	if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
    		    showError(response.responseMessage);
    	  	} else if (response.response === "AWKWARD") {
    		    location.href = "/errorPage";
    		} else if (response.response === "SUCCESS") {
    			 $("#finish_task").removeAttr("disabled");
    	    	 location.replace(sourceleadUrl+"/vendorOnboardTemplateList");
    		}else if (response.indexOf('Sign In') !== -1) {
    			location.replace(sourceleadUrl);
    		} else {
    		    showError("Operation failed, please try again");
    		}
	    	 
	    }
  }); 
	

}
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
   /*  document.getElementById("uploadBtn").onchange = function () {
    document.getElementById("uploadFile").value = this.value;
	}; */
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


<!-- fileinput function is not declared..! -->


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
  <div class="backtotop" style="display: none;"><img src="/resources/images/backtotop.png"></div>  
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