<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<!-- Side Nav Bar -->
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

  <aside>
  <div class="side-bar theme_sideMenu">
    <div class="side-bar-inner">
      <%-- <div class="user-panel">
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
      </div>--%>
      
      <div class="side-nav sidenav_inner">
        <ul class="list-unstyled">
        <c:if test="${SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_PROFESSIONAL' && SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_RECRUITER' && SourceLeadWeb_prodSupportLoginUserRole == null}">
        <li class="${sessionData.nav == 'Dashboard' ? 'side-nav-active' : ''}"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>" title="Dashboard"><span><i class="fa fa-dashboard"></i></span><i class="subMenu">Dashboard</i></a></li>
        </c:if>
        
         <c:if test="${SourceLeadWeb_prodSupportLoginUserRole == 'ROLE_PRODUCTION_SUPPORT'}">
        <li class="${sessionData.nav == 'Dashboard' ? 'side-nav-active' : ''}"><a href="<c:url value='/productionSupportdashboard/${SourceLeadWeb_orgLocationId}/${SourceLeadWeb_prodSupportOrgLocationId}'/>" title="Dashboard"><span><i class="fa fa-dashboard"></i></span><i class="subMenu">Dashboard</i></a></li>
        </c:if>
        
        <c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_PROFESSIONAL'}">

        <li class="${sessionData.nav == 'Dashboard' ? 'side-nav-active' : ''}"><a href="<c:url value='/professionalDashboard/${SourceLeadWeb_orgLocationId}/${SourceLeadWeb_displayName}'/>" title="Dashboard"><span><i class="fa fa-dashboard"></i></span><i class="subMenu">Dashboard</i></a></li>
        </c:if> 
        
        <c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_RECRUITER'}">
        <li class="${sessionData.nav == 'Dashboard' ? 'side-nav-active' : ''}"><a href="<c:url value='/recruiterDashboard/${SourceLeadWeb_orgLocationId}/${SourceLeadWeb_displayName}'/>" title="Dashboard"><span><i class="fa fa-dashboard"></i></span><i class="subMenu">Dashboard</i></a></li>
 		</c:if>
        
             
         <li class="${sessionData.nav == 'Profile' ? 'side-nav-active' : ''}"><a href="<c:url value='/professionalProfile'/>" title="Profile"><span><i class="fa fa-id-card-o"></i></span><i class="subMenu">Profile</i></a></li> 
          
          <c:set var="timesheetTabExist" scope="session" value="N"/>
          <c:forEach var="userTabs" items="${SourceLeadWeb_tabsList}">
                    <c:if test="${userTabs.tabName == 'Clients'}">
                 	  <li class="${sessionData.nav == 'Clients' ? 'side-nav-active' : ''}"><a href="<c:url value='/clientDashboard'/>" title="Clients"><span><i class="fa fa-user-plus"></i></span><i class="subMenu">Clients</i></a></li>
                    </c:if>
	                <c:if test="${userTabs.tabName == 'Jobs'}"> 
	                <c:if test="${SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_PROFESSIONAL' && SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_RECRUITER'}"> 
	                   <li class="${sessionData.nav == 'Requirement' ? 'side-nav-active' : ''}"><a href="<c:url value='/requirementList'/>" title="Jobs"><span><i class="fa fa-briefcase"></i></span><i class="subMenu">Jobs</i></a></li>
	                </c:if> 
	                
	                <c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_PROFESSIONAL' || SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_RECRUITER' }">
	                 <li class="${sessionData.nav == 'Requirement' ? 'side-nav-active' : ''}"><a href="<c:url value='/professionalRequirements'/>" title="Jobs"><span><i class="fa fa-briefcase"></i></span><i class="subMenu">Jobs</i></a></li>
	                </c:if>
	                </c:if>
	                
	                
	                
	                <c:if test="${userTabs.tabName == 'Candidates'}">
	                 <c:if test="${SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_RECRUITER'}">
	                   <li class="${sessionData.nav == 'Candidates' ? 'side-nav-active' : ''}"><a href="<c:url value='/candidatesList'/>" title="Candidates"><span><i class="fa fa-user"></i></span><i class="subMenu">Candidates</i></a></li>
	                 </c:if>
	                 <c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_RECRUITER'}">
	                   <li class="${sessionData.nav == 'Candidates' ? 'side-nav-active' : ''}"><a href="<c:url value='/candidatesList'/>" title="Candidates"><span><i class="fa fa-user"></i></span><i class="subMenu">Candidates</i></a></li>
	                 </c:if>
	                </c:if> 
	                
                
	                <c:if test="${userTabs.tabName == 'Vendors'}">
	                   <li class="${sessionData.nav == 'Vendors' ? 'side-nav-active' : ''}"><a href="<c:url value='/vendorsList'/>" title="Vendors"><span><i class="fa fa-suitcase"></i></span><i class="subMenu">Vendors</i></a></li>
	                </c:if> 
	                 
	                 <c:if test="${userTabs.tabName == 'Users'}">
	                 <li class="${sessionData.nav == 'Users' ? 'side-nav-active' : ''}"><a href="<c:url value='/usersList'/>" title="Users"><span><i class="fa fa-users"></i></span><i class="subMenu">Users</i></a></li>
	                 </c:if>
	                 
	                 <c:if test="${userTabs.tabName == 'Projects'}">
	                	  <li class="${sessionData.nav == 'Projects' ? 'side-nav-active' : ''}"><a href="<c:url value='/projectDashboard'/>" title="Projects"><span><i class="fa fa-bar-chart"></i></span><i class="subMenu">Projects</i></a></li>
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
							  	     
							  	    <li class="${sessionData.nav == 'TimeSheet' ? 'treview-active' : ''}"><a href="<c:url value='/timesheet'/>" title="My TimeSheets">TimeSheets</a></li>
									<li class="${sessionData.nav == 'TimeSheetApproval' ? 'treview-active' : ''}"><a href="<c:url value='/timesheetApproval'/>" title="TimeSheet Approval">Approval</a></li>
							  </ul>
							  </c:if>
							  <c:if test="${sessionData.nav != 'TimeSheet' || sessionData.nav != 'TimeSheetApproval'}">
							  <c:set var ="timesheetTabExist" scope="session" value="Y"/>
							  <ul class="collapses" style="display: none">
							 		<%-- <li class="${sessionData.nav == 'TimeSheet' ? 'treview-active' : ''}"><a href="<c:url value='/timesheet'/>" title="My TimeSheets">My TimeSheets</a></li>
							  		<li class="${sessionData.nav == 'TimeSheet' ? 'treview-active' : ''}"><a href="<c:url value='/newTimesheetApproval'/>" title="My TimeSheets">TimeSheet Approval</a></li> --%>
							  		<li class="${sessionData.nav == 'TimeSheet' ? 'treview-active' : ''}"><a href="<c:url value='/timesheet'/>" title="My TimeSheets">TimeSheets</a></li>
									<li class="${sessionData.nav == 'TimeSheetApproval' ? 'treview-active' : ''}"><a href="<c:url value='/timesheetApproval'/>" title="TimeSheet Approval">Approval</a></li> 
							  </ul>
							  </c:if>
						  </li>
					  </sec:authorize>
	                 </c:if>
	                 <c:if test="${userTabs.tabName == 'My Timesheets'}">
	                 <sec:authorize access="hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_EXPORT_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS') && !hasAnyRole('ROLE_FILL_MEMBER_TIMESHEETS','ROLE_VIEW_MEMBER_TIMESHEETS','ROLE_EXPORT_MEMBER_TIMESHEETS')">
	                       <li class="${sessionData.nav == 'TimeSheet' ? 'side-nav-active' : ''}"><a href="<c:url value='/timesheet'/>"><span><i class="fa fa-clock-o"></i></span><i class="subMenu">TimeSheet</i></a></li>
	                 </sec:authorize>
	                 </c:if>
	                 <c:if test="${userTabs.tabName == 'Timesheet Approval'}">
	                 <sec:authorize access="!hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_EXPORT_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS') && hasAnyRole('ROLE_FILL_MEMBER_TIMESHEETS','ROLE_VIEW_MEMBER_TIMESHEETS','ROLE_EXPORT_MEMBER_TIMESHEETS')">
	                       <li class="${sessionData.nav == 'TimeSheetApproval' ? 'side-nav-active' : ''}"><a href="<c:url value='/timesheetApproval'/>"><span><i class="fa fa-clock-o"></i></span><i class="subMenu">TimeSheet</i></a></li>
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
														  
							  <a href="#" class="associatesLi" title="Associates">
								<span><i class="fa fa-black-tie"></i></span>
							  <i class="subMenu">Associates</i><i class="fa fa-fw fa-caret-down"></i>
							  </a>
							  
						   
						   <c:if test="${sessionData.nav == 'Associates' || sessionData.nav == 'OnAndOffboard'}">
                              <ul class="collapses" style="display: none">
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
	                   <li class="${sessionData.nav == 'Associates' ? 'side-nav-active' : ''}"><a href="<c:url value='/associatesList'/>" title="Associates List"><span><i class="fa fa-black-tie"></i></span><i class="subMenu">Associates List</i></a></li>
	                 </c:if>
	                 
	                 <c:if test="${SourceLeadWeb_associatesTabsCount < 2 && userTabs.tabName == 'On & Off Boarding'}">
	                 <li class="${sessionData.nav == 'OnAndOffboard' ? 'side-nav-active' : ''}"><a href="<c:url value='/candidateOnBoardingList'/>" title="On / Off Boarding"><span><i class="fa fa-black-tie"></i></span><i class="subMenu">On / Off Boarding</i></a></li>
	                 </c:if>
	                 
	                 
	                 
	                 <c:if test="${userTabs.tabName == 'Contracts'}">
	                   <li class="${sessionData.nav == 'Contract' ? 'side-nav-active' : ''}"><a href="<c:url value='/contractListbyStatus'/>" title="Contracts"><span><i class="fa fa-object-ungroup"></i></span><i class="subMenu">Contracts</i></a></li>
	                </c:if> 
	                
	                
	                
	                
	                
	                <c:if test="${SourceLeadWeb_billingTabsCount == 2 && userTabs.tabName == 'Receivable'}">
	                   <li class="${sessionData.nav == 'Receivable' || sessionData.nav == 'Payables' ? 'side-nav-active' : ''}">
														  
							  <a href="#" class="billingLi" title="Billing">
								<span><i class="fa fa-money"></i></span>
							  <i class="subMenu">Billing</i> <i class="fa fa-fw fa-caret-down"></i>
							  </a>
							  
						   
						   <c:if test="${sessionData.nav == 'Receivable' || sessionData.nav == 'Payables'}">
                              <ul class="collapses" style="display: none">
                                  <li class="${sessionData.nav == 'Receivable' ? 'treview-active' : ''}"><a href="<c:url value='/invoiceList'/>" title="Receivables">Receivables</a></li>
                                  <li class="${sessionData.nav == 'Payables' ? 'treview-active' : ''}"><a href="<c:url value='/payablesList'/>" title="Payables">Payables</a></li>
                              </ul>
                           </c:if>
                              
                           <c:if test="${sessionData.nav != 'Receivable' || sessionData.nav != 'Payables'}">
                           <ul class="collapses" style="display: none">
                               <li class="${sessionData.nav == 'Receivable' ? 'treview-active' : ''}"><a href="<c:url value='/invoiceList'/>" title="Receivables">Receivables</a></li>
                               <li class="${sessionData.nav == 'Payables' ? 'treview-active' : ''}"><a href="<c:url value='/payablesList'/>" title="Payables">Payables</a></li>
                           </ul>
                           </c:if>
                              
						  </li>
	                 </c:if>
	                 
	                 
	                  <c:if test="${SourceLeadWeb_billingTabsCount < 2 && userTabs.tabName == 'Receivable'}">
	                   <li class="${sessionData.nav == 'Receivable' ? 'side-nav-active' : ''}"><a href="<c:url value='/invoiceList'/>" title="Receivables"><span><i class="fa fa-money"></i></span><i class="subMenu">Receivables</i></a></li>
	                 </c:if>
	                 
	                 <c:if test="${SourceLeadWeb_billingTabsCount < 2 && userTabs.tabName == 'Payables'}">
	                 <li class="${sessionData.nav == 'Payables' ? 'side-nav-active' : ''}"><a href="<c:url value='/payablesList'/>" title="Payables"><span><i class="fa fa-money"></i></span><i class="subMenu">Payables</i></a></li>
	                 </c:if>
	                 
	                 
	                 
	                 
	                 <c:if test="${SourceLeadWeb_expensesTabCount == 2 && userTabs.tabName == 'My Expenses'}">
	                   <li class="${sessionData.nav == 'My Expenses' || sessionData.nav == 'Expenses Approval' ? 'side-nav-active' : ''}">
														  
							  <a href="#" class="expencesLi" title="Expenses">
								<span><i class="fa fa-calculator"></i></span>
							  <i class="subMenu">Expenses</i> <i class="fa fa-fw fa-caret-down"></i>
							  </a>
							  
						   
						   <c:if test="${sessionData.nav == 'My Expenses' || sessionData.nav == 'Expenses Approval'}">
                              <ul class="collapses" style="display: none">
                                  <li class="${sessionData.nav == 'My Expenses' ? 'treview-active' : ''}"><a href="<c:url value='/expenses'/>" title="My Expenses">My Expenses</a></li>
                                  <li class="${sessionData.nav == 'Expenses Approval' ? 'treview-active' : ''}"><a href="<c:url value='/expensesApprovalList'/>" title="Expenses Approval">Expenses Approval</a></li>
                              </ul>
                           </c:if>
                              
                           <c:if test="${sessionData.nav != 'My Expenses' || sessionData.nav != 'Expenses Approval'}">
                           <ul class="collapses" style="display: none">
                               <li class="${sessionData.nav == 'My Expenses' ? 'treview-active' : ''}"><a href="<c:url value='/expenses'/>" title="My Expenses">My Expenses</a></li>
                               <li class="${sessionData.nav == 'Expenses Approval' ? 'treview-active' : ''}"><a href="<c:url value='/expensesApprovalList'/>" title="Expenses Approval">Expenses Approval</a></li>
                           </ul>
                           </c:if>
                              
						  </li>
	                 </c:if>
	                 
	                 <c:if test="${SourceLeadWeb_expensesTabCount < 2 && userTabs.tabName == 'My Expenses'}">
	                   <li class="${sessionData.nav == 'My Expenses' ? 'side-nav-active' : ''}"><a href="<c:url value='/expenses'/>" title="My Expenses"><span><i class="fa fa-calculator"></i></span><i class="subMenu">My Expenses</i></a></li>
	                 </c:if>
	                 
	                 <c:if test="${SourceLeadWeb_expensesTabCount < 2 && userTabs.tabName == 'Expenses Approval'}">
	                 <li class="${sessionData.nav == 'Expenses Approval' ? 'side-nav-active' : ''}"><a href="<c:url value='/expensesApprovalList'/>" title="Expenses Approval"><span><i class="fa fa-calculator"></i></span><i class="subMenu">Expenses Approval</i></a></li>
	                 </c:if>

          </c:forEach>
         <c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_SOURCELEAD_ADMIN' || SourceLeadWeb_loginUserRole == 'ROLE_ADMIN'}">
          <li class="${sessionData.nav == 'Payroll' ? 'side-nav-active' : ''}"><a href="<c:url value='/payrollList'/>" title="PayRoll"><span><i class="fa fa-usd"></i></span><i class="subMenu">Payroll</i></a></li>
          	</c:if>	
          	
          	<c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_USER'}">
          <li class="${sessionData.nav == 'Pay' ? 'side-nav-active' : ''}"><a href="<c:url value='/userPay'/>" title="Pay"><span><i class="fa fa-usd"></i></span><i class="subMenu">Pay</i></a></li>
          	</c:if>	
          	
        </ul>
      </div>
    </div>
    </div>
    

 
  </aside>
  <!-- Side Nav Bar --> 