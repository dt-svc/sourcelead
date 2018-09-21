<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

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
                          <td><span id="federalWT">0</span></td>
                        </tr>
                        <tr>
                          <td>Medicare</td>
                           <td><span id="medicareWT">0</span></td>
                        </tr>
                        <tr>
                          <td>FICA</td>
                           <td><span id="ficaWT">0</span></td>
                        </tr>
                      
                        <tr>
                          <td>State Withholding</td>
                          <td><span id="stateWithHoldingWT">0</span></td>
                        </tr>
                          <tr>
                          <td>Advanced Earned Income Credit</td>
                           <td><span id="aeicWT">0</span></td>
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
                      <tbody>json.federal
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
		            
		            $("#federalWT").text(json.federal);
		            $("#medicareWT").text(json.medicare);
		            $("#ficaWT").text(json.fica);
		            $("#stateWithHoldingWT").text(json.stateWithHolding);
		            $("#aeicWT").text(json.aeic);
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
</script> 


<script>
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
</script>


<script>
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
</script>

<script>
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
 </script>
 
 <script>
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
 </script>



