<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script type="text/javascript">
var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
</script>
<section class="content-wraper">

    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Reports Dashboard</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Reports Dashboard</div>
    </section>
    
    <div class="reportNewDash clearfix">
    
         <div class="reqListBtn clearfix mb10">
              <span class="GView rAct" title="Graphical View"><i class="fa fa-th-large"></i></span>
              <span class="LView" title="List View"><i class="fa fa-list"></i></span>
              <div class="repSearch"><input class="innSeaFld" id="repSearchaa" onKeyUp="myManagersFunction();" type="text" placeholder="Search"></div>
          </div>
          
          <div class="leaveConfigure newRepGrid clerfix">
          <sec:authorize access="!hasRole('ROLE_ENROLL_ORG')">
             
	      <sec:authorize access="hasAnyRole('ROLE_RECRUITERS_REPORT','ROLE_VENDORS_REPORT','ROLE_CLIENT_REPORT','ROLE_APPLICANT_DECLINE','ROLE_APPLICANT_BACKOUT','ROLE_JOBS_PIPELINE','ROLE_APPLICANT_SOURCE','ROLE_TIME_TO_FILL','ROLE_APPLICANT_RECEIVED','ROLE_JOBS_STAGES','ROLE_MY_PERFORMANCE_REPORT')">
            
             <div class="col-md-4 reportNewCta">
                 
			<div class="dropdown pull-right report-dropdown">
				<span class="dropdown-toggle btn btn-sm btn-default repDropBtn" type="button" id="menu1" data-toggle="dropdown"><span class="caret cursorPointer"></span></span>
				<ul class="dropdown-menu" role="menu" aria-labelledby="menu1">
				<sec:authorize access="hasRole('ROLE_APPLICANTS_JOINED_REPORT')">
				<li><a href='${appPath}/offerAcceptanceRate'>Offer Acceptance Rate</a></li>
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_APPLICANTS_JOINED_REPORT')">
				<li><a href='${SourceLeadWeb_sourceleadUrl}/applicantsjoinedlist'>Applicants Joined Report</a></li>
				</sec:authorize>
				 <sec:authorize access="hasRole('ROLE_RECRUITERS_REPORT')">
				 <li><a href='${SourceLeadWeb_sourceleadUrl}/recruitersReports'>Recruiter(s) Report</a></li>
				 </sec:authorize>
				 <sec:authorize access="hasRole('ROLE_MY_PERFORMANCE_REPORT')">
				 <li><a href='${SourceLeadWeb_sourceleadUrl}/myPerformanceReports'>My Performance Report</a></li>
				 </sec:authorize>
				 <sec:authorize access="hasRole('ROLE_VENDORS_REPORT')">
				 <li><a href="${SourceLeadWeb_sourceleadUrl}/vendorsReports">Vendor(s) Report</a></li>
				 </sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_CLIENT_REPORT')">
				<li><a href="${SourceLeadWeb_sourceleadUrl}/perfomanceClientReports">Client(s) Report</a></li>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_APPLICANT_DECLINE')">
				<li><a href="${SourceLeadWeb_sourceleadUrl}/applicantDeclineReports">Applicant(s) Decline</a></li>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_APPLICANT_BACKOUT')">
				<li><a href="${SourceLeadWeb_sourceleadUrl}/applicantsBackoutReport">Applicant(s) Backout</a></li>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_JOBS_PIPELINE')">
				<li><a href="${SourceLeadWeb_sourceleadUrl}/jobPipelineReports">Job(s) Pipeline</a></li>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_APPLICANT_SOURCE')">
				<li><a href="${SourceLeadWeb_sourceleadUrl}/applicantBySource">Applicant(s) by Source</a></li>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_TIME_TO_FILL')">
				<li><a href="${SourceLeadWeb_sourceleadUrl}/timeToFill">Time to Fill</a></li>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_APPLICANT_RECEIVED')">
				<li><a href="${SourceLeadWeb_sourceleadUrl}/applicantsReceivedListForReports">Applicant(s) Received</a></li>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_JOBS_STAGES')">
				<li><a href="${SourceLeadWeb_sourceleadUrl}/requirementStagesListForReports">Job(s) Stage</a></li>
				</sec:authorize>
				
				<li><a href="${SourceLeadWeb_sourceleadUrl}/jobsPerformanceReport">Jobs Report</a></li>
				
				</ul>
			</div>
                 <!-- Dropdown -->
                  <a href="${SourceLeadWeb_sourceleadUrl}/performanceReportList" class="block">
 				<div class="clearfix text-center mb10"><i class="perReport"></i></div>
                  <div class="clearfix text-center"> <h4>Performance Report</h4></div>
                   </a>
              </div>
             
           </sec:authorize>   
              
              
              
         <sec:authorize access="hasAnyRole('ROLE_BILLING_REPORTS','ROLE_A_R_AGING','ROLE_A_P_AGING','ROLE_MISSING_INVOICE','ROLE_MISSING_BILL','ROLE_PAID_BILL','ROLE_RECEIVED_INVOICES')">     
              
              <div class="col-md-4 reportNewCta">
                 <!-- DropDown -->
			<div class="dropdown pull-right report-dropdown">
				<span class="dropdown-toggle btn btn-sm btn-default repDropBtn" type="button" id="menu2" data-toggle="dropdown"><span class="caret cursorPointer"></span></span>
				<ul class="dropdown-menu" role="menu" aria-labelledby="menu2">
				<!-- <li><a href="#">Invoice</a></li> -->
				
				<sec:authorize access="hasRole('ROLE_BILLING_REPORTS')">
				<li><a href="${SourceLeadWeb_sourceleadUrl}/billingReports">Finance</a></li>
				</sec:authorize>
				<!-- <li><a href="#">Invoice Report By Customer / Project</a></li>
				<li><a href="#">Bill Report By Customer / Project</a></li> -->
				<sec:authorize access="hasRole('ROLE_A_R_AGING')">
				<%-- <c:if test="${SourceLeadWeb_ats == 'Y'}"> --%>
				<li><a href='${SourceLeadWeb_sourceleadUrl}/arAgingReport'>A/R Aging</a></li>
				<%-- </c:if> --%>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_A_P_AGING')">
				<%-- <c:if test="${SourceLeadWeb_vms == 'Y'}"> --%>
				<li><a href='${SourceLeadWeb_sourceleadUrl}/apAgingReport'>A/P Aging</a></li>
