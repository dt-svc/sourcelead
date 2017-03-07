<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="resources/js/amcharts.js"></script>
<script src="resources/js/serial.js"></script>
<script src="resources/js/light.js"></script>
<script src="resources/js/pie.js"></script>


<!-- <script src="https://www.amcharts.com/lib/3/plugins/export/export.min.js"></script>
<link rel="stylesheet" href="https://www.amcharts.com/lib/3/plugins/export/export.css" type="text/css" media="all" /> --> 


<script type="text/javascript">
$(document).ready(function(){
	getJobSummaryDoNutChart();
	setInterval(function() {getJobSummaryDoNutChart(); }, 300000);
});
</script>

<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Jobs Update</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Vendor &raquo; Add Client</div>
    </section>
    <div class="content-new clearfix">
      <div class="row">
          <div class="col-md-7 paddingRight">
              <div id="chartdiv" style="width: 100%;">
              
              </div>
          </div>

          <div class="col-md-5 text-center paddingLeft">
              <div id="jobSummary">
              
                  </div>
          </div>
      </div>
      <div class="clearfix"></div>
      <div class="row mt25 activeJobs">
          <div class="col-sm-6 col-md-3">
            <div class="content_widget jobs-graphview buttonView">
                 <span class="label label-primary">Active Jobs</span>
                <img src="resources/images/active_jobs.png" title="Active Jobs" alt="Active Jobs">
                <span class="count">
                    100
                </span>
              </div>
          </div>
          <div class="col-sm-6 col-md-3">
            <div class="content_widget jobs-graphview buttonView">
                <span class="label label-warning">Referrals</span>
                    <img src="resources/images/refer-icon.png" title="Referrals" alt="Referrals">
                
                <span class="count">100</span>
            </div>
          </div>
          <div class="col-sm-6 col-md-3">
                          <div class="content_widget jobs-graphview buttonView">
                <span class="label label-success">Applied</span>
                              
                    <img src="resources/images/applied_icon.png" title="Applied" alt="Applied">
                <span class="count">100</span>
            </div>

          </div>
          <div class="col-sm-6 col-md-3">
                          <div class="content_widget jobs-graphview buttonView">
                <span class="label label-info">Shortlisted</span>
                              <img src="resources/images/short_listed.png" title="Shortlisted" alt="Shortlisted">
                <span class="count">100</span>
            </div>

          </div>
          <div class="col-sm-6 col-md-3">
                          <div class="content_widget jobs-graphview buttonView">
                <span class="label label-success">Interviews</span>
                              
                    <img src="resources/images/interview_icons.png" title="Interviews" alt="Interviews">
                <span class="count">100</span>
            </div>

          </div>
          <div class="col-sm-6 col-md-3">
                          <div class="content_widget jobs-graphview buttonView">
                <span class="label label-info">Offered</span>
                              
                    <img src="resources/images/offered_icon.png" title="Offered" alt="Offered">
                <span class="count">100</span>
            </div>

          </div>
          <div class="col-sm-6 col-md-3">
                          <div class="content_widget jobs-graphview buttonView">
                <span class="label label-primary">Hired</span>
                    <img src="resources/images/hired_icon.png" title="Hired" alt="Hired">
                <span class="count">100</span>
            </div>

          </div>
          <div class="col-sm-6 col-md-3">
                          <div class="content_widget jobs-graphview buttonView">
                <span class="label label-danger">              Rejected
</span>
                    
                              <img src="resources/images/reject_icon.png" title="Rejected" alt="Rejected">
                <span class="count">100</span>
            </div>

          </div>
      </div>

      </div>
  </section>
  
  
  
  <script>
  function getJobSummaryDoNutChart(){
	var sourceleadUrl = '${sourceleadUrl}';
	$.ajax({ 
		   	type: "GET",
		   	url: sourceleadUrl+"/getJobSummaryDoNutChart?${_csrf.parameterName}=${_csrf.token}",
		    success: function(data) {
	        	var json = JSON.parse(data);
	        	
	        	loadJobSummaryDoNutChart(json['appliedJobs'],json['referToMeJobs'],json['referByMeJobs'])
	        },
	        error: function(e){
	        }
	 });
  }
  </script>
  
  
  
  
  <!-- For donut chart -->
<script>
function loadJobSummaryDoNutChart(appliedJobs,referToMeJobs,referByMeJobs){
	 var chart = AmCharts.makeChart( "jobSummary", {
	  "type": "pie",
	  "theme": "light",
	  "dataProvider": [ {
	    "title": "Refered To Me ("+referToMeJobs+")",
	    "value": referToMeJobs
	  }, {
	    "title": "Refered By Me ("+referByMeJobs+")",
	    "value": referByMeJobs
	  }, {
	    "title": "Applied ("+appliedJobs+")",
	    "value": appliedJobs
	  } ],
	  "titleField": "title",
	  "valueField": "value",
	  "labelRadius": 5,
	
	  "radius": "35%",
	  "innerRadius": "40%",
	  "labelText": "[[title]]",
	  "export": {
	    "enabled": true
	  }
	} );
 
}
    
