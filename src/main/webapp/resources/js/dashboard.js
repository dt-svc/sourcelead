/*// below details need to be dynamic as of now used the user details of 
//narasimha.seeral@businessintelli.com for india location 
var sourceleadUrl ="http://localhost:8080/app";//'${sourceleadUrl}'; // setting common user settings 
//var locationId = "5";//'${SourceLeadWeb_orgLocationId}'; //narasimha
var locationId = "3";//'${SourceLeadWeb_orgLocationId}'; snigitha
var locationList ="dd-MM-yyyy";//'${SourceLeadWeb_orgLocations.dateFormat}';	
//var userId ="31";// '${SourceLeadWeb_user.id}'; Narasimha
var userId ="4";// '${SourceLeadWeb_user.id}'; snigitha
//var orgLocationCode = "txhlIN"; //'${SourceLeadWeb_locationCode}'; Narasimha
var orgLocationCode = "775BUS";// snigitha
var orgCode = '775B'
//var candiadteId = "9105";//'${SourceLeadWeb_candidateId}'; Narasimha
var candiadteId = '9077';// snigitha
var isGlobalAccess = "N";//'${SourceLeadWeb_isGlobalAccess}';
var loginUserRole ="ROLE_ADMIN";// '${SourceLeadWeb_loginUserRole}';
var orgATS = "Y";//'${SourceLeadWeb_ats}';
var orgVMS ="Y";// '${SourceLeadWeb_vms}';
//var approverUserId ="31";// '${SourceLeadWeb_user.id}'; Narasimha
var approverUserId = '4'; // snigitha
//var loginUser = "Narasimha Seerla";//'${SourceLeadWeb_userName}';
var loginUser = 'Snigitha Paruchuri';
var date = $("#eventCalendar").val();
// its an entity commented now. var timezone ='${SourceLeadWeb_orgLocations.timezone}'; 
var requirementId = "";//'${requirementId}';
var timezoneId = "246" 
 if(timezone != null && timezone != '' && timezone != 'undefined'){
	timezoneId ="246" ;//'${SourceLeadWeb_orgLocations.timezone.id}';
} 
var country = "India";//'${SourceLeadWeb_orgLocations.country}';
var locationDate = locationList; // locationList is unique variable using for date format.
*/

var finalDateFormatEDashboardeventCalendar = '';
var tabAccessArray = '';
var roleActionsArray = '';
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
	      url: sourceleadUrl+"/totalCountsForSelfLeaves?orgLocationId="+orgLocationId+"&userId="+userId,  
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
	      url: sourceleadUrl+"/totalCountsForEmployeeLeaves?orgLocationId="+orgLocationId+"&userId="+userId,  
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
// job section start here....
// jobs or requirement list.
function requirementList(isWalkIn, walkInDay){  
	//  var sourceleadUrl ='${sourceleadUrl}';
	  $("#requirementListActions").empty();
	  $("#requirementList").empty();
	  $("#requirementLoading").show();
	  $("#requirementLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	  $.ajax({
         type : "POST",
         url: sourceleadUrl+"/getAssignedRequirementListByOrgLocationIDAndUserId?="+ (isWalkIn ? "&isWalkIn="+isWalkIn+"&walkInDay="+walkInDay : ""),    
          success :function(data) {
          	if (data.response === "FAILED") {
		    		showError(data.responseMessage);
		    	} else if (data.response === "AWKWARD") {
		    		location.href = "/errorPage";
		    	} else if (data.response === "SUCCESS") {
          	 var json = data.data;
               if(json != null && json != 'undefined' && json != "" && json != '[]'){
              	 $("#requirementList").empty();
	                	 var result = "";
	                	 result = result +"<table id='requirementPagination' class='display' cellspacing='0' width='99.9%' >"; 
	                     result = result +"<thead>"; 
	                     result = result +"  <tr>"; 
	                     result = result +"    <th>Actions</th>";
	                     result = result +"    <th>Generated ID</th>"; 
	                     result = result +"    <th>Title</th>";
	                     if(isWalkIn) {
	                    	 result = result +"    <th>Drive Day(s)</th>";
	                     }
	                     if(orgATS == 'Y'){
	                     result = result +"    <th>Client</th>";
	                     }
	                     result = result +"    <th>Rate</th>"; 
	                     result = result +"    <th>Priority</th>"; 
	                     result = result +"    <th>Applicants</th>"; 
	                     result = result +"    <th>Shortlisted</th>"; 
	                     result = result +"    <th>Submitted</th>"; 
	                     result = result +"    <th>Interview</th>"; 
	                     result = result +"    <th>Offered</th>";
	                     result = result +"    <th>Joined</th>";
	                     result = result +"    <th>Status</th>";
//	                     result = result +"    <th>Release</th>"; 
	                     result = result +"  </tr>"; 
	                     result = result +"</thead>";
	                     result = result +"<tbody>"; 
	                     
	                     result = result + '<div class="new_side_wrap">';
	                 	 result = result + '<div class="new_side_wrap_inn">';
	                 	 result = result + '<div class="new_side_wrap_cta">';
	                 	 result = result +'<div id="addApplicantDiv"></div>';
	                 	 result = result +'</div>';
	                 	 result = result +'</div>';
	                 	 result = result +'</div>';
	                 	 
	                 	var checkSize = json.length;
	                     if(checkSize>10){
	                         checkSize = 10;
	                     }
	                     else{
	                         checkSize = json.length;
	                     }
	                     for (var i = 0; i < checkSize; i++) { 
	                    	 var opt = json[i]; 
	                    	 
	                    	 result = result +"<tr>";
	                    	  result = result +"<td>";
			                     result = result + '<div class="tblminwidth-75">';
			                     
			         	        '';
				         	       if(opt.requirementSubmissionStatus == 'APPROVED' || opt.requirementSubmissionStatus == 'OPEN' || opt.requirementSubmissionStatus == 'NEW'){
				         	    	  result = result +'<div class="text-center pull-left"><a href="javascript:void(0)" title="Add Applicant" onclick="getAddApplicant('+opt.id+',\''+opt.reqCoolingOffPeriod+'\');" class="add-applicant"><i class="fa fa-user-plus"></i></a></div>';
				         	       }else{
				         	    	  result = result +"<div class='text-center pull-left'>---</div>";
				         	       }
			         	        '';
			         	        
			         	       '';
			         				result = result +'<div class="text-center pull-left ml10 mr10"><a href="javascript:void(0)" title="Assign To" data-toggle="modal" data-target="#myModal" onclick="getAssignedMembersDiv('+opt.id+');"><i class="fa fa-address-book '+(opt.assignedMembersCount == 0 ? "anchorBlack" : "")+'"></i></a></div>';
			         		   '';
			         	        
			         	       '';
			                     if(opt.requirementSubmissionStatus == 'APPROVED' || opt.requirementSubmissionStatus == 'OPEN' || opt.requirementSubmissionStatus == 'NEW'){
			                    	 if(opt.isRequirementReleasedByuser == 'Y'){
			                       		 result = result +"<div class='pull-left'><a href='/app/releaseRequirementToClientOrVendors?requirementUUID="+opt.requirementUUID+"'><i class='fa fa-bullhorn success' title='Release Job'></i></a></div>"; 
			                       	 }else{	
			                       	 result = result +"<div class='pull-left'><a href='/app/releaseRequirementToClientOrVendors?requirementUUID="+opt.requirementUUID+"'><i class='fa fa-bullhorn' title='Release Job'></i></a></div>";
			                       	 }
			                     }else{
				                     result = result +"<div class='pull-left'>---</div>";
			                     } 
			                     '';
			                     result = result + '</div>';
			                     result = result +"</td>";
			                     
	                    	 if(opt.generatedId!=null && opt.generatedId !='' && opt.generatedId !='undefined'){
	                    		 result = result +"<td><div class=''><span title='"+opt.generatedId+"'>"+opt.generatedId+"</span></a></div></td>";
	                    	 }
	                    	 else{
	                    		 result = result +"<td><div>---</div></td>";
	                    	 }
	                    	 if(opt.jobId!=null && opt.jobId !='' && opt.jobId !='undefined'){
	                    		 result = result +"<td><div class='tbldata-dotdot-sm'> <a href='"+sourceleadUrl+"/requirementSummary?requirementUUID="+opt.requirementUUID+"'><span title='"+opt.jobTitle+"'>"+opt.jobTitle+"</span></a></div></td>";
	                    	 }else{
		                    	 result = result +"<td><div class='tbldata-dotdot-sm'> <a href='"+sourceleadUrl+"/requirementSummary?requirementUUID="+opt.requirementUUID+"'><span title='"+opt.jobTitle+"'>"+opt.jobTitle+"</span></a></div></td>";
		                     }
	                    	 if(isWalkIn) {
	                    		 result = result +"<td><div class=''><span title='"+opt.walkInDays+"'>"+opt.walkInDays+"</span></a></div></td>";
	                    	 }
		                     if(orgATS == 'Y'){
			                     if(opt.clientName != null && opt.clientName != '' && opt.clientName != 'undefined'){
			                    	
			                    	 '';
			                    	  result = result + '<td><div  class="tbldata-dotdot-sm"><a href="'+sourceleadUrl+'/clientDashboard?orgRelationUUID='+opt.orgRelationUUID+'" title="'+opt.clientName+'">'+opt.clientName+'</a></div></td>';
				           	         '';
         	         
					           	      '';
			                    	 
			                    	 
			                     }else{
			                    	 result = result +"<td><div>---</div></td>";
			                     }
		                     }
		                     if(opt.rate != null && opt.rate != '' && opt.rate != 'undefined' && opt.maxRate != null && opt.maxRate != '' && opt.maxRate != 'undefined'){
			                     result = result +"<td><div><span  title='"+opt.currencyCode+"&nbsp;"+opt.rate+"-"+opt.maxRate+"&nbsp;"+opt.rateType+"'>"+opt.currencyCode+"&nbsp;"+opt.rate+"-"+opt.maxRate+"&nbsp;"+opt.rateType+"</span></div></td>";
		                     }else if(opt.rate != null && opt.rate != '' && opt.rate != 'undefined'){
			                     result = result +"<td><div><span  title='"+opt.currencyCode+"&nbsp;"+opt.rate+"&nbsp;"+opt.rateType+"'>"+opt.currencyCode+"&nbsp;"+opt.rate+"&nbsp;"+opt.rateType+"</span></div></td>";
	 
		                     }
		                     else{
			                     result = result +"<td><div><span><span>---</span></span></div></td>";
		                     }
		                     if(opt.priority != null && opt.priority != '' && opt.priority != 'undefined'){
		                    	 result = result +"<td><div>"+opt.priority+"</div></td>";
		                     }else{
		                    	 result = result +"<td><div>---</div></td>";
		                     }
		                     
		                     
//		                     if(opt.noOfApplicants != null && opt.noOfApplicants != '' && opt.noOfApplicants != 'undefined'){
	
	                    	 result = result +"<td><input type='hidden' class='requirementDashboardCount'  id='"+opt.id+"' /><a href='/app/requirementSummary?type=applicants&requirementUUID="+opt.requirementUUID+"'><div id='"+opt.id+"applicantCount'><i class='fa fa-spinner fa-pulse'></i></div></a></td>";
	                    	 
//		                     }
//		                     if(opt.noOfShortlistedApplicants != null && opt.noOfShortlistedApplicants != '' && opt.noOfShortlistedApplicants != 'undefined'){
	
		                     result = result +"<td><a href='/app/requirementSummary?type=SHORTLIST&requirementUUID="+opt.requirementUUID+"'><div id='"+opt.id+"shortlistedCount'><i class='fa fa-spinner fa-pulse'></i></div></a></td>";
		                     
//		                     }
//		                     if(opt.noOfSubmittedApplicants != null && opt.noOfSubmittedApplicants != '' && opt.noOfSubmittedApplicants != 'undefined'){
	
			                 result = result +"<td><a href='/app/requirementSummary?type=SUBMIT_TO_CLIENT&requirementUUID="+opt.requirementUUID+"'><div id='"+opt.id+"submittedCount'><i class='fa fa-spinner fa-pulse'></i></div></a></td>";
			                 
//		                     }
//		                     if(opt.noOfInterviewedApplicants != null && opt.noOfInterviewedApplicants != '' && opt.noOfInterviewedApplicants != 'undefined'){
		                     result = result +"<td><a href='/app/requirementSummary?type=INTERVIEW&requirementUUID="+opt.requirementUUID+"'><div id='"+opt.id+"interviewedCount'><i class='fa fa-spinner fa-pulse'></i></div></a></td>";
//		                     }
//		                     if(opt.noOfOfferedApplicants != null && opt.noOfOfferedApplicants != '' && opt.noOfOfferedApplicants != 'undefined'){
		                     result = result +"<td><a href='/app/requirementSummary?type=OFFERMADE&requirementUUID="+opt.requirementUUID+"'><div id='"+opt.id+"offeredCount'><i class='fa fa-spinner fa-pulse'></i></div></a></td>";
//		                     }
//		                     if(opt.noOfJoinedApplicants != null && opt.noOfJoinedApplicants != '' && opt.noOfJoinedApplicants != 'undefined'){
			                     result = result +"<td><a href='/app/requirementSummary?type=JOINED&requirementUUID="+opt.requirementUUID+"'><div id='"+opt.id+"joinedCount'><i class='fa fa-spinner fa-pulse'></i></div></a></td>";
//			                     }



		                     if(opt.requirementSubmissionStatus!=null && opt.requirementSubmissionStatus !='' && opt.requirementSubmissionStatus !='undefined'){
	                    		 result = result +"<td><div class=''><span title='"+opt.requirementSubmissionStatus+"'>"+opt.requirementSubmissionStatus+"</span></a></div></td>";
	                    	 }
	                    	 else{
	                    		 result = result +"<td><div>---</div></td>";
	                    	 }
		                     
		                   
		                    if(checkSize>=10 && i==9 ){
		                    	/* $("#viewmoresDiv").css("display", "block"); */
		                    	var text = "<div class='pull-right' id='viewmoresDiv'>";
		                    	text += "<a href='/app/jobs' class='btn btn-sm btn-primary pull-right' title='View more'> View More</a>";
		                    	text += "</div>";
		                    	$("#requirementListActions").append(text);
			 	   	   	    } 
		                     result = result +"</tr>";
		                     result = result +"</tr>";
	                     }
	                     result = result +" </tbody>";
	                     result = result +"</table>";
	                     
	                     if(isWalkIn) {
	                    	 $("#requirementListActions").append(getDynamicDayActionsForWalkInJobs(parseInt(data.responseMessage.split(":")[1]) - 1, data.responseMessage.split(":")[2], parseInt(data.responseMessage.split(":")[1]) + 1));
	                     }
	                     $("#requirementList").append(result);
	                     $("#requirementLoading").hide();
	                     /* $("#requirementLoading").hide(); */
	                     /* $("#assignedRequirementsDiv").css("display", "block"); */
	                     
	                	 $(".proj_close, .side_mask").click(function(){
								$(".clients_side_wrap").removeClass("clients_side_wrap_active");
								$(".side_mask").fadeOut(200);
								$("body").css('overflow-y', 'auto');
						 });	
	                     pagination(); 
	                     getDashboardJobCounts();
               	} else {
               		$("#requirementList").empty();
               		$("#requirementList").append("<table id='noResultsTable'><tr class='odd'><td colspan='7' class='dataTables_empty' valign='top'></td></tr></table>");
               		if(isWalkIn) {
	                 		$("#requirementListActions").append(getDynamicDayActionsForWalkInJobs(parseInt(data.responseMessage.split(":")[1]) - 1, data.responseMessage.split(":")[2], parseInt(data.responseMessage.split(":")[1]) + 1));
               		}
               	}
               	 $("#requirementLoading").hide();
               	 $("#requirementList").removeAttr("style");
	                 if(isWalkIn) {
	        	    	 $("#assigned").removeClass("current");
	        	    	 $("#drive").addClass("current"); 
	                 } else {
	        	    	 $("#assigned").addClass("current");
	        	    	 $("#drive").removeClass("current");
	                 }
		    	} else if (data.indexOf('Sign In') !== -1) {
		    		location.replace(sourceleadUrl);
		    	} else {
		    		showError("Operation failed, please try again");
		    	}
          	
        },
          
     }); 
}
// get job status count individually function .
function getDashboardJobCounts(){
	 $('.requirementDashboardCount').each(function() {
		 var reqId = this.id;
		 if(reqId != null &&  reqId !='undefined' && reqId != ''){
			 $.ajax({
		           type : "GET",
		           url: sourceleadUrl+"/dashboardPipelineJobsCount?requirementId="+reqId,    
		            success :function(data) {
		            	  $("#"+reqId+"applicantCount").empty();
		             	  $("#"+reqId+"applicantCount").html(data.applicantsCount);
		             	  
		             	  $("#"+reqId+"shortlistedCount").empty();
		             	  $("#"+reqId+"shortlistedCount").html(data.shortlistCount);
		             	  
		             	  $("#"+reqId+"interviewedCount").empty();
		             	  $("#"+reqId+"interviewedCount").html(data.interviewCount);
		             	  
		             	  $("#"+reqId+"submittedCount").empty();
		             	  $("#"+reqId+"submittedCount").html(data.submitToClientCount);
		             	  
		             	  $("#"+reqId+"offeredCount").empty();
		             	  $("#"+reqId+"offeredCount").html(data.offersCount);
		             	  
		             	  $("#"+reqId+"joinedCount").empty();
		             	  $("#"+reqId+"joinedCount").html(data.joinedCount);
		            }
			    });
		 }
			
  }); 
}
// related to jobs or requirement section.
function getDynamicDayActionsForWalkInJobs(prevDay, selectedDay, nextDay){
	var text="<div class='walk_in_days pull-right mb10 mt10'>";
	 text += "<a  class='btn btn-default pull-left mr btn-sm' href='javascript:void(0)' onclick='requirementList(true," + prevDay + ")'>&lt;&lt;</a>";
	 text += "<div class='paginate_button pull-left'>" + selectedDay + "</div>";
	 text += "<a  class='btn btn-default pull-left ml btn-sm' href='javascript:void(0)' onclick='requirementList(true," + nextDay + ")'>&gt;&gt;</a>";
	 text += "</div>";
	 return text;
}
// requirement or jobs pagination.
function pagination(){ 
    $('#requirementPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[5, 10, 15, -1], [5, 10, 15, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"bFilter": false,
    	"bPaginate":false,
    	"ordering": true,
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    } );
}

