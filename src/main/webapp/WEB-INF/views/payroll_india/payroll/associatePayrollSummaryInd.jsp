<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

	<div class="row">
		<div class="col-md-6">
			<div class="row">
				<label class="col-md-4"><strong>Name</strong></label>
				<div class="clearfix dropdown-select col-md-8">
					<select disabled>
						<c:forEach var="emp" items="${indEmployeePayrollList}">
							<option  ${empPayrollUUID == emp.indEmpPayrollUUID ? 'selected' : ''} >${emp.employee.user.fullName}</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</div>
		<div class="col-md-2 text-center">
			<div class="row">
				<label class="col-md-4 mt5"><strong>Days</strong></label>
				<div class="clearfix col-md-8">
					<p class="form-control-static"><fmt:formatNumber>${indEmployeePayroll.noOfDays}</fmt:formatNumber></p>
				</div>
			</div>
		</div>
		<div class="col-md-2 text-center">
			<div class="row">
				<label class="col-md-4 mt5"><strong>Leaves</strong></label>
				<div class="clearfix col-md-8">
					<p class="form-control-static"><fmt:formatNumber>${indEmployeePayroll.noOfLeaves}</fmt:formatNumber></p>
				</div>
			</div>
		</div>
		<div class="col-md-2 text-center">
			<div class="pull-right">
				<button type="button" class="btn btn-success btnSuccess editNetpay"
					onClick="editNetpay();">Edit</button>
				<button type="button" class="btn btn-success btnSuccess saveNetpay"
					onClick="saveNetpay();" style="display: none;">Save</button>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12 mt">
			<table width="100%" class="table mb0" id="tableNetpay">
				<tbody>
			<tr>
			<td class="padding_right">
				<table class="table table-bordered tableNetpay mb0">
					<c:set var="earningTotal" value="${0.00}"/>
						<thead>
							<tr>
								<th>Earning</th>
								<th>Amount</th>
							</tr>
						</thead>
						<tbody>
						<c:forEach var="earning" items="${earningPayhead}">
							<tr>
								<th><div class="tbldata-dotdot inlineBlock">${earning.payrollPayheadName}</div></th>
								<td><input type="text" value="${earning.monthly}" class="formControl" readOnly></td>
								<c:set var="earningTotal" value="${earningTotal + earning.monthly}"/>
							</tr>
						</c:forEach>
						</tbody>
						<tfoot>
							<tr><th><strong>Total</strong></th><td>${earningTotal}</td></tr>
						</tfoot>
				</table>
			</td>
			<td  class="padding_left">		
				<table class="table table-bordered tableNetpay mb0">
					<c:set var="decuctionTotal" value="${0.00}"/>
						<thead>
						<tr>
							<th>Deductions</th>
							<th>Amount</th>
						</tr>
					</thead>
						<tbody>
						<c:forEach var="decuction" items="${decuctionPayhead}">
							<tr>
								<th><div class="tbldata-dotdot inlineBlock">${decuction.payrollPayheadName}</div></th>
								<td><input type="text" value="${decuction.monthly}" class="formControl" readOnly></td>
								<c:set var="decuctionTotal" value="${decuctionTotal + decuction.monthly}"/>
							</tr>
						</c:forEach>
						</tbody>
						<tfoot>
						<tr><th><strong>Total</strong></th><td>${decuctionTotal}</td></tr>
					</tfoot>
				</table>
			</td></tr>
				</tbody>
			</table>
			
			<table class="table" style="width:100%">
				<tbody>
				<tr>
				<td class="padding_right">
					<table class="table table-bordered tableNetpay mb0">
					<tbody>
						<th>Gross Salary</th>
						<td>${indEmployeePayroll.grossPay}</td>
					</tbody>
					</table>
				</td>
				<td class="padding_right">
					<table class="table table-bordered tableNetpay mb0">
					<tbody>
						<th>Net Salary</th>
						<td>${indEmployeePayroll.netPay}</td>
					</tbody>
					</table>
				</td>
				</tr>
				</tbody>
			</table>
			
		</div>
	</div>