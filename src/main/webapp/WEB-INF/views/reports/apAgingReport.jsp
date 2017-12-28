<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<script type="text/javascript">
$(document).ready(function(){
	vendorapAging();
});
</script>


 <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>A/P Aging List</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; A/P Agings List</div>
    </section>
    <div class="clearfix"></div>
    <div class="ReqRepHead clearfix">
      <div class="col-md-3 pull-left reqListBtn">
          <a href='${appPath}/apAgingGraphReport' title="Graph View" id="GView" class="btn btn-default"><i class="fa fa-pie-chart"></i></a>
          <a href='${appPath}/apAgingReport' title="List View" id="LView" class="btn btn-default"><i class="fa fa-list"></i></a>
     </div>
      <div class="col-md-2 dropdown-select positionRelative col-md-offset-4 hidden">
        <select class="form-control" id="custom-datedds">
          <option>Select Date</option>
          <option selected>Last 7 days</option>
          <option>Last 15 days</option>
          <option>1 month</option>
          <option>3 months</option>
          <option>last year</option>
          <option value="custom">Custom</option>
        </select>
        <!-- Top Leave Takers Custom Date -->
        <div class="clearfix pull-lefet col-md-12 paddingRight customLeaves" id="custom-selecddt" style="display: none; width: 346px;">
          <div class="pull-left">
            <div class="pull-left mr5" style="width: 140px">
              <input value="" name="Date" id="datepicker4" placeholder="From" class="form-control calendar input-sm" type="text">
            </div>
            <div class="pull-left mr5" style="width: 140px">
              <input value="" name="Date" id="datepicker2" placeholder="To" class="form-control calendar input-sm" type="text">
            </div>
            <button type="button" class="btn btn-primary btn-sm"><i class="fa fa-search"></i></button>
          </div>
        </div>
        <!-- Top Leave Takers Custom Date --> 
      </div>
      <div class="col-md-1 col-md-offset-8">
        <div class="btn-group bootstrap_dropdownMenu dropdownPadding">
          <button type="button" class="btn btn-primary btn-sm" title="Actions"><i class="fa fa-retweet"></i></button>
          <button type="button" class="btn btn-primary dropdown-toggle btn-sm" data-toggle="dropdown"> <span class="caret"></span> </button>
          <ul class="dropdown-menu clearfix  dropdown-menu-right">
            <li role="Export" class="mt5 cursorPointer"><i class="fa fa-file-pdf-o paddingRight"></i>Export</li>
            <!--
						<li role="Print" class="mt5 cursorPointer"><i class="fa fa-print paddingRight"></i>Print</li>
						<li role="Download" class="mt5 cursorPointer"><i class="fa fa-download paddingRight"></i>Download</li>
						<li role="Share" class="mt5 mb5 cursorPointer"><i class="fa fa-share-alt paddingRight"></i>Share</li>
-->
          </ul>
        </div>
      </div>
    </div>
    <div class="clearfix"></div>
      <div class="vendor_wrap mt">
        <div class="tableStyles">
         <div class="clearfix table_head">
        	 
        	 <div class="col-md-offset-4 select-fild col-md-2 mt5">
                    <select id="apagingType" name="apagingType" onchange="change();">
                          <option value="Payables">By Vendor</option>
                          <option value="bill">By Bill</option>
                    </select>
             </div>
             <div class="col-md-2 dropdown-select positionRelatives mt5" style="display: none" id="customDatesDropdown">
        <select class="form-control" id="custom-dates"  name="filterDateType" onchange="vendorapagingBill();">
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
              <input value="" name="Date" id="datepicker4" placeholder="From" class="form-control calendar input-sm" type="text">
            </div>
            <div class="pull-left mr5" style="width: 125px">
              <input value="" name="Date" id="datepicker2" placeholder="To" class="form-control calendar input-sm" type="text">
            </div>
            <button type="button" class="btn btn-primary btn-sm">Submit</button>
          </div>
        </div>
        <!-- Top Leave Takers Custom Date --> 
      </div>
        	 
         	 <p class="col-md-2 form-control-static">
         	 	<label class="mt5 text-muted">Total:</label><span class="ml mt5"><strong>250</strong></span>
         	 </p>
         </div>
         <div class="clearfix"></div>
         <div class='remove_search'>
 		 <input type='text' placeholder='Search' id='dataTableBillSearch'/>
 		</div>
         <div id="apagingVendorByBill" class="datatable_filter">
          
        </div>
        
        <div class="clearfix"></div>
       <div class='remove_search'>
 		 <input type='text' placeholder='Search' id='dataTableSearch'/>
 		</div> 
      <div id="apagingByVendor" class="datatable_filter">
        </div>
        </div>
        </div>
  </section>
		
		
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
				})
				$(document).ready(function(){
					$("#cd").click(function(){
						$(".clientDetails").toggleClass('hidden', 500);
					});
	
	});
 </script> 
 
 
 <script>
