<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<script type="text/javascript">
$(document).ready(function(){
	 arAgingReport(); 
	 $("#apagingType").val('client');
});
</script>

<!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>A/R Aging</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/reportsDashboard'/>">Reports Dashboard</a> &raquo; <a href="<c:url value='${appPath}/financialReportsList'/>">Financial Reports</a> &raquo; A/R Aging</div>
    </section>
    <div class="clearfix"></div>

      <div class="vendor_wrap">
      
      
    <div class="ReqRepHead clearfix">
      <div class="col-md-3 pull-left reqListBtn"> <a href='${appPath}/arAgingGraphReport' title="Graphical View" class="btn btn-default"><i class="fa fa-pie-chart"></i></a>
       <a href='${appPath}/arAgingReport' title="List View" class="btn btn-default"><i class="fa fa-list"></i></a>
        <div class="hidden"> <span class="rAct" title='${appPath}/arAgingReport' id="GView"><i class="fa fa-pie-chart"></i></span> <span class="" title="List View" id="LView"><i class="fa fa-list"></i></span> </div>
      </div>
   
     <!--  <div class="col-md-1  col-md-offset-8">
        <div class="btn-group bootstrap_dropdownMenu dropdownPadding">
          <button type="button" class="btn btn-primary btn-sm" title="Actions"><i class="fa fa-retweet"></i></button>
          <button type="button" class="btn btn-primary dropdown-toggle btn-sm" data-toggle="dropdown"> <span class="caret"></span> </button>
          <ul class="dropdown-menu clearfix  dropdown-menu-right">
            <li role="Export" class="mt5 cursorPointer"><i class="fa fa-file-pdf-o paddingRight"></i>Export</li>
            
						<li role="Print" class="mt5 cursorPointer"><i class="fa fa-print paddingRight"></i>Print</li>
						<li role="Download" class="mt5 cursorPointer"><i class="fa fa-download paddingRight"></i>Download</li>
						<li role="Share" class="mt5 mb5 cursorPointer"><i class="fa fa-share-alt paddingRight"></i>Share</li>

          </ul>
        </div>
      </div> -->
    </div>
    <div class="clearfix"></div>      
      
      
        <div class="project_tabs_inn_table positionRelative">
         <div class="clearfix table_head searchPad">
        	 
        	 <div class="select-fild col-md-2 mt5 pull-right">
                    <select  name="apagingType" id="apagingType" onchange="change();">
                        <option value="client">By Client</option>
                        <option value="Receivable">By Invoice</option>
                    </select>
                </div>
        	 <div class="col-md-2 dropdown-select positionRelatives mt5 mb5 pull-right" id="customDatesDropdown" style="display: none">
        <select class="form-control" id="custom-dates"  name="filterType" onchange="aragingInvoice();">
          <option value="">Select Date</option>
          <option value="last_7days">Last 7 days</option> 
          <option value="last_15days">Last 15 days</option>
          <option value="1month">1 month</option>
          <option value="3months">3 months</option>
          <option value="last_years" >last year</option>
          <option value="custom">Custom</option>
        </select>
        <!-- Top Leave Takers Custom Date -->
        <div class="clearfix pull-lefet col-md-12 paddingRight customLeaves" id="custom-select" style="display: none; width: 346px;">
          <div class="pull-left">
            <div class="pull-left mr5" style="width: 125px">
              <input value="" name="Date" id="fromDate" placeholder="From" class="form-control calendar input-sm" type="text">
            </div>
            <div class="pull-left mr5" style="width: 125px">
              <input value="" name="Date" id="toDate" placeholder="To" class="form-control calendar input-sm" type="text">
            </div>
           <button type="button" class="btn btn-primary btn-sm pull-left"  onclick="customSearch();">Submit</button>
          </div>
        </div>
        <!-- Top Leave Takers Custom Date --> 
      </div>
         	<!--  <p class="col-md-2 form-control-static">
         	 	<label class="mt5 text-muted">Total:</label><span class="ml mt5" id="apagingTotal"><strong></strong></span>
         	 	<label class="mt5 text-muted">Total:</label><span class="ml mt5" id="apagingInvoiceTotal"><strong></strong></span>
         	 </p> -->
         </div>
         
         <div class='remove_search'>
 	   <input type='text' placeholder='Search' id='dataTableClientSearch'/>
 	    </div>
         <div id=aragingByClient class="datatable_filter">
        
        </div>
        
        <div class="clearfix"></div>
         <div class='remove_search'>
 	   <input type='text' placeholder='Search' id='dataTableInvoiceSearch'/>
 	    </div>
      <div id="aragingByInvoice" class="datatable_filter">
         
        </div>
        </div>
        </div>
  </section>
  
