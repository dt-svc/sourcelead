<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Dashboard</h2>
      </div>
    </section>
    
    <!-- Profile Content -->
				<section class="row mt">
				        <div class="col-md-12">
				          <div class="dashboard-widget">
				            <h4 class="pull-left">Interviews <span class="dashboard-datepkr dashboarddatepkr dashboarddatepkr">
				            <input type="text" class="input-sm hasDatepicker" id="interviewdate">
				            <i class="fa fa-calendar"></i></span></h4>
				            <ul class="nav nav-tabs">
				              <li class="active"><a data-toggle="tab" href="#today">Today</a></li>
				              <li><a data-toggle="tab" href="#upcoming">Upcoming</a></li>
				              <li><a data-toggle="tab" href="#previous">Previous</a></li>
				            </ul>
				            
				           <div class="tab-content clearfix mt">
				               <div id="today" class="tab-pane fade in active">
				               <div class="clearfix require_wrap_table dashboard-data">
							  <table>
							    <thead>
							      <tr>
							        <th>Requirement Title</th>
							        <th>Interview</th>
							        <th>Location</th>
							        <th>Interview Type</th>
							        <th>Date &amp; Time</th>
							        <th>Action</th>
							      </tr>
							      <tr>
							        <td>
							          <div class="tbldata-dotdot-big">Java Developer, Hibernet, Springs, MySql, Shutter</div>
							        </td>
							        <td>
							          <div class="tbldata-dotdot-vsm">
							          6 years</div></td>
							        <td>
							          <div class="tbldata-dotdot-sm">Banglore, India</div>
							        </td>
							        <td>
							          <div class="tbldata-dotdot-100">100 US</div>
							        </td>
							        <td>
							          <div class="tbldata-dotdot-vsm">23/12/2016</div>
							        </td>
							        <td>Selected</td>
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
	                <div id="upcoming" class="tab-pane fade in">
				      <div class="clearfix require_wrap_table dashboard-data">
				  <table>
				    <thead>
				      <tr>
				        <th>Requirement Title</th>
				        <th>Interview</th>
				        <th>Location</th>
				        <th>Interview Type</th>
				        <th>Date &amp; Time</th>
				        <th>Action</th>
				      </tr>
				      <tr>
				        <td>
				          <div class="tbldata-dotdot-big">Java Developer, Hibernet, Springs, MySql, Shutter</div>
				        </td>
				        <td>
				          <div class="tbldata-dotdot-vsm">
				          6 years</div></td>
				        <td>
				          <div class="tbldata-dotdot-sm">Banglore, India</div>
				        </td>
				        <td>
				          <div class="tbldata-dotdot-100">100 US</div>
				        </td>
				        <td>
				          <div class="tbldata-dotdot-vsm">23/12/2016</div>
				        </td>
				        <td>Selected</td>
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
                <div id="previous" class="tab-pane fade in">
				   <div class="clearfix require_wrap_table dashboard-data">
				  <table>
				    <thead>
				      <tr>
				        <th>Requirement Title</th>
				        <th>Interview</th>
				        <th>Location</th>
				        <th>Interview Type</th>
				        <th>Date &amp; Time</th>
				        <th>Action</th>
				      </tr>
				      <tr>
				        <td>
				          <div class="tbldata-dotdot-big">Java Developer, Hibernet, Springs, MySql, Shutter</div>
				        </td>
				        <td>
				          <div class="tbldata-dotdot-vsm">
				          6 years</div></td>
				        <td>
				          <div class="tbldata-dotdot-sm">Banglore, India</div>
				        </td>
				        <td>
				          <div class="tbldata-dotdot-100">100 US</div>
				        </td>
				        <td>
				          <div class="tbldata-dotdot-vsm">23/12/2016</div>
				        </td>
				        <td>Selected</td>
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
				           
				           </div>
				           
				            </div>
				        </div>
				      </section>
				      
				      
			      <section class="mt"> 
			        <!-- Assigned Requirements -->
			        <div class="clearfix dashboard-widget mb col-md-12">
			          <h4 class="pull-left">Applied Requirements <span class="dashboard-datepkr dashboarddatepkr">
			            <input type="text" class="input-sm hasDatepicker" id="ar">
			            <i class="fa fa-calendar"></i></span></h4>
			          <div class="clearfix require_wrap_table dashboard-data">
			            <table>
			              <thead>
			                <tr>
			                  <th>Requirement Title</th>
			                  <th>Experience</th>
			                  <th>Location</th>
			                  <th>Rate</th>
			                  <th>Applied on</th>
			                  <th>Status</th>
			                </tr>
			                <tr>
			                  <td>
			                    <div class="tbldata-dotdot-big">Java Developer, Hibernet, Springs, MySql, Shutter</div>
			                  </td>
			                  <td>
			                    <div class="tbldata-dotdot-vsm">
			                    6 years</div></td>
			                  <td>
			                    <div class="tbldata-dotdot-sm">Banglore, India</div>
			                  </td>
			                  <td>
			                    <div class="tbldata-dotdot-100">100 US</div>
			                  </td>
			                  <td>
			                    <div class="tbldata-dotdot-vsm">23/12/2016</div>
			                  </td>
			                  <td>Selected</td>
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
			      </section>
			      
			      
			      <section class="mt"> 
			        <!-- Assigned Requirements -->
			        <div class="clearfix dashboard-widget mb col-md-12">
			          <h4 class="pull-left">Referred Requirements <span class="dashboard-datepkr dashboarddatepkr">
			            <input type="text" class="input-sm hasDatepicker" id="rr">
			            <i class="fa fa-calendar"></i></span></h4>
			          <div class="clearfix require_wrap_table dashboard-data">
			            <table>
			              <thead>
			                <tr>
			                  <th>Requirement Title</th>
			                  <th>Experience</th>
			                  <th>Location</th>
			                  <th>Rate</th>
			                  <th>Referred on</th>
			                  <th>Status</th>
			                </tr>
			                <tr>
			                  <td>
			                    <div class="tbldata-dotdot-big">Java Developer, Hibernet, Springs, MySql, Shutter</div>
			                  </td>
			                  <td>
			                    <div class="tbldata-dotdot-vsm">
			                    6 years</div></td>
			                  <td>
			                    <div class="tbldata-dotdot-sm">Banglore, India</div>
			                  </td>
			                  <td>
			                    <div class="tbldata-dotdot-100">100 US</div>
			                  </td>
			                  <td>
			                    <div class="tbldata-dotdot-vsm">23/12/2016</div>
			                  </td>
			                  <td>Selected</td>
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
			      </section>
    <!-- Profile Content --> 
  </section>

<jsp:include page="../masterFooter.jsp" />