<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="resources/js/jquery-fun.js"></script> 
<script src="resources/js/formvalidator.js"></script>




<!-- <!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Welcome to Source Lead :: Recruitment-Add Vendor</title>
Bootstrap
<link href="css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="css/bootstrap-select.min.css">
<link rel="stylesheet" href="datetimepicker.css">
<link rel="stylesheet" href="css/chosen.min.css">
<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.3/css/font-awesome.min.css" rel="stylesheet">
<link href="css/sourcelead-main.css" rel="stylesheet">
<link href="css/timesheet.css" rel="stylesheet">
</head>
<body> -->
<!-- <div class="wraper"> -->

<!-- Content Area -->
<section class="content-wraper">
  <section class="content-header clearfix">
    <div class="pull-left">
      <h2>Requirement Details</h2>
    </div>
    <div class="pull-right brud-crum">Home &raquo; Vendor &raquo; Client Contact Details</div>
  </section>
  <div class="timesheet_out">
    <div class="vendor_wrap">
      <div class="timesheet_head">
        <ul>
          <li><i class="glyphicon glyphicon-export"></i><span>Export</span><i class="glyphicon glyphicon-play" id="export-pop"></i>|
            <div class="export_pop">
              <ul>
                <li>PDF<i class="fa fa-file-pdf-o"></i></li>
                <li>Excel<i class="fa fa-file-excel-o"></i></li>
                <li>Word<i class="fa fa-file-word-o"></i></li>
              </ul>
            </div>
          </li>
          <li> <a href="<c:url value='/requirement'/>"><i class="fa fa-plus"></i>Add Requirement</a></li>
          <div class="clearfix"></div>
        </ul>
        <div class="clearfix"></div>
        <div class="remove_search">
          <input type="text" placeholder="search" />
        </div>
      </div>
      <div class="require_wrap_table">
        <table>
          <tbody>
          <thead>
            <tr>
              <th><input type="checkbox" id="checkbox1"/></th>
              <th>Title</th>
              <th>Client</th>
              <th>Position type</th>
              <th>Location</th>
              <th>No.of<br>
                Positions</th>
              <th>Owner</th>
              <th>Status</th>
              <th>Release</th>
            </tr>
          </thead>
          <tr>
            <td><input type="checkbox" id="checkbox1"/></td>
            <td><div class="proj_show"><span>Vannam vannam vannam vannam</span><i class="fa fa-binoculars"></i></div></td>
            <td><div><span>Vannam vannam vannam vannam</span></div></td>
            <td><div>Full Time</div></td>
            <td><div><span><span>Vannam vannam vannam vannam</span></span></div></td>
            <td><div>2</div></td>
            <td><div>Name here</div></td>
            <td><div><b class="proj_process">Process</b></div></td>
            <td><div><a href="javascript:void(0)"><i class="fa fa-bullhorn"></i></a></div></td>
          </tr>
          <tr>
            <td><input type="checkbox" id="checkbox1"/></td>
            <td><div class="proj_show"><span>Vannam vannam vannam vannam</span><i class="fa fa-binoculars"></i></div></td>
            <td><div><span>Vannam vannam vannam vannam</span></div></td>
            <td><div>Full Time</div></td>
            <td><div><span><span>Vannam vannam vannam vannam</span></span></div></td>
            <td><div>2</div></td>
            <td><div>Name here</div></td>
            <td><div><b>Open</b></div></td>
            <td><div><a href="javascript:void(0)"><i class="fa fa-bullhorn"></i></a></div></td>
          </tr>
          <tr>
            <td><input type="checkbox" id="checkbox1"/></td>
            <td><div class="proj_show"><span>Vannam vannam vannam vannam</span><i class="fa fa-binoculars"></i></div></td>
            <td><div><span>Vannam vannam vannam vannam</span></div></td>
            <td><div>Full Time</div></td>
            <td><div><span><span>Vannam vannam vannam vannam</span></span></div></td>
            <td><div>2</div></td>
            <td><div>Name here</div></td>
            <td><div><b>Open</b></div></td>
            <td><div><a href="javascript:void(0)"><i class="fa fa-bullhorn"></i></a></div></td>
          </tr>
          <tr>
            <td><input type="checkbox" id="checkbox1"/></td>
            <td><div class="proj_show"><span>Vannam vannam vannam vannam</span><i class="fa fa-binoculars"></i></div></td>
            <td><div><span>Vannam vannam vannam vannam</span></div></td>
            <td><div>Full Time</div></td>
            <td><div><span><span>Vannam vannam vannam vannam</span></span></div></td>
            <td><div>2</div></td>
            <td><div>Name here</div></td>
            <td><div><b class="proj_closed">Closed</b></div></td>
            <td><div><a href="javascript:void(0)"><i class="fa fa-bullhorn"></i></a></div></td>
          </tr>
          <tr>
            <td><input type="checkbox" id="checkbox1"/></td>
            <td><div class="proj_show"><span>Vannam vannam vannam vannam</span><i class="fa fa-binoculars"></i></div></td>
            <td><div><span>Vannam vannam vannam vannam</span></div></td>
            <td><div>Full Time</div></td>
            <td><div><span><span>Vannam vannam vannam vannam</span></span></div></td>
            <td><div>2</div></td>
            <td><div>Name here</div></td>
            <td><div><b class="proj_active">Active</b></div></td>
            <td><div><a href="javascript:void(0)"><i class="fa fa-bullhorn"></i></a></div></td>
          </tr>
          <tr>
            <td><input type="checkbox" id="checkbox1"/></td>
            <td><div class="proj_show"><span>Vannam vannam vannam vannam</span><i class="fa fa-binoculars"></i></div></td>
            <td><div><span>Vannam vannam vannam vannam</span></div></td>
            <td><div>Full Time</div></td>
            <td><div><span><span>Vannam vannam vannam vannam</span></span></div></td>
            <td><div>2</div></td>
            <td><div>Name here</div></td>
            <td><div><b class="proj_closed">Closed</b></div></td>
            <td><div><a href="javascript:void(0)"><i class="fa fa-bullhorn"></i></a></div></td>
          </tr>
          <tr>
            <td><input type="checkbox" id="checkbox1"/></td>
            <td><div class="proj_show"><span>Vannam vannam vannam vannam</span><i class="fa fa-binoculars"></i></div></td>
            <td><div><span>Vannam vannam vannam vannam</span></div></td>
            <td><div>Full Time</div></td>
            <td><div><span><span>Vannam vannam vannam vannam</span></span></div></td>
            <td><div>2</div></td>
            <td><div>Name here</div></td>
            <td><div><b class="proj_active">Active</b></div></td>
            <td><div><a href="javascript:void(0)"><i class="fa fa-bullhorn"></i></a></div></td>
          </tr>
          <tr>
            <td><input type="checkbox" id="checkbox1"/></td>
            <td><div class="proj_show"><span>Vannam vannam vannam vannam</span><i class="fa fa-binoculars"></i></div></td>
            <td><div><span>Vannam vannam vannam vannam</span></div></td>
            <td><div>Full Time</div></td>
            <td><div><span><span>Vannam vannam vannam vannam</span></span></div></td>
            <td><div>2</div></td>
            <td><div>Name here</div></td>
            <td><div><b class="proj_closed">Closed</b></div></td>
            <td><div><a href="javascript:void(0)"><i class="fa fa-bullhorn"></i></a></div></td>
          </tr>
          <tr>
            <td><input type="checkbox" id="checkbox1"/></td>
            <td><div class="proj_show"><span>Vannam vannam vannam vannam</span><i class="fa fa-binoculars"></i></div></td>
            <td><div><span>Vannam vannam vannam vannam</span></div></td>
            <td><div>Full Time</div></td>
            <td><div><span><span>Vannam vannam vannam vannam</span></span></div></td>
            <td><div>2</div></td>
            <td><div>Name here</div></td>
            <td><div><b class="proj_active">Active</b></div></td>
            <td><div><a href="javascript:void(0)"><i class="fa fa-bullhorn"></i></a></div></td>
          </tr>
          <tr>
            <td><input type="checkbox" id="checkbox1"/></td>
            <td><div class="proj_show"><span>Vannam vannam vannam vannam</span><i class="fa fa-binoculars"></i></div></td>
            <td><div><span>Vannam vannam vannam vannam</span></div></td>
            <td><div>Full Time</div></td>
            <td><div><span><span>Vannam vannam vannam vannam</span></span></div></td>
            <td><div>2</div></td>
            <td><div>Name here</div></td>
            <td><div><b class="proj_closed">Closed</b></div></td>
            <td><div><a href="javascript:void(0)"><i class="fa fa-bullhorn"></i></a></div></td>
          </tr>
          </tbody>
          
        </table>
      </div>
      <nav>
        <ul id="paging" class="pagination">
          <li><a href="#">Previous</a></li>
          <li><a href="#">2</a></li>
          <li><a href="#">3</a></li>
          <li><a href="#">4</a></li>
          <li><a href="#">5</a></li>
          <li class="disabled"><a href="#">6</a></li>
          <li data-page="last"><a href="#">Next</a></li>
        </ul>
        <div class="clearfix"></div>
      </nav>
    </div>
    <div class="side_mask">
      <div class="proj_close"></div>
    </div>
    <div class="clients_side_wrap">
      <div class="clients_side_wrap_inn">
        <div class="profile_details requirement_details clearfix">
          <div class="col-md-9">
            <div class="row">
              <div class="col-md-3">
                <div class="company-logo">
                  <div class="imageParent"> <img src="./images/dell-logo.png" alt="Company" title="Company Logo" class="img-responsive"> </div>
                  <span class="hidden"> TATA Consulting Private Limited, Hyderebad. </span> </div>
              </div>
              <div class="col-md-9">
                <div class="canDetails clearfix">
                  <h4>SL 201 - Business Analyst - Full Time</h4>
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
        </div>
        <div class="proj_close"></div>
        <div class="project_side_wrap_inn_tabs">
          <ul class="project-tabs-menu">
            <li class="current"><a href="#tab-1">Details</a></li>
            <li><a href="#tab-2">Pipeline</a></li>
            <div class="clearfix"></div>
          </ul>
          <div class="project_tabs">
            <div id="tab-1" class="project_tabs_inn">
              <div class="require_wrap_details">
                <h1>Description:</h1>
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
                  <!-- Popup Div -- Add Offer -->
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
                  <!-- Popup Div -- Add Offer Ends --> 
                  <!-- Popup Div -- Add Interview -->
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
                  <!-- Popup Div -- Add Interview -->
                  <!-- Popup Div -- add Applicant -->
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
                  <!-- Popup Div -- add Applicant -->
                  
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
                      <th>Experince</th>
                      <th>Status</th>
                    </tr>
                  </thead>
                  <tr>
                    <td><input type="checkbox" id="checkbox1"/></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div><span>9000 000 000</span></div></td>
                    <td><div><a href="javascript:void(0)"><i class="glyphicon glyphicon-list-alt"></i></a></div></td>
                    <td><div><span>08/08/2016</span></div></td>
                    <td><div><span>12345678</span></div></td>
                    <td><div><span>Careers</span></div></td>
                    <td><div>3.4 Years</div></td>
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
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div>9000 000 000</div></td>
                    <td><div><a href="javascript:void(0)"><i class="glyphicon glyphicon-list-alt"></i></a></div></td>
                    <td><div><span>08/08/2016</span></div></td>
                    <td><div><span>12345678</span></div></td>
                    <td><div>Careers</div></td>
                    <td><div>3.4 Years</div></td>
                    <td><div><span><b class="proj_process">Interview</b><i class=" fa fa-edit"></i></span></div></td>
                  </tr>
                  <tr>
                    <td><input type="checkbox" id="checkbox1"/></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div>9000 000 000</div></td>
                    <td><div><a href="javascript:void(0)"><i class="glyphicon glyphicon-list-alt"></i></a></div></td>
                    <td><div><span>08/08/2016</span></div></td>
                    <td><div><span>12345678</span></div></td>
                    <td><div>Careers</div></td>
                    <td><div>3.4 Years</div></td>
                    <td><div><span><b class="proj_process">Interview</b><i class=" fa fa-edit"></i></span></div></td>
                  </tr>
                  <tr>
                    <td><input type="checkbox" id="checkbox1"/></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div>9000 000 000</div></td>
                    <td><div><a href="javascript:void(0)"><i class="glyphicon glyphicon-list-alt"></i></a></div></td>
                    <td><div><span>08/08/2016</span></div></td>
                    <td><div><span>12345678</span></div></td>
                    <td><div>Careers</div></td>
                    <td><div>3.4 Years</div></td>
                    <td><div><span><b class="proj_process">Interview</b><i class=" fa fa-edit"></i></span></div></td>
                  </tr>
                  <tr>
                    <td><input type="checkbox" id="checkbox1"/></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div>9000 000 000</div></td>
                    <td><div><a href="javascript:void(0)"><i class="glyphicon glyphicon-list-alt"></i></a></div></td>
                    <td><div><span>08/08/2016</span></div></td>
                    <td><div><span>12345678</span></div></td>
                    <td><div>Careers</div></td>
                    <td><div>3.4 Years</div></td>
                    <td><div><span><b class="proj_process">Interview</b><i class=" fa fa-edit"></i></span></div></td>
                  </tr>
                  <tr>
                    <td><input type="checkbox" id="checkbox1"/></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div>9000 000 000</div></td>
                    <td><div><a href="javascript:void(0)"><i class="glyphicon glyphicon-list-alt"></i></a></div></td>
                    <td><div><span>08/08/2016</span></div></td>
                    <td><div><span>12345678</span></div></td>
                    <td><div>Careers</div></td>
                    <td><div>3.4 Years</div></td>
                    <td><div><span><b class="proj_process">Interview</b><i class=" fa fa-edit"></i></span></div></td>
                  </tr>
                  <tr>
                    <td><input type="checkbox" id="checkbox1"/></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div>9000 000 000</div></td>
                    <td><div><a href="javascript:void(0)"><i class="glyphicon glyphicon-list-alt"></i></a></div></td>
                    <td><div><span>08/08/2016</span></div></td>
                    <td><div><span>12345678</span></div></td>
                    <td><div>Careers</div></td>
                    <td><div>3.4 Years</div></td>
                    <td><div><span><b>Offer</b><i class=" fa fa-edit"></i></span></div></td>
                  </tr>
                  <tr>
                    <td><input type="checkbox" id="checkbox1"/></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div>9000 000 000</div></td>
                    <td><div><a href="javascript:void(0)"><i class="glyphicon glyphicon-list-alt"></i></a></div></td>
                    <td><div><span>08/08/2016</span></div></td>
                    <td><div><span>12345678</span></div></td>
                    <td><div>Careers</div></td>
                    <td><div>3.4 Years</div></td>
                    <td><div><span><b>Offer</b><i class=" fa fa-edit"></i></span></div></td>
                  </tr>
                  <tr>
                    <td><input type="checkbox" id="checkbox1"/></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div>9000 000 000</div></td>
                    <td><div><a href="javascript:void(0)"><i class="glyphicon glyphicon-list-alt"></i></a></div></td>
                    <td><div><span>08/08/2016</span></div></td>
                    <td><div><span>12345678</span></div></td>
                    <td><div>Careers</div></td>
                    <td><div>3.4 Years</div></td>
                    <td><div><span><b class="proj_closed">Submit</b><i class=" fa fa-edit"></i></span></div></td>
                  </tr>
                  <tr>
                    <td><input type="checkbox" id="checkbox1"/></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div>9000 000 000</div></td>
                    <td><div><a href="javascript:void(0)"><i class="glyphicon glyphicon-list-alt"></i></a></div></td>
                    <td><div><span>08/08/2016</span></div></td>
                    <td><div><span>12345678</span></div></td>
                    <td><div>Careers</div></td>
                    <td><div>3.4 Years</div></td>
                    <td><div><span><b class="proj_closed">Submit</b><i class=" fa fa-edit"></i></span></div></td>
                  </tr>
                  <!--<tr>
                	<td><input type="checkbox" id="checkbox1"/></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div>Full Time</div></td>
                    <td><div><a href="javascript:void(0)"><i class="glyphicon glyphicon-list-alt"></i></a></div></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>                    
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div>9000 000 000</div></td>
                    <td><div>08/08/2016 07:30 AM</div></td>
                    <td><div>Careers</div></td>
                    <td><div>3.4 Years</div></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div><span><b class="proj_closed">Submit</b><i class=" fa fa-edit"></i></span></div></td>
                </tr>
                <tr>
                	<td><input type="checkbox" id="checkbox1"/></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div>Full Time</div></td>
                    <td><div><a href="javascript:void(0)"><i class="glyphicon glyphicon-list-alt"></i></a></div></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>                    
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div>9000 000 000</div></td>
                    <td><div>08/08/2016 07:30 AM</div></td>
                    <td><div>Careers</div></td>
                    <td><div>3.4 Years</div></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div><span><b class="proj_active">Joined</b><i class=" fa fa-edit"></i></span></div></td>
                </tr>
                <tr>
                	<td><input type="checkbox" id="checkbox1"/></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div>Full Time</div></td>
                    <td><div><a href="javascript:void(0)"><i class="glyphicon glyphicon-list-alt"></i></a></div></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>                    
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div>9000 000 000</div></td>
                    <td><div>08/08/2016 07:30 AM</div></td>
                    <td><div>Careers</div></td>
                    <td><div>3.4 Years</div></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div><span><b class="proj_active">Offer</b><i class=" fa fa-edit"></i></span></div></td>
                </tr>
                <tr>
                	<td><input type="checkbox" id="checkbox1"/></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div><span>Full Time</span></div></td>
                    <td><div><a href="javascript:void(0)"><i class="glyphicon glyphicon-list-alt"></i></a></div></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>                    
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div><span>9000 000 000</span></div></td>
                    <td><div><span>08/08/2016 07:30 AM</span></div></td>
                    <td><div><span>Careers</span></div></td>
                    <td><div>3.4 Years</div></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div><span><b>Screening</b><i class=" fa fa-edit"></i></span></div></td>
                </tr>
                <tr>
                	<td><input type="checkbox" id="checkbox1"/></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div>Full Time</div></td>
                    <td><div><a href="javascript:void(0)"><i class="glyphicon glyphicon-list-alt"></i></a></div></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>                    
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div>9000 000 000</div></td>
                    <td><div>08/08/2016 07:30 AM</div></td>
                    <td><div>Careers</div></td>
                    <td><div>3.4 Years</div></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div><span><b class="proj_process">Interview</b><i class=" fa fa-edit"></i></span></div></td>
                </tr>
                <tr>
                	<td><input type="checkbox" id="checkbox1"/></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div>Full Time</div></td>
                    <td><div><a href="javascript:void(0)"><i class="glyphicon glyphicon-list-alt"></i></a></div></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>                    
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div>9000 000 000</div></td>
                    <td><div>08/08/2016 07:30 AM</div></td>
                    <td><div>Careers</div></td>
                    <td><div>3.4 Years</div></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div><span><b class="proj_closed">Submit</b><i class=" fa fa-edit"></i></span></div></td>
                </tr>
                <tr>
                	<td><input type="checkbox" id="checkbox1"/></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div>Full Time</div></td>
                    <td><div><a href="javascript:void(0)"><i class="glyphicon glyphicon-list-alt"></i></a></div></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>                    
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div>9000 000 000</div></td>
                    <td><div>08/08/2016 07:30 AM</div></td>
                    <td><div>Careers</div></td>
                    <td><div>3.4 Years</div></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div><span><b class="proj_active">Joined</b><i class=" fa fa-edit"></i></span></div></td>
                </tr>
                <tr>
                	<td><input type="checkbox" id="checkbox1"/></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div>Full Time</div></td>
                    <td><div><a href="javascript:void(0)"><i class="glyphicon glyphicon-list-alt"></i></a></div></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>                    
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div>9000 000 000</div></td>
                    <td><div>08/08/2016 07:30 AM</div></td>
                    <td><div>Careers</div></td>
                    <td><div>3.4 Years</div></td>
                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
                    <td><div><span><b class="proj_active">Offer</b><i class=" fa fa-edit"></i></span></div></td>
                </tr>-->
                  
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
        </div>
      </div>
    </div>
    <!-- Project --> 
  </div>
