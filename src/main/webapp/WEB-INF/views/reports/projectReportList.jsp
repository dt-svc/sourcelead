<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<section class="content-wraper">

    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Project Reports</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/reportsDashboard'/>">Reports Dashboard
</a> &raquo; Project Reports</div>
    </section>
    
<div class="reportNewDash">
	
<a href="${appPath}/projectReport">
	<div class="repDashCta">
		<span><i class="fa fa-bar-chart"></i></span>
		<div class="repDashCtaWrap">
		<h2>Project Report</h2><b>View</b>
		</div>
	</div>
</a>
	
<a href="${appPath}/employeeTasksReport">
	<div class="repDashCta">
		<span><i class="fa fa-user-circle"></i></span>
		<div class="repDashCtaWrap">
		<h2>Employee Tasks</h2><b>View</b>
		</div>
	</div>	
</a>
     
	
 <a href="${appPath}/overdueTaskReport">
	<div class="repDashCta">
		<span><i class="fa fa-clock-o"></i></span>
		<div class="repDashCtaWrap">
		 <h2>Over Due Report</h2><b>View</b>
		</div>
	</div>
</a>
		
	<div class="clearfix"></div>	
</div>
    
    
  </section>

<jsp:include page="../masterFooter.jsp" />