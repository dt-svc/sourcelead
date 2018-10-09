<jsp:include page="../../masterHeader.jsp" />
<jsp:include page="../../masterSideNav.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<section class="content-wraper contentWraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Payroll List</h2>
		</div>
		<div class="pull-right brud-crum">Home &raquo; Payroll List</div>
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
							<th>Pay run Id</th>
							<th>Group Name</th>
							<th>Pay Date</th>
							<th>Type</th>
							<th>Pay Period</th>
							<th>Total</th>
							<th>Status</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><div class="tblminwidth-150">01</div></td>
							<td><div class="tbldata-dotdot-vsm">
									<a href="payrollListSummary">Weekly</a>
								</div>
							</td>
							<td><div class="tblminwidth-150">01/02/2018</div></td>
							<td><div class="tblminwidth-150 clearfix">Regular</div></td>
							<td><span class="tblminwidth-100 mr"> 25/12/2017 </span> <span
								class="tblminwidth-100"> 25/02/2018 </span></td>
							<td><div class="tblminwidth-150 clearfix">40,000 Rs</div></td>
							<td><div class="tblminwidth-150">DRAFTED</div></td>
						</tr>
						<tr>
							<td><div class="tblminwidth-150">02</div></td>
							<td><div class="tbldata-dotdot-vsm">
									<a href="payrollListSummary">Monthly</a>
								</div></td>
							<td><div class="tblminwidth-150">01/02/2018</div></td>
							<td><div class="tblminwidth-150 clearfix">Regular</div></td>
							<td><span class="tblminwidth-100 mr"> 25/12/2017 </span> <span
								class="tblminwidth-100"> 25/02/2018 </span></td>
							<td><div class="tblminwidth-150 clearfix">60,000 Rs</div></td>
							<td><div class="tblminwidth-150">APPROVED</div></td>
						</tr>
						<tr>
							<td><div class="tblminwidth-150">03</div></td>
							<td><div class="tbldata-dotdot-vsm">
									<a href="payrollListSummary">Weekly</a>
								</div>
							</td>
							<td><div class="tblminwidth-150">01/02/2018</div></td>
							<td><div class="tblminwidth-150 clearfix">Regular</div></td>
							<td><span class="tblminwidth-100 mr"> 25/12/2017 </span> <span
								class="tblminwidth-100"> 25/02/2018 </span></td>
							<td><div class="tblminwidth-150 clearfix">40,000 Rs</div></td>
							<td><div class="tblminwidth-150">SUBMITTED FOR APPROVAL</div></td>
						</tr>
						<tr>
							<td><div class="tblminwidth-150">04</div></td>
							<td><div class="tbldata-dotdot-vsm">
									<a href="payrollListSummary">Weekly</a>
								</div>
							</td>
							<td><div class="tblminwidth-150">01/02/2018</div></td>
							<td><div class="tblminwidth-150 clearfix">Regular</div></td>
							<td><span class="tblminwidth-100 mr"> 25/12/2017 </span> <span
								class="tblminwidth-100"> 25/02/2018 </span></td>
							<td><div class="tblminwidth-150 clearfix">40,000 Rs</div></td>
							<td><div class="tblminwidth-150">REJECTED</div></td>
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
<script type="text/javascript">
	$(function() {
		$("#payDay").datepicker();
	})
</script>