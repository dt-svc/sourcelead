<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script src="/resources/js/payrollReports.js"></script>

<script>
$(document).ready(function(){
   getPayGroupList("payGroup");
   getDepartments("payrollDepartment");
});
</script>

 
 <!-- Content Area -->
  <section class="content-wraper contentWraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Payroll Comparison Report</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/usPayrollReport'/>">US Payroll Reports List</a> &raquo; Payroll Comparison Report</div>
    </section>
    
    <div class="contentareaInner-styles clearfix">
    
    <form:form id="payroll_comparison_form"  name="payroll_comparison_form" method="post">
    <input type="hidden" id="groupId" name="groupId" value="" />
    <div class="clearfix minHeight">
        <div class="clearfix row">
          <div class="col-md-5">
            <div class="form-group">
              <label class="col-md-12 paddingRight">Pay Group<span class="text-danger">&nbsp;*</span></label>  
				<div class="col-md-12">
					<select class="form-control sel" id="payGroup" name="payGroup" field-name="Pay Group" onchange="getPayPeriodByGroupId('payGroup','payPeriod','payPeriod2');" data-validation="required">
					</select>
				</div>
            </div>
            <div class="form-group">
              <label class="col-md-12">Payroll Type</label>
              <div class="col-md-12">
                <select class="form-control sel" id="payrollType" name="payrollType" field-name="Payroll Type" data-validation="required">
					    <option>Select Type</option>
						<option selected>Regular</option>
						<option>Additional</option>
					</select>
              </div>
            </div>
			</div>
            <div class="col-md-5">
             <div class="form-group">
              <label class="col-md-12">Compare Between</label>
              <div class="col-md-12">
               
               <div class="row">
               		<div class="col-md-6">
               		<label >Frequency1<span class="text-danger">&nbsp;*</span></label>
               			<select class="form-control sel" id="payPeriod" onchange="validateComparisonDates();" name="payPeriod" field-name="Frequency1" data-validation="required">
					   <!--  <option>Select Pay Period</option>
						<option>01-02-2018 28-02-2018</option>
						<option>01-03-2018 28-03-2018</option>
						<option>01-02-2018 28-02-2018</option> -->
					</select>
               		</div>
               		<div class="col-md-6">
               		 <label >Frequency2<span class="text-danger">&nbsp;*</span></label>
               			<select class="form-control sel" id="payPeriod2" onchange="validateComparisonDates();" name="payPeriod2" field-name="Frequency2" data-validation="required">
					   <!--  <option>Select Pay Period</option>
						<option>01-02-2018 28-02-2018</option>
						<option>01-03-2018 28-03-2018</option>
						<option>01-02-2018 28-02-2018</option> -->
					</select>
               		</div>
               </div>
                <div id="frequencyDiv"></div>
              </div>
            </div>
             <div class="form-group">
              <label class="col-md-12">Department <span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-12">
              
                <select class="form-control chosen-select" id="payrollDepartment" name="payrollDepartment" field-name="Department" data-validation="required">
					   <!--  <option>Select Department</option>
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
            <input value="Run" id="run_id" class="btn btn-success btnSuccess" type="button" onClick="runPayrollregister();">
            <input value="Reset" class="btn btn-defualt ml10" type="button" onClick="resetData();">
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
    <li><a class="newAdBtn mr5 hidden" href="/app/userCreation">+  Associate</a></li>
<!--     <li><a href="javascript:void(0)" class="filtIcon" title="Filters" onclick="openPop();"></a></li> -->
  <!--   <li><a href="javascript:void(0)" class="overIcon" title="Actions" onclick="newActions();"></a>
      <div class="newActPop" style="">
       <span><a href="javascript:void(0)" onclick="exportToPdf();" title="PDF">Export PDF</a> <a href="javascript:void(0)" onclick="exportUserData();" title="Excel">Export Excel</a> </span> </div>
    </li> -->
  </ul>
