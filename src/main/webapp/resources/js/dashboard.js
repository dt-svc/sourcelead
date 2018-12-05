// below details need to be dynamic as of now used the user details of 
//narasimha.seeral@businessintelli.com for india location 
var sourceleadUrl ="http://localhost:8080/app";//'${sourceleadUrl}'; // setting common user settings 
var locationId = "5";//'${SourceLeadWeb_orgLocationId}';
var locationList ="dd-MM-yyyy";//'${SourceLeadWeb_orgLocations.dateFormat}';	
var userId ="31";// '${SourceLeadWeb_user.id}';
var orgLocationCode = "txhlIN"; //'${SourceLeadWeb_locationCode}';
var candiadteId = "9105";//'${SourceLeadWeb_candidateId}';
var isGlobalAccess = "N";//'${SourceLeadWeb_isGlobalAccess}';
var loginUserRole ="ROLE_ADMIN";// '${SourceLeadWeb_loginUserRole}';
var orgATS = "Y";//'${SourceLeadWeb_ats}';
var orgVMS ="Y";// '${SourceLeadWeb_vms}';
var approverUserId ="31";// '${SourceLeadWeb_user.id}';
var loginUser = "Narasimha Seerla";//'${SourceLeadWeb_userName}';
var date = $("#eventCalendar").val();
// its an entity commented now. var timezone ='${SourceLeadWeb_orgLocations.timezone}'; 
var requirementId = "";//'${requirementId}';
var timezoneId = "246" 
/* if(timezone != null && timezone != '' && timezone != 'undefined'){
	timezoneId ="246" ;//'${SourceLeadWeb_orgLocations.timezone.id}';
} */
var country = "India";//'${SourceLeadWeb_orgLocations.country}';
var locationDate = locationList; // locationList is unique variable using for date format.
var finalDateFormatEDashboardeventCalendar = '';
//$(document).ready(function() {
	if (locationDate.match("yyyy-MM-dd")) {
		finalDateFormatEDashboardeventCalendar = 'yy-mm-dd';
	}
	if (locationDate.match("dd-MM-yyyy")) {
		finalDateFormatEDashboardeventCalendar = 'dd-mm-yy';
	}
	if (locationDate.match("MM-dd-yyyy")) {
		finalDateFormatEDashboardeventCalendar = 'mm-dd-yy';
	}
	if (locationDate.match("yyyy-dd-MM")) {
		finalDateFormatEDashboardeventCalendar = 'yy-dd-mm';
	}

//1. below code is for dashboard timesheet counts  
 var wsocket;  
//var userId = '${SourceLeadWeb_user.id}';
//var orgLocationCode = '${SourceLeadWeb_locationCode}';
//var candiadteId = '${SourceLeadWeb_candidateId}';
var timesheetType;
//var sourceleadUrl ='${sourceleadUrl}';
var sourceleadWebSocketUrl = sourceleadUrl.replace("http","ws");
//var isGlobalAccess = '${SourceLeadWeb_isGlobalAccess}';

