<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script src="/resources/js/payrollReports.js"></script>

<script>
$(document).ready(function(){
	getPayGroupList("payGroup");
});
</script>

  <!-- Content Area -->
  <section class="content-wraper contentWraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Payroll List Summary</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard'/>" title="Home">Home</a>&raquo;<a
				href="<c:url value='${appPath}/usPayrollReport'/>"> US Payroll Reports List</a>&raquo;Payroll List Summary
		</div>
    </section>
    <div class="clearfix"></div>
    <div class="timesheet_out">
      <div class="tablelist-wrap">
        <div class="timesheet_head minHeight">
          <div class="col-md-7 mt">
            <div class="row">
              <div class="col-md-3">
                <div class="dropdown-select">
                  <select class="chosen-select" id="payGroup" name="payGroup" onchange="clearDropDown();">
                   <!--  <option selected>Pay Period</option>
                    <option>Group-1</option>
                    <option>Group-2</option>
                    <option>Group-3</option> -->
                  </select>
                </div>
                <div><span id="payPeriodErrorDiv"></span></div>
              </div>
              <div class="col-md-3 paddingRight" id="payrollPayDateDivId">
                <input name="" class="form-control calendar" placeholder="Pay Check Date" type="text" id="payCheckDate" name="payCheckDate">
              </div>
            </div>
          </div>
       <!--    <div class="clearfix"></div>
          <div class="remove_search">
            <input placeholder="Search" type="text">
          </div> -->
        </div>
          <div class="styleTables clearfix mt">
        <div class='reportHead'>
		<ul class='pull-right'>
		<li><input type='text' placeholder='Search' class='repSearchBar' id='dataTableSearch'/><span class='repSearchIcon' onClick='payCheckDateSearch();'></span></li>
		</ul>
		</div>
          <div id="payCheckDateTableDIV" class="datatable_filter"></div>
            <!-- <table>
              <thead>
                <tr>
                  <th>Emp Name</th>
                  <th>Rate</th>
                  <th>Regular Hours</th>
                  <th>Overtime Hours</th>
                  <th>Gross Pay</th>
                  <th>Pre-Tax Deduction</th>
                  <th>Post-Tax Deduction</th>
                  <th>Taxes</th>
                  <th>Net Pay</th>
                  <th>Last Pay</th>
                  <th>YTD</th>
                  <th>Action</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td><div class="tbldata-dotdot-vsm">Laxmisaraswathi</div></td>
                  <td><div class="tblminwidth-50">20$/hrs</div></td>
                  <td><div class="tblminwidth-50 clearfix">40Hrs</div><div class="tblminwidth-50 clearfix">800$</div></td>
                  <td><div class="tblminwidth-50 clearfix">10Hrs</div><div class="tblminwidth-50 clearfix">300$</div></td>
                  <td><div class="tblminwidth-50 clearfix">100$</div></td>
                  <td><div class="tblminwidth-50 clearfix">100$</div></td>
                  <td><div class="tblminwidth-50 clearfix">50$</div></td>
                  <td><div class="tblminwidth-50 clearfix">850$</div></td>
                  <td><div class="tblminwidth-50 clearfix"><a href="javascript:void(0)" onclick="usRunpayroll();">200$</a></div></td>
                  <td><div class="tblminwidth-50 clearfix">850$</div></td>
                  <td><div class="tblminwidth-50 clearfix">20,000$</div></td>
                  <td>
					  <div class="tblminwidth-30 text-center">
					  	<a href="javascript:void(0)" class="anchorBlack"><i class="fa fa-eye"></i></a>
					  </div>
                  </td>
                </tr>
              </tbody>
            </table> -->
          </div>
        <div class="clearfix"></div>
       <!--  <nav>
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
        </nav> -->
      </div>
    </div>
  </section>
  <!-- Content Area Ends--> 
  
  <script>
  function clearDropDown(){
	  $("#payPeriodErrorDiv").text('');  
  }
  
  </script>
  <script>
	$(function() {
		
		 var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
		  // written common method in slead-common.js for date formats start
		  var finalDateFormat = getJSDateFormat(locationDate);
		  //end
		  
		  $('#payCheckDate').datepicker({
		      dateFormat: finalDateFormat,
		      changeMonth: true,
		  	  changeYear: true, 
		  	  onSelect : function(selected) {
				addValidClass();
				getPayCheckDateReports();
			}
		  
		      //minDate: 0  this line for to restrict selection of previous dates
		  });
		  
	});
	
	function addValidClass(){
		 $('#payrollPayDateDivId').find('p.jquery_form_error_message').remove(); 
	}
