<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="success_pop" id="selectoruser" style="display: none;">
	<i class="fa fa-check-circle"></i><span id="userMsgDiv"></span>
</div>
			<c:if test="${!empty associateSalaryInd}">
				<div class="clearfix" id="stopSalaryDiv" style="position: absolute; right: 100px; z-index: 1px">
					<div class="pull-right mr mt">
						<label class="textBold">Stop Salary</label>
						<span class="toggle-button pull-right ml">
						  <label class="switch switchSalary">
						  	<input type="checkbox"  value="${associateSalaryInd.stopSalary == 'YES' ? 'YES' : 'NO'}" id="stopsalary" ${associateSalaryInd.stopSalary == 'YES' ? 'checked': ''}>
						  	<div class="slider round"></div>
						  </label>
						</span>
					</div>
				</div>
			</c:if>
			<form:form id="associateSalary_form" name="associateSalary_form">
                <div class="clearfix minHeightcol5 mt">
                <div class="clearfix mb">
                <ul class="list-unstyled list-inline pull-right">
                	<li><a class="pull-right mr anchorBlack" id="editAssociateBtn" style="display:none" onclick=""><i class="glyphicon glyphicon-pencil paddingRight"></i>Edit</a></li>
                </ul>
                </div>
                
                <div class="col-md-5">
                    <label>Pay Type<span class="text-danger paddingLeft">*</span></label>
                    <div>
                      <select class="sel" id="payType" name="payType" id="payType" data-validation="required" field-name="Pay Type">
                        <option value="">--Select--</option>
                        <%-- <option ${associateSalaryInd.payType == "HourlyRate" ? "selected" : ""} value="HourlyRate">Hourly Rate</option> --%>
                        <option ${associateSalaryInd.payType == "Salary" ? "selected" : ""} value = "Salary">Salary</option>
                        <%-- <option ${associateSalaryInd.payType == "CommissionOnly" ? "selected" : ""} value= "CommissionOnly">Commission Only</option> --%>
                      </select>
                    </div>
                  </div>
                   <div class="col-md-5">
                    <label>Pay Frequency<span class="text-danger paddingLeft">*</span></label>
                    <div>
                      <select class="sel" id="payFrequency" name="payFrequency" onchange="getPayGroups();" data-validation="required" field-name="Pay Frequency">
                        <option value="">--Select--</option>
<%--                         <option ${associateSalaryInd.payFrequency == "Annual" ? "selected" : ""} value="Annual">Annually</option> --%>
                        <option ${associateSalaryInd.payFrequency == "Monthly" ? "selected" : ""} value="Monthly">Monthly</option>
