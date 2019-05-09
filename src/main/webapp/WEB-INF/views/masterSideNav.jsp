<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<!-- Side Nav Bar -->
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


  <aside class="">
	<nav class="newMenu">
		<ul>
        
        <c:if test="${SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_PROFESSIONAL' && SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_RECRUITER' && SourceLeadWeb_prodSupportLoginUserRole == null}">
        <li class="${sessionData.nav == 'Dashboard' ? 'navActive' : ''}"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>" title="Dashboard"><span class="navIcons"></span><b>Dashboard</b></a></li>
        </c:if>
        
         <c:if test="${SourceLeadWeb_prodSupportLoginUserRole == 'ROLE_PRODUCTION_SUPPORT'}">
        <li class="${sessionData.nav == 'Dashboard' ? 'navActive' : ''}"><a href="<c:url value='${appPath}/productionSupportdashboard/${SourceLeadWeb_orgLocationId}/${SourceLeadWeb_prodSupportOrgLocationId}'/>" title="Dashboard"><span class="navIcons"></span><b>Dashboard</b></a></li>
        </c:if>
        
        <c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_PROFESSIONAL'}">

        <li class="${sessionData.nav == 'Dashboard' ? 'navActive' : ''}"><a href="<c:url value='${appPath}/professionalDashboard/${SourceLeadWeb_orgLocationId}/${SourceLeadWeb_displayName}'/>" title="Dashboard"><span class="navIcons"></span><b>Dashboard</b></a></li>
        </c:if> 
        
        <c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_RECRUITER'}">
        <li class="${sessionData.nav == 'Dashboard' ? 'navActive' : ''}"><a href="<c:url value='${appPath}/recruiterDashboard/${SourceLeadWeb_orgLocationId}/${SourceLeadWeb_displayName}'/>" title="Dashboard"><span class="navIcons"></span><b>Dashboard</b></a></li>
 		</c:if>
 		
 		<sec:authorize access="hasAnyRole('ROLE_ENROLL_ORG')"> 
	        <li class="${sessionData.nav == 'OrgSettings' ? 'navActive' : ''}"><a href="<c:url value='${appPath}/enrolledOrgsList'/>" title="Orgs"><span class="navIcons orgIcon"></span><b>Orgs</b></a></li>
        </sec:authorize>
          
          <c:set var="timesheetTabExist" scope="session" value="N"/>
          <c:forEach var="userTabs" items="${SourceLeadWeb_tabsList}">
                    <c:if test="${userTabs.tabName == 'Clients'}">
                 	  <li class="${sessionData.nav == 'Clients' ? 'navActive' : ''}"><a href="<c:url value='${appPath}/clientDashboard'/>" title="Clients"><span class="navIcons assIcon"></span><b>Clients</b></a></li>
                    </c:if>
	                <c:if test="${userTabs.tabName == 'Jobs'}"> 
	                <c:if test="${SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_PROFESSIONAL' && SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_RECRUITER'}"> 
	                   <li class="${sessionData.nav == 'Requirement' ? 'navActive' : ''}"><a href="<c:url value='${appPath}/jobs'/>" title="Jobs"><span class="navIcons jobIcon"></span><b>Jobs</b></a></li>
	                </c:if> 
	                
	                <c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_PROFESSIONAL' || SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_RECRUITER' }">
	                 <li class="${sessionData.nav == 'Requirement' ? 'navActive' : ''}"><a href="<c:url value='${appPath}/professionalRequirements'/>" title="Jobs"><span class="navIcons jobIcon"></span><b>Jobs</b></a></li>
	                </c:if>
	                </c:if>
	                
	                
	                
	                <c:if test="${userTabs.tabName == 'Candidates'}">
	                 <c:if test="${SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_RECRUITER'}">
	                   <li class="${sessionData.nav == 'Candidates' ? 'navActive' : ''}"><a href="<c:url value='${appPath}/candidatesList'/>" title="Candidates"><span class="navIcons canIcon"></span><b>Candidates</b></a></li>
	                 </c:if>
	                 <c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_RECRUITER'}">
	                   <li class="${sessionData.nav == 'Candidates' ? 'navActive' : ''}"><a href="<c:url value='${appPath}/candidatesList'/>" title="Candidates"><span class="navIcons canIcon"></span><b>Candidates</b></a></li>
	                 </c:if>
	                </c:if> 
	                
                
	                <c:if test="${userTabs.tabName == 'Vendors'}">
	                   <li class="${sessionData.nav == 'Vendors' ? 'navActive' : ''}"><a href="<c:url value='${appPath}/vendorsList'/>" title="Vendors"><span class="navIcons venIcon"></span><b>Vendors</b></a></li>
	                </c:if> 
	                 
	                 <c:if test="${userTabs.tabName == 'Associates'}">
	                 <li class="${sessionData.nav == 'Users' ? 'navActive' : ''}"><a href="<c:url value='${appPath}/usersList'/>" title="Associates"><span class="navIcons cliIcon"></span><b>Associates</b></a></li>
	                 </c:if>
	                 
	                 <c:if test="${userTabs.tabName == 'Projects'}">
	                	  <li class="${sessionData.nav == 'Projects' ? 'navActive' : ''}"><a href="<c:url value='${appPath}/projectDashboard'/>" title="Projects"><span class="navIcons projIcon"></span><b>Projects</b></a></li>
	                 </c:if>
	                 
	                 <c:if test="${timesheetTabExist == 'N' && (userTabs.tabName == 'My Timesheets' || userTabs.tabName == 'Timesheet Approval')}">
	                 <sec:authorize access="hasAnyRole('ROLE_FILL_MEMBER_TIMESHEETS','ROLE_VIEW_MEMBER_TIMESHEETS','ROLE_EXPORT_MEMBER_TIMESHEETS') && hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_EXPORT_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS')"> 
				           <li class="${sessionData.nav == 'TimeSheet' || sessionData.nav == 'TimeSheetApproval' ? 'navActive' : ''}">
							 							  
							  <a href="#" class="timesheetLi" title="Timesheets"><span class="navIcons timIcon"></span><b>Timesheets</b>
							  <i class="fa fa-fw fa-caret-down"></i>
							  </a>
							 
							  <c:if test="${sessionData.nav == 'TimeSheet' || sessionData.nav == 'TimeSheetApproval'}">
							   <c:set var ="timesheetTabExist" scope="session" value="Y"/> 
							  <ul class="collapses" style="display: none">
							  	    <li class="${sessionData.nav == 'TimeSheet' ? 'treview-active' : ''}"><a href="<c:url value='${appPath}/timesheet'/>" title="My TimeSheets"><i class="fa fa-chevron-right"></i> Self</a></li>
									<li class="${sessionData.nav == 'TimeSheetApproval' ? 'treview-active' : ''}"><a href="<c:url value='${appPath}/timesheetApproval'/>" title="TimeSheet Approval"><i class="fa fa-chevron-right"></i> Employees</a></li>
							  </ul>
							  </c:if>
							  <c:if test="${sessionData.nav != 'TimeSheet' || sessionData.nav != 'TimeSheetApproval'}">
							  <c:set var ="timesheetTabExist" scope="session" value="Y"/>
							  <ul class="collapses" style="display: none">
							  		<li class="${sessionData.nav == 'TimeSheet' ? 'treview-active' : ''}"><a href="<c:url value='${appPath}/timesheet'/>" title="My TimeSheets"><i class="fa fa-chevron-right"></i> Self</a></li>
									<li class="${sessionData.nav == 'TimeSheetApproval' ? 'treview-active' : ''}"><a href="<c:url value='${appPath}/timesheetApproval'/>" title="TimeSheet Approval"><i class="fa fa-chevron-right"></i> Employees</a></li> 
							  </ul>
							  </c:if>
						  </li>
					  </sec:authorize>
	                 </c:if>
	                 <c:if test="${userTabs.tabName == 'My Timesheets'}">
	                 <sec:authorize access="hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_EXPORT_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS') && !hasAnyRole('ROLE_FILL_MEMBER_TIMESHEETS','ROLE_VIEW_MEMBER_TIMESHEETS','ROLE_EXPORT_MEMBER_TIMESHEETS')">
	                       <li class="${sessionData.nav == 'TimeSheet' ? 'navActive' : ''}"><a href="<c:url value='${appPath}/timesheet'/>"><span class="navIcons timIcon"></span><b>TimeSheets</b></a></li>
	                 </sec:authorize>
	                 </c:if>
	                 <c:if test="${userTabs.tabName == 'Timesheet Approval'}">
	                 <sec:authorize access="!hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_EXPORT_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS') && hasAnyRole('ROLE_FILL_MEMBER_TIMESHEETS','ROLE_VIEW_MEMBER_TIMESHEETS','ROLE_EXPORT_MEMBER_TIMESHEETS')">
	                       <li class="${sessionData.nav == 'TimeSheetApproval' ? 'navActive' : ''}"><a href="<c:url value='${appPath}/timesheetApproval'/>"><span class="navIcons timIcon"></span><b>TimeSheets</b></a></li>
	                 </sec:authorize>
	                 </c:if>
	                 
	                 <c:if test="${userTabs.tabName == 'Finance' }">
				           <li class="${sessionData.nav == 'Finance' || sessionData.nav == 'AssetManagement' ? 'navActive' : ''}">
							 							  
							  <a href="#" class="backofficeLi" title="Back Office">
								<span class="navIcons backofIcon"></span><b>Back Office</b><i class="fa fa-fw fa-caret-down"></i>
							  </a>
							  
							   <c:if test="${sessionData.nav == 'Finance' || sessionData.nav == 'AssetManagement'}">
                              <ul class="collapses" style="display: none">
                                  <li class="${sessionData.nav == 'Finance' ? 'treview-active' : ''}"><a href="<c:url value='${appPath}/finance'/>" title="Finance"><i class="fa fa-chevron-right"></i> Finance</a></li>
                                    <li class="${sessionData.nav == 'AssetManagement' ? 'treview-active' : ''}"><a href="<c:url value='${appPath}/assetManagement'/>" title="Asset Management"><i class="fa fa-chevron-right"></i> Asset Management</a></li>
                                    <li><a href="<c:url value='${appPath}/quickBookInvoice'/>" title="QuickBook Invoice"><i class="fa fa-chevron-right"></i> QuickBook Customers</a></li>
                              </ul>
                              </c:if>
                              <c:if test="${sessionData.nav != 'Finance' || sessionData.nav != 'AssetManagement'}">
                              <ul class="collapses" style="display: none">
                                  <li class="${sessionData.nav == 'BackOffice' ? 'treview-active' : ''}"><a href="<c:url value='${appPath}/finance'/>" title="Finance"><i class="fa fa-chevron-right"></i> Finance</a></li>
                                    <li class="${sessionData.nav == 'AssetManagement' ? 'treview-active' : ''}"><a href="<c:url value='${appPath}/assetManagement'/>" title="Asset Management"><i class="fa fa-chevron-right"></i> Asset Management</a></li>
                                      <li><a href="<c:url value='${appPath}/quickBookInvoice'/>" title="QuickBook Invoice"><i class="fa fa-chevron-right"></i> QuickBook Customers</a></li>
                              
                              </ul>
                              </c:if>
                              
						  </li>
	                 </c:if>
	                 
	                 
	                 <c:if test="${userTabs.tabName == 'On & Off Boarding'}">
	                  <li class="${sessionData.nav == 'OnAndOffboard' ? 'navActive' : ''}"><a href="<c:url value='${appPath}/candidateOnBoardingList'/>" title="On / Off Boarding"><span class="navIcons onoffIcon"></span><b>On / Off Board</b></a></li>
	                 </c:if>
	                 
	                 
	                 
	                 <c:if test="${userTabs.tabName == 'Contracts'}">
	                   <li class="${sessionData.nav == 'Contract' ? 'navActive' : ''}"><a href="<c:url value='${appPath}/contractListbyStatus'/>" title="Contracts"><span class="navIcons contIcon"></span><b>Contracts</b></a></li>
	                </c:if> 
	                
	                <c:if test="${SourceLeadWeb_billingTabsCount == 2 && userTabs.tabName == 'Receivable'}">
	                   <li class="${sessionData.nav == 'Receivable' || sessionData.nav == 'Payables' ? 'navActive' : ''}">
														  
							  <a href="#" class="billingLi" title="Finance">
								<span class="navIcons finIcon"></span><b>Finance</b> <i class="fa fa-fw fa-caret-down"></i>
							  </a>
							  
						   
						   <c:if test="${sessionData.nav == 'Receivable' || sessionData.nav == 'Payables'}">
                              <ul class="collapses" style="display: none">
                                  <li class="${sessionData.nav == 'Receivable' ? 'treview-active' : ''}"><a href="<c:url value='${appPath}/invoiceList'/>" title="Invoices"><i class="fa fa-chevron-right"></i> Invoices</a></li>
                                  <li class="${sessionData.nav == 'Payables' ? 'treview-active' : ''}"><a href="<c:url value='${appPath}/payablesList'/>" title="Bills"><i class="fa fa-chevron-right"></i> Bills</a></li>
                              </ul>
                           </c:if>
                              
                           <c:if test="${sessionData.nav != 'Receivable' || sessionData.nav != 'Payables'}">
                           <ul class="collapses" style="display: none">
                               <li class="${sessionData.nav == 'Receivable' ? 'treview-active' : ''}"><a href="<c:url value='${appPath}/invoiceList'/>" title="Invoices"><i class="fa fa-chevron-right"></i> Invoices</a></li>
                               <li class="${sessionData.nav == 'Payables' ? 'treview-active' : ''}"><a href="<c:url value='${appPath}/payablesList'/>" title="Bills"><i class="fa fa-chevron-right"></i> Bills</a></li>
                           </ul>
                           </c:if>
                              
						  </li>
	                 </c:if>
	                 
	                 
	                  <c:if test="${SourceLeadWeb_billingTabsCount < 2 && userTabs.tabName == 'Receivable'}">
	                   <li class="${sessionData.nav == 'Receivable' ? 'navActive' : ''}"><a href="<c:url value='${appPath}/invoiceList'/>" title="Invoices"><span class="navIcons invoIcon"></span><b>Invoices</b></a></li>
	                 </c:if>
	                 
	                 <c:if test="${SourceLeadWeb_billingTabsCount < 2 && userTabs.tabName == 'Payables'}">
	                 <li class="${sessionData.nav == 'Payables' ? 'navActive' : ''}"><a href="<c:url value='${appPath}/payablesList'/>" title="Bills"><span class="navIcons billIcon"></span><b>Bills</b></a></li>
	                 </c:if>
	                 
	                 
	                 
	                 
	                 <c:if test="${SourceLeadWeb_expensesTabCount == 2 && userTabs.tabName == 'My Expenses'}">
	                   <li class="${sessionData.nav == 'My Expenses' || sessionData.nav == 'Expenses Approval' ? 'navActive' : ''}">
														  
							  <a href="#" class="expencesLi" title="Expenses">
								<span class="navIcons expIcon"></span><b>Expenses</b> <i class="fa fa-fw fa-caret-down"></i>
							  </a>
							  
						   
						   <c:if test="${sessionData.nav == 'My Expenses' || sessionData.nav == 'Expenses Approval'}">
                              <ul class="collapses" style="display: none">
                                  <li class="${sessionData.nav == 'My Expenses' ? 'treview-active' : ''}"><a href="<c:url value='${appPath}/expenses'/>" title="My Expenses"><i class="fa fa-chevron-right"></i> Self</a></li>
                                  <li class="${sessionData.nav == 'Expenses Approval' ? 'treview-active' : ''}"><a href="<c:url value='${appPath}/expensesApprovalList'/>" title="Expenses Approval"><i class="fa fa-chevron-right"></i> Employees</a></li>
                              </ul>
                           </c:if>
                              
                           <c:if test="${sessionData.nav != 'My Expenses' || sessionData.nav != 'Expenses Approval'}">
                           <ul class="collapses" style="display: none">
                               <li class="${sessionData.nav == 'My Expenses' ? 'treview-active' : ''}"><a href="<c:url value='${appPath}/expenses'/>" title="My Expenses"><i class="fa fa-chevron-right"></i> Self</a></li>
                               <li class="${sessionData.nav == 'Expenses Approval' ? 'treview-active' : ''}"><a href="<c:url value='${appPath}/expensesApprovalList'/>" title="Expenses Approval"><i class="fa fa-chevron-right"></i> Employees</a></li>
                           </ul>
                           </c:if>
                              
						  </li>
	                 </c:if>
	                 
	                 <c:if test="${SourceLeadWeb_expensesTabCount < 2 && userTabs.tabName == 'My Expenses'}">
	                   <li class="${sessionData.nav == 'My Expenses' ? 'navActive' : ''}"><a href="<c:url value='${appPath}/expenses'/>" title="My Expenses"><span class="navIcons expIcon"></span><b>Expenses</b></a></li>
	                 </c:if>
	                 
	                 <c:if test="${SourceLeadWeb_expensesTabCount < 2 && userTabs.tabName == 'Expenses Approval'}">
	                 <li class="${sessionData.nav == 'Expenses Approval' ? 'navActive' : ''}"><a href="<c:url value='${appPath}/expensesApprovalList'/>" title="Expenses Approval"><span class="navIcons expIcon"></span><b>Expenses Approval</b></a></li>
	                 </c:if>
	                 
	                 <c:if test="${userTabs.tabName == 'Asset Management'}">
	                   <li class="${sessionData.nav == 'AssetManagement' ? 'navActive' : ''}"><a href="<c:url value='${appPath}/assetManagement'/>" title="Asset Management"><span class="navIcons asstIcon"></span><b>Asset Management</b></a></li>
	                </c:if> 
	                
	                <c:if test="${userTabs.tabName == 'Support'}">
	                   <li class="${sessionData.nav == 'Support' ? 'navActive' : ''}"><a href="<c:url value='${appPath}/sourceleadTicketList'/>" title="Support"><span class="navIcons supIcon"></span><b>Support</b></a></li>
	                </c:if> 
	                
	                <c:if test="${SourceLeadWeb_resourcesTabCount == 2 && userTabs.tabName == 'Blog'}">
	                   <li class="${sessionData.nav == 'Blogs' || sessionData.nav == 'UserManuals' ? 'navActive' : ''}">
														  
							  <a href="#" class="resourcesli" title="Resources">
								<span class="navIcons resIcon"></span><b>Resources</b> <i class="fa fa-fw fa-caret-down"></i>
							  </a>
						   <c:if test="${sessionData.nav == 'Blogs' || sessionData.nav == 'UserManuals'}">
                              <ul class="collapses" style="display: none">
                                  <li class="${sessionData.nav == 'Blogs' ? 'treview-active' : ''}"><a href="<c:url value='${appPath}/blogs'/>" title="Blog"><i class="fa fa-chevron-right"></i> Blog</a></li>
                                  <li class="${sessionData.nav == 'UserManuals' ? 'treview-active' : ''}"><a href="<c:url value='${appPath}/userManuals'/>" title="User Manuals"><i class="fa fa-chevron-right"></i> User Manuals</a></li>
                                  <li class=""><a href="<c:url value='${appPath}/releaseNote'/>" title="Realease Note"><i class="fa fa-chevron-right"></i> Release Note</a></li>
                              </ul>
                           </c:if>
                              
                           <c:if test="${sessionData.nav != 'Blogs' || sessionData.nav != 'UserManuals'}">
                           <ul class="collapses" style="display: none">
                               <li class="${sessionData.nav == 'Blogs' ? 'treview-active' : ''}"><a href="<c:url value='${appPath}/blogs'/>" title="Blog"><i class="fa fa-chevron-right"></i> Blog</a></li>
                               <li class="${sessionData.nav == 'UserManuals' ? 'treview-active' : ''}"><a href="<c:url value='${appPath}/userManuals'/>" title="User Manuals"><i class="fa fa-chevron-right"></i> User Manuals</a></li>
                               <li class=""><a href="<c:url value='${appPath}/releaseNote'/>" title="Realease Note"><i class="fa fa-chevron-right"></i> Release Note</a></li>
                               
                           </ul>
                           </c:if>
                           
                              
						  </li>
	                 </c:if>
	                 
	                 <c:if test="${SourceLeadWeb_resourcesTabCount < 2}">
	                 
	                 <c:if test="${userTabs.tabName == 'Blog'}">
	                    <li class="${sessionData.nav == 'Blogs' ? 'navActive' : ''}"><a href="<c:url value='${appPath}/blogs'/>" title="Blogs"><span class="navIcons blogIcon"></span><b>Blogs</b></a></li>
	                 </c:if>
	                 
	                 <c:if test="${userTabs.tabName == 'User Manuals'}">
	                    <li class="${sessionData.nav == 'UserManuals' ? 'navActive' : ''}"><a href="<c:url value='${appPath}/userManuals'/>" title="User Manuals"><span class="navIcons usermanuIcon"></span><b>User Manuals</b></a></li>
	                 </c:if>
	                      
	                 </c:if>
	                
	                
	                <c:if test="${userTabs.tabName == 'Campaigns'}">
                         <li class="${sessionData.nav == 'Campaigns' || sessionData.nav == 'CampaignSender' || sessionData.nav == 'CampaignRecipient' ? 'navActive' : ''}"><a href="<c:url value='${appPath}/campaigns/hotcampaigns'/>" title="Campaigns"><span class="navIcons campIcon"></span><b>Campaigns</b></a></li>				  
			         </c:if>

					<c:if test="${SourceLeadWeb_paryrollTabCount == 2 && userTabs.tabName == 'Run Payroll'}">
						<li class="${sessionData.nav == 'Run Payroll' || sessionData.nav == 'My Payroll' ? 'navActive' : ''}">
						
						<a href="#" class="payrollli" title="Pay Roll">
						<span class="navIcons payIcon"></span><b>Pay Roll</b> <i class="fa fa-fw fa-caret-down"></i>
						</a>
						
						
						<c:if test="${sessionData.nav == 'Payroll' || sessionData.nav == 'My Payroll'}">
							<ul class="collapses" style="display: none">
							 <c:if test="${SourceLeadWeb_orgLocations.country == 'United States'}">
								<li class="${sessionData.nav == 'Payroll' ? 'treview-active' : ''}"><a href="<c:url value='${appPath}/uSPayrollDashboard'/>" title="Run Payroll"><i class="fa fa-chevron-right"></i> Run Payroll</a></li>
								<li><a href="<c:url value='${appPath}/userPay'/>" title="Legacy Payroll"><i class="fa fa-chevron-right"></i>Legacy Payroll</a></li>
								<li><a href="<c:url value='${appPath}/myPayroll'/>" title="My Payroll"><i class="fa fa-chevron-right"></i>My Payroll</a></li>
							</c:if>
							
							<c:if test="${SourceLeadWeb_orgLocations.country == 'India'}">
								<li class="${sessionData.nav == 'MyPayroll' ? 'treview-active' : ''}"><a href="<c:url value='${appPath}/myPayrollInd'/>" title="My Pays"><i class="fa fa-chevron-right"></i>My Payroll</a></li>
								<li class="${sessionData.nav == 'RunPayroll' ? 'treview-active' : ''}"><a href="<c:url value='${appPath}/runIndianPayRoll'/>" title="Payroll"><i class="fa fa-chevron-right"></i>Org Payroll</a></li>
							</c:if>
							</ul>
						</c:if>
						
						<c:if test="${sessionData.nav != 'Payroll' || sessionData.nav != 'My Payroll'}">
							<ul class="collapses" style="display: none">
							<c:if test="${SourceLeadWeb_orgLocations.country == 'United States'}">
								<li class="${sessionData.nav == 'Payroll' ? 'treview-active' : ''}"><a href="<c:url value='${appPath}/uSPayrollDashboard'/>" title="Run Payroll"><i class="fa fa-chevron-right"></i> Run Payroll</a></li>
								<li><a href="<c:url value='${appPath}/userPay'/>" title="Legacy Payroll"><i class="fa fa-chevron-right"></i> Legacy Payroll</a></li>
								<li><a href="<c:url value='${appPath}/myPayroll'/>" title="My Payroll"><i class="fa fa-chevron-right"></i> My Payroll</a></li>
							</c:if>
							
							<c:if test="${SourceLeadWeb_orgLocations.country == 'India'}">
								<li class="${sessionData.nav == 'MyPayroll' ? 'treview-active' : ''}"><a href="<c:url value='${appPath}/myPayrollInd'/>" title="My Pays"><i class="fa fa-chevron-right"></i>My Payroll</a></li>
								<li class="${sessionData.nav == 'RunPayroll' ? 'treview-active' : ''}"><a href="<c:url value='${appPath}/runIndianPayRoll'/>" title="Payroll"><i class="fa fa-chevron-right"></i>Org Payroll</a></li>
							</c:if>
							</ul>
						</c:if>
						
						</li>
					</c:if>
					
					<c:if test="${SourceLeadWeb_paryrollTabCount < 2 && userTabs.tabName == 'Run Payroll'}">
							<c:if test="${SourceLeadWeb_orgLocations.country == 'United States'}">
								<li class="${sessionData.nav == 'Payroll' ? 'navActive' : ''}"><a href="<c:url value='${appPath}/uSPayrollDashboard'/>" title="Run Payroll"><span class="navIcons payIcon"></span><b>Run Payroll</b></a></li>
							</c:if>
							
							<c:if test="${SourceLeadWeb_orgLocations.country == 'India'}">
								<li class="${sessionData.nav == 'RunPayroll' ? 'navActive' : ''}"><a href="<c:url value='${appPath}/runIndianPayRoll'/>" title="Payroll"><span class="navIcons payIcon"></span><b>Org Payroll</b></a></li>
							</c:if>
					</c:if>
					
					<c:if test="${SourceLeadWeb_paryrollTabCount < 2 && userTabs.tabName == 'My Payroll'}">
							<c:if test="${SourceLeadWeb_orgLocations.country == 'United States'}">
								<li class="${sessionData.nav == 'My Payroll' ? 'navActive' : ''}"><a href="<c:url value='${appPath}/userPay'/>" title="Leagacy Payroll"><span class="navIcons payIcon"></span><b>Legacy Payroll</b></a></li>
								<li class="${sessionData.nav == 'My Payroll' ? 'navActive' : ''}"><a href="<c:url value='${appPath}/myPayroll'/>" title="My Payroll"><span class="navIcons payIcon"></span><b>My Payroll</b></a></li>
							</c:if>
							
							<c:if test="${SourceLeadWeb_orgLocations.country == 'India'}">
								<li class="${sessionData.nav == 'MyPayroll' ? 'navActive' : ''}"><a href="<c:url value='${appPath}/myPayrollInd'/>" title="My Pays"><span class="navIcons payIcon"></span><b>My Payroll</b></a></li>
							</c:if>
					</c:if>

					<c:if test="${userTabs.tabName == 'Immigrations'}">
					 <sec:authorize access="hasAnyRole('ROLE_ADD_PETITION','ROLE_EDIT_VIEW_PETITION','ROLE_DELETE_PETITION','ROLE_VIEW_MY_PETITION')"> 
					

							<li class="${sessionData.nav == 'Immigrations' || sessionData.nav == 'MyImmigration' ? 'navActive' : ''}">
							<a href="#" class="expencesLi" title="Immigration">
									<span class="navIcons immIcon"></span><b>Immigration</b><i class="fa fa-fw fa-caret-down"></i>
							</a>

								<ul class="collapses" style="display: none">
								
									<li class="${sessionData.nav == 'MyImmigration' ? 'treview-active' : ''}"><a	href="<c:url value='${appPath}/immigration/myImmigration'/>" title="My Immigration"><i class="fa fa-chevron-right"></i> Self</a></li>
									<li class="${sessionData.nav == 'Immigrations' ? 'treview-active' : ''}"><a	href="<c:url value='${appPath}/immigration/viewImmigrationList'/>" title="Immigrations"><i class="fa fa-chevron-right"></i> Employees</a></li>
									
								</ul>
								
								</li>
								</sec:authorize>
						</c:if>
				    <c:if test="${userTabs.tabName == 'Leave Management'}">
		             <li class="${sessionData.nav == 'Leave Management' ? 'navActive' : ''}"><a href="<c:url value='${appPath}/leaveManagementReports'/>" title="Leave Management"><span class="navIcons leaveIcon"></span><b>Leave Management</b></a></li>
		          	</c:if>	
          </c:forEach>
	         
          	  <c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_SOURCELEAD_ADMIN'}">
          			 <li class="${sessionData.nav == 'Taxes' ? 'navActive' : ''}"><a href="<c:url value='${appPath}/stateTax/list'/>" title="Taxes"><span class="navIcons finIcon"></span><b>Taxes</b></a></li>
       	 	  </c:if>	
	     
	     <%-- <li class="${sessionData.nav == 'Taxes' ? 'navActive' : ''}"><a href="${appPath}/performanceGoalsList" title="Goals"><span class="navIcons finIcon"></span><b>Goals</b></a></li> --%>       	
			
          	
        </ul>
        </nav>
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
  
  