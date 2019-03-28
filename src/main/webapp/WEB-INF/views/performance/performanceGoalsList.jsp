<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<script src="${SourceLeadWeb_pmsUrl}/resources/js/amcharts.js?staticTime=${staticTime}"></script>
<script src="${SourceLeadWeb_pmsUrl}/resources/js/serial.js?staticTime=${staticTime}"></script>
<script src="${SourceLeadWeb_pmsUrl}/resources/js/light.js?staticTime=${staticTime}"></script>
<script src="${SourceLeadWeb_pmsUrl}/resources/js/pie.js?staticTime=${staticTime}"></script>

<script>
var pmsUrl ='${SourceLeadWeb_pmsUrl}';
</script>

<script type="text/javascript">
		window.onload = function () {
			getTotalGoalsDoNutChart();
			getInProgressGoalsDoNutChart();
			getCompletedGoalsDoNutChart();
			getOverDueGoalsDoNutChart();
			getNotStartedGoalsDoNutChart();
		 };
	</script>


<section class="content-wraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Goals</h2>
		</div>
		<div class="pull-right brud-crum">
			<a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>
			&raquo; Goals
		</div>
	</section>
	
	 <div class="project_wrap">
    <div class="perfTabs">
    	<ul class="clearfix">
    		<li><a href="#" class="Act">My Goals</a></li>
    		<li><a href="#">Team Goals</a></li>
    		<li><a href="#">Department Goals</a></li>
    	</ul>
    </div>  
    
    <div class="goalsDash clearfix">
    	<div class="row clearfix">
	    		<div class="col-md-2">
							<div class="clearfix text-center">
									<h5 class="margin-top0 mb0">Total Goals</h5>
									<div id ="totalGoalsLoading"></div>
							</div>
						<div id="totalGoals" class="goalChat"></div>
	    		</div>
    			
    			<div class="col-md-2">
						<div class="clearfix text-center">
								<h5 class="margin-top0 mb0">In-progress Goals</h5>
								<div id ="inprogressGoalsLoading"></div>
						</div>
					<div id="inProgress" class="goalChat"></div>
				</div>
				
				
				<div class="col-md-2">
						<div class="clearfix text-center">
								<h5 class="margin-top0 mb0">Completed Goals</h5>
								<div id ="completedGoalsLoading"></div>
						</div>
					<div id="completedGoals" class="goalChat"></div>
				</div>
				<div class="col-md-2">
						<div class="clearfix text-center">
								<h5 class="margin-top0 mb0">Overdue Goals</h5>
								<div id ="overDueGoalsLoading"></div>
						</div>
					<div id="overdueGoals" class="goalChat"></div>
				</div>
				<div class="col-md-2">
						<div class="clearfix text-center">
								<h5 class="margin-top0 mb0">Goals Not Started</h5>
								<div id ="notStartedGoalsLoading"></div>
						</div>
					<div id="goalsNotstarted" class="goalChat"></div>
				</div>
    </div>  
	</div>
    </div>
		
<div class="positionRelative reportHead clearfix mt">
      	<span class="pull-left refreIcon"><a href="javascript:void(0)" class="refeIcon" title="Refresh"></a></span>
      	<ul class="pull-right list-unstyled list-inline">
						<li>
								<a href="javascript:void(0)" class="filtIcon" title="Filters" onclick="openPop();"></a>
							</li>
					<li>
					<a class="newAdBtn mr5" href="${SourceLeadWeb_pmsUrl}/addNewGoal" >+ Goal</a>
	  		</li>
		  	<li class=""><input class="repSearchBar" placeholder="Search" type="text"><span class="repSearchIcon"></span></li>
        </ul>
</div> 

	</section>
	
