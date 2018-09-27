<jsp:include page="../../masterHeader.jsp" />
<jsp:include page="../../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- Content Area -->
<section class="content-wraper">
<section class="content-header clearfix">
<div class="pull-left">
<h2>Run Payroll</h2>
</div>
   <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo;Run Payroll</div>
  </section>
		<div class="success_pop" id="selector1" style="display: none;">
 			<i class="fa fa-check-circle"></i><span id="msgDiv"></span>
       </div>
  <div class="project_wrap">
   <div id="tabs-menu" class="clearfix project-tabs-menu">
	<ul class="nav nav-tabs">
		<li id="initiatePayrollLi" class=""><a onclick="initiatePayrollDiv();highlight('initiatePayrollLi');" title="Initiate Payroll">Initiate Payroll</a></li>
		<li id="payrollLi"  class=""><a onclick="payrollDiv(); highlight('payrollLi');" title="Payroll">Payroll</a></li>
		<li id="groupsLi"  class=""><a onclick="groupsDiv(); highlight('groupsLi');" title="Groups">Groups</a></li>
		<li id="formsLi"  class=""><a onclick="formsDiv(); highlight('formsLi');" title="Forms">Forms</a></li>
	</ul>
   </div>
   </div>	
   <div class="clearfix"></div>
   <div class='data_loading' id="loadTime"></div>
   <div id="runPayRollTabsContent"></div>
   <div id="payrollTabsContent"></div>
   <div id="groupsTabsContent"></div>
   <div id="formsTabsContent"></div>
</section>

<div class="comMask"></div>
<div class="modal netPay" style="display: none;" id="netPay">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"  onclick="closeNetPay();">&times;</button>
	     <h4 class="modal-title">Employee Net Pay</h4>
	 </div>
	<div class="modal-body" style="min-height: 150px; max-height: 470px; overflow-y: auto;" id="associateSalaryIndDiv">
		<!--  container  -->
	</div>
			<div class="modal-footer">
        <button type="button" class="btn btn-default" onclick="closeNetPay();">Close</button>
      </div></div>
	 </div>
</div>

 
<script>
	$(document).ready(function() {
		initiatePayrollDiv();
		highlight('initiatePayrollLi');
	});
</script> 
 <script>
	function initiatePayrollDiv(){ 
	               var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
	               $("#loadTime").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:60% center; background-repeat:no-repeat; background-size:75px;');
	        	   $.ajax({
		      	          type : "POST",
	        	      	  url: sourceleadUrl+"/initiateIndPayroll",		
	        		      success :function(data) {
	        		    	  $("#runPayRollTabsContent").show();
	        		    	  $("#runPayRollTabsContent").empty();
	        		    	  $("#runPayRollTabsContent").append(data);
	        		    	  $("#loadTime").removeAttr('style');
	        		    	  $("#groupsTabsContent").hide();
	        		    	  $("#formsTabsContent").hide();
	        		    	  prepareChosenScripts();
	        		      }
	        	    }); 
	}
</script>

