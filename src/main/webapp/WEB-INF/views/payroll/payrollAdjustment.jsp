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
        
        <div class="col-md-6">
        <div class="row">
            <label class="col-md-4"><strong>Employee Id:</strong></label>
            <div class="col-md-8">
              <p class="mb0" id="empId"></p>
            </div>
          </div>
        
        <div class="row">
            <label class="col-md-4"><strong>Name:</strong></label>
            <div class="col-md-8">
              <p class="mb0" id="empName"></p>
            </div>
          </div>
          <div class="row mt5">
            <label class="col-md-4"><strong>Address:</strong></label>
            <div class="col-md-8">
              <p class="mb0" id="empAddress"></p>
            </div>
          </div>
          <div class="row mt5">
            <label class="col-md-4"><strong>Deposit Type:</strong></label>
            <div class="col-md-8">
              <select class="sel" title="Select Type" id="depositType" name="depositType">
                <option selected value="Direct Deposit">Direct Deposit</option>
                <option value = "By Check">By Check</option>
              </select>
              <input type="hidden" id="payRate" name="payRate"/>
               <input type="hidden" id="payrollId" name="payrollId"/>
                <input type="hidden" id="payrollUUID" name="payrollUUID"/>
                 <input type="hidden" id="preRegularHours" name="regularHours"/>
                <input type="hidden" id="preRgularHoursAmt" name="preRegularHoursAmt"/>
                 <input type="hidden" id="preOverTimeHours" name="preOverTimeHours"/>
                <input type="hidden" id="preOverTimeHoursAmt" name="preOverTimeHoursAmt"/>
                 <input type="hidden" id="preDeductionLength" name="preDeductionLength" value="">
                 <input type="hidden" id="postDeductionLength" name="postDeductionLength" value="">
                 <input type="hidden" id="totalContributionsLngth" name="totalContributionsLngth" value="">
                 <input type="hidden" id="total1payItemsLength" name="total1payItemsLength" value="">
                 <input type="hidden" id="totalloanAdvanceLength" name="totalloanAdvanceLength" value="">
                 <input type="hidden" id="assSalType" name="assSalType" value="">
                 <input type="hidden" id="assSalary" name="assSalary" value="">
                 <input type="hidden" id="preAssSalaryAmount" name="preAssSalaryAmount" value="">
              
            </div>
          </div>
          </div>
          
      <div class="col-md-5">

			<table class="table">
				<thead><th>Type</th> <th>No. of Leaves</th> <th class="text-right">LOP Amount</th></thead>
				<tbody id="LOPDiv">
				</tbody>
				<tfoot><tr><th>Total Amount <i class="fa fa-dollar mr5"></i></th>
				<td colspan="2" class="noPad"><input type="text" id="totLopAmount" name="totLopAmount" readOnly class="payTotalFiled"></td>
				</tr></tfoot>
				<!-- <td colspan="2" class="text-right textBold"><span id="lopAmount"></span></td>< -->
			</table>

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
                     <table width="100%" class="table table-bordered" id="hoursAndEarningsTable">
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
                          <td><i class="fa fa-clock-o paddingRight"></i><input type="text" id="regularHours" name="regularHours"  onblur = "editRegularHours();" class="formControl" readOnly></td>
                          <td><i class="fa fa-dollar mr5"></i><input type="text" id="regularHoursAmt" name="regularHoursAmt"  class="formControl" readOnly></td>
                        </tr>
                        <tr>
                          <td>Overtime</td>
                          <td><i class="fa fa-clock-o paddingRight"></i><input type="text" id="overTimeHours" name="overTimeHours"   onblur = "editOverTimeHours();" class="formControl" readOnly></td>
                          <td><i class="fa fa-dollar mr5"></i><input type="text" id="overTimeHoursAmt" name="overTimeHoursAmt"  class="formControl" readOnly></td>
                        </tr>
                      </tbody>
                      <tfoot>
                        <tr>
                          <th>Total</th>
                          <td><i class="fa fa-clock-o paddingRight"></i><span id="totalHours" >00:00</span></td>
                          <td><i class="fa fa-dollar mr5"></i><span id="totalEarnings">0.0</span></td>
                        </tr>
                      </tfoot>
                    </table>
                    <table width="100%" class="table table-bordered" id="assSalaryTable">
                      <thead>
                        <tr>
                          <th colspan="4">Salary</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr>
                          <th>Pay Salary</th>
                          <td><i class="fa fa-dollar mr5"></i>
                          	<input type="text" id="assSalaryAmount" name="assSalaryAmount" readonly>
                          </td>
                        </tr>
                      </tbody>
                    </table>
                    </td>
                </tr>
                <tr>
                  <td colspan="2" id="payItemCol">
                  <div id="payItemDiv"></div>
                    </td>
                </tr>
                
                  <tr>
                  <td colspan="2" id="loanAdvanceCol">
                  <div id="loanAdvancesDiv"></div>
                    </td>
                </tr>
                 
                 <tr>
                  <td id="postDedCol">
                  <div id="postdedDiv"></div>
                    </td>
                  <td id="preDedCol">
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
                      <tbody id="taxesBodyDIV">
                        
                      </tbody>
                      <tfoot>
                        <tr>
                          <th>Total</th>
                          <td><i class="fa fa-dollar mr5"></i><span id="taxes">0.0</span></td>
                        </tr>
                      </tfoot>
                    </table>
                    </td>
                  <td id="contributionCol">
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
                            <td><i class="fa fa-dollar mr5"></i><span id="gross">0</span></td>
                            <td><i class="fa fa-dollar mr5"></i><span id="federalGross">0</span></td>
                            <td><i class="fa fa-dollar mr5"></i><span id="ficaGross">0</span></td>
                            <td><i class="fa fa-dollar mr5"></i><span id="totalTaxes">0</span></td>
                            <td><i class="fa fa-dollar mr5"></i><span id="dedTotal">0</span></td>
                            <td><i class="fa fa-dollar mr5"></i><span id="netpay">0</span></td>
                        </tr>
                        <tr>
                          <td>YTD</td>
                          <td><i class="fa fa-dollar mr5"></i><span id="ytdGross">0</span></td>
                          <td><i class="fa fa-dollar mr5"></i><span id="ytdFederal">0</span></td> 
                          <td><i class="fa fa-dollar mr5"></i><span id="ytdFICA">0</span></td>
                          <td><i class="fa fa-dollar mr5"></i><span id="ytdTaxes">0</span></td>
                          <td><i class="fa fa-dollar mr5"></i><span id="ytdDeduction">0</span></td>
                          <td><i class="fa fa-dollar mr5"></i><span id="ytdNetpay">0</span></td>
                        </tr>
                      </tbody>
                    </table></td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
        <div class="col-md-1 text-center mt">
            <div class="pull-right">
              <button type="button" class="btn btn-success btnSuccess editUspayslip btn-sm" onClick="editUspayslip(event)" id="editUspaysliptext">Edit</button>
            </div>
          </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" onClick="closeusRunpayrol();">Close</button>
      </div>
      <div id="editPayrollLoading"></div>
    </div>
  </div>
  </form:form>
