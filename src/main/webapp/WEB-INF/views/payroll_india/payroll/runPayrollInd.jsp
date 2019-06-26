<jsp:include page="../../masterHeader.jsp" />
<jsp:include page="../../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- Content Area -->
<section class="content-wraper contentWraper">
  <section class="content-header clearfix">
	<div class="pull-left">
	<h2>Org Payroll</h2>
	</div>
	   <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo; Org Payroll</div>
  </section>
		<div class="success_pop" id="selector1" style="display: none;">
 			<i class="fa fa-check-circle"></i><span id="msgDiv"></span>
       </div>
  <div class="project_wrap">
   <div id="tabs-menu" class="clearfix project-tabs-menu">
	<ul class="nav nav-tabs newTabs">
		<li id="initiatePayrollLi" class=""><a onclick="initiatePayrollDiv();highlight('initiatePayrollLi');" title="Initiate Payroll">Initiate Payroll</a></li>
		<li id="payrollLi"  class=""><a onclick="payrollDiv();" title="Payroll">Payroll</a></li>
		<li id="groupsLi"  class=""><a onclick="groupsDiv(); highlight('groupsLi');" title="Groups">Groups</a></li>
		<li id="formsLi"  class=""><a onclick="formsDiv(); highlight('formsLi');" title="Forms">Forms</a></li>
	</ul>
   </div>
   
   <div class="clearfix"></div>
   <div class='data_loading' id="loadTime"></div>
   <div id="runPayRollTabsContent" class="require_wrap_table datatable_filter"></div>
   </div>
</section>

<script>
	$(document).ready(function() {
		var stab = '${stab}';
		var redirectionTab = '${redirectionTab}';
		if(stab != null && stab != '' && stab != 'undefined' && stab == 'payroll'){
			payrollDiv();
		}else if ((stab != null && stab != '' && stab != 'undefined' && stab == 'forms') || (redirectionTab == 'forms' && redirectionTab != null)){
			formsDiv();
			highlight('formsLi');
		}else{
			initiatePayrollDiv();
			highlight('initiatePayrollLi');
		}
	});
</script> 

<script>
function employeePayrollList(uuid){
	var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
    /* $("#loadTime").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:60% center; background-repeat:no-repeat; background-size:75px;');
	   $.ajax({
	          type : "POST",
	      	  url: sourceleadUrl+"/employeePayrollList",		
		      success :function(data) {
		    	  $("#runPayRollTabsContent").empty();
		  		  $("#runPayRollTabsContent").append(data);
		    	  $("#loadTime").removeAttr('style');
		      }
	    }); */
	   
	   
	   $(location).attr('href', 'employeePayrollList?paySheetFrequencyUUID='+uuid+'');
}
</script>

 <script>
	function initiatePayrollDiv(){
		           var pmsUrl ='${SourceLeadWeb_pmsUrl}';
	               $("#loadTime").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:60% center; background-repeat:no-repeat; background-size:75px;');
	        	   $.ajax({
		      	          type : "POST",
	        	      	  url: pmsUrl+"/initiateIndPayroll",		
	        		      success :function(data) {
	        		    	  $("#runPayRollTabsContent").empty();
	        		  		  $("#runPayRollTabsContent").append(data);
	        		    	  $("#loadTime").removeAttr('style');
	        		    	  prepareChosenScripts();
	        		      }
	        	    }); 
	}
</script>

