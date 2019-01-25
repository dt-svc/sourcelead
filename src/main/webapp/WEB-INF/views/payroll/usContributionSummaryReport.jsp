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
      <a href="<c:url value='${appPath}/usPayrollReport'/>"> US Payroll Reports List</a>&raquo;Contribution Summary
		</div>
    </section>
    <div class="contentareaInner-styles clearfix">
     <form:form id="contribution_summary_report_form" name="contribution_summary_report_form" method="post">
    <div class="clearfix minHeight">
     <div class="clearfix row">
		  <div class="col-md-5">
            <div class="form-group">
              <label class="col-md-12 paddingRight">From Date<span class="text-danger">&nbsp;*</span></label>
				<div class="col-md-12" id="fromDateDiv">
					<input type="text" class="form-control calendar" readonly field-name="From Date" id="fromDate" data-validation="required">
				</div>
            </div>
            </div>
            <div class="col-md-5">
            <div class="form-group">
              <label class="col-md-12">To Date<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-12" id="toDateDiv">
                <input type="text" class="form-control calendar" readonly field-name="To Date" id="toDate" data-validation="required">
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
				removeFromDateValid();
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
			removeToDateValid();
			}
		});
	});
	
	function removeFromDateValid(){
		$('#fromDateDiv').find('p.jquery_form_error_message').remove();
	}

	function removeToDateValid(){
		$('#toDateDiv').find('p.jquery_form_error_message').remove();
	}
</script>

<div id="popUp"></div>
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
$('body').on('blur', '#contribution_summary_report_form', function() {
	$("#contribution_summary_report_form").showHelpOnFocus();
	$("#contribution_summary_report_form").validateOnBlur(false, validationSettings);
});



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
											result = result+ '<td><div class="tblminwidth-175" id="usContributionTypeDiv">'+opt.deductionType+'</div></td>';
										}else{
											result = result+ '<td><div class="tblminwidth-175">--</div></td>';
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
											result = result + '<td class="tblminwidth-75"><i class="fa fa-dollar mr5"style="color:#454545;"></i>'+opt.currentAmt+'</td>';
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
											result = result + '<td class="tblminwidth-75"><i class="fa fa-dollar mr5"style="color:#454545;"></i>'+opt.qtd+'</td>';
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
											result = result + '<td class="tblminwidth-75"><i class="fa fa-dollar mr5"style="color:#454545;"></i>'+opt.ytd+'</td>';
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
	$('#toDate').attr('value', '');
	$('#toDate').datepicker( "option" , {
        minDate: null,
        maxDate: null} );
	$("#toDate").val('');
}

</script>


 <script>
 function exportcontributionSummaryPdf(){
	 var searchValue = $("#contributionSummarySearch").val();
	 searchValue = searchValue ? searchValue : "";
     var fromDate = $("#fromDate").val();
     var toDate = $("#toDate").val();
     var usContribution = $('#usContributionTypeDiv').html();
 	if(usContribution != null && usContribution !='' && usContribution !='undefined'){
	 window.location.href = sourceleadUrl+"/exportdeductionContributionSummaryReport?"+tokenParamName+"="+tokenValue+"&fromDate="+fromDate+"&toDate="+toDate+"&searchValue="+searchValue+"&type=CONTRIBUTION&exportType=pdf";
 	}else{
		var result = " ";
		result = result +"	<div class='project_inn_mask'></div>";
		result = result +"	   <div id='empty_receivable_pop' class='remove_user_pop remove_user_pop_a'>";
		result = result +"		<p>Contribution Summary List is Empty</p>";
		result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
		result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='cancelExport();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelExport();'></b>";
		result = result +"	</div>";
		$("#popUp").append(result);
		$("#empty_receivable_pop").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
	}
}
 
 </script>

 <script type="text/javascript">
 function cancelExport(){
 	$("#popUp").empty();
 	$(".project_inn_mask").fadeOut(200);
 }
 </script>
 
   <script>
 function exportcontributionSummaryXls(){
	 var searchValue = $("#contributionSummarySearch").val();
	 searchValue = searchValue ? searchValue : "";
     var fromDate = $("#fromDate").val();
     var toDate = $("#toDate").val();
     var usContribution = $('#usContributionTypeDiv').html();
  	if(usContribution != null && usContribution !='' && usContribution !='undefined'){
	 window.location.href = sourceleadUrl+"/exportdeductionContributionSummaryReport?"+tokenParamName+"="+tokenValue+"&fromDate="+fromDate+"&toDate="+toDate+"&searchValue="+searchValue+"&type=CONTRIBUTION&exportType=xls";
 }else{
		var result = " ";
		result = result +"	<div class='project_inn_mask'></div>";
		result = result +"	   <div id='empty_receivable_pop' class='remove_user_pop remove_user_pop_a'>";
		result = result +"		<p>Contribution Summary List is Empty</p>";
		result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
		result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='cancelExport();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelExport();'></b>";
		result = result +"	</div>";
		$("#popUp").append(result);
		$("#empty_receivable_pop").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
	}
}
</script>

<script type="text/javascript">
 function cancelExport(){
 	$("#popUp").empty();
 	$(".project_inn_mask").fadeOut(200);
 }
 </script>  


<jsp:include page="../masterFooter.jsp" />