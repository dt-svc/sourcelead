<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script>
$(document).ready(function(){
	getPaySheetFrequency();
});
</script>


<!-- Content Area -->
  <section class="content-wraper contentWraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Payroll List</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Payroll List</div>
    </section>
    
				<div class="col-md-2">
         			<a href="<c:url value='${appPath}/runPayroll'/>" class="btn btn-success btnSuccess btn-sm anchorWhite pull-right mt10">Run Payroll</a>
         	</div>
    
    	<div class='reportHead'>
		<ul class='pull-right'>
		<li><input type='text' placeholder='Search' class='repSearchBar' id='dataTableSearch'/><span class='repSearchIcon' onClick=''></span></li>
		</ul>
		</div>
	<div class="datatable_filter">
                <div id="payrollFrequencyDiv"></div>
    </div>
    <div class="clearfix"></div>
    <%-- <div class="timesheet_out">
      <div class="tablelist-wrap">
        <div class="timesheet_head minHeight">
          <div class="col-md-7 mt">
            <div class="row">
              <div class="col-md-3">
                <div class="dropdown-select">
                  <select>
                    <option selected>Pay Period</option>
                    <option>Group-1</option>
                    <option>Group-2</option>
                    <option>Group-3</option>
                  </select>
                </div>
              </div>
              <div class="col-md-3 paddingRight">
                <input name="" class="form-control calendar" placeholder="Pay Day" type="text" id="payDay">
              </div>
            </div>
          </div>
				<div class="col-md-2">
         			<a href="<c:url value='${appPath}/runPayroll'/>" class="btn btn-success btnSuccess btn-sm anchorWhite pull-right mt10">Run Payroll</a>
         	</div>
         
          <div class="clearfix"></div>
          <div class="remove_search">
            <input placeholder="Search" type="text">
          </div>
        </div>
          <div class="styleTables clearfix mt">
            <table>
              <thead>
                <tr>
                  <th>Pay Group</th>
                  <th>Pay Date</th>
                  <th>Type</th>
                  <th>Pay Period</th>
                  <th>Total</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td><div class="tbldata-dotdot-vsm"><a href="payrolllist-summary-us.html">Weekly</a></div></td>
                  <td><div class="tblminwidth-150">01/02/2018</div></td>
                  <td><div class="tblminwidth-150 clearfix">Regular</div></td>
                  <td>
					  <span class="tblminwidth-100 mr">
							25/12/2017                 	
					  </span>
					  <span class="tblminwidth-100">
							25/02/2018                 	
					  </span>
                  </td>
                  <td><div class="tblminwidth-150 clearfix">40,000 $</div></td>
                </tr>
                 <tr>
                  <td><div class="tbldata-dotdot-vsm"><a href="payrolllist-summary-us.html">Monthly</a></div></td>
                  <td><div class="tblminwidth-150">01/02/2018</div></td>
                  <td><div class="tblminwidth-150 clearfix">Regular</div></td>
                  <td>
					  <span class="tblminwidth-100 mr">
							25/12/2017                 	
					  </span>
					  <span class="tblminwidth-100">
							25/02/2018                 	
					  </span>
                  </td>
                  <td><div class="tblminwidth-150 clearfix">60,000 $</div></td>
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
            <li data-pagse="last"><a href="#">Next</a></li>
          </ul>
          <div class="clearfix"></div>
        </nav>
      </div>
    </div> --%>
  </section>
  <!-- Content Area Ends--> 
  
  
 <script>
	function getPaySheetFrequency() {
		var sourceleadUrl = '${sourceleadUrl}';
		var searchValue = $("#dataTableSearch").val();
		searchValue = searchValue ? searchValue : "";
		var url = sourceleadUrl+ "/getPaySheetFrequencyData?searchValue="+searchValue;
		preparePaySheetFrequency(url);
	}
</script>

