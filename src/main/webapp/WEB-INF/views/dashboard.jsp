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
	
	requirementSubmissionsCount("today");
	expensesCount("myExpenses");
	requirementList(false, -1);
	onboardingCandidatesList();
	getProjectResourceReqestsListCount();
	resourceRequestList("Waiting For Approval");
	requirementApproverList('SUBMITTED');
	ApplicantAuthorization('requestSent');
    getInterviewsandTasks();
	getInterviewsDates(); 
	getJobFairDates();
	getJobFairs();
	contractList(); 
});
</script> 
 
<body>
<div class="wraper">
  <header class="main-header">
    <div class="menuIcon"></div>
    <span class="logo"> <a href="dashboard/971" title="Source Lead"> <i>
    <div class="text-logo"> <span class="tbldata-dotdot" data-id="orgLogoImage" title="INFO SOLUTIONS">INFO SOLUTIONS</span> </div>
    </i> </a> </span>
    <div class="search-area searchArea_mrgn newHeadSea">
      <form id="search_form" name="search_form" action="/app/dashboard" method="post">
        <div class="uasrch-selectpicker">
            <select id="searchType" name="searchType" class="selectpicker uasrch pull-left show-menu-arrow" tabindex="-98">
              <option value="All">All</option>
              
              <!--   commented by laxman because client based project querys are not implemented -->
              
              <option value="Users">Associates</option>
              
              <!--   commented by laxman because client based project querys are not implemented -->
              
              <option value="Client">Clients</option>
              
              <!--   commented by laxman because client based project querys are not implemented -->
              
              <option value="Requirement">Jobs</option>
              
              <!--   commented by laxman because client based project querys are not implemented -->
              
              <option value="Vendor">Vendors</option>
              
              <!--   commented by laxman because client based project querys are not implemented -->
              
              <option value="Candidate">Candidates</option>
              
              <!--   commented by laxman because client based project querys are not implemented --> 
              
              <!--   commented by laxman because client based project querys are not implemented --> 
              
              <!--   commented by laxman because client based project querys are not implemented --> 
              
              <!--   commented by laxman because client based project querys are not implemented --> 
              
              <!--   commented by laxman because client based project querys are not implemented --> 
              
              <!--   commented by laxman because client based project querys are not implemented --> 
              
              <!--   commented by laxman because client based project querys are not implemented --> 
              
              <!--   commented by laxman because client based project querys are not implemented --> 
              
              <!--   commented by laxman because client based project querys are not implemented --> 
              
              <!--   commented by laxman because client based project querys are not implemented --> 
              
              <!--   commented by laxman because client based project querys are not implemented --> 
              
              <!--   commented by laxman because client based project querys are not implemented --> 
              
              <!--   commented by laxman because client based project querys are not implemented --> 
              
              <!--   commented by laxman because client based project querys are not implemented --> 
              
              <!--   commented by laxman because client based project querys are not implemented --> 
              
              <!--   commented by laxman because client based project querys are not implemented --> 
              
              <!--   commented by laxman because client based project querys are not implemented --> 
              
              <!--   commented by laxman because client based project querys are not implemented --> 
              
              <!--   commented by laxman because client based project querys are not implemented --> 
              
              <!--   commented by laxman because client based project querys are not implemented -->
              
            </select>
        </div>
        <div class="pull-left universalSrch">
          <input type="text" id="searchKey" name="searchKey" class="form-control input-sm" placeholder="Search" onkeypress="handle(event);" value="">
          <button type="button" class="srcbutton" value="searchValue" id="searchValue" onclick="universalSearch();"></button>
        </div>
        <div> </div>
      </form>
    </div>
    <div class="headProfile">
      <div class="headProfileIcon"><img class="img-circle" data-id="userProfileImage" alt="Admin" title="neha " onerror="predefineProfileImage(this);" src="/slead-static/profile/professional-woman-51527854182232.jpg"> <span class=" hidden pull-left login-user-xs tbldata-dotdot-vsm" title="neha "> neha </span></div>
      <div class="headPop" id="profilePop">
        <div class="headPopInn"> <a href="/app/userProfile" title="Profile">
          <div class="headPopUser clearfix">
            <div class="pull-left"><img class="img-circle" data-id="userProfileImage" title="neha " alt="Admin" onerror="predefineProfileImage(this);" src="/slead-static/profile/professional-woman-51527854182232.jpg"></div>
            <div class="pull-right">
              <p>neha </p>
              <b>Admin</b> </div>
          </div>
          </a>
          <ul>
            <li> <a title="Connections" href="http://localhost:8080/app/loadConnectedList?status=connected" id="connections"><i class="connectionIC"></i>Connections <span class="label label-warning pull-right"></span></a> </li>
            <li><a title="Contacts" href="http://localhost:8080/app/referRequirementList"><i class="contactsIC"></i>Contacts</a></li>
            <li><a title="Invite Company" data-toggle="modal" id="invitePop"><i class="invitecompanyIC"></i>Invite Company</a></li>
            <li><a href="javascript:void(0)" class="icon-addTask" title="Add Task"><i class="addtaskIC"></i>Add Task</a></li>
            <li><a href="/app/sourceleadTicketList" title="Report Issue"><i class="reporttaskIC"></i>Report Issue</a></li>
            <li><a class="logColor" href="javascript:logoutFormSubmit()" title="Log Out"><i class="logOutIC"></i>Log Out</a></li>
          </ul>
        </div>
      </div>
    </div>
    <div class="headNav">
      <ul>
        <li><a id="orgPopId" href="javascript:void(0)" title="Locations"><i class="headNavIcon"></i></a>
          <div class="headPop headPopnoti" id="orgPopView">
            <div class="headPopInn">
              <div class="headPopSear">
                <input type="text" class="" id="orgsSearch" placeholder="Search">
              </div>
              <ul>
                <li> <a class="sopcSwith noPad"> </a><a onclick="checkAuth(971,3979,'N','2342');" class="sopcSwith noPad">
                  <div class="clearfix  orgAct">
                    <div class="orgProfile"><img onerror="predefineOrgImage(this);" data-id="orgLogoImage" src="/resources/images/office-image.jpg"></div>
                    <div class="orgDetails">
                      <h3>INFO SOLUTIONS</h3>
                      <h4><i class="fa fa fa-map-marker"></i>Hyderabad India</h4>
                      <h4><i class="fa fa-user"></i>Admin</h4>
                    </div>
                  </div>
                  </a> </li>
                <li> <a onclick="checkAuth(633,4415,'N','2342');" class="sopcSwith noPad">
                  <div class="clearfix  ">
                    <div class="orgProfile"><img onerror="predefineOrgImage(this);" data-id="orgLogoImage" src="/resources/images/office-image.jpg"></div>
                    <div class="orgDetails">
                      <h3>Airlines client</h3>
                      <h4><i class="fa fa fa-map-marker"></i>Shimla India</h4>
                      <h4><i class="fa fa-user"></i>Manager</h4>
                    </div>
                  </div>
                  </a> </li>
                <li> <a onclick="checkAuth(1259,5006,'N','2342');" class="sopcSwith noPad">
                  <div class="clearfix  ">
                    <div class="orgProfile"><img onerror="predefineOrgImage(this);" data-id="orgLogoImage" src="/resources/images/office-image.jpg"></div>
                    <div class="orgDetails">
                      <h3>pulla reddy</h3>
                      <h4><i class="fa fa fa-map-marker"></i>New York United States</h4>
                      <h4><i class="fa fa-user"></i>Admin</h4>
                    </div>
                  </div>
                  </a> </li>
                <li> <a onclick="checkAuth(1873,7837,'N','2342');" class="sopcSwith noPad">
                  <div class="clearfix  ">
                    <div class="orgProfile"><img onerror="predefineOrgImage(this);" data-id="orgLogoImage" src="/resources/images/office-image.jpg"></div>
                    <div class="orgDetails">
                      <h3>Info</h3>
                      <h4><i class="fa fa fa-map-marker"></i>Hyderabad India</h4>
                      <h4><i class="fa fa-user"></i>Manager</h4>
                    </div>
                  </div>
                  </a> </li>
              </ul>
              <div class="noDate noDateSm" style="display: none;"></div>
            </div>
          </div>
        </li>
        <li><a title="Reports" href="/app/reportsDashboard"><i class="headNavIcon reporIcon"></i></a></li>
        <li><a href="http://localhost:8080/app/mailBox/mails" title="Mailbox" id="mailbox"><i class="headNavIcon maiIcon"></i> </a></li>
        <li> <a id="notiPopId" href="#" title="Alerts" onclick="getNotificationsByuserId()"><i class="headNavIcon notiIcon"></i> <span class="label-warning">188</span></a>
          <div class="headPop headPopnoti" id="notificationPopup"></div>
        </li>
        <li><a href="javascript:void(0)" title="Settings" id="settingPopId"><i class="headNavIcon setiIcon"></i></a>
          <div class="headPop" id="settingPopup">
            <div class="headPopInn">
              <div class="headPopSear">
                <input type="text" class="" id="settingSearch" placeholder="Search">
              </div>
              <ul>
                <li title="License Upgrade"><a href="/app/orgLicenseSubscriptionList">License Upgrade</a></li>
                <li title="Bulk Import"><a id="subMenu" href="javascript:void(0)">Bulk Import<i class="fa fa-caret-down arrRight"></i></a>
                  <div class="subMenuView"> <a href="/app/bulk-resume-import.html" target="_blank">Bulk Import</a> <a data-toggle="modal" href="javascript:void(0)" id="bulkImport">Process File(s)</a> </div>
                </li>
                <li title="Org Chart"><a href="/app/orgChart">Org Chart</a></li>
                <li title="Role Configuration"><a href="/app/orgRolesList">Role Configuration</a></li>
                <li title="Org Settings"><a href="/app/orgSettings">Org Settings</a></li>
                <li title="Templates"><a href="/app/orgTemplates">Templates</a></li>
                <li title="Expense Category"><a href="/app/expenseCategory">Expense Category</a></li>
                <li title="Bank Accounts"><a href="/app/bankAccountDetails">Bank Accounts</a></li>
                <li title="Leave Configuration"><a href="/app/leaveConfiguration">Leave Configuration</a></li>
                <li title="Job Board Configuration"><a href="/app/job-boards">Job Board Configuration</a></li>
                <li title="Custom Fields"><a href="/app/customFields">Custom Fields</a></li>
                <li><a href="/app/projectAttributes">Project Attributes</a></li>
                <li><a id="campSubMenu" href="javascript:void(0)">Campaigns <i class="fa fa-caret-down arrRight"></i></a>
                  <div class="campsubMenuView"> <a title="Senders" href="/app/campaignSendersList">Senders</a> <a title="Recipients" href="/app/recipientsList">Recipients</a> </div>
                </li>
                <li title="Payroll Configuration"><a href="/app/indPayrollConfiguration">Payroll Configuration</a></li>
                <li><a href="http://localhost:8080/app/tagList" title="Tags" data-toggle="modal" id="tagsPop">Tags</a></li>
              </ul>
              <div class="noDate noDateSm" style="display: none;"></div>
            </div>
          </div>
        </li>
      </ul>
    </div>
    <ul class="responIcons clearfix">
      <li><a><img src="/resources/images/India.gif" title="India"></a></li>
      <li class="srch-btn-xs"> <a title="Search"><i class="fa fa-search"></i></a></li>
    </ul>
  </header>
  <aside class="">
    <nav class="newMenu">
      <ul>
        <li class="navActive"><a href="/app/dashboard/971" title="Dashboard"><span class="navIcons"></span><b>Dashboard</b></a></li>
        <li class=""><a href="/app/usersList" title="Associates"><span class="navIcons assIcon"></span><b>Associates</b></a></li>
        <li class=""><a href="/app/clientDashboard" title="Clients"><span class="navIcons cliIcon"></span><b>Clients</b></a></li>
        <li class=""><a href="/app/jobs" title="Jobs"><span class="navIcons jobIcon"></span><b>Jobs</b></a></li>
        <li class=""><a href="/app/vendorsList" title="Vendors"><span class="navIcons venIcon"></span><b>Vendors</b></a></li>
        <li class=""><a href="/app/candidatesList" title="Candidates"><span class="navIcons canIcon"></span><b>Candidates</b></a></li>
        <li class=""><a href="/app/candidateOnBoardingList" title="On / Off Boarding"><span class="navIcons onoffIcon"></span><b>On / Off Board</b></a></li>
        <li class=""><a href="/app/projectDashboard" title="Projects"><span class="navIcons projIcon"></span><b>Projects</b></a></li>
        <li class=""> <a href="#" class="timesheetLi" title="Timesheets"><span class="navIcons timIcon"></span><b>Timesheets</b> <i class="fa fa-fw fa-caret-down"></i> </a>
          <ul class="collapses" style="display: none">
            <li class=""><a href="/app/timesheet" title="My TimeSheets"><i class="fa fa-chevron-right"></i> Self</a></li>
            <li class=""><a href="/app/timesheetApproval" title="TimeSheet Approval"><i class="fa fa-chevron-right"></i> Employees</a></li>
          </ul>
        </li>
        <li class=""> <a href="#" class="expencesLi" title="Expenses"> <span class="navIcons expIcon"></span><b>Expenses</b> <i class="fa fa-fw fa-caret-down"></i> </a>
          <ul class="collapses" style="display: none">
            <li class=""><a href="/app/expenses" title="My Expenses"><i class="fa fa-chevron-right"></i> Self</a></li>
            <li class=""><a href="/app/expensesApprovalList" title="Expenses Approval"><i class="fa fa-chevron-right"></i> Employees</a></li>
          </ul>
        </li>
        <li class=""><a href="/app/assetManagement" title="Asset Management"><span class="navIcons asstIcon"></span><b>Asset Management</b></a></li>
        <li class=""><a href="/app/contractListbyStatus" title="Contracts"><span class="navIcons contIcon"></span><b>Contracts</b></a></li>
        <li class=""> <a href="#" class="billingLi" title="Finance"> <span class="navIcons finIcon"></span><b>Finance</b> <i class="fa fa-fw fa-caret-down"></i> </a>
          <ul class="collapses" style="display: none">
            <li class=""><a href="/app/invoiceList" title="Invoices"><i class="fa fa-chevron-right"></i> Invoices</a></li>
            <li class=""><a href="/app/payablesList" title="Bills"><i class="fa fa-chevron-right"></i> Bills</a></li>
          </ul>
        </li>
        <li class=""><a href="/app/campaigns/hotcampaigns" title="Campaigns"><span class="navIcons campIcon"></span><b>Campaigns</b></a></li>
        <li class=""><a href="/app/leaveManagementReports" title="Leave Management"><span class="navIcons leaveIcon"></span><b>Leave Management</b></a></li>
        <li class=""> <a href="#" class="payrollli" title="Pay Roll"> <span class="navIcons payIcon"></span><b>Pay Roll</b> <i class="fa fa-fw fa-caret-down"></i> </a>
          <ul class="collapses" style="display: none">
            <li class=""><a href="/app/myPayrollInd" title="My Pays"><i class="fa fa-chevron-right"></i>My Payroll</a></li>
            <li class=""><a href="/app/runIndianPayRoll" title="Payroll"><i class="fa fa-chevron-right"></i>Run Payroll</a></li>
          </ul>
        </li>
        <li class=""> <a href="#" class="expencesLi" title="Immigration"> <span class="navIcons immIcon"></span><b>Immigration</b><i class="fa fa-fw fa-caret-down"></i> </a>
          <ul class="collapses" style="display: none">
            <li class=""><a href="/app/immigration/myImmigration" title="My Immigration"><i class="fa fa-chevron-right"></i> Self</a></li>
            <li class=""><a href="/app/immigration/viewImmigrationList" title="Immigrations"><i class="fa fa-chevron-right"></i> Employees</a></li>
          </ul>
        </li>
      </ul>
    </nav>
  </aside>
  <section class="content-wraper">
    <div class="dashboard_loaded">
      <section class="content-header clearfix">
        <div class="pull-left">
          <h2>Dashboard<a href="javascript:void(0)" onclick="$('#configureDashboard').show();$('.advancedsearch-mask').fadeIn();"><i class="fa fa-cog ml"></i></a></h2>
        </div>
      </section>
    </div>
