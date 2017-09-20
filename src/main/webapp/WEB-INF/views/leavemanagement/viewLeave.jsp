<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

 <!-- Content Area -->
  <section class="content-wraper" id="themeroller">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Leave Type</h2>
      </div>
		<div class="pull-right brud-crum"><a href="dashboard_us.html">Home</a> &raquo; <a href="leavetype.html">Leave Management</a> &raquo; Leave Type</div>
    </section>
    
<div class="content-area content-new clearfix">

<div class="timesheet_out">
  <div class="clearfix">
    <div class="timesheet_head">
      <ul>
          <li>
              <a href="leavetype.html" title="Add Leave Type">
                <i class="fa fa-plus paddingRight"></i>Add Leave Type
              </a>
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
            <th>Name</th>
            <th>Duration</th>
            <th>Type</th>
            <th>No.of Leaves</th>
            <th>Status</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
        <tr>
            <td>
              <div class="proj_show tbldata-dotdot-big">
                  <div class="pull-left tbldata-dotdot-me" title="Sick Leave">Casual Leave</div>
                  <i class="fa fa-binoculars pull-right"></i>
              </div>
            </td>
            <td>
                <div title="2days" class="tblminwidth-75">
                    Fullday
                </div>
            </td>
            <td>
              <div title="TCS, Hyderabad" class="tbldata-dotdot-100">
                Non Billable
                </div>
            </td>
            <td>
                <div title="2" class="tblminwidth-75">
                    2
                </div>
            </td>
            <td>
                <div title="10" class="tblminwidth-75">
                    <b class="proj_closed">Inactive</b>
                </div>
            </td>
            <td>
                <div class="tblminwidth-50">
                    <span title="Edit" class="paddingRight"><i class="fa fa-pencil text-primary cursorPointer"></i></span>
                    <span title="Delete" class="paddingLeft"><i class="fa fa-trash text-danger cursorPointer"></i></span>
                </div>
            </td>
          </tr>
          <tr>
            <td>
              <div class="proj_show tbldata-dotdot-big">
                  <div class="pull-left tbldata-dotdot-me" title="Sick Leave">Sick Leave</div>
                  <i class="fa fa-binoculars pull-right"></i>
              </div>
            </td>
            <td>
                <div title="2days" class="tblminwidth-75">
                    Fullday
                </div>
            </td>
            <td>
              <div title="TCS, Hyderabad" class="tbldata-dotdot-100">
                Non Billable
                </div>
            </td>
            <td>
                <div title="2" class="tblminwidth-75">
                    2
                </div>
            </td>
            <td>
                <div title="10" class="tblminwidth-75">
                    <b class="proj_active">Active</b>
                </div>
            </td>
            <td>
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
