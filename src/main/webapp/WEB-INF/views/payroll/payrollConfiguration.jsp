<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

  <!-- Content Area -->
  <section class="content-wraper contentWraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Payroll Configure</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard'/>" title="Home">Home</a> &raquo; <a href="<c:url value='${appPath}/settings'/>">Settings</a>&raquo; Payroll Configure</div>
    </section>
    <div class="clearfix"></div>
    <div class="leaveConfigure clerfix">
      <div class="col-md-3 mb">
        <div class="pull-left mr10"> <i class="fa fa-usd fa-2x text-muted"></i> </div>
        <div class="pull-left">
          <h4 class="text-muted">Deductions</h4>
          <small class="text-muted">Configure Deductions</small>
          <div class="clearfix mt10"> 
          <a href="<c:url value='${appPath}/addDeduction'/>" class="btn btn-info btn-sm mr10" title="Add">Add</a>
          <a href="<c:url value='${appPath}/payrollDeductionList'/>" class="btn btn-primary btn-sm" title="View">View</a> </div>
        </div>
        <div class="clearfix"></div>
      </div>
      <div class="col-md-3 mb">
        <div class="pull-left mr10"> <i class="fa fa-code-fork fa-2x text-muted"></i> </div>
        <div class="pull-left">
          <h4 class="text-muted">Contributions</h4>
          <small class="text-muted">Configure Contributions</small>
          <div class="clearfix mt10"> 
          <a href="<c:url value='${appPath}/addContribution'/>" class="btn btn-info btn-sm mr10" title="Add">Add</a> 
          <a href="<c:url value='${appPath}/payrollContribution'/>" class="btn btn-primary btn-sm" title="View">View</a> </div>
        </div>
        <div class="clearfix"></div>
      </div>
      <div class="col-md-3 mb">
        <div class="pull-left mr10"> <i class="fa fa-plus fa-2x text-muted"></i> </div>
        <div class="pull-left">
          <h4 class="text-muted">Pay Items</h4>
          <small class="text-muted">Configure Pay Items</small>
          <div class="mt10">
           <a href="<c:url value='${appPath}/addPayItem'/>" class="btn btn-info btn-sm mr10" title="Add">Add</a>
          <a href="<c:url value='${appPath}/viewPayItem'/>"class="btn btn-primary btn-sm" title="View">View</a> </div>
        </div>
        <div class="clearfix"></div>
      </div>
       <div class="col-md-3 mb">
        <div class="pull-left mr10"> <i class="fa fa-calendar fa-2x text-muted"></i> </div>
        <div class="pull-left">
          <h4 class="text-muted">Pay Schedule</h4>
          <small class="text-muted">Configure Pay Schedule</small>
          <div class="clearfix mt10"> <a href="<c:url value='${appPath}/payrollScheduleList'/>" class="btn btn-info btn-sm mr10" title="Add">View</a></div>
        </div>
        <div class="clearfix"></div>
      </div>
      
       <div class="col-md-3 mb">
        <div class="pull-left mr10"> <i class="fa fa-gears fa-2x text-muted"></i> </div>
        <div class="pull-left">
          <h4 class="text-muted">Payroll Configure</h4>
          <small class="text-muted">Configure Payroll</small>
          <div class="clearfix mt10"> <a href="<c:url value='${appPath}/payrollApprover'/>" class="btn btn-info btn-sm mr10" title="Configure">Configure</a>
           </div>
        </div>
        <div class="clearfix"></div>
      </div>
      
       <div class="col-md-3 mb">
        <div class="pull-left mr10"> <i class="fa fa-pie-chart fa-2x text-muted"></i> </div>
        <div class="pull-left">
          <h4 class="text-muted">Payroll Legacy Reports</h4>
          <small class="text-muted">Configure Payroll Reports</small>
          <div class="clearfix mt10"> <a href="<c:url value='${appPath}/payrollImports'/>" class="btn btn-info btn-sm mr10" title="Configure">Configure</a>
           </div>
        </div>
        <div class="clearfix"></div>
      </div>
<!--       <div class="clearfix"></div> -->
      
        <div class="col-md-3 mb">
        <div class="pull-left mr10"> <i class="fa fa-plus-circle fa-2x text-muted"></i> </div>
        <div class="pull-left">
          <h4 class="text-muted">Create Batch</h4>
          <small class="text-muted">Configure Batch</small>
          <div class="mt10">
          <a href="<c:url value='${appPath}/userGroupList'/>"class="btn btn-primary btn-sm" title="View">View</a> </div>
        </div>
        <div class="clearfix"></div>
      </div>
      
       <c:if test="${SourceLeadWeb_displayName == 'Admin'}">
      <div class="col-md-3 mb">
        <div class="pull-left mr10"> <i class="fa fa-code-fork fa-2x text-muted"></i> </div>
        <div class="pull-left">
          <h4 class="text-muted">Client Filtered List</h4>
          <small class="text-muted">Client Filtered List</small>
          <div class="clearfix mt10"> 
      <a href="<c:url value='${appPath}/getClientFiltered'/>" class="btn btn-primary btn-sm" title="View">View</a> </div>
        </div>
        <div class="clearfix"></div>
      </div>
      
      <div class="col-md-3 mb">
        <div class="pull-left mr10"> <i class="fa fa-code-fork fa-2x text-muted"></i> </div>
        <div class="pull-left">
          <h4 class="text-muted">Staffing Filtered List</h4>
          <small class="text-muted">Staffing Filtered List</small>
          <div class="clearfix mt10"> 
      <a href="<c:url value='${appPath}/staffingFilteredList'/>" class="btn btn-primary btn-sm" title="View">View</a> </div>
        </div>
        <div class="clearfix"></div>
      </div> 
      
      <div class="col-md-3 mb">
        <div class="pull-left mr10"> <i class="fa fa-code-fork fa-2x text-muted"></i> </div>
        <div class="pull-left">
          <h4 class="text-muted">Vendor Recruiters</h4>
          <small class="text-muted">Vendor Recruiters</small>
          <div class="clearfix mt10"> 
      <a href="<c:url value='${appPath}/getVendorRecruiters'/>" class="btn btn-primary btn-sm" title="View">View</a> </div>
        </div>
        <div class="clearfix"></div>
      </div>
      
      </c:if>
      
       <div class="col-md-3 mb">
        <div class="pull-left mr10"> <i class="fa fa-suitcase fa-2x text-muted"></i> </div>
        <div class="pull-left">
          <h4 class="text-muted">Loan & Advances</h4>
          <small class="text-muted">Manage your loan & advances</small>
          <div class="mt10">
          	<a class="btn btn-info btn-sm mr10" href="<c:url value='${appPath}/payrollLoanAndAdvances'/>" title="Add">Add</a> 
			<a class="btn btn-primary btn-sm" href="<c:url value='${appPath}/payrollLoanAndAdvancesList'/>" title="View">View</a>
		  </div>
        </div>
        <div class="clearfix"></div>
      </div>
      <div class="clearfix"></div>
      
    </div>
  </section>

<jsp:include page="../masterFooter.jsp" />