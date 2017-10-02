<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script type="text/javascript" src="https://www.amcharts.com/lib/3/amcharts.js"></script>
<script type="text/javascript" src="https://www.amcharts.com/lib/3/serial.js"></script>
<script type="text/javascript" src="https://www.amcharts.com/lib/3/pie.js"></script> 


<script type="text/javascript">
		 $(document).ready(function(){
			 getHolidaysList();
			});
</script>


 <!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Leave Management</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Leave Management</div>
    </section>
    <div class="timesheet_out">
      <div class="clients_side_wrap_inn no_pad">
        <div class="project_side_wrap_inn_tabs" id="new-id">
             <div class="pull-right">
                          <a href="<c:url value='/applyLeave'/>" title="Apply Leave" class="btn btn-primary btn-sm zindex">Apply Leave</a>
                      </div>
          <ul class="project-tabs-menu projecttabs-custom">
            <li class="current"><a href="#tab-1">Self</a></li>
            <li><a href="#tab-2">Employees</a></li>
            <div class="clearfix"></div>
          </ul>
           
          <div class="project_tabs projecttabs-custom"> 
            
            <!-- Details Tab -->
            <div id="tab-1" class="project_tabs_inn" style="display: block">
              <div class="clearfix">
                <div class="row">
                  <div class="clearfix"></div>
                  <div class="col-md-6">
                    <h4 class="leaves-heading">My Calendar</h4>
                    <div id="scheduleCalendar"></div>
                    <div class="clearfix"></div>
                    <ul class="scheduleAvailable">
                      <li><span class="bg-danger paddingRight"></span>Sick Leave</li>
                      <li><span class="bg-primary paddingRight"></span>Vacation</li>
                    </ul>
                      
                  </div>
                  <div class="col-md-6">
                    <h4 class="leaves-heading">My Time off balance</h4>
                    <div class="row time-balance">
                      <div class="col-md-3 mb">
                        <h4 class="text-center">Vacation</h4>
                        <div id="vacationDonut" class="amchartAnchor-Hide" style="width: 100%; height: 125px;"> </div>
                        <div class="text-center">
                          <h2>
                            <label class="small text-muted">Remaining:</label>
                            <label class="clearfix width100percnt mt5">120/80</label>
                          </h2>
                        </div>
                      </div>
                      <div class="col-md-3 mb">
                        <h4 class="text-center">Sick Leaves</h4>
                        <div id="sickDonut" class="amchartAnchor-Hide" style="width: 100%; height: 125px;"> </div>
                        <div class="text-center">
                          <h2>
                            <label class="small text-muted">Remaining:</label>
                            <label class="clearfix width100percnt mt5">120/80</label>
                          </h2>
                        </div>
                      </div>
                      <div class="col-md-3">
                        <h4 class="text-center">Casual Leaves</h4>
                        <div id="casualDonut" class="amchartAnchor-Hide" style="width: 100%; height: 125px;"></div>
                        <div class="text-center">
                          <h2>
                            <label class="small text-muted">Remaining:</label>
                            <label class="clearfix width100percnt mt5">120/80</label>
                          </h2>
                        </div>
                      </div>
                      <div class="col-md-3">
                        <h4 class="text-center">Other Leaves</h4>
                        <div id="otherDonut" class="amchartAnchor-Hide" style="width: 100%; height: 125px;"></div>
                        <div class="text-center">
                          <h2>
                            <label class="small text-muted">Remaining:</label>
                            <label class="clearfix width100percnt mt5">120/80</label>
                          </h2>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="clearfix"></div>
                    <div class="col-md-12 mt10"><hr></div>
                  <div class="col-md-10 col-md-offset-1 listof-holidays datatable_filter">
                        <div class="row">
                            <div class="col-md-5"><strong class="leaves-heading">List of Holidays</strong></div>
                            <div class="col-md-3 noPad dropdown-select">
                                <select class="form-control">
                                    <option selected>--Select Year--</option>
                                    <option>2020</option>
                                    <option>2019</option>
                                    <option>2018</option>
                                    <option>2017</option>
                                    <option>2016</option>
                                    <option>2015</option>
                                    <option>2014</option>
                                    <option>2013</option>
                                    <option>2012</option>
                                    <option>2011</option>
                                    <option>2010</option>
                                </select>
                            </div>
                            <div class="col-md-4 noPad">&nbsp</div>
                            </div>
                            <div id="holidayListDiv"></div>
                        
                    </div>
                  <div class="col-md-12 hidden">
                    <div class="clearfix project_wrap_table">
                      <table>
                        <thead>
                          <tr>
                            <th>Employee Name</th>
                            <th>Requirement</th>
                            <th>Client</th>
                            <th>Priority</th>
                            <th>Applicants</th>
                            <th>Action</th>
                          </tr>
                          <tr>
                            <td>
                              <div class="mt5 pull-left tbldata-dotdot-me">
                                <div class="media">
                                  <div class="media-left">
                                      <img src="images/userprofile-img.png" class="media-object" style="width:30px">
                                  </div>
                                  <div class="media-body text-left">
                                    <h5 class="media-heading tbldata-dotdot">Suresh Kumar Sahoo</h5>
                                    <p class="small text-muted">Lorem ipsum dolor sit amet, consectetur adipiscing elit</p>
                                  </div>
                                </div>
                              </div>
                            </td>
                            <td>
                              <div><span><a href="requirement-list.html" title="Click here">Java Deloper</a></span></div>
                            </td>
                            <td>
                              <div><span>ABC Infotech PVT. Ltd.</span></div>
                            </td>
                            <td>50%</td>
                            <td>
                              <div><span>Immediate</span></div>
                            </td>
                            <td>10</td>
                          </tr>
                        </thead>
                        <tbody>
                        </tbody>
                      </table>
                      <!-- Pagenation -->
                      
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div id="tab-2" class="project_tabs_inn" style="display: none">
                <div class="row clearfix">
                    <div class="clearfix mb5">
                        <div class="col-md-8">
                            <h4 class="leaves-heading"><span class="pull-left">Employee Leaves</span>
                            <div class="pull-right dropdown-select">
                                <select>
                                    <option selected>Department</option>
                                    <option>UI</option>
                                    <option>Developers</option>
                                    <option>HR Department</option>
                                    <option>Accounts</option>
                                </select>
                            </div>
                                </h4>
                        </div>
                        <div class="col-md-4">
                            <div class="form-inline leaves-heading">
                                <div class="pull-right">
                                <div class="form-group">
                            <input type="text" class="calendar form-control input-sm" id="leavesCalendar">
                                    </div>
                                <div class="form-group">
                            <button type="button" class="btn btn-primary btn-sm" title="Custom" onClick="customizeEmployee();">Custom</button>
                                </div>
                                    </div>
                                </div>
                                <div class="clearfix customizeEmployee" style="display: none;">
                                <div class="clearfix">
                                <div class="width150px mr2">
                                <input name="Date" placeholder="From" class="form-control calendar input-sm" type="text">
                                </div>
                                <div class="width150px mr2">
                                <input type="text" name="Date" placeholder="To" class="form-control calendar input-sm">
                                </div>
                                <button type="button" class="btn btn-primary btn-sm"><i class="fa fa-search"></i></button>
                                    </div>
                                </div>
                        </div>
                    </div>
                    <div class="clearfix ptagmrng-none">
                        <div class="col-md-3">
                      <div class="media media-custom mb">
                          <div class="media-left">
                              <div class="empimg-parent">
                                <img src="images/24.jpg" class="">
                              </div>
                          </div>
                          <div class="media-body text-left">
                            <h5 class="media-heading tbldata-dotdot">Suresh Kumar Sahoo</h5>
                            <p class="small text-muted">Java Developer</p>
                              <ul class="list-unstyled list-inline mrng0 mt5 empLeave-li">
                                  <li title="Date">S</li>
                                  <li title="Date">M</li>
                                  <li title="Date">T</li>
                                  <li title="Date" class="halfday">W</li>
                                  <li title="Date" class="fullday">T</li>
                                  <li title="Date">F</li>
                                  <li title="Date">S</li>
                              </ul>
                          </div>
                      </div>
                            </div>
                        <div class="col-md-3">
                        <div class="media media-custom mb">
                          <div class="media-left">
                              <div class="empimg-parent">
                                <img src="images/23.jpg" class="">
                              </div>
                          </div>
                          <div class="media-body text-left">
                            <h5 class="media-heading tbldata-dotdot">Suresh Kumar Sahoo</h5>
                            <p class="small text-muted">Java Developer</p>
