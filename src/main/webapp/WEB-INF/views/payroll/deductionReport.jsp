<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script src="/resources/js/payrollReports.js"></script>

<script>
$(document).ready(function(){
	getPayGroupList("payGroup");
	getDepartments("departmentId");
	getDeductionType();
});
</script>

  <!-- Content Area -->
  <section class="content-wraper contentWraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Deduction</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard'/>" title="Home">Home</a>&raquo;<a
				href="<c:url value='${appPath}/usPayrollReport'/>"> US Payroll Reports List</a>&raquo;Deduction
		</div>
    </section>
    
    <div class="contentareaInner-styles clearfix">
    
    <div class="clearfix minHeight">
     <form:form id="deduction_report_form" name="deduction_report_form" method="post">
        <div class="clearfix row">
          <div class="col-md-5">
            <div class="form-group">
              <label class="col-md-12 paddingRight">Pay Group<span class="text-danger">&nbsp;*</span></label>
				<div class="col-md-12">
					<select class="form-control chosen-select" id="payGroup" name="payGroup" onchange="getPayPeriod();" field-name="Pay Group" data-validation="required">
				<!-- 	<option>Select Group</option>
						<option>Monthly</option>
						<option>Weekly</option> -->
					</select>
				</div>
            </div>
            <div class="form-group">
              <label class="col-md-12">Deduction Type<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-12">
                <select class="form-control chosen-select" multiple data-placeholder="Deduction Type" id="deductionId" name="deductionId" field-name="Deduction Type" data-validation="required">
					 <!--    <option value="">Select Type</option>
						<option value="Regular">Regular</option>
						<option value="Additional">Additional</option> -->
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
             <div class="form-group">
              <label class="col-md-12">Department</label>
              <div class="col-md-12">
                <select class="form-control chosen-select" id="departmentId" name="departmentId">
					 <!--    <option>Select Department</option>
						<option>Department-1</option>
						<option>Department-2</option>
						<option>Department-3</option> -->
					</select>
              </div>
            </div>
        </div>
      </div>
	<div class="clearfix footerstyelsNew">
        <div class="col-lg-12">
          <div class="pull-right">
            <input value="Run" class="btn btn-success btnSuccess" type="button" onClick="deductionReport();"> <!-- runPayrollregister -->
            <input value="Reset" class="btn btn-defualt ml10" type="button" onclick="resetDeductionReport();">
          </div>
        </div>
      </div>     
       </form:form> 
	</div>
    
     <div class="clearfix"></div>
    		
    		
    		
    		
  <div class="timesheet_out" id="payrollRegister" style="display: none;">
      <div class="clearfix">
        
        <div class="positionRelative reportHead clearfix">
  <ul class="pull-right list-unstyled list-inline">
    <li><a class="newAdBtn mr5 hidden" href="/app/userCreation">+  Associate</a></li>
    <!-- <li><a href="javascript:void(0)" class="filtIcon" title="Filters" onclick="openPop();"></a></li> -->
    <li><a href="javascript:void(0)" class="overIcon" title="Actions" onclick="newActions();"></a>
      <div class="newActPop" style="">
       <span><a href="javascript:void(0)" onclick="deductionReportExportToPdf();" title="PDF">Export PDF</a> <!-- <a href="javascript:void(0)" onclick="exportUserData();" title="Excel">Export Excel</a>  --></span> </div>
    </li>
    <li class="">
      <input class="repSearchBar" placeholder="Search" id="dataTableSearch" type="text">
      <span class="repSearchIcon" onclick="deductionReportSearch();"></span></li>
  </ul>
