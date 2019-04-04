<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script>
$(document).ready(function(){
	$("#approveDiv").hide();
	$("#submitButtonDiv").hide();
	$("#historySearchDiv").hide();
	var redirectValue = "${redirect}";
	if(redirectValue != null && redirectValue != '' && redirectValue != 'undefined'){
		if(redirectValue == "draft"){
			$("#draftAId").parent().addClass("current");
	        $("#draftAId").parent().siblings().removeClass("current");
			getDraftData();
		}else if(redirectValue == "history"){
			$("#historyAId").parent().addClass("current");
	        $("#historyAId").parent().siblings().removeClass("current");
	        getPayPeriodData();
		}
	}
});
</script>

<script>
function reviewPayrollDiv(){
	$("#reviewPayrollDiv").show();
	$("#approveDiv").hide();
}

function approvePayrollDiv(){
	$("#reviewPayrollDiv").hide();
	$("#approveDiv").show();
}
</script>

<!-- Content Area -->
<section class="content-wraper contentWraper">
<section class="content-header clearfix">
  <div class="pull-left">
    <h2>Payroll</h2>
  </div>
<div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Payroll</div>
</section>

<div id="initiateLoading"></div>
   <div class="project_side_wrap_inn_tabs">
   	<ul class="project-tabs-menu">
   	    <li id="dashId" class="current"><a class="active" onclick="dashboardTab();">Dashboard</a></li>
	   <li id="overId"><a id="overViewAId" onclick="getOverviewdata();">Overview</a></li>
	   <li><a onclick="getDraftData();" id="draftAId">Draft</a></li>

<%-- 	   <c:if test="${SourceLeadWeb_isPayRollApprover == 'Y'}"> --%>
	        <li><a onclick="getPayPeriodData();" id="historyAId">History</a></li>
<%-- 	   </c:if> --%>
	   
	   <li><a onclick="getUserGroupList('DASHBOARD');" id="employeesAId">Employees</a></li>
<!-- 	   <li><a href="javascript:void(0)">Contractors</a></li> -->
<!-- 	   <li><a href="javascript:void(0)">Tax Forms</a></li> -->
<!-- 	   <li><a href="javascript:void(0)">Settings</a></li> -->
	 </ul>
   </div> 
   
   <div class="clearfix"></div>
   <%-- <div class="col-md-2 col-md-offset-7">
         			<a href="<c:url value='${appPath}/runPayroll'/>" class="btn btn-success btnSuccess btn-sm anchorWhite pull-right mt10">Initiate Payroll</a>
    </div> --%>
    
    <div id="overViewDiv">
<div class="clearfix"></div>
<div class="timesheet_out" id="reviewPayrollDiv" style = "display : none">

<div class="tablelist-wrap">
<div class="timesheet_head minHeight">
<form:form id="generate_payroll_form" name="generate_payroll_form" method="post">
<div id="initiateDivv">
<div class="col-md-12 mt">
	<div class="row">
		<div class="col-md-3" id="groupIdDiv">
			<select class="chosen-select" id="groupId" name="groupId" onchange="removeValidateGroup();clearSubmitDiv();getGroupFrequency();" data-validation="required" field-name="User Group">
				<option value="">User Group</option>
                    <c:forEach var="ug" items="${userGroupList}">
					<option value="${ug.groupUUID},${ug.id}">${ug.groupName}</option>
				</c:forEach>
			</select>
		</div>
		<div class="col-md-3 paddingLeft paddingRight" id="payPeriodDiv">
			<select id="payPeriod" name="payPeriod" data-validation="required" field-name="Pay Period" class="chosen-select" onchange="errPayPeriod();clearPayrollDiv();">
			</select>
		</div>

		<div class="col-md-2 paddingLeft paddingRight" id="payTypeIdDiv">
			<select id="payType" name="payType" data-validation="required" field-name="Pay Type" class="chosen-select" onchange="errPayType();">
				 <option value="">Pay type</option>
				 <option value="All">All</option>
				 <option value="HourlyRate">Hourly Rate</option>
                 <option value = "Salary">Salary</option>
                 <option value = "LcaWage">LCA Wage</option>
			</select>
		</div>

		<div class="col-md-2 paddingRight" id="payrollPayDateDivId">
			<input name="" class="form-control calendar" placeholder="Check Date" id="payrollPayDate" name="payrollPayDate" type="text" data-validation="required" field-name="Check Date" readonly="readonly">
		</div>
		<div class="col-md-2">
			<a  class="btn btn-success btnSuccess btn-sm anchorWhite" id="generateUSPayroll" onclick="runUSPayroll();">Initiate Payroll</a>
		</div>
	</div>						
