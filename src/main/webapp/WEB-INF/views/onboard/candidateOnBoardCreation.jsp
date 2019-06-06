<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<link rel="stylesheet" href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/chosen.min.css?staticTime=${staticTime}">
<script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/chosen.jquery.min.js?staticTime=${staticTime}"></script> 
<script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/jquery-fun.js?staticTime=${staticTime}"></script> 
<script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/formvalidator.js?staticTime=${staticTime}"></script>



<script> 
var atsUrl ='${SourceLeadWeb_atsUrl}';
	$(document).ready(function(){
		document.getElementById("templateDocs").disabled = true;
		document.getElementById("templateTasks").disabled = true;
		
		
		var onBoardingId = '${onBoardingId}';
		if(onBoardingId != null && onBoardingId != '' && onBoardingId != 'undefined'){
			$("#candidateOnBoardId").val(onBoardingId);
  	        document.getElementById("templateDocs").disabled = false;
  	        document.getElementById("templateTasks").disabled = false;
  	        $("#candidateOnBoardUUID").val('${onBoardingUUID}');
  	        $("#documentstemplateId").val(onBoardingId);
  	        
  	        loadOnboardingCandidate('${onBoardingUUID}');
  	       shiftList();
  	        
		}else{
			var candidateId = '${candidateId}';
			var offerId = '${offerId}';
			if(candidateId != null && candidateId != '' && candidateId != 'undefined'){
				loadOnboardCandidateFormDashobard(candidateId,offerId);
			}else{
				loadOnboardingDetails();
			}
			
			
		}
		
		
		
	});
</script>

<section class="content-wraper">
<div id="loading"></div>
  <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Employee Onboarding</h2>
      </div>
      <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a>  &raquo; <a href="${SourceLeadWeb_atsUrl}/candidateOnBoardingList">Onboard & Offboard List</a> &raquo; Employee Onboarding </div>
 </section>
 
 
 <div class="content-area content-new clearfix">
      <div class="applicant_accept">
        <div class="clearfix onboardBtns">
        </div>
        <div class="tabbable">
        <div id="onboardingPopUp"></div>
          <ul class="nav nav-tabs" id="tab_bar">
            <li class="active" onclick="loadOnboardingCandidate();"> <a href="#details" data-toggle="tab"> <i class="fa fa-th-list paddingRight"></i> <span>Details</span> <br>
              <small>Employee details &amp; Position details</small> </a> </li>
            <li onclick="getTemplatePolicyDocumentsDetails();getTemplateSupportingDocumentsDetails();getTemplateContractsDocumentsDetails();getTemplateFormsDocumentsDetails();"><a href="#documents" data-toggle="tab" id="templateDocs"> <i class="fa fa-files-o paddingRight"></i> <span>Documents</span> <br>
              <small>Acceptance &amp; Policy Documents</small> </a> </li>
            <li onclick="getOnboardingTasksList();"> <a href="#tasks" data-toggle="tab" id="templateTasks"> <i class="fa fa-check-square-o paddingRight"></i> <span>Tasks</span> <br>
              <small>List of tasks</small> </a></li>
          </ul>
          <input type="hidden" name="candidateOnBoardId" id="candidateOnBoardId" value=""/>
          <input type="hidden" name="candidateOnBoardUUID" id="candidateOnBoardUUID" value=""/>
          <div class="tab-content"> 
            
            <!-- Employee Details -->
            <div class="tab-pane active" id="details">
              <div class="form-horizontal">
               <form:form class="login-form clearfix"  id="onboardCandidate_form"  name="onboardCandidate_form" method="post" >
               <input type="hidden" name="savedonboardId" id="savedonboardId" value=""/>
                <p><strong>Employee Details</strong></p>
                <div class="form-group" id="onboardCandidatesDiv">
                  <label class="col-md-3 control-label">Select Candidate <span class="text-danger">* </span><i class='fa fa-info-circle' title='Select the candidate who is being onboarded'></i></label>
                  <div class="col-md-6">
                    <select class="chosen-select" id="onboardCandiate" name="onboardCandiate" data-validation="required" onchange="addCandidateDetailsForOnboarding();validOnboardCandiate();getCandidateDetails();getOfferDetails();getShiftList();" field-name="Candidate">
                    </select>
                  </div>
                </div>
               
               <div id="onboardingCandidateDetailsDiv"></div>
                  
                <div class="form-footer">
                  <div class="pull-right mr">
                    <input type="button" value="Cancel" class="btn btn-sm btn-default" onClick="self.location='${SourceLeadWeb_atsUrl}/candidateOnBoardingList'">
                    <input type="button" value="Save" id="save_onboardUser" onclick="saveOnboardingCandidateDetails('save');" class="btn btn-sm btn-success">
                    <input type="button" value="Save and Continue" id="saveAndContinue_onboardUser" onclick="saveOnboardingCandidateDetails('saveAndContinue');"  class="btn btn-sm btn-primary">
                  </div>
                </div>
             </form:form>
              </div>
            </div>
            
            <!-- TAB 2 -->
            <div class="tab-pane" id="documents">
              
            <div class="form-horizontal">
              <form id='templateDocuments_form' name='templateDocuments_form' >
                 <input type="hidden" name="candidateonboardingId" id="documentstemplateId" value=""/>
                <p><strong>Acceptance Documents</strong> <br>
                  <small class="text-muted">These documents need to be accepted by the new hire as part of acceptance step  - Learn more</small> </p>
                
                
                <div class="form-group">
                  <label class="col-md-12"> <strong>Contracts</strong> <br>
                  </label>
                  <label class="col-md-4 control-label mt10">These contracts will be added to this offer:</label>
                     <div id="timesheetDocumentsId" class="timesheetDocs">
                      <div class="col-md-7 commentFile timesheet_week_comm onboardDocs">
				        <span class="uploadAttachments"> 
			              <div id="savedContractsAttachments"></div>
				        </span>
                      </div>
                      </div>
                </div>
                
                
                <div class="form-group">
                  <label class="col-md-12"> <strong>Forms</strong> <br>
                  </label>
                  <label class="col-md-4 control-label mt10">These forms will be added to this offer:</label>
                     <div id="timesheetDocumentsId" class="timesheetDocs">
                      <div class="col-md-7 commentFile timesheet_week_comm onboardDocs">
				        <span class="uploadAttachments"> 
			              <div id="savedFormsAttachments"></div>
				        </span>
                      </div>
                      </div>
                </div>
                
                
                <div class="form-group">
                  <label class="col-md-12"> <strong>Policies</strong> <br>
                  </label>
                  <label class="col-md-4 control-label mt10">These policies will be added to this offer:</label>
                     <div id="timesheetDocumentsId" class="timesheetDocs">
                      <div class="col-md-7 commentFile timesheet_week_comm onboardDocs">
				        <span class="uploadAttachments"> 
			              <div id="savedPociliesAttachments"></div>
				        </span>
                      </div>
                      </div>
                </div>
                <div class="clearfix"></div>
                <p><strong>Supporting Information</strong> <br>
                  <small class="text-muted">This is great place to upload a position description, commission structures, photo of the team , their desk or even a view from their new office.</small> </p>
                <div class="form-group">
                  <label class="col-md-4 control-label mt10">These files will be available in the portal:</label>
                  <div id="timesheetDocumentsId" class="timesheetDocs">
                      <div class="col-md-7 commentFile timesheet_week_comm onboardDocs">
				        <span class="uploadAttachments"> 
			             <div id="savedSupportedAttachments"></div>
				        </span>
                      </div>
                      </div>
                </div>
                <div class="form-footer">
                  <div class="pull-right mr">
                     <input type="button" value="Cancel" class="btn btn-sm btn-default" onClick="self.location='${SourceLeadWeb_atsUrl}/candidateOnBoardingList'">
                    <input type="button" value="Save" id="save_docs_button"  onclick="createTemplateDocs('save');" class="btn btn-sm btn-success">
                    <input type="button" value="Save and Continue" id="save_continue_docs_button" onclick="createTemplateDocs('saveAndContinue');" class="btn btn-sm btn-primary">
                  </div>
                </div>
                </form>
              </div>
                
                
                
            </div>
            <div class="tab-pane" id="tasks">
              <div class="clearfix">
                <h4 class="pull-left">Task List</h4>
                <div class="pull-right">
                  <button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#addTask" onclick="getAssignToUsersList();flushOnboardTask();"><i class="fa fa-plus paddingRight"></i>Add Task</button>
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
                             <input type="hidden" name="onboardTaskId" id="candidateOnboardTaskId" value=""/>
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
                                      Candidate </label>
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
                                      <label><strong>Due after:</strong><span class="text-danger">*</span></label>
                                                                            
                                      <!-- Form inline Due after -->
                                      
                                      <div class="row" id="dueAfterDiv">
										   <div class="col-md-6 paddingRight">
											<label class="small">Months</label> <br>
												<div class="select-field">
													<select class="form-control input-sm" id="taskMonths" data-validation="required" field-name="Due after" name="taskMonths" onchange="validateDueAfter();">
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
 
  <jsp:include page="../masterFooter.jsp" />

  
  
<div id="divAttachmentTd1" style="display: none; float: left;">
			<div id='REPLACEROWIDdocFileuploadFileGroup'>
	  			<input type='file' name='REPLACEROWIDdocFile' id='REPLACEROWIDdocFile' class="timesheetAttachmentValidation" data-validation="required validatedoc" field-name="Policies Document"  onchange='uploadPoliciesDocument(REPLACEROWID)'> 
	  			<i class="fa fa-times" onclick="removePoliciesAttachmentRow(REPLACEROWID);"></i>
	  		</div>
</div>

<div id="divSupportingAttachmentTd1" style="display: none; float: left;">
			<div id='REPLACEROWIDSupportingdocFileuploadFileGroup'>
	  			<input type='file' name='REPLACEROWIDsupportingdocFile' id='REPLACEROWIDsupportingdocFile' class="timesheetAttachmentValidation" data-validation="required validatedoc" field-name="Supporting Document" onchange='uploadSupportingDocument(REPLACEROWID)'> 
	  			<i class="fa fa-times" onclick="removeSupportingAttachmentRow(REPLACEROWID);"></i>
	  		</div>
</div>

<div id="divContractsAttachmentTd1" style="display: none; float: left;">
			<div id='REPLACEROWIDContractsdocFileuploadFileGroup'>
	  			<input type='file' name='REPLACEROWIDcontractsdocFile' id='REPLACEROWIDcontractsdocFile' class="timesheetAttachmentValidation" data-validation="required validatedoc"  field-name="Contract Document" onchange='uploadContractDocument(REPLACEROWID)'> 
	  			<i class="fa fa-times" onclick="removeContractsAttachmentRow(REPLACEROWID);"></i>
	  		</div>
</div>

<div id="divFormsAttachmentTd1" style="display: none; float: left;">
			<div id='REPLACEROWIDFormsdocFileuploadFileGroup'>
	  			<input type='file' name='REPLACEROWIDformsdocFile' id='REPLACEROWIDformsdocFile' class="timesheetAttachmentValidation" data-validation="required validatedoc" field-name="Forms Document"  onchange='uploadFormsDocument(REPLACEROWID)'> 
	  			<i class="fa fa-times" onclick="removeFormsAttachmentRow(REPLACEROWID);"></i>
	  		</div>
</div>


<script>
function getEmploymentType(){
	var employmentType = $("#employmentType").val();
	if(employmentType == 'Other'){
		document.getElementById("otherEmploymentTypeDiv").style.display="block";
		$("#employmentOtherType").attr("data-validation", "required");
		$("#employmentOtherType").removeAttr("data-validation-optional");
	}else{
		document.getElementById("otherEmploymentTypeDiv").style.display="none";
		$("#employmentOtherType").removeAttr("data-validation");
		$("#employmentOtherType").attr("data-validation-optional", "true");
		$('#otherEmploymentTypeDiv').find('p.jquery_form_error_message').remove(); 
	}
	
	
	 if(employmentType == 'C2C'){
		 document.getElementById("vendorsDiv").style.display="";
		 document.getElementById("vendorContactsDiv").style.display="";
		 document.getElementById("otherEmploymentTypeDiv").style.display="none";
		
		 $('#vendors').removeAttr("data-validation-optional"); 
		 $('#vendorContact').removeAttr("data-validation-optional"); 
		 
		 $('#vendors').attr("data-validation","required"); 
		 $('#vendorContact').attr("data-validation","required");  
		 
		 $('#employmentOtherType').removeAttr("data-validation"); 
		 $('#employmentOtherType').attr("data-validation-optional", "true"); 
		 $('#employmentOtherType').attr("data-validation","validate_Space validate_length length2-250"); 
		 
		 
		  var onboardCandiate = document.getElementById("onboardCandiate").value;
		  var candidate = onboardCandiate.split(",");
		  var candidateId = candidate[0];
		  if(candidateId != null && candidateId != "" && candidateId != 'undefined'){
			  $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			  $.ajax({
				   	type: "POST",
				    url: atsUrl+"/getOnBoardingCandidateDetails?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId,	    	   
				    success: function(response) {
				    	if (response.response === "AWKWARD") {
				    	    location.href = "/errorPage";
				    	} else if (response.response === "SUCCESS") {
				    		var json = response.object;
				    		if(json['vendorOrgRelationId'] != null && json['vendorOrgRelationId'] != '' && json['vendorOrgRelationId'] != 'undefined' 
			    				 && json['vendorSpocId'] != null && json['vendorSpocId'] != '' && json['vendorSpocId'] != 'undefined'){
				    			 getVendorsByOrgLocationForAddApplicant(json['vendorOrgRelationId']);
				    		     getAddApplicantVendorContactList(json['vendorOrgRelationId'],json['vendorSpocId']);
				    		 }else{
				    			 getVendorsByOrgLocationForAddApplicant();
				    		 }
				    	} else if (response.indexOf('Sign In') !== -1) {
				    		location.replace(sourceleadUrl);
				    	} else {
				    	    showError("Operation failed, please try again");
				    	}
				    }
			   });
			  $("#loading").removeAttr("style");
		  }
	 }else{
		 document.getElementById("otherEmploymentTypeDiv").style.display="none";
		 document.getElementById("vendorsDiv").style.display="none";
		 document.getElementById("vendorContactsDiv").style.display="none";
		 
		 $('#vendors').removeAttr("data-validation"); 
		 $('#vendorContact').removeAttr("data-validation"); 
		 
		 $('#vendors').attr("data-validation-optional", "true"); 
		 $('#vendorContact').attr("data-validation-optional", "true");   
		 
		 $('#employmentOtherType').removeAttr("data-validation"); 
		 $('#employmentOtherType').attr("data-validation-optional", "true"); 
		 $('#employmentOtherType').attr("data-validation","validate_Space validate_length length2-250"); 
	 }
}
</script>

