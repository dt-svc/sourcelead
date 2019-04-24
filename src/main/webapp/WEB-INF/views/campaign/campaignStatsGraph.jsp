<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/amcharts/amcharts.js"></script>
<script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/amcharts/serial.js"></script>
<script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/amcharts/pie.js"></script>

<script>
$(document).ready(function() {
 	var campaignName = '${campaignName}';
	campaignStatsGraph(campaignName);

});

</script>

<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Campaigns Stats</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='campaignStats'/>">Bank Office</a>&raquo; Campaigns List</div>
    </section>
      <div class="clearfix">
        <div class="tableStyles_head clearfix positionRelative"> 
          <!-- From date to date picker -->
          	<div class="row clearfix">
          		<div class="col-md-3">
          			  <div class="panel panel-default panel-campaignsStats">
						<div class="panel-body text-center">
						 <div class="panelHeading text-center">Delivered</div>
							<!-- <h3 class="text-success">66.7%</h3> -->
							<span class="clearfix" id="delivered"></span>
						</div>
					  </div>
          		</div>
          		<div class="col-md-3">
          			  <div class="panel panel-default panel-campaignsStats">
						<div class="panel-body text-center">
						<div class="panelHeading text-center">Opens</div>
							<!-- <h3 class="text-warning">66.7%</h3> -->
							<span class="clearfix" id="opens"></span>
						</div>
					  </div>
          		</div>
          		<div class="col-md-3">
          			  <div class="panel panel-default panel-campaignsStats">
						<div class="panel-body text-center">
						<div class="panelHeading text-center">Clicks</div>
							<!-- <h3 class="text-info">66.7%</h3> -->
							<span class="clearfix" id="clicks"></span>
						</div>
					  </div>
          		</div>
          		<div class="col-md-3 panel-campaignsStats">
          			  <div class="panel panel-default panel-campaignsStats">
						<div class="panel-body">
							 <ul class="list-unstyled campaignsstats-list mb0">
							 	<li><span class="fa fa-circle mr10 text-success"></span>Requests
							 	<span class="campaignsstatslist-count" id="request"></span>
							 	</li>
							 	<li><span class="fa fa-circle mr10 text-info"></span>Bounces
							 	<span class="campaignsstatslist-count" id="bounces"></span>
							 	</li>
							 	<li><span class="fa fa-circle mr10 text-warning"></span>Unsubscribes
							 	<span class="campaignsstatslist-count" id="unsubscribes"></span>
							 	</li>
							 	<li><span class="fa fa-circle mr10 text-danger"></span>Spam Reports
							 	<span class="campaignsstatslist-count" id="spamReports"></span>
							 	</li>
							 </ul>
						</div>
					  </div>
          		</div>
          	</div>
          <!-- Advanced Search -->
        </div>
        <div class="clearfix  amchartAnchor-Hide">
          <div id="campaignsstats" style="width: 100%; height: 350px; margin-top: 15px;"></div>
        </div>
      </div>
  </section>
  
  
   <script>
  function campaignStatsGraph(campaignName){
      $.ajax({
      type : "GET",
       url: sourceleadUrl+"/getCampaignStatsByCampaignId?"+tokenParamName+"="+tokenValue+"&campaignName="+campaignName,       
       success :function(json) {
    	  if(json !=null){
    		  $("#delivered").html(json.delivered)
       	   $("#opens").html(json.opens);
       	   $("#clicks").html(json.clicks);
       	   $("#request").html(json.request);
       	   $("#bounces").html(json.bounces);
       	   $("#unsubscribes").html(json.unsubscribes);
       	   $("#spamReports").html(json.spamReports);
       	   campaignStatsAmchart(json);  
    	  }
      },
       
  }); 
  }
  </script>
  
		   
  
  <script>
  function campaignStatsAmchart(json){
	       var chartData = [
				{
					"date": json.createdDate,
	        		"column-1": json.request,
	        		"column-2": json.delivered,
	        		"column-3": json.opens,
	        		"column-4": json.uniqueOpens,
	        		"column-5": json.clicks,
	        		"column-6": json.uniqueClicks,
	        		"column-7": json.bounceDrops,
	        		"column-8": json.spamReportDrops,
	        		"column-9": json.unsubscribes,
	        		"column-10": json.bounces,
	        		"column-11": json.spamReports,
	        		"column-12": json.invalidEmails
				}
			];

			AmCharts.makeChart("campaignsstats",
				{
					"type": "serial",
					"categoryField": "date",
					"dataDateFormat": "YYYY-MM-DD",
					"marginBottom": 10,
					"marginLeft": 10,
					"marginRight": 10,
					"marginTop": 10,
					"categoryAxis": {
						"autoRotateAngle": 0,
						"autoRotateCount": 0,
						"equalSpacing": true,
						"parseDates": true,
						"firstDayOfWeek": 0,
						"markPeriodChange": false
					},
					"trendLines": [],
					"graphs": [
						{
							"bullet": "round",
							"id": "requests",
							"title": "Requests",
							"valueField": "column-1"
						},
						{
							"bullet": "square",
							"id": "delivered",
							"title": "Delivered",
							"valueField": "column-2"
						},
						{
							"id": "opens",
							"title": "Opens",
							"valueField": "column-3"
						},
						{
							"id": "uniqueOpens",
							"title": "Unique Opens",
							"valueField": "column-4"
						},
						{
							"id": "clicks",
							"title": "Clicks",
							"valueField": "column-5"
						},
						{
							"id": "uniqueClicks",
							"title": "Unique Clicks",
							"valueField": "column-6"
						},
						{
							"id": "bounceDrops",
							"title": "Bounce Drops",
							"valueField": "column-7"
						},
						{
							"id": "spamReportDrops",
							"title": "Spam Report Drops",
							"valueField": "column-8"
						},
						{
							"id": "unsubscribes",
							"title": "Unsubscribes",
							"valueField": "column-9"
						},
						{
							"id": "bounces",
							"title": "Bounces",
							"valueField": "column-10"
						},
						{
							"id": "spamReports",
							"title": "Spam Reports",
							"valueField": "column-11"
						},
						{
							"id": "invalidEmails",
							"title": "Invalid Emails",
							"valueField": "column-12"
						}
					],
					"guides": [],
					"valueAxes": [
						{
							"id": "ValueAxis-1",
							"title": ""
						}
					],
					"allLabels": [],
					"balloon": {},
					"legend": {
						"enabled": true,
						"markerBorderThickness": 0,
						"markerSize": 10,
						"markerType": "circle",
						"valueWidth": 0
					},
					"titles": [],
					"dataProvider":chartData
				}
			);
			
  }
	</script>
  
  
 
  <jsp:include page="../masterFooter.jsp" />