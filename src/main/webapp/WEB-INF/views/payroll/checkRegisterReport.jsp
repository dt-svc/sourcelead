<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script src="/resources/js/payrollReports.js"></script>

<script>
$(document).ready(function(){
	getPayGroupList("payGroup");
});
</script>

<!-- Content Area -->
  <section class="content-wraper contentWraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Check Register Report</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard'/>" title="Home">Home</a>&raquo;<a
				href="<c:url value='${appPath}/usPayrollReport'/>"> US Payroll Reports List</a>&raquo;Check Register Report
		</div>
    </section>
    <div class="contentareaInner-styles clearfix">
    <div id="loading"></div>
    <form:form id="check_register_report_form" name="check_register_report_form" method="post">
      <div class="clearfix minHeight">
        <div class="clearfix row">
          <div class="col-md-5">
            <div class="form-group">
              <label class="col-md-12 paddingRight">Pay Group<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-12">
                <select class="form-control chosen-select" id="payGroup" name="groupId" field-name="Pay Group" data-validation="required" onchange="getPariodDates();">
                </select>
              </div>
            </div>
          </div>
          <div class="col-md-5">
            <div class="form-group">
              <label class="col-md-12">Pay Period<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-12">
                <select class="form-control chosen-select" id="payPeriod" name="payPeriod" field-name="Pay Period" data-validation="required">
                </select>
                <input type="hidden" id="freqStartDate" name="freqStartDate"/>
                 <input type="hidden" id="freqEndDate" name="freqEndDate"/>
              </div>
            </div>
          </div>
        </div>
        <div class="clearfix footerstyelsNew">
          <div class="col-lg-12">
            <div class="pull-right">
              <input value="Run" class="btn btn-success btnSuccess" type="button" onClick="getCheckRegisterReports();">
              <input value="Reset" class="btn btn-defualt ml10" type="button" onclick="resetForm();">
            </div>
          </div>
        </div>
         </div>
        </form:form>
      </div>
      <div class="clearfix"></div>
      <div class="timesheet_out" id="payrollRegister" style="display: none;">
       
       <div class="clearfix">
                  	<div class="col-md-12">
                  		<div class="row">
                  			<label class="col-md-2">No.of Check:</label>
                  			<span class="col-md-5" id="noOfChecks">0</span>
                  		</div>
                  	</div>
                  	<div class="col-md-12">
                  		<div class="row">
                  			<label class="col-md-2">Employee Live Checks:</label>
                  			<span class="col-md-5" id="liveChecks">0</span>
                  		</div>
                  	</div>
                  	<div class="col-md-12">
                  		<div class="row">
                  			<label class="col-md-2">Void Checks:</label>
                  			<span class="col-md-5">0</span>
                  		</div>
                  	</div>
                  	<div class="col-md-12">
                  		<div class="row">
                  			<label class="col-md-2">Manual Checks:</label>
                  			<span class="col-md-5">0</span>
                  		</div>
                  	</div>

                  	<div class="clearfix mb mt"></div>
       </div>
       
        <div class="styleTables clearfix mt">
        <div class='reportHead'>
		<ul class='pull-right'>
		<li><a class="newAdBtn mr5 hidden" href="/app/userCreation">+  Associate</a></li>
    <!-- <li><a href="javascript:void(0)" class="filtIcon" title="Filters" onclick="openPop();"></a></li> -->
    <li><a href="javascript:void(0)" class="overIcon" title="Actions" onclick="newActions();"></a>
      <div class="newActPop" style="">
          <span>
          <a href="javascript:void(0)" onclick="exportToPdf();" title="PDF">Export PDF</a>
           <!-- <a href="javascript:void(0)" onclick="exportExcel();" title="Excel">Export Excel</a> -->
            </span>
      </div>
    </li>
		<li><input type='text' placeholder='Search' class='repSearchBar' id='dataTableSearch'/>
		<span class='repSearchIcon' onClick='payCheckDateSearch();'></span></li>
		</ul>
		</div>
		 <div class="require_wrap_table tableFirstSecond clearfix datatable_filter">
		 
		 	<div id="popUp"></div>
	          <input type="hidden" id="checkRegisterDataLength" value="0"/>
          <div id="checkRegisterReportDIV" class="datatable_filter"></div>
          </div>
      </div>
      
      <!-- Content Area Ends--> 
    </div>
  </section>
  
  
  
  
  
 <script>
 function resetForm(){
	 $("#payrollRegister").hide();
	 $("#payGroup").val('');
	 $("#payGroup").trigger("chosen:updated"); 
	 $("#payPeriod").empty();
	 $("#payPeriod").trigger("chosen:updated"); 
 }
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
<script type="text/javascript">
	$(document).ready(function(e) {
		$("#savevendor").click(function(){
			$('#myform').validate({
			rules:{
				things:{
					required:true
				}
			}
			});
		});
	});
	$(function () {

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
function runPayrollregister() {
	$("#payrollRegister").toggle();
}
</script>

<script>
function getPariodDates(){
	getPayPeriodByGroupId("payGroup","payPeriod","");
}
</script>


<script type="text/javascript">
function getCheckRegisterReports(){
	 if ($('#check_register_report_form').validate(false, validationSettings)){ 
// 		 runPayrollregister();
		 $("#payrollRegister").show();
		 checkRegisterReports();
		  /* 	var formData = $($('#check_register_report_form')[0]).serialize();
			$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			$.post(sourceleadUrl+"/getCheckRegisterReports?"+tokenParamName+"="+tokenValue, formData, function(result) {
				alert("Done");
			$("#loading").hide();
			}); */ 
			return true;
	 }else{ 
			return false;
	 }   
		
}
$('body').on('blur', '#check_register_report_form', function() {
	$("#check_register_report_form").showHelpOnFocus();
	$("#check_register_report_form").validateOnBlur(false, validationSettings);
});
</script>


<script>
function checkRegisterReports(){
	    var payCheckDate = $('#payCheckDate').val();
	    var groupId = $('#payGroup').val();
	    getFreqdates("freqStartDate", "freqEndDate");
	    var  freqStartDate = $("#freqStartDate").val();
	    var freqEndDate = $("#freqEndDate").val();
		var searchValue = $("#dataTableSearch").val();
		searchValue = searchValue ? searchValue : "";
		var url = sourceleadUrl+"/getCheckRegisterReports?searchValue="+searchValue+"&groupId="+groupId+"&freqStartDate="+freqStartDate+"&freqEndDate="+freqEndDate+"&depositType=Cheque";
		checkRegisterReportsList(url);	
		$("#checkRegisterDataLength").val(0);
}

</script>


<script>
	function checkRegisterReportsList(url) {
		$("#checkRegisterReportDIV").empty();
		var result = "";
		result = result + '<table id="checkRegisterReportTabble" class="display" cellspacing="0" width="100%" >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result + '<th>Employee Id</th>';
		result = result + '<th>Employee Name</th>';
		result = result + '<th>Department</th>';
		result = result + '<th>Amount</th>';
		result = result + '<th>Total Amount</th>';
		result = result + '</tr>';
		result = result + '</thead>	';
		result = result + "</table>";
		$("#checkRegisterReportDIV").append(result);
		$("#noOfChecks").text(0);
		$("#liveChecks").text(0);
		payCheckDateData(url);

	}
</script>

<script>
	function payCheckDateData(url) {
		var colIndex = 0;
		$('#checkRegisterReportTabble')
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
								"data" : "empName"
							}, {
								"data" : "empId"
							}, {
								"data" : "department"
							}, {
								"data" : "netPay"
							},{
								"data" : "netPay"
							}],
							"columnDefs" : [
									{
										"targets" : colIndex++,
										"data" : "empId",
										"sortable": false,
										"render" : function(data, type, opt,meta) {
											$("#checkRegisterDataLength").val(1);
											var result = "";
											if(opt.employeeId != null && opt.employeeId != '' && opt.employeeId != 'undefined'){
												result = result+ ' <td><div class="tblminwidth-75">'+opt.employeeId+'</div></td>';
											}else{
												result = result+ ' <td><div class="tblminwidth-75">--</div></td>';
											}
											$("#noOfChecks").text(opt.totalChecks);
											$("#liveChecks").text(opt.totalChecks);
											return result;
										}
									},
									{
										"targets" : colIndex++,
										"data" : "empName",
										"sortable": false,
										"render" : function(data, type, opt,meta) {
											var result = "";
											if(opt.lastName != null && opt.lastName != '' && opt.lastName != 'undefined'){
												result = result+ '<td><div class="tblminwidth-75">'+opt.firstName+' '+opt.lastName+'</div></td>';
											}else{
												result = result+ '<td><div class="tblminwidth-75">'+opt.firstName+'</div></td>';
											}
											
											return result;

										}
									},
									{
										"targets" : colIndex++,
										"data" : "department",
										"sortable": false,
										"render" : function(data, type, opt,meta) {
											var result = "";
											if(opt.departmentId != null && opt.departmentId != '' && opt.departmentId != 'undefined'){
												result = result + '<td class="tblminwidth-75"><div>'+opt.departmentId+'</div></td>';
											}else{
												result = result + '<td class="tblminwidth-75"><div>--</div></td>';
											}
											
											return result;
										}
									},
									{
										"targets" : colIndex++,
										"data" : "netPay",
										"render" : function(data, type, opt,meta) {
											var result = "";
											result = result+ '<td><div class="tbldata-dotdot-vsm" title=""><i class="fa fa-dollar"style="color:#454545;"></i> '+opt.netPay+'</div></td>';
											return result;
										}
									},
									{
										"targets" : colIndex++,
										"data" : "ytdNet",
										"render" : function(data, type, opt,meta) {
											var result = "";
											result = result+ '<td><div class="tbldata-dotdot-vsm" title=""><i class="fa fa-dollar"style="color:#454545;"></i> '+opt.ytdNet+'</div></td>';
											return result;
										}
									}],
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
		$("#checkRegisterReportTabble").wrap(
				"<div style='overflow-x: auto; overflow-y: hidden;'></div>");
		$("input[type=search]").hide();
		$("#dataTableSearch").keyup(function(e) {
			if (e.which === 13) {
				checkRegisterReports();
			}
		});
		
	}
	function payCheckDateSearch(){
		checkRegisterReports();
	}
	
