<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

	<div class="row">
		<div class="col-md-6">
			<div class="row">
				<label class="col-md-4"><strong>Name</strong></label>
				<div class="clearfix dropdown-select col-md-8">
					<select>
						<option selected>Select Name</option>
						<option>Suresh Kumar</option>
						<option>Sarath Kumar</option>
						<option>Muralimanohar Kumar</option>
						<option>Sarath Kumar</option>
					</select>
				</div>
			</div>
		</div>
		<div class="col-md-2 text-center">
			<div class="row">
				<label class="col-md-4 mt5"><strong>Days</strong></label>
				<div class="clearfix col-md-8">
					<p class="form-control-static">30</p>
				</div>
			</div>
		</div>
		<div class="col-md-2 text-center">
			<div class="row">
				<label class="col-md-4 mt5"><strong>Leaves</strong></label>
				<div class="clearfix col-md-8">
					<p class="form-control-static">30</p>
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
			<table width="100%" class="table table-bordered tableNetpay"
				id="tableNetpay">
				<thead>
					<tr>
						<th>Earning</th>
						<th>Amount</th>
						<th>Deductions</th>
						<th>Amount</th>
					</tr>
				</thead>
				<tbody>
	
					<tr>
					<%-- <c:forEach var="ref" items="${associatePayrollPayhead}">
						${ref.payrollPayheadName}
					</c:forEach> --%>
						<th>Basic Salary</th>
						<td><input type="text" value="124,783.00" class="formControl"
							readOnly></td>
						<th>Provident Fund</th>
						<td><input type="text" value="1800" class="formControl"
							readOnly></td>
					</tr>
	
					<tr>
						<th>HRA</th>
						<td><input type="text" value="124,783.00" class="formControl"
							readOnly></td>
						<th>Professional Tax</th>
						<td><input type="text" value="1800" class="formControl"
							readOnly></td>
					</tr>
	
					<tr>
						<th>Total</th>
						<td>124,783.00</td>
						<th>Total</th>
						<td>1800</td>
					</tr>
					<tr>
						<th>Net Amount</th>
						<td>124,783.00</td>
						<td colspan="2">&nbsp;</td>
					</tr>
					<tr>
						<th colspan="4">Rs. One Lac Ninety Founr Thousand Six Hundeed
							Sixty Six Only</th>
					</tr>
				</tbody>
				<tfoot>
					<tr>
						<th colspan="3">Gross Salary</th>
						<td>247,766.00</td>
					</tr>
					<tr>
						<th colspan="3">Net Salary Salary</th>
						<td>247,766.00</td>
					</tr>
					<tr>
						<th colspan="3">For HRA Exempt</th>
						<td>247,766.00</td>
					</tr>
				</tfoot>
			</table>
		</div>
	</div>