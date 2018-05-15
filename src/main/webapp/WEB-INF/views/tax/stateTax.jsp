<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	var activeTaxTab = "${activeTaxTab}";
</script>
<script type="text/javascript" src='/resources/js/tax/tax.js'></script>
<section class="content-wraper contentWraper">
	<jsp:include page="../tax/taxHeader.jsp"></jsp:include>
	<div class="timesheet_out">

		<div class="tablelist-wrap">
			<div class="timesheet_head">

				<div class="col-md-2 dropdown-select mt10 mb10">
					<select class="form-control" id="stateTaxYearList"
						onchange="tax.getStateTaxList()">
						<c:forEach items="${yearList}" var="year">
							<option ${year.value==true?"selected=selected":""} value="${year.key}">${year.key}</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-md-3 dropdown-select mt10 mb10">
					<select class="form-control" id="stateTaxStateList"
						onchange="tax.getStateTaxList()">
						<option selected value="0">Select State</option>
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
							<th>States</th>
							<th>Type of Tax</th>
							<th>Action</th>
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