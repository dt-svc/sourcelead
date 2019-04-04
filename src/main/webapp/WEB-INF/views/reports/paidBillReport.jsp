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
        <h2>Paid Bill(s)</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/reportsDashboard'/>">Reports Dashboard</a> &raquo; <a href="<c:url value='${appPath}/financialReportsList'/>">Financial Reports</a> &raquo; Paid Bill(s) </div>
    </section>
    <!-- hiring Process  -->
    <div class="content-dashboard clearfix">
      <div class="ReqRepHead clearfix">
        <div class="col-md-2 pull-left reqListBtn">
        <a href="${appPath}/paidBillReportGraph" title="Graphical View" class="btn btn-default"><i class="fa fa-pie-chart"></i></a>
        <a href="${appPath}/paidBillReport" title="List View" class="btn btn-default rAct"><i class="fa fa-list"></i></a>
        </div>
        
        <div class="col-md-4 col-md-offset-1">
          <select class="chosen-select form-control" multiple data-placeholder="Select Vendor" name="orgId" id="orgId">
           <c:if test="${not empty vendorList}">
               <c:forEach items="${vendorList}" var="vendor">
   						<option value='${vendor.id }'>${vendor.orgName}</option>
				</c:forEach>
		  </c:if>
          </select>
          
        </div>         
        
        <div class="col-md-2 dropdown-select positionRelative">
          <select class="form-control" id="filterType" name="filterType" onChange="getPaidBillsCustomDates();">
          <option value="last_7days">Last 7 days</option> 
          <option value="last_15days">Last 15 days</option>
          <option value="1month">1 month</option>
          <option value="3months">3 months</option>
          <option value="last_years" >last year</option>
          <option value="custom">Custom</option>
          </select>
          
          <!-- Top Leave Takers Custom Date -->
          <form:form id="paidbill_custom_form" name="paidbill_custom_form" method="post">
          <div class="clearfix pull-lefet col-md-12 paddingRight customLeaves" id="customSearch" style="display: none; width: 346px;">
            <div class="pull-left">
              <div class="pull-left mr5" style="width: 140px" id="fromDateDiv">
                <input value="" name="Date" id="fromDate" placeholder="From" class="form-control calendar input-sm" type="text" data-validation="required" field-name="From Date" readonly>
              </div>
              <div class="pull-left mr5" style="width: 140px" id="toDateDiv">
                <input value="" name="Date" id="toDate" placeholder="To" class="form-control calendar input-sm" type="text" data-validation="required" field-name="ToDate Name" readonly>
              </div>
              <button type="button" class="btn btn-primary btn-sm" onclick="getPaidBillCustomReport();"><i class="fa fa-search"></i></button>
            </div>
          </div>
          </form:form>
          <!-- Top Leave Takers Custom Date --> 
          
        </div>              
         <div class="col-md-2" id="getPaidBillreport">
        <button type="button" class="btn btn-primary btn-sm	mt2" onclick="getPaidBillreport();">Run</button>
		</div>
        <!-- <div class="col-md-1">
          <div class="btn-group bootstrap_dropdownMenu dropdownPadding">
            <button type="button" class="btn btn-primary btn-sm" title="Actions"><i class="glyphicon glyphicon-export"></i></button>
            <button type="button" class="btn btn-primary dropdown-toggle btn-sm" data-toggle="dropdown"> <span class="caret"></span> </button>
            <ul class="dropdown-menu clearfix  dropdown-menu-right">
              <li role="Export" class="mt5 cursorPointer"><i class="fa fa-file-pdf-o paddingRight"></i>Export</li>
            </ul>
          </div>
        </div> -->
      </div>
      <!-- Best recruiters  -->
      <div class="content-dashboard clearfix"> 
        <!-- Assigned Requirements -->
        <div class="clearfix dashboard-widget mt25 col-md-12">
          <div class="clearfix">
          <h4 class="pull-left">Paid Bills Lists</h4>
          </div>
          
          
      <div id="paidBillReportId" class="datatable_filter mt5">
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
/*		$(this).next().find("tableStyles").slideToggle(100);*/
		$(".tableStyles").toggle();``
	});
});

</script>


