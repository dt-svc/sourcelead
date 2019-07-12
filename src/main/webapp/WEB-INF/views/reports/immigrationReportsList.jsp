<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<section class="content-wraper">

    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Immigration Reports</h2>
      </div>
      <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo; <a href="${SourceLeadWeb_sourceleadUrl}/reportsDashboard">Reports Dashboard
</a> &raquo; Immigration Reports</div>
    </section>
    
<div class="reportNewDash">
    
 <%-- <sec:authorize access="hasRole('ROLE_ALL_ASSOCIATE_ROI')"> --%>
	<a href="${SourceLeadWeb_sourceleadUrl}/petitionReport">
	<div class="repDashCta">
		<span><i class="fa fa-file-text-o"></i></span>
		<div class="repDashCtaWrap">
		 <h2>Petition Report</h2><b>View</b>
		</div>
	</div>
	</a>
	<%-- </sec:authorize> --%>
	
	<%-- <sec:authorize access="hasRole('ROLE_CAMPAIGN_REPORTS')"> --%>
	<a href="${SourceLeadWeb_sourceleadUrl}/petitionAttorneyReport">	
	<div class="repDashCta">
		<span><i class="fa fa-file-word-o"></i></span>
		<div class="repDashCtaWrap">
		<h2>Petition Attorney Report</h2><b>View</b>
		</div>
	</div>
	</a>
	<%-- </sec:authorize>	 --%>	
				<a href='${SourceLeadWeb_sourceleadUrl}/lcaReport'>
				<div class="repDashCta">
		<span><i class="fa fa-clipboard"></i></span>
		<div class="repDashCtaWrap">
		<h2>LCA Report</h2><b>View</b>
		</div>
	</div>
	</a>
				
	<div class="clearfix"></div>	
</div>
    
    
  </section>

<jsp:include page="../masterFooter.jsp" />