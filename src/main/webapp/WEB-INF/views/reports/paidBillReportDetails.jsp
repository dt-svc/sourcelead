<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<script>
$(document).ready(function(){
  getPaidBillDetailReport();
  var financeType = '${billType}';
  if(financeType === "Payables"){
  document.getElementById("invoiceIdBr").style.display="none";
  document.getElementById("BillIdBr").style.display="block"; 
  }else{
  document.getElementById("invoiceIdBr").style.display="block";
  document.getElementById("BillIdBr").style.display="none"; 
  }
});

</script>




<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>${orgName }</h2>
      </div>
      <div class="pull-right brud-crum" style="display: none;" id="BillIdBr"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/reportsDashboard'/>">Reports Dashboard</a> &raquo; <a href="<c:url value='${appPath}/financialReportsList'/>">Financial Reports</a> &raquo; Paid Bill(s)</div>
	<div class="pull-right brud-crum" style="display: none;" id="invoiceIdBr"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/reportsDashboard'/>">Reports Dashboard</a> &raquo; <a href="<c:url value='${appPath}/financialReportsList'/>">Financial Reports</a> &raquo; Received Invoice(s)</div>
    </section>
    <!-- hiring Process  -->
    <div class="content-dashboard clearfix">
      <div class="ReqRepHead clearfix hidden">
        <div class="col-md-5 pull-left reqListBtn">
        <a href="financial-paidbills.html" title="Graphical View" class="btn btn-default"><i class="fa fa-pie-chart"></i></a>
        <a href="financial-paidbills-list.html" title="List View" class="btn btn-default rAct"><i class="fa fa-list"></i></a>
        </div>
        
        <div class="col-md-2 dropdown-select noPad">
          <select class="form-control">
            <option>Select Vendor</option>
            <option selected>Vendor 1</option>
            <option>Vendor 2</option>
            <option>Vendor 3</option>
            <option>Vendor 4</option>
            <option>Vendor 5</option>
            <option>Vendor 6</option>
          </select>
          
        </div>         
        
        
        <div class="col-md-2 dropdown-select">
          <select class="form-control">
            <option>Select Year</option>
            <option selected>2018</option>
            <option>2019</option>
            <option>1 month</option>
            <option>3 months</option>
            <option>YTD</option>
            <option>Last Year</option>
          </select>
          
        </div>          
        
        <div class="col-md-2 dropdown-select positionRelative noPad">
          <select class="form-control" id="custom-dates">
            <option>Select Date</option>
            <option selected>Last 7 days</option>
            <option>Last 15 days</option>
            <option>1 month</option>
            <option>3 months</option>
            <option>YTD</option>
            <option>Last Year</option>
            <option value="custom">Custom</option>
          </select>
          
          <!-- Top Leave Takers Custom Date -->
          <div class="clearfix pull-lefet col-md-12 paddingRight customLeaves" id="customLeaves" style="display: none; width: 346px;">
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
        
        <div class="col-md-1">
          <div class="btn-group bootstrap_dropdownMenu dropdownPadding">
            <button type="button" class="btn btn-primary btn-sm" title="Actions"><i class="glyphicon glyphicon-export"></i></button>
            <button type="button" class="btn btn-primary dropdown-toggle btn-sm" data-toggle="dropdown"> <span class="caret"></span> </button>
            <ul class="dropdown-menu clearfix  dropdown-menu-right">
              <li role="Export" class="mt5 cursorPointer"><i class="fa fa-file-pdf-o paddingRight"></i>Export</li>
            </ul>
          </div>
        </div>
      </div>
      <!-- Best recruiters  -->
      <div class="content-dashboard clearfix"> 
        <!-- Assigned Requirements -->
        <div class="clearfix dashboard-widget col-md-12">
          <div class="clearfix minHeight40">
          <h4 class="pull-left">Total : <span id ="totalCalAmount">0.00</span></h4>
          	<div class="remove_search pull-right">
                  <input placeholder="Search" type="text" id='dataTableSearch'>
                </div>
          </div>
          <div id="paidBillReportDetailId" class="datatable_filter tableStyles">
              </div>
          
            
          
        </div>
        <!-- Pagenation Ends --> 
      </div>
      <!-- Best recruiters Ends  --> 
      
    </div>
  </section>


<script>
///////////////////// Back To Top 
$(document).ready(function() {
    $(window).scroll(function() {
        if ($(this).scrollTop() > 200) {
            $('.backtotop').fadeIn('slow');
        } else {
            $('.backtotop').fadeOut('slow');
        }
    });
    $('.backtotop').click(function() {
        $("html, body").animate({scrollTop: 0}, 500);
        return false;
    });
});
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
    $('#priority').trigger('chosen:updated');
