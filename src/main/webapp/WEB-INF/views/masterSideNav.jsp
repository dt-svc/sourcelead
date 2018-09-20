<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<!-- Side Nav Bar -->
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

  <aside>
  <div class="side-bar theme_sideMenu">
    <div class="side-bar-inner">
      
      <div class="side-nav sidenav_inner">
        <ul class="list-unstyled">
        <c:if test="${SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_PROFESSIONAL' && SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_RECRUITER' && SourceLeadWeb_prodSupportLoginUserRole == null}">
        <li class="${sessionData.nav == 'Dashboard' ? 'side-nav-active' : ''}"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>" title="Dashboard"><span><i class="fa fa-dashboard"></i></span><i class="subMenu">Dashboard</i></a></li>
        </c:if>
        
         <c:if test="${SourceLeadWeb_prodSupportLoginUserRole == 'ROLE_PRODUCTION_SUPPORT'}">
        <li class="${sessionData.nav == 'Dashboard' ? 'side-nav-active' : ''}"><a href="<c:url value='${appPath}/productionSupportdashboard/${SourceLeadWeb_orgLocationId}/${SourceLeadWeb_prodSupportOrgLocationId}'/>" title="Dashboard"><span><i class="fa fa-dashboard"></i></span><i class="subMenu">Dashboard</i></a></li>
        </c:if>
        
        <c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_PROFESSIONAL'}">

        <li class="${sessionData.nav == 'Dashboard' ? 'side-nav-active' : ''}"><a href="<c:url value='${appPath}/professionalDashboard/${SourceLeadWeb_orgLocationId}/${SourceLeadWeb_displayName}'/>" title="Dashboard"><span><i class="fa fa-dashboard"></i></span><i class="subMenu">Dashboard</i></a></li>
        </c:if> 
        
        <c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_RECRUITER'}">
        <li class="${sessionData.nav == 'Dashboard' ? 'side-nav-active' : ''}"><a href="<c:url value='${appPath}/recruiterDashboard/${SourceLeadWeb_orgLocationId}/${SourceLeadWeb_displayName}'/>" title="Dashboard"><span><i class="fa fa-dashboard"></i></span><i class="subMenu">Dashboard</i></a></li>
 		</c:if>
 		
 		<sec:authorize access="hasAnyRole('ROLE_ENROLL_ORG')"> 
	        <li class="${sessionData.nav == 'OrgSettings' ? 'side-nav-active' : ''}"><a href="<c:url value='${appPath}/enrolledOrgsList'/>" title="Orgs"><span><i class="fa fa-cubes"></i></span><i class="subMenu">Orgs</i></a></li>
        </sec:authorize>
        
             
        <%--  <li class="${sessionData.nav == 'Profile' ? 'side-nav-active' : ''}"><a href="<c:url value='${appPath}/professionalProfile'/>" title="Profile"><span><i class="fa fa-id-card-o"></i></span><i class="subMenu">Profile</i></a></li> --%> 
          
          <c:set var="timesheetTabExist" scope="session" value="N"/>
          <c:forEach var="userTabs" items="${SourceLeadWeb_tabsList}">
                    <c:if test="${userTabs.tabName == 'Clients'}">
                 	  <li class="${sessionData.nav == 'Clients' ? 'side-nav-active' : ''}"><a href="<c:url value='${appPath}/clientDashboard'/>" title="Clients"><span><i class="fa fa-user-plus"></i></span><i class="subMenu">Clients</i></a></li>
                    </c:if>
	                <c:if test="${userTabs.tabName == 'Jobs'}"> 
	                <c:if test="${SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_PROFESSIONAL' && SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_RECRUITER'}"> 
	                   <li class="${sessionData.nav == 'Requirement' ? 'side-nav-active' : ''}"><a href="<c:url value='${appPath}/jobs'/>" title="JobsTest"><span><i class="fa fa-briefcase"></i></span><i class="subMenu">Jobs</i></a></li>
	                   
