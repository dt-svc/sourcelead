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
      <h2>Candidate list</h2>
    </div>
    <div class="pull-right brud-crum">Home &raquo; Vendor &raquo; Client Contact Details</div>
  </section>
  <div class="timesheet_out">
    <div class="vendor_wrap">
      <div class="timesheet_head candidateHead">
        <ul>
          <li class="hidden"><i class="glyphicon glyphicon-export"></i><span>Export</span><i class="glyphicon glyphicon-play" id="export-pop"></i>
            <div class="export_pop">
              <ul>
                <li>PDF<i class="fa fa-file-pdf-o"></i></li>
                <li>Excel<i class="fa fa-file-excel-o"></i></li>
                <li>Word<i class="fa fa-file-word-o"></i></li>
              </ul>
            </div>
          </li>
         <!--  <li><a href="javascript:void(0)" title=""><i class="fa fa-envelope"></i></a></li>
          <li><a href="javascript:void(0)" title=""><i class="fa fa-mail-forward"></i></a></li>
          <li><a href="javascript:void(0)" title=""><i class="fa fa-upload"></i></a></li>
          <li><a href="javascript:void(0)" title=""><i class="fa fa-download"></i></a></li> -->
          <li><a href="<c:url value='/candidate'/>" title=""><i class="fa fa-plus"></i>Add Candidate</a></li>
          <div class="clearfix"></div>
        </ul>
        <div class="clearfix"></div>
        
            <div class="remove_search">
            	<input type="text" placeholder="search" />
            </div>          
        
      </div>
      <div class="candidate_wrap_table">
        <table>
          <tbody>
          <thead>
            <tr>
              <th><input type="checkbox" id="checkbox1"/></th>
              <th>Name</th>
              <th>Designation</th>
              <th>Position type</th>
              <th>Email</th>
              <th>Mobile</th>
              <th>Address</th>
              <th>Last Update</th>
            </tr>
          </thead>
          <tr>
            <td><input type="checkbox" id="checkbox12"/></td>
            <td><div class="proj_show"><span>Vannam vannam vannam vannam</span><i class="fa fa-binoculars"></i></div></td>
            <td><div><span>Web designer Web designer</span></div></td>
            <td><div>Full Time</div></td>
            <td><div ><span>vannam@domain.com</span></div></td>
            <td><div>9000 000 000</div></td>
            <td><div><span title="Hyderabad, Hyderabad, Hyderabad">Hyderabad, Hyderabad, Hyderabad</span></div></td>
            <td><div>11/08/2016</div></td>
          </tr>
          <tr>
            <td><input type="checkbox" id="checkbox1"/></td>
            <td><div class="proj_show"><span class="td_overflow">Broadridge</span><i class="fa fa-binoculars"></i></div></td>
            <td><div><span>Web designer</span></div></td>
            <td><div>Full Time</div></td>
            <td><div>domain.com</div></td>
            <td><div>9000 000 000</div></td>
            <td><div><span title="Hyderabad, Hyderabad, Hyderabad" class="td_overflow">Hyderabad, Hyderabad, Hyderabad</span></div></td>
            <td><div>11/08/2016</div></td>
          </tr>
          <tr>
            <td><input type="checkbox" id="checkbox1"/></td>
            <td><div class="proj_show"><span class="td_overflow">Broadridge</span><i class="fa fa-binoculars"></i></div></td>
            <td><div><span>Web designer</span></div></td>
            <td><div>Full Time</div></td>
            <td><div>domain.com</div></td>
            <td><div>9000 000 000</div></td>
            <td><div><span title="Hyderabad, Hyderabad, Hyderabad" class="td_overflow">Hyderabad, Hyderabad, Hyderabad</span></div></td>
            <td><div>11/08/2016</div></td>
          </tr>
          <tr>
            <td><input type="checkbox" id="checkbox1"/></td>
            <td><div class="proj_show"><span class="td_overflow">Broadridge</span><i class="fa fa-binoculars"></i></div></td>
            <td><div><span>Web designer</span></div></td>
            <td><div>Full Time</div></td>
            <td><div>domain.com</div></td>
            <td><div>9000 000 000</div></td>
            <td><div><span title="Hyderabad, Hyderabad, Hyderabad" class="td_overflow">Hyderabad, Hyderabad, Hyderabad</span></div></td>
            <td><div>11/08/2016</div></td>
          </tr>
          <tr>
            <td><input type="checkbox" id="checkbox1"/></td>
            <td><div class="proj_show"><span class="td_overflow">Broadridge</span><i class="fa fa-binoculars"></i></div></td>
            <td><div><span>Web designer</span></div></td>
            <td><div>Full Time</div></td>
            <td><div>domain.com</div></td>
            <td><div>9000 000 000</div></td>
            <td><div><span title="Hyderabad, Hyderabad, Hyderabad" class="td_overflow">Hyderabad, Hyderabad, Hyderabad</span></div></td>
            <td><div>11/08/2016</div></td>
          </tr>
          <tr>
            <td><input type="checkbox" id="checkbox1"/></td>
            <td><div class="proj_show"><span class="td_overflow">Broadridge</span><i class="fa fa-binoculars"></i></div></td>
            <td><div><span>Web designer</span></div></td>
            <td><div>Full Time</div></td>
            <td><div>domain.com</div></td>
            <td><div>9000 000 000</div></td>
            <td><div><span title="Hyderabad, Hyderabad, Hyderabad" class="td_overflow">Hyderabad, Hyderabad, Hyderabad</span></div></td>
            <td><div>11/08/2016</div></td>
          </tr>
          <tr>
            <td><input type="checkbox" id="checkbox1"/></td>
            <td><div class="proj_show"><span class="td_overflow">Broadridge</span><i class="fa fa-binoculars"></i></div></td>
            <td><div><span>Web designer</span></div></td>
            <td><div>Full Time</div></td>
            <td><div>domain.com</div></td>
            <td><div>9000 000 000</div></td>
            <td><div><span title="Hyderabad, Hyderabad, Hyderabad" class="td_overflow">Hyderabad, Hyderabad, Hyderabad</span></div></td>
            <td><div>11/08/2016</div></td>
          </tr>
          <tr>
            <td><input type="checkbox" id="checkbox1"/></td>
            <td><div class="proj_show"><span class="td_overflow">Broadridge</span><i class="fa fa-binoculars"></i></div></td>
            <td><div><span>Web designer</span></div></td>
            <td><div>Full Time</div></td>
            <td><div>domain.com</div></td>
            <td><div>9000 000 000</div></td>
            <td><div><span title="Hyderabad, Hyderabad, Hyderabad" class="td_overflow">Hyderabad, Hyderabad, Hyderabad</span></div></td>
            <td><div>11/08/2016</div></td>
          </tr>
          <tr>
            <td><input type="checkbox" id="checkbox1"/></td>
            <td><div class="proj_show"><span class="td_overflow">Broadridge</span><i class="fa fa-binoculars"></i></div></td>
            <td><div><span>Web designer</span></div></td>
            <td><div>Full Time</div></td>
            <td><div>domain.com</div></td>
            <td><div>9000 000 000</div></td>
            <td><div><span title="Hyderabad, Hyderabad, Hyderabad" class="td_overflow">Hyderabad, Hyderabad, Hyderabad</span></div></td>
            <td><div>11/08/2016</div></td>
          </tr>
          <tr>
            <td><input type="checkbox" id="checkbox1"/></td>
            <td><div class="proj_show"><span class="td_overflow">Broadridge</span><i class="fa fa-binoculars"></i></div></td>
            <td><div><span>Web designer</span></div></td>
            <td><div>Full Time</div></td>
            <td><div>domain.com</div></td>
            <td><div>9000 000 000</div></td>
            <td><div><span title="Hyderabad, Hyderabad, Hyderabad" class="td_overflow">Hyderabad, Hyderabad, Hyderabad</span></div></td>
            <td><div>11/08/2016</div></td>
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
    
    <!-- Compnay details -->
    <div class="side_mask">
      <div class="proj_close"></div>
    </div>
    <div class="clients_side_wrap">
      <div class="clients_side_wrap_inn">
       <div class="profile_details clearfix" style="margin-bottom:20px;">
                    <div class="col-md-6">
                        <div class="media">
                          <div class="media-left image-parent">
                          <div class="imageParent">
                          	<img src="./images/24.jpg" alt="candidate" title="Candidate">
                          </div>
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
                      	<ul class="list-inline list-unstyled"><li>BA</li><li>BRS</li><li>SRS</li><li>Agile</li><li>SDLC</li></ul>
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
        <div class="proj_close"></div>
        <div class="project_side_wrap_inn_tabs">
          <ul class="project-tabs-menu">
            <li class="current"><a href="#tab-1">Profile</a></li>
            <li><a href="#tab-2">Resume</a></li>
            <li><a href="#tab-3">Experience</a></li>
            <li><a href="#tab-4">Education</a></li>
            <li><a href="#tab-5">Certificate</a></li>
            <li><a href="#tab-6">Activities</a></li>
            <div class="clearfix"></div>
          </ul>
          <div class="project_tabs">
            <div id="tab-1" class="project_tabs_inn">
