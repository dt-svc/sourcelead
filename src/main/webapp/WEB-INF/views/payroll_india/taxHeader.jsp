<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	var viewPage = "${viewPage}";
</script>
<div id="loading"></div>
<div class="success_pop">
  <i class="fa fa-check-circle"></i><span>Operation executed successfully...</span>
</div>
<section class="content-header clearfix">
	<div class="pull-left" >
		<h2> ${(activeTaxTab == 'state' || activeTaxTab =='createStateTax') ? 'State Tax' : (activeTaxTab == 'central' || activeTaxTab =='createCentralTax') ? 'Central Tax' : (formTabType == 'createForm' ? 'Create Form' : formTabType == 'viewForm' ? 'View Form' : 'Forms')}</h2>
	</div>
	<c:choose>
		<c:when test="${formTabType == 'createForm' || formTabType == 'viewForm'}">
			<div class="pull-right brud-crum">
				<a href="/app/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo; 
				<a href="/app/form/list">Forms List</a> &raquo; 
				${formTabType == 'createForm' ? 'Create Form' :'View Form'}
			</div>
		</c:when>
		<c:otherwise>
			<div class="pull-right brud-crum"><a href="/app/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo; ${(activeTaxTab == 'state' || activeTaxTab =='createStateTax') ? 'State Tax' : (activeTaxTab == 'central' || activeTaxTab =='createCentralTax') ? 'Central Tax' : (formTabType == 'createForm' ? 'Create Form' : formTabType == 'viewForm' ? 'View Form' : 'Forms')}</div>
		</c:otherwise>
	</c:choose>
</section>

<div class="tabsMenu">
	<ul class="list-unstyled">
		<li class="${activeTaxTab == 'state' || activeTaxTab =='createStateTax' ? 'current' : ''}"><a
			href="${appPath}/stateTax/list" class="${activeTaxTab == 'state' || activeTaxTab =='createStateTax' ? 'active' : ''}">State</a></li>
		<li class="${activeTaxTab == 'central' || activeTaxTab == 'createCentralTax' ? 'current' : ''}"><a
			href="${appPath}/centralTax/list" class="${activeTaxTab == 'central' || activeTaxTab =='createCentralTax' ? 'active' : ''}">Central</a></li>
		<li class="${activeTaxTab == 'forms' ? 'current' : ''}"><a
			href="${appPath}/form/list" class="${activeTaxTab == 'forms' ? 'active' : ''}">Forms</a></li>	
	</ul>
</div>

<div class="clearfix"></div>
<div class='project_inn_mask'></div>
<div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>
	<p>You are choosing to delete the Tax</p>
	<div>&nbsp;&nbsp;&nbsp;</div>
	<b>
	<c:choose>
		<c:when test="${activeTaxTab == 'forms'}">
			<c:if test="${viewPage == 'payroll_india/addForm'}">
				<input id='confirmId' type='button' value='Ok' onclick='tax.changeFormSectionStatus()'>
			</c:if>
			<c:if test="${viewPage == 'payroll_india/form_list'}">
				<input id='confirmId' type='button' value='Ok' onclick='tax.changeFormStatus()'>
			</c:if>
		</c:when>
		<c:otherwise>
			<input id='confirmId' type='button' value='Ok' onclick='tax.changeTaxStatus()'>
		</c:otherwise>
	</c:choose>
		<input class='u_can_color' type='button' value='Cancel' onClick='tax.closeChangeStatusPopup()'>
	</b>
</div>