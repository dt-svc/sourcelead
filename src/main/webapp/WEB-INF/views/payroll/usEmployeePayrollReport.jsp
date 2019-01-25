 <jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 
 <script src="/resources/js/payrollReports.js"></script>
 
 <script>
$(document).ready(function(){
   getDepartments("payrollDepartment");
});
</script>
 
  <!-- Content Area -->
  <section class="content-wraper contentWraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Employee Payroll</h2>
      </div>
       <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/usPayrollReport'/>">US Payroll Reports List</a> &raquo; Employee Payroll</div>
    </section>
    
    <div class="contentareaInner-styles clearfix">
    
    <form:form id="associate_payroll_form"  name="associate_payroll_form" method="post">
    <div class="clearfix minHeight">
        <div class="clearfix row">
          <div class="col-md-5">
          
            <div class="form-group">
              <label class="col-md-12">From Date<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-12">
                	<input type="text" id="fromDate" field-name = "From Date" class="form-control calendar" data-validation="required">
              </div>
            </div>
                 <div class="form-group">
              <label class="col-md-12">Select Department</label>
              <div class="col-md-12">
                 <select class="form-control chosen-select" multiple id="payrollDepartment" name="payrollDepartment" field-name="Department" onchange="getEmployeesBasedOnDepartment('employees');">
					</select>
              </div>
            </div>
     
			</div>
            <div class="col-md-5">
            
                 <div class="form-group">
              <label class="col-md-12">To Date<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-12">
                	<input type="text" id="toDate" field-name = "To Date" class="form-control calendar" data-validation="required">
              </div>
            </div>
                   <div class="form-group">
              <label class="col-md-12 paddingRight">Select Employee</label>
				<div class="col-md-12">
					<select class="form-control chosen-select" multiple id="employees" name="employees">
					</select>
				</div>
            </div>
             
        </div>
      </div>
	<div class="clearfix footerstyelsNew">
        <div class="col-lg-12">
          <div class="pull-right">
            <input value="Run" class="btn btn-success btnSuccess" type="button" onClick="runAssociatePayrollReport();">
            <input value="Reset" class="btn btn-defualt ml10" type="button" onClick="resetAllData();">
          </div>
        </div>
      </div>      
	</div>
	</form:form>
    
     <div class="clearfix"></div>
    		
    		
    		
    		
  <div class="timesheet_out" id="payrollRegister" style="display: none;">
      
<!--       <div class="clearfix">
                  	<div class="col-md-12">
                  		<div class="row">
                  			<label class="col-md-2">Total Employees:</label>
                  			<span class="col-md-5">10</span>
                  		</div>
                  	</div>
                  	<div class="col-md-12">
                  		<div class="row">
                  			<label class="col-md-2">Net Pay:</label>
                  			<span class="col-md-5">8</span>
                  		</div>
                  	</div>
                  	<div class="clearfix mb mt"></div>
       </div> -->
      
       <div class="clearfix">
        
        <div class="positionRelative reportHead clearfix">
  <ul class="pull-right list-unstyled list-inline">
    <li><a href="javascript:void(0)" class="overIcon" title="Actions" onclick="newActions();"></a>
      <div class="newActPop" style="">
       <span><a href="javascript:void(0)" onclick="exportEmployeePayrollPdf();" title="PDF">Export PDF</a> 
       		<a href="javascript:void(0)" onclick="exportEmployeePayrollExcel();" title="Excel">Export Excel</a> 
       	</span> </div>
    </li>
    <li class="">
      <input class="repSearchBar" placeholder="Search" id="dynamicUserPayrollTableSearch" type="text">
      <span class="repSearchIcon" onclick="runAssociatePayrollReport();"></span></li>
  </ul>