<div class="require_wrap_details">
            	<h1>Summary:</h1>
                <textarea placeholder="Enter description">Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages.</textarea>
            	<h2>Skills:</h2>
                <textarea placeholder="Enter description">Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages.</textarea>               
            </div>
            </div>
            <div id="tab-2" class="project_tabs_inn">
              <div class="project_tabs_inn_head">
              	<a href="#" class="addUser-org"><i class="glyphicon glyphicon-plus"></i>Add Resume</a>
				<!-- Popup Div -->
                    <div class="collapse-div-adduser col-md-4"> <i class="fa fa-close close-div"></i>
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
                                      <input type="file" style="display: none;" multiple="" id="uploadBtn" class="upload">
                                      </span> </label>
                                    <input type="text" class="form-control" readonly="" id="uploadFile" placeholder="Files are not selected...">
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
            <div id="tab-3" class="project_tabs_inn">
              <div class="project_tabs_inn_head">
                  <a href="#" class="addUser-org">
                        <i class="glyphicon glyphicon-plus"></i>Add Experince
                  </a>
                  	<!-- Popup Div -->
                    <div class="collapse-div-adduser col-md-4"> <i class="fa fa-close close-div"></i>
                          <div class="form-horizontal clearfix">
                            <div class="clearfix mb10">
                              <div class="col-md-12">
                                <input type="text" class="form-control input-sm" value="" placeholder="Employer *">
                              </div>
                            </div>
                            <div class="clearfix mb10">
                              <div class="col-md-12 checkbox">
                                <label>
                                <input type="checkbox" name="Position" id="endDate">Is Current Position</label>
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
                                <span class="input-group-addon input-groupAddon hidden"><i class="fa fa-calendar"></i></span>
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
                      <th>Employer</th>
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
            
			<div id="tab-4" class="project_tabs_inn">
              <div class="project_tabs_inn_head addUser-org">
              	<a href="javascript:void(0)"><i class="glyphicon glyphicon-plus"></i>Add Education</a>
                <!-- Popup Div -->
                    <div class="collapse-div-adduser col-md-4">
                    <form role="form">
                    <i class="fa fa-close close-div"></i>
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
                                <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span>
                              </div>
                              <div class="col-md-6 positionRelative">
                                <input type="text" class="form-control input-sm" value="" placeholder="End Date" id="edu-endDate">
                                <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span>
                              </div>
                            </div>   
                            
                            <div class="clearfix mb10">
                              <div class="col-md-12">
                                <input type="text" class="form-control input-sm" value="" placeholder="Grade">
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
                          </form>
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
            
