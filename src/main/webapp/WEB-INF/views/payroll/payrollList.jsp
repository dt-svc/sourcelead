<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script>
window.onload = function () {
	loadEmployeePayrollCost();
}
</script>

<section class="content-wraper">
<section class="content-header clearfix">
  <div class="pull-left">
    <h2>Pay Roll</h2>
  </div>
  
  <div class="success_pop" id="selector1" style="display: none;">
		<i class="fa fa-check-circle"></i><span id="msgDiv"></span>
  </div>
       
  <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Payroll</div>
  <div class="pull-left ml generateButtons">
 <a href="<c:url value='/uploadDocuments'/>" class="ml10 mt5" id="uloadDocs" title="upload documents">Upload Files</a>
 <a href="<c:url value='/payrollReport'/>" class="ml10 mt5" id=generateReportId title="Generate Report">Generate Report</a>
 </div>
</section>

<form:form id="payroll_list_form" name="payroll_list_form" method="post">
<section class="contrHead">
		<ul id="parentUl">
			<li title="Employer Payroll Cost"><a class="wVer" id="empPayrollCostLi" onclick="loadEmployeePayrollCost();">Employer Payroll</a></li>
				<li title="Employee list"><a id="empList" onclick="getEmployeeList();">Employee List</a></li>
			<li title="Wages & Tax Summary"><a id="WageTaxSummaryLi" onclick="wagesAndTaxList();">Wages & Tax Summary</a></li>
			<li title="Account Deductions"><a id="accountDedLi" onclick="accountDeductionsList();">Account Deductions</a></li>
			<li title="HR Deductions"><a id="HrdedLi" onclick="hrDeductionsList();">HR Deductions</a></li>
			<li title="Checks"><a id="BisChecks" onclick="bisChecksList();">Bis Checks</a></li>
			<li title="InvoiceDetails"><a id="invoiceData" onclick="getInvoiceDetails();">Invoice Details</a></li>
			<li title="Paylist"><a id="paylistLi" onclick="showPayrollDiv();">Run Payroll</a></li>
			<!-- <li><button type="button" class="btn btn-sm btn-primary pull-right" id="generatePayroll" onclick="generatrPayroll();">Generate Payroll</button></li> -->
		</ul>
		<div class="clearfix"></div>
	</section>
	
	</form:form>
<div class="content-area content-new clearfix">
 <!-- <div class="project_wrap"> -->
 <div class="clearfix">
	<div class="clearfix">
	
<div class="timesheet_head clearfix">

<div class="pull-left mr">

<a href="<c:url value='/userMapping'/>" class="btn btn-primary btn-sm ml10 mt5 hidden" title="User Mapping" onClick="userMaping();">User Mapping </a>

<!--  Runpayroll div -->
<form:form id="run_payroll_cycle" name="run_payroll_cycle" method="post">
                  <div class="col-md-4 mt" id="addPayrollDiv" style='width:600px; right: 0px; top:0px; padding:10px 0 display:none;'>
                    <div class="form-horizontal clearfix row">
                        <div class="col-md-6 dropdown-select paddingRight" id="payrollCycleDiv">
							<select class="chosen-select" id="payrollCycle" name="payrollCycle"  field-name="Payroll Cycle"  data-validation="required">
							<option value="">---Select Payroll Cycle---</option>
							<option value="2">Mar 1st - Mar 15th</option>
							<option value="3_FIRST">Mar 15th - April 1st</option>
							<option value="3">April 1st - April 15th</option>
							<option value="4_FIRST">April 15th - May 1st</option>
							<option value="4">May 1st - May 15th</option>
							<option value="5_FIRST">May 15th - June 1st</option>
							<option value="5">June 1st - June 15th</option>
							<option value="6_FIRST">June 15th - July 1st</option>
							<option value="6">July 1st - July 15th</option>
							<option value="7_FIRST">July 15th - Aug 1st</option>
							<option value="7">Aug 1st - Aug 15th</option>
							<option value="8_FIRST">Aug 15th - Sep 1st</option>
							<option value="8">Sep 1st - Sep 15th</option>
							<option value="9_FIRST">Sep 15th - Oct 1st</option>
							<option value="9">Oct 1st - Oct 15th</option>
							<option value="10_FIRST">Oct 15th - Nov 1st</option>
							<option value="10">Nov 1st - Nov 15th</option>
							<option value="11_FIRST">Nov 15th - Dec 1st</option>
							<option value="11">Dec 1st - Dec 15th</option>
							</select>
                        </div>
                      <div class="col-md-2 paddingLeft">
                        <button type="button" class="btn btn-primary" id="runPayrollCycle" onclick="runPayrollCycles();">Run Payroll</button>
                      </div>
                      <div id="payScale" class="col-md-4 mt5" style="display:none;">
						<span class="label label-info runpayRoll-txt" id="payCycleName"></span>
						</div>
                      </div>
                      
                    </div>
