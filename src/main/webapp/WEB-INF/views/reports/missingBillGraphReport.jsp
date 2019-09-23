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
	missingBillDataGraph();
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
    <div class="clearfix">
      <div class="ReqRepHead clearfix">
    		      <div class="col-md-2 pull-left reqListBtn">
          <a href='${appPath}/missingBillGraphReport' title="List View" id="GView" class="btn btn-default rAct"><i class="fa fa-pie-chart"></i></a>
          <a href='${appPath}/missingBillReport' title="List View" id="LView" class="btn btn-default"><i class="fa fa-list"></i></a>
    		      </div>
     		<!--<a href="jobsPipline-list.html" class="btn btn-primary">List</a>-->
       
<!--         <div class="col-md-4 col-md-offset-1"> -->
    <%-- <div class="dropdown-scroll">
		<select class="chosen-select form-control" multiple data-placeholder="Select Vendor" id="orgIds" name="orgIds">
		 <c:if test="${not empty vendorList}">
               <c:forEach items="${vendorList}" var="vendor">
   						<option value='${vendor.id }'>${vendor.orgName}</option>
				</c:forEach>
		</c:if>
		</select>
	</div> --%>
<!--         </div> -->
         <div class="col-md-4">
          <select class="chosen-select form-control" multiple data-placeholder="Select Vendor" id="orgIds" name="orgIds">
            <c:if test="${not empty vendorList}">
               <c:forEach items="${vendorList}" var="vendor">
   						<option value='${vendor.id }'>${vendor.orgName}</option>
				</c:forEach>
		</c:if>
          </select>
        </div>
        <div class="col-md-2 dropdown-select">
        <div class="dropdown-select positionRelative">
        <select class="form-control" id="customDates" onchange="getMissingBillgraphDates();" >
          <option value="last_7days">Last 7 days</option> 
          <option value="last_15days">Last 15 days</option>
          <option value="1month">1 month</option>
          <option value="3months">3 months</option>
          <option value="last_years">last year</option>
          <option value="custom">Custom</option>
        </select>
        <!-- Top Leave Takers Custom Date -->
    <div class="clearfix pull-lefet col-md-12 paddingRight customLeaves" id="custom-select" style="display: none; width: 346px;">
    <div class="pull-left">
    <div class="pull-left mr5" style="width: 140px">
    <input value="" name="fromDate" id="fromDate" placeholder="From" class="form-control calendar input-sm" type="text">
    </div>
    <div class="pull-left mr5" style="width: 140px">
    <input value="" name="toDate" id="toDate" placeholder="To" class="form-control calendar input-sm" type="text">
    </div>
    <button type="button" class="btn btn-primary btn-sm" onclick="missingBillDataGraph();"><i class="fa fa-search"></i></button>
    </div>
    </div>
<!-- Top Leave Takers Custom Date --> 
      </div>

        </div>
        <div class="col-md-2">
        <button type="button" class="btn btn-primary btn-sm	mt2" onclick="missingBillDataGraph();" id="customizeButton">Run</button>
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

			<div class="content-area clearfix mt">
			<div class="row">
			<div class="col-md-3">
				<div class="clearfix billing_count mt25 text-center">
                      <h4><span class="rupee_usd mt10 mb10 mr5">Total:</span><span id="total">0.00</span></h4>
                </div>
                <div class="clearfix"></div>
               <!--  <ul class="list-unstyled mt text-center ulDesing">
                	<li><label class="tblminwidth-50">2017:</label><span>20,0000.00<b class="paddingLeft">(50%)</b></span></li>
                	<li><label class="tblminwidth-50">2016:</label><span>20,0000.00<b class="paddingLeft">(50%)</b></span></li>
                	<li><label class="tblminwidth-50">2015:</label><span>20,0000.00<b class="paddingLeft">(50%)</b></span></li>
                	<li><label class="tblminwidth-50">2014:</label><span>20,0000.00<b class="paddingLeft">(50%)</b></span></li>
                	<li><label class="tblminwidth-50">2013:</label><span>20,0000.00<b class="paddingLeft">(50%)</b></span></li>
                	<li><label class="tblminwidth-50">2012:</label><span>20,0000.00<b class="paddingLeft">(50%)</b></span></li>
                	<li><label class="tblminwidth-50">2011:</label><span>20,0000.00<b class="paddingLeft">(50%)</b></span></li>
                	<li><label class="tblminwidth-50">2010:</label><span>20,0000.00<b class="paddingLeft">(50%)</b></span></li>
                </ul> -->
		</div>
				<div class="col-md-9">
				<div id="last_years" style="width: 100%; height: 375px;" class="amchartAnchor-Hide"></div>
				<div id="last_7days" style="width: 100%; height: 375px; display: none;" class="amchartAnchor-Hide"></div>
				<div id="last_15days" style="width: 100%; height: 375px; display: none;" class="amchartAnchor-Hide"></div>
				<div id="1month" style="width: 100%; height: 375px; display: none;" class="amchartAnchor-Hide"></div>
				<div id="3months" style="width: 100%; height: 375px; display: none;" class="amchartAnchor-Hide"></div>
				</div>
				</div>
			</div>
  </section>
  <!-- Content Area Ends--> 
  
  
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

