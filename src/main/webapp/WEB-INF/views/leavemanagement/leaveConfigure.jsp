<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 


<!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Leave Configure</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Leave Management &raquo; Leave Configure</div>
    </section>
    <div class="content-area content-new clearfix">
      <div class="clearfix form-horizontal inner-content mt mb col-md-12">
          <div class="leaveConfigure clerfix mt40">
             <div class="col-md-3 mb">
                  <div class="pull-left mr10">
                      <i class="fa fa-calendar-minus-o fa-2x text-info"></i>
                  </div>
                  <div class="pull-left">
                      <h4 class="text-info">Leave Type</h4>
                      <small class="text-muted">Loremipsum Loremipsum</small>
                      <div class="clearfix mt10">
                          <a href="<c:url value='/addLeave'/>" class="btn btn-info btn-sm mr10" title="Add">Add</a>
                          <a href="<c:url value='/viewLeaves'/>" class="btn btn-primary btn-sm" title="View">View</a>
                      </div>
                  </div>
                  </div>
              <div class="col-md-3 mb">
                  <div class="pull-left mr10">
                      <i class="fa fa-calendar fa-2x text-muted"></i>
                  </div>
                  <div class="pull-left">
                      <h4 class="text-muted">Holiday</h4>
                      <small class="text-muted">Loremipsum Loremipsum</small>
                      <div class="clearfix mt10">
                          <a href="<c:url value='/addHoliday'/>" class="btn btn-info btn-sm mr10" title="Add">Add</a>
                          <a href="<c:url value='/viewHoliday'/>" class="btn btn-primary btn-sm" title="View">View</a>
                      </div>
                  </div>
                  </div>
               <div class="col-md-3 mb">
                  <div class="pull-left mr10">
                      <i class="fa fa-calendar-plus-o fa-2x text-primary"></i>
                  </div>
                  <div class="pull-left">
                      <h4 class="text-primary">Shift</h4>
                      <small class="text-muted">Loremipsum Loremipsum</small>
                      <div class="clearfix mt10">
                          <a href="<c:url value='/addShift'/>" class="btn btn-info btn-sm mr10" title="Add">Add</a>
                          <a href="<c:url value='/viewShift'/>" class="btn btn-primary btn-sm" title="View">View</a>
                      </div>
                  </div>
                  </div>
              
                  <div class="col-md-3 mb">
                  <div class="pull-left mr10">
                      <i class="fa fa-calendar-times-o fa-2x text-danger"></i>
                  </div>
                  <div class="pull-left">
                      <h4 class="text-danger">Restrict Day</h4>
                      <small class="text-muted">Loremipsum Loremipsum</small>
                      <div class="clearfix mt10">
                          <a href="<c:url value='/addRestrictDay'/>" class="btn btn-info btn-sm mr10" title="Add">Add</a>
                          <a href="<c:url value='/viewRestrictDay'/>" class="btn btn-primary btn-sm" title="View">View</a>
                      </div>
                  </div>
                      </div>
              <div class="col-md-3">
                  <div class="pull-left mr10">
                      <i class="fa fa-toggle-on fa-2x text-warning"></i>
                  </div>
                  <div class="pull-left">
                      <h4 class="text-warning">Options</h4>
                      <small class="text-muted">Loremipsum Loremipsum</small>
                      <div class="clearfix mt10">
                          <a href="<c:url value='/addOptions'/>" class="btn btn-info btn-sm mr10" title="Add">Add</a>
                          <a href="<c:url value='/viewOptions'/>" class="btn btn-primary btn-sm" title="View">View</a>
                      </div>
                  </div>
                      </div>
      </div>
        </div>
      <!-- Content Area Ends--> 
    </div>
  </section>




<jsp:include page="../masterFooter.jsp" />