</div>
        <div class="require_wrap_table tableFirstSecond clearfix">
           <div class="styleTables clearfix mt">
          <div id="deductionReportDIV" class="datatable_filter"></div>
      </div>
      </div>
    </div>
    		
     		
      <!-- Content Area Ends--> 
    </div>
    
  </section>
  
 <script>
 function getPayPeriod(){
	 getPayPeriodByGroupId("payGroup","payPeriod");
 }
 
 function resetDeductionReport(){
	 $("#payrollRegister").hide();
	 $("#payGroup").val('');
	 $("#payGroup").trigger("chosen:updated"); 
	 $("#payPeriod").empty();
	 $("#payPeriod").trigger("chosen:updated"); 
	 $("#deductionId").val('');
	 $("#deductionId").trigger("chosen:updated"); 
	 $("#departmentId").val('');
	 $("#departmentId").trigger("chosen:updated"); 
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
/* function runPayrollregister() {
	$("#payrollRegister").toggle();
} */
</script>    



<div id="popUp"></div>
<script type="text/javascript">
function deductionReport(){
	 if ($('#deduction_report_form').validate(false, validationSettings)){ 
		 /* runPayrollregister();    */
		 getDeductionReports(); 
			return true;
	 }else{ 
			return false;
	 }   
		
}
$('body').on('blur', '#deduction_report_form', function() {
	$("#deduction_report_form").showHelpOnFocus();
	$("#deduction_report_form").validateOnBlur(false, validationSettings);
});
</script>


<script>
function getDeductionReports(){
	    var groupId = $('#payGroup').val();
	    getFreqdates("freqStartDate", "freqEndDate");
	    var  freqStartDate = $("#freqStartDate").val();
	    var freqEndDate = $("#freqEndDate").val();
	    var deductionId = $("#deductionId").val();
		var searchValue = $("#dataTableSearch").val();
		searchValue = searchValue ? searchValue : "";
		var url = sourceleadUrl+"/getDeductionReports?searchValue="+searchValue+"&groupId="+groupId+"&freqStartDate="+freqStartDate+"&freqEndDate="+freqEndDate+"&deductionId="+deductionId;
		deductionReportURL(url);	
}

</script>


<script>
	function deductionReportURL(url) {
		$("#payrollRegister").show();
		$("#deductionReportDIV").empty();
		var result = "";
		result = result + '<table id="deductionReportTabble" class="display" cellspacing="0" width="100%" >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result + '<th>Type</th>';
		result = result + '<th>Employee Id</th>';
		result = result + '<th>Employee Name</th>';
		result = result + '<th>Department</th>';
		result = result + '<th>Schedule</th>';
		result = result + '<th>Amount</th>';
		result = result + '<th>YTD</th>';
		result = result + '</tr>';
		result = result + '</thead>	';
		result = result + "</table>";
		$("#deductionReportDIV").append(result);
		payCheckDateData(url);

	}
</script>

<script>
	function payCheckDateData(url) {
		var colIndex = 0;
		$('#deductionReportTabble')
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
								"data" : "type"
							}, {
								"data" : "empId"
							}, {
								"data" : "empName"
							}, {
								"data" : "departmentName"
							}, {
								"data" : "schedule"
							}, {
								"data" : "dedAmount"
							}, {
								"data" : "ytdDeductions"
							}],
							"columnDefs" : [
								{
									"targets" : colIndex++,
									"data" : "type",
									"sortable": true,
									"render" : function(data, type, opt,meta) {
										     var result = "";
											result = result+ ' <td><div class="tblminwidth-75" id="deductionTypeDiv">'+opt.deductionType+'</div></td>';
										
										return result;
									}
								},
									{
										"targets" : colIndex++,
										"data" : "empId",
										"sortable": false,
										"render" : function(data, type, opt,meta) {
											
											var result = "";
											if(opt.empId != null && opt.empId != '' && opt.empId != 'undefined'){
												result = result+ ' <td><div class="tblminwidth-75">'+opt.empId+'</div></td>';
											}else{
												result = result+ ' <td><div class="tblminwidth-75">--</div></td>';
											}
											return result;
										}
									},
									{
										"targets" : colIndex++,
										"data" : "empName",
										"sortable": true,
										"render" : function(data, type, opt,meta) {
											var result = "";
												result = result+ '<td><div class="tblminwidth-75">'+opt.empName+'</div></td>';
											return result;

										}
									},
									{
										"targets" : colIndex++,
										"data" : "departmentName",
										"sortable": true,
										"render" : function(data, type, opt,meta) {
											var result = "";
											if(opt.departmentName != null && opt.departmentName != '' && opt.departmentName != 'undefined'){
												result = result + '<td class="tblminwidth-75"><div>'+opt.departmentName+'</div></td>';
											}else{
												result = result + '<td class="tblminwidth-75"><div>--</div></td>';
											}
											return result;
										}
									},
									{
										"targets" : colIndex++,
										"data" : "schedule",
										"sortable": false,
										"render" : function(data, type, opt,meta) {
											var result = "";
											result = result+ '<td><div class="tbldata-dotdot-vsm" title="">'+opt.schedule+'</div></td>';
											return result;
										}
									},{
										"targets" : colIndex++,
										"data" : "dedAmount",
										"sortable": false,
										"render" : function(data, type, opt,meta) {
											var result = "";
											result = result+ '<td><div class="tbldata-dotdot-vsm" title=""><i class="fa fa-dollar"style="color:#454545;"></i> '+opt.dedAmount+'</div></td>';
											return result;
										}
									},{
										"targets" : colIndex++,
										"data" : "ytdDeductions",
										"render" : function(data, type, opt,meta) {
											var result = "";
											result = result+ '<td><div class="tbldata-dotdot-vsm" title=""><i class="fa fa-dollar"style="color:#454545;"></i> '+opt.deductionYTD+'</div></td>';
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
		$("#deductionReportTabble").wrap(
				"<div style='overflow-x: auto; overflow-y: hidden;'></div>");
		$("input[type=search]").hide();
		$("#dataTableSearch").keyup(function(e) {
			if (e.which === 13) {
				getDeductionReports()();
			}
		});
		
	}
	function deductionReportSearch(){
		getDeductionReports();
	}
</script>			

<script>
 function getDeductionType(){
	 $.ajax({
			type : "POST",
			url: sourceleadUrl+"/getAllDeductionType?"+tokenParamName+"="+tokenValue+"",
			success :function(data) {
			var json = data.data;
			var output = "";
			for (var i=0; i<json.length; i++)
			{
			var opt = json[i];
				output = output + "<option value="+opt.id+">"+opt.payrollDeductionType+"</option>";
			}
			$("#deductionId").empty();
			$("#deductionId").append(output);
			$('#deductionId').trigger("chosen:updated");
			
		},
		});
 }
 </script>
 
 <script>
function deductionReportExportToPdf(){
	
	var groupId = $('#payGroup').val();
    getFreqdates("freqStartDate", "freqEndDate");
    var  freqStartDate = $("#freqStartDate").val();
    var freqEndDate = $("#freqEndDate").val();
	var searchValue = $("#dataTableSearch").val();
	searchValue = searchValue ? searchValue : "";
	var deductionId = $("#deductionId").val();
	var deduction = $('#deductionTypeDiv').html();
	if(deduction != null && deduction !='' && deduction !='undefined'){
		window.location.href = sourceleadUrl+"/exportToPdfDeductionReport?searchValue="+searchValue+"&groupId="+groupId+"&freqStartDate="+freqStartDate+"&freqEndDate="+freqEndDate+"&deductionId="+deductionId;
	}else{
		var result = " ";
		result = result +"	<div class='project_inn_mask'></div>";
		result = result +"	   <div id='empty_receivable_pop' class='remove_user_pop remove_user_pop_a'>";
		result = result +"		<p>Deduction Report List is Empty</p>";
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