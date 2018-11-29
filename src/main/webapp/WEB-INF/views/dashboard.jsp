<!doctype html>
<%-- <jsp:include page="masterSideNav.jsp" /> --%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="icon" type="image/png" href="/resources/images/fav.png" />
<title>Welcome to Source Lead</title>
<c:if test="${staticTime == null || staticTime == 0 }">
	    <c:set var='staticTime' value="<%=new java.util.Date().getTime() %>" scope="application"></c:set>
</c:if>
<script src="/resources/js/formvalidator.js?staticTime=${staticTime}"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
<link rel="stylesheet" href="/resources/css/dashboard_3118.css?staticTime=${staticTime}" type="text/css" media="all"/>
<link rel="stylesheet" href="/resources/css/chosen.min.css">
<link href="/resources/css/sourcelead-main.css" rel="stylesheet">
<link rel="stylesheet" href="/resources/css/timesheet.css?staticTime=${staticTime}">
<script src="/resources/js/jquery-1.11.1.min.js" /></script>
<script src="/resources/js/jquery-ui.js"></script>
<script src="/resources/js/formvalidator.js?staticTime=${staticTime}"></script>
<script src="/resources/js/bootstrap-select.min.js"></script>
<script src="/resources/js/chosen.jquery.min.js"></script>
<script src="/resources/js/jquery.dataTables.min.js"></script>
<script src="/resources/js/sourcelead-app.js"></script>
<script src="/resources/js/jquery.timepicker.js"></script>
<script src="/resources/js/jquery.timepicker.js"></script>
<script src="/resources/js/dashboardEvents.js?staticTime=${staticTime}"></script>
</head>
<sec:authorize access="hasAnyRole('ROLE_FILL_MEMBER_TIMESHEETS','ROLE_VIEW_MEMBER_TIMESHEETS','ROLE_EXPORT_MEMBER_TIMESHEETS') && hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_EXPORT_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS')" var="bothTimesheets"/>
<sec:authorize access="!hasAnyRole('ROLE_FILL_MEMBER_TIMESHEETS','ROLE_VIEW_MEMBER_TIMESHEETS','ROLE_EXPORT_MEMBER_TIMESHEETS') && hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_EXPORT_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS')" var="onlyTimesheets"/>
<sec:authorize access="hasAnyRole('ROLE_FILL_MEMBER_TIMESHEETS','ROLE_VIEW_MEMBER_TIMESHEETS','ROLE_EXPORT_MEMBER_TIMESHEETS') && !hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_EXPORT_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS')" var="onlyApprovalTimesheets"/>

<style>
.chosen-container {
    width: 100% !important;
}
.add_inter  .customchosen-select .chosen-container {
width: 100% !important;
}
.validationError p.jquery_form_error_message {
width: 75px; padding: 0 !important;
}

.settPop .form-control {
    height: auto !important; 
}
.chosen-container {
    width: 100% !important;
}
.add_inter  .customchosen-select .chosen-container {
width: 100% !important;
}
.validationError p.jquery_form_error_message {
width: 75px; padding: 0 !important;
}
.fa.pull-right {
    margin-left: -1.7em !important;
}
</style>
<!--  on boarding candidate list end -->
<script>
$( document ).ready(function() {
	 if('${bothTimesheets}' == 'true' || '${onlyTimesheets}' == 'true'){
		  timeSheetSubmissionStatusCount("myTimesheets");
	  }
	  else{
		  timeSheetSubmissionStatusCount("employes");
	  } 
	 getSelfTotalCounts(); // leave self count need to check authorizarion
	 expensesCount("myExpenses"); // expenses self need to check authorizarion
	 getJobFairs();//Events need to check authorizarion
	// contractList();
	var isJobsTabAccessable = '${SourceLeadWeb_isJobsTabAccessable}';
		if(isJobsTabAccessable=="Y" ){
			//requirementSubmissionsCount("today");//need to check authorizarion
			requirementList(false, -1);//need to check authorizarion
			onboardingCandidatesList();// onboarding candidate list , need to check authorizarion
			getProjectResourceReqestsListCount();// Project Requests Resource Associate  need to check authorizarion
			resourceRequestList("Waiting For Approval");// Project Requests Resource Associate  need to check authorizarion
			requirementApproverList('SUBMITTED'); //need to check authorizarion
			ApplicantAuthorization('requestSent');//need to check authorizarion
		    getInterviewsandTasks();//need to check authorizarion
			getInterviewsDates(); //need to check authorizarion
			//getJobFairDates();
			contractList();//need to check authorizarion
	} 
	 
	 var settingsConfigured = '${SourceLeadWeb_settingsConfigured}';
	 if(settingsConfigured == "N"){
		configureDiv();
	 }
});
function logoutForConfigure() {
	document.getElementById("logoutForm").submit();
}
$('#dasboardTaskDatpicker').datepicker({
	dateFormat : 'dd-mm-yy',
	changeMonth : true,
	changeYear : true,
	onSelect : function(selected) {
		assignTasksList();
	}
});
</script> 
 <!--   -->
<body>
<div class="wraper">
 <jsp:include page="masterHeaderPerfomance.jsp"></jsp:include>
<jsp:include page="masterSideNav.jsp"></jsp:include>    
 <jsp:include page="dashboard_configure_popup.jsp"></jsp:include> 
<%--<%@include  file="../views/html/masterHeader.html" %> --%>

  <section class="content-wraper">
    <div class="dashboard_loaded">
      <section class="content-header clearfix">
	      
	      <c:if test="${!empty param.invitation}">
	        <div class="success_pop" id="selector"  style="display: block;">
		         <i class="fa fa-check-circle"></i><span>You accepted invitation successfully.</span>
			</div>
	     </c:if>
	     
	     <c:if test="${!empty msg}">
	        <div class="success_pop" id="selector1"  style="display: block;">
		         <i class="fa fa-check-circle"></i><span>${msg}</span>
			</div>
	     </c:if>
	    
	      <div class="pull-left">
	        <h2>Dashboard<a  onclick="$('#configureDashboard').show();$('.advancedsearch-mask').fadeIn();"><i class="fa fa-cog ml"></i></a></h2>
	      </div>
	    </section>
	    
    </div>
<div class="row">

