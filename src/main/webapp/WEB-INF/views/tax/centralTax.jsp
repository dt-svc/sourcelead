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
			<div class="clearfix">
				<div class="col-md-2 dropdown-select mt10 mb10">
					<select class="form-control">
						<c:forEach items="${yearList}" var="year">
							<option ${year.value==true?"selected=selected":""} value="${year.key}">${year.key}</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-md-3 dropdown-select mt10 mb10 hidden">
					<select class="selectpicker width100percnt"
						title="Select Type of Tax" data-live-search="true">
						<option>Income tax</option>
						<option>Sales tax</option>
						<option>Excise duty</option>
						<option>Service tax</option>
						<option>Custom duty</option>
					</select>
				</div>
				<div class="col-md-10">
					<a href="create"
						class=" mt5 pull-right btn btn-success btnSuccess anchorWhite">
						+ Create</a>
				</div>
				<div class="clearfix"></div>
			</div>
			<div class="styleTables">
				<table>
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