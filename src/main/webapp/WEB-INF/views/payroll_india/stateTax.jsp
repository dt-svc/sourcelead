<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="/resources/js/jquery.simplePagination.js"></script>
<script type="text/javascript">
	var activeTaxTab = "${activeTaxTab}";
</script>
<script type="text/javascript" src='/resources/js/tax/tax.js'></script>
<section class="content-wraper contentWraper">
	<jsp:include page="../payroll_india/taxHeader.jsp"></jsp:include>
	<div class="timesheet_out">

		<div class="tablelist-wrap">
			<div class="timesheet_head">

				<div class="col-md-2 dropdown-select mt10 mb10">
					<select class="form-control" id="stateTaxYearList"
						onchange="tax.getStateTaxList(0)">
						<c:forEach items="${yearList}" var="year">
							<option ${year.value==true?"selected=selected":""}
								value="${year.key}">${year.key}</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-md-3 dropdown-select mt10 mb10">
					<select class="form-control" id="stateTaxStateList"
						onchange="tax.getStateTaxList(0)">
						<option selected value="0">All</option>
						<c:forEach items="${stateList}" var="s">
							<option value="${s.id}">${s.stateName}</option>
						</c:forEach>
					</select>
				</div>

				<div class="col-md-7">
					<a href="create"
						class=" mt5 pull-right btn btn-success btnSuccess anchorWhite">
						+ Create</a>
				</div>

				<div class="clearfix"></div>
			</div>
			<div class="styleTables">
				<table id="stateListTable">
					<thead>
						<tr>
							<th>State</th>
							<th>Type of Tax</th>
							<th>Actions</th>
						</tr>
					</thead>
					<tbody id="stateList">
					</tbody>
				</table>
				<table id="noResultsTable">
					<tr class='odd'>
						<td colspan='7' class='dataTables_empty' valign='top'></td>
					</tr>
				</table>
			</div>
		</div>
	</div>

	<div id="paginationDiv" class="clearfix">
		<div class="col-md-6">
			<div class="pull-left mt5">
				<label class="pull-left">
				<span class="pull-left mt10 mr5">Show</span>
					<div class="dropdown-select pull-left mr5 tblminwidth-50">
						<select id="pageSize" onchange="tax.getStateTaxList(0)">
							<option value="5">5</option>
							<option value="10">10</option>
							<option value="15">15</option>
							<option value="20">20</option>
						</select>
					</div>
				<span class="pull-left mt10">entries</span>
				</label>
				<span class="pull-left mt10 ml" id="pageMsg"></span>
			</div>
		</div>
		<div id="paginationholder" class="col-md-6 pagiPad">
			<ul id="pagination-demo"
				class="pagination-sm pull-right pagination light-theme simple-pagination">
				<li class="disabled"><span class="current prev">Prev</span></li>
				<li class="active"><span class="current">1</span></li>
				<li class="disabled"><span class="current next">Next</span></li>
			</ul>
		</div>
	</div>
</section>