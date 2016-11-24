 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link rel="icon" type="resources/image/png" href="resources/images/fav.png" />
 <link href="resources/css/jquery-ui.css" rel="stylesheet">
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script src="resources/js/formvalidator.js"></script>

<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script> 

<!-- this script is for Project Details functionality at Projects screen -->
<script>
	function projectDetails(projectId) {
		var sourceleadUrl = '${sourceleadUrl}';
		
				$.ajax({
					type : "POST",
					url : sourceleadUrl
							+ "/getProjectById?${_csrf.parameterName}=${_csrf.token}&ProjectId="
							+ projectId,
					success : function(data) {
						var json = JSON.parse(data);
						var endDateStr = "";
						var description = "";
						var clientName = "";
						var projectSequeneNumber = "";
						if (json.endDateStr != null && json.endDateStr != ''
								&& json.endDateStr != 'undefined') {
							endDateStr = json.endDateStr;
						} else {
							endDateStr = "--";
						}
						if (json.description != null && json.description != ''
								&& json.description != 'undefined') {
							description = json.description;
						} else {
							description = "NO DATA";
						}
						if (json.projectSequeneNumber != null
								&& json.projectSequeneNumber != ''
								&& json.projectSequeneNumber != 'undefiend') {
							projectSequeneNumber = json.projectSequeneNumber;
						} else {
							projectSequeneNumber = "--";
						}
						if (json.client != null && json.client != ''
								&& json.client != 'undefined') {
							clientName = json.client
						} else {
							clientName = "--";
						}
						var result = "";
						result = result
								+ '      <div class="project_tabs_inn_table">';
						result = result + '			<div class="project_details">';
						result = result + '<div class="useredit useredit_a">';
						result = result
								+ '<a id="edit-user" href="javascript:editProjectDetails('
								+ projectId
								+ ')"><i class="glyphicon glyphicon-pencil"></i>Edit</a>';
						result = result + '</div>';
						result = result
								+ '				<div class="project_details_cta">';
						result = result + '					<strong>Status:</strong><span>';
						result = result + json.projectStatus;
						result = result + '					</i></span>';
						result = result
								+ '				</div></span><div class="clearfix"></div>';

						result = result + '				<div class="clearfix"></div>';
						result = result
								+ '				<div class="project_details_cta" id="ProjectIdDiv">';
						result = result + '					<strong>Project Id:</strong>';
						result = result + '					<span>' + projectSequeneNumber
								+ '</span><div class="clearfix"></div>';
						result = result + '				</div>';

						result = result
								+ '				<div class="project_details_cta" id="projectNameId">';
						result = result + '					<strong>Project name:</strong>';
						result = result + '					<span>' + json.projectName
								+ '</span><div class="clearfix"></div>';
						result = result + '				</div>';

						result = result + '<div class="project_details_cta">';
						result = result + '<strong>Manager 1:</strong><span>';

						for (var j = 0; j < json.managersList.length; j++) {
							var manager = json.managersList[j];
							if (json.manager1 == manager.id) {
								result = result + manager.firstName + " "
										+ manager.lastName;
							}
						}
						result = result
								+ '			</span><div class="clearfix"></div>';
						result = result + '				</div>';

						if (json.manager2 != null && json.manager2 != ""
								&& json.manager2 != "undefined") {
							result = result
									+ '				<div class="project_details_cta">';
							result = result
									+ '					<strong>Manager 2:</strong><span>';

							for (var j = 0; j < json.managersListwithoutManager1.length; j++) {
								var manager = json.managersListwithoutManager1[j];
								if (json.manager2 == manager.id) {
									result = result + manager.firstName + " "
											+ manager.lastName;
								}
							}
							result = result
									+ '			</span><div class="clearfix"></div>';
							result = result + '				</div>';
						}

						result = result + '<div class="project_details_cta">';
						result = result + '<strong>Client:</strong>';
						result = result + '	<span>' + clientName
								+ '</span><div class="clearfix"></div>';
						result = result + '<div class="clearfix"></div>';
						result = result + '</div>';
						result = result
								+ '				<div class="project_details_cta">';
						result = result
								+ '					<strong>Team members:</strong><span>'
								+ json.projectMembersCount
								+ '</span><div class="clearfix"></div>';
						result = result + '				</div>';
						result = result + '				<div class="clearfix"></div>';
						result = result
								+ '				<div class="project_details_cta">';
						result = result + '					<strong>Start date:</strong>';
						result = result + '					<span>' + json.startDateStr
								+ '</span><div class="clearfix"></div>';
						result = result + '				</div>';
						result = result
								+ '				<div class="project_details_cta">';
						result = result + '					<strong>End date:</strong>';
						result = result + '					<span> ' + endDateStr
								+ '</span><div class="clearfix"></div>';
						result = result + '				</div>';
						result = result + '				<div class="clearfix"></div>';

						result = result
								+ '				<div class="project_details_cta">';
						result = result
								+ '					<strong>Created By:</strong><span>'
								+ json.createdBy
								+ '</span><div class="clearfix"></div>';
						result = result + '				</div>';
						result = result
								+ '				<div class="project_details_cta">';
						result = result
								+ '					<strong>Created date:</strong><span> '
								+ json.createdDateStr
								+ '</span><div class="clearfix"></div>';

						result = result + '				</div>';
						if (json.updatedDateStr != null
								&& json.updatedDateStr != ''
								&& json.updatedDateStr != 'undefiend') {
							result = result
									+ '				<div class="project_details_cta">';
							result = result
									+ '					<strong>Last Updated:</strong><span>'
									+ json.updatedDateStr
									+ '</span><div class="clearfix"></div>';
							result = result + '				</div>';
						}
						result = result + '				<div class="clearfix"></div>';
						result = result
								+ '				<p><strong>Description:</strong></p>'
								+ description + '<div class="clearfix"></div>';
						result = result
								+ ' <div class="user_summary_edit user_summary_edit_a" id="editProjectDetailsDiv"></div>';
						result = result + '			</div>';
						result = result + '      </div>';
						result = result + '  </div>   ';
						result = result + '</div>';

						$("#tab-1").empty();
						$("#tab-2").empty();
						$("#tab-3").empty();
						$("#tab-3").append(result);

					}
				});
	}