// jobs or requirement related function 
function getAssignedMembersDiv(reqId){
	$("#selectedUsersGrid").empty();
	$("#summaryButtonsDiv").hide();
	$("#listButtonsDiv").show();
	$('#listSaveButton').removeAttr('onclick');
	$('#listSaveButton').attr('onClick', 'editAssignedTeamsOrUsers('+reqId+');');
	getAssignedMembersListForEdit(reqId);
	
}
//jobs or requirement related function 
function getAssignedMembersListForEdit(reqId){
	if(reqId != '' && reqId != 'undefined' && reqId != null){
		$.ajax({
	           type : "POST",
	           url: sourceleadUrl+"/getAssignedMembersForEdit?reqId="+reqId,    
	            success :function(result) {
		            	var json = result.object;
		            	if (result.response === "SESSION_TIMEDOUT") {
			  				location.replace(sourceleadUrl);
		                } else if (result.response === "AWKWARD") {
		                    location.href = "/errorPage";
		                } else if (result.response === "SUCCESS") {
		                	selectAssignUsersIds = json;
			            	$("#assignedTeamId").val(json);	
		                } else if (result.indexOf('Sign In') !== -1) {
							location.replace(sourceleadUrl);
						} else {
		                    showError("Operation failed, please try again");
		                }
	            },
	            complete: function (result) {
	            	getTeamsList(reqId);
	            }
	      }); 
	}
}
//jobs or requirement related function 
function editAssignedTeamsOrUsers(requirementId){
	//assignedMemberList(requirementId);
	$('.assignedGrid2').click();
	var assignedIdVal = [];
	var assignedToEditCount = 0;
	$("#selectedUsersGrid input[type=checkbox]").each(function(){
		assignedIdVal.push($(this).val());
		assignedToEditCount++;
     });
	$("#assignedTeamId").val(assignedIdVal);
	handleTeamRoleClick();
	$("#forAssignedToCount1").text(assignedToEditCount);
	$("#forAssignedToCount2").text(assignedToEditCount);
		$.ajax({
	           type : "POST",
	           url: sourceleadUrl+"/saveOrUpdateAssignToTeam?assignedIdVal="+assignedIdVal+"&requirementId="+requirementId,    
	            success :function(result) {
	            	if (result.response === "SESSION_TIMEDOUT") {
		  				location.replace(sourceleadUrl);
	                } else if (result.response === "AWKWARD") {
	                    location.href = "/errorPage";
	                } else if (result.response === "SUCCESS") {
	                	
	                } else if (result.indexOf('Sign In') !== -1) {
						location.replace(sourceleadUrl);
					} else {
	                    showError("Operation failed, please try again");
	                }
	            },
   		});
	$("#usersGrid").empty();
}
/// jobs or requirement related function
window.teamRoleIds = new Array();
function handleTeamRoleClick() {
	teamRoleIds = [];
	$("option[name=TeamAndRole]:selected").each(function(){
		teamRoleIds.push($(this).val());
	});
}
/// jobs or requirement related function
function getAddApplicant(requirementId,reqCoolingOffPeriod){
	$('.action-pop').slideUp();
	//var sourceleadUrl ='${sourceleadUrl}';
	  var finalDateFormatScript = '';
	  if(locationDate.match("yyyy-MM-dd")){
		  finalDateFormatScript = 'yy-mm-dd';
	  }if(locationDate.match("dd-MM-yyyy")){
		  finalDateFormatScript = 'dd-mm-yy'; 
	  }if(locationDate.match("MM-dd-yyyy")){
		  finalDateFormatScript = 'mm-dd-yy'; 
	  }if(locationDate.match("yyyy-dd-MM")){
		  finalDateFormatScript = 'yy-dd-mm'; 
	  }
	generateOrgSubmissionForm(sourceleadUrl,requirementId,finalDateFormatScript) // refereneced guestApplyToJob.jsp as in guestUserSubmissionForm-html-generator.js
	var result = "";
	result = result + '<form id="addApplicant_form" name="addApplicant_form" enctype="multipart/form-data" method="post">';
	result = result + '<h3>Add Applicant</h3>';
	result = result + '<div class="form-horizontal clearfix select-field applicant-label">';
	result = result + '<input type="hidden" id="requirementId" name="requirementId" value='+requirementId+'>';
	result = result + '<input type="hidden" id="reqCoolingOffPeriod" name="reqCoolingOffPeriod" value=\''+reqCoolingOffPeriod+'\'>';
	result = result + '<div class="row">';
	result = result + '<div class="venk clearfix">';
	result = result + '<div class="col-md-7 radioInline">';
	result = result + '<label class="mr"><input type="radio" checked="checked" value="N" name="candidateType" onClick="getCandidatesList('+requirementId+',\'N\');">Candidates</label>';
	
	result = result + '<label class="mr"><input type="radio" value="New candidate" name="candidateType" onClick="getCandidatesList('+requirementId+',\'New\');getCandidateSelectionType();">Add New Candidate</label>';
	result = result + '<label class="mr"><input type="radio" value="Parese candidate" name="candidateType" onClick="getCandidatesList('+requirementId+',\'parseCandidate\');getCandidateSelectionType();">Import Candidate</label>';
	result = result + '</div>';	
	
	result = result + '<div class="col-md-5 pull-right">';
	result = result + '<div class="col-md-12" id="browseDiv"  style="display: none;">';
	result = result + '<div class="input-group" id="uploadFileGroup">';
	result = result + '<label class="input-group-btn" onchange="importAddApplicantDoc();"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File';
	result = result + '<input type="file" style="display: none;" name="locationMapFile" multiple id="uploadBtn"  class="upload">';
	result = result + '</span> </label>';
	result = result + '<input type="text" class="form-control" data-validation="validatedoc" data-validation-optional="true" field-name="Resume" readonly id="uploadFile" placeholder="Upload Resume">';
	result = result + '</div>';
	result = result + '</div>';	
	result = result + '</div>';
	result = result + '<div class="clearfix"></div>';
	result = result + '<div class="col-md-6 mt10">';
	result = result + '<div id="selectCandidateDiv" style="display: block;">';
	result = result + '<select class="form-control chosen-select" onchange="getCandidateDetails(); getCandidateResumes();"  name="candidateId" id="candidateId" data-validation="required" field-name="Applicant" class="form-control  block mt10">';
	result = result + '<option value="">-- Select Candidate --</option>';
	result = result + '</select>';
	result = result + '</div>';	
	result = result + '</div>';	
	
	result = result + '<div class="panel-border_a col-md-5 mt10" id="resumesDiv"  style="display: none;">';
	result = result + '<select id="candidateResumesList" name="candidateResume" field-name="Resume" data-validation-optional="true" class="form-control block">';
	result = result + '</select>';
	result = result + '</div>';
	
	result = result + '<div id="orConditionDiv" class="text-center text-info col-md-2" style="display: none;">';
	result = result + '------- ( OR ) -------';
	result = result + '</div>';
	
	
	
	result = result + '</div>';
	
	result = result + '<div id="sjfb-fields" class="viewTemplate"> <div id="loading"></div> <div id="emailDiv"></div>';
	
	result = result + '</div>';
	
                        
	result = result + '</div>';
	
		
	result = result + '<div class="row">';
    result = result + '	<div class="col-md-6 upresume mb" id="skillsDiv" style="display: none;">';
	result = result + '<label class="col-md-4">Skills</label>';
	result = result + '<div class="col-md-8">';
	result = result + '<select multiple data-placeholder="select Skills" class="chosen-select form-control" name="candidateSkills" id="candidateSkills"  data-validation-optional="true">';
	result = result + '';
	result = result + "<option value='3D Max'>3D Max</option>";
	result = result + "<option value='MVC.net'>MVC.net</option>";
	result = result + ' ';
	result = result + '</select>';
	result = result + '		 </div>';
	result = result + '	 </div>';
	result = result + '	 </div>';
	
	result = result + '<div class="row">';
    result = result + '	<div class="col-md-6 upresume mb" id="primarySkillDiv" style="display: none;">';
	result = result + '<label class="col-md-4">Primary Skill</label>';
	result = result + '<div class="col-md-8">';
	result = result + '<input type="text" class="form-control" id="primarySkill" name="primarySkill"  field-name="Primary Skill">';
	result = result + '</div>';
	result = result + '</div>';
	result = result + '</div>'; 
	
	result = result + '<div class="row">';
    result = result + '	<div class="col-md-6 upresume mb" id="employmentTypeDiv" style="display: none;">';
	result = result + '<label class="col-md-4">Employment<br/> Type<span class="paddingLeft">*</span></label>';
	result = result + '<div class="col-md-8">';
	result = result + '<select class="form-control" id="designationType" style="width: 100%;" name="designationType" onchange="getEmployementType();" field-name="Employment Type">';
	result = result + '<option value="">--Select Employment Type--</option>';
	result = result + '</select>';
	result = result + '</div>';
	result = result + '</div>';
	result = result + '</div>'; 
	
	
    result = result + '<div class="row">';
    result = result + '	<div class="col-md-6 upresume mb" id="employmentOtherTypeDiv" style="display: none;">';
    result = result + '<label class="col-md-4"></label>';
    result = result + '<div class="col-md-8">';
	result = result + '<input type="text" class="form-control" placeholder="Employment Other Type" id="designationOtherType" name="designationOtherType" field-name="Employment Other Type" >';
	result = result + '</div>';
	result = result + '</div>';
	result = result + '</div>'; 
	
	
    result = result + '<div class="row">';
    result = result + '	<div class="col-md-6 upresume mb" id="vendorsDiv" style="display: none;">';
	result = result + '<label class="col-md-4">Vendors</label>';
	result = result + '<div class="col-md-8">';
	result = result + '<select class="chosen-select form-control" id="vendors" data-validation-optional="true" name="vendors" onchange="getAddApplicantVendorContactList();" field-name="Vendors">';
	result = result + '</select>';
	result = result + '</div>';
	result = result + '</div>';
	result = result + '</div>';
	
	
	result = result + '<div class="row">';
    result = result + '	<div class="col-md-6 upresume mb" id="vendorContactsDiv" style="display: none;">';
	result = result + '<label class="col-md-4">Vendor Contacts</label>';
	result = result + '<div class="col-md-8">';
	result = result + '<select class="chosen-select form-control" id="vendorContact" data-validation-optional="true" name="vendorContact"  field-name="vendor Contacts">';
	result = result + '<option value="">--Select Vendor Contacts--</option>';
	result = result + '</select>';
	result = result + '</div>';
	result = result + '</div>';
	result = result + '</div>'; 
	
	
	
	result = result + '<div class="row">';
	result = result + '	<div class="col-md-6 upresume mb" id="newResumeDiv" style="display: none;">';
	result = result + '<label class="col-md-4">Add Resume</label>';
	result = result + '		<div class="col-md-8">';
	result = result + '			    <div class="input-group col-md-12 attachfile-error" id="uploadFileGroup1" style="margin:0 auto">';
	result = result + '			        <label class="input-group-btn" onchange="uploadAddApplicantCandidateDoc();"> <span class="btn btn-danger"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File';
	result = result + '                   <input type="file" style="display: none;" name="newUploadBtn" id="newUploadBtn"  class="upload">';
	result = result + '			          </span> </label>';
	result = result + '			          <input type="text" class="form-control" readonly id="newUploadFile" placeholder="Upload Resume ..." field-name="Upload Resume" data-validation-optional="true" data-validation="validatedoc "  >';
	result = result + '			    </div>';
	result = result + '		 </div>';
	result = result + '	 </div>';
	
	
	result = result + '	<div class="col-md-6 upresume mb">';
	result = result + '<label class="col-md-4">&nbsp;</label>';
	result = result + '<input type="checkbox" name="needApplicantPermit" id="needApplicantPermit" onClick="ApplicantPermit();" >&nbsp;&nbsp;<strong>Need applicant permission</strong><br> ';
	result = result + '<input type="checkbox" name="notifyToApplicant" value="Y" id="notifyToApplicantId" onClick="NotifyToApplicant();" >&nbsp;&nbsp;<span><strong>Notify to applicant</strong></span> ';
	result = result + '</div>';
	result = result + '</div>';
	
	result = result + '<div class="form-footer clearfix mt">';
	result = result + '<div class="pull-right mr">';
	result = result + '<button type="submit" id="save_button"  class="btn btn-sm btn-primary " >SUBMIT</button>';
	result = result + '</div>';
	result = result + '</div>';
	result = result + '</div>';  
	result = result + '</form>';
	
	
	 
	getCandidatesList(requirementId,'N');
	
	
	$("#addApplicantDiv").empty();
	$("#addApplicantDiv").append(result);
	
	   var config = {
				  '.chosen-select'           : {},
				  '.chosen-select-deselect'  : {allow_single_deselect:true},
				  '.chosen-select-no-single' : {disable_search_threshold:10},
				  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
				  '.chosen-select-width'     : {width:"95%"}
				}
	   
	   
	 //Adding Customized Skills to the list
       var select, chosen;
	    select = $("#candidateSkills");
	    select.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add New Skill:' });
	    chosen = select.data('chosen');
	    
	    
	    $("#candidateSkills").chosen().change(function(event) {
	    	// var sourceleadUrl ='${sourceleadUrl}';
	    	var allSkills = $(event.target).val();
	   
	    	
	    	$.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/profileBySkillSet?=&allSkills="+allSkills, 
		        success: function(response) {
		        	if (response.response === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (response.response === "SUCCESS") {
		        		var primarySkill = response.object;
			        	$("#primarySkill").val(primarySkill);
		        	} else if (response.indexOf('Sign In') !== -1) {
		        		location.replace(sourceleadUrl);
		        	} else {
		        	    showError("Operation failed, please try again");
		        	}
		        },
		    });
	    });
	    
	    chosen.search_field.on('keydown keyup', function(e)
			    {
			    	   var skillsArray = this.value.split(",");
				    	 if(skillsArray.length > 1){
			        		 for (var i=0; i < skillsArray.length; i++) {
			        			 var option = $("<option>").val(skillsArray[i].replace(",","")).text(skillsArray[i].replace(",",""));
			        			 select.append(option);
			 		             select.find(option).prop('selected', true);
			 		             select.find(option).siblings("[value='"+ skillsArray[i].replace(",","")+"']").remove();
			 	        		 $("#candidateSkills").trigger("chosen:updated");
			                 }
				    	 }else{
			    			var newSkill = this.value.replace(","," ");
			    			 if (tagItBykeyForSkills(e) && (newSkill != null  && newSkill != '' && newSkill != 'undefined'))
			                {
			    				 preventTabkey(e);
			    				 var option = $("<option>").val(newSkill).text(newSkill);
			                     select.append(option);
			                     select.find(option).prop('selected', true);
			                     $("#candidateSkills option").each(function(){
			                         $(this).siblings("[value='"+ this.value+"']").remove();
			                   });
			                   $("#candidateSkills").trigger("chosen:updated");
			                }
					       
		    	         }
	    	});
	    //Adding Customized Skills to the list
			
	$("#candidateSkills").val('').trigger("chosen:updated");
	    
		for (var selector in config) {
		  $(selector).chosen(config[selector]);
		}
		$('.chosen-select').each(function() {
			var $this = $(this);
			$this.next().css({'width': '100%'});
			})
	
	
	$(".new_side_wrap").addClass("new_side_wrap_active");
	$(".side_mask").fadeIn(200);
	$("body").css('overflow-y', 'hidden');
	
	var selectedFile = document.getElementById("newUploadBtn");
    selectedFile.addEventListener("change", function() {
    	 $("#newUploadFile").focus();
    	 if(validate_fileupload(this.value)){
    		 var docSize = this.files[0].size;
   	      $("#uploadFileGroup1").find('p.jquery_form_error_message').remove();
   	      if(docSize > 2097152){
   	    	  document.getElementById("newUploadFile").value = '';
   	    	  $('#uploadFileGroup1').find('p.jquery_form_error_message').remove();
   			  $("input#newUploadFile").after("<p class='jquery_form_error_message'>Limit is exceeded than 2MB </span>");
   			  document.getElementById("newUploadFile").setAttribute('record-exist','yes');
   			  document.getElementById("newUploadFile").setAttribute('record-exist-errorMsg','Limit is exceeded than 2MB'); 
   	      }else{
   	    	 $('input#newUploadFile').removeAttr( "record-exist record-exist-errormsg");
      		 $('#uploadFileGroup1').find('p.jquery_form_error_message').remove(); 
    	    }
    	 }
    });
	
	document.getElementById("newUploadBtn").onchange = function () {
		$('#newUploadFile').val($(this).val().replace(/C:\\fakepath\\/i, ''));
	};
	
}
/// jobs or requirement related function
function ApplicantPermit(){
	 if($("#needApplicantPermit").is(':checked')){
		  $("#needApplicantPermit").val("Y");
	 }else{
		 $("#needApplicantPermit").val("N");
	 }
}
/// jobs or requirement related function
function NotifyToApplicant(){
	 if($("#notifyToApplicantId").is(':checked')){
		  $("#notifyToApplicantId").val("Y");
	 }else{
		 $("#notifyToApplicantId").val("N");
	 }
}
/// jobs or requirement related function
function isCoolingOffAlert(){
	var reqCoolingOffPeriod = document.getElementById("reqCoolingOffPeriod").value;
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Applicant Cooling-Off period "+reqCoolingOffPeriod+" not completed</p>";
	result = result + ' <div id="reasonForSpocDiv"/>';
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Proceed' onClick='proceedApplicant();'><input class='u_can_color' type='button' value='Discard' onClick='discordApplicant();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
/// jobs or requirement related function
function proceedApplicant(){
	$("#popUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
/// jobs or requirement related function
function discordApplicant(){
	$("#save_button").attr("disabled", "disabled");
	$("#popUp").empty();
	$(".project_inn_mask").fadeOut(200);
	
	$(".new_side_wrap").removeClass("new_side_wrap_active");
	$(".side_mask").fadeOut(200);
	$("body").css('overflow-y', 'auto');
}
/// jobs or requirement related function
function getCandidateDetails(){
	var candidateId = document.getElementById("candidateId").value
	//var sourceleadUrl ='http://localhost:8080/app';
	var requirementId = $("#requirementId").val();
	var orgLocationId = locationId;
	if(candidateId != null && candidateId != "" && candidateId != 'undefined'){
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getCandidateDetails?=&candidateId="+candidateId+"&requirementId="+requirementId+"&orgLocationId="+orgLocationId, 
		        success: function(data) {
		        	if (data.response === "FAILED") {
			    		showError(data.responseMessage);
			    	} else if (data.response === "AWKWARD") {
			    		location.href = "/errorPage";
			    	} else if (data.response === "SUCCESS") {
			    		var json = data.object;
			        	 $("input[name=FirstName]").val(json['firstName']);
			        	 $("input[name=EmailId]").val(json['email']);
			        	 $("input[name=EmailId]").attr('readonly', 'readonly');
			        	 
			        	 if(json['email'] != null && json['email'] != "" && json['email'] != 'email'){
			        		 var requirementId = $("#requirementId").val();
			        		 isCoolingOff(requirementId);
			        	 }
			        	 
			        	 if(json['lastName'] != null && json['lastName'] != "" && json['lastName'] != 'undefined'){
			        		 $("input[name=LastName]").val(json['lastName']);
			        	 }else{
			        		 $("input[name=LastName]").val(''); 
			        	 }
			        	 
			        	 if(json['mobilePhone'] != null && json['mobilePhone'] != "" && json['mobilePhone'] != 'undefined'){
			        		 $("input[name=PhoneNumber]").val(json['mobilePhone']);
			        	 }else{
			        		 $("input[name=PhoneNumber]").val(''); 
			        	 }
			        	 
			        	 if(json['alternateMobilePhone'] != null && json['alternateMobilePhone'] != "" && json['alternateMobilePhone'] != 'undefined'){
			        		 $("input[name=AlternateNumber]").val(json['alternateMobilePhone']);
			        	 }else{
			        		 $("input[name=AlternateNumber]").val(''); 
			        	 }
			        	 
			        	 
			        	 if(json['employer'] != null && json['employer'] != "" && json['employer'] != 'undefined'){
			        		 $("input[name='Current/LastCompany']").val(json['employer']);
			        	 }else{
			        		 $("input[name='Current/LastCompany']").val(''); 
			        	 }
			        	 
			        	 if(json['designation'] != null && json['designation'] != "" && json['designation'] != 'undefined'){
			        		 $("input[name=Designation]").val(json['designation']);
			        	 }else{
			        		 $("input[name=Designation]").val(''); 
			        	 }
			        	 
			        	 if(json['candidateEdu'] != null && json['candidateEdu'] != "" && json['candidateEdu'] != 'undefined'){
			        		 $("input[name=Education]").val(json['candidateEdu']);
			        	 }else{
			        		 $("input[name=Education]").val(''); 
			        	 }
			        	 
			        	 if(json['country'] != null && json['country'] != "" && json['country'] != 'undefined'){
			        		 $("select[name=country]").val(json['country']);
			        	 }else{
			        		 $("select[name=country]").val('');
			        	 }
			        	 
			        	 if(json['state'] != null && json['state'] != "" && json['state'] != 'undefined'){
			        		 $("select[name=state]").val(json['state']);
			        		 getCandidateStates(json['state']);
			        	 }else{
			        		 $("select[name=state]").val('');
			        	 }
			        	 
			        	 if(json['city'] != null && json['city'] != "" && json['city'] != 'undefined'){
			        		 getSubmissionCandidateCities(json['city']);
			        	 }else{
			        		 $("select[name=city]").val('');
			        	 }
			        	 
			        	 if(json['dateofBirthString'] != null && json['dateofBirthString'] != "" && json['dateofBirthString'] != 'undefined'){
			        		 $("#DOB").val(json['dateofBirthString']);
			        	 }else{
			        		 $("#DOB").val('');
			        	 }
			        
			        	 if(json['noticePeriod'] != null && json['noticePeriod'] != "" && json['noticePeriod'] != 'undefined'){
			        		 $("select[name=NoticePeriod]").val(json['noticePeriod']);
			        	 }else{
			        		 $("select[name=NoticePeriod]").val('');
			        	 }
			        	 
			        	 if(json['experienceYears'] != null && json['experienceYears'] != "" && json['experienceYears'] != 'undefined'){
			        		 $("select[name=WorkExperience]").val(json['experienceYears']);
			        		 if(json['experienceYears'] == "25+"){
			        			 $("#monthExp").attr("disabled", "disabled");
			        		 }else{
			        			 $("#monthExp").removeAttr("disabled");
			        		 }
			        	 }else{
			        		 $("select[name=WorkExperience]").val(0);
			        	 }
			        	 
			        	 if(json['experienceMonths'] != null && json['experienceMonths'] != "" && json['experienceMonths'] != 'undefined'){
			        		 $("select[name=monthExp]").val(json['experienceMonths']);
			        	 }else{
			        		 $("select[name=monthExp]").val(0);
			        	 }
			        	 
			        	 if(json['expectedRate'] != null && json['expectedRate'] != "" && json['expectedRate'] != 'undefined'){
			        		 $("input[name=ExpectedCTC]").val(json['expectedRate']);
			        	 }else{
			        		 $("input[name=ExpectedCTC]").val(''); 
			        	 }
			        	 
			        	 
			        	 if(json['rateType'] != null && json['rateType'] != "" && json['rateType'] != 'undefined'){
			        		 $("select[name=expectedCTCRatePer]").val(json['rateType']);
			        		 validateExpectedCTCRatePer();
			        	 }else{
			        		 $("select[name=expectedCTCRatePer]").val('');
			        		 validateExpectedCTCRatePer();
			        	 }
			        	 
			        	 if(json['expectedRateCurrencey'] != null && json['expectedRateCurrencey'] != "" && json['expectedRateCurrencey'] != 'undefined'){
			        		 $("select[name=expectedCTCRateType]").val(json['expectedRateCurrencey']);
			        	 }
			        	 
			        	 if(json['currentRate'] != null && json['currentRate'] != "" && json['currentRate'] != 'undefined'){
			        		 $("input[name=CurrentCTC]").val(json['currentRate']);
			        		 validateCurrentCTC();
			        	 }else{
			        		 $("input[name=CurrentCTC]").val(''); 
			        		 validateCurrentCTC();
			        	 }
			        	 
			        	 if(json['currentRateType'] != null && json['currentRateType'] != "" && json['currentRateType'] != 'undefined'){
			        		 $("select[name=currentCTCRatePer]").val(json['currentRateType']);
			        		 validateExpectedCTC();
			        		 validateCurrentCTCRatePer();
			        	 }else{
			        		 $("select[name=currentCTCRatePer]").val(''); 
			        		 validateExpectedCTC();
			        		 validateCurrentCTCRatePer();
			        	 }
			        	 
			        	 if(json['currentRateCurrency'] != null && json['currentRateCurrency'] != "" && json['currentRateCurrency'] != 'undefined'){
			        		 $("select[name=currentCTCRateType]").val(json['currentRateCurrency']);
			        	 }
			    	}else if (data.indexOf('Sign In') !== -1) {
			    		location.replace(sourceleadUrl);
			    	} else {
			    		showError("Operation failed, please try again");
			    	}
		        },
		    });
	 }else{
		 $("input[name=FirstName]").val('');
    	 $("input[name=LastName]").val('');
    	 $("input[name=EmailId]").val('');
    	 $("input[name=EmailId]").removeAttr("readonly");
    	 
    	 
    	 $("input[name=PhoneNumber]").val('');
    	 $("input[name=Designation]").val('');
    	 $("select[name=country]").val('');
    	 $("select[name=state]").val('');
    	 $("select[name=city]").val('');
    	 $("select[name=NoticePeriod]").val('');
    	 $("select[name=WorkExperience]").val('');
    	 $("input[name=ExpectedCTC]").val('');
    	 $("input[name=CurrentCTC]").val('');
    	 $("input[name=Education]").val('');
	 }
}
/// jobs or requirement related function
function isCoolingOffAlert(){
	var reqCoolingOffPeriod = document.getElementById("reqCoolingOffPeriod").value;
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Applicant Cooling-Off period "+reqCoolingOffPeriod+" not completed</p>";
	result = result + ' <div id="reasonForSpocDiv"/>';
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Proceed' onClick='proceedApplicant();'><input class='u_can_color' type='button' value='Discard' onClick='discordApplicant();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
/// jobs or requirement related function
function getCandidatesList(requirementId,candidateType) {
	 $('#sjfb-fields').find('p.jquery_form_error_message').remove();
	 $('#uploadFileGroup').find('p.jquery_form_error_message').remove();
	 $('#uploadFileGroup1').find('p.jquery_form_error_message').remove();
	 
	 $("input[name=AlternateNumber]").val(''); 
	 $("input[name=DOB]").val('');
	 $("input[name='Current/LastCompany']").val('');
	 $("select[name=WorkExperience]").val(0);
	 $("select[name=monthExp]").val(0);
	 if($("#needApplicantPermit").is(':checked')){
		 document.getElementById("needApplicantPermit").checked = false;
		 $("#needApplicantPermit").val('N');
	 }
	 
	 $('#candidateId').removeAttr('data-validation-optional');
	 $("#candidateId").attr("data-validation", "required");
	 
	// var sourceleadUrl ='${sourceleadUrl}';
	 if(candidateType != null && candidateType != "" && candidateType != 'undefined' && candidateType != 'New' && candidateType != 'parseCandidate'){
		 $("#selectCandidateDiv").css("display","block");
		
		 var skillsDiv = document.getElementById('skillsDiv'); 
		 if(skillsDiv != null){
			 document.getElementById("skillsDiv").style.display="none";
			 $("#candidateSkills").val('').trigger("chosen:updated");
			 
			 document.getElementById("primarySkillDiv").style.display="none";
			 $("#primarySkill").val('');
		 }
		 
		 var employmentTypeDiv = document.getElementById('employmentTypeDiv');
		 if(employmentTypeDiv != null){
			 document.getElementById("employmentTypeDiv").style.display="none";
			 $("#designationType").removeAttr("data-validation");
		     $('#designationType').attr("data-validation-optional","true");
		     $("#designationType").empty();
		 } 
		 
		 
		 if(requirementId != null && requirementId != "" && requirementId != 'undefined'){
			
			 $.ajax({
			    	type: "POST",
			        url: sourceleadUrl+"/getCandidatesList?=&requirementId="+requirementId, 
			        success: function(response) {
			        	if (response.response === "AWKWARD") {
			        	    location.href = "/errorPage";
			        	} else if (response.response === "SUCCESS") {
			        		var json = response.data;
				        	var output = "";
				        	output = output + "<option value=''>-- Select Candidate --</option>"; 
				        	for (var i=0; i<json.length; i++) {
					        		var opt = json[i];
					        		var candidateType = "";
					        		if(opt.isAssociate == 'Y'){
					        			candidateType = 'Associate';
					        		}else{
					        			candidateType = 'Candidate';
					        		}
					        		output = output + "<option value='" +opt.candId+ "'>"+opt.candidateName+" ("+candidateType+")</option>";
							}
				        	$("#candidateId").empty();
					        $("#candidateId").append(output);
					        $('#candidateId').trigger("chosen:updated");
					        getCandidateResumes();
			        	} else if (response.indexOf('Sign In') !== -1) {
			        		location.replace(sourceleadUrl);
			        	} else {
			        	    showError("Operation failed, please try again");
			        	}
			        },
			    }); 
		 }else{
			 var output = "";
	     	 output = output + "<option value=''>-- Select Candidate --</option>"; 
	     	 $("#candidateId").empty();
		     $("#candidateId").append(output);
		     $('#candidateId').trigger("chosen:updated");
		 }
	 }else{
		 var output = "";
    	 $("#candidateId").empty();
	     $("#candidateId").append(output);
	     $('#candidateId').trigger("chosen:updated");
	     $("#selectCandidateDiv").css("display","none");
	     
	     $("#candidateId").removeAttr("data-validation");
	     $('#candidateId').attr("data-validation-optional","true"); 
	     
	     
	     if(candidateType != null && candidateType != "" && candidateType != 'undefined' && candidateType == 'parseCandidate'){
	    	 document.getElementById("browseDiv").style.display="";
	    	 $('#uploadFile').removeAttr('data-validation-optional');
			 $('#uploadFile').attr("data-validation","required validatedoc "); 
			 
			 document.getElementById("newResumeDiv").style.display="none";
			 document.getElementById("skillsDiv").style.display="";
			 document.getElementById("primarySkillDiv").style.display="";
			 
			 document.getElementById("employmentTypeDiv").style.display="";
			 $("#designationType").removeAttr("data-validation-optional");
		     $('#designationType').attr("data-validation","required"); 
			
		     $("#candidateSkills").val('').trigger("chosen:updated");
			 $("#primarySkill").val('');
	     }else if(candidateType != null && candidateType != "" && candidateType != 'undefined' && candidateType == 'New'){
	    	 document.getElementById("browseDiv").style.display="none";
	    	 $('#uploadFile').removeAttr('data-validation');
			 $('#uploadFile').attr("data-validation-optional","true"); 
	    	 
	    	 document.getElementById("newResumeDiv").style.display="";
	    	 document.getElementById("skillsDiv").style.display="";
	    	 document.getElementById("primarySkillDiv").style.display="";
	    	 
	     	 document.getElementById("employmentTypeDiv").style.display="";
	    	 $("#designationType").removeAttr("data-validation-optional");
		     $('#designationType').attr("data-validation","required"); 
	    	
		     $("#candidateSkills").val('').trigger("chosen:updated");
			 $("#primarySkill").val('');
			 
			
	     }
	     document.getElementById("resumesDiv").style.display="none";
		 document.getElementById("orConditionDiv").style.display="none";
		
	 }
	 
	 $("input[name=FirstName]").val('');
	 $("input[name=LastName]").val('');
	 $("input[name=EmailId]").val('');
	 $("input[name=EmailId]").removeAttr("readonly");
	 
	 
	 $("input[name=PhoneNumber]").val('');
	 $("input[name=Designation]").val('');
	 $("select[name=NoticePeriod]").val('');
	 $("select[name=WorkExperience]").val(0);
	 $("input[name=ExpectedCTC]").val('');
	 $("input[name=CurrentCTC]").val('');
	 $("input[name=Education]").val('');
	 
	 $("#newUploadFile").val('');
	 $("#uploadFile").val('');
	 
	 getCandidateStates();
	 getSubmissionCandidateCities();
	
}
/// jobs or requirement related function
function getAllSubmissionCandidateCountries(){
	$.ajax({
    	type: "POST",
    	dataType: "json",
    	 url: sourceleadUrl +"/countryAutoComplete?=",
        success: function(data) {
        	var json = JSON.parse(data);
        	$('#submissionCountry').empty();
        	$('#submissionCountry').append('<option value="">---Select Country---</option>');
            for(var i=0;i<json.length;i++){
            	if(json[i] == 'India'){
            		$('#submissionCountry').append('<option value="'+json[i]+'" selected>'+json[i]+'</option>');
            	}
            	else{
            		$('#submissionCountry').append('<option value="'+json[i]+'">'+json[i]+'</option>');
            	}
            	
            }
            $('#submissionCountry').trigger("chosen:updated");
            getCandidateStates();
        },
        error: function(e){
        }
    }); 
} 
/// jobs or requirement related function
function getCandidateStates(state){
	
	var countryName = $("#submissionCountry").val();
	if(countryName != null && countryName != "" && countryName !="undefined"){
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: sourceleadUrl+"/getStatesByCountry?=&countryName="+countryName, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#submissionState').empty();
	        	$('#submissionState').append('<option value="">---Select State---</option>');
	            for(var i=0;i<json.length;i++){
	            	if(state != null && state != 'undefined' && json[i] == state){
	            		$('#submissionState').append('<option value="'+state+'" selected>'+json[i]+'</option>');
	            	}
	            	else{
	            		$('#submissionState').append('<option value="'+json[i]+'">'+json[i]+'</option>');
	            	}
	            }
	            $('#submissionState').trigger("chosen:updated");
	            
	            $('#submissionCity').empty();
	            $('#submissionCity').trigger("chosen:updated");
	        },
	        error: function(e){
	        }
	    });
	}else{
		$('#submissionState').empty();
    	$('#submissionState').append('<option value="">---Select State---</option>');
    	$('#submissionState').trigger("chosen:updated");
    	$('#submissionCity').empty();
    	$('#submissionCity').append('<option value="">---Select City---</option>');
        $('#submissionCity').trigger("chosen:updated");
	}
}
/// jobs or requirement related function
function getSubmissionCandidateCities(city){
	
	var countryName = $("#submissionCountry").val();
	var stateName = $("#submissionState").val();
	
	if(stateName == null || stateName == "" || stateName =="undefined"){
		stateName = document.getElementById("state");
	}
	
	if(countryName != null && countryName != "" && countryName !="undefined" && stateName != null && stateName != "" && stateName !="undefined" ){
		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: sourceleadUrl +"/getCitiesByStateAndCountry?=&countryName="+countryName+"&stateName="+stateName, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#submissionCity').empty();
	        	$('#submissionCity').append('<option value="">---Select City---</option>');
	            for(var i=0;i<json.length;i++){
	            	var selected = '';
	            	if(city != null && city != "" && city !="undefined" && json[i] == city){
	            		selected = 'selected';
	            	}
	            	$('#submissionCity').append('<option value="'+json[i]+'" '+selected+'>'+json[i]+'</option>');
	            }
	            $('#submissionCity').trigger("chosen:updated");
	            $("#loading").removeAttr("style"); 
	        },
	        error: function(e){
	        }
	    });
	}else{
		$('#submissionCity').empty();
    	$('#submissionCity').append('<option value="">---Select City---</option>');
    	$('#submissionCity').trigger("chosen:updated");
	}
}
/// jobs or requirement related function
function validateCurrentCTC(){
	 var currentCTC = $("input[name=CurrentCTC]").val();
	 if(currentCTC){
		 $("#currentCTCRatePer").removeAttr("data-validation-optional");
		 $("#currentCTCRatePer").attr("data-validation", "required");
	 }else{
		 $("#currentCTCRatePer").removeAttr("data-validation");
		 $("#currentCTCRatePer").attr("data-validation-optional", "true");
	 }
}
/// jobs or requirement related function
function validateExpectedCTC(){
	 var expectedCTC = $("input[name=ExpectedCTC]").val();
	 if(expectedCTC){
		 $("#expectedCTCRatePer").removeAttr("data-validation-optional");
		 $("#expectedCTCRatePer").attr("data-validation", "required");
	 }else{
		 $("#expectedCTCRatePer").removeAttr("data-validation");
		 $("#expectedCTCRatePer").attr("data-validation-optional", "true");
	 }
}
/// jobs or requirement related function
function validateExpectedCTCRatePer(){
	 var expectedCTCRatePer = document.getElementById("expectedCTCRatePer").value;
	 if(expectedCTCRatePer){
		 $("input[name=ExpectedCTC]").removeAttr("data-validation-optional");
		 $("input[name=ExpectedCTC]").attr("data-validation", "required validate_Space validate_float validate_length length1-8");
	 }else{
		 $("input[name=ExpectedCTC]").removeAttr("data-validation");
		 $("input[name=ExpectedCTC]").attr("data-validation-optional", "true");
		 $("input[name=ExpectedCTC]").attr("data-validation", "validate_Space validate_float validate_length length1-8");
	 }
}
/// jobs or requirement related function
function getEmployementType(){
	 var designationType = document.getElementById("designationType").value;
	 
	 if(designationType == 'C2C'){
		 $("#needApplicantPermit").attr("disabled", "disabled");
		 document.getElementById("needApplicantPermit").checked = false;
		 $("#needApplicantPermit").val('N');
	 }else{
		 $("#needApplicantPermit").removeAttr("disabled");
		
	 }
	 
	 if(designationType == 'C2C'){
		 document.getElementById("vendorsDiv").style.display="";
		 document.getElementById("vendorContactsDiv").style.display="";
		 document.getElementById("employmentOtherTypeDiv").style.display="none";
		
		 
		 $('#designationOtherType').removeAttr("data-validation"); 
		 $('#designationOtherType').attr("data-validation-optional", "true"); 
		 $('#designationOtherType').attr("data-validation","validate_Space validate_length length2-250"); 
		 
		 getVendorsByOrgLocationForAddApplicant();
	 }
	 else if(designationType == 'C2C-1099'){
		 document.getElementById("vendorsDiv").style.display="";
		 document.getElementById("vendorContactsDiv").style.display="";
		 document.getElementById("employmentOtherTypeDiv").style.display="none";
		
		 
		 $('#designationOtherType').removeAttr("data-validation"); 
		 $('#designationOtherType').attr("data-validation-optional", "true"); 
		 $('#designationOtherType').attr("data-validation","validate_Space validate_length length2-250"); 
		 
		 getVendorsByOrgLocationForAddApplicant();
	 }else if(designationType == 'Other'){
		 
		 document.getElementById("vendorsDiv").style.display="none";
		 document.getElementById("vendorContactsDiv").style.display="none";
		 document.getElementById("employmentOtherTypeDiv").style.display="";
		 
		 
		 $('#designationOtherType').removeAttr("data-validation-optional"); 
		 $('#designationOtherType').attr("data-validation","required validate_Space validate_length length2-250"); 
		 
	 }
	 else{
		 
		 
		 document.getElementById("employmentOtherTypeDiv").style.display="none";
		 document.getElementById("vendorsDiv").style.display="none";
		 document.getElementById("vendorContactsDiv").style.display="none";
		 
		 
		 $('#designationOtherType').removeAttr("data-validation"); 
		 $('#designationOtherType').attr("data-validation-optional", "true"); 
		 $('#designationOtherType').attr("data-validation","validate_Space validate_length length2-250"); 
		 
	 }
}
/// jobs or requirement related function
function getVendorsByOrgLocationForAddApplicant(){
	 
	 //var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getVendorsByOrgLocation?=", 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	var output = "";
	        	output = output + "<option value=''>--Select Vendor--</option>"; 
	        	for (var i=0; i<json.length; i++)
				{
		        		var opt = json[i];
		        		
		        		output = output + "<option value='" +opt.id+ "'>"+opt.orgName+"</option>";
				}
	        	$("#vendors").empty();
		        $("#vendors").append(output);
		        $('#vendors').trigger("chosen:updated"); 
		        
	        },
	    }); 
	 
}
/// jobs or requirement related function
function getCandidateSelectionType(){
	var output = "";
	output = output + "<option value=''>--Select Employment Type--</option>"; 
	output = output + "<option value='C2C'>C2C</option>"; 
	output = output + "<option value='C2C-1099'>C2C-1099</option>"; 
	output = output + "<option value='W-2'>W-2</option>"; 
	output = output + "<option value='C2H'>C2H</option>"; 
	output = output + "<option value='C2H-1099'>C2H-1099</option>"; 
	output = output + "<option value='Part Time'>Part Time</option>"; 
	output = output + "<option value='Full Time'>Full Time</option>"; 
	output = output + "<option value='Other'>Other</option>"; 
	$("#designationType").empty();
    $("#designationType").append(output);
}
/// jobs or requirement related function
function getCandidateResumes() {
	 $("#save_button").removeAttr("disabled");
	 $('.jquery_form_error_message').remove();
	 var candidateId = document.getElementById("candidateId").value
	// var sourceleadUrl ='http://localhost:8080/app';
	 if(candidateId != null && candidateId != "" && candidateId != 'undefined'){
		
		 document.getElementById("resumesDiv").style.display="";
		 document.getElementById("browseDiv").style.display="none";
		 document.getElementById("orConditionDiv").style.display="none";
		 $("#uploadFile").removeAttr("data-validation");
		 $("#uploadFile").val('');
		 $('.jquery_form_error_message').remove();
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getCandidateResumes?=&candidateId="+candidateId, 
		        success: function(data) {
		        	var json = data.data;
		        	if (data.response === "FAILED") {
			    		showError(data.responseMessage);
			    	} else if (data.response === "AWKWARD") {
			    		location.href = "/errorPage";
			    	} else if (data.response === "SUCCESS") {
			        	if(json!=null){
			        	var output = "";
			        	
			        	if(json.length == 1){
			        		var opt = json[0];
			        		if(opt.title != null && opt.title != 'undefined' && opt.title != ""){
			        			output = output + "<option value='" +opt.id+ "'>"+opt.title+"</option>";
			            	}else{
			            		output = output + "<option value='" +opt.id+ "'>"+opt.fileName+"</option>";
			            	}
			        	}else{
			        		output = output + "<option value=''>-- Select Resume --</option>"; 
				        	for (var i=0; i<json.length; i++)
							{
					        		var opt = json[i];
					        		if(opt.title != null && opt.title != 'undefined' && opt.title != ""){
					        			output = output + "<option value='" +opt.id+ "'>"+opt.title+"</option>";
					            	}else{
					            		output = output + "<option value='" +opt.id+ "'>"+opt.fileName+"</option>";
					            	}
							}
			        	}
			        	
			        	
			        	$("#candidateResumesList").empty();
				        $("#candidateResumesList").append(output);
				        
				        document.getElementById("newResumeDiv").style.display="none";
				        document.getElementById("skillsDiv").style.display="none";
				        document.getElementById("primarySkillDiv").style.display="none";
				        
				       /*  document.getElementById("employmentTypeDiv").style.display="none";
					    $('#designationType').removeAttr("data-validation");
					    $("#designationType").attr("data-validation-optional","true");
					    $("#designationType").empty(); */
				        
		        		
		        	}else{
		        		
		        		
		        		 document.getElementById("resumesDiv").style.display="none";
		        		 document.getElementById("browseDiv").style.display="none";
		        		 document.getElementById("orConditionDiv").style.display="none";
		        		
		        		 document.getElementById("newResumeDiv").style.display="";
		        		 
		        		 
		        		 
		        		 $('#uploadFile').removeAttr('data-validation');
		        		 $('#uploadFile').attr("data-validation-optional","true"); 
		        	}
			    	}else if (data.indexOf('Sign In') !== -1) {
			    		location.replace(sourceleadUrl);
			    	} else {
			    		showError("Operation failed, please try again");
			    	}
		        	
		        },
		    });
	 }else{
		 var output = "";
    	 output = output + "<option value=''>-- Select Resume --</option>"; 
    	 $("#candidateResumesList").empty();
		 document.getElementById("resumesDiv").style.display="none";
		 document.getElementById("browseDiv").style.display="none";
		 document.getElementById("orConditionDiv").style.display="none";
		 $('#uploadFile').removeAttr('data-validation');
		 $('#uploadFile').attr("data-validation-optional","true"); 
	     $("#candidateResumesList").append(output);
	 }
	
}
/// jobs or requirement related function
function getAddApplicantVendorContactList(){
	 var orgRelationID = $('#vendors').val();
	// var sourceleadUrl ='${sourceleadUrl}';
		 $.ajax({
			    type: "POST",
		        url: sourceleadUrl+"/getOrgSpocsList?=&orgRelationId="+orgRelationID+"&type=Vendor",  
		        success: function(json) {
		        	//var json = JSON.parse(data);
		        	$('#vendorContact').empty();
		        	$('#vendorContact').append('<option value="">--Select Vendor contact--</option>');
		             for(var i=0;i<json.data.length;i++){
		            	 var opt = json.data[i];
		            	 $('#vendorContact').append('<option value='+opt.id+' >'+opt.spocName+'</option>');
		            } 
		            $('#vendorContact').trigger("chosen:updated");
		        }
		 }); 
}
// jobs end 
//on boarding canidate list starts here
function onboardingCandidatesList() {
	// var sourceleadUrl ='${sourceleadUrl}';
	$("#onboardingLoading")
			.attr(
					'style',
					'position:absolute; width:100%; height:150px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	// var userId = '${SourceLeadWeb_userId}';
	// var loginUserRole = '${SourceLeadWeb_loginUserRole}';
	// var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';
	if (locationList.match("yyyy-MM-dd")) {
		onBoardListDate = 'YYYY-MM-DD';
	}
	if (locationList.match("dd-MM-yyyy")) {
		onBoardListDate = 'DD-MM-YYYY';
	}
	if (locationList.match("MM-dd-yyyy")) {
		onBoardListDate = 'MM-DD-YYYY';
	}
	if (locationList.match("yyyy-dd-MM")) {
		onBoardListDate = 'YYYY-DD-MM';
	}
	$
			.ajax({
				type : "POST",
				url : sourceleadUrl + "/getOnboardingCandidatesList",
				success : function(response) {
					if (response.response === "AWKWARD") {
						location.href = "/errorPage";
					} else if (response.response === "SUCCESS") {
						$("#onboardingLoading").removeAttr("style");
						$("#onboardingCandidatesList").empty();
						var result = "";
						result = result
								+ "<table id='onboardingPagination' class='display' cellspacing='0' width='99.9%' >";
						result = result + "<thead>";
						result = result + "  <tr>";
						result = result + "    <th>Candidate Name</th>";
						result = result + "    <th>Email</th>";
						result = result + "    <th>Title</th>";
						result = result
								+ "    <th>DOJ<br>(<small class='custom-small'>"
								+ onBoardListDate + "</small>)</th>";
						result = result + "    <th>Rate</th>";
						result = result + "    <th>Position Type</th>";
						result = result + "    <th>On-board Status</th>";
						'<sec:authorize access="hasRole(\'ROLE_CREATE_ON_BOARD_USER\')">'
						result = result + "<th>Actions</th>";
						'</sec:authorize>';
						result = result + "  </tr>";
						result = result + "</thead>";
						result = result + "<tbody>";

						var json = JSON.parse(response.object);

						for (var i = 0; i < json.length; i++) {
							var opt = json[i];
							result = result + "<tr>";
							result = result
									+ "<td><div class='tbldata-dotdot-sm'><span title='"
									+ opt.candidateName + "'>"
									+ opt.candidateName + "</span></div></td>";
							result = result + "<td><div><span title='"
									+ opt.candidateEmail + "'>"
									+ opt.candidateEmail + "</span></div></td>";
							if (opt.reqManagerId == userId
									|| opt.reqReleaseAllJobUserStatus == "Y"
									|| loginUserRole == 'ROLE_ADMIN') {
								result = result
										+ "<td><div class='tbldata-dotdot-sm'> <a href='"
										+ sourceleadUrl
										+ "/requirementSummary?requirementUUID="
										+ opt.requirementUUID
										+ "'><span title='"
										+ opt.requirementName + "'>"
										+ opt.requirementName
										+ "</span></a></div></td>";
							} else {
								result = result
										+ "<td><div class='tbldata-dotdot-sm'><span title='"
										+ opt.requirementName + "'>"
										+ opt.requirementName
										+ "</span></div></td>";
							}
							result = result + "<td><div><span>"
									+ opt.offerJoinDate + "</span></div></td>";

							if (opt.rate != null && opt.rate != ''
									&& opt.rate != 'undefined') {
								result = result + "<td><div><span title='"
										+ opt.paymentType + " " + opt.rate
										+ " / " + opt.rateType + "'>"
										+ opt.paymentType + " " + opt.rate
										+ " / " + opt.rateType
										+ "</span></div></td>";
							} else {
								result = result
										+ "<td><div><span>-----</span></div></td>";
							}

							if (opt.positionType != null
									&& opt.positionType != ''
									&& opt.positionType != 'undefined') {
								result = result + "<td><div><span title='"
										+ opt.positionType + "'>"
										+ opt.positionType
										+ "</span></div></td>";
							} else {
								result = result
										+ "<td><div><span>-----</span></div></td>";
							}

							if (opt.onboardStatus != null
									&& opt.onboardStatus != ''
									&& opt.onboardStatus != 'undefined') {
								if (opt.onboardStatus == "Finished") {
									result = result
											+ "<td><div><span title='Sent'>Sent</span></div></td>";
								} else {
									result = result + "<td><div><span title='"
											+ opt.onboardStatus + "'>"
											+ opt.onboardStatus
											+ "</span></div></td>";
								}

							} else {
								result = result
										+ "<td><div><span>Ready to onboard</span></div></td>";
							}

							'<sec:authorize access="hasRole(\'ROLE_CREATE_ON_BOARD_USER\')">'
							if (opt.onboardUUId != null
									&& opt.onboardUUId != ''
									&& opt.onboardUUId != 'undefined') {
								if (opt.onboardStatus == 'Draft') {
									result = result
											+ "<td><div class='tbldata-dotdot-sm'> <a href='"
											+ sourceleadUrl
											+ "/viewOnboardCandidateDetails?onboardUUID="
											+ opt.onboardUUId
											+ "'><span class='text-success'>View Onboard</span></a></div></td>";
								} else {
									result = result
											+ "<td><div class='tbldata-dotdot-sm'> <a href='"
											+ sourceleadUrl
											+ "/viewOnboardCandidateSummary?onboardUUID="
											+ opt.onboardUUId
											+ "'> <span class='text-success'>View Onboard</span></i></a> ";
									if (opt.onboardStatus == 'Submitted') {
										result = result
												+ '<span>&nbsp;&nbsp;|&nbsp;&nbsp;<a><i class="fa fa-check text-success" title="Accept" onclick="acceptOnboardingCandiate(\''
												+ opt.onboardUUId
												+ '\');"></i></a></span>&nbsp;&nbsp;&nbsp;<span><a><i class="fa fa-close text-danger" title="Reject" onclick="rejectedReason(\''
												+ opt.onboardUUId + '\',\''
												+ opt.candidateName + '\',\''
												+ opt.candidateEmail
												+ '\');"></i></a></span>';
									}

								}
								result = result + "</div></td>";

							} else {
								result = result
										+ "<td><div class='tbldata-dotdot-sm'><a href='"
										+ sourceleadUrl
										+ "/candidateOnBoardingDetails?candidateUUId="
										+ opt.candidateUUID
										+ "&offerUUID="
										+ opt.offerUUID
										+ "'><span class='text-success'>Onboard</span></a></div></td>";
							}
							'</sec:authorize>';

							result = result + "</tr>";
							result = result + "</tr>";
						}
						result = result + " </tbody>";
						result = result + "</table>";
						$("#onboardingLoading").removeAttr("style");
						$("#onboardingCandidatesList").append(result);

						onboardingPagenation();
					} else if (response.indexOf('Sign In') !== -1) {
						location.replace(sourceleadUrl);
					} else {
						showError("Operation failed, please try again");
					}
				},
			});
}
function acceptOnboardingCandiate(onboardCandidateUUID) {
	// var sourceleadUrl ='${sourceleadUrl}';
	$.ajax({
		type : "POST",
		url : sourceleadUrl + "/acceptOnboardingCandiate?onboardCandidateUUID="
				+ onboardCandidateUUID,
		success : function(response) {
			if (response.response === "AWKWARD") {
				location.href = "/errorPage";
			} else if (response.response === "SUCCESS") {
				location.replace(sourceleadUrl + "/dashboard/locationId");
			} else if (response.indexOf('Sign In') !== -1) {
				location.replace(sourceleadUrl);
			} else {
				showError("Operation failed, please try again");
			}
		},
	});
}
function rejectedReason(onboardCandidateUUID, candidateName, candidateEmail) {

	var result = "";
	result = result + '	<div class="settPop">';
	result = result + '	<div id="loading"></div>';
	result = result
			+ '<form id="rejectedReason_form" name="rejectedReason_form" method="post">';
	result = result
			+ '<input type="hidden" name="onboardCandidateUUID" value="'
			+ onboardCandidateUUID + '">';
	result = result
			+ '	<h3>Onboard Rejected Reason<i class="fa fa-times" onclick="cancelRejectedReason();"></i></h3>  ';
	result = result + '	<div class="settPopInn"> ';

	result = result + '		<div class="clearfix">  ';
	result = result + '		<div class="col-md-12">  ';
	result = result
			+ '		  <label class="col-md-2 paddingLeft" for="Candidate Name">Name :</label> ';
	result = result + '		  <label class="col-md-8 paddingRight">'
			+ candidateName + '</label> ';
	result = result + '		</div>  ';

	result = result + '		<div class="col-md-12">  ';
	result = result
			+ '		  <label class="col-md-2 paddingLeft" for="Email">Email :</label> ';
	result = result + '		  <label class="col-md-8 paddingRight">'
			+ candidateEmail + '</label> ';
	result = result + '		</div>  ';

	result = result
			+ '		  <label class="col-md-12 mt10" for="Project Id">Reason</label> ';
	result = result + '		  <div class="col-md-12 taskdesp mt10">   ';
	result = result
			+ '			<textarea class="form-control" name="rejectedReason" id="rejectedReason" field-name="Reason" data-validation="required  validate_length length2-500" rows="2"></textarea>';
	result = result + '			<span class="hidden"></span> </div>  ';
	result = result + '		</div>';
	result = result + '		<div class="col-md-12 mt5 clearfix"> ';
	result = result + '		<div class="pull-right">  ';
	result = result
			+ '		  <input type="submit" value="Save" id="submit_rejectedReason"  class="btn btn-primary" >';
	result = result
			+ '		  <input type="button" value="Cancel" class="btn btn-default ml10" onclick="cancelRejectedReason();">';
	result = result + '		</div>';
	result = result + '		</div>';
	result = result + '		<div class="clearfix"></div>   ';
	result = result + '	</div>  ';
	result = result + '</form>';
	result = result + '	</div>';
	$("#rejectedReasonDiv").empty();
	$("#rejectedReasonDiv").append(result);

	$(".settPop").slideDown(200);
	$(".vendor_wrap_mask").fadeIn(200);

}
function onboardingPagenation() {
	$('#onboardingPagination').DataTable({
		"scrollCollapse" : false,
		"lengthMenu" : [ [ 5, 15, 20, -1 ], [ 2, 15, 20, "All" ] ],
		"bLengthChange" : false,
		"bDestroy" : true,
		"ordering" : true,
		"oLanguage" : {
			"sSearch" : "",
			"sZeroRecords" : "",
			"sEmptyTable" : ""
		}
	});
}// On boarding candidates list END.
//Project Requests starts here
function getProjectResourceReqestsListCount() {
	$.ajax({
		type : "GET",
		url : sourceleadUrl + "/getProjectResourceRequestsCounts",
		success : function(json) {
			$("#waitingCount").html(json.waitingCount);
			$("#approvedCount").html(json.approvedCount);
			$("#rejectedCount").html(json.rejectedCount);
			$("#onholdCount").html(json.onholdCount);
		}

	});
} // Project Requests end here.
// Resource request list starts here
function resourceRequestList(requestType){
	  $('#newResourceRequestTabDiv').hide();
	  $('#assocaiteRequestsTabDiv').show();
	  $("#budgetTabDiv").hide();
	  $("#associateDiv").addClass("active");
	  $("#newResourceDiv").removeClass("active");
	  $("#budgetDiv").removeClass("active");
	  
	    var result = "";
	 	result = result +" <div class='remove_search'>";
	 	result = result +" <input type='text' class='repSearchBar' placeholder='Associate search' id='requestAssociatesTableSearch'/>";
	 	result = result +"</div>";
	 	
	    result = result +'<div class="clearfix popupFilter">';
		result = result +'<div class="clearfix popupFilter datatable_filter">'; 
	 	
		result = result +'<table id="requestAssociates" class="display" cellspacing="0" width="100%"  >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result +'<th>Request Id</th>';
		result = result +'<th>Project</th>';
		result = result +'<th>Associate</th>';
		result = result +'<th>Initiator</th>';
		result = result +'<th>Approver</th>';
		result = result +'<th>Consulting</th>';
		result = result +'<th>Actions</th>';
		result = result + '</tr>';
		result = result + '</thead>	';
	    result = result +"</table>";
	    
	    result = result +'</div>'; 
  	result = result +'<div class="clearfix">';
  	result = result +'</div>'; 
  	result = result +'</div>';
  	 
	    $("#resourceRequestList").empty();
	    $("#resourceRequestList").append(result);
	    requestAssociatesDataTableList(requestType);
}
function requestAssociatesDataTableList(requestType){
	// var sourceleadUrl ='${sourceleadUrl}';
	 //var approverUserId = '${SourceLeadWeb_user.id}';
	 var orgLocationId =  locationId; // replcaced with dashboard varia
	 
	 var searchValue = $("#requestAssociatesTableSearch").val();
	 searchValue = searchValue ? searchValue : "";
	 var url = sourceleadUrl+"/getProjectPlanningResourceRequestList?searchValue="+searchValue+"&resourceStatus="+requestType;
	 resourceRequestPagination(url,requestType,sourceleadUrl);
	 
	   if(requestType == 'Waiting For Approval'){
	    	 $("#waiting").addClass("activeIcon");
	    	 $("#requestApproved").removeClass("activeIcon");
	    	 $("#requestRejected").removeClass("activeIcon");
	    	 $("#requestOnhold").removeClass("activeIcon");
	    }
	   
	    else if(requestType == 'Approved'){
	    	 $("#requestApproved").addClass("activeIcon");
	    	 $("#waiting").removeClass("activeIcon");
	    	 $("#requestRejected").removeClass("activeIcon");
	    	 $("#requestOnhold").removeClass("activeIcon");
	    }
	    
	    else if(requestType == 'Rejected'){
	    	 $("#requestRejected").addClass("activeIcon");
	    	 $("#waiting").removeClass("active");
	    	 $("#requestApproved").removeClass("activeIcon");
	    	 $("#requestOnhold").removeClass("activeIcon");
	    }
	    
	    else if(requestType == 'On hold'){
	    	 $("#requestOnhold").addClass("activeIcon");
	    	 $("#waiting").removeClass("active");
	    	 $("#requestApproved").removeClass("activeIcon");
	    	 $("#requestRejected").removeClass("activeIcon");
	    	
	    }
	    
}
function resourceRequestPagination(url,requestType,sourceleadUrl){
	var colIndex = 0;
    $('#requestAssociates').DataTable( { 
	  "processing": true,
      "serverSide": true,
      "searching": false,
      "ajax": $.fn.dataTable.pipeline( {
          url: url,
          method: 'GET',
          pages: 2
        }),
        "columns": [
        	{ "data": "projectPlanningRequestId" },
            { "data": "projectName" },
            { "data": "candidateName"},
            { "data": "requestedUser"},
            { "data": "approverName"},
            { "data": "consultingType"},
            { "data": ""}
           
        ],
        "columnDefs": [ {
    	  	"targets": colIndex++,
    	  	"data": "projectPlanningRequestId",
    	  	"render": function(data, type, opt, meta) {
    	  		 var result = "";
    	  		 var flag = false;
    	  		 result = result + '<td><div  class="tbldata-dotdot-vsm"><a href="'+sourceleadUrl+'/requestedAssociatesList?requestUUID='+opt.projectPlanningRequestUUID+'">'+opt.projectPlanningRequestId+'</a></div></td>';
                 return result;
    	  	}
         },{
	    	  	"targets": colIndex++,
	    	  	"data": "projectName",
	    	  	"render": function(data, type, opt, meta) {
	    	  		  var result = "";
	    	  		  if(opt.projectClientUUID != null && opt.projectClientUUID != '' && opt.projectClientUUID != 'undefined'){
	    	  			result = result + '<td><div  class="tbldata-dotdot-sm"><a href="'+sourceleadUrl+'/projectSummary?projectUUID='+opt.projectUUID+'&projectClientUUID='+opt.projectClientUUID+'&tabType=resources" title="'+opt.projectName+'">'+opt.projectName+'</a></div></td>';
	    	  		  }else{
	    	  			result = result + '<td><div  class="tbldata-dotdot-sm"><a href="javascript:void(0)" onclick="#">'+opt.projectName+'</a></div></td>';
	    	  			/* result = result + '<td><div  class="tbldata-dotdot-sm"><a href="javascript:void(0)" onclick="openProjectDetailsPopup(\''+opt.projectUUID+'\','+opt.requestedOrgLocationId+');">'+opt.projectName+'</a></div></td>'; */
	    	  		  }
	    	  		  
		             return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "candidateName",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  result = result + '<td><div  class="tbldata-dotdot-sm"><a href="'+sourceleadUrl+'/userSummary/'+opt.userCode+'" title="'+opt.candidateName+'">'+opt.candidateName+'</a></div></td>';
	              return result;
              		
              }
	      
	          },{
		    	  "targets": colIndex++,
	              "data": "requestedUser",
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            		result = result + '<td><div class="tblminwidth-75"><span title="'+opt.requestedUser+'">'+opt.requestedUser+'</span></div></td>';
	            		return result;
	              }
		     },{
		    	  "targets": colIndex++,
	              "data": "approverName",
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            		result = result + '<td><div class="tblminwidth-75"><span title="'+opt.approverName+'">'+opt.approverName+'</span></div></td>';
	            		return result;
	              }
		     },{
		    	  "targets": colIndex++,
	              "data": "consultingType",
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            		result = result + '<td><div class="tblminwidth-75"><span title="'+opt.consultingType+'">'+opt.consultingType+'</span></div></td>';
	            		return result;
	              }
		     } ,{
		    	  "targets": colIndex++,
	              "data": "",
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    
	            	    result = result + '<td id="action'+opt.requestUUID+'"><div class="tblminwidth-100">';
			    		if(opt.resourceStatus == 'Waiting For Approval' || opt.resourceStatus == 'On hold'){
			    			
			    			if(opt.resourceStatus == 'On hold'){
			    			  result = result +"<i class='glyphicon glyphicon-comment edit-reason'><div class='edit-status'><p>"+opt.rejectedReason+"</p></div></i>&nbsp;|&nbsp;";
			    			  result = result+" <br><span class='rejNam' title='"+opt.approvedUser+"'>By : "+opt.approvedUser+"</span>";
			    			}
			    			result = result + '<a href="javascript:void(0)" onclick="acceptAssociate(\''+opt.requestUUID+'\',\''+requestType+'\');"><i class="fa fa-check text-success" alt="Approve" title="Approve"></i></a>&nbsp;|&nbsp;';
				    		result = result + '<a href="javascript:void(0)" onclick="rejectAssociate(\''+opt.requestUUID+'\',\''+opt.candidateName+'\');"><i class="fa fa-times text-danger" alt="Reject" title="Reject"></i></a>';
				    		
				    		if(opt.resourceStatus == 'Waiting For Approval'){
				    			result = result + '&nbsp;|&nbsp;<a href="javascript:void(0)" onclick="onholdAssociateRequest(\''+opt.requestUUID+'\',\''+opt.requestedUser+'\',\''+requestType+'\');"><i class="fa fa-clock-o text-primary" alt="On hold" title="On hold"></i></a>';
				    		}
				    		
			    		}else{
			    			if(opt.resourceStatus == 'Rejected'){
			    				result = result + '<a href="javascript:void(0)" onclick="acceptAssociate(\''+opt.requestUUID+'\',\''+requestType+'\');"><i class="fa fa-check text-success" alt="Approve" title="Approve"></i></a>&nbsp;|&nbsp;';
			    				result = result + '<a href="javascript:void(0)" onclick="onholdAssociateRequest(\''+opt.requestUUID+'\',\''+opt.requestedUser+'\',\''+requestType+'\');"><i class="fa fa-clock-o text-primary" alt="On hold" title="On hold"></i></a>&nbsp;|&nbsp;';
			    				result = result +"<i class='glyphicon glyphicon-comment edit-reason'><div class='edit-status'><p>"+opt.rejectedReason+"</p></div></i>";
			    				result = result + '<b class="bg-red">'+opt.resourceStatus+'</b>';
			    				result = result+" <br><span class='rejNam' title='"+opt.approvedUser+"'>By : "+opt.approvedUser+"</span>";
			    			}else{
			    				result = result + '<b class="bg-green">'+opt.resourceStatus+'</b>';
			    				result = result+" <br><span class='rejNam' title='"+opt.approvedUser+"'>By : "+opt.approvedUser+"</span>";
			    			}
			    			
			    		}
			    		
			    		result = result + '</div></td>';
			    		return result;
	              }
		     } ],
		     
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"aaSorting": [[1,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": false,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
    } );
    $("#requestAssociates").wrap("<div class='clearfix require_wrap_table'></div>"); 
    $("input[type=search]").hide();
    $("#requestAssociatesTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   requestAssociatesDataTableList(requestType);
    	   }
    	});
}
function acceptAssociate(requestUUID,requestType){
	 //var userId = '${SourceLeadWeb_userId}';
    var orglocationId =  locationId; // replcaced with dashboard variable.
	// var sourceleadUrl ='${sourceleadUrl}';
	 $("#requestedAssociatesDivLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 $.ajax({
		    type : "POST",
	    	url: sourceleadUrl+"/acceptRequestedAssociate?requestUUID="+requestUUID+"&userId="+userId+"&orglocationId="+orglocationId, 
	        success: function(result) {
	        	$("#action"+requestUUID).html("<span>Accepted</span>");
	        	getProjectResourceReqestsListCount();
	        	resourceRequestList(requestType);
	        	$("#requestedAssociatesDivLoading").removeAttr("style"); 
	        },
	    });
}