</div>
        <div class="require_wrap_table datatable_filter">
        	<div id="usEmployeePayrollDiv"></div>
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
  
  
  
  <script type="text/javascript">
			var config = {
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			}
			for (var selector in config) {
			  $(selector).chosen(config[selector]);
			}
			$('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});
				})
				$(document).ready(function(){
					$("#cd").click(function(){
						$(".clientDetails").toggleClass('hidden', 500);
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
function runAssociatePayrollReport(){
	
	
	 if ($('#associate_payroll_form').validate(false, validationSettings)){
		 $("#payrollRegister").show();
		var fromDate = $('#fromDate').val();
		var toDate = $('#toDate').val();
		var department = $('#payrollDepartment').val();
		var employees = $('#employees').val();
		var searchValue = $("#dynamicUserPayrollTableSearch").val();
		searchValue = searchValue ? searchValue : "";
		var url = sourceleadUrl+"/runEmployeePayroll?"+tokenParamName+"="+tokenValue+"&fromDate="+fromDate+"&toDate="+toDate+"&department="+department+"&employees="+employees+"&searchValue="+searchValue;
		runAssociatePayrollList(url);	
	 }else{
			return false;
	 }
}

</script>


<script>
	function runAssociatePayrollList(url) {
		$("#usEmployeePayrollDiv").empty();
		var result = "";
		result = result + '<table id="runAssociatePayrollDataTable" class="display" cellspacing="0" width="100%" >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result + '<th>Employee Id</th>';
		result = result + '<th>Employee Name</th>';
		result = result + '<th>Gross Pay</th>';
		result = result + '<th>Pre Tax Deductions</th>';
		result = result + '<th>Federal Tax</th>';
		result = result + '<th>Social Security</th>';
		result = result + '<th>Medicare Withholding</th>';
		result = result + '<th>State Withholding</th>';
		result = result + '<th>SUI/SDI</th>';
		result = result + '<th>Post Tax Deductions</th>';
		result = result + '<th>Net Pay</th>';
		result = result + '</tr>';
		result = result + '</thead>	';
		result = result + "</table>";
		$("#usEmployeePayrollDiv").append(result);
		runAssociatePayrollData(url);

	}
</script>

<script>
	function runAssociatePayrollData(url) {
		var colIndex = 0;
		$('#runAssociatePayrollDataTable')
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
					 			}, {
									"data" : "employeeName"
								}, {
									"data" : "grossPay"
								}, {
									"data" : "preTaxDeductions"
								}, {
									"data" : "federalWithholding"
								},{
									"data" : "socialSecurity"
								},{
									"data" : "medicareWithholding"
								},{
									"data" : "stateWithholding"
								},{
									"data" : "sui"
								},{
									"data" : "postTaxDeductions"
								},{
									"data" : "netPay" 
								}],
							
							 "columnDefs" : [
									{
										"targets" : colIndex++,
										"data" : "employeeId",
										"sortable": false,
										"render" : function(data, type, opt,meta) {
											var result = "";
											if(opt.empId != null && opt.empId != '' && opt.empId != 'undefined'){
												result = result+ ' <td><div class="tblminwidth-125">'+opt.empId+'</div></td>';
											}else{
												result = result+ ' <td><div class="tblminwidth-125">--</div></td>';
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
										
										if(opt.empName != null && opt.empName != '' && opt.empName != 'undefined'){
											result = result+ ' <td><div class="tbldata-dotdot" title="'+opt.empName+'">'+opt.empName+'</div></td>';
										}else{
											result = result+ ' <td><div class="tbldata-dotdot">--</div></td>';
										}
										
										return result;

									}
								},
								{
									"targets" : colIndex++,
									"data" : "grossPay",
									"sortable": false,
									"render" : function(data, type, opt,meta) {
										var result = "";
										
										if(opt.grossPay != null && opt.grossPay != '' && opt.grossPay != 'undefined'){
											result = result+ ' <td><div class="tblminwidth-125">$ '+opt.grossPay+'</div></td>';
										}else{
											result = result+ ' <td><div class="tblminwidth-125">$ 0.0</div></td>';
										}
										
										return result;
									}
								},{
									"targets" : colIndex++,
									"data" : "preTaxDeductions",
									"sortable": false,
									"render" : function(data, type, opt,meta) {
										var result = "";
										
										if(opt.preTaxDeduction != null && opt.preTaxDeduction != '' && opt.preTaxDeduction != 'undefined'){
											result = result+ ' <td><div class="tblminwidth-125">$ '+opt.preTaxDeduction+'</div></td>';
										}else{
											result = result+ ' <td><div class="tblminwidth-125">$ 0.0</div></td>';
										}
										
										return result;
									}
								},{
									"targets" : colIndex++,
									"data" : "federalWithholding",
									"sortable": false,
									"render" : function(data, type, opt,meta) {
										var result = "";
										
										if(opt.totalFederalWithholding != null && opt.totalFederalWithholding != '' && opt.totalFederalWithholding != 'undefined'){
											result = result+ ' <td><div class="tblminwidth-125">$ '+opt.totalFederalWithholding+'</div></td>';
										}else{
											result = result+ ' <td><div class="tblminwidth-125">$ 0.0</div></td>';
										}
										
										return result;
									}
								},{
									"targets" : colIndex++,
									"data" : "socialSecurity",
									"sortable": false,
									"render" : function(data, type, opt,meta) {
										var result = "";
										
										if(opt.totalfica != null && opt.totalfica != '' && opt.totalfica != 'undefined'){
											result = result+ ' <td><div class="tblminwidth-125">$ '+opt.totalfica+'</div></td>';
										}else{
											result = result+ ' <td><div class="tblminwidth-125">$ 0.0</div></td>';
										}
										
										return result;
									}
								},{
									"targets" : colIndex++,
									"data" : "medicareWithholding",
									"sortable": false,
									"render" : function(data, type, opt,meta) {
										var result = "";
										
										if(opt.totalMedicareWithholding != null && opt.totalMedicareWithholding != '' && opt.totalMedicareWithholding != 'undefined'){
											result = result+ ' <td><div class="tblminwidth-125">$ '+opt.totalMedicareWithholding+'</div></td>';
										}else{
											result = result+ ' <td><div class="tblminwidth-125">$ 0.0</div></td>';
										}
									
										return result;
									}
								},{
									"targets" : colIndex++,
									"data" : "stateWithholding",
									"sortable": false,
									"render" : function(data, type, opt,meta) {
										var result = "";
										
										if(opt.totalStateWithholding != null && opt.totalStateWithholding != '' && opt.totalStateWithholding != 'undefined'){
											result = result+ ' <td><div class="tblminwidth-125">$ '+opt.totalStateWithholding+'</div></td>';
										}else{
											result = result+ ' <td><div class="tblminwidth-125">$ 0.0</div></td>';
										}
									
										return result;
									}
								},
								{
									"targets" : colIndex++,
									"data" : "sui",
									"sortable": false,
									"render" : function(data, type, opt,meta) {
										var result = "";
										
										if(opt.totalSui != null && opt.totalSui != '' && opt.totalSui != 'undefined'){
											result = result+ ' <td><div class="tblminwidth-125">$ '+opt.totalSui+'</div></td>';
										}else{
											result = result+ ' <td><div class="tblminwidth-125">$ 0.0</div></td>';
										}
										
										return result;
									}
								},{
									"targets" : colIndex++,
									"data" : "postTaxDeductions",
									"sortable": false,
									"render" : function(data, type, opt,meta) {
										var result = "";
										
										if(opt.postTaxDeduction != null && opt.postTaxDeduction != '' && opt.postTaxDeduction != 'undefined'){
											result = result+ ' <td><div class="tblminwidth-125">$ '+opt.postTaxDeduction+'</div></td>';
										}else{
											result = result+ ' <td><div class="tblminwidth-125">$ 0.0</div></td>';
										}
									
										return result;
									}
								},{
									"targets" : colIndex++,
									"data" : "netPay",
									"sortable": false,
									"render" : function(data, type, opt,meta) {
										var result = "";
										
										if(opt.netPay != null && opt.netPay != '' && opt.netPay != 'undefined'){
											result = result+ ' <td><div class="tblminwidth-125">$ '+opt.netPay+'</div></td>';
										}else{
											result = result+ ' <td><div class="tblminwidth-125">$ 0.0</div></td>';
										}
									
										return result;
									}
									}], 
									
									"scrollCollapse" : false,
									"lengthMenu" : [ [ 10, 15, 20 ], [ 10, 15, 20 ] ],
// 									"aaSorting" : [ [ 3, 'desc' ] ],
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
		$("#runAssociatePayrollDataTable").wrap(
				"<div style='overflow-x: auto; overflow-y: hidden;'></div>");
		$("input[type=search]").hide();
		$("#dynamicUserPayrollTableSearch").keyup(function(e) {
			if (e.which === 13) {
				runAssociatePayrollReport()();
			}
		});
		
	}
	
</script>

<script>
function resetAllData(){
	 $("#payrollRegister").hide();
	 $("#fromDate").val('');
	 $('#toDate').attr('value', '');
		$('#toDate').datepicker( "option" , {
	        minDate: null,
	        maxDate: null} );
	 $("#toDate").val('');
	 $("#employees").val('');
	 $("#employees").trigger("chosen:updated"); 
	 $("#payrollDepartment").val('');
	 $("#payrollDepartment").trigger("chosen:updated"); 
}
</script>

<script>
function exportEmployeePayrollPdf(){
	
	var fromDate = $('#fromDate').val();
	var toDate = $('#toDate').val();
	var department = $('#payrollDepartment').val();
	var employees = $('#employees').val();
	var searchValue = $("#dynamicUserPayrollTableSearch").val();
	searchValue = searchValue ? searchValue : "";

    window.location.href = sourceleadUrl+"/exportEmployeePayroll?"+tokenParamName+"="+tokenValue+"&fromDate="+fromDate+"&toDate="+toDate+"&department="+department+"&employees="+employees+"&searchValue="+searchValue+"&exportType=pdf";
}
</script>

<script>
function exportEmployeePayrollExcel(){
	
	var fromDate = $('#fromDate').val();
	var toDate = $('#toDate').val();
	var department = $('#payrollDepartment').val();
	var employees = $('#employees').val();
	var searchValue = $("#dynamicUserPayrollTableSearch").val();
	searchValue = searchValue ? searchValue : "";

    window.location.href = sourceleadUrl+"/exportEmployeePayroll?"+tokenParamName+"="+tokenValue+"&fromDate="+fromDate+"&toDate="+toDate+"&department="+department+"&employees="+employees+"&searchValue="+searchValue+"&exportType=xls";
}
</script>



  
  
  <jsp:include page="../masterFooter.jsp" />