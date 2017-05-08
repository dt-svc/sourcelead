
<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<!-- Side Nav Bar -->
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

  <aside>
  <div class="side-bar theme_sideMenu">
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
				 <c:if test="${SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_PROFESSIONAL' && SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_RECRUITER'}">
					<c:if test="${!empty SourceLeadWeb_orgName}">
					<span class="tbldata-dotdot" title="${SourceLeadWeb_orgName}">${SourceLeadWeb_orgName}</span>
					</c:if>
				</c:if>
				<c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_PROFESSIONAL' || SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_RECRUITER'}">
				<span class="tbldata-dotdot" title="${SourceLeadWeb_displayName}">${SourceLeadWeb_displayName}</span>
				</c:if>
				</div>
			</c:if>
		 </div>
	 </div>

        
      </div>
      <div class="side-nav">
        <ul class="list-unstyled">
        <c:if test="${SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_PROFESSIONAL' && SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_RECRUITER'}">
        <li class="${sessionData.nav == 'Dashboard' ? 'side-nav-active' : ''}"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>" title="Dashboard"><span><i class="fa fa-dashboard"></i></span>Dashboard</a></li>
        </c:if>
        
        <c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_PROFESSIONAL'}">
        <li class="${sessionData.nav == 'Dashboard' ? 'side-nav-active' : ''}"><a href="<c:url value='/professionalDashboard'/>" title="Dashboard"><span><i class="fa fa-dashboard"></i></span>Dashboard</a></li>
        </c:if> 
        
        <c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_RECRUITER'}">
        <li class="${sessionData.nav == 'Dashboard' ? 'side-nav-active' : ''}"><a href="<c:url value='/recruiterDashboard'/>" title="Dashboard"><span><i class="fa fa-dashboard"></i></span>Dashboard</a></li>
        </c:if> 
          
          <c:set var="timesheetTabExist" scope="session" value="N"/>
          <c:forEach var="userTabs" items="${SourceLeadWeb_tabsList}">
                    <c:if test="${userTabs.tabName == 'Clients'}">
                 	  <li class="${sessionData.nav == 'Clients' ? 'side-nav-active' : ''}"><a href="<c:url value='/clientDashboard'/>" title="Clients"><span><i class="fa fa-user-plus"></i></span>Clients</a></li>
                    </c:if>
	                <c:if test="${userTabs.tabName == 'Requirement'}"> 
	                <c:if test="${SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_PROFESSIONAL' && SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_RECRUITER'}"> 
	                   <li class="${sessionData.nav == 'Requirement' ? 'side-nav-active' : ''}"><a href="<c:url value='/requirementList'/>" title="Requirements"><span><i class="fa fa-briefcase"></i></span>Requirements</a></li>
	                </c:if> 
	                
	                <c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_PROFESSIONAL' || SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_RECRUITER' }">
	                 <li class="${sessionData.nav == 'Requirement' ? 'side-nav-active' : ''}"><a href="<c:url value='/professionalRequirements'/>" title="Requirements"><span><i class="fa fa-briefcase"></i></span>Requirements</a></li>
	                </c:if>
	                </c:if>
	                
	                
	                
	                <c:if test="${userTabs.tabName == 'Candidates'}">
	                 <c:if test="${SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_RECRUITER'}">
	                   <li class="${sessionData.nav == 'Candidates' ? 'side-nav-active' : ''}"><a href="<c:url value='/candidatesList'/>" title="Candidates"><span><i class="fa fa-user"></i></span>Candidates</a></li>
	                 </c:if>
	                 <c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_RECRUITER'}">
	                   <li class="${sessionData.nav == 'Candidates' ? 'side-nav-active' : ''}"><a href="<c:url value='/candidatesList'/>" title="Candidates"><span><i class="fa fa-user"></i></span>Candidates</a></li>
	                 </c:if>
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
	                 
	                 <c:if test="${userTabs.tabName == 'Profile'}">
	                	  <li class="${sessionData.nav == 'Profile' ? 'side-nav-active' : ''}"><a href="<c:url value='/professionalProfile'/>" title="Profile"><span><i class="fa fa-users"></i></span>Profile</a></li>
	                 </c:if>
	                 
	                 <c:if test="${timesheetTabExist == 'N' && (userTabs.tabName == 'My Timesheets' || userTabs.tabName == 'Timesheet Approval')}">
	                 <sec:authorize access="hasAnyRole('ROLE_FILL_MEMBER_TIMESHEETS','ROLE_VIEW_MEMBER_TIMESHEETS','ROLE_EXPORT_MEMBER_TIMESHEETS') && hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_EXPORT_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS')"> 
				           <li class="${sessionData.nav == 'TimeSheet' || sessionData.nav == 'TimeSheetApproval' ? 'side-nav-active' : ''}">
							  <a href="#" class="tree-view-new" title="Timesheets">
								<span><i class="fa fa-clock-o"></i></span>
							  Timesheets <i class="fa fa-fw fa-caret-down"></i>
							  </a>
							  
							  <a href="#" class="tree-view" title="Timesheets">
								<span><i class="fa fa-clock-o"></i></span>
							  Timesheets <i class="fa fa-fw fa-caret-down"></i>
							  </a>
							 
							  <c:if test="${sessionData.nav == 'TimeSheet' || sessionData.nav == 'TimeSheetApproval'}">
							   <c:set var ="timesheetTabExist" scope="session" value="Y"/> 
							  <ul class="collapses" style="display: block">
							        
							        <%-- <li class="${sessionData.nav == 'TimeSheet' ? 'treview-active' : ''}"><a href="<c:url value='/timesheet'/>" title="My TimeSheets">My TimeSheets</a></li>
							  	    <li class="${sessionData.nav == 'TimeSheet' ? 'treview-active' : ''}"><a href="<c:url value='/timesheetApproval'/>" title="My TimeSheets">TimeSheet Approval</a></li> --%>
							  	     
							  	    <li class="${sessionData.nav == 'TimeSheet' ? 'treview-active' : ''}"><a href="<c:url value='/timesheet'/>" title="My TimeSheets">My TimeSheets</a></li>
									<li class="${sessionData.nav == 'TimeSheetApproval' ? 'treview-active' : ''}"><a href="<c:url value='/timesheetApproval'/>" title="TimeSheet Approval">TimeSheet Approval</a></li>
							  </ul>
							  </c:if>
							  <c:if test="${sessionData.nav != 'TimeSheet' || sessionData.nav != 'TimeSheetApproval'}">
							  <c:set var ="timesheetTabExist" scope="session" value="Y"/>
							  <ul class="collapses" style="display: none">
							 		<%-- <li class="${sessionData.nav == 'TimeSheet' ? 'treview-active' : ''}"><a href="<c:url value='/timesheet'/>" title="My TimeSheets">My TimeSheets</a></li>
							  		<li class="${sessionData.nav == 'TimeSheet' ? 'treview-active' : ''}"><a href="<c:url value='/newTimesheetApproval'/>" title="My TimeSheets">TimeSheet Approval</a></li> --%>
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
	                 <c:if test="${userTabs.tabName == 'Timesheet Approval'}">
	                 <sec:authorize access="!hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_EXPORT_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS') && hasAnyRole('ROLE_FILL_MEMBER_TIMESHEETS','ROLE_VIEW_MEMBER_TIMESHEETS','ROLE_EXPORT_MEMBER_TIMESHEETS')">
	                       <li class="${sessionData.nav == 'TimeSheetApproval' ? 'side-nav-active' : ''}"><a href="<c:url value='/timesheetApproval'/>"><span><i class="fa fa-clock-o"></i></span>TimeSheet</a></li>
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
                                    <li><a href="<c:url value='/quickBookInvoice'/>" title="QuickBook Invoice">QuickBook Customers</a></li>
                                    
                              </ul>
                              </c:if>
                              <c:if test="${sessionData.nav != 'Finance' || sessionData.nav != 'AssetManagement'}">
                              <ul class="collapses" style="display: none">
                                  <li class="${sessionData.nav == 'BackOffice' ? 'treview-active' : ''}"><a href="<c:url value='/finance'/>" title="Finance">Finance</a></li>
                                    <li class="${sessionData.nav == 'AssetManagement' ? 'treview-active' : ''}"><a href="<c:url value='/assetManagement'/>" title="Asset Management">Asset Management</a></li>
                                      <li><a href="<c:url value='/quickBookInvoice'/>" title="QuickBook Invoice">QuickBook Customers</a></li>
                              
                              </ul>
                              </c:if>
                              
						  </li>
	                 </c:if>
	                 
	                                 
	                 
                     <c:if test="${SourceLeadWeb_associatesTabsCount == 2 && userTabs.tabName == 'Associates List'}">
	                   <li class="${sessionData.nav == 'Associates' || sessionData.nav == 'OnAndOffboard' ? 'side-nav-active' : ''}">
							  <a href="#" class="tree-view-new" title="Associates">
								<span><i class="fa fa-black-tie"></i></span>
							  Associates <i class="fa fa-fw fa-caret-down"></i>
							  </a>
							  
							  <a href="#" class="tree-view" title="Associates">
								<span><i class="fa fa-black-tie"></i></span>
							  Associates <i class="fa fa-fw fa-caret-down"></i>
							  </a>
							  
						   
						   <c:if test="${sessionData.nav == 'Associates' || sessionData.nav == 'OnAndOffboard'}">
                              <ul class="collapses" style="display: block">
                                  <li class="${sessionData.nav == 'Associates' ? 'treview-active' : ''}"><a href="<c:url value='/associatesList'/>" title="Associates List">Associates List</a></li>
                                  <li class="${sessionData.nav == 'OnAndOffboard' ? 'treview-active' : ''}"><a href="<c:url value='/candidateOnBoardingList'/>" title="On / Off Boarding">On / Off Boarding</a></li>
                              </ul>
                           </c:if>
                              
                           <c:if test="${sessionData.nav != 'Associates' || sessionData.nav != 'OnAndOffboard'}">
                           <ul class="collapses" style="display: none">
                               <li class="${sessionData.nav == 'Associates' ? 'treview-active' : ''}"><a href="<c:url value='/associatesList'/>" title="Associates List">Associates List</a></li>
                               <li class="${sessionData.nav == 'OnAndOffboard' ? 'treview-active' : ''}"><a href="<c:url value='/candidateOnBoardingList'/>" title="On / Off Boarding">On / Off Boarding</a></li>
                           </ul>
                           </c:if>
                           
                              
						  </li>
	                 </c:if>
	                 
	                 <c:if test="${SourceLeadWeb_associatesTabsCount < 2 && userTabs.tabName == 'Associates List'}">
	                   <li class="${sessionData.nav == 'Associates' ? 'side-nav-active' : ''}"><a href="<c:url value='/associatesList'/>" title="Associates List"><span><i class="fa fa-black-tie"></i></span>Associates List</a></li>
	                 </c:if>
	                 
	                 <c:if test="${SourceLeadWeb_associatesTabsCount < 2 && userTabs.tabName == 'On & Off Boarding'}">
	                 <li class="${sessionData.nav == 'OnAndOffboard' ? 'side-nav-active' : ''}"><a href="<c:url value='/candidateOnBoardingList'/>" title="On / Off Boarding"><span><i class="fa fa-black-tie"></i></span>On / Off Boarding</a></li>
	                 </c:if>
	                 
	                 
	                 
	                 <c:if test="${userTabs.tabName == 'Contracts'}">
	                   <li class="${sessionData.nav == 'Contract' ? 'side-nav-active' : ''}"><a href="<c:url value='/contractListbyStatus'/>" title="Contracts"><span><i class="fa fa-object-ungroup"></i></span>Contracts</a></li>
	                </c:if> 
	                
	                
	                
	                
	                
	                <c:if test="${SourceLeadWeb_billingTabsCount == 2 && userTabs.tabName == 'Receivable'}">
	                   <li class="${sessionData.nav == 'Receivable' || sessionData.nav == 'Payables' ? 'side-nav-active' : ''}">
							  <a href="#" class="tree-view-new" title="Billing">
								<span><i class="fa fa-money"></i></span>
							  Billing <i class="fa fa-fw fa-caret-down"></i>
							  </a>
							  
							  <a href="#" class="tree-view" title="Billing">
								<span><i class="fa fa-money"></i></span>
							  Billing <i class="fa fa-fw fa-caret-down"></i>
							  </a>
							  
						   
						   <c:if test="${sessionData.nav == 'Receivable' || sessionData.nav == 'Payables'}">
                              <ul class="collapses" style="display: block">
                                  <li class="${sessionData.nav == 'Receivable' ? 'treview-active' : ''}"><a href="<c:url value='/invoiceList'/>" title="Receivable">Receivable</a></li>
                                  <li class="${sessionData.nav == 'Payables' ? 'treview-active' : ''}"><a href="<c:url value='/payablesList'/>" title="Payables">Payables</a></li>
                              </ul>
                           </c:if>
                              
                           <c:if test="${sessionData.nav != 'Receivable' || sessionData.nav != 'Payables'}">
                           <ul class="collapses" style="display: none">
                               <li class="${sessionData.nav == 'Receivable' ? 'treview-active' : ''}"><a href="<c:url value='/invoiceList'/>" title="Receivable">Receivable</a></li>
                               <li class="${sessionData.nav == 'Payables' ? 'treview-active' : ''}"><a href="<c:url value='/payablesList'/>" title="Payables">Payables</a></li>
                           </ul>
                           </c:if>
                              
						  </li>
	                 </c:if>
	                 
	                 
	                  <c:if test="${SourceLeadWeb_billingTabsCount < 2 && userTabs.tabName == 'Receivable'}">
	                   <li class="${sessionData.nav == 'Receivable' ? 'side-nav-active' : ''}"><a href="<c:url value='/invoiceList'/>" title="Billing"><span><i class="fa fa-money"></i></span>Billing</a></li>
	                 </c:if>
	                 
	                 <c:if test="${SourceLeadWeb_billingTabsCount < 2 && userTabs.tabName == 'Payables'}">
	                 <li class="${sessionData.nav == 'Payables' ? 'side-nav-active' : ''}"><a href="<c:url value='/payablesList'/>" title="Billing"><span><i class="fa fa-money"></i></span>Billing</a></li>
	                 </c:if>
	                 

          </c:forEach>
          
          				 
     <%--  <li class="${sessionData.nav == 'SocialInviter' ? 'side-nav-active' : ''}"><a href="<c:url value='/socialinviter'/>" title="SocialInviter"><span><i class="fa fa-bar-chart"></i></span>Social Inviter</a></li> --%>
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