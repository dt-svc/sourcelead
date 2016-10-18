<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script src="resources/js/formvalidator.js"></script>
 
 
 
<script>
$(document).ready(function() {
	 var requirementId = '${requirementId}'; 
	 sideBar(requirementId); 
});	
</script>

<!-- Content Area -->
<section class="content-wraper">
  <section class="content-header clearfix">
    <div class="pull-left">
      <h2>Requirement Summary</h2>
    </div>
    <div class="pull-right brud-crum"><a href="<c:url value='/requirementList'/>">Requirement List  </a>&raquo; Requirement Details</div>
  </section>
  <div class="timesheet_out">
    
    
<div class="clients_side_wrap_inn no_pad">
        
        <div id="requirementSideDiv"> </div>
        <!-- <div class="profile_details requirement_details clearfix">
        <h4 id="id1">SL 201 - Business Analyst - Full Time<i></i></h4>
        
        <div class="hide-row">
          <div class="col-md-9">
            <div class="row">
              <div class="col-md-3">
                <div class="company-logo">
                  <div class="imageParent"> <img src="./images/dell-logo.png" alt="Company" title="Company Logo" class="img-responsive"> </div>
                  <span class="hidden"> TATA Consulting Private Limited, Hyderebad. </span> </div>
              </div>
              <div class="col-md-9">
                <div class="canDetails clearfix">
                  <div class="row">
                    <div class="col-md-6">
                      <ul class="list-unstyled can-perdt">
                        <li><i class="fa fa-black-tie"></i><span>Experience - 4 years</span></li>
                        <li><i class="fa fa-rupee"></i><span>200 Per Hour</span></li>
                        <li><i class="fa fa-map-marker"></i><span>Hyderabad, TS, India</span></li>
                      </ul>
                    </div>
                    <div class="col-md-6">
                      <ul class="list-unstyled can-perdt">
                        <li> <i class="fa fa-user"></i><span id="canName">SampathKumar Reddy Nakkapalli</span> <b class="dropdown"> <span class="caret dropdown-toggle caret-top" data-toggle="dropdown"></span>
                          <ul class="dropdown-menu dropdown-menu-right" id="canName-ch">
                            <li><a href="#">Muralimanohar Reddy</a></li>
                            <li><a href="#">Krishnamanohar Surampalli</a></li>
                            <li><a href="#">Prasadraj Mamidipayy</a></li>
                          </ul>
                          </b> </li>
                        <li><i class="fa fa-clock-o"></i><span>30/08/2005</span></li>
                        <li><i class="fa fa-flag"></i><span id="pri-val">Select Priority</span> <b class="dropdown"> <span class="caret dropdown-toggle caret-top" data-toggle="dropdown"></span>
                          <ul class="dropdown-menu dropdown-menu-right" id="pri-ch">
                            <li id="h"><a href="#">High</a></li>
                            <li id="m"><a href="#">Medium</a></li>
                            <li id="l"><a href="#">Low</a></li>
                          </ul>
                          </b> </li>
                      </ul>
                    </div>
                  </div>
                </div>
                <div class="pro-detials">
                  <div class="professinal-details">
                    <ul class="list-inline list-unstyled">
                      <li>BA</li>
                      <li>BRS</li>
                      <li>SRS</li>
                      <li>Agile</li>
                      <li>SDLC</li>
                    </ul>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="col-md-3">
            <select class="selectpicker pull-right">
              <option>Open</option>
              <option>Draft</option>
              <option>Filled Closed</option>
              <option>Unfilled</option>
              <option>On Hold</option>
              <option>Cancel</option>
            </select>
          </div>
          <div class="clearfix"></div>
          </div>
          
        </div> --> 
        <!-- <div class="proj_close"></div> -->
        
        <!-- <div class="project_side_wrap_inn_tabs" id="new-id">
          <ul class="project-tabs-menu">
            <li class="current"><a href="#tab-1">Details</a></li>
            <li><a href="#tab-2">Pipeline</a></li>
            <div class="clearfix"></div>
          </ul>
          <div class="project_tabs">
            <div id="tab-1" class="project_tabs_inn">
              <div class="require_wrap_details">
	Assigned Members
                <div class="clearfix row">
                    <div class="col-md-10 divskill divsAssigned">
                    <h2>Assigned Members</h2> 
                    <div class="heightScroll mt10 mb">
                        <table class="table table-responsive table-hover clearfix mb">
                        <thead>
                        <tr>
                        <th>Name</th>
                        <th>Role</th>
                        <th>Team</th>
                        <th>No. of Applicants</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                        <td><div class="tbldata-dotdot-me">Murali Krishna Manohar</div></td>
                        <td><div class="tbldata-dotdot-sm">Admin</div></td>
                        <td><div class="tbldata-dotdot-sm">Team</div></td>
                        <td><div class="tbldata-dotdot-sm">020</div></td>
                        </tr>
                        <tr>
                        <td><div class="tbldata-dotdot-me">Murali Krishna Manohar</div></td>
                        <td><div class="tbldata-dotdot-sm">Admin</div></td>
                        <td><div class="tbldata-dotdot-sm">Team</div></td>
                        <td><div class="tbldata-dotdot-sm">020</div></td>
                        </tr>
                        <tr>
                        <td><div class="tbldata-dotdot-me">Murali Krishna Manohar</div></td>
                        <td><div class="tbldata-dotdot-sm">Admin</div></td>
                        <td><div class="tbldata-dotdot-sm">Team</div></td>
                        <td><div class="tbldata-dotdot-sm">020</div></td>
                        </tr>
                        <tr>
                        <td><div class="tbldata-dotdot-me">Murali Krishna Manohar</div></td>
                        <td><div class="tbldata-dotdot-sm">Admin</div></td>
                        <td><div class="tbldata-dotdot-sm">Team</div></td>
                        <td><div class="tbldata-dotdot-sm">020</div></td>
                        </tr>
                        <tr>
                        <td><div class="tbldata-dotdot-me">Murali Krishna Manohar</div></td>
                        <td><div class="tbldata-dotdot-sm">Admin</div></td>
                        <td><div class="tbldata-dotdot-sm">Team</div></td>
                        <td><div class="tbldata-dotdot-sm">020</div></td>
                        </tr>
                        <tr>
                        <td><div class="tbldata-dotdot-me">Murali Krishna Manohar</div></td>
                        <td><div class="tbldata-dotdot-sm">Admin</div></td>
                        <td><div class="tbldata-dotdot-sm">Team</div></td>
                        <td><div class="tbldata-dotdot-sm">020</div></td>
                        </tr>
                        <tr>
                        <td><div class="tbldata-dotdot-me">Murali Krishna Manohar</div></td>
                        <td><div class="tbldata-dotdot-sm">Admin</div></td>
                        <td><div class="tbldata-dotdot-sm">Team</div></td>
                        <td><div class="tbldata-dotdot-sm">020</div></td>
                        </tr>
                        <tr>
                        <td><div class="tbldata-dotdot-me">Murali Krishna Manohar</div></td>
                        <td><div class="tbldata-dotdot-sm">Admin</div></td>
                        <td><div class="tbldata-dotdot-sm">Team</div></td>
                        <td><div class="tbldata-dotdot-sm">020</div></td>
                        </tr>
                        <tr>
                        <td><div class="tbldata-dotdot-me">Murali Krishna Manohar</div></td>
                        <td><div class="tbldata-dotdot-sm">Admin</div></td>
                        <td><div class="tbldata-dotdot-sm">Team</div></td>
                        <td><div class="tbldata-dotdot-sm">020</div></td>
                        </tr>
                        <tr>
                        <td><div class="tbldata-dotdot-me">Murali Krishna Manohar</div></td>
                        <td><div class="tbldata-dotdot-sm">Admin</div></td>
                        <td><div class="tbldata-dotdot-sm">Team</div></td>
                        <td><div class="tbldata-dotdot-sm">020</div></td>
                        </tr>
                        <tr>
                        <td><div class="tbldata-dotdot-me">Murali Krishna Manohar</div></td>
                        <td><div class="tbldata-dotdot-sm">Admin</div></td>
                        <td><div class="tbldata-dotdot-sm">Team</div></td>
                        <td><div class="tbldata-dotdot-sm">020</div></td>
                        </tr>
                        <tr>
                        <td><div class="tbldata-dotdot-me">Murali Krishna Manohar</div></td>
                        <td><div class="tbldata-dotdot-sm">Admin</div></td>
                        <td><div class="tbldata-dotdot-sm">Team</div></td>
                        <td><div class="tbldata-dotdot-sm">020</div></td>
                        </tr>
                        </tbody>
                        </table>
                    </div>
                    </div>
                </div>
 Assigned Members

                <h2>Descriptions:</h2>
                <textarea placeholder="Enter description">Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages.</textarea>
                <h2>Other details:</h2>
                <div class="require_one">
                  <div class="require_wrap_details_cta"> <b>No. of positions:</b> <span>10</span>
                    <div class="clearfix"></div>
                  </div>
                  <div class="require_wrap_details_cta"> <b>Education:</b> <span>MBA</span>
                    <div class="clearfix"></div>
                  </div>
                  <div class="require_wrap_details_cta"> <b>Attachment:</b> <span>file name<i class="glyphicon glyphicon-save"></i></span>
                    <div class="clearfix"></div>
                  </div>
                  <div class="clearfix"></div>
                </div>
                <h2>Client details:</h2>
                <div class="require_one">
                  <div class="require_wrap_details_cta"> <b>Client contact:</b> <span class="pull-left"> Sudhakar Reddy </span>
                    <div class="dropdown pull-left"> <i class="fa fa-edit dropdown-toggle" data-toggle="dropdown"></i>
                      <ul class="dropdown-menu dropdown-menu-right">
                        <li><a href="#">Client Contact-1</a></li>
                        <li><a href="#">Client Contact-2</a></li>
                        <li><a href="#">Client Contact-3</a></li>
                      </ul>
                    </div>
                    <div class="clearfix"></div>
                  </div>
                  <div class="require_wrap_details_cta"> <b>Email:</b> <span>sudhakar@tcs.com</span>
                    <div class="clearfix"></div>
                  </div>
                  <div class="require_wrap_details_cta"> <b>Phone No:</b> <span>+91 9000 000 000</span>
                    <div class="clearfix"></div>
                  </div>
                  <div class="require_wrap_details_cta"> <b>Bill rate/salary:</b> <span><i class="fa fa-inr"></i>300</span>
                    <div class="clearfix"></div>
                  </div>
                  <div class="require_wrap_details_cta"> <b>Submission Form:</b> <span>Genral</span>
                    <div class="dropdown pull-left"> <i class="fa fa-edit dropdown-toggle" data-toggle="dropdown"></i>
                      <ul class="dropdown-menu dropdown-menu-right">
                        <li><a href="#">General Form-1</a></li>
                        <li><a href="#">General Form-2</a></li>
                        <li><a href="#">General Form-3</a></li>
                      </ul>
                    </div>
                    <div class="clearfix"></div>
                  </div>
                  <div class="clearfix"></div>
                </div>
                <h2>Internal details:</h2>
                <div class="require_one">
                  <div class="require_wrap_details_cta"> <b>Manager:</b> <span>Sujith S</span>
                    <div class="dropdown pull-left dropup"> <i class="fa fa-edit dropdown-toggle" data-toggle="dropdown"></i>
                      <ul class="drop-up dropdown-menu dropdown-menu-right">
                        <li><a href="#">Manager-1</a></li>
                        <li><a href="#">Manager-2</a></li>
                        <li><a href="#">Manager-3</a></li>
                      </ul>
                    </div>
                    <div class="clearfix"></div>
                  </div>
                  <div class="require_wrap_details_cta"> <b>Application form:</b> <span>Business Analyst <i class="fa fa-edit"></i></span>
                    <div class="clearfix"></div>
                  </div>
                  <div class="clearfix"></div>
                </div>
              </div>
            </div>
            <div id="tab-2" class="project_tabs_inn">
              <div class="graf_icon"><i class="fa fa-area-chart"></i>Report</div>
              <div class="pipeline_graf"><img src="images/bargraph.jpg" alt=""/></div>
              <div class="timesheet_head candidateHead recruitmentHead">
                <ul>
                  <li class="li-vendor">
                    <input type="text" class="vendorfild" placeholder="Search">
                    <i class="fa fa-search"></i></li>
                  <li class="hidden"><i class="glyphicon glyphicon-export"></i><span>Export</span><i class="glyphicon glyphicon-play" id="export-pop"></i>
                    <div class="export_pop">
                      <ul>
                        <li>PDF<i class="fa fa-file-pdf-o"></i></li>
                        <li>Excel<i class="fa fa-file-excel-o"></i></li>
                        <li>Word<i class="fa fa-file-word-o"></i></li>
                      </ul>
                    </div>
                  </li>
                  <li><a href="javascript:void(0)" title=""><i class="fa fa-envelope"></i></a></li>
                  <li><a href="javascript:void(0)" title=""><i class="fa fa-mail-forward"></i></a></li>
                  <li><a href="javascript:void(0)" title=""><i class="fa fa-upload"></i></a></li>
                  <li><a href="javascript:void(0)" title=""><i class="fa fa-download"></i></a></li>
                  <li><a href="javascript:void(0)" title="" class="add-applicant"><i class="fa fa-plus"></i>&nbsp;Add Applicant</a></li>
                  <li><a href="javascript:void(0)" title="" class="add-interview"><i class="fa fa-plus"></i>&nbsp;Add Interview</a></li>
                  <li> <a href="javascript:void(0)" title="" class="add-offer"><i class="fa fa-plus"></i>&nbsp;Add Offer</a> </li>
                  <div class="clearfix"></div>
                  Popup Div -- Add Offer
                  <div class="collapseDiv_add collapseDiv_styles col-xs-4 addOffer-Req"> <i class="fa fa-close close-div"></i>
                    <div class="form-horizontal clearfix">
                      <div class="row">
                        <div class="col-md-12 mb10">
                          <label>Candiate</label>
                          <select class="form-control selectpicker">
                            <option>Rameshkumar Korrapati</option>
                            <option>Prasanth Korrapati</option>
                            <option>Muralimohan Korrapati</option>
                            <option>Sarathkumar Korrapati</option>
                          </select>
                        </div>
                        <div class="col-md-12 mb10">
                          <label>Date of Joining</label>
                          <div class="positionRelative">
                            <input type="text" value="" class="form-control input-sm">
                            <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span> </div>
                        </div>
                        <div class="col-md-12 mb10">
                          <label>Rate / Salary</label>
                          <div class="row div-padding">
                            <div class="col-md-4">
                              <select class="form-control selectpicker">
                                <option>Rupee</option>
                                <option>Dollar</option>
                                <option>Rupee</option>
                              </select>
                            </div>
                            <div class="col-md-4">
                              <input type="text" value="" class="form-control">
                            </div>
                            <div class="col-md-4">
                              <select class="form-control selectpicker">
                                <option>Yearly</option>
                                <option>Monthly</option>
                              </select>
                            </div>
                          </div>
                        </div>
                        <div class="col-md-12 mb10">
                          <label>Position Type</label>
                          <select class="form-control selectpicker">
                            <option>Full Type</option>
                            <option>C2C</option>
                            <option>Parttime</option>
                          </select>
                        </div>
                        <div class="col-md-12 mb10">
                          <label>Attachment</label>
                          <div class="input-group">
                            <label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
                              <input type="file" style="display: none;" multiple id="uploadBtn"  class="upload">
                              </span> </label>
                            <input type="text" class="form-control" readonly id="uploadFile" placeholder="Files are not selected...">
                          </div>
                        </div>
                        <div class="col-md-12 mb10 hidden">
                          <button type="button" class="btn btn-danger"><i class="fa fa-pulse"></i>Add</button>
                        </div>
                      </div>
                      <div class="clearfix mt10">
                        <div class="pull-right">
                          <button type="submit" class="btn btn-sm btn-primary mr">Submit</button>
                          <button type="submit" class="btn btn-sm btn-default">Cancel</button>
                        </div>
                      </div>
                    </div>
                  </div>
                  Popup Div -- Add Offer Ends 
                  Popup Div -- Add Interview
                  <div class="collapseDiv_add col-xs-4 collapseDiv_styles addInterview-Re"> <i class="fa fa-close close-div"></i>
                    <div class="form-horizontal clearfix">
                      <div class="row">
                        <div class="col-md-12 mb10">
                          <label>Candiate</label>
                          <select class="form-control selectpicker">
                            <option>Rameshkumar Korrapati</option>
                            <option>Prasanth Korrapati</option>
                            <option>Muralimohan Korrapati</option>
                            <option>Sarathkumar Korrapati</option>
                          </select>
                        </div>
                        <div class="col-md-12 mb10">
                          <div class="positionRelative date" id="datetimepicker3">
                            <input type="text" value="" class="form-control input-sm" placeholder="Date of Joining">
                            <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span> </div>
                        </div>
                        <div class="col-md-12 mb10">
                          <div class="row div-padding">
                            <div class="col-md-4">
                              <label>Start Time</label>
                              <div class="positionRelative">
                                <input type="text" value="" class="form-control input-sm">
                                <span class="input-group-addon input-groupAddon"><i class="fa fa-clock-o"></i></span> </div>
                            </div>
                            <div class="col-md-4">
                              <label>End Time</label>
                              <div class="positionRelative">
                                <input type="text" value="" class="form-control input-sm">
                                <span class="input-group-addon input-groupAddon"><i class="fa fa-clock-o"></i></span> </div>
                            </div>
                            <div class="col-md-4">
                              <label>Inteview Type</label>
                              <select class="form-control selectpicker">
                                <option>F2F</option>
                                <option>Telephonic</option>
                                <option>Skype Call</option>
                              </select>
                            </div>
                          </div>
                        </div>
                        <div class="col-md-12 mb10">
                          <input type="text" value="" class="form-control input-sm" placeholder="Location">
                        </div>
                        <div class="col-md-12 mb10">
                          <label>Interviewer</label>
                          <select class="form-control selectpicker">
                            <option>Hyderabad</option>
                            <option>Visakhapatnam</option>
                            <option>Pune</option>
                          </select>
                        </div>
                        <div class="col-md-12 mb10">
                          <label>
                          Set Reminder &nbsp;<i class="fa fa-gear reminder" title="Click here"></i>
                          <div class="reminder-div col-sm-7">
                          
                            <div class="row mb10">
                              <div class="col-md-12">
                                <input type="text" class="form-control input-sm" value="" placeholder="Remind before">
                              </div>
                            </div>
                            <div class="row mb10">
                              <div class="col-md-12">
                                <input type="text" class="form-control input-sm" value="" placeholder="Frequency">
                              </div>
                            </div>
                            <div class="row mb10">
                              <div class="col-md-12">
                                <input type="text" class="form-control input-sm" value="" placeholder="Notify to">
                              </div>
                            </div>
                            <div class="row">
                              <div class="pull-right mr">
                                <button type="button" class="btn btn-sm btn-success">Set</button>
                              </div>
                            </div>
                          </div>
                          </label>
                        </div>
                        <div class="col-md-12 mb10 hidden">
                          <button type="button" class="btn btn-danger"><i class="fa fa-pulse"></i>Add</button>
                        </div>
                      </div>
                      <div class="clearfix mt10">
                        <div class="pull-right">
                          <button type="submit" class="btn btn-sm btn-primary mr">Submit</button>
                          <button type="submit" class="btn btn-sm btn-default">Cancel</button>
                        </div>
                      </div>
                    </div>
                  </div>
                  Popup Div -- Add Interview
                  Popup Div -- add Applicant
                  <div class="collapseDiv_add col-xs-4 collapseDiv_styles addApplicant-Re"> <i class="fa fa-close close-div"></i>
                    <div class="form-horizontal clearfix">
                      <div class="row">
                        <div class="col-md-12 mb10">
                          <label>Select Candiate</label>
                          <select class="form-control selectpicker">
                            <option>Rameshkumar Korrapati</option>
                            <option>Prasanth Korrapati</option>
                            <option>Muralimohan Korrapati</option>
                            <option>Sarathkumar Korrapati</option>
                          </select>
                        </div>
                        <div class="clearfix text-center text-info mb10">
                      -------------------------------- OR --------------------------------
                         </div>
                        <div class="col-md-12 mb10">
						 <input type="text" value="" class="form-control input-sm" placeholder="First Name *">
                        </div>
                        <div class="col-md-12 mb10">
						 <input type="text" value="" class="form-control input-sm" placeholder="Last Name">
                        </div>
                        <div class="col-md-12 mb10">
						 <input type="text" value="" class="form-control input-sm" placeholder="Email*">
                        </div>
                        <div class="col-md-12 mb10">
						 <input type="text" value="" class="form-control input-sm" placeholder="Phone">
                        </div>
                        <div class="col-md-12 mb10">
						 <input type="text" value="" class="form-control input-sm" placeholder="Experience">
                        </div>
                        <div class="col-md-12">
                        <div class="panel-border">
                        <div class="col-md-12">
                          <div class="input-group">
                            <label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
                              <input type="file" style="display: none;" multiple id="uploadBtn"  class="upload">
                              </span> </label>
                            <input type="text" class="form-control" readonly id="uploadFile" placeholder="Upload Resume *">
                          </div>
                        </div>
                    
                  <div class="col-md-12 mt10 mb10 clearfix text-center text-info">
                      -------------------------------- OR --------------------------------
                  </div>
                  <div class="col-md-12">
                          <select class="form-control selectpicker">
                            <option>Select</option>
                            <option>Prasanth Korrapati</option>
                            <option>Muralimohan Korrapati</option>
                            <option>Sarathkumar Korrapati</option>
                          </select>
                        </div>
                        </div></div>
                        <div class="col-md-12 mb10">
                          <label>Expected Rate / Salary <span class="text-danger">*</span></label>
                          <div class="row div-padding">
                            <div class="col-md-4">
                              <select class="form-control selectpicker">
                                <option>Rupee</option>
                                <option>Dollar</option>
                                <option>Rupee</option>
                              </select>
                            </div>
                            <div class="col-md-4">
                              <input type="text" value="" class="form-control input-sm">
                            </div>
                            <div class="col-md-4">
                              <select class="form-control selectpicker">
                                <option>Yearly</option>
                                <option>Monthly</option>
                              </select>
                            </div>
                          </div>
                        </div>
                        
                        
                      </div>
                      <div class="clearfix mt10">
                        <div class="pull-right">
                          <button type="submit" class="btn btn-sm btn-primary mr">Submit</button>
                          <button type="submit" class="btn btn-sm btn-default">Cancel</button>
                        </div>
                      </div>
                    </div>
                  </div>
                  Popup Div -- add Applicant
                  
                </ul>
                <div class="clearfix"></div>
              </div>
              <div class="require_wrap_table">
                <table>
                  <tbody>
                  <thead>
                    <tr>
                      <th><input type="checkbox" id="checkbox1"/></th>
                      <th>Name</th>
                      <th>Email</th>
                      <th>Ph. No</th>
                      <th>Resume</th>
                      <th>Applied on</th>
                      <th>Rate</th>
                      <th>Source</th>
                      <th>Exp</th>
                      <th>Status</th>
                    </tr>
                  </thead>
                  <tr>
                    <td><input type="checkbox" id="checkbox1"/></td>
                    <td><div class="proj_show"><span>Vannam vannam vannam vannam</span><i class="fa fa-binoculars"></i></div></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div><span>9000 000 000</span></div></td>
                    <td><div><a href="javascript:void(0)"><i class="glyphicon glyphicon-list-alt"></i></a></div></td>
                    <td><div><span>08/08/2016</span></div></td>
                    <td><div><span>12345678</span></div></td>
                    <td><div><span>Careers</span></div></td>
                    <td><div>3.4</div></td>
                    <td><div><span>
                        <select>
                          <option>Interview</option>
                          <option>Submit</option>
                          <option>Joined</option>
                          <option>Screening</option>
                          <option>Offer</option>
                        </select>
                        </span></div></td>
                  </tr>
                  <tr>
                    <td><input type="checkbox" id="checkbox1"/></td>
                    <td><div class="proj_show"><span>Vannam vannam vannam vannam</span><i class="fa fa-binoculars"></i></div></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div>9000 000 000</div></td>
                    <td><div><a href="javascript:void(0)"><i class="glyphicon glyphicon-list-alt"></i></a></div></td>
                    <td><div><span>08/08/2016</span></div></td>
                    <td><div><span>12345678</span></div></td>
                    <td><div>Careers</div></td>
                    <td><div>3.4</div></td>
                    <td><div><span><b class="proj_process">Interview</b><i class=" fa fa-edit"></i></span></div></td>
                  </tr>
                  <tr>
                    <td><input type="checkbox" id="checkbox1"/></td>
                    <td><div class="proj_show"><span>Vannam vannam vannam vannam</span><i class="fa fa-binoculars"></i></div></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div>9000 000 000</div></td>
                    <td><div><a href="javascript:void(0)"><i class="glyphicon glyphicon-list-alt"></i></a></div></td>
                    <td><div><span>08/08/2016</span></div></td>
                    <td><div><span>12345678</span></div></td>
                    <td><div>Careers</div></td>
                    <td><div>3.4</div></td>
                    <td><div><span><b class="proj_process">Interview</b><i class=" fa fa-edit"></i></span></div></td>
                  </tr>
                  <tr>
                    <td><input type="checkbox" id="checkbox1"/></td>
                    <td><div class="proj_show"><span>Vannam vannam vannam vannam</span><i class="fa fa-binoculars"></i></div></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div>9000 000 000</div></td>
                    <td><div><a href="javascript:void(0)"><i class="glyphicon glyphicon-list-alt"></i></a></div></td>
                    <td><div><span>08/08/2016</span></div></td>
                    <td><div><span>12345678</span></div></td>
                    <td><div>Careers</div></td>
                    <td><div>3.4</div></td>
                    <td><div><span><b class="proj_process">Interview</b><i class=" fa fa-edit"></i></span></div></td>
                  </tr>
                  <tr>
                    <td><input type="checkbox" id="checkbox1"/></td>
                    <td><div class="proj_show"><span>Vannam vannam vannam vannam</span><i class="fa fa-binoculars"></i></div></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div>9000 000 000</div></td>
                    <td><div><a href="javascript:void(0)"><i class="glyphicon glyphicon-list-alt"></i></a></div></td>
                    <td><div><span>08/08/2016</span></div></td>
                    <td><div><span>12345678</span></div></td>
                    <td><div>Careers</div></td>
                    <td><div>3.4</div></td>
                    <td><div><span><b class="proj_process">Interview</b><i class=" fa fa-edit"></i></span></div></td>
                  </tr>
                  <tr>
                    <td><input type="checkbox" id="checkbox1"/></td>
                    <td><div class="proj_show"><span>Vannam vannam vannam vannam</span><i class="fa fa-binoculars"></i></div></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div>9000 000 000</div></td>
                    <td><div><a href="javascript:void(0)"><i class="glyphicon glyphicon-list-alt"></i></a></div></td>
                    <td><div><span>08/08/2016</span></div></td>
                    <td><div><span>12345678</span></div></td>
                    <td><div>Careers</div></td>
                    <td><div>3.4</div></td>
                    <td><div><span><b class="proj_process">Interview</b><i class=" fa fa-edit"></i></span></div></td>
                  </tr>
                  <tr>
                    <td><input type="checkbox" id="checkbox1"/></td>
                    <td><div class="proj_show"><span>Vannam vannam vannam vannam</span><i class="fa fa-binoculars"></i></div></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div>9000 000 000</div></td>
                    <td><div><a href="javascript:void(0)"><i class="glyphicon glyphicon-list-alt"></i></a></div></td>
                    <td><div><span>08/08/2016</span></div></td>
                    <td><div><span>12345678</span></div></td>
                    <td><div>Careers</div></td>
                    <td><div>3.4</div></td>
                    <td><div><span><b>Offer</b><i class=" fa fa-edit"></i></span></div></td>
                  </tr>
                  <tr>
                    <td><input type="checkbox" id="checkbox1"/></td>
                    <td><div class="proj_show"><span>Vannam vannam vannam vannam</span><i class="fa fa-binoculars"></i></div></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div>9000 000 000</div></td>
                    <td><div><a href="javascript:void(0)"><i class="glyphicon glyphicon-list-alt"></i></a></div></td>
                    <td><div><span>08/08/2016</span></div></td>
                    <td><div><span>12345678</span></div></td>
                    <td><div>Careers</div></td>
                    <td><div>3.4</div></td>
                    <td><div><span><b>Offer</b><i class=" fa fa-edit"></i></span></div></td>
                  </tr>
                  <tr>
                    <td><input type="checkbox" id="checkbox1"/></td>
                    <td><div class="proj_show"><span>Vannam vannam vannam vannam</span><i class="fa fa-binoculars"></i></div></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div>9000 000 000</div></td>
                    <td><div><a href="javascript:void(0)"><i class="glyphicon glyphicon-list-alt"></i></a></div></td>
                    <td><div><span>08/08/2016</span></div></td>
                    <td><div><span>12345678</span></div></td>
                    <td><div>Careers</div></td>
                    <td><div>3.4</div></td>
                    <td><div><span><b class="proj_closed">Submit</b><i class=" fa fa-edit"></i></span></div></td>
                  </tr>
                  <tr>
                    <td><input type="checkbox" id="checkbox1"/></td>
                   <td><div class="proj_show"><span>Vannam vannam vannam vannam</span><i class="fa fa-binoculars"></i></div></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div>9000 000 000</div></td>
                    <td><div><a href="javascript:void(0)"><i class="glyphicon glyphicon-list-alt"></i></a></div></td>
                    <td><div><span>08/08/2016</span></div></td>
                    <td><div><span>12345678</span></div></td>
                    <td><div>Careers</div></td>
                    <td><div>3.4</div></td>
                    <td><div><span><b class="proj_closed">Submit</b><i class=" fa fa-edit"></i></span></div></td>
                  </tr>
                  </tbody>
                  
                </table>
              </div>
              <nav>
                <ul id="paging" class="pagination">
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
          
        </div> -->
              
        
      </div>    
    
    
    <div class="side_mask">
      <div class="proj_close"></div>
    </div>
    <div class="clients_side_wrap">
      <div class="clients_side_wrap_inn">
        <div class="project_side_wrap_inn_tabs"> 
          
