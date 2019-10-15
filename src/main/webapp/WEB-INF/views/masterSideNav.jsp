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
        <li class="${sessionData.nav == 'Dashboard' ? 'navActive' : ''}"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}" title="Dashboard"><span class="navIcons"></span><b>Dashboard</b></a></li>
        </c:if>
        
         <c:if test="${SourceLeadWeb_prodSupportLoginUserRole == 'ROLE_PRODUCTION_SUPPORT'}">
        <li class="${sessionData.nav == 'Dashboard' ? 'navActive' : ''}"><a href="${SourceLeadWeb_sourceleadUrl}/productionSupportdashboard/${SourceLeadWeb_orgLocationId}/${SourceLeadWeb_prodSupportOrgLocationId}" title="Dashboard"><span class="navIcons"></span><b>Dashboard</b></a></li>
        </c:if>
        
        <c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_PROFESSIONAL'}">

        <li class="${sessionData.nav == 'Dashboard' ? 'navActive' : ''}"><a href="${SourceLeadWeb_sourceleadUrl}/professionalDashboard/${SourceLeadWeb_orgLocationId}/${SourceLeadWeb_displayName}" title="Dashboard"><span class="navIcons"></span><b>Dashboard</b></a></li>
        </c:if> 
        
        <c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_RECRUITER'}">
        <li class="${sessionData.nav == 'Dashboard' ? 'navActive' : ''}"><a href="${SourceLeadWeb_sourceleadUrl}/recruiterDashboard/${SourceLeadWeb_orgLocationId}/${SourceLeadWeb_displayName}" title="Dashboard"><span class="navIcons"></span><b>Dashboard</b></a></li>
 		</c:if>
 		
 		<sec:authorize access="hasAnyRole('ROLE_ENROLL_ORG')"> 
	        <li class="${sessionData.nav == 'OrgSettings' ? 'navActive' : ''}"><a href="${SourceLeadWeb_sourceleadUrl}/enrolledOrgsList" title="Orgs"><span class="navIcons orgIcon"></span><b>Orgs</b></a></li>
        </sec:authorize>
          
          <c:set var="timesheetTabExist" scope="session" value="N"/>
          <c:forEach var="tabName" items="${SourceLeadWeb_tabsList}">
                    <c:if test="${tabName == 'Clients'}">
                 	  <li class="${sessionData.nav == 'Clients' ? 'navActive' : ''}"><a href="${SourceLeadWeb_vmsUrl}/clientDashboard" title="Clients"><span class="navIcons assIcon"></span><b>Clients</b></a></li>
                    </c:if>
	                <c:if test="${tabName == 'Jobs'}"> 
	                <c:if test="${SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_PROFESSIONAL' && SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_RECRUITER'}"> 
	                   <li class="${sessionData.nav == 'Requirement' ? 'navActive' : ''}"><a href="${SourceLeadWeb_atsUrl}/jobsList" title="Jobs"><span class="navIcons jobIcon"></span><b>Jobs</b></a></li>
	                </c:if> 
	                
	                <c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_PROFESSIONAL' || SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_RECRUITER' }">
	                 <li class="${sessionData.nav == 'Requirement' ? 'navActive' : ''}"><a href="${SourceLeadWeb_atsUrl}/professionalRequirements" title="Jobs"><span class="navIcons jobIcon"></span><b>Jobs</b></a></li>
	                </c:if>
	                </c:if>
	                
	                
	                
	                <c:if test="${tabName == 'Candidates'}">
	                 <c:if test="${SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_RECRUITER'}">
	                   <li class="${sessionData.nav == 'Candidates' ? 'navActive' : ''}"><a href="${SourceLeadWeb_atsUrl}/candidatesList" title="Candidates"><span class="navIcons canIcon"></span><b>Candidates</b></a></li>
	                 </c:if>
	                 <c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_RECRUITER'}">
	                   <li class="${sessionData.nav == 'Candidates' ? 'navActive' : ''}"><a href="${SourceLeadWeb_atsUrl}/candidatesList" title="Candidates"><span class="navIcons canIcon"></span><b>Candidates</b></a></li>
	                 </c:if>
	                </c:if> 
	                
                
	                <c:if test="${tabName == 'Vendors'}">
	                   <li class="${sessionData.nav == 'Vendors' ? 'navActive' : ''}"><a href="${SourceLeadWeb_vmsUrl}/vendorsList" title="Vendors"><span class="navIcons venIcon"></span><b>Vendors</b></a></li>
	                </c:if> 
	                 
	                 <c:if test="${tabName == 'Associates'}">
	                 <li class="${sessionData.nav == 'Users' ? 'navActive' : ''}"><a href="${SourceLeadWeb_atsUrl}/usersList" title="Associates"><span class="navIcons cliIcon"></span><b>Associates</b></a></li>

	                 </c:if>
	                 
	                 <c:if test="${tabName == 'Projects'}">
	                	  <li class="${sessionData.nav == 'Projects' ? 'navActive' : ''}"><a href="${SourceLeadWeb_pmsUrl}/projectDashboard" title="Projects"><span class="navIcons projIcon"></span><b>Projects</b></a></li>
	                 </c:if>
	                 
	                 <c:if test="${timesheetTabExist == 'N' && (tabName == 'My Timesheets' || tabName == 'Timesheet Approval')}">
	                 <sec:authorize access="hasAnyRole('ROLE_FILL_MEMBER_TIMESHEETS','ROLE_VIEW_MEMBER_TIMESHEETS','ROLE_EXPORT_MEMBER_TIMESHEETS') && hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_EXPORT_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS')"> 
				           <li class="${sessionData.nav == 'TimeSheet' || sessionData.nav == 'TimeSheetApproval' ? 'navActive' : ''}">
							 							  
							  <a href="#" class="timesheetLi" title="Timesheets"><span class="navIcons timIcon"></span><b>Timesheets</b>
							  <i class="fa fa-fw fa-caret-down"></i>
							  </a>
							 
							  <c:if test="${sessionData.nav == 'TimeSheet' || sessionData.nav == 'TimeSheetApproval'}">
							   <c:set var ="timesheetTabExist" scope="session" value="Y"/> 
							  <ul class="collapses" style="display: none">
							  	    <li class="${sessionData.nav == 'TimeSheet' ? 'treview-active' : ''}"><a href="${SourceLeadWeb_pmsUrl}/timesheet" title="My TimeSheets"><i class="fa fa-chevron-right"></i> Self</a></li>
									<li class="${sessionData.nav == 'TimeSheetApproval' ? 'treview-active' : ''}"><a href="${SourceLeadWeb_pmsUrl}/timesheetApproval" title="TimeSheet Approval"><i class="fa fa-chevron-right"></i> Employees</a></li>
							  </ul>
							  </c:if>
							  <c:if test="${sessionData.nav != 'TimeSheet' || sessionData.nav != 'TimeSheetApproval'}">
							  <c:set var ="timesheetTabExist" scope="session" value="Y"/>
							  <ul class="collapses" style="display: none">
							  		<li class="${sessionData.nav == 'TimeSheet' ? 'treview-active' : ''}"><a href="${SourceLeadWeb_pmsUrl}/timesheet" title="My TimeSheets"><i class="fa fa-chevron-right"></i> Self</a></li>
									<li class="${sessionData.nav == 'TimeSheetApproval' ? 'treview-active' : ''}"><a href="${SourceLeadWeb_pmsUrl}/timesheetApproval" title="TimeSheet Approval"><i class="fa fa-chevron-right"></i> Employees</a></li> 
							  </ul>
							  </c:if>
						  </li>
					  </sec:authorize>
	                 </c:if>
	                 <c:if test="${tabName == 'My Timesheets'}">
	                 <sec:authorize access="hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_EXPORT_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS') && !hasAnyRole('ROLE_FILL_MEMBER_TIMESHEETS','ROLE_VIEW_MEMBER_TIMESHEETS','ROLE_EXPORT_MEMBER_TIMESHEETS')">
	                       <li class="${sessionData.nav == 'TimeSheet' ? 'navActive' : ''}"><a href="${SourceLeadWeb_pmsUrl}/timesheet"><span class="navIcons timIcon"></span><b>TimeSheets</b></a></li>
	                 </sec:authorize>
	                 </c:if>
	                 <c:if test="${tabName == 'Timesheet Approval'}">
	                 <sec:authorize access="!hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_EXPORT_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS') && hasAnyRole('ROLE_FILL_MEMBER_TIMESHEETS','ROLE_VIEW_MEMBER_TIMESHEETS','ROLE_EXPORT_MEMBER_TIMESHEETS')">
	                       <li class="${sessionData.nav == 'TimeSheetApproval' ? 'navActive' : ''}"><a href="${SourceLeadWeb_pmsUrl}/timesheetApproval"><span class="navIcons timIcon"></span><b>TimeSheets</b></a></li>
	                 </sec:authorize>
	                 </c:if>
	                 
	                 <c:if test="${tabName == 'Finance' }">
				           <li class="${sessionData.nav == 'Finance' || sessionData.nav == 'AssetManagement' ? 'navActive' : ''}">
							 							  
							  <a href="#" class="backofficeLi" title="Back Office">
								<span class="navIcons backofIcon"></span><b>Back Office</b><i class="fa fa-fw fa-caret-down"></i>
							  </a>
							  
							   <c:if test="${sessionData.nav == 'Finance' || sessionData.nav == 'AssetManagement'}">
                              <ul class="collapses" style="display: none">
                                  <li class="${sessionData.nav == 'Finance' ? 'treview-active' : ''}"><a href="${SourceLeadWeb_vmsUrl}/finance" title="Finance"><i class="fa fa-chevron-right"></i> Finance</a></li>
                                    <li class="${sessionData.nav == 'AssetManagement' ? 'treview-active' : ''}"><a href="${SourceLeadWeb_atsUrl}/assetManagement" title="Asset Management"><i class="fa fa-chevron-right"></i> Asset Management</a></li>
                                    <li><a href="${SourceLeadWeb_sourceleadUrl}/quickBookInvoice" title="QuickBook Invoice"><i class="fa fa-chevron-right"></i> QuickBook Customers</a></li>
                              </ul>
                              </c:if>
                              <c:if test="${sessionData.nav != 'Finance' || sessionData.nav != 'AssetManagement'}">
                              <ul class="collapses" style="display: none">
                                  <li class="${sessionData.nav == 'BackOffice' ? 'treview-active' : ''}"><a href="${SourceLeadWeb_sourceleadUrl}/finance" title="Finance"><i class="fa fa-chevron-right"></i> Finance</a></li>
                                    <li class="${sessionData.nav == 'AssetManagement' ? 'treview-active' : ''}"><a href="${SourceLeadWeb_atsUrl}/assetManagement" title="Asset Management"><i class="fa fa-chevron-right"></i> Asset Management</a></li>
                                      <li><a href="${SourceLeadWeb_sourceleadUrl}/quickBookInvoice" title="QuickBook Invoice"><i class="fa fa-chevron-right"></i> QuickBook Customers</a></li>
                              
                              </ul>
                              </c:if>
                              
						  </li>
	                 </c:if>
	                 
	                 

	                 <c:if test="${tabName == 'On & Off Boarding'}">
	                  <li class="${sessionData.nav == 'OnAndOffboard' ? 'navActive' : ''}"><a href="${SourceLeadWeb_atsUrl}/candidateOnBoardingList" title="On / Off Boarding"><span class="navIcons onoffIcon"></span><b>On / Off Board</b></a></li>

	                 </c:if>
	                 
	                 
	                 
	                 <c:if test="${tabName == 'Contracts'}">
	                   <li class="${sessionData.nav == 'Contract' ? 'navActive' : ''}"><a href="${SourceLeadWeb_vmsUrl}/contractListbyStatus" title="Contracts"><span class="navIcons contIcon"></span><b>Contracts</b></a></li>
	                </c:if> 
	                
	                <c:if test="${SourceLeadWeb_billingTabsCount == 2 && tabName == 'Receivable'}">
	                   <li class="${sessionData.nav == 'Receivable' || sessionData.nav == 'Payables' ? 'navActive' : ''}">
														  
							  <a href="#" class="billingLi" title="Finance">
								<span class="navIcons finIcon"></span><b>Finance</b> <i class="fa fa-fw fa-caret-down"></i>
							  </a>
							  
						   
						   <c:if test="${sessionData.nav == 'Receivable' || sessionData.nav == 'Payables'}">
                              <ul class="collapses" style="display: none">
                                  <li class="${sessionData.nav == 'Receivable' ? 'treview-active' : ''}"><a href="${SourceLeadWeb_vmsUrl}/invoiceList" title="Invoices"><i class="fa fa-chevron-right"></i> Invoices</a></li>
                                  <li class="${sessionData.nav == 'Payables' ? 'treview-active' : ''}"><a href="${SourceLeadWeb_vmsUrl}/payablesList" title="Bills"><i class="fa fa-chevron-right"></i> Bills</a></li>
                              </ul>
                           </c:if>
                              
                           <c:if test="${sessionData.nav != 'Receivable' || sessionData.nav != 'Payables'}">
                           <ul class="collapses" style="display: none">
                               <li class="${sessionData.nav == 'Receivable' ? 'treview-active' : ''}"><a href="${SourceLeadWeb_vmsUrl}/invoiceList" title="Invoices"><i class="fa fa-chevron-right"></i> Invoices</a></li>
                               <li class="${sessionData.nav == 'Payables' ? 'treview-active' : ''}"><a href="${SourceLeadWeb_vmsUrl}/payablesList" title="Bills"><i class="fa fa-chevron-right"></i> Bills</a></li>
                           </ul>
                           </c:if>
                              
						  </li>
	                 </c:if>
	                 
	                 
	                  <c:if test="${SourceLeadWeb_billingTabsCount < 2 && tabName == 'Receivable'}">
	                   <li class="${sessionData.nav == 'Receivable' ? 'navActive' : ''}"><a href="${SourceLeadWeb_vmsUrl}/invoiceList" title="Invoices"><span class="navIcons invoIcon"></span><b>Invoices</b></a></li>
	                 </c:if>
	                 
	                 <c:if test="${SourceLeadWeb_billingTabsCount < 2 && tabName == 'Payables'}">
	                 <li class="${sessionData.nav == 'Payables' ? 'navActive' : ''}"><a href="${SourceLeadWeb_vmsUrl}/payablesList" title="Bills"><span class="navIcons billIcon"></span><b>Bills</b></a></li>
	                 </c:if>
	                 
	                 
	                 
	                 
	                 <c:if test="${SourceLeadWeb_expensesTabCount == 2 && tabName == 'My Expenses'}">
	                   <li class="${sessionData.nav == 'My Expenses' || sessionData.nav == 'Expenses Approval' ? 'navActive' : ''}">
														  
							  <a href="#" class="expencesLi" title="Expenses">
								<span class="navIcons expIcon"></span><b>Expenses</b> <i class="fa fa-fw fa-caret-down"></i>
							  </a>
							  
						   
						   <c:if test="${sessionData.nav == 'My Expenses' || sessionData.nav == 'Expenses Approval'}">
                              <ul class="collapses" style="display: none">
                                  <li class="${sessionData.nav == 'My Expenses' ? 'treview-active' : ''}"><a href="${SourceLeadWeb_pmsUrl}/expenses" title="My Expenses"><i class="fa fa-chevron-right"></i> Self</a></li>
                                  <li class="${sessionData.nav == 'Expenses Approval' ? 'treview-active' : ''}"><a href="${SourceLeadWeb_pmsUrl}/expensesApprovalList" title="Expenses Approval"><i class="fa fa-chevron-right"></i> Employees</a></li>
                              </ul>
                           </c:if>
                              
                           <c:if test="${sessionData.nav != 'My Expenses' || sessionData.nav != 'Expenses Approval'}">
                           <ul class="collapses" style="display: none">
                               <li class="${sessionData.nav == 'My Expenses' ? 'treview-active' : ''}"><a href="${SourceLeadWeb_pmsUrl}/expenses" title="My Expenses"><i class="fa fa-chevron-right"></i> Self</a></li>
                               <li class="${sessionData.nav == 'Expenses Approval' ? 'treview-active' : ''}"><a href="${SourceLeadWeb_pmsUrl}/expensesApprovalList" title="Expenses Approval"><i class="fa fa-chevron-right"></i> Employees</a></li>
                           </ul>
                           </c:if>
                              
						  </li>
	                 </c:if>
	                 
	                 <c:if test="${SourceLeadWeb_expensesTabCount < 2 && tabName == 'My Expenses'}">
	                   <li class="${sessionData.nav == 'My Expenses' ? 'navActive' : ''}"><a href="${SourceLeadWeb_pmsUrl}/expenses" title="My Expenses"><span class="navIcons expIcon"></span><b>Expenses</b></a></li>
	                 </c:if>
	                 
	                 <c:if test="${SourceLeadWeb_expensesTabCount < 2 && tabName == 'Expenses Approval'}">
	                 <li class="${sessionData.nav == 'Expenses Approval' ? 'navActive' : ''}"><a href="${SourceLeadWeb_pmsUrl}/expensesApprovalList" title="Expenses Approval"><span class="navIcons expIcon"></span><b>Expenses Approval</b></a></li>
	                 </c:if>
	                 
	                 <c:if test="${tabName == 'Asset Management'}">
	                   <li class="${sessionData.nav == 'AssetManagement' ? 'navActive' : ''}"><a href="<c:url value='${SourceLeadWeb_atsUrl}/assetManagement'/>" title="Asset Management"><span class="navIcons asstIcon"></span><b>Asset Management</b></a></li>
	                </c:if> 
	                
	                <c:if test="${tabName == 'Support'}">
	                   <li class="${sessionData.nav == 'Support' ? 'navActive' : ''}"><a href="${SourceLeadWeb_sourceleadUrl}/sourceleadTicketList" title="Support"><span class="navIcons supIcon"></span><b>Support</b></a></li>
	                </c:if> 
	                
	                <c:if test="${SourceLeadWeb_resourcesTabCount == 2 && tabName == 'Blog'}">
	                   <li class="${sessionData.nav == 'Blogs' || sessionData.nav == 'UserManuals' ? 'navActive' : ''}">
														  
							  <a href="#" class="resourcesli" title="Resources">
								<span class="navIcons resIcon"></span><b>Resources</b> <i class="fa fa-fw fa-caret-down"></i>
							  </a>
						   <c:if test="${sessionData.nav == 'Blogs' || sessionData.nav == 'UserManuals'}">
                              <ul class="collapses" style="display: none">
                                  <li class="${sessionData.nav == 'Blogs' ? 'treview-active' : ''}"><a href="${SourceLeadWeb_sourceleadUrl}/blogs" title="Blog"><i class="fa fa-chevron-right"></i> Blog</a></li>
                                  <li class="${sessionData.nav == 'UserManuals' ? 'treview-active' : ''}"><a href="${SourceLeadWeb_sourceleadUrl}/userManuals" title="User Manuals"><i class="fa fa-chevron-right"></i> User Manuals</a></li>
                                  <li class=""><a href="${SourceLeadWeb_sourceleadUrl}/releaseNote" title="Realease Note"><i class="fa fa-chevron-right"></i> Release Note</a></li>
                              </ul>
                           </c:if>
                              
                           <c:if test="${sessionData.nav != 'Blogs' || sessionData.nav != 'UserManuals'}">
                           <ul class="collapses" style="display: none">
                               <li class="${sessionData.nav == 'Blogs' ? 'treview-active' : ''}"><a href="${SourceLeadWeb_sourceleadUrl}/blogs" title="Blog"><i class="fa fa-chevron-right"></i> Blog</a></li>
                               <li class="${sessionData.nav == 'UserManuals' ? 'treview-active' : ''}"><a href="${SourceLeadWeb_sourceleadUrl}/userManuals" title="User Manuals"><i class="fa fa-chevron-right"></i> User Manuals</a></li>
                               <li class=""><a href="${SourceLeadWeb_sourceleadUrl}/releaseNote" title="Realease Note"><i class="fa fa-chevron-right"></i> Release Note</a></li>
                               
                           </ul>
                           </c:if>
                           
                              
						  </li>
	                 </c:if>
	                 
	                 <c:if test="${SourceLeadWeb_resourcesTabCount < 2}">
	                 
	                 <c:if test="${tabName == 'Blog'}">
	                    <li class="${sessionData.nav == 'Blogs' ? 'navActive' : ''}"><a href="${SourceLeadWeb_sourceleadUrl}/blogs" title="Blogs"><span class="navIcons blogIcon"></span><b>Blogs</b></a></li>
	                 </c:if>
	                 
	                 <c:if test="${tabName == 'User Manuals'}">
	                    <li class="${sessionData.nav == 'UserManuals' ? 'navActive' : ''}"><a href="${SourceLeadWeb_sourceleadUrl}/userManuals" title="User Manuals"><span class="navIcons usermanuIcon"></span><b>User Manuals</b></a></li>
	                 </c:if>
	                      
	                 </c:if>
	                
	                


	                <c:if test="${tabName == 'Campaigns'}">
                         <li class="${sessionData.nav == 'Campaigns' || sessionData.nav == 'CampaignSender' || sessionData.nav == 'CampaignRecipient' ? 'navActive' : ''}"><a href="${SourceLeadWeb_sourceleadUrl}/campaigns/hotcampaigns" title="Campaigns"><span class="navIcons campIcon"></span><b>Campaigns</b></a></li>				  
                     </c:if>

					<c:if test="${SourceLeadWeb_paryrollTabCount == 2 && tabName == 'Run Payroll'}">
						<li class="${sessionData.nav == 'Run Payroll' || sessionData.nav == 'My Payroll' ? 'navActive' : ''}">
						
						<a href="#" class="payrollli" title="Pay Roll">
						<span class="navIcons payIcon"></span><b>Pay Roll</b> <i class="fa fa-fw fa-caret-down"></i>
						</a>
						
						
						<c:if test="${sessionData.nav == 'Payroll' || sessionData.nav == 'My Payroll'}">
							<ul class="collapses" style="display: none">
							 <c:if test="${SourceLeadWeb_country == 'United States'}">
								<li class="${sessionData.nav == 'Payroll' ? 'treview-active' : ''}"><a href="${SourceLeadWeb_pmsUrl}/uSPayrollDashboard" title="Run Payroll"><i class="fa fa-chevron-right"></i> Run Payroll</a></li>
								<li><a href="${SourceLeadWeb_pmsUrl}/userPay" title="Legacy Payroll"><i class="fa fa-chevron-right"></i>Legacy Payroll</a></li>
								<li><a href="${SourceLeadWeb_pmsUrl}/myPayroll" title="My Payroll"><i class="fa fa-chevron-right"></i>My Payroll</a></li>
							</c:if>
							
							<c:if test="${SourceLeadWeb_country == 'India'}">
								<li class="${sessionData.nav == 'MyPayroll' ? 'treview-active' : ''}"><a href="${SourceLeadWeb_pmsUrl}/myPayrollInd" title="My Pays"><i class="fa fa-chevron-right"></i> My Payroll</a></li>
								<li class="${sessionData.nav == 'RunPayroll' ? 'treview-active' : ''}"><a href="${SourceLeadWeb_pmsUrl}/runIndianPayRoll" title="Payroll"><i class="fa fa-chevron-right"></i>Org Payroll</a></li>
							</c:if>
							</ul>
						</c:if>
						
						<c:if test="${sessionData.nav != 'Payroll' || sessionData.nav != 'My Payroll'}">
							<ul class="collapses" style="display: none">
							<c:if test="${SourceLeadWeb_country == 'United States'}">
								<li class="${sessionData.nav == 'Payroll' ? 'treview-active' : ''}"><a href="${SourceLeadWeb_pmsUrl}/uSPayrollDashboard" title="Run Payroll"><i class="fa fa-chevron-right"></i> Run Payroll</a></li>
								<li><a href="${SourceLeadWeb_pmsUrl}/userPay" title="Legacy Payroll"><i class="fa fa-chevron-right"></i> Legacy Payroll</a></li>
								<li><a href="${SourceLeadWeb_pmsUrl}/myPayroll" title="My Payroll"><i class="fa fa-chevron-right"></i> My Payroll</a></li>
							</c:if>
							
							<c:if test="${SourceLeadWeb_country == 'India'}">
								<li class="${sessionData.nav == 'MyPayroll' ? 'treview-active' : ''}"><a href="${SourceLeadWeb_pmsUrl}/myPayrollInd" title="My Pays"><i class="fa fa-chevron-right"></i>My Payroll</a></li>
								<li class="${sessionData.nav == 'RunPayroll' ? 'treview-active' : ''}"><a href="${SourceLeadWeb_pmsUrl}/runIndianPayRoll" title="Payroll"><i class="fa fa-chevron-right"></i>Org Payroll</a></li>
							</c:if>
							</ul>
						</c:if>
						
						</li>
					</c:if>
					
					<c:if test="${SourceLeadWeb_paryrollTabCount < 2 && tabName == 'Run Payroll'}">
							<c:if test="${SourceLeadWeb_country == 'United States'}">
								<li class="${sessionData.nav == 'Payroll' ? 'navActive' : ''}"><a href="${SourceLeadWeb_pmsUrl}/uSPayrollDashboard" title="Run Payroll"><span class="navIcons payIcon"></span><b>Run Payroll</b></a></li>
							</c:if>
							
							<c:if test="${SourceLeadWeb_country == 'India'}">
								<li class="${sessionData.nav == 'RunPayroll' ? 'navActive' : ''}"><a href="${SourceLeadWeb_pmsUrl}/runIndianPayRoll" title="Payroll"><span class="navIcons payIcon"></span><b>Org Payroll</b></a></li>
							</c:if>
					</c:if>
					
					<c:if test="${SourceLeadWeb_paryrollTabCount < 2 && tabName == 'My Payroll'}">
							<c:if test="${SourceLeadWeb_country == 'United States'}">
								<li class="${sessionData.nav == 'My Payroll' ? 'navActive' : ''}"><a href="${SourceLeadWeb_pmsUrl}/userPay" title="Leagacy Payroll"><span class="navIcons payIcon"></span><b>Legacy Payroll</b></a></li>
								<li class="${sessionData.nav == 'My Payroll' ? 'navActive' : ''}"><a href="${SourceLeadWeb_pmsUrl}/myPayroll" title="My Payroll"><span class="navIcons payIcon"></span><b>My Payroll</b></a></li>
							</c:if>
							
							<c:if test="${SourceLeadWeb_country == 'India'}">
								<li class="${sessionData.nav == 'MyPayroll' ? 'navActive' : ''}"><a href="${SourceLeadWeb_pmsUrl}/myPayrollInd" title="My Pays"><span class="navIcons payIcon"></span><b>My Payroll</b></a></li>
							</c:if>
					</c:if>

					<c:if test="${tabName == 'Immigrations'}">
					 <sec:authorize access="hasAnyRole('ROLE_ADD_PETITION','ROLE_EDIT_VIEW_PETITION','ROLE_DELETE_PETITION','ROLE_VIEW_MY_PETITION')"> 
					

							<li class="${sessionData.nav == 'Immigrations' || sessionData.nav == 'MyImmigration' ? 'navActive' : ''}">
							<a href="#" class="expencesLi" title="Immigration">
									<span class="navIcons immIcon"></span><b>Immigration</b><i class="fa fa-fw fa-caret-down"></i>
							</a>

								<ul class="collapses" style="display: none">
								
									<li class="${sessionData.nav == 'MyImmigration' ? 'treview-active' : ''}"><a	href="${SourceLeadWeb_vmsUrl}/immigration/myImmigration" title="My Immigration"><i class="fa fa-chevron-right"></i> Self</a></li>
									<li class="${sessionData.nav == 'Immigrations' ? 'treview-active' : ''}"><a	href="${SourceLeadWeb_vmsUrl}/immigration/viewImmigrationList" title="Immigrations"><i class="fa fa-chevron-right"></i> Employees</a></li>
									
								</ul>
								
								</li>
								</sec:authorize>
						</c:if>
						
						<%--<c:if test="${tabName == 'BackgroundCheck'}">
					       <sec:authorize access="hasAnyRole('ROLE_ADD_BACKGROUND_CHECK_DETAILS','ROLE_VIEW_EDIT_BACKGROUND_CHECK_DETAILS','ROLE_DELETE_BACKGROUND_CHECK_DETAILS')">
					

							<li class="${sessionData.nav == 'BackgroundCheck' || sessionData.nav == 'InProgress' ? 'navActive' : ''}">
							<a href="#" class="expencesLi" title="Immigration">
									<span class="navIcons immIcon"></span><b>Background Check</b><i class="fa fa-fw fa-caret-down"></i>
							</a>

								<ul class="collapses" style="display: none">
								
									<li class="${sessionData.nav == 'InProgress' ? 'treview-active' : ''}"><a	href="${SourceLeadWeb_vmsUrl}/bgc/inProgressBgcDetails" title="In Progress"><i class="fa fa-chevron-right"></i> In Progress</a></li>
									<li class="${sessionData.nav == 'Completed' ? 'treview-active' : ''}"><a	href="${SourceLeadWeb_vmsUrl}/bgc/completedBgcDetails" title="Completed"><i class="fa fa-chevron-right"></i> Completed</a></li>
									
								</ul>
								
								</li>
							 	</sec:authorize> 
						</c:if> --%>
				    <c:if test="${tabName == 'Leave Management'}">
		             <li class="${sessionData.nav == 'Leave Management' ? 'navActive' : ''}"><a href="${SourceLeadWeb_pmsUrl}/leaveManagementReports" title="Leave Management"><span class="navIcons leaveIcon"></span><b>Leave Management</b></a></li>
		          	</c:if>	
		          	
          </c:forEach>
	         
          	  <c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_SOURCELEAD_ADMIN'}">
          			 <li class="${sessionData.nav == 'Taxes' ? 'navActive' : ''}"><a href="${SourceLeadWeb_sourceleadUrl}/stateTax/list" title="Taxes"><span class="navIcons finIcon"></span><b>Taxes</b></a></li>
       	 	  </c:if>
       	 	  
   	 	  <sec:authorize access="hasAnyRole('ROLE_ADD_MY_GOALS','ROLE_ADD_TEAM_GOALS','ROLE_ADD_MY_APPRAISAL','ROLE_ADD_EMPLOYEE_APPRAISAL','ROLE_ADD_EMPLOYEE_APPRAISAL', 'ROLE_EMPLOYEE_PERFORMANCE_ORG_CHART')"> 
			<li>
				  <a href="#" class="performanceli" title="Performance">
					<span class="navIcons finIcon"></span><b>Performance</b> <i class="fa fa-fw fa-caret-down"></i>
				  </a>
					  
			        <ul class="collapses" style="display: none">
                                
                               <li class="${sessionData.nav == 'Goals' ? 'treview-active' : ''}"><a href="${SourceLeadWeb_pmsUrl}/performanceGoalsList" title="Goals"><i class="fa fa-chevron-right"></i> Goals</a></li>
                               
                               <sec:authorize access="hasRole('ROLE_ADD_MY_APPRAISAL')"> 
                               		<li class="${sessionData.nav == 'My Appraisal' ? 'treview-active' : ''}"><a href="${SourceLeadWeb_pmsUrl}/performanceAppraisalList" title="My Appraisal"><i class="fa fa-chevron-right"></i> My Appraisal</a></li>
                                </sec:authorize>
                               
                                <sec:authorize access="hasRole('ROLE_ADD_EMPLOYEE_APPRAISAL')">
                                	<li class="${sessionData.nav == 'Employee Appraisal' ? 'treview-active' : ''}"><a href="${SourceLeadWeb_pmsUrl}/employeePerformanceAppraisalList" title="Employee Appraisal"><i class="fa fa-chevron-right"></i> Employee Appraisal</a></li>
                               </sec:authorize>
                               
                               <sec:authorize access="hasRole('ROLE_EMPLOYEE_PERFORMANCE_ORG_CHART')">
                               		<li class="${sessionData.nav == 'Org Chart' ? 'treview-active' : ''}"><a href="${SourceLeadWeb_pmsUrl}/performanceOrgChartList" title="Org Chart"><i class="fa fa-chevron-right"></i> Org Chart</a></li>
                              </sec:authorize>
                               
                    </ul>
                              
			  </li>
			</sec:authorize>
			
          		<li class="${sessionData.nav == 'BGC' || sessionData.nav == 'InProgress' ? 'navActive' : ''}">
							<a href="#" class="expencesLi" title="Backround Check">
									<span class="navIcons immIcon"></span><b>BGC</b><i class="fa fa-fw fa-caret-down"></i>
							</a>

								<ul class="collapses" style="display: none">
								
									<li class="${sessionData.nav == 'InProgress' ? 'treview-active' : ''}"><a	href="${SourceLeadWeb_vmsUrl}/bgc/inProgressBgcDetails" title="In Progress"><i class="fa fa-chevron-right"></i> In Progress</a></li>
									<li class="${sessionData.nav == 'Completed' ? 'treview-active' : ''}"><a	href="${SourceLeadWeb_vmsUrl}/bgc/completedBgcDetails" title="Completed"><i class="fa fa-chevron-right"></i> Completed</a></li>
									
								</ul>
								
					</li>
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
  
  