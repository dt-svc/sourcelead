<jsp:include page="../../masterHeader.jsp" />
<jsp:include page="../../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

 <script>
$(document).ready(function(){
	$("#approveDiv").hide();
	$("#submitButtonDiv").hide();
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
<div class="pull-right brud-crum">Home &raquo; Payroll</div>
</section>
		<div class="success_pop" id="selector1" style="display: none;">
 			<i class="fa fa-check-circle"></i><span id="msgDiv"></span>
       </div>
<div class="project_wrap">
<div class="clearfix project-tabs-menu">
	<ul class="nav nav-tabs newTabs">
		<li class="current"><a onclick="reviewPayrollDiv();">Initiate Payroll</a></li>
		<li class=""><a onclick="#">Payroll</a></li>
		<li class=""><a onclick="#">Groups</a></li>
		<li class=""><a href="<c:url value='${appPath}/forms/list'/>" title="Forms">Forms</a></li>
	</ul>
   </div>
   </div>

<div class="clearfix"></div>
<div class="timesheet_out" id="reviewPayrollDiv">

<div class="tablelist-wrap">
<div class="timesheet_head minHeight">
<form:form id="generate_payroll_form" name="generate_payroll_form" method="post">
<div class="col-md-10 mt">
	<div class="row">
		<div class="col-md-3">
			<div class="dropdown-select">
			<select id="groupId" name="groupId" onchange="getGroupFrequency();" data-validation="required" field-name="User Group">
				<option value="">--Select User Group--</option>
                    <c:forEach var="ug" items="${userGroupList}">
					<option value="${ug.groupUUID},${ug.id}">${ug.groupName}</option>
				</c:forEach>
			</select>
			</div>
		</div>
		<div class="col-md-3 paddingLeft paddingRight">
			<select id="payPeriod" name="payPeriod" data-validation="required" field-name="Pay Period" class="chosen-select">
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
		
		<div class="col-md-2 paddingRight">
			<input name="" class="form-control calendar" placeholder="Check Date" id="payrollPayDate" name="payrollPayDate" type="text" data-validation="required" field-name="Pay Date">
		</div>
		<div class="col-md-2">
			<a  class="btn btn-success btnSuccess btn-sm anchorWhite" id="generateUSPayroll" onclick="runUSPayroll();">Initiate Payroll</a>
		</div>
	</div>						
</div>
<div class="clearfix"></div>
<div class="remove_search mt">
	<input placeholder="Search" type="text">
</div>              
</form:form>
</div>

  
 
<div class="tableScroll">
<form:form id="submit_for_approver" name="submit_for_approver" method="post">
 <div class="styleTables clearfix mt">
 <!-- <div id="USPayrollDiv"></div> -->
 <div id="payrollDataDiv"></div>
</div> 

<div class="clearfix mt"></div>

<div class="clearfix">
<!-- <div class="col-md-2"><a href="javascript:void(0)" class="btn anchorBlue">Exclude</a></div> -->
	<div id="submitButtonDiv">
	<ul class="pull-right mr list-unstyled list-inline">
		<li><a href="#" class="btn btn-primary btnSuccess" onClick="sendPayrollForApprover();">Submit for Approve</a></li>
		<li><a href="#" class="btn btn-default ml mr" onclick="confirmPayroll();">Confirm</a></li>
		<li><a href="#" class="btn btn-default" onclick="window.location='/app/payrollList'">Cancel</a></li>
	</ul>
	</div>
<div class="clearfix"></div>
</div>
</form:form>
</div>


</div>


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

<!-- <script>
function sendApproverFun() {
	getApprovers();
	$(".sendApprover, .comMask").fadeIn();
}

function closeApproverFun() {
	$(".sendApprover, .comMask").fadeOut();
}
</script> -->

<!-- New Code Here -->

<script>
function getGroupFrequency(){
	var groupUUID = $("#groupId").val();
	 $.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getGroupPayPeriod?"+tokenParamName+"="+tokenValue+"&groupUUID="+groupUUID                  , 
	        success: function(json) {
	        var output = "";
			for (var i=0; i<json.length; i++)
			{
				var date = json[i].replace(",", " to ");
				output = output + "<option value='" +json[i]+ "'>"+date+"</option>";
			}
			$("#payPeriod").empty();
			$("#payPeriod").append(output);
			$('#payPeriod').trigger("chosen:updated"); 
	        },
	    });
}
</script>
<!-- New Code End Here -->

<!--   Error Message   -->
<div class="vendor_wrap_mask"></div>
<div class="vendor_error">
<textarea placeholder="Enter message"></textarea>
<a class="close_color" href="javascript:void(0)">Yes</a><a href="javascript:void(0)">No</a> </div>

<!--   Error Message   --> 


<%-- 
<jsp:include page="payrollApprove.jsp" />
<jsp:include page="payrollAdjustment.jsp" /> --%>

</section>

 

  
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
		      //minDate: 0  this line for to restrict selection of previous dates
		  });
		
		
		
	});
</script>






