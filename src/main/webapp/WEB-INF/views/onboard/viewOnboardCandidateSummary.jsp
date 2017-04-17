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
		onboardCandidateSummaryDetails();
	});
</script>

 <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
          <h2>Srinivas Lankalapalli <span class="small">onboard details</span></h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='/usersList'/>">Users List</a> &raquo; <a href="<c:url value='/candidateOnBoardingList'/>">Onboard & Offboard List</a> &raquo; Candidate Onboarding Summary </div>
    </section>
    <div class="content-area content-new clearfix">
      <div class="applicant_accept">
        <div class="clearfix onboardBtns">
        </div>
        <div class="tabbable">
          <ul class="nav nav-tabs" id="tab_bar">
            <li class="active"> <a href="#details" data-toggle="tab" onclick="onboardCandidateSummaryDetails();"> <i class="fa fa-th-list paddingRight"></i> <span>Details</span> <br>
              <small>Employee details &amp; Postion details</small> </a> </li>
            <li><a href="#documents" data-toggle="tab" onclick="getTemplateContractsDocumentsDetails();getTemplateFormsDocumentsDetails();getTemplatePolicyDocumentsDetails();getTemplateSupportingDocumentsDetails();"> <i class="fa fa-files-o paddingRight"></i> <span>Documents</span> <br>
              <small>Acceptance &amp; Policy Documents</small> </a> </li>

              <li onclick="getOnboardingTasksList();"> <a href="#tasks" data-toggle="tab" id="templateTasks"> <i class="fa fa-check-square-o paddingRight"></i> <span>Taks</span> <br>
              <small>List of tasks</small> </a></li>
             
          </ul>
          
          
          <div class="tab-content"> 
            
            <!-- Employee Details -->
            <div class="tab-pane clearfix onboard_offboad_view active" id="details">
              <div id="onboardCandidateSummaryDetailsId"></div>
            </div>
            
            
            <!-- TAB 2 -->
            
            <div class="tab-pane clearfix" id="documents">
              <div class="col-md-12 clearfix mt">
				  <div class="form-group">
				    <label class="clearfix"> <strong>Employment Contract</strong> </label>
				    <div id="employmentContractDivId"></div>
				  </div>
				  
				  <div class="form-group mt">
				    <label class="clearfix"> <strong>Forms</strong> </label>
				    <div id="formstDivId"></div>
				  </div>
				  
				  <div class="form-group mt">
				    <label class="clearfix"> <strong>Policies</strong> </label>
				    <div id="policiesDivId"></div>
				  </div>
				  
				  <div class="form-group mt">
				    <label class="clearfix"> <strong>Supporting Information</strong> </label>
				    <div id="supportedDivId"></div>
				  </div>
				  
			</div>
            </div>
            <!-- Keep as pending -->
              <div class="tab-pane hidden" id="tasks">
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
          <!-- Keep as pending -->
            </div>
          <!-- STYLED IN FORMS.CSS --> 
        </div>
      </div>
    </div>
  </section>
 
 <script>
 function onboardCandidateSummaryDetails(){
	 var onBoardingUUID = '${onBoardingUUID}';
    var sourceleadUrl ='${sourceleadUrl}';
	 $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	    $.ajax({
	    	type: "POST",
	    	 url: sourceleadUrl+"/loadOnboardingCandidateDetails?${_csrf.parameterName}=${_csrf.token}&onBoardingUUID="+onBoardingUUID, 
	        success: function(data) {
	        	
	        	 var json = JSON.parse(data);
	        	if(json.zipCode != null && json.zipCode != '' && json.zipCode != 'undefined'){
	        		zipCode = json.zipCode;
	        	}else{
	        		zipCode = "";
	        	}
	        	 var result = "";
				 result = result +'	 <div class="col-md-12">                                                                                          ';
				 result = result +'   <div class="form-horizontal">                                                                                   ';
				 result = result +'     <p><strong>Employee Details</strong></p>                                                                      ';
				 result = result +'     <div class="form-group">                                                                                      ';
				 result = result +'       <label class="col-md-3 control-label">First Name <span class="text-danger paddingLeft">*</span></label>     ';
				 result = result +'       <div class="col-md-6">                                                                                      ';
				 result = result +'           <label>'+json.firstName+'</label>                                                                      ';
				 result = result +'       </div>                                                                                                      ';
				 result = result +'     </div>                                                                                                        ';
				 
				 if(json.lastName != null && json.lastName != '' && json.lastName != 'undefiend'){
					 result = result +'     <div class="form-group">                                                                                      ';
					 result = result +'       <label class="col-md-3 control-label">Last Name </label>      ';
					 result = result +'       <div class="col-md-6">                                                                                      ';
					 result = result +'           <label>'+json.lastName+'</label>                                                                                ';
					 result = result +'       </div>                                                                                                      ';
					 result = result +'     </div>                                                                                                        ';
				 }
				
				 
				 result = result +'     <div class="form-group">                                                                                      ';
				 result = result +'       <label class="col-md-3 control-label">Street Addess 1 <span class="text-danger paddingLeft">*</span></label>';
				 result = result +'       <div class="col-md-6">                                                                                      ';
				 result = result +'           <label>'+json.address1+'</label>                                      ';
				 result = result +'       </div>                                                                                                      ';
				 result = result +'     </div>                                                                                                        ';
				
				 if(json.address2 != null && json.address2 != '' && json.address2 != 'undefined'){
					 result = result +'     <div class="form-group">                                                                                      ';
					 result = result +'       <label class="col-md-3 control-label">Street Addess 2</label>';
					 result = result +'       <div class="col-md-6">    ';
					 result = result +'           <label>'+json.address2+'</label> ';
					 result = result +'       </div>                                                                                                      ';
					 result = result +'     </div>                                                                                                        ';
				 }
				 
				 
				 result = result +'  <div class="form-group">                                                                    ';
				 result = result +'       <label class="col-md-3 control-label">Country <span class="text-danger paddingLeft">*</span></label>        ';
				 result = result +'       <div class="col-md-6">                                   ';
				 result = result +'           <label>'+json.country+'</label>                                 ';
				 result = result +'       </div>                                                   ';
				 result = result +'     </div>                                                     ';
			    
				 if(json.state != null && json.state != '' && json.state != 'undefined' ){
					 result = result +'     <div class="form-group">                                   ';
					 result = result +'       <label class="col-md-3 control-label">State</label>      ';
					 result = result +'       <div class="col-md-6">                                   ';
					 result = result +'           <label>'+json.state+'</label>                             ';
					 result = result +'       </div>                                                   ';
					 result = result +'     </div>     ';
				 }
			                                                                                     
				 if(json.city != null && json.city != '' && json.city != 'undefined' ){
					 result = result +'     <div class="form-group">                                   ';
					 result = result +'       <label class="col-md-3 control-label">City</label>       ';
					 result = result +'       <div class="col-md-6">                                   ';
					 result = result +'           <label>'+json.city+'</label>                             ';
					 result = result +'       </div>                                                   ';
					 result = result +'     </div>                                                     ';
					
				 }
				 if(zipCode != ''){
					 result = result +'     <div class="form-group">                                   ';
					 result = result +'       <label class="col-md-3 control-label">Zipcode</label>    ';
					 result = result +'       <div class="col-md-6">                ';
					 result = result +'           <label>'+zipCode+'</label>   ';
					 result = result +'       </div>                                ';
					 result = result +'     </div>                                  ';
				 }
					 
				 
				 if(json.personalEmail != null && json.personalEmail != '' && json.personalEmail != 'undefined'){
					 result = result +'                                                                          ';
					 result = result +'     <div class="form-group">                                             ';
					 result = result +'       <label class="col-md-3 control-label">Personal Email <span class="text-danger paddingLeft">*</span></label> ';
					 result = result +'       <div class="col-md-6">                                                                                      ';
					 result = result +'           <label>'+json.personalEmail+'</label>                                                            ';
					 result = result +'       </div>                                                                                                      ';
					 result = result +'     </div>                                                                                                        ';
				 }
			    
				if(json.mobileNumber != null && json.mobileNumber != '' && json.mobileNumber != 'undefined'){
					 result = result +'     <div class="form-group">                                                                                      ';
					 result = result +'       <label class="col-md-3 control-label">Mobile number <span class="text-danger paddingLeft">*</span></label>  ';
					 result = result +'       <div class="col-md-6">                                                                                      ';
					 result = result +'           <label>'+json.mobileNumber+'</label>                                                                           ';
					 result = result +'       </div>                                                                                                      ';
					 result = result +'     </div>                                                                                                        ';
				}
				
				 if(json.roleName != null && json.roleName != '' && json.roleName != 'undefined'){
					 result = result +'   <div class="form-group">                                                                                        ';
					 result = result +'       <label class="col-md-3 control-label">Role <span class="text-danger paddingLeft">*</span></label>                                                          ';
					 result = result +'       <div class="col-md-6">                                                                                      ';
					 result = result +'           <label>'+json.roleName+'</label>                                                                                   ';
					 result = result +'       </div>                                                                                                      ';
					 result = result +'     </div>                                                                                                        ';
				 }
			     
				 
			     result = result +'       <div class="form-group">                                                                                    ';
				 result = result +'       <label class="col-md-3 control-label">Date Of Join<span class="text-danger paddingLeft">*</span></label>      ';
				 result = result +'       <div class="col-md-6">                                                                                      ';
				 result = result +'         <label>'+json.joiningDateStr+'                                                                                        ';
				 result = result +'       </label></div>                                                                                              ';
				 result = result +'     </div>                                                                                                        ';
				
				 if(json.endDateStr != null && json.endDateStr != '' && json.endDateStr != 'undefined'){
					 result = result +'       <div class="form-group">                                                                                    ';
					 result = result +'       <label class="col-md-3 control-label">End date</label>        ';
					 result = result +'       <div class="col-md-6">                                                                                      ';
					 result = result +'         <label>'+json.endDateStr+'</label>                                                                                 ';
					 result = result +'       </div>                                                                                                      ';
					 result = result +'     </div>                                                                                                        ';
				 }
				
				 
				 result = result +'     <div class="form-group">                                                                                      ';
				 result = result +'       <label class="col-md-3 control-label">Rate / Salary <span class="text-danger paddingLeft">*</span></label>  ';
				 result = result +'       <div class="col-md-6">                                                                                      ';
				 result = result +'         <label>'+json.salaryRate+'</label>                                                                                  ';
				 result = result +'       </div>                                                                                                      ';
				 result = result +'     </div>                                                                                                        ';
				
				 if(json.managerName != null && json.managerName != '' && json.managerName != 'undefined'){
					 result = result +'     <div class="form-group">                                                                                      ';
					 result = result +'       <label class="col-md-3 control-label">Manager</label>                                                       ';
					 result = result +'       <div class="col-md-6">                                                                                      ';
					 result = result +'                 <label>'+json.managerName+'</label>                                                                          ';
					 result = result +'         </div>                                                                                                    ';
					 result = result +'     </div>                                                                                                        ';
				 }
				 
				 if(json.teamName != null && json.teamName != '' && json.teamName != 'undefined'){
					 result = result +'     <div class="form-group">                                                                                      ';
					 result = result +'       <label class="col-md-3 control-label">Team</label>                                                          ';
					 result = result +'       <div class="col-md-6">                                                                                      ';
					 result = result +'           <label>'+json.teamName+'</label>                                                                                     ';
					 result = result +'       </div>                                                                                                      ';
					 result = result +'     </div>                                                                                                        ';
				 }
				 
				if(json.employmentType != null && json.employmentType != '' && json.employmentType != 'undefined'){
					 result = result +'     <div class="form-group">                                                                                      ';
					 result = result +'       <label class="col-md-3 control-label">Employment Type</label>                                               ';
					 result = result +'       <div class="col-md-6">                                                                                      ';
					 result = result +'           <label>'+json.employmentType+'</label>                                                                        ';
					 result = result +'       </div>                                                                                                      ';
					 result = result +'     </div>                                                                                                        ';
				}
				
				 
				 if(json.probationNotice != null && json.probationNotice !='' && json.probationNotice != 'undefined'){
					 result = result +'       <div class="form-group">                                                                                    ';
					 result = result +'       <label class="col-md-3 control-label">Probation Notice</label>                                              ';
					 result = result +'       <div class="col-md-6">                                                                                      ';
					 result = result +'           <label>'+json.probationNotice+'</label>                                                                                 ';
					 result = result +'       </div>                                                                                                      ';
					 result = result +'     </div>                                                                                                        ';
				 }
				
				if(json.terminationNotice != '' && json.terminationNotice != '' && json.terminationNotice != 'undefined'){
					result = result +'       <div class="form-group">                                                                                    ';
					 result = result +'       <label class="col-md-3 control-label">Termination Notice</label>                                            ';
					 result = result +'       <div class="col-md-6">                                                                                      ';
					 result = result +'           <label>'+json.terminationNotice+'</label>                                                                                  ';
					 result = result +'       </div>                                                                                                      ';
					 result = result +'     </div>                                                                                                        ';
				}
				 
				 if(json.dueDateStr != null && json.dueDateStr != '' && json.dueDateStr != 'undefined'){
					 result = result +'       <div class="form-group">                                                                                    ';
					 result = result +'       <label class="col-md-3 control-label">Due Date</label>                                                      ';
					 result = result +'       <div class="col-md-6">                                                                                      ';
					 result = result +'        <label>'+json.dueDateStr+'                                                                                          ';
					 result = result +'       </label></div>                                                                                              ';
					 result = result +'     </div>                                                                                                        ';
				 }
				 

				 if(json.specialConditions != null && json.specialConditions != '' && json.specialConditions != 'undefined'){
					  result = result +'       <div class="form-group">                                                                                    ';
						 result = result +'       <label class="col-md-3 control-label">Speical Conditions</label>                                            ';
						 result = result +'       <div class="col-md-6">                                                                                      ';
						 result = result +'           <p>                                                                                                     ';
						 result = result +'           '+json.specialConditions+'                              ';
						 result = result +'           </p>                                                                                                    ';
						 result = result +'       </div>                                                                                                      ';
						 result = result +'     </div>                                                                                                        ';
				 }
			   
				
				 result = result +'        <div class="form-group">                                                                                   ';
				 result = result +'            <label class="col-md-3 control-label">Onboard Template </label> ';
				 result = result +'       <div class="col-md-6">                                                                                    ';
				 result = result +'             <div id="onboardingTemplate"></div>                                                                                  ';
				 result = result +'       </div>                                                                                                    ';
				 result = result +'     </div>                                                                                                      ';
				
				 result = result +'   </div>                                                                                                        ';
				 result = result +'     </div>';
	        	 
				 $("#onboardCandidateSummaryDetailsId").empty();
            	 
            	 $("#onboardCandidateSummaryDetailsId").append(result);
            	 loadOnBoardingTemplates(json.onboardTemplateId);
	        	 
	        },
	    });
 }
 </script>
 <script>
