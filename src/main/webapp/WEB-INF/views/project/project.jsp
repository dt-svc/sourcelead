<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link rel="icon" type="resources/image/png"
	href="resources/images/fav.png" />
<link
	href="https://fonts.googleapis.com/css?family=Roboto:400,300italic,300,400italic,500,700,900"
	rel="stylesheet" type="text/css">
<link rel="stylesheet"
	href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css">
<link rel="stylesheet" href="resources/css/chosen.min.css">
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<link href="resources/css/register-style.css" rel="stylesheet">
<link href="resources/css/jquery-ui.css" rel="stylesheet">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="resources/css/register-style.css" rel="stylesheet">
<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>
<!-- these files for autocomplete with tagit -->
<link href="resources/css/jquery-tagit.css" rel="stylesheet">

<style>
.clsDatePicker {
	z-index: 100000 !important;
}
</style>


<script type="text/javascript">
	function validateProjectSequeneNumber() {
		var ProjectSequeneNumber = document.getElementById("ProjectSequeneNumber").value;
		 //space value  consider this condition 
		if(ProjectSequeneNumber.match(/^[^\s-](.*)*$/) !== null){
		var sourceleadUrl = '${sourceleadUrl}';
		$('#ProjectIdDiv').find('p.jquery_form_error_message').remove();
		if (ProjectSequeneNumber != null && ProjectSequeneNumber != "") {
			$.ajax({
						type : "POST",
						url : sourceleadUrl+ "/validateProjectSequeneNumberById?${_csrf.parameterName}=${_csrf.token}&ProjectSequeneNumber="+ ProjectSequeneNumber,
						success : function(data) {
							if (data == true) {
								$("input#ProjectSequeneNumber").after("<p class='jquery_form_error_message'>"+ ProjectSequeneNumber+ " is Already Existed</span>");
								document.getElementById("ProjectSequeneNumber").setAttribute('record-exist', 'yes');
								document.getElementById("ProjectSequeneNumber").setAttribute('record-exist-errorMsg',ProjectSequeneNumber+ ' is Already Existed');
								$('input#ProjectSequeneNumber').removeAttr( "record-exist record-exist-errorMsg");
							} else {
								$('input#ProjectSequeneNumber').removeAttr("record-exist record-exist-errormsg");
								$('#ProjectIdDiv').find('p.jquery_form_error_message').remove();
							}
						},
					});
	     	}
		}
	}
</script>