</div>



<script>
	function payAdjustment(payrollUUID,status){
		$.ajax({
		  	type: "GET",
		        url: sourceleadUrl+"/getEmployeePayroll?payrollUUID="+payrollUUID+"&status="+status,                 
		        success: function(data) {
		            var json = data.object;
		            if(json.empId != null){
		            	$("#empId").text(json.empId);
		            }else{
		            	$("#empId").text("--");
		            }
		            $("#empName").text(json.empName);
		            $("#empAddress").text(json.employeeAddress);
		            
		            $("#assSalType").val(json.assSalType);
            		$("#assSalary").val(json.assSalary);
		            if(json.assSalType == "Salary" || json.assSalType == "LcaWage"){
		            	$("#hoursAndEarningsTable").hide();
		            	$("#assSalaryTable").show();
		            	$("#assSalaryAmount").val(json.grossPay);
		            	$("#preAssSalaryAmount").val(json.grossPay);
		            }else{
		            	$("#hoursAndEarningsTable").show();
		            	$("#assSalaryTable").hide();
		            	$("#regularHours").val(json.regularHours);
			            $("#regularHoursAmt").val(json.regularHrsTotalAmt.toFixed(2));
			            $("#overTimeHours").val(json.overtimeHours);
			            $("#overTimeHoursAmt").val(json.otHoursTotalAmt.toFixed(2));
			            $("#totalHours").text(json.totalHours.toFixed(2));
			            $("#totalEarnings").text(json.totalEarnings.toFixed(2));
		            }
		            
		            $("#preRegularHours").val(json.regularHours);
		            $("#preRegularHoursAmt").val(json.regularHrsTotalAmt.toFixed(2));
		            $("#preOverTimeHours").val(json.overtimeHours);
		            $("#preOverTimeHoursAmt").val(json.otHoursTotalAmt.toFixed(2));
		            
		            $("#totLopAmount").val(json.lopAmount);
		            
		            // Taxes Tax
		            
			        var taxes = "";
			        taxes = taxes + '<tr>';
			        taxes = taxes + '<th>Description</th>';
			        taxes = taxes + '<th>Amount</th>';
			        taxes = taxes + '</tr>';
			        for(var e in json.tax){
			        	//json.tax[e]
			        	if(json.tax[e] > 0){
			        		taxes = taxes + '<tr>';
				        	taxes = taxes + '<td>'+e+'</td>';
				        	taxes = taxes + '<td><i class="fa fa-dollar mr5"></i>'+json.tax[e]+'</td>';
				        	taxes = taxes + ' </tr>';
			        	}
			        }
		            $("#taxesBodyDIV").empty();
		            $("#taxesBodyDIV").append(taxes);
		           /*  $("#federalWT").text(json.federal);
		            $("#medicareWT").text(json.medicare);
		            $("#ficaWT").text(json.fica);
		            $("#stateWithHoldingWT").text(json.stateWithHolding);
		            $("#aeicWT").text(json.aeic);
		            $("#localTaxWT").text(json.localTax); */
		            $("#taxes").text(json.taxes);
		            
		            $("#gross").text(json.grossPay);
		            $("#federalGross").text(json.federal);
		            $("#ficaGross").text(json.fica);
		            $("#totalTaxes").text(json.taxes);
		            $("#dedTotal").text(json.totalDeduction);
		            $("#netpay").text(json.netPay);
		            $("#payRate").val(json.payRate);
		            $("#payrollId").val(json.payrollId);
		            $("#payrollUUID").val(json.payrollUUID);
		            
		             // YTD      
		             var ytd = json.ytdDTO;
	                 $("#ytdGross").text(ytd.ytdGross);
	                 $("#ytdFederal").text(ytd.ytdFederal);
	                 $("#ytdFICA").text(ytd.ytdFICA);
	                 $("#ytdTaxes").text(ytd.ytdTaxes);
	                 $("#ytdDeduction").text(ytd.ytdDeduction);
	                 $("#ytdNetpay").text(ytd.ytdNet);
		             
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
			            if(json.postDed != null && json.postDed.length > 0){ 
			            	  for (var i=0; i< json.postDed.length; i++){
					            	var ded = json.postDed[i];
				        			postDed = postDed + '<tr>';
				        			postDed = postDed + ' <td>'+ded.deductionType+'</td>';
				        			postDed = postDed + ' <td>';
				        			postDed = postDed + ' <input type="hidden"  name="postDeduction['+i+'].id" value='+ded.id+'>';
				        			postDed = postDed + ' <input type="hidden"  name="postDeduction['+i+'].preDeductionType" value='+ded.deductionType+'>';
				        			postDed = postDed + ' <input type="hidden"  name="postDeduction['+i+'].preDeductionAmt" value='+ded.postDeductionAmt+' > ';
				        			postDed = postDed + '<i class="fa fa-dollar paddingRight"></i><input type="text" id="'+i+'amount" name="postDeduction['+i+'].amount" value='+ded.postDeductionAmt+' class="formControl" readOnly style="width:94.5%"></td>';
				        			postDed = postDed + ' </tr>';
					            }	
			            	  $("#postDeductionLength").val(json.postDed.length);
			            	  
			            	  
			            	  postDed = postDed + '</tbody>';
		                      postDed = postDed + '<tfoot>';
		                      postDed = postDed + '<tr>';
		                      postDed = postDed + '<th>Total</th>';
		                      postDed = postDed + '<td><span id="postDeductionTotal">$ '+json.postDedTotal+'</span></td>';
		                      postDed = postDed + '</tr>';
		                      postDed = postDed + '</tfoot>';
		                      postDed = postDed + '</table>';
		                      $("#postDedCol").show(); 
		                      $("#postdedDiv").empty(); 
		                      $("#postdedDiv").append(postDed);
			            	  
			            }else{
			            	$("#postDedCol").hide(); 
			            }
	                     
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
	                        if(json.preDed != null && json.preDed.length > 0){
				            for (var i=0; i< json.preDed.length; i++){
				            	var preded = json.preDed[i];
				            	preDed = preDed + '<tr>';
				            	preDed = preDed + ' <td>'+preded.deductionType+'</td>';
				            	preDed = preDed + ' <td>';
				            	preDed = preDed + ' <input type="hidden"  name="preDeduction['+i+'].id" value='+preded.id+'>';
				            	preDed = preDed + ' <input type="hidden"  name="preDeduction['+i+'].preDeductionType" value='+preded.deductionType+'>';
				            	preDed = preDed + ' <input type="hidden"  name="preDeduction['+i+'].preDeductionAmt" value='+preded.preDeductionAmount+' >'; 
				            	preDed = preDed + ' <i class="fa fa-dollar paddingRight"></i><input type="text" id="preDeductionAmount'+i+'" name="preDeduction['+i+'].amount" value='+preded.preDeductionAmount+' class="formControl" readOnly></td>';
				            	preDed = preDed + ' </tr>';
				            }
				            $("#preDeductionLength").val(json.preDed.length);
				            
				            preDed = preDed + '</tbody>';
				            preDed = preDed + '<tfoot>';
				            preDed = preDed + '<tr>';
				            preDed = preDed + '<th>Total</th>';
				            preDed = preDed + '<td><span id="preDeductionTotal">$ '+json.preDedTotal+'</span></td>';
				            preDed = preDed + '</tr>';
				            preDed = preDed + '</tfoot>';
				            preDed = preDed + '</table>';
				              $("#preDedCol").show();
		                      $("#prededDiv").empty();
		                      $("#prededDiv").append(preDed);
	                        }else{
	                        	 $("#preDedCol").hide();
	                        }
		                      
		                      
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
					            	output = output + '<td>';
					            	output = output + ' <input type="hidden"  name="contribution['+i+'].id" value='+ot.id+'>';
					            	output = output + ' <input type="hidden"  name="contribution['+i+'].taxType" value='+ot.taxType+'>';
					            	output = output + ' <input type="hidden"  name="contribution['+i+'].preContributionType" value='+ot.contributionType+'>';
					            	output = output + ' <input type="hidden"  name="contribution['+i+'].preContributionAmt" value='+ot.contributionAmt+' >'; 
					            	output = output + '<i class="fa fa-dollar paddingRight"></i><input type="text" id="totalContributions'+i+'" name="contribution['+i+'].contributionAmount" value='+ot.contributionAmt+' class="formControl" readOnly></td>';
					            	output = output + '</tr>';
			                  }
		                	  $("#totalContributionsLngth").val(json.contribution.length);
		                	  
		                	  	output = output + ' </tbody>';
		                        output = output + ' <tfoot>';
		                        output = output + '   <tr>';
		                        output = output + '  <th>Total</th>';
		                        output = output + '  <td><span id="totalGrandContributions">$ '+json.contributionTotal+'</span></td>';
		                        output = output + '  </tr>';
		                        output = output + '  </tfoot>';
		                        output = output + '  </table>';
		                        $("#contributionCol").show();
		                        $("#employerPaidBenefitDiv").empty();
		                        $("#employerPaidBenefitDiv").append(output);
		                  }else{
		                	  $("#contributionCol").hide();
		                  }
	                        
	                        
	                        // PayItems
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
	                       /*  payItem = payItem +'		<input id="blockAll" class="checkbox-custom" name="PartofaCTC" type="checkbox">'; */
	                        payItem = payItem +'		<label class="checkbox-custom-label"><span class="ml10">Block</span></label>';
	                        payItem = payItem +'	</div>';
	                        payItem = payItem +'    </th>';
	                        payItem = payItem +'</tr>';
	                        if(json.payItem != null && json.payItem.length > 0){
			                	  for (var i=0; i< json.payItem.length; i++){
						            	var pi = json.payItem[i];
						            	payItem = payItem +' <tr>';
						            	payItem = payItem +' <td>'+pi.payItemName+'</td>';	
						            	payItem = payItem + '<td>';
						            	payItem = payItem +'<input type="hidden" value='+pi.id+' id="id" name="payItem['+i+'].payItemId"/>';
						            	payItem = payItem +'<input type="hidden" value='+pi.payItemName+' id="prePayItemName" name="payItem['+i+'].prePayItemName"/>';
						            	payItem = payItem +'<input type="hidden" value='+pi.payItemAmt+' id="prePayItemAmt" name="payItem['+i+'].prePayItemAmt"/>';
						            	payItem = payItem +' <i class="fa fa-dollar paddingRight"></i><input type="text" class="formControl" id="payItemAmount'+i+'" name="payItem['+i+'].amount" readOnly value='+pi.payItemAmt+'></td>';
						            	payItem = payItem +' <td>';
						            	payItem = payItem +' <div class="checkbox-new ml mr pull-left">';
						            	if(pi.isBlocked == 'YES' && pi.isBlocked != null){
						            		payItem = payItem +' <input id="cashTips['+i+']" checked class="checkbox-custom" name="payItem['+i+'].payItemCheck" type="checkbox">';
							            	payItem = payItem +' <label for="cashTips['+i+']" class="checkbox-custom-label"></label>';
						            	}else{
						            		if(pi.payItemName != null && pi.payItemName != '' && pi.payItemName != 'undefined'){
						            			payItem = payItem +' <input id="cashTips['+i+']" class="checkbox-custom" name="payItem['+i+'].payItemCheck" type="checkbox">';
								            	payItem = payItem +' <label for="cashTips['+i+']" class="checkbox-custom-label"></label>';	
						            		}else{
						            			payItem = payItem +' <input type="text" value="--" readonly>';
						            		}
						            		
						            	}
						            	
						            	payItem = payItem +' </div>';
						            	payItem = payItem +' </td>';
						            	payItem = payItem +' </tr>';
				                  }
			                	  $("#total1payItemsLength").val(json.payItem.length);
			                	  
			                	  
			                	  payItem = payItem +'</tbody>';
		                          payItem = payItem +'<tfoot>';
		                          payItem = payItem +' <tr>';
		                          payItem = payItem +'  <th>Total</th>';
		                          payItem = payItem +'<td><span id="payItemTotalAmount">$ '+json.payItemTotal+'</span></td>';
		                          payItem = payItem +' <td></td>';
		                          payItem = payItem +' </tr>';
		                          payItem = payItem +' </tfoot>';
		                          payItem = payItem +' </table>';
		                          $("#payItemCol").show();
		                          $("#payItemDiv").empty();
		                          $("#payItemDiv").append(payItem)
		                          $(".tablePayslip").find(".fa-dollar, .fa-clock-o").next(".formControl").css({"width":"94.5%"});
			                  }else{
			                	  $("#payItemCol").hide();
			                  }
	                        
	                        
	                        // Loan And Advances
	                        var loanAdvance = "";
	                        
	                        loanAdvance = loanAdvance +'<table width="100%" class="table table-bordered">';
	                        loanAdvance = loanAdvance +'<thead>';
	                        loanAdvance = loanAdvance +' <tr>';
	                        loanAdvance = loanAdvance +'<th colspan="4">Loan & Advances</th>';
	                        loanAdvance = loanAdvance +'</tr>';
	                        loanAdvance = loanAdvance +'  </thead>';
	                        loanAdvance = loanAdvance +' <tbody>';
	                        loanAdvance = loanAdvance +' <tr>';
	                        loanAdvance = loanAdvance +'  <th>Loan Name</th>';
	                        loanAdvance = loanAdvance +'  <th>Amount</th>';
	                        loanAdvance = loanAdvance +'</tr>';
	                        if(json.loanAdvance != null && json.loanAdvance.length > 0){
			                	  for (var i=0; i< json.loanAdvance.length; i++){
						            	var pi = json.loanAdvance[i];
						            	loanAdvance = loanAdvance +' <tr>';
						            	loanAdvance = loanAdvance +' <td>'+pi.loanAndAdvName+'</td>';	
						            	loanAdvance = loanAdvance +' <td><i class="fa fa-dollar paddingRight"></i><input type="text" id="loanAmount'+i+'" name="loanAdvance['+i+'].loanAmount" readOnly value='+pi.loanAmount+'></td>';
						            	loanAdvance = loanAdvance +' </tr>';
				                  }
			                	  $("#totalloanAdvanceLength").val(json.loanAdvance.length);
			                	  
			                	  
			                	  loanAdvance = loanAdvance +'</tbody>';
		                          loanAdvance = loanAdvance +'<tfoot>';
		                          loanAdvance = loanAdvance +' <tr>';
		                          loanAdvance = loanAdvance +'  <th>Total</th>';
		                          loanAdvance = loanAdvance +'<td><span id="loanTotalAmount">$ '+json.loanAmountTotal+'</span></td>';
		                          loanAdvance = loanAdvance +' </tr>';
		                          loanAdvance = loanAdvance +' </tfoot>';
		                          loanAdvance = loanAdvance +' </table>';
		                          $("#loanAdvanceCol").show();
		                          $("#loanAdvancesDiv").empty();
		                          $("#loanAdvancesDiv").append(loanAdvance)
		                          $(".tablePayslip").find(".fa-dollar, .fa-clock-o").next(".formControl").css({"width":"94.5%"});
			                  }else{
			                	  $("#loanAdvanceCol").hide();
			                  }
	                        
	                        var lopres = "";
	                        var lopData = json.lopHis;
	                        if(lopData != '' && lopData.length > 0 ){
	                        	for(var i = 0 ; i< lopData.length;i++){
	                        		var lopJson = lopData[i];
	                        		lopres = lopres + '<tr>';
	                        		lopres = lopres +'<input type="hidden" value='+lopJson.id+' id="id" name="lopHis['+i+'].id"/>';
	                        		lopres = lopres +'<input type="hidden" value='+lopJson.leaveTypeName+' id="leaveTypeName" name="lopHis['+i+'].leaveTypeName"/>';
	                        		lopres = lopres +'<input type="hidden" value='+lopJson.lopAmount+' id="lopAmount" name="lopHis['+i+'].lopAmount"/>';
	                        		lopres = lopres + '<td class="text-left checkBox"><input type="checkbox" name="lopHis['+i+'].lopExempt" id="lopHis['+i+'].lopExempt" class="noMar" onClick="excludeFun();"><span class="ml10">'+lopJson.leaveTypeName+'</span></td>';
	                        		lopres = lopres + '<td class="text-left">'+lopJson.lopLeaves+'</td>';
	                        		lopres = lopres + '<td class="text-right">'+lopJson.lopAmount+' <div class="text-right text-danger small" style="display:none" id="exclude">Excluded</div></td>';
	                        		lopres = lopres + '</tr>';
	                        	} 
	                        }else{
	                        	lopres = lopres + '<tr><td class="text-center">--</td><td class="text-center">--</td><td class="text-center">--</td></tr>';
	                        }
	                        
	                        $("#LOPDiv").empty();
 	                        $("#LOPDiv").append(lopres);
	                        /* () lopres = lopres + '<tr><td class="text-left">Normal Leave</td><td class="text-right">0.7</td></tr>';
	                        lopres = lopres + '<tr><td class="text-left">Total Leaves</td><td class="text-right">1,4</td></tr>'; */
	                        
	                       
	                          
		        },
		    }); 
		$(".usRunpayroll").fadeIn();
		$(".sideMask").fadeIn(); 
	}
	function closeusRunpayrol(){
		$(".usRunpayroll").fadeOut();
		$(".sideMask").fadeOut();
		setTimeout(function(){
			var text = document.getElementById("editUspaysliptext");
			if(text.innerHTML === "Save")
				text.innerHTML = "Edit";
		}, 1000)
	};
