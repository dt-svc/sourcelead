<!-- Side Nav Bar -->
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
  <aside class="side-bar">
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
				<span>${SourceLeadWeb_orgName}</span>
				</div>
			</c:if>
		 </div>
	 </div>

        
      </div>
      <div class="side-nav">
        <ul class="list-unstyled">
          <li class="${sessionData.nav == 'Dashboard' ? 'side-nav-active' : ''}"><a href="<c:url value='/dashboard'/>"><span><i class="fa fa-dashboard"></i></span>Dashboard</a></li>
          <li class="${sessionData.nav == 'Users' ? 'side-nav-active' : ''}"><a href="<c:url value='/usersList'/>"><span><i class="fa fa-users"></i></span>Users</a></li>
          <li class="${sessionData.nav == 'Projects' ? 'side-nav-active' : ''}"><a href="<c:url value='/projectDashboard'/>"><span><i class="fa fa-bar-chart"></i></span>Projects</a></li>
          
          <li class="${sessionData.nav == 'TimeSheet' || sessionData.nav == 'TimeSheetApproval' ? 'side-nav-active' : ''}">
			  <a href="#" class="tree-view">
				<span><i class="fa fa-clock-o"></i></span>
			  Timesheets <i class="fa fa-fw fa-caret-down"></i>
			  </a>
			  <c:if test="${sessionData.nav == 'TimeSheet' || sessionData.nav == 'TimeSheetApproval'}">
			  <ul class="collapses" style="display: block">
			  	<li class="${sessionData.nav == 'TimeSheet' ? 'treview-active' : ''}"><a href="<c:url value='/timesheet'/>">Timesheet</a></li>
					<li class="${sessionData.nav == 'TimeSheetApproval' ? 'treview-active' : ''}"><a href="<c:url value='/timesheetApproval'/>">Timesheet Approval</a></li>
			  </ul>
			  </c:if>
			  <c:if test="${sessionData.nav != 'TimeSheet' || sessionData.nav != 'TimeSheetApproval'}">
			  <ul class="collapses" style="display: none">
			  	<li class="${sessionData.nav == 'TimeSheet' ? 'treview-active' : ''}"><a href="<c:url value='/timesheet'/>">Timesheet</a></li>
					<li class="${sessionData.nav == 'TimeSheetApproval' ? 'treview-active' : ''}"><a href="<c:url value='/timesheetApproval'/>">Timesheet Approval</a></li>
			  </ul>
			  </c:if>
		  </li>
          
          <li class="${sessionData.nav == 'Clients' ? 'side-nav-active' : ''}"><a href="<c:url value='/clientDashboard'/>"><span><i class="fa fa-user-plus"></i></span>Clients</a></li>
        </ul>
      </div>
    </div>
  </aside>
  <!-- Side Nav Bar --> 