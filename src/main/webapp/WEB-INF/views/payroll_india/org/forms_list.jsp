<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<div class="timesheet_out">

		<div class="tablelist-wrap">
			<div class="timesheetHead minHeight40 mt minHeight40">
				<div class="col-md-2 dropdown-select mt10 mb10">
					<select class="form-control" id="yearList">
						<c:forEach items="${yearList}" var="year">
							<option ${year.value==true?"selected=selected":""}
								value="${year.key}">${year.key}</option>
								<c:if test="${year.value==true}">
									<c:set var="selectedYear" value="${year.key}"></c:set>
								</c:if>
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
						<li><a href="forms/create" class="addBtn">+ Add Form</a></li>
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
							<!-- <th>Action</th> -->
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${formList}" var="form">
							<tr>
								<td><div class="tblminwidth proj_show">
										<span>${form.name}</span> <i class="fa fa-binoculars"
											onclick="location.href='forms/list?uuid=${form.uuid}&financialYear=${selectedYear}'"></i>
									</div></td>
								<td><div class="tbldata-dotdot-verybig">${form.financialYear}</div></td>
								<td><div class="tbldata-dotdot-verybig">${form.totalFormsCount}</div></td>
								<!-- <td>
								<ul class="list-unstyled list-inline mb0">
									<li><a href="javascript:void()" title="View"
										class="anchor-black"><i class="fa fa-eye-slash"></i></a></li>
									<li><a href="javascript:void()" title="Edit"
										class="anchor-black"><i class="fa fa-pencil"></i></a></li>
									<li><a href="javascript:void()" title="Delete"
										class="text-danger"><i class="fa fa-trash"></i></a></li>
								</ul>

							</td> -->
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
<script type="text/javascript">
	$("#yearList").change(function() {
		/* location.href = 'list?financialYear='+$(this).val(); */
		formsDiv($(this).val());
	});
</script>