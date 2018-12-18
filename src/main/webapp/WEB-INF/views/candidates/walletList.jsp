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
<div class="mask"></div>
<div class="popupWrap" id="modalWallet">
      <div class="popupHead"><span>Task Type</span><i class="closePop pull-right popClose"></i></div>
        <form id="#" name="" action="" method="post">
        <div class="popupContent">
			<div class="addInner">
      <div class="row">
        <div class="col-md-6 mb positionRelative">
        <div class="clearfix dynamic_search filterDynamicsearch">
        	<h5>Transaction Type</h5>
		</div>
          <div class="clearfix filterSearch-ul">
            <ul class="list-unstyled positionRelative filterSearch mt5" id="myManagersUL">
              <li class="checkBox positionRelative">
                <input name="orgManagers" value="1883" type="checkbox">
                <label>Credit</label>
              </li>
              <li class="checkBox positionRelative">
                <input name="orgManagers" value="" type="checkbox">
                <label>Debit </label>
              </li>
            </ul>
          </div>
        </div>
        
        <div class="col-md-6 positionRelative">
         <!--  <div class="clearfix dynamic_search filterDynamicsearch hidden">
            <input id="myRolesInput" onkeyup="myRolesFunction()" placeholder="By Date" title="Type in a roles" type="text">
            <span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span> </div> -->
          <div class="clearfix filterSearch-ul">
            <ul class="list-unstyled positionRelative filterSearch mt5" id="">
			<li class="checkBox positionRelative">
                <input name="timeInterval" value="2366" type="radio">
                <label>Today</label>
            </li>
			<li class="checkBox positionRelative">
                <input name="timeInterval" value="2366" type="radio">
                <label>Week Ago</label>
            </li>
			  <li class="checkBox positionRelative">
				<input name="timeInterval" value="2367" type="radio">
				<label>This Month</label>
			  </li>
			  <li class="checkBox positionRelative">
				<input name="timeInterval" value="2367" type="radio">
				<label>3 months ago</label>
			  </li>
			  <li class="checkBox positionRelative">
				<input name="timeInterval" value="2367" type="radio">
				<label>6 Months ago</label>
			  </li>
			  <li class="checkBox positionRelative">
				<input name="timeInterval" value="2367" type="radio">
				<label>1 Year ago</label>
			  </li>
			  <li class="checkBox positionRelative">
				<input name="timeInterval" value="customDates" type="radio" id="custmdateChkbx">
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
                	<input type="text" value="" class="form-control calendar input-sm" field-name="From Date" onclick="showWalletFromDate();" placeholder="From Date" id="frmDate">
                </div>
                <div class="col-md-6" id="custToDateDiv">
                	<input type="text" value="" class="form-control calendar input-sm" field-name="To Date" onclick="showWalletToDate();" placeholder="To Date" id="toDate">
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
  /* function goToCandidateList(){
		location.href = "${appPath}/candidatesList";
	} */
		
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
	/* function goToWalletList(){
		location.href = "${appPath}/walletList";
	} */
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
  <script type="text/javascript">
	var config = {
	  '.chosen-select'           : {},
	  '.chosen-select-deselect'  : {allow_single_deselect:true},
	  '.chosen-select-no-single' : {disable_search_threshold:10},
	  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
	  '.chosen-select-width'     : {width:"95%"}
	}
	for (var selector in config) {
	  $(selector).chosen(config[selector]);
	}
	$('.chosen-select').each(function() {
		var $this = $(this);
		$this.next().css({'width': '100%'});
	});
