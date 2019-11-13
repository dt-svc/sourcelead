<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>


<script src="/resources/js/summernote.js?staticTime=${staticTime}"></script>
<link href="/resources/css/summernote.css?staticTime=${staticTime}" rel="stylesheet" />
<script src="/resources/js/editor.js?staticTime=${staticTime}"></script>
<link href="/resources/css/editor.css?staticTime=${staticTime}" type="text/css" rel="stylesheet"/>

<!-- Content Area -->
<section class="content-wraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Create Project</h2>
		</div>
		<div class="pull-right brud-crum">
			<a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>
			&raquo;<a href="<c:url value='${appPath}/projectDashboard'/>"> Projects List</a>
			&raquo; Create Project
	</section>
	<div class="content-area clearfix">
		<div id="projectDetailsloading"></div>
		
		<div class="newPrDetails newPrDetailsPMO"> 
      		<div class="newPrTabs">
   			  <ul class="newPrTabsMenu">
				<li class="current" onclick="projects.getProjectDetails();"><a href="#tab-a" data-toggle="tab">Details</a></li>
				<li><a href="#tab-b" data-toggle="tab" id="assignedMembers" onclick="getAssignedMembers();">Assign Members</a></li>
				<li><a href="#tab-c" data-toggle="tab" id="additionalDetails" onclick="getAdditionalDetails();">Questionnaire</a></li>
				<div class="clearfix mb"></div>
			  </ul>
			  <div class="clearfix"></div>
		
		<div class="project_tabs projectTabs_brdnone">
		<div class="clearfix form-area form-horizontal toggle-div remove-div  newPrTabs_inn mt" id="tab-a">
			<form:form id="project_form" name="project_form" method="post">
			<input type="hidden" id="actionId" value="${actionId}">
				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">

						<div class="form-group" id="ProjectIdDiv">
							<label class="col-lg-3">Project Id</label>
							<div class="col-lg-9">
								<input type="text" value="" id="projectSequeneNumber"
									name="projectSequeneNumber"
									data-validation="validate_Space validate_length length2-50"
									data-validation-optional="true" class="form-control" placeholder="Project Id"
									onblur="projects.validateProjectSequeneNumber();">
							</div>
						</div>
						
						 <input type="hidden" name="projectId" id=projectId value=""/>
						 <input type="hidden" name="projectUUID" id="projectUUID" value=""/>
						 <input type="hidden" name="startDateStr" id="startDateStr" value=""/>
						 <input type="hidden" name="endDateStr" id="endDateStr" value=""/>
						 <input type="hidden" id="loginOrgLocationId" value="${SourceLeadWeb_orgLocations.id}"/>
						 
 
						<div class="form-group">
							<label class="col-md-3"> Date <span class="text-danger">*</span></label>
							<div class="col-md-9">
								<div class="row">
									<div class="col-lg-6 positionRelative" id="StartDateDiv">
										<input type="text" class="form-control calendar" value="" id="startDate" name="startDate" field-name="Start Date" placeholder="Start Date" data-validation="required" readonly="readonly" > 
									</div>
									<div class="col-lg-6 positionRelative" id="endDateDiv">
									<div class="input-group errorInptgroup">
										<input type="text" class="form-control calendar" value="" id="endDate" name="endDate" field-name="End Date" placeholder="End Date" readonly="readonly">
										 <span class="input-group-addon" onClick="clearProjectEndDate();"><i class="colorRed fa fa-times cursorPointer"></i></span>
									</div>
									</div>
									<span class="hidden">Enter Project Id</span>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3" for="Project Id">Status<span
								class="text-danger">*</span></label>
							<div class="col-md-9 select-field">
								<select class="form-control" name="status" id="status" onchange="getStatus();" field-name="Status" data-validation="required">
									<option value="">Select Status</option>
									<option value="Not Started">Not Started</option>
									<option value="In-Progress">In-Progress</option>
									<option value="Completed">Completed</option>
									<option value="On-Hold">On-Hold</option>
									<option value="Dropped">Dropped</option>
								</select> <span class="hidden">Select Status</span>
							</div>
						</div>
						
						
						
						<div class="form-group">
							<label class="col-md-3">Timesheet Frequency <span
								class="text-danger">*</span></label>
							<div class="col-md-9" id="chosen-InvoiceFrequency">
								<select class="form-control chosen-select" id="invoiceFrequency"
									name="invoiceFrequency" onchange="removeInvoiceFrequencyValidation();"
									field-name="Invoice Frequency" data-validation="required">
									<option value="">-Invoice Frequency-</option>
									<option value="Weekly">Weekly</option>
									<option value="Bi-Weekly">Bi-Weekly</option>
									<option value="Semi-Monthly">Semi-Monthly</option>
									<option value="Monthly">Monthly</option>
								</select>
							</div>
						</div>
						
						
					
					 <div class="form-group">
						<label class="col-md-3">Timesheet Approval Mechanism</label>
						<div class="col-md-9">
							<label class="paddingLeft paddingRight"><input type="radio" id="parallelTimesheetsApproval" value="Parallel" checked onclick="projectApprovalForCheckValidation(this)" name="projectApprovalType"><span class="mr5"></span>Parallel</label>
							<label class="paddingRight"><input type="radio" id="sequentialTimesheetsApproval"  value="Sequential" onclick="projectApprovalForCheckValidation(this)" name="projectApprovalType"><span class="mr5"></span>Sequential</label>
						    <input type="hidden"  id="projectApprovalTypeValue" name="projectApprovalTypeValue"  field-name="Timesheet Approval Mechanism" data-validation="required" value="Parallel" /> 
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3">Expense Approval Mechanism</label>
						<div class="col-md-9">
							<label class="paddingLeft paddingRight"><input type="radio" id="parallelExpenseApproval" value="Parallel" checked onclick="expenseApprovalForCheckValidation(this)"  name="expenseApprovalType"><span class="mr5"></span>Parallel</label>
							<label class="paddingRight"><input type="radio" id="sequentialExpenseApproval"  value="Sequential" onclick="expenseApprovalForCheckValidation(this)" name="expenseApprovalType"><span class="mr5"></span>Sequential</label>
						    <input type="hidden"  id="expenseApprovalTypeValue" name="expenseApprovalTypeValue"  field-name="Expense Approval Mechanism" data-validation="required" value="Parallel" /> 
						</div>
					</div>  
						
						
					<div class="form-group">
						<label class="col-md-3">Project For<span class="text-danger">&nbsp;*</span></label>
						<div class="col-md-9">
							<label class="paddingLeft paddingRight"><input type="radio" id="timesheets" value="Timesheets" name="projectFor" onclick="projectForCheckValidation(this)"><span class="mr5"></span>Timesheets</label>
							<label class="paddingRight"><input type="radio" id="expenses"  value="Expenses" name="projectFor" onclick="projectForCheckValidation(this)"><span class="mr5"></span>Expenses</label>
							<label class="paddingRight"><input type="radio" id="timesheetsandexpenses"  value="TimesheetsAndExpences"  name="projectFor" onclick="projectForCheckValidation(this)"><span class="mr5"></span>Timesheets & Expenses</label>
							<input type="hidden"  id="projectForValue" name="projectForValue"  field-name="Project For" data-validation="required"/> 		
						</div>
						
					</div>
						
						<c:if test="${!empty projectType && projectType == 'sharedProject' }">
							<input type="hidden" name="savedSharedClientOrgLocId" id="savedSharedClientOrgLocId">
							<div class="form-group">
							<label class="col-md-3" for="Client">Client<span class="text-danger">*</span></label>
							<div class="col-md-9 select-field">
								<select class="form-control chosen-select"
											name="sharedClientOrgLocId" id="sharedClientOrgLocId" field-name="Client"
											data-validation-optional="true">
											<option value="">-- Select Client --</option>
											<c:forEach var="report" items="${clientsList}">
											<option value="${report.clientLocationId}">${report.clientName}</option>
											</c:forEach>
								</select>
							</div>
						</div>
							
					</c:if>
						
						
						<div class="form-group">
					             <label class="col-md-3"></label>
					              <div class="col-md-9">
					                <div class="checkbox checkBox">
					                  <label>
					                    <input type="checkbox" value="Y" name="isTicketNumberRequired" id="isTicketNumberRequired">
					                   Ref/Ticket Number Required In Time Sheets</label>
					                </div>
					                 </div>
			               </div>
			               
			               <div class="form-group">
					             <label class="col-md-3"></label>
					              <div class="col-md-9">
					                <div class="checkbox checkBox">
					                  <label>
					                    <input type="checkbox" value="Y" name="isTaskCommentsRequired" id="isTaskCommentsRequired">
					                   Task Comments Required In Time Sheets</label>
					                </div>
					                 </div>
			               </div>

						 <div class="form-group">
							<label class="col-md-3"></label>
							<div class="col-md-9">
								<div class="checkBox">
									<label> <input type="checkbox" value="Y" name="isAllowFreelanceDevelopers" id="isAllowFreelanceDevelopers" > Allow Freelance Developers </label>
								</div>
							</div>
						</div> 

						<div class="form-group">
							<label class="col-md-3"></label>
							<div class="col-md-9">
								<div class="checkBox">
									<label> <input type="checkbox" value="Y" name="isAllowRemoteWork" id="isAllowRemoteWork" > Allow Remote Work </label>
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3"></label>
							<div class="col-md-9">
								<div class="checkBox">
									<label> <input type="checkbox" value="Y" name="isAllowOverTime" id="isAllowOverTime" onchange="allowOverTime();"> Allow Over Time </label>
								</div>
							</div>
						</div>


						<div class="form-group" id="flatPercentageDivId">
							<label class="col-md-3"></label>
							<div class="col-md-9">
								<div class="input-group">
									<div class="input-group-btn selp-custom-min tblminwidth-125" id="flatPercentageId" >
										<select class="form-control chosen-select"  name="flatPercentageId" id="flatPercentId" onchange="changeOverTime();">
											<option value="flat">FLAT</option>
											<option value="percentage">PERCENTAGE</option>
										</select>
									</div>
									<input type="number" min="0" max="100" step="0.5" value="0"
										id="flatPercentage" name="flatPercentage" maxlength="5"
										class="form-control" data-validation-optional="true" onkeyup="flatPercentageType();" onblur="">
								</div>
							</div>
						</div>


					</div>

					<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
						<c:if test="${empty user_List}">
							<p style="font-size: 11px; color: red;" class="text-muted small">
								<i class="fa fa-info-circle"></i>&nbsp;All Admin's in Org will have access to Edit/Approve/Reject TimeSheet
							</p>
						</c:if>

						<div class="form-group">
							<label class="col-md-3"> Project Name <span
								class="text-danger">*</span>
							</label>
							<div class="col-md-9">

								<input type="text" class="form-control" name="projectName" id="projectName" placeholder="Project name" onblur="projects.validateProjectName();" data-validation="required validate_Space validate_length length2-250"
									field-name="Project Name" value=""> <span class="hidden">Project Name</span> <span class="hidden" >Enter Project Id</span>

							</div>
						</div>

					<c:if test="${not empty user_List}">
							<div class="form-group">
								<label class="col-md-3" for="Project Manager">Manager <span
									class="text-danger">*&nbsp;</span></label>
								<div class="col-md-9">
									<div class="form-icon-min" id="chosen-level1Manager">
										<select class="form-control chosen-select"
											name="level1Manager" id="level1Manager" field-name="Manager"
											data-validation="required" onchange="projects.getLevel2Approvers();">
											<option value="">-- Select Level1 Manager --</option>
										</select> <span class="btn btn-default formic1 addLevel"
											id="level2Toggle" onclick="projects.getLevel2Approvers();">Add
											Level-II</span>
									</div>
									<span class="hidden">Select Project Manager</span>
								</div>
							</div>
							<div class="form-group level-second" id="level2Div">
								<label class="col-md-3" for="Level-II">Level-II Manager</label>
								<div class="col-md-9">
									<select class="form-control chosen-select" name="level2Manager"
										id="level2Manager">
									</select>
								</div>
							</div>
						</c:if>
						
						
						
						<c:if test="${not empty user_List}">
							<div class="form-group">
								<label class="col-md-3" for="Project Manager">Expense Approver&nbsp;<i class='fa fa-info-circle' title='Expense Approver for project. If not selected managers will be the default approvers'></i>
								</label>
								<div class="col-md-9">
									<div class="form-icon-min" id="chosen-level1ExpenseApprover">
										<select class="form-control chosen-select"
											name="level1ExpenseApprover" id="level1ExpenseApprover" field-name="Expense Approver"
											data-validation-optional="true" onchange="projects.getLevel2ExpenseApprovers();">
											<option value="">-- Select Level1 Approver --</option>
											
										</select> <span class="btn btn-default formic1 addExpenseApproverLevel"
											id="level2ExpenseApproverToggle" onclick="projects.getLevel2ExpenseApprovers();">Add
											Level-II</span>
									</div>
									<span class="hidden">Select Project Manager</span>
								</div>
							</div>
							<div class="form-group expenselevel-second" id="level2ExpenseApproverDiv">
								<label class="col-md-3" for="Level-II">Level-II Approver</label>
								<div class="col-md-9">
									<select class="form-control chosen-select" name="level2ExpenseApprover"
										id="level2ExpenseApprover">
									</select>
								</div>
							</div>
						</c:if>
						
						<div class="form-group">
					             <label class="col-md-3"></label>
					              <div class="col-md-9">
					                <div class="checkbox checkBox">
					                  <label>
					                    <input type="checkbox" value="Y" name="isReportingManagerAsApprover" id="isReportingManagerAsApprover">
					                   Reporting Manager will be T/E approver</label>
					                </div>
					                 </div>
			               </div> 
						
						<div class="form-group" id="shortCodeDivId">
							<label class="col-md-3"> Project Short Code <i class='fa fa-info-circle' title='The internal unique code for the project'></i>
							</label>
							<div class="col-md-9">
								<input type="text" class="form-control" name="projectShortCode" id="projectShortCode" placeholder="Project Short Code" data-validation-optional="true" data-validation="validate_length length3-3"
									field-name="Project Short Code" value="" onkeyUp="projects.toUpperCase();" onblur="projects.validateShortCode();"  pattern="[a-zA-Z]"  maxlength="3">  

							</div>
						</div>
						
						
						<div class="form-group">
							<label class="col-md-3"></label>
							<div class="col-md-9">
								<div class="checkBox">
									<label> <input type="checkbox" value="Y" name="isTimesheetAttachmentRequired" id="isTimesheetAttachmentRequired"> Timesheet Attachment Required </label>
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3"></label>
							<div class="col-md-9">
								<div class="checkBox">
									<label> <input type="checkbox" value="Y" name="isAllowOffshoreResource" id="isAllowOffshoreResource"> Allow Offshore Resource </label>
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3"></label>
							<div class="col-md-9">
								<div class="checkBox">
									<label> <input type="checkbox" value="Y" name="isTimeEntryAfterDeadline" id="isTimeEntryAfterDeadline"> Time Entry after Deadline </label>
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3"></label>
							<div class="col-md-9">
								<div class="checkBox">
									<label> <input type="checkbox" value="Y" name="isAllowBidding" id="isAllowBidding"> Allow Bidding </label>
								</div>
							</div>
						</div>
					</div>	
						<div class="clearfix mb myForm">
						<div class="clearfix"></div>
			            <c:forEach var="section" items="${sourceleadSectionList}" varStatus="sectionOrder">
			               <c:if test="${sectionOrder.index == 0}">
			                  <div id="customDiv${section.id}"></div>
			               </c:if>
			            </c:forEach>
						</div>
						
					
				</div>
				
				<!--  div row end -->