</script>


<script>
	function getPayCheckDateReports() {
		var payCheckDate = $('#payCheckDate').val();
		var groupId = $('#payGroup').val();
		if(groupId != '' && groupId != 'undefined'){
			var searchValue = $("#dataTableSearch").val();
			searchValue = searchValue ? searchValue : "";
			var url = sourceleadUrl+"/getPayCheckDateReports?searchValue="+searchValue+"&groupId="+groupId+"&payCheckDate="+payCheckDate;
			payCheckDatePayrollList(url);	
		}else{
			$("#payPeriodErrorDiv").text('select pay period');
		}
		
	}
</script>

<script>
	function payCheckDatePayrollList(url) {
		$("#payCheckDateTableDIV").empty();
		var result = "";
		result = result + '<table id="payCheckDateReportTabble" class="display" cellspacing="0" width="100%" >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result + '<th>Emp Name</th>';
		result = result + '<th>Rate</th>';
		result = result + '<th>Reugular Hours</th>';
		result = result + '<th>Overtime Hours</th>';
		result = result + '<th>Gross pay</th>';
		result = result + '<th>Pre-Tax Deduction</th>';
		result = result + '<th>Post-Tax Deduction</th>';
		result = result + '<th>Taxes</th>';
		result = result + '<th>Net Pay</th>';
		result = result + '<th>Last Pay</th>';
		result = result + '<th>YTD</th>';
		result = result + '<th>Action</th>';
		result = result + '</tr>';
		result = result + '</thead>	';
		result = result + "</table>";
		$("#payCheckDateTableDIV").append(result);
		payCheckDateData(url);

	}
</script>

