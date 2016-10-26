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
    
    	<jsp:include page="dashboardAssignedRequirements.jsp"></jsp:include>
	    <div class="clearfix row mb"> 
	   	 	 <jsp:include page="dashboardRequirementApprocals.jsp"></jsp:include>
	    	 <jsp:include page="dashboardInterviews.jsp"></jsp:include>
	    </div>
	    <div class="clearfix row mb"> 
	      	 <jsp:include page="dashboardTasks.jsp"></jsp:include>
	      	 <jsp:include page="dashboardTimesheets.jsp"></jsp:include>
	    </div>
	    <div class="clearfix row mb"> 
	   	  <jsp:include page="dashboardTimesheetsApprovals.jsp"></jsp:include>
	      <jsp:include page="dashboardRequirement.jsp"></jsp:include>
	    </div>
</section>
  

<script>
  $('#selector').delay(2000).fadeOut('slow');
</script> 

<jsp:include page="masterFooter.jsp" />