</section>
<!-- Footer Area -->
<!-- <footer class="footer-area clearfix">
  <div class="copyright"> @ Copyright : Source Lead, All right reserved </div>
  <div class="powered-by"> Powered by: businessintelli.com </div>
</footer> -->
<!-- Footer Area --> 

<!--   Error Message   -->
<div class="vendor_wrap_mask"></div>
<div class="vendor_error">
  <p>Delete conformation message....</p>
  <textarea placeholder="Enter message"></textarea>
  <a class="close_color" href="javascript:void(0)">Yes</a><a href="javascript:void(0)">No</a> </div>

<!--   Error Message   --> 

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) --> 
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script> 
<script src="js/moment.min.js"></script>
<script src="js/bootstrap-datetimepicker.min.js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script> 
<script src="js/masterHeaderAction.js"></script> 
<script src="js/bootstrap-select.min.js"></script> 
<script src="js/chosen.jquery.min.js"></script> 

<script type="text/javascript">
            $(function () {
                $('#datetimepicker3').datetimepicker({
                    format: 'LT'
                });
            });
        </script> 
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
<div class="theme-color"> <span class="icon-theme" title="click here"><i class="fa fa-gears"></i></span>
  <ul class="list-unstyled">
    <li><a href="#" class="" id="btn1"><span class="dark-blue"></span><span class="light-grey"></span></a></li>
    <li><a href="#" class="" id="btn2"><span class="flat-green"></span><span class="dark-purle"></span></a></li>
    <li><a href="#" class="" id="btn3"><span class="hdr-blue"></span><span class="sidenav-blue"></span></a></li>
  </ul>
</div>
</body>
</html>




  
<jsp:include page="../masterFooter.jsp" />