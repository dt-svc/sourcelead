
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
						<input type="hidden" name="id"  value="${payrollPayhead.id}">
						<input type="text" class="form-control" readonly placeholder="Enter" value="${payrollPayhead.name} (${payrollPayhead.shortCode})">
					</div>
				</div>
				<div class="form-group">
					<label class="clearfix">Method</label>
					<div class="clearfix">
						<select class="sel" id="method" name="method">
							<!-- <option value="">--Select method--</option> -->
							<option ${payrollPayhead.method == 'Flat' ? 'selected' : ''}>Flat</option>
							<option ${payrollPayhead.method == '% of CTC' ? 'selected' : ''}>% of CTC</option>
							<%-- <option ${payrollPayhead.method == '% of Gross pay' ? 'selected' : ''}>% of Gross pay</option> --%>
							<option ${payrollPayhead.method == '% of Payheads' ? 'selected' : ''}>% of Payheads</option>
							<%-- <option ${payrollPayhead.method == 'Formula' ? 'selected' : ''}>Formula</option> --%>
						</select>
					</div>
				</div>
				
				<div class="form-group" id="formulaInputDiv" ${empty payrollPayhead || empty payrollPayhead.method || payrollPayhead.method == '% of CTC' || payrollPayhead.method == '% of Gross pay' || payrollPayhead.method == 'Flat' ? '' : 'hidden'}>
					<label class="clearfix" id="formulaLabel">${payrollPayhead.method == '% of Payheads' || payrollPayhead.method == 'Formula' ? 'Formula' : payrollPayhead.method == '% of CTC' || payrollPayhead.method == '% of Gross pay' ? 'Percentage' :'Amount'}</label>
					<div class="clearfix">
						<input type="text" class="form-control" id="formula" ${payrollPayhead.method == '% of CTC' || payrollPayhead.method == '% of Gross pay' || payrollPayhead.method == 'Flat' || empty payrollPayhead.method ? 'name="formula"' : ''} placeholder="Enter amount" data-validation-optional="true" value="${payrollPayhead.formula}">
					</div>
				</div>
				
				
				<div class="row" id="payheadsFormulaGroup" ${payrollPayhead.method == '% of Payheads' || payrollPayhead.method == 'Formula' ? '' : 'hidden'}>
					<div id="payheadsFormulaGroupOpt" ${payrollPayhead.method == 'Formula' ? 'hidden' : ''}>
					<div class="col-md-3">
		              <label>Percentage (%)</label>
		              <div class="clearfix">
							<select class="form-control chosen-select" id="formulaPercentage" name="formulaPercentage">
								<option value="">Select</option>
		                			<c:forEach begin="1" end="100" varStatus="percent">
											<option >${percent.index}%</option>
									</c:forEach>
							</select>
		              </div>
		            </div>
		            <div class="col-md-5">
		              <label>Payhead</label>
		              <div class="clearfix">
							<select class="form-control chosen-select" id="formulaPayhead" name="formulaPayhead">
								<option value="">Select</option>
									<%-- <option value="(CTC)">CTC (CTC)</option>
									<option value="(GROSS)">Gross (GROSS)</option> --%>
		                			<c:forEach var="list" items="${payheadList}">
			                			<c:if test="${payrollPayhead.id != list.id}">
			                				<option value="(${list.shortCode})">${list.name} (${list.shortCode})</option>
			                			</c:if>
									</c:forEach>
							</select>
		              </div>
		            </div>
		            <div class="col-md-2">
		              <label>Operator</label>
		              <div class="clearfix">
		                <select class="form-control chosen-select" id="formulaOperator" name="formulaOperator">
		                  <option value="">Select</option>
		                  <option>&#43;</option>
		                  <option>&#42;</option>
		                  <option>&#47;</option>
		                  <option>&#45;</option>
		                  <option>&#61;</option>
		                  <option>&#37;</option>
		                  <%--<option>&#40;</option>
		                  <option>&#41;</option> --%>
		                  <option>&#91;</option>
		                  <option>&#93;</option>
		                  <option>&#123;</option>
		                  <option>&#125;</option>
		                </select>
		              </div>
		            </div>
		            <div class="col-md-2">
		              <label>Action</label>
		              <button type="button" class="btn btn-success btnSuccess btn-block" onclick="insertAtCursor('formulaTextareaId');return false;">Add</button>
		            </div>
	            </div>
		            <div class="clearfix"></div>
		            <div class="clearfix mt  col-md-12" id="formulaTextareaDiv">
		              <textarea id="formulaTextareaId" ${payrollPayhead.method == '% of Payheads' || payrollPayhead.method == 'Formula' ? 'name="formula"' : ''}  class="form-control" rows="2">${payrollPayhead.formula}</textarea>
		              <div class="pull-right mt5" id="payheadsFormulaValidateBtn" ${payrollPayhead.method == 'Formula' ? 'hidden' : ''}>
		                <button type="button" class="btn btn-success btnSuccess" onClick="validateFormula();">Validate</button>
		              </div>
		              <!-- <p class="text-success mt5">Valid expression</p>
		              <p class="text-danger mt5">Invalid expression</p> -->
		            </div>
	          </div>
			</div>
			
			<div class="modal-footer">
				<div class="pull-right">
					<input type="button" id="saveSalarySetupBtn" class="btn btn-success btnSuccess" value="Save" title="Save">
					<input type="button" class="btn btn-default" value="Cancel" title="Cancel" onClick="closesalarySetup();">
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
</script>

<script>
    $(".chosen-select").chosen();
