<!-- this is for mask -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script src="/resources/js/jquery.maskedinput-1.3.min.js"></script>
<script src="/resources/js/formvalidator.js"></script>

	<div class='timesheet_head timesheetRep'>
		<div class="timeReFi">
			<select id="frequencyYear" name="frequencyYear"	class="form-control chosen-select" onchange="getMonths();">
			<c:forEach var="yrList1" items="${timesheetReportsDTO.monthYearList}">
					<option data-icon="fa fa-calendar" ${timesheetReportsDTO.frequencyYear == yrList1.key ? 'selected' : ''}  value="${yrList1.key}">${yrList1.key}</option>
			</c:forEach>
			</select>
		</div>
		<c:set var="flag" value="true"></c:set>
		<c:forEach var="yrList" items="${timesheetReportsDTO.monthYearList}">
			<div class="timeReFi time_months" data-year="${yrList.key}">
				<select id="frequencyMonth" name="frequencyMonth" class="form-control chosen-select">
					 <c:forEach var="mnList" items="${yrList.value}"> 
						<option data-icon="fa fa-calendar" ${timesheetReportsDTO.frequencyMonth == mnList? 'selected' : ''}  value="${mnList}">${mnList}</option>
					 </c:forEach> 
				</select>
			</div>
			<c:set var="flag" value="false"></c:set>
		</c:forEach>
		
		
		
		<div class="timeReFi">
			<input type="button" onclick="timesheetReportsDiv();" class="btn btn-success" value="View"> 
		</div>

		<ul>
			<li title="Export" class="mrPOP" id="mypop"	onclick="exportToggle();"><i class="glyphicon glyphicon-export"></i>
				<div id="exportPop">
					<ul>
						<li onclick="exportTimeSheetReportsToPdf('pdf');"><i class="fa fa-file-pdf-o"></i>&nbsp;&nbsp;PDF</li>
						<li onclick="exportTimeSheetReportsToPdf('Excel');"><i class="fa fa-file-excel-o text-primary"></i>&nbsp;&nbsp;Excel</li>
					</ul>
				</div>
			</li>
		</ul>
	<div class="remove_search">
       		<input type="text" placeholder="Search..." value="" id="dataTableSearchExpenses">
        </div>
		<div class="clearfix"></div>
	</div>
	<jsp:include page="timeReportsTableAndData.jsp"></jsp:include>
	<script type="text/javascript">
		$(document).ready(function(){
			getMonths();
		});
		function getMonths() {
			var year = $("#frequencyYear").val();
			$(".time_months").each(function () {
				$(this).hide();
			});
			$("div[data-year="+year+"]").show();
		}
	</script>

	<script>
	$("#dataTableSearchExpenses").keyup(function(event) {
	    if (event.keyCode === 13) {
	    	var searchData = $('#dataTableSearchExpenses').val();
	    	timesheetReportsDiv(searchData)
	    	$('#dataTableSearchExpenses').val(searchData);
	    }
	});
	</script>
	
	