<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" href="/resources/css/chosen.min.css">

<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" onclick="bulkAdjustmentClose();">×</button>
				 <h4 class="modal-title">Bulk Pay Adjustments</h4>
			 </div>
			 
			<form:form id="bulkPayheadAdjust_form" name="bulkPayheadAdjust_form">
			<div class="modal-body" style="min-height: 150px;">
					<div class="row mb10 clearfix">
						<div class="col-md-6">
							<div class="clearfix">
								<div class="checkbox-new pull-left ml">
										<input id="sae" class="checkbox-custom" name="allEmployees" type="checkbox">
										<label for="sae" class="checkbox-custom-label"></label><span class="ml10">Select All Employees</span>
								</div>
							</div>
						</div>
						<div class="col-md-6">
							<div class="clearfix">
							<select class="chosen-select" multiple data-placeholder="Select Employees" field-name="Select Employee" id="selectEmployees" name="indEmpPayrollUUIDList" data-validation="required">
								<c:forEach var="payFrequencyEmployee" items="${payFrequencyEmployeeList}">
									<option value="${payFrequencyEmployee.indEmpPayrollUUID}">${payFrequencyEmployee.empName}</option>
								</c:forEach>
							</select>
							</div>
						</div>
					</div>
					<input type="text" hidden id="paySheetFrequencyUUID" name="paySheetFrequencyUUID" value="${indPaysheetFrequency.paySheetFrequencyUUID}">
					<div class="clearfix"></div>
					<hr>
					<div class="clearfix mt25 minDivHeight">
						<c:forEach var="variablePayhead" items="${variablePayheadsList}" varStatus="loop">
							<div class="mb clearfix">
								<label class="col-md-3 textBold text-right">${variablePayhead.name}</label>
								<div class="col-md-6">
									<input type="text" hidden name="associateSalaryPayheadIndEarningList[${loop.index}].payrollPayheadName" value="${variablePayhead.name}">
									<input type="text" hidden name="associateSalaryPayheadIndEarningList[${loop.index}].payrollPayheadShortCode" value="${variablePayhead.shortCode}">
									<input type="text" hidden name="associateSalaryPayheadIndEarningList[${loop.index}].uuid" value="${variablePayhead.uuid}">
									<input type="text" hidden name="associateSalaryPayheadIndEarningList[${loop.index}].payrollPayhead" value="${variablePayhead.id}">
									<input type="text" hidden name="associateSalaryPayheadIndEarningList[${loop.index}].payrollPayheadType" value="${variablePayhead.type}">
									<input type="text" name="associateSalaryPayheadIndEarningList[${loop.index}].monthly" data-validation="validate_Space validate_float validate_length length1-8" data-validation-optional="true" value="" class="form-control input-sm">
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
			</form:form>
		 <div class="modal-footer clearfix">
			<button type="button" class="btn btn-primary" id="bulkAdjustmentBtn" onclick="bulkAdjustmentSaving();">Save</button>
	        <button type="button" class="btn btn-default" onclick="bulkAdjustmentClose();">Cancel</button>
	     </div>
     </div>
     </div>
</div>
 
<script src="/resources/js/chosen.jquery.min.js"></script>
<script>
$('#sae').change(function () {
    if (this.checked) {
    	$('#sae').val('Y');
    	$('#selectEmployees').removeAttr("data-validation");
        $('#selectEmployees').prop('disabled', true).trigger("chosen:updated");
    }else{
    	$('#sae').val('N');
    	$('#selectEmployees').attr("data-validation","required");
    	$('#selectEmployees').prop('disabled', false).trigger("chosen:updated");
    }
});

function bulkAdjustmentSaving(){
	if ($('#bulkPayheadAdjust_form').validate(false, validationSettings)) {
		$('#bulkAdjustmentBtn').hide();
		var psfUUID = $('#paySheetFrequencyUUID').val();
		var formData = getFormData1("bulkPayheadAdjust_form");
		$.ajax({
			 type : "POST",
			 data: formData,
		     processData: false,
		     contentType:false, 
			 url : sourceleadUrl+"/saveBulkPayheadAdjustInd?"+tokenParamName+"="+tokenValue,
			 success : function(result) {
				 bulkAdjustmentClose();
				 $(location).attr('href', 'employeePayrollList?paySheetFrequencyUUID='+psfUUID);
			 }
		});
	}
}
</script>

<script type="text/javascript">
    var staticTime='${staticTime}';
    var config = {
			  '.chosen-select'           : {},
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			}
    $("#selectEmployees").chosen();
 </script>
 