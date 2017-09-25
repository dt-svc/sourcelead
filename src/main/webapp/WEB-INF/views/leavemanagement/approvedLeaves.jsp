<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <!-- Content Area -->
  <section class="content-wraper" id="themeroller">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Approved</h2>
      </div>
		<div class="pull-right brud-crum"><a href="dashboard_us.html">Home</a> &raquo; <a href="dashboard-lavemanagement.html">Leave Management</a> &raquo; Approved</div>
    </section>
    
<div class="content-area content-new clearfix">

<div class="timesheet_out">
  <div class="clearfix">
    <div class="timesheet_head">
      <ul>
         <li class="dropdown">
                <div class="pull-left select-fild">
                        <select onchange="location = this.value;">
                        <option value="leavetype-pendingcount.html">Pending Leaves</option>
                        <option  value="leavetype-approvedcount.html" selected>Approved Leaves</option>
                        <option value="leavetype-rejectedcount.html">Rejected Leaves</option>
                        <option value="leavetype-cancelcount.html">Cancel Leaves</option>
                    </select>
                </div>
         </li>
        <div class="clearfix"></div>
      </ul>
      <div class="clearfix"></div>
      <div class="remove_search">
        <input placeholder="Search" type="text">
      </div>
    </div>
    <div class="require_wrap_table">
      <table>
        <thead>
          <tr>
            <th>Leave Type</th>
            <th>Duration</th>
            <th>Applied On</th>
            <th>Reason</th>
            <th>Approvers</th>
            <th>Approved By</th>
            <th>Status</th>
          </tr>
        </thead>
        <tbody>
        <tr>
            <td>
              <div class="proj_show tbldata-dotdot-big">
                  <div class="pull-left tbldata-dotdot-me" title="">Non Billable</div>
                  <i class="fa fa-binoculars pull-right"></i>
              </div>
            </td>
            <td>
                <div title="Duration" class="tblminwidth-75">
                   <div class="width100percnt">
                        09/10/2017
                    </div>
                    <div class="width100percnt mt2">
                        09/12/2017
                    </div>
                </div>
            </td>
            <td>
              <div title="" class="tbldata-dotdot-100">
                    08/20/2017
                </div>
            </td>
            <td>
                <div title="" class="tbldata-dotdot-100">
                    Personal
                </div>
            </td>
            <td>
                <div title="" class="tbldata-dotdot-100">
                    Approvers
                </div>
            </td>
            <td>
                <div title="" class="tbldata-dotdot-100">
                   Admin
                </div>
            </td>
            <td>
                <div title="10" class="tblminwidth-75">
                    <b class="proj_active">Approved</b>
                </div>
            </td>
            <td class="hidden">
                <div class="tblminwidth-50">
                    <span title="Edit" class="paddingRight"><i class="fa fa-pencil text-primary cursorPointer"></i></span>
                    <span title="Delete" class="paddingLeft"><i class="fa fa-trash text-danger cursorPointer"></i></span>
                </div>
            </td>
          </tr>
          </tbody>
      </table>
    </div>
    <nav class="clearfix">
      <ul id="paging" class="pagination pull-right mr">
        <li><a href="#">Previous</a></li>
        <li><a href="#">2</a></li>
        <li><a href="#">3</a></li>
        <li class="disabled"><a href="#">6</a></li>
        <li data-page="last"><a href="#">Next</a></li>
      </ul>
      <div class="clearfix"></div>
    </nav>
  </div>
</div>

    </div>
    <!-- Content Area Ends--> 
  </section>
  <!-- Content Area Ends--> 







<jsp:include page="../masterFooter.jsp" />