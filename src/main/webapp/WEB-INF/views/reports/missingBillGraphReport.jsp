<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript" src="https://www.amcharts.com/lib/3/amcharts.js"></script>
<script type="text/javascript" src="https://www.amcharts.com/lib/3/serial.js"></script>

<script type="text/javascript" src="https://www.amcharts.com/lib/3/plugins/export/export.min.js"></script>
<link rel="stylesheet" href="https://www.amcharts.com/lib/3/plugins/export/export.css">

<script type="text/javascript">
$(document).ready(function(){
	
});
</script>

<!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Missing Bills</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Missing Bills</div>
    </section>
    <!-- hiring Process  -->
    <div class="clearfix">
      <div class="ReqRepHead clearfix">
    		      <div class="col-md-2 pull-left reqListBtn">
          <a href='${appPath}/missingBillGraphReport' title="List View" id="GView" class="btn btn-default rAct"><i class="fa fa-pie-chart"></i></a>
          <a href='${appPath}/missingBillReport' title="List View" id="LView" class="btn btn-default"><i class="fa fa-list"></i></a>
    		      </div>
     		<!--<a href="jobsPipline-list.html" class="btn btn-primary">List</a>-->
       
        <div class="col-md-4 col-md-offset-1">
    <div class="dropdown-scroll">
		<select class="chosen-select form-control" multiple data-placeholder="Select Vendor">
		<option>Client-1</option>
		<option>Client-2</option>
		<option>Client-3</option>
		<option>Client-4</option>
		<option>Client-5</option>
		<option>Client-1</option>
		<option>Client-2</option>
		<option>Client-3</option>
		<option>Client-4</option>
		<option>Client-5</option>
		</select>
	</div>
        </div>
        <div class="col-md-2 dropdown-select">
        <div class="dropdown-select positionRelative">
        <select class="form-control" id="customDates">
          <option>Select Date</option>
          <option value="invoiceweekly">Last 7 days</option>
          <option value="invoice15Days">Last 15 days</option>
          <option value="invoice1Month">1 month</option>
          <option value="invoice3Month">3 months</option>
          <option value="invoices" selected>Last year</option>
          <option>Year to Date</option>
          <option value="custom-select">Custom</option>
        </select>
        <!-- Top Leave Takers Custom Date -->
    <div class="clearfix pull-lefet col-md-12 paddingRight customLeaves" id="custom-select" style="display: none; width: 346px;">
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

        </div>
        <div class="col-md-2">
        <button type="button" class="btn btn-primary btn-sm	mt2">Customize</button>
		</div>
		<div class="col-md-1">
          <div class="btn-group bootstrap_dropdownMenu dropdownPadding">
            <button type="button" class="btn btn-primary btn-sm" title="Actions"><i class="fa fa-download"></i></button>
            <button type="button" class="btn btn-primary dropdown-toggle btn-sm" data-toggle="dropdown"> <span class="caret"></span> </button>
            <ul class="dropdown-menu clearfix  dropdown-menu-right">
              <li role="Export" class="mt5 cursorPointer"><i class="fa fa-file-pdf-o paddingRight"></i>Export</li>
            </ul>
          </div>
        </div>
      </div>
    </div>

			<div class="content-area clearfix mt">
			<div class="row">
			<div class="col-md-3">
				<div class="clearfix billing_count mt25 text-center">
                      <h4><span class="rupee_usd mt10 mb10 mr5">Total:</span>23,5000</h4>
                </div>
                <div class="clearfix"></div>
                <ul class="list-unstyled mt text-center ulDesing">
                	<li><label class="tblminwidth-50">2017:</label><span>20,0000.00<b class="paddingLeft">(50%)</b></span></li>
                	<li><label class="tblminwidth-50">2016:</label><span>20,0000.00<b class="paddingLeft">(50%)</b></span></li>
                	<li><label class="tblminwidth-50">2015:</label><span>20,0000.00<b class="paddingLeft">(50%)</b></span></li>
                	<li><label class="tblminwidth-50">2014:</label><span>20,0000.00<b class="paddingLeft">(50%)</b></span></li>
                	<li><label class="tblminwidth-50">2013:</label><span>20,0000.00<b class="paddingLeft">(50%)</b></span></li>
                	<li><label class="tblminwidth-50">2012:</label><span>20,0000.00<b class="paddingLeft">(50%)</b></span></li>
                	<li><label class="tblminwidth-50">2011:</label><span>20,0000.00<b class="paddingLeft">(50%)</b></span></li>
                	<li><label class="tblminwidth-50">2010:</label><span>20,0000.00<b class="paddingLeft">(50%)</b></span></li>
                </ul>
		</div>
				<div class="col-md-9">
				<div id="invoices" style="width: 100%; height: 375px;" class="amchartAnchor-Hide"></div>
				<div id="invoiceweekly" style="width: 100%; height: 375px; display: none;" class="amchartAnchor-Hide"></div>
				<div id="invoice15Days" style="width: 100%; height: 375px; display: none;" class="amchartAnchor-Hide"></div>
				<div id="invoice1Month" style="width: 100%; height: 375px; display: none;" class="amchartAnchor-Hide"></div>
				<div id="invoice3Month" style="width: 100%; height: 375px; display: none;" class="amchartAnchor-Hide"></div>
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
	$(function(){
		$("#customDates").change(function() {
			var customDates = $(this).val();
			if (customDates === "custom-select") {
				$("#custom-select").show();
				$("#invoiceweekly").hide();
				$("#invoice15Days").hide();
				$("#invoice1Month").hide();
				$("#invoice3Month").hide();
				$("#invoices").show();
			}else if (customDates === "invoiceweekly") {
				$("#custom-select").hide();
				$("#invoiceweekly").show();
				$("#invoice15Days").hide();
				$("#invoice1Month").hide();
				$("#invoice3Month").hide();
				$("#invoices").hide();
			}else if (customDates === "invoice15Days") {
				$("#custom-select").hide();
				$("#invoiceweekly").hide();
				$("#invoice15Days").show();
				$("#invoice1Month").hide();
				$("#invoice3Month").hide();
				$("#invoices").hide();
			}else if (customDates === "invoice1Month") {
				$("#custom-select").hide();
				$("#invoiceweekly").hide();
				$("#invoice15Days").hide();
				$("#invoice1Month").show();
				$("#invoice3Month").hide();
				$("#invoices").hide();
			}else if (customDates === "invoice3Month") {
				$("#custom-select").hide();
				$("#invoiceweekly").hide();
				$("#invoice15Days").hide();
				$("#invoice1Month").hide();
				$("#invoice3Month").show();
				$("#invoices").hide();
			}else if (customDates === "invoices") {
				$("#custom-select").hide();
				$("#invoiceweekly").hide();
				$("#invoice15Days").hide();
				$("#invoice1Month").hide();
				$("#invoice3Month").hide();
				$("#invoices").show();
			}else{
				$("#custom-select").hide();
				$("#invoiceweekly").hide();
				$("#invoice15Days").hide();
				$("#invoice1Month").hide();
				$("#invoice3Month").hide();
				$("#invoices").show();
			}
		});
	})
