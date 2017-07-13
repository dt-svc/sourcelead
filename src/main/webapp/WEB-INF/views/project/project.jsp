<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link rel="icon" type="resources/image/png"
	href="resources/images/fav.png" />
<link href="resources/css/register-style.css" rel="stylesheet">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="resources/css/register-style.css" rel="stylesheet">
<!-- <script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script> -->
<!-- these files for autocomplete with tagit -->
<link href="resources/css/jquery-tagit.css" rel="stylesheet">
<script src="resources/js/formvalidator.js"></script>
<script src="resources/js/summernote.js"></script>
<link href="resources/css/summernote.css" rel="stylesheet" />
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<style>
.clsDatePicker {
	z-index: 100000 !important;
}
</style>
<script>
function validateTaskName(){

		var Taskname= $("#taskName").val();
		var Taskdesc= $("#taskDescription").val();
		var checkMaxTsRowNum = $("#maxTsRowNum").val();
		for(i = 1; i<checkMaxTsRowNum;i++){
			var oldTaskname = $("#" + i + "taskName").val()
			var oldTaskdesc = $("#" + i + "taskDescription").val()
			if(oldTaskname == Taskname && oldTaskdesc == Taskdesc){
// 				$("#save_task").hide();
				$('input#taskName').removeAttr("record-exist record-exist-errormsg");
				$('#taskNameDIV').find('p.jquery_form_error_message').remove(); 
				$("input#taskName").after("<p class='jquery_form_error_message'>"+ Taskname+ " is Already Existed</span>");
				document.getElementById("taskName").setAttribute('record-exist', 'yes');
				document.getElementById("taskName").setAttribute('record-exist-errorMsg',Taskname+ ' is Already Existed');
				break;
			}else{
				$("#save_task").show();
				$('#taskNameDIV').find('p.jquery_form_error_message').remove(); 
				$('input#taskName').removeAttr("record-exist record-exist-errormsg");
			}
			
		}
}
</script>

<script type="text/javascript">
	function validateProjectSequeneNumber() {
		var ProjectSequeneNumber = document.getElementById("ProjectSequeneNumber").value.trim();
		 //space value  consider this condition 
		if(ProjectSequeneNumber.match(/^[^\s-](.*)*$/) !== null){
		var sourceleadUrl = '${sourceleadUrl}';
		var orgLocId = '${SourceLeadWeb_orgLocations.id}';
		$('#ProjectIdDiv').find('p.jquery_form_error_message').remove();
		if (ProjectSequeneNumber != null && ProjectSequeneNumber != "") {
			$.ajax({
						type : "POST",
						url : sourceleadUrl+ "/validateProjectSequeneNumberById?${_csrf.parameterName}=${_csrf.token}&ProjectSequeneNumber="+ ProjectSequeneNumber+"&orgLocId="+orgLocId,
						success : function(data) {
							if (data == true) {
								$("input#ProjectSequeneNumber").after("<p class='jquery_form_error_message'>"+ ProjectSequeneNumber+ " is Already Existed</span>");
								document.getElementById("ProjectSequeneNumber").setAttribute('record-exist', 'yes');
								document.getElementById("ProjectSequeneNumber").setAttribute('record-exist-errorMsg',ProjectSequeneNumber+ ' is Already Existed');
								$('input#ProjectSequeneNumber').removeAttr( "record-exist record-exist-errorMsg");
							} else {
								$('input#ProjectSequeneNumber').removeAttr("record-exist record-exist-errormsg");
							}
						},
					});
	     	}
		}
	}
	
	
	var fileCounter = 0;
	function addFileUpload(){
		
		$('<div>').attr('style','display:table-cell').append('<input id="file' +  '" name = "project_files' + '" type="file" />' +
	            '<input id="Button' + fileCounter + '" type="button" ' +
	            'value="Remove Document" onclick = "RemoveFileUpload(this)" />').appendTo($('#FileUploadContainer'));

	    		fileCounter++;
	}
	function RemoveFileUpload(div)
	{
		 document.getElementById("FileUploadContainer").removeChild(div.parentNode);
	}
</script>