function loadOnBoardingTemplates(onboardTemplateId){
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
        			result = result + report.templateName +' , ';
        		
			}
        	 $("#onboardingTemplate").empty();
           	 $("#onboardingTemplate").append(result); 
	    }
   });
}
</script>





<script>
function getTemplateContractsDocumentsDetails(){
	var onBoardingUUID = '${onBoardingUUID}';
	var documentType = "Contracts"
		var sourceleadUrl ='${sourceleadUrl}';
	     $.ajax({
		    	type: "POST",
		    	 url: sourceleadUrl+"/getCandidateOnboardTemplateDocumentByonBoardingUUID?${_csrf.parameterName}=${_csrf.token}&onBoardingUUID="+onBoardingUUID+"&documentType="+documentType, 
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	var result = "";
		        	result = result +'	 <div class="row">                                                       ';
		        	for (var i = 0; i < json.length; i++) { 
               		 var report = json[i]; 
               		 
               		
						result = result +'    <div class="col-md-2 text-center mb10">                                ';
						result = result +'      <div class="uploaded_file"> <i class="fa fa-file-text-o fa-4x"></i>  ';
						result = result +'        <p class="small mt tbldata-dotdot-100" title="'+report.fileName+'">'+report.fileName+'</p>                        ';
						result = result +'        <i class="fa fa-check text-success permission"></i> </div>         ';
						result = result +'    </div>                                                                 ';
		        	}
					result = result +'    </div>                                                                 ';
                	 $("#employmentContractDivId").empty();
                	 $("#employmentContractDivId").append(result);
                	 
                	 
			        
		        },
		    });
}
</script>