function timeSheetSubmissionStatusCount(type) { 
  $("#rejectLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:5; background-image:url("/resources/images/Spinner.gif"); background-position:center; background-repeat:no-repeat; background-size:60px;');
  $("#waitingLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:5; background-image:url("/resources/images/Spinner.gif"); background-position:center; background-repeat:no-repeat; background-size:60px;');
  $("#draftLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:5; background-image:url("/resources/images/Spinner.gif"); background-position:center; background-repeat:no-repeat; background-size:60px;');
  $("#approvedloading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:5; background-image:url("/resources/images/Spinner.gif"); background-position:center; background-repeat:no-repeat; background-size:60px;');
  
  timesheetType = type;
  wsocket = new WebSocket(sourceleadWebSocketUrl.trim()+"/ws/dashBoardDynamicCount?userId="+userId+"&orgLocationCode="+orgLocationCode+"&candiadteId="+candiadteId+"&timesheetType="+timesheetType+"&isGlobalAccess="+isGlobalAccess+"&countFor=timesheet");       
  wsocket.onmessage = onMessage; 
}

window.onbeforeunload = function() {
	 var myWebSocket = new WebSocket(sourceleadWebSocketUrl.trim()+"/ws/dashBoardDynamicCount?userId="+userId+"&orgLocationCode="+orgLocationCode+"&candiadteId="+candiadteId+"&timesheetType="+timesheetType+"&isGlobalAccess="+isGlobalAccess+"&countFor=timesheet"); 
	 myWebSocket.close();
};

function onMessage(evt) {

  var json = JSON.parse(evt.data);
  var  approvalTimesheetsSubmissionsCount =json.approvedTimesheetSubmissionCount;
  var  savedTimesheetsSubmissionsCount = json.savedTimesheetSubmissionCount;
  var  rejectedTimesheetsSubmissionsCount = json.rejectedTimesheetSubmissionCount;
  var  waitingTimesheetsSubmissionsCount = json.waitingForApprovalTimesheetSubmissionCount;
  $("#draftLoading").removeAttr("style");
  $("#savedTimesheetsSubmissionsCount").empty();
  $("#savedCountAnchor").empty();
  $("#approvedloading").removeAttr("style");
  $("#approvalTimesheetsSubmissionsCount").empty();
  $("#approvedCountAnchor").empty();
 
  $("#rejectLoading").removeAttr("style");
  $("#rejectTimesheetsSubmissionsCount").empty();
  $("#rejectedCountAnchor").empty();
  $("#waitingLoading").removeAttr("style");
  $("#waitingTimesheetsSubmissionsCount").empty();
  $("#waitingCountAnchor").empty();
 
  if(rejectedTimesheetsSubmissionsCount<1 || timesheetType == "myTimesheets"){
	  $("#rejectTimesheetsSubmissionsCount").html(rejectedTimesheetsSubmissionsCount);
     $("#rejectedCountAnchor").html("Rejected");
}else{
	    $("#rejectTimesheetsSubmissionsCount").html(rejectedTimesheetsSubmissionsCount);
      $("#rejectedCountAnchor").html('<a  href="/app/totalTimesheetList/REJECTED">Rejected</a>'); 
}
  if(savedTimesheetsSubmissionsCount<1 || timesheetType == "myTimesheets"){
	  $("#savedTimesheetsSubmissionsCount").html(savedTimesheetsSubmissionsCount);
     $("#savedCountAnchor").html("Draft");
 }else{
	  $("#savedTimesheetsSubmissionsCount").html(savedTimesheetsSubmissionsCount);
     $("#savedCountAnchor").html("<a href='/app/totalTimesheetList/DRAFT'>Draft</a>"); 
    
 }
 
 if(waitingTimesheetsSubmissionsCount<1 || timesheetType == "myTimesheets"){
	  $("#waitingTimesheetsSubmissionsCount").html(waitingTimesheetsSubmissionsCount);
    $("#waitingCountAnchor").html("Submitted");
}else{
	  $("#waitingTimesheetsSubmissionsCount").html(waitingTimesheetsSubmissionsCount);
     $("#waitingCountAnchor").html('<a  href="/app/totalTimesheetList/SUBMITTED">Waiting</a>'); 
}
 
  if(approvalTimesheetsSubmissionsCount<1 || timesheetType == "myTimesheets"){
	  $("#approvalTimesheetsSubmissionsCount").html(approvalTimesheetsSubmissionsCount);
     $("#approvedCountAnchor").html("Approved");
}else{
	  $("#approvalTimesheetsSubmissionsCount").html(approvalTimesheetsSubmissionsCount);
     $("#approvedCountAnchor").html('<a  href="/app/totalTimesheetList/APPROVED">Approved</a>'); 
 }
 if(timesheetType == "myTimesheets"){
	 $("#timeSheetLink").attr("href", "/app/timesheet");
 }else{
	 $("#timeSheetLink").attr("href", "/app/timesheetApproval");
 }
} 

//2.Jobs section counts start here 
function loadRequirementApplicants(){
	var tab = $("#dateActiveTab").val();
	//var sourceleadUrl = '${sourceleadUrl}';
	var orgLocId =locationId;// '${SourceLeadWeb_orgLocationId}';
	window.location.href = sourceleadUrl + "/requirementApplicantsList?&tab="+tab+"&orgLocationId="+orgLocId;
}
function loadRequirementSubmissions(){
	var tab = $("#dateActiveTab").val();
	//var sourceleadUrl = '${sourceleadUrl}';
	var orgLocId = locationId;//'${SourceLeadWeb_orgLocationId}';
	window.location.href = sourceleadUrl + "/requirementSubmissionsList?&tab="+tab+"&orgLocationId="+orgLocId;;
}
function loadRequirementInterviews(){
	var tab = $("#dateActiveTab").val();
	//var sourceleadUrl = '${sourceleadUrl}';
	var orgLocId = locationId;//'${SourceLeadWeb_orgLocationId}';
	window.location.href = sourceleadUrl + "/requirementInterviewsList?&tab="+tab+"&orgLocationId="+orgLocId;;
}
function loadRequirementOffers(){
	var tab = $("#dateActiveTab").val();
	//var sourceleadUrl = '${sourceleadUrl}';
	var orgLocId = locationId;//'${SourceLeadWeb_orgLocationId}';
	window.location.href = sourceleadUrl + "/requirementOffersList?&tab="+tab+"&orgLocationId="+orgLocId;;
}

function requirementSubmissionsCount(daterange) {
	$("#dateActiveTab").val(daterange);
	$(
			"#applicantsCountLoading, #submissionsCountLoading, #interviewsCountLoading, #offersCountLoading")
			.attr(
					'style',
					'position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:5; background-image:url("/resources/images/Spinner.gif"); background-position:center; background-repeat:no-repeat; background-size:60px;');
	  // var sourceleadUrl ='${sourceleadUrl}';
	  // var userId = '${SourceLeadWeb_user.id}';
	   var orgLocationId = locationId;//''${SourceLeadWeb_orgLocationId}';
	$.ajax({
				type : "GET",
				url : sourceleadUrl
						+ "/getRequirementSubmissionsCount?daterange="	+ daterange + "&userId=" + userId + "&orgLocationId="+ orgLocationId,
				success : function(data) {
					if (data.response === "FAILED") {
						showError(data.responseMessage);
					} else if (data.response === "AWKWARD") {
						location.href = "/errorPage";
					} else if (data.response === "SUCCESS") {
						var json = data.object;
						$(
								"#applicantsCountLoading, #submissionsCountLoading, #interviewsCountLoading, #offersCountLoading")
								.removeAttr("style");
						var submissionsCount = json.submissionCount;
						var applicantsCount = json.applicantsCount;
						var interviewsCount = json.interviewsCount;
						var offersCount = json.offersCount;
						if (submissionsCount > 0) {
							$("#submissionsCount").text(submissionsCount);
							$('#submissionsCount')
									.attr("onClick",
											"loadRequirementSubmissions(); return false;");
							$('#subDiv').attr("style", "cursor:pointer");
						} else {
							$("#submissionsCount").html("0");
							$("#submissionsCount").removeAttr("onclick");
							$("#subDiv").removeAttr("style");
						}
						if (applicantsCount > 0) {
							$("#applicantsCount").text(applicantsCount);
							$('#applicantsCount')
									.attr("onClick",
											"loadRequirementApplicants(); return false;");
							$('#appDiv').attr("style", "cursor:pointer");
						} else {
							$("#applicantsCount").html("0");
							$("#applicantsCount").removeAttr("onclick");
							$("#appDiv").removeAttr("style");
						}
						if (interviewsCount > 0) {
							$("#interviewsCount").text(interviewsCount);
							$('#interviewsCount')
									.attr("onClick",
											"loadRequirementInterviews(); return false;");
							$('#intDiv').attr("style", "cursor:pointer");
						} else {
							$("#interviewsCount").html("0");
							$("#interviewsCount").removeAttr("onclick");
							$("#intDiv").removeAttr("style");
						}
						if (offersCount > 0) {
							$("#offersCount").text(offersCount);
							$('#offersCount').attr("onClick",
									"loadRequirementOffers(); return false;");
							$('#offDiv').attr("style", "cursor:pointer");
						} else {
							$("#offersCount").html("0");
							$("#offersCount").removeAttr("onclick");
							$("#offDiv").removeAttr("style");
						}
					} else if (data.indexOf('Sign In') !== -1) {
						location.replace(sourceleadUrl);
					} else {
						showError("Operation failed, please try again");
					}
				}
			});

	if (daterange == "today") {
		$("#jobsLink").attr("href", "/app/jobs");
	} else {
		$("#jobsLink").attr("href", "/app/jobs");
	}

}// Jobs End
//Expensive count start.
function expensesCount(expenseType) {
	// var sourceleadUrl ='${sourceleadUrl}';
	$.ajax({
				type : "GET",
				url : sourceleadUrl + "/getExpensesCount?expenseType="
						+ expenseType,
				success : function(json) {
					// var json = JSON.parse(data);

					$("#expensesDraftCount").empty();
					$("#expensesSubmittedForApprovalCount").empty();
					$("#expensesApprovedCount").empty();
					$("#expensesRejectCount").empty();

					$("#dial1txt").empty();
					$("#dial2txt").empty();
					$("#dial3txt").empty();
					$("#dial4txt").empty();

					var expensesDraftCount = 0;
					if (json.expensesDraftCount != null
							&& json.expensesDraftCount != ""
							&& json.expensesDraftCount != 'undefined') {
						expensesDraftCount = json.expensesDraftCount;
					}

					var expensesSubmittedForApprovalCount = 0;
					if (json.expensesSubmittedForApprovalCount != null
							&& json.expensesSubmittedForApprovalCount != ""
							&& json.expensesSubmittedForApprovalCount != 'undefined') {
						expensesSubmittedForApprovalCount = json.expensesSubmittedForApprovalCount;
					}

					var expensesApprovedCount = 0;
					if (json.expensesApprovedCount != null
							&& json.expensesApprovedCount != ""
							&& json.expensesApprovedCount != 'undefined') {
						expensesApprovedCount = json.expensesApprovedCount;
					}

					var expensesRejectCount = 0;
					if (json.expensesRejectCount != null
							&& json.expensesRejectCount != ""
							&& json.expensesRejectCount != 'undefined') {
						expensesRejectCount = json.expensesRejectCount;
					}

					var empExpensesSubmittedForApprovalCount = 0;
					if (json.empExpensesSubmittedForApprovalCount != null
							&& json.empExpensesSubmittedForApprovalCount != ""
							&& json.empExpensesSubmittedForApprovalCount != 'undefined') {
						empExpensesSubmittedForApprovalCount = json.empExpensesSubmittedForApprovalCount;
					}

					var empExpensesApprovedCount = 0;
					if (json.empExpensesApprovedCount != null
							&& json.empExpensesApprovedCount != ""
							&& json.empExpensesApprovedCount != 'undefined') {
						empExpensesApprovedCount = json.empExpensesApprovedCount;
					}

					var empExpensesRejectedCount = 0;
					if (json.empExpensesRejectedCount != null
							&& json.empExpensesRejectedCount != ""
							&& json.empExpensesRejectedCount != 'undefined') {
						empExpensesRejectedCount = json.empExpensesRejectedCount;
					}

					// var empExpensesReopenCount = json.empExpensesReopenCount;

					if (expenseType == "myExpenses") {
						if (expensesDraftCount > 0) {
							$("#dial1txt")
									.html(
											"<a onclick='myExpensesListByStage(\"draft\");'>Draft</a>");
							$("#expensesDraftCount").html(
									"<a onclick='myExpensesListByStage(\"draft\");'>"
											+ expensesDraftCount + "</a>");
						} else {
							$("#dial1txt").html("Draft");
							$("#expensesDraftCount").html(expensesDraftCount);
						}

					} else {
						if (empExpensesSubmittedForApprovalCount > 0) {
							$("#dial1txt")
									.html(
											"<a onclick='employeeExpensesListByStage(\"submitted\");'>Waiting</a>");
							$("#expensesDraftCount")
									.html(
											"<a onclick='employeeExpensesListByStage(\"submitted\");'>"
													+ empExpensesSubmittedForApprovalCount
													+ "</a>");
						} else {
							$("#dial1txt").html("Waiting");
							$("#expensesDraftCount").html(
									empExpensesSubmittedForApprovalCount);
						}

					}

					if (expenseType == "myExpenses") {
						$("#submittedId").show();
						if (expensesSubmittedForApprovalCount > 0) {
							$("#dial2txt")
									.html(
											"<a onclick='myExpensesListByStage(\"submitted\");'>Submitted</a>");
							$("#expensesSubmittedForApprovalCount").html(
									"<a onclick='myExpensesListByStage(\"submitted\");'>"
											+ expensesSubmittedForApprovalCount
											+ "</a>");
						} else {
							$("#dial2txt").html("Submitted");
							$("#expensesSubmittedForApprovalCount").html(
									expensesSubmittedForApprovalCount);
						}

					} else {
						$("#submittedId").hide();
					}

					if (expenseType == "myExpenses") {
						if (expensesApprovedCount > 0) {
							$("#dial3txt")
									.html(
											"<a onclick='myExpensesListByStage(\"approved\");'>Approved</a>");
							$("#expensesApprovedCount").html(
									"<a onclick='myExpensesListByStage(\"approved\");'>"
											+ expensesApprovedCount + "</a>");
						} else {
							$("#dial3txt").html("Approved");
							$("#expensesApprovedCount").html(
									expensesApprovedCount);
						}

					} else {
						if (empExpensesApprovedCount > 0) {
							$("#dial3txt")
									.html(
											"<a onclick='employeeExpensesListByStage(\"approved\");'>Approved</a>");
							$("#expensesApprovedCount")
									.html(
											"<a onclick='employeeExpensesListByStage(\"approved\");'>"
													+ empExpensesApprovedCount
													+ "</a>");
						} else {
							$("#dial3txt").html("Approved");
							$("#expensesApprovedCount").html(
									empExpensesApprovedCount);
						}
					}

					if (expenseType == "myExpenses") {
						$("#dial4txtDial").show();
						if (expensesRejectCount > 0) {
							$("#dial4txt")
									.html(
											"<a onclick='myExpensesListByStage(\"rejected\");'>Rejected</a>");
							$("#expensesRejectCount").html(
									"<a onclick='myExpensesListByStage(\"rejected\");'>"
											+ expensesRejectCount + "</a>");
						} else {
							$("#dial4txt").html("Rejected");
							$("#expensesRejectCount").html(expensesRejectCount);
						}
					} else {
						if (empExpensesRejectedCount > 0) {
							$("#dial4txt")
									.html(
											"<a onclick='employeeExpensesListByStage(\"rejected\");'>Rejected</a>");
							$("#expensesRejectCount")
									.html(
											"<a onclick='employeeExpensesListByStage(\"rejected\");'>"
													+ empExpensesRejectedCount
													+ "</a>");
						} else {
							$("#dial4txt").html("Rejected");
							$("#expensesRejectCount").html(
									empExpensesRejectedCount);
						}
					}

				}
			});

	if (expenseType == "myExpenses") {
		$("#expensesLink").attr("href", "/app/expenses");
	} else {
		$("#expensesLink").attr("href",	"/app/expensesApprovalList");
	}
}// expensive count end.
// get self leaves count 
function getSelfTotalCounts(){
	var orgLocationId =  locationId; // replcaced with dashboard variable.
	//var userId = '${SourceLeadWeb_userId}';
	 $.ajax({
	      type : "GET",
	      url: sourceleadUrl+"/totalCountsForSelfLeaves?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&userId="+userId,  
	       success :function(report) { 
	   	   if(report.selfPendingCounts != null && report.selfPendingCounts != '' && report.selfPendingCounts != 'undefined'){
	   		if(report.selfPendingCounts == 0){
//    				$('#selfapp').removeAttr("href");
   				$("#selfpend").html("Pending"); 
   			   }else{
   				$("#selfpend").html("<a href='/app/pendingLeaves'>Pending</a>"); 
   			   }
   			   $("#selfPendingCounts").html(report.selfPendingCounts); 
   		   }
   		   if(report.selfApprovedCounts != null && report.selfApprovedCounts != '' && report.selfApprovedCounts != 'undefined'){
   			if(report.selfApprovedCounts == 0){
//    				$('#selfrej').removeAttr("href");
   				$("#selfapp").html("Approved"); 
   			   }else{
   				$("#selfapp").html("<a href='/app/approvedLeaves'>Approved</a>");
   			   }
   			   $("#selfApprovedCounts").html(report.selfApprovedCounts); 
   		   }
   		   if(report.selfRejectedCounts != null && report.selfRejectedCounts != '' && report.selfRejectedCounts != 'undefined'){
   			   if(report.selfRejectedCounts == 0){
//    				$('#selfrej').removeAttr("href");
   				$("#selfrej").html("Rejected"); 
   			   }else{
   				$("#selfrej").html("<a href='/app/rejectedLeaves'>Rejected</a>");
   			   }
   			   $("#selfRejectedCounts").html(report.selfRejectedCounts); 
   		   }
   		   if(report.selfCancelledCounts != null && report.selfCancelledCounts != '' && report.selfCancelledCounts != 'undefined'){
   			   
   			if(report.selfCancelledCounts == 0){
//    				$('#selfrej').removeAttr("href");
   				$('#selfcanc').html("Cancelled");
   			   }else{
   				$("#selfcanc").html("<a href='/app/cancelLeaves'>Cancelled</a>");
   			   }
   			   $("#selfCancelledCounts").html(report.selfCancelledCounts); 
   		   }
   			if((report.totalBalLeaves != null && report.totalBalLeaves != '' && report.totalBalLeaves != 'undefined') || report.totalBalLeaves == '0'){
   			   $("#selfBalancedCounts").html(report.totalBalLeaves); 
   		   }
   		   
   		   },
		  });
	}

// employee leave count leave management  
function getEmployeeTotalCounts(){
	var orgLocationId =  locationId; // replcaced with dashboard variable.
//	var userId = '${SourceLeadWeb_userId}';
	 $.ajax({
	      type : "GET",
	      url: sourceleadUrl+"/totalCountsForEmployeeLeaves?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&userId="+userId,  
	       success :function(report) { 
	    	   if(report.employeeRequestedCounts != null && report.employeeRequestedCounts != '' && report.employeeRequestedCounts != 'undefined'){
	    		   if(report.employeeRequestedCounts == 0){
// 	      				$('#empReq').removeAttr("href");
	      				$('#empReq').html("Requested");
	      			   }else{
	      				 $('#empReq').html("<a href='/app/employeeRequestedLeaves'>Requested</a>"); 
	      			   }
    			   $("#employeeRequestedCounts").html(report.employeeRequestedCounts); 
    		   }
    		   if(report.employeeApprovedCounts != null && report.employeeApprovedCounts != '' && report.employeeApprovedCounts != 'undefined'){
    			   if(report.employeeApprovedCounts == 0){
//     	   				$('#empApp').removeAttr("href");
    	   				$('#empApp').html("Approved");
    	   			   }else{
    	   				$('#empApp').html("<a href='/app/employeeApprovedLeaves'>Approved</a>"); 
    	   			   }
    			   $("#employeeApprovedCounts").html(report.employeeApprovedCounts); 
    		   }
    		   if(report.employeeRejectedCounts != null && report.employeeRejectedCounts != '' && report.employeeRejectedCounts != 'undefined'){
    			   if(report.employeeRejectedCounts == 0){
//     	   				$('#empRej').removeAttr("href");
    	   				$('#empRej').html("Rejected");
    	   			   }else{
    	   				$('#empRej').html("<a href='/app/employeeRejectedLeaves'>Rejected</a>");
    	   			   }
    			   $("#employeeRejectedCounts").html(report.employeeRejectedCounts); 
    		   }
    		   if(report.employeeCancelledCounts != null && report.employeeCancelledCounts != '' && report.employeeCancelledCounts != 'undefined'){
    			   if(report.employeeCancelledCounts == 0){
//     	   				$('#empCanc').removeAttr("href");
    				    $('#empCanc').html("Cancelled");
    	   			   }else{
    	   				$('#empCanc').html("<a href='/app/employeeCancelLeaves'>Cancelled</a>");
    	   			   }
    			   $("#employeeCancelledCounts").html(report.employeeCancelledCounts); 
    		   }
	       },
		  });
}
 // end of leave counts section.