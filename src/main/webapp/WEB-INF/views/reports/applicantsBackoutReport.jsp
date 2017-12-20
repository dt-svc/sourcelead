<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />


<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<link href="/resources/css/jquery-tagit.css" rel="stylesheet">
<script src="/resources/js/csc.js" type="text/javascript" charset="utf-8"></script>
<script src="/resources/js/tag-it.js" type="text/javascript" charset="utf-8"></script>
<script src="/resources/js/tag-it.min.js" type="text/javascript" charset="utf-8"></script>


 <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Applicant(s) Backout</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Applicant(s) Backout</div>
    </section>
    <!-- hiring Process  -->
    <div class="content-dashboard clearfix">
      <div class="ReqRepHead clearfix">
        <div class="col-md-5 pull-left reqListBtn">
        <a href="applicants-backout.html" title="Graphical View" class="btn btn-default"><i class="fa fa-pie-chart"></i></a>
        <a href="applicantsbackout-list.html" title="List View" class="btn btn-default"><i class="fa fa-list"></i></a>
        </div>
        <div class="col-md-4">
          <select class="chosen-select form-control" multiple data-placeholder="Select Job">
            <option>--Select Job--</option>
            <option>Job 1</option>
            <option>Job 2</option>
            <option>Job 3</option>
          </select>
        </div>
        <div class="col-md-2 dropdown-select positionRelative">
          <select class="form-control" id="custom-dates">
            <option>Select Date</option>
            <option selected>Last 7 days</option>
            <option>Last 15 days</option>
            <option>1 month</option>
            <option>3 months</option>
            <option>YTD</option>
            <option>Last Year</option>
            <option value="custom">Custom</option>
          </select>
          
          <!-- Top Leave Takers Custom Date -->
          <div class="clearfix pull-lefet col-md-12 paddingRight customLeaves" id="customLeaves" style="display: none; width: 346px;">
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
        <div class="col-md-1">
          <div class="btn-group bootstrap_dropdownMenu dropdownPadding">
            <button type="button" class="btn btn-primary btn-sm" title="Actions"><i class="fa fa-retweet"></i></button>
            <button type="button" class="btn btn-primary dropdown-toggle btn-sm" data-toggle="dropdown"> <span class="caret"></span> </button>
            <ul class="dropdown-menu clearfix  dropdown-menu-right">
              <li role="Export" class="mt5 cursorPointer"><i class="fa fa-file-pdf-o paddingRight"></i>Export</li>
            </ul>
          </div>
        </div>
      </div>
      <!-- Decline Reasons -->
      <div class="row">
        <div class="col-md-12 mt25">
          <div class="clients_wrap_table dashboard-widget">
            <h4 class="pull-left">Backout Reasons</h4>
            <div id="backoutReason" style="width:100%; height: 250px; margin-bottom: 25px;" class="amchartAnchor-Hide"></div>
          </div>
        </div>
      </div>
      <!-- Decline Reasons -->
      
      <!-- Best recruiters  -->
      <div class="content-dashboard clearfix hidden"> 
        <!-- Assigned Requirements -->
        <div class="clearfix dashboard-widget mt25 col-md-12">
          <div class="clearfix">
          <h4 class="pull-left">Applicant Backout Lists</h4>
          	<div class="remove_search pull-right">
                  <input placeholder="Search" type="text">
                </div>
          </div>
          <div class="clearfix require_wrap_table dashboard-data">
            <table class="tableBor">
              <thead>
                <tr>
                  <th>Jobs</th>
                  <th>Current Company</th>
                  <th>Compensation</th>
                  <th>Better Offer</th>
                  <th>Cold Feet</th>
                  <th>Personal</th>
                  <th>Other</th>
                  <th>Total</th>
                </tr>
                <tr>
				  <td><div>Jobs</div></td>
                  <td><div>Current Company</div></td>
                  <td><div>Compensation</div></td>
                  <td><div>Better Offer</div></td>
                  <td><div>Cold Feet</div></td>
                  <td><div>Personal</div></td>
                  <td><div>Other</div></td>
                  <td><div>200</div></td> 
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
      <!-- Best recruiters Ends  --> 
      
    </div>
  </section>