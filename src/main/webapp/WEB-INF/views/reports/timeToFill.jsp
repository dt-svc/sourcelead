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
	getTimeToFillChart();
});
</script>

<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Time to fill</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/reportsDashboard'/>">Reports Dashboard</a> &raquo; <a href="<c:url value='${appPath}/performanceReportList'/>">Performance Reports</a> &raquo; Time to fill </div>
    </section>
    
    
    <div class="vendor_wrap clearfix">
    
<div class="reportHead clearfix">
	
    	<ul class="clearfix pull-right">
    		<li>
				<div class="ReqRepHead-chosen repChosen">
			          <select class="chosen-select form-control" multiple data-placeholder="Select Job" name="jobIds" id="jobIds">
			            <c:forEach var="option" items="${requirementsList}">
							<option value="${option.requirementId}">${option.generatedId} - ${option.jobTitle}</option>
						</c:forEach>
			          </select>
		        </div>    		
    		</li>
    		<li>
	        <div class="dropdown-select positionRelative repSelWrap clearfix">
		          <select class="form-control repSelFiled" name="year" id="year">
		            <option value=''>--Select Year--</option>
		            <c:forEach var="option" items="${yearList}">
						<option value="${option.year}">${option.year}</option>
					</c:forEach>
		          </select>          
				<div class="repSelBtn"><button type="button" class="btn btn-primary btn-sm" id="customize_btn" onclick="getTimeToFillChart();">Run</button></div> 
	        </div>    		
    		</li> 
    	</ul>
    </div>     

    	<div class="dashboard-widget clearfix">
    	 	<div id="loading"></div>
    	 	<div id="timetofill" class="" style="height: 350px; width: 100%;"></div>
    	 	<div class="clearfix">
    			<div class="reTenWrap mt">
					<h2 class="clearfix"><p id="yearchart"></p><span class="text-success" id="totalyearcount">0</span></h2>
					<h3 class="clearfix"><p>Quarter 1 - Jan To Mar</p><span class="text-primary" id="quarter1">0</span></h3>
					<h3 class="clearfix"><p>Quarter 2 - Apr To Jun</p><span class="text-warning" id="quarter2">0</span></h3>
					<h3 class="clearfix"><p>Quarter 3 - Jul To Sep</p><span class="text-primary" id="quarter3">0</span></h3>
					<h3 class="clearfix"><p>Quarter 4 - Oct To Dec</p><span class="text-warning" id="quarter4">0</span></h3>
				</div>
    		</div>
    		
    	 </div>
    
    	<div class="clearfix"></div>
    </div>
</section>    
    
<jsp:include page="../masterFooter.jsp" />

<script>
function reload(){
	location.reload();
}
</script>

<script>
function getTimeToFillChart(){
	 var flag = true;
	  var jobIds = $("#jobIds").val();
	  if(jobIds == null){
		  jobIds = '';
	  }
	  
	  var year = $("#year").val(); 

	
	  $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		  $.ajax({ 
			  type: "GET",
			 	url: sourceleadUrl+"/getTimeToFillDetails?${_csrf.parameterName}=${_csrf.token}&jobIds="+jobIds+"&year="+year,
			    success: function(json) {
		  
			    	if(json.length > 0)	
		 	   		{
			    		
			    		var quarter1 = 0;
			    		var quarter2 = 0;
			    		var quarter3 = 0;
			    		var quarter4 = 0;
			    		var totalyearcount = 0;
			    		
		        		    var chartData = [];
							for(var i=0; i < json.length ; i++){
		        			 var timeToFill = json[i];
		        			    chartData.push( {
		        			    	date: timeToFill.month,
		        					column1: timeToFill.fillCount,
		        			    } );
		        			     if(timeToFill.monthStr == "January" || timeToFill.monthStr == "February" || timeToFill.monthStr == "March") {
		        	    	    	  quarter1 = parseFloat(quarter1)+parseFloat(timeToFill.fillCountValue);
		        	    	      }else if(timeToFill.monthStr == "Apirl" || timeToFill.monthStr == "May" || timeToFill.monthStr == "June") {
		        	    	    	  quarter2 = parseFloat(quarter2)+parseFloat(timeToFill.fillCountValue);
		        	    	      }else if(timeToFill.monthStr == "July" || timeToFill.monthStr == "August" || timeToFill.monthStr == "September") {
		        	    	    	  quarter3 = parseFloat(quarter3)+parseFloat(timeToFill.fillCountValue);
		        	    	      }else {
		        	    	    	  quarter4 = parseFloat(quarter4)+parseFloat(timeToFill.fillCountValue);
		        	    	      }
		        			  }
		        	
		        		 loadTimeToFillChart(chartData);
		        		 
		        		 
		        		 if(year != null && year != '' && year != 'undefined'){
		        			 $("#yearchart").html(year); 
		        		 }else{
		        			 $("#yearchart").html('${currentYear}'); 
		        		 }
		        		 
		        		 $("#totalyearcount").html(parseFloat(quarter1)+parseFloat(quarter2)+parseFloat(quarter3)+parseFloat(quarter4));
		        		 $("#quarter1").html(quarter1);
		        		 $("#quarter2").html(quarter2);
		        		 $("#quarter3").html(quarter3);
		        		 $("#quarter4").html(quarter4);
		        		 
		 	   		}
			    	
			    	
			    	
			    	 $("#loading").removeAttr("style");
			    },
		        error: function(e){
		        }
		    });
	
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
			"responsive": {
				"enabled": true
			},
			"export": {
				"enabled": true,
				"fileName":"Time to fill"
			},
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
					"title": "Avg Days"
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