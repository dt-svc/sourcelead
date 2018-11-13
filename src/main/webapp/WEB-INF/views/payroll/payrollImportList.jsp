<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script>
	$(document).ready(function() {
		$("#employeeCostSearch").show();
		$("#wageTaxSearch").hide();
		$("#accountDedSearch").hide();
		$("#hrDedSearch").hide();
		$("#checksSearch").hide();
		$("#invoiceDataSearch").hide();
		loadEmployeePayrollCost();
	});
</script>



<section class="content-wraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>
				Pay Roll <a href="<c:url value='${appPath}/uploadDocuments'/>"
					class="jobHeadBtn" id="uloadDocs" title="Upload Files">Upload
					Files</a>
			</h2>
		</div>
		<div class="success_pop" id="selector1" style="display: none;">
			<i class="fa fa-check-circle"></i><span id="msgDiv"></span>
		</div>

		<div class="pull-right brud-crum">
			<a
				href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&raquo;
			<a href="<c:url value='${appPath}/payrollConfiguration'/>">
				Payroll Configuration</a>&raquo; Payroll
		</div>
	</section>


	<%-- <a href="<c:url value='/addChecks'/>" class="ml10 mt5"
		id=generateReportId title="Generate Report">Add Checks</a> --%>
	<form:form id="payroll_list_form" name="payroll_list_form"
		method="post">
		<section class="contrHead">
			<ul id="parentUl">
				<li title="Employer Payroll"><a class="wVer"
					id="empPayrollCostLi" onclick="loadEmployeePayrollCost();">Employer
						Payroll</a></li>
				<!-- <li title="Employee list"><a id="empList" onclick="getEmployeeList();">Master Data</a></li> -->
				<li title="Wages & Tax Summary"><a id="WageTaxSummaryLi"
					onclick="wagesAndTaxList();">Wages & Tax Summary</a></li>
				<li title="Account Deductions"><a id="accountDedLi"
					onclick="accountDeductionsList();">Account Deductions</a></li>
				<li title="HR Deductions"><a id="HrdedLi"
					onclick="hrDeductionsList();">HR Deductions</a></li>
				<li title="Payments"><a id="BisChecks"
					onclick="bisChecksList();">Payments</a></li>
				<li title="Invoice Details"><a id="invoiceData"
					onclick="getInvoiceDetails();">Invoice Details</a></li>
				<li title="Generate Payroll"><a id="paylistLi"
					onclick="showPayrollDiv();">Generate Payroll</a></li>
				<!-- <li><button type="button" class="btn btn-sm btn-primary pull-right" id="generatePayroll" onclick="generatrPayroll();">Generate Payroll</button></li> -->
			</ul>
			<div class="clearfix"></div>
		</section>

	</form:form>
	<div class="content-area content-new backWhite clearfix">
		<!-- <div class="project_wrap"> -->
		<div class="clearfix">
			<div class="clearfix">



				<div class="timesheet_head clearfix">

					<div class="clearfix">

						<a href="<c:url value='${appPath}/userMapping'/>"
							class="btn btn-primary btn-sm ml10 mt5 hidden"
							title="User Mapping" onClick="userMaping();">User Mapping </a>

						<!--  Runpayroll div -->
						<form:form id="run_payroll_cycle" name="run_payroll_cycle"
							method="post">
							<div class="clearfix mt" id="addPayrollDiv">
								<div class="clearfix">
									<div id="payrollCycleDiv" class="col-md-7">
										<div class="form-inline">
											<span class="form-group"> <input type="text"
												class="form-control calendar" value="" id="payrollStartDate"
												name="StartDate" field-name="Start Date"
												data-validation="required" readonly="readonly"
												placeholder="Start Date">
												<p id="reportStartDate" class="ReError"></p>
											</span> <span class="form-group"> <input type="text"
												class="form-control calendar" value="" id="payrollEndDate"
												name="EndDate" field-name="End Date"
												data-validation="required" readonly="readonly"
												placeholder="End Date">
												<p id="reportEndDate" class="ReError"></p>
											</span>

											<div class="form-group">
												<button type="button" class="btn btn-primary btn-block"
													id="runPayrollCycle" onclick="runPayrollCycles();">Run
													Payroll</button>
											</div>
										</div>
									</div>

									<div id="payScale" class="col-md-3 mt5 paddingLeft"
										style="display: none;">
										<span class="label label-info runpayRoll-txt"
											id="payCycleName"></span>
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

		<div
			class="project_tabs_inn_table vendor_wrap_inn_table dataFilterNew">
			<div id="loading"></div>
			<div class='reportHead' id="employeeCostSearch">
				<ul class='pull-right'>
					<li><input type='text' placeholder='Search'
						class='repSearchBar' id='employeeDataTableSearch' /> <span
						class='repSearchIcon' onClick='employeeDataSearch();'></span></li>
				</ul>
			</div>
			<div class='reportHead' id="wageTaxSearch">
				<ul class='pull-right'>
					<li><input type='text' placeholder='Search'
						class='repSearchBar' id='dataTableSearch' /><span
						class='repSearchIcon' onClick='dataTableSearch()'></span></li>
				</ul>
			</div>
			<div class='reportHead' id="accountDedSearch">
				<ul class='pull-right'>
					<li><input type='text' placeholder='Search'
						class='repSearchBar' id='accountDataTableSearch' /><span
						class='repSearchIcon' onClick='accountDataTableSearch();'></span></li>
				</ul>
			</div>
			<div class='reportHead' id="hrDedSearch">
				<ul class='pull-right'>
					<li><input type='text' placeholder='Search'
						class='repSearchBar' id='hrDataTableSearch' /><span
						class='repSearchIcon' onClick='hrDataTableSearch();'></span></li>
				</ul>
			</div>
			<div class='reportHead' id="checksSearch">
				<ul class='pull-right'>
					<li><input type='text' placeholder='Search'
						class='repSearchBar' id='checksDataTableSearch' /><span
						class='repSearchIcon' onClick='checksDataTableSearch();'></span></li>
				</ul>
			</div>

			<div class='reportHead' id="invoiceDataSearch">
				<ul class='pull-right'>
					<li><input type='text' placeholder='Search'
						class='repSearchBar' id='invoiceDataTableSearch' /> <span
						class='repSearchIcon' onClick='invoiceDataTableSearch();'></span></li>
				</ul>
			</div>

			<div id="employerCostDiv" class="datatable_filter"></div>
			<div id="wagesAndTaxListDiv" class="datatable_filter"></div>
			<div id="accountDeductionsListDiv" class="datatable_filter"></div>
			<div id="hrDeductionsListDiv" class="datatable_filter"></div>
			<div id="checksListDiv" class="datatable_filter"></div>
			<div id="employeeListDiv" class="datatable_filter"></div>
			<div id="invoiceDataDiv" class="datatable_filter"></div>
			<div id="finalPayrollReportDiv" class="dtbuttonShow movetopDiv"></div>
		</div>
	</div>
	<div id="popUp"></div>