</script>
<!-- amCharts javascript code --> 
<script type="text/javascript">
	AmCharts.makeChart("invoices",
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
			"id": "missingBills",
			"size": 15,
			"text": "Missing Bills"
		}
	],
	"dataProvider": [
		{
			"date": "2017-01",
			"column-1": "100",
		},
		{
			"date": "2017-02",
			"column-1": "80",
		},
		{
			"date": "2017-03",
			"column-1": "60",
		},
		{
			"date": "2017-04",
			"column-1": "40",
		},
		{
			"date": "2017-05",
			"column-1": "30",
		},
		{
			"date": "2017-06",
			"column-1": "20",
		},
		{
			"date": "2017-07",
			"column-1": "10",
		},
		{
			"date": "2017-08",
			"column-1": "5",
		},
		{
			"date": "2017-09",
			"column-1": "15",
		},
		{
			"date": "2017-10",
			"column-1": "25",
		},
		{
			"date": "2017-11",
			"column-1": "35",
		},
		{
			"date": "2017-12",
			"column-1": "45",
		}
	]
}
);
//invoice weekly	
	AmCharts.makeChart("invoiceweekly",
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
			"id": "invoices",
			"size": 15,
			"text": "Weekly Missing Bills"
		}
	],
	"dataProvider": [
		{
			"date": "2017-01-01",
			"column-1": "80"
		},
		{
			"date": "2017-01-02",
			"column-1": "60"
		},
		{
			"date": "2017-01-03",
			"column-1": "40"
		},
		{
			"date": "2017-01-04",
			"column-1": "30"
		},
		{
			"date": "2017-01-05",
			"column-1": "20"
		},
		{
			"date": "2017-01-06",
			"column-1": "10"
		},
		{
			"date": "2017-01-07",
			"column-1": "5"
		}
	]
}
);
//invoice15Days
	AmCharts.makeChart("invoice15Days",
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
			"id": "invoices",
			"size": 15,
			"text": "15Days Missing Bills"
		}
	],
	"dataProvider": [
		{
			"date": "2017-01-01",
			"column-1": "80",
		},
		{
			"date": "2017-01-02",
			"column-1": "60",
		},
		{
			"date": "2017-01-03",
			"column-1": "40",
		},
		{
			"date": "2017-01-04",
			"column-1": "30",
		},
		{
			"date": "2017-01-05",
			"column-1": "20",
			"Paid": 12,
			"Un-Paid": 84
		},
		{
			"date": "2017-01-06",
			"column-1": "10",
			"Paid": 71,
			"Un-Paid": 14
		},
		{
			"date": "2017-01-07",
			"column-1": "33",
		},
		{
			"date": "2017-01-08",
			"column-1": "95",
		},
		{
			"date": "2017-01-09",
			"column-1": "65",
			"Paid": 13,
			"Un-Paid": 4
		},
		{
			"date": "2017-01-10",
			"column-1": "45",
		},
		{
			"date": "2017-01-11",
			"column-1": "12",
		},
		{
			"date": "2017-01-12",
			"column-1": "35",
		},
		{
			"date": "2017-01-13",
			"column-1": "25",
		},
		{
			"date": "2017-01-14",
			"column-1": "15",
			"Paid": 13,
			"Un-Paid": 34
		},
		{
			"date": "2017-01-15",
			"column-1": "10",
		},
	]
}
);
	//invoice1Month
	AmCharts.makeChart("invoice1Month",
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
			"id": "invoices",
			"size": 15,
			"text": "1 Month Missing Bills"
		}
	],
	"dataProvider": [
		{
			"date": "2017-01-01",
			"column-1": "80",
		},
		{
			"date": "2017-01-02",
			"column-1": "60",
		},
		{
			"date": "2017-01-03",
			"column-1": "40",
		},
		{
			"date": "2017-01-04",
			"column-1": "30",
		},
		{
			"date": "2017-01-05",
			"column-1": "20",
		},
		{
			"date": "2017-01-06",
			"column-1": "10",
		},
		{
			"date": "2017-01-07",
			"column-1": "33",
		},
		{
			"date": "2017-01-08",
			"column-1": "95",
		},
		{
			"date": "2017-01-09",
			"column-1": "65",
		},
		{
			"date": "2017-01-10",
			"column-1": "45",
		},
		{
			"date": "2017-01-11",
			"column-1": "12",
		},
		{
			"date": "2017-01-12",
			"column-1": "35",
		},
		{
			"date": "2017-01-13",
			"column-1": "25",
		},
		{
			"date": "2017-01-14",
			"column-1": "15",
		},
		{
			"date": "2017-01-15",
			"column-1": "10",
		},
		{
			"date": "2017-01-16",
			"column-1": "80",
		},
		{
			"date": "2017-01-17",
			"column-1": "60",
		},
		{
			"date": "2017-01-18",
			"column-1": "40",
		},
		{
			"date": "2017-01-19",
			"column-1": "30",
		},
		{
			"date": "2017-01-20",
			"column-1": "20",
		},
		{
			"date": "2017-01-21",
			"column-1": "10",
		},
		{
			"date": "2017-01-22",
			"column-1": "33",
		},
		{
			"date": "2017-01-23",
			"column-1": "95",
		},
		{
			"date": "2017-01-24",
			"column-1": "65",
		},
		{
			"date": "2017-01-25",
			"column-1": "45",
		},
		{
			"date": "2017-01-26",
			"column-1": "12",
		},
		{
			"date": "2017-01-27",
			"column-1": "35",
		},
		{
			"date": "2017-01-28",

			"column-1": "25",
		},
		{
			"date": "2017-01-29",
			"column-1": "15",
		},
		{
			"date": "2017-01-30",
			"column-1": "10",
		},
		{
			"date": "2017-01-31",
			"column-1": "10",
		},
	]
}
);
//invoice3Month
	AmCharts.makeChart("invoice3Month",
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
			"id": "invoices",
			"size": 15,
			"text": "Three Months Missing Bills"
		}
	],
	"dataProvider": [
		{
			"date": "2017-01",
			"column-1": "100",
		},
		{
			"date": "2017-02",
			"column-1": "80",
		},
		{
			"date": "2017-03",
			"column-1": "60",
		}
	]
}
);
</script> 
<jsp:include page="../masterFooter.jsp" />