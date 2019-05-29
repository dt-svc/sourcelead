<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<section class="content-wraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Sales Orders List</h2>
		</div>
		<div class="pull-right brud-crum">
			<a
				href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>
			&raquo; Sales Order List
		</div>
	</section>

	<div class="clearfix">
		<ul class="project-tabs-menu projecttabs-custom clearfix">
			<li id=""><a href="${appPath}/candidatesList">Candidate
					List</a></li>
			<li id=""><a href="javascript:void(0)"
				onclick="javascript:goToOrgMarketPlaceList();">Marketplace</a></li>
			<li id=""><a href="javascript:void(0)"
				onclick="javascript:goToGlobalMarketPlaceList();">Global
					Marketplace</a></li>
			<li id=""><a href="javascript:void(0)"
				onclick="javascript:goToCartList();">Cart</a></li>
			<li id=""><a href="javascript:void(0)"
				onclick="javascript:goToPurchaseOrderList();">Purchase Orderlist</a></li>
			<li id="" class="current"><a href="javascript:void(0)">Sales
					Orderlist</a></li>
			<li id=""><a href="javascript:void(0)"
				onclick="javascript:goToWalletList();">Wallet</a></li>

		</ul>
	</div>

	<div class="clearfix">
		<div class="timesheet_out" id="payrollRegister"
			style="display: block;">
			<div class="clearfix">
				<div class="positionRelative reportHead clearfix">
					<span class="pull-left refreIcon mb10"> <a
						href="javascript:void(0)" class="refeIcon" title="Refresh"
						onclick="refreshPage();"></a>
					</span>
					<ul class="pull-right list-unstyled list-inline mr">
						<li><a href="javascript:void(0)" class="filtIcon"
							title="Filters" onclick="openPop();"></a></li>
						<li><a href="javascript:void(0)" class="overIcon"
							title="Actions" onclick="newActions();"></a>
							<div class="newActPop" style="">
								<span><a href="javascript:void(0)"
									onclick="exportSalesOrderToPdf();" title="PDF">Export PDF</a> <a
									href="javascript:void(0)" onclick="exportSalesOrderToExcel();"
									title="Excel">Export Excel</a> </span>
							</div></li>
						<li class="noMar"><input class="repSearchBar"
							placeholder="Order No" id="searchByValue" type="text" value="">
							<span class="repSearchIcon" onclick="getDynamicSalesOrderList();"></span>

						</li>
					</ul>
				</div>


				<div
					class="require_wrap_table tableFirstSecond clearfix datatable_filter">
					<div id="dynamicSalesOrderList"></div>

				</div>
			</div>
		</div>
	</div>
</section>