<script>
function missingBillDataGraph(){
	      var invoiceType = "bill";
		  var sourceleadUrl ='${sourceleadUrl}';
		  var contractType = "vendorSow";
		  var filterType = $("#customDates").val();
			if(filterType == 'undefined'){
				filterType = "";
			}
			var fromDate = $("#fromDate").val();
			var toDate = $("#toDate").val();
	       $.post(sourceleadUrl+"/missingInvoiceDataGraph?&invoiceType="+invoiceType+"&contractType="+contractType+"&filterType="+filterType+"&fromDate="+fromDate+"&toDate="+toDate, function(json) {
	    	var data = json.data;
	    	if(data != null){
	    		prepareapMissingBillGraph(data);
	    	}
		});  
}
</script>



<script>
function prepareapMissingBillGraph(data){
			var customDates = $("#customDates").val();
			if (customDates === "custom-select") {
				$("#custom-select").show();
				$("#last_7days").hide();
				$("#last_15days").hide();
				$("#1month").hide();
				$("#3months").hide();
				$("#last_years").show();
			}else if (customDates === "last_7days") {
				$("#custom-select").hide();
				$("#last_7days").show();
				$("#last_15days").hide();
				$("#1month").hide();
				$("#3months").hide();
				$("#last_years").hide();
				laset7Days(data);
			}else if (customDates === "last_15days") {
				$("#custom-select").hide();
				$("#last_7days").hide();
				$("#last_15days").show();
				$("#1month").hide();
				$("#3months").hide();
				$("#last_years").hide();
				laset15Days(data);
			}else if (customDates === "1month") {
				$("#custom-select").hide();
				$("#last_7days").hide();
				$("#last_15days").hide();
				$("#1month").show();
				$("#3months").hide();
				$("#last_years").hide();
				oneMonth(data);
			}else if (customDates === "3months") {
				$("#custom-select").hide();
				$("#last_7days").hide();
				$("#last_15days").hide();
				$("#1month").hide();
				$("#3months").show();
				$("#last_years").hide();
				threeMonths(data);
			}else if (customDates === "last_years") {
				$("#custom-select").hide();
				$("#last_7days").hide();
				$("#last_15days").hide();
				$("#1month").hide();
				$("#3months").hide();
				$("#last_years").show();
				lastYearGraph(data);
			}else{
				$("#custom-select").hide();
				$("#last_7days").hide();
				$("#last_15days").hide();
				$("#1month").hide();
				$("#3months").hide();
				$("#last_years").show();
				lastYearGraph(data);
			}
		
	}
</script>


