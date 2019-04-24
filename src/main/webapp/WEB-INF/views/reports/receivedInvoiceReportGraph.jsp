<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<script>
$(document).ready(function(){
  getPaidInvoiceGraphReport();
  getTotalAmountPerYear();
});
</script>


 <!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Received Invoice(s)</h2>
      </div>
       <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/reportsDashboard'/>">Reports Dashboard</a> &raquo;  <a href="<c:url value='${appPath}/financialReportsList'/>">Financial Reports</a> &raquo; Received Invoice(s) </div>
    </section>
    <!-- hiring Process  -->
    <div class="content-dashboard clearfix">
      <div class="ReqRepHead clearfix">
        <div class="col-md-2 pull-left reqListBtn">
        <a href="${appPath}/paidInvoiceReportGraph" title="Graphical View" class="btn btn-default rAct"><i class="fa fa-pie-chart"></i></a>
        <a href="${appPath}/receivedInvoiceReport" title="List View" class="btn btn-default"><i class="fa fa-list"></i></a>
        </div>
        
        <div class="col-md-4 dropdown-select col-md-offset-1">
          <select class="chosen-select form-control" multiple data-placeholder="Select Client" name="orgId" id="orgId">
           <c:if test="${not empty clientsList}">
               <c:forEach items="${clientsList}" var="client">
   						<option value='${client.id }'>${client.orgName}</option>
				</c:forEach>
		  </c:if>
          </select>
          
        </div>         
        
        <div class="col-md-2 dropdown-select positionRelative noPad">
         <select class="form-control" id="filterType" name="filterType" onChange="getPaidBillsCustomDates();">
          <option value="last_7days">Last 7 days</option> 
          <option value="last_15days">Last 15 days</option>
          <option value="1month">1 month</option>
          <option value="3months">3 months</option>
          <option value="last_years" >last year</option>
          <option value="custom">Custom</option>
          </select>
          
          <!-- Top Leave Takers Custom Date -->
          <form:form id="paidbillgraph_custom_form" name="paidbillgraph_custom_form" method="post">
          <div class="clearfix pull-lefet col-md-12 paddingRight customLeaves" id="customSearch" style="display: none; width: 346px;">
            <div class="pull-left">
              <div class="pull-left mr5" style="width: 140px" id="fromErrDate">
                <input value="" name="Date" id="fromDate" placeholder="From" class="form-control calendar input-sm" type="text" data-validation="required" field-name="From Date">
              </div>
              <div class="pull-left mr5" style="width: 140px" id="toErrDate">
                <input value="" name="Date" id="toDate" placeholder="To" class="form-control calendar input-sm" type="text" data-validation="required" field-name="To Date">
              </div>
              <button type="button" class="btn btn-primary btn-sm" onclick="getPaidBillGraphCustomReport();"><i class="fa fa-search"></i></button>
            </div>
          </div>
          </form:form>
          <!-- Top Leave Takers Custom Date --> 
          
        </div>              
         <div class="col-md-2" id="paidBillreportGraph">
        <button type="button" class="btn btn-primary btn-sm	mt2" onclick="getPaidInvoiceGraphReport();">Run</button>
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
      <!-- Decline Reasons -->
      <div class="row">
        <div class="col-md-12 mt">
          <div class="dashboard-widget">
            <h4>Received Invoices</h4>
			  <div class="row mt">
				  <div class="col-md-9">
				  	<div id="invoicePaidDiv" style="width:100%; height: 250px; margin-bottom: 25px;" class="amchartAnchor-Hide"></div>
				  </div>
				  <div class="col-md-3 padding_left">
					<div class="pyexpenses-table">
					   <h5>Amount Paid</h5>
						<div id="abc"></div>
					   </div>
				  </div>
			  </div>
          </div>
        </div>
      </div>
      <!-- Decline Reasons -->
      
      <!-- Best recruiters  -->
      <div class="content-dashboard clearfix hidden"> 
        <!-- Assigned Requirements -->
        <div class="clearfix dashboard-widget mt25 col-md-12">
          <div class="clearfix">
          <h4 class="pull-left">Applicant Backout Lists</h4>
          	<div class="remove_search pull-right">
                  <input placeholder="Search" type="text">
                </div>
          </div>
          <!-- <div class="clearfix require_wrap_table dashboard-data">
            <table class="tableBor">
              <thead>
                <tr>
                  <th>Jobs</th>
                  <th>Current Company</th>
                  <th>Compensation</th>
                  <th>Better Offer</th>
                  <th>Cold Feet</th>
                  <th>Personal</th>
                  <th>Other</th>
                  <th>Total</th>
                </tr>
                <tr>
				  <td><div>Jobs</div></td>
                  <td><div>Current Company</div></td>
                  <td><div>Compensation</div></td>
                  <td><div>Better Offer</div></td>
                  <td><div>Cold Feet</div></td>
                  <td><div>Personal</div></td>
                  <td><div>Other</div></td>
                  <td><div>200</div></td> 
                  </tr>
              </thead>
              <tbody>
              </tbody>
            </table>
            Pagenation
            <nav class="clearfix">
              <ul id="paging" class="pagination pull-right">
                <li><a href="#">Previous</a></li>
                <li class="active"><a href="#">1</a></li>
                <li><a href="#">2</a></li>
                <li><a href="#">3</a></li>
                <li><a href="#">4</a></li>
                <li><a href="#">5</a></li>
                <li data-page="last"><a href="#">Next</a></li>
              </ul>
            </nav>
          </div>
        </div>
        Pagenation Ends 
      </div> -->
      <!-- Best recruiters Ends  --> 
      
    </div>
  </section>
  <!-- Content Area Ends--> 

  


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
function getPaidInvoiceGraphReport(){
	    var billType = 'Receivable';
		var sourceleadUrl ='${sourceleadUrl}';
		var orgId = $("#orgId").val(); 
		var filterType = ""; 
		if($("#filterType").val() != 'undefined'){
			filterType = $("#filterType").val();
		}
	    $.post(sourceleadUrl+"/getPaidBillGraphReport?"+tokenParamName+"="+tokenValue+"&billType="+billType+"&orgId="+orgId+"&filterType="+filterType, function(json) {
	    	
	    	var data = json.data;
	    	if(data != null){
	    		preparePaidInvoiceGraph(data);
	    	}
		});  
}