<!-- dont remove the code because next implement code Laxman -->
				<!--  div row start -->
			 <div class="row">
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
						<h5>Budget Details</h5>
						<div class="form-group" id="">
							<label class="col-md-3"> </label>
							<div class="col-md-9"></div>
						</div>
					</div>
				</div> 


          
				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">

						<div class="form-group" id="EstimatedHoursDiv">
							<label class="col-md-3">Estimated hours</label>
							<div class="col-md-9">
								<input type="text" value="" id="estimatedHours"  class="form-control" data-validation-optional="true"
									data-validation="validate_Space validate_float validate_length length1-15" class="form-control" onblur="">
							</div>
						</div>

						<div class="form-group" id="EstimatedBudgetDiv">
							<label class="col-md-3">Notify Budget At</label>
							<div class="col-md-9">
								<div class="input-group errorInptgroup">
								<div class="input-group-btn tblminwidth-75" style="min-width: 65px !important;">
								      <input type="text" class="pull-left btn btn-primary btn-block" value="%" readonly>
					                </div>
									<input type="text" value="" id="notifyBudgetAt" name="notifyBudgetAt" class="form-control"
										data-validation="validate_Space validate_length length1-50" data-validation-optional="true" class="form-control" onkeyup="recSelectType();" >
								</div>
							</div>
						</div>

					</div> 


					 <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
						<div class="form-group" id="EstimatedBudgetDiv">
							<label class="col-md-3">Estimated Budget</label>
							<div class="col-md-9">
								<div class="input-group">
								<div class="input-group-btn selp-custom-min selp-customMin">
					                  <select class="selectpicker" id="estimatedBudgetCurrency" name="estimatedBudgetCurrency" >
						                 <option ${SourceLeadWeb_currencyCode == 'INR' ? 'selected' : ''}>INR</option>
						                 <option ${SourceLeadWeb_currencyCode == 'AUD' ? 'selected' : ''}>AUD</option>
						                 <option ${SourceLeadWeb_currencyCode == 'USD' ? 'selected' : ''}>USD</option>
						                 <option ${SourceLeadWeb_currencyCode == 'GBP' ? 'selected' : ''}>GBP</option>
						                 <option ${SourceLeadWeb_currencyCode == 'SGD' ? 'selected' : ''}>SGD</option>
						                 <option ${SourceLeadWeb_currencyCode == 'EUR' ? 'selected' : ''}>EUR</option>
					                  
					                </select>
					                </div>
									<input type="text" value="" id="estimatedBudget"  class="form-control"
										data-validation="validate_Space validate_float validate_length length1-15" data-validation-optional="true" class="form-control" >
								</div>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-md-3">Budget Approver <i class='fa fa-info-circle' title='The person who approves the budget for the project'></i></label>
							<div class="col-md-9" id="chosen-budgetapprover">
								<select class="form-control" id="budgetApprover" name="budgetApproverUser" onchange="" field-name="Budget Approver" onchange="projects.getBudgetApprover();">
								<option value="">-- Select Budget Approver --</option>
								</select>
							</div>
						</div>
					</div> 


					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12  txtEditor_p">
						<h5>Description</h5>
						<textarea id="txtEditor" name="projectDescription"   value="" class="form-control newContrtextarea"  data-validation-optional="true"></textarea>
					</div>
					
					<div id="timesheetDocumentsId">
			       		<div class="timesheet_week_comm invoiceDocs clearfix">
			       			<span>
			         			 <div id="savedProjectDocuments" class="col-md-6 mt"></div>
			         		</span>
			           </div>
			         </div>

					<!--  Documents Section -->
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
						<div class="row mt mb10">
						<h5 class="col-md-1 paddingRight">Documents <i class='fa fa-info-circle' title='The project documents'></i></h5>
						<div class="col-md-10 paddingLeft">
						<input id="Button1" type="button" value="Add Document" onclick="addFileUpload()" class="btn btn-sm btn-primary" />
						</div>
							</div>
						<div id="FileUploadContainer">
							<!--FileUpload Controls will be added here -->
						</div>
						<br />
						<!--<input type="file" name="project_files" id="project_files" multiple="multiple"/>-->
					</div>
					
					<!-- <div class="clearfix col-md-12">
						<span><input type="checkbox" id="isMailToProjectManager" name="isMailToProjectManager">&nbsp;Mail to manager</span>
					</div> -->
					
		            <c:forEach var="section" items="${sourceleadSectionList}" varStatus="sectionOrder">
		               <c:if test="${sectionOrder.index == 1}">
		                  <div id="customDiv${section.id}"></div>
		               </c:if>
		            </c:forEach>

				</div>

				<!--  div row end -->


				<div class="form-footer clearfix mt">
					<div class="col-lg-12">
						<div class="pull-right">
							<input type="button" value="Cancel" onClick="javascript:window.location.href = '<c:url value='${appPath}/projectDashboard'/>'" class="btn btn-danger ml">
							<input type="button" value="Save & Continue" id="save_project" onclick="projects.saveProject();flushSaveProject()" class="btn btn-primary">
						</div>
					</div>
				</div>



			</form:form>
		</div>
	   <jsp:include page="projectAssignedMembers.jsp" />
	   <jsp:include page="projectAdditionalDetails.jsp" />
   </div>
		</div>
   	</div>