</script>
<script type="text/javascript">
	function editProjectDetails(projectId) {

		var sourceleadUrl = '${sourceleadUrl}';
		$
				.ajax({
					type : "POST",
					url : sourceleadUrl
							+ "/getProjectById?${_csrf.parameterName}=${_csrf.token}&ProjectId="
							+ projectId,
					success : function(data) {
						var json = JSON.parse(data);

						var endDateStr = "";
						var description = "";
						var projectSequeneNumber = "";
						if (json.endDateStr != null && json.endDateStr != ''
								&& json.endDateStr != 'undefined') {
							endDateStr = json.endDateStr;
						} else {
							endDateStr = "";
						}
						if (json.description != null && json.description != ''
								&& json.description != 'undefined') {
							description = json.description;
						} else {
							description = "";
						}
						if (json.projectSequeneNumber != null
								&& json.projectSequeneNumber != ''
								&& json.projectSequeneNumber != 'undefiend') {
							projectSequeneNumber = json.projectSequeneNumber;
						} else {
							projectSequeneNumber = "";
						}

						var result = "";
						result = result
								+ ' <form id="editProject_form"  name="editProject_form" method="post">';
						result = result
								+ '<div class="user_summary_edit_cta" id="ProjectIdDiv">';
						result = result + '	<label>Project Id</label>';
						result = result
								+ '	<input type="text"  id="ProjectSequeneNumber" name="ProjectSequeneNumber" value="'
								+ projectSequeneNumber
								+ '" placeholder="ProjectSequene Number" data-validation="validate_Space validate_length length2-50" data-validation-optional="true" onKeyUp="validateProjectSequeneNumber();" class="project_detfield">';
						result = result + '</div>	';

						result = result + '<div class="user_summary_edit_cta">';
						result = result + '	<label>Status</label>';
						result = result
								+ '<select id="projectStatusId" name="projectStatusId">';
						if (json.projectStatus == 'STARTED') {
							result = result
									+ '<option selected>Started</option>';
						} else {
							result = result + '<option>Started</option>';
						}

						if (json.projectStatus == 'INPROGRESS') {
							result = result
									+ '<option selected>In-Progress</option>';
						} else {
							result = result + '<option>In-Progress</option>';
						}

						if (json.projectStatus == 'COMPLETED') {
							result = result
									+ '<option selected>Completed</option>';
						} else {
							result = result + '<option>Completed</option>';
						}

						if (json.projectStatus == 'ONHOLD') {
							result = result
									+ '<option selected>On-Hold</option>';
						} else {
							result = result + '<option>On-Hold</option>';
						}

						result = result + '</select>';
						result = result + '</div>';
						result = result + '<div class="user_summary_edit_cta">';
						result = result
								+ '<label>Project name<span	class="text-danger">*</span></label>';
						result = result
								+ '		<span><input type="text" id="projectName" name="projectName" value="'+json.projectName+'" placeholder="Project name" class="project_detfield" data-validation="required validate_Space validate_length length2-100" field-name="Project Name" ></span><span id=fname_msg></span><div class="clearfix"></div>';
						result = result + '</div>';
						result = result + '<div class="user_summary_edit_cta">';
						result = result + '	<label>Manager 1</label>';
						result = result
								+ '<select onchange="getLevel2Approvers('
								+ json.manager2
								+ ');" id="level1Manager" name="level1Manager" >';
						for (var j = 0; j < json.managersList.length; j++) {
							var manager = json.managersList[j];
							if (json.manager1 == manager.id) {
								selected = 'selected';
								result = result
										+ "<option value="+manager.id+" "+selected+" >"
										+ manager.firstName + " "
										+ manager.lastName + "</option>";
							} else {
								result = result
										+ "<option value="+manager.id+" >"
										+ manager.firstName + " "
										+ manager.lastName + "</option>";
							}
						}
						result = result + '</select>';
						result = result + '</div>';

						if (json.manager2 != null && json.manager2 != ""
								&& json.manager2 != "undefined") {

							result = result
									+ '<div class="user_summary_edit_cta">';
							result = result + '<label> Manager 2: </label>';
							result = result
									+ '<select id="level2Manager" name="level2Manager">';
							for (var j = 0; j < json.managersListwithoutManager1.length; j++) {
								var manager = json.managersListwithoutManager1[j];
								if (json.manager2 == manager.id) {
									selected = 'selected';
									result = result
											+ "<option value="+manager.id+" "+selected+" >"
											+ manager.firstName + " "
											+ manager.lastName + "</option>";
								} else {

									result = result
											+ "<option value="+manager.id+" >"
											+ manager.firstName + " "
											+ manager.lastName + "</option>";
								}
							}
							result = result + '</select>';
							result = result + '</div>';

						}

						result = result + '<div class="user_summary_edit_cta">';
						result = result + '	<label>Client</label>';

						if (json.relationId != null) {
							result = result
									+ '<select id="clientId" disabled="disabled" name="clientId">';
						} else {
							result = result
									+ '<select id="clientId" name="clientId">';
						}
						result = result + "<option value=''>Select</option>";
						for (var i = 0; i < json.clientName.length; i++) {
							var managers = json.clientName[i];
							var projrctmanager = managers.split('&');
							var name = projrctmanager[0];
							var id = projrctmanager[1];
							var selected = '';
							if (json.relationId == id) {
								selected = 'selected';
								result = result
										+ "<option value="+id+" "+selected+" >"
										+ name + "</option>";
							} else {
								result = result + "<option value="+id+" >"
										+ name + "</option>";
							}

						}
						result = result + '</select>';
						result = result + '</div>';

						result = result + '<div class="user_summary_edit_cta">';
						result = result + '<label>Team members</label>';
						result = result
								+ '<input type="text" value='+json.projectMembersCount+' disabled="disabled">';
						result = result + '</div>';

						result = result + '<div class="user_summary_edit_cta">';
						result = result + '	<label>Start date</label>';
						result = result
								+ '	<input class="calendar" value='+json.startDateStr+' type="text" disabled="disabled">';
						result = result + '</div>	';

						result = result + '<div class="user_summary_edit_cta">';
						result = result + '<label>End date</label>';
						result = result
								+ '	<input class="calendar" type="text" placeholder="DD/MM/YY" value="'+endDateStr+'" id="endDateStr1" name="endDateStr1" >';
						result = result + '</div>';

						result = result + '<div class="user_summary_edit_cta">';
						result = result + '<label>Description </label>';
						result = result
								+ '<textarea class="edit_desc" placeholder="Enter Description" id="description" name="description">'
								+ description + '</textarea>';
						result = result + '</div>       ';
						result = result + '<div class="clearfix"></div>';
						result = result + '<div class="user_summary_edit_cta">';
						result = result
								+ '	<input class="us-btn" value="Save" onclick = "updateProjectDetails('
								+ projectId
								+ ');"type="button"> <input id="btnClose" class="us-btn us-btn_a" value="Cancel" onClick="editProjectDetails('
								+ projectId + ');" type="button">';
						result = result + '</div>		';
						result = result + '<div class="clearfix"></div>';
						result = result + ' </form>';

						$(".user_summary_edit").slideToggle(100);
						$("#editProjectDetailsDiv").empty();
						$("#editProjectDetailsDiv").append(result);

						//Assign date of project member
						var selected = json.startDateStr;
						var dt = new Date(selected);
						dt.setDate(dt.getDate());

						//Main project end date
						var projectEndDate = json.endDateStr;
						$('#endDateStr1').datepicker({
							dateFormat : 'yy-mm-dd',
							minDate : dt,
							onClose : function() {
							}
						});

					},
				});

	}
