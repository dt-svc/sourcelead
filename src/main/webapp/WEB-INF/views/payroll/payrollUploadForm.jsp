<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!-- <script>
window.onload = function () {
	loadEmployeePayrollCost();
}
</script>
 -->
<section class="content-wraper">
<section class="content-header clearfix">
  <div class="pull-left">
    <h2>Pay Roll</h2>
  </div>
  <div class="success_pop" id="selector1" style="display: none;">
		<i class="fa fa-check-circle"></i><span id="msgDiv"></span>
  </div>
  <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Payroll</div>
</section>


<div class="content-area content-new clearfix">
<div class="timesheet_head clearfix">
<div id="payScale" class="pull-left mt10" style="display:none;">
<span class="label label-info runpayRoll-txt" id="payCycleName"></span>
</div>

     <ul class="pull-right noPad">
                  
                  <!-- Start -->
                  
                  <!-- <ul class="popdiv_two"> -->
                  <li class="mr10">
                  	<button type="button" class="btn btn-primary" onClick="downloadTemplate();">Download Template</button>
                  </li>
                  
                  <li class="mr10">
                  <a href="javascript:void(0)" class="bulk_addconpop paddingRight hidden" onclick="flush();"> <i class="glyphicon glyphicon-plus paddingRight"></i>Bulk</a>
                  <a href="<c:url value='${appPath}/userMapping'/>" class="btn btn-primary ml10 ColWhite" title="User Mapping" onClick="userMaping();">User Mapping </a>
                  <!-- Popup Div -->
                  	<form:form id="upload_bulk_payroll_form" name="upload_bulk_payroll_form" method="post">
                  <div class="collapse-bulk-adduser col-md-4 mt" id="addbulkDiv" style='width:400px; right: 110px; top:25px; padding:10px 0'> <i class="fa fa-close close-div"></i>
                    <div class="form-horizontal clearfix">
                        <div class="col-md-12 mt10">
                          <div class="input-group ratetype" id="uploadBulkFileGroup">
	                        <label class="input-group-btn"> <div class="btn btn-primary pull-left"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
	                          <input type="file" name="uploadBulkBtn" id="uploadBulkBtn" style="display: none;"  multiple="" class="upload">
	                            </div> </label>
		                          <input type="text" class="form-control" readonly id="uploadBulkFile" name="docBulkFile" placeholder="Upload Bulk File." field-name="Attachment" data-validation="required validate_importFile">
	                        </div>
                          
                        </div>
                      </div>
                      <div class="clearfix mr mt">
                        <button type="button" class="btn btn-sm btn-primary pull-right" id="uploadBulkPayroll" onclick="uploadBulkPayrollDocument();">Upload</button>
                      </div>
                    </div>
                    </form:form>
                    </li>
                  </ul>
                  </div>
                  <!-- End -->
    
    <!--  import div -->
    <div class="clearfix">
     <div id="loading"></div>
		 <form:form id="upload_payroll_form" name="upload_payroll_form" method="post">
                  <div class="col-md-12" id="adddocDiv">
                    <div class="form-horizontal clearfix myForm mt">
                     
                        <div class="form-group" id="fromDateDiv">
                        	<label class="col-md-3">From Date<span class="text-danger paddingLeft">*</span></label>
                        	<div class="col-md-9">
                        	<input type="text" class="form-control calendar input-sm" id="fromDate" name="fromDate" value="" placeholder="From Date*" field-name="From Date" data-validation="required">
                        	</div>
                        </div>
                        
                        <div class="form-group" id="toDateDiv">
	                        <label class="col-md-3">To Date<span class="text-danger paddingLeft">*</span></label>
	                        <div class="col-md-9">
	                        <input type="text" class="form-control calendar input-sm" id="toDate" name="toDate" value="" placeholder="To Date*" field-name="To Date" data-validation="required">
	                        </div>
                        </div>
                     
                        <div class="form-group" id="batchNumberDiv">
                          <label class="col-md-3">Batch Number<span class="text-danger paddingLeft">*</label>
                          	<div class="col-md-9">
                          		<input type="text" class="form-control input-sm" value="" placeholder="Batch Name" id="batchNumber" name="batchNumber" field-name="Batch name" data-validation="required">
                        	</div>
                        </div>

                      <div class="form-group">
                      <label class="col-md-3">Document Type<span class="text-danger paddingLeft">*</label>
                        <div class="col-md-9 chosenSelect_li" id="payrollTypeDiv">
                          <select id="payrollType" name="payrollType" class="chosen-select" field-name="PayRoll Type" data-validation="required">
                            <option value="">Select Type</option>
                            <option value="EMPLOYEE_PAYROLL">Employer Payroll</option>
                            <option value="WAGE_TAX">Wage & Tax Summary</option>
                            <option value="ACCOUNT_DEDUCTIONS">Account Deductions</option>
                            <option value="HR_DEDUCTIONS">HR Deductions</option>
                             <option value="BIS_CHECKS">Payments</option>
                              <option value="INVOICE">Invoice Data</option>
                          </select>
                        </div>
                        </div>
                        
                        <div class="form-group">
                        <label class="col-md-3">Upload Document<span class="text-danger paddingLeft">*</label>
                        <div class="col-md-9 positionRelative">
                          <div class="input-group ratetype" id="uploadFileGroup">
	                        <label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
	                          <input type="file" name="uploadBtn" id="uploadBtn" style="display: none;"  multiple="" class="upload">
	                            </span> </label>
		                          <input type="text" class="form-control" readonly id="uploadFile" name="docFile" placeholder="Upload File." field-name="Attachment" data-validation="required validate_importFile">

	                        </div>
                          
                        </div>
                        </div>
                      </div>
                      <div class="clearfix mr">
                      <div class="pull-right">
                        <button type="button" class="btn btn-sm btn-default" onclick="cancelUpload();">Cancel</button>
                        <button type="button" class="btn btn-sm btn-primary ml" id="uploadPayroll" onclick="uploadPayrollDocument();">Upload</button>
                      </div></div>
                    </div>
                  </div>
                   </form:form>
		
		
    </div>
    <!--  import div -->
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
		$("#loading").attr('style','position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
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

function cancelUpload(){
	var sourceleadUrl = '${sourceleadUrl}';
	window.location.href = sourceleadUrl + "/payrollImports";
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

<!-- <script>
function loadEmployeePayrollCost(){
	alert("Hai");
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
								result = result + '<td><div class="tbldata-dotdot-vsm">'+opt.employeeName+'</div></td>';
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
								result = result + ' <td><i class="fa fa-dollar paddingLeft text-left pull-left"></i><div class="tblminwidth-125 text-right">'+opt.grossWages+'</div></td>';
								result = result + '<td><div class="tblminwidth-75">'+opt.fica+'</div></td>';
								result = result + '<td><div class="tblminwidth-75">'+opt.futa+'</div></td>';
								result = result + ' <td><div class="tblminwidth-75">'+opt.sui+'</div></td>';
								result = result + '<td><div class="tblminwidth-75">'+opt.erLocals+'</div></td>';
								result = result + ' <td><div class="tblminwidth-75">'+opt.wcCost+'</div></td>';
								result = result + '<td><div class="tblminwidth-100">'+opt.adminFee+'</div></td>';
								result = result + '<td><i class="fa fa-dollar paddingLeft text-left pull-left"></i><div class="tblminwidth-150 text-right">'+opt.totalErCost+'</div></td>';
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
    $("#EmployeePayrollCostTable").wrap("<div class='overflow_Auto'></div>")
}
</script> -->


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
	
	
	
	/* $('html').click(function() {
		$(".collapse-div-adduser").removeClass("show-div");
		$(".collapse-div").removeClass("show-div");
	}); */
</script>

<!-- Added By Rajashekar  -->
<script type="text/javascript">
	function uploadBulkPayrollDocument() {
		 if ($('#upload_bulk_payroll_form').validate(false, validationSettings)) { 
			$('#addbulkDiv').removeClass("show-div");
		var frm = $('#upload_bulk_payroll_form').serializeFormJSON();
		var con = JSON.stringify(frm);
		con = con.replace(/[{}]/g, "");
		var jsonData = encodeURIComponent(con);
		var form = $('#upload_bulk_payroll_form')[0];
		var formData = new FormData(form);
		$("#loading").attr('style','position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		var sourceleadUrl = '${sourceleadUrl}';
		$
				.ajax({
					type : "POST",
					data : formData,
					contentType : 'application/json; charset=utf-8',
					enctype : 'multipart/form-data',
					processData : false,
					contentType : false,
					dataType : 'json',
					url : sourceleadUrl
							+ "/uploadPayrollDocument?${_csrf.parameterName}=${_csrf.token}&json="
							+ jsonData,
					success : function(result) {
					/* 	var json = JSON.parse(result); */
					$("#loading").removeAttr('style');
						$("#selector1").removeAttr("style");
		    			$("#selector1").css("display", "block");
		    			$("#msgDiv").html(result+" Records Imported Successfully.");
						msgFadein();
						location.replace(sourceleadUrl+'/payrollImports');
					},
				});
		
		}  
	}
	
</script>


<script type="text/javascript">
	function uploadPayrollDocument() {
		if ($('#upload_payroll_form').validate(false, validationSettings)) { 
			$('#adddocDiv').removeClass("show-div");
			var payrollType = $("#payrollType").val();
		var frm = $('#upload_payroll_form').serializeFormJSON();
		var con = JSON.stringify(frm);
		con = con.replace(/[{}]/g, "");
		var jsonData = encodeURIComponent(con);
		var form = $('#upload_payroll_form')[0];
		var formData = new FormData(form);
		$("#loading").attr('style','position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		var sourceleadUrl = '${sourceleadUrl}';
		$
				.ajax({
					type : "POST",
					data : formData,
					contentType : 'application/json; charset=utf-8',
					enctype : 'multipart/form-data',
					processData : false,
					contentType : false,
					dataType : 'json',
					url : sourceleadUrl+ "/uploadPayrollDocument?${_csrf.parameterName}=${_csrf.token}&json="+jsonData,
					success : function(result) {
						/* var json = JSON.parse(result); */
						if(result == 0){
							$("#loading").removeAttr('style');
							showAlertMessage();
						}else{
							$("#loading").removeAttr('style');
							$("#selector1").removeAttr("style");
			    			$("#selector1").css("display", "block");
			    			$("#msgDiv").html(result+" Records Imported Successfully.");
							msgFadein();
							location.replace(sourceleadUrl+'/payrollImports');
							
						}
					},
				});
		
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
<!-- <script>
function clearFromDate(){
	$("#fromDate").val("");
	};	
function clearToDate(){
	$("#toDate").val("");
	};	
	/* $(".clearUpload").click(function(){
		$("#uploadFile").val("");
		$("#upload_bulk_payroll_form input[data-id*=uploadBtn]").remove();

	}); */
</script> -->
<div class="sideMask"></div>
 <div class="modal downloadTemplate">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" onClick="clsoerequestPop();">×</button>
        <h4 class="modal-title">Download Template</h4>
      </div>
      <div class="modal-body">
        
        <div class="form-group clearfix">
              <label class="col-md-3">Template Name</label>
            <div class="clearfix dropdown-select col-md-9" id="selectTemplateDiv">
              <select class="sel" id="templateDownload" onchange="changeButtonVal();">
              	<option value="">--Select Template Type--</option>
              	<option value="EMPLOYEE_PAYROLL">Employer Payroll</option>
                <option value="WAGE_TAX">Wage & Tax Summary</option>
                <option value="ACCOUNT_DEDUCTIONS">Account Deductions</option>
                <option value="HR_DEDUCTIONS">HR Deductions</option>
                <option value="BIS_CHECKS">Payments</option>
                <option value="INVOICE">Invoice Data</option>
              </select>
            </div>
        </div>
      </div>
      <div class="modal-footer">
        <a type="button" class="btn btn-primary" id="downloadTemplateBtn">Download</a>
        <button type="button" class="btn btn-default" onClick="clsoerequestPop();">Close</button>
      </div>
    </div>
  </div>
</div>
<script>
function downloadTemplate() {
	$(".downloadTemplate, .sideMask").fadeIn();
}
function clsoerequestPop() {
	$(".downloadTemplate, .sideMask").fadeOut();
}
	$("#uploadBtn").change(function () {
		//var m = $(this).val();
	$('#uploadFile').val($(this).val().replace(/C:\\fakepath\\/i, ''))
})

function changeButtonVal(){
	var templateType = $("#templateDownload").val();
	var fn = "";
	$('#selectTemplateDiv').find('p.jquery_form_error_message').remove();
	if ("EMPLOYEE_PAYROLL" === templateType) {
		fn = '<c:url value="${appPath}/downloadPayrollTemplates?templateType=EMPLOYEE_PAYROLL"/>';
	}else if ("WAGE_TAX" === templateType) {
		fn = '<c:url value="${appPath}/downloadPayrollTemplates?templateType=WAGE_TAX"/>';
	}else if ("ACCOUNT_DEDUCTIONS" === templateType) {
		fn = '<c:url value="${appPath}/downloadPayrollTemplates?templateType=ACCOUNT_DEDUCTIONS"/>';
	}else if ("HR_DEDUCTIONS" === templateType) {
		fn = '<c:url value="${appPath}/downloadPayrollTemplates?templateType=HR_DEDUCTIONS"/>';
	}else if ("BIS_CHECKS" === templateType) {
		fn = '<c:url value="${appPath}/downloadPayrollTemplates?templateType=BIS_CHECKS"/>';
	}else if ("INVOICE" === templateType) {
		fn = '<c:url value="${appPath}/downloadPayrollTemplates?templateType=INVOICE"/>';
	}else if("" == templateType || templateType == null || templateType == 'undefined'){
		$('#selectTemplateDiv').find('p.jquery_form_error_message').remove();
	    $("#templateDownload").after("<p class='jquery_form_error_message'>Select Template type</span>");
	}
	if(fn != "")
	$("#downloadTemplateBtn").attr("href",fn);
}
</script>

<jsp:include page="wagesAndTaxlist.jsp" />
<jsp:include page="accountDeductionsList.jsp" />
<jsp:include page="hrDeductionsList.jsp" />
<jsp:include page="finalPayrollReport.jsp" />
<jsp:include page="checksList.jsp" />
<jsp:include page="../masterFooter.jsp" />