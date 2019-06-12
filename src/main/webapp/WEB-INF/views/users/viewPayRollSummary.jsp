
<div class="sideMask"></div>
<div class="modal usRunpayroll" style="display: none;" id="usRunpayroll">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close hidden" data-dismiss="modal"  onclick="closeusRunpayrol();">&times;</button>
        <h4 class="modal-title hidden">Pay Stub</h4>
        <div class="row">
          <div class="clearfix mb5">
            <label class="col-md-2"><strong>Employee Id:</strong></label>
            <div class="col-md-5">
              <p class="mb0" id="empId"></p>
            </div>
          </div>
        <div class="clearfix mb5">
            <label class="col-md-2"><strong>Name:</strong></label>
            <div class="col-md-5">
              <p class="mb0" id="empName"></p>
            </div>
        </div>
        
          <div class="clearfix mb5">
            <label class="col-md-2"><strong>Address:</strong></label>
            <div class="col-md-5">
              <p class="mb0" id="empAddress"></p>
            </div>
          </div>
          
          <div class="clearfix mb5">
            <label class="col-md-2"><strong>Deposit Type:</strong></label>
            <div class="col-md-5">
              <input type="text" id="depositType" name="depositType" value="" readOnly>
              <input type="hidden" id="payRate" name="payRate"/>
               <input type="hidden" id="payrollId" name="payrollId"/>
                <input type="hidden" id="payrollUUID" name="payrollUUID"/>
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
                           <td><i class="fa fa-clock-o paddingRight"></i><input type="text" id="regularHoursView" name="regularHours"  onblur = "editRegularHours();" class="formControl" readOnly></td>
                          <td><i class="fa fa-dollar mr5"></i><input type="text" id="regularHoursAmt" name="regularHoursAmt"  class="formControl" readOnly></td>
                        </tr>
                        <tr>
                          <td>Overtime</td>
                          <td><i class="fa fa-clock-o paddingRight"></i><input type="text" id="overTimeHoursView" name="overTimeHours"   onblur = "editOverTimeHours();" class="formControl" readOnly></td>
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
                          <th>Pay Salary</th><td><i class="fa fa-dollar mr5"></i><input type="text" id="assSalaryAmount" name="assSalaryAmount" readOnly></td>
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
                  <td id="contributionsCol">
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
      <div class="modal-footer">
        <button type="button" class="btn btn-default" onClick="closeusRunpayrol();">Close</button>
      </div>
    </div>
  </div>
</div>

