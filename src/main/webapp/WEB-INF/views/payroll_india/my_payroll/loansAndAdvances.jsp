<jsp:include page="../../masterHeader.jsp" />
<jsp:include page="../../masterSideNav.jsp" />
<section class="content-wraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>My ${activeTaxTab == 'pays' ? 'Pays' : activeTaxTab =='forms' ? 'Forms' : 'Loans & Advances'}</h2>
		</div>
		<div class="pull-right brud-crum">Home &raquo; My Forms</div>
	</section>
	<div class="clearfix"></div>
	<div class="project_wrap">
		<jsp:include page="../my_payroll/myPayrollHeader.jsp"></jsp:include>
		
		<div class="tablelist-wrap">
			<div class="col-md-12">
				<ul class="list-unstyled mt row">
					<li class="col-md-3" title="Outstanding" onClick="getOutstandingLoans()">
						<div class="cardModel-widget text-center cursorPointer currentActive" id="outstandingWidget">
							<h5 class="margin-top0">Outstanding</h5>
							<p class="text-muted mb0">
								<i class="fa fa-rupee paddingRight"></i><span>1000</span>
							</p>
						</div>
					</li>
					<li class="col-md-3" title="Completed" onClick="getCompletedLoans()">
						<div class="cardModel-widget text-center cursorPointer" id="completedWidget">
							<h5 class="margin-top0">Completed</h5>
							<p class="text-muted mb0">
								<i class="fa fa-rupee paddingRight"></i><span>1000</span>
							</p>
						</div>
					</li>
					<li class="col-md-3" title="Request" onClick="getRequestedLoans()">
						<div class="cardModel-widget text-center cursorPointer" id="requestWidget">
							<h5 class="margin-top0">Requested</h5>
							<p class="text-muted mb0">
								<i class="fa fa-rupee paddingRight"></i><span>1000</span>
							</p>
						</div>
					</li>
					<li class="col-md-3"><a href="javascript:void(0)"
						class="pull-right" onClick="requestLoanPop();">Request</a></li>
				</ul>
			</div>
			<div class="clearfix"></div>

			<div class="loan_and_advance_table project_tabs_inn_table clearfix" id="outStadingTable">
				<table id="outstandingList">
					<thead>
						<tr>
							<th>Name</th>
							<th>Type</th>
							<th>Total Amount</th>
							<th>Paid Amount</th>
							<th>Due Amount</th>
							<th>No. of Schedule</th>
							<th>Next Schedule</th>
							<th>Action</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><div class="tbldata-dotdot-vsm">Car</div></td>
							<td><div class="tblminwidth-50">Loan</div></td>
							<td><div class="tblminwidth-50 clearfix">5,000,000</div></td>
							<td><div class="tblminwidth-50 clearfix">3,000,000</div></td>
							<td><div class="tblminwidth-50 clearfix">2,000,000</div></td>
							<td><div class="tblminwidth-50 clearfix">10</div></td>
							<td><div class="tblminwidth-50 clearfix">11/08/2018</div></td>
							<td>
								<div class="tblminwidth-30 text-center">
									<a href="javascript:void(0)" class="anchorBlack"><i class="fa fa-eye"></i></a>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="loan_and_advance_table project_tabs_inn_table clearfix" id="completedTable" style="display: none">
				<table id="completedList">
					<thead>
						<tr>
							<th>Name</th>
							<th>Type</th>
							<th>Total Amount</th>
							<th>No. of Schedule</th>
							<th>Last Paid On</th>
							<th>Action</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><div class="tbldata-dotdot-vsm">Personal</div></td>
							<td><div class="tblminwidth-50">Loan</div></td>
							<td><div class="tblminwidth-50 clearfix">5,000,000</div></td>
							<td><div class="tblminwidth-50 clearfix">5</div></td>
							<td><div class="tblminwidth-50 clearfix">11/08/2018</div></td>
							<td>
								<div class="tblminwidth-30 text-center">
									<a href="javascript:void(0)" class="anchorBlack"><i class="fa fa-eye"></i></a>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="loan_and_advance_table project_tabs_inn_table clearfix" id="requestTable" style="display: none">
				<table id="requestedList">
					<thead>
						<tr>
							<th>Name</th>
							<th>Type</th>
							<th>Total Amount</th>
							<th>Requested To</th>
							<th>Requested On</th>
							<th>Status</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><div class="tbldata-dotdot-vsm">Gold</div></td>
							<td><div class="tblminwidth-50">Loan</div></td>
							<td><div class="tblminwidth-50 clearfix">5,000,000</div></td>
							<td><div class="tblminwidth-50 clearfix">Users</div></td>
							<td><div class="tblminwidth-50 clearfix">11/08/2018</div></td>
							<td>
								<div class="tblminwidth-30 text-center text-danger">Pending</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</section>

<div class="sideMask"></div>
<div class="modal requestPop">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" onClick="closeRequestLoanPop();">×</button>
				<h4 class="modal-title">Request</h4>
			</div>
			<div class="modal-body">
				<div class="form-group clearfix">
					<label class="col-md-3">Type</label>
					<div class="clearfix dropdown-select col-md-9">
						<select>
							<option selected="">Type</option>
							<option>Type1</option>
							<option>Type2</option>
							<option>Type3</option>
							<option>Type4</option>
						</select>
					</div>
				</div>
				<div class="form-group clearfix">
					<label class="col-md-3">Name</label>
					<div class="clearfix dropdown-select col-md-9">
						<input type="text" class="form-control" placeholder="Enter">
					</div>
				</div>
				<div class="form-group clearfix">
					<label class="col-md-3">Amount</label>
					<div class="clearfix dropdown-select col-md-9">
						<input type="text" class="form-control" placeholder="Enter">
					</div>
				</div>
				<div class="form-group clearfix">
					<label class="col-md-3">No. of Scheduels</label>
					<div class="clearfix dropdown-select col-md-9">
						<input type="text" class="form-control" placeholder="Enter">
					</div>
				</div>
				<div class="form-group clearfix">
					<label class="col-md-3">Requested To</label>
					<div class="clearfix dropdown-select col-md-9">
						<input type="text" class="form-control" placeholder="Enter">
					</div>
				</div>
				<div class="form-group clearfix">
					<label class="col-md-3">Emi6</label>
					<div class="clearfix dropdown-select col-md-9">
						<input type="text" class="form-control" placeholder="Enter">
					</div>
				</div>
				<div class="form-group clearfix">
					<label class="col-md-3">Comment</label>
					<div class="clearfix dropdown-select col-md-9">
						<textarea cols="2" class="form-control" placeholder="Enter"></textarea>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary">Request</button>
				<button type="button" class="btn btn-default"
					onClick="closeRequestLoanPop();">Close</button>
			</div>
		</div>
	</div>
</div>