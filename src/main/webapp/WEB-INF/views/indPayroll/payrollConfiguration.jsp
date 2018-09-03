<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<section class="content-wraper">
    <section class="content-header clearfix">
          <div class="pull-left">
      <!-- <h2>Submission Templates</h2> -->
      <h2 class="pull-left">Payroll Configure</h2>
				
    </div>
          <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Payroll Configure</div>
 <div class="clearfix"></div>
    </section>
    
    
 
<section class="landTemp">
	<div class="landTempCta">
		<div class="pull-left mr10">
           <i class="fa fa-inr fa-2x text-muted"></i>
        </div>
		<div class="pull-left">
		<h2 class="text-primary">Pay Heads</h2>
		<p>Manage your pay heads</p>
		<span>
			<a class="btn btn-info btn-sm mr10" href="<c:url value='${appPath}/payrollPayhead'/>">Add</a>
			<a class="btn btn-primary btn-sm" href="<c:url value='${appPath}/payheadList'/>">View</a>
		</span>
		</div>
	</div>
	<div class="landTempCta">
	<div class="pull-left mr10">
           <i class="fa fa-file-pdf-o fa-2x text-muted"></i>
        </div>
		<div class="pull-left">
		<h2 class="text-info">Pay Slips</h2>
		<p>Manage your pay slips</p>
		<span>
			<a class="btn btn-info btn-sm mr10" href="#">Add</a> 
			<a class="btn btn-primary btn-sm" href="#">View</a>
		</span>
	</div>
	</div>
	<div class="landTempCta">
	<div class="pull-left mr10">
           <i class="fa fa-gift fa-2x text-muted"></i>
        </div>
		<div class="pull-left">
		<h2 class="text-success">Bonus</h2>
		<p>Manage your bonus</p>
		<span>
			<a class="btn btn-info btn-sm mr10" href="<c:url value='${appPath}/payrollBonus'/>">Add</a> 
			<a class="btn btn-primary btn-sm" href="<c:url value='${appPath}/payrollBonusList'/>">View</a>
		</span>
		</div>
	</div>
	<div class="landTempCta">
	<div class="pull-left mr10">
           <i class="fa fa-suitcase fa-2x text-muted"></i>
        </div>
		<div class="pull-left">
		<h2 class="text-danger">Loan & Advances</h2>
		<p>Manage your loan & advances</p>
		<span>
			<a class="btn btn-info btn-sm mr10" href="<c:url value='${appPath}/payrollLoanAndAdvances'/>">Add</a> 
			<a class="btn btn-primary btn-sm" href="<c:url value='${appPath}/payrollLoanAndAdvancesList'/>">View</a>
		</span>
		</div>
	</div>
	<div class="landTempCta">
	<div class="pull-left mr10">
           <i class="fa fa-inr fa-2x text-muted"></i>
        </div>
		<div class="pull-left">
		<h2 class="text-warning">Taxes</h2>
		<p>Manage your taxes</p>
		<span>
			<a  class="btn btn-info btn-sm mr10" href="${appPath}/payrollTaxConfiguration">Configure</a> 
		</span>
		</div>
	</div>
	
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
	
	
	
	
	<div class="clearfix"></div>
</section>
    
        
</section>






<jsp:include page="../masterFooter.jsp" />