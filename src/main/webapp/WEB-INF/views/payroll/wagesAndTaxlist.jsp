

ss<script type="text/javascript">
function wagesAndTaxList(){
		var sourceleadUrl ='${sourceleadUrl}';
		var searchValue = $("#dataTableSearch").val();
		searchValue = searchValue ? searchValue : "";
		var url = sourceleadUrl+"/getWagesAndTaxList?searchValue="+searchValue;
		/* url += "&"+$($('#payroll_list_form')[0]).serialize(); */
	    prepareWagesAndTaxList(url);
}
</script> 

<script>
function prepareWagesAndTaxList(url){
	$("#employerCostDiv").empty();
	$("#accountDeductionsListDiv").empty();
	$("#hrDeductionsListDiv").empty();
	$("#checksListDiv").empty();
	$("#employeeListDiv").empty();
	$("#invoiceDataDiv").empty();
	$("#finalPayrollReportDiv").empty();
	$("#wagesAndTaxListDiv").append(result);
	$("#loading").removeAttr("style");
	$('#payScale').css('display','none');
	
	 $("#wagesAndTaxListDiv").removeAttr("style");
 	 $("#wagesAndTaxListDiv").empty();
 	 var result = "";
 	result = result +" <div class='modal-header'>";
 	result = result +" <div class='remove_search'>";
 	result = result +" <input type='text' placeholder='Search' id='dataTableSearch'/>";
 	result = result +"</div>";
 	result = result +"</div>";
 	  result = result +"<table id='wageTaxPagination' class='display' cellspacing='0' width='100%' >"; 
      result = result +"<thead>"; 
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
      result = result +"</thead>";
      result = result +"<tbody>"; 
      result = result +" </tbody>";
      result = result +"</table>";
      $("#wagesAndTaxListDiv").append(result);
      wagesTaxpagination(url); 
}
</script>


