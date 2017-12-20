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
          <select class="chosen-select form-control" multiple data-placeholder="Select Job" name="jobIds" id="jobIds">
            <c:forEach var="option" items="${requirementsList}">
				<option value="${option.requirementId}">${option.jobTitle}</option>
			</c:forEach>
          </select>
        </div>
        <div class="col-md-3 dropdown-select positionRelative">
          <select class="form-control" name="dateSelection" id="dateSelection">
            <option value=''>Select Date</option>
            <option value='Last_7_days' selected>Last 7 days</option>
            <option value='Last_15_days'>Last 15 days</option>
            <option value='1_month'>1 month</option>
            <option value='3_months'>3 months</option>
            <option value='YTD'>YTD</option>
            <option value='Last_Year'>Last Year</option>
            <option value="custom">Custom</option>
          </select>
        </div>
        
        <div class="col-md-2">
        <button type="button" class="btn btn-primary btn-sm	mt2" onclick="getApplicantBySourceChart();">Customize</button>
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
	
  var jobIds = $("#jobIds").val();
  if(jobIds == null){
	  jobIds = '';
  }
  var dateSelection = $("#dateSelection").val(); 
	
  $.ajax({ 
	   	type: "GET",
	   	url: sourceleadUrl+"/getApplicantBySourceDetails?${_csrf.parameterName}=${_csrf.token}&jobIds="+jobIds+"&dateSelection="+dateSelection,
	    success: function(data) {
	    	
	    }
	 });
}
</script>