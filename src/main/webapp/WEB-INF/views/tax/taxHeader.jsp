<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="loading"></div>
<section class="content-header clearfix">
	<div class="pull-left">
		<h2>State Tax</h2>
	</div>
	<div class="pull-right brud-crum">Home &raquo; State Tax</div>
</section>

<div class="tabsMenu">
	<ul class="list-unstyled">
		<li class="${activeTaxTab=='state'?'current':''}"><a
			href="${appPath}/stateTax/list" class="${activeTaxTab=='state'?'active':''}">State</a></li>
		<li class="${activeTaxTab=='central'?'current':''}"><a
			href="${appPath}/centralTax/list" class="${activeTaxTab=='central'?'active':''}">Central</a></li>
	</ul>
</div>

<div class="clearfix"></div>
