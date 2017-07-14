<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link rel="icon" type="resources/image/png" href="resources/images/fav.png" />
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script src="resources/js/formvalidator.js"></script>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!-- this script is for Project Details functionality at Projects screen -->
<script>
	function projectDetails(projectId) {
		var sourceleadUrl = '${sourceleadUrl}';
				$.ajax({
					type : "POST",
					url : sourceleadUrl	+ "/getProjectById?${_csrf.parameterName}=${_csrf.token}&ProjectId="+ projectId,
					success : function(data) {
						var json = JSON.parse(data);
						var endDateStr = "";
						var description = "";
						var clientName = "";
						var projectSequeneNumber = "";
						var invoiceFrequency = "";
						var isTimesheetAttachmentRequired = "";
						var allowFreeLance = "";
						var allowRemote = "";
						var allowOvertime = "";
						var allowTimeEntryAfterDeadLine = "";
						var allowBidding = "";
						var allowOffShore = "";
						var flatPercentageId = "";
						
						
						if (json.isTimesheetAttachmentRequired != null && json.isTimesheetAttachmentRequired != '' && json.isTimesheetAttachmentRequired != 'undefined'
								&& json.isTimesheetAttachmentRequired == '1') {
							isTimesheetAttachmentRequired = "YES";
					     } else {
					    	 isTimesheetAttachmentRequired = "NO";
					    }
						
						
						if (json.allowFreeLance != null && json.allowFreeLance != '' && json.allowFreeLance != 'undefined'
							&& json.allowFreeLance == '1') {
							allowFreeLance = "YES";
				     	} else {
				     		allowFreeLance = "NO";
				    	}

						if (json.allowRemote != null && json.allowRemote != '' && json.allowRemote != 'undefined'
							&& json.allowRemote == '1') {
							allowRemote = "YES";
				     	} else {
				     		allowRemote = "NO";
				    	}
						
						if (json.allowOvertime != null && json.allowOvertime != '' && json.allowOvertime != 'undefined'
							&& json.allowOvertime == '1') {
							allowOvertime = "YES";
				     	} else {
				     		allowOvertime = "NO";
				    	}
						
						if (json.allowTimeEntryAfterDeadLine != null && json.allowTimeEntryAfterDeadLine != '' && json.allowTimeEntryAfterDeadLine != 'undefined'
							&& json.allowTimeEntryAfterDeadLine == '1') {
							allowTimeEntryAfterDeadLine = "YES";
				     	} else {
				     		allowTimeEntryAfterDeadLine = "NO";
				    	}
						
						if (json.allowBidding != null && json.allowBidding != '' && json.allowBidding != 'undefined'
							&& json.allowBidding == '1') {
							allowBidding = "YES";
				     	} else {
				     		allowBidding = "NO";
				    	}
						
						if (json.allowOffShore != null && json.allowOffShore != '' && json.allowOffShore != 'undefined'
							&& json.allowOffShore == '1') {
							allowOffShore = "YES";
				     	} else {
				     		allowOffShore = "NO";
				    	}						
						
					
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
							description = "----";
						}
						
						if (json.invoiceFrequency != null && json.invoiceFrequency != '' && json.invoiceFrequency != 'undefined') {
							invoiceFrequency = json.invoiceFrequency;
					     } else {
					    	 invoiceFrequency = "----";
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
						
						if (json.overtimeRateType != null && json.overtimeRateType != '' && json.overtimeRateType != 'undefined') {
							flatPercentageId = json.overtimeRateType
						} else {
							flatPercentageId = "";
						}
						
						var proStatus ='';
						if(json.projectStatus == 'STARTED'){
							proStatus = 'Started';
						}else if(json.projectStatus == 'INPROGRESS'){
							proStatus = 'In-Progress';
						}else if(json.projectStatus == 'ONHOLD'){
							proStatus = 'On-Hold';
						}else if(json.projectStatus == 'COMPLETED'){
							proStatus = 'Completed';
						}
						
						
						
						var result = "";
						result = result	+ '<div class="project_tabs_inn_table">';
						result = result + '<div class="project_details">';
						result = result + '<div class="useredit useredit_a">';
						result  = result + '<div class="success_pop" id="selector2" style="display: none;">';
					    result  = result + '<i class="fa fa-check-circle"></i><span id="docMsgDiv2"></span>';
					    result  = result + '</div>';
						
						result = result +'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_PROJECT\')">'; 
						result = result	+ '<a id="edit-user" href="javascript:editProjectDetails('+ projectId+ ')"><i class="glyphicon glyphicon-pencil"></i>Edit</a>';
	   		    		result = result +'</sec:authorize>';
						
	   		    		result = result + '</div>';
	   		    		
						result = result	+ '<div class="project_details_cta">';
						result = result + '<strong>Status:</strong><span>';
						result = result +  proStatus;
						result = result + '</span>';
						result = result	+ '</div><div class="clearfix"></div>';
						
						result = result + '<input type="hidden" name="dbProjectStatus" id="dbProjectStatus" value="'+json.projectStatus+'" >';
						result = result + '<div class="clearfix"></div>';

						result = result	+ '<div class="project_details_cta" id="ProjectIdDiv">';
						result = result + '<strong>Project Id:</strong>';
						result = result + '<span>'+projectSequeneNumber+'</span>';
						result = result + '<div class="clearfix"></div>';
						result = result + '</div>';

						result = result	+ '	<div class="project_details_cta" id="projectNameId">';
						result = result + '	<strong>Project name:</strong>';
						result = result + '	<span title="'+json.projectName+'">'+json.projectName+'</span><div class="clearfix"></div>';
						result = result + '	</div>';

						result = result + '<div class="project_details_cta">';
						result = result + '<strong>Client:</strong>';
						result = result + '<span title="'+clientName+'">'+clientName+'</span><div class="clearfix"></div>';
						result = result + '<div class="clearfix"></div>';
						result = result + '</div>';
						
						
						result = result	+ '<div class="project_details_cta">';
						result = result	+ '<strong>Team members:</strong><span>'+ json.projectMembersCount+ '</span><div class="clearfix"></div>';
						result = result + '</div>';
						result = result + '<div class="clearfix"></div>';
						result = result	+ '<div class="project_details_cta">';
						result = result + '<strong>Start date:</strong>';
						result = result + '<span title="'+json.startDateStr+'">'+json.startDateStr+'</span><div class="clearfix"></div>';
						result = result + '</div>';
						result = result	+ '<div class="project_details_cta">';
						result = result + '<strong>End date:</strong>';
						result = result + '<span title="'+endDateStr+'">'+endDateStr+'</span><div class="clearfix"></div>';
						result = result + '</div>';
						result = result + '<div class="clearfix"></div>';
						
						result = result + '<div class="project_details_cta">';
						result = result + '<strong>Allow Freelance Developers:</strong>';
						result = result + '<span>'+allowFreeLance+'</span><div class="clearfix"></div>';
						result = result + '<div class="clearfix"></div>';
						result = result + '</div>';

						result = result + '<div class="project_details_cta">';
						result = result + '<strong>Allow Offshore Resource:</strong>';
						result = result + '<span>'+allowOffShore+'</span><div class="clearfix"></div>';
						result = result + '<div class="clearfix"></div>';
						result = result + '</div>';
						
						result = result + '<div class="project_details_cta">';
						result = result + '<strong>Allow Remote Work:</strong>';
						result = result + '<span>'+allowRemote+'</span><div class="clearfix"></div>';
						result = result + '<div class="clearfix"></div>';
						result = result + '</div>';
						
						result = result + '<div class="project_details_cta">';
						result = result + '<strong>Time Entry after Deadline:</strong>';
						result = result + '<span>'+allowTimeEntryAfterDeadLine+'</span><div class="clearfix"></div>';
						result = result + '<div class="clearfix"></div>';
						result = result + '</div>';
						
						result = result + '<div class="project_details_cta">';
						result = result + '<strong>Allow Overtime:('+flatPercentageId+')</strong>';
						result = result + '<span>'+allowOvertime+'</span><div class="clearfix"></div>';
						result = result + '<div class="clearfix"></div>';
						result = result + '</div>';		
						
						result = result + '<div class="project_details_cta">';
						result = result + '<strong>Allow Bidding:</strong>';
						result = result + '<span>'+allowBidding+'</span><div class="clearfix"></div>';
						result = result + '<div class="clearfix"></div>';
						result = result + '</div>';
						
						result = result + '<div class="project_details_cta">';
						result = result	+ '<strong>Description:</strong>';
						result = result + '<span>'+ description +'</span><div class="clearfix"></div>';
						result = result + '</div>';
						
						result = result	+ '<div class="user_summary_edit user_summary_edit_a" id="editProjectDetailsDiv"></div>';
						result = result + '</div>';
						result = result + '</div>';
						result = result + ' </div>';
						result = result + '</div>';

						$("#tab-1").empty();
						$("#tab-2").empty();
						$("#tab-3").empty();
						$("#tab-4").empty();
						$("#tab-1").append(result);
						$("#tab-1").show();
						
						$(".project-tabs-menu a").click(
								function(event) {
									event.preventDefault();
									$(this).parent().addClass("current");
									$(this).parent().siblings().removeClass(
											"current");
									var tab = $(this).attr("href");
									$(".project_tabs_inn").not(tab).css(
											"display", "none");
									$(tab).fadeIn();
								});

					}
				});
	}
