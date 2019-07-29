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
			getGoalsList();
		 };
	</script>


<section class="content-wraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Team Goals</h2>
		</div>
		<div class="pull-right brud-crum">
			<a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>
			&raquo; Team Goals
		</div>
	</section>
	
	 <div class="project_wrap">
    <div class="perfTabs">
    	<ul class="clearfix">
    		<li><a href="${SourceLeadWeb_pmsUrl}/performanceGoalsList">My Goals</a></li>
   		    <li><a href="${SourceLeadWeb_pmsUrl}/performanceTeamGoalsList"  class="Act">Team Goals</a></li>
   		
    		
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
      	<span class="pull-left refreIcon"><a onclick="getGoalsList();" class="refeIcon" title="Refresh"></a></span>
      	
      	<ul class="pull-right list-unstyled list-inline">
				
				<sec:authorize access="hasRole('ROLE_ADD_TEAM_GOALS')">		
					<li>
							<a class="newAdBtn mr5" href="${SourceLeadWeb_pmsUrl}/addNewTeamGoal" >+ Goal</a>
			  		</li>
	  		</sec:authorize>
	  		<li id="goalsListSearch"><input type="text" class="repSearchBar"  id='goalsDataTableSearch'/><span class="repSearchIcon" onClick="goalsDataTableList();"></span></li>
        </ul>
     <div class="clearfix"></div>       
           
           <div class="candidate_wrap_table datatable_filter positionRelative" style='position:relative; top:-25px;'>
	          <div id="goalsListLoading"></div>
	         
		    <div class="project_tabs_inn_table vendor_wrap_inn_table dataFilterNew pdtop">
		    	<div id="goalsList" class="datatable_filter"></div>
			</div>	  
		
          </div>
</div> 

	</section>
	