<%-- 				</c:if> --%>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_MISSING_INVOICE')">
					<%-- <c:if test="${SourceLeadWeb_ats == 'Y'}"> --%>
					<li><a href='${SourceLeadWeb_sourceleadUrl}/missingInvoiceReport'>Missing Invoice(s)</a></li>
					<%-- </c:if> --%>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_RECEIVED_INVOICES')">
				<%-- <c:if test="${SourceLeadWeb_ats == 'Y'}"> --%>
			     <li><a href='${SourceLeadWeb_sourceleadUrl}/receivedInvoiceReport'>Received Invoice(s)</a></li>    
			     <%-- </c:if> --%>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_MISSING_BILL')">
				<%-- <c:if test="${SourceLeadWeb_vms == 'Y'}"> --%>
				<li><a href='${SourceLeadWeb_sourceleadUrl}/missingBillReport'>Missing Bill(s)</a></li>
				<%-- </c:if> --%>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_PAID_BILL')">
					<%-- <c:if test="${SourceLeadWeb_vms == 'Y'}"> --%>
				    <li><a href='${SourceLeadWeb_sourceleadUrl}/paidBillReport'>Paid Bill(s)</a></li>
				  <%--   </c:if> --%>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_MARGIN_SHEET_REPORT')">
			<%-- 	<c:if test="${SourceLeadWeb_vms == 'Y'}"> --%>
				<li><a href='${SourceLeadWeb_sourceleadUrl}/marginSheetReports'>MarginSheetReport</a></li>
			<%-- 	</c:if> --%>
				</sec:authorize>
				
			   <li><a href='${SourceLeadWeb_sourceleadUrl}/assetmanagementGrowth'>Asset Growth</a></li>
				
				
			    
				<!-- <li><a href="#">Profit &amp; Loss</a></li>
				<li><a href="#">Balance Sheet</a></li>
				<li><a href="#">Client Balance</a></li>
				<li><a href="#">Unpaid Bills</a></li>
				<li><a href="#">Paid Bills</a></li>
				<li><a href="#">Bad debt</a></li>
				<li><a href="#">Cashflow projection</a></li>
				<li><a href="#">Cashflow Requirement</a></li> 
				<li><a href='${SourceLeadWeb_sourceleadUrl}/missingInvoiceReport'>Missing Invoice</a></li>
				<li><a href='${SourceLeadWeb_sourceleadUrl}/missingBillReport'>Missing Bill</a></li>
				<li><a href="#">Associate Billed vs Paid Report</a></li> -->
				
				</ul>
			</div>
                 <!-- Dropdown -->
                 <a href="${SourceLeadWeb_sourceleadUrl}/financialReportsList" class="block">
                  <div class="clearfix text-center mb10"><i class="finReport"></i></div>
                  <div class="clearfix text-center"><h4>Financial Report</h4></div></a>
                  </div>
                 
     </sec:authorize>             
                  
                  
    <sec:authorize access="hasAnyRole('ROLE_DAYWISE_TIMESHEET_REPORT','ROLE_TIMESHEET_REPORT','ROLE_EXPENSES_REPORT','ROLE_MISSING_TIMESHEETS','ROLE_PENDING_FOR_APPROVAL','ROLE_EMPLOYEMENT_VERIFICATION_REPORT')">              

  <div class="col-md-4 reportNewCta">
                 
                 <!-- DropDown -->
			<div class="dropdown pull-right report-dropdown">
				<span class="dropdown-toggle btn btn-sm btn-default repDropBtn" type="button" id="menu3" data-toggle="dropdown"><span class="caret cursorPointer"></span></span>
				<ul class="dropdown-menu" role="menu" aria-labelledby="menu3">

                <li><a href="${SourceLeadWeb_sourceleadUrl}/assetsReport">Assigned Assets History</a></li>

				<sec:authorize access="hasRole('ROLE_DAYWISE_TIMESHEET_REPORT')">
				<li><a href="${SourceLeadWeb_sourceleadUrl}/daywiseTimeSheetReports">Daywise Timesheet Report</a></li>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_TIMESHEET_REPORT')">
				  <li><a href="${SourceLeadWeb_sourceleadUrl}/timeSheetReports">Timesheet Monthly Report</a></li>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_EXPENSES_REPORT')">
				<li><a href="${SourceLeadWeb_sourceleadUrl}/expensesReportsList">Expense(s)</a></li>
				</sec:authorize>
				
				 <sec:authorize access="hasRole('ROLE_PENDING_FOR_APPROVAL')">
				<li><a href="${SourceLeadWeb_sourceleadUrl}/pendingForExpenseApprovalReport">Pending for Approval Expenses</a></li>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_MISSING_TIMESHEETS')">
                <li><a href="${SourceLeadWeb_sourceleadUrl}/missingTimesheetsReport">Missing Timesheet(s)</a></li>
                </sec:authorize>
                
                <sec:authorize access="hasRole('ROLE_PENDING_FOR_APPROVAL')">
				<li><a href="${SourceLeadWeb_sourceleadUrl}/pendingForApprovalReport">Pending for Approval Timesheets</a></li>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_EMPLOYEMENT_VERIFICATION_REPORT')">
				<li><a href="${SourceLeadWeb_sourceleadUrl}/employementVerificationReport">Employement Verification Report</a></li>
				</sec:authorize>
        
				<li><a href="${SourceLeadWeb_sourceleadUrl}/daywiseLeaveReport">Daywise Leave Report</a></li>
				<li><a href="${SourceLeadWeb_sourceleadUrl}/leaveReport">Leave Report</a></li>
				<li><a href="${SourceLeadWeb_sourceleadUrl}/leaveBalancesReport">Leave Balance Report</a></li>


				<li><a href="${SourceLeadWeb_sourceleadUrl}/topLeaveTakenReports">Top Leave Taken Report</a></li>
	
				<!-- <li><a href="#">Employment Verficication</a></li>
				<li><a href="#">Wage and Tax</a></li>
				<li><a href="#">Associate Onboard</a></li>
				<li><a href="#">Associate Offboard</a></li> -->
						

				</ul>
			</div>
                 <!-- Dropdown -->
                  <a href="${SourceLeadWeb_sourceleadUrl}/hrReportsList" class="block">
                  <div class="clearfix text-center mb10"><i class="hrReport"></i></div>
                  <div class="clearfix text-center"><h4>HR Report</h4></div> </a>
     </div>
    
     </sec:authorize>
     
     
     
     
     
     <sec:authorize access="hasAnyRole('ROLE_ASSOCIATE_ROI','ROLE_ALL_ASSOCIATE_ROI','ROLE_CAMPAIGN_REPORTS')">   
         <div class="col-md-4 reportNewCta">
                  <!-- DropDown -->
			<div class="dropdown pull-right report-dropdown">
				<span class="dropdown-toggle btn btn-sm btn-default repDropBtn" type="button" id="menu4" data-toggle="dropdown"><span class="caret cursorPointer"></span></span>
				<ul class="dropdown-menu" role="menu" aria-labelledby="menu3">
					<sec:authorize access="hasRole('ROLE_ASSOCIATE_ROI')">
					<li><a href="${SourceLeadWeb_sourceleadUrl}/payrollReport">Associate(s) ROI</a></li>
					</sec:authorize>
					
					<sec:authorize access="hasRole('ROLE_ALL_ASSOCIATE_ROI')">
					<li><a href="${SourceLeadWeb_sourceleadUrl}/payrollAllBalanceReport">All Associates ROI</a></li>
					</sec:authorize>
					<sec:authorize access="hasRole('ROLE_CAMPAIGN_REPORTS')">
					<li><a href='${SourceLeadWeb_sourceleadUrl}/campaignStats'>Campaign(s) Report</a></li>
					</sec:authorize>
					<sec:authorize access="hasRole('ROLE_ASSOCIATE_JOINED_REPORT')">
				<li><a href='${SourceLeadWeb_sourceleadUrl}/associateJoineReports'>Associate Joined Report</a></li>
				</sec:authorize>  
				
				<li><a href='${SourceLeadWeb_sourceleadUrl}/associateClockInSummaryReport'>Associate Clock-In Summary Report</a></li>
				
				<li><a href='${appPath}/availableAssociatesReport'>Available Associate Report</a></li> 

					<!--<li><a href="#">Bank Report</a></li>
					 <li><a href="#">Performance</a></li>
					<li><a href="#">Payrol</a></li>
					<li><a href="#">360 View</a></li>
					<li><a href="#">Pay History</a></li>
					<li><a href="#">Bill Histroy</a></li> -->
					<li><a href='${SourceLeadWeb_sourceleadUrl}/loansAndAdvancesReport'>Loans And Advances Report</a></li> 
				</ul>
			</div>
                 <!-- Dropdown -->
                  
                  <a href="${SourceLeadWeb_sourceleadUrl}/associateReportsList" class="block">
                  <div class="clearfix text-center mb10"><i class="assReport"></i></div>
                  <div class="clearfix text-center"><h4>Associate Report</h4></div> </a> 
                      </div>
                    
              </sec:authorize>    
                 
       </sec:authorize> 
       
                       
          
          <sec:authorize access="hasAnyRole('ROLE_USAGE_REPORT')">   
         <div class="col-md-4 reportNewCta">
                  <!-- DropDown -->
			<div class="dropdown pull-right report-dropdown">
				<span class="dropdown-toggle btn btn-sm btn-default repDropBtn" type="button" id="menu4" data-toggle="dropdown"><span class="caret cursorPointer"></span></span>
				<ul class="dropdown-menu" role="menu" aria-labelledby="menu3">
			     <sec:authorize access="hasRole('ROLE_USAGE_REPORT')">
				<li><a href="${SourceLeadWeb_sourceleadUrl}/userUsageReport">Usage Report(s)</a></li>
				</sec:authorize>
					
				</ul>
			</div>
                 <!-- Dropdown -->
                  <a href="${SourceLeadWeb_sourceleadUrl}/userUsageReport" class="block">
                  <div class="clearfix text-center mb10"><i class="useReport"></i></div>
                  <div class="clearfix text-center"><h4>Users Usage Report</h4></div> </a>
                      </div>
                     
              </sec:authorize>
              
              
              
             <%--  <sec:authorize access="hasAnyRole('ROLE_USAGE_REPORT')">    --%>
      <c:if test="${SourceLeadWeb_country == 'United States'}">
       <div class="col-md-4 reportNewCta">
                  <!-- DropDown payReport -->
			<div class="dropdown pull-right report-dropdown">
				<span class="dropdown-toggle btn btn-sm btn-default repDropBtn" type="button" id="menu4" data-toggle="dropdown"><span class="caret cursorPointer"></span></span>
				<ul class="dropdown-menu" role="menu" aria-labelledby="menu3">
			     <sec:authorize access="hasRole('ROLE_USAGE_REPORT')">
				<li><a href="${SourceLeadWeb_pmsUrl}/payrollRegisterReport">Payroll Register</a></li>
				<li><a href="${SourceLeadWeb_pmsUrl}/payrollComparison">Comparsion</a></li>
				<li><a href="${SourceLeadWeb_pmsUrl}/payrollListSummaryReport">Payroll Summary</a></li>
				<li><a href="${SourceLeadWeb_pmsUrl}/checkRegisterReport">Check Register</a></li>
				<li><a href="${SourceLeadWeb_pmsUrl}/associateWithholding">Associate Withholding</a></li>
				<li><a href="${SourceLeadWeb_pmsUrl}/payrollTaxesAndWagesReport">Taxable Wages Report</a></li>
				<!-- <li><a href="companysnapshot.html">Employee Hours Paid</a></li>
				<li><a href="companysnapshot.html">Taxable Wages Report</a></li> -->
				<li><a href="${SourceLeadWeb_pmsUrl}/deductionReport">Deduction Reports</a></li>
				<li><a href="${SourceLeadWeb_pmsUrl}/contributionReport">Contribution Reports</a></li>

				<li><a href="${SourceLeadWeb_pmsUrl}/usDeductionSummaryReport" ><!-- Us -->Deduction Summary Report</a></li>
				<li><a href="${SourceLeadWeb_pmsUrl}/usContributionSummaryReport" ><!-- Us -->Contribution Summary Reports</a></li>

				<!-- <li><a href="companysnapshot.html">Tax Liability Report</a></li>
				<li><a href="companysnapshot.html">Payroll Timesheet Report</a></li> -->
				<li><a href="${SourceLeadWeb_pmsUrl}/directDepositReport">Direct Deposit</a></li>
				<li><a href="${SourceLeadWeb_pmsUrl}/usEmployeePayrollReport">Employee Payroll</a></li>

				<li><a href="${SourceLeadWeb_pmsUrl}/contractEmployeePayreport">Contract Employee Payroll</a></li>

				</sec:authorize>
				</ul>
			</div>
                 <!-- Dropdown -->
                  <a href="${SourceLeadWeb_pmsUrl}/usPayrollReport" class="block">
                  <div class="clearfix text-center mb10"><i class="payReport"></i></div>
                  <div class="clearfix text-center"><h4>US Payroll Reports</h4></div> </a>
                  </div> 
                  
      </c:if>
                     
          <%--     </sec:authorize>  --%>
              
                <div class="col-md-4 reportNewCta">
                  <!-- DropDown Petition Report -->
			<div class="dropdown pull-right report-dropdown">
				<span class="dropdown-toggle btn btn-sm btn-default repDropBtn" type="button" id="menu4" data-toggle="dropdown"><span class="caret cursorPointer"></span></span>
				<ul class="dropdown-menu" role="menu" aria-labelledby="menu3">
					<li><a href="${SourceLeadWeb_vmsUrl}/petitionReport">Petition Report</a></li>
					<li><a href="${SourceLeadWeb_vmsUrl}/petitionAttorneyReport">Petition Attorney Report</a></li>
					<li><a href="${SourceLeadWeb_vmsUrl}/lcaReport">LCA Report</a></li>
				</ul>
			</div>
                 <!-- Dropdown -->
                  <a href="${SourceLeadWeb_sourceleadUrl}/immigrationReportsList" class="block">
                  <div class="clearfix text-center mb10"><i class="petitionReport"></i></div>
                  <div class="clearfix text-center"><h4>Immigration Reports</h4></div> </a>

                  </div> 

		<div class="col-md-4 reportNewCta">
                  <!-- DropDown -->
			<div class="dropdown pull-right report-dropdown">
				<span class="dropdown-toggle btn btn-sm btn-default repDropBtn" type="button" id="menu4" data-toggle="dropdown" aria-expanded="false"><span class="caret cursorPointer"></span></span>
				<ul class="dropdown-menu" role="menu" aria-labelledby="menu3">
			     <li><a href="${SourceLeadWeb_sourceleadUrl}/projectReport">Project Report</a></li>
				<li><a href="${SourceLeadWeb_sourceleadUrl}/employeeTasksReport">Employee Tasks</a></li>
				<li><a href="${SourceLeadWeb_sourceleadUrl}/overdueTaskReport">Over Due Report</a></li>
					
				</ul>
			</div>
                 <!-- Dropdown -->
                  <a href="${SourceLeadWeb_sourceleadUrl}/projectReportList" class="block">
                  <div class="clearfix text-center mb10"><i class="proReport"></i></div>
                  <div class="clearfix text-center"><h4>Project Reports</h4></div> </a>
        </div>
           
           
           <c:if test="${SourceLeadWeb_country == 'India'}">
           <div class="col-md-4 reportNewCta">
                  <!-- DropDown -->
			<div class="dropdown pull-right report-dropdown">
				<span class="dropdown-toggle btn btn-sm btn-default repDropBtn" type="button" id="menu4" data-toggle="dropdown" aria-expanded="false"><span class="caret cursorPointer"></span></span>
				<ul class="dropdown-menu" role="menu" aria-labelledby="menu3">
			     <li><a href="${SourceLeadWeb_sourceleadUrl}/indPayrollRegisterReport">Payroll Register</a></li>
				 <li><a href="${SourceLeadWeb_sourceleadUrl}/indPayrollBankTransferReport">Bank Transfer</a></li>
				 <li><a href="${SourceLeadWeb_sourceleadUrl}/indPayrollYTDSummaryReport">YTD Summary</a></li>
				 <li><a href="${SourceLeadWeb_sourceleadUrl}/indPayrollAnnualTaxReport">Annual Tax</a></li>
