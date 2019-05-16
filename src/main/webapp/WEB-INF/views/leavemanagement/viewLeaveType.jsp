<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<script src="https://cdn.jsdelivr.net/g/mark.js(jquery.mark.min.js)"></script>
<script
	src="https://cdn.datatables.net/plug-ins/1.10.13/features/mark.js/datatables.mark.js"></script>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/datatables.mark.js/2.0.0/datatables.mark.min.css"
	type="text/css" media="all" />
<link rel="stylesheet"
	href="https://cdn.datatables.net/plug-ins/1.10.13/features/mark.js/datatables.mark.min.css"
	media="all" />

<script type="text/javascript">
	$(document).ready(function() {
		getLeaveTypesList();
	});
</script>

<script>
var pmsUrl ='${SourceLeadWeb_pmsUrl}';
</script>

<!-- Content Area -->
<section class="content-wraper" id="themeroller">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Leave Type</h2>
		</div>
		<div class="pull-right brud-crum">
			<a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a>&raquo;
			<a href="${SourceLeadWeb_sourceleadUrl}/settings">Settings</a>&raquo;
			<a href="${SourceLeadWeb_pmsUrl}/leaveConfiguration">Leave
				Configuration</a> &raquo; Leave Type
		</div>
	</section>

	<div class="content-area content-new clearfix">

		<div class="timesheet_out">
			<div class="clearfix">
				<div class="success_pop" id="selector1" style="display: none;">
					<i class="fa fa-check-circle"></i><span id="msgDiv"></span>
				</div>
				<div class="timesheet_head">
					<ul>
						<li><a href="${SourceLeadWeb_pmsUrl}/addLeaveType"
							title="Add Leave Type"> <i class="fa fa-plus paddingRight"></i>Add
								Leave Type
						</a></li>
						<div class="clearfix"></div>
					</ul>
					<div class="clearfix"></div>
				</div>
				<div class="side_mask" style="display: none;">
					<div class="proj_close"></div>
				</div>
				<div id="typeSummaryDiv"></div>
				<div class="clients_wrap_table datatable_filter">
					<div id="loading"></div>
					<div id="leaveTypeListTableDiv"></div>
				</div>

			</div>
		</div>

	</div>
	<!-- Content Area Ends-->
	<div id="popUp"></div>
</section>
<!-- Content Area Ends-->


