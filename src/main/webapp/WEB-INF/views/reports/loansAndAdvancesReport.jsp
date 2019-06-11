<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!-- <script type="text/javascript" src="/resources/js/loanAndAdvances.js"></script> -->

<script>  
 $(document).ready(function() { 
  	getLoanNames(); 
  }); 
 </script>  


 <!-- Content Area -->
  <section class="content-wraper contentWraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Loan Report</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/reportsDashboard'/>">Reports Dashboard</a> &raquo; <a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/associateReportsList'/>">Associate Reports</a> &raquo; Loan Report </div>
    </section>
    
    <div class="contentareaInner-styles clearfix" id="resetBottonDiv">
    
    <form:form id="loan_report_form"  name="loan_report_form" method="post">
    <div class="clearfix minHeight">
        <div class="clearfix row">
          <div class="col-md-5">
            <div class="form-group">
              <label class="clearfix">Loan Name</label>
				<div class="dropdown-select">
                    <select class="form-control chosen-select" name="loanName" id="loanName" field-name="Name">
                        
                      </select>
                  </div>
            </div>
			</div>
			
			<div class="col-md-5">
			<div class="form-group">
              <label class="clearfix">Emp Type</label>
              <div class="clearfix">
                <select class="sel" id="designType"
								name="designType"
								field-name="Employment type">
								<option value="">--Select Employment Type--</option>
								<option>C2C</option>
								<option>C2C-1099</option>
								<option>W-2</option>
								<option>C2H</option>
								<option>C2H -1099</option>
								<option>Part Time</option>
								<option>Full Time</option>
								<option>Other</option>
							</select>
              </div>
            </div>
			</div>
			
			<div class="col-md-5">
			<div class="form-group">
              <label class="clearfix">Status</label>
               <div class="dropdown-select">
                    <select class="form-control" data-placeholder="Select Type" id="loanAndAdvStatus" name="loanAndAdvStatus" field-name="Status">
                      <option value="">-- Select Status --</option>
                      <option value="Requested">Requested</option>
                      <option value="Outstanding">Outstanding</option>
                      <option value="Completed">Completed</option>
                    </select>
                  </div>
            </div>
			</div>
			
			  
			  <div class="col-md-5">
            <div class="form-group">
              <label class="clearfix">From Date<span class="text-danger">&nbsp;*</span></label>
              <div class="clearfix">
                	<input type="text" class="form-control calendar" id="fromDate" data-validation="required" field-name="From Date">
              </div>
            </div>
			</div>
            <div class="col-md-5">
             <div class="form-group">
              <label class="clearfix">To Date<span class="text-danger">&nbsp;*</span></label>
              <div class="clearfix">
                	<input type="text" class="form-control calendar" id="toDate" data-validation="required" field-name="To Date">
              </div>
            </div>
             
        </div>
      </div>
	<div class="clearfix footerstyelsNew">
        <div class="col-lg-12">
          <div class="pull-right">
            <input value="Run" class="btn btn-success btnSuccess" type="button" onClick="runLoanReport();">
            <input value="Reset" class="btn btn-defualt ml10" type="button" onclick="resetLoanReport();">
          </div>
        </div>
      </div>      
	</div>
	</form:form>
    
     <div class="clearfix"></div>
    		
    		
  <div class="timesheet_out" id="payrollRegister" >
       <div class="clearfix">
       
        <div class="positionRelative reportHead clearfix" id="loanSearchDiv" style="display: none;">
  <ul class="pull-right list-unstyled list-inline">
    <li><a href="javascript:void(0)" class="overIcon" title="Actions" onclick="newActions();"></a>
      <div class="newActPop" style="">
       <span><a href="javascript:void(0)" onclick="exportLoanAndAdvanceReportPdf();" title="PDF">Export PDF</a> <a href="javascript:void(0)" onclick="exportLoanAndAdvanceReportExcel();" title="Excel">Export Excel</a> </span> </div>
    </li>
    <li class="">
      <input class="repSearchBar" placeholder="Search" id="dataTableSearch" type="text">
      <span class="repSearchIcon" onclick="getDynamciUserList();"></span></li>
  </ul>
</div>


        <div class="require_wrap_table tableFirstSecond clearfix datatable_filter">
<!--        <div id="deductionReportDIV" class="clearfix require_wrap_table datatable_filter">  -->
          
           				<div id="popUp"></div>
	                 <input type="hidden" id="loanAndAdvanceDataLength" value="0"/>
	                 <div id="deductionReportDIV"></div>
          </div>
        
        
      </div>
    </div>
      <!-- Content Area Ends--> 
    </div>
    
  </section>

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
	/* function runPayrollregister() {
		$("#payrollRegister").toggle();
	} */
