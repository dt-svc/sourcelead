<%-- <jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
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





!-- Content Area -->
<section class="content-wraper contentWraper">
<section class="content-header clearfix">
<div class="pull-left">
<h2>Payroll</h2>
</div>
<div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo;<a href="<c:url value='${appPath}/payrollList'/>">payrollList</a> &raquo; Payroll</div>

</section>
		<div class="success_pop" id="selector1" style="display: none;">
 			<i class="fa fa-check-circle"></i><span id="msgDiv"></span>
       </div>

<div class="tabsMenu clearfix">
   		<ul class="list-unstyled">
		   <li class="current"><a onclick="reviewPayrollDiv();">Review Payroll</a></li>
		    <li class="current"><a onclick="approvePayrollDiv();">Approve Payroll</a></li>
		   <li><a href="<c:url value='${appPath}/approvePayrollDiv'/>" >Approve Payroll</a></li>

	   </ul>
 </div>

<div class="clearfix"></div>
<div class="timesheet_out" id="reviewPayrollDiv">

<div class="tablelist-wrap">
<div class="timesheet_head minHeight">
<form:form id="generate_payroll_form" name="generate_payroll_form" method="post">
<div class="col-md-7 mt">
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
		<div class="col-md-4 paddingLeft paddingRight">
			<select id="payPeriod" name="payPeriod" data-validation="required" field-name="Pay Period" class="chosen-select" onchange="clearPayrollDiv();">
			</select>
		</div>
		<div class="col-md-3 paddingRight">
			<input name="" class="form-control calendar" placeholder="Check Date" id="payrollPayDate" name="payrollPayDate" type="text" data-validation="required" field-name="Pay Date">
		</div>
		<div class="col-md-2">
			<a  class="btn btn-success btnSuccess btn-sm anchorWhite" id="generateUSPayroll" onclick="runUSPayroll();">Initiate Payroll</a>
		</div>
	</div>						
</div>
<div class="clearfix"></div>
<div class="remove_search">
	<input placeholder="Search" type="text">
</div>              
</form:form>
</div>

  
 
<div class="tableScroll">
<form:form id="submit_for_approver"  name="submit_for_approver" method="post">
 <div class="styleTables clearfix mt">
 <!-- <div id="USPayrollDiv"></div> -->
 <div id="payrollDataDiv"></div>
 <div id="initiateLoading"></div>
</div> 

<div class="clearfix mt"></div>

<div class="clearfix">
<!-- <div class="col-md-2"><a href="javascript:void(0)" class="btn anchorBlue">Exclude</a></div> -->
	<div id="submitButtonDiv">
	<ul class="pull-right mr list-unstyled list-inline">
		<li><a href="#" class="btn btn-primary btnSuccess" id="Submit" onClick="sendPayrollForApprover();">Submit for Approve</a></li>
		<li><a href="#" class="btn btn-default ml mr" id="Confirm" onclick="confirmPayroll();">Confirm</a></li>
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
	        output = output + "<option value=''>select ferquency</option>";
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
	 $("#payrollDataDiv").empty();
	 $('#payrollPayDate').val("").datepicker("update");
	 $("#submitButtonDiv").hide();
}
</script>
<!-- New Code End Here -->

<!--   Error Message   -->
<div class="vendor_wrap_mask"></div>
<div class="vendor_error">
<textarea placeholder="Enter message"></textarea>
<a class="close_color" href="javascript:void(0)">Yes</a><a href="javascript:void(0)">No</a> </div>

<!--   Error Message   --> 



