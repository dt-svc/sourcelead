<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<link rel="stylesheet" href="resources/css/chosen.min.css">
<script src="resources/js/chosen.jquery.min.js"></script> 
<script src="resources/js/jquery-fun.js"></script> 
<script src="resources/js/formvalidator.js"></script>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<script> 
	$(document).ready(function(){
		document.getElementById("templateDocs").disabled = true;
		document.getElementById("templateTasks").disabled = true;
		
		
		var onBoardingId = '${onBoardingId}';
		if(onBoardingId != null && onBoardingId != '' && onBoardingId != 'undefined'){
			$("#candidateOnBoardId").val(onBoardingId);
  	        document.getElementById("templateDocs").disabled = false;
  	        document.getElementById("templateTasks").disabled = false;
		}
		
		loadOnboardingDetails();
		
	});
</script>

<section class="content-wraper">
<div id="loading"></div>
  <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Employee Onboarding</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Employee onboarding</div>
 </section>
 
 
 <div class="content-area content-new clearfix">
      <div class="applicant_accept">
        <div class="clearfix onboardBtns">
        </div>
        <div class="tabbable">
          <ul class="nav nav-tabs" id="tab_bar">
            <li class="active"> <a href="#details" data-toggle="tab"> <i class="fa fa-th-list paddingRight"></i> <span>Details</span> <br>
              <small>Employee details &amp; Postion details</small> </a> </li>
            <li><a href="#documents" data-toggle="tab" id="templateDocs"> <i class="fa fa-files-o paddingRight"></i> <span>Documents</span> <br>
              <small>Acceptance &amp; Policy Documents</small> </a> </li>
            <li> <a href="#tasks" data-toggle="tab" id="templateTasks"> <i class="fa fa-check-square-o paddingRight"></i> <span>Taks</span> <br>
              <small>List of tasks</small> </a></li>
          </ul>
          <input type="hidden" name="candidateOnBoardId" id="candidateOnBoardId" value=""/>
          <div class="tab-content"> 
            
            <!-- Employee Details -->
            <div class="tab-pane active" id="details">
              <div class="form-horizontal">
               <form:form class="login-form clearfix"  id="onboardCandidate_form"  name="onboardCandidate_form" method="post" >
                <p><strong>Employee Details</strong></p>
                <div class="form-group">
                  <label class="col-md-3 control-label">Select Candidate <span class="text-danger paddingLeft">*</span></label>
                  <div class="col-md-6">
                    <select class="chosen-select" id="onboardCandiate" name="onboardCandiate" data-validation="required" onchange="getCandidateDetails();getOfferDetails();" field-name="Candidate">
                    </select>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-md-3 control-label">First Name <span class="text-danger paddingLeft">*</span></label>
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm" id="firstName" name="firstName" filed-name="First Name" readonly="readonly">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-md-3 control-label">Last Name</label>
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm" id="lastName" name="lastName" value="" filed-name="Last Name" data-validation-optional="true" data-validation="validate_Space validate_AlphaNumber validate_length length1-100">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-md-3 control-label">Street Addess 1 <span class="text-danger paddingLeft">*</span></label>
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm" id="address1" name="address1" field-name="Address 1" data-validation="required validate_Space">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-md-3 control-label">Street Addess 2</label>
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm" id="address2" name="address2" field-name="Address 2" data-validation-optional="true"  data-validation="validate_Space">
                  </div>
                </div>
                  
                 <div class="form-group">
                  <label class="col-md-3 control-label">Country <span class="text-danger paddingLeft">*</span></label>
                  <div class="col-md-6">
                    <select class="chosen-select" data-placeholder="Select Country" id="country" onchange="getStates();" name="country">
                      <option disabled>Select Country</option>
                    </select>
                  </div>
                </div>

                <div class="form-group">
                  <label class="col-md-3 control-label">State</label>
                  <div class="col-md-6">
                    <select class="chosen-select" data-placeholder="Select State" onchange="getCities();" id="state" name="state">
                      <option disabled>Select State</option>
                      
                    </select>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-md-3 control-label">City</label>
                  <div class="col-md-6">
                    <select class="chosen-select" data-placeholder="Select City" id="city" name="city">
                      <option disabled>Select City</option>
                    </select>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-md-3 control-label">Zipcode</label>
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm" id="zipCode" name="zipCode"  data-validation-optional="true"  data-validation="validate_Space validate_length length1-10" >
                  </div>
                </div>

                <div class="form-group">
                  <label class="col-md-3 control-label">Mobile number <span class="text-danger paddingLeft">*</span></label>
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm" id="mobileNumber" name="mobileNumber" readonly="readonly">
                  </div>
                </div>

                  
                <div class="form-group">
                  <label class="col-md-3 control-label">Personal Email <span class="text-danger paddingLeft">*</span></label>
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm" id="personalEmail" name="personalEmail" readonly="readonly">
                  </div>
                </div>
                  
                <div class="form-group">
                  <label class="col-md-3 control-label">Company Email</label>
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm" id="companyEmail" name="companyEmail" data-validation-optional="true"data-validation="validate_Space validate_length length2-100 validate_email">
                  </div>
                </div>
                <div class="clearfix"></div>
                <p><strong>Position Details</strong></p>
                <!-- <div class="form-group">
                  <label class="col-md-3 control-label">Department</label>
                  <div class="col-md-6">
                      <select class="form-control selectpicker">
                          
                      </select>
                  </div>
                </div> -->

                <div class="form-group">
                  <label class="col-md-3 control-label">Role<span class="text-danger paddingLeft">*</span></label>
                  <div class="col-md-6">
                      <select class="chosen-select" data-placeholder="Select Role" onchange="getReportingManager();"  id="onboardCandidateRole" name="onboardCandidateRole" data-validation="required"  field-name="Role">
                    </select>
                  </div>
                </div>

                  <div class="form-group">
                  <label class="col-md-3 control-label">Date Of Join<span class="text-danger paddingLeft">*</span></label>
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm calendar" data-validation="required" field-name="Date Of Join" id="startDate" name="dateOfJo" readonly="readonly">
                  </div>
                </div>
                  <div class="form-group">
                  <label class="col-md-3 control-label">End Date</label>
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm calendar" data-validation-optional="true" id="endDate" name="endDate" readonly="readonly">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-md-3 control-label">Rate / Salary <span class="text-danger paddingLeft">*</span></label>
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm" id="salaryRate" name="salaryRate">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-md-3 control-label">Manager</label>
                  <div class="col-md-6">
                   <select class="chosen-select" data-placeholder="Select Manager" id="reportingManager" name="reportingManager" data-validation-optional="true">
                    </select>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-md-3 control-label">Team</label>
                  <div class="col-md-6">
                    <select class="chosen-select" data-placeholder="Select Team" id="team" name="team">
                    </select>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-md-3 control-label">Employment Type</label>
                  <div class="col-md-6">
                    <select class="chosen-select" data-placeholder="Select Employment Type" id="employmentType" name="employmentType">
                      <option value="" disabled selected="selected">Select Employment Type</option>
                      <option value="C2C">C2C</option>
                      <option value="W-2">W-2</option>
                      <option value="C2H">C2H</option>
                      <option value="Part Time">Part Time</option>
                      <option value="Full Time">Full Time</option>
                    </select>
                  </div>
                </div>
                  
                  <div class="form-group">
                  <label class="col-md-3 control-label">Probation Notice</label>
                  <div class="col-md-6">
                    <select class="chosen-select" data-placeholder="Select Probation Notice" id="probationNotice" name="probationNotice">
                      <option disabled selected="selected">Select Probation Notice</option>
                      <option>15 Days</option>
                      <option>1 Month</option>
                      <option>2 Months</option>
                      <option>3 Months</option>
                    </select>
                  </div>
                </div>
                  <div class="form-group">
                  <label class="col-md-3 control-label">Termination Notice</label>
                  <div class="col-md-6">
                    <select class="chosen-select" data-placeholder="Select Termination Notice" id="terminationNotice" name="terminationNotice">
                      <option disabled selected="selected">Select Termination Notice</option>
                      <option>15 Days</option>
                      <option>1 Month</option>
                      <option>2 Months</option>
                      <option>3 Months</option>
                    </select>
                  </div>
                </div>
                  
                  
                  <div class="form-group">
                  <label class="col-md-3 control-label">Due Date</label>
                  <div class="col-md-6">
                   <input type="text" class="form-control input-sm calendar" id="dueDate" name="dueDate" readonly="readonly">
                  </div>
                </div>

                  <div class="form-group">
                  <label class="col-md-3 control-label">Speical Conditions</label>
                  <div class="col-md-6">
                      <textarea rows="2" class="form-control" id="specialConditions" name="specialConditions" data-validation-optional="true"></textarea>
                  </div>
                </div>
                   <div class="form-group">
                       <label class="col-md-3 control-label">Onboard Template</label>
                  <div class="col-md-6">
                      <select class="chosen-select" multiple data-placeholder="Select Template" data-validation-optional="true" id="onboardingTemplate" name="onboardingTemplate" data-validation="required">
                       </select>
                  </div>
                </div>
                  
                <div class="form-footer">
                  <div class="pull-right mr">
                    <input type="button" value="Cancel" class="btn btn-sm btn-default">
                    <input type="button" value="Save" onclick="saveOnboardingCandidateDetails('save');" class="btn btn-sm btn-success">
                    <input type="button" value="Save and Continue" onclick="saveOnboardingCandidateDetails('saveAndContinue');"  class="btn btn-sm btn-primary">
                  </div>
                </div>
             </form:form>
              </div>
            </div>
            
            <!-- TAB 2 -->
            <div class="tab-pane" id="documents">
              
                <div class="form-horizontal">
                <p><strong>Acceptance Documents</strong> <br>
                  <small class="text-muted">These documents need to be accepted by the new hire as part of acceptance step  - Learn more</small> </p>
                
                <div class="form-group">
                  <label class="col-md-12">
                    <strong>Employment Contract</strong>
                    </label>
                    <label class="col-md-4 control-label"></label>
                  <div class="col-md-7">
                      <button type="button" class="btn btn-default btn-sm"><i class="fa fa-plus paddingRight"></i>Upload additional contract files</button>
                  </div>
                </div>
                  
                  
                <div class="form-group">
                  <label class="col-md-12">
                      <strong>Policies</strong> <br>
                    </label>

                    <label class="col-md-4 control-label">These policies will be added to this offer:</label>
                  <div class="col-md-7">
                      <button type="button" class="btn btn-default btn-sm"><i class="fa fa-plus paddingRight"></i>Upload additional policies files</button>
                  </div>
                </div>
                    
                <div class="clearfix"></div>
                <p><strong>Supporting Information</strong> <br>
                  <small class="text-muted">This is great place to upload a position description, commission structures, photo of the team , their desk or even a view from their new office.</small> </p>
                <div class="form-group">
                  <label class="col-md-4 control-label">These files will be available in the portal</label>
                  <div class="col-md-7">
                    <button type="button" class="btn btn-default btn-sm"><i class="fa fa-plus paddingRight"></i>Upload additional Information files</button>
                  </div>
                </div>
                <div class="form-footer">
                  <div class="pull-right mr">
                    <input type="button" value="Cancel" class="btn btn-sm btn-default">
                    <input type="button" value="Save" class="btn btn-sm btn-success">
                    <input type="button" value="Save and Continue" class="btn btn-sm btn-primary">
                  </div>
                </div>
              </div>
                
                
                
            </div>
            <div class="tab-pane" id="tasks">
              <div class="clearfix">
                  <h4 class="pull-left">Task List</h4>
                <div class="pull-right">
                  <button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#addTask"><i class="fa fa-plus paddingRight"></i>Add Task</button>
                  <!-- Modal -->
                  <div id="addTask" class="modal fade" role="dialog">
                    <div class="modal-dialog modal-lg">
                      <div class="modal-content">
                        <div class="modal-header">
                          <button type="button" class="close" data-dismiss="modal">&times;</button>
                          <h4 class="modal-title">Add Tasks</h4>
                        </div>
                        <div class="modal-body clearfix"> 
                          
 <!-- Add New Task Widget -->
                          <div class="col-md-12 clearfix">
                            <form class="form-horizontal clearfix task-widget">
                              <div class="row">
                                <div class="col-md-6">
                                  <label>Subject <span class="text-danger paddingLeft">*</span></label>
                                  <input type="text" class="form-control input-sm" placeholder="Subject">
                                </div>
                                <div class="col-md-6">
                                  <label>Assign To <span class="text-danger paddingLeft">*</span></label>
                                  <select class="chosen-select" multiple data-placeholder="Select Priority" id="priority">
                                    <option>High</option>
                                    <option>Low</option>
                                    <option>Mediam</option>
                                  </select>
                                </div>
                                <div class="col-md-6 dropdown-select">
                                  <label>Priority</label>
                                  <select class="form-control">
                                    <option selected disabled>-- Select Priority --</option>
                                    <option>High</option>
                                    <option>Low</option>
                                    <option>Mediam</option>
                                  </select>
                                </div>
                                <div class="col-md-6">
                                  <div class="row">
                                    <div class="col-md-12">
                                        <label><strong>Due after:</strong><span class="text-danger paddingLeft">*</span></label>
                                      <div class="input-group hidden">
                                          <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                        <input class="form-control" value="" name="Date" id="datepicker2" type="text">
                                      </div>
                                        
                                        <!-- Form inline Due after -->
                                        
                        <div class="row">
                            <div class="col-md-3 paddingRight">
                                <label class="small">Months</label> <br>
                                <input class="form-control input-sm" type="text">
                            </div>
                            <div class="col-md-3 paddingRight paddingLeft">
                                <label class="small">Days</label> <br>
                                <input class="form-control input-sm" type="text">
                            </div>
                            <div class="col-md-3 paddingRight paddingLeft">
                                <label class="small">Hours</label> <br>
                                <input type="text" class="form-control input-sm">
                            </div>
                            <div class="col-md-3 paddingRight paddingLeft">
                                <label class="small">Minutes</label> <br>
                                <input type="text" class="form-control input-sm">
                            </div>
                        </div>
                                        
                                        <!-- Form inline Due after -->
                                        
                                        
                                        
                                        
                                    </div>
                                    <div class="col-md-6 hidden">
                                      <label>Time</label>
                                      <div class="input-group"> <span class="input-group-addon"><i class="fa fa-clock-o"></i></span>
                                        <input class="form-control" value="" name="Time" id="example" type="text">
                                      </div>
                                    </div>
                                  </div>
                                </div>
                                <div class="col-md-6 dropdown-select">
                                  <label>Repeat</label>
                                  <select class="form-control input-sm">
                                    <option selected disabled>-- Select Repeat --</option>
                                    <option>Never</option>
                                    <option>Daily</option>
                                    <option>Weekly</option>
                                    <option>Monthly</option>
                                    <option>Yearly</option>
                                  </select>
                                </div>
                                <div class="col-md-6">
                                  <div class="mt">
                                    <label class="checkbox-inline">
                                      <input type="checkbox" name="chkbox">
                                      Email </label>
                                    <label class="checkbox-inline">
                                      <input type="checkbox" name="chkbox">
                                      Notification </label>
                                  </div>
                                </div>
                                <div class="col-md-6">
                                  <label>Description</label>
                                  <textarea rows="2" placeholder="Description" class="form-control"></textarea>
                                </div>
                                <div class="clearfix"></div>
                                <div class="clearfix mt col-md-12">
                                  <div class="pull-right">
                                    <input type="button" class="btn btn-primary btn-sm" value="Save">
                                    <input type="button" class="btn btn-default btn-sm ml10" value="Cancel">
                                  </div>
                                </div>
                              </div>
                            </form>
                          </div>
                          <!-- Add New Task Widget -->                           
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
                
                <div class="clearfix taskwidget_inner">
                    <div class="media-body">
                    <div class="clearfix taskheder">
                        <label>
                            <span class="paddingRight">Subject:</span> <strong class="text-primary">Account Setup</strong>
                        </label>
                        <label class="small pull-right text-muted">
                            <span>Due after 15days</span>
                        </label>
                    </div>
                    <ul class="list-inline list-description">
                        <li><span>Assign To:</span> Pramodkumar Mahagen</li>
                        <li><span>Priority:</span> High</li>
                        <li><span>Repeat:</span> Never</li>
                    </ul>
                    </div>
                </div>
                <div class="clearfix taskwidget_inner">
                    <div class="media-body">
                    <div class="clearfix taskheder">
                        <label>
                            <span class="paddingRight">Subject:</span> <strong class="text-primary">Desktop arriange</strong>
                        </label>
                        <label class="small pull-right text-muted">
                            <span>Due after 2 months</span>
                        </label>
                    </div>
                    <ul class="list-inline list-description">
                        <li><span>Assign To:</span> Pramodkumar Mahagen</li>
                        <li><span>Priority:</span> High</li>
                        <li><span>Repeat:</span> Never</li>
                    </ul>
                    </div>
                </div>
                <div class="clearfix taskwidget_inner">
                    <div class="media-body">
                    <div class="clearfix taskheder">
                        <label>
                            <span class="paddingRight">Subject:</span> <strong class="text-primary">Providing access</strong>
                        </label>
                        <label class="small pull-right text-muted">
                            <span>Due after 3 months</span>
                        </label>
                    </div>
                    <ul class="list-inline list-description">
                        <li><span>Assign To:</span> Pramodkumar Mahagen</li>
                        <li><span>Priority:</span> High</li>
                        <li><span>Repeat:</span> Never</li>
                    </ul>
                    </div>
                </div>
                <div class="clearfix taskwidget_inner">
                    <div class="media-body">
                    <div class="clearfix taskheder">
                        <!--<label>Account Setup, Desk top Arriange, Providing access to the Candidate</label>-->
                        <label>
                            <span class="paddingRight">Subject:</span> <strong class="text-primary">Account Setup</strong>
                        </label>
                        <label class="small pull-right text-muted">
                            <span>Due after 3 months</span>
                        </label>
                    </div>
                    <ul class="list-inline list-description">
                        <li><span>Assign To:</span> Pramodkumar Mahagen</li>
                        <li><span>Priority:</span> High</li>
                        <li><span>Repeat:</span> Never</li>
                    </ul>
                    </div>
                </div>
                <div class="clearfix taskwidget_inner">
                    <div class="media-body">
                    <div class="clearfix taskheder">
                        <!--<label>Account Setup, Desk top Arriange, Providing access to the Candidate</label>-->
                        <label>
                            <span class="paddingRight">Subject:</span> <strong class="text-primary">Account Setup</strong>
                        </label>
                        <label class="small pull-right text-muted">
                            <span>Due after 15 days</span>
                        </label>
                    </div>
                    <ul class="list-inline list-description">
                        <li><span>Assign To:</span> Pramodkumar Mahagen</li>
                        <li><span>Priority:</span> High</li>
                        <li><span>Repeat:</span> Never</li>
                    </ul>
                    </div>
                </div>
                              <div class="form-footer clearfix mt">
                <div class="pull-right mr">
                  <input type="button" value="Cancel" class="btn btn-sm btn-default">
                  <input type="button" value="Finish" class="btn btn-sm btn-primary">
                </div>
              </div>
            </div>
          </div>
          <!-- STYLED IN FORMS.CSS --> 
        </div>
      </div>
    </div>
 </section>
 
  <jsp:include page="../masterFooter.jsp" />
  
  
  <script>
  function loadOnboardingDetails(){
	  loadOfferAcceptedCandidates();
	  loadCandidateRoles();
	  loadTeams();
	  loadOnBoardingTemplates();
  }
  </script>
  
  
  <script>
  function loadOfferAcceptedCandidates(){
	  
		var sourceleadUrl = '${sourceleadUrl}';
		$.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/getOfferAcceptedCandidatesForOnBoard?${_csrf.parameterName}=${_csrf.token}",	    	   
		    success: function(data) {
		    	 var json = JSON.parse(data);
	        	 var result = "";
	        	
	        	result = result +'<option disabled selected>--Select Candidate--</option>';
	        	for (var i=0; i<json.length; i++)
				{
	        		var report = json[i];
	        		result = result +'<option value='+report.candidateId+","+report.id+' >'+report.candidateName+' ('+report.jobTitle+')</option>';
				}
	        	 
	        	 $("#onboardCandiate").empty();
	           	 $("#onboardCandiate").append(result); 
	           	 $('#onboardCandiate').trigger("chosen:updated");
		    }
	   });
	  
  }
  </script>
  
  <script>
  function getCandidateDetails(){
	  
	  var onboardCandiate = document.getElementById("onboardCandiate").value;
	  var candidate = onboardCandiate.split(",");
	  var candidateId = candidate[0];
	  
	  var sourceleadUrl = '${sourceleadUrl}';
	  
	  if(candidateId != null && candidateId != "" && candidateId != 'undefined'){
		  
		  $.ajax({
			   	type: "POST",
			    url: sourceleadUrl+"/getOnBoardingCandidateDetails?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId,	    	   
			    success: function(data) {
			    	 var json = JSON.parse(data);
		        	
			    	 $("#firstName").val(json['firstName']);
			    	 $("#lastName").val(json['lastName']);
			    	 $("#mobileNumber").val(json['mobilePhone']);
			    	 $("#personalEmail").val(json['email']);
			    	 getOnBoardCandidateCountry(json['country'])
			    	 getOnBoardCandidateStates(json['country'],json['state']);
			    	 getOnBoardCandidateStates(json['state'],json['city']);
			    	 getOnBoardCandidateCities(json['country'],json['state'],json['city']);
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
	  
	  var sourceleadUrl = '${sourceleadUrl}';
	  
	  if(offerId != null && offerId != "" && offerId != 'undefined'){
		  
		  $.ajax({
			   	type: "POST",
			    url: sourceleadUrl+"/getOnBoardingCandidateOfferDetails?${_csrf.parameterName}=${_csrf.token}&offerId="+offerId,	    	   
			    success: function(data) {
			    	 var json = JSON.parse(data);
		        	
			    	 if(json['amount'] != null && json['amount'] != "" && json['amount'] != 'undefined'){
			    		 $("#salaryRate").val(json['amount']);
			    	 }
			    	 
			    	 if(json['dateOfJoiningStr'] != null && json['dateOfJoiningStr'] != "" && json['dateOfJoiningStr'] != 'undefined'){
			    		 $("#startDate").val(json['dateOfJoiningStr']);
			    	 }
			    	 
			    	 if(json['positionType'] != null && json['positionType'] != "" && json['positionType'] != 'undefined'){
			    		 $("#employmentType").val(json['positionType']);
			    		 $('#employmentType').trigger("chosen:updated");
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
    	 url: '${sourceleadUrl}'+"/countryAutoComplete?${_csrf.parameterName}=${_csrf.token}",
          success: function(data) {
        	var json = JSON.parse(data);
        	$('#country').empty();
        	$('#country').append('<option value="">---Select---</option>');
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
	    	 url: '${sourceleadUrl}'+"/getStatesByCountry?${_csrf.parameterName}=${_csrf.token}&countryName="+country, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#state').empty();
	        	$('#state').append('<option value="">---Select---</option>');
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
	    	 url: '${sourceleadUrl}'+"/getCitiesByStateAndCountry?${_csrf.parameterName}=${_csrf.token}&countryName="+country+"&stateName="+state, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#city').empty();
	        	$('#city').append('<option value="">---Select---</option>');
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
    	$('#city').append('<option value="">---Select---</option>');
    	$('#city').trigger("chosen:updated");
	}
}
</script> 


<script>
function loadCandidateRoles(){
	
	var sourceleadUrl = '${sourceleadUrl}';
	$.ajax({
	   	type: "POST",
	    url: sourceleadUrl+"/getRolesListForCandidateOnBoarding?${_csrf.parameterName}=${_csrf.token}",	    	   
	    success: function(data) {
	    	 var json = JSON.parse(data);
        	 var result = "";
        	
        	result = result +'<option disabled selected>--Select Role--</option>';
        	for (var i=0; i<json.length; i++)
			{
        		var report = json[i];
        		result = result +'<option value='+report.id+' >'+report.displayName+'</option>';
			}
        	 
        	 $("#onboardCandidateRole").empty();
           	 $("#onboardCandidateRole").append(result); 
           	 $('#onboardCandidateRole').trigger("chosen:updated");
	    }
   });
}
</script>

<script>
function loadTeams(){
	
	var sourceleadUrl = '${sourceleadUrl}';
	$.ajax({
	   	type: "POST",
	    url: sourceleadUrl+"/getTeamsListForCandidateOnBoarding?${_csrf.parameterName}=${_csrf.token}",	    	   
	    success: function(data) {
	    	 var json = JSON.parse(data);
        	 var result = "";
        	
        	result = result +'<option disabled selected>--Select Team--</option>';
        	for (var i=0; i<json.length; i++)
			{
        		var report = json[i];
        		result = result +'<option value='+report.id+' >'+report.name+'</option>';
			}
        	 
        	 $("#team").empty();
           	 $("#team").append(result); 
           	 $('#team').trigger("chosen:updated");
	    }
   });
}
</script>


<script>
function loadOnBoardingTemplates(){
	
	var sourceleadUrl = '${sourceleadUrl}';
	$.ajax({
	   	type: "POST",
	    url: sourceleadUrl+"/getOnboardingTemplatesForCandidateOnBoarding?${_csrf.parameterName}=${_csrf.token}",	    	   
	    success: function(data) {
	    	 var json = JSON.parse(data);
        	 var result = "";
        	
        	for (var i=0; i<json.length; i++)
			{
        		var report = json[i];
        		result = result +'<option value='+report.id+' >'+report.templateName+'</option>';
			}
        	 
        	 $("#onboardingTemplate").empty();
           	 $("#onboardingTemplate").append(result); 
           	 $('#onboardingTemplate').trigger("chosen:updated");
	    }
   });
}
</script>

<script type="text/javascript">
 function getReportingManager() {
	 var role = document.getElementById("onboardCandidateRole").value; 
	 var sourceleadUrl ='${sourceleadUrl}';
	 if(role != null && role != "" && role != 'undefined'){
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getReportingManager?${_csrf.parameterName}=${_csrf.token}&role="+role, 
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	var output = "";
		        	output = output + "<option value=''>-- Select Manager --</option>"; 
		        	for (var i=0; i<json.length; i++)
					{
			        		var opt = json[i];
			        		output = output +'<option value='+opt.id+' >'+opt.firstName+' '+opt.lastName+' ('+opt.userType+')</option>';
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
  $( function() {
	  
	  $('#startDate').datepicker({
	      dateFormat: 'dd-mm-yy',
	      changeMonth: true,
	  	  changeYear: true, 
	  });
	  
	  $('#endDate').datepicker({
	      dateFormat: 'dd-mm-yy',
	      changeMonth: true,
	  	  changeYear: true, 
	  });
	  
	  $('#dueDate').datepicker({
	      dateFormat: 'dd-mm-yy',
	      changeMonth: true,
	  	  changeYear: true, 
	  });
	  
  } );
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
		    var frm = $('#onboardCandidate_form').serializeFormJSON();
	        var con = JSON.stringify(frm);
	        con = con.replace(/[{}]/g, "");
	        
	        //It will escape all the special characters
	        var jsonData = encodeURIComponent(con);
	        
	        var sourceleadUrl ='${sourceleadUrl}';
	        
	       
	        $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	         $.ajax({
		    	type: "POST",
		    	 url: sourceleadUrl+"/saveOnboardingCandidateDetails?${_csrf.parameterName}=${_csrf.token}&json="+jsonData, 
		        success: function(onboardId) {
		        	
		        	if(saveType == 'saveAndContinue'){
		        		var $tabs = $('.tabbable li');
		     	        $tabs.filter('.active').next('li').find('a[data-toggle="tab"]').tab('show');
		     	        document.getElementById("templateDocs").disabled = false;
		        	}
		        	
	     	        $("#candidateOnBoardId").val(onboardId);
	     	        $("#loading").removeAttr("style");
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
  
  