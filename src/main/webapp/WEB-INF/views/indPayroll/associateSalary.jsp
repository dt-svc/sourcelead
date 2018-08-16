
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


	<div id="tab-5" class="tabsMenu_tabs_inn" style="display: block;">
			<form:form id="associateSalary_form" name="associateSalary_form">
                <div class="clearfix row minHeightcol5">
                 <div class="col-md-4">
                  <label>CTC <small>(Annual)</small><span class="text-danger paddingLeft">*</span></label>
                  <div>
                    <input class="form-control" id="ctc" name="ctc" value="${candidateCTCPerYear}" type="text" field-name="CTC" data-validation="required validate_Space validate_length length1-20 validate_float">
                  </div>
                </div>
               <div class="col-md-4">
                  <label>FBP</label>
                  <div>
                    <input class="form-control" id="fbp" name="fbp" value="${fbp}" type="text" field-name="FBP" data-validation="validate_Space validate_length length1-20 validate_float" data-validation-optional="true">
                  </div>
                </div>
                
                <div class="col-md-4">
                  <label>Variable Pay</label>
                  <div>
                    <input class="form-control" id="variablePay" name="variablePay" value="${variablePay}" type="text" field-name="Variable Pay" data-validation="validate_Space validate_length length1-20 validate_float" data-validation-optional="true">
                  </div>
                </div>
                 <div class="clearfix"></div>
                 </div>
         <div class="clearfix styleTables tableStyles-textLeft">
              <div class="clearfix mb mt">
              </div>
               <table>
               		<thead>
               		<tr>
               			<th style="width:50px;"><input type="checkbox" name="salary" class="selectall"></th>
               			<th>Payhead</th>
               			<th>Paytype</th>
               			<th>Method</th>
               			<th>Formula</th>
               			<th>Monthly</th>
               			<th>Annualy</th>
               		</tr>
               		</thead>
	               		<tbody>
	               			<c:forEach var="payHead" items="${payrollPayheadList}" varStatus="loop">
		               			<c:set var="checked" value="" />
		               			<c:set var="monthlyValue" value="" />
							    <c:set var="yearlyValue" value="" />
							    <c:set var="assoSalPayHeadId" value="" />
		               			<c:forEach var="assoSalPayHead" items="${associateSalaryPayheadIndList}">
			               			<c:if test="${assoSalPayHead.payrollPayhead.id == payHead.id}">
							            <c:set var="checked" value="checked" />
							            <c:set var="monthlyValue" value="${assoSalPayHead.monthly}" />
							            <c:set var="yearlyValue" value="${assoSalPayHead.annualy}" />
							            <c:set var="assoSalPayHeadId" value="${assoSalPayHead.id}" />
							        </c:if>
		               			</c:forEach>
	               				<tr>
									<td><input type="checkbox"  ${checked} id="${payHead.shortCode}" name="associateSalaryPayheadIndList[${loop.index}].checked" onclick="payHeadCalculation(${payHead.id}, '${payHead.shortCode}', '${payHead.method}', '${payHead.formula}')"></td>
									<td>${payHead.name} <input type="text" style="display:none" name="associateSalaryPayheadIndList[${loop.index}].payrollPayheadId" value="${payHead.id}"></td>
									<td>${payHead.type} <input type="text" style="display:none" name="associateSalaryPayheadIndList[${loop.index}].id" value="${assoSalPayHeadId}"></td>
									<td>${payHead.method}</td>
									<td>${payHead.formula}</td>
									<td><input type="text" id="payHeadMonthly[${payHead.shortCode}]" name="associateSalaryPayheadIndList[${loop.index}].monthly" value="${monthlyValue}" onblur="calculateToPerYear('${payHead.shortCode}');" value="" class="form-control input-sm" field-name="${payHead.name}" data-validation="validate_Space validate_length length1-20 validate_float" data-validation-optional="true"></td>
									<td><input type="text" id="payHeadYearly[${payHead.shortCode}]" name="associateSalaryPayheadIndList[${loop.index}].annualy" value="${yearlyValue}" onblur="calculateToPerMonth('${payHead.shortCode}');" value="" class="form-control input-sm" field-name="${payHead.name}" data-validation="validate_Space validate_length length1-20 validate_float" data-validation-optional="true"></td>
								</tr>
							</c:forEach>
	               		</tbody>
               		</table>
                
                <input type="text" style="display:none" name="candidateId" value="${candidate.id}">
                <input type="text" style="display:none" name="uuid" value="${uuid}">
                  
                <div class="clearfix footerstyelsNew">
                 <div class="col-md-6">
			     </div>
                  <div class="col-md-6">
                   <div class="pull-right">
                    <input value="Save &amp; Continue" class="btn btn-success btnSuccess" type="button" onclick="saveAssociateSalaryInd();">
                    <input value="Cancel" class="btn btn-default ml10" type="button">
                    </div>
                  </div>
                </div>
            </div>
         </form:form>
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
function calculateToPerYear(shortCode){
	var monthly = document.getElementById("payHeadMonthly["+shortCode+"]").value;
	document.getElementById("payHeadYearly["+shortCode+"]").value = (monthly*12).toFixed(2);
}
function calculateToPerMonth(shortCode){
	var yearly = document.getElementById("payHeadYearly["+shortCode+"]").value;
	document.getElementById("payHeadMonthly["+shortCode+"]").value = (yearly/12).toFixed(2);
}