<ul class="list-unstyled list-inline mrng0 mt5 empLeave-li">
                                  <li title="Date">S</li>
                                  <li title="Date">M</li>
                                  <li title="Date">T</li>
                                  <li title="Date" class="halfday">W</li>
                                  <li title="Date" class="fullday">T</li>
                                  <li title="Date">F</li>
                                  <li title="Date">S</li>
                              </ul>
                            </div>
                      </div>
                        </div>
                        <div class="col-md-3">
                        <div class="media media-custom mb">
                          <div class="media-left">
                              <div class="empimg-parent">
                                <img src="images/24.jpg" class="">
                              </div>
                          </div>
                          <div class="media-body text-left">
                            <h5 class="media-heading tbldata-dotdot">Suresh Kumar Sahoo</h5>
                            <p class="small text-muted">Java Developer</p>
<ul class="list-unstyled list-inline mrng0 mt5 empLeave-li">
                                  <li title="Date">S</li>
                                  <li title="Date">M</li>
                                  <li title="Date">T</li>
                                  <li title="Date" class="halfday">W</li>
                                  <li title="Date" class="fullday">T</li>
                                  <li title="Date">F</li>
                                  <li title="Date">S</li>
                              </ul>                          </div>
                      </div>
                        </div>
                        <div class="col-md-3">
                         <div class="media media-custom mb">
                          <div class="media-left">
                              <div class="empimg-parent">
                                <img src="images/23.jpg" class="">
                              </div>
                          </div>
                          <div class="media-body text-left">
                            <h5 class="media-heading tbldata-dotdot">Suresh Kumar Sahoo</h5>
                            <p class="small text-muted">Java Developer</p>
                             <ul class="list-unstyled list-inline mrng0 mt5 empLeave-li">
                                  <li title="Date">S</li>
                                  <li title="Date">M</li>
                                  <li title="Date">T</li>
                                  <li title="Date" class="halfday">W</li>
                                  <li title="Date" class="fullday">T</li>
                                  <li title="Date">F</li>
                                  <li title="Date">S</li>
                              </ul>
                             </div>
                      </div>
                        </div>
                        <div class="col-md-3">
                         <div class="media media-custom mb">
                          <div class="media-left">
                              <div class="empimg-parent">
                                <img src="images/23.jpg" class="">
                              </div>
                          </div>
                          <div class="media-body text-left">
                            <h5 class="media-heading tbldata-dotdot">Suresh Kumar Sahoo</h5>
                            <p class="small text-muted">Java Developer</p>
                              <ul class="list-unstyled list-inline mrng0 mt5 empLeave-li">
                                  <li title="Date">S</li>
                                  <li title="Date">M</li>
                                  <li title="Date">T</li>
                                  <li title="Date" class="halfday">W</li>
                                  <li title="Date" class="fullday">T</li>
                                  <li title="Date">F</li>
                                  <li title="Date">S</li>
                              </ul>
                             </div>
                      </div>
                        </div>
                        <div class="col-md-3">
                         <div class="media media-custom mb">
                          <div class="media-left">
                              <div class="empimg-parent">
                                <img src="images/23.jpg" class="">
                              </div>
                          </div>
                          <div class="media-body text-left">
                            <h5 class="media-heading tbldata-dotdot">Suresh Kumar Sahoo</h5>
                            <p class="small text-muted">Java Developer</p>
                              <ul class="list-unstyled list-inline mrng0 mt5 empLeave-li">
                                  <li title="Date">S</li>
                                  <li title="Date">M</li>
                                  <li title="Date">T</li>
                                  <li title="Date" class="halfday">W</li>
                                  <li title="Date" class="fullday">T</li>
                                  <li title="Date">F</li>
                                  <li title="Date">S</li>
                              </ul>
                          </div>
                      </div>
                        </div>
                      </div>
                </div>
                <div class="clearfix"><hr></div>
                <div class="row">
                  <div class="col-md-6">
                 
                 <div class="clearfix mt5">
                   <h4 class="heading col-md-4">Top leave Takers</h4>
                    <div class="timesheet_head clearfix col-md-8">
                          <div class="dropdown-select">
                            <div class="form-inline pull-right">
                            <div class="form-group">
                            <select class="form-control pull-right">
                              <option selected>Year</option>
                              <option>2010</option>
                              <option>2011</option>
                              <option>2012</option>
                              <option>2013</option>
                              <option>2014</option>
                              <option>2015</option>
                              <option>2016</option>
                              <option>2017</option>
                            </select>
    </div>
    <div class="form-group">
    <select class="form-control pull-right">
                              <option selected>Month</option>
                              <option>January</option>
                              <option>February</option>
                              <option>March</option>
                              <option>April</option>
                              <option>May</option>
                              <option>June</option>
                              <option>July</option>
                              <option>August</option>
                              <option>September</option>
                              <option>October</option>
                              <option>November</option>
                              <option>December</option>
                            </select>
    </div>
    <button type="submit" class="btn btn-primary btn-sm" id="customLeaves" onClick="showCustom()">Custom</button>
  </div>
