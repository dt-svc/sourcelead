<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script src="/resources/js/payrollReports.js"></script>

<script>
$(document).ready(function(){
	associateWithHolding();
});
</script>

<!-- Content Area -->
  <section class="content-wraper contentWraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Associate with Holding</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Payhead List &raquo; Associate with Holding</div>
    </section>
    <div class="contentareaInner-styles clearfix">
  <div class="timesheet_out" id="payrollRegister" style="display: block;">
      <div class="clearfix">
        
        <div class="positionRelative reportHead clearfix"> <span class="pull-left refreIcon"><a href="javascript:void(0)" class="refeIcon" title="Refresh" onclick="resetFilter();"></a></span>
  <ul class="pull-right list-unstyled list-inline">
    <li><a class="newAdBtn mr5 hidden" href="/app/userCreation">+  Associate</a></li>
    <li><a href="javascript:void(0)" class="filtIcon" title="Filters" onclick="openPop();"></a></li>
    <li><a href="javascript:void(0)" class="overIcon" title="Actions" onclick="newActions();"></a>
      <div class="newActPop" style="">
       <span><a href="javascript:void(0)" onclick="exportToPdf();" title="PDF">Export PDF</a> <a href="javascript:void(0)" onclick="exportUserData();" title="Excel">Export Excel</a> </span> </div>
    </li>
    <li><input type='text' placeholder='Search' class='repSearchBar' id='dataTableSearch'/>
		<span class='repSearchIcon' onClick='associateWithHoldingSearch();'></span></li>
  </ul>
</div>
        <div class="require_wrap_table tableFirstSecond clearfix">
        <div id="associateWithHoldingDIV" class="datatable_filter"></div>
         <!--  <table>
            <thead>
              <tr>
                <th>Associate ID</th>
                <th>Name</th>
                <th>SSN</th>
                <th>Work Location</th>
                <th>Federal Allowances</th>
                <th>State</th>
                <th>State Filling Status</th>
                <th>State Allowances</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td><div class="tblminwidth-100">001543245</div></td>
                <td><div class="tbldata-dotdot">Bharath Kumar Sahoo</div></td>
                <td><div class="tblminwidth-100">SSN</div></td>
                <td><div class="tbldata-dotdot">Dalls, United States of America</div></td>
                <td><div class="tblminwidth-100">$ 30000</div></td>
                <td><div class="tblminwidth-100">Dallas</div></td>
                <td><div class="tbldata-dotdot">State Filling Status</div></td>
                <td><div class="tblminwidth-100">$ 20000</div></td>
              </tr>
            </tbody>
          </table> -->
        </div>
       <!--  <nav>
          <ul id="paging" class="pagination">
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
      <!-- Content Area Ends--> 
    </div>
  </section>
  
  
  <script>
function associateWithHolding(){
		var searchValue = $("#dataTableSearch").val();
		searchValue = searchValue ? searchValue : "";
		var url = sourceleadUrl+"/getAssociateWithHoldingReports?searchValue="+searchValue;
		associateWithHoldingList(url);	
}

</script>


<script>
	function associateWithHoldingList(url) {
		$("#associateWithHoldingDIV").empty();
		var result = "";
		result = result + '<table id="aasociatewithHoldingTabble" class="display" cellspacing="0" width="100%" >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result + '<th>Associate ID</th>';
		result = result + '<th>Name</th>';
		result = result + '<th>SSN</th>';
		result = result + '<th>Work Location</th>';
		result = result + '<th>Federal Filling Status</th>';
		result = result + '<th>Federal Allowances</th>';
		result = result + '<th>State</th>';
		result = result + '<th>State Filling Status</th>';
		result = result + '<th>State Allowances</th>';
		result = result + '</tr>';
		result = result + '</thead>	';
		result = result + "</table>";
		$("#associateWithHoldingDIV").append(result);
		$("#noOfChecks").text(0);
		$("#liveChecks").text(0);
		associateWithHoldingData(url);

	}
</script>