function payHeadCalculation(id, shortCode, method, formula){
	
	if(document.getElementById(shortCode).checked){
		try{
			switch(method) {
			    case "% of Payheads":
						var formatedFormula = formula.replace(/%/g, "/100*");
						var matches = [];
						var pattern = /\((.*?)\)/g;
						var match;
						while ((match = pattern.exec(formatedFormula)) != null)
						{
						  //matches.push(match[1]);
						  var yearPayHeadValue = document.getElementById("payHeadYearly["+match[1]+"]").value;
						  formatedFormula = formatedFormula.replace(match[1], yearPayHeadValue)
						}
						 var result = eval(formatedFormula);
						document.getElementById("payHeadMonthly["+shortCode+"]").value = (result/12).toFixed(2);
						document.getElementById("payHeadYearly["+shortCode+"]").value = (result).toFixed(2);
			        break;
			    case "% of CTC":
						var ctc = document.getElementById("ctc").value;
						var result = eval((ctc/100)*formula);
						document.getElementById("payHeadMonthly["+shortCode+"]").value = (result/12).toFixed(2);
						document.getElementById("payHeadYearly["+shortCode+"]").value = (result).toFixed(2);
			        break;
			    case "% of Gross pay":
						
			        break;
			    case "Flat":
				    	var result = eval(formula);
						document.getElementById("payHeadMonthly["+shortCode+"]").value = (result/12).toFixed(2);
						document.getElementById("payHeadYearly["+shortCode+"]").value = (result).toFixed(2);
			    	break;
			    default:
			    		document.getElementById("payHeadMonthly["+shortCode+"]").value = ("");
						document.getElementById("payHeadYearly["+shortCode+"]").value = ("");
			}
		}catch(err){
			document.getElementById("payHeadMonthly["+shortCode+"]").value = (0).toFixed(2);
			document.getElementById("payHeadYearly["+shortCode+"]").value = (0).toFixed(2);
		}
	}else{
		document.getElementById("payHeadMonthly["+shortCode+"]").value = ("");
		document.getElementById("payHeadYearly["+shortCode+"]").value = ("");
	}
}
</script>

<script>
function saveAssociateSalaryInd(){
	if ($('#associateSalary_form').validate(false, validationSettings)) {
		var formData = getFormData1("associateSalary_form");
		$.ajax({
			 type : "POST",
			 data: formData,
		     processData: false,
		     contentType:false, 
			 url : sourceleadUrl+"/saveAssociateSalaryInd?"+tokenParamName+"="+tokenValue,
			 success : function(result) {
			  		
			  		
			 }
		});
	}
}
$('body').on('blur', '#associateSalary_form', function() {
	$("#associateSalary_form").showHelpOnFocus();
	$("#associateSalary_form").validateOnBlur(false, validationSettings);
});



$('.selectall').click(function() {
    $(this.form.elements).filter(':checkbox').prop('checked', this.checked).trigger("onclick");
});

</script>