</script>
<script type="text/javascript">
	function editProjectDetails(projectId) {

		var sourceleadUrl = '${sourceleadUrl}';
		$.ajax({
					type : "POST",
					url : sourceleadUrl	+ "/getProjectById?${_csrf.parameterName}=${_csrf.token}&ProjectId="+ projectId,
					success : function(data) {
						var json = JSON.parse(data);

						var endDateStr = "";
						var description = "";
						var invoiceFrequency = "";
						
						var projectSequeneNumber = "";
						
                        var isTimesheetAttachmentRequired = "";
						
						
						if (json.isTimesheetAttachmentRequired != null && json.isTimesheetAttachmentRequired != '' && json.isTimesheetAttachmentRequired != 'undefined'
								&& json.isTimesheetAttachmentRequired == '1') {
							isTimesheetAttachmentRequired = "Y";
					     } else {
					    	 isTimesheetAttachmentRequired = "N";
					    }
						
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
						
						if (json.invoiceFrequency != null && json.invoiceFrequency != '' && json.invoiceFrequency != 'undefined') {
							invoiceFrequency = json.invoiceFrequency;
					     } else {
					    	 invoiceFrequency = "----";
					    }
						
						if (json.projectSequeneNumber != null
								&& json.projectSequeneNumber != ''
								&& json.projectSequeneNumber != 'undefiend') {
							projectSequeneNumber = json.projectSequeneNumber;
						} else {
							projectSequeneNumber = "";
						}

						var result = "";
						result = result	+ ' <form id="editProject_form"  name="editProject_form" method="post">';
						result = result	+ '<div class="user_summary_edit_cta" id="ProjectIdDiv">';
						result = result + '	<label>Project Id</label>';
						result = result	+ '	<input type="text"  id="ProjectSequeneNumber" name="ProjectSequeneNumber" value="'+projectSequeneNumber+'" placeholder="ProjectSequene Number" data-validation="validate_Space validate_length length2-50" data-validation-optional="true" onKeyUp="validateProjectSequeneNumber();" class="project_detfield">';
						result = result + '</div>';

						result = result + '<div class="user_summary_edit_cta">';
						result = result + '	<label>Status</label>';
						result = result	+ '<select id="projectStatusId" name="projectStatusId" onchange="projectStatus();">';
						if (json.projectStatus == 'STARTED') {
							result = result	+ '<option selected >Started</option>';
						} else {
							result = result + '<option>Started</option>';
						}

						if (json.projectStatus == 'INPROGRESS') {
							result = result	+ '<option selected>In-Progress</option>';
						} else {
							result = result + '<option>In-Progress</option>';
						}

						if (json.projectStatus == 'COMPLETED') {
							result = result	+ '<option selected>Completed</option>';
						} else {
							result = result + '<option>Completed</option>';
						}

						if (json.projectStatus == 'ONHOLD') {
							result = result	+ '<option selected>On-Hold</option>';
						} else {
							result = result + '<option>On-Hold</option>';
						}

						result = result + '</select>';
						result = result + '</div>';
						result = result + '<div class="user_summary_edit_cta">';
						result = result + '<label>Project name<span	class="text-danger">*</span></label>';
						result = result	+ '		<span><input type="text" id="projectName" name="projectName" value="'+json.projectName+'" placeholder="Project name" class="project_detfield" data-validation="required validate_Space validate_length length2-100" field-name="Project Name" ></span><span id=fname_msg></span><div class="clearfix"></div>';
						result = result + '</div>';
						result = result + '<div class="user_summary_edit_cta">';
						result = result + '	<label>Manager 1 <span class="text-danger">*</span></label>';
						result = result + '<div class="row">';
						 if ((json.manager2 != null && json.manager2 != "" && json.manager2 != "undefined") ||
								 (json.managerLevel1UserType != null && json.mamanagerLevel1UserTypenager2 != "" && json.managerLevel1UserType != "undefined" 
								 &&  json.managerLevel1UserType == "Admin")) {  
							result = result + '<div class="col-md-12" id="levelone-select">';
						 }else{
							result = result + '<div class="col-md-9" id="levelone-select">';	
						} 
						result = result	+ '<select onchange="getLevel2Approvers('+json.manager2+ ');" id="level1Manager" name="level1Manager" >';
						for (var j = 0; j < json.managersList.length; j++) {
							var manager = json.managersList[j];
							if (json.manager1 == manager.id) {
								selected = 'selected';
								result = result	+ "<option value="+manager.id+","+manager.userType+" "+selected+" >"	+ manager.firstName + " "+ manager.lastName + "</option>";
							} else {
								result = result	+ "<option value="+manager.id+","+manager.userType+" >"+ manager.firstName + " "+ manager.lastName + "</option>";
							}
						}
						result = result + '</select>';
						result = result + '</div>';
						
						if (json.managerLevel1UserType != null && json.mamanagerLevel1UserTypenager2 != "" && json.managerLevel1UserType != "undefined" &&  json.managerLevel1UserType != "Admin") { 
							if (json.manager2 != null && json.manager2 != "" && json.manager2 != "undefined") { 
								result = result + '<div class="col-md-2" id="level2Toggle" style="display:none">';
								result = result + '<span class="btn btn-default formic1 addLevel" onclick="getLevel2Approvers(); showLevel2Div();">Add Level-II</span>';
								result = result + '</div>';
								} else{
									result = result + '<div class="col-md-2" id="level2Toggle" style="display:block">';
									result = result + '<span class="btn btn-default formic1 addLevel" onclick="getLevel2Approvers(); showLevel2Div();">Add Level-II</span>';
									result = result + '</div>';
								} 
						}else{
							result = result + '<div class="col-md-2" id="level2Toggle" style="display:none">';
							result = result + '<span class="btn btn-default formic1 addLevel" onclick="getLevel2Approvers(); showLevel2Div();">Add Level-II</span>';
							result = result + '</div>';
						}
						result = result	+ '<input type="hidden" id="level2Div">';
						result = result + '</div>';
						
						result = result + '</div>';

						if (json.manager2 != null && json.manager2 != "" && json.manager2 != "undefined") { 
							result = result+ '<div class="user_summary_edit_cta" id="demo" >';
						}else{
							result = result+ '<div class="user_summary_edit_cta" style="display:none;" id="demo">';
						}
							result = result + '<label> Manager 2 </label>';
							result = result	+ '<select id="level2Manager" name="level2Manager">';
							result = result    +'<option value="">-Select Manager-</option>';
							for (var j = 0; j < json.managersListwithoutManager1.length; j++) {
								var manager = json.managersListwithoutManager1[j];
								if (json.manager2 == manager.id) {
									selected = 'selected';
									result = result	+ "<option value="+manager.id+" "+selected+" >"+ manager.firstName + " "+ manager.lastName + "</option>";
								} else {

									result = result	+ "<option value="+manager.id+" >"+ manager.firstName + " "+ manager.lastName + "</option>";
								}
							}
							result = result + '</select>';
							result = result + '</div>';

						result = result + '<div class="user_summary_edit_cta">';
						result = result + '	<label>Client</label>';

						if (json.relationId != null) {
							result = result	+ '<select id="clientId" disabled="disabled" name="clientId">';
						} else {
							result = result	+ '<select id="clientId" name="clientId">';
						}
						result = result + "<option value=''>Select</option>";
						for (var i = 0; i < json.clientName.length; i++) {
							var managers = json.clientName[i];
							var projrctmanager = managers.split(',');
							var name = projrctmanager[0];
							var id = projrctmanager[1];
							var selected = '';
							if (json.relationId == id) {
								selected = 'selected';
								result = result + "<option value="+id+" "+selected+" >"+name+ "</option>";
							} else {
								result = result + "<option value="+id+" >"+name+ "</option>";
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
						result = result + '	<label>Start date <span class="text-danger">*</span></label>';
						result = result
								+ '	<input class="calendar" value='+json.startDateStr+' type="text" disabled="disabled" id="startDateStr1">';
						result = result + '</div>	';
						
						
						/* result = result + '<div class="user_summary_edit_cta">';
						result = result + '<label>Invoice Frequency <span class="text-danger">*</span></label>';
						result = result  +'	<select   name="invoiceFrequency" id="invoiceFrequency" class="input-sm form-control">';
						result = result  +' <option '+(json.invoiceFrequency == "Weekly" ? "selected" : '') +'>Weekly</option>';
						result = result  +'	 <option '+(json.invoiceFrequency == "Bi-Weekly" ? "selected" : '') +'>Bi-Weekly</option>';
						result = result  +'	 <option '+(json.invoiceFrequency == "Semi-Monthly" ? "selected" : '') +'>Semi-Monthly</option>';
						result = result  +'	<option '+(json.invoiceFrequency == "Monthly" ? "selected" : '') +'>Monthly</option>';
						result = result  +'	</select>';
						result = result + '</div>'; */
						
						result = result + '<div class="clearfix"></div>';

						result = result + '<div class="user_summary_edit_cta" id="endDateDiv">';
						result = result + '<label>End date <span class="text-danger" id="endDateSpan" style="display: none; ">*</span></label>';
						result = result
								+ '	<input class="calendar" type="text" placeholder="DD/MM/YY" value="'+endDateStr+'" id="endDateStr1" name="endDateStr1" field-name="End Date" readonly="readonly">';
						result = result + '<input type="hidden" name="dbEndDateStr" id="dbEndDateStr" value="'+endDateStr+'" >';
						result = result + '</div>';
						
						
						

						result = result + '<div class="user_summary_edit_cta">';
						result = result + '<label>Description </label>';
						result = result
								+ '<textarea class="edit_desc" placeholder="Enter Description" id="description" data-validation-optional="true" data-validation="validate_length length10-5000" name="description">'
								+ description + '</textarea>';
						result = result + '</div>       ';
						result = result + '<div class="clearfix"></div>';
						
						result = result + '<div class="form-group">';
						result = result + '<div class="col-md-9">';
						result = result + '<div class="checkbox">';
						result = result + '<label> <input type="checkbox" value="Y" '+(isTimesheetAttachmentRequired == "Y" ? "checked" : '')+'  name="isTimesheetAttachmentRequired"> Timesheet Attachment Required</label>';
						result = result + '</div>';
						result = result + '</div>';
						result = result + '</div>';
					result = result + '<div class="clearfix"></div>';
						
						result = result + '<div class="user_summary_edit_cta pull-right">';
						
						result = result	+ '	<input class="us-btn" value="Save" onclick = "updateProjectDetails('+projectId+ ');"type="button"> <input id="btnClose" class="us-btn us-btn_a" value="Cancel" onClick="editProjectDetails('+ projectId + ');" type="button">';
						result = result + '</div>		';
						result = result + '<div class="clearfix"></div>';
						result = result + ' </form>';

						$(".user_summary_edit").slideToggle(100);
						$("#editProjectDetailsDiv").empty();
						$("#editProjectDetailsDiv").append(result);

						//Assign date of project member
						var selected = json.startDateStr;
						var dt = toDate("#startDateStr1");
						dt.setDate(dt.getDate());

						//Main project end date
						var projectEndDate = json.endDateStr;
						$('#endDateStr1').datepicker({
							dateFormat : 'dd-mm-yy',
							showOn: 'focus',
				            showButtonPanel: true,
				            closeText: 'Clear', 
							changeMonth: true,
							changeYear: true, 
							minDate : dt,
							onClose : function(selected) {
								addValidClass();
								var event = arguments.callee.caller.caller.arguments[0];
				                // If "Clear" gets clicked, then really clear it
				                if ($(event.delegateTarget).hasClass('ui-datepicker-close')) {
				                    $(this).val('');
				                }
							}
						});

					},
				});

	}
</script>

<script>
function projectStatus(){
	var projectStatus = $("#projectStatusId").val();
	var dbProjectStatus = $("#dbProjectStatus").val();
	var dbEndDateStr = $("#dbEndDateStr").val();
	if((projectStatus == "Started" || projectStatus == "In-Progress" || projectStatus == "On-Hold") && dbProjectStatus == "COMPLETED"){
		 $("#endDateStr1").val('');
	}else{
		$("#endDateStr1").val(dbEndDateStr);
	}
	
	$('#endDateDiv').find('p.jquery_form_error_message').remove(); 
	if(projectStatus != null && projectStatus != "" && projectStatus != "undefined" && projectStatus == "Completed"){
		 $("#endDateSpan" ).show(); 
		 $('#endDateStr1').removeAttr('data-validation-optional');
		 $('#endDateStr1').attr("data-validation","required"); 
	 }else{
		 $("#endDateSpan" ).hide();
		 $('#endDateStr1').removeAttr('data-validation');
		 $('#endDateStr1').attr("data-validation-optional","true"); 
	 } 
}
</script>

<script type="text/javascript"> 
	function addValidClass(){
		 var EndDate = document.getElementById("endDateStr1").value;
		 if(EndDate){
			 $('#endDateDiv').find('p.jquery_form_error_message').remove(); 
		 }
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
							$("#selector2").removeAttr("style");
	            			$("#selector2").css({ display: "block" });
	            			$("#docMsgDiv2").html("Success!! Project details updated");
							projectDetails(projectId);
							docMsgFadein();
							
							var json = JSON.parse(data);
							$("#empTab").removeAttr("onClick");
			   		    	$("#empTab").attr("onclick","projectEmployee('"+projectId+"', '"+json.startDateStr+"', '"+json.endDateStr+"', '"+json.projectStatus+"', '"+json.projectName+"')");
			   		    	
			   		    	$("#taskTab").removeAttr("onClick");
			   		    	$("#taskTab").attr("onclick","projectTask('"+projectId+"', '"+json.projectStatus+"')");
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
							url : sourceleadUrl + "/validateProjectSequeneNumberById?${_csrf.parameterName}=${_csrf.token}&ProjectSequeneNumber=" + ProjectSequeneNumber,
							success : function(data) {
								if (data == true) {
									$("input#ProjectSequeneNumber") .after( "<p class='jquery_form_error_message'>" + ProjectSequeneNumber + " is Already Existed</span>");
									document .getElementById( "ProjectSequeneNumber") .setAttribute('record-exist', 'yes');
									document .getElementById( "ProjectSequeneNumber") .setAttribute( 'record-exist-errorMsg', ProjectSequeneNumber + ' is Already Existed');
								} else {
									$('input#ProjectSequeneNumber') .removeAttr( "record-exist record-exist-errormsg");
									$('#ProjectIdDiv').find( 'p.jquery_form_error_message') .remove();
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
function showLevel2Div(){
	var currentApproverId = document.getElementById("level1Manager").value;
	var currentUser = currentApproverId.split(',');
	var currentUserType = currentUser[1];
	if(currentUserType != null && currentUserType != '' && currentUserType !='undefined' && currentUserType != 'Admin'){
	    $("#demo").show();  
	}else if(currentUserType != null && currentUserType != '' && currentUserType !='undefined' && currentUserType == 'Admin'){
	    $("#demo").hide();  
	}
}

</script>

<script type="text/javascript">
	function getLevel2Approvers(manager2) {
		var currentApproverId = document.getElementById("level1Manager").value;
		var currentUser = currentApproverId.split(',');
		var currentUserId = currentUser[0];
		var currentUserType = currentUser[1];
		$("#level2Manager").empty();    
		$('#level2Manager').val('');
		if(currentUserType != null && currentUserType != '' && currentUserType !='undefined' && currentUserType != 'Admin'){
		    if(manager2 == null || manager2 == "" || manager2 == "undefined"){
		    	$("#level2Toggle").show();
		    	$("#levelone-select").removeClass('col-md-12');
			    $("#levelone-select").addClass('col-md-9');  
		    }else{
		    	$("#demo").show();
		    }
		    getChildLevel1Approvers(currentUserId, manager2);
		}else if(currentUserType != null && currentUserType != '' && currentUserType !='undefined' && currentUserType == 'Admin'){
		    $("#level2Toggle").hide();   
		    $("#levelone-select").removeClass('col-md-9');
		    $("#levelone-select").addClass('col-md-12');  
		    $("#demo").hide();  
		}
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
							url : sourceleadUrl + "/getApproversByUserId?${_csrf.parameterName}=${_csrf.token}&currentApproverId=" + currentApproverId,
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
<script>
	function docMsgFadein() {
		$("#selector2").fadeIn(200);
        $('#selector2').delay(2000).fadeOut('slow');
	}
</script> 

<!-- script for converting dateformat in datepicker -->
<script>
function toDate(selector) {
    var from = $(selector).val().split("-");
    return new Date(from[2], from[1] - 1, from[0]);
}
</script>
<!-- script for dateformat in datepicker End -->
