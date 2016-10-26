<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<%-- #######################################################################################################
 <!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
    	 <c:if test="${not empty msg}">
	        <div class="success_pop" id="selector"  style="display: block;">
		         <i class="fa fa-check-circle"></i><span>${msg}</span>
			</div>
	     </c:if>
      <div class="pull-left">
        <h2>Dashboard</h2>
      </div>
    </section>
    <div class="content-dashboard clearfix">
      <section class="clearfix section-action-btns hidden">
        <ul class="pull-right list-unstyled list-inline">
          <li><i class="fa fa-filter" aria-hidden="true"></i>&nbsp;Filers&nbsp;</a>
			<select>
			<option>All</option>
				<option>This Week</option>
				<option>Month</option>
				<option>Year</option>
			</select>
		  </li>
        </ul>
	</section>
        <div class="clearfix row db-widget-sm">

		<!-- /.info-box Ends -->
		<div class="col-md-3 col-sm-6 col-xs-12">
		<div class="info-box">
            <span class="info-box-icon bg-orange">
				<i class="fa fa-floppy-o" aria-hidden="true"></i>
			</span>
            <div class="info-box-content text-center">
              <p class="info-box-text"><a href="/sourcelead/totalTimesheetList/NEW" >Saved</a></p>
              <p class="info-box-number"><span id="savedTimesheetsSubmissionsCount"></span></p>
            </div>
          </div>
        </div>
          <!-- /.info-box Ends -->

		<!-- /.info-box Ends -->
		<div class="col-md-3 col-sm-6 col-xs-12">
		<div class="info-box">
            <span class="info-box-icon bg-blue">
				<i class="fa fa-clock-o" aria-hidden="true"></i>
			</span>
            <div class="info-box-content text-center">
              <p class="info-box-text"><a href="/sourcelead/totalTimesheetList/SUBMITTED" >Waiting</a></p>
              <p class="info-box-number"><span  id="waitingTimesheetsSubmissionsCount"></span></p>
            </div>
          </div>
        </div>
          <!-- /.info-box Ends -->

		<!-- /.info-box Ends -->
		<div class="col-md-3 col-sm-6 col-xs-12">
		<div class="info-box">
            <span class="info-box-icon bg-green">
				<i class="fa fa-check-circle-o" aria-hidden="true"></i>
			</span>
            <div class="info-box-content text-center">
              <p class="info-box-text"><a href="/sourcelead/totalTimesheetList/APPROVED" >Approved</a></p>
              <p class="info-box-number"><span id="approvalTimesheetsSubmissionsCount"></span></p>
            </div>
          </div>
        </div>
          <!-- /.info-box Ends -->

		<!-- /.info-box Ends -->
		<div class="col-md-3 col-sm-6 col-xs-12">
		<div class="info-box">
            <span class="info-box-icon bg-red">
				<i class="fa fa-ban" aria-hidden="true"></i>
			</span>
            <div class="info-box-content text-center">
              <p class="info-box-text"><a href="/sourcelead/totalTimesheetList/REJECTED" >Rejected</a></p>
              <p class="info-box-number"><span id="rejectedTimesheetsSubmissionsCount"></span></p>
            </div>
          </div>
        </div>
          <!-- /.info-box Ends -->
		  
		</div>
		
		<!-- Pie chart -->
			<!-- <div class="clearfix row">
				<section class="clearfix">
				 <div class="dropdown col-xs-2">
					<button class="btn btn-primary dropdown-toggle  btn-block" type="button" data-toggle="dropdown">Select Client
					&nbsp;&nbsp;<span class="caret"></span></button>
					<ul class="dropdown-menu">
					  <li><a href="#">Client</a></li>
					  <li><a href="#">Client</a></li>
					  <li><a href="#">Client</a></li>
					</ul>
				  </div>
				  
				 
				  <div class="dropdown col-xs-2">
					<button class="btn btn-success dropdown-toggle  btn-block" type="button" data-toggle="dropdown">Select Projects
					&nbsp;&nbsp;<span class="caret"></span></button>
					<ul class="dropdown-menu">
					  <li><a href="#">Project-1</a></li>
					  <li><a href="#">Project-2</a></li>
					  <li><a href="#">Project-3</a></li>
					</ul>
				  </div>
				</section>
				Highchart
				<div class="col-md-12">
				<section class="clearfix piechart-area mt">
						<h3>Timesheet Highchart</h3>
				</section></div>
				Highchart Ends
			</div> -->
				
				
			</div>
		<!-- Pie chart -->
  </section>
  <!-- Content Area Ends-->
  ######################################################################################################## --%>


  <!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Dashboard</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Dashboard</div>
    </section>
    <!-- Assigned Requirements Row -->
    <div class="content-dashboard clearfix"> 
      <!-- Assigned Requirements -->
      <div class="clearfix dashboard-widget mb col-md-12">
        <h4 class="pull-left">
        Assigned Requirements
        &nbsp;&nbsp;<span class="pull-right" title="Calendar"><i aria-hidden="true" class="fa fa-calendar" id="datepicker"></i>
        </span>
        </h4>
      <a href="#" class="collpase-div pull-right" title="SlideUp/SlideDown"><i aria-hidden="true" class="fa fa-angle-down"></i></a>
        <div class="clearfix require_wrap_table dashboard-data">
          <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>Requirement</th>
                <th>Client</th>
                <th>Rate</th>
                <th>Priority</th>
                <th>Applicants</th>
                <th>Shortlisted</th>
                <th>Submited</th>
                <th>Interviewd</th>
                <th>Offered</th>
                <th>Action</th>
              </tr>
              <tr>
                <td>001</td>
                <td><div><span><a href="requirement-list.html" title="Click here">Java Deloper</a></span></div></td>
                <td><div><span>ABC Infotech PVT. Ltd.</span></div></td>
                <td>50%</td>
                <td><div><span>Immediate</span></div></td>
                <td>10</td>
                <td>5</td>
                <td>20</td>
                <td>20</td>
                <td>8</td>
                <td><div><a href="javascript:void(0)"><i class="fa fa-bullhorn"></i></a></div></td>
              </tr>
              <tr>
                <td>001</td>
                <td><div><span><a href="requirement-list.html" title="Click here">Java Deloper</a></span></div></td>
                <td><div><span>ABC Infotech PVT. Ltd.</span></div></td>
                <td>50%</td>
                <td><div><span>Immediate</span></div></td>
                <td>10</td>
                <td>5</td>
                <td>20</td>
                <td>20</td>
                <td>8</td>
                <td><div><a href="javascript:void(0)"><i class="fa fa-bullhorn"></i></a></div></td>
              </tr>
              <tr>
                <td>001</td>
                <td><div><span><a href="requirement-list.html" title="Click here">Java Deloper</a></span></div></td>
                <td><div><span>ABC Infotech PVT. Ltd.</span></div></td>
                <td>50%</td>
                <td><div><span>Immediate</span></div></td>
                <td>10</td>
                <td>5</td>
                <td>20</td>
                <td>20</td>
                <td>8</td>
                <td><div><a href="javascript:void(0)"><i class="fa fa-bullhorn"></i></a></div></td>
              </tr>
              <tr>
                <td>001</td>
                <td><div><span><a href="requirement-list.html" title="Click here">Java Deloper</a></span></div></td>
                <td><div><span>ABC Infotech PVT. Ltd.</span></div></td>
                <td>50%</td>
                <td><div><span>Immediate</span></div></td>
                <td>10</td>
                <td>5</td>
                <td>20</td>
                <td>20</td>
                <td>8</td>
                <td><div><a href="javascript:void(0)"><i class="fa fa-bullhorn"></i></a></div></td>
              </tr>
              <tr>
                <td>001</td>
                <td><div><span><a href="requirement-list.html" title="Click here">Java Deloper</a></span></div></td>
                <td><div><span>ABC Infotech PVT. Ltd.</span></div></td>
                <td>50%</td>
                <td><div><span>Immediate</span></div></td>
                <td>10</td>
                <td>5</td>
                <td>20</td>
                <td>20</td>
                <td>8</td>
                <td><div><a href="javascript:void(0)"><i class="fa fa-bullhorn"></i></a></div></td>
              </tr>
            </thead>
            <tbody>
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
      <!-- Pagenation Ends --> 
    </div>
    <!-- Assigned Requirements Row Ends  -->

   <!-- Requirement Approvals and Interview Row --> 
    <div class="clearfix row mb"> 
      <div class="col-md-6 mb">
        <div class="clients_wrap_table dashboard-widget">
          <h4 class="pull-left">Requirement Approvals &nbsp;&nbsp;<a href="#" class="pull-right" title="Calendar"><i aria-hidden="true" class="fa fa-calendar"></i></a></h4>
          <a href="javascript:void(0)" class="collpase-div pull-right" title="SlideUp/SlideDown"><i aria-hidden="true" class="fa fa-angle-down"></i></a>
          <div class="clearfix require_wrap_table dashboard-data">
            <table>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Requirement</th>
                  <th>Rate</th>
                  <th>Exp.</th>
                  <th>Action</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>002</td>
                  <td><div class="tbldata-dotdot">Requirement</div></td>
                  <td><div class="tblminwidth-75">5lakhs</div></td>
                  <td><div><span>5yrs</span></div></td>
                  <td><div class="tblminwidth-50">Primary</div></td>
                </tr>
                <tr>
                  <td>002</td>
                  <td><div class="tbldata-dotdot">Requirement</div></td>
                  <td><div class="tblminwidth-75">5lakhs</div></td>
                  <td><div><span>5yrs</span></div></td>
                  <td><div class="tblminwidth-50">Primary</div></td>
                </tr>
                <tr>
                  <td>002</td>
                  <td><div class="tbldata-dotdot">Requirement</div></td>
                  <td><div class="tblminwidth-75">5lakhs</div></td>
                  <td><div><span>5yrs</span></div></td>
                  <td><div class="tblminwidth-50">Primary</div></td>
                </tr>
                <tr>
                  <td>002</td>
                  <td><div class="tbldata-dotdot">Requirement</div></td>
                  <td><div class="tblminwidth-75">5lakhs</div></td>
                  <td><div><span>5yrs</span></div></td>
                  <td><div class="tblminwidth-50">Primary</div></td>
                </tr>
                
              </tbody>
            </table>
            
            <!-- Pagenation -->
            <nav class="clearfix">
              <ul id="paginbg" class="pagination pull-right">
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
        <!-- Pagenation Ends --> 
      </div>
      <!-- Requirement Approvals Ends --> 
      
      <!-- Inteviews -->
      <div class="col-md-6">
        <div class="clients_wrap_table dashboard-widget">
          <h4 class="pull-left">Interviews &nbsp;&nbsp;<a href="#" class="pull-right" title="Calendar"><i aria-hidden="true" class="fa fa-calendar"></i></a></h4>
          <a href="javascript:void(0)" class="collpase-div pull-right" title="SlideUp/SlideDown"><i aria-hidden="true" class="fa fa-angle-down"></i></a>
          <div class="clearfix require_wrap_table dashboard-data">
            <ul class="nav nav-tabs">
              <li class="active"><a data-toggle="tab" href="#today">Today</a></li>
              <li><a data-toggle="tab" href="#upcoming">Upcoming</a></li>
              <li><a data-toggle="tab" href="#previous">Previous</a></li>
            </ul>
            <div class="tab-content clearfix mt">
              <div id="today" class="tab-pane fade in active">
           <table>
              <thead>
                <tr>
                  <th>Applicant</th>
                  <th>Requirement</th>
                  <th>Date &amp; Time</th>
                  <th>Client</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td><div><span>Srinivas Subharamanyam Sahu</span></div></td>
                  <td><div class="tbldata-dotdot-vsm">Java Devloper</div></td>
                  <td><div><span>26/10/2016 <br/> 10:15PM</span></div></td>
                  <td><div><span>TCS Consultancy Services, HYD</span></div></td>
                </tr>
                <tr>
                  <td><div><span>Srinivas Subharamanyam Sahu</span></div></td>
                  <td>Java Devloper</td>
                  <td><div><span>26/10/2016 <br/> 10:15PM</span></div></td>
                 <td><div><span>TCS Consultancy Services, HYD</span></div></td>
                </tr>
                <tr>
                  <td><div><span>Srinivas Subharamanyam Sahu</span></div></td>
                  <td>Java Devloper</td>
                  <td><div><span>26/10/2016 <br/> 10:15PM</span></div></td>
                  <td><div><span>TCS Consultancy Services, HYD</span></div></td>
                </tr>
              </tbody>
            </table>
            
            <!-- Pagenation -->
            <nav class="clearfix">
              <ul id="pagincg" class="pagination pull-right">
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
              <div id="upcoming" class="tab-pane fade">
           <table>
              <thead>
                <tr>
                  <th>Applicant</th>
                  <th>Requirement</th>
                  <th>Date &amp; Time</th>
                  <th>Client</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td><div><span>Srinivas Subharamanyam Sahu</span></div></td>
                  <td><div class="tbldata-dotdot-vsm">Java Devloper</div></td>
                  <td><div><span>26/10/2016 <br/> 10:15PM</span></div></td>
                  <td><div><span>TCS Consultancy Services, HYD</span></div></td>
                </tr>
                <tr>
                  <td><div><span>Srinivas Subharamanyam Sahu</span></div></td>
                  <td>Java Devloper</td>
                  <td><div><span>26/10/2016 <br/> 10:15PM</span></div></td>
                 <td><div><span>TCS Consultancy Services, HYD</span></div></td>
                </tr>
                <tr>
                  <td><div><span>Srinivas Subharamanyam Sahu</span></div></td>
                  <td>Java Devloper</td>
                  <td><div><span>26/10/2016 <br/> 10:15PM</span></div></td>
                  <td><div><span>TCS Consultancy Services, HYD</span></div></td>
                </tr>
              </tbody>
            </table>
            
            <!-- Pagenation -->
            <nav class="clearfix">
              <ul id="pagincg" class="pagination pull-right">
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
                            <div id="previous" class="tab-pane fade">
           <table>
              <thead>
                <tr>
                  <th>Applicant</th>
                  <th>Requirement</th>
                  <th>Date &amp; Time</th>
                  <th>Client</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td><div><span>Srinivas Subharamanyam Sahu</span></div></td>
                  <td><div class="tbldata-dotdot-vsm">Java Devloper</div></td>
                  <td><div><span>26/10/2016 <br/> 10:15PM</span></div></td>
                  <td><div><span>TCS Consultancy Services, HYD</span></div></td>
                </tr>
                <tr>
                  <td><div><span>Srinivas Subharamanyam Sahu</span></div></td>
                  <td>Java Devloper</td>
                  <td><div><span>26/10/2016 <br/> 10:15PM</span></div></td>
                 <td><div><span>TCS Consultancy Services, HYD</span></div></td>
                </tr>
                <tr>
                  <td><div><span>Srinivas Subharamanyam Sahu</span></div></td>
                  <td>Java Devloper</td>
                  <td><div><span>26/10/2016 <br/> 10:15PM</span></div></td>
                  <td><div><span>TCS Consultancy Services, HYD</span></div></td>
                </tr>
              </tbody>
            </table>
            
            <!-- Pagenation -->
            <nav class="clearfix">
              <ul id="pagincg" class="pagination pull-right">
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
          </div>
        </div>
        <!-- Pagenation Ends --> 
      </div>
      <!-- Inteviews Ends --> 
    </div>
    <!-- Requirement Approvals and Interview Row ends --> 
   
    <!-- Tasks and and Row -->
    <div class="clearfix row mb"> 
      
      <div class="col-md-6 mb">
        <div class="clients_wrap_table dashboard-widget ">
          <h4 class="pull-left">Tasks &nbsp;&nbsp;<a href="#" class="pull-right" title="Calendar"><i aria-hidden="true" class="fa fa-calendar"></i></a></h4>
          <a href="javascript:void(0)" class="collpase-div pull-right" title="SlideUp/SlideDown"><i aria-hidden="true" class="fa fa-angle-down"></i></a>
          <div class="clearfix require_wrap_table dashboard-data">
            <table>
              <thead>
                <tr>
                  <th>Subject</th>
                  <th>Date &amp; Time</th>
                  <th>Priority</th>
                  <th>Status</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td><div><span>Java</span></div></td>
                  <td><div><span>26/09/2016 3.30 pm.</span></div></td>
                  <td>Immediate</td>
                  <td>Process</td>
                </tr>
                <tr>
                  <td>Java</td>
                  <td>26/09/2016 3.30 pm.</td>
                  <td>Immediate</td>
                  <td>Process</td>
                </tr>
                <tr>
				<td>Java</td>
                  <td>26/09/2016 3.30 pm.</td>
                  <td>Immediate</td>
                  <td>Process</td>
                </tr>
              </tbody>
            </table>
            
            <!-- Pagenation -->
            <nav class="clearfix">
              <ul id="paginog" class="pagination pull-right">
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
        <!-- Pagenation Ends --> 
      </div>
      <!-- Tasks Ends --> 
      <!-- Timesheets -->
      <div class="col-md-6">
        <div class="clients_wrap_table dashboard-widget">
          <h4 class="pull-left">Timesheets &nbsp;&nbsp;<a href="#" class="pull-right" title="Calendar"><i aria-hidden="true" class="fa fa-calendar"></i></a></h4>
          <a href="javascript:void(0)" class="collpase-div pull-right" title="SlideUp/SlideDown"><i aria-hidden="true" class="fa fa-angle-down"></i></a>
          <div class="clearfix require_wrap_table dashboard-data">
            <div class="db-widget-sm">
              <div class="col-xs-12 col-sm-3">
                <div class="info-box"> <span class="info-box-icon bg-orange"> <i class="fa fa-floppy-o" aria-hidden="true"></i> </span>
                  <div class="info-box-content text-center">
                    <p class="info-box-text"><a href="timesheet-status.html">Saved</a></p>
                    <p class="info-box-number">1000</p>
                  </div>
                </div>
              </div>
              <div class="col-xs-12 col-sm-3">
                <div class="info-box"> <span class="info-box-icon bg-blue"> <i class="fa fa-clock-o" aria-hidden="true"></i> </span>
                  <div class="info-box-content text-center">
                    <p class="info-box-text"><a href="timesheet-status.html">Waiting</a></p>
                    <p class="info-box-number"><strong>90</strong></p>
                  </div>
                </div>
              </div>
              <div class="col-xs-12 col-sm-3">
                <div class="info-box"> <span class="info-box-icon bg-green"> <i class="fa fa-check-circle-o" aria-hidden="true"></i> </span>
                  <div class="info-box-content text-center">
                    <p class="info-box-text"><a href="timesheet-status.html">Approved</a></p>
                    <p class="info-box-number"><strong>900</strong></p>
                  </div>
                </div>
              </div>
              <div class="col-xs-12 col-sm-3">
                <div class="info-box"> <span class="info-box-icon bg-red"> <i class="fa fa-ban" aria-hidden="true"></i> </span>
                  <div class="info-box-content text-center">
                    <p class="info-box-text"><a href="timesheet-status.html">Rejected</a></p>
                    <p class="info-box-number"><strong>10</strong></p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <!-- Pagenation Ends --> 
      </div>
      <!-- Timesheets ends --> 
    </div>
    <!-- Tasks and Timesheets columns ends -->


    <!-- Timesheets Approvals and Requirement row  -->
    <div class="clearfix row mb"> 
      <div class="col-md-6 mb">
        <div class="clients_wrap_table dashboard-widget ">
          <h4 class="pull-left">Timesheets Approvals &nbsp;&nbsp;<a href="#" class="pull-right" title="Calendar"><i aria-hidden="true" class="fa fa-calendar"></i></a></h4>
          <a href="javascript:void(0)" class="collpase-div pull-right" title="SlideUp/SlideDown"><i aria-hidden="true" class="fa fa-angle-down"></i></a>
          <div class="clearfix require_wrap_table dashboard-data">
            <table>
              <thead>
                <tr>
                  <th>Employee</th>
                  <th>Frequency</th>
                  <th>Total Hours</th>
                  <th>Action</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td><div><span>Kumar Sarathsahu</span></div></td>
                  <td><div><span>Full Time</span></div></td>
                  <td>290 hrs.</td>
                  <td>Primary</td>
                </tr>
                <tr>
                  <td><div><span>Kumar Sarathsahu</span></div></td>
                  <td><div><span>Full Time</span></div></td>
                  <td>290 hrs.</td>
                  <td>Primary</td>
                </tr>                <tr>
                  <td><div><span>Kumar Sarathsahu</span></div></td>
                  <td><div><span>Full Time</span></div></td>
                  <td>290 hrs.</td>
                  <td>Primary</td>
                </tr>
                                <tr>
                  <td><div><span>Kumar Sarathsahu</span></div></td>
                  <td><div><span>Full Time</span></div></td>
                  <td>290 hrs.</td>
                  <td>Primary</td>
                </tr>
                                <tr>
                  <td><div><span>Kumar Sarathsahu</span></div></td>
                  <td><div><span>Full Time</span></div></td>
                  <td>290 hrs.</td>
                  <td>Primary</td>
                </tr>
              </tbody>
            </table>
            
            <!-- Pagenation -->
            <nav class="clearfix">
              <ul id="paginodg" class="pagination pull-right">
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
        <!-- Pagenation Ends --> 
      </div>
      <!-- Tasks Ends --> 
      <!-- Requirement -->
      <div class="col-md-6">
        <div class="dashboard-widget">
          <h4 class="pull-left">Requirement &nbsp;&nbsp;<a href="#" class="pull-right" title="Calendar"><i aria-hidden="true" class="fa fa-calendar"></i></a></h4>
          <a href="javascript:void(0)" class="collpase-div pull-right" title="SlideUp/SlideDown"><i aria-hidden="true" class="fa fa-angle-down"></i></a>
          <div class="clearfix require_wrap_table dashboard-data">
            <ul class="nav nav-tabs">
              <li class="active"><a data-toggle="tab" href="#today-req">Today</a></li>
              <li><a data-toggle="tab" href="#prev-req">Previous</a></li>
            </ul>
            <div class="tab-content clearfix mt">
              <div id="today-req" class="tab-pane fade in active">
                <div class="db-widget-sm">
                  <div class="col-xs-12 col-sm-3 mb">
                    <div class="info-box"> <span class="info-box-icon bg-red"> <i class="fa fa-check-square" aria-hidden="true"></i> </span>
                      <div class="info-box-content text-center">
                        <p class="info-box-number">1000</p>
                        <p class="info-box-text"> <a href="timesheet-status.html">Submission</a> </p>
                      </div>
                    </div>
                  </div>
                  <div class="col-xs-12 col-sm-3 mb">
                    <div class="info-box"> <span class="info-box-icon bg-green"> <i class="fa fa-black-tie" aria-hidden="true"></i> </span>
                      <div class="info-box-content text-center">
                        <p class="info-box-number">1000</p>
                        <p class="info-box-text"> <a href="timesheet-status.html">Applicants</a> </p>
                      </div>
                    </div>
                  </div>
                  <div class="col-xs-12 col-sm-3 mb">
                    <div class="info-box"> <span class="info-box-icon bg-blue"> <i class="fa fa-graduation-cap" aria-hidden="true"></i> </span>
                      <div class="info-box-content text-center">
                        <p class="info-box-number">1000</p>
                        <p class="info-box-text"><a href="timesheet-status.html">Interviews</a></p>
                      </div>
                    </div>
                  </div>
                  <div class="col-xs-12 col-sm-3 mb">
                    <div class="info-box"> <span class="info-box-icon bg-orange"> <i class="fa fa-tags" aria-hidden="true"></i> </span>
                      <div class="info-box-content text-center">
                        <p class="info-box-number">1000</p>
                        <p class="info-box-text"><a href="timesheet-status.html">Offers</a></p>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div id="prev-req" class="tab-pane fade">
                <div class="db-widget-sm">
                  <div class="col-xs-12 col-sm-3 mb">
                    <div class="info-box"> <span class="info-box-icon bg-red"> <i class="fa fa-check-square" aria-hidden="true"></i> </span>
                      <div class="info-box-content text-center">
                        <p class="info-box-number">1000</p>
                        <p class="info-box-text"> <a href="timesheet-status.html">Submission</a> </p>
                      </div>
                    </div>
                  </div>
                  <div class="col-xs-12 col-sm-3 mb">
                    <div class="info-box"> <span class="info-box-icon bg-green"> <i class="fa fa-black-tie" aria-hidden="true"></i> </span>
                      <div class="info-box-content text-center">
                        <p class="info-box-number">1000</p>
                        <p class="info-box-text"> <a href="timesheet-status.html">Applicants</a> </p>
                      </div>
                    </div>
                  </div>
                  <div class="col-xs-12 col-sm-3 mb">
                    <div class="info-box"> <span class="info-box-icon bg-blue"> <i class="fa fa-graduation-cap" aria-hidden="true"></i> </span>
                      <div class="info-box-content text-center">
                        <p class="info-box-number">1000</p>
                        <p class="info-box-text"><a href="timesheet-status.html">Interviews</a></p>
                      </div>
                    </div>
                  </div>
                  <div class="col-xs-12 col-sm-3 mb">
                    <div class="info-box"> <span class="info-box-icon bg-orange"> <i class="fa fa-tags" aria-hidden="true"></i> </span>
                      <div class="info-box-content text-center">
                        <p class="info-box-number">1000</p>
                        <p class="info-box-text"><a href="timesheet-status.html">Offers</a></p>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <!-- Requirement ends --> 
      </div>
    </div>
    
    <!-- Pie chart --> 
  </section>
  <!-- Content Area Ends--> 
  
  
  
  <script> 