<!--    Requirment details    -->
<div class="req_hide">
<div class="profile_details clearfix">
          <div class="col-md-6">
            <div class="media">
              <div class="media-left image-parent">
                <div class="imageParent"> <img src="./images/24.jpg" alt="candidate" title="Candidate"> </div>
              </div>
              <div class="media-body candidate-details">
                <h4 class="media-heading">Jaden Smith</h4>
                <ul class="list-unstyled">
                  <li><strong>Business Analyst - Full Time</strong></li>
                  <li>Experience - 4 years</li>
                </ul>
              </div>
            </div>
            <div class="other-detials">
              <div class="professinal-details">
                <ul class="list-inline list-unstyled">
                  <li>BA</li>
                  <li>BRS</li>
                  <li>SRS</li>
                  <li>Agile</li>
                  <li>SDLC</li>
                </ul>
              </div>
              <div class="socialnetwork-details">
                <ul class="list-inline list-unstyled">
                  <li class="bg-facebook"><a href="javascript:void(0)"> <i class="fa fa-facebook-square"></i></a></li>
                  <li class="bg-twitter"><a href="javascript:void(0)"> <i class="fa fa-twitter-square"></i></a></li>
                  <li class="bg-google "><a href="javascript:void(0)"> <i class="fa fa-google-plus-square"></i></a></li>
                  <li class="bg-linked"><a href="javascript:void(0)"> <i class="fa fa-linkedin-square"></i></a></li>
                  <li class="bg-web"><a href="javascript:void(0)"> <i class="fa fa-globe"></i></a></li>
                </ul>
              </div>
            </div>
          </div>
          <div class="col-md-6">
            <ul class="list-unstyled can-perdt">
              <li><i class="fa fa-envelope-o"></i><a href="#" title="click here">jadensmith@hotmail.com</a></li>
              <li><i class="fa fa-phone"></i>+91 9900223355</li>
              <li><i class="fa fa-location-arrow"></i>Hyderabad, TS, India</li>
              <li><i class="fa fa-rupee"></i>200/Per Hour - Current Rate / Salary</li>
              <li><i class="fa fa-rupee"></i>300/Per Hour - Expected Rate / Salary</li>
            </ul>
          </div>
        </div>

