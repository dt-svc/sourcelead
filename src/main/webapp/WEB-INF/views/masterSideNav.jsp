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
          <li><a href="javascript:void(0)"><span><i class="fa fa-envelope"></i></span>Mailbox</a></li>
          <li><a href="javascript:void(0)"><span><i class="fa fa-calendar"></i></span>Calendar</a></li>

          <li class="${sessionData.nav == 'Users' ? 'side-nav-active' : ''}"><a href="<c:url value='/usersList'/>"><span><i class="fa fa-users"></i></span>Users</a></li>
          <li class="${sessionData.nav == 'Projects' ? 'side-nav-active' : ''}"><a href="<c:url value='/projectDashboard'/>"><span><i class="fa fa-bar-chart"></i></span>Projects</a></li>
          <li><a href="<c:url value='/timesheet'/>"><span><i class="fa fa-bar-chart"></i></span>Timesheet</a></li>
          <li><a href="<c:url value='/timesheetApproval'/>"><span><i class="fa fa-bar-chart"></i></span>Timesheet approval</a></li>
          <li class="${sessionData.nav == 'Clients' ? 'side-nav-active' : ''}"><a href="<c:url value='/clientDashboard'/>"><span><i class="fa fa-user-plus"></i></span>Clients</a></li>
         <li><a href="javascript:void(0)"><span><i class="fa fa-file-text"></i></span>Documentation</a></li>
        </ul>
      </div>
    </div>
  </aside>
  <!-- Side Nav Bar --> 