<div id="emptyPopup"></div>
<div class="advancedsearch-mask"></div>
<div class="popupWrap" id="modalSalesOrder">
	<div class="popupHead">
		<span>Task Type</span><i class="closePop pull-right popClose"
			onclick="closePop();"></i>
	</div>
	<form id="#" name="" action="" method="post">
		<div class="popupContent">
			<div class="addInner">
				<div class="row">
					<div class="col-md-6 mb positionRelative">
						<div class="clearfix dynamic_search filterDynamicsearch">
							<h5>Payment Status</h5>
						</div>
						<div class="clearfix filterSearch-ul">
							<ul class="list-unstyled positionRelative filterSearch mt5"
								id="salesOrderPaymentStatus">
								<li class="checkBox positionRelative"><input
									name="orgManagers" value="" type="checkbox" class="mt2"
									id="paymentSuccessCheckBox"> <label>Success</label></li>
								<li class="checkBox positionRelative"><input
									name="orgManagers" value="" type="checkbox" class="mt2"
									id="paymentFailureCheckBox"> <label>Failure</label></li>
							</ul>
						</div>
					</div>

					<div class="col-md-6 positionRelative">
						<!-- <div class="clearfix dynamic_search filterDynamicsearch">
            <input id="myRolesInput" onkeyup="myRolesFunction()" placeholder="By Date" title="Type in a roles" type="text">
            <span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span> </div> -->
						<div class="clearfix filterSearch-ul">
							<ul class="list-unstyled positionRelative filterSearch mt5"
								id="salesOrderCheckBox">
								<li class="checkBox positionRelative"><input
									name="dateCheckBox" value="today" type="radio" class="mt2">
									<label>Today</label></li>
								<li class="checkBox positionRelative"><input
									name="dateCheckBox" value="1_week" type="radio" class="mt2">
									<label>Week Ago</label></li>
								<li class="checkBox positionRelative"><input
									name="dateCheckBox" value="1_month" type="radio" class="mt2">
									<label>This Month</label></li>
								<li class="checkBox positionRelative"><input
									name="dateCheckBox" value="3_months" type="radio" class="mt2">
									<label>3 months ago</label></li>
								<li class="checkBox positionRelative"><input
									name="dateCheckBox" value="6_months" type="radio" class="mt2">
									<label>6 Months ago</label></li>
								<li class="checkBox positionRelative"><input
									name="dateCheckBox" value="YTD" type="radio" class="mt2">
									<label>1 Year ago</label></li>
								<li class="checkBox positionRelative"><input
									name="dateCheckBox" value="custom" type="radio"
									id="salesOrderCustomDate" class="mt2"> <label>Custom
										Dates</label></li>
							</ul>
						</div>
					</div>
					<div class="clearfix"></div>
					<div class="clearfix" id="customDates" style="display: none;">
						<div class="col-md-12 positionRelative">
							<div class="clearfix dynamic_search filterDynamicsearch">
								<h5 class="margin-top0">
									Custom Dates<span class="text-danger paddingLeft">*</span>
								</h5>
								<ul class="list-unstyled positionRelative filterSearch mb0">
									<li class="positionRelative">
										<div class="row">
											<div class="col-md-6" id="custFromDateDiv">
												<input type="text" value=""
													class="form-control calendar input-sm"
													field-name="From Date" placeholder="From Date"
													id="salesOrderFromDate">
											</div>
											<div class="col-md-6" id="custToDateDiv">
												<input type="text" value=""
													class="form-control calendar input-sm" field-name="To Date"
													placeholder="To Date" id="salesOrderToDate">
											</div>
										</div>
									</li>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="popupFoot text-right">
			<button type="button" class="slBtn md colorBtn"
				onclick="validateSalesOrderFilter();">Apply</button>
			<button type="button" class="slBtn md"
				onclick="resetSalesOrderFilter()">Reset</button>
		</div>

		<div></div>
	</form>
</div>


