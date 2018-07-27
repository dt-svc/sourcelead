<jsp:include page="../../masterHeader.jsp" />
<jsp:include page="../../masterSideNav.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<section class="content-wraper contentWraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>${taxForm.name} list</h2>
		</div>
		<div class="pull-right brud-crum">Home &raquo; ${taxForm.name} list</div>
	</section>

	<!-- Timesheet -->

	<div class="timesheet_out">

		<div class="tablelist-wrap">
			<div class="timesheetHead minHeight40 mt minHeight40">
				<div class="pull-right">
					<ul class="list-unstyled list-inline">
						<li>
							<div class="dynamic_search">
								<input placeholder="Search" type="text"> <i
									class="fa fa-search"></i>
							</div>
						</li>
						<li><a href="getForm?uuid=${uuid}" class="addBtn">+ Add Form</a>
						</li>
					</ul>
				</div>
			</div>
			<div id="ResultsDiv" ${formList.size() > 0 ? "" : "style='display:none;'"}>			
				<div class="styleTables">
					<table>
						<thead>
							<tr>
								<th>Payslip Name</th>
								<th>Year</th>
								<th>Status</th>
								<th>Action</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${formList}" var="form">
								<tr>
									<td>
										<div class="tblminwidth proj_show">
											<span>${form.userName}</span> <i class="fa fa-binoculars"></i>
										</div>
									</td>
									<td><div class="tbldata-dotdot-verybig">${form.financialYear}</div></td>
									<td><div>${form.statusText}</div></td>
									<td>
										<ul class="list-unstyled list-inline mb0">
											<li><a href="viewForm?uuid=${uuid}&formUUID=${form.uuid}" title="View"
												class="anchor-black"><i class="fa fa-eye-slash"></i></a></li>
											<li><a href="editForm?uuid=${uuid}&formUUID=${form.uuid}" title="Edit"
												class="anchor-black"><i class="fa fa-pencil"></i></a></li>
											<li><a href="javascript:void()" title="Delete"
												class="text-danger"><i class="fa fa-trash"></i></a></li>
										</ul>
	
									</td>
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
			<div id="emptyRecordsDiv" class="dataTables_empty" ${formList.size() > 0 ? "style='display:none;'" : ""}></div>
		</div>
	</div>
</section>