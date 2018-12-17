<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<section class="content-wraper contentWraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Central Tax</h2>
		</div>
	<div class="pull-right brud-crum">
		<a href="/app/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo; <a href="${appPath}/centralTax/list">Centraltax List</a> &raquo;  Central Tax Summary
	</div>
	</section>

	<div class="clearfix"></div>
	<div class="timesheet_out">

		<div class="tablelist-wrap">
			<div class="clerfix viewlabel-b">
				<div class="minHeight_225 modal-body">
					<div class="viewgrid-header pull-right zIndex">
						<a href="create?centraltaxuuid=${centralTax.centraltaxuuid}"
							class="btn btn-default btn-sm" title="Edit"><i
							class="fa fa-pencil paddingRight"></i>Edit</a>
					</div>
					<div class="row">
						<div class="col-md-12">
							<h4>Details</h4>
						</div>
						<div class="clearfix">
							<div class="col-md-5 mb">
								<div class="row">
									<label class="col-md-4 control-label"><b>Type of
											Tax:</b></label>
									<div class="col-md-8">
										<span>${centralTax.sourceleadTaxType}</span>
									</div>
								</div>
							</div>
							<div class="clearfix"></div>
							<h4 class="col-md-10">Provident Fund</h4>
							<div class="clearfix"></div>
							<div class="col-md-5 mb">
								<div class="row">
									<label class="col-md-4 control-label"><b>Ceil Value:</b></label>
									<div class="col-md-8">
										<span>${centralTax.ceilvaluepf}</span>
									</div>
								</div>
							</div>
							<div class="col-md-5 mb">
								<div class="row">
									<label class="col-md-4 control-label"><b>No. of
											Employees:</b></label>
									<div class="col-md-8">
										<span>${centralTax.noofemployees}</span>
									</div>
								</div>
							</div>
							<div class="col-md-5 mb">
								<div class="row">
									<label class="col-md-4 control-label"><b>Employee:</b></label>
									<div class="col-md-8">
									<span>${centralTax.employeepf}</span>
									</div>
								</div>
							</div>
							<div class="col-md-5 mb">
								<div class="row">
									<label class="col-md-4 control-label"><b>Employer:</b></label>
									<div class="col-md-8">
										<span>${centralTax.employerpf}</span>
									</div>
								</div>
							</div>

							<div class="clearfix"></div>
							<h4 class="col-md-10">ESI</h4>
							<div class="clearfix"></div>



							<div class="col-md-5 mb">
								<div class="row">
									<label class="col-md-4 control-label"><b>ESI Ceil
											Value:</b></label>
									<div class="col-md-8">
									<span>${centralTax.ceilvalueEsi}</span>
									</div>
								</div>
							</div>
							<div class="col-md-5 mb">
								<div class="row">
									<label class="col-md-4 control-label"><b>Employee:</b></label>
									<div class="col-md-8">
									<span>${centralTax.employeeEsi}</span>
									</div>
								</div>
							</div>
							<div class="col-md-5 mb">
								<div class="row">
									<label class="col-md-4 control-label"><b>Employer:</b></label>
									<div class="col-md-8">
								<span>${centralTax.employerEsi}</span>
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
							<th>Age</th>
							<th>From</th>
							<th>To</th>
							<th>Percentage</th>
							<th>Education Ceiss</th>
							<th>Surcharge</th>
						</tr>
					</thead>
					<tbody>
						<tr>
						<c:forEach items="${centralTax.tdsTaxList}" var="tds">
							<tr>
								<td><div class="tblminwidth-175">${tds.age}</div></td>
								<td><div class="tblminwidth-175">${tds.fromamount}</div></td>
								<td><div class="tblminwidth-175">${tds.toamount}</div></td>
								<td><div class="tblminwidth-175">${tds.percentage}</div></td>
								<td><div class="tblminwidth-175">${tds.educationceiss}</div></td>
								<td><div class="tblminwidth-175">${tds.surcharge}</div></td>
							</tr>
						</c:forEach>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</section>