<div class="col-md-6 mb10">
    <div class="dashboardWidget-new dashNewWrap mb">
      <div class="dashNewWrapHead clearfix"> <a id="timeSheetLink" href="/app/timesheet" class="pull-left"> Timesheets &nbsp;&nbsp;</a>
        <input type="hidden" value="" id="timesheetSocketsFor">
        <ul class="nav nav-tabs pull-right">
          <li class="active"><a data-toggle="tab" onclick="timeSheetSubmissionStatusCount('myTimesheets');">Self</a></li>
          <li><a data-toggle="tab" onclick="timeSheetSubmissionStatusCount('employes');">Employees</a></li>
        </ul>
      </div>
      <div class="db-widget-sm">
        <div class="col-xs-12 col-sm-3">
          <div class="dashNewCount">
            <p id="savedCountAnchor">Draft</p>
            <p class="NewCount"><span id="savedTimesheetsSubmissionsCount">0</span></p>
            <div id="draftLoading"></div>
          </div>
        </div>
        <div class="col-xs-12 col-sm-3">
          <div class="dashNewCount">
            <p id="waitingCountAnchor">Submitted</p>
            <p class="NewCount suColor"><span id="waitingTimesheetsSubmissionsCount">0</span></p>
            <div id="waitingLoading"></div>
          </div>
        </div>
        <div class="col-xs-12 col-sm-3">
          <div class="dashNewCount">
            <p id="approvedCountAnchor">Approved</p>
            <p class="NewCount apColor"><span id="approvalTimesheetsSubmissionsCount">0</span></p>
            <div id="approvedloading"></div>
          </div>
        </div>
        <div class="col-xs-12 col-sm-3">
          <div class="dashNewCount">
            <p id="rejectedCountAnchor">Rejected</p>
            <p class="NewCount reColor"><span id="rejectTimesheetsSubmissionsCount">0</span></p>
            <div id="rejectLoading"></div>
          </div>
        </div>
      </div>
    </div>
  </div>

  <div class="col-md-6 mb10">
    <div class="dashboardWidget-new dashNewWrap mb">
      <div class="dashNewWrapHead clearfix"> <a class="pull-left" id="jobsLink" href="/app/jobs">Jobs </a><a class="moreLink" href="/app/requirementApplicantsList?&amp;tab=previous&amp;orgLocationId=${SourceLeadWeb_orgLocationId}" title="View more" id="viewmoresDiv"> View More..</a>
        <ul class="nav nav-tabs pull-right">
          <li class="active"><a data-toggle="tab" onclick="requirementSubmissionsCount('today');">Today</a></li>
          <li><a data-toggle="tab" onclick="requirementSubmissionsCount('previous');">1 Month</a></li>
          <input type="hidden" name="dateActiveTab" id="dateActiveTab" value="today">
        </ul>
      </div>
      <div class="db-widget-sm">
        <div class="col-xs-12 col-sm-3">
          <div class="dashNewCount" title="Applicants" id="appDiv">
            <p>Applicants</p>
            <p class="NewCount"><span id="applicantsCount">0</span></p>
            <div id="applicantsCountLoading"></div>
          </div>
        </div>
        <div class="col-xs-12 col-sm-3">
          <div class="dashNewCount" title="Client Submissions" id="subDiv">
            <p>Client Submissions</p>
            <p class="NewCount suColor"><span id="submissionsCount">0</span></p>
            <div id="submissionsCountLoading"></div>
          </div>
        </div>
        <div class="col-xs-12 col-sm-3">
          <div class="dashNewCount" title="Interviews" id="intDiv">
            <p>Interviews</p>
            <p class="NewCount deColor"><span id="interviewsCount">0</span></p>
            <div id="interviewsCountLoading"></div>
          </div>
        </div>
        <div class="col-xs-12 col-sm-3">
          <div class="dashNewCount" title="Offers" id="offDiv">
            <p>Offers</p>
            <p class="NewCount apColor"><span id="offersCount">0</span></p>
            <div id="offersCountLoading"></div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <sec:authorize access="hasAnyRole('ROLE_ADD_MY_EXPENSES','ROLE_VIEW_EDIT_MY_EXPENSES','ROLE_EXPORT_MY_EXPENSES', 'ROLE_ADD_EMPLOYEE_EXPENSES','ROLE_VIEW_EDIT_EMPLOYEE_EXPENSES','ROLE_EXPORT_EMPLOYEE_EXPENSES')">
  <div class="col-md-6 mb10">
    <div class="dashboardWidget-new dashNewWrap mb">
      <div class="dashNewWrapHead clearfix"> <a class="pull-left" id="expensesLink" href="/app/expenses">Expenses &nbsp;&nbsp;</a>
        <ul class="nav nav-tabs pull-right">
          <li class="active"><a data-toggle="tab" onclick="expensesCount('myExpenses');">Self</a></li>
         <sec:authorize access="hasAnyRole('ROLE_ADD_EMPLOYEE_EXPENSES','ROLE_VIEW_EDIT_EMPLOYEE_EXPENSES','ROLE_EXPORT_EMPLOYEE_EXPENSES')">
	              <li><a data-toggle="tab" onclick="expensesCount('employeeExpenses');" >Employees</a></li>
	       </sec:authorize>
        </ul>
      </div>
      <div id="today-req" class="tab-pane fade in active">
        <div class="db-widget-sm">
          <div class="col-xs-12 col-sm-3">
            <div class="dashNewCount">
              <p id="dial1txt">Draft</p>
              <p class="NewCount"><span id="expensesDraftCount">0</span></p>
            </div>
          </div>
          <div class="col-xs-12 col-sm-3" id="submittedId">
            <div class="dashNewCount">
              <p id="dial2txt"><a onclick="myExpensesListByStage('submitted');">Submitted</a></p>
              <p class="NewCount suColor"><span id="expensesSubmittedForApprovalCount"><a onclick="myExpensesListByStage('submitted');">1</a></span></p>
            </div>
          </div>
          <div class="col-xs-12 col-sm-3">
            <div class="dashNewCount">
              <p id="dial3txt">Approved</p>
              <p class="NewCount apColor"><span id="expensesApprovedCount">0</span></p>
            </div>
          </div>
          <div class="col-xs-12 col-sm-3" id="dial4txtDial">
            <div class="dashNewCount">
              <p id="dial4txt">Rejected</p>
              <p class="NewCount reColor"><span id="expensesRejectCount">0</span></p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  </sec:authorize>
  <sec:authorize access="hasAnyRole('ROLE_APPLY_LEAVE','ROLE_APPLY_BEHALF_LEAVE','ROLE_APPROVE_LEAVE')">
  <div class="col-md-6 mb10">
    <div class="dashboardWidget-new dashNewWrap mb">
      <div class="dashNewWrapHead clearfix"> <a class="pull-left" href="/app/leaveManagementReports" title="Leave Management">Leaves</a>
        <div class="pull-left balLeaves">Balance Leaves<span class="label label-info" id="selfBalancedCounts" title="Balance Leaves">0</span></div>
        <ul class="nav nav-tabs pull-right">
          <li class="active"><a data-toggle="tab" href="#self">Self</a></li>
          <sec:authorize access="hasAnyRole(\'ROLE_APPROVE_LEAVE\')">
          <li><a data-toggle="tab" href="#employee" onclick="getEmployeeTotalCounts();">Employees</a></li>
          </sec:authorize>
        </ul>
      </div>
      <div class="clearfix">
        <div class="tab-content clearfix">
          <div id="self" class="tab-pane fade in active">
            <div class="width100percnt clearfix">
              <div id="empselfLeaves" style="width: 60%; height: 100px;" class="amchartAnchor-Hide pull-left hidden"> </div>
              <div class=" clearfix">
                <div class="col-xs-12 col-sm-3">
                  <div class="dashNewCount">
                    <p id="selfpend">Pending</p>
                    <p class="NewCount suColor"><span id="selfPendingCounts">0</span></p>
                  </div>
                </div>
                <div class="col-xs-12 col-sm-3">
                  <div class="dashNewCount">
                    <p id="selfapp">Approved</p>
                    <p class="NewCount apColor"><span id="selfApprovedCounts">0</span></p>
                  </div>
                </div>
                <div class="col-xs-12 col-sm-3">
                  <div class="dashNewCount">
                    <p id="selfrej">Rejected</p>
                    <p class="NewCount reColor"><span id="selfRejectedCounts">0</span></p>
                  </div>
                </div>
                <div class="col-xs-12 col-sm-3">
                  <div class="dashNewCount">
                    <p id="selfcanc">Cancelled</p>
                    <p class="NewCount caColor"><span id="selfCancelledCounts">0</span></p>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-xs-12 col-sm-4 mb hidden">
              <div id="selfApproved" style="width: 100%; height: 150px;" class="amchartAnchor-Hide"> </div>
              <div class="leavescount text-center"> <a href="leavetype-approvedcount.html" id="approved" title="Approved">20</a> </div>
            </div>
            <div class="col-xs-12 col-sm-4 mb hidden">
              <div id="selfRejected" style="width: 100%; height: 150px;" class="amchartAnchor-Hide"> </div>
              <div class="leavescount text-center"> <a href="leavetype-rejectedcount.html" id="rejected" title="Rejected">10</a> </div>
            </div>
          </div>
          <div id="employee" class="tab-pane fade">
            <div class="db-widget-sm clearfix">
              <div class="width100percnt clearfix">
                <div class="clearfix">
                  <div class="col-xs-12 col-sm-3">
                    <div class="dashNewCount">
                      <p id="empReq"></p>
                      <p class="NewCount suColor"><span id="employeeRequestedCounts"></span></p>
                    </div>
                  </div>
                  <div class="col-xs-12 col-sm-3">
                    <div class="dashNewCount">
                      <p id="empApp"></p>
                      <p class="NewCount apColor"><span id="employeeApprovedCounts"></span></p>
                    </div>
                  </div>
                  <div class="col-xs-12 col-sm-3">
                    <div class="dashNewCount">
                      <p id="empRej"></p>
                      <p class="NewCount reColor"><span id="employeeRejectedCounts"></span></p>
                    </div>
                  </div>
                  <div class="col-xs-12 col-sm-3">
                    <div class="dashNewCount">
                      <p id="empCanc"></p>
                      <p class="NewCount caColor"><span id="employeeCancelledCounts"></span></p>
                    </div>
                  </div>
                </div>
                <div style="width: 40%; height: 105px;" class="countList pull-left hidden">
                  <ul class="list-unstyled list-group clearfix mb5">
                    <li class="clearfix">
                      <label class="pull-left">Requested Leaves</label>
                      <a href="/app/employeeRequestedLeaves" class="color_white" id="empReq" title="Requested Leaves"><span class="label label-primary pull-right" id="employeeRequestedCounts"></span>&gt;</a> </li>
                    <li class="clearfix">
                      <label class="pull-left">Approved Leaves</label>
                      <a href="/app/employeeApprovedLeaves" class="color_white" id="empApp" title="Approved Leaves"><span class="label label-success pull-right" id="employeeApprovedCounts"></span></a> </li>
                    <li class="clearfix">
                      <label class="pull-left">Rejected Leaves</label>
                      <a href="/app/employeeRejectedLeaves" class="color_white" id="empRej" title="Rejected Leaves"><span class="label label-danger pull-right" id="employeeRejectedCounts"></span></a> </li>
                    <li class="clearfix">
                      <label class="pull-left">Cancelled Leaves</label>
                      <a href="/app/employeeCancelLeaves" class="color_white" id="empCanc" title="Cancelled Leaves"><span class="label label-warning pull-right" id="employeeCancelledCounts"></span></a> </li>
                  </ul>
                </div>
              </div>
            </div>
            <div class="db-widget-sm hidden">
              <div class="col-xs-12 col-sm-4 mb">
                <div id="empRequested" style="width: 100%; height: 150px;" class="amchartAnchor-Hide"> </div>
                <div class="leavescount text-center"> <a href="javascript:void(0)" id="requested" title="Requested">70</a> </div>
              </div>
              <div class="col-xs-12 col-sm-4 mb">
                <div id="empApproved" style="width: 100%; height: 150px;" class="amchartAnchor-Hide"> </div>
                <div class="leavescount text-center"> <a href="javascript:void(0)" id="empApproved2" title="Approved">20</a> </div>
              </div>
              <div class="col-xs-12 col-sm-4 mb">
                <div id="empRejected" style="width: 100%; height: 150px;" class="amchartAnchor-Hide"> </div>
                <div class="leavescount text-center"> <a href="javascript:void(0)" id="empRejected2" title="Rejected">10</a> </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  </sec:authorize>
  <sec:authorize access="hasAnyRole('ROLE_VIEW_REQUIREMENT')">
   <div class="mb clearfix col-md-12"> 
      <!-- Assigned Requirements -->
      <div class="dashNewWrap mb10" id="assignedRequirementsDiv">
        <div class="dashNewWrapHead noBor clearfix">
          <h4 class="pull-left">Jobs</h4>
        </div>
        <div class="project_side_wrap_inn_tabs newDashPad">
          <div id="resourceRequestsDivLoading"></div>
          <div id="requirementListTabHead">
            <div id="requirementListActions">
              <div class="pull-right" id="viewmoresDiv"><a href="/app/jobs" class="btn btn-sm btn-primary pull-right" title="View more"> View More</a></div>
            </div>
            <div class="clearfix require_wrap_table dashboard-data filter_position positionRelative" style="min-height: 200px;">
              <div id="requirementLoading" style="position: absolute; width: 100%; height: 100%; background-color: rgba(255, 255, 255, 0.8); top: 0px; left: 0px; z-index: 100; background-image: url(&quot;/resources/images/loading.gif&quot;); background-position: center center; background-repeat: no-repeat; background-size: 75px auto; display: none;"></div>
              <div id="requirementList">
                <div class="new_side_wrap">
                  <div class="new_side_wrap_inn">
                    <div class="new_side_wrap_cta">
                      <div id="addApplicantDiv"></div>
                    </div>
                  </div>
                </div>
                <div id="requirementPagination_wrapper" class="dataTables_wrapper no-footer">
                  <table id="requirementPagination" class="display dataTable no-footer" role="grid" aria-describedby="requirementPagination_info" style="width: 99.9%;" width="99.9%" cellspacing="0">
                    <thead>
                      <tr role="row">
                        <th>Actions</th>
                        <th >Generated ID</th>
                        <th >Title</th>
                        <th>Client</th>
                        <th >Rate</th>
                        <th>Priority</th>
                        <th>Applicants</th>
                        <th>Shortlisted</th>
                        <th>Submitted</th>
                        <th>Interview</th>
                        <th>Offered</th>
                        <th>Joined</th>
                        <th>Status</th>
                      </tr>
                    </thead>
                  </table>
                  <div class="dataTables_info" id="requirementPagination_info" role="status" aria-live="polite"></div>
                </div>
              </div>
            </div>
          </div>
          <div class="vendor_wrap_mask"></div>
        </div>
      </div>
    </div>
  </sec:authorize>
  <sec:authorize access="hasAnyRole('ROLE_CREATE_ON_BOARD_USER','ROLE_VIEW_EDIT_ON_BOARD_USER')">
  <div class="clearfix"></div>
  <div class="col-md-12">
    <div class="mb clearfix col-md-12 noPad">
      <div class=" dashNewWrap hdrPosition minHeight_225 mb10">
        <div class="dashNewWrapHead noBor clearfix">
          <h4 class="pull-left">Candidates on-boarding</h4>
        </div>
        <div class="clearfix project_tabs_inn_table dashboard-data newDashPad dataSeaNew">
          <div id="onboardingLoading"></div>
          <div id="onboardingCandidatesList">
            <div id="onboardingPagination_wrapper" class="dataTables_wrapper no-footer">
              <div id="onboardingPagination_filter" class="dataTables_filter">
                <label>
                  <input type="search" class="" placeholder="" aria-controls="onboardingPagination">
                </label>
              </div>
              <table id="onboardingPagination" class="display dataTable no-footer" role="grid" aria-describedby="onboardingPagination_info" style="width: 99.9%;" width="99.9%" cellspacing="0">
                <thead>
                    <th>Candidate Name</th>
                    <th>Email</th>
                    <th>Title</th>
                    <th>DOJ<br>
                      (<small class="custom-small">DD-MM-YYYY</small>)</th>
                    <th>Rate</th>
                    <th>Position Type</th>
                    <th>On-board Status</th>
                    <th>Actions</th>
                  </tr>
                </thead>
              </table>
              <div class="dataTables_info" id="onboardingPagination_info" role="status" aria-live="polite"></div>
              <div class="dataTables_paginate paging_simple_numbers" id="onboardingPagination_paginate"><a class="paginate_button previous disabled" aria-controls="onboardingPagination" data-dt-idx="0" tabindex="0" id="onboardingPagination_previous">Previous</a><span><a class="paginate_button current" aria-controls="onboardingPagination" data-dt-idx="1" tabindex="0">1</a><a class="paginate_button " aria-controls="onboardingPagination" data-dt-idx="2" tabindex="0">2</a></span><a class="paginate_button next" aria-controls="onboardingPagination" data-dt-idx="3" tabindex="0" id="onboardingPagination_next">Next</a></div>
            </div>
          </div>
          <div id="rejectedReasonDiv"></div>
        </div>
      </div>
    </div>
  </div>
  </sec:authorize>
  <div class="mb clearfix col-md-12">
  <div class="mb clearfix col-md-12">
  <div class="dashNewWrap mb10">
 <div class="dashNewWrap mb10">
        	<div class="dashNewWrapHead clearfix">
				<h4 class="pull-left">Project Requests</h4>
	     	    <ul class="nav nav-tabs pull-right">
	     	    	<sec:authorize access="hasRole('ROLE_RESOURCE_REQUEST_APPROVER')">
				        <li class="active" id="associateDiv"><a  onclick="resourceRequestList('Waiting For Approval')">Associate</a></li>
				        <li id="newResourceDiv"><a href="javascript:void(0)"  onclick="newResourceRequestList('Waiting For Approval');">New Resource</a></li>
				       </sec:authorize> 
				       <sec:authorize access="hasRole('ROLE_BUDGET_APPROVER')">
				        <li id="budgetDiv"><a href="javascript:void(0)" onclick="getBudgetRequestList('Waiting For Approval')">Budget</a></li>
				       </sec:authorize>
			        <div class="clearfix"></div>
			    </ul>				
        	</div>       	  
      	  
      	    
      	    <div class="clearfix newDashPad">
      	    
      	    <div class="project_side_wrap_inn_tabs" id="assocaiteRequestsTabDiv" style="display: block;">
      	    <div id="resourceRequestsDivLoading"></div>
			    <ul class="project-tabs-menu tabnewStyles tabnew-styles mt5">
			        <li class="activeIcon" id="waiting"><a title="Waiting" onclick="resourceRequestList('Waiting For Approval')"><i class="fa fa-hourglass-start paddingRight"></i> (<span id="waitingCount"></span>)</a></li>
			        <li id="requestApproved"><a title="Approved"  onclick="resourceRequestList('Approved')"><i class="fa fa-check paddingRight"></i> (<span id="approvedCount"></span>)</a></li>
			        <li id="requestRejected"><a title="Rejected" onclick="resourceRequestList('Rejected')"><i class="fa fa-times paddingRight"></i> (<span id="rejectedCount"></span>)</a></li>
			        <li id="requestOnhold"><a title="On-Hold" onclick="resourceRequestList('On hold')"><i class="fa fa-clock-o paddingRight"></i>(<span id="onholdCount"></span>)</a></li>
			        <div class="clearfix"></div>
			    </ul>
			    <div class="clearfix mb5"></div>
			    <div id="resourceRequestList"></div>
			    <div class="vendor_wrap_mask"></div>
   				<div id="requestedAssociatesDivLoading"></div>
   				<div id="requestOnHoldReasonDiv"></div>
   				<div id="rejectedReasonDiv"></div>
           </div>
           
           <div class="project_side_wrap_inn_tabs" id="newResourceRequestTabDiv" style="display: none;">
      	    <div id="resourceRequestsDivLoading"></div>
			    <ul class="project-tabs-menu tabnewStyles tabnew-styles mt5">
			        <li class="activeIcon" id="newReqWaiting"><a title="Waitig" onclick="newResourceRequestList('Waiting For Approval')"><i class="fa fa-hourglass-start paddingRight"></i>(<span id="newWaitingCount"></span>)</a></li>
			        <li id="newRequestApproved"><a title="Approved" onclick="newResourceRequestList('Approved')"><i class="fa fa-check paddingRight"></i> (<span id="newApprovedCount"></span>)</a></li>
			        <li id="newRequestRejected"><a title="Rejected" onclick="newResourceRequestList('Rejected')"><i class="fa fa-times paddingRight"></i> (<span id="newRejectedCount"></span>)</a></li>
			        <li id="newRequestOnhold"><a title="On-Hold" onclick="newResourceRequestList('On hold')"><i class="fa fa-clock-o paddingRight"></i> (<span id="newOnholdCount"></span>)</a></li>
			        <div class="clearfix"></div>
			    </ul>
			    <div class="clearfix mb5"></div>
			    <div id="newResourceRequestList"></div>
			    <div class="vendor_wrap_mask"></div>
   				<div id="requestedNewAssociatesDivLoading"></div>
   				<div id="newRequestOnHoldReasonDiv"></div>
   				<div id="resourceRejectedReasonDiv"></div>
           </div>
           
           <div class="project_side_wrap_inn_tabs" id="budgetTabDiv" style="display: none;">
      	    <div id="resourceRequestsDivLoading"></div>
			    <ul class="project-tabs-menu tabnewStyles tabnew-styles mt5">
			        <li class="activeIcon" id="budgetWaiting"><a title="Waiting" onclick="getBudgetRequestList('Waiting For Approval')"><i class="fa fa-hourglass-start paddingRight"></i> (<span id="budgetWaitingCount"></span>)</a></li>
			        <li id="budgetApproved"><a title="Approved"  onclick="getBudgetRequestList('Approved')"><i class="fa fa-check paddingRight"></i> (<span id="budgetApprovedCount"></span>)</a></li>
			        <li id="budgetRejected"><a title="Rejected" onclick="getBudgetRequestList('Rejected')"><i class="fa fa-times paddingRight"></i> (<span id="budgetRejectedCount"></span>)</a></li>
			        <li id="budgetOnhold"><a title="On-Hold" onclick="getBudgetRequestList('On Hold')"><i class="fa fa-clock-o paddingRight"></i> (<span id="budgetOnholdCount"></span>)</a></li>
			        <div class="clearfix"></div>
			    </ul>
			    <div class="clearfix mb5"></div>
			    <div id="budgetRequestListDiv"></div>
			    <div class="vendor_wrap_mask"></div>
   				<div id="budgetRejectedReasonDiv" ></div>
   				<div id="budgetOnHoldReasonDiv"></div>
           </div>
           
           </div>
      	  </div>
      	  <!-- projectPopupDetails.jsp included  -->
      	  <div class="cand-advsrch-popup advancedsearch-popup filterDynamic-Search">
			<div id="projectPopupDetailsLoading"></div>
				<h5 class="headerAdsrch" id="ProjectDetailsDiv">Project Details
					<a href="javascript:void(0)" class="close-div" onClick="closeProjectDetailsPopup()"><i
						class="fa fa-close"></i></a>
				</h5>
				<div class="addInner">
					<div class="row">
							
						
						<div class="col-md-4">
						<div class="row">
						   <div id="projectDetailsDiv"></div>
						</div>
						</div>
						
						
					</div>
				</div>
					<div class="clearfix advancedsearch-footer">
						<div class="pull-right mr">
							<input type="button" value="Close" id="search_requiremet"
								onclick="closeProjectDetailsPopup();" class="btn btn-sm btn-success" />
						</div>
					</div>
				</div>
