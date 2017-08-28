
<script>
function getInvoiceDetails(){
	$("#addPayrollDiv").hide();
	var userId = '${SourceLeadWeb_user.id}';
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	
	$("#loading").attr('style','position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		  var sourceleadUrl ='${sourceleadUrl}';
				  $.ajax({
				    	type: "GET",
				        url: sourceleadUrl+"/getInvoiceDetails?${_csrf.parameterName}=${_csrf.token}?userId="+userId+"&orgLocationId="+orgLocationId,
				        success: function(data) {
						 var result = "";				        	
						result = result + '<table id="invoiceDataTable" class="display" cellspacing="0" width="100%" >';
						result = result + '<thead>';
						result = result + '<tr>';
						result = result + '<th>SKU</th>';
						result = result + '<th>Product/Service</th>';
						result = result + '<th>Name</th>';
						result = result + '<th>Num</th>';
						result = result + '<th>Memo/Description</th>';
						result = result + '<th>Date</th>';
						result = result + '<th>Last Modified</th>';
						result = result + '<th>Due Date</th>';
						result = result + '<th>Rate</th>';
						result = result + '<th>Qty</th>';
						result = result + '<th>Amount</th>';
						result = result + '<th>A/R Paid</th>';
						result = result + '<th>Old/New</th>';
						result = result + '</tr>';
						result = result + '</thead>	';
						result = result + '<tbody>';
			        	 for (var i=0; i<data.length; i++)
						{
			        		 var opt = data[i];
			        		    result = result + ' <tr>';
			        		    
			        		    if(opt.sku !=null && opt.sku !='' && opt.sku !='undefined'){
			        		    	result = result + '<td><div class="tblminwidth-75">'+opt.sku+'</div></td>';
			        		    }else{
			        		    	result = result + '<td><div class="tblminwidth-75">---</div></td>';
			        		    }
			        		    
			        		    if(opt.productService !=null && opt.productService !='' && opt.productService !='undefined'){
			        		    	result = result + '<td><div>'+opt.productService+'</div></td>';
			        		    }else{
			        		    	result = result + '<td><div>---</div></td>';
			        		    }
			        		    
			        		    if(opt.empName !=null && opt.empName !='' && opt.empName !='undefined'){
									result = result + ' <td><div class="tblminwidth-75">'+opt.empName+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">--</div></td>';
								}
			        		    
			        		    if(opt.number !=null && opt.number !='' && opt.number !='undefined'){
									result = result + ' <td><div class="tblminwidth-75">'+opt.number+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">--</div></td>';
								}
								
								if(opt.memoDescription !=null && opt.memoDescription !='' && opt.memoDescription !='undefined'){
									result = result + ' <td><div class="tblminwidth-75">'+opt.memoDescription+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">--</div></td>';
								}
								
								if(opt.dateStr !=null && opt.dateStr !='' && opt.dateStr !='undefined'){
									result = result + ' <td><div class="tblminwidth-75">'+opt.dateStr+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">--</div></td>';
								}
								
								if(opt.lastModifiedStr !=null && opt.lastModifiedStr !='' && opt.lastModifiedStr !='undefined'){
									result = result + '<td><div class="tblminwidth-75">'+opt.lastModifiedStr+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">--</div></td>';
								}
								
								if(opt.dueDateStr !=null && opt.dueDateStr !='' && opt.dueDateStr !='undefined'){
									result = result + '<td><div class="tblminwidth-75">'+opt.dueDateStr+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">--</div></td>';
								}
								if(opt.rate !=null && opt.rate !='' && opt.rate !='undefined'){
									result = result + '<td><div class="tblminwidth-75">$ '+opt.rate+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">0.00</div></td>';
								}
								
								if(opt.qty !=null && opt.qty !='' && opt.qty !='undefined'){
									result = result + '<td><div class="tblminwidth-75">$ '+opt.qty+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">$ 0.00</div></td>';
								}
								
								if(opt.amount !=null && opt.amount !='' && opt.amount !='undefined'){
									result = result + '<td><div class="tblminwidth-75">$ '+opt.amount+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">$ 0.00</div></td>';
								}
								
								if(opt.arPaid !=null && opt.arPaid !='' && opt.arPaid !='undefined'){
									result = result + '<td><div class="tblminwidth-75">'+opt.arPaid+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">--</div></td>';
								}
								
								if(opt.oldNew !=null && opt.oldNew !='' && opt.oldNew !='undefined'){
									result = result + '<td><div class="tblminwidth-75">'+opt.oldNew+'</div></td>';
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
						$("#invoiceDataDiv").append(result);
						$("#loading").removeAttr("style");
						$('#payScale').css('display','none');
						invoiceDataTablePagination(); 
				        	
				        },
				        
				    });
		 
}
</script>



<script>
function invoiceDataTablePagination(){ 
    $('#invoiceDataTable').DataTable( { 
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
    $("#invoiceDataTable").wrap("<div class='overflow_Auto  width100percnt'></div>")
}
</script>

