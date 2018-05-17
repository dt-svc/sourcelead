<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<section class="content-wraper contentWraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>State Tax</h2>
		</div>
		<div class="pull-right brud-crum">Home &raquo; State Tax</div>
	</section>

	<div class="clearfix"></div>
	<div class="timesheet_out">

		<div class="tablelist-wrap">
			<div class="clerfix viewlabel-b">
				<div class="modal-body">

					<div class="viewgrid-header pull-right zIndex">
						<a href="create?stateTaxId=${stateTax.id}" class="btn btn-default btn-sm" title="Edit"><i
							class="fa fa-pencil paddingRight"></i>Edit</a>
					</div>


					<div class="row">
						<div class="col-md-12">
							<h4>Details</h4>
						</div>
						<div class="clearfix">
							<div class="col-md-5 mb">
								<div class="row">
									<label class="col-md-4 control-label"><b>Year:</b></label>
									<div class="col-md-8">
										<span>${stateTax.financialYear}</span>
									</div>
								</div>
							</div>

							<div class="col-md-5 mb">
								<div class="row">
									<label class="col-md-4 control-label"><b>State:</b></label>
									<div class="col-md-8">
										<span>${stateTax.stateName}</span>
									</div>
								</div>
							</div>
							<div class="col-md-5 mb">
								<div class="row">
									<label class="col-md-4 control-label"><b>Type of
											Tax:</b></label>
									<div class="col-md-8">
										<span>${stateTax.taxType}</span>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="clearfix"></div>
			<div class="styleTables">
				<table>
					<thead>
						<tr>
							<th>Amount From</th>
							<th>Amount To</th>
							<th>Amount</th>
							<th>Month</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${stateTax.professionalTaxList}" var="pt">
							<tr>
								<td><div class="tblminwidth-175">${pt.amountFrom}</div></td>
								<td><div class="tblminwidth-175">${pt.amountTo}</div></td>
								<td><div class="tblminwidth-175">${pt.amount}</div></td>
								<td><div class="tblminwidth-175">${pt.monthApplicable}</div></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
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