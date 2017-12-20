<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<script type="text/javascript">
$(document).ready(function(){
	getApplicantBySourceChart();
});
</script>


<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Applicant(s) by Source</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Applicant(s) by Source</div>
    </section>
    <div class="clearfix">
    <div class="clearfix"></div>
    	<div class="ReqRepHead clearfix">
        <div class="col-md-2">
        <a href="applicant-source.html" title="Graphical View" class="btn btn-default"><i class="fa fa-pie-chart"></i></a>
        <a href="applicantsource-list.html" title="List View" class="btn btn-default"><i class="fa fa-list"></i></a>
        </div>
        <div class="col-md-4">
          <select class="chosen-select form-control" multiple data-placeholder="Select Job">
            <c:forEach var="option" items="${requirementsList}">
				<option value="${option.requirementId}">${option.jobTitle}</option>
			</c:forEach>
          </select>
        </div>
        <div class="col-md-3 dropdown-select positionRelative">
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
        
        <div class="col-md-2">
        <button type="button" class="btn btn-primary btn-sm	mt2">Customize</button>
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
    	<div class="clearfix"></div>
   	 	<!-- Applicant source Graph 2017 -->
    	 	<div class="dashboard-widget clearfix mt">
    	 	<div id="applicantSource" class="amchartAnchor-Hide" style="height: 350px; width: 100%;"></div>
    	 	</div>
    	 	<!-- Applicant source Graph December 2017 -->
    	<div class="clearfix"></div>
    </div>
  </section>


<jsp:include page="../masterFooter.jsp" />



<script type="text/javascript">
function getApplicantBySourceChart(){
	
  $.ajax({ 
	   	type: "GET",
	   	url: sourceleadUrl+"/getApplicantBySourceDetails?${_csrf.parameterName}=${_csrf.token}",
	    success: function(data) {
	    	
	    }
	 });
}
</script>