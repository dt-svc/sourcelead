<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script src="/pms/resources/js/tax/my_payroll.js" type="text/javascript"></script>

<script>
var pmsUrl ='${SourceLeadWeb_pmsUrl}';
</script>
	<div class="tablelist-wrap">
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
			<!-- 	<li>
					<a class="action newAdBtn mr5" onclick="getActions();" href="javascript:void(0)">+ Submit Form <i class="fa fa-caret-down"></i></a>
					<div class="action-pop">
						<span> 
							<a href="/app/itDeclaration?autoSelectUser=true">IT Declaration</a>
							<a href="javascript:void(0)">IT Challan</a>
							<a href="javascript:void(0)">Form 16</a>
						</span>
					</div>
				</li> -->
			  	<!-- <li class=""><input type="text" class="repSearchBar" placeholder='Search' id='dynamicUserDataTableSearch'/><span class="repSearchIcon" onClick="getDynamciUserList();"></span></li> -->
	        </ul>
		</div>			
		<div class="candidate_wrap_table datatable_filter dataSeaTop  clearfix">
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


