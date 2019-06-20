
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<div id="popupSaveId" class="popupWrap popupWrap2" style="display: none;"></div>

<script>
	function requirementPipelinePagination(url, requirementId, submissionType) {
		var atsUrl ='${SourceLeadWeb_atsUrl}';
		var orgLocationId = '${SourceLeadWeb_orgLocationId}';
		var clientSubmissions = '${clientSubmissions}';
		var isSpocActive = '${isSpocActive}';
		var isManager = '${isManager}';
		var isAdmin = '${isAdmin}';
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
											$("#emptyPipelinelist").val(1);
											'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_APPLICANT\')">';
											result = result + '<input type = hidden id ="submissionId" value='+opt.id+'>';
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
												+ '         <td><div><span><a download class="glyphicon glyphicon-download-alt" title="Download" href=\"'+opt.fileURL+'\"></a><a class="fa fa-eye ml10" title="View" onclick="viewFile(\''+opt.fileURL+'\',\''+opt.fileName+'\',\''+opt.fileContentType+'\');"></a></span></div></td>';
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
													if (opt.submissionStatus != 'JOINED') {
														result = result
																+ ' <td><div><span class="showRate '
																+ colorHint
																+ '" onclick="getRateListDiv('
																+ opt.id
																+ ','
																+ opt.candidateId
																+ ');">'
																+ opt.proposedSubmissionRate
																+ '</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
													} else {
														var expexted = (opt.proposedSubmissionRate != ''
																&& opt.proposedSubmissionRate != 'undefined' && opt.proposedSubmissionRate != null) ? opt.proposedSubmissionRate
																: '<i class="glyphicon fa fa-money"></i>';
														result = result
																+ ' <td><div><span class="showRate">'
																+ expexted
																+ '</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
													}
													'</sec:authorize>';
													'<sec:authorize access="!hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
													result = result
															+ ' <td><div><span class="showRate '+colorHint+'">'
															+ opt.proposedSubmissionRate
															+ '</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
													'</sec:authorize>';
												} else {
													var expexted = (opt.expectedCTC != ''
															&& opt.expectedCTC != 'undefined' && opt.expectedCTC != null) ? opt.expectedCTC
															: '<i class="glyphicon fa fa-money"></i>';
													'<sec:authorize access="hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
													if (opt.submissionStatus != 'JOINED') {
														result = result
																+ ' <td><div><span class="showRate '
																+ colorHint
																+ '" onclick="getRateListDiv('
																+ opt.id
																+ ','
																+ opt.candidateId
																+ ');">'
																+ expexted
																+ '</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
													} else {
														var expexted = (opt.expectedCTC != ''
																&& opt.expectedCTC != 'undefined' && opt.expectedCTC != null) ? opt.expectedCTC
																: '<i class="glyphicon fa fa-money"></i>';
														result = result
																+ ' <td><div><span class="showRate">'
																+ expexted
																+ '</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
													}
													'</sec:authorize>';
													'<sec:authorize access="!hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
													result = result
															+ ' <td><div><span class="showRate '+colorHint+'">'
															+ expexted
															+ '</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
													'</sec:authorize>';
												}
											} else {
												if (opt.expectedRate != null
														&& opt.expectedRate != ''
														&& opt.expectedRate != 'undefined') {
													'<sec:authorize access="hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
													if (opt.submissionStatus != 'JOINED') {
														result = result
																+ ' <td><div><span class="showRate '
																+ colorHint
																+ '" onclick="getRateListDiv('
																+ opt.id
																+ ','
																+ opt.candidateId
																+ ');">'
																+ opt.expectedRate
																+ '</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
													} else {
														var expexted = (opt.expectedRate != ''
																&& opt.expectedRate != 'undefined' && opt.expectedRate != null) ? opt.expectedRate
																: '<i class="glyphicon fa fa-money"></i>';
														result = result
																+ ' <td><div><span class="showRate">'
																+ expexted
																+ '</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
													}
													'</sec:authorize>';
													'<sec:authorize access="!hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
													result = result
															+ ' <td><div><span class="showRate '+colorHint+'">'
															+ opt.expectedRate
															+ '</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
													'</sec:authorize>';
												} else {
													var expexted = (opt.expectedCTC != ''
															&& opt.expectedCTC != 'undefined' && opt.expectedCTC != null) ? opt.expectedCTC
															: '<i class="glyphicon fa fa-money"></i>';
													'<sec:authorize access="hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
													if (opt.submissionStatus != 'JOINED') {
														result = result
																+ ' <td><div><span class="showRate '
																+ colorHint
																+ '" onclick="getRateListDiv('
																+ opt.id
																+ ','
																+ opt.candidateId
																+ ');">'
																+ expexted
																+ '</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
													} else {
														var expexted = (opt.expectedCTC != ''
																&& opt.expectedCTC != 'undefined' && opt.expectedCTC != null) ? opt.expectedCTC
																: '<i class="glyphicon fa fa-money"></i>';
														result = result
																+ ' <td><div><span class="showRate">'
																+ expexted
																+ '</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
													}
													'</sec:authorize>';
													'<sec:authorize access="!hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
													result = result
															+ ' <td><div><span class="showRate '+colorHint+'">'
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
														+ ' <td><div style="width:150px;"><div class="tbldata-dotdot-vsm pull-left" title="'+opt.createdUser+''+orgName+''+candidateSource+'" class="pull-left">'
														+ opt.createdUser + ''
														+ orgName + '<br/>'
														+ candidateSource
														+ '</div>'
														if(opt.orgName == null || opt.orgName == ''){
															 if(isManager == "true" || isAdmin == "true"){
																 result = result +'<i class="fa fa-pencil pull-left mt2 cursorPointer" onClick="applicantsourceHandler('+requirementId+','+opt.id+');"></i>';
															 }
															 }
														+'</div>';
												+'</td>';
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
															+ opt.submissionDisplayStatus
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
															+ opt.submissionStatus
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
															+ opt.submissionDisplayStatus
															+ "</option>";
													if (opt.submissionStatus == 'SUBMISSION') {
														'<sec:authorize access="hasRole(\'ROLE_SHORTLIST\')">';
														result = result
																+ "<option value='shortlist'>Shortlist</option>";
														'</sec:authorize>';
													}
													if (opt.submissionStatus == 'OFFERMADE'
															|| opt.submissionStatus == 'OFFERACCEPTED') {
														result = result
																+ "<option value='Joined'>Joined</option>";
													}
													if (opt.previousSubmissionStatus != null
															&& opt.previousSubmissionStatus == 'SUBMISSION'
															&& opt.submissionStatus != 'SHORTLIST'
															&& opt.submissionStatus != 'INTERVIEW'
															&& opt.submissionStatus != 'SUBMIT_TO_CLIENT'
															&& opt.submissionStatus != 'CLIENT_INTERVIEW') {
														result = result
																+ "<option value='Status'>Submission</option>";
													}
													 if (opt.previousSubmissionStatus != null
															&& opt.previousSubmissionStatus == 'SHORTLIST'
															&& opt.submissionStatus == 'ONHOLD') {
														
														result = result
																+ "<option value='shortlist'>Shortlist</option>";
														
													} 
													if (opt.previousSubmissionStatus != null
															&& opt.previousSubmissionStatus == 'INTERVIEW'
															&& opt.submissionStatus != 'SELECTED_ON_INTERVIEW'
															&& opt.submissionStatus != 'SUBMIT_TO_CLIENT'
															&& opt.submissionStatus != 'OFFERMADE'
															&& opt.submissionStatus != 'OFFERACCEPTED') {
														result = result
																+ "<option value='Interview'>INTERVIEW</option>";
													}
													 if (opt.previousSubmissionStatus != null
															&& opt.previousSubmissionStatus == 'SELECTED_ON_INTERVIEW'
															&& opt.submissionStatus == 'ONHOLD') {

														result = result
																+ "<option value='Selected on Interview'>Selected on Interview</option>";
													} 
													if (opt.previousSubmissionStatus != null
															&& opt.previousSubmissionStatus == 'OFFERMADE'
															&& opt.submissionStatus != 'JOINED') {
														result = result
																+ "<option value='Offer Made'>OFFERMADE</option>";
													}
													if (opt.previousSubmissionStatus != 'CLIENT_INTERVIEW'
															&& opt.previousSubmissionStatus != null
															&& opt.previousSubmissionStatus == 'CLIENT_INTERVIEW'
															&& opt.submissionStatus != 'CLIENT_INTERVIEW') {
														result = result
																+ "<option value='Client Interview'>Client Interview</option>";
													}
													if (opt.submissionStatus == 'SUBMISSION') {
														result = result
																+ "<option value='On Hold'>On Hold</option>";
														result = result
																+ "<option value='NoResponse'>NoResponse</option>";
													}
													if (opt.submissionStatus == 'SHORTLIST') {
														result = result
																+ "<option value='On Hold'>On Hold</option>";
														result = result
																+ "<option value='NoResponse'>NoResponse</option>";
													}
													if (opt.submissionStatus == 'INTERVIEW') {
														result = result
																+ "<option value='On Hold'>On Hold</option>";
														result = result
																+ "<option value='NoResponse'>NoResponse</option>";
													}
													if (opt.submissionStatus == 'OFFERMADE' && opt.previousSubmissionStatus != 'CLIENT_INTERVIEW') {
														result = result
																+ "<option value='On Hold'>On Hold</option>";
														result = result
																+ "<option value='NoResponse'>NoResponse</option>";
													}

													if (opt.submissionStatus == 'SELECTED_ON_INTERVIEW' && opt.previousSubmissionStatus !=null) {
														result = result
																+ "<option value='On Hold'>On Hold</option>";
														result = result
																+ "<option value='NoResponse'>NoResponse</option>";
													}

													if (opt.submissionStatus == 'CLIENT_INTERVIEW'
															&& opt.submissionStatus != 'ONHOLD'
															&& opt.submissionStatus != 'JOINED'
															&& opt.submissionStatus != 'NORESPONSE') {
														result = result
																+ "<option value='On Hold'>On Hold</option>";
														result = result
														+ "<option value='NoResponse'>NoResponse</option>";
													}
													/* if (opt.previousSubmissionStatus == 'CLIENT_INTERVIEW'
															&& opt.submissionStatus != 'SUBMIT_TO_CLIENT'
															&& opt.submissionStatus != 'NORESPONSE'
															&& opt.submissionStatus != 'JOINED'
															&& opt.submissionStatus != 'ONHOLD') {
														result = result
																+ "<option value='NoResponse'>NoResponse</option>";
													} */
													result = result
															+ "<option value='Reject'>Reject</option>";
													if (opt.submissionStatus == 'OFFERMADE'
															|| opt.submissionStatus == 'OFFERACCEPTED'
															|| opt.submissionStatus == 'JOINED'
															|| opt.submissionStatus == 'CLIENT_OFFERMADE'
															|| opt.submissionStatus == 'CLIENT_OFFERACCEPTED') {
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
							"createdRow" : function(row, data, index) {
								row.setAttribute('id', "job_" + data.id);
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
		var submissionId = '${submissionId}';
		var candidateId = '${candidateId}';
		var requirementId = '${requirementId}';
		var submissionStatus = '${submissionStatus}';
		if (submissionId != null && submissionId != ''
				&& submissionId != 'undefined' && requirementId != null
				&& requirementId != '' && requirementId != 'undefined'
				&& submissionStatus == "REJECTED" && submissionType != "all"
				&& submissionType != "DUPLICATE_APPLICANTS"
				&& submissionType != "REQUEST_SENT"
				&& submissionType != "SHORTLIST"
				&& submissionType != "SUBMIT_TO_CLIENT"
				&& submissionType != "INTERVIEW"
				&& submissionType != "OFFERMADE") {
			$("#detailsTab").removeClass("current");
			$('li[name=applicantNotification]').addClass("current");
			requirementStatusBar(requirementId);
			$("#applicantli").addClass("reqAct");
			// 		    	   $("#submitToClientli").addClass("reqAct");
			requirementPipeline(requirementId, 'all');
		} else if (submissionId != null && submissionId != ''
				&& submissionId != 'undefined' && requirementId != null
				&& requirementId != '' && requirementId != 'undefined'
				&& submissionStatus == "SUBMIT_TO_CLIENT"
				&& submissionType != "all"
				&& submissionType != "DUPLICATE_APPLICANTS"
				&& submissionType != "REQUEST_SENT"
				&& submissionType != "SHORTLIST"
				&& submissionType != "REJECTED"
				&& submissionType != "INTERVIEW"
				&& submissionType != "OFFERMADE") {
			$("#detailsTab").removeClass("current");
			$('li[name=applicantNotification]').addClass("current");
			requirementStatusBar(requirementId);
			$("#applicantli").removeClass("reqAct");
			$("#submitToClientli").addClass("reqAct");
			requirementPipeline(requirementId, 'SUBMIT_TO_CLIENT');
			if ('${subStatus}' != null && '${subStatus}' != ''
					&& '${subStatus}' != 'undefined'
					&& '${subStatus}' == 'SUBMISSION') {
				getRateListDiv(submissionId, candidateId)
			}

		} else if (submissionId != null && submissionId != ''
				&& submissionId != 'undefined' && requirementId != null
				&& requirementId != '' && requirementId != 'undefined'
				&& submissionType != "all"
				&& submissionType != "DUPLICATE_APPLICANTS"
				&& submissionType != "REQUEST_SENT"
				&& submissionType != "SHORTLIST"
				&& submissionType != "REJECTED"
				&& submissionType != "INTERVIEW"
				&& submissionType != "OFFERMADE" && submissionStatus == "all") {
			$("#detailsTab").removeClass("current");
			$('li[name=applicantNotification]').addClass("current");
			requirementStatusBar(requirementId);
			$("#applicantli").removeClass("reqAct");
			$("#submitToClientli").addClass("reqAct");
			requirementPipeline(requirementId, 'all');
			if ('${subStatus}' != null && '${subStatus}' != ''
					&& '${subStatus}' != 'undefined'
					&& '${subStatus}' == 'SUBMISSION') {
				getRateListDiv(submissionId, candidateId)
			}
		} else if (submissionId != null && submissionId != ''
				&& submissionId != 'undefined' && candidateId != null
				&& candidateId != '' && candidateId != 'undefined'
				&& submissionType != "all"
				&& submissionType != "DUPLICATE_APPLICANTS"
				&& submissionType != "REQUEST_SENT"
				&& submissionStatus != "SUBMIT_TO_CLIENT"
				&& submissionStatus != "all" || submissionStatus == "REJECTED") {
			applicantQuickViewProfile(submissionId, candidateId);
			getSubmissionDetailsSideBar(submissionId, candidateId,
					requirementId);
		}
	}
</script>
<script type="text/javascript">

function updateUserPop(submissionId){
$("#popupSaveId").empty();
var result = "";	
result = result +'<div class="clearfix">                                 ';
result = result +'<div class="popupHead clearfix"> Assign Employee                                                                      ';
result = result +'	<i class="closePop pull-right clPopClose" onClick="applicantsourceHandlerClose()"></i>                              ';
result = result +'</div>                                                                                                                ';
result = result +'<div class="cleaffix col-md-12 mt mb">                                                                                               ';
result = result +'	<label>Assign Employee</label>                                                                                      ';
result = result +'	<div class="mt5 width100percnt">                                                                                    ';
result = result +'	<select class="chosen-select width100percnt" data-placeholder="Select Assign Employee" id="assignedNewPerson">      ';
result = result +'		<option>Employee</option>                                                                                       ';
result = result +'	</select>                                                                                                           ';
result = result +'	</div>                                                                                                              ';
result = result +'</div>                                                                                                                ';
result = result +'<div class="col-md-12 mb mt5">                                                                                            ';
result = result +'	<div class="pull-right">                                                                                            ';
result = result +'		<button type="button" class="btn btn-primary" onclick="applicantsourceSave('+submissionId+')">Assign</button>                   ';
result = result +'	        <button type="button" class="btn btn-default ml" onClick="applicantsourceHandlerClose()">Cancel</button>    ';
result = result +'	 </div>                                                                                                             ';
result = result +'</div>                                                                                                                ';
result = result +'</div>                                                                                                                ';
$("#popupSaveId").append(result);
$('#assignedNewPerson').chosen();
}
</script>
	
<script>
function applicantsourceHandler(reqId,submissionId) {
	$(".mask, #popupSaveId").fadeIn();
	updateUserPop(submissionId);
	if(reqId != '' && reqId != 'undefined' && reqId != null){
		$.ajax({
	           type : "POST",
	           url: atsUrl+"/updateApplicantSourceMember?&reqId="+reqId,    
	            success :function(data) {
		                var json = JSON.parse(data);
	                $('#assignedNewPerson').empty();
	                $('#assignedNewPerson').append('<option value="">--Select Assign Employee--</option>');
	                for (var i = 0; i < json.length; i++) {
	                    var opt = json[i];
	                    $('#assignedNewPerson').append('<option value=' + opt.assignedUserIdTem + ' >' + opt.assignedUserName + '</option>');
	                }
	                $('#assignedNewPerson').trigger("chosen:updated");
	            },
	      }); 
	}
}
function applicantsourceHandlerClose() {
	$(".mask, #popupSaveId").fadeOut();
}
function applicantsourceSave(submissionId){
	var userId = $("#assignedNewPerson").val();
	if(userId !=null && userId !=''){
	$.ajax({
        type : "POST",
        url: atsUrl+"/editApplicantSourceName?&submissionId="+submissionId+"&userId="+userId,    
         success :function(response) {
        	 applicantsourceHandlerClose();
         },
   }); 
	}
}
</script>