<script>
function payrollDiv(){ 
	    var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
	    $("#loadTime").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:60% center; background-repeat:no-repeat; background-size:75px;');
	    $("#runPayRollTabsContent").empty();
	    $("#loadTime").removeAttr('style');
		preparePaySheetFrequencyInd();
  }
  
  function preparePaySheetFrequencyInd() {
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
		var url = sourceleadUrl+ "/getPaySheetFrequencyDataInd?searchValue="+searchValue;
		
		payFrequencyPagination(url);
		
		/* $("#overViewDiv").hide();
		$("#draftFrequencyDiv").hide();
		$("#approveDiv").hide();
		$("#userGroupDiv").hide();
		$("#searchDivForEmployee").hide();
		$("#historySearchIndDiv").show();
		$("#historyDataTableSearch").show();
		$("#payrollFrequencyDiv").show(); */

	}
  
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
								"data" : "Pay run id"
							}, {
								"data" : "Group Name"
							}, {
								"data" : "Pay Period"
							}, {
								"data" : "Pay Date"
							},{
								"data" : "Type"
							},{
								"data" : "total"
							}, {
								"data" : "status"
							}],
							
							
							 "columnDefs" : [
									{
										"targets" : colIndex++,
										"data" : "Pay run id",
										"render" : function(data, type, opt,meta) {
											var result = "";
											if(opt.payPeriodNum != null && opt.payPeriodNum != '' && opt.payPeriodNum !='undefined'){
						                		  result = result +'<div class="tblminwidth-100"><a href="'+sourceleadUrl+'/viewPaySheetFrequencyData?approverStatus='+opt.approverStatus+'&paySheetFrequencyUUID='+opt.paySheetFrequencyuuid+'&payPeriodNum='+opt.payPeriodNum+'">'+opt.payPeriodNum+'</a></div>';
											}else{
						              		  result = result +'<div class="tblminwidth-100">--</div>';
						              		}
											return result;
										}
									},{
										"targets" : colIndex++,
										"data" : "Group Name",
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
										"data" : "Pay Period",
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
										"data" : "Pay Date",
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
										"data" : "Type",
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
										"data" : "Total",
										"render" : function(data, type, opt,meta) {
											var result = "";
											if(opt.total != null && opt.total != '' && opt.total !='undefined'){
						                		  result = result +'<div class="tbldata-dotdot-vsm">$ '+opt.total.toFixed(2)+'</div>';
						              		}else{
						              		  result = result +'<div class="tbldata-dotdot-vsm">$ 0.0</div>';
						              		}
											return result;
										}
										
									},{
										"targets" : colIndex++,
										"data" : "Status",
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
		$("#historyDataTableSearch").keyup(function(e) {
			if (e.which === 13) {
				payrollDiv();
			}
		});
		
	}
  
  </script>



 <script>
	function groupsDiv(){ 
	               var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
	               $("#loadTime").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:60% center; background-repeat:no-repeat; background-size:75px;');
	               $("#runPayRollTabsContent").empty();
	               $("#loadTime").removeAttr('style');
	               $.ajax({
	                   type : "POST",
	         	      	  url: sourceleadUrl+"/indianPayrolluserGroupList",		
	         		      success :function(data) {
	         		    	  $("#runPayRollTabsContent").hide();
	         		    	  $("#payrollTabsContent").hide();
	         		    	  $("#formsTabsContent").hide();
	         		    	  $("#groupsTabsContent").show();
	         		    	  
	         		    	  $("#groupsTabsContent").empty();
	         		    	  $("#groupsTabsContent").append(data);
	         		      }
	         		});
	         	}
</script>
 <script>
	function formsDiv(){ 
       var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
       $("#loadTime").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:60% center; background-repeat:no-repeat; background-size:75px;');
       $("#runPayRollTabsContent").empty();
       $("#loadTime").removeAttr('style');
       $.ajax({
          type : "POST",
	      	  url: sourceleadUrl+"/forms/list",		
		      success :function(data) {
		    	  $("#runPayRollTabsContent").hide();
		    	  $("#payrollTabsContent").hide();
		    	  $("#groupsTabsContent").hide();
		    	  $("#formsTabsContent").show();
		    	  
		    	  $("#formsTabsContent").empty();
		    	  $("#formsTabsContent").append(data);
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
$(document).ready(function() {
	$("#runPay").click(function(){
		$(".popMask").fadeIn(200);
		$("#popPassView").fadeIn(100);
	});		
	$(".popCan").click(function(){
		$(".popMask").fadeOut(200);
		$("#popPassView").fadeOut(100);
	});
});
	
function netPay(){
	$("#netPay").fadeIn();
	$(".comMask").fadeIn();
	var candidateId = "";
	$.ajax({
        type : "GET",
        url: sourceleadUrl+"/calculateAssociateSalaryInd?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId,    
         success :function(result) { 
        	 
        	 $("#associateSalaryIndDiv").empty(); 
             $("#associateSalaryIndDiv").append(result);
         },
    }); 
};

function closeNetPay(){
	$("#netPay").fadeOut();
	$(".comMask").fadeOut();
};
//Net payedit
function editNetpay(){
	$("#tableNetpay").find(".formControl").toggleClass("activeEdit");
	var x = document.getElementsByClassName("formControl"), i;
	for (i=0; i<x.length; i++) {
		if(x[i].readOnly) {
			x[i].readOnly = false;
		}else {
			x[i].readOnly = true;
		}
	}
	$(".editNetpay").hide();
	$(".saveNetpay").show();
}
function saveNetpay(){
	$("#tableNetpay").find(".formControl").toggleClass("activeEdit");
	var x = document.getElementsByClassName("formControl"), i;
	for (i=0; i<x.length; i++) {
		if(x[i].readOnly) {
			x[i].readOnly = false;
		}else {
			x[i].readOnly = true;
		}
	}
	$(".editNetpay").show();
	$(".saveNetpay").hide();
}
function confirmPayroll() {
	$(".confirmPayroll, .comMask").fadeIn();
}
function closeCPR() {
	$(".confirmPayroll, .comMask").fadeOut();
}
</script>
<jsp:include page="../../masterFooter.jsp" />