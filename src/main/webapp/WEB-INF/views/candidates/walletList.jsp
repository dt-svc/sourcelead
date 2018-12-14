<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!-- Starts -->
  <!-- Content Area -->
 <script type="text/javascript">
 $(document).ready(function () {
     getDynamicWalletList();
 });
 </script>
 
 
<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left"><h2>Wallet</h2></div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo;Wallet</div>
    </section>
     <div class="clearfix">
	<ul class="project-tabs-menu projecttabs-custom clearfix">
           <li id="" ><a href="${appPath}/candidatesList">Candidate List</a></li>
           <li id=""><a href="javascript:void(0)" onclick="javascript:goToOrgMarketPlaceList();">Marketplace</a></li>
           <li id=""><a href="javascript:void(0)" onclick="javascript:goToGlobalMarketPlaceList();">Global Marketplace</a></li>
           <li id=""><a href="javascript:void(0)" onclick="javascript:goToPurchaseOrderList();">Purchase Orderlist</a></li>
           <li id=""><a href="javascript:void(0)" onclick="javascript: goToSalesOrderList();">Sales Orderlist</a></li>
          <li id="" class="current"><a href="javascript:void(0)" onclick="javascript: void(0);">Wallet</a></li>
         <div class="clearfix"></div>
     </ul>
   </div>
		<div class="clearfix">
			 <div class="media profileDetails-Widget backgroundWhite">
              <div class="media-left rewardImage">
				  <i class="walletSvg"></i>
				</div>
              <div class="media-body">
                <div class="col-md-9">
					<div class="ml5">
                <h4 class="text-muted text-primary mt5 mb0"><span  id="totoalAmount" ></span></h4>
				<p class="text-muted fontSmall mt5">Wallet balance</p>
						</div>
                  <div class="clearfix"></div>
               </div>
				  <div class="col-md-3 padding_right">
					  <a href="javascript:void(0)" title="Edit" class="btn-block btn btn-primary mt10">Withdraw Amount</a>
				  </div>
              </div>
            </div>	
          		  
			 <div class="clearfix"></div>
	<div class="col-md-12 cartInvoiceTable backgroundWhite mt">
		<div class="positionRelative reportHead clearfix mt">
            <ul class="pull-right list-unstyled list-inline noPad">
         <li><a href="javascript:void(0)" class="filtIcon" title="Filters" onclick="openPop();"></a></li>
				<li>
				  <a href="javascript:void(0)" title="Print" class="printIC"></a>
			</li>
            <li>
				  <a href="javascript:void(0)" title="Sorting" class="assenDesendingIC"></a>
			</li>
            <li class="noMar">
                <input class="repSearchBar" placeholder="Search" id="walletSearch" type="text">
                <span class="repSearchIcon" onclick="getDynamicWalletList();"></span>
			</li>
            </ul>
			<div class="clearfix mb"></div>
          </div>
		
			<div id="dynamicWalletListViewDiv" class="project_wrap_table datatable_filter"></div>
		</div>
		</div>
  </section>

