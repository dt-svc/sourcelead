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
  <section class="content-wraper">
    <section class="content-header clearfix ">
      <div class="pull-left">
        <h2>Payroll List</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Payroll List</div>
    </section>
    
				<div class="col-md-2 col-md-offset-7">
         			<a href="<c:url value='${appPath}/runPayroll'/>" class="btn btn-success btnSuccess btn-sm anchorWhite pull-right mt10">Initiate Payroll</a>
         	</div>
    
    	<div class='reportHead'>
		<ul class='pull-right'>
		<li><input type='text' placeholder='Search' class='repSearchBar' id='dataTableSearch'/><span class='repSearchIcon' onClick=''></span></li>
		</ul>
		</div>
	<div class="candidate_wrap_table datatable_filter backgroundWhite">
                <div id="payrollFrequencyDiv"></div>
    </div>
    <div class="clearfix"></div>
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
		result = result + '<th>Pay Period</th>';
		result = result + '<th>Total</th>';
		result = result + '<th>Status</th>';
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
								"data" : "payPeriod"
							}, {
								"data" : "total"
							}, {
								"data" : "status"
							}],
							
							
							 "columnDefs" : [
									{
										"targets" : colIndex++,
										"data" : "payGroup",
										"render" : function(data, type, opt,meta) {
											var result = "";
											if(opt.payFrequency != null && opt.payFrequency != '' && opt.payFrequency !='undefined'){
						                		  result = result +'<div class="tblminwidth-100"><a href="'+sourceleadUrl+'/viewPaySheetFrequencyData?approverStatus='+opt.approverStatus+'&paySheetFrequencyUUID='+opt.paySheetFrequencyuuid+'" title="'+opt.payFrequency+'">'+opt.payFrequency+'</a></div>';
											}else{
						              		  result = result +'<div class="tblminwidth-100">--</div>';
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
						                		  result = result +'<div class="tblminwidth-100">'+opt.payDate+'</div>';
						              		}else{
						              		  result = result +'<div class="tblminwidth-100">--</div>';
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
						                		  result = result +'<div class="tblminwidth-100">'+opt.payPeriod+'</div>';
						              		}else{
						              		  result = result +'<div class="tblminwidth-100">--</div>';
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
										
									},{
										"targets" : colIndex++,
										"data" : "status",
										"render" : function(data, type, opt,meta) {
											var result = "";
											if(opt.approverStatus != null && opt.approverStatus != '' && opt.approverStatus !='undefined'){
						                		  result = result +'<div class="tblminwidth-100">'+opt.approverStatus+'</div>';
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
		$("#payFrequency").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>");
		$("input[type=search]").hide();
		$("#dataTableSearch").keyup(function(e) {
			if (e.which === 13) {
				getPaySheetFrequency();
			}
		});
		
	}
</script>
  
<jsp:include page="../masterFooter.jsp" />