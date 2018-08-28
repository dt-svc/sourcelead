<%-- <jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script>
$(document).ready(function(){
   getPayrollDraftSummary();
});
</script>


<!-- Content Area -->
  <section class="content-wraper contentWraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Review Payroll</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Review Payroll</div>
    </section>
    
    <div class="tabsMenu clearfix">
   		<ul class="list-unstyled">
		   <li class="current"><a onclick="reviewPayroll();">Review Payroll</a></li>
		   <li><a  onclick="approvePayrollTab();">Approve Payroll</a></li>
	   </ul>
   </div>
   
    <div id="reviewPayrollDiv" class="candidate_wrap_table datatable_filter backgroundWhite">
   <form:form id="submit_for_approver" name="submit_for_approver" method="post">
    <div class="positionRelative timesheet_head minHeight clearfix"></div>
    	<div id="reviewDiv"></div>
    
    
            <nav class="clearfix">
          <ul class="pull-right mr list-unstyled list-inline">
<!--             <li><a href="#" class="btn btn-primary btnSuccess" onclick="sendPayrollForApproval();">Sent for Approve</a></li> -->
            <li><a href="#" class="btn btn-default ml mr" onclick="confirmPayroll();">Confirm</a></li>
            <li><a class="btn btn-default" href="<c:url value='${appPath}/payrollList'/>">Cancel</a></li>
          </ul>
          <div class="clearfix"></div>
        </nav>
    
    
    <div class="clearfix"></div>
    </form:form>
  </div>
  
  <jsp:include page="usPayrollApprove.jsp" />
  <jsp:include page="payrollAdjustment.jsp" />
  </section>
  <!-- Content Area Ends--> 
    
    
    
    <div class="sideMask"></div>
<div class="modal usRunpayroll" style="display: none;" id="usRunpayroll">
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
                <option value="Direct Deposit">Direct Deposit</option>
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
                          <td><input type="text" id="regularHoursAmt" name="regularHoursAmt"  class="formControl" readOnly></td>
                        </tr>
                        <tr>
                          <td>Overtime</td>
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
                          <td>Federal with holding</td>
                          <td><span id="federal">0</span></td>
                         <!--  <td><input type="text" id="federal" name="federal" value="" class="formControl" readOnly></td> -->
                        </tr>
                        <tr>
                          <td>Medicare</td>
                           <td><span id="medicare">0</span></td>
                         <!--  <td><input type="text" id="medicare" name="medicare" value="" class="formControl" readOnly></td> -->
                        </tr>
                        <tr>
                          <td>FICA</td>
                           <td><span id="fica">0</span></td>
                         <!-- <td><input type="text" id="fica" name="fica" value="" class="formControl" readOnly></td> -->
                        </tr>
                      
                        <tr>
                          <td>State Withholding</td>
                          <td><span id="stateWithHolding">0</span></td>
                         <!--  <td><input type="text" id="stateWithHolding" name="stateWithHolding" value="" class="formControl" readOnly></td> -->
                        </tr>
                          <tr>
                          <td>Advanced Earned Income Credit</td>
                           <td><span id="aeic">0</span></td>
                          <!-- <td><input type="text" id="aeic" name="aeic" value="124,783.00" class="formControl" readOnly></td> -->
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
              </tbody>
            </table>
          </div>
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" onClick="closeusRunpayrol();">Close</button>
      </div>
    </div>
  </div>
