<!-- this is for mask -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<script src="/resources/js/jquery.maskedinput-1.3.min.js"></script>
<script src="/resources/js/formvalidator.js"></script>


<div class="reportHead">
    	<ul class="clearfix pull-right">
    		<li>
				<div class="timeReFi noPad mr10">
					<select id="frequencyYear" name="frequencyYear"	class="form-control chosen-select" onchange="getMonths();">
					<c:forEach var="yrList1" items="${timesheetReportsDTO.monthYearList}">
							<option data-icon="fa fa-calendar" ${timesheetReportsDTO.frequencyYear == yrList1.key ? 'selected' : ''}  value="${yrList1.key}">${yrList1.key}</option>
					</c:forEach>
					</select>
				</div>
				<c:set var="flag" value="true"></c:set>
				<c:forEach var="yrList" items="${timesheetReportsDTO.monthYearList}">
					<div class="timeReFi time_months noPad" data-year="${yrList.key}">
						<select id="frequencyMonth" name="frequencyMonth" class="form-control chosen-select">
							 <c:forEach var="mnList" items="${yrList.value}"> 
								<option data-icon="fa fa-calendar" ${timesheetReportsDTO.frequencyMonth == mnList? 'selected' : ''}  value="${mnList}">${mnList}</option>
							 </c:forEach> 
						</select>
					</div>
					<c:set var="flag" value="false"></c:set>
				</c:forEach>
				<div id="" class="repSelBtn"> <button type="button" class="btn btn-primary btn-sm" onclick="timesheetReportsDiv();">Run</button></div> 
    		</li>
			<li class="staPdfExl"><i onclick="exportTimeSheetReportsToPdf('pdf');" title="PDF" class="fa fa-file-pdf-o text-danger"></i><i onclick="exportTimeSheetReportsToPdf('Excel');" title="Excel" class="fa fa-file-excel-o text-primary"></i></li>
    		<li class=""><input type="text" class="repSearchBar" placeholder='Search' id='dataTableSearchExpenses'/><span class="repSearchIcon" onclick=""></span></li>
    	</ul>
    </div> 
	
	
	<jsp:include page="timeReportsTableAndData.jsp"></jsp:include>
	<script type="text/javascript">
		$(document).ready(function(){
			if('${AWKWARD}' == "true"){
				   window.location.href = "/errorPage";
			}else{
				getMonths();
			}
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
	
	