</div>
<div class="clearfix"></div>
<!-- <div class="remove_search">
	<input placeholder="Search" type="text">
</div>   -->   
</div>         
</form:form>
</div>

 
<div class="clearfix">
<form:form id="submit_for_approver"  name="submit_for_approver" method="post">
 <div class="tableScroll styleTables clearfix mt">
 <!-- <div id="USPayrollDiv"></div> -->
 <div id="payrollDataDiv"></div>
</div> 

<div class="clearfix mt"></div>

<div class="clearfix">
<!-- <div class="col-md-2"><a href="javascript:void(0)" class="btn anchorBlue">Exclude</a></div> -->
	<div id="submitButtonDiv" style="display:none">
	
<div class="col-md-7">
	<div class="row">
	
	<div class="col-md-5">
		<div>
			<label class="pull-left">No. of Payroll Generated  :</label>
			<div id="totalNoPayroll" class="pull-left ml"><strong>00.00</strong></div>
		</div>
	</div>
	
	<div class="col-md-7">
			<label class="pull-left">Total Net Pay  :</label>
			<div class="pull-left ml" id="totalPayrollAmount"><strong>00.00</strong></div>
	</div>
	
	</div>
	
</div>	
	
	<div class="col-md-5">
	<ul class="pull-right mr list-unstyled list-inline">
		<li><a href="#" class="btn btn-primary btnSuccess" id="Submit" onClick="sendPayrollForApprover();">Submit for Approve</a></li>
		 <c:if test="${SourceLeadWeb_isPayRollApprover == 'Y'}">
		<li><a href="#" class="btn btn-default ml mr" id="Confirm" onclick="confirmPayroll();">Confirm</a></li>
		</c:if>
		<li><a href="#" class="btn btn-default" onclick="window.location='/app/payrollList?&redirect=draft'">Cancel</a></li>
	</ul>
	</div>
	
	</div>
<div class="clearfix"></div>
</div>
</form:form>
</div>


</div>


</div>
		
	</div>
	<div class="clearfix"></div>
		
	 <div class='reportHead' id="historySearchDiv" style = "display : none">
		<ul class='pull-right'>
		<li><input type='text' placeholder='Search' class='repSearchBar' id='historyDataTableSearch'/><span class='repSearchIcon' onClick=''></span></li>
		</ul>
   </div>
   
    <div class='reportHead' id="searchDivForEmployee" style = "display : none">
		<ul class='pull-right'>
		<li><input type='text' placeholder='Search' class='repSearchBar' id='dataTableSearch'/><span class='repSearchIcon' onClick=''></span></li>
		</ul>
   </div>
   
	<div class="candidate_wrap_table datatable_filter backgroundWhite">
                <div id="payrollFrequencyDiv"></div>
                <div id="draftFrequencyDiv" class="mt25"></div>
                <div id="userGroupDiv"></div>
    </div>
    
	
		
		
		
		<!--  send approver popup -->