$(function() {
        $('#custom-dates').change(function(){
			var optval = $(this).val();
			if(optval === "custom") {
				$("#custom-select").show();
			}else{
				$("#custom-select").hide();
			}
        });
    });
</script>


<script>
function change(){
	var optval = $("#apagingType").val();
	if(optval === "bill") {
		vendorapagingBill();
	}else{
		vendorapAging();
	}
}
</script>

<script>
function vendorapAging(){
	   $("#apagingVendorByBill").empty();	
	   $("#dataTableBillSearch").hide();
	   $("#dataTableSearch").show();
	    var apagingType = $("#apagingType").val();
		var sourceleadUrl ='${sourceleadUrl}';
		var searchValue = $("#dataTableSearch").val();
		searchValue = searchValue ? searchValue : "";
		var url = sourceleadUrl+"/vendorapAging?searchValue="+searchValue+"&apagingType="+apagingType;
		prepareApagingList(url);
	    /* $.post(sourceleadUrl+"/vendorapAging?"+tokenParamName+"="+tokenValue+"&apagingType="+apagingType, function(data) {
			prepareApagingList();
		});  */
}
</script>
<script>
function prepareApagingList(url){
	   $("#apagingByVendor").empty();
 	    var result = "";
 	    /* result = result +" <div class='remove_search'>";
 	    result = result +" <input type='text' placeholder='Search' id='dataTableSearch'/>";
 	    result = result +"</div>"; */
 	    result = result +"<table id='apagingPagination' class='display' cellspacing='0' width='100%' >"; 
        result = result +"<thead>"; 
        result = result + '<tr>';
        result = result + '<th>Vendor</th>';
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
      $("#apagingByVendor").append(result);
      apaging(url); 
}
</script>

