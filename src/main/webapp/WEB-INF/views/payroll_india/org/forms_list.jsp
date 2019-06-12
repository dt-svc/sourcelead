<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<div class="timesheet_out">

		<div class="tablelist-wrap">
			<div class="timesheetHead minHeight40 mt minHeight40">
				<div class="col-md-2 dropdown-select mb10">
					<select class="sel" id="yearList">
						<c:forEach items="${yearList}" var="year">
							<option ${year.value==true?"selected=selected":""}
								value="${year.key}">${year.key}</option>
								<c:if test="${year.value==true}">
									<c:set var="selectedYear" value="${year.key}"></c:set>
								</c:if>
						</c:forEach>
					</select>
				</div>
				<div class="pull-right mr10">
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
					<tbody id="formsTabl">
						<c:forEach items="${formList}" var="form">
							<tr>
								<td>
								
								<div class="width300px">	
									<a class="tblminwidth proj_show" onclick="location.href='forms/list?uuid=${form.uuid}&financialYear=${selectedYear}'">
										<div class="tbldata-dotdot-200 inlineBlock">${form.name}</div> <i class="fa fa-binoculars"></i>
									</a>
								</div>
								</td>
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
		/* var pmsUrl ='${SourceLeadWeb_pmsUrl}';
		$.ajax({
		 type : "POST",
		 url : pmsUrl+"/getFormsByFinancialYear?financialYear="+$(this).val(),
		 success : function(result) {
			 	if (result.response === "SESSION_TIMEDOUT") {
	  				location.replace(pmsUrl);
	            } else if (result.response === "AWKWARD") {
	                location.href = "/errorPage";
	            } else if (result.response === "SUCCESS") {
  		  		  var opt = "";
	  		  		for (var i = 0; i < result.data.length; i++) {
	  		  			var res = result.data[i];
		  		  		opt = opt + '<tr>';
		  		  		opt = opt + '<td><div class="tblminwidth proj_show">';
		  		 		opt = opt + '		<span>'+${form.name}+'</span> <i class="fa fa-binoculars"';
		  				opt = opt + '			onclick="location.href="forms/list?uuid='+${res.uuid}+'&financialYear='+${selectedYear}+'""></i>';
		  				opt = opt + '		</div></td>';
		  				opt = opt + '		<td><div class="tbldata-dotdot-verybig">'+${res.financialYear}+'</div></td>';
		  				opt = opt + '		<td><div class="tbldata-dotdot-verybig">'+${res.totalFormsCount}+'</div></td> ';
		  				opt = opt + '	</tr>';
	  		  		}
	  		  	  $("#formsTabl").empty();
		  		  $("#formsTabl").append(result.data);
	            } else if (result.indexOf('Sign In') !== -1) {
					location.replace(pmsUrl);
				} else {
	                showError("Operation failed, please try again");
	            }
		  	},
		}); */
		formsDiv($(this).val());
	});
</script>

