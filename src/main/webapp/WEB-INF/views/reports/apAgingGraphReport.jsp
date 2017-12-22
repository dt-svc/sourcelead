<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="https://www.amcharts.com/lib/3/amcharts.js"></script>
<script type="text/javascript" src="https://www.amcharts.com/lib/3/serial.js"></script>

<script type="text/javascript">
$(document).ready(function(){
	
});
</script>


  <!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>A/P Aging</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; A/P Aging</div>
    </section>
    <!-- hiring Process  -->
    <div class="clearfix">
      <div class="ReqRepHead clearfix">
    		      <div class="col-md-2 pull-left reqListBtn">
          <a href='${appPath}/apAgingGraphReport' title="List View" id="GView" class="btn btn-default"><i class="fa fa-pie-chart"></i></a>
          <a href='${appPath}/apAgingReport' title="List View" id="LView" class="btn btn-default"><i class="fa fa-list"></i></a>
    		      </div>
     		<!--<a href="jobsPipline-list.html" class="btn btn-primary">List</a>-->
       
        <div class="col-md-4 col-md-offset-5">
    <div class="dropdown-scroll">
		<select class="chosen-select form-control" multiple data-placeholder="Select Clinet">
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
        <div class="col-md-3 dropdown-select hidden">
        <div class="dropdown-select positionRelative">
        <select class="form-control" id="custom-dates">
          <option>Select Date</option>
          <option>Last 7 days</option>
          <option>Last 15 days</option>
          <option>1 month</option>
          <option>3 months</option>
          <option>last year</option>
          <option>Year to Date</option>
          <option value="custom">Custom</option>
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
		<div class="col-md-1">
          <div class="btn-group bootstrap_dropdownMenu dropdownPadding">
            <button type="button" class="btn btn-primary btn-sm" title="Actions"><i class="fa fa-retweet"></i></button>
            <button type="button" class="btn btn-primary dropdown-toggle btn-sm" data-toggle="dropdown"> <span class="caret"></span> </button>
            <ul class="dropdown-menu clearfix  dropdown-menu-right">
              <li role="Export" class="mt5 cursorPointer"><i class="fa fa-file-pdf-o paddingRight"></i>Export</li>
            </ul>
          </div>
        </div>
      </div>
    </div>
    <div class="clearfix amchartAnchor-Hide mt content-area"> 
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
               <div id="apaging" style="width: 100%; height: 350px;"></div>
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

<!-- amCharts javascript code --> 
<script type="text/javascript">
	AmCharts.makeChart("apaging",
	{
	"type": "serial",
	"categoryField": "category",
	"marginBottom": 15,
	"marginLeft": 15,
	"marginRight": 15,
	"marginTop": 15,
	"colors": [],
	"startDuration": 1,
	"responsive": {
		"enabled": true
	},
	"export": {
		"enabled": true
	},
	"categoryAxis": {
		"autoRotateAngle": 30,
		"autoRotateCount": 0,
		"gridPosition": "start",
		"axisThickness": 0,
		"gridThickness": 0
	},
	"trendLines": [],
	"graphs": [
		{
			"balloonText": "[[title]]<br> [[category]]<br>Value:  [[value]]",
			"bulletBorderThickness": 10,
			"bulletColor": "#E57373",
			"bulletOffset": 5,
			"bulletSize": 10,
			"fillAlphas": 1,
			"fillColors": "#E57373",
			"fixedColumnWidth": 50,
			"id": "araging",
			"lineColor": "#EF5350",
			"showOnAxis": true,
			"title": "ARAging",
			"topRadius": 0,
			"type": "column",
			"valueField": "value"
		}
	],
	"guides": [],
	"valueAxes": [
		{
			"id": "ValueAxis-1",
			"axisThickness": 0,
			"gridColor": "#E57373",
			"gridThickness": 0,
			"title": ""
		}
	],
	"allLabels": [],
	"balloon": {
		"fixedPosition": false
	},
	"titles": [
		{
			"color": "#E57373",
			"id": "Title-2",
			"size": 15,
			"text": "A/P Aging"
		}
	],
	"dataProvider": [
		{
			"category": "1 - 10 Days",
			"value": "10000"
		},
		{
			"category": "11 - 15 Days",
			"value": "50000"
		},
		{
			"category": "16 - 30 Days",
			"value": "10000"
		},
		{
			"category": "31  - 60 Days",
			"value": "20000"
		},
		{
			"category": "61 - 90 Days",
			"value": "30000"
		},
		{
			"category": "91 - 180 Days",
			"value": "80000"
		},
		{
			"category": "181 - 365 Days",
			"value": "40000"
		},
		{
			"category": ">  365 Days",
			"value": "50000"
		}
	]
}
	);	
	</script> 

<jsp:include page="../masterFooter.jsp" />