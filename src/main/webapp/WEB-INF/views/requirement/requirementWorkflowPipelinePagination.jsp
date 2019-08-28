
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<div id="popupSaveId" class="popupWrap popupWrap2" style="display: none;"></div>

<script>
var workflowConfigured = '${workflowConfigured}';
var columnPropArr=[];
var columnDefs =[];
var searchVal = "";
var  isWorkflowConfiured=true;

function requirementWorkflowPipelineList(url, requirementId, submissionType, columnsMap){
	var clientSubmissions = '${clientSubmissions}';
	  var isSpocActive = '${isSpocActive}';
	  var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	  $("#status-data").removeAttr("style");
	  $("#status-data").empty();
	  
	  var locationList ='${SourceLeadWeb_dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  requirementPipelineListDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  requirementPipelineListDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  requirementPipelineListDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  requirementPipelineListDate = 'YYYY-DD-MM'; 
	  } 
 	 var result = "";
 	 
 	result = result +'<div class="side_mask"><div class="viewCandidate_close addApplicant_close addInterview_close submitToClient_close addOffer_close"></div></div>';
	 	result = result +'<span class="applicant_export mr">';
		 '<sec:authorize access="hasAnyRole(\'ROLE_EXPORT_PIPELINE\')">';
	      result = result +'<a title="Export" onClick="piplineexport();"class="Clo1"><i class="glyphicon glyphicon-export expIcon"></i></a>';
	      result = result +'<div class="export_pipline" style="display:none">';
	      result = result +'<ul>';
	      result = result +'<li onclick="exportPipelineToPdf('+requirementId+',\''+submissionType+'\');"><i class="fa fa-file-pdf-o text-danger"></i>&nbsp;&nbsp;PDF</li>';
	      result = result +' <li onclick="exportPipelineToExcel('+requirementId+',\''+submissionType+'\');"><i class="fa fa-file-excel-o text-primary"></i>&nbsp;&nbsp;Excel</li>';
	      result = result +' </ul>';
	      result = result +'</div>';
	    '</sec:authorize>';
	    result = result +'</span>';
 	
	  result = result + '<div class="clearfix"></div>';
	  result = result + '<div class="require_wrap_table">';
	  result = result + '<div class="clerfix">';
	  result = result + " <div class='remove_search searchCustom'>";
		result = result
				+ " <input type='text' placeholder='Search' id='submissionDataTableSearch'/>";
		result = result + "</div>";
	  result = result + '<table id="requirementsPagination" class="display" cellspacing="0" width="100%"">';
	  result = result + '<thead>';
	  result = result + '<tr>';
	  columnPropArr=[]; // empty the values;
	  $.map( columnsMap, function( colProp, colName ) {
		  if(colName == 'DOB' || colName == 'Applied on'){
			  result = result + '<th>'+colName+'<br>(<small class="custom-small">'+requirementPipelineListDate+'</small>)</th>';
			  columnPropArr.push(colProp);
		  }else{
				  result = result + '<th>'+colName+'</th>';
			  columnPropArr.push(colProp);
		  }
	   });
	  result = result + '         </tr>';
	  result = result + '       </thead>';
	  result = result + '       <tbody>';
    $("#status-data").append(result);
    // below is line of searchval.
    if (searchVal) $("#submissionDataTableSearch").val(searchVal);
    requirementWorkflowPipelinePagination(url, requirementId, submissionType, columnsMap)
    result = result + '       </tbody>';
    result = result + '     </table>';
    result = result + '   </div>';
    result = result + '   </div>';
    prepareAllWorkflowCounts(requirementId);
}

	function requirementWorkflowPipelinePagination(url, requirementId, submissionType, columnsMap) {
		var atsUrl ='${SourceLeadWeb_atsUrl}';
		var orgLocationId = '${SourceLeadWeb_orgLocationId}';
		var clientSubmissions = '${clientSubmissions}';
		var isSpocActive = '${isSpocActive}';
		var isManager = '${isManager}';
		var isAdmin = '${isAdmin}';
		var colIndex = 0;
		  var columns = [];
		    for (var i in columnPropArr) {
		      columns.push({data: columnPropArr[i]});
		    }

		  
		    columnDefs = [] // empty the array values.
     for (var i in columnPropArr) {
		    if(columnPropArr[i] =="candidateName" ){
		    	columnDefs.push(
		    			{
							"targets" : colIndex++,
							"data" : "candidateName",
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
						});
		    	}else if(columnPropArr[i] == "candidateEmail" ){
		    		columnDefs.push({
						"targets" : colIndex++,
						"data" : "candidateEmail",
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
					});
		    	
		   	    } else if(columnPropArr[i] == "phoneNumber" ){
			    		columnDefs.push({
							"targets" : colIndex++,
							"data" : "phoneNumber",
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
						});	
			    } else if(columnPropArr[i] == "alternateNumber" ){
		 		columnDefs.push({
						"targets" : colIndex++,
						"data" : "alternateNumber",
						"render" : function(data, type, opt,
								meta) {
							var result = "";
							if (opt.alternateNumber != null
									&& opt.alternateNumber != ''
									&& opt.alternateNumber != 'undefined') {
								result = result
										+ '         <td><div><span>'
										+ opt.alternateNumber
										+ '</span></div></td>';
							} else {
								result = result
										+ '         <td><div><span>--</span></div></td>';
							}
							return result;
						}
					});	
		 }else if(columnPropArr[i] == "location" ){
			 		columnDefs.push({
							"targets" : colIndex++,
							"data" : "location",
							"render" : function(data, type, opt,
									meta) {
								var result = "";
								if (opt.location != null
										&& opt.location != ''
										&& opt.location != 'undefined') {
									result = result
											+ '         <td><div><span>'
											+ opt.location
											+ '</span></div></td>';
								} else {
									result = result
											+ '         <td><div><span>--</span></div></td>';
								}
								return result;
							}
						});	
		 }else if(columnPropArr[i] == "submittedQuestionrUuid" ){
				 		columnDefs.push({
								"targets" : colIndex++,
								"data" : "submittedQuestionrUuid",
								"render" : function(data, type, opt,
										meta) {
									var result = "";
									if (opt.submittedQuestionrUuid != null
											&& opt.submittedQuestionrUuid != ''
											&& opt.submittedQuestionrUuid != 'undefined') {
										result = result
												+ '         <td><div><a href ="'+ opt.submittedQuestionrUuid+'" target="_blank">View Submitted</a></div></td>';
												
									} else {
										result = result
												+ '         <td><div>--</div></td>';
									}
									return result;
								}
						   });	
			 }else if(columnPropArr[i] == "dateOfBirthStr" ){
			 		columnDefs.push({
						"targets" : colIndex++,
						"data" : "dateOfBirthStr",
						"render" : function(data, type, opt,
								meta) {
							var result = "";
							if (opt.dateOfBirthStr != null
									&& opt.dateOfBirthStr != ''
									&& opt.dateOfBirthStr != 'undefined') {
								result = result
										+ '         <td><div><span>'
										+ opt.dateOfBirthStr
										+ '</span></div></td>';
							} else {
								result = result
										+ '         <td><div><span>--</span></div></td>';
							}
							return result;
						}
					});	
		 }else if(columnPropArr[i] == "workExperience" ){
		 		columnDefs.push({
					"targets" : colIndex++,
					"data" : "workExperience",
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
				});	
	 }else if(columnPropArr[i] == "currentCtc" ){
	 		columnDefs.push({
				"targets" : colIndex++,
				"data" : "currentCtc",
				"render" : function(data, type, opt,
						meta) {
					var result = "";
					if (opt.currentCtc != null
							&& opt.currentCtc != ''
							&& opt.currentCtc != 'undefined') {
						result = result
								+ '         <td><div><span>'
								+ opt.currentCtc
								+ '</span></div></td>';
					} else {
						result = result
								+ '         <td><div><span>--</span></div></td>';
					}
					return result;
				}
			});	
 		}else if(columnPropArr[i] == "expectedCTC" ){
	 		columnDefs.push({
				"targets" : colIndex++,
				"data" : "expectedCTC",
				"render" : function(data, type, opt,
						meta) {
					var result = "";
					if (opt.expectedCTC != null
							&& opt.expectedCTC != ''
							&& opt.expectedCTC != 'undefined') {
						result = result
								+ '         <td><div><span>'
								+ opt.expectedCTC
								+ '</span></div></td>';
					} else {
						result = result
								+ '         <td><div><span>--</span></div></td>';
					}
					return result;
				}
			});	
 		}else if(columnPropArr[i] == "candidateScore" ){
	 		columnDefs.push({
				"targets" : colIndex++,
				"data" : "candidateScore",
				"render" : function(data, type, opt,
						meta) {
					var result = "";
					if (opt.candidateScore != null
							&& opt.candidateScore != ''
							&& opt.candidateScore != 'undefined') {
						result = result
								+ '         <td><div><span>'
								+ opt.candidateScore
								+ '</span></div></td>';
					} else {
						result = result
								+ '         <td><div><span>--</span></div></td>';
					}
					return result;
				}
			});	
 		}else if(columnPropArr[i] == "noticePeriod" ){
	 		columnDefs.push({
				"targets" : colIndex++,
				"data" : "noticePeriod",
				"render" : function(data, type, opt,
						meta) {
					var result = "";
					if (opt.noticePeriod != null
							&& opt.noticePeriod != ''
							&& opt.noticePeriod != 'undefined') {
						result = result
								+ '         <td><div><span>'
								+ opt.noticePeriod
								+ '</span></div></td>';
					} else {
						result = result
								+ '         <td><div><span>--</span></div></td>';
					}
					return result;
				}
			});	
 		}else if(columnPropArr[i] == "createdDateStr" ){
	 		columnDefs.push({
				"targets" : colIndex++,
				"data" : "createdDateStr",
				"render" : function(data, type, opt,
						meta) {
					var result = "";
					if (opt.createdDateStr != null
							&& opt.createdDateStr != ''
							&& opt.createdDateStr != 'undefined') {
						result = result
								+ '         <td><div><span>'
								+ opt.createdDateStr
								+ '</span></div></td>';
					} else {
						result = result
								+ '         <td><div><span>--</span></div></td>';
					}
					return result;
				}
			});	
 		}else if(columnPropArr[i] == "createdUser" ){
	 		columnDefs.push({
				"targets" : colIndex++,
				"data" : "createdUser",
				"render" : function(data, type, opt,
						meta) {
					var result = "";
					if (opt.createdUser != null
							&& opt.createdUser != ''
							&& opt.createdUser != 'undefined') {
						result = result
								+ '         <td><div><span>'
								+ opt.createdUser
								+ '</span></div></td>';
					} else {
						result = result
								+ '         <td><div><span>--</span></div></td>';
					}
					return result;
				}
			});	
 		}else if(columnPropArr[i] == "submissionStatus" ){
	 		columnDefs.push({
				"targets" : colIndex++,
				"data" : "status",
				"render" : function(data, type, opt,
						meta) {
					var result = "";
					if(workflowConfigured == 'false'){
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
							if(opt.workflowStages != null){
								//result = result+"<option value=''>xxxxx</option>";
								for(var i=0; i< opt.workflowStages.length; i++){
									result = result+"<option value='"+opt.workflowStages[i]+"'>"+opt.workflowStages[i]+"</option>";
								}	
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
					}else{
						

						
						/// if workflow is configured preparing the status drop down values.
						/*  var workflowStagesMap = opt.workflowStagesMap;  SRINIVA SABBU.
						console.log(workflowStagesMap); */
						//Below blcok excuted when current is REJECTED and BACKOUT stages.
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
						} 
						
				else{// else blcok excuted other than REJECTED and BACKOUT stages.
					
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
				     // if not a client requirement remove option from the dropdown options list.
						if(clientSubmissions != 'Y' && isSpocActive != 'Y' ){
							delete opt.workflowStagesMap["SUBMIT_TO_CLIENT"];
					 }
				     $.each( opt.workflowStagesMap, function(val, name ) {
					     if(opt.submissionDisplayStatus != name ){
				    	    result = result+"<option value='"+val+"'>"+name+"</option>";
					     }
				    	});
					 result = result
									+ '</select>';
							result = result
									+ '</div></td>';
					}
						
					
						
					}
					return result;
				}
			});	
 		}
	}
		  /*   var columnDefs =[];
		  //  for (var j in columnPropArr) {
		    //	alert(columnPropArr[j])
		    	columnDefs.push(
		    			
		    			{
							"targets" : colIndex++,
							"data" :columnPropArr[colIndex],
							"render" : function(data, type, opt,
									meta) {
								console.log(meta.settings); 
								//console.log(meta.settings.columns[meta.col].name)
								console.log(opt.submissionStatus);
							 	alert(meta.settings.aoColumns[colIndex].data)
								console.log("row is "+meta.row);
								console.log("col is"+meta.col);
								console.log("meta");
								console.log(meta); 
								//console.log(columnPropArr[meta.col]); 
								 console.log(data); 
								console.log(type); 
								console.log("columnPropArr[colIndex]"+columnPropArr[colIndex]); 
								var result = "";
							    $("#emptyPipelinelist").val(1);
							
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
										+ ');" class="tbldata-dotdot proj_show positionRelative"><span class="pull-left" title ="'+opt[columnPropArr[meta.col]]+'">'
										+ opt[columnPropArr[meta.col]]
										+ '</span><i class="fa fa-binoculars"></i>';
								result = result + ' </div></td>';
								alert(result);
					     	return result;
							}
						}
		    			
		    	
		    	
		    	); */
			   // }

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
							"columns" : columns,
							"columnDefs" :columnDefs,
							"scrollCollapse" : false,
							"initComplete" : function(settings, json) {
								$('#tab-1').hide();
							},
							"lengthMenu" : [ [ 10, 15, 20 ], [ 10, 15, 20 ] ],
							"aaSorting" : [ [ 1, 'asc' ] ],
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
				searchVal = $("#submissionDataTableSearch").val();
				requirementWorkflowPipeline(requirementId, submissionType);
			}
		});
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


