<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 

  <!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Leave Options</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Leave Management &raquo; Leave Options</div>
    </section>
    <div class="content-area content-new clearfix">
      <div class="clearfix form-horizontal inner-content">
        <div class="row">
          <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
            <div class="form-group">
              <label class="col-md-3">Select Department</label>
              <div class="col-md-9 dropdown-select">
                <select class="form-control">
                  <option selected>--Select Department--</option>
                  <option>Department-1</option>
                  <option>Department-2</option>
                </select>
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-3">Select Role</label>
              <div class="col-md-9 dropdown-select">
                <select class="form-control">
                  <option selected>--Select Role--</option>
                  <option>Role-1</option>
                  <option>Role-2</option>
                </select>
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-3">Calendar Month</label>
              <div class="col-md-9 dropdown-select">
                <select class="form-control">
                  <option selected>--Select Month--</option>
                  <option>Month-1</option>
                  <option>Month-2</option>
                </select>
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-3">Weekends</label>
              <div class="col-md-9 dropdown-select">
                <select class="form-control">
                  <option selected>--Select Day--</option>
                  <option>Sunday</option>
                  <option>Monday</option>
                  <option>Tuesday</option>
                  <option>Wednesday</option>
                  <option>Thursday</option>
                  <option>Friday</option>
                  <option>Saturday</option>
                </select>
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-3">Halfday Request</label>
              <div class="col-md-9 dropdown-select">
                <select class="form-control">
                  <option selected>--Select Request--</option>
                  <option>Yes</option>
                  <option>No</option>
                </select>
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-3">Leave Transfer</label>
              <div class="col-md-9 dropdown-select">
                <select class="form-control">
                  <option selected>--Select Transfer--</option>
                  <option>Yes</option>
                  <option>No</option>
                </select>
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-3">Working Hours</label>
              <div class="col-md-9 dropdown-select">
                <input type="text" class="form-control" placeholder="Working Hours">
              </div>
            </div>
          </div>
        </div>
      </div>
    <div class="form-footer clearfix mt">
      <div class="col-lg-12">
        <div class="pull-right">
          <input type="button" value="Save" class="btn btn-primary">
          <input type="button" value="Cancel" class="btn btn-default ml10">
        </div></div></div>
      </div>
  </section>


<jsp:include page="../masterFooter.jsp" />
