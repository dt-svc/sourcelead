<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


 <!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>India Payroll Reports</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard'/>" title="Home">Home</a>&raquo; <a href="<c:url value='${appPath}/reportsDashboard'/>">Reports Dashboard
</a> &raquo; India Payroll Reports</div>
    </section>
    <div class="content-area content-new clearfix reportNewDash">
      <div class="clearfix form-horizontal inner-content mt mb col-md-12">
        <div class="leaveConfigure clerfix">
          <div class="col-md-3">
            <div class="pull-left mr10">
			<i class="fa fa-pie-chart fa-2x text-muted"></i>
           </div>
            <div class="pull-left">
              <h4 class="text-muted">Payroll Register</h4>
              <small class="text-muted">Payroll Register Reports</small>
              <div class="clearfix mt10"> <a href="${appPath}/indPayrollRegisterReport"  class="btn btn-primary btn-sm" title="View">View</a> </div>
            </div>
          </div>
           <div class="col-md-3">
            <div class="pull-left mr10"> <i class="fa fa-exchange fa-2x text-muted"></i> </div>
            <div class="pull-left">
              <h4 class="text-muted">Bank Transfer</h4>
              <small class="text-muted">Bank Transfer Reports</small>
              <div class="clearfix mt10"> <a href="${appPath}/indPayrollBankTransferReport" class="btn btn-primary btn-sm" title="View">View</a> </div>
            </div>
          </div>

         <div class="col-md-3">
            <div class="pull-left mr10"> <i class="fa fa-calendar fa-2x text-muted"></i> </div>
            <div class="pull-left">
              <h4 class="text-muted">YTD Summary</h4>
              <small class="text-muted">Payroll YTD Summary Reports</small>
              <div class="clearfix mt10"><a href="${appPath}/indPayrollYTDSummaryReport" class="btn btn-primary btn-sm" title="View">View</a> </div>
            </div>
          </div> 
          
          <div class="col-md-3">
            <div class="pull-left mr10"> <i class="fa fa-inr fa-2x text-muted"></i> </div>
            <div class="pull-left">
              <h4 class="text-muted">Annual Tax </h4>
              <small class="text-muted">Payroll Annual Tax Reports</small>
              <div class="clearfix mt10"><a href="${appPath}/indPayrollAnnualTaxReport" class="btn btn-primary btn-sm" title="View">View</a> </div>
            </div>
          </div> 
          
          <%-- <div class="col-md-3">
            <div class="pull-left mr10"> <i class="fa fa-inr fa-2x text-muted"></i> </div>
            <div class="pull-left">
              <h4 class="text-muted">Loan</h4>
              <small class="text-muted">Payroll Loan Reports</small>
              <div class="clearfix mt10"><a href="${appPath}/indPayrollLoanReport" class="btn btn-primary btn-sm" title="View">View</a> </div>
            </div>
          </div>  --%>
          
          <div class="col-md-3">
            <div class="pull-left mr10"> <i class="fa fa-inr fa-2x text-muted"></i> </div>
            <div class="pull-left">
              <h4 class="text-muted">Salary Statement</h4>
              <small class="text-muted">Payroll Salary Statement Reports</small>
              <div class="clearfix mt10"><a href="${appPath}/indPayrollSalaryStatementReport" class="btn btn-primary btn-sm" title="View">View</a> </div>
            </div>
          </div> 
          
          <div class="col-md-3">
            <div class="pull-left mr10"> <i class="fa fa-medkit fa-2x text-muted"></i> </div>
            <div class="pull-left">
              <h4 class="text-muted">ESI Summary</h4>
              <small class="text-muted">Payroll ESI Summary Reports</small>
              <div class="clearfix mt10"><a href="${appPath}/indPayrollESISummaryReport" class="btn btn-primary btn-sm" title="View">View</a> </div>
            </div>
          </div> 
          
          
          <div class="col-md-3">
            <div class="pull-left mr10"> <i class="fa fa-suitcase fa-2x text-muted"></i> </div>
            <div class="pull-left">
              <h4 class="text-muted">PF Summary</h4>
              <small class="text-muted">Payroll PF Summary Reports</small>
              <div class="clearfix mt10"><a href="${appPath}/indPayrollPFSummaryReport" class="btn btn-primary btn-sm" title="View">View</a> </div>
            </div>
          </div> 
          
          
          <div class="col-md-3">
            <div class="pull-left mr10"> <i class="fa fa-money fa-2x text-muted"></i> </div>
            <div class="pull-left">
              <h4 class="text-muted">Salary Register</h4>
              <small class="text-muted">Payroll Salary Register Reports</small>
              <div class="clearfix mt10"><a href="${appPath}/indPayrollSalaryRegisterReport" class="btn btn-primary btn-sm" title="View">View</a> </div>
            </div>
          </div> 
          
        </div>
      </div>
      <!-- Content Area Ends--> 
    </div>
  </section>

<jsp:include page="../masterFooter.jsp" />