</script>


<script>
function exportToPdf(){
	
	var groupId = $('#payGroup').val();
    getFreqdates("freqStartDate", "freqEndDate");
    var  freqStartDate = $("#freqStartDate").val();
    var freqEndDate = $("#freqEndDate").val();
	var searchValue = $("#dataTableSearch").val();
	searchValue = searchValue ? searchValue : "";
	var depositType = 'Cheque';
	
	 var checkRegisterDataLength = $("#checkRegisterDataLength").val();
	 
	  if(checkRegisterDataLength > 0){
			window.location.href = sourceleadUrl+"/exportToPdfCheckRegisterReport?searchValue="+searchValue+"&groupId="+groupId+"&freqStartDate="+freqStartDate+"&freqEndDate="+freqEndDate+"&depositType="+depositType;

	   }else{
		   var result = "";
			result = result +"	<div class='project_inn_mask'></div>";
			result = result +"	   <div id='check_register_popup' class='remove_user_pop remove_user_pop_a'>";
			result = result +"		<p>Check Register List is Empty</p>";
			result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
			result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='cancelRegister();' style='width : 100%'></b>";
			result = result +"	</div>";
			$("#popUp").append(result);
			$("#check_register_popup").slideDown(200);
			$(".project_inn_mask").fadeIn(200);
	   }
	
}
</script>

<script>

function cancelRegister(){
 	$("#popUp").empty();
 	$(".project_inn_mask").fadeOut(200);
 }

</script>

<jsp:include page="../masterFooter.jsp" />