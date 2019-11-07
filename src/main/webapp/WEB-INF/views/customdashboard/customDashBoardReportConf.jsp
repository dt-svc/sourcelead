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
    <div id="loading"></div>
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
          <form:form id="reports_configuration_form"  name="reports_configuration_form" method="post">
            <div class="clearfix" id="tab-content">
            <div id="tab1">
                <ul>
                <sec:authorize access="hasRole('ROLE_APPLICANTS_JOINED_REPORT')">
                <li class="clearfix">
                        <input id="offerAcceptanceRate"  value="OFFER_ACCEPTANCE_RATE" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="offerAcceptanceRate" class="checkbox-custom-label paddingright">Offer Acceptance Rate
                        </label>
                    </li>
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_APPLICANTS_JOINED_REPORT')">
				<li class="clearfix">
                        <input id="aplicantJoin" value="APPLICANTS_JOINED_REPORT" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="" class="checkbox-custom-label paddingright">Applicants Joined Report
                        </label>
                    </li>
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_RECRUITERS_REPORT')">
                    <li class="clearfix">
                        <input id="recruiterReport" value="RECRUITER_REPORT" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="recruiterReport" class="checkbox-custom-label paddingright">Recruiter(s) Report
                        </label>
                    </li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_MY_PERFORMANCE_REPORT')">
                    <li class="clearfix">
                        <input id="performanceReport" value="MY_PERFORMANCE_REPORT" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="performanceReport" class="checkbox-custom-label paddingright">My Performance Report</label>
                    </li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_VENDORS_REPORT')">
                    <li class="clearfix">
                        <input id="vendorReport" value="VENDOR_REPORT" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="vendorReport" class="checkbox-custom-label paddingright">Vendor(s) Report</label>
                    </li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_CLIENT_REPORT')">
                    <li class="clearfix">
                        <input id="clientReport" value="CLIENT_REPORT" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="clientReport" class="checkbox-custom-label paddingright">Client(s) Report</label>
                    </li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_APPLICANT_DECLINE')">
                    <li class="clearfix">
                        <input id="applicantDecline" value="APPLICANT_DECLINE" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="applicantDecline" class="checkbox-custom-label paddingright">Applicant(s) Decline</label>
                    </li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_APPLICANT_BACKOUT')">
                    <li class="clearfix">
                        <input id="applicantBackout" value="APPLICANT_BACKOUT" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="applicantBackout" class="checkbox-custom-label paddingright">Applicant(s) Backout</label>
                    </li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_JOBS_PIPELINE')">
                    <li class="clearfix">
                        <input id="jobsReport" value="JOBS_REPORT" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="jobsReport" class="checkbox-custom-label paddingright">Job(s) Pipeline</label>
                    </li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_APPLICANT_SOURCE')">
                    <li class="clearfix">
                        <input id="applicantBySource" value="APPLICANTS_BY_SOURCE" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="applicantBySource" class="checkbox-custom-label paddingright">Applicant(s) by Source</label>
                    </li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_TIME_TO_FILL')">
                    <li class="clearfix">
                        <input id="timeToFill" value="TIME_TO_FILL" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="timeToFill" class="checkbox-custom-label paddingright">Time to Fill</label>
                    </li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_APPLICANT_RECEIVED')">
                    <li class="clearfix">
                        <input id="applicantByReceived" value="APPLICANTS_BY_RECEIVED" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="applicantByReceived" class="checkbox-custom-label paddingright">Applicant(s) by Received</label>
                    </li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_JOBS_STAGES')">
                    <li class="clearfix">
                        <input id="jobsStage" value="JOBS_STAGE" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="jobsStage" class="checkbox-custom-label paddingright">Job(s) Stage</label>
                    </li>  
                    </sec:authorize>
                    <li class="clearfix">
                        <input id="jobsReport" value="JOBS_REPORT" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="jobsReport" class="checkbox-custom-label paddingright">Job(s) Report</label>
                    </li> 
				</ul>
            </div>
            
            <sec:authorize access="hasAnyRole('ROLE_DAYWISE_TIMESHEET_REPORT','ROLE_TIMESHEET_REPORT','ROLE_EXPENSES_REPORT','ROLE_MISSING_TIMESHEETS','ROLE_PENDING_FOR_APPROVAL','ROLE_EMPLOYEMENT_VERIFICATION_REPORT')">
            <div id="tab2">
                <ul>
                    <li class="clearfix">
                        <input id="assignedAssetHis" value="ASSIGNED_ASSETS_HISTORY" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="assignedAssetHis" class="checkbox-custom-label paddingright">Assigned Assets History</label>
                    </li>
                    <sec:authorize access="hasRole('ROLE_DAYWISE_TIMESHEET_REPORT')">
                    <li class=" clearfix">
                        <input id="daywiseTimesheet" value="DAYWISE_TIMESHEET_REPORT" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="daywiseTimesheet" class="checkbox-custom-label paddingright">Daywise Timesheet Report</label>
                    </li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_TIMESHEET_REPORT')">
                    <li class="clearfix">
                        <input id="timesheetMonthlyReport" value="TIMESHEET_MONTHLY_REPORT" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="timesheetMonthlyReport" class="checkbox-custom-label paddingright">Timesheet Monthly Report</label>
                    </li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_EXPENSES_REPORT')">
                    <li class="clearfix">
                        <input id="expenses" value="EXPENSES" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="expenses" class="checkbox-custom-label paddingright">Expense(s)</label>
                    </li> 
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_PENDING_FOR_APPROVAL')">
                    <li class="clearfix">
                        <input id="pendingApprovalExpenses" value="PENDING_FOR_APPROVAL_EXPENSES" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="pendingApprovalExpenses" class="checkbox-custom-label paddingright">Pending for Approval Expenses</label>
                    </li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_MISSING_TIMESHEETS')">
                    <li class="clearfix">
                        <input id="missingTimesheet" value="MISSING_TIMESHEET" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="missingTimesheet" class="checkbox-custom-label paddingright">Missing Timesheet(s)</label>
                    </li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_PENDING_FOR_APPROVAL')">
                    <li class="clearfix">
                        <input id="pendingApprovalTimesheets" value="PENDING_FOR_APPROVAL_TIMESHEETS" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="pendingApprovalTimesheets" class="checkbox-custom-label paddingright">Pending for Approval Timesheets</label>
                    </li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_EMPLOYEMENT_VERIFICATION_REPORT')">
                    <li class="clearfix">
                        <input id="verificationReport" value="EMPLOYEMENT_VERIFICATION_REPORT" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="verificationReport" class="checkbox-custom-label paddingright">Employement Verification Report</label>
                    </li>
                    </sec:authorize>
                    <li class="clearfix">
                        <input id="daywiseLeaveReport" value="DAYWISE_LEAVE_REPORT" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="daywiseLeaveReport" class="checkbox-custom-label paddingright">Daywise Leave Report</label>
                    </li>
                    <li class="clearfix">
                        <input id="leaveReport" value="LEAVE_REPORT" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="leaveReport" class="checkbox-custom-label paddingright">Leave Report</label>
                    </li>
                    <li class="clearfix">
                        <input id="leaveBalanceReport" value="LEAVE_BALANCE_REPORT" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="leaveBalanceReport" class="checkbox-custom-label paddingright">Leave Balance Report</label>
                    </li>
                    <li class="clearfix">
                        <input id="topLeaveTaken" value="TOP_LEAVE_TAKEN_REPORT" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="topLeaveTaken" class="checkbox-custom-label paddingright">Top Leave Taken Report</label>
                    </li>
				</ul>
            </div>
            </sec:authorize>
            
            <sec:authorize access="hasAnyRole('ROLE_BILLING_REPORTS','ROLE_A_R_AGING','ROLE_A_P_AGING','ROLE_MISSING_INVOICE','ROLE_MISSING_BILL','ROLE_PAID_BILL','ROLE_RECEIVED_INVOICES')">
            <div id="tab3">
                <ul>
                   <sec:authorize access="hasRole('ROLE_BILLING_REPORTS')">
                    <li class="clearfix">
                        <input id="finance" value="FINANCE" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="finance" class="checkbox-custom-label paddingright">Finance</label>
                    </li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_A_R_AGING')">
                    <li class=" clearfix">
                        <input id="arAging" value="AR_AGING" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="arAging" class="checkbox-custom-label paddingright">A/R Aging</label>
                    </li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_A_P_AGING')">
                    <li class="clearfix">
                        <input id="apAging" value="AP_AGING" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="apAging" class="checkbox-custom-label paddingright">A/P Aging</label>
                    </li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_MISSING_INVOICE')">
                    <li class="clearfix">
                        <input id="missingInvoice" value="MISSING_INVOICE" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="missingInvoice" class="checkbox-custom-label paddingright">Missing Invoice(s)</label>
                    </li> 
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_RECEIVED_INVOICES')">
                    <li class="clearfix">
                        <input id="receivedInvoice" value="RECEIVED_INVOICE" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="receivedInvoice" class="checkbox-custom-label paddingright">Received Invoice(s)</label>
                    </li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_MISSING_BILL')">
                    <li class="clearfix">
                        <input id="missingBill" value="MISSING_BILL" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="missingBill" class="checkbox-custom-label paddingright">Missing Bill(s)</label>
                    </li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_PAID_BILL')">
                    <li class="clearfix">
                        <input id="paidBill" value="PAID_BILL" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="paidBill" class="checkbox-custom-label paddingright">Paid Bill(s)</label>
                    </li>
                     </sec:authorize>
                     <sec:authorize access="hasRole('ROLE_MARGIN_SHEET_REPORT')">
                    <li class="clearfix">
                        <input id="marginSheetReport" value="MARGIN_SHEET_REPORT" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="marginSheetReport" class="checkbox-custom-label paddingright">Margin Sheet Report</label>
                    </li>
                    </sec:authorize>
                    <li class="clearfix">
                        <input id="assetGrowth" value="ASSET_GROWTH" class="checkbox-custom" name="reportName[]" type="checkbox">
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
                        <input id="associatesROI" value="ASSOCIATE_ROI" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="associatesROI" class="checkbox-custom-label paddingright">Associate(s) ROI</label>
                    </li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_ALL_ASSOCIATE_ROI')">
                    <li class=" clearfix">
                        <input id="allAssociatesROI" value="ALL_ASSOCIATES_ROI" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="allAssociatesROI" class="checkbox-custom-label paddingright">All Associates ROI</label>
                    </li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_CAMPAIGN_REPORTS')">
                    <li class="clearfix">
                        <input id="campaignReport" value="CAMPAIGN_REPORT" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="campaignReport" class="checkbox-custom-label paddingright">Campaign(s) Report</label>
                    </li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_ASSOCIATE_JOINED_REPORT')">
                    <li class="clearfix">
                        <input id="associateJoinedReport" value="ASSOCIATE_JOINED_REPORT" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="associateJoinedReport" class="checkbox-custom-label paddingright">Associate Joined Report</label>
                    </li> 
                    </sec:authorize>
                    <li class="clearfix">
                        <input id="clock-InSummaryReport" value="ASSOCIATE_CLOCKIN_SUMMARY_REPORT" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="clock-InSummaryReport" class="checkbox-custom-label paddingright">Associate Clock-In Summary Report</label>
                    </li>
                    <li class="clearfix">
                        <input id="availableAssociateReport" value="AVAILABLE_ASSOCIATE_REPORT" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="availableAssociateReport" class="checkbox-custom-label paddingright">Available Associate Report</label>
                    </li>
                    <li class="clearfix">
                        <input id="loanAndAdvancesReport" value="LOANS_AND_ADVANCES_REPORT" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="loanAndAdvancesReport" class="checkbox-custom-label paddingright">Loans And Advances Report</label>
                    </li>
				</ul>
            </div>
            </sec:authorize>

            <div id="tab5">
                <ul>
                    <li class="clearfix">
                        <input id="projectReport" value="PROJECT_REPORT" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="projectReport" class="checkbox-custom-label paddingright">Project Report</label>
                    </li>
                    <li class=" clearfix">
                        <input id="employeeTasks" value="EMPLOYEE_TASKS" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="employeeTasks" class="checkbox-custom-label paddingright">Employee Tasks</label>
                    </li>
                    <li class="clearfix">
                        <input id="overDueReport" value="OVER_DUE_REPORT" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="overDueReport" class="checkbox-custom-label paddingright">Over Due Report</label>
                    </li>
				</ul>
            </div>
            
            <div id="tab6">
                <ul>
                <sec:authorize access="hasRole('ROLE_USAGE_REPORT')">
                    <li class="clearfix">
                        <input id="payrollRegister" value="PAYROLL_REGISTER" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="payrollRegister" class="checkbox-custom-label paddingright">Payroll Register</label>
                    </li>
                    <li class=" clearfix">
                        <input id="comparsion" value="COMPARSION" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="comparsion" class="checkbox-custom-label paddingright">Comparsion</label>
                    </li>
                    <li class="clearfix">
                        <input id="payrollSummary" value="PAYROLL_SUMMARY" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="payrollSummary" class="checkbox-custom-label paddingright">Payroll Summary</label>
                    </li>
                    <li class="clearfix">
                        <input id="checkRegister" value="CHECK_REGISTER" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="checkRegister" class="checkbox-custom-label paddingright">Check Register</label>
                    </li> 
                    <li class="clearfix">
                        <input id="associateWithholding" value="ASSOCIATE_WITHHOLDING" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="associateWithholding" class="checkbox-custom-label paddingright">Associate Withholding</label>
                    </li>
                    <li class="clearfix">
                        <input id="taxableWagesReport" value="TAXABLE_WAGES_REPORT" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="taxableWagesReport" class="checkbox-custom-label paddingright">Taxable Wages Report</label>
                    </li>
                    <li class="clearfix">
                        <input id="deductionReports" value="DEDUCTION_REPORTS" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="deductionReports" class="checkbox-custom-label paddingright">Deduction Reports</label>
                    </li>
                    <li class="clearfix">
                        <input id="contributionReports" value="CONTRIBUTION_REPORTS" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="contributionReports" class="checkbox-custom-label paddingright">Contribution Reports</label>
                    </li>
                    <li class="clearfix">
                        <input id="deductionSummaryReport" value="DEDUCTION_SUMMARY_REPORT" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="deductionSummaryReport" class="checkbox-custom-label paddingright">Deduction Summary Report</label>
                    </li>
                    <li class="clearfix">
                        <input id="contributionSummaryReports" value="CONTRIBUTION_SUMMARY_REPORTS" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="contributionSummaryReports" class="checkbox-custom-label paddingright">Contribution Summary Reports</label>
                    </li>
                    <li class="clearfix">
                        <input id="directDeposit" value="DIRECT_DEPOSIT" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="directDeposit" class="checkbox-custom-label paddingright">Direct Deposit</label>
                    </li>
                    <li class="clearfix">
                        <input id="employeePayroll" value="EMPLOYEE_PAYROLL" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="employeePayroll" class="checkbox-custom-label paddingright">Employee Payroll</label>
                    </li>
                    <li class="clearfix">
                        <input id="contractEmployeePayroll" value="CONTRACT_EMPLOYEE_PAYROLL" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="contractEmployeePayroll" class="checkbox-custom-label paddingright">Contract Employee Payroll</label>
                    </li>
                    </sec:authorize>
				</ul>
            </div>
            
            <div id="tab7">
                <ul>
                    <li class="clearfix">
                        <input id="petitionReport" value="PETITION_REPORT" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="petitionReport" class="checkbox-custom-label paddingright">Petition Report</label>
                    </li>
                    <li class=" clearfix">
                        <input id="petitionAttorneyReport" value="PETITION_ATTORNEY_REPORT" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="petitionAttorneyReport" class="checkbox-custom-label paddingright">Petition Attorney Report</label>
                    </li>
                    <li class="clearfix">
                        <input id="lcaReport" value="LCA_REPORT" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="lcaReport" class="checkbox-custom-label paddingright">LCA Report</label>
                    </li>
				</ul>
            </div>
            
            <sec:authorize access="hasAnyRole('ROLE_USAGE_REPORT')"> 
            <div id="tab8">
                <ul>
                    <li class="clearfix">
                        <input id="usageReport" value="USAGE_REPORT" class="checkbox-custom" name="reportName[]" type="checkbox">
                        <label for="usageReport" class="checkbox-custom-label paddingright">Usage Report(s)</label>
                    </li>
				</ul>
            </div>
            </sec:authorize>
        </div>
        
    <div class="footerstyelsNew">
        <div class="pull-right mr">
            <input value="Add" class="btn btn-primary mr" type="button" onclick="customReportsConfiguration();">
        </div>
    </div>
	</form:form>
        </div>

	</div>
	<div id="actionsPopUp"></div>
  </section>
  
  
  
 <script>
 function customReportsConfiguration(){
	 var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
	  var customDashBoardConfUUID = '${customDashBoardConfUUID}';
	  var reportTypeArray = [];
		 $("input[name='reportName[]']:checked").each( function (i) {
			 reportTypeArray.push($(this).val());
		});
		 
		if(reportTypeArray.length > 0){
			$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:1000;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			$.ajax({ 
		    	type: "POST",
		    	url: sourceleadUrl+"/customReportsConfiguration?${_csrf.parameterName}=${_csrf.token}&customDashBoardConfUUID="+customDashBoardConfUUID+"&reportTypeArray="+reportTypeArray,
		        success: function(json) {
		        	if((json.response == 'SUCCESS')){
		        		//location.replace(sourceleadUrl+"/enrollRoleActions?uuid="+uuid); 
		        		location.replace(sourceleadUrl+"/configureCustomDashBoard"); 
			        } 
		        },
		 }); 
		}else{
			alertMessage("Select atleast one Report");
		}
	  
 }
 
 
 
 function alertMessage(message){
		var result = "";
		result = result +"	<div class='project_inn_mask'></div>";
		result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a pofix'>";
		result = result +"		<p>"+message+"</p>";
		result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
		result = result +"		<b href='javascript:void(0)'><input id='confirm-id' style='width:100%' type='button' value='Ok' onclick='cancel();'></b>";
		result = result +"	</div>";
		$("#actionsPopUp").append(result);
		$("#remove_user_pop").slideDown(200);
		$(".project_inn_mask").fadeIn(200);

	}