<%--                         <option ${associateSalaryInd.payFrequency == "Bi-Weekly" ? "selected" : ""} value="Bi-Weekly">Bi-Weekly</option> --%>
<%--                         <option ${associateSalaryInd.payFrequency == "Weekly" ? "selected" : ""} value="Weekly">Weekly</option> --%>
                      </select>
                    </div>
                  </div>
                   <div class="col-md-5">
                    <label>Pay Group<span class="text-danger paddingLeft">*</span></label>
                    <div>
                      <select class="sel" id="payGroupId" name="payGroup" data-validation="required" field-name="Pay Group">
	                      <option value="">--Select--</option>
	                      <c:forEach var="userGroup" items="${userGroupList}">
				               	<option ${userGroup.id == associateSalaryInd.groupId.id ? "selected" : ""} value='${userGroup.groupUUID}'>${userGroup.groupName}</option>
			              </c:forEach>
                      </select>
                    </div>
                  </div>
                  <div class="col-md-5">
                    <label>Standard Hours<span class="text-danger paddingLeft">*</span></label>
                    <div>
                      <input type="text" class="form-control"  id="standardHours" name="standardHours" value="${associateSalaryInd.standardHours}" data-validation="required validate_float" field-name="Standard Hours" onblur="calculateSal();">
                    </div>
                  </div>
                   <div class="col-md-5">
                    <label>Annual Salary<span class="text-danger paddingLeft">*</span></label>
                    <div>
                      <input type="text" class="form-control allownumericwithdecimal" id="annualSal" name="annualSal" value="${associateSalaryInd.annualSalary != '' ? associateSalaryInd.annualSalary : candidateCTCPerYear }" data-validation="required validate_float" field-name="Annual Salary" onblur="calculateSal();">
                    </div>
                  </div>
                   <div class="col-md-5">
                    <label>Pay Per Salary<span class="text-danger paddingLeft">*</span></label>
                    <div>
                      <input type="text" class="form-control allownumericwithdecimal" id="perPaySal" name="perPaySal" value="${associateSalaryInd.grossSalary}" data-validation="required validate_float" field-name="Pay Per Salary" onblur = "calculatePayPerSal();">
                    </div>
                  </div>  
                   <div class="col-md-5">
                    <label>Hourly Rate<span class="text-danger paddingLeft">*</span></label>
                    <div>
                      <input type="text" class="form-control allownumericwithdecimal" id="hourlyRate" name="hourlyRate" value="${associateSalaryInd.hourlyRate}" data-validation="required validate_float" field-name="Hourly Rate" onblur = "calculateHourlyRateSal();">
                    </div>
                  </div>
                  
                  <div id="editSectionDiv" style="display:none">
	                  <div class="col-md-5">
		                <label>Effective Date</label>
		                <div class="input-group">
		                  <input type="text" class="form-control calendar" id="effectiveDate" name="effectiveDate" value="${associateSalaryInd.effectiveDateStr}" field-name="Effective Date">
						  <span class="input-group-addon" onClick="clearEffectiveDate();"><i class="colorRed fa fa-times cursorPointer"></i></span>
		                </div>
		              </div>
		              <div class="col-md-5">
		                <label>% Increase</label>
		                <div>
		                  <input class="form-control allownumericwithdecimal" name="increasePer" id="increasePer" type="text" value="${associateSalaryInd.incrementPercentage}" data-validation="validate_float" data-validation-optional="true" onblur = "calculatePercentSal();">
		                </div>
		              </div>
		              <div class="col-md-5">
		                <label>Change Reason</label>
		                <div>
		                  <textarea class="form-control" rows="2" placeholder="Enter reason" name="changeReason" id="changeReason">${associateSalaryInd.changeReason}</textarea>
		              	</div>
		              </div>
                </div>
                
                 <div class="clearfix"></div>
                 </div>
         <div class="clearfix clients_wrap_table">
         <div class="clearfix mb5">
         	<a href="${SourceLeadWeb_pmsUrl}/payrollPayhead"class="btn btn-primary  pull-right mr"><i class="fa fa-plus paddingRight"></i>Add Payhead</a>
         </div>
               <table>
               		<thead>
               		<tr>