</script>

<script>
function resetButton(){
	document.getElementById("resetBottonDiv").reset();
}
</script>
  
<script>
function getLoanNames(){
	var pmsUrl ='${SourceLeadWeb_pmsUrl}';
                 $.ajax({
				   	type: "GET",
				    url: pmsUrl+"/getLoanNames?${_csrf.parameterName}=${_csrf.token}",
		   		    success: function(data) {
		   		    	result  = "";
		   		    	result = result + '<option value=""> -- Select Loan Name -- </option>';
		   		       for (var i=0; i < data.length; i++){
		   		    	   var report = data[i];
		   		    	result = result+ '<option> '+report.name+' </option>';
		   		    	}
		   		        $("#loanName").empty();
		   		    	$("#loanName").append(result);
		   		    	$("#loanName").trigger("chosen:updated");
		   		    	
		   		    }
			     });
	 
}
 </script>
 
 <script>
 function runLoanReport() {
	 if ($('#loan_report_form').validate(false, validationSettings)){
	    var fromDate = $("#fromDate").val();
	    var toDate = $("#toDate").val();
	    var loanAndAdvStatus = $("#loanAndAdvStatus").val();
	    var loanName = $("#loanName").val();
	    var designType = $("#designType").val();
	    var searchValue = $("#dataTableSearch").val();
	    searchValue = searchValue ? searchValue : "";
		var url =  sourceleadUrl+"/getLoanReport?searchValue="+searchValue+"&fromDate="+fromDate+"&toDate="+toDate+"&loanAndAdvStatus="+loanAndAdvStatus+"&loanName="+loanName+"&empType="+designType;
		loanReportURL(url);	
	 }else{
		   return false;
	 }
}

</script>


<script>
	function loanReportURL(url) {
		$("#deductionReportDIV").show();
		$("#deductionReportDIV").empty();
		var result = "";
		result = result + '<table id="loanReportTable" class="display" cellspacing="0" width="100%" >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result + '<th>Employee Id</th>';
		result = result + '<th>Employee Name</th>';
		result = result + '<th>Type</th>';
		result = result + '<th>Name</th>';
		result = result + '<th>Start Date</th>';
		result = result + '<th>End Date</th>';
		result = result + '<th>Claimed Amount</th>';
		result = result + '<th>Paid Amount</th>';
		result = result + '<th>Peding Amount</th>';
		result = result + '<th>No. of Schedules</th>';
		result = result + '<th>Deducted Amount</th>';
		result = result + '<th>Status</th>';
		result = result + '</tr>';
		result = result + '</thead>	';
		result = result + "</table>";
		$("#deductionReportDIV").append(result);
		payCheckDateData(url);
		$("#loanAndAdvanceDataLength").val(0);

	}
