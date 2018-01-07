
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<div class="form-group">
	<label class="col-md-4 text-right mt5">Emp Id</label>
	<div class="col-md-8 dropdown-select">
		<select class="form-control" name="employeeId" field-name="Emp Id">
			<option value="">- - - Select - - -</option>
			<c:forEach var="map" items="${dataWithCellId}">
				<option value="${map.key}">${map.value}(column-${map.key})</option>
			</c:forEach>
		</select>
	</div>
</div>

<div class="form-group">
	<label class="col-md-4 text-right mt5">Emp Name<span
		class="text-danger paddingLeft">*</span></label>
	<div class="col-md-8 dropdown-select">
		<select class="form-control" name="employeeName" field-name="Emp Name" data-validation="required">
			<option value="">- - - Select - - -</option>
			<c:forEach var="map" items="${dataWithCellId}">
				<option value="${map.key}">${map.value}(column-${map.key})</option>
			</c:forEach>
		</select>
	</div>
</div>

<div class="form-group">
	<label class="col-md-4 text-right mt5">Email<span
		class="text-danger paddingLeft">*</span></label>
	<div class="col-md-8 dropdown-select">
		<select class="form-control" name="employeeEmail" field-name="Email" data-validation="required">
			<option value="">- - - Select - - -</option>
			<c:forEach var="map" items="${dataWithCellId}">
				<option value="${map.key}">${map.value}(column-${map.key})</option>
			</c:forEach>
		</select>
	</div>
</div>

<div class="form-group">
	<label class="col-md-4 text-right mt5">Project</label>
	<div class="col-md-8 dropdown-select">
		<select class="form-control" name="project" field-name="Project">
			<option value="">- - - Select - - -</option>
			<c:forEach var="map" items="${dataWithCellId}">
				<option value="${map.key}">${map.value}(column-${map.key})</option>
			</c:forEach>
		</select>
	</div>
</div>

<div class="form-group">
	<label class="col-md-4 text-right mt5">Client</label>
	<div class="col-md-8 dropdown-select">
		<select class="form-control" name="clientName" field-name="Client">
			<option value="">- - - Select - - -</option>
			<c:forEach var="map" items="${dataWithCellId}">
				<option value="${map.key}">${map.value}(column-${map.key})</option>
			</c:forEach>
		</select>
	</div>
</div>

<div class="form-group">
	<label class="col-md-4 text-right mt5">Month-Year<span
		class="text-danger paddingLeft">*</span></label>
	<div class="col-md-8 dropdown-select">
		<select class="form-control" name="actualStartDate" field-name="Start Date" data-validation="required">
			<option value="">- - - Select - - -</option>
			<c:forEach var="map" items="${dataWithCellId}">
				<option value="${map.key}">${map.value}(column-${map.key})</option>
			</c:forEach>
		</select>
	</div>
</div>

<%-- <div class="form-group">
	<label class="col-md-4 text-right mt5">End Date<span
		class="text-danger paddingLeft">*</span></label>
	<div class="col-md-8 dropdown-select">
		<select class="form-control" name="actualEndDate" field-name="End Date" data-validation="required">
			<option value="">- - - Select - - -</option>
			<c:forEach var="map" items="${dataWithCellId}">
				<option value="${map.key}">${map.value}(column-${map.key})</option>
			</c:forEach>
		</select>
	</div>
</div> --%>

<c:forEach var="field" items="${fields}">
	<div class="form-group">
		<label class="col-md-4 text-right mt5">${field.labelName}</label>
		<div class="col-md-8 dropdown-select">
			<select class="form-control" name="${field.labelName}" field-name="${field.labelName}">
			<option value="">- - - Select - - -</option>
			<c:forEach var="map" items="${dataWithCellId}">
				<option value="${map.key}">${map.value}(column-${map.key})</option>
			</c:forEach>
			</select>
		</div>
	</div>
</c:forEach>

<div class="form-group">
	<label class="col-md-4 text-right mt5">Total Hrs</label>
	<div class="col-md-8 dropdown-select">
		<select class="form-control" name="totalHours" field-name="Total Hrs">
			<option value="">- - - Select - - -</option>
			<c:forEach var="map" items="${dataWithCellId}">
				<option value="${map.key}">${map.value}(column-${map.key})</option>
			</c:forEach>
		</select>
	</div>
</div>

<div class="form-group">
	<label class="col-md-4 text-right mt5">Billable Hrs</label>
	<div class="col-md-8 dropdown-select">
		<select class="form-control" name="billableHours" field-name="Billable Hrs">
			<option value="">- - - Select - - -</option>
			<c:forEach var="map" items="${dataWithCellId}">
				<option value="${map.key}">${map.value}(column-${map.key})</option>
			</c:forEach>
		</select>
	</div>
</div>

<div class="form-group">
	<label class="col-md-4 text-right mt5">Non Billable Hrs</label>
	<div class="col-md-8 dropdown-select">
		<select class="form-control" name="nnnBillableHours" field-name="Non Billable Hrs">
			<option value="">- - - Select - - -</option>
			<c:forEach var="map" items="${dataWithCellId}">
				<option value="${map.key}">${map.value}(column-${map.key})</option>
			</c:forEach>
		</select>
	</div>
</div>

<div class="form-group">
	<label class="col-md-4 text-right mt5">Status<span
		class="text-danger paddingLeft">*</span></label>
	<div class="col-md-8 dropdown-select">
		<select class="form-control" name="submissionStatus" field-name="Status" data-validation="required">
			<option value="">- - - Select - - -</option>
			<c:forEach var="map" items="${dataWithCellId}">
				<option value="${map.key}">${map.value}(column-${map.key})</option>
			</c:forEach>
		</select>
	</div>
</div>

<div class="form-group">
	<label class="col-md-4 text-right mt5">Comment</label>
	<div class="col-md-8 dropdown-select">
		<select class="form-control" name="weekComments" field-name="Comment">
			<option value="">- - - Select - - -</option>
			<c:forEach var="map" items="${dataWithCellId}">
				<option value="${map.key}">${map.value}(column-${map.key})</option>
			</c:forEach>
		</select>
	</div>
</div>