<div class="project_side_wrap_inn_tabs">
          <ul class="new-tabs">
            <li class="current"><a href="#tabb-1">Profile</a></li>
            <li><a href="#tabb-2">Resume</a></li>
            <li><a href="#tabb-3">Employer's</a></li>
            <li><a href="#tabb-4">Education</a></li>
            <li><a href="#tabb-5">Certifications</a></li>
            <li><a href="#tabb-6">Activities</a></li>
            <div class="clearfix"></div>
          </ul>
          <div class="project_tabs">
            <div id="tabb-1" class="new-tabs_inn">
              <div class="new-tabs_inn_head addUser-org"> <a href="javascript:void(0)" class="mr"><i class="glyphicon glyphicon-pencil"></i>Edit</a> <a href="javascript:void(0)"><i class="glyphicon glyphicon-trash"></i>Remove</a> 
                <!-- Popup Div: Edit Candidate -->
                <div class="edit-details col-md-8 edit-candaite"> <i class="fa fa-close close-div"></i>
                  <div class="form-horizontal clearfix">
                    <div class="clearfix mb10">
                      <div class="col-md-6">
                        <input type="text" class="form-control input-sm" value="" placeholder="First Name">
                      </div>
                      <div class="col-md-6">
                        <input type="text" class="form-control input-sm" value="" placeholder="Last Name">
                      </div>
                    </div>
                    <div class="clearfix mb10">
                      <div class="col-md-6">
                        <input type="text" class="form-control input-sm" value="" placeholder="Email">
                      </div>
                      <div class="col-md-6">
                        <input type="text" class="form-control input-sm" value="" placeholder="Mobile">
                      </div>
                    </div>
                    <div class="clearfix mb10">
                      <div class="col-md-6 custom-fields">
                        <select class="form-control select-arrow select-bg">
                          <option>India</option>
                          <option>United States</option>
                          <option>UK</option>
                        </select>
                      </div>
                      <div class="col-md-6 custom-fields select-arrow">
                        <select class="form-control input-sm">
                          <option>India</option>
                          <option>United States</option>
                          <option>UK</option>
                        </select>
                      </div>
                    </div>
                    <div class="clearfix mb10">
                      <div class="col-md-6">
                        <input type="text" class="form-control input-sm" value="" placeholder="City">
                      </div>
                      <div class="col-md-6">
                        <input type="text" class="form-control input-sm" value="" placeholder="Skills">
                      </div>
                    </div>
                    <div class="clearfix mb10">
                      <div class="col-md-12">
                        <textarea class="form-control input-sm" placeholder="Summary" rows="2" cols="0">
                              
                              </textarea>
                      </div>
                    </div>
                    <div class="clearfix mr">
                      <div class="pull-right">
                        <button type="submit" class="btn btn-sm btn-primary mr">Save</button>
                        <button type="submit" class="btn btn-sm btn-danger">Cancel</button>
                      </div>
                    </div>
                  </div>
                </div>
                <!-- Popup Div --> 
              </div>
              <div class="require_wrap_details">
                <h1>Summary:</h1>
                <textarea placeholder="Enter description">Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages.</textarea>
                
                <!-- Select Skills -->
                <h2>Skills:</h2>
                <div class="row select-skills">
                  <div class="divskill col-md-8"> 
                    <!-- Add skills div -->
                    <div class="addSkills"> <a href="#" class="btn-addskills" title="Add Skills"> <i class="fa fa-code"></i>Add Skills </a>
                      <div class="col-md-7 add-skills"> <i class="fa fa-close close-div"></i>
                        <div class="form-horizontal clearfix">
                          <div class="clearfix div-padding">
                            <div class="col-md-10">
                              <input type="text" placeholder="Skill..." value="" class="form-control input-sm">
                            </div>
                            <div class="col-md-2">
                              <button class="btn btn-sm btn-primary pull-right" type="submit">Add</button>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                    <!-- Add skills div ends -->
                    <table class="table table-responsive table-hover clearfix">
                      <thead>
                        <tr>
                          <th>Skills</th>
                          <th>Level</th>
                          <th>Experience</th>
                          <th>Action</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr>
                          <td><p class="form-control-static">Java</p></td>
                          <td><select class="tbldata-dotdot-me width100percnt">
                              <option selected disabled>Select</option>
                              <option>Beginner</option>
                              <option>Familiar</option>
                              <option>proficient</option>
                              <option>Expert</option>
                              <option>Master</option>
                            </select></td>
                          <td><select class="tbldata-dotdot-me width100percnt">
                              <option selected disabled>Select</option>
                              <option>0</option>
                              <option>1</option>
                              <option>2</option>
                              <option>3</option>
                              <option>4</option>
                              <option>5</option>
                              <option>6</option>
                              <option>7</option>
                              <option>8</option>
                              <option>9</option>
                              <option>10</option>
                              <option>11</option>
                              <option>12</option>
                              <option>13</option>
                              <option>14</option>
                              <option>15</option>
                              <option>15</option>
                              <option>16</option>
                              <option>17</option>
                              <option>18</option>
                              <option>19</option>
                              <option>20+</option>
                            </select></td>
                          <td><div class="text-center"> <i class="fa fa-trash"></i> </div></td>
                        </tr>
                        <tr>
                          <td><p class="form-control-static">Javascript</p></td>
                          <td><select class="tbldata-dotdot-me width100percnt">
                              <option selected disabled>Select</option>
                              <option>Beginner</option>
                              <option>Familiar</option>
                              <option>proficient</option>
                              <option>Expert</option>
                              <option>Master</option>
                            </select></td>
                          <td><select class="tbldata-dotdot-me width100percnt">
                              <option selected disabled>Select</option>
                              <option>0</option>
                              <option>1</option>
                              <option>2</option>
                              <option>3</option>
                              <option>4</option>
                              <option>5</option>
                              <option>6</option>
                              <option>7</option>
                              <option>8</option>
                              <option>9</option>
                              <option>10</option>
                              <option>11</option>
                              <option>12</option>
                              <option>13</option>
                              <option>14</option>
                              <option>15</option>
                              <option>15</option>
                              <option>16</option>
                              <option>17</option>
                              <option>18</option>
                              <option>19</option>
                              <option>20+</option>
                            </select></td>
                          <td><div class="text-center"> <i class="fa fa-trash"></i> </div></td>
                        </tr>
                        <tr>
                          <td><p class="form-control-static">Java</p></td>
                          <td><select class="tbldata-dotdot-me width100percnt">
                              <option selected disabled>Select</option>
                              <option>Beginner</option>
                              <option>Familiar</option>
                              <option>proficient</option>
                              <option>Expert</option>
                              <option>Master</option>
                            </select></td>
                          <td><select class="tbldata-dotdot-me width100percnt">
                              <option selected disabled>Select</option>
                              <option>0</option>
                              <option>1</option>
                              <option>2</option>
                              <option>3</option>
                              <option>4</option>
                              <option>5</option>
                              <option>6</option>
                              <option>7</option>
                              <option>8</option>
                              <option>9</option>
                              <option>10</option>
                              <option>11</option>
                              <option>12</option>
                              <option>13</option>
                              <option>14</option>
                              <option>15</option>
                              <option>15</option>
                              <option>16</option>
                              <option>17</option>
                              <option>18</option>
                              <option>19</option>
                              <option>20+</option>
                            </select></td>
                          <td><div class="text-center"> <i class="fa fa-trash"></i> </div></td>
                        </tr>
                        <tr>
                          <td><p class="form-control-static">Java</p></td>
                          <td><select class="tbldata-dotdot-me width100percnt">
                              <option selected disabled>Select</option>
                              <option>Beginner</option>
                              <option>Familiar</option>
                              <option>proficient</option>
                              <option>Expert</option>
                              <option>Master</option>
                            </select></td>
                          <td><select class="tbldata-dotdot-me width100percnt">
                              <option selected disabled>Select</option>
                              <option>0</option>
                              <option>1</option>
                              <option>2</option>
                              <option>3</option>
                              <option>4</option>
                              <option>5</option>
                              <option>6</option>
                              <option>7</option>
                              <option>8</option>
                              <option>9</option>
                              <option>10</option>
                              <option>11</option>
                              <option>12</option>
                              <option>13</option>
                              <option>14</option>
                              <option>15</option>
                              <option>15</option>
                              <option>16</option>
                              <option>17</option>
                              <option>18</option>
                              <option>19</option>
                              <option>20+</option>
                            </select></td>
                          <td><div class="text-center"> <i class="fa fa-trash"></i> </div></td>
                        </tr>
                        <tr>
                          <td><p class="form-control-static">Java</p></td>
                          <td><select class="tbldata-dotdot-me width100percnt">
                              <option selected disabled>Select</option>
                              <option>Beginner</option>
                              <option>Familiar</option>
                              <option>proficient</option>
                              <option>Expert</option>
                              <option>Master</option>
                            </select></td>
                          <td><select class="tbldata-dotdot-me width100percnt">
                              <option selected disabled>Select</option>
                              <option>0</option>
                              <option>1</option>
                              <option>2</option>
                              <option>3</option>
                              <option>4</option>
                              <option>5</option>
                              <option>6</option>
                              <option>7</option>
                              <option>8</option>
                              <option>9</option>
                              <option>10</option>
                              <option>11</option>
                              <option>12</option>
                              <option>13</option>
                              <option>14</option>
                              <option>15</option>
                              <option>15</option>
                              <option>16</option>
                              <option>17</option>
                              <option>18</option>
                              <option>19</option>
                              <option>20+</option>
                            </select></td>
                          <td><div class="text-center"> <i class="fa fa-trash"></i> </div></td>
                        </tr>
                    </tbody>
                    </table>
                    <a href="javascript:void(0)">more <i class="fa fa-angle-right"></i></a>
                  </div>
                </div>
                <!-- Select Skills --> 
              </div>
            </div>
            <div id="tabb-2" class="new-tabs_inn">
              <div class="project_tabs_inn_head"> <a href="#" class="addUser-org"><i class="glyphicon glyphicon-plus"></i>Add Resume</a> 
                <!-- Popup Div -->
                <div class="edit-details col-md-4"> <i class="fa fa-close close-div"></i>
                  <div class="form-horizontal clearfix">
                    <div class="clearfix mb10">
                      <div class="col-md-12">
                        <input type="text" class="form-control input-sm" value="" placeholder="Title">
                      </div>
                    </div>
                    <div class="clearfix mb10">
                      <div class="col-md-12">
                        <textarea rows="2" placeholder="Description" class="form-control"></textarea>
                      </div>
                    </div>
                    <div class="clearfix mb10">
                      <div class="col-md-12">
                        <div class="input-group">
                          <label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
                            <input type="file" style="display: none;" multiple id="uploadBtn" class="upload">
                            </span> </label>
                          <input type="text" class="form-control" readonly id="uploadFile" placeholder="Files are not selected...">
                        </div>

                      </div>
                    </div>
                    <div class="clearfix mr">
                      <button type="submit" class="btn btn-sm btn-primary pull-right">Add</button>
                    </div>
                  </div>
                </div>
                <!-- Popup Div --> 
              </div>
              <div class="project_tabs_inn_table vendor_wrap_inn_table">
                <table>
                  <tbody>
                    <tr>
                      <th>Title</th>
                      <th>Attachment</th>
                      <th>Description</th>
                      <th>Created By</th>
                      <th>Action</th>
                    </tr>
                    <tr>
                      <td><div><span>javaresume</span></div></td>
                      <td><div><span>javaresume.doc</span></div></td>
                      <td><div><span>javaresume description here</span></div></td>
                      <td><div><span>Sujith S</span></div></td>
                      <td><div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div></td>
                    </tr>
                    <tr>
                      <td><div><span>javaresume</span></div></td>
                      <td><div><span>javaresume.doc</span></div></td>
                      <td><div><span>javaresume description here</span></div></td>
                      <td><div><span>Sujith S</span></div></td>
                      <td><div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div></td>
                    </tr>
                    <tr>
                      <td><div><span>javaresume</span></div></td>
                      <td><div><span>javaresume.doc</span></div></td>
                      <td><div><span>javaresume description here</span></div></td>
                      <td><div><span>Sujith S</span></div></td>
                      <td><div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div></td>
                    </tr>
                    <tr>
                      <td><div><span>javaresume</span></div></td>
                      <td><div><span>javaresume.doc</span></div></td>
                      <td><div><span>javaresume description here</span></div></td>
                      <td><div><span>Sujith S</span></div></td>
                      <td><div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div></td>
                    </tr>
                    <tr>
                      <td><div><span>javaresume</span></div></td>
                      <td><div><span>javaresume.doc</span></div></td>
                      <td><div><span>javaresume description here</span></div></td>
                      <td><div><span>Sujith S</span></div></td>
                      <td><div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div></td>
                    </tr>
                    <tr>
                      <td><div><span>javaresume</span></div></td>
                      <td><div><span>javaresume.doc</span></div></td>
                      <td><div><span>javaresume description here</span></div></td>
                      <td><div><span>Sujith S</span></div></td>
                      <td><div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div></td>
                    </tr>
                    <tr>
                      <td><div><span>javaresume</span></div></td>
                      <td><div><span>javaresume.doc</span></div></td>
                      <td><div><span>javaresume description here</span></div></td>
                      <td><div><span>Sujith S</span></div></td>
                      <td><div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div></td>
                    </tr>
                    <tr>
                      <td><div><span>javaresume</span></div></td>
                      <td><div><span>javaresume.doc</span></div></td>
                      <td><div><span>javaresume description here</span></div></td>
                      <td><div><span>Sujith S</span></div></td>
                      <td><div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div></td>
                    </tr>
                    <tr>
                      <td><div><span>javaresume</span></div></td>
                      <td><div><span>javaresume.doc</span></div></td>
                      <td><div><span>javaresume description here</span></div></td>
                      <td><div><span>Sujith S</span></div></td>
                      <td><div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div></td>
                    </tr>
                    <tr>
                      <td><div><span>javaresume</span></div></td>
                      <td><div><span>javaresume.doc</span></div></td>
                      <td><div><span>javaresume description here</span></div></td>
                      <td><div><span>Sujith S</span></div></td>
                      <td><div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div></td>
                    </tr>
                  </tbody>
                </table>
              </div>
              <nav>
                <ul id="pagingg" class="pagination">
                  <li><a href="#">Previous</a></li>
                  <li><a href="#">2</a></li>
                  <li><a href="#">3</a></li>
                  <li class="disabled"><a href="#">6</a></li>
                  <li data-page="last"><a href="#">Next</a></li>
                </ul>
                <div class="clearfix"></div>
              </nav>
            </div>
            <div id="tabb-3" class="new-tabs_inn">
              <div class="project_tabs_inn_head"> <a href="#" class="addUser-org"> <i class="glyphicon glyphicon-plus"></i>Add Employer's </a> 
                <!-- Popup Div -->
                <div class="edit-details col-md-4"> <i class="fa fa-close close-div"></i>
                  <div class="form-horizontal clearfix">
                    <div class="clearfix mb10">
                      <div class="col-md-12">
                        <input type="text" class="form-control input-sm" value="" placeholder="Employer *">
                      </div>
                    </div>
                    <div class="clearfix mb10">
                      <div class="col-md-12 checkbox">
                        <label>
                          <input type="checkbox" name="Position" id="endDate">
                          Is Current Position</label>
                      </div>
                    </div>
                    <div class="clearfix mb10">
                      <div class="col-md-12">
                        <input type="text" class="form-control input-sm" value="" placeholder="Designation">
                      </div>
                    </div>
                    <div class="clearfix mb10">
                      <div class="col-md-12">
                        <input type="text" class="form-control input-sm" value="" placeholder="Address">
                      </div>
                    </div>
                    <div class="clearfix mb10">
                      <p class="ml">Start Date</p>
                      <div class="col-md-6">
                        <select class="chosen-select">
                          <option value="Select Month">Select Month</option>
                          <option value="January">January</option>
                          <option value="February">February</option>
                          <option value="March">March</option>
                          <option value="April">April</option>
                          <option value="April">May</option>
                          <option value="April">June</option>
                          <option value="April">Jully</option>
                          <option value="April">August</option>
                          <option value="April">September</option>
                          <option value="April">October</option>
                          <option value="April">November</option>
                          <option value="April">December</option>
                        </select>
                      </div>
                      <div class="col-md-6">
                        <input type="text" max class="form-control" value="" placeholder="Enter Year">
                      </div>
                    </div>
                    <div class="clearfix mb10 endDate">
                      <p class="ml">End Date</p>
                      <div class="col-md-6">
                        <select class="chosen-select">
                          <option value="Select Month">Select Month</option>
                          <option value="January">January</option>
                          <option value="February">February</option>
                          <option value="March">March</option>
                          <option value="April">April</option>
                          <option value="April">May</option>
                          <option value="April">June</option>
                          <option value="April">Jully</option>
                          <option value="April">August</option>
                          <option value="April">September</option>
                          <option value="April">October</option>
                          <option value="April">November</option>
                          <option value="April">December</option>
                        </select>
                      </div>
                      <div class="col-md-6">
                        <input type="text" max class="form-control" value="" placeholder="Enter Year">
                      </div>
                    </div>
                    <div class="clearfix mb10 hidden">
                      <div class="col-md-12">
                        <label>End Date</label>
                        <select class="chosen-select">
                          <option value="Select Month">Select Month</option>
                          <option value="January">January</option>
                          <option value="February">February</option>
                          <option value="March">March</option>
                          <option value="April">April</option>
                          <option value="April">May</option>
                          <option value="April">June</option>
                          <option value="April">Jully</option>
                          <option value="April">August</option>
                          <option value="April">September</option>
                          <option value="April">October</option>
                          <option value="April">November</option>
                          <option value="April">December</option>
                        </select>
                        <input type="text" class="form-control input-sm mt10" value="" placeholder="Enter Year">
                        <span class="input-group-addon input-groupAddon hidden"><i class="fa fa-calendar"></i></span> </div>
                    </div>
                    <div class="clearfix mr">
                      <button type="submit" class="btn btn-sm btn-primary pull-right">Add</button>
                    </div>
                  </div>
                </div>
                <!-- Popup Div --> 
              </div>
              <div class="project_tabs_inn_table vendor_wrap_inn_table">
                <table>
                  <tbody>
                    <tr>
                      <th>Employer's</th>
                      <th>Designation</th>
                      <th>Address</th>
                      <th>Start date</th>
                      <th>End date</th>
                      <th>Action</th>
                    </tr>
                    <tr>
                      <td><div><span>TCS Pvt Ltd</span></div></td>
                      <td><div><span>Web developer</span></div></td>
                      <td><div><span>Hyderabad Hyderabad</span></div></td>
                      <td><div><span>30/08/2016</span></div></td>
                      <td><div><span>30/09/2016</span></div></td>
                      <td><div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div></td>
                    </tr>
                    <tr>
                      <td><div><span>TCS Pvt Ltd</span></div></td>
                      <td><div><span>Web developer</span></div></td>
                      <td><div><span>Hyderabad Hyderabad</span></div></td>
                      <td><div><span>30/08/2016</span></div></td>
                      <td><div><span>30/09/2016</span></div></td>
                      <td><div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div></td>
                    </tr>
                    <tr>
                      <td><div><span>TCS Pvt Ltd</span></div></td>
                      <td><div><span>Web developer</span></div></td>
                      <td><div><span>Hyderabad Hyderabad</span></div></td>
                      <td><div><span>30/08/2016</span></div></td>
                      <td><div><span>30/09/2016</span></div></td>
                      <td><div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div></td>
                    </tr>
                    <tr>
                      <td><div><span>TCS Pvt Ltd</span></div></td>
                      <td><div><span>Web developer</span></div></td>
                      <td><div><span>Hyderabad Hyderabad</span></div></td>
                      <td><div><span>30/08/2016</span></div></td>
                      <td><div><span>30/09/2016</span></div></td>
                      <td><div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div></td>
                    </tr>
                    <tr>
                      <td><div><span>TCS Pvt Ltd</span></div></td>
                      <td><div><span>Web developer</span></div></td>
                      <td><div><span>Hyderabad Hyderabad</span></div></td>
                      <td><div><span>30/08/2016</span></div></td>
                      <td><div><span>30/09/2016</span></div></td>
                      <td><div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div></td>
                    </tr>
                    <tr>
                      <td><div><span>TCS Pvt Ltd</span></div></td>
                      <td><div><span>Web developer</span></div></td>
                      <td><div><span>Hyderabad Hyderabad</span></div></td>
                      <td><div><span>30/08/2016</span></div></td>
                      <td><div><span>30/09/2016</span></div></td>
                      <td><div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div></td>
                    </tr>
                    <tr>
                      <td><div><span>TCS Pvt Ltd</span></div></td>
                      <td><div><span>Web developer</span></div></td>
                      <td><div><span>Hyderabad Hyderabad</span></div></td>
                      <td><div><span>30/08/2016</span></div></td>
                      <td><div><span>30/09/2016</span></div></td>
                      <td><div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div></td>
                    </tr>
                    <tr>
                      <td><div><span>TCS Pvt Ltd</span></div></td>
                      <td><div><span>Web developer</span></div></td>
                      <td><div><span>Hyderabad Hyderabad</span></div></td>
                      <td><div><span>30/08/2016</span></div></td>
                      <td><div><span>30/09/2016</span></div></td>
                      <td><div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div></td>
                    </tr>
                    <tr>
                      <td><div><span>TCS Pvt Ltd</span></div></td>
                      <td><div><span>Web developer</span></div></td>
                      <td><div><span>Hyderabad Hyderabad</span></div></td>
                      <td><div><span>30/08/2016</span></div></td>
                      <td><div><span>30/09/2016</span></div></td>
                      <td><div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div></td>
                    </tr>
                    <tr>
                      <td><div><span>TCS Pvt Ltd</span></div></td>
                      <td><div><span>Web developer</span></div></td>
                      <td><div><span>Hyderabad Hyderabad</span></div></td>
                      <td><div><span>30/08/2016</span></div></td>
                      <td><div><span>30/09/2016</span></div></td>
                      <td><div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div></td>
                    </tr>
                  </tbody>
                </table>
              </div>
              <nav>
                <ul id="pagingdg" class="pagination">
                  <li><a href="#">Previous</a></li>
                  <li><a href="#">2</a></li>
                  <li><a href="#">3</a></li>
                  <li class="disabled"><a href="#">6</a></li>
                  <li data-page="last"><a href="#">Next</a></li>
                </ul>
                <div class="clearfix"></div>
              </nav>
            </div>
            <div id="tabb-4" class="new-tabs_inn">
              <div class="project_tabs_inn_head addUser-org"> <a href="javascript:void(0)"><i class="glyphicon glyphicon-plus"></i>Add Education</a> 
                <!-- Popup Div -->
                <div class="edit-details col-md-4"> <i class="fa fa-close close-div"></i>
                  <div class="form-horizontal clearfix">
                    <div class="clearfix mb10">
                      <div class="col-md-12">
                        <input type="text" class="form-control input-sm" value="" placeholder="Education *" required>
                      </div>
                    </div>
                    <div class="clearfix mb10">
                      <div class="col-md-12">
                        <input type="text" class="form-control input-sm" value="" placeholder="Specialization">
                      </div>
                    </div>
                    <div class="clearfix mb10">
                      <div class="col-md-6 positionRelative">
                        <input type="text" class="form-control input-sm" value="" placeholder="Start Date" id="edu-startDate">
                        <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span> </div>
                      <div class="col-md-6 positionRelative">
                        <input type="text" class="form-control input-sm" value="" placeholder="End Date" id="edu-endDate">
                        <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span> </div>
                    </div>
                    <div class="clearfix mb10">
                      <div class="col-md-8">
                        <input type="text" class="form-control input-sm" value="" placeholder="Grade">
                      </div>
                      <div class="col-md-4">
                        <select>
                          <option>Grade</option>
                          <option>%</option>
                          <option>CGPA</option>
                        </select>
                      </div>
                    </div>
                    <div class="clearfix mb10">
                      <div class="col-md-12">
                        <input type="text" class="form-control input-sm" value="" placeholder="Institute">
                      </div>
                    </div>
                    <div class="clearfix mb10">
                      <div class="col-md-12">
                        <input type="text" class="form-control input-sm" value="" placeholder="Address">
                      </div>
                    </div>
                    <div class="clearfix mr">
                      <button type="submit" class="btn btn-sm btn-primary pull-right">Add</button>
                    </div>
                  </div>
                </div>
                <!-- Popup Div --> 
              </div>
              <div class="project_tabs_inn_table vendor_wrap_inn_table">
                <table>
                  <tbody>
                    <tr>
                      <th>Education</th>
                      <th>Specilization</th>
                      <th>Start date</th>
                      <th>End date</th>
                      <th>Grade %</th>
                      <th>Institute/University</th>
                      <th>Address</th>
                      <th>Action</th>
                    </tr>
                    <tr>
                      <td><div><span>MBA MBA MBA MBA MBA</span></div></td>
                      <td><div><span>Finance</span></div></td>
                      <td><div><span>30/08/2016</span></div></td>
                      <td><div><span>30/09/2016</span></div></td>
                      <td><div><span>65%</span></div></td>
                      <td><div><span>JNTU JNTU JNTU JNTU</span></div></td>
                      <td><div><span>Hyderabad Hyderabad</span></div></td>
                      <td><div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div></td>
                    </tr>
                    <tr>
                      <td><div><span>MBA MBA MBA MBA MBA</span></div></td>
                      <td><div><span>Finance</span></div></td>
                      <td><div><span>30/08/2016</span></div></td>
                      <td><div><span>30/09/2016</span></div></td>
                      <td><div><span>65%</span></div></td>
                      <td><div><span>JNTU JNTU JNTU JNTU</span></div></td>
                      <td><div><span>Hyderabad Hyderabad</span></div></td>
                      <td><div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div></td>
                    </tr>
                    <tr>
                      <td><div><span>MBA MBA MBA MBA MBA</span></div></td>
                      <td><div><span>Finance</span></div></td>
                      <td><div><span>30/08/2016</span></div></td>
                      <td><div><span>30/09/2016</span></div></td>
                      <td><div><span>65%</span></div></td>
                      <td><div><span>JNTU JNTU JNTU JNTU</span></div></td>
                      <td><div><span>Hyderabad Hyderabad</span></div></td>
                      <td><div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div></td>
                    </tr>
                    <tr>
                      <td><div><span>MBA MBA MBA MBA MBA</span></div></td>
                      <td><div><span>Finance</span></div></td>
                      <td><div><span>30/08/2016</span></div></td>
                      <td><div><span>30/09/2016</span></div></td>
                      <td><div><span>65%</span></div></td>
                      <td><div><span>JNTU JNTU JNTU JNTU</span></div></td>
                      <td><div><span>Hyderabad Hyderabad</span></div></td>
                      <td><div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div></td>
                    </tr>
                    <tr>
                      <td><div><span>MBA MBA MBA MBA MBA</span></div></td>
                      <td><div><span>Finance</span></div></td>
                      <td><div><span>30/08/2016</span></div></td>
                      <td><div><span>30/09/2016</span></div></td>
                      <td><div><span>65%</span></div></td>
                      <td><div><span>JNTU JNTU JNTU JNTU</span></div></td>
                      <td><div><span>Hyderabad Hyderabad</span></div></td>
                      <td><div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div></td>
                    </tr>
                    <tr>
                      <td><div><span>MBA MBA MBA MBA MBA</span></div></td>
                      <td><div><span>Finance</span></div></td>
                      <td><div><span>30/08/2016</span></div></td>
                      <td><div><span>30/09/2016</span></div></td>
                      <td><div><span>65%</span></div></td>
                      <td><div><span>JNTU JNTU JNTU JNTU</span></div></td>
                      <td><div><span>Hyderabad Hyderabad</span></div></td>
                      <td><div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div></td>
                    </tr>
                    <tr>
                      <td><div><span>MBA MBA MBA MBA MBA</span></div></td>
                      <td><div><span>Finance</span></div></td>
                      <td><div><span>30/08/2016</span></div></td>
                      <td><div><span>30/09/2016</span></div></td>
                      <td><div><span>65%</span></div></td>
                      <td><div><span>JNTU JNTU JNTU JNTU</span></div></td>
                      <td><div><span>Hyderabad Hyderabad</span></div></td>
                      <td><div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div></td>
                    </tr>
                    <tr>
                      <td><div><span>MBA MBA MBA MBA MBA</span></div></td>
                      <td><div><span>Finance</span></div></td>
                      <td><div><span>30/08/2016</span></div></td>
                      <td><div><span>30/09/2016</span></div></td>
                      <td><div><span>65%</span></div></td>
                      <td><div><span>JNTU JNTU JNTU JNTU</span></div></td>
                      <td><div><span>Hyderabad Hyderabad</span></div></td>
                      <td><div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div></td>
                    </tr>
                    <tr>
                      <td><div><span>MBA MBA MBA MBA MBA</span></div></td>
                      <td><div><span>Finance</span></div></td>
                      <td><div><span>30/08/2016</span></div></td>
                      <td><div><span>30/09/2016</span></div></td>
                      <td><div><span>65%</span></div></td>
                      <td><div><span>JNTU JNTU JNTU JNTU</span></div></td>
                      <td><div><span>Hyderabad Hyderabad</span></div></td>
                      <td><div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div></td>
                    </tr>
                    <tr>
                      <td><div><span>MBA MBA MBA MBA MBA</span></div></td>
                      <td><div><span>Finance</span></div></td>
                      <td><div><span>30/08/2016</span></div></td>
                      <td><div><span>30/09/2016</span></div></td>
                      <td><div><span>65%</span></div></td>
                      <td><div><span>JNTU JNTU JNTU JNTU</span></div></td>
                      <td><div><span>Hyderabad Hyderabad</span></div></td>
                      <td><div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div></td>
                    </tr>
                  </tbody>
                </table>
              </div>
              <nav>
                <ul id="pagingg" class="pagination">
                  <li><a href="#">Previous</a></li>
                  <li><a href="#">2</a></li>
                  <li><a href="#">3</a></li>
                  <li class="disabled"><a href="#">6</a></li>
                  <li data-page="last"><a href="#">Next</a></li>
                </ul>
                <div class="clearfix"></div>
              </nav>
            </div>
            <div id="tabb-5" class="new-tabs_inn">
              <div class="project_tabs_inn_head"> <a href="#" class="addUser-org"><i class="glyphicon glyphicon-plus"></i>Add Certification</a> 
                <!-- Popup Div -->
                <div class="edit-details col-md-4"> <i class="fa fa-close close-div"></i>
                  <div class="form-horizontal clearfix">
                    <div class="clearfix mb10">
                      <div class="col-md-12">
                        <input type="text" class="form-control input-sm" value="" placeholder="Name *">
                      </div>
                    </div>
                    <div class="clearfix mb10">
                      <div class="col-md-12">
                        <input type="text" class="form-control input-sm" value="" placeholder="Provider *">
                      </div>
                    </div>
                    <div class="clearfix mb10">
                      <div class="col-md-8">
                        <input type="text" class="form-control input-sm" value="" placeholder="Score">
                      </div>
                      <div class="col-md-4">
                        <select>
                          <option>Grade</option>
                          <option>%</option>
                          <option>CGPA</option>
                        </select>
                      </div>
                    </div>
                    <div class="clearfix mb10">
                      <div class="col-md-12">
                        <textarea class="form-control" rows="1" placeholder="Description"></textarea>
                      </div>
                    </div>
                    <div class="clearfix mb10">
                      <div class="col-md-6 positionRelative">
                        <input type="text" class="form-control input-sm" value="" placeholder="Valid From" id="validFrom">
                        <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span> </div>
                      <div class="col-md-6 positionRelative">
                        <input type="text" class="form-control input-sm" value="" placeholder="Validate To" id="validTo">
                        <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span> </div>
                    </div>
                    <div class="clearfix mr">
                      <button type="submit" class="btn btn-sm btn-primary pull-right">Add</button>
                    </div>
                  </div>
                </div>
                <!-- Popup Div --> 
              </div>
              <div class="project_tabs_inn_table vendor_wrap_inn_table">
                <table>
                  <tbody>
                    <tr>
                      <th>Name</th>
                      <th>Provider</th>
                      <th>Score</th>
                      <th>Description</th>
                      <th>Valid from</th>
                      <th>Valid to</th>
                      <th>Action</th>
                    </tr>
                    <tr>
                      <td><div><span>TCS Pvt Ltd</span></div></td>
                      <td><div><span>Web developer</span></div></td>
                      <td><div><span>300</span></div></td>
                      <td><div><span>Description Description</span></div></td>
                      <td><div><span>30/08/2016</span></div></td>
                      <td><div><span>30/09/2016</span></div></td>
                      <td><div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div></td>
                    </tr>
                    <tr>
                      <td><div><span>TCS Pvt Ltd</span></div></td>
                      <td><div><span>Web developer</span></div></td>
                      <td><div><span>300</span></div></td>
                      <td><div><span>Description Description</span></div></td>
                      <td><div><span>30/08/2016</span></div></td>
                      <td><div><span>30/09/2016</span></div></td>
                      <td><div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div></td>
                    </tr>
                    <tr>
                      <td><div><span>TCS Pvt Ltd</span></div></td>
                      <td><div><span>Web developer</span></div></td>
                      <td><div><span>300</span></div></td>
                      <td><div><span>Description Description</span></div></td>
                      <td><div><span>30/08/2016</span></div></td>
                      <td><div><span>30/09/2016</span></div></td>
                      <td><div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div></td>
                    </tr>
                    <tr>
                      <td><div><span>TCS Pvt Ltd</span></div></td>
                      <td><div><span>Web developer</span></div></td>
                      <td><div><span>300</span></div></td>
                      <td><div><span>Description Description</span></div></td>
                      <td><div><span>30/08/2016</span></div></td>
                      <td><div><span>30/09/2016</span></div></td>
                      <td><div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div></td>
                    </tr>
                    <tr>
                      <td><div><span>TCS Pvt Ltd</span></div></td>
                      <td><div><span>Web developer</span></div></td>
                      <td><div><span>300</span></div></td>
                      <td><div><span>Description Description</span></div></td>
                      <td><div><span>30/08/2016</span></div></td>
                      <td><div><span>30/09/2016</span></div></td>
                      <td><div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div></td>
                    </tr>
                    <tr>
                      <td><div><span>TCS Pvt Ltd</span></div></td>
                      <td><div><span>Web developer</span></div></td>
                      <td><div><span>300</span></div></td>
                      <td><div><span>Description Description</span></div></td>
                      <td><div><span>30/08/2016</span></div></td>
                      <td><div><span>30/09/2016</span></div></td>
                      <td><div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div></td>
                    </tr>
                    <tr>
                      <td><div><span>TCS Pvt Ltd</span></div></td>
                      <td><div><span>Web developer</span></div></td>
                      <td><div><span>300</span></div></td>
                      <td><div><span>Description Description</span></div></td>
                      <td><div><span>30/08/2016</span></div></td>
                      <td><div><span>30/09/2016</span></div></td>
                      <td><div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div></td>
                    </tr>
                    <tr>
                      <td><div><span>TCS Pvt Ltd</span></div></td>
                      <td><div><span>Web developer</span></div></td>
                      <td><div><span>300</span></div></td>
                      <td><div><span>Description Description</span></div></td>
                      <td><div><span>30/08/2016</span></div></td>
                      <td><div><span>30/09/2016</span></div></td>
                      <td><div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div></td>
                    </tr>
                    <tr>
                      <td><div><span>TCS Pvt Ltd</span></div></td>
                      <td><div><span>Web developer</span></div></td>
                      <td><div><span>300</span></div></td>
                      <td><div><span>Description Description</span></div></td>
                      <td><div><span>30/08/2016</span></div></td>
                      <td><div><span>30/09/2016</span></div></td>
                      <td><div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div></td>
                    </tr>
                    <tr>
                      <td><div><span>TCS Pvt Ltd</span></div></td>
                      <td><div><span>Web developer</span></div></td>
                      <td><div><span>300</span></div></td>
                      <td><div><span>Description Description</span></div></td>
                      <td><div><span>30/08/2016</span></div></td>
                      <td><div><span>30/09/2016</span></div></td>
                      <td><div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div></td>
                    </tr>
                  </tbody>
                </table>
              </div>
              <nav>
                <ul id="pagingg" class="pagination">
                  <li><a href="#">Previous</a></li>
                  <li><a href="#">2</a></li>
                  <li><a href="#">3</a></li>
                  <li class="disabled"><a href="#">6</a></li>
                  <li data-page="last"><a href="#">Next</a></li>
                </ul>
                <div class="clearfix"></div>
              </nav>
            </div>
            <div id="tabb-6" class="new-tabs_inn">
              <div class="project_tabs_inn_head">
                <input class="vendorfild" type="text">
                <i class="fa fa-search"></i> </div>
              <div class="project_tabs_inn_table vendor_wrap_inn_table">
                <table>
                  <tbody>
                    <tr>
                      <th>By</th>
                      <th>To</th>
                      <th>Date & Time</th>
                      <th>Action</th>
                    </tr>
                    <tr>
                      <td><div><span class="mid-span">Vannam (vannam@bis.com)</span></div></td>
                      <td><div><span class="mid-span">Mahesh (mahesh@amazon.in)</span></div></td>
                      <td><div><span>30/08/2016 7:30 AM</span></div></td>
                      <td><div><a href="javascript:void(0)">View</a></div></td>
                    </tr>
                    <tr>
                      <td><div><span class="mid-span">Vannam (vannam@bis.com)</span></div></td>
                      <td><div><span class="mid-span">Mahesh (mahesh@amazon.in)</span></div></td>
                      <td><div><span>30/08/2016 7:30 AM</span></div></td>
                      <td><div><a href="javascript:void(0)">View</a></div></td>
                    </tr>
                    <tr>
                      <td><div><span class="mid-span">Vannam (vannam@bis.com)</span></div></td>
                      <td><div><span class="mid-span">Mahesh (mahesh@amazon.in)</span></div></td>
                      <td><div><span>30/08/2016 7:30 AM</span></div></td>
                      <td><div><a href="javascript:void(0)">View</a></div></td>
                    </tr>
                    <tr>
                      <td><div><span class="mid-span">Vannam (vannam@bis.com)</span></div></td>
                      <td><div><span class="mid-span">Mahesh (mahesh@amazon.in)</span></div></td>
                      <td><div><span>30/08/2016 7:30 AM</span></div></td>
                      <td><div><a href="javascript:void(0)">View</a></div></td>
                    </tr>
                    <tr>
                      <td><div><span class="mid-span">Vannam (vannam@bis.com)</span></div></td>
                      <td><div><span class="mid-span">Mahesh (mahesh@amazon.in)</span></div></td>
                      <td><div><span>30/08/2016 7:30 AM</span></div></td>
                      <td><div><a href="javascript:void(0)">View</a></div></td>
                    </tr>
                    <tr>
                      <td><div><span class="mid-span">Vannam (vannam@bis.com)</span></div></td>
                      <td><div><span class="mid-span">Mahesh (mahesh@amazon.in)</span></div></td>
                      <td><div><span>30/08/2016 7:30 AM</span></div></td>
                      <td><div><a href="javascript:void(0)">View</a></div></td>
                    </tr>
                    <tr>
                      <td><div><span class="mid-span">Vannam (vannam@bis.com)</span></div></td>
                      <td><div><span class="mid-span">Mahesh (mahesh@amazon.in)</span></div></td>
                      <td><div><span>30/08/2016 7:30 AM</span></div></td>
                      <td><div><a href="javascript:void(0)">View</a></div></td>
                    </tr>
                    <tr>
                      <td><div><span class="mid-span">Vannam (vannam@bis.com)</span></div></td>
                      <td><div><span class="mid-span">Mahesh (mahesh@amazon.in)</span></div></td>
                      <td><div><span>30/08/2016 7:30 AM</span></div></td>
                      <td><div><a href="javascript:void(0)">View</a></div></td>
                    </tr>
                    <tr>
                      <td><div><span class="mid-span">Vannam (vannam@bis.com)</span></div></td>
                      <td><div><span class="mid-span">Mahesh (mahesh@amazon.in)</span></div></td>
                      <td><div><span>30/08/2016 7:30 AM</span></div></td>
                      <td><div><a href="javascript:void(0)">View</a></div></td>
                    </tr>
                    <tr>
                      <td><div><span class="mid-span">Vannam (vannam@bis.com)</span></div></td>
                      <td><div><span class="mid-span">Mahesh (mahesh@amazon.in)</span></div></td>
                      <td><div><span>30/08/2016 7:30 AM</span></div></td>
                      <td><div><a href="javascript:void(0)">View</a></div></td>
                    </tr>
                  </tbody>
                </table>
              </div>
              <nav>
                <ul id="pagingg" class="pagination">
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
</div>
<!--    Requirment details    -->            
          
          
        </div>
              
        
      </div>
    </div>
    <!-- Project --> 
  </div>