<!-- Content Area -->
<section class="content-wraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Create Project</h2>
		</div>
		<div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo;<a href="<c:url value='/projectDashboard'/>">ProjectList</a> &raquo;Create Project
	</section>
	<div class="content-area clearfix">
	  <div id="loading"></div>
		<div class="clearfix form-area form-horizontal toggle-div remove-div mt">
			<form:form id="project_form" name="project_form" method="post">
				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">

						<div class="form-group" id="ProjectIdDiv">
							<label class="col-lg-3">Project Id</label>
							<div class="col-lg-9">
								<input type="text" value="" id="ProjectSequeneNumber" name="ProjectSequeneNumber" data-validation="validate_Space validate_length length2-50"
									data-validation-optional="true" class="form-control" onblur="validateProjectSequeneNumber();">
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3"> Project Name <span
								class="text-danger">*</span>
							</label>
							<div class="col-md-9">
								<input type="text" class="form-control" name="ProjectName" data-validation="required validate_Space validate_length length2-100" field-name="Project Name" value=""> <span
									class="hidden">Project Name</span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3"> Project Description </label>
							<div class="col-md-9">
								<textarea class="form-control" rows="3" name="ProjectDescription" data-validation="validate_length length10-5000"
									data-validation-optional="true"></textarea>
								<span class="hidden">Project Description</span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3"> Date <span
								class="text-danger">*</span></label>
							<div class="col-md-9">
								<div class="row">
									<div class="col-lg-6 positionRelative" id="StartDateDiv">
										<input type="text" class="form-control" value="" id="StartDate" name="StartDate" field-name="Start Date" data-validation="required" readonly="readonly"> <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span>
									</div>
									<div class="col-lg-6 positionRelative">
										<input type="text" class="form-control" value="" id="EndDate" name="EndDate" readonly="readonly"> <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span>
									</div>
									<span class="hidden">Enter Project Id</span>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3">Tasks</label>
							<div class="col-md-9">
								<div class="input-group">
									<div class="input-group-btn">
										<button class="btn btn-default" type="button">
											<span class="badge" id="addTaskCount">0
										</button>
										<button class="btn btn-primary" id="david" type="button" onclick="addSourceleadTasks();" data-toggle="modal" data-target="#addTask">
											<i class="fa fa-plus" title="Add Tasks"></i> &nbsp;&nbsp;View / Add Tasks </button>
									</div>
								</div>
								<!-- Modal -->
								<div class="modal fade" id="addTask" role="dialog">
									<div class="modal-dialog modal-lg">

										<!-- Modal content-->
										<div class="modal-content">
											<div class="modal-header">
												<button type="button" class="close" data-dismiss="modal">&times;</button>
												<h4 class="modal-title">Add Tasks</h4>
											</div>
											<div class="modal-body clearfix">
											<div class="modal-body-custom custom-table tbl-cumstom-data">
												<table class="table table-bordered table-striped  clearfix" id="projectTaskTbl" cols="0">
													<tr id="divTsRow0">
														<th><input type="hidden" id="maxTsRowNum" name="maxTsRowNum" value="1" />Task Name</th>
														<th>Description</th>
														<th class="tblminwidth-75">Action</th>
													</tr>
												  </table>
											   </div>
											   <div class="form-horizontal row custom-form">
													<div class="col-xs-12 col-lg-7">
														<div class="form-group" id="taskNameDIV">
															<label class="col-md-3">Task Name</label>
															<div class="col-md-9">
																<input type="text" value="" id="taskName" name="taskName" placeholder="Enter Task Name" class="form-control">
															</div>
														</div>
														<div class="form-group">
															<label class="col-md-3">Description</label>
															<div class="col-md-9">
																<textarea class="form-control" placeholder="Enter Task Description" id="taskDescription" name="taskDescription" rows="2"></textarea>
															</div>
														</div>
													</div>
													<div class="col-xs-12 col-lg-5">
														<a href="javascript:void(0)" class="btn btn-primary" id="save_task" onclick="addTasks();" data-target="addTask"> <i class="fa fa-plus"></i><span id="addTaskText"> Add Task</span></a>
													</div>
												</div>
											</div>
											<div class="modal-footer">
												<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
											</div>
										</div>
									</div>
								</div>
								<!-- Modal contente Ends-->

							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3"></label>
							<div class="col-md-9">
								<div class="checkbox">
									<label> <input type="checkbox" value="Y" checked="checked" name="isMailToProjectManager"> 
									Mail to Project Manager
									</label>
								</div>
								<span class="hidden">Select Check Box</span>
							</div>
						</div>
					</div>
					<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
					 <c:if test="${empty user_List}">
					<p style="font-size: 11px; color: red;" class="text-muted small"><i class="fa fa-info-circle"></i>&nbsp;All Admin's in Org will have access to Edit/Approve/Reject TimeSheet</p>
					</c:if>
						
					 <c:if test="${not empty user_List}">
						<div class="form-group">
							<label class="col-md-3" for="Project Manager">Manager <span
								class="text-danger">*</span></label>
							<div class="col-md-9">
								<div class="form-icon-min">
									<select class="form-control chosen-select" name="level1Manager" id="level1Manager" field-name="Select Manager" data-validation="required" onchange="getLevel2Approvers();">
										<option value="">-- Select Manager --</option>
										<c:forEach var="option" items="${user_List}">
											<option value="${option.id}">${option.firstName}
												${option.lastName}</option>
										</c:forEach>
									</select> <span class="btn btn-default formic1 addLevel" onclick="getLevel2Approvers();">Add
										Level-II</span>
								</div>
								<span class="hidden">Select Project Manager</span>
							</div>
						</div>
						<div class="form-group level-second">
							<label class="col-md-3" for="Level-II">Level-II</label>
							<div class="col-md-9">
								<select class="form-control chosen-select" name="level2Manager" id="level2Manager">
									<option value=''>-- Select Manager --</option>
									<c:forEach var="option" items="${user_List}">
										<option value="${option.id}">${option.firstName}
											${option.lastName}</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</c:if>
						<div class="form-group">
							<label class="col-md-3">Client</label>
							<div class="col-md-9">
								<div class="form-icon">
									<select class="form-control select-bg chosen-select" name="selectClient" id="addClientId">
										<option value=''>-- Select Client --</option>
										<c:forEach var="option" items="${add_clientList}">
											<option value="${option.orgid}">${option.orgName}</option>
										</c:forEach>
									</select> 
								</div>
								<span class="hidden">Enter Project Id</span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3">Assign Team</label>

							<div id="assignTeamSavingDiv" style="display: none;"></div>
							<input type="hidden" name="team" value="" />

							<div class="col-md-9">
								<div class="form-icon">
									<span class="icon-inside"> <i class="fa fa-search-plus"></i>
										<input type="text" value="Select Team" id ="assignTeamModal"class="form-control" data-target="#assignTeam" data-toggle="modal" onclick="getTeamMembers();flushEmployee();" readonly="readonly">
									</span> <span data-target="#addTeam" data-toggle="modal"> <i class="fa fa-plus btn btn-default formic1" onclick="flushEmployee();"></i>
									</span>
								</div>
								<span class="hidden">Select Team</span>
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
															<input type="search" class="form-control"  placeholder="Name / Email" id="searchByName">
															<span class="input-group-addon bt-primary" onclick="getTeamMembers();"><i class="fa fa-search"></i></span>
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
													type="button" onclick="assignEmployee();">Cancel</button>
											</div>
										</div>
									</div>
								</div>
								<!-- Assign Team modal popup ends -->
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3" for="Project Id">Status</label>
							<div class="col-md-9">
								<select class="form-control select-bg" name="Satatus" id="Satatus">
									<option>Started</option>
									<option>In-Progress</option>
									<option>Completed</option>
									<option>On-Hold</option>
								</select> <span class="hidden">Select Status</span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3"></label>
							<div class="col-md-9">
								<div class="checkbox">
									<label> <input type="checkbox" value="Y" checked="checked"
										name="isMailToTeamMembers"> Mail to Team Members
									</label>
								</div>
								<span class="hidden">Select Check Box</span>
							</div>
						</div>
					</div>
				</div>
				<div class="form-footer clearfix row">
					<div class="col-lg-12">
						<div class="pull-right">
							<input type="button" value="Save" id="save_project" onclick="saveProject();flushSaveProject()" class="btn btn-primary">
							 <input type="button" value="Cancel" onClick="javascript:location.href = '<c:url value='/projectDashboard'/>'" class="btn btn-default ml">
						</div>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</section>