</form:form>
<!--  Runpayroll div -->
</div>
                  </div>
                  <!-- End -->
                  
                  </div>
                  
                  <!-- Popup Div --> 
                
                </div>
    
    <div class="project_tabs_inn_table vendor_wrap_inn_table dataFilterNew pdtop">
    	<div id="loading"></div>
    	<div id="employerCostDiv" class="datatable_filter"></div>
    	<div id="wagesAndTaxListDiv" class="datatable_filter"> </div>
    	<div id="accountDeductionsListDiv" class="datatable_filter"></div> 
    	<div id="hrDeductionsListDiv" class="datatable_filter"></div>
    	<div id="checksListDiv" class="datatable_filter"></div>
    	<div id="employeeListDiv" class="datatable_filter"></div>
    	<div id="invoiceDataDiv" class="datatable_filter"></div>
    	<div id="finalPayrollReportDiv" class="dtbuttonShow movetopDiv"></div>
	</div>
 </div>
 <div id="popUp">
</div>
</section>


<script>
function runPayrollCycles(type){
	var payrollValue = $('#payrollCycle').val();
	if(type != "main" || (payrollValue != "" && payrollValue != 'undefined' && payrollValue != null)){
		if ($('#run_payroll_cycle').validate(false, validationSettings)) { 
			$('#addPayrollDiv').removeClass("show-div");
			var payrollCycle = $('#payrollCycle :selected').text();
		var frm = $('#run_payroll_cycle').serializeFormJSON();
		var con = JSON.stringify(frm);
		con = con.replace(/[{}]/g, "");
		var jsonData = encodeURIComponent(con);
		var form = $('#run_payroll_cycle')[0];
		var formData = new FormData(form);
		$("#loading").attr('style','position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		var sourceleadUrl = '${sourceleadUrl}';
		$.ajax({
					type : "GET",
					data : formData,
					contentType : 'application/json; charset=utf-8',
					enctype : 'multipart/form-data',
					processData : false,
					contentType : false,
					dataType : 'json',
					url : sourceleadUrl+ "/runPayrollCycle?${_csrf.parameterName}=${_csrf.token}&json="+jsonData,
					success : function(result) {
						generatrPayroll(result,payrollCycle);
						$("#loading").removeAttr("style");
					},
				});
		
		} 
	}else if(type == "main"){
		var result = "";
		result = result +"	<div class='project_inn_mask'></div>";
		result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
		result = result +"		<p>Please Select Payroll Cycle</p>";
		result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
		result = result +"		<b href='javascript:void(0)'><input class='width100percnt' id='confirm-id' type='button' value='Ok' onclick='cancel();'></b>";
		result = result +"	</div>";
		$("#popUp").append(result);
		$("#remove_user_pop").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
	}
	 
}

function flushPayrollCycle(){
	$("#payrollCycleDiv").find('p.jquery_form_error_message').remove();
	$('#payrollCycle').val('').trigger("chosen:updated");
}

</script>

<script>
function flushValues(){
	$("#fromDate").val("");
	$("#toDate").val("");
	$("#payrollType").val('').trigger("chosen:updated");
	$("#batchNumber").val("");
	$("#uploadFile").val("");
	
	$("#fromDateDiv").find('p.jquery_form_error_message').remove();
	$("#toDateDiv").find('p.jquery_form_error_message').remove();
	$("#payrollTypeDiv").find('p.jquery_form_error_message').remove();
	$("#batchNumberDiv").find('p.jquery_form_error_message').remove();
	$("#uploadFileGroup").find('p.jquery_form_error_message').remove();
	
	
}

</script>

<script>
$("ul#parentUl").on("click","li", function(){
	$(this).find('a').addClass("wVer");
	$(this).siblings('li').find('a').removeClass("wVer");
});
</script>

<script>
function flush(){
	$("#uploadBulkBtn").val("");	
}
</script>

<script>
function loadEmployeePayrollCost(){
	$("#addPayrollDiv").hide();
	$("#loading").attr('style','position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		  var sourceleadUrl ='${sourceleadUrl}';
				  $.ajax({
				    	type: "GET",
				        url: sourceleadUrl+"/loadEmployerPayrollCostByOrgLocationId?${_csrf.parameterName}=${_csrf.token}",
				        success: function(data) {
						var result = "";				        	
						result = result + '<table id="EmployeePayrollCostTable" class="display" cellspacing="0" width="100%" >';
						result = result + '<thead>';
						result = result + '<tr>';
						//Employee	Emp ID	SSN	Total Hrs	Gross Wages	FICA	FUTA	SUI	ER Locals	WC Cost	Admin Fee	Total ER Cost	
						result = result + '<th>From Date</th>';
						result = result + '<th>To Date</th>';
						result = result + '<th>Employee Id</th>';
						result = result + '<th>Employee Name</th>';
						result = result + '<th>SSN</th>';
						result = result + '<th>Total Hrs</th>';
						result = result + '<th>Gross Wages</th>';
						result = result + '<th>FICA</th>';
						result = result + '<th>FUTA</th>';
						result = result + '<th>SUI</th>';
						result = result + '<th>ER Locals</th>';
						result = result + '<th>WC Cost</th>';
						result = result + '<th>Admin Fee</th>';
						result = result + '<th>Total ER Cost</th>';
						result = result + '</tr>';
						result = result + '</thead>	';
						result = result + '<tbody>';
						 var json = JSON.parse(data);
			        	 for (var i=0; i<json.length; i++)
						{
			        		 var opt = json[i];
			        		    result = result + ' <tr>';
								result = result + '<td><div class="tblminwidth-75">'+opt.fromDateStr+'</div></td>';
								result = result + ' <td><div class="tblminwidth-75">'+opt.toDateStr+'</div></td>';
								result = result + '<td class="tblminwidth-75"><div>'+opt.employeeId+'</div></td>';
								result = result + '<td><div class="tbldata-dotdot-vsm" title="'+opt.employeeName+'">'+opt.employeeName+'</div></td>';
								if(opt.ssn !=null && opt.ssn !='' && opt.ssn !='undefined'){
									result = result + ' <td><div class="tbldata-dotdot-sm">'+opt.ssn+'</div></td>';
								}else{
									result = result + ' <td><div>--</div></td>';
								}
								if(opt.totalHrs !=null && opt.totalHrs !='' && opt.totalHrs !='undefined'){
									result = result + '<td><div class="tblminwidth-50">'+opt.totalHrs+'</div></td>';
								}else{
									result = result + ' <td><div>--</div></td>';
								}
								result = result + ' <td><div class="tblminwidth-125 text-right"><i class="fa fa-dollar"style="color:#454545;"></i> '+opt.grossWages.toFixed(2)+'</div></td>';
								result = result + '<td><div class="tblminwidth-75"><i class="fa fa-dollar"style="color:#454545;"></i> '+opt.fica+'</div></td>';
								result = result + '<td><div class="tblminwidth-75"><i class="fa fa-dollar"style="color:#454545;"></i> '+opt.futa+'</div></td>';
								result = result + ' <td><div class="tblminwidth-75"><i class="fa fa-dollar"style="color:#454545;"></i> '+opt.sui+'</div></td>';
								result = result + '<td><div class="tblminwidth-75">'+opt.erLocals+'</div></td>';
								result = result + ' <td><div class="tblminwidth-75">'+opt.wcCost+'</div></td>';
								result = result + '<td><div class="tblminwidth-100">'+opt.adminFee+'</div></td>';
								result = result + '<td><div class="tblminwidth-150 text-right"><i class="fa fa-dollar"style="color:#454545;"></i> '+opt.totalErCost+'</div></td>';
								result = result + ' </tr>';
						}
						result = result + '  </table>';
						result = result + ' </tbody>';
						$("#employerCostDiv").empty();
						$("#wagesAndTaxListDiv").empty();
						$("#accountDeductionsListDiv").empty();
						$("#hrDeductionsListDiv").empty();
						$("#wagesAndTaxListDiv").empty();
						$("#checksListDiv").empty();
						$("#finalPayrollReportDiv").empty();
						$("#employeeListDiv").empty();
						$("#invoiceDataDiv").empty();
						$("#employerCostDiv").append(result);
						$("#loading").removeAttr("style");
						$('#payScale').css('display','none');
						employeePayrollCostPagination();
				        	
				        },
				        
				    });
		 
}
</script>

<script>
function employeePayrollCostPagination(){ 
    $('#EmployeePayrollCostTable').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    });
    $("#EmployeePayrollCostTable").wrap("<div class='overflow_Auto width100percnt'></div>")
}
</script>




