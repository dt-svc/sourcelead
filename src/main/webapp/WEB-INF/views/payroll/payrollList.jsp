<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- these files for autocomplete with tagit -->
<link href="resources/css/jquery-tagit.css" rel="stylesheet">
<script src="resources/js/csc.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/tag-it.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/tag-it.min.js" type="text/javascript" charset="utf-8"></script>
<!-- end files for autocomplete with tagit -->

<script>
window.onload = function () {
	loadEmployeePayrollCost();
}
</script>

<section class="content-wraper">
<section class="content-header clearfix">
  <div class="pull-left">
    <h2>Expenses New</h2>
  </div>
  <div class="pull-right brud-crum">Home &raquo; Expenses New</div>
</section>

<section class="contrHead">
		<ul id="parentUl">
			<li title="Employer Payroll Cost"><a class="wVer" onclick="loadEmployeePayrollCost();">Employer Payroll Cost</a></li>
			<li title="Wages & Tax Summary"><a onclick="wagesAndTaxList();">Wages & Tax Summary</a></li>
			<li title="Account Deductions"><a onclick="accountDeductionsList();">Account Deductions</a></li>
			<li title="HR Deductions"><a onclick="hrDeductionsList();">HR Deductions</a></li>
		</ul>
		<div class="clearfix"></div>
	</section>
	
<div class="content-area content-new clearfix">
 <div class="project_wrap">
	<div class="clearfix">
	<form:form id="upload_payroll_form" name="upload_payroll_form" method="post">
<div class="timesheet_head clearfix">

     <ul>
     <li>
     <a href="javascript:void(0)" class="addcon-pop">
      	<i class="glyphicon glyphicon-plus paddingRight"></i>Add Document</a>
                  <!-- Popup Div -->
                  <div class="collapse-div-adduser col-md-4" id="adddocDiv" style='width:400px; right: 35px; top:25px;'> <i class="fa fa-close close-div"></i>
                    <div class="form-horizontal clearfix">
                     
                      <div class="clearfix mb10">
                        <div class="col-md-6"><input type="text" class="form-control calendar input-sm" id="fromDate" name="fromDate" value="" placeholder="From Date*" field-name="From Date" data-validation="required"></div>
                        <div class="col-md-6"><input type="text" class="form-control calendar input-sm" id="toDate" name="toDate" value="" placeholder="To Date*" field-name="To Date" data-validation="required"></div>
                      </div>                     
                     
                      <div class="clearfix mb10">
                        <div class="col-md-12">
                          <input type="text" class="form-control input-sm" value="" placeholder="Batch Name" id="batchNumber" name="batchNumber" field-name="Batch name" data-validation="required">
                        </div>
                      </div>
                      <div class="clearfix mb10">
                        <div class="col-md-12 chosenSelect_li">
                          <select id="payrollType" name="payrollType" class="chosen-select" field-name="PayRoll Type" data-validation="required">
                            <option value="">Select Type</option>
                            <option value="EMPLOYEE_PAYROLL">Employee Payroll</option>
                            <option value="WAGE_TAX">Wage & Tax Summary</option>
                            <option value="ACCOUNT_DEDUCTIONS">Account Deductions</option>
                            <option value="HR_DEDUCTIONS">HR Deductions</option>
                          </select>
                        </div>
                        <div class="col-md-12 mt10">
                          <div class="input-group ratetype" id="uploadFileGroup">
	                        <label class="input-group-btn"> <span class="btn btn-primary uploadDiv"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
	                          <input type="file" name="uploadBtn" id="uploadBtn" style="display: none;"  multiple="" class="upload">
	                            </span> </label>
		                          <input type="text" class="form-control" readonly id="uploadFile" name="docFile" placeholder="Upload File." field-name="Attachment" data-validation="required validate_importFile">
	                        </div>
                          
                        </div>
                      </div>
                      <div class="clearfix mr">
                        <button type="button" class="btn btn-sm btn-primary pull-right" id="uploadPayroll" onclick="uploadPayrollDocument();">Upload</button>
                      </div>
                    </div>
                  </div>
                    </form:form>
                  </li>
                  
                  
                  <!-- Start -->
                  
                  <!-- <ul class="popdiv_two"> -->
                  <li>
                  <a href="javascript:void(0)" class="bulk_addconpop"> <i class="glyphicon glyphicon-plus paddingRight"></i>Bulk</a>
                  <!-- Popup Div -->
                  	<form:form id="upload_bulk_payroll_form" name="upload_payroll_form" method="post">
                  <div class="collapse-bulk-adduser col-md-4" id="adddocDiv" style='width:400px; right: 35px; top:25px; padding:10px 0'> <i class="fa fa-close close-div"></i>
                    <div class="form-horizontal clearfix">
                        <div class="col-md-12 mt10">
                          <div class="input-group ratetype" id="uploadBulkFileGroup">
	                        <label class="input-group-btn"> <span class="btn btn-primary uploadDiv"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
	                          <input type="file" name="uploadBulkBtn" id="uploadBulkBtn" style="display: none;"  multiple="" class="upload">
	                            </span> </label>
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
                  
                  </div>
                  
                  <!-- Popup Div --> 
                
                </div>
    <div class="project_tabs_inn_table vendor_wrap_inn_table">
    	<div id="loading"></div>
    	<div id="employerCostDiv" class="datatable_filter"></div>
    	<div id="wagesAndTaxListDiv" class="datatable_filter"></div>
    	<div id="accountDeductionsListDiv" class="datatable_filter"></div>
    	<div id="hrDeductionsListDiv" class="datatable_filter"></div>
       
	</div>
	
 </div>
</div>
</section>


<script>
function loadEmployeePayrollCost(){
	$("#loading").attr('style','position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
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
						$("#employerCostDiv").append(result);
						$("#loading").removeAttr("style");
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
	$(".collapse-div").removeClass("show-div");
});
$(".addcon-pop").click(function() {
	$(".collapse-div-adduser").toggleClass("show-div");
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
		alert("Hi");
		 if ($('#upload_bulk_payroll_form').validate(false, validationSettings)) { 
			$('#adddocDiv').removeClass("show-div");
		var frm = $('#upload_bulk_payroll_form').serializeFormJSON();
		var con = JSON.stringify(frm);
		con = con.replace(/[{}]/g, "");
		var jsonData = encodeURIComponent(con);
		var form = $('#upload_bulk_payroll_form')[0];
		var formData = new FormData(form);

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
					    alert("Hi");
					},
				});
		
		}  
	}
	
</script>


<script type="text/javascript">
	function uploadPayrollDocument() {
		if ($('#upload_payroll_form').validate(false, validationSettings)) { 
			$('#adddocDiv').removeClass("show-div");
		var frm = $('#upload_payroll_form').serializeFormJSON();
		var con = JSON.stringify(frm);
		con = con.replace(/[{}]/g, "");
		var jsonData = encodeURIComponent(con);
		var form = $('#upload_payroll_form')[0];
		var formData = new FormData(form);

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
						loadEmployeePayrollCost();
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
$("ul#parentUl").on("click","li", function(){
	$(this).find('a').addClass("wVer");
	$(this).siblings('li').find('a').removeClass("wVer");
});
</script>

<jsp:include page="wagesAndTaxlist.jsp" />
<jsp:include page="accountDeductionsList.jsp" />
<jsp:include page="hrDeductionsList.jsp" />

<jsp:include page="../masterFooter.jsp" />