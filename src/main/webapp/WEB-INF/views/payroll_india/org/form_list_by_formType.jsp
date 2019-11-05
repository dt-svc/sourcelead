<jsp:include page="../../masterHeader.jsp" />
<jsp:include page="../../masterSideNav.jsp" />
<script src="/resources/js/jquery.simplePagination.js"></script>
<script type="text/javascript" src="/resources/js/tax/it_declaration.js"></script>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="success_pop">
  <i class="fa fa-check-circle"></i><span>Operation executed successfully...</span>
</div>
<script type="text/javascript">
       var pmsUrl ='${SourceLeadWeb_pmsUrl}';
</script>
<section class="content-wraper contentWraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>${taxForm.name} list</h2>
		</div>
		<div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo; ${taxForm.name} list</div>
	</section>

	<!-- Timesheet -->

	<div class="timesheet_out">

		<div class="tablelist-wrap">
			<div class="timesheetHead minHeight40 mt minHeight40">
				<div class="pull-right">
					<ul class="list-unstyled list-inline">
						<li>
							<div class="dynamic_search">
								<input placeholder="Search" type="text" id="formSearch"> 
								<i class="fa fa-search" onclick="getItDeclarationSubmissionList('${uuid}', '${financialYear}', 0);"></i>
							</div>
						</li>
						<li><a href="/pms/myPayroll/forms/getForm?uuid=${uuid}" class="addBtn">+ Add Form</a>
						</li>
					</ul>
				</div>
			</div>
			<div id="ResultsDiv" ${formList.size() > 0 ? "" : "style='display:none;'"}>			
				<div class="styleTables">
					<table id="form_table">
						<thead>
							<tr>
								<th>Employee Id</th>
								<th>Employee Name</th>
								<th>Year</th>
								<th>Submitted Amount</th>
								<!-- <th>Tax Amount</th> -->
								<th>Status</th>
								<th>Action</th>
							</tr>
						</thead>
						<tbody id="formList">
						</tbody>
					</table>
				</div>
				
				<div id="paginationDiv" class="clearfix">
				<div class="col-md-6">
					<div class="pull-left mt5">
						<label class="pull-left">
						<span class="pull-left mt10 mr5">Show</span>
							<div class="dropdown-select pull-left mr5 tblminwidth-50">
								<select id="pageSize" onchange="getItDeclarationSubmissionList('${uuid}', '${financialYear}', 0);">
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
				
			</div>
			<div id="emptyRecordsDiv" class="dataTables_empty" ${formList.size() > 0 ? "style='display:none;'" : ""}></div>
		</div>
	</div>
</section>

<div class='project_inn_mask'></div>
<div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>
	<p>You are choosing to delete the Tax</p>
	<div>&nbsp;&nbsp;&nbsp;</div>
	<b>
		<input id='confirmId' type='button' value='Ok' onclick='changeFormStatus()'>
		<input class='u_can_color' type='button' value='Cancel' onClick='closeChangeStatusPopup()'>
	</b>
</div>

<script type="text/javascript">
	var taxFormName = "${taxForm.name}";
	$(document).ready(function () {
		if(taxFormName === "IT Declaration") {
			getItDeclarationSubmissionList('${uuid}', '${financialYear}', 0);
		}
		
		$("#formSearch").keyup(function(e) {
			if(e.which === 13) {
				getItDeclarationSubmissionList('${uuid}', '${financialYear}', 0);
			}
		});
	})
	/* $('#form_table').DataTable(); */
</script>