<script>
	$(function() {
		$('#fromDate').datepicker({
			changeMonth : true,
			changeYear : true,
			dateFormat : 'dd-mm-yy',
			onSelect : function(selected) {
				$("#toDate").datepicker("option", "minDate", selected);
			}

		});

		$('#toDate').datepicker({
			changeMonth : true,
			changeYear : true,
			dateFormat : 'dd-mm-yy',
			onSelect : function(selected) {
				$("#fromDate").datepicker("option", "maxDate", selected);
			}
		});

	});
</script>


<script>
//bulk_addconpop script
$(".bulk_addconpop").click(function() {
	$(".collapse-bulk-adduser").toggleClass("show-div");
	$(".collapse-div-adduser").removeClass("show-div");
	$(".runpayroll_div").removeClass("show-div");
	$(".collapse-div").removeClass("show-div");
});
//Run Payroll script
$(".runpayroll").click(function() {
	$(".runpayroll_div").toggleClass("show-div");
	$(".collapse-bulk-adduser").removeClass("show-div");
	$(".collapse-div-adduser").removeClass("show-div");
	$(".collapse-div").removeClass("show-div");
});
$(".addcon-pop").click(function() {
	$(".collapse-div-adduser").toggleClass("show-div");
	$(".runpayroll_div").removeClass("show-div");
	$(".collapse-bulk-adduser").removeClass("show-div");
	$(".collapse-div").removeClass("show-div");
});
	
	$(".addcon-pop, .adddoc-pop, .collapse-div-adduser, .collapse-div, .bulk_addconpop").click(
			function(e) {
				e.stopPropagation();
			});
	
	$(".close-div").click(function() {
		$(this).parent().removeClass("show-div");
		$("#fromDate").val("");
		$("#toDate").val("");
		$("#payrollType").val('').trigger("chosen:updated");
		$("#batchNumber").val("");
		$("#uploadFile").val("");
	});
	
	
	
