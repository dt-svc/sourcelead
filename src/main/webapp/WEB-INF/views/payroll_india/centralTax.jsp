<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	var activeTaxTab = "${activeTaxTab}";
</script>
<script type="text/javascript" src='/resources/js/tax/tax.js'></script>
<section class="content-wraper contentWraper">
	<jsp:include page="../payroll_india/taxHeader.jsp"></jsp:include>
	<div class="timesheet_out">

		<div class="tablelist-wrap">
			<div class="clearfix">
				<div class="col-md-2 dropdown-select mt10 mb10">
					<select class="form-control" id="centralTaxYearList" onchange="tax.getCentralTaxList()">
						<c:forEach items="${yearList}" var="year">
							<option ${year.value==true?"selected=selected":""} value="${year.key}">${year.key}</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-md-3 dropdown-select mt10 mb10 hidden">
					<select class="selectpicker width100percnt">
					</select>
				</div>
				<div class="col-md-10">
					<a href="create" class=" mt5 pull-right btn btn-success btnSuccess anchorWhite">+ Create</a>
				</div>
				<div class="clearfix"></div>
			</div>
			
			
				<div class="styleTables">
				<table id="centralListTable">
					<thead>
						<tr>
							<th>Type of Tax</th>
							<th>Action</th>
						</tr>
					</thead>
					<tbody id="centralList">
					</tbody>
				</table>
				<table id="noResultsTable" style="display: none;">
					<tr class='odd'>
						<td colspan='7' class='dataTables_empty' valign='top'></td>
					</tr>
				</table>
			</div>
			
			
			
		<!-- 	<div class="styleTables">
				<tableid="centralListTable">
					<thead>
						<tr>
							<th>Type of Tax</th>
							<th>Action</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>
								<div>
									<a href="taxSummary" title="Incometax">Income Tax</a>
								</div>
							</td>
							<td>
								<div>
									<ul class="list-unstyled list-inline mb0">
										<li><a href="sourceleadadmin-Centraltax.html"
											title="Edit" class="anchor-black"><i class="fa fa-pencil"></i></a></li>
										<li><a href="javascript:void()" title="Delete"
											class="text-danger"><i class="fa fa-trash"></i></a></li>
									</ul>
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<div>
									<a href="taxSummary" title="Incometax">Sales tax</a>
								</div>
							</td>
							<td>
								<div>
									<ul class="list-unstyled list-inline mb0">
										<li><a href="sourceleadadmin-Centraltax.html"
											title="Edit" class="anchor-black"><i class="fa fa-pencil"></i></a></li>
										<li><a href="javascript:void()" title="Delete"
											class="text-danger"><i class="fa fa-trash"></i></a></li>
									</ul>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
 -->
		</div>
	</div>
</section>