</div>
</div>
   <sec:authorize access="hasAnyRole('ROLE_VIEW_REQUIREMENT')">
  <div class="col-md-6 mb">
    <div class="dashNewWrap mb10">
      <div class="dashNewWrapHead clearfix">
        <h4 class="pull-left">Job Approvals</h4>
        <ul class="nav nav-tabs pull-right">
          <li class="active" id="waitingTab"><a data-toggle="tab" onclick="requirementApproverList('SUBMITTED');">Waiting </a></li>
          <li id="approvedTab"><a data-toggle="tab" onclick="requirementApproverList('APPROVED');">Approved </a></li>
          <li id="rejectedTab"><a data-toggle="tab" onclick="requirementApproverList('REJECTED');">Rejected</a></li>
        </ul>
      </div>
      <div class="clearfix project_tabs_inn_table dashboard-data newDashPadSearch dataSeaNew">
        <div class="success_pop" id="selector4" style="display: none;"><i class="fa fa-check-circle"></i><span id="eduMsgDiv"></span></div>
        <div id="requirementApproverList" class="">
          <div id="rejectRequirementAlert"></div>
          <div id="requirementApproverPagination_wrapper" class="dataTables_wrapper no-footer">
            <div id="requirementApproverPagination_filter" class="dataTables_filter">
              <label>
                <input type="search" class="" placeholder="" aria-controls="requirementApproverPagination">
              </label>
            </div>
            <table id="requirementApproverPagination" class="display dataTable no-footer" role="grid" aria-describedby="requirementApproverPagination_info" style="width: 99.5%;" width="99.5%" cellspacing="0">
              <thead>
                <tr role="row">
                  <th class="sorting_asc" tabindex="0" aria-controls="requirementApproverPagination" rowspan="1" colspan="1" style="width: 137px;" aria-sort="ascending" aria-label="ID-Title: activate to sort column descending">ID-Title</th>
                  <th class="sorting" tabindex="0" aria-controls="requirementApproverPagination" rowspan="1" colspan="1" style="width: 102px;" aria-label="Rate: activate to sort column ascending">Rate</th>
                  <th class="sorting" tabindex="0" aria-controls="requirementApproverPagination" rowspan="1" colspan="1" style="width: 89px;" aria-label="Exp: activate to sort column ascending">Exp</th>
                  <th class="sorting" tabindex="0" aria-controls="requirementApproverPagination" rowspan="1" colspan="1" style="width: 127px;" aria-label="Action: activate to sort column ascending">Action</th>
                </tr>
              </thead>
              <tbody>
                <tr class="odd">
                  <td colspan="4" class="dataTables_empty" valign="top"></td>
                </tr>
              </tbody>
            </table>
            <div class="dataTables_info" id="requirementApproverPagination_info" role="status" aria-live="polite"></div>
            <div class="dataTables_paginate paging_simple_numbers" id="requirementApproverPagination_paginate"><a class="paginate_button previous disabled" aria-controls="requirementApproverPagination" data-dt-idx="0" tabindex="0" id="requirementApproverPagination_previous">Previous</a><span></span><a class="paginate_button next disabled" aria-controls="requirementApproverPagination" data-dt-idx="1" tabindex="0" id="requirementApproverPagination_next">Next</a></div>
          </div>
        </div>
      </div>
      <!-- Pagenation Ends --> 
    </div>
  </div>
  </sec:authorize>
  <sec:authorize access="hasAnyRole('ROLE_VIEW_REQUIREMENT')">
  <div class="col-md-6 mb">
    <div class="dashNewWrap mb10">
      <div class="dashNewWrapHead clearfix">
        <h4 class="pull-left">Applicant Permission</h4>
        <ul class="nav nav-tabs pull-right">
          <li class="active" id="acceptedTab"><a data-toggle="tab" href="javascript:void(0)" onclick="ApplicantAuthorization('requestSent')">Sent</a></li>
          <li id="requestSentTab"><a data-toggle="tab" href="javascript:void(0)" onclick="ApplicantAuthorization('accepted')">Accepted</a></li>
          <li id="rejectedTab"><a data-toggle="tab" onclick="ApplicantAuthorization('REJECTED');">Rejected</a></li>
        </ul>
      </div>
      <!-- <a href="javascript:void(0)" class="collpase-div pull-right" title="SlideUp/SlideDown"><i aria-hidden="true" class="fa fa-angle-down"></i></a> -->
      <div class="clearfix project_tabs_inn_table dashboard-data newDashPadSearch dataSeaNew">
        <div class="success_pop" id="selector4" style="display: none;"><i class="fa fa-check-circle"></i><span id="eduMsgDiv"></span></div>
        <div class="tab-content clearfix">
          <div id="accepted" class=""></div>
          <div id="requestSent" class="tab-pane fade in active">
            <div id="rejectApplicantAlert"></div>
            <div id="applicantsPagination_wrapper" class="dataTables_wrapper no-footer">
              <div id="applicantsPagination_filter" class="dataTables_filter">
                <label>
                  <input type="search" class="" placeholder="" aria-controls="applicantsPagination">
                </label>
              </div>
              <table id="applicantsPagination">
                <thead>
                  <tr role="row">
                    <th >Applicant</th>
                    <th>Job</th>
                    <th>Team Member</th>
                    <th>Status</th>
                    <th>Action</th>
                  </tr>
                </thead>
              </table>
              <div class="dataTables_info" id="applicantsPagination_info" role="status" aria-live="polite"></div>
              <div class="dataTables_paginate paging_simple_numbers" id="applicantsPagination_paginate"><a class="paginate_button previous disabled" aria-controls="applicantsPagination" data-dt-idx="0" tabindex="0" id="applicantsPagination_previous">Previous</a><span></span><a class="paginate_button next disabled" aria-controls="applicantsPagination" data-dt-idx="1" tabindex="0" id="applicantsPagination_next">Next</a></div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  </sec:authorize>
  
  <div class="clearfix"></div>
  <div class="col-md-12">
    <div class="mb eventAlert clearfix col-md-12 noPad">
      <div class="dashNewWrap clearfix mb10">
        <div class="dashNewWrapHead event_calendar">
          <h4 class="pull-left">Events</h4>
          <button class="btn btn-primary pull-left btn-sm ml mt7" onclick="dashboardAddInterview();" type="button">Schedule Interview</button>
          <ul class="nav nav-tabs pull-right">
            <li id="jobFairsTab" class="active"><a href="javascript:void(0)" onclick="getJobFairs()">Job Fairs</a></li>
            <li id="interviewsTab" class=""><a href="javascript:void(0)" onclick="getInterviewsandTasks()">Interviews &amp; Tasks</a></li>
            <div class="clearfix"></div>
          </ul>
        </div>
        
        <!-- show Calendar -->
        <div class="col-xs-12 col-md-5 mb mt">
          <div id="eventCalendar" style="display: none;"></div>
          <div id="jobFairCalendar" style="" class="hasDatepicker">
            <div class="ui-datepicker-inline ui-datepicker ui-widget ui-widget-content ui-helper-clearfix ui-corner-all" style="display: block;">
              <div class="ui-datepicker-header ui-widget-header ui-helper-clearfix ui-corner-all"><a class="ui-datepicker-prev ui-corner-all" data-handler="prev" data-event="click" title="Prev"><span class="ui-icon ui-icon-circle-triangle-w">Prev</span></a><a class="ui-datepicker-next ui-corner-all" data-handler="next" data-event="click" title="Next"><span class="ui-icon ui-icon-circle-triangle-e">Next</span></a>
                <div class="ui-datepicker-title">
                  <select class="ui-datepicker-month" data-handler="selectMonth" data-event="change">
                  </select>
                  <select class="ui-datepicker-year" data-handler="selectYear" data-event="change">
                  </select>
                </div>
              </div>
              <table class="ui-datepicker-calendar">
                <thead>
                  <tr>
                    <th scope="col" class="ui-datepicker-week-end"><span title="Sunday">Su</span></th>
                    <th scope="col"><span title="Monday">Mo</span></th>
                    <th scope="col"><span title="Tuesday">Tu</span></th>
                    <th scope="col"><span title="Wednesday">We</span></th>
                    <th scope="col"><span title="Thursday">Th</span></th>
                    <th scope="col"><span title="Friday">Fr</span></th>
                    <th scope="col" class="ui-datepicker-week-end"><span title="Saturday">Sa</span></th>
                  </tr>
                </thead>
              </table>
            </div>
          </div>
        </div>
        
        <!-- show Calendar -->
        <div class="col-xs-12 col-md-7 mb mt alert alert-dismissable positionRelative" id="dashboardEventsDiv" style="display: none;">
          <div class="eventHeader">
            <div class="media">
              <div class="media-left bg-success paddingRight"> <i class="fa fa-calendar fa-3x"></i> </div>
              <div class="media-body paddingLeft">
                <h4 class="media-heading" id="dayOfweek"></h4>
                <p class="small" id="selectedDate"></p>
              </div>
            </div>
          </div>
          <div class="eventCalendr_content">
            <div id="interviewDiv"></div>
            <div id="taskDiv"></div>
          </div>
        </div>
        <div class="col-xs-12 col-md-7 mb mt alert alert-dismissable positionRelative" id="dashboardJobFairsDiv" style="">
          <div class="eventHeader">
            <div class="media">
              <div class="media-left bg-success paddingRight"> <i class="fa fa-calendar fa-3x"></i> </div>
              <div class="media-body paddingLeft">
                <h4 class="media-heading" id="dayOfweekForJobFair">Tuesday</h4>
                <p class="small" id="selectedDateForJobFair">Nov 20 2018</p>
              </div>
            </div>
          </div>
          <div class="eventCalendr_content">
            <div id="fairJobsDiv">
              <div class="row">
                <div class="dataTables_empty" style="border: 1px solid #ddd;margin: 0px 15px;"> </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="add_inter_wrap">
        <div class="new_side_wrap_inn" id="addInterviewDiv"></div>
      </div>
    </div>
  </div>
  <sec:authorize access="hasAnyRole('ROLE_VIEW_CONTRACT')">
  <div class="clearfix"></div>
  <div class="col-md-12">
    <div class="col-md-12 mb clearfix noPad" id="contractListDiv" style="display: block;">
      <div class="dashNewWrap mb10">
        <div class="dashNewWrapHead noBor clearfix">
          <h4 class="pull-left">Contracts</h4>
        </div>
        <div class="newDashPad">
          <div class="clearfix project_tabs_inn_table dashboard-data filter_position">
            <div id="contractList">
              <div id="contractListPagination_wrapper" class="dataTables_wrapper no-footer">
                <table id="contractListPagination" class="display dataTable no-footer" role="grid" aria-describedby="contractListPagination_info" style="width: 100%;" width="100%" cellspacing="0">
                  <thead>
                    <tr role="row">
                      <th>Applicant Name</th>
                      <th>Job</th>
                      <th>Rate</th>
                      <th>Join Date<br>
                        (<small class="custom-small">DD-MM-YYYY</small>)</th>
                      <th>Vendor</th>
                      <th>Contact</th>
                      <th>Action</th>
                    </tr>
                  </thead>
                  </tbody>
                </table>
                <div class="dataTables_info" id="contractListPagination_info" role="status" aria-live="polite"></div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  </sec:authorize>
  
  
  
  <div class="col-md-12">
    <div class="modal fade" id="myModal" role="dialog">
      <div class="modal-dialog modal-lg" style="width: 98%;">
        <div class="modal-content">
          <div class="modalHeader">
            <button type="button" id="assignToPopUpCloseId" class="close" data-dismiss="modal">×</button>
            <h4 class="modal-title">Assign To<i class="fa fa-info-circle" title="Assign the job to the internal team to process the resources for the job"></i></h4>
          </div>
          <div class="modal-body">
            <div class="row">
              <div class="clearfix"></div>
              <div class="swaptables">
                <div class="row positionRelative">
                  <div class="col-md-6 mb5 bootSelectpicker">
                    <div class="btn-group bootstrap-select show-tick">
                      <button type="button" class="btn dropdown-toggle btn-default" data-toggle="dropdown" data-id="teamSelectionCategory" title="--Select Assign Team--"><span class="filter-option pull-left">--Select Assign Team--</span>&nbsp;<span class="bs-caret"><span class="caret"></span></span></button>
                      <div class="dropdown-menu open">
                        <div class="bs-actionsbox">
                          <div class="btn-group btn-group-sm btn-block">
                            <button type="button" class="actions-btn bs-select-all btn btn-default">Select All</button>
                            <button type="button" class="actions-btn bs-deselect-all btn btn-default">Deselect All</button>
                          </div>
                        </div>
                        <ul class="dropdown-menu inner" role="menu">
                        </ul>
                      </div>
                      <select id="teamSelectionCategory" class="selectpicker" multiple="" data-actions-box="true" onchange="filterTeamMembers();" style="display: block !important" title="--Select Assign Team--" tabindex="-98">
                      </select>
                    </div>
                  </div>
                  <!-- <div class="remove_search fa-pull-right">
								<input placeholder="Search" type="text">
							</div> --> 
                </div>
                <div class="clearfix positionRelative assignTableScroll  assignTableScroll-mnh">
                  <div id="usersGrid-loader"></div>
                  <table id="assignToDataTableId" class="display table table-stripped table-bordered dataTable no-footer" role="grid" aria-describedby="assignToDataTableId_info" style="width: 100%;" cellspacing="0">
                    <thead>
                      <tr role="row">
                        <th style="vertical-align: middle"><input type="checkbox" id="checkAll"></th>
                        <th style="vertical-align: middle">Name</th>
                        <th style="vertical-align: middle">Email</th>
                        <th style="vertical-align: middle">Role</th>
                        <th>Active<br>
                          Jobs</th>
                      </tr>
                    </thead>
                    <tbody id="usersGrid">
                    </tbody>
                  </table>
                  <div class="clearfix"></div>
                </div>
              </div>
              <div class="swaptables-buttons">
                <div class="swaptables-buttons-inner">
                  <div class="btn-arrow clearfix mb"> <i class="fa fa-2x fa-arrow-circle-right cursorPointer" onclick="selectAssignUsers();"></i> </div>
                  <div class="btn-arrow clearfix"> <i class="fa fa-2x fa-arrow-circle-left cursorPointer" onclick="deSelectAssignUsers();"></i> </div>
                </div>
              </div>
              <div class="swaptables">
                <div class="clearfix mt40 assignTableScroll" style="height:250px; overflow-y:auto;" id="assignToSelectedDataTableDiv">
                  <table id="assignToSelectedDataTableId" class="display table table-stripped table-bordered dataTable no-footer" role="grid" aria-describedby="assignToDataTableId_info" style="width: 100%;" cellspacing="0">
                    <thead>
                      <tr role="row">
                        <th style="vertical-align: middle"><input id="checkAllSelected" type="checkbox"></th>
                        <th style="vertical-align: middle"><span class="tbldata-dotdot-custom">Name</span></th>
                        <th style="vertical-align: middle"><span class="tbldata-dotdot-100">Email</span></th>
                        <th style="vertical-align: middle"><span class="tbldata-dotdot_80">Role</span></th>
                        <th><span class="tbldata-dotdot_30">Active<br>
                          Jobs</span> </th>
                      </tr>
                    </thead>
                    <tbody id="selectedUsersGrid">
                    </tbody>
                  </table>
                  <div class="panel-default text-center" id="noAssignedmembers">
                    <div class="panel-body"> <i class="fa fa-users fa-3x text-muted mt mb"></i>
                      <p>No Assigned Members</p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="clearfix mb">
            <div class="pull-right mr" id="summaryButtonsDiv">
              <button type="button" class="btn btn-primary mr5 btn-sm" data-dismiss="modal" onclick="getAssignedTeamsOrUsers();">Assign<span id="assignToSelectedPopupCount"> (0) </span></button>
              <button type="button" class="btn btn-default btn-sm" data-dismiss="modal" onclick="clearAssignedTeamsOrUsers();">Reset</button>
            </div>
            <div class="pull-right mr" id="listButtonsDiv" style="display: none;">
              <button type="button" id="listSaveButton" class="btn btn-primary mr5 btn-sm" data-dismiss="modal">Save</button>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- Modal Pop ends --> 
    
    <script>