</script>

<!-- Added By Rajashekar  -->



<script type="text/javascript">
	function listRedirectBasedOnPayrollType(payrollType){
		if(payrollType == "EMPLOYEE_PAYROLL"){
			$("#empPayrollCostLi").addClass('wVer');
			$("#WageTaxSummaryLi").removeClass('wVer');
			$("#accountDedLi").removeClass('wVer');
			$("#paylistLi").removeClass('wVer');
			$("#HrdedLi").removeClass('wVer');
			$("#BisChecks").removeClass('wVer');
			$("#invoiceData").removeClass('wVer');
			loadEmployeePayrollCost();
		}else if(payrollType == "WAGE_TAX"){
			$("#empPayrollCostLi").removeClass('wVer');
			$("#WageTaxSummaryLi").addClass('wVer');
			$("#accountDedLi").removeClass('wVer');
			$("#paylistLi").removeClass('wVer');
			$("#HrdedLi").removeClass('wVer');
			$("#BisChecks").removeClass('wVer');
			$("#invoiceData").removeClass('wVer');
			wagesAndTaxList();
		}else if(payrollType == "ACCOUNT_DEDUCTIONS"){
			$("#empPayrollCostLi").removeClass('wVer');
			$("#WageTaxSummaryLi").removeClass('wVer');
			$("#accountDedLi").addClass('wVer');
			$("#paylistLi").removeClass('wVer');
			$("#HrdedLi").removeClass('wVer');
			$("#BisChecks").removeClass('wVer');
			$("#invoiceData").removeClass('wVer');
			accountDeductionsList();
		}else if(payrollType == "HR_DEDUCTIONS"){
			$("#empPayrollCostLi").removeClass('wVer');
			$("#WageTaxSummaryLi").removeClass('wVer');
			$("#accountDedLi").removeClass('wVer');
			$("#paylistLi").removeClass('wVer');
			$("#BisChecks").removeClass('wVer');
			$("#HrdedLi").addClass('wVer');
			$("#invoiceData").removeClass('wVer');
			hrDeductionsList();
		}else if(payrollType == "BIS_CHECKS"){
			$("#empPayrollCostLi").removeClass('wVer');
			$("#WageTaxSummaryLi").removeClass('wVer');
			$("#accountDedLi").removeClass('wVer');
			$("#paylistLi").removeClass('wVer');
			$("#HrdedLi").removeClass('wVer');
			$("#BisChecks").addClass('wVer');
			$("#invoiceData").removeClass('wVer');
			bisChecksList();
		}else if(payrollType == "INVOICE"){
			$("#empPayrollCostLi").removeClass('wVer');
			$("#WageTaxSummaryLi").removeClass('wVer');
			$("#accountDedLi").removeClass('wVer');
			$("#paylistLi").removeClass('wVer');
			$("#HrdedLi").removeClass('wVer');
			$("#BisChecks").removeClass('wVer');
			$("#invoiceData").addClass('wVer');
			getInvoiceDetails();
		}
		
	}
	
