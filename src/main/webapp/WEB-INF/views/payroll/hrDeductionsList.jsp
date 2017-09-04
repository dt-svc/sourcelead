<script type="text/javascript">
function hrDeductionsList(){
		var sourceleadUrl ='${sourceleadUrl}';
		var searchValue = $("#hrDataTableSearch").val();
		searchValue = searchValue ? searchValue : "";
		var url = sourceleadUrl+"/getHrDeductionsList?searchValue="+searchValue;
		/* url += "&"+$($('#payroll_list_form')[0]).serialize(); */
	    prepareHrDeductionsList(url);
}
</script> 

<script>
function prepareHrDeductionsList(url){
	$("#employerCostDiv").empty();
	$("#accountDeductionsListDiv").empty();
	$("#wagesAndTaxListDiv").empty();
	$("#checksListDiv").empty();
	$("#employeeListDiv").empty();
	$("#invoiceDataDiv").empty();
	$("#finalPayrollReportDiv").empty();
	$("#hrDeductionsListDiv").append(result);
	$("#loading").removeAttr("style");
	$('#payScale').css('display','none');
	
	 $("#hrDeductionsListDiv").removeAttr("style");
 	 $("#hrDeductionsListDiv").empty();
 	 var result = "";
 	result = result +" <div class='modal-header'>";
 	result = result +" <div class='remove_search'>";
 	result = result +" <input type='text' placeholder='Search' id='hrDataTableSearch'/>";
 	result = result +"</div>";
 	result = result +"</div>";
 	  result = result +"<table id='hrDeductionsPagination' class='display' cellspacing='0' width='100%' >"; 
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
        result = result +"</table>";
      $("#hrDeductionsListDiv").append(result);
      hrDeductionsPagination(url); 
}
</script>


