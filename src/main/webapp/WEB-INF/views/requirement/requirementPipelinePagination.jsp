
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<script>
	function requirementPipelinePagination(url, requirementId, submissionType) {
		var sourceleadUrl = '${sourceleadUrl}';
		var orgLocationId = '${SourceLeadWeb_orgLocationId}';
		var clientSubmissions = '${clientSubmissions}';
		var isSpocActive = '${isSpocActive}';
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
								pages : 10
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
								"data" : "appliedOn"
							}, {
								"data" : "rate"
							}, {
								"data" : "source"
							}, {
								"data" : "score"
							}, {
								"data" : "exp"
							}, {
								"data" : "status"
							} ],
							"columnDefs" : [
									{
										"targets" : colIndex++,
										"data" : "name",
										"render" : function(data, type, opt,
												meta) {
											var result = "";
											'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_APPLICANT\')">';
											result = result
													+ '<td><div onclick="applicantQuickViewProfile('
													+ opt.id
													+ ','
													+ opt.candidateId
													+ '); getSubmissionDetailsSideBar('
													+ opt.id
													+ ','
													+ opt.candidateId
													+ ','
													+ requirementId
													+ ');" class="tbldata-dotdot proj_show positionRelative"><span class="pull-left" title ="'+opt.candidateName+'">'
													+ opt.candidateName
													+ '</span><i class="fa fa-binoculars"></i>';
											if (opt.candidateScore != null
													&& opt.candidateScore != ''
													&& opt.candidateScore != 'undefined') {
												if (opt.candidateScore >= 50) {
													result = result
															+ '<small class="valid-profile validProfile fa fa-check-square text-success"></small>';
												}
											}
											result = result + ' </div></td>';
											'</sec:authorize>';
											'<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_APPLICANT\')">';
											result = result
													+ '<td><div class="tbldata-dotdot proj_show positionRelative"><span class="pull-left">'
													+ opt.candidateName
													+ '</span>';
											if (opt.candidateScore != null
													&& opt.candidateScore != ''
													&& opt.candidateScore != 'undefined') {
												if (opt.candidateScore >= 50) {
													result = result
															+ '<small class="valid-profile validProfile fa fa-check-square text-success"></small>';
												}
											}
											result = result + ' </div></td>';
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
														+ '         <td><div><span><a class="glyphicon glyphicon-download-alt" href=\"'
														+ sourceleadUrl
														+ '/downloadResume?resumeUUID='
														+ opt.resumeUUID
														+ '\"></a><a class="fa fa-eye ml10" onclick="viewResumeRequirementSummary(\''
														+ opt.resumeUUID
														+ '\',\''
														+ opt.filename
														+ '\')"></a></span></div></td>';
											} else {
												result = result
														+ '         <td><div>--</div></td>';
											}
											return result;
										}
									},
									{
										"targets" : colIndex++,
										"data" : "appliedOn",
										"render" : function(data, type, opt,
												meta) {
											var result = "";
											if (opt.createdDateStr != null
													&& opt.createdDateStr != ''
													&& opt.createdDateStr != 'undefined') {
												result = result
														+ '         <td><div class="tblminwidth-100">'
														+ opt.createdDateStr
														+ '</div></td>';
											} else {
												result = result
														+ '         <td><div><span>--</span></div></td>';
											}
											return result;
										}
									},
									{
										"targets" : colIndex++,
										"data" : "rate",
										"render" : function(data, type, opt,
												meta) {
											var result = "";
											var colorHint = "";
											if (opt.lastRateStage != null
													&& opt.lastRateIniLocId != null
													&& opt.lastRateIniType != null
													&& opt.lastRateStage != "undefined"
													&& opt.lastRateIniLocId != "undefined"
													&& opt.lastRateIniType != "undefined"
													&& opt.lastRateStage != ""
													&& opt.lastRateIniLocId != ""
													&& opt.lastRateIniType != "") {
												if (opt.lastRateStage == "rejected") {
													colorHint = "textDanger";
												} else if (opt.lastRateStage == "accepted") {
													colorHint = "textSuccess";
												} else if (orgLocationId != opt.lastRateIniLocId
														&& opt.lastRateIniUserCode != "") {
													colorHint = "textWarning";
												} else if (orgLocationId == opt.lastRateIniLocId
														&& opt.lastRateIniUserCode == "") {
													colorHint = "textWarning";
												}
											}
											if (opt.vendorLocationId != null
													&& opt.sourceSubmission != null) {
												if (opt.proposedSubmissionRate != null
														&& opt.proposedSubmissionRate != ''
														&& opt.proposedSubmissionRate != 'undefined') {
													'<sec:authorize access="hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
													result = result
															+ '         <td><div><span class="showRate '
															+ colorHint
															+ '" onclick="getRateListDiv('
															+ opt.id
															+ ','
															+ opt.candidateId
															+ ');">'
															+ opt.proposedSubmissionRate
															+ '</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
													'</sec:authorize>';
													'<sec:authorize access="!hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
													result = result
															+ '         <td><div><span class="showRate '+colorHint+'">'
															+ opt.proposedSubmissionRate
															+ '</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
													'</sec:authorize>';
												} else {
													var expexted = (opt.expectedCTC != ''
															&& opt.expectedCTC != 'undefined' && opt.expectedCTC != null) ? opt.expectedCTC
															: '<i class="glyphicon fa fa-money"></i>';
													'<sec:authorize access="hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
													result = result
															+ '         <td><div><span class="showRate '
															+ colorHint
															+ '" onclick="getRateListDiv('
															+ opt.id
															+ ','
															+ opt.candidateId
															+ ');">'
															+ expexted
															+ '</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
													'</sec:authorize>';
													'<sec:authorize access="!hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
													result = result
															+ '         <td><div><span class="showRate '+colorHint+'">'
															+ expexted
															+ '</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
													'</sec:authorize>';
												}
											} else {
												if (opt.expectedRate != null
														&& opt.expectedRate != ''
														&& opt.expectedRate != 'undefined') {
													'<sec:authorize access="hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
													result = result
															+ '         <td><div><span class="showRate '
															+ colorHint
															+ '" onclick="getRateListDiv('
															+ opt.id
															+ ','
															+ opt.candidateId
															+ ');">'
															+ opt.expectedRate
															+ '</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
													'</sec:authorize>';
													'<sec:authorize access="!hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
													result = result
															+ '         <td><div><span class="showRate '+colorHint+'">'
															+ opt.expectedRate
															+ '</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
													'</sec:authorize>';
												} else {
													var expexted = (opt.expectedCTC != ''
															&& opt.expectedCTC != 'undefined' && opt.expectedCTC != null) ? opt.expectedCTC
															: '<i class="glyphicon fa fa-money"></i>';
													'<sec:authorize access="hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
													result = result
															+ '         <td><div><span class="showRate '
															+ colorHint
															+ '" onclick="getRateListDiv('
															+ opt.id
															+ ','
															+ opt.candidateId
															+ ');">'
															+ expexted
															+ '</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
													'</sec:authorize>';
													'<sec:authorize access="!hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
													result = result
															+ '         <td><div><span class="showRate '+colorHint+'">'
															+ expexted
															+ '</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
													'</sec:authorize>';
												}
											}
											return result;
										}
									},
									{
										"targets" : colIndex++,
										"data" : "source",
										"render" : function(data, type, opt,
												meta) {
											var result = "";
											var orgName = "";
											if (opt.orgName != null
													&& opt.orgName != ''
													&& opt.orgName != 'undefined') {
												orgName = "(" + opt.orgName
														+ ")";
											}
											var candidateSource = "";
											if (opt.candidateSource != null
													&& opt.candidateSource != ''
													&& opt.candidateSource != 'undefined') {
												candidateSource = "("
														+ opt.candidateSource
														+ ")";
											}
											if (opt.createdUser != null
													&& opt.createdUser != ''
													&& opt.createdUser != 'undefined') {
												result = result
														+ '         <td><div><span title="'+opt.createdUser+''+orgName+''+candidateSource+'">'
														+ opt.createdUser + ''
														+ orgName + '<br/>'
														+ candidateSource
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
										"data" : "score",
										"render" : function(data, type, opt,
												meta) {
											var result = "";
											if (opt.candidateScore != null) {
												result = result
														+ '         <td><div><span>'
														+ opt.candidateScore
														+ ' %</span></div></td>';
											} else {
												result = result
														+ '         <td><div><span>--</span></div></td>';
											}
											return result;
										}
									},
									{
										"targets" : colIndex++,
										"data" : "exp",
										"render" : function(data, type, opt,
												meta) {
											var result = "";
											if (opt.workExperience != null
													&& opt.workExperience != ''
													&& opt.workExperience != 'undefined') {
												result = result
														+ '         <td><div><span>'
														+ opt.workExperience
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
												} else {
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
													result = result
															+ "<option value='Status'>"
															+ opt.submissionStatus
															+ "</option><option value='Reject'>Reject</option>";
													if (opt.submissionStatus == 'OFFERACCEPTED'
															|| opt.submissionStatus == 'CLIENT_OFFERACCEPTED') {
														result = result
																+ '<option value="backout">Back-Out</option>';
													}
													result = result
															+ '</select>';
													result = result
															+ '</div></td>';
												}
											} else {
												result = result
														+ '<td><div class="tblwidth-150"><span>'
														+ opt.submissionStatus
														+ '</span>';
											}
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
							"oLanguage" : {
								"sSearch" : "",
								"sZeroRecords" : "",
								"sEmptyTable" : "",
								"sProcessing" : "<img src='resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
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