</script>


<script type="text/javascript">
	function updateProjectDetails(projectId) {
		if ($('#editProject_form').validate(false, validationSettings)) {
			var frm = $('#editProject_form').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, "");
			//It will escape all the special characters
			var jsonData = encodeURIComponent(con);
			var sourceleadUrl = '${sourceleadUrl}';
			$.ajax({
						type : "POST",
						url : sourceleadUrl + "/updateProjectByProjectId?${_csrf.parameterName}=${_csrf.token}&json="+ jsonData + "&ProjectId=" + projectId,
						success : function(data) {
							projectDetails(projectId);
						}
					});
			return true;
		} else {
			return false;
		}

	}
	$('body').on('blur', '#editProject_form', function() {
		$("#editProject_form").showHelpOnFocus();
		;
		$("#editProject_form").validateOnBlur(false, validationSettings);
	});
</script>
<script type="text/javascript">
	function validateProjectSequeneNumber() {
		var ProjectSequeneNumber = document
				.getElementById("ProjectSequeneNumber").value;
		//space value  consider this condition 
		if (ProjectSequeneNumber.match(/^[^\s-](.*)*$/) != null) {
			var sourceleadUrl = '${sourceleadUrl}';
			$('#ProjectIdDiv').find('p.jquery_form_error_message').remove();
			if (ProjectSequeneNumber != null && ProjectSequeneNumber != "") {
				$
						.ajax({
							type : "POST",
							url : sourceleadUrl
									+ "/validateProjectSequeneNumberById?${_csrf.parameterName}=${_csrf.token}&ProjectSequeneNumber="
									+ ProjectSequeneNumber,
							success : function(data) {
								if (data == true) {
									$("input#ProjectSequeneNumber")
											.after(
													"<p class='jquery_form_error_message'>"
															+ ProjectSequeneNumber
															+ " is Already Existed</span>");
									document
											.getElementById(
													"ProjectSequeneNumber")
											.setAttribute('record-exist', 'yes');
									document
											.getElementById(
													"ProjectSequeneNumber")
											.setAttribute(
													'record-exist-errorMsg',
													ProjectSequeneNumber
															+ ' is Already Existed');
								} else {
									$('input#ProjectSequeneNumber')
											.removeAttr(
													"record-exist record-exist-errormsg");
									$('#ProjectIdDiv').find(
											'p.jquery_form_error_message')
											.remove();
								}
							},
						});
			}
		}
	}
