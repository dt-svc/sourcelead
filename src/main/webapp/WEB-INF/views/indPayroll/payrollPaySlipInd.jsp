<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 
<script>
function validatePayslipName(uuid){
	var payslipName = document.getElementById("paySlipName").value.trim();
	var sourceleadUrl = '${sourceleadUrl}';
	$('#payslipNameDiv').find('p.jquery_form_error_message').remove();
	$.ajax({
		type : "POST",
		url : sourceleadUrl+ "/validatePayslipName?${_csrf.parameterName}=${_csrf.token}&payslipName="+payslipName+"&payslipUUID="+uuid,
		success : function(data) {
			 if (data == true) {
				$("input#paySlipName").after("<p class='jquery_form_error_message'>"+ payslipName+ " is Already Existed</span>");
				document.getElementById("paySlipName").setAttribute('record-exist', 'yes');
				document.getElementById("paySlipName").setAttribute('record-exist-errorMsg',payslipName+ ' is Already Existed');
			} else {
				$('input#paySlipName').removeAttr("record-exist record-exist-errormsg");
			} 
		},
	});
}
</script> 
<section class="content-wraper contentWraper">
<section class="content-header clearfix">
  <div class="pull-left">
    <h2>Configure Payslip</h2>
  </div>
  <c:if test="${empty payslipInd.uuid}">
    <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo;  <a href="<c:url value='${appPath}/settings'/>">Settings</a>&raquo; <a href="<c:url value='${appPath}/payrollConfiguration'/>">Payroll Configure</a> &raquo; Payslip</div>
  </c:if>
  <c:if test="${!empty payslipInd.uuid}">
    <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo;  <a href="<c:url value='${appPath}/settings'/>">Settings</a>&raquo; <a href="<c:url value='${appPath}/payrollConfiguration'/>">Payroll Configure</a> &raquo; <a href="<c:url value='${appPath}/payrollPaySlipView'/>">Payroll Configure List</a> &raquo; Payslip Edit</div>
  </c:if>
