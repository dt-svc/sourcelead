<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<script type="text/javascript" src="https://www.amcharts.com/lib/3/amcharts.js"></script>
<script type="text/javascript" src="https://www.amcharts.com/lib/3/serial.js"></script>
<script type="text/javascript" src="https://www.amcharts.com/lib/3/pie.js"></script> 
  <script type="text/javascript">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script>
		$(document).ready(function() {
			campaignStatsGraph();
		});
	</script>
	
<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Campaigns Stats</h2>
      </div>
      <div class="pull-right brud-crum">Bank Office &raquo; Campaigns List</div>
    </section>
      <div class="clearfix">
        <div class="tableStyles_head clearfix positionRelative"> 
          <!-- From date to date picker -->
          	<div class="row clearfix">
          		<div class="col-md-3">
          			  <div class="panel panel-default panel-campaignsStats">
						<div class="panel-body text-center">
						 <div class="panelHeading text-center">Delivered</div>
							<h3 class="text-success">66.7%</h3>
							<span class="clearfix">2</span>
						</div>
					  </div>
          		</div>
          		<div class="col-md-3">
          			  <div class="panel panel-default panel-campaignsStats">
						<div class="panel-body text-center">
						<div class="panelHeading text-center">Unique Opens</div>
							<h3 class="text-warning">66.7%</h3>
							<span class="clearfix">2</span>
						</div>
					  </div>
          		</div>
          		<div class="col-md-3">
          			  <div class="panel panel-default panel-campaignsStats">
						<div class="panel-body text-center">
						<div class="panelHeading text-center">Unique Clicks</div>
							<h3 class="text-info">66.7%</h3>
							<span class="clearfix">2</span>
						</div>
					  </div>
          		</div>
          		<div class="col-md-3 panel-campaignsStats">
          			  <div class="panel panel-default panel-campaignsStats">
						<div class="panel-body">
							 <ul class="list-unstyled campaignsstats-list mb0">
							 	<li><span class="fa fa-circle mr10 text-success"></span>Requests
							 	<span class="campaignsstatslist-count">00.00%</span>
							 	</li>
							 	<li><span class="fa fa-circle mr10 text-info"></span>Bounces
							 	<span class="campaignsstatslist-count">00.00%</span>
							 	</li>
							 	<li><span class="fa fa-circle mr10 text-warning"></span>Unsubscribes
							 	<span class="campaignsstatslist-count">00.00%</span>
							 	</li>
							 	<li><span class="fa fa-circle mr10 text-danger"></span>Spam Reports
							 	<span class="campaignsstatslist-count">00.00%</span>
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
					"dataProvider": [
						{
							"date": "2014-03-01",
							"column-1": 8,
							"column-2": 5,
							"column-3": 92,
							"column-4": 70,
							"column-5": 59,
							"column-6": 18,
							"column-7": 19,
							"column-8": 53,
							"column-9": 45,
							"column-10": 58,
							"column-11": 38,
							"column-12": 40
						},
						{
							"date": "2014-03-02",
							"column-1": 6,
							"column-2": 7,
							"column-3": 58,
							"column-4": 16,
							"column-5": 67,
							"column-6": 68,
							"column-7": 33,
							"column-8": 50,
							"column-9": 72,
							"column-10": 97,
							"column-11": 94,
							"column-12": 40
						},
						{
							"date": "2014-03-03",
							"column-1": 2,
							"column-2": 3,
							"column-3": 90,
							"column-4": 28,
							"column-5": 74,
							"column-6": 84,
							"column-7": 66,
							"column-8": 61,
							"column-9": 4,
							"column-10": 99,
							"column-11": 5,
							"column-12": 37
						},
						{
							"date": "2014-03-04",
							"column-1": 1,
							"column-2": 3,
							"column-3": 39,
							"column-4": 67,
							"column-5": 8,
							"column-6": 68,
							"column-7": 99,
							"column-8": 4,
							"column-9": 2,
							"column-10": 41,
							"column-11": 38,
							"column-12": 36
						},
						{
							"date": "2014-03-05",
							"column-1": 2,
							"column-2": 1,
							"column-3": 47,
							"column-4": 99,
							"column-5": 37,
							"column-6": 8,
							"column-7": 83,
							"column-8": 33,
							"column-9": 3,
							"column-10": 15,
							"column-11": 62,
							"column-12": 42
						},
						{
							"date": "2014-03-06",
							"column-1": 3,
							"column-2": 2,
							"column-3": 87,
							"column-4": 54,
							"column-5": 52,
							"column-6": 58,
							"column-7": 55,
							"column-8": 9,
							"column-9": 65,
							"column-10": 0,
							"column-11": 72,
							"column-12": 79
						},
						{
							"date": "2014-03-07",
							"column-1": 6,
							"column-2": 8,
							"column-3": 3,
							"column-4": 33,
							"column-5": 35,
							"column-6": 95,
							"column-7": 45,
							"column-8": 32,
							"column-9": 21,
							"column-10": 12,
							"column-11": 27,
							"column-12": 51
						}
					]
				}
			);
	</script>
  
  <jsp:include page="../masterFooter.jsp" />