$(document).ready(function(){
	
	timeSheetSubmissionStatusCount();
	
});
</script>
  

<script type="text/javascript">


function timeSheetSubmissionStatusCount(){
    var sourceleadUrl ='${sourceleadUrl}';
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+"/getTimeSheetSubmissionStatus", 
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	
		        	var  savedTimesheetsSubmissionsCount = json.savedTimesheetSubmissionCount;
		        	var  waitingTimesheetsSubmissionsCount =json.waitingForApprovalTimesheetSubmissionCount;
		        	var  approvalTimesheetsSubmissionsCount =json.approvedTimesheetSubmissionCount;
		        	var  rejectedTimesheetsSubmissionsCount = json.rejectedTimesheetSubmissionCount; 
		        	
		        	if(savedTimesheetsSubmissionsCount>0){
	                      $("#savedTimesheetsSubmissionsCount").text(savedTimesheetsSubmissionsCount);
	                  }else{
	                      $("#savedTimesheetsSubmissionsCount").html("0");
	                      $("#savedTimesheetsSubmissionsCount").removeAttr("href");
	                  }
		        	if(waitingTimesheetsSubmissionsCount>0){
	                      $("#waitingTimesheetsSubmissionsCount").text(waitingTimesheetsSubmissionsCount);
	                  }else{
	                      $("#waitingTimesheetsSubmissionsCount").html("0");
	                      $("#waitingTimesheetsSubmissionsCount").removeAttr("href");
	                  }
		        	if(approvalTimesheetsSubmissionsCount>0){
	                      $("#approvalTimesheetsSubmissionsCount").text(approvalTimesheetsSubmissionsCount);
	                  }else{
	                      $("#approvalTimesheetsSubmissionsCount").html("0");
	                      $("#approvalTimesheetsSubmissionsCount").removeAttr("href");
	                  }
		        	if(rejectedTimesheetsSubmissionsCount>0){
	                      $("#rejectedTimesheetsSubmissionsCount").text(rejectedTimesheetsSubmissionsCount);
	                  }else{
	                      $("#rejectedTimesheetsSubmissionsCount").html("0");
	                      $("#rejectedTimesheetsSubmissionsCount").removeAttr("href");
	                  }
		        	
		 		}
		  });
		 
	}

</script>


<script>
  $('#selector').delay(2000).fadeOut('slow');
</script> 

<jsp:include page="masterFooter.jsp" />