</div>
        <div class="clearfix tableNtable">
        <table width="100%" cellpadding="0" cellspacing="0">
            <thead>
              <tr>
                <th>Header</th>
                <th id="compareDate1"></th>
                <th id="compareDate2"></th>
              </tr>
            </thead>
		</table>
           <table width="100%" cellpadding="0" cellspacing="0">
            <tbody>
						<tr>
							<td colspan="3"><h4>Hours</h4></td>
					</tr>
						<tr>
							<td>Regular</td>
							<td id="regularHours1"></td>
							<td id="regularHours2"></td>
						</tr>
						<tr>
							<td>OT</td>
							<td id="overtimeHours1"></td>
							<td id="overtimeHours2"></td>
						</tr>
			</tbody>
			</table>
           
            <table width="100%" cellpadding="0" cellspacing="0" class="clearfix">
					<tbody>
					<tr>
						<td colspan="3"><h4>Wages</h4></td>
					</tr>
						<tr>
							<td>Gross Wage</td>
							<td id="grossWage1"></td>
                			<td id="grossWage2"></td>
						</tr>
					</tbody>
				</table>
            
				<table width="100%" cellpadding="0" cellspacing="0" id="contributionTableId">
					<tbody>
						<!-- <tr>
							<td colspan="3"><h4>Contribution</h4></td>
						</tr> -->
						<!--  <tr>
							<td>Contribution1</td>
                				<td>20 hrs</td>
						</tr>
						 <tr>
							<td>Contribution2</td>
                				<td>20 hrs</td>
               					<td>30 hrs</td>
						</tr>  -->
					</tbody>
				</table>

            
				<table width="100%" cellpadding="0" cellspacing="0" id="deductionTableId">
					<tbody>
						<!-- <tr>
							<td colspan="3"><h4>Deductions</h4></td>
						</tr> -->
						<!-- <tr>
							<td>Deduction1</td>
                <td>20 hrs</td>
                <td>30 hrs</td>
						</tr>
						<tr>
							<td>Deduction2</td>
                <td>20 hrs</td>
                <td>30 hrs</td>
						</tr> -->
					</tbody>
				</table>

				<table width="100%" cellpadding="0" cellspacing="0" id="payItemTableId">
					<tbody>
<!-- 						<tr>
							<td colspan="3"><h4>Pay Items</h4></td>
						</tr> -->
					<!-- 	<tr>
							<td>Item1</td>
                <td>20 hrs</td>
                <td>30 hrs</td>
						</tr>
						<tr>
							<td>Item2</td>
                <td>20 hrs</td>
                <td>30 hrs</td>
						</tr> -->
					</tbody>
				</table>
				
				
				<table width="100%" cellpadding="0" cellspacing="0" id="taxTableId">
					<tbody>
						<!-- <tr>
							<td colspan="3"><h4>Taxes</h4></td>
						</tr> -->
			<!-- 			<tr>
							<td>FDI</td>
                <td>20 hrs</td>
                <td>30 hrs</td>
						</tr>
						<tr>
							<td>SUI</td>
                <td>20 hrs</td>
                <td>30 hrs</td>
						</tr> -->
					</tbody>
				</table>
				
              
				<table width="100%" cellpadding="0" cellspacing="0">
					<tbody>
						<tr>
							<td><h4>Net Pay</h4></td>
                			<td id="netPayId1"></td>
                			<td id="netPayId2"></td>
						</tr>
					</tbody>
				</table>

        </div>
        
        
 <!--        <nav>
          <ul id="paging" class="pagination">
            <li><a href="#">Previous</a></li>
            <li><a href="#">2</a></li>
            <li><a href="#">3</a></li>
            <li><a href="#">4</a></li>
            <li><a href="#">5</a></li>
            <li class="disabled"><a href="#">6</a></li>
            <li data-page="last"><a href="#">Next</a></li>
          </ul>
          <div class="clearfix"></div>
        </nav> -->
      </div>
    </div>
     		
      <!-- Content Area Ends--> 
    </div>
    
  </section>
  
 
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
function resetData() {
	 $("#payrollRegister").hide();
	 $("#payGroup").val('');
// 	 $("#payrollType").val('');
	 $("#payPeriod").empty();
	 $("#payPeriod2").empty();
	 $("#payrollDepartment").val('');
	 $("#payrollDepartment").trigger("chosen:updated"); 
	 $("#frequencyDiv").find('p.jquery_form_error_message').remove();
	 $("#run_id").removeAttr("disabled");
	
}
</script>

