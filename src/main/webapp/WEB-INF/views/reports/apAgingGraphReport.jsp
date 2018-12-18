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
	vendorapAgingBillGraph();
});
</script>


  <!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>A/P Aging</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/reportsDashboard'/>">Reports Dashboard</a> &raquo; <a href="<c:url value='${appPath}/financialReportsList'/>">Financial Reports
</a>&raquo; A/P Aging</div>
    </section>
    <!-- hiring Process  -->
    <div class="clearfix">
      <div class="ReqRepHead clearfix">
    		      <div class="col-md-2 pull-left reqListBtn">
          <a href='${appPath}/apAgingGraphReport' title="List View" id="GView" class="btn btn-default"><i class="fa fa-pie-chart"></i></a>
          <a href='${appPath}/apAgingReport' title="List View" id="LView" class="btn btn-default"><i class="fa fa-list"></i></a>
    		      </div>
     		<!--<a href="jobsPipline-list.html" class="btn btn-primary">List</a>-->
       
        <div class="col-md-4 col-md-offset-3">
    <div class="dropdown-scroll">
		<select class="chosen-select form-control" multiple data-placeholder="Select Vendor" id="orgId">
		  <c:if test="${not empty vendorList}">
               <c:forEach items="${vendorList}" var="vendor">
   						<option value='${vendor.id }'>${vendor.orgName}</option>
				</c:forEach>
		</c:if>
		</select>
	</div>
        </div>
        <div class="col-md-2">
    		<a href="javascript:void(0)" class="btn btn-primary btn-sm" onclick="vendorapAgingBillGraph();">Customize</a>
        </div>
        
      
		<!-- <div class="col-md-1">
          <div class="btn-group bootstrap_dropdownMenu dropdownPadding">
            <button type="button" class="btn btn-primary btn-sm" title="Actions"><i class="fa fa-retweet"></i></button>
            <button type="button" class="btn btn-primary dropdown-toggle btn-sm" data-toggle="dropdown"> <span class="caret"></span> </button>
            <ul class="dropdown-menu clearfix  dropdown-menu-right">
              <li role="Export" class="mt5 cursorPointer"><i class="fa fa-file-pdf-o paddingRight"></i>Export</li>
            </ul>
          </div>
        </div> -->
      </div>
    </div>
    <div class="clearfix  mt content-area"> 
      <div class="col-md-3">
				<div class="clearfix billing_count mt25 text-center">
                     <h4><span class="rupee_usd mt10 mb10 mr5">Total:</span><span class="totValue" id="total">0.00</span></h4>
                </div>
                <div class="clearfix"></div>
                 <div id="ulDiv"></div>
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


<script>
function vendorapAgingBillGraph(){
	    var apagingType = 'Payables';
		var sourceleadUrl ='${sourceleadUrl}';
		var orgIds = $("#orgId").val();
	    $.post(sourceleadUrl+"/vendorapAgingBillGraph?"+tokenParamName+"="+tokenValue+"&apagingType="+apagingType+"&orgIds="+orgIds, function(json) {
	    	
	    	var result = "";
	    	result = result + "<ul class='list-unstyled mt text-center ulDesing'>";
	    	var year = json.above365Dates;
	    	 $("#total").html(json.total.toFixed(2)) 
	    	for(var i=0; i< year.length; i++){
	    		var data = year[i];
	    		result = result + "<li><label class='tblminwidth-50'>"+data.year+":</label><span>"+data.amount+"<b class='paddingLeft'></b></span></li>";
	    	}
	    	result = result + "</ul>";
	    	$("#ulDiv").empty();
	    	$("#ulDiv").append(result);
	    	
	    	prepareapagingGraph(json);
		});  
}
</script>

<script>
function prepareapagingGraph(data){
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
				"enabled": true,
				"fileName":"A/P Aging"
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
					"id": "apaging",
					"lineColor": "#EF5350",
					"showOnAxis": true,
					"title": "APAging",
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
					"value": ""+data.tenDaysToatl+""
				},
				{
					"category": "11 - 15 Days",
					"value": ""+data.fifteenDaysTotal+""
				},
				{
					"category": "16 - 30 Days",
					"value": ""+data.thirtyDaysTotal+""
				},
				{
					"category": "31  - 60 Days",
					"value": ""+data.sixtyDaysTotal+""
				},
				{
					"category": "61 - 90 Days",
					"value": ""+data.nintyDaysTotal+""
				},
				{
					"category": "91 - 180 Days",
					"value": ""+data.oneeightyDaysTotal+""
				},
				{
					"category": "181 - 365 Days",
					"value": ""+data.threeSixtyFiveDaysTotal+""
				},
				{
					"category": ">  365 Days",
					"value": ""+data.above365DaysTotal+""
				}
			]
		}
			);	
}
</script>


<jsp:include page="../masterFooter.jsp" />