<script>
function getTemplateFormsDocumentsDetails(){
	var onBoardingUUID = '${onBoardingUUID}';
	var documentType = "Forms"
		var sourceleadUrl ='${sourceleadUrl}';
	     $.ajax({
		    	type: "POST",
		    	 url: sourceleadUrl+"/getCandidateOnboardTemplateDocumentByonBoardingUUID?${_csrf.parameterName}=${_csrf.token}&onBoardingUUID="+onBoardingUUID+"&documentType="+documentType, 
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	var result = "";
		        	result = result +'	 <div class="row">                                                       ';
		        	for (var i = 0; i < json.length; i++) { 
               		 var report = json[i]; 
               		 
               		
						result = result +'    <div class="col-md-2 text-center mb10">                                ';
						result = result +'      <div class="uploaded_file"> <i class="fa fa-file-text-o fa-4x"></i>  ';
						result = result +'        <p class="small mt tbldata-dotdot-100" title="'+report.fileName+'">'+report.fileName+'</p>                        ';
						result = result +'        <i class="fa fa-check text-success permission"></i> </div>         ';
						result = result +'    </div>                                                                 ';
		        	}
					result = result +'    </div>                                                                 ';
                	 $("#formstDivId").empty();
                	 $("#formstDivId").append(result);
                	 
                	 
			        
		        },
		    });
}
</script>
<script>
function getTemplatePolicyDocumentsDetails(){
	var onBoardingUUID = '${onBoardingUUID}';
	var documentType = "Policies"
		var sourceleadUrl ='${sourceleadUrl}';
	     $.ajax({
		    	type: "POST",
		    	 url: sourceleadUrl+"/getCandidateOnboardTemplateDocumentByonBoardingUUID?${_csrf.parameterName}=${_csrf.token}&onBoardingUUID="+onBoardingUUID+"&documentType="+documentType, 
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	var result = "";
		        	result = result +'	 <div class="row">                                                       ';
		        	for (var i = 0; i < json.length; i++) { 
               		 var report = json[i]; 
               		 
               		
						result = result +'    <div class="col-md-2 text-center mb10">                                ';
						result = result +'      <div class="uploaded_file"> <i class="fa fa-file-text-o fa-4x"></i>  ';
						result = result +'        <p class="small mt tbldata-dotdot-100" title="'+report.fileName+'">'+report.fileName+'</p>                        ';
						result = result +'        <i class="fa fa-check text-success permission"></i> </div>         ';
						result = result +'    </div>                                                                 ';
		        	}
					result = result +'    </div>                                                                 ';
                	 $("#policiesDivId").empty();
                	 $("#policiesDivId").append(result);
                	 
                	 
			        
		        },
		    });
}
</script>

