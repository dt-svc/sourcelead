<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<script type="text/javascript">
$( document ).ready(function() {
	var settingsConfigured = '${SourceLeadWeb_settingsConfigured}';
	if(settingsConfigured == "N"){
		configureDiv();
	}
});
</script> 

<form:form class="login-form clearfix" id="configure_form" name="configure_form" method="post">
	<div id="configurePopUpDiv"></div>
</form:form>

<script>
function logoutForConfigure() {
	document.getElementById("logoutForm").submit();
}
var loaded = false;
</script>

<c:url var="logoutUrl" value="/logout"/>
<form action="${logoutUrl}" method="post" id="logoutForm">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

<form action="${logoutUrl}" method="post" id="logoutForm">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
  
<section class="content-wraper">
	<div class="dashboard_loaded">
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
		    	<h2>Dashboard<a href="javascript:void(0)" onclick="$('#configureDashboard').show();$('.advancedsearch-mask').fadeIn();"><i class="fa fa-cog ml"></i></a></h2>
		  	</div>
		</section>
		
		<div class="row">
	    <c:if test="${dashboardItems != null && dashboardItems.itemsObjectList.size() > 0}">
			<c:forEach items="${dashboardItems.itemsObjectList}" var="itemObj">
				<c:set var="itemName" value="${itemObj.get('name')}"></c:set>
				<c:set var="itemChecked" value="${itemObj.get('checked')}"></c:set>
				<c:set var="itemOrder" value="${itemObj.get('order')}"></c:set>
				<c:if test='${(itemName eq "Timesheets" && itemChecked == true) || (itemName eq "My Timesheets" && itemChecked == true) || (itemName eq "Approval Timesheets" && itemChecked == true)}'>
			    	<sec:authorize access="hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_EXPORT_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS','ROLE_FILL_MEMBER_TIMESHEETS','ROLE_VIEW_MEMBER_TIMESHEETS','ROLE_EXPORT_MEMBER_TIMESHEETS')"> 
					<c:if test = "${SourceLeadWeb_timesheet == 'Y'}">       
						<jsp:include page="dashboardTimesheets.jsp"></jsp:include>  
						
					</c:if> 
			     	</sec:authorize>
				</c:if>
				<c:if test='${itemName eq "Jobs" && itemChecked == true}'>
			      <c:forEach var="userTabs" items="${SourceLeadWeb_tabsList}">
			      	<c:if test="${userTabs.tabName == 'Jobs'}">
						 <sec:authorize access="hasAnyRole('ROLE_VIEW_REQUIREMENT')">
							<c:if test = "${SourceLeadWeb_ats == 'Y' || SourceLeadWeb_vms == 'Y'}">
								<jsp:include page="dashboardRequirement.jsp"></jsp:include>
							</c:if>
					     </sec:authorize>
				     </c:if>
				   </c:forEach>
				</c:if>
		    	<c:if test='${itemName eq "Expenses" && itemChecked == true}'>
					<sec:authorize access="hasAnyRole('ROLE_ADD_MY_EXPENSES','ROLE_VIEW_EDIT_MY_EXPENSES','ROLE_EXPORT_MY_EXPENSES','ROLE_ADD_EMPLOYEE_EXPENSES','ROLE_VIEW_EDIT_EMPLOYEE_EXPENSES','ROLE_EXPORT_EMPLOYEE_EXPENSES')">   
						<jsp:include page="expensesDashboardCounts.jsp"></jsp:include>
					</sec:authorize>  	
		    	</c:if>
				<c:if test='${itemName eq "Leaves" && itemChecked == true}'>
					<sec:authorize access="hasAnyRole('ROLE_APPLY_LEAVE','ROLE_APPLY_BEHALF_LEAVE','ROLE_APPROVE_LEAVE')">
						<jsp:include page="leaveManagement.jsp"></jsp:include>
					</sec:authorize>
				</c:if>
				
				<!-- Candidates for Invoice -->
				<c:if test='${itemName eq "Candidates for Invoice" && itemChecked == true}'>
			<sec:authorize access="hasAnyRole('ROLE_ADD_INVOICE','ROLE_VIEW_REQUIREMENT')">
				<jsp:include page="candidatesForInvoiceDashboard.jsp"></jsp:include>
			</sec:authorize> 
			</c:if>
	    
		    	<c:if test = "${SourceLeadWeb_ats == 'Y' || SourceLeadWeb_vms == 'Y'}">
		    		<%-- <c:forEach items="${dashboardItems.itemsList}" var="item"> --%>
			    		<c:forEach var="userTabs" items="${SourceLeadWeb_tabsList}">
				    		<c:if test="${userTabs.tabName == 'Jobs'}">
				    			<c:if test='${itemName eq "Assigned Jobs" && itemChecked == true}'>
					    			<sec:authorize access="hasAnyRole('ROLE_VIEW_REQUIREMENT')">
										<div class="col-md-12"><jsp:include page="dashboardAssignedRequirements.jsp"></jsp:include></div> 
									</sec:authorize>
				    			</c:if>
							</c:if>	
						</c:forEach>
						<sec:authorize access="hasAnyRole('ROLE_CREATE_ON_BOARD_USER','ROLE_VIEW_EDIT_ON_BOARD_USER')">
							<c:if test='${itemName eq "Candidates for Onboarding" && itemChecked == true}'>
								<div class="col-md-12"><jsp:include page="onboardingCandidatesList.jsp"></jsp:include></div>
							</c:if>
						</sec:authorize>
							
						<sec:authorize access="hasRole('ROLE_RESOURCE_REQUEST_APPROVER')">
							   <%-- <jsp:include page="resourceRequest.jsp"></jsp:include>  --%>
							<c:if test='${itemName eq "Project Requests" && itemChecked == true}'>
							   <div class="col-md-12"> <jsp:include page="projectResourceRequest.jsp"></jsp:include> </div> 
							    <jsp:include page="projectNewResourceRequest.jsp"></jsp:include>  
							</c:if>
						</sec:authorize>
							
						<sec:authorize access="hasRole('ROLE_BUDGET_APPROVER')">
							<c:if test='${itemName eq "Project Requests" && itemChecked == true}'>
						    	<jsp:include page="budgetRequest.jsp"></jsp:include>
							</c:if>
						</sec:authorize>
							
					    
					    <c:forEach var="userTabs" items="${SourceLeadWeb_tabsList}">
					    	<c:if test="${userTabs.tabName == 'Jobs'}">
						    	 <sec:authorize access="hasAnyRole('ROLE_VIEW_REQUIREMENT')">
						    	 <c:if test='${itemName eq "Job Approvals" && itemChecked == true}'>
							   	 	 <jsp:include page="dashboardRequirementApprovals.jsp"></jsp:include>
						    	 </c:if>
						   	 	 </sec:authorize>
					   	 	 </c:if>	
					   	 </c:forEach>
			    </c:if>
		     	<c:forEach var="userTabs" items="${SourceLeadWeb_tabsList}">
			     	<c:if test="${userTabs.tabName == 'Jobs'}">
			     		<sec:authorize access="hasAnyRole('ROLE_VIEW_REQUIREMENT')">
			     			<%-- <c:forEach items="${dashboardItems.itemsList}" var="item"> --%>
			     				<c:if test='${itemName eq "Applicant Permission" && itemChecked == true}'>
					     				<jsp:include page="dashboardApplicants.jsp"></jsp:include>
			     				</c:if>
			     			<%-- </c:forEach> --%>
			     		</sec:authorize>
			     	</c:if>
		     	</c:forEach>
		   
	    	
	    		<c:if test='${itemName eq "Events" && itemChecked == true}'>
					<div class="clearfix"></div>
					<div class="col-md-12"><jsp:include page="dashboardEvents.jsp"></jsp:include></div>
					<div class="clearfix"></div>
	    		</c:if>
	     	
				<sec:authorize access="hasAnyRole('ROLE_VIEW_CONTRACT')">
		     		<c:if test='${itemName eq "Contracts" && itemChecked == true}'>
						<div class="col-md-12"><jsp:include page="dashboardContracts.jsp"></jsp:include></div>
		     		</c:if>
				</sec:authorize>
	    	</c:forEach>
	    </c:if>
		<jsp:include page="dashboard_configure_popup.jsp"></jsp:include>
	</div>
	</div>
</section>

<jsp:include page="masterFooter.jsp" />
<jsp:include page="dashboard_scripts.jsp"/>