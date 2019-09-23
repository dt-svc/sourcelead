<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<section class="content-wraper contentWraper">
    <section class="content-header clearfix">
	    <div class="pull-left">
	      <!-- <h2>Submission Templates</h2> -->
	      <h2 class="pull-left">Payroll Configure</h2>
	    </div>
	    <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/settings'/>">Settings</a>&raquo; Payroll Configure</div>
	 	<div class="clearfix"></div>
    </section>
    
<div class="clearfix"></div>
   <div class="leaveConfigure clerfix">
	<div class="col-md-3 mb">
        <div class="pull-left mr10"> <i class="fa fa-inr fa-2x text-muted"></i> </div>
        <div class="pull-left">
          <h4 class="text-muted">Pay Heads</h4>
          <small class="text-muted">Manage your pay heads</small>
          <div class="clearfix mt10"> 
          	<a href="<c:url value='${appPath}/payrollPayhead'/>" class="btn btn-info btn-sm mr10" title="Add">Add</a>
          	<a href="<c:url value='${appPath}/payheadList'/>" class="btn btn-primary btn-sm" title="View">View</a>
          </div>
        </div>
        <div class="clearfix"></div>
      </div>
      
      
	<div class="col-md-3 mb">
        <div class="pull-left mr10"> <i class="fa fa-file-pdf-o fa-2x text-muted"></i> </div>
        <div class="pull-left">
          <h4 class="text-muted">Pay Slips</h4>
          <small class="text-muted">Manage your pay slips</small>
          <div class="clearfix mt10"> 
          	<a href="<c:url value='${appPath}/payrollPaySlip'/>" class="btn btn-info btn-sm mr10" title="Add">Add</a>
          	<a href="<c:url value='${appPath}/payrollPaySlipView'/>" class="btn btn-primary btn-sm" title="View">View</a>
          </div>
        </div>
        <div class="clearfix"></div>
      </div>
	
	<div class="col-md-3 mb">
        <div class="pull-left mr10"> <i class="fa fa-gift fa-2x text-muted"></i> </div>
        <div class="pull-left">
          <h4 class="text-muted">Bonus</h4>
          <small class="text-muted">Manage your bonus</small>
          <div class="clearfix mt10"> 
          	<a href="<c:url value='${appPath}/payrollBonus'/>" class="btn btn-info btn-sm mr10" title="Add">Add</a>
          	<a href="<c:url value='${appPath}/payrollBonusList'/>" class="btn btn-primary btn-sm" title="View">View</a>
          </div>
        </div>
        <div class="clearfix"></div>
      </div>
	
	
	<div class="col-md-3 mb">
        <div class="pull-left mr10"> <i class="fa fa-suitcase fa-2x text-muted"></i> </div>
        <div class="pull-left">
          <h4 class="text-muted">Loan & Advances</h4>
          <small class="text-muted">Manage your loan & advances</small>
          <div class="clearfix mt10"> 
          	<a href="<c:url value='${appPath}/payrollLoanAndAdvances'/>" class="btn btn-info btn-sm mr10" title="Add">Add</a>
          	<a href="<c:url value='${appPath}/payrollLoanAndAdvancesList'/>" class="btn btn-primary btn-sm" title="View">View</a>
          </div>
        </div>
        <div class="clearfix"></div>
     </div>
      
	
	<div class="col-md-3 mb">
        <div class="pull-left mr10"> <i class="fa fa-inr fa-2x text-muted"></i> </div>
        <div class="pull-left">
          <h4 class="text-muted">Taxes</h4>
          <small class="text-muted">Manage your taxes</small>
          <div class="clearfix mt10"> <a href="<c:url value='${appPath}/payrollTaxConfiguration'/>" class="btn btn-info btn-sm mr10" title="Configure">Configure</a>
           </div>
        </div>
        <div class="clearfix"></div>
     </div>
     
     
	<div class="col-md-3 mb">
        <div class="pull-left mr10"> <i class="fa fa-plus-circle fa-2x text-muted"></i> </div>
        <div class="pull-left">
          <h4 class="text-muted">Create Batch</h4>
          <small class="text-muted">Configure Batch</small>
          <div class="clearfix mt10"> <a href="<c:url value='${appPath}/userGroupList'/>" class="btn btn-info btn-sm mr10" title="View">View</a>
           </div>
        </div>
        <div class="clearfix"></div>
     </div>
	
	<div class="col-md-3 mb">
        <div class="pull-left mr10"> <i class="fa fa-gears fa-2x text-muted"></i> </div>
        <div class="pull-left">
          <h4 class="text-muted">Payroll Configure</h4>
          <small class="text-muted">Configure Payroll</small>
          <div class="clearfix mt10"> <a href="<c:url value='${appPath}/payrollApprover'/>" class="btn btn-info btn-sm mr10" title="Add">Configure</a>
           </div>
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
	
	<div class="clearfix"></div>
</div>
        
</section>






<jsp:include page="../masterFooter.jsp" />