</section>
 
 

<!--   Error Message   -->
<div class="vendor_wrap_mask"></div>
<div class="vendor_error">
  <p>Delete conformation message....</p>
  <textarea placeholder="Enter message"></textarea>
  <a class="close_color" href="javascript:void(0)">Yes</a><a href="javascript:void(0)">No</a> </div>

  
<script type="text/javascript">
			var config = {
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			}
			for (var selector in config) {
			  $(selector).chosen(config[selector]);
			}
			$('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});

});
 		</script> 
<script>

// Error pop
$(document).ready(function() {
    $(".glyphicon-trash").click(function(){
		$(".vendor_error").slideToggle(200);
		$(".vendor_wrap_mask").fadeIn(200);
	});
	$(".vendor_error a").click(function(){
		$(".vendor_error").slideUp(200);
		$(".vendor_wrap_mask").fadeOut(200);
	});
});	

// Approved Popup
$(document).ready(function() {
    $("#appro-pop").click(function(){
		$(".approved_pop").slideToggle(200);
	});
    $("html").click(function() {
        $(".approved_pop").slideUp(200);
    });
    $(".approved_pop, #appro-pop").click(function(e) {
        e.stopPropagation();
    });	
});

// Export Popup
$(document).ready(function() {
    $("#export-pop").click(function(){
		$(".export_pop").slideToggle(200);
	});
    $("html").click(function() {
        $(".export_pop").slideUp(200);
    });
    $(".export_pop, #export-pop").click(function(e) {
        e.stopPropagation();
    });	
});	
	