<script>
function  lastYearGraph(data){
	var total = 0;
	 var chartData = [];
	   for (var i = 0; i < data.length; i++) { 
	 		 var opt = data[i]; 
	         chartData.push({
	        	 "date": opt.invoiceDate,
	        	 "column-1": opt.invoiceAmount
	       });
	         
	         total = parseFloat(total)+parseFloat(opt.invoiceAmount);
	   }  
	   $("#total").html(total.toFixed(2));
	   
	AmCharts.makeChart("last_years",
			{
			"type": "serial",
			"categoryField": "date",
			"dataDateFormat": "YYYY-MM",
			"marginBottom": 15,
			"marginLeft": 15,
			"marginRight": 15,
			"marginTop": 15,
			"zoomOutButtonImageSize": 15,
			"addClassNames": true,
			"borderColor": "#FFFFFF",
			"theme": "default",
			"responsive": {
				"enabled": true
			},
			"export": {
				"enabled": true,
				"fileName":"Missing Bills"
			},
			"categoryAxis": {
				"minPeriod": "MM",
				"parseDates": true,
				"twoLineMode": true,
				"axisThickness": 0,
				"centerLabels": true,
				"gridThickness": 0,
				"markPeriodChange": false,
				"minHorizontalGap": 0,
				"minVerticalGap": 0
			},
			"trendLines": [],
			"graphs": [
				{
					"balloonText": " [[category]]  <br>Amount:[[value]]",
					"fillAlphas": 1,
					"fillColors": "#EF9A9A",
					"fixedColumnWidth": 20,
					"id": "total",
					"lineColor": "#EF9A9A",
					"title": "Total",
					"type": "column",
					"valueField": "column-1"
				}
			],
			"guides": [],
			"valueAxes": [
				{
					"axisTitleOffset": 15,
					"id": "invoiceValueAxis",
					"axisThickness": 0,
					"color": "#737373",
					"gridThickness": 0,
					"labelsEnabled": true,
					"title": ""
				}
			],
			"allLabels": [],
			"balloon": {},
			"legend": {
				"enabled": true,
				"align": "center",
				"markerLabelGap": 10,
				"markerSize": 13
			},
			"titles": [
				{
					"id": "missingBills",
					"size": 15,
					"text": "Missing Bills"
				}
			],
			"dataProvider": chartData
		}
		);
	
}

</script>

<script>
function laset7Days(data){
	//invoice weekly
	
	var total = 0;
	 var chartData = [];
	   for (var i = 0; i < data.length; i++) { 
	 		 var opt = data[i]; 
	         chartData.push({
	        	 "date": opt.invoiceDate,
	        	 "column-1": opt.invoiceAmount
	       });
	         
	         total = parseFloat(total)+parseFloat(opt.invoiceAmount);
	   }  
	   $("#total").html(total.toFixed(2));
	   
	AmCharts.makeChart("last_7days",
			{
			"type": "serial",
			"categoryField": "date",
			"dataDateFormat": "YYYY-MM-DD",
			"marginBottom": 15,
			"marginLeft": 15,
			"marginRight": 15,
			"marginTop": 15,
			"zoomOutButtonImageSize": 15,
			"addClassNames": true,
			"borderColor": "#FFFFFF",
			"theme": "default",
			"responsive": {
				"enabled": true
			},
			"export": {
				"enabled": true
			},
			"categoryAxis": {
				"parseDates": true,
				"twoLineMode": true,
				"axisThickness": 0,
				"centerLabels": true,
				"gridThickness": 0,
				"markPeriodChange": false,
				"minHorizontalGap": 0,
				"minVerticalGap": 0
			},
			"trendLines": [],
			"graphs": [
				{
					"balloonText": "[[category]]<br>Amount:[[value]]",
					"fillAlphas": 1,
					"fillColors": "#EF9A9A",
					"fixedColumnWidth": 20,
					"id": "total",
					"lineColor": "#EF9A9A",
					"title": "Total",
					"type": "column",
					"valueField": "column-1"
				},
			],
			"guides": [],
			"valueAxes": [
				{
					"axisTitleOffset": 15,
					"id": "invoiceValueAxis",
					"axisThickness": 0,
					"color": "#737373",
					"gridThickness": 0,
					"labelsEnabled": true,
					"title": ""
				}
			],
			"allLabels": [],
			"balloon": {},
			"legend": {
				"enabled": true,
				"align": "center",
				"markerLabelGap": 10,
				"markerSize": 13
			},
			"titles": [
				{
					"id": "last_years",
					"size": 15,
					"text": "Weekly Missing Bills"
				}
			],
			"dataProvider": chartData
		}
		);
}

