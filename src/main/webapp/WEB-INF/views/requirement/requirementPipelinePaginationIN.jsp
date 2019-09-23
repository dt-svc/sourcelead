
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	
<div class="comMask" style="display: none;"></div>
<div class="modal-POP">
	<div id="showDetailsOfInterviews"></div>
</div>

<script>
	function requirementPipelinePaginationIN(url, requirementId, submissionType) {
		var sourceleadUrl = '${sourceleadUrl}';
		var orgLocationId = '${SourceLeadWeb_orgLocationId}';
		var colIndex = 0;
		$('#requirementsPagination')
				.DataTable(
						{
							"processing" : true,
							"serverSide" : true,
							"searching" : false,
							"ajax" : $.fn.dataTable.pipeline({
								url : url,
								method : 'POST',
								pages : 1
							}),
							"columns" : [ {
								"data" : "name"
							}, {
								"data" : "email"
							}, {
								"data" : "phNo"
							}, {
								"data" : "resume"
							}, {
								"data" : "noOfInterviews"
							}, {
								"data" : "status"
							} ],
							"columnDefs" : [
									{
										"targets" : colIndex++,
										"data" : "name",
										"render" : function(data, type, opt,
												meta) {
											if (opt.candidateId != null
													&& opt.candidateId != ''
													&& opt.candidateId != 'undefined') {
												$("#emptyPipelinelist").val(1);
											}
											var result = "";
											'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_APPLICANT\')">';
											result = result
													+ '         <td><div onclick="applicantQuickViewProfile('
													+ opt.id
													+ ','
													+ opt.candidateId
													+ '); getSubmissionDetailsSideBar('
													+ opt.id
													+ ','
													+ opt.candidateId
													+ ','
													+ requirementId
													+ ');" class="tbldata-dotdot proj_show"><span class="pull-left" title ="'+opt.candidateName+'">'
													+ opt.candidateName
													+ '</span><i class="fa fa-binoculars"></i></div></td>';
											'</sec:authorize>';
											'<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_APPLICANT\')">';
											result = result
													+ '         <td><div class="tbldata-dotdot proj_show"><span class="pull-left">'
													+ opt.candidateName
													+ '</span></div></td>';
											'</sec:authorize>';
											return result;
										}
									},
									{
										"targets" : colIndex++,
										"data" : "email",
										"render" : function(data, type, opt,
												meta) {
											var result = "";
											if (opt.candidateEmail != null
													&& opt.candidateEmail != ''
													&& opt.candidateEmail != 'undefined') {
												result = result
														+ '<td><div><span title ="'+opt.candidateEmail+'">'
														+ opt.candidateEmail
														+ '</span></div></td>';
											} else {
												result = result
														+ '<td><div><span>--</span></div></td>';
											}
											return result;

										}
									},
									{
										"targets" : colIndex++,
										"data" : "phNo",
										"render" : function(data, type, opt,
												meta) {
											var result = "";
											if (opt.phoneNumber != null
													&& opt.phoneNumber != ''
													&& opt.phoneNumber != 'undefined') {
												result = result
														+ '         <td><div><span>'
														+ opt.phoneNumber
														+ '</span></div></td>';
											} else {
												result = result
														+ '         <td><div><span>--</span></div></td>';
											}
											return result;
										}
									},
									{
										"targets" : colIndex++,
										"data" : "resume",
										"render" : function(data, type, opt,
												meta) {
											var result = "";
											if (opt.resumeId != null
													&& opt.resumeId != ''
													&& opt.resumeId != 'undefined') {
												result = result
												+ '         <td><div><span><a download class="glyphicon glyphicon-download-alt" href=\"'+opt.fileURL+'\"></a><a class="fa fa-eye ml10" onclick="viewFile(\''+opt.fileURL+'\',\''+opt.fileName+'\',\''+opt.fileContentType+'\');"></a></span></div></td>';
											} else {
												result = result
														+ '         <td><div>--</div></td>';
											}
											return result;
										}
									},
									{
										"targets" : colIndex++,
										"data" : "noOfInterviews",
										"render" : function(data, type, opt,
												meta) {
											var result = "";
											if (opt.totalNoOfInterviews != null
													&& opt.totalNoOfInterviews != ''
													&& opt.totalNoOfInterviews != 'undefined') {
												result = result
														+ '<td><a href="javascript:void(0)" onclick="fetchInterviewsListForSubmission('
														+ opt.id
														+ ','
														+ opt.candidateId
														+ ');"><span>'
														+ opt.totalNoOfInterviews
														+ '</span></a></td>';
											} else {
												result = result
														+ '<td><div><span>--</span></div></td>';
											}
											return result;
										}
									},
									{
										"targets" : colIndex++,
										"data" : "status",
										"render" : function(data, type, opt,
												meta) {
											var result = "";
											if (opt.requirementCurrentStatus != 'SUBMITTED'
													&& opt.requirementCurrentStatus != 'CLOSED'
													&& opt.requirementCurrentStatus != 'REJECTED'
													&& opt.requirementCurrentStatus != 'CANCEL'
													&& opt.requirementCurrentStatus != 'DRAFT'
													&& opt.requirementCurrentStatus != 'ONHOLD'
													&& opt.requirementCurrentStatus != 'FILLEDCLOSED'
													&& opt.requirementCurrentStatus != 'UNFILLEDCLOSED') {
												if (opt.submissionStatus == 'REJECTED') {
													result = result
															+ '<td><div class="tblwidth-150"><span>'
															+ opt.submissionStatus
															+ '</span>';
													if (opt.rejectedReason != null
															&& opt.rejectedReason != ''
															&& opt.rejectedReason != 'undefined'
															&& opt.rejectedCategoryType != null
															&& opt.rejectedCategoryType != 'Other') {
														result = result
																+ '<i class="glyphicon glyphicon-comment ml10 edit-reason"><div class="edit-status"><p>'
																+ "<font color='red'>Category</font>: "
																+ opt.rejectedCategoryType
																+ '<br>'
																+ "Reason: "
																+ opt.rejectedReason
																+ '</p></div></i></div></td>';
													}

													if (opt.rejectedReason != null
															&& opt.rejectedReason != ''
															&& opt.rejectedReason != 'undefined'
															&& opt.rejectedCategoryType == null) {
														result = result
																+ '<i class="glyphicon glyphicon-comment ml10 edit-reason"><div class="edit-status"><p>'
																+ "<font color='red'>Category</font>: "
																+ "Bad Fit"
																+ '<br>'
																+ "Reason: "
																+ opt.rejectedReason
																+ '</p></div></i></div></td>';
													}

													if (opt.rejectedReason != null
															&& opt.rejectedReason != ''
															&& opt.rejectedReason != 'undefined'
															&& opt.rejectedCategoryType != null
															&& opt.rejectedCategoryType == 'Other') {
														result = result
																+ '<i class="glyphicon glyphicon-comment ml10 edit-reason"><div class="edit-status"><p>'
																+ "<font color='red'>Category</font>:"
																+ opt.rejectedCategoryType
																+ '<br>'
																+ "Other: "
																+ opt.rejectedCategoryOther
																+ '<br>'
																+ "Reason: "
																+ opt.rejectedReason
																+ '</p></div></i></div></td>';
													}
												} else if (opt.submissionStatus == 'BACKOUT') {
													result = result
													+ '<td><div class="tblwidth-150"><span>'
													+ opt.submissionDisplayStatus
													+ '</span>';
											if (opt.backOutReason != null
													&& opt.backOutReason != ''
													&& opt.backOutReason != 'undefined'
													&& opt.backOutCategoryType != null
													&& opt.backOutCategoryType != 'Other') {
												result = result
														+ '<i class="glyphicon glyphicon-comment ml10 edit-reason"><div class="edit-status"><p>'
														+ "<font color='red'>Category</font>: "
														+ opt.backOutCategoryType
														+ '<br>'
														+ "Reason: "
														+ opt.backOutReason
														+ '</p></div></i></div></td>';
											}
											if (opt.backOutReason != null
													&& opt.backOutReason != ''
													&& opt.backOutReason != 'undefined'
													&& opt.backOutCategoryType != null
													&& opt.backOutCategoryType == 'Other') {
												result = result
														+ '<i class="glyphicon glyphicon-comment ml10 edit-reason"><div class="edit-status"><p>'
														+ "<font color='red'>Category</font>:"
														+ opt.backOutCategoryType
														+ '<br>'
														+ "Other: "
														+ opt.backOutCategoryOther
														+ '<br>'
														+ "Reason: "
														+ opt.backOutReason
														+ '</p></div></i></div></td>';
											}
										}else {
													result = result
															+ '<td><div class="tblwidth-150">';
													result = result
															+ '<select id="rejectApplicantSelectspocState'
															+ opt.id
															+ '" onChange="rejectApplicant('
															+ opt.id + ','
															+ requirementId
															+ ',\''
															+ submissionType
															+ '\')";>';
													result = result + "<option value='Status'>" + opt.submissionDisplayStatus + "</option>";
													/* if (opt.submissionStatus == 'INTERVIEW'){
														result = result + "<option value='SelectedOnInterview'>Selected on Interview</option>";
													} */
													if (opt.submissionStatus != 'ONHOLD' && opt.submissionStatus != 'JOINED'){	
														result = result + "<option value='On Hold'>On Hold</option>";
														}
													if (opt.submissionStatus != 'NORESPONSE' && opt.submissionStatus != 'JOINED'){	
														result = result + "<option value='NoResponse'>NoResponse</option>";
														}
													result = result + "<option value='Reject'>Reject</option>";
													if (opt.submissionStatus == 'OFFERMADE' || opt.submissionStatus == 'OFFERACCEPTED' || opt.submissionStatus == 'JOINED'
														 || opt.submissionStatus == 'CLIENT_OFFERMADE' || opt.submissionStatus == 'CLIENT_OFFERACCEPTED') {
														result = result
																+ '<option value="backout">BackOut</option>';
													}
													result = result
															+ '</select>';
													result = result
															+ '</div></td>';
												}
											} else {
												result = result
														+ '<td><div class="tblwidth-150"><span>'
														+ opt.submissionDisplayStatus
														+ '</span>';
											}
											result = result + '</div></td>';
											return result;
										}
									} ],
							"scrollCollapse" : false,
							"initComplete" : function(settings, json) {
								$('#tab-1').hide();
							},
							"lengthMenu" : [ [ 10, 15, 20 ], [ 10, 15, 20 ] ],
							"aaSorting" : [ [ 3, 'desc' ] ],
							"bLengthChange" : true,
							"bDestroy" : true,
							"ordering" : true,
							"createdRow": function ( row, data, index ) {
					    		row.setAttribute('id',"job_"+data.id);
				    	    },
							"oLanguage" : {
								"sSearch" : "",
								"sZeroRecords" : "",
								"sEmptyTable" : "",
								"sProcessing" : "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
							}
						});
		$("#requirementsPagination").wrap(
				"<div style='overflow-x: auto; overflow-y: hidden;'></div>");
		$("input[type=search]").hide();
		$("#submissionDataTableSearch").keyup(function(e) {
			if (e.which === 13) {
				requirementPipeline(requirementId, submissionType);
			}
		});
	}
</script>


<script type="text/javascript">
function fetchInterviewsListForSubmission(submissionId, candidateId){
		  $('#showDetailsOfInterviews').empty();
      	  $("#showDetailsOfInterviews").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
          	$.ajax({
    			type: "POST",
    		    url: sourceleadUrl+"/getInterviewsListBySubmission?"+tokenParamName+"="+tokenValue+"&submissionId="+submissionId+"&candidateId="+candidateId, 
    		    success: function(data) {
    		    	$('#showDetailsOfInterviews').removeAttr("style");
    		    	$('#showDetailsOfInterviews').append(data);
    		    },
    		});
          	$('#loading').removeAttr("style");
          	$(".comMask, .modal-POP").fadeIn();
}
</script>