</script>

 <script>
	function payCheckDateData(url) {
		$("#loanSearchDiv").show();
		var colIndex = 0;
		$('#loanReportTable')
				.DataTable(
						{
							"processing" : true,
							"serverSide" : true,
							"searching" : false,
							"ajax" : $.fn.dataTable.pipeline({
								url : url,
								method : 'GET',
								pages : 10
							}),
							"columns" : [ {
								"data" : "employeeId"
							},  {
								"data" : "employeeName"
							}, {
								"data" : "loanAndAdvType"
							}, {
								"data" : "loanName"
							},{
								"data" : "startDate"
							}, {
								"data" : "endDate"
							}, {
								"data" : "approvedAmount"
							}, {
								"data" : "paidAmount"
							},{
								"data" : "pedingAmount"
							},{
								"data" : "noOfSchedules"
							},{
								"data" : "deductedAmount"
							},{
								"data" : "status"
							}], 
							 "columnDefs" : [
								{
									"targets" : colIndex++,
									"data" : "employeeId",
									"sortable": true,
									"render" : function(data, type, opt, meta) {
										   $("#loanAndAdvanceDataLength").val(1);
										     var result = "";
										     if(opt.employeeId != null && opt.employeeId != '' && opt.employeeId != 'undefined'){
										    	 result = result+ ' <td><div class="tblminwidth-75">'+opt.employeeId+'</div></td>';
										     }else{
										    	 result = result+ ' <td><div class="tblminwidth-75">--</div></td>';
										     }
											
										
										return result;
									}
								},
								{
										"targets" : colIndex++,
										"data" : "employeeName",
										"sortable": false,
										"render" : function(data, type, opt,meta) {
											
											var result = "";
											if(opt.firstName != null && opt.firstName != '' && opt.firstName != 'undefined'){
												result = result+ ' <td><div class="tblminwidth-75">'+opt.firstName+'</div></td>';
											}else{
												result = result+ ' <td><div class="tblminwidth-75">--</div></td>';
											}
											return result;
										}
									},
									{
										"targets" : colIndex++,
										"data" : "loanAndAdvType",
										"sortable": true,
										"render" : function(data, type, opt,meta) {
											var result = "";
											if(opt.loanAndAdvType != null && opt.loanAndAdvType != '' && opt.loanAndAdvType != 'undefined'){
												result = result+ '<td><div class="tblminwidth-75">'+opt.loanAndAdvType+'</div></td>';
											}else{
												result = result+ ' <td><div class="tblminwidth-75">--</div></td>';
											}
												
											return result;

										}
									},
									{
										"targets" : colIndex++,
										"data" : "loanName",
										"sortable": true,
										"render" : function(data, type, opt,meta) {
											var result = "";
											if(opt.loanName != null && opt.loanName != '' && opt.loanName != 'undefined'){
												result = result + '<td class="tblminwidth-75"><div>'+opt.loanName+'</div></td>';
											}else{
												result = result + '<td class="tblminwidth-75"><div>--</div></td>';
											}
											return result;
										}
									},
									{
										"targets" : colIndex++,
										"data" : "startDate",
										"sortable": false,
										"render" : function(data, type, opt,meta) {
											var result = "";
											result = result+ '<td><div class="tbldata-dotdot-vsm" title="">'+opt.fromDate+'</div></td>';
											return result;
										}
									},{
										"targets" : colIndex++,
										"data" : "endDate",
										"sortable": false,
										"render" : function(data, type, opt,meta) {
											var result = "";
											result = result+ '<td><div class="tbldata-dotdot-vsm" title="">'+opt.toDate+'</div></td>';
											return result;
										}
									},{
										"targets" : colIndex++,
										"data" : "approvedAmount",
										"render" : function(data, type, opt,meta) {
											var result = "";
											if(opt.loanTotalAmount != null && opt.loanTotalAmount != '' && opt.loanTotalAmount != 'undefined'){
												result = result+ '<td><div class="tbldata-dotdot-vsm" title="">$ '+opt.loanTotalAmount+'</div></td>';
											}else{
												result = result + '<td class="tblminwidth-75"><div>--</div></td>';
											}
											
											return result;
										}
									},{
										"targets" : colIndex++,
										"data" : "paidAmount",
										"render" : function(data, type, opt,meta) {
											var result = "";
											if(opt.loanPaidAmount != null && opt.loanPaidAmount != '' && opt.loanPaidAmount != 'undefined'){
												result = result+ '<td><div class="tbldata-dotdot-vsm" title="">$ '+opt.loanPaidAmount+'</div></td>';
											}else{
												result = result + '<td class="tblminwidth-75"><div>--</div></td>';
											}
											
											return result;
										}
									},{
										"targets" : colIndex++,
										"data" : "pedingAmount",
										"render" : function(data, type, opt,meta) {
											var result = "";
											if(opt.loanPendingAmount != null && opt.loanPendingAmount != '' && opt.loanPendingAmount != 'undefined'){
												result = result+ '<td><div class="tbldata-dotdot-vsm" title="">$ '+opt.loanPendingAmount+'</div></td>';
											}else{
												result = result + '<td class="tblminwidth-75"><div>--</div></td>';
											}
											
											return result;
										}
									},{
										"targets" : colIndex++,
										"data" : "noOfSchedules",
										"render" : function(data, type, opt,meta) {
											var result = "";
											if(opt.loanNoOfSchedules != null && opt.loanNoOfSchedules != '' && opt.loanNoOfSchedules != 'undefined'){
												result = result+ '<td><div class="tbldata-dotdot-vsm" title="">'+opt.loanNoOfSchedules+'</div></td>';
											}else{
												result = result + '<td class="tblminwidth-75"><div>--</div></td>';
											}
											
											return result;
										}
									},{
										"targets" : colIndex++,
										"data" : "deductedAmount",
										"render" : function(data, type, opt,meta) {
											var result = "";
											if(opt.loanDeductAmount != null && opt.loanDeductAmount != '' && opt.loanDeductAmount != 'undefined'){
												result = result+ '<td><div class="tbldata-dotdot-vsm" title="">$ '+opt.loanDeductAmount+'</div></td>';
											}else{
												result = result + '<td class="tblminwidth-75"><div>--</div></td>';
											}
											
											return result;
										}
									},{
										"targets" : colIndex++,
										"data" : "status",
										"render" : function(data, type, opt,meta) {
											var result = "";
											if(opt.loanAndAdvStatus != null && opt.loanAndAdvStatus != '' && opt.loanAndAdvStatus != 'undefined'){
												result = result+ '<td><div class="tbldata-dotdot-vsm" title="">'+opt.loanAndAdvStatus+'</div></td>';
											}else{
												result = result + '<td class="tblminwidth-75"><div>--</div></td>';
											}
											
											return result;
										}
									}], 
							"scrollCollapse" : false,
							"lengthMenu" : [ [ 10, 15, 20 ], [ 10, 15, 20 ] ],
							"aaSorting" : [ [ 3, 'desc' ] ],
							"bLengthChange" : true,
							"bDestroy" : true,
							"ordering" : false,
							 "oLanguage" : {
								"sSearch" : "",
								"sZeroRecords" : "",
								"sEmptyTable" : "",
								"sProcessing" : "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
							} 
						});
		$("#loanReportTable").wrap(
				"<div style='overflow-x: auto; overflow-y: hidden;'></div>");
		$("input[type=search]").hide();
		$("#dataTableSearch").keyup(function(e) {
			if (e.which === 13) {
				runLoanReport();
			}
		});
	}