<script type="text/javascript">
	function getPaidBillCustomReport() {
		if ($('#paidbill_custom_form').validate(false, validationSettings)) {
		  	var formData = $($('#paidbill_custom_form')[0]).serialize();
		  	 var billType = 'Payables';
				var sourceleadUrl ='${sourceleadUrl}';
				var orgId = $("#orgId").val();
				var filterType = ""; 
				if($("#filterType").val() != 'undefined'){
					filterType = $("#filterType").val();
				}
				var fromDate = $("#fromDate").val();
				var toDate = $("#toDate").val();
				searchValue = searchValue ? searchValue : "";
				var url = sourceleadUrl+"/getPaidBillreport?"+tokenParamName+"="+tokenValue+"&billType="+billType+"&orgId="+orgId+"&filterType="+filterType+"&fromDate="+fromDate+"&toDate="+toDate;
				preparePaidBillReportList(url);
			return true;
		} else {
			return false;
		}
	}
	$('body').on('blur', '#paidbill_custom_form', function() {
		$("#paidbill_custom_form").showHelpOnFocus();
		$("#paidbill_custom_form").validateOnBlur(false, validationSettings);
	});
</script>


<script>
function getPaidBillreport(){
	    var billType = 'Payables';
		var sourceleadUrl ='${sourceleadUrl}';
		var searchValue = $("#dataTableSearch").val();
		var orgId = $("#orgId").val();
		var filterType = ""; 
		if($("#filterType").val() != 'undefined'){
			filterType = $("#filterType").val();
		}
		searchValue = searchValue ? searchValue : "";
		var url = sourceleadUrl+"/getPaidBillreport?"+tokenParamName+"="+tokenValue+"&billType="+billType+"&orgId="+orgId+"&filterType="+filterType+"&searchValue="+searchValue;
		preparePaidBillReportList(url);
}
</script>
<script>
function preparePaidBillReportList(url){
	$("#paidBillReportId").empty();
 	    var result = "";
 	    result = result +" <div class='remove_search'>";
 	    result = result +" <input type='text' placeholder='Search' id='dataTableSearch'/>";
 	    result = result +"</div>";
 	   
 	    result = result +" <div class='tableStyles clearfix'>";
 	    result = result +"<table id='paidBillPagination' class='display' cellspacing='0' width='100%' >"; 
        result = result +"<thead>"; 
        result = result + '<tr>';
        result = result + '<th>Vendor Name</th>';
		result = result + '<th>No.Of Associates</th>';
		result = result + '<th>No.Of Projects</th>';
		result = result + '<th>Total</th>';
		result = result + '</tr>';
        result = result +"</thead>";
        result = result +"<tbody>"; 
        result = result +" </tbody>";
        result = result +"</table>";
        result = result +"</div>";
      $("#paidBillReportId").append(result);
      prepareBill(url); 
}
</script>

<script>
function prepareBill(url){
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
	    	  		result += '<td><div class="tbldata-dotdot"><a href="'+sourceleadUrl+'/paidBillDetails?&orgRelationId='+opt.orgRelationId+'&billType=Payables">'+opt.orgName+'</a> </div></td>';
					/*  result = result + ' <td><div class="tblminwidth-75">'+opt.orgName+'</div></td>';  */ 
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
					 result = result + ' <td><div class="tblminwidth-75">'+opt.numberOfprojects+'</div></td>';
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

<script>
//custom search
function getPaidBillsCustomDates() {
	 var dateSelection = $("#filterType").val(); 
	 if(dateSelection == 'custom'){
		 document.getElementById("customSearch").style.display="block";
		 document.getElementById("getPaidBillreport").style.display="none";
		 //$("#jobReportList_custome").attr("disabled", "disabled");
		 
		  $('#fromDate').datepicker({
				dateFormat : "yy-mm-dd",
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
				dateFormat : "yy-mm-dd",
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
		 document.getElementById("getPaidBillreport").style.display="block";
		 //$("#jobReportList_custome").removeAttr("disabled");
		 $("#fromDate").val('');
		 $("#toDate").val('');
		 $("#fromDate").datepicker("destroy");
		 $("#toDate").datepicker("destroy");
	 }
}
</script>  
 
<script>
function removeErrMsg(){
	  $("#fromDateDiv").find("p.jquery_form_error_message").remove();
}
function removeErrMsgTo(){
	  $("#toDateDiv").find("p.jquery_form_error_message").remove();
}
</script>

<jsp:include page="../masterFooter.jsp" />