<script>
	function getLeaveTypesList() {
		var locationId = '${SourceLeadWeb_orgLocationId}';
		$("#loading")
				.attr(
						'style',
						'position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:999;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
				$.get(
						pmsUrl
								+ "/getLeaveTypesByLocationid?&orgLocationId="
								+ locationId,
						function(response) {
							if (response.response === "AWKWARD") {
								location.href = "/errorPage";
							} else if (response.response === "SUCCESS") {
								var result = "";
								result += '<table id="leaveTypeListTable" class="display" cellspacing="0" width="100%" >';
								result += '<thead>';
								result += '  <tr>';
								result += '    <th>Name</th>';
								result += '    <th>Duration</th>';
								result += '    <th>Type</th>';
								// 	         result += '    <th>No.of Leaves</th>';
								result += '    <th>Status</th>';
								result += '    <th>Action</th>';
								result += '  </tr>';
								result += '</thead>';
								result += '<tbody>';

								for (i = 0; i < response.data.length; i++) {
									var report = response.data[i];
									result += '<tr>';
									result += '    <td>';
									if (report.name != null
											&& report.name != ''
											&& report.name != 'undefined') {
										result += '      <div class="proj_show tbldata-dotdot-big" onclick="openLeaveTypeSummary(\''
												+ report.leaveTypeUUID
												+ '\');">';
										result += '          <div class="pull-left tbldata-dotdot-me" title="'+report.name+'" >'
												+ report.name + '</div>';
										result += '          <i class="fa fa-binoculars pull-right"></i>';
										result += '      </div>';
									}
									result += '    </td>';
									result += '    <td>';
									if (report.duration != null
											&& report.duration != ''
											&& report.duration != 'undefined') {
										result += '        <div title="'+report.duration+'" class="tblminwidth-75">'
												+ report.duration + '</div>';
									}
									result += '    </td>';
									result += '    <td>';
									if (report.leaveType != null
											&& report.leaveType != ''
											&& report.leaveType != 'undefined') {
										result += '      <div title="'+report.leaveType+'" class="tbldata-dotdot-100">'
												+ report.leaveType + '</div>';
									}
									result += '    </td>';
									/* result += '    <td>';
									if(report.numbefrOfLeavesPeryear != null && report.numbefrOfLeavesPeryear != '' && report.numbefrOfLeavesPeryear != 'undefined'){
									 result += '        <div title="'+report.numbefrOfLeavesPeryear+'" class="tblminwidth-75">'+report.numbefrOfLeavesPeryear+'</div>'; 
									}
									result += '    </td>'; */
									result += '    <td>';
									if (report.status != null
											&& report.status != ''
											&& report.status != 'undefined') {
										if (report.status == 1) {
											result += '        <div title="Active" class="tblminwidth-75"><b class="proj_open">Active</b></div>';
										} else {
											result += '        <div title="In Active" class="tblminwidth-75"><b class="proj_closed">In Active</b></div>';
										}

									}
									result += '    </td>';
									result += '    <td>';
									result += '        <div class="tblminwidth-50">';
									result += '            <span title="Edit" class="paddingRight"><a href="'
											+ pmsUrl
											+ '/editLeaveType?leaveTypeUUID='
											+ report.leaveTypeUUID
											+ '"><i class="fa fa-pencil text-primary cursorPointer"></i></a></span> ';
									if (report.status == 1) {
										result += '            <span title="Delete" class="paddingLeft"><i class="fa fa-trash text-danger cursorPointer" onclick="deleteLeaveType(\''
												+ report.leaveTypeUUID
												+ '\')"></i></span>';
									} else {
										result += '            <span id="spin'+report.leaveTypeUUID+'" title="Reactive" class="paddingLeft"><i class="fa fa-repeat text-primary cursorPointer" onclick="reactiveLeaveType(\''
												+ report.leaveTypeUUID
												+ '\')"></i></span>';
									}
									result += '        </div>';
									result += '    </td>';
									result += '  </tr>';
								}

								result += '  </tbody>                                                                                                         ';
								result += '</table> ';
								$("#leaveTypeListTableDiv").empty();
								$("#leaveTypeListTableDiv").append(result);
								$("#loading").removeAttr('style');
								listTypePagination();

								$(".proj_show").click(
										function() {
											$(".new_side_wrap").addClass(
													"new_side_wrap_active");
											$(".side_mask").fadeIn(200);
											$("body").css('overflow-y',
													'hidden');
										});

							} else if (response.indexOf('Sign In') !== -1) {
								location.replace(pmsUrl);
							} else {
								showError("Operation failed, please try again");
							}
						});
	}
</script>

<script>
	function listTypePagination() {
		$('#leaveTypeListTable').DataTable({
			"scrollCollapse" : false,
			"lengthMenu" : [ [ 10, 15, 20, -1 ], [ 10, 15, 20, "All" ] ],
			"bLengthChange" : true,
			"bDestroy" : true,
			"ordering" : true,
			"mark" : true,
			"oLanguage" : {
				"sSearch" : "",
				"sZeroRecords" : "",
				"sEmptyTable" : ""
			}
		});
		$("#leaveTypeListTable").wrap(
				"<div class='overflow_Auto' style='width:100%;'></div>");

	}
</script>

<script>
	function deleteLeaveType(typeId) {
		$(".project_inn_mask").remove();
		$("#deleteLeaveTypeDiv").empty();
		var result = "";
		result = result + "	<div class='project_inn_mask'></div>";
		result = result
				+ "	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a positionRelative'>";
		result = result + "	<div id='leaveTypeReasonLoad'></div>";
		result = result
				+ "		<textarea id='reason' class='form-control' field-name='Reject Reason'  data-validation='required validate_Space validate_Description validate_length length2-250' name='reason' value='' placeholder='Reason' rows='2' onclick='validateReason();'></textarea>";
		result = result + ' <div id="deleteLeaveTypeDiv"/>';
		result = result
				+ "		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Delete' onclick='deleteType(\""
				+ typeId
				+ "\");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelRejectPopup();'></b>";
		result = result + "	</div>";
		$("#popUp").append(result);
		$("#remove_user_pop").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
	}

	function deleteType(typeId) {
		var loginUserId = '${SourceLeadWeb_user.id}'
		var reason = document.getElementById("reason").value;
		reason = reason.replace(/\s/g, "")
		if (reason.match(/^[^\s-](.*)*$/) != null) {
			$("#deleteLeaveTypeDiv").empty();
			$("#leaveTypeReasonLoad")
					.attr(
							'style',
							'position:absolute; width:100%; height:150px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			$.ajax({
				type : "POST",
				url : pmsUrl + "/deleteLeaveType?" + tokenParamName
						+ "=" + tokenValue + "&leaveTypeUUID=" + typeId
						+ "&deleteReason=" + reason + "&loginUserId="
						+ loginUserId,
				success : function(result) {
					if (result.response === "SUCCESS") {
						location.replace(pmsUrl + "/viewLeaveTypes");
						$("#leaveTypeReasonLoad").removeAttr("style");
						$("#selector1").removeAttr("style");
						$("#selector1").css({
							display : "block"
						});
						$("#msgDiv").html("Leave Type removed");
						$("#popUp").empty();
						$(".project_inn_mask").fadeOut(200);
						msgFadein();
					} else if (result.indexOf('Sign In') !== -1) {
						location.replace(pmsUrl);
					} else {
						showError("Operation failed, please try again");
					}

				}
			});
		} else {
			if (reason.length == 0) {
				$("#deleteLeaveTypeDiv")
						.empty()
						.append(
								"<font color='red' style='padding: 5px 10px; '>Reason is required</font>");
			} else {
				$("#deleteLeaveTypeDiv")
						.empty()
						.append(
								"<font color='red' style='padding: 5px 10px; '>Reason is required</font>");
			}
		}
	}

	function reactiveLeaveType(typeId, from) {

		$("#spin" + typeId).empty();
		$("#spin" + typeId).prepend('<i class="fa fa-spinner fa-spin"></i>');

		var loginUserId = '${SourceLeadWeb_user.id}'
		$.ajax({
			type : "POST",
			url : pmsUrl + "/deleteLeaveType?" + tokenParamName + "="
					+ tokenValue + "&leaveTypeUUID=" + typeId
					+ "&status=reactive",
			success : function(result) {
				if (result.response === "SUCCESS") {
					if (from == "summary") {
						openLeaveTypeSummary(typeId);
						getLeaveTypesList();
					} else {
						location.replace(pmsUrl + "/viewLeaveTypes");
					}
				} else if (result.indexOf('Sign In') !== -1) {
					location.replace(pmsUrl);
				} else {
					showError("Operation failed, please try again");
				}
			}

		});
	}

	function validateReason() {
		var output = document.getElementById("reason").value;
		output = output.replace(/\s/g, "")
		if (output.length != 0) {
		}
	}

	function cancelRejectPopup() {
		$("#remove_user_pop, .project_inn_mask").fadeOut();
		$("#reason").val('');
		$("#reason").find("p.jquery_form_error_message").remove();
		$("#reason").empty();

	}

	function msgFadein() {
		$("#selector1").fadeIn(200);
		$('#selector1').delay(2000).fadeOut('slow');
	}