// project side bar
$(document).ready(function() {
    $(".proj_show").click(function(){
		$(".clients_side_wrap").addClass("clients_side_wrap_active");
		$(".side_mask").fadeIn(200);
		$("body").css('overflow-y', 'hidden');
	});
    $(".proj_close, .side_mask").click(function(){
		$(".clients_side_wrap").removeClass("clients_side_wrap_active");
		$(".side_mask").fadeOut(200);
		$("body").css('overflow-y', 'auto');
	});	
});

// project side bar tabs
$(document).ready(function() {
	$("#tab-1").show()
    $(".project-tabs-menu a").click(function(event) {
        event.preventDefault();
        $(this).parent().addClass("current");
        $(this).parent().siblings().removeClass("current");
        var tab = $(this).attr("href");
        $(".project_tabs_inn").not(tab).css("display", "none");
        $(tab).fadeIn();
    });
});

// project side bar tabs
$(document).ready(function() {
	$("#tabb-1").show()
    $(".new-tabs a").click(function(event) {
        event.preventDefault();
        $(this).parent().addClass("current");
        $(this).parent().siblings().removeClass("current");
        var tab = $(this).attr("href");
        $(".new-tabs_inn").not(tab).css("display", "none");
        $(tab).fadeIn();
    });
});

// script for scroll Popups:: Add or Edit details
$(document).ready(function(event) {	
$(".addUser-org").click(function(){
	 $(".edit-details").toggleClass("edit-details-active");
	 $(".collapse-div").removeClass("edit-details-active");
 });
 $(".close-div").click(function(){
	 $(this).closest(".edit-details").toggleClass("edit-details-active");
	 });
 $(".addUser-org, .assign-org, .collapse-div-adduser, .collapse-div, .edit-details").click(function(e){
	 e.stopPropagation();
 });
 $('html').click(function(){
	$(".edit-details").removeClass("edit-details-active");
   });	
   $("#endDate").click(function(){
	   $(".endDate").toggle(200);
   });
   event.preventDefault();
});

$(".addSkills").click(function(){
	$(".add-skills").toggleClass('add-skills-active');
	});
$(".close-div").click(function(){
	$(".add-skills").removeClass('add-skills-active');
	});
$(".add-skills").click(function(e){
	 e.stopPropagation();
 });


