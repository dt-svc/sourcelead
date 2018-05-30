
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!-- Content Area -->

<div class="modal-dialog">
	<div class="modal-content">
		<form:form id="salarySetup_form" name="salarySetup_form" method="post">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" onClick="closesalarySetup();">&times;</button>
				<h4 class="modal-title">Salary Setup</h4>
			</div>
			<div class="modal-body">
			   
			    <div class="form-group">
					<label class="clearfix">Payhead Title</label>
					<div class="clearfix">
						<%-- <input type="hidden" name="id"  value="${payrollPayhead.id}"> --%>
						<input type="text" class="form-control"  readonly placeholder="Enter" value="${payrollPayhead.name } (${payrollPayhead.shortCode})">
					</div>
				</div>
				<div class="form-group">
					<label class="clearfix">Method</label>
					<div class="clearfix">
						<select class="sel" id="method" name="method">
							<!-- <option value="">--Select method--</option> -->
							<option ${payrollPayhead.method == 'Flat' ? 'selected' : ''}>Flat</option>
							<option ${payrollPayhead.method == '% of CTC' ? 'selected' : ''}>% of CTC</option>
							<option ${payrollPayhead.method == '% of Gross' ? 'selected' : ''}>% of Gross</option>
							<option ${payrollPayhead.method == '% of Payheads' ? 'selected' : ''}>% of Payheads</option>
							<option ${payrollPayhead.method == 'Formula' ? 'selected' : ''}>Formula</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label class="clearfix" id="formulaLabel">${payrollPayhead.method == '% of Payheads' || payrollPayhead.method == 'Formula' ? 'Formula' : payrollPayhead.method == '% of CTC' || payrollPayhead.method == '% of Gross' ? 'Percentage' :'Amount'}</label>
					<div class="clearfix">
						<input type="text" class="form-control" id="formula" name="formula" placeholder="Enter amount" data-validation="validate_Space validate_length length1-15 validate_int" value="${payrollPayhead.formula}">
					</div>
				</div>
				<div class="form-group" id="payheadsFormulaGroup" ${payrollPayhead.method == '% of Payheads' ? '' : 'hidden'}>
					<c:set var="rowIndex" value="0" scope="page" />
					<label class="clearfix">Payheads</label>
					<div class="row">
						<c:if test="${empty salarySetupList}">
							<div class="duplicateDiv form-group clearfix">
								<div class="col-md-5">
									<input type="text" class="form-control" id="formulaPercentage0" name="salarySetup[0].formulaPercentage" placeholder="Enter percentage" 
										data-validation-optional="true" data-validation="validate_Space validate_length length1-2">
								</div>
								<div class="col-md-6">
									<select class="sel" id="formulaPayhead0" name="salarySetup[0].formulaPayhead">
										<option value="">--Select payhead--</option>
				                			<c:forEach var="list" items="${payheadList}">
					                			<c:if test="${payrollPayhead.id != list.id}">
					                				<option value="${list.id}">${list.name} (${list.shortCode})</option>
					                			</c:if>
											</c:forEach>
									</select>
								</div>
								<div class="col-md-1">
									<a href="javascript:void(0)" class="cloneDiv" ><i class="fa fa-plus mt10"></i></a> 			
								</div>
							</div>
						</c:if>
						<c:if test="${!empty salarySetupList}">
							<c:forEach var="ppssList" items="${salarySetupList}" varStatus="loop">
							<c:set var="rowIndex" value="${loop.index}" scope="page" />
								<div class="duplicateDiv form-group clearfix">
									<div class="col-md-5">
										<input type="text" class="form-control" id="formulaPercentage${loop.index}" name="salarySetup[${loop.index}].formulaPercentage" placeholder="Enter percentage" 
											data-validation-optional="true" data-validation="validate_Space validate_length length1-2 validate_int" value="${ppssList.formulaPercentage}">
									</div>
									<input type="hidden" id="uuid${loop.index}" name="salarySetup[${loop.index}].uuid" value="${ppssList.uuid}">
									<div class="col-md-6">
										<select class="sel" id="formulaPayhead${loop.index}" name="salarySetup[${loop.index}].formulaPayhead">
											<option value="">--Select payhead--</option>
					                			<c:forEach var="list" items="${payheadList}">
						                			<c:if test="${payrollPayhead.id != list.id}">
						                				<option value="${list.id}" ${ppssList.formulaPayhead == list.id ? 'selected' : ''}>${list.name} (${list.shortCode})</option>
						                			</c:if>
												</c:forEach>
										</select>
									</div>
									<div class="col-md-1">
										<c:if test="${loop.index == 0}">
											<a href="javascript:void(0)" class="cloneDiv" ><i class="fa fa-plus mt10"></i></a>
										</c:if>
										<c:if test="${loop.index > 0}">
											<a href="javascript:void(0)" class="cloneDiv" ><i class="fa fa-trash mt10" onclick="deleteRowCurrent(this)"></i></a>
										</c:if>
									</div>
								</div>
							</c:forEach>
						</c:if>
						<div id="duplicateDiv"></div>
					</div>
				</div>
			</div>
			
			<div class="modal-footer">
				<div class="pull-right">
					<input type="button" id="saveSalarySetupBtn" class="btn btn-success btnSuccess" value="Save" title="Save">
					<input type="button" class="btn btn-default mr" value="Cancel" title="Cancel" onClick="closesalarySetup();">
				</div>
			</div>
		</form:form>
	</div>
