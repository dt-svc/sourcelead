<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>



<script type="text/javascript">
	$(document).ready(function() {
		getCancelledLeavesList();
		if ('${notifyLink}' == "notifyLink") 
		{
		var leaveUUID = '${leaveUUID}';
		var orgLocationId = '${orgLocationId}';
		getRequestedAproveSummary(leaveUUID,orgLocationId);
		}
	});
</script>

<!-- Content Area -->
<section class="content-wraper" id="themeroller">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Cancelled</h2>
		</div>
		<div class="pull-right brud-crum">
			<a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>"> Home</a> &raquo; <a href="<c:url value='${appPath}/leaveManagementReports'/>">Leave Management</a> &raquo; Cancelled Leaves</div>
	</section>

	<div class="content-area content-new clearfix">

		<div class="timesheet_out">
			<div class="clearfix">
				<div class="timesheet_head">
					<ul>
						<li class="dropdown">
							<div class="pull-left select-fild">
								<select onchange="redirectlocation(this.value);">
									<option value="/pendingLeaves">Pending Leaves</option>
									<option value="/withdrawLeaves">Withdraw Leaves</option>
									<option value="/approvedLeaves">Approved Leaves</option>
									<option value="/rejectedLeaves">Rejected Leaves</option>
									<option value="/cancelLeaves" selected>Cancelled Leaves</option>
								</select>
							</div>
						</li>
						<div class="clearfix"></div>
					</ul>
					<div class="clearfix"></div>
				</div>
				<div class="clients_wrap_table datatable_filter">
					<div id="loading"></div>
					<div id="cancelledListDiv"></div>
				</div>
			</div>
		</div>

	</div>
	<!-- Content Area Ends-->
</section>
<!-- Content Area Ends-->


<script>
	function redirectlocation(location) {
		window.location.href = sourceleadUrl + location;
	}
</script>