<script>
function getTemplateSupportingDocumentsDetails(){
	var onBoardingUUID = '${onBoardingUUID}';
	var documentType = "Supported"
		var sourceleadUrl ='${sourceleadUrl}';
	     $.ajax({
		    	type: "POST",
		    	 url: sourceleadUrl+"/getCandidateOnboardTemplateDocumentByonBoardingUUID?${_csrf.parameterName}=${_csrf.token}&onBoardingUUID="+onBoardingUUID+"&documentType="+documentType, 
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	var result = "";
		        	result = result +'	 <div class="row">                                                       ';
		        	for (var i = 0; i < json.length; i++) { 
               		 var report = json[i]; 
               		 
               		
						result = result +'    <div class="col-md-2 text-center mb10">                                ';
						result = result +'      <div class="uploaded_file"> <i class="fa fa-file-text-o fa-4x"></i>  ';
						result = result +'        <p class="small mt tbldata-dotdot-100" title="'+report.fileName+'">'+report.fileName+'</p>                        ';
						result = result +'        <i class="fa fa-check text-success permission"></i> </div>         ';
						result = result +'    </div>                                                                 ';
		        	}
					result = result +'    </div>                                                                 ';
                	 $("#supportedDivId").empty();
                	 $("#supportedDivId").append(result);
                	 
                	 
			        
		        },
		    });
}
</script>
 <jsp:include page="../masterFooter.jsp" />