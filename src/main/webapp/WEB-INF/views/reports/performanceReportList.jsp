<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<section class="content-wraper">

    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Performance Reports</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/reportsDashboard'/>">Reports Dashboard
</a> &raquo; Performance Reports</div>
    </section>
    
<div class="reportNewDash">
    
	<div class="repDashCta">
		<span><i class="fa fa-bar-chart"></i></span>
		<div class="repDashCtaWrap">
			<sec:authorize access="hasRole('ROLE_RECRUITERS_REPORT')">
				<a href='${appPath}/recruitersReports'><h2>Recruiter(s) Report</h2><b>View</b></a>
			</sec:authorize>
		</div>
	</div>
	
	<div class="repDashCta">
		<span><i class="fa fa-suitcase"></i></span>
		<div class="repDashCtaWrap">
			<sec:authorize access="hasRole('ROLE_VENDORS_REPORT')">
				 <a href="${appPath}/vendorsReports"><h2>Vendor(s) Report</h2><b>View</b></a>
			 </sec:authorize>
		</div>
	</div>
		
	<div class="repDashCta">
		<span><i class="fa fa-pie-chart"></i></span>
		<div class="repDashCtaWrap">
				<sec:authorize access="hasRole('ROLE_CLIENT_REPORT')">
				<a href="${appPath}/perfomanceClientReports"><h2>Client(s) Report</h2><b>View</b></a>
				</sec:authorize>		
		</div>
	</div>
	
	<div class="repDashCta">
		<span><i class="fa fa-ban"></i></span>
		<div class="repDashCtaWrap">
			<sec:authorize access="hasRole('ROLE_APPLICANT_DECLINE')">
				<a href="${appPath}/applicantDeclineReports"><h2>Applicant(s) Decline</h2><b>View</b></a>
			</sec:authorize>
		</div>
	</div>	
	
	<div class="repDashCta">
		<span><i class="fa fa-undo"></i></span>
		<div class="repDashCtaWrap">
			<sec:authorize access="hasRole('ROLE_APPLICANT_BACKOUT')">
				<a href="${appPath}/applicantsBackoutReport"><h2>Applicant(s) Backout</h2><b>View</b></a>
			</sec:authorize>
		</div>
	</div>	
	
	<div class="repDashCta">
		<span><i class="fa fa-briefcase"></i></span>
		<div class="repDashCtaWrap">
			<sec:authorize access="hasRole('ROLE_JOBS_PIPELINE')">
				<a href="${appPath}/jobPipelineReports"><h2>Job(s) Pipeline</h2><b>View</b></a>
			</sec:authorize>
		</div>
	</div>	
				
	<div class="repDashCta">
		<span><i class="fa fa-group"></i></span>
		<div class="repDashCtaWrap">
			<sec:authorize access="hasRole('ROLE_APPLICANT_SOURCE')">
				<a href="${appPath}/applicantBySource"><h2>Applicant(s) by Source</h2><b>View</b></a>
			</sec:authorize>
		</div>
	</div>
	
	<div class="repDashCta">
		<span><i class="fa fa-clock-o"></i></span>
		<div class="repDashCtaWrap">
				<sec:authorize access="hasRole('ROLE_TIME_TO_FILL')">
				<a href="${appPath}/timeToFill"><h2></>Time to Fill</h2><b>View</b></a>
				</sec:authorize>
		</div>
	</div>
				
	<div class="repDashCta">
		<span><i class="fa fa-handshake-o"></i></span>
		<div class="repDashCtaWrap">
				<sec:authorize access="hasRole('ROLE_APPLICANT_RECEIVED')">
				<a href="${appPath}/applicantsReceivedListForReports"><h2>Applicant(s) Received</h2><b>View</b></a>
				</sec:authorize>
		</div>
	</div>
				
	<div class="repDashCta">
		<span><i class="fa fa-line-chart"></i></span>
		<div class="repDashCtaWrap">
				<sec:authorize access="hasRole('ROLE_JOBS_STAGES')">
				<a href="${appPath}/requirementStagesListForReports"><h2>Job(s) Stage</h2><b>View</b></a>
				</sec:authorize>	
		</div>
	</div>
				
	<div class="clearfix"></div>	
</div>
    
    
  </section>

<jsp:include page="../masterFooter.jsp" />