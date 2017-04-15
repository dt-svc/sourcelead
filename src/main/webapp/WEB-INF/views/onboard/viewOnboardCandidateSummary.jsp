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
          <h2>Srinivas Lankalapalli <span class="small">onboard details</span></h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='/usersList'/>">Users List</a> &raquo; <a href="<c:url value='/candidateOnBoardingList'/>">Onboard & Offboard List</a> &raquo; Candidate Onboarding Summary </div>
    </section>
    <div class="content-area content-new clearfix">
      <div class="applicant_accept">
        <div class="clearfix onboardBtns">
        </div>
        <div class="tabbable">
          <ul class="nav nav-tabs" id="tab_bar">
            <li class="active"> <a href="#details" data-toggle="tab"> <i class="fa fa-th-list paddingRight"></i> <span>Details</span> <br>
              <small>Employee details &amp; Postion details</small> </a> </li>
            <li><a href="#documents" data-toggle="tab"> <i class="fa fa-files-o paddingRight"></i> <span>Documents</span> <br>
              <small>Acceptance &amp; Policy Documents</small> </a> </li>
            <!-- Kept pending -->
              <li class="hidden"> <a href="#tasks" data-toggle="tab"> <i class="fa fa-check-square-o paddingRight"></i> <span>Taks</span> <br>
              <small>List of tasks</small> </a></li>
              <!-- Kept pending -->
          </ul>
          <div class="tab-content"> 
            
            <!-- Employee Details -->
            <div class="tab-pane active clearfix" id="details">
              <div class="col-md-12">
              <div class="form-horizontal">
                <p><strong>Employee Details</strong></p>
                <div class="form-group">
                  <label class="col-md-3 control-label">Title</label>
                  <div class="col-md-6">
                    <label> Mr </label>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-md-3 control-label">First Name <span class="text-danger paddingLeft">*</span></label>
                  <div class="col-md-6">
                    Muralikumar Joshi
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-md-3 control-label">Last Name <span class="text-danger paddingLeft">*</span></label>
                  <div class="col-md-6">
                    Avanagiri
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-md-3 control-label">Street Addess 1 <span class="text-danger paddingLeft">*</span></label>
                  <div class="col-md-6">
                    23-22-56/2, Roadno3, Muralinagar, Hyderabad- 500049.
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-md-3 control-label">Street Addess 2</label>
                  <div class="col-md-6">
                    23-22-56/2, Roadno3, Muralinagar, Hyderabad- 500049.
                  </div>
                </div>
                  
                                  <div class="form-group">
                  <label class="col-md-3 control-label">Country <span class="text-danger paddingLeft">*</span></label>
                  <div class="col-md-6">
                      India
                  </div>
                </div>

                <div class="form-group">
                  <label class="col-md-3 control-label">State</label>
                  <div class="col-md-6">
                      Telangana
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-md-3 control-label">City</label>
                  <div class="col-md-6">
                   Hyderabad    
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-md-3 control-label">Zipcode</label>
                  <div class="col-md-6">
                    500049
                  </div>
                </div>


                <div class="form-group">
                  <label class="col-md-3 control-label">Personal Email <span class="text-danger paddingLeft">*</span></label>
                  <div class="col-md-6">
                    muralikumarJoshi.23@gmail.com
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-md-3 control-label">Mobile number <span class="text-danger paddingLeft">*</span></label>
                  <div class="col-md-6">
                    +91 8899554750
                  </div>
                </div>
                <div class="clearfix"></div>
                <p><strong>Position Details</strong></p>
                <div class="form-group">
                  <label class="col-md-3 control-label">Department</label>
                  <div class="col-md-6">
                         Deaprtment-2
                  </div>
                </div>

                                  <div class="form-group">
                  <label class="col-md-3 control-label">Role</label>
                  <div class="col-md-6">
                          Role-1
                  </div>
                </div>

                  <div class="form-group">
                  <label class="col-md-3 control-label">Start date<span class="text-danger paddingLeft">*</span></label>
                  <div class="col-md-6">
                    18/04/2017
                  </div>
                </div>
                  <div class="form-group">
                  <label class="col-md-3 control-label">End date<span class="text-danger paddingLeft">*</span></label>
                  <div class="col-md-6">
                    30/05/2017
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-md-3 control-label">Rate / Salary <span class="text-danger paddingLeft">*</span></label>
                  <div class="col-md-6">
                    850000.00
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-md-3 control-label">Manager</label>
                  <div class="col-md-6">
                            Manager-1
                    </div>
                </div>
                <div class="form-group">
                  <label class="col-md-3 control-label">Team</label>
                  <div class="col-md-6">
                      Team
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-md-3 control-label">Employment Type</label>
                  <div class="col-md-6">
                      Employment Type-4
                  </div>
                </div>
                  
                  <div class="form-group">
                  <label class="col-md-3 control-label">Probation Notice</label>
                  <div class="col-md-6">
                      2 Months
                  </div>
                </div>
                  <div class="form-group">
                  <label class="col-md-3 control-label">Termination Notice</label>
                  <div class="col-md-6">
                      15 Days
                  </div>
                </div>
                  
                  
                  <div class="form-group">
                  <label class="col-md-3 control-label">Due Date</label>
                  <div class="col-md-6">
                   25th April
                  </div>
                </div>

                  <div class="form-group">
                  <label class="col-md-3 control-label">Speical Conditions</label>
                  <div class="col-md-6">
                      <p>
                      Lorem Ipsum is simply dummy text of the printing and typesetting industry.
                      Lorem Ipsum has been the industry's standard dummy text ever since the 1500s,
                      </p>
                  </div>
                </div>
                   <div class="form-group">
                       <label class="col-md-3 control-label">Onboard Template <span class="text-danger paddingLeft">*</span></label>
                  <div class="col-md-6">
                         Tempalte-1.doc, 
                         Tempalte-2.doc
                  </div>
                </div>
              </div>
                </div>
            </div>
            <!-- TAB 2 -->
            <div class="tab-pane clearfix" id="documents">
              <div class="col-md-12 clearfix mt">
  <div class="form-group">
    <label class="clearfix"> <strong>Employment Contract</strong> </label>
    <div class="row">
      <div class="col-md-2 text-center mb10">
        <div class="uploaded_file"> <i class="fa fa-file-text-o fa-4x"></i>
          <p class="small mt">karakumar_varam.doc</p>
          <i class="fa fa-check text-success permission"></i> </div>
      </div>
      <div class="col-md-2 text-center mb10">
        <div class="uploaded_file"> <i class="fa fa-file-text-o fa-4x"></i>
          <p class="small mt">karakumar_varam.doc</p>
          <i class="fa fa-check text-success permission"></i> </div>
      </div>
      <div class="col-md-2 text-center mb10">
        <div class="uploaded_file"> <i class="fa fa-file-text-o fa-4x"></i>
          <p class="small mt">karakumar_varam.doc</p>
          <i class="fa fa-check text-success permission"></i> </div>
      </div>
      <div class="col-md-2 text-center mb10">
        <div class="uploaded_file"> <i class="fa fa-file-text-o fa-4x"></i>
          <p class="small mt">karakumar_varam.doc</p>
          <i class="fa fa-check text-success permission"></i> </div>
      </div>
      <div class="col-md-2 text-center mb10">
        <div class="uploaded_file"> <i class="fa fa-file-text-o fa-4x"></i>
          <p class="small mt">karakumar_varam.doc</p>
          <i class="fa fa-close text-danger permission"></i> </div>
      </div>
      <div class="col-md-2 text-center mb10">
        <div class="uploaded_file"> <i class="fa fa-file-text-o fa-4x"></i>
          <p class="small mt">karakumar_varam.doc</p>
          <i class="fa fa-clock-o text-info permission"></i> </div>
      </div>
    </div>
  </div>
  <div class="form-group mt">
    <label class="clearfix"> <strong>Policies</strong> </label>
    <div class="row">
      <div class="col-md-2 text-center mb10">
        <div class="uploaded_file"> <i class="fa fa-file-text-o fa-4x"></i>
          <p class="small mt">karakumar_varam.doc</p>
          <i class="fa fa-check text-success permission"></i> </div>
      </div>
      <div class="col-md-2 text-center mb10">
        <div class="uploaded_file"> <i class="fa fa-file-text-o fa-4x"></i>
          <p class="small mt">karakumar_varam.doc</p>
          <i class="fa fa-check text-success permission"></i> </div>
      </div>
      <div class="col-md-2 text-center mb10">
        <div class="uploaded_file"> <i class="fa fa-file-text-o fa-4x"></i>
          <p class="small mt">karakumar_varam.doc</p>
          <i class="fa fa-check text-success permission"></i> </div>
      </div>
      <div class="col-md-2 text-center mb10">
        <div class="uploaded_file"> <i class="fa fa-file-text-o fa-4x"></i>
          <p class="small mt">karakumar_varam.doc</p>
          <i class="fa fa-check text-success permission"></i> </div>
      </div>
      <div class="col-md-2 text-center mb10">
        <div class="uploaded_file"> <i class="fa fa-file-text-o fa-4x"></i>
          <p class="small mt">karakumar_varam.doc</p>
          <i class="fa fa-close text-danger permission"></i> </div>
      </div>
      <div class="col-md-2 text-center mb10">
        <div class="uploaded_file"> <i class="fa fa-file-text-o fa-4x"></i>
          <p class="small mt">karakumar_varam.doc</p>
          <i class="fa fa-clock-o text-info permission"></i> </div>
      </div>
    </div>
  </div>
</div>
            </div>
            <!-- Keep as pending -->
              <div class="tab-pane hidden" id="tasks">
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
                                  <select class="chosen-select" multiple data-placeholder="Select Priority" id="priority">
                                    <option>High</option>
                                    <option>Low</option>
                                    <option>Mediam</option>
                                  </select>
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
                                <div class="col-md-6">
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
                                    <input type="button" class="btn btn-default btn-sm ml10" value="Cancel">
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
          <!-- Keep as pending -->
            </div>
          <!-- STYLED IN FORMS.CSS --> 
        </div>
      </div>
    </div>
  </section>
 
 <jsp:include page="../masterFooter.jsp" />