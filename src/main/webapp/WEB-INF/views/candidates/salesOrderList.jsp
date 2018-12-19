<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 <section class="content-wraper contentWraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Sales Orders List</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Sales Order List</div>
    </section>
    
       <div class="clearfix">
	<ul class="project-tabs-menu projecttabs-custom clearfix">
         <li id="" ><a href="${appPath}/candidatesList">Candidate List</a></li>
          <li id=""><a href="javascript:void(0)" onclick="javascript:goToOrgMarketPlaceList();">Marketplace</a></li>
          <li id=""><a href="javascript:void(0)" onclick="javascript:goToGlobalMarketPlaceList();">Global Marketplace</a></li>
          <li id=""><a href="javascript:void(0)" onclick="javascript:goToPurchaseOrderList();">Purchase Orderlist</a></li>
            <li id="" class="current"><a href="javascript:void(0)">Sales Orderlist</a></li>
            <li id=""><a href="javascript:void(0)" onclick="javascript:goToWalletList();">Wallet</a></li>       
     
     </ul>
</div>
    
    <div class="contentareaInner-styles clearfix">
      <div class="timesheet_out" id="payrollRegister" style="display: block;">
        <div class="clearfix">
    	 <div class="positionRelative reportHead clearfix">
			  <span class="pull-left refreIcon noPad mb10">
				  <a href="javascript:void(0)" class="refeIcon" title="Refresh" onclick="refreshPage();"></a>
			  </span>
            <ul class="pull-right list-unstyled list-inline noPad">
              <li><a href="javascript:void(0)" class="filtIcon" title="Filters" onclick="openPop();"></a></li>
              <li><a href="javascript:void(0)" class="overIcon" title="Actions" onclick="newActions();"></a>
                <div class="newActPop" style=""> <span><a href="javascript:void(0)" onclick="exportToPdf();" title="PDF">Export PDF</a> <a href="javascript:void(0)" onclick="exportToExcel();" title="Excel">Export Excel</a> </span> </div>
              </li>
            <li class="noMar">
                <input class="repSearchBar" placeholder="Search" id="searchByValue" type="text" value="">
                <span class="repSearchIcon" onclick="getDynamicSalesOrderList();"></span>
			</li>
            </ul>
          </div>
          
          
          <div  class="require_wrap_table tableFirstSecond clearfix datatable_filter">
          	<div id="dynamicSalesOrderList"></div>
               
      		</div>
        </div>
      </div>
    </div> 
  </section>
  
  
<div class="mask"></div>
<div class="popupWrap" id="modalWallet">
      <div class="popupHead"><span>Task Type</span><i class="closePop pull-right popClose"></i></div>
        <form id="#" name="" action="" method="post">
        <div class="popupContent">
			<div class="addInner">
      <div class="row">
        <div class="col-md-6 mb positionRelative">
        <div class="clearfix dynamic_search filterDynamicsearch">
        	<h5>Payment Status</h5>
		</div>
          <div class="clearfix filterSearch-ul">
            <ul class="list-unstyled positionRelative filterSearch mt5" id="myManagersUL">
              <li class="checkBox positionRelative">
                <input name="orgManagers" value="" type="checkbox">
                <label>Success</label>
              </li>
              <li class="checkBox positionRelative">
                <input name="orgManagers" value="" type="checkbox">
                <label>Failure</label>
              </li>
            </ul>
          </div>
        </div>
        
        <div class="col-md-6 positionRelative">
          <!-- <div class="clearfix dynamic_search filterDynamicsearch">
            <input id="myRolesInput" onkeyup="myRolesFunction()" placeholder="By Date" title="Type in a roles" type="text">
            <span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span> </div> -->
          <div class="clearfix filterSearch-ul">
            <ul class="list-unstyled positionRelative filterSearch mt5" id="">
			<li class="checkBox positionRelative">
                <input name="userRole" value="2366" type="radio">
                <label>Today</label>
            </li>
			<li class="checkBox positionRelative">
                <input name="userRole" value="2366" type="radio">
                <label>Week Ago</label>
            </li>
			  <li class="checkBox positionRelative">
				<input name="userRole" value="2367" type="radio">
				<label>This Month</label>
			  </li>
			  <li class="checkBox positionRelative">
				<input name="userRole" value="2367" type="radio">
				<label>3 months ago</label>
			  </li>
			  <li class="checkBox positionRelative">
				<input name="userRole" value="2367" type="radio">
				<label>6 Months ago</label>
			  </li>
			  <li class="checkBox positionRelative">
				<input name="userRole" value="2367" type="radio">
				<label>1 Year ago</label>
			  </li>
			  <li class="checkBox positionRelative">
				<input name="userRole" value="2367" type="radio" id="custmdateChkbx">
				<label>Custom Dates</label>
			  </li>
			  </ul>
          </div>
        </div>
        <div class="clearfix"></div>
		  <div class="clearfix" id="customDates" style="display: none;">
		<div class="col-md-12 positionRelative">
          <div class="clearfix dynamic_search filterDynamicsearch" >
          <h5 class="margin-top0">Custom Dates<span class="text-danger paddingLeft">*</span></h5>
		  </div>
          <div class="clearfix">
            <ul class="list-unstyled positionRelative filterSearch mb0">
              <li class="positionRelative">
                <div class="row">
                <div class="col-md-6" id="custFromDateDiv">
                	<input type="text" value="" class="form-control calendar input-sm" field-name="From Date" placeholder="From Date" id="frmDate">
                </div>
                <div class="col-md-6" id="custToDateDiv">
                	<input type="text" value="" class="form-control calendar input-sm" field-name="To Date" placeholder="To Date" id="toDate">
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
          <button type="button" class="slBtn md colorBtn" onclick="saveInviteCompany();">Save</button>
          <button type="button" class="slBtn md popClose" onclick="flushInviteCompany();"> Cancel</button>
        </div>
        
        <div>
</div></form>
  </div>
  
  
  <script>
  var table = "";
  $(document).ready(function () {
	  getDynamicSalesOrderList();
	});
  function getDynamicSalesOrderList(){
	  	var result = "";
	  	var sourceleadUrl = '${sourceleadUrl}';	
	  	var url ="";
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
	  	var searchValue = $("#searchByValue").val();
  		url = sourceleadUrl + "/getDynamicSalesOrderList?searchValue="+searchValue;  
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
	            { "data": "noOfResumes" },
	            { "data": "totalResumePrice" },
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
	    $("#salesOrderListTableSearch").keyup(function(e) {
	    	   if(e.which === 13) {
	    		   getDynamicSalesOrderList();
	    	   }
	    	});
	 	
	    
  }
  
  function goToSalesOrder(salesUuid){
	  location.href = "${appPath}/SalesOrder?salesUuid="+salesUuid;
  }
  
  function exportToPdf(){
	  if (!table.data().count()) {
		    alert( 'Empty table' );
		}else{
	  		window.location.href = sourceleadUrl+"/exportSalesOrderToPdfOrExcel?"+tokenParamName+"="+tokenValue+"&exportType=pdf";
		}
  	}
  
  function exportToExcel(){
		if (!table.data().count()) {
		    alert( 'Empty table' );
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
		location.reload();
	}
  </script>
  <script>
  function openPop(){
		$("#modalWallet, .mask").fadeIn();
	}
	$(".popClose").click(function() {
		$("#modalWallet, .mask").fadeOut();
	});
  
	//Custom Dates
	$("#custmdateChkbx").click(function() {
	if($(this).is(":checked")){
		$("#customDates").show();
	}else {
		$("#customDates").hide();
	}
	});
	
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
  <jsp:include page="../masterFooter.jsp" /> 