<!-- Content Area -->
<section class="content-wraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Create Project</h2>
		</div>
		<div class="pull-right brud-crum">
			<a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>
			&raquo;<a href="<c:url value='/projectDashboard'/>">ProjectList</a>
			&raquo;Create Project
	</section>
	<div class="content-area clearfix">
		<div id="loading"></div>
		<div
			class="clearfix form-area form-horizontal toggle-div remove-div mt">
			<form:form id="project_form" name="project_form" method="post"
				enctype="multipart/form-data">
				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">

						<div class="form-group" id="ProjectIdDiv">
							<label class="col-lg-3">Project Id</label>
							<div class="col-lg-9">
								<input type="text" value="" id="ProjectSequeneNumber"
									name="ProjectSequeneNumber"
									data-validation="validate_Space validate_length length2-50"
									data-validation-optional="true" class="form-control"
									onblur="validateProjectSequeneNumber();">
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3">Client</label>
							<div class="col-md-9">
								<select class="form-control select-bg chosen-select"
									name="selectClient" id="addClientId">
									<option value=''>-- Select Client --</option>
									<c:forEach var="option" items="${add_clientList}">
										<option value="${option.orgid}">${option.orgName}</option>
									</c:forEach>
								</select> <span class="hidden">Enter Project Id</span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3"> Date <span class="text-danger">*</span></label>
							<div class="col-md-9">
								<div class="row">
									<div class="col-lg-6 positionRelative" id="StartDateDiv">
										<input type="text" class="form-control" value=""
											id="StartDate" name="StartDate" field-name="Start Date"
											data-validation="required" readonly="readonly"> <span
											class="input-group-addon input-groupAddon"><i
											class="fa fa-calendar"></i></span>
									</div>
									<div class="col-lg-6 positionRelative" id="endDateDiv">
										<input type="text" class="form-control" value="" id="EndDate"
											name="EndDate" field-name="End Date" readonly="readonly">
										<span class="input-group-addon input-groupAddon"><i
											class="fa fa-calendar"></i></span>
									</div>
									<span class="hidden">Enter Project Id</span>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3" for="Project Id">Status<span
								class="text-danger">*</span></label>
							<div class="col-md-9 select-field">
								<select class="form-control" name="Satatus" id="Satatus"
									onchange="getStatus();">
									<option>Select Status</option>
									<option>Not Started</option>
									<option>In-Progress</option>
									<option>Completed</option>
									<option>On-Hold</option>
									<option>Dropped</option>
								</select> <span class="hidden">Select Status</span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3">Timesheet Frequency <span
								class="text-danger">*</span></label>
							<div class="col-md-9" id="chosen-frequency">
								<select class="form-control chosen-select"
									id="timeSheetFrequency" name="timeSheetFrequency"
									onchange="removeFrequencyValidation();"
									field-name="Timesheet Frequency" data-validation="required">
									<option value="">--Timesheet Frequency-</option>
									<option value="Weekly">Weekly</option>
									<option value="Bi-Weekly">Bi-Weekly</option>
									<option value="Semi-Monthly">Semi-Monthly</option>
									<option value="Monthly">Monthly</option>
								</select>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3"></label>
							<div class="col-md-9">
								<div class="checkBox">
									<label> <input type="checkbox" value="Y"
										name="isAllowFreelanceDevelopers"> Allow Freelance
										Developers
									</label>
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3"></label>
							<div class="col-md-9">
								<div class="checkBox">
									<label> <input type="checkbox" value="Y"
										name="isAllowRemoteWork"> Allow Remote Work
									</label>
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3"></label>
							<div class="col-md-9">
								<div class="checkBox">
									<label> <input type="checkbox" value="Y"
										name="isAllowOverTime" id="isAllowOverTime"
										onchange="allowOverTime();"> Allow Over Time
									</label>
								</div>
							</div>
						</div>


						<div class="form-group">
							<label class="col-md-3"></label>
							<div class="col-md-9">
								<div class="input-group">
									<div class="input-group-btn selp-custom-min"
										id="flatPercentageId" name="flatPercentageId">
										<select class="form-control chosen-select">
											<option value="c2c">FLAT</option>
											<option value="%">%</option>
										</select>
									</div>
									<input type="number" min="0" max="100" step="0.5" value="0"
										id="flatPercentage" name="flatPercentage" maxlength="5"
										class="form-control" data-validation-optional="true" onblur="">
								</div>
							</div>
						</div>


					</div>

					<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
						<c:if test="${empty user_List}">
							<p style="font-size: 11px; color: red;" class="text-muted small">
								<i class="fa fa-info-circle"></i>&nbsp;All Admin's in Org will
								have access to Edit/Approve/Reject TimeSheet
							</p>
						</c:if>

						<div class="form-group">
							<label class="col-md-3"> Project Name <span
								class="text-danger">*</span>
							</label>
							<div class="col-md-9">

								<input type="text" class="form-control" name="ProjectName"
									data-validation="required validate_Space validate_length length2-100"
									field-name="Project Name" value=""> <span
									class="hidden">Project Name</span> <span class="hidden">Enter
									Project Id</span>

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
											data-validation="required" onchange="getLevel2Approvers();">
											<option value="">-- Level Manager --</option>
											<c:forEach var="option" items="${user_List}">
												<option value="${option.id},${option.userType}">${option.firstName}
													${option.lastName}</option>
											</c:forEach>
										</select> <span class="btn btn-default formic1 addLevel"
											id="level2Toggle" onclick="getLevel2Approvers();">Add
											Level-II</span>
									</div>
									<span class="hidden">Select Project Manager</span>
								</div>
							</div>
							<div class="form-group level-second" id="level2Div">
								<label class="col-md-3" for="Level-II">Level-II</label>
								<div class="col-md-9">
									<select class="form-control chosen-select" name="level2Manager"
										id="level2Manager">
									</select>
								</div>
							</div>
						</c:if>

						<sec:authorize
							access="hasAnyRole('ROLE_ASSIGN_EMPLOYEE_TO_PROJECT')">
							<div class="form-group">
								<label class="col-md-3">Assign Team &nbsp;<i
									class=" text-info fa fa-info-circle"
									title="Before Assinging Team Project Start Date & Manager is Mandatory"></i></label>

								<div id="assignTeamSavingDiv" style="display: none;"></div>
								<input type="hidden" name="team" value="" />

								<div class="col-md-9">
									<span class="icon-inside"> <i class="fa fa-search-plus"></i>
										<input type="text" value="" placeholder="Select Team"
										id="assignTeamModal" class="form-control"
										data-target="#assignTeam" data-toggle="modal"
										onclick="getTeamMembers();" readonly="readonly">
									</span> <span data-target="#addTeam" data-toggle="modal"> </span> <span
										class="hidden">Select Team</span>
									<!-- Modal content Assign Team-->
									<div role="dialog" id="assignTeam" class="modal fade">
										<div class="modal-dialog modal-lg">
											<div class="modal-content">
												<div class="modal-header">
													<button data-dismiss="modal" class="close" type="button">x</button>
													<h4 class="modal-title">Assign Employees</h4>
												</div>
												<div class="modal-body clearfix">
													<div class="form-horizontal row custom-form hidden">
														<div class="col-xs-12 col-md-12 col-lg-12">
															<div class="input-group">
																<input type="search" class="form-control"
																	placeholder="Name / Email" id="searchByName"> <span
																	class="input-group-addon bt-primary"
																	onclick="getTeamMembers();"><i
																	class="fa fa-search"></i></span>
															</div>
														</div>
													</div>
													<div class="modal-body-custom mt clearfix over"
														id="employeesList"></div>
												</div>
												<div class="modal-footer">
													<button data-dismiss="modal" class="btn btn-primary"
														type="button" onclick="assignEmployee();">Assign</button>
													<button data-dismiss="modal" class="btn btn-default"
														type="button" onclick="assignEmployeeCancel();">Cancel</button>
												</div>
											</div>
										</div>
									</div>
									<!-- Assign Team modal popup ends -->
								</div>
							</div>
						</sec:authorize>

						<div class="form-group">
							<label class="col-md-3">Invoice Frequency <span
								class="text-danger">*</span></label>
							<div class="col-md-9" id="chosen-frequency">
								<select class="form-control chosen-select" id="invoiceFrequency"
									name="invoiceFrequency" onchange="removeFrequencyValidation();"
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
							<label class="col-md-3"></label>
							<div class="col-md-9">
								<div class="checkBox">
									<label> <input type="checkbox" value="Y"
										name="isTimesheetAttachmentRequired"> Timesheet
										Attachment Required
									</label>
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3"></label>
							<div class="col-md-9">
								<div class="checkBox">
									<label> <input type="checkbox" value="Y"
										name="isAllowOffshoreResource"> Allow Offshore
										Resource
									</label>
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3"></label>
							<div class="col-md-9">
								<div class="checkBox">
									<label> <input type="checkbox" value="Y"
										name="isTimeEntryAfterDeadline"> Time Entry after
										Deadline
									</label>
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3"></label>
							<div class="col-md-9">
								<div class="checkBox">
									<label> <input type="checkbox" value="Y"
										name="isAllowBidding"> Allow Bidding
									</label>
								</div>
							</div>
						</div>
					</div>





				</div>
				<!--  div row end -->

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
								<input type="number" value="" id="EstimatedHours" min="1"
									max="99999" name="EstimatedHours" class="form-control"
									data-validation-optional="true" class="form-control" onblur="">
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3">Budget Approver</label>
							<div class="col-md-9" id="chosen-budgetapprover">
								<select class="form-control chosen-select" id="budgetApprover"
									name="budgetApprover" onchange="" field-name="Budget Approver">

									<option value="">-- Budget Approver --</option>
									<c:forEach var="option" items="${user_List}">
										<option value="${option.id},${option.userType}">${option.firstName}
											${option.lastName}</option>
									</c:forEach>

								</select>
							</div>
						</div>


					</div>


					<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">

						<div class="form-group" id="EstimatedBudgetDiv">
							<label class="col-md-3">Estimated Budget</label>
							<div class="col-md-9">
								<div class="input-group">
									<div class="input-group-btn selp-custom-min"
										id="estimatedBudgetId">
										<select class="form-control chosen-select">
											<option value="c2c">INR</option>
											<option value="w2">USD</option>
										</select>
									</div>
									<input type="number" value="" id="EstimatedBudget"
										name="EstimatedBudget" class="form-control"
										data-validation="validate_Space validate_length length2-50"
										data-validation-optional="true" class="form-control" onblur="">
								</div>
							</div>
						</div>
					</div>


					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
						<h5>Description</h5>

						<textarea maxlength="5000" cols="80" rows="10"
							id="ProjectDescription" name="ProjectDescription"></textarea>

					</div>

					<!--  Documents Section -->
					<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
						<h5>Documents</h5>
						<input id="Button1" type="button" value="Add Document"
							onclick="addFileUpload()" /> <br /> <br /> <br />
						<div id="FileUploadContainer">
							<!--FileUpload Controls will be added here -->
						</div>
						<br />
						<!--<input type="file" name="project_files" id="project_files" multiple="multiple"/>-->
					</div>

				</div>

				<!--  div row end -->


				<div class="form-footer clearfix row">
					<div class="col-lg-12">
						<div class="pull-right">
							<input type="button" value="Save" id="save_project"
								onclick="saveProject();flushSaveProject()"
								class="btn btn-primary"> <input type="button"
								value="Cancel"
								onClick="javascript:window.location.href = '<c:url value='/projectDashboard'/>'"
								class="btn btn-default ml">
						</div>
					</div>
				</div>



			</form:form>
		</div>
	</div>