<script>
	function associateWithHoldingData(url) {
		var colIndex = 0;
		$('#aasociatewithHoldingTabble')
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
								"data" : "employeeId"
							}, {
								"data" : "empName"
							}, {
								"data" : "ssnNumber"
							}, {
								"data" : "workLocation"
							}, {
								"data" : "federalFillingStatus"
							}, {
								"data" : "federalTaxAllowances"
							}, {
								"data" : "residenceStateCode"
							}, {
								"data" : "stateFillingStatus"
							}, {
								"data" : "stateTaxAllowances"
							}],
							"columnDefs" : [
									{
										"targets" : colIndex++,
										"data" : "employeeId",
										"sortable": false,
										"render" : function(data, type, opt,meta) {
											var result = "";
										 	if(opt.employeeId != null && opt.employeeId != '' && opt.employeeId != 'undefined'){
												result = result+ ' <td><div class="tblminwidth-75">'+opt.employeeId+'</div></td>';
											}else{
												result = result+ ' <td><div class="tblminwidth-75">--</div></td>';
											}
											return result;
										}
									},
									{
										"targets" : colIndex++,
										"data" : "empName",
										"sortable": false,
										"render" : function(data, type, opt,meta) {
											var result = "";
											 if(opt.lastName != null && opt.lastName != '' && opt.lastName != 'undefined'){
												result = result+ '<td><div class="tblminwidth-75">'+opt.firstName+' '+opt.lastName+'</div></td>';
											}else{
												result = result+ '<td><div class="tblminwidth-75">'+opt.firstName+'</div></td>';
											} 
											
											return result;

										}
									},
									{
										"targets" : colIndex++,
										"data" : "ssnNumber",
										"sortable": false,
										"render" : function(data, type, opt,meta) {
											var result = "";
											 if(opt.ssnNumber != null && opt.ssnNumber != '' && opt.ssnNumber != 'undefined'){
												result = result + '<td class="tblminwidth-75"><div>'+opt.ssnNumber+'</div></td>';
											}else{
												result = result + '<td class="tblminwidth-75"><div>--</div></td>';
											} 
											
											return result;
										}
									},{
										"targets" : colIndex++,
										"data" : "workLocation",
										"render" : function(data, type, opt,meta) {
											var result = "";
											if(opt.workLocation != null && opt.workLocation != ''){
												result = result+ '<td><div class="tbldata-dotdot-vsm" title="">'+opt.workLocation+'</div></td>'; 
											}else{
												result = result+ '<td><div class="tbldata-dotdot-vsm" title="">--</div></td>'; 
											}
											return result;
										}
									},{
										"targets" : colIndex++,
										"data" : "federalFillingStatus",
										"render" : function(data, type, opt,meta) {
											var result = "";
											if(opt.federalFillingStatus != null && opt.federalFillingStatus != ''){
											result = result+ '<td><div class="tbldata-dotdot-vsm" title="">'+opt.federalFillingStatus+'</div></td>'; 
											}else{
												result = result+ '<td><div class="tbldata-dotdot-vsm" title="">--</div></td>';
											}
											return result;
										}
									},{
										"targets" : colIndex++,
										"data" : "federalTaxAllowances",
										"render" : function(data, type, opt,meta) {
											var result = "";
											if(opt.federalTaxAllowances != null && opt.federalTaxAllowances != ''){
												 result = result+ '<td><div class="tbldata-dotdot-vsm" title="">'+opt.federalTaxAllowances+'</div></td>'; 
											}else{
												 result = result+ '<td><div class="tbldata-dotdot-vsm" title="">--</div></td>'; 
											}
											return result;
										}
									},{
										"targets" : colIndex++,
										"data" : "residenceStateCode",
										"render" : function(data, type, opt,meta) {
											var result = "";
											if(opt.residenceStateCode != null && opt.residenceStateCode != ''){
												result = result+ '<td><div class="tbldata-dotdot-vsm" title="">'+opt.residenceStateCode+'</div></td>';
											}else{
												result = result+ '<td><div class="tbldata-dotdot-vsm" title="">--</div></td>';
											}
											  
											return result;
										}
									},{
										"targets" : colIndex++,
										"data" : "stateFillingStatus",
										"render" : function(data, type, opt,meta) {
											var result = "";
											if(opt.stateFillingStatus != null && opt.stateFillingStatus != ''){
												result = result+ '<td><div class="tbldata-dotdot-vsm" title="">'+opt.stateFillingStatus+'</div></td>'; 
											}else{
												result = result+ '<td><div class="tbldata-dotdot-vsm" title="">--</div></td>'; 
											}
											 
											return result;
										}
									},{
										"targets" : colIndex++,
										"data" : "stateTaxAllowances",
										"render" : function(data, type, opt,meta) {
											var result = "";
											if(opt.stateTaxAllowances != null && opt.stateTaxAllowances != ''){
												result = result+ '<td><div class="tbldata-dotdot-vsm" title="">'+opt.stateTaxAllowances+'</div></td>';		
											}else{
												result = result+ '<td><div class="tbldata-dotdot-vsm" title="">--</div></td>';
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
		$("#aasociatewithHoldingTabble").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>");
		$("input[type=search]").hide();
		$("#dataTableSearch").keyup(function(e) {
			if (e.which === 13) {
				associateWithHolding();
			}
		});
		
	}
	function associateWithHoldingSearch(){
		associateWithHolding();
	}
	
</script>
  

<jsp:include page="../masterFooter.jsp" />