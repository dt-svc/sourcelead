<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="/resources/amcharts/amcharts.js"></script>
<script src="/resources/amcharts/serial.js"></script>
<script src="/resources/amcharts/plugins/export/export.min.js"></script>
<link rel="stylesheet" href="/resources/amcharts/plugins/export/export.css" type="text/css" media="all" />

<script type="text/javascript">
$(document).ready(function(){
	missingInvoiceReportData();
});
</script>

 <!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Missing Invoice(s)</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home </a> &raquo; <a href="<c:url value='${appPath}/reportsDashboard'/>">Reports Dashboard</a> &raquo; <a href="<c:url value='${appPath}/financialReportsList'/>">Financial Reports</a> &raquo;  Missing Invoice(s)</div>
    </section>
    <!-- hiring Process  -->
   
    <div class="clearfix">
      <div class="ReqRepHead clearfix">
    		      <div class="col-md-2 pull-left reqListBtn">
          <a href='${appPath}/missingInvoiceGraphReport' title="List View" id="GView" class="btn btn-default"><i class="fa fa-pie-chart"></i></a>
          <a href='${appPath}/missingInvoiceReport' title="List View" id="LView" class="btn btn-default rAct"><i class="fa fa-list"></i></a>
    		      </div>
     		<!--<a href="jobsPipline-list.html" class="btn btn-primary">List</a>-->
       
        <div class="col-md-4 col-md-offset-1">
    <div class="dropdown-scroll">
		<select class="chosen-select form-control" multiple data-placeholder="Select Client" id="orgIds" name="orgIds">
		 <c:if test="${not empty clientList}">
               <c:forEach items="${clientList}" var="client">
   						<option value='${client.id }'>${client.orgName}</option>
		       </c:forEach>
		</c:if>
		</select>
	</div>
        </div>
        <div class="col-md-2 dropdown-select">
        <div class="dropdown-select positionRelative">
        <select class="form-control" id="filterType" name="filterType" onchange="getMissingInvoiceCustomDates();">
          <option value="last_7days">Last 7 days</option> 
          <option value="last_15days">Last 15 days</option>
          <option value="1month">1 month</option>
          <option value="3months">3 months</option>
          <option value="last_years" >last year</option>
          <option value="custom">Custom</option>
        </select>
        <!-- Top Leave Takers Custom Date -->
    <div class="clearfix pull-lefet col-md-12 paddingRight customLeaves" id="customSearch" style="display: none; width: 346px;">
		<div class="pull-left">
		<div class="pull-left mr5" style="width: 140px">
		<input value="" name="fromDate" id="fromDate" placeholder="From" class="form-control calendar input-sm" type="text">
		</div>
		<div class="pull-left mr5" style="width: 140px">
		<input value="" name="toDate" id="toDate" placeholder="To" class="form-control calendar input-sm" type="text">
		</div>
		<button type="button" class="btn btn-primary btn-sm" onclick="missingInvoiceReportData();"><i class="fa fa-search"></i></button>
		</div>
    </div>
<!-- Top Leave Takers Custom Date --> 
      </div>

        </div>
        <div class="col-md-2">
        <button type="button" class="btn btn-primary btn-sm	mt2" onclick="missingInvoiceReportData();">Run</button>
		</div>
		<!-- <div class="col-md-1">
          <div class="btn-group bootstrap_dropdownMenu dropdownPadding">
            <button type="button" class="btn btn-primary btn-sm" title="Actions"><i class="fa fa-download"></i></button>
            <button type="button" class="btn btn-primary dropdown-toggle btn-sm" data-toggle="dropdown"> <span class="caret"></span> </button>
            <ul class="dropdown-menu clearfix  dropdown-menu-right">
              <li role="Export" class="mt5 cursorPointer"><i class="fa fa-file-pdf-o paddingRight"></i>Export</li>
            </ul>
          </div>
        </div> -->
      </div>
    </div>    
     
      <div class="content-dashboard clearfix"> 
        <!-- Assigned Requirements -->
        <div class="clearfix dashboard-widget mt col-md-12">
          <div class="clearfix">
          <p class="col-md-2 mb0 margin-top0 padding_left fontSize20">
         	 	<span class="text-muted">Total:</span><span class="ml" id="total"><strong>0.00</strong></span>
         	 </p>
          	<div class="remove_search pull-right">
                <!--   <input placeholder="Search" type="text"> -->
 	           <input type='text' placeholder='Search' id='dataTableSearch'/>
                </div>
          </div>
          
          
          <div class="clearfix tableStyles mt5 datatable_filter">
         <div id="missingInvoiceData"  ></div>
       
          </div>
          
        </div>
        <!-- Pagenation Ends --> 
      </div>
      
  </section>
  <!-- Content Area Ends--> 
  
  
  
  <script>