<!-- Content Area Ends-->


<!-- Modal content Add Team-->
<div role="dialog" id="addTeam" class="modal fade">
	<div class="modal-dialog">
		<form:form id="addEmployee_form" name="addEmployee_form" method="post">
			<div class="modal-content" id="newEmployee">
				<div class="modal-header">
					<button data-dismiss="modal" class="close" type="button">x</button>
					<h4 class="modal-title">Add Employees</h4>
				</div>
				<div class="modal-body clearfix">
					<div class="form-horizontal custom-form clearfix">
						<div class="col-xs-12 col-md-12 col-lg-12">
							<div class="form-group">
								<label class="col-lg-3">Frist Name<span
									class="text-danger">*</span></label>
								<div class="col-lg-9">
									<input type="text" class="form-control" value="" name="firstName" id="firstName"
										field-name="Frist Name" data-validation="required validate_Space validate_length length2-100">
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-3">Last Name</label>
								<div class="col-lg-9">
									<input type="text" class="form-control" value="" name="lastName" id="lastName">
								</div>
							</div>
							<div class="form-group" id="EmailIdDiv">
								<label class="col-lg-3">Email <span class="text-danger">*</span></label>
								<div class="col-lg-9">
									<input type="text" class="form-control" value="" name="email" id="EmailId" field-name="Email"
										data-validation="required validate_email" onblur="emailVerification();">
								</div>
							</div>
							<!-- <div class="form-group">
								<label class="col-lg-3">Invoice Frequency<span
									class="text-danger">*</span></label>
								<div class="col-lg-9">
									<select class="form-control" name="invoiceFrequency" id="invoiceFrequency" field-name="Invoice Frequency" data-validation="required">
										<option value="">-Select Frequency-</option>
										<option>Weekly</option>
										<option>Bi-Weekly</option>
										<option>Semi-Monthly</option>
										<option>Monthly</option>
									</select>
								</div>
							</div> -->
							<div class="form-group">
								<label class="col-lg-3"></label>
								<div class="col-lg-9">
									<div class="checkbox">
										<label> <input type="checkbox" value="Y"
											name="isMailSent" checked="checked"> Send Invitation
										</label>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" id="add_employee" class="btn btn-primary" onclick="addEmployee();">Save</button>
					<button data-dismiss="modal" class="btn btn-default" type="button">Cancel</button>
				</div>
			</div>
		</form:form>
	</div>
