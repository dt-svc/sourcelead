<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Dashboard</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Dashboard</div>
    </section>
   <!-- Requirement Approvals and Interview Row --> 
    <div class="clearfix row mb"> 
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
  </section>
  <!-- Content Area Ends-->