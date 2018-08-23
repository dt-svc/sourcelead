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