<div class="comMask"></div>
<!-- Modal -->
  <div class="modal sendApprover" id="sendApprover" style="display:none;">
    <div class="modal-dialog">
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" onclick="closeApproverFun();">&times;</button>
          <h4 class="modal-title">Approver</h4>
        </div>
        <div class="modal-body">
        	<div class="form-group clearfix mb0">
            
          <lable class="col-md-3">Select Approver</lable>
          <div class="col-md-9">
          <select class="form-control chosen-select" multiple data-placeholder="Select Approvers" id="approver" name="approver" field-name="Approver" data-validation="required">
          <!-- 	<option>Approver1</option>
            <option>Approver2</option> -->
          </select>
          </div>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-primary" onclick="sendPayrollForApprover();">Send</button>
          <button type="button" class="btn btn-default ml" onClick="closeApproverFun();">Cancel</button>
        </div>
      </div>
      
    </div>
  </div>
<!--  send approver popup -->
		
		
<!--   Error Message   -->
<div class="vendor_wrap_mask"></div>
<div class="vendor_error">
<textarea placeholder="Enter message"></textarea>
<a class="close_color" href="javascript:void(0)">Yes</a><a href="javascript:void(0)">No</a> </div>

<!--   Error Message   --> 


<jsp:include page="uSPayrollDashboard.jsp" />
<jsp:include page="usPayrollApprove.jsp" />
<jsp:include page="payrollAdjustment.jsp" />
<jsp:include page="usPayrollDashboardEmployees.jsp" />
  </section>
  
