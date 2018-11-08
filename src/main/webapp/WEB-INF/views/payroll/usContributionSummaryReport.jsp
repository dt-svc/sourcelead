<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- Content Area -->
  <section class="content-wraper contentWraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Contribution Summary</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard'/>" title="Home">Home</a>&raquo;
      <a href="<c:url value='${appPath}/usPayrollReport'/>"> USPayroll Reports List</a>&raquo;Contribution Summary
		</div>
    </section>
    <div class="contentareaInner-styles clearfix">
     <form:form id="contribution_summary_report_form" name="contribution_summary_report_form" method="post">
    <div class="clearfix minHeight">
     <div class="clearfix row">
		  <div class="col-md-5">
            <div class="form-group">
              <label class="col-md-12 paddingRight">From Date<span class="text-danger">&nbsp;*</span></label>
				<div class="col-md-12">
					<input type="text" class="form-control calendar" field-name="From Date" id="fromDate" data-validation="required">
				</div>
            </div>
            </div>
            <div class="col-md-5">
            <div class="form-group">
              <label class="col-md-12">To Date<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-12">
                <input type="text" class="form-control calendar" field-name="To Date" id="toDate" data-validation="required">
              </div>
            </div>
			</div>
     </div>
	<div class="clearfix footerstyelsNew">
        <div class="col-lg-12">
          <div class="pull-right">
            <input value="Run" class="btn btn-success btnSuccess" type="button" onClick="runContributionSummary();">
            <input value="Reset" class="btn btn-defualt ml10" type="button" onclick="resetDiv();">
          </div>
        </div>
      </div>      
	</div>
	</form:form>
     <div class="clearfix"></div>
  <div class="timesheet_out" id="payrollRegister" style="display: none;">
      <div class="clearfix">
        
        <div class="positionRelative reportHead clearfix">
  <ul class="pull-right list-unstyled list-inline">
    <li><a href="javascript:void(0)" class="overIcon" title="Actions" onclick="newActions();"></a>
      <div class="newActPop" style="">
       <span><a href="javascript:void(0)" onclick="exportcontributionSummaryPdf();" title="PDF">Export PDF</a> <a href="javascript:void(0)" onclick="exportcontributionSummaryXls();" title="Excel">Export Excel</a> </span> </div>
    </li>
    <li class="">
      <input class="repSearchBar" placeholder="Search" id="contributionSummarySearch" type="text">
      <span class="repSearchIcon" onclick="runContributionSummary();"></span></li>
  </ul>
</div>
<div class="candidate_wrap_table datatable_filter">
	                 <div id="contributionSummaryReportDIV"></div>
         </div>
      </div>
    </div>
      <!-- Content Area Ends--> 
    </div>
  </section>
  
  
<script>	
function getActions(){
	$(".action-pop").slideToggle(200);
	$(".newActPop").slideUp(200);
}	
$("html").click(function(){
	$(".action-pop").slideUp(200);
});
$(".action, .action-pop").click(function(e){
 	e.stopPropagation();
});	
function newActions(){
	$(".newActPop").slideToggle(200);
	$(".action-pop").slideUp(200);
}
$("html").click(function(){
	$(".newActPop").slideUp(200);
});
$(".overIcon, .newActPop").click(function(e){
 	e.stopPropagation();
});

</script>


  <script>
	$(function() {
		 var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
		 var finalDateFormatEndDate = '';
		  if(locationDate.match("yyyy-MM-dd")){
			  finalDateFormatEndDate = 'yy-mm-dd';
		  }if(locationDate.match("dd-MM-yyyy")){
			  finalDateFormatEndDate = 'dd-mm-yy'; 
		  }if(locationDate.match("MM-dd-yyyy")){
			  finalDateFormatEndDate = 'mm-dd-yy'; 
		  }if(locationDate.match("yyyy-dd-MM")){
			  finalDateFormatEndDate = 'yy-dd-mm'; 
		  }
		//to set from date to current month
	       var currentTime = new Date()
	       var minDate = new Date(currentTime.getFullYear(), currentTime.getMonth(), +1);
		   $('#fromDate').datepicker({
				dateFormat : finalDateFormatEndDate,
				changeMonth: true,
				changeYear: true,
				onSelect : function(selected) {
				var dt = $(this).datepicker('getDate', '+1d');
				dt.setDate(dt.getDate());
				$("#toDate").datepicker("option", "minDate", dt);
				}
			});
		
	  $('#toDate').datepicker({
			dateFormat : finalDateFormatEndDate,
			changeMonth: true,
			changeYear: true, 
			onSelect : function(selected) {
			var dt = $(this).datepicker('getDate', '-1d');
			dt.setDate(dt.getDate());
			$("#fromdate").datepicker("option", "maxDate", dt);
			}
		});
	});
</script>

<script>
function runContributionSummary(){
	
	if ($('#contribution_summary_report_form').validate(false, validationSettings)){ 
		$("#payrollRegister").show();
	    var  fromDate = $("#fromDate").val();
	    var toDate = $("#toDate").val();
		var searchValue = $("#contributionSummarySearch").val();
		searchValue = searchValue ? searchValue : "";
		var url = sourceleadUrl+"/getContributionSummaryReports?searchValue="+searchValue+"&fromDate="+fromDate+"&toDate="+toDate+"&type=CONTRIBUTION";
		contributionSummaryReportsList(url);	
			return true;
	}else{ 
			return false;
	}  
	
}


