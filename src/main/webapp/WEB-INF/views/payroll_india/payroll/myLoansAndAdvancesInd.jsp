<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

		
		<div class="tablelist-wrap">
			<div class="col-md-12">
				<ul class="list-unstyled mt row">
					<li class="col-md-3" title="Outstanding" onclick="getMyLoansAdv('Outstanding');">
						<div class="cardModel-widget text-center cursorPointer ${loanStatus == 'Outstanding' ? 'currentActive' : ''}" id="outstandingWidget">
							<h5 class="margin-top0">Outstanding</h5>
							<p class="text-muted mb0">
								<!-- <i class="fa fa-rupee paddingRight"></i><span>1000</span> -->
							</p>
						</div>
					</li>
					<li class="col-md-3" title="Completed" onclick="getMyLoansAdv('Completed');">
						<div class="cardModel-widget text-center cursorPointer ${loanStatus == 'Completed' ? 'currentActive' : ''}" id="completedWidget">
							<h5 class="margin-top0">Completed</h5>
							<p class="text-muted mb0">
								<!-- <i class="fa fa-rupee paddingRight"></i><span>1000</span> -->
							</p>
						</div>
					</li>
					<li class="col-md-3" title="Request" onclick="getMyLoansAdv('Requested');">
						<div class="cardModel-widget text-center cursorPointer ${loanStatus == 'Requested' ? 'currentActive' : ''}" id="requestWidget">
							<h5 class="margin-top0">Requested</h5>
							<p class="text-muted mb0">
								<!-- <i class="fa fa-rupee paddingRight"></i><span>1000</span> -->
							</p>
						</div>
					</li>
					<li class="col-md-3"><a href="javascript:void(0)"
						class="pull-right" onClick="requestLoanPop();">Request</a></li>
				</ul>
			</div>
			<div class="clearfix"></div>
			
			<div class="clearfix project_tabs_inn_table overflow_Auto">
				<table width="100%">
					<thead>
						<tr>
						  <th>Type</th>   
						  <th>Name</th>        
						  <th>Status</th>         
						  <th>Amount</th>     
						  <th>Scheduler</th>     
						  <th>No Of Schedules</th> 
						  <th>Effective Date</th>
						  <th>Deduct Amount</th>         
						  <th>Approver</th>         
						  <th>Action</th>                  
						</tr>
					</thead>
					
					<tbody>
						<c:if test="${loanAdvListSize > 0}">
						 <c:forEach var="loanAdv" items="${loanAdvList}">
							 <tr>
							   <td><div class="tblminwidth-50" title="${loanAdv.loanAdvanceType}">${loanAdv.loanAdvanceType}</div></td>
							   <td><div class="tblminwidth-50" title="${loanAdv.payrollLoanAndAdvanceId.name}">${loanAdv.payrollLoanAndAdvanceId.name}</div></td>
							   <td><div class="tblminwidth-50" title="${loanAdv.loanAndAdvanceStatus}">${loanAdv.loanAndAdvanceStatus}</div></td>
							   <td><div class="tblminwidth-50" title="${loanAdv.amount}">${SourceLeadWeb_orgLocations.country == 'United States' ? '$' : ''}   ${loanAdv.amount}</div></td>
							   <td><div class="tblminwidth-50" title="${loanAdv.scheduler}">${loanAdv.scheduler}</div></td>
							   <td><div class="tblminwidth-30" title="${loanAdv.noOfSchedules}">${loanAdv.noOfSchedules}</div></td>
							   <td><div class="tblminwidth-50" title="${loanAdv.effectiveDateStr}">${loanAdv.effectiveDateStr}</div></td>
							   <td><div class="tblminwidth-50" title="${loanAdv.deductAmount}">${loanAdv.deductAmount}</div></td>
							   <td><div class="tblminwidth-50" title="${loanAdv.approverName}">${loanAdv.approverName}</div></td>
							   <td><div class="tblminwidth-50"><a href="javascript:void(0)" onclick="editLoanAndAdvance('${loanAdv.id}')">Edit</a>
							     |&nbsp;<a href="javascript:void(0)" onclick="deleteLoanAndAdvance('${loanAdv.id}')">Delete</a>
							   </div></td>
							 </tr>
						 </c:forEach>
						</c:if>
					</tbody>
				</table>
				<c:if test="${loanAdvListSize == 0}">
					 <div class='row'> 
						 <div class='col-lg-12'> 
							 <div class='dataTables_empty'>
							 </div>
						 </div>
					 </div>
				</c:if>
			</div>

			<!-- <div class="loan_and_advance_table project_tabs_inn_table clearfix" id="outStadingTable">
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
			</div> -->
			<!-- <div class="loan_and_advance_table project_tabs_inn_table clearfix" id="completedTable" style="display: none">
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
			</div> -->
		</div>

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