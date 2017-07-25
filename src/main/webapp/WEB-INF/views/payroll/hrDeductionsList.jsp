
<script>
function hrDeductionsList(){
	$("#loading").attr('style','position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		  var sourceleadUrl ='${sourceleadUrl}';
				  $.ajax({
				    	type: "GET",
				        url: sourceleadUrl+"/getHrDeductionsList?${_csrf.parameterName}=${_csrf.token}",
				        success: function(data) {
						var result = "";				        	
						result = result + '<table id="hrDeductionsListTable" class="display" cellspacing="0" width="100%" >';
						result = result + '<thead>';
						result = result + '<tr>';
						result = result + '<th>Employee Id</th>';
						result = result + '<th>Employee Name</th>';
						result = result + '<th>From Date</th>';
						result = result + '<th>To Date</th>';
						result = result + '<th>Ded. Amount</th>';
						result = result + '<th>Batch Number</th>';
						result = result + '<th>Request Sub.Date</th>';
						result = result + '<th>Request Approved Date</th>';
						result = result + '<th>Repaid Status</th>';
						result = result + '</tr>';
						result = result + '</thead>	';
						result = result + '<tbody>';
						var json = JSON.parse(data);
			        	 for (var i=0; i<json.length; i++)
						{
			        		 var opt = json[i];
			        		    result = result + ' <tr>';
			        		    
			        		    if(opt.employeeId !=null && opt.employeeId !='' && opt.employeeId !='undefined'){
			        		    	result = result + '<td><div class="tblminwidth-75">'+opt.employeeId+'</div></td>';
			        		    }else{
			        		    	result = result + '<td><div class="tblminwidth-75">---</div></td>';
			        		    }
			        		    
			        		    if(opt.employeeName !=null && opt.employeeName !='' && opt.employeeName !='undefined'){
			        		    	result = result + '<td><div onclick="getHrDeductionDetails('+opt.id+',\''+opt.employeeName+'\');"><a>'+opt.employeeName+'</a></div></td>';
			        		    }else{
			        		    	result = result + '<td><div>---</div></td>';
			        		    }
			        		    
			        		    if(opt.fromDateStr !=null && opt.fromDateStr !='' && opt.fromDateStr !='undefined'){
									result = result + ' <td><div class="tblminwidth-75">'+opt.fromDateStr+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">--</div></td>';
								}
			        		    
			        		    if(opt.toDateStr !=null && opt.toDateStr !='' && opt.toDateStr !='undefined'){
									result = result + ' <td><div class="tblminwidth-75">'+opt.toDateStr+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">--</div></td>';
								}
								
								if(opt.dedAmount !=null && opt.dedAmount !='' && opt.dedAmount !='undefined'){
									result = result + ' <td><div class="tblminwidth-75">'+opt.dedAmount+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">--</div></td>';
								}
								
								if(opt.batchNumber !=null && opt.batchNumber !='' && opt.batchNumber !='undefined'){
									result = result + ' <td><div class="tblminwidth-75">'+opt.batchNumber+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">--</div></td>';
								}
								
								if(opt.requestSubDate !=null && opt.requestSubDate !='' && opt.requestSubDate !='undefined'){
									result = result + '<td><div class="tblminwidth-75">'+opt.requestSubDate+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">--</div></td>';
								}
								
								if(opt.requestApprovedDate !=null && opt.requestApprovedDate !='' && opt.requestApprovedDate !='undefined'){
									result = result + '<td><div class="tblminwidth-75">'+opt.requestApprovedDate+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">--</div></td>';
								}
								
								if(opt.repaidStatus !=null && opt.repaidStatus !='' && opt.repaidStatus !='undefined'){
									result = result + '<td><div class="tblminwidth-75">'+opt.repaidStatus+'</div></td>';
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
						$("#hrDeductionsListDiv").append(result);
						$("#loading").removeAttr("style");
						$('#payScale').css('display','none');
						hrDeductionsListTablePagination();
				        	
				        },
				        
				    });
		 
}
</script>

<script>
function hrDeductionsListTablePagination(){ 
    $('#hrDeductionsListTable').DataTable( { 
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
    $("#hrDeductionsListTable").wrap("<div class='overflow_Auto'></div>");
}
</script>


<div class="modal modal_Expenses hrDedu">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" onClick="closeModalExpenses();">&times;</button>
        <h4 class="modal-title" id="hrEmpName"></h4>
      </div>
      <div class="modal-body project_tabs_inn_table">
        <div id="hrDeductiondetails"></div>
      </div>
    </div>

</div>
</div>
<script>
function getHrDeductionDetails(hrDedId,empName){
	$(".sideMask").fadeIn("slow");
	$(".hrDedu").fadeIn("slow");
	  var sourceleadUrl ='${sourceleadUrl}';
			  $.ajax({
			    	type: "GET",
			        url: sourceleadUrl+"/getEmpHrDeductionsDetailsbyId?${_csrf.parameterName}=${_csrf.token}&hrDeductionId="+hrDedId,
			        success: function(data) {
					var result = "";				        	
					result = result + '<table id="employeeHrDeductionsListTable" class="display" cellspacing="0" width="100%" >';
					result = result + '<thead>';
					result = result + '<tr>';
					result = result + '<th>Pay Cycle Name</th>';
					result = result + '<th>Ded. Amount per Cycle</th>';
					result = result + '<th>Paid Status</th>';
					result = result + '</tr>';
					result = result + '</thead>	';
					result = result + '<tbody>';
					var json = JSON.parse(data);
		        	 for (var i=0; i<json.length; i++)
					{
		        		 var opt = json[i];
		        		    result = result + ' <tr>';
		        		    
		        		    if(opt.payCycleName !=null && opt.payCycleName !='' && opt.payCycleName !='undefined'){
		        		    	result = result + '<td><div>'+opt.payCycleName+'</div></td>';
		        		    }else{
		        		    	result = result + '<td><div>---</div></td>';
		        		    }
		        		    
		        		    if(opt.dedAmountPerCycle !=null && opt.dedAmountPerCycle !='' && opt.dedAmountPerCycle !='undefined'){
		        		    	result = result + '<td><div onclick="getEmpAccountDeductionDetails('+opt.id+');">'+opt.dedAmountPerCycle+'</div></td>';
		        		    }else{
		        		    	result = result + '<td><div>---</div></td>';
		        		    }
		        		    
		        		    if(opt.paidStatus !=null && opt.paidStatus !='' && opt.paidStatus !='undefined'){
								result = result + ' <td><div>'+opt.paidStatus+'</div></td>';
							}else{
								result = result + ' <td><div>--</div></td>';
							}
		        		    
							result = result + ' </tr>';
					} 
					result = result + '  </tbody>';
					result = result + ' </table>';
					$("#hrDeductiondetails").empty();
					$("#hrEmpName").html(empName);
					$("#hrDeductiondetails").append(result);
					employeeHrDeductionsListTablePagination();
			        	
			        },
			        
			    });
			  
}
function closeModalExpenses(){
	$(".sideMask").fadeOut("slow");
	$(".modal_Expenses").fadeOut("slow");	
}
</script>

<script>
function employeeHrDeductionsListTablePagination(){ 
    $('#employeeHrDeductionsListTable').DataTable( { 
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
    $("#employeeHrDeductionsListTable").wrap("<div class='overflow_Auto'></div>");
}
</script>

