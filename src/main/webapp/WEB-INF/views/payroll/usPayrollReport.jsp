<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


 <!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>US Payroll Reports</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard'/>" title="Home">Home</a>&raquo; <a href="<c:url value='${appPath}/reportsDashboard'/>">Reports Dashboard
</a> &raquo;US Payroll Reports</div>
    </section>
    <div class="content-area content-new clearfix">
      <div class="clearfix form-horizontal inner-content mt mb col-md-12">
        <div class="leaveConfigure clerfix">
          <div class="col-md-3">
            <div class="pull-left mr10">
			<i class="fa fa-pie-chart fa-2x text-muted"></i>
           </div>
            <div class="pull-left">
              <h4 class="text-muted">Payroll Register</h4>
              <small class="text-muted">Payroll Register Reports</small>
              <div class="clearfix mt10"> <a href="${appPath}/payrollRegisterReport"  class="btn btn-primary btn-sm" title="View">View</a> </div>
            </div>
          </div>
          <div class="col-md-3">
            <div class="pull-left mr10"> <i class="fa fa-exchange fa-2x text-muted"></i> </div>
            <div class="pull-left">
              <h4 class="text-muted">Comparison</h4>
              <small class="text-muted">Payroll Comparison</small>
              <div class="clearfix mt10"> <a href="${appPath}/payrollComparison" class="btn btn-primary btn-sm" title="View">View</a> </div>
            </div>
          </div>

          <div class="col-md-3">
            <div class="pull-left mr10"> <i class="fa fa-file-text-o fa-2x text-muted"></i> </div>
            <div class="pull-left">
              <h4 class="text-muted">Payroll Summary</h4>
              <small class="text-muted">Payroll Summary Report</small>
              <div class="clearfix mt10"><a href="${appPath}/payrollListSummaryReport" class="btn btn-primary btn-sm" title="View">View</a> </div>
            </div>
          </div>
          <div class="col-md-3">
            <div class="pull-left mr10"> <i class="fa fa-check fa-2x text-muted"></i> </div>
            <div class="pull-left">
              <h4 class="text-muted">Check Register</h4>
              <small class="text-muted">Check Register Report</small>
              <div class="clearfix mt10"><a href="${appPath}/checkRegisterReport" class="btn btn-primary btn-sm" title="View">View</a> </div>
            </div>
          </div>
          
           <div class="col-md-3">
            <div class="pull-left mr10"> <i class="fa fa-check fa-2x text-muted"></i> </div>
            <div class="pull-left">
              <h4 class="text-muted">Associate Withholding</h4>
              <small class="text-muted">Associate Withholding Report</small>
              <div class="clearfix mt10"><a href="${appPath}/associateWithholding" class="btn btn-primary btn-sm" title="View">View</a> </div>
            </div>
          </div>
          
          <!-- <div class="col-md-3">
            <div class="pull-left mr10"> <i class="fa fa-line-chart fa-2x text-primary"></i> </div>
            <div class="pull-left">
              <h4 class="text-primary"><a href="apaging-graph.html" class="anchorBlue">Employee Report</a></h4>
              <small class="text-muted">Employee Payroll Report</small>
             <div class="clearfix mt10"><a href="#" class="btn btn-primary btn-sm" title="View">View</a> </div>
            </div>
          </div>
          <div class="col-md-3">
            <div class="pull-left mr10"> <i class="fa fa-clock-o fa-2x text-primary"></i> </div>
            <div class="pull-left">
              <h4 class="text-primary"><a href="billing-report.html" class="anchorBlue">Employee Hours Paid</a>s</h4>
              <small class="text-muted">Loremipsum Loremipsum</small>
              <div class="clearfix mt10"> <a href="billing-report.html" class="btn btn-primary btn-sm" title="View">View</a> </div>
            </div>
          </div>-->
          <div class="col-md-3">
            <div class="pull-left mr10"> <i class="fa fa-usd fa-2x text-muted"></i> </div>
            <div class="pull-left">
              <h4 class="text-muted">Taxable Wages Report</h4>
              <small class="text-muted">Payroll Taxable Wages Reports</small>
              <div class="clearfix mt10"><a href="${appPath}/payrollTaxesAndWagesReport" class="btn btn-primary btn-sm" title="View">View</a> </div>
            </div>
          </div> 
          <div class="col-md-3">
            <div class="pull-left mr10"> <i class="fa fa-scissors fa-2x text-muted"></i> </div>
            <div class="pull-left">
              <h4 class="text-muted">Deduction Reports</h4>
              <small class="text-muted">Payroll Deduction Reports</small>
              <div class="clearfix mt10"><a href="${appPath}/deductionReport"  class="btn btn-primary btn-sm" title="View">View</a> </div>
            </div>
          </div>
          <div class="col-md-3">
            <div class="pull-left mr10"> <i class="fa fa-puzzle-piece fa-2x text-muted"></i> </div>
            <div class="pull-left">
              <h4 class="text-muted">Contribution Reports</h4> 
              <small class="text-muted">Payroll Contribution Reports</small>
              <div class="clearfix mt10"> <a href="${appPath}/contributionReport" class="btn btn-primary btn-sm" title="View">View</a> </div>
            </div>
          </div>
         <!--  <div class="col-md-3">
            <div class="pull-left mr10"> <i class="fa fa-suitcase fa-2x text-muted"></i> </div>
            <div class="pull-left">
              <h4 class="text-muted"><a href="financial-paidbills.html" class="anchorBlue">Tax Liability Report</a></h4>
              <small class="text-muted">Loremipsum Loremipsum</small>
              <div class="clearfix mt10"><a href="javascript:void(0)" class="btn btn-primary btn-sm" title="View">View</a> </div>
            </div>
          </div>
          
          <div class="col-md-3">
            <div class="pull-left mr10"> <i class="fa fa-calendar-check-o fa-2x text-muted"></i> </div>
            <div class="pull-left">
              <h4 class="text-muted">Payroll Timesheet Report</h4>
              <small class="text-muted">Loremipsum Loremipsum</small>
              <div class="clearfix mt10"><a href="javascript:void(0)" class="btn btn-primary btn-sm" title="View">View</a> </div>
            </div>
          </div> -->
           <div class="col-md-3">
            <div class="pull-left mr10"> <i class="fa fa-arrow-circle-right fa-2x text-muted"></i> </div>
            <div class="pull-left">
              <h4 class="text-muted">Direct Deposit</h4>
              <small class="text-muted">Direct Deposit Report</small>
              <div class="clearfix mt10"><a href="${appPath}/directDepositReport" class="btn btn-primary btn-sm" title="View">View</a> </div>
            </div>
          </div>
          
          
            <div class="col-md-3">
            <div class="pull-left mr10"> <i class="fa fa-calendar fa-2x text-muted"></i> </div>
            <div class="pull-left">
              <h4 class="text-muted">Employee Payroll</h4>
              <small class="text-muted">Employee Payroll Report</small>
              <div class="clearfix mt10"><a href="${appPath}/usEmployeePayrollReport" class="btn btn-primary btn-sm" title="View">View</a> </div>
            </div>
          </div>
          
         <div class="col-md-3">
            <div class="pull-left mr10"> <i class="fa fa-calendar fa-2x text-muted"></i> </div>
            <div class="pull-left">
              <h4 class="text-muted">Contract Employee Payroll</h4>
              <small class="text-muted">Contract Employee Payroll Report</small>
              <div class="clearfix mt10"><a href="${appPath}/contractEmployeePayreport" class="btn btn-primary btn-sm" title="View">View</a> </div>
              </div>
          </div> 
          
           <div class="col-md-3">
            <div class="pull-left mr10"> <i class="fa fa-calendar fa-2x text-muted"></i> </div>
            <div class="pull-left">
              <h4 class="text-muted">Deduction Summary</h4>
              <small class="text-muted">Deduction Summary Report</small>
              <div class="clearfix mt10"><a href="${appPath}/usDeductionSummaryReport" class="btn btn-primary btn-sm" title="View">View</a> </div>
            </div>
          </div>
          
           <div class="col-md-3">
            <div class="pull-left mr10"> <i class="fa fa-calendar fa-2x text-muted"></i> </div>
            <div class="pull-left">
              <h4 class="text-muted">Contribution Summary</h4>
              <small class="text-muted">Contribution Summary Report</small>
              <div class="clearfix mt10"><a href="${appPath}/usContributionSummaryReport" class="btn btn-primary btn-sm" title="View">View</a> </div>
            </div>
          </div>
          
        </div>
      </div>
      <!-- Content Area Ends--> 
    </div>
  </section>

<jsp:include page="../masterFooter.jsp" />