<script>
function validateComparisonDates(){
	
	var compareDate1 = $('#payPeriod :selected').text();
	var compareDate2 = $('#payPeriod2 :selected').text();
	
	if (compareDate1 == compareDate2) {
		$("#frequencyDiv").find('p.jquery_form_error_message').remove();
		$("#run_id").removeAttr("disabled");
		$("#run_id").attr("disabled", "disabled");
		$("#frequencyDiv")
				.append(
						"<p class='jquery_form_error_message'>Both frequencies cannot be same</span>");
	} else {
		$("#frequencyDiv").find('p.jquery_form_error_message').remove();
		$("#run_id").removeAttr("disabled");
	}
}
</script>s

<script type="text/javascript">
function runPayrollregister(){
	
	var compareDate1 = $('#payPeriod :selected').text();
	$("#compareDate1").empty();
	$("#compareDate1").append(compareDate1);
	var compareDate2 = $('#payPeriod2 :selected').text();
	$("#compareDate2").empty();
	$("#compareDate2").append(compareDate2);
	var groupId = $("#groupId").val();
	
	 if ($('#payroll_comparison_form').validate(false, validationSettings)){
		 $("#payrollRegister").show();
		 var formData = getFormData("payroll_comparison_form");
         $.ajax({
				type : "POST",
     	    	data: formData,
				url : sourceleadUrl+"/runPayrollComparison?"+tokenParamName+"="+tokenValue,
				success : function(result) {
					
					
					if(result.compareData1 != null){
						$("#regularHours1").empty();
						$("#overtimeHours1").empty();
						$("#grossWage1").empty();
						$("#netPayId1").empty();
						$("#regularHours1").append(result.compareData1.totalRegHours +' hrs');
						$("#overtimeHours1").append(result.compareData1.totalOTHours +' hrs');
						$("#grossWage1").append('$ '+result.compareData1.totalGross);
						$("#netPayId1").append('$ '+result.compareData1.netPay);
						
						$("#contributionTableId > tbody").html("");
						 $('#contributionTableId').append('<tr><td colspan="3"><h4>Contribution</h4></td></tr>');
						   for (var p in result.compareData1.contriMap) {
							  var conStr = p.replace(/[^a-zA-Z0-9]/g, '');
							  $('#contributionTableId').append( '<tr><td>' + p + '</td><td>$ ' + result.compareData1.contriMap[p] + '</td><td><span id="'+conStr+'conAmount2"></span></td></tr>' );
						   }
						   
						   $("#deductionTableId > tbody").html("");
						   $('#deductionTableId').append('<tr><td colspan="3"><h4>Deductions</h4></td></tr>');
						   for (var p in result.compareData1.dedMap) {
							   var dedStr = p.replace(/[^a-zA-Z0-9]/g, '');
							   $('#deductionTableId').append( '<tr><td>' + p + '</td><td>$ ' + result.compareData1.dedMap[p] + '</td><td><span id="'+dedStr+'dedAmount2"></span></td></tr>' );
						   }
						   
						   $("#payItemTableId > tbody").html("");
						   $('#payItemTableId').append('<tr><td colspan="3"><h4>Pay Items</h4></td></tr>');
						   for (var p in result.compareData1.payItemMap) {
							   var payStr = p.replace(/[^a-zA-Z0-9]/g, '');
							   if(result.compareData1.payItemMap[p] != 0)
							   $('#payItemTableId').append( '<tr><td>' + p + '</td><td>$ ' + result.compareData1.payItemMap[p] + '</td><td><span id="'+payStr+'payAmount2"></span></td></tr>' );
						   }
						   
						   $("#taxTableId > tbody").html("");
						   $('#taxTableId').append('<tr><td colspan="3"><h4>Taxes</h4></td></tr>');
						   for (var p in result.compareData1.taxMap) {
							   var taxStr = p.replace(/[^a-zA-Z0-9]/g, '');
							   if(result.compareData1.taxMap[p] != 0)
							   $('#taxTableId').append( '<tr><td>' + p + '</td><td>$ ' + result.compareData1.taxMap[p] + '</td><td><span id="'+taxStr+'taxAmount2"></span></td></tr>' );
						   }
					}
					
					if(result.compareData2 != null){
						$("#regularHours2").empty();
						$("#overtimeHours2").empty();
						$("#grossWage2").empty();
						$("#netPayId2").empty();
						$("#regularHours2").append(result.compareData2.totalRegHours +' hrs');
						$("#overtimeHours2").append(result.compareData2.totalOTHours+' hrs');
						$("#grossWage2").append('$ '+result.compareData2.totalGross);
						$("#netPayId2").append('$ '+result.compareData2.netPay);
						
						   for (var p in result.compareData2.contriMap) {
							   var conStr2 = p.replace(/[^a-zA-Z0-9]/g, '');
							   if (p in result.compareData1.contriMap)
							   {
							      $('#'+conStr2+'conAmount2').append('$ '+result.compareData2.contriMap[p]);
							   }else{
								   $('#contributionTableId').append( '<tr><td>' + p + '</td><td></td><td>$ ' + result.compareData2.contriMap[p] + '</td></tr>' );
							   }
						   }
						   
							   for (var p in result.compareData2.dedMap) {
								   var dedStr2 = p.replace(/[^a-zA-Z0-9]/g, '');
								   if (p in result.compareData1.dedMap)
								   {
								      $('#'+dedStr2+'dedAmount2').append('$ '+result.compareData2.dedMap[p]);
								   }else{
									   $('#deductionTableId').append( '<tr><td>' + p + '</td><td></td><td>$ ' + result.compareData2.dedMap[p] + '</td></tr>' );
								   }
							   }
							   
							   
							   for (var p in result.compareData2.payItemMap) {
								   var payStr2 = p.replace(/[^a-zA-Z0-9]/g, '');
								   if (p in result.compareData1.payItemMap)
								   {
								      $('#'+payStr2+'payAmount2').append('$ '+result.compareData2.payItemMap[p]);
								   }else{
									   if(result.compareData2.payItemMap[p] != 0)
									   $('#payItemTableId').append( '<tr><td>' + p + '</td><td></td><td>$ ' + result.compareData2.payItemMap[p] + '</td></tr>' );
								   }
							   }
							   for (var p in result.compareData2.taxMap) {
								   var taxStr2 = p.replace(/[^a-zA-Z0-9]/g, '');
								   if (p in result.compareData1.taxMap)
								   {
								      $('#'+taxStr2+'taxAmount2').append('$ '+result.compareData2.taxMap[p]);
								   }else{
									   if(result.compareData2.taxMap[p] != 0)
									   $('#taxTableId').append( '<tr><td>' + p + '</td><td></td><td>$ ' + result.compareData2.taxMap[p] + '</td></tr>' );
								   }
							   }
						
					}

				},
			});
     return true;
}else{
	return false;
}
}

$('body').on('blur', '#payroll_comparison_form', function() {
	$("#payroll_comparison_form").showHelpOnFocus();
	$("#payroll_comparison_form").validateOnBlur(false, validationSettings);
}); 

</script>
 
<jsp:include page="../masterFooter.jsp" />