</script>

<script>
function laset15Days(data){
	//last_15days
	 var total = 0;
	 var chartData = [];
	   for (var i = 0; i < data.length; i++) { 
	 		 var opt = data[i]; 
	         chartData.push({
	        	 "date": opt.invoiceDate,
	        	 "column-1": opt.invoiceAmount
	       });
	         
	         total = parseFloat(total)+parseFloat(opt.invoiceAmount);
	   }  
	   $("#total").html(total.toFixed(2));
	   
	AmCharts.makeChart("last_15days",
	{
	"type": "serial",
	"categoryField": "date",
	"dataDateFormat": "YYYY-MM-DD",
	"marginBottom": 15,
	"marginLeft": 15,
	"marginRight": 15,
	"marginTop": 15,
	"zoomOutButtonImageSize": 15,
	"addClassNames": true,
	"borderColor": "#FFFFFF",
	"theme": "default",
	"responsive": {
		"enabled": true
	},
	"export": {
		"enabled": true
	},
	"categoryAxis": {
		"parseDates": true,
		"twoLineMode": true,
		"axisThickness": 0,
		"centerLabels": true,
		"gridThickness": 0,
		"markPeriodChange": false,
		"minHorizontalGap": 0,
		"minVerticalGap": 0
	},
	"trendLines": [],
	"graphs": [
		{
			"balloonText": " [[category]]  <br>Amount:[[value]]",
			"fillAlphas": 1,
			"fillColors": "#EF9A9A",
			"fixedColumnWidth": 20,
			"id": "total",
			"lineColor": "#EF9A9A",
			"title": "Total",
			"type": "column",
			"valueField": "column-1"
		}
	],
	"guides": [],
	"valueAxes": [
		{
			"axisTitleOffset": 15,
			"id": "invoiceValueAxis",
			"axisThickness": 0,
			"color": "#737373",
			"gridThickness": 0,
			"labelsEnabled": true,
			"title": ""
		}
	],
	"allLabels": [],
	"balloon": {},
	"legend": {
		"enabled": true,
		"align": "center",
		"markerLabelGap": 10,
		"markerSize": 13
	},
	"titles": [
		{
			"id": "last_years",
			"size": 15,
			"text": "15Days Missing Bills"
		}
	],
	"dataProvider":chartData
}
);
}
</script>


<script>
function oneMonth(data){
	//1month
	var total = 0;
	 var chartData = [];
	   for (var i = 0; i < data.length; i++) { 
	 		 var opt = data[i]; 
	         chartData.push({
	        	 "date": opt.invoiceDate,
	        	 "column-1": opt.invoiceAmount
	       });
	         
	         total = parseFloat(total)+parseFloat(opt.invoiceAmount);
	   }  
	   $("#total").html(total.toFixed(2));
	   
	AmCharts.makeChart("1month",
	{
	"type": "serial",
	"categoryField": "date",
	"dataDateFormat": "YYYY-MM-DD",
	"marginBottom": 15,
	"marginLeft": 15,
	"marginRight": 15,
	"marginTop": 15,
	"zoomOutButtonImageSize": 15,
	"addClassNames": true,
	"borderColor": "#FFFFFF",
	"theme": "default",
	"responsive": {
		"enabled": true
	},
	"export": {
		"enabled": true
	},
	"categoryAxis": {
		"autoRotateAngle": 45,
		"autoRotateCount": 0,
		"parseDates": true,
		"twoLineMode": true,
		"axisThickness": 0,
		"centerLabels": true,
		"gridThickness": 0,
		"markPeriodChange": false,
		"minHorizontalGap": 0,
		"minVerticalGap": 0
	},
	"trendLines": [],
	"graphs": [
		{
			"balloonText": " [[category]]  <br>Amount:[[value]]",
			"fillAlphas": 1,
			"fillColors": "#EF9A9A",
			"fixedColumnWidth": 20,
			"id": "total",
			"lineColor": "#EF9A9A",
			"title": "Total",
			"type": "column",
			"valueField": "column-1"
		}
	],
	"guides": [],
	"valueAxes": [
		{
			"axisTitleOffset": 15,
			"id": "invoiceValueAxis",
			"axisThickness": 0,
			"color": "#737373",
			"gridThickness": 0,
			"labelsEnabled": true,
			"title": ""
		}
	],
	"allLabels": [],
	"balloon": {},
	"legend": {
		"enabled": true,
		"align": "center",
		"markerLabelGap": 10,
		"markerSize": 13
	},
	"titles": [
		{
			"id": "last_years",
			"size": 15,
			"text": "1 Month Missing Bills"
		}
	],
	"dataProvider": chartData
}
);
}
</script>


