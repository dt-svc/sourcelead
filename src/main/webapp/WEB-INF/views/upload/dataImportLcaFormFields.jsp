
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!-- below rows.count are equal to column indexes in excel and those fields are not mandatory-->
<c:forEach var="columnHeader" items="${columnHeaders}" varStatus="rows"> <!-- below rows.count are equal to column indexes in excel and those fields are mandatory-->
	<c:choose>
		<c:when test="${rows.count ==5||  rows.count == 6}">
			<div class="form-group">
				<label class="col-md-4 text-right mt5">${columnHeader} 
				</label>
				<div class="col-md-8 dropdown-select">
					<select class="chosen-select form-control" name="${headersNamesMap[rows.count]}" field-name="${headersFieldNamesMap[rows.count]}" >
						<option value="">- - - Select - - -</option>
						<c:forEach var="map" items="${dataWithCellId}">
							<option value="${map.key}" ${columnHeader == map.value.replace("*", "") ? 'selected' : ''}>${map.value}(column-${map.key})</option>
						</c:forEach>
					</select>
				</div>
				</div>
		</c:when>
		<c:otherwise>
			<div class="form-group">
				<label class="col-md-4 text-right mt5">${columnHeader}</label>
				<span
					class="text-danger paddingLeft">*</span>
				<div class="col-md-8 dropdown-select">
					<select class="chosen-select form-control"name="${headersNamesMap[rows.count]}" field-name="${headersFieldNamesMap[rows.count]}" data-validation="required">
						<option value="">- - - Select - - -</option>
						<c:forEach var="map" items="${dataWithCellId}">
							<option value="${map.key}" ${columnHeader == map.value.replace("*", "") ? 'selected' : ''}>${map.value}(column-${map.key})</option>
						</c:forEach>
					</select>
				</div>
				</div>
		</c:otherwise>
	</c:choose>
</c:forEach>