function rejectAssociate(requestUUID,candidateName){
	$("#requestOnHoldReasonDiv").empty();
	var result = "";
	result = result + '	<div class="settPop">';
	result = result + '	<div id="loading"></div>';
	result = result + '<form id="rejectedReason_form" name="rejectedReason_form" method="post">';
	result = result + '<input type="hidden" name="requestUUID" value="'+requestUUID+'">';
	result = result + '	<h3>Associate Rejected Reason<i class="fa fa-times" onclick="cancelRejectedReason();"></i></h3>  ';
	result = result + '	<div class="settPopInn"> ';
	
	result = result + '		<div class="clearfix">  ';
	result = result + '		<div class="col-md-12">  ';
	result = result + '		  <label class="col-md-2 noPad" for="Candidate Name">Name :</label> ';
	result = result + '		  <label class="col-md-8 paddingRight">'+candidateName+'</label> ';
	result = result + '		</div>  ';

	result = result + '		  <label class="col-md-12 mt10" for="Project Id">Reason</label> ';
	result = result + '		  <div class="col-md-12 taskdesp">   ';
	result = result + '			<textarea class="form-control" name="rejectedReason" id="rejectedReason" field-name="Reason" data-validation="required  validate_length length2-500" rows="2"></textarea>';
	result = result + '			<span class="hidden"></span> </div>  ';
	result = result + '		</div>';
	result = result + '		<div class="col-md-12 mt5 clearfix"> ';
	result = result + '		<div class="pull-right">  ';
	result = result + '		  <input type="button" value="Save" id="submit_rejectedReason"  onclick="saveRejectAssociate(\''+requestUUID+'\');" class="btn btn-primary" >';
	result = result + '		  <input type="button" value="Cancel" class="btn btn-default ml10" onclick="cancelRejectedReason();">';
	result = result + '		</div>';
	result = result + '		</div>';
	result = result + '		<div class="clearfix"></div>   ';
	result = result + '	</div>  ';
	result = result + '</form>';
	result = result + '	</div>';
    $("#rejectedReasonDiv").empty();
    $("#rejectedReasonDiv").append(result);
    
    $(".settPop").slideDown(200);
	$(".vendor_wrap_mask").fadeIn(200);
}
function cancelRejectedReason(){
	$(".settPop").slideUp(200);
	$(".vendor_wrap_mask").fadeOut(200);
}
function planningRequestsPagination(){  
    $('#planningRequestsPaginationList').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[5, 15, 25, 30, -1], [5, 15, 25, 30, "All"]],
		"aaSorting": [[4,'desc']],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		"sZeroRecords": "", "sEmptyTable": ""
     	}
    });
    
    $('#planningRequestsList').wrap("<div class='overflow_Auto'></div>");
    
}

function onholdAssociateRequest(requestUUID,Initiator,requestType){
	var result = "";
	result = result + '	<div class="settPop">';
	result = result + '	<div id="loading"></div>';
	result = result + '<form id="onHold_form" name="onHold_form" method="post">';
	result = result + '<input type="hidden" name="requestUUID" value="'+requestUUID+'">';
	result = result + '	<h3>Associate Request On Hold Reason<i class="fa fa-times" onclick="cancelRequestOnholdReason();"></i></h3>  ';
	result = result + '	<div class="settPopInn"> ';
	
	result = result + '		<div class="clearfix">  ';
	result = result + '		<div class="col-md-12">  ';
	result = result + '		  <label class="col-md-2 noPad" for="Initiator Name">Initiator :</label> ';
	result = result + '		  <label class="col-md-8 paddingRight">'+Initiator+'</label> ';
	result = result + '		</div>  ';

	result = result + '		  <label class="col-md-12 mt10" for="Project Id">Reason</label> ';
	result = result + '		  <div class="col-md-12 taskdesp">   ';
	result = result + '			<textarea class="form-control" name="rejectedReason" id="rejectedReason" field-name="Reason" data-validation="required  validate_length length2-500" rows="2"></textarea>';
	result = result + '			<span class="hidden"></span> </div>  ';
	result = result + '		</div>';
	result = result + '		<div class="col-md-12 mt5 clearfix"> ';
	result = result + '		<div class="pull-right">  ';
	result = result + '		  <input type="button" value="Save" id="submit_onHold"  onclick="saveOnHoldRequest(\''+requestUUID+'\',\''+requestType+'\');" class="btn btn-primary" >';
	result = result + '		  <input type="button" value="Cancel" class="btn btn-default ml10" onclick="cancelRequestOnholdReason();">';
	result = result + '		</div>';
	result = result + '		</div>';
	result = result + '		<div class="clearfix"></div>   ';
	result = result + '	</div>  ';
	result = result + '</form>';
	result = result + '	</div>';
    $("#requestOnHoldReasonDiv").empty();
    $("#requestOnHoldReasonDiv").append(result);
    
    $(".settPop").slideDown(200);
	$(".vendor_wrap_mask").fadeIn(200);
	 
}
function saveOnHoldRequest(requestUUID,requestType){
	
	if ($('#onHold_form').validate(false, validationSettings)){ 
		  	
			var frm = $('#onHold_form').serializeFormJSON();
			var jsonData = JSON.stringify(frm);
		    var form = $('#onHold_form')[0];
		    var formData = new FormData(form);
		  	formData.append("json",jsonData)
		  	
	       // var sourceleadUrl ='${sourceleadUrl}';
	      //  var userId = '${SourceLeadWeb_userId}';
	        var orglocationId =  locationId; // replcaced with dashboard variable.
	        
	        $("#submit_onHold").attr("disabled", "disabled");
	        $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 	 	 $.ajax({
		 		    type : "POST",
		    	    data: formData,
		    	    contentType : 'application/json; charset=utf-8',
			        processData: false,
			        contentType:false, 
		    	    dataType : 'json',
			    	url: sourceleadUrl+"/onholdRequestedAssociate?requestUUID="+requestUUID+"&userId="+userId+"&orglocationId="+orglocationId,
			        success: function(result) {
			        	
			        	cancelRequestOnholdReason();
			        	$("#action"+requestUUID).html("<span>Onhold</span>")
			        	getProjectResourceReqestsListCount();
			        	resourceRequestList(requestType);
			        	$("#loading").removeAttr("style"); 
			        },
			    });  
	      return true; 
			
		}else{
			return false;
		  }
		
		  $('body').on('blur', '#onHold_form', function() {
		   		$("#onHold_form").showHelpOnFocus();
		   		$("#onHold_form").validateOnBlur(false, validationSettings);
		   	});
	}