</section>

<script>
	$(function() {

		$('#payrollStartDate').datepicker({
			dateFormat : 'dd-mm-yy',
			changeMonth : true,
			changeYear : true,
			onSelect : function(selected) {
				var dt = $(this).datepicker('getDate', '+1d');
				dt.setDate(dt.getDate() + 1);
				$("#payrollEndDate").datepicker("option", "minDate", dt);
				addValidClass();
			}
		});

		$('#payrollEndDate').datepicker({
			dateFormat : 'dd-mm-yy',
			changeMonth : true,
			changeYear : true,
			onSelect : function(selected) {
				var dt = $(this).datepicker('getDate', '-1d');
				dt.setDate(dt.getDate() - 1);
				$("#startDate").datepicker("option", "maxDate", dt);
				addValidClass();
			}
		});

	});
</script>

<script type="text/javascript">
	function addValidClass() {
		var StartDate = document.getElementById("payrollStartDate").value;
		if (StartDate) {
			$('#reportStartDate').empty()
		}

		var EndDate = document.getElementById("payrollEndDate").value;
		if (EndDate) {
			$('#reportEndDate').empty();
		}
	}
</script>

<script>
	function runPayrollCycles(type) {

		var payrollValue = $('#payrollCycle').val();
		var payrollStartDate = $('#payrollStartDate').val();
		var payrollEndDate = $('#payrollEndDate').val();
		if (type != "main"
				|| (payrollValue != "" && payrollValue != 'undefined' && payrollValue != null)) {
			if ($('#run_payroll_cycle').validate(false, validationSettings)) {
				$('#addPayrollDiv').removeClass("show-div");
				var payrollCycle = payrollStartDate + " - " + payrollEndDate;
				var frm = $('#run_payroll_cycle').serializeFormJSON();
				var con = JSON.stringify(frm);
				con = con.replace(/[{}]/g, "");
				var jsonData = encodeURIComponent(con);
				var form = $('#run_payroll_cycle')[0];
				var formData = new FormData(form);
				$("#loading")
						.attr(
								'style',
								'position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
				var sourceleadUrl = '${sourceleadUrl}';
				$
						.ajax({
							type : "GET",
							data : formData,
							contentType : 'application/json; charset=utf-8',
							enctype : 'multipart/form-data',
							processData : false,
							contentType : false,
							dataType : 'json',
							url : sourceleadUrl
									+ "/runPayrollCycle?${_csrf.parameterName}=${_csrf.token}&json="
									+ jsonData,
							success : function(result) {
								generatrPayroll(result, payrollCycle);
								$("#loading").removeAttr("style");
							},
						});

			}
		} else if (type == "main") {
			var result = "";
			result = result + "	<div class='project_inn_mask'></div>";
			result = result
					+ "	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
			result = result + "		<p>Please Select Payroll Cycle</p>";
			result = result + "		<div>&nbsp;&nbsp;&nbsp;</div>";
			result = result
					+ "		<b href='javascript:void(0)'><input class='width100percnt' id='confirm-id' type='button' value='Ok' onclick='cancel();'></b>";
			result = result + "	</div>";
			$("#popUp").append(result);
			$("#remove_user_pop").slideDown(200);
			$(".project_inn_mask").fadeIn(200);
		}

	}

	function flushPayrollCycle() {
		$("#payrollCycleDiv").find('p.jquery_form_error_message').remove();
		$('#payrollCycle').val('').trigger("chosen:updated");
	}
</script>

<script>
	function flushValues() {
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
	$("ul#parentUl").on("click", "li", function() {
		$(this).find('a').addClass("wVer");
		$(this).siblings('li').find('a').removeClass("wVer");
	});
</script>

<script>
	function flush() {
		$("#uploadBulkBtn").val("");
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

	$(
			".addcon-pop, .adddoc-pop, .collapse-div-adduser, .collapse-div, .bulk_addconpop")
			.click(function(e) {
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
	function listRedirectBasedOnPayrollType(payrollType) {
		if (payrollType == "EMPLOYEE_PAYROLL") {
			$("#empPayrollCostLi").addClass('wVer');
			$("#WageTaxSummaryLi").removeClass('wVer');
			$("#accountDedLi").removeClass('wVer');
			$("#paylistLi").removeClass('wVer');
			$("#HrdedLi").removeClass('wVer');
			$("#BisChecks").removeClass('wVer');
			$("#invoiceData").removeClass('wVer');

			loadEmployeePayrollCost();
		} else if (payrollType == "WAGE_TAX") {
			$("#empPayrollCostLi").removeClass('wVer');
			$("#WageTaxSummaryLi").addClass('wVer');
			$("#accountDedLi").removeClass('wVer');
			$("#paylistLi").removeClass('wVer');
			$("#HrdedLi").removeClass('wVer');
			$("#BisChecks").removeClass('wVer');
			$("#invoiceData").removeClass('wVer');
			wagesAndTaxList();
		} else if (payrollType == "ACCOUNT_DEDUCTIONS") {
			$("#empPayrollCostLi").removeClass('wVer');
			$("#WageTaxSummaryLi").removeClass('wVer');
			$("#accountDedLi").addClass('wVer');
			$("#paylistLi").removeClass('wVer');
			$("#HrdedLi").removeClass('wVer');
			$("#BisChecks").removeClass('wVer');
			$("#invoiceData").removeClass('wVer');
			accountDeductionsList();
		} else if (payrollType == "HR_DEDUCTIONS") {
			$("#empPayrollCostLi").removeClass('wVer');
			$("#WageTaxSummaryLi").removeClass('wVer');
			$("#accountDedLi").removeClass('wVer');
			$("#paylistLi").removeClass('wVer');
			$("#BisChecks").removeClass('wVer');
			$("#HrdedLi").addClass('wVer');
			$("#invoiceData").removeClass('wVer');
			hrDeductionsList();
		} else if (payrollType == "BIS_CHECKS") {
			$("#empPayrollCostLi").removeClass('wVer');
			$("#WageTaxSummaryLi").removeClass('wVer');
			$("#accountDedLi").removeClass('wVer');
			$("#paylistLi").removeClass('wVer');
			$("#HrdedLi").removeClass('wVer');
			$("#BisChecks").addClass('wVer');
			$("#invoiceData").removeClass('wVer');
			bisChecksList();
		} else if (payrollType == "INVOICE") {
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
	function showAlertMessage() {
		var result = "";
		result = result + "	<div class='project_inn_mask'></div>";
		result = result
				+ "	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
		result = result + "		<p>Upload Correct file</p>";
		result = result + "		<div>&nbsp;&nbsp;&nbsp;</div>";
		result = result
				+ "		<b href='javascript:void(0)'><input class='width100percnt' id='confirm-id' type='button' value='Ok' onclick='cancel();'></b>";
		result = result + "	</div>";
		$("#popUp").append(result);
		$("#remove_user_pop").slideDown(200);
		$(".project_inn_mask").fadeIn(200);

	}
</script>

<script type="text/javascript">
	function cancel() {
		$("#popUp").empty();
		$(".project_inn_mask").fadeOut(200);
	}
</script>

<script>
	function showPayrollDiv() {
		$("#addPayrollDiv").show();
		$("#employerCostDiv").empty();
		$("#wagesAndTaxListDiv").empty();
		$("#accountDeductionsListDiv").empty();
		$("#hrDeductionsListDiv").empty();
		$("#checksListDiv").empty();
		$("#employeeListDiv").empty();
		$("#invoiceDataDiv").empty();
		//runPayrollCycles();

	}
</script>

<script>
	function loadEmployeePayrollCost() {
		$("#employeeCostSearch").show();
		$("#wageTaxSearch").hide();
		$("#accountDedSearch").hide();
		$("#hrDedSearch").hide();
		$("#checksSearch").hide();
		$("#invoiceDataSearch").hide();

		var sourceleadUrl = '${sourceleadUrl}';
		var searchValue = $("#employeeDataTableSearch").val();
		var orgLocationId = '${SourceLeadWeb_orgLocationId}';
		searchValue = searchValue ? searchValue : "";
		var url = sourceleadUrl
				+ "/loadEmployerPayrollCostByOrgLocationId?searchValue="
				+ searchValue + "&orgLocationId=" + orgLocationId;
		prepareEmployeePayrollList(url);
	}
</script>

<script>
	function prepareEmployeePayrollList(url) {
		$("#addPayrollDiv").hide();
		$("#checksListDiv").empty();
		$("#accountDeductionsListDiv").empty();
		$("#hrDeductionsListDiv").empty();
		$("#wagesAndTaxListDiv").empty();
		$("#employeeListDiv").empty();
		$("#invoiceDataDiv").empty();
		$("#finalPayrollReportDiv").empty();
		$("#loading").removeAttr("style");
		$('#payScale').css('display', 'none');

		$("#employerCostDiv").removeAttr("style");
		$("#employerCostDiv").empty();
		$("#payrollCycleDiv").find('p.jquery_form_error_message').remove();
		$('#payrollCycle').val('').trigger("chosen:updated");
		var result = "";
		/* result = result +" <div class='reportHead'>";
		result = result + " <ul class='pull-right'>";
		result = result + " <li><input type='text' placeholder='Search' class='repSearchBar' id='employeeDataTableSearch'/><span class='repSearchIcon' onClick='employeeDataSearch();'></span></li>";
		result = result + "</ul>";
		result = result + "</div>";  */
		result = result
				+ '<table id="employeePayrollTable" class="display" cellspacing="0" width="100%" >';
		result = result + '<thead>';
		result = result + '<tr>';
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
		result = result + '<th>Action</th>';
		result = result + '</tr>';
		result = result + '</thead>	';
		result = result + "</table>";
		$("#employerCostDiv").append(result);
		employeePayrollPagination(url);

	}
</script>

<script>
	function employeePayrollPagination(url) {
		var colIndex = 0;
		$('#employeePayrollTable')
				.DataTable(
						{
							"processing" : true,
							"serverSide" : true,
							"searching" : false,
							"ajax" : $.fn.dataTable.pipeline({
								url : url,
								method : 'POST',
								pages : 10
							}),
							"columns" : [ {
								"data" : "fromDate"
							}, {
								"data" : "toDate"
							}, {
								"data" : "employeeId"
							}, {
								"data" : "employeeName"
							}, {
								"data" : "ssn"
							}, {
								"data" : "totalHrs"
							}, {
								"data" : "grossWages"
							}, {
								"data" : "fica"
							}, {
								"data" : "futa"
							}, {
								"data" : "sui"
							}, {
								"data" : "erLocals"
							}, {
								"data" : "wcCost"
							}, {
								"data" : "adminFee"
							}, {
								"data" : "totalErCost"
							}, {
								"data" : "action"
							} ],
							"columnDefs" : [
									{
										"targets" : colIndex++,
										"data" : "fromDate",
										"sortable" : false,
										"render" : function(data, type, opt,
												meta) {
											var result = "";
											result = result
													+ '<td><div class="tblminwidth-75">'
													+ opt.fromDateStr
													+ '</div></td>';
											return result;
										}
									},
									{
										"targets" : colIndex++,
										"data" : "toDate",
										"sortable" : false,
										"render" : function(data, type, opt,
												meta) {
											var result = "";
											result = result
													+ ' <td><div class="tblminwidth-75">'
													+ opt.toDateStr
													+ '</div></td>';
											return result;

										}
									},
									{
										"targets" : colIndex++,
										"data" : "employeeId",
										"sortable" : false,
										"render" : function(data, type, opt,
												meta) {
											var result = "";
											result = result
													+ '<td class="tblminwidth-75"><div>'
													+ opt.employeeId
													+ '</div></td>';
											return result;
										}
									},
									{
										"targets" : colIndex++,
										"data" : "employeeName",
										"render" : function(data, type, opt,
												meta) {
											var result = "";
											result = result
													+ '<td><div class="tbldata-dotdot-vsm" title="'+opt.employeeName+'">'
													+ opt.employeeName
													+ '</div></td>';
											return result;
										}
									},
									{
										"targets" : colIndex++,
										"data" : "ssn",
										"sortable" : false,
										"render" : function(data, type, opt,
												meta) {
											var result = "";
											if (opt.ssn != null
													&& opt.ssn != ''
													&& opt.ssn != 'undefined') {
												result = result
														+ ' <td><div class="tbldata-dotdot-sm">'
														+ opt.ssn
														+ '</div></td>';
											} else {
												result = result
														+ ' <td><div>--</div></td>';
											}
											return result;
										}
									},
									{
										"targets" : colIndex++,
										"data" : "totalHrs",
										"sortable" : false,
										"render" : function(data, type, opt,
												meta) {
											var result = "";
											if (opt.totalHrs != null
													&& opt.totalHrs != ''
													&& opt.totalHrs != 'undefined') {
												result = result
														+ '<td><div class="tblminwidth-50">'
														+ opt.totalHrs
														+ '</div></td>';
											} else {
												result = result
														+ ' <td><div>--</div></td>';
											}
											return result;
										}
									},
									{
										"targets" : colIndex++,
										"data" : "grossWages",
										"sortable" : false,
										"render" : function(data, type, opt,
												meta) {
											var result = "";
											result = result
													+ ' <td><div class="tblminwidth-125 text-right"><i class="fa fa-dollar"style="color:#454545;"></i> '
													+ opt.grossWages.toFixed(2)
													+ '</div></td>';
											return result;
										}
									},
									{
										"targets" : colIndex++,
										"data" : "fica",
										"sortable" : false,
										"render" : function(data, type, opt,
												meta) {
											var result = "";
											result = result
													+ '<td><div class="tblminwidth-75"><i class="fa fa-dollar"style="color:#454545;"></i> '
													+ opt.fica + '</div></td>';
											return result;
										}
									},
									{
										"targets" : colIndex++,
										"data" : "futa",
										"sortable" : false,
										"render" : function(data, type, opt,
												meta) {
											var result = "";
											result = result
													+ '<td><div class="tblminwidth-75"><i class="fa fa-dollar"style="color:#454545;"></i> '
													+ opt.futa + '</div></td>';
											return result;
										}
									},
									{
										"targets" : colIndex++,
										"data" : "sui",
										"sortable" : false,
										"render" : function(data, type, opt,
												meta) {
											var result = "";
											result = result
													+ ' <td><div class="tblminwidth-75"><i class="fa fa-dollar"style="color:#454545;"></i> '
													+ opt.sui + '</div></td>';
											return result;
										}
									},
									{
										"targets" : colIndex++,
										"data" : "erLocals",
										"sortable" : false,
										"render" : function(data, type, opt,
												meta) {
											var result = "";
											result = result
													+ '<td><div class="tblminwidth-75">'
													+ opt.erLocals
													+ '</div></td>';
											return result;
										}
									},
									{
										"targets" : colIndex++,
										"data" : "wcCost",
										"sortable" : false,
										"render" : function(data, type, opt,
												meta) {
											var result = "";
											result = result
													+ ' <td><div class="tblminwidth-75">'
													+ opt.wcCost
													+ '</div></td>';
											return result;
										}
									},
									{
										"targets" : colIndex++,
										"data" : "adminFee",
										"sortable" : false,
										"render" : function(data, type, opt,
												meta) {
											var result = "";
											result = result
													+ '<td><div class="tblminwidth-100">'
													+ opt.adminFee
													+ '</div></td>';
											return result;
										}
									},
									{
										"targets" : colIndex++,
										"data" : "totalErCost",
										"sortable" : false,
										"render" : function(data, type, opt,
												meta) {
											var result = "";
											result = result
													+ '<td><div class="tblminwidth-150 text-right"><i class="fa fa-dollar"style="color:#454545;"></i> '
													+ opt.totalErCost
													+ '</div></td>';
											return result;
										}
									},
									{
										"targets" : colIndex++,
										"data" : "action",
										"sortable" : false,
										"render" : function(data, type, opt,
												meta) {
											var result = "";
											result = result
													+ '<td><div><i class="glyphicon glyphicon-trash" onclick="deletePayrollEntity(\''
													+ opt.uuid
													+ '\',\'EmployeePayrollCost\')"></i></div></td>';
											return result;
										}
									} ],
							"scrollCollapse" : false,
							"lengthMenu" : [ [ 10, 15, 20 ], [ 10, 15, 20 ] ],
							"aaSorting" : [ [ 3, 'desc' ] ],
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
		$("#employeePayrollTable").wrap(
				"<div style='overflow-x: auto; overflow-y: hidden;'></div>");
		$("input[type=search]").hide();
	}

	$("#employeeDataTableSearch").keyup(function(e) {
		if (e.which === 13) {
			loadEmployeePayrollCost();
		}
	});

	function employeeDataSearch() {
		loadEmployeePayrollCost();
	}

	function deletePayrollEntity(uuid, entityType) {
		var sourceleadUrl = '${sourceleadUrl}';
		$.get(sourceleadUrl + "/deletePayrollEntitiesByUUIDAndEntityType?"
				+ tokenParamName + "=" + tokenValue + "&uuid=" + uuid
				+ "&entityType=" + entityType, function(result) {
			if (entityType == "EmployeePayrollCost") {
				loadEmployeePayrollCost();
			} else if (entityType == "WageTaxSummary") {
				wagesAndTaxList();
			} else if (entityType == "AccountDeductions") {
				accountDeductionsList();
			} else if (entityType == "HrDeductions") {
				hrDeductionsList();
			} else if (entityType == "Checks") {
				bisChecksList();
			} else if (entityType == "InvoiceData") {
				getInvoiceDetails();
			}
		});
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