<script>
function threeMonths(data){
	//3months
	
	  var total = 0;
	 var chartData = [];
	   for (var i = 0; i < data.length; i++) { 
	 		 var opt = data[i]; 
	         chartData.push({
	        	 "date": opt.invoiceDate,
	        	 "column-1": opt.invoiceAmount
	       });
	         
	         total = parseFloat(total)+parseFloat(opt.invoiceAmount);
	   }  
	   $("#total").html(total.toFixed(2));
	   
	AmCharts.makeChart("3months",
	{
	"type": "serial",
	"categoryField": "date",
	"dataDateFormat": "YYYY-MM",
	"marginBottom": 15,
	"marginLeft": 15,
	"marginRight": 15,
	"marginTop": 15,
	"zoomOutButtonImageSize": 15,
	"addClassNames": true,
	"borderColor": "#FFFFFF",
	"theme": "default",
	"responsive": {
		"enabled": true
	},
	"export": {
		"enabled": true
	},
	"categoryAxis": {
		"minPeriod": "MM",
		"parseDates": true,
		"twoLineMode": true,
		"axisThickness": 0,
		"centerLabels": true,
		"gridThickness": 0,
		"markPeriodChange": false,
		"minHorizontalGap": 0,
		"minVerticalGap": 0
	},
	"trendLines": [],
	"graphs": [
		{
			"balloonText": " [[category]]  <br>Amount:[[value]]",
			"fillAlphas": 1,
			"fillColors": "#EF9A9A",
			"fixedColumnWidth": 20,
			"id": "total",
			"lineColor": "#EF9A9A",
			"title": "Total",
			"type": "column",
			"valueField": "column-1"
		}
	],
	"guides": [],
	"valueAxes": [
		{
			"axisTitleOffset": 15,
			"id": "invoiceValueAxis",
			"axisThickness": 0,
			"color": "#737373",
			"gridThickness": 0,
			"labelsEnabled": true,
			"title": ""
		}
	],
	"allLabels": [],
	"balloon": {},
	"legend": {
		"enabled": true,
		"align": "center",
		"markerLabelGap": 10,
		"markerSize": 13
	},
	"titles": [
		{
			"id": "last_years",
			"size": 15,
			"text": "Three Months Missing Bills"
		}
	],
	"dataProvider": chartData
}
);
}
</script>

<script>
//custom search

function getMissingBillgraphDates() {
	document.getElementById("customizeButton").style.display="block";
	 var dateSelection = $("#customDates").val();
	 if(dateSelection == 'custom'){
		 document.getElementById("custom-select").style.display="block";
		 $("#jobReportList_custome").attr("disabled", "disabled");
		 document.getElementById("customizeButton").style.display="none";
	 }else{
		 document.getElementById("custom-select").style.display="none";
		 $("#jobReportList_custome").removeAttr("disabled");
		 $("#fromDate").val('');
		 $("#toDate").val('');
		 document.getElementById("fromDateDiv").style.display="none";
		 document.getElementById("toDateDiv").style.display="none";
		 document.getElementById("customizeButton").style.display="block";
	 }
}
</script> 

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

<jsp:include page="../masterFooter.jsp" />