<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>



<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Help</h2>
      </div>
      <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home </a> &raquo; Issues List</div>
    </section>
    
			<div class="project_wrap">
						
			<ul class="pull-left list-unstyled list-inline">
			<li><a href="/quick_reference_guide_login.html" id="aboutusID">Quick Reference Guide Login</a></li>
			<li><a href="/quick_reference_guide_assetMgt.html" id="aboutusID">Quick Reference Guide - Asset Management</a></li>
			<li><a href="/quick_reference_guide_associates.html" id="aboutusID">Quick Reference Guide - Associates</a></li>
			<li><a href="/quick_reference_guide_candidates.html" id="aboutusID">Quick Reference Guide - Candidates</a></li>
			<li><a href="/quick_reference_guide_clients.html" id="aboutusID">Quick Reference Guide - Clients</a></li>
			<li><a href="/quick_reference_guide_companyOnboarding.html" id="aboutusID">Quick Reference Guide - Company Onboarding</a></li>
			<li><a href="/quick_reference_guide_expenses.html" id="aboutusID">Quick Reference Guide - Expenses</a></li>
			<li><a href="/quick_reference_guide_jobs.html" id="aboutusID">Quick Reference Guide - Jobs</a></li>
			<li><a href="/quick_reference_guide_onOffBoard.html" id="aboutusID">Quick Reference Guide - On Off Board</a></li>
			<li><a href="/quick_reference_guide_orgSettings.html" id="aboutusID">Quick Reference Guide - Org Settings</a></li>
			<li><a href="/quick_reference_guide_projects.html" id="aboutusID">Quick Reference Guide - Projects</a></li>
			<li><a href="/quick_reference_guide_signUp.html" id="aboutusID">Quick Reference Guide - Sign Up</a></li>
			<li><a href="/quick_reference_guide_timesheets.html" id="aboutusID">Quick Reference Guide - Timesheets</a></li>
			<li><a href="/quick_reference_guide_vendors.html" id="aboutusID">Quick Reference Guide - Vendors</a></li>
			  <%-- <li><a href="${SourceLeadWeb_sourceleadUrl}/addSourceleadTicket"  title="Add Issue" class="action newAdBtn mr5">Quick Reference Guide Login</a></li> --%>	
			</ul>						
                
                  
				</div>     
		      
 <div class="sideMask"></div>
 <div id="ticketcommentPopup"></div>
  </section>
 
 <jsp:include page="masterFooter.jsp" />	