<script>
   var table = "";
   var paymentStatus = "";
   $(document).ready(function () {
	   getDynamicSalesOrderList();
	 });
  
   function validateSalesOrderFilter(){ 
	   		   var salesOrderFilterDate = $('input[name=dateCheckBox]:checked').val(); 
	   		   var salesOrderFromDate = $("#salesOrderFromDate").val(); 
	   		   var salesOrderToDate = $("#salesOrderToDate").val(); 
	   		   if(salesOrderFilterDate === "custom"){ 
	   		   if(salesOrderFromDate === '' && salesOrderToDate != null){ 
	   		   $('#custFromDateDiv').find('p.jquery_form_error_message').remove(); 
	   		   $("input#salesOrderFromDate").after( "<p class='jquery_form_error_message'>From Date is required.</span>"); 
	   		   $("input#salesOrderFromDate").empty(); 
	   		   return false; 
	   		   } 
	   		   if(salesOrderToDate === '' && salesOrderFromDate !== ''){ 
	   		   $('#custToDateDiv').find('p.jquery_form_error_message').remove(); 
	   		   $("input#salesOrderToDate").after( "<p class='jquery_form_error_message'>To Date is required.</span>"); 
	   		   $("input#salesOrderToDate").empty(); 
	   		   return false; 
	   		   } 
	   		   if((salesOrderFromDate != '' && salesOrderFromDate != 'undefined' && salesOrderFromDate != null) && 
	   		   (salesOrderToDate != '' && salesOrderToDate != 'undefined' && salesOrderToDate != null)){ 
	   		   $('#custFromDateDiv').find('p.jquery_form_error_message').remove(); 
	   		   $('#custToDateDiv').find('p.jquery_form_error_message').remove(); 
	   		   } 
	   		   } 
	   		   else{
	   		   $('#custFromDateDiv').find('p.jquery_form_error_message').remove(); 
	   		   $('#custToDateDiv').find('p.jquery_form_error_message').remove(); 
	   		   }
	   		   closePop(); 
	   		   getDynamicSalesOrderList(salesOrderFilterDate, salesOrderFromDate, salesOrderToDate);
	   		   } 
	      
	   function getDynamicSalesOrderList(salesOrderFilterDate, salesOrderFromDate, salesOrderToDate){
	  	var result = "";
	  	var searchValue = $("#searchByValue").val();
	  	var url = "";
	  	result = result +"<table id='salesOrderListTable' class='display' cellspacing='0' width='100%'>";
	  	result = result +"<thead>";
	  	result = result +"<tr>"
	  	result = result +"<th>Order No</th>";
	  	result = result +"<th>Order Date</th>";
	  	result = result +"<th>Name of the Company</th>";
	  	result = result +"<th>No. of Resumes</th>";
	  	result = result +"<th>Total Resumes Price</th>";
	  	result = result +"<th>Payment Status</th>";
	  	result = result +"</tr>";
	  	result = result +"</thead>";
	  	result = result +"<table>";
	  	$("#dynamicSalesOrderList").html(result);
  		url = sourceleadUrl + "/getDynamicSalesOrderList?"+tokenParamName+"="+tokenValue+"&searchValue="+searchValue+"&salesOrderFromDate="+salesOrderFromDate+"&salesOrderToDate="+salesOrderToDate+"&salesOrderFilterDate="+salesOrderFilterDate;  
  		dynamicSalesOrderList(url);
  	}
  
   function dynamicSalesOrderList(url){
	   var colIndex = 0;
	    table = $('#salesOrderListTable').DataTable( { 
		  "processing": true,
	      "serverSide": true,
	      "searching": false,
	      "ajax": $.fn.dataTable.pipeline( {
	          url: url,
	          method: 'GET',
	          pages: 10
	        }),
	        "columns": [
	            { "data": "orderNo" },
	            { "data": "orderDate"},
	            { "data": "companyName"},
	            { "data": "totalResume" },
	            { "data": "totalPrice" },
	            { "data": "paymentStatus"},
	        ],
	    
	        "columnDefs": [{
	            "targets": colIndex++,
	            "data": "orderNo",
	            "sortable": false,
	            "render": function ( data, type, report, meta ) {
	            	var result = "";
	            	 if(report.orderNo != null && report.orderNo != '' && report.orderNo !='undefined'){
	           		  result = result +'<div class="tblminwidth-100"><a onclick="goToSalesOrder(\''+report.salesUuid+'\')">'+report.orderNo+'</a></div>';
	         		}else{
	         		  result = result +'<div class="tblminwidth-100">--</div>';
	         		} 
	            	
	            	 return result;
	            }
	    	}, {
		    	  	"targets": colIndex++,
		    	  	"data": "orderDate",
		    	  	"render": function(data, type, report, meta) {
		    	  		var result = "";
		            	  if(report.orderDate != null && report.orderDate != '' && report.orderDate !='undefined'){
		           		  result = result +'<div class="tblminwidth-100">'+report.orderDate+'</div>';
		         		}else{
		         		  result = result +'<div class="tblminwidth-100">--</div>';
 		         		} 
		    	  		
			             return result;
		    	  	}
		      }, {
		    	  "targets": colIndex++,
	              "data": "companyName",
	              "render": function ( data, type, report, meta ) {
	            	  var result = "";
	             	  if(report.companyName != null && report.companyName != '' && report.companyName !='undefined'){
	            		  result = result +'<div class="tblminwidth-100">'+report.companyName+'</div>';
	          		}else{
	          		  result = result +'<div class="tblminwidth-100">--</div>';
	          		} 
	            	 
	          
		              return result;
	              		
	              }
		          }, {
	            "targets": colIndex++,
	            "data": "totalResume",
	            "render": function ( data, type, report, meta ) {
	            	var result = "";
	           	  if(report.totalResume != null && report.totalResume != '' && report.totalResume !='undefined'){
	        		  result = result +'<div class="tblminwidth-100">'+report.totalResume+'</div>';
	      		}else{
	      		  result = result +'<div class="tblminwidth-100">--</div>';
	      		} 
	        	 
	            
	            	 return result;
	            }
	          }, {
	              "targets": colIndex++,
	              "data": "totalPrice",
	              "render": function ( data, type, report, meta ) {
	            	  var result = "";
	             	  if(report.totalPrice != null && report.totalPrice != '' && report.totalPrice !='undefined'){
	            		  result = result +'<div class="tblminwidth-100">'+report.totalPrice+'</div>';
	          		}else{
	          		  result = result +'<div class="tblminwidth-100">--</div>';
	          		} 
	            	
	            	
	            	  return result;
	              }
	            }, {
	                  "targets": colIndex++,
	                  "data": "paymentStatus",
	                  "render": function ( data, type, report, meta ) {
	                	  var result = "";
	               	  if(report.paymentStatus != null && report.paymentStatus != '' && report.paymentStatus !='undefined'){
	                		  result = result +'<div class="tblminwidth-100">'+report.paymentStatus+'</div>';
	              		}else{
	              		  result = result +'<div class="tblminwidth-100">--</div>';
	              		} 
	                	  return result;
	                  }
	                    } ],
		    	"scrollCollapse": false,
		    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
		    	"aaSorting": [[4,'desc']],
		    	"bLengthChange": true,
		    	"bDestroy": true,
		    	"ordering": true,
		    	"oLanguage": {
		     		"sSearch": "",
		     		 "sZeroRecords": "", "sEmptyTable": "", 
		     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
		     }
	    } );
	    $("#salesOrderListTable").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
	    $("input[type=search]").hide();
	    $("#searchByValue").keyup(function(e) {
	    	   if(e.which === 13) {
	    		   getDynamicSalesOrderList();
	    	   }
	    	});
 	 }
  
  	function goToSalesOrder(salesUuid){
		  location.href = "${appPath}/SalesOrder?salesUuid="+salesUuid;
  	}
  
  	function exportSalesOrderToPdf(){
		  if (!table.data().count()) {
			  popupDisplay();
		  }else{
	  			window.location.href = sourceleadUrl+"/exportSalesOrderToPdfOrExcel?"+tokenParamName+"="+tokenValue+"&exportType=pdf";
		  }
  	}
  
    function exportSalesOrderToExcel(){
		 if (!table.data().count()) {
			 popupDisplay();
		 }else{
		 	 window.location.href = sourceleadUrl+"/exportSalesOrderToPdfOrExcel?"+tokenParamName+"="+tokenValue+"&exportType=excel";
		 }
	}

   </script>
