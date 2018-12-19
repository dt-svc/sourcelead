<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script>
$(document).ready(function(){
   getPurchaseOrderList();
});
</script>


<!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Purchase Order List</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo;Purchase Order List</div>
    </section>
    
          <div class="clearfix">
	<ul class="project-tabs-menu projecttabs-custom clearfix">
          <li id="" ><a href="${appPath}/candidatesList">Candidate List</a></li>
          <li id=""><a href="javascript:void(0)" onclick="javascript:goToOrgMarketPlaceList();">Marketplace</a></li>
          <li id=""><a href="javascript:void(0)" onclick="javascript:goToGlobalMarketPlaceList();">Global Marketplace</a></li>
          <li id="" class="current"><a href="javascript:void(0)">Purchase Orderlist</a></li>
          <li id=""><a href="javascript:void(0)" onclick="javascript: goToSalesOrderList();">Sales Orderlist</a></li> 
          <li id=""><a href="javascript:void(0)" onclick="javascript:goToWalletList();">Wallet</a></li>       
         <div class="clearfix"></div>         
     </ul>
</div>
    <div id="emptyPopup"></div>
    <div class="clearfix">
      <div class="timesheet_out" id="payrollRegister" style="display: block;">
        <div class="clearfix">
          <div class="positionRelative reportHead clearfix">
			  <span class="pull-left refreIcon mb10">
				  <a href="javascript:void(0)" class="refeIcon" title="Refresh" onclick="refreshPage();"></a>
			  </span>
            <ul class="pull-right list-unstyled list-inline mr">
              <li><a href="javascript:void(0)" class="filtIcon" title="Filters" onclick="openPop();"></a></li>
              
              <li><a href="javascript:void(0)" class="overIcon" title="Actions" onclick="newActions();"></a>
                <div class="newActPop" style=""> 
                <span>
                <a href="javascript:void(0)" onclick="exportToPdf();" title="PDF">Export PDF</a> 
                <a href="javascript:void(0)" onclick="exportToExcel();" title="Excel">Export Excel</a>
                 </span> 
                 </div>
              </li>
            <li class="noMar">
                <input class="repSearchBar" placeholder="Search" id="dynamicUserDataTableSearch" type="text">
                <span class="repSearchIcon" onclick="getDynamciUserList();"></span>
			</li>
            </ul>
          </div>
        <div class="candidate_wrap_table datatable_filter">
        <input type="hidden" id="noCandidates" name="noCandidates" />
        
        <div id="purchaseList"></div>
        </div>
        </div>
      </div>
    </div>
  </section>
  
  
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
  
   function getPurchaseOrderList(){
	  
	   	var url = sourceleadUrl+"/getPurchasedOrderList";
	   	getPurchaseOrderListPagin(url);	  
  }
  </script>
  
   
  
  <script>
function getPurchaseOrderListPagin(url){
 	var result = "";
 
 	
 	result = result + '<table id="purchaseTable" class="display" cellspacing="0" width="100%" >';
	result = result + '<thead>';
	result = result +'       <tr>                             ';		                            
	result = result +'         <th>Order No</th>    ';
	result = result +'         <th>Order Date</th>                    ';
	result = result +'         <th>Company Name</th>          ';
	result = result +'         <th>Total Resumes</th>              ';
	result = result +'         <th>Total Price</th>  ';
	result = result +'         <th>Service fee</th>        ';
	result = result +'         <th>Discount</th>               ';
	result = result +'         <th>Grant Total</th>                  ';
	result = result +'         <th>Payment Status</th>            ';
	result = result +'       </tr>                            ';
	result = result + '</thead>	';
    result = result +"</table>";
    $("#purchaseList").empty();  
    $("#purchaseList").append(result);
    purchasePagination(url); 
	
}

</script>