<!--                		Don't delete the code because select all functionality is not working properly if any contact Rana -->
<!--                			<th style="width:50px;"><input type="checkbox" name="salary" class="selectall"></th> -->
						<th></th>
               			<th>Payhead</th>
               			<th>Paytype</th>
               			<th>Method</th>
               			<th>Formula</th>
               			<th>Monthly</th>
               			<th>Annualy</th>
               		</tr>
               		</thead>
	               		<tbody id="phTblBody">
	               			<c:forEach var="payHead" items="${payrollPayheadList}" varStatus="loop">
	               			<c:if test="${payHead.active eq 'YES'}">
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
									<td title="${payHead.name} (${payHead.shortCode})">${payHead.name} (${payHead.shortCode})<input type="text" style="display:none" name="associateSalaryPayheadIndList[${loop.index}].payrollPayheadId" value="${payHead.id}"></td>
									<td>${payHead.type} <input type="text" style="display:none" id="payheadSC[${payHead.shortCode}]" value="${payHead.type}"> <input type="text" style="display:none" name="associateSalaryPayheadIndList[${loop.index}].id" value="${assoSalPayHeadId}"></td>
									<td>${payHead.method} <input type="text" style="display:none" name="associateSalaryPayheadIndList[${loop.index}].payrollPayheadShortCode" value="${payHead.shortCode}"></td>
									<td>${payHead.formula}</td>
									<td><input type="text" id="payHeadMonthly[${payHead.shortCode}]" name="associateSalaryPayheadIndList[${loop.index}].monthly" value="${monthlyValue}" onblur="calculateToPerYear('${payHead.shortCode}');" value="" class="form-control input-sm" field-name="${payHead.name}" data-validation="validate_Space validate_length length1-20 validate_number" data-validation-optional="true"></td>
									<td><input type="text" id="payHeadYearly[${payHead.shortCode}]" name="associateSalaryPayheadIndList[${loop.index}].annualy" value="${yearlyValue}" onblur="calculateToPerMonth('${payHead.shortCode}');" value="" class="form-control input-sm" field-name="${payHead.name}" data-validation="validate_Space validate_length length1-20 validate_number" data-validation-optional="true"></td>
								</tr>
							</c:if>
							</c:forEach>
	               		</tbody>
               		</table>
                
                <input type="text" style="display:none" name="candidateId" id="assocandidateId" value="${candidate.id}">
                <input type="text" style="display:none" name="uuid" value="${uuid}">
                <input type="text" style="display:none" id="status" value="${status}">
                <input type="text" style="display:none" id="userId" name="userId" value="${candidate.consultantUser.id}">
                  	             <div class="clearfix"></div>
                <div class="clearfix footerstyelsNew">
                 <div class="col-md-6">
                 <c:if test="${status == 'NEW'}">
                 <input type="button" value="Previous" onclick="shiftToLeaveTypeDetails();" class="btn btn-default">
			     </c:if>
			     </div>
                  <div class="col-md-6">
                   <div class="pull-right" id="saveAssociateBtnDiv">
                    <input value="Save & Finish" class="btn btn-success btnSuccess" type="button" onclick="saveAssociateSalaryInd();">
                    <c:choose>
                    <c:when test="${userCode == null || userCode == ''}">
                    <input value="Cancel" class="btn btn-default ml10" 
                    onclick="javascript:window.location.href = '${SourceLeadWeb_atsUrl}/usersList'"
                    type="button">
                    </c:when>
                    <c:otherwise>
                    <input value="Cancel" class="btn btn-default ml10" 
                    onclick="javascript:window.location.href = '${SourceLeadWeb_atsUrl}/userSummary/${userCode}'"
                    type="button">
                    </c:otherwise>
                    </c:choose>
<%--                    <c:if test="${status == 'NEW' || status == 'NODATA'}">						     --%>
<!-- 						<input value="Cancel" class="btn btn-default ml10" type="button" id="cancelAssociateSalaryIndBtn">                     -->
<%--                     </c:if>  --%>
                    </div>
                  </div>
                </div>
            </div>
         </form:form>
<div id="cnfPHPopUpDiv"></div>
<script type="text/javascript">
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };
</script>

<script type="text/javascript">
function shiftToLeaveTypeDetails(){
	var indLoanAndAdvancesDetails = document.getElementById("indLeaveTypeDetails");
	 document.getElementById("indLeaveTypeDetails").removeAttribute("class");
	 indLoanAndAdvancesDetails.href="#tab-6";
	 $("#indLeaveTypeDetails").click();
}
</script>