<script>
	function payAdjustment(payrollUUID,status){
		var pmsUrl ='${SourceLeadWeb_pmsUrl}';
		 $.ajax({
		  	type: "GET",
		        url: pmsUrl+"/getEmployeePayroll?payrollUUID="+payrollUUID+"&status="+status,                 
		        success: function(data) {
		            var json = data.object;
		            if(json.empId != null){
		            	$("#empId").text(json.empId);
		            }else{
		            	$("#empId").text("--");
		            }
		            $("#empName").text(json.empName);
		            $("#empAddress").text(json.employeeAddress);
		            $("#depositType").val(json.depositType);
		            if(json.assSalType == "Salary" || json.assSalType == "LcaWage"){
		            	$("#hoursAndEarningsTable").hide();
		            	$("#assSalaryTable").show();
		            	$("#assSalaryAmount").val(json.grossPay);
		            }else{
		            	$("#hoursAndEarningsTable").show();
		            	$("#assSalaryTable").hide();
		            	$("#regularHoursView").val(json.regularHours.toFixed(2));
			            $("#regularHoursAmt").val(json.regularHrsTotalAmt.toFixed(2));
			            $("#overTimeHoursView").val(json.overtimeHours.toFixed(2));
			            $("#overTimeHoursAmt").val(json.otHoursTotalAmt.toFixed(2));
			            $("#totalHours").text(json.totalHours.toFixed(2));
			            $("#totalEarnings").text(json.totalEarnings.toFixed(2));
		            }
		        	
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
		            /* $("#federalWT").text(json.federal);
		            $("#medicareWT").text(json.medicare);
		            $("#ficaWT").text(json.fica);
		            $("#stateWithHoldingWT").text(json.stateWithHolding);
		            $("#aeic").text(json.aeic); */
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
			            if(json.postDed != null && json.postDed.length){
			            	  for (var i=0; i< json.postDed.length; i++){
					            	var ded = json.postDed[i];
				        			postDed = postDed + '<tr>';
				        			postDed = postDed + ' <td>'+ded.deductionType+'</td>';
				        			postDed = postDed + ' <td><i class="fa fa-dollar paddingRight"></i><input type="text" id="'+i+'amount" name="postDeduction['+i+'].amount" value='+ded.postDeductionAmt+' class="formControl width90percnt" readOnly></td>';
				        			postDed = postDed + ' </tr>';
					            }	
			            	  
			            	  postDed = postDed + '</tbody>';
		                      postDed = postDed + '<tfoot>';
		                      postDed = postDed + '<tr>';
		                      postDed = postDed + '<th>Total</th>';
		                      postDed = postDed + '<td><span>$ '+json.postDedTotal+'</span></td>';
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
	                        if(json.preDed != null && json.preDed.length){
				            for (var i=0; i< json.preDed.length; i++){
				            	var preded = json.preDed[i];
				            	preDed = preDed + '<tr>';
				            	preDed = preDed + ' <td>'+preded.deductionType+'</td>';
				            	preDed = preDed + ' <td><i class="fa fa-dollar paddingRight"></i><input type="text" id="amount" name="preDeduction['+i+'].amount" value='+preded.preDeductionAmount+' class="formControl width90percnt" readOnly></td>';
				            	preDed = preDed + ' </tr>';
				            }
				            
				            preDed = preDed + '</tbody>';
				            preDed = preDed + '<tfoot>';
				            preDed = preDed + '<tr>';
				            preDed = preDed + '<th>Total</th>';
				            preDed = preDed + '<td><span>$ '+json.preDedTotal+'</span></td>';
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
					            	output = output + ' <td><i class="fa fa-dollar paddingRight"></i><input type="text" name="contribution['+i+'].contributionAmount" value='+ot.contributionAmt+' class="formControl width90percnt" readOnly></td>';
					            	output = output + '</tr>';
			                  }
		                	  
		                	  output = output + ' </tbody>';
		                        output = output + ' <tfoot>';
		                        output = output + '   <tr>';
		                        output = output + '  <th>Total</th>';
		                        output = output + '  <td><span>$ '+json.contributionTotal.toFixed(2)+'</span></td>';
		                        output = output + '  </tr>';
		                        output = output + '  </tfoot>';
		                        output = output + '  </table>';
		                        $("#contributionsCol").show();
		                        $("#employerPaidBenefitDiv").empty();
		                        $("#employerPaidBenefitDiv").append(output);
		                  }else{
		                	  $("#contributionsCol").hide();
		                  }
	                        
	                        
	                        var payItem = "";
	                        
	                        payItem = payItem +'<table width="100%" class="table table-bordered">';
	                        payItem = payItem +'<thead>';
	                        payItem = payItem +' <tr>';
	                        payItem = payItem +'<th colspan="3">Pay Items</th>';
	                        payItem = payItem +'</tr>';
	                        payItem = payItem +'  </thead>';
	                        payItem = payItem +' <tbody>';
	                        payItem = payItem +' <tr>';
	                        payItem = payItem +'  <th>Item Name</th>';
	                        payItem = payItem +'  <th>Amount</th>';
	                        payItem = payItem +'  <th></th>';
	                        payItem = payItem +'</tr>';
	                        if(json.payItem != null && json.payItem.length > 0){
			                	  for (var j=0; j< json.payItem.length; j++){
						            	var pi = json.payItem[j];
						            	 payItem = payItem +' <tr>';
						            	payItem = payItem +' <td>'+pi.payItemName+'</td>';
						            	payItem = payItem +'<input type="hidden" value='+pi.id+' id="id" name="payItem['+i+'].id"/>';
							            payItem = payItem +' <td><i class="fa fa-dollar paddingRight"></i><input type="text" class="formControl" id="payItemAmount" name="payItem['+i+'].payItemAmount" readOnly value='+pi.payItemAmt+'></td>';
							           if(pi.isBlocked == 'YES'){
								            payItem = payItem +' <td><input type="text" class="formControl text-danger" readonly value="Blocked"></td>';
							            }else{
								            payItem = payItem +' <td><input type="text" class="formControl text-danger" readonly value=""></td>';
							              }  
						            	payItem = payItem +' </tr>'; 
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
		                          $("#payItemCol").show();
		                          $("#payItemDiv").empty();
		                          $("#payItemDiv").append(payItem);
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