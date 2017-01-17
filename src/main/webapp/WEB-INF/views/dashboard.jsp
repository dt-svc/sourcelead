
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<style>
.dashboard-widget {
/* min-height: 400px !important; */
}
.dashboard-widget.dashboard-widget-icons{
min-height: 225px !important;
}
.dashboard-widget .blank-pages{
padding: 0;
min-height: 100px;
}
.side-bar .side-nav ul li span {
display: inline-block !important;
float: none !important;
}
.custom-datatble table{
 margin-top: 20px !important;
 }
 .custom-datatble .dataTables_filter{
   top: -35px !important;
    padding: 0 !important;
 }
 .dashboard-datepkr{
       position:relative;
}
.main-header span.logo i{
position: relative;
top:10px;
}
/* .dashboard-datepkr input[type="text"]{
       width:115px;
       border:1px solid #ccc;
       margin-right:5px;
}
.dashboard-datepkr .fa{
   position: absolute;
   right: 10px;
   top: 4px;
} */
</style>

 <style type="text/css">
.clients_wrap_table {
overflow-x: auto;
}
</style>

 <jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="resources/css/jquery-ui.css" rel="stylesheet">


<section class="content-wraper">
	    <section class="content-header clearfix">
	      <div class="pull-left">
	        <h2>Dashboard</h2>
	      </div>
	    </section>
	    
	    <div class="clearfix row mb">
	      <sec:authorize access="hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_EXPORT_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS','ROLE_FILL_MEMBER_TIMESHEETS','ROLE_VIEW_MEMBER_TIMESHEETS','ROLE_EXPORT_MEMBER_TIMESHEETS')"> 
			 <c:if test = "${SourceLeadWeb_timesheet == 'Y'}">       
			   <jsp:include page="dashboardTimesheets.jsp"></jsp:include>  
			 </c:if> 
	      </sec:authorize>
			 <sec:authorize access="hasAnyRole('ROLE_VIEW_REQUIREMENT')">
				<c:if test = "${SourceLeadWeb_ats == 'Y' || SourceLeadWeb_vms == 'Y'}">
					<jsp:include page="dashboardRequirement.jsp"></jsp:include> 
				</c:if>
		     </sec:authorize>
	    </div>
	    	
	    	<c:if test = "${SourceLeadWeb_ats == 'Y' || SourceLeadWeb_vms == 'Y'}">
	    			<sec:authorize access="hasAnyRole('ROLE_VIEW_REQUIREMENT')">
					<jsp:include page="dashboardAssignedRequirements.jsp"></jsp:include> 
					</sec:authorize>
					
			    <div class="clearfix row mb"> 
			    	 <sec:authorize access="hasAnyRole('ROLE_VIEW_REQUIREMENT')">
			   	 	 <jsp:include page="dashboardRequirementApprovals.jsp"></jsp:include>
			   	 	 </sec:authorize>
			   	 	  <sec:authorize access="hasAnyRole('ROLE_VIEW_INTERVIEW')">
			    	 <jsp:include page="dashboardInterviews.jsp"></jsp:include>
			    	 </sec:authorize>
			    </div>
		    </c:if>
		     <div class="clearfix row mb"> 
		    	 <c:if test = "${isManagerWithSubmissionRateStatusAsRequestOrAccept == true}">  
		     		<jsp:include page="dashboardApplicants.jsp"></jsp:include>
		     	</c:if>
		     </div>
	    
	   			 <sec:authorize access="!hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_EXPORT_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS','ROLE_FILL_MEMBER_TIMESHEETS','ROLE_VIEW_MEMBER_TIMESHEETS','ROLE_EXPORT_MEMBER_TIMESHEETS','ROLE_VIEW_REQUIREMENT','ROLE_VIEW_INTERVIEW')">
	   				  <div class='row'>
						<div class='col-lg-12'> 
						<div class='content-area clearfix'>
							<div class='blank-pages'>
							        <p><i class='fa fa-5x fa-bar-chart'></i></p>
						        	<p>No Records Found</p>
							 </div>
						</div>
						</div>
					  </div> 
				 </sec:authorize> 
				 
	    <div class="clearfix row mb"> 
	    </div>
</section>
<jsp:include page="masterFooter.jsp" />

