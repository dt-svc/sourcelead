<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- <link href="/resources/css/jquery-ui.css" rel="stylesheet"> -->

<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
     <script src="/resources/js/sourcelead.common.js"></script> 

 <script type="text/javascript">
 $( document ).ready(function() {
	 var settingsConfigured = '${SourceLeadWeb_settingsConfigured}';
	 if(settingsConfigured == "N"){
		configureDiv();
	 }
	});
</script> 


<form:form class="login-form clearfix"  id="configure_form"  name="configure_form" method="post" >
   <div id ="configurePopUpDiv"></div>
</form:form>

<script>
function logoutForConfigure() {
	document.getElementById("logoutForm").submit();
}
</script>


<c:url var="logoutUrl" value="/logout"/>
<form action="${logoutUrl}" method="post" id="logoutForm">
 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

<form action="${logoutUrl}" method="post" id="logoutForm">
 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

<section class="content-wraper">
	    <section class="content-header clearfix">
	      
	      <c:if test="${!empty param.invitation}">
	        <div class="success_pop" id="selector"  style="display: block;">
		         <i class="fa fa-check-circle"></i><span>You accepted invitation successfully.</span>
			</div>
	     </c:if>
	     
	     <c:if test="${!empty msg}">
	        <div class="success_pop" id="selector1"  style="display: block;">
		         <i class="fa fa-check-circle"></i><span>${msg}</span>
			</div>
	     </c:if>
	    
	      <div class="pull-left">
	        <h2>Dashboard<a  onclick="$('#configureDashboard').show();$('.advancedsearch-mask').fadeIn();"><i class="fa fa-cog ml"></i></a></h2>
	      </div>
	    </section>
	    
	    
	    <div class="clearfix row">
	    	
	      <sec:authorize access="hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_EXPORT_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS','ROLE_FILL_MEMBER_TIMESHEETS','ROLE_VIEW_MEMBER_TIMESHEETS','ROLE_EXPORT_MEMBER_TIMESHEETS')"> 
			   <jsp:include page="dashboardTimesheets.jsp"></jsp:include>  
	      </sec:authorize>
	      <c:forEach var="userTabs" items="${SourceLeadWeb_tabsList}">
	      	<c:if test="${userTabs.tabName == 'Jobs'}">
				 <sec:authorize access="hasAnyRole('ROLE_VIEW_REQUIREMENT')">
						<jsp:include page="dashboardRequirement.jsp"></jsp:include> 
			     </sec:authorize>
		     </c:if>
		   </c:forEach>
			 <sec:authorize access="hasAnyRole('ROLE_ADD_MY_EXPENSES','ROLE_VIEW_EDIT_MY_EXPENSES','ROLE_EXPORT_MY_EXPENSES','ROLE_ADD_EMPLOYEE_EXPENSES','ROLE_VIEW_EDIT_EMPLOYEE_EXPENSES','ROLE_EXPORT_EMPLOYEE_EXPENSES')">   
				     <jsp:include page="expensesDashboardCounts.jsp"></jsp:include>
			 </sec:authorize>  	
			
			<sec:authorize access="hasAnyRole('ROLE_APPLY_LEAVE','ROLE_APPLY_BEHALF_LEAVE','ROLE_APPROVE_LEAVE')">
				<jsp:include page="leaveManagement.jsp"></jsp:include>
			</sec:authorize>
			
	    		<c:forEach var="userTabs" items="${SourceLeadWeb_tabsList}">
	    		<c:if test="${userTabs.tabName == 'Jobs'}">
	    			<sec:authorize access="hasAnyRole('ROLE_VIEW_REQUIREMENT')">
					<div class="col-md-12"><jsp:include page="dashboardAssignedRequirements.jsp"></jsp:include> </div>
					</sec:authorize>
				</c:if>	
				</c:forEach>
					<sec:authorize access="hasAnyRole('ROLE_CREATE_ON_BOARD_USER','ROLE_VIEW_EDIT_ON_BOARD_USER')">
					<div class="col-md-12"><jsp:include page="onboardingCandidatesList.jsp"></jsp:include></div> 
					</sec:authorize>
					
					<!-- Candidates for Invoice -->
			<sec:authorize access="hasAnyRole('ROLE_ADD_INVOICE','ROLE_VIEW_REQUIREMENT')">
				<jsp:include page="candidatesForInvoiceDashboard.jsp"></jsp:include>
			</sec:authorize> 
					
					<sec:authorize access="hasRole('ROLE_RESOURCE_REQUEST_APPROVER')">
					   <%-- <jsp:include page="resourceRequest.jsp"></jsp:include>  --%>
					    <div class="col-md-12"><jsp:include page="projectResourceRequest.jsp"></jsp:include></div>  
					    <jsp:include page="projectNewResourceRequest.jsp"></jsp:include>  
					 </sec:authorize>
					
					<sec:authorize access="hasRole('ROLE_BUDGET_APPROVER')">
					   <jsp:include page="budgetRequest.jsp"></jsp:include>
					</sec:authorize>
					
			    
			    <c:forEach var="userTabs" items="${SourceLeadWeb_tabsList}">
			    	<c:if test="${userTabs.tabName == 'Jobs'}">
				    	 <sec:authorize access="hasAnyRole('ROLE_VIEW_REQUIREMENT')">
				   	 	 <jsp:include page="dashboardRequirementApprovals.jsp"></jsp:include>
				   	 	 </sec:authorize>
			   	 	 </c:if>	
			   	 </c:forEach>
		     	
		     	<c:forEach var="userTabs" items="${SourceLeadWeb_tabsList}">
			     	<c:if test="${userTabs.tabName == 'Jobs'}">
			     		<sec:authorize access="hasAnyRole('ROLE_VIEW_REQUIREMENT')">
			     		<jsp:include page="dashboardApplicants.jsp"></jsp:include>
			     		</sec:authorize>
			     	</c:if>
		     	</c:forEach>
		     
		     <div class="clearfix"></div>
	     
<%--        <sec:authorize access="!hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_EXPORT_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS','ROLE_FILL_MEMBER_TIMESHEETS','ROLE_VIEW_MEMBER_TIMESHEETS','ROLE_EXPORT_MEMBER_TIMESHEETS','ROLE_VIEW_REQUIREMENT','ROLE_VIEW_INTERVIEW')">
			<div class='dashNewWrap mb'> 
				<div class='blank-pages col-md-12'>
			        <p><i class='fa fa-5x fa-bar-chart'></i></p>
		        	<p>No Records Found</p>
				 </div>
			</div>
		</sec:authorize>  --%>
	    
	    <div class="clearfix"> 
	     	<div class="col-md-12"><jsp:include page="dashboardEvents.jsp"></jsp:include></div>   
	   </div>  	
	     	<sec:authorize access="hasAnyRole('ROLE_VIEW_CONTRACT')">
				<div class="col-md-12"><jsp:include page="dashboardContracts.jsp"></jsp:include> </div>
			</sec:authorize>

		<jsp:include page="dashboard_configure_popup.jsp"></jsp:include>
		</div>
</section>

<jsp:include page="masterFooter.jsp" />
<jsp:include page="dashboard_scripts.jsp"/>