</section>
 <div class="clearfix"></div>
 
  <div class="contentareaInner-styles clearfix">
      <div class="clearfix">
      <form id="payslip_form" name="payslip_form">
      <input type="hidden" name="uuid" value="${payslipInd.uuid}"> 
      <div class="row">
      <div class="clearfix">
      	<div class="col-md-4" id="payslipNameDiv">
      		<label>Payslip Name</label>
      		<div class="clearfix">
      			<input type="text" class="form-control"  name="paySlipName" id="paySlipName" value="${payslipInd.paySlipName}"  data-validation="required validate_length length2-250" field-name="Payslip Name" onkeyup="validatePayslipName('${payslipInd.uuid}');">
      		</div>
      		<div class="clearfix"></div>
      	</div>
      	<div class="col-md-6">
      		<label>Payslip Description</label>
      		<div class="clearfix">
      			<textarea class="form-control" cols="2"  name="paySlipDescription" id="paySlipDescription" value="${payslipInd.paySlipDescription}" data-validation="required validate_length length2-100" field-name="Payslip Description">${payslipInd.paySlipDescription}</textarea>
      		</div>
      		<div class="clearfix"></div>
      	</div>
      	</div>
		  <div class="clearfix mt mb"></div>
				<div class="col-md-3">
				<h5>Company Details</h5>
				<ul class="list-unstyled">
				<li>
				<div class="checkbox-new">
				<input id="cName" class="checkbox-custom" name="companyName" value="Y" <c:if test="${payslipInd.companyName == 'Y'}">checked='checked'</c:if> type="checkbox">
				<label for="cName" class="checkbox-custom-label"><span class="ml10">Company Name</span></label>
				<div class="clearfix"></div>
				</div>
				</li>
				
				<li>
				<div class="checkbox-new">
				<input id="cAddress" class="checkbox-custom" name="companyAddress" value="Y" <c:if test="${payslipInd.companyAddress == 'Y'}">checked='checked'</c:if> type="checkbox">
				<label for="cAddress" class="checkbox-custom-label"><span class="ml10">Address</span></label>
				<div class="clearfix"></div>
				</div>
				</li>
				
				<li>
				<div class="checkbox-new">
				<input id="cContactDetails" class="checkbox-custom" name="companyContactDetails" <c:if test="${payslipInd.companyContactDetails == 'Y'}">checked='checked'</c:if> value="Y" type="checkbox">
				<label for="cContactDetails" class="checkbox-custom-label"><span class="ml10">Contact Details</span></label>
				<div class="clearfix"></div>
				</div>
				</li>
				
				<li>
				<div class="checkbox-new">
				<input id="companyLogo" class="checkbox-custom" name="companyLogo" value="Y" <c:if test="${payslipInd.companyLogo == 'Y'}">checked='checked'</c:if> type="checkbox">
				<label for="companyLogo" class="checkbox-custom-label"><span class="ml10">Company Logo</span></label>
				<div class="clearfix"></div>
				</div>
				</li>
				</ul>

				</div>
				<div class="col-md-5">
				<h5>Employee Details</h5>
				<ul class="list-unstyled list-inline" id="ed">
				<li>
				<div class="checkbox-new">
				<input id="employeeId" class="checkbox-custom" name="employeeId" value="Y" <c:if test="${payslipInd.employeeId == 'Y'}">checked='checked'</c:if> type="checkbox">
				<label for="employeeId" class="checkbox-custom-label"><span class="ml10">Employee Id</span></label>
				<div class="clearfix"></div>
				</div>
				</li>
				<li>
				<div class="checkbox-new">
				<input id="employeeName" class="checkbox-custom" name="employeeName" value="Y" <c:if test="${payslipInd.employeeName == 'Y'}">checked='checked'</c:if> type="checkbox">
				<label for="employeeName" class="checkbox-custom-label"><span class="ml10">Employee Name</span></label>
				<div class="clearfix"></div>
				</div>
				</li>
				<li>
				<div class="checkbox-new">
				<input id="employeeType" class="checkbox-custom" name="employeeType" value="Y" <c:if test="${payslipInd.employeeType == 'Y'}">checked='checked'</c:if> type="checkbox">
				<label for="employeeType" class="checkbox-custom-label"><span class="ml10">Employee Type</span></label>
				<div class="clearfix"></div>
				</div>
				</li>
				<li>
				<div class="checkbox-new">
				<input id="empContactDetails" class="checkbox-custom" name="empContactDetails" value="Y" <c:if test="${payslipInd.employeeType == 'Y'}">checked='checked'</c:if> type="checkbox">
				<label for="empContactDetails" class="checkbox-custom-label"><span class="ml10">Contact Details</span></label>
				<div class="clearfix"></div>
				</div>
				</li>
				<li>
				<div class="checkbox-new">
				<input id="empJoiningdate" class="checkbox-custom" name="empJoiningdate" value="Y" <c:if test="${payslipInd.employeeJoinDate == 'Y'}">checked='checked'</c:if> type="checkbox">
				<label for="empJoiningdate" class="checkbox-custom-label"><span class="ml10">Joining Date</span></label>
				<div class="clearfix"></div>
				</div>
				</li>
				
				<li>
				<div class="checkbox-new">
				<input id="empUan" class="checkbox-custom" name="employeeUanNumber" value="Y" <c:if test="${payslipInd.employeeUanNumber == 'Y'}">checked='checked'</c:if> type="checkbox">
				<label for="empUan" class="checkbox-custom-label"><span class="ml10">UAN Number</span></label>
				<div class="clearfix"></div>
				</div>
				</li>
				
				
				<li>
				<div class="checkbox-new">
				<input id="employeePfNumber" class="checkbox-custom" name="employeePfNumber" value="Y" <c:if test="${payslipInd.employeePfNumber == 'Y'}">checked='checked'</c:if> type="checkbox">
				<label for="employeePfNumber" class="checkbox-custom-label"><span class="ml10">PF Number</span></label>
				<div class="clearfix"></div>
				</div>
				</li>
				<li>
				<div class="checkbox-new">
				<input id="employeeEsiNumber" class="checkbox-custom" name="employeeEsiNumber" value="Y" <c:if test="${payslipInd.employeeEsiNumber == 'Y'}">checked='checked'</c:if> type="checkbox">
				<label for="employeeEsiNumber" class="checkbox-custom-label"><span class="ml10">ESI Number</span></label>
				<div class="clearfix"></div>
				</div>
				</li>
				
				<li>
				<div class="checkbox-new">
				<input id="employeePanNumber" class="checkbox-custom" name="employeePanNumber" value="Y" <c:if test="${payslipInd.employeePanNumber == 'Y'}">checked='checked'</c:if> type="checkbox">
				<label for="employeePanNumber" class="checkbox-custom-label"><span class="ml10">PAN Number</span></label>
				<div class="clearfix"></div>
				</div>
				</li>
				
				
				<li>
				<div class="checkbox-new">
				<input id="employeeBankDetails" class="checkbox-custom" name="employeeBankDetails" value="Y" <c:if test="${payslipInd.employeeBankDetails == 'Y'}">checked='checked'</c:if> type="checkbox">
				<label for="employeeBankDetails" class="checkbox-custom-label"><span class="ml10">Bank Details</span></label>
				<div class="clearfix"></div>
				</div>
				</li>
				<li>
				<div class="checkbox-new">
				<input id="employeeLocation" class="checkbox-custom" name="employeeLocation" value="Y" <c:if test="${payslipInd.employeeLocation == 'Y'}">checked='checked'</c:if> type="checkbox">
				<label for="employeeLocation" class="checkbox-custom-label"><span class="ml10">Location</span></label>
				<div class="clearfix"></div>
				</div>
				</li>
				
				<li>
				<div class="checkbox-new">
				<input id="empDesignation" class="checkbox-custom" name="employeeDesignation" value="Y" <c:if test="${payslipInd.employeeDesignation == 'Y'}">checked='checked'</c:if> type="checkbox">
				<label for="empDesignation" class="checkbox-custom-label"><span class="ml10">Designation</span></label>
				<div class="clearfix"></div>
				</div>
				</li>
				</ul>
				</div>
				<div class="col-md-3">
				<h5>Leave Details</h5>
				<ul class="list-unstyled">
				<li>
				<div class="checkbox-new">
				<input id="leaveAttended" class="checkbox-custom" name="leaveAttended" value="Y" <c:if test="${payslipInd.leaveAttended == 'Y'}">checked='checked'</c:if> type="checkbox">
				<label for="leaveAttended" class="checkbox-custom-label"><span class="ml10">Attended</span></label>
				<div class="clearfix"></div>
				</div>
				</li>
				<li>
				<div class="checkbox-new">
				<input id="leaveLeaves" class="checkbox-custom" name="leaveLeaves" value="Y" <c:if test="${payslipInd.leaveLeaves == 'Y'}">checked='checked'</c:if> type="checkbox">
				<label for="leaveLeaves" class="checkbox-custom-label"><span class="ml10">Leaves</span></label>
				<div class="clearfix"></div>
				</div>
				</li>
				<li>
				<div class="checkbox-new">
				<input id="leaveLop" class="checkbox-custom" name="leaveLop" value="Y" <c:if test="${payslipInd.leaveLop == 'Y'}">checked='checked'</c:if> type="checkbox">
				<label for="leaveLop" class="checkbox-custom-label"><span class="ml10">LOP</span></label>
				<div class="clearfix"></div>
				</div>
				</li>
				</ul>
				</div>
				
      	<div class="clearfix"></div>
      </div>
   <hr>
    <div class="row">
    <div class="col-md-10">
				<ul class="list-unstyled list-inline">
				<li>
				<div class="checkbox-new">
				<input id="previousMonth" class="checkbox-custom" name="previousMonth" value="Y" <c:if test="${payslipInd.previousMonthPaid == 'Y'}">checked='checked'</c:if> type="checkbox">
				<label for="previousMonth" class="checkbox-custom-label"><span class="ml10">Show previous month paid</span></label>
				<div class="clearfix"></div>
				</div>
				</li>
				
				<li>
				<div class="checkbox-new">
				<input id="regularHours" class="checkbox-custom" name="regularHours" value="Y" <c:if test="${payslipInd.regularHours == 'Y'}">checked='checked'</c:if> type="checkbox">
				<label for="regularHours" class="checkbox-custom-label"><span class="ml10">Show regular hours</span></label>
				<div class="clearfix"></div>
				</div>
				</li>
				
				<li>
				<div class="checkbox-new">
				<input id="overtimeHours" class="checkbox-custom" name="overtimeHours" value="Y" <c:if test="${payslipInd.overtimeHours == 'Y'}">checked='checked'</c:if> type="checkbox">
				<label for="overtimeHours" class="checkbox-custom-label"><span class="ml10">Show overtime hours</span></label>
				<div class="clearfix"></div>
				</div>
				</li>
				</ul>
				</div>
	</div>
      </form>
