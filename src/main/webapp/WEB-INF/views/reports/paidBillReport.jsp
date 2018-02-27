<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<script>
$(document).ready(function(){
  getPaidBillreport();
});

</script>




<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Paid Bills</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Paid Bills</div>
    </section>
    <!-- hiring Process  -->
    <div class="content-dashboard clearfix">
      <div class="ReqRepHead clearfix">
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
        <div class="clearfix dashboard-widget mt25 col-md-12">
          <div class="clearfix">
          <h4 class="pull-left">Paid Bills Lists</h4>
          	<div class="remove_search pull-right">
                  <input placeholder="Search" type="text">
                </div>
          </div>
          
          
           <div class='remove_search'>
 		 <input type='text' placeholder='Search' id='dataTableSearch'/>
 		</div> 
      <div id="paidBillReportId" class="datatable_filter">
        </div>
          
         <!--  <h3 class="cliHead">Vendor 1<i class="fa fa-caret-down"></i><span class="pull-right mr">Total: <label>200000</label></span> </h3>
          <div class="clearfix tableStyles">
            <table class="table table-bordered">
              <thead>
                <tr>
                  <th>Project Name</th>
                  <th>Bill date</th>
                  <th>Pay date</th>
                  <th>Amount</th>
                </tr>
                </thead>
                <tbody>
                  <tr>
					  <td><div>Business Intelli</div></td>
					  <td><div>02/08/2018 - 02/08/2018</div></td>
					  <td><div>02/08/2018 - 02/08/2018</div></td>
					  <td><div>546541</div></td>
                  </tr>
                  <tr>
					  <td><div>Business Intelli</div></td>
					  <td><div>02/08/2018 - 02/08/2018</div></td>
					  <td><div>02/08/2018 - 02/08/2018</div></td>
					  <td><div>546541</div></td>
                  </tr>                                  
              </tbody>
            </table>
          </div> -->
          
          <!-- <h3 class="cliHead">Vendor 2<i class="fa fa-caret-down"></i><span class="pull-right mr">Total: <label>200000</label></span> </h3>
          <div class="clearfix tableStyles">
            <table class="table table-bordered">
              <thead>
                <tr>
                  <th>Project Name</th>
                  <th>Bill date</th>
                  <th>Pay date</th>
                  <th>Amount</th>
                </tr>
                </thead>
                <tbody>
                  <tr>
					  <td><div>Business Intelli</div></td>
					  <td><div>02/08/2018 - 02/08/2018</div></td>
					  <td><div>02/08/2018 - 02/08/2018</div></td>
					  <td><div>546541</div></td>
                  </tr>
                  <tr>
					  <td><div>Business Intelli</div></td>
					  <td><div>02/08/2018 - 02/08/2018</div></td>
					  <td><div>02/08/2018 - 02/08/2018</div></td>
					  <td><div>546541</div></td>
                  </tr>                                   
              </tbody>
            </table>
            Pagenation

          </div>   -->        
          
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
/*		$(this).next().find("tableStyles").slideToggle(100);*/
		$(".tableStyles").toggle();``
	});
});

</script>


<!-- <script>
function getPaidBillreport(){
	    var billType = 'Payables';
		var sourceleadUrl ='${sourceleadUrl}';
	    $.post(sourceleadUrl+"/getPaidBillreport?"+tokenParamName+"="+tokenValue+"&billType="+billType, function(json) {
	    	alert(json);
		});  
}
</script> -->





<script>
function getPaidBillreport(){
	    var billType = 'Payables';
		var sourceleadUrl ='${sourceleadUrl}';
		var searchValue = $("#dataTableSearch").val();
		searchValue = searchValue ? searchValue : "";
		var url = sourceleadUrl+"/getPaidBillreport?"+tokenParamName+"="+tokenValue+"&billType="+billType;
		preparePaidBillReportList(url);
}
</script>
<script>
function preparePaidBillReportList(url){
	$("#paidBillReportId").empty();
 	    var result = "";
 	    /* result = result +" <div class='remove_search'>";
 	    result = result +" <input type='text' placeholder='Search' id='dataTableSearch'/>";
 	    result = result +"</div>"; */
 	    result = result +"<table id='paidBillPagination' class='display' cellspacing='0' width='100%' >"; 
        result = result +"<thead>"; 
        result = result + '<tr>';
        result = result + '<th>Vendor Name</th>';
		result = result + '<th>No.Of Associates</th>';
		result = result + '<th>No.Of Projects</th>';
		result = result + '<th>Total Amount</th>';
		result = result + '</tr>';
        result = result +"</thead>";
        result = result +"<tbody>"; 
        result = result +" </tbody>";
        result = result +"</table>";
      $("#paidBillReportId").append(result);
      apaging(url); 
}
</script>

<script>
function apaging(url){
	var colIndex = 0;
	var apagingTotal = 0;
    $('#paidBillPagination').DataTable( { 
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
            { "data": "numberOfAssociates" },
            { "data": "numberOfprojects"},
            { "data": "totalAmount"}
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
              "data": "numberOfAssociates",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
						 result = result + ' <td><div class="tblminwidth-75">'+opt.numberOfAssociates+'</div></td>'; 
	              return result;
              		
              }
	          },{
            "targets": colIndex++,
            "data": "numberOfprojects",
            "render": function ( data, type, opt, meta ) {
            	  var result = "";
					 result = result + ' <td><div class="tblminwidth-75">--</div></td>';
               return result;
            }
          }, {
              "targets": colIndex++,
              "data": "totalAmount",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
					 result = result + ' <td><div class="tblminwidth-75">'+opt.totalAmount.toFixed(2)+'</div></td>'; 
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
    		   getPaidBillreport();
    	   }
    	});
    
	
}
</script>


<jsp:include page="../masterFooter.jsp" />