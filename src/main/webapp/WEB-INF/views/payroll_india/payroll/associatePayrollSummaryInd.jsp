<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

	<div class="row">
		<div class="col-md-6">
			<div class="row">
				<label class="col-md-4"><strong>Name</strong></label>
					<div class="clearfix col-md-8">
						<p class="form-control-static">${indPayrollDTO.empName}</p>
					</div>
			</div>
		</div>
		<div class="col-md-2 text-center">
			<div class="row">
				<label class="col-md-4 mt5"><strong>Days</strong></label>
				<div class="clearfix col-md-8">
					<p class="form-control-static"><fmt:formatNumber>${indPayrollDTO.noOfDays}</fmt:formatNumber></p>
				</div>
			</div>
		</div>
		<div class="col-md-2 text-center">
			<div class="row">
				<label class="col-md-4 mt5"><strong>Leaves</strong></label>
				<div class="clearfix col-md-8">
					<p class="form-control-static"><fmt:formatNumber>${indPayrollDTO.noOfLeaves}</fmt:formatNumber></p>
				</div>
			</div>
		</div>
		<div class="col-md-2 text-center">
			<c:if test="${indPayrollDTO.approverStatus != 'APPROVED'}">
				<div class="pull-right">
					<button type="button" class="btn btn-success btnSuccess editNetpay"
						onClick="editNetpay();">Edit</button>
					<button type="button" class="btn btn-success btnSuccess saveNetpay"
						onClick="saveNetpay();" style="display: none;">Save</button>
				</div>
			</c:if>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12 mt">
		<form:form id="associatePayheadAdjust_form" name="associatePayheadAdjust_form">
			<input type="text" hidden id="paySheetFrequencyUUID" name="paySheetFrequencyUUID" value="${indPayrollDTO.paySheetFrequencyUUID}">
			<input type="text" hidden name="indEmpPayrollUUID" value="${indPayrollDTO.indEmpPayrollUUID}">
			<input type="text" hidden name="grossPay" value="${indPayrollDTO.grossPay}">
			<input type="text" hidden name="totalCtc" value="${indPayrollDTO.totalCtc}">
			<input type="text" hidden name="netPay" value="${indPayrollDTO.netPay}">
			<table width="100%" class="table mb0" id="tableNetpay">
				<tbody>
			<tr>
			<c:if test="${fn:length(earningPayhead) gt 0}">
				<td class="padding_right">
					<table class="table table-bordered tableNetpay mb0">
						<c:set var="earningTotal" value="${0.00}"/>
							<thead>
								<tr>
									<th>Earning</th>
									<th>Amount <span class="paddingLeft">(&#8377;)</span></th>
								</tr>
							</thead>
							<tbody class="earningPayheadTbady">
							<c:forEach var="earning" items="${earningPayhead}" varStatus="loop1">
							<c:if test="${earning.taxStatus eq 'Taxable'}">
								<tr>
									<th><div class="tbldata-dotdot inlineBlock"> <input type="text" hidden name="associateSalaryPayheadIndEarningList[${loop1.index}].payrollPayheadName" value="${earning.payrollPayheadName}"> ${earning.payrollPayheadName}</div></th>
									<td><input type="text" id="earningPayheadAmount" name="associateSalaryPayheadIndEarningList[${loop1.index}].monthly" data-validation="validate_Space validate_length length1-20 validate_number" data-validation-optional="true" value="${earning.monthly}" class="formControl" readOnly onBlur="calculateGrossSalary();">
										<input type="text" hidden name="associateSalaryPayheadIndEarningList[${loop1.index}].payrollPayhead" value="${earning.payrollPayhead}">
										<input type="text" hidden name="associateSalaryPayheadIndEarningList[${loop1.index}].payrollPayheadShortCode" value="${earning.payrollPayheadShortCode}">
										<input type="text" hidden name="associateSalaryPayheadIndEarningList[${loop1.index}].indEmployeePayrollId" value="${earning.indEmployeePayrollId}">
										<input type="text" hidden name="associateSalaryPayheadIndEarningList[${loop1.index}].monthlyOld" value="${earning.monthly}">
										<input type="text" hidden name="associateSalaryPayheadIndEarningList[${loop1.index}].taxStatus" value="${earning.taxStatus}">
										<input type="text" hidden name="associateSalaryPayheadIndEarningList[${loop1.index}].payrollPayheadType" value="Earnings">
										
									</td>
									<c:set var="earningTotal" value="${earningTotal + earning.monthly}"/>
								</tr>
							</c:if>
							</c:forEach>
							</tbody>
							<tfoot>
							    <tr><th><strong>Gross Salary (A)</strong></th><td ><span class="paddingLeft">&#8377;</span> <label id="earningPayheadAmountTotal"> ${earningTotal}</label></td></tr>
							</tfoot>
					</table>
				</td>
			</c:if>
			
			<c:if test="${fn:length(earningPayhead) gt 0}">
				<td class="padding_right">
					<table class="table table-bordered tableNetpay mb0">
						<c:set var="employeeEarningTotal" value="${0.00}"/>
							<thead>
								<tr>
									<th>Employer Contribution</th>
									<th>Amount <span class="paddingLeft">(&#8377;)</span></th>
								</tr>
							</thead>
							<tbody class="earningPayheadTbady">
							<c:forEach var="earning" items="${earningPayhead}" varStatus="loop1">
							<c:if test="${earning.taxStatus eq 'NonTaxable'}">
								<tr>
									<th><div class="tbldata-dotdot inlineBlock"> <input type="text" hidden name="associateSalaryPayheadIndEarningList[${loop1.index}].payrollPayheadName" value="${earning.payrollPayheadName}"> ${earning.payrollPayheadName}</div></th>
									<td><input type="text" id="employerEarningPayheadAmount" name="associateSalaryPayheadIndEarningList[${loop1.index}].monthly" data-validation="validate_Space validate_length length1-20 validate_number" data-validation-optional="true" value="${earning.monthly}" class="formControl" readOnly onBlur="calculateEmployerContribution();">
										<input type="text" hidden name="associateSalaryPayheadIndEarningList[${loop1.index}].payrollPayhead" value="${earning.payrollPayhead}">
										<input type="text" hidden name="associateSalaryPayheadIndEarningList[${loop1.index}].payrollPayheadShortCode" value="${earning.payrollPayheadShortCode}">
										<input type="text" hidden name="associateSalaryPayheadIndEarningList[${loop1.index}].indEmployeePayrollId" value="${earning.indEmployeePayrollId}">
										<input type="text" hidden name="associateSalaryPayheadIndEarningList[${loop1.index}].monthlyOld" value="${earning.monthly}">
										 <input type="text" hidden name="associateSalaryPayheadIndEarningList[${loop1.index}].taxStatus" value="${earning.taxStatus}">
										<input type="text" hidden name="associateSalaryPayheadIndEarningList[${loop1.index}].payrollPayheadType" value="Earnings">
									   
										
									</td>
									<c:set var="employeeEarningTotal" value="${employeeEarningTotal + earning.monthly}"/>
								</tr>
							</c:if>
							</c:forEach>
							</tbody>
							<tfoot>
							    <tr><th><strong>Total Contribution  (B) </strong></th><td ><span class="paddingLeft">&#8377;</span> <label id="employerEarningPayheadAmountTotal"> ${employeeEarningTotal}</label></td></tr>
							    <tr><th><strong>Cost to Company (CTC)A+B</strong></th><td ><span class="paddingLeft">&#8377;</span> <label id="totalGrossPay"> ${indPayrollDTO.grossPay}</label></td></tr>
							</tfoot>
					</table>
				</td>
			</c:if>
			
			<c:if test="${fn:length(decuctionPayhead) gt 0}">
				<td  class="padding_left">		
					<table class="table table-bordered tableNetpay mb0">
						<c:set var="decuctionTotal" value="${0.00}"/>
							<thead>
							<tr>
								<th>Deductions</th>
								<th>Amount <span class="paddingLeft">(&#8377;)</span></th>
							</tr>
						</thead>
							<tbody class="deductionPayheadTbady">
							<c:forEach var="decuction" items="${decuctionPayhead}" varStatus="loop2">
								<tr>
									<th><div class="tbldata-dotdot inlineBlock"> <input type="text" hidden name="associateSalaryPayheadIndDeductionList[${loop2.index}].payrollPayheadName" value="${decuction.payrollPayheadName}"> ${decuction.payrollPayheadName}</div></th>
									<td><input type="text" id="deductionPayheadAmount" name="associateSalaryPayheadIndDeductionList[${loop2.index}].monthly" data-validation="validate_Space validate_length length1-20 validate_number" data-validation-optional="true" value="${decuction.monthly}" class="formControl" readOnly onBlur="calculateNetSalary();">
										<input type="text" hidden name="associateSalaryPayheadIndDeductionList[${loop2.index}].payrollPayhead" value="${decuction.payrollPayhead}">
										<input type="text" hidden name="associateSalaryPayheadIndDeductionList[${loop2.index}].payrollPayheadShortCode" value="${decuction.payrollPayheadShortCode}">
										<input type="text" hidden name="associateSalaryPayheadIndDeductionList[${loop2.index}].indEmployeePayrollId" value="${decuction.indEmployeePayrollId}">
										<input type="text" hidden name="associateSalaryPayheadIndDeductionList[${loop2.index}].monthlyOld" value="${decuction.monthly}">
										<input type="text" hidden name="associateSalaryPayheadIndDeductionList[${loop2.index}].payrollPayheadType" value="Deduction">
									</td>
									<c:set var="decuctionTotal" value="${decuctionTotal + decuction.monthly}"/>
								</tr>
							</c:forEach>
							<c:if test="${!empty indPayrollDTO.lopAmount && indPayrollDTO.lopAmount != '' && indPayrollDTO.lopAmount != '0' && indPayrollDTO.lopAmount != '0.0' && indPayrollDTO.lopAmount != '0.00'}">
								<tr>
									<th><div class="tbldata-dotdot inlineBlock">Loss of Pay</div></th>
									<td><input type="text" id="lopAmount" name="lopAmount" data-validation="validate_Space validate_float validate_length length1-8" data-validation-optional="true" value="${indPayrollDTO.lopAmount}" class="formControl" readOnly onBlur="calculateNetSalary();"> </td>
								</tr>
								<c:set var="decuctionTotal" value="${decuctionTotal + indPayrollDTO.lopAmount}"/>
							</c:if>
							</tbody>
							<tfoot>
							<tr><th><strong>Total Deductions (C)</strong></th><td ><span class="paddingLeft">&#8377;</span> <label id="deductionPayheadAmountTotal"> ${decuctionTotal}</label></td></tr>
						</tfoot>
					</table>
				</td>
			</c:if>
			</tr>
				</tbody>
			</table>
			</form:form>
			<table class="table" style="width:100%">
				<tbody>
				<tr>
				<%-- <td class="padding_right">
					<table class="table table-bordered tableNetpay mb0">
					<tbody>
						<th>Gross Salary <strong>(A+B)</strong>  </th>
						<td ><span class="paddingLeft">&#8377;</span> <label id="totalGrossPay"> ${indPayrollDTO.grossPay}</label></td>
					</tbody>
					</table>
				</td> --%>
				<td class="padding_right">
					<table class="table table-bordered tableNetpay mb0">
					<tbody>
						<th>Net Salary <strong>(A-C)</strong></th>
						<td ><span class="paddingLeft">&#8377;</span><label id="totalNetPay"> ${earningTotal - decuctionTotal} </label></td>
					</tbody>
					</table>
				</td>
				</tr>
				</tbody>
			</table>
			
		</div>
	</div>