</script>
<script>
$('#method').on('change', function() {
	  $("#formulaTextareaId").val("");
	  $("#formula").val("");
	  $('#formulaPercentage').trigger("chosen:updated");
	  $('#formulaPayhead').trigger("chosen:updated");
	  $('#formulaOperator').trigger("chosen:updated");
	  $("#formulaInputDiv").show();
	  $("#payheadsFormulaValidateBtn").hide();
      $('#formula').removeAttr("data-validation");
      $('#formulaTextareaId').removeAttr("data-validation");
      
	  if($('#method :selected').text() == 'Flat'){
		  $("#formula").attr("placeholder", "Enter amount");
		  $("#formulaLabel").text("Amount");
		  $("#formula").attr("name", "formula");
		  $("#payheadsFormulaGroup").hide();
		  $("#formulaTextareaId").removeAttr("name");
		  $('#formula').attr("data-validation", "validate_Space validate_length length1-10 validate_int");
		  
	  }else if($('#method :selected').text() == '% of CTC' || $('#method :selected').text() == '% of Gross pay'){
		  $("#formula").attr("placeholder", "Enter percentage");
		  $("#formulaLabel").text("Percentage");
		  $("#formula").attr("name", "formula");
		  $("#payheadsFormulaGroup").hide();
		  $("#formulaTextareaId").removeAttr("name");
		  $('#formula').attr("data-validation", "validate_Space validate_length length1-5 validate_float");
		  
	  }else if($('#method :selected').text() == '% of Payheads'){
		  $("#formula").removeAttr("name");
		  $("#formulaInputDiv").hide();
		  $("#payheadsFormulaGroup").show();
		  $("#formulaTextareaId").attr("name", "formula");
		  $("#payheadsFormulaGroupOpt").show();
		  $("#payheadsFormulaValidateBtn").show();
	      $('#formulaTextareaId').attr("data-validation", "validate_Space validate_length length1-200");
		  
	  }else if($('#method :selected').text() == 'Formula'){
		  $("#formula").removeAttr("name");
		  $("#formulaInputDiv").hide();
		  $("#payheadsFormulaGroup").show();
		  $("#formulaInputDiv").hide();
		  $("#formulaTextareaId").attr("name", "formula");
		  $("#payheadsFormulaGroupOpt").hide();
	      $('#formulaTextareaId').attr("data-validation", "validate_Space validate_length length1-200");
		  
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
		    	var pmsUrl ='${SourceLeadWeb_pmsUrl}';
      			var formData = getFormData("salarySetup_form");
      			var uuid = '${uuid}';
				$.ajax({
					 type : "POST",
					 data: formData,
					  url : pmsUrl+"/storePayheadWithSalarySetup?uuid="+uuid,
					  	success : function(result) {
					  		if (result && result.response === "INVALID_DATA") {
			                    showError("Invalid data");
			                } else if (result.response === "AWKWARD") {
			                    location.href = "/errorPage";
			                } else if (result.response === "SUCCESS") {
			                	if($("#formula").val() != null && $("#formula").val() != ''){
			                		$("#formulaSpan"+uuid).text($.trim($("#formula").val()));
			                	}else{
			                		$("#formulaSpan"+uuid).text($.trim($("textarea#formula").val()));
			                	}
			                	$("#methodSpan"+uuid).text($("#method").val());
			                	
			                	closesalarySetup();
			                    
			                } else if (result.indexOf('Sign In') !== -1) {
								location.replace(pmsUrl);
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
	
	
	function insertAtCursor(areaId) {
		var percentage = $("#formulaPercentage").val();
		var payhead = $("#formulaPayhead").val();
		var operator = $("#formulaOperator").val();
		
		var text = percentage+payhead+operator;
		  var txtarea = document.getElementById(areaId);
		  if (!txtarea) {
		    return;
		  }

		  var scrollPos = txtarea.scrollTop;
		  var strPos = 0;
		  var br = ((txtarea.selectionStart || txtarea.selectionStart == '0') ?
		    "ff" : (document.selection ? "ie" : false));
		  if (br == "ie") {
		    txtarea.focus();
		    var range = document.selection.createRange();
		    range.moveStart('character', -txtarea.value.length);
		    strPos = range.text.length;
		  } else if (br == "ff") {
		    strPos = txtarea.selectionStart;
		  }

		  var front = (txtarea.value).substring(0, strPos);
		  var back = (txtarea.value).substring(strPos, txtarea.value.length);
		  txtarea.value = front + text + back;
		  strPos = strPos + text.length;
		  if (br == "ie") {
		    txtarea.focus();
		    var ieRange = document.selection.createRange();
		    ieRange.moveStart('character', -txtarea.value.length);
		    ieRange.moveStart('character', strPos);
		    ieRange.moveEnd('character', 0);
		    ieRange.select();
		  } else if (br == "ff") {
		    txtarea.selectionStart = strPos;
		    txtarea.selectionEnd = strPos;
		    txtarea.focus();
		  }

		  txtarea.scrollTop = scrollPos;
		  
		  $('#formulaPercentage').val('').trigger("chosen:updated");
		  $('#formulaPayhead').val('').trigger("chosen:updated");
		  $('#formulaOperator').val('').trigger("chosen:updated");
		}
	
	function validateFormula1(){
		var formula = $("#formulaTextareaId").val();
		
		for (var i = 0; i < formula.length; i++) {
			  
			alert(formula.charAt(i));
			  
			  if(/^[a-zA-Z]$/.test(formula.charAt(i))){
				  
					
					alert(i);
					
					
				}
			  
			}
		
		
	}
	
</script>