</div>
    
  
<script>
function getPayrollDraftSummary(){
	var sourceleadUrl = '${sourceleadUrl}';
	var uuid = '${paySheetFrequencyUUID}';
	 $.ajax({
		type : "GET",
		url : sourceleadUrl+ "/getPaySheetFrequencySummary?paySheetFrequencyuuid="+uuid+"&approverStatus=DRAFT",
		success : function(data) {
			    var result = "";
	        	result = result +'<table id="paySheetSummary">';
		    	result = result +'<thead>';
		    	result = result +'	<tr>';
		    	result = result +'	<th>Emp Name</th>';
		    	result = result +'	<th>Rate</th>';
		    	result = result +'	<th>Regular Hours</th>';
		    	result = result +'	<th>Overtime Hours</th>';
		    	result = result +'	<th>Gross Pay</th>';
		    	result = result +'	<th>Pre-Tax Deduction</th>';
		    	result = result +'	<th>Post-Tax Deduction</th>';
		    	result = result +'	<th>Taxes</th>';
		    	result = result +'    <th>Net Pay</th>'; 
		    	result = result +'    <th>Last Pay</th>'; 
		    	result = result +'    <th>YTD</th>'; 
		    	result = result +'     <th>Action</th>';
		    	result = result +'    </tr>';
		    	result = result +'</thead>	';
		    	result = result +'<tbody>';
		    	var json = data.payrollDTO;
		    	for (var i=0; i<json.length; i++)
   				{
		    	var report = json[i];
		    	result = result +'<tr>																													';	
	            if(report.empName != null && report.empName != '' && report.empName != 'undefined'){
	            	result = result +'  <td><div class="tbldata-dotdot-vsm">'+report.empName+'</div></td>                                                      ';
	            }else{
	            	result = result +'  <td><div class="tbldata-dotdot-vsm">--</div></td>                                                      ';
	            }
	            
	            if(report.payRate != null && report.payRate != '' && report.payRate != 'undefined'){
	            	result = result +'  <td><div class="tblminwidth-50 clearfix">'+report.payRate.toFixed(2)+'</div></td>                                                      ';
	            }else{
	            	result = result +'  <td><div class="tblminwidth-50 clearfix">--</div></td>                                                      ';
	            }
	            
	            if(report.regularHours != null && report.regularHours != '' && report.regularHours != 'undefined'){
	            	result = result +'  <td><div class="tblminwidth-50 clearfix">'+report.regularHours+'</div>                                                      ';
	            }else{
	            	result = result +'  <td><div class="tblminwidth-50 clearfix">--</div>                                                      ';
	            }
	            
	            if(report.regularHrsTotalAmt != null && report.regularHrsTotalAmt != '' && report.regularHrsTotalAmt != 'undefined'){
	            	result = result +'<div class="tblminwidth-50 clearfix">$'+report.regularHrsTotalAmt.toFixed(2)+'</div></td>            ';
	            }else{
	            	result = result +'<div class="tblminwidth-50 clearfix">$0.0</div></td>            ';
	            }
	            
	            if(report.overtimeHours != null && report.overtimeHours != '' && report.overtimeHours != 'undefined'){
	            	result = result +'  <td><div class="tblminwidth-50 clearfix">'+report.overtimeHours+'</div>';
	            }else{
	            	result = result +'	<td><div class="tblminwidth-50 clearfix">--</div>            ';
	            }
	            
	            if(report.otHoursTotalAmt != null && report.otHoursTotalAmt != '' && report.otHoursTotalAmt != 'undefined'){
	            	result = result +' <div class="tblminwidth-50 clearfix">$'+report.otHoursTotalAmt.toFixed(2)+'</div></td>            ';
	            }else{
	            	result = result +' <div class="tblminwidth-50 clearfix">$0.0</div></td>            ';
	            }
	            
	            if(report.grossPay != null && report.grossPay != '' && report.grossPay != 'undefined'){
	            	result = result +'  <td><div class="tblminwidth-50 clearfix">'+report.grossPay.toFixed(2)+'</div></td>                                                      ';
	            }else{
	            	result = result +'  <td><div class="tblminwidth-50 clearfix">0.0</div></td>                                                      ';
	            }
	            
	            if(report.preTaxDeduction != null && report.preTaxDeduction != '' && report.preTaxDeduction != 'undefined'){
	            	result = result +'  <td><div class="tblminwidth-50 clearfix">'+report.preTaxDeduction.toFixed(2)+'</div></td>                                                      ';
	            }else{
	            	result = result +'  <td><div class="tblminwidth-50 clearfix">0.0</div></td>                                                      ';
	            }
	            
	            if(report.postTaxDeduction != null && report.postTaxDeduction != '' && report.postTaxDeduction != 'undefined'){
	            	result = result +'  <td><div class="tblminwidth-50 clearfix">'+report.postTaxDeduction.toFixed(2)+'</div></td>                                                      ';
	            }else{
	            	result = result +'  <td><div class="tblminwidth-50 clearfix">0.0</div></td>                                                      ';
	            }
	            
	            if(report.taxes != null && report.taxes != '' && report.taxes != 'undefined'){
	            	result = result +'  <td><div class="tblminwidth-50 clearfix">'+report.taxes.toFixed(2)+'</div></td>                                                      ';
	            }else{
	            	result = result +'  <td><div class="tblminwidth-50 clearfix">0.0</div></td>                                                      ';
	            }
	            
	            if(report.netPay != null && report.netPay != '' && report.netPay != 'undefined'){
	            	result = result +'  <td><div class="tblminwidth-50 clearfix">'+report.netPay.toFixed(2)+'</div></td>                                                      ';
	            }else{
	            	result = result +'  <td><div class="tblminwidth-50 clearfix">0.0</div></td>                                                      ';
	            }
	            
	            result = result +'  <td><div class="tblminwidth-50 clearfix">0.0</div></td>                                                            ';
	            result = result +'  <td><div class="tblminwidth-50 clearfix">0.0</div></td>                                                         ';
	            result = result +'  <td>                                                                                                                ';
				result = result +'	  <div class="tblminwidth-30 text-center">                                                                          ';
				result = result +'	  	<a href="javascript:void(0)" class="anchorBlack"><i class="fa fa-eye" onclick="payAdjustment(\''+report.empPayrollUUID+'\');"></i></a>                                  ';
				result = result +'	  </div>                                                                                                            ';
	            result = result +'  </td>                                                                                                               ';
	            result = result +'</tr>                                                                                                                 ';
   			}
		    	result = result +'</tbody>';
			    $("#paySummaryDiv").empty();
			    $("#paySummaryDiv").append(result);
			    paySummaryPagination();
		}
	});
}
</script>

 <script>