</div>
</div>
<div class="clearfix footerstyelsNew">
        <div class="col-lg-12">
          <div class="pull-right">
          <c:if test="${!empty payslipInd.uuid}">
               <input value="Update" class="btn btn-success btnSuccess" type="button" onclick="savePaySlip();">
               <input value="Cancel" class="btn btn-default ml10" type="button" onClick="javascript:window.location.href = '<c:url value='${appPath}/payrollPaySlipView'/>'">
          </c:if>
          <c:if test="${empty payslipInd.uuid}">
               <input value="Save" class="btn btn-success btnSuccess" type="button" onclick="savePaySlip();">
               <input value="Cancel" class="btn btn-default ml10" type="button" onClick="javascript:window.location.href = '<c:url value='${appPath}/payrollConfiguration'/>'">
          </c:if>
          </div>
        </div>
      </div>
      
</section>
 
 <script>
 function savePaySlip(){
	 var uuid = '${payslipInd.uuid}';
	 if ($('#payslip_form').validate(false, validationSettings)) {
		 var sourceleadUrl = '${sourceleadUrl}';
		 var formData = getFormData("payslip_form");
		 $.ajax({
				type: "POST",
				data: formData,
				url : sourceleadUrl+ "/savePayrollPaySlipInd?"+tokenParamName+"="+tokenValue,
				success : function(data) {
					 if (data.response === "AWKWARD") {
	            			location.href = "/errorPage";
	  	    		    }
	            		else if(data.response === "SUCCESS"){
	            			if(uuid != null){
		            			 location.replace(sourceleadUrl + "/payrollPaySlipView");
	            			}else{
		            			 location.replace(sourceleadUrl + "/indPayrollConfiguration");
	            			}
			        	}
	            		else if (data.indexOf('Sign In') !== -1) {
	            			location.replace(sourceleadUrl);
	  				   } else {
	  					showError("Operation failed, please try again");
	  				}
				},
			}); 
			return true;
		}else {
			return false;
		}
 }
 $('body').on('blur', '#payslip_form', function() {
		$("#payslip_form").showHelpOnFocus();
		$("#payslip_form").validateOnBlur(false, validationSettings);
	});
 
 </script>
<script>
	$(function() {
		$("#ed").find("li").css({"width":"49%"});
	});
</script>

<jsp:include page="../masterFooter.jsp" />