// Bar chat pop
$(document).ready(function() {
    $(".graf_icon").click(function(){
		$(".pipeline_graf").slideToggle(200);
	});
$( "ul#canName-ch li").click(function(){
   var m = $(this).text();
  $("#canName").text(m);
});
});
</script> 
<script>
$( "#pri-ch li").click(function(){
   var m = $(this).text();
    var mid = $(this).attr('id');
    $("#pri-val").text(m);
     if('h' === $(this).attr("id")){
          $("#pri-val").removeClass("text-warning text-primary").addClass("text-danger");
     }else if('m' === $(this).attr("id")){
        $("#pri-val").removeClass("text-danger text-warning").addClass("text-primary");
     }else{
      $("#pri-val").removeClass("text-danger text-primary").addClass("text-warning");
     }   
});
</script> 
<script>
$(document).ready(function(){
	// Levels adding script
$(".add-offer").click(function(){
	 $(".addOffer-Req").toggleClass("show-div");
	 $(".addInterview-Re").removeClass("show-div");
	 $(".addApplicant-Re").removeClass("show-div");
 });
$(".add-interview").click(function(){
	 $(".addInterview-Re").toggleClass("show-div");
	 $(".addOffer-Req").removeClass("show-div");
	 $(".addApplicant-Re").removeClass("show-div");
 });
$(".add-applicant").click(function(){
	 $(".addApplicant-Re").toggleClass("show-div");
	 $(".addOffer-Req").removeClass("show-div");
	 $(".addInterview-Re").removeClass("show-div");
 });
$(".reminder").click(function(){
	 $(".reminder-div").toggleClass("reminder-div-active");
 });

 $(".close-div").click(function(){
	 $(this).parent().toggleClass("show-div");
	 });
 $(".div-close").click(function(){
	 $(this).parent().toggleClass("showDiv");
	 });

 $(".reminder").click(function(e){
	 e.stopPropagation();
 });
 $('html').click(function(){
    $("").removeClass("show-div");
   });	
   $("#endDate").click(function(){
	   $(".endDate").toggle(200);
   });
   });
</script>
<script type="text/javascript">
            $(function () {
                $('#datetimepicker5').datetimepicker({
					locale: 'ru'
                });
            });
</script>
</body>
</html>

<script type="text/javascript">
function openTopDiv(){
	 $(".hide-row").slideToggle(200);
	  $(".profile_details h4 i").toggleClass('icon-add');
}  
 /*  $("#id1").click(function(){ 
	  $(".hide-row").slideToggle(200);
	  $(".profile_details h4 i").toggleClass('icon-add');
});	  */ 
 </script>
<%-- 
     @author : sarath kumar.
     this script is for show side bar when you click on the candidate name.
     1.usage     : called at candidateList() function.
     2.parameter : csrf token , candidateId .
     3.ajax url implementation in CandidateListRestController.
--%>

<script>
function sideBar(requirementId){
	  var sourceleadUrl ='${sourceleadUrl}';
	  $.ajax({
          type : "POST",
           url: sourceleadUrl+"/getRequirementById?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId,        
           success :function(data) {  
                if(data != null && data != 'undefined' && data != ""){  
                	 var json = JSON.parse(data);
              		 if( data.indexOf('Sign In') >= 0){
			    		location.replace(sourceleadUrl);
			    	 }
              		 else if (data.indexOf('exception') >= 0) {
              			 location.replace(sourceleadUrl+"/errorPage");
              		 }
              		 else{
              			var result = '';
              			
              			result = result+'    <div class="profile_details requirement_details clearfix">';
				        result = result+'<a href="#" onClick="openTopDiv();"><h4 id="id1">SL 201 - Business Analyst - Full Time<i></i></h4></a>';
				        result = result+'<div class="hide-row">';
				        
				       /*  result = result+'    <div class="profile_details requirement_details clearfix">'; */
				        result = result+'<div class="col-md-9">';
				        result = result+'<div class="row">';
				        result = result+'<div class="col-md-3">';
				        result = result+'<div class="company-logo">';
				        result = result+'<div class="imageParent"> <img onerror="predefineOrgImage(this);" src="${sourceleadUrl}/displayOrgImage/'+json.orgLocationId.org.orgCode+'" alt="Company" title="Company Logo" class="img-responsive"> </div>';
				        result = result+'<span class="hidden"> TATA Consulting Private Limited, Hyderebad. </span> </div>';
				        result = result+'</div>';
				        result = result+'<div class="col-md-9">';
				        result = result+'<div class="canDetails clearfix">';
				        result = result+'<h4>SL 201 - Business Analyst - Full Time</h4>';
				        result = result+'<div class="row">';
				        result = result+'<div class="col-md-6">';
				        result = result+'<ul class="list-unstyled can-perdt">';
				        if(json.maxExp != null && json.maxExp != '' && json.maxExp !='undefined'&& json.minExp != null && json.minExp != '')
				        	{
				       	result = result+'<li><i class="fa fa-black-tie"></i><span>Experience : '+json.maxExp+' years</span></li>';
				        	}
					    else if(json.minExp != null && json.minExp != '' && json.minExp !='undefined'){
						result = result+' <li><i class="fa fa-black-tie"></i><span>Experience : '+json.minExp+' years</span></li>';
	
					        }
				        else{
					    result = result+'<li><i class="fa fa-black-tie"></i><span>Experience : --</span></li>';
				        }
				        
				        if(json.currencyCode == 'INR' && json.currencyCode  !='' && json.currencyCode  !='undefined' && json.rate != '' && json.rateType != '')
				    	   {
				        result = result+'                  <li><i class="fa fa-rupee"></i><span>'+json.rate+', '+json.rateType+'</span></li>';
				    	   }
				       else if(json.currencyCode  == 'USD' && json.currencyCode  !='' && json.currencyCode  !='undefined' && json.rate != '' && json.rateType != '' ){
					   result = result+'                  <li><i class="fa fa-dollar"></i><span>'+json.rate+', '+json.rateType+'</span></li>';
   
				       }
				       else if(json.currencyCode  == 'AUD' && json.currencyCode  !='' && json.currencyCode  !='undefined' && json.rate != '' && json.rateType != '' ){
					   result = result+'                  <li><i class="fa fa-dollar"></i><span>'+json.rate+', '+json.rateType+'</span></li>';
				       }
				       else if(json.currencyCode  == 'SGD' && json.currencyCode  !='' && json.currencyCode  !='undefined' && json.rate != '' && json.rateType != ''){
					   result = result+'                  <li><i class="fa fa-dollar"></i><span>'+json.rate+', '+json.rateType+'</span></li>';
   
				       }
				       else if(json.currencyCode  == 'EUR' && json.currencyCode  !='' && json.currencyCode  !='undefined' && json.rate != '' && json.rateType != ''){
					   result = result+'                  <li><i class="fa fa-eur"></i><span>'+json.rate+', '+json.rateType+'</span></li>';
   
				       }
				       else if(json.currencyCode  == 'GPB' && json.currencyCode  !='' && json.currencyCode  !='undefined' && json.rate != '' && json.rateType != ''){
					   result = result+'                  <li><i class="fa fa-gbp"></i><span>'+json.rate+', '+json.rateType+'</span></li>';
  
				       }
				       else{
					   result = result+'                  <li><i class="fa fa-money"></i><span> --- </span></li>';
  
				       }
				       
				        if(json.state !='' && json.state != 'undefined' && json.city !='' && json.city != 'undefined')
				        	{
				        result = result+'                  <li><i class="fa fa-map-marker"></i><span>'+json.country+', '+json.state+', '+json.city+'</span></li>';
				        	}
				        else if(json.state !='' && json.state != 'undefined')
				        	{
					    result = result+'                  <li><i class="fa fa-map-marker"></i><span>'+json.country+', '+json.state+'</span></li>';

				        	}
				        else{
					     result = result+' <li><i class="fa fa-map-marker"></i><span>'+json.country+'</span></li>';

				        }
				        result = result+'                </ul>';
				        result = result+'              </div>';
				        result = result+'              <div class="col-md-6">';
				        result = result+'                <ul class="list-unstyled can-perdt">';
				        if(json.manager != null && json.manager != '' && json.manager != 'undefined'){
				        result = result+'<li> <i class="fa fa-user"></i><span id="canName">'+json.manager+'</span></li>';
				        }
				        else{
				        	 result = result+'<li> <i class="fa fa-user"></i><span id="canName"> --- </span></li>';	
				        }
				        result = result+'<li><i class="fa fa-clock-o"></i><span>'+json.requirementCreatedDateToString+'</span></li>';
				        if(json.priority != null && json.priority != '' && json.priority != 'undefined'){
				        result = result+'<li><i class="fa fa-flag"></i><span>'+json.priority+' Priority</span>';
				        }
				        else{
					    result = result+'<li><i class="fa fa-flag"></i><span> --- </span>';
				        }
				         result = result+'</li>';
				        result = result+'</ul>';
				        result = result+'</div>';
				        result = result+'</div>';
				        result = result+'</div>';
				        result = result+'<div class="pro-detials">';
				        result = result+'<div class="professinal-details">';
				        result = result+'<ul class="list-inline list-unstyled">';
				        var skills = [];
			           if(skills != null && skills != '' && skills != 'undefined'){
			        	   skills = json.skiills.split(",");
					        result = result+'                <li>'+skills[0]+'</li>';
					        result = result+'                <li>'+skills[1]+'</li>';
					        result = result+'                <li>'+skills[2]+'</li>';
					        result = result+'                <li>'+skills[3]+'</li>';
					        result = result+'                <li>'+skills[4]+'</li>';
					        result = result+'              </ul>';
			           }
				        result = result+'            </div>';
				        result = result+'          </div>';
				        result = result+'        </div>';
				        result = result+'      </div>';
				        result = result+'    </div>';
				        result = result+'    <div class="col-md-3">';
				        result = result+'      <select class="selectpicker pull-right">';
				        result = result+'        <option>Open</option>';
				        result = result+'        <option>Draft</option>';
				        result = result+'        <option>Filled Closed</option>';
				        result = result+'        <option>Unfilled</option>';
				        result = result+'        <option>On Hold</option>';
				        result = result+'        <option>Cancel</option>';
				        result = result+'      </select>';
				        result = result+'    </div>';
				        result = result+'  </div>';
				        result = result+'</div>';
				        
					    result = result+'  <div class="proj_close"></div>';
					    result = result+'  <div class="project_side_wrap_inn_tabs">';
					    result = result+'    <ul class="project-tabs-menu">';
					    result = result+'      <li class="current"><a href="#tab-1" onclick="requirementDetails('+requirementId+');">Details</a></li>';
					    result = result+'      <li><a href="#tab-2" onclick="requirementPipeline('+requirementId+');">Pipeline</a></li>';
					    result = result+'      <div class="clearfix"></div>';
					    result = result+'    </ul>';
					    result = result+'    <div class="project_tabs">';
						    result = result+' <div id="tab-1" class="project_tabs_inn">';
						    result = result+'</div>';
						    result = result+' <div id="tab-2" class="project_tabs_inn">';
						    result = result+'</div>';
					    result = result+'</div>';
					    result = result+'</div>';
					    
			             $("#requirementSideDiv").empty();  
			 		     $("#requirementSideDiv").append(result);  
			 		     requirementDetails(requirementId);
				 		 
			 		     /*  $(".clients_side_wrap").addClass("clients_side_wrap_active");
						 $(".side_mask").fadeIn(200);
						 $("body").css('overflow-y', 'hidden');
						 requirementDetails(requirementId);
						 $(".proj_close, .side_mask").click(function(){
								$(".clients_side_wrap").removeClass("clients_side_wrap_active");
								$(".side_mask").fadeOut(200);
								$("body").css('overflow-y', 'auto');
						 });	 */
			 			 
			    	} 
                }else{
               	 $("#requirementSideDiv").removeAttr("style");
 	    	         var result ="";
		    	     result = result+"   <div class='row'>"; 
					 result = result+"	<div class='col-lg-12'>"; 
					 result = result+"		<div class='content-area clearfix'>"; 
					 result = result+"			<div class='blank-pages'>";
					 result = result+"			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
					 result = result+"			        <p>No Candidates Found</p>";
					 result = result+"			 </div>";
					 result = result+"		</div>"; 
					 result = result+"	</div>"; 
					 result = result+"  </div> "; 
					$("#requirementSideDiv").empty();
				    $("#requirementSideDiv").append(result); 
                }
         },
           
      }); 
				
}
</script>

<script>
	function predefineOrgImage(imageId){
		$(imageId).attr('src','resources/images/office-image.jpg');
	}
</script>


<%-- 
     @author : sarath kumar.
     this script is for edit Candidate.
     1.usage     :  used in candidateList.jsp.
     2.parameter : csrf token .
     3.ajax url implementation in CandidateListRestController.