<script>
$(function() {
        $('#apagingType').change(function(){
			var optval = $(this).val();
			if(optval === "Receivable") {
				$("#byInvoice").show();
				$("#byCustomer").hide();
				$("#customDatesDropdown").show();
			}else{
				$("#byCustomer").show();
				$("#byInvoice").hide();
				$("#customDatesDropdown").hide();
			}
        });
    });
</script> 
  
  <!-- Bill Started end -->
  
  <script>
  $( function() {
		 var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
		  var finalDateFormatProjectSave = '';
		  if(locationDate.match("yyyy-MM-dd")){
			  finalDateFormatProjectSave = 'yy-mm-dd';
		  }if(locationDate.match("dd-MM-yyyy")){
			  finalDateFormatProjectSave = 'dd-mm-yy'; 
		  }if(locationDate.match("MM-dd-yyyy")){
			  finalDateFormatProjectSave = 'mm-dd-yy'; 
		  }if(locationDate.match("yyyy-dd-MM")){
			  finalDateFormatProjectSave = 'yy-dd-mm'; 
		  }
    
	  $('#fromDate').datepicker({
			dateFormat : finalDateFormatProjectSave,
			changeMonth: true,
			changeYear: true, 
			onSelect : function(selected) {
				var dt = $(this).datepicker('getDate', '+1d');
				dt.setDate(dt.getDate());
				$("#toDate").datepicker("option", "minDate", dt);
			}
		});
	  
	  $('#toDate').datepicker({
			dateFormat : finalDateFormatProjectSave,
			changeMonth: true,
			changeYear: true, 
			onSelect : function(selected) {
				var dt = $(this).datepicker('getDate', '-1d');
				dt.setDate(dt.getDate());
				$("#fromDate").datepicker("option", "maxDate", dt);
			}
		});
  } );
  </script>

  
  
  <script>
function change(){
	var optval = $("#apagingType").val();
	if(optval === "Receivable") {
		aragingInvoice();
	}else{
		arAgingReport();
	}
}
</script>

<script>
function arAgingReport(){
			var apagingType = "Receivable";
			var sourceleadUrl ='${sourceleadUrl}';
			var searchValue = $("#dataTableClientSearch").val();
			searchValue = searchValue ? searchValue : "";
			var url = sourceleadUrl+"/vendorapAging?searchValue="+searchValue+"&apagingType="+apagingType;
			prepareAragingList(url);
}
</script>


<script>
function prepareAragingList(url){
	$("#aragingByInvoice").empty();
	   $("#aragingByClient").empty();
	   $("#dataTableClientSearch").show();
	   $("#dataTableInvoiceSearch").hide();
	   $("#apagingInvoiceTotal").hide();
	   $("#apagingTotal").show();
 	    var result = "";
 	   /*  result = result +" <div class='remove_search'>";
 	    result = result +" <input type='text' placeholder='Search' id='dataTableSearch'/>";
 	    result = result +"</div>"; */
 	    result = result +"<table id='aragingPagination' class='display' cellspacing='0' width='100%' >"; 
        result = result +"<thead>"; 
        result = result + '<tr>';
        result = result + '<th>Client</th>';
		result = result + '<th>10 Days</th>';
		result = result + '<th>15 Days</th>';
		result = result + '<th>30 Days</th>';
		result = result + '<th>60 Days</th>';
		result = result + '<th>90 Days</th>';
		result = result + '<th>180 Days</th>';
		result = result + '<th>365 Days</th>';
		result = result + '<th>&gt 365 Days</th>';
		result = result + '<th>Total</th>';
		result = result + '</tr>';
        result = result +"</thead>";
        result = result +"<tbody>"; 
        result = result +" </tbody>";
        result = result +"</table>";
      $("#aragingByClient").append(result);
      araging(url); 
}
</script>