<!-- Top Leave Takers Custom Date -->
    <div class="clearfix pull-lefet col-md-6 paddingRight customLeaves" style="display: none;">
    <div class="pull-left">
    <div class="pull-left mr5" style="width: 140px">
    <input value="" name="Date" id="datepicker4" placeholder="From" class="form-control calendar input-sm" type="text">
    </div>
    <div class="pull-left mr5" style="width: 140px">
    <input value="" name="Date" id="datepicker2" placeholder="To" class="form-control calendar input-sm" type="text">
    </div>
    <button type="button" class="btn btn-primary btn-sm"><i class="fa fa-search"></i></button>
    </div>
    </div>
<!-- Top Leave Takers Custom Date -->    
                        </div>
                        </div>
                 </div>
                     
                      <div class="clearfix project_wrap_table mt5">
                      <table>
                        <thead>
                          <tr>
                            <th>Employee Name</th>
                            <th>Days</th>
                            <th>Status</th>
                          </tr>
                          </thead>
                        <tbody>
                        <tr>
                            <td>
                              <div class="tbldata-dotdot-big" title="Santoshkumar AvaraDeloper">
                                  <a href="javascript:void(0)">Santoshkumar AvaraDeloper</a>
                              </div>
                            </td>
                            <td>
                              <div class="tbldata-dotdot-100">2Weeks</div>
                            </td>
                              <td><div class="tblminwidth-75">Approved</div></td>
                          </tr>
                        <tr>
                            <td>
                              <div class="tbldata-dotdot-big" title="Santoshkumar AvaraDeloper">
                                  <a href="javascript:void(0)">Santoshkumar AvaraDeloper</a>
                              </div>
                            </td>
                            <td>
                              <div class="tbldata-dotdot-100">2Weeks</div>
                            </td>
                              <td><div class="tblminwidth-75">Approved</div></td>
                          </tr>
                        <tr>
                            <td>
                              <div class="tbldata-dotdot-big" title="Santoshkumar AvaraDeloper">
                                  <a href="javascript:void(0)">Santoshkumar AvaraDeloper</a>
                              </div>
                            </td>
                            <td>
                              <div class="tbldata-dotdot-100">2Weeks</div>
                            </td>
                              <td><div class="tblminwidth-75">Approved</div></td>
                          </tr>
                        <tr>
                            <td>
                              <div class="tbldata-dotdot-big" title="Santoshkumar AvaraDeloper">
                                  <a href="javascript:void(0)">Santoshkumar AvaraDeloper</a>
                              </div>
                            </td>
                            <td>
                              <div class="tbldata-dotdot-100">2Weeks</div>
                            </td>
                              <td><div class="tblminwidth-75">Approved</div></td>
                          </tr>
                        <tr>
                            <td>
                              <div class="tbldata-dotdot-big" title="Santoshkumar AvaraDeloper">
                                  <a href="javascript:void(0)">Santoshkumar AvaraDeloper</a>
                              </div>
                            </td>
                            <td>
                              <div class="tbldata-dotdot-100">2Weeks</div>
                            </td>
                              <td><div class="tblminwidth-75">Approved</div></td>
                          </tr>
                        <tr>
                            <td>
                              <div class="tbldata-dotdot-big" title="Santoshkumar AvaraDeloper">
                                  <a href="javascript:void(0)">Santoshkumar AvaraDeloper</a>
                              </div>
                            </td>
                            <td>
                              <div class="tbldata-dotdot-100">2Weeks</div>
                            </td>
                              <td><div class="tblminwidth-75">Approved</div></td>
                          </tr>
                        <tr>
                            <td>
                              <div class="tbldata-dotdot-big" title="Santoshkumar AvaraDeloper">
                                  <a href="javascript:void(0)">Santoshkumar AvaraDeloper</a>
                              </div>
                            </td>
                            <td>
                              <div class="tbldata-dotdot-100">2Weeks</div>
                            </td>
                              <td><div class="tblminwidth-75">Approved</div></td>
                          </tr>
                        <tr>
                            <td>
                              <div class="tbldata-dotdot-big" title="Santoshkumar AvaraDeloper">
                                  <a href="javascript:void(0)">Santoshkumar AvaraDeloper</a>
                              </div>
                            </td>
                            <td>
                              <div class="tbldata-dotdot-100">2Weeks</div>
                            </td>
                              <td><div class="tblminwidth-75">Approved</div></td>
                          </tr>
                        <tr>
                            <td>
                              <div class="tbldata-dotdot-big" title="Santoshkumar AvaraDeloper">
                                  <a href="javascript:void(0)">Santoshkumar AvaraDeloper</a>
                              </div>
                            </td>
                            <td>
                              <div class="tbldata-dotdot-100">2Weeks</div>
                            </td>
                              <td><div class="tblminwidth-75">Approved</div></td>
                          </tr>
                        <tr>
                            <td>
                              <div class="tbldata-dotdot-big" title="Santoshkumar AvaraDeloper">
                                  <a href="javascript:void(0)">Santoshkumar AvaraDeloper</a>
                              </div>
                            </td>
                            <td>
                              <div class="tbldata-dotdot-100">2Weeks</div>
                            </td>
                              <td><div class="tblminwidth-75">Approved</div></td>
                          </tr>
                        </tbody>
                      </table>
                      <!-- Pagenation -->
                      <nav class="clearfix">
                        <ul id="paging22" class="pagination pull-right">
                          <li><a href="#">Previous</a></li>
                          <li class="active"><a href="#">1</a></li>
                          <li><a href="#">2</a></li>
                          <li><a href="#">3</a></li>
                          <li><a href="#">4</a></li>
                          <li><a href="#">5</a></li>
                          <li data-page="last"><a href="#">Next</a></li>
                        </ul>
                      </nav>
                    </div>
                  </div>

                  <div class="col-md-6">
                    <div class="clearfix mt5">
                   <h4 class="heading col-md-4">Employee Leave Report</h4>
                    <div class="timesheet_head clearfix col-md-8">
                          <div class="dropdown-select">
                            <div class="form-inline pull-right">
                            <div class="form-group">
                            <select class="form-control pull-right">
                              <option selected>Year</option>
                              <option>2010</option>
                              <option>2011</option>
                              <option>2012</option>
                              <option>2013</option>
                              <option>2014</option>
                              <option>2015</option>
                              <option>2016</option>
                              <option>2017</option>
                            </select>
                            </div>
                            <div class="form-group">
                            <select class="form-control pull-right">
                              <option selected>Month</option>
                              <option>January</option>
                              <option>February</option>
                              <option>March</option>
                              <option>April</option>
                              <option>May</option>
                              <option>June</option>
                              <option>July</option>
                              <option>August</option>
                              <option>September</option>
                              <option>October</option>
                              <option>November</option>
                              <option>December</option>
                            </select>
    </div>
    <button type="submit" class="btn btn-primary btn-sm" id="showLeaveReport" onClick="showLeaveReport()">Custom</button>
  </div>
