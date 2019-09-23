<script>
function getEmployeeList(){
	$("#addPayrollDiv").hide();
	var sourceleadUrl ='${sourceleadUrl}';
	var searchValue = $("#employeesDataTableSearch").val();
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	searchValue = searchValue ? searchValue : "";
	var url = sourceleadUrl+"/getEmployeeList?searchValue="+searchValue+"&orgLocationId="+orgLocationId;
    prepareEmployeeList(url);
}

</script>

<script>
function prepareEmployeeList(url){
	$("#employerCostDiv").empty();
	$("#accountDeductionsListDiv").empty();
	$("#hrDeductionsListDiv").empty();
	$("#wagesAndTaxListDiv").empty();
	$("#checksListDiv").empty();
	$("#invoiceDataDiv").empty();
	$("#finalPayrollReportDiv").empty();
	$("#loading").removeAttr("style");
	$('#payScale').css('display','none');
	
	 $("#employeeListDiv").removeAttr("style");
 	 $("#employeeListDiv").empty();
 	$("#payrollCycleDiv").find('p.jquery_form_error_message').remove();
	$('#payrollCycle').val('').trigger("chosen:updated");
 	 var result = "";
 	result = result +" <div class='modal-header'>";
 	result = result +" <div class='remove_search'>";
 	result = result +" <input type='text' placeholder='Search' id='employeesDataTableSearch'/>";
 	result = result +"</div>";
 	result = result +"</div>";
 	result = result + '<table id="employeesListTablePagination" class="display" cellspacing="0" width="100%" >';
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
    result = result +"</table>";
    $("#employeeListDiv").append(result);
    employeesPagination(url); 
	
	
}

</script>

