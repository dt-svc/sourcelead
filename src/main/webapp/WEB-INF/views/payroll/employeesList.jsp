
<script>
function getEmployeeList(){
	$("#addPayrollDiv").hide();
	var userId = '${SourceLeadWeb_user.id}';
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	
	$("#loading").attr('style','position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		  var sourceleadUrl ='${sourceleadUrl}';
				  $.ajax({
				    	type: "GET",
				        url: sourceleadUrl+"/getEmployeeList?${_csrf.parameterName}=${_csrf.token}?userId="+userId+"&orgLocationId="+orgLocationId,
				        success: function(data) {
						 var result = "";	
						result = result + '<table id="employeeListTable" class="display" cellspacing="0" width="100%" >';
						result = result + '<thead>';
						result = result + '<tr>';
						result = result + '<th>COMPANY</th>';
						result = result + '<th>BRANCH</th>';
						result = result + '<th>Emp Id</th>';
						result = result + '<th>Candidate Name</th>';
						result = result + '<th>RATE</th>';
						result = result + '<th>FIXED RATE</th>';
						result = result + '<th>PAY RATE</th>';
						result = result + '<th>FIXED PAY</th>';
						result = result + '<th>INSURANCE</th>';
						result = result + '<th>DED</th>';
						result = result + '<th>EFFECTIVE RATE</th>';
						result = result + '<th>Payroll Difference</th>';
						result = result + '<th>Immigration</th>';
						result = result + '<th>Comments</th>';
						result = result + '<th>Advance (HCP)</th>';
						result = result + '<th>Balance</th>';
						result = result + '</tr>';
						result = result + '</thead>	';
						result = result + '<tbody>';
			        	 for (var i=0; i<data.length; i++)
						{
			        		 var opt = data[i];
			        		    result = result + ' <tr>';
			        		    
			        		    if(opt.company !=null && opt.company !='' && opt.company !='undefined'){
			        		    	result = result + '<td><div class="tblminwidth-75">'+opt.company+'</div></td>';
			        		    }else{
			        		    	result = result + '<td><div class="tblminwidth-75">---</div></td>';
			        		    }
			        		    
			        		    if(opt.branch !=null && opt.branch !='' && opt.branch !='undefined'){
			        		    	result = result + '<td><div><a>'+opt.branch+'</a></div></td>';
			        		    }else{
			        		    	result = result + '<td><div>---</div></td>';
			        		    }
			        		    
			        		    if(opt.empId !=null && opt.empId !='' && opt.empId !='undefined'){
									result = result + ' <td><div class="tblminwidth-75">'+opt.empId+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">--</div></td>';
								}
			        		    
			        		    if(opt.candidateName !=null && opt.candidateName !='' && opt.candidateName !='undefined'){
									result = result + ' <td><div class="tbldata-dotdot-vsm" title="'+opt.candidateName+'">'+opt.candidateName+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">--</div></td>';
								}
								
								if(opt.rate !=null && opt.rate !='' && opt.rate !='undefined'){
									result = result + ' <td><div class="tblminwidth-75"><i class="fa fa-dollar"style="color:#454545;"></i> '+opt.rate.toFixed(2)+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">--</div></td>';
								}
// 								alert("hai"+opt.fixedRate);
								if(opt.fixedRate !=null && opt.fixedRate !='' && opt.fixedRate !='undefined'){
									result = result + ' <td><div class="tblminwidth-75"><i class="fa fa-dollar"style="color:#454545;"></i> '+opt.fixedRate.toFixed(2)+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">--</div></td>';
								}
								if(opt.payRate !=null && opt.payRate !='' && opt.payRate !='undefined'){
									result = result + '<td><div class="tblminwidth-75"><i class="fa fa-dollar"style="color:#454545;"></i> '+opt.payRate.toFixed(2)+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">--</div></td>';
								}
								
								if(opt.fixedPay !=null && opt.fixedPay !='' && opt.fixedPay !='undefined'){
									result = result + '<td><div class="tblminwidth-75"><i class="fa fa-dollar"style="color:#454545;"></i> '+opt.fixedPay.toFixed(2)+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">--</div></td>';
								}
								if(opt.insurance !=null && opt.insurance !='' && opt.insurance !='undefined'){
									result = result + '<td><div class="tblminwidth-75"><i class="fa fa-dollar"style="color:#454545;"></i> '+opt.insurance.toFixed(2)+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">--</div></td>';
								}
								
								if(opt.ded !=null && opt.ded !='' && opt.ded !='undefined'){
									result = result + '<td><div class="tblminwidth-75">$ '+opt.ded.toFixed(2)+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">--</div></td>';
								}
								
								if(opt.effectiveRate !=null && opt.effectiveRate !='' && opt.effectiveRate !='undefined'){
									result = result + '<td><div class="tblminwidth-75"><i class="fa fa-dollar"style="color:#454545;"></i> '+opt.effectiveRate.toFixed(2)+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">--</div></td>';
								}
								
								if(opt.payrollDifference !=null && opt.payrollDifference !='' && opt.payrollDifference !='undefined'){
									result = result + '<td><div class="tblminwidth-75"><i class="fa fa-dollar"style="color:#454545;"></i> '+opt.payrollDifference+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">--</div></td>';
								}
								var visa = parseFloat(opt.immigration);
								if(opt.immigration !=null && opt.immigration !='' && opt.immigration !='undefined'){
									result = result + '<td><div class="tblminwidth-75"><i class="fa fa-dollar"style="color:#454545;"></i> '+visa.toFixed(2)+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">--</div></td>';
								}
								
								if(opt.comments !=null && opt.comments !='' && opt.comments !='undefined'){
									result = result + '<td><div class="tbldata-dotdot-vsm" title="'+opt.comments+'">'+opt.comments+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">--</div></td>';
								}
								if(opt.advanceHcp !=null && opt.advanceHcp !='' && opt.advanceHcp !='undefined'){
									result = result + '<td><div class="tblminwidth-75">'+opt.advanceHcp+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">--</div></td>';
								}
								if(opt.balance !=null && opt.balance !='' && opt.balance !='undefined'){
									result = result + '<td><div class="tblminwidth-75">'+opt.balance+'</div></td>';
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
						$("#employeeListDiv").empty();
						$("#employeeListDiv").append(result);
						$("#loading").removeAttr("style");
						$('#payScale').css('display','none');
						employeeListPagination(); 
				        	
				        },
				        
				    });
		 
}
</script>



<script>
function employeeListPagination(){ 
    $('#employeeListTable').DataTable( { 
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
    $("#employeeListTable").wrap("<div class='overflow_Auto  width100percnt'></div>")
}
</script>