<script>
function calculateToPerYear(shortCode){
	var monthly = document.getElementById("payHeadMonthly["+shortCode+"]").value;
	var floatRegex = /^((\d+(\.\d *)?)|((\d*\.)?\d+))$/;
	if(monthly != '' && floatRegex.test(monthly)){
		document.getElementById("payHeadYearly["+shortCode+"]").value = (monthly*12).toFixed(2);
	}else{
		document.getElementById("payHeadYearly["+shortCode+"]").value = "";
	}
}
function calculateToPerMonth(shortCode){
	var yearly = document.getElementById("payHeadYearly["+shortCode+"]").value;
	var floatRegex = /^((\d+(\.\d *)?)|((\d*\.)?\d+))$/;
	if(yearly != '' && floatRegex.test(yearly)){
		document.getElementById("payHeadMonthly["+shortCode+"]").value = (yearly/12).toFixed(2);
	}else{
		document.getElementById("payHeadMonthly["+shortCode+"]").value = "";
	}
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
						  var yearPayHeadValue = document.getElementById("payHeadYearly["+match[1]+"]").value;
						  formatedFormula = formatedFormula.replace(match[1], yearPayHeadValue);
						}
						 var result = eval(formatedFormula);
						document.getElementById("payHeadMonthly["+shortCode+"]").value = (result/12).toFixed(2);
						document.getElementById("payHeadYearly["+shortCode+"]").value = (result).toFixed(2);
			        break;
			    case "% of CTC":
						var ctc = document.getElementById("annualSal").value;
						var result = eval((ctc/100)*formula);
						document.getElementById("payHeadMonthly["+shortCode+"]").value = (result/12).toFixed(2);
						document.getElementById("payHeadYearly["+shortCode+"]").value = (result).toFixed(2);
			        break;
			    case "% of Gross pay":
						
			        break;
			    case "Flat":
				    	var result = eval(formula);
						document.getElementById("payHeadMonthly["+shortCode+"]").value = (result).toFixed(2);
						document.getElementById("payHeadYearly["+shortCode+"]").value = (result*12).toFixed(2);
			    	break;
			    default:
			    		if(document.getElementById("payHeadMonthly["+shortCode+"]").value != ""){
			    			var result = eval(document.getElementById("payHeadMonthly["+shortCode+"]").value);
			    			document.getElementById("payHeadMonthly["+shortCode+"]").value = (result).toFixed(2);
							document.getElementById("payHeadYearly["+shortCode+"]").value = (result*12).toFixed(2);
			    		}else if(document.getElementById("payHeadYearly["+shortCode+"]").value != ""){
			    			var result = eval(document.getElementById("payHeadYearly["+shortCode+"]").value);
			    			document.getElementById("payHeadMonthly["+shortCode+"]").value = (result).toFixed(2);
							document.getElementById("payHeadYearly["+shortCode+"]").value = (result*12).toFixed(2);
			    		}else{
			    			document.getElementById("payHeadMonthly["+shortCode+"]").value = ("0.00");
							document.getElementById("payHeadYearly["+shortCode+"]").value = ("0.00");
			    		}
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
		
		if(document.getElementById("BA")!=null)
		if(!document.getElementById("BA").checked || document.getElementById("payHeadMonthly[BA]").value == "" || document.getElementById("payHeadMonthly[BA]").value == "0.00" || document.getElementById("payHeadMonthly[BA]").value == "0" || document.getElementById("payHeadMonthly[BA]").value == "0.0" || document.getElementById("payHeadMonthly[BA]").value == "0." || document.getElementById("payHeadYearly[BA]").value == "" || document.getElementById("payHeadYearly[BA]").value == "0.00" || document.getElementById("payHeadYearly[BA]").value == "0" || document.getElementById("payHeadYearly[BA]").value == "0.0" || document.getElementById("payHeadYearly[BA]").value == "0."){
			payheadsNotMatchedAlert('impba');
			return false;
		}
		
		var annualSalary = document.getElementById("annualSal").value;
		var annualPayheadTotal = 0.00;
		$("#phTblBody input:checked").each(function(){
			/* if(document.getElementById("payheadSC["+$(this).attr('id')+"]").value == 'Earnings'){ */
				annualPayheadTotal += parseFloat(document.getElementById("payHeadYearly["+$(this).attr('id')+"]").value);
			/* } */
		});
		if(parseFloat(annualSalary) != parseFloat(annualPayheadTotal)){
			payheadsNotMatchedAlert('mismatch');
		}else{
			cnfToSaveAssociateSalaryInd();
		}
	}
}