</script> 

<!-- Applicant Source AmCharts javascript code --> 
 
<script>
$(document).ready(function(){
	$(".cliHead").click(function(){
		$(".tableStyles").toggle();
	});
});

</script>




<script>
function getPaidBillDetailReport(){
	    var orgRelationId = '${orgRelationId}';
		var sourceleadUrl ='${sourceleadUrl}';
		var billType ='${billType}';
		$("#totalCalAmount").html("0.00"); // reset total value
		var searchValue = $("#dataTableSearch").val();
		searchValue = searchValue ? searchValue : "";
		var url = sourceleadUrl+"/getPaidBillDetailReport?"+tokenParamName+"="+tokenValue+"&orgRelationId="+orgRelationId+"&billType="+billType+"&searchValue="+searchValue;
		getPaidBillDetailReportList(url);
}
</script>
<script>
function getPaidBillDetailReportList(url){
	$("#paidBillReportDetailId").empty();
		var financeType = '${billType}';
 	    var result = "";
 	    /* result = result +" <div class='remove_search'>";
 	    result = result +" <input type='text' placeholder='Search' id='dataTableSearch'/>";
 	    result = result +"</div>"; */
 	    result = result +"<table id='paidBillDetailPagination' class='display' cellspacing='0' width='100%' >"; 
        result = result +"<thead>"; 
        result = result + '<tr>';
        result = result + '<th>Project Name</th>';
		if(financeType === "Payables"){
 		result = result + '<th>Bill Date</th>';
        }else{
        result = result + '<th>Invoice Date</th>';	
       }
		result = result + '<th>Pay Date</th>';
		result = result + '<th>Amount</th>';
		result = result + '</tr>';
        result = result +"</thead>";
        result = result +"<tbody>"; 
        result = result +" </tbody>";
        result = result +"</table>";
      $("#paidBillReportDetailId").append(result);
      paidBillDetailReportList(url); 
}
</script>

<script>
function paidBillDetailReportList(url){
	var colIndex = 0;
	var apagingTotal = 0;
    $('#paidBillDetailPagination').DataTable( { 
    	"processing": true,
        "serverSide": true,
        "searching": false,
        "ajax": $.fn.dataTable.pipeline( {
            url: url,
            method: 'POST',
            pages: 10
        }),
        "columns": [
        	{ "data": "projectName" },
            { "data": "billDate" },
            { "data": "payDate"},
            { "data": "amount"}
        ],
        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"data": "projectName",
	    	  	"sortable": false,
	    	  	"render": function(data, type, opt, meta) {
	    	  		var result = "";
	    	  		if(opt.projectName != null && opt.projectName != '' && opt.projectName != 'undefined'){
	    	  			result = result + ' <td><div class="tblminwidth-75">'+opt.projectName+'</div></td>'; 
	    	  		}else{
	    	  			result = result + ' <td><div class="tblminwidth-75">--</div></td>'; 
	    	  		}
	    	  		 
					/*  result = result + ' <td><div class="tblminwidth-75">'+opt.orgName+'</div></td>';   setProjectName  setBillDate setPayDate setTotalAmount */
		             return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "billDate",
              "sortable": false,
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
						 result = result + ' <td><div class="tblminwidth-75">'+opt.billDate+'</div></td>'; 
						 if(opt.totalCalAmount >0 )
				    	  		$("#totalCalAmount").html(opt.totalCalAmount.toFixed(2));
	              return result;
              		
              }
	          },{
            "targets": colIndex++,
            "data": "payDate",
            "sortable": false,
            "render": function ( data, type, opt, meta ) {
            	  var result = "";
					if(opt.payDate != null && opt.payDate != '' && opt.payDate != 'undefined'){
						result = result + ' <td><div class="tblminwidth-75">'+opt.payDate+'</div></td>';
					}else{
						result = result + ' <td><div class="tblminwidth-75">--</div></td>';
					}
            	  
               return result;
            }
          }, {
              "targets": colIndex++,
              "data": "amount",
              "sortable": false,
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
					 result = result + ' <td><div class="tblminwidth-75">'+opt.amount+'</div></td>'; 
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
    $("#apagingPagination").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $("#dataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   getPaidBillDetailReport();
    	   }
    	});
    
}
</script>


<jsp:include page="../masterFooter.jsp" />