<script>
function apaging(url){
	var colIndex = 0;
    $('#apagingPagination').DataTable( { 
    		"processing": true,
        "serverSide": true,
        "searching": false,
        "ajax": $.fn.dataTable.pipeline( {
            url: url,
            method: 'POST',
            pages: 10
        }),
        "columns": [
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
        ],
        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"data": "orgName",
	    	  	"render": function(data, type, opt, meta) {
	    	  		var result = "";
					result = result + ' <td><div class="tblminwidth-75">'+opt.orgName+'</div></td>';
		             return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "tenDays",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
						result = result + ' <td><div class="tblminwidth-75">'+opt.tenDays.toFixed(2)+'</div></td>';
	              return result;
              		
              }
	          },{
            "targets": colIndex++,
            "data": "fifteenDays",
            "render": function ( data, type, opt, meta ) {
            	return  '<td><div class="tblminwidth-75">'+opt.fifteenDays.toFixed(2)+'</div></td>';
            }
          }, {
              "targets": colIndex++,
              "data": "thirtyDays",
              "render": function ( data, type, opt, meta ) {
            	  return '<td><div class="tbldata-dotdot-vsm" title="'+opt.thirtyDays+'">'+opt.thirtyDays.toFixed(2)+'</div></td>';
              }
            }, {
                "targets": colIndex++,
                "data": "sixtyDays",
                "render": function ( data, type, opt, meta ) {
                		var result = "";
							result = result + ' <td><div class="tbldata-dotdot-sm"">'+opt.sixtyDays.toFixed(2)+'</div></td>';
    	              return result;
                }
          },{
	    	  	"targets": colIndex++,
	    	  	"data": "nintyDays",
	    	  	"render": function(data, type, opt, meta) {
	    	  		var result = "";
					result = result + '<td><div class="tblminwidth-50">'+opt.nintyDays.toFixed(2)+'</div></td>';
  	              return result;
	    	  	}
	    	  },{
	    	  	"targets": colIndex++,
	    	  	"data": "oneeightyDays",
	    	  	"render": function(data, type, opt, meta) {
	    	  		var result = "";
	    	  			result = result + ' <td><div class="tblminwidth-75">'+opt.oneeightyDays.toFixed(2)+'</div></td>';
	    	  		return result;
	    	  	}
	    	  }, {
                  "targets": colIndex++,
                  "data": "threeSixtyFiveDays",
                  "render": function ( data, type, opt, meta ) {
                	  return '<td><div class="tblminwidth-75">'+opt.threeSixtyFiveDays.toFixed(2)+'</div></td>';
                  }
                },{
                    "targets": colIndex++,
                    "data": "above365Days",
                    "render": function ( data, type, opt, meta ) {
                    	return '<td><div class="tblminwidth-75">'+opt.above365Days.toFixed(2)+'</div></td>';
                    }
                  },{
                      "targets": colIndex++,
                      "data": "total",
                      "render": function ( data, type, opt, meta ) {
                    	  return ' <td><div class="tblminwidth-75">'+opt.total.toFixed(2)+'</div></td>';
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
    $("#apagingPagination").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $("#dataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   vendorapAging();
    	   }
    	});
	
}
</script>

  <!-- Bill Started -->
  <script>
function vendorapagingBill(){
	$("#apagingByVendor").empty();	
	   $("#dataTableSearch").hide();
	   $("#dataTableBillSearch").show();
	     var apagingType = 'Payables';
		var sourceleadUrl ='${sourceleadUrl}';
		var filterType = $("#custom-dates").val(); 
		alert(filterType);
		var searchValue = $("#dataTableBillSearch").val();
		var filterDateType = $("#filterDateType").val();
		searchValue = searchValue ? searchValue : "";
		var url = sourceleadUrl+"/apagingBill?searchValue="+searchValue+"&apagingType="+apagingType+"&filterType="+filterType;
		if(filterType != 'custom'){
			prepareApagingBillList(url);
		}
		
}
</script>
<script>
function prepareApagingBillList(url){
	   $("#apagingVendorByBill").empty();
 	    var result = "";
 	   /*  result = result +" <div class='remove_search'>";
	    result = result +" <input type='text' placeholder='Search' id='dataTableBillSearch'/>";
	    result = result +"</div>"; */
 	    result = result +"<table id='apagingBillPagination' class='display' cellspacing='0' width='100%' >"; 
        result = result +"<thead>"; 
        result = result + '<tr>';
        result = result + '<th>Refer Id</th>';
		result = result + '<th>Vendor</th>';
		result = result + '<th>Bill Date</th>';
		result = result + '<th>Due Date</th>';
		result = result + '<th>Due Amount</th>';
		result = result + '<th>Total Amount</th>';
		result = result + '</tr>';
        result = result +"</thead>";
        result = result +"<tbody>"; 
        result = result +" </tbody>";
        result = result +"</table>";
      $("#apagingVendorByBill").append(result);
      apagingBill(url); 
}
</script>

<script>
function apagingBill(url){
	var colIndex = 0;
    $('#apagingBillPagination').DataTable( { 
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
            	return  '<td><div class="tblminwidth-75">'+opt.billDate+'</div></td>';
            }
          }, {
              "targets": colIndex++,
              "data": "dueDate",
              "render": function ( data, type, opt, meta ) {
            	  return '<td><div class="tbldata-dotdot-vsm" title="'+opt.dueDate+'">'+opt.dueDate+'</div></td>';
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
    $("#apagingBillPagination").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $("#dataTableBillSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   vendorapagingBill();
    	   }
    	});
	
}
</script>
 <script>
$(function() {
        $('#apagingType').change(function(){
			var optval = $(this).val();
			if(optval === "Payables") {
				$("#byInvoice").show();
				$("#byCustomer").hide();
				$("#customDatesDropdown").hide();
			}else if(optval === "bill"){
				$("#byCustomer").show();
				$("#byInvoice").hide();
				$("#customDatesDropdown").show();
			}
        });
    });
</script>
  <!-- Bill Started end -->


<jsp:include page="../masterFooter.jsp" />