</div>
<!-- Add Team modal popup ends -->

<!-- Add Client Model DialogBox Start-->
<div class="modal fade" id="addClient" role="dialog">
	<div class="modal-dialog">
		<form:form id="addClient_form" name="addClient_form" method="post">
			
			<div class="modal-dialog" id="newClient">
               <div class="modal-content">
                 <div class="modal-header">
                   <button data-dismiss="modal" class="close" type="button"><i class="fa fa-close"></i></button>
                   <h4 class="modal-title">Select Client</h4>
                 </div>
                 <div class="modal-body clearfix">
                   <div class="form-horizontal custom-form clearfix">
                     <div class="col-xs-12 col-md-12 col-lg-12">
                       <div class="form-group" id="org_dialog">
                         <label class="col-lg-3">Client Name <span class="text-danger">&nbsp;*</span></label>
                         <div class="col-lg-9" id="clientNameDiv">
                           <input type="text" class="form-control" id="orgName" name="orgName" value="${orgName}" onclick="getOrgName();"  onblur="getOrgDetails();validateClientName();" field-name="Client Name" data-validation="required validate_Space validate_length length2-250" value="">
                           <input type="hidden" id="orgLocationId" />
                         </div>
                       </div>
                       <div class="form-group">
                         <label class="col-lg-3">Website</label>
                         <div class="col-lg-9">
                           <input type="text" placeholder="client website" id="companyWebsite"  name="companyWebsite" class="form-control" data-validation="validate_Space validate_length length2-100 validate_domain" data-validation-optional="true">
                         </div>
                       </div>
                       
                       <div class="form-group">
			              <label class="col-md-3">Tier</label>
			              <div class="col-md-9">
			                  <select class="form-control selectpicker" id="level" name="level">
			                    <option value="Tier-1">Tier-1</option>
			                    <option value="Tier-2">Tier-2</option>
			                    <option value="Tier-3">Tier-3</option>
			                    <option value="Tier-4">Tier-4</option>
			                    <option selected="selected" value="Tier-5">Tier-5</option>
			                  </select>
			              </div>
                      </div>
                    </div>
                 </div>
              </div>
             <div class="modal-footer">
              <button type="button" id="save_client" class="btn btn-primary" onclick="addclient();">Save</button>
              <button data-dismiss="modal" class="btn btn-default" type="button">Cancel</button>
             </div>
          </div>
        </div>
		</form:form>
	</div>
</div>

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




<script type="text/javascript">
function getOrgName(){
	var orgCode = '${SourceLeadWeb_orgCode}';
	var sourceleadUrl = '${sourceleadUrl}';
	
	
	$( "#orgName" ).val( '' );
	$( "#orgLocationId" ).val( '' );
	
	$("#companyWebsite").attr("disabled", false);
	$("#companyWebsite" ).val('');
	
	$.ajax({                           
    	type: "POST",
    	url : sourceleadUrl+"/getOrgs?${_csrf.parameterName}=${_csrf.token}&orgCode="+orgCode,
        success: function(data) {
        	var json = JSON.parse(data);
        	var org = [];
        	for(var i=0;i<json.length;i++){
       		 var label = json[i][0]; 
       		 var value = json[i][1];
       	     tmp = {
       	        'label': label,
       	        'value': value
       	      };
       	  	org.push(tmp);
       		}
        	$("#orgName").autocomplete({
                
                minLength: 0,
                source: org,
                appendTo: "#org_dialog",
                focus: function( event, ui ) {
                       $( "#orgName" ).val( ui.item.label );
                       $( "#orgLocationId" ).val( ui.item.value );
                       return false;
                   },
                select: function( event, ui ) {
                    return false;
                }  
             })
        },
        error: function(e){
           alert('Ajax Error: ' + e.value);
        }
    });
}
</script>