<jsp:include page="../masterFooter.jsp" />


 <script>
  function getTotalGoalsDoNutChart(){
	  $("#totalGoalsLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	$.ajax({ 
		   	type: "GET",
			url: pmsUrl+"/getMyTotalGoalsDoNutCharts",
		    success: function(response) {
		    	if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
  	    		    showError(response.responseMessage);
  	    		}
		        	else if (response.response === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (response.response === "SUCCESS") {
		        		 loadTotalGoalsDoNutChart(response.object.goalCount,response.object.selfCount,response.object.assignedCount);
		        		 $("#totalGoalsLoading").removeAttr('style');
		        	} else if (response.indexOf('Sign In') !== -1) {
		        		location.replace(sourceleadUrl);
		        	} else {
		        	    showError("Operation failed, please try again");
		        	}
	        },
	        error: function(e){
	        }
	 });
  }
  </script>
  
  <script>
  function getInProgressGoalsDoNutChart(){
	  $("#inprogressGoalsLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	$.ajax({ 
		   	type: "GET",
			url: pmsUrl+"/getMyInprogressGoalsDoNutCharts",
		    success: function(response) {
		    	if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
  	    		    showError(response.responseMessage);
  	    		}
		        	else if (response.response === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (response.response === "SUCCESS") {
		        		 loadInprogressGoalsDoNutChart(response.object.goalCount,response.object.selfCount,response.object.assignedCount);
		        		 $("#inprogressGoalsLoading").removeAttr('style');
		        	} else if (response.indexOf('Sign In') !== -1) {
		        		location.replace(sourceleadUrl);
		        	} else {
		        	    showError("Operation failed, please try again");
		        	}
	        },
	        error: function(e){
	        }
	 });
  }
  </script>
  
   <script>
  function getCompletedGoalsDoNutChart(){
	$("#completedGoalsLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	$.ajax({ 
		   	type: "GET",
			url: pmsUrl+"/getMyCompletedGoalsDoNutCharts",
		    success: function(response) {
		    	if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
  	    		    showError(response.responseMessage);
  	    		}
		        	else if (response.response === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (response.response === "SUCCESS") {
		        		 loadCompeltedGoalsDoNutChart(response.object.goalCount,response.object.selfCount,response.object.assignedCount);
		        		 $("#completedGoalsLoading").removeAttr('style');
		        	} else if (response.indexOf('Sign In') !== -1) {
		        		location.replace(sourceleadUrl);
		        	} else {
		        	    showError("Operation failed, please try again");
		        	}
	        },
	        error: function(e){
	        }
	 });
  }
  </script>
  
  <script>
  function getOverDueGoalsDoNutChart(){
	  
	  $("#overDueGoalsLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		$.ajax({ 
			   	type: "GET",
				url: pmsUrl+"/getMyOverDueGoalsDoNutCharts",
			    success: function(response) {
			    	if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
	  	    		    showError(response.responseMessage);
	  	    		}
			        	else if (response.response === "AWKWARD") {
			        	    location.href = "/errorPage";
			        	} else if (response.response === "SUCCESS") {
			        		 loadOverDueGoalsDoNutChart(response.object.goalCount,response.object.selfCount,response.object.assignedCount);
			        		 $("#overDueGoalsLoading").removeAttr('style');
			        	} else if (response.indexOf('Sign In') !== -1) {
			        		location.replace(sourceleadUrl);
			        	} else {
			        	    showError("Operation failed, please try again");
			        	}
		        },
		        error: function(e){
		        }
		 });
	  
  }
  </script>
  
  <script>
  function getNotStartedGoalsDoNutChart(){
	  $("#notStartedGoalsLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		$.ajax({ 
			   	type: "GET",
				url: pmsUrl+"/getMyNotStartedGoalsDoNutCharts",
			    success: function(response) {
			    	if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
	  	    		    showError(response.responseMessage);
	  	    		}
			        	else if (response.response === "AWKWARD") {
			        	    location.href = "/errorPage";
			        	} else if (response.response === "SUCCESS") {
			        		 loadNotStartedGoalsDoNutChart(response.object.goalCount,response.object.selfCount,response.object.assignedCount);
			        		 $("#notStartedGoalsLoading").removeAttr('style');
			        	} else if (response.indexOf('Sign In') !== -1) {
			        		location.replace(sourceleadUrl);
			        	} else {
			        	    showError("Operation failed, please try again");
			        	}
		        },
		        error: function(e){
		        }
		 });
  }
  </script>
  

 <script>
  function loadTotalGoalsDoNutChart(goalCount,selfCount,assignedCount){
		AmCharts.makeChart("totalGoals",
				{
		"type": "pie",
		"balloonText": "[[title]]<br><span style='font-size:14px'><b>[[value]]</b> ([[percents]]%)</span>",
		"innerRadius": "65%",
		"pieX": "50%",
		"pieY": "50%",
		"radius": "%",
		"colorField": "color",
		"colors": [],
		"labelsEnabled": false,
		"marginBottom": 0,
		"marginTop": 0,
		"titleField": "category",
		"valueField": "column-1",
		"allLabels": [
			{
				"bold": true,
				"id": "Label-1",
				"size": 12,
				"text": goalCount,
				"x": "45%",
				"y": "40%"
			}
		],
		"balloon": {
			"fixedPosition": false
		},
		"legend": {
			"enabled": true,
			"align": "center",
			"marginLeft": 0,
			"marginRight": 0,
			"markerType": "circle"
		},
		"titles": [],
		"dataProvider": [
			{
				"category": "Self Goals",
				"column-1": selfCount,
				"color": "#74b9ff"
			},
			{
				"category": "Assigned Goals",
				"column-1": assignedCount,
				"color": "#a29bfe"
			}
		]
	});
  }
  </script> 

  <script>
  function loadInprogressGoalsDoNutChart(goalCount,selfCount,assignedCount){
  AmCharts.makeChart("inProgress",
		  {
		  	"type": "pie",
		  	"balloonText": "[[title]]<br><span style='font-size:14px'><b>[[value]]</b> ([[percents]]%)</span>",
		  	"innerRadius": "65%",
		  	"pieX": "50%",
		  	"pieY": "50%",
		  	"radius": "%",
		  	"colorField": "color",
		  	"colors": [],
		  	"labelsEnabled": false,
		  	"marginBottom": 0,
		  	"marginTop": 0,
		  	"titleField": "category",
		  	"valueField": "column-1",
		  	"allLabels": [
		  		{
		  			"bold": true,
		  			"id": "Label-1",
		  			"size": 12,
		  			"text": goalCount,
		  			"x": "45%",
		  			"y": "40%"
		  		}
		  	],
		  	"balloon": {
		  		"fixedPosition": false
		  	},
		  	"legend": {
		  		"enabled": true,
		  		"align": "center",
		  		"marginLeft": 0,
		  		"marginRight": 0,
		  		"markerType": "circle"
		  	},
		  	"titles": [],
		  	"dataProvider": [
		  		{
		  			"category": "Self Goals",
		  			"column-1": selfCount,
		  			"color": "#ffeaa7"
		  		},
		  		{
		  			"category": "Assigned Goals",
		  			"column-1": assignedCount,
		  			"color": "#fdcb6e"
		  		}
		  	]
		  }
		  );	
  }
  </script>
  
  <script>
  function loadCompeltedGoalsDoNutChart(goalCount,selfCount,assignedCount){
	  AmCharts.makeChart("completedGoals",
			  {
			  	"type": "pie",
			  	"balloonText": "[[title]]<br><span style='font-size:14px'><b>[[value]]</b> ([[percents]]%)</span>",
			  	"innerRadius": "65%",
			  	"pieX": "50%",
			  	"pieY": "50%",
			  	"radius": "%",
			  	"colorField": "color",
			  	"colors": [],
			  	"labelsEnabled": false,
			  	"marginBottom": 0,
			  	"marginTop": 0,
			  	"titleField": "category",
			  	"valueField": "column-1",
			  	"allLabels": [
			  		{
			  			"bold": true,
			  			"id": "Label-1",
			  			"size": 12,
			  			"text": goalCount,
			  			"x": "45%",
			  			"y": "40%"
			  		}
			  	],
			  	"balloon": {
			  		"fixedPosition": false
			  	},
			  	"legend": {
			  		"enabled": true,
			  		"align": "center",
			  		"marginLeft": 0,
			  		"marginRight": 0,
			  		"markerType": "circle"
			  	},
			  	"titles": [],
			  	"dataProvider": [
			  		{
			  			"category": "Self Goals",
			  			"column-1": selfCount,
			  			"color": "#2ecc71"
			  		},
			  		{
			  			"category": "Assigned Goals",
			  			"column-1":assignedCount,
			  			"color": "#16a085"
			  		}
			  	]
			  }
			  );
  }
  </script>
  
  <script>
  function loadOverDueGoalsDoNutChart(goalCount,selfCount,assignedCount){
	  AmCharts.makeChart("overdueGoals",
			  {
			  	"type": "pie",
			  	"balloonText": "[[title]]<br><span style='font-size:14px'><b>[[value]]</b> ([[percents]]%)</span>",
			  	"innerRadius": "65%",
			  	"pieX": "50%",
			  	"pieY": "50%",
			  	"radius": "%",
			  	"colorField": "color",
			  	"colors": [],
			  	"labelsEnabled": false,
			  	"marginBottom": 0,
			  	"marginTop": 0,
			  	"titleField": "category",
			  	"valueField": "column-1",
			  	"allLabels": [
			  		{
			  			"bold": true,
			  			"id": "Label-1",
			  			"size": 12,
			  			"text": goalCount,
			  			"x": "45%",
			  			"y": "40%"
			  		}
			  	],
			  	"balloon": {
			  		"fixedPosition": false
			  	},
			  	"legend": {
			  		"enabled": true,
			  		"align": "center",
			  		"marginLeft": 0,
			  		"marginRight": 0,
			  		"markerType": "circle"
			  	},
			  	"titles": [],
			  	"dataProvider": [
			  		{
			  			"category": "Self Goals",
			  			"column-1": selfCount,
			  			"color": "#e74c3c"
			  		},
			  		{
			  			"category": "Assigned Goals",
			  			"column-1": assignedCount,
			  			"color": "#c0392b"
			  		}
			  	]
			  }
			  );
  }
  </script>
  
  <script>
  function loadNotStartedGoalsDoNutChart(goalCount,selfCount,assignedCount){
	  AmCharts.makeChart("goalsNotstarted",
			  {
			  	"type": "pie",
			  	"balloonText": "[[title]]<br><span style='font-size:14px'><b>[[value]]</b> ([[percents]]%)</span>",
			  	"innerRadius": "65%",
			  	"pieX": "50%",
			  	"pieY": "50%",
			  	"radius": "%",
			  	"colorField": "color",
			  	"colors": [],
			  	"labelsEnabled": false,
			  	"marginBottom": 0,
			  	"marginTop": 0,
			  	"titleField": "category",
			  	"valueField": "column-1",
			  	"allLabels": [
			  		{
			  			"bold": true,
			  			"id": "Label-1",
			  			"size": 12,
			  			"text": goalCount,
			  			"x": "45%",
			  			"y": "40%"
			  		}
			  	],
			  	"balloon": {
			  		"fixedPosition": false
			  	},
			  	"legend": {
			  		"enabled": true,
			  		"align": "center",
			  		"marginLeft": 0,
			  		"marginRight": 0,
			  		"markerType": "circle"
			  	},
			  	"titles": [],
			  	"dataProvider": [
			  		{
			  			"category": "Self Goals",
			  			"column-1": selfCount,
			  			"color": "#95a5a6"
			  		},
			  		{
			  			"category": "Assigned Goals",
			  			"column-1": assignedCount,
			  			"color": "#bdc3c7"
			  		}
			  	]
			  }
			  ); 
  }
  </script>
  