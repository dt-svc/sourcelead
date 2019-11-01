<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="loading"></div>
<section class="content-wraper contentWraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Taxes</h2>
		</div>
		<div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo; <a href="${SourceLeadWeb_pmsUrl}/payrollConfiguration">Payroll Configure</a> &raquo;Taxes</div>
	</section>
	<div class="contentareaInner-styles clearfix">
		<div class="clearfix form-horizontal minHeight_225 minHeightcol5">
			<form id="addTaxConfigForm">
				<input type="hidden" name="id" value="${tax.id}" />
				<input type="hidden" name="stateTaxId" value="${tax.stateTaxId}" />
				<div class="row min">
					<h4 class="col-md-12">ESI</h4>
					<div class="col-md-5">
						<label class="clearfix">Associate Contribution</label>
						<div class="clearfix">
							<input type="text" class="form-control" field-name="Associate Contribution" data-validation="required validate_float validate_length length1-15"
								name="employerESIContribution" value="${tax.employerESIContribution}"
								placeholder="Associate Contribution">
						</div>
					</div>
					<div class="col-md-5">
						<label class="clearfix">Organisation Contribution</label>
						<div class="clearfix">
							<input type="text" class="form-control" field-name="Organisation Contribution"
								data-validation="required validate_float validate_length length1-15" name="employeeESIContribution"
								value="${tax.employeeESIContribution}" placeholder="Enter Organisation Contribution">
						</div>
					</div>
					<h4 class="col-md-12">Provident Fund</h4>
					<div class="col-md-5">
						<label class="clearfix">Associate Contribution</label>
						<div class="clearfix">
							<input type="text" class="form-control" field-name="Provident Fund"
								data-validation="required validate_float validate_length length1-5" name="employerPFContribution"
								value="${tax.employerPFContribution}" placeholder="Enter Associate Contribution %">
						</div>
					</div>
					<div class="col-md-5">
						<label class="clearfix">Organisation Contribution</label>
						<div class="clearfix">
							<input type="text" class="form-control" field-name="Organisation Contribution"
								data-validation="required validate_float validate_length length1-5" name="employeePFContribution"
								value="${tax.employeePFContribution}" placeholder="Enter Organisation Contribution %">
						</div>
					</div>
					
					<%-- <div class="col-md-5">
						<label class="clearfix">EDLI</label>
						<div class="clearfix">
							<input type="text" class="form-control" field-name="EDLI" name="edli" 
								data-validation="required validate_float validate_length length1-5" name="edli" value="${tax.edli}" placeholder="Enter EDLI %">
						</div>
					</div>
					<div class="col-md-5">
						<label class="clearfix">Pension</label>
						<div class="clearfix">
							<input type="text" class="form-control" field-name="Pension"
								data-validation="required validate_float validate_length length1-5" name="pension" value="${tax.pension}" placeholder="Enter Pension %">
						</div>
					</div>
					<div class="col-md-5">
						<label class="clearfix">PF Admin Charges</label>
						<div class="clearfix">
							<input type="text" class="form-control"
								field-name="PF Admin Charges" name="pfAdminCharges"
								data-validation="required validate_float validate_length length1-5" name="pfAdminCharges" value="${tax.pfAdminCharges}" placeholder="Enter PF Admin Charges %">
						</div>
					</div>
					<div class="col-md-5">
						<label class="clearfix">EDLI Admin Charges</label>
						<div class="clearfix">
							<input type="text" class="form-control"
								field-name="EDLI Admin Charges" name="edliAdminCharges"
								data-validation="required validate_float validate_length length1-5" name="edliAdminCharges" value="${tax.edliAdminCharges}" placeholder="Enter EDLI Admin Charges">
						</div>
					</div> --%>
					
					<div class="col-md-5">
						<label class="clearfix">PF Ceil Value</label>
						<div class="clearfix">
							<input type="text" class="form-control" field-name="PF Ceil Value" name="pfCeilValue"
								data-validation="required validate_float" name="pfCeilValue" value="${tax.pfCeilValue != null && tax.pfCeilValue != '' ? tax.pfCeilValue : '15000'}" placeholder="Enter PF Ceil value">
						</div>
					</div>
					<div class="clearfix"></div>
					<h4 class="col-md-12">IT Declaration Form</h4>
					<div class="col-md-5">
						<label class="clearfix">From Month</label>
						<div class="clearfix">
	                      <c:choose>
	                      	<c:when test="${orgLocations.startMonth != null && orgLocations.startMonth != ''}">
	                      		<select class="chosen-select" disabled='disabled' style="cursor: no-drop;">
			                    	<option selected="selected">${orgLocations.startMonth}</option>
			                    </select>	
	                      	</c:when>
	                      	<c:otherwise>
	                      		<select class="chosen-select" id="financialStartMonth" field-name="From Month" data-validation="required"
			                       onChange="validateFinancialYear();" name="financialStartMonth">
				                      <option value = "">--Select Month--</option>
				                      <option ${orgLocations.startMonth == 'January' ? 'selected' : ''}>January</option>
				                      <option ${orgLocations.startMonth == 'February' ? 'selected' : ''}>February</option>
				                      <option ${orgLocations.startMonth == 'March' ? 'selected' : ''}>March</option>
				                      <option ${orgLocations.startMonth == 'April' ? 'selected' : ''}>April</option>
				                      <option ${orgLocations.startMonth == 'May' ? 'selected' : ''}>May</option>
				                      <option ${orgLocations.startMonth == 'June' ? 'selected' : ''}>June</option>
				                      <option ${orgLocations.startMonth == 'July' ? 'selected' : ''}>July</option>
				                      <option ${orgLocations.startMonth == 'August' ? 'selected' : ''}>August</option>
				                      <option ${orgLocations.startMonth == 'September' ? 'selected' : ''}>September</option>
				                      <option ${orgLocations.startMonth == 'October' ? 'selected' : ''}>October</option>
				                      <option ${orgLocations.startMonth == 'November' ? 'selected' : ''}>November</option>
				                      <option ${orgLocations.startMonth == 'December' ? 'selected' : ''}>December</option>
			                      </select>
	                      	</c:otherwise>
	                      </c:choose>
						</div>
					</div>
					<div class="col-md-5">
						<label class="clearfix">To Month</label>
						<div class="clearfix">
							<c:choose>
	                      	<c:when test="${orgLocations.startMonth != null && orgLocations.startMonth != ''}">
	                      		<select class="chosen-select" disabled='disabled'>
			                    	<option selected="selected">${orgLocations.endMonth}</option>
			                    </select>	
	                      	</c:when>
	                      	<c:otherwise>
	                      		<input type="hidden" name="financialEndMonth" id="financialEndMonthHidden" value=""/>
	                      		<select class="chosen-select" id="financialEndMonth" field-name="To Month" data-validation="required" disabled="disabled" name="financialEndMonth">
			                    	<option value = "">--Select Month--</option>
				                    <option ${orgLocations.endMonth == 'January' ? 'selected' : ''}>January</option>
				                    <option ${orgLocations.endMonth == 'February' ? 'selected' : ''}>February</option>
				                    <option ${orgLocations.endMonth == 'March' ? 'selected' : ''}>March</option>
				                    <option ${orgLocations.endMonth == 'April' ? 'selected' : ''}>April</option>
				                    <option ${orgLocations.endMonth == 'May' ? 'selected' : ''}>May</option>
				                    <option ${orgLocations.endMonth == 'June' ? 'selected' : ''}>June</option>
				                    <option ${orgLocations.endMonth == 'July' ? 'selected' : ''}>July</option>
				                    <option ${orgLocations.endMonth == 'August' ? 'selected' : ''}>August</option>
				                    <option ${orgLocations.endMonth == 'September' ? 'selected' : ''}>September</option>
				                    <option ${orgLocations.endMonth == 'October' ? 'selected' : ''}>October</option>
				                    <option ${orgLocations.endMonth == 'November' ? 'selected' : ''}>November</option>
				                    <option ${orgLocations.endMonth == 'December' ? 'selected' : ''}>December</option>
		                    	</select>
	                      	</c:otherwise>
	                      </c:choose>
						</div>
					</div>
					<div class="clearfix"></div>
					<c:if test="${tax.ptList.size() > 0}">
						<h4 class="col-md-12">Professional Tax</h4>
						<div class="clearfix col-md-5">
							<table class="clearfix table table-bordered">
								<thead>
									<tr>
										<th>Monthly Salary</th>
										<th>PT</th>
										<th>Applicable Month</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${tax.ptList}" var="pt">
										<tr>
											<td>${pt.amountFrom} - ${pt.amountTo}</td>
											<td>${pt.amount}</td>
											<td>${pt.monthApplicable}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</c:if>
				</div>
			</form>
		</div>
	</div>
	<div class="clearfix footerstyelsNew">
		<div class="col-lg-12">
			<div class="pull-right">
				<input value="${tax.id > 0 ? 'Update' : 'Save'}" class="btn btn-success btnSuccess" type="button" id="saveTaxConfig">
				<input value="Cancel" class="btn btn-default ml10" type="button" id="cancelTaxConfig" onclick="cancel()";>
			</div>
		</div>
	</div>
	<!-- Content Area Ends-->
