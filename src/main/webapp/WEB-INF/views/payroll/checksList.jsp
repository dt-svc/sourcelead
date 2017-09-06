<script>
function bisChecksList(){
	var sourceleadUrl ='${sourceleadUrl}';
	var searchValue = $("#checksDataTableSearch").val();
	searchValue = searchValue ? searchValue : "";
	var url = sourceleadUrl+"/getChecksList?searchValue="+searchValue;
    prepareBisChecksList(url);
}

</script>

<script>
function prepareBisChecksList(url){
	$("#employerCostDiv").empty();
	$("#accountDeductionsListDiv").empty();
	$("#hrDeductionsListDiv").empty();
	$("#wagesAndTaxListDiv").empty();
	$("#employeeListDiv").empty();
	$("#invoiceDataDiv").empty();
	$("#finalPayrollReportDiv").empty();
	$("#loading").removeAttr("style");
	$('#payScale').css('display','none');
	
	 $("#checksListDiv").removeAttr("style");
 	 $("#checksListDiv").empty();
 	 var result = "";
 	result = result +" <div class='clearfix col-md-12 mb10'>";
 	result = result +'<a href="/sourcelead/addChecks" class="btn btn-primary btn-sm pull-left" id="generateReportId" title="Generate Report">Add Checks</a>';
 	result = result +" <div class='remove_search'>";
 	result = result +" <input type='text' placeholder='Search' id='checksDataTableSearch'/>";
 	result = result +"</div>";
 	result = result +"</div>";
 	result = result + '<table id="checksListTable" class="display" cellspacing="0" width="100%" >';
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
    result = result +"</table>";
    $("#checksListDiv").append(result);
    checksPagination(url); 
	
	
}

</script>