</script>

<script type="text/javascript">
function cancel(){
	$("#actionsPopUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>

<script>
var custmConfUUID = '${reportCongList}';

<c:forEach var="report" items="${reportCongList}">  
		<c:if test = "${report.reportName == 'OFFER_ACCEPTANCE_RATE'}">
		   $("#offerAcceptanceRate").prop('checked', true);
		</c:if>

		<c:if test = "${report.reportName == 'APPLICANTS_JOINED_REPORT'}">
		   $("#aplicantJoin").prop('checked', true);
		</c:if>
 
        <c:if test = "${report.reportName == 'RECRUITER_REPORT'}">
		   $("#recruiterReport").prop('checked', true);
	    </c:if>
	    
	    <c:if test = "${report.reportName == 'MY_PERFORMANCE_REPORT'}">
		   $("#performanceReport").prop('checked', true);
	    </c:if>
	    
	    <c:if test = "${report.reportName == 'VENDOR_REPORT'}">
		   $("#vendorReport").prop('checked', true);
	    </c:if>
	    
	    <c:if test = "${report.reportName == 'CLIENT_REPORT'}">
		   $("#clientReport").prop('checked', true);
	    </c:if>
	    
	    <c:if test = "${report.reportName == 'APPLICANT_DECLINE'}">
		   $("#applicantDecline").prop('checked', true);
	    </c:if>
	    <c:if test = "${report.reportName == 'APPLICANT_BACKOUT'}">
		   $("#applicantBackout").prop('checked', true);
	    </c:if>
	     
	    <c:if test = "${report.reportName == 'JOBS_REPORT'}">
		   $("#jobsReport").prop('checked', true);
	    </c:if>
	    
	    <c:if test = "${report.reportName == 'APPLICANTS_BY_SOURCE'}">
		   $("#applicantBySource").prop('checked', true);
	    </c:if>
	   
	    <c:if test = "${report.reportName == 'TIME_TO_FILL'}">
		   $("#timeToFill").prop('checked', true);
	    </c:if>
	    
	    <c:if test = "${report.reportName == 'APPLICANTS_BY_RECEIVED'}">
		   $("#applicantByReceived").prop('checked', true);
	    </c:if>
	     
	    <c:if test = "${report.reportName == 'JOBS_STAGE'}">
		   $("#jobsStage").prop('checked', true);
	    </c:if>
	     
	    <c:if test = "${report.reportName == 'JOBS_REPORT'}">
		   $("#jobsReport").prop('checked', true);
	    </c:if>
	     
	    <c:if test = "${report.reportName == 'ASSIGNED_ASSETS_HISTORY'}">
		   $("#assignedAssetHis").prop('checked', true);
	    </c:if> 
	     
	    <c:if test = "${report.reportName == 'DAYWISE_TIMESHEET_REPORT'}">
		   $("#daywiseTimesheet").prop('checked', true);
	    </c:if> 
	    
	    <c:if test = "${report.reportName == 'TIMESHEET_MONTHLY_REPORT'}">
		   $("#timesheetMonthlyReport").prop('checked', true);
	    </c:if> 
	    
	    <c:if test = "${report.reportName == 'EXPENSES'}">
		   $("#expenses").prop('checked', true);
	    </c:if> 
	     
	    <c:if test = "${report.reportName == 'PENDING_FOR_APPROVAL_EXPENSES'}">
		   $("#pendingApprovalExpenses").prop('checked', true);
	    </c:if> 
	    <c:if test = "${report.reportName == 'MISSING_TIMESHEET'}">
		   $("#missingTimesheet").prop('checked', true);
	    </c:if> 
	    <c:if test = "${report.reportName == 'PENDING_FOR_APPROVAL_TIMESHEETS'}">
		   $("#pendingApprovalTimesheets").prop('checked', true);
	    </c:if> 
	    
	    <c:if test = "${report.reportName == 'EMPLOYEMENT_VERIFICATION_REPORT'}">
		   $("#verificationReport").prop('checked', true);
	    </c:if> 
	    
	    <c:if test = "${report.reportName == 'DAYWISE_LEAVE_REPORT'}">
		   $("#daywiseLeaveReport").prop('checked', true);
	    </c:if> 
	    
	    <c:if test = "${report.reportName == 'LEAVE_REPORT'}">
		   $("#leaveReport").prop('checked', true);
	    </c:if> 
	    
	    <c:if test = "${report.reportName == 'LEAVE_BALANCE_REPORT'}">
		   $("#leaveBalanceReport").prop('checked', true);
	    </c:if>
	    
	    <c:if test = "${report.reportName == 'TOP_LEAVE_TAKEN_REPORT'}">
		   $("#topLeaveTaken").prop('checked', true);
	    </c:if>
	      
	    
	    <c:if test = "${report.reportName == 'FINANCE'}">
		   $("#finance").prop('checked', true);
	    </c:if>
	     
	    <c:if test = "${report.reportName == 'AR_AGING'}">
		   $("#arAging").prop('checked', true);
	    </c:if>
	     
	    <c:if test = "${report.reportName == 'AP_AGING'}">
		   $("#apAging").prop('checked', true);
	    </c:if>
	     
	    <c:if test = "${report.reportName == 'MISSING_INVOICE'}">
		   $("#missingInvoice").prop('checked', true);
	    </c:if>
	     
	    <c:if test = "${report.reportName == 'RECEIVED_INVOICE'}">
		   $("#receivedInvoice").prop('checked', true);
	    </c:if>
	    
	    
	    <c:if test = "${report.reportName == 'MISSING_BILL'}">
		   $("#missingBill").prop('checked', true);
	    </c:if>
	    
	    <c:if test = "${report.reportName == 'PAID_BILL'}">
		   $("#paidBill").prop('checked', true);
	    </c:if>
	     
	    
	    <c:if test = "${report.reportName == 'MARGIN_SHEET_REPORT'}">
		   $("#marginSheetReport").prop('checked', true);
	    </c:if>
	     
	    <c:if test = "${report.reportName == 'ASSET_GROWTH'}">
		   $("#assetGrowth").prop('checked', true);
	    </c:if>
	    
	    <c:if test = "${report.reportName == 'ASSOCIATE_ROI'}">
		   $("#associatesROI").prop('checked', true);
	    </c:if>
	    <c:if test = "${report.reportName == 'ALL_ASSOCIATES_ROI'}">
		   $("#allAssociatesROI").prop('checked', true);
	    </c:if>
	    <c:if test = "${report.reportName == 'CAMPAIGN_REPORT'}">
		   $("#campaignReport").prop('checked', true);
	    </c:if>
	    <c:if test = "${report.reportName == 'ASSOCIATE_JOINED_REPORT'}">
		   $("#associateJoinedReport").prop('checked', true);
	    </c:if>
	    <c:if test = "${report.reportName == 'ASSOCIATE_CLOCKIN_SUMMARY_REPORT'}">
		   $("#clock-InSummaryReport").prop('checked', true);
	    </c:if>
	    <c:if test = "${report.reportName == 'AVAILABLE_ASSOCIATE_REPORT'}">
		   $("#availableAssociateReport").prop('checked', true);
	    </c:if>
	    <c:if test = "${report.reportName == 'LOANS_AND_ADVANCES_REPORT'}">
		   $("#loanAndAdvancesReport").prop('checked', true);
	    </c:if>
	     
	    <c:if test = "${report.reportName == 'PROJECT_REPORT'}">
		   $("#projectReport").prop('checked', true);
	    </c:if>
	     
	    <c:if test = "${report.reportName == 'EMPLOYEE_TASKS'}">
		   $("#employeeTasks").prop('checked', true);
	    </c:if>
	     
	    <c:if test = "${report.reportName == 'OVER_DUE_REPORT'}">
		   $("#overDueReport").prop('checked', true);
	    </c:if>
	     
	    <c:if test = "${report.reportName == 'PAYROLL_REGISTER'}">
		   $("#payrollRegister").prop('checked', true);
	    </c:if>
	    <c:if test = "${report.reportName == 'COMPARSION'}">
		   $("#comparsion").prop('checked', true);
	    </c:if>
	    <c:if test = "${report.reportName == 'PAYROLL_SUMMARY'}">
		   $("#payrollSummary").prop('checked', true);
	    </c:if>
	    <c:if test = "${report.reportName == 'CHECK_REGISTER'}">
		   $("#checkRegister").prop('checked', true);
	    </c:if>
	    <c:if test = "${report.reportName == 'ASSOCIATE_WITHHOLDING'}">
		   $("#associateWithholding").prop('checked', true);
	    </c:if>
	    <c:if test = "${report.reportName == 'TAXABLE_WAGES_REPORT'}">
		   $("#taxableWagesReport").prop('checked', true);
	    </c:if>
	    <c:if test = "${report.reportName == 'DEDUCTION_REPORTS'}">
		   $("#deductionReports").prop('checked', true);
	    </c:if>
	    <c:if test = "${report.reportName == 'CONTRIBUTION_REPORTS'}">
		   $("#contributionReports").prop('checked', true);
	    </c:if>
	    <c:if test = "${report.reportName == 'DEDUCTION_SUMMARY_REPORT'}">
		   $("#deductionSummaryReport").prop('checked', true);
	    </c:if>
	     
	    <c:if test = "${report.reportName == 'CONTRIBUTION_SUMMARY_REPORTS'}">
		   $("#contributionSummaryReports").prop('checked', true);
	    </c:if>
	    <c:if test = "${report.reportName == 'DIRECT_DEPOSIT'}">
		   $("#directDeposit").prop('checked', true);
	    </c:if>
	    <c:if test = "${report.reportName == 'EMPLOYEE_PAYROLL'}">
		   $("#employeePayroll").prop('checked', true);
	    </c:if>
	    <c:if test = "${report.reportName == 'CONTRACT_EMPLOYEE_PAYROLL'}">
		   $("#contractEmployeePayroll").prop('checked', true);
	    </c:if>
	    <c:if test = "${report.reportName == 'PETITION_REPORT'}">
		   $("#petitionReport").prop('checked', true);
	    </c:if>
	    <c:if test = "${report.reportName == 'PETITION_ATTORNEY_REPORT'}">
		   $("#petitionAttorneyReport").prop('checked', true);
	    </c:if>
	    
	    <c:if test = "${report.reportName == 'LCA_REPORT'}">
		   $("#lcaReport").prop('checked', true);
	    </c:if>
	    
	    <c:if test = "${report.reportName == 'USAGE_REPORT'}">
		   $("#usageReport").prop('checked', true);
	    </c:if>
	     
	     
	     
</c:forEach>
 


</script>
  
  
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