<jsp:include page="usPayrollApprove.jsp" />
<jsp:include page="payrollAdjustment.jsp" />

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
		
		$("#initiateLoading").attr('style','position:absolute; width:100%; height:140%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		
		var searchValue = $("#dataTableSearch").val();
		searchValue = searchValue ? searchValue : "";
		var groupId = $("#groupId").val();
		var payPeriod = $("#payPeriod").val();
		var payrollPayDate = $("#payrollPayDate").val();
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/generateUSPayroll?searchValue="+searchValue+"&groupId="+groupId+"&payPeriod="+payPeriod+"&payrollPayDate="+payrollPayDate,                 
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
				        	result += '<th>Payrate</th>';
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
						        	result += '<td><div class="tbldata-dotdot-vsm">'+opt.payRate+'</div></td>';
						        	
						        	if(opt.overtimeHours != null && opt.overtimeHours != '' && opt.overtimeHours != 'undefined'){
						        		result += '<td><div class="tblminwidth-50">'+opt.overtimeHours+'</div><div class="tblminwidth-50 clearfix text-danger">$ '+opt.otHoursTotalAmt.toFixed(2)+'</div></td>';
						        	}else{
						        		result += '<td><div class="tblminwidth-50">0.00</div><div class="tblminwidth-50 clearfix text-danger">$ 0.00</div></td>';
						        	}
						        	
						        	result += '<td><div class="tblminwidth-50 clearfix">$ '+opt.grossPay.toFixed(2)+'</div>';
						        	if(opt.preTaxDeduction != null && opt.preTaxDeduction != '' && opt.preTaxDeduction != 'undefined'){
						        		result += '</td><td><div class="tblminwidth-50 clearfix">$ '+opt.preTaxDeduction.toFixed(2)+'</div></td>';
						        	}else{
						        		result += '</td><td><div class="tblminwidth-50 clearfix">$ 0.000</td>';
						        	}
						        	if(opt.postTaxDeduction != null && opt.postTaxDeduction != '' && opt.postTaxDeduction != 'undefined'){
						        		result += '<td><div class="tblminwidth-50 clearfix">$ '+opt.postTaxDeduction.toFixed(2)+'</div></td>';	
						        	}else{
						        		result += '<td><div class="tblminwidth-50 clearfix">$ 0.00</div></td>';
						        	}
						        	
						        	result += '<td><div class="tblminwidth-50 clearfix">$ '+opt.taxes.toFixed(2)+'</div></td>';
						        	result += '<td><div class="tblminwidth-50 clearfix"><a onClick="payAdjustment(\''+opt.empPayrollUUID+'\');">$ '+opt.netPay.toFixed(2)+'</a></div></td>';
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
				        	$("#payrollDataDiv").empty();
				        	$("#payrollDataDiv").append(result);
				        	$("#submitButtonDiv").show();
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

<!-- <script>
	function payAdjustment(payrollUUID){
		  $.ajax({
		  	type: "GET",
		        url: sourceleadUrl+"/getEmployeePayroll?payrollUUID="+payrollUUID,                 
		        success: function(data) {
		            var json = data.object;
		            $("#empAddress").text(json.employeeAddress);
		            $("#regularHours").val(json.regularHours.toFixed(2));
		            $("#regularHoursAmt").val(json.regularHrsTotalAmt.toFixed(2));
		            $("#overTimeHours").val(json.overtimeHours.toFixed(2));
		            $("#overTimeHoursAmt").val(json.otHoursTotalAmt.toFixed(2));
		            $("#totalHours").text(json.totalHours.toFixed(2));
		            $("#totalEarnings").text(json.totalEarnings.toFixed(2));
		        	
		            $("#federal").text(json.federal);
		            $("#medicare").text(json.medicare);
		            $("#fica").text(json.fica);
		            $("#stateWithHolding").text(json.stateWithHolding);
		            $("#aeic").text(json.aeic);
		            $("#taxes").text(json.taxes);
		            
		            $("#gross").text(json.grossPay);
		            $("#federalGross").text(json.federal);
		            $("#ficaGross").text(json.fica);
		            $("#totalTaxes").text(json.taxes);
		            $("#dedTotal").text(9999);
		            $("#netpay").text(json.netPay);
		            $("#payRate").val(json.payRate);
		            $("#payrollId").val(json.payrollId);
		            $("#payrollUUID").val(json.payrollUUID);
		             
		                var postDed = "";
	        			postDed = postDed + '<table width="100%" class="table table-bordered" >';
			            postDed = postDed + '<thead>';
			            postDed = postDed + '<tr>';
			            postDed = postDed + '<th colspan="2">Post Tax Deduction</th>';
			            postDed = postDed + '</tr>';
			            postDed = postDed + '</thead>';
			            postDed = postDed + '<tbody>';
			            postDed = postDed + '<tr>';
			            postDed = postDed + '<th>Description</th>';
			            postDed = postDed + '<th>Amount</th>';
			            postDed = postDed + ' </tr>';
			            if(json.postDed != null && json.postDed.length){
			            	  for (var i=0; i< json.postDed.length; i++){
					            	var ded = json.postDed[i];
				        			postDed = postDed + '<tr>';
				        			postDed = postDed + ' <td>'+ded.deductionType+'</td>';
				        			postDed = postDed + ' <td><input type="text" id="'+i+'amount" name="postDeduction['+i+'].amount" value='+ded.amount+' class="formControl" readOnly></td>';
				        			postDed = postDed + ' </tr>';
					            }	
			            }
	                      postDed = postDed + '</tbody>';
	                      postDed = postDed + '<tfoot>';
	                      postDed = postDed + '<tr>';
	                      postDed = postDed + '<th>Total</th>';
	                      postDed = postDed + '<td><span>'+json.postDedTotal+'</span></td>';
	                      postDed = postDed + '</tr>';
	                      postDed = postDed + '</tfoot>';
	                      postDed = postDed + '</table>';
	                     $("#postdedDiv").empty(); 
	                      $("#postdedDiv").append(postDed);
	                     
	                        var preDed = "";
	                        preDed = preDed + '<table width="100%" class="table table-bordered" >';
	                        preDed = preDed + '<thead>';
	                        preDed = preDed + '<tr>';
	                        preDed = preDed + '<th colspan="2">Pre Tax Deduction</th>';
	                        preDed = preDed + '</tr>';
	                        preDed = preDed + '</thead>';
	                        preDed = preDed + '<tbody>';
	                        preDed = preDed + '<tr>';
	                        preDed = preDed + '<th>Description</th>';
	                        preDed = preDed + '<th>Amount</th>';
	                        preDed = preDed + ' </tr>';
	                        if(json.preDed != null && json.preDed.length){
				            for (var i=0; i< json.preDed.length; i++){
				            	var preded = json.preDed[i];
				            	preDed = preDed + '<tr>';
				            	preDed = preDed + ' <td>'+preded.deductionType+'</td>';
				            	preDed = preDed + ' <td><input type="text" id="amount" name="preDeduction['+i+'].amount" value='+preded.amount+' class="formControl" readOnly></td>';
				            	preDed = preDed + ' </tr>';
				            }
	                        }
				            preDed = preDed + '</tbody>';
				            preDed = preDed + '<tfoot>';
				            preDed = preDed + '<tr>';
				            preDed = preDed + '<th>Total</th>';
				            preDed = preDed + '<td><span>$ '+json.preDedTotal+'</span></td>';
				            preDed = preDed + '</tr>';
				            preDed = preDed + '</tfoot>';
				            preDed = preDed + '</table>';
		                      $("#prededDiv").empty();
		                      $("#prededDiv").append(preDed);
		                      
		                      
		                  var output = "";
		                  output = output + '<table width="100%" class="table table-bordered">';
		                  output = output + '<thead>';
		                  output = output + '<tr>';
		                  output = output + '<th colspan="2">Contributions</th>';
		                  output = output + ' </tr>';
		                  output = output + '</thead>';
		                  output = output + ' <tbody>';
		                  output = output + ' <tr>';
		                  output = output + '  <th>Description</th>';
		                  output = output + '  <th>Current</th>';
		                  output = output + ' </tr>';
		                  if(json.contribution != null && json.contribution.length > 0){
		                	  for (var i=0; i< json.contribution.length; i++){
					            	var ot = json.contribution[i];
					            	output = output + '<tr>';
					            	output = output + '<input type="hidden" name="contribution['+i+'].taxtType" value='+ot.taxtType+' id="taxtType"/>';
					            	output = output + '<td>'+ot.contributionType+'</td>';
					            	output = output + ' <td><input type="text" name="contribution['+i+'].contributionAmount" value='+ot.contributionAmount+' class="formControl" readOnly></td>';
					            	output = output + '</tr>';
			                  }
		                  }
	                        output = output + ' </tbody>';
	                        output = output + ' <tfoot>';
	                        output = output + '   <tr>';
	                        output = output + '  <th>Total</th>';
	                        output = output + '  <td><span>$ '+json.contributionTotal+'</span></td>';
	                        output = output + '  </tr>';
	                        output = output + '  </tfoot>';
	                        output = output + '  </table>';
	                        $("#employerPaidBenefitDiv").empty();
	                        $("#employerPaidBenefitDiv").append(output);
	                        
	                        
	                        var payItem = "";
	                        
	                        payItem = payItem +'<table width="100%" class="table table-bordered">';
	                        payItem = payItem +'<thead>';
	                        payItem = payItem +' <tr>';
	                        payItem = payItem +'<th colspan="4">Pay Items</th>';
	                        payItem = payItem +'</tr>';
	                        payItem = payItem +'  </thead>';
	                        payItem = payItem +' <tbody>';
	                        payItem = payItem +' <tr>';
	                        payItem = payItem +'  <th>Item Name</th>';
	                        payItem = payItem +'  <th>Amount</th>';
	                        payItem = payItem +' <th>';
	                        payItem = payItem +' <div class="checkbox-new ml mr pull-left">';
	                        payItem = payItem +'		<input id="blockAll" class="checkbox-custom" name="PartofaCTC" type="checkbox">';
	                        payItem = payItem +'		<label for="blockAll" class="checkbox-custom-label"><span class="ml10">Block</span></label>';
	                        payItem = payItem +'	</div>';
	                        payItem = payItem +'    </th>';
	                        payItem = payItem +'</tr>';
	                        if(json.payItem != null && json.payItem.length > 0){
			                	  for (var i=0; i< json.payItem.length; i++){
						            	var pi = json.payItem[i];
						            	payItem = payItem +' <tr>';
						            	payItem = payItem +' <td>'+pi.payItemName+'</td>';
						            	payItem = payItem +'<input type="hidden" value='+pi.id+' id="id" name="payItem['+i+'].id"/>';
						            	payItem = payItem +' <td><input type="text" class="formControl" id="payItemAmount" name="payItem['+i+'].payItemAmount" readOnly value='+pi.payItemAmount+'></td>';
						            	payItem = payItem +' <td>';
						            	payItem = payItem +' <div class="checkbox-new ml mr pull-left">';
						            	payItem = payItem +' <input id="cashTips" class="checkbox-custom" name="payItem['+i+'].payItemCheck" type="checkbox">';
						            	payItem = payItem +' <label for="cashTips" class="checkbox-custom-label"></label>';
						            	payItem = payItem +' </div>';
						            	payItem = payItem +' </td>';
						            	payItem = payItem +' </tr>';
				                  }
			                  }
	                          
	                          payItem = payItem +'</tbody>';
	                          payItem = payItem +'<tfoot>';
	                          payItem = payItem +' <tr>';
	                          payItem = payItem +'  <th>Total</th>';
	                          payItem = payItem +'<td><span>$ '+json.payItemTotal+'</span></td>';
	                          payItem = payItem +' <td></td>';
	                          payItem = payItem +' </tr>';
	                          payItem = payItem +' </tfoot>';
	                          payItem = payItem +' </table>';
	                          $("#payItemDiv").empty();
	                          $("#payItemDiv").append(payItem)
                       
		        },
		    }); 
		$(".usRunpayroll").fadeIn();
		$(".sideMask").fadeIn(); 
	}
	function closeusRunpayrol(){
		$(".usRunpayroll").fadeOut();
		$(".sideMask").fadeOut();
	};
</script> -->

 <!-- Net Edit popup -->

<div class="sideMask"></div>
<div class="modal usRunpayroll" style="display: none;" id="usRunpayroll">
<form:form id="payroll_adjustment_form" name="payroll_adjustment_form" method="post">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close hidden" data-dismiss="modal"  onclick="closeusRunpayrol();">&times;</button>
        <h4 class="modal-title hidden">Pay Stub</h4>
        <div class="row">
          <div class="col-md-3">
            <label class="clearfix"><strong>Address</strong></label>
            <div class="clearfix">
              <p class="mb0" id="empAddress"></p>
            </div>
          </div>
          <div class="col-md-3">
            <label class="clearfix"><strong>Deposit Type</strong></label>
            <div class="clearfix">
              <select class="sel" title="Select Type" id="depositType" name="depositType">
                <option selected value="Direct Deposit">Direct Deposit</option>
                <option value = "By Check">By Check</option>
              </select>
              <input type="hidden" id="payRate" name="payRate"/>
               <input type="hidden" id="payrollId" name="payrollId"/>
                <input type="hidden" id="payrollUUID" name="payrollUUID"/>
            </div>
          </div>
          
          <div class="col-md-2 text-center">
            <label class="clearfix"><strong>No. Leaves</strong></label>
            <div class="clearfix">
              <p class="mt5 mb0">30</p>
            </div>
          </div>
        
        </div>
      </div>
      <div class="modal-body" style="min-height: 150px; max-height: 425px; overflow-y: auto; padding: 5px 15px;">
        <div class="row">
          <div class="col-md-12 tablePayslip" id="tablePayslip">
            <table width="100%" class="table">
              <tbody>
                <tr>
                  <td colspan="2">
                     <table width="100%" class="table table-bordered">
                      <thead>
                        <tr>
                          <th colspan="4">Hours &amp; Earnings</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr>
                          <th>Description</th>
                          <th>No. of Hours</th>
                          <th>Earning</th>
                        </tr>
                        <tr>
                          <td>Regular</td>
                          <td><input type="text" id="regularHours" name="regularHours"  onblur = "editRegularHours();" class="formControl" readOnly></td>
                          <td><input type="text" id="regularHoursAmt" name="regularHoursAmt"  class="formControl" readOnly></td>
                        </tr>
                        <tr>
                          <td>Overtime</td>
                          <td><input type="text" id="overTimeHours" name="overTimeHours"   onblur = "editOverTimeHours();" class="formControl" readOnly></td>
                          <td><input type="text" id="overTimeHoursAmt" name="overTimeHoursAmt"  class="formControl" readOnly></td>
                        </tr>
                      </tbody>
                      <tfoot>
                        <tr>
                          <th>Total</th>
                          <td><span id="totalHours" >0</span></td>
                          <td><span id="totalEarnings">0</span></td>
                        </tr>
                      </tfoot>
                    </table>
                    </td>
                </tr>
                <tr>
                  <td colspan="2">
                  <div id="payItemDiv"></div>
                    </td>
                </tr>
                 
                 <tr>
                  <td>
                  <div id="postdedDiv"></div>
                    </td>
                  <td>
                  <div id="prededDiv"></div>
                    </td>
                </tr>
                <tr>
                  <td><table width="100%" class="table table-bordered">
                      <thead>
                        <tr>
                          <th colspan="2">Taxes</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr>
                          <th>Description</th>
                          <th>Amount</th>
                        </tr>
                        <tr>
                          <td>Federal Withholding</td>
                          <td><span id="federal">0</span></td>
                        </tr>
                        <tr>
                          <td>Medicare</td>
                           <td><span id="medicare">0</span></td>
                        </tr>
                        <tr>
                          <td>FICA</td>
                           <td><span id="fica">0</span></td>
                        </tr>
                      
                        <tr>
                          <td>State Withholding</td>
                          <td><span id="stateWithHolding">0</span></td>
                        </tr>
                          <tr>
                          <td>Advanced Earned Income Credit</td>
                           <td><span id="aeic">0</span></td>
                        </tr>
                        
                      </tbody>
                      <tfoot>
                        <tr>
                          <th>Total</th>
                          <td><span id="taxes">0</span></td>
                        </tr>
                      </tfoot>
                    </table>
                    </td>
                  <td>
                  <div id="employerPaidBenefitDiv"></div>
                    </td>
                </tr>
                <tr>
                  <td colspan="2"><table class="table table-bordered">
                      <thead>
                      <th>&nbsp;</th>
                        <th>Total Gross</th>
                        <th>Fed Taxable Gross</th>
                        <th>FICA Gross</th>
                        <th>Total Taxes</th>
                        <th>Total Deductions</th>
                        <th>Net Pay</th>
                          </thead>
                      <tbody>
                        <tr>
                          <td>Current</td>
                            <td><span id="gross">0</span></td>
                            <td><span id="federalGross">0</span></td>
                            <td><span id="ficaGross">0</span></td>
                            <td><span id="totalTaxes">0</span></td>
                            <td><span id="dedTotal">0</span></td>
                            <td><span id="netpay">0</span></td>
                        </tr>
                        <tr>
                          <td>YTD</td>
                          <td>xxxx</td>
                          <td>xxxx</td> 
                          <td>xxxx</td>
                          <td>xxxx</td>
                          <td>xxxx</td>
                          <td>xxxx</td>
                        </tr>
                      </tbody>
                    </table></td>
                </tr>
              <!--   <tr>
                  <td><table class="table table-bordered">
                      <tbody>
                        <tr>
                          <th>Total Gross</th>
                          <td>2,062.53</td>
                        </tr>
                        <tr>
                          <th>Before-Tax Deductions</th>
                          <td>2,062.53</td>
                        </tr>
                        <tr>
                          <th>Life Insurance Premiums</th>
                          <td>2,062.53</td>
                        </tr>
                        <tr>
                          <th>Fed Taxable and FICA</th>
                          <td>2,062.53</td>
                        </tr>
                      </tbody>
                    </table></td>
                  <td><table class="table table-bordered">
                      <thead>
                        <tr>
                          <th colspan="2">Net Pay Disctibution</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr>
                          <th>Advice#1006795</th>
                          <td>2,062.53</td>
                        </tr>
                        <tr>
                          <th>Total</th>
                          <td>2,062.53</td>
                        </tr>
                      </tbody>
                    </table></td>
                </tr> -->
              </tbody>
            </table>
          </div>
        </div>
      </div>
        <div class="col-md-1 text-center">
            <div class="pull-right">
              <button type="button" class="btn btn-success btnSuccess editUspayslip btn-sm" onClick="editUspayslip(event)" id="editUspaysliptext">Edit</button>
            </div>
          </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" onClick="closeusRunpayrol();">Close</button>
      </div>
    </div>
  </div>
  </form:form>
</div>


//Net payedit Script
<!-- 	<script>
	function editUspayslip(event){
		var text = document.getElementById("editUspaysliptext");
		if(text.innerHTML === "Edit") {
			text.innerHTML = "Save"
		}else {
			text.innerHTML = "Edit"
			savePayrollAdjustment();
		}
		
	$("#tablePayslip").find(".formControl").toggleClass("activeEdit");
		var x = document.getElementsByClassName("formControl"), i;
		for (i=0; i<x.length; i++) {
			if(x[i].readOnly) {
			x[i].readOnly = false;
			}else {
			x[i].readOnly = true;
			}
		}
	}
</script> -->

<!-- Net Edit popup Ends -->

<!-- <script>
function savePayrollAdjustment(){
	  var formData = getFormData("payroll_adjustment_form");
	 /* var formData = $($('#payroll_adjustment_form')[0]).serialize(); */
	  	//$("#paysheetLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		$.post(sourceleadUrl+"/editEmployeePayroll?"+tokenParamName+"="+tokenValue, formData, function(result) {
			if (result.response === "INVALID_DATA") {
 		    showError(result.responseMessage);
 		} else if (result.response === "AWKWARD") {
 		    location.href = "/errorPage";
 		} else if (result.response === "SUCCESS") {
 			closeusRunpayrol();
 			runUSPayroll();
 		} else if (result.indexOf('Sign In') !== -1) {
 			location.replace(sourceleadUrl);
 		} else {
 		    showError("Operation failed, please try again");
 		}
			/*  $("#paysheetLoading").hide(); */
		});
}
</script> -->
 
 
<!--  <script>
 function editRegularHours(){
	var regularHours = $("#regularHours").val();
	var overTimeHours = $("#overTimeHours").val();
	var payRate = $("#payRate").val();
	 var perMinuteRate = payRate /60;
	 var split = regularHours.split('.');
     var hours = split[0];
	 var minute = split[1];
	 var totlalAmount = 0;
	 if(typeof minute !== "undefined"){
		 var hourminits = hours * 60;
		 var finalmin = parseFloat(hourminits) + parseFloat(minute);
		 totlalAmount = (perMinuteRate * finalmin);
	 }else{
		 var hourminits = hours * 60;
		 totlalAmount = (perMinuteRate * hourminits);
	 }
	 $("#regularHoursAmt").val(totlalAmount.toFixed(2));
	 combineRegularOverTimeHours(regularHours,overTimeHours);
	 var overTimeAmt = $("#overTimeHoursAmt").val();
	 calculateTotalEarnings(totlalAmount,overTimeAmt);
 }
 </script> -->
 
 <!-- <script>
 function editOverTimeHours(){
	  var payRate = $("#payRate").val();
	  var overTimeHours = $("#overTimeHours").val();
	  var regularHours = $("#regularHours").val();
	 var perMinuteRate = payRate /60;
	 var otSplit = overTimeHours.split('.');
     var otHours = otSplit[0];
	 var otMinute = otSplit[1];
	 var totlalOtAmount = 0;
	 if(typeof otMinute !== "undefined"){
		 var hourminits = otHours * 60;
		 var finalmin = parseFloat(hourminits) + parseFloat(otMinute);
		 totlalOtAmount = (perMinuteRate * finalmin);
	 }else{
		 var hourminits = otHours * 60;
		 totlalOtAmount = (perMinuteRate * hourminits);
	 }
	 $("#overTimeHoursAmt").val(totlalOtAmount.toFixed(2));
	 combineRegularOverTimeHours(regularHours,overTimeHours);
	 var regularAmt = $("#regularHoursAmt").val();
	 calculateTotalEarnings(regularAmt,totlalOtAmount)
 }
 </script>
 
 <script>
 function combineRegularOverTimeHours(regularHours,overTimeHours){
	
	 var minute = regularHours.split(".")[0] * 60;
     var minute1 = 0; 
     var regMinute = regularHours.split(".")[1] ;
     if(typeof regMinute !== "undefined"){
    	 minute1 = regularHours.split(".")[1] ;
     }
     
     var minute2 = overTimeHours.split(".")[0] * 60;
     var minute3 = 0; 
     var otMinute = overTimeHours.split(".")[1] ;
     if(typeof otMinute !== "undefined"){
    	 minute3 = overTimeHours.split(".")[1] ;
     }
     
     var bsum = parseInt(minute) + parseInt(minute1)+parseInt(minute2) + parseInt(minute3);
     
    var t = bsum; 
    var hours = Math.floor(t / 60); 
    var minutes = t % 60;
    
    if (minutes < 10) {
        minutes = '0' + minutes;
    }
    if (hours < 10) {
        hours = '0' + hours;
    }
    var total;
    if(!isNaN(hours) && !isNaN(minutes)){
        total = hours +"."+ minutes;
    }else{
        total =  "00.00";
    }
    $("#totalHours").text(total);
 }
 </script>
 
 <script>
 function calculateTotalEarnings(regularHourAmt,overTimeHourAmt){
	 var totalEarn = parseFloat(regularHourAmt) + parseFloat(overTimeHourAmt);
	 $("#totalEarnings").text(totalEarn.toFixed(2));
 }
 </script> -->
 





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

<script>
function clearPayrollDiv(){
// 	$("#groupId").val('');
	$("#payrollPayDate").val('');
	$("#payrollDataDiv").empty();
	$("#submitButtonDiv").hide();
}
</script>

<jsp:include page="../masterFooter.jsp" /> --%>