<script>
function runUSPayroll(){
	if ($('#generate_payroll_form').validate(false, validationSettings)){  
		var searchValue = $("#dataTableSearch").val();
		searchValue = searchValue ? searchValue : "";
		var groupId = $("#groupId").val();
		var payPeriod = $("#payPeriod").val();
		var payrollPayDate = $("#payrollPayDate").val();
		var payType = $("#payType").val();
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/generateINDPayroll?searchValue="+searchValue+"&groupId="+groupId+"&payPeriod="+payPeriod+"&payrollPayDate="+payrollPayDate+"&paytype="+payType,                 
		        success: function(data) {
		            var json = data.indPayrollDTO;
		        	var result = "";
		        	result += '<table>';
		        	result += '<thead>';
		        	result += '<tr>';
		        	result += '<th>Exclude</th>';
		        	result += '<th>Emp ID</th>';
		        	result += '<th>Emp Name</th>';
		        	result += '<th>Payrate</th>';
		        	result += '<th>Regular<br>Hours</th>';
		        	result += '<th>OT<br>Hours</th>';
		        	result += '<th>No.of Leaves</th>';
		        	result += '<th>Gross<br>Pay</th>';
		        	result += '<th>Professional Tax</th>';
		        	result += '<th>TDS</th>';
		        	result += '<th>Net<br>Pay</th>';
		        	result += '</tr>';
		        	result += '</thead>	';
		        	result += '<tbody>';
		        	result += '<input type="hidden" value='+data.id+' id="payrollFrequencyId" name="payrollFrequencyId" />';
		        	result += '<input type="hidden" value='+data.payrollFrequencyUUID+' id="payrollFrequencyUUID" name="payrollFrequencyUUID" />';
		        	if(json != null && json.length > 0){
		        		for (var i=0; i<json.length; i++){
		        			var opt = json[i];
		        			result += '<tr>';
				        	 result += '<td>';
				        	result += '<div class="checkbox-new pull-left ml5">';
				        	result += '<input id="'+i+'emp1" value="YES" class="checkbox-custom" name="uspayroll['+i+'].isExcept" type="checkbox">';
				        	result += '<label for="'+i+'emp1" class="checkbox-custom-label"></label>';
				        	result += '</div>';
				        	result += '</td>'; 
				        	result += '<input type="hidden" value='+opt.empPayrollId+' id="'+i+'empPayrollId" name="uspayroll['+i+'].empPayrollId" />';
				        	if(opt.empId != null && opt.empId != 'undefined' && opt.empId != ''){
				        		result += '<td><div class="tblminwidth-50">'+opt.empId+'</div></td>';
				        	}else{
				        		result += '<td><div class="tblminwidth-50">--</div></td>';
				        	}
				        	result += '<td><div class="tbldata-dotdot-vsm">'+opt.empName+'</div></td>';
				        	result += '<td><div class="tbldata-dotdot-vsm">'+opt.payRate+'</div></td>';
				        	result += '<td><div class="tblminwidth-50">'+opt.regularHours+'</div><div class="tblminwidth-50 clearfix text-danger">$ '+ (opt.regularHrsTotalAmt !=null ? opt.regularHrsTotalAmt.toFixed(2) : 0) +'</div></td>';	
				        	
				        	if(opt.overtimeHours != null && opt.overtimeHours != '' && opt.overtimeHours != 'undefined'){
				        		result += '<td><div class="tblminwidth-50">'+opt.overtimeHours+'</div><div class="tblminwidth-50 clearfix text-danger">$ '+ (opt.otHoursTotalAmt !=null ? opt.otHoursTotalAmt.toFixed(2) : 0) +'</div></td>';
				        	}else{
				        		result += '<td><div class="tblminwidth-50">0.00</div><div class="tblminwidth-50 clearfix text-danger">$ 0.00</div></td>';
				        	}
				        	
				        	result += '<td><div class="tblminwidth-50 clearfix">$ '+ (opt.grossPay != null ? opt.grossPay.toFixed(2) : 0) +'</div>';
				        	if(opt.preTaxDeduction != null && opt.preTaxDeduction != '' && opt.preTaxDeduction != 'undefined'){
				        		result += '</td><td><div class="tblminwidth-50 clearfix">$ '+ (opt.preTaxDeduction != null ? opt.preTaxDeduction.toFixed(2) : 0) +'</div></td>';
				        	}else{
				        		result += '</td><td><div class="tblminwidth-50 clearfix">$ 0.000</td>';
				        	}
				        	if(opt.postTaxDeduction != null && opt.postTaxDeduction != '' && opt.postTaxDeduction != 'undefined'){
				        		result += '<td><div class="tblminwidth-50 clearfix">$ '+ (opt.postTaxDeduction != null ? opt.postTaxDeduction.toFixed(2) : 0) +'</div></td>';	
				        	}else{
				        		result += '<td><div class="tblminwidth-50 clearfix">$ 0.00</div></td>';
				        	}
				        	
				        	result += '<td><div class="tblminwidth-50 clearfix">$ '+ (opt.taxes != null ? opt.taxes.toFixed(2) : 0) +'</div></td>';
				        	result += '<td><div class="tblminwidth-50 clearfix"><a onClick="payAdjustment(\''+opt.empPayrollUUID+'\');">$ '+ (opt.netPay != null ? opt.netPay.toFixed(2) : 0) +'</a></div></td>';
				        	result += '</tr>';  
			        	}
			        	
		        	}
		        	result += '</tbody>';
		        	result += '</table>';
		        	$("#payrollDataDiv").empty();
		        	$("#payrollDataDiv").append(result);
		        	$("#submitButtonDiv").show();
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
<script type="text/javascript">
function sendPayrollForApprover(){
      var formData = $($('#submit_for_approver')[0]).serialize();
      $.post(sourceleadUrl+"/sendPayrollForApprover?"+tokenParamName+"="+tokenValue, formData, function(result) {
        if (result && result.response === "INVALID_DATA") {
              showError(result.responseMessage);
          } else if (result.response === "AWKWARD") {
              location.href = "/errorPage";
          } else if (result.response === "SUCCESS") {
        	  location.replace(sourceleadUrl+ "/payrollList"); 
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
	 var formData = $($('#submit_for_approver')[0]).serialize();
	 calculateTotalPay(formData,confirmType);
}
</script>

<script>
function msgFadein() {
	$("#selector1").fadeIn(200);
    $('#selector1').delay(2000).fadeOut('slow');
}

</script>

<jsp:include page="../../masterFooter.jsp" />