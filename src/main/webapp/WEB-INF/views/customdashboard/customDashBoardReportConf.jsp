<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left"><h2>Add Dashboard Reports</h2></div>
      <div class="pull-right brud-crum"><a href="${appPath}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo; <a href="${appPath}/configureCustomDashBoard">Dashboarrd list </a> &raquo; Add Dashboard Reports</div>
    </section>
    
    <div class="project_wrap newDashSection clearfix">
        <div class="newDashLeft">
            <ul id="nav" class="clearfix">
                <li><a href="#" class="active">Performance Reports</a></li>
                <li><a href="#">HR Reports</a></li>
                <li><a href="#">Financial Reports</a></li>
                <li><a href="#">Associate Reports</a></li>
                <li><a href="#">Project Reports</a></li>
                <li><a href="#">Pay roll Reports</a></li>
                <li><a href="#">Immigration Reports</a></li>
                <li><a href="#">User usage Reports</a></li>
            </ul>
        </div>

        <div class="newDashRight">
            <div class="clearfix" id="tab-content">
            <div id="tab1">
                <ul>
                <sec:authorize access="hasRole('ROLE_APPLICANTS_JOINED_REPORT')">
                <li class="clearfix">
                        <input id="c" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="c" class="checkbox-custom-label paddingright">Offer Acceptance Rate
                        </label>
                    </li>
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_APPLICANTS_JOINED_REPORT')">
				<li class="clearfix">
                        <input id="c" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="c" class="checkbox-custom-label paddingright">Applicants Joined Report
                        </label>
                    </li>
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_RECRUITERS_REPORT')">
                    <li class="clearfix">
                        <input id="c" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="c" class="checkbox-custom-label paddingright">Recruiter(s) Report
                        </label>
                    </li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_MY_PERFORMANCE_REPORT')">
                    <li class="clearfix">
                        <input id="d" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="d" class="checkbox-custom-label paddingright">My Performance Report</label>
                    </li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_VENDORS_REPORT')">
                    <li class="clearfix">
                        <input id="e" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="e" class="checkbox-custom-label paddingright">Vendor(s) Report</label>
                    </li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_CLIENT_REPORT')">
                    <li class="clearfix">
                        <input id="f" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="f" class="checkbox-custom-label paddingright">Client(s) Report</label>
                    </li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_APPLICANT_DECLINE')">
                    <li class="clearfix">
                        <input id="g" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="g" class="checkbox-custom-label paddingright">Applicant(s) Decline</label>
                    </li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_APPLICANT_BACKOUT')">
                    <li class="clearfix">
                        <input id="h" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="h" class="checkbox-custom-label paddingright">Applicant(s) Backout</label>
                    </li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_JOBS_PIPELINE')">
                    <li class="clearfix">
                        <input id="j" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="j" class="checkbox-custom-label paddingright">Job(s) Pipeline</label>
                    </li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_APPLICANT_SOURCE')">
                    <li class="clearfix">
                        <input id="k" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="k" class="checkbox-custom-label paddingright">Applicant(s) by Received</label>
                    </li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_TIME_TO_FILL')">
                    <li class="clearfix">
                        <input id="l" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="l" class="checkbox-custom-label paddingright">Time to Fill</label>
                    </li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_APPLICANT_RECEIVED')">
                    <li class="clearfix">
                        <input id="m" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="m" class="checkbox-custom-label paddingright">Applicant(s) by Received</label>
                    </li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_JOBS_STAGES')">
                    <li class="clearfix">
                        <input id="n" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="n" class="checkbox-custom-label paddingright">Job(s) Stage</label>
                    </li>  
                    </sec:authorize>
                    <li class="clearfix">
                        <input id="o" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="o" class="checkbox-custom-label paddingright">Job(s) Report</label>
                    </li> 
				</ul>
            </div>
            
            <sec:authorize access="hasAnyRole('ROLE_DAYWISE_TIMESHEET_REPORT','ROLE_TIMESHEET_REPORT','ROLE_EXPENSES_REPORT','ROLE_MISSING_TIMESHEETS','ROLE_PENDING_FOR_APPROVAL','ROLE_EMPLOYEMENT_VERIFICATION_REPORT')">
            <div id="tab2">
                <ul>
                    <li class="clearfix">
                        <input id="a" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="a" class="checkbox-custom-label paddingright">Assigned Assets History</label>
                    </li>
                    <sec:authorize access="hasRole('ROLE_DAYWISE_TIMESHEET_REPORT')">
                    <li class=" clearfix">
                        <input id="b" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="b" class="checkbox-custom-label paddingright">Daywise Timesheet Report</label>
                    </li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_TIMESHEET_REPORT')">
                    <li class="clearfix">
                        <input id="monthlyReport" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="monthlyReport" class="checkbox-custom-label paddingright">Timesheet Monthly Report</label>
                    </li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_EXPENSES_REPORT')">
                    <li class="clearfix">
                        <input id="expenses" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="expenses" class="checkbox-custom-label paddingright">Expense(s)</label>
                    </li> 
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_PENDING_FOR_APPROVAL')">
                    <li class="clearfix">
                        <input id="approvalExpenses" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="approvalExpenses" class="checkbox-custom-label paddingright">Pending for Approval Expenses</label>
                    </li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_MISSING_TIMESHEETS')">
                    <li class="clearfix">
                        <input id="missingTimesheets" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="missingTimesheets" class="checkbox-custom-label paddingright">Missing Timesheet(s)</label>
                    </li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_PENDING_FOR_APPROVAL')">
                    <li class="clearfix">
                        <input id="approvalTimesheets" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="approvalTimesheets" class="checkbox-custom-label paddingright">Pending for Approval Timesheets</label>
                    </li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_EMPLOYEMENT_VERIFICATION_REPORT')">
                    <li class="clearfix">
                        <input id="verificationReport" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="verificationReport" class="checkbox-custom-label paddingright">Employement Verification Report</label>
                    </li>
                    </sec:authorize>
                    <li class="clearfix">
                        <input id="daywiseLeaveReport" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="daywiseLeaveReport" class="checkbox-custom-label paddingright">Daywise Leave Report</label>
                    </li>
                    <li class="clearfix">
                        <input id="leaveReport" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="leaveReport" class="checkbox-custom-label paddingright">Leave Report</label>
                    </li>
                    <li class="clearfix">
                        <input id="balanceReport" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="balanceReport" class="checkbox-custom-label paddingright">Leave Balance Report</label>
                    </li>
                    <li class="clearfix">
                        <input id="leaveTakenReport" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="leaveTakenReport" class="checkbox-custom-label paddingright">Top Leave Taken Report</label>
                    </li>
				</ul>
            </div>
            </sec:authorize>
            
            <sec:authorize access="hasAnyRole('ROLE_BILLING_REPORTS','ROLE_A_R_AGING','ROLE_A_P_AGING','ROLE_MISSING_INVOICE','ROLE_MISSING_BILL','ROLE_PAID_BILL','ROLE_RECEIVED_INVOICES')">
            <div id="tab3">
                <ul>
                   <sec:authorize access="hasRole('ROLE_BILLING_REPORTS')">
                    <li class="clearfix">
                        <input id="finance" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="finance" class="checkbox-custom-label paddingright">Finance</label>
                    </li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_A_R_AGING')">
                    <li class=" clearfix">
                        <input id="arAging" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="arAging" class="checkbox-custom-label paddingright">A/R Aging</label>
                    </li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_A_P_AGING')">
                    <li class="clearfix">
                        <input id="apAging" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="apAging" class="checkbox-custom-label paddingright">A/P Aging</label>
                    </li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_MISSING_INVOICE')">
                    <li class="clearfix">
                        <input id="missingInvoice" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="missingInvoice" class="checkbox-custom-label paddingright">Missing Invoice(s)</label>
                    </li> 
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_RECEIVED_INVOICES')">
                    <li class="clearfix">
                        <input id="receivedInvoice" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="receivedInvoice" class="checkbox-custom-label paddingright">Received Invoice(s)</label>
                    </li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_MISSING_BILL')">
                    <li class="clearfix">
                        <input id="missingBill" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="missingBill" class="checkbox-custom-label paddingright">Missing Bill(s)</label>
                    </li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_PAID_BILL')">
                    <li class="clearfix">
                        <input id="paidBill" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="paidBill" class="checkbox-custom-label paddingright">Paid Bill(s)</label>
                    </li>
                     </sec:authorize>
                     <sec:authorize access="hasRole('ROLE_MARGIN_SHEET_REPORT')">
                    <li class="clearfix">
                        <input id="marginSheetReport" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="marginSheetReport" class="checkbox-custom-label paddingright">MarginSheetReport</label>
                    </li>
                    </sec:authorize>
                    <li class="clearfix">
                        <input id="assetGrowth" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="assetGrowth" class="checkbox-custom-label paddingright">Asset Growth</label>
                    </li>
				</ul>
            </div>
            </sec:authorize>
            
            <sec:authorize access="hasAnyRole('ROLE_ASSOCIATE_ROI','ROLE_ALL_ASSOCIATE_ROI','ROLE_CAMPAIGN_REPORTS')">
            <div id="tab4">
                <ul>
                <sec:authorize access="hasRole('ROLE_ASSOCIATE_ROI')">
                    <li class="clearfix">
                        <input id="associatesROI" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="associatesROI" class="checkbox-custom-label paddingright">Associate(s) ROI</label>
                    </li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_ALL_ASSOCIATE_ROI')">
                    <li class=" clearfix">
                        <input id="allAssociatesROI" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="allAssociatesROI" class="checkbox-custom-label paddingright">All Associates ROI</label>
                    </li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_CAMPAIGN_REPORTS')">
                    <li class="clearfix">
                        <input id="campaignsReport" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="campaignsReport" class="checkbox-custom-label paddingright">Campaign(s) Report</label>
                    </li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_ASSOCIATE_JOINED_REPORT')">
                    <li class="clearfix">
                        <input id="associateJoinedReport" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="associateJoinedReport" class="checkbox-custom-label paddingright">Associate Joined Report</label>
                    </li> 
                    </sec:authorize>
                    <li class="clearfix">
                        <input id="clock-InSummaryReport" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="clock-InSummaryReport" class="checkbox-custom-label paddingright">Associate Clock-In Summary Report</label>
                    </li>
                    <li class="clearfix">
                        <input id="availableAssociateReport" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="availableAssociateReport" class="checkbox-custom-label paddingright">Available Associate Report</label>
                    </li>
                    <li class="clearfix">
                        <input id="loanAndAdvancesReport" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="loanAndAdvancesReport" class="checkbox-custom-label paddingright">Loans And Advances Report</label>
                    </li>
				</ul>
            </div>
            </sec:authorize>

            <div id="tab5">
                <ul>
                    <li class="clearfix">
                        <input id="projectReport" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="projectReport" class="checkbox-custom-label paddingright">Project Report</label>
                    </li>
                    <li class=" clearfix">
                        <input id="employeeTasks" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="employeeTasks" class="checkbox-custom-label paddingright">Employee Tasks</label>
                    </li>
                    <li class="clearfix">
                        <input id="overDueReport" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="overDueReport" class="checkbox-custom-label paddingright">Over Due Report</label>
                    </li>
				</ul>
            </div>
            
            <div id="tab6">
                <ul>
                <sec:authorize access="hasRole('ROLE_USAGE_REPORT')">
                    <li class="clearfix">
                        <input id="payrollRegister" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="payrollRegister" class="checkbox-custom-label paddingright">Payroll Register</label>
                    </li>
                    <li class=" clearfix">
                        <input id="comparsion" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="comparsion" class="checkbox-custom-label paddingright">Comparsion</label>
                    </li>
                    <li class="clearfix">
                        <input id="payrollSummary" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="payrollSummary" class="checkbox-custom-label paddingright">Payroll Summary</label>
                    </li>
                    <li class="clearfix">
                        <input id="checkRegister" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="checkRegister" class="checkbox-custom-label paddingright">Check Register</label>
                    </li> 
                    <li class="clearfix">
                        <input id="associateWithholding" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="associateWithholding" class="checkbox-custom-label paddingright">Associate Withholding</label>
                    </li>
                    <li class="clearfix">
                        <input id="taxableWagesReport" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="taxableWagesReport" class="checkbox-custom-label paddingright">Taxable Wages Report</label>
                    </li>
                    <li class="clearfix">
                        <input id="deductionReports" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="deductionReports" class="checkbox-custom-label paddingright">Deduction Reports</label>
                    </li>
                    <li class="clearfix">
                        <input id="contributionReports" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="contributionReports" class="checkbox-custom-label paddingright">Contribution Reports</label>
                    </li>
                    <li class="clearfix">
                        <input id="deductionSummaryReport" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="deductionSummaryReport" class="checkbox-custom-label paddingright">Deduction Summary Report</label>
                    </li>
                    <li class="clearfix">
                        <input id="contributionSummaryReports" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="contributionSummaryReports" class="checkbox-custom-label paddingright">Contribution Summary Reports</label>
                    </li>
                    <li class="clearfix">
                        <input id="directDeposit" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="directDeposit" class="checkbox-custom-label paddingright">Direct Deposit</label>
                    </li>
                    <li class="clearfix">
                        <input id="employeePayroll" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="employeePayroll" class="checkbox-custom-label paddingright">Employee Payroll</label>
                    </li>
                    <li class="clearfix">
                        <input id="contractEmployeePayroll" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="contractEmployeePayroll" class="checkbox-custom-label paddingright">Contract Employee Payroll</label>
                    </li>
                    </sec:authorize>
				</ul>
            </div>
            
            <div id="tab7">
                <ul>
                    <li class="clearfix">
                        <input id="petitionReport" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="petitionReport" class="checkbox-custom-label paddingright">Petition Report</label>
                    </li>
                    <li class=" clearfix">
                        <input id="petitionAttorneyReport" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="petitionAttorneyReport" class="checkbox-custom-label paddingright">Petition Attorney Report</label>
                    </li>
                    <li class="clearfix">
                        <input id="lcaReport" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="lcaReport" class="checkbox-custom-label paddingright">LCA Report</label>
                    </li>
				</ul>
            </div>
            
            <sec:authorize access="hasAnyRole('ROLE_USAGE_REPORT')"> 
            <div id="tab8">
                <ul>
                    <li class="clearfix">
                        <input id="usageReport" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="usageReport" class="checkbox-custom-label paddingright">Usage Report(s)</label>
                    </li>
				</ul>
            </div>
            </sec:authorize>
        </div>
        
    <div class="footerstyelsNew">
        <div class="pull-right mr">
            <input value="Add" class="btn btn-primary mr" type="button">
        </div>
    </div>

        </div>

	</div>
  </section>
  
  <script>
$(document).ready(function(){
    $('#tab-content div').hide();
    $('#tab-content div:first').show();

    $('#nav li').click(function() {
        $('#nav li a').removeClass("active");
        $(this).find('a').addClass("active");
        $('#tab-content div').hide();

        var indexer = $(this).index(); 
        $('#tab-content div:eq(' + indexer + ')').fadeIn(); 
    });  
});

</script>
<jsp:include page="../masterFooter.jsp" />