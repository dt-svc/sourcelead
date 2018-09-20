<jsp:include page="../../masterHeader.jsp" />
<jsp:include page="../../masterSideNav.jsp" />
<section class="content-wraper">
<section class="content-header clearfix">
	<div class="pull-left">
		<h2>My ${activeTaxTab == 'pays' ? 'Pays' : activeTaxTab =='forms' ? 'Forms' : 'Loans & Advances'}</h2>
	</div>
	<div class="pull-right brud-crum">Home &raquo; My Forms</div>
</section>	
	
	<div class="project_wrap">
		<jsp:include page="../my_payroll/myPayrollHeader.jsp"></jsp:include>
		<p>Under Construction</p>
	</div>
	
</section>

