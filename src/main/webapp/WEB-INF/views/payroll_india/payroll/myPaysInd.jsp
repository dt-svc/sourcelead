<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
	
<script>
	function prepareMyPaysListInd() {
		var result = "";
		result = result + '<div class="reportHead" id="paySearchIndDiv">';
		result = result + '	<ul class="pull-right">';
		result = result + '	<li><input type="text" placeholder="Search" class="repSearchBar" id="myPayTableSearchInd"/><span class="repSearchIcon" onClick="prepareMyPaysListInd();"></span></li>';
		result = result + '	</ul>';
		result = result + '</div>';
		result = result + '<table id="payListTable" class="display" cellspacing="0" width="100%" >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result + '<th>Pay Period</th>';
		result = result + '<th>Pay Date</th>';
		result = result + '<th>No. of Days</th>';
		result = result + '<th>No. of Leaves</th>';
		result = result + '<th>Gross Pay</th>';
		result = result + '<th>TDS</th>';
		result = result + '<th>Net Pay</th>';
		result = result + '<th>Action</th>';
		result = result + '</tr>';
		result = result + '</thead>	';
		result = result + "</table>";
		$("#myPayRollTabsContent").empty();
		$("#myPayRollTabsContent").append(result);
		var searchValue = $("#myPayTableSearchInd").val();
		searchValue = searchValue ? searchValue : "";
		var url = sourceleadUrl+ "/getMyPaysListInd?searchValue="+searchValue;
		myPaysListIndPagination(url);
	}
  
  function myPaysListIndPagination(url) {
		var sourceleadUrl = '${sourceleadUrl}';
		var colIndex = 0;
		$('#payListTable').DataTable({
							"processing" : true,
							"serverSide" : true,
							"searching" : false,
							"ajax" : $.fn.dataTable.pipeline({
								url : url,
								method : 'GET',
								pages : 10
							}),
							
							"columns" : [ {
								"data" : "Pay Period"
							}, {
								"data" : "Pay Date"
							}, {
								"data" : "No of Days"
							}, {
								"data" : "No of Leaves"
							},{
								"data" : "Gross Pay"
							},{
								"data" : "TDS"
							},{
								"data" : "Net Pay"
							}, {
								"data" : "Action"
							}],
							
							
							 "columnDefs" : [
									{
										"targets" : colIndex++,
										"data" : "Pay Period",
										"render" : function(data, type, opt,meta) {
											var result = "";
											if(opt.payPeriod != null && opt.payPeriod != '' && opt.payPeriod !='undefined'){
												result = result +'<div class="tblminwidth-100" >'+opt.payPeriod+'</div>';
											}else{
						              		  result = result +'<div class="tblminwidth-100">--</div>';
						              		}
											return result;
										}
									},{
										"targets" : colIndex++,
										"data" : "Pay Date",
										"render" : function(data, type, opt,meta) {
											var result = "";
											if(opt.payCheckDateStr != null && opt.payCheckDateStr != '' && opt.payCheckDateStr !='undefined'){
						                		  result = result +'<div class="tblminwidth-100">'+opt.payCheckDateStr+'</div>';
											}else{
						              		  result = result +'<div class="tblminwidth-100">--</div>';
						              		}
											return result;
										}
									},{
										"targets" : colIndex++,
										"data" : "No of Days",
										"render" : function(data, type, opt,meta) {
											var result = "";
											if(opt.noOfDays != null && opt.noOfDays != '' && opt.noOfDays !='undefined'){
						                		  result = result +'<div class="tblminwidth-100">'+opt.noOfDays+'</div>';
						              		}else{
						              		  result = result +'<div class="tblminwidth-100">0</div>';
						              		}
											return result;
										}
									},
									{
										"targets" : colIndex++,
										"data" : "No of Leaves",
										"render" : function(data, type, opt,meta) {
											var result = "";
											if(opt.noOfLeaves != null && opt.noOfLeaves != '' && opt.noOfLeaves !='undefined'){
						                		  result = result +'<div class="tblminwidth-100">'+opt.noOfLeaves+'</div>';
						              		}else{
						              		  result = result +'<div class="tblminwidth-100">0</div>';
						              		}
											return result;

										}
									},
									{
										"targets" : colIndex++,
										"data" : "Gross Pay",
										"render" : function(data, type, opt,meta) {
											var result = "";
											if(opt.grossPay != null && opt.grossPay != '' && opt.grossPay !='undefined'){
						                		  result = result +'<div class="tblminwidth-100">'+opt.grossPay+' <i class="paddingLeft">&#8377;</i></div>';
						              		}else{
						              		  result = result +'<div class="tblminwidth-100">0.00 <i class="paddingLeft">&#8377;</i></div>';
						              		}
											return result;
										}
									},
									{
										"targets" : colIndex++,
										"data" : "TDS",
										"render" : function(data, type, opt,meta) {
											var result = "";
											if(opt.tds != null && opt.tds != '' && opt.tds !='undefined'){
						                		  result = result +'<div class="tbldata-dotdot-vsm">'+opt.tds+' <i class="paddingLeft">&#8377;</i></div>';
						              		}else{
						              		  result = result +'<div class="tbldata-dotdot-vsm">0.00 <i class="paddingLeft">&#8377;</i></div>';
						              		}
											return result;
										}
										
									},{
										"targets" : colIndex++,
										"data" : "Net Pay",
										"render" : function(data, type, opt,meta) {
											var result = "";
											if(opt.netPay != null && opt.netPay != '' && opt.netPay !='undefined'){
						                		  result = result +'<div class="tbldata-dotdot-vsm">'+opt.netPay+' <i class="paddingLeft">&#8377;</i></div>';
						              		}else{
						              		  result = result +'<div class="tbldata-dotdot-vsm">0.00 <i class="paddingLeft">&#8377;</i></div>';
						              		}
											return result;
										}
										
									},{
										"targets" : colIndex++,
										"data" : "Action",
										"render" : function(data, type, opt,meta) {
											var result = "";           
											if(opt.submitForApproverStatus != null && opt.submitForApproverStatus != '' && opt.submitForApproverStatus !='undefined' && opt.submitForApproverStatus == 'APPROVED'){
						                		  result = result +'<div class="tblminwidth-30 clearfix">';
						                		  result = result +'<a href="<c:url value="${appPath}/indPayrollPaySlipView?empPayrollUUID='+opt.indEmpPayrollUUID+'"/>"  class="fa fa-eye mr" title="View"></a>';
						                		  result = result +'<a class="glyphicon glyphicon-download-alt" title="Download Payslip" onclick="downloadPayslip(\''+opt.indEmpPayrollUUID+'\')"></a>';
						                		  result = result +'</div>';
						              		}else{
						              		  result = result +'<div class="tblminwidth-100">--</div>';
						              		}
											return result;
										}
									}],
							"scrollCollapse" : false,
							"lengthMenu" : [ [ 10, 15, 20 ], [ 10, 15, 20 ] ],
							"aaSorting" : [ [ 0, 'desc' ] ],
							"bLengthChange" : true,
							"bDestroy" : true,
							"ordering" : true,
							"oLanguage" : {
								"sSearch" : "",
								"sZeroRecords" : "",
								"sEmptyTable" : "",
								"sProcessing" : "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
							}
						});
		$("#payListTable").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>");
		$("input[type=search]").hide();
		$("#myPayTableSearchInd").keyup(function(e) {
			if (e.which === 13) {
				prepareMyPaysListInd();
			}
		});
	}
</script>

<script>
	$(document).ready(function() {
		prepareMyPaysListInd();
	});
</script>

