<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link rel="icon" type="resources/image/png"
	href="resources/images/fav.png" />
<link href="resources/css/register-style.css" rel="stylesheet">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="resources/css/register-style.css" rel="stylesheet">
<!-- <script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script> -->
<!-- these files for autocomplete with tagit -->
<link href="resources/css/jquery-tagit.css" rel="stylesheet">
<script src="resources/js/formvalidator.js"></script>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script>
$(document).ready(function(){
   var searchType = "${searchType}";
   var searchValue = "${searchValue}";
   getSearchResults(searchType,searchValue);
});
</script>



<section class="content-wraper">
<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Universal Search Result</h2>
		</div>
		<div class="pull-right brud-crum hide"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo;<a href="<c:url value='/projectDashboard'/>">ProjectList</a> &raquo;Create Project
	</section>
	<div class="clearfix">
<div id ="searchList"></div>

<!-- Requirement Search Results -->
      <!-- <div class="search-result-widget clearfix">
           <h4>Requirement</h4>
          <div class="srchresult clearfix row">
              <div class="srchresult-content col-xs-12 col-md-12">
                  <p><label><strong><a href="javascript:void(0)">Requirement Title</a></strong></label>, <label>Type</label>,
                  <label>Location</label>
                  </p>
                  <p><label><strong>Client Name</strong></label></p>
                  <p><label>2.4 yrs</label>, <label>1 Month</label></p>
                  <p><label>Java, Hibernet, Sprints, Javascript, MySql, jQuery, AngularJS, ReactJS, NodeJs. etc.. </label></p>
                  <span href="javascript:void(0)" class="reqstatus-btn">Open</span>
              </div>
          </div>
          
        <div class="srchresult clearfix row">
              <div class="srchresult-content col-xs-12 col-md-12">
                  <p><label><strong><a href="javascript:void(0)">Requirement Title</a></strong></label>, <label>Type</label>
                  <label>Location</label>
                  </p>
                  <p><label><strong>Client Name</strong></label></p>
                  <p><label>2.4 yrs</label>, <label>1 Month</label></p>
                  <p><label>Java, Hibernet, Sprints, Javascript, MySql, jQuery, AngularJS, ReactJS, NodeJs. etc.. </label></p>

                  <span href="javascript:void(0)" class="reqstatus-btn">OnHold</span>
              </div>
        </div>
        <div class="srchresult clearfix row">
              <div class="srchresult-content col-xs-12 col-md-12">
                  <p><label><strong><a href="javascript:void(0)">Requirement Title</a></strong></label>,<label>Type</label>
                  <label>Location</label>
                  </p>
                  <p><label><strong>Client Name</strong></label></p>
                  <p><label>2.4 yrs</label>, <label>1 Month</label></p>
                  <p><label>Java, Hibernet, Sprints, Javascript, MySql, jQuery, AngularJS, ReactJS, NodeJs. etc.. </label></p>
                  <span href="javascript:void(0)" class="reqstatus-btn">Open</span>
              </div>
          </div>
          <div class="clearfix">
              <a href="javascript:void(0)" title="View More" class="pull-right btn btn-sm btn-default">View More</a>
          </div>

      </div> -->
     <!-- Requirement Search Results -->
	</div>
	</section>

<script type="text/javascript">
function getSearchResults(searchType,searchValue){
	var sourceleadUrl = '${sourceleadUrl}';
	  $.ajax({
	   	type: "GET",
	    url: sourceleadUrl+"/universalSearch?${_csrf.parameterName}=${_csrf.token}&searchType="+searchType+"&searchValue="+searchValue,	    	   
	    success: function(data) {
	    	var result='';
	    	var json = JSON.parse(data);
	    	result = result+'<div class="search-result-widget clearfix">';
	    	result = result+'   <h4>Requirement</h4>';
	    	 for (var i = 0; i < json.length; i++) {
	             var report= json[i];
			    	result = result+'  <div class="srchresult clearfix row">';
			    	result = result+'      <div class="srchresult-content col-xs-12 col-md-12">';
			    	result = result+'          <p><label><strong><a href="javascript:void(0)">'+report.jobTitle+'</a></strong></label>, <label>'+report.jobType+'</label>';
			    	result = result+'          <label>Location</label>';
			    	result = result+'          </p>';
			    	result = result+'          <p><label><strong>'+report.clientName+'</strong></label></p>';
			    	result = result+'          <p><label>'+report.minExp+'-'+report.maxExp+'</label></p>';
			    	result = result+'          <p><label>'+report.skills+'</label></p>';
			    	result = result+'          <span href="javascript:void(0)" class="reqstatus-btn">'+report.requirementSubmissionStatus+'</span>';
			    	result = result+'      </div>';
			    	result = result+'  </div>';
			    	result = result+'  <div class="clearfix">';
			    	result = result+'      <a href="javascript:void(0)" title="View More" class="pull-right btn btn-sm btn-default">View More</a>';
			    	result = result+'  </div>';
	    	 }
	    	 result = result+'</div>';
	    	 $("#searchList").empty();
	    	 $("#searchList").append(result);
	    	
	    }
   }); 
}
</script>



<jsp:include page="../masterFooter.jsp" />








