<jsp:include page="../../masterHeader.jsp" />
<jsp:include page="../../masterSideNav.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<section class="content-wraper contentWraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Forms list</h2>
		</div>
		<div class="pull-right brud-crum">Home &raquo; Forms list</div>
	</section>

	<!-- Timesheet -->

	<div class="timesheet_out">

		<div class="tablelist-wrap">
			<div class="timesheetHead minHeight40 mt minHeight40">
				<div class="col-md-2 dropdown-select mt10 mb10">
					<select class="form-control" id="stateTaxYearList"
						onchange="tax.getStateTaxList(0)">
						<c:forEach items="${yearList}" var="year">
							<option ${year.value==true?"selected=selected":""}
								value="${year.key}">${year.key}</option>
						</c:forEach>
					</select>
				</div>
				<div class="pull-right">
					<ul class="list-unstyled list-inline">
						<li>
							<div class="dynamic_search">
								<input placeholder="Search" type="text"> <i
									class="fa fa-search"></i>
							</div>
						</li>
						<li><a href="create" class="addBtn">+ Add Form</a>
						</li>
					</ul>
				</div>
			</div>
			<div class="styleTables">
				<table>
					<thead>
						<tr>
							<th>Form Name</th>
							<th>Financial Year</th>
							<th>No.of Forms</th>
							<th>Action</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><div class="tblminwidth proj_show">
									<span>IT Decl</span> <i class="fa fa-binoculars"></i>
								</div></td>
							<td><div class="tbldata-dotdot-verybig">2018</div></td>
							<td><div class="tbldata-dotdot-verybig">18</div></td>
							<td>
								<ul class="list-unstyled list-inline mb0">
									<li><a href="javascript:void()" title="View"
										class="anchor-black"><i class="fa fa-eye-slash"></i></a></li>
									<li><a href="javascript:void()" title="Edit"
										class="anchor-black"><i class="fa fa-pencil"></i></a></li>
									<li><a href="javascript:void()" title="Delete"
										class="text-danger"><i class="fa fa-trash"></i></a></li>
								</ul>

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