<script>
function payrollDiv(){
	  
	    $("#loadTime").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:60% center; background-repeat:no-repeat; background-size:75px;');
	    $("#runPayRollTabsContent").empty();
	    $("#loadTime").removeAttr('style');
		preparePaySheetFrequencyInd();
		highlight('payrollLi');
  }
  
  function preparePaySheetFrequencyInd() {
	  var pmsUrl ='${SourceLeadWeb_pmsUrl}';
		var result = "";
		result = result + '<div class="reportHead" id="historySearchIndDiv">';
		result = result + '	<ul class="pull-right">';
		result = result + '	<li><input type="text" placeholder="Search" class="repSearchBar" id="historyDataTableSearchInd"/><span class="repSearchIcon" onClick=""></span></li>';
		result = result + '	</ul>';
		result = result + '</div>';
		result = result + '<table id="payFrequency" class="display" cellspacing="0" width="100%" >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result + '<th>Pay run id</th>';
		result = result + '<th>Group Name</th>';
		result = result + '<th>Pay Period</th>';
		result = result + '<th>Pay Date</th>';
		result = result + '<th>Type</th>';
		result = result + '<th>Total</th>';
		result = result + '<th>Status</th>';
		result = result + '</tr>';
		result = result + '</thead>	';
		result = result + "</table>";
		$("#runPayRollTabsContent").empty();
		$("#runPayRollTabsContent").append(result);
		var searchValue = $("#historyDataTableSearchInd").val();
		searchValue = searchValue ? searchValue : "";
		var url = pmsUrl+ "/getPaySheetFrequencyDataInd?searchValue="+searchValue;
		
		payFrequencyPagination(url);
	}
  
  function payFrequencyPagination(url) {
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
								"data" : "payRunId"
							}, {
								"data" : "groupName"
							}, {
								"data" : "frequencyStartDate"
							}, {
								"data" : "payCheckDate"
							},{
								"data" : "type"
							},{
								"data" : "total"
							}, {
								"data" : "approverStatus"
							}],
							
							
							 "columnDefs" : [
									{
										"targets" : colIndex++,
										"data" : "payRunId",
										"sortable": true,
										"render" : function(data, type, opt,meta) {
											var result = "";
											if(opt.payRunId != null && opt.payRunId != '' && opt.payRunId !='undefined'){
						                		  /* result = result +'<div class="tblminwidth-100" onClick="netPay();"><a>'+opt.payRunId+'</a></div>'; */
												result = result +'<div class="tblminwidth-100" onClick="employeePayrollList(\''+opt.paySheetFrequencyUUID+'\');"><a>'+opt.payRunId+'</a></div>';
											}else{
						              		  result = result +'<div class="tblminwidth-100">--</div>';
						              		}
											return result;
										}
									},{
										"targets" : colIndex++,
										"data" : "groupName",
										"sortable": true,
										"render" : function(data, type, opt,meta) {
											var result = "";
											if(opt.groupName != null && opt.groupName != '' && opt.groupName !='undefined'){
						                		  result = result +'<div class="tblminwidth-100">'+opt.groupName+'</div>';
											}else{
						              		  result = result +'<div class="tblminwidth-100">--</div>';
						              		}
											return result;
										}
									},{
										"targets" : colIndex++,
										"data" : "frequencyStartDate",
										"sortable": true,
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
										"data" : "payCheckDate",
										"sortable": true,
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
										"data" : "type",
										"sortable": false,
										"render" : function(data, type, opt,meta) {
											var result = "";
											if(opt.payPeriod != null && opt.payPeriod != '' && opt.payPeriod !='undefined'){
						                		  result = result +'<div class="tblminwidth-100">Regular</div>';
						              		}else{
						              		  result = result +'<div class="tblminwidth-100">--</div>';
						              		}
											return result;
										}
									},
									{
										"targets" : colIndex++,
										"data" : "total",
										"sortable": false,
										"render" : function(data, type, opt,meta) {
											var result = "";
											if(opt.total != null && opt.total != '' && opt.total !='undefined'){
						                		  result = result +'<div class="tbldata-dotdot-vsm">'+opt.total.toFixed(2)+' <i class="paddingLeft">&#8377;</i></div>';
						              		}else{
						              		  result = result +'<div class="tbldata-dotdot-vsm">0.0 <i class="paddingLeft">&#8377;</i></div>';
						              		}
											return result;
										}
										
									},{
										"targets" : colIndex++,
										"data" : "approverStatus",
										"sortable": true,
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
		$("#historyDataTableSearchInd").keyup(function(e) {
			if (e.which === 13) {
				payrollDiv();
			}
		});
	}
</script>



 <script>
	function groupsDiv(){ 
		           var pmsUrl ='${SourceLeadWeb_pmsUrl}';
	               $("#loadTime").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:60% center; background-repeat:no-repeat; background-size:75px;');
	               $("#runPayRollTabsContent").empty();
	               $("#loadTime").removeAttr('style');
	               $.ajax({
	                   type : "POST",
	         	      	  url: pmsUrl+"/indianPayrolluserGroupList",		
	         		      success :function(data) {
	         		    	 $("#runPayRollTabsContent").empty();
	         				$("#runPayRollTabsContent").append(data);
	         		      }
	         		});
	         	}
</script>
 <script>
	function formsDiv(financialYear){ 
		var pmsUrl ='${SourceLeadWeb_pmsUrl}';
       $("#loadTime").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:60% center; background-repeat:no-repeat; background-size:75px;');
       $("#runPayRollTabsContent").empty();
       $("#loadTime").removeAttr('style');
       $.ajax({
          type : "POST",
	      	  url: pmsUrl+"/forms/list" + (financialYear && financialYear !== "" ? "?financialYear=" + financialYear : ''),		
		      success :function(data) {
		    	$("#runPayRollTabsContent").empty();
		  		$("#runPayRollTabsContent").append(data);
		      }
		});
	}
</script>

<script>
function highlight(id) {
	$("#tabs-menu>ul>li.active").removeClass("active");
    $("#"+id).addClass("active");
} 
</script>


<script>
function tabPick(){
	var stab = '${param.stab}';
	if(stab == 'payroll'){
		payrollDiv();
	}
}
window.onload=tabPick;
</script>

<jsp:include page="../../masterFooter.jsp" />