<script>
function hrDeductionsPagination(url){
	var colIndex = 0;
	/*$.fn.dataTable.moment( 'DD-MM-YYYY' );*/
    $('#hrDeductionsPagination').DataTable( { 
    		"processing": true,
        "serverSide": true,
        "searching": false,
        "ajax": $.fn.dataTable.pipeline( {
            url: url,
            method: 'POST',
            pages: 10
        }),
        "columns": [
            { "data": "employeeId" },
            { "data": "employeeName"},
            { "data": "fromDate"},
            { "data": "toDate" },
            { "data": "dedAmount"},
            { "data": "batchNumber" },
            { "data": "requestSubDate" },
            { "data": "requestApprovedDate"},
            { "data": "repaidStatus"}
            
        ],
        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"className": "tbldata-dotdot_50",
	    	  	"data": "employeeId",
	    	  	"render": function(data, type, opt, meta) {
	    	  		  var result = "";
			    	  if(opt.employeeId !=null && opt.employeeId !='' && opt.employeeId !='undefined'){
		        		    result = result + '<td><div class="tblminwidth-75">'+opt.employeeId+'</div></td>';
		        	  }else{
		        		    result = result + '<td><div class="tblminwidth-75">---</div></td>';
		        	  }
		             return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "employeeName",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  if(opt.employeeName !=null && opt.employeeName !='' && opt.employeeName !='undefined'){
      		    	    result = result + '<td><div onclick="getHrDeductionDetails('+opt.id+',\''+opt.employeeName+'\');"><a>'+opt.employeeName+'</a></div></td>';
      		      }else{
      		    	    result = result + '<td><div>---</div></td>';
      		      }
	              return result;
              		
              }
	          },{
            "targets": colIndex++,
            "data": "fromDate",
            "render": function ( data, type, opt, meta ) {
            	var result = "";
            	if(opt.fromDateStr !=null && opt.fromDateStr !='' && opt.fromDateStr !='undefined'){
					   result = result + ' <td><div class="tblminwidth-75">'+opt.fromDateStr+'</div></td>';
				}else{
					   result = result + ' <td><div class="tblminwidth-75">--</div></td>';
				}
            	return result;
            }
          }, {
              "targets": colIndex++,
              "data": "toDate",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  if(opt.toDateStr !=null && opt.toDateStr !='' && opt.toDateStr !='undefined'){
						  result = result + ' <td><div class="tblminwidth-75">'+opt.toDateStr+'</div></td>';
					}else{
						  result = result + ' <td><div class="tblminwidth-75">--</div></td>';
					}
            	    return result;
              }
            }, {
                "targets": colIndex++,
                "data": "dedAmount",
                "render": function ( data, type, opt, meta ) {
                		var result = "";
                		 if(opt.dedAmount !=null && opt.dedAmount !='' && opt.dedAmount !='undefined'){
//			        		    result = result + '<td><div class="deeds tblminwidth-125" id="preDedDiv'+opt.id+'">'+opt.dedAmount+'<small class="fa fa-pencil ml10 pull-right" onClick="showEditt('+opt.id+','+opt.dedAmount+')"></small></div>';
								result = result + '<td><div class="deeds tblminwidth-125" id="preDedDiv'+opt.id+'"><i class="fa fa-dollar"style="color:#454545;"></i> '+opt.dedAmount.toFixed(2)+'<small class="fa fa-pencil ml10 pull-right" onClick="showEdit('+opt.id+','+opt.dedAmount+')"></small></div>';
			        		    result = result + '<div class="tblminwidth-125"><p id="preDedError'+opt.id+'" class="re_Error"></p></div>';
								result = result + '<div class="edit-input tblminwidth-125" id="editDedDiv'+opt.id+'" style="display:none;"><input type="text" id ="preDed'+opt.id+'" class="form-control input-sm tbldata-dotdot-100 pull-left"><small class="fa fa-floppy-o ml10 pull-right mt5"  onClick="saveEdit('+opt.id+')"></small></div></td>';
		        		    }
		        		    else{
		        		    	result = result + '<td><div class="deeds tblminwidth-125" id="preDedDiv'+opt.id+'"><small class="fa fa-dollar expenses_symbol text-muted"></small>0<small class="fa fa-pencil ml10 pull-right" onClick="showEdit('+opt.id+',0)"></small></div>';
			        		    result = result + '<div class="tblminwidth-125"><p id="preDedError'+opt.id+'" class="re_Error"></p></div>';
								result = result + '<div class="edit-input tblminwidth-125" id="editDedDiv'+opt.id+'" style="display:none;"><input type="text" id ="preDed'+opt.id+'" class="form-control input-sm tbldata-dotdot-100 pull-left"><small class="fa fa-floppy-o ml10 pull-right mt5"  onClick="saveEdit('+opt.id+')"></small></div></td>';
							}
    	                return result;
                }
          },{
	    	  	"targets": colIndex++,
	    	  	"data": "batchNumber",
	    	  	"render": function(data, type, opt, meta) {
	    	  		var result = "";
	    	  		if(opt.batchNumber !=null && opt.batchNumber !='' && opt.batchNumber !='undefined'){
						  result = result + ' <td><div class="tblminwidth-75">'+opt.batchNumber+'</div></td>';
					}else{
						  result = result + ' <td><div class="tblminwidth-75">--</div></td>';
					}
  	                return result;
	    	  	}
	    	  },{
	    	  	"targets": colIndex++,
	    	  	"data": "requestSubDate",
	    	  	"render": function(data, type, opt, meta) {
	    	  		var result = "";
	    	  		if(opt.requestSubDate !=null && opt.requestSubDate !='' && opt.requestSubDate !='undefined'){
						   result = result + '<td><div class="tblminwidth-75">'+opt.requestSubDate+'</div></td>';
					}else{
						   result = result + ' <td><div class="tblminwidth-75">--</div></td>';
					}
	    	  		return result;
	    	  	}
	    	  }, {
                  "targets": colIndex++,
                  "data": "requestApprovedDate",
                  "render": function ( data, type, opt, meta ) {
                	  var result = "";
                	  if(opt.requestApprovedDate !=null && opt.requestApprovedDate !='' && opt.requestApprovedDate !='undefined'){
							  result = result + '<td><div class="tblminwidth-75">'+opt.requestApprovedDate+'</div></td>';
						}else{
							  result = result + ' <td><div class="tblminwidth-75">--</div></td>';
						}
                	    return result;
                  }
                },{
                    "targets": colIndex++,
                    "data": "repaidStatus",
                    "render": function ( data, type, opt, meta ) {
                    	var result = "";
                    	if(opt.repaidStatus !=null && opt.repaidStatus !='' && opt.repaidStatus !='undefined'){
							   result = result + '<td><div class="tblminwidth-75">'+opt.repaidStatus+'</div></td>';
						}else{
							   result = result + ' <td><div class="tblminwidth-75">--</div></td>';
						}
                    	return result;
                    }
                  } ], 
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"aaSorting": [[0,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
    } );
    $("#hrDeductionsPagination").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $("#hrDataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   hrDeductionsList();
    	   }
    	});
}
</script>


<div class="modal modal_Expenses hrDedu">
  <div class="modal-dialog">

    Modal content
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

function showEditt(id,dedVal){
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
		        url: sourceleadUrl+"/updateHrDeduction?${_csrf.parameterName}=${_csrf.token}&hrDeductionId="+id+"&dedAmount="+output,
		        success: function(data) {
		        	document.getElementById("preDedDiv"+id).style.display="block";
		   		 	document.getElementById("editDedDiv"+id).style.display="none";
		   		    hrDeductionsList();
		         //$("#preDedDiv"+id).empty();
		   		// $("#preDedDiv"+id).append(output+'<small class="fa fa-pencil ml10 pull-right" onClick="showEditt('+id+','+output+')"></small>');
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


<!-- <script>
function hrDeductionsList(){
	$("#addPayrollDiv").hide();
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
									result = result + ' <td><div class="tblminwidth-75"><i class="fa fa-dollar"style="color:#454545;"></i> '+opt.dedAmount.toFixed(2)+'</div></td>';
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
						$("#employeeListDiv").empty();
						$("#checksListDiv").empty();
						$("#invoiceDataDiv").empty();
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
    $("#hrDeductionsListTable").wrap("<div class='overflow_Auto width100percnt'></div>");
}
</script>


<div class="modal modal_Expenses hrDedu">
  <div class="modal-dialog">

    Modal content
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
</script> -->