<script>
 function getVendorsByOrgLocationForAddApplicant(orgRelationId){
	 $.ajax({
	    	type: "GET",
	        url: atsUrl+"/getVendorsByOrgLocation?"+tokenParamName+"="+tokenValue+"", 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	var output = "";
	        	output = output + "<option value=''>--Select Vendor--</option>"; 
	        	for (var i=0; i<json.length; i++)
				{
		        		var opt = json[i];
		        		var selected = "";
		        		if(orgRelationId != null && orgRelationId != 'undefined' && orgRelationId != ''){
		        			if(opt.id == orgRelationId){
		        				selected = "selected";
		        			}
		        			output = output + "<option value='" +opt.id+ "' "+selected+">"+opt.orgName+"</option>";
		        		}else{
		        			output = output + "<option value='" +opt.id+ "'>"+opt.orgName+"</option>";
		        		}
		        		
				}
	        	$("#vendors").empty();
		        $("#vendors").append(output);
		        $('#vendors').trigger("chosen:updated"); 
		        
	        },
	    }); 
	 
 }
 </script>
 
 <script>
 function getAddApplicantVendorContactList(orgRelationId,spocId){
	 
	 var vmsUrl = '${SourceLeadWeb_vmsUrl}';
	 var orgRelationID = '';
	 if(orgRelationId != null && orgRelationId != 'undefined' && orgRelationId != ''){
		 orgRelationID = orgRelationId;
	 }else{
		 orgRelationID = $('#vendors').val();
	 }
		 $.ajax({
			    type: "POST",
		        url: vmsUrl+"/getOrgSpocsList?"+tokenParamName+"="+tokenValue+"&orgRelationId="+orgRelationID+"&type=Vendor",  
		        success: function(json) {
		        	$('#vendorContact').empty();
		        	$('#vendorContact').append('<option value="">--Select Vendor contact--</option>');
		             for(var i=0;i<json.data.length;i++){
		            	 var opt = json.data[i];
		            	 if(spocId != null && spocId != 'undefined' && spocId != ''){
		            		 if(opt.id == spocId){
			        				selected = "selected";
			        			}
		        			 $('#vendorContact').append('<option value='+opt.id+' '+selected+'>'+opt.spocName+'</option>');
		            	 }else{
		            		 $('#vendorContact').append('<option value='+opt.id+' >'+opt.spocName+'</option>');
		            	 }
		            	
		            } 
		            $('#vendorContact').trigger("chosen:updated");
		        }
		 }); 
 }
 </script>
 

<!-- getEmployementTye -->

<script>
function addPoliciesAttachment(){  
			var checkMaxTsRowNum = $("#policiesAttcahmentMaxRowNumber").val();
			var tbl = document.getElementById("policiesAttachmentTable");
			var maxRowNum = parseInt($("input[name = 'policiesAttcahmentMaxRowNumber']").val());
			$("input[name = 'policiesAttcahmentMaxRowNumber']").val(maxRowNum + 1);
			
			
			var row = tbl.insertRow(maxRowNum);
			row.setAttribute("id", "divAttachmentPoliciesRow" + maxRowNum);
			
			
			var cell0 = row.insertCell(0);
			var divAttachmentTdStr = document.getElementById("divAttachmentTd1").innerHTML;
			divAttachmentTdStr = divAttachmentTdStr.replace(/REPLACEROWID/g, (maxRowNum));
			cell0.innerHTML = divAttachmentTdStr;
			
			
}			
</script>
	
<script type="text/javascript">
	function removePoliciesAttachmentRow(rowId) {
		 document.getElementById("divAttachmentPoliciesRow"+rowId).style.display = 'none';
		 document.getElementById("divAttachmentPoliciesRow"+rowId).value= '';
		 $("#templateDocuments_form input[data-id='"+rowId+"docFile']").remove();
		 var  rId = "divAttachmentPoliciesRow"+rowId;
		  var r = document.getElementById("policiesAttachmentTable").rows[rId];
          var count = 2*(r.cells.length);
		    for(i=0;i<=count;i++){
		        r.deleteCell(0);
		    } 
		 
	}
</script>


<script>
function addSupportingAttachment(){  
			var checkMaxTsRowNum = $("#supportingAttcahmentMaxRowNumber").val();
			var tbl = document.getElementById("supportingAttachmentTable");
			var maxRowNum = parseInt($("input[name = 'supportingAttcahmentMaxRowNumber']").val());
			$("input[name = 'supportingAttcahmentMaxRowNumber']").val(maxRowNum + 1);
			
			
			var row = tbl.insertRow(maxRowNum);
			row.setAttribute("id", "divAttachmentSupportingRow" + maxRowNum);
			
			
			var cell0 = row.insertCell(0);
			var divAttachmentTdStr = document.getElementById("divSupportingAttachmentTd1").innerHTML;
			divAttachmentTdStr = divAttachmentTdStr.replace(/REPLACEROWID/g, (maxRowNum));
			cell0.innerHTML = divAttachmentTdStr;
			
			
}			
</script>
	
