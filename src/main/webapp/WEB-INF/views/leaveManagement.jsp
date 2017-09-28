<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">

<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%-- <section class="content-wraper"> --%>
  <!-- <section class="content-header clearfix">
    <div class="pull-left">
      <h2>Dashboard :: Leave Management</h2>
    </div>
    <div class="pull-right brud-crum">Home &raquo; Dashboard</div>
  </section> -->
  <!-- Tasks and and Row -->
  <div class="clearfix row mb">
    <div class="col-md-6">
      <div class="dashboard-widget">
          <h4 class="pull-left"><a href="<c:url value='/leaveManagementReports'/>" title="Leave Management">Leaves</a></h4>
        <div class="clearfix require_wrap_table dashboard-data">
          <ul class="nav nav-tabs">
            <li class="active"><a data-toggle="tab" href="#self">Self</a></li>
            <li><a data-toggle="tab" href="#employee">Employee</a></li>
          </ul>
          <div class="tab-content clearfix mt">
            <div id="self" class="tab-pane fade in active">
              <div class="db-widget-sm">
                <div class="width100percnt">
                  <div id="empselfLeaves" style="width: 60%; height: 150px;" class="amchartAnchor-Hide pull-left"> </div>
                  <div style="width: 40%; height: 150px;" class="countList pull-left">
                <ul class="list-unstyled list-group clearfix">
                    <li class="clearfix">
                        <label class="pull-left">Pending Leaves</label>
                        <span class="label label-primary pull-right"><a href="<c:url value='/pendingLeaves'/>" class="color_white" title="Pending Leaves">100</a></span>
                    </li>
                    <li class="clearfix mt10">
                        <label class="pull-left">Approved Leaves</label>
                        <span class="label label-success pull-right"><a href="<c:url value='/approvedLeaves'/>" class="color_white" title="Approved Leaves">100</a></span>
                    </li>
                    <li class="clearfix mt10">
                        <label class="pull-left">Rejected Leaves</label>
                        <span class="label label-danger pull-right"><a href="<c:url value='/rejectedLeaves'/>" class="color_white" title="Rejected Leaves">100</a></span>
                    </li>
                    <li class="clearfix mt10">
                        <label class="pull-left">Cancel Leaves</label>
                        <span class="label label-warning pull-right"><a href="<c:url value='/cancelLeaves'/>" class="color_white" title="Cancel Leaves">100</a></span>
                    </li>
                </ul>
                      
                    </div>
                </div>
              </div>
              <div class="col-xs-12 col-sm-4 mb hidden">
                <div id="selfApproved" style="width: 100%; height: 150px;" class="amchartAnchor-Hide"> </div>
                <div class="leavescount text-center"> <a href="leavetype-approvedcount.html" id="approved" title="Approved">20</a> </div>
              </div>
              <div class="col-xs-12 col-sm-4 mb hidden">
                <div id="selfRejected" style="width: 100%; height: 150px;" class="amchartAnchor-Hide"> </div>
                <div class="leavescount text-center"> <a href="leavetype-rejectedcount.html" id="rejected" title="Rejected">10</a> </div>
              </div>
            </div>
          
            <div id="employee" class="tab-pane fade">
                
            <div class="db-widget-sm">
                <div class="width100percnt">
                  <div id="employeeLeaves" style="width: 60%; height: 150px;" class="amchartAnchor-Hide pull-left"> </div>
                  <div style="width: 40%; height: 150px;" class="countList pull-left">
                <ul class="list-unstyled list-group clearfix">
                    <li class="clearfix">
                        <label class="pull-left">Requested Leaves</label>
                        <span class="label label-primary pull-right"><a href="<c:url value='/employeeRequestedLeaves'/>" class="color_white" title="Pending Leaves">100</a></span>
                    </li>
                    <li class="clearfix mt10">
                        <label class="pull-left">Approved Leaves</label>
                        <span class="label label-success pull-right"><a href="<c:url value='/employeeApprovedLeaves'/>" class="color_white" title="Approved Leaves">100</a></span>
                    </li>
                    <li class="clearfix mt10">
                        <label class="pull-left">Rejected Leaves</label>
                        <span class="label label-danger pull-right"><a href="<c:url value='/employeeRejectedLeaves'/>" class="color_white" title="Rejected Leaves">100</a></span>
                    </li>
                    <li class="clearfix mt10">
                        <label class="pull-left">Cancel Leaves</label>
                        <span class="label label-warning pull-right"><a href="<c:url value='/employeeCancelLeaves'/>" class="color_white" title="Cancel Leaves">100</a></span>
                    </li>
                </ul>
                      
                    </div>
                </div>
              </div>    
                
              <div class="db-widget-sm hidden">
                <div class="col-xs-12 col-sm-4 mb">
                  <div id="empRequested" style="width: 100%; height: 150px;" class="amchartAnchor-Hide"> </div>
                  <div class="leavescount text-center"> <a href="javascript:void(0)" id="requested" title="Requested">70</a> </div>
                </div>
              
              <div class="col-xs-12 col-sm-4 mb">
                <div id="empApproved" style="width: 100%; height: 150px;" class="amchartAnchor-Hide"> </div>
                <div class="leavescount text-center"> <a href="javascript:void(0)" id="empApproved2" title="Approved">20</a> </div>
              </div>
              <div class="col-xs-12 col-sm-4 mb">
                <div id="empRejected" style="width: 100%; height: 150px;" class="amchartAnchor-Hide"> </div>
                <div class="leavescount text-center"> <a href="javascript:void(0)" id="empRejected2" title="Rejected">10</a> </div>
              </div>
                  </div>
            </div>
            
            </div>
        </div>
      </div>
    </div>
    <!-- Requirement ends --> 
  </div>
  <!-- Tasks and Timesheets columns ends --> 
<%-- </section> --%>