</script>


 <!-- For Linear chart -->
  <script>
  var chart = AmCharts.makeChart("chartdiv", {
      "type": "serial",
      "theme": "light",
      "marginTop":0,
      "marginRight": 80,
      "dataProvider": [{
	  "year": "1950",
	  "value": -0.307
      }, {
	  "year": "1951",
	  "value": -0.168
      }, {
	  "year": "1952",
	  "value": -0.073
      }, {
	  "year": "1953",
	  "value": -0.027
      }, {
	  "year": "1954",
	  "value": -0.251
      }, {
	  "year": "1955",
	  "value": -0.281
      }, {
	  "year": "1956",
	  "value": -0.348
      }, {
	  "year": "1957",
	  "value": -0.074
      }, {
	  "year": "1958",
	  "value": -0.011
      }, {
	  "year": "1959",
	  "value": -0.074
      }, {
	  "year": "1960",
	  "value": -0.124
      }, {
	  "year": "1961",
	  "value": -0.024
      }, {
	  "year": "1962",
	  "value": -0.022
      }, {
	  "year": "1963",
	  "value": 0
      }, {
	  "year": "1964",
	  "value": -0.296
      }, {
	  "year": "1965",
	  "value": -0.217
      }, {
	  "year": "1966",
	  "value": -0.147
      }, {
	  "year": "1967",
	  "value": -0.15
      }, {
	  "year": "1968",
	  "value": -0.16
      }, {
	  "year": "1969",
	  "value": -0.011
      }, {
	  "year": "1970",
	  "value": -0.068
      }, {
	  "year": "1971",
	  "value": -0.19
      }, {
	  "year": "1972",
	  "value": -0.056
      }, {
	  "year": "1973",
	  "value": 0.077
      }, {
	  "year": "1974",
	  "value": -0.213
      }, {
	  "year": "1975",
	  "value": -0.17
      }, {
	  "year": "1976",
	  "value": -0.254
      }, {
	  "year": "1977",
	  "value": 0.019
      }, {
	  "year": "1978",
	  "value": -0.063
      }, {
	  "year": "1979",
	  "value": 0.05
      }, {
	  "year": "1980",
	  "value": 0.077
      }, {
	  "year": "1981",
	  "value": 0.12
      }, {
	  "year": "1982",
	  "value": 0.011
      }, {
	  "year": "1983",
	  "value": 0.177
      }, {
	  "year": "1984",
	  "value": -0.021
      }, {
	  "year": "1985",
	  "value": -0.037
      }, {
	  "year": "1986",
	  "value": 0.03
      }, {
	  "year": "1987",
	  "value": 0.179
      }, {
	  "year": "1988",
	  "value": 0.18
      }, {
	  "year": "1989",
	  "value": 0.104
      }, {
	  "year": "1990",
	  "value": 0.255
      }, {
	  "year": "1991",
	  "value": 0.21
      }, {
	  "year": "1992",
	  "value": 0.065
      }, {
	  "year": "1993",
	  "value": 0.11
      }, {
	  "year": "1994",
	  "value": 0.172
      }, {
	  "year": "1995",
	  "value": 0.269
      }, {
	  "year": "1996",
	  "value": 0.141
      }, {
	  "year": "1997",
	  "value": 0.353
      }, {
	  "year": "1998",
	  "value": 0.548
      }, {
	  "year": "1999",
	  "value": 0.298
      }, {
	  "year": "2000",
	  "value": 0.267
      }, {
	  "year": "2001",
	  "value": 0.411
      }, {
	  "year": "2002",
	  "value": 0.462
      }, {
	  "year": "2003",
	  "value": 0.47
      }, {
	  "year": "2004",
	  "value": 0.445
      }, {
	  "year": "2005",
	  "value": 0.47
      }],
      "valueAxes": [{
	  "axisAlpha": 0,
	  "position": "left"
      }],
      "graphs": [{
	  "id":"g1",
	  "balloonText": "[[category]]<br><b><span style='font-size:14px;'>[[value]]</span></b>",
	  "bullet": "round",
	  "bulletSize": 8,
	  "lineColor": "#d1655d",
	  "lineThickness": 2,
	  "negativeLineColor": "#637bb6",
	  "type": "smoothedLine",
	  "valueField": "value"
      }],
      "chartScrollbar": {
	  "graph":"g1",
	  "gridAlpha":0,
	  "color":"#888888",
	  "scrollbarHeight":55,
	  "backgroundAlpha":0,
	  "selectedBackgroundAlpha":0.1,
	  "selectedBackgroundColor":"#888888",
	  "graphFillAlpha":0,
	  "autoGridCount":true,
	  "selectedGraphFillAlpha":0,
	  "graphLineAlpha":0.2,
	  "graphLineColor":"#c2c2c2",
	  "selectedGraphLineColor":"#888888",
	  "selectedGraphLineAlpha":1

      },
      "chartCursor": {
	  "categoryBalloonDateFormat": "YYYY",
	  "cursorAlpha": 0,
	  "valueLineEnabled":true,
	  "valueLineBalloonEnabled":true,
	  "valueLineAlpha":0.5,
	  "fullWidth":true
      },
      "dataDateFormat": "YYYY",
      "categoryField": "year",
      "categoryAxis": {
	  "minPeriod": "YYYY",
	  "parseDates": true,
	  "minorGridAlpha": 0.1,
	  "minorGridEnabled": true
      },
      "export": {
	  "enabled": true
      }
  });

  chart.addListener("rendered", zoomChart);
  if(chart.zoomChart){
	  chart.zoomChart();
  }

  function zoomChart(){
      chart.zoomToIndexes(Math.round(chart.dataProvider.length * 0.4), Math.round(chart.dataProvider.length * 0.55));
  }
  </script>
    
    

 

<jsp:include page="../masterFooter.jsp" />