<script>
function wagesTaxpagination(url){
	var colIndex = 0;
	/*$.fn.dataTable.moment( 'DD-MM-YYYY' );*/
    $('#wageTaxPagination').DataTable( { 
    		"processing": true,
        "serverSide": true,
        "searching": false,
        "ajax": $.fn.dataTable.pipeline( {
            url: url,
            method: 'POST',
            pages: 10
        }),
        "columns": [
            { "data": "fromDate" },
            { "data": "toDate"},
            { "data": "employeeId"},
            { "data": "employeeName" },
            { "data": "ssnNumber"},
            { "data": "checkDate" },
            { "data": "checkNo" },
            { "data": "gross"},
            { "data": "fwt"},
            { "data": "ssec"},
            { "data": "med"},
            { "data": "swt"},
            { "data": "sdiSui"},
            { "data": "local"},
            { "data": "deds"},
            { "data": "net"},
            { "data": "batchNumber" }
        ],
        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"className": "tbldata-dotdot_50",
	    	  	"data": "fromDate",
	    	  	"render": function(data, type, opt, meta) {
	    	  		  var result = "";
	    	  		 if(opt.fromDateStr !=null && opt.fromDateStr !='' && opt.fromDateStr !='undefined'){
							result = result + ' <td><div class="tblminwidth-75">'+opt.fromDateStr+'</div></td>';
						}else{
							result = result + ' <td><div>--</div></td>';
						}
		             return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "toDate",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  if(opt.toDateStr !=null && opt.toDateStr !='' && opt.toDateStr !='undefined'){
						result = result + ' <td><div class="tblminwidth-75">'+opt.toDateStr+'</div></td>';
					}else{
						result = result + ' <td><div>--</div></td>';
					}
	              return result;
              		
              }
	          },{
            "targets": colIndex++,
            "data": "employeeId",
            "render": function ( data, type, opt, meta ) {
            	return  '<td><div class="tblminwidth-75">'+opt.employeeId+'</div></td>';
            }
          }, {
              "targets": colIndex++,
              "data": "employeeName",
              "render": function ( data, type, opt, meta ) {
            	  return '<td><div class="tbldata-dotdot-vsm">'+opt.employeeName+'</div></td>';
              }
            }, {
                "targets": colIndex++,
                "data": "ssnNumber",
                "render": function ( data, type, opt, meta ) {
                		var result = "";
                		if(opt.ssnNumber !=null && opt.ssnNumber !='' && opt.ssnNumber !='undefined'){
							result = result + ' <td><div class="tbldata-dotdot-sm"">'+opt.ssnNumber+'</div></td>';
						}else{
							result = result + ' <td><div>--</div></td>';
						}
    	              return result;
                }
          },{
	    	  	"targets": colIndex++,
	    	  	"data": "checkDate",
	    	  	"render": function(data, type, opt, meta) {
	    	  		var result = "";
	    	  		if(opt.checkDateStr !=null && opt.checkDateStr !='' && opt.checkDateStr !='undefined'){
						result = result + '<td><div class="tblminwidth-50">'+opt.checkDateStr+'</div></td>';
					}else{
						result = result + ' <td><div>--</div></td>';
					}
  	              return result;
	    	  	}
	    	  },{
	    	  	"targets": colIndex++,
	    	  	"data": "checkNo",
	    	  	"render": function(data, type, opt, meta) {
	    	  		return ' <td><div class="tblminwidth-75">'+opt.checkNo+'</div></td>';
	    	  	}
	    	  }, {
                  "targets": colIndex++,
                  "data": "gross",
                  "render": function ( data, type, opt, meta ) {
                	  return '<td><div class="tblminwidth-75">'+opt.gross+'</div></td>';
                  }
                },{
                    "targets": colIndex++,
                    "data": "fwt",
                    "render": function ( data, type, opt, meta ) {
                    	return '<td><div class="tblminwidth-75">'+opt.fwt+'</div></td>';
                    }
                  },{
                      "targets": colIndex++,
                      "data": "ssec",
                      "render": function ( data, type, opt, meta ) {
                    	  return ' <td><div class="tblminwidth-75">'+opt.ssec+'</div></td>';
                      }
                    },{
                        "targets": colIndex++,
                        "data": "med",
                        "render": function ( data, type, opt, meta ) {
                        	return '<td><div class="tblminwidth-75">'+opt.med+'</div></td>';
                        }
                      },{
                          "targets": colIndex++,
                          "data": "swt",
                          "render": function ( data, type, opt, meta ) {
                        	 return' <td><div class="tblminwidth-75">'+opt.swt+'</div></td>';
                          }
                        },{
                            "targets": colIndex++,
                            "data": "sdiSui",
                            "render": function ( data, type, opt, meta ) {
                            	return '<td><div class="tblminwidth-75">'+opt.sdiSui+'</div></td>';
                            }
                          },{
                              "targets": colIndex++,
                              "data": "local",
                              "render": function ( data, type, opt, meta ) {
                            	  return '<td><div class="tblminwidth-75">'+opt.local+'</div></td>';
                              }
                            },{
                                "targets": colIndex++,
                                "data": "deds",
                                "render": function ( data, type, opt, meta ) {
                                	var result = "";
                                	result = result + '<td><div class="deeds tblminwidth-125">'+opt.deds+'</div>';
                                	result = result + '<div class="edit-input tblminwidth-125" style="display:none;"><input type="text" class="form-control input-sm tbldata-dotdot-100 pull-left" value="" id=""><small class="fa fa-floppy-o ml10 pull-right mt5"  onClick="saveEdit(this)"></small></div></td>';
                                    return result;
                                }
                              },{
                                  "targets": colIndex++,
                                  "data": "net",
                                  "render": function ( data, type, opt, meta ) {
                                	 return '<td><div class="tblminwidth-75">'+opt.net+'</div></td>';
                                  }
                                }, {
                  "targets": colIndex++,
                  "data": "batchNumber",
                  "className": "tblminwidth-75",
                  "render": function ( data, type, opt, meta ) {
                  		var result = "";
                  		if(opt.batchNumber !=null && opt.batchNumber !='' && opt.batchNumber !='undefined'){
								result = result + ' <td><div class="tblminwidth-75">'+opt.batchNumber+'</div></td>';
							}else{
								result = result + ' <td><div>--</div></td>';
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
    $("#wageTaxPagination").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $("#dataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   wagesAndTaxList();
    	   }
    	});
}
</script>

<script>
function wagesAndTaxListRajashekar(){
	$("#addPayrollDiv").hide();
	$("#loading").attr('style','position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
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
									result = result + ' <td><div class="tblminwidth-75">'+opt.fromDateStr+'</div></td>';
								}else{
									result = result + ' <td><div>--</div></td>';
								}
			        		    
			        		    if(opt.toDateStr !=null && opt.toDateStr !='' && opt.toDateStr !='undefined'){
									result = result + ' <td><div class="tblminwidth-75">'+opt.toDateStr+'</div></td>';
								}else{
									result = result + ' <td><div>--</div></td>';
								}
								result = result + '<td><div class="tblminwidth-75">'+opt.employeeId+'</div></td>';
								result = result + '<td><div class="tbldata-dotdot-vsm">'+opt.employeeName+'</div></td>';
								if(opt.ssnNumber !=null && opt.ssnNumber !='' && opt.ssnNumber !='undefined'){
									result = result + ' <td><div class="tbldata-dotdot-sm"">'+opt.ssnNumber+'</div></td>';
								}else{
									result = result + ' <td><div>--</div></td>';
								}
								if(opt.checkDateStr !=null && opt.checkDateStr !='' && opt.checkDateStr !='undefined'){
									result = result + '<td><div class="tblminwidth-50">'+opt.checkDateStr+'</div></td>';
								}else{
									result = result + ' <td><div>--</div></td>';
								}
								result = result + ' <td><div class="tblminwidth-75">'+opt.checkNo+'</div></td>';
								result = result + '<td><div class="tblminwidth-75">'+opt.gross+'</div></td>';
								result = result + '<td><div class="tblminwidth-75">'+opt.fwt+'</div></td>';
								result = result + ' <td><div class="tblminwidth-75">'+opt.ssec+'</div></td>';
								result = result + '<td><div class="tblminwidth-75">'+opt.med+'</div></td>';
								result = result + ' <td><div class="tblminwidth-75">'+opt.swt+'</div></td>';
								result = result + '<td><div class="tblminwidth-75">'+opt.sdiSui+'</div></td>';
								result = result + '<td><div class="tblminwidth-75">'+opt.local+'</div></td>';
								result = result + '<td><div class="deeds tblminwidth-125">'+opt.deds+'</div>';
								result = result + '<div class="edit-input tblminwidth-125" style="display:none;"><input type="text" class="form-control input-sm tbldata-dotdot-100 pull-left" value="" id=""><small class="fa fa-floppy-o ml10 pull-right mt5"  onClick="saveEdit(this)"></small></div></td>';
								
								result = result + '<td><div class="tblminwidth-75">'+opt.net+'</div></td>';
								if(opt.batchNumber !=null && opt.batchNumber !='' && opt.batchNumber !='undefined'){
									result = result + ' <td><div class="tblminwidth-75">'+opt.batchNumber+'</div></td>';
								}else{
									result = result + ' <td><div>--</div></td>';
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
						$("#checksListDiv").empty();
						$("#employeeListDiv").empty();
						$("#invoiceDataDiv").empty();
						$("#finalPayrollReportDiv").empty();
						$("#wagesAndTaxListDiv").append(result);
						$("#loading").removeAttr("style");
						$('#payScale').css('display','none');
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
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    });
    $("#WagesAndTaxListTable").wrap("<div class='overflow_Auto width100percnt'></div>")
}
</script>
<script>
$(".edit-input").hide();
//document.get
function showEdit(el){
	$(".edit-input").show();
	$(".deeds").hide();
}
function saveEdit(){
	$(".edit-input").hide();
	$(".deeds").show();
}
</script>
