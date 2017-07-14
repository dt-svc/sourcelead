
<script>
function accountDeductionsList(){
	$("#loading").attr('style','position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		  var sourceleadUrl ='${sourceleadUrl}';
				  $.ajax({
				    	type: "GET",
				        url: sourceleadUrl+"/getAccountDeductionsList?${_csrf.parameterName}=${_csrf.token}",
				        success: function(data) {
						var result = "";				        	
						result = result + '<table id="AccountDeductionsListTable" class="display" cellspacing="0" width="100%" >';
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
			        		    	result = result + '<td><div class="tblminwidth-75" onclick="getEmpAccountDeductionDetails('+opt.id+',\''+opt.employeeName+'\');"><a>'+opt.employeeName+'</a></div></td>';
			        		    }else{
			        		    	result = result + '<td><div class="tblminwidth-75">---</div></td>';
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
				        		    //result = result + '<td><div class="deeds tblminwidth-125" id="preDedDiv'+opt.id+'">'+opt.dedAmount+'<small class="fa fa-pencil ml10 pull-right" onClick="showEdit('+opt.id+','+opt.dedAmount+')"></small></div>';
									result = result + '<td><div class="deeds tblminwidth-125" id="preDedDiv'+opt.id+'"><small class="fa fa-dollar expenses_symbol text-muted"></small>'+opt.dedAmount+'<small class="fa fa-pencil ml10 pull-right" onClick="showEdit('+opt.id+','+opt.dedAmount+')"></small></div>';
				        		    result = result + '<div class="tblminwidth-125"><p id="preDedError'+opt.id+'" class="re_Error"></p></div>';
									result = result + '<div class="edit-input tblminwidth-125" id="editDedDiv'+opt.id+'" style="display:none;"><input type="text" id ="preDed'+opt.id+'" class="form-control input-sm tbldata-dotdot-100 pull-left"><small class="fa fa-floppy-o ml10 pull-right mt5"  onClick="saveEdit('+opt.id+')"></small></div></td>';
			        		    }
			        		    else{
			        		    	result = result + '<td><div class="deeds tblminwidth-125" id="preDedDiv'+opt.id+'"><small class="fa fa-dollar expenses_symbol text-muted"></small>0<small class="fa fa-pencil ml10 pull-right" onClick="showEdit('+opt.id+',0)"></small></div>';
				        		    result = result + '<div class="tblminwidth-125"><p id="preDedError'+opt.id+'" class="re_Error"></p></div>';
									result = result + '<div class="edit-input tblminwidth-125" id="editDedDiv'+opt.id+'" style="display:none;"><input type="text" id ="preDed'+opt.id+'" class="form-control input-sm tbldata-dotdot-100 pull-left"><small class="fa fa-floppy-o ml10 pull-right mt5"  onClick="saveEdit('+opt.id+')"></small></div></td>';
								}
								/* if(opt.dedAmount !=null && opt.dedAmount !='' && opt.dedAmount !='undefined'){
									result = result + ' <td><div class="tblminwidth-75">'+opt.dedAmount+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">--</div></td>';
								} */
								
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
						$("#accountDeductionsListDiv").append(result);
						$("#loading").removeAttr("style");
						$('#payScale').css('display','none');
						accountDeductionsListTablePagination();
				        	
				        },
				        
				    });
		 
}
</script>

<script>
function accountDeductionsListTablePagination(){ 
    $('#AccountDeductionsListTable').DataTable( { 
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
    $("#AccountDeductionsListTable").wrap("<div class='overflow_Auto'></div>");
}
</script>



<div class="modal modal_Expenses AccountDedu">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" onClick="closeModalExpenses();">&times;</button>
        <h4 class="modal-title" id="empName"></h4>
      </div>
      <div class="modal-body project_tabs_inn_table">
        <div id="accountdeDuctiondetails"></div>
      </div>
    </div>

</div>
</div>
<script>
function getEmpAccountDeductionDetails(accountDedId,empName){
	$(".sideMask").fadeIn("slow");
	$(".AccountDedu").fadeIn("slow");
	  var sourceleadUrl ='${sourceleadUrl}';
			  $.ajax({
			    	type: "GET",
			        url: sourceleadUrl+"/getDeductionsDetailsbyId?${_csrf.parameterName}=${_csrf.token}&deductionId="+accountDedId,
			        success: function(data) {
					var result = "";				        	
					result = result + '<table id="employeeDeductionsListTable" class="display" cellspacing="0" width="100%" >';
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
					$("#accountdeDuctiondetails").empty();
					$("#empName").html(empName);
					$("#accountdeDuctiondetails").append(result);
					employeeDeductionsListTablePagination();
			        	
			        },
			        
			    });
			  
}
function closeModalExpenses(){
	$(".sideMask").fadeOut("slow");
	$(".modal_Expenses").fadeOut("slow");	
}
function showEdit(id,dedVal){
	 document.getElementById("preDedDiv"+id).style.display="none";
	 document.getElementById("editDedDiv"+id).style.display="block";
	 document.getElementById("preDed"+id).value = dedVal;

	

}
function saveEdit(id){
	$("#preDedError"+id).empty();
	var output = document.getElementById("preDed"+id).value;
	var flag ="valid";
	if(output==""){
		flag="empty";
	}
	else if(/^[a-zA-Z0-9\.\_]*$/.test(output) == false || output.match(/[a-z]/i)) {
		flag = "invalid";
	}
	if(flag == "valid"){
		$("#preDedError").empty();
		 var sourceleadUrl ='${sourceleadUrl}';
		  $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/updateAccountDeduction?${_csrf.parameterName}=${_csrf.token}&accDeductionId="+id+"&dedAmount="+output,
		        success: function(data) {
		        	document.getElementById("preDedDiv"+id).style.display="block";
		   		 	document.getElementById("editDedDiv"+id).style.display="none";
		        	accountDeductionsList();
		         //$("#preDedDiv"+id).empty();
		   		// $("#preDedDiv"+id).append(output+'<small class="fa fa-pencil ml10 pull-right" onClick="showEdit('+id+','+output+')"></small>');
		        },
		    });
	}
	else{
		if(flag == "empty"){
			$("#preDedError"+id).empty().append("please Enter value") ; 
		}
		else{
			$("#preDedError"+id).empty().append("Invalid Number.") ; 
		}
	}
}
</script>

<script>
function employeeDeductionsListTablePagination(){ 
    $('#employeeDeductionsListTable').DataTable( { 
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
    $("#employeeDeductionsListTable").wrap("<div class='overflow_Auto'></div>");
}
</script>