</script>


<script type="text/javascript">
	
function preparePaidInvoiceGraph(data){
	
	 var chartData = [];
	   for (var i = 0; i < data.length; i++) { 
	 		 var opt = data[i]; 
	         chartData.push({
	        	 "category": opt.orgName,
	        	 "column-1": opt.totalAmount.toFixed(2),
	        	 "color": "#9FA8DA"
	       });
	         
	   }  
	AmCharts.makeChart("invoicePaidDiv",
			{
			"type": "serial",
			"categoryField": "category",
			"rotate": true,
			"marginBottom": 10,
			"marginLeft": 10,
			"marginRight": 50,
			"marginTop": 10,
			"startDuration": 1,
			"export": {
			"enabled": true
			},
			"categoryAxis": {
			"gridPosition": "start",
			"position": "left",
			"axisThickness": 0,
			"gridThickness": 0,
			"title": ""
			},
			"trendLines": [],
			"graphs": [
			{
			"bulletHitAreaSize": -2,
			"colorField": "color",
			"fillAlphas": 1,
			"id": "AmGraph-1",
			"labelText": "[[value]]",
			"lineColor": "#000000",
			"lineColorField": "color",
			"title": "graph 1",
			"type": "column",
			"valueField": "column-1"
			}
			],
			"guides": [],
			"valueAxes": [
			{
			"axisFrequency": 0,
			"id": "ValueAxis-1",
			"totalText": "",
			"unit": "",
			"axisThickness": 0,
			"gridThickness": 0,
			"labelsEnabled": false,
			"title": ""
			}
			],
			"allLabels": [],
			"balloon": {
			"fixedPosition": false
			},
			"titles": [],
			"dataProvider": chartData
			}
			);
}
	
</script>