</script> 

<script>
function calculatePreTaxDedAmount(){
	var maxCount = $("#preDeductionLength").val();
	var amount = 0;
	for(var i=0;i<maxCount; i++){
 		var preEditAmount = $("#preDeductionAmount"+i+"").val();
		if(preEditAmount != null && preEditAmount != '' && preEditAmount != 'undefined')
			{
			amount += parseFloat(preEditAmount);
			}
		else
			{
			$("#preDeductionTotal").html("$ "+amount+""); 
			}
	} 
	$("#preDeductionTotal").html("$ "+amount+""); 
}

function calculatePostTaxDedAmount(){
	var maxCount = $("#postDeductionLength").val();
	var amount = 0;
	for(var i=0;i<maxCount; i++){
		var editedAmount = $("#"+i+"amount").val()
		if(editedAmount != null && editedAmount != '' && editedAmount != 'undefined')
			amount += parseFloat(editedAmount);
		else
			$("#postDeductionTotal").html("$ "+amount+""); 
	} 
	$("#postDeductionTotal").html("$ "+amount+""); 
}

function calculateTotalCotributionsAmount(){
	var maxCount = $("#totalContributionsLngth").val();
	var amount = 0;
	for(var i=0;i<maxCount; i++){
		var contriAmount = $("#totalContributions"+i+"").val();
		if(contriAmount != null && contriAmount != '' && contriAmount != 'undefined')
			amount += parseFloat(contriAmount);
		else
			$("#totalGrandContributions").html("$ "+amount+""); 
	} 
	$("#totalGrandContributions").html("$ "+amount+""); 
}