<script type="text/javascript">
	function removeSupportingAttachmentRow(rowId) {
		 document.getElementById("divAttachmentSupportingRow"+rowId).style.display = 'none';
		 document.getElementById("divAttachmentSupportingRow"+rowId).value= '';
		 $("#templateDocuments_form input[data-id='"+rowId+"supportingdocFile']").remove();
		 var  rId = "divAttachmentSupportingRow"+rowId;
		  var r = document.getElementById("supportingAttachmentTable").rows[rId];
          var count = 2*(r.cells.length);
		    for(i=0;i<=count;i++){
		        r.deleteCell(0);
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
			
			
			var cell0 = row.insertCell(0);
			var divAttachmentTdStr = document.getElementById("divContractsAttachmentTd1").innerHTML;
			divAttachmentTdStr = divAttachmentTdStr.replace(/REPLACEROWID/g, (maxRowNum));
			cell0.innerHTML = divAttachmentTdStr;
			
			
}	
function uploadContractDocument(id){
	fileUpload(""+id+"contractsdocFile", "Candidate-Onboard-Contracts", "templateDocuments_form","contractsFileInfo");
}
function uploadFormsDocument(id){
	fileUpload(""+id+"formsdocFile", "Candidate-Onboard-Forms", "templateDocuments_form","formsFileInfo");
}
function uploadPoliciesDocument(id){
	fileUpload(""+id+"docFile", "Candidate-Onboard-Policies", "templateDocuments_form","polaciesFileInfo");
}
function uploadSupportingDocument(id){
	fileUpload(""+id+"supportingdocFile", "Candidate-Onboard-Supporting", "templateDocuments_form","supportingFileInfo");
}
 
</script>
	
<script type="text/javascript">
	function removeContractsAttachmentRow(rowId) {
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
function addFormsAttachment(){  
			var checkMaxTsRowNum = $("#formsAttcahmentMaxRowNumber").val();
			var tbl = document.getElementById("formsAttachmentTable");
			var maxRowNum = parseInt($("input[name = 'formsAttcahmentMaxRowNumber']").val());
			$("input[name = 'formsAttcahmentMaxRowNumber']").val(maxRowNum + 1);
			
			
			var row = tbl.insertRow(maxRowNum);
			row.setAttribute("id", "divAttachmentFormsRow" + maxRowNum);
			
			
			var cell0 = row.insertCell(0);
			var divAttachmentTdStr = document.getElementById("divFormsAttachmentTd1").innerHTML;
			divAttachmentTdStr = divAttachmentTdStr.replace(/REPLACEROWID/g, (maxRowNum));
			cell0.innerHTML = divAttachmentTdStr;
			
			
}			
</script>
	
<script type="text/javascript">
	function removeFormsAttachmentRow(rowId) {
		 document.getElementById("divAttachmentFormsRow"+rowId).style.display = 'none';
		 document.getElementById("divAttachmentFormsRow"+rowId).value= '';
		 $("#templateDocuments_form input[data-id='"+rowId+"formsdocFile']").remove();
		 var  rId = "divAttachmentFormsRow"+rowId;
		  var r = document.getElementById("formsAttachmentTable").rows[rId];
          var count = 2*(r.cells.length);
		    for(i=0;i<=count;i++){
		        r.deleteCell(0);
		    } 
		 
	}
</script>



<!-- <script type="text/javascript">
function validateFormsUploadFile(id)
{
	 var selectedFile = $('#'+id+'formsdocFile').val();
	  if(validate_fileupload(selectedFile)){
		   var docSize = $('#'+id+'formsdocFile')[0].files[0].size;
	       $("#"+id+"FormsdocFileuploadFileGroup").find('p.jquery_form_error_message').remove();
	      if(docSize > 2097152){
	    	  $('#'+id+'FormsdocFileuploadFileGroup').find('p.jquery_form_error_message').remove();
	    	  $('#'+id+'FormsdocFileuploadFileGroup').find('i').after("<p class='jquery_form_error_message'>Limit is exceeded than 2MB </span>");
			  $('#'+id+'formsdocFile').attr('record-exist','yes');
			  $('#'+id+'formsdocFile').attr('record-exist-errorMsg','Limit is exceeded than 2MB'); 
	      }else{
	    	 $('input#'+id+'formsdocFile').removeAttr( "record-exist record-exist-errormsg");
   		 $('#'+id+'FormsdocFileuploadFileGroup').find('p.jquery_form_error_message').remove(); 
	      }
	  }
	  else{
		      $('#'+id+'FormsdocFileuploadFileGroup').find('p.jquery_form_error_message').remove();
		      $('#'+id+'FormsdocFileuploadFileGroup').find('i').after("<p class='jquery_form_error_message'>Invalid Format</span>");
			  $('#'+id+'formsdocFile').attr('record-exist','yes');
			  $('#'+id+'formsdocFile').attr('record-exist-errorMsg','Invalid Format'); 
	  }
	
}
</script> -->




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

<!-- <script type="text/javascript">
function validateSupportingUploadFile(id)
{
	 var selectedFile = $('#'+id+'supportingdocFile').val();
	  if(validate_fileupload(selectedFile)){
		   var docSize = $('#'+id+'supportingdocFile')[0].files[0].size;
	       $("#"+id+"SupportingdocFileuploadFileGroup").find('p.jquery_form_error_message').remove();
	      if(docSize > 2097152){
	    	  $('#'+id+'SupportingdocFileuploadFileGroup').find('p.jquery_form_error_message').remove();
	    	  $('#'+id+'SupportingdocFileuploadFileGroup').find('i').after("<p class='jquery_form_error_message'>Limit is exceeded than 2MB </span>");
			  $('#'+id+'supportingdocFile').attr('record-exist','yes');
			  $('#'+id+'supportingdocFile').attr('record-exist-errorMsg','Limit is exceeded than 2MB'); 
	      }else{
	    	 $('input#'+id+'supportingdocFile').removeAttr( "record-exist record-exist-errormsg");
   		 $('#'+id+'SupportingdocFileuploadFileGroup').find('p.jquery_form_error_message').remove(); 
	      }
	  }
	  else{
		      $('#'+id+'SupportingdocFileuploadFileGroup').find('p.jquery_form_error_message').remove();
		      $('#'+id+'SupportingdocFileuploadFileGroup').find('i').after("<p class='jquery_form_error_message'>Invalid Format</span>");
			  $('#'+id+'supportingdocFile').attr('record-exist','yes');
			  $('#'+id+'supportingdocFile').attr('record-exist-errorMsg','Invalid Format'); 
	  }
	
}
</script> -->



<!-- <script type="text/javascript">
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
	      }
	  }
	  else{
		      $('#'+id+'docFileuploadFileGroup').find('p.jquery_form_error_message').remove();
		      $('#'+id+'docFileuploadFileGroup').find('i').after("<p class='jquery_form_error_message'>Invalid Format</span>");
			  $('#'+id+'docFile').attr('record-exist','yes');
			  $('#'+id+'docFile').attr('record-exist-errorMsg','Invalid Format'); 
	  }
	
}
</script> -->
  
  
  
  <script>
  function loadOnboardingDetails(){
	  loadOfferAcceptedCandidates();
  }
  </script>
  
  
  <script>
  function loadOfferAcceptedCandidates(candidateId){
		$.ajax({
		   	type: "POST",
		    url: atsUrl+"/getOfferAcceptedCandidatesForOnBoard?${_csrf.parameterName}=${_csrf.token}",	    	   
		    success: function(response) {
		    	if (response.response === "AWKWARD") {
		    	    location.href = "/errorPage";
		    	} else if (response.response === "SUCCESS") {
		    		 var json = JSON.parse(response.object);
		        	 var result = "";
		        	
		        	result = result +'<option disabled selected>--Select Candidate--</option>';
		        	for (var i=0; i<json.length; i++)
					{
		        		var report = json[i];
		        		var selected = '';
		        		if(candidateId != null && candidateId != '' && candidateId != 'undefind'){
		        			if(candidateId == report.candidateId){
		        				selected = 'selected';
		        			}
		        		}
		        		result = result +'<option value='+report.candidateId+","+report.id+' '+selected+' >'+report.candidateName+' ('+report.jobTitle+')</option>';
					}
		        	 
		        	 $("#onboardCandiate").empty();
		           	 $("#onboardCandiate").append(result); 
		           	 $('#onboardCandiate').trigger("chosen:updated");
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
  function getCandidateDetails(){
	  
	  var onboardCandiate = document.getElementById("onboardCandiate").value;
	  var candidate = onboardCandiate.split(",");
	  var candidateId = candidate[0];
	  
	  
	  if(candidateId != null && candidateId != "" && candidateId != 'undefined'){
		  
		  $.ajax({
			   	type: "POST",
			    url: atsUrl+"/getOnBoardingCandidateDetails?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId,	    	   
			    success: function(response) {
			    	if (response.response === "AWKWARD") {
			    	    location.href = "/errorPage";
			    	} else if (response.response === "SUCCESS") {
			    		var json = response.object;
			    		$("#firstName").val(json['firstName']);
				    	 $("#lastName").val(json['lastName']);
				    	 
				    	 if(json.defaultOrgProbationPeriod != "" && json.defaultOrgProbationPeriod != null && json.defaultOrgProbationPeriod !="undefined"){
					    	 $("#probationNotice").val(json.defaultOrgProbationPeriod);
					    	 $('#probationNotice').trigger("chosen:updated");
				    	 }
				    	 
				    	 if(json['mobilePhone'] != "" && json['mobilePhone'] != null && json['mobilePhone'] !="undefined"){
				        		$("#mobileNumber").val(json['mobilePhone']);
				        		$("#mobileNumber").attr("readonly","readonly");
				        	}else{
				        		$("#mobileNumber").removeAttr("readonly");
				        	}
				    	 
				    	 if(json['gender'] != null && json['gender'] != "" && json['gender'] != "undefined"){
				    		 $("input[name='gender'][value='"+json['gender']+"']").prop('checked', true); 
				    		 $("#genderValue").val(json['gender']);
				    		 
				    	 }	 
				    	 
				    	 if(json['dateofBirth'] != null && json['dateofBirth'] != "" && json['dateofBirth'] != "undefined"){
				    		 $("#employeeDateOfBirth").val(json['dateofBirth']); 
				    	 }
				    	 
				    	 $("#personalEmail").val(json['email']);
				    	 $("#offBoardLocation").val(json['location']);
				    	 $("#country").val(json['country']);
				    	 $("#state").val(json['state']);
				    	 $("#city").val(json['city']);
				    	 $("#zipCode").val(json['zipcode']);
				    	 
				    	 validateOnboardCandidate(json['email']);
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
  function getDashboardCandidateDetails(){
	  
	  var onboardCandiate = document.getElementById("onboardCandiate").value;
	  var candidate = onboardCandiate.split(",");
	  var candidateId = candidate[0];
	  
	  
	  
	  if(candidateId != null && candidateId != "" && candidateId != 'undefined'){
		  
		  $.ajax({
			   	type: "POST",
			    url: atsUrl+"/getOnBoardingCandidateDetails?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId,	    	   
			    success: function(response) {
			    	if (response.response === "AWKWARD") {
			    	    location.href = "/errorPage";
			    	} else if (response.response === "SUCCESS") {
			    		var json = response.object;
			    		$("#firstName").val(json['firstName']);
				    	 $("#lastName").val(json['lastName']);
				    	
				    	 
				    	 if(json['mobilePhone'] != null && json['mobilePhone'] != "" && json['mobilePhone'] !="undefined"){
				        		$("#mobileNumber").val(json['mobilePhone']);
				        		$("#mobileNumber").attr("readonly","readonly");
				        	}else{
				        		$("#mobileNumber").removeAttr("readonly");
				        	}
				    	
				    	 if(json['gender'] != null && json['gender'] != "" && json['gender'] != "undefined"){
				    		 $("input[name='gender'][value='"+json['gender']+"']").prop('checked', true); 
				    		 $("#genderValue").val(json['gender']);
				    	 }
				    	 
				    	 if(json['dateofBirth'] != null && json['dateofBirth'] != "" && json['dateofBirth'] != "undefined"){
				    		 $("#employeeDateOfBirth").val(json['dateofBirth']); 
				    	 }
				    	
				    	 $("#personalEmail").val(json['email']);
				    	 if(json['country'] != null && json['country'] != "" && json['country'] != "undefined"){
					    	 $("#country").val(json['country']);
				    	 }else{
					    	 $("#country").removeAttr('readonly');
				    	 }
				    	 if(json['state'] != null && json['state'] != "" && json['state'] != "undefined"){
					    	 $("#state").val(json['state']);
				    	 }else{
					    	 $("#state").removeAttr('readonly');
				    	 }
				    	 if(json['city'] != null && json['city'] != "" && json['city'] != "undefined"){
					    	 $("#city").val(json['city']);
				    	 }else{
					    	 $("#city").removeAttr('readonly');
				    	 }
				    	 if(json['location'] != null && json['location'] != "" && json['location'] != "undefined"){
					    	 $("#offBoardLocation").val(json['location']);
				    	 } 
				    	 if(json['zipCode'] != null && json['zipCode'] != "" && json['zipCode'] != "undefined"){
					    	 $("#zipCode").val(json['zipCode']);
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
  function getOfferDetails(){
	  
	  var onboardCandiate = document.getElementById("onboardCandiate").value;
	  var offer = onboardCandiate.split(",");
	  var offerId = offer[1];
	  
	  
	  
	  var locationCode ='${SourceLeadWeb_locationCode}';
	  
	  if(offerId != null && offerId != "" && offerId != 'undefined'){
		  
		  $.ajax({
			   	type: "POST",
			    url: atsUrl+"/getOnBoardingCandidateOfferDetails?${_csrf.parameterName}=${_csrf.token}&offerId="+offerId+"&locationCode="+locationCode,	    	   
			    success: function(response) {
			    	if (response.response === "AWKWARD") {
			    	    location.href = "/errorPage";
			    	} else if (response.response === "SUCCESS") {
			    		var json = JSON.parse(response.object);
			        	
				    	 if(json['amount'] != null && json['amount'] != "" && json['amount'] != 'undefined'){
				    		 $("#salaryRate").val(json['amount']);
				    		 $("#paymentType").val(json['paymentType']);
					    	 $('#paymentType').trigger("chosen:updated");
					    	 $("#paymentMode").val(json['paymentMode']);
					    	 $('#paymentMode').trigger("chosen:updated");
				    	 }
							if(json['positionType'] != null && json['positionType'] != "" && json['positionType'] != 'undefined'){
				    		 if(json['positionType'] == 'Other'){
				    			 document.getElementById("otherEmploymentTypeDiv").style.display="block";
				    			 $("#employmentOtherType").val(json['otherPositionType']);
				    			 $("#employmentOtherType").attr("data-validation", "required");
				    			 $("#employmentOtherType").removeAttr("data-validation-optional");
				    		 }else{
				    			 $("#employmentOtherType").removeAttr("data-validation");
				    			 $("#employmentOtherType").attr("data-validation-optional","true");
				    			 $('#otherEmploymentTypeDiv').find('p.jquery_form_error_message').remove(); 
				    		 }
				    		 
			    		     $("#employmentType").val(json['positionType']);
					    	 $('#employmentType').trigger("chosen:updated");
					    	 
					    	 if(json['positionType'] = 'C2C'){
					    		 getEmploymentType();
					    		 
					    		 if(json['orgRelationId'] != null && json['orgRelationId'] != '' && json['orgRelationId'] != 'undefined' 
				    				 && json['spocId'] != null && json['spocId'] != '' && json['spocId'] != 'undefined'){
					    			 getVendorsByOrgLocationForAddApplicant(json['orgRelationId']);
					    		     getAddApplicantVendorContactList(json['orgRelationId'],json['spocId']);
					    		 }
				    			
					    	 }
				    		
				    	 }else{
				    		 document.getElementById("otherEmploymentTypeDiv").style.display="none";
				    		 $('#otherEmploymentTypeDiv').find('p.jquery_form_error_message').remove(); 
				    	 } 
				    	 if(json['dateOfJoiningStr'] != null && json['dateOfJoiningStr'] != "" && json['dateOfJoiningStr'] != 'undefined'){
				    		 $("#startDate").val(json['dateOfJoiningStr']);
				    		 var dt = getJSDate(json['dateOfJoiningStr'],'${SourceLeadWeb_orgLocations.dateFormat}');
				 			 dt.setDate(dt.getDate() + 1);
				 			 $("#endDate").datepicker("option", "minDate", dt);
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
  function getStates(){
	  var countryName = $("#country").val();
	  getOnBoardCandidateStates(countryName);
  }
  </script>
  
  <script>
  function getCities(){
	  var countryName = $("#country").val();
	  var stateName = $("#state").val();
	  getOnBoardCandidateCities(countryName,stateName);
  }
  </script>
  
<script type="text/javascript">
function getOnBoardCandidateCountry(country){
	$.ajax({
    	type: "POST",
    	dataType: "json",
    	 url: '${SourceLeadWeb_atsUrl}'+"/countryAutoComplete?${_csrf.parameterName}=${_csrf.token}",
          success: function(data) {
        	var json = JSON.parse(data);
        	$('#country').empty();
        	$('#country').append('<option value="">---Select Country---</option>');
            for(var i=0;i<json.length;i++){
            	if(json[i] == country){
            		$('#country').append('<option value="'+json[i]+'" selected>'+json[i]+'</option>');
            	}
            	else{
            		$('#country').append('<option value="'+json[i]+'">'+json[i]+'</option>');
            	}
            	
            }
            $('#country').trigger("chosen:updated");
        },
        error: function(e){
        }
    }); 
} 
</script>


<script>
function getOnBoardCandidateStates(country,state){
	
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: '${SourceLeadWeb_atsUrl}'+"/getStatesByCountry?${_csrf.parameterName}=${_csrf.token}&countryName="+country, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#state').empty();
	        	$('#state').append('<option value="">---Select State---</option>');
	            for(var i=0;i<json.length;i++){
	            	if(state != null && state != 'undefined' && json[i] == state){
	            		$('#state').append('<option value="'+state+'" selected>'+json[i]+'</option>');
	            	}
	            	else{
	            		$('#state').append('<option value="'+json[i]+'">'+json[i]+'</option>');
	            	}
	            }
	            $('#state').trigger("chosen:updated");
	            
	            $('#city').empty();
	            $('#city').trigger("chosen:updated");
	        },
	        error: function(e){
	        }
	    });
	
}
</script>


<script>
function getOnBoardCandidateCities(country,state,city){
	
	
	if(country != null && country != "" && country !="undefined" && state != null && state != "" && state !="undefined" ){
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: '${SourceLeadWeb_atsUrl}'+"/getCitiesByStateAndCountry?${_csrf.parameterName}=${_csrf.token}&countryName="+country+"&stateName="+state, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#city').empty();
	        	$('#city').append('<option value="">---Select City---</option>');
	            for(var i=0;i<json.length;i++){
	            	var selected = '';
	            	if(city != null && city != "" && city !="undefined" && json[i] == city){
	            		selected = 'selected';
	            	}
	            	$('#city').append('<option value="'+json[i]+'" '+selected+'>'+json[i]+'</option>');
	            }
	            $('#city').trigger("chosen:updated");
	        },
	        error: function(e){
	        }
	    });
	}else{
		$('#city').empty();
    	$('#city').append('<option value="">---Select City---</option>');
    	$('#city').trigger("chosen:updated");
	}
}
</script> 


<script>
function loadCandidateRoles(onBoardRoleId){
	
	
	$.ajax({
	   	type: "POST",
	    url: atsUrl+"/getRolesListForCandidateOnBoarding?${_csrf.parameterName}=${_csrf.token}",	    	   
	    success: function(response) {
	    	 var json = JSON.parse(response.object);
	    	 if (response.response === "AWKWARD") {
		    	    location.href = "/errorPage";
		    	} else if (response.response === "SUCCESS") {
		    		var json = JSON.parse(response.object);
		    		var result = "";
		        	result = result +'<option  selected value="">--Select Role--</option>';
		        	for (var i=0; i<json.length; i++) {
		        		var report = json[i];
		        		var selected = '';
		        		if(onBoardRoleId != null && onBoardRoleId != "" && onBoardRoleId !="undefined" && onBoardRoleId == report.id){
		            		selected = 'selected';
		            	}
		        		result = result +'<option value='+report.id+' '+selected+' >'+report.displayName+'</option>';
					}
		        	 $("#onboardCandidateRole").empty();
		           	 $("#onboardCandidateRole").append(result); 
		           	 $('#onboardCandidateRole').trigger("chosen:updated");
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
function loadTeams(onboardTeamId){
	
	
	$.ajax({
	   	type: "POST",
	    url: atsUrl+"/getTeamsListForCandidateOnBoarding?${_csrf.parameterName}=${_csrf.token}",	    	   
	    success: function(response) {
	    	if (response.response === "AWKWARD") {
	    	    location.href = "/errorPage";
	    	} else if (response.response === "SUCCESS") {
	    	 	var json = JSON.parse(response.object);
	    	 	var result = "";
	        	result = result +'<option selected value="">--Select Team--</option>';
	        	for (var i=0; i<json.length; i++) {
	        		var report = json[i];
	        		var selected = '';
	        		if(onboardTeamId != null && onboardTeamId != "" && onboardTeamId !="undefined" && onboardTeamId == report.id){
	            		selected = 'selected';
	            	}
	        		result = result +'<option value='+report.id+' '+selected+' >'+report.name+'</option>';
				}
	        	 
	        	 $("#team").empty();
	           	 $("#team").append(result); 
	           	 $('#team').trigger("chosen:updated");
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
function loadOnBoardingTemplates(onboardTemplateId){
	
	
	$.ajax({
	   	type: "POST",
	    url: atsUrl+"/getOnboardingTemplatesForCandidateOnBoarding?${_csrf.parameterName}=${_csrf.token}",	    	   
	    success: function(response) {
	    	if (response.response === "AWKWARD") {
	    	    location.href = "/errorPage";
	    	} else if (response.response === "SUCCESS") {
	    		var json = JSON.parse(response.object);
	    		var result = "";
	        	for (var i=0; i<json.length; i++) {
	        		var report = json[i];
	        		if(onboardTemplateId != null && onboardTemplateId != "" && onboardTemplateId !="undefined"){
	        			var templateId = onboardTemplateId.split(",");
	        			var selected = '';
	        			for(var j=0; j<templateId.length; j++){
	        				if(templateId[j] == report.id){
	        					selected = 'selected';
	        				}
	        			}
	        		}
	        		result = result +'<option value='+report.id+' '+selected+'>'+report.templateName+'</option>';
				}
	        	 $("#onboardingTemplate").empty();
	           	 $("#onboardingTemplate").append(result); 
	           	 $('#onboardingTemplate').trigger("chosen:updated");
	           	$('#onboardingTemplate_chosen').find(".chosen-choices").css({"min-height":"34px"});
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
 function getReportingManager(roleId,onBoardManagerId) {
	 var role= '';
	 if(roleId != null && roleId != "" && roleId != 'undefined'){
		 role = roleId;
	 }else{
		 role = document.getElementById("onboardCandidateRole").value; 
	 }
	 
	 var atsUrl ='${SourceLeadWeb_atsUrl}';
	 if(role != null && role != "" && role != 'undefined'){
		 $.ajax({
		    	type: "POST",
		        url: atsUrl+"/getAssociateReportingManager?${_csrf.parameterName}=${_csrf.token}&role="+role, 
		        success: function(json) {
		        	var output = "";
		        	output = output + "<option value=''>-- Select Manager --</option>"; 
		        	for (var i=0; i<json.length; i++)
					{
			        		var opt = json[i];
			        		var selected = '';
			        		if(onBoardManagerId != null && onBoardManagerId != "" && onBoardManagerId !="undefined" && onBoardManagerId == opt.id){
			            		selected = 'selected';
			            	}
			        		output = output +'<option value='+opt.id+'  '+selected+'>'+opt.firstName+' '+opt.lastName+' ('+opt.userType+')</option>';
					}
		        	$("#reportingManager").empty();
			        $("#reportingManager").append(output);
			        $('#reportingManager').trigger("chosen:updated");
		        },
		    });
	 }else{
		 var output = "";
     	 output = output + "<option value=''>-- Select Manager --</option>"; 
     	 $("#reportingManager").empty();
	     $("#reportingManager").append(output);
	     $('#reportingManager').trigger("chosen:updated");
	 }
	
 }
</script>



  
 <script>
 function saveOnboardingCandidateDetails(saveType){
	 
	 var validationSettings = {
		        errorMessagePosition : 'element',
		       borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd' 
	         }; 
	 
	 if ($('#onboardCandidate_form').validate(false, validationSettings)){ 
	        var atsUrl ='${SourceLeadWeb_atsUrl}';
	        $("#save_onboardUser").attr("disabled", "disabled");
	        $("#saveAndContinue_onboardUser").attr("disabled", "disabled");
	        var formData = getFormData1("onboardCandidate_form");
	        $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	         $.ajax({
		    	type: "POST",
		    	 data: formData,
		 		processData: false, 
		 		contentType: false,
		    	 url: atsUrl+"/saveOnboardingCandidateDetails?${_csrf.parameterName}=${_csrf.token}", 
		        success: function(response) {
		        	if (response.response === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (response.response === "SUCCESS") {
		        		var json = response.object;
			        	if(saveType == 'saveAndContinue'){
			        		var $tabs = $('.tabbable li');
			     	        $tabs.filter('.active').next('li').find('a[data-toggle="tab"]').tab('show');
			     	        document.getElementById("templateDocs").disabled = false;
			     	       getTemplatePolicyDocumentsDetails(json.id);
			     	       getTemplateSupportingDocumentsDetails(json.id);
			     	       getTemplateContractsDocumentsDetails(json.id);
			     	       getTemplateFormsDocumentsDetails(json.id);
			     	       $("#documentstemplateId").val(json.id);
			        	}
		     	        $("#candidateOnBoardId").val(json.id);
		     	        $("#savedonboardId").val(json.id);
		     	        $("#save_onboardUser").removeAttr("disabled");
		   	            $("#saveAndContinue_onboardUser").removeAttr("disabled");
		     	        $("#loading").removeAttr("style");
		        	} else if (response.indexOf('Sign In') !== -1) {
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
 
 $('body').on('blur', '#onboardCandidate_form', function() {
		$("#onboardCandidate_form").showHelpOnFocus();
		$("#onboardCandidate_form").validateOnBlur(false, validationSettings);  
	});
 </script> 
 
 
 <script>
 function loadOnboardingCandidate(onBoardingUUID){
	 if(onBoardingUUID != null && onBoardingUUID != "" && onBoardingUUID !="undefined"){
		 onBoardingUUID 
	 }else{
		 onBoardingUUID = document.getElementById("candidateOnBoardUUID").value;
	 }
	 if(onBoardingUUID){
		 
	 addCandidateDetailsForOnboarding();
	
	 var atsUrl ='${SourceLeadWeb_atsUrl}';
	 var locationCode ='${SourceLeadWeb_locationCode}';
	 
	 $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	    $.ajax({
	    	type: "POST",
	    	url: atsUrl+"/loadOnboardingCandidateDetails?${_csrf.parameterName}=${_csrf.token}&onBoardingUUID="+onBoardingUUID+"&locationCode="+locationCode, 
	        success: function(response) {
	        	if (response.response === "AWKWARD") {
        		    location.href = "/errorPage";
        		} else if (response.response === "SUCCESS") {
        			var json = response.object;
        			var result = "";
   	        	 var selected = 'selected';
   	        	 result = result +'<option disabled selected>--Select Candidate--</option>';
   	        	 result = result +'<option value='+json['onBoardCandidateId']+","+json['onBoardOfferId']+' '+selected+' >'+json['candidateName']+' ('+json['jobTitle']+')</option>';
   	        	 $("#onboardCandiate").empty();
   	           	 $("#onboardCandiate").append(result); 
   	           	 $('#onboardCandiate').trigger("chosen:updated");
   	        	$("#firstName").val(json['firstName']);
   	        	if(json['lastName'] != null && json['lastName'] != "" && json['lastName'] !="undefined"){
   	        		$("#lastName").val(json['lastName']);
   	        	}
   	        	$("#address1").val(json['address1']);
   	        	if(json['address2'] != null && json['address2'] != "" && json['address2'] !="undefined"){
   	        		$("#address2").val(json['address2']);
   	        	}
   	        	if(json['mobileNumber'] != null && json['mobileNumber'] != "" && json['mobileNumber'] !="undefined"){
   	        		$("#mobileNumber").val(json['mobileNumber']);
   	        		$("#mobileNumber").attr("readonly","readonly");
   	        	}else{
   	        		$("#mobileNumber").remveAttr("readonly");
   	        	}
   		    	$("#personalEmail").val(json['personalEmail']);
   		    	if(json['companyEmail'] != null && json['companyEmail'] != "" && json['companyEmail'] !="undefined"){
   		    		$("#companyEmail").val(json['companyEmail']);
   		    	}
   		    	if(json['zipCode'] != null && json['zipCode'] != "" && json['zipCode'] !="undefined"){
   		    		$("#zipCode").val(json['zipCode']);
   		    	}
   		    	if(json['country'] != null && json['country'] != "" && json['country'] !="undefined"){
   		    		$("#country").val(json['country']);
   		    	}
   		    	if(json['state'] != null && json['state'] != "" && json['state'] !="undefined"){
   		    		$("#state").val(json['state']);
   		    	}
   		    	if(json['city'] != null && json['city'] != "" && json['city'] !="undefined"){
   		    		$("#city").val(json['city']);
   		    	}
   		    	if(json['location'] != null && json['location'] != "" && json['location'] !="undefined"){
   		    		$("#offBoardLocation").val(json['location']);
   		    	}
   		    	
   		    	if(json['dateOfBirth'] != null && json['dateOfBirth'] != "" && json['dateOfBirth'] !="undefined"){
   		    		$("#employeeDateOfBirth").val(json['dateOfBirth']);
   		    	}
   		    	if(json['gender'] != null && json['gender'] != "" && json['gender'] !="undefined"){
   		    		if(json['gender'] == 'Male'){
   		    			$("#male").prop("checked",true);
   		    		}else if(json['gender'] == 'Female'){
   		    			$("#female").prop("checked",true);
   		    		}else if(json['gender'] == 'Transgender'){
   		    			$("#transgender").prop("checked",true);
   		    		}
		    		$("input[name='gender'][value='"+json['gender']+"']").prop('checked', true); 
		    		$("#genderValue").val(json['gender']);
		    		 
   		    		
   		    	}
   		    	if(json['shift'] != null && json['shift'] != "" && json['shift'] !="undefined"){
   		    		shiftList(json['shift']);
   		    	}
   		    	
   		    	if(json['employmentEligibility'] != null && json['employmentEligibility'] != "" && json['employmentEligibility'] !="undefined"){
   		    	     $("#employmentEligibility").val(json['employmentEligibility']);
			    	 $('#employmentEligibility').trigger("chosen:updated");
			    	 changeCitizenShip()
   		    	}
   		    	if(json['authorizedWorkUntil'] != null && json['authorizedWorkUntil'] != "" && json['authorizedWorkUntil'] !="undefined"){
   		    		$("#authorizedDate").val(json['authorizedWorkUntil']);
   		    	}
   		    	
   		    	if(json['uscisAlien'] != null && json['uscisAlien'] != "" && json['uscisAlien'] !="undefined"){
   		    		$("#uscisAlien").val(json['uscisAlien']);
   		    	}
   		    			
   		    	
   		    	 
   		    	loadCandidateRoles(json['onBoardRoleId']);
   		    	$("#startDate").val(json['joiningDateStr']);
   		    	 var dt = toDate("#startDate");
   	 			 dt.setDate(dt.getDate() + 1);
   	 			 $("#endDate").datepicker("option", "minDate", dt);
   	 			 $("#dueDate").datepicker("option", "minDate", dt);
   		    	if(json['endDateStr'] != null && json['endDateStr'] != "" && json['endDateStr'] !="undefined"){
   		    		$("#endDate").val(json['endDateStr']);
   		    	}
   		    	$("#paymentType").val(json['paymentType']);
   		    	 $('#paymentType').trigger("chosen:updated");
   		    	$("#salaryRate").val(json['salaryRate']);
   		    	$("#paymentMode").val(json['paymentMode']);
   		    	 $('#paymentMode').trigger("chosen:updated");
   		    	if(json['onBoardManagerId'] != null && json['onBoardManagerId'] != "" && json['onBoardManagerId'] !="undefined"){
   		    		getReportingManager(json['onBoardRoleId'],json['onBoardManagerId']);
   		    	}else{
   		    		getReportingManager(json['onBoardRoleId']);
   		    	}
   		    	if(json['onBoardTeamId'] != null && json['onBoardTeamId'] != "" && json['onBoardTeamId'] !="undefined"){
   		    		loadTeams(json['onBoardTeamId']);
   		    	}
   		    	 if(json['employmentType'] != null && json['employmentType'] != "" && json['employmentType'] != 'undefined'){
   		    		 if(json['employmentType'] == 'Other'){
   		    			 document.getElementById("otherEmploymentTypeDiv").style.display="block";
   		    			 $("#employmentOtherType").val(json['employmentOtherType']);
   		    			 $("#employmentOtherType").attr("data-validation", "required");
   		    			 $("#employmentOtherType").removeAttr("data-validation-optional");
   		    		 }else{
   		    			 $("#employmentOtherType").removeAttr("data-validation");
   		    			 $("#employmentOtherType").attr("data-validation-optional","true");
   		    			 $('#otherEmploymentTypeDiv').find('p.jquery_form_error_message').remove(); 
   		    		 }
   	    		     $("#employmentType").val(json['employmentType']);
   			    	 $('#employmentType').trigger("chosen:updated");
   			    	 if(json['employmentType'] == 'C2C'){
   			    		  getEmploymentType();
   			    		  getVendorsByOrgLocationForAddApplicant(json['vendors']);
   			    		  getAddApplicantVendorContactList(json['vendors'],json['vendorContact']);
   			    	 }
   		    	 }else{
   		    		 document.getElementById("otherEmploymentTypeDiv").style.display="none";
   		    		 $('#otherEmploymentTypeDiv').find('p.jquery_form_error_message').remove(); 
   		    	 }
   		    	if(json['probationNotice'] != null && json['probationNotice'] != "" && json['probationNotice'] !="undefined"){
   		    		$("#probationNotice").val(json['probationNotice']);
   			    	$('#probationNotice').trigger("chosen:updated");
   		    	}
   		    	if(json['terminationNotice'] != null && json['terminationNotice'] != "" && json['terminationNotice'] !="undefined"){
   		    		$("#terminationNotice").val(json['terminationNotice']);
   			    	$('#terminationNotice').trigger("chosen:updated");
   		    	}
   		    	if(json['dueDateStr'] != null && json['dueDateStr'] != "" && json['dueDateStr'] !="undefined"){
   		    		$("#dueDate").val(json['dueDateStr']);
   		    	}
   		    	if(json['specialConditions'] != null && json['specialConditions'] != "" && json['specialConditions'] !="undefined"){
   		    		$("#specialConditions").val(json['specialConditions']);
   		    	}
   		    	if(json['onboardTemplateId'] != null && json['onboardTemplateId'] != "" && json['onboardTemplateId'] !="undefined"){
   		    		loadOnBoardingTemplates(json['onboardTemplateId']);
   		    	}
   		    	$("#savedonboardId").val(json['id']);
   		    	$("#loading").removeAttr("style");
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
 function loadOnboardCandidateFormDashobard(candidateId,offerId){
	 var candidateName = '${candidateName}';
	 var jobTitle = '${jobTitle}';
	 var candidateEmail ='${candidateEmail}';
	 var result = "";
	 var selected = 'selected';
	 result = result +'<option disabled selected>--Select Candidate--</option>';
	 result = result +'<option value='+candidateId+","+offerId+' '+selected+' >'+candidateName+' ('+jobTitle+')</option>';
	 $("#onboardCandiate").empty();
   	 $("#onboardCandiate").append(result); 
   	 $('#onboardCandiate').trigger("chosen:updated");
   	 getDashboardCandidateDetails();
     validateOnbardUserByCandidateId(candidateId,candidateName);
   	 getOfferDetails();
     addCandidateDetailsForOnboarding(); 	
 }
 </script> 
 
 <script>
function getTemplatePolicyDocumentsDetails(onboardId){
	$("#templateDocuments_form input[name*='contractsFileInfo']").remove();
	$("#templateDocuments_form input[name*='formsFileInfo']").remove();
	$("#templateDocuments_form input[name*='polaciesFileInfo']").remove();
	$("#templateDocuments_form input[name*='supportingFileInfo']").remove();
	
	if(onboardId == '' || onboardId == null || onboardId =='undefined'){
		onboardId = document.getElementById("candidateOnBoardId").value;
	}
	var documentType = "Policies"
	if(onboardId){
		var atsUrl ='${SourceLeadWeb_atsUrl}';
	     $.ajax({
		    	type: "POST",
		    	 url: atsUrl+"/getCandidateOnboardTemplateDocumentByOnboardId?${_csrf.parameterName}=${_csrf.token}&onboardId="+onboardId+"&documentType="+documentType, 
		        success: function(json) {
		        	
		        	 var result = "";
		        	 
                	 for (var i = 0; i < json.length; i++) { 
                		 var opt = json[i]; 
                		 result = result +"<strong id='policydocument"+opt.id+"'>";
           				 result = result +"<i class=''><a class='glyphicon glyphicon-save' href=\""+opt.fileURL+"\"></a></i>";
           				 result = result +"<input type='button' value='"+opt.fileName+"'>";
           				 result = result +"</a>";
           				 
           				
           				 if(opt.onboardDocumentType != 'Candidate Documents'){
           					result = result +"<input class='dele' type='button' onClick='deleteTemplateDocument("+opt.id+",\""+documentType+"\");' >"; 
           				 }else{
           					result = result +"<input class='dele' type='button' onClick='deleteCandidateOnboardDocument(\""+opt.documentUUID+"\","+opt.id+",\""+documentType+"\");' >";
           				 }
           				 
           				 result = result +"</strong>";
           				
           				 if(opt.onboardDocumentType != 'Candidate Documents'){
           					result = result +"<input type='hidden' name='savedPoliciesAttcahments' id='policyAttachment"+opt.id+"' value='"+opt.id+"' />";
           				}
           				 
                	 }
                	
                	 result = result +"<input type='hidden' name='policiesAttcahmentMaxRowNumber'  id='policiesAttcahmentMaxRowNumber' value='1' />";
                	 result = result +"<table class='timesheetAttachment' id='policiesAttachmentTable' cellpadding='0' cellspacing='0'>";
                	 result = result +"<tr id='divAttachmentTsRow0'>";
                	 result = result +"<td><div id='0docFileuploadFileGroup'><input type='file'  id='0docFile' field-name='Attachment' onchange='uploadPoliciesDocument(0)' data-validation='validatedoc' data-validation-optional='true' class='timesheetAttachmentValidation'><i class='fa fa-plus' onClick='addPoliciesAttachment()'></i></div></td>";
                	 result = result +"</tr>";
                	 result = result +"</table>";
                	 $("#savedPociliesAttachments").empty();
                	 
                	 $("#savedPociliesAttachments").append(result);
                	 
                	 $("#onboardTasksList").empty();
                	 
			        
		        },
		    });
	}
}
</script>

<script>
function getTemplateSupportingDocumentsDetails(onboardId){
	if(onboardId == '' || onboardId == null || onboardId =='undefined'){
		onboardId = document.getElementById("candidateOnBoardId").value;
	}
	var documentType = "Supported"
	if(onboardId){
		var atsUrl ='${SourceLeadWeb_atsUrl}';
	     $.ajax({
		    	type: "POST",
		    	 url: atsUrl+"/getCandidateOnboardTemplateDocumentByOnboardId?${_csrf.parameterName}=${_csrf.token}&onboardId="+onboardId+"&documentType="+documentType, 
		        success: function(json) {
		        	
		        	 var result = "";
		        	 
                	 for (var i = 0; i < json.length; i++) { 
                		 var opt = json[i]; 
                		 result = result +"<strong id='supportdocument"+opt.id+"'>";
                		 result = result +"<i class=''><a class='glyphicon glyphicon-save' href=\""+opt.fileURL+"\"></a></i>";
           				 result = result +"<input type='button' value='"+opt.fileName+"'>";
           				 result = result +"</a>";
           				
           				 if(opt.onboardDocumentType != 'Candidate Documents'){
            					result = result +"<input class='dele' type='button' onClick='deleteTemplateDocument("+opt.id+",\""+documentType+"\");' >"; 
           				 }else{
           					result = result +"<input class='dele' type='button' onClick='deleteCandidateOnboardDocument(\""+opt.documentUUID+"\","+opt.id+",\""+documentType+"\");' >";
           				 }
           				
           				 result = result +"</strong>";
           				 
           				 if(opt.onboardDocumentType != 'Candidate Documents'){
           					result = result +"<input type='hidden' name='savedSupportedAttcahments' id='supportedAttachment"+opt.id+"' value='"+opt.id+"' />";
           				 }
           				
                	 }
                	 
                	 result = result +"<input type='hidden' name='supportingAttcahmentMaxRowNumber'  id='supportingAttcahmentMaxRowNumber' value='1' />";
                	 result = result +"<table class='timesheetAttachment' id='supportingAttachmentTable' cellpadding='0' cellspacing='0'>";
                	 result = result +"<tr id='divAttachmentTsRow0'>";
                	 result = result +"<td><div id='0SupportingdocFileuploadFileGroup'><input type='file' id='0supportingdocFile' data-validation='validatedoc' data-validation-optional='true' field-name='Attachment' onchange='uploadSupportingDocument(0)'  class='timesheetAttachmentValidation'><i class='fa fa-plus' onClick='addSupportingAttachment()'></i></div></td>";
                	 result = result +"</tr>";
                	 result = result +"</table>";
                	 $("#savedSupportedAttachments").empty();
                	 $("#savedSupportedAttachments").append(result);
                	 
                	 $("#onboardTasksList").empty();
                	 
			        
		        },
		    });
	}
}
</script>



<script>
function getTemplateContractsDocumentsDetails(onboardId){
	if(onboardId == '' || onboardId == null || onboardId =='undefined'){
		onboardId = document.getElementById("candidateOnBoardId").value;
	}
	var documentType = "Contracts"
	if(onboardId){
		var atsUrl ='${SourceLeadWeb_atsUrl}';
	     $.ajax({
		    	type: "POST",
		    	 url: atsUrl+"/getCandidateOnboardTemplateDocumentByOnboardId?${_csrf.parameterName}=${_csrf.token}&onboardId="+onboardId+"&documentType="+documentType, 
		        success: function(json) {
		        	
		        	 var result = "";
		        	 
                	 for (var i = 0; i < json.length; i++) { 
                		 var opt = json[i]; 
                		 result = result +"<strong id='contractsdocument"+opt.id+"'>";
                		 result = result +"<i class=''><a class='glyphicon glyphicon-save' href=\""+opt.fileURL+"\"></a></i>";
           				 result = result +"<input type='button' value='"+opt.fileName+"'>";
           				 result = result +"</a>";
           				
           				 if(opt.onboardDocumentType != 'Candidate Documents'){
            					result = result +"<input class='dele' type='button' onClick='deleteTemplateDocument("+opt.id+",\""+documentType+"\");' >"; 
           				 }else{
           					result = result +"<input class='dele' type='button' onClick='deleteCandidateOnboardDocument(\""+opt.documentUUID+"\","+opt.id+",\""+documentType+"\");' >";
           				 }
           				
           				 result = result +"</strong>";
           				 
           				 if(opt.onboardDocumentType != 'Candidate Documents'){
           					result = result +"<input type='hidden' name='savedContractsAttcahments' id='contractsAttachment"+opt.id+"' value='"+opt.id+"' />";
           				 }
           				
                	 }
                	 
                	 result = result +"<input type='hidden' name='contractsAttcahmentMaxRowNumber'  id='contractsAttcahmentMaxRowNumber' value='1' />";
                	 result = result +"<table class='timesheetAttachment' id='contractsAttachmentTable' cellpadding='0' cellspacing='0'>";
                	 result = result +"<tr id='divAttachmentTsRow0'>";
                	 result = result +"<td><div id='0ContractsdocFileuploadFileGroup'><input type='file'  id='0contractsdocFile' field-name='Attachment' data-validation='validatedoc' data-validation-optional='true'    onChange='uploadContractDocument(0);' class='timesheetAttachmentValidation'><i class='fa fa-plus' onClick='addContractsAttachment()'></i></div></td>";
                	 result = result +"</tr>";
                	 result = result +"</table>";
                	 $("#savedContractsAttachments").empty();
                	 $("#savedContractsAttachments").append(result);
                	 
                	 $("#onboardTasksList").empty();
                	 
                	 
			        
		        },
		    });
	}
}
</script>




<script>
function getTemplateFormsDocumentsDetails(onboardId){
	if(onboardId == '' || onboardId == null || onboardId =='undefined'){
		onboardId = document.getElementById("candidateOnBoardId").value;
	}
	var documentType = "Forms"
	if(onboardId){
		var atsUrl ='${SourceLeadWeb_atsUrl}';
	     $.ajax({
		    	type: "POST",
		    	 url: atsUrl+"/getCandidateOnboardTemplateDocumentByOnboardId?${_csrf.parameterName}=${_csrf.token}&onboardId="+onboardId+"&documentType="+documentType, 
		        success: function(json) {
		        	
		        	 var result = "";
		        	 
                	 for (var i = 0; i < json.length; i++) { 
                		 var opt = json[i]; 
                		 result = result +"<strong id='formsdocument"+opt.id+"'>";
                		 result = result +"<i class=''><a class='glyphicon glyphicon-save' href=\""+opt.fileURL+"\"></a></i>";
           				 result = result +"<input type='button' value='"+opt.fileName+"'>";
           				 result = result +"</a>";
           				
           				 if(opt.onboardDocumentType != 'Candidate Documents'){
            					result = result +"<input class='dele' type='button' onClick='deleteTemplateDocument("+opt.id+",\""+documentType+"\");' >"; 
           				 }else{
           					result = result +"<input class='dele' type='button' onClick='deleteCandidateOnboardDocument(\""+opt.documentUUID+"\","+opt.id+",\""+documentType+"\");' >";
           				 }
           				
           				 result = result +"</strong>";
           				 
           				 if(opt.onboardDocumentType != 'Candidate Documents'){
           					result = result +"<input type='hidden' name='savedFormsAttcahments' id='formsAttachment"+opt.id+"' value='"+opt.id+"' />";
           				 }
           				
                	 }
                	 
                	 result = result +"<input type='hidden' name='formsAttcahmentMaxRowNumber'  id='formsAttcahmentMaxRowNumber' value='1' />";
                	 result = result +"<table class='timesheetAttachment' id='formsAttachmentTable' cellpadding='0' cellspacing='0'>";
                	 result = result +"<tr id='divAttachmentTsRow0'>";
                	 result = result +"<td><div id='0FormsdocFileuploadFileGroup'><input type='file' id='0formsdocFile' field-name='Attachment' data-validation='validatedoc' data-validation-optional='true' onchange='uploadFormsDocument(0)'  class='timesheetAttachmentValidation'><i class='fa fa-plus' onClick='addFormsAttachment()'></i></div></td>";
                	 result = result +"</tr>";
                	 result = result +"</table>";
                	 $("#savedFormsAttachments").empty();
                	 $("#savedFormsAttachments").append(result);
                	 
                	 $("#onboardTasksList").empty();
                	 
                	 
			        
		        },
		    });
	}
}
</script>

  
  
  
<script>
function deleteTemplateDocument(documentId,documentType){
	
	if(documentType == 'Policies'){
		document.getElementById("policydocument"+documentId).remove();
		document.getElementById("policyAttachment"+documentId).remove();
	}
	else if(documentType == 'Supported'){
		document.getElementById("supportdocument"+documentId).remove();
		document.getElementById("supportedAttachment"+documentId).remove();
	}
	else if(documentType == 'Contracts'){
		document.getElementById("contractsdocument"+documentId).remove();
		document.getElementById("contractsAttachment"+documentId).remove();
	}
	else if(documentType == 'Forms'){
		document.getElementById("formsdocument"+documentId).remove();
		document.getElementById("formsAttachment"+documentId).remove();
	}
	
	 
}
</script>

<script>
function deleteCandidateOnboardDocument(documentUUID,documentId,documentType){
	
	var atsUrl ='${SourceLeadWeb_atsUrl}';
	   $.ajax({
	    	type: "POST",
	    	 url: atsUrl+"/deleteTemplateDocumentByDocumentUUID?${_csrf.parameterName}=${_csrf.token}&documentUUID="+documentUUID, 
	        success: function(response) {
	        	if (response && response.response === "INVALID_DATA") {
	        	    showError(response.responseMessage);
	        	} else if (response.response === "AWKWARD") {
	        	    location.href = "/errorPage";
	        	} else if (response.response === "SUCCESS") {
	        		if(documentType == 'Policies'){
	        			document.getElementById("policydocument"+documentId).remove();
	        		}
	        		else if(documentType == 'Supported'){
	        			document.getElementById("supportdocument"+documentId).remove();
	        		}
	        		else if(documentType == 'Contracts'){
	        			document.getElementById("contractsdocument"+documentId).remove();
	        		}
	        		else if(documentType == 'Forms'){
	        			document.getElementById("formsdocument"+documentId).remove();
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
function createTemplateDocs(saveType){
	var validationSettings = {
	        errorMessagePosition : 'element',
	       borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
         }; 
	
	  var atsUrl ='${SourceLeadWeb_atsUrl}';
	  if ($('#templateDocuments_form').validate(false, validationSettings)){ 
		   $("#save_docs_button").attr("disabled", "disabled");
		   $("#save_continue_docs_button").attr("disabled", "disabled");
		   var formData = getFormData1("templateDocuments_form");
	       $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		   $.ajax({
	        type : "POST",
		      data: formData,
		      processData: false, 
		 	  contentType: false,
	    	  url: atsUrl+"/saveCandidatedOnboardingTemplateDocuments?${_csrf.parameterName}=${_csrf.token}",		
		      success :function(response) {
		    	  	if (response.response === "AWKWARD") {
		    		    location.href = "/errorPage";
		    	  	} else if (response.response === "SUCCESS") {
		    	  		if(saveType == 'saveAndContinue') {
			        		var $tabs = $('.tabbable li');
			     	        $tabs.filter('.active').next('li').find('a[data-toggle="tab"]').tab('show');
			     	        document.getElementById("templateTasks").disabled = false;
			     	        getOnboardingTasksList();
			        	} else {
			        		getTemplatePolicyDocumentsDetails();
			        		getTemplateSupportingDocumentsDetails();
			        		getTemplateContractsDocumentsDetails();
				     	    getTemplateFormsDocumentsDetails();
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
	var candidateOnBoardId = document.getElementById("candidateOnBoardId").value;
    $("#candidateOnboardTaskId").val(candidateOnBoardId);
	$.ajax({
	   	type: "POST",
	    url: atsUrl+"/getOnboardTasksAssignToUsersList?${_csrf.parameterName}=${_csrf.token}",	    	   
	    success: function(response) {
	    	if (response.response === "AWKWARD") {
	    	    location.href = "/errorPage";
	    	} else if (response.response === "SUCCESS") {
	    		var json = response.data;
	    		var result = "";
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

<script>
function saveOnboardTask(){
	
	var validationSettings = {
	        errorMessagePosition : 'element',
	       borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
         }; 
	
	  if ($('#addonboardTask_form').validate(false, validationSettings)){ 
		    var atsUrl ='${SourceLeadWeb_atsUrl}';
	        var formData = getFormData1("addonboardTask_form");
	    	var tasksIds = document.getElementsByName("savedTemplateTask");
	    	var tasks = '';
	    	for(var i=0; i < tasksIds.length; i++){
	    		tasks = tasks+tasksIds[i].value+",";
	    	}
	    	
	    	tasks = tasks.substring(0, tasks.length - 1);
	       
	        $("#onboardtasksLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	        $.ajax({
		    	type: "POST",
		    	 data: formData,
		    	 processData: false,
			     contentType:false,
		    	 url: atsUrl+"/saveCandidateOnBoardTasks?${_csrf.parameterName}=${_csrf.token}&tasks="+tasks, 
		        success: function(response) {
		        	if (response.response === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (response.response === "SUCCESS") {
		        		$("#onboardtasksLoading").removeAttr("style");
			        	flushOnboardTask();
			        	getAssignToUsersList();
			        	getOnboardingTasksList();
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

$('body').on('blur', '#addonboardTask_form', function() {
	$("#addonboardTask_form").showHelpOnFocus();
	$("#addonboardTask_form").validateOnBlur(false, validationSettings);  
});
</script>

<script>
function getOnboardingTasksList(){
	var onboardId = document.getElementById("candidateOnBoardId").value;
	
	var locationId = '${SourceLeadWeb_orgLocationId}';
	
	if(onboardId != null && onboardId != '' && onboardId!= 'undefined'){
	
	 $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	$.ajax({
	   	type: "POST",
	    url: atsUrl+"/getCandidateOnboardTasksList?${_csrf.parameterName}=${_csrf.token}&onboardId="+onboardId,	    	   
	    success: function(json) {
        	 var result = "";
        	 
        	if(json.length > 0){ 
	        	for (var i=0; i<json.length; i++)
				{
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
	        		
	        		result = result +'<div class="clearfix taskwidget_inner" id="onboardTask'+report.id+'">';
	        		result = result +'<div class="media-body">';
	        		result = result +'<div class="clearfix taskheder">';
	        		
	        		result = result +"<label> <span class='paddingRight'>Subject:</span> <strong class='text-primary'>"+report.taskName+"</strong<a href='#'><i class='fa fa-trash text-danger paddingLeft' onClick='deleteTask("+report.id+",\""+report.onboardTaskType+"\");'></i></a></label>";
	        		
	        		result = result +'<label class="small pull-right text-muted"> <span>'+report.dueAfterString+'</span> </label>';
	        		result = result +'</div>';
	        		result = result +'<ul class="list-inline list-description mt10">';
	        		
	        		result = result +'<li><span>Assign To:</span>';
	        		
	        		if(report.usersList.length > 0){
	        			for(var j=0; j<report.usersList.length; j++){
		 					result = result +'<label>'+report.usersList[j]+'</label>';
		 				}
	        		}else{
	 					result = result +'Onboarding Candidate';
	 				}
	        		result = result +'</li>';
	        		
	        		result = result +'<li><span>Priority:</span>'+prority+'</li>';
	        		result = result +'<li><span>Repeat:</span> '+repeat+'</li>';
	        		result = result +'</ul>';
	        		result = result +'</div>';
	        		result = result +'</div>';
        		   
	        		if(report.onboardTaskType != 'Candidate Task'){
       					result = result +"<input type='hidden' name='savedTemplateTask' id='templateTask"+report.id+"' value='"+report.id+"' />";
       				}
				}
        	}else{
        		result = result +'<div><h4>No Tasks Found</h4></div>';
        	}
        	
        	result = result +'<div class="form-footer clearfix mt">';
    		result = result +'<div class="pull-right mr">';
    		result = result +'<input type="button" id="finish_task" value="Send" onclick="saveAndFinishOnboardTemplate('+onboardId+');" class="btn btn-sm btn-primary">';
    		result = result +'</div>';
    		result = result +'</div>';
    		
    		 $("#onboardTasksList").empty();
           	 $("#onboardTasksList").append(result); 
    		
        	 
	    }
   }); 
	
  $("#loading").removeAttr("style");
}
	
}
</script>


<script>
function deleteTask(taskId,taskType){
	if(taskType != 'Candidate Task'){
		document.getElementById("onboardTask"+taskId).remove();
		document.getElementById("templateTask"+taskId).remove()
	}else{
		var atsUrl ='${SourceLeadWeb_atsUrl}';
		   $.ajax({
		    	type: "POST",
		    	 url: atsUrl+"/deleteOnboardTaskByTaskId?${_csrf.parameterName}=${_csrf.token}&taskId="+taskId, 
		        success: function(response) {
		        	if (response && response.response === "INVALID_DATA") {
	        		    showError(response.responseMessage);
	        		} else if (response.response === "AWKWARD") {
	        		    location.href = "/errorPage";
	        		} else if (response.response === "SUCCESS") {
	        			document.getElementById("onboardTask"+taskId).remove();
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
function saveAndFinishOnboardTemplate(onboardId){
	
	$("#finish_task").attr("disabled", "disabled");
	
	var tasksIds = document.getElementsByName("savedTemplateTask");
	var tasks = '';
	for(var i=0; i < tasksIds.length; i++){
		tasks = tasks+tasksIds[i].value+",";
	}
	
	tasks = tasks.substring(0, tasks.length - 1);
	
	
	 $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	$.ajax({
	   	type: "POST",
	    url: atsUrl+"/saveAndFinishCandidateOnboard?${_csrf.parameterName}=${_csrf.token}&onboardId="+onboardId+"&tasks="+tasks, 	    	   
	    success: function(response) {
	    	if (response.response === "AWKWARD") {
	    	    location.href = "/errorPage";
	    	} else if (response.response === "SUCCESS") {
		    	$("#finish_task").removeAttr("disabled");
		    	location.replace(atsUrl+"/candidateOnBoardingList"); 
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
 function validateUserEmail() {
	 var email = document.getElementById("companyEmail").value;
	 $('#emailDiv').find('p.jquery_form_error_message').remove();
	 var atsUrl ='${SourceLeadWeb_atsUrl}';
	 if(email != null && email != ""){
		 $("#save_onboardUser").attr("disabled", "disabled");
		 $("#saveAndContinue_onboardUser" ).attr("disabled", "disabled");
		 $.ajax({
		    	type: "POST",
		        url: atsUrl+"/validateOnboardCandidateEmail?${_csrf.parameterName}=${_csrf.token}&email="+email, 
		        success: function(response) {
		        	if (response && response.response === "INVALID_USER") {
		        	    showError(response.responseMessage);
		        	} else if (response.response === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (response.response === "SUCCESS") {
		        		if(response.object == 'Invalid User Email'){
			        		  $('#emailDiv').find('p.jquery_form_error_message').remove();
							  $("input#companyEmail").after("<p class='jquery_form_error_message'>"+email+" is Already as user</span>");
							  document.getElementById("companyEmail").setAttribute('record-exist','yes');
							  document.getElementById("companyEmail").setAttribute('record-exist-errorMsg',email+' is as user'); 
			        	}
			        	else if(response.object == 'Invalid OnboardUser Email'){
			        		  $('#emailDiv').find('p.jquery_form_error_message').remove();
							  $("input#companyEmail").after("<p class='jquery_form_error_message'>"+email+" is Already as onboard user</span>");
							  document.getElementById("companyEmail").setAttribute('record-exist','yes');
							  document.getElementById("companyEmail").setAttribute('record-exist-errorMsg',email+' is as user'); 
			        	}
			        	else{
			        		 $('input#companyEmail').removeAttr( "record-exist record-exist-errormsg");
			        		 $('#emailDiv').find('p.jquery_form_error_message').remove(); 
	                         $("#save_onboardUser").removeAttr("disabled");
	                         $("#saveAndContinue_onboardUser").removeAttr("disabled");
			        	}
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
 function validateOnboardCandidate(email){
	 var atsUrl ='${SourceLeadWeb_atsUrl}';
	  if(email){
		  $("#save_onboardUser").attr("disabled", "disabled");
			 $("#saveAndContinue_onboardUser" ).attr("disabled", "disabled");
			 $.ajax({
			    	type: "POST",
			        url: atsUrl+"/validateOnboardCandidate?${_csrf.parameterName}=${_csrf.token}&email="+email, 
			        success: function(response) {
			        	if (response.response === "AWKWARD") {
			        	    location.href = "/errorPage";
			        	} else if (response.response === "SUCCESS") {
			        		if(response.object == 'Invalid'){
				        		alertMessage(email+" is Already exists as user")
				        	} else{
		                         $("#save_onboardUser").removeAttr("disabled");
		                         $("#saveAndContinue_onboardUser").removeAttr("disabled");
				        	}
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
 function validateOnbardUserByCandidateId(candidateId,candidateName){
	 var atsUrl ='${SourceLeadWeb_atsUrl}';
	  if(candidateId){
			 $.ajax({
			    	type: "POST",
			        url: atsUrl+"/validateOnboardCandidateFromDashboard?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId, 
			        success: function(response) {
			        	if (response.response === "AWKWARD") {
			        	    location.href = "/errorPage";
			        	} else if (response.response === "FAILED") {
			        		 $("#save_onboardUser").attr("disabled", "disabled");
			    			 $("#saveAndContinue_onboardUser" ).attr("disabled", "disabled");
			        		 alertMessage(candidateName+" is Already exists as onboard user");	
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
 
 <script type="text/javascript">
function alertMessage(message){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a posiF'>";
	result = result +"		<p>"+message+"</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input class='fullBtn' id='confirm-id' type='button' value='Ok' onclick='cancel();'></b>";
	result = result +"	</div>";
	$("#onboardingPopUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);

}
</script>

<script type="text/javascript">
function cancel(){
	$("#onboardingPopUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>


<script>
function addCandidateDetailsForOnboarding(){
	var loginCountry = "${SourceLeadWeb_country}";
	 $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 var result = "";
	 result = result +'<div class="form-group">';
	 result = result +'<label class="col-md-3 control-label">First Name <span class="text-danger">*</span></label>';
	 result = result +'<div class="col-md-6">';
	 result = result +'<input type="text" class="form-control input-sm" id="firstName" name="firstName" filed-name="First Name" readonly="readonly">';
	 result = result +'</div>';
	 result = result +'</div>';
	 result = result +'<div class="form-group">';
	 result = result +'<label class="col-md-3 control-label">Last Name</label>';
	 result = result +'<div class="col-md-6">';
	 result = result +'<input type="text" class="form-control input-sm" id="lastName" name="lastName" value="" filed-name="Last Name" data-validation-optional="true" data-validation="validate_Space validate_AlphaNumber validate_length length1-100">';
	 result = result +'</div>';
	 result = result +'</div>';
	 result = result +'<div class="form-group">';
	 result = result +'<label class="col-md-3 control-label">Street Address 1</label>';
	 result = result +'<div class="col-md-6">';
	 result = result +'<input type="text" class="form-control input-sm" id="address1" name="address1" field-name="Street Address 1" data-validation-optional="true" data-validation="validate_Space">';
	 result = result +'</div>';
	 result = result +'</div>';
	 result = result +'<div class="form-group">';
	 result = result +'<label class="col-md-3 control-label">Street Address 2</label>';
	 result = result +'<div class="col-md-6">';
	 result = result +'<input type="text" class="form-control input-sm" id="address2" name="address2" field-name="Street Address 2" data-validation-optional="true"  data-validation="validate_Space">';
	 result = result +'</div>';
	 result = result +'</div>';
     
	 result = result +'<div class="form-group">';
	 result = result +'<label class="col-md-3 control-label">Location <span class="text-danger">*</span></label>';
	 result = result +'<div class="col-md-6">';
	 result = result +'<input type="text" class="form-control input-sm" name="location"  id="offBoardLocation" placeholder="Location" field-name="Location" data-validation="required" >';
	 result = result + '<i class="fa fa-refresh btn btn-primary profilerefresh-icon" title="Refresh" onclick="refreshGoogleApiLocation(\'offBoardLocation\',\'country\',\'state\',\'city\',\'zipCode\');"></i>';
	 result = result +'</div>';
	 result = result +'</div>';
	 
	 result = result +'<div class="form-group">';
	 result = result +'<label class="col-md-3 control-label">Country <span class="text-danger">*</span></label>';
	 result = result +'<div class="col-md-6">';
	 result = result +'<input type="text" class="form-control" data-placeholder="Country" id="country" field-name="Country" name="country" readonly data-validation="required">';
	 result = result +'</div>';
	 result = result +'</div>';

	 result = result +'<div class="form-group">';
	 result = result +'<label class="col-md-3 control-label">State</label>';
	 result = result +'<div class="col-md-6">';
	 result = result +'<input type="text" class="form-control" data-placeholder="State"  id="state" name="state" readonly>';
	 result = result +'</div>';
	 result = result +'</div>';
	 
	 result = result +'<div class="form-group">';
	 result = result +'<label class="col-md-3 control-label">City</label>';
	 result = result +'<div class="col-md-6">';
	 result = result +'<input type="text" class="form-control" data-placeholder="City" id="city" name="city" readonly>';
	 result = result +'</div>';
	 result = result +'</div>';
	 
	 result = result +'<div class="form-group">';
	 result = result +'<label class="col-md-3 control-label">Zipcode</label>';
	 result = result +'<div class="col-md-6">';
	 result = result +'<input type="text" class="form-control input-sm" id="zipCode" name="zipCode"  data-validation-optional="true"  data-validation="validate_Space validate_length length1-10" >';
	 result = result +'</div>';
	 result = result +'</div>';

	 result = result +'<div class="form-group">';
	 result = result +'<label class="col-md-3 control-label">Mobile Number <span class="text-danger">*</span></label>';
	 result = result +'<div class="col-md-6">';
	 result = result +'<input type="text" class="form-control input-sm" id="mobileNumber" name="mobileNumber" field-name="Mobile Number" data-validation="required validate_Space validate_length length9-20 validate_phone" >';
	 result = result +'</div>';
	 result = result +'</div>';

     
	 result = result +'<div class="form-group">';
	 result = result +'<label class="col-md-3 control-label">Personal Email <span class="text-danger">*</span></label>';
	 result = result +'<div class="col-md-6">';
	 result = result +'<input type="text" class="form-control input-sm" id="personalEmail" name="personalEmail" readonly="readonly">';
	 result = result +'</div>';
	 result = result +'</div>';
     
	 result = result +'<div class="form-group">';
	 result = result +'<label class="col-md-3 control-label">Date of Birth</label>';
	 result = result +'<div class="col-md-6">';
	 result = result +'<input type="text" class="form-control input-sm calendar" id="employeeDateOfBirth" name="dateOfBirth" readonly="readonly">';
	 result = result +'</div>';
	 result = result +'</div>';
	 result = result +'<div class="clearfix"></div>';
	 
	 result = result +'<div class="form-group">';
	 result = result +'<label class="col-md-3 control-label">Gender <span class="text-danger">*</span></label>';
	 result = result +'<div class="col-md-6">';
	 result = result +'<label class="paddingRight"><input type="radio" id="male" value="Male"  name="gender" onblur="genderCheckValidation(this)"><span class="mr5"></span>Male</label>';
	 result = result +'<label class="paddingLeft paddingRight"><input type="radio" id="female"  value="Female" name="gender" onblur="genderCheckValidation(this)"><span class="mr5"></span>Female</label>';
	 result = result +'<label class="paddingRight"><input type="radio"  value="Transgender" id="transgender" name="gender" onblur="genderCheckValidation(this)"><span class="mr5"></span>Transgender</label>';
	 result = result +'<input type="hidden"  id="genderValue" name="genderValue"  field-name="Gender" data-validation="required"/>';
	 result = result +'</div>';
	 result = result +'</div>';
	 result = result +'<div class="clearfix"></div>';
	 
	 
	 result = result +'<div class="form-group" id="emailDiv">';
	 result = result +'<label class="col-md-3 control-label">Company Email</label>';
	 result = result +'<div class="col-md-6">';
	 result = result +'<input type="text" class="form-control input-sm" id="companyEmail" name="companyEmail"  data-validation-optional="true"   data-validation="validate_Space validate_length length2-100 validate_email">';
	 result = result +'</div>';
	 result = result +'</div>';
	 result = result +'<div class="clearfix"></div>';
	 
	 if(loginCountry == 'United States'){
		 result = result +'<div class="form-group">';
		 result = result +'<label class="col-md-3 control-label">Employment Eligibility</label>';
		 result = result +'<div class="col-md-6">';
		 result = result +'<select class="chosen-select" data-placeholder="Select Citizenship" name="employmentEligibility" id="employmentEligibility"   onchange="changeCitizenShip();" field-name="Role">';
		 result = result +'<option value="">-- Select Citizenship --</option>';
		 result = result +'<option >Citizenship of United States</option>';
		 result = result +'<option >Resident Card</option>';
		 result = result +'<option >An alien authorized to work</option>';
		 result = result +'<option >Non-citizen</option>';
		 result = result +'</select>';
		 result = result +'</div>';
		 result = result +'</div>';
		 result = result +'<div class="clearfix"></div>';
	 }
	 
	 result = result +'<div class="form-group" id="residentCardId" style="display:none">';
	 result = result +'<label class="col-md-3 control-label">USCIS /Alien registration Number</label>';
	 result = result +'<div class="col-md-6">';
	 result = result +'<input type="text" class="form-control input-sm" id="uscisAlien" name="uscisAlien"  data-validation-optional="true"   data-validation="validate_length length9-13 validate_int">';
	 result = result +'</div>';
	 result = result +'</div>';
	 result = result +'<div class="clearfix"></div>';
	 
	 result = result +'<div class="form-group" id="nonCittizenId" style="display:none">';
	 result = result +'<label class="col-md-3 control-label">Authorized work until </label>';
	 result = result +'<div class="col-md-6">';
	 result = result +'<input type="text" class="form-control input-sm calendar" id="authorizedDate" name="authorizedWorkUntil" readonly="readonly">';
	 result = result +'</div>';
	 result = result +'</div>';
	 result = result +'<div class="clearfix"></div>';
	 
	 
	 
	 result = result +'<p><strong>Position Details</strong></p>';

	 result = result +'<div class="form-group">';
	 result = result +'<label class="col-md-3 control-label">Role<span class="text-danger">*</span></label>';
	 result = result +'<div class="col-md-6 chooRadius">';
	 result = result +'<div class="input-group col-md-10 pull-left">';
	 result = result +'<select class="chosen-select" data-placeholder="Select Role" onchange="getReportingManager();"  id="onboardCandidateRole" name="onboardCandidateRole" data-validation="required"  field-name="Role">';
	 result = result +'</select>';
	 result = result +'<span class="input-group-addon cursorPointer bg-blue" onclick=" refreshRoleList(); "><i class="fa fa-refresh "></i></span>';
	 result = result +' </div>';
	 result = result +'<sec:authorize access="hasAnyRole(\'ROLE_ENROLL_NEW_ROLE\')">';
	 result = result +'<span class="col-md-2 noPad clearfix"><a href="${SourceLeadWeb_sourceleadUrl}/orgRolesList" target="_blank"><i class="fa fa-plus btn btn-default pull-right minHeBtn"></i></a> </span>';
	 result = result +'</sec:authorize>';
	 result = result +' </div>';
	 result = result +'</div>';
	 result = result +'</div>';
	 result = result +'<div class="form-group" id="dojDiv">';
	 result = result +'<label class="col-md-3 control-label">Date of Joining<span class="text-danger">*</span></label>';
	 result = result +'<div class="col-md-6">';
	 result = result +'<input type="text" class="form-control input-sm calendar" data-validation="required" field-name="Date Of Join" id="startDate" name="startDate" readonly="readonly">';
	 result = result +'</div>';
	 result = result +'</div>';
	 result = result +'</div>';
	 result = result +'<div class="form-group" id="endDateDiv">';
	 result = result +'<label class="col-md-3 control-label">End Date <i class="fa fa-info-circle" title="The employment end date for the contract employee"></i></label>';
	 result = result +'<div class="col-md-6">';
	 result = result +'<div class="input-group">';
	 result = result +'<input type="text" class="form-control input-sm calendar" data-validation-optional="true" id="endDate" name="endDate" readonly="readonly">';
	 result = result +'<span class="input-group-addon" onClick="clearEndDate();"><i class="colorRed fa fa-times cursorPointer"></i></span>';
	 result = result +'</div>';
	 result = result +'</div>';
	 result = result +'</div>';
	 result = result +'<div class="form-group">';
	 result = result +'<label class="col-md-3 control-label">Rate / Salary </label>';
	 result = result +'<div class="col-md-6">';
	 result = result +'<div class="input-group er ratetype">';
	 result = result +'<div class="input-group-btn tblminwidth-75 displyBlock">';
	 result = result +'<input type="text" class="pull-left btn btn-primary btn-block" value="" id="paymentType" name="paymentType" readonly>';
	 result = result +'</div>';
	 result = result +' <input type="text" class="form-control" id="salaryRate" field-name="Rate" name="salaryRate" value="" data-validation="validate_Space validate_float validate_length length1-8" data-validation-optional="true">';
	 result = result +' <span class="hidden">Rate</span>';
	 result = result +' <div class="input-group-btn tblminwidth-75 ratetype">';
	 result = result +'<input type="text" class="pull-left btn btn-primary btn-block" value="" id="paymentMode" name="paymentMode" readonly>';
	 result = result +' </div>';
	 result = result +' </div>';
	 result = result +' </div>';
	 result = result +'</div>';
	 
	 result = result +'<div class="form-group">';
	 result = result +'<label class="col-md-3 control-label">Manager<span class="text-danger">* </span><i class="fa fa-info-circle" title="The person who is the reporting manager for the onboarded candidate in the organisation"></i></label>';
	 result = result +'<div class="col-md-6">';
	 result = result +'<select class="chosen-select" data-placeholder="Select Manager" id="reportingManager" name="reportingManager" field-name="Manager" data-validation="required">';
	 result = result +'</select>';
	 result = result +'</div>';
	 result = result +'</div>';
	 result = result +'<div class="form-group">';
	 result = result +'<label class="col-md-3 control-label">Team <i class="fa fa-info-circle" title="The respective team of the onboarded candidate"></i></label>';
	 result = result +'<div class="col-md-6 chooRadius">';
	 result = result +'<div class="input-group col-md-10 pull-left">';
	 result = result +'<select class="chosen-select" data-placeholder="Select Team" id="team" name="team">';
	 result = result +'</select>';
	 result = result +'<span class="input-group-addon cursorPointer bg-blue" onclick="loadTeams(); "><i class="fa fa-refresh "></i></span>';
	 result = result +'</div>';
	 result = result +'<sec:authorize access="hasAnyRole(\'ROLE_ADD_TEAM\')">';
	 result = result +'<span class="col-md-2 noPad clearfix"><a href="<c:url value="/app/team" />" target="_blank"><i class="fa fa-plus btn btn-default pull-right minHeBtn"></i></a> </span>';
	 result = result +'</sec:authorize>';
	 result = result +'</div>';
	 result = result +'</div>';
	 result = result +'</div>';
   
	 result = result +'<div class="form-group" id="employmentTypeDiv">';
	 result = result +'<label class="col-md-3 control-label">Employment Type<span class="text-danger">* </span></label>';
	 result = result +'<div class="col-md-6">';
	 result = result +'<select class="chosen-select" data-validation="required" id="employmentType" onchange="getEmploymentType();" name="employmentType" field-name="Employment Type">';
	 result = result +'<option value=""  selected="selected">Select Employment Type</option>';
	 result = result +'<option value="C2C">C2C</option>';
	 result = result +'<option value="W-2">W-2</option>';
	 result = result +'<option value="C2H">C2H</option>';
	 result = result +'<option value="Part Time">Part Time</option>';
	 result = result +'<option value="Full Time">Full Time</option>';
	 result = result +'<option value="Contract">Contract</option>';
	 result = result +'<option value="Other">Other</option>';
	 result = result +'</select>';
	 result = result +'</div>';
	 result = result +'</div>';
	 
	 result = result +'<div class="form-group" >';
	 result = result +'<label class="col-md-3 control-label">Shift</label>';
	 result = result +'<div class="col-md-6">';
	 result = result +'<select class="chosen-select"  id="shiftList"  name="shift">';
	 result = result +'</select>';
	 result = result +'</div>';
	 result = result +'</div>';
	 
   
	 result = result +'<div class="form-group" id="otherEmploymentTypeDiv" style="display: none">';
	 result = result +'<label class="col-md-3 control-label">Other Type<span class="text-danger">*</span></label>';
	 result = result +'<div class="col-md-6">';
	 result = result +'<input type="text" class="form-control input-sm" id="employmentOtherType" field-name="Other Type" name="employmentOtherType" >';
	 result = result +'</div>';
	 result = result +'</div>';
	 
    result = result +'<div class="form-group" id="vendorsDiv" style="display: none;">';
	result = result + '<label class="col-md-3  control-label">Vendors<span class="text-danger">* </span></label>';
	result = result + '<div class="col-md-6">';
	result = result + '<select class="chosen-select form-control" id="vendors" data-validation-optional="true" name="vendors" onchange="getAddApplicantVendorContactList();" field-name="Vendors">';
	result = result + '</select>';
	result = result + '</div>';
	result = result + '</div>';
	
    result = result + '	<div class="form-group" id="vendorContactsDiv" style="display: none;">';
	result = result + '<label class="col-md-3  control-label">Vendor Contacts<span class="text-danger">* </span></label>';
	result = result + '<div class="col-md-6">';
	result = result + '<select class="chosen-select form-control" id="vendorContact" data-validation-optional="true" name="vendorContact"  field-name="vendor Contacts">';
	result = result + '<option value="">--Select Vendor Contacts--</option>';
	result = result + '</select>';
	result = result + '</div>';
	result = result + '</div>'; 
     
	 result = result +'<div class="form-group">';
	 result = result +'<label class="col-md-3 control-label">Probation Period</label>';
	 result = result +'<div class="col-md-6">';
	 result = result +'<select class="chosen-select" data-placeholder="Select Probation Notice" id="probationNotice" name="probationNotice">';
	 result = result +'<option selected="selected" value="">Select Probation Notice</option>';
	 result = result +'<option value="15 Days">15 Days</option>';
	 result = result +'<option value="1 Month">1 Month</option>';
	 result = result +'<option value="2 Months">2 Months</option>';
	 result = result +'<option value="3 Months">3 Months</option>';
	 result = result +'</select>';
	 result = result +'</div>';
	 result = result +'</div>';
	 result = result +'<div class="form-group">';
	 result = result +'<label class="col-md-3 control-label">Notice Period</label>';
	 result = result +'<div class="col-md-6">';
	 result = result +'<select class="chosen-select" data-placeholder="Select Termination Notice" id="terminationNotice" name="terminationNotice">';
	 result = result +'<option selected="selected" value="">Select Termination Notice</option>';
	 result = result +'<option value="15 Days">15 Days</option>';
	 result = result +'<option value="1 Month">1 Month</option>';
	 result = result +'<option value="2 Months">2 Months</option>';
	 result = result +'<option value="3 Months">3 Months</option>';
	 result = result +'</select>';
	 result = result +'</div>';
	 result = result +'</div>';
     
	 result = result +'<div class="form-group" id="dueDateDiv">';
	 result = result +'<label class="col-md-3 control-label">Due Date <i class="fa fa-info-circle" title="The due date for the onboarding process of the selected candidate"></i></label>';
	 result = result +'<div class="col-md-6">';
	 result = result +'<div class="input-group">';
	 result = result +'<input type="text" class="form-control input-sm calendar" id="dueDate" name="dueDate" readonly="readonly">';
	 result = result +'<span class="input-group-addon" onClick="clearDueDate();"><i class="colorRed fa fa-times cursorPointer"></i></span>';
	 result = result +'</div>';
	 result = result +'</div>';
	 result = result +'</div>';

	 result = result +'<div class="form-group">';
	 result = result +'<label class="col-md-3 control-label">Special Conditions <i class="fa fa-info-circle" title="Any special conditions laid by the company"></i></label>';
	 result = result +'<div class="col-md-6">';
	 result = result +'<textarea rows="2" class="form-control" id="specialConditions" name="specialConditions" data-validation-optional="true"></textarea>';
	 result = result +'</div>';
	 result = result +'</div>';
	 result = result +'<div class="form-group">';
	 result = result +'<label class="col-md-3 control-label">Onboard Template <i class="fa fa-info-circle" title="The default template for the candidate onboarding process"></i></label>';
	 result = result +'<div class="col-md-6 chooRadius">';
	 result = result +'<div class="row">';
	 result = result +'<div class="col-md-9 padding_right">';
	 result = result +'<select class="chosen-select form-control" multiple data-placeholder="Select Template" data-validation-optional="true" id="onboardingTemplate" name="onboardingTemplate" data-validation="required">';
	 result = result +'</select>';
	 result = result +'</div>';
	 result = result +'<span class="cursorPointer btn btn-primary col-md-1 padding_left" onclick="loadOnBoardingTemplates();"><i class="fa fa-refresh"></i></span>';
	 result = result +'<sec:authorize access="hasAnyRole(\'ROLE_CREATE_ON_BOARD_TEMPLATE\')">';
	 result = result +'<span class="col-md-2 clearfix"><a href="${SourceLeadWeb_sourceleadUrl}/addNewOnboardTemplate" target="_blank"><i class="fa fa-plus btn btn-default pull-right minHeBtn"></i></a> </span>';
	 result = result +'</div>';
	 result = result +'</sec:authorize>';
	 result = result +'</div>';
	 
	 $("#onboardingCandidateDetailsDiv").empty();
   	 $("#onboardingCandidateDetailsDiv").append(result); 
   	 
     initLocationAuto('offBoardLocation','country','state','city','zipCode','','',''); 
     
	 loadCandidateRoles();
	 loadTeams();
	 loadOnBoardingTemplates();
	 getDatesScript();
   	 $("#loading").removeAttr("style");
   	 
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
	});
}
</script>

 <script type="text/javascript">
	function genderCheckValidation(id){
		 var gendersList = [];
 		 $("input[name='gender']:checked").each( function (i) {
 			gendersList[i] = $(this).val();
 		});
 		 
		if (gendersList.length > 0) { 
		document.getElementById("genderValue").value = id.value;
		}else{
			$('#genderValue').val(''); 
		}
	} 
</script>


<script>
  function getDatesScript(){
	  
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
	  
	  $('#authorizedDate').datepicker({
	      dateFormat: finalDateFormat,
	      changeMonth: true,
	  	  changeYear: true, 
	  });
	  
	  $('#employeeDateOfBirth').datepicker({
	      dateFormat: finalDateFormat,
	      changeMonth: true,
	  	  changeYear: true, 
	  	  yearRange: "-100:+0",
	  	  maxDate:0
	  });
	  
	  $('#startDate').datepicker({
	      dateFormat: finalDateFormat,
	      changeMonth: true,
	  	  changeYear: true, 
	  	  
	  	   onSelect : function(selected) {
			var dt = $(this).datepicker('getDate', '+1d');
			dt.setDate(dt.getDate() + 1);
			$("#endDate").datepicker("option", "minDate", dt);
			$("#dueDate").datepicker("option", "minDate", dt);
			addValidClass();
		}
	  });
	  
	  $('#endDate').datepicker({
	      dateFormat: finalDateFormat,
	      changeMonth: true,
	  	  changeYear: true, 
	  	   onSelect : function(selected) {
	  		var dt = $(this).datepicker('getDate', '-1d');
			dt.setDate(dt.getDate() - 1);
			$("#startDate").datepicker("option", "maxDate", dt);
			addValidClass();
			}
	  	
	  });
	  
	  $('#dueDate').datepicker({
	      dateFormat: finalDateFormat,
	      changeMonth: true,
	  	  changeYear: true,
	  	  
	  	  onSelect : function(selected) {
	  		var dt = $(this).datepicker('getDate', '-1d');
			dt.setDate(dt.getDate() - 1);
			$("#startDate").datepicker("option", "maxDate", dt);
			addValidClass();
			}
	  });
	  
  } 
</script>


<script type="text/javascript"> 
	function addValidClass(){
		 var startDate = document.getElementById("startDate").value;
		 if(startDate){
			 $('#dojDiv').find('p.jquery_form_error_message').remove(); 
		 }
		 
		 var endDate = document.getElementById("endDate").value;
		 if(endDate){
			 $('#endDateDiv').find('p.jquery_form_error_message').remove(); 
		 }
		 
		 var dueDate = document.getElementById("dueDate").value;
		 if(dueDate){
			 $('#dueDateDiv').find('p.jquery_form_error_message').remove(); 
		 }
}
</script>


<script>
function validOnboardCandiate(){
	
	 var onboardCandidate = document.getElementById("onboardCandiate").value;
	 if(onboardCandidate){
		 $('#onboardCandidatesDiv').find('p.jquery_form_error_message').remove(); 
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
function refreshRoleList(){
		 $.ajax({
	      type : "POST",
	       url: atsUrl+"/refreshRoleList",       
	       success: function(json) {
	        	var output = "";
	        	output = output + "<option value=''>Select Role</option>"; 
	        	for (var i=0; i<json.length; i++)
				{
	        		var opt = json[i];
	       			output = output + "<option value='"+opt.id+"'>"+opt.displayName+"</option>";
				}
	        	$("#onboardCandidateRole").empty();
		        $("#onboardCandidateRole").append(output);
		        $('#onboardCandidateRole').trigger("chosen:updated");
	        }
		 });
	
}
</script>
  
<script>
/* function clearStartDate(){
	$("#startDate").val("");
	}; */
function clearEndDate(){
	$("#endDate").val("");
	};
function clearDueDate(){
	$("#dueDate").val("");
	};
</script>
<script>
function changeCitizenShip(){
	var eligibility = $("#employmentEligibility").val();
	if(eligibility == 'Resident Card'){
		document.getElementById("residentCardId").style.display="block";
		document.getElementById("nonCittizenId").style.display="none";
		$("#authorizedDate").val('');
	}else if(eligibility == 'An alien authorized to work'){
		document.getElementById("nonCittizenId").style.display="block";
		document.getElementById("residentCardId").style.display="block";
	}else{
		document.getElementById("nonCittizenId").style.display="none";
		document.getElementById("residentCardId").style.display="none";
		$("#authorizedDate").val('');
		$("#uscisAlien").val('');

	}
}
</script>
 <script>
function shiftList(shiftId){
		 $.ajax({
	      type : "POST",
	       url: atsUrl+"/getOrgLocationsShiftList",       
	       success: function(json) {
	        	var output = "";
	        	output = output + "<option value=''>Select Shift</option>"; 
	        	for (var i=0; i<json.length; i++)
				{
	        		var opt = json[i];
	        		if(shiftId != null && shiftId != '' && shiftId != 'undefined' && shiftId == opt.id){
		       			output = output + "<option value='"+opt.id+"' selected >"+opt.shiftName+"</option>";
	        		}else{
		       			output = output + "<option value='"+opt.id+"'>"+opt.shiftName+"</option>";
	        		}
				}
	        	$("#shiftList").empty();
		        $("#shiftList").append(output);
		        $('#shiftList').trigger("chosen:updated");
	        }
		 });
	
}
</script>

<script>
function getShiftList(){
		 $.ajax({
	      type : "POST",
	       url: atsUrl+"/getOrgLocationsShiftList",       
	       success: function(json) {
	        	var output = "";
	        	output = output + "<option value=''>Select Shift</option>"; 
	        	for (var i=0; i<json.length; i++)
				{
	        		var opt = json[i];
	        		output = output + "<option value='"+opt.id+"'>"+opt.shiftName+"</option>";
				}
	        	$("#shiftList").empty();
		        $("#shiftList").append(output);
		        $('#shiftList').trigger("chosen:updated");
	        }
		 });
	
}
</script>
