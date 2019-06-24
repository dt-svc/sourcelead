
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
	<label class="col-md-4 text-right mt5">Name </label>
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
	<label class="col-md-4 text-right mt5">Cpu</label>
	<div class="col-md-8 dropdown-select">
		<select class="chosen-select form-control" name="cpu" field-name="CPU">
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
	<label class="col-md-4 text-right mt5">Operating System</label>
	<div class="col-md-8 dropdown-select">
		<select class="chosen-select form-control" name="operatingSystem" field-name="Operating System">
			<option value="">- - - Select - - -</option>
			<c:forEach var="map" items="${dataWithCellId}">
				<option value="${map.key}">${map.value}(column-${map.key})</option>
			</c:forEach>
		</select>
	</div>
</div>

<div class="form-group">
	<label class="col-md-4 text-right mt5">OS Key</label>
	<div class="col-md-8 dropdown-select">
		<select class="chosen-select form-control" name="osKey" field-name="OS Key">
			<option value="">- - - Select - - -</option>
			<c:forEach var="map" items="${dataWithCellId}">
				<option value="${map.key}">${map.value}(column-${map.key})</option>
			</c:forEach>
		</select>
	</div>
</div>

<div class="form-group">
	<label class="col-md-4 text-right mt5">Anti-Virus</label>
	<div class="col-md-8 dropdown-select">
		<select class="chosen-select form-control" name="antiVirus" field-name="Anti-Virus">
			<option value="">- - - Select - - -</option>
			<c:forEach var="map" items="${dataWithCellId}">
				<option value="${map.key}">${map.value}(column-${map.key})</option>
			</c:forEach>
		</select>
	</div>
</div>

<div class="form-group">
	<label class="col-md-4 text-right mt5">Manufactured Date</label>
	<div class="col-md-8 dropdown-select">
		<select class="chosen-select form-control" name="issueDate" field-name="Manufactured Date">
			<option value="">- - - Select - - -</option>
			<c:forEach var="map" items="${dataWithCellId}">
				<option value="${map.key}">${map.value}(column-${map.key})</option>
			</c:forEach>
		</select>
	</div>
</div>

<div class="form-group">
	<label class="col-md-4 text-right mt5">Purchase Date</label>
	<div class="col-md-8 dropdown-select">
		<select class="chosen-select form-control" name="purchaseDate" field-name="Purchase Date">
			<option value="">- - - Select - - -</option>
			<c:forEach var="map" items="${dataWithCellId}">
				<option value="${map.key}">${map.value}(column-${map.key})</option>
			</c:forEach>
		</select>
	</div>
</div>

<div class="form-group">
	<label class="col-md-4 text-right mt5">Cost</label>
	<div class="col-md-8 dropdown-select">
		<select class="chosen-select form-control" name="cost" field-name="Cost">
			<option value="">- - - Select - - -</option>
			<c:forEach var="map" items="${dataWithCellId}">
				<option value="${map.key}">${map.value}(column-${map.key})</option>
			</c:forEach>
		</select>
	</div>
</div>

<div class="form-group">
	<label class="col-md-4 text-right mt5">Depreciation Assessment Date</label>
	<div class="col-md-8 dropdown-select">
		<select class="chosen-select form-control" name="depreciationDate" field-name="Depreciation Assessment Date">
			<option value="">- - - Select - - -</option>
			<c:forEach var="map" items="${dataWithCellId}">
				<option value="${map.key}">${map.value}(column-${map.key})</option>
			</c:forEach>
		</select>
	</div>
</div>


<div class="form-group">
	<label class="col-md-4 text-right mt5">Depreciation Amount</label>
	<div class="col-md-8 dropdown-select">
		<select class="chosen-select form-control" name="depreciationAmount" field-name="Depreciation Amount">
			<option value="">- - - Select - - -</option>
			<c:forEach var="map" items="${dataWithCellId}">
				<option value="${map.key}">${map.value}(column-${map.key})</option>
			</c:forEach>
		</select>
	</div>
</div>


<div class="form-group">
	<label class="col-md-4 text-right mt5">Vendor Details</label>
	<div class="col-md-8 dropdown-select">
		<select class="chosen-select form-control" name="vendorDetails" field-name="Vendor Details">
			<option value="">- - - Select - - -</option>
			<c:forEach var="map" items="${dataWithCellId}">
				<option value="${map.key}">${map.value}(column-${map.key})</option>
			</c:forEach>
		</select>
	</div>
</div>

<%-- <c:forEach var="shift" items="${shiftList}" varStatus="shiftOrder">
<div class="form-group">
<label class="col-md-4 text-right mt5">${shift.shiftName}</label>
<div class="col-md-8 dropdown-select">
 <input type="hidden" name="assignAsset[${shiftOrder.index}].shift" value="${shift.id}"/>
		<select class="chosen-select form-control" name="assignAsset[${shiftOrder.index}].xlCol">
			<option value="">- - - Select - - -</option>
			<c:forEach var="map" items="${dataWithCellId}">
				<option value="${map.key}">${map.value}(column-${map.key})</option>
			</c:forEach>
		</select>
	</div>
</div>
</c:forEach> --%>

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
	<label class="col-md-4 text-right mt5">MAC Address</label>
	<div class="col-md-8 dropdown-select">
		<select class="chosen-select form-control" name="macAddress" field-name="MAC Address">
			<option value="">- - - Select - - -</option>
			<c:forEach var="map" items="${dataWithCellId}">
				<option value="${map.key}">${map.value}(column-${map.key})</option>
			</c:forEach>
		</select>
	</div>
</div>
