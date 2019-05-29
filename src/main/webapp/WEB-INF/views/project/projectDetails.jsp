<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!-- script for dateformat in datepicker End -->
<script src="/resources/js/editor.js"></script>
<link href="/resources/css/editor.css" type="text/css" rel="stylesheet"/>
		<!-- <script>
			$(document).ready(function() {
				$("#txtEditor1").Editor();
				$("#txtEditor1").find(".Editor-editor").data-validation('validate_length length2-5000');
				$("#txtEditor1").find(".Editor-editor").data-validation-optional('rule-required',true);
			});
       </script>  -->

<div id="projectDetailsLoading"></div>
       
       
<!-- this script is for Project Details functionality at Projects screen -->
<script>
	function projectDetails(projectId) {
		var projectClientUUID = '${projectClientUUID}';
		$("#projectDetailsLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		var sourceleadUrl = '${sourceleadUrl}';
				$.ajax({
					type : "POST",
					url : sourceleadUrl	+ "/getProjectById?${_csrf.parameterName}=${_csrf.token}&ProjectId="+projectId+"&projectClientUUID="+projectClientUUID,
					success : function(json) {
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
						var invoiceFrequency = "";
						var reportingManagerAsApprover = "";
						var ticketNumberRequired = "";
						var taskCommentsRequired = "";
						
						if (json.isReportingManagerAsApprover != null && json.isReportingManagerAsApprover != '' && json.isReportingManagerAsApprover != 'undefined'
							&& json.isReportingManagerAsApprover == 'Y') {
							reportingManagerAsApprover = "YES"
						}else{
							reportingManagerAsApprover = "NO"
						}
						
						if (json.isTicketNumberRequired != null && json.isTicketNumberRequired != '' && json.isTicketNumberRequired != 'undefined'
							&& json.isTicketNumberRequired == 'Y') {
							ticketNumberRequired = "YES"
						}else{
							ticketNumberRequired = "NO"
						}
						
						if (json.isTaskCommentsRequired != null && json.isTaskCommentsRequired != '' && json.isTaskCommentsRequired != 'undefined'
							&& json.isTaskCommentsRequired == 'Y') {
							taskCommentsRequired = "YES"
						}else{
							taskCommentsRequired = "NO"
						}
						
						
						if (json.isTimesheetAttachmentRequired != null && json.isTimesheetAttachmentRequired != '' && json.isTimesheetAttachmentRequired != 'undefined'
								&& json.isTimesheetAttachmentRequired == '1') {
							isTimesheetAttachmentRequired = "YES";
					     } else {
					    	 isTimesheetAttachmentRequired = "NO";
					    }
						
						
						if (json.allowFreeLance != null && json.allowFreeLance != '' && json.allowFreeLance != 'undefined'
							&& json.allowFreeLance == 'true') {
							allowFreeLance = "YES";
				     	} else {
				     		allowFreeLance = "NO";
				    	}

						if (json.allowRemote != null && json.allowRemote != '' && json.allowRemote != 'undefined'
							&& json.allowRemote == 'true') {
							allowRemote = "YES";
				     	} else {
				     		allowRemote = "NO";
				    	}
						
						if (json.allowOvertime != null && json.allowOvertime != '' && json.allowOvertime != 'undefined'
							&& json.allowOvertime == 'true') {
							allowOvertime = "YES";
				     	} else {
				     		allowOvertime = "NO";
				    	}
						
						if (json.allowTimeEntryAfterDeadLine != null && json.allowTimeEntryAfterDeadLine != '' && json.allowTimeEntryAfterDeadLine != 'undefined'
							&& json.allowTimeEntryAfterDeadLine == 'true') {
							allowTimeEntryAfterDeadLine = "YES";
				     	} else {
				     		allowTimeEntryAfterDeadLine = "NO";
				    	}
						
						if (json.allowBidding != null && json.allowBidding != '' && json.allowBidding != 'undefined'
							&& json.allowBidding == 'true') {
							allowBidding = "YES";
				     	} else {
				     		allowBidding = "NO";
				    	}
						
						if (json.allowOffShore != null && json.allowOffShore != '' && json.allowOffShore != 'undefined'
							&& json.allowOffShore == 'true') {
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
						
						if(json.invoiceFrequency != null && json.invoiceFrequency !='' && json.invoiceFrequency != 'undefined'){
							invoiceFrequency = json.invoiceFrequency
						}else{
							invoiceFrequency = "--";
						}
						
				 		var proStatus ='';
						if(json.projectStatus == 'NOTSTARTED'){
							proStatus = 'Not Started';
						}else if(json.projectStatus == 'INPROGRESS'){
							proStatus = 'In-Progress';
						}else if(json.projectStatus == 'ONHOLD'){
							proStatus = 'On-Hold';
						}else if(json.projectStatus == 'COMPLETED'){
							proStatus = 'Completed';
						}else if(json.projectStatus == 'DROPPED'){
							proStatus = 'Dropped';
						} 
						
						var result = "";
						result = result	+ '<div class="project_tabs_inn_table">';
						result = result + '<div class="project_details">';
						result = result + '<div class="useredit useredit_a">';
						result  = result + '<div class="success_pop" id="selector2" style="display: none;">';
					    result  = result + '<i class="fa fa-check-circle"></i><span id="docMsgDiv2"></span>';
					    result  = result + '</div>';
						if((json.orgLocationId == '${SourceLeadWeb_orgLocations.id}') || ('${SourceLeadWeb_isGlobalAccess}' == 'Y') || (json.sharedProjectCreatedOrgLocId == '${SourceLeadWeb_orgLocations.id}') ){
							if(json.projectApprover == 'Y' || '${SourceLeadWeb_loginUserRole}' == 'ROLE_ADMIN'){
								result = result +'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_PROJECT\')">'; 
								result = result	+ '<a id="edit-user" href="javascript:editProjectDetails('+ projectId+ ')"><i class="glyphicon glyphicon-pencil"></i>Edit</a>';
			   		    		result = result +'</sec:authorize>';
							}
							
						}else{
							result = result +'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_PROJECT\')">'; 
							result = result	+ '<a id="edit-user" href="javascript:editClientProjectDetails('+ projectId+ ')"><i class="glyphicon glyphicon-pencil"></i>Edit</a>';
		   		    		result = result +'</sec:authorize>';
						}
						
	   		    		result = result + '</div>';
	   		    		
	   		    		result = result	+ '<div class="project_details_cta" id="ProjectIdDiv">';
						result = result + '<strong>Project Id:</strong>';
						result = result + '<span>'+projectSequeneNumber+'</span>';
						result = result + '<div class="clearfix"></div>';
						result = result + '</div>';
						
						result = result	+ '	<div class="project_details_cta" id="projectNameId">';
						result = result + '	<strong>Project name:</strong>';
						result = result + '	<span title="'+json.projectName+'">'+json.projectName+'</span><div class="clearfix"></div>';
						result = result + '	</div>';
						
						if(json.manager1Name != null && json.manager1Name != "" && json.manager1Name !="undefined"){
							result = result + '<div class="project_details_cta">';
							result = result + '<strong>Manager 1:</strong><span>';
							result = result + '	<span title="'+json.manager1Name+'">'+json.manager1Name+'</span><div class="clearfix"></div>';
							result = result + '	</div>';
						}
						

						if (json.manager2Name != null && json.manager2Name != "" && json.manager2Name != "undefined") {
							result = result	+ '<div class="project_details_cta">';
							result = result	+ '	<strong>Manager 2:</strong><span>';
							result = result + '	<span title="'+json.manager2Name+'">'+json.manager2Name+'</span><div class="clearfix"></div>';
							result = result	+ '</span><div class="clearfix"></div>';
							result = result + '	</div>';
						}
						
						
						if(json.expenseApprover1Name != null && json.expenseApprover1Name != "" && json.expenseApprover1Name !="undefined"){
							result = result + '<div class="project_details_cta">';
							result = result + '<strong>Expense Approver 1:</strong><span>';
							result = result + '	<span title="'+json.expenseApprover1Name+'">'+json.expenseApprover1Name+'</span><div class="clearfix"></div>';
							result = result + '	</div>';
						}
						

						if (json.expenseApprover2Name != null && json.expenseApprover2Name != "" && json.expenseApprover2Name != "undefined") {
							result = result	+ '<div class="project_details_cta">';
							result = result	+ '	<strong>Expense Approver 2:</strong><span>';
							result = result + '	<span title="'+json.expenseApprover2Name+'">'+json.expenseApprover2Name+'</span><div class="clearfix"></div>';
							result = result	+ '</span><div class="clearfix"></div>';
							result = result + '	</div>';
						}
						

						result = result + '<div class="project_details_cta">';
						result = result + '<strong>Timesheet Frequency:</strong><span>';
						result = result + '	<span title="'+invoiceFrequency+'">'+invoiceFrequency+'</span><div class="clearfix"></div>';
						result = result + '	</div>';
						
						result = result + '<div class="project_details_cta">';
						result = result + '<strong>Project For:</strong><span>';
						if(json.isProjectForTimeSheets == 'Y' && json.isProjectForExpenses =='Y'){
							result = result + '	<span>Timesheets & Expenses</span><div class="clearfix"></div>';
						}else if(json.isProjectForTimeSheets == 'Y' && json.isProjectForExpenses !='Y'){
							result = result + '	<span>Timesheets</span><div class="clearfix"></div>';
						}else if(json.isProjectForTimeSheets != 'Y' && json.isProjectForExpenses =='Y'){
							result = result + '	<span>Expenses</span><div class="clearfix"></div>';
						}
						result = result + '	</div>';
						
				 		
						 result = result + '<div class="project_details_cta">';
						result = result + '<strong>Timesheet Approval Mechanism:</strong><span>';
						   if (json.projectApprovalType != null && json.projectApprovalType != "" && json.projectApprovalType != "undefined") {
								result = result + '	<span>'+json.projectApprovalType+'</span><div class="clearfix"></div>';
							}else{
								result = result + '	<span>----</span><div class="clearfix"></div>';
							}
						result = result + '	</div>';
						
						
						result = result + '<div class="project_details_cta">';
						result = result + '<strong>Expemse Approval Mechanism:</strong><span>';
						   if (json.expenseApprovalType != null && json.expenseApprovalType != "" && json.expenseApprovalType != "undefined") {
								result = result + '	<span>'+json.expenseApprovalType+'</span><div class="clearfix"></div>';
							}else{
								result = result + '	<span>----</span><div class="clearfix"></div>';
							}
						result = result + '	</div>';  
 
						result = result + '<div class="project_details_cta">';
						result = result + '<strong>Client:</strong>';
						
						'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CLIENT\',\'ROLE_VIEW_EDIT_CLIENT\')">';
						 if (json.client != null && json.client != '' && json.client != 'undefined') {
							 result = result + '<span><a href="'+sourceleadUrl+'/clientDashboard?orgRelationUUID='+json.orgRelationUUID+'" title="'+clientName+'">'+clientName+' </a></span>';
						 }else{
							 result = result + '<span>'+clientName+'</span>';
						 }
						 '</sec:authorize>';
						
						'<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_CLIENT\',\'ROLE_VIEW_EDIT_CLIENT\')">';
						 result = result + '<span title="'+clientName+'">'+clientName+'</span><div class="clearfix"></div>';
						'</sec:authorize>';
						
						result = result + '<div class="clearfix"></div>';
						result = result + '</div>';
						
						
						result = result	+ '<div class="project_details_cta">';
						result = result + '<strong>Status:</strong><span>';
						result = result +  proStatus;
						result = result + '</span>';
						result = result	+ '</div><div class="clearfix"></div>';
						
						if(json.projectShortCode != null && json.projectShortCode != "" && json.projectShortCode != "undefined"){
						result = result	+ '<div class="project_details_cta">';
						result = result + '<strong>Project Short Code:</strong><span>';
						result = result + json.projectShortCode;
						result = result + '</span>';
						result = result	+ '</div><div class="clearfix"></div>';
						}
						
						result = result + '<input type="hidden" name="dbProjectStatus" id="dbProjectStatus" value="'+json.projectStatus+'" >';
						result = result + '<div class="clearfix"></div>';

						if((json.orgLocationId == '${SourceLeadWeb_orgLocations.id}') || ('${SourceLeadWeb_isGlobalAccess}' == 'Y') || (json.sharedProjectCreatedOrgLocId == '${SourceLeadWeb_orgLocations.id}')){
						
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
						result = result + '<strong>Reporting Manager will be T/E approver:</strong>';
						result = result + '<span>'+reportingManagerAsApprover+'</span><div class="clearfix"></div>';
						result = result + '<div class="clearfix"></div>';
						result = result + '</div>';
						
						result = result + '<div class="project_details_cta">';
						result = result + '<strong> Ref/Ticket Number Required In Time Sheets:</strong>';
						result = result + '<span>'+ticketNumberRequired+'</span><div class="clearfix"></div>';
						result = result + '<div class="clearfix"></div>';
						result = result + '</div>';
						
						result = result + '<div class="project_details_cta">';
						result = result + '<strong>Task Comments required In Time Sheets:</strong>';
						result = result + '<span>'+taskCommentsRequired+'</span><div class="clearfix"></div>';
						result = result + '<div class="clearfix"></div>';
						result = result + '</div>';
						
						result = result + '<div class="project_details_cta">';
						result = result + '<strong>Timesheet Attachment Required:</strong>';
						result = result + '<span>'+isTimesheetAttachmentRequired+'</span><div class="clearfix"></div>';
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
						
						if(json.projectCustomFieldlabelNameAndValue != null && json.projectCustomFieldlabelNameAndValue != "" && json.projectCustomFieldlabelNameAndValue != 'undefined'){
				              $.each(json.projectCustomFieldlabelNameAndValue, function(key, value) {
				            	  var labelNameAndlabelData = value.split("$");
				            	  if(labelNameAndlabelData[1] != 'null' && labelNameAndlabelData[1] != '' && labelNameAndlabelData[1] !='undefined'){
				            	  result = result + '	<div class="project_details_cta">';
				            	  result = result + '	<strong>'+labelNameAndlabelData[0]+':</strong><span class="fix">'+labelNameAndlabelData[1]+'</span><div class="clearfix"></div>';
				            	  result = result + '	</div>';
				            	  }
				            	  else{
				            		  result = result + '	<div class="project_details_cta">';
					            	  result = result + '	<strong>'+labelNameAndlabelData[0]+':</strong><span class="fix">--</span><div class="clearfix"></div>';
					            	  result = result + '	</div>'; 
				            	  }
				              });
			              }
						
						result = result + '<div class="project_details_cta project_description project_details_cta_100">';
						result = result	+ '<strong>Description:</strong>';
						result = result + '<div class="descriptionStyle">'+description+' </div>';
						result = result + '</div>';
						
						}
						
						result = result + '<div class="clearfix"></div>';
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
						$("#projectDetailsLoading").removeAttr("style"); 
						
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


<script>
function editClientProjectDetails(projectId){
	var projectClientUUID = '${projectClientUUID}'; var postedOrgLocationId = '${postedOrgLocationId}';
	var loginOrgLocationId = '${SourceLeadWeb_orgLocations.id}'
    var postedSharedProjectOrgLocationId = '${postedSharedProjectOrgLocationId}';
	$.ajax({
		type : "POST",
		url : sourceleadUrl	+ "/getClientProjectApprovers?${_csrf.parameterName}=${_csrf.token}&projectClientUUID="+ projectClientUUID,
		success : function(json) {
			
			var projectApprovalType = '';
			var expenseApprovalType = '';
			
			
			if(json.projectApprovalType != null && json.projectApprovalType != '' && json.projectApprovalType != 'undefined'){
				projectApprovalType = json.projectApprovalType
			}
			
			if(json.expenseApprovalType != null && json.expenseApprovalType != '' && json.expenseApprovalType != 'undefined'){
				expenseApprovalType = json.expenseApprovalType
			}
			
			if(json.manager2 != null && json.manager2 != '' && json.manager2 != 'undefined'){
				getApproversLevel2(json.manager1,json.manager2)
			}
			
			if(json.expenseApprover2 != null && json.expenseApprover2 != '' && json.expenseApprover2 != 'undefined'){
				getExpenseApproversLevel2(json.expenseApprover1,json.expenseApprover2)
			}
			
			var result = "";
			result = result	+ '<div id="projectEditDetailsLoading"></div>';
			result = result	+ ' <form id="editClientProject_form"  name="editClientProject_form" method="post">';
			result = result	+ '<div  id="ProjectIdDiv">';
			result = result + '<div class="user_summary_edit_cta projectdetails_edit_cta">';
			result = result + '	<label>Manager 1 <span class="text-danger">*</span></label>';
			result = result + '<div class="row">';
			 if ((json.manager2 != null && json.manager2 != "" && json.manager2 != "undefined") ||
					 (json.managerLevel1UserType != null && json.managerLevel1UserType != "" && json.managerLevel1UserType != "undefined" 
					 &&  json.managerLevel1UserType == "Admin")) {  
				result = result + '<div class="col-md-12" id="levelone-select-manager">';
			 }else{
				result = result + '<div class="col-md-8 paddingRight" id="levelone-select-manager">';	
			} 
			result = result	+ '<select  class="form-control chosen-select" onchange="getLevel2Approvers('+json.manager2+ ');" id="level1Manager" name="level1Manager" >';
			for (var j = 0; j < json.managersList.length; j++) {
				var manager = json.managersList[j];
				if(manager.lastName != null && manager.lastName != '' && manager.lastName != 'undefined'){
					var managerName = manager.firstName +'' + manager.lastName ;
				}else{
					var managerName = manager.firstName;
				}
				if (json.manager1 == manager.id) {
					selected = 'selected';
					result = result	+ "<option value="+manager.id+","+manager.userType+" "+selected+" >" + manager.name + " ("+manager.userType+")</option>";
				} else {
					result = result	+ "<option value="+manager.id+","+manager.userType+" >"+ manager.name + "  ("+manager.userType+")</option>";

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
						result = result + '<div class="col-md-4 paddingLeft" id="level2Toggle" style="display:block">';
						result = result + '<span class="btn btn-default formic1 addLevel" onclick="getLevel2Approvers(); showLevel2Div();">Add Level-II</span>';
						result = result + '</div>';
					} 
			}else{
				result = result + '<div class="col-md-4 paddingLeft" id="level2Toggle" style="display:none">';
				result = result + '<span class="btn btn-default formic1 addLevel" onclick="getLevel2Approvers(); showLevel2Div();">Add Level-II</span>';
				result = result + '</div>';
			}
			result = result	+ '<input type="hidden" id="level2Div">';
			result = result + '</div>';
			
			result = result + '</div>';
			

			if (json.manager2 != null && json.manager2 != "" && json.manager2 != "undefined") { 
				result = result+ '<div class="user_summary_edit_cta projectdetails_edit_cta" id="demo" >';
			}else{
				result = result+ '<div class="user_summary_edit_cta projectdetails_edit_cta" style="display:none;" id="demo">';
			}
				result = result + '<label> Manager 2 </label>';
				result = result	+ '<select class="form-control chosen-select" class="form-control chosen-select"  id="level2Manager" name="level2Manager">';
				result = result    +'<option value="">-Select Manager-</option>';
			/* 	if(json.managersListwithoutManager1 != null){
				for (var j = 0; j < json.managersListwithoutManager1.length; j++) {
					var manager = json.managersListwithoutManager1[j];
					var managerName2 = manager.name;
					if (json.manager2 == manager.id) {
						selected = 'selected';
						result = result	+ "<option value="+manager.id+" "+selected+" >"+ managerName2 +"  ("+manager.userType+")</option>";
					} else {

						result = result	+ "<option value="+manager.id+" >"+ managerName2 + "  ("+manager.userType+")</option>";									 
					}
				}
				} */
				result = result + '</select>';
				result = result + '</div>';
				
			  result = result + '<div class="clearfix"></div>';	
				
				
				result = result + '<div class="user_summary_edit_cta projectdetails_edit_cta">';
				result = result + '	<label>Expense Approver 1 <span class="text-danger">*</span></label>';
				result = result + '<div class="row">';
				 if ((json.expenseApprover2 != null && json.expenseApprover2 != "" && json.expenseApprover2 != "undefined") ||
						 (json.expenseApproverLevel1UserType != null && json.expenseApproverLevel1UserType != "" && json.expenseApproverLevel1UserType != "undefined" 
						 &&  json.expenseApproverLevel1UserType == "Admin")) {  
					result = result + '<div class="col-md-12" id="levelone-select-approver">';
				 }else{
					result = result + '<div class="col-md-8 paddingRight" id="levelone-select-approver">';	
				} 
				result = result	+ '<select  class="form-control chosen-select" onchange="getLevel2ExpenseApprovers('+json.expenseApprover2+ ');" id="level1ExpenseApprover" name="level1ExpenseApprover" >';
				for (var j = 0; j < json.managersList.length; j++) {
					var manager = json.managersList[j];
					if(manager.lastName != null && manager.lastName != '' && manager.lastName != 'undefined'){
						var managerName = manager.firstName +'' + manager.lastName ;
					}else{
						var managerName = manager.firstName;
					}
					if (json.expenseApprover1 == manager.id) {
						selected = 'selected';
						result = result	+ "<option value="+manager.id+","+manager.userType+" "+selected+" >" + manager.name + " ("+manager.userType+")</option>";
					} else {
						result = result	+ "<option value="+manager.id+","+manager.userType+" >"+ manager.name + "  ("+manager.userType+")</option>";

					}
				}
				result = result + '</select>';
				result = result + '</div>';
				
				
				if (json.expenseApproverLevel1UserType != null && json.expenseApproverLevel1UserType != "" && json.expenseApproverLevel1UserType != "undefined" &&  json.expenseApproverLevel1UserType != "Admin") { 
					if (json.expenseApprover2 != null && json.expenseApprover2 != "" && json.expenseApprover2 != "undefined") { 
						result = result + '<div class="col-md-2" id="level2ExpenseToggle" style="display:none">';
						result = result + '<span class="btn btn-default formic1 addExpenseApproverLevel" onclick="getLevel2ExpenseApprovers(); showExpenseLevel2Div();">Add Level-II</span>';
						result = result + '</div>';
						} else{
							result = result + '<div class="col-md-4 paddingLeft" id="level2ExpenseToggle" style="display:block">';
							result = result + '<span class="btn btn-default formic1 addExpenseApproverLevel" onclick="getLevel2ExpenseApprovers(); showExpenseLevel2Div();">Add Level-II</span>';
							result = result + '</div>';
						} 
				}else{
					result = result + '<div class="col-md-4 paddingLeft" id="level2ExpenseToggle" style="display:none">';
					result = result + '<span class="btn btn-default formic1 addExpenseApproverLevel" onclick="getLevel2ExpenseApprovers(); showExpenseLevel2Div();">Add Level-II</span>';
					result = result + '</div>';
				}
				result = result	+ '<input type="hidden" id="level2Div">';
				result = result + '</div>';
				
				result = result + '</div>';
				

				if (json.expenseApprover2 != null && json.expenseApprover2 != "" && json.expenseApprover2 != "undefined") { 
					result = result+ '<div class="user_summary_edit_cta projectdetails_edit_cta" id="demo" >';
				}else{
					result = result+ '<div class="user_summary_edit_cta projectdetails_edit_cta" style="display:none;" id="expensedemo">';
				}
					result = result + '<label> Expense Approver 2 </label>';
					result = result	+ '<select class="form-control chosen-select" class="form-control chosen-select"  id="level2ExpenseApprover" name="level2ExpenseApprover">';
					result = result    +'<option value="">-Select Manager-</option>';
			/* 		if(json.expensesApproversListwithoutApprover1 != null){
					for (var j = 0; j < json.expensesApproversListwithoutApprover1.length; j++) {
						var manager = json.expensesApproversListwithoutApprover1[j];
						var managerName2 = manager.name;
						if (json.expenseApprover2 == manager.id) {
							selected = 'selected';
							result = result	+ "<option value="+manager.id+" "+selected+" >"+ managerName2 +"  ("+manager.userType+")</option>";
						} else {

							result = result	+ "<option value="+manager.id+" >"+ managerName2 + "  ("+manager.userType+")</option>";									 
						}
					}
					} */
					result = result + '</select>';
					result = result + '</div>';
		
		 result = result + '<div class="clearfix"></div>';
		   if('${isClientOrgActive}' == "N" ){
				result = result+' <div class="col-md-4">';
				result = result + '<label>Project For</label>';
				result = result + '<div class="clearfix"></div>';
				result = result + '<label class="paddingLeft paddingRight"><input type="radio" id="timesheets" '+(json.isProjectForTimeSheets == "Y" && json.isProjectForExpenses == "N" ? "checked=checked" : '')+'  value="Timesheets" name="projectFor" onclick="projectForCheckValidation(this)"><span class="mr5"></span>Timesheets</label>';
				result = result + '<label class="paddingRight"><input type="radio" id="expenses"  value="Expenses" '+(json.isProjectForTimeSheets == "N" && json.isProjectForExpenses == "Y" ? "checked=checked" : '')+' name="projectFor" onclick="projectForCheckValidation(this)"><span class="mr5"></span>Expenses</label>';
				result = result + '<label class="paddingRight"><input type="radio" id="timesheetsandexpenses" '+(json.isProjectForTimeSheets == "Y" && json.isProjectForExpenses == "Y" ? "checked=checked" : '')+' value="TimesheetsAndExpences"  name="projectFor" onclick="projectForCheckValidation(this)"><span class="mr5"></span>Timesheets & Expenses</label>';
				result = result + '<input type="hidden"  id="projectForValue" name="projectForValue"  field-name="Project For"/>';
				result = result + '</div>';
				
				
		  }		
				
		    result = result + '<div class="clearfix"></div>';
			
			result = result + '<div class="user_summary_edit_cta projectdetails_edit_cta pull-right">';
			
			result = result	+ '	<input class="us-btn" value="Save" id="save_project" onclick = "updateClientProjectDetails('+projectId+ ');"type="button"> <input id="btnClose" class="us-btn us-btn_a" value="Cancel" onClick="editProjectDetails('+ projectId + ');" type="button">';
			result = result + '</div>		';
			result = result + '<div class="clearfix"></div>';
			result = result + ' </form>';

			$(".user_summary_edit").slideToggle(100);
			$(".project_details").toggleClass("project_detailsHei");
			$("#editProjectDetailsDiv").empty();
			$("#editProjectDetailsDiv").append(result);
			
			
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
		},
	});
}
</script>


<script type="text/javascript">
	function editProjectDetails(projectId) {
		var projectClientUUID = '${projectClientUUID}';
		var sourceleadUrl = '${sourceleadUrl}';
		 var postedOrgLocationId = '${postedOrgLocationId}';
		 var loginOrgLocationId = '${SourceLeadWeb_orgLocations.id}'
			 var postedSharedProjectOrgLocationId = '${postedSharedProjectOrgLocationId}';
		$.ajax({
					type : "POST",
					url : sourceleadUrl	+ "/getProjectById?${_csrf.parameterName}=${_csrf.token}&ProjectId="+projectId+"&projectClientUUID="+projectClientUUID,
					success : function(json) {
						
						var endDateStr = "";
						var description = "";
						var invoiceFrequency = "";
						
						var projectSequeneNumber = "";
						
                        var isTimesheetAttachmentRequired = "";
                        var allowRemote = "";
                        var allowOvertime = "";
                        var allowFreeLance = ""
                        var allowOffShore  = "";
                        var allowTimeEntryAfterDeadLine = "";
                        var allowBidding = "";
                        var reoprtingManagerChecked = "";
                        var ticketNumberRequired = "";
						var taskCommentsRequired = "";
						var projectShortCode = "";
						var projectShortCodeDisabled = "";
						var projectApprovalType = '';
						var expenseApprovalType = '';
						
						
						if(json.manager2 != null && json.manager2 != '' && json.manager2 != 'undefined'){
							getApproversLevel2(json.manager1,json.manager2)
						}
						
						if(json.expenseApprover2 != null && json.expenseApprover2 != '' && json.expenseApprover2 != 'undefined'){
							getExpenseApproversLevel2(json.expenseApprover1,json.expenseApprover2)
						}
						
						if(json.projectApprovalType != 'null' && json.projectApprovalType != '' && json.projectApprovalType != 'undefined'){
							projectApprovalType = json.projectApprovalType
						}
						
						if(json.expenseApprovalType != 'null' && json.expenseApprovalType != '' && json.expenseApprovalType != 'undefined'){
							expenseApprovalType = json.expenseApprovalType
						}
						
                        
                        <c:forEach var="section" items="${sourceleadSectionList}" varStatus="sectionOrder">
	              		  var sectionId = '${section.id}';
	              		  preapareCustomFieldDetails(projectId,sectionId);
	              		</c:forEach>
	              		
	              		if(json.isReportingManagerAsApprover != null && json.isReportingManagerAsApprover != '' && json.isReportingManagerAsApprover != 'undefined' && json.isReportingManagerAsApprover == 'Y'){
	              			reoprtingManagerChecked = "checked";
	              		}
	              		
	              		if (json.isTicketNumberRequired != null && json.isTicketNumberRequired != '' && json.isTicketNumberRequired != 'undefined'
							&& json.isTicketNumberRequired == 'Y') {
	              			ticketNumberRequired = "checked"
						}
						
						if (json.isTaskCommentsRequired != null && json.isTaskCommentsRequired != '' && json.isTaskCommentsRequired != 'undefined'
							&& json.isTaskCommentsRequired == 'Y') {
							taskCommentsRequired = "checked"
						}
                        
                        if(json.allowRemote != null && json.allowRemote != '' && json.allowRemote != 'undefined' && json.allowRemote == 'true'){
                        	allowRemote = "checked";
                        }
					
					    if (json.allowOvertime != null && json.allowOvertime != '' && json.allowOvertime != 'undefined' && json.allowOvertime == 'true'){
                        	allowOvertime = "checked";
                        }
		
						if (json.allowFreeLance != null && json.allowFreeLance != '' && json.allowFreeLance != 'undefined' && json.allowFreeLance == 'true'){
                        	allowFreeLance = "checked";
                        }
                        
						if (json.allowOffShore != null && json.allowOffShore != '' && json.allowOffShore != 'undefined' && json.allowOffShore == 'true'){
                        	allowOffShore = "checked";
                        }
						
						if (json.allowTimeEntryAfterDeadLine != null && json.allowTimeEntryAfterDeadLine != '' && json.allowTimeEntryAfterDeadLine != 'undefined' && json.allowTimeEntryAfterDeadLine == 'true'){
                        	allowTimeEntryAfterDeadLine = "checked";
                        }
                        
						if (json.allowBidding != null && json.allowBidding != '' && json.allowBidding != 'undefined' && json.allowBidding == 'true'){
                        	allowBidding = "checked";
                        }
                        
						if (json.isTimesheetAttachmentRequired != null && json.isTimesheetAttachmentRequired != '' && json.isTimesheetAttachmentRequired != 'undefined' && json.isTimesheetAttachmentRequired == '1'){
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
						if (json.description != null && json.description != '' && json.description != 'undefined') {
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
						
						if (json.projectShortCode != null && json.projectShortCode != '' && json.projectShortCode != 'undefined') {
							projectShortCode = json.projectShortCode;
							projectShortCodeDisabled = "disabled";
						}
						
						
						var result = "";
						result = result	+ '<div id="projectEditDetailsLoading"></div>';
						result = result	+ ' <form id="editProject_form"  name="editProject_form" method="post">';
						result = result	+ '<div class="user_summary_edit_cta projectdetails_edit_cta" id="ProjectIdDiv">';
						result = result + '	<label>Project Id</label>';
						result = result	+ '	<input type="text"  id="ProjectSequeneNumber" name="projectSequeneNumber" value="'+projectSequeneNumber+'" placeholder="Project Id" data-validation="validate_Space validate_length length2-50" data-validation-optional="true" onKeyUp="validateProjectSequeneNumber();" class="project_detfield">';
						result = result + '</div>';

						result = result + '<div class="user_summary_edit_cta projectdetails_edit_cta">';
						result = result + '	<label>Status<span	class="text-danger">*</span></label>';
						result = result	+ '<select id="projectStatusId" name="status" onchange="projectStatus();" field-name="Status" data-validation="required">';
						result = result	+ '<option value="">Select Status</option>';
						if (json.projectStatus == 'NOTSTARTED') {
							result = result	+ '<option selected value="Not Started">Not Started</option>';
						} else {
							result = result + '<option value="Not Started">Not Started</option>';
						}

						if (json.projectStatus == 'INPROGRESS') {
							result = result	+ '<option selected value="In-Progress">In-Progress</option>';
						} else {
							result = result + '<option value="In-Progress">In-Progress</option>';
						}

						if (json.projectStatus == 'COMPLETED') {
							result = result	+ '<option selected value="Completed">Completed</option>';
						} else {
							result = result + '<option value="Completed">Completed</option>';
						}

						if (json.projectStatus == 'ONHOLD') {
							result = result	+ '<option selected value="On-Hold">On-Hold</option>';
						} else {
							result = result + '<option value="On-Hold">On-Hold</option>';
						}
						if (json.projectStatus == 'DROPPED') {
							result = result	+ '<option selected value="Dropped">Dropped</option>';
						} else {
							result = result + '<option value="Dropped">Dropped</option>';
						}
						

						result = result + '</select>';
						result = result + '</div>';
						
						
						result = result + '<div class="user_summary_edit_cta projectdetails_edit_cta">';
						result = result + '<label>Project name<span	class="text-danger">*</span></label>';
						result = result	+ '		<span><input type="text" id="projectName" name="projectName" value="'+json.projectName+'" placeholder="Project name" class="project_detfield" onblur="validateProjectName(\''+json.projectUUID+'\');" data-validation="required validate_Space validate_length length2-250" field-name="Project Name" ></span><span id=fname_msg></span><div class="clearfix"></div>';
						result = result + '</div>';
						
						result = result + '<div class="user_summary_edit_cta projectdetails_edit_cta">';
						result = result + '<label>Project Short Code&nbsp;<i class="fa fa-info-circle" title="The internal unique code for the project"></i></label>';
						result = result	+ '	<span><input type="text" id="projectShortCode" name="projectShortCode" '+projectShortCodeDisabled+' value="'+projectShortCode+'"  class="project_detfield" onkeyUp="toUpperCase();" onblur="validateShortCode();"  pattern="[a-zA-Z]"  maxlength="3" data-validation-optional="true" data-validation="validate_length length3-3" field-name="Project Short Code" ></span><div class="clearfix"></div>';
						result = result + '</div>';
						
						if(json.isProjectShared != 'Y' || json.orgLocationId == '${SourceLeadWeb_orgLocations.id}' ){
							result = result + '<div class="user_summary_edit_cta projectdetails_edit_cta">';
							result = result + '	<label>Client</label>';
							result = result + '<div class="row">';
						    result = result + '<div class="col-md-12" id="levelone-select">';
							result = result	+ '<select class="form-control chosen-select" id="clientlocationId" name="clientlocationId" >';
							result = result	+ '<option value="">Select Client</option>';
							 for (var k = 0; k < json.clientsList.length; k++) {
								var client = json.clientsList[k];
								result = result	+ "<option value="+client.orgRelationId+","+client.orgLocationId+" >"+ client.orgName + " </option>";
							} 
							result = result + '</select>';
							result = result + '</div>';
							result = result + '</div>';
							result = result + '</div>';
						}
						
						
						
						result = result + '<div class="user_summary_edit_cta projectdetails_edit_cta">';
						result = result + '	<label>Manager 1 <span class="text-danger">*</span></label>';
						result = result + '<div class="row">';
						 if ((json.manager2 != null && json.manager2 != "" && json.manager2 != "undefined") ||
								 (json.managerLevel1UserType != null && json.managerLevel1UserType != "" && json.managerLevel1UserType != "undefined" 
								 &&  json.managerLevel1UserType == "Admin")) {  
							result = result + '<div class="col-md-12" id="levelone-select-manager">';
						 }else{
							result = result + '<div class="col-md-8 paddingRight" id="levelone-select-manager">';	
						} 
						result = result	+ '<select class="form-control chosen-select"  onchange="getLevel2Approvers('+json.manager2+ ');" id="level1Manager" name="level1Manager" >';
						for (var j = 0; j < json.managersList.length; j++) {
							var manager = json.managersList[j];
							if(manager.lastName != null && manager.lastName != '' && manager.lastName != 'undefined'){
								var managerName = manager.firstName +'' + manager.lastName ;
							}else{
								var managerName = manager.firstName;
							}
							if (json.manager1 == manager.id) {
								selected = 'selected';
								result = result	+ "<option value="+manager.id+","+manager.userType+" "+selected+" >" + manager.name + " ("+manager.userType+")</option>";
							} else {
								result = result	+ "<option value="+manager.id+","+manager.userType+" >"+ manager.name + "  ("+manager.userType+")</option>";

							}
						}
						result = result + '</select>';
						result = result + '</div>';
						
						
						if (json.managerLevel1UserType != null && json.managerLevel1UserType != "" && json.managerLevel1UserType != "undefined" &&  json.managerLevel1UserType != "Admin") { 
							if (json.manager2 != null && json.manager2 != "" && json.manager2 != "undefined") { 
								result = result + '<div class="col-md-2" id="level2Toggle" style="display:none">';
								result = result + '<span class="btn btn-default formic1 addLevel" onclick="getLevel2Approvers(); showLevel2Div();">Add Level-II</span>';
								result = result + '</div>';
								} else{
									result = result + '<div class="col-md-4 paddingLeft" id="level2Toggle" style="display:block">';
									result = result + '<span class="btn btn-default formic1 addLevel" onclick="getLevel2Approvers(); showLevel2Div();">Add Level-II</span>';
									result = result + '</div>';
								} 
						}else{
							result = result + '<div class="col-md-4 paddingLeft" id="level2Toggle" style="display:none">';
							result = result + '<span class="btn btn-default formic1 addLevel" onclick="getLevel2Approvers(); showLevel2Div();">Add Level-II</span>';
							result = result + '</div>';
						}
						result = result	+ '<input type="hidden" id="level2Div">';
						result = result + '</div>';
						
						result = result + '</div>';
						

						if (json.manager2 != null && json.manager2 != "" && json.manager2 != "undefined") { 
							result = result+ '<div class="user_summary_edit_cta projectdetails_edit_cta" id="demo" >';
						}else{
							result = result+ '<div class="user_summary_edit_cta projectdetails_edit_cta" style="display:none;" id="demo">';
						}
							result = result + '<label> Manager 2 </label>';
							result = result	+ '<select class="form-control chosen-select" id="level2Manager" name="level2Manager" onChange="manager2()">';
							result = result    +'<option value="">-Select Manager-</option>';
						/* 	if(json.managersListwithoutManager1 != null){
							for (var j = 0; j < json.managersListwithoutManager1.length; j++) {
								var manager = json.managersListwithoutManager1[j];
								var managerName2 = manager.name;
								if (json.manager2 == manager.id) {
									selected = 'selected';
									result = result	+ "<option value="+manager.id+" "+selected+" >"+ managerName2 +"  ("+manager.userType+")</option>";
								} else {

									result = result	+ "<option value="+manager.id+" >"+ managerName2 + "  ("+manager.userType+")</option>";									 
								}
							}
							} */
							result = result + '</select>';
							result = result + '</div>';
							
							result = result + '<div class="user_summary_edit_cta projectdetails_edit_cta">';
							result = result + '	<label>Expense Approver 1 </label>';
							result = result + '<div class="row">';
							 if ((json.expenseApprover2 != null && json.expenseApprover2 != "" && json.expenseApprover2 != "undefined") ||
									 (json.expenseApproverLevel1UserType != null && json.expenseApproverLevel1UserType != "" && json.expenseApproverLevel1UserType != "undefined" 
									 &&  json.expenseApproverLevel1UserType == "Admin")) {  
								result = result + '<div class="col-md-12" id="levelone-select-manager">';
							 }else{
								result = result + '<div class="col-md-8 paddingRight" id="levelone-select-approver">';	
							} 
							result = result	+ '<select class="form-control chosen-select"  onchange="getLevel2ExpenseApprovers('+json.expenseApprover2+ ');" id="level1ExpenseApprover" name="level1ExpenseApprover" >';
							for (var j = 0; j < json.managersList.length; j++) {
								var manager = json.managersList[j];
								if(manager.lastName != null && manager.lastName != '' && manager.lastName != 'undefined'){
									var managerName = manager.firstName +'' + manager.lastName ;
								}else{
									var managerName = manager.firstName;
								}
								
								if (json.expenseApprover1 == manager.id) {
									selected = 'selected';
									result = result	+ "<option value="+manager.id+","+manager.userType+" "+selected+" >" + manager.name + " ("+manager.userType+")</option>";
								} else {
									result = result	+ "<option value="+manager.id+","+manager.userType+" >"+ manager.name + "  ("+manager.userType+")</option>";

								}
							}
							result = result + '</select>';
							result = result + '</div>';
							
							
							if (json.expenseApproverLevel1UserType != null && json.expenseApproverLevel1UserType != "" && json.expenseApproverLevel1UserType != "undefined" &&  json.expenseApproverLevel1UserType != "Admin") { 
								if (json.expenseApprover2 != null && json.expenseApprover2 != "" && json.expenseApprover2 != "undefined") { 
									result = result + '<div class="col-md-2" id="level2ExpenseToggle" style="display:none">';
									result = result + '<span class="btn btn-default formic1 addExpenseApproverLevel" onclick="getLevel2ExpenseApprovers(); showExpenseLevel2Div();">Add Level-II</span>';
									result = result + '</div>';
									} else{
										result = result + '<div class="col-md-4 paddingLeft" id="level2ExpenseToggle" style="display:block">';
										result = result + '<span class="btn btn-default formic1 addExpenseApproverLevel" onclick="getLevel2ExpenseApprovers(); showExpenseLevel2Div();">Add Level-II</span>';
										result = result + '</div>';
									} 
							}else{
								result = result + '<div class="col-md-4 paddingLeft" id="level2ExpenseToggle" style="display:none">';
								result = result + '<span class="btn btn-default formic1 addExpenseApproverLevel" onclick="getLevel2ExpenseApprovers(); showExpenseLevel2Div();">Add Level-II</span>';
								result = result + '</div>';
							}
							result = result	+ '<input type="hidden" id="level2Div">';
							result = result + '</div>';
							
							result = result + '</div>';
							

							
							
							
							if (json.expenseApprover2 != null && json.expenseApprover2 != "" && json.expenseApprover2 != "undefined") { 
								result = result+ '<div class="user_summary_edit_cta projectdetails_edit_cta" id="expensedemo" >';
							}else{
								result = result+ '<div class="user_summary_edit_cta projectdetails_edit_cta" style="display:none;" id="expensedemo">';
							}
								result = result + '<label> Expense Approver 2  </label>';
								result = result	+ '<select class="form-control chosen-select" id="level2ExpenseApprover" name="level2ExpenseApprover">';
								result = result    +'<option value="">-Select Approver-</option>';
								if(json.expensesApproversListwithoutApprover1 != null){
								for (var j = 0; j < json.expensesApproversListwithoutApprover1.length; j++) {
									var manager = json.expensesApproversListwithoutApprover1[j];
									var managerName2 = manager.name;
									if (json.expenseApprover2 == manager.id) {
										selected = 'selected';
										result = result	+ "<option value="+manager.id+" "+selected+" >"+ managerName2 +"  ("+manager.userType+")</option>";
									} else {

										result = result	+ "<option value="+manager.id+" >"+ managerName2 + "  ("+manager.userType+")</option>";									 
									}
								}
								}
								result = result + '</select>';
								result = result + '</div>';
							
							
								
							
							
							result = result + '<div class="col-md-4 mt">';
							result = result + ' <label>&nbsp;</label>';
							result = result + '<div class="checkbox checkBox margin-top0">';
							result = result + '<label>';
							result = result + '<input type="checkbox" value="Y" '+reoprtingManagerChecked+' id="isReportingManagerAsApprover" name="isReportingManagerAsApprover">';
							result = result + 'Reporting Manager will be T/E approver</label>';
							result = result + '</div>';
							result = result + '</div>';
							
							

						

						result = result + '<div class="user_summary_edit_cta projectdetails_edit_cta">';
						result = result + '<label>Team members</label>';
						result = result
								+ '<input type="text" value='+json.projectMembersCount+' disabled="disabled">';
						result = result + '</div>';

						
						
						result = result + '<div class="user_summary_edit_cta projectdetails_edit_cta">';
						result = result + '	<label>Start date <span class="text-danger">*</span></label>';
						result = result
								+ '	<input class="calendar" value='+json.startDateStr+' type="text" name="startDate" readonly id="startDateStr1">';
						result = result + '</div>	';
						
						result = result + '<div class="user_summary_edit_cta projectdetails_edit_cta" id="endDateDiv">';
						result = result + '<label>End date <span class="text-danger" id="endDateSpan" style="display: none; ">*</span></label>';
						result = result +'<div class="input-group">';
						result = result+ '	<input class="calendar" type="text" placeholder="End Date" value="'+endDateStr+'" id="endDateStr1" name="endDateStr1" field-name="End Date" readonly="readonly">';
						result = result + '<input type="hidden" name="dbEndDateStr" id="dbEndDateStr" value="'+endDateStr+'" >';
					    result = result +'<span class="input-group-addon" onClick="clearEndtDate();"><i class="colorRed fa fa-times cursorPointer"></i></span>';

						result = result + '</div>';
						result = result +'</div>';
						
						result = result + '<div class="user_summary_edit_cta projectdetails_edit_cta">';
						result = result + '<label>Invoice Frequency<span class="text-danger">*</span></label>';
						result = result  +'	<input type="text"  name="invoiceFrequency" id="invoiceFrequency" value="'+json.invoiceFrequency+'" class="input-sm form-control" readonly>';
						result = result + '</div>';
						
				 
						result = result + '<div class="user_summary_edit_cta projectdetails_edit_cta projectdetails_checkbox">';
						result = result + '<label>&nbsp;</label>';
						
						
						  result = result + '<div class="checkBox">';
					    	result = result + '<label> <input type="checkbox" value="Y" '+ticketNumberRequired+'  name="isTicketNumberRequired">Ref/Ticket Number Required In Time Sheets</label>';	
						 result = result + '</div>';
						
						 result = result + '<div class="checkBox">';
					    	result = result + '<label> <input type="checkbox" value="Y" '+taskCommentsRequired+'  name="isTaskCommentsRequired">Task Comments required In Time Sheets</label>';	
						 result = result + '</div>';
						
					    result = result + '<div class="checkBox">';
					    	result = result + '<label> <input type="checkbox" value="Y" '+allowFreeLance+'  name="isAllowFreelanceDevelopers">Allow Freelance Developers</label>';	
						result = result + '</div>';

						result = result + '<div class="checkBox">';
							 result = result + '<label> <input type="checkbox" value="Y" '+allowRemote+'  name="isAllowRemoteWork">Allow Remote Work</label>';
						result = result + '</div>';
					    
						result = result + '<div class="checkBox">';
							result = result + '<label><input type="checkbox" value="Y" '+allowOvertime+' id="isAllowOverTime" name="isAllowOverTime" onchange="allowOverTime();">Allow Over Time</label>';	
						
							
						result = result + '<div class="row mt5" id="rateIdDiv" style="display:none;">';
						result = result + '<div class="col-md-5 paddingRight"  >';
						result = result + '<select class="form-control input-sm" id="flatPercentageId" name="flatPercentageId">';
						result = result  +' <option value="flat" '+(json.overtimeRateType == "flat" ? "selected" : '') +' >FLAT</option>';
						result = result  +' <option value="percentage"  '+(json.overtimeRateType == "percentage" ? "selected" : '') +' >PERCENTAGE</option>';
						
						result = result + '</select>';
						result = result + '</div>';
						result = result + '<div class="col-md-7 paddingLeft">';
						if(json.flatPercent == null){
							result = result + '<input type="text" id="flatPercentage" name="flatPercentage" class="form-control input-sm" data-validation-optional="true">';
						}else{
							result = result + '<input type="text" id="flatPercentage" name="flatPercentage" class="form-control input-sm" data-validation-optional="true" value='+json.flatPercent+' >';
						}
						result = result + '</div>';
						
						result = result + '</div>';
						
						result = result + '</div>';
						
						
						result = result + '</div>';
						
						result = result + '</div>';
						
						
					   if('${isClientOrgActive}' == "N" || postedOrgLocationId == loginOrgLocationId || postedSharedProjectOrgLocationId == loginOrgLocationId){
								result = result+' <div class="col-md-4">';
								result = result + '<label>Project For</label>';
								result = result + '<div class="clearfix"></div>';
								result = result + '<label class="paddingLeft paddingRight"><input type="radio" id="timesheets" '+(json.isProjectForTimeSheets == "Y" && json.isProjectForExpenses == "N" ? "checked=checked" : '')+'  value="Timesheets" name="projectFor" onclick="projectForCheckValidation(this)"><span class="mr5"></span>Timesheets</label>';
								result = result + '<label class="paddingRight"><input type="radio" id="expenses"  value="Expenses" '+(json.isProjectForTimeSheets == "N" && json.isProjectForExpenses == "Y" ? "checked=checked" : '')+' name="projectFor" onclick="projectForCheckValidation(this)"><span class="mr5"></span>Expenses</label>';
								result = result + '<label class="paddingRight"><input type="radio" id="timesheetsandexpenses" '+(json.isProjectForTimeSheets == "Y" && json.isProjectForExpenses == "Y" ? "checked=checked" : '')+' value="TimesheetsAndExpences"  name="projectFor" onclick="projectForCheckValidation(this)"><span class="mr5"></span>Timesheets & Expenses</label>';
								result = result + '<input type="hidden"  id="projectForValue" name="projectForValue"  field-name="Project For"/>';
								result = result + '</div>';
						  }
					   
					   
						result = result + '<div class="user_summary_edit_cta projectdetails_edit_cta projectdetails_checkbox">';
						result = result + '<label>&nbsp;</label>';
							
					    result = result + '<div class="checkBox">';
					    result = result + '<label> <input type="checkbox" value="Y" '+(isTimesheetAttachmentRequired == "Y" ? "checked" : '')+'  name="isTimesheetAttachmentRequired" id="isTimesheetAttachmentRequired"> Timesheet Attachment Required</label>';
						result = result + '</div>';

						result = result + '<div class="checkBox">';
						result = result + '<label> <input type="checkbox" value="Y" '+allowOffShore+'   name="isAllowOffshoreResource">Allow Offshore Resource</label>';
						result = result + '</div>';
					    
						result = result + '<div class="checkBox">';
						result = result + '<label> <input type="checkbox" value="Y" '+allowTimeEntryAfterDeadLine+' name="isTimeEntryAfterDeadline">Time Entry after Deadline</label>';
						result = result + '</div>';

						result = result + '<div class="checkBox">';
						result = result + '<label> <input type="checkbox" value="Y" '+allowBidding+'  name="isAllowBidding">Allow Bidding</label>';
						result = result + '</div>';
						
						result = result + '</div>';
						result = result + '<div class="clearfix"></div>';
						result = result + '<div id="customFieldDataId" class="customFieldDataId-3column"></div>';
						
						
					     result = result+' <div class="col-md-4">';
						result = result + '<label>Timesheet Approval Mechanism</label>';
						result = result + '<div class="clearfix"></div>';
						result = result + '<label class="paddingLeft paddingRight"><input type="radio" id="parallelTimesheetsApproval" value="Parallel" '+(json.projectApprovalType == "Parallel"? "checked=checked" : '')+' name="projectApprovalType" onclick="projectApprovalForCheckValidation(this)"><span class="mr5"></span>Parallel</label>';
						result = result + '<label class="paddingRight"><input type="radio" id="sequentialTimesheetsApproval"  value="Sequential" '+(json.projectApprovalType == "Sequential" ? "checked=checked" : '')+' name="projectApprovalType" onclick="projectApprovalForCheckValidation(this)"><span class="mr5"></span>Sequential</label>';
						result = result + '<input type="hidden"  id="projectApprovalTypeValue" name="projectApprovalTypeValue" value="'+projectApprovalType+'" data-validation="required" field-name="Timesheet Approval"/>';
						result = result + '</div>';
						
						
						result = result+' <div class="col-md-4">';
						result = result + '<label>Expense Approval Mechanism</label>';
						result = result + '<div class="clearfix"></div>';
						result = result + '<label class="paddingLeft paddingRight"><input type="radio" id="parallerExpenseApproval" value="Parallel" '+(json.expenseApprovalType == "Parallel"? "checked=checked" : '')+' name="expenseApprovalType" onclick="expenseApprovalForCheckValidation(this)"><span class="mr5"></span>Parallel</label>';
						result = result + '<label class="paddingRight"><input type="radio" id="sequentialExpenseApproval"  value="Sequential" '+(json.expenseApprovalType == "Sequential" ? "checked=checked" : '')+' name="expenseApprovalType" onclick="expenseApprovalForCheckValidation(this)"><span class="mr5"></span>Sequential</label>';
						result = result + '<input type="hidden"  id="expenseApprovalTypeValue" name="expenseApprovalTypeValue" value="'+expenseApprovalType+'" data-validation="required"  field-name="Expense Approval"/>';
						result = result + '</div>';  
						
						result = result + '<div class="clearfix"></div>';
						result = result + '<div class="clearfix"></div>';
						result = result + '<div class="width100percnt col-md-12 mb txtEditor_p">';
						result = result + '<label>Description</label>';
						result = result + '<textarea id="txtEditor1" name="description" data-validation-optional="true"  value="'+ description +'" class="form-control newContrtextarea">' + description + '</textarea>';
						result = result + '</div>       ';
						
					    result = result + '<div class="clearfix"></div>';
						
						result = result + '<div class="user_summary_edit_cta projectdetails_edit_cta pull-right">';
						
						result = result	+ '	<input class="us-btn" value="Save" id="save_project" onclick = "updateProjectDetails('+projectId+ ');"type="button"> <input id="btnClose" class="us-btn us-btn_a" value="Cancel" onClick="editProjectDetails('+ projectId + ');" type="button">';
						result = result + '</div>		';
						result = result + '<div class="clearfix"></div>';
						result = result + ' </form>';

						$(".user_summary_edit").slideToggle(100);
						$(".project_details").toggleClass("project_detailsHei");
						$("#editProjectDetailsDiv").empty();
						$("#editProjectDetailsDiv").append(result);
						getEditProjectDesc(projectId);
						allowOverTime();
						$("#txtEditor1").Editor();
						//Assign date of project member
						var selected = json.startDateStr;
						var dt = toDate("#startDateStr1");
						dt.setDate(dt.getDate());

						//Main project end date
						var projectEndDate = json.endDateStr;
						 var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
						  var finalDateFormatEduSave = '';
						  if(locationDate.match("yyyy-MM-dd")){
							  finalDateFormatEduSave = 'yy-mm-dd';
						  }if(locationDate.match("dd-MM-yyyy")){
							  finalDateFormatEduSave = 'dd-mm-yy'; 
						  }if(locationDate.match("MM-dd-yyyy")){
							  finalDateFormatEduSave = 'mm-dd-yy'; 
						  }if(locationDate.match("yyyy-dd-MM")){
							  finalDateFormatEduSave = 'yy-dd-mm'; 
						  }
						
						  $('#endDateStr1').datepicker({
								dateFormat : finalDateFormatEduSave,
								changeMonth: true,
								changeYear: true, 
								minDate : json.startDateStr,
								onSelect : function(selected) {
									var dt = $(this).datepicker('getDate', '-1d');
									dt.setDate(dt.getDate() - 1);
									$("#startDateStr1").datepicker("option", "maxDate", dt);
									addValidClass();
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
								}
							});
						
						
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

					},
					
				});

	}
	
	function manager2(){
		var manager2 = $("#level2Manager").val();
		if(manager2 == ""){
			$('#level2ExpenseApprover').val('').trigger("chosen:updated");
		} 
		
	}
</script>


<script type="text/javascript">
	function projectForCheckValidation(id){
		document.getElementById("projectForValue").value = id.value;
	} 
	
	function projectApprovalForCheckValidation(id){
		document.getElementById("projectApprovalTypeValue").value = id.value;
	} 
	
	function expenseApprovalForCheckValidation(id){
		document.getElementById("expenseApprovalTypeValue").value = id.value;
	} 
</script>

<script>
function preapareCustomFieldDetails(projectId,sectionId){
	$.ajax({
        type : "POST",
         url: sourceleadUrl+"/getAllASectionCustomFields?&projectId="+projectId+"&requestFrom=project"+"&sectionId="+sectionId,        
         success :function(data) {
        	 $('#customFieldDataId').empty();
        	 $('#customFieldDataId').append(data);
           },
     }); 
}
</script>

<script>
function validateProjectName(projectUUID){
	var projectName = document.getElementById("projectName").value;
	
	 $.ajax({
		type : "GET",
		url : sourceleadUrl+ "/validateOrgLocationProjectName?${_csrf.parameterName}=${_csrf.token}&projectUUID="+projectUUID+"&projectName="+projectName,
		success : function(data) {
			if (data == true) {
				$("input#projectName").after("<p class='jquery_form_error_message'>"+ projectName+ " is Already Existed</span>");
				document.getElementById("projectName").setAttribute('record-exist', 'yes');
				document.getElementById("projectName").setAttribute('record-exist-errorMsg',projectName+ ' is Already Existed');
				$('input#projectName').removeAttr( "record-exist record-exist-errorMsg");
				$("#save_project").attr("disabled", "disabled");
			} else {
				$('input#projectName').removeAttr("record-exist record-exist-errormsg");
				$("#save_project").removeAttr("disabled");
			}
		},
	}); 

	
}
</script>


<script>
function getEditProjectDesc(projectId){
	var sourceleadUrl = '${sourceleadUrl}';
		$.ajax({
			type : "GET",
			url : sourceleadUrl + "/getProjectDescriptionById?${_csrf.parameterName}=${_csrf.token}&projectId="+projectId,
			success : function(data) {
				var json = JSON.parse(data);
				$(".Editor-container").find('.Editor-editor').html(json['description']);
			},
		}); 
	
}
function allowOverTime(){
	if ($('#isAllowOverTime').is(":checked")){
		document.getElementById("rateIdDiv").style.display="block";
	}else{
		document.getElementById("rateIdDiv").style.display="none";
		$("#flatPercentage").val('');
	}
	
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
		var ee = $("#txtEditor1").val($('.Editor-editor').html()); 
		
		 if ($('#editProject_form').validate(false, validationSettings)) {
			var formData = getFormData("editProject_form");
			$("#projectEditDetailsLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			$.ajax({
						type : "POST",
						data: formData,
						url : sourceleadUrl + "/updateProjectByProjectId?${_csrf.parameterName}=${_csrf.token}&ProjectId=" + projectId,
						success : function(result) {
							if (result && result.response === "INVALID_DATA") {
  								$("#projectEditDetailsLoading").removeAttr("style");
  			                    showError(result.responseMessage);
  			                } else if (result.response === "AWKWARD") {
  			                    location.href = "/errorPage";
  			                } else if (result.response === "SUCCESS") {
  			                	$("#selector2").removeAttr("style");
  		            			$("#selector2").css({ display: "block" });
  		            			$("#docMsgDiv2").html("Success!! Project details updated");
  								projectDetails(projectId);
  								docMsgFadein();
  								
  								var json = data.object;
  								
  								$("#empTab").removeAttr("onClick");
  				   		    	$("#empTab").attr("onclick","projectEmployee('"+projectId+"', '"+json.startDateStr+"', '"+json.endDateStr+"', '"+json.projectStatus+"', '"+json.projectName+"')");
  				   		    	
  				   		    	$("#teamTab").removeAttr("onClick");
  				   		    	$("#teamTab").attr("onclick","projectTeam('"+projectId+"', '"+json.startDateStr+"', '"+json.endDateStr+"', '"+json.projectStatus+"', '"+json.projectName+"')");
  				   		    	
  				   		    	$("#taskTab").removeAttr("onClick");
  				   		    	$("#taskTab").attr("onclick","projectTask('"+projectId+"', '"+json.projectStatus+"')");
  			                }
							
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

<script>
function toUpperCase(){
	var toupper = $('#projectShortCode').val().toUpperCase().trim();
	$('#projectShortCode').val(toupper);
}
function validateShortCode(){
	var projectShortCode = $('#projectShortCode').val();
	var orgLocId = '${SourceLeadWeb_orgLocations.id}';
	$.ajax({
		type : "POST",
		url : sourceleadUrl+ "/validateProjectShortCodeByOrgLocationId?${_csrf.parameterName}=${_csrf.token}&projectShortCode="+projectShortCode+"&orgLocId="+orgLocId,
		success : function(data) {
			if (data == true) {
				$("input#projectShortCode").after("<p class='jquery_form_error_message'>"+ projectShortCode+ " is Already Existed</span>");
				document.getElementById("projectShortCode").setAttribute('record-exist', 'yes');
				document.getElementById("projectShortCode").setAttribute('record-exist-errorMsg',projectShortCode+ ' is Already Existed');
				$('input#projectShortCode').removeAttr( "record-exist record-exist-errorMsg");
				$("#save_project").attr("disabled", "disabled");
			} else {
				$('input#projectShortCode').removeAttr("record-exist record-exist-errormsg");
				$("#save_project").removeAttr("disabled");
			}
		},
	});
}
</script>


<script type="text/javascript">
	function updateClientProjectDetails(projectId) {
		var projectClientUUID = '${projectClientUUID}';
		 if ($('#editClientProject_form').validate(false, validationSettings)) {
			 $("#projectEditDetailsLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			var formData = getFormData("editClientProject_form");
			$.ajax({
						type : "POST",
						data: formData,
						url : sourceleadUrl + "/updateClientProjectApprovers?${_csrf.parameterName}=${_csrf.token}&projectClientUUID=" + projectClientUUID,
						success : function(data) {
							$("#selector2").removeAttr("style");
	            			$("#selector2").css({ display: "block" });
	            			$("#docMsgDiv2").html("Success!! Project details updated");
							projectDetails(projectId);
							docMsgFadein();
							
							var json = JSON.parse(data);
							$("#empTab").removeAttr("onClick");
			   		    	$("#empTab").attr("onclick","projectEmployee('"+projectId+"', '"+json.startDateStr+"', '"+json.endDateStr+"', '"+json.projectStatus+"', '"+json.projectName+"')");
			   		    	
			   		    	$("#teamTab").removeAttr("onClick");
			   		    	$("#teamTab").attr("onclick","projectTeam('"+projectId+"', '"+json.startDateStr+"', '"+json.endDateStr+"', '"+json.projectStatus+"', '"+json.projectName+"')");
			   		    	
			   		    	$("#taskTab").removeAttr("onClick");
			   		    	$("#taskTab").attr("onclick","projectTask('"+projectId+"', '"+json.projectStatus+"')");
						}
					});
			return true;
		} else {
			return false;
		} 

	}
	$('body').on('blur', '#editClientProject_form', function() {
		$("#editClientProject_form").showHelpOnFocus();
		;
		$("#editClientProject_form").validateOnBlur(false, validationSettings);
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
		$ .ajax({
					type : "POST",
					url : sourceleadUrl + "/getApproversByUserId?${_csrf.parameterName}=${_csrf.token}&currentApproverId="+ currentApproverId,
					success : function(json) {
						var output = "";
						output = output + "<option value=''>-Select Manager-</option>";
						for (var i = 0; i < json.length; i++) {
							var opt = json[i];
							 if (manager2 == opt.id) {
								selected = 'selected';
								output = output + "<option "+selected+" value='" +opt.id+ "'>" + opt.firstName + " " + opt.lastName + "  ("+opt.userType+")</option>";
							} else { 
								output = output + "<option value='" +opt.id+ "'>" + opt.firstName + " " + opt.lastName + "  ("+opt.userType+")</option>";
						}

						}
						$("#level2Manager").empty();
						$("#level2Manager").append(output);
						$('#level2Manager').trigger("chosen:updated");
					},
				});

	}
</script>



<script type="text/javascript">
	function getExpenseApproversLevel2(currentApproverId, manager2) {
		var sourceleadUrl = '${sourceleadUrl}';
		var currentApproverId = currentApproverId;
		var manager2 = manager2;
		$ .ajax({
					type : "POST",
					url : sourceleadUrl + "/getApproversByUserId?${_csrf.parameterName}=${_csrf.token}&currentApproverId="+ currentApproverId,
					success : function(json) {
						var output = "";
						output = output + "<option value=''>-Select Manager-</option>";
						for (var i = 0; i < json.length; i++) {
							var opt = json[i];
							output = output + "<option value='" +opt.id+ "'>" + opt.firstName + " " + opt.lastName + "  ("+opt.userType+")</option>";

						}
						$("#level2ExpenseApprover").empty();
						$("#level2ExpenseApprover").append(output);
						$('#level2ExpenseApprover').trigger("chosen:updated");
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
function showExpenseLevel2Div(){
	var currentApproverId = document.getElementById("level1ExpenseApprover").value;
	var currentUser = currentApproverId.split(',');
	var currentUserType = currentUser[1];
	if(currentUserType != null && currentUserType != '' && currentUserType !='undefined' && currentUserType != 'Admin'){
	    $("#expensedemo").show();  
	}else if(currentUserType != null && currentUserType != '' && currentUserType !='undefined' && currentUserType == 'Admin'){
	    $("#expensedemo").hide();  
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
		    	$("#levelone-select-manager").removeClass('col-md-12');
			    $("#levelone-select-manager").addClass('col-md-8');  
		    }else{
		    	$("#demo").show();
		    }
		    getChildLevel1Approvers(currentUserId, manager2);
		}else if(currentUserType != null && currentUserType != '' && currentUserType !='undefined' && currentUserType == 'Admin'){
		    $("#level2Toggle").hide();   
		    $("#levelone-select-manager").removeClass('col-md-8');
		    $("#levelone-select-manager").addClass('col-md-12');  
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
				$.ajax({ 
					type : "POST",
					url : sourceleadUrl + "/getApproversByUserId?${_csrf.parameterName}=${_csrf.token}&currentApproverId=" + currentApproverId,
					success : function(json) {
						var output = "";
						output = output + "<option value=''>-Select Manager-</option>";
						for (var i = 0; i < json.length; i++) {
							var opt = json[i];
							output = output + "<option value='" +opt.id+ "'>" + opt.firstName + " " + opt.lastName + " ("+opt.userType+")</option>";
						}
						$("#level2Manager").empty();
						$("#level2Manager").append(output);
						$('#level2Manager').trigger("chosen:updated");
					},
				});
			}
		} else {
			getApproversLevel2(currentApproverId, manager2);
		}
	}
</script>






<script type="text/javascript">
	function getLevel2ExpenseApprovers(manager2) {
		var currentApproverId = document.getElementById("level1ExpenseApprover").value;
		var currentUser = currentApproverId.split(',');
		var currentUserId = currentUser[0];
		var currentUserType = currentUser[1];
		$("#level2ExpenseApprover").empty();    
		$('#level2ExpenseApprover').val('');
		if(currentUserType != null && currentUserType != '' && currentUserType !='undefined' && currentUserType != 'Admin'){
		    if(manager2 == null || manager2 == "" || manager2 == "undefined"){
		    	$("#level2ExpenseToggle").show();
		    	$("#levelone-select-approver").removeClass('col-md-12');
			    $("#levelone-select-approver").addClass('col-md-8');  
		    }else{
		    	$("#expensedemo").show();
		    }
		    getChildLevel1ExpenseApprovers(currentUserId, manager2);
		}else if(currentUserType != null && currentUserType != '' && currentUserType !='undefined' && currentUserType == 'Admin'){
		    $("#level2ExpenseToggle").hide();   
		    $("#levelone-select-approver").removeClass('col-md-8');
		    $("#levelone-select-approver").addClass('col-md-12');  
		    $("#expensedemo").hide();  
		}
	}
</script>
<script type="text/javascript">
	function getChildLevel1ExpenseApprovers(currentApproverId, manager2) {
		var sourceleadUrl = '${sourceleadUrl}';
		if (currentApproverId == manager2) {
			if (currentApproverId != null && currentApproverId != ''
					&& currentApproverId != 'undefined') {
				$.ajax({ 
					type : "POST",
					url : sourceleadUrl + "/getApproversByUserId?${_csrf.parameterName}=${_csrf.token}&currentApproverId=" + currentApproverId,
					success : function(json) {
						var output = "";
						output = output + "<option value=''>-Select Manager-</option>";
						for (var i = 0; i < json.length; i++) {
							var opt = json[i];
							output = output + "<option value='" +opt.id+ "'>" + opt.firstName + " " + opt.lastName + " ("+opt.userType+")</option>";
						}
						$("#level2ExpenseApprover").empty();
						$("#level2ExpenseApprover").append(output);
						$('#level2ExpenseApprover').trigger("chosen:updated");
					},
				});
			}
		} else {
			getExpenseApproversLevel2(currentApproverId, manager2);
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

<script>
function clearEndtDate(){
	$("#endDateStr1").val("");
	};
	
	
</script>
