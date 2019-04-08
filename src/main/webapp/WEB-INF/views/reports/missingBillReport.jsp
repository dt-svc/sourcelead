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
	
	missingBillReportData();
});
</script>

 <!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Missing Bill(s)</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/reportsDashboard'/>">Reports Dashboard</a> &raquo; <a href="<c:url value='${appPath}/financialReportsList'/>">Financial Reports</a> &raquo; Missing Bill(s)</div>
    </section>
    <!-- hiring Process  -->
    <div class="content-dashboard clearfix">
      <div class="ReqRepHead clearfix">
        <div class="col-md-3 pull-left reqListBtn">
        <a href='${appPath}/missingBillGraphReport' title="Graphical View" class="btn btn-default"><i class="fa fa-pie-chart"></i></a>
        <a href='${appPath}/missingBillReport' title="List View" class="btn btn-default rAct"><i class="fa fa-list"></i></a>
        </div>
        <div class="col-md-4">
          <select class="chosen-select form-control" multiple data-placeholder="Select Vendor" id="orgIds" name="orgIds">
            <c:if test="${not empty vendorList}">
               <c:forEach items="${vendorList}" var="vendor">
   						<option value='${vendor.id }'>${vendor.orgName}</option>
				</c:forEach>
		</c:if>
          </select>
        </div>
        <div class="col-md-2 dropdown-select positionRelative">
          <select class="form-control" id="filterType" name="filterType" onchange="getMissingBillCustomDates();">
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
                <input value="" name="fromDate" id="fromDate" placeholder="From" class="form-control calendar input-sm" type="text" readonly>
                 <p id="fromDateDiv" style="display: none; font-size: 12px;"><font color="red">From Date Required</font></p>
              </div>
              <div class="pull-left mr5" style="width: 140px">
                <input value="" name="toDate" id="toDate" placeholder="To" class="form-control calendar input-sm" type="text" readonly>
                <p id="toDateDiv" style="display: none; font-size: 12px;"><font color="red">To Date Required</font></p>
              </div>
              <button type="button" class="btn btn-primary btn-sm" onclick="missingBillReportData();"><i class="fa fa-search"></i></button>
            </div>
          </div>
          <!-- Top Leave Takers Custom Date --> 
          
        </div>
         <div class="col-md-2">
        <button type="button" class="btn btn-primary btn-sm	mt2" onclick="missingBillReportData();">Run</button>
		</div>
   
      </div>
      <!-- Best recruiters  -->
      <div class="content-dashboard clearfix"> 
        <!-- Assigned Requirements -->
        <div class="clearfix dashboard-widget mt25 col-md-12">
          <div class="clearfix">
          <p class="col-md-2 mb0 margin-top0 padding_left fontSize20">
         	 	<span class="text-muted">Total:</span><span class="ml"id="billAmountTotal"><strong>0.00</strong></span>
         	 </p>
          	<div class="remove_search pull-right">
                  <input placeholder="Search" type="text" id='dataTableSearch'>
                </div>
          </div>
          
          <div class="clearfix tableStyles mt5 datatable_filter">
         <div id="missingBillData"  ></div>
          </div>
        </div>
        <!-- Pagenation Ends --> 
      </div>
      <!-- Best recruiters Ends  --> 
    </div>
  </section>
  <!-- Content Area Ends--> 
  
  
  
  <script>
function missingBillReportData(){
	
	   $("#billAmountTotal").html("0.00"); // reset the total value when applied filters.
	    var flag = true;
		var sourceleadUrl ='${sourceleadUrl}';
		var searchValue = $("#dataTableSearch").val();
		searchValue = searchValue ? searchValue : "";
		var invoiceType = "bill";
		var filterType = $("#filterType").val();
		if(filterType == 'undefined'){
			filterType = "";
		}
		var orgIds = $("#orgIds").val();
		var contractType = "vendorSow";
		
		  var dateSelection = $("#filterType").val(); 
		  var fromDate = '';
		  var toDate = '';
		  if(dateSelection == 'custom'){
			   fromDate = $("#fromDate").val();
			   toDate = $("#toDate").val();
			  
			  if(fromDate == '' || toDate == ''){
				  flag = false;
				  if(fromDate == ''){
					  document.getElementById("fromDateDiv").style.display="block";
				  }else{
					  document.getElementById("fromDateDiv").style.display="none";
				  }
				  if(toDate == ''){
					  document.getElementById("toDateDiv").style.display="block";
				  }else{
					  document.getElementById("toDateDiv").style.display="none";
				  }
			  }else{
				  document.getElementById("fromDateDiv").style.display="none";
				  document.getElementById("toDateDiv").style.display="none";
			  }
			  
		  }
		  if(flag){
			  var url = sourceleadUrl+"/missingInvoiceData?searchValue="+searchValue+"&invoiceType="+invoiceType+"&filterType="+filterType+"&orgIds="+orgIds+"&fromDate="+fromDate+"&toDate="+toDate+"&contractType="+contractType;
				prepareMissingBillData(url); 
		  }
		
}
</script>
<script>
function prepareMissingBillData(url){
	   $("#missingBillData").empty();
 	    var result = "";
 	    /* result = result +" <div class='remove_search'>";
 	    result = result +" <input type='text' placeholder='Search' id='dataTableSearch'/>";
 	    result = result +"</div>"; */
 	    result = result +"<table id='missingInvoicePagination' class='display' cellspacing='0' width='100%' >"; 
        result = result +"<thead>"; 
        result = result + '<tr>';
        result = result + '<th>Refer Id</th>'; 
        result = result + '<th>Candidate Name</th>';
		result = result + '<th>Vendor Name</th>';
		result = result + '<th>Project Name</th>';
		result = result + '<th>Project Frequency</th>';
		result = result + '<th>Date</th>';
		result = result + '<th>Total Amount</th>';
// 		result = result + '<th>Action</th>';
		result = result + '</tr>';
        result = result +"</thead>";
        result = result +"<tbody>"; 
        result = result +" </tbody>";
        result = result +"</table>";
        $("#missingBillData").append(result);
        missingBill(url); 
}
</script>

