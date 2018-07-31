<script src="/resources/js/tax/my_payroll.js" type="text/javascript"></script>
<section class="content-header clearfix">
	<div class="pull-left">
		<h2>My ${activeTaxTab == 'pays' ? 'Pays' : activeTaxTab =='forms' ? 'Forms' : 'Loans & Advances'}</h2>
	</div>
	<div class="pull-right brud-crum">Home &raquo; My Forms</div>
</section>

<div class="tabsMenu">
	<ul class="list-unstyled">
		<li class="${activeTaxTab == 'pays' ? 'current' : ''}"><a href="${appPath}/myPayroll/pays" class="${activeTaxTab == 'pays' ? 'active' : ''}">Pays</a></li>
		<li class="${activeTaxTab == 'forms' ? 'current' : ''}"><a href="${appPath}/myPayroll/forms" class="${activeTaxTab == 'forms' ? 'active' : ''}">Forms</a></li>
		<li class="${activeTaxTab == 'loansAndAdvances' ? 'current' : ''}"><a href="${appPath}/myPayroll/loansAndAdvances" class="${activeTaxTab == 'loansAndAdvances' ? 'active' : ''}">Loans &amp; Advances</a></li>
	</ul>
</div>

<div class="clearfix"></div>
<div class='project_inn_mask'></div>