<script type="text/javascript">
function getOrgDetails()
{
	var orgName = document.getElementById("orgName").value;
	var orgLocationId = document.getElementById("orgLocationId").value;
	var sourceleadUrl = '${sourceleadUrl}';
	if(orgName && orgLocationId){
   	 $.ajax({
   	    	type: "POST",
   	    	url : sourceleadUrl+"/getOrgDetails?${_csrf.parameterName}=${_csrf.token}&orgLocationId="+orgLocationId,
   	        contentType: "application/json; charset=utf-8",
   	        dataType: "json",
   	        success: function(data) {
   	        	if(data != null && data != ''){
   	        		var json = JSON.parse(data);
			      	if(json[3] != null && json[3] != ''){
			      		$("#companyWebsite").val(json[3]);
			      		$("#companyWebsite").attr("disabled", true);
			      	}
			      	 
   	        	}
   			 },
   	        error: function(e){
   	            alert('Ajax Error: ' + e.value);
   	        }
   	    });
	}
	
}
</script>


<!-- =============================== AddTask Divs  ============================ -->
<div id="divTaskTd1" style="display: none; float: left;">
	<input type="hidden" id="REPLACEROWIDtaskName"
		name="REPLACEROWIDtaskName" value="TASKNAMEVALUE" /><span
		id="REPLACEROWIDtaskNameValue">TASKNAMEVALUE</span>
</div>
<div id="divTaskTd2" style="display: none; float: left;">
	<input type="hidden" id="REPLACEROWIDtaskDescription"
		name="REPLACEROWIDtaskDescription" value="TASKDESCRIPTIONVALUE" /><span
		id="REPLACEROWIDtaskDescriptionValue">TASKDESCRIPTIONVALUE</span>
</div>
<div id="divTaskTd3" style="display: none; float: left;">
	<span><a title="Edit" href="javascript:void(0)" onclick="editTask(REPLACEROWID)"><i class="fa fa-edit"></i></a></span>&nbsp;&nbsp;&nbsp;
	<span><a title="Remove" href="javascript:void(0)" onclick="removeRow(REPLACEROWID)"><i class="fa fa-close"></i></a></span>