function rejectAssociateRequest(requestUUID,Initiator){
	
	var result = "";
	result = result + '	<div class="settPop">';
	result = result + '	<div id="loading"></div>';
	result = result + '<form id="rejectedReason_form" name="rejectedReason_form" method="post">';
	result = result + '<input type="hidden" name="requestUUID" value="'+requestUUID+'">';
	result = result + '	<h3>Associate Request Rejected Reason<i class="fa fa-times" onclick="cancelRejectedReason();"></i></h3>  ';
	result = result + '	<div class="settPopInn"> ';
	
	result = result + '		<div class="clearfix">  ';
	result = result + '		<div class="col-md-12">  ';
	result = result + '		  <label class="col-md-2 noPad" for="Initiator Name">Initiator :</label> ';
	result = result + '		  <label class="col-md-8 paddingRight">'+Initiator+'</label> ';
	result = result + '		</div>  ';

	result = result + '		  <label class="col-md-12 mt10" for="Project Id">Reason</label> ';
	result = result + '		  <div class="col-md-12 taskdesp">   ';
	result = result + '			<textarea class="form-control" name="rejectedReason" id="rejectedReason" field-name="Reason" data-validation="required  validate_length length2-500" rows="2"></textarea>';
	result = result + '			<span class="hidden"></span> </div>  ';
	result = result + '		</div>';
	result = result + '		<div class="col-md-12 mt5 clearfix"> ';
	result = result + '		<div class="pull-right">  ';
	result = result + '		  <input type="button" value="Save" id="submit_rejectedReason"  onclick="saveRejectAssociateRequest(\''+requestUUID+'\');" class="btn btn-primary" >';
	result = result + '		  <input type="button" value="Cancel" class="btn btn-default ml10" onclick="cancelRequestRejectedReason();">';
	result = result + '		</div>';
	result = result + '		</div>';
	result = result + '		<div class="clearfix"></div>   ';
	result = result + '	</div>  ';
	result = result + '</form>';
	result = result + '	</div>';
    $("#requestRejectedReasonDiv").empty();
    $("#requestRejectedReasonDiv").append(result);
    
    $(".settPop").slideDown(200);
	$(".vendor_wrap_mask").fadeIn(200);
}
function cancelRequestRejectedReason(){
	$(".settPop").slideUp(200);
	$(".vendor_wrap_mask").fadeOut(200);
}
function saveRejectAssociateRequest(requestUUID){
	
	if ($('#rejectedReason_form').validate(false, validationSettings)){ 
	  	
		var frm = $('#rejectedReason_form').serializeFormJSON();
		var jsonData = JSON.stringify(frm);
		
	    var form = $('#rejectedReason_form')[0];
	    var formData = new FormData(form);
	  	formData.append("json",jsonData)
       // var sourceleadUrl ='${sourceleadUrl}';
       // var userId = '${SourceLeadWeb_userId}';
        
        var orglocationId = locationId // replaced with dashboard jps variable
        $("#submit_rejectedReason").attr("disabled", "disabled");
		
        $("#resourceRequestsDivLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
 	 	 $.ajax({
	 		    type : "POST",
	    	    data: formData,
	    	    contentType : 'application/json; charset=utf-8',
		        processData: false,
		        contentType:false, 
	    	    dataType : 'json',
		    	url: sourceleadUrl+"/rejectAssociatesRequest?userId="+userId+"&orglocationId="+orglocationId,
		        success: function(result) {
		        	cancelRejectedReason();
		        	location.replace(sourceleadUrl+"/dashboard/"+orglocationId);  
		        },
		    });  
      return true; 
		
	}else{
		return false;
	  }
	
	  $('body').on('blur', '#rejectedReason_form', function() {
	   		$("#rejectedReason_form").showHelpOnFocus();
	   		$("#rejectedReason_form").validateOnBlur(false, validationSettings);
	   	});
	
}
// end resource request list end here.
//requirementApproverList('SUBMITTED');
function requirementApproverList(statusType) {

	// var sourceleadUrl ='${sourceleadUrl}';

	// $("#requirementApproverList").attr('style','position:absolute;
	// width:100%; height:350px; background-color:rgba(255,255,255,0.8);
	// top:0px; left:0px;
	// z-index:100;background-image:url("resources/images/loading.gif");
	// background-position:center; background-repeat:no-repeat;
	// background-size:75px;');
	$
			.ajax({
				type : "GET",
				url : sourceleadUrl
						+ "/getApproverRequirementApproverListByOrgLocationIDAndUserId?statusType="
						+ statusType,
				success : function(data) {
					if (data.response === "FAILED") {
						showError(data.responseMessage);
					} else if (data.response === "AWKWARD") {
						location.href = "/errorPage";
					} else if (data.response === "SUCCESS") {
						var json = data.data;
						$("#requirementApproverList").removeAttr("style");
						$("#requirementApproverList").empty();
						var result = "";
						result = result
								+ '<div id="rejectRequirementAlert" ></div>  ';
						result = result
								+ "<table id='requirementApproverPagination' class='display' cellspacing='0' width='99.5%' >";
						result = result + "<thead>";
						result = result + "  <tr>";
						result = result + "    <th>ID-Title</th>";
						result = result + "    <th>Rate</th>";
						result = result + "    <th>Exp</th>";

						if (statusType == "APPROVED") {
							result = result + "    <th>Approved By</th>";
						} else if (statusType == "REJECTED") {
							result = result + "    <th>Rejected By</th>";
						} else {
							result = result + "    <th>Action</th>";
						}
						result = result + "  </tr>";
						result = result + "</thead>";
						result = result + "<tbody>";

						for (var i = 0; i < json.length; i++) {
							var opt = json[i];
							result = result + "<tr>";
							if (opt.jobId != null && opt.jobId != ''
									&& opt.jobId != 'undefined') {
								result = result
										+ "<td><div class='tbldata-dotdot-vsm'> <a href='"
										+ sourceleadUrl
										+ "/requirementSummary?requirementUUID="
										+ opt.requirementUUID
										+ "'><span title = '" + opt.jobId + "-"
										+ opt.jobTitle + "'>" + opt.jobId + "-"
										+ opt.jobTitle
										+ "</span></a></div></td>";
							} else {
								result = result
										+ "<td><div class='tbldata-dotdot-vsm'> <a href='"
										+ sourceleadUrl
										+ "/requirementSummary?requirementUUID="
										+ opt.requirementUUID
										+ "'><span title = '" + opt.jobTitle
										+ "'>" + opt.jobTitle
										+ "</span></a></div></td>";
							}
							if (opt.rate != null && opt.rate != ''
									&& opt.rate != 'undefined'
									&& opt.maxRate != null && opt.maxRate != ''
									&& opt.maxRate != 'undefined') {
								result = result + "<td><div><span title = '"
										+ opt.currencyCode + "-" + opt.rate
										+ "-" + opt.maxRate + "-"
										+ opt.rateType + "'>"
										+ opt.currencyCode + "&nbsp;"
										+ opt.rate + "-" + opt.maxRate
										+ "&nbsp;" + opt.rateType
										+ "</span></div></td>";
							} else if (opt.rate != null && opt.rate != ''
									&& opt.rate != 'undefined') {
								result = result + "<td><div><span title = '"
										+ opt.currencyCode + "-" + opt.rate
										+ "-" + opt.rateType + "'>"
										+ opt.currencyCode + "&nbsp;"
										+ opt.rate + "&nbsp;" + opt.rateType
										+ "</span></div></td>";
							} else {
								result = result + "<td><div>-----</div></td>";
							}

							var exp = '';

							if (opt.maxExp != null && opt.maxExp != ''
									&& opt.maxExp != 'undefined') {
								exp = opt.maxExp;
							}

							if (opt.minExp != null && opt.minExp != ''
									&& opt.minExp != 'undefined') {
								exp = opt.minExp;
							}
							if (opt.maxExp != null && opt.maxExp != ''
									&& opt.maxExp != 'undefined'
									&& opt.minExp != null && opt.minExp != ''
									&& opt.minExp != 'undefined') {
								exp = opt.minExp + " - " + opt.maxExp;
							}

							result = result + "<td><div><span title='" + exp
									+ "'>" + exp + "</span></div></td>";

							var APPROVED = "APPROVED";
							var REJECTED = "REJECTED";
							result = result + "<td>";
							if (opt.requirementSubmissionStatus != 'DRAFT'
									&& opt.requirementSubmissionStatus != 'OPEN'
									&& opt.requirementApproverHierarchyStatus != 'APPROVED'
									&& opt.requirementApproverHierarchyStatus != 'REJECTED'
									&& opt.requirementApproverHierarchyStatus != ''
									&& opt.requirementApproverHierarchyStatus != 'undefined'
									&& opt.requirementApproverHierarchyStatus != null) {

								result = result
										+ '<a class="paddingRight" href="javascript:void(0)" title="Accept"  onClick="updateReuqirementStatusAccept(\''
										+ APPROVED
										+ '\','
										+ opt.id
										+ ');"   ><i class="fa fa-check text-success"></i></a>';
								result = result
										+ '<a class="paddingLeft" href="javascript:void(0)" title="Reject"  onClick="rejectRequirementAlertMessage(\''
										+ REJECTED
										+ '\','
										+ opt.id
										+ ');"  ><i class="fa fa-close text-danger"></i></a>';

							} else if ((opt.requirementApproverHierarchyStatus == 'APPROVED' || opt.requirementSubmissionStatus == 'OPEN')
									|| (opt.requirementApproverHierarchyStatus != ''
											&& opt.requirementApproverHierarchyStatus != 'undefined' && opt.requirementApproverHierarchyStatus != null)) {
								result = result
										+ '<div  id="requirementStatus"  ><span title ="'
										+ opt.approvedBy + '">'
										+ opt.approvedBy + '</span></div>';
							} else if (opt.requirementSubmissionStatus != 'DRAFT'
									&& opt.requirementApproverHierarchyStatus == 'REJECTED'
									&& opt.requirementApproverHierarchyStatus != ''
									&& opt.requirementApproverHierarchyStatus != 'undefined'
									&& opt.requirementApproverHierarchyStatus != null) {
								result = result
										+ '<div  id="requirementStatus" ><span title = "'
										+ opt.approvedBy + '">'
										+ opt.approvedBy + '</span></div>';

							} else {
								result = result + "<div>-----</div>";
							}
							result = result + "</td>";
							result = result + "</tr>";
						}
						result = result + " </tbody>";
						result = result + "</table>";
						$("#requirementApproverList").append(result);
						requirementApprovalPagination();
					} else if (data.indexOf('Sign In') !== -1) {
						location.replace(sourceleadUrl);
					} else {
						showError("Operation failed, please try again");
					}
				},

			});
}

function requirementApprovalPagination() {
	$('#requirementApproverPagination').DataTable({
		"scrollCollapse" : false,
		"lengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"bLengthChange" : false,
		"bDestroy" : true,
		"ordering" : true,
		"oLanguage" : {
			"sSearch" : "",
			"sZeroRecords" : "",
			"sEmptyTable" : ""
		}
	});
}// requirementApproverList('SUBMITTED'); end here

function eduMsgFadeinFunction() {
	$("#selector4").fadeIn(200);
	$('#selector4').delay(2000).fadeOut('slow');
}

function updateReuqirementStatusAccept(status, requirementId) {
	// var sourceleadUrl = '${sourceleadUrl}';
	var requirementId = requirementId;
	var reason;
	if ($('#rejectReason').length) {
		reason = document.getElementById("rejectReason").value;
	} else {
		reason = '';
	}
	$.ajax({
		type : "POST",
		url : sourceleadUrl + "/requirementsAcceptOrReject?" + requirementId
				+ "&status=" + status + "&reason=" + reason,
		success : function(result) {
			if (result.response === "FAILED") {
				showError(result.responseMessage);
			} else if (result.response === "AWKWARD") {
				location.href = "/errorPage";
			} else if (result.response === "SUCCESS") {
				var data = result.object;
				if (data != null && data != 'undefined' && data != "") {
					requirementApproverList('SUBMITTED');
					if (status == 'APPROVED') {
						$("#selector4").removeAttr("style");
						$("#selector4").css({
							display : "block"
						});
						$("#eduMsgDiv").html("Approved");
						eduMsgFadeinFunction();
					}
				}
			} else if (result.indexOf('Sign In') !== -1) {
				location.replace(sourceleadUrl);
			} else {
				showError("Operation failed, please try again");
			}
		},
	});
}

function updateReuqirementStatus(status, requirementId) {
	var requirementId = requirementId;
	var reason = document.getElementById("rejectReason").value;
	if (reason.match(/^[^\s-](.*)*$/) != null) {
		$
				.ajax({
					type : "POST",
					url : sourceleadUrl + "/requirementsAcceptOrReject?"
							+ requirementId + "&status=" + status + "&reason="
							+ reason,
					success : function(result) {
						if (result.response === "FAILED") {
							showError(result.responseMessage);
						} else if (result.response === "AWKWARD") {
							location.href = "/errorPage";
						} else if (result.response === "SUCCESS") {
							var data = result.object;
							if (data != null && data != 'undefined'
									&& data != "") {
								requirementApproverList('SUBMITTED');
								if (status == 'REJECTED') {
									$("#selector4").removeAttr("style");
									$("#selector4").css({
										display : "block"
									});
									$("#eduMsgDiv").html("Rejected");
									eduMsgFadeinFunction();
								}
							}
						} else if (result.indexOf('Sign In') !== -1) {
							location.replace(sourceleadUrl);
						} else {
							showError("Operation failed, please try again");
						}
					},
				});

	} else {
		if (reason) {
			$("#reasonDasbordDiv")
					.empty()
					.append(
							"<font color='red' style='padding: 5px 10px; '>Must not Start With Space</font>");
		} else {
			$("#reasonDasbordDiv")
					.empty()
					.append(
							"<font color='red' style='padding: 5px 10px; '>Reason is required</font>");
		}
	}
}

function rejectRequirementAlertMessage(status, requirementId) {
	var result = "";
	result = result + "	<div class='project_inn_mask'></div>";
	result = result
			+ "	    <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result
			+ "		<textarea id='rejectReason' class='form-control' field-name='Reject Reason' data-validation-optional='true' data-validation='validate_Space validate_Description validate_length length2-250' name='rejectReason' value='' placeholder='Reject Reason' rows='2'></textarea>";
	result = result + "	<div id='reasonDasbordDiv'/>";
	result = result + "		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result
			+ "		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onClick ='updateReuqirementStatus(\""
			+ status
			+ "\",\""
			+ requirementId
			+ "\");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelAlertDashboard();'></b>";
	result = result + "	</div>";
	$("#rejectRequirementAlert").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}

function cancelAlertDashboard() {
	$("#rejectRequirementAlert").empty();
	$(".project_inn_mask").fadeOut(200);
}
// requirementApproverList('SUBMITTED'); END HERE
function ApplicantAuthorization(status) {
	// var sourceleadUrl = '${sourceleadUrl}';
	// var loginUser = '${SourceLeadWeb_userName}';
	// $("#accepted").attr('style','position:absolute; width:100%; height:350px;
	// background-color:rgba(255,255,255,0.8); top:0px; left:0px;
	// z-index:100;background-image:url("/resources/images/loading.gif");
	// background-position:center; background-repeat:no-repeat;
	// background-size:75px;');

	// var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';
	if (locationList.match("yyyy-MM-dd")) {
		dashBoardDate = 'YYYY-MM-DD';
	}
	if (locationList.match("dd-MM-yyyy")) {
		dashBoardDate = 'DD-MM-YYYY';
	}
	if (locationList.match("MM-dd-yyyy")) {
		dashBoardDate = 'MM-DD-YYYY';
	}
	if (locationList.match("yyyy-dd-MM")) {
		dashBoardDate = 'YYYY-DD-MM';
	}

	$.ajax({
				type : "POST",
				url : sourceleadUrl + "/getApplicantsByStatus?status=" + status,
				success : function(data) {
					if (data.response === "FAILED") {
						showError(data.responseMessage);
					} else if (data.response === "AWKWARD") {
						location.href = "/errorPage";
					} else if (data.response === "SUCCESS") {
						var json = data.data;
						$("#accepted").empty();
						$("#requestSent").empty();
						$("#accepted").removeAttr("style");
						var resultToday = "";
						// if(json.length > 0){
						resultToday = resultToday
								+ '<div id="rejectApplicantAlert" ></div>  ';
						resultToday = resultToday
								+ '	<table id="applicantsPagination" class="display" cellspacing="0" width="99.5%">';
						resultToday = resultToday + '	<thead>';
						resultToday = resultToday + '	  <tr>';
						resultToday = resultToday + '	    <th>Applicant</th>';
						resultToday = resultToday + '	    <th>Job</th>';
						resultToday = resultToday + '	    <th>Team Member</th>';
						if (status == 'accepted') {
							resultToday = resultToday
									+ '	    <th>Accepted By</th>';
							resultToday = resultToday
									+ '	    <th><div class="tblminwidth-100">Accepted On<br>(<small class="custom-small">'
									+ dashBoardDate + '</small>)</div></th>';
						}
						if (status == 'REJECTED') {
							resultToday = resultToday
									+ '	    <th>Rejected By</th>';
							resultToday = resultToday
									+ '	    <th><div class="tblminwidth-100">Rejected On<br>(<small class="custom-small">'
									+ dashBoardDate + '</small>)</div></th>';
						}
						if (status == 'requestSent') {
							resultToday = resultToday + '	    <th>Status</th>';
							resultToday = resultToday + '	    <th>Action</th>';
						}
						resultToday = resultToday + '	  </tr>';
						resultToday = resultToday + '	</thead>';
						resultToday = resultToday + '	<tbody>';

						for (var i = 0; i < json.length; i++) {
							var report = json[i];
							resultToday = resultToday + '      <tr>';

							if (report.candidateName != ''
									&& report.candidateName != null
									&& report.candidateName != 'undefined') {
								resultToday = resultToday
										+ '           <td><div><span title="'
										+ report.candidateName + '">'
										+ report.candidateName
										+ '</span></div></td>';
							} else {
								resultToday = resultToday
										+ '           <td><div>-----</div></td>';
							}
							if (report.requirementName != ''
									&& report.requirementName != null
									&& report.requirementName != 'undefined') {
								resultToday = resultToday
										+ '<td><div class="tbldata-dotdot-sm"> <a href="'
										+ sourceleadUrl
										+ '/requirementSummary?requirementUUID='
										+ report.requirementUUID
										+ '"><span title="'
										+ report.requirementName + '">'
										+ report.requirementName
										+ '</span></a></div></td>';

							} else {
								resultToday = resultToday
										+ '           <td><div>-----</div></td>';
							}
							if (report.submittedBy != ''
									&& report.submittedBy != null
									&& report.submittedBy != 'undefined') {
								resultToday = resultToday
										+ '           <td><div><span title="'
										+ report.submittedBy + '">'
										+ report.submittedBy
										+ '</span></div></td>';
							} else {
								resultToday = resultToday
										+ '           <td><div>-----</div></td>';
							}
							if (status == 'accepted') {
								if (report.submissionRequestUser != null
										&& report.submissionRequestUser != 'undefined') {
									resultToday = resultToday
											+ '           <td><div><span title="'
											+ report.submissionRequestUser
											+ '">'
											+ report.submissionRequestUser
											+ '</span></div></td>';
								} else {
									resultToday = resultToday
											+ '           <td><div><span title="'
											+ report.candidateName + '">'
											+ report.candidateName
											+ '</span></div></td>';

								}

								resultToday = resultToday
										+ '           <td><div class="tblminwidth-100"><span title="'
										+ report.submittedOn + '">'
										+ report.submittedOn
										+ '</span></div></td>';
							}
							if (status == 'REJECTED') {
								if (report.submissionRequestUser != null
										&& report.submissionRequestUser != 'undefined') {
									resultToday = resultToday
											+ '           <td><div><span title="'
											+ report.submissionRequestUser
											+ '">'
											+ report.submissionRequestUser
											+ '</span></div></td>';
								} else {
									resultToday = resultToday
											+ '           <td><div><span title="'
											+ report.candidateName + '">'
											+ report.candidateName
											+ '</span></div></td>';

								}

								resultToday = resultToday
										+ '           <td><div class="tblminwidth-100"><span title="'
										+ report.submittedOn + '">'
										+ report.submittedOn
										+ '</span></div></td>';
							}

							if (status == 'requestSent') {
								resultToday = resultToday
										+ '<td><div>Received</div></td>';
								resultToday = resultToday + "<td>";
								if (report.submissionRateStatus == 'REQUEST'
										&& report.createdUser.trim() != loginUser
												.trim()) {
									resultToday = resultToday
											+ '<a  class="paddingRight"  href="javascript:void(0)" title="Accept"  onClick="acceptCandidateFromDashboard(\''
											+ report.id
											+ '\', \'accept\');"><i class="fa fa-check text-success"></i></a>';
									resultToday = resultToday
											+ '<a class="paddingLeft" href="javascript:void(0)" title="Reject"   onClick="rejectApplicantAlertMessage(\''
											+ report.id
											+ '\', \'rejected\');"><i class="fa fa-close text-danger"></i></a>';
								} else {
									resultToday = resultToday + '---';
								}
								resultToday = resultToday + "</td>";
							}
							resultToday = resultToday + '     </tr>';
						}
						resultToday = resultToday + '	 </tbody>';
						resultToday = resultToday + '	</table>';
						// }
						if (status == 'accepted') {
							$("#accepted").append(resultToday);
							applicantsPagination();
							$("#accepted").addClass("tab-pane fade in active");
							$("#requestSent").removeClass(
									"tab-pane fade in active");
						} else if (status == 'requestSent') {
							$("#requestSent").append(resultToday);
							applicantsPagination();
							$("#accepted").removeClass(
									"tab-pane fade in active");
							$("#requestSent").addClass(
									"tab-pane fade in active");
						} else {
							$("#accepted").append(resultToday);
							applicantsPagination();
							$("#acceptedTab").removeClass("active");
							$("#requestSentTab").removeClass("active");

						}
					} else if (data.indexOf('Sign In') !== -1) {
						location.replace(sourceleadUrl);
					} else {
						showError("Operation failed, please try again");
					}
				},
			});
	return true;
}
function rejectApplicantAlertMessage(submissionId, status) {
	var result = "";
	result = result + "	<div class='vendor_wrap_mask'></div>";
	result = result
			+ "	    <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result
			+ "<form id='rejected_from_dashboard' name='rejected_from_dashboard'>";
	result = result + "    <div class='col-md-12 inputOther mt10 inputOther'>";
	result = result
			+ "    <div class='dropdown-select mb10' id='rejectedCategorytDiv'>";
	result = result
			+ "<select id='rejectedCategory' name='rejectedCategory' class='form-control' onchange='getRejectedCategory()' data-validation='required' field-name='Category'>";
	result = result + "<option selected value=''>--Select Category--</option>";
	result = result
			+ "<option value='Wrong Skill Sets'>Wrong Skill Sets</option>";
	result = result
			+ "<option value='Unsuitable Personality'>Unsuitable Personality</option>";
	result = result + "<option value='Bad Fit'>Bad Fit</option>";
	result = result + "<option value='Salary'>Salary</option>";
	result = result
			+ "<option value='Interview Rejection'>Interview Rejection</option>";
	result = result + "<option value='Other'>Other</option>";
	result = result + "</select>";
	result = result + "    </div>";

	result = result
			+ " <div id='rejectOtherCategoryDiv' style='display: none' class='mb10'>";
	result = result
			+ " <input type='text' id='rejectedCategoryOther' name='rejectedCategoryOther' class='form-control' data-validation-optional='true' field-name='Other' >";
	result = result + " </div>";

	// result = result +" <p>Applicant reject message here</p>";
	result = result + "    <div class='clearfix'>";
	result = result
			+ "        <textarea  id='comment' class='form-control txtarea_action' name='comment'  placeholder='Comments here' onkeypress='dashboardReasonForRejectRemoveSpaces();' data-validation='required validate_Space validate_length length2-500' field-name='Reason'></textarea>";
	result = result + " </div>";
	result = result + " </div>";
	result = result + ' <div id="reasonForApplicantDiv"/>';
	result = result + "		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result
			+ "		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onClick ='acceptRejectCandidateFromDashboard(\" "
			+ submissionId
			+ "\", \"rejected\");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelAlert();'></b>";
	result = result + "</form>";
	result = result + "	</div>";
	$("#rejectApplicantAlert").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".vendor_wrap_mask").fadeIn(200);
}
function dashboardReasonForRejectRemoveSpaces() {
	var output = document.getElementById("reasonForReject").value;
	output = output.replace(/\s/g, "")
	if (output.length != 0) {
		$("#reasonForReject").removeAttr("data-validation");
	}
}
function acceptCandidateFromDashboard(submissionId, status) {
	if ($('#rejected_from_dashboard').validate(false, validationSettings)) {
		var formData = getFormData('rejected_from_dashboard');
		$.ajax({
			type : "POST",
			data : formData,
			url : sourceleadUrl
					+ "/acceptRejectCandidateFromDashboard?submissionId="
					+ submissionId + "&status=" + status,
			success : function(result) {
				if (result.response === "FAILED") {
					showError(result.responseMessage);
				} else if (result.response === "AWKWARD") {
					location.href = "/errorPage";
				} else if (result.response === "SUCCESS") {
					ApplicantAuthorization('requestSent');
					$("#selector4").removeAttr("style");
					$("#selector4").css({
						display : "block"
					});
					$("#eduMsgDiv").html("Accepted");
					eduMsgFadeinFunction();
				} else if (result.indexOf('Sign In') !== -1) {
					location.replace(sourceleadUrl);
				} else {
					showError("Operation failed, please try again");
				}
			},
		});
		return true;
	} else {
		return false;
	}
}
function getRejectedCategory() {
	var rejectedCategory = document.getElementById("rejectedCategory").value;
	if (rejectedCategory == 'Other') {
		document.getElementById("rejectOtherCategoryDiv").style.display = "block";
		$("#rejectedCategoryOther").removeAttr("data-validation-optional");
		$('#rejectedCategoryOther').attr("data-validation",
				"required validate_Space validate_length length2-100 ");
		$('#rejectOtherCategoryDiv').find('p.jquery_form_error_message')
				.remove();
	} else {
		$("#rejectedCategoryOther").attr("data-validation-optional", "true");
		$("#rejectedCategoryOther").removeAttr("data-validation");
		$("#rejectedCategoryOther").val('');
		document.getElementById("rejectOtherCategoryDiv").style.display = "none";
		$('#rejectOtherCategoryDiv').find('p.jquery_form_error_message')
				.remove();
	}

}
function eduMsgFadeinFunction() {
	$("#selector4").fadeIn(200);
	$('#selector4').delay(2000).fadeOut('slow');
}
function cancelAlert() {
	$("#rejectApplicantAlert").empty();
	$(".vendor_wrap_mask").fadeOut(200);
}
var validationSettings = {
	errorMessagePosition : 'element',
	borderColorOnError : '',
	scrollToTopOnError : true,
	dateFormat : 'yyyy/mm/dd'
};
function acceptRejectCandidateFromDashboard(submissionId, status) {

	if ($('#rejected_from_dashboard').validate(false, validationSettings)) {

		var formData = getFormData('rejected_from_dashboard');
		$.ajax({
			type : "POST",
			data : formData,
			url : sourceleadUrl
					+ "/acceptRejectCandidateFromDashboard?submissionId="
					+ submissionId + "&status=" + status,
			success : function(result) {
				if (result.response === "FAILED") {
					showError(result.responseMessage);
				} else if (result.response === "AWKWARD") {
					location.href = "/errorPage";
				} else if (result.response === "SUCCESS") {
					$("#popUp").empty();
					$("#rejectApplicantAlert").empty();
					$(".vendor_wrap_mask").fadeOut(200);
					ApplicantAuthorization('requestSent');
					$("#selector4").removeAttr("style");
					$("#selector4").css({
						display : "block"
					});
					$("#eduMsgDiv").html("Rejected");
					eduMsgFadeinFunction();
				} else if (result.indexOf('Sign In') !== -1) {
					location.replace(sourceleadUrl);
				} else {
					showError("Operation failed, please try again");
				}
			}
		});
		return true;
	} else {
		return false;
	}
}
$('body').on('blur', '#rejected_from_dashboard', function() {
	$("#rejected_from_dashboard").showHelpOnFocus();
	$("#rejected_from_dashboard").validateOnBlur(false, validationSettings);
});
function applicantsPagination() {
	$('#applicantsPagination').DataTable({
		"scrollCollapse" : false,
		"lengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"bLengthChange" : false,
		"bDestroy" : true,
		"ordering" : true,
		"oLanguage" : {
			"sSearch" : "",
			"sZeroRecords" : "",
			"sEmptyTable" : ""
		}
	});
}
// interview tasks starts here

function getInterviewsandTasks() {
	/*
	 * var date = $("#eventCalendar").val(); var timezone
	 * ='${SourceLeadWeb_orgLocations.timezone}'; var timezoneId = "";
	 * if(timezone != null && timezone != '' && timezone != 'undefined'){
	 * timezoneId = '${SourceLeadWeb_orgLocations.timezone.id}'; }
	 */
	// var candidateId = '${SourceLeadWeb_candidateId}';
	// var sourceleadUrl ='${sourceleadUrl}';
	$.ajax({
				type : "POST",
				url : sourceleadUrl
						+ "/getProfessionalInterviewsEventDashBoard?dateRange="	+ date + "&candidateId=" + candidateId + "&timezoneId="	+ timezoneId,
				success : function(json) {
					var result = "";
					result = result
							+ '    <div class="eventHeader">                                                      ';
					result = result
							+ '          <div class="media">                                                      ';
					result = result
							+ '            <div class="media-left bg-success paddingRight">                       ';
					result = result
							+ '                <i class="fa fa-calendar fa-3x"></i>                               ';
					result = result
							+ '            </div>                                                                 ';
					result = result
							+ '            <div class="media-body paddingLeft">                                   ';
					result = result
							+ '              <h4 class="media-heading">'
							+ json.dayOfweek
							+ '</h4>                               ';
					result = result + '              <p class="small">'
							+ json.selectedDate
							+ '</p>                               ';
					result = result
							+ '            </div>                                                                 ';
					result = result
							+ '          </div>                                                                   ';
					result = result + '    </div>																		   ';
					for (var i = 0; i < json.interviewerList.length; i++) {
						var report = json.interviewerList[i];
						var location = '';
						if (report.location != null && report.location != ''
								&& report.location != 'undefined') {
							location = report.location
						} else {
							location = "--";
						}

						if (report.interviewType != null
								&& report.interviewType != ""
								&& report.interviewType != 'undefined') {
							interviewType = report.interviewType;
						} else {
							interviewType = "--";
						}
						result = result
								+ '   <div class="eventList clearfix">  ';
						result = result
								+ '      <div class="media">              ';
						result = result
								+ '          <div class="media-left">     ';
						result = result + '              <div>'
								+ report.startTimeStr + '</div>       ';
						result = result + '              <div>'
								+ report.endTimeStr + '</div>        ';
						result = result
								+ '          </div>                       ';
						result = result
								+ '              <div class="media-body paddingLeft">   ';
						result = result
								+ '                  <figure class="pull-left">         ';
						result = result
								+ '                  <img src="/resources/images/interview_icons.png" class="img-responsive" width="40">    ';
						result = result
								+ '                  </figure>                                                                   ';
						result = result
								+ '                      <div class="pull-left event_details form-horizontal">                   ';
						result = result
								+ '                          <p><label class="text-muted small col-md-2">Job Title   :</label> <a href="'
								+ sourceleadUrl + '/requirementProfile/'
								+ report.requirementUUID + '"><span title ="'
								+ report.requirementTitle + '">'
								+ report.requirementTitle + '</span></a></p>';
						result = result
								+ '                          <p><label class="text-muted small col-md-2">Company Name:</label>'
								+ report.companyName + ' </p>    ';
						result = result
								+ '                          <p><label class="text-muted small col-md-2">Interview Type:</label> '
								+ interviewType + '</p>    ';
						result = result
								+ '                          <p><label class="text-muted small col-md-2">Location:</label>'
								+ location + '</p>    ';
						result = result
								+ ' 								</div>                                                                         ';
						result = result
								+ '                      </div>                                                                                   ';
						result = result
								+ '              </div>                                                                                           ';
						result = result + '      </div>   ';
						result = result
								+ '  </div>                                                                                                       ';
					}

					if (json.interviewerList.length <= 0) {
						result = result
								+ '<div class="row">                                            ';
						result = result
								+ '	<div class="dataTables_empty" style="border: 1px solid #ddd;margin: 0px 15px;">                                     ';
						result = result
								+ '	</div>                                                      ';
						result = result
								+ ' </div>                                                      ';
					}
					result = result + '<div class="mask_inner"></div>';
					$("#professionalInterviewDiv").empty();
					$("#professionalInterviewDiv").append(result);
				}
			});

}
$(".addInterview_close, .side_mask").click(function() {
	$(".add_inter_wrap").removeClass("add_inter_wrap_active");
	$(".side_mask").fadeOut(200);
	$("body").css('overflow-y', 'auto');
});
$(function() {
	var finalDateFormatProeventCalendar = '';
	if (locationDate.match("yyyy-MM-dd")) {
		finalDateFormatProeventCalendar = 'yy-mm-dd';
	}
	if (locationDate.match("dd-MM-yyyy")) {
		finalDateFormatProeventCalendar = 'dd-mm-yy';
	}
	if (locationDate.match("MM-dd-yyyy")) {
		finalDateFormatProeventCalendar = 'mm-dd-yy';
	}
	if (locationDate.match("yyyy-dd-MM")) {
		finalDateFormatProeventCalendar = 'yy-dd-mm';
	}
	$("#eventCalendar").datepicker({
		changeMonth : true,
		changeYear : true,
		dateFormat : finalDateFormatProeventCalendar,
		onSelect : function(selected) {
			getInterviewsandTasks();

		}
	});
});
$(function() {
	$('#example').timepicker();
}); // interview task here end here.
// interview dates starts here
var allDates = {};
var interviewDates = {};
function getInterviewsDates() {
	// var sourceleadUrl = '${sourceleadUrl}';
	var dates = {}
	$.ajax({
		type : "POST",
		url : sourceleadUrl + "/getInterviewsTaskDates?",
		success : function(result) {
			if (result.response === "FAILED") {
				showError(result.responseMessage);
			} else if (result.response === "AWKWARD") {
				location.href = "/errorPage";
			} else if (result.response === "SUCCESS") {
				var data = result.data;
				if (data.length > 0) {
					for (var i = 0; i < data.length; i++) {
						dates[new Date(data[i]).getTime()] = 'visible';
					}
				}
				interviewDates = dates;
				eventCalendar(dates);
			} else if (result.indexOf('Sign In') !== -1) {
				location.replace(sourceleadUrl);
			} else {
				showError("Operation failed, please try again");
			}
		}
	});
}

var jobFairDates = {};
function getJobFairDates() {
//	var sourceleadUrl = '${sourceleadUrl}';
	var dates = {}
	$.ajax({
				type : "POST",
				url : sourceleadUrl	+ "/getJobFairDatesForCalendar",
				success : function(result) {
				
					if (result.response === "FAILED") {
						showError(result.responseMessage);
					} else if (result.response === "AWKWARD") {
						location.href = "/errorPage";
					} else if (result.response === "SUCCESS") {
						var data = result.data;
						if (data.length > 0) {
							for (var i = 0; i < data.length; i++) {
								dates[new Date(data[i]).getTime()] = 'visible';
							}
						}
						jobFairDates = dates;
						getJobFairCalendar(dates);
					} else if (result.indexOf('Sign In') !== -1) {
						location.replace(sourceleadUrl);
					} else {
						showError("Operation failed, please try again");
					}
				}
	          
			});
}
function test() {
	for ( var e in jobFairDates) {
		alert(jobFairDates[e]);
	}
}
function getJobFairs() {
	$("#interviewsTab").removeClass("active");
	$("#dashboardEventsDiv").hide();

	$("#jobFairsTab").addClass("active");
	$("#dashboardJobFairsDiv").show();
	getJobFairDates();
	getJobsForSelectedFairDate();
}

function getInterviewsandTasks() {
	$("#interviewsTab").addClass("active");
	$("#dashboardEventsDiv").show();

	$("#jobFairsTab").removeClass("active");
	$("#dashboardJobFairsDiv").hide();

	getInterviewsDates();
	getInterviewsandTasksForSelectedDate();
}

function getSelectedDateFromCalendar(selectedDate) {
	var date = selectedDate;
	if (date == null || date == '' || date == 'undefined') {
		date = new Date();
		var month = parseInt(date.getMonth()) + parseInt(1);
		if (locationDate.match("yyyy-MM-dd")) {
			date = date.getFullYear() + "-" + month + "-" + date.getDate();
		}
		if (locationDate.match("dd-MM-yyyy")) {
			date = date.getDate() + "-" + month + "-" + date.getFullYear();
		}
		if (locationDate.match("MM-dd-yyyy")) {
			date = month + "-" + date.getDate() + "-" + date.getFullYear();
		}
		if (locationDate.match("yyyy-dd-MM")) {
			date = date.getFullYear() + "-" + date.getDate() + "-" + month;
		}
	}
	return date;
}

function getInterviewsandTasksForSelectedDate() {
	//var sourceleadUrl = '${sourceleadUrl}';
	var date = getSelectedDateFromCalendar($("#eventCalendar").val());
	$.ajax({
				type : "POST",
				url : sourceleadUrl	+ "/getInterviewsandTasks?dateRange="+ date,
				success : function(data) {
					if (data.response === "FAILED") {
						showError(data.responseMessage);
					} else if (data.response === "AWKWARD") {
						location.href = "/errorPage";
					} else if (data.response === "SUCCESS") {
						var json = data.object;
						var result = "";
						var opt = "";
						$("#dayOfweek").text(json.dayOfweek);
						$("#selectedDate").text(json.selectedDate);

						for (var i = 0; i < json.interviewerList.length; i++) {

							var report = json.interviewerList[i];
							var clientName = "---";
							if (report.clientName != null
									&& report.clientName != ""
									&& report.clientName != 'undefined'
									&& report.interviewSourceType != 'INTERVIEW') {
								clientName = report.clientName;
							}

							result = result + '   <div class="clearfix">  ';
							result = result
									+ '   <div class="eventList clearfix">  ';
							result = result
									+ '      <div class="media">              ';
							result = result
									+ '          <div class="media-left">     ';
							result = result + '              <div>'
									+ report.startTimeStr + '</div>       ';
							result = result + '              <div>'
									+ report.endTimeStr + '</div>        ';
							result = result
									+ '          </div>                       ';
							result = result
									+ '              <div class="media-body paddingLeft">   ';
							result = result
									+ '                  <figure class="pull-left">         ';
							result = result
									+ '                  <img src="/resources/images/interview_icons.png" class="img-responsive" width="40">    ';
							result = result
									+ '                  </figure>                                                                   ';
							result = result
									+ '                      <div class="pull-left event_details form-horizontal">                   ';
							result = result
									+ '                          <p><label class="text-muted small col-md-2">Name:</label>'
									+ report.candidateName + ' </p>    ';
							result = result
									+ '									 <div class="personalInfo pull-right">                                          ';
							result = result
									+ ' 									<i class="fa fa-ellipsis-v" title="Email & Phone"></i>                         ';
							result = result
									+ '										<div class="userdetails">                                                      ';
							result = result
									+ ' 									<p><i class="fa fa-envelope"></i><label>'
									+ report.candidateEmail
									+ '</label></p>     ';
							result = result
									+ ' 									<p><i class="fa fa-phone"></i><label>'
									+ report.candidatePhoneNumber
									+ '</label></p>                 ';
							result = result
									+ ' 									</div>                                                                         ';
							result = result
									+ ' 								</div>                                                                         ';
							result = result
									+ '                          <p><label class="text-muted small col-md-2">Job:</label> <a href="'
									+ sourceleadUrl
									+ "/requirementSummaryView?requirementUUID="
									+ report.requirementUUID + "&interviewId="
									+ report.interviewId.id + '">'
									+ report.requirementTitle + '</a></p>';
							result = result
									+ '                          <p><label class="text-muted small col-md-2">Client:</label> '
									+ clientName + '</p>    ';
							result = result
									+ '                          <div class="feedBack"> ';

							if (report.interviewerUUID != null
									&& report.interviewerUUID != ''
									&& report.interviewerUUID != 'undefined'
									&& report.interviewTemplateUUId != null
									&& report.interviewTemplateUUId != ''
									&& report.interviewTemplateUUId != 'undefined') {
								result = result
										+ '	 <a  href="'
										+ sourceleadUrl
										+ '/'
										+ 'dashboardInterviewFeedBackTemplate?templateId='
										+ report.interviewTemplateUUId
										+ '&requirementInterviewid='
										+ report.interviewerUUID + '" >';
								if (report.feedbackDate != null
										&& report.feedbackDate != ''
										&& report.feedbackDate != 'undefined') {
									result = result
											+ '<i class="fa fa-handshake-o success_color"></i>';
								} else {
									result = result
											+ '<i class="fa fa-handshake-o"></i>';
								}
								result = result + '</a>';
							}

							result = result
									+ '                          </div>                                                                               ';
							result = result
									+ '                      </div>                                                                                   ';
							result = result
									+ '              </div>                                                                                           ';
							result = result + '      </div>   ';
							result = result
									+ ' <div id="feedbackInterviewerId"></div>';
							result = result
									+ '  </div>                                                                                                       ';
						}

						for (var i = 0; i < json.tasksList.length; i++) {
							var report = json.tasksList[i];
							var taskTime = "";
							var priority = "";
							var description = "";
							if (report.taskTime != null
									&& report.taskTime != ''
									&& report.taskTime != 'undefined') {
								taskTime = report.taskTime;
							}
							if (report.priority != null
									&& report.priority != ''
									&& report.priority != 'undefined') {
								priority = report.priority;
							} else {
								priority = "--";
							}
							if (report.description != null
									&& report.description != ''
									&& report.description != 'undefined') {
								description = report.description;
							} else {
								description = "--";
							}
							opt = opt
									+ '  <div class="eventList clearfix">       ';
							opt = opt
									+ '     <div class="media">                 ';
							opt = opt
									+ '         <div class="media-left">        ';
							opt = opt + '             <div>' + taskTime
									+ '</div>           ';
							opt = opt
									+ '         </div>                          ';
							opt = opt
									+ '             <div class="media-body paddingLeft"> ';
							opt = opt
									+ '                 <figure class="pull-left">       ';
							opt = opt
									+ '                 <img src="/resources/images/task-icon.png" class="img-responsive" width="40">    ';
							opt = opt
									+ '                 </figure>                                                             ';
							opt = opt
									+ '                     <div class="pull-left event_details">                             ';
							opt = opt
									+ '                         <p><label class="text-muted small col-md-2">Subject:</label> '
									+ report.subject + '</p>         ';
							opt = opt
									+ '                     <div class="clearfix"></div>            ';
							opt = opt
									+ '                     <div class="row">            ';
							opt = opt
									+ '                     <div class="col-md-5">            ';
							opt = opt
									+ '                         <p class="text-danger"><label class="text-muted small col-md-6" style="width:45%;">Priority:</label> '
									+ priority + '</p>   ';
							opt = opt
									+ '                     </div>            ';
							opt = opt
									+ '                     <div class="col-md-5">            ';
							opt = opt
									+ '                         <p><label class="text-muted small col-md-6">Status:</label> '
									+ report.taskStatus
									+ '</p>                    ';
							opt = opt
									+ '                     </div>            ';

							opt = opt
									+ '                     </div>            ';

							opt = opt
									+ '                         <p><label class="text-muted small col-md-2" style="width:15.7%;">Des.:</label><div class="col-md-10 paddingLeft">'
									+ description + '</div></p>';
							opt = opt
									+ '                     </div>            ';
							opt = opt
									+ '             </div>                    ';
							opt = opt
									+ '     </div>                            ';
							opt = opt
									+ ' </div>                                ';
						}
						if (json.tasksList.length <= 0
								&& json.interviewerList.length <= 0) {
							result = result
									+ '<div class="row">                                            ';
							result = result
									+ '	<div class="dataTables_empty" style="border: 1px solid #ddd;margin: 0px 15px;">                                     ';
							result = result
									+ '	</div>                                                      ';
							result = result
									+ ' </div>                                                    ';
							result = result
									+ ' </div>                                                    ';
						}
						result = result + '<div class="mask_inner"></div>';
						$("#interviewDiv").empty();
						$("#interviewDiv").append(result);
						$("#taskDiv").empty();
						$("#taskDiv").append(opt);
					} else if (data.indexOf('Sign In') !== -1) {
						location.replace(sourceleadUrl);
					} else {
						showError("Operation failed, please try again");
					}
				}
			});
}