function contributionSummaryReportsList(url) {
	$("#contributionSummaryReportDIV").empty();
	var result = "";
	result = result + '<table id="contributionSummaryReportTable" class="display" cellspacing="0" width="100%" >';
	result = result + '<thead>';
	result = result + '<tr>';
	result = result + '<th>Type</th>';
	result = result + '<th>No of Employees</th>';
	result = result + '<th>Current</th>';
	result = result + '<th>QTD</th>';
	result = result + '<th>YTD</th>';
	result = result + '</tr>';
	result = result + '</thead>	';
	result = result + "</table>";
	$("#contributionSummaryReportDIV").append(result);
	contributionSummaryReportData(url);

}


function contributionSummaryReportData(url) {
	var colIndex = 0;
	$('#contributionSummaryReportTable')
			.DataTable(
					{
						"processing" : true,
						"serverSide" : true,
						"searching" : false,
						"sorting" : false,
						"ajax" : $.fn.dataTable.pipeline({
							url : url,
							method : 'GET',
							pages : 10
						}),
						"columns" : [ {
							"data" : "type"
						}, {
							"data" : "noOfEmployees"
						}, {
							"data" : "current"
						}, {
							"data" : "qtd"
						},{
							"data" : "ytd"
						}],
						"columnDefs" : [
								{
									"targets" : colIndex++,
									"data" : "type",
									"sortable": false,
									"render" : function(data, type, opt,meta) {
										
										var result = "";
										
										if(opt.deductionType != null && opt.deductionType != '' && opt.deductionType != 'undefined'){
											result = result+ '<td><div class="tblminwidth-75">'+opt.deductionType+'</div></td>';
										}else{
											result = result+ '<td><div class="tblminwidth-75">--</div></td>';
										} 
										
										return result;
									}
								},
								{
									"targets" : colIndex++,
									"data" : "noOfEmployees",
									"sortable": false,
									"render" : function(data, type, opt,meta) {
										var result = "";
										if(opt.candidateCount != null && opt.candidateCount != '' && opt.candidateCount != 'undefined'){
											result = result+ '<td><div class="tblminwidth-75">'+opt.candidateCount+'</div></td>';
										}else{
											result = result+ '<td><div class="tblminwidth-75">--</div></td>';
										}
										
										return result;

									}
								},
								{
									"targets" : colIndex++,
									"data" : "current",
									"sortable": false,
									"render" : function(data, type, opt,meta) {
										var result = "";
										if(opt.currentAmt != null && opt.currentAmt != '' && opt.currentAmt != 'undefined'){
											result = result + '<td class="tblminwidth-75"><i class="fa fa-dollar"style="color:#454545;"></i><div>'+opt.currentAmt+'</div></td>';
										}else{
											result = result + '<td class="tblminwidth-75"><div>--</div></td>';
										}
										
										return result;
									}
								},
								{
									"targets" : colIndex++,
									"data" : "qtd",
									"sortable": false,
									"render" : function(data, type, opt,meta) {
										var result = "";
										if(opt.qtd != null && opt.qtd != '' && opt.qtd != 'undefined'){
											result = result + '<td class="tblminwidth-75"><i class="fa fa-dollar"style="color:#454545;"></i><div>'+opt.qtd+'</div></td>';
										}else{
											result = result + '<td class="tblminwidth-75"><div>--</div></td>';
										}
										return result;
									}
								},
								{
									"targets" : colIndex++,
									"data" : "ytd",
									"sortable": false,
									"render" : function(data, type, opt,meta) {
										var result = "";
										if(opt.ytd != null && opt.ytd != '' && opt.ytd != 'undefined'){
											result = result + '<td class="tblminwidth-75"><i class="fa fa-dollar"style="color:#454545;"></i><div>'+opt.ytd+'</div></td>';
										}else{
											result = result + '<td class="tblminwidth-75"><div>--</div></td>';
										}
										return result;
									}
								}],
						"scrollCollapse" : false,
						"lengthMenu" : [ [ 10, 15, 20 ], [ 10, 15, 20 ] ],
						"bLengthChange" : true,
						"bDestroy" : true,
						"ordering" : true,
						"oLanguage" : {
							"sSearch" : "",
							"sZeroRecords" : "",
							"sEmptyTable" : "",
							"sProcessing" : "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
						}
					});
	$("#contributionSummaryReportTable").wrap(
			"<div style='overflow-x: auto; overflow-y: hidden;'></div>");
	$("input[type=search]").hide();
	$("#contributionSummarySearch").keyup(function(e) {
		if (e.which === 13) {
			runContributionSummary();
		}
	});
	
}

function resetDiv(){
	$("#payrollRegister").hide();
	$("#fromDate").val('');
	$("#toDate").val('');
}

</script>


 <script>
 function exportcontributionSummaryPdf(){
	 var searchValue = $("#contributionSummarySearch").val();
	 searchValue = searchValue ? searchValue : "";
     var fromDate = $("#fromDate").val();
     var toDate = $("#toDate").val();
	 window.location.href = sourceleadUrl+"/exportdeductionContributionSummaryReport?"+tokenParamName+"="+tokenValue+"&fromDate="+fromDate+"&toDate="+toDate+"&searchValue="+searchValue+"&type=CONTRIBUTION&exportType=pdf";
 }
 
 </script>
 
   <script>
 function exportcontributionSummaryXls(){
	 var searchValue = $("#contributionSummarySearch").val();
	 searchValue = searchValue ? searchValue : "";
     var fromDate = $("#fromDate").val();
     var toDate = $("#toDate").val();
	 window.location.href = sourceleadUrl+"/exportdeductionContributionSummaryReport?"+tokenParamName+"="+tokenValue+"&fromDate="+fromDate+"&toDate="+toDate+"&searchValue="+searchValue+"&type=CONTRIBUTION&exportType=xls";
 }
 
 </script>
  


<jsp:include page="../masterFooter.jsp" />