<script>
  	function goToCandidateList(){
			location.href = "${appPath}/candidatesList";
	}
		
	function goToGlobalMarketPlaceList(){
		location.href = "${appPath}/globalMarketplacePage";
	}

	function goToOrgMarketPlaceList(){
		location.href = "${appPath}/OrgMarketplace";
	}
	function goToCartList(){
		location.href = "${appPath}/candidateMarketplaceCart";
	}
	function goToPurchaseOrderList(){
		location.href ="${appPath}/PurchaseOrderList";
		}
	function goToSalesOrderList(){
		location.href = "${appPath}/SalesOrderList";
	}
	function goToWalletList(){
	    location.href = "${appPath}/walletList";
	}
 	function refreshPage(){
		resetSalesOrderFilter();
		$("#searchByValue").val('');
		getDynamicSalesOrderList();
		
	}
  </script>
<script>
  
  	var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
  	var locationDateFormat = '';
  	if(locationDate.match("yyyy-MM-dd")){
  		locationDateFormat = 'yy-mm-dd';
    }if(locationDate.match("dd-MM-yyyy")){
    	locationDateFormat = 'dd-mm-yy'; 
    }if(locationDate.match("MM-dd-yyyy")){
    	locationDateFormat = 'mm-dd-yy'; 
    }if(locationDate.match("yyyy-dd-MM")){
    	locationDateFormat = 'yy-dd-mm'; 
    }
  
  	function createSalesOrderFromDate(){
  		$("#salesOrderFromDate").datepicker({
  	   		dateFormat: locationDateFormat,
  	   		changeMonth: true,
  	  		changeYear: true, 
  	   		onSelect: function (selected) {
  	         	var dt = $(this).datepicker('getDate', '+1d');
  	         	dt.setDate(dt.getDate() + 1);
  	         	$("#salesOrderToDate").datepicker("option", "minDate", dt);
  	     		}
  	  	})	
  	}
  	
  	function createSalesOrderToDate(){
  		$("#salesOrderToDate").datepicker({
  	   		dateFormat: locationDateFormat,
  	   		changeMonth: true,
  	  	  	changeYear: true, 
  	     	onSelect: function (selected) {
  	         	var dt = $(this).datepicker('getDate', '-1d');
  	         	dt.setDate(dt.getDate() - 1);
  	         	$("#salesOrderFromDate").datepicker("option", "maxDate", dt);
  	     		}
  	  	})
  	}
  	
  	$(function(){
  		 createSalesOrderFromDate();
  		 createSalesOrderToDate();
  	 });
  	
 </script>
