<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="/resources/js/jquery.simplePagination.js"></script>
<script type="text/javascript">
	var activeTaxTab = "${activeTaxTab}";
	var viewPage = "${viewPage}";
</script>
<script type="text/javascript" src='/resources/js/tax/tax.js'></script>
<section class="content-wraper contentWraper">
	<jsp:include page="../payroll_india/taxHeader.jsp"></jsp:include>
	<div class="timesheet_out">

		<div class="tablelist-wrap">
			<div class="timesheet_head">

				<div class="col-md-2 dropdown-select mt10 mb10">
					<select class="form-control" id="formTaxYearList"
						onchange="">
						<c:forEach items="${yearList}" var="year">
							<option ${year.value==true?"selected=selected":""}
								value="${year.key}">${year.key}</option>
						</c:forEach>
					</select>
				</div>

				<div class="col-md-2 col-md-offset-8 mt5">
					<a href="create"
						class=" mt5 pull-right btn btn-success btnSuccess anchorWhite">
						+ Configure Forms</a>
				</div>

				<div class="clearfix"></div>
			</div>
			<div class="styleTables">
				<table id="formListTable">
					<thead>
						<tr>
							<th>Form Name</th>
							<th>Under Section</th>
							<th>Actions</th>
						</tr>
					</thead>
					<tbody id="formList">
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
</section>