<jsp:include page="../../masterHeader.jsp" />
<jsp:include page="../../masterSideNav.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<section class="content-wraper contentWraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Payroll List Summary</h2>
		</div>
		<div class="pull-right brud-crum">Home &raquo; Payroll List
			Summary</div>
	</section>
	<div class="clearfix"></div>
	<div class="timesheet_out">
		<div class="tablelist-wrap">
			<div class="timesheet_head minHeight">
				<div class="col-md-7 mt">
					<div class="row">
						<div class="col-md-3">
							<div class="dropdown-select">
								<select>
									<option selected>Pay Period</option>
									<option>Group-1</option>
									<option>Group-2</option>
									<option>Group-3</option>
								</select>
							</div>
						</div>
						<div class="col-md-3 paddingRight">
							<input name="" class="form-control calendar"
								placeholder="Pay Day" type="text" id="payDay">
						</div>
						<div class="col-md-2">
							<a href="runPayroll"
								class="btn btn-success btnSuccess btn-sm anchorWhite">Run
								Payroll</a>
						</div>
					</div>
				</div>
				<div class="clearfix"></div>
				<div class="remove_search">
					<input placeholder="Search" type="text">
				</div>
			</div>
			<div class="styleTables clearfix mt">
				<table>
					<thead>
						<tr>
							<th>Emp Name</th>
							<th>Rate</th>
							<th>Regular Hours</th>
							<th>Overtime Hours</th>
							<th>Gross Pay</th>
							<th>Pre-Tax Deduction</th>
							<th>Post-Tax Deduction</th>
							<th>Taxes</th>
							<th>Net Pay</th>
							<th>Last Pay</th>
							<th>YTD</th>
							<th>Action</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><div class="tbldata-dotdot-vsm">Laxmisaraswathi</div></td>
							<td><div class="tblminwidth-50">20$/hrs</div></td>
							<td><div class="tblminwidth-50 clearfix">40Hrs</div>
								<div class="tblminwidth-50 clearfix">56000 Rs</div></td>
							<td><div class="tblminwidth-50 clearfix">10Hrs</div>
								<div class="tblminwidth-50 clearfix">21000 Rs</div></td>
							<td><div class="tblminwidth-80 clearfix">7000 Rs</div></td>
							<td><div class="tblminwidth-80 clearfix">7000 Rs</div></td>
							<td><div class="tblminwidth-80 clearfix">3500 Rs</div></td>
							<td><div class="tblminwidth-80 clearfix">7000 Rs</div></td>
							<td><div class="tblminwidth-80 clearfix">
									<a href="javascript:void(0)" onclick="usRunpayroll();">14000
										Rs</a>
								</div></td>
							<td><div class="tblminwidth-80 clearfix">14000 Rs</div></td>
							<td><div class="tblminwidth-80 clearfix">20,0000 Rs</div></td>
							<td>
								<div class="tblminwidth-30 text-center">
									<a href="javascript:void(0)" class="anchorBlack"><i
										class="fa fa-eye"></i></a>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="clearfix"></div>
			<nav>
				<ul id="paging" class="pagination pull-right mr">
					<li><a href="#">Previous</a></li>
					<li><a href="#">2</a></li>
					<li><a href="#">3</a></li>
					<li><a href="#">4</a></li>
					<li><a href="#">5</a></li>
					<li class="disabled"><a href="#">6</a></li>
					<li data-page="last"><a href="#">Next</a></li>
				</ul>
				<div class="clearfix"></div>
			</nav>
		</div>
	</div>
</section>