<script>
	function payCheckDateData(url) {
		var colIndex = 0;
		$('#payCheckDateReportTabble')
				.DataTable(
						{
							"processing" : true,
							"serverSide" : true,
							"searching" : false,
							"ajax" : $.fn.dataTable.pipeline({
								url : url,
								method : 'POST',
								pages : 10
							}),
							"columns" : [ {
								"data" : "empName"
							}, {
								"data" : "empPayRate"
							}, {
								"data" : "regularHours"
							}, {
								"data" : "overTimeHours"
							}, {
								"data" : "grossPay"
							}, {
								"data" : "preTaxDeduction"
							}, {
								"data" : "postTaxDeduction"
							}, {
								"data" : "taxes"
							}, {
								"data" : "netpay"
							}, {
								"data" : "lastPay"
							}, {
								"data" : "YTD"
							}, {
								"data" : "action"
							}],
							"columnDefs" : [
									{
										"targets" : colIndex++,
										"data" : "empName",
										"sortable": false,
										"render" : function(data, type, opt,meta) {
											var result = "";
											result = result
													+ '<td><div class="tblminwidth-75">'+ opt.empName+ '</div></td>';
											return result;
										}
									},
									{
										"targets" : colIndex++,
										"data" : "empPayRate",
										"render" : function(data, type, opt,meta) {
											var result = "";
											result = result
													+ ' <td><div class="tblminwidth-75"><i class="fa fa-dollar"style="color:#454545;"></i> '+ opt.empPayRate+ '</div></td>';
											return result;

										}
									},
									{
										"targets" : colIndex++,
										"data" : "regularHours",
										"render" : function(data, type, opt,meta) {
											var result = "";
											result = result
													+ '<td class="tblminwidth-75"><div>'+ opt.regularHours+ '</div></td>';
											return result;
										}
									},
									{
										"targets" : colIndex++,
										"data" : "overtimeHours",
										"render" : function(data, type, opt,meta) {
											var result = "";
											result = result
													+ '<td><div class="tbldata-dotdot-vsm" title="'+opt.overTimeHours+'">'+ opt.overTimeHours+ '</div></td>';
											return result;
										}
									},
									{
										"targets" : colIndex++,
										"data" : "grossPay",
										"render" : function(data, type, opt,meta) {
											var result = "";
												result = result + ' <td><div class="tbldata-dotdot-sm"><i class="fa fa-dollar"style="color:#454545;"></i> '+ opt.grossPay+ '</div></td>';
											return result;
										}
									},
									{
										"targets" : colIndex++,
										"data" : "preTaxDeduction",
										"render" : function(data, type, opt,meta) {
											var result = "";
											result = result+ '<td><div class="tblminwidth-50"><i class="fa fa-dollar"style="color:#454545;"></i> '+ opt.preTaxDeduction+ '</div></td>';
											return result;
										}
									},
									{
										"targets" : colIndex++,
										"data" : "postTaxDeduction",
										"render" : function(data, type, opt,meta) {
											var result = "";
											result = result+ ' <td><div class="tblminwidth-125 text-right"><i class="fa fa-dollar"style="color:#454545;"></i> '+ opt.postTaxDeduction+ '</div></td>';
											return result;
										}
									},
									{
										"targets" : colIndex++,
										"data" : "taxes",
										"render" : function(data, type, opt,meta) {
											var result = "";
											result = result+ '<td><div class="tblminwidth-75"><i class="fa fa-dollar"style="color:#454545;"></i> '+ opt.taxes + '</div></td>';
											return result;
										}
									},
									{
										"targets" : colIndex++,
										"data" : "netpay",
										"sortable": false,
										"render" : function(data, type, opt,meta) {
											var result = "";
											result = result+ '<td><div class="tblminwidth-75"><i class="fa fa-dollar"style="color:#454545;"></i> '+ opt.netPay + '</div></td>';
											return result;
										}
									},
									{
										"targets" : colIndex++,
										"data" : "lastPay",
										"sortable": false,
										"render" : function(data, type, opt,meta) {
											var result = "";
											result = result+ ' <td><div class="tblminwidth-75"><i class="fa fa-dollar"style="color:#454545;"></i> xxxx</div></td>';
											return result;
										}
									},
									{
										"targets" : colIndex++,
										"data" : "YTD",
										"sortable": false,
										"render" : function(data, type, opt,meta) {
											var result = "";
											result = result+ '<td><div class="tblminwidth-150"><i class="fa fa-dollar"style="color:#454545;"></i> '+opt.ytdNet +'</div></td>';
											return result;
										}
									},
									{
										"targets" : colIndex++,
										"data" : "action",
										"sortable": false,
										"render" : function(data, type, opt, meta) {
											var result = "";
											result = result + '<td>';
											result = result + '<div class="tblminwidth-30 text-center">';
											result = result + '<a href="javascript:void(0)" class="anchorBlack" onclick="payAdjustment(\''+opt.payrollUUID+'\',\'APPROVED\');"><i class="fa fa-eye"></i></a>';
											result = result + '</div>';
											result = result + '</td>';
											return result;
										}
									} ],
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
		$("#payCheckDateReportTabble").wrap(
				"<div style='overflow-x: auto; overflow-y: hidden;'></div>");
		$("input[type=search]").hide();
		$("#dataTableSearch").keyup(function(e) {
			if (e.which === 13) {
				getPayCheckDateReports();
			}
		});
		
	}
	function payCheckDateSearch(){
		getPayCheckDateReports();
	}
	

</script>
<jsp:include page="./viewPayRollSummary.jsp" /> 
 
<jsp:include page="../masterFooter.jsp" />