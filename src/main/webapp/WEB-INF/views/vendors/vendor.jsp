<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="resources/js/jquery-fun.js"></script> 
<script src="resources/js/formvalidator.js"></script>

    <!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Create Vendors</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Vendor &raquo; Add Vendor</div>
    </section>
    <div class="content-area content-new clearfix">
      <div class="clearfix form-horizontal inner-content">
        <div class="row">
          <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
           <h3>Vendor Details</h3>
            <div class="form-group">
              <label class="col-md-3">Company<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-9">
                <input type="text" class="form-control" name="Company Name" value="">
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-3">Website</label>
              <div class="col-md-9">
                <input type="text" class="form-control" name="Website" value="">
              </div>
            </div>
            
            <div class="form-group">
              <label class="col-md-3">Country</label>
              <div class="col-md-9">
                <select class="form-control selectpicker">
                  <option>India</option>
                  <option>USA</option>
                  <option>UK</option>
                  <option>Japan</option>
                  <option>China</option>
                </select>
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-3">State</label>
              <div class="col-md-9">
                <input type="text" class="form-control">
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-3">City</label>
                <div class="col-md-9">
                <input type="text" class="form-control">
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-3">Level</label>
              <div class="col-md-9">
                <div class="form-icon">
                  <select class="chosen-select form-control">
                    <option>Select Level</option>
                    <option>Level-1</option>
                    <option>Level-2</option>
                    <option>Level-3</option>
                  </select>
                  <i data-target="#addLevel" data-toggle="modal" class="fa fa-plus btn btn-default formic1-new"></i> </div>
                <div role="dialog" id="addLevel" class="modal fade">
                  <div class="modal-dialog">
                    <div class="modal-content">
                      <div class="modal-header">
                        <button data-dismiss="modal" class="close" type="button"><i class="fa fa-close"></i></button>
                        <h4 class="modal-title">Add Level</h4>
                      </div>
                      <div class="modal-body clearfix">
                        <div class="form-horizontal custom-form clearfix">
                          <div class="col-xs-12 col-md-12 col-lg-12">
                            <div class="form-group">
                              <label class="col-lg-3">Level Name</label>
                              <div class="col-lg-9">
                                <input type="text" class="form-control" value="">
                              </div>
                            </div>
                            <div class="form-group">
                              <label class="col-lg-3">Description </label>
                              <div class="col-lg-9">
                                <textarea class="form-control" rows="2"></textarea>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                      <div class="modal-footer">
                        <button data-dismiss="modal" class="btn btn-primary" type="button">Save</button>
                        <button data-dismiss="modal" class="btn btn-default" type="button">Cancel</button>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
            <h3>Contact Details</h3>
            <div class="form-group">
              <label class="col-md-3">First Name<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-9">
                <input type="text" class="form-control" name="First Name" value="">
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-3">Last Name</label>
              <div class="col-md-9">
                <input type="text" class="form-control" name="Last Name" value="">
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-3">Email<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-9">
                <input type="text" class="form-control" name="Email" value="">
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-3">Phone Number</label>
              <div class="col-md-9">
                <input type="text" class="form-control" name="Email" value="">
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-3">Status</label>
              <div class="col-md-9">
                <select class="form-control selectpicker">
                  <option>Active</option>
                  <option>In-Active</option>
                </select>
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-3">Owner</label>
              <div class="col-md-9">
                <select multiple class="chosen-select form-control">
                  <option>Select Owner</option>
                  <option>Owner-1</option>
                  <option>Owner-2</option>
                  <option>Owner-3</option>
                </select>
              </div>
            </div>
            </div>
            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
            <div class="form-group">
              <label class="col-md-3"></label>
              <div class="col-md-9 checkbox">
                <label>
                  <input type="checkbox" value="" id="cd">
                  Send Invitation</label>
              </div>
            </div>
            <div class="form-group hidden clientDetails">
              <label class="col-md-3">Role <span class="text-danger">*</span></label>
              <div class="col-md-9 checkbox check-box">
                <label>
                  <input type="radio" value="" name="role">
                  Admin</label>
                <label>
                  <input type="radio" value=""  name="role">
                  Project Manager</label>
                <label>
                  <input type="radio" value="" name="role">
                  HR Manager</label>
                <label>
                  <input type="radio" value="" name="role">
                  Account Manager</label>
                <label>
                  <input type="radio" value="" name="role">
                  Recruiter</label>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="form-footer clearfix">
        <div class="col-lg-12">
          <div class="pull-right">
            <input type="button" value="Save" class="btn btn-primary">
            <input type="button" value="Cancel" class="btn btn-default ml">
          </div>
        </div>
      </div>
      
      <!-- Content Area Ends--> 
    </div>
  </section>

<jsp:include page="../masterFooter.jsp" />