<script>
function missingBill(url){
	var colIndex = 0;
	/* var total = 0; */
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
            { "data": "totalAmount" },
//             { "data": "action"}
        ],
        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"data": "referId",
	    		"sortable": false,
	    	  	"render": function(data, type, opt, meta) {
	    	  		var result = "";
	    	  		if(opt.invoiceReferId != null && opt.invoiceReferId != '' && opt.invoiceReferId != 'undefined'){
	    	  			 result = result + ' <td><div class="tblminwidth-50">'+opt.invoiceReferId+'</div></td>';
	    	  		}else{
	    	  			 result = result + ' <td><div class="tblminwidth-50">--</div></td>';
	    	  		}
	    	  		if(opt.totalCalAmount >0 )
	    	  		$("#billAmountTotal").html(opt.totalCalAmount.toFixed(2));
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
						 result = result + ' <td><div class="tbldata-dotdot-vsm inlineBlock">'+opt.orgName+'</div></td>'; 
	              return result;
              		
              }
	          },{
            "targets": colIndex++,
            "data": "projectName",
        	"sortable": false,
            "render": function ( data, type, opt, meta ) {
            	  var result = "";
					 result = result + ' <td><div class="tbldata-dotdot-vsm inlineBlock">'+opt.projectName+'</div></td>'; 
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
            }, {
                "targets": colIndex++,
                "data": "date",
            	  "sortable": false,
                "render": function ( data, type, opt, meta ) {
              	  var result = "";
              	  if(opt.billDate !=null && opt.billDate != '' && opt.billDate != 'undefined'){
              		  result = result + ' <td><div class="tblminwidth-50">'+opt.billDate+'</div></td>'; 
              	  }else{
              		  result = result + ' <td><div class="tblminwidth-50">--</div></td>'; 
              	  }
  					
                   return result;
                }
              }, {
                "targets": colIndex++,
                "data": "totalAmount",
            	"sortable": false,
                "render": function ( data, type, opt, meta ) {
                		var result = "";
                	
                		if(opt.billAmount !=null && opt.billAmount !='' && opt.billAmount != 'undefined'){
                			 result = result + ' <td><div class="tblminwidth-75">'+opt.billAmount.toFixed(2)+'</div></td>';	
                			
                		}else{
                			 result = result + ' <td><div class="tblminwidth-75">0.00</div></td>';
                		}
    	              return result;
                }
          }],/* {
	    	  	"targets": colIndex++,
	    	  	"data": "action",
	    		"sortable": false,
	    	  	"render": function(data, type, opt, meta) {
	    	  		var result = "";
					 result = result + '<td><div class="tblminwidth-50">Action</div></td>';
  	              return result;
	    	  	}
	    	  } ], */
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
	     },
    } );
    $("#missingInvoicePagination").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    
}

$(function() {
    $("#dataTableSearch").keyup(function(e) {
    	if(e.which === 13) {
//  		   $('#dataTableSearch').parent().find('p.jquery_form_error_message').remove();
	    	var searchData = $('#dataTableSearch').val();
// 	    	var regex =  new RegExp(/[\\-]/g);
// 	    	if(regex.test(searchData) == false){
  		        missingBillReportData();
	    	/* }else{
	    		$("#dataTableSearch").after("<p class='jquery_form_error_message'><span><font color='red'>Enter valid Search Data</font></span>");
	    	}
	    	$('#dataTableSearch').val(searchData); */
 	   }
    	});
});


</script>


<script>
//custom search
function getMissingBillCustomDates() {
	 var dateSelection = $("#filterType").val(); 
	 if(dateSelection == 'custom'){
		 document.getElementById("customSearch").style.display="block";
		 $("#jobReportList_custome").attr("disabled", "disabled");
		 
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
				removeErrMsg();
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
				removeErrMsgTo();
			}
		});
	  
		 
	 }else{
		 document.getElementById("customSearch").style.display="none";
		 $("#jobReportList_custome").removeAttr("disabled");
		 $("#fromDate").datepicker("destroy");
		 $("#toDate").datepicker("destroy");
		 $("#fromDate").val('');
		 $("#toDate").val('');
		 document.getElementById("fromDateDiv").style.display="none";
		 document.getElementById("toDateDiv").style.display="none";
	 }
}
</script>  

<script>
function removeErrMsg(){
	  document.getElementById("fromDateDiv").style.display="none";
}
function removeErrMsgTo(){
	  document.getElementById("toDateDiv").style.display="none";
}
</script>
<jsp:include page="../masterFooter.jsp" />