<script>
	function getCancelledLeavesList() {
		var orgLocationId = '${SourceLeadWeb_orgLocationId}';
		var userId = '${SourceLeadWeb_user.id}';
		$("#loading")
				.attr(
						'style',
						'position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:999;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');

		$
				.get(
						sourceleadUrl + "/getLeavesListByLocationIdAndStatus?"
								+ tokenParamName + "=" + tokenValue
								+ "&orgLocationId=" + orgLocationId
								+ "&status=CANCELLED&userId=" + userId,
						function(response) {
						if (response.response === "SUCCESS") {
							var result = "";
							result = result
									+ '<table id="cancelledListTable" class="display" cellspacing="0" width="100%" >';
							result = result + '<thead>';
							result = result + '<tr>';
							result = result + '<th>Leave Type</th>';
							result = result + '<th>Duration</th>';
							result = result + '<th>Applied On</th>';
							result = result + '<th>Reason</th>';
							result = result + '<th>Approved By</th>';
							result = result + '<th>Cancelled By</th>';
							result = result + '<th>Cancelled Reason</th>';
							result = result + '<th>Status</th>';
							result = result + '</tr>';
							result = result + '</thead>	';
							result = result + '<tbody>';
							//var data = json.data;
							if (response != null && response != '') {
								for (var i = 0; i < response.data.length; i++) {
									var opt = response.data[i];
									result = result + ' <tr>';
									result = result + ' <td>';
									result = result
											+ ' <div class="proj_show tbldata-dotdot-big">';
									result = result
											+ '     <div class="pull-left tbldata-dotdot-me" title="'
											+ opt.leaveType
											+ '" onclick="getRequestedAproveSummary(\''
											+ opt.leaveUUID
											+ '\','
											+ orgLocationId
											+ ');">'
											+ opt.leaveType
											+ ' <i class="fa fa-binoculars pull-right" ></i></div>';
									result = result + '  </div>';
									result = result + ' </td>';
									result = result + ' <td>';
									result = result
											+ ' <div title="'+opt.fromDate+' - '+opt.toDate+'" class="tblminwidth-75 mt2">';
									result = result
											+ ' <div class="width100percnt">'
											+ opt.fromDate + '</div>';
									result = result
											+ ' <div class="width100percnt mt2">'
											+ opt.toDate + '</div>';
									result = result + ' </div>';
									result = result + ' </td>';
									result = result + ' <td>';
									result = result
											+ '   <div title="'+opt.createdDate+'" class="tbldata-dotdot-100">'
											+ opt.createdDate + '</div>';
									result = result + '</td>';
									result = result + ' <td>';
									if (opt.reason != null
											&& opt.reason != 'undefined'
											&& opt.reason != '') {
										result = result
												+ '     <div title="'+opt.reason+'" class="tbldata-dotdot-vsm">'
												+ opt.reason + '</div>';
									} else {
										result = result
												+ '     <div title="" class="tblminwidth-75">--</div>';
									}
									result = result + '  </td>';
									result = result + ' <td>';
									if (opt.approvedByUser != null
											&& opt.approvedByUser != 'undefined'
											&& opt.approvedByUser != '') {
										result = result
												+ '   <div title="'+opt.approvedByUser+'" class="tbldata-dotdot-100">'
												+ opt.approvedByUser + '</div>';
									} else {
										result = result
												+ '   <div title="" class="tbldata-dotdot-100">---</div>';
									}
									result = result + '</td>';
									result = result + ' <td>';
									if (opt.updatedBy != null
											&& opt.updatedBy != 'undefined'
											&& opt.updatedBy != '') {
										result = result
												+ '     <div title="'+opt.updatedBy+'" class="tblminwidth-75">'
												+ opt.updatedBy + '</div>';
									} else {
										result = result
												+ '     <div title="" class="tblminwidth-75">--</div>';
									}
									result = result + '  </td>';
									result = result + ' <td>';
									if (opt.cancelReason != null
											&& opt.cancelReason != 'undefined'
											&& opt.cancelReason != '') {
										result = result
												+ '     <div title="'+opt.cancelReason+'" class="tblminwidth-75">'
												+ opt.cancelReason + '</div>';
									} else {
										result = result
												+ '     <div title="" class="tblminwidth-75">--</div>';
									}
									result = result + '  </td>';
									result = result + ' <td>';
									result = result
											+ '  <div title="'+opt.status+'" class="tblminwidth-75">';
									result = result
											+ ' <b class="proj_closed">'
											+ opt.status + '</b> ';
									result = result + '  </div>';
									result = result + ' </td>';
									result = result + '  </tr>';
								}
							}

							result = result + '  </tbody>';
							result = result + ' </table>';
							$("#cancelledListDiv").empty();
							$("#cancelledListDiv").append(result);
							$("#loading").removeAttr("style");
							rejectedListPagination();

							$(".proj_show").click(
									function() {
										$(".new_side_wrap").addClass(
												"new_side_wrap_active");
										$(".side_mask").fadeIn(200);
										$("body").css('overflow-y', 'hidden');
									});

							$(".proj_close, .side_mask").click(
									function() {
										$(".new_side_wrap").removeClass(
												"new_side_wrap_active");
										$(".side_mask").fadeOut(200);
										$("body").css('overflow-y', 'auto');
										$('.addcandidate-popup').fadeOut(200);
									});
						}else if (response.indexOf('Sign In') !== -1) {
							location.replace(sourceleadUrl);
					    } else if (response.response === "AWKWARD") {
						    location.href = "/errorPage";
					     }
						else {
						    showError("Operation failed, please try again");
						}
						});
	}
</script>