<script>
function araging(url){
	var colIndex = 0;
	var apagingTotal = 0;
    $('#aragingPagination').DataTable( { 
    		"processing": true,
        "serverSide": true,
        "searching": false,
        "ajax": $.fn.dataTable.pipeline( {
            url: url,
            method: 'POST',
            pages: 10
        }),
        /* "columns": [
        	{ "data": "orgName" },
            { "data": "tenDays" },
            { "data": "fifteenDays"},
            { "data": "thirtyDays"},
            { "data": "sixtyDays" },
            { "data": "nintyDays"},
            { "data": "oneeightyDays" },
            { "data": "threeSixtyFiveDays" },
            { "data": "above365Days"},
            { "data": "total"}
        ], */
        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"render": function(data, type, opt, meta) {
	    	  		var result = "";
	    	  		result = result + ' <td><div class="tbldata-dotdot-sm">'+opt.orgName+'</div></td>';
		             return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
						result = result + ' <td><div class="">'+opt.tenDays.toFixed(2)+'</div></td>';
	              return result;
              		
              }
	          },{
            "targets": colIndex++,
            "render": function ( data, type, opt, meta ) {
            	return  '<td><div class="">'+opt.fifteenDays.toFixed(2)+'</div></td>';
            }
          }, {
              "targets": colIndex++,
              "render": function ( data, type, opt, meta ) {
            	  return '<td><div class="" title="'+opt.thirtyDays+'">'+opt.thirtyDays.toFixed(2)+'</div></td>';
              }
            }, {
                "targets": colIndex++,
                "render": function ( data, type, opt, meta ) {
                		var result = "";
							result = result + ' <td><div class="">'+opt.sixtyDays.toFixed(2)+'</div></td>';
    	              return result;
                }
          },{
	    	  	"targets": colIndex++,
	    	  	"render": function(data, type, opt, meta) {
	    	  		var result = "";
					result = result + '<td><div class="">'+opt.nintyDays.toFixed(2)+'</div></td>';
  	              return result;
	    	  	}
	    	  },{
	    	  	"targets": colIndex++,
	    	  	"render": function(data, type, opt, meta) {
	    	  		var result = "";
	    	  			result = result + ' <td><div class="">'+opt.oneeightyDays.toFixed(2)+'</div></td>';
	    	  		return result;
	    	  	}
	    	  }, {
                  "targets": colIndex++,
                  "render": function ( data, type, opt, meta ) {
                	  return '<td><div class="">'+opt.threeSixtyFiveDays.toFixed(2)+'</div></td>';
                  }
                },{
                    "targets": colIndex++,
                    "render": function ( data, type, opt, meta ) {
                    	return '<td><div class="">'+opt.abovethreeSixtyFiveDays.toFixed(2)+'</div></td>';
                    }
                  },{
                      "targets": colIndex++,
                      "render": function ( data, type, opt, meta ) {
                    	  apagingTotal = parseFloat(apagingTotal)+parseFloat(opt.total);
                    	  $("#apagingTotal").html(apagingTotal.toFixed(2));
                    	  return ' <td><div class="">'+opt.total.toFixed(2)+'</div></td>';
                      }
                    } ],
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"aaSorting": [[0,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
    } );
    $("#aragingPagination").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $("#dataTableClientSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   arAgingReport();
    	   }
    	});
    
   
}
</script>


<!-- Invoice start -->
<script>
	function customSearch(){
		aragingInvoice();
		$("#custom-select").hide();
	}
</script>

 <script>
function aragingInvoice(){
	var filterType = $("#custom-dates").val(); 
	var fromDate = "";
	var toDate = "";
	if(filterType === 'custom'){
		fromDate = $("#fromDate").val();
		toDate = $("#toDate").val();
	}else{
		$("#fromDate").val("");
		$("#toDate").val("");
	}
   // var apagingType = 'Receivable';
    var apagingType = $("#apagingType").val();
	var sourceleadUrl ='${sourceleadUrl}';
	var searchValue = $("#dataTableInvoiceSearch").val();
	var filterType = $("#custom-dates").val(); 
	searchValue = searchValue ? searchValue : "";
	var url = sourceleadUrl+"/apagingBill?searchValue="+searchValue+"&apagingType="+apagingType+"&filterType="+filterType+"&fromDate="+fromDate+"&toDate="+toDate;
	if(filterType != 'custom'){
		prepareAragingInvoiceList(url);
	}else{
		if(fromDate != null && fromDate != '' && fromDate != 'undefined' && toDate != null && toDate != '' && toDate != 'undefined'){
			prepareAragingInvoiceList(url);
		}
	}
}
</script>

