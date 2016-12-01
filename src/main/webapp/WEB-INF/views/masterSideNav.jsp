<!-- Side Nav Bar -->
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
  <aside>
  <div class="side-bar">
    <div class="side-bar-inner">
      <div class="user-panel">
                
	<div class="media">
		<div class="media-body">
			 <c:if test="${!empty SourceLeadWeb_org.fileContentType}">
				<div class="image">
				<img class="logo" src="<c:url value='/displayOrgImage/${SourceLeadWeb_orgCode}'/>" title="${SourceLeadWeb_orgName}" />
				</div>
			</c:if>
			<c:if test="${empty SourceLeadWeb_org.fileContentType}">
				<div class="text-logo">
				<span class="tbldata-dotdot">${SourceLeadWeb_orgName}</span>
				</div>
			</c:if>
		 </div>
	 </div>

        
      </div>
      <div class="side-nav">
        <ul class="list-unstyled">
          <li class="${sessionData.nav == 'Dashboard' ? 'side-nav-active' : ''}"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>" title="Dashboard"><span><i class="fa fa-dashboard"></i></span>Dashboard</a></li>
          
          <c:forEach var="userTabs" items="${SourceLeadWeb_tabsList}">
	                 
                    <c:if test="${userTabs.tabName == 'Clients'}">
                 	  <li class="${sessionData.nav == 'Clients' ? 'side-nav-active' : ''}"><a href="<c:url value='/clientDashboard'/>" title="Clients"><span><i class="fa fa-user-plus"></i></span>Clients</a></li>
                    </c:if>
	                
	                <c:if test="${userTabs.tabName == 'Requirement'}"> 
	                   <li class="${sessionData.nav == 'Requirement' ? 'side-nav-active' : ''}"><a href="<c:url value='/requirementList'/>" title="Requirement"><span><i class="fa fa-briefcase"></i></span>Requirement</a></li>
	                </c:if> 
	                
	                <c:if test="${userTabs.tabName == 'Candidates'}">
	                   <li class="${sessionData.nav == 'Candidates' ? 'side-nav-active' : ''}"><a href="<c:url value='/candidatesList'/>" title="Candidates"><span><i class="fa fa-user"></i></span>Candidates</a></li>
	                </c:if> 
                
	                <c:if test="${userTabs.tabName == 'Vendors'}">
	                   <li class="${sessionData.nav == 'Vendors' ? 'side-nav-active' : ''}"><a href="<c:url value='/vendorsList'/>" title="Vendors"><span><i class="fa fa-suitcase"></i></span>Vendors</a></li>
	                </c:if> 
	                 
	                 <c:if test="${userTabs.tabName == 'Users'}">
	                 <li class="${sessionData.nav == 'Users' ? 'side-nav-active' : ''}"><a href="<c:url value='/usersList'/>" title="Users"><span><i class="fa fa-users"></i></span>Users</a></li>
	                 </c:if>
	                 
	                 <c:if test="${userTabs.tabName == 'Projects'}">
	                	  <li class="${sessionData.nav == 'Projects' ? 'side-nav-active' : ''}"><a href="<c:url value='/projectDashboard'/>" title="Projects"><span><i class="fa fa-bar-chart"></i></span>Projects</a></li>
	                 </c:if>
	                 
	                 <c:if test="${userTabs.tabName == 'Timesheet Approval' }">
	                 <sec:authorize access="hasAnyRole('ROLE_FILL_MEMBER_TIMESHEETS','ROLE_VIEW_MEMBER_TIMESHEETS','ROLE_EXPORT_MEMBER_TIMESHEETS')"> 
				           <li class="${sessionData.nav == 'TimeSheet' || sessionData.nav == 'TimeSheetApproval' ? 'side-nav-active' : ''}">
							  <a href="#" class="tree-view-new" title="TimeSheets">
								<span><i class="fa fa-clock-o"></i></span>
							  Timesheets <i class="fa fa-fw fa-caret-down"></i>
							  </a>
							  
							  <a href="#" class="tree-view" title="TimeSheets">
								<span><i class="fa fa-clock-o"></i></span>
							  Timesheets <i class="fa fa-fw fa-caret-down"></i>
							  </a>
							  
							  <c:if test="${sessionData.nav == 'TimeSheet' || sessionData.nav == 'TimeSheetApproval'}">
							  <ul class="collapses" style="display: block">
							  	<li class="${sessionData.nav == 'TimeSheet' ? 'treview-active' : ''}"><a href="<c:url value='/timesheet'/>" title="My TimeSheets">My TimeSheets</a></li>
									<li class="${sessionData.nav == 'TimeSheetApproval' ? 'treview-active' : ''}"><a href="<c:url value='/timesheetApproval'/>" title="TimeSheet Approval">TimeSheet Approval</a></li>
							  </ul>
							  </c:if>
							  <c:if test="${sessionData.nav != 'TimeSheet' || sessionData.nav != 'TimeSheetApproval'}">
							  <ul class="collapses" style="display: none">
							  	<li class="${sessionData.nav == 'TimeSheet' ? 'treview-active' : ''}"><a href="<c:url value='/timesheet'/>" title="My TimeSheets">My TimeSheets</a></li>
									<li class="${sessionData.nav == 'TimeSheetApproval' ? 'treview-active' : ''}"><a href="<c:url value='/timesheetApproval'/>" title="TimeSheet Approval">TimeSheet Approval</a></li>
							  </ul>
							  </c:if>
						  </li>
					  </sec:authorize>
	                 </c:if>
	                 
	                 <c:if test="${userTabs.tabName == 'My Timesheets'}">
	                 <sec:authorize access="hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_EXPORT_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS') && !hasAnyRole('ROLE_FILL_MEMBER_TIMESHEETS','ROLE_VIEW_MEMBER_TIMESHEETS','ROLE_EXPORT_MEMBER_TIMESHEETS')">
	                       <li class="${sessionData.nav == 'TimeSheet' ? 'side-nav-active' : ''}"><a href="<c:url value='/timesheet'/>"><span><i class="fa fa-clock-o"></i></span>TimeSheet</a></li>
	                 </sec:authorize>
	                 </c:if>
	                 
	                 
	                 <c:if test="${userTabs.tabName == 'Finance' }">
				           <li class="${sessionData.nav == 'Finance' || sessionData.nav == 'AssetManagement' ? 'side-nav-active' : ''}">
							  <a href="#" class="tree-view-new" title="Back Office">
								<span><i class="fa fa-bank"></i></span>
							  Back Office <i class="fa fa-fw fa-caret-down"></i>
							  </a>
							  
							  <a href="#" class="tree-view" title="Back Office">
								<span><i class="fa fa-bank"></i></span>
							  Back Office <i class="fa fa-fw fa-caret-down"></i>
							  </a>
							  
							  <c:if test="${sessionData.nav == 'Finance' || sessionData.nav == 'AssetManagement'}">
							  <ul class="collapses" style="display: block">
							  	<li class="${sessionData.nav == 'Finance' ? 'treview-active' : ''}"><a href="<c:url value='/finance'/>" title="Finance">Finance</a></li>
									<li class="${sessionData.nav == 'AssetManagement' ? 'treview-active' : ''}"><a href="<c:url value='/assetManagement'/>" title="Asset Management">Asset Management</a></li>
							  </ul>
							  </c:if>
							  <c:if test="${sessionData.nav != 'Finance' || sessionData.nav != 'AssetManagement'}">
							  <ul class="collapses" style="display: none">
							  	<li class="${sessionData.nav == 'BackOffice' ? 'treview-active' : ''}"><a href="<c:url value='/finance'/>" title="Finance">Finance</a></li>
									<li class="${sessionData.nav == 'AssetManagement' ? 'treview-active' : ''}"><a href="<c:url value='/assetManagement'/>" title="Asset Management">Asset Management</a></li>
							  </ul>
							  </c:if>
						  </li>
	                 </c:if>
	                 
	               
	                 
          </c:forEach>
          
         
        
        </ul>
      </div>
    </div>
    </div>
    
<ul class="timesheet-menu list-unstyled">
   <li class="${sessionData.nav == 'TimeSheet' ? 'treview-active' : ''}"><a href="<c:url value='/timesheet'/>">My Timesheet</a></li>
   <li class="${sessionData.nav == 'TimeSheetApproval' ? 'treview-active' : ''}"><a href="<c:url value='/timesheetApproval'/>">Timesheet Approval</a></li>
 </ul>
  </aside>
  <!-- Side Nav Bar --> 