<!-- ends -->

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
  </script>
  
  <script>
  function getDynamicWalletList(){
	  var sourceleadUrl = '${sourceleadUrl}';
	  var searchValue = $("#walletSearch").val();
		 searchValue = searchValue ? searchValue : "";
		var url = sourceleadUrl	+"/getWalletList?searchValue="+searchValue;
		prepareWalletList(url);	
  }
  function prepareWalletList(url){
	     var result = "";
	     result = result + '<table id="dynamicWalletListTbl">';
	     result = result + '<thead>';
	     result = result + '<tr>';
	     result = result + '<th>Transaction Date</th>';
	     result = result + '<th>Transaction ID</th>';
	     result = result + '<th>Description</th>';
	     result = result + '<th>Amount</th>';
	     result = result + '</tr>';
	     result = result + '</thead>';
	     result = result + '</table>';
	     $("#dynamicWalletListViewDiv").empty();
	     $("#dynamicWalletListViewDiv").append(result);
	     walletPaginations(url);
  
  }
  function walletPaginations(url){
		var colIndex = 0;
	    $('#dynamicWalletListTbl').DataTable( { 
		  "processing": true,
	      "serverSide": true,
	      "searching": false,
	      "ajax": $.fn.dataTable.pipeline( {
	          url: url,
	          method: 'POST',
	          pages: 10
	        }),
	        
	        "columns": [
	             {"data": "Transaction Date"},
	             {"data": "Transaction ID"},
	             {"data": "Description"},
	             {"data": "Amount"}
	         ],
	         "columnDefs": [
	             {
	                 "targets": colIndex++,
	                 bSortable: true,
	                 aTargets: [ -1 ],
	                  "data": "orderDate", 
	                 "render": function (data, type, opt, meta) {
	                	 
	                     var result = "";
	                     if (opt.orderDate !== null && opt.orderDate !== '' && opt.orderDate !== 'undefined') {
	                    	    result = result + "<td><div class='tbldata-dotdot-sm' title = '"+opt.orderDate +"'>"+opt.orderDate+"  </div></td>";
	                     } else {
	                         result = result + "<td><div class='tbldata-dotdot-sm'>---</div></td>";
	                     }
	                     return result;
	                 }
	             },
	             {
	                 "targets": colIndex++,
	                 "data": "orderNo",
	                 "render": function (data, type, opt, meta) {
	                     var result = "";
	                     if (opt.orderNo !== null && opt.orderNo !== '' && opt.orderNo !== 'undefined') {
	                    	 result = result + "<td><div class='tbldata-dotdot-sm' title = '"+opt.orderNo+"'>"+opt.orderNo+"</div></td>";
	                     } else {
	                         result = result + "<td><div class='tbldata-dotdot-sm'>---</div></td>";
	                     }
	                     return result;
	                 }
	             },
	             {
	                 "targets": colIndex++,
	                 "data": "orderNo",
	                 "render": function (data, type, opt, meta) {
	                     var result = "";
	                     if (opt.orderNo !== null && opt.orderNo !== '' && opt.orderNo !== 'undefined') {
	                    	 result = result + "<td><div class='tbldata-dotdot-sm' title = '"+opt.orderNo+"'>" + opt.orderNo + "</div></td>";
	                     } else {
	                         result = result + "<td><div class='tbldata-dotdot-sm'>---</div></td>";
	                     }
	                     return result;
	                 }
	             },
	             {
	                 "targets": colIndex++,
	                 "data": "totalPrice",
	                 "render": function (data, type, opt, meta) {
	                     var result = "";
	                     if (opt.totalPrice !== null && opt.totalPrice !== '' && opt.totalPrice !== 'undefined') {
	                    	 $("#totoalAmount").html(opt.sumOfOrdersAmount)
	                    	 result = result + "<td><div class='tbldata-dotdot-sm' title = '"+opt.totalPrice+"'>" + opt.totalPrice + "</div></td>";
	                     } else {
	                         result = result + "<td><div class='tbldata-dotdot-sm'>---</div></td>";
	                     } 
	                     return result;
	                 }
	             }
	         ],
	         "scrollCollapse": false,
	         "lengthMenu": [[10, 15, 20], [10, 15, 20]],
	         "aaSorting": [],
	         "bLengthChange": true,
	         "bDestroy": true,
	         "ordering": true,
	         "initComplete": function (settings, json) {
	         },
	         "oLanguage": {
	             "sSearch": "",
	             "sZeroRecords": "", "sEmptyTable": "",
	             "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	         }
	     });
	    
	     $("#dynamicWalletListTbl").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>");
	     
	    $("table").colResizable(); // for resizing columns in tables. 
	      
  }
  </script>
  
  
  <jsp:include page="../masterFooter.jsp" /> 