<%-- 				 <li><a href="${SourceLeadWeb_sourceleadUrl}/indPayrollLoanReport">Loan</a></li> --%>
				 <li><a href="${SourceLeadWeb_sourceleadUrl}/indPayrollESISummaryReport">ESI</a></li>
				 <li><a href="${SourceLeadWeb_sourceleadUrl}/indPayrollPFSummaryReport">PF</a></li>
				 <li><a href="${SourceLeadWeb_sourceleadUrl}/indPayrollSalaryRegisterReport">Salary Register</a></li>
					
				</ul>
			</div>
                 <!-- Dropdown -->
                  <a href="${SourceLeadWeb_sourceleadUrl}/indPayrollReportList" class="block">
                  <div class="clearfix text-center mb10"><i class="payReport"></i></div>
                  <div class="clearfix text-center"><h4>India Payroll Reports</h4></div> </a>
           </div>
        </c:if>


                  </div>  

                  
      
      
      <!--   List View   -->
      
     <div class="newRepList clearfix">
            <div class="newRepListCta">
                <h2><a href="javascript:void(0)">Performance Reports</a></h2>
                <ul class="repSearchList">
                
                <sec:authorize access="hasRole('ROLE_APPLICANTS_JOINED_REPORT')">
				<li><a href='${appPath}/offerAcceptanceRate'>Offer Acceptance Rate</a></li>
				</sec:authorize>
                
                <sec:authorize access="hasRole('ROLE_APPLICANTS_JOINED_REPORT')">
				<li><a href='${SourceLeadWeb_sourceleadUrl}/applicantsjoinedlist'>Applicants Joined Report</a></li>
				</sec:authorize>
				
 				<sec:authorize access="hasRole('ROLE_RECRUITERS_REPORT')">
				 <li><a href='${SourceLeadWeb_sourceleadUrl}/recruitersReports'>Recruiter(s) Report</a></li>
				 </sec:authorize>
				 
				  <sec:authorize access="hasRole('ROLE_MY_PERFORMANCE_REPORT')">
				 <li><a href='${SourceLeadWeb_sourceleadUrl}/myPerformanceReports'>My Performance Report</a></li>
				 </sec:authorize>
				
				 <sec:authorize access="hasRole('ROLE_VENDORS_REPORT')">
				 <li><a href="${SourceLeadWeb_sourceleadUrl}/vendorsReports">Vendor(s) Report</a></li>
				 </sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_CLIENT_REPORT')">
				<li><a href="${SourceLeadWeb_sourceleadUrl}/perfomanceClientReports">Client(s) Report</a></li>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_APPLICANT_DECLINE')">
				<li><a href="${SourceLeadWeb_sourceleadUrl}/applicantDeclineReports">Applicant(s) Decline</a></li>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_APPLICANT_BACKOUT')">
				<li><a href="${SourceLeadWeb_sourceleadUrl}/applicantsBackoutReport">Applicant(s) Backout</a></li>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_JOBS_PIPELINE')">
				<li><a href="${SourceLeadWeb_sourceleadUrl}/jobPipelineReports">Job(s) Pipeline</a></li>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_APPLICANT_SOURCE')">
				<li><a href="${SourceLeadWeb_sourceleadUrl}/applicantBySource">Applicant(s) by Source</a></li>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_TIME_TO_FILL')">
				<li><a href="${SourceLeadWeb_sourceleadUrl}/timeToFill">Time to Fill</a></li>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_APPLICANT_RECEIVED')">
				<li><a href="${SourceLeadWeb_sourceleadUrl}/applicantsReceivedListForReports">Applicant(s) Received</a></li>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_JOBS_STAGES')">
				<li><a href="${SourceLeadWeb_sourceleadUrl}/requirementStagesListForReports">Job(s) Stage</a></li>
				</sec:authorize>
				
				<li><a href="${SourceLeadWeb_sourceleadUrl}/jobsPerformanceReport">Jobs Report</a></li>
				
                </ul>
                <div class="noDate noDateSm" style="display: none;"></div>
            </div>
            <div class="newRepListCta">
                <h2><a href="javascript:void(0)">Financial Reports</a></h2>
                <ul class="repSearchList">
                    <sec:authorize access="hasRole('ROLE_BILLING_REPORTS')">
				<li><a href="${SourceLeadWeb_sourceleadUrl}/billingReports">Finance</a></li>
				</sec:authorize>
				<!-- <li><a href="#">Invoice Report By Customer / Project</a></li>
				<li><a href="#">Bill Report By Customer / Project</a></li> -->
				<sec:authorize access="hasRole('ROLE_A_R_AGING')">
				<li><a href='${SourceLeadWeb_sourceleadUrl}/arAgingReport'>A/R Aging</a></li>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_A_P_AGING')">
				<li><a href='${SourceLeadWeb_sourceleadUrl}/apAgingReport'>A/P Aging</a></li>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_MISSING_INVOICE')">
					<li><a href='${SourceLeadWeb_sourceleadUrl}/missingInvoiceReport'>Missing Invoice(s)</a></li>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_RECEIVED_INVOICES')">
			     <li><a href='${SourceLeadWeb_sourceleadUrl}/receivedInvoiceReport'>Received Invoice(s)</a></li>    
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_MISSING_BILL')">
				<li><a href='${SourceLeadWeb_sourceleadUrl}/missingBillReport'>Missing Bill(s)</a></li>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_PAID_BILL')">
				    <li><a href='${SourceLeadWeb_sourceleadUrl}/paidBillReport'>Paid Bill(s)</a></li>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_MARGIN_SHEET_REPORT')">
				<li><a href='${SourceLeadWeb_sourceleadUrl}/marginSheetReports'>MarginSheetReport</a></li>
				</sec:authorize>
				
				<li><a href='${SourceLeadWeb_sourceleadUrl}/assetmanagementGrowth'>Asset Growth</a></li>
				
                </ul>
                <div class="noDate noDateSm" style="display: none;"></div>
            </div>
            
            
            <div class="newRepListCta">
                <h2><a href="javascript:void(0)">HR Reports</a></h2>
                <ul class="repSearchList">
                
                <li><a href="${SourceLeadWeb_sourceleadUrl}/projectReport">Project Report</a></li>
                
                <sec:authorize access="hasRole('ROLE_DAYWISE_TIMESHEET_REPORT')">
					<li><a href="${SourceLeadWeb_sourceleadUrl}/daywiseTimeSheetReports">Daywise Timesheet Report</a></li>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_TIMESHEET_REPORT')">
				  <li><a href="${SourceLeadWeb_sourceleadUrl}/timeSheetReports">Timesheet Monthly Report</a></li>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_EXPENSES_REPORT')">
				<li><a href="${SourceLeadWeb_sourceleadUrl}/expensesReportsList">Expense(s)</a></li>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_MISSING_TIMESHEETS')">
                <li><a href="${SourceLeadWeb_sourceleadUrl}/missingTimesheetsReport">Missing Timesheet(s)</a></li>
                </sec:authorize>
                
                <sec:authorize access="hasRole('ROLE_PENDING_FOR_APPROVAL')">
				<li><a href="${SourceLeadWeb_sourceleadUrl}/pendingForApprovalReport">Pending for Approval</a></li>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_EMPLOYEMENT_VERIFICATION_REPORT')">
				<li><a href="${SourceLeadWeb_sourceleadUrl}/employementVerificationReport">Employement Verification Report</a></li>
				</sec:authorize>

				<li><a href="${SourceLeadWeb_sourceleadUrl}/daywiseLeaveReport">Daywise Leave Report</a></li>
				<li><a href="${SourceLeadWeb_sourceleadUrl}/leaveReport">Leave Report</a></li>
				<li><a href="${SourceLeadWeb_sourceleadUrl}/leaveBalancesReport">Leave Balance Report</a></li>
            	<li><a href="${SourceLeadWeb_sourceleadUrl}/topLeaveTakenReports">Top Leave Taken Report</a></li>


                </ul>
                <div class="noDate noDateSm" style="display: none;"></div>
            </div> 
            
            <div class="clearfix mt mb"></div>
            <div class="newRepListCta">
                <h2><a href="javascript:void(0)">Associate Report</a></h2>
                <ul class="repSearchList">
					<sec:authorize access="hasRole('ROLE_ASSOCIATE_ROI')">
					<li><a href="${SourceLeadWeb_sourceleadUrl}/payrollReport">Associate(s) ROI</a></li>
					</sec:authorize>
					
					<sec:authorize access="hasRole('ROLE_ALL_ASSOCIATE_ROI')">
					<li><a href="${SourceLeadWeb_sourceleadUrl}/payrollAllBalanceReport">All Associates ROI</a></li>
					</sec:authorize>
					<sec:authorize access="hasRole('ROLE_CAMPAIGN_REPORTS')">
					<li><a href='${SourceLeadWeb_sourceleadUrl}/campaignStats'>Campaign(s) Report</a></li>
					</sec:authorize>  
					<sec:authorize access="hasRole('ROLE_ASSOCIATE_JOINED_REPORT')">
				<li><a href='${SourceLeadWeb_sourceleadUrl}/associateJoineReports'>Associate Joined Report</a></li>
				</sec:authorize>    

				<li><a href='${SourceLeadWeb_sourceleadUrl}/associateClockInSummaryReport'>Associate Clock-In Summary Report</a></li>

				<li><a href='${appPath}/availableAssociatesReport'>Available Associate Report</a></li>           
                <li><a href='${SourceLeadWeb_sourceleadUrl}/loansAndAdvancesReport'>Loans And Advances Report</a></li>
                </ul>
                <div class="noDate noDateSm" style="display: none;"></div>
            </div> 
            
			
			
			<div class="newRepListCta">
                <h2><a href="javascript:void(0)">Users Usage Reports</a></h2>
                <ul class="repSearchList">
                <sec:authorize access="hasRole('ROLE_USAGE_REPORT')">
				<li><a href="${SourceLeadWeb_sourceleadUrl}/userUsageReport">Usage Report(s)</a></li>
				</sec:authorize>
                </ul>
                <div class="noDate noDateSm" style="display: none;"></div>
            </div>
            
            <div class="newRepListCta">
                <h2><a href="javascript:void(0)">Project Reports</a></h2>
                <ul class="repSearchList">
                <li><a href="${SourceLeadWeb_sourceleadUrl}/projectReport">Project Report</a></li>
				<li><a href="${SourceLeadWeb_sourceleadUrl}/employeeTasksReport">Employee Tasks</a></li>
				<li><a href="${SourceLeadWeb_sourceleadUrl}/overdueTaskReport">Over Due Report</a></li>
                </ul>
                <div class="noDate noDateSm" style="display: none;"></div>
            </div>
            
            <div class="clearfix mt mb"></div>
            
            <c:if test="${SourceLeadWeb_country == 'India'}">
            <div class="newRepListCta">
                <h2><a href="javascript:void(0)">India Payroll Reports</a></h2>
                <ul class="repSearchList">
                <li><a href="${SourceLeadWeb_sourceleadUrl}/indPayrollRegisterReport">Payroll Register</a></li>
				 <li><a href="${SourceLeadWeb_sourceleadUrl}/indPayrollBankTransferReport">Bank Transfer</a></li>
				 <li><a href="${SourceLeadWeb_sourceleadUrl}/indPayrollYTDSummaryReport">YTD Summary</a></li>
				 <li><a href="${SourceLeadWeb_sourceleadUrl}/indPayrollAnnualTaxReport">Annual Tax</a></li>
