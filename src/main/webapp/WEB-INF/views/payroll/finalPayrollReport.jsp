<script>
function generatrPayroll(data,payrollCycle){
		    	var result = "";				        	
				result = result + '<table id="finalPayrollreportTable" class="display" cellspacing="0" width="100%" >';
				result = result + '<thead>';
				result = result + '<tr>';
				result = result + '<th>Employee Id</th>';
				result = result + '<th>Employee Name</th>';
				result = result + '<th>Total '+payrollCycle+' Hours</th>';
				/* result = result + '<th>1/2 Hours</th>'; */
				result = result + '<th>Pay Rate</th>';
				result = result + '<th>Amount</th>';
				result = result + '<th>Pre Deductions</th>';
				result = result + '<th>Final</th>';
				result = result + '<th>Post Deductions</th>';
				result = result + '<th>Reimbursement</th>';
				result = result + '<th>Status</th>';
				result = result + '<th>Comments</th>';
				result = result + '</tr>';
				result = result + '</thead>	';
				result = result + '<tbody>';
				var json = JSON.parse(data);
	        	 for (var i=0; i<json.length; i++)
				{
	        		 var opt = json[i];
	        		    result = result + ' <tr>';
	        		    if(opt.empId !=null && opt.empId !='' && opt.empId !='undefined'){
							result = result + ' <td><div class="tblminwidth-75">'+opt.empId+'</div></td>';
						}else{
							result = result + ' <td><div>--</div></td>';
						}
	        		    
	        		    if(opt.employeeName !=null && opt.employeeName !='' && opt.employeeName !='undefined'){
							result = result + ' <td><div class="tblminwidth-75" title="'+opt.employeeName+'">'+opt.employeeName+'</div></td>';
						}else{
							result = result + ' <td><div>--</div></td>';
						}
						result = result + '<td><div class="tblminwidth-75">'+opt.totalHours+'</div></td>';
						/* result = result + '<td><div class="tbldata-dotdot-vsm">'+opt.halfHours+'</div></td>'; */
						if(opt.payRate !=null && opt.payRate !='' && opt.payRate !='undefined'){
							result = result + ' <td><i class="fa fa-dollar paddingLeft text-left pull-left"></i><div class="tbldata-dotdot-sm"">'+opt.payRate+'</div></td>';
						}else{
							result = result + ' <td><i class="fa fa-dollar paddingLeft text-left pull-left"></i><div>0</div></td>';
						}
						if(opt.amount !=null && opt.amount !='' && opt.amount !='undefined'){
							result = result + '<td><i class="fa fa-dollar paddingLeft text-left pull-left"></i><div class="tblminwidth-125 text-right">'+opt.amount+'</div></td>';
						}else{
							result = result + ' <td><div>--</div></td>';
						}
						if(opt.preDeductins !=null && opt.preDeductins !='' && opt.preDeductins !='undefined'){
							result = result + ' <td><div class="tblminwidth-75">'+opt.preDeductins+'</div></td>';
						}else{
							result = result + ' <td><div class="tblminwidth-75">0</div></td>';
						}
						if(opt.finalAmount !=null && opt.finalAmount !='' && opt.finalAmount != 'undefined'){
							result = result + '<td><div class="tblminwidth-75">'+opt.finalAmount+'</div></td>';
						}else{
							result = result + '<td><div class="tblminwidth-75">0</div></td>';
						}
						if(opt.postDeductions !=null && opt.postDeductions !='' && opt.postDeductions !='undefined'){
							result = result + '<td><div class="tblminwidth-75">'+opt.postDeductions+'</div></td>';
						}else{
							result = result + '<td><div class="tblminwidth-75">0</div></td>';
						}
						if(opt.reimbursement !=null && opt.reimbursement !='' && opt.reimbursement !='undefined'){
							result = result + ' <td><div class="tblminwidth-75">'+opt.reimbursement+'</div></td>';	
						}else{
							result = result + ' <td><div class="tblminwidth-75">0</div></td>';
						}
						
						if(opt.status !=null && opt.status !='undefined' && opt.status !=''){
							result = result + '<td><div class="tblminwidth-75">'+opt.status+'</div></td>';
						}else{
							result = result + '<td><div class="tblminwidth-75">--</div></td>';
						}
						if(opt.comments !=null && opt.comments !='undefined' && opt.comments !=''){
							result = result + '<td><div class="tblminwidth-75">'+opt.comments+'</div></td>';
						}else{
							result = result + '<td><div class="tblminwidth-75">--</div></td>';
						}
						/* result = result + ' <td><div class="tblminwidth-75">'+opt.comments+'</div></td>'; */
						result = result + ' </tr>';
				}
				result = result + '  </tbody>';
				result = result + ' </table>';
				$("#employerCostDiv").empty();
				$("#wagesAndTaxListDiv").empty();
				$("#accountDeductionsListDiv").empty();
				$("#hrDeductionsListDiv").empty();
				$("#wagesAndTaxListDiv").empty();
				$("#checksListDiv").empty();
				$("#employeeListDiv").empty();
				$("#invoiceDataDiv").empty();
				$("#finalPayrollReportDiv").empty();
				$("#finalPayrollReportDiv").append(result);
				$('#payScale').css('display','block');
				$('#payCycleName').html(payrollCycle);
				finalPayrollreportPagination();
}
</script>


<script>
function finalPayrollreportPagination(){ 
    $('#finalPayrollreportTable').DataTable( { 
    	dom: 'Bfrtip',
        buttons: [
        	{
                extend: 'excel',
                title:  'Payroll',
            }
        ],
    	
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    });
    $("#finalPayrollreportTable").wrap("<div class='overflow_Auto width100percnt'></div>")
}
</script>
 