--%>
<script>  
function requirementDetails(requirementId){ 
	  var sourceleadUrl = '${sourceleadUrl}';
	  $.ajax({
        type : "POST",
         url: sourceleadUrl+"/getRequirementById?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId,        
         success :function(data) {  
              if(data != null && data != 'undefined' && data != ""){  
              	var json = JSON.parse(data);
            		 if( data.indexOf('Sign In') >= 0){
			    		location.replace(sourceleadUrl);
			    	 }
            		 else if (data.indexOf('exception') >= 0) {
            			 location.replace(sourceleadUrl+"/errorPage");
            		 }
            		 else{    
							var result ="";
				            result = result +' <div class="require_wrap_details">';
				           
				            result = result +' <a href="<c:url value="/requirement?requirementId='+requirementId+'"/>"><button  class="btn btn-primary pull-right" type="button">Edit</button></a>';
				            result = result +' <a href="<c:url value="/requirementAccept?requirementId='+requirementId+'"/>"><button  class="btn btn-primary pull-right" type="button">Accept</button></a>';
				            result = result +' <a href="<c:url value="/requirementReject?requirementId='+requirementId+'"/>"><button  class="btn btn-primary pull-right" type="button">Reject</button></a>';
							
				            result = result +'     <div class="clearfix row">';
					        result = result +'     <div class="col-md-10 divskill divsAssigned">';
					        
					        result = result + '<div id="assignedMemberList"></div>';
					        
				            result = result +'   <h1>Description:</h1>';
				            if(json.description != null && json.description != '' && json.description != 'undefined'){
					            result = result +'   <textarea placeholder="Enter description">'+json.description+'</textarea>';
					            }
					            else{
					            result = result +'   <textarea placeholder="Enter description"> NO DATA</textarea>';
				    	            }
				            result = result +'   <h2>Other details:</h2>';
				            result = result +'   <div class="require_one">';
				            if(json.positions != null && json.positions != '' && json.positions != 'undefined'){
				            result = result +'     <div class="require_wrap_details_cta"> <b>No. of positions:</b> <span>'+json.positions+'</span>';
				            }
				            else{
				            result = result +'<div class="require_wrap_details_cta"> <b>No. of positions:</b> <span> -- </span>';	
				            }
				            result = result +'       <div class="clearfix"></div>';
				            result = result +'     </div>';
				            if(json.education != null && json.education != '' && json.education != 'undefined'){
					            result = result +'     <div class="require_wrap_details_cta"> <b>Education:</b> <span>'+json.education+'</span>';
					            }else{
					           	result = result +'<div class="require_wrap_details_cta"> <b>Education:</b> <span> -- </span>';
					            }
				            result = result +'       <div class="clearfix"></div>';
				            result = result +'     </div>';
				            if(json.fileName != null && json.fileName != '' && json.fileName != 'undefined'){
					            result = result +'     <div class="require_wrap_details_cta"> <b>Attachment:</b> <span>'+json.fileName+'<i class="glyphicon glyphicon-save"></i></span>';
					            }
					            else{
					            result = result +'<div class="require_wrap_details_cta"> <b>Attachment:</b> <span> -- </span>';	
					            }				            
				            result = result +'       <div class="clearfix"></div>';
				            result = result +'     </div>';
				            result = result +'     <div class="clearfix"></div>';
				            result = result +'   </div>';
				            result = result +'   <h2>Client details:</h2>';
				            result = result +'   <div class="require_one">';
				            
				            if(json.spocName != '' && json.spocName !=null && json.spocName != 'undefined'){
				            	
				            result = result +'     <div class="require_wrap_details_cta"> <b>Client Contact:</b> <span class="pull-left"> '+json.spocName+' </span>';
				            }
				            else{
					        result = result +'     <div class="require_wrap_details_cta"> <b>Client contact:</b> <span class="pull-left"> -- </span>';

				            }
				            result = result +'       <div class="clearfix"></div>';
				            result = result +'     </div>';
				            if(json.clientName != '' && json.clientName !=null && json.clientName != 'undefined'){
					        result = result +'     <div class="require_wrap_details_cta"> <b>Client Name:</b> <span class="pull-left"> '+json.clientName+' </span>';
				            }
				            else{
						        result = result +'     <div class="require_wrap_details_cta"> <b>Client Name:</b> <span class="pull-left"> ---  </span>';
				            }
				            result = result +'       <div class="clearfix"></div>';
				            result = result +'     </div>'; 
				            if(json.spocEmail != '' && json.spocEmail !=null && json.spocEmail != 'undefined'){
						    result = result +'     <div class="require_wrap_details_cta"> <b>Email:</b> <span>'+json.spocEmail+'</span>';
				            }
				            else{
							result = result +'     <div class="require_wrap_details_cta"> <b>Email:</b> <span> --- </span>';
	
				            }
				            result = result +'       <div class="clearfix"></div>';
				            result = result +'     </div>'; 
				            if(json.spocMobilePhone != '' && json.spocMobilePhone !=null && json.spocMobilePhone != 'undefined'){
					        result = result +'     <div class="require_wrap_details_cta"> <b>Phone No:</b> <span>'+json.spocMobilePhone+'</span>';
				            }
				            else{
						    result = result +'     <div class="require_wrap_details_cta"> <b>Phone No:</b> <span> --- </span>';

				            }
				            result = result +'       <div class="clearfix"></div>';
				            result = result +'     </div>';
				            if(json.clientCurrencyCode == 'INR' && json.clientCurrencyCode  !='' && json.clientCurrencyCode  !='undefined' &&  json.clientRate != '' && json.clientRate != 'undefined' && json.clientRate != null){
					        result = result +'     <div class="require_wrap_details_cta"> <b>Bill rate/salary:</b> <span><i class="fa fa-inr"></i> ' +json.clientRate+'</span>';
						    }
						    else if(json.clientCurrencyCode == 'EUR' && json.clientCurrencyCode  !='' && json.clientCurrencyCode  !='undefined' && json.clientRate != '' && json.clientRate != 'undefined'){

						    result = result +'     <div class="require_wrap_details_cta"> <b>Bill rate/salary:</b> <span><i class="fa fa-eur"></i> ' +json.clientRate+'</span>';
							}
						    else if(json.clientCurrencyCode == 'GPB' && json.clientCurrencyCode  !='' && json.clientCurrencyCode  !='undefined' && json.clientRate != '' && json.clientRate != 'undefined'){

							result = result +'     <div class="require_wrap_details_cta"> <b>Bill rate/salary:</b> <span><i class="fa fa-gbp"></i> ' +json.clientRate+'</span>';
							}
						    else if(json.clientCurrencyCode  == 'USD' && json.clientCurrencyCode  !='' && json.clientCurrencyCode  !='undefined' && json.clientRate != '' && json.clientRate != 'undefined'){
							result = result +'     <div class="require_wrap_details_cta" > <b>Bill rate/salary:</b> <span><i class="fa fa-dollar"></i> ' +json.clientRate+'</span>';
							}
						    else if(json.clientCurrencyCode  == 'AUD'  && json.clientCurrencyCode  !='' && json.clientCurrencyCode  !='undefined' && json.clientRate != '' && json.clientRate != 'undefined'){
							result = result +'     <div class="require_wrap_details_cta" > <b>Bill rate/salary:</b> <span><i class="fa fa-dollar"></i> ' +json.clientRate+'</span>';
	
						    }
						    else if(json.clientCurrencyCode  == 'SGD' && json.clientCurrencyCode  !='' && json.clientCurrencyCode  !='undefined' && json.clientRate != '' && json.clientRate != 'undefined'){
							result = result +'     <div class="require_wrap_details_cta" > <b>Bill rate/salary:</b> <span><i class="fa fa-dollar"></i> ' +json.clientRate+'</span>';
	
						    }
						    else{
							result = result +'     <div class="require_wrap_details_cta"> <b>Bill rate/salary:</b> <span> --- </span>';
						    }
				            result = result +'       <div class="clearfix"></div>';
				            result = result +'     </div>';
				            result = result +'     <div class="require_wrap_details_cta"> <b>Submission Form:</b> <span>Submission Form</span>';
				            result = result +'       <div class="clearfix"></div>';
				            result = result +'     </div>';
				            result = result +'     <div class="clearfix"></div>';
				            result = result +'   </div>';
				            result = result +'   <h2>Internal details:</h2>';
				            result = result +'   <div class="require_one">';
				            //result = result +'     <div class="require_wrap_details_cta"> <b>Manager:</b> <span>Manager</span>';
				            result = result +'       <div class="clearfix"></div>';
				            result = result +'     </div>';
				            result = result +'     <div class="require_wrap_details_cta"> <b>Application form:</b> <span>Business Analyst </span>';
				            result = result +'       <div class="clearfix"></div>';
				            result = result +'     </div>';
				            result = result +'     <div class="clearfix"></div>';
				            result = result +'   </div>';
				            result = result +' </div>';
							$("#tab-1").empty();
							$("#tab-2").empty(); 
							$("#tab-1").append(result); 
							
							assignedMemberList(requirementId);
							$("#tab-1").show();
							$(".project-tabs-menu a").click(function(event) {
							     event.preventDefault();
							     $(this).parent().addClass("current");
							     $(this).parent().siblings().removeClass("current");
							     var tab = $(this).attr("href");
							     $(".project_tabs_inn").not(tab).css("display", "none");
							     $(tab).fadeIn();
							 });
            		 }
              }else{
                     	 $("#requirementSideDiv").removeAttr("style");
       	    	         var result ="";
      		    	     result = result+"   <div class='row'>"; 
      					 result = result+"	<div class='col-lg-12'>"; 
      					 result = result+"		<div class='content-area clearfix'>"; 
      					 result = result+"			<div class='blank-pages'>";
      					 result = result+"			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
      					 result = result+"			        <p>No Candidates Found</p>";
      					 result = result+"			 </div>";
      					 result = result+"		</div>"; 
      					 result = result+"	</div>"; 
      					 result = result+"  </div> "; 
      					$("#requirementSideDiv").empty();
      				    $("#requirementSideDiv").append(result); 
               }
               },
                 
            }); 
}
</script>

<%-- 
     @author : sarath kumar.
     this script is for edit Candidate.
     1.usage     :  used in candidateList.jsp.
     2.parameter : csrf token .
     3.ajax url implementation in CandidateListRestController.