function payheadsNotMatchedAlert(msg){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_cnf_pop' class='remove_user_pop remove_user_pop_a'>";
	if(msg==='impba'){
		result = result +"		<p>Configuration of 'Basic (BA)' is mandatory</p>";
	}else if(msg==='mismatch'){
		result = result +"		<p>Annual Salary and Payheads are mismatched</p>";
	}
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'>";
	if(msg==='mismatch'){
		result = result +"		<input type='button' value='Proceed to Save' onClick='proceedToSavePH();'>";
	}
	if(msg==='impba'){
		result = result +"		<input type='button'  value='Cancel' onClick='closeAlertPH();'></b>";
	}else if(msg==='mismatch'){
		result = result +"		<input class='u_can_color' type='button'  value='Cancel' onClick='closeAlertPH();'></b>";
	}
	result = result +"	</div>";
	$("#cnfPHPopUpDiv").empty();
	$("#cnfPHPopUpDiv").append(result);
	$("#remove_cnf_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}

function closeAlertPH(){
	$("#remove_cnf_pop, .project_inn_mask").fadeOut();
}

function proceedToSavePH(){
	$("#remove_cnf_pop, .project_inn_mask").fadeOut();
	cnfToSaveAssociateSalaryInd();
}

function cnfToSaveAssociateSalaryInd(){
	if ($('#associateSalary_form').validate(false, validationSettings)) {
		var formData = getFormData1("associateSalary_form");
		var status = $("#status").val();
		var pmsUrl ='${SourceLeadWeb_pmsUrl}';
		var atsUrl ='${SourceLeadWeb_atsUrl}';
		$.ajax({
			 type : "POST",
			 data: formData,
		     processData: false,
		     contentType:false, 
			 url : pmsUrl+"/saveAssociateSalaryInd?"+tokenParamName+"="+tokenValue,
			 success : function(result) {
			  		if(status == "NEW" || status == "NODATA"){
			  		//Redirecting to userlist
				  		location.replace(atsUrl+"/usersList");	
			  		}else{
			  			$('#editAssociateBtn').css('display','block');
				  		$('#associateSalary_form input').attr('disabled','true');
				  		$('#associateSalary_form select').prop('disabled', true);
				  		$('#saveAssociateBtnDiv').css('display','none');
				  		$('#editSectionDiv').css('display','none');
				  		$('#stopSalaryDiv').show();
			  		}
			 }
		});
	}
}
$('body').on('blur', '#associateSalary_form', function() {
	$("#associateSalary_form").showHelpOnFocus();
	$("#associateSalary_form").validateOnBlur(false, validationSettings);
});
</script>

<script>
$('#editAssociateBtn').click(function() {
	$('#editAssociateBtn').css('display','none');
	$('#saveAssociateBtnDiv').css('display','block');
	$('#associateSalary_form input').removeAttr('disabled');
	$('#associateSalary_form select').removeAttr('disabled');
	$('#editSectionDiv').css('display','block');
	$('#stopSalaryDiv').hide();
});
</script>

<script>
$('#cancelAssociateSalaryIndBtn').click(function() {
	var status = $("#status").val();
	if(status == "NEW" || status == "NODATA"){
		location.reload();
	}
});
</script>

<script>

$('.selectall').click(function() {
    $(this.form.elements).filter(':checkbox').prop('checked', this.checked).trigger("onclick");
});

</script>

<script>
function getPayGroups(){
	var pmsUrl ='${SourceLeadWeb_pmsUrl}';
	var payFrequency = $("#payFrequency").val();
	var userId = $("#userId").val();
		 $.ajax({
				type : "GET",
				url : pmsUrl+"/getPayGroupsByPayFrequency?"+tokenParamName+"="+tokenValue+"&payFrequency="+payFrequency+"&userId="+userId,
				success : function(result) {
				var output = "";
				output = output + "<option value=''>Select Group</option>";
				for (var i=0; i<result.length; i++)
				{
					var opt = result[i];
					output = output + "<option value='" +opt.groupUUID+ "'>"+opt.groupName+"</option>";
				}
				$("#payGroupId").empty();
				$("#payGroupId").append(output);
				$('#payGroupId').trigger("chosen:updated");
					
				}
		 });
		 getPayFreqHours();
}
</script>

<script>
function getPayFreqHours(){
	var payFrequency = $("#payFrequency").val();
	if(payFrequency === 'Annual'){
		$("#standardHours").val('2088');
	}
	if(payFrequency === 'Monthly'){
		$("#standardHours").val('176');
	}
	if(payFrequency === 'Bi-Weekly'){
		$("#standardHours").val('80');
	}
	if(payFrequency === 'Weekly'){
		$("#standardHours").val('40');
	}
	calculateSal();
}
</script>

<script>
function updatePayheads(){
	$("#phTblBody input:checked").each(function(){
	    $(this).trigger('onclick');
	});
}
</script>

<script>
function calculateSal(){
	var payFrequency = $("#payFrequency").val();
	var annualSal = $("#annualSal").val();
	if(!annualSal){
		annualSal = 0;
	}
	var hours = $("#standardHours").val();
	if(!hours){
		hours = 0;
	}
	if(annualSal != null && annualSal != '' && annualSal != 'undefined' && hours != null && hours != '' && hours != 'undefined'){
		var rate = 0;
		if(payFrequency === 'Annual'){
			rate = parseFloat(annualSal) / 1;
			$("#perPaySal").val(rate.toFixed(2));
		}
		if(payFrequency === 'Monthly'){
			rate = parseFloat(annualSal) / 12;
			$("#perPaySal").val(rate.toFixed(2));
		}
		if(payFrequency === 'Bi-Weekly'){
			rate = parseFloat(annualSal) / 26;
			$("#perPaySal").val(rate.toFixed(2));
		}
		if(payFrequency === 'Weekly'){
			rate = parseFloat(annualSal) / 52;
			$("#perPaySal").val(rate.toFixed(2));
		}
		var hourRate = rate/hours;
		$("#hourlyRate").val(hourRate.toFixed(2));
	}
	updatePayheads();
}
</script>

<script>
function calculatePayPerSal(){
	var payFrequency = $("#payFrequency").val();
	var perPaySal = $("#perPaySal").val();
	if(!perPaySal){
		perPaySal = 0;
	}
	var hourlyRate = $("#hourlyRate").val();
	if(!hourlyRate){
		hourlyRate = 0;
	}
	var standardHours = $("#standardHours").val();
	if(!standardHours){
		standardHours = 0;
	}
	if(perPaySal !='' && perPaySal != 'undefined'){
		if(payFrequency === 'Annual'){
			var annualRate = parseFloat(perPaySal) * 1;
			$("#annualSal").val(annualRate.toFixed(2));
		}
		if(payFrequency === 'Monthly'){
			var annualRate = parseFloat(perPaySal) * 12;
			$("#annualSal").val(annualRate.toFixed(2));
		}
		if(payFrequency === 'Bi-Weekly'){
			var annualRate = parseFloat(perPaySal) * 26;
			$("#annualSal").val(annualRate.toFixed(2));
		}
		if(payFrequency === 'Weekly'){
			var annualRate = parseFloat(perPaySal) * 52;
			$("#annualSal").val(annualRate.toFixed(2));
		}
		if(hourlyRate != null && hourlyRate != '' && hourlyRate != 'undefined' && standardHours != 0){
			var hourlyrate = parseFloat(perPaySal) / standardHours;
			$("#hourlyRate").val(hourlyrate.toFixed(2));
		}
	}
	updatePayheads();
}
</script>

<script>
function calculateHourlyRateSal(){
	var payFrequency = $("#payFrequency").val();
	var perPaySal = $("#perPaySal").val();
	if(!perPaySal){
		perPaySal = 0;
	}
	var hourlyRate = $("#hourlyRate").val();
	var standardHours = $("#standardHours").val();
	var annualSal = $("#annualSal").val();
	if(annualSal != null && annualSal != '' && annualSal != 'undefined'){
		var perPaySal = parseFloat(hourlyRate) * standardHours;
		$("#perPaySal").val(perPaySal.toFixed(2));
		calculatePayPerSal();
	}
	updatePayheads();
}
</script>

<script>
function calculatePercentSal(){
	var percent = $("#increasePer").val();
	if(!percent){
		percent = 0;
	}
	var annualSal = ${associateSalaryInd.annualSalary};
	if(!annualSal){
		annualSal = 0;
	}
	var annualByPercentage = eval((annualSal * percent)/100);
	$("#annualSal").val( eval(annualSal + annualByPercentage).toFixed(2));
	calculateSal();
}
</script>

<script>
	  var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
	  var jsDateFormat= '';
	  if(locationDate.match("yyyy-MM-dd")){
		  jsDateFormat = 'yy-mm-dd';
	  }if(locationDate.match("dd-MM-yyyy")){
		  jsDateFormat = 'dd-mm-yy'; 
	  }if(locationDate.match("MM-dd-yyyy")){
		  jsDateFormat = 'mm-dd-yy'; 
	  }if(locationDate.match("yyyy-dd-MM")){
		  jsDateFormat = 'yy-dd-mm'; 
	  }
	  $('#effectiveDate').datepicker({
	      dateFormat: jsDateFormat,
	      changeMonth: true,
	  	  changeYear: true,
	  	  minDate:0,
	  	  onSelect : function(selected) {
	  	  }
	  });
	  
	  function clearEffectiveDate(){
			$("#effectiveDate").val("");
	  }
	  
	  /* $(document).ready(function(){
		  if ($('#stopsalary').is(':checked')) {
	        	$('.switchSalary').find('.slider').css({"background":"red"});
	        }
	  }); */  
		  
	  	$('#stopsalary').on('click',function () {
	    	var stopsalary = 'NO';
	    	var uuid = "${uuid}";
	        if ($('#stopsalary').is(':checked')) {
	        	//$('.switchSalary').find('.slider').css({"background":"red"});
	        	stopsalary = 'YES';
	        } else {
	        	stopsalary = 'NO';
	        	//$('.switchSalary').find('.slider').css({"background":"#ccc"});
	        }
	        var pmsUrl ='${SourceLeadWeb_pmsUrl}';

	        $.ajax({
	        	type : "POST",
				url : pmsUrl+"/stopSalaryProcessingForEmployee?"+tokenParamName+"="+tokenValue+"&stopSalary="+stopsalary+"&uuid="+uuid,
				success : function(result) {
					if(result != null && result.object === "DataMissing") {
						if($('#stopsalary').is(':checked')){
							$('#stopsalary').prop('checked', false);
						}else{
							$('#stopsalary').prop('checked', true);
						}
   						showError("Oops! Something went wrong");
   						$("#locationAlert").fadeIn(200);
   				        $('#locationAlert').delay(2000).fadeOut('slow');
   					}else{
                    	$("#selectoruser").removeAttr("style");
                   		$("#selectoruser").css({ display: "block" });
                   		$("#userMsgDiv").html("Success!! Stop Salary is updated");
                   		$("#selectoruser").fadeIn(200);
                        $('#selectoruser').delay(2000).fadeOut('slow');
   					}
				}
		 	});
	  	});
	  
</script>

<script>
$(".allownumericwithdecimal").on("keypress keyup blur",function (event) {
	$(this).val($(this).val().replace(/[^0-9\.]/g,''));
	if ((event.which != 46 || $(this).val().indexOf('.') != -1) && (event.which < 48 || event.which > 57)) {
	event.preventDefault();
	}
});
</script>