<!-- Top Leave Takers Custom Date -->
    <div class="clearfix pull-lefet col-md-6 paddingRight showLeaveReport" style="display: none;">
    <div class="pull-left">
    <div class="pull-left mr5" style="width: 140px">
    <input value="" name="Date" id="datepicker4" placeholder="From" class="form-control calendar input-sm" type="text">
    </div>
    <div class="pull-left mr5" style="width: 140px">
    <input value="" name="Date" id="datepicker2" placeholder="To" class="form-control calendar input-sm" type="text">
    </div>
    <button type="button" class="btn btn-primary btn-sm"><i class="fa fa-search"></i></button>
    </div>
    </div>
<!-- Top Leave Takers Custom Date -->    
                        </div>
                        </div>
                 </div>
                      <div id="empLeavereport" class="amchartAnchor-Hide mt5" style="width: 100%; height: 250px;"></div>
                  
                    </div>
                    </div>
                <!-- Recent Request -->
                
                <div class="clearfix">
                    <hr>
                    <h4 class="leaves-heading">Recent Requestes</h4>
                    <div class="clearfix project_wrap_table">
                      <table>
        <thead>
          <tr>
            <th>Employee Name</th>
            <th>Leave Type</th>
            <th>Duration</th>
            <th>No of Days</th>
            <th>Leaves Taken</th>
            <th>This Month</th>
            <th>Balance Leaves</th>
            <th>Action </th>
          </tr>
        </thead>
        <tbody>
        <tr>
            <td>
              <div class="proj_show tbldata-dotdot-me">
                  <div class="pull-left tbldata-dotdot" title="BIS10041">Anandkumar Joshimanohar</div>
                  <i class="fa fa-binoculars pull-right"></i>
              </div>
            </td>
            <td>
                <div title="2days" class="tblminwidth-90">
                    Sick Leave
                </div>
            </td>
            
            <td>
                <div title="Duration" class="tblminwidth-75">
                   <div class="width100percnt">
                        09/10/2017
                    </div>
                    <div class="width100percnt mt2">
                        09/12/2017
                    </div>
                </div>
            </td>
            <td>
              <div title="2 days" class="tblminwidth-75">
                    2 days
                </div>
            </td>
            <td>
              <div title="No" class="tblminwidth-100">
                    20
                </div>
            </td>
            <td>
              <div title="No" class="tbldata-dotdot-100">
                10
                </div>
            </td>
            <td>
              <div title="No" class="tbldata-dotdot-100">
                    10
                </div>
            </td>
            <td>
                <div class="tblminwidth-75">
                    <div title="Accept" class="fa fa-check text-success cursorPointer tblminwidth-30"></div>
                    <div title="Reject" class="fa fa-times text-danger cursorPointer tblminwidth-30"></div>
                </div>
            </td>
          </tr>
          </tbody>
      </table>
                      <!-- Pagenation -->
                      <nav class="clearfix">
                        <ul id="paging" class="pagination pull-right">
                          <li><a href="#">Previous</a></li>
                          <li class="active"><a href="#">1</a></li>
                          <li><a href="#">2</a></li>
                          <li><a href="#">3</a></li>
                          <li><a href="#">4</a></li>
                          <li><a href="#">5</a></li>
                          <li data-page="last"><a href="#">Next</a></li>
                        </ul>
                      </nav>
                    </div>
                  </div>
                <!-- Recent Request -->
              </div>
              
          </div>
        </div>
      </div>
      
      <!-- Project --> 
    </div>
  </section>


