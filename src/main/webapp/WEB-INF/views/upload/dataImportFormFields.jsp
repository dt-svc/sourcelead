<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<c:forEach var="field" items="${fields}">
	<div class="form-group">
		<c:if test="${empty field.slDependantField}">
			<label class="col-md-4 text-right mt5">${field.labelName}<span
				class="text-danger paddingLeft">${field.isFieldRequired == "1" ? "*" : ""}</span></label>
		</c:if>
		<c:if test="${!empty field.slDependantField}">
			<label class="col-md-4 text-right mt5">${field.labelName}</label>
		</c:if>
		<div class="col-md-8 dropdown-select">
			<c:if test="${empty field.slDependantField}">
				<select class="chosen-select form-control" name="${field.fieldName}"
					field-name="${field.labelName}"
					${field.isFieldRequired == "1" ? "data-validation='required'" : ""}>
			</c:if>
			<c:if test="${!empty field.slDependantField}">
				<select class="chosen-select form-control" name="${field.fieldName}"
					field-name="${field.labelName}">
			</c:if>
			<option value="">- - - Select - - -</option>
			<c:forEach var="map" items="${dataWithCellId}">
				<option value="${map.key}" ${field.labelName == map.value.replace("*", "").trim() ? 'selected' : ''}>${map.value}(column-${map.key})</option>
			</c:forEach>
			</select>
		</div>
	</div>
</c:forEach>
