<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<section class="content-wraper">

    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Report(s) Dashboard</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Reports Dashboard</div>
    </section>
    
    <div class="reportNewDash clearfix">
    
         <div class="reqListBtn clearfix mb10">
              <span class="GView rAct" title="Graphical View"><i class="fa fa-th-large"></i></span>
              <span class="LView" title="List View"><i class="fa fa-list"></i></span>
              <div class="repSearch"><input class="innSeaFld" id="repSearchaa" onKeyUp="myManagersFunction();" type="text" placeholder="Search"></div>
          </div>
          
          <div class="leaveConfigure newRepGrid clerfix">
          <sec:authorize access="!hasRole('ROLE_ENROLL_ORG')">
             
      <sec:authorize access="hasAnyRole('ROLE_RECRUITERS_REPORT','ROLE_VENDORS_REPORT','ROLE_CLIENT_REPORT','ROLE_APPLICANT_DECLINE','ROLE_APPLICANT_BACKOUT','ROLE_JOBS_PIPELINE','ROLE_APPLICANT_SOURCE','ROLE_TIME_TO_FILL','ROLE_APPLICANT_RECEIVED','ROLE_JOBS_STAGES')">
             <div class="col-md-4 reportNewCta">
                 
			<div class="dropdown pull-right report-dropdown">
				<span class="dropdown-toggle btn btn-sm btn-default repDropBtn" type="button" id="menu1" data-toggle="dropdown"><span class="caret cursorPointer"></span></span>
				<ul class="dropdown-menu" role="menu" aria-labelledby="menu1">
				 <sec:authorize access="hasRole('ROLE_RECRUITERS_REPORT')">
				 <li><a href='${appPath}/recruitersReports'>Recruiters Reports</a></li>
				 </sec:authorize>
				
				 <sec:authorize access="hasRole('ROLE_VENDORS_REPORT')">
				 <li><a href="${appPath}/vendorsReports">Vendors Reports</a></li>
				 </sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_CLIENT_REPORT')">
				<li><a href="${appPath}/perfomanceClientReports">Client Reports</a></li>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_APPLICANT_DECLINE')">
				<li><a href="${appPath}/applicantDeclineReports">Applicants Decline</a></li>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_APPLICANT_BACKOUT')">
				<li><a href="${appPath}/applicantsBackoutReport">Applicants Backout</a></li>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_JOBS_PIPELINE')">
				<li><a href="${appPath}/jobPipelineReports">Jobs Pipeline</a></li>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_APPLICANT_SOURCE')">
				<li><a href="${appPath}/applicantBySource">Applicants Source</a></li>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_TIME_TO_FILL')">
				<li><a href="${appPath}/timeToFill">Time to Fill</a></li>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_APPLICANT_RECEIVED')">
				<li><a href="${appPath}/applicantsReceivedListForReports">Applicants Received</a></li>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_JOBS_STAGES')">
				<li><a href="${appPath}/requirementStagesListForReports">Jobs Stages</a></li>
				</sec:authorize>
				
				</ul>
			</div>
                 <!-- Dropdown -->
 				<div class="clearfix text-center mb10"><i class="perReport"></i></div>
                  <div class="clearfix text-center"> <h4><a href="#">Performance Report</a></h4></div>
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
				<li><a href="${appPath}/billingReports">Finance</a></li>
				</sec:authorize>
				<!-- <li><a href="#">Invoice Report By Customer / Project</a></li>
				<li><a href="#">Bill Report By Customer / Project</a></li> -->
				<sec:authorize access="hasRole('ROLE_A_R_AGING')">
				<c:if test="${SourceLeadWeb_ats == 'Y'}">
				<li><a href='${appPath}/arAgingReport'>A/R Aging</a></li>
				</c:if>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_A_P_AGING')">
				<c:if test="${SourceLeadWeb_vms == 'Y'}">
				<li><a href='${appPath}/apAgingReport'>A/P Aging</a></li>
				</c:if>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_MISSING_INVOICE')">
					<c:if test="${SourceLeadWeb_ats == 'Y'}">
					<li><a href='${appPath}/missingInvoiceReport'>Missing Invoices</a></li>
					</c:if>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_MISSING_BILL')">
				<c:if test="${SourceLeadWeb_vms == 'Y'}">
				<li><a href='${appPath}/missingBillReport'>Missing Bills</a></li>
				</c:if>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_PAID_BILL')">
					<c:if test="${SourceLeadWeb_vms == 'Y'}">
				    <li><a href='${appPath}/paidBillReport'>Paid Bill</a></li>
				    </c:if>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_RECEIVED_INVOICES')">
				<c:if test="${SourceLeadWeb_ats == 'Y'}">
			     <li><a href='${appPath}/receivedInvoiceReport'>Received Invoices</a></li>    
			     </c:if>
				</sec:authorize>
			    
				<!-- <li><a href="#">Profit &amp; Loss</a></li>
				<li><a href="#">Balance Sheet</a></li>
				<li><a href="#">Client Balance</a></li>
				<li><a href="#">Unpaid Bills</a></li>
				<li><a href="#">Paid Bills</a></li>
				<li><a href="#">Bad debt</a></li>
				<li><a href="#">Cashflow projection</a></li>
				<li><a href="#">Cashflow Requirement</a></li> 
				<li><a href='${appPath}/missingInvoiceReport'>Missing Invoice</a></li>
				<li><a href='${appPath}/missingBillReport'>Missing Bill</a></li>
				<li><a href="#">Associate Billed vs Paid Report</a></li> -->
				
				</ul>
			</div>
                 <!-- Dropdown -->
                 
                  <div class="clearfix text-center mb10"><i class="finReport"></i></div>
                  <div class="clearfix text-center"><h4><a href="#">Financial Report</a></h4></div>
                  </div>
     </sec:authorize>             
                  
                  
    <sec:authorize access="hasAnyRole('ROLE_DAYWISE_TIMESHEET_REPORT','ROLE_TIMESHEET_REPORT','ROLE_EXPENSES_REPORT','ROLE_MISSING_TIMESHEETS','ROLE_PENDING_FOR_APPROVAL','ROLE_EMPLOYEMENT_VERIFICATION_REPORT')">              
  <div class="col-md-4 reportNewCta">
                 
                 <!-- DropDown -->
			<div class="dropdown pull-right report-dropdown">
				<span class="dropdown-toggle btn btn-sm btn-default repDropBtn" type="button" id="menu3" data-toggle="dropdown"><span class="caret cursorPointer"></span></span>
				<ul class="dropdown-menu" role="menu" aria-labelledby="menu3">

				<%-- <li><a href="${appPath}/dailyTimeSheetReports">Daily Timesheet Report</a></li> --%>

				<sec:authorize access="hasRole('ROLE_DAYWISE_TIMESHEET_REPORT')">
				<li><a href="${appPath}/daywiseTimeSheetReports">Daywise Timesheet Reports</a></li>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_TIMESHEET_REPORT')">
				  <li><a href="${appPath}/timeSheetReports">Timesheet Reports</a></li>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_EXPENSES_REPORT')">
				<li><a href="${appPath}/expensesReportsList">Expenses</a></li>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_MISSING_TIMESHEETS')">
                <li><a href="${appPath}/missingTimesheetsReport">Missing Timesheets</a></li>
                </sec:authorize>
                
                <sec:authorize access="hasRole('ROLE_PENDING_FOR_APPROVAL')">
				<li><a href="${appPath}/pendingForApprovalReport">Pending for Approval</a></li>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_EMPLOYEMENT_VERIFICATION_REPORT')">
				<li><a href="${appPath}/employementVerificationReport">Employement Verification Reports</a></li>
				</sec:authorize>
				<!-- <li><a href="#">Employment Verficication</a></li>
				<li><a href="#">Wage and Tax</a></li>
				<li><a href="#">Associate Onboard</a></li>
				<li><a href="#">Associate Offboard</a></li> -->
						

				</ul>
			</div>
                 <!-- Dropdown -->
                 
                  <div class="clearfix text-center mb10"><i class="hrReport"></i></div>
                  <div class="clearfix text-center"><h4><a href="#">HR Report</a></h4></div>
     </div>
     </sec:authorize>
     
     
     
     
     
     <sec:authorize access="hasAnyRole('ROLE_ASSOCIATE_ROI','ROLE_ALL_ASSOCIATE_ROI','ROLE_CAMPAIGN_REPORTS')">   
         <div class="col-md-4 reportNewCta">
                  <!-- DropDown -->
			<div class="dropdown pull-right report-dropdown">
				<span class="dropdown-toggle btn btn-sm btn-default repDropBtn" type="button" id="menu4" data-toggle="dropdown"><span class="caret cursorPointer"></span></span>
				<ul class="dropdown-menu" role="menu" aria-labelledby="menu3">
					<sec:authorize access="hasRole('ROLE_ASSOCIATE_ROI')">
					<li><a href="${appPath}/payrollReport">Associate ROI</a></li>
					</sec:authorize>
					
					<sec:authorize access="hasRole('ROLE_ALL_ASSOCIATE_ROI')">
					<li><a href="${appPath}/payrollAllBalanceReport">All Associates ROI</a></li>
					</sec:authorize>
					<sec:authorize access="hasRole('ROLE_CAMPAIGN_REPORTS')">
					<li><a href='${appPath}/campaignStats'>Campaign Reports</a></li>
					</sec:authorize>
					<!--<li><a href="#">Bank Report</a></li>
					 <li><a href="#">Performance</a></li>
					<li><a href="#">Payrol</a></li>
					<li><a href="#">360 View</a></li>
					<li><a href="#">Pay History</a></li>
					<li><a href="#">Bill Histroy</a></li> -->
				</ul>
			</div>
                 <!-- Dropdown -->
                  
                  
                  
                  <div class="clearfix text-center mb10"><i class="assReport"></i></div>
                  <div class="clearfix text-center"><h4 class="text-danger"><a href="#">Associate Report</a></h4></div>
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
				<li><a href="${appPath}/userUsageReport">Usage Reports</a></li>
				</sec:authorize>
					
				</ul>
			</div>
                 <!-- Dropdown -->
                  
                  
                  
                  <div class="clearfix text-center mb10"><i class="perReport"></i></div>
                  <div class="clearfix text-center"><h4><a href="#">Users Usage Reports</a></h4></div>
                      </div>
                      
              </sec:authorize>     
      </div>
      
      <!--   List View   -->
      
     <div class="newRepList clearfix">
            <div class="newRepListCta">
                <h2><a href="javascript:void(0)">Performance Reports</a></h2>
                <ul class="repSearchList">
 				<sec:authorize access="hasRole('ROLE_RECRUITERS_REPORT')">
				 <li><a href='${appPath}/recruitersReports'>Recruiters Reports</a></li>
				 </sec:authorize>
				
				 <sec:authorize access="hasRole('ROLE_VENDORS_REPORT')">
				 <li><a href="${appPath}/vendorsReports">Vendors Reports</a></li>
				 </sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_CLIENT_REPORT')">
				<li><a href="${appPath}/perfomanceClientReports">Client Reports</a></li>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_APPLICANT_DECLINE')">
				<li><a href="${appPath}/applicantDeclineReports">Applicants Decline</a></li>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_APPLICANT_BACKOUT')">
				<li><a href="${appPath}/applicantsBackoutReport">Applicants Backout</a></li>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_JOBS_PIPELINE')">
				<li><a href="${appPath}/jobPipelineReports">Jobs Pipeline</a></li>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_APPLICANT_SOURCE')">
				<li><a href="${appPath}/applicantBySource">Applicants Source</a></li>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_TIME_TO_FILL')">
				<li><a href="${appPath}/timeToFill">Time to Fill</a></li>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_APPLICANT_RECEIVED')">
				<li><a href="${appPath}/applicantsReceivedListForReports">Applicants Received</a></li>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_JOBS_STAGES')">
				<li><a href="${appPath}/requirementStagesListForReports">Jobs Stages</a></li>
				</sec:authorize>
                </ul>
                <div class="noDate noDateSm" style="display: none;"></div>
            </div>
            <div class="newRepListCta">
                <h2><a href="javascript:void(0)">Financial Reports</a></h2>
                <ul class="repSearchList">
                    <sec:authorize access="hasRole('ROLE_BILLING_REPORTS')">
				<li><a href="${appPath}/billingReports">Finance</a></li>
				</sec:authorize>
				<!-- <li><a href="#">Invoice Report By Customer / Project</a></li>
				<li><a href="#">Bill Report By Customer / Project</a></li> -->
				<sec:authorize access="hasRole('ROLE_A_R_AGING')">
				<c:if test="${SourceLeadWeb_ats == 'Y'}">
				<li><a href='${appPath}/arAgingReport'>A/R Aging</a></li>
				</c:if>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_A_P_AGING')">
				<c:if test="${SourceLeadWeb_vms == 'Y'}">
				<li><a href='${appPath}/apAgingReport'>A/P Aging</a></li>
				</c:if>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_MISSING_INVOICE')">
					<c:if test="${SourceLeadWeb_ats == 'Y'}">
					<li><a href='${appPath}/missingInvoiceReport'>Missing Invoices</a></li>
					</c:if>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_MISSING_BILL')">
				<c:if test="${SourceLeadWeb_vms == 'Y'}">
				<li><a href='${appPath}/missingBillReport'>Missing Bills</a></li>
				</c:if>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_PAID_BILL')">
					<c:if test="${SourceLeadWeb_vms == 'Y'}">
				    <li><a href='${appPath}/paidBillReport'>Paid Bill</a></li>
				    </c:if>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_RECEIVED_INVOICES')">
				<c:if test="${SourceLeadWeb_ats == 'Y'}">
			     <li><a href='${appPath}/receivedInvoiceReport'>Received Invoices</a></li>    
			     </c:if>
				</sec:authorize>
                </ul>
                <div class="noDate noDateSm" style="display: none;"></div>
            </div>
            <div class="newRepListCta">
                <h2><a href="javascript:void(0)">HR Reports</a></h2>
                <ul class="repSearchList">
                   <sec:authorize access="hasRole('ROLE_DAYWISE_TIMESHEET_REPORT')">
				<li><a href="${appPath}/daywiseTimeSheetReports">Daywise Timesheet Reports</a></li>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_TIMESHEET_REPORT')">
				  <li><a href="${appPath}/timeSheetReports">Timesheet Reports</a></li>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_EXPENSES_REPORT')">
				<li><a href="${appPath}/expensesReportsList">Expenses</a></li>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_MISSING_TIMESHEETS')">
                <li><a href="${appPath}/missingTimesheetsReport">Missing Timesheets</a></li>
                </sec:authorize>
                
                <sec:authorize access="hasRole('ROLE_PENDING_FOR_APPROVAL')">
				<li><a href="${appPath}/pendingForApprovalReport">Pending for Approval</a></li>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_EMPLOYEMENT_VERIFICATION_REPORT')">
				<li><a href="${appPath}/employementVerificationReport">Employement Verification Reports</a></li>
				</sec:authorize>
                </ul>
                <div class="noDate noDateSm" style="display: none;"></div>
            </div> 
            
            
            <div class="newRepListCta">
                <h2><a href="javascript:void(0)">Associate Report</a></h2>
                <ul class="repSearchList">
					<sec:authorize access="hasRole('ROLE_ASSOCIATE_ROI')">
					<li><a href="${appPath}/payrollReport">Associate ROI</a></li>
					</sec:authorize>
					
					<sec:authorize access="hasRole('ROLE_ALL_ASSOCIATE_ROI')">
					<li><a href="${appPath}/payrollAllBalanceReport">All Associates ROI</a></li>
					</sec:authorize>
					<sec:authorize access="hasRole('ROLE_CAMPAIGN_REPORTS')">
					<li><a href='${appPath}/campaignStats'>Campaign Reports</a></li>
					</sec:authorize>               
                </ul>
                <div class="noDate noDateSm" style="display: none;"></div>
            </div> 
            
			<div class="newRepListCta">
                <h2><a href="javascript:void(0)">Users Usage Reports</a></h2>
                <ul class="repSearchList">
                <sec:authorize access="hasRole('ROLE_USAGE_REPORT')">
				<li><a href="${appPath}/userUsageReport">Usage Reports</a></li>
				</sec:authorize>
                </ul>
                <div class="noDate noDateSm" style="display: none;"></div>
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