function getJobsForSelectedFairDate() {
	//var sourceleadUrl = '${sourceleadUrl}';
	var date = getSelectedDateFromCalendar($("#jobFairCalendar").val());
	$("#fairJobsDiv").empty();
	$("#fairJobsDiv").attr(
					'style',
					'position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	$.ajax({
				type : "POST",
				url : sourceleadUrl + "/getJobFairRequirements?date="+ date,
				success : function(result) {
					if (result.response === "FAILED") {
						showError(result.responseMessage);
					} else if (result.response === "AWKWARD") {
						location.href = "/errorPage";
					} else if (result.response === "SUCCESS") {
						var dateFormat = "";
						if (locationDate.match("yyyy-MM-dd")) {
							dateFormat = 'yy-mm-dd';
						}
						if (locationDate.match("dd-MM-yyyy")) {
							dateFormat = 'dd-mm-yy';
						}
						if (locationDate.match("MM-dd-yyyy")) {
							dateFormat = 'mm-dd-yy';
						}
						if (locationDate.match("yyyy-dd-MM")) {
							dateFormat = 'yy-dd-mm';
						}

						$("#dayOfweekForJobFair").text(
								result.responseMessage.split("<>")[0]);
						$("#selectedDateForJobFair").text(
								result.responseMessage.split("<>")[1]);

						var data = result.data;
						if (data != null && data.length > 0) {
							var jobFairsText = "";
							for ( var e in data) {
								var text = "<div class='eventList clearfix'>";
								text += "	<div class='pull-left event_details form-horizontal dashboard_fair_table'>";
								text += "		<p>";
								text += "			<a target='_blank' href='"	+ sourceleadUrl	+ "/requirementSummary?requirementUUID=" 
													+ data[e].requirementUUID + "'>"
													+ data[e].jobTitle + "</a>";
								text += "		</p>";
								if (data[e].clientId != null) {
									text += "		<p>";
									text += " 			<a target='_blank' href='"
											+ sourceleadUrl
											+ "/clientDashboard?orgRelationUUID="
											+ data[e].clientId.uuid
											+ "' title='"
											+ data[e].clientId.clientName
											+ "'>"
											+ data[e].clientId.clientName
											+ "</a>";
									text += "		</p>";
								}
								text += "		<p>";
								for ( var i in data[e].jobFairList) {
									text += "<span class='dashboard_span'>"
											+ data[e].jobFairList[i].name
											+ "</span>";
								}
								text += "		</p>";
								text += "	</div>";
								text += "</div>";

								/* $("#fairJobsDiv").append(text); */
								jobFairsText += text;
							}
							
							$("#fairJobsDiv").append(jobFairsText);
						} else {
							var text = '<div class="row">';
							text = text
									+ '	<div class="dataTables_empty" style="border: 1px solid #ddd;margin: 0px 15px;">';
							text = text + '	</div>';
							text = text + ' </div>';
							text = text + ' </div>';
							$("#fairJobsDiv").append(text);
						}
						$("#fairJobsDiv").removeAttr("style");
					} else if (result.indexOf('Sign In') !== -1) {
						location.replace(sourceleadUrl);
					} else {
						showError("Operation failed, please try again");
					}
				}
			});
}
function dashboardAddInterview() {
	var selectedDate = $("#eventCalendar").val();
	$('.action-pop').slideUp();
	interviewTemplate();

	//var locationList = '${SourceLeadWeb_orgLocations.dateFormat}';
	var  DashBoardInterViewDate ='';
	if (locationList.match("yyyy-MM-dd")) {
		DashBoardInterViewDate = 'YYYY-MM-DD';
	}
	if (locationList.match("dd-MM-yyyy")) {
		DashBoardInterViewDate = 'DD-MM-YYYY';
	}
	if (locationList.match("MM-dd-yyyy")) {
		DashBoardInterViewDate = 'MM-DD-YYYY';
	}
	if (locationList.match("yyyy-dd-MM")) {
		DashBoardInterViewDate = 'YYYY-DD-MM';
	}

	var result = "";
	result = result + '<div class="add_inter addinterview_dashboard">';
	result = result + '<form id="addInterview_form" name="addInterview_form" >';
	result = result
			+ '<div class="add_inter_head"> <span>Schedule Interview</span>';
	result = result + '<div class="clearfix"></div>';
	result = result + '</div>';

	result = result + '<div class="clearfix form-group row">';

	result = result + '<div class="col-md-5">';
	result = result
			+ '<span class="col-md-3 paddingLeft mt10">Posted from<span class="text-danger">&nbsp;*</span></span>';
	result = result + '<div class="col-md-9 req_list">';
	result = result
			+ '<select class="form-control" id="requirementsTimeRange" onchange="getRequirements();">';
	result = result + '		<option value="today">Today</option>';
	result = result + '		<option value="yesterday">Yesterday</option>';
	result = result + '		<option value="week" selected>1 week</option>';
	result = result + '		<option value="twoweeks">2 weeks</option>';
	result = result + '		<option value="month">1 month</option>';
	result = result + '		<option value="twomonths">2 months</option>';
	result = result + '</select>';
	result = result + '</div>';
	result = result + '</div>';
	result = result + '<div class="clearfix form-group row"></div>';

	result = result + '<div class="col-md-5">';
	result = result
			+ '<span class="col-md-3 paddingLeft mt10">Job<span class="text-danger">&nbsp;*</span></span>';
	result = result + '<div class="col-md-9 req_list" id="reqDiv">';
	result = result
			+ '<select class="form-control" id="requirementsList" name="requirementsList" onchange="selectAndGetApplicantsForRequirement();" field-name="Job" data-validation="required">';

	result = result + '</select>';
	result = result + '</div>';
	result = result + '</div>';

	result = result
			+ '<div class="col-md-5" id="isClientInterviewDiv" style="display: none;">';
	result = result
			+ '<input type="checkbox" name="isClientInterview" id="isClientInterview" class="isClient" onclick="myFunction();">';
	result = result
			+ '<label>&nbsp;&nbsp;<strong>Is Client Interview</strong>&nbsp;&nbsp;</label>';
	result = result + '</div>';

	result = result + '</div>';

	result = result
			+ '		<input value="2" style="display: none;" type="text" id="maxTsRowNum" name="maxTsRowNum">';

	result = result
			+ '<table cellpadding="0" cellspacing="0" id="addInterviewTbl" class="addinterTable">';
	result = result + '<thead>';
	result = result + '<tr id="divTsRow0">';
	result = result + '<th>Applicant</th>';
	result = result + '<th>Interview Type</th>';
	result = result + '<th>Interview Date<br>(<small class="custom-small">'
			+ DashBoardInterViewDate + '</small>)</th>';
	result = result + '<th>Start - End Time</th>';
	result = result + '<th>Time Zone</th>';
	result = result + '<th>Details</th>';
	result = result + '<th>Interviewer</th>';
	result = result + '</tr>';
	result = result + '</thead>';

	result = result + '       <tbody>';
	result = result + '       <tr>';
	result = result + '		  <td>';
	result = result
			+ '  		<select  multiple data-placeholder="Select Applicants" onchange="selectInterviewApplicants()" class="blminwidth-50" id="1applicant" name="1applicant" field-name="Applicant" data-validation="required" >';
	result = result + ' 		</select>';
	result = result + '		  </td>';

	result = result + '		<td>';
	result = result
			+ '		<select class="form-control"  id="1interviewType" name="1interviewType" onchange="interviewType(this);">';
	result = result + '			<option value="">--Select Type--</option>';
	result = result + '			<option value="F2F">F2F</option>';
	result = result + '			<option value="Telephonic">Telephonic</option>';
	result = result + '			<option value="Technical">Technical</option>';
	result = result + '			<option value="Screening">Screening</option>';
	result = result + '			<option value="Skype">Skype</option>';
	result = result
			+ '			<option value="Webex/GoToMeeting">Webex/GoTo Meeting</option>';
	result = result + '			<option value="Other">Other</option>';
	result = result + '		</select>';
	result = result
			+ '		<div id="1interviewTypeCheck" style="display: none;"><input value=""  type="text" id="1interviewTypeother" name="1interviewTypeother" class="form-control input-sm pavInp" placeholder="Interview type" field-name="Interview type" data-validation-optional="true"></div>';
	result = result + '		</td>';

	result = result + '		<td>';
	result = result
			+ '		<input class="form-control input-sm" type="text" id="1dateOfInterview" name="1dateOfInterview" value="'
			+ selectedDate + '" readonly>';
	result = result + '		</td>';

	result = result + '		<td>';
	result = result
			+ '			<div class="pull-left timeZone validationError" id="timeDiv"> <span class="positionRelative"><input class="form-control input-sm timepicker" placeholder="Start Time" name="1startTime" id="1startTime" onBlur="validateEndTime();" type="text" data-validation="required" field-name="Start Time"/></span> <span class="positionRelative"><input class="form-control input-sm timepicker" placeholder="End Time" name="1endTime" id="1endTime" onBlur="validateEndTime();" type="text" field-name="End Time"/></span></div>';
	result = result + '		</td>';

	result = result + '		<td>';
	result = result
			+ '			<select class="form-control"  id="1timeZone" name="1timeZone" field-name="Timezone" data-validation="required">';
	result = result + '			</select>';
	result = result + '		</td>';

	result = result + '		<td>';
	result = result
			+ ' 		 <input value="" type="text" id="1location" name="1location" class="form-control input-sm location" placeholder="Details" field-name="Details" >';
	result = result + '		</td>';

	result = result + '		 <td>';
	result = result
			+ '  		<select  multiple data-placeholder="Select Interviewers" class="blminwidth-50" id="1interviewer" name="1interviewer" field-name="Interviewer">';
	result = result + ' 		</select>';
	result = result + '		 </td>';

	result = result + '      </tr>';
	result = result + '      </tbody>';

	result = result + '</table>';

	result = result + '<div class="clearfix"></div>';

	result = result + '<div class="row pad2">';
	result = result + '<div class="col-md-12 mt10">';
	result = result + '<div class="ne-ch row">';
	result = result
			+ '<label><input type="checkbox" name="notificationToApplicant" value="Y" checked="checked" ><b>Notify to Applicants</b></label>';
	result = result
			+ '<label><input type="checkbox" name="proposeTime" value="Y" checked="checked" ><b>Propose time</b></label>';
	result = result + '</div>';

	result = result
			+ '<div id="emptyTemplate" style="dispaly:none" class="row">';
	result = result
			+ '<strong class="clearfix col-md-12">Interview Template</strong>';
	result = result
			+ '<div class="chosen-select col-md-4 customchosen-select">';
	result = result
			+ '<select class="form-control mt10" id="interviewSubmissionTemplate" name="interviewSubmissionTemplate">';
	result = result + '</select>';
	result = result + '</div>';
	result = result + '</div>';

	result = result + '</div>';
	result = result + '</div>';

	result = result + '<div class="pull-right">';
	result = result
			+ '<button type="submit" id="add_interview" class="btn btn-sm btn-primary mr">Submit</button>';
	result = result
			+ '<button type="button" class="btn btn-sm btn-default" onClick="cancelAddInterviewDiv();">Cancel</button>';
	result = result + '</div>';
	result = result + '<div class="clearfix"></div>';
	result = result + '</form>';
	result = result + '<div id="interviewLoading"></div>';
	result = result
			+ '<div class="mt25" id="scheduledInterviewsList" style="overflow:auto;"></div>';
	result = result + '<div class="clearfix"></div>';
	result = result + '</div>';
	$("#addInterviewDiv").empty();
	$("#addInterviewDiv").append(result);

	$("#1applicant").chosen();
	$("#1applicant").trigger('chosen:update');
	$("#1interviewType").chosen();
	$("#1interviewType").trigger('chosen:update');

	$(".add_inter_wrap").addClass("add_inter_wrap_active");
	$(".side_mask").fadeIn(200);
	$("body").css('overflow-y', 'hidden');
	getRequirements();
	getTimezonesListForInterview();
	getInterviewers();

	$("#1startTime").timepicker({
		'showDuration' : true,
		'disableTextInput' : true
	}).on('changeTime', function() {
		$("#1endTime").timepicker('option', 'minTime', $(this).val());
	});

	$("#1endTime").timepicker({
		'showDuration' : true,
		'maxTime' : '23:30:00',
		'disableTextInput' : true
	}).on('changeTime', function() {
		$("#1startTime").timepicker('option', 'maxTime', $(this).val());
	});
}
function interviewTemplate() {
	//var sourceleadUrl = '${sourceleadUrl}';
	$.ajax({
				type : "POST",
				url : sourceleadUrl	+ "/getInterviewFeedbackTemplatesList",
				success : function(result) {
					if (result.response === "FAILED") {
						showError(result.responseMessage);
					} else if (result.response === "AWKWARD") {
						location.href = "/errorPage";
					} else if (result.response === "SUCCESS") {
						var json = result.data;
						var output = "";
						if (json.length > 0) {
							for (var i = 0; i < json.length; i++) {
								var opt = json[i];
								if (opt.isDefault == "Y") {
									output = output + "<option value='"
											+ opt.uuid + "' selected>"
											+ opt.templateName + "</option>";
								} else {
									output = output + "<option value='"
											+ opt.uuid + "'>"
											+ opt.templateName + "</option>";
								}
							}
							$("#interviewSubmissionTemplate").empty();
							$("#interviewSubmissionTemplate").append(output);

							$("#interviewSubmissionTemplate").chosen();
							$("#interviewSubmissionTemplate").trigger(
									'chosen:update');
							$('.chosen-container').css({
								'width' : '100% !important'
							});

						} else {
							$("#emptyTemplate").empty();
						}
					} else {
						showError("Operation failed, please try again");
					}
				},
			});
}
function interviewType(id) {
	var selectedReqId = $("#1interviewType").val();
	if (selectedReqId == "Other") {
		$("#1interviewTypeCheck").attr("style", "display:block");
		$("#1interviewTypeother").removeAttr('data-validation-optional');
		$("#1interviewTypeother").attr("data-validation",
				"required validate_length length1-50");
	} else {
		$("#1interviewTypeCheck").attr("style", "display:none");
		$("#1interviewTypeother").removeAttr("data-validation");
		$("#1interviewTypeother").attr("data-validation-optional", "true");
	}
}
function selectInterviewApplicants() {
	var selectedInterviewApplicants = $('#1applicant_chosen  span').size();
	$("#selectedInterviewApplicantsCount").html(selectedInterviewApplicants);
	$('#1applicant_chosen').next('p.jquery_form_error_message').remove();

	$('.search-choice-close')
			.click(
					function() {
						var interviewApplicants = $('#1applicant_chosen  span')
								.size();
						$('#1applicant_chosen').next(
								'p.jquery_form_error_message').remove();
						if (interviewApplicants < 1) {
							$('#1applicant_chosen')
									.after(
											'<p class="jquery_form_error_message">Applicant is Required</p>');
						}
						$("#selectedInterviewApplicantsCount").html(
								interviewApplicants);
					});

	getSeheduledInterviewsList();
}
function getSeheduledInterviewsList() {
	//var sourceleadUrl = '${sourceleadUrl}';
	var selectedReqId = $("#requirementsList").val();
	var selectedSubmissions = $("#1applicant").val();
	$("#scheduledInterviewsList").empty();
	$
			.ajax({
				type : "POST",
				url : sourceleadUrl
						+ "/getScheduledInterviewsListForSelectedApplicants?selectedReqId="
						+ selectedReqId + "&selectedSubmissions="
						+ selectedSubmissions,
				success : function(data) {
					if (data != null && data != 'undefined' && data != ""
							&& data != '[]') {
						var json = JSON.parse(data);
						var result = "";
						var jsonLength = json.length;
						if (jsonLength > 0) {

							result = result
									+ '<table cellpadding="0" cellspacing="0" id="addInterviewTbl" class="addinterTable">';
							result = result + '<thead>';
							result = result + '<tr id="divTsRow0">';
							result = result + '<th>Applicant</th>';
							result = result + '<th>Interview Type</th>';
							result = result + '<th>Start Date</th>';
							result = result + '<th>Start time - End time</th>';
							result = result + '<th>Time Zone</th>';
							result = result + '<th>Details</th>';
							result = result + '<th>Interviewer</th>';
							result = result + '</tr>';
							result = result + '</thead>';
							result = result + '       <tbody>';

							for (var i = 0; i < jsonLength; i++) {
								var opt = json[i];
								result = result + '       <tr>';
								result = result + '       <td>';
								if (null != opt.candidateName
										&& '' != opt.candidateName
										&& 'undefined' != opt.candidateName) {
									result = result
											+ '			<div class="tbldata-dotdot-sm text-center">'
											+ opt.candidateName + '</div>';
								} else {
									result = result
											+ '			<div class="tbldata-dotdot-sm text-center">---</div>';
								}
								result = result + '       </td>';

								result = result + '       <td>';
								if (null != opt.interviewType
										&& '' != opt.interviewType
										&& 'undefined' != opt.interviewType) {
									result = result
											+ '			<div class="tbldata-dotdot-100 text-center">'
											+ opt.interviewType + '</div>';
								} else {
									result = result
											+ '			<div class="tbldata-dotdot-100 text-center">---</div>';
								}
								result = result + '       </td>';

								result = result + '       <td>';
								var sTime = "";
								if (null != opt.interviewTimeZoneStartTimeStr
										&& '' != opt.interviewTimeZoneStartTimeStr
										&& 'undefined' != opt.interviewTimeZoneStartTimeStr) {
									sTime = opt.interviewTimeZoneStartTimeStr
											.substr(opt.interviewTimeZoneStartTimeStr.length - 8);
									var iDate = opt.interviewTimeZoneStartTimeStr
											.substring(0, 10)
									result = result
											+ '			<div class="tbldata-dotdot-sm text-center">'
											+ iDate + '</div>';
								} else {
									result = result
											+ '			<div class="tbldata-dotdot-sm text-center">---</div>';
								}
								result = result + '       </td>';

								result = result + '       <td>';
								var eTime = "";
								if (null != opt.interviewTimeZoneEndTimeStr
										&& '' != opt.interviewTimeZoneEndTimeStr
										&& 'undefined' != opt.interviewTimeZoneEndTimeStr) {
									eTime = opt.interviewTimeZoneEndTimeStr
											.substr(opt.interviewTimeZoneEndTimeStr.length - 8);
									result = result
											+ '			<div class="tbldata-dotdot-sm text-center">'
											+ sTime + " - " + eTime + '</div>';
								} else {
									result = result
											+ '			<div class="tbldata-dotdot-sm text-center">'
											+ sTime + '</div>';
								}
								result = result + '       </td>';

								result = result + '       <td>';
								if (null != opt.timezone && '' != opt.timezone
										&& 'undefined' != opt.timezone) {
									result = result
											+ '			<div class="tbldata-dotdot-sm text-center">'
											+ opt.timezone + '</div>';
								} else {
									result = result
											+ '			<div class="tbldata-dotdot-sm text-center">---</div>';
								}
								result = result + '       </td>';

								result = result + '       <td>';
								if (null != opt.location && '' != opt.location
										&& 'undefined' != opt.location) {
									result = result
											+ '			<div class="tbldata-dotdot text-center">'
											+ opt.location + '</div>';
								} else {
									result = result
											+ '			<div class="tbldata-dotdot text-center">---</div>';
								}
								result = result + '       </td>';

								result = result + '       <td>';
								if (null != opt.interviewersList
										&& '' != opt.interviewersList
										&& 'undefined' != opt.interviewersList) {
									result = result
											+ '			<div class="tbldata-dotdot text-center">'
											+ opt.interviewersList + '</div>';
								} else {
									result = result
											+ '			<div class="tbldata-dotdot text-center">---</div>';
								}
								result = result + '       </td>';
								result = result + '      </tr>';
							}

							result = result + '      </tbody>';
							result = result + '</table>';
							result = result + '<div class="clearfix"></div>';
							$("#scheduledInterviewsList").empty();
							$("#scheduledInterviewsList").append(result);

						}
					}
				},
			});
}
function getRequirements() {
	//var sourceleadUrl = '${sourceleadUrl}';
	var postedDateRange = $("#requirementsTimeRange").val();
	$.ajax({
				type : "GET",
				url : sourceleadUrl
						+ "/getAssignedRequirementListByOrgLocationIdAndUserIdAndPostedDate?postedDateRange="
						+ postedDateRange,
				success : function(data) {
					if (data != null && data != 'undefined' && data != ""
							&& data != '[]') {
						var json = JSON.parse(data);
						var result = "";
						result = result
								+ '<option value="">--- Select Job ---</option>';
						for (var i = 0; i < json.length; i++) {
							var opt = json[i];
							var jobId = "";
							if (null != opt.jobId && "" != opt.jobId
									&& 'undefined' != opt.jobId) {
								jobId = " - " + opt.jobId;
							}
							result = result + '<option value="' + opt.id
									+ '"> ' + opt.jobTitle + '' + jobId
									+ '</option>';
						}
						$("#requirementsList").empty();
						$("#requirementsList").append(result);
						$("#requirementsList").chosen();
						$("#requirementsList").trigger('chosen:update');
						$('#requirementsList').find('option:first-child').prop(
								'selected', true).end().trigger(
								'chosen:updated');
						$("#reqDiv").find('p.jquery_form_error_message')
								.remove();
						$("#reqDiv").find('p.text-primary').empty();
						$("#reqDiv")
								.append(
										"<p class='text-primary pull-left small'><i class='fa fa-info-circle paddingRight'></i>Select Job</p>");
					} else {
						var result = "";
						result = result
								+ '<option value="">--- Select Job ---</option>';
						$("#requirementsList").empty();
						$("#requirementsList").append(result);
						$("#requirementsList").chosen();
						$("#requirementsList").trigger('chosen:update');
						$('#requirementsList').find('option:first-child').prop(
								'selected', true).end().trigger(
								'chosen:updated');
						$("#reqDiv").find('p.jquery_form_error_message')
								.remove();
						$("#reqDiv").find('p.text-primary').empty();
						$("#reqDiv")
								.append(
										"<p class='jquery_form_error_message'>No Jobs found</span>");
					}
					$("#1applicant").attr("data-placeholder",
							"Select Applicants");
					$("#1applicant").empty();
					$('#1applicant').trigger('chosen:updated');
				},
			});

}
function selectAndGetApplicantsForRequirement() {
	$("#reqDiv").find('p.text-primary').empty();
	$('#isClientInterview').attr('checked', false);
	$("#isClientInterviewDiv").hide();
	getApplicantsForRequirement();
	$("#scheduledInterviewsList").empty();
}
function getApplicantsForRequirement() {
	//var sourceleadUrl = '${sourceleadUrl}';
	var selectedReqId = $("#requirementsList").val();
	if (selectedReqId != "") {
		var isClientInterview = "";
		if (document.getElementById('isClientInterview').checked) {
			isClientInterview = 'Y';
		} else {
			isClientInterview = 'N';
		}
		var existingApplicantsIds = [];
		$
				.ajax({
					type : "POST",
					url : sourceleadUrl
							+ "/getAllShortListCandidates?requirementId="
							+ selectedReqId + "&existingApplicantsIds="
							+ existingApplicantsIds + "&isClientInterview="
							+ isClientInterview,
					success : function(data) {
						if (data.response === "FAILED") {
							showError(data.responseMessage);
						} else if (data.response === "AWKWARD") {
							location.href = "/errorPage";
						} else if (data.response === "SUCCESS") {
							var json = data.data;
							var appCount = json.length;
							$("#1applicant").attr("data-placeholder",
									appCount + " Applicants available");
							$("#reqDiv").find('p.jquery_form_error_message')
									.remove();
							var output = "";
							for (var i = 0; i < appCount; i++) {
								var opt = json[i];
								if (opt.createdUser != null
										&& opt.createdUser != ''
										&& opt.createdUser != 'undefined') {
									output = output + "<option value='"
											+ opt.id + "'>" + opt.candidateName
											+ " (" + opt.createdUser
											+ ")</option>";
								} else {
									output = output + "<option value='"
											+ opt.id + "'>" + opt.candidateName
											+ "</option>";
								}

								if (opt.isClientSubmission == 'Y') {
									$("#isClientInterviewDiv").css("display",
											"block");
								} else {
									$('#isClientInterview').attr('checked',
											false);
									$("#isClientInterviewDiv").hide();
								}
							}
							$("#1applicant").empty();
							$("#1applicant").append(output);
							$("#1applicant").trigger("chosen:updated");
						} else if (data.indexOf('Sign In') !== -1) {
							location.replace(sourceleadUrl);
						} else {
							showError("Operation failed, please try again");
						}
					},
				});
	} else {
		$('#isClientInterview').attr('checked', false);
		$("#isClientInterviewDiv").hide();
		$("#1applicant").empty();
		$("#1applicant").trigger("chosen:updated");
	}
}
function myFunction() {
	getApplicantsForRequirement();
}
function getTimezonesListForInterview() {
	//var sourceleadUrl = '${sourceleadUrl}';
	//var timezone = '${timezone}';
	$.ajax({
		type : "POST",
		url : sourceleadUrl
				+ "/getTimezonesList?",
		success : function(data) {
			var json = JSON.parse(data);
			var output = "";
			output = output + "<option value=''>--Select--</option>";
			for (var i = 0; i < json.length; i++) {
				var opt = json[i];
				if (opt.timeZoneId == timezone) {
					output = output + "<option value='" + opt.id
							+ "' selected>" + opt.timeZoneId + "</option>";
				} else {
					output = output + "<option value='" + opt.id + "'>"
							+ opt.timeZoneId + "</option>";
				}

			}
			$("#1timeZone").empty();
			$("#1timeZone").append(output);
			$("#1timeZone").chosen();
			$("#1timeZone").trigger('chosen:update');
		}
	});
}
function getInterviewers() {
	//var sourceleadUrl = '${sourceleadUrl}';
	$.ajax({
		type : "POST",
		url : sourceleadUrl
				+ "/getAllInterviewers?",
		success : function(data) {
			if (data.response === "FAILED") {
				showError(data.responseMessage);
			} else if (data.response === "AWKWARD") {
				location.href = "/errorPage";
			} else if (data.response === "SUCCESS") {
				var json = data.data;
				var output = "";
				for (var i = 0; i < json.length; i++) {
					var opt = json[i];
					if (opt.lastName != 'undefined' && opt.lastName != null
							&& opt.lastName != "") {
						output = output + "<option value='" + opt.id + "'>"
								+ opt.firstName + " " + opt.lastName
								+ "</option>";
					} else {
						output = output + "<option value='" + opt.id + "'>"
								+ opt.firstName + "</option>";
					}
				}
				$("#1interviewer").empty();
				$("#1interviewer").append(output);
				$("#1interviewer").chosen();
				$("#1interviewer").trigger("chosen:updated");
			} else {
				showError("Operation failed, please try again");
			}
		},
	});
}
function validateEndTime() {
	$("#timeDiv").find('p.jquery_form_error_message').remove();
	var startTime = $("#1startTime").val();
	var endTime = $("#1endTime").val();
	if (endTime != null && endTime != '' && endTime != 'undefined') {
		if (startTime == endTime) {
			$("#timeDiv").find('p.jquery_form_error_message').remove();
			$("#add_interview").removeAttr("disabled");
			$("#add_interview").attr("disabled", "disabled");
			$("#timeDiv")
					.append(
							"<p class='jquery_form_error_message'>End Time should greater than Start Time</span>");
		} else {
			$("#timeDiv").find('p.jquery_form_error_message').remove();
			$("#add_interview").removeAttr("disabled");
		}
	}

	var startTime1 = $("1startTime").val();
	var endTime1 = $("#1endTime").val();
	if (endTime1 != null && endTime1 != '' && endTime1 != 'undefined') {
		if (startTime1 == endTime1) {
			$("#add_interview").attr("disabled", "disabled");
		}
	}
}
function cancelAddInterviewDiv() {
	$(".add_inter_wrap").removeClass("add_inter_wrap_active");
	$(".side_mask").fadeOut(200);
	$("body").css('overflow-y', 'auto');
}
$('body')
		.on(
				'submit',
				'#addInterview_form',
				function(e) {
					/*
					 * var isNofityToOthers =
					 * document.getElementById("notificationToOthers").value;
					 * if(isNofityToOthers == "Y"){ validateOtherMail(); }
					 */
					$("#reqDiv").find('p.text-primary').empty();
					if ($(this).validate(false, validationSettings)) {
						e.preventDefault();
						var frm = $('#addInterview_form').serializeFormJSON();
						var con = JSON.stringify(frm);
						var jsonData = encodeURIComponent(con);
					//	var sourceleadUrl = '${sourceleadUrl}';
					//	var requirementId = '${requirementId}';

						var selectedInterviewApplicants = $(
								'#1applicant_chosen  span').size();

						if (selectedInterviewApplicants <= 50) {
							$("#add_interview").attr("disabled", "disabled");
							$("#add_interview").addClass('button').val(
									'Processing..');
							$("#interviewLoading")
									.attr(
											'style',
											'position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
							$.ajax({
										type : "POST",
										url : sourceleadUrl	+ "/saveInterview?jsonData="+ jsonData,
										success : function(result) {
											$(".add_inter_wrap").removeClass(
													"add_inter_wrap_active");
											$(".side_mask").fadeOut(200);
											$("body").css('overflow-y', 'auto');
											/*
											 * var allApplicants = 'all';
											 * requirementPipeline(requirementId,allApplicants);
											 * pipelineList(requirementId);
											 */
										},
									});
						} else {
							alertInterviewMessage("<b>Selected Applicants should not more than 50</b>");
						}
						return true;
					} else {
						return false;
					}
				});

$('#addInterview_form').showHelpOnFocus();
$('#addInterview_form').validateOnBlur(false, validationSettings);

$(".addInterview_close, .side_mask").click(function() {
	$(".add_inter_wrap").removeClass("add_inter_wrap_active");
	$(".side_mask").fadeOut(200);
	$("body").css('overflow-y', 'auto');
});
function eventCalendar(dates) {
	$("#eventCalendar").show();
	$("#jobFairCalendar").hide();
	$("#eventCalendar").datepicker({
		changeMonth : true,
		changeYear : true,
		dateFormat : finalDateFormatEDashboardeventCalendar,
		onSelect : function(selected) {
			getInterviewsandTasksForSelectedDate();
		},
		beforeShowDay : function(date) {
			var hlText = dates[date.getTime()];
			return (hlText) ? [ true, "Highlighted", '' ] : [ true, '', '' ];
		}
	});
}



$(function() {
	$('#example').timepicker();
});
function feedbackView(interviewId, templateId) {
	$(".feedback_view").show(200);
	$(".mask_inner").fadeIn(200);
	// var sourceleadUrl ='${sourceleadUrl}';
	// var locationId = '${SourceLeadWeb_orgLocationId}';
	$.ajax({
				type : "GET",
				url : sourceleadUrl
						+ "/getInterviewrsFeedbackList?interviewId="+ interviewId + "&locationId=" + locationId,
				success : function(data) {
					var json = JSON.parse(data);
					var result = "";
					result = result
							+ '	<div id="#" class="require_wrap_table feedback_view overflow_inherint">                                     ';
					result = result
							+ '<a href="javascript:void(0)" class="close-div" onclick="closePop()"><i class="fa fa-close"></i></a>';
					result = result
							+ '	<strong>Interview Feedback</strong>                                                       ';
					result = result + '<div class="feedbackInner">';
					result = result
							+ '	<table id="requirementApproverPagination" cellspacing="0" width="100%" class="mt10">      ';
					result = result
							+ '	<thead>                                                                                   ';
					result = result
							+ '	<tr>                                                                                      ';
					result = result
							+ '	<th>Interviewer Name</th>                                                                 ';
					result = result + '	<th>Date</th>';
					result = result + '	<th>Action</th>';
					result = result + '	</tr>';
					result = result + '	</thead>';
					result = result + '	<tbody>';
					for (var i = 0; i < json.length; i++) {
						var report = json[i];
						if (report.feedbackDate != null
								&& report.feedbackDate != ''
								&& report.feedbackDate != 'undefined') {
							feedbackDate = report.feedbackDate;
						} else {
							feedbackDate = "--";
						}
						result = result + "  <tr>";
						result = result + '<td><div class="tbldata-dotdot-me">'
								+ report.interviewerName + '</div></td>';
						result = result + ' <td><div class="tblminwidth-100">'
								+ feedbackDate + '</div></td>';
						if (report.feedbackDate != null
								&& report.feedbackDate != ''
								&& report.feedbackDate != 'undefined') {
							result = result
									+ ' <td><div class="tblminwidth-50"><span class="text-primary"><a onclick="viewfeedback('
									+ report.id + ',\'' + templateId + '\',\''
									+ report.feedbackStatus
									+ '\');">View</a></span></div></td>';
						} else {
							result = result
									+ '<td><div class="tblminwidth-50"> &nbsp;<span class="text-danger">Notfilled</span></div></td>';
						}

						result = result + '</tr>';
					}
					result = result + '	</tbody>';
					result = result + '	</table>';
					result = result + '</div> ';
					result = result + '</div> ';
					$("#feedbackInterviewerId").empty();
					$("#feedbackInterviewerId").append(result);

				}
			});

}
function closePop() {
	$(".feedback_view").hide();
	$(".mask_inner").hide();
}
// contracts starts here
function contractList() {

	$("#contractList")
			.attr(
					'style',
					'position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	if (locationList.match("yyyy-MM-dd")) {
		DashboardContractDate = 'YYYY-MM-DD';
	}
	if (locationList.match("dd-MM-yyyy")) {
		DashboardContractDate = 'DD-MM-YYYY';
	}
	if (locationList.match("MM-dd-yyyy")) {
		DashboardContractDate = 'MM-DD-YYYY';
	}
	if (locationList.match("yyyy-dd-MM")) {
		DashboardContractDate = 'YYYY-DD-MM';
	}
	$
			.ajax({
				type : "POST",
				url : sourceleadUrl + "/getDashboardContracts?locationId="
						+ locationId,
				success : function(data) {
					$("#contractList").removeAttr("style");
					$("#contractList").empty();
					var result = "";
					result = result
							+ "<table id='contractListPagination' class='display' cellspacing='0' width='100%' >";
					result = result + "<thead>";
					result = result + "  <tr>";
					result = result + "    <th>Applicant Name</th>";
					result = result + "    <th>Job</th>";
					result = result + "    <th>Rate</th>";
					result = result
							+ "    <th>Join Date<br>(<small class='custom-small'>"
							+ DashboardContractDate + "</small>)</th>";
					result = result + "    <th>Vendor</th>";
					result = result + "    <th>Contact</th>";
					result = result + "    <th>Action</th>";
					result = result + "  </tr>";
					result = result + "</thead>";
					result = result + "<tbody>";

					for (var i = 0; i < data.length; i++) {
						var opt = data[i];
						// if(opt.contractUUID != null && opt.contractUUID !=''
						// && opt.contractUUID !='undefined'){
						result = result + "<tr>";
						if (opt.candidateName != null
								&& opt.candidateName != ''
								&& opt.candidateName != 'undefined') {
							result = result + "<td><div class=''><span title='"
									+ opt.candidateName + "'>"
									+ opt.candidateName
									+ "</span></a></div></td>";
						} else {
							result = result + "<td><div>---</div></td>";
						}
						if (opt.jobTitle != null && opt.jobTitle != ''
								&& opt.jobTitle != 'undefined') {
							result = result
									+ "<td><div class='tbldata-dotdot-sm'><span title='"
									+ opt.jobTitle + "'>" + opt.jobTitle
									+ "</span></div></td>";
						} else {
							result = result
									+ "<td><div class='tbldata-dotdot-sm'>---</div></td>";
						}
						if (opt.amount != null && opt.amount != ''
								&& opt.amount != 'undefined'
								&& opt.paymentMode != null
								&& opt.paymentMode != ''
								&& opt.paymentMode != 'undefined') {
							result = result + "<td><div><span  title='"
									+ opt.paymentType + "&nbsp;" + opt.amount
									+ "/" + opt.paymentMode + "&nbsp;'>"
									+ opt.paymentType + "&nbsp;" + opt.amount
									+ "/" + opt.paymentMode
									+ "</span></div></td>";
						} else if (opt.rate != null && opt.rate != ''
								&& opt.rate != 'undefined') {
							result = result + "<td><div>---</div></td>";
						}
						if (opt.dateOfJoiningStr != null
								&& opt.dateOfJoiningStr != ''
								&& opt.dateOfJoiningStr != 'undefined') {
							result = result + "<td><div><span title='"
									+ opt.dateOfJoiningStr + "'>"
									+ opt.dateOfJoiningStr
									+ "</span></div></td>";
						} else {
							result = result + "<td><div>---</div></td>";
						}
						if (opt.clientName != null && opt.clientName != ''
								&& opt.clientName != 'undefined') {
							result = result + "<td><div><span title='"
									+ opt.clientName + "'>" + opt.clientName
									+ "</span></div></td>";
						} else {
							result = result + "<td><div>---</div></td>";
						}
						if (opt.vendorContact != null
								&& opt.vendorContact != ''
								&& opt.vendorContact != 'undefined') {
							result = result + "<td><div><span title='"
									+ opt.vendorContact + "'>"
									+ opt.vendorContact + "</span></div></td>";
						} else {
							result = result + "<td><div>---</div></td>";
						}
						result = result
								+ "<td><div><a href='/app/addNewContract?contractUUID="
								+ opt.contractUUID + "&orgRelationId="
								+ opt.orgRelationId + "&spocUserCode="
								+ opt.spocUserCode
								+ "&type=Sow' title='Create Sow'>SOW</a>";
						if (opt.vendorMsaCreated != true) {
							result = result
									+ "<a href='/app/addNewContract?orgRelationId="
									+ opt.orgRelationId
									+ "&spocUserCode="
									+ opt.spocUserCode
									+ "&type=Msa' title='Create Msa'> / MSA</a>";
						}
						result = result + "</div></td>";

						result = result + "</tr>";
					}
					// }
					result = result + " </tbody>";
					result = result + "</table>";
					$("#contractList").append(result);
					$("#contractListDiv").css("display", "block");
					contractListPagination();
				},

			});
}

function contractListPagination() {
	$('#contractListPagination').DataTable({
		"scrollCollapse" : false,
		"lengthMenu" : [ [ 10, 15, 20, -1 ], [ 10, 15, 20, "All" ] ],
		"bLengthChange" : false,
		"bDestroy" : true,
		"bFilter" : false,
		"bPaginate" : false,
		"ordering" : true,
		"oLanguage" : {
			"sSearch" : "",
			"sZeroRecords" : "",
			"sEmptyTable" : ""
		}
	});
}

function predefineProfileImage(imageId){   
	$(imageId).attr('src','/resources/images/userprofile-img.png');
}

//contracts starts here
function getLoginUserTabsList() {

			$.ajax({
				type : "GET",
				url : sourceleadUrl + "/getLoginUserTabAccessList?locationId="+locationId+"&userId="+userId,
				success : function(data) {
					tabAccessArray = data;
					$("#masterSideNav").html(masterSideNav.masterSideNavPanel());
					
				},

			});
}

function getLoginUserRolesActionsList(){
	$.ajax({
		type : "GET",
		url : sourceleadUrl + "/getLoginUserRoleActionsList?locationId="+locationId+"&userId="+userId,
		success : function(data) {
			roleActionsArray = data;
			$("#masterHeaderNav").html(masterHeader.masterHeaderPanel());
			
		},

	});
}





// dashboard scripts
function configureDiv(){
	getDateFormatByOrgLocation();
	var result = '';
	result=result+'<div class="sideMask" style="display:block"></div>';
	result=result+'<div class="configure_wrap">';
	result=result+'	<div id="loading"></div>';
	result=result+'<div class="configure_wrap-inner">';
	result=result+'<h2 class="text-danger">Configure</h2>';
	result=result+'<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">'; 
	result=result+'<div class="form-group">';
	result=result+'<label>Time Zone<span class="text-danger paddingLeft">*</span></label>';
	result=result+'<div class="clearfix">';
	result=result+'<select class="chosen-select form-control" data-placeholder="Time Zone" field-name="Time zone" data-validation="required" id="timeZone" name="timeZone">';
	result=result+'</select>';
	//result=result+'<span class="hidden">Time Zone</span>';
	result=result+'</div>';
	result=result+'</div>';
	result=result+'<div class="form-group">';
	result=result+'<label>Date Format</label>';
	result=result+'<div class="clearfix dropdown-select">';
	result=result+'<select class="form-control" name="dateFormatForOrg" id="dateFormatForOrgId" data-validation="required" field-name="Date Format">';
	result=result+'</select>';
	result=result+'</div>';
	result=result+'</div>';
	result=result+'<div class="form-group mb10">';
	result=result+'<label>Organization Short Code<span class="text-danger paddingLeft">*</span></label>';
	result=result+'<div class="clearfix" id="generatedUserIdDiv">';
	result=result+'<input type="text" class="form-control" id="generatedUserId" style="text-transform:uppercase" name="generatedUserId" data-validation="required validate_length length1-4" field-name="Organization Short Code" onkeyup="validateOrgShortCode();" maxlength="4">';
	result=result+'</div>';
	result=result+'</div>';
	result=result+'<div class="clearfix">';
	result=result+'<p class="text-muted small" style="font-size: 11px;" title="Org Short code cannot be modified once submitted."><i class="fa fa-info-circle"></i> Org Short code cannot be modified once submitted.</p>';
	result=result+'</div>';
	
	result=result+'<div class="form-group">';
	result=result+'<label>Average Working Hours</label>';
	result=result+'<div class="clearfix dropdown-select" id="averageWorkingHoursDiv">';
	result=result+'<input type="text" class="form-control" maxlength="2" name="averageWorkingHours" id="averageWorkingHours" data-validation="required" field-name="Average Working Hours">';
	result=result+'</div>';
	result=result+'</div>';
	
	result=result+'</div>';
	result=result+'</div>';
	result=result+'<div class="clearfix">';
	result=result+'<div class="col-md-6">';
	result=result+'<input type="button" value="Proceed" id="saveOrgConfiguration" class="btn btn-primary btn-block" onclick="storeOrgShortCode();">';
	result=result+'</div>';
	result=result+'<div class="col-md-6">';
	result=result+'<input type="button" value="Cancel" onclick="logoutForConfigure();" class="btn btn-default btn-block">';
	result=result+'</div>';
	result=result+'</div>';
	result=result+'</div>'; 
	result=result+'</div>';
	$("#configurePopUpDiv").empty();
	$("#configurePopUpDiv").append(result);
	getTimezonesList(); 
}
function checkForOldrecords(){
	//var sourceleadUrl ='${sourceleadUrl}';
	var orglocationId =locationId; // replaced with dashboard variable
	$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	  $.ajax({
	    	type: "POST",
	    	url : sourceleadUrl+"/checkForOldrecords?orglocationId="+orglocationId,
	        success: function(data) {
	        	if(data == true){
	        		  var result = '';
	               	  result=result+'<div class="sideMask" style="display:none"></div>';
	               	  $("#configurePopUpDiv").empty();
	               	  $("#configurePopUpDiv").append(result);
	        	}else{
	        		  var result = '';
	               	  result=result+'<div class="sideMask" style="display:none"></div>';
	               	  $("#configurePopUpDiv").empty();
	               	  $("#configurePopUpDiv").append(result);
	        	}
	        }
	 }); 
}
function validateOrgShortCode(){
	//var sourceleadUrl ='${sourceleadUrl}';
	var orgShortId = $("#generatedUserId").val();
	$('#generatedUserIdDiv').parent().find('p.jquery_form_error_message').remove();
		  $.ajax({
		    	type: "POST",
		    	url : sourceleadUrl+"/validateOrgShortCode?orgShortId="+orgShortId, 
		        success: function(data) {
		        	if(data == true){
		        		$('#generatedUserIdDiv').parent().find('p.jquery_form_error_message').remove();
		        		$("input#generatedUserId").parent().after("<p class='jquery_form_error_message'><span>Org Short Code Already Exists !!!</span>");
		        		$("#saveOrgConfiguration").attr("disabled","disabled");
		        		
		        	}else{
		        		 $('#generatedUserIdDiv').parent().find('p.jquery_form_error_message').remove();
		        		 $("#saveOrgConfiguration").removeAttr("disabled","disabled");
		        	}
		        }
		 }); 
}
$(function(){
	$("#generatedUserId").keypress(function (event) {
        return event.key.length > 1 || event.ctrlKey || !!event.key.match(/[a-zA-Z]/);
    });
});
$(function() {
	  $('#averageWorkingHoursDiv').on('keydown', '#averageWorkingHours', function(e){-1!==$.inArray(e.keyCode,[46,8,9,27,13,110,190])||/65|67|86|88/.test(e.keyCode)&&(!0===e.ctrlKey||!0===e.metaKey)||35<=e.keyCode&&40>=e.keyCode||(e.shiftKey||48>e.keyCode||57<e.keyCode)&&(96>e.keyCode||105<e.keyCode)&&e.preventDefault()});
});
$(function(){
    $("#averageWorkingHours").keyup(function(event){
        var ew = $("#averageWorkingHours").val();
        $('#averageWorkingHoursDiv').parent().find('p.jquery_form_error_message').remove(); 
        if(ew > 24){
        	$("#averageWorkingHours").val('');
        	$("input#averageWorkingHours").parent().after("<p class='jquery_form_error_message'><span>Average Working Hours should be less then or equal to 24</span>");
        }
    });
});
function storeOrgShortCode(){
	 var validationSettings = {
	        errorMessagePosition : 'element',
	        borderColorOnError : '',
			scrollToTopOnError : true,
	   };  
	   		if ($('#configure_form').validate(false, validationSettings)){   
			var frm = $('#configure_form').serializeFormJSON();
			var jsonData = JSON.stringify(frm);
			var form = $('#configure_form')[0];
	  		var formData = new FormData(form);
	   		formData.append("json",jsonData);
		//	var sourceleadUrl = '${sourceleadUrl}';
		 	var locationCode =orgLocationCode; // replaced with dashboard variable.
			$("#saveOrgConfiguration" ).addClass('button').val('Processing..');
	   		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	        $.ajax({
	       	      type: "POST",
	       	      data: formData,
	       	      contentType : 'application/json; charset=utf-8',
	       	      enctype: 'multipart/form-data',
	   	          processData: false,
	   	          contentType:false,
	       	      dataType : 'json',
	            	  url: sourceleadUrl+"/saveOrgShortCodeform?locationCode="+locationCode,
	                  success: function(data) {
	                	  checkForOldrecords();
	      			},  
	             });
	    	    return true;
	    	 }else{
	    		return false;	
	       } 
	         $('#configure_form').showHelpOnFocus();
	         $('#configure_form').validateOnBlur(false, validationSettings); 
	}  
function getTimezonesList(){
//	var sourceleadUrl ='${sourceleadUrl}';
	//var country = '${SourceLeadWeb_orgLocations.country}';
	if(country){
		 $.ajax({
		    	type: "POST",
		    	url : sourceleadUrl+"/getTimezonesList?", 
		        success: function(data) {
		        var json = JSON.parse(data);
		        var output = "";
		        output = output + "<option value=''>--Select--</option>";
		        if(country == 'India'){
	    			output = output + "<option value='246' selected> Asia/Kolkata </option>";
	    		}else if(country == 'United States'){
	    			output = output + "<option value='132' selected> America/Chicago </option>";
	    		}else if(country == 'United Kingdom'){
	    			output = output + "<option value='333' selected> Europe/London </option>";
	    		}else if(country == 'Singapore'){
	    			output = output + "<option value='272' selected> Asia/Singapore </option>";
	    		}else if(country == 'France'){
	    			output = output + "<option value='342' selected> Europe/Paris </option>";
	    		}else if(country == 'Australia'){
	    			output = output + "<option value='298' selected> Australia/Adelaide </option>";
	    		}
		        	for (var i=0; i<json.length; i++){
		        		var opt = json[i];
		        		output = output + "<option value='" +opt.id+ "'>"+opt.timeZoneId+"</option>";
					}
		        	$("#timeZone").empty();
		        	$("#timeZone").append(output);
		        	$("#timeZone").trigger("chosen:updated");
		        }
		 });
	}

}
function getDateFormatByOrgLocation() {
    var locationCode =orgLocationCode; // replaced with dashboard variable.
//	var sourceleadUrl = '${sourceleadUrl}';
   $.ajax({
	      type: "POST",
     	url: sourceleadUrl+"/getDateFormatByOrgLocation?locationCode="+locationCode, 
       	success: function(result) {
				 $("#dateFormatForOrgId").empty();
				$("#dateFormatForOrgId").append("<option value='' disabled selected>-- Select Date Format --</option>");
				if(result == 'dd-MM-yyyy'){
					$("#dateFormatForOrgId").append("<option value='dd-MM-yyyy' selected>dd-MM-yyyy</option>");
				    $('#dateFormatForOrgId').append("<option value='MM-dd-yyyy'>MM-dd-yyyy</option>");
                    $('#dateFormatForOrgId').append("<option value='yyyy-dd-MM'>yyyy-dd-MM</option>");
                    $('#dateFormatForOrgId').append("<option value='yyyy-MM-dd'>yyyy-MM-dd</option>");
				}else if(result == 'MM-dd-yyyy'){
					$("#dateFormatForOrgId").append("<option value='dd-MM-yyyy'>dd-MM-yyyy</option>");
				    $('#dateFormatForOrgId').append("<option value='MM-dd-yyyy' selected>MM-dd-yyyy</option>");
                    $('#dateFormatForOrgId').append("<option value='yyyy-dd-MM'>yyyy-dd-MM</option>");
                    $('#dateFormatForOrgId').append("<option value='yyyy-MM-dd'>yyyy-MM-dd</option>");
				}else if(result == 'yyyy-dd-MM'){
					$("#dateFormatForOrgId").append("<option value='dd-MM-yyyy'>dd-MM-yyyy</option>");
				    $('#dateFormatForOrgId').append("<option value='MM-dd-yyyy'>MM-dd-yyyy</option>");
                    $('#dateFormatForOrgId').append("<option value='yyyy-dd-MM' selected>yyyy-dd-MM</option>");
                    $('#dateFormatForOrgId').append("<option value='yyyy-MM-dd'>yyyy-MM-dd</option>");
				}else if(result == 'yyyy-MM-dd'){
					$("#dateFormatForOrgId").append("<option value='dd-MM-yyyy'>dd-MM-yyyy</option>");
				    $('#dateFormatForOrgId').append("<option value='MM-dd-yyyy'>MM-dd-yyyy</option>");
                    $('#dateFormatForOrgId').append("<option value='yyyy-dd-MM'>yyyy-dd-MM</option>");
                    $('#dateFormatForOrgId').append("<option value='yyyy-MM-dd' selected>yyyy-MM-dd</option>");
				}else{
					$("#dateFormatForOrgId").append("<option value='dd-MM-yyyy'>dd-MM-yyyy</option>");
				    $('#dateFormatForOrgId').append("<option value='MM-dd-yyyy'>MM-dd-yyyy</option>");
                    $('#dateFormatForOrgId').append("<option value='yyyy-dd-MM'>yyyy-dd-MM</option>");
                    $('#dateFormatForOrgId').append("<option value='yyyy-MM-dd'>yyyy-MM-dd</option>");
				}
				
				$('#dateFormatForOrgId').trigger("chosen:updated"); 
        },  
   });
   $(function(){
	    var config = {
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			}
	    $("#c2owner").chosen();
	    $('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});
		  })
	});
}
function assignTasksList() {
	//var sourceleadUrl ='${sourceleadUrl}';
	var date = $("#dasboardTaskDatpicker").val();
	var status =  "myTask";
	
	$.ajax({
    	type: "POST",
        url: sourceleadUrl+"/getTasksList?status="+status+"&date="+date,
        success: function(data) {
        	 var json = JSON.parse(data);
        	 var result = "";
        	 result = result + '<table id="dasboardTasksPagination">'; 
        	 result = result + '<thead>';
         	result = result + '<tr>';
         	result = result + '<th>Subject</th>';
         	result = result + '<th>Date &amp; Time</th>';
         	result = result + '<th>Priority</th>';
         	result = result + '<th>Status</th>';
         	result = result + ' </tr>';
        	result = result + ' </thead>';
        	result = result + ' <tbody>';
        	for (var i=0; i<json.length; i++)
			{
        		var report = json[i];
        		
        		if(report.taskDateStr != null && report.taskDateStr != '' && report.taskDateStr != 'undefined'){
        			taskDateStr = report.taskDateStr
        		}else{
        			taskDateStr = "--";
        		}
        		if(report.taskTime != null && report.taskTime != '' && report.taskTime != 'undefined'){
        			taskTime = report.taskTime;
        		}else{
        			taskTime = "";
        		}
        		if(report.priority != null && report.priority != '' &&  report.priority != 'undefined'){
        			priority = report.priority;
        		}else{
        			priority = "--";
        		}
        		
        		result = result + '<tr>';
        		result = result + ' <td><div title="'+report.subject+'"><span>'+report.subject+'</span></div></td>';
        		result = result + '<td><div class="text-center">'+taskDateStr+'  '+taskTime+'</div></td>';
        		result = result + '<td>'+priority+'</td>';
        		result = result + ' <td>'+report.taskStatus+'</td>';
        		result = result + ' </tr>';
               
			}
        	 result = result + '     </tbody>';
        	 result = result + '   </table>';
        	 $("#assignTaskList").empty();
           	 $("#assignTaskList").append(result); 
           	 if(json.length > 0){
           		$("#assignTaskListDiv").css("display", "block");
           	 }
           	 
           	dasboardTasksPagination();
        }
	});
	
}
function dasboardTasksPagination(){
    $('#dasboardTasksPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[5, 10, 15, -1], [5, 10, 15, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
}
$("#selector").fadeIn(300);
$('#selector').delay(3000).fadeOut('slow');
$("#selector1").fadeIn(300);
$('#selector1').delay(3000).fadeOut('slow');
// End dashboard Scripts here.
// new project reuest start here.
function getNewProjectResourceReqestsListCount(){
	 //var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getProjectNewResourceRequestsCounts?", 
	        success: function(json) {
	        	$("#newWaitingCount").html(json.waitingCount);
	        	$("#newApprovedCount").html(json.approvedCount);
	        	$("#newRejectedCount").html(json.rejectedCount);
	        	$("#newOnholdCount").html(json.onholdCount);
	        }
	   
    });
}