</script>

<script>
	function openLeaveTypeSummary(leaveTypeUUID) {
		$(".new_side_wrap").addClass("new_side_wrap_active");
		$(".side_mask").fadeIn(200);
		$("body").css('overflow-y', 'hidden');
		$
				.ajax({
					type : "POST",
					url : pmsUrl + "/leaveTypeSummary?&leaveTypeUUID="
							+ leaveTypeUUID,
					success : function(response) {
						if (response.response === "AWKWARD") {
							location.href = "/errorPage";
						} else if (response.response === "SUCCESS") {
							/* if(response !=null && response !=''){
							 for (var i=0; i<response.data.length; i++) { 
							   		 var report = response.data[i]; */
							var report = response.object;
							var result = "";
							result += '<div class="new_side_wrap">';
							result += '  <div class="clients_side_wrap_inn">';
							result += '      <div class="clerfix showDetails">';
							result += '      <div class="project_tabs minHeight_225 form-horizontal modal-body">';

							result += '          <div class="viewgrid-header pull-right zIndex">';
							if (report.status == 1) {
								result += '            <a href="javascript:void(0)" class= "btn btn-primary btn-sm mr10" title="Active">Active</a>';
							} else {
								result += '            <a class= "btn btn-danger btn-sm mr10" onclick="reactiveLeaveType(\''
										+ report.leaveTypeUUID
										+ '\', \'summary\')" title="Re-Active">Re-Active</a>';
							}
							result += '              <a href="'
									+ pmsUrl
									+ '/editLeaveType?leaveTypeUUID='
									+ leaveTypeUUID
									+ '" class="btn btn-default btn-sm" title="Edit"><i class="fa fa-pencil paddingRight"></i>Edit</a> ';
							result += '          </div> ';

							result += '          <div class="row">';
							result += '              <div class="col-md-12">';
							result += '                <h4>Details</h4>';
							result += '              </div>';
							result += '              <div class="clearfix">';
							result += '              <div class="col-md-5 mb">';
							result += '                  <label class="col-md-5 control-label"><b>Name:</b></label>';
							if (report.name != null && report.name != ''
									&& report.name != 'undefined') {
								result += '                  <div class="col-md-7">';
								result += '                      <span>'
										+ report.name + '</span>';
								result += '                  </div>';
							}

							result += '              </div>';
							result += '              <div class="col-md-5 mb">';
							result += '                  <label class="col-md-5 control-label"><b>Duration:</b></label>';
							result += '                  <div class="col-md-7">';
							if (report.duration != null
									&& report.duration != ''
									&& report.duration != 'undefined') {
								result += '                      <span>'
										+ report.duration + '</span>';
							} else {
								result += '                      <span>---</span>';
							}
							result += '                  </div>';
							result += '              </div>';
							result += '              <div class="col-md-5 mb">';
							result += '                  <label class="col-md-5 control-label"><b>Type:</b></label>';
							result += '                  <div class="col-md-7">';
							if (report.leaveType != null
									&& report.leaveType != ''
									&& report.leaveType != 'undefined') {
								result += '                      <span>'
										+ report.leaveType + '</span>';
							} else {
								result += '                      <span>---</span>';
							}
							result += '                  </div>';
							result += '              </div>';
							// 			result +='              <div class="col-md-12 mb">';
							// 			result +='                  <label class="col-md-2 control-label"><b>No. of Leaves Per Year:</b></label>';
							// 			result +='                  <div class="col-md-8">';
							// 			if(report.numbefrOfLeavesPeryear != null && report.numbefrOfLeavesPeryear != '' && report.numbefrOfLeavesPeryear != 'undefined'){
							// 				result +='                      <span>'+report.numbefrOfLeavesPeryear+'</span>';
							// 			}else{
							// 				result +='                      <span>---</span>';
							// 			}
							// 			result +='                  </div>';
							// 			result +='              </div>';
							result += '              <div class="col-md-5 mb">';
							result += '                  <label class="col-md-5 control-label"><b>Applicable To All Users:</b></label>';
							result += '                  <div class="col-md-7">';
							if (report.applicableToUsers != null
									&& report.applicableToUsers != ''
									&& report.applicableToUsers != 'undefined') {
								if (report.applicableToUsers == 'Y') {
									result += '                      <span>Yes</span>';
								} else {
									result += '                      <span>No</span>';
								}
								result += '                  </div>';
							} 
							 
							result += '              </div>';
							
							/* if (report.departmentId != null
									&& report.departmentId != ''
									&& report.departmentId != 'undefined') {
								result += '              <div class="col-md-12 mb">';
								result += '                  <label class="col-md-2 control-label"><b style="color:#808080;"><i class="fa fa-level-up paddingRight"></i>Select Department:</b></label>';
								result += '                  <div class="col-md-8">';
								result += '                      <span>'
										+ report.departmentId + '</span>';
								result += '                  </div>';

								result += '              </div>';
							}
							
							if (report.departmentId != null
									&& report.departmentId != ''
									&& report.departmentId != 'undefined') {
								result += '              <div class="col-md-12 mb">';
								result += '                  <label class="col-md-2 control-label"><b style="color:#808080;"><i class="fa fa-level-up paddingRight"></i>Select Department:</b></label>';
								result += '                  <div class="col-md-8">';
								result += '                      <span>'
										+ report.departmentId + '</span>';
								result += '                  </div>';

								result += '              </div>';
							}
							
							if (report.roleId != null
									&& report.roleId != ''
									&& report.roleId != 'undefined') {
								result += '              <div class="col-md-12 mb">';
								result += '                  <label class="col-md-2 control-label"><b style="color:#808080;"><i class="fa fa-level-up paddingRight"></i>Select Department:</b></label>';
								result += '                  <div class="col-md-8">';
								result += '                      <span>'
										+ report.roleId + '</span>';
								result += '                  </div>';

								result += '              </div>';
							} */
							
							result += '              <div class="col-md-5 mb">';
							
							result += '              <div class="col-md-12 mb">';
							result += '                  <label class="col-md-5 control-label"><b>Leaves Carryforward:</b></label>';
							result += '                  <div class="col-md-7">';
							if (report.leavesCarryforward != null
									&& report.leavesCarryforward != ''
									&& report.leavesCarryforward != 'undefined') {
								if (report.leavesCarryforward == 'Y') {
									result += '                      <span>Yes</span>';
								} else {
									result += '                      <span>No</span>';
								}
								result += '                  </div>';
							} 
							 
							result += '              </div>';
							
							if (report.maxDaysToCarryforward != null
									&& report.maxDaysToCarryforward != ''
									&& report.maxDaysToCarryforward != 'undefined') {
								result += '              <div class="col-md-12 mb">';
								result += '                  <label class="col-md-5 control-label"><b style="color:#808080;"><i class="fa fa-level-up paddingRight"></i>Max no of Days to Carry forward:</b></label>';

								result += '                  <div class="col-md-7">';
								result += '                      <span>'
										+ report.maxDaysToCarryforward + '</span>';
								result += '                  </div>';

								result += '              </div>';
							}
							result += '              </div>';
							
							
							result += '              <div class="col-md-5 mb">';
							result += '                  <label class="col-md-5 control-label"><b>Description:</b></label>';
							result += '                  <div class="col-md-7">';
							if (report.roleId != null
									&& report.description != ''
									&& report.description != 'undefined') {
								result += '                      <span>'
										+ report.description + '</span>';
							} else {
								result += '                      <span>---</span>';
							}
							result += '                  </div>';
							result += '              </div>';
							
							
							result += '              <div class="col-md-5 mb">';
							result += '              <div class="col-md-12 mb">';
							result += '                  <label class="col-md-5 control-label"><b>Calculation Period:</b></label>';
							if (report.period != null && report.period != ''
									&& report.period != 'undefined') {
								result += '                  <div class="col-md-7">';
								result += '                      <span>'
										+ report.period + '</span>';
								result += '                  </div>';
							}
							result += '              </div>';

							if (report.noOfDays != null
									&& report.noOfDays != ''
									&& report.noOfDays != 'undefined') {
								result += '              <div class="col-md-12 mb">';
								result += '                  <label class="col-md-5 control-label"><b style="color:#808080;"><i class="fa fa-level-up paddingRight"></i>No Of Days Annualy:</b></label>';

								result += '                  <div class="col-md-7">';
								result += '                      <span>'
										+ report.noOfDays + '</span>';
								result += '                  </div>';

								result += '              </div>';
							}
							
							if (report.noOfDaysPerPeriod != null
									&& report.noOfDaysPerPeriod != ''
									&& report.noOfDaysPerPeriod != 'undefined') {
								result += '              <div class="col-md-12 mb">';
								result += '                  <label class="col-md-5 control-label"><b style="color:#808080;"><i class="fa fa-level-up paddingRight"></i>No Of Days per Period:</b></label>';

								result += '                  <div class="col-md-7">';
								result += '                      <span>'
										+ report.noOfDaysPerPeriod + '</span>';
								result += '                  </div>';

								result += '              </div>';
							}
							result += '              </div>';
							
							
							result += '              <div class="col-md-5 mb">';
							result += '              <div class="col-md-12 mb">';
							result += '                  <label class="col-md-5 control-label"><b style="color:#101010;">Leave Effective:</b></label>';
							if (report.leaveEffective != null
									&& report.leaveEffective != ''
									&& report.leaveEffective != 'undefined') {
								result += '                  <div class="col-md-7">';
								result += '                      <span>'
										+ report.leaveEffective + '</span>';
								result += '                  </div>';
							}

							result += '              </div>';

							if (report.newJoinee != null && report.newJoinee != '' && report.newJoinee != 'undefined') {
								result += '              <div class="col-md-12 mb">';
								result += '                  <label class="col-md-5 control-label"><b style="color:#808080;"><i class="fa fa-level-up paddingRight"></i>New Joinee:</b></label>';
								result += '                  <div class="col-md-7">';
								result += '                      <span>' + report.newJoinee + ' day(s) / Annually</span>';
								result += '                  </div>';
								result += '              </div>';
								
								result += '              <div class="col-md-12 mb">';
								result += '                  <label class="col-md-5 control-label"><b style="color:#808080;"><i class="fa fa-level-up paddingRight"></i>New Joinee Per Period:</b></label>';
								result += '                  <div class="col-md-7">';
								result += '                      <span>' + report.newJoinPerPeriod + ' day(s) / Period</span>';
								result += '                  </div>';
								result += '              </div>';
							}

							if ((report.experiencedType != null
									&& report.experiencedType != '' && report.experiencedType != 'undefined')
									&& (report.experiencedDuration != null
											&& report.experiencedDuration != '' && report.experiencedDuration != 'undefined')
									&& (report.experiencedApplicableDays != null
											&& report.experiencedApplicableDays != '' && report.experiencedApplicableDays != 'undefined')) {
								result += '              <div class="col-md-12 mb">';
								result += '                  <label class="col-md-5 control-label"><b style="color:#808080;"><i class="fa fa-level-up paddingRight"></i>Experienced:</b></label>';
								result += '                  <div class="col-md-7">';
								result += '                      <span>'
										+ report.experiencedDuration
										+ '</span>';
								result += '                      <span>'
										+ report.experiencedType + '</span>'
								result += '                      <span>/'
										+ report.experiencedApplicableDays
										+ 'day(s)</span>';

								result += '                  </div>';

								result += '              </div>';
								
								result += '              <div class="col-md-12 mb">';
								result += '                  <label class="col-md-5 control-label"><b style="color:#808080;"><i class="fa fa-level-up paddingRight"></i>Experienced Per Period:</b></label>';
								result += '                  <div class="col-md-7">';
								result += '                      <span>' + report.experiencedPerPeriod + ' day(s) / Period</span>';
								result += '                  </div>';
								result += '              </div>';
								
							}
							    result += '              </div>';
							
							result += '              <div class="col-md-5 mb">';    
							    
							result += '              <div class="col-md-12 mb">';
							result += '                  <label class="col-md-5 control-label"><b>Auto Approve:</b></label>';
							if (report.autoApprove != null
									&& report.autoApprove != ''
									&& report.autoApprove != 'undefined') {
								result += '                  <div class="col-md-7">';
								if (report.autoApprove == 'Y') {
									result += '                      <span>Yes</span>';
								} else {
									result += '                      <span>No</span>';
								}
								result += '                  </div>';
							}
							result += '              </div>';

							if (report.autoApprove == 'Y') {
								result += '              <div class="col-md-12 mb">';
								result += '                  <label class="col-md-5 control-label"><b>Auto Approve Type:</b></label>';
								if (report.autoApproveType != null
										&& report.autoApproveType != ''
										&& report.autoApproveType != 'undefined') {
									result += '                  <div class="col-md-7">';
									result += '                      <span>By '
											+ report.autoApproveType
											+ '</span>';
									result += '                  </div>';
								}
								result += '              </div>';

								result += '              <div class="col-md-12 mb">';
								result += '                  <label class="col-md-5 control-label"><i class="fa fa-level-up paddingRight"></i></label>';
								if (report.autoApproveTypeListNames != null
										&& report.autoApproveTypeListNames != ''
										&& report.autoApproveTypeListNames != 'undefined') {
									result += '                  <div class="col-md-7">';
									result += '                      <span>'
											+ report.autoApproveTypeListNames
											+ '</span>';
									result += '                  </div>';
								}
								result += '              </div>';
							}
							result += '              </div>';
							
							result += '              <div class="col-md-5 mb">';
							result += '                  <label class="col-md-5 control-label"><b>Reason Required:</b></label>';
							if (report.reasonRequired != null
									&& report.reasonRequired != ''
									&& report.reasonRequired != 'undefined') {
								result += '                  <div class="col-md-7">';
								if (report.reasonRequired == 'Y') {
									result += '                      <span>Yes</span>';
								} else {
									result += '                      <span>No</span>';
								}
								result += '                  </div>';
							}
							result += '              </div>';

							result += '              <div class="col-md-5 mb">';
							result += '                  <label class="col-md-5 control-label"><b>Effective Gender:</b></label>';
							if (report.effectiveGender != null
									&& report.effectiveGender != ''
									&& report.effectiveGender != 'undefined') {
								result += '                  <div class="col-md-7">';
								result += '                      <span>'
										+ report.effectiveGender + '</span>';
								result += '                  </div>';
							}
							result += '              </div>';
							
							result += '              <div class="col-md-5 mb">';
							result += '                  <label class="col-md-5 control-label"><b>Allowability:</b></label>';
							if (report.allowability != null
									&& report.allowability != ''
									&& report.allowability != 'undefined') {
								result += '                  <div class="col-md-7">';
								result += '                      <span>'
										+ report.allowability + '</span>';
								result += '                  </div>';
							}
							result += '              </div>';

							result += '              <div class="col-md-5 mb">';
							result += '                  <label class="col-md-5 control-label"><b>Attachment Required:</b></label>';
							if (report.attachmentRequired != null
									&& report.attachmentRequired != ''
									&& report.attachmentRequired != 'undefined') {
								result += '                  <div class="col-md-7">';
								if (report.attachmentRequired == 'Y') {
									result += '                      <span>Yes</span>';
								} else {
									result += '                      <span>No</span>';
								}
								result += '                  </div>';
							}

							result += '              </div>';

							result += '              <div class="col-md-5 mb">';
							result += '                  <label class="col-md-5 control-label"><b>TimeSheet Autofill:</b></label>';
							if (report.timeSheetAutofill != null
									&& report.timeSheetAutofill != ''
									&& report.timeSheetAutofill != 'undefined') {
								result += '                  <div class="col-md-7">';
								if (report.timeSheetAutofill == 'Y') {
									result += '                      <span>Yes</span>';
								} else {
									result += '                      <span>No</span>';
								}
								result += '                  </div>';
							}
							result += '              </div>';
							
							result += '              <div class="col-md-5 mb">';
							result += '                  <label class="col-md-5 control-label"><b>Include Week off:</b></label>';
							if (report.includeWeekoff != null
									&& report.includeWeekoff != ''
									&& report.includeWeekoff != 'undefined') {
								result += '                  <div class="col-md-7">';
								if (report.includeWeekoff == 'Y') {
									result += '                      <span>Yes</span>';
								} else {
									result += '                      <span>No</span>';
								}
								result += '                  </div>';
							}
							result += '              </div>';
							
							result += '              <div class="col-md-5 mb">';
							result += '                  <label class="col-md-5 control-label"><b>Include Holiday:</b></label>';
							if (report.includeHolidays != null
									&& report.includeHolidays != ''
									&& report.includeHolidays != 'undefined') {
								result += '                  <div class="col-md-7">';
								if (report.includeHolidays == 'Y') {
									result += '                      <span>Yes</span>';
								} else {
									result += '                      <span>No</span>';
								}
								result += '                  </div>';
							}
							result += '              </div>';
							
							result += '              <div class="col-md-5 mb">';
							result += '                  <label class="col-md-5 control-label"><b>Visible in Balance Leaves:</b></label>';
							if (report.visibleInBalanceLeaves != null
									&& report.visibleInBalanceLeaves != ''
									&& report.visibleInBalanceLeaves != 'undefined') {
								result += '                  <div class="col-md-7">';
								if (report.visibleInBalanceLeaves == 'Y') {
									result += '                      <span>Yes</span>';
								} else {
									result += '                      <span>No</span>';
								}
								result += '                  </div>';
							}
							result += '              </div>';
							
							result += '              <div class="col-md-5 mb">';
							result += '                  <label class="col-md-5 control-label"><b>Include in PayRoll:</b></label>';
							if (report.includeInPayroll != null
									&& report.includeInPayroll != ''
									&& report.includeInPayroll != 'undefined') {
								result += '                  <div class="col-md-7">';
								if (report.includeInPayroll == 'Y') {
									result += '                      <span>Yes</span>';
								} else {
									result += '                      <span>No</span>';
								}
								result += '                  </div>';
							}
							result += '              </div>';
							
							
							result += '              <div class="col-md-5 mb">';
							result += '              <div class="col-md-12 mb">';
							result += '                  <label class="col-md-5 control-label"><b>Leave En-cashment:</b></label>';
							if (report.leaveEncashment != null
									&& report.leaveEncashment != ''
									&& report.leaveEncashment != 'undefined') {
								result += '                  <div class="col-md-7">';
								if (report.leaveEncashment == 'Y') {
									result += '                      <span>Yes</span>';
								} else {
									result += '                      <span>No</span>';
								}
								result += '                  </div>';
							}
							result += '              </div>';
							
							if (report.leaveEncashMinDays != null && report.leaveEncashMinDays != ''
								&& report.leaveEncashMinDays != 'undefined') {
							result += '              <div class="col-md-12 mb">';
							result += '                  <label class="col-md-5 control-label"><b style="color:#808080;"><i class="fa fa-level-up paddingRight"></i>Min Days:</b></label>';

							result += '                  <div class="col-md-7">';
							result += '                      <span>'
									+ report.leaveEncashMinDays + '</span>';
							result += '                  </div>';

							result += '              </div>';
						}

						if (report.leaveEncashMaxDays != null && report.leaveEncashMaxDays != ''
								&& report.leaveEncashMaxDays != 'undefined') {
							result += '              <div class="col-md-12 mb">';
							result += '                  <label class="col-md-5 control-label"><b style="color:#808080;"><i class="fa fa-level-up paddingRight"></i>Max Days:</b></label>';

							result += '                  <div class="col-md-7">';
							result += '                      <span>'
									+ report.leaveEncashMaxDays + '</span>';
							result += '                  </div>';

							result += '              </div>';
						}
						    result += '              </div>';
						    
							result += '              <div class="col-md-5 mb">';
							result += '                  <label class="col-md-5 control-label"><b>Overdraw Leaves:</b></label>';
							if (report.overdrawLeaves != null
									&& report.overdrawLeaves != ''
									&& report.overdrawLeaves != 'undefined') {
								result += '                  <div class="col-md-7">';
								if (report.overdrawLeaves == 'Y') {
									result += '                      <span>Yes</span>';
								} else {
									result += '                      <span>No</span>';
								}
								result += '                  </div>';
							}

							result += '              </div>';
							result += '              <div class="col-md-5 mb">';
							result += '                  <label class="col-md-5 control-label"><b>Donate Leave:</b></label>';
							if (report.donateLeave != null
									&& report.donateLeave != ''
									&& report.donateLeave != 'undefined') {
								result += '                  <div class="col-md-7">';
								if (report.donateLeave == 'Y') {
									result += '                      <span>Yes</span>';
								} else {
									result += '                      <span>No</span>';
								}
								result += '                  </div>';
							}

							result += '              </div>';
							result += '              <div class="col-md-5 mb">';
							result += '              <div class="col-md-12 mb">';
							result += '                  <label class="col-md-5 control-label"><b style="color:#101010;">Prior Leave Applying:</b></label>';
							if (report.priorLeaveApplying != null
									&& report.priorLeaveApplying != ''
									&& report.priorLeaveApplying != 'undefined') {
								result += '                  <div class="col-md-7">';
								if (report.priorLeaveApplying == 'Y') {
									result += '                      <span>Yes</span>';
								} else {
									result += '                      <span>No</span>';
								}
								result += '                  </div>';
							}

							result += '              </div>';

							if (report.priorDays != null
									&& report.priorDays != ''
									&& report.priorDays != 'undefined') {
								result += '              <div class="col-md-12 mb">';
								result += '                  <label class="col-md-5 control-label"><b style="color:#808080;"><i class="fa fa-level-up paddingRight"></i>Prior Days:</b></label>';
								result += '                  <div class="col-md-7">';
								result += '                      <span>'
										+ report.priorDays + '</span>';
								result += '                  </div>';

								result += '              </div>';
							}
							result += '              </div>';
							
							result += '              <div class="col-md-5 mb">';
							result += '              <div class="col-md-12 mb">';
							result += '                  <label class="col-md-5 control-label"><b style="color:#101010;">Leave Duration:</b></label>';
							if (report.leaveDuration != null
									&& report.leaveDuration != ''
									&& report.leaveDuration != 'undefined') {
								result += '                  <div class="col-md-7">';
								if (report.leaveDuration == 'Y') {
									result += '                      <span>Yes</span>';
								} else {
									result += '                      <span>No</span>';
								}

								result += '                  </div>';

							}

							result += '              </div>';

							if (report.minDays != null && report.minDays != ''
									&& report.minDays != 'undefined') {
								result += '              <div class="col-md-12 mb">';
								result += '                  <label class="col-md-5 control-label"><b style="color:#808080;"><i class="fa fa-level-up paddingRight"></i>Min Days:</b></label>';

								result += '                  <div class="col-md-7">';
								result += '                      <span>'
										+ report.minDays + '</span>';
								result += '                  </div>';

								result += '              </div>';
							}

							if (report.maxDays != null && report.maxDays != ''
									&& report.maxDays != 'undefined') {
								result += '              <div class="col-md-12 mb">';
								result += '                  <label class="col-md-5 control-label"><b style="color:#808080;"><i class="fa fa-level-up paddingRight"></i>Max Days:</b></label>';

								result += '                  <div class="col-md-7">';
								result += '                      <span>'
										+ report.maxDays + '</span>';
								result += '                  </div>';

								result += '              </div>';
							}
							result += '              </div>';
							
							result += '              <div class="col-md-5 mb">';
							result += '                  <label class="col-md-5 control-label"><b style="color:#101010;">Accural Policy:</b></label>';
							if (report.accuralPolicy != null
									&& report.accuralPolicy != ''
									&& report.accuralPolicy != 'undefined') {
								result += '                  <div class="col-md-7">';
								if (report.accuralPolicy == 'Y') {
									result += '                      <span>Yes</span>';
								} else {
									result += '                      <span>No</span>';
								}

								result += '                  </div>';

							}

							result += '              </div>';

							if (report.leavePolicy != null
									&& report.leavePolicy != ''
									&& report.leavePolicy != 'undefined') {
								result += '              <div class="clearfix"></div>';
								result += '             <div class="col-md-12 mb">';
								result += '                  <h4>Leave Policies</h4>';
								result += '              </div>';
								result += '                            <div class="clearfix"></div>';
								result += '             <div class="col-md-5 mb">';
								result += '                  <label class="col-md-5 control-label"><b>Policy Name:</b></label>';
								result += '                  <div class="col-md-7">';
								result += '                      <span>'
										+ report.policyName + '</span>';

								result += '                  </div>';
								result += '              </div>';
								result += '              <div class="col-md-5 mb">';
								result += '                  <label class="col-md-5 control-label"><b>Description:</b></label>';
								result += '                  <div class="col-md-7">';
								if (report.policyDescription != null
										&& report.policyDescription != ''
										&& report.policyDescription != 'undefined') {
									result += '                      <span>'
											+ report.policyDescription
											+ '</span>';
								} else {
									result += '                      <span>---</span>';
								}
								result += '                  </div>';
								result += '              </div>';
								if (report.visibilityToAllUsers != null
										&& report.visibilityToAllUsers != ''
										&& report.visibilityToAllUsers != 'undefined'
										&& report.visibilityToAllUsers == 'Y') {
									result += '              <div class="col-md-5 mb">';
									result += '                 <label class="col-md-5 control-label">&nbsp;&nbsp;</label>';
									result += '                 <div class="checkBox col-md-7">';

									result += '                  <input type="checkbox" Checked disabled="true" value="" name="visible">Visible to all users';

									result += '                 </div>';
									result += '              </div>';
								}
							}
							result += '              <div class="col-md-5 mb">';
							result += '                  <label class="col-md-5 control-label"><b>Created By:</b></label>';
							result += '                  <div class="col-md-7">';
							if (report.createdUser != null
									&& report.createdUser != ''
									&& report.createdUser != 'undefined') {
								result += '                      <span>'
										+ report.createdUser + '</span>';
							} else {
								result += '                      <span>---</span>';
							}
							result += '                  </div>';
							result += '              </div>';
							
							result += '              <div class="clearfix"></div>';
							
							result += '              <div class="col-md-5 mb">';
							result += '                  <label class="col-md-5 control-label"><b>Updated By:</b></label>';
							result += '                  <div class="col-md-7">';
							if (report.updatedUser != null
									&& report.updatedUser != ''
									&& report.updatedUser != 'undefined') {
								result += '                      <span>'
										+ report.updatedUser + '</span>';
							} else {
								result += '                      <span>---</span>';
							}
							result += '                  </div>';
							result += '              </div>';
							result += '          </div>';
							result += '              </div>';

							result += '  </div>';
							result += '  </div>';
							result += '  </div>';
							result += '</div>';
							/* }
							} */
							$("#typeSummaryDiv").empty();
							$("#typeSummaryDiv").append(result);
							$(".new_side_wrap")
									.addClass("new_side_wrap_active");
							$(".proj_close, .side_mask").click(
									function() {
										$(".new_side_wrap").removeClass(
												"new_side_wrap_active");
										$(".side_mask").fadeOut(200);
										$("body").css('overflow-y', 'auto');
									});
						} else if (response.indexOf('Sign In') !== -1) {
							location.replace(pmsUrl);
						} else {
							showError("Operation failed, please try again");
						}

					}
				});
	}
</script>

<jsp:include page="../masterFooter.jsp" />