<script>	
   function closePop(){
		$("div.popupWrap").hide();
		$(".advancedsearch-mask").hide();
	}
  
   function openPop(){
		$("#modalSalesOrder, .advancedsearch-mask").fadeIn();
	}
    
    function closePop(){
    	$("#modalSalesOrder, .advancedsearch-mask").fadeOut();
    }
  
    $('input[name=dateCheckBox]').on('change', function() {
    	   		if($(this).val() == "custom"){
    	   			$("#customDates").show();
    		   		$('#salesOrderFromDate').attr('data-validation',"required");
    		        $('#salesOrderToDate').attr('data-validation',"required");
    	   		}else{
    	   			$("#customDates").hide();
    	   			$('#salesOrderFromDate').removeAttr('data-validation');
    	   	        $('#salesOrderToDate').removeAttr('data-validation');
    	   	     	$('#salesOrderToDate, #salesOrderFromDate').val('').datepicker('setDate', null);
    	   		}
    });
	
	function resetSalesOrderFilter(){
		$('#salesOrderFromDate, #salesOrderToDate').val('').datepicker('setDate', null);
		$("#customDates").hide();
		$("ul#salesOrderCheckBox > li input:radio").prop("checked", false);
		$("ul#salesOrderPaymentStatus > li input:checkbox").prop("checked", false);
	}
	
	
	function getActions(){
		$(".action-pop").slideToggle(200);
		$(".newActPop").slideUp(200);
	}	
	$("html").click(function(){
		$(".action-pop").slideUp(200);
	});
	$(".action, .action-pop").click(function(e){
 		e.stopPropagation();
	});	

	function newActions(){
		$(".newActPop").slideToggle(200);
		$(".action-pop").slideUp(200);
	}
	$("html").click(function(){
		$(".newActPop").slideUp(200);
	});
	$(".overIcon, .newActPop").click(function(e){
 		e.stopPropagation();
	});	
  </script>
<script>
  function popupDisplay(){
		$(".project_inn_mask").remove();
		var result = "";
		result = result +" <div class='project_inn_mask'></div>";
		result = result +" <div id='remove_SalesOrder_pop' class='remove_user_pop remove_user_pop_a'>";
		result = result +" <div class='modal-body'><p id='popupMsg'></p></div>";
		result = result +" <div class='clearfix'><input class='u_can_color width100percnt' type='button'  value='Ok' onClick='cancelMarketplacePopup();'></div>";
		result = result +" </div>";
		$("#emptyPopup").empty().append(result);
		$("#popupMsg").empty().append("No Records To Export in Sales Order.");
		$("#remove_SalesOrder_pop").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
	}

	function cancelMarketplacePopup(){
		$("#remove_SalesOrder_pop").fadeOut(200);
		$(".project_inn_mask").fadeOut(200);
	}
  </script>

<jsp:include page="../masterFooter.jsp" />
