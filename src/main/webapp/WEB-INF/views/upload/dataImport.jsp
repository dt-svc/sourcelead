
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!-- Content Area -->
<!-- <section class="content-header clearfix">
  <div class="pull-left">
    <h2>Jobs Import</h2>
  </div>
  <div class="pull-right brud-crum">Home &raquo; Jobs &raquo; Jobs Import</div>
</section> -->
<div class="content-area content-new clearfix" id="importFieldsDiv">

	<div class="col-xs-12 col-md-12 col-lg-12 mt">
		<div class="well">
			<p>
				<strong>Please Note</strong>
			</p>
			<ul class="liststyels-custom">
				<li>Please map the fields appropriately to import the date.</li>
				<li>Choose the excel columns from the dropdown to map with the
					TalentHire columns.</li>
				<li>Click on Import to start the importing.</li>
			</ul>
			
			<c:if test="${!empty importStatus}"> 
				<span class="colorRed">Some data is failed to import <a download class="colorRed glyphicon glyphicon-download-alt" target="_blank" href="${importStatus}" title="Download error file"></a></span>
			</c:if>
			
			
		</div>
	</div>
	<div class="clearfix"></div>
	<form:form id="dataMigration_form" name="dataMigration_form">
	<div class="jobsimort-header clearfix">
		<h4 class="col-md-10 mt5">Fields</h4>
		<div class="col-md-2">
			<div class="pull-right">
				<button type="button" class="btn btn-sm btn-primary mr"
					onclick="saveDataFields();">Import</button>
				<button type="button" class="btn btn-sm btn-primary"
					onclick="cancelFileImport();">Cancel</button>
			</div>
		</div>
	</div>
	<div class="form-horizontal myForm row">
		<div class="clearfix">
			<!-- Select Dropdowns -->
			<div class="col-xs-12 col-sm-6 col-md-12 col-lg-12">
				<c:forEach var="field" items="${fields}">
					<div class="form-group">
						<c:if test="${empty field.slDependantField}">
							<label class="col-md-4 text-right mt5">${field.labelName}<span class="text-danger paddingLeft">${field.isFieldRequired == "1" ? "*" : ""}</span></label>
						</c:if>
						<c:if test="${!empty field.slDependantField}">
							<label class="col-md-4 text-right mt5">${field.labelName}</label>
						</c:if>
						<div class="col-md-8 dropdown-select">
						<c:if test="${empty field.slDependantField}">
							<select class="form-control" name="${field.fieldName}" field-name="${field.labelName}" ${field.isFieldRequired == "1" ? "data-validation='required'" : ""}>
						</c:if>
						<c:if test="${!empty field.slDependantField}">
							<select class="form-control" name="${field.fieldName}" field-name="${field.labelName}">
						</c:if>
								<option value="">- - - Select - - -</option>
								<c:forEach var="map" items="${dataWithCellId}">
									<option value="${map.key}">${map.value} (column-${map.key})</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</c:forEach>
				<input type="hidden"  readonly id="exportFilePath" name="exportFilePath" value="${exportFilePath}">
				<input type="hidden"  readonly id="uploadType" name="uploadType" value="${uploadType}">
			</div>
			<!-- Select Dropdowns -->
		</div>
	</div>
	</form:form>
</div>

<script type="text/javascript">
	function saveDataFields() {
		if ($('#dataMigration_form').validate(false, validationSettings)) {
			var formData = getFormData1("dataMigration_form");
			$.ajax({
				type : "POST",
				data : formData,
				processData : false,
				contentType : false,
				url : sourceleadUrl + "/getDataByMatchedFields?"
						+ tokenParamName + "=" + tokenValue,
				success : function(data) {
					$("#jobFileImportDiv").attr("style","display:none");
			    	$('#jobFieldsDiv').removeAttr("style");
			    	$('#jobFieldsDiv').empty();
			    	$('#jobFieldsDiv').append(data);
				}
			});
		}
	}
	$('body').on('blur', '#dataMigration_form', function() {
		$("#dataMigration_form").showHelpOnFocus();
		$("#dataMigration_form").validateOnBlur(false, validationSettings);
	});
</script>
<script type="text/javascript">
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };
</script>