<div class="row">
  <div class="col-md-6 mb10">
    <div class="dashboardWidget-new dashNewWrap mb">
      <div class="dashNewWrapHead clearfix"> <a class="pull-left" id="jobsLink" href="/app/jobs">Jobs </a><a class="moreLink" href="http://localhost:8080/app/requirementApplicantsList?&amp;tab=previous&amp;orgLocationId=971" title="View more" id="viewmoresDiv"> View More..</a>
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
  <div class="col-md-6 mb10">
    <div class="dashboardWidget-new dashNewWrap mb">
      <div class="dashNewWrapHead clearfix"> <a class="pull-left" id="expensesLink" href="/app/expenses">Expenses &nbsp;&nbsp;</a>
        <ul class="nav nav-tabs pull-right">
          <li class="active"><a data-toggle="tab" onclick="expensesCount('myExpenses');">Self</a></li>
          <li><a data-toggle="tab" onclick="expensesCount('employeeExpenses');">Employees</a></li>
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
              <p id="dial2txt"><a onclick="myExpensesListByStage(&quot;submitted&quot;);">Submitted</a></p>
              <p class="NewCount suColor"><span id="expensesSubmittedForApprovalCount"><a onclick="myExpensesListByStage(&quot;submitted&quot;);">1</a></span></p>
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
  <div class="col-md-6 mb10">
    <div class="dashboardWidget-new dashNewWrap mb">
      <div class="dashNewWrapHead clearfix"> <a class="pull-left" href="/app/leaveManagementReports" title="Leave Management">Leaves</a>
        <div class="pull-left balLeaves">Balance Leaves<span class="label label-info" id="selfBalancedCounts" title="Balance Leaves">0</span></div>
        <ul class="nav nav-tabs pull-right">
          <li class="active"><a data-toggle="tab" href="#self">Self</a></li>
          <li><a data-toggle="tab" href="#employee" onclick="getEmployeeTotalCounts();">Employees</a></li>
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
                  <tr role="row">
                    <th class="sorting_asc" tabindex="0" aria-controls="onboardingPagination" rowspan="1" colspan="1" style="width: 146px;" aria-sort="ascending" aria-label="Candidate Name: activate to sort column descending">Candidate Name</th>
                    <th class="sorting" tabindex="0" aria-controls="onboardingPagination" rowspan="1" colspan="1" style="width: 164px;" aria-label="Email: activate to sort column ascending">Email</th>
                    <th class="sorting" tabindex="0" aria-controls="onboardingPagination" rowspan="1" colspan="1" style="width: 146px;" aria-label="Title: activate to sort column ascending">Title</th>
                    <th class="sorting" tabindex="0" aria-controls="onboardingPagination" rowspan="1" colspan="1" style="width: 73px;" aria-label="DOJ(DD-MM-YYYY): activate to sort column ascending">DOJ<br>
                      (<small class="custom-small">DD-MM-YYYY</small>)</th>
                    <th class="sorting" tabindex="0" aria-controls="onboardingPagination" rowspan="1" colspan="1" style="width: 107px;" aria-label="Rate: activate to sort column ascending">Rate</th>
                    <th class="sorting" tabindex="0" aria-controls="onboardingPagination" rowspan="1" colspan="1" style="width: 84px;" aria-label="Position Type: activate to sort column ascending">Position Type</th>
                    <th class="sorting" tabindex="0" aria-controls="onboardingPagination" rowspan="1" colspan="1" style="width: 106px;" aria-label="On-board Status: activate to sort column ascending">On-board Status</th>
                    <th class="sorting" tabindex="0" aria-controls="onboardingPagination" rowspan="1" colspan="1" style="width: 146px;" aria-label="Actions: activate to sort column ascending">Actions</th>
                  </tr>
                </thead>
                <tbody>
                  <tr role="row" class="odd">
                    <td class="sorting_1"><div class="tbldata-dotdot-sm"><span title=" "> </span></div></td>
                    <td><div><span title="bkcs45@gmail.com">bkcs45@gmail.com</span></div></td>
                    <td><div class="tbldata-dotdot-sm"> <a href="http://localhost:8080/app/requirementSummary?requirementUUID=PCofG3iW"><span title="job of the day">job of the day</span></a></div></td>
                    <td><div><span>12-07-2018</span></div></td>
                    <td><div><span title="INR 2000 / Weekly">INR 2000 / Weekly</span></div></td>
                    <td><div><span title="Full Time">Full Time</span></div></td>
                    <td><div><span>Ready to onboard</span></div></td>
                    <td><div class="tbldata-dotdot-sm"><a href="http://localhost:8080/app/candidateOnBoardingDetails?candidateUUId=q7kGHvnM&amp;offerUUID=qFIt8MjV"><span class="text-success">Onboard</span></a></div></td>
                  </tr>
                  <tr role="row" class="even">
                    <td class="sorting_1"><div class="tbldata-dotdot-sm"><span title="Aditi Chande">Aditi Chande</span></div></td>
                    <td><div><span title="aditichande@mail.com">aditichande@mail.com</span></div></td>
                    <td><div class="tbldata-dotdot-sm"> <a href="http://localhost:8080/app/requirementSummary?requirementUUID=qeu9cFit"><span title="job of the day">job of the day</span></a></div></td>
                    <td><div><span>25-06-2018</span></div></td>
                    <td><div><span title="INR 2000 / Hourly">INR 2000 / Hourly</span></div></td>
                    <td><div><span title="Part Time">Part Time</span></div></td>
                    <td><div><span>Ready to onboard</span></div></td>
                    <td><div class="tbldata-dotdot-sm"><a href="http://localhost:8080/app/candidateOnBoardingDetails?candidateUUId=OWStgdCg&amp;offerUUID=fWf8G3CM"><span class="text-success">Onboard</span></a></div></td>
                  </tr>
                  <tr role="row" class="odd">
                    <td class="sorting_1"><div class="tbldata-dotdot-sm"><span title="Aditi Chande">Aditi Chande</span></div></td>
                    <td><div><span title="aditichande@mail.com">aditichande@mail.com</span></div></td>
                    <td><div class="tbldata-dotdot-sm"> <a href="http://localhost:8080/app/requirementSummary?requirementUUID=Dg5wbdOK"><span title="job@345">job@345</span></a></div></td>
                    <td><div><span>04-09-2018</span></div></td>
                    <td><div><span title="INR 2000 / Hourly">INR 2000 / Hourly</span></div></td>
                    <td><div><span title="Full Time">Full Time</span></div></td>
                    <td><div><span>Ready to onboard</span></div></td>
                    <td><div class="tbldata-dotdot-sm"><a href="http://localhost:8080/app/candidateOnBoardingDetails?candidateUUId=OWStgdCg&amp;offerUUID=qyAap7mr"><span class="text-success">Onboard</span></a></div></td>
                  </tr>
                  <tr role="row" class="even">
                    <td class="sorting_1"><div class="tbldata-dotdot-sm"><span title="Archana Samal">Archana Samal</span></div></td>
                    <td><div><span title="archna@yopmail.com">archna@yopmail.com</span></div></td>
                    <td><div class="tbldata-dotdot-sm"> <a href="http://localhost:8080/app/requirementSummary?requirementUUID=yAMxucp8"><span title="Software Developer">Software Developer</span></a></div></td>
                    <td><div><span>25-06-2018</span></div></td>
                    <td><div><span title="INR 2000 / Hourly">INR 2000 / Hourly</span></div></td>
                    <td><div><span title="Part Time">Part Time</span></div></td>
                    <td><div><span>Ready to onboard</span></div></td>
                    <td><div class="tbldata-dotdot-sm"><a href="http://localhost:8080/app/candidateOnBoardingDetails?candidateUUId=wJrMyadE&amp;offerUUID=YXKijPoU"><span class="text-success">Onboard</span></a></div></td>
                  </tr>
                  <tr role="row" class="odd">
                    <td class="sorting_1"><div class="tbldata-dotdot-sm"><span title="Archana Samal">Archana Samal</span></div></td>
                    <td><div><span title="archana1@yopmail.com">archana1@yopmail.com</span></div></td>
                    <td><div class="tbldata-dotdot-sm"> <a href="http://localhost:8080/app/requirementSummary?requirementUUID=keP03gs0"><span title="job of the week">job of the week</span></a></div></td>
                    <td><div><span>01-08-2018</span></div></td>
                    <td><div><span title="INR 2000 / Hourly">INR 2000 / Hourly</span></div></td>
                    <td><div><span title="Full Time">Full Time</span></div></td>
                    <td><div><span>Ready to onboard</span></div></td>
                    <td><div class="tbldata-dotdot-sm"><a href="http://localhost:8080/app/candidateOnBoardingDetails?candidateUUId=yxJzCYgV&amp;offerUUID=IbhGsk66"><span class="text-success">Onboard</span></a></div></td>
                  </tr>
                </tbody>
              </table>
              <div class="dataTables_info" id="onboardingPagination_info" role="status" aria-live="polite">Showing 1 to 5 of 9 entries</div>
              <div class="dataTables_paginate paging_simple_numbers" id="onboardingPagination_paginate"><a class="paginate_button previous disabled" aria-controls="onboardingPagination" data-dt-idx="0" tabindex="0" id="onboardingPagination_previous">Previous</a><span><a class="paginate_button current" aria-controls="onboardingPagination" data-dt-idx="1" tabindex="0">1</a><a class="paginate_button " aria-controls="onboardingPagination" data-dt-idx="2" tabindex="0">2</a></span><a class="paginate_button next" aria-controls="onboardingPagination" data-dt-idx="3" tabindex="0" id="onboardingPagination_next">Next</a></div>
            </div>
          </div>
          <div id="rejectedReasonDiv"></div>
        </div>
      </div>
    </div>
  </div>
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
                    <option value="0">Jan</option>
                    <option value="1">Feb</option>
                    <option value="2">Mar</option>
                    <option value="3">Apr</option>
                    <option value="4">May</option>
                    <option value="5">Jun</option>
                    <option value="6">Jul</option>
                    <option value="7">Aug</option>
                    <option value="8">Sep</option>
                    <option value="9">Oct</option>
                    <option value="10" selected="selected">Nov</option>
                    <option value="11">Dec</option>
                  </select>
                  <select class="ui-datepicker-year" data-handler="selectYear" data-event="change">
                    <option value="2008">2008</option>
                    <option value="2009">2009</option>
                    <option value="2010">2010</option>
                    <option value="2011">2011</option>
                    <option value="2012">2012</option>
                    <option value="2013">2013</option>
                    <option value="2014">2014</option>
                    <option value="2015">2015</option>
                    <option value="2016">2016</option>
                    <option value="2017">2017</option>
                    <option value="2018" selected="selected">2018</option>
                    <option value="2019">2019</option>
                    <option value="2020">2020</option>
                    <option value="2021">2021</option>
                    <option value="2022">2022</option>
                    <option value="2023">2023</option>
                    <option value="2024">2024</option>
                    <option value="2025">2025</option>
                    <option value="2026">2026</option>
                    <option value="2027">2027</option>
                    <option value="2028">2028</option>
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
                <tbody>
                  <tr>
                    <td class=" ui-datepicker-week-end ui-datepicker-other-month ui-datepicker-unselectable ui-state-disabled">&nbsp;</td>
                    <td class=" ui-datepicker-other-month ui-datepicker-unselectable ui-state-disabled">&nbsp;</td>
                    <td class=" ui-datepicker-other-month ui-datepicker-unselectable ui-state-disabled">&nbsp;</td>
                    <td class=" ui-datepicker-other-month ui-datepicker-unselectable ui-state-disabled">&nbsp;</td>
                    <td class=" " data-handler="selectDay" data-event="click" data-month="10" data-year="2018"><a class="ui-state-default" href="#">1</a></td>
                    <td class=" " data-handler="selectDay" data-event="click" data-month="10" data-year="2018"><a class="ui-state-default" href="#">2</a></td>
                    <td class=" ui-datepicker-week-end " data-handler="selectDay" data-event="click" data-month="10" data-year="2018"><a class="ui-state-default" href="#">3</a></td>
                  </tr>
                  <tr>
                    <td class=" ui-datepicker-week-end " data-handler="selectDay" data-event="click" data-month="10" data-year="2018"><a class="ui-state-default" href="#">4</a></td>
                    <td class=" " data-handler="selectDay" data-event="click" data-month="10" data-year="2018"><a class="ui-state-default" href="#">5</a></td>
                    <td class=" " data-handler="selectDay" data-event="click" data-month="10" data-year="2018"><a class="ui-state-default" href="#">6</a></td>
                    <td class=" " data-handler="selectDay" data-event="click" data-month="10" data-year="2018"><a class="ui-state-default" href="#">7</a></td>
                    <td class=" " data-handler="selectDay" data-event="click" data-month="10" data-year="2018"><a class="ui-state-default" href="#">8</a></td>
                    <td class=" " data-handler="selectDay" data-event="click" data-month="10" data-year="2018"><a class="ui-state-default" href="#">9</a></td>
                    <td class=" ui-datepicker-week-end " data-handler="selectDay" data-event="click" data-month="10" data-year="2018"><a class="ui-state-default" href="#">10</a></td>
                  </tr>
                  <tr>
                    <td class=" ui-datepicker-week-end " data-handler="selectDay" data-event="click" data-month="10" data-year="2018"><a class="ui-state-default" href="#">11</a></td>
                    <td class=" " data-handler="selectDay" data-event="click" data-month="10" data-year="2018"><a class="ui-state-default" href="#">12</a></td>
                    <td class=" " data-handler="selectDay" data-event="click" data-month="10" data-year="2018"><a class="ui-state-default" href="#">13</a></td>
                    <td class=" " data-handler="selectDay" data-event="click" data-month="10" data-year="2018"><a class="ui-state-default" href="#">14</a></td>
                    <td class=" " data-handler="selectDay" data-event="click" data-month="10" data-year="2018"><a class="ui-state-default" href="#">15</a></td>
                    <td class=" " data-handler="selectDay" data-event="click" data-month="10" data-year="2018"><a class="ui-state-default" href="#">16</a></td>
                    <td class=" ui-datepicker-week-end " data-handler="selectDay" data-event="click" data-month="10" data-year="2018"><a class="ui-state-default" href="#">17</a></td>
                  </tr>
                  <tr>
                    <td class=" ui-datepicker-week-end " data-handler="selectDay" data-event="click" data-month="10" data-year="2018"><a class="ui-state-default" href="#">18</a></td>
                    <td class=" " data-handler="selectDay" data-event="click" data-month="10" data-year="2018"><a class="ui-state-default" href="#">19</a></td>
                    <td class=" ui-datepicker-days-cell-over  ui-datepicker-current-day ui-datepicker-today" data-handler="selectDay" data-event="click" data-month="10" data-year="2018"><a class="ui-state-default ui-state-highlight ui-state-active ui-state-hover" href="#">20</a></td>
                    <td class=" " data-handler="selectDay" data-event="click" data-month="10" data-year="2018"><a class="ui-state-default" href="#">21</a></td>
                    <td class=" " data-handler="selectDay" data-event="click" data-month="10" data-year="2018"><a class="ui-state-default" href="#">22</a></td>
                    <td class=" " data-handler="selectDay" data-event="click" data-month="10" data-year="2018"><a class="ui-state-default" href="#">23</a></td>
                    <td class=" ui-datepicker-week-end " data-handler="selectDay" data-event="click" data-month="10" data-year="2018"><a class="ui-state-default" href="#">24</a></td>
                  </tr>
                  <tr>
                    <td class=" ui-datepicker-week-end " data-handler="selectDay" data-event="click" data-month="10" data-year="2018"><a class="ui-state-default" href="#">25</a></td>
                    <td class=" " data-handler="selectDay" data-event="click" data-month="10" data-year="2018"><a class="ui-state-default" href="#">26</a></td>
                    <td class=" " data-handler="selectDay" data-event="click" data-month="10" data-year="2018"><a class="ui-state-default" href="#">27</a></td>
                    <td class=" " data-handler="selectDay" data-event="click" data-month="10" data-year="2018"><a class="ui-state-default" href="#">28</a></td>
                    <td class=" " data-handler="selectDay" data-event="click" data-month="10" data-year="2018"><a class="ui-state-default" href="#">29</a></td>
                    <td class=" " data-handler="selectDay" data-event="click" data-month="10" data-year="2018"><a class="ui-state-default" href="#">30</a></td>
                    <td class=" ui-datepicker-week-end ui-datepicker-other-month ui-datepicker-unselectable ui-state-disabled">&nbsp;</td>
                  </tr>
                </tbody>
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
            <div class="dataTables_info" id="requirementApproverPagination_info" role="status" aria-live="polite">Showing 0 to 0 of 0 entries</div>
            <div class="dataTables_paginate paging_simple_numbers" id="requirementApproverPagination_paginate"><a class="paginate_button previous disabled" aria-controls="requirementApproverPagination" data-dt-idx="0" tabindex="0" id="requirementApproverPagination_previous">Previous</a><span></span><a class="paginate_button next disabled" aria-controls="requirementApproverPagination" data-dt-idx="1" tabindex="0" id="requirementApproverPagination_next">Next</a></div>
          </div>
        </div>
      </div>
      <!-- Pagenation Ends --> 
    </div>
  </div>
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
              <table id="applicantsPagination" class="display dataTable no-footer" role="grid" aria-describedby="applicantsPagination_info" style="width: 99.5%;" width="99.5%" cellspacing="0">
                <thead>
                  <tr role="row">
                    <th class="sorting_asc" tabindex="0" aria-controls="applicantsPagination" rowspan="1" colspan="1" style="width: 97px;" aria-sort="ascending" aria-label="Applicant: activate to sort column descending">Applicant</th>
                    <th class="sorting" tabindex="0" aria-controls="applicantsPagination" rowspan="1" colspan="1" style="width: 47px;" aria-label="Job: activate to sort column ascending">Job</th>
                    <th class="sorting" tabindex="0" aria-controls="applicantsPagination" rowspan="1" colspan="1" style="width: 138px;" aria-label="Team Member: activate to sort column ascending">Team Member</th>
                    <th class="sorting" tabindex="0" aria-controls="applicantsPagination" rowspan="1" colspan="1" style="width: 71px;" aria-label="Status: activate to sort column ascending">Status</th>
                    <th class="sorting" tabindex="0" aria-controls="applicantsPagination" rowspan="1" colspan="1" style="width: 71px;" aria-label="Action: activate to sort column ascending">Action</th>
                  </tr>
                </thead>
                <tbody>
                  <tr class="odd">
                    <td colspan="5" class="dataTables_empty" valign="top"></td>
                  </tr>
                </tbody>
              </table>
              <div class="dataTables_info" id="applicantsPagination_info" role="status" aria-live="polite">Showing 0 to 0 of 0 entries</div>
              <div class="dataTables_paginate paging_simple_numbers" id="applicantsPagination_paginate"><a class="paginate_button previous disabled" aria-controls="applicantsPagination" data-dt-idx="0" tabindex="0" id="applicantsPagination_previous">Previous</a><span></span><a class="paginate_button next disabled" aria-controls="applicantsPagination" data-dt-idx="1" tabindex="0" id="applicantsPagination_next">Next</a></div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
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
    <div class="mb clearfix col-md-12 noPad"> 
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
                        <th class="sorting_asc" tabindex="0" aria-controls="requirementPagination" rowspan="1" colspan="1" style="width: 65px;" aria-sort="ascending" aria-label="Actions: activate to sort column descending">Actions</th>
                        <th class="sorting" tabindex="0" aria-controls="requirementPagination" rowspan="1" colspan="1" style="width: 61px;" aria-label="Generated ID: activate to sort column ascending">Generated ID</th>
                        <th class="sorting" tabindex="0" aria-controls="requirementPagination" rowspan="1" colspan="1" style="width: 140px;" aria-label="Title: activate to sort column ascending">Title</th>
                        <th class="sorting" tabindex="0" aria-controls="requirementPagination" rowspan="1" colspan="1" style="width: 140px;" aria-label="Client: activate to sort column ascending">Client</th>
                        <th class="sorting" tabindex="0" aria-controls="requirementPagination" rowspan="1" colspan="1" style="width: 115px;" aria-label="Rate: activate to sort column ascending">Rate</th>
                        <th class="sorting" tabindex="0" aria-controls="requirementPagination" rowspan="1" colspan="1" style="width: 43px;" aria-label="Priority: activate to sort column ascending">Priority</th>
                        <th class="sorting" tabindex="0" aria-controls="requirementPagination" rowspan="1" colspan="1" style="width: 63px;" aria-label="Applicants: activate to sort column ascending">Applicants</th>
                        <th class="sorting" tabindex="0" aria-controls="requirementPagination" rowspan="1" colspan="1" style="width: 65px;" aria-label="Shortlisted: activate to sort column ascending">Shortlisted</th>
                        <th class="sorting" tabindex="0" aria-controls="requirementPagination" rowspan="1" colspan="1" style="width: 61px;" aria-label="Submitted: activate to sort column ascending">Submitted</th>
                        <th class="sorting" tabindex="0" aria-controls="requirementPagination" rowspan="1" colspan="1" style="width: 54px;" aria-label="Interview: activate to sort column ascending">Interview</th>
                        <th class="sorting" tabindex="0" aria-controls="requirementPagination" rowspan="1" colspan="1" style="width: 44px;" aria-label="Offered: activate to sort column ascending">Offered</th>
                        <th class="sorting" tabindex="0" aria-controls="requirementPagination" rowspan="1" colspan="1" style="width: 40px;" aria-label="Joined: activate to sort column ascending">Joined</th>
                        <th class="sorting" tabindex="0" aria-controls="requirementPagination" rowspan="1" colspan="1" style="width: 39px;" aria-label="Status: activate to sort column ascending">Status</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr role="row" class="odd">
                        <td class="sorting_1"><div class="tblminwidth-75">
                            <div class="text-center pull-left"><a href="javascript:void(0)" title="Add Applicant" onclick="getAddApplicant(10389,'3 Months');" class="add-applicant"><i class="fa fa-user-plus"></i></a></div>
                            <div class="text-center pull-left ml10 mr10"><a href="javascript:void(0)" title="Assign To" data-toggle="modal" data-target="#myModal" onclick="getAssignedMembersDiv(10389);"><i class="fa fa-address-book anchorBlack"></i></a></div>
                            <div class="pull-left"><a href="/app/releaseRequirementToClientOrVendors?requirementUUID=Ve7pU1Kk"><i class="fa fa-bullhorn" title="Release Job"></i></a></div>
                          </div></td>
                        <td><div class=""><span title="GDFHJB45">GDFHJB45</span></div></td>
                        <td><div class="tbldata-dotdot-sm"> <a href="http://localhost:8080/app/requirementSummary?requirementUUID=Ve7pU1Kk"><span title="Sample">Sample</span></a></div></td>
                        <td><div class="tbldata-dotdot-sm"><a href="http://localhost:8080/app/clientDashboard?orgRelationUUID=4Y6vQc0o" title="Dell">Dell</a></div></td>
                        <td><div><span><span>---</span></span></div></td>
                        <td><div>Medium</div></td>
                        <td><input type="hidden" class="requirementDashboardCount" id="10389">
                          <a href="/app/requirementSummary?type=applicants&amp;requirementUUID=Ve7pU1Kk">
                          <div id="10389applicantCount">2</div>
                          </a></td>
                        <td><a href="/app/requirementSummary?type=SHORTLIST&amp;requirementUUID=Ve7pU1Kk">
                          <div id="10389shortlistedCount">1</div>
                          </a></td>
                        <td><a href="/app/requirementSummary?type=SUBMIT_TO_CLIENT&amp;requirementUUID=Ve7pU1Kk">
                          <div id="10389submittedCount">0</div>
                          </a></td>
                        <td><a href="/app/requirementSummary?type=INTERVIEW&amp;requirementUUID=Ve7pU1Kk">
                          <div id="10389interviewedCount">0</div>
                          </a></td>
                        <td><a href="/app/requirementSummary?type=OFFERMADE&amp;requirementUUID=Ve7pU1Kk">
                          <div id="10389offeredCount">0</div>
                          </a></td>
                        <td><a href="/app/requirementSummary?type=JOINED&amp;requirementUUID=Ve7pU1Kk">
                          <div id="10389joinedCount">0</div>
                          </a></td>
                        <td><div class=""><span title="OPEN">OPEN</span></div></td>
                      </tr>
                      <tr role="row" class="even">
                        <td class="sorting_1"><div class="tblminwidth-75">
                            <div class="text-center pull-left"><a href="javascript:void(0)" title="Add Applicant" onclick="getAddApplicant(10233,'6 Months');" class="add-applicant"><i class="fa fa-user-plus"></i></a></div>
                            <div class="text-center pull-left ml10 mr10"><a href="javascript:void(0)" title="Assign To" data-toggle="modal" data-target="#myModal" onclick="getAssignedMembersDiv(10233);"><i class="fa fa-address-book anchorBlack"></i></a></div>
                            <div class="pull-left"><a href="/app/releaseRequirementToClientOrVendors?requirementUUID=B5uoA1ZT"><i class="fa fa-bullhorn success" title="Release Job"></i></a></div>
                          </div></td>
                        <td><div class=""><span title="GDFHJB44">GDFHJB44</span></div></td>
                        <td><div class="tbldata-dotdot-sm"> <a href="http://localhost:8080/app/requirementSummary?requirementUUID=B5uoA1ZT"><span title="job of the day">job of the day</span></a></div></td>
                        <td><div>---</div></td>
                        <td><div><span><span>---</span></span></div></td>
                        <td><div>Medium</div></td>
                        <td><input type="hidden" class="requirementDashboardCount" id="10233">
                          <a href="/app/requirementSummary?type=applicants&amp;requirementUUID=B5uoA1ZT">
                          <div id="10233applicantCount">1</div>
                          </a></td>
                        <td><a href="/app/requirementSummary?type=SHORTLIST&amp;requirementUUID=B5uoA1ZT">
                          <div id="10233shortlistedCount">0</div>
                          </a></td>
                        <td><a href="/app/requirementSummary?type=SUBMIT_TO_CLIENT&amp;requirementUUID=B5uoA1ZT">
                          <div id="10233submittedCount">0</div>
                          </a></td>
                        <td><a href="/app/requirementSummary?type=INTERVIEW&amp;requirementUUID=B5uoA1ZT">
                          <div id="10233interviewedCount">0</div>
                          </a></td>
                        <td><a href="/app/requirementSummary?type=OFFERMADE&amp;requirementUUID=B5uoA1ZT">
                          <div id="10233offeredCount">1</div>
                          </a></td>
                        <td><a href="/app/requirementSummary?type=JOINED&amp;requirementUUID=B5uoA1ZT">
                          <div id="10233joinedCount">0</div>
                          </a></td>
                        <td><div class=""><span title="OPEN">OPEN</span></div></td>
                      </tr>
                      <tr role="row" class="odd">
                        <td class="sorting_1"><div class="tblminwidth-75">
                            <div class="text-center pull-left"><a href="javascript:void(0)" title="Add Applicant" onclick="getAddApplicant(10178,'6 Months');" class="add-applicant"><i class="fa fa-user-plus"></i></a></div>
                            <div class="text-center pull-left ml10 mr10"><a href="javascript:void(0)" title="Assign To" data-toggle="modal" data-target="#myModal" onclick="getAssignedMembersDiv(10178);"><i class="fa fa-address-book anchorBlack"></i></a></div>
                            <div class="pull-left"><a href="/app/releaseRequirementToClientOrVendors?requirementUUID=9IureNjR"><i class="fa fa-bullhorn success" title="Release Job"></i></a></div>
                          </div></td>
                        <td><div class=""><span title="GDFHJB43">GDFHJB43</span></div></td>
                        <td><div class="tbldata-dotdot-sm"> <a href="http://localhost:8080/app/requirementSummary?requirementUUID=9IureNjR"><span title="production job">production job</span></a></div></td>
                        <td><div>---</div></td>
                        <td><div><span><span>---</span></span></div></td>
                        <td><div>Medium</div></td>
                        <td><input type="hidden" class="requirementDashboardCount" id="10178">
                          <a href="/app/requirementSummary?type=applicants&amp;requirementUUID=9IureNjR">
                          <div id="10178applicantCount">1</div>
                          </a></td>
                        <td><a href="/app/requirementSummary?type=SHORTLIST&amp;requirementUUID=9IureNjR">
                          <div id="10178shortlistedCount">0</div>
                          </a></td>
                        <td><a href="/app/requirementSummary?type=SUBMIT_TO_CLIENT&amp;requirementUUID=9IureNjR">
                          <div id="10178submittedCount">0</div>
                          </a></td>
                        <td><a href="/app/requirementSummary?type=INTERVIEW&amp;requirementUUID=9IureNjR">
                          <div id="10178interviewedCount">0</div>
                          </a></td>
                        <td><a href="/app/requirementSummary?type=OFFERMADE&amp;requirementUUID=9IureNjR">
                          <div id="10178offeredCount">1</div>
                          </a></td>
                        <td><a href="/app/requirementSummary?type=JOINED&amp;requirementUUID=9IureNjR">
                          <div id="10178joinedCount">0</div>
                          </a></td>
                        <td><div class=""><span title="OPEN">OPEN</span></div></td>
                      </tr>
                      <tr role="row" class="even">
                        <td class="sorting_1"><div class="tblminwidth-75">
                            <div class="text-center pull-left"><a href="javascript:void(0)" title="Add Applicant" onclick="getAddApplicant(10172,'3 Months');" class="add-applicant"><i class="fa fa-user-plus"></i></a></div>
                            <div class="text-center pull-left ml10 mr10"><a href="javascript:void(0)" title="Assign To" data-toggle="modal" data-target="#myModal" onclick="getAssignedMembersDiv(10172);"><i class="fa fa-address-book anchorBlack"></i></a></div>
                            <div class="pull-left"><a href="/app/releaseRequirementToClientOrVendors?requirementUUID=mgK1siAZ"><i class="fa fa-bullhorn success" title="Release Job"></i></a></div>
                          </div></td>
                        <td><div class=""><span title="GDFHJB42">GDFHJB42</span></div></td>
                        <td><div class="tbldata-dotdot-sm"> <a href="http://localhost:8080/app/requirementSummary?requirementUUID=mgK1siAZ"><span title="oct2nd job">oct2nd job</span></a></div></td>
                        <td><div>---</div></td>
                        <td><div><span><span>---</span></span></div></td>
                        <td><div>Medium</div></td>
                        <td><input type="hidden" class="requirementDashboardCount" id="10172">
                          <a href="/app/requirementSummary?type=applicants&amp;requirementUUID=mgK1siAZ">
                          <div id="10172applicantCount">1</div>
                          </a></td>
                        <td><a href="/app/requirementSummary?type=SHORTLIST&amp;requirementUUID=mgK1siAZ">
                          <div id="10172shortlistedCount">0</div>
                          </a></td>
                        <td><a href="/app/requirementSummary?type=SUBMIT_TO_CLIENT&amp;requirementUUID=mgK1siAZ">
                          <div id="10172submittedCount">0</div>
                          </a></td>
                        <td><a href="/app/requirementSummary?type=INTERVIEW&amp;requirementUUID=mgK1siAZ">
                          <div id="10172interviewedCount">0</div>
                          </a></td>
                        <td><a href="/app/requirementSummary?type=OFFERMADE&amp;requirementUUID=mgK1siAZ">
                          <div id="10172offeredCount">1</div>
                          </a></td>
                        <td><a href="/app/requirementSummary?type=JOINED&amp;requirementUUID=mgK1siAZ">
                          <div id="10172joinedCount">0</div>
                          </a></td>
                        <td><div class=""><span title="OPEN">OPEN</span></div></td>
                      </tr>
                      <tr role="row" class="odd">
                        <td class="sorting_1"><div class="tblminwidth-75">
                            <div class="text-center pull-left"><a href="javascript:void(0)" title="Add Applicant" onclick="getAddApplicant(9953,'3 Months');" class="add-applicant"><i class="fa fa-user-plus"></i></a></div>
                            <div class="text-center pull-left ml10 mr10"><a href="javascript:void(0)" title="Assign To" data-toggle="modal" data-target="#myModal" onclick="getAssignedMembersDiv(9953);"><i class="fa fa-address-book anchorBlack"></i></a></div>
                            <div class="pull-left"><a href="/app/releaseRequirementToClientOrVendors?requirementUUID=LtEUgkp5"><i class="fa fa-bullhorn" title="Release Job"></i></a></div>
                          </div></td>
                        <td><div class=""><span title="GDFHJB41">GDFHJB41</span></div></td>
                        <td><div class="tbldata-dotdot-sm"> <a href="http://localhost:8080/app/requirementSummary?requirementUUID=LtEUgkp5"><span title="new job list">new job list</span></a></div></td>
                        <td><div>---</div></td>
                        <td><div><span><span>---</span></span></div></td>
                        <td><div>Medium</div></td>
                        <td><input type="hidden" class="requirementDashboardCount" id="9953">
                          <a href="/app/requirementSummary?type=applicants&amp;requirementUUID=LtEUgkp5">
                          <div id="9953applicantCount">0</div>
                          </a></td>
                        <td><a href="/app/requirementSummary?type=SHORTLIST&amp;requirementUUID=LtEUgkp5">
                          <div id="9953shortlistedCount">0</div>
                          </a></td>
                        <td><a href="/app/requirementSummary?type=SUBMIT_TO_CLIENT&amp;requirementUUID=LtEUgkp5">
                          <div id="9953submittedCount">0</div>
                          </a></td>
                        <td><a href="/app/requirementSummary?type=INTERVIEW&amp;requirementUUID=LtEUgkp5">
                          <div id="9953interviewedCount">0</div>
                          </a></td>
                        <td><a href="/app/requirementSummary?type=OFFERMADE&amp;requirementUUID=LtEUgkp5">
                          <div id="9953offeredCount">0</div>
                          </a></td>
                        <td><a href="/app/requirementSummary?type=JOINED&amp;requirementUUID=LtEUgkp5">
                          <div id="9953joinedCount">0</div>
                          </a></td>
                        <td><div class=""><span title="OPEN">OPEN</span></div></td>
                      </tr>
                      <tr role="row" class="even">
                        <td class="sorting_1"><div class="tblminwidth-75">
                            <div class="text-center pull-left"><a href="javascript:void(0)" title="Add Applicant" onclick="getAddApplicant(9948,'6 Months');" class="add-applicant"><i class="fa fa-user-plus"></i></a></div>
                            <div class="text-center pull-left ml10 mr10"><a href="javascript:void(0)" title="Assign To" data-toggle="modal" data-target="#myModal" onclick="getAssignedMembersDiv(9948);"><i class="fa fa-address-book anchorBlack"></i></a></div>
                            <div class="pull-left"><a href="/app/releaseRequirementToClientOrVendors?requirementUUID=6zY9W5I2"><i class="fa fa-bullhorn" title="Release Job"></i></a></div>
                          </div></td>
                        <td><div class=""><span title="GDFHJB40">GDFHJB40</span></div></td>
                        <td><div class="tbldata-dotdot-sm"> <a href="http://localhost:8080/app/requirementSummary?requirementUUID=6zY9W5I2"><span title="selenium job">selenium job</span></a></div></td>
                        <td><div>---</div></td>
                        <td><div><span title="INR&nbsp;2000-3000&nbsp;Monthly">INR&nbsp;2000-3000&nbsp;Monthly</span></div></td>
                        <td><div>Medium</div></td>
                        <td><input type="hidden" class="requirementDashboardCount" id="9948">
                          <a href="/app/requirementSummary?type=applicants&amp;requirementUUID=6zY9W5I2">
                          <div id="9948applicantCount">2</div>
                          </a></td>
                        <td><a href="/app/requirementSummary?type=SHORTLIST&amp;requirementUUID=6zY9W5I2">
                          <div id="9948shortlistedCount">0</div>
                          </a></td>
                        <td><a href="/app/requirementSummary?type=SUBMIT_TO_CLIENT&amp;requirementUUID=6zY9W5I2">
                          <div id="9948submittedCount">0</div>
                          </a></td>
                        <td><a href="/app/requirementSummary?type=INTERVIEW&amp;requirementUUID=6zY9W5I2">
                          <div id="9948interviewedCount">0</div>
                          </a></td>
                        <td><a href="/app/requirementSummary?type=OFFERMADE&amp;requirementUUID=6zY9W5I2">
                          <div id="9948offeredCount">0</div>
                          </a></td>
                        <td><a href="/app/requirementSummary?type=JOINED&amp;requirementUUID=6zY9W5I2">
                          <div id="9948joinedCount">1</div>
                          </a></td>
                        <td><div class=""><span title="OPEN">OPEN</span></div></td>
                      </tr>
                      <tr role="row" class="odd">
                        <td class="sorting_1"><div class="tblminwidth-75">
                            <div class="text-center pull-left"><a href="javascript:void(0)" title="Add Applicant" onclick="getAddApplicant(9947,'6 Months');" class="add-applicant"><i class="fa fa-user-plus"></i></a></div>
                            <div class="text-center pull-left ml10 mr10"><a href="javascript:void(0)" title="Assign To" data-toggle="modal" data-target="#myModal" onclick="getAssignedMembersDiv(9947);"><i class="fa fa-address-book anchorBlack"></i></a></div>
                            <div class="pull-left"><a href="/app/releaseRequirementToClientOrVendors?requirementUUID=Dg5wbdOK"><i class="fa fa-bullhorn" title="Release Job"></i></a></div>
                          </div></td>
                        <td><div class=""><span title="GDFHJB39">GDFHJB39</span></div></td>
                        <td><div class="tbldata-dotdot-sm"> <a href="http://localhost:8080/app/requirementSummary?requirementUUID=Dg5wbdOK"><span title="job@345">job@345</span></a></div></td>
                        <td><div>---</div></td>
                        <td><div><span title="INR&nbsp;2000-4000&nbsp;Monthly">INR&nbsp;2000-4000&nbsp;Monthly</span></div></td>
                        <td><div>Medium</div></td>
                        <td><input type="hidden" class="requirementDashboardCount" id="9947">
                          <a href="/app/requirementSummary?type=applicants&amp;requirementUUID=Dg5wbdOK">
                          <div id="9947applicantCount">1</div>
                          </a></td>
                        <td><a href="/app/requirementSummary?type=SHORTLIST&amp;requirementUUID=Dg5wbdOK">
                          <div id="9947shortlistedCount">0</div>
                          </a></td>
                        <td><a href="/app/requirementSummary?type=SUBMIT_TO_CLIENT&amp;requirementUUID=Dg5wbdOK">
                          <div id="9947submittedCount">0</div>
                          </a></td>
                        <td><a href="/app/requirementSummary?type=INTERVIEW&amp;requirementUUID=Dg5wbdOK">
                          <div id="9947interviewedCount">0</div>
                          </a></td>
                        <td><a href="/app/requirementSummary?type=OFFERMADE&amp;requirementUUID=Dg5wbdOK">
                          <div id="9947offeredCount">1</div>
                          </a></td>
                        <td><a href="/app/requirementSummary?type=JOINED&amp;requirementUUID=Dg5wbdOK">
                          <div id="9947joinedCount">0</div>
                          </a></td>
                        <td><div class=""><span title="OPEN">OPEN</span></div></td>
                      </tr>
                      <tr role="row" class="even">
                        <td class="sorting_1"><div class="tblminwidth-75">
                            <div class="text-center pull-left"><a href="javascript:void(0)" title="Add Applicant" onclick="getAddApplicant(9946,'6 Months');" class="add-applicant"><i class="fa fa-user-plus"></i></a></div>
                            <div class="text-center pull-left ml10 mr10"><a href="javascript:void(0)" title="Assign To" data-toggle="modal" data-target="#myModal" onclick="getAssignedMembersDiv(9946);"><i class="fa fa-address-book anchorBlack"></i></a></div>
                            <div class="pull-left"><a href="/app/releaseRequirementToClientOrVendors?requirementUUID=uUCznJKn"><i class="fa fa-bullhorn" title="Release Job"></i></a></div>
                          </div></td>
                        <td><div class=""><span title="GDFHJB38">GDFHJB38</span></div></td>
                        <td><div class="tbldata-dotdot-sm"> <a href="http://localhost:8080/app/requirementSummary?requirementUUID=uUCznJKn"><span title="job@123">job@123</span></a></div></td>
                        <td><div>---</div></td>
                        <td><div><span title="INR&nbsp;2000-5000&nbsp;Monthly">INR&nbsp;2000-5000&nbsp;Monthly</span></div></td>
                        <td><div>Medium</div></td>
                        <td><input type="hidden" class="requirementDashboardCount" id="9946">
                          <a href="/app/requirementSummary?type=applicants&amp;requirementUUID=uUCznJKn">
                          <div id="9946applicantCount">2</div>
                          </a></td>
                        <td><a href="/app/requirementSummary?type=SHORTLIST&amp;requirementUUID=uUCznJKn">
                          <div id="9946shortlistedCount">0</div>
                          </a></td>
                        <td><a href="/app/requirementSummary?type=SUBMIT_TO_CLIENT&amp;requirementUUID=uUCznJKn">
                          <div id="9946submittedCount">0</div>
                          </a></td>
                        <td><a href="/app/requirementSummary?type=INTERVIEW&amp;requirementUUID=uUCznJKn">
                          <div id="9946interviewedCount">0</div>
                          </a></td>
                        <td><a href="/app/requirementSummary?type=OFFERMADE&amp;requirementUUID=uUCznJKn">
                          <div id="9946offeredCount">0</div>
                          </a></td>
                        <td><a href="/app/requirementSummary?type=JOINED&amp;requirementUUID=uUCznJKn">
                          <div id="9946joinedCount">0</div>
                          </a></td>
                        <td><div class=""><span title="OPEN">OPEN</span></div></td>
                      </tr>
                      <tr role="row" class="odd">
                        <td class="sorting_1"><div class="tblminwidth-75">
                            <div class="text-center pull-left"><a href="javascript:void(0)" title="Add Applicant" onclick="getAddApplicant(9939,'3 Months');" class="add-applicant"><i class="fa fa-user-plus"></i></a></div>
                            <div class="text-center pull-left ml10 mr10"><a href="javascript:void(0)" title="Assign To" data-toggle="modal" data-target="#myModal" onclick="getAssignedMembersDiv(9939);"><i class="fa fa-address-book anchorBlack"></i></a></div>
                            <div class="pull-left"><a href="/app/releaseRequirementToClientOrVendors?requirementUUID=z0wc5W7p"><i class="fa fa-bullhorn" title="Release Job"></i></a></div>
                          </div></td>
                        <td><div class=""><span title="GDFHJB37">GDFHJB37</span></div></td>
                        <td><div class="tbldata-dotdot-sm"> <a href="http://localhost:8080/app/requirementSummary?requirementUUID=z0wc5W7p"><span title="Aob">Aob</span></a></div></td>
                        <td><div>---</div></td>
                        <td><div><span><span>---</span></span></div></td>
                        <td><div>Medium</div></td>
                        <td><input type="hidden" class="requirementDashboardCount" id="9939">
                          <a href="/app/requirementSummary?type=applicants&amp;requirementUUID=z0wc5W7p">
                          <div id="9939applicantCount">0</div>
                          </a></td>
                        <td><a href="/app/requirementSummary?type=SHORTLIST&amp;requirementUUID=z0wc5W7p">
                          <div id="9939shortlistedCount">0</div>
                          </a></td>
                        <td><a href="/app/requirementSummary?type=SUBMIT_TO_CLIENT&amp;requirementUUID=z0wc5W7p">
                          <div id="9939submittedCount">0</div>
                          </a></td>
                        <td><a href="/app/requirementSummary?type=INTERVIEW&amp;requirementUUID=z0wc5W7p">
                          <div id="9939interviewedCount">0</div>
                          </a></td>
                        <td><a href="/app/requirementSummary?type=OFFERMADE&amp;requirementUUID=z0wc5W7p">
                          <div id="9939offeredCount">0</div>
                          </a></td>
                        <td><a href="/app/requirementSummary?type=JOINED&amp;requirementUUID=z0wc5W7p">
                          <div id="9939joinedCount">0</div>
                          </a></td>
                        <td><div class=""><span title="OPEN">OPEN</span></div></td>
                      </tr>
                      <tr role="row" class="even">
                        <td class="sorting_1"><div class="tblminwidth-75">
                            <div class="text-center pull-left"><a href="javascript:void(0)" title="Add Applicant" onclick="getAddApplicant(7982,'6 Months');" class="add-applicant"><i class="fa fa-user-plus"></i></a></div>
                            <div class="text-center pull-left ml10 mr10"><a href="javascript:void(0)" title="Assign To" data-toggle="modal" data-target="#myModal" onclick="getAssignedMembersDiv(7982);"><i class="fa fa-address-book anchorBlack"></i></a></div>
                            <div class="pull-left"><a href="/app/releaseRequirementToClientOrVendors?requirementUUID=DqCBNe52"><i class="fa fa-bullhorn success" title="Release Job"></i></a></div>
                          </div></td>
                        <td><div class=""><span title="GDFHJB36">GDFHJB36</span></div></td>
                        <td><div class="tbldata-dotdot-sm"> <a href="http://localhost:8080/app/requirementSummary?requirementUUID=DqCBNe52"><span title="job123">job123</span></a></div></td>
                        <td><div>---</div></td>
                        <td><div><span title="INR&nbsp;3000-6000&nbsp;Monthly">INR&nbsp;3000-6000&nbsp;Monthly</span></div></td>
                        <td><div>Medium</div></td>
                        <td><input type="hidden" class="requirementDashboardCount" id="7982">
                          <a href="/app/requirementSummary?type=applicants&amp;requirementUUID=DqCBNe52">
                          <div id="7982applicantCount">2</div>
                          </a></td>
                        <td><a href="/app/requirementSummary?type=SHORTLIST&amp;requirementUUID=DqCBNe52">
                          <div id="7982shortlistedCount">0</div>
                          </a></td>
                        <td><a href="/app/requirementSummary?type=SUBMIT_TO_CLIENT&amp;requirementUUID=DqCBNe52">
                          <div id="7982submittedCount">0</div>
                          </a></td>
                        <td><a href="/app/requirementSummary?type=INTERVIEW&amp;requirementUUID=DqCBNe52">
                          <div id="7982interviewedCount">1</div>
                          </a></td>
                        <td><a href="/app/requirementSummary?type=OFFERMADE&amp;requirementUUID=DqCBNe52">
                          <div id="7982offeredCount">1</div>
                          </a></td>
                        <td><a href="/app/requirementSummary?type=JOINED&amp;requirementUUID=DqCBNe52">
                          <div id="7982joinedCount">0</div>
                          </a></td>
                        <td><div class=""><span title="OPEN">OPEN</span></div></td>
                      </tr>
                    </tbody>
                  </table>
                  <div class="dataTables_info" id="requirementPagination_info" role="status" aria-live="polite">Showing 1 to 10 of 10 entries</div>
                </div>
              </div>
            </div>
          </div>
          <div class="vendor_wrap_mask"></div>
        </div>
      </div>
    </div>
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
	
	  var sourceleadUrl ='http://localhost:8080/app';
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
	var sourceleadUrl ='http://localhost:8080/app';
	var locationDate ='dd-MM-yyyy'; 
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
	result = result + ' ';
	result = result + "<option value='ABAP'>ABAP</option>";
	result = result + ' ';
	result = result + "<option value='AbInitio'>AbInitio</option>";
	result = result + ' ';
	result = result + "<option value='Active Directory'>Active Directory</option>";
	result = result + ' ';
	result = result + "<option value='ActiveX'>ActiveX</option>";
	result = result + ' ';
	result = result + "<option value='ADO'>ADO</option>";
	result = result + ' ';
	result = result + "<option value='Adobe Illustrator'>Adobe Illustrator</option>";
	result = result + ' ';
	result = result + "<option value='Adobe Pagemaker'>Adobe Pagemaker</option>";
	result = result + ' ';
	result = result + "<option value='Adobe Photoshop'>Adobe Photoshop</option>";
	result = result + ' ';
	result = result + "<option value='AIX'>AIX</option>";
	result = result + ' ';
	result = result + "<option value='AJAX'>AJAX</option>";
	result = result + ' ';
	result = result + "<option value='ALE'>ALE</option>";
	result = result + ' ';
	result = result + "<option value='Android Development'>Android Development</option>";
	result = result + ' ';
	result = result + "<option value='Ansys'>Ansys</option>";
	result = result + ' ';
	result = result + "<option value='Ant'>Ant</option>";
	result = result + ' ';
	result = result + "<option value='Apache Commons'>Apache Commons</option>";
	result = result + ' ';
	result = result + "<option value='Apache Tomcat'>Apache Tomcat</option>";
	result = result + ' ';
	result = result + "<option value='Apache Web Server'>Apache Web Server</option>";
	result = result + ' ';
	result = result + "<option value='ArchiCAD'>ArchiCAD</option>";
	result = result + ' ';
	result = result + "<option value='AS 400'>AS 400</option>";
	result = result + ' ';
	result = result + "<option value='ASIC'>ASIC</option>";
	result = result + ' ';
	result = result + "<option value='ASP'>ASP</option>";
	result = result + ' ';
	result = result + "<option value='ASP.NET'>ASP.NET</option>";
	result = result + ' ';
	result = result + "<option value=' Assembly Language'> Assembly Language</option>";
	result = result + ' ';
	result = result + "<option value='ATL'>ATL</option>";
	result = result + ' ';
	result = result + "<option value='Auto CAD'>Auto CAD</option>";
	result = result + ' ';
	result = result + "<option value='Automation Testing'>Automation Testing</option>";
	result = result + ' ';
	result = result + "<option value='Autosys'>Autosys</option>";
	result = result + ' ';
	result = result + "<option value='AWT'>AWT</option>";
	result = result + ' ';
	result = result + "<option value='BAAN'>BAAN</option>";
	result = result + ' ';
	result = result + "<option value='Backend'>Backend</option>";
	result = result + ' ';
	result = result + "<option value='Back-office Operations'>Back-office Operations</option>";
	result = result + ' ';
	result = result + "<option value='BASIC'>BASIC</option>";
	result = result + ' ';
	result = result + "<option value='Blackberry'>Blackberry</option>";
	result = result + ' ';
	result = result + "<option value='Bluetooth'>Bluetooth</option>";
	result = result + ' ';
	result = result + "<option value='BPCS'>BPCS</option>";
	result = result + ' ';
	result = result + "<option value='BPEL'>BPEL</option>";
	result = result + ' ';
	result = result + "<option value='BREW'>BREW</option>";
	result = result + ' ';
	result = result + "<option value='Business Objects'>Business Objects</option>";
	result = result + ' ';
	result = result + "<option value='C'>C</option>";
	result = result + ' ';
	result = result + "<option value='C#'>C#</option>";
	result = result + ' ';
	result = result + "<option value='C++'>C++</option>";
	result = result + ' ';
	result = result + "<option value='CAD/CAM'>CAD/CAM</option>";
	result = result + ' ';
	result = result + "<option value='Call Center '>Call Center </option>";
	result = result + ' ';
	result = result + "<option value='Calypso'>Calypso</option>";
	result = result + ' ';
	result = result + "<option value='CATIA'>CATIA</option>";
	result = result + ' ';
	result = result + "<option value='CCIE'>CCIE</option>";
	result = result + ' ';
	result = result + "<option value='CDMA'>CDMA</option>";
	result = result + ' ';
	result = result + "<option value='Chordiant'>Chordiant</option>";
	result = result + ' ';
	result = result + "<option value='CICS'>CICS</option>";
	result = result + ' ';
	result = result + "<option value='CISCO'>CISCO</option>";
	result = result + ' ';
	result = result + "<option value='CLDC'>CLDC</option>";
	result = result + ' ';
	result = result + "<option value='Clipper'>Clipper</option>";
	result = result + ' ';
	result = result + "<option value='COBOL'>COBOL</option>";
	result = result + ' ';
	result = result + "<option value='Cognos'>Cognos</option>";
	result = result + ' ';
	result = result + "<option value='ColdFusion'>ColdFusion</option>";
	result = result + ' ';
	result = result + "<option value='COM/COM+/DCOM '>COM/COM+/DCOM </option>";
	result = result + ' ';

	result = result + "<option value='CORBA'>CORBA</option>";
	result = result + ' ';
	result = result + "<option value='CoreJava'>CoreJava</option>";
	result = result + ' ';
	result = result + "<option value='Corel Draw '>Corel Draw </option>";
	result = result + ' ';
	result = result + "<option value='CRM'>CRM</option>";
	result = result + ' ';
	result = result + "<option value='Crystal Report '>Crystal Report </option>";
	result = result + ' ';
	result = result + "<option value='Csharp'>Csharp</option>";
	result = result + ' ';
	result = result + "<option value='CSS'>CSS</option>";
	result = result + ' ';
	result = result + "<option value='Data Analysis '>Data Analysis </option>";
	result = result + ' ';
	result = result + "<option value='Data Structures '>Data Structures </option>";
	result = result + ' ';
	result = result + "<option value='Database'>Database</option>";
	result = result + ' ';
	result = result + "<option value='Databse Administration '>Databse Administration </option>";
	result = result + ' ';
	result = result + "<option value='Datastage'>Datastage</option>";
	result = result + ' ';
	result = result + "<option value='Datawarehousing '>Datawarehousing </option>";
	result = result + ' ';
	result = result + "<option value='DB2'>DB2</option>";
	result = result + ' ';
	result = result + "<option value='Db4o'>Db4o</option>";
	result = result + ' ';
	result = result + "<option value='DBase'>DBase</option>";
	result = result + ' ';
	result = result + "<option value='DCA'>DCA</option>";
	result = result + ' ';
	result = result + "<option value='DCOM'>DCOM</option>";
	result = result + ' ';
	result = result + "<option value='Delphi'>Delphi</option>";
	result = result + ' ';
	result = result + "<option value='Derby'>Derby</option>";
	result = result + ' ';
	result = result + "<option value='Developer/D2K'>Developer/D2K</option>";
	result = result + ' ';
	result = result + "<option value='DHCP'>DHCP</option>";
	result = result + ' ';
	result = result + "<option value='DHTML'>DHTML</option>";
	result = result + ' ';
	result = result + "<option value='Distribution'>Distribution</option>";
	result = result + ' ';
	result = result + "<option value='DNS'>DNS</option>";
	result = result + ' ';
	result = result + "<option value='Documentation'>Documentation</option>";
	result = result + ' ';
	result = result + "<option value='Documentum'>Documentum</option>";
	result = result + ' ';
	result = result + "<option value='DOS'>DOS</option>";
	result = result + ' ';
	result = result + "<option value='Downstream'>Downstream</option>";
	result = result + ' ';
	result = result + "<option value='Dreamweaver'>Dreamweaver</option>";
	result = result + ' ';
	result = result + "<option value='DSP'>DSP</option>";
	result = result + ' ';
	result = result + "<option value='DTP'>DTP</option>";
	result = result + ' ';
	result = result + "<option value='Eclipse'>Eclipse</option>";
	result = result + ' ';
	result = result + "<option value='Editing'>Editing</option>";
	result = result + ' ';
	result = result + "<option value='EJB'>EJB</option>";
	result = result + ' ';
	result = result + "<option value='Embedded C'>Embedded C</option>";
	result = result + ' ';
	result = result + "<option value='ERP'>ERP</option>";
	result = result + ' ';
	result = result + "<option value='Ethernet'>Ethernet</option>";
	result = result + ' ';
	result = result + "<option value='Excel'>Excel</option>";
	result = result + ' ';
	result = result + "<option value='Expeditor'>Expeditor</option>";
	result = result + ' ';
	result = result + "<option value='FACT'>FACT</option>";
	result = result + ' ';
	result = result + "<option value='FileNet'>FileNet</option>";
	result = result + ' ';
	result = result + "<option value='Finacle'>Finacle</option>";
	result = result + ' ';
	result = result + "<option value='Finone'>Finone</option>";
	result = result + ' ';
	result = result + "<option value='Firewall'>Firewall</option>";
	result = result + ' ';
	result = result + "<option value='FTP'>FTP</option>";
	result = result + ' ';
	result = result + "<option value='Fireworks'>Fireworks</option>";
	result = result + ' ';
	result = result + "<option value='Flash'>Flash</option>";
	result = result + ' ';
	result = result + "<option value='FlashLite'>FlashLite</option>";
	result = result + ' ';
	result = result + "<option value='Flex'>Flex</option>";
	result = result + ' ';
	result = result + "<option value='Flexcube'>Flexcube</option>";
	result = result + ' ';
	result = result + "<option value='Focus'>Focus</option>";
	result = result + ' ';
	result = result + "<option value='ForTran'>ForTran</option>";
	result = result + ' ';
	result = result + "<option value='FoxPro'>FoxPro</option>";
	result = result + ' ';
	result = result + "<option value='FPGA'>FPGA</option>";
	result = result + ' ';
	result = result + "<option value='FreeBSD'>FreeBSD</option>";
	result = result + ' ';
	result = result + "<option value='GLOSS'>GLOSS</option>";
	result = result + ' ';
	result = result + "<option value='GSM'>GSM</option>";
	result = result + ' ';
	result = result + "<option value='Hibernate'>Hibernate</option>";
	result = result + ' ';
	result = result + "<option value='HP UNIX '>HP UNIX </option>";
	result = result + ' ';
	result = result + "<option value='HTML'>HTML</option>";
	result = result + ' ';
	result = result + "<option value='Humming Bird'>Humming Bird</option>";
	result = result + ' ';
	result = result + "<option value='Hyperion '>Hyperion </option>";
	result = result + ' ';
	result = result + "<option value='iBatis'>iBatis</option>";
	result = result + ' ';
	result = result + "<option value='Ideas'>Ideas</option>";
	result = result + ' ';
	result = result + "<option value='IIS'>IIS</option>";
	result = result + ' ';
	result = result + "<option value='ImageReady'>ImageReady</option>";
	result = result + ' ';
	result = result + "<option value='Impromptu'>Impromptu</option>";
	result = result + ' ';
	result = result + "<option value='IMS'>IMS</option>";
	result = result + ' ';
	result = result + "<option value='Informatica'>Informatica</option>";
	result = result + ' ';
	result = result + "<option value='Informix'>Informix</option>";
	result = result + ' ';
	result = result + "<option value='Ingres'>Ingres</option>";
	result = result + ' ';
	result = result + "<option value='Installshield'>Installshield</option>";
	result = result + ' ';
	result = result + "<option value='ISDN'>ISDN</option>";
	result = result + ' ';
	result = result + "<option value='ITIL'>ITIL</option>";
	result = result + ' ';
	result = result + "<option value='J++'>J++</option>";
	result = result + ' ';
	result = result + "<option value='J2EE'>J2EE</option>";
	result = result + ' ';
	result = result + "<option value='J2ME'>J2ME</option>";
	result = result + ' ';
	result = result + "<option value='J2SE'>J2SE</option>";
	result = result + ' ';
	result = result + "<option value='Java'>Java</option>";
	result = result + ' ';
	result = result + "<option value='Java2D'>Java2D</option>";
	result = result + ' ';
	result = result + "<option value='JavaCard'>JavaCard</option>";
	result = result + ' ';
	result = result + "<option value='JavaFX'>JavaFX</option>";
	result = result + ' ';
	result = result + "<option value='JavaScript'>JavaScript</option>";
	result = result + ' ';
	result = result + "<option value='JavaSE'>JavaSE</option>";
	result = result + ' ';
	result = result + "<option value='JBoss'>JBoss</option>";
	result = result + ' ';
	result = result + "<option value='JBoss Seam '>JBoss Seam </option>";
	result = result + ' ';
	result = result + "<option value='JCL'>JCL</option>";
	result = result + ' ';
	result = result + "<option value='JDBC'>JDBC</option>";
	result = result + ' ';
	result = result + "<option value='JDOM'>JDOM</option>";
	result = result + ' ';
	result = result + "<option value='JFace'>JFace</option>";
	result = result + ' ';
	result = result + "<option value='Jini'>Jini</option>";
	result = result + ' ';
	result = result + "<option value='JIRA'>JIRA</option>";
	result = result + ' ';
	result = result + "<option value='JMock'>JMock</option>";
	result = result + ' ';
	result = result + "<option value='JMS'>JMS</option>";
	result = result + ' ';
	result = result + "<option value='JNI'>JNI</option>";
	result = result + ' ';
	result = result + "<option value='JPA'>JPA</option>";
	result = result + ' ';
	result = result + "<option value='Jquery'>Jquery</option>";
	result = result + ' ';
	result = result + "<option value='JSE'>JSE</option>";
	result = result + ' ';
	result = result + "<option value='JSF'>JSF</option>";
	result = result + ' ';
	result = result + "<option value='JSP'>JSP</option>";
	result = result + ' ';
	result = result + "<option value='JUnit'>JUnit</option>";
	result = result + ' ';
	result = result + "<option value='Kickboxing'>Kickboxing</option>";
	result = result + ' ';
	result = result + "<option value='KSH'>KSH</option>";
	result = result + ' ';
	result = result + "<option value='LAN'>LAN</option>";
	result = result + ' ';
	result = result + "<option value='LINQ'>LINQ</option>";
	result = result + ' ';
	result = result + "<option value='Linux'>Linux</option>";
	result = result + ' ';
	result = result + "<option value='Load Runner'>Load Runner</option>";
	result = result + ' ';
	result = result + "<option value='Log4j'>Log4j</option>";
	result = result + ' ';
	result = result + "<option value='Lotus Notes'>Lotus Notes</option>";
	result = result + ' ';
	result = result + "<option value='Mac OS'>Mac OS</option>";
	result = result + ' ';
	result = result + "<option value='Mainframe'>Mainframe</option>";
	result = result + ' ';
	result = result + "<option value='Manual Testing'>Manual Testing</option>";
	result = result + ' ';
	result = result + "<option value='MATLAB'>MATLAB</option>";
	result = result + ' ';
	result = result + "<option value='Maven'>Maven</option>";
	result = result + ' ';
	result = result + "<option value='Maximo'>Maximo</option>";
	result = result + ' ';
	result = result + "<option value='Maya'>Maya</option>";
	result = result + ' ';
	result = result + "<option value='MCP'>MCP</option>";
	result = result + ' ';
	result = result + "<option value='MCSA'>MCSA</option>";
	result = result + ' ';
	result = result + "<option value='MCSE'>MCSE</option>";
	result = result + ' ';
	result = result + "<option value='MFC'>MFC</option>";
	result = result + ' ';
	result = result + "<option value='Microsoft Access'>Microsoft Access</option>";
	result = result + ' ';
	result = result + "<option value='Microsoft Excel'>Microsoft Excel</option>";
	result = result + ' ';
	result = result + "<option value='Microsoft Exchange'>Microsoft Exchange</option>";
	result = result + ' ';
	result = result + "<option value='Microsoft Office'>Microsoft Office</option>";
	result = result + ' ';
	result = result + "<option value='MicroStation'>MicroStation</option>";
	result = result + ' ';
	result = result + "<option value='MIDP'>MIDP</option>";
	result = result + ' ';
	result = result + "<option value='MIS Reports'>MIS Reports</option>";
	result = result + ' ';
	result = result + "<option value='Motif'>Motif</option>";
	result = result + ' ';
	result = result + "<option value='MS DOS'>MS DOS</option>";
	result = result + ' ';
	result = result + "<option value='MS Project'>MS Project</option>";
	result = result + ' ';
	result = result + "<option value='MS SQL Server'>MS SQL Server</option>";
	result = result + ' ';
	result = result + "<option value='MS Visio'>MS Visio</option>";
	result = result + ' ';
	result = result + "<option value='MSCIT'>MSCIT</option>";
	result = result + ' ';
	result = result + "<option value='Multimedia'>Multimedia</option>";
	result = result + ' ';
	result = result + "<option value='Multithreading'>Multithreading</option>";
	result = result + ' ';
	result = result + "<option value='Murex'>Murex</option>";
	result = result + ' ';
	result = result + "<option value='MVS'>MVS</option>";
	result = result + ' ';
	result = result + "<option value='MySQL'>MySQL</option>";
	result = result + ' ';
	result = result + "<option value='Natural Adabas'>Natural Adabas</option>";
	result = result + ' ';
	result = result + "<option value='NetWeaver'>NetWeaver</option>";
	result = result + ' ';
	result = result + "<option value='sourceleading'>sourceleading</option>";
	result = result + ' ';
	result = result + "<option value='Nortel'>Nortel</option>";
	result = result + ' ';
	result = result + "<option value='Novell'>Novell</option>";
	result = result + ' ';
	result = result + "<option value='ODBC'>ODBC</option>";
	result = result + ' ';
	result = result + "<option value='OOPS'>OOPS</option>";
	result = result + ' ';
	result = result + "<option value='OpenGL ES'>OpenGL ES</option>";
	result = result + ' ';
	result = result + "<option value='Operating Systems'>Operating Systems</option>";
	result = result + ' ';
	result = result + "<option value='Oracle'>Oracle</option>";
	result = result + ' ';
	result = result + "<option value='Oracle Apps'>Oracle Apps</option>";
	result = result + ' ';
	result = result + "<option value='Oracle WareHouse Builder'>Oracle WareHouse Builder</option>";
	result = result + ' ';
	result = result + "<option value='ORCAD'>ORCAD</option>";
	result = result + ' ';
	result = result + "<option value='OS/2'>OS/2</option>";
	result = result + ' ';
	result = result + "<option value='PASCAL'>PASCAL</option>";
	result = result + ' ';
	result = result + "<option value='PeopleSoft'>PeopleSoft</option>";
	result = result + ' ';
	result = result + "<option value='Perl'>Perl</option>";
	result = result + ' ';
	result = result + "<option value='Photoshop'>Photoshop</option>";
	result = result + ' ';
	result = result + "<option value='PHP'>PHP</option>";
	result = result + ' ';
	result = result + "<option value='PL/1'>PL/1</option>";
	result = result + ' ';
	result = result + "<option value='PL/SQL'>PL/SQL</option>";
	result = result + ' ';
	result = result + "<option value='PLC/Microcontrollers/Microprocessors'>PLC/Microcontrollers/Microprocessors</option>";
	result = result + ' ';
	result = result + "<option value='PostgreSQL'>PostgreSQL</option>";
	result = result + ' ';
	result = result + "<option value='PowerBuilder'>PowerBuilder</option>";
	result = result + ' ';
	result = result + "<option value='PowerPlay'>PowerPlay</option>";
	result = result + ' ';
	result = result + "<option value='Primavera'>Primavera</option>";
	result = result + ' ';
	result = result + "<option value='Pro*C'>Pro*C</option>";
	result = result + ' ';
	result = result + "<option value='PRO-E'>PRO-E</option>";
	result = result + ' ';
	result = result + "<option value='Programming'>Programming</option>";
	result = result + ' ';
	result = result + "<option value='Progress 4GL'>Progress 4GL</option>";
	result = result + ' ';
	result = result + "<option value='PVCS'>PVCS</option>";
	result = result + ' ';
	result = result + "<option value='Python'>Python</option>";
	result = result + ' ';
	result = result + "<option value='QBasic'>QBasic</option>";
	result = result + ' ';
	result = result + "<option value='QTP'>QTP</option>";
	result = result + ' ';
	result = result + "<option value='Quality Assurance/QA'>Quality Assurance/QA</option>";
	result = result + ' ';
	result = result + "<option value='RDO'>RDO</option>";
	result = result + ' ';
	result = result + "<option value='RedHat/Linux'>RedHat/Linux</option>";
	result = result + ' ';
	result = result + "<option value='Remedy'>Remedy</option>";
	result = result + ' ';
	result = result + "<option value='Remoting'>Remoting</option>";
	result = result + ' ';
	result = result + "<option value='REXX'>REXX</option>";
	result = result + ' ';
	result = result + "<option value='RichFaces'>RichFaces</option>";
	result = result + ' ';
	result = result + "<option value='RIM'>RIM</option>";
	result = result + ' ';
	result = result + "<option value='RMI'>RMI</option>";
	result = result + ' ';
	result = result + "<option value='Routing'>Routing</option>";
	result = result + ' ';
	result = result + "<option value='RTOS'>RTOS</option>";
	result = result + ' ';
	result = result + "<option value='S60'>S60</option>";
	result = result + ' ';
	result = result + "<option value='SAP'>SAP</option>";
	result = result + ' ';
	result = result + "<option value='SAP ABAP/IDOCS'>SAP ABAP/IDOCS</option>";
	result = result + ' ';
	result = result + "<option value='SAP Basis'>SAP Basis</option>";
	result = result + ' ';
	result = result + "<option value='SAP BW/BI'>SAP BW/BI</option>";
	result = result + ' ';
	result = result + "<option value='SAP CRM'>SAP CRM</option>";
	result = result + ' ';
	result = result + "<option value='SAP EP'>SAP EP</option>";
	result = result + ' ';
	result = result + "<option value='SAP FICO/COPA'>SAP FICO/COPA</option>";
	result = result + ' ';
	result = result + "<option value='SAP HR'>SAP HR</option>";
	result = result + ' ';
	result = result + "<option value='SAP IS-GAS/OIL'>SAP IS-GAS/OIL</option>";
	result = result + ' ';
	result = result + "<option value='SAP IS-Retail'>SAP IS-Retail</option>";
	result = result + ' ';
	result = result + "<option value='SAP IS-Utilities'>SAP IS-Utilities</option>";
	result = result + ' ';
	result = result + "<option value='SAP MDM'>SAP MDM</option>";
	result = result + ' ';
	result = result + "<option value='SAP MM'>SAP MM</option>";
	result = result + ' ';
	result = result + "<option value='SAP PM'>SAP PM</option>";
	result = result + ' ';
	result = result + "<option value='SAP PP'>SAP PP</option>";
	result = result + ' ';
	result = result + "<option value='SAP Practice'>SAP Practice</option>";
	result = result + ' ';
	result = result + "<option value='SAP PS'>SAP PS</option>";
	result = result + ' ';
	result = result + "<option value='SAP QM'>SAP QM</option>";
	result = result + ' ';
	result = result + "<option value='SAP SCM'>SAP SCM</option>";
	result = result + ' ';
	result = result + "<option value='SAP SD'>SAP SD</option>";
	result = result + ' ';
	result = result + "<option value='SAP Security'>SAP Security</option>";
	result = result + ' ';
	result = result + "<option value='SAP SEM'>SAP SEM</option>";
	result = result + ' ';
	result = result + "<option value='SAP SRM'>SAP SRM</option>";
	result = result + ' ';
	result = result + "<option value='SAP WMS'>SAP WMS</option>";
	result = result + ' ';
	result = result + "<option value='SAP XI'>SAP XI</option>";
	result = result + ' ';
	result = result + "<option value='SAS'>SAS</option>";
	result = result + ' ';
	result = result + "<option value='Savvion'>Savvion</option>";
	result = result + ' ';
	result = result + "<option value='SCADA'>SCADA</option>";
	result = result + ' ';
	result = result + "<option value='Seam'>Seam</option>";
	result = result + ' ';
	result = result + "<option value='Sharepoint Server/MOSS'>Sharepoint Server/MOSS</option>";
	result = result + ' ';
	result = result + "<option value='Shell Scripting '>Shell Scripting </option>";
	result = result + ' ';
	result = result + "<option value='Siebel'>Siebel</option>";
	result = result + ' ';
	result = result + "<option value='Silverlight'>Silverlight</option>";
	result = result + ' ';
	result = result + "<option value='SMARTY'>SMARTY</option>";
	result = result + ' ';
	result = result + "<option value='SMTP'>SMTP</option>";
	result = result + ' ';
	result = result + "<option value='Software Testing'>Software Testing</option>";
	result = result + ' ';
	result = result + "<option value='Solaris'>Solaris</option>";
	result = result + ' ';
	result = result + "<option value='SoundForge'>SoundForge</option>";
	result = result + ' ';
	result = result + "<option value='Spring'>Spring</option>";
	result = result + ' ';
	result = result + "<option value='SPSS'>SPSS</option>";
	result = result + ' ';
	result = result + "<option value='SQL Server'>SQL Server</option>";
	result = result + ' ';
	result = result + "<option value='STAAD'>STAAD</option>";
	result = result + ' ';
	result = result + "<option value='Struts'>Struts</option>";
	result = result + ' ';
	result = result + "<option value='SunOS'>SunOS</option>";
	result = result + ' ';
	result = result + "<option value='Switching'>Switching</option>";
	result = result + ' ';
	result = result + "<option value='SWT'>SWT</option>";
	result = result + ' ';
	result = result + "<option value='Sybase'>Sybase</option>";
	result = result + ' ';
	result = result + "<option value='Symbian'>Symbian</option>";
	result = result + ' ';
	result = result + "<option value='Symbian C++'>Symbian C++</option>";
	result = result + ' ';
	result = result + "<option value='SyncML'>SyncML</option>";
	result = result + ' ';
	result = result + "<option value='Tally'>Tally</option>";
	result = result + ' ';
	result = result + "<option value='TCL/TK'>TCL/TK</option>";
	result = result + ' ';
	result = result + "<option value='TCP/IP'>TCP/IP</option>";
	result = result + ' ';
	result = result + "<option value='TELNET'>TELNET</option>";
	result = result + ' ';
	result = result + "<option value='Teradata'>Teradata</option>";
	result = result + ' ';
	result = result + "<option value='Tibco'>Tibco</option>";
	result = result + ' ';
	result = result + "<option value='TLM'>TLM</option>";
	result = result + ' ';
	result = result + "<option value='TOAD'>TOAD</option>";
	result = result + ' ';
	result = result + "<option value='TPF'>TPF</option>";
	result = result + ' ';
	result = result + "<option value='T-SQL'>T-SQL</option>";
	result = result + ' ';
	result = result + "<option value='Turbo Pascal'>Turbo Pascal</option>";
	result = result + ' ';
	result = result + "<option value='Tuxedo'>Tuxedo</option>";
	result = result + ' ';
	result = result + "<option value='UIQ'>UIQ</option>";
	result = result + ' ';
	result = result + "<option value='UML'>UML</option>";
	result = result + ' ';
	result = result + "<option value='UniGraphics'>UniGraphics</option>";
	result = result + ' ';
	result = result + "<option value='Unix'>Unix</option>";
	result = result + ' ';
	result = result + "<option value='Upstream'>Upstream</option>";
	result = result + ' ';
	result = result + "<option value='VAX/VMS'>VAX/VMS</option>";
	result = result + ' ';
	result = result + "<option value='VB.NET'>VB.NET</option>";
	result = result + ' ';
	result = result + "<option value='VBScript'>VBScript</option>";
	result = result + ' ';
	result = result + "<option value='Verilog'>Verilog</option>";
	result = result + ' ';
	result = result + "<option value='Vi'>Vi</option>";
	result = result + ' ';
	result = result + "<option value='Video Editing'>Video Editing</option>";
	result = result + ' ';
	result = result + "<option value='Vignette'>Vignette</option>";
	result = result + ' ';
	result = result + "<option value='Vision Plus'>Vision Plus</option>";
	result = result + ' ';
	result = result + "<option value='Visual Basic'>Visual Basic</option>";
	result = result + ' ';
	result = result + "<option value='Visual C++'>Visual C++</option>";
	result = result + ' ';
	result = result + "<option value='Visual Foxpro'>Visual Foxpro</option>";
	result = result + ' ';
	result = result + "<option value='Visual Interdev'>Visual Interdev</option>";
	result = result + ' ';
	result = result + "<option value='VLSI'>VLSI</option>";
	result = result + ' ';
	result = result + "<option value='VMS'>VMS</option>";
	result = result + ' ';
	result = result + "<option value='VOIP'>VOIP</option>";
	result = result + ' ';
	result = result + "<option value='VPN'>VPN</option>";
	result = result + ' ';
	result = result + "<option value='VSAT'>VSAT</option>";
	result = result + ' ';
	result = result + "<option value='VSS/Clearcase'>VSS/Clearcase</option>";
	result = result + ' ';
	result = result + "<option value='VxWorks'>VxWorks</option>";
	result = result + ' ';
	result = result + "<option value='WAN'>WAN</option>";
	result = result + ' ';
	result = result + "<option value='WAP'>WAP</option>";
	result = result + ' ';
	result = result + "<option value='WCF'>WCF</option>";
	result = result + ' ';
	result = result + "<option value='Web Designing'>Web Designing</option>";
	result = result + ' ';
	result = result + "<option value='WebLogic'>WebLogic</option>";
	result = result + ' ';
	result = result + "<option value='WebMethods'>WebMethods</option>";
	result = result + ' ';
	result = result + "<option value='Website Development'>Website Development</option>";
	result = result + ' ';
	result = result + "<option value='WebSphere'>WebSphere</option>";
	result = result + ' ';
	result = result + "<option value='Winform'>Winform</option>";
	result = result + ' ';
	result = result + "<option value='Winrunner'>Winrunner</option>";
	result = result + ' ';
	result = result + "<option value='WML'>WML</option>";
	result = result + ' ';
	result = result + "<option value='WPF'>WPF</option>";
	result = result + ' ';
	result = result + "<option value='XDoclet'>XDoclet</option>";
	result = result + ' ';
	result = result + "<option value='Xenix'>Xenix</option>";
	result = result + ' ';
	result = result + "<option value='XHTML'>XHTML</option>";
	result = result + ' ';
	result = result + "<option value='XML'>XML</option>";
	result = result + ' ';
	result = result + "<option value='XStream'>XStream</option>";
	result = result + ' ';
	result = result + "<option value='Accounting'>Accounting</option>";
	result = result + ' ';
	result = result + "<option value='Accounts '>Accounts </option>";
	result = result + ' ';
	result = result + "<option value='Accounts Management'>Accounts Management</option>";
	result = result + ' ';
	result = result + "<option value='Acting '>Acting </option>";
	result = result + ' ';
	result = result + "<option value='Administration'>Administration</option>";
	result = result + ' ';
	result = result + "<option value='Advertisement Sales'>Advertisement Sales</option>";
	result = result + ' ';
	result = result + "<option value='Advertising'>Advertising</option>";
	result = result + ' ';
	result = result + "<option value='Advertising Account Management '>Advertising Account Management </option>";
	result = result + ' ';
	result = result + "<option value='Advertising Art Direction '>Advertising Art Direction </option>";
	result = result + ' ';
	result = result + "<option value='Advisory'>Advisory</option>";
	result = result + ' ';
	result = result + "<option value='Analysis'>Analysis</option>";
	result = result + ' ';
	result = result + "<option value='Animation'>Animation</option>";
	result = result + ' ';
	result = result + "<option value='Aquisitions '>Aquisitions </option>";
	result = result + ' ';
	result = result + "<option value='Architecture'>Architecture</option>";
	result = result + ' ';
	result = result + "<option value='Art'>Art</option>";
	result = result + ' ';
	result = result + "<option value='Art Therapy'>Art Therapy</option>";
	result = result + ' ';
	result = result + "<option value='Astrology'>Astrology</option>";
	result = result + ' ';
	result = result + "<option value='Authoring'>Authoring</option>";
	result = result + ' ';
	result = result + "<option value='Baking'>Baking</option>";
	result = result + ' ';
	result = result + "<option value='Banking'>Banking</option>";
	result = result + ' ';
	result = result + "<option value='Banking Insurance'>Banking Insurance</option>";
	result = result + ' ';
	result = result + "<option value='Biotechnology'>Biotechnology</option>";
	result = result + ' ';
	result = result + "<option value='Body Art'>Body Art</option>";
	result = result + ' ';
	result = result + "<option value='Bookbinding'>Bookbinding</option>";
	result = result + ' ';
	result = result + "<option value='Bookkeeping'>Bookkeeping</option>";
	result = result + ' ';
	result = result + "<option value='Business Analysis'>Business Analysis</option>";
	result = result + ' ';
	result = result + "<option value='Buying'>Buying</option>";
	result = result + ' ';
	result = result + "<option value='Channel Account Management'>Channel Account Management</option>";
	result = result + ' ';
	result = result + "<option value='Chartered Accountancy'>Chartered Accountancy</option>";
	result = result + ' ';
	result = result + "<option value='Cloth Design'>Cloth Design</option>";
	result = result + ' ';
	result = result + "<option value='Coaching'>Coaching</option>";
	result = result + ' ';
	result = result + "<option value='Corporate Finance'>Corporate Finance</option>";
	result = result + ' ';
	result = result + "<option value='Cost Accounting'>Cost Accounting</option>";
	result = result + ' ';
	result = result + "<option value='Counselling'>Counselling</option>";
	result = result + ' ';
	result = result + "<option value='Curating'>Curating</option>";
	result = result + ' ';
	result = result + "<option value='Customer Relations'>Customer Relations</option>";
	result = result + ' ';
	result = result + "<option value='Dance'>Dance</option>";
	result = result + ' ';
	result = result + "<option value='Design'>Design</option>";
	result = result + ' ';
	result = result + "<option value='Document Administration'>Document Administration</option>";
	result = result + ' ';
	result = result + "<option value='Education'>Education</option>";
	result = result + ' ';
	result = result + "<option value='Electrical Distribution'>Electrical Distribution</option>";
	result = result + ' ';
	result = result + "<option value='Email Marketing'>Email Marketing</option>";
	result = result + ' ';
	result = result + "<option value='Entertainment'>Entertainment</option>";
	result = result + ' ';
	result = result + "<option value='Film Editing'>Film Editing</option>";
	result = result + ' ';
	result = result + "<option value='Finance'>Finance</option>";
	result = result + ' ';
	result = result + "<option value='Floristry'>Floristry</option>";
	result = result + ' ';
	result = result + "<option value='Graphic Design'>Graphic Design</option>";
	result = result + ' ';
	result = result + "<option value='Guest Service'>Guest Service</option>";
	result = result + ' ';
	result = result + "<option value='Hairdressing'>Hairdressing</option>";
	result = result + ' ';
	result = result + "<option value='Headhunting'>Headhunting</option>";
	result = result + ' ';
	result = result + "<option value='Hiring'>Hiring</option>";
	result = result + ' ';
	result = result + "<option value='Human Resource Management'>Human Resource Management</option>";
	result = result + ' ';
	result = result + "<option value='HVAC'>HVAC</option>";
	result = result + ' ';
	result = result + "<option value='Illustration'>Illustration</option>";
	result = result + ' ';
	result = result + "<option value='Image Consulting'>Image Consulting</option>";
	result = result + ' ';
	result = result + "<option value='Income Tax'>Income Tax</option>";
	result = result + ' ';
	result = result + "<option value='Industrial Designing'>Industrial Designing</option>";
	result = result + ' ';
	result = result + "<option value='Interior Design'>Interior Design</option>";
	result = result + ' ';
	result = result + "<option value='IT Recruitment'>IT Recruitment</option>";
	result = result + ' ';
	result = result + "<option value='Jewellery'>Jewellery</option>";
	result = result + ' ';
	result = result + "<option value='Journalism'>Journalism</option>";
	result = result + ' ';
	result = result + "<option value='Key Accounts Management'>Key Accounts Management</option>";
	result = result + ' ';
	result = result + "<option value='Knowledge Management'>Knowledge Management</option>";
	result = result + ' ';
	result = result + "<option value='Landscape Architecture'>Landscape Architecture</option>";
	result = result + ' ';
	result = result + "<option value='Landscape Gardening'>Landscape Gardening</option>";
	result = result + ' ';
	result = result + "<option value='Lighting'>Lighting</option>";
	result = result + ' ';
	result = result + "<option value='Make-up Art'>Make-up Art</option>";
	result = result + ' ';
	result = result + "<option value='Manufacturing'>Manufacturing</option>";
	result = result + ' ';
	result = result + "<option value='Map Making'>Map Making</option>";
	result = result + ' ';
	result = result + "<option value='Marketing'>Marketing</option>";
	result = result + ' ';
	result = result + "<option value='Mathematical'>Mathematical</option>";
	result = result + ' ';
	result = result + "<option value='Merchandise'>Merchandise</option>";
	result = result + ' ';
	result = result + "<option value='Mergers'>Mergers</option>";
	result = result + ' ';
	result = result + "<option value='Motivating Skill'>Motivating Skill</option>";
	result = result + ' ';
	result = result + "<option value='Music'>Music</option>";
	result = result + ' ';
	result = result + "<option value='Negotiating Skill'>Negotiating Skill</option>";
	result = result + ' ';
	result = result + "<option value='Nursing'>Nursing</option>";
	result = result + ' ';
	result = result + "<option value='Office Administration'>Office Administration</option>";
	result = result + ' ';
	result = result + "<option value='Office Management'>Office Management</option>";
	result = result + ' ';
	result = result + "<option value='Personal Services'>Personal Services</option>";
	result = result + ' ';
	result = result + "<option value='Planning and Organising'>Planning and Organising</option>";
	result = result + ' ';
	result = result + "<option value='Plumbing'>Plumbing</option>";
	result = result + ' ';
	result = result + "<option value='PPC'>PPC</option>";
	result = result + ' ';
	result = result + "<option value='Private Tutoring'>Private Tutoring</option>";
	result = result + ' ';
	result = result + "<option value='Producing'>Producing</option>";
	result = result + ' ';
	result = result + "<option value='Record Producing'>Record Producing</option>";
	result = result + ' ';
	result = result + "<option value='Recruitment'>Recruitment</option>";
	result = result + ' ';
	result = result + "<option value='Talent Acquisition'>Talent Acquisition</option>";
	result = result + ' ';
	result = result + "<option value='Talent Acquisition'>Talent Acquisition</option>";
	result = result + ' ';
	result = result + "<option value='Tax Audits'>Tax Audits</option>";
	result = result + ' ';
	result = result + "<option value='Tax Laws'>Tax Laws</option>";
	result = result + ' ';
	result = result + "<option value='Taxation'>Taxation</option>";
	result = result + ' ';
	result = result + "<option value='Teaching and Instructing'>Teaching and Instructing</option>";
	result = result + ' ';
	result = result + "<option value='Telemarketing'>Telemarketing</option>";
	result = result + ' ';
	result = result + "<option value='Telling'>Telling</option>";
	result = result + ' ';
	result = result + "<option value='Textile Designing'>Textile Designing</option>";
	result = result + ' ';
	result = result + "<option value='Therapy'>Therapy</option>";
	result = result + ' ';
	result = result + "<option value='Trade Execution'>Trade Execution</option>";
	result = result + ' ';
	result = result + "<option value='Training'>Training</option>";
	result = result + ' ';
	result = result + "<option value='Upholstery'>Upholstery</option>";
	result = result + ' ';
	result = result + "<option value='Vehicle Operating'>Vehicle Operating</option>";
	result = result + ' ';
	result = result + "<option value='Vendor Management'>Vendor Management</option>";
	result = result + ' ';
	result = result + "<option value='Visa Expat Management'>Visa Expat Management</option>";
	result = result + ' ';
	result = result + "<option value='Visual Merchandising'>Visual Merchandising</option>";
	result = result + ' ';
	result = result + "<option value='Waste Management'>Waste Management</option>";
	result = result + ' ';
	result = result + "<option value='Weaving'>Weaving</option>";
	result = result + ' ';
	result = result + "<option value='Winemaking'>Winemaking</option>";
	result = result + ' ';
	result = result + "<option value='Writing'>Writing</option>";
	result = result + ' ';
	result = result + "<option value='Yoga'>Yoga</option>";
	result = result + ' ';
	result = result + "<option value='Selenium'>Selenium</option>";
	result = result + ' ';
	result = result + "<option value='QTP'>QTP</option>";
	result = result + ' ';
	result = result + "<option value='Quality Center'>Quality Center</option>";
	result = result + ' ';
	result = result + "<option value='Advanced Java'>Advanced Java</option>";
	result = result + ' ';
	result = result + "<option value='AdvancedModelsim'>AdvancedModelsim</option>";
	result = result + ' ';
	result = result + "<option value='.Net'>.Net</option>";
	result = result + ' ';
	result = result + "<option value='ADOAdvancedModelsim'>ADOAdvancedModelsim</option>";
	result = result + ' ';
	result = result + "<option value='AdobeFlexBuilder'>AdobeFlexBuilder</option>";
	result = result + ' ';
	result = result + "<option value='ActiveHDL'>ActiveHDL</option>";
	result = result + ' ';
	result = result + "<option value='AcegiSecurity'>AcegiSecurity</option>";
	result = result + ' ';
	result = result + "<option value='.NET(C#&ASP.NET)'>.NET(C#&ASP.NET)</option>";
	result = result + ' ';
	result = result + "<option value='.NETFramework'>.NETFramework</option>";
	result = result + ' ';
	result = result + "<option value='AdwordKeywordTool'>AdwordKeywordTool</option>";
	result = result + ' ';
	result = result + "<option value='Agile'>Agile</option>";
	result = result + ' ';
	result = result + "<option value='AHB'>AHB</option>";
	result = result + ' ';
	result = result + "<option value='Ahreftool'>Ahreftool</option>";
	result = result + ' ';
	result = result + "<option value='AjaxandJSON'>AjaxandJSON</option>";
	result = result + ' ';
	result = result + "<option value='ALM/QC'>ALM/QC</option>";
	result = result + ' ';
	result = result + "<option value='ALM'>ALM</option>";
	result = result + ' ';
	result = result + "<option value='Angular'>Angular</option>";
	result = result + ' ';
	result = result + "<option value='AngularJS'>AngularJS</option>";
	result = result + ' ';
	result = result + "<option value='Apache'>Apache</option>";
	result = result + ' ';
	result = result + "<option value='ApacheCamel'>ApacheCamel</option>";
	result = result + ' ';
	result = result + "<option value='ApacheDrill'>ApacheDrill</option>";
	result = result + ' ';
	result = result + "<option value='ApachePOI'>ApachePOI</option>";
	result = result + ' ';
	result = result + "<option value='ApacheSubversion(SVN)'>ApacheSubversion(SVN)</option>";
	result = result + ' ';
	result = result + "<option value='APB'>APB</option>";
	result = result + ' ';
	result = result + "<option value='AprimoApplicationWorkbench'>AprimoApplicationWorkbench</option>";
	result = result + ' ';
	result = result + "<option value='AprimoMarketingStudio'>AprimoMarketingStudio</option>";
	result = result + ' ';
	result = result + "<option value='AptanaStudio'>AptanaStudio</option>";
	result = result + ' ';
	result = result + "<option value='AquaStudio'>AquaStudio</option>";
	result = result + ' ';
	result = result + "<option value='ArchitectureOOP'>ArchitectureOOP</option>";
	result = result + ' ';
	result = result + "<option value='ASP.NetAJAX'>ASP.NetAJAX</option>";
	result = result + ' ';
	result = result + "<option value='ASP.NetandMVCFramework'>ASP.NetandMVCFramework</option>";
	result = result + ' ';
	result = result + "<option value='ASP.NETFramework'>ASP.NETFramework</option>";
	result = result + ' ';
	result = result + "<option value='Asp.NetMVC'>Asp.NetMVC</option>";
	result = result + ' ';
	result = result + "<option value='ASP.NETMVCC#'>ASP.NETMVCC#</option>";
	result = result + ' ';
	result = result + "<option value='ASP.NETMVCRAZOR'>ASP.NETMVCRAZOR</option>";
	result = result + ' ';
	result = result + "<option value='AuqaDataStudio'>AuqaDataStudio</option>";
	result = result + ' ';
	result = result + "<option value='AutoIT'>AutoIT</option>";
	result = result + ' ';
	result = result + "<option value='AXI'>AXI</option>";
	result = result + ' ';
	result = result + "<option value='AxisFramework'>AxisFramework</option>";
	result = result + ' ';
	result = result + "<option value='BDD'>BDD</option>";
	result = result + ' ';
	result = result + "<option value='Bitbucket'>Bitbucket</option>";
	result = result + ' ';
	result = result + "<option value='BlueJIDE'>BlueJIDE</option>";
	result = result + ' ';
	result = result + "<option value='BMCRemedy'>BMCRemedy</option>";
	result = result + ' ';
	result = result + "<option value='Bootstrap'>Bootstrap</option>";
	result = result + ' ';
	result = result + "<option value='BPT'>BPT</option>";
	result = result + ' ';
	result = result + "<option value='BugZero'>BugZero</option>";
	result = result + ' ';
	result = result + "<option value='Bugzilla'>Bugzilla</option>";
	result = result + ' ';
	result = result + "<option value='BusinessExplorer'>BusinessExplorer</option>";
	result = result + ' ';
	result = result + "<option value='C#.NET'>C#.NET</option>";
	result = result + ' ';
	result = result + "<option value='C#.NET.VB.NET'>C#.NET.VB.NET</option>";
	result = result + ' ';
	result = result + "<option value='C/C++'>C/C++</option>";
	result = result + ' ';
	result = result + "<option value='Cache'>Cache</option>";
	result = result + ' ';
	result = result + "<option value='CadenceIES'>CadenceIES</option>";
	result = result + ' ';
	result = result + "<option value='CadenceVirtuoso'>CadenceVirtuoso</option>";
	result = result + ' ';
	result = result + "<option value='Calibre'>Calibre</option>";
	result = result + ' ';
	result = result + "<option value='CAWilyIntroscope'>CAWilyIntroscope</option>";
	result = result + ' ';
	result = result + "<option value='ChromewebInspector'>ChromewebInspector</option>";
	result = result + ' ';
	result = result + "<option value='Citrix'>Citrix</option>";
	result = result + ' ';
	result = result + "<option value='CL/400'>CL/400</option>";
	result = result + ' ';
	result = result + "<option value='Clearcase'>Clearcase</option>";
	result = result + ' ';
	result = result + "<option value='Clearcasetool'>Clearcasetool</option>";
	result = result + ' ';
	result = result + "<option value='ClickScheduleforFieldServiceManagement.'>ClickScheduleforFieldServiceManagement.</option>";
	result = result + ' ';
	result = result + "<option value='clientserver'>clientserver</option>";
	result = result + ' ';
	result = result + "<option value='CLLE'>CLLE</option>";
	result = result + ' ';
	result = result + "<option value='CloudFoundry'>CloudFoundry</option>";
	result = result + ' ';
	result = result + "<option value='Controller'>Controller</option>";
	result = result + ' ';
	result = result + "<option value='COSMOS'>COSMOS</option>";
	result = result + ' ';
	result = result + "<option value='CrystalReportsandSSRSReports'>CrystalReportsandSSRSReports</option>";
	result = result + ' ';
	result = result + "<option value='CVC'>CVC</option>";
	result = result + ' ';
	result = result + "<option value='CVS'>CVS</option>";
	result = result + ' ';
	result = result + "<option value='CXF'>CXF</option>";
	result = result + ' ';
	result = result + "<option value='D'>D</option>";
	result = result + ' ';
	result = result + "<option value='D2K'>D2K</option>";
	result = result + ' ';
	result = result + "<option value='DatabasesMSSQLServer'>DatabasesMSSQLServer</option>";
	result = result + ' ';
	result = result + "<option value='DataTransformationservices'>DataTransformationservices</option>";
	result = result + ' ';
	result = result + "<option value='DBMS'>DBMS</option>";
	result = result + ' ';
	result = result + "<option value='DefectReporting'>DefectReporting</option>";
	result = result + ' ';
	result = result + "<option value='DesignPatterns'>DesignPatterns</option>";
	result = result + ' ';
	result = result + "<option value='Designstudio'>Designstudio</option>";
	result = result + ' ';
	result = result + "<option value='DevBox'>DevBox</option>";
	result = result + ' ';
	result = result + "<option value='DevExpress'>DevExpress</option>";
	result = result + ' ';
	result = result + "<option value='DjangoJquery'>DjangoJquery</option>";
	result = result + ' ';
	result = result + "<option value='DotNet'>DotNet</option>";
	result = result + ' ';
	result = result + "<option value='Eclipse Fresher'>Eclipse Fresher</option>";
	result = result + ' ';
	result = result + "<option value='Dozer'>Dozer</option>";
	result = result + ' ';
	result = result + "<option value='Drupal'>Drupal</option>";
	result = result + ' ';
	result = result + "<option value='DynamicsCRM'>DynamicsCRM</option>";
	result = result + ' ';
	result = result + "<option value='Eclipse3.4Helileo/Gelileo '>Eclipse3.4Helileo/Gelileo </option>";
	result = result + ' ';
	result = result + "<option value='EclipseEEIDE'>EclipseEEIDE</option>";
	result = result + ' ';
	result = result + "<option value='EclipseIDE'>EclipseIDE</option>";
	result = result + ' ';
	result = result + "<option value='EclipseSDE'>EclipseSDE</option>";
	result = result + ' ';
	result = result + "<option value='EJB-MDB'>EJB-MDB</option>";
	result = result + ' ';
	result = result + "<option value='EntityFrameWork'>EntityFrameWork</option>";
	result = result + ' ';
	result = result + "<option value='ERWIN'>ERWIN</option>";
	result = result + ' ';
	result = result + "<option value='ExpressJS'>ExpressJS</option>";
	result = result + ' ';
	result = result + "<option value='Extjs'>Extjs</option>";
	result = result + ' ';
	result = result + "<option value='Factory'>Factory</option>";
	result = result + ' ';
	result = result + "<option value='FamiliarwithWINDOWSfamily'>FamiliarwithWINDOWSfamily</option>";
	result = result + ' ';
	result = result + "<option value='Fedora'>Fedora</option>";
	result = result + ' ';
	result = result + "<option value='FileZilla'>FileZilla</option>";
	result = result + ' ';
	result = result + "<option value='Firebug'>Firebug</option>";
	result = result + ' ';
	result = result + "<option value='FlexcubeRAD'>FlexcubeRAD</option>";
	result = result + ' ';
	result = result + "<option value='Fogbugz'>Fogbugz</option>";
	result = result + ' ';
	result = result + "<option value='FrontController'>FrontController</option>";
	result = result + ' ';
	result = result + "<option value='Git'>Git</option>";
	result = result + ' ';
	result = result + "<option value='GITandCVS'>GITandCVS</option>";
	result = result + ' ';
	result = result + "<option value='GitHub'>GitHub</option>";
	result = result + ' ';
	result = result + "<option value='GoogleAnalytics'>GoogleAnalytics</option>";
	result = result + ' ';
	result = result + "<option value='Greenplum'>Greenplum</option>";
	result = result + ' ';
	result = result + "<option value='Groovy'>Groovy</option>";
	result = result + ' ';
	result = result + "<option value='Hadoop/HDFS'>Hadoop/HDFS</option>";
	result = result + ' ';
	result = result + "<option value='Hive'>Hive</option>";
	result = result + ' ';
	result = result + "<option value='HPALM'>HPALM</option>";
	result = result + ' ';
	result = result + "<option value='HPSC'>HPSC</option>";
	result = result + ' ';
	result = result + "<option value='HPSiteScope'>HPSiteScope</option>";
	result = result + ' ';
	result = result + "<option value='Hybrid'>Hybrid</option>";
	result = result + ' ';
	result = result + "<option value='IBMHTTPServer'>IBMHTTPServer</option>";
	result = result + ' ';
	result = result + "<option value='IBMRPT'>IBMRPT</option>";
	result = result + ' ';
	result = result + "<option value='IBMSynergy'>IBMSynergy</option>";
	result = result + ' ';
	result = result + "<option value='InfrastructureSVN'>InfrastructureSVN</option>";
	result = result + ' ';
	result = result + "<option value='Insurance&WebApplication'>Insurance&WebApplication</option>";
	result = result + ' ';
	result = result + "<option value='IntegrationTesting'>IntegrationTesting</option>";
	result = result + ' ';
	result = result + "<option value='InternetBrowsing'>InternetBrowsing</option>";
	result = result + ' ';
	result = result + "<option value='ionicframework'>ionicframework</option>";
	result = result + ' ';
	result = result + "<option value='iReport'>iReport</option>";
	result = result + ' ';
	result = result + "<option value='JavaServlet'>JavaServlet</option>";
	result = result + ' ';
	result = result + "<option value='JAX-B'>JAX-B</option>";
	result = result + ' ';
	result = result + "<option value='JAX-WS WebServices'>JAX-WS WebServices</option>";
	result = result + ' ';
	result = result + "<option value='Jenkins'>Jenkins</option>";
	result = result + ' ';
	result = result + "<option value='JenkinsApacheJavaTomcat'>JenkinsApacheJavaTomcat</option>";
	result = result + ' ';
	result = result + "<option value='Jmeter'>Jmeter</option>";
	result = result + ' ';
	result = result + "<option value='JqueryandAJAX'>JqueryandAJAX</option>";
	result = result + ' ';
	result = result + "<option value='JQueryandCSS'>JQueryandCSS</option>";
	result = result + ' ';
	result = result + "<option value='Jqwidgets&DevExpress'>Jqwidgets&DevExpress</option>";
	result = result + ' ';
	result = result + "<option value='jQWidjetsandJQuery'>jQWidjetsandJQuery</option>";
	result = result + ' ';
	result = result + "<option value='json'>json</option>";
	result = result + ' ';
	result = result + "<option value='KendoMVC'>KendoMVC</option>";
	result = result + ' ';
	result = result + "<option value='KendoWebUIforASP.NETMVC4'>KendoWebUIforASP.NETMVC4</option>";
	result = result + ' ';
	result = result + "<option value='knowledgeinangularjs'>knowledgeinangularjs</option>";
	result = result + ' ';
	result = result + "<option value='Komodo'>Komodo</option>";
	result = result + ' ';
	result = result + "<option value='Languages5'>Languages5</option>";
	result = result + ' ';
	result = result + "<option value='Linq&ADO.NET'>Linq&ADO.NET</option>";
	result = result + ' ';
	result = result + "<option value='LinqtoSqlJavaScript'>LinqtoSqlJavaScript</option>";
	result = result + ' ';
	result = result + "<option value='LoadRunner'>LoadRunner</option>";
	result = result + ' ';
	result = result + "<option value='LTSpice'>LTSpice</option>";
	result = result + ' ';
	result = result + "<option value='Lumira'>Lumira</option>";
	result = result + ' ';
	result = result + "<option value='MainframeDB2'>MainframeDB2</option>";
	result = result + ' ';
	result = result + "<option value='MAC'>MAC</option>";
	result = result + ' ';
	result = result + "<option value='Makefile'>Makefile</option>";
	result = result + ' ';
	result = result + "<option value='MavenHudson'>MavenHudson</option>";
	result = result + ' ';
	result = result + "<option value='MCSD'>MCSD</option>";
	result = result + ' ';
	result = result + "<option value='MDBandJAXB'>MDBandJAXB</option>";
	result = result + ' ';
	result = result + "<option value='Media'>Media</option>";
	result = result + ' ';
	result = result + "<option value='MicrosoftADMAD'>MicrosoftADMAD</option>";
	result = result + ' ';
	result = result + "<option value='MicrosoftSQLServer'>MicrosoftSQLServer</option>";
	result = result + ' ';
	result = result + "<option value='MicrosoftSQLServer2008/2012andMicrosoftAccess'>MicrosoftSQLServer2008/2012andMicrosoftAccess</option>";
	result = result + ' ';
	result = result + "<option value='MicrosoftSQLServerManagementStudio'>MicrosoftSQLServerManagementStudio</option>";
	result = result + ' ';
	result = result + "<option value='MicrosoftTestManagement(MTM)'>MicrosoftTestManagement(MTM)</option>";
	result = result + ' ';
	result = result + "<option value='MicrosoftVisualStudio.NET'>MicrosoftVisualStudio.NET</option>";
	result = result + ' ';
	result = result + "<option value='MicroStrategyDesktop'>MicroStrategyDesktop</option>";
	result = result + ' ';
	result = result + "<option value='Modelsim'>Modelsim</option>";
	result = result + ' ';
	result = result + "<option value='Monodroid'>Monodroid</option>";
	result = result + ' ';
	result = result + "<option value='MQJExplorer'>MQJExplorer</option>";
	result = result + ' ';
	result = result + "<option value='MSBI(SSRS)'>MSBI(SSRS)</option>";
	result = result + ' ';
	result = result + "<option value='MS-Outlook'>MS-Outlook</option>";
	result = result + ' ';
	result = result + "<option value='MSSDKAdd-in '>MSSDKAdd-in </option>";
	result = result + ' ';
	result = result + "<option value='MSSQL'>MSSQL</option>";
	result = result + ' ';
	result = result + "<option value='MSSQLSERVER'>MSSQLSERVER</option>";
	result = result + ' ';
	result = result + "<option value='MSTFS2015'>MSTFS2015</option>";
	result = result + ' ';
	result = result + "<option value='MSVision'>MSVision</option>";
	result = result + ' ';
	result = result + "<option value='MSVisualStudio'>MSVisualStudio</option>";
	result = result + ' ';
	result = result + "<option value='Multisim'>Multisim</option>";
	result = result + ' ';
	result = result + "<option value='MVC'>MVC</option>";
	result = result + ' ';
	result = result + "<option value='MySQL&Sybase'>MySQL&Sybase</option>";
	result = result + ' ';
	result = result + "<option value='NetBeansIDE'>NetBeansIDE</option>";
	result = result + ' ';
	result = result + "<option value='Networking'>Networking</option>";
	result = result + ' ';
	result = result + "<option value='NodeJs'>NodeJs</option>";
	result = result + ' ';
	result = result + "<option value='Open-Office'>Open-Office</option>";
	result = result + ' ';
	result = result + "<option value='OCP'>OCP</option>";
	result = result + ' ';
	result = result + "<option value='OperatingSystemsWindows2000/XP/Vista/7/8'>OperatingSystemsWindows2000/XP/Vista/7/8</option>";
	result = result + ' ';
	result = result + "<option value='OracleApplicationServer10g'>OracleApplicationServer10g</option>";
	result = result + ' ';
	result = result + "<option value='OracleDatabase10g/11i'>OracleDatabase10g/11i</option>";
	result = result + ' ';
	result = result + "<option value='OracleDesigner6i'>OracleDesigner6i</option>";
	result = result + ' ';
	result = result + "<option value='OracleFLEXCUBE'>OracleFLEXCUBE</option>";
	result = result + ' ';
	result = result + "<option value='OracleformBuilder10g'>OracleformBuilder10g</option>";
	result = result + ' ';
	result = result + "<option value='OracleFormsDeveloper'>OracleFormsDeveloper</option>";
	result = result + ' ';
	result = result + "<option value='OracleJDeveloper'>OracleJDeveloper</option>";
	result = result + ' ';
	result = result + "<option value='OracleReportBuilder10g'>OracleReportBuilder10g</option>";
	result = result + ' ';
	result = result + "<option value='OracleReportsDeveloper'>OracleReportsDeveloper</option>";
	result = result + ' ';
	result = result + "<option value='OracleSQLDeveloper'>OracleSQLDeveloper</option>";
	result = result + ' ';
	result = result + "<option value='OTN'>OTN</option>";
	result = result + ' ';
	result = result + "<option value='PAAS(PlatformASService)WebServices'>PAAS(PlatformASService)WebServices</option>";
	result = result + ' ';
	result = result + "<option value='Patentsearching'>Patentsearching</option>";
	result = result + ' ';
	result = result + "<option value='pdfboxetc'>pdfboxetc</option>";
	result = result + ' ';
	result = result + "<option value='PeopleSoftSAPBOXI'>PeopleSoftSAPBOXI</option>";
	result = result + ' ';
	result = result + "<option value='Performancecenter'>Performancecenter</option>";
	result = result + ' ';
	result = result + "<option value='PerformanceTesting'>PerformanceTesting</option>";
	result = result + ' ';
	result = result + "<option value='PerlScripting'>PerlScripting</option>";
	result = result + ' ';
	result = result + "<option value='PES8.0'>PES8.0</option>";
	result = result + ' ';
	result = result + "<option value='PGADMIN'>PGADMIN</option>";
	result = result + ' ';
	result = result + "<option value='PGDCA'>PGDCA</option>";
	result = result + ' ';
	result = result + "<option value='PL/SQLinDBMS'>PL/SQLinDBMS</option>";
	result = result + ' ';
	result = result + "<option value='PlasticsSCMtool'>PlasticsSCMtool</option>";
	result = result + ' ';
	result = result + "<option value='PLSQLDeveloper'>PLSQLDeveloper</option>";
	result = result + ' ';
	result = result + "<option value='POM'>POM</option>";
	result = result + ' ';
	result = result + "<option value='PTP/OAM'>PTP/OAM</option>";
	result = result + ' ';
	result = result + "<option value='PuTTY'>PuTTY</option>";
	result = result + ' ';
	result = result + "<option value='pycharm'>pycharm</option>";
	result = result + ' ';
	result = result + "<option value='QCALM'>QCALM</option>";
	result = result + ' ';
	result = result + "<option value='QTP11/UFT'>QTP11/UFT</option>";
	result = result + ' ';
	result = result + "<option value='QualityCenter(HPQC)'>QualityCenter(HPQC)</option>";
	result = result + ' ';
	result = result + "<option value='QualityCenterALM'>QualityCenterALM</option>";
	result = result + ' ';
	result = result + "<option value='RAD'>RAD</option>";
	result = result + ' ';
	result = result + "<option value='Raptortool'>Raptortool</option>";
	result = result + ' ';
	result = result + "<option value='RationalRose'>RationalRose</option>";
	result = result + ' ';
	result = result + "<option value='Razor'>Razor</option>";
	result = result + ' ';
	result = result + "<option value='RDLCReports'>RDLCReports</option>";
	result = result + ' ';
	result = result + "<option value='RemedyBMCSoftware'>RemedyBMCSoftware</option>";
	result = result + ' ';
	result = result + "<option value='RestFulClient'>RestFulClient</option>";
	result = result + ' ';
	result = result + "<option value='RESTfulServices'>RESTfulServices</option>";
	result = result + ' ';
	result = result + "<option value='RestfulWebservices'>RestfulWebservices</option>";
	result = result + ' ';
	result = result + "<option value='RML'>RML</option>";
	result = result + ' ';
	result = result + "<option value='RPG/400'>RPG/400</option>";
	result = result + ' ';
	result = result + "<option value='RPGLE'>RPGLE</option>";
	result = result + ' ';
	result = result + "<option value='RTLCompiler'>RTLCompiler</option>";
	result = result + ' ';
	result = result + "<option value='Ruby'>Ruby</option>";
	result = result + ' ';
	result = result + "<option value='SAML'>SAML</option>";
	result = result + ' ';
	result = result + "<option value='SAPB1DI-APIVer'>SAPB1DI-APIVer</option>";
	result = result + ' ';
	result = result + "<option value='SAPB1DI-ServerVer'>SAPB1DI-ServerVer</option>";
	result = result + ' ';
	result = result + "<option value='SAPBI&ECC'>SAPBI&ECC</option>";
	result = result + ' ';
	result = result + "<option value='SAPBO'>SAPBO</option>";
	result = result + ' ';
	result = result + "<option value='SAPBusinessObjects(SAPBO)'>SAPBusinessObjects(SAPBO)</option>";
	result = result + ' ';
	result = result + "<option value='Scrum'>Scrum</option>";
	result = result + ' ';
	result = result + "<option value='SDLC'>SDLC</option>";
	result = result + ' ';
	result = result + "<option value='SDMethodologiesAgile'>SDMethodologiesAgile</option>";
	result = result + ' ';
	result = result + "<option value='SeleniumIDE'>SeleniumIDE</option>";
	result = result + ' ';
	result = result + "<option value='SeleniumRC'>SeleniumRC</option>";
	result = result + ' ';
	result = result + "<option value='ServerArchitectureMonitoring'>ServerArchitectureMonitoring</option>";
	result = result + ' ';
	result = result + "<option value='ServiceLocator'>ServiceLocator</option>";
	result = result + ' ';
	result = result + "<option value='Servlets'>Servlets</option>";
	result = result + ' ';
	result = result + "<option value='SharePointdevelopment'>SharePointdevelopment</option>";
	result = result + ' ';
	result = result + "<option value='ShellProgramming'>ShellProgramming</option>";
	result = result + ' ';
	result = result + "<option value='ShellSubversion'>ShellSubversion</option>";
	result = result + ' ';
	result = result + "<option value='SiebelAnalytics'>SiebelAnalytics</option>";
	result = result + ' ';
	result = result + "<option value='SiebelAnswers'>SiebelAnswers</option>";
	result = result + ' ';
	result = result + "<option value='SiebelCRM'>SiebelCRM</option>";
	result = result + ' ';
	result = result + "<option value='Singleton'>Singleton</option>";
	result = result + ' ';
	result = result + "<option value='SISSandInformatica'>SISSandInformatica</option>";
	result = result + ' ';
	result = result + "<option value='Sitecore'>Sitecore</option>";
	result = result + ' ';
	result = result + "<option value='SlimFramework'>SlimFramework</option>";
	result = result + ' ';
	result = result + "<option value='SOA'>SOA</option>";
	result = result + ' ';
	result = result + "<option value='SOAPServicesandRestfulServices'>SOAPServicesandRestfulServices</option>";
	result = result + ' ';
	result = result + "<option value='SoapUI'>SoapUI</option>";
	result = result + ' ';
	result = result + "<option value='SoftwareandToolsMSVision'>SoftwareandToolsMSVision</option>";
	result = result + ' ';
	result = result + "<option value='SoftwareEngineering'>SoftwareEngineering</option>";
	result = result + ' ';
	result = result + "<option value='Sonar'>Sonar</option>";
	result = result + ' ';
	result = result + "<option value='SourceForge'>SourceForge</option>";
	result = result + ' ';
	result = result + "<option value='SourceJammer'>SourceJammer</option>";
	result = result + ' ';
	result = result + "<option value='Spring Security'>Spring Security</option>";
	result = result + ' ';
	result = result + "<option value='Spring Boot'>Spring Boot</option>";
	result = result + ' ';
	result = result + "<option value='Spring Integration'>Spring Integration</option>";
	result = result + ' ';
	result = result + "<option value='Spring Batch'>Spring Batch</option>";
	result = result + ' ';
	result = result + "<option value='Spring Mvc'>Spring Mvc</option>";
	result = result + ' ';
	result = result + "<option value='Helical Springs'>Helical Springs</option>";
	result = result + ' ';
	result = result + "<option value='Spring Framework'>Spring Framework</option>";
	result = result + ' ';
	result = result + "<option value='Spring Aop'>Spring Aop</option>";
	result = result + ' ';
	result = result + "<option value='Spring Core'>Spring Core</option>";
	result = result + ' ';
	result = result + "<option value='Spring Ioc'>Spring Ioc</option>";
	result = result + ' ';
	result = result + "<option value='SQL'>SQL</option>";
	result = result + ' ';
	result = result + "<option value='SQL/C-tree'>SQL/C-tree</option>";
	result = result + ' ';
	result = result + "<option value='SQLDeveloper'>SQLDeveloper</option>";
	result = result + ' ';
	result = result + "<option value='XMLWebServices'>XMLWebServices</option>";
	result = result + ' ';
	result = result + "<option value='Sqllite'>Sqllite</option>";
	result = result + ' ';
	result = result + "<option value='SQLLoader'>SQLLoader</option>";
	result = result + ' ';
	result = result + "<option value='SQLPlus'>SQLPlus</option>";
	result = result + ' ';
	result = result + "<option value='SQLplusVisualStudio'>SQLplusVisualStudio</option>";
	result = result + ' ';
	result = result + "<option value='SQLRPGLE'>SQLRPGLE</option>";
	result = result + ' ';
	result = result + "<option value='SQLServer'>SQLServer</option>";
	result = result + ' ';
	result = result + "<option value='SQLServerDenali'>SQLServerDenali</option>";
	result = result + ' ';
	result = result + "<option value='SQLServermanagementstudio'>SQLServermanagementstudio</option>";
	result = result + ' ';
	result = result + "<option value='SQLServerReportingServices'>SQLServerReportingServices</option>";
	result = result + ' ';
	result = result + "<option value='SRDF/S'>SRDF/S</option>";
	result = result + ' ';
	result = result + "<option value='SSH'>SSH</option>";
	result = result + ' ';
	result = result + "<option value='SSHfiletransfer'>SSHfiletransfer</option>";
	result = result + ' ';
	result = result + "<option value='SSRSReport'>SSRSReport</option>";
	result = result + ' ';
	result = result + "<option value='StarTeam'>StarTeam</option>";
	result = result + ' ';
	result = result + "<option value='Strategy'>Strategy</option>";
	result = result + ' ';
	result = result + "<option value='STS'>STS</option>";
	result = result + ' ';
	result = result + "<option value='SunSolaris'>SunSolaris</option>";
	result = result + ' ';
	result = result + "<option value='SVN'>SVN</option>";
	result = result + ' ';
	result = result + "<option value='SunSolarisandWindows'>SunSolarisandWindows</option>";
	result = result + ' ';
	result = result + "<option value='SVNServer'>SVNServer</option>";
	result = result + ' ';
	result = result + "<option value='SYMCLI'>SYMCLI</option>";
	result = result + ' ';
	result = result + "<option value='SymmetrixDMX'>SymmetrixDMX</option>";
	result = result + ' ';
	result = result + "<option value='Taleo'>Taleo</option>";
	result = result + ' ';
	result = result + "<option value='TeamCity'>TeamCity</option>";
	result = result + ' ';
	result = result + "<option value='TeamFounderServer(TFS)'>TeamFounderServer(TFS)</option>";
	result = result + ' ';
	result = result + "<option value='TelerikControls'>TelerikControls</option>";
	result = result + ' ';
	result = result + "<option value='TelerikRADControls'>TelerikRADControls</option>";
	result = result + ' ';
	result = result + "<option value='Testdata'>Testdata</option>";
	result = result + ' ';
	result = result + "<option value='TestDesign'>TestDesign</option>";
	result = result + ' ';
	result = result + "<option value='TestEnvironment'>TestEnvironment</option>";
	result = result + ' ';
	result = result + "<option value='TestExecution'>TestExecution</option>";
	result = result + ' ';
	result = result + "<option value='TestPlan'>TestPlan</option>";
	result = result + ' ';
	result = result + "<option value='TestProcessToolsJira'>TestProcessToolsJira</option>";
	result = result + ' ';
	result = result + "<option value='Testscripts'>Testscripts</option>";
	result = result + ' ';
	result = result + "<option value='TestStatusReports'>TestStatusReports</option>";
	result = result + ' ';
	result = result + "<option value='TFS'>TFS</option>";
	result = result + ' ';
	result = result + "<option value='TFSandSVN'>TFSandSVN</option>";
	result = result + ' ';
	result = result + "<option value='TivoliWorkloadScheduler'>TivoliWorkloadScheduler</option>";
	result = result + ' ';
	result = result + "<option value='Tomcat'>Tomcat</option>";
	result = result + ' ';
	result = result + "<option value='TomcatandJBossSOAServer'>TomcatandJBossSOAServer</option>";

	result = result + ' ';
	result = result + "<option value='Transact-SQL(T-SQL)'>Transact-SQL(T-SQL)</option>";
	result = result + ' ';
	result = result + "<option value='TwitterBootstrap'>TwitterBootstrap</option>";
	result = result + ' ';
	result = result + "<option value='TypeScript'>TypeScript</option>";
	result = result + ' ';
	result = result + "<option value='Ubuntu'>Ubuntu</option>";
	result = result + ' ';
	result = result + "<option value='UFT'>UFT</option>";
	result = result + ' ';
	result = result + "<option value='UnitTesting'>UnitTesting</option>";
	result = result + ' ';
	result = result + "<option value='Unix/Linux'>Unix/Linux</option>";
	result = result + ' ';
	result = result + "<option value='UNIXShellScripting'>UNIXShellScripting</option>";
	result = result + ' ';
	result = result + "<option value='USB'>USB</option>";
	result = result + ' ';
	result = result + "<option value='VB'>VB</option>";
	result = result + ' ';
	result = result + "<option value='VCS'>VCS</option>";
	result = result + ' ';
	result = result + "<option value='VHDL'>VHDL</option>";
	result = result + ' ';
	result = result + "<option value='Vista'>Vista</option>";
	result = result + ' ';
	result = result + "<option value='VisualSourceSafe'>VisualSourceSafe</option>";
	result = result + ' ';
	result = result + "<option value='VisualStudio'>VisualStudio</option>";
	result = result + ' ';
	result = result + "<option value='VisualStudio.NET'>VisualStudio.NET</option>";
	result = result + ' ';
	result = result + "<option value='VMAX'>VMAX</option>";
	result = result + ' ';
	result = result + "<option value='VNX&Clariionseries'>VNX&Clariionseries</option>";
	result = result + ' ';
	result = result + "<option value='VS.Net2008'>VS.Net2008</option>";
	result = result + ' ';
	result = result + "<option value='VSS'>VSS</option>";
	result = result + ' ';
	result = result + "<option value='WAS8.x'>WAS8.x</option>";
	result = result + ' ';
	result = result + "<option value='Waterfall&VModel'>Waterfall&VModel</option>";
	result = result + ' ';
	result = result + "<option value='WCFandWebServices'>WCFandWebServices</option>";
	result = result + ' ';
	result = result + "<option value='WCFServices'>WCFServices</option>";
	result = result + ' ';
	result = result + "<option value='WEBAPI'>WEBAPI</option>";
	result = result + ' ';
	result = result + "<option value='WebApplication'>WebApplication</option>";
	result = result + ' ';
	result = result + "<option value='webbasedandmobileapplications'>webbasedandmobileapplications</option>";
	result = result + ' ';
	result = result + "<option value='WebDriver'>WebDriver</option>";
	result = result + ' ';
	result = result + "<option value='Weblogicconsole'>Weblogicconsole</option>";
	result = result + ' ';
	result = result + "<option value='WeblogicJava'>WeblogicJava</option>";
	result = result + ' ';
	result = result + "<option value='WebSCME'>WebSCME</option>";
	result = result + ' ';
	result = result + "<option value='WebService'>WebService</option>";
	result = result + ' ';
	result = result + "<option value='WebServices(SOAP)'>WebServices(SOAP)</option>";
	result = result + ' ';
	result = result + "<option value='WhizbilPBN'>WhizbilPBN</option>";
	result = result + ' ';
	result = result + "<option value='Wicket'>Wicket</option>";
	result = result + ' ';
	result = result + "<option value='Win2000Server'>Win2000Server</option>";
	result = result + ' ';
	result = result + "<option value='Windows'>Windows</option>";
	result = result + ' ';
	result = result + "<option value='WinNT'>WinNT</option>";
	result = result + ' ';
	result = result + "<option value='WinServer'>WinServer</option>";
	result = result + ' ';
	result = result + "<option value='WinXP'>WinXP</option>";
	result = result + ' ';
	result = result + "<option value='XFire'>XFire</option>";
	result = result + ' ';
	result = result + "<option value='Xilinx'>Xilinx</option>";
	result = result + ' ';
	result = result + "<option value='XMLandRDLCReports'>XMLandRDLCReports</option>";
	result = result + ' ';
	result = result + "<option value='XMLPL/SQL'>XMLPL/SQL</option>";
	result = result + ' ';
	result = result + "<option value='XMLRAD'>XMLRAD</option>";
	result = result + ' ';
	result = result + "<option value='XSD'>XSD</option>";
	result = result + ' ';
	result = result + "<option value='XP'>XP</option>";
	result = result + ' ';
	result = result + "<option value='XSLT'>XSLT</option>";
	result = result + ' ';
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
	    	 var sourceleadUrl ='http://localhost:8080/app';
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
	var sourceleadUrl ='http://localhost:8080/app';
	var reqCoolingOffPeriod = document.getElementById("reqCoolingOffPeriod").value;
	var orgLocationId ='971';

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
	 
	 var sourceleadUrl ='http://localhost:8080/app';
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
      var sourceleadUrl ='http://localhost:8080/app';
      
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
			    		location.replace(sourceleadUrl+"/dashboard/971");
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
	 var sourceleadUrl ='http://localhost:8080/app';
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
	 var sourceleadUrl ='http://localhost:8080/app';
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
	var sourceleadUrl ='http://localhost:8080/app';
	var requirementId = $("#requirementId").val();
	var orgLocationId ='971';
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
    	 url: 'http://localhost:8080/app'+"/countryAutoComplete?=",
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
	    	 url: 'http://localhost:8080/app'+"/getStatesByCountry?=&countryName="+countryName, 
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
	    	 url: 'http://localhost:8080/app'+"/getCitiesByStateAndCountry?=&countryName="+countryName+"&stateName="+stateName, 
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
		var sourceleadUrl = 'http://localhost:8080/app';
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
	 	                     
	 						    internalOutput = internalOutput+"<option value='ABAP'>ABAP</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='AbInitio'>AbInitio</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Active Directory'>Active Directory</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='ActiveX'>ActiveX</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='ADO'>ADO</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Adobe Illustrator'>Adobe Illustrator</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Adobe Pagemaker'>Adobe Pagemaker</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Adobe Photoshop'>Adobe Photoshop</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='AIX'>AIX</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='AJAX'>AJAX</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='ALE'>ALE</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Android Development'>Android Development</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Ansys'>Ansys</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Ant'>Ant</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Apache Commons'>Apache Commons</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Apache Tomcat'>Apache Tomcat</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Apache Web Server'>Apache Web Server</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='ArchiCAD'>ArchiCAD</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='AS 400'>AS 400</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='ASIC'>ASIC</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='ASP'>ASP</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='ASP.NET'>ASP.NET</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value=' Assembly Language'> Assembly Language</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='ATL'>ATL</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Auto CAD'>Auto CAD</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Automation Testing'>Automation Testing</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Autosys'>Autosys</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='AWT'>AWT</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='BAAN'>BAAN</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Backend'>Backend</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Back-office Operations'>Back-office Operations</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='BASIC'>BASIC</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Blackberry'>Blackberry</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Bluetooth'>Bluetooth</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='BPCS'>BPCS</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='BPEL'>BPEL</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='BREW'>BREW</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Business Objects'>Business Objects</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='C'>C</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='C#'>C#</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='C++'>C++</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='CAD/CAM'>CAD/CAM</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Call Center '>Call Center </option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Calypso'>Calypso</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='CATIA'>CATIA</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='CCIE'>CCIE</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='CDMA'>CDMA</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Chordiant'>Chordiant</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='CICS'>CICS</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='CISCO'>CISCO</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='CLDC'>CLDC</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Clipper'>Clipper</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='COBOL'>COBOL</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Cognos'>Cognos</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='ColdFusion'>ColdFusion</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='COM/COM+/DCOM '>COM/COM+/DCOM </option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='CORBA'>CORBA</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='CoreJava'>CoreJava</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Corel Draw '>Corel Draw </option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='CRM'>CRM</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Crystal Report '>Crystal Report </option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Csharp'>Csharp</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='CSS'>CSS</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Data Analysis '>Data Analysis </option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Data Structures '>Data Structures </option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Database'>Database</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Databse Administration '>Databse Administration </option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Datastage'>Datastage</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Datawarehousing '>Datawarehousing </option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='DB2'>DB2</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Db4o'>Db4o</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='DBase'>DBase</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='DCA'>DCA</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='DCOM'>DCOM</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Delphi'>Delphi</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Derby'>Derby</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Developer/D2K'>Developer/D2K</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='DHCP'>DHCP</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='DHTML'>DHTML</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Distribution'>Distribution</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='DNS'>DNS</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Documentation'>Documentation</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Documentum'>Documentum</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='DOS'>DOS</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Downstream'>Downstream</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Dreamweaver'>Dreamweaver</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='DSP'>DSP</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='DTP'>DTP</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Eclipse'>Eclipse</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Editing'>Editing</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='EJB'>EJB</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Embedded C'>Embedded C</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='ERP'>ERP</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Ethernet'>Ethernet</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Excel'>Excel</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Expeditor'>Expeditor</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='FACT'>FACT</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='FileNet'>FileNet</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Finacle'>Finacle</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Finone'>Finone</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Firewall'>Firewall</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='FTP'>FTP</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Fireworks'>Fireworks</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Flash'>Flash</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='FlashLite'>FlashLite</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Flex'>Flex</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Flexcube'>Flexcube</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Focus'>Focus</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='ForTran'>ForTran</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='FoxPro'>FoxPro</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='FPGA'>FPGA</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='FreeBSD'>FreeBSD</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='GLOSS'>GLOSS</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='GSM'>GSM</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Hibernate'>Hibernate</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='HP UNIX '>HP UNIX </option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='HTML'>HTML</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Humming Bird'>Humming Bird</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Hyperion '>Hyperion </option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='iBatis'>iBatis</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Ideas'>Ideas</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='IIS'>IIS</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='ImageReady'>ImageReady</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Impromptu'>Impromptu</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='IMS'>IMS</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Informatica'>Informatica</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Informix'>Informix</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Ingres'>Ingres</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Installshield'>Installshield</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='ISDN'>ISDN</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='ITIL'>ITIL</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='J++'>J++</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='J2EE'>J2EE</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='J2ME'>J2ME</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='J2SE'>J2SE</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Java'>Java</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Java2D'>Java2D</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='JavaCard'>JavaCard</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='JavaFX'>JavaFX</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='JavaScript'>JavaScript</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='JavaSE'>JavaSE</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='JBoss'>JBoss</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='JBoss Seam '>JBoss Seam </option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='JCL'>JCL</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='JDBC'>JDBC</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='JDOM'>JDOM</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='JFace'>JFace</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Jini'>Jini</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='JIRA'>JIRA</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='JMock'>JMock</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='JMS'>JMS</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='JNI'>JNI</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='JPA'>JPA</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Jquery'>Jquery</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='JSE'>JSE</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='JSF'>JSF</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='JSP'>JSP</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='JUnit'>JUnit</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Kickboxing'>Kickboxing</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='KSH'>KSH</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='LAN'>LAN</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='LINQ'>LINQ</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Linux'>Linux</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Load Runner'>Load Runner</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Log4j'>Log4j</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Lotus Notes'>Lotus Notes</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Mac OS'>Mac OS</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Mainframe'>Mainframe</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Manual Testing'>Manual Testing</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='MATLAB'>MATLAB</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Maven'>Maven</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Maximo'>Maximo</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Maya'>Maya</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='MCP'>MCP</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='MCSA'>MCSA</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='MCSE'>MCSE</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='MFC'>MFC</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Microsoft Access'>Microsoft Access</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Microsoft Excel'>Microsoft Excel</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Microsoft Exchange'>Microsoft Exchange</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Microsoft Office'>Microsoft Office</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='MicroStation'>MicroStation</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='MIDP'>MIDP</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='MIS Reports'>MIS Reports</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Motif'>Motif</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='MS DOS'>MS DOS</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='MS Project'>MS Project</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='MS SQL Server'>MS SQL Server</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='MS Visio'>MS Visio</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='MSCIT'>MSCIT</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Multimedia'>Multimedia</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Multithreading'>Multithreading</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Murex'>Murex</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='MVS'>MVS</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='MySQL'>MySQL</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Natural Adabas'>Natural Adabas</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='NetWeaver'>NetWeaver</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='sourceleading'>sourceleading</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Nortel'>Nortel</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Novell'>Novell</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='ODBC'>ODBC</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='OOPS'>OOPS</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='OpenGL ES'>OpenGL ES</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Operating Systems'>Operating Systems</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Oracle'>Oracle</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Oracle Apps'>Oracle Apps</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Oracle WareHouse Builder'>Oracle WareHouse Builder</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='ORCAD'>ORCAD</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='OS/2'>OS/2</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='PASCAL'>PASCAL</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='PeopleSoft'>PeopleSoft</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Perl'>Perl</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Photoshop'>Photoshop</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='PHP'>PHP</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='PL/1'>PL/1</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='PL/SQL'>PL/SQL</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='PLC/Microcontrollers/Microprocessors'>PLC/Microcontrollers/Microprocessors</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='PostgreSQL'>PostgreSQL</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='PowerBuilder'>PowerBuilder</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='PowerPlay'>PowerPlay</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Primavera'>Primavera</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Pro*C'>Pro*C</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='PRO-E'>PRO-E</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Programming'>Programming</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Progress 4GL'>Progress 4GL</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='PVCS'>PVCS</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Python'>Python</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='QBasic'>QBasic</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='QTP'>QTP</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Quality Assurance/QA'>Quality Assurance/QA</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='RDO'>RDO</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='RedHat/Linux'>RedHat/Linux</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Remedy'>Remedy</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Remoting'>Remoting</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='REXX'>REXX</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='RichFaces'>RichFaces</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='RIM'>RIM</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='RMI'>RMI</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Routing'>Routing</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='RTOS'>RTOS</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='S60'>S60</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SAP'>SAP</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SAP ABAP/IDOCS'>SAP ABAP/IDOCS</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SAP Basis'>SAP Basis</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SAP BW/BI'>SAP BW/BI</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SAP CRM'>SAP CRM</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SAP EP'>SAP EP</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SAP FICO/COPA'>SAP FICO/COPA</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SAP HR'>SAP HR</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SAP IS-GAS/OIL'>SAP IS-GAS/OIL</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SAP IS-Retail'>SAP IS-Retail</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SAP IS-Utilities'>SAP IS-Utilities</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SAP MDM'>SAP MDM</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SAP MM'>SAP MM</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SAP PM'>SAP PM</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SAP PP'>SAP PP</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SAP Practice'>SAP Practice</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SAP PS'>SAP PS</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SAP QM'>SAP QM</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SAP SCM'>SAP SCM</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SAP SD'>SAP SD</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SAP Security'>SAP Security</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SAP SEM'>SAP SEM</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SAP SRM'>SAP SRM</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SAP WMS'>SAP WMS</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SAP XI'>SAP XI</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SAS'>SAS</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Savvion'>Savvion</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SCADA'>SCADA</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Seam'>Seam</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Sharepoint Server/MOSS'>Sharepoint Server/MOSS</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Shell Scripting '>Shell Scripting </option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Siebel'>Siebel</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Silverlight'>Silverlight</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SMARTY'>SMARTY</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SMTP'>SMTP</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Software Testing'>Software Testing</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Solaris'>Solaris</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SoundForge'>SoundForge</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Spring'>Spring</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SPSS'>SPSS</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SQL Server'>SQL Server</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='STAAD'>STAAD</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Struts'>Struts</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SunOS'>SunOS</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Switching'>Switching</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SWT'>SWT</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Sybase'>Sybase</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Symbian'>Symbian</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Symbian C++'>Symbian C++</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SyncML'>SyncML</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Tally'>Tally</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='TCL/TK'>TCL/TK</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='TCP/IP'>TCP/IP</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='TELNET'>TELNET</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Teradata'>Teradata</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Tibco'>Tibco</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='TLM'>TLM</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='TOAD'>TOAD</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='TPF'>TPF</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='T-SQL'>T-SQL</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Turbo Pascal'>Turbo Pascal</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Tuxedo'>Tuxedo</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='UIQ'>UIQ</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='UML'>UML</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='UniGraphics'>UniGraphics</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Unix'>Unix</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Upstream'>Upstream</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='VAX/VMS'>VAX/VMS</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='VB.NET'>VB.NET</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='VBScript'>VBScript</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Verilog'>Verilog</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Vi'>Vi</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Video Editing'>Video Editing</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Vignette'>Vignette</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Vision Plus'>Vision Plus</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Visual Basic'>Visual Basic</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Visual C++'>Visual C++</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Visual Foxpro'>Visual Foxpro</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Visual Interdev'>Visual Interdev</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='VLSI'>VLSI</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='VMS'>VMS</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='VOIP'>VOIP</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='VPN'>VPN</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='VSAT'>VSAT</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='VSS/Clearcase'>VSS/Clearcase</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='VxWorks'>VxWorks</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='WAN'>WAN</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='WAP'>WAP</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='WCF'>WCF</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Web Designing'>Web Designing</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='WebLogic'>WebLogic</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='WebMethods'>WebMethods</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Website Development'>Website Development</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='WebSphere'>WebSphere</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Winform'>Winform</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Winrunner'>Winrunner</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='WML'>WML</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='WPF'>WPF</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='XDoclet'>XDoclet</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Xenix'>Xenix</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='XHTML'>XHTML</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='XML'>XML</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='XStream'>XStream</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Accounting'>Accounting</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Accounts '>Accounts </option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Accounts Management'>Accounts Management</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Acting '>Acting </option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Administration'>Administration</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Advertisement Sales'>Advertisement Sales</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Advertising'>Advertising</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Advertising Account Management '>Advertising Account Management </option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Advertising Art Direction '>Advertising Art Direction </option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Advisory'>Advisory</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Analysis'>Analysis</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Animation'>Animation</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Aquisitions '>Aquisitions </option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Architecture'>Architecture</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Art'>Art</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Art Therapy'>Art Therapy</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Astrology'>Astrology</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Authoring'>Authoring</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Baking'>Baking</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Banking'>Banking</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Banking Insurance'>Banking Insurance</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Biotechnology'>Biotechnology</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Body Art'>Body Art</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Bookbinding'>Bookbinding</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Bookkeeping'>Bookkeeping</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Business Analysis'>Business Analysis</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Buying'>Buying</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Channel Account Management'>Channel Account Management</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Chartered Accountancy'>Chartered Accountancy</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Cloth Design'>Cloth Design</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Coaching'>Coaching</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Corporate Finance'>Corporate Finance</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Cost Accounting'>Cost Accounting</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Counselling'>Counselling</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Curating'>Curating</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Customer Relations'>Customer Relations</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Dance'>Dance</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Design'>Design</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Document Administration'>Document Administration</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Education'>Education</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Electrical Distribution'>Electrical Distribution</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Email Marketing'>Email Marketing</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Entertainment'>Entertainment</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Film Editing'>Film Editing</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Finance'>Finance</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Floristry'>Floristry</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Graphic Design'>Graphic Design</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Guest Service'>Guest Service</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Hairdressing'>Hairdressing</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Headhunting'>Headhunting</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Hiring'>Hiring</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Human Resource Management'>Human Resource Management</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='HVAC'>HVAC</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Illustration'>Illustration</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Image Consulting'>Image Consulting</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Income Tax'>Income Tax</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Industrial Designing'>Industrial Designing</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Interior Design'>Interior Design</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='IT Recruitment'>IT Recruitment</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Jewellery'>Jewellery</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Journalism'>Journalism</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Key Accounts Management'>Key Accounts Management</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Knowledge Management'>Knowledge Management</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Landscape Architecture'>Landscape Architecture</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Landscape Gardening'>Landscape Gardening</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Lighting'>Lighting</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Make-up Art'>Make-up Art</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Manufacturing'>Manufacturing</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Map Making'>Map Making</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Marketing'>Marketing</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Mathematical'>Mathematical</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Merchandise'>Merchandise</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Mergers'>Mergers</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Motivating Skill'>Motivating Skill</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Music'>Music</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Negotiating Skill'>Negotiating Skill</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Nursing'>Nursing</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Office Administration'>Office Administration</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Office Management'>Office Management</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Personal Services'>Personal Services</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Planning and Organising'>Planning and Organising</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Plumbing'>Plumbing</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='PPC'>PPC</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Private Tutoring'>Private Tutoring</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Producing'>Producing</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Record Producing'>Record Producing</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Recruitment'>Recruitment</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Talent Acquisition'>Talent Acquisition</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Talent Acquisition'>Talent Acquisition</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Tax Audits'>Tax Audits</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Tax Laws'>Tax Laws</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Taxation'>Taxation</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Teaching and Instructing'>Teaching and Instructing</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Telemarketing'>Telemarketing</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Telling'>Telling</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Textile Designing'>Textile Designing</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Therapy'>Therapy</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Trade Execution'>Trade Execution</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Training'>Training</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Upholstery'>Upholstery</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Vehicle Operating'>Vehicle Operating</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Vendor Management'>Vendor Management</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Visa Expat Management'>Visa Expat Management</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Visual Merchandising'>Visual Merchandising</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Waste Management'>Waste Management</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Weaving'>Weaving</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Winemaking'>Winemaking</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Writing'>Writing</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Yoga'>Yoga</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Selenium'>Selenium</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='QTP'>QTP</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Quality Center'>Quality Center</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Advanced Java'>Advanced Java</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='AdvancedModelsim'>AdvancedModelsim</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='.Net'>.Net</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='ADOAdvancedModelsim'>ADOAdvancedModelsim</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='AdobeFlexBuilder'>AdobeFlexBuilder</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='ActiveHDL'>ActiveHDL</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='AcegiSecurity'>AcegiSecurity</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='.NET(C#&ASP.NET)'>.NET(C#&ASP.NET)</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='.NETFramework'>.NETFramework</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='AdwordKeywordTool'>AdwordKeywordTool</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Agile'>Agile</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='AHB'>AHB</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Ahreftool'>Ahreftool</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='AjaxandJSON'>AjaxandJSON</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='ALM/QC'>ALM/QC</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='ALM'>ALM</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Angular'>Angular</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='AngularJS'>AngularJS</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Apache'>Apache</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='ApacheCamel'>ApacheCamel</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='ApacheDrill'>ApacheDrill</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='ApachePOI'>ApachePOI</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='ApacheSubversion(SVN)'>ApacheSubversion(SVN)</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='APB'>APB</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='AprimoApplicationWorkbench'>AprimoApplicationWorkbench</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='AprimoMarketingStudio'>AprimoMarketingStudio</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='AptanaStudio'>AptanaStudio</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='AquaStudio'>AquaStudio</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='ArchitectureOOP'>ArchitectureOOP</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='ASP.NetAJAX'>ASP.NetAJAX</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='ASP.NetandMVCFramework'>ASP.NetandMVCFramework</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='ASP.NETFramework'>ASP.NETFramework</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Asp.NetMVC'>Asp.NetMVC</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='ASP.NETMVCC#'>ASP.NETMVCC#</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='ASP.NETMVCRAZOR'>ASP.NETMVCRAZOR</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='AuqaDataStudio'>AuqaDataStudio</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='AutoIT'>AutoIT</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='AXI'>AXI</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='AxisFramework'>AxisFramework</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='BDD'>BDD</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Bitbucket'>Bitbucket</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='BlueJIDE'>BlueJIDE</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='BMCRemedy'>BMCRemedy</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Bootstrap'>Bootstrap</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='BPT'>BPT</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='BugZero'>BugZero</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Bugzilla'>Bugzilla</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='BusinessExplorer'>BusinessExplorer</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='C#.NET'>C#.NET</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='C#.NET.VB.NET'>C#.NET.VB.NET</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='C/C++'>C/C++</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Cache'>Cache</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='CadenceIES'>CadenceIES</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='CadenceVirtuoso'>CadenceVirtuoso</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Calibre'>Calibre</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='CAWilyIntroscope'>CAWilyIntroscope</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='ChromewebInspector'>ChromewebInspector</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Citrix'>Citrix</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='CL/400'>CL/400</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Clearcase'>Clearcase</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Clearcasetool'>Clearcasetool</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='ClickScheduleforFieldServiceManagement.'>ClickScheduleforFieldServiceManagement.</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='clientserver'>clientserver</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='CLLE'>CLLE</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='CloudFoundry'>CloudFoundry</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Controller'>Controller</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='COSMOS'>COSMOS</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='CrystalReportsandSSRSReports'>CrystalReportsandSSRSReports</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='CVC'>CVC</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='CVS'>CVS</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='CXF'>CXF</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='D'>D</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='D2K'>D2K</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='DatabasesMSSQLServer'>DatabasesMSSQLServer</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='DataTransformationservices'>DataTransformationservices</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='DBMS'>DBMS</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='DefectReporting'>DefectReporting</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='DesignPatterns'>DesignPatterns</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Designstudio'>Designstudio</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='DevBox'>DevBox</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='DevExpress'>DevExpress</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='DjangoJquery'>DjangoJquery</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='DotNet'>DotNet</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Eclipse Fresher'>Eclipse Fresher</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Dozer'>Dozer</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Drupal'>Drupal</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='DynamicsCRM'>DynamicsCRM</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Eclipse3.4Helileo/Gelileo '>Eclipse3.4Helileo/Gelileo </option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='EclipseEEIDE'>EclipseEEIDE</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='EclipseIDE'>EclipseIDE</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='EclipseSDE'>EclipseSDE</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='EJB-MDB'>EJB-MDB</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='EntityFrameWork'>EntityFrameWork</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='ERWIN'>ERWIN</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='ExpressJS'>ExpressJS</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Extjs'>Extjs</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Factory'>Factory</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='FamiliarwithWINDOWSfamily'>FamiliarwithWINDOWSfamily</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Fedora'>Fedora</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='FileZilla'>FileZilla</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Firebug'>Firebug</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='FlexcubeRAD'>FlexcubeRAD</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Fogbugz'>Fogbugz</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='FrontController'>FrontController</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Git'>Git</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='GITandCVS'>GITandCVS</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='GitHub'>GitHub</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='GoogleAnalytics'>GoogleAnalytics</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Greenplum'>Greenplum</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Groovy'>Groovy</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Hadoop/HDFS'>Hadoop/HDFS</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Hive'>Hive</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='HPALM'>HPALM</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='HPSC'>HPSC</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='HPSiteScope'>HPSiteScope</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Hybrid'>Hybrid</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='IBMHTTPServer'>IBMHTTPServer</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='IBMRPT'>IBMRPT</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='IBMSynergy'>IBMSynergy</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='InfrastructureSVN'>InfrastructureSVN</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Insurance&WebApplication'>Insurance&WebApplication</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='IntegrationTesting'>IntegrationTesting</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='InternetBrowsing'>InternetBrowsing</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='ionicframework'>ionicframework</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='iReport'>iReport</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='JavaServlet'>JavaServlet</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='JAX-B'>JAX-B</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='JAX-WS WebServices'>JAX-WS WebServices</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Jenkins'>Jenkins</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='JenkinsApacheJavaTomcat'>JenkinsApacheJavaTomcat</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Jmeter'>Jmeter</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='JqueryandAJAX'>JqueryandAJAX</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='JQueryandCSS'>JQueryandCSS</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Jqwidgets&DevExpress'>Jqwidgets&DevExpress</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='jQWidjetsandJQuery'>jQWidjetsandJQuery</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='json'>json</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='KendoMVC'>KendoMVC</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='KendoWebUIforASP.NETMVC4'>KendoWebUIforASP.NETMVC4</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='knowledgeinangularjs'>knowledgeinangularjs</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Komodo'>Komodo</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Languages5'>Languages5</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Linq&ADO.NET'>Linq&ADO.NET</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='LinqtoSqlJavaScript'>LinqtoSqlJavaScript</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='LoadRunner'>LoadRunner</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='LTSpice'>LTSpice</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Lumira'>Lumira</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='MainframeDB2'>MainframeDB2</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='MAC'>MAC</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Makefile'>Makefile</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='MavenHudson'>MavenHudson</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='MCSD'>MCSD</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='MDBandJAXB'>MDBandJAXB</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Media'>Media</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='MicrosoftADMAD'>MicrosoftADMAD</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='MicrosoftSQLServer'>MicrosoftSQLServer</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='MicrosoftSQLServer2008/2012andMicrosoftAccess'>MicrosoftSQLServer2008/2012andMicrosoftAccess</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='MicrosoftSQLServerManagementStudio'>MicrosoftSQLServerManagementStudio</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='MicrosoftTestManagement(MTM)'>MicrosoftTestManagement(MTM)</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='MicrosoftVisualStudio.NET'>MicrosoftVisualStudio.NET</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='MicroStrategyDesktop'>MicroStrategyDesktop</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Modelsim'>Modelsim</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Monodroid'>Monodroid</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='MQJExplorer'>MQJExplorer</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='MSBI(SSRS)'>MSBI(SSRS)</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='MS-Outlook'>MS-Outlook</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='MSSDKAdd-in '>MSSDKAdd-in </option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='MSSQL'>MSSQL</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='MSSQLSERVER'>MSSQLSERVER</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='MSTFS2015'>MSTFS2015</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='MSVision'>MSVision</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='MSVisualStudio'>MSVisualStudio</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Multisim'>Multisim</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='MVC'>MVC</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='MySQL&Sybase'>MySQL&Sybase</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='NetBeansIDE'>NetBeansIDE</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Networking'>Networking</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='NodeJs'>NodeJs</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Open-Office'>Open-Office</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='OCP'>OCP</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='OperatingSystemsWindows2000/XP/Vista/7/8'>OperatingSystemsWindows2000/XP/Vista/7/8</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='OracleApplicationServer10g'>OracleApplicationServer10g</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='OracleDatabase10g/11i'>OracleDatabase10g/11i</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='OracleDesigner6i'>OracleDesigner6i</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='OracleFLEXCUBE'>OracleFLEXCUBE</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='OracleformBuilder10g'>OracleformBuilder10g</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='OracleFormsDeveloper'>OracleFormsDeveloper</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='OracleJDeveloper'>OracleJDeveloper</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='OracleReportBuilder10g'>OracleReportBuilder10g</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='OracleReportsDeveloper'>OracleReportsDeveloper</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='OracleSQLDeveloper'>OracleSQLDeveloper</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='OTN'>OTN</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='PAAS(PlatformASService)WebServices'>PAAS(PlatformASService)WebServices</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Patentsearching'>Patentsearching</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='pdfboxetc'>pdfboxetc</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='PeopleSoftSAPBOXI'>PeopleSoftSAPBOXI</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Performancecenter'>Performancecenter</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='PerformanceTesting'>PerformanceTesting</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='PerlScripting'>PerlScripting</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='PES8.0'>PES8.0</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='PGADMIN'>PGADMIN</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='PGDCA'>PGDCA</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='PL/SQLinDBMS'>PL/SQLinDBMS</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='PlasticsSCMtool'>PlasticsSCMtool</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='PLSQLDeveloper'>PLSQLDeveloper</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='POM'>POM</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='PTP/OAM'>PTP/OAM</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='PuTTY'>PuTTY</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='pycharm'>pycharm</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='QCALM'>QCALM</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='QTP11/UFT'>QTP11/UFT</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='QualityCenter(HPQC)'>QualityCenter(HPQC)</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='QualityCenterALM'>QualityCenterALM</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='RAD'>RAD</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Raptortool'>Raptortool</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='RationalRose'>RationalRose</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Razor'>Razor</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='RDLCReports'>RDLCReports</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='RemedyBMCSoftware'>RemedyBMCSoftware</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='RestFulClient'>RestFulClient</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='RESTfulServices'>RESTfulServices</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='RestfulWebservices'>RestfulWebservices</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='RML'>RML</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='RPG/400'>RPG/400</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='RPGLE'>RPGLE</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='RTLCompiler'>RTLCompiler</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Ruby'>Ruby</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SAML'>SAML</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SAPB1DI-APIVer'>SAPB1DI-APIVer</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SAPB1DI-ServerVer'>SAPB1DI-ServerVer</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SAPBI&ECC'>SAPBI&ECC</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SAPBO'>SAPBO</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SAPBusinessObjects(SAPBO)'>SAPBusinessObjects(SAPBO)</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Scrum'>Scrum</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SDLC'>SDLC</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SDMethodologiesAgile'>SDMethodologiesAgile</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SeleniumIDE'>SeleniumIDE</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SeleniumRC'>SeleniumRC</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='ServerArchitectureMonitoring'>ServerArchitectureMonitoring</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='ServiceLocator'>ServiceLocator</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Servlets'>Servlets</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SharePointdevelopment'>SharePointdevelopment</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='ShellProgramming'>ShellProgramming</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='ShellSubversion'>ShellSubversion</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SiebelAnalytics'>SiebelAnalytics</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SiebelAnswers'>SiebelAnswers</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SiebelCRM'>SiebelCRM</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Singleton'>Singleton</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SISSandInformatica'>SISSandInformatica</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Sitecore'>Sitecore</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SlimFramework'>SlimFramework</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SOA'>SOA</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SOAPServicesandRestfulServices'>SOAPServicesandRestfulServices</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SoapUI'>SoapUI</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SoftwareandToolsMSVision'>SoftwareandToolsMSVision</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SoftwareEngineering'>SoftwareEngineering</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Sonar'>Sonar</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SourceForge'>SourceForge</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SourceJammer'>SourceJammer</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Spring Security'>Spring Security</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Spring Boot'>Spring Boot</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Spring Integration'>Spring Integration</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Spring Batch'>Spring Batch</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Spring Mvc'>Spring Mvc</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Helical Springs'>Helical Springs</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Spring Framework'>Spring Framework</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Spring Aop'>Spring Aop</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Spring Core'>Spring Core</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Spring Ioc'>Spring Ioc</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SQL'>SQL</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SQL/C-tree'>SQL/C-tree</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SQLDeveloper'>SQLDeveloper</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='XMLWebServices'>XMLWebServices</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Sqllite'>Sqllite</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SQLLoader'>SQLLoader</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SQLPlus'>SQLPlus</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SQLplusVisualStudio'>SQLplusVisualStudio</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SQLRPGLE'>SQLRPGLE</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SQLServer'>SQLServer</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SQLServerDenali'>SQLServerDenali</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SQLServermanagementstudio'>SQLServermanagementstudio</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SQLServerReportingServices'>SQLServerReportingServices</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SRDF/S'>SRDF/S</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SSH'>SSH</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SSHfiletransfer'>SSHfiletransfer</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SSRSReport'>SSRSReport</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='StarTeam'>StarTeam</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Strategy'>Strategy</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='STS'>STS</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SunSolaris'>SunSolaris</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SVN'>SVN</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SunSolarisandWindows'>SunSolarisandWindows</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SVNServer'>SVNServer</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SYMCLI'>SYMCLI</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='SymmetrixDMX'>SymmetrixDMX</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Taleo'>Taleo</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='TeamCity'>TeamCity</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='TeamFounderServer(TFS)'>TeamFounderServer(TFS)</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='TelerikControls'>TelerikControls</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='TelerikRADControls'>TelerikRADControls</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Testdata'>Testdata</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='TestDesign'>TestDesign</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='TestEnvironment'>TestEnvironment</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='TestExecution'>TestExecution</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='TestPlan'>TestPlan</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='TestProcessToolsJira'>TestProcessToolsJira</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Testscripts'>Testscripts</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='TestStatusReports'>TestStatusReports</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='TFS'>TFS</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='TFSandSVN'>TFSandSVN</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='TivoliWorkloadScheduler'>TivoliWorkloadScheduler</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Tomcat'>Tomcat</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='TomcatandJBossSOAServer'>TomcatandJBossSOAServer</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Transact-SQL(T-SQL)'>Transact-SQL(T-SQL)</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='TwitterBootstrap'>TwitterBootstrap</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='TypeScript'>TypeScript</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Ubuntu'>Ubuntu</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='UFT'>UFT</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='UnitTesting'>UnitTesting</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Unix/Linux'>Unix/Linux</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='UNIXShellScripting'>UNIXShellScripting</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='USB'>USB</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='VB'>VB</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='VCS'>VCS</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='VHDL'>VHDL</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Vista'>Vista</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='VisualSourceSafe'>VisualSourceSafe</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='VisualStudio'>VisualStudio</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='VisualStudio.NET'>VisualStudio.NET</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='VMAX'>VMAX</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='VNX&Clariionseries'>VNX&Clariionseries</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='VS.Net2008'>VS.Net2008</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='VSS'>VSS</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='WAS8.x'>WAS8.x</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Waterfall&VModel'>Waterfall&VModel</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='WCFandWebServices'>WCFandWebServices</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='WCFServices'>WCFServices</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='WEBAPI'>WEBAPI</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='WebApplication'>WebApplication</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='webbasedandmobileapplications'>webbasedandmobileapplications</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='WebDriver'>WebDriver</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Weblogicconsole'>Weblogicconsole</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='WeblogicJava'>WeblogicJava</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='WebSCME'>WebSCME</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='WebService'>WebService</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='WebServices(SOAP)'>WebServices(SOAP)</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='WhizbilPBN'>WhizbilPBN</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Wicket'>Wicket</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Win2000Server'>Win2000Server</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Windows'>Windows</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='WinNT'>WinNT</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='WinServer'>WinServer</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='WinXP'>WinXP</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='XFire'>XFire</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='Xilinx'>Xilinx</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='XMLandRDLCReports'>XMLandRDLCReports</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='XMLPL/SQL'>XMLPL/SQL</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='XMLRAD'>XMLRAD</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='XSD'>XSD</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='XP'>XP</option>";
	 	                     
	 						    internalOutput = internalOutput+"<option value='XSLT'>XSLT</option>";
	 	                     
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
	 var sourceleadUrl ='http://localhost:8080/app';
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
	 var sourceleadUrl ='http://localhost:8080/app';
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
	 
	 var sourceleadUrl ='http://localhost:8080/app';
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
	 var sourceleadUrl ='http://localhost:8080/app';
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

</script>