</script>
<script>
    function showWalletFromDate(){
    var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
      var finalDateFormatEndDate = '';
      if(locationDate.match("yyyy-MM-dd")){
          finalDateFormatEndDate = 'yy-mm-dd';
      }if(locationDate.match("dd-MM-yyyy")){
          finalDateFormatEndDate = 'dd-mm-yy'; 
      }if(locationDate.match("MM-dd-yyyy")){
          finalDateFormatEndDate = 'mm-dd-yy'; 
      }if(locationDate.match("yyyy-dd-MM")){
          finalDateFormatEndDate = 'yy-dd-mm'; 
      }
      
      $('#frmDate').datepicker({
	      dateFormat: finalDateFormatEndDate,
	      changeMonth: true,
	  	  changeYear: true, 
	      onSelect: function (selected) {
	            //var dt = new Date(selected);
	            var dt = $(this).datepicker('getDate', '+1d');
	            dt.setDate(dt.getDate() + 1);
	            $("#toDate").datepicker("option", "minDate", dt);
	        }
	  }).datepicker('show');
    }
    
    function showWalletToDate(){
        var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
          var finalDateFormatEndDate = '';
          if(locationDate.match("yyyy-MM-dd")){
              finalDateFormatEndDate = 'yy-mm-dd';
          }if(locationDate.match("dd-MM-yyyy")){
              finalDateFormatEndDate = 'dd-mm-yy'; 
          }if(locationDate.match("MM-dd-yyyy")){
              finalDateFormatEndDate = 'mm-dd-yy'; 
          }if(locationDate.match("yyyy-dd-MM")){
              finalDateFormatEndDate = 'yy-dd-mm'; 
          }
      $('#toDate').datepicker({
	      dateFormat: finalDateFormatEndDate,
	      changeMonth: true,
	  	  changeYear: true, 
	      onSelect: function (selected) {
	            //var dt = new Date(selected);
	            var dt = $(this).datepicker('getDate', '-1d');
	            dt.setDate(dt.getDate() + 1);
	            $("#frmDate").datepicker("option", "maxDate", dt);
	        }
	  }).datepicker('show');
  }

	/* function removeStartValid(){
	    $('#custFromDateDiv').find('p.jquery_form_error_message').remove();
	}
	function removeEndValid(){
	    $('#custToDateDiv').find('p.jquery_form_error_message').remove();
	} */

  /*  function validateCustDates(){
    var frequency = $("#dateFrequency").val();
    var fromDate = $("#frmDate").val();
    var toDate = $("#toDate").val();
   
    if(frequency == "Custom"){
        if(fromDate == ''){
            $('#custFromDateDiv').find('p.jquery_form_error_message').remove();
            $("input#frmDate").after( "<p class='jquery_form_error_message'>FromDate is required.</span>");
            $("input#frmDate").empty();
        }
        if(toDate == ''){
            $('#custToDateDiv').find('p.jquery_form_error_message').remove();
            $("input#toDate").after( "<p class='jquery_form_error_message'>ToDate is required.</span>");
            $("input#toDate").val('');
        }
        if((fromDate != '' && fromDate != 'undefined' && fromDate != null) && (toDate != '' && toDate != 'undefined' && toDate != null)){
            $('#custFromDateDiv').find('p.jquery_form_error_message').remove();
            $('#custToDateDiv').find('p.jquery_form_error_message').remove();
            refreshWithNewFreq();
        }
    }else{
        $('#custFromDateDiv').find('p.jquery_form_error_message').remove();
        $('#custToDateDiv').find('p.jquery_form_error_message').remove();
        refreshWithNewFreq();    
    } 
} */
</script>
<script>

/* function showWalletStartDate(){
    
    var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
	  var finalDateFormatClientInvoiceDate = '';
	  if(locationDate.match("yyyy-MM-dd")){
		  finalDateFormatClientInvoiceDate = 'yy-mm-dd';
	  }if(locationDate.match("dd-MM-yyyy")){
		  finalDateFormatClientInvoiceDate = 'dd-mm-yy'; 
	  }if(locationDate.match("MM-dd-yyyy")){
		  finalDateFormatClientInvoiceDate = 'mm-dd-yy'; 
	  }if(locationDate.match("yyyy-dd-MM")){
		  finalDateFormatClientInvoiceDate = 'yy-dd-mm'; 
	  }
	  
	
	$('#billingStartDate').datepicker({
		changeMonth: true,
		changeYear: true, 
		dateFormat : finalDateFormatClientInvoiceDate,
		onSelect : function(selected) {
			//var dt = new Date(selected);
			//var dt = toDate("#billingStartDate");
			 var dt = $(this).datepicker('getDate', '+1d');
	            dt.setDate(dt.getDate() + 1);
			$("#billingEndDate").datepicker("option", "minDate", dt);
			removeStartdateval();
		}
	}).datepicker('show');;
} */


	function openPop(){
		$("#modalWallet, .mask").fadeIn();
	}
	$(".popClose").click(function() {
		$("#modalWallet, .mask").fadeOut();
	});
	//Custom Dates
	$('input[name=timeInterval]').on('change', function() {
   		if($(this).val() == "customDates"){
   			$("#customDates").show();
	   		$('#frmDate').attr('data-validation',"required");
	        $('#toDate').attr('data-validation',"required");
   		}else{
   			$("#customDates").hide();
   			$('#frmDate').removeAttr('data-validation');
   	        $('#toDate').removeAttr('data-validation');
   		}
		
	});
	/* $("#custmdateChkbx").click(function() {
		
	if($(this).is(":checked")){
		
	}else {
		
	}
	}); */
</script>
  <jsp:include page="../masterFooter.jsp" /> 