<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

 <script src="/resources/js/amcharts.js"></script>
<script src="/resources/js/serial.js"></script>
<script src="/resources/js/light.js"></script>
<script src="/resources/js/pie.js"></script>



<script type="text/javascript">
$(document).ready(function(){
	getApplicantBySourceChart();
});
</script>


<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Applicant(s) by Source</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Applicant(s) by Source</div>
    </section>
    <div class="clearfix">
    <div class="clearfix"></div>
    	<div class="ReqRepHead clearfix">
        
        <div class="col-md-2">
        <a href="#" id="graphicView" title="Graphical View" class="btn btn-default"><i class="fa fa-pie-chart"></i></a>
        <a href="#" title="List View" id="listView" class="btn btn-default"><i class="fa fa-list"></i></a>
        </div>
        <div class="col-md-4">
          <select class="chosen-select form-control" multiple data-placeholder="Select Job" name="jobIds" id="jobIds">
            <c:forEach var="option" items="${requirementsList}">
				<option value="${option.requirementId}">${option.jobTitle}</option>
			</c:forEach>
          </select>
        </div>
        <div class="col-md-3 dropdown-select positionRelative">
          <select class="form-control" name="dateSelection" id="dateSelection">
            <option value=''>Select Date</option>
            <option value='Last_7_days' selected>Last 7 days</option>
            <option value='Last_15_days'>Last 15 days</option>
            <option value='1_month'>1 month</option>
            <option value='3_months'>3 months</option>
            <option value='YTD'>YTD</option>
            <option value='Last_Year'>Last Year</option>
            <option value="custom">Custom</option>
          </select>
        </div>
        
        <div class="col-md-2">
        <button type="button" class="btn btn-primary btn-sm	mt2" onclick="getApplicantBySourceChart();">Customize</button>
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
   	 	<!-- Applicant source Graph 2017 -->
    	 	<div class="dashboard-widget clearfix mt">
    	 	<div id="applicantSource" class="amchartAnchor-Hide" style="height: 350px; width: 100%;"></div>
    	 	</div>
    	 	<!-- Applicant source Graph December 2017 -->
    	 	
    	 	
 	<!-- <div class="clearfix dashboard-widget mts col-md-12 mt">
		<div class="clearfix">
		  <div id="applicantSourceListView" class="datatable_filter"></div>
		</div>
    </div> -->
    
    	<div class="clearfix"></div>
    </div>
  </section>


<jsp:include page="../masterFooter.jsp" />