</div>
</section>
<!-- Content Area Ends-->

<script type="text/javascript" src="/resources/js/projects.js"></script>


<script type="text/javascript">
	function projectForCheckValidation(id){
		document.getElementById("projectForValue").value = id.value;
	} 
	
	function projectApprovalForCheckValidation(id){
		$('#projectApprovalTypeValue').val('');
		document.getElementById("projectApprovalTypeValue").value = id.value;
	} 
	
	function expenseApprovalForCheckValidation(id){
		$('#expenseApprovalTypeValue').val('');
		document.getElementById("expenseApprovalTypeValue").value = id.value;
	} 
	
	
	
</script>

<script>
function removeFrequencyValidation(){
	$('#chosen-frequency').find('p.jquery_form_error_message').remove();
}

function allowOverTime(){
	
	if ($('#isAllowOverTime').is(":checked"))
	{
		$('#flatPercentageId').attr('style','visibility:visible');
		$('#flatPercentage').attr('style','visibility:visible');
	}else{
		$('#flatPercentageId').attr('style','visibility:hidden');
		$('#flatPercentage').attr('style','visibility:hidden');
		
		$('#flatPercentage').val('');
		$('#flatPercentageDivId').parent().find('p.jquery_form_error_message').remove(); 
		$("#save_project").removeAttr("disabled");
		
	}
	
}
function removeInvoiceFrequencyValidation(){
	$('#chosen-InvoiceFrequency').find('p.jquery_form_error_message').remove();
}
</script>
<script>
function getStatus(){
	$('#endDateDiv').find('p.jquery_form_error_message').remove(); 
	var status = $("#Satatus").val();
	if(status != null && status != "" && status != "undefined" && status == "Completed"){
		 $('#endDate').removeAttr('data-validation-optional');
		 $('#endDate').attr("data-validation","required"); 
	 }else{
		 $('#endDate').removeAttr('data-validation');
		 $('#endDate').attr("data-validation-optional","true"); 
	 }
}

