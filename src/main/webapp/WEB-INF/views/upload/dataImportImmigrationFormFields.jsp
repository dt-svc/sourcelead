
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<c:forEach var="columnHeader" items="${columnHeaders}" varStatus="rows">
	<c:choose><!-- below row numbers are equal to column indexes in excel and those fields are mandatory-->
		<c:when test="${rows.count == 4 || rows.count == 10 || rows.count == 11
		  || rows.count == 12 || rows.count == 13|| rows.count == 15|| 
			 rows.count == 16|| rows.count == 20 || rows.count == 22}">
			<div class="form-group">
				<label class="col-md-4 text-right mt5">${columnHeader} <span
					class="text-danger paddingLeft">*</span>
				</label>
				<div class="col-md-8 dropdown-select">
					<select class="chosen-select form-control" name="${headersNamesMap[rows.count]}" field-name="${headersFieldNamesMap[rows.count]}" data-validation="required">
						<option value="">- - - Select - - -</option>
						<c:forEach var="map" items="${dataWithCellId}">
							<option value="${map.key}">${map.value}(column-${map.key})</option>
						</c:forEach>
					</select>
				</div>
				</div>
		</c:when>
		<c:otherwise>
			<div class="form-group">
				<label class="col-md-4 text-right mt5">${columnHeader}</label>
				<div class="col-md-8 dropdown-select">
					<select class="chosen-select form-control"name="${headersNamesMap[rows.count]}" field-name="${headersFieldNamesMap[rows.count]}">
						<option value="">- - - Select - - -</option>
						<c:forEach var="map" items="${dataWithCellId}">
							<option value="${map.key}">${map.value}(column-${map.key})</option>
						</c:forEach>
					</select>
				</div>
				</div>
		</c:otherwise>
	</c:choose>
</c:forEach>