</div>
<!-- =============================== AddTask Divs  ============================ -->
<!-- Add Task Script start -->
<script type="text/javascript">
	function addTasks() {

		$('#taskNameDIV').find('p.jquery_form_error_message').remove();

		if ($('#taskName').val() == '') {
			$("input#taskName")
					.after(
							"<p class='jquery_form_error_message'>Task Name is required</span>");
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

		var cell0 = row.insertCell(0);
		var divTsTd1Str = document.getElementById("divTaskTd1").innerHTML;
		divTsTd1Str = divTsTd1Str.replace(/REPLACEROWID/g, (maxRowNum));
		divTsTd1Str = divTsTd1Str.replace(/TASKNAMEVALUE/g, (taskName));
		cell0.innerHTML = divTsTd1Str;

		var cell1 = row.insertCell(1);
		var divTsTd2Str = document.getElementById("divTaskTd2").innerHTML;
		divTsTd2Str = divTsTd2Str.replace(/REPLACEROWID/g, (maxRowNum));
		divTsTd2Str = divTsTd2Str.replace(/TASKDESCRIPTIONVALUE/g,
				(taskDescription));
		cell1.innerHTML = divTsTd2Str;

		var cell2 = row.insertCell(2);
		var divTsTd3Str = document.getElementById("divTaskTd3").innerHTML;
		divTsTd3Str = divTsTd3Str.replace(/REPLACEROWID/g, (maxRowNum));
		cell2.innerHTML = divTsTd3Str;

		$("#taskName").val('');
		$("#taskDescription").val('');
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
		var updateTaskName = $("#" + rowId + "taskName").val();
		var updateTaskDescription = $("#" + rowId + "taskDescription").val();
		$("#taskName").val('');
		$("#taskDescription").val('');
		$("#addTaskText").html('Update Task');
		$("#taskName").val(updateTaskName);
		$("#taskDescription").val(updateTaskDescription);
		$("#taskId").val(rowId);
		$("#save_task").removeAttr('onclick');
		$("#save_task").attr('onclick', 'updateTask(' + rowId + ');');
	}
</script>

<script type="text/javascript">
	function updateTask(rowId) {
		$("#" + rowId + "taskName").val($("#taskName").val());
		$("#" + rowId + "taskDescription").val($("#taskDescription").val());
		$("#" + rowId + "taskNameValue").html($("#taskName").val());
		$("#" + rowId + "taskDescriptionValue").html(
				$("#taskDescription").val());
		$("#taskName").val('');
		$("#taskDescription").val('');
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
								$('david[onclick]').removeAttr('onclick');
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
								divTsTd2Str = divTsTd2Str.replace(/TASKDESCRIPTIONVALUE/g,(opt.description));
								cell1.innerHTML = divTsTd2Str;

								var cell2 = row.insertCell(2);
								var divTsTd3Str = document.getElementById("divTaskTd3").innerHTML;
								divTsTd3Str = divTsTd3Str.replace(/REPLACEROWID/g, (opt.Id));
								cell2.innerHTML = divTsTd3Str;

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
	function addclient() {
		
		if ($('#addClient_form').validate(false, validationSettings)) {
			var orgLocationId = document.getElementById("orgLocationId").value;
			var frm = $('#addClient_form').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, "");
	    	//It will escape all the special characters
		    var jsonData = encodeURIComponent(con);
			 
			var sourceleadUrl = '${sourceleadUrl}';
			$("#save_client").attr("disabled", "disabled");

			$.ajax({
				type : "GET",
				url : sourceleadUrl + "/saveClientFromProject?json="+jsonData+"&orgLocationId="+orgLocationId+"&responderType=Client",
				success : function(data) {
					var json = JSON.parse(data);
					var output = "";
					output = output
							+ "<option value=''>-- Select Client --</option>";
					for (var i = 0; i < json.length; i++) {
						var opt = json[i];
						output = output + "<option value='" +opt.orgid+ "'>"
								+ opt.orgName + "</option>";
					}
					$("#addClientId").empty();
					$("#addClientId").append(output);
					$('#addClient').modal('hide');
				},
				error : function(e) {

				}
			});

			$('#orgName').attr('');
			$('#webSite').attr('');
			return true;
		} else {
			return false;
		}
	}
	$('body').on('blur', '#addClient_form', function() {
		$("#addClient_form").showHelpOnFocus();
		$("#addClient_form").validateOnBlur(false, validationSettings);
	});
</script>


<script type="text/javascript">
	function saveProject() {
		var validationSettings = {
			errorMessagePosition : 'element',
			borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd'
		};
		if ($('#project_form').validate(false, validationSettings)) {
			var frm = $('#project_form').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, "");

			//It will escape all the special characters
			var jsonData = encodeURIComponent(con);

			var sourceleadUrl = '${sourceleadUrl}';
			$("#save_project").attr("disabled", "disabled");
			$("#save_project").addClass('button').val('Processing..');
     		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');


			$.ajax({
						type : "POST",
						url : sourceleadUrl+ "/storeProject?${_csrf.parameterName}=${_csrf.token}&json="+ jsonData,
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

<!-- this script is for Save Employee functionality Start-->
<script type="text/javascript">
	function addEmployee() {
		var validationSettings = {
			errorMessagePosition : 'element',
			borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd'
		};
		if ($('#addEmployee_form').validate(false, validationSettings)) {
			var frm = $('#addEmployee_form').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, "");
			
			 //It will escape all the special characters
		     var jsonData = encodeURIComponent(con);
			  
			var sourceleadUrl = '${sourceleadUrl}';
			$("#add_employee").attr("disabled", "disabled");

			$.ajax({
					type : "POST",
					url : sourceleadUrl
							+ "/addNewEmployee?${_csrf.parameterName}=${_csrf.token}&json="
							+ jsonData,
					success : function(data) {
						$('#addTeam').modal('hide');
					},
					});
			return true;
		} else {
			return false;
		}
	}
	$('body').on('blur', '#addEmployee_form', function() {
		$("#addEmployee_form").showHelpOnFocus();
		;
		$("#addEmployee_form").validateOnBlur(false, validationSettings);
	});
</script>

<!-- this script is for Save Employee functionality Ends -->

<!-- this script is for Get the TeamMembers functionality Start -->
<script type="text/javascript">
	function getTeamMembers() {
		var level1Manager = $("#level1Manager").val(); 
		var level2Manager = $("#level2Manager").val();
		var startDate     =    $("#StartDate").val();
		var searchByName = $("#searchByName").val();
		var sourceleadUrl = '${sourceleadUrl}';
		if(startDate != null  && startDate != ''){
			
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
			
			
			  $("#assignTeamModal").attr('data-target','#assignTeam');
			$.ajax({
						type : "POST",
						url : sourceleadUrl + "/getEmployeesListByExcludingManagers?${_csrf.parameterName}=${_csrf.token}&level1Manager="+level1Manager+"&level2Manager="+level2Manager+"&searchByName="+searchByName,
						success : function(data) {
							var json = JSON.parse(data);
							var output = "";
							output = output+' <div class="wrap_employeeTable">';
							output = output + ' <table cols="0" id="assignEmployeePagination" class="table table-bordered table-striped  clearfix">';
							output = output + ' <thead>';
							output = output + ' <tr>';
							output = output + ' <th>&nbsp;&nbsp;</th>';
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
								// var projectEndDate = $('#EndDate').val();
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
									output = output + "<td><span class='tbldata-dotdot-sm' title='"+opt.user.firstName+"&nbsp;"+opt.user.lastName+"'>" + opt.user.firstName+ "&nbsp;" + opt.user.lastName+ "</span></td>";
									output = output + "<td><span class='tbldata-dotdot-vsm' title='"+opt.user.email+"'>" + opt.user.email+ "</span></td>";
									output = output + "<td><span class='tbldata-dotdot-vsm' title='"+role+"'>" + role + "</span></td>";
									output = output+ "<td><span class='' title='"+projectStartDate+"'><div class='input-group tblminwidth-150'> <span class='input-group-addon'><i class='fa fa-calendar'></i></span>";
	
									output = output+ "<input  id='startDate"+ opt.id+ "' type='text' name='Date' value='"+ projectStartDate+ "' class='form-control clsDatePicker' readonly='readonly'>";
									output = output + "</div></span></td>";
									output = output+ "<td><div class='input-group tblminwidth-150'> <span class='input-group-addon'><i class='fa fa-calendar'></i></span>";
									output = output + "<input id='endDate" + opt.id + "'  type='text' name='Date' value='' class='form-control clsDatePicker' readonly='readonly'>";
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
									
									$('#startDate'+opt.id).datepicker({
										dateFormat : 'yy-mm-dd',
										minDate:projectStartDate,
										maxDate:projectEndDate,
										onSelect : function(selected) {
											var dt = new Date(selected);
											var id = $(this).attr('id');
											var actualId = $(this).attr('id').split('startDate')[1];
											dt.setDate(dt.getDate() + 1);
											$("#endDate"+actualId).datepicker("option", "minDate", dt);
											
										}
									});
		
									$('#endDate'+opt.id).datepicker({
										dateFormat : 'yy-mm-dd',
										maxDate:projectEndDate,
										minDate:projectStartDate,
										onSelect : function(selected) {
											var dt = new Date(selected);
											var id = $(this).attr('id');
											var actualId = $(this).attr('id').split('endDate')[1];
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
function assignEmployeespagination(){  
    $('#assignEmployeePagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10000, 15, 20, -1], [10000, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
     		"sSearch": ""
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

<!-- this script is for website validation functionality Start -->
<script type="text/javascript">
	function validateWebsite() {
		var website = document.getElementById("companyWebsite").value;
		var sourceleadUrl = '${sourceleadUrl}';
		if(website.match(/^[^\s-](.*)*$/) !== null){
		var sourceleadBLockedwebsites = '${sourceLeadBlockedSocialDomains}';
		$('#companyWebsiteDiv').find('p.jquery_form_error_message').remove();
		
		if (website != null && website != '') {

			//$("#save_client").attr("disabled", "disabled");
			// $( "#save_client" ).addClass('button').val('Processing..');
			$.ajax({
				type : "GET",
				url : sourceleadUrl + "/ws/validateClientWebsite?website="+ website + "&sourceleadBLockedwebsites="+ sourceleadBLockedwebsites,
				success : function(data) {
					var result = data;
					if (result == "Website Duplicated" || result == "Invalid Website") {
						//existed website
						$("#save_client").attr("disabled", "disabled");
						$("input#companyWebsite").after("<p class='jquery_form_error_message'>"+ website+ " is "+result+"</span>");
						document.getElementById("companyWebsite").setAttribute('record-exist', 'yes');
						document.getElementById("companyWebsite").setAttribute('record-exist-errorMsg',website + ' is '+result);
						$('input#companyWebsite').removeAttr( "record-exist record-exist-errormsg");
						
					} else {
						//New WebSite class="form-control"
					    $("#save_client").removeAttr("disabled");
						$('input#companyWebsite').removeAttr("class record-exist record-exist-errormsg");
						$('input#companyWebsite').attr('class','valid form-control');
						$('#companyWebsiteDiv').find('p.jquery_form_error_message').remove();
				
						$("#save_client").click(function() {
							if ($(this).validate(false, validationSettings)) {
							}
						});
					}
				},

			});
		}
	  }
	}
</script>
<!-- this script is for website validation functionality Ends -->

<script type="text/javascript">
	function flushProject() {
		$("#save_client").removeAttr("disabled");
		$('#orgName').val('');
		$('#companyWebsite').val('');
		$(".error").removeClass("error");
		$('#newClient').find('p.jquery_form_error_message').remove();
	}
</script>

<script type="text/javascript">
	function flushEmployee() {
		$("#add_employee").removeAttr("disabled");
		$('#firstName').val('');
		$('#EmailId').val('');
		$('#lastName').val('');
		$('#invoiceFrequency').val('');
		$(".error").removeClass("error");
		$('#newEmployee').find('p.jquery_form_error_message').remove();
	}
</script>

<script>
	$(function() {

		$('#StartDate').datepicker({
			dateFormat : 'yy-mm-dd',
			onSelect : function(selected) {
				var dt = new Date(selected);
				dt.setDate(dt.getDate() + 1);
				$("#EndDate").datepicker("option", "minDate", dt);
				addValidClass();
			}
		});

		$('#EndDate').datepicker({
			dateFormat : 'yy-mm-dd',
			onSelect : function(selected) {
				var dt = new Date(selected);
				dt.setDate(dt.getDate() - 1);
				$("#StartDate").datepicker("option", "maxDate", dt);
				
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
				if(dummyDate < today){
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
	var currentApproverId = document.getElementById("level1Manager").value;
	var count = 1;
	$(".myCheckbox").each(
					function() {
						var localId = this.id;
						document.getElementById(localId).checked = false; 
	});
	$(".myCheckbox").val('');
	getChildLevel1Approvers(currentApproverId);
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
		 flushEmployee();
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

<script type="text/javascript">
function validateClientName(){
	 var clientName = document.getElementById("orgName").value;
	var sourceleadUrl = '${sourceleadUrl}';
	var sourceleadBLockedwebsites = '${sourceLeadBlockedSocialDomains}';
	$('#clientNameDiv').find('p.jquery_form_error_message').remove();
	if (clientName != null && clientName != '') {
		$.ajax({
			type : "GET",
			url : sourceleadUrl + "/ws/isClientNameValid?${_csrf.parameterName}=${_csrf.token}&clientName="+clientName,
			success : function(data) {
				var result = data;
				if (result == true) {
					//duplicate client
					$("input#orgName").after("<p class='jquery_form_error_message'>"+ clientName+ " is Already Existed</span>");
					document.getElementById("orgName").setAttribute('record-exist', 'yes');
					document.getElementById("orgName").setAttribute('record-exist-errorMsg',clientName +"  already existed");
					$('input#orgName').removeAttr( "record-exist record-exist-errormsg");
					
				} else {
					$('input#orgName').removeAttr("class record-exist record-exist-errormsg");
					$('input#orgName').attr('class','valid form-control');
					$('#clientNameDiv').find('p.jquery_form_error_message').remove();
					$("#save_client").click(function() {
						if ($(this).validate(false, validationSettings)) {
						}
					});
				}
			},

		});
	} 
}
</script>
<script src="resources/js/chosen.jquery.min.js"></script> 
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