</section>
<script type="text/javascript">
	$(document).ready(function() {
		var locationDate = '${SourceLeadWeb_orgLocations.dateFormat}';
		if (locationDate.match("yyyy-MM-dd")) {
			dateFormat = 'yy-mm-dd';
		}
		if (locationDate.match("dd-MM-yyyy")) {
			dateFormat = 'dd-mm-yy';
		}
		if (locationDate.match("MM-dd-yyyy")) {
			dateFormat = 'mm-dd-yy';
		}
		if (locationDate.match("yyyy-dd-MM")) {
			dateFormat = 'yy-dd-mm';
		}

		$('#itDeclarationFromDate').datepicker({
			createButton : false,
			displayClose : true,
			closeOnSelect : false,
			selectMultiple : true,
			dateFormat : dateFormat,
			changeMonth : true,
			changeYear : true,
			onSelect : function(selected) {
				var dt = $(this).datepicker('getDate', '');
				dt.setDate(dt.getDate());
				$("#itDeclarationToDate").datepicker("option", "minDate", dt);
			}
		});
		$('#itDeclarationToDate').datepicker({
			dateFormat : dateFormat,
			changeMonth : true,
			changeYear : true,
			minDate : $("#itDeclarationFromDate").val(),
			onSelect : function(selected) {

			}
		});
	});
	
	var taxId = "${tax.id}";
	$("#saveTaxConfig").click(function() {
		if ($('#addTaxConfigForm').validate(false, validationSettings)) {
			$("#saveTaxConfig").attr("disabled","disabled");
			$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
			var formData = getFormData("addTaxConfigForm");
			var pmsUrl ='${SourceLeadWeb_pmsUrl}';

			$.ajax({
				type : "POST",
        	    data: formData,
				url : pmsUrl+"/savePayrollTax",
				success : function(response) {
					if (response.response === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (response.response === "SUCCESS") {
		        		if (response.object > 0) {
			        		$(".success_pop").css("display","block");
			        		$(".success_pop span").html("Tax Configuration " + (taxId > 0 ? "updated" : "added") + " successfully....");
			        		setTimeout(function() {
			        			location.reload();					 			
					 		},100);    
		        		} else if (response.object === -1) {
		        			/* showError("Tax record already exists for the selected fields"); */
		        		}
		        	} else if(response.response === "FAILED") {
		        		showError(response.responseMessage);
		        	} else if (response.indexOf('Sign In') !== -1) {
		        		location.replace(pmsUrl);
		        	} else {
		        	    showError("Operation failed, please try again");
		        	}
					$("#saveTaxConfig").removeAttr("disabled","disabled");
					$("#loading").removeAttr("style");
				},
			});
			return true;
		} else {
			return false;
		}
	});
	
	function validateFinancialYear(){
		var startMonth = $("#financialStartMonth").val();
		var endMonth = "";
		if (startMonth == "January") {
			endMonth = "December";
		} else if (startMonth == "February") {
			endMonth = "January";
		} else if (startMonth == "March") {
			endMonth = "February";
		} else if (startMonth == "April") {
			endMonth = "March";
		} else if (startMonth == "May") {
			endMonth = "April";
		} else if (startMonth == "June") {
			endMonth = "May";
		} else if (startMonth == "July") {
			endMonth = "June";
		} else if (startMonth == "August") {
			endMonth = "July";
		} else if (startMonth == "September") {
			endMonth = "August";
		} else if (startMonth == "October") {
			endMonth = "September";
		} else if (startMonth == "November") {
			endMonth = "October";
		} else if (startMonth == "December") {
			endMonth = "November";
		}
		$("#financialEndMonth").val(endMonth);
		$("#financialEndMonthHidden").val(endMonth);
		$("#financialEndMonth").trigger("chosen:updated");
	}

	function saveFinancialYear() {
		var locationId = '${SourceLeadWeb_orgLocationId}';
		var startMonth = $("#financialStartMonth").val();
		var endMonth = $("#financialEndMonth").val();
		if (startMonth != null && startMonth != 'undefined' && endMonth != null
				&& endMonth != 'undefined') {
			var pmsUrl ='${SourceLeadWeb_pmsUrl}';

			$.ajax({
				type : "GET",
				url : pmsUrl + "/saveFinanacialYear?locationId="
						+ locationId + "&startMonth=" + startMonth
						+ "&endMonth=" + endMonth,
				success : function(result) {
					/* $("#selector1").removeAttr("style");
					$("#selector1").css({ display: "block" });
					$("#msgDiv").html("Details Updated Successfully");
					msgFadein(); */
					$(".success_pop").css("display", "block");
					$(".success_pop span").html("Financial Year updated successfully....");
					setTimeout(function() {
						location.reload();
					}, 100);
				},
			});
		}
	}
</script>

<script>
function cancel(){
	var pmsUrl ='${SourceLeadWeb_pmsUrl}';
	window.location.href =  pmsUrl+"/indPayrollConfiguration";
}



</script>