function newResourceRequestList(requestType){
	getNewProjectResourceReqestsListCount();
	  $('#newResourceRequestTabDiv').show();
	  $('#assocaiteRequestsTabDiv').hide();
	  $("#budgetTabDiv").hide();
	  $("#associateDiv").removeClass("active");
	  $("#newResourceDiv").addClass("active");
	  $("#budgetDiv").removeClass("active");
	    var result = "";
	 	result = result +" <div class='remove_search'>";
	 	result = result +" <input type='text' class='repSearchBar' placeholder='New Resource search' id='newRequestAssociatesTableSearch'/>";
	 	result = result +"</div>";
	 	
	    result = result +'<div class="clearfix popupFilter mt5">';
  		result = result +'<div class="clearfix popupFilter datatable_filter">'; 
	 	
  		result = result +'<table id="newRequestAssociates" class="display" cellspacing="0" width="100%"  >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result +'<th>Request Id</th>';
		result = result +'<th>Project</th>';
		result = result +'<th>Role</th>';
		result = result +'<th>Initiator</th>';
		result = result +'<th>Approver</th>';
		result = result +'<th>Consulting</th>';
		result = result +'<th>Actions</th>';
		result = result + '</tr>';
		result = result + '</thead>	';
	    result = result +"</table>";
	    
	    result = result +'</div>'; 
    	result = result +'<div class="clearfix">';
    	result = result +'</div>'; 
    	result = result +'</div>';
    	 
	    $("#newResourceRequestList").empty();
	    $("#newResourceRequestList").append(result);
	    newRequestAssociatesDataTableList(requestType);
}

function newRequestAssociatesDataTableList(requestType){
	// var sourceleadUrl ='${sourceleadUrl}';
	 //var approverUserId = '${SourceLeadWeb_user.id}';
	 var orgLocationId =  locationId; // replcaced with dashboard variable.
	 
	 var searchValue = $("#newRequestAssociatesTableSearch").val();
	 searchValue = searchValue ? searchValue : "";
	 var url = sourceleadUrl+"/getProjectPlanningNewResourceRequestList?searchValue="+searchValue+"&resourceStatus="+requestType;
	 newResourceRequestPagination(url,requestType,sourceleadUrl);
	 
	   if(requestType == 'Waiting For Approval'){
	    	 $("#newReqWaiting").addClass("activeIcon");
	    	 $("#newRequestApproved").removeClass("activeIcon");
	    	 $("#newRequestRejected").removeClass("activeIcon");
	    	 $("#newRequestOnhold").removeClass("activeIcon");
	    }
	   
	    else if(requestType == 'Approved'){
	    	 $("#newRequestApproved").addClass("activeIcon");
	    	 $("#newReqWaiting").removeClass("activeIcon");
	    	 $("#newRequestRejected").removeClass("activeIcon");
	    	 $("#newRequestOnhold").removeClass("activeIcon");
	    }
	    
	    else if(requestType == 'Rejected'){
	    	 $("#newRequestRejected").addClass("activeIcon");
	    	 $("#newReqWaiting").removeClass("activeIcon");
	    	 $("#newRequestApproved").removeClass("activeIcon");
	    	 $("#newRequestOnhold").removeClass("activeIcon");
	    }
	    
	    else if(requestType == 'On hold'){
	    	 $("#newRequestOnhold").addClass("activeIcon");
	    	 $("#newReqWaiting").removeClass("activeIcon");
	    	 $("#newRequestApproved").removeClass("activeIcon");
	    	 $("#newRequestRejected").removeClass("activeIcon");
	    	
	    }
	    
}
function newResourceRequestPagination(url,requestType,sourceleadUrl){
	var colIndex = 0;
    $('#newRequestAssociates').DataTable( { 
	  "processing": true,
      "serverSide": true,
      "searching": false,
      "ajax": $.fn.dataTable.pipeline( {
          url: url,
          method: 'GET',
          pages: 2
        }),
        "columns": [
        	{ "data": "projectPlanningRequestId" },
            { "data": "projectName" },
            { "data": "roleName"},
            { "data": "requestedUser"},
            { "data": "approverName"},
            { "data": "consultingType"},
            { "data": ""}
           
        ],
        "columnDefs": [ {
    	  	"targets": colIndex++,
    	  	"data": "projectPlanningRequestId",
    	  	"render": function(data, type, opt, meta) {
    	  		 var result = "";
    	  		 var flag = false;
    	  		 result = result + '<td><div  class="tbldata-dotdot-vsm"><a href="'+sourceleadUrl+'/viewNewResourceRequestDashborad?requestUUID='+opt.projectPlanningRequestUUID+'">'+opt.projectPlanningRequestId+'</a></div></td>';
	              return result;
    	  	}
         },{
	    	  	"targets": colIndex++,
	    	  	"data": "projectName",
	    	  	"render": function(data, type, opt, meta) {
	    	  		var result = "";
	    	  	    result = result + '<td><div  class="tbldata-dotdot-sm"><a href="'+sourceleadUrl+'/projectSummary?projectUUID='+opt.projectUUID+'&projectClientUUID='+opt.projectClientUUID+'&tabType=resources" title="'+opt.projectName+'">'+opt.projectName+'</a></div></td>';
		            return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "candidateName",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  result = result + '<td data-title="Id"><div class="tblminwidth-75" title="'+opt.roleName+'">'+opt.roleName+'</div></td>';
	              return result;
              		
              }
	      
	          },{
		    	  "targets": colIndex++,
	              "data": "requestedUser",
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            		result = result + '<td><div class="tblminwidth-75"><span title="'+opt.requestedUser+'">'+opt.requestedUser+'</span></div></td>';
	            		return result;
	              }
		     },{
		    	  "targets": colIndex++,
	              "data": "approverName",
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            		result = result + '<td><div class="tblminwidth-75"><span title="'+opt.approverName+'">'+opt.approverName+'</span></div></td>';
	            		return result;
	              }
		     },{
		    	  "targets": colIndex++,
	              "data": "consultingType",
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            		result = result + '<td><div class="tblminwidth-75"><span title="'+opt.consultingType+'">'+opt.consultingType+'</span></div></td>';
	            		return result;
	              }
		     } ,{
		    	  "targets": colIndex++,
	              "data": "",
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    
	            	    result = result + '<td id="newResourceAction'+opt.requestUUID+'"><div class="tblminwidth-100">';
			    		if(opt.resourceStatus == 'Waiting For Approval' || opt.resourceStatus == 'On hold'){
			    			
			    			if(opt.resourceStatus == 'On hold'){
			    			  result = result +"<i class='glyphicon glyphicon-comment edit-reason colB'><div class='edit-status'><p>"+opt.rejectedReason+"</p></div></i>&nbsp;|&nbsp;";
			    			  result = result+" <br><span class='rejNam' title='"+opt.approvedUser+"'>By : "+opt.approvedUser+"</span>";
			    			}
			    			result = result + '<a href="javascript:void(0)" onclick="acceptNewAssociate(\''+opt.requestUUID+'\',\''+requestType+'\');"><i class="fa fa-check text-success" alt="Approve" title="Approve"></i></a>&nbsp;|&nbsp;';
				    		result = result + '<a href="javascript:void(0)" onclick="rejectNewAssociate(\''+opt.requestUUID+'\',\''+opt.roleName+'\');"><i class="fa fa-times text-danger" alt="Reject" title="Reject"></i></a>';
				    		
				    		if(opt.resourceStatus == 'Waiting For Approval'){
				    			result = result + '&nbsp;|&nbsp;<a href="javascript:void(0)" onclick="onholdNewAssociateRequest(\''+opt.requestUUID+'\',\''+opt.requestedUser+'\',\''+requestType+'\');"><i class="fa fa-clock-o text-primary" alt="On hold" title="On hold"></i></a>';
				    		}
				    		
			    		}else{
			    			if(opt.resourceStatus == 'Rejected'){
			    				result = result +"<i class='glyphicon glyphicon-comment colB edit-reason'><div class='edit-status'><p>"+opt.rejectedReason+"</p></div></i>";
			    				result = result + '<b class="bg-red">'+opt.resourceStatus+'</b>';
			    				result = result+" <br><span class='rejNam' title='"+opt.approvedUser+"'>By : "+opt.approvedUser+"</span>";
			    			}else{
			    				result = result + '<b class="bg-green">'+opt.resourceStatus+'</b>';
			    				result = result+" <br><span class='rejNam' title='"+opt.approvedUser+"'>By : "+opt.approvedUser+"</span>";
			    			}
			    			
			    		}
			    		
			    		result = result + '</div></td>';
			    		return result;
	              }
		     } ],
		     
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"aaSorting": [[1,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": false,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
    } );
    $("#newRequestAssociates").wrap("<div class='clearfix require_wrap_table'></div>"); 
    $("input[type=search]").hide();
    $("#newRequestAssociatesTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   newRequestAssociatesDataTableList(requestType);
    	   }
    	});
}
function acceptNewAssociate(requestUUID,requestType){
	 //var userId = '${SourceLeadWeb_userId}';
    var orglocationId =  locationId; // replcaced with dashboard variable.
	// var sourceleadUrl ='${sourceleadUrl}';
	 $("#requestedNewAssociatesDivLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 $.ajax({
		    type : "POST",
	    	url: sourceleadUrl+"/acceptNewRequestedResource?requestUUID="+requestUUID+"&userId="+userId+"&orglocationId="+orglocationId, 
	        success: function(result) {
	        	$("#newResourceAction"+requestUUID).html("<span>Accepted</span>");
	        	getNewProjectResourceReqestsListCount();
	        	newResourceRequestList(requestType);
	        	$("#requestedNewAssociatesDivLoading").removeAttr("style"); 
	        },
	    });
}

function rejectNewAssociate(requestUUID,roleName){
	
	var result = "";
	result = result + '	<div class="settPop">';
	result = result + '	<div id="loading"></div>';
	result = result + '<form id="newResourceRejectedReason_form" name="newResourceRejectedReason_form" method="post">';
	result = result + '<input type="hidden" name="requestUUID" value="'+requestUUID+'">';
	result = result + '	<h3>New Resource Rejected Reason<i class="fa fa-times" onclick="cancelRejectedReason();"></i></h3>  ';
	result = result + '	<div class="settPopInn"> ';
	
	result = result + '		<div class="clearfix">  ';
	result = result + '		<div class="col-md-12">  ';
	result = result + '		  <label class="col-md-2 noPad" for="Role Name">Role :</label> ';
	result = result + '		  <label class="col-md-8 paddingRight">'+roleName+'</label> ';
	result = result + '		</div>  ';

	result = result + '		  <label class="col-md-12 mt10" for="Project Id">Reason</label> ';
	result = result + '		  <div class="col-md-12 taskdesp">   ';
	result = result + '			<textarea class="form-control" name="rejectedReason" id="rejectedReason" field-name="Reason" data-validation="required  validate_length length2-500" rows="2"></textarea>';
	result = result + '			<span class="hidden"></span> </div>  ';
	result = result + '		</div>';
	result = result + '		<div class="col-md-12 mt5 clearfix"> ';
	result = result + '		<div class="pull-right">  ';
	result = result + '		  <input type="button" value="Save" id="submit_rejectedReason"  onclick="saveRejectNewAssociate(\''+requestUUID+'\');" class="btn btn-primary" >';
	result = result + '		  <input type="button" value="Cancel" class="btn btn-default ml10" onclick="cancelRejectedReason();">';
	result = result + '		</div>';
	result = result + '		</div>';
	result = result + '		<div class="clearfix"></div>   ';
	result = result + '	</div>  ';
	result = result + '</form>';
	result = result + '	</div>';
    $("#resourceRejectedReasonDiv").empty();
    $("#resourceRejectedReasonDiv").append(result);
    
    $(".settPop").slideDown(200);
	$(".vendor_wrap_mask").fadeIn(200);
}
function cancelRejectedReason(){
	$(".settPop").slideUp(200);
	$(".vendor_wrap_mask").fadeOut(200);
}
function cancelRequestOnholdReason(){
	$(".settPop").slideUp(200);
	$(".vendor_wrap_mask").fadeOut(200);
	$("#requestOnHoldReasonDiv").empty();
}
function cancelRequestOnholdReason(){
	$(".settPop").slideUp(200);
	$(".vendor_wrap_mask").fadeOut(200);
	$("#requestOnHoldReasonDiv").empty();
}
function saveRejectAssociate(requestUUID){
	
	if ($('#rejectedReason_form').validate(false, validationSettings)){ 
	  	
		var frm = $('#rejectedReason_form').serializeFormJSON();
		var jsonData = JSON.stringify(frm);
		
	    var form = $('#rejectedReason_form')[0];
	    var formData = new FormData(form);
	  	formData.append("json",jsonData)
	  	/// var userId = '${SourceLeadWeb_userId}';
        var orglocationId =  locationId; // replcaced with dashboard variable.
       // var sourceleadUrl ='${sourceleadUrl}';
      
        $("#submit_rejectedReason").attr("disabled", "disabled");
		
        $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 	 $.ajax({
	 		    type : "POST",
	    	    data: formData,
	    	    contentType : 'application/json; charset=utf-8',
		        processData: false,
		        contentType:false, 
	    	    dataType : 'json',
		    	url: sourceleadUrl+"/rejectRequestedAssociate?userId="+userId+"&orglocationId="+orglocationId, 
		        success: function(result) {
		        	cancelRejectedReason();
		        	$("#action"+requestUUID).html("<span>Rejected</span>")
		        	getProjectResourceReqestsListCount();
		        	resourceRequestList("Waiting For Approval");
		        	$("#loading").removeAttr("style"); 
		        },
		    }); 
      return true; 
		
	}else{
		return false;
	  }
	
	  $('body').on('blur', '#rejectedReason_form', function() {
	   		$("#rejectedReason_form").showHelpOnFocus();
	   		$("#rejectedReason_form").validateOnBlur(false, validationSettings);
	   	});
	
}
function acceptAssociateRequest(requestUUID){
	// var sourceleadUrl ='${sourceleadUrl}';
	// var userId = '${SourceLeadWeb_userId}';
    var orglocationId =  locationId; // replcaced with dashboard variable.
	 $("#resourceRequestsDivLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 $.ajax({
		    type : "POST",
	    	url: sourceleadUrl+"/acceptAssociateRequest?requestUUID="+requestUUID+"&userId="+userId+"&orglocationId="+orglocationId, 
	        success: function(result) {
	        	location.replace(sourceleadUrl+"/dashboard/"+orglocationId);  
	        },
	    });
	 
}