<script>
  function getApplicantBySourceChart(){
	  
	  var jobIds = $("#jobIds").val();
	  if(jobIds == null){
		  jobIds = '';
	  }
	  var dateSelection = $("#dateSelection").val(); 
		
		$.ajax({ 
			   	type: "GET",
			 	url: sourceleadUrl+"/getApplicantBySourceDetails?${_csrf.parameterName}=${_csrf.token}&jobIds="+jobIds+"&dateSelection="+dateSelection,
			    success: function(json) {
			    	
			        	if(json.length > 0)	
			 	   		{
			        		    var chartData = [];
								for(var i=0; i < json.length ; i++){
			        			 var applicantSource = json[i];
			        			    chartData.push( {
			        			    	category: applicantSource.category,
			        					column2: applicantSource.applicantsCount,
			        					column3: applicantSource.shortlistedCount,
			        					column4: applicantSource.interviewsCount,
			        					column5: applicantSource.rejectedCount,
			        					column6: applicantSource.offeredCount,
			        					column7: applicantSource.offerAcceptedCount,
			        					column8: applicantSource.offerRejectedCount,
			        					column9: applicantSource.backoutCount
			        			     
			        			    } );
			        			  }
			        	
			        		 loadApplicantSourceChart(chartData);
			 	   		}
			        	
			        	 var listView = document.getElementById("listView");
			        	 listView.addEventListener("click", function() {
							 getListView(json);
						  });
			        	 
			        	 var graphicView = document.getElementById("graphicView");
			        	 graphicView.addEventListener("click", function() {
			        		 loadApplicantSourceChart(chartData);
						  });
		        	
		        },
		        error: function(e){
		        }
		 });
  }
  </script>

 <script>
 function loadApplicantSourceChart(result){
	 
	 $("#applicantSource").empty();
     
	 var chart = AmCharts.makeChart("applicantSource", {
		 
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
			"categoryAxis": {
				"autoRotateAngle": 30,
				"autoRotateCount": 0,
				"gridPosition": "start",
				"autoGridCount": false,
				"axisColor": "#333",
				"axisThickness": 0,
				"gridColor": "#AAB3B3",
				"gridThickness": 0,
				"labelOffset": 6,
				"minHorizontalGap": 0,
				"minVerticalGap": 0,
				"tickLength": 0,
				"title": ""
			},
		   "dataProvider": result,
		   "valueAxes": [
				{
					"id": "ValueAxis-1",
					"stackType": "regular",
					"totalText": "",
					"unit": "",
					"autoGridCount": false,
					"axisThickness": 0,
					"gridColor": "#AAB3B3",
					"gridThickness": 0,
					"labelsEnabled": false,
					"tickLength": 0,
					"title": ""
				}
			],
			"allLabels": [],
			"balloon": {
				"fixedPosition": false
			},
			"legend": {
				"enabled": true,
				"equalWidths": false,
				"labelWidth": 0,
				"marginLeft": 0,
				"marginRight": 0,
				"markerLabelGap": 10,
				"markerSize": 10,
				"position": "left"
			},
			"titles": [],
		   "graphs": [
				{
					"balloonText": "[[title]] of<br> [[category]]:[[value]]",
					"columnWidth": 0.3,
					"fillAlphas": 1,
					"fillColors": "#90CAF9",
					"id": "applicants",
					"labelText": "[[value]]",
					"legendValueText": "",
					"lineColor": "#90CAF9",
					"title": "Applicants",
					"type": "column",
					"valueField": "column2"
				},
				{
					"balloonText": "[[title]] of<br>[[category]]:[[value]]",
					"columnWidth": 0.3,
					"fillAlphas": 1,
					"fillColors": "#F48FB1",
					"id": "shortlisted",
					"labelText": "[[value]]",
					"lineColor": "#F48FB1",
					"title": "Shortlisted",
					"type": "column",
					"valueField": "column3"
				},
				  {
					"balloonText": "[[title]] of<br>[[category]]:[[value]]",
					"columnWidth": 0.3,
					"fillAlphas": 1,
					"fillColors": "#FFCC80",
					"id": "interviews",
					"labelText": "[[value]]",
					"lineColor": "#FFCC80",
					"title": "Interviews",
					"type": "column",
					"valueField": "column4"
				},
				{
					"balloonText": "[[title]] of<br>[[category]]:[[value]]",
					"columnWidth": 0.3,
					"fillAlphas": 1,
					"fillColors": "#B39DDB",
					"id": "rejected",
					"labelText": "[[value]]",
					"lineColor": "#B39DDB",
					"title": "Rejected",
					"type": "column",
					"valueField": "column5"
				},
				{
					"balloonText": "[[title]] of<br>[[category]]:[[value]]",
					"columnWidth": 0.3,
					"fillAlphas": 1,
					"fillColors": "#81C784",
					"id": "offered",
					"labelText": "[[value]]",
					"lineColor": "#81C784",
					"title": "Offered",
					"type": "column",
					"valueField": "column6"
				},
				{
					"balloonText": "[[title]] of<br>[[category]]:[[value]]",
					"columnWidth": 0.3,
					"fillAlphas": 1,
					"fillColors": "#E57373",
					"id": "offerAccepted",
					"labelText": "[[value]]",
					"lineColor": "#E57373",
					"title": "Offer Accepted",
					"type": "column",
					"valueField": "column7"
				},
				{
					"balloonText": "[[title]] of<br>[[category]]:[[value]]",
					"columnWidth": 0.3,
					"fillAlphas": 1,
					"fillColors": "#FF8A54",
					"id": "offerrejected",
					"labelText": "[[value]]",
					"lineColor": "#FF8A54",
					"title": "Offer Rejected",
					"type": "column",
					"valueField": "column8"
				},
				{
					"balloonText": "[[title]] of<br>[[category]]:[[value]]",
					"columnWidth": 0.3,
					"fillAlphas": 1,
					"fillColors": "#F44336",
					"id": "backout",
					"labelText": "[[value]]",
					"lineColor": "#F44336",
					"title": "Backout",
					"type": "column",
					"valueField": "column9"
				}
			],
	 });
 }
 </script> 

 <script>
 function getListView(json){
	 if(json.length > 0)	
 		{
		 var result = "";
		 result = result +"<div class='clearfix tableStyles mt'>"; 
		 result = result +"<table class='table table-bordered mt10'>"; 
         result = result +"<thead>"; 
         result = result +"  <tr>"; 
         result = result +"    <th>Source</th>";
         result = result +"    <th>Applicants</th>"; 
         result = result +"    <th>Shortlisted</th>"; 
         result = result +"    <th>Rejected</th>"; 
         result = result +"    <th>Interviews</th>"; 
         result = result +"    <th>Offered</th>"; 
         result = result +"    <th>Offer Accepted</th>"; 
         result = result +"    <th>Offer Rejected</th>"; 
         result = result +"    <th>Back Out</th>"; 
         result = result +"  </tr>"; 
         result = result +"</thead>";
         result = result +"<tbody>"; 
			 for(var i=0; i < json.length ; i++){
				 var applicantSource = json[i];
				 
	   					
				 result = result +"<tr>"; 
				
				 result = result +"<td>"; 
				 result = result +"<div class='tbldata-dotdot'>"+applicantSource.category+"</div>"; 
				 result = result +"</td>"; 
				
				 result = result +"<td>"; 
				 result = result +"<div class='tblminwidth-50'>"+applicantSource.applicantsCount+"</div>"; 
				 result = result +"</td>"; 
				 
				 result = result +"<td>"; 
				 result = result +"<div class='tblminwidth-50'>"+applicantSource.shortlistedCount+"</div>"; 
				 result = result +"</td>"; 
				 
				 result = result +"<td>"; 
				 result = result +"<div class='tblminwidth-50'>"+applicantSource.rejectedCount+"</div>"; 
				 result = result +"</td>"; 
				 
				 result = result +"<td>"; 
				 result = result +"<div class='tblminwidth-50'>"+applicantSource.interviewsCount+"</div>"; 
				 result = result +"</td>";
				 
				 result = result +"<td>"; 
				 result = result +"<div class='tblminwidth-50'>"+applicantSource.offeredCount+"</div>"; 
				 result = result +"</td>";
				 
				 result = result +"<td>"; 
				 result = result +"<div class='tblminwidth-50'>"+applicantSource.offerAcceptedCount+"</div>"; 
				 result = result +"</td>";
				 
				 result = result +"<td>"; 
				 result = result +"<div class='tblminwidth-50'>"+applicantSource.offerRejectedCount+"</div>"; 
				 result = result +"</td>";
				 
				 result = result +"<td>"; 
				 result = result +"<div class='tblminwidth-50'>"+applicantSource.backoutCount+"</div>"; 
				 result = result +"</td>";
				
				
				 result = result +"</tr>"; 
			 }
			 result = result +" </tbody>";
             result = result +"</table>";
             
             $("#applicantSource").empty();
             $("#applicantSource").append(result);
            
             
 		}
 }
 </script>
 