</script>

<script type="text/javascript">
	$(document.body)
			.on(
					'change',
					'#uploadBtn',
					function() {
						$('#uploadFile').val(this.value.split('\\').pop());
						$("#uploadFile").focus();
						if (validate_fileupload(this.value)) {
							var docFileSize = $('#uploadFile');
							var docSize = this.files[0].size;
							$("#uploadFileGroup").find(
									'p.jquery_form_error_message').remove();
							if (docSize > 2097152) {
								document.getElementById("uploadFile").value = '';
								$('#uploadFileGroup').find(
										'p.jquery_form_error_message').remove();
								$("input#uploadFile")
										.after(
												"<p class='jquery_form_error_message'>Limit is exceeded than 2MB </span>");
								document.getElementById("uploadFile")
										.setAttribute('record-exist', 'yes');
								document.getElementById("uploadFile")
										.setAttribute('record-exist-errorMsg',
												'Limit is exceeded than 2MB');
							} else {
								$('input#uploadFile').removeAttr(
										"record-exist record-exist-errormsg");
								$('#uploadFileGroup').find(
										'p.jquery_form_error_message').remove();
							}
						}
					});
</script>


<script type="text/javascript">
	$(document.body)
			.on(
					'change',
					'#uploadBulkBtn',
					function() {
						$('#uploadBulkFile').val(this.value.split('\\').pop());
						$("#uploadBulkFile").focus();
						if (validate_fileupload(this.value)) {
							var docFileSize = $('#uploadBulkFile');
							var docSize = this.files[0].size;
							$("#uploadBulkFileGroup").find(
									'p.jquery_form_error_message').remove();
							if (docSize > 2097152) {
								document.getElementById("uploadBulkFile").value = '';
								$('#uploadBulkFileGroup').find(
										'p.jquery_form_error_message').remove();
								$("input#uploadBulkFile")
										.after(
												"<p class='jquery_form_error_message'>Limit is exceeded than 2MB </span>");
								document.getElementById("uploadBulkFile")
										.setAttribute('record-exist', 'yes');
								document.getElementById("uploadBulkFile")
										.setAttribute('record-exist-errorMsg',
												'Limit is exceeded than 2MB');
							} else {
								$('input#uploadBulkFile').removeAttr(
										"record-exist record-exist-errormsg");
								$('#uploadBulkFileGroup').find(
										'p.jquery_form_error_message').remove();
							}
						}
					});
</script>

<script>
	function msgFadein() {
		$("#selector1").fadeIn(200);
        $('#selector1').delay(2000).fadeOut('slow');
	}
</script> 

<script type="text/javascript">
function showAlertMessage(){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Upload Correct file</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input class='width100percnt' id='confirm-id' type='button' value='Ok' onclick='cancel();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);

}
</script>

<script type="text/javascript">
function cancel(){ 
	$("#popUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>

<script>
function showPayrollDiv(){
	$("#addPayrollDiv").show();
	$("#employerCostDiv").empty();
	$("#wagesAndTaxListDiv").empty();
	$("#accountDeductionsListDiv").empty();
	$("#hrDeductionsListDiv").empty();
	$("#checksListDiv").empty();
	$("#employeeListDiv").empty();
	runPayrollCycles();
	
}
</script>


<jsp:include page="wagesAndTaxlist.jsp" />
<jsp:include page="accountDeductionsList.jsp" />
<jsp:include page="hrDeductionsList.jsp" />
<jsp:include page="finalPayrollReport.jsp" />
<jsp:include page="checksList.jsp" />
<jsp:include page="invoiceData.jsp" />
<jsp:include page="employeesList.jsp" />
<jsp:include page="../masterFooter.jsp" />