</section>
<!-- Content Area Ends-->
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
	}
	
}
</script>
<script>
function getStatus(){
	$('#endDateDiv').find('p.jquery_form_error_message').remove(); 
	var status = $("#Satatus").val();
	if(status != null && status != "" && status != "undefined" && status == "Completed"){
		 $('#EndDate').removeAttr('data-validation-optional');
		 $('#EndDate').attr("data-validation","required"); 
	 }else{
		 $('#EndDate').removeAttr('data-validation');
		 $('#EndDate').attr("data-validation-optional","true"); 
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


<!-- =============================== AddTask Divs  ============================ -->
<div id="divTaskTd1" style="display: none; float: left;">
	<input type="hidden" id="REPLACEROWIDtaskName"
		name="REPLACEROWIDtaskName" value="TASKNAMEVALUE" /><span
		id="REPLACEROWIDtaskNameValue">TASKNAMEVALUE</span>
</div>

<div id="divTaskTd2" style="display: none; float: left;">
	<input type="hidden" id="REPLACEROWIDtaskBillableType"
		name="REPLACEROWIDtaskBillableType" value="TASKBILLABLETYPEVALUE" /><span
		id="REPLACEROWIDtaskBillableTypeValue">TASKBILLABLETYPEVALUE</span>
</div>

<div id="divTaskTd3" style="display: none; float: left;">
	<input type="hidden" id="REPLACEROWIDtaskDescription"
		name="REPLACEROWIDtaskDescription" value="TASKDESCRIPTIONVALUE" /><span
		id="REPLACEROWIDtaskDescriptionValue">TASKDESCRIPTIONVALUE</span>
</div>

<div id="divTaskTd4" style="display: none; float: left;">
	<span><a title="Edit" href="javascript:void(0)"
		onclick="editTask(REPLACEROWID)"><i class="fa fa-edit"></i></a></span>&nbsp;&nbsp;&nbsp;
	<span><a title="Remove" href="javascript:void(0)"
		onclick="removeRow(REPLACEROWID)"><i class="fa fa-close"></i></a></span>
</div>
<!-- =============================== AddTask Divs  ============================ -->
<!-- Add Task Script start -->
<script type="text/javascript">
	function addTasks() {
		$('#taskNameDIV').find('p.jquery_form_error_message').remove();
		$('#descriptionDiv').find('p.jquery_form_error_message').remove();
		if ($('#taskName').val() == '' || $("#taskDescription").val().length > 200  || $("#taskName").val().length > 100 || $("#taskName").val().length < 2) {
			if ($('#taskName').val() == '') {
				$("input#taskName") .after( "<p class='jquery_form_error_message'>Task Name is required</span>");
			}
			if(($('#taskName').val() != '' && $("#taskName").val().length < 2 )|| $("#taskName").val().length > 100){
				$("input#taskName") .after( "<p class='jquery_form_error_message'>The values Entered must be between 2-100 characters</span>");
			}
			if (($("#taskDescription").val() != '' && $("#taskDescription").val().length < 2 )  || $("#taskDescription").val().length > 200) {
				$("textarea#taskDescription") .after( "<p class='jquery_form_error_message'>The values Entered must be between2-5000 characters</span>");
			}
			return true;
		} 
		
		var tbl = document.getElementById("projectTaskTbl");
		var maxRowNum = parseInt($("input[name = 'maxTsRowNum']").val());
		$("input[name = 'maxTsRowNum']").val(maxRowNum + 1);
		$("#addTaskCount").html(maxRowNum);
		var row = tbl.insertRow(maxRowNum);
		row.setAttribute("id", "divTsRow" + maxRowNum);

		var taskName = $('#taskName').val();
		var taskDescription = $('#taskDescription').val();
		var taskBillableType = $('#taskBillableType').val();

		var cell0 = row.insertCell(0);
		var divTsTd1Str = document.getElementById("divTaskTd1").innerHTML;
		divTsTd1Str = divTsTd1Str.replace(/REPLACEROWID/g, (maxRowNum));
		divTsTd1Str = divTsTd1Str.replace(/TASKNAMEVALUE/g, (taskName));
		cell0.innerHTML = divTsTd1Str;
		
		var cell1 = row.insertCell(1);
		var divTsTd2Str = document.getElementById("divTaskTd2").innerHTML;
		divTsTd2Str = divTsTd2Str.replace(/REPLACEROWID/g, (maxRowNum));
		divTsTd2Str = divTsTd2Str.replace(/TASKBILLABLETYPEVALUE/g,
				(taskBillableType));
		cell1.innerHTML = divTsTd2Str;

		var cell2 = row.insertCell(2);
		var divTsTd3Str = document.getElementById("divTaskTd3").innerHTML;
		divTsTd3Str = divTsTd3Str.replace(/REPLACEROWID/g, (maxRowNum));
		divTsTd3Str = divTsTd3Str.replace(/TASKDESCRIPTIONVALUE/g,
				(taskDescription));
		cell2.innerHTML = divTsTd3Str;

		var cell3 = row.insertCell(3);
		var divTsTd4Str = document.getElementById("divTaskTd4").innerHTML;
		divTsTd4Str = divTsTd4Str.replace(/REPLACEROWID/g, (maxRowNum));
		cell3.innerHTML = divTsTd4Str;

		$("#taskName").val('');
		$("#taskDescription").val('');
		$("#taskBillableType").val('Billable');
		$('#taskBillableType').selectpicker('refresh');
		addtasksCountafterAddingTask();
	}
</script>
<script type="text/javascript">
	function removeRow(rowId) {
		addtasksCountafterRemoving()
		document.getElementById('divTsRow' + rowId).style.display = 'none';
		var r = document.getElementById('projectTaskTbl').rows[rowId];
		var count = 2 * (r.cells.length);
		for (i = 0; i <= count; i++) {
			r.deleteCell(0);
		}
	}
</script>
<script type="text/javascript">
	function editTask(rowId) {
		$('#addTask').find('p.jquery_form_error_message').remove();	 	
		var updateTaskName = $("#" + rowId + "taskName").val();
		var updateTaskDescription = $("#" + rowId + "taskDescription").val();
		var updateTaskBillableType = $("#" + rowId + "taskBillableType").val();
		$("#taskName").val('');
		$("#taskDescription").val('');
		$("#taskBillableType").val('Billable');
		$('#taskBillableType').selectpicker('refresh');
		$("#addTaskText").html('Update Task');
		$("#taskName").val(updateTaskName);
		$("#taskDescription").val(updateTaskDescription);
		$('#taskBillableType').val(updateTaskBillableType);
		$('#taskBillableType').selectpicker('refresh');
		$("#taskId").val(rowId);
		$("#save_task").removeAttr('onclick');
		$("#save_task").attr('onclick', 'updateTask(' + rowId + ');');
	}
</script>

<script type="text/javascript">
	function updateTask(rowId) {
		$("#" + rowId + "taskName").val($("#taskName").val());
		$("#" + rowId + "taskDescription").val($("#taskDescription").val());
		$("#" + rowId + "taskBillableType").val($("#taskBillableType").val());
		
		$("#" + rowId + "taskNameValue").html($("#taskName").val());
		$("#" + rowId + "taskDescriptionValue").html($("#taskDescription").val());
		$("#" + rowId + "taskBillableTypeValue").html($("#taskBillableType").val());
		
		$("#taskName").val('');
		$("#taskDescription").val('');
		$("#taskBillableType").val('Billable');
		$('#taskBillableType').selectpicker('refresh');
		$("#addTaskText").html('Add Task');
		$("#save_task").removeAttr('onclick');
		$("#save_task").attr('onclick', 'addTasks();');

	}
</script>
<script>
	$(document).ready(function() {
		$("#maxTsRowNum").val('6');
		addSourceleadTasks();
		document.getElementById("project_form").reset();
		$('#flatPercentageId').attr('style','visibility:hidden');
		$('#flatPercentage').attr('style','visibility:hidden');
		$('#ProjectDescription').summernote({height:300});
	});
</script>

<script type="text/javascript">
	function addSourceleadTasks() {

		var checkMaxTsRowNum = $("#maxTsRowNum").val();
		if (checkMaxTsRowNum == 6) {
			var tbl = document.getElementById("projectTaskTbl");
			var maxRowNum = parseInt($("input[name = 'maxTsRowNum']").val());
			$("input[name = 'maxTsRowNum']").val(maxRowNum + 1);
			$("#addTaskCount").html(maxRowNum);

			var sourceleadUrl = '${sourceleadUrl}';
			$
					.ajax({
						type : "GET",
						url : sourceleadUrl + "/sourceLeadTasks",
						success : function(data) {

							var json = JSON.parse(data);
							var output = "";
							for (var i = 0; i < json.length; i++) {
								$('taskButton[onclick]').removeAttr('onclick');
								var opt = json[i];
								var row = tbl.insertRow(opt.Id);
								row.setAttribute("id", "divTsRow" + opt.Id);
								var taskName = opt.taskName;
								var taskDescription = opt.taskDescription;
								var cell0 = row.insertCell(0);
								var divTsTd1Str = document.getElementById("divTaskTd1").innerHTML;
								divTsTd1Str = divTsTd1Str.replace(/REPLACEROWID/g, (opt.Id));
								divTsTd1Str = divTsTd1Str.replace(/TASKNAMEVALUE/g, (opt.taskName));
								cell0.innerHTML = divTsTd1Str;
								
								var cell1 = row.insertCell(1);
								var divTsTd2Str = document.getElementById("divTaskTd2").innerHTML;
								divTsTd2Str = divTsTd2Str.replace(/REPLACEROWID/g, (opt.Id));
								divTsTd2Str = divTsTd2Str.replace(/TASKBILLABLETYPEVALUE/g,(opt.billableType));
								cell1.innerHTML = divTsTd2Str;
								
								var cell2 = row.insertCell(2);
								var divTsTd3Str = document.getElementById("divTaskTd3").innerHTML;
								divTsTd3Str = divTsTd3Str.replace(/REPLACEROWID/g, (opt.Id));
								divTsTd3Str = divTsTd3Str.replace(/TASKDESCRIPTIONVALUE/g,(opt.description));
								cell2.innerHTML = divTsTd3Str;

								var cell3 = row.insertCell(3);
								var divTsTd4Str = document.getElementById("divTaskTd4").innerHTML;
								divTsTd4Str = divTsTd4Str.replace(/REPLACEROWID/g, (opt.Id));
								cell3.innerHTML = divTsTd4Str;

							}

						}
					});
		}

	}
</script>


<script type="text/javascript">
	function addtasksCountafterRemoving() {

		var tasksCount = $("#maxTsRowNum").val();
		var count = tasksCount;
		var addtaskcount = null;
		for (i = 0; i < count; i++) {

			if ($('#divTsRow' + i).filter(function() {
				return $(this).css('display') !== 'none';
			}).length) {
				addtaskcount++
			}
		}
		$("#addTaskCount").html(addtaskcount - 2);

	}
</script>

<script type="text/javascript">
	function addtasksCountafterAddingTask() {

		var tasksCount = $("#maxTsRowNum").val();
		var count = tasksCount;
		var addtaskcount = null;
		for (i = 0; i < count; i++) {

			if ($('#divTsRow' + i).filter(function() {
				return $(this).css('display') !== 'none';
			}).length) {
				addtaskcount++
			}
		}
		$("#addTaskCount").html(addtaskcount - 1);

	}
</script>

<!-- Add Task Script Ends -->
<script type="text/javascript">
function flushProjectTasks(){
	$("#taskName").val('');
	$("#taskDescription").val('');
	$("#addTaskText").html('Add Task');
	$("#save_task").removeAttr('onclick');
	$("#save_task").attr('onclick', 'addTasks();');
	$("input#taskName").removeClass("error");
	 $('input#taskName').removeAttr( "record-exist record-exist-errormsg");
	$('#addTask').find('p.jquery_form_error_message').remove();	
}
</script>

<script type="text/javascript">
	function saveProject() {
		validateProjectSequeneNumber();
		var validationSettings = {
			errorMessagePosition : 'element',
			borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd'
		};
		
		var projDesc = $('#ProjectDescription').summernote('code');
		
		$('#addTask').find('p.jquery_form_error_message').remove();
		if ($('#project_form').validate(false, validationSettings)) {
			var frm = $('#project_form').serializeFormJSON();
			/* var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, ""); */
			frm.ProjectDescription = projDesc;
			var jsonData = JSON.stringify(frm);
			
		    var form = $('#project_form')[0];
		    var formData = new FormData(form);
		  	formData.append("json",jsonData)

			//It will escape all the special characters
		//	var jsonData = encodeURIComponent(con);
			
			var sourceleadUrl = '${sourceleadUrl}';
			$("#save_project").attr("disabled", "disabled");
			$("#save_project").addClass('button').val('Processing..');
     		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');


			$.ajax({
						 type : "POST",
						 data: formData,
		  	    	     contentType : 'application/json; charset=utf-8',
		  		         processData: false,
		  		         contentType:false, 
		  	    	     dataType : 'json',
						url : sourceleadUrl+ "/storeProject?${_csrf.parameterName}=${_csrf.token}",
						success : function(result) {
							if (result == 'projectDashboard') {
								location.replace(sourceleadUrl
										+ "/projectDashboard");
							}

						},
					});
			return true;
		} else {
			return false;
		}
	}
	$('body').on('blur', '#project_form', function() {
		$("#project_form").showHelpOnFocus();
		$("#project_form").validateOnBlur(false, validationSettings);
	});
</script>
<script type="text/javascript">
	function flushSaveProject() {
		$("#save_project").removeAttr("disabled");
	}
</script>


<!-- this script is for Get the TeamMembers functionality Start -->
<script type="text/javascript">
	function getTeamMembers() {
		var level1Manager = $("#level1Manager").val(); 
		var level1ManagerArray = level1Manager.split(',');
		level1Manager = level1ManagerArray[0];
		
		var level2Manager = $("#level2Manager").val();
		var startDate     =    $("#StartDate").val();
		var searchByName = $("#searchByName").val();
		var sourceleadUrl = '${sourceleadUrl}';
		if(startDate != null  && startDate != '' && level1Manager != null && level1Manager != '' ){
			
			if(level2Manager != null){
				level2Manager = level2Manager;
			}else{
				level2Manager = "";
			}
			if(level1Manager != null){
				level1Manager = level1Manager;
			}else{
				level1Manager = "";
			}
			
			var orgLocId = '${SourceLeadWeb_orgLocations.id}';
			
			  $("#assignTeamModal").attr('data-target','#assignTeam');
			$.ajax({
						type : "POST",
						url : sourceleadUrl + "/getEmployeesListByExcludingManagers?${_csrf.parameterName}=${_csrf.token}&level1Manager="+level1Manager+"&level2Manager="+level2Manager+"&searchByName="+searchByName+"&orgLocId="+orgLocId,
						success : function(data) {
							var json = JSON.parse(data);
							var output = "";
							output = output+' <div class="wrap_employeeTable">';
							output = output + ' <table cols="0" id="assignEmployeePagination" class="clearfix table-bordered">';
							output = output + ' <thead>';
							output = output + ' <tr>';
				            output = output+' <th><input type="checkbox" name="emp" id="projectChekId" onclick="totalCheck();"></th>';
							output = output + ' <th>Name</th>';
							output = output + ' <th>Email</th>';
							output = output + ' <th>Role</th>';
							output = output + ' <th>Start Date</th>';
							output = output + ' <th>End Date</th>';
							output = output + ' </tr>';
							output = output + ' </thead>';
							output = output + ' <tbody>';
							for (var i = 0; i < json.length; i++) {
								var projectStartDate = $('#StartDate').val();
								var projectEndDate = $('#EndDate').val();
								if(projectEndDate == null ||  projectEndDate == "" || projectEndDate == "undefined"){
									projectEndDate = "";
								}
								var opt = json[i];
								var role = '';
								if (opt.userType != null
										&& opt.userType != ''
										&& opt.userType != 'undefined') {
									role = opt.userType;
								} else {
									role = 'Employee';
								}
	
								var count = 1;
								$(".myCheckbox").each(
												function() {
													var localId = this.id;
													
													if ($(this).is(':checked')) {
														var startDate = $("#startDate"+ localId).val();
														var endDate = $("#endDate"+ localId).val();
														result = result+ "<input type='hidden'  name='employeeId"+count+"' id='employeeId"+count+"' value='"+localId+"' />";
														result = result+ "<input type='hidden'  name='employeestartDate"+count+"' id='employeestartDate"+count+"' value='"+startDate+"' />";
														result = result+ "<input type='hidden'  name='employeeendDate"+count+"' id='employeeendDate"+count+"' value='"+endDate+"' />";
														count++;
													}
												});
	
								if (opt.user.userType != 'Admin') {
	
									output = output + "<tr>";
									output = output+ "<td><input class='myCheckbox' id="+opt.id+" type='checkbox' name='team"+i+"' value="+opt.id+"></td>";
									if(opt.user.lastName != null && opt.user.lastName !='Undefined' && opt.user.lastName != ''){
										output = output + "<td><span class='tbldata-dotdot-sm' title='"+opt.user.firstName+"&nbsp;"+opt.user.lastName+"'>" + opt.user.firstName+ "&nbsp;" + opt.user.lastName+ "</span></td>";	
									}else{
										output = output + "<td><span class='tbldata-dotdot-sm' title='"+opt.user.firstName+"'>" + opt.user.firstName+ "</span></td>";
									}
									
									output = output + "<td><span class='tbldata-dotdot-vsm' title='"+opt.user.email+"'>" + opt.user.email+ "</span></td>";
									output = output + "<td><span class='tbldata-dotdot-vsm' title='"+role+"'>" + role + "</span></td>";
									output = output+ "<td><span class='' title='"+projectStartDate+"'><div class='input-group tblminwidth-150'> <span class='input-group-addon'><i class='fa fa-calendar'></i></span>";
	
									output = output+ "<input  id='startDate"+ opt.id+ "' type='text' name='Date' value='"+ projectStartDate+ "' class='form-control clsDatePicker' readonly='readonly'>";
									output = output + "</div></span></td>";
									output = output+ "<td><div class='input-group tblminwidth-150'> <span class='input-group-addon'><i class='fa fa-calendar'></i></span>";
									output = output + "<input id='endDate" + opt.id + "'  value='"+projectEndDate+"' type='text' name='Date' value='' class='form-control clsDatePicker' readonly='readonly'>";
									output = output + "</div></td>";
									output = output + "</tr>";
								}
							}
							output = output + ' </tbody>';
							output = output + ' </table>';
							output = output+' </div>';
							$("#employeesList").empty();
							$("#employeesList").append(output);

							assignEmployeespagination();	
						  //StartDate And EndDate Pickers
					       if(json.length > 0){
						    	for (var i = 0; i < json.length; i++) {
									var opt = json[i];
									var projectStartDate = $("#StartDate").val();
									var projectEndDate = $("#EndDate").val();
									
									var datepickerStartDate = toDate("#StartDate");
									datepickerStartDate.setDate(datepickerStartDate.getDate() + 1);
									
									$('#startDate'+opt.id).datepicker({
										dateFormat : 'dd-mm-yy',
										changeMonth: true,
										changeYear: true, 
										minDate:projectStartDate,
										maxDate:projectEndDate,
										onSelect : function(selected) {
											var actualId = $(this).attr('id').split('startDate')[1];
											var dt = toDate('#startDate'+actualId);
											//var id = $(this).attr('id');
											dt.setDate(dt.getDate() + 1);
											$("#endDate"+actualId).datepicker("option", "minDate", dt);
										}
									});
		
									$('#endDate'+opt.id).datepicker({
										dateFormat : 'dd-mm-yy',
										changeMonth: true,
										changeYear: true, 
										maxDate:projectEndDate,
										minDate: datepickerStartDate,
										onSelect : function(selected) {
											var actualId = $(this).attr('id').split('endDate')[1];
											var dt = toDate('#endDate'+actualId);
											//var id = $(this).attr('id');
											dt.setDate(dt.getDate() - 1);
											$("#startDate"+actualId).datepicker("option", "maxDate", dt);
										}
									});
								}
					      }
							
							
						},
						error : function(e) {
						}
					});
		}else{
			
			$("#assignTeamModal").removeAttr('data-target');
			
		}
		// $('#add_employee').attr('data-dismiss','modal');
		$('#firstName').val('');
		$('#lastName').val('');
		$('#employeeEmail').val('');
	}
</script>
<!-- this script is for Get the TeamMembers functionality Ends -->

<script>
function totalCheck() {
	var checked = document.getElementById("projectChekId").checked  
	
    if(checked) {
        $('.myCheckbox').each(function() {
            this.checked = true;   
        });
    }else{
        $('.myCheckbox').each(function() {
            this.checked = false;      
        });         
    } 
}
</script>

<script>
function assignEmployeespagination(){  
    $('#assignEmployeePagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10000, 15, 20, -1], [10000, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": true,
    	"aaSorting": [[1,'desc']],
    	"aoColumnDefs": [{
	           'bSortable': false,
	           'aTargets': 0
			}],
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
		
    } );
}
</script>

<!-- this script is for Assign Employee functionality Start -->
<script>
	function assignEmployee() {

		//var table = $('#assignEmployeePagination').DataTable();

		$('#assignEmployeePagination').DataTable().search('').draw();
		var result = "";
		var count = 1;
		$(".myCheckbox").each(function() {
							var localId = this.id;
							if ($(this).is(':checked')) {
								$(this).removeAttr('emp-assigned');
								$(this).attr('emp-assigned','true');
								var startDate = $("#startDate" + localId).val();
								var endDate = $("#endDate" + localId).val();
								result = result
										+ "<input type='hidden'  name='employeeId"+count+"' id='employeeId"+count+"' value='"+localId+"' />";
								result = result
										+ "<input type='hidden'  name='employeestartDate"+count+"' id='employeestartDate"+count+"' value='"+startDate+"' />";
								result = result
										+ "<input type='hidden'  name='employeeendDate"+count+"' id='employeeendDate"+count+"' value='"+endDate+"' />";
								count++;
							}
						});
		result = result
				+ "<input type='hidden' name='employeeMaxCount' id='employeeMaxCount' value='"+count+"'/>";
		$("#assignTeamSavingDiv").empty();
		$("#assignTeamSavingDiv").append(result);
	}
</script>
<!-- this script is for Assign Employee functionality Ends -->

<script type="text/javascript">
	function flushProject() {
		$("#save_client").removeAttr("disabled");
		$('#orgName').val('');
		$('#companyWebsite').val('');
		$(".error").removeClass("error");
		$('#newClient').find('p.jquery_form_error_message').remove();
	}
</script>
<script>
function assignEmployeeCancel(){
	$(".myCheckbox").each(function() {
		var empAssigned = $(this).attr('emp-assigned');
		if(empAssigned != null && empAssigned != 'undefined' && empAssigned != '' && empAssigned == 'true'){
		}
		else{
			$(this).prop('checked', false);
		}
		
    });	
}
</script>


<script>
	$(function() {

		$('#StartDate').datepicker({
			dateFormat : 'dd-mm-yy',
			changeMonth: true,
			changeYear: true, 
			onSelect : function(selected) {
				//var dt = new Date(selected);
				var dt = toDate("#StartDate");
				dt.setDate(dt.getDate() + 1);
				$("#EndDate").datepicker("option", "minDate", dt);
				addValidClass();
			}
		});

		$('#EndDate').datepicker({
			dateFormat : 'dd-mm-yy',
			changeMonth: true,
			changeYear: true, 
			onSelect : function(selected) {
				//var dt = new Date(selected);
				var dt = toDate("#EndDate");
				dt.setDate(dt.getDate() - 1);
				$("#StartDate").datepicker("option", "maxDate", dt);
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
                var d2 = new Date(proEndDateTP[2], proEndDateTP[1] - 1, proEndDateTP[0]);
                
				if(d1 > d2){
					$("#Satatus").empty();
					$('#Satatus').append('<option >Completed</option>');
				}else{
					$("#Satatus").empty();
					$('#Satatus').append('<option >Started</option>');
					$('#Satatus').append('<option >In-Progress</option>');
					$('#Satatus').append('<option >Completed</option>');
					$('#Satatus').append('<option >On-Hold</option>');
				}
			}
		});

	});
</script>
<script type="text/javascript"> 
	function addValidClass(){
		 var StartDate = document.getElementById("StartDate").value;
		 if(StartDate){
			 $('#StartDateDiv').find('p.jquery_form_error_message').remove(); 
		 }
		 
		 var EndDate = document.getElementById("EndDate").value;
		 if(EndDate){
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
function getLevel2Approvers(){
	$('#chosen-level1Manager').find('p.jquery_form_error_message').remove();
	var currentApproverId = document.getElementById("level1Manager").value;
	var currentUser = currentApproverId.split(',');
	var currentUserId = currentUser[0];
	var currentUserType = currentUser[1];
	var count = 1;
	$(".myCheckbox").each(
					function() {
						var localId = this.id;
						document.getElementById(localId).checked = false; 
	});
	$(".myCheckbox").val('');
	
	$("#level2Manager").empty();    
	$('#level2Manager').val('').trigger('chosen:updated');
	if(currentUserType != null && currentUserType != '' && currentUserType !='undefined' && currentUserType != 'Admin'){
	    $("#level2Toggle").show();
		getChildLevel1Approvers(currentUserId);
	}else if(currentUserType != null && currentUserType != '' && currentUserType !='undefined' && currentUserType == 'Admin'){
	    $("#level2Toggle").hide();   
	    $("#level2Div").removeClass("level-second-active");
	}
	
	
} 
</script>
<script type="text/javascript">
function getChildLevel1Approvers(currentApproverId){
	 var sourceleadUrl ='${sourceleadUrl}';
	 if(currentApproverId != null && currentApproverId != ''  && currentApproverId != 'undefined'){
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getApproversByUserId?${_csrf.parameterName}=${_csrf.token}&currentApproverId="+currentApproverId,
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	var output = "";
		        	output = output + "<option value=''>-Select Manager-</option>"; 
		        	for (var i=0; i<json.length; i++)
					{
			        		var opt = json[i];
			        		output = output + "<option value='" +opt.id+ "'>"+opt.firstName+" "+opt.lastName+"</option>";
					}
		        	$("#level2Manager").empty();
			        $("#level2Manager").append(output);
			        $('#level2Manager').trigger("chosen:updated");
		        },
		        error: function(e){
		            
		        }
		    });
	 }else{
		 getApproversLevel2();
		
	 }
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
<script>
function toDate(selector) {
    var from = $(selector).val().split("-");
    return new Date(from[2], from[1] - 1, from[0]);
}
</script>
<!-- script for dateformat in datepicker End -->