</script>
<script type="text/javascript">
	function projectIdValidate(projectId, localFieldName) {
		var ProjectSequeneNumber = document
				.getElementById("projectSequeneNumber").value;
		var sourceleadUrl = '${sourceleadUrl}';
		$('#ProjectIdDiv').find('p.jquery_form_error_message').remove();
		if (ProjectSequeneNumber.trim().match(/^\S*$/) == '') {
			if (ProjectSequeneNumber != "") {
				$("input#ProjectSequeneNumber")
						.after(
								"<p class='jquery_form_error_message'>Must not start with spaces</span>");
			}
		} else {
			if (ProjectSequeneNumber != null && ProjectSequeneNumber != "") {
				$
						.ajax({
							type : "POST",
							url : sourceleadUrl
									+ "/validateProjectSequeneNumberById?${_csrf.parameterName}=${_csrf.token}&ProjectSequeneNumber="
									+ ProjectSequeneNumber,
							success : function(data) {
								if (data == true) {
									$("input#projectSequeneNumber")
											.after(
													"<p class='jquery_form_error_message'>"
															+ ProjectSequeneNumber
															+ " is Already Existed</span>");
									document
											.getElementById(
													"projectSequeneNumber")
											.setAttribute('record-exist', 'yes');
									document
											.getElementById(
													"projectSequeneNumber")
											.setAttribute(
													'record-exist-errorMsg',
													ProjectSequeneNumber
															+ ' is Already Existed');
									$('input#projectSequeneNumber')
											.removeAttr(
													"record-exist record-exist-errorMsg");
								} else {
									$('input#projectSequeneNumber')
											.removeAttr(
													"record-exist record-exist-errormsg");
									$('#ProjectIdDiv').find(
											'p.jquery_form_error_message')
											.remove();
									inlineEditForProjects(projectId,
											localFieldName);
								}
							},
						});
			}

		}
	}