<div id="tab-5" class="project_tabs_inn">
              <div class="project_tabs_inn_head">
              	<a href="#" class="addUser-org"><i class="glyphicon glyphicon-plus"></i>Add Certification</a>
                <!-- Popup Div -->
                    <div class="collapse-div-adduser col-md-4"> <i class="fa fa-close close-div"></i>
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
                              <div class="col-md-12">
                                <input type="text" class="form-control input-sm" value="" placeholder="Score">
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
                                <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span>
                              </div>
                              <div class="col-md-6 positionRelative">
                                <input type="text" class="form-control input-sm" value="" placeholder="Validate To" id="validTo">
                                <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span>
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
            
<div id="tab-6" class="project_tabs_inn">
<div class="project_tabs_inn_head">
              <input class="vendorfild" type="text"><i class="fa fa-search"></i> </div>
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
    </div>
    <!-- Compnay details Ends --> 
    <!-- Project --> 
  </div>
</section>


<!--   Error Message   -->
<div class="vendor_wrap_mask"></div>
<div class="vendor_error">
  <p>Delete conformation message....</p>
  <textarea placeholder="Enter message"></textarea>
  <a class="close_color" href="javascript:void(0)">Yes</a><a href="javascript:void(0)">No</a> </div>

<!--   Error Message   --> 

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) --> 
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script> 
<script src="js/jquery-ui.min.js"></script> 
<script src="js/moment.min.js"></script> 
<script src="js/bootstrap-datetimepicker.min.js"></script> 
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script> 
<script src="js/masterHeaderAction.js"></script> 
<script src="js/chosen.jquery.min.js"></script> 
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
	
	
	// Levels adding script
$(".addUser-org").click(function(){
	 $(".collapse-div-adduser").toggleClass("show-div");
	 $(".collapse-div").removeClass("show-div");
 });
 $(".close-div").click(function(){
	 $(this).parent().toggleClass("show-div");
	 });
 $(".assign-org").click(function(){
	 $(".collapse-div").toggleClass("show-div");
	 $(".collapse-div-adduser").removeClass("show-div");
 });
 $(".addUser-org, .assign-org, .collapse-div-adduser, .collapse-div").click(function(e){
	 e.stopPropagation();
 });
 $('html').click(function(){
    $(".collapse-div-adduser").removeClass("show-div");
	$(".collapse-div").removeClass("show-div");
   });	
   $("#endDate").click(function(){
	   $(".endDate").toggle(200);
   });
});
</script>
<script>
  $(function() {
    $("#validFrom, #edu-endDate, #edu-startDate, #validTo").datepicker();
  } );
  </script>
  
	<div class="theme-color">
        <span class="icon-theme" title="click here"><i class="fa fa-gears"></i></span>
        <ul class="list-unstyled">
            <li><a href="#" class="" id="btn1"><span class="dark-blue"></span><span class="light-grey"></span></a></li>
            <li><a href="#" class="" id="btn2"><span class="flat-green"></span><span class="dark-purle"></span></a></li>
            <li><a href="#" class="" id="btn3"><span class="hdr-blue"></span><span class="sidenav-blue"></span></a></li>
        </ul>
	</div>  
  
</body>
</html>

<jsp:include page="../masterFooter.jsp" />