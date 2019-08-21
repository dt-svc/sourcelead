<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<section class="content-wraper">

    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>HR Reports</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/reportsDashboard'/>">Reports Dashboard
</a> &raquo; HR Reports</div>
    </section>
    
<div class="reportNewDash">
	
		<a href="${appPath}/assetsReport">
	<div class="repDashCta">
		<span><i class="fa fa-check-square-o"></i></span>
		<div class="repDashCtaWrap">
		<h2>Asset Assigned History</h2><b>View</b>
		</div>
	</div>	

	</a>

    <sec:authorize access="hasRole('ROLE_DAYWISE_TIMESHEET_REPORT')">
    <a href="${appPath}/daywiseTimeSheetReports">
	<div class="repDashCta">
		<span><i class="fa fa-calendar"></i></span>
		<div class="repDashCtaWrap">
		<h2>Daywise Timesheet Report</h2><b>View</b>
		</div>
	</div>
	</a>
	</sec:authorize>
	
	<a href="${appPath}/projectTimeEntriesReport">
	<div class="repDashCta">
		<span><i class="fa fa-calendar"></i></span>
		<div class="repDashCtaWrap">
		<h2>Project Time Entries Report</h2><b>View</b>
		</div>
	</div>
	</a>
	
	<a href="${appPath}/resourceTimeEntriesReport">
	<div class="repDashCta">
		<span><i class="fa fa-calendar"></i></span>
		<div class="repDashCtaWrap">
		<h2>Resource Time Entries Report</h2><b>View</b>
		</div>
	</div>
	</a>
	
	<sec:authorize access="hasRole('ROLE_TIMESHEET_REPORT')">
		<a href="${appPath}/timeSheetReports">
	<div class="repDashCta">
		<span><i class="fa fa-clock-o"></i></span>
		<div class="repDashCtaWrap">
		 <h2>Timesheet Monthly Report</h2><b>View</b>
		</div>
	</div>
	</a>
	</sec:authorize>
		
	<sec:authorize access="hasRole('ROLE_EXPENSES_REPORT')">	
	<a href="${appPath}/expensesReportsList">
	<div class="repDashCta">
		<span><i class="fa fa-dollar"></i></span>
		<div class="repDashCtaWrap">
	    <h2>Expense(s)</h2><b>View</b>
		</div>
	</div>
	</a>
	</sec:authorize>
	
	<sec:authorize access="hasRole('ROLE_MISSING_TIMESHEETS')">
	<a href="${appPath}/missingTimesheetsReport">
	<div class="repDashCta">
		<span><i class="fa fa-clock-o"></i></span>
		<div class="repDashCtaWrap">
         <h2>Missing Timesheet(s)</h2><b>View</b>
		</div>
	</div>	
	</a>
	</sec:authorize>
<%-- 
	<a href="${${SourceLeadWeb_sourceleadUrl}/pendingForExpenseApprovalReport">
	<div class="repDashCta">
		<span><i class="fa fa-square-o"></i></span>
		<div class="repDashCtaWrap">
		<h2>Pending for Approval Expenses</h2><b>View</b>
		</div>
	</div>	
	</a> --%>
	
	<sec:authorize access="hasRole('ROLE_PENDING_FOR_APPROVAL')">
	<a href="${appPath}/pendingForApprovalReport">
	<div class="repDashCta">
		<span><i class="fa fa-square-o"></i></span>
		<div class="repDashCtaWrap">
		<h2>Pending for Approval Timesheet(s)</h2><b>View</b>
		</div>
	</div>	
	</a>
		</sec:authorize>
	
	<sec:authorize access="hasRole('ROLE_EMPLOYEMENT_VERIFICATION_REPORT')">
	<a href="${appPath}/employementVerificationReport">
	<div class="repDashCta">
		<span><i class="fa fa-check-square-o"></i></span>
		<div class="repDashCtaWrap">
		<h2>Employment Verification Report</h2><b>View</b>
		</div>
	</div>	
	</a>
	</sec:authorize>

			<a href="${appPath}/daywiseLeaveReport">
	<div class="repDashCta">
		<span><i class="fa fa-check-square-o"></i></span>
		<div class="repDashCtaWrap">
		<h2>Daywise Leave Report</h2><b>View</b>
		</div>
	</div>	
	</a>
	<a href="${appPath}/leaveReport">
	<div class="repDashCta">
		<span><i class="fa fa-check-square-o"></i></span>
		<div class="repDashCtaWrap">
		<h2>Leave Report</h2><b>View</b>
		</div>
	</div>	
	</a>
	<a href="${appPath}/leaveBalancesReport">
	<div class="repDashCta">
		<span><i class="fa fa-check-square-o"></i></span>
		<div class="repDashCtaWrap">
		<h2>Leave Balance Report</h2><b>View</b>
		</div>
	</div>	

	</a>
	
	
	<a href="${appPath}/topLeaveTakenReports">
	<div class="repDashCta">
		<span><i class="fa fa-check-square-o"></i></span>
		<div class="repDashCtaWrap">
		<h2>Top Leave Taken Report</h2><b>View</b>
		</div>
	</div>	
	</a>
	<div class="clearfix"></div>	
</div>
    
    
  </section>

<jsp:include page="../masterFooter.jsp" />