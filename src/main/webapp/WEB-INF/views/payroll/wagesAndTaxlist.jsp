
<script>
function wagesAndTaxList(){
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