function paySummaryPagination(){  
    $('#paySheetSummary').DataTable( { 
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
					            	output = output + ' <td><input type="text" name="contribution['+i+'].contributionAmount" value='+ot.amount+' class="formControl" readOnly></td>';
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
	                        payItem = payItem +'</tr>';
	                        if(json.payItem != null && json.payItem.length > 0){
			                	  for (var i=0; i< json.payItem.length; i++){
						            	var pi = json.payItem[i];
						            	payItem = payItem +' <tr>';
						            	payItem = payItem +' <td>'+pi.payItemName+'</td>';
						            	payItem = payItem +'<input type="hidden" value='+pi.id+' id="id" name="payItem['+i+'].id"/>';
						            	payItem = payItem +' <td><input type="text" class="formControl" id="payItemAmount" name="payItem['+i+'].payItemAmount" readOnly value='+pi.payItemAmount+'></td>';
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
</script>





 --%>





<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script>
$(document).ready(function(){
	$("#approveDiv").hide();
	getPayrollDraftSummary(); 
});
</script>


<script>
function reviewPayroll(){
	$("#approveDiv").hide();
	$("#reviewPayrollDiv").show();
}

function approvePayrollTab(){
	 $("#reviewPayrollDiv").hide();
	$("#approveDiv").show(); 
}
</script>

<!-- Content Area -->
  <section class="content-wraper contentWraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Review Payroll</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Review Payroll</div>
    </section>
    
    <div class="tabsMenu clearfix">
   		<ul class="list-unstyled">
		   <li class="current"><a onclick="reviewPayroll();">Review Payroll</a></li>
		   <li><a  onclick="approvePayrollTab();">Approve Payroll</a></li>
	   </ul>
   </div>
   
   <%--  <div id="reviewPayrollDiv" class="candidate_wrap_table datatable_filter backgroundWhite">
   <form:form id="submit_for_approver" name="submit_for_approver" method="post">
    <div class="positionRelative timesheet_head minHeight clearfix"></div>
    	<div id="reviewDiv"></div>
    
    
            <nav class="clearfix">
          <ul class="pull-right mr list-unstyled list-inline">
<!--             <li><a href="#" class="btn btn-primary btnSuccess" onclick="sendPayrollForApproval();">Sent for Approve</a></li> -->
            <li><a href="#" class="btn btn-default ml mr" onclick="confirmPayroll();">Confirm</a></li>
            <li><a class="btn btn-default" href="<c:url value='${appPath}/payrollList'/>">Cancel</a></li>
          </ul>
          <div class="clearfix"></div>
        </nav>
    
    
    <div class="clearfix"></div>
    </form:form>
  </div> --%>
  
  
  <div class="reviewPayrollDiv">
<form:form id="submit_for_approver"  name="submit_for_approver" method="post">
 <div class="styleTables clearfix mt">
 <!-- <div id="USPayrollDiv"></div> -->
 <div id="reviewDiv"></div>
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
  
  <jsp:include page="usPayrollApprove.jsp" />
  <jsp:include page="payrollAdjustment.jsp" />
  </section>
  <!-- Content Area Ends--> 
  
  <script>
  function getPayrollDraftSummary(){
	  var uuid = '${paySheetFrequencyUUID}';
		 $.ajax({
		    	type: "GET",
		    	url : sourceleadUrl+ "/getPaySheetFrequencySummary?paySheetFrequencyuuid="+uuid+"&approverStatus=DRAFT",                
		        success: function(data) {
					var json = data.payrollDTO;
		        	var result = "";
		        	result = result +'<table id="reviewTableId">';
			    	result = result +'<thead>';
			    	result = result +'	<tr>';
			    	result = result +'	<th>Exclude</th>';
			    	result = result +'	<th>Emp Id</th>';
			    	result = result +'	<th>Emp Name</th>';
			    	result = result +'	<th>Rate</th>';
			    	result = result +'	<th>Regular Hours</th>';
			    	result = result +'	<th>Overtime Hours</th>';
			    	result = result +'	<th>Gross Pay</th>';
			    	result = result +'	<th>Pre-Tax Deduction</th>';
			    	result = result +'	<th>Post-Tax Deduction</th>';
			    	result = result +'	<th>Taxes</th>';
			    	result = result +'    <th>Net Pay</th>'; 
			    	result = result +'    </tr>';
			    	result = result +'</thead>	';
			    	result = result +'<tbody>';
			    	result += '<input type="hidden" value='+data.id+' id="payrollFrequencyId" name="payrollFrequencyId" />';
		        	result += '<input type="hidden" value='+data.payrollFrequencyUUID+' id="payrollFrequencyUUID" name="payrollFrequencyUUID" />';
		        	result += '<input type="hidden" value='+data.payPeriodNum+' id="payPeriodNum" name="payPeriodNum" />';
			    	for (var i=0; i<json.length; i++)
	   				{
			    	var report = json[i];
			    	result = result +'<tr>																													';	
		            
			   	 	result += '<td>';
			   	 	if(json.length > 1){
		        	result += '<div class="checkbox-new pull-left ml5">';
		        	result += '<input id="'+i+'emp1" value="YES" class="checkbox-custom" name="uspayroll['+i+'].isExcept" type="checkbox">';
		        	result += '<label for="'+i+'emp1" class="checkbox-custom-label"></label>';
		        	result += '</div>';
			   	 	}
		        	result += '</td>'; 
		        	result += '<input type="hidden" value='+report.empPayrollId+' id="'+i+'empPayrollId" name="uspayroll['+i+'].empPayrollId" />';
			    	if(report.empId != null && report.empId != '' && report.empId != 'undefined'){
		            	result = result +'  <td><div class="tblminwidth-50 clearfix">'+report.empId+'</div></td>                                                      ';
		            }else{
		            	result = result +'  <td><div class="tblminwidth-50 clearfix">--</div></td>                                                      ';
		            }		
			    	
			    	if(report.empName != null && report.empName != '' && report.empName != 'undefined'){
		            	result = result +'  <td><div class="tbldata-dotdot-vsm">'+report.empName+'</div></td>                                                      ';
		            }else{
		            	result = result +'  <td><div class="tbldata-dotdot-vsm">--</div></td>                                                      ';
		            }
		            
		            if(report.payRate != null && report.payRate != '' && report.payRate != 'undefined'){
		            	result = result +'  <td><div class="tblminwidth-50 clearfix">'+report.payRate.toFixed(2)+'</div></td>                                                      ';
		            }else{
		            	result = result +'  <td><div class="tblminwidth-50 clearfix">--</div></td>                                                      ';
		            }
		            
		            if(report.regularHours != null && report.regularHours != '' && report.regularHours != 'undefined'){
		            	result = result +'  <td><div class="tblminwidth-50 clearfix">'+report.regularHours+'</div>                                                      ';
		            }else{
		            	result = result +'  <td><div class="tblminwidth-50 clearfix">--</div>                                                      ';
		            }
		            
		            if(report.regularHrsTotalAmt != null && report.regularHrsTotalAmt != '' && report.regularHrsTotalAmt != 'undefined'){
		            	result = result +'<div class="tblminwidth-50 clearfix">$'+report.regularHrsTotalAmt.toFixed(2)+'</div></td>            ';
		            }else{
		            	result = result +'<div class="tblminwidth-50 clearfix">$0.0</div></td>            ';
		            }
		            
		            if(report.overtimeHours != null && report.overtimeHours != '' && report.overtimeHours != 'undefined'){
		            	result = result +'  <td><div class="tblminwidth-50 clearfix">'+report.overtimeHours+'</div>';
		            }else{
		            	result = result +'	<td><div class="tblminwidth-50 clearfix">--</div>            ';
		            }
		            
		            if(report.otHoursTotalAmt != null && report.otHoursTotalAmt != '' && report.otHoursTotalAmt != 'undefined'){
		            	result = result +' <div class="tblminwidth-50 clearfix">$'+report.otHoursTotalAmt.toFixed(2)+'</div></td>            ';
		            }else{
		            	result = result +' <div class="tblminwidth-50 clearfix">$0.0</div></td>            ';
		            }
		            
		            if(report.grossPay != null && report.grossPay != '' && report.grossPay != 'undefined'){
		            	result = result +'  <td><div class="tblminwidth-50 clearfix">'+report.grossPay.toFixed(2)+'</div></td>                                                      ';
		            }else{
		            	result = result +'  <td><div class="tblminwidth-50 clearfix">0.0</div></td>                                                      ';
		            }
		            
		            if(report.preTaxDeduction != null && report.preTaxDeduction != '' && report.preTaxDeduction != 'undefined'){
		            	result = result +'  <td><div class="tblminwidth-50 clearfix">'+report.preTaxDeduction.toFixed(2)+'</div></td>                                                      ';
		            }else{
		            	result = result +'  <td><div class="tblminwidth-50 clearfix">0.0</div></td>                                                      ';
		            }
		            
		            if(report.postTaxDeduction != null && report.postTaxDeduction != '' && report.postTaxDeduction != 'undefined'){
		            	result = result +'  <td><div class="tblminwidth-50 clearfix">'+report.postTaxDeduction.toFixed(2)+'</div></td>                                                      ';
		            }else{
		            	result = result +'  <td><div class="tblminwidth-50 clearfix">0.0</div></td>                                                      ';
		            }
		            
		            if(report.taxes != null && report.taxes != '' && report.taxes != 'undefined'){
		            	result = result +'  <td><div class="tblminwidth-50 clearfix">'+report.taxes.toFixed(2)+'</div></td>                                                      ';
		            }else{
		            	result = result +'  <td><div class="tblminwidth-50 clearfix">0.0</div></td>                                                      ';
		            }
		            
		            if(report.netPay != null && report.netPay != '' && report.netPay != 'undefined'){
// 		            	result = result +'  <td><div class="tblminwidth-50 clearfix">'+report.netPay.toFixed(2)+'</div></td>                                                      ';
		            	result += '<td><div class="tblminwidth-50 clearfix"><a onClick="payAdjustment(\''+report.empPayrollUUID+'\',\'DRAFT\');">$ '+report.netPay.toFixed(2)+'</a></div></td>';
		            }else{
		            	result = result +'  <td><div class="tblminwidth-50 clearfix">0.0</div></td>                                                      ';
		            }
		            
		            result = result +'</tr>                                                                                                                 ';
	   			}
			    	result = result +'</tbody>';
				    $("#reviewDiv").empty();
				    $("#reviewDiv").append(result);
// 				    reviewPagination();
		        }
		    });
	  
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
	 var confirmType = "SENT";
	 var formData = $($('#submit_for_approver')[0]).serialize();
	 calculateTotalPay(formData,confirmType);
}
</script>

<jsp:include page="../masterFooter.jsp" />
