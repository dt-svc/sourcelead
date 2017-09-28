<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 
<!-- Content Area -->
  <section class="content-wraper" id="themeroller">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Rejected</h2>
      </div>
		<div class="pull-right brud-crum"><a href="dashboard_us.html">Home</a> &raquo; <a href="dashboard-lavemanagement.html">Leave Management</a> &raquo; Rejected</div>
    </section>
    
<div class="content-area content-new clearfix">

<div class="timesheet_out">
  <div class="clearfix">
    <div class="timesheet_head">
      <ul>
          <li class="dropdown">
            <div class="pull-left select-fild">
                    <select onchange="location = this.value;">
                        <option value="employee-requestcount.html">Requested Leaves</option>
                        <option  value="employee-approvedcount.html">Approved Leaves</option>
                        <option value="employee-rejectedcount.html" selected>Rejected Leaves</option>
                        <option value="employee-cancelcount.html">Cancel Leaves</option>
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
            <th>Employee Name</th>
            <th>Leave Type</th>
            <th>Duration</th>
            <th>No of Days</th>
            <th>Leaves Taken</th>
            <th>Rejected By</th>
            <th>Reason</th>
          </tr>
        </thead>
        <tbody>
        <tr>
            <td>
              <div class="proj_show tbldata-dotdot-big">
                  <div class="pull-left tbldata-dotdot-me" title="BIS10041">Anandkumar Joshimanohar</div>
                  <i class="fa fa-binoculars pull-right"></i>
              </div>
            </td>
            <td>
                <div title="2days" class="tblminwidth-75">
                    Billable
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
              <div title="2 days" class="tbldata-dotdot-100">
                    2 days
                </div>
            </td>
            <td>
              <div title="No" class="tbldata-dotdot-100">
                    Yes
                </div>
            </td>
            <td>
              <div title="No" class="tbldata-dotdot-100">
                   HR Manager
                </div>
            </td>
            <td>
              <div title="No" class="tbldata-dotdot">
                    Required resources for urgent deliverables.
                </div>
            </td>
            <td class="hidden">
                <div class="tblminwidth-50">
                    <a href="javascript:void(0)"><b class="proj_closed">Cancel</b></a>
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