<script>
function checksPagination(url){
	var colIndex = 0;
    $('#checksListTable').DataTable( { 
	  "processing": true,
      "serverSide": true,
      "searching": false,
      "ajax": $.fn.dataTable.pipeline( {
          url: url,
          method: 'POST',
          pages: 10
        }),
        "columns": [
            { "data": "empId" },
            { "data": "empName"},
            { "data": "date"},
            { "data": "typeOfExpense" },
            { "data": "bankName" },
            { "data": "month"},
            { "data": "legalFees"},
            { "data": "advance"},
            { "data": "generalExpenses"},
            { "data": "vendorPayments"},
            { "data": "electronicWithdrawals"},
            { "data": "payrollChecks"},
            { "data": "amount"},
            { "data": "checkNo"}
        ],
        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"data": "empId",
	    	  	"render": function(data, type, opt, meta) {
	    	  		  var result = "";
	    	  		  if(opt.empId !=null && opt.empId !='' && opt.empId !='undefined'){
			        		    	result = result + '<td><div class="tblminwidth-75">'+opt.empId+'</div></td>';
			        		    }else{
			        		    	result = result + '<td><div class="tblminwidth-75">---</div></td>';
			        		    }
		             return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "empName",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	   if(opt.empName !=null && opt.empName !='' && opt.empName !='undefined'){
			        		    	result = result + '<td><div>'+opt.empName+'</div></td>';
			        		    }else{
			        		    	result = result + '<td><div>---</div></td>';
			        		    }
	              return result;
              		
              }
	          },{
            "targets": colIndex++,
            "data": "date",
            "render": function ( data, type, opt, meta ) {
            	var result = "";
            	if(opt.dateStr !=null && opt.dateStr !='' && opt.dateStr !='undefined'){
									result = result + ' <td><div class="tblminwidth-75">'+opt.dateStr+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">--</div></td>';
								}
            	 return result;
            }
          }, {
              "targets": colIndex++,
              "data": "typeOfExpense",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  if(opt.typeOfExpense !=null && opt.typeOfExpense !='' && opt.typeOfExpense !='undefined'){
									result = result + ' <td><div class="tblminwidth-75">'+opt.typeOfExpense+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">--</div></td>';
								}
            	  return result;
              }
            }, {
                "targets": colIndex++,
                "data": "bankName",
                "render": function ( data, type, opt, meta ) {
                	var result = "";
                		if(opt.bankName !=null && opt.bankName !='' && opt.bankName !='undefined'){
									result = result + ' <td><div class="tblminwidth-75">'+opt.bankName+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">--</div></td>';
								}
    	              return result;
                }
          },{
	    	  	"targets": colIndex++,
	    	  	"data": "month",
	    	  	"render": function(data, type, opt, meta) {
	    	  		var result = "";
	    	  		if(opt.month !=null && opt.month !='' && opt.month !='undefined'){
									result = result + ' <td><div class="tblminwidth-75">'+opt.month+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">--</div></td>';
								}
  	              return result;
	    	  	}
	    	  },{
	    	  	"targets": colIndex++,
	    	  	"data": "legalFees",
	    	  	"render": function(data, type, opt, meta) {
	    	  		var result = "";
	    	  		if(opt.legalFees !=null && opt.legalFees !='' && opt.legalFees !='undefined'){
									result = result + '<td><div class="tblminwidth-75">'+opt.legalFees+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">--</div></td>';
								}
	    	  		 return result;
	    	  	}
	    	  }, {
                  "targets": colIndex++,
                  "data": "advance",
                  "render": function ( data, type, opt, meta ) {
                	  var result = "";
                	  if(opt.advanceStr !=null && opt.advanceStr !='' && opt.advanceStr !='undefined'){
									result = result + '<td><div class="tblminwidth-75">$ '+opt.advanceStr+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">$ 0.00</div></td>';
								}
                	  return result;
                  }
                },{
                    "targets": colIndex++,
                    "data": "generalExpenses",
                    "render": function ( data, type, opt, meta ) {
                    	var result = "";
                    	if(opt.generalExpensesStr !=null && opt.generalExpensesStr !='' && opt.generalExpensesStr !='undefined'){
									result = result + '<td><div class="tblminwidth-75">'+opt.generalExpensesStr+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">0.00</div></td>';
								}
                    	 return result;
                    }
                  },{
                      "targets": colIndex++,
                      "data": "vendorPayments",
                      "render": function ( data, type, opt, meta ) {
                    	  var result = "";
                    	  if(opt.vendorPaymentsStr !=null && opt.vendorPaymentsStr !='' && opt.vendorPaymentsStr !='undefined'){
									result = result + '<td><div class="tblminwidth-75">$ '+opt.vendorPaymentsStr+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">$ 0.00</div></td>';
								}
                    	  return result;
                      }
                    },{
                        "targets": colIndex++,
                        "data": "electronicWithdrawals",
                        "render": function ( data, type, opt, meta ) {
                        	var result = "";
                        	if(opt.electronicWithdrawals !=null && opt.electronicWithdrawals !='' && opt.electronicWithdrawals !='undefined'){
									result = result + '<td><div class="tblminwidth-75">'+opt.electronicWithdrawals+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">--</div></td>';
								}
                        	 return result;
                        }
                      },{
                          "targets": colIndex++,
                          "data": "payrollChecks",
                          "render": function ( data, type, opt, meta ) {
                        	  var result = "";
                        	  if(opt.payrollChecksStr !=null && opt.payrollChecksStr !='' && opt.payrollChecksStr !='undefined'){
									result = result + '<td><div class="tblminwidth-75">$ '+opt.payrollChecksStr+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">$ 0.00</div></td>';
								}
                        	  return result;
                          }
                        },{
                            "targets": colIndex++,
                            "data": "amount",
                            "render": function ( data, type, opt, meta ) {
                            	var result = "";
                            	if(opt.amountStr !=null && opt.amountStr !='' && opt.amountStr !='undefined'){
									result = result + '<td><div class="tblminwidth-75">$ '+opt.amountStr+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">$ 0.00</div></td>';
								}
                            	 return result;
                            }
                          },{
                              "targets": colIndex++,
                              "data": "checkNo",
                              "render": function ( data, type, opt, meta ) {
                            	  var result = "";
                            	  if(opt.checkNo !=null && opt.checkNo !='' && opt.checkNo !='undefined'){
									result = result + '<td><div class="tblminwidth-75">'+opt.checkNo+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">--</div></td>';
								}
                            	  return result;
                              }
                } ],
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"aaSorting": [[1,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
    } );
    $("#checksListTable").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $("#checksDataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   bisChecksList();
    	   }
    	});
}

</script>

<!-- TODO do not delete @Rajashekar -->

<!--  <script>
function bisChecksList(){
	$("#addPayrollDiv").hide();
	var userId = '${SourceLeadWeb_user.id}';
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	
	$("#loading").attr('style','position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		  var sourceleadUrl ='${sourceleadUrl}';
				  $.ajax({
				    	type: "GET",
				        url: sourceleadUrl+"/getChecksList?${_csrf.parameterName}=${_csrf.token}?userId="+userId+"&orgLocationId="+orgLocationId,
				        success: function(data) {
						 var result = "";				        	
						result = result + '<table id="checksListTable" class="display" cellspacing="0" width="100%" >';
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
						$("#employeeListDiv").empty();
						$("#checksListDiv").empty();
						$("#invoiceDataDiv").empty();
						$("#checksListDiv").append(result);
						$("#loading").removeAttr("style");
						$('#payScale').css('display','none');
						checksListPagination(); 
				        	
				        },
				        
				    });
		 
}
</script>



<script>
function checksListPagination(){ 
    $('#checksListTable').DataTable( { 
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
    $("#checksListTable").wrap("<div class='overflow_Auto  width100percnt'></div>")
}
</script> -->

<!--  TODO do not delete @Rajashekar -->

