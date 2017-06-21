
<script>
function wagesAndTaxList(){
		  var sourceleadUrl ='${sourceleadUrl}';
				  $.ajax({
				    	type: "GET",
				        url: sourceleadUrl+"/getWagesAndTaxList?${_csrf.parameterName}=${_csrf.token}",
				        success: function(data) {
						var result = "";				        	
						result = result + '<table id="WagesAndTaxListTable" class="display" cellspacing="0" width="100%" >';
						result = result + '<thead>';
						result = result + '<tr>';
						result = result + '<th>From Date</th>';
						result = result + '<th>To Date</th>';
						result = result + '<th>Employee Id</th>';
						result = result + '<th>Employee Name</th>';
						result = result + '<th>SSN</th>';
						result = result + '<th>Check Date</th>';
						result = result + '<th>Check No</th>';
						result = result + '<th>Gross</th>';
						result = result + '<th>FWT</th>';
						result = result + '<th>SSEC</th>';
						result = result + '<th>MED</th>';
						result = result + '<th>SWT Locals</th>';
						result = result + '<th>SDI/SUI</th>';
						result = result + '<th>local</th>';
						result = result + '<th>DEDS</th>';
						result = result + '<th>NET</th>';
						result = result + '<th>Batch Number</th>';
						result = result + '</tr>';
						result = result + '</thead>	';
						result = result + '<tbody>';
						var json = JSON.parse(data);
			        	 for (var i=0; i<json.length; i++)
						{
			        		 var opt = json[i];
			        		    result = result + ' <tr>';
			        		    if(opt.fromDateStr !=null && opt.fromDateStr !='' && opt.fromDateStr !='undefined'){
									result = result + ' <td><div>'+opt.fromDateStr+'</div></td>';
								}else{
									result = result + ' <td><div>--</div></td>';
								}
			        		    
			        		    if(opt.toDateStr !=null && opt.toDateStr !='' && opt.toDateStr !='undefined'){
									result = result + ' <td><div>'+opt.toDateStr+'</div></td>';
								}else{
									result = result + ' <td><div>--</div></td>';
								}
								result = result + '<td><div>'+opt.employeeId+'</div></td>';
								result = result + '<td><div>'+opt.employeeName+'</div></td>';
								if(opt.ssn !=null && opt.ssn !='' && opt.ssn !='undefined'){
									result = result + ' <td><div>'+opt.ssn+'</div></td>';
								}else{
									result = result + ' <td><div>--</div></td>';
								}
								if(opt.checkDateStr !=null && opt.checkDateStr !='' && opt.checkDateStr !='undefined'){
									result = result + '<td><div>'+opt.checkDateStr+'</div></td>';
								}else{
									result = result + ' <td><div>--</div></td>';
								}
								result = result + ' <td><div>'+opt.checkNo+'</div></td>';
								result = result + '<td><div>'+opt.gross+'</div></td>';
								result = result + '<td><div>'+opt.fwt+'</div></td>';
								result = result + ' <td><div>'+opt.ssec+'</div></td>';
								result = result + '<td><div>'+opt.med+'</div></td>';
								result = result + ' <td><div>'+opt.swt+'</div></td>';
								result = result + '<td><div>'+opt.sdiSui+'</div></td>';
								result = result + '<td><div>'+opt.local+'</div></td>';
								result = result + '<td><div>'+opt.deds+'</div></td>';
								result = result + '<td><div>'+opt.net+'</div></td>';
								if(opt.batchNumber !=null && opt.batchNumber !='' && opt.batchNumber !='undefined'){
									result = result + ' <td><div>'+opt.batchNumber+'</div></td>';
								}else{
									result = result + ' <td><div>--</div></td>';
								}
								result = result + ' </tr>';
						}
						result = result + '  </tbody>';
						result = result + ' </table>';
						$("#employerCostDiv").empty();
						$("#wagesAndTaxListDiv").empty();
						$("#wagesAndTaxListDiv").append(result);
						wagesAndTaxListTablePagination();
				        	
				        },
				        
				    });
		 
}
</script>

<script>
function wagesAndTaxListTablePagination(){ 
    $('#WagesAndTaxListTable').DataTable( { 
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
    $("#WagesAndTaxListTable").wrap("<div class='overflow_Auto'></div>")
}
</script>