<script>
function prepareAragingInvoiceList(url){
	  $("#aragingByClient").empty();
	  $("#aragingByInvoice").empty();
	  $("#dataTableClientSearch").hide();
	   $("#dataTableInvoiceSearch").show();
	   $("#apagingInvoiceTotal").show();
	   $("#apagingTotal").hide();
        var result = "";
 	    result = result +"<table id='aragingInvoicePagination' class='display' cellspacing='0' width='100%' >"; 
        result = result +"<thead>"; 
        result = result + '<tr>';
        result = result + '<th>Refer Id</th>';
		result = result + '<th>Client</th>';
		result = result + '<th>Invoice Date</th>';
		result = result + '<th>Due Date</th>';
		result = result + '<th>Due Amount</th>';
		result = result + '<th>Total Amount</th>';
		result = result + '</tr>';
        result = result +"</thead>";
        result = result +"<tbody>"; 
        result = result +" </tbody>";
        result = result +"</table>";
      $("#aragingByInvoice").append(result);
      aragingInvoiceData(url); 
}
</script>

<script>
function aragingInvoiceData(url){
	var colIndex = 0;
	var apagingInvoiceTotal = 0;
   $('#aragingInvoicePagination').DataTable( { 
    		"processing": true,
        "serverSide": true,
        "searching": false,
        "ajax": $.fn.dataTable.pipeline( {
            url: url,
            method: 'POST',
            pages: 10
        }),
        "columns": [
        	{ "data": "referId" },
        	{ "data": "orgName" },
            { "data": "billDate"},
            { "data": "dueDate"},
            { "data": "dueAmount" },
            { "data": "totalAmount"},
        ],
        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"data": "referId",
	    	  	"render": function(data, type, opt, meta) {
	    	  		var result = "";
					result = result + ' <td><div class="tblminwidth-75">'+opt.referId+'</div></td>';
		             return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "orgName",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
						result = result + ' <td><div class="tblminwidth-75">'+opt.orgName+'</div></td>';
	              return result;
              		
              }
	          },{
            "targets": colIndex++,
            "data": "billDate",
            "render": function ( data, type, opt, meta ) {
            	var result = "";
            	if(opt.billDate != null && opt.billDate !='' && opt.billDate !='undefined' ){
            		
            		result = result+  '<td><div class="tblminwidth-75" title="'+opt.billDate+'">'+opt.billDate+'</div></td>';
            		}else{
            			 result = result + '<td><div class="tblminwidth-75">--</div></td>';
            		}
            	  return result;
            }
          }, {
              "targets": colIndex++,
              "data": "dueDate",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
              	if(opt.dueDate != null && opt.dueDate !='' && opt.dueDate !='undefined' ){
              		 result = result+ '<td><div class="tbldata-dotdot-vsm" title="'+opt.dueDate+'">'+opt.dueDate+'</div></td>';
              	}else{
              		result = result + '<td><div class="tbldata-dotdot-vsm"---</div></td>';
              	}
            	 return result;
              }
            }, {
                "targets": colIndex++,
                "data": "dueAmount",
                "render": function ( data, type, opt, meta ) {
                		var result = "";
							result = result + ' <td><div class="tbldata-dotdot-sm"">'+opt.dueAmount.toFixed(2)+'</div></td>';
    	              return result;
                }
          },{
	    	  	"targets": colIndex++,
	    	  	"data": "totalAmount",
	    	  	"render": function(data, type, opt, meta) {
	    	  		var result = "";
					result = result + '<td><div class="tblminwidth-50">'+opt.totalAmount.toFixed(2)+'</div></td>';
					apagingInvoiceTotal = parseFloat(apagingInvoiceTotal)+parseFloat(opt.totalAmount);
               	   $("#apagingInvoiceTotal").html(apagingInvoiceTotal.toFixed(2));
  	              return result;
	    	  	}
	    	  }],
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"aaSorting": [[0,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
    } );
    $("#aragingInvoicePagination").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $("#dataTableInvoiceSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   aragingInvoice();
    	   }
    	});
	
}
</script>

<script>
//customdates search
$(function(){
$("#custom-dates").change(function() {
var customDates = $(this).val();
if (customDates === "custom") {
$("#custom-select").show();
}else {
$("#custom-select").hide();
}
});
})
</script>

 <!-- Invoice End -->

<jsp:include page="../masterFooter.jsp" />