<script>
	function preparePaySheetFrequency(url) {
	 
		var result = "";
		result = result + '<table id="payFrequency" class="display" cellspacing="0" width="100%" >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result + '<th>Pay Group</th>';
		result = result + '<th>Pay Date</th>';
		result = result + '<th>Type</th>';
		result = result + '<th>Pay Period</th>';
		result = result + '<th>Total</th>';
		result = result + '</tr>';
		result = result + '</thead>	';
		result = result + "</table>";
		$("#payrollFrequencyDiv").empty();
		$("#payrollFrequencyDiv").append(result);
		payFrequencyPagination(url);

	}
</script>

<script>
	function payFrequencyPagination(url) {
		var sourceleadUrl = '${sourceleadUrl}';
		var colIndex = 0;
		$('#payFrequency').DataTable({
							"processing" : true,
							"serverSide" : true,
							"searching" : false,
							"ajax" : $.fn.dataTable.pipeline({
								url : url,
								method : 'GET',
								pages : 10
							}),
							
							"columns" : [ {
								"data" : "payGroup"
							}, {
								"data" : "payDate"
							}, {
								"data" : "type"
							}, {
								"data" : "payPeriod"
							}, {
								"data" : "total"
							} ],
							
							
							 "columnDefs" : [
									{
										"targets" : colIndex++,
										"data" : "payGroup",
										"render" : function(data, type, opt,meta) {
											var result = "";
											if(opt.payFrequency != null && opt.payFrequency != '' && opt.payFrequency !='undefined'){
						                		  result = result +'<div class="tbldata-dotdot-vsm"><a href="'+sourceleadUrl+'/viewPaySheetFrequencyData?paySheetFrequencyUUID='+opt.paySheetFrequencyuuid+'" title="'+opt.payFrequency+'">'+opt.payFrequency+'</a></div>';
											}else{
						              		  result = result +'<div class="tbldata-dotdot-vsm">--</div>';
						              		}
											return result;
										}
									},
									{
										"targets" : colIndex++,
										"data" : "payDate",
										"render" : function(data, type, opt,meta) {
											var result = "";
											if(opt.payDate != null && opt.payDate != '' && opt.payDate !='undefined'){
						                		  result = result +'<div class="tbldata-dotdot-vsm">'+opt.payDate+'</div>';
						              		}else{
						              		  result = result +'<div class="tbldata-dotdot-vsm">--</div>';
						              		}
											return result;

										}
									},
									{
										"targets" : colIndex++,
										"data" : "type",
										"render" : function(data, type, opt,meta) {
											var result = "";
											if(opt.paySheetFrequencyType != null && opt.paySheetFrequencyType != '' && opt.paySheetFrequencyType !='undefined'){
						                		  result = result +'<div class="tbldata-dotdot-vsm">'+opt.paySheetFrequencyType+'</div>';
						              		}else{
						              		  result = result +'<div class="tbldata-dotdot-vsm">--</div>';
						              		}
											return result;
										}
									},
									{
										"targets" : colIndex++,
										"data" : "payPeriod",
										"render" : function(data, type, opt,meta) {
											var result = "";
											if(opt.payPeriod != null && opt.payPeriod != '' && opt.payPeriod !='undefined'){
						                		  result = result +'<div class="tbldata-dotdot-vsm">'+opt.payPeriod+'</div>';
						              		}else{
						              		  result = result +'<div class="tbldata-dotdot-vsm">--</div>';
						              		}
											return result;
										}
									},
									{
										"targets" : colIndex++,
										"data" : "total",
										"render" : function(data, type, opt,meta) {
											var result = "";
											if(opt.total != null && opt.total != '' && opt.total !='undefined'){
						                		  result = result +'<div class="tbldata-dotdot-vsm">'+opt.total.toFixed(2)+'</div>';
						              		}else{
						              		  result = result +'<div class="tbldata-dotdot-vsm">--</div>';
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
		$("#payFrequency").wrap(
				"<div style='overflow-x: auto; overflow-y: hidden;'></div>");
		$("input[type=search]").hide();
		$("#dataTableSearch").keyup(function(e) {
			if (e.which === 13) {
				getPaySheetFrequency();
			}
		});
		
	}
</script>
  
<jsp:include page="../masterFooter.jsp" />