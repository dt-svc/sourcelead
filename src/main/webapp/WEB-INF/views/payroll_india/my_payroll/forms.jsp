<jsp:include page="../../masterHeader.jsp" />
<jsp:include page="../../masterSideNav.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="/resources/js/jquery.simplePagination.js"></script>
<section class="content-wraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>My ${activeTaxTab == 'pays' ? 'Pays' : activeTaxTab =='forms' ? 'Forms' : 'Loans & Advances'}</h2>
		</div>
		<div class="pull-right brud-crum"><a href="/app/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo; My Forms</div>
	</section>	

	<div class="project_wrap">
		<jsp:include page="../my_payroll/myPayrollHeader.jsp"></jsp:include>	
		<div class="positionRelative reportHead clearfix">
			<ul class="pull-right list-unstyled list-inline">
				<li>
					<select class="selectpicker mr5"
						data-live-search="true" title="Select Year" id="financialYear" onchange="getFormSubmissionMasterList();">
						<c:forEach items="${yearList}" var="year">
							<option ${year.value==true?"selected=selected":""} value="${year.key}">${year.key}</option>
						</c:forEach>
					</select>		
				</li>
				<li>
					<a class="action newAdBtn mr5" onclick="getActions();" href="javascript:void(0)">+ Submit Form <i class="fa fa-caret-down"></i></a>
					<div class="action-pop">
						<span> 
							<a href="/app/itDeclaration?autoSelectUser=true">IT Declaration</a>
							<!-- <a href="javascript:void(0)">IT Challan</a>
							<a href="javascript:void(0)">Form 16</a> -->
						</span>
					</div>
				</li>
			  	<li class=""><input type="text" class="repSearchBar" placeholder='Search' id='dynamicUserDataTableSearch'/><span class="repSearchIcon" onClick="getDynamciUserList();"></span></li>
	        </ul>
		</div>			
		<div class="project_tabs_inn_table clearfix">
			<table id="submissionTable">
				<thead>
					<tr>
						<th>Assessment Year</th>
						<th>Form Type</th>
						<th>Filling Date</th>
						<th>Status</th>
						<th>Actions</th>
					</tr>
				</thead>
				<tbody id="submissionList">
				</tbody>
			</table>
			<table id="noResultsTable" style="display: none;">
				<tr class='odd'>
					<td colspan='7' class='dataTables_empty' valign='top'></td>
				</tr>
			</table>
		</div>
		<div class="clearfix"></div>
		<div id="paginationDiv" class="clearfix">
			<div class="col-md-6">
				<div class="pull-left mt5">
					<label class="pull-left">
					<span class="pull-left mt10 mr5">Show</span>
						<div class="dropdown-select pull-left mr5 tblminwidth-50">
							<select id="pageSize" onchange="getFormSubmissionMasterList();">
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
</section>
<script type="text/javascript">
	$(document).ready(function () {
		getFormSubmissionMasterList();
	});
	function getActions(){
		$(".action-pop").slideToggle(200);
	}	
	$("html").click(function(){
		$(".action-pop").slideUp(200);
	});
	$(".action, .action-pop").click(function(e){
	 	e.stopPropagation();
	});
</script>