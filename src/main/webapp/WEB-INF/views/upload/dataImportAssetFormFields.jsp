
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<div class="form-group">
	<label class="col-md-4 text-right mt5">Category
	<span class="text-danger paddingLeft">*</span>
	</label>
	<div class="col-md-8 dropdown-select">
		<select class="chosen-select form-control" name="category" field-name="Category" data-validation="required">
			<option value="">- - - Select - - -</option>
			<c:forEach var="map" items="${dataWithCellId}">
				<option value="${map.key}">${map.value}(column-${map.key})</option>
			</c:forEach>
		</select>
	</div>
</div>

<div class="form-group">
	<label class="col-md-4 text-right mt5">Working Status<span
		class="text-danger paddingLeft">*</span></label>
	<div class="col-md-8 dropdown-select">
		<select class="chosen-select form-control" name="workingStatus" field-name="Working Status" data-validation="required">
			<option value="">- - - Select - - -</option>
			<c:forEach var="map" items="${dataWithCellId}">
				<option value="${map.key}">${map.value}(column-${map.key})</option>
			</c:forEach>
		</select>
	</div>
</div>

<div class="form-group">
	<label class="col-md-4 text-right mt5">Serial Number<span
		class="text-danger paddingLeft">*</span></label>
	<div class="col-md-8 dropdown-select">
		<select class="chosen-select form-control" name="sno" field-name="Serial Number" data-validation="required">
			<option value="">- - - Select - - -</option>
			<c:forEach var="map" items="${dataWithCellId}">
				<option value="${map.key}">${map.value}(column-${map.key})</option>
			</c:forEach>
		</select>
	</div>
</div>

<div class="form-group">
	<label class="col-md-4 text-right mt5">Make</label>
	<div class="col-md-8 dropdown-select">
		<select class="chosen-select form-control" name="make" field-name="Make">
			<option value="">- - - Select - - -</option>
			<c:forEach var="map" items="${dataWithCellId}">
				<option value="${map.key}">${map.value}(column-${map.key})</option>
			</c:forEach>
		</select>
	</div>
</div>

<div class="form-group">
	<label class="col-md-4 text-right mt5">Model</label>
	<div class="col-md-8 dropdown-select">
		<select class="chosen-select form-control" name="model" field-name="Model">
			<option value="">- - - Select - - -</option>
			<c:forEach var="map" items="${dataWithCellId}">
				<option value="${map.key}">${map.value}(column-${map.key})</option>
			</c:forEach>
		</select>
	</div>
</div>

<div class="form-group">
	<label class="col-md-4 text-right mt5">Name</label>
	<div class="col-md-8 dropdown-select">
		<select class="chosen-select form-control" name="name" field-name="Name">
			<option value="">- - - Select - - -</option>
			<c:forEach var="map" items="${dataWithCellId}">
				<option value="${map.key}">${map.value}(column-${map.key})</option>
			</c:forEach>
		</select>
	</div>
</div>


<div class="form-group">
	<label class="col-md-4 text-right mt5">Memory</label>
	<div class="col-md-8 dropdown-select">
		<select class="chosen-select form-control" name="memory" field-name="Memory">
			<option value="">- - - Select - - -</option>
			<c:forEach var="map" items="${dataWithCellId}">
				<option value="${map.key}">${map.value}(column-${map.key})</option>
			</c:forEach>
		</select>
	</div>
</div>



<div class="form-group">
	<label class="col-md-4 text-right mt5">Hard Disk</label>
	<div class="col-md-8 dropdown-select">
		<select class="chosen-select form-control" name="harddisk" field-name="Hard Disk">
			<option value="">- - - Select - - -</option>
			<c:forEach var="map" items="${dataWithCellId}">
				<option value="${map.key}">${map.value}(column-${map.key})</option>
			</c:forEach>
		</select>
	</div>
</div>

<div class="form-group">
	<label class="col-md-4 text-right mt5">Description</label>
	<div class="col-md-8 dropdown-select">
		<select class="chosen-select form-control" name="description" field-name="Description">
			<option value="">- - - Select - - -</option>
			<c:forEach var="map" items="${dataWithCellId}">
				<option value="${map.key}">${map.value}(column-${map.key})</option>
			</c:forEach>
		</select>
	</div>
</div>



<div class="form-group">
	<label class="col-md-4 text-right mt5">Monitor</label>
	<div class="col-md-8 dropdown-select">
		<select class="chosen-select form-control" name="monitor" field-name="Monitor">
			<option value="">- - - Select - - -</option>
			<c:forEach var="map" items="${dataWithCellId}">
				<option value="${map.key}">${map.value}(column-${map.key})</option>
			</c:forEach>
		</select>
	</div>
</div>

<div class="form-group">
	<label class="col-md-4 text-right mt5">Shift A</label>
	<div class="col-md-8 dropdown-select">
		<select class="chosen-select form-control" name="shiftAEmployee" field-name="Memory">
			<option value="">- - - Select - - -</option>
			<c:forEach var="map" items="${dataWithCellId}">
				<option value="${map.key}">${map.value}(column-${map.key})</option>
			</c:forEach>
		</select>
	</div>
</div>

<div class="form-group">
	<label class="col-md-4 text-right mt5">Shift B</label>
	<div class="col-md-8 dropdown-select">
		<select class="chosen-select form-control" name="shiftBEmployee" field-name="Memory">
			<option value="">- - - Select - - -</option>
			<c:forEach var="map" items="${dataWithCellId}">
				<option value="${map.key}">${map.value}(column-${map.key})</option>
			</c:forEach>
		</select>
	</div>
</div>