</script>
<script type="text/javascript">
	function getApproversLevel2(currentApproverId, manager2) {
		var sourceleadUrl = '${sourceleadUrl}';
		var currentApproverId = currentApproverId;
		var manager2 = manager2;
		$
				.ajax({
					type : "POST",
					url : sourceleadUrl
							+ "/getApproversByUserId?${_csrf.parameterName}=${_csrf.token}&currentApproverId="
							+ currentApproverId,
					success : function(data) {
						var json = JSON.parse(data);
						var output = "";
						output = output
								+ "<option value=''>-Select Manager-</option>";
						for (var i = 0; i < json.length; i++) {

							var opt = json[i];
							if (manager2 == opt.id) {
								selected = 'selected';
								output = output
										+ "<option "+selected+" value='" +opt.id+ "'>"
										+ opt.firstName + " " + opt.lastName
										+ "</option>";
							} else {
								output = output
										+ "<option value='" +opt.id+ "'>"
										+ opt.firstName + " " + opt.lastName
										+ "</option>";
							}

						}
						$("#level2Manager").empty();
						$("#level2Manager").append(output);
					},
				});

	}
</script>


<script type="text/javascript">
	function getLevel2Approvers(manager2) {
		var currentApproverId = document.getElementById("level1Manager").value;
		getChildLevel1Approvers(currentApproverId, manager2);
	}
</script>
<script type="text/javascript">
	function getChildLevel1Approvers(currentApproverId, manager2) {
		var sourceleadUrl = '${sourceleadUrl}';
		if (currentApproverId == manager2) {
			if (currentApproverId != null && currentApproverId != ''
					&& currentApproverId != 'undefined') {
				$
						.ajax({
							type : "POST",
							url : sourceleadUrl
									+ "/getApproversByUserId?${_csrf.parameterName}=${_csrf.token}&currentApproverId="
									+ currentApproverId,
							success : function(data) {
								var json = JSON.parse(data);
								var output = "";
								output = output
										+ "<option value=''>-Select Manager-</option>";
								for (var i = 0; i < json.length; i++) {
									var opt = json[i];
									output = output
											+ "<option value='" +opt.id+ "'>"
											+ opt.firstName + " "
											+ opt.lastName + "</option>";
								}
								$("#level2Manager").empty();
								$("#level2Manager").append(output);
							},
							error : function(e) {

							}
						});
			}
		} else {
			getApproversLevel2(currentApproverId, manager2);
		}
	}
</script>


<!-- this script is for Email verification functionality at Projects screen -->
<script type="text/javascript">
	function emailVerification() {
		var EmailId = document.getElementById("EmailId").value;
		var sourceleadUrl = '${sourceleadUrl}';
		$('#EmailIdDiv').find('p.jquery_form_error_message').remove();
		if (EmailId != null && EmailId != "") {
			$ .ajax({
						type : "POST",
						url : sourceleadUrl + "/getEmailVerification?${_csrf.parameterName}=${_csrf.token}&EmailId=" + EmailId,
						success : function(data) {
							if (data == 'true') {
								$("input#EmailId").after( "<p class='jquery_form_error_message'>" + EmailId + " is Already Existed</span>");
								document.getElementById("EmailId") .setAttribute('record-exist', 'yes');
								document .getElementById("EmailId") .setAttribute('record-exist-errorMsg', EmailId + ' is Already Existed');
								$('input#EmailId').removeAttr( "record-exist record-exist-errorMsg");
							} else {
								$('input#EmailId').removeAttr( "record-exist record-exist-errormsg");
								$('#EmailIdDiv').find( 'p.jquery_form_error_message').remove();
							}
						},
					});
		}
	}
</script>
