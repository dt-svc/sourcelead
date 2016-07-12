<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
  <!-- Content Area -->
  <section class="content-wraper">
        <section class="content-header clearfix">
          <div class="pull-left">
            <h2>Create User</h2>
          </div>
          <div class="pull-right brud-crum">Home &raquo; General Form &raquo; Create User</div>
        </section>
        <div class="content-area clearfix">
          <section class="action-btns clearfix">
            <ul class="pull-right list-unstyled list-inline">
              <li><a href="#" class="faclose"><i aria-hidden="true" class="fa fa-close"></i></a></li>
              <li><a href="#" class="collpase-div"><i aria-hidden="true" class="fa fa-angle-down"></i></a></li>
            </ul>
          </section>
          <div class="clearfix form-area form-horizontal toggle-div remove-div">
            <div class="row">
              <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                <div class="form-group">
                  <label class="col-md-3">Employee Id</label>
                  <div class="col-md-9">
                    <input type="text" class="form-control" name="Employee Id" value="">
                    <span class="hidden">Error Text</span></div>
                </div>
                <div class="form-group">
                  <label class="col-md-3">First Name<span class="text-danger">&nbsp;*</span></label>
                  <div class="col-md-9">
                    <input type="text" class="form-control" name="First Name" value="">
                    <span class="hidden">First Name</span>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-md-3">Last Name</label>
                  <div class="col-md-9">
                    <input type="text" class="form-control" name="Last Name" value="">
                    <span class="hidden">Last Name</span>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-md-3">Email Id<span class="text-danger">&nbsp;*</span></label>
                  <div class="col-md-9">
                    <input type="text" class="form-control" name="Email Id" value="">
                    <span class="hidden">Valid Email</span>
                  </div>
                  </div>
                <div class="form-group">
                  <label class="col-md-3">Phone Number</label>
                   <div class="col-md-9">
                    <input type="text" class="form-control" name="Phone Number" value="">
                    <span class="hidden">Phone Number</span>
                  </div>
                </div>
                 <div class="form-group">
                  <label class="col-md-3">Rate</label>
                  <div class="col-md-9">
                    <div class="row">
                        <div class="col-md-6">
                    <input type="text" class="form-control" name="Rate" value="">
                    <span class="hidden">Rate</span>
                  </div>
                        <div class="col-md-6">
                    <input type="text" class="form-control" name="Rate" value="">
                    <span class="hidden">Rate</span>
                  </div>
                    </div>
                    <span class="hidden">Select Check Box</span> </div>
                </div>
              </div>
              <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                <div class="form-group">
                  <label class="col-md-3">Department</label>
                  <div class="col-md-9">
                    <div class="form-icon">
                      <select class="form-control select-bg">
                        <option>Department</option>
                        <option>Department-2</option>
                        <option>Department-3</option>
                        <option>Department-4</option>
                      </select>
                      <i class="fa fa-plus btn btn-default formic1" data-toggle="modal" data-target="#addDepartment"></i> </div>
                    <span class="hidden">Enter Project Id</span>
                    <div class="modal fade" id="addDepartment" role="dialog">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"><i class="fa fa-close"></i></button>
            <h4 class="modal-title">Add Department</h4>
          </div>
          <div class="modal-body clearfix">
            <div class="form-horizontal custom-form clearfix">
              <div class="col-xs-12 col-md-12 col-lg-12">
                <div class="form-group">
                  <label class="col-lg-3">Client Name</label>
                  <div class="col-lg-9">
                    <input type="text" value="" class="form-control">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-lg-3">Client Website</label>
                  <div class="col-lg-9">
                    <input type="text" value="" class="form-control">
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-primary" data-dismiss="modal">Save</button>
            <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
          </div>
        </div>
      </div>
    </div>
                    </div>
                </div>
                <div class="form-group">
                  <label class="col-md-3">Role <span class="text-danger">&nbsp; *</span></label>
                  <div class="col-md-9">
                    <label class="radio-inline">
                      <input type="radio" name="optradio">Administrator
                    </label>
                    <label class="radio-inline">
                      <input type="radio" name="optradio">Approver
                    </label>
                    <label class="radio-inline">
                      <input type="radio" name="optradio">Employee
                    </label>
                    <span class="hidden">Select Role</span> 
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-md-3">Project</label>
                  <div class="col-md-9">
                    <div class="form-icon">
                      <select class="form-control select-bg">
                        <option>Project</option>
                        <option>Project-2</option>
                        <option>Project-3</option>
                        <option>Project-4</option>
                      </select>
                      <i class="fa fa-plus btn btn-default formic1" data-toggle="modal" data-target="#addProject"></i> </div>
                    <span class="hidden">Select Project</span>
                    <div class="modal fade" id="addProject" role="dialog">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"><i class="fa fa-close"></i></button>
            <h4 class="modal-title">Add Project</h4>
          </div>
          <div class="modal-body clearfix">
            <div class="form-horizontal custom-form clearfix">
              <div class="col-xs-12 col-md-12 col-lg-12">
                <div class="form-group">
                  <label class="col-lg-3">Client Name</label>
                  <div class="col-lg-9">
                    <input type="text" value="" class="form-control">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-lg-3">Client Website</label>
                  <div class="col-lg-9">
                    <input type="text" value="" class="form-control">
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-primary" data-dismiss="modal">Save</button>
            <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
          </div>
        </div>
      </div>
    </div>
                    </div>
                </div>
                  
                  <div class="form-group">
                  <label class="col-md-3">Manager</label>
                  <div class="col-md-9">
                      <input type="text" value="Project Manager" class="form-control" disabled>
              </div>
            </div>
                  <div class="form-group">
                  <label class="col-md-3">Timesheet Frequency<span class="text-danger">&nbsp;*</span></label>
                  <div class="col-md-9">
                      <select class="form-control">
                        <option>Frequency-1</option>
                        <option>Frequency-1</option>
                        <option>Frequency-1</option>
                        <option>Frequency-1</option>
                      </select>
              </div>
            </div>
                  <div class="form-group">
                  <label class="col-md-3">Day Hours</label>
                  <div class="col-md-9">
                      <div class="input-group">
                      <input type="text" value="" class="form-control" id="datetimepicker3">
                      <span class="input-group-addon"><i class="fa fa-clock-o"></i></span>
                      </div>
              </div>
            </div>
                  </div>
                </div>
            <div class="form-footer clearfix row">
              <div class="col-lg-12">
                <div class="pull-right">
                  <input type="button" value="Save" class="btn btn-primary">
                  <input type="button" value="Cancel" class="btn btn-default ml">
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>
      <!-- Content Area Ends--> 

      <!-- Footer Area -->
      <footer class="footer-area clearfix">
        <div class="copyright"> @ Copyright : Source Lead, All right reserved </div>
        <div class="powered-by"> Powered by: businessintelli.com </div>
      </footer>
      <!-- Footer Area --> 
    </div>

    