<script>
function purchasePagination(url){
	var colIndex = 0;
    $('#purchaseTable').DataTable( { 
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
            { "data": "serviceFee"},
            { "data": "discount"},
            { "data": "grandTotal"},
            { "data": "paymentStatus"},
        ],
        "columnDefs": [{
            "targets": colIndex++,
            "data": "orderNo",
            "sortable": false,
            "render": function ( data, type, report, meta ) {
            	var result = "";
            	$("#noCandidates").val(1);
            	 if(report.orderNo != null && report.orderNo != '' && report.orderNo !='undefined'){
             result = result +'<div class="tbldata-dotdot-vsm" title="'+report.orderNo+'"><a href="'+sourceleadUrl+'/PurchaseOrder?purchaseUuid='+report.purchaseUuid+'" title="'+report.purchaseUuid+'">'+report.orderNo+'</a></div>';	 
            		         		 
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
	      },{
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
	          },{
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
                "data": "serviceFee",
                "render": function ( data, type, report, meta ) {
                	var result = "";
               	  if(report.serviceFee != null && report.serviceFee != '' && report.serviceFee !='undefined'){
            		  result = result +'<div class="tblminwidth-100">'+report.serviceFee+'</div>';
          		}else{
          		  result = result +'<div class="tblminwidth-100">--</div>';
          		} 
            	  
                	
    	              return result;
                }
          },{
	    	  	"targets": colIndex++,
	    	  	"data": "discount",
	    	  	"render": function(data, type, report, meta) {
	    	  		var result = "";
	    	   	  if(report.discount != null && report.discount != '' && report.discount !='undefined'){
            		  result = result +'<div class="tblminwidth-100">'+report.discount+'</div>';
          		}else{
          		  result = result +'<div class="tblminwidth-100">--</div>';
          		} 
            	 
	    	  	
  	              return result;
	    	  	}
	    	  },{
	    	  	"targets": colIndex++,
	    	  	"data": "grandTotal",
	    	  	"render": function(data, type, report, meta) {
	    	  		var result = "";
	    	   	  if(report.grandTotal != null && report.grandTotal != '' && report.grandTotal !='undefined'){
            		  result = result +'<div class="tblminwidth-100">'+report.grandTotal+'</div>';
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
    $("#purchaseTable").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $("#purchaseTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   getPurchaseOrderList();
    	   }
    	});
}

</script>
<script>
function candidateCancel(){
	$("#emptyPopup").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>
<script>
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
function exportToPdf(){
	var isCandidatesAvailable= $("#noCandidates").val();
	if(isCandidatesAvailable >0)
		{
	  window.location.href = sourceleadUrl+"/getPurchaseCandidatesExportList?&exportType=pdf";	
	   }
	  else{
		
		    var result = "";
			result = result +"	<div class='project_inn_mask'></div>";
			result = result +"	   <div id='candidate_user_pop' class='remove_user_pop remove_user_pop_a'>";
			result = result +"		<div class='modal-body text-center'>";
			result = result +"		<span>Candidates List is Empty</span>";
			result = result +"		</div>";
			result = result +"		<b href='javascript:void(0)'><input class='width100percnt' type='button' value='Ok' onclick='candidateCancel();'></b>";
			result = result +"	</div>";
			$("#emptyPopup").append(result);
			$("#candidate_user_pop").slideDown(200);
			$(".project_inn_mask").fadeIn(200);
	  }
}
</script>
<script>
function exportToExcel(){
	var isCandidatesAvailable= $("#noCandidates").val();
	if(isCandidatesAvailable >0)
		{	
	  window.location.href = sourceleadUrl+"/getPurchaseCandidatesExportList?&exportType=xls";	
	  }
	else{
		
	  		var result = "";
			result = result +"	<div class='project_inn_mask'></div>";
			result = result +"	   <div id='candidate_user_pop' class='remove_user_pop remove_user_pop_a'>";
			result = result +"		<div class='modal-body text-center'>";
			result = result +"		<span>Candidates List is Empty</span>";
			result = result +"		</div>";
			result = result +"		<b href='javascript:void(0)'><input class='width100percnt'  type='button' value='Ok' onclick='candidateCancel();'></b>";
			result = result +"	</div>";
			$("#emptyPopup").append(result);
			$("#candidate_user_pop").slideDown(200);
			$(".project_inn_mask").fadeIn(200);
	     } 
}
</script>

  
  <jsp:include page="../masterFooter.jsp" /> 