function missingInvoiceReportData(){
	  $("#total").html("0.00"); // reset the total value when applied filters.
		var sourceleadUrl ='${sourceleadUrl}';
		var searchValue = $("#dataTableSearch").val();
		searchValue = searchValue ? searchValue : "";
		var invoiceType = "invoice";
		var filterType = $("#filterType").val();
		if(filterType == 'undefined'){
			filterType = "";
		}
		var fromDate = $("#fromDate").val();
		var toDate = $("#toDate").val();
		var orgIds = $("#orgIds").val();
		var contractType = "clientSow";
		var url = sourceleadUrl+"/missingInvoiceData?searchValue="+searchValue+"&invoiceType="+invoiceType+"&filterType="+filterType+"&orgIds="+orgIds+"&fromDate="+fromDate+"&toDate="+toDate+"&contractType="+contractType;
		prepareMissingInvoiceData(url); 
}
</script>
<script>
function prepareMissingInvoiceData(url){
	   $("#missingInvoiceData").empty();
 	    var result = "";
 	    /* result = result +" <div class='remove_search'>";
 	    result = result +" <input type='text' placeholder='Search' id='dataTableSearch'/>";
 	    result = result +"</div>"; */
 	    result = result +"<table id='missingInvoicePagination' class='display' cellspacing='0' width='100%' >"; 
        result = result +"<thead>"; 
        result = result + '<tr>';
        result = result + '<th>Refer Id</th>';
        result = result + '<th>Candidate Name</th>';
		result = result + '<th>Client Name</th>';
		result = result + '<th>Project Name</th>';
		result = result + '<th>Project Frequency</th>';
		result = result + '<th>Date</th>';
		result = result + '<th>Total Amount</th>';
		result = result + '</tr>';
        result = result +"</thead>";
        result = result +"<tbody>"; 
        result = result +" </tbody>";
        result = result +"</table>";
      $("#missingInvoiceData").append(result);
      missingInvoice(url); 
}
</script>