<jsp:include page="../masterFooter.jsp" />


 <script>
  function getTotalGoalsDoNutChart(){
	  $("#totalGoalsLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	$.ajax({ 
		   	type: "GET",
			url: pmsUrl+"/getMyTotalGoalsDoNutCharts?performanceGoalType=team",
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
			url: pmsUrl+"/getMyInprogressGoalsDoNutCharts?performanceGoalType=team",
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
			url: pmsUrl+"/getMyCompletedGoalsDoNutCharts?performanceGoalType=team",
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
				url: pmsUrl+"/getMyOverDueGoalsDoNutCharts?performanceGoalType=team",
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
				url: pmsUrl+"/getMyNotStartedGoalsDoNutCharts?performanceGoalType=team",
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
  
  
  <script>
 function getGoalsList(){
	 var sourceleadUrl = '${SourceLeadWeb_sourceleadUrl}';
	 var locationCode = '${SourceLeadWeb_locationCode}';
	 
	 var locationList ='${SourceLeadWeb_dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  goalsListDate = 'YY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  goalsListDate = 'DD-MM-YY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  goalsListDate = 'MM-DD-YY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  goalsListDate = 'YY-DD-MM'; 
	  }
	  
	 $("#goalsListLoading").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	  var result = "";

	 	result = result + '<table id="goalsListTable" class="display mt10" cellspacing="0" width="100%" >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result +'	<th>Goal Name</th>';
		result = result +'  <th>Percentage</th>';
	  	result = result +'  <th>End Date<br>(<small class="custom-small">'+goalsListDate+'</small>)</th>';
	  	result = result +'  <th>Actions</th>';
		result = result + '</tr>';
		result = result + '</thead>	';
	    result = result +"</table>";
	    $("#goalsList").empty();
	    $("#goalsList").append(result);
	    $("#goalsListSearch").show();
	    $("#goalsDataTableSearch").val('');
	    goalsDataTableList();
	   
 }
 </script>
 
<script>
	function goalsDataTableList(){
		 var pmsUrl = '${SourceLeadWeb_pmsUrl}';
		 var searchValue = $("#goalsDataTableSearch").val();
		 searchValue = searchValue ? searchValue : "";
		 var url = pmsUrl+"/getPerformanceGoalsList?performanceGoalType=team&searchValue="+searchValue;
		 goalsPagination(url);
		 $("#goalsListLoading").removeAttr("style");
	}
</script>


<script>
function goalsPagination(url){
	var colIndex = 0;
    $('#goalsListTable').DataTable( { 
      "processing": true,
      "serverSide": true,
      "searching": false,
      "ajax": $.fn.dataTable.pipeline( {
    	  url: url,
          method: 'GET',
          pages: 1
        }),
        "columns": [
            { "data": "goalName" },
            { "data": "percentageDone"},
            { "data": "goalEndDate"},
            { "data": "goalActions"}
        ],
        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"data": "goalName",
	    	  	"render": function(data, type, opt, meta) {
	    	  		  var result = "";
	    	  	   	 result = result +"<td>";
                	 result = result +"<div class='width300px'>";
                	 result = result +"<div class='tbldata-dotdot-big pull-left' title='"+opt.goalName+"'><a href='"+pmsUrl+"/goalSummary?goalUUID="+opt.uuid+"'>"+opt.goalName+"</a>";
                	 result = result +"<p class='text-muted mb0 fontSmall'>"+opt.goalStartDate+"</p>";
                	 result = result +"</div>"
                	 result = result +"</td>";
		             return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "percentageDone",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  if(opt.percentageDone == null){
            		  percentageDone = '0';
            	  }else{
            		  percentageDone = opt.percentageDone;
            	  }
	              result = result +"<div class='tblminwidth-30'>";
	              result = result +"<div class='progress mb0 progressGoals'>";
	              result = result +"<div class='progress-bar progress-bar-success' role='progressbar' aria-valuenow='"+percentageDone.toFixed(2)+"' aria-valuemin='0' aria-valuemax='100' style='width:"+percentageDone+"%'>";
	              result = result +"</div>";
	              result = result +"</div>";
	              result = result +"<p class='mb0 fontSmall mt5'>"+percentageDone.toFixed(2)+"% Completed (info)</p>";
	              result = result +"</div>";
				
	              return result;
              		
              }
	      
	          },{
	        	  "targets": colIndex++,
	              "data": "goalEndDate",
	              "render": function ( data, type, opt, meta ) {
	            	  var result = "";
	             	  result = result +"<td><div class='tblminwidth-30'><span>"+opt.goalEndDate+"</span></div></td>";
		              return result;
	              }
		     }, {
		    	  "targets": colIndex++,
	              "data": "actions",
	              "sortable": false,
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    result = result +'<td>';
	            	    result = result +'<div class="width150px clearfix">';
	            	    result = result +'<ul class="list-unstyled list-inline ml clearfix">';
	            	    if(opt.goalType == "New_Goal"){
	            	    	 result = result +'<li><a href="'+pmsUrl+'/addNewTeamGoal?goalUUID='+opt.uuid+'"><i class="fa fa-pencil cursorPointer" title="Edit"></i></a></li>';
	            	    }else{
	            	    	result = result +'<li><a href="'+pmsUrl+'/addNewTeamSubGoal?goalUUID='+opt.uuid+'&goalType=editGoal"><i class="fa fa-pencil cursorPointer" title="Edit"></i></a></li>';
	            	    }
	            	    
	            	    result = result +"<li><a onclick='deletePerformanceGoal(\""+opt.uuid+"\")'><i class='fa fa-trash cursorPointer' title='Delete'></i></a></li>";
	            	    result = result +'<li><a href="'+pmsUrl+'/goalSummary?goalUUID='+opt.uuid+'"><i class="fa fa-comments cursorPointer text-primary" title="Comment"></i></a></li>';
		            	   if(opt.goalType == "New_Goal"){
		            		   result = result +'<li><a href="'+pmsUrl+'/addNewTeamSubGoal?goalUUID='+opt.uuid+'&goalType=newSubGoal"><i class="fa fa-level-down cursorPointer textSuccess" title="Sub Goals"></i></a></li>';
		            	   }
	            	    result = result +'</ul>';
	            	    result = result +'</div>';
	            	    result = result +'</td>';
	            	    return result;
	              }
		     }],
		     
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"aaSorting": [[0,'desc']],
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
    } );
    $("#goalsListTable").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $("#goalsDataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
   		    $('#goalsDataTableSearch').parent().find('p.jquery_form_error_message').remove();
   	    	var searchData = $('#goalsDataTableSearch').val();
   	    	
   	 	if(searchData.endsWith("-") || searchData.endsWith("\\") || searchData.endsWith("//") || searchData.endsWith("%")){
	    		$("#goalsDataTableSearch").after("<p class='jquery_form_error_message'><span><font color='red'>Enter valid Search Data</font></span>");
	    	}else{
	    		goalsDataTableList();
	    	}
   	    	$('#goalsDataTableSearch').val(searchData);
    	   }
    	});
}

</script>


<script>
function deletePerformanceGoal(goalUUID){
	  $.ajax({
	    	type: "POST",
	        url: pmsUrl+"/deletePerformanceGoalByGoalUUID?goalUUID="+goalUUID, 
	        success: function(response) {
	        
	    	if (response.response === "AWKWARD") {
	      		location.href = "/errorPage";
	      	} else if (response.response === "SUCCESS") {
	      		location.replace(pmsUrl+"/performanceTeamGoalsList");
	      	}
		        
	       },   
	    });
}
</script>
  