<script type="text/javascript">
    //Chosen script
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
$(document).ready(function() {
$("#tab-1").show();
    $(".project-tabs-menu a").click(function(event) {
        event.preventDefault();
        $(this).parent().addClass("current");
        $(this).parent().siblings().removeClass("current");
        var tab = $(this).attr("href");
        $(".project_tabs_inn").not(tab).css("display", "none");
        $(tab).fadeIn();
    });
});
    // jQuery Ui Calendar
    $(function() {
    $( "#scheduleCalendar" ).datepicker({
	  changeMonth: true,
      changeYear: true,
        onSelect : function(selected) {
            var tet = $("<div></div>").text("Today No Events").appendTo("#ui-datepicker-div")
        }
    });
  });
</script> 


<script>
function getHolidaysList(){
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	var userId = '${SourceLeadWeb_userId}';
	$.get(sourceleadUrl+"/getHolidaysList?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&userId="+userId+"", function(data) {
		var result = "";				        	
				result = result + '<table id="viewHolidaysTable" class="display table table-bordered" cellspacing="0" width="100%" >';
				result = result + '<thead>';
				result = result + '<tr>';
				result = result +'<th>S.No</th>';
				result = result +'<th>Name</th>';
				result = result +'<th>Date</th>';
				result = result + '<th>Day of the Week</th>';
				result = result + '</tr>';
				result = result + '</thead>	';
				result = result + '<tbody>';
				if(data !=null && data !=''){
					var j = 1;
					 for (var i=0; i<data.length; i++)
						{
			        		 var opt = data[i];
			        		 result = result + ' <tr class="text-danger">';
			        		 result = result + ' <td>'+j+'</td>';
			        		 if(opt.holidayName !=null && opt.holidayName !='' && opt.holidayName !='undefined'){
			        			 result = result + '<td title="'+opt.holidayName+'">'+opt.holidayName+'</td>'; 
			        		 }else{
			        			 result = result + '<td>---</td>';
			        		 }
			        		 result = result + ' <td title="'+opt.fromDate+'">'+opt.fromDate+'</td>';
			        		 result = result + ' <td title="'+opt.dayOfTheWeek+'">'+opt.dayOfTheWeek+'</td>';
			        		 result = result + '  </tr>';
			        		 j++;
						}
				}
	        	
				result = result + '  </tbody>';
				result = result + ' </table>';
				$("#holidayListDiv").empty();
				$("#holidayListDiv").append(result);
				viewHolidaysTable();
		}); 
}
</script>

