<%-- <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="tabsMenu">
	<ul class="list-unstyled">
	<c:forEach items="${formList}" var="form">
		<li class="${activeFormTabUUID == form.uuid ? 'current' : ''}">
			<a href="${appPath}/itDeclaration/getForm?uuid=${form.uuid}" class="${activeFormTabUUID == 'form.uuid' ? 'active' : ''}">${form.name}</a>
		</li>
	</c:forEach>
	</ul>
</div> --%>