--%>
<script>  
function requirementPipeline(requirementId){
	  var sourceleadUrl = '${sourceleadUrl}';
	  $.ajax({
        type : "POST",
         url: sourceleadUrl+"/getRequirementById?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId,        
         success :function(data) {  
              if(data != null && data != 'undefined' && data != ""){  
              	var json = JSON.parse(data);
            		 if( data.indexOf('Sign In') >= 0){
			    		location.replace(sourceleadUrl);
			    	 }
            		 else if (data.indexOf('exception') >= 0) {
            			 location.replace(sourceleadUrl+"/errorPage");
            		 }
            		 else{ 
							var result ="";
							
				            result = result +'<div class="graf_icon"><i class="fa fa-area-chart"></i>Report</div>';
				            result = result +'<div class="pipeline_graf"><img src="images/bargraph.jpg" alt=""/></div>';
				            result = result +'<div class="timesheet_head candidateHead recruitmentHead">';
				            result = result +'  <ul>';
				            result = result +'    <li class="li-vendor">';
				            result = result +'      <input type="text" class="vendorfild" placeholder="Search">';
				            result = result +'      <i class="fa fa-search"></i></li>';
				            result = result +'    <li class="hidden"><i class="glyphicon glyphicon-export"></i><span>Export</span><i class="glyphicon glyphicon-play" id="export-pop"></i>';
				            result = result +'      <div class="export_pop">';
				            result = result +'        <ul>';
				            result = result +'          <li>PDF<i class="fa fa-file-pdf-o"></i></li>';
				            result = result +'          <li>Excel<i class="fa fa-file-excel-o"></i></li>';
				            result = result +'          <li>Word<i class="fa fa-file-word-o"></i></li>';
				            result = result +'        </ul>';
				            result = result +'      </div>';
				            result = result +'    </li>';
				            result = result +'    <li><a href="javascript:void(0)" title=""><i class="fa fa-envelope"></i></a></li>';
				            result = result +'    <li><a href="javascript:void(0)" title=""><i class="fa fa-mail-forward"></i></a></li>';
				            result = result +'    <li><a href="javascript:void(0)" title=""><i class="fa fa-upload"></i></a></li>';
				            result = result +'    <li><a href="javascript:void(0)" title=""><i class="fa fa-download"></i></a></li>';
				            result = result +'    <li><a href="javascript:void(0)" title="" class="add-applicant"><i class="fa fa-plus"></i>&nbsp;Add Applicant</a></li>';
				            result = result +'    <li><a href="javascript:void(0)" title="" class="add-interview"><i class="fa fa-plus"></i>&nbsp;Add Interview</a></li>';
				            result = result +'    <li> <a href="javascript:void(0)" title="" class="add-offer"><i class="fa fa-plus"></i>&nbsp;Add Offer</a> </li>';
				            result = result +'    <div class="clearfix"></div>';
				            result = result +'    <div class="collapseDiv_add collapseDiv_styles col-xs-4 addOffer-Req"> <i class="fa fa-close close-div"></i>';
				            result = result +'     <div class="form-horizontal clearfix">';
				            result = result +'       <div class="row">';
				            result = result +'         <div class="col-md-12 mb10">';
				            result = result +'           <label>Candiate</label>';
				            result = result +'           <select class="form-control selectpicker">';
				            result = result +'             <option>Rameshkumar Korrapati</option>';
				            result = result +'             <option>Prasanth Korrapati</option>';
				            result = result +'             <option>Muralimohan Korrapati</option>';
				            result = result +'             <option>Sarathkumar Korrapati</option>';
				            result = result +'           </select>';
				            result = result +'         </div>';
				            result = result +'         <div class="col-md-12 mb10">';
				            result = result +'           <label>Date of Joining</label>';
				            result = result +'           <div class="positionRelative">';
				            result = result +'             <input type="text" value="" class="form-control input-sm">';
				            result = result +'             <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span> </div>';
				            result = result +'         </div>';
				            result = result +'         <div class="col-md-12 mb10">';
				            result = result +'           <label>Rate / Salary</label>';
				            result = result +'           <div class="row div-padding">';
				            result = result +'             <div class="col-md-4">';
				            result = result +'               <select class="form-control selectpicker">';
				            result = result +'                 <option>Rupee</option>';
				            result = result +'                 <option>Dollar</option>';
				            result = result +'                 <option>Rupee</option>';
				            result = result +'               </select>';
				            result = result +'             </div>';
				            result = result +'             <div class="col-md-4">';
				            result = result +'               <input type="text" value="" class="form-control">';
				            result = result +'             </div>';
				            result = result +'             <div class="col-md-4">';
				            result = result +'               <select class="form-control selectpicker">';
				            result = result +'                 <option>Yearly</option>';
				            result = result +'                 <option>Monthly</option>';
				            result = result +'               </select>';
				            result = result +'             </div>';
				            result = result +'           </div>';
				            result = result +'         </div>';
				            result = result +'         <div class="col-md-12 mb10">';
				            result = result +'           <label>Position Type</label>';
				            result = result +'           <select class="form-control selectpicker">';
				            result = result +'             <option>Full Type</option>';
				            result = result +'             <option>C2C</option>';
				            result = result +'             <option>Parttime</option>';
				            result = result +'           </select>';
				            result = result +'         </div>';
				            result = result +'         <div class="col-md-12 mb10">';
				            result = result +'           <label>Attachment</label>';
				            result = result +'           <div class="input-group">';
				            result = result +'             <label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File';
				            result = result +'               <input type="file" style="display: none;" multiple id="uploadBtn"  class="upload">';
				            result = result +'               </span> </label>';
				            result = result +'             <input type="text" class="form-control" readonly id="uploadFile" placeholder="Files are not selected...">';
				            result = result +'           </div>';
				            result = result +'         </div>';
				            result = result +'         <div class="col-md-12 mb10 hidden">';
				            result = result +'           <button type="button" class="btn btn-danger"><i class="fa fa-pulse"></i>Add</button>';
				            result = result +'         </div>';
				            result = result +'       </div>';
				            result = result +'       <div class="clearfix mt10">';
				            result = result +'         <div class="pull-right">';
				            result = result +'           <button type="submit" class="btn btn-sm btn-primary mr">Submit</button>';
				            result = result +'           <button type="submit" class="btn btn-sm btn-default">Cancel</button>';
				            result = result +'         </div>';
				            result = result +'       </div>';
				            result = result +'     </div>';
				            result = result +'   </div>';
				            result = result +' <div class="collapseDiv_add col-xs-4 collapseDiv_styles addInterview-Re"> <i class="fa fa-close close-div"></i>';
				            result = result +'   <div class="form-horizontal clearfix">';
				            result = result +'     <div class="row">';
				            result = result +'       <div class="col-md-12 mb10">';
				            result = result +'         <label>Candiate</label>';
				            result = result +'         <select class="form-control selectpicker">';
				            result = result +'           <option>Rameshkumar Korrapati</option>';
				            result = result +'           <option>Prasanth Korrapati</option>';
				            result = result +'           <option>Muralimohan Korrapati</option>';
				            result = result +'           <option>Sarathkumar Korrapati</option>';
				            result = result +'         </select>';
				            result = result +'       </div>';
				            result = result +'       <div class="col-md-12 mb10">';
				            result = result +'         <div class="positionRelative date" id="datetimepicker3">';
				            result = result +'           <input type="text" value="" class="form-control input-sm" placeholder="Date of Joining">';
				            result = result +'           <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span> </div>';
				            result = result +'       </div>';
				            result = result +'       <div class="col-md-12 mb10">';
				            result = result +'         <div class="row div-padding">';
				            result = result +'           <div class="col-md-4">';
				            result = result +'             <label>Start Time</label>';
				            result = result +'             <div class="positionRelative">';
				            result = result +'               <input type="text" value="" class="form-control input-sm">';
				            result = result +'               <span class="input-group-addon input-groupAddon"><i class="fa fa-clock-o"></i></span> </div>';
				            result = result +'           </div>';
				            result = result +'           <div class="col-md-4">';
				            result = result +'             <label>End Time</label>';
				            result = result +'             <div class="positionRelative">';
				            result = result +'               <input type="text" value="" class="form-control input-sm">';
				            result = result +'               <span class="input-group-addon input-groupAddon"><i class="fa fa-clock-o"></i></span> </div>';
				            result = result +'           </div>';
				            result = result +'           <div class="col-md-4">';
				            result = result +'             <label>Inteview Type</label>';
				            result = result +'             <select class="form-control selectpicker">';
				            result = result +'               <option>F2F</option>';
				            result = result +'               <option>Telephonic</option>';
				            result = result +'               <option>Skype Call</option>';
				            result = result +'             </select>';
				            result = result +'           </div>';
				            result = result +'         </div>';
				            result = result +'       </div>';
				            result = result +'       <div class="col-md-12 mb10">';
				            result = result +'         <input type="text" value="" class="form-control input-sm" placeholder="Location">';
				            result = result +'       </div>';
				           result = result + '             <div class="col-md-12 mb10">';
				           result = result + '               <label>Interviewer</label>';
				           result = result + '               <select class="form-control selectpicker">';
				           result = result + '                 <option>Hyderabad</option>';
				           result = result + '                 <option>Visakhapatnam</option>';
				           result = result + '                 <option>Pune</option>';
				           result = result + '               </select>';
				           result = result + '             </div>';
				           result = result + '             <div class="col-md-12 mb10">';
				           result = result + '               <label>';
				           result = result + '               Set Reminder &nbsp;<i class="fa fa-gear reminder" title="Click here"></i>';
				           result = result + '               <div class="reminder-div col-sm-7">';
				           result = result + '                 <div class="row mb10">';
				           result = result + '                   <div class="col-md-12">';
				           result = result + '                     <input type="text" class="form-control input-sm" value="" placeholder="Remind before">';
				           result = result + '                   </div>';
				           result = result + '                 </div>';
				           result = result + '                 <div class="row mb10">';
				           result = result + '                   <div class="col-md-12">';
				           result = result + '                     <input type="text" class="form-control input-sm" value="" placeholder="Frequency">';
				           result = result + '                   </div>';
				           result = result + '                 </div>';
				           result = result + '                 <div class="row mb10">';
				           result = result + '                   <div class="col-md-12">';
				           result = result + '                     <input type="text" class="form-control input-sm" value="" placeholder="Notify to">';
				           result = result + '                   </div>';
				           result = result + '                 </div>';
				           result = result + '                 <div class="row">';
				           result = result + '                   <div class="pull-right mr">';
				           result = result + '                     <button type="button" class="btn btn-sm btn-success">Set</button>';
				           result = result + '                   </div>';
				           result = result + '                 </div>';
				           result = result + '               </div>';
				           result = result + '               </label>';
				           result = result + '             </div>';
				           result = result + '             <div class="col-md-12 mb10 hidden">';
				           result = result + '               <button type="button" class="btn btn-danger"><i class="fa fa-pulse"></i>Add</button>';
				           result = result + '             </div>';
				           result = result + '           </div>';
				           result = result + '           <div class="clearfix mt10">';
				           result = result + '             <div class="pull-right">';
				           result = result + '               <button type="submit" class="btn btn-sm btn-primary mr">Submit</button>';
				           result = result + '               <button type="submit" class="btn btn-sm btn-default">Cancel</button>';
				           result = result + '             </div>';
				           result = result + '           </div>';
				           result = result + '         </div>';
				           result = result + '       </div>';
				           result = result + '       <div class="collapseDiv_add col-xs-4 collapseDiv_styles addApplicant-Re"> <i class="fa fa-close close-div"></i>';
				           result = result + '         <div class="form-horizontal clearfix">';
				           result = result + '           <div class="row">';
				           result = result + '             <div class="col-md-12 mb10">';
				           result = result + '               <label>Select Candiate</label>';
				           result = result + '               <select class="form-control selectpicker">';
				           result = result + '                 <option>Rameshkumar Korrapati</option>';
				           result = result + '                 <option>Prasanth Korrapati</option>';
				           result = result + '                 <option>Muralimohan Korrapati</option>';
				           result = result + '                 <option>Sarathkumar Korrapati</option>';
				           result = result + '               </select>';
				           result = result + '             </div>';
				           result = result + '             <div class="clearfix text-center text-info mb10">';
				           result = result + '           -------------------------------- OR --------------------------------';
				           result = result + '              </div>';
				           result = result + '             <div class="col-md-12 mb10">';
						   result = result + '		 <input type="text" value="" class="form-control input-sm" placeholder="First Name *">';
				           result = result + '             </div>';
				           result = result + '             <div class="col-md-12 mb10">';
						   result = result + '		 <input type="text" value="" class="form-control input-sm" placeholder="Last Name">';
				           result = result + '             </div>';
				           result = result + '             <div class="col-md-12 mb10">';
						   result = result + '		 <input type="text" value="" class="form-control input-sm" placeholder="Email*">';
				           result = result + '             </div>';
				           result = result + '             <div class="col-md-12 mb10">';
						   result = result + '		 <input type="text" value="" class="form-control input-sm" placeholder="Phone">';
				           result = result + '             </div>';
				           result = result + '             <div class="col-md-12 mb10">';
						   result = result + '		 <input type="text" value="" class="form-control input-sm" placeholder="Experience">';
				           result = result + '             </div>';
				           result = result + '             <div class="col-md-12">';
				           result = result + '             <div class="panel-border">';
				           result = result + '             <div class="col-md-12">';
				           result = result + '               <div class="input-group">';
				           result = result + '                 <label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File';
				           result = result + '                   <input type="file" style="display: none;" multiple id="uploadBtn"  class="upload">';
				           result = result + '                   </span> </label>';
				           result = result + '                 <input type="text" class="form-control" readonly id="uploadFile" placeholder="Upload Resume *">';
				           result = result + '               </div>';
				           result = result + '             </div>';
				           result = result + '       <div class="col-md-12 mt10 mb10 clearfix text-center text-info">';
				           result = result + '           -------------------------------- OR --------------------------------';
				           result = result + '       </div>';
				           result = result + '       <div class="col-md-12">';
				           result = result + '               <select class="form-control selectpicker">';
				           result = result + '                 <option>Select</option>';
				           result = result + '                 <option>Prasanth Korrapati</option>';
				           result = result + '                 <option>Muralimohan Korrapati</option>';
				           result = result + '                 <option>Sarathkumar Korrapati</option>';
				           result = result + '               </select>';
				           result = result + '             </div>';
				           result = result + '             </div></div>';
				           result = result + '             <div class="col-md-12 mb10">';
				           result = result + '               <label>Expected Rate / Salary <span class="text-danger">*</span></label>';
				           result = result + '               <div class="row div-padding">';
				           result = result + '                 <div class="col-md-4">';
				           result = result + '                   <select class="form-control selectpicker">';
				           result = result + '                     <option>Rupee</option>';
				           result = result + '                     <option>Dollar</option>';
				           result = result + '                     <option>Rupee</option>';
				           result = result + '                   </select>';
				           result = result + '                 </div>';
				           result = result + '                 <div class="col-md-4">';
				           result = result + '                   <input type="text" value="" class="form-control input-sm">';
				           result = result + '                 </div>';
				           result = result + '                 <div class="col-md-4">';
				           result = result + '                   <select class="form-control selectpicker">';
				           result = result + '                     <option>Yearly</option>';
				           result = result + '                     <option>Monthly</option>';
				           result = result + '                   </select>';
				           result = result + '                 </div>';
				           result = result + '               </div>';
				           result = result + '             </div>';
				           result = result + '           </div>';
				           result = result + '           <div class="clearfix mt10">';
				           result = result + '             <div class="pull-right">';
				           result = result + '               <button type="submit" class="btn btn-sm btn-primary mr">Submit</button>';
				           result = result + '               <button type="submit" class="btn btn-sm btn-default">Cancel</button>';
				           result = result + '             </div>';
				           result = result + '           </div>';
				           result = result + '         </div>';
				           result = result + '       </div>';
				           result = result + '     </ul>';
				           result = result + '     <div class="clearfix"></div>';
				           result = result + '   </div>';
				           result = result + '   <div class="require_wrap_table">';
				           result = result + '     <table>';
				           result = result + '       <tbody>';
				           result = result + '       <thead>';
				           result = result + '         <tr>';
				           result = result + '           <th><input type="checkbox" id="checkbox1"/></th>';
				           result = result + '           <th>Name</th>';
				           result = result + '           <th>Email</th>';
				           result = result + '           <th>Ph. No</th>';
				           result = result + '           <th>Resume</th>';
				           result = result + '           <th>Applied on</th>';
				           result = result + '           <th>Rate</th>';
				           result = result + '           <th>Source</th>';
				           result = result + '           <th>Experince</th>';
				           result = result + '           <th>Status</th>';
				           result = result + '         </tr>';
				           result = result + '       </thead>';
				           result = result + '       <tr>';
				           result = result + '         <td><input type="checkbox" id="checkbox1"/></td>';
				           result = result + '         <td><div><span>Vannam vannam vannam vannam</span></div></td>';
				           result = result + '         <td><div><span>Vannam vannam vannam vannam</span></div></td>';
				           result = result + '         <td><div><span>9000 000 000</span></div></td>';
				           result = result + '         <td><div><a href="javascript:void(0)"><i class="glyphicon glyphicon-list-alt"></i></a></div></td>';
				           result = result + '         <td><div><span>08/08/2016</span></div></td>';
				           result = result + '         <td><div><span>12345678</span></div></td>';
				           result = result + '         <td><div><span>Careers</span></div></td>';
				           result = result + '         <td><div>3.4 Years</div></td>';
				           result = result + '         <td><div><span>';
				           result = result + '             <select>';
				           result = result + '               <option>Interview</option>';
				           result = result + '               <option>Submit</option>';
				           result = result + '               <option>Joined</option>';
				           result = result + '               <option>Screening</option>';
				           result = result + '               <option>Offer</option>';
				           result = result + '             </select>';
				           result = result + '             </span></div></td>';
				           result = result + '       </tr>';
				           result = result + '       <tr>';
				           result = result + '         <td><input type="checkbox" id="checkbox1"/></td>';
				           result = result + '         <td><div><span>Vannam vannam vannam vannam</span></div></td>';
				           result = result + '         <td><div><span>Vannam vannam vannam vannam</span></div></td>';
				           result = result + '         <td><div>9000 000 000</div></td>';
				           result = result + '         <td><div><a href="javascript:void(0)"><i class="glyphicon glyphicon-list-alt"></i></a></div></td>';
				           result = result + '         <td><div><span>08/08/2016</span></div></td>';
				           result = result + '         <td><div><span>12345678</span></div></td>';
				           result = result + '         <td><div>Careers</div></td>';
				           result = result + '         <td><div>3.4 Years</div></td>';
				           result = result + '         <td><div><span><b class="proj_process">Interview</b><i class=" fa fa-edit"></i></span></div></td>';
				           result = result + '       </tr>';
				           result = result + '       <tr>';
				           result = result + '         <td><input type="checkbox" id="checkbox1"/></td>';
				           result = result + '         <td><div><span>Vannam vannam vannam vannam</span></div></td>';
				           result = result + '         <td><div><span>Vannam vannam vannam vannam</span></div></td>';
				           result = result + '         <td><div>9000 000 000</div></td>';
				           result = result + '         <td><div><a href="javascript:void(0)"><i class="glyphicon glyphicon-list-alt"></i></a></div></td>';
				           result = result + '         <td><div><span>08/08/2016</span></div></td>';
				           result = result + '         <td><div><span>12345678</span></div></td>';
				           result = result + '         <td><div>Careers</div></td>';
				           result = result + '         <td><div>3.4 Years</div></td>';
				           result = result + '         <td><div><span><b class="proj_process">Interview</b><i class=" fa fa-edit"></i></span></div></td>';
				           result = result + '       </tr>';
				           result = result + '       </tbody>';
				           result = result + '     </table>';
				           result = result + '   </div>';
				           result = result + '   <nav>';
				           result = result + '     <ul id="paging" class="pagination">';
				           result = result + '       <li><a href="#">Previous</a></li>';
				           result = result + '       <li><a href="#">2</a></li>';
				           result = result + '       <li><a href="#">3</a></li>';
				           result = result + '       <li class="disabled"><a href="#">6</a></li>';
				           result = result + '       <li data-page="last"><a href="#">Next</a></li>';
				           result = result + '     </ul>';
				           result = result + '     <div class="clearfix"></div>';
				           result = result + '   </nav>';
				            
				            
							$("#tab-1").empty();
							$("#tab-2").empty(); 
							$("#tab-2").append(result);  
							    
							$("#tab-2").show();
							$(".project-tabs-menu a").click(function(event) {
							     event.preventDefault();
							     $(this).parent().addClass("current");
							     $(this).parent().siblings().removeClass("current");
							     var tab = $(this).attr("href");
							     $(".project_tabs_inn").not(tab).css("display", "none");
							     $(tab).fadeIn();
							 });
            		 }
              }else{
                     	 $("#requirementSideDiv").removeAttr("style");
       	    	         var result ="";
      		    	     result = result+"   <div class='row'>"; 
      					 result = result+"	<div class='col-lg-12'>"; 
      					 result = result+"		<div class='content-area clearfix'>"; 
      					 result = result+"			<div class='blank-pages'>";
      					 result = result+"			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
      					 result = result+"			        <p>No Candidates Found</p>";
      					 result = result+"			 </div>";
      					 result = result+"		</div>"; 
      					 result = result+"	</div>"; 
      					 result = result+"  </div> "; 
      					$("#requirementSideDiv").empty();
      				    $("#requirementSideDiv").append(result); 
                      }
               },
                 
            }); 
}
</script>

<%-- 
     @author : sarath kumar.
     this script is for edit Candidate.
     1.usage     :  used in candidateList.jsp.
     2.parameter : csrf token .
     3.ajax url implementation in CandidateListRestController.
--%>
<script>  
function assignedMemberList(requirementId){
	  var sourceleadUrl = '${sourceleadUrl}';
	  $.ajax({
        type : "POST",
         url: sourceleadUrl+"/getRequirementAssignedListByRequirementId?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId,        
         success :function(data) {  
              if(data != null && data != 'undefined' && data != ""){  
              	var json = JSON.parse(data);
            		 if( data.indexOf('Sign In') >= 0){
			    		location.replace(sourceleadUrl);
			    	 }
            		 else if (data.indexOf('exception') >= 0) {
            			 location.replace(sourceleadUrl+"/errorPage");
            		 }
            		 else{ 
							var result = '';
							result = result +'     <h2>Assigned Members</h2> ';
						    result = result +'     <div class="heightScroll mt10 mb">';
						    result = result +'         <table class="table table-responsive table-hover clearfix mb">';
						    result = result +'         <thead>';
						    result = result +'         <tr>';
						    result = result +'         <th>Name</th>';
						    result = result +'         <th>Role</th>';
						    result = result +'         <th>Team</th>';
						    result = result +'         <th>No. of Applicants</th>';
						    result = result +'         </tr>';
						    result = result +'         </thead>';
						    result = result +'         <tbody>';
						    for (var i=0; i<json.length; i++)
			   				{
						    var report = json[i];
						    result = result +'         <tr>';
						    result = result +'         <td><div class="tbldata-dotdot-me">'+report.assignedUserName+'</div></td>';
						    result = result +'         <td><div class="tbldata-dotdot-sm">'+report.assignedUserRole+'</div></td>';
						    result = result +'         <td><div class="tbldata-dotdot-sm">'+report.teamName+'</div></td>';
						    result = result +'         <td><div class="tbldata-dotdot-sm">020</div></td>';
						    result = result +'         </tr>';
			   				}
						    result = result +'         </tbody>';
						    result = result +'         </table>';
						    result = result +'     </div>';
						    result = result +'     </div>';
						    result = result +' </div> ';
						    
						    $("#assignedMemberList").empty();
						    $("#assignedMemberList").append(result);
							
            		 }
              }else{
                     	 $("#assignedMemberList").removeAttr("style");
       	    	         var result ="";
      		    	     result = result+"   <div class='row'>"; 
      					 result = result+"	<div class='col-lg-12'>"; 
      					 result = result+"		<div class='content-area clearfix'>"; 
      					 result = result+"			<div class='blank-pages'>";
      					 result = result+"			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
      					 result = result+"			        <p>No AssignedMember Found</p>";
      					 result = result+"			 </div>";
      					 result = result+"		</div>"; 
      					 result = result+"	</div>"; 
      					 result = result+"  </div> "; 
      					$("#assignedMemberList").empty();
      				    $("#assignedMemberList").append(result); 
                      }
               },
                 
            }); 
}
</script>

