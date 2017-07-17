
<script>
function bisChecksList(){
	
	var userId = '${SourceLeadWeb_user.id}';
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	
	$("#loading").attr('style','position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		  var sourceleadUrl ='${sourceleadUrl}';
				  $.ajax({
				    	type: "GET",
				        url: sourceleadUrl+"/getChecksList?${_csrf.parameterName}=${_csrf.token}?userId="+userId+"&orgLocationId="+orgLocationId,
				        success: function(data) {
						 var result = "";				        	
						result = result + '<table id="rajashekar" class="display" cellspacing="0" width="100%" >';
						result = result + '<thead>';
						result = result + '<tr>';
						result = result + '<th>Emp Id</th>';
						result = result + '<th>Emp Name</th>';
						result = result + '<th>Date</th>';
						result = result + '<th>Type of Expense</th>';
						result = result + '<th>Bank</th>';
						result = result + '<th>Month</th>';
						result = result + '<th>Legal Fees</th>';
						result = result + '<th>Advance</th>';
						result = result + '<th>General Expenses</th>';
						result = result + '<th>Vendor Payments</th>';
						result = result + '<th>Electronic Withdrawals</th>';
						result = result + '<th>Payroll Chks</th>';
						result = result + '<th>Amount</th>';
						result = result + '<th>Check No</th>';
						result = result + '</tr>';
						result = result + '</thead>	';
						result = result + '<tbody>';
			        	 for (var i=0; i<data.length; i++)
						{
			        		 var opt = data[i];
			        		    result = result + ' <tr>';
			        		    
			        		    if(opt.empId !=null && opt.empId !='' && opt.empId !='undefined'){
			        		    	result = result + '<td><div class="tblminwidth-75">'+opt.empId+'</div></td>';
			        		    }else{
			        		    	result = result + '<td><div class="tblminwidth-75">---</div></td>';
			        		    }
			        		    
			        		    if(opt.empName !=null && opt.empName !='' && opt.empName !='undefined'){
			        		    	result = result + '<td><div><a>'+opt.empName+'</a></div></td>';
			        		    }else{
			        		    	result = result + '<td><div>---</div></td>';
			        		    }
			        		    
			        		    if(opt.dateStr !=null && opt.dateStr !='' && opt.dateStr !='undefined'){
									result = result + ' <td><div class="tblminwidth-75">'+opt.dateStr+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">--</div></td>';
								}
			        		    
			        		    if(opt.typeOfExpense !=null && opt.typeOfExpense !='' && opt.typeOfExpense !='undefined'){
									result = result + ' <td><div class="tblminwidth-75">'+opt.typeOfExpense+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">--</div></td>';
								}
								
								if(opt.bankName !=null && opt.bankName !='' && opt.bankName !='undefined'){
									result = result + ' <td><div class="tblminwidth-75">'+opt.bankName+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">--</div></td>';
								}
								
								if(opt.month !=null && opt.month !='' && opt.month !='undefined'){
									result = result + ' <td><div class="tblminwidth-75">'+opt.month+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">--</div></td>';
								}
								
								if(opt.legalFees !=null && opt.legalFees !='' && opt.legalFees !='undefined'){
									result = result + '<td><div class="tblminwidth-75">'+opt.legalFees+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">--</div></td>';
								}
								
								if(opt.advanceStr !=null && opt.advanceStr !='' && opt.advanceStr !='undefined'){
									result = result + '<td><div class="tblminwidth-75">$ '+opt.advanceStr+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">$ 0.00</div></td>';
								}
								if(opt.generalExpensesStr !=null && opt.generalExpensesStr !='' && opt.generalExpensesStr !='undefined'){
									result = result + '<td><div class="tblminwidth-75">'+opt.generalExpensesStr+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">0.00</div></td>';
								}
								
								if(opt.vendorPaymentsStr !=null && opt.vendorPaymentsStr !='' && opt.vendorPaymentsStr !='undefined'){
									result = result + '<td><div class="tblminwidth-75">$ '+opt.vendorPaymentsStr+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">$ 0.00</div></td>';
								}
								
								if(opt.electronicWithdrawals !=null && opt.electronicWithdrawals !='' && opt.electronicWithdrawals !='undefined'){
									result = result + '<td><div class="tblminwidth-75">'+opt.electronicWithdrawals+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">--</div></td>';
								}
								
								if(opt.payrollChecksStr !=null && opt.payrollChecksStr !='' && opt.payrollChecksStr !='undefined'){
									result = result + '<td><div class="tblminwidth-75">$ '+opt.payrollChecksStr+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">$ 0.00</div></td>';
								}
								
								if(opt.amountStr !=null && opt.amountStr !='' && opt.amountStr !='undefined'){
									result = result + '<td><div class="tblminwidth-75">$ '+opt.amountStr+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">$ 0.00</div></td>';
								}
								
								if(opt.checkNo !=null && opt.checkNo !='' && opt.checkNo !='undefined'){
									result = result + '<td><div class="tblminwidth-75">'+opt.checkNo+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">--</div></td>';
								}
								
								result = result + ' </tr>';
						} 
						result = result + '  </tbody>';
						result = result + ' </table>';
						$("#employerCostDiv").empty();
						$("#wagesAndTaxListDiv").empty();
						$("#accountDeductionsListDiv").empty();
						$("#hrDeductionsListDiv").empty();
						$("#wagesAndTaxListDiv").empty();
						$("#finalPayrollReportDiv").empty();
						$("#checksListDiv").empty();
						$("#checksListDiv").append(result);
						$("#loading").removeAttr("style");
						$('#payScale').css('display','none');
						pavanPag(); 
				        	
				        },
				        
				    });
		 
}
</script>



<script>
function pavanPag(){ 
    $('#rajashekar').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    });
    $("#rajashekar").wrap("<div class='overflow_Auto'></div>")
}
</script>

