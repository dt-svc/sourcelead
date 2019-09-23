<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<section class="content-wraper">

    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Financial Reports</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/reportsDashboard'/>">Reports Dashboard
</a> &raquo; Financial Reports</div>
    </section>
    
<div class="reportNewDash">
    
    <sec:authorize access="hasRole('ROLE_BILLING_REPORTS')">
    <a href="${appPath}/billingReports">
	<div class="repDashCta">
		<span><i class="fa fa-money"></i></span>
		<div class="repDashCtaWrap">
		<h2>Finance</h2><b>View</b>
		</div>
	</div>
	</a>
	</sec:authorize>
	
<sec:authorize access="hasRole('ROLE_A_R_AGING')">
<c:if test="${SourceLeadWeb_ats == 'Y'}">
	<a href='${appPath}/arAgingReport'>
	<div class="repDashCta">
		<span><i class="fa fa-calendar-plus-o"></i></span>
		<div class="repDashCtaWrap">
				<h2>A/R Aging</h2><b>View</b>
		</div>
	</div>
	</a>
</c:if>
</sec:authorize>	
		
<sec:authorize access="hasRole('ROLE_A_P_AGING')">
<c:if test="${SourceLeadWeb_vms == 'Y'}">
<a href='${appPath}/apAgingReport'>
	<div class="repDashCta">
		<span><i class="fa fa-calendar-plus-o"></i></span>
		<div class="repDashCtaWrap">

				<h2>A/P Aging</h2><b>View</b>

		</div>
	</div>
	</a>
</c:if>
</sec:authorize>			
	
	
<sec:authorize access="hasRole('ROLE_MISSING_INVOICE')">
<c:if test="${SourceLeadWeb_ats == 'Y'}">
<a href='${appPath}/missingInvoiceReport'>
	<div class="repDashCta">
		<span><i class="fa fa-file-text-o"></i></span>
		<div class="repDashCtaWrap">
	<h2>Missing Invoice(s)</h2><b>View</b>
		</div>
	</div>
</a>
</c:if>
</sec:authorize>

<sec:authorize access="hasRole('ROLE_RECEIVED_INVOICES')">
<c:if test="${SourceLeadWeb_ats == 'Y'}">	
<a href='${appPath}/receivedInvoiceReport'>
	<div class="repDashCta">
		<span><i class="fa fa-copy"></i></span>
		<div class="repDashCtaWrap">
			     <h2>Received Invoice(s)</h2><b>View</b>
		</div>
	</div>	
</a>   	
</c:if>
</sec:authorize>	
	

<sec:authorize access="hasRole('ROLE_MISSING_BILL')">
<c:if test="${SourceLeadWeb_vms == 'Y'}">
<a href='${appPath}/missingBillReport'>	
	<div class="repDashCta">
		<span><i class="fa fa-calendar-times-o"></i></span>
		<div class="repDashCtaWrap">
				<h2>Missing Bill(s)</h2><b>View</b>
		</div>
	</div>	
</a>
</c:if>
</sec:authorize>	
	
	
<sec:authorize access="hasRole('ROLE_PAID_BILL')">
<c:if test="${SourceLeadWeb_vms == 'Y'}">
<a href='${appPath}/paidBillReport'>						
	<div class="repDashCta">
		<span><i class="fa fa-calendar-check-o"></i></span>
		<div class="repDashCtaWrap">
				    <h2>Paid Bill(s)</h2><b>View</b>
		</div>
	</div>	
</a>				    
 </c:if>
</sec:authorize>
			
	<a href='${appPath}/assetmanagementGrowth'>						
		<div class="repDashCta">
			<span class="navIcons asstIcon"></span>
			<div class="repDashCtaWrap">
					    <h2>Asset Growth</h2><b>View</b>
			</div>
		</div>	
	</a>
	<sec:authorize access="hasRole('ROLE_MARGIN_SHEET_REPORT')">
<c:if test="${SourceLeadWeb_vms == 'Y'}">
<a href='${appPath}/marginSheetReports'>						
	<div class="repDashCta">
		<span><i class="fa fa-calendar-check-o"></i></span>
		<div class="repDashCtaWrap">
				    <h2>MarginSheetReport(s)</h2><b>View</b>
		</div>
	</div>	
</a>				    
 </c:if>
</sec:authorize>	
				
	<div class="clearfix"></div>	
</div>
    
    
  </section>

<jsp:include page="../masterFooter.jsp" />