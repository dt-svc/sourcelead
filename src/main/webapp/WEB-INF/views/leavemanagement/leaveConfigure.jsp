<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 


<!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Leave Configuration</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>"> Home</a> &raquo; <a href="<c:url value='${appPath}/settings'/>">Settings</a>&raquo; Leave Configuration</div>
    </section>
    <div class="project_wrap clearfix">
      <div class="clearfix form-horizontal inner-content mt mb col-md-12">
          <div class="leaveConfigure clerfix mt40">
             <div class="col-md-3 mb">
                  <div class="pull-left mr10">
                      <i class="fa fa-calendar-minus-o fa-2x text-info"></i>
                  </div>
                  <div class="pull-left">
                      <h4 class="text-info">Leave Type</h4>
<!--                       <small class="text-muted">Loremipsum Loremipsum</small> -->
                      <div class="clearfix mt10">
                          <a href="<c:url value='${appPath}/addLeaveType'/>" class="btn btn-info btn-sm mr10" title="Add">Add</a>
                          <a href="<c:url value='${appPath}/viewLeaveTypes'/>" class="btn btn-primary btn-sm" title="View">View</a>
                      </div>
                  </div>
                  </div>
              <div class="col-md-3 mb">
                  <div class="pull-left mr10">
                      <i class="fa fa-calendar fa-2x text-muted"></i>
                  </div>
                  <div class="pull-left">
                      <h4 class="text-muted">Holidays</h4>
<!--                       <small class="text-muted">Loremipsum Loremipsum</small> -->
                      <div class="clearfix mt10">
                          <a href="<c:url value='${appPath}/addHoliday'/>" class="btn btn-info btn-sm mr10" title="Add">Add</a>
                          <a href="<c:url value='${appPath}/viewHoliday'/>" class="btn btn-primary btn-sm" title="View">View</a>
                      </div>
                  </div>
                  </div>
               <div class="col-md-3 mb">
                  <div class="pull-left mr10">
                      <i class="fa fa-calendar-plus-o fa-2x text-primary"></i>
                  </div>
                  <div class="pull-left">
                      <h4 class="text-primary">Shifts</h4>
<!--                       <small class="text-muted">Loremipsum Loremipsum</small> -->
                      <div class="clearfix mt10">
                          <a href="<c:url value='${appPath}/addShift'/>" class="btn btn-info btn-sm mr10" title="Add">Add</a>
                          <a href="<c:url value='${appPath}/viewShift'/>" class="btn btn-primary btn-sm" title="View">View</a>
                      </div>
                  </div>
                  </div>
              
                  <div class="col-md-3 mb">
                  <div class="pull-left mr10">
                      <i class="fa fa-calendar-times-o fa-2x text-danger"></i>
                  </div>
                  <div class="pull-left">
                      <h4 class="text-danger">Restricted Day</h4>
<!--                       <small class="text-muted">Loremipsum Loremipsum</small> -->
                      <div class="clearfix mt10">
                          <a href="<c:url value='${appPath}/addRestrictDay'/>" class="btn btn-info btn-sm mr10" title="Add">Add</a>
                          <a href="<c:url value='${appPath}/viewRestrictDay'/>" class="btn btn-primary btn-sm" title="View">View</a>
                      </div>
                  </div>
                      </div>
           <%--    <div class="col-md-3">
                  <div class="pull-left mr10">
                      <i class="fa fa-toggle-on fa-2x text-warning"></i>
                  </div>
                  <div class="pull-left">
                      <h4 class="text-warning">Leave Options</h4>
                      <div class="clearfix mt10">
                          <a href="<c:url value='${appPath}/addOptions'/>" class="btn btn-info btn-sm mr10" title="Add">Add</a>
                          <a href="<c:url value='${appPath}/viewOptions'/>" class="btn btn-primary btn-sm" title="View">View</a>
                      </div>
                  </div>
                      </div> --%>
                     
      </div>
        </div>
      <!-- Content Area Ends--> 
    </div>
  </section>




<jsp:include page="../masterFooter.jsp" />