<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<section class="content-wraper">

    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Associate Reports</h2>
      </div>
      <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo; <a href="${SourceLeadWeb_sourceleadUrl}/reportsDashboard">Reports Dashboard
</a> &raquo; Associate Reports</div>
    </section>
    
<div class="reportNewDash">
    
    	<sec:authorize access="hasRole('ROLE_ASSOCIATE_ROI')">
    	<a href="${appPath}/payrollReport">
	<div class="repDashCta">
		<span><i class="fa fa-user-circle"></i></span>
		<div class="repDashCtaWrap">
		<h2>Associate(s) ROI</h2><b>View</b>
		</div>
	</div>
	</a>
	</sec:authorize>
	
	<sec:authorize access="hasRole('ROLE_ALL_ASSOCIATE_ROI')">
	<a href="${appPath}/payrollAllBalanceReport">
	<div class="repDashCta">
		<span><i class="fa fa-users"></i></span>
		<div class="repDashCtaWrap">
		 <h2>All Associates ROI</h2><b>View</b>
		</div>
	</div>
	</a>
	</sec:authorize>
	
	<sec:authorize access="hasRole('ROLE_ASSOCIATE_JOINED_REPORT')">
	<a href="${appPath}/associateJoineReports">	
	<div class="repDashCta">
		<span><i class="fa fa-bullhorn"></i></span>
		<div class="repDashCtaWrap">
		<h2>Associate Joined Report</h2><b>View</b>
		</div>
	</div>
	</a>
	</sec:authorize>		
	<a href='${appPath}/associateClockInSummaryReport'>
	<div class="repDashCta">
		<span><i class="fa fa-users"></i></span>
		<div class="repDashCtaWrap">
		<h2>Associate Clock-In Summary Report</h2><b>View</b>
		</div>
	</div>
	</a>
				<a href='${appPath}/availableAssociatesReport'>
				<div class="repDashCta">
		<span><i class="fa fa-users"></i></span>
		<div class="repDashCtaWrap">
		<h2>Available Associate Report</h2><b>View</b>
		</div>
	</div>
	</a>
				
				
	<c:if test="${SourceLeadWeb_orgLocations.country == 'United States'}">
	<a href='${appPath}/loansAndAdvancesReport'>
				<div class="repDashCta">
		<span><i class="fa fa-dollar"></i></span>
		<div class="repDashCtaWrap">
		<h2>Loans And Advances Report</h2><b>View</b>
		</div>
	</div>
	</a>
	</c:if>
	
	<c:if test="${SourceLeadWeb_orgLocations.country == 'India'}">
	<a href='${appPath}/loansAndAdvancesReport'>
				<div class="repDashCta">
		<span><i class="fa fa-rupee"></i></span>
		<div class="repDashCtaWrap">
		<h2>Loans And Advances Report</h2><b>View</b>
		</div>
	</div>
	</a>
	</c:if>
				
	<div class="clearfix"></div>	
</div>
    
    
  </section>

<jsp:include page="../masterFooter.jsp" />