<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="/resources/js/amcharts.js"></script>
<script src="/resources/js/serial.js"></script>
<script src="/resources/js/light.js"></script>
<script src="/resources/js/pie.js"></script>

<script type="text/javascript" src="https://www.amcharts.com/lib/3/plugins/export/export.min.js"></script>
<link rel="stylesheet" href="https://www.amcharts.com/lib/3/plugins/export/export.css">


<script type="text/javascript">
$(document).ready(function(){
	getTimeToFillChart();
});
</script>

<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Time to fill</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/reportsDashboard'/>">Reports Dashboard</a> &raquo; Time to fill </div>
    </section>
    
    
    <div class="clearfix">
    <div class="clearfix"></div>
    	<div class="ReqRepHead clearfix">
        
        <!-- <div class="col-md-2">
        <a href="#" id="graphicView" title="Graphical View" class="btn btn-default"><i class="fa fa-pie-chart"></i></a>
        <a href="#" title="List View" id="listView" class="btn btn-default"><i class="fa fa-list"></i></a>
        </div> -->
        <div class="col-md-4">
          <select class="chosen-select form-control" multiple data-placeholder="Select Job" name="jobIds" id="jobIds">
            <c:forEach var="option" items="${requirementsList}">
				<option value="${option.requirementId}">${option.jobTitle}</option>
			</c:forEach>
          </select>
        </div>
        
        <div class="col-md-2">
          <select class="form-control" name="year" id="year">
            <option value=''>--Select Year--</option>
            <c:forEach var="option" items="${yearList}">
				<option value="${option.year}">${option.year}</option>
			</c:forEach>
          </select>
        </div>
        
        <div class="col-md-3 dropdown-select positionRelative">
          <select class="form-control" name="dateSelection" id="dateSelection" onchange="getCustomDates();">
            <option value=''>Select Date</option>
            <option value='Last_7_days' selected>Last 7 days</option>
            <option value='Last_15_days'>Last 15 days</option>
            <option value='1_month'>1 month</option>
            <option value='3_months'>3 months</option>
            <option value='YTD'>YTD</option>
            <option value='Last_Year'>Last Year</option>
            <option value="custom">Custom</option>
          </select>
          
          
          <div class="clearfix pull-lefet col-md-12 paddingRight customLeaves" id="customSearch" style="display: none; width: 346px;">
            <div class="pull-left">
              <div class="pull-left mr5" style="width: 140px">
                <input type="text" class="form-control calendar" value="" id="fromDate" name="fromDate"  placeholder="From Date"  readonly="readonly" >
                <p id="fromDateDiv" style="display: none; font-size: 12px;"><font color="red">From Date Required</font></p>
              </div>
              
              <div class="pull-left mr5" style="width: 140px">
                <input type="text" class="form-control calendar" value="" id="toDate" name="toDate"  placeholder="To Date" field-name="From Date" readonly="readonly" >
                <p id="toDateDiv" style="display: none; font-size: 12px;"><font color="red">To Date Required</font></p>
              </div>
              <button type="button" class="btn btn-primary btn-sm" onclick="getApplicantBySourceChart();"><i class="fa fa-search"></i></button>
            </div>
          </div>
          
        </div>
        
        
        
        <div class="col-md-2">
        <button type="button" class="btn btn-primary btn-sm	mt2" id="customize_btn" onclick="getTimeToFillChart();">Customize</button>
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
    	<div class="clearfix"></div>
    	 	<div class="dashboard-widget clearfix mt">
    	 	<div id="timetofill" class="" style="height: 350px; width: 100%;"></div>
    	 	
    	 	<div class="clearfix">
    			<div class="reTenWrap mt">
			<h2 class="clearfix"><p>2017</p><span class="text-success">80%</span></h2>
			<h3 class="clearfix"><p>Quarter 1</p><span class="text-primary">20%</span></h3>
			<h3 class="clearfix"><p>Quarter 2</p><span class="text-warning">20%</span></h3>
			<h3 class="clearfix"><p>Quarter 3</p><span class="text-primary">20%</span></h3>
			<h3 class="clearfix"><p>Quarter 4</p><span class="text-warning">20%</span></h3>
		</div>
    		</div>
    		
    	 	</div>
    
    	<div class="clearfix"></div>
    </div>
</section>    
    
<jsp:include page="../masterFooter.jsp" />

<script>
function getTimeToFillChart(){
	
	 var flag = true;
	  var jobIds = $("#jobIds").val();
	  if(jobIds == null){
		  jobIds = '';
	  }
	  
	  var year = $("#year").val(); 
	  var dateSelection = $("#dateSelection").val(); 
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
		  $.ajax({ 
			  type: "GET",
			 	url: sourceleadUrl+"/getTimeToFillDetails?${_csrf.parameterName}=${_csrf.token}&jobIds="+jobIds+"&dateSelection="+dateSelection+"&fromDate="+fromDate+"&toDate="+toDate+"&year="+year,
			    success: function(json) {
		  
			    	if(json.length > 0)	
		 	   		{
		        		    var chartData = [];
							for(var i=0; i < json.length ; i++){
		        			 var timeToFill = json[i];
		        			    chartData.push( {
		        			    	date: timeToFill.month,
		        					column1: timeToFill.fillCount,
		        			    } );
		        			  }
		        	
		        		 loadTimeToFillChart(chartData);
		 	   		}
			    },
		        error: function(e){
		        }
		    });
	  }
	
}
</script>


<script>
function loadTimeToFillChart(result){
	 var chart = AmCharts.makeChart("timetofill", {
		 
			"type": "serial",
			"categoryField": "date",
			"columnSpacing": 0,
			"dataDateFormat": "YYYY-MM",
			"plotAreaBorderColor": "#3A3A3A",
			"accessibleTitle": "",
			"borderColor": "#FFFFFF",
			"theme": "default",
			"categoryAxis": {
				"forceShowField": "",
				"minPeriod": "MM",
				"parseDates": true,
				"twoLineMode": true,
				"axisColor": "#AAB3B3",
				"boldLabels": true,
				"centerLabels": true,
				"gridColor": "#3A3A3A",
				"gridThickness": 0,
				"title": ""
			},
		   "dataProvider": result,
			"valueAxes": [
				{
					"baseValue": 90,
					"id": "ValueAxis-1",
					"stackType": "regular",
					"zeroGridAlpha": 0,
					"axisColor": "#888888",
					"color": "#3A3A3A",
					"gridColor": "#FFFFFF",
					"gridCount": 0,
					"gridThickness": 0,
					"minorGridAlpha": 0,
					"title": ""
				}
			],
			"allLabels": [],
			"balloon": {
				"borderAlpha": 0.47,
				"borderColor": "#FF8000",
				"borderThickness": 1,
				"color": "#FF8000",
				"fillAlpha": 1,
				"shadowColor": "#FF8000",
				"showBullet": true
			},
	
			"titles": [],
			"graphs": [
				{
					"balloonText": "[[value]]",
					"bullet": "round",
					"columnWidth": 0,
					"fillColors": "#000000",
					"gapPeriod": 0,
					"id": "jobs",
					"minDistance": 0,
					"negativeFillAlphas": 0,
					"negativeFillColors": "#008000",
					"negativeLineAlpha": 0,
					"negativeLineColor": "undefined",
					"title": "Jobs",
					"topRadius": 0,
					"type": "smoothedLine",
					"valueField": "column1",
					"visibleInLegend": false
				}
			],
	 });
}
</script>