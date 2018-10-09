<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
	
	<!-- <div class="tablelist-wrap">
        <div class="timesheet_head minHeight">
          <div class="clearfix"></div>
          <div class="remove_search">
            <input placeholder="Search" type="text">
          </div>
        </div>
          <div class="styleTables clearfix mt">
            <table>
              <thead>
                <tr>
                  <th>Pay Period</th>
                  <th>Pay Date</th>
                  <th>No. of Days</th>
                  <th>No. of Leaves</th>
                  <th>Gross Pay</th>
                  <th>TDS</th>
                  <th>Net Pay</th>
                  <th>Action</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>
                  	<div class="tblminwidth-150"><span class="mr">01/02/2018</span><span>02/02/2019</span></div>
                  </td>
                  <td>
					  <span class="tblminwidth-100">
							25/02/2018                 	
					  </span>
                  </td>
                   <td>
                  	<div class="tblminwidth-50 clearfix">200</div>
                  </td>
                  <td><div class="tblminwidth-50 clearfix">100</div></td>
                  <td><div class="tblminwidth-50 clearfix">100</div></td>
                  <td><div class="tblminwidth-50 clearfix">100</div></td>
                  <td><div class="tblminwidth-50 clearfix">100</div></td>
					<td><div class="tblminwidth-30 clearfix"><i class="fa fa-eye cursorPointer payView" title="Show Payslip"></i></div></td>
                </tr>
                 <tr>
                  <td>
                  	<div class="tblminwidth-150"><span class="mr">01/02/2018</span><span>02/02/2019</span></div>
                  </td>
                  <td>
					  <span class="tblminwidth-100">
							25/02/2018                 	
					  </span>
                  </td>
                   <td>
                  	<div class="tblminwidth-50 clearfix">200</div>
                  </td>
                  <td><div class="tblminwidth-50 clearfix">100</div></td>
                  <td><div class="tblminwidth-50 clearfix">100</div></td>
                  <td><div class="tblminwidth-50 clearfix">100</div></td>
                  <td><div class="tblminwidth-50 clearfix">100</div></td>
					<td><div class="tblminwidth-30 clearfix"><i class="fa fa-eye cursorPointer payView" title="Show Payslip"></i></div></td>
                </tr>
              </tbody>
            </table>
          </div>
        <div class="clearfix"></div>
        <nav>
          <ul id="paging" class="pagination pull-right mr">
            <li><a href="#">Previous</a></li>
            <li><a href="#">2</a></li>
            <li><a href="#">3</a></li>
            <li><a href="#">4</a></li>
            <li><a href="#">5</a></li>
            <li class="disabled"><a href="#">6</a></li>
            <li data-page="last"><a href="#">Next</a></li>
          </ul>
          <div class="clearfix"></div>
        </nav>
      </div> -->
	

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
												result = result +'<div class="tblminwidth-100" ><a>'+opt.payPeriod+'</a></div>';
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
						              		  result = result +'<div class="tblminwidth-100">--</div>';
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
						              		  result = result +'<div class="tblminwidth-100">--</div>';
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
						                		  result = result +'<div class="tblminwidth-100">'+opt.grossPay+'</div>';
						              		}else{
						              		  result = result +'<div class="tblminwidth-100">--</div>';
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
						                		  result = result +'<div class="tbldata-dotdot-vsm">'+opt.tds+'</div>';
						              		}else{
						              		  result = result +'<div class="tbldata-dotdot-vsm">0.0</div>';
						              		}
											return result;
										}
										
									},{
										"targets" : colIndex++,
										"data" : "Net Pay",
										"render" : function(data, type, opt,meta) {
											var result = "";
											if(opt.netPay != null && opt.netPay != '' && opt.netPay !='undefined'){
						                		  result = result +'<div class="tbldata-dotdot-vsm">'+opt.netPay+'</div>';
						              		}else{
						              		  result = result +'<div class="tbldata-dotdot-vsm">0.0</div>';
						              		}
											return result;
										}
										
									},{
										"targets" : colIndex++,
										"data" : "Action",
										"render" : function(data, type, opt,meta) {
											var result = "";
											if(opt.submitForAproverStatus != null && opt.submitForAproverStatus != '' && opt.submitForAproverStatus !='undefined' && opt.submitForAproverStatus == 'APPROVED'){
						                		  result = result +'<div class="tblminwidth-30 clearfix"><i class="fa fa-eye cursorPointer payView" title="Show Payslip"></i></div>';
						              		}else{
						              		  result = result +'<div class="tblminwidth-100">--</div>';
						              		}
											return result;
										}
									}],
							"scrollCollapse" : false,
							"lengthMenu" : [ [ 10, 15, 20 ], [ 10, 15, 20 ] ],
							"aaSorting" : [ [ 3, 'desc' ] ],
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
