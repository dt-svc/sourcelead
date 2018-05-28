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
      <div class="pull-right brud-crum">Home &raquo; Payroll Configure</div>
    </section>
    <div class="clearfix"></div>
    <div class="leaveConfigure clerfix">
      <div class="col-md-3 mb">
        <div class="pull-left mr10"> <i class="fa fa-usd fa-2x text-muted"></i> </div>
        <div class="pull-left">
          <h4 class="text-muted">Deductions</h4>
          <small class="text-muted">Loremipsum Loremipsum</small>
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
          <small class="text-muted">Loremipsum Loremipsum</small>
          <div class="clearfix mt10"> 
          <a href="<c:url value='${appPath}/addContribution'/>" class="btn btn-info btn-sm mr10" title="Add">Add</a> 
          <a href="<c:url value='${appPath}/payrollContribution'/>" class="btn btn-primary btn-sm" title="View">View</a> </div>
        </div>
        <div class="clearfix"></div>
      </div>
      <div class="col-md-3 mb">
        <div class="pull-left mr10"> <i class="fa fa-plus fa-2x text-muted"></i> </div>
        <div class="pull-left">
          <h4 class="text-muted">Pay Itmes</h4>
          <small class="text-muted">Loremipsum Loremipsum</small>
          <div>
           <a href="<c:url value='${appPath}/addPayItem'/>" class="btn btn-info btn-sm mr10" title="Add">Add</a>
          <a href="<c:url value='${appPath}/viewPayItem'/>"class="btn btn-primary btn-sm" title="View">View</a> </div>
        </div>
        <div class="clearfix"></div>
      </div>
      <div class="col-md-3 mb">
        <div class="pull-left mr10"> <i class="fa fa-calendar fa-2x text-muted"></i> </div>
        <div class="pull-left">
          <h4 class="text-muted">Pay Schedule</h4>
          <small class="text-muted">Loremipsum Loremipsum</small>
          <div class="clearfix mt10"> <a href="restrictday.html" class="btn btn-info btn-sm mr10" title="Add">Add</a> <a href="restrictday-list.html" class="btn btn-primary btn-sm" title="View">View</a> </div>
        </div>
        <div class="clearfix"></div>
      </div>
      <div class="clearfix"></div>
    </div>
  </section>

<jsp:include page="../masterFooter.jsp" />