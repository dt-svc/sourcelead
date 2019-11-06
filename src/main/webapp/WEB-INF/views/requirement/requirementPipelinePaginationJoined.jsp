
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<script>
	function requirementPipelinePaginationJoined(url, requirementId, submissionType) {
		var atsUrl ='${SourceLeadWeb_atsUrl}';
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
								pages : 1
							}),
							"columns" : [ {
								"data" : "name"
							}, {
								"data" : "email"
							}, {
								"data" : "phNo"
							},  {
								"data" : "resume"
							}, {
								"data" : "appliedOn"
							},{
								"data" : "source"
							}, {
								"data" : "joinDate"
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
									},{
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
									},{
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
									},{
										"targets" : colIndex++,
										"data" : "joinDate",
										"render" : function(data, type, opt,
												meta) {
											var result = "";
											if (opt.joinedDateStr != null
													&& opt.joinedDateStr != ''
													&& opt.joinedDateStr != 'undefined') {
												result = result
														+ '         <td><div><span>'
														+ opt.joinedDateStr
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
											if(workflowConfigured == 'false'){// Default Workflow Starts here if workflowConfigured is false.
											if (opt.requirementCurrentStatus != 'SUBMITTED'
													&& opt.requirementCurrentStatus != 'CLOSED'
													&& opt.requirementCurrentStatus != 'REJECTED'
													&& opt.requirementCurrentStatus != 'CANCEL'
													&& opt.requirementCurrentStatus != 'DRAFT'
													&& opt.requirementCurrentStatus != 'ONHOLD'
													&& opt.requirementCurrentStatus != 'FILLEDCLOSED'
													&& opt.requirementCurrentStatus != 'UNFILLEDCLOSED') {
												if (opt.submissionStatus == 'JOINED') {
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
										 } // Default Workflow Ends here.
										 else{ // Custom Workflow Starts here.
												
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
										     $.each( opt.workflowStagesMap, function(val, name ) {
											     if(opt.submissionDisplayStatus != name ){
										    	    result = result+"<option value='"+val+"'>"+name+"</option>";
											     }
										    	});
											 result = result
															+ '</select>';
													result = result
															+ '</div></td>';
										 
										 }// Custom Workflow Ends here.
										 
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