<%-- 				 <li><a href="${SourceLeadWeb_sourceleadUrl}/indPayrollLoanReport">Loan</a></li> --%>
				 <li><a href="${SourceLeadWeb_sourceleadUrl}/indPayrollESISummaryReport">ESI</a></li>
				 <li><a href="${SourceLeadWeb_sourceleadUrl}/indPayrollPFSummaryReport">PF</a></li>
				 <li><a href="${SourceLeadWeb_sourceleadUrl}/indPayrollSalaryRegisterReport">Salary Register</a></li>
                </ul>
                <div class="noDate noDateSm" style="display: none;"></div>
            </div>
            </c:if>
            
            
            <c:if test="${SourceLeadWeb_country == 'United States'}">
            	<div class="newRepListCta">
                <h2><a href="javascript:void(0)">US Payroll Reports</a></h2>
	                <ul class="repSearchList">
		                <sec:authorize access="hasRole('ROLE_USAGE_REPORT')">
							<li><a href="${SourceLeadWeb_pmsUrl}/payrollRegisterReport">Payroll Register</a></li>
						</sec:authorize>
						<sec:authorize access="hasRole('ROLE_USAGE_REPORT')">
							<li><a href="${SourceLeadWeb_pmsUrl}/payrollComparison">Comparison</a></li>
						</sec:authorize>
						<sec:authorize access="hasRole('ROLE_USAGE_REPORT')">
							<li><a href="${SourceLeadWeb_pmsUrl}/payrollListSummaryReport">Payroll Summary</a></li>
						</sec:authorize>
						<sec:authorize access="hasRole('ROLE_USAGE_REPORT')">
							<li><a href="${SourceLeadWeb_pmsUrl}/checkRegisterReport">Check Register</a></li>
						</sec:authorize>
						<sec:authorize access="hasRole('ROLE_USAGE_REPORT')">
							<li><a href="${SourceLeadWeb_pmsUrl}/associateWithholding">Associate Withholding</a></li>
						</sec:authorize>
						<sec:authorize access="hasRole('ROLE_USAGE_REPORT')">
							<li><a href="${SourceLeadWeb_pmsUrl}/payrollTaxesAndWagesReport">Taxable Wages Report</a></li>
						</sec:authorize>
						<sec:authorize access="hasRole('ROLE_USAGE_REPORT')">
							<li><a href="${SourceLeadWeb_pmsUrl}/deductionReport">Deduction Reports</a></li>
						</sec:authorize>
						<sec:authorize access="hasRole('ROLE_USAGE_REPORT')">
							<li><a href="${SourceLeadWeb_pmsUrl}/contributionReport">Contribution Reports</a></li>
						</sec:authorize>

						<li><a href="${SourceLeadWeb_pmsUrl}/usDeductionSummaryReport" ><!-- Us -->Deduction Summary Report</a></li>
				        <li><a href="${SourceLeadWeb_pmsUrl}/usContributionSummaryReport" ><!-- Us -->Contribution Summary Reports</a></li>

						<sec:authorize access="hasRole('ROLE_USAGE_REPORT')">
							<li><a href="${SourceLeadWeb_pmsUrl}/directDepositReport">Direct Deposit</a></li>
						</sec:authorize>
						<sec:authorize access="hasRole('ROLE_USAGE_REPORT')">
							<li><a href="${SourceLeadWeb_pmsUrl}/usEmployeePayrollReport">Employee Payroll</a></li>
						</sec:authorize>

						<sec:authorize access="hasRole('ROLE_USAGE_REPORT')">
						<li><a href="${SourceLeadWeb_pmsUrl}/contractEmployeePayreport">Contract Employee Payroll</a></li>
						</sec:authorize>

	                </ul>
                <div class="noDate noDateSm" style="display: none;"></div>
            </div>
            </c:if>
            <div class="newRepListCta">
                <h2><a href="javascript:void(0)">Immigration Reports</a></h2>
                <ul class="repSearchList">
                    <li><a href="${SourceLeadWeb_vmsUrl}/petitionReport">Petition Report</a></li>
					<li><a href="${SourceLeadWeb_vmsUrl}/petitionAttorneyReport">Petition Attorney Report</a></li>
					<li><a href="${SourceLeadWeb_vmsUrl}/lcaReport">LCA Report</a></li>
                </ul>
                <div class="noDate noDateSm" style="display: none;"></div>
            </div>
            <div class="clearfix mt mb"></div>
                                   
        </div>
        
      
        
        </div>
        <!--  List View End  --> 
    
    </div>
  </section>

 <script>
    
$(".GView").click(function(){
    $(".newRepGrid").show();
    $(".newRepList").hide();
    $(".GView").addClass("rAct");
    $(".LView").removeClass("rAct");
    $(".repSearch").hide();
});
$(".LView").click(function(){
    $(".newRepGrid").hide();
    $(".newRepList").show();
    $(".LView").addClass("rAct");
    $(".GView").removeClass("rAct");
    $(".repSearch").show();
});        
    </script>

<script>
    
function myManagersFunction() {
    
    var input, filter, ul, li, a, i;
    input = document.getElementById("repSearchaa");
    filter = input.value.toUpperCase();
    /*ul = document.getElementById("repSearchList");*/
    $(".repSearchList").each(function(){
        var length = 0;
        var liList = $(this).find("li");
        for(var i=0; i < liList.length; i++){
            var li = liList[i];
            a = $(li).find('a').html().toUpperCase();
            if (a.indexOf(filter) > -1) {
                li.style.display = "";
                length++;
            } else {
                li.style.display = "none";
            }    
        }
        if(length == 0) {
            $(this).parent().find(".noDate").show();
        } else {
            $(this).parent().find(".noDate").hide();
        }
    });
}
    
</script>

<jsp:include page="../masterFooter.jsp" />