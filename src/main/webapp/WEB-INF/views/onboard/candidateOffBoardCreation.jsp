<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<link rel="stylesheet" href="resources/css/chosen.min.css">
<script src="resources/js/chosen.jquery.min.js"></script> 
<script src="resources/js/jquery-fun.js"></script> 
<script src="resources/js/formvalidator.js"></script>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Employee offboarding</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Employee onboarding</div>
    </section>
    <div class="content-area content-new clearfix">
      <div class="applicant_accept">
        <div class="clearfix onboardBtns">
          <div class="btn-group pull-right">
            <button class="btn" id="prevtab" type="button"><i class="fa fa-angle-double-left paddingRight"></i>Prev</button>
            <button class="btn" id="nexttab" type="button">Next<i class="fa fa-angle-double-right paddingLeft"></i></button>
          </div>
        </div>
        <div class="tabbable">
          <ul class="nav nav-tabs" id="tab_bar">
            <li class="active"> <a href="#details" data-toggle="tab"> <i class="fa fa-th-list paddingRight"></i> <span>Details</span> <br>
              <small>Employee details &amp; Postion details</small> </a> </li>
            <li><a href="#documents" data-toggle="tab"> <i class="fa fa-files-o paddingRight"></i> <span>Documents</span> <br>
              <small>Acceptance &amp; Policy Documents</small> </a> </li>
            <li> <a href="#tasks" data-toggle="tab"> <i class="fa fa-check-square-o paddingRight"></i> <span>Taks</span> <br>
              <small>List of tasks</small> </a></li>
          </ul>
          <div class="tab-content"> 
            
            <!-- Employee Details -->
            <div class="tab-pane active" id="details">
              <div class="form-horizontal">
                <p><strong>Employee Details</strong></p>
                <div class="form-group">
                  <label class="col-md-3 control-label">Select User</label>
                  <div class="col-md-6">
                    <select class="chosen-select" data-placeholder="Select User">
                      <option disabled selected>-Select User-</option>
                      <option>User-1</option>
                      <option>User-2</option>
                      <option>User-3</option>
                    </select>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-md-3 control-label">First Name <span class="text-danger paddingLeft">*</span></label>
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm" value="">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-md-3 control-label">Last Name <span class="text-danger paddingLeft">*</span></label>
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm" value="">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-md-3 control-label">Street Addess 1 <span class="text-danger paddingLeft">*</span></label>
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm" value="">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-md-3 control-label">Street Addess 2</label>
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm" value="">
                  </div>
                </div>
                  
                                  <div class="form-group">
                  <label class="col-md-3 control-label">Country <span class="text-danger paddingLeft">*</span></label>
                  <div class="col-md-6">
                    <select class="chosen-select" data-placeholder="Select Country">
                      <option disabled>Select Country</option>
                      <option>United States of America</option>
                      <option>United Kingdom</option>
                      <option>India</option>
                      <option>Germany</option>
                      <option>New Zealand</option>
                    </select>
                  </div>
                </div>

                <div class="form-group">
                  <label class="col-md-3 control-label">State</label>
                  <div class="col-md-6">
                    <select class="chosen-select" data-placeholder="Select State">
                      <option disabled>Select State</option>
                      <option>Andra Pradesh</option>
                      <option>Telangana</option>
                      <option>Uttar Pradesh</option>
                      <option>Karnataka</option>
                      <option>Kerala</option>
                    </select>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-md-3 control-label">City</label>
                  <div class="col-md-6">
                    <select class="chosen-select" data-placeholder="Select State">
                      <option disabled>Select State</option>
                      <option>Andra Pradesh</option>
                      <option>Telangana</option>
                      <option>Uttar Pradesh</option>
                      <option>Karnataka</option>
                      <option>Kerala</option>
                    </select>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-md-3 control-label">Zipcode</label>
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm" value="">
                  </div>
                </div>


                <div class="form-group">
                  <label class="col-md-3 control-label">Personal Email <span class="text-danger paddingLeft">*</span></label>
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm" value="">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-md-3 control-label">Company Email</label>
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm" value="">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-md-3 control-label">Mobile number <span class="text-danger paddingLeft">*</span></label>
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm" value="">
                  </div>
                </div>
                  <div class="form-group">
                  <label class="col-md-3 control-label">Joined date<span class="text-danger paddingLeft">*</span></label>
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm calendar" value="" id="startDate">
                  </div>
                </div>
                  
                <div class="clearfix"></div>
                <p><strong>Offboard Details</strong></p>