function saveRejectNewAssociate(requestUUID){
	
	if ($('#newResourceRejectedReason_form').validate(false, validationSettings)){ 
	  	
		var frm = $('#newResourceRejectedReason_form').serializeFormJSON();
		var jsonData = JSON.stringify(frm);
		
	    var form = $('#newResourceRejectedReason_form')[0];
	    var formData = new FormData(form);
	  	formData.append("json",jsonData)
	  	// var userId = '${SourceLeadWeb_userId}';
        var orglocationId =  locationId; // replcaced with dashboard variable.
       // var sourceleadUrl ='${sourceleadUrl}';
      
        $("#submit_rejectedReason").attr("disabled", "disabled");
		
        $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 	 $.ajax({
	 		    type : "POST",
	    	    data: formData,
	    	    contentType : 'application/json; charset=utf-8',
		        processData: false,
		        contentType:false, 
	    	    dataType : 'json',
		    	url: sourceleadUrl+"/rejectNewRequestedResource?userId="+userId+"&orglocationId="+orglocationId, 
		        success: function(result) {
		        	cancelRejectedReason();
		        	$("#newResourceAction"+requestUUID).html("<span>Rejected</span>")
		        	getNewProjectResourceReqestsListCount();
		        	newResourceRequestList("Waiting For Approval");
		        	$("#loading").removeAttr("style"); 
		        },
		    }); 
      return true; 
		
	}else{
		return false;
	  }
	
	  $('body').on('blur', '#newResourceRejectedReason_form', function() {
	   		$("#newResourceRejectedReason_form").showHelpOnFocus();
	   		$("#newResourceRejectedReason_form").validateOnBlur(false, validationSettings);
	   	});
	
}
(function ($) {
	   $.fn.serializeFormJSON = function () {

	       var o = {};
	       var a = this.serializeArray();
	       $.each(a, function () {
	           if (o[this.name]) {
	               if (!o[this.name].push) {
	                   o[this.name] = [o[this.name]];
	               }
	               o[this.name].push(this.value || '');
	           } else {
	               o[this.name] = this.value || '';
	           }
	       });
	       return o;
	   };
	})(jQuery);

var validationSettings = {
        errorMessagePosition : 'element',
        borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd' 
        };

function onholdNewAssociateRequest(requestUUID,Initiator,requestType){
	var result = "";
	result = result + '	<div class="settPop">';
	result = result + '	<div id="loading"></div>';
	result = result + '<form id="newRequestOnHold_form" name="newRequestOnHold_form" method="post">';
	result = result + '<input type="hidden" name="requestUUID" value="'+requestUUID+'">';
	result = result + '	<h3>Associate Request On Hold Reason<i class="fa fa-times" onclick="cancelRequestOnholdReason();"></i></h3>  ';
	result = result + '	<div class="settPopInn"> ';
	
	result = result + '		<div class="clearfix">  ';
	result = result + '		<div class="col-md-12">  ';
	result = result + '		  <label class="col-md-2 noPad" for="Initiator Name">Initiator :</label> ';
	result = result + '		  <label class="col-md-8 paddingRight">'+Initiator+'</label> ';
	result = result + '		</div>  ';

	result = result + '		  <label class="col-md-12 mt10" for="Project Id">Reason</label> ';
	result = result + '		  <div class="col-md-12 taskdesp">   ';
	result = result + '			<textarea class="form-control" name="onholdReason" id="onholdReason" field-name="Reason" data-validation="required  validate_length length2-500" rows="2"></textarea>';
	result = result + '			<span class="hidden"></span> </div>  ';
	result = result + '		</div>';
	result = result + '		<div class="col-md-12 mt5 clearfix"> ';
	result = result + '		<div class="pull-right">  ';
	result = result + '		  <input type="button" value="Save" id="submit_onHold"  onclick="saveOnHoldNewRequest(\''+requestUUID+'\',\''+requestType+'\');" class="btn btn-primary" >';
	result = result + '		  <input type="button" value="Cancel" class="btn btn-default ml10" onclick="cancelRequestOnholdReason();">';
	result = result + '		</div>';
	result = result + '		</div>';
	result = result + '		<div class="clearfix"></div>   ';
	result = result + '	</div>  ';
	result = result + '</form>';
	result = result + '	</div>';
    $("#newRequestOnHoldReasonDiv").empty();
    $("#newRequestOnHoldReasonDiv").append(result);
    
    $(".settPop").slideDown(200);
	$(".vendor_wrap_mask").fadeIn(200);
	 
}
function saveOnHoldNewRequest(requestUUID,requestType){
	
	if ($('#newRequestOnHold_form').validate(false, validationSettings)){ 
		  	
			var frm = $('#newRequestOnHold_form').serializeFormJSON();
			var jsonData = JSON.stringify(frm);
		    var form = $('#newRequestOnHold_form')[0];
		    var formData = new FormData(form);
		  	formData.append("json",jsonData)
		  	
	      //  var sourceleadUrl ='${sourceleadUrl}';
	        //var userId = '${SourceLeadWeb_userId}';
	        var orglocationId =  locationId; // replcaced with dashboard variable.
	        
	        $("#submit_onHold").attr("disabled", "disabled");
	        $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 	 	 $.ajax({
		 		    type : "POST",
		    	    data: formData,
		    	    contentType : 'application/json; charset=utf-8',
			        processData: false,
			        contentType:false, 
		    	    dataType : 'json',
			    	url: sourceleadUrl+"/onholdRequestedNewAssociate?requestUUID="+requestUUID+"&userId="+userId+"&orglocationId="+orglocationId,
			        success: function(result) {
			        	
			        	cancelRequestOnholdReason();
			        	$("#newResourceAction"+requestUUID).html("<span>Onhold</span>")
			        	getNewProjectResourceReqestsListCount();
			        	newResourceRequestList(requestType);
			        	$("#loading").removeAttr("style"); 
			        },
			    });  
	      return true; 
			
		}else{
			return false;
		  }
		
		  $('body').on('blur', '#newRequestOnHold_form', function() {
		   		$("#newRequestOnHold_form").showHelpOnFocus();
		   		$("#newRequestOnHold_form").validateOnBlur(false, validationSettings);
		   	});
	}


function cancelRequestRejectedReason(){
	$(".settPop").slideUp(200);
	$(".vendor_wrap_mask").fadeOut(200);
}

function saveRejectAssociate(requestUUID){
	
	if ($('#rejectedReason_form').validate(false, validationSettings)){ 
	  	
		var frm = $('#rejectedReason_form').serializeFormJSON();
		var jsonData = JSON.stringify(frm);
		
	    var form = $('#rejectedReason_form')[0];
	    var formData = new FormData(form);
	  	formData.append("json",jsonData)
	  	 //var userId = '${SourceLeadWeb_userId}';
        var orglocationId = locationId; // replcaced with dashboard variable.
       // var sourceleadUrl ='${sourceleadUrl}';
      
        $("#submit_rejectedReason").attr("disabled", "disabled");
		
        $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 	 $.ajax({
	 		    type : "POST",
	    	    data: formData,
	    	    contentType : 'application/json; charset=utf-8',
		        processData: false,
		        contentType:false, 
	    	    dataType : 'json',
		    	url: sourceleadUrl+"/rejectRequestedAssociate?userId="+userId+"&orglocationId="+orglocationId, 
		        success: function(result) {
		        	cancelRejectedReason();
		        	$("#action"+requestUUID).html("<span>Rejected</span>")
		        	getProjectResourceReqestsListCount();
		        	resourceRequestList("Waiting For Approval");
		        	$("#loading").removeAttr("style"); 
		        },
		    }); 
      return true; 
		
	}else{
		return false;
	  }
	
	  $('body').on('blur', '#rejectedReason_form', function() {
	   		$("#rejectedReason_form").showHelpOnFocus();
	   		$("#rejectedReason_form").validateOnBlur(false, validationSettings);
	   	});
	
}
// budget request start here=========
function getBudgetRequestListCount(){
	 //var sourceleadUrl ='${sourceleadUrl}';
	// var approverUserId = '${SourceLeadWeb_user.id}';
	 var orgLocationId =  locationId; // replcaced with dashboard variable.
	 $.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getBudgetRequestListCount?approverUserId="+approverUserId+"&orgLocationId="+orgLocationId, 
	        success: function(json) {
	        	$("#budgetWaitingCount").html(json.waitingCount);
	        	$("#budgetApprovedCount").html(json.approvedCount);
	        	$("#budgetRejectedCount").html(json.rejectedCount);
	        	$("#budgetOnholdCount").html(json.onholdCount);
	        }
	   
    });
}
function getBudgetRequestList(requestType){
	  getBudgetRequestListCount();
	
	  $('#newResourceRequestTabDiv').hide();
	  $('#assocaiteRequestsTabDiv').hide();
	  $("#budgetTabDiv").show();
	  $("#associateDiv").removeClass("active");
	  $("#newResourceDiv").removeClass("active");
	  $("#budgetDiv").addClass("active");
	  
	 //var sourceleadUrl ='${sourceleadUrl}';
	// var approverUserId = '${SourceLeadWeb_user.id}';
	 var orgLocationId =  locationId; // replcaced with dashboard variable.
	
	// var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  resListDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  resListDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  resListDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  resListDate = 'YYYY-DD-MM'; 
	  }  
	 $.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getBudgetRequsetListByBudgetStatusAndOrgLocationId?approverUserId="+approverUserId+"&orgLocationId="+orgLocationId+"&requestType="+requestType, 
	        success: function(json) {
	        	var result = "";
	        	
	      		
	    		result = result + '		<div class="project_tabs_inn_table datatable_filter overflow_inherint mrgTp dataSeaNew">';
	    		result = result + '		<table id="budgetRequestsPaginationList" cellspacing="0" width="100%">';
	    		result = result + '	    <thead>';
	    		result = result + '	     	<tr>';
	    		result = result + '	         	<th>Request Id</th>';
	    		result = result + '	             <th>Description</th>';
	    		result = result + '	             <th>Project</th>';
	    		result = result + '	             <th>Est Hours</th>';
	    		result = result + '	             <th>Est Budget</th>';
	    		result = result + '	             <th>Initiator</th>';
	    		result = result + '	             <th>Date<br>(<small class="custom-small">'+resListDate+'</small>)</th>';
	    		result = result + '	             <th>Action</th>';
	    		result = result + '	         </tr>';
	    		result = result + '	    </thead>';
	    		result = result + '	 	<tbody>';
	    		
	    		 for (var i=0; i<json.length; i++){
	    			var report = json[i];
	    			if(report.estimatedBudgetValue != null  && report.estimatedBudgetValue != "" && report.estimatedBudgetValue != "undefined"){
	    				estimatedBudgetValue = report.estimatedBudgetValue;
	    			}else{
	    				estimatedBudgetValue = "--";
	    			}
	    			if(report.estimatedHours != null && report.estimatedHours != "" && report.estimatedHours !="undefined"){
	    				estimatedHours = report.estimatedHours;
	    			}else{
	    				estimatedHours = "--";
	    			}
	    			if(report.budgetDescription != null && report.budgetDescription != "" && report.budgetDescription !="undefined"){
	    				budgetDescription = report.budgetDescription;
	    			}else{
	    				budgetDescription = "--";
	    			}
	    			result = result + '<tr>';
		    		
	    			if((report.approverUserIdlLong == userId) && (requestType == "On Hold" || requestType == "Rejected" || requestType == "Waiting For Approval")){
	    				result = result + ' 	<td><div class="tbldata-dotdot-vsm"> <div class="tbldata-dotdot-100 pull-left">'+report.budgetRequestedId+'</div> <i class="fa fa-pencil ml10 pull-right" onclick="editBudgetrequest(\''+report.id+'\',\''+requestType+'\')"></i></div></td>';
	    			}else{
	    				result = result + '  <td><div class="tbldata-dotdot-vsm"> <div class="tbldata-dotdot-100 pull-left">'+report.budgetRequestedId+'</div></div></td>';
	    			}
	    			
	    			result = result + '     <td><div class="tbldata-dotdot-sm" title="'+budgetDescription+'">'+budgetDescription+'</div></td>';
	    			result = result + '     <td><div class="tbldata-dotdot-sm" title="'+report.projectName+'">'+report.projectName+'</div></td>';
	    			result = result + '     <td><div  class="tbldata-dotdot_80">'+estimatedHours+'</div></td>';
	    			result = result + '     <td><div class="tbldata-dotdot_80" title="'+estimatedBudgetValue+'">'+estimatedBudgetValue+'</div></td>';
					result = result + '     <td><div  class="tbldata-dotdot-vsm" title="'+report.createdUserName+'">'+report.createdUserName+'</div></td>';
					result = result + '     <td><div  class="tblminwidth-100">'+report.createdStr+'</div></td>';
					
					if(requestType == 'Waiting For Approval'){
						result = result + '     <td><div class="tblminwidth-90">';
						result = result + '     <i class="fa fa-check text-success" alt="Approve" title="Approve" onclick="acceptBudgetRequset(\''+report.id+'\',\''+requestType+'\')"></i>|';
						result = result + '     <i class="fa fa-times text-danger" alt="Rejected" title="Reject" onclick="deleteBudgetRequaet(\''+report.id+'\',\''+requestType+'\');"></i>|';
						result = result + '     <i class="fa fa-clock-o text-primary" alt="On hold" title="On hold" onclick="onholdBudgetRequaet(\''+report.id+'\',\''+requestType+'\');"></i></div></td>';
					}
					
					if(requestType == "Rejected"){
						result = result + '<td><div class="tblminwidth-90 paTp"><b class="Rejec">Rejected</b>';
						 result = result+"  <i class='glyphicon glyphicon-comment colB' id='"+report.id+"' onclick='rejectedMessage(\""+report.rejectedReason+"\");'>";
					 	 result = result+" <div class='app_error_mes' id='rejectedMessage"+report.id+"'>"+report.rejectedReason+"</div></i>";
					 	 result = result+" <br><span class='rejNam' title='"+report.updatedUserName+"'>By : "+report.updatedUserName+"</span>";
						result = result + '</div></td>';
					}
					if(requestType == "Approved"){
						result = result + '<td><div class="tblminwidth-90 paTp"><b>Approved</b>';
						 result = result+" <br><span class='rejNam' title='"+report.updatedUserName+"'>By : "+report.updatedUserName+"</span>";
						 result = result + '</div></td>';
					}
					
					
					
					if((report.approverUserIdlLong ==  userId) &&  requestType == "On Hold"){
					
						result = result + '     <td><div class="tblminwidth-75 paTp">';
						 result = result+"  <i class='glyphicon glyphicon-comment colB' id='"+report.id+"' onclick='rejectedMessage(\""+report.rejectedReason+"\");'>";
					 	 result = result+" <div class='app_error_mes' id='rejectedMessage"+report.id+"'>"+report.rejectedReason+"</div></i>|";
					 	result = result + '     <i class="fa fa-check text-success" alt="Approve" title="Approve" onclick="acceptBudgetRequset(\''+report.id+'\',\''+requestType+'\')"></i>|';
						result = result + '     <i class="fa fa-times text-danger" alt="Rejected" title="Reject" onclick="deleteBudgetRequaet(\''+report.id+'\',\''+requestType+'\');"></i>';
						result = result + '     </div></td>';
												
						
					}
					if(requestType == "On Hold" && (report.approverUserIdlLong !=  userId)){
						result = result + '<td><div class="tblminwidth-90 paTp"><b class="processs-hold">On Hold</b>';
						 result = result+"  <i class='glyphicon glyphicon-comment colB' id='"+report.id+"' onclick='rejectedMessage(\""+report.rejectedReason+"\");'>";
					 	 result = result+" <div class='app_error_mes' id='rejectedMessage"+report.id+"'>"+report.rejectedReason+"</div></i>";
					 	 result = result+" <br><span class='rejNam' title='"+report.updatedUserName+"'>By : "+report.updatedUserName+"</span>";
						result = result + '</div></td>';
					}
					
					
	    			result = result + '</tr>';
	    		} 
	    		
	    		
	    		result = result + '	     </tbody>';
	    		result = result + '	 </table>';
	    		result = result + '</div>';
	    	    $("#budgetRequestListDiv").empty();
	    	    $("#budgetRequestListDiv").append(result); 
	    	    budgetRequsetPagination();
	    	    
	    	    if(requestType == 'Waiting For Approval'){
	    	    	 $("#budgetWaiting").addClass("activeIcon");
	    	    	 $("#budgetApproved").removeClass("activeIcon");
	    	    	 $("#rejected").removeClass("activeIcon");
	    	    	 $("#budgetOnhold").removeClass("activeIcon");
	    	    }
	    	   
	    	    else if(requestType == 'Approved'){
	    	    	 $("#budgetApproved").addClass("activeIcon");
	    	    	 $("#budgetWaiting").removeClass("activeIcon");
	    	    	 $("#budgetRejected").removeClass("activeIcon");
	    	    	 $("#budgetOnhold").removeClass("activeIcon");
	    	    }
	    	    
	    	    else if(requestType == 'Rejected'){
	    	    	 $("#budgetRejected").addClass("activeIcon");
	    	    	 $("#budgetWaiting").removeClass("activeIcon");
	    	    	 $("#budgetApproved").removeClass("activeIcon");
	    	    	 $("#budgetOnhold").removeClass("activeIcon");
	    	    }
	    	    
	    	    else if(requestType == 'On Hold'){
	    	    	 $("#budgetOnhold").addClass("activeIcon");
	    	    	 $("#budgetWaiting").removeClass("activeIcon");
	    	    	 $("#budgetApproved").removeClass("activeIcon");
	    	    	 $("#budgetRejected").removeClass("activeIcon");
	    	    	
	    	    }
	    	    
	        }
	   
    });
		 
}

$(document).on("mouseover", ".glyphicon-comment", function() { 
	var localId = $(this).attr('id');
	$("#rejectedMessage"+localId).addClass('app_error_mes_active');
	});	
	$(document).on("mouseleave", ".glyphicon-comment", function() { 
	var localId = $(this).attr('id');
	$("#rejectedMessage"+localId).removeClass('app_error_mes_active');
});	
	
function rejectedMessage(local){ 
	   $("#"+local).slideToggle(200);
	   $(".timesheet_appr_table .glyphicon strong, .timesheet_appr_table .glyphicon").click(function(e) {
	       e.stopPropagation();
	   });	
	   
	}
	
function acceptBudgetRequset(budgetRequestedId,requestType){
	//var sourceleadUrl = '${sourceleadUrl}';
	//var userId = '${SourceLeadWeb_user.id}';
	 $.ajax({
				type : "POST",
				url : sourceleadUrl+"/acceptBudgetRequest?budgetRequestedId="+budgetRequestedId+"&userId="+userId,
				success : function(result) {
					getBudgetRequestList(requestType);
					getBudgetRequestListCount();
				},
			}); 
}
function deleteBudgetRequaet(budgetRequestedId,requestType){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>"; 
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"<form id='deleteBudget_form' name='deleteBudget_form' >";
	result = result +"<input type='hidden' id='budgetRequestedId' name='budgetRequestedId' value='"+budgetRequestedId+"'>";
	result = result +"<input type='hidden' id='loginUserid' name='loginUserid' value="+userId+">";
	result = result +"		<p>Are you sure you want to reject ?</p>";
	result = result +"		<textarea  id='reasonForProjectBudget' name='reasonForProjectBudget'  placeholder='Comments here' field-name='Reason' data-validation='required validate_length length2-5000'></textarea>";
	result = result +"</form>";
	result = result +'		<b href="javascript:void(0)"><input id="delete_budget-id" type="button" value="Delete" onclick="saveBudgetReason(\''+budgetRequestedId+'\',\''+requestType+'\');"><input class="u_can_color" type="button"  value="Cancel" onClick="cancelTermination();"></b>';
	result = result +"	</div>";

	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".vendor_wrap_mask").fadeIn(200);

}
function cancelTermination(){
	$("#popUp").empty();
	 $(".vendor_wrap_mask").fadeOut(200);
}
function saveBudgetReason(budgetRequestedId,requestType){
	if ($('#deleteBudget_form').validate(false, validationSettings)) {
	//var sourceleadUrl = '${sourceleadUrl}';
    $("#delete_budget").attr("disabled", "disabled");
	 $("#delete_budget").attr("disabled", "disabled");
	 var formData = getFormData("deleteBudget_form");
	 $.ajax({
				type : "POST",
				data :formData,
				url : sourceleadUrl+"/saveBudgetRequestReason",
				success : function(result) {
					$("#popUp").empty();
					$(".project_inn_mask").fadeOut(200);
					cancelTermination();
					getBudgetRequestList(requestType);
					getBudgetRequestListCount();
				},
			}); 
return true;
}else{
	return false;
}}
/* $('body').on('blur', '#deleteBudget_form', function() {
	$("#deleteBudget_form").showHelpOnFocus();;
	$("#deleteBudget_form").validateOnBlur(false, validationSettings);  
});  */

function editBudgetrequest(budgetRequestedId,requestType){
	//var sourceleadUrl = '${sourceleadUrl}';
	 $.ajax({
				type : "POST",
				url : sourceleadUrl+"/getBudgetRequestDetailsById?budgetRequestedId="+budgetRequestedId,
				success : function(json) {
					var result = "";
					result = result + '	<div class="budgetModalpop"> <i class="fa fa-close close-div" onClick="editCloseDiv();"></i>';
					result = result + '		<div class="form-horizontal clearfix">';
					result = result + '<form id="budgetRequest-form" name="budgetRequest-form">';
					result = result + '	    <input type="hidden" id="projectUUID" name="projectUUID" value="'+json.projectIdUUID+'">';
					result = result + '	    <input type="hidden" id="userId" name="loginUserid" value='+userId+'>';
					result = result + '	    <input type="hidden" id="budgetRequestedId" name="budgetRequestedId" value="'+json.id+'">';
					result = result + '	    <div class="clearfix mb10">';
					result = result + '	    <div class="clearfix mb10">';
					result = result + '	      <div class="col-md-12">';
					result = result + '	        <input type="text" class="form-control" value="'+json.budgetDescription+'" id="baseLine" name="baseLine" placeholder="Base Line *" field-name="Base Line" data-validation="required validate_Space validate_length length1-250">';
					result = result + '	      </div>';
					result = result + '	    </div>    ';                        
				 
					result = result + '	    <div class="clearfix mb10">';
					result = result + '	      <div class="col-md-12" id="bgApErrMsg">';
					result = result + '	        <select class="chosen-select" field-name="Approver" id="budgetApprover" name="budgetApprover" data-validation="required" onchange="removeErrMsg();">';
					result = result + '	        </select>';
					result = result + '	        </div>';
					result = result + '	      </div>'; 
					 
					result = result + '	    <div class="clearfix mb10">';
					result = result + '	      <div class="col-md-12">';
					result = result + '	        <input type="text" class="form-control inlineField" value="'+json.estimatedHours+'" id="estimatedHours" placeholder="Estimated Hours *" name="estimatedHours" field-name="Estimated Hours" data-validation="required">';
					result = result + '	      </div>';
					result = result + '	    </div>';
				  
					result = result + '	    <div class="clearfix mb10">';
					result = result + '	      <div class="col-md-12">';
					result = result + ' <div class="input-group">';
					result = result + ' <div class="input-group-btn selp-custom-min selp-customMin">';
					result = result + '       <select class="selectpicker" id="estimatedBudgetCurrency" name="estimatedBudgetCurrency" >';
					result = result + '          <option '+ (json.estimatedBudgetCurrency == "INR" ? "selected" : '') +' value="INR" >INR</option>';
					result = result + '          <option '+ (json.estimatedBudgetCurrency == "AUD" ? "selected" : '') +' value="AUD" >AUD</option>';
					result = result + '          <option '+ (json.estimatedBudgetCurrency == "USD" ? "selected" : '') +' value="USD" >USD</option>';
					result = result + '          <option '+ (json.estimatedBudgetCurrency == "GBP" ? "selected" : '') +' value="GBP" >GBP</option>';
					result = result + '          <option '+ (json.estimatedBudgetCurrency == "SGD" ? "selected" : '') +' value="SGD" >SGD</option>';
					result = result + '          <option '+ (json.estimatedBudgetCurrency == "EUR" ? "selected" : '') +' value="EUR" >EUR</option>';
					result = result + '     </select>';
					result = result + '     </div>';
					result = result + '	        <input type="text" class="form-control " value="'+json.estimatedBudgetValue+'" id="estimatedBudget" name="estimatedBudget" placeholder="Estimated Budget *" field-name="Estimated Budget" data-validation="required validate_Space validate_float validate_length length1-16">';
					result = result + ' </div>';
					result = result + ' </div>';
					
					result = result + '	    <div class="clearfix mt10 mb10"></div>';
					 
					result = result + '	    <div class="clearfix mr">';
					result = result + '	      <button type="button" class="btn btn-sm btn-primary pull-right" id="save_budget" onclick="saveBudgetRequest(\''+requestType+'\');">Update</button>';
					result = result + '	    </div>';
					result = result + '	  </div>';
					result = result + '</form>';
					result = result + '	</div>';
					 $("#editBudgetRequestPopUpid").empty();
					 $("#editBudgetRequestPopUpid").append(result);
					 $("#editBudgetRequestPopUpid").show();
					 getApproversList(json.approverUserIdlLong);
					 $('#estimatedBudgetCurrency').selectpicker('refresh');
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
				    
					$(".vendor_wrap_mask").fadeIn(200);
					$(".budgetModalpop").fadeIn(200);
					
				    
						$('.chosen-select').each(function() {
							var $this = $(this);
							$this.next().css({'width': '100%'});
							});
						
				},
			}); 
	 
	
			
}
function editCloseDiv(){
	$(".budgetModalpop").hide();
	$(".vendor_wrap_mask").hide(); 	
}

function onholdBudgetRequaet(budgetRequestedId,requestType){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>"; 
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"<form id='onHoldBudget_form' name='onHoldBudget_form' >";
	result = result +"<input type='hidden' id='budgetRequestedId' name='budgetRequestedId' value='"+budgetRequestedId+"'>";
	result = result +"<input type='hidden' id='loginUserid' name='loginUserid' value="+userId+">";
	result = result +"		<p>Are you sure you want to On-Hold ?</p>";
	result = result +"		<textarea  id='reasonForProjectBudget' name='reasonForProjectBudget'  placeholder='Comments here' field-name='Reason' data-validation='required validate_length length2-5000' ></textarea>";
	result = result +"</form>";
	result = result +'		<b href="javascript:void(0)"><input id="onHold_budget-id" type="button" value="On-Hold" onclick="saveOnHoldBudgetReason(\''+requestType+'\');"><input class="u_can_color" type="button"  value="Cancel" onClick="cancelTermination();"></b>';
	result = result +"	</div>";

	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".vendor_wrap_mask").fadeIn(200);
	}
function saveOnHoldBudgetReason(requestType){
	if ($('#onHoldBudget_form').validate(false, validationSettings)) {
	//var sourceleadUrl = '${sourceleadUrl}';
    $("#onHold_budget").attr("disabled", "disabled");
	 $("#onHold_budget").attr("disabled", "disabled");
	 var formData = getFormData("onHoldBudget_form");
	 $.ajax({
				type : "POST",
				data :formData,
				url : sourceleadUrl+"/onHoldBudgetRequest",
				success : function(result) {
					$("#popUp").empty();
	   			 	//$(".project_inn_mask").fadeOut(200);
	   			 	$(".vendor_wrap_mask").fadeOut(200);
					//cancelTermination();
					getBudgetRequestList(requestType);
					getBudgetRequestListCount();
				},
			}); 
	 return true;
	}else{
		return false;
}}
	/* }
$('body').on('blur', '#deleteBudget_form', function() {
	$("#deleteBudget_form").showHelpOnFocus();;
	$("#deleteBudget_form").validateOnBlur(false, validationSettings);  
});  */
function saveBudgetRequest(requestType){
	 var validationSettings = {
			errorMessagePosition : 'element',
			borderColorOnError : '',
			scrollToTopOnError : true,
		}; 
	 if ($('#budgetRequest-form').validate(false, validationSettings)) {
			// var sourceleadUrl = '${sourceleadUrl}';
			 $("#save_budget").attr("disabled", "disabled");
			 $("#save_budget").attr("disabled", "disabled");
			 var formData = getFormData("budgetRequest-form");
			 $.ajax({
						type : "POST",
						data :formData,
						url : sourceleadUrl+"/saveBudgetRequest",
						success : function(result) {
							getBudgetRequestList(requestType);
							getBudgetRequestListCount();
							 editCloseDiv();
						},
					}); 
			return true;
		} else {
			return false;
		}
}
$('body').on('blur', '#budgetRequest-form', function() {
	$("#budgetRequest-form").showHelpOnFocus();;
	$("#budgetRequest-form").validateOnBlur(false, validationSettings);  
}); 
function budgetRequsetPagination(){  
   $('#budgetRequestsPaginationList').DataTable( { 
   	"scrollCollapse": false,
   	"lengthMenu": [[5, 15, 25, 30, -1], [5, 15, 25, 30, "All"]],
		"aaSorting": [[4,'desc']],
   	"bLengthChange": true,
   	"bDestroy": true,
   	"ordering": true,
   	"oLanguage": {
    		"sSearch": "",
    		"sZeroRecords": "", "sEmptyTable": ""
    	}
   });
   $('#budgetRequestsPaginationList').wrap("<div class='overflow_Auto'></div>");
   $("#budgetRequestsPaginationList_filter input").attr("placeholder", "Budget search");
}
function getApproversList(approverUserId){
//var sourceleadUrl ='${sourceleadUrl}';
var orgLocationId =  locationId; // replcaced with dashboard variable.
	$.ajax({
	    	type: "GET",
	    	 url: sourceleadUrl+"/getResourceRequestApproversList?orgLocationId="+orgLocationId,
	    		success: function(json) {
	     		var output = "";
	     			output = output + "<option value=''>--Select Approver--</option>";
	     		for (var i=0; i<json.length; i++)
	     		{
		     		var opt = json[i];
		     		 
		     		 if(opt.id == approverUserId){
		        			output = output + "<option value='"+opt.id+"' selected>"+opt.fullName+" ("+opt.userType+")</option>";
		        		}else{
		        			output = output + "<option value='"+opt.id+"'> "+opt.fullName+" ("+opt.userType+")</option>";
		        		}
		     		 
	     		}
	     		$("#budgetApprover").empty();
	     		$("#budgetApprover").append(output);
	     		$('#budgetApprover').trigger("chosen:updated");
	         }
	 });
}
// leave management 
function getEmployeeTotalCounts(){
	var orgLocationId =  locationId; // replcaced with dashboard variable.
//	var userId = '${SourceLeadWeb_userId}';
	 $.ajax({
	      type : "GET",
	      url: sourceleadUrl+"/totalCountsForEmployeeLeaves?orgLocationId="+orgLocationId+"&userId="+userId,  
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
    		   
    		   //pie chart count
    		/*    AmCharts.makeChart("employeeLeaves",
    					{
    						"type": "pie",
    						"balloonText": "[[title]]<br><span style='font-size:14px'><b>[[value]]</b> </span>",
    						"innerRadius": "50%",
    						"labelRadius": 0,
    						"minRadius": 0,
    						"pieX": "50%",
    						"pieY": "50%",
    						"pullOutRadius": 0,
    						"radius": "49%",
    						"startAngle": 0,
    						"colors": [
    							"#337ab7",
    							"#66bb6a",
    							"#ef5350",
    							"#f0ad4e"
    						],
    						"labelsEnabled": false,
    						"marginBottom": 0,
    						"marginTop": 0,
    						"outlineThickness": 0,
    						"titleField": "category",
    						"valueField": "column-1",
    						"fontSize": 12,
    						"allLabels": [],
    						"balloon": {
    							"fixedPosition": false
    						},
    						"titles": [],
    						"dataProvider": [
    							{
    								"category": "Requested",
    								"column-1": report.employeeRequestedCounts
    							},
    							{
    								"category": "Approved",
    								"column-1": report.employeeApprovedCounts
    							},
    							{
    								"category": "Rejected",
    								"column-1": report.employeeRejectedCounts
    							},
    							{
    								"category": "Cancelled",
    								"column-1": report.employeeCancelledCounts
    							} 
    						]
    					} 
    				);*/
    		   
	       },
		  });
}
 

function getSelfTotalCounts(){
	var orgLocationId =  locationId; // replcaced with dashboard variable.
	//var userId = '${SourceLeadWeb_userId}';
	 $.ajax({
	      type : "GET",
	      url: sourceleadUrl+"/totalCountsForSelfLeaves?orgLocationId="+orgLocationId+"&userId="+userId,  
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
   		   
   		/*  AmCharts.makeChart("empselfLeaves",
 				{
 					"type": "pie",
 					"balloonText": "[[title]]<br><span style='font-size:14px'><b>[[value]]</b> </span>",
 					"innerRadius": "50%",
 					"labelRadius": 0,
 					"minRadius": 0,
 					"pieX": "50%",
 					"pieY": "50%",
 					"pullOutRadius": 0,
 					"radius": "49%",
 					"startAngle": 0,
 					"colors": [
 						"#337ab7",
						"#66bb6a",
						"#ef5350",
						"#f0ad4e",
						"#5bc0de"
 					],
 					"labelsEnabled": false,
 					"marginBottom": 0,
 					"marginTop": 0,
 					"outlineThickness": 0,
 					"titleField": "category",
 					"valueField": "column-1",
 					"fontSize": 12,
 					"allLabels": [],
 					"balloon": {
 						"fixedPosition": false
 					},
 					"titles": [],
 					"dataProvider": [
 						{
 							"category": "Pending",
 							"column-1": report.selfPendingCounts
 						},
 						{
 							"category": "Approved",
 							"column-1": report.selfApprovedCounts
 						},
 						{
 							"category": "Rejected",
 							"column-1": report.selfRejectedCounts
 						},
 						{
 							"category": "Cancelled",
 							"column-1": report.selfCancelledCounts
 						},
 						{
 							"category": "Balance",
 							"column-1": report.selfBalancedCounts
 						} 
 					]
 				}
 			); */ 
   		 
	       },
		  });
}
// end leave management.
// project detials code
function closeProjectDetailsPopup(){
	$("#projectDetailsDiv").empty();
	$("div.cand-advsrch-popup").hide();
	$(".advancedsearch-mask").fadeOut();
}