</script>

<!-- Add Client Model DialogBox ends-->

<jsp:include page="../masterFooter.jsp" />
<script type="text/javascript">
	(function($) {
		$.fn.serializeFormJSON = function() {

			var o = {};
			var a = this.serializeArray();
			$.each(a, function() {
				if (o[this.name]) {
					if (!o[this.name].push) {
						o[this.name] = [ o[this.name] ];
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


<script>
	$(document).ready(function() {
		$('#flatPercentageId').attr('style','visibility:hidden');
		$('#flatPercentage').attr('style','visibility:hidden');
		
			
		var savedProjectId = '${savedProjectId}';
		var projectUUID = '${projectUUID}';
		if(savedProjectId != null && savedProjectId != '' && savedProjectId != 'undefined'){
			$("#projectId").val(savedProjectId);
			$("#projectUUID").val(projectUUID);
			projects.getProjectDetails();
			
			var isProjectDetailsFilled = '${isProjectDetailsFilled}';
			if(isProjectDetailsFilled == 'N'){
				var assignedMembers = document.getElementById("assignedMembers");
				if(assignedMembers){
					assignedMembers.href = "#";
					assignedMembers.className = "disabledLink";
				}
			}
			var isProjectMembersFilled = '${isProjectMembersFilled}';
			if(isProjectMembersFilled == 'N'){
				var additionalDetails = document.getElementById("additionalDetails");
				if(additionalDetails){
					additionalDetails.href = "#";
					additionalDetails.className = "disabledLink";
				}
			}
			
		}else{
			var assignedMembers = document.getElementById("assignedMembers");
			if(assignedMembers){
				assignedMembers.href = "#";
				assignedMembers.className = "disabledLink";
			}
			
			var additionalDetails = document.getElementById("additionalDetails");
			if(additionalDetails){
				additionalDetails.href = "#";
				additionalDetails.className = "disabledLink";
			}
			projects.getProjectLevel1Approvers();
			projects.getProjectLevel1ExpenseApprovers();
			projects.getBudgetApprover();
		}
		
		$("#tab-a").show()
	    $(".newPrTabsMenu a").click(function(event) {
	        event.preventDefault();
	        $(this).parent().addClass("current");
	        $(this).parent().siblings().removeClass("current");
	        var tab = $(this).attr("href");
	        $(".newPrTabs_inn").not(tab).css("display", "none");
	        $(tab).fadeIn();
	    });
	});
</script>

<script type="text/javascript">
	function flushSaveProject() {
		$("#save_project").removeAttr("disabled");
	}
</script>




<script>
$(window).load(function() {
	var dbStartDate = $("#startDate").val();
		 var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
		  var finalDateFormatProjectSave = '';
		  if(locationDate.match("yyyy-MM-dd")){
			  finalDateFormatProjectSave = 'yy-mm-dd';
		  }if(locationDate.match("dd-MM-yyyy")){
			  finalDateFormatProjectSave = 'dd-mm-yy'; 
		  }if(locationDate.match("MM-dd-yyyy")){
			  finalDateFormatProjectSave = 'mm-dd-yy'; 
		  }if(locationDate.match("yyyy-dd-MM")){
			  finalDateFormatProjectSave = 'yy-dd-mm'; 
		  }
		$('#startDate').datepicker({
			dateFormat : finalDateFormatProjectSave,
			changeMonth: true,
			changeYear: true, 
			onSelect : function(selected) {
				var dt = $(this).datepicker('getDate', '+1d');
				dt.setDate(dt.getDate() + 1);
				$("#endDate").datepicker("option", "minDate", dt);
				addValidClass();
			}
		});
		$('#endDate').datepicker({
			dateFormat : finalDateFormatProjectSave,
			changeMonth: true,
			changeYear: true, 
			minDate:dbStartDate,
			onSelect : function(selected) {
				var dt = $(this).datepicker('getDate', '-1d');
				dt.setDate(dt.getDate() - 1);
				$("#startDate").datepicker("option", "maxDate", dt);
				addValidClass();
				//this is section is for compare project end date with current date
				//if project end date is less than current date, the project state should be COMPLETED
				var dummyDate = $(this).val();
                var today = new Date();
                var dd = today.getDate();
                var mm = today.getMonth()+1; //January is 0!
                var yyyy = today.getFullYear();
                if(dd<10){
                    dd='0'+dd
                } 
                if(mm<10){
                    mm='0'+mm
                } 
                
               
                var today = yyyy+'-'+mm+'-'+dd;
                
                var proStartDateTP = today.split("-");
                var d1 = new Date(proStartDateTP[0], proStartDateTP[1] - 1, proStartDateTP[2]);
                
                var proEndDateTP = dummyDate.split("-");
                if(locationDate.match("yyyy-MM-dd")){
                	 var d2 = new Date(proEndDateTP[0], proEndDateTP[1] - 1, proEndDateTP[2]);
      		    }if(locationDate.match("dd-MM-yyyy")){
      		    	var d2 = new Date(proEndDateTP[2], proEndDateTP[1] - 1, proEndDateTP[0]);
      		    }if(locationDate.match("MM-dd-yyyy")){
      		    	var d2 = new Date(proEndDateTP[2], proEndDateTP[0] - 1, proEndDateTP[1]);
      		    }if(locationDate.match("yyyy-dd-MM")){
      		    	var d2 = new Date(proEndDateTP[0], proEndDateTP[2] - 1, proEndDateTP[1]);
      		    }
      		    
      		    
      		   var projectStartDate = $("#startDate").val();
               var from = projectStartDate.split("-");
               if(locationDate.match("yyyy-MM-dd")){
            	   var d3 = new Date(from[0], from[1] - 1, from[2]);
    		    }if(locationDate.match("dd-MM-yyyy")){
    		    	var d3 = new Date(from[2], from[1] - 1, from[0]);
    		    }if(locationDate.match("MM-dd-yyyy")){
    		    	var d3 = new Date(from[2], from[0] - 1, from[1]);
    		    }if(locationDate.match("yyyy-dd-MM")){
    		    	var d3 = new Date(from[0], from[2] - 1, from[1]);
    		    }
                
				if(d1 > d2){
					$("#status").empty();
					$('#status').append('<option >Completed</option>');
				}else{
					$("#status").empty();
					if(d1 < d3){
						$('#status').append('<option >Not Started</option>');
						$('#status').append('<option >In-Progress</option>');
						$('#status').append('<option >Completed</option>');
						$('#status').append('<option >On-Hold</option>');
						$('#status').append('<option >Dropped</option>');
					}else{
						$('#status').append('<option >In-Progress</option>');
						$('#status').append('<option >Not Started</option>');
						$('#status').append('<option >Completed</option>');
						$('#status').append('<option >On-Hold</option>');
						$('#status').append('<option >Dropped</option>');
					}
					
				}
			}
		});

	});
</script>
<script type="text/javascript"> 
	function addValidClass(){
		 var startDate = document.getElementById("startDate").value;
		 if(startDate){
			 $('#StartDateDiv').find('p.jquery_form_error_message').remove(); 
		 }
		 
		 var endDate = document.getElementById("endDate").value;
		 if(endDate){
			 $('#endDateDiv').find('p.jquery_form_error_message').remove(); 
		 }
}
</script>

<script>
	function getStartDatePickerById(id, incrementor) {
		$('#' + id).datepicker({
			dateFormat : 'yy-mm-dd',
			onSelect : function(selected) {
				var dt = new Date(selected);
				dt.setDate(dt.getDate() + 1);
			}
		});
	}
</script>
<script>
	function getEndDatePickerById(id, incrementor) {
		$('#' + id).datepicker({
			dateFormat : 'yy-mm-dd',
			onSelect : function(selected) {
				var dt = new Date(selected);
				dt.setDate(dt.getDate() + 1);
			}
		});
	}
</script>


									
<script type="text/javascript">
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };
</script>
<!-- <script src="resources/js/chosen.jquery.min.js"></script>  -->
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
				});
</script>

<!-- script for converting dateformat in datepicker -->

<!-- script for dateformat in datepicker End -->

<script>
	$(document).ready(function() {
		<c:forEach var="section" items="${sourceleadSectionList}" varStatus="sectionOrder">
		  var sectionId = '${section.id}';
		  getCustomDependentField(sectionId);
		 </c:forEach>
		 
		 var projectType = '${projectType}';
		 if(projectType != '' && projectType != 'undefined' && projectType == 'sharedProject'){
			 $("#sharedClientOrgLocId").removeAttr("data-validation-optional");
			 $("#sharedClientOrgLocId").attr("data-validation", "required");
		 }
		 
	});
</script>


<!-- <script>
	$(document).ready(function() {
		$("#txtEditor").Editor();
		$("#txtEditor").find(".Editor-editor").data-validation('validate_length length2-5000');
		$("#txtEditor").find(".Editor-editor").data-validation-optional('rule-required',true);
	});
</script> -->
 
<script>
function getCustomDependentField(sectionId){
	var projectId = $('#projectId').val();
	$.ajax({
			type : "POST" , 
				 url: sourceleadUrl+"/getAllASectionCustomFields?sectionId="+sectionId+"&projectId="+projectId+"&requestFrom=project",
				 success: function(data) {
		        	$('#customDiv'+sectionId).empty();
		        	$('#customDiv'+sectionId).append(data);
				 }
		  });
}
</script>


 
 
 <script type="text/javascript">
function recSelectType(){
	 var canScore = document.getElementById("notifyBudgetAt").value;
	 $('#EstimatedBudgetDiv').parent().find('p.jquery_form_error_message').remove(); 
		  var floatNum = /^(\d{0,2}(\.\d{0,2})?|100)$/
		  if(canScore.match(floatNum)){
			  $("#save_project").removeAttr("disabled");
			   return true;
		  }		  
		 else{
				$("input#notifyBudgetAt").parent().after("<p class='jquery_form_error_message'><span>Enter your Valid Percentage</span>");
				 $("#save_project").attr("disabled", "disabled");
				return false; 
		 	 }

	 
	
		 }
	  
</script>

 <script type="text/javascript">
function flatPercentageType(){
	var flatPercentId  = $("#flatPercentId").val();
	 var flatPercentage = document.getElementById("flatPercentage").value;
	 $('#flatPercentageDivId').parent().find('p.jquery_form_error_message').remove();
	 var floatNum = "";
	 
	 if(flatPercentage != '' && flatPercentage != 'undefined' && flatPercentage != null){
		 if(flatPercentId == 'flat'){
			  var floatNum = /^(\d{0,10}(\.\d{0,10})?|100)$/
		 }else{
			  var floatNum = /^(\d{0,2}(\.\d{0,2})?|100)$/
		 }
		  if(flatPercentage.match(floatNum)){
			  $("#save_project").removeAttr("disabled");
			   return true;
		  }		  
		 else{
			 if(flatPercentId == 'flat'){
					$("input#flatPercentage").parent().after("<p class='jquery_form_error_message'><span>Enter  valid flat value length 1 to 10</span>");
			 }else{
					$("input#flatPercentage").parent().after("<p class='jquery_form_error_message'><span>Enter  valid percentage</span>");
			 }
				 $("#save_project").attr("disabled", "disabled");
				return false; 
		 	 }
		 
	    }else{
			$("input#flatPercentage").parent().after("<p class='jquery_form_error_message'><span>Enter value</span>");
			$("#save_project").attr("disabled", "disabled");
	    }
}
function changeOverTime(){
	 $('#flatPercentageDivId').parent().find('p.jquery_form_error_message').remove();
	 $("#flatPercentage").val(0);

}
	  
</script>

<!-- <script type="text/javascript">
    $(function () {
        $("#endDate").bind("click", function () {
            $("#status")[0].selectedIndex = 0;
        });
    });
</script> -->

  <script>
 /* function clearStartDate(){
	$("#startDate").val("");
	}; */ 
function clearProjectEndDate(){
		 $("#endDate").val("");
		 $("#status").empty();
		 $('#status').append('<option >Select Status</option>');
		 $('#status').append('<option >In-Progress</option>');
		 $('#status').append('<option >Not Started</option>');
		 $('#status').append('<option >Completed</option>');
		 $('#status').append('<option >On-Hold</option>');
		 $('#status').append('<option >Dropped</option>');
	}
</script> 