<div class="form-group">
                  <label class="col-md-3 control-label">End date<span class="text-danger paddingLeft">*</span></label>
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm calendar" value="" id="endDate">
                  </div>
                </div>
                  <div class="form-group">
                      <label class="col-md-3 control-label">Reasons <span class="paddingLeft text-danger">*</span></label>
                  <div class="col-md-6">
                      <select class="form-control selectpicker">
                          <option selected disabled>- Select Reason -</option>
                          <option>Resignation</option>
                          <option>Termination</option>
                          <option>End of the contract</option>
                          <option>Others</option>
                      </select>
                  </div>
                </div>

            <div class="form-group">
                  <label class="col-md-3 control-label">Explanation</label>
                  <div class="col-md-6">
                    <input type="text" value="" class="form-control input-sm">
                 </div>
            </div>

                  
                  <div class="form-group">
                  <label class="col-md-3 control-label">Notice Period</label>
                  <div class="col-md-6 select-field">
                    <select class="form-control" data-placeholder="Select Notice Period">
                      <option disabled selected>Select Notice Period</option>
                      <option>15 Days</option>
                      <option>1 Month</option>
                      <option>2 Months</option>
                      <option>3 Months</option>
                    </select>
                  </div>
                </div>
                  
                  
                  <div class="form-group">
                  <label class="col-md-3 control-label">Effective Date<span class="text-danger paddingLeft">*</span></label>
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm calendar" value="" id="effectiveDate">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-md-3 control-label">Have to work in notice period ?<span class="text-danger paddingLeft">*</span></label>
                  <div class="col-md-6">
                      <label class="radio-inline">
                    <input type="radio" value="" name="noticeperiod"> Yes
                          </label>
                      <label class="radio-inline">
                    <input type="radio" value="" name="noticeperiod"> No
                    </label>
                  </div>
                </div>
                  <div class="form-footer">
                  <div class="pull-right mr">
                    <input type="button" value="Cancel" class="btn btn-sm btn-default">
                    <input type="button" value="Save" class="btn btn-sm btn-success">
                    <input type="button" value="Save and Continue" class="btn btn-sm btn-primary">
                  </div>
                </div>
              </div>
            </div>
            
            <!-- TAB 2 -->
            <div class="tab-pane" id="documents">
              
                <div class="form-horizontal">
                <div class="form-group">
                  <label class="col-md-12">
                    <strong>Resignation letter</strong>
                    </label>
                    <label class="col-md-4 control-label"></label>
                  <div class="col-md-7">
                      <button type="button" class="btn btn-default btn-sm"><i class="fa fa-plus paddingRight"></i>Upload additional contract files</button>
                  </div>
                </div>
                  
                  
                <div class="form-group">
                  <label class="col-md-12">
                      <strong>Offboard pack</strong> <br>
                    </label>

                    <label class="col-md-4 control-label"></label>
                  <div class="col-md-7">
                      <button type="button" class="btn btn-default btn-sm"><i class="fa fa-plus paddingRight"></i>Upload additional policies files</button>
                  </div>
                </div>
                <div class="clearfix"></div>
                <div class="form-footer">
                  <div class="pull-right mr">
                    <input type="button" value="Cancel" class="btn btn-sm btn-default">
                    <input type="button" value="Save" class="btn btn-sm btn-success">
                    <input type="button" value="Save and Continue" class="btn btn-sm btn-primary">
                  </div>
                </div>
              </div>
                
                
                
            </div>
            <div class="tab-pane" id="tasks">
              <div class="clearfix">
                  <h4 class="pull-left">Task List</h4>
                <div class="pull-right">
                  <button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#addTask"><i class="fa fa-plus paddingRight"></i>Add Task</button>
                  <!-- Modal -->
                  <div id="addTask" class="modal fade" role="dialog">
                    <div class="modal-dialog modal-lg">
                      <div class="modal-content">
                        <div class="modal-header">
                          <button type="button" class="close" data-dismiss="modal">&times;</button>
                          <h4 class="modal-title">Add Tasks</h4>
                        </div>
                        <div class="modal-body clearfix"> 
                          
 <!-- Add New Task Widget -->
                          <div class="col-md-12 clearfix">
                            <form class="form-horizontal clearfix task-widget">
                              <div class="row">
                                <div class="col-md-6">
                                  <label>Subject <span class="text-danger paddingLeft">*</span></label>
                                  <input type="text" class="form-control input-sm" placeholder="Subject">
                                </div>
                                <div class="col-md-6">
                                  <label>Assign To <span class="text-danger paddingLeft">*</span></label>
                                    <div class="clearfix assignRadio">
                                        <label class="radio-inline"><input type="radio" name="assignto" value="assignUsers" id="assignUsers">Users</label>
                                        <label class="radio-inline"><input type="radio" name="assignto"  value="assignManager" id="assignManager">Manager</label>
                                        <label class="radio-inline"><input type="radio" name="assignto"  value="assignEmployee" id="assignEmployee">Employee</label>
                                    </div>
                                    <div id="users" class="mt10" style="display: none;">
                                    <select class="chosen-select" multiple data-placeholder="Select Priority" id="priority">
                                        <option>High</option>
                                        <option>Low</option>
                                        <option>Mediam</option>
                                    </select>
                                    </div>
                                    <div id="manager" class="mt10" style="display: none;">
                                        <input type="text" class="form-control input-sm" value="Manager">
                                    </div>
                                    <div id="employee" class="mt10" style="display: none;">
                                        <input type="text" class="form-control input-sm" value="Employee">
                                    </div>

                                  </div>
                                <div class="col-md-6 dropdown-select">
                                  <label>Priority</label>
                                  <select class="form-control">
                                    <option selected disabled>-- Select Priority --</option>
                                    <option>High</option>
                                    <option>Low</option>
                                    <option>Mediam</option>
                                  </select>
                                </div>
                                <div class="col-md-6 mt10">
                                  <div class="row">
                                    <div class="col-md-12">
                                        <label><strong>Due after:</strong><span class="text-danger paddingLeft">*</span></label>
                                      <div class="input-group hidden">
                                          <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                        <input class="form-control" value="" name="Date" id="datepicker2" type="text">
                                      </div>
                                        
                                        <!-- Form inline Due after -->
                                        
                        <div class="row">
                            <div class="col-md-3 paddingRight">
                                <label class="small">Months</label> <br>
                                <input class="form-control input-sm" type="text">
                            </div>
                            <div class="col-md-3 paddingRight paddingLeft">
                                <label class="small">Days</label> <br>
                                <input class="form-control input-sm" type="text">
                            </div>
                            <div class="col-md-3 paddingRight paddingLeft">
                                <label class="small">Hours</label> <br>
                                <input type="text" class="form-control input-sm">
                            </div>
                            <div class="col-md-3 paddingRight paddingLeft">
                                <label class="small">Minutes</label> <br>
                                <input type="text" class="form-control input-sm">
                            </div>
                        </div>
                                        
                                        <!-- Form inline Due after -->
                                        
                                        
                                        
                                        
                                    </div>
                                    <div class="col-md-6 hidden">
                                      <label>Time</label>
                                      <div class="input-group"> <span class="input-group-addon"><i class="fa fa-clock-o"></i></span>
                                        <input class="form-control" value="" name="Time" id="example" type="text">
                                      </div>
                                    </div>
                                  </div>
                                </div>
                                <div class="col-md-6 dropdown-select">
                                  <label>Repeat</label>
                                  <select class="form-control input-sm">
                                    <option selected disabled>-- Select Repeat --</option>
                                    <option>Never</option>
                                    <option>Daily</option>
                                    <option>Weekly</option>
                                    <option>Monthly</option>
                                    <option>Yearly</option>
                                  </select>
                                </div>
                                <div class="col-md-6">
                                  <div class="mt">
                                    <label class="checkbox-inline">
                                      <input type="checkbox" name="chkbox">
                                      Email </label>
                                    <label class="checkbox-inline">
                                      <input type="checkbox" name="chkbox">
                                      Notification </label>
                                  </div>
                                </div>
                                <div class="col-md-6">
                                  <label>Description</label>
                                  <textarea rows="2" placeholder="Description" class="form-control"></textarea>
                                </div>
                                <div class="clearfix"></div>
                                <div class="clearfix mt col-md-12">
                                  <div class="pull-right">
                                    <input type="button" class="btn btn-primary btn-sm" value="Save">
                                    <input type="button" onClick="closeModal();" class="btn btn-default btn-sm ml10" value="Cancel">
                                  </div>
                                </div>
                              </div>
                            </form>
                          </div>
                          <!-- Add New Task Widget -->                           
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
                
                <div class="clearfix taskwidget_inner">
                    <div class="media-body">
                    <div class="clearfix taskheder">
                        <label>
                            <span class="paddingRight">Subject:</span> <strong class="text-primary">Account Setup</strong>
                        </label>
                        <label class="small pull-right text-muted">
                            <span>Due after 15days</span>
                        </label>
                    </div>
                    <ul class="list-inline list-description">
                        <li><span>Assign To:</span> Pramodkumar Mahagen</li>
                        <li><span>Priority:</span> High</li>
                        <li><span>Repeat:</span> Never</li>
                    </ul>
                    </div>
                </div>
                <div class="clearfix taskwidget_inner">
                    <div class="media-body">
                    <div class="clearfix taskheder">
                        <label>
                            <span class="paddingRight">Subject:</span> <strong class="text-primary">Desktop arriange</strong>
                        </label>
                        <label class="small pull-right text-muted">
                            <span>Due after 2 months</span>
                        </label>
                    </div>
                    <ul class="list-inline list-description">
                        <li><span>Assign To:</span> Pramodkumar Mahagen</li>
                        <li><span>Priority:</span> High</li>
                        <li><span>Repeat:</span> Never</li>
                    </ul>
                    </div>
                </div>
                <div class="clearfix taskwidget_inner">
                    <div class="media-body">
                    <div class="clearfix taskheder">
                        <label>
                            <span class="paddingRight">Subject:</span> <strong class="text-primary">Providing access</strong>
                        </label>
                        <label class="small pull-right text-muted">
                            <span>Due after 3 months</span>
                        </label>
                    </div>
                    <ul class="list-inline list-description">
                        <li><span>Assign To:</span> Pramodkumar Mahagen</li>
                        <li><span>Priority:</span> High</li>
                        <li><span>Repeat:</span> Never</li>
                    </ul>
                    </div>
                </div>
                <div class="clearfix taskwidget_inner">
                    <div class="media-body">
                    <div class="clearfix taskheder">
                        <!--<label>Account Setup, Desk top Arriange, Providing access to the Candidate</label>-->
                        <label>
                            <span class="paddingRight">Subject:</span> <strong class="text-primary">Account Setup</strong>
                        </label>
                        <label class="small pull-right text-muted">
                            <span>Due after 3 months</span>
                        </label>
                    </div>
                    <ul class="list-inline list-description">
                        <li><span>Assign To:</span> Pramodkumar Mahagen</li>
                        <li><span>Priority:</span> High</li>
                        <li><span>Repeat:</span> Never</li>
                    </ul>
                    </div>
                </div>
                <div class="clearfix taskwidget_inner">
                    <div class="media-body">
                    <div class="clearfix taskheder">
                        <!--<label>Account Setup, Desk top Arriange, Providing access to the Candidate</label>-->
                        <label>
                            <span class="paddingRight">Subject:</span> <strong class="text-primary">Account Setup</strong>
                        </label>
                        <label class="small pull-right text-muted">
                            <span>Due after 15 days</span>
                        </label>
                    </div>
                    <ul class="list-inline list-description">
                        <li><span>Assign To:</span> Pramodkumar Mahagen</li>
                        <li><span>Priority:</span> High</li>
                        <li><span>Repeat:</span> Never</li>
                    </ul>
                    </div>
                </div>
                              <div class="form-footer clearfix mt">
                <div class="pull-right mr">
                  <input type="button" value="Cancel" class="btn btn-sm btn-default">
                  <input type="button" value="Finish" class="btn btn-sm btn-primary">
                </div>
              </div>
            </div>
          </div>
          <!-- STYLED IN FORMS.CSS --> 
        </div>
      </div>
    </div>
  </section>

  
 
  <jsp:include page="../masterFooter.jsp" />
  
  