<%-- 	                   <li class="${sessionData.nav == 'Requirement' ? 'side-nav-active' : ''}"><a href="javascript:goToJobs('${appPath}/jobs');" title="JobsTest"><span><i class="fa fa-briefcase"></i></span><i class="subMenu">Jobs</i></a></li> --%>
	                </c:if> 
	                
	                <c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_PROFESSIONAL' || SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_RECRUITER' }">
	                 <li class="${sessionData.nav == 'Requirement' ? 'side-nav-active' : ''}"><a href="<c:url value='${appPath}/professionalRequirements'/>" title="Jobs"><span><i class="fa fa-briefcase"></i></span><i class="subMenu">Jobs</i></a></li>
	                </c:if>
	                </c:if>
	                
	                
	                
	                <c:if test="${userTabs.tabName == 'Candidates'}">
	                 <c:if test="${SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_RECRUITER'}">
	                   <li class="${sessionData.nav == 'Candidates' ? 'side-nav-active' : ''}"><a href="<c:url value='${appPath}/candidatesList'/>" title="Candidates"><span><i class="fa fa-user"></i></span><i class="subMenu">Candidates</i></a></li>
	                 </c:if>
	                 <c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_RECRUITER'}">
	                   <li class="${sessionData.nav == 'Candidates' ? 'side-nav-active' : ''}"><a href="<c:url value='${appPath}/candidatesList'/>" title="Candidates"><span><i class="fa fa-user"></i></span><i class="subMenu">Candidates</i></a></li>
	                 </c:if>
	                </c:if> 
	                
                
	                <c:if test="${userTabs.tabName == 'Vendors'}">
	                   <li class="${sessionData.nav == 'Vendors' ? 'side-nav-active' : ''}"><a href="<c:url value='${appPath}/vendorsList'/>" title="Vendors"><span><i class="fa fa-suitcase"></i></span><i class="subMenu">Vendors</i></a></li>
	                </c:if> 
	                 
	                 <c:if test="${userTabs.tabName == 'Associates'}">
	                 <li class="${sessionData.nav == 'Users' ? 'side-nav-active' : ''}"><a href="<c:url value='${appPath}/usersList'/>" title="Associates"><span><i class="fa fa-users"></i></span><i class="subMenu">Associates</i></a></li>
	                 </c:if>
	                 
	                 <c:if test="${userTabs.tabName == 'Projects'}">
	                	  <li class="${sessionData.nav == 'Projects' ? 'side-nav-active' : ''}"><a href="<c:url value='${appPath}/projectDashboard'/>" title="Projects"><span><i class="fa fa-bar-chart"></i></span><i class="subMenu">Projects</i></a></li>
	                 </c:if>
	                 
	                 <%-- <c:if test="${userTabs.tabName == 'Profile'}">
	                	  <li class="${sessionData.nav == 'Profile' ? 'side-nav-active' : ''}"><a href="<c:url value='/professionalProfile'/>" title="Profile"><span><i class="fa fa-users"></i></span><i class="subMenu">Profile</i></a></li>
	                 </c:if> --%>
	                 
	                 <c:if test="${timesheetTabExist == 'N' && (userTabs.tabName == 'My Timesheets' || userTabs.tabName == 'Timesheet Approval')}">
	                 <sec:authorize access="hasAnyRole('ROLE_FILL_MEMBER_TIMESHEETS','ROLE_VIEW_MEMBER_TIMESHEETS','ROLE_EXPORT_MEMBER_TIMESHEETS') && hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_EXPORT_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS')"> 
				           <li class="${sessionData.nav == 'TimeSheet' || sessionData.nav == 'TimeSheetApproval' ? 'side-nav-active' : ''}">
							 							  
							  <a href="#" class="timesheetLi" title="Timesheets">
								<span><i class="fa fa-clock-o"></i></span>
							  <i class="subMenu">Timesheets</i><i class="fa fa-fw fa-caret-down"></i>
							  </a>
							 
							  <c:if test="${sessionData.nav == 'TimeSheet' || sessionData.nav == 'TimeSheetApproval'}">
							   <c:set var ="timesheetTabExist" scope="session" value="Y"/> 
							  <ul class="collapses" style="display: none">
							        
							        <%-- <li class="${sessionData.nav == 'TimeSheet' ? 'treview-active' : ''}"><a href="<c:url value='/timesheet'/>" title="My TimeSheets">My TimeSheets</a></li>
							  	    <li class="${sessionData.nav == 'TimeSheet' ? 'treview-active' : ''}"><a href="<c:url value='/timesheetApproval'/>" title="My TimeSheets">TimeSheet Approval</a></li> --%>
							  	     
							  	    <li class="${sessionData.nav == 'TimeSheet' ? 'treview-active' : ''}"><a href="<c:url value='${appPath}/timesheet'/>" title="My TimeSheets">Self</a></li>
									<li class="${sessionData.nav == 'TimeSheetApproval' ? 'treview-active' : ''}"><a href="<c:url value='${appPath}/timesheetApproval'/>" title="TimeSheet Approval">Employees</a></li>
							  </ul>
							  </c:if>
							  <c:if test="${sessionData.nav != 'TimeSheet' || sessionData.nav != 'TimeSheetApproval'}">
							  <c:set var ="timesheetTabExist" scope="session" value="Y"/>
							  <ul class="collapses" style="display: none">
							 		<%-- <li class="${sessionData.nav == 'TimeSheet' ? 'treview-active' : ''}"><a href="<c:url value='/timesheet'/>" title="My TimeSheets">My TimeSheets</a></li>
							  		<li class="${sessionData.nav == 'TimeSheet' ? 'treview-active' : ''}"><a href="<c:url value='/newTimesheetApproval'/>" title="My TimeSheets">TimeSheet Approval</a></li> --%>
							  		<li class="${sessionData.nav == 'TimeSheet' ? 'treview-active' : ''}"><a href="<c:url value='${appPath}/timesheet'/>" title="My TimeSheets">Self</a></li>
									<li class="${sessionData.nav == 'TimeSheetApproval' ? 'treview-active' : ''}"><a href="<c:url value='${appPath}/timesheetApproval'/>" title="TimeSheet Approval">Employees</a></li> 
							  </ul>
							  </c:if>
						  </li>
					  </sec:authorize>
	                 </c:if>
	                 <c:if test="${userTabs.tabName == 'My Timesheets'}">
	                 <sec:authorize access="hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_EXPORT_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS') && !hasAnyRole('ROLE_FILL_MEMBER_TIMESHEETS','ROLE_VIEW_MEMBER_TIMESHEETS','ROLE_EXPORT_MEMBER_TIMESHEETS')">
	                       <li class="${sessionData.nav == 'TimeSheet' ? 'side-nav-active' : ''}"><a href="<c:url value='${appPath}/timesheet'/>"><span><i class="fa fa-clock-o"></i></span><i class="subMenu">TimeSheets</i></a></li>
	                 </sec:authorize>
	                 </c:if>
	                 <c:if test="${userTabs.tabName == 'Timesheet Approval'}">
	                 <sec:authorize access="!hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_EXPORT_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS') && hasAnyRole('ROLE_FILL_MEMBER_TIMESHEETS','ROLE_VIEW_MEMBER_TIMESHEETS','ROLE_EXPORT_MEMBER_TIMESHEETS')">
	                       <li class="${sessionData.nav == 'TimeSheetApproval' ? 'side-nav-active' : ''}"><a href="<c:url value='${appPath}/timesheetApproval'/>"><span><i class="fa fa-clock-o"></i></span><i class="subMenu">TimeSheets</i></a></li>
	                 </sec:authorize>
	                 </c:if>
	                 
	                 <c:if test="${userTabs.tabName == 'Finance' }">
				           <li class="${sessionData.nav == 'Finance' || sessionData.nav == 'AssetManagement' ? 'side-nav-active' : ''}">
							 							  
							  <a href="#" class="backofficeLi" title="Back Office">
								<span><i class="fa fa-bank"></i></span>
							  <i class="subMenu">Back Office</i><i class="fa fa-fw fa-caret-down"></i>
							  </a>
							  
							   <c:if test="${sessionData.nav == 'Finance' || sessionData.nav == 'AssetManagement'}">
                              <ul class="collapses" style="display: none">
                                  <li class="${sessionData.nav == 'Finance' ? 'treview-active' : ''}"><a href="<c:url value='${appPath}/finance'/>" title="Finance">Finance</a></li>
                                    <li class="${sessionData.nav == 'AssetManagement' ? 'treview-active' : ''}"><a href="<c:url value='${appPath}/assetManagement'/>" title="Asset Management">Asset Management</a></li>
                                    <li><a href="<c:url value='${appPath}/quickBookInvoice'/>" title="QuickBook Invoice">QuickBook Customers</a></li>
                                    
                              </ul>
                              </c:if>
                              <c:if test="${sessionData.nav != 'Finance' || sessionData.nav != 'AssetManagement'}">
                              <ul class="collapses" style="display: none">
                                  <li class="${sessionData.nav == 'BackOffice' ? 'treview-active' : ''}"><a href="<c:url value='${appPath}/finance'/>" title="Finance">Finance</a></li>
                                    <li class="${sessionData.nav == 'AssetManagement' ? 'treview-active' : ''}"><a href="<c:url value='${appPath}/assetManagement'/>" title="Asset Management">Asset Management</a></li>
                                      <li><a href="<c:url value='${appPath}/quickBookInvoice'/>" title="QuickBook Invoice">QuickBook Customers</a></li>
                              
                              </ul>
                              </c:if>
                              
						  </li>
	                 </c:if>
	                 
	                 
	                 <c:if test="${userTabs.tabName == 'On & Off Boarding'}">
	                  <li class="${sessionData.nav == 'OnAndOffboard' ? 'side-nav-active' : ''}"><a href="<c:url value='${appPath}/candidateOnBoardingList'/>" title="On / Off Boarding"><span><i class="fa fa-black-tie"></i></span><i class="subMenu">On / Off Board</i></a></li>
	                 </c:if>
	                 
	                 
	                 
	                 <c:if test="${userTabs.tabName == 'Contracts'}">
	                   <li class="${sessionData.nav == 'Contract' ? 'side-nav-active' : ''}"><a href="<c:url value='${appPath}/contractListbyStatus'/>" title="Contracts"><span><i class="fa fa-object-ungroup"></i></span><i class="subMenu">Contracts</i></a></li>
	                </c:if> 
	                
	                <c:if test="${SourceLeadWeb_billingTabsCount == 2 && userTabs.tabName == 'Receivable'}">
	                   <li class="${sessionData.nav == 'Receivable' || sessionData.nav == 'Payables' ? 'side-nav-active' : ''}">
														  
							  <a href="#" class="billingLi" title="Finance">
								<span><i class="fa fa-money"></i></span>
							  <i class="subMenu">Finance</i> <i class="fa fa-fw fa-caret-down"></i>
							  </a>
							  
						   
						   <c:if test="${sessionData.nav == 'Receivable' || sessionData.nav == 'Payables'}">
                              <ul class="collapses" style="display: none">
                                  <li class="${sessionData.nav == 'Receivable' ? 'treview-active' : ''}"><a href="<c:url value='${appPath}/invoiceList'/>" title="Invoices">Invoices</a></li>
                                  <li class="${sessionData.nav == 'Payables' ? 'treview-active' : ''}"><a href="<c:url value='${appPath}/payablesList'/>" title="Bills">Bills</a></li>
                              </ul>
                           </c:if>
                              
                           <c:if test="${sessionData.nav != 'Receivable' || sessionData.nav != 'Payables'}">
                           <ul class="collapses" style="display: none">
                               <li class="${sessionData.nav == 'Receivable' ? 'treview-active' : ''}"><a href="<c:url value='${appPath}/invoiceList'/>" title="Invoices">Invoices</a></li>
                               <li class="${sessionData.nav == 'Payables' ? 'treview-active' : ''}"><a href="<c:url value='${appPath}/payablesList'/>" title="Bills">Bills</a></li>
                           </ul>
                           </c:if>
                              
						  </li>
	                 </c:if>
	                 
	                 
	                  <c:if test="${SourceLeadWeb_billingTabsCount < 2 && userTabs.tabName == 'Receivable'}">
	                   <li class="${sessionData.nav == 'Receivable' ? 'side-nav-active' : ''}"><a href="<c:url value='${appPath}/invoiceList'/>" title="Invoices"><span><i class="fa fa-money"></i></span><i class="subMenu">Invoices</i></a></li>
	                 </c:if>
	                 
	                 <c:if test="${SourceLeadWeb_billingTabsCount < 2 && userTabs.tabName == 'Payables'}">
	                 <li class="${sessionData.nav == 'Payables' ? 'side-nav-active' : ''}"><a href="<c:url value='${appPath}/payablesList'/>" title="Bills"><span><i class="fa fa-money"></i></span><i class="subMenu">Bills</i></a></li>
	                 </c:if>
	                 
	                 
	                 
	                 
	                 <c:if test="${SourceLeadWeb_expensesTabCount == 2 && userTabs.tabName == 'My Expenses'}">
	                   <li class="${sessionData.nav == 'My Expenses' || sessionData.nav == 'Expenses Approval' ? 'side-nav-active' : ''}">
														  
							  <a href="#" class="expencesLi" title="Expenses">
								<span><i class="fa fa-calculator"></i></span>
							  <i class="subMenu">Expenses</i> <i class="fa fa-fw fa-caret-down"></i>
							  </a>
							  
						   
						   <c:if test="${sessionData.nav == 'My Expenses' || sessionData.nav == 'Expenses Approval'}">
                              <ul class="collapses" style="display: none">
                                  <li class="${sessionData.nav == 'My Expenses' ? 'treview-active' : ''}"><a href="<c:url value='${appPath}/expenses'/>" title="My Expenses">Self</a></li>
                                  <li class="${sessionData.nav == 'Expenses Approval' ? 'treview-active' : ''}"><a href="<c:url value='${appPath}/expensesApprovalList'/>" title="Expenses Approval">Employees</a></li>
                              </ul>
                           </c:if>
                              
                           <c:if test="${sessionData.nav != 'My Expenses' || sessionData.nav != 'Expenses Approval'}">
                           <ul class="collapses" style="display: none">
                               <li class="${sessionData.nav == 'My Expenses' ? 'treview-active' : ''}"><a href="<c:url value='${appPath}/expenses'/>" title="My Expenses">Self</a></li>
                               <li class="${sessionData.nav == 'Expenses Approval' ? 'treview-active' : ''}"><a href="<c:url value='${appPath}/expensesApprovalList'/>" title="Expenses Approval">Employees</a></li>
                           </ul>
                           </c:if>
                              
						  </li>
	                 </c:if>
	                 
	                 <c:if test="${SourceLeadWeb_expensesTabCount < 2 && userTabs.tabName == 'My Expenses'}">
	                   <li class="${sessionData.nav == 'My Expenses' ? 'side-nav-active' : ''}"><a href="<c:url value='${appPath}/expenses'/>" title="My Expenses"><span><i class="fa fa-calculator"></i></span><i class="subMenu">Expenses</i></a></li>
	                 </c:if>
	                 
	                 <c:if test="${SourceLeadWeb_expensesTabCount < 2 && userTabs.tabName == 'Expenses Approval'}">
	                 <li class="${sessionData.nav == 'Expenses Approval' ? 'side-nav-active' : ''}"><a href="<c:url value='${appPath}/expensesApprovalList'/>" title="Expenses Approval"><span><i class="fa fa-calculator"></i></span><i class="subMenu">Expenses Approval</i></a></li>
	                 </c:if>
	                 
	                 <c:if test="${userTabs.tabName == 'Asset Management'}">
	                   <li class="${sessionData.nav == 'AssetManagement' ? 'side-nav-active' : ''}"><a href="<c:url value='${appPath}/assetManagement'/>" title="Asset Management"><span><i class="fa fa-cogs"></i></span><i class="subMenu">Asset Management</i></a></li>
	                </c:if> 
	                
	                <c:if test="${userTabs.tabName == 'Support'}">
	                   <li class="${sessionData.nav == 'Support' ? 'side-nav-active' : ''}"><a href="<c:url value='${appPath}/sourceleadTicketList'/>" title="Support"><span><i class="fa fa-ticket"></i></span><i class="subMenu">Support</i></a></li>
	                </c:if> 
	                
	                <c:if test="${SourceLeadWeb_resourcesTabCount == 2 && userTabs.tabName == 'Blog'}">
	                   <li class="${sessionData.nav == 'Blogs' || sessionData.nav == 'UserManuals' ? 'side-nav-active' : ''}">
														  
							  <a href="#" class="resourcesli" title="Resources">
								<span><i class="fa fa-rss"></i></span>
							  <i class="subMenu">Resources</i> <i class="fa fa-fw fa-caret-down"></i>
							  </a>
							  
						   
						   <c:if test="${sessionData.nav == 'Blogs' || sessionData.nav == 'UserManuals'}">
                              <ul class="collapses" style="display: none">
                                  <li class="${sessionData.nav == 'Blogs' ? 'treview-active' : ''}"><a href="<c:url value='${appPath}/blogs'/>" title="Blog">Blog</a></li>
                                  <li class="${sessionData.nav == 'UserManuals' ? 'treview-active' : ''}"><a href="<c:url value='${appPath}/userManuals'/>" title="User Manuals">User Manuals</a></li>
                              </ul>
                           </c:if>
                              
                           <c:if test="${sessionData.nav != 'Blogs' || sessionData.nav != 'UserManuals'}">
                           <ul class="collapses" style="display: none">
                               <li class="${sessionData.nav == 'Blogs' ? 'treview-active' : ''}"><a href="<c:url value='${appPath}/blogs'/>" title="Blog">Blog</a></li>
                               <li class="${sessionData.nav == 'UserManuals' ? 'treview-active' : ''}"><a href="<c:url value='${appPath}/userManuals'/>" title="User Manuals">User Manuals</a></li>
                           </ul>
                           </c:if>
                              
						  </li>
	                 </c:if>
	                 
	                 <c:if test="${SourceLeadWeb_resourcesTabCount < 2}">
	                 
	                 <c:if test="${userTabs.tabName == 'Blog'}">
	                    <li class="${sessionData.nav == 'Blogs' ? 'side-nav-active' : ''}"><a href="<c:url value='${appPath}/blogs'/>" title="Blogs"><span><i class="fa fa-rss"></i></span><i class="subMenu">Blogs</i></a></li>
	                 </c:if>
	                 
	                 <c:if test="${userTabs.tabName == 'User Manuals'}">
	                    <li class="${sessionData.nav == 'UserManuals' ? 'side-nav-active' : ''}"><a href="<c:url value='${appPath}/userManuals'/>" title="User Manuals"><span><i class="fa fa-book"></i></span><i class="subMenu">User Manuals</i></a></li>
	                 </c:if>
	                      
	                 </c:if>
	                
	                
	                <c:if test="${userTabs.tabName == 'Campaigns'}">
                         <li class="${sessionData.nav == 'Campaigns' || sessionData.nav == 'CampaignSender' || sessionData.nav == 'CampaignRecipient' ? 'side-nav-active' : ''}"><a href="<c:url value='${appPath}/campaigns/hotcampaigns'/>" title="Campaigns"><span><i class="fa fa-bullhorn"></i></span><i class="subMenu">Campaigns</i></a></li>			  
			         </c:if>

					<c:if test="${SourceLeadWeb_paryrollTabCount == 2 && userTabs.tabName == 'Run Payroll'}">
						<li class="${sessionData.nav == 'Run Payroll' || sessionData.nav == 'My Payroll' ? 'side-nav-active' : ''}">
						
						<a href="#" class="payrollli" title="Pay Roll">
						<span><i class="fa fa-usd"></i></span>
						<i class="subMenu">Pay Roll</i> <i class="fa fa-fw fa-caret-down"></i>
						</a>
						
						
						<c:if test="${sessionData.nav == 'Payroll' || sessionData.nav == 'My Payroll'}">
							<ul class="collapses" style="display: none">
							 <c:if test="${SourceLeadWeb_orgLocations.country == 'United States'}">
								<li class="${sessionData.nav == 'Payroll' ? 'treview-active' : ''}"><a href="<c:url value='${appPath}/payrollList'/>" title="Run Payroll">Run Payroll</a></li>
								<li><a href="<c:url value='${appPath}/userPay'/>" title="Legacy Payroll">Legacy Payroll</a></li>
								<li><a href="<c:url value='${appPath}/myPayroll'/>" title="My Payroll">My Payroll</a></li>
							</c:if>
							
							<c:if test="${SourceLeadWeb_orgLocations.country == 'India'}">
								<li class="${sessionData.nav == 'Payroll' ? 'treview-active' : ''}"><a href="#" title="My Pays">My Pays</a></li>
								<li><a href="#" title="Payroll">Payroll</a></li>
							</c:if>
							</ul>
						</c:if>
						
						<c:if test="${sessionData.nav != 'Payroll' || sessionData.nav != 'My Payroll'}">
							<ul class="collapses" style="display: none">
							<c:if test="${SourceLeadWeb_orgLocations.country == 'United States'}">
								<li class="${sessionData.nav == 'Payroll' ? 'treview-active' : ''}"><a href="<c:url value='${appPath}/payrollList'/>" title="Run Payroll">Run Payroll</a></li>
								<li><a href="<c:url value='${appPath}/userPay'/>" title="Legacy Payroll">Legacy Payroll</a></li>
								<li><a href="<c:url value='${appPath}/myPayroll'/>" title="My Payroll">My Payroll</a></li>
							</c:if>
							
							<c:if test="${SourceLeadWeb_orgLocations.country == 'India'}">
								<li class="${sessionData.nav == 'Payroll' ? 'treview-active' : ''}"><a href="#" title="My Pays"> My Pays</a></li>
								<li><a href="#" title="Payroll">Payroll</a></li>
							</c:if>
							</ul>
						</c:if>
						
						</li>
					</c:if>
					
					<c:if test="${SourceLeadWeb_paryrollTabCount < 2 && userTabs.tabName == 'Run Payroll'}">
							<c:if test="${SourceLeadWeb_orgLocations.country == 'United States'}">
								<li class="${sessionData.nav == 'Payroll' ? 'side-nav-active' : ''}"><a href="<c:url value='${appPath}/payrollList'/>" title="Run Payroll"><span><i class="fa fa-usd"></i></span><i class="subMenu">Run Payroll</i></a></li>
							</c:if>
							
							<c:if test="${SourceLeadWeb_orgLocations.country == 'India'}">
								<li class="${sessionData.nav == 'Payroll' ? 'side-nav-active' : ''}"><a href="#" title="Payroll"><span><i class="fa fa-usd"></i></span><i class="subMenu">Payroll</i></a></li>
							</c:if>
					</c:if>
					
					<c:if test="${SourceLeadWeb_paryrollTabCount < 2 && userTabs.tabName == 'My Payroll'}">
							<c:if test="${SourceLeadWeb_orgLocations.country == 'United States'}">
								<li class="${sessionData.nav == 'My Payroll' ? 'side-nav-active' : ''}"><a href="<c:url value='${appPath}/userPay'/>" title="Leagacy Payroll"><span><i class="fa fa-usd"></i></span><i class="subMenu">Legacy Payroll</i></a></li>
								<li class="${sessionData.nav == 'My Payroll' ? 'side-nav-active' : ''}"><a href="<c:url value='${appPath}/myPayroll'/>" title="My Payroll"><span><i class="fa fa-usd"></i></span><i class="subMenu">My Payroll</i></a></li>
							</c:if>
							
							<c:if test="${SourceLeadWeb_orgLocations.country == 'India'}">
								<li class="${sessionData.nav == 'My Payroll' ? 'side-nav-active' : ''}"><a href="#" title="My Pays"><span><i class="fa fa-usd"></i></span><i class="subMenu">My Pays</i></a></li>
							</c:if>
					</c:if>

		       	   <%-- <c:if test="${userTabs.tabName == 'Payroll' && SourceLeadWeb_loginUserRole != 'ROLE_USER'}">
		             <li class="${sessionData.nav == 'Payroll' ? 'side-nav-active' : ''}"><a href="<c:url value='${appPath}/payrollList'/>" title="Payroll"><span><i class="fa fa-usd"></i></span><i class="subMenu">Payroll</i></a></li>
		          	</c:if>	
		          	
		          	<c:if test="${userTabs.tabName == 'Payroll' && SourceLeadWeb_loginUserRole != 'ROLE_ADMIN'}">
		              <li class="${sessionData.nav == 'Pay' ? 'side-nav-active' : ''}"><a href="<c:url value='${appPath}/userPay'/>" title="Pay"><span><i class="fa fa-usd"></i></span><i class="subMenu">Pay</i></a></li>
		          	</c:if>	 --%>

					<c:if test="${userTabs.tabName == 'Immigrations'}">
					 <sec:authorize access="hasAnyRole('ROLE_ADD_PETITION','ROLE_EDIT_VIEW_PETITION','ROLE_DELETE_PETITION','ROLE_VIEW_MY_PETITION')"> 
					

							<li class="${sessionData.nav == 'Immigrations' || sessionData.nav == 'MyImmigration' ? 'side-nav-active' : ''}">
							<a href="#" class="expencesLi" title="Immigration">
									<span><i class="fa fa-plane"></i></span> <i class="subMenu">Immigration</i>
									<i class="fa fa-fw fa-caret-down"></i>
							</a>

								<ul class="collapses" style="display: none">
								
								<li class="${sessionData.nav == 'Immigrations' ? 'treview-active' : ''}"><a	href="<c:url value='${appPath}/immigration/viewImmigrationList'/>" title="Immigrations">Immigrations</a></li>
								
								<li class="${sessionData.nav == 'MyImmigration' ? 'treview-active' : ''}"><a	href="<c:url value='${appPath}/immigration/myImmigration'/>" title="My Immigration">My Immigration</a></li>
									
								</ul>
								
								</li>
								</sec:authorize>
						</c:if>
				    <c:if test="${userTabs.tabName == 'Leave Management'}">
		             <li class="${sessionData.nav == 'Leave Management' ? 'side-nav-active' : ''}"><a href="<c:url value='${appPath}/leaveManagementReports'/>" title="Leave Management"><span><i class="fa fa-calendar"></i></span><i class="subMenu">Leave Management</i></a></li>
		          	</c:if>	
          </c:forEach>
          	
          	  <c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_SOURCELEAD_ADMIN'}">
          			 <li class="${sessionData.nav == 'Taxes' ? 'side-nav-active' : ''}"><a href="<c:url value='${appPath}/stateTax/list'/>" title="Taxes"><span><i class="fa fa-dashboard"></i></span><i class="subMenu">Taxes</i></a></li>
       	 	  </c:if>	
	            	
			
          	
        </ul>
      </div>
    </div>
    </div>
    

 
  </aside>
  <!-- Side Nav Bar -->
  
  <script>
$(function(){
	resizeFunction();
});
function resizeFunction() {
	var sidenavHeight = $(window).innerHeight();
	$(".sidenav_inner").height(sidenavHeight-75);
}
$(window).resize(function(){
	resizeFunction();
});

var sessionDataNav = "${sessionData.nav}";

function goToJobs(url){
	   $.ajax({
           url: url,
           type: "GET",
           success: function (result) {
        	   $("#BodyConetentDiv").empty();
		    	  $("#BodyConetentDiv").html(result);
		    	  requirementListCheck('','','','http://localhost:8080/app');
                  console.log(" inside success function ==========>")
               	
           }
       })
}

</script>
  
  