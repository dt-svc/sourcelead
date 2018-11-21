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
	                
	                
	                <c:if test="${userTabs.tabName == 'Candidates'}">
	                 <c:if test="${SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_RECRUITER'}">
	                   <li class="${sessionData.nav == 'Candidates' ? 'navActive' : ''}"><a href="<c:url value='${appPath}/candidatesList'/>" title="Candidates"><span class="navIcons canIcon"></span><b>Candidates</b></a></li>
	                 </c:if>
	                 <c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_RECRUITER'}">
	                   <li class="${sessionData.nav == 'Candidates' ? 'navActive' : ''}"><a href="<c:url value='${appPath}/candidatesList'/>" title="Candidates"><span class="navIcons canIcon"></span><b>Candidates</b></a></li>
	                 </c:if>
	                </c:if> 
	                
                
	                 
	                 <c:if test="${userTabs.tabName == 'Associates'}">
	                 <li class="${sessionData.nav == 'Users' ? 'navActive' : ''}"><a href="<c:url value='${appPath}/usersList'/>" title="Associates"><span class="navIcons assIcon"></span><b>Associates</b></a></li>
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
	                
	                

					<c:if test="${SourceLeadWeb_paryrollTabCount == 2 && userTabs.tabName == 'Run Payroll'}">
						<li class="${sessionData.nav == 'Run Payroll' || sessionData.nav == 'My Payroll' ? 'navActive' : ''}">
						
						<a href="#" class="payrollli" title="Pay Roll">
						<span class="navIcons payIcon"></span><b>Pay Roll</b> <i class="fa fa-fw fa-caret-down"></i>
						</a>
						
						
						<c:if test="${sessionData.nav == 'Payroll' || sessionData.nav == 'My Payroll'}">
							<ul class="collapses" style="display: none">
							 <c:if test="${SourceLeadWeb_orgLocations.country == 'United States'}">
								<li class="${sessionData.nav == 'Payroll' ? 'treview-active' : ''}"><a href="<c:url value='${appPath}/payrollList'/>" title="Run Payroll"><i class="fa fa-chevron-right"></i> Run Payroll</a></li>
								<li><a href="<c:url value='${appPath}/userPay'/>" title="Legacy Payroll"><i class="fa fa-chevron-right"></i>Legacy Payroll</a></li>
								<li><a href="<c:url value='${appPath}/myPayroll'/>" title="My Payroll"><i class="fa fa-chevron-right"></i>My Payroll</a></li>
							</c:if>
							
							<c:if test="${SourceLeadWeb_orgLocations.country == 'India'}">
								<li class="${sessionData.nav == 'MyPayroll' ? 'treview-active' : ''}"><a href="<c:url value='${appPath}/myPayrollInd'/>" title="My Pays"><i class="fa fa-chevron-right"></i> My Pays</a></li>
								<li class="${sessionData.nav == 'RunPayroll' ? 'treview-active' : ''}"><a href="<c:url value='${appPath}/runIndianPayRoll'/>" title="Payroll"><i class="fa fa-chevron-right"></i> Payroll</a></li>
							</c:if>
							</ul>
						</c:if>
						
						<c:if test="${sessionData.nav != 'Payroll' || sessionData.nav != 'My Payroll'}">
							<ul class="collapses" style="display: none">
							<c:if test="${SourceLeadWeb_orgLocations.country == 'United States'}">
								<li class="${sessionData.nav == 'Payroll' ? 'treview-active' : ''}"><a href="<c:url value='${appPath}/payrollList'/>" title="Run Payroll"><i class="fa fa-chevron-right"></i> Run Payroll</a></li>
								<li><a href="<c:url value='${appPath}/userPay'/>" title="Legacy Payroll"><i class="fa fa-chevron-right"></i> Legacy Payroll</a></li>
								<li><a href="<c:url value='${appPath}/myPayroll'/>" title="My Payroll"><i class="fa fa-chevron-right"></i> My Payroll</a></li>
							</c:if>
							
							<c:if test="${SourceLeadWeb_orgLocations.country == 'India'}">
								<li class="${sessionData.nav == 'MyPayroll' ? 'treview-active' : ''}"><a href="<c:url value='${appPath}/myPayrollInd'/>" title="My Pays"><i class="fa fa-chevron-right"></i>My Payroll</a></li>
								<li class="${sessionData.nav == 'RunPayroll' ? 'treview-active' : ''}"><a href="<c:url value='${appPath}/runIndianPayRoll'/>" title="Payroll"><i class="fa fa-chevron-right"></i>Run Payroll</a></li>
							</c:if>
							</ul>
						</c:if>
						
						</li>
					</c:if>
					
					<c:if test="${SourceLeadWeb_paryrollTabCount < 2 && userTabs.tabName == 'Run Payroll'}">
							<c:if test="${SourceLeadWeb_orgLocations.country == 'United States'}">
								<li class="${sessionData.nav == 'Payroll' ? 'navActive' : ''}"><a href="<c:url value='${appPath}/payrollList'/>" title="Run Payroll"><span class="navIcons payIcon"></span><b>Run Payroll</b></a></li>
							</c:if>
							
							<c:if test="${SourceLeadWeb_orgLocations.country == 'India'}">
								<li class="${sessionData.nav == 'RunPayroll' ? 'navActive' : ''}"><a href="<c:url value='${appPath}/runIndianPayRoll'/>" title="Payroll"><span class="navIcons payIcon"></span><b>Run Payroll</b></a></li>
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

				    <c:if test="${userTabs.tabName == 'Leave Management'}">
		             <li class="${sessionData.nav == 'Leave Management' ? 'navActive' : ''}"><a href="<c:url value='${appPath}/leaveManagementReports'/>" title="Leave Management"><span class="navIcons leaveIcon"></span><b>Leave Management</b></a></li>
		          	</c:if>	
          </c:forEach>
	         
          	  <c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_SOURCELEAD_ADMIN'}">
          			 <li class="${sessionData.nav == 'Taxes' ? 'navActive' : ''}"><a href="<c:url value='${appPath}/stateTax/list'/>" title="Taxes"><span class="navIcons finIcon"></span><b>Taxes</b></a></li>
       	 	  </c:if>	
	            	
			
          	
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
		    	  requirementListCheck('','','','http://localhost:8080/console');
                  console.log(" inside success function ==========>")
               	
           }
       })
}

</script>
  
  