</script> 

<script>
function resetLoanReport(){
	$("#deductionReportDIV").hide();
	$("#loanSearchDiv").hide();
	$("#loanName").val('');
	$("#loanName").trigger("chosen:updated");
	$("#designType").val('');
	$("#designType").trigger("chosen:updated");
	$("#loanAndAdvStatus").val('');
	$("#loanAndAdvStatus").trigger("chosen:updated");
	$("#fromDate").val('');
	$("#fromDate").trigger("chosen:updated");
	$("#toDate").val('');
	$("#toDate").trigger("chosen:updated");
}
</script>

<script>
function exportLoanAndAdvanceReportPdf(){
	var searchValue = $("#dataTableSearch").val();
	var fromDate = $('#fromDate').val();
	var toDate = $('#toDate').val();
	
  var loanAndAdvanceDataLength = $("#loanAndAdvanceDataLength").val();
	  
	  if(loanAndAdvanceDataLength > 0){
		  
			if(fromDate != null && fromDate != '' && fromDate != 'undefined' && toDate != null && toDate != '' && toDate != 'undefined'){
				window.location.href = sourceleadUrl+ "/getLoanAndAdvancesReportExport?${_csrf.parameterName}=${_csrf.token}&fromDate="+fromDate+"&toDate="+toDate+"&exportType=pdf&searchValue="+searchValue;
			}
	   }else{
		   
		   var result = "";
			result = result +"	<div class='project_inn_mask'></div>";
			result = result +"	   <div id='loan_and_advance_popup' class='remove_user_pop remove_user_pop_a'>";
			result = result +"		<p>Loan And Advance Report List is Empty</p>";
			result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
			result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='cancelLoanAndAdvance();' style='width : 100%'></b>";
			result = result +"	</div>";
			$("#popUp").append(result);
			$("#loan_and_advance_popup").slideDown(200);
			$(".project_inn_mask").fadeIn(200);
		   
	   }
	  
}
</script>

<script>
function cancelLoanAndAdvance(){
 	$("#popUp").empty();
 	$(".project_inn_mask").fadeOut(200);
 }
</script>

<script>
function exportLoanAndAdvanceReportExcel(){
	var searchValue = $("#dataTableSearch").val();
	var fromDate = $('#fromDate').val();
	var toDate = $('#toDate').val();
	
  var loanAndAdvanceDataLength = $("#loanAndAdvanceDataLength").val();
	  
	  if(loanAndAdvanceDataLength > 0){
		  
			if(fromDate != null && fromDate != '' && fromDate != 'undefined' && toDate != null && toDate != '' && toDate != 'undefined'){
				window.location.href = sourceleadUrl+ "/getLoanAndAdvancesReportExport?${_csrf.parameterName}=${_csrf.token}&fromDate="+fromDate+"&toDate="+toDate+"&exportType=excel&searchValue="+searchValue;
			}
	   }else{
		   
		   var result = "";
			result = result +"	<div class='project_inn_mask'></div>";
			result = result +"	   <div id='loan_and_advance_popup' class='remove_user_pop remove_user_pop_a'>";
			result = result +"		<p>Loan And Advance Report List is Empty</p>";
			result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
			result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='cancelLoanAndAdvance();' style='width : 100%'></b>";
			result = result +"	</div>";
			$("#popUp").append(result);
			$("#loan_and_advance_popup").slideDown(200);
			$(".project_inn_mask").fadeIn(200);
		   
	   }
	  
}
</script>


<jsp:include page="../masterFooter.jsp" />