function openProjectDetailsPopup(projectUUID,orgLocationId){
	
	$("#projectDetailsDiv").empty();
	
	// var sourceleadUrl ='${sourceleadUrl}';
	 $("#projectPopupDetailsLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	
	 $.ajax({
	    	type: "POST",
	        url: sourceleadUrl+"/getProjectPopDetails?projectUUID="+projectUUID+"&orgLocationId="+orgLocationId, 
	        success: function(result) {
	        	
	        	var result = '';
	        	
	        	result = result +'<div class="clearfix mb">';
	        	result = result +'<label class="col-md-4"><strong>Project Name :</strong></label>';
	        	result = result + '<div class="col-md-8 title="'+result.projectName+'">'+result.projectName+'</div><div class="clearfix"></div>';
                result = result +'</div> ';
                
                $("#projectDetailsDiv").append(result);
	        }
	        
	 });
	
	
	 $("div.cand-advsrch-popup").toggle();
	 $(".advancedsearch-mask").fadeIn();
	 $("#projectPopupDetailsLoading").removeAttr("style"); 
}
// other event handlers...
function uploadAddApplicantCandidateDoc(){
	fileUpload("newUploadBtn", "addApplicants", "addApplicant_form");
}
function importAddApplicantDoc(){
	fileUpload("uploadBtn", "addApplicants", "addApplicant_form");
}
// not sure functions where exactly used need to check.
function changeRateLabels(){ // not used any where
	 
	 var currentRate = document.getElementById("currentRate").textContent;
	 var currentstringLength = currentRate.length; 
	 var currentRatelastChar = currentRate.charAt(currentstringLength - 1);
	 
	 if(currentRatelastChar == "*"){
		 $("#currentRate").html("Current Rate *"); 
	 }else{
		 $("#currentRate").html("Current Rate");
	 }
	 
	 var expectedRate = document.getElementById("expectedRate").textContent;
	 var expectedstringLength = expectedRate.length; 
	 var expectedRatelastChar = expectedRate.charAt(expectedstringLength - 1);
	 
	 if(expectedRatelastChar == "*"){
		 $("#expectedRate").html("Expected Rate *"); 
	 }else{
		 $("#expectedRate").html("Expected Rate");
	 }
	 
}

//
$(document.body).on('change', '#uploadBtn' ,function(){
	 $('#uploadFile').val(this.value.split( '\\' ).pop());
	 $("#uploadFile").focus();
	 if(validate_fileupload(this.value)){
		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		var form = $('#addApplicant_form')[0];
 		var formData = new FormData(form); 
		//var sourceleadUrl = '${sourceleadUrl}';
		   $.ajax({
				    type: "POST",
			    	data: formData,
		   	        contentType : 'application/json; charset=utf-8',
		   	        enctype: 'multipart/form-data',
			        processData: false,
			        contentType:false,
		   	        dataType : 'json',
					url : sourceleadUrl+"/parseResume?=",
					success : function(response) {
						if (response.response === "AWKWARD") {
						    location.href = "/errorPage";
						} else if (response.response === "SUCCESS") {
							var json = response.object;
							var name = json.name;
							var firstName = '';
							var lastName = '';
							if(name != null && name != 'undefined' && name != ''){
								 firstName = name.split(".")[0]; 
								 lastName = name.split(".")[1]; 
								 if(firstName == null || firstName ==  'undefined' || firstName == '' ||
										 lastName ==  null || lastName ==  'undefined'|| lastName ==  ''){
									 firstName = name;
								 }
							}
							
							 $("input[name=FirstName]").val(firstName);
				        	 $("input[name=LastName]").val(lastName);
				        	 $("input[name=EmailId]").val(json.email);
				        	 $("input[name=PhoneNumber]").val(json.phoneNumbers);
				        	 
				        	 $("#candidateSkills").empty();
				        	 var internalOutput = "";
	 						 
	 						    internalOutput = internalOutput+"<option value='3D Max'>3D Max</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Microsoft Exchange'>Microsoft Exchange</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='MVC.net'>MVC.net</option>";
	 	                     
	 						var skills = json.skills;
	 						if(skills != null && skills != 'undefined' && skills != ''){
	 							var str_array = skills.split(',');
	 							for(var i = 0; i < str_array.length; i++) {
	 							   str_array[i] = str_array[i].replace(/^\s*/, "").replace(/\s*$/, "");
	 							   internalOutput = internalOutput+"<option value='" +str_array[i]+ "' selected>"+str_array[i]+"</option>";
	 							}
	 						}
	 						$("#candidateSkills").append(internalOutput);
	 		        		$("#candidateSkills").trigger("chosen:updated");
	  						
							 validateSubmissionCandidateEmail();
							 getUserByUserEmail();
							 $("#loading").removeAttr("style");
						} else if (response.indexOf('Sign In') !== -1) {
							location.replace(sourceleadUrl);
						} else {
						    showError("Operation failed, please try again");
						}
					},
				});
	 }
});
function getUserByUserEmail() {
	 var emailId =   $("input[name=EmailId]").val();
	 //var sourceleadUrl ='${sourceleadUrl}';
	 if(emailId != null && emailId != ""){
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getUserByUserEmail?=&EmailId="+emailId, 
		        success: function(json) {
		        	
		        		if(json.firstName != null && json.firstName != ''){
		        			$("input[name=FirstName]").val(json.firstName);
		        			$("input[name=FirstName]").attr("readonly", "readonly");
		        			$("input[name=FirstName]").find('p.jquery_form_error_message').remove();
		        		}else{
		        			$("input[name=FirstName]").removeAttr("readonly", "readonly");
		        			var firstName = $("input[name=FirstName]");
		        			if(firstName == ''){
		        				$("input[name=FirstName]").val('');
		        			}
		        			
		        		}
		        		
		        		if(json.lastName != null && json.lastName != ''){
		        			$("input[name=LastName]").attr("readonly", "readonly");
		        			$("input[name=LastName]").val(json.lastName);
		        		}else{
		        			$("input[name=LastName]").removeAttr("readonly", "readonly");
		        			
		        			var lastName = $("input[name=LastName]");
		        			if(lastName == ''){
		        				$("input[name=LastName]").val('');
		        			}
		        			
		        			
		        		}
		        		
		        		if(json.mobilePhone != null && json.mobilePhone != ''){
		        			$("input[name=PhoneNumber]").val(json.mobilePhone);
		        			$("input[name=PhoneNumber]").attr("readonly", "readonly");
		        		}else{
		        			$("input[name=PhoneNumber]").removeAttr("readonly", "readonly");
		        			
		        			var phno = $("input[name=PhoneNumber]");
		        			if(phno == ''){
		        				$("input[name=PhoneNumber]").val('');
		        			}
		        	   }
		        },
		    });
	 }else{
		 $("input[name=FirstName]").attr("disabled", false);
		 $("input[name=FirstName]").val('');
		 
		 $("input[name=LastName]").attr("disabled", false);
		 $("input[name=LastName]").val('');
		 
		 $("input[name=PhoneNumber]").attr("disabled", false);
		 $("input[name=PhoneNumber]").val('');
		 
	 }
}
function validateSubmissionCandidateEmail() {
	 var email =   $("input[name=EmailId]").val();
	$('#emailDiv').find('p.jquery_form_error_message').remove();
	// var sourceleadUrl ='${sourceleadUrl}';
	 var requirementId = $("#requirementId").val();
	 if(email != null && email != ""){
		 $("#save_button").attr("disabled", "disabled");
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/validateRequirementCandidateEmail?=&email="+email+"&requirementId="+requirementId, 
		        success: function(data) {
		         	if(data == 'true'){
			        	  $("#save_button" ).addClass('btn btn-sm btn-primary').val('SUBMIT');
			        	  $('.jquery_form_error_message').remove();
		        		  $("input[name=EmailId]").after("<p class='jquery_form_error_message'>"+email+" is Already Existed</span>");
		        		  $("input[name=EmailId]").setAttribute('record-exist','yes');
		        		  $("input[name=EmailId]").setAttribute('record-exist-errorMsg',email+' is Already Existed'); 
		        	}
		        	else{
		        		$("#save_button").removeAttr("disabled");
		        		$("input[name=EmailId]").removeAttr( "record-exist record-exist-errormsg");
		        		$('.jquery_form_error_message').remove();
		        		isCoolingOff(requirementId);
		        	} 
		        },
		    });
	 }
	 
}
function validate_fileupload(fileName)
{
    var allowed_extensions = new Array("docx","doc","pdf","rtf");
    var file_extension = fileName.split('.').pop(); // split function will split the filename by dot(.), and pop function will pop the last element from the array which will give you the extension as well. If there will be no extension then it will return the filename.

    for(var i = 0; i <= allowed_extensions.length; i++)
    {
        if(allowed_extensions[i]==file_extension)
        {
            return true; // valid file extension
        }
    }

    return false;
}
$(document.body).on('change', '#uploadBtn' ,function(){
	 $('#uploadFile').val(this.value.split( '\\' ).pop());
	 $("#uploadFile").focus();
	 if(validate_fileupload(this.value)){
		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		var form = $('#addApplicant_form')[0];
 		var formData = new FormData(form); 
		//var sourceleadUrl = '${sourceleadUrl}';
		   $.ajax({
				    type: "POST",
			    	data: formData,
		   	        contentType : 'application/json; charset=utf-8',
		   	        enctype: 'multipart/form-data',
			        processData: false,
			        contentType:false,
		   	        dataType : 'json',
					url : sourceleadUrl+"/parseResume?=",
					success : function(response) {
						if (response.response === "AWKWARD") {
						    location.href = "/errorPage";
						} else if (response.response === "SUCCESS") {
							var json = response.object;
							var name = json.name;
							var firstName = '';
							var lastName = '';
							if(name != null && name != 'undefined' && name != ''){
								 firstName = name.split(".")[0]; 
								 lastName = name.split(".")[1]; 
								 if(firstName == null || firstName ==  'undefined' || firstName == '' ||
										 lastName ==  null || lastName ==  'undefined'|| lastName ==  ''){
									 firstName = name;
								 }
							}
							
							 $("input[name=FirstName]").val(firstName);
				        	 $("input[name=LastName]").val(lastName);
				        	 $("input[name=EmailId]").val(json.email);
				        	 $("input[name=PhoneNumber]").val(json.phoneNumbers);
				        	 
				        	 $("#candidateSkills").empty();
				        	 var internalOutput = "";
	 						 
	 						    internalOutput = internalOutput+"<option value='3D Max'>3D Max</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Microsoft Exchange'>Microsoft Exchange</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='MVC.net'>MVC.net</option>";
	 	                     
	 						var skills = json.skills;
	 						if(skills != null && skills != 'undefined' && skills != ''){
	 							var str_array = skills.split(',');
	 							for(var i = 0; i < str_array.length; i++) {
	 							   str_array[i] = str_array[i].replace(/^\s*/, "").replace(/\s*$/, "");
	 							   internalOutput = internalOutput+"<option value='" +str_array[i]+ "' selected>"+str_array[i]+"</option>";
	 							}
	 						}
	 						$("#candidateSkills").append(internalOutput);
	 		        		$("#candidateSkills").trigger("chosen:updated");
	  						
							 validateSubmissionCandidateEmail();
							 getUserByUserEmail();
							 $("#loading").removeAttr("style");
						} else if (response.indexOf('Sign In') !== -1) {
							location.replace(sourceleadUrl);
						} else {
						    showError("Operation failed, please try again");
						}
					},
				});
	 }
});
function dobDatePicker() { // not used any where
	  $('#DOB').datepicker({
	      dateFormat: 'yy-mm-dd',
	  });
	  
}
function setRateTypesByLocation(){ // not used any where
	var country = 'India';
	if(country == 'India'){
		$('#currentCTCRateType').val('INR');
		$('#expectedCTCRateType').val('INR');
	}else if(country == 'Australia'){
		$('#currentCTCRateType').val('AUD');
		$('#expectedCTCRateType').val('AUD');
	}else if(country == 'United States'){
		$('#currentCTCRateType').val('USD');
		$('#expectedCTCRateType').val('USD');
	}else if(country == 'Singapore'){
		$('#currentCTCRateType').val('SGD');
		$('#expectedCTCRateType').val('SGD');
	}else if(country == 'United Kingdom'){
		$('#currentCTCRateType').val('GBP');
		$('#expectedCTCRateType').val('GBP');
	}else if(country == 'France'){
		$('#currentCTCRateType').val('EUR');
		$('#expectedCTCRateType').val('EUR');
	}
}
function init(){ // not used any where 
	getAllSubmissionCandidateCountries();
	initLocationAuto('submissionLocation','submissionCountry','submissionState','submissionCity','submissionZipCode','','','');
	var country = 'India';
	$("#submissionCountry").val(country)
	      
	      /*  $("#submissionCountry").on('change', function (e) {
	    	   getCandidateStates();
	    	   getSubmissionCandidateCities();
	       });
	       $("#submissionState").on('change', function (e) {
	    	   getSubmissionCandidateCities();
	       }); */
	      
	       $('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});
		  })
		  
    setRateTypesByLocation();
}
function getExpYears(){
	var expYears = $("select[name=WorkExperience]").val();
	 if(expYears == "25+"){
		 $("#monthExp").attr("disabled", "disabled");
	 }else{
		 $("#monthExp").removeAttr("disabled");
	 }
}
function getRecomendedCandidateResumes(candidateId) {
	 $("#save_button").removeAttr("disabled");
	 $('.jquery_form_error_message').remove();
	// var sourceleadUrl ='http://localhost:8080/app';
	 if(candidateId != null && candidateId != "" && candidateId != 'undefined'){
		
		 $("#uploadFile").removeAttr("data-validation");
		 $("#uploadFile").val('');
		 $('.jquery_form_error_message').remove();
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getCandidateResumes?=&candidateId="+candidateId, 
		        success: function(data) {
		        	var json = data.data;
		        	if (data.response === "FAILED") {
			    		showError(data.responseMessage);
			    	} else if (data.response === "AWKWARD") {
			    		location.href = "/errorPage";
			    	} else if (data.response === "SUCCESS") {
			        	if(json!=null){
				        	var output = "";
				        	
				        	if(json.length == 1){
				        		var opt = json[0];
				        		if(opt.title != null && opt.title != 'undefined' && opt.title != ""){
				        			output = output + "<option value='" +opt.id+ "'>"+opt.title+"</option>";
				            	}else{
				            		output = output + "<option value='" +opt.id+ "'>"+opt.fileName+"</option>";
				            	}
				        	}else{
				        		output = output + "<option value=''>-- Select Resume --</option>"; 
					        	for (var i=0; i<json.length; i++)
								{
						        		var opt = json[i];
						        		if(opt.title != null && opt.title != 'undefined' && opt.title != ""){
						        			output = output + "<option value='" +opt.id+ "'>"+opt.title+"</option>";
						            	}else{
						            		output = output + "<option value='" +opt.id+ "'>"+opt.fileName+"</option>";
						            	}
								}
				        	}
				        	
				        	
				        	$("#candidateResumesList").empty();
					        $("#candidateResumesList").append(output);
					        
					        document.getElementById("newResumeDiv").style.display="none";
					        
					      
			        		
			        	}else{
			        		
			        		
			        		 document.getElementById("resumesDiv").style.display="none";
			        		 document.getElementById("newResumeDiv").style.display="";
			        		
			        		 
			        		 
			        	}
			    	}else if (data.indexOf('Sign In') !== -1) {
			    		location.replace(sourceleadUrl);
			    	} else {
			    		showError("Operation failed, please try again");
			    	}
		        },
		    });
	 }else{
		 var output = "";
    	 output = output + "<option value=''>-- Select Resume --</option>"; 
    	 $("#candidateResumesList").empty();
		 document.getElementById("resumesDiv").style.display="none";
	     $("#candidateResumesList").append(output);
	 }
	
}
(function($) {
	$.fn.serializeFormJSON = function() {
		var o = {};
		var a = this.serializeArray();
		$.each(a, function() {
			if (o[this.name]) {
				if (!o[this.name].push) {
					o[this.name] = [ o[this.name] ];
				}
				o[this.name].push(this.value || '');
			} else {
				o[this.name] = this.value || '';
			}
		});
		return o;
	};
})(jQuery);
var validationSettings = {
        errorMessagePosition : 'element',
        borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd' 
        };

	$('body').on('blur', '#addApplicant_form', function() {
		$("#addApplicant_form").showHelpOnFocus();
		$("#addApplicant_form").validateOnBlur(false, validationSettings);
	});
	
$('body').on('submit', '#addApplicant_form', function(e){

		  var requirementId = $("#requirementId").val();
		  if ($(this).validate(false, validationSettings)){
	      e.preventDefault();
	      var frm = $('#addApplicant_form').serializeFormJSON();
	     // var sourceleadUrl ='http://localhost:8080/app';
	      
	      var frm = $('#addApplicant_form').serializeFormJSON();
	      var con1 = JSON.stringify(frm);
	      con1= con1.replace(/[{}]/g, "");
	      var jsonData = encodeURIComponent(con1);
	      var form = $('#addApplicant_form')[0];
	      var formData = new FormData(form); 
	      //Loop through fields and save field data to array
	      var fields = [];
	      $('.sjfb-field').each(function() { 
	      	  var $this = $(this); 
	      	  var divId = $(this).attr('id');  
	      	  var fieldLabel = $this.find('label:first').text().replace("*","");
	      	  var inputValue = $this.find('input[type="radio"]:checked').val();
	      	  
	      	  var inputtype = $(this).find('input').attr('type');
	    	  if(inputtype == null || inputtype == '' || inputtype == 'undefined'){
	    		  if($(this).find('select').val() != null){
	    			  inputtype = 'select';
	    		  }else{
	    			  inputtype = 'textarea';
	    		  }
	    	  }
	      	  
	      	 if(inputValue == null || inputValue == '' || inputValue == 'undefined'){
	    		   inputValue = $this.find('input[type="checkbox"]:checked').val();
	    		   if(inputValue != null){
	    			   var selected = [];
	               	$this.find('input[type="checkbox"]:checked').each(function() {
	      		        selected.push($(this).val()); 
	      		       });
	               	inputValue = selected.toString()
	    		   }
	    		   
	      	 }
	      	 
	      	
	      	  if(inputValue == null || inputValue == '' || inputValue == 'undefined'){
	      		  inputValue = $this.find('input').val();
	      	  }
	      	  
	      	   
	    	  
	     	      if(inputValue == null || inputValue == '' || inputValue == 'undefined'){
	     	    	 inputValue = $this.find('select').val();
	     	      }
	     	      
	     	     if(inputValue == null || inputValue == '' || inputValue == 'undefined'){
	     	    	 inputValue = $this.find('textarea').val();
	     	      }
	     	      
	     	    
	     	     
	     	      if(inputValue == null || inputValue == '' || inputValue == 'undefined'){
	     	    	inputValue = '';
	     	      }
	     	      
	     	      
	     	      
		       	  var name = $this.find('input[type="radio"]:checked').attr('name');
		       	  var id = $this.find('input[type="radio"]:checked').attr('id');
		     	  if(name == null || name == '' || name == 'undefined'){
		     		  name = $this.find('input').attr('name');
		     		  id = $this.find('input').attr('id');
		     	  }
		     	  if(name == null || name == '' || name == 'undefined'){
		     		 name = $this.find('select').attr('name');
		     		 id = $this.find('select').attr('id');
	     	      }
		     	  
		     	  if(name == null || name == '' || name == 'undefined'){
		     		 name = $this.find('textarea').attr('name');
	     	      }
		     	 
		     	  if(name == "WorkExperience"){
		     		 var monthExp = document.getElementById("monthExp").value; 
		     		 if(inputValue != null && inputValue != 'undefined' && inputValue != ''){
		     			 if(inputValue == "50"){
		     				 inputValue = inputValue+" Years";
		     			  }else{
		     				 inputValue = inputValue+" Years"+" "+monthExp+" Months" ;
		     			  }
		     			 
		     		  }
		     		  
		     		  if(null == inputValue && monthExp != '0'){
		     			 inputValue = monthExp+" Months" ;
		     		  }
		     	  }
		     	  
		     	  
		     	 if(name == "CurrentCTC"){
		     		 inputValue = $this.find('input').val();
		     		 if(inputValue){
		     			 var currentCTCRatePer = document.getElementById("currentCTCRatePer").value; 
		     			 var currentCTCRateType = document.getElementById("currentCTCRateType").value; 
		     			 inputValue = currentCTCRateType+" "+inputValue+" /"+" "+currentCTCRatePer ;
		     		  }
		     	  }
		     	 
		     	if(name == "ExpectedCTC"){
		     		 inputValue = $this.find('input').val();
		     		  if(inputValue){
		     			 var expectedCTCRatePer = document.getElementById("expectedCTCRatePer").value; 
		     			 var expectedCTCRateType = document.getElementById("expectedCTCRateType").value; 
		     			 inputValue = expectedCTCRateType+" "+inputValue+" /"+" "+expectedCTCRatePer ;
		     		  }
		     	  }
		     	  
		     	  
	      	  fields.push({
	                label: fieldLabel,
	                type : inputtype,
	                id :id,
	                name :name,
	                value: inputValue,
	            });
	       });
	      var con = JSON.stringify(fields);
	      
	      con = encodeURIComponent(con);
	     
			
	       $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	      
	  	     $.ajax({
		    	type: "POST",
		    	data: formData,
	 	        contentType : 'application/json; charset=utf-8',
	 	        enctype: 'multipart/form-data',
		        processData: false,
		        contentType:false,
	 	        dataType : 'json',
	 	    	url : sourceleadUrl+"/saveAddApplicant?=&jsonData="+jsonData+"&con="+con,
		        success: function(result) {
		        	 $(".new_side_wrap").removeClass("new_side_wrap_active");
		        	 if(result.response === "SESSION_TIMEDOUT") {
							location.replace(sourceleadUrl);
						} else if (result.response === "AWKWARD") {
							location.href = "/errorPage";
						} else if (result.response === "SUCCESS") {
				    		$(".side_mask").fadeOut(200);
				    		$("body").css('overflow-y', 'auto');
				    		location.replace(sourceleadUrl+"/dashboard/"+locationId);
						}
		        },
		    }); 
	      return true; 
	    }
	    else{
	    	return false;	
	       }
	  });

	    
	   	$('body').on('blur', '#addApplicant_form', function() {
			$("#addApplicant_form").showHelpOnFocus();
			$("#addApplicant_form").validateOnBlur(false, validationSettings);
		});
	   	
	    $(".addApplicant_close, .side_mask").click(function() {
	    	$(".new_side_wrap").removeClass("new_side_wrap_active");
			$(".side_mask").fadeOut(200);
			$("body").css('overflow-y', 'auto');
		});	
	    
	   
		function clearAssignedTeamsOrUsers(){
			$("#assignToSelectedPopupCount").text(" (0) ");
			$("#assignedTeamId").val('');
			$("#selectedUsersGrid").empty();
			$("#assignedTeamsOrUsers").empty();
			$(".showGrid").text('0');
			$("#showGrid").prop('disabled', 'disabled');
			teamRoleIds = [];
			selectAssignUsersIds = [];
			$("#teamSelectionCategory").empty();
			$("#usersGrid").empty();
		}
		function getAssignedTeamsOrUsers(){
			$("#assignedTeamsOrUsers").slideUp();
			handleTeamRoleClick();
			$("#assignedTeamsOrUsers").empty();
			$("#assignedTeamsOrUsers").append($("#assignToSelectedDataTableId").clone());
		    $('#assignedTeamsOrUsers').find('thead').remove();
			var selectedIDs = $("#assignedTeamsOrUsers input:checkbox").map(function(){
				$(this).prop('disabled', true);
				$(this).closest( 'td' )[0].remove();
				return $(this).val();
		    }).toArray();
			$(".showGrid").text(selectedIDs.length);
			if(selectedIDs.length===0){
				$("#showGrid").prop('disabled', 'disabled');
			}else{
				$("#showGrid").removeAttr('disabled');
			}
		    $("#assignedTeamId").empty();
		    $("#assignedTeamId").val(selectedIDs);
		    $("#usersGrid").empty();
		}
		$(':checkbox[id=checkAll]').click(function() {
			//toggle all checkboxes except disabled checkboxes
			$(':checkbox[name=team]').not("[disabled]").prop('checked', this.checked);
		});

		$(':checkbox[id=checkAllSelected]').click(function() {
			//toggle all checkboxes
			$(':checkbox[name=selectedUser]').prop('checked', this.checked);
		});
		
function getUserList(teamType){
			$("#usersGrid").empty(); 
			var teamTypeArray = new Array();
			var isTeamExists = false;
			var isRoleExists = false;
			var teamTypeStr = ""+teamType+"";
			if(teamTypeStr != null){
				teamTypeStr.split(',').forEach(function (item) {
					if(item.includes("role")){
						isRoleExists = true;
					}else{
						isTeamExists = true;
					}
				});
			}
		 	   $("#usersGrid-loader").attr('style','position:absolute; width:100%; height:200px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		 	   $.ajax({
		           type : "POST",
		           url: sourceleadUrl+"/getUserList?teamType="+teamType,    
		            success :function(data) {
		            	$("#usersGrid").removeAttr("style");
		            	if (data.response === "SESSION_TIMEDOUT") {
			  				location.replace(sourceleadUrl);
		                } else if (data.response === "AWKWARD") {
		                    location.href = "/errorPage";
		                } else if (data.response === "SUCCESS") {
			                 if(data != null && data != 'undefined' && data != "" && data != '[]'){
				                	    var result = '';
				                	    //result = result+"<input type='hidden' name='assignedTeamId' id='assignedTeamId' value='"+teamType+"'/>";
					                    var json = data.data;
					                    //var selectedTempIds = $("#assignedTeamId").val();
					                    for (var i = 0; i < json.length; i++) {
					                    	 var opt = json[i];
						                     var isSelected = "";
						                     var isBlockedBySelected = "";
						                     var selectAssignUsersIdsStr = ""+selectAssignUsersIds+"";
						                     var str_array = selectAssignUsersIdsStr.match(/[^,]+,[^,]+/g);
						                     if(str_array != null && str_array != 'undefined' && str_array != "" && str_array != '[]'){
							                     for(var j = 0; j < str_array.length; j++) {
							                        if((""+str_array[j]+"") === (""+opt.id+','+opt.assignedTeamId+"")){
								                    	 isSelected = "checked disabled";
								                     }else if((""+str_array[j]+"") === (""+opt.id+"")){
								                    	 isBlockedBySelected = "fa fa-ban";
								                    	 isSelected = "disabled";
								                     }
							                     }
						                     }
						                     /* if(selectAssignUsersIds.includes(opt.id+','+opt.assignedTeamId)){
						                    	 isSelected = "checked disabled";
						                     }else if(selectAssignUsersIdsStr.includes(opt.id)){
						                    	 isBlockedBySelected = "fa fa-ban";
						                    	 isSelected = "disabled";
						                     } */
					                    	 result = result +'<tr>';
					                    	 
					                    	 var lastName = '';
					                    	 if(opt.lastName != null && opt.lastName != '' && opt.lastName != 'undefined'){
					                    		 lastName = ' '+opt.lastName;
					                    	 }
					                    	 var owner ='';
					         				 if(null != opt.isAssignedOwner && 'undefined' != opt.isAssignedOwner){
					         					 owner = opt.isAssignedOwner;
					         				 }
					         				 var assignedTeam = '';
					         				 if(null != opt.assignedTeam && 'undefined' != opt.assignedTeam && '' != opt.assignedTeam){
					         					 assignedTeam = opt.assignedTeam;
					         				 }
					                    	 
					         				 result = result +'  <td><input id="'+opt.id+'"  class="myCheckbox '+isBlockedBySelected+'" value="'+opt.id+','+opt.assignedTeamId+'"  type="checkbox" '+isSelected+' name="team" onchange="handleChange(this);"></td>';
					                    	 result = result +'             <td><span class="tbldata-dotdot-custom" title="'+opt.firstName+''+lastName+''+assignedTeam+''+owner+'">'+opt.firstName+''+lastName+''+assignedTeam+''+owner+'</span></td>';
					                    	 result = result +'             <td><span class="tbldata-dotdot-100" title="'+opt.email+'">'+opt.email+'</span></td>';
					                    	 result = result +'             <td><span class="tbldata-dotdot_80">'+opt.userType+'</span></td>';
					                    	 result = result +'             <td><span class="tbldata-dotdot_30">'+opt.assignJobCount+'</span></td>';
					                    	 result = result +'           </tr>';
				                       }
					                    
				                 	   //result = result +'	<span class="jquery_form_error_message" style="display:none;">Assign at least to a new member</span>'; */
				                 	   $("#usersGrid-loader").removeAttr("style");
				                       $("#usersGrid").append(result);
				                       $("#checkAll").prop('checked', false);
				                       if(isTeamExists && isRoleExists){
				                    	   $("#checkAll").prop('disabled', true);
				                       }else{
				                    	   $("#checkAll").prop('disabled', false);
				                       }
				                       //paginations(); 
			                 	}
			                } else if (data.indexOf('Sign In') !== -1) {
								location.replace(sourceleadUrl);
							} else {
			                    showError("Operation failed, please try again");
			                }
		          		},complete: function (data) {
		                	selectAssignUsers();
		                }
		       		});
		  	} 

function selectAssignUsers(){
	$("#selectedUsersGrid").empty();
	selectAssignUsersIds = [];
	$("input:checkbox[name=team]:checked").each(function(){
		var thisRow = $(this).closest( 'tr' )[0];
	    $("#selectedUsersGrid").append($(thisRow).clone());
	    $(this).attr('disabled', 'disabled');
	    selectAssignUsersIds.push($(this).val());
	});
	$("#selectedUsersGrid input[type=checkbox]").each(function(){
		$(this).attr('name', 'selectedUser');
		$(this).prop('disabled', false);
		$(this).removeAttr('onchange');
		$(this).attr('checked', false);
     });
	$("#assignToSelectedPopupCount").text(" ("+$('#assignToSelectedDataTableDiv >table >tbody >tr').length+") ");
	if($('#assignToSelectedDataTableDiv >table >tbody >tr').length == 0){
		$("#noAssignedmembers").show();
	}else{
		$("#noAssignedmembers").hide();
	}
}
function deSelectAssignUsers(){
	$("input:checkbox[name=selectedUser]:checked").each(function(){
		var deSelectedId = $(this).attr('value');
		$(this).closest( 'tr' )[0].remove();
		$("#assignToDataTableId input[type=checkbox]").each(function(){
			if(deSelectedId == $(this).attr('value')){
				$(this).prop('disabled', false);
			}
	     });
		
		const index = selectAssignUsersIds.indexOf(deSelectedId);
		selectAssignUsersIds.splice(index,1);
		
// 		selectAssignUsersIds = selectAssignUsersIds.filter(item => item !== deSelectedId);
	});
	$("#assignToSelectedPopupCount").text(" ("+$('#assignToSelectedDataTableDiv >table >tbody >tr').length+") ");
	if($('#assignToSelectedDataTableDiv >table >tbody >tr').length == 0){
		$("#noAssignedmembers").show();
	}else{
		$("#noAssignedmembers").hide();
	}
}
window.selectAssignUsersIds = new Array();
var isIE = /*@cc_on!@*/false || !!document.documentMode;
if(isIE){
(function() {
	  function remove() { this.parentNode && this.parentNode.removeChild(this); }
	  if (!Element.prototype.remove) Element.prototype.remove = remove;
	  if (Text && !Text.prototype.remove) Text.prototype.remove = remove;
	})();
};
function filterTeamMembers(){
	var teamType = $("#teamSelectionCategory").val();
	getUserList(teamType);
}
function getTeamsList(reqId){
	  $.ajax({
         type : "POST",
         url: sourceleadUrl+"/getTeamsList?reqId="+reqId,    
          success :function(result) {
          	var json = result.object;
          	if (result.response === "SESSION_TIMEDOUT") {
	  				location.replace(sourceleadUrl);
              } else if (result.response === "AWKWARD") {
                  location.href = "/errorPage";
              } else if (result.response === "SUCCESS") {
					var output = "";
				  '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_TEAM\')">';
				  if(json.teams.length > 0){
					  output = output + "<optgroup label='Teams'>";
					  for (var i = 0; i < json.teams.length; i++) {
							var opt = json.teams[i];
							if('Y' == opt.isTeamAssigned){
								output = output + "<option name='TeamAndRole' value='"+opt.id+"' selected >"+ opt.name + "</option>";
							}else if($.inArray(''+opt.id, teamRoleIds) > -1){
								output = output + "<option name='TeamAndRole' value='"+opt.id+"' selected>"+ opt.name + "</option>";	
						    }else{
								output = output + "<option name='TeamAndRole' value='"+opt.id+"'>"+ opt.name + "</option>";	
							}
						}
					  output = output + "</optgroup>";
				  }
				  '</sec:authorize>';
	
				  if(json.roles.length > 0){
					  output = output + "<optgroup label='Roles'>"; 
						for (var i = 0; i < json.roles.length; i++) {
							var opt = json.roles[i];
							if('Y' == opt.isRoleAssigned){
								output = output + "<option name='TeamAndRole' value='"+opt.id+"role' selected >"+ opt.displayName + "</option>";
							}else if($.inArray(''+opt.id+'role', teamRoleIds) > -1){
								output = output + "<option name='TeamAndRole' value='"+opt.id+"role' selected>"+ opt.displayName + "</option>";
							}else{
								output = output + "<option name='TeamAndRole' value='"+opt.id+"role'>"+ opt.displayName + "</option>";	
							}
						}
						output = output + "</optgroup>";
				  }
					$("#teamSelectionCategory").empty();
				    $("#teamSelectionCategory").append(output);
				    $("#teamSelectionCategory").selectpicker("refresh");
              } else if (result.indexOf('Sign In') !== -1) {
					location.replace(sourceleadUrl);
				} else {
                  showError("Operation failed, please try again");
              }
          },
          complete: function (result) {
          	var teamType = $("#teamSelectionCategory").val();
        		getUserList(teamType);
          }
          
    }); 
} 