<script>
$(document).ready(function() {
    $(".project-tabs-menu a").click(function(event) {
        event.preventDefault();
        $(this).parent().addClass("current");
        $(this).parent().siblings().removeClass("current");
    });
});
</script>
  
  <script>
  function getOverviewdata(){
	  
	  $("#approveDiv").hide();
	  $("#dashboardDiv").hide();
	  $("#userGroupDiv").hide();
	  $("#draftFrequencyDiv").hide();
	  $("#payrollFrequencyDiv").hide();
	  $("#historySearchDiv").hide();
	  $("#overViewDiv").show();
	  $("#searchDivForEmployee").hide();
	  $("#reviewPayrollDiv").show();
	  $('#payrollPayDateDivId').find('p.jquery_form_error_message').remove();
	  $('#payTypeIdDiv').find('p.jquery_form_error_message').remove();
	  $('#payPeriodDiv').find('p.jquery_form_error_message').remove();
	  $('#groupIdDiv').find('p.jquery_form_error_message').remove();
  }
  </script>
  
  
  <script>
  function getPayPeriodData(){
			var sourceleadUrl = '${sourceleadUrl}';
			var searchValue = $("#historyDataTableSearch").val();
			searchValue = searchValue ? searchValue : "";
			var url = sourceleadUrl+ "/getPaySheetFrequencyData?searchValue="+searchValue;
			preparePaySheetFrequency(url);
	  
  }
  
  
  function preparePaySheetFrequency(url) {
		 
		var result = "";
		result = result + '<table id="payFrequency" class="display" cellspacing="0" width="100%" >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result + '<th>Pay run id</th>';
		result = result + '<th>Group Name</th>';
		result = result + '<th>Pay Period</th>';
		result = result + '<th>Pay Date</th>';
		result = result + '<th>Type</th>';
		result = result + '<th>Total</th>';
		result = result + '<th>Status</th>';
		result = result + '</tr>';
		result = result + '</thead>	';
		result = result + "</table>";
		$("#payrollFrequencyDiv").empty();
		$("#payrollFrequencyDiv").append(result);
		payFrequencyPagination(url);
		
		$("#overViewDiv").hide();
		$("#draftFrequencyDiv").hide();
		$("#approveDiv").hide();
		$("#userGroupDiv").hide();
		$("#searchDivForEmployee").hide();
		$("#dashboardDiv").hide();
		$("#historySearchDiv").show();
		$("#historyDataTableSearch").show();
		$("#payrollFrequencyDiv").show();

	}
  
  function payFrequencyPagination(url) {
		var sourceleadUrl = '${sourceleadUrl}';
		var colIndex = 0;
		$('#payFrequency').DataTable({
							"processing" : true,
							"serverSide" : true,
							"searching" : false,
							"ajax" : $.fn.dataTable.pipeline({
								url : url,
								method : 'GET',
								pages : 10
							}),
							
							"columns" : [ {
								"data" : "Pay run id"
							}, {
								"data" : "Group Name"
							}, {
								"data" : "Pay Period"
							}, {
								"data" : "Pay Date"
							},{
								"data" : "Type"
							},{
								"data" : "total"
							}, {
								"data" : "status"
							}],
							
							
							 "columnDefs" : [
									{
										"targets" : colIndex++,
										"data" : "Pay run id",
										"render" : function(data, type, opt,meta) {
											var result = "";
											if(opt.payPeriodNum != null && opt.payPeriodNum != '' && opt.payPeriodNum !='undefined'){
						                		  result = result +'<div class="tblminwidth-100"><a href="'+sourceleadUrl+'/viewPaySheetFrequencyData?approverStatus='+opt.approverStatus+'&paySheetFrequencyUUID='+opt.paySheetFrequencyuuid+'&payPeriodNum='+opt.payPeriodNum+'">'+opt.payPeriodNum+'</a></div>';
											}else{
						              		  result = result +'<div class="tblminwidth-100">--</div>';
						              		}
											return result;
										}
									},{
										"targets" : colIndex++,
										"data" : "Group Name",
										"render" : function(data, type, opt,meta) {
											var result = "";
											if(opt.groupName != null && opt.groupName != '' && opt.groupName !='undefined'){
						                		  result = result +'<div class="tblminwidth-100">'+opt.groupName+'</div>';
											}else{
						              		  result = result +'<div class="tblminwidth-100">--</div>';
						              		}
											return result;
										}
									},{
										"targets" : colIndex++,
										"data" : "Pay Period",
										"render" : function(data, type, opt,meta) {
											var result = "";
											if(opt.payPeriod != null && opt.payPeriod != '' && opt.payPeriod !='undefined'){
						                		  result = result +'<div class="tblminwidth-100">'+opt.payPeriod+'</div>';
						              		}else{
						              		  result = result +'<div class="tblminwidth-100">--</div>';
						              		}
											return result;
										}
									},
									{
										"targets" : colIndex++,
										"data" : "Pay Date",
										"render" : function(data, type, opt,meta) {
											var result = "";
											if(opt.payDate != null && opt.payDate != '' && opt.payDate !='undefined'){
						                		  result = result +'<div class="tblminwidth-100">'+opt.payDate+'</div>';
						              		}else{
						              		  result = result +'<div class="tblminwidth-100">--</div>';
						              		}
											return result;

										}
									},
									{
										"targets" : colIndex++,
										"data" : "Type",
										"render" : function(data, type, opt,meta) {
											var result = "";
											if(opt.payPeriod != null && opt.payPeriod != '' && opt.payPeriod !='undefined'){
						                		  result = result +'<div class="tblminwidth-100">Regular</div>';
						              		}else{
						              		  result = result +'<div class="tblminwidth-100">--</div>';
						              		}
											return result;
										}
									},
									{
										"targets" : colIndex++,
										"data" : "Total",
										"render" : function(data, type, opt,meta) {
											var result = "";
											if(opt.total != null && opt.total != '' && opt.total !='undefined'){
						                		  result = result +'<div class="tbldata-dotdot-vsm">$ '+opt.total.toFixed(2)+'</div>';
						              		}else{
						              		  result = result +'<div class="tbldata-dotdot-vsm">$ 0.0</div>';
						              		}
											return result;
										}
										
									},{
										"targets" : colIndex++,
										"data" : "Status",
										"render" : function(data, type, opt,meta) {
											var result = "";
											if(opt.approverStatus != null && opt.approverStatus != '' && opt.approverStatus !='undefined'){
						                		  result = result +'<div class="tblminwidth-100">'+opt.approverStatus+'</div>';
						              		}else{
						              		  result = result +'<div class="tblminwidth-100">--</div>';
						              		}
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
		$("#payFrequency").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>");
		$("input[type=search]").hide();
		$("#historyDataTableSearch").keyup(function(e) {
			if (e.which === 13) {
				getPayPeriodData();
			}
		});
		
	}
  
  </script>
  
  
  <script>
function getGroupFrequency(){
	var groupUUID = $("#groupId").val();
	 $.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getGroupPayPeriod?"+tokenParamName+"="+tokenValue+"&groupUUID="+groupUUID                  , 
	        success: function(json) {
	        var output = "";
	        output = output + "<option value=''>Ferquency</option>";
			for (var i=0; i<json.length; i++)
			{
				var opt = json[i];
				var date = opt.payPeriod.replace(",", " to ");
				output = output + "<option value='" +opt.payFrequency+ "'>"+date+"</option>";
			}
			$("#payPeriod").empty();
			$("#payPeriod").append(output);
			$('#payPeriod').trigger("chosen:updated"); 
	        },
	    });
	 $("#payrollDataDiv").empty();
	 $('#payrollPayDate').val("").datepicker("update");
// 	 $("#submitButtonDiv").hide();
}
 
 function removeValidateGroup(){
	$('#groupIdDiv').find('p.jquery_form_error_message').remove();
 }
 function errPayType(){
		$('#payTypeIdDiv').find('p.jquery_form_error_message').remove(); 
		$("#payrollDataDiv").empty();
		$("#submitButtonDiv").hide();

 }
 
</script>


<script>
function runUSPayroll(){
	if ($('#generate_payroll_form').validate(false, validationSettings)){  
		
		$("#initiateLoading").attr('style','position:absolute; width:100%; height:140%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		
		var searchValue = $("#dataTableSearch").val();
		searchValue = searchValue ? searchValue : "";
		var groupId = $("#groupId").val();
		var payPeriod = $("#payPeriod").val();
		var payType = $("#payType").val();
		var payrollPayDate = $("#payrollPayDate").val();
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/generateUSPayroll?searchValue="+searchValue+"&groupId="+groupId+"&payPeriod="+payPeriod+"&payrollPayDate="+payrollPayDate+"&payType="+payType,                 
		        success: function(data) {
		        	   var count = 0;
		        		 var json = data.payrollDTO;
				        	var result = "";
				        	result += '<table>';
				        	result += '<thead>';
				        	result += '<tr>';
				         	/* result += '<th>';
				        	result += '<div class="checkbox-new pull-left">';
				        	result += '<input id="all" class="checkbox-custom" name="paidComponent" type="checkbox">';
				        	result += '<label for="all" class="checkbox-custom-label"></label>';
				        	result += '</div>';
				        	result += '</th>'; */ 
				        	result += '<th>Exclude</th>';
				        	result += '<th>Emp ID</th>';
				        	result += '<th>Emp Name</th>';
				        	result += '<th>Regular<br>Hours</th>';
				        	result += '<th>Pay Per Salary</th>';
				        	result += '<th>Pay Type</th>';
				        	result += '<th>OT<br>Hours</th>';
				        	result += '<th>Gross<br>Pay</th>';
				        	result += '<th>Pretax<br>Deduction</th>';
				        	result += '<th>Post<br>Deduction</th>';
				        	result += '<th>Taxes</th>';
				        	result += '<th>Net<br>Pay</th>';
				        	result += '</tr>';
				        	result += '</thead>	';
				        	result += '<tbody>';
				        	result += '<input type="hidden" value='+data.id+' id="payrollFrequencyId" name="payrollFrequencyId" />';
				        	result += '<input type="hidden" value='+data.payrollFrequencyUUID+' id="payrollFrequencyUUID" name="payrollFrequencyUUID" />';
				        	if(json != null && json.length > 0){
				        		$("#submitButtonDiv").show();
				        		$("#Submit").show();
					    		$("#Confirm").show();
				        		for (var i=0; i<json.length; i++){
				        			var opt = json[i];
				        			count++;
				        			result += '<tr>';
						        	 result += '<td>';
						        	if(json.length > 1){
						        	result += '<div class="checkbox-new pull-left ml5">';
						        	result += '<input id="'+i+'emp1" value="YES" class="checkbox-custom" name="uspayroll['+i+'].isExcept" type="checkbox">';
						        	result += '<label for="'+i+'emp1" class="checkbox-custom-label"></label>';
						        	result += '</div>';
				        			}
						        	result += '</td>'; 
						        	result += '<input type="hidden" value='+opt.empPayrollId+' id="'+i+'empPayrollId" name="uspayroll['+i+'].empPayrollId" />';
						        	if(opt.empId != null && opt.empId != 'undefined' && opt.empId != ''){
						        		result += '<td><div class="tblminwidth-50">'+opt.empId+'</div></td>';
						        	}else{
						        		result += '<td><div class="tblminwidth-50">--</div></td>';
						        	}
						        	result += '<td><div class="tbldata-dotdot-vsm">'+opt.empName+'</div></td>';
 						        	result += '<td><div class="tblminwidth-50">'+opt.regularHours+'</div><div class="tblminwidth-50 clearfix text-danger">$ '+opt.regularHrsTotalAmt.toFixed(2)+'</div></td>';	
						        	/* result += '<td><div class="tblminwidth-125 clearfix">$ '+opt.grossPay.toFixed(2)+'</div>'; */
						        	result += '<td><div class="tbldata-dotdot-vsm">$ '+opt.payPerSal+'</div></td>';
						        	if(opt.payType != null && opt.payType != ''){
						        		result += '<td><div class="tbldata-dotdot-vsm">'+opt.payType+'</div></td>';
						        	}else{
						        		result += '<td><div class="tbldata-dotdot-vsm">--</div></td>';
						        	}
						        	
						        	
						        	if(opt.overtimeHours != null && opt.overtimeHours != '' && opt.overtimeHours != 'undefined'){
						        		result += '<td><div class="tblminwidth-50">'+opt.overtimeHours+'</div><div class="tblminwidth-50 clearfix text-danger">$ '+opt.otHoursTotalAmt.toFixed(2)+'</div></td>';
						        	}else{
						        		result += '<td><div class="tblminwidth-50">0.00</div><div class="tblminwidth-50 clearfix text-danger">$ 0.00</div></td>';
						        	}
						        	
						        	result += '<td><div class="tblminwidth-125 clearfix">$ '+opt.grossPay.toFixed(2)+'</div>';
						        	if(opt.preTaxDeduction != null && opt.preTaxDeduction != '' && opt.preTaxDeduction != 'undefined'){
						        		result += '</td><td><div class="tblminwidth-125 clearfix">$ '+opt.preTaxDeduction.toFixed(2)+'</div></td>';
						        	}else{
						        		result += '</td><td><div class="tblminwidth-125 clearfix">$ 0.00</td>';
						        	}
						        	if(opt.postTaxDeduction != null && opt.postTaxDeduction != '' && opt.postTaxDeduction != 'undefined'){
						        		result += '<td><div class="tblminwidth-125 clearfix">$ '+opt.postTaxDeduction.toFixed(2)+'</div></td>';	
						        	}else{
						        		result += '<td><div class="tblminwidth-125 clearfix">$ 0.00</div></td>';
						        	}
						        	
						        	result += '<td><div class="tblminwidth-125 clearfix">$ '+opt.taxes.toFixed(2)+'</div></td>';
						        	result += '<td><div class="tblminwidth-125 clearfix"><a onClick="payAdjustment(\''+opt.empPayrollUUID+'\',\'DRAFT\');">$ '+opt.netPay.toFixed(2)+'</a></div></td>';
						        	result += '</tr>';  
					        	}
					        	
				        	}
				        	result += '</tbody>';
				        	if(count== 0){
					    		result = result+'<tr><td align="top" colspan="11" class="tblminwidth-50 clearfix dataTables_empty"></td></tr>';	
					    		$("#Submit").hide();
					    		$("#Confirm").hide();
					    	}
				        	result += '</table>';
				        	$("#payrollDataDiv").show();
				        	$("#payrollDataDiv").empty();
				        	$("#totalNoPayroll").html(data.noOfGeneratedPayroll);
				        	$("#totalPayrollAmount").html('<span>$ ' + data.totalGeneratedAmount + '</span>');
				        	$("#payrollDataDiv").append(result);
				        	$("#initiateLoading").removeAttr('style');
		        	    
		        	},
		           
		    });
		 
		return true; 
    }else{
   return false;
   	  } 
	}
	
$('body').on('blur', '#generate_payroll_form', function() {
	$("#generate_payroll_form").showHelpOnFocus();
	$("#generate_payroll_form").validateOnBlur(false, validationSettings);  
});


</script>

<script>
	$(function() {
		
		 var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
		  // written common method in slead-common.js for date formats start
		  var finalDateFormat = getJSDateFormat(locationDate);
		  //end
		  $('#payrollStartDate').datepicker({
		      dateFormat: finalDateFormat,
		      changeMonth: true,
		  	  changeYear: true, 
		      //minDate: 0  this line for to restrict selection of previous dates
		  });
		  
		  $('#payrollEndDate').datepicker({
		      dateFormat: finalDateFormat,
		      changeMonth: true,
		  	  changeYear: true, 
		      //minDate: 0  this line for to restrict selection of previous dates
		  });
		  $('#payrollPayDate').datepicker({
		      dateFormat: finalDateFormat,
		      changeMonth: true,
		  	  changeYear: true, 
	  		  maxDate: 0,
		  	  onSelect : function(selected) {
			   addValidClass();
/* 		  		document.getElementById("payrollPayDateDivId").style.display="block"; */
			}
		      //minDate: 0  this line for to restrict selection of previous dates
		  });
		  
	});
	
	function addValidClass(){
		 $('#payrollPayDateDivId').find('p.jquery_form_error_message').remove(); 
	}
</script>

<script type="text/javascript">
function sendPayrollForApprover(){
      var formData = $($('#submit_for_approver')[0]).serialize();
      $.post(sourceleadUrl+"/sendPayrollForApprover?"+tokenParamName+"="+tokenValue, formData, function(result) {
        if (result && result.response === "INVALID_DATA") {
              showError(result.responseMessage);
          } else if (result.response === "AWKWARD") {
              location.href = "/errorPage";
          } else if (result.response === "SUCCESS") {
        	  location.replace(sourceleadUrl+ "/payrollList?&redirect=history"); 
        	  $("#msgDiv").html("Submited Successfully");
          } else if (result.indexOf('Sign In') !== -1) {
            location.replace(sourceleadUrl);
          } else {
              showError("Operation failed, please try again");
          }
      });   
    
}
</script>

<script>
function confirmPayroll(){
	$("#reviewPayrollDiv").hide();
	$("#approveDiv").show();
	 var confirmType = "DRAFT";
	 var payType = $("#payType").val();
	 var formData = $($('#submit_for_approver')[0]).serialize();
	 calculateTotalPay(formData,confirmType,"",payType);
}
</script>

<script>
function msgFadein() {
	$("#selector1").fadeIn(200);
    $('#selector1').delay(2000).fadeOut('slow');
}

</script>

<script>
function clearPayrollDiv(){
// 	$("#groupId").val('');
// 	$("#payrollPayDate").val('');
	$("#payrollDataDiv").empty();
	$("#submitButtonDiv").hide();
}
function errPayPeriod(){
	$('#payPeriodDiv').find('p.jquery_form_error_message').remove();  

}
function clearSubmitDiv(){
	$("#submitButtonDiv").hide();
	$("#payType").val('');
	$('#payType').trigger("chosen:updated"); 
}
</script>

  <script>
  $( function() {
	  var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
	  // written common method in slead-common.js for date formats start
	  var finalDateFormat = getJSDateFormat(locationDate);
	  //end
	  $('#DateofJoin').datepicker({
	      dateFormat: finalDateFormat,
	      changeMonth: true,
	  	  changeYear: true, 
	      //minDate: 0  this line for to restrict selection of previous dates
	  });
	  
  } );
</script>

<script type="text/javascript">
function getDraftData(){
	
	$("#initiateLoading").attr('style','position:absolute; width:100%; height:140%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	  $.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getPaysheetFrequencyDraftData",                 
	        success: function(data) {
			        	var result = '';
			        	result += '<table id="draftPagination">';
			        	result += '<thead>';
			        	result += '<tr>';
			        	result += '<th>Group Name</th>';
			        	result += '<th>Frequency</th>';
			        	result += '<th>PayCheckDate</th>';
// 			        	result += '<th>Status</th>';
			        	result += '</tr>';
			        	result += '</thead>	';
			        	result += '<tbody>';
			        	if(data != null && data.length > 0){
			        		for (var i=0; i<data.length; i++){
			        			var opt = data[i];
			        			result += '<tr>';
					        	if(opt.groupName != null && opt.groupName != 'undefined' && opt.groupName != ''){
					        		result += '<td><div class="tbldata-dotdot-200">'+opt.groupName+'</div></td>';
					        	}else{
					        		result += '<td><div class="tbldata-dotdot-200">--</div></td>';
					        	}
					        	
					        	if(opt.payFrequency != null && opt.payFrequency != '' && opt.payFrequency != 'undefined'){
					        		result += '<td><div class="tbldata-dotdot-200"><a href="'+sourceleadUrl+'/viewPaySheetFrequencyData?paySheetFrequencyUUID='+opt.paySheetFrequencyuuid+'&approverStatus=DRAFT">'+opt.payFrequency+'</a></div></td>';
					        	}else{
					        		result += '<td><div class="tbldata-dotdot-200">--</div></td>';
					        	}
					        	
					        	if(opt.payDate != null && opt.payDate != '' && opt.payDate != 'undefined'){
					        		result += '<td><div class="tbldata-dotdot-200">'+opt.payDate+'</div></td>';
					        	}else{
					        		result += '<td><div class="tbldata-dotdot-200">--</div></td>';
					        	}
// 					        	if(opt.approverStatus != null && opt.approverStatus != '' && opt.approverStatus != 'undefined'){
// 					        		result += '<td><div class="tblminwidth-50">'+opt.approverStatus+'</div></td>';	
// 					        	}else{
// 					        		result += '<td><div class="tblminwidth-50">--</div></td>';
// 					        	}
					        	
					        	result += '</tr>';  
				        	}
				        	
			        	}
			        	result += '</tbody>';
			        	result += '</table>';
			        	
			        	$("#overViewDiv").hide();
			        	$("#payrollFrequencyDiv").hide();
			        	$("#approveDiv").hide();
			        	$("#historyDataTableSearch").hide();
			        	$("#userGroupDiv").hide();
			        	$("#searchDivForEmployee").hide();
			        	$("#dashboardDiv").hide();
			        	
			        	$("#initiateLoading").removeAttr('style');
			        	
			        	$("#draftFrequencyDiv").empty();
			        	$("#draftFrequencyDiv").show();
			        	$("#draftFrequencyDiv").append(result);
			        	draftPagination();
	        }
	 }); 
}

function draftPagination(){  
    $('#draftPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		"sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
}

</script>

<script>
function dashboardTab(){
	  $("#overViewDiv").hide();
	  $("#userGroupDiv").hide();
	  $("#draftFrequencyDiv").hide();
	  $("#payrollFrequencyDiv").hide();
	  $("#historySearchDiv").hide();
	  $("#searchDivForEmployee").hide();
	  $("#dashboardDiv").show();
	  
	  location.replace(sourceleadUrl+ "/uSPayrollDashboard"); 
	
}
</script>


<jsp:include page="../masterFooter.jsp" />