<script>
//custom search
function getPaidBillsCustomDates() {
	 var dateSelection = $("#filterType").val(); 
	 if(dateSelection == 'custom'){
		 document.getElementById("customSearch").style.display="block";
		 document.getElementById("paidBillreportGraph").style.display="none";
		 //$("#jobReportList_custome").attr("disabled", "disabled");
		
	 }else{
		 document.getElementById("customSearch").style.display="none";
		 document.getElementById("paidBillreportGraph").style.display="block";
		 //$("#jobReportList_custome").removeAttr("disabled");
		 $("#fromDate").val('');
		 $("#toDate").val('');
		 document.getElementById("fromDateDiv").style.display="none";
		 document.getElementById("toDateDiv").style.display="none";
	 }
}
</script> 


<script type="text/javascript">
	function getPaidBillGraphCustomReport() {
		if ($('#paidbillgraph_custom_form').validate(false, validationSettings)) {
		  	var formData = $($('#paidbillgraph_custom_form')[0]).serialize();
		  	 var billType = 'Receivable';
				var sourceleadUrl ='${sourceleadUrl}';
				var searchValue = $("#dataTableSearch").val();
				var orgId = $("#orgId").val();
				var filterType = ""; 
				if($("#filterType").val() != 'undefined'){
					filterType = $("#filterType").val();
				}
				var fromDate = $("#fromDate").val();
				var toDate = $("#toDate").val();
				searchValue = searchValue ? searchValue : "";
				$.post(sourceleadUrl+"/getPaidBillGraphReport?"+tokenParamName+"="+tokenValue+"&billType="+billType+"&orgId="+orgId+"&filterType="+filterType+"&fromDate="+fromDate+"&toDate="+toDate, function(json) {
			    	var data = json.data;
			    	if(data != null){
			    		preparePaidInvoiceGraph(data);
			    	}
				});  
			return true;
		} else {
			return false;
		}
	}
	$('body').on('blur', '#paidbillgraph_custom_form', function() {
		$("#paidbillgraph_custom_form").showHelpOnFocus();
		$("#paidbillgraph_custom_form").validateOnBlur(false, validationSettings);
	});
</script> 


<script>
  $( function() {
		/*  var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
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
				todate();
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
				fromdate();
			}
		});
  } );
  
  function todate(){
	  $('#fromErrDate').find('p.jquery_form_error_message').remove(); 
  }
  
  function fromdate(){
	  $('#toErrDate').find('p.jquery_form_error_message').remove(); 
  }
  </script>

<script>
function getTotalAmountPerYear(){
	  var billType = 'Receivable';
		var orgId = $("#orgId").val(); 
		var filterType = ""; 
		if($("#filterType").val() != 'undefined'){
			filterType = $("#filterType").val();
		}
	    $.post(sourceleadUrl+"/getTotalAmountPerYear?"+tokenParamName+"="+tokenValue+"&billType="+billType+"&orgId="+orgId+"&filterType="+filterType, function(json) {
	    	
	    	if(json != null){
	    		  if(json.totalAmountMap != null){
	    			 var result = "";
	    			 result = result+ ' <table class="clearfix mt tableFixed table">';
	    			 result = result+ '<thead>';
	    			 result = result+ '<tr>';
	    			 result = result+ '<th class="col-xs-6">Year</th>';
	    			 result = result+ '<th class="col-xs-6">Amount</th>';
	    			 result = result+ '</tr>';
	    			 result = result+ '</thead>';
	    			 result = result+ '<tbody>';
	    		    	 for(var k in json.totalAmountMap) {
	    		    		 result = result+ '<tr>';
	    		    		 result = result+ '<td class="col-xs-6">'+k+'</td>';
	    		    		 result = result+ '<td class="col-xs-6">'+json.totalAmountMap[k].toFixed(2)+'</td>';
	    		    		 result = result+ ' </li>';
	    		    		 result = result+ '</tr>';
	    		    	    }
	    		    	 result = result+ '</tbody>';
	    		    	 result = result+ '</table>';
	    		    	 $("#abc").empty();
	    		    	 $("#abc").append(result);
	    		    }
	    		
	    	}
		});  
}
</script>


<jsp:include page="../masterFooter.jsp" />