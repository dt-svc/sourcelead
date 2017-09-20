<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 


  <!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Leave Type</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Leave Management &raquo; Leave Type</div>
    </section>
    <div class="content-area content-new clearfix">
      <div class="clearfix form-horizontal inner-content">
        <div class="row">
          <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
            <div class="form-group">
              <label class="col-md-3">Name<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-9">
                <input type="text" class="form-control" name="Name" value="">
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-3">Duration</label>
              <div class="col-md-9 dropdown-select">
                  <select class="form-control">
                      <option selected>--Select Duration--</option>
                      <option>Day</option>
                      <option>Hours</option>
                  </select>
              </div>
            </div>
              
              <div class="form-group">
              <label class="col-md-3">Hours</label>
              <div class="col-md-9 dropdown-select">
                  <div class="row">
                      <div class="col-md-9 paddingRight">
                   <input type="text" class="form-control" placeholder="Hours">
                          </div>
                      <div class="col-md-3 paddingLeft">
                  <select class="form-control">
                      <option>Am</option>
                      <option>Pm</option>
                  </select>
                      </div></div>
              </div>
            </div>
              
            <div class="form-group">
              <label class="col-md-3">Type</label>
              <div class="col-md-9">
                  <div class="clearfix mb"> 
              <label class="paddingLeft paddingRight">
                <input name="raise" type="radio">
                <span class="paddingLeft">Billable</span></label>
              <label class="paddingLeft">
                <input name="raise" type="radio">
                <span class="paddingLeft">Non Billable</span></label>
            </div>
                   
              </div>
            </div>
              <div class="form-group">
              <label class="col-md-3">Applicable to All Users</label>
              <div class="col-md-9">
                <div class="clearfix mb"> 
              <label class="paddingLeft paddingRight">
                <input name="users" type="radio" onClick="yesUsers()" checked="true">
                <span class="paddingLeft">Yes</span></label>
              <label class="paddingLeft">
                <input name="users" type="radio" id="noUsers" onClick="noUsers()">
                <span class="paddingLeft">No</span></label>
            </div>
              </div>
            </div>
              
              <div class="clearfix" id="no" style="display: none;">
                  <div class="clearfix"></div>
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
              <label class="col-md-3">Select Roles</label>
              <div class="col-md-9 dropdown-select">
                  <select class="form-control">
                      <option selected>--Select Roles--</option>
                      <option>Role-1</option>
                      <option>Role-2</option>
                  </select>
              </div>
            </div>
                  
              </div>
            <div class="form-group">
              <label class="col-md-3">Leaves Carryforward</label>
              <div class="col-md-9">
                <div class="clearfix mb"> 
              <label class="paddingLeft paddingRight">
                <input name="users" type="radio">
                <span class="paddingLeft">Yes</span></label>
              <label class="paddingLeft">
                <input name="users" type="radio">
                <span class="paddingLeft">No</span></label>
            </div>
              </div>
                
            </div>
                          <div class="form-group">
              <label class="col-md-3">Number of Leaves per year</label>
              <div class="col-md-9">
                <input type="text" class="form-control" name="Policy Name" value="" placeholder="Number of Leaves Per Year">
              </div>
            </div>                                   

              <div class="form-group">
              <label class="col-md-3">Description</label>
              <div class="col-md-9">
                  <textarea class="form-control" rows="2"></textarea>
              </div>
            </div>
              <div class="clearfix"></div>
            <div class="form-group mt10">
              <div class="col-md-12 checkBox">
                <input type="checkbox" name="leavepolicy" value="" onClick="leavePolicy()" id="leavePolicy">Leave Policy
              </div>
            </div>
              <div class="clearfix"></div>
              <div class="clearfix req-dtls" id="leavepolicy-div" style="display: none;">
                  <h3>Leave Policy</h3>
              <div class="form-group">
              <label class="col-md-3">Policy Name</label>
              <div class="col-md-9">
                <input type="text" class="form-control" name="Policy Name" value="">
              </div>
            </div>
                  <div class="form-group">
              <label class="col-md-3"></label>
              <div class="col-md-9 checkBox">
                <input type="checkbox" name="Policy Name" value="">Visiblity All Users
              </div>
            </div>
              <div class="clearfix">
              <label class="col-md-12">Description</label>
              <div class="col-md-12 txtEditor_p">
               <textarea class="txtEditor"></textarea>
              </div>
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
          </div>
        </div>
      </div>
      
      <!-- Content Area Ends--> 
    </div>
  </section>


<jsp:include page="../masterFooter.jsp" />