<script>
function viewHolidaysTable(){ 
    $('#viewHolidaysTable').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    });
}
</script>


<script>
    /*------My Timeoff Balance Piecharts------*/
    //vacationDonut
        AmCharts.makeChart("vacationDonut",
				{
					"type": "pie",
					"balloonText": "[[title]]<br><span style='font-size:14px'><b>[[value]]</b></span>",
                    //"balloonText": "",
					"innerRadius": "60%",
                    "minRadius":0,
                    "pieY": "50%",
					"pullOutRadius": 0,
					"radius": "45%",
					"startAngle": 0,
					"colors": [
						"#337ab7",
						"#ddd"
					],
					"labelsEnabled": false,
					"marginBottom": 0,
					"marginTop": 0,
					"titleField": "category",
					"valueField": "column-1",
					"allLabels": [],
					"balloon": {
						"fixedPosition": false
					},
					"titles": [],
					"dataProvider": [
						{
							"category": "Vacation",
							"column-1": "80"
						},
						{
							"category": "Pending Leaves",
							"column-1": "20"
						}
					]
				}
			);
        //sickDonut
        AmCharts.makeChart("sickDonut",
				{
					"type": "pie",
					"balloonText": "[[title]]<br><span style='font-size:14px'><b>[[value]]</b></span>",
                    //"balloonText": "",
					"innerRadius": "60%",
                    "minRadius":0,
                    "pieY": "50%",
					"pullOutRadius": 0,
					"radius": "45%",
					"startAngle": 0,
					"colors": [
						"#e53935",
						"#ddd"
					],
					"labelsEnabled": false,
					"marginBottom": 0,
					"marginTop": 0,
					"titleField": "category",
					"valueField": "column-1",
					"allLabels": [],
					"balloon": {
						"fixedPosition": false
					},
					"titles": [],
					"dataProvider": [
						{
							"category": "Sick Leaves",
							"column-1": "80"
						},
						{
							"category": "Pending Leaves",
							"column-1": "20"
						}
					]
				}
			);
        //casualDonut
                AmCharts.makeChart("casualDonut",
				{
					"type": "pie",
					"balloonText": "[[title]]<br><span style='font-size:14px'><b>[[value]]</b></span>",
                    //"balloonText": "",
					"innerRadius": "60%",
                    "minRadius":0,
                    "pieY": "50%",
					"pullOutRadius": 0,
					"radius": "45%",
					"startAngle": 0,
					"colors": [
						"#ffa726",
						"#ddd"
					],
					"labelsEnabled": false,
					"marginBottom": 0,
					"marginTop": 0,
					"titleField": "category",
					"valueField": "column-1",
					"allLabels": [],
					"balloon": {
						"fixedPosition": false
					},
					"titles": [],
					"dataProvider": [
						{
							"category": "Casual Leaves",
							"column-1": "80"
						},
						{
							"category": "Pending Leaves",
							"column-1": "20"
						}
					]
				}
			);
                        AmCharts.makeChart("otherDonut",
				{
					"type": "pie",
					"balloonText": "[[title]]<br><span style='font-size:14px'><b>[[value]]</b></span>",
                    //"balloonText": "",
					"innerRadius": "60%",
                    "minRadius":0,
                    "pieY": "50%",
					"pullOutRadius": 0,
					"radius": "45%",
					"startAngle": 0,
					"colors": [
						"#5c6bc0",
						"#ddd"
					],
					"labelsEnabled": false,
					"marginBottom": 0,
					"marginTop": 0,
					"titleField": "category",
					"valueField": "column-1",
					"allLabels": [],
					"balloon": {
						"fixedPosition": false
					},
					"titles": [],
					"dataProvider": [
						{
							"category": "Other Leaves",
							"column-1": "80"
						},
						{
							"category": "Pending Leaves",
							"column-1": "20"
						}
					]
				}
			);
    //Custom buttons
    function showCustom(){
        $(".customLeaves").toggle();
    }
    function showLeaveReport(){
        $(".showLeaveReport").toggle();
    }
    //jQuery UI Calendar
    $("#leavesCalendar").datepicker();
    </script>
    
		<script>
                //Employee LeaveReport Amchart
			AmCharts.makeChart("empLeavereport",
				{
					"type": "serial",
					"categoryField": "category",
					"columnSpacing": 20,
					"columnWidth": 0.5,
					"autoMarginOffset": 20,
					"marginBottom": 5,
					"marginLeft": 0,
					"marginRight": 5,
					"marginTop": 10,
					"minMarginBottom": 0,
					"minMarginLeft": 0,
					"minMarginRight": 0,
					"minMarginTop": 0,
					"startDuration": 1,
					"autoDisplay": true,
					"touchClickDuration": 10,
					"categoryAxis": {
						"gridPosition": "start",
						"titleFontSize": 0
					},
					"trendLines": [],
					"graphs": [
						{
							"balloonText": "[[category]] [[value]]",
							"colorField": "color",
							"fillAlphas": 1,
							"id": "AmGraph-1",
							"lineColorField": "color",
							"title": "graph 1",
							"type": "column",
							"valueField": "column-1"
						}
					],
					"guides": [],
					"valueAxes": [],
					"allLabels": [],
					"balloon": {
						"offsetY": 4,
						"pointerWidth": 10,
						"verticalPadding": 5
					},
					"titles": [],
					"dataProvider": [
						{
							"category": "Annual Leave",
							"column-1": "12",
							"color": "#64b5f6"
						},
						{
							"category": "Sick Leave",
							"column-1": "6",
							"color": "#ef5350"
						},
						{
							"category": "Casual Leave",
							"column-1": "12",
							"color": "#66bb6a "
						},
						{
							"category": "Maternity Leave",
							"column-1": "6",
							"color": "#3949ab"
						},
						{
							"category": "Personal Leave",
							"column-1": "8",
							"color": "#ffa726"
						}
					]
				}
			);
		</script>
    <script>
    function customizeEmployee(){
        $(".customizeEmployee").fadeToggle();
    }
    </script>


<jsp:include page="../masterFooter.jsp" />