function getAssignedMembersListForEdit(reqId){
	if(reqId != '' && reqId != 'undefined' && reqId != null){
		$.ajax({
	           type : "POST",
	           url: sourceleadUrl+"/getAssignedMembersForEdit?"+tokenParamName+"="+tokenValue+"&reqId="+reqId,    
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

function getTeamsList(reqId){
	  $.ajax({
           type : "POST",
           url: sourceleadUrl+"/getTeamsList?"+tokenParamName+"="+tokenValue+"&reqId="+reqId,    
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
</script> 
    <script type="text/javascript">
	function filterTeamMembers(){
		var teamType = $("#teamSelectionCategory").val();
		getUserList(teamType);
	}
</script> 
    <script>
window.selectAssignUsersIds = new Array();
var isIE = /*@cc_on!@*/false || !!document.documentMode;
if(isIE){
(function() {
	  function remove() { this.parentNode && this.parentNode.removeChild(this); }
	  if (!Element.prototype.remove) Element.prototype.remove = remove;
	  if (Text && !Text.prototype.remove) Text.prototype.remove = remove;
	})();
};	
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
</script> 
    <script>
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
           url: sourceleadUrl+"/getUserList?"+tokenParamName+"="+tokenValue+"&teamType="+teamType,    
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
</script> 
    <script>
function paginations(){
    $("#assignToDataTableId").DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[5, 10, 15, -1], [5, 10, 15, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"aaSorting": [[1,'desc']],
    	"ordering": true,
    	"aoColumnDefs": [{
	           'bSortable': false,
	           'aTargets': 0
			}],
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	},
    });
    $("#assignToDataTableId").wrap("<div class='modal-body-custom mailerpopup modalAlign'></div>");
}
</script> 
    <script>
	$(':checkbox[id=checkAll]').click(function() {
		//toggle all checkboxes except disabled checkboxes
		$(':checkbox[name=team]').not("[disabled]").prop('checked', this.checked);
	});

	$(':checkbox[id=checkAllSelected]').click(function() {
		//toggle all checkboxes
		$(':checkbox[name=selectedUser]').prop('checked', this.checked);
	});
	
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
		           url: sourceleadUrl+"/saveOrUpdateAssignToTeam?"+tokenParamName+"="+tokenValue+"&assignedIdVal="+assignedIdVal+"&requirementId="+requirementId,    
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
</script> 
    <script>
window.teamRoleIds = new Array();
function handleTeamRoleClick() {
	teamRoleIds = [];
	$("option[name=TeamAndRole]:selected").each(function(){
		teamRoleIds.push($(this).val());
	});
}

/* this function is for block remaining duplicate users */
function handleChange(e){
	$("#assignToDataTableId input[type=checkbox]").each(function(){
		/* if(e.checked){
			selectAssignUsersIds.push($(this).val());
		} */
	    if(e.checked && e.id == $(this).attr('id') && e.value != $(this).attr('value')){
	    	$(this).prop('disabled', true);
	    	$(this).addClass('fa fa-ban');
	    }else if(!e.checked && e.id == $(this).attr('id')){
	    	$(this).prop('disabled', false);
	    	$(this).removeClass('fa fa-ban');
	    }
	});
}

$('#assignToPopUpCloseId').submit(function() {
	$("#usersGrid").empty();
});
</script>
   
    
    
    <div class="side_mask">
      <div class="proj_close"></div>
    </div>
    <script> 
$(document).ready(function(){
	requirementList(false, -1);
});
</script>
    <style>
#resumesDiv select{
width: 100% !important;
}
</style>
    <script>
function requirementList(isWalkIn, walkInDay){  
	
	  var sourceleadUrl ='${sourceleadUrl}';
	  var orgATS = 'Y';
	  var orgVMS = 'Y';
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
// 	                     result = result +"    <th>Release</th>"; 
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
		                     
		                     
// 		                     if(opt.noOfApplicants != null && opt.noOfApplicants != '' && opt.noOfApplicants != 'undefined'){
	
	                    	 result = result +"<td><input type='hidden' class='requirementDashboardCount'  id='"+opt.id+"' /><a href='/app/requirementSummary?type=applicants&requirementUUID="+opt.requirementUUID+"'><div id='"+opt.id+"applicantCount'><i class='fa fa-spinner fa-pulse'></i></div></a></td>";
	                    	 
// 		                     }
// 		                     if(opt.noOfShortlistedApplicants != null && opt.noOfShortlistedApplicants != '' && opt.noOfShortlistedApplicants != 'undefined'){
	
		                     result = result +"<td><a href='/app/requirementSummary?type=SHORTLIST&requirementUUID="+opt.requirementUUID+"'><div id='"+opt.id+"shortlistedCount'><i class='fa fa-spinner fa-pulse'></i></div></a></td>";
		                     
// 		                     }
// 		                     if(opt.noOfSubmittedApplicants != null && opt.noOfSubmittedApplicants != '' && opt.noOfSubmittedApplicants != 'undefined'){
	
			                 result = result +"<td><a href='/app/requirementSummary?type=SUBMIT_TO_CLIENT&requirementUUID="+opt.requirementUUID+"'><div id='"+opt.id+"submittedCount'><i class='fa fa-spinner fa-pulse'></i></div></a></td>";
			                 
// 		                     }
// 		                     if(opt.noOfInterviewedApplicants != null && opt.noOfInterviewedApplicants != '' && opt.noOfInterviewedApplicants != 'undefined'){
		                     result = result +"<td><a href='/app/requirementSummary?type=INTERVIEW&requirementUUID="+opt.requirementUUID+"'><div id='"+opt.id+"interviewedCount'><i class='fa fa-spinner fa-pulse'></i></div></a></td>";
// 		                     }
// 		                     if(opt.noOfOfferedApplicants != null && opt.noOfOfferedApplicants != '' && opt.noOfOfferedApplicants != 'undefined'){
		                     result = result +"<td><a href='/app/requirementSummary?type=OFFERMADE&requirementUUID="+opt.requirementUUID+"'><div id='"+opt.id+"offeredCount'><i class='fa fa-spinner fa-pulse'></i></div></a></td>";
// 		                     }
// 		                     if(opt.noOfJoinedApplicants != null && opt.noOfJoinedApplicants != '' && opt.noOfJoinedApplicants != 'undefined'){
			                     result = result +"<td><a href='/app/requirementSummary?type=JOINED&requirementUUID="+opt.requirementUUID+"'><div id='"+opt.id+"joinedCount'><i class='fa fa-spinner fa-pulse'></i></div></a></td>";
// 			                     }



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
</script> 
    <script>
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
</script> 
    <script>
function getDynamicDayActionsForWalkInJobs(prevDay, selectedDay, nextDay){
	var text="<div class='walk_in_days pull-right mb10 mt10'>";
	 text += "<a  class='btn btn-default pull-left mr btn-sm' href='javascript:void(0)' onclick='requirementList(true," + prevDay + ")'>&lt;&lt;</a>";
	 text += "<div class='paginate_button pull-left'>" + selectedDay + "</div>";
	 text += "<a  class='btn btn-default pull-left ml btn-sm' href='javascript:void(0)' onclick='requirementList(true," + nextDay + ")'>&gt;&gt;</a>";
	 text += "</div>";
	 return text;
}
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
</script> 
    <script>
function getAssignedMembersDiv(reqId){
	$("#selectedUsersGrid").empty();
	$("#summaryButtonsDiv").hide();
	$("#listButtonsDiv").show();
	$('#listSaveButton').removeAttr('onclick');
	$('#listSaveButton').attr('onClick', 'editAssignedTeamsOrUsers('+reqId+');');
	getAssignedMembersListForEdit(reqId);
	
}
</script>
    <link rel="stylesheet" href="/resources/css/style.css?staticTime=1542288668111">
    <script src="/resources/js/submissionFormBuilder.js?staticTime=1542288668111" type="text/javascript"></script> <!-- form builder --> 
    <script src="/resources/js/submissionForm-html-generator.js?staticTime=1542288668111" type="text/javascript"></script> <!-- form generator -->
    
    <style type="text/css">
.mt10 {
    margin-top: 10px !important;
}
</style>
    
    <!-- For add New Applicant --> 
    <script>
function getAddApplicant(requirementId,reqCoolingOffPeriod){
	$('.action-pop').slideUp();
	var sourceleadUrl ='${sourceleadUrl}';
	var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}';
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
	generateOrgSubmissionForm(sourceleadUrl,requirementId,finalDateFormatScript)
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
	    	 var sourceleadUrl ='${sourceleadUrl}';
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
</script> 
    <script type="text/javascript">
function ApplicantPermit(){
	 if($("#needApplicantPermit").is(':checked')){
		  $("#needApplicantPermit").val("Y");
	 }else{
		 $("#needApplicantPermit").val("N");
	 }
}
</script> 
    <script type="text/javascript">
function NotifyToApplicant(){
	 if($("#notifyToApplicantId").is(':checked')){
		  $("#notifyToApplicantId").val("Y");
	 }else{
		 $("#notifyToApplicantId").val("N");
	 }
}
</script>
    <div id="popUp"></div>
    <script>
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
</script> 
    <script>
function proceedApplicant(){
	$("#popUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script> 
    <script>
function discordApplicant(){
	$("#save_button").attr("disabled", "disabled");
	$("#popUp").empty();
	$(".project_inn_mask").fadeOut(200);
	
	$(".new_side_wrap").removeClass("new_side_wrap_active");
	$(".side_mask").fadeOut(200);
	$("body").css('overflow-y', 'auto');
}
</script> 
    <script type="text/javascript">
 function isCoolingOff(requirementId){
	var candidateEmail =   $("input[name=EmailId]").val();
	var sourceleadUrl ='${SourceLeadurl}';
	var reqCoolingOffPeriod = document.getElementById("reqCoolingOffPeriod").value;
	var orgLocationId ='${SourceLeadWeb_orgLocationId}';// replaced with common vai

	$.ajax({
		type: "POST",
	    url: sourceleadUrl+"/verifyCoolingOffPeriod?=&requirementId="+requirementId+"&candidateEmail="+candidateEmail+"&reqCoolingOffPeriod="+reqCoolingOffPeriod+"&orgLocationId="+orgLocationId,
	    success: function(response) {
	    	if (response.response === "AWKWARD") {
	    	    location.href = "/errorPage";
	    	} else if (response.response === "SUCCESS") {
	    		if(response.object === true){
		    		isCoolingOffAlert();
		    		return true;
		    	} else {
		    		return false;
		    	}
	    	} else if (response.indexOf('Sign In') !== -1) {
	    		location.replace(sourceleadUrl);
	    	} else {
	    	    showError("Operation failed, please try again");
	    	}
	    },
	});
}
</script> 
    <script type="text/javascript">
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
	 
	 var sourceleadUrl ='${sourceleadUrl}';
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
</script> 
    
    <!-- For add New Applicant --> 
    <script>
    $(".addApplicant_close, .side_mask").click(function() {
    	$(".new_side_wrap").removeClass("new_side_wrap_active");
		$(".side_mask").fadeOut(200);
		$("body").css('overflow-y', 'auto');
	});	
</script> 
    
    <!-- For add New Applicant --> 
    <script type="text/javascript">
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
  
  </script> 
    <script type="text/javascript">
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };
</script> 
    <script type="text/javascript">
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
</script> 
    <script>
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
</script> 
    <script type="text/javascript">
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
</script> 
    <script type="text/javascript">
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
</script> 
    <script type="text/javascript">
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
</script> 
    <script>
function getExpYears(){
	var expYears = $("select[name=WorkExperience]").val();
	 if(expYears == "25+"){
		 $("#monthExp").attr("disabled", "disabled");
	 }else{
		 $("#monthExp").removeAttr("disabled");
	 }
}
</script> 
    <script type="text/javascript">
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
</script> 
    <script>
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
</script> 
    <script>
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
</script> 
    <script type="text/javascript">
function init(){
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
</script> 
    <script type="text/javascript">
function setRateTypesByLocation(){
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
</script> 
    <script>
  function dobDatePicker() {
	  $('#DOB').datepicker({
	      dateFormat: 'yy-mm-dd',
	  });
	  
  }
</script> 
    <script>
 $(document.body).on('change', '#uploadBtn' ,function(){
	 $('#uploadFile').val(this.value.split( '\\' ).pop());
	 $("#uploadFile").focus();
 	 if(validate_fileupload(this.value)){
 		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		var form = $('#addApplicant_form')[0];
  		var formData = new FormData(form); 
		var sourceleadUrl = '${sourceleadUrl}';
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

</script> 
    
    <!-- Country,State,City Script Starts Here --> 
    <script type="text/javascript">
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
</script> 
    <script type="text/javascript">
 function validateSubmissionCandidateEmail() {
	 var email =   $("input[name=EmailId]").val();
	$('#emailDiv').find('p.jquery_form_error_message').remove();
	 var sourceleadUrl ='${sourceleadUrl}';
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
 </script> 
    <script type="text/javascript">
 function getUserByUserEmail() {
	 var emailId =   $("input[name=EmailId]").val();
	 var sourceleadUrl ='${sourceleadUrl}';
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
 </script> 
    <script>
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
 </script> 
    <script>
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
 </script> 
    <script>
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
 </script> 
    <script>
 function validateCurrentCTCRatePer(){
	 var currentCTCRatePer = document.getElementById("currentCTCRatePer").value;
	 if(currentCTCRatePer){
		 $("input[name=CurrentCTC]").removeAttr("data-validation-optional");
		 $("input[name=CurrentCTC]").attr("data-validation", "required validate_Space validate_float validate_length length1-8");
	 }else{
		 $("input[name=CurrentCTC]").removeAttr("data-validation");
		 $("input[name=CurrentCTC]").attr("data-validation-optional", "true");
		 $("input[name=CurrentCTC]").attr("data-validation", "validate_Space validate_float validate_length length1-8");
	 }
 }
 </script> 
    <script>
 function changeRateLabels(){
	 
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
 </script> 
    <script>
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
 </script> 
    <script>
 function getVendorsByOrgLocationForAddApplicant(){
	 
	 var sourceleadUrl ='${sourceleadUrl}';
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
 </script> 
    <script>
 function getAddApplicantVendorContactList(){
	 var orgRelationID = $('#vendors').val();
	 var sourceleadUrl ='${sourceleadUrl}';
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
 </script> 
    <script>
function uploadAddApplicantCandidateDoc(){
	fileUpload("newUploadBtn", "addApplicants", "addApplicant_form");
}
</script> 
    <script>
function importAddApplicantDoc(){
	fileUpload("uploadBtn", "addApplicants", "addApplicant_form");
}
</script> 
  </div>
</div>

	</section>
</div>
</body>
<script>
var sourceleadUrl ='${sourceleadUrl}'; // setting common user settings 
var locationId = '${SourceLeadWeb_orgLocationId}';
var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
var userId = '${SourceLeadWeb_user.id}';
var orgLocationCode = '${SourceLeadWeb_locationCode}';
var candiadteId = '${SourceLeadWeb_candidateId}';
var isGlobalAccess = '${SourceLeadWeb_isGlobalAccess}';
var loginUserRole = '${SourceLeadWeb_loginUserRole}';
var orgATS = '${SourceLeadWeb_ats}';
var orgVMS = '${SourceLeadWeb_vms}';
var approverUserId = '${SourceLeadWeb_user.id}';
var loginUser = '${SourceLeadWeb_userName}';
var date = $("#eventCalendar").val();
var timezone ='${SourceLeadWeb_orgLocations.timezone}'; 
var requirementId = '${requirementId}';
var timezoneId = "";
if(timezone != null && timezone != '' && timezone != 'undefined'){
	timezoneId = '${SourceLeadWeb_orgLocations.timezone.id}';
}
var country = '${SourceLeadWeb_orgLocations.country}';

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
</script>
</html>

<script>
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
	 $("#timeSheetLink").attr("href", "<c:url value='/app/timesheet'/>");
 }else{
	 $("#timeSheetLink").attr("href", "<c:url value='/app/timesheetApproval'/>");
 }
} 
</script>
<!-- -Jobs tab code -->
<script type="text/javascript">
function loadRequirementApplicants(){
	var tab = $("#dateActiveTab").val();
	var sourceleadUrl = '${sourceleadUrl}';
	var orgLocId = '${SourceLeadWeb_orgLocationId}';
	window.location.href = sourceleadUrl + "/requirementApplicantsList?&tab="+tab+"&orgLocationId="+orgLocId;
}
function loadRequirementSubmissions(){
	var tab = $("#dateActiveTab").val();
	var sourceleadUrl = '${sourceleadUrl}';
	var orgLocId = '${SourceLeadWeb_orgLocationId}';
	window.location.href = sourceleadUrl + "/requirementSubmissionsList?&tab="+tab+"&orgLocationId="+orgLocId;;
}
function loadRequirementInterviews(){
	var tab = $("#dateActiveTab").val();
	var sourceleadUrl = '${sourceleadUrl}';
	var orgLocId = '${SourceLeadWeb_orgLocationId}';
	window.location.href = sourceleadUrl + "/requirementInterviewsList?&tab="+tab+"&orgLocationId="+orgLocId;;
}
function loadRequirementOffers(){
	var tab = $("#dateActiveTab").val();
	var sourceleadUrl = '${sourceleadUrl}';
	var orgLocId = '${SourceLeadWeb_orgLocationId}';
	window.location.href = sourceleadUrl + "/requirementOffersList?&tab="+tab+"&orgLocationId="+orgLocId;;
}
</script>     
<script type="text/javascript">
function requirementSubmissionsCount(daterange){
	$("#dateActiveTab").val(daterange);
	$("#applicantsCountLoading, #submissionsCountLoading, #interviewsCountLoading, #offersCountLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:5; background-image:url("/resources/images/Spinner.gif"); background-position:center; background-repeat:no-repeat; background-size:60px;');
    var sourceleadUrl ='${sourceleadUrl}';
    var userId =  '${SourceLeadWeb_user.id}';
    var orgLocationId = '${SourceLeadWeb_orgLocationId}';
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+"/getRequirementSubmissionsCount?${_csrf.parameterName}=${_csrf.token}&daterange="+daterange+"&userId="+userId+"&orgLocationId="+orgLocationId, 
		        success: function(data) {
		        	if (data.response === "FAILED") {
			    		showError(data.responseMessage);
			    	} else if (data.response === "AWKWARD") {
			    		location.href = "/errorPage";
			    	} else if (data.response === "SUCCESS") {
			        	var json = data.object;
			        	$("#applicantsCountLoading, #submissionsCountLoading, #interviewsCountLoading, #offersCountLoading").removeAttr("style");
			        	 var  submissionsCount = json.submissionCount;
			        	 var  applicantsCount =json.applicantsCount;
			        	 var  interviewsCount =json.interviewsCount;
			        	 var  offersCount = json.offersCount; 
			        	 if(submissionsCount>0){
		                      $("#submissionsCount").text(submissionsCount);
		                      $('#submissionsCount').attr("onClick", "loadRequirementSubmissions(); return false;"); 
		                      $('#subDiv').attr("style", "cursor:pointer"); 
		                  }else{
		                      $("#submissionsCount").html("0");
		                      $("#submissionsCount").removeAttr("onclick");
		                      $("#subDiv").removeAttr("style");
		                  } 
			        	 if(applicantsCount>0){
		                      $("#applicantsCount").text(applicantsCount);
		                      $('#applicantsCount').attr("onClick", "loadRequirementApplicants(); return false;"); 
		                      $('#appDiv').attr("style", "cursor:pointer"); 
		                  }else{
		                      $("#applicantsCount").html("0");
		                      $("#applicantsCount").removeAttr("onclick");
		                      $("#appDiv").removeAttr("style");
		                  }
			        	if(interviewsCount>0){
		                      $("#interviewsCount").text(interviewsCount);
		                      $('#interviewsCount').attr("onClick", "loadRequirementInterviews(); return false;"); 
		                      $('#intDiv').attr("style", "cursor:pointer"); 
		                  }else{
		                      $("#interviewsCount").html("0");
		                      $("#interviewsCount").removeAttr("onclick");
		                      $("#intDiv").removeAttr("style");
		                  }
			        	if(offersCount>0){
		                      $("#offersCount").text(offersCount);
		                      $('#offersCount').attr("onClick", "loadRequirementOffers(); return false;"); 
		                      $('#offDiv').attr("style", "cursor:pointer"); 
		                  }else{
		                      $("#offersCount").html("0");
		                      $("#offersCount").removeAttr("onclick");
		                      $("#offDiv").removeAttr("style");
		                  } 
			    	}else if (data.indexOf('Sign In') !== -1) {
			    		location.replace(sourceleadUrl);
			    	} else {
			    		showError("Operation failed, please try again");
			    	}
		 		}
		  });
		 
		 if(daterange == "today"){
			 $("#jobsLink").attr("href", "<c:url value='/app/jobs'/>");
		 }else{
			 $("#jobsLink").attr("href", "<c:url value='/app/jobs'/>");
		 }
		 
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
	// var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	$.ajax({
				type : "GET",
				url : sourceleadUrl
						+ "/getRequirementSubmissionsCount?daterange="
						+ daterange + "&userId=" + userId + "&orgLocationId="
						+ orgLocationId,
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
		$("#jobsLink").attr("href", "<c:url value='/app/jobs'/>");
	} else {
		$("#jobsLink").attr("href", "<c:url value='/app/jobs'/>");
	}

}// Jobs End
//Expensive count start.
function expensesCount(expenseType) {
	// var sourceleadUrl ='${sourceleadUrl}';
	$
			.ajax({
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
		$("#expensesLink").attr("href", "<c:url value='/app/expenses'/>");
	} else {
		$("#expensesLink").attr("href",
				"<c:url value='/app/expensesApprovalList'/>");
	}
}// expensive count end.
function requirementList(isWalkIn, walkInDay) {

	// var sourceleadUrl ='${sourceleadUrl}';
	// var orgATS = '${SourceLeadWeb_ats}';
	// var orgVMS = '${SourceLeadWeb_vms}';
	$("#requirementListActions").empty();
	$("#requirementList").empty();
	$("#requirementLoading").show();
	$("#requirementLoading")
			.attr(
					'style',
					'position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	$.ajax({
				type : "POST",
				url : sourceleadUrl
						+ "/getAssignedRequirementListByOrgLocationIDAndUserId?"
						+ (isWalkIn ? "isWalkIn=" + isWalkIn + "&walkInDay="
								+ walkInDay : ""),
				success : function(data) {
					if (data.response === "FAILED") {
						showError(data.responseMessage);
					} else if (data.response === "AWKWARD") {
						location.href = "/errorPage";
					} else if (data.response === "SUCCESS") {
						var json = data.data;
						if (json != null && json != 'undefined' && json != ""
								&& json != '[]') {
							$("#requirementList").empty();
							var result = "";
							result = result
									+ "<table id='requirementPagination' class='display' cellspacing='0' width='99.9%' >";
							result = result + "<thead>";
							result = result + "  <tr>";
							result = result + "    <th>Actions</th>";
							result = result + "    <th>Generated ID</th>";
							result = result + "    <th>Title</th>";
							if (isWalkIn) {
								result = result + "    <th>Drive Day(s)</th>";
							}
							if (orgATS == 'Y') {
								result = result + "    <th>Client</th>";
							}
							result = result + "    <th>Rate</th>";
							result = result + "    <th>Priority</th>";
							result = result + "    <th>Applicants</th>";
							result = result + "    <th>Shortlisted</th>";
							result = result + "    <th>Submitted</th>";
							result = result + "    <th>Interview</th>";
							result = result + "    <th>Offered</th>";
							result = result + "    <th>Joined</th>";
							result = result + "    <th>Status</th>";
							// result = result +" <th>Release</th>";
							result = result + "  </tr>";
							result = result + "</thead>";
							result = result + "<tbody>";

							result = result + '<div class="new_side_wrap">';
							result = result + '<div class="new_side_wrap_inn">';
							result = result + '<div class="new_side_wrap_cta">';
							result = result
									+ '<div id="addApplicantDiv"></div>';
							result = result + '</div>';
							result = result + '</div>';
							result = result + '</div>';

							var checkSize = json.length;
							if (checkSize > 10) {
								checkSize = 10;
							} else {
								checkSize = json.length;
							}
							for (var i = 0; i < checkSize; i++) {
								var opt = json[i];

								result = result + "<tr>";
								result = result + "<td>";
								result = result
										+ '<div class="tblminwidth-75">';

								'<sec:authorize access="hasAnyRole(\'ROLE_ADD_APPLICANT\')">';
								if (opt.requirementSubmissionStatus == 'APPROVED'
										|| opt.requirementSubmissionStatus == 'OPEN'
										|| opt.requirementSubmissionStatus == 'NEW') {
									result = result
											+ '<div class="text-center pull-left"><a href="javascript:void(0)" title="Add Applicant" onclick="getAddApplicant('
											+ opt.id
											+ ',\''
											+ opt.reqCoolingOffPeriod
											+ '\');" class="add-applicant"><i class="fa fa-user-plus"></i></a></div>';
								} else {
									result = result
											+ "<div class='text-center pull-left'>---</div>";
								}
								'</sec:authorize>';

								'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_REQUIREMENT\')">';
								result = result
										+ '<div class="text-center pull-left ml10 mr10"><a href="javascript:void(0)" title="Assign To" data-toggle="modal" data-target="#myModal" onclick="getAssignedMembersDiv('
										+ opt.id
										+ ');"><i class="fa fa-address-book '
										+ (opt.assignedMembersCount == 0 ? "anchorBlack"
												: "") + '"></i></a></div>';
								'</sec:authorize>';

								'<sec:authorize access="hasAnyRole(\'ROLE_RELEASE_REQUIREMENT\')">';
								if (opt.requirementSubmissionStatus == 'APPROVED'
										|| opt.requirementSubmissionStatus == 'OPEN'
										|| opt.requirementSubmissionStatus == 'NEW') {
									if (opt.isRequirementReleasedByuser == 'Y') {
										result = result
												+ "<div class='pull-left'><a href='<c:url value='/app/releaseRequirementToClientOrVendors?requirementUUID="
												+ opt.requirementUUID
												+ "'/>'><i class='fa fa-bullhorn success' title='Release Job'></i></a></div>";
									} else {
										result = result
												+ "<div class='pull-left'><a href='<c:url value='/app/releaseRequirementToClientOrVendors?requirementUUID="
												+ opt.requirementUUID
												+ "'/>'><i class='fa fa-bullhorn' title='Release Job'></i></a></div>";
									}
								} else {
									result = result
											+ "<div class='pull-left'>---</div>";
								}
								'</sec:authorize>';
								result = result + '</div>';
								result = result + "</td>";

								if (opt.generatedId != null
										&& opt.generatedId != ''
										&& opt.generatedId != 'undefined') {
									result = result
											+ "<td><div class=''><span title='"
											+ opt.generatedId + "'>"
											+ opt.generatedId
											+ "</span></a></div></td>";
								} else {
									result = result + "<td><div>---</div></td>";
								}
								if (opt.jobId != null && opt.jobId != ''
										&& opt.jobId != 'undefined') {
									result = result
											+ "<td><div class='tbldata-dotdot-sm'> <a href='"
											+ sourceleadUrl
											+ "/requirementSummary?requirementUUID="
											+ opt.requirementUUID
											+ "'><span title='" + opt.jobTitle
											+ "'>" + opt.jobTitle
											+ "</span></a></div></td>";
								} else {
									result = result
											+ "<td><div class='tbldata-dotdot-sm'> <a href='"
											+ sourceleadUrl
											+ "/requirementSummary?requirementUUID="
											+ opt.requirementUUID
											+ "'><span title='" + opt.jobTitle
											+ "'>" + opt.jobTitle
											+ "</span></a></div></td>";
								}
								if (isWalkIn) {
									result = result
											+ "<td><div class=''><span title='"
											+ opt.walkInDays + "'>"
											+ opt.walkInDays
											+ "</span></a></div></td>";
								}
								if (orgATS == 'Y') {
									if (opt.clientName != null
											&& opt.clientName != ''
											&& opt.clientName != 'undefined') {

										'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CLIENT\',\'ROLE_VIEW_EDIT_CLIENT\')">';
										result = result
												+ '<td><div  class="tbldata-dotdot-sm"><a href="'
												+ sourceleadUrl
												+ '/clientDashboard?orgRelationUUID='
												+ opt.orgRelationUUID
												+ '" title="' + opt.clientName
												+ '">' + opt.clientName
												+ '</a></div></td>';
										'</sec:authorize>';

										'<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_CLIENT\',\'ROLE_VIEW_EDIT_CLIENT\')">';
										result = result
												+ '<td><div  class="tbldata-dotdot-sm">'
												+ opt.clientName
												+ '</div></td>';
										'</sec:authorize>';

									} else {
										result = result
												+ "<td><div>---</div></td>";
									}
								}
								if (opt.rate != null && opt.rate != ''
										&& opt.rate != 'undefined'
										&& opt.maxRate != null
										&& opt.maxRate != ''
										&& opt.maxRate != 'undefined') {
									result = result + "<td><div><span  title='"
											+ opt.currencyCode + "&nbsp;"
											+ opt.rate + "-" + opt.maxRate
											+ "&nbsp;" + opt.rateType + "'>"
											+ opt.currencyCode + "&nbsp;"
											+ opt.rate + "-" + opt.maxRate
											+ "&nbsp;" + opt.rateType
											+ "</span></div></td>";
								} else if (opt.rate != null && opt.rate != ''
										&& opt.rate != 'undefined') {
									result = result + "<td><div><span  title='"
											+ opt.currencyCode + "&nbsp;"
											+ opt.rate + "&nbsp;"
											+ opt.rateType + "'>"
											+ opt.currencyCode + "&nbsp;"
											+ opt.rate + "&nbsp;"
											+ opt.rateType
											+ "</span></div></td>";

								} else {
									result = result
											+ "<td><div><span><span>---</span></span></div></td>";
								}
								if (opt.priority != null && opt.priority != ''
										&& opt.priority != 'undefined') {
									result = result + "<td><div>"
											+ opt.priority + "</div></td>";
								} else {
									result = result + "<td><div>---</div></td>";
								}

								// if(opt.noOfApplicants != null &&
								// opt.noOfApplicants != '' &&
								// opt.noOfApplicants != 'undefined'){

								result = result
										+ "<td><input type='hidden' class='requirementDashboardCount'  id='"
										+ opt.id
										+ "' /><a href='<c:url value='/app/requirementSummary?type=applicants&requirementUUID="
										+ opt.requirementUUID
										+ "'/>'><div id='"
										+ opt.id
										+ "applicantCount'><i class='fa fa-spinner fa-pulse'></i></div></a></td>";

								// }
								// if(opt.noOfShortlistedApplicants != null &&
								// opt.noOfShortlistedApplicants != '' &&
								// opt.noOfShortlistedApplicants !=
								// 'undefined'){

								result = result
										+ "<td><a href='<c:url value='/app/requirementSummary?type=SHORTLIST&requirementUUID="
										+ opt.requirementUUID
										+ "'/>'><div id='"
										+ opt.id
										+ "shortlistedCount'><i class='fa fa-spinner fa-pulse'></i></div></a></td>";

								// }
								// if(opt.noOfSubmittedApplicants != null &&
								// opt.noOfSubmittedApplicants != '' &&
								// opt.noOfSubmittedApplicants != 'undefined'){

								result = result
										+ "<td><a href='<c:url value='/app/requirementSummary?type=SUBMIT_TO_CLIENT&requirementUUID="
										+ opt.requirementUUID
										+ "'/>'><div id='"
										+ opt.id
										+ "submittedCount'><i class='fa fa-spinner fa-pulse'></i></div></a></td>";

								// }
								// if(opt.noOfInterviewedApplicants != null &&
								// opt.noOfInterviewedApplicants != '' &&
								// opt.noOfInterviewedApplicants !=
								// 'undefined'){
								result = result
										+ "<td><a href='<c:url value='/app/requirementSummary?type=INTERVIEW&requirementUUID="
										+ opt.requirementUUID
										+ "'/>'><div id='"
										+ opt.id
										+ "interviewedCount'><i class='fa fa-spinner fa-pulse'></i></div></a></td>";
								// }
								// if(opt.noOfOfferedApplicants != null &&
								// opt.noOfOfferedApplicants != '' &&
								// opt.noOfOfferedApplicants != 'undefined'){
								result = result
										+ "<td><a href='<c:url value='/app/requirementSummary?type=OFFERMADE&requirementUUID="
										+ opt.requirementUUID
										+ "'/>'><div id='"
										+ opt.id
										+ "offeredCount'><i class='fa fa-spinner fa-pulse'></i></div></a></td>";
								// }
								// if(opt.noOfJoinedApplicants != null &&
								// opt.noOfJoinedApplicants != '' &&
								// opt.noOfJoinedApplicants != 'undefined'){
								result = result
										+ "<td><a href='<c:url value='/app/requirementSummary?type=JOINED&requirementUUID="
										+ opt.requirementUUID
										+ "'/>'><div id='"
										+ opt.id
										+ "joinedCount'><i class='fa fa-spinner fa-pulse'></i></div></a></td>";
								// }

								if (opt.requirementSubmissionStatus != null
										&& opt.requirementSubmissionStatus != ''
										&& opt.requirementSubmissionStatus != 'undefined') {
									result = result
											+ "<td><div class=''><span title='"
											+ opt.requirementSubmissionStatus
											+ "'>"
											+ opt.requirementSubmissionStatus
											+ "</span></a></div></td>";
								} else {
									result = result + "<td><div>---</div></td>";
								}

								if (checkSize >= 10 && i == 9) {
									/*
									 * $("#viewmoresDiv").css("display",
									 * "block");
									 */
									var text = "<div class='pull-right' id='viewmoresDiv'>";
									text += "<a href='/app/jobs' class='btn btn-sm btn-primary pull-right' title='View more'> View More</a>";
									text += "</div>";
									$("#requirementListActions").append(text);
								}
								result = result + "</tr>";
								result = result + "</tr>";
							}
							result = result + " </tbody>";
							result = result + "</table>";

							if (isWalkIn) {
								$("#requirementListActions")
										.append(
												getDynamicDayActionsForWalkInJobs(
														parseInt(data.responseMessage
																.split(":")[1]) - 1,
														data.responseMessage
																.split(":")[2],
														parseInt(data.responseMessage
																.split(":")[1]) + 1));
							}
							$("#requirementList").append(result);
							$("#requirementLoading").hide();
							/* $("#requirementLoading").hide(); */
							/*
							 * $("#assignedRequirementsDiv").css("display",
							 * "block");
							 */

							$(".proj_close, .side_mask").click(
									function() {
										$(".clients_side_wrap").removeClass(
												"clients_side_wrap_active");
										$(".side_mask").fadeOut(200);
										$("body").css('overflow-y', 'auto');
									});
							pagination();
							getDashboardJobCounts();
						} else {
							$("#requirementList").empty();
							$("#requirementList")
									.append(
											"<table id='noResultsTable'><tr class='odd'><td colspan='7' class='dataTables_empty' valign='top'></td></tr></table>");
							if (isWalkIn) {
								$("#requirementListActions")
										.append(
												getDynamicDayActionsForWalkInJobs(
														parseInt(data.responseMessage
																.split(":")[1]) - 1,
														data.responseMessage
																.split(":")[2],
														parseInt(data.responseMessage
																.split(":")[1]) + 1));
							}
						}
						$("#requirementLoading").hide();
						$("#requirementList").removeAttr("style");
						if (isWalkIn) {
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
</script>

<jsp:include page="masterFooter.jsp"></jsp:include> 