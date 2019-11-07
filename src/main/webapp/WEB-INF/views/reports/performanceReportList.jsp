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
      <div class="pull-right brud-crum"><a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/reportsDashboard'/>">Reports Dashboard
</a> &raquo; Performance Reports</div>
    </section>
    
<div class="reportNewDash">

	<sec:authorize access="hasRole('ROLE_RECRUITERS_REPORT')">
		<a href='${appPath}/offerAcceptanceRate'>
			<div class="repDashCta">
				<span><i class="fa fa-bar-chart"></i></span>
				<div class="repDashCtaWrap">
					<h2>Offer Acceptance Rate</h2><b>View</b>
				</div>
			</div>
		</a>
	</sec:authorize>
	
 	<sec:authorize access="hasRole('ROLE_RECRUITERS_REPORT')">
		<a href='${SourceLeadWeb_sourceleadUrl}/applicantsjoinedlist'>
			<div class="repDashCta">
				<span><i class="fa fa-bar-chart"></i></span>
				<div class="repDashCtaWrap">
					<h2>Applicants Joined Report</h2><b>View</b>
				</div>
			</div>
		</a>
	</sec:authorize>
	
    <sec:authorize access="hasRole('ROLE_RECRUITERS_REPORT')">
	<a href='${SourceLeadWeb_sourceleadUrl}/recruitersReports'>
		<div class="repDashCta">
			<span><i class="fa fa-bar-chart"></i></span>
			<div class="repDashCtaWrap">
				<h2>Recruiter(s) Report</h2><b>View</b>
			</div>
		</div>
	</a>
	</sec:authorize>
	 <sec:authorize access="hasRole('ROLE_MY_PERFORMANCE_REPORT')">
	<a href='${SourceLeadWeb_sourceleadUrl}/myPerformanceReports'>
		<div class="repDashCta">
			<span><i class="fa fa-bar-chart"></i></span>
			<div class="repDashCtaWrap">
				<h2>My Performance Report</h2><b>View</b>
			</div>
		</div>
	</a>
	</sec:authorize>
	<sec:authorize access="hasRole('ROLE_VENDORS_REPORT')">
	<a href="${SourceLeadWeb_sourceleadUrl}/vendorsReports">
	<div class="repDashCta">
		<span><i class="fa fa-suitcase"></i></span>
		<div class="repDashCtaWrap">
		 <h2>Vendor(s) Report</h2><b>View</b>
		</div>
	</div>
	</a>
	 </sec:authorize>
	 
		<sec:authorize access="hasRole('ROLE_CLIENT_REPORT')">
		<a href="${SourceLeadWeb_sourceleadUrl}/perfomanceClientReports">
	<div class="repDashCta">
		<span><i class="fa fa-pie-chart"></i></span>
		<div class="repDashCtaWrap">
		<h2>Client(s) Report</h2><b>View</b>
		</div>
	</div>
	</a>
	</sec:authorize>		
	
	<sec:authorize access="hasRole('ROLE_APPLICANT_DECLINE')">
	<a href="${SourceLeadWeb_sourceleadUrl}/applicantDeclineReports">
	<div class="repDashCta">
		<span><i class="fa fa-ban"></i></span>
		<div class="repDashCtaWrap">
		<h2>Applicant(s) Decline</h2><b>View</b>
		</div>
	</div>	
	</a>
	</sec:authorize>
	
	<sec:authorize access="hasRole('ROLE_APPLICANT_BACKOUT')">
	<a href="${SourceLeadWeb_sourceleadUrl}/applicantsBackoutReport">
	<div class="repDashCta">
		<span><i class="fa fa-undo"></i></span>
		<div class="repDashCtaWrap">
		<h2>Applicant(s) Backout</h2><b>View</b>
		</div>
	</div>
	</a>	
	</sec:authorize>
	
	<sec:authorize access="hasRole('ROLE_JOBS_PIPELINE')">
	<a href="${SourceLeadWeb_sourceleadUrl}/jobPipelineReports">
	<div class="repDashCta">
		<span><i class="fa fa-briefcase"></i></span>
		<div class="repDashCtaWrap">
		<h2>Job(s) Pipeline</h2><b>View</b>
		</div>
	</div>	
	</a>
	</sec:authorize>
		
	<sec:authorize access="hasRole('ROLE_APPLICANT_SOURCE')">	
	<a href="${SourceLeadWeb_sourceleadUrl}/applicantBySource">		
	<div class="repDashCta">
		<span><i class="fa fa-group"></i></span>
		<div class="repDashCtaWrap">
		<h2>Applicant(s) by Source</h2><b>View</b>
		</div>
	</div>
	</a>
	</sec:authorize>
	
	<sec:authorize access="hasRole('ROLE_TIME_TO_FILL')">
	<a href="${SourceLeadWeb_sourceleadUrl}/timeToFill">
	<div class="repDashCta">
		<span><i class="fa fa-clock-o"></i></span>
		<div class="repDashCtaWrap">
		<h2></>Time to Fill</h2><b>View</b>
		</div>
	</div>
	</a>
	</sec:authorize>
	
	<sec:authorize access="hasRole('ROLE_APPLICANT_RECEIVED')">	
	<a href="${SourceLeadWeb_sourceleadUrl}/applicantsReceivedListForReports">		
	<div class="repDashCta">
		<span><i class="fa fa-handshake-o"></i></span>
		<div class="repDashCtaWrap">
		<h2>Applicant(s) Received</h2><b>View</b>
		</div>
	</div>
	</a>
	</sec:authorize>
	
	<sec:authorize access="hasRole('ROLE_JOBS_STAGES')">	
	<a href="${SourceLeadWeb_sourceleadUrl}/requirementStagesListForReports">		
	<div class="repDashCta">
		<span><i class="fa fa-line-chart"></i></span>
		<div class="repDashCtaWrap">
		<h2>Job(s) Stage</h2><b>View</b>
		</div>
	</div>
	</a>
	</sec:authorize>
	
	 <a href="${SourceLeadWeb_sourceleadUrl}/jobsPerformanceReport">		
	<div class="repDashCta">
		<span><i class="fa fa-id-badge"></i></span>
		<div class="repDashCtaWrap">
		<h2>Jobs Report</h2><b>View</b>
		</div>
	</div>
	</a>
				
	<div class="clearfix"></div>	
</div>
    
    
  </section>

<jsp:include page="../masterFooter.jsp" />