function calculateTotalPayItemAmount(){
	var maxCount = $("#total1payItemsLength").val();
	var amount = 0;
	for(var i=0;i<maxCount; i++){
		var pItemAmount = $("#payItemAmount"+i+"").val();
		if(pItemAmount != null && pItemAmount != '' && pItemAmount != 'undefined')
			amount += parseFloat(pItemAmount);
		else
			$("#payItemTotalAmount").html("$ "+amount+""); 
	} 
	$("#payItemTotalAmount").html("$ "+amount+""); 
}

/* function calculateTotalLoanAmount(){
	var maxCount = $("#totalloanAdvanceLength").val();
	var amount = 0;
	for(var i=1;i<maxCount; i++){
		amount += parseFloat($("#loanAmount"+i+"").val());
	} 
	$("#loanTotalAmount").html("$ "+amount+""); 
} */
</script>
<script>
$(function(){
	$(".tablePayslip").find(".fa-dollar, .fa-clock-o").next(".formControl").css({"width":"94.5%"});
});
function editUspayslip(event){
	var maxPreCount = $("#preDeductionLength").val();
	var maxPostCount = $("#postDeductionLength").val();
	var maxContriCount = $("#totalContributionsLngth").val();
	var maxPayItemCount = $("#total1payItemsLength").val();
// 	var maxLoanAdvanceCount = $("#totalloanAdvanceLength").val();
	var text = document.getElementById("editUspaysliptext");
	if(text.innerHTML === "Edit") {
		$("#assSalaryAmount").attr("readonly", false);
		text.innerHTML = "Save";
			for(var i = 0; i < maxPreCount; i++){
				$("#preDeductionAmount"+i+"").attr("onblur","calculatePreTaxDedAmount();");
			}
		
		for(var i = 0; i < maxPostCount; i++){
			$("#"+i+"amount").attr("onblur","calculatePostTaxDedAmount();");
		}
		for(var i = 0; i < maxContriCount; i++){
			$("#totalContributions"+i+"").attr("onblur","calculateTotalCotributionsAmount();");
		}
		for(var i = 0; i < maxPayItemCount; i++){
			$("#payItemAmount"+i+"").attr("onblur","calculateTotalPayItemAmount();");
		}
	/* 	for(var i = 0; i < maxLoanAdvanceCount; i++){
			$("#loanAmount"+i+"").attr("onblur","calculateTotalLoanAmount();");
		} */
	}else {
		$("#assSalaryAmount").attr("readonly", true);
		text.innerHTML = "Edit";
		for(var i = 0; i < maxPreCount; i++){
			$("#preDeductionAmount"+i+"").removeAttr("onblur");
		}
		for(var i = 0; i < maxPostCount; i++){
			$("#"+i+"amount").removeAttr("onblur");
		}
		for(var i = 0; i < maxContriCount; i++){
			$("#totalContributions"+i+"").removeAttr("onblur");
		}
		for(var i = 0; i < maxPayItemCount; i++){
			$("#payItemAmount"+i+"").removeAttr("onblur");
		}
		/* for(var i = 0; i < maxLoanAdvanceCount; i++){
			$("#loanAmount"+i+"").removeAttr("onblur");
		} */
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
	  	$("#editPayrollLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		$.post(sourceleadUrl+"/editEmployeePayroll?"+tokenParamName+"="+tokenValue, formData, function(result) {
			$("#editPayrollLoading").removeAttr('style');
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

<script>
//excude
function excludeFun(){
	/* $("#lopExempt").val("YES"); */
	$("#exclude").toggle();
}
</script>