<script>
	function getRequestedAproveSummary(leaveUUID, orgLocationId) {
		$.get(
						sourceleadUrl + "/getRequestedAproveSummary?"
								+ tokenParamName + "=" + tokenValue
								+ "&leaveUUID=" + leaveUUID + "&orgLocationId="
								+ orgLocationId,
						function(response) {
							if(response.response === "SUCCESS") {
								   if(response != null){
								$(".new_side_wrap").addClass(
										"new_side_wrap_active");
								$(".side_mask").fadeIn(200);
								$("body").css('overflow-y', 'hidden');
								var data = response.object;
								var result = "";
								result = result
										+ ' <div class="viewgrid-header pull-right zIndex positionRelative">';
								// 			 result = result +' <span class="btn btn-primary btn-sm mr10">Rejected</span>';
								result = result
										+ ' <span class="btn btn-danger btn-sm mr10" title="CANCEL" onClick="cancel()">CANCEL</span>';
								result = result
										+ '<div class="clearfix"></div>';
								result = result
										+ '<div class="cancel-reason" id="cancelReason">';
								result = result + '<div class="clearfix">';
								result = result
										+ ' <textarea rows="1" class="form-control" placeholder="Cancel Reason..." id="textarea"></textarea>';
								result = result + '</div>';
								result = result + '<div class="clearfix mt5">';
								result = result + '</div>';
								result = result + '</div>';
								result = result + ' </div> ';
								result = result + ' <div class="row">';
								result = result + '<div class="col-md-12 mb">';
								result = result
										+ '<label class="col-md-2 control-label"><b>Leave Type:</b></label>';
								result = result + '<div class="col-md-8">';
								if(data.leaveName != null && data.leaveName !='' && data.leaveName !='undefined'){
									 result = result +'<span>'+data.leaveName+'</span>';
								 }else{
						            	result = result +' <span>--</span>';
						            }
								result = result + '</div>';
								result = result + '</div>';
								result = result + '<div class="col-md-12 mb">';
								result = result
										+ '<label class="col-md-2 control-label"><b>Duration:</b></label>';
								result = result + '<div class="col-md-8">';
								if(data.duration != null && data.duration !='' && data.duration !='undefined'){
									 result = result +'<span>'+data.duration+'</span>';
								 }else{
						            	result = result +' <span>--</span>';
						            }
								result = result + ' </div>';
								result = result + '</div>';
								result = result + ' <div class="col-md-12 mb">';
								result = result
										+ '<label class="col-md-2 control-label"><b>Type:</b></label>';
								result = result + '<div class="col-md-8">';
								if(data.isBillable != null && data.isBillable !='' && data.isBillable !='undefined'){
									 result = result +'<span>'+data.isBillable+'</span>';
								 }else{
						            	result = result +' <span>--</span>';
						            }
								result = result + ' </div>';
								result = result + ' </div>';
								/* result = result + '<div class="col-md-12 mb">';
								result = result
										+ '<label class="col-md-2 control-label"><b>No of leaves taken:</b></label>';
								result = result + ' <div class="col-md-8">';
								if(data.actualLeavesTaken != null && data.actualLeavesTaken !='' && data.actualLeavesTaken !='undefined'){
									 result = result +'<span>'+data.actualLeavesTaken+'</span>';
								 }else{
						            	result = result +' <span>--</span>';
						            }
								result = result + '</div>';
								result = result + '</div>'; */
								result = result + '<div class="col-md-12 mb">';
								result = result
										+ '<label class="col-md-2 control-label"><b>Applied on:</b></label>';
								result = result + '<div class="col-md-8">';
								if(data.createdDate != null && data.createdDate !='' && data.createdDate !='undefined'){
									 result = result +'<span>'+data.createdDate+'</span>';
								 }else{
						            	result = result +' <span>--</span>';
						            }
								result = result + ' </div>';
								result = result + '</div>';
								result = result + '<div class="col-md-12 mb">';
								result = result
										+ '<label class="col-md-2 control-label"><b>Approvers:</b></label>';
								result = result + '<div class="col-md-8">';
								if(data.approverUsers != null && data.approverUsers !='' && data.approverUsers !='undefined'){
									 result = result +'<span>'+data.approverUsers+'</span>';
								 }else{
						            	result = result +' <span>--</span>';
						            }
								result = result + '</div>';
								result = result + '</div>';
								result = result + '<div class="col-md-12 mb">';
								result = result
										+ '<label class="col-md-2 control-label"><b>Approved By:</b></label>';
								result = result + '<div class="col-md-8">';
								if(data.approvedBy != null && data.approvedBy !='' && data.approvedBy !='undefined'){
									 result = result +'<span>'+data.approvedBy+'</span>';
								 }else{
						            	result = result +' <span>--</span>';
						            }
								result = result + '</div>';
								result = result + '</div>';
								result = result + '<div class="col-md-12 mb">';
								result = result
										+ '<label class="col-md-2 control-label"><b>Approved On:</b></label>';
								result = result + '<div class="col-md-8">';
								if(data.updatedDate != null && data.updatedDate !='' && data.updatedDate !='undefined'){
									 result = result +'<span>'+data.updatedDate+'</span>';
								 }else{
						            	result = result +' <span>--</span>';
						            }
								result = result + '</div>';
								result = result + '</div>';
								result = result + '<div class="col-md-12 mb">';
								result = result
										+ '<label class="col-md-2 control-label"><b>Cancelled By:</b></label>';
								result = result + '<div class="col-md-8">';
								if(data.cancelledBy != null && data.cancelledBy !='' && data.cancelledBy !='undefined'){
									 result = result +'<span>'+data.cancelledBy+'</span>';
								 }else{
						            	result = result +' <span>--</span>';
						            }
								result = result + '</div>';
								result = result + '</div>';
								result = result + '<div class="col-md-12 mb">';
								result = result
										+ '<label class="col-md-2 control-label"><b>Cancelled On:</b></label>';
								result = result + '<div class="col-md-8">';
								if(data.updatedDate != null && data.updatedDate !='' && data.updatedDate !='undefined'){
									 result = result +'<span>'+data.updatedDate+'</span>';
								 }else{
						            	result = result +' <span>--</span>';
						            }
								result = result + '</div>';
								result = result + '</div>';
								result = result + '<div class="col-md-12 mb">';
								result = result
										+ '<label class="col-md-2 control-label"><b>Cancelled Reason:</b></label>';
								result = result + '<div class="col-md-8">';
								if(data.deleteReason != null && data.deleteReason !='' && data.deleteReason !='undefined'){
									 result = result +'<span>'+data.deleteReason+'</span>';
								 }else{
						            	result = result +' <span>--</span>';
						            }
								result = result + '</div>';
								result = result + '</div>';
								result = result + '<div class="col-md-12 mb">';
								result = result
										+ '<label class="col-md-2 control-label"><b>Reason:</b></label>';
								result = result + '<div class="col-md-8">';
								if(data.reason != null && data.reason !='' && data.reason !='undefined'){
									 result = result +'<span>'+data.reason+'</span>';
								 }else{
						            	result = result +' <span>--</span>';
						            }
								result = result + '</div>';
								result = result + '</div>';
								result = result + '<div class="col-md-12 mb">';
								result = result
										+ '<label class="col-md-2 control-label"><b>Attachment:</b></label>';
								result = result + ' <div class="col-md-8">';
								//              result = result +' <span><a href="javascript:void(o)">demo.pdf<i class="fa fa-download padddingLeft cursorPointer"></i></a></span>';
								if (data.fileUrl != null
										&& data.fileUrl != 'undefined'
										&& data.fileUrl != '') {
									result = result+'<td><div class="tblicons_4"> <a download class="glyphicon glyphicon-download-alt" target="_blank" href=\"'+data.fileUrl+'\" title="Download"></a>&nbsp&nbsp';
									result = result
											+ '<a href="#" title="Preview"><i class="fa fa-eye cursorPointer" onclick="viewFile(\''
											+ data.fileUrl + '\', \''
											+ data.fileName + '\', \''
											+ data.fileContentType + '\')"></i></a>';
								} else {
									result = result + '<span>--</span>';
								}
								result = result + '</div>';
								result = result + ' </div>';
								result = result
										+ '<div class="clearfix"></div>';
								result = result + '</div>';

								$("#leaveSummaryId").empty();
								$("#leaveSummaryId").append(result);

							}
							   } else if (response && response.response === "INVALID_DATA") {
									//$(".new_side_wrap").removeClass("new_side_wrap_active");
					   		    showError(response.responseMessage);
							   } else if (response.indexOf('Sign In') !== -1) {
									location.replace(sourceleadUrl);
							    } else if (response.response === "AWKWARD") {
								    location.href = "/errorPage";
							     }
								else {
								    showError("Operation failed, please try again");
								}   

						});
	}
</script>


<script>
	function rejectedListPagination() {
		$('#cancelledListTable').DataTable({
			"scrollCollapse" : false,
			"lengthMenu" : [ [ 10, 15, 20, -1 ], [ 10, 15, 20, "All" ] ],
			"bLengthChange" : true,
			"bDestroy" : true,
			"ordering" : true,
			"oLanguage" : {
				"sSearch" : "",
				"sZeroRecords" : "",
				"sEmptyTable" : ""
			}
		});
		$("#cancelledListTable").wrap(
				"<div class='overflow_Auto width100percnt'></div>")
	}
</script>

<div class="side_mask">
	<div class="proj_close"></div>
</div>

<div class="new_side_wrap">
	<div class="clients_side_wrap_inn">
		<div class="clerfix showDetails">
			<div class="project_tabs minHeight_225 form-horizontal modal-body">
				<div id="leaveSummaryId"></div>
			</div>
		</div>
	</div>
</div>



<jsp:include page="../masterFooter.jsp" />