<script>
function missingInvoice(url){
	var colIndex = 0;
	var apagingTotal = 0;
	
    $('#missingInvoicePagination').DataTable( { 
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
        	{ "data": "candidateName"},
            { "data": "orgName" },
            { "data": "projectName"},
            { "data": "frequencyDate"},
            { "data": "date"},
            { "data": "totalAmount"}
        ],
        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"data": "referId",
	    		"sortable": false,
	    	  	"render": function(data, type, opt, meta) {
	    	  		var result = "";
	    	  		if(opt.invoiceReferId != null && opt.invoiceReferId != '' && opt.invoiceReferId != 'undefined'){
	    	  			 result = result + ' <td><div class="tblminwidth-75">'+opt.invoiceReferId+'</div></td>';
	    	  		}else{
	    	  			 result = result + ' <td><div class="tblminwidth-75">--</div></td>';
	    	  		}
	    	  		if(opt.totalCalAmount >0 )
		    	  		$("#total").html(opt.totalCalAmount.toFixed(2));
		             return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "candidateName",
          	  "sortable": false, 
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  result = result + ' <td><div class="tbldata-dotdot-vsm inlineBlock">'+opt.candidateName+'</div></td>'; 
	              return result;
              		
              }
	          },{
	    	  "targets": colIndex++,
              "data": "orgName",
          	  "sortable": false, 
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
						 result = result + ' <td><div class="tblminwidth-75">'+opt.orgName+'</div></td>'; 
	              return result;
              		
              }
	          },{
            "targets": colIndex++,
            "data": "projectName",
        	"sortable": false,
            "render": function ( data, type, opt, meta ) {
            	  var result = "";
					 result = result + ' <td><div class="tblminwidth-75">'+opt.projectName+'</div></td>'; 
                 return result;
            }
          }, {
              "targets": colIndex++,
              "data": "frequencyDate",
          	  "sortable": false,
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  if(opt.frequencyDate !=null && opt.frequencyDate != '' && opt.frequencyDate != 'undefined'){
            		  result = result + ' <td><div class="tblminwidth-75">'+opt.frequencyDate+'</div></td>'; 
            	  }else{
            		  result = result + ' <td><div class="tblminwidth-75">--</div></td>'; 
            	  }
					
                 return result;
              }
            },{
              "targets": colIndex++,
              "data": "date",
          	  "sortable": false,
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  if(opt.invoiceDate !=null && opt.invoiceDate != '' && opt.invoiceDate != 'undefined'){
            		  result = result + ' <td><div class="tblminwidth-75">'+opt.invoiceDate+'</div></td>'; 
            	  }else{
            		  result = result + ' <td><div class="tblminwidth-75">--</div></td>'; 
            	  }
					
                 return result;
              }
            }, {
                "targets": colIndex++,
                "data": "totalAmount",
            	"sortable": false,
                "render": function ( data, type, opt, meta ) {
                		var result = "";
                		var total = 0; 
                		if(opt.invoiceAmount !=null && opt.invoiceAmount !='' && opt.invoiceAmount != 'undefined'){
                			 result = result + ' <td><div class="tbldata-dotdot-sm"">'+opt.invoiceAmount.toFixed(2)+'</div></td>';	
                		}else{
                			 result = result + ' <td><div class="tbldata-dotdot-sm"">0.00</div></td>';
                		}
						
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
    $("#missingInvoicePagination").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $("#dataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   missingInvoiceReportData();
    	   }
    	});
    
	
}
</script>
<script>
//custom search
function getMissingInvoiceCustomDates() {
	 var dateSelection = $("#filterType").val(); 
	 if(dateSelection == 'custom'){
		 document.getElementById("customSearch").style.display="block";
		 $("#jobReportList_custome").attr("disabled", "disabled");
		 
	 }else{
		 document.getElementById("customSearch").style.display="none";
		 $("#jobReportList_custome").removeAttr("disabled");
		 $("#fromDate").val('');
		 $("#toDate").val('');
		 document.getElementById("fromDateDiv").style.display="none";
		 document.getElementById("toDateDiv").style.display="none";
	 }
}
</script>  


<script>
  $( function() {
		 /* var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
		  var finalDateFormatProjectSave = '';
		  if(locationDate.match("yyyy-MM-dd")){
			  finalDateFormatProjectSave = 'yy-mm-dd';
		  }if(locationDate.match("dd-MM-yyyy")){
			  finalDateFormatProjectSave = 'dd-mm-yy'; 
		  }if(locationDate.match("MM-dd-yyyy")){
			  finalDateFormatProjectSave = 'mm-dd-yy'; 
		  }if(locationDate.match("yyyy-dd-MM")){
			  finalDateFormatProjectSave = 'yy-dd-mm'; 
		  } */
    
	  $('#fromDate').datepicker({
			dateFormat : "yy-mm-dd",
			changeMonth: true,
			changeYear: true, 
			onSelect : function(selected) {
				var dt = $(this).datepicker('getDate', '+1d');
				dt.setDate(dt.getDate());
				$("#toDate").datepicker("option", "minDate", dt);
			}
		});
	  
	  $('#toDate').datepicker({
			dateFormat : "yy-mm-dd",
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

<jsp:include page="../masterFooter.jsp" />