<div class="sideMask"></div>
<div class="modal usRunpayroll" style="display: none;" id="usRunpayroll">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close hidden" data-dismiss="modal"
					onclick="closeusRunpayrol();">&times;</button>
				<h4 class="modal-title hidden">Pay Stub</h4>
				<div class="row">
					<div class="col-md-4">
						<label class="clearfix"><strong>Name</strong></label>
						<div class="clearfix">
							<select class="selectpicker width100percnt" title="Select Name"
								data-live-search="true">
								<option selected></option>
								<option>Suresh Kumar</option>
								<option>Sarath Kumar</option>
								<option>Muralimanohar Kumar</option>
								<option>Sarath Kumar</option>
							</select>
						</div>
					</div>
					<div class="col-md-5">
						<label class="clearfix"><strong>Address</strong></label>
						<div class="clearfix">
							<p class="mb0">Vertex Corporate 2nd Floor, Plot no: 8 &amp; 9
								Jubilee Enclave, Opp. HITEX Entrance, Madhapur, Hyderabad,
								Telangana 500081</p>
						</div>
					</div>
					<div class="col-md-2">
						<label class="clearfix"><strong>No. Leaves</strong></label>
						<div class="clearfix">
							<p class="mt5 mb0">30</p>
						</div>
					</div>
					<div class="col-md-1 text-center">
						<div class="pull-right">
							<button type="button"
								class="btn btn-success btnSuccess editUspayslip"
								onClick="editUspayslip(event)" id="editUspaysliptext">Edit</button>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-body"
				style="min-height: 150px; max-height: 425px; overflow-y: auto; padding: 5px 15px;">
				<div class="row">
					<div class="col-md-12 tablePayslip" id="tablePayslip">
						<table width="100%" class="table">
							<tbody>
								<tr>
									<td colspan="2"><table width="100%"
											class="table table-bordered">
											<thead>
												<tr>
													<th colspan="4">Hours &amp; Earnings</th>
												</tr>
											</thead>
											<tbody>
												<tr>
													<th>Description</th>
													<th>No. of Hours</th>
													<th>Earning</th>
												</tr>
												<tr>
													<td>Regular</td>
													<td><input type="text" value="124,783.00"
														class="formControl" readOnly></td>
													<td><input type="text" value="124,783.00"
														class="formControl" readOnly></td>
												</tr>
												<tr>
													<td>Overtime</td>
													<td><input type="text" value="124,783.00"
														class="formControl" readOnly></td>
													<td><input type="text" value="124,783.00"
														class="formControl" readOnly></td>
												</tr>
											</tbody>
											<tfoot>
												<tr>
													<th>Total</th>
													<td><span>124,783.00</span></td>
													<td><span>124,783.00</span></td>
												</tr>
											</tfoot>
										</table></td>
								</tr>
								<tr>
									<td><table width="100%" class="table table-bordered">
											<thead>
												<tr>
													<th colspan="2">Post Tax Deduction</th>
												</tr>
											</thead>
											<tbody>
												<tr>
													<th>Description</th>
													<th>Amount</th>
												</tr>
												<tr>
													<td>Description1</td>
													<td><input type="text" value="124,783.00"
														class="formControl" readOnly></td>
												</tr>
											</tbody>
											<tfoot>
												<tr>
													<th>Total</th>
													<td><span>124,783.00</span></td>
												</tr>
											</tfoot>
										</table></td>
									<td><table width="100%" class="table table-bordered">
											<thead>
												<tr>
													<th colspan="2">Pre-Tax Deduction</th>
												</tr>
											</thead>
											<tbody>
												<tr>
													<th>Description</th>
													<th>Amount</th>
												</tr>
												<tr>
													<td>Description</td>
													<td><input type="text" value="124,783.00"
														class="formControl" readOnly></td>
												</tr>
											</tbody>
											<tfoot>
												<tr>
													<th>Total</th>
													<td colspan="2"><span>124,783.00 </span></td>
												</tr>
											</tfoot>
										</table></td>
								</tr>
								<tr>
									<td><table width="100%" class="table table-bordered">
											<thead>
												<tr>
													<th colspan="2">Taxes</th>
												</tr>
											</thead>
											<tbody>
												<tr>
													<th>Description</th>
													<th>Amount</th>
												</tr>
												<tr>
													<td>Federal with holding</td>
													<td><input type="text" value="124,783.00"
														class="formControl" readOnly></td>
												</tr>
												<tr>
													<td>Medicare</td>
													<td><input type="text" value="124,783.00"
														class="formControl" readOnly></td>
												</tr>
												<tr>
													<td>FICA</td>
													<td><input type="text" value="124,783.00"
														class="formControl" readOnly></td>
												</tr>
												<tr>
													<td>State Withholding</td>
													<td><input type="text" value="124,783.00"
														class="formControl" readOnly></td>
												</tr>
											</tbody>
											<tfoot>
												<tr>
													<th>Total</th>
													<td><span>124,783.00</span></td>
												</tr>
											</tfoot>
										</table></td>
									<td><table width="100%" class="table table-bordered">
											<thead>
												<tr>
													<th colspan="2">Employer-Paid Benefits</th>
												</tr>
											</thead>
											<tbody>
												<tr>
													<th>Description</th>
													<th>Current</th>
												</tr>
												<tr>
													<td>Healthcare</td>
													<td><input type="text" value="124,783.00"
														class="formControl" readOnly></td>
												</tr>
												<tr>
													<td>Dental</td>
													<td><input type="text" value="124,783.00"
														class="formControl" readOnly></td>
												</tr>
												<tr>
													<td>ABC LIfe Insurance</td>
													<td><input type="text" value="124,783.00"
														class="formControl" readOnly></td>
												</tr>
											</tbody>
											<tfoot>
												<tr>
													<th>Total</th>
													<td><span>124,783.00</span></td>
												</tr>
											</tfoot>
										</table></td>
								</tr>
								<tr>
									<td colspan="2"><table class="table table-bordered">
											<thead>
												<th>&nbsp;</th>
												<th>Total Gross</th>
												<th>Fed Taxable Gross</th>
												<th>FICA Gross</th>
												<th>Total Taxes</th>
												<th>Total Deductions</th>
												<th>Net Pay</th>
											</thead>
											<tbody>
												<tr>
													<td>Current</td>
													<td>2,062.53</td>
													<td>2,062.53</td>
													<td>2,062.53</td>
													<td>2,062.53</td>
													<td>2,062.53</td>
													<td>2,062.53</td>
												</tr>
												<tr>
													<td>YTD</td>
													<td>2,062.53</td>
													<td>2,062.53</td>
													<td>2,062.53</td>
													<td>2,062.53</td>
													<td>2,062.53</td>
													<td>2,062.53</td>
												</tr>
											</tbody>
										</table></td>
								</tr>
								<tr>
									<td><table class="table table-bordered">
											<tbody>
												<tr>
													<th>Total Gross</th>
													<td>2,062.53</td>
												</tr>
												<tr>
													<th>Before-Tax Deductions</th>
													<td>2,062.53</td>
												</tr>
												<tr>
													<th>Life Insurance Premiums</th>
													<td>2,062.53</td>
												</tr>
												<tr>
													<th>Fed Taxable and FICA</th>
													<td>2,062.53</td>
												</tr>
											</tbody>
										</table></td>
									<td><table class="table table-bordered">
											<thead>
												<tr>
													<th colspan="2">Net Pay Disctibution</th>
												</tr>
											</thead>
											<tbody>
												<tr>
													<th>Advice#1006795</th>
													<td>2,062.53</td>
												</tr>
												<tr>
													<th>Total</th>
													<td>2,062.53</td>
												</tr>
											</tbody>
										</table></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default"
					onClick="closeusRunpayrol();">Close</button>
			</div>
		</div>
	</div>
</div>

<script>
	$(function() {
		$("#payDay").datepicker();
	});
	function usRunpayroll() {
		$(".usRunpayroll").fadeIn();
		$(".sideMask").fadeIn();
	}
	function closeusRunpayrol() {
		$(".usRunpayroll").fadeOut();
		$(".sideMask").fadeOut();
	};

	//Net payedit Script
	function editUspayslip(event) {
		var text = document.getElementById("editUspaysliptext");
		if (text.innerHTML === "Edit") {
			text.innerHTML = "Save"
		} else {
			text.innerHTML = "Edit"
		}
		$("#tablePayslip").find(".formControl").toggleClass("activeEdit");
		var x = document.getElementsByClassName("formControl"), i;
		for (i = 0; i < x.length; i++) {
			if (x[i].readOnly) {
				x[i].readOnly = false;
			} else {
				x[i].readOnly = true;
			}
		}
	}
</script>