</div>

<script type="text/javascript">
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };
        
     // Clone Div
     /* var rowIndex = 0; */
     	var rowIndex = '${rowIndex}';
    	$(".cloneDiv").click(function(){
    		rowIndex++;
    		$(this).parents(".duplicateDiv").clone().appendTo("#duplicateDiv").find("#formulaPercentage0").attr('name', 'salarySetup['+rowIndex+'].formulaPercentage').attr('id', 'formulaPercentage'+rowIndex).parents(".duplicateDiv").find("#formulaPayhead0").attr('name', 'salarySetup['+rowIndex+'].formulaPayhead').attr('id', 'formulaPayhead'+rowIndex).parents(".duplicateDiv").find(".fa-plus").removeClass("fa-plus").addClass("fa-trash").attr("onclick","deleteRowCurrent(this)");
    		$("#formulaPercentage"+rowIndex).val("");
    		$("#formulaPayhead"+rowIndex).val("");
    		/* $('#formulaPayhead'+rowIndex).chosen();
    		$('#formulaPayhead'+rowIndex).trigger("chosen:updated"); */
    	});
     
    	function deleteRowCurrent(event){
    		$(event).parent().parent().parent().remove();
    	}
</script>

<script>
    $(".chosen-select").chosen();
</script>
<script>
$('#method').on('change', function() {
	  if($('#method :selected').text() == 'Flat'){
		  $("#formula").attr("placeholder", "Enter amount");
		  $("#formulaLabel").text("Amount");
		  $("#payheadsFormulaGroup").hide();
	  }else if($('#method :selected').text() == '% of CTC' || $('#method :selected').text() == '% of Gross'){
		  $("#formula").attr("placeholder", "Enter percentage");
		  $("#formulaLabel").text("Percentage");
		  $("#payheadsFormulaGroup").hide();
	  }else if($('#method :selected').text() == '% of Payheads'){
		  $("#formula").attr("placeholder", "Enter formula");
		  $("#formulaLabel").text("Formula");
		  /* $('#formulaPayhead1').chosen();
  		  $('#formulaPayhead1').trigger("chosen:updated"); */
		  $("#payheadsFormulaGroup").show();
	  }else if($('#method :selected').text() == 'Formula'){
		  $("#formula").attr("placeholder", "Enter formula");
		  $("#formulaLabel").text("Formula");
		  $("#payheadsFormulaGroup").hide();
	  }
})
</script>



<script type="text/javascript">
	$("#saveSalarySetupBtn").click(function(){
		var validationSettings = {
		        errorMessagePosition : 'element',
		       borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd' 
	         };
		     if ($('#salarySetup_form').validate(false, validationSettings)){
      			var formData = getFormData("salarySetup_form");
      			var uuid = '${uuid}';
				$.ajax({
					 type : "POST",
					 data: formData,
					  url : sourceleadUrl+"/storePayheadWithSalarySetup?uuid="+uuid,
					  	success : function(result) {
					  		if (result && result.response === "INVALID_DATA") {
			                    showError("Invalid data");
			                } else if (result.response === "AWKWARD") {
			                    location.href = "/errorPage";
			                } else if (result.response === "SUCCESS") {
			                	
			                	
			                	closesalarySetup();
			                    
			                } else if (result.indexOf('Sign In') !== -1) {
								location.replace(sourceleadUrl);
							} else {
			                    showError("Operation failed, please try again");
			                }
					  	},
				});
			
				return true;
		   	}else{ 
				return false;
		 }   
	});
	
	$('body').on('blur', '#salarySetup_form', function() {
		$("#salarySetup_form").showHelpOnFocus();
		$("#salarySetup_form").validateOnBlur(false, validationSettings);
	});
	
</script>
