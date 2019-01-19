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
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/reportsDashboard'/>">Reports Dashboard
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
	
	<sec:authorize access="hasRole('ROLE_CAMPAIGN_REPORTS')">
	<a href="${appPath}/campaignStats">	
	<div class="repDashCta">
		<span><i class="fa fa-bullhorn"></i></span>
		<div class="repDashCtaWrap">
		<h2>Campaign(s) Report</h2><b>View</b>
		</div>
	</div>
	</a>
	</sec:authorize>		
	
				
	<div class="clearfix"></div>	
</div>
    
    
  </section>

<jsp:include page="../masterFooter.jsp" />