<script>
function employeesPagination(url){
	var colIndex = 0;
    $('#employeesListTablePagination').DataTable( { 
	  "processing": true,
      "serverSide": true,
      "searching": false,
      "ajax": $.fn.dataTable.pipeline( {
          url: url,
          method: 'POST',
          pages: 10
        }),
        "columns": [
            { "data": "company" },
            { "data": "branch"},
            { "data": "empId"},
            { "data": "candidateName" },
            { "data": "rate" },
            { "data": "fixedRate"},
            { "data": "payRate"},
            { "data": "fixedPay"},
            { "data": "insurance"},
            { "data": "ded"},
            { "data": "effectiveRate"},
            { "data": "payrollDifference"},
            { "data": "immigration"},
            { "data": "comments"},
            { "data": "advanceHcp"},
            { "data": "balance"}
        ],
        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"data": "company",
	    	  	"render": function(data, type, opt, meta) {
	    	  		  var result = "";
	    	  		  if(opt.company !=null && opt.company !='' && opt.company !='undefined'){
        		    	result = result + '<td><div class="tblminwidth-75">'+opt.company+'</div></td>';
        		    }else{
        		    	result = result + '<td><div class="tblminwidth-75">---</div></td>';
        		    }
		             return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "branch",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  if(opt.branch !=null && opt.branch !='' && opt.branch !='undefined'){
      		    	result = result + '<td><div>'+opt.branch+'</div></td>';
      		    }else{
      		    	result = result + '<td><div>---</div></td>';
      		    }
	              return result;
              		
              }
	          },{
            "targets": colIndex++,
            "data": "empId",
            "render": function ( data, type, opt, meta ) {
            	var result = "";
            	 if(opt.empId !=null && opt.empId !='' && opt.empId !='undefined'){
						result = result + ' <td><div class="tblminwidth-75">'+opt.empId+'</div></td>';
					}else{
						result = result + ' <td><div class="tblminwidth-75">--</div></td>';
					}
            	 return result;
            }
          }, {
              "targets": colIndex++,
              "data": "candidateName",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  if(opt.candidateName !=null && opt.candidateName !='' && opt.candidateName !='undefined'){
						result = result + ' <td><div class="tbldata-dotdot-vsm" title="'+opt.candidateName+'">'+opt.candidateName+'</div></td>';
					}else{
						result = result + ' <td><div class="tbldata-dotdot-vsm">--</div></td>';
					}
            	  return result;
              }
            }, {
                "targets": colIndex++,
                "data": "rate",
                "render": function ( data, type, opt, meta ) {
                	var result = "";
                	if(opt.rate !=null && opt.rate !='' && opt.rate !='undefined'){
						result = result + ' <td><div class="tblminwidth-75">'+opt.rate+'</div></td>';
					}else{
						result = result + ' <td><div class="tblminwidth-75">--</div></td>';
					}
    	              return result;
                }
          },{
	    	  	"targets": colIndex++,
	    	  	"data": "fixedRate",
	    	  	"render": function(data, type, opt, meta) {
	    	  		var result = "";
	    	  		if(opt.fixedRate !=null && opt.fixedRate !='' && opt.fixedRate !='undefined'){
						result = result + ' <td><div class="tblminwidth-75">'+opt.fixedRate+'</div></td>';
					}else{
						result = result + ' <td><div class="tblminwidth-75">--</div></td>';
					}
  	              return result;
	    	  	}
	    	  },{
	    	  	"targets": colIndex++,
	    	  	"data": "payRate",
	    	  	"render": function(data, type, opt, meta) {
	    	  		var result = "";
	    	  		if(opt.payRate !=null && opt.payRate !='' && opt.payRate !='undefined'){
						result = result + '<td><div class="tblminwidth-75">'+opt.payRate+'</div></td>';
					}else{
						result = result + ' <td><div class="tblminwidth-75">--</div></td>';
					}
	    	  		 return result;
	    	  	}
	    	  }, {
                  "targets": colIndex++,
                  "data": "fixedPay",
                  "render": function ( data, type, opt, meta ) {
                	  var result = "";
                	  if(opt.fixedPay !=null && opt.fixedPay !='' && opt.fixedPay !='undefined'){
							result = result + '<td><div class="tblminwidth-75">$ '+opt.fixedPay+'</div></td>';
						}else{
							result = result + ' <td><div class="tblminwidth-75">--</div></td>';
						}
                	  return result;
                  }
                },{
                    "targets": colIndex++,
                    "data": "insurance",
                    "render": function ( data, type, opt, meta ) {
                    	var result = "";
                    	if(opt.insurance !=null && opt.insurance !='' && opt.insurance !='undefined'){
							result = result + '<td><div class="tblminwidth-75">'+opt.insurance+'</div></td>';
						}else{
							result = result + ' <td><div class="tblminwidth-75">--</div></td>';
						}
                    	 return result;
                    }
                  },{
                      "targets": colIndex++,
                      "data": "ded",
                      "render": function ( data, type, opt, meta ) {
                    	  var result = "";
                    	  if(opt.ded !=null && opt.ded !='' && opt.ded !='undefined'){
								result = result + '<td><div class="tblminwidth-75">$ '+opt.ded+'</div></td>';
							}else{
								result = result + ' <td><div class="tblminwidth-75">--</div></td>';
							}
                    	  return result;
                      }
                    },{
                        "targets": colIndex++,
                        "data": "effectiveRate",
                        "render": function ( data, type, opt, meta ) {
                        	var result = "";
                        	if(opt.effectiveRate !=null && opt.effectiveRate !='' && opt.effectiveRate !='undefined'){
								result = result + '<td><div class="tblminwidth-75">'+opt.effectiveRate+'</div></td>';
							}else{
								result = result + ' <td><div class="tblminwidth-75">--</div></td>';
							}
                        	 return result;
                        }
                      },{
                          "targets": colIndex++,
                          "data": "payrollDifference",
                          "render": function ( data, type, opt, meta ) {
                        	  var result = "";
                        	  if(opt.payrollDifference !=null && opt.payrollDifference !='' && opt.payrollDifference !='undefined'){
									result = result + '<td><div class="tblminwidth-75">$ '+opt.payrollDifference+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">--</div></td>';
								}
                        	  return result;
                          }
                        },{
                            "targets": colIndex++,
                            "data": "immigration",
                            "render": function ( data, type, opt, meta ) {
                            	var result = "";
                            	if(opt.immigration !=null && opt.immigration !='' && opt.immigration !='undefined'){
									result = result + '<td><div class="tblminwidth-75">$ '+opt.immigration+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">--</div></td>';
								}
                            	 return result;
                            }
                          },{
                              "targets": colIndex++,
                              "data": "comments",
                              "render": function ( data, type, opt, meta ) {
                              	var result = "";
                              	if(opt.comments !=null && opt.comments !='' && opt.comments !='undefined'){
									result = result + '<td><div class="tblminwidth-75">'+opt.comments+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">--</div></td>';
								}
                              	 return result;
                              }
                            },
                            {
                                "targets": colIndex++,
                                "data": "advanceHcp",
                                "render": function ( data, type, opt, meta ) {
                                	var result = "";
                                	if(opt.advanceHcp !=null && opt.advanceHcp !='' && opt.advanceHcp !='undefined'){
    									result = result + '<td><div class="tblminwidth-75">'+opt.advanceHcp+'</div></td>';
    								}else{
    									result = result + ' <td><div class="tblminwidth-75">--</div></td>';
    								}
                                	 return result;
                                }
                              },{
                              "targets": colIndex++,
                              "data": "balance",
                              "render": function ( data, type, opt, meta ) {
                            	  var result = "";
                            	  if(opt.balance !=null && opt.balance !='' && opt.balance !='undefined'){
  									result = result + '<td><div class="tblminwidth-75">'+opt.balance+'</div></td>';
  								}else{
  									result = result + ' <td><div class="tblminwidth-75">--</div></td>';
  								}
                            	  return result;
                              }
                } ],
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"aaSorting": [[3,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
    } );
    $("#employeesListTablePagination").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
   
}
$("#employeesDataTableSearch").keyup(function(e) {
	   if(e.which === 13) {
		   getEmployeeList();
	   }
	});
</script>

<!--  TODO do not delete @Rajashekar -->
<!-- <script>
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

 -->
 
 <!--  TODO do not delete @Rajashekar -->