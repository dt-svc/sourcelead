<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

	<script src="/resources/js/amcharts.js?staticTime=${staticTime}"></script>
	<script src="/resources/js/serial.js?staticTime=${staticTime}"></script>
	<script src="/resources/js/light.js?staticTime=${staticTime}"></script>
	<script src="/resources/js/pie.js?staticTime=${staticTime}"></script>
<!-- this script is for Tasks list functionality at Projects screen -->
<script>
function getDayWiseReports(projectId){
	var reportType = $("#filterDates").val();
	if(reportType === "custom"){
		$("#custom").fadeIn();
	}else{
		$("#custom").fadeOut();
		$("#startDate").val('');
		$("#endDate").val('');
		taskReports(projectId,reportType,"","");
		overdueTask(projectId,reportType,"","");
		tasksprogressChart(projectId,reportType,"","");
		budgetStatusReport(projectId,reportType,"","");
		timesheetReport(projectId,reportType,"","");
		expensesReport(projectId,reportType,"","");
		document.getElementById("projectReportDiv").style.display="none";
		document.getElementById("tasksStatusCoutnDiv").style.display="block";
	}
	
}
function submitBtn(projectId){
	if ($('#projectTasksReports_form').validate(false, validationSettings)) {
		var reportType = $("#filterDates").val();
		var startDate = $("#startDate").val();
		var endDate = $("#endDate").val();
		taskReports(projectId,reportType,startDate,endDate);
// 		stackTaskReport(projectId,reportType,startDate,endDate);
		overdueTask(projectId,reportType,startDate,endDate);
		tasksprogressChart(projectId,reportType,startDate,endDate);
		budgetStatusReport(projectId,reportType,startDate,endDate);
		timesheetReport(projectId,reportType,startDate,endDate);
		expensesReport(projectId,reportType,startDate,endDate);
	}
	$('body').on('blur', '#projectTasksReports_form', function() {
		$("#projectTasksReports_form").showHelpOnFocus();
		$("#projectTasksReports_form").validateOnBlur(false, validationSettings);
	});
	
 	
}
</script>
<script>
	function projectReports(projectId) {
			var result = "";
			result = result + '	<div class="clearfix mt mb">';
			
			/* ================= Tasks reports =====================*/
			result = result + '     <div class="clearfix">';
			
			result = result + '	<div class="clearfix mb">';
			
				result = result + ' <div class="col-md-2 dropdown-select">';
				result = result + ' <select class="form-control" onChange="getDayWiseReports('+projectId+')" id="filterDates">';
				result = result + '	<option value="Last_7_days">Last 7 days</option>';
		        result = result + ' <option value="Last_15_days">Last 15 days</option>';
		        result = result + ' <option value="1_month" selected>1 month</option>';
		        result = result + ' <option value="3_months">3 months</option>';
		        result = result + ' <option value="YTD" >YTD</option>';
				result = result + '   <option value="upToDate" >UpToDate</option>';
		        result = result + ' <option value="Last_Year">Last Year</option>';
		        result = result + ' <option value="custom">Custom</option>';
		        
	            /* 
				result = result + '   <option value="today">Today</option>';
				result = result + '   <option value="yesterday">Yesterday</option>';
				result = result + '   <option value="week">1 Week ago</option>';
				result = result + '   <option value="twoweeks">2 Weeks ago</option>';
				result = result + '   <option value="month" selected>1 Month ago</option>';
				result = result + '   <option value="twomonths">2 Months ago</option>';
				result = result + '   <option value="YTD" >YTD</option>';
				result = result + '   <option value="upToDate" >UpToDate</option>';
				result = result + '    <option value="custom">Custom</option> '; */
				result = result + ' </select>';
				result = result + ' </div>';
				
				result = result + '<div class="col-md-6" id="custom" style="display:none;">';
				result = result + '<form class="form-inline" id="projectTasksReports_form" name="projectTasksReports_form">';
				result = result + '   <div class="form-group" id="startDateErr">';
				result = result + '   <input type="text" class="form-control calendar" id="startDate" placeholder="Start Date" name="startdate" field-name="Start Date"  data-validation="required" readonly>';
				result = result + '   </div>';
				result = result + '   <div class="form-group" id="endDateErr">';
				result = result + '   <input type="text" class="form-control calendar" id="endDate" placeholder="End Date" name="enddate" field-name="End Date"  data-validation="required" readonly>';
				result = result + '   </div>'; 
				result = result + '   <button type="button" class="btn btn-primary" onclick="submitBtn('+projectId+');">Submit</button>';
				 result = result + ' 	</form>';
				result = result + '</div>'; 
				result = result + '</div>';
			
			result = result + '	</div>';
			
			
			
			
			
			 
			
			
			result = result + '	<div class="col-md-12 mb" id="tasksStatusCoutnDiv" style="display:block;">                                                                             ';
			result = result + '	<div class="projectDashboad">                                                                             ';
			result = result + '	  <div class="row workstatus-titles">                                                                     ';
			result = result + '	    <div class="col-md-4 amchartAnchor-Hide">                                                             ';
			result = result + '	      <h5 class="ml">Work Status</h5>                                                                     ';
			result = result + '	      <div id="projectsWorkStatus" style="width: 100%; min-height: 180px;"> </div>                        ';
			result = result + '	    </div>                                                                                                ';
			
			result = result + '	    <!--new-->                                                                                           ';
			result = result + '	    <div class="col-md-2 amchartAnchor-Hide">                                                             ';
			result = result + '	      <h5 class="ml">New</h5>                                                                            ';
			result = result + '	      <div id="workstatusopen" style="width: 100%; min-height: 200px;"></div>                             ';
			result = result + '	    </div>                                                                                                ';
			
			
			result = result + '	    <!--Inprogress-->                                                                                     ';
			result = result + '	    <div class="col-md-2 amchartAnchor-Hide">                                                             ';
			result = result + '	      <h5 class="ml">In Progress</h5>                                                                     ';
			result = result + '	      <div id="workstatusinprogress" style="width: 100%; min-height: 200px;"></div>                       ';
			result = result + '	    </div>                                                                                                ';
			
			result = result + '	    <!--Done-->                                                                                           ';
			result = result + '	    <div class="col-md-2 amchartAnchor-Hide">                                                             ';
			result = result + '	      <h5 class="ml">Done</h5>                                                                            ';
			result = result + '	      <div id="workstatusdone" style="width: 100%;  min-height: 200px;"></div>                            ';
			result = result + '	    </div>                                                                                                ';
			
			result = result + '	    <!--on-Hold-->                                                                                        ';
			result = result + '	    <div class="col-md-2 amchartAnchor-Hide">                                                             ';
			result = result + '	      <h5 class="ml">On Hold</h5>                                                                         ';
			result = result + '	      <div id="workstatusonhold" style="width: 100%; min-height: 200px;"></div>                           ';
			result = result + '	    </div>                                                                                                ';
			
			
			
			result = result + '	  </div>                                                                                                  ';
			result = result + '	  <!-- Work Status -->                                                                                    ';
			result = result + '	</div>                                                                                                    ';
			result = result + '	</div>                                                                                                    ';
			result = result + '	<div class="clearfix"></div>                                                                              ';
							
			result = result + '	           <div class="col-md-12" id="projectReportDiv" style="display:none;">';
			result = result + '		            <div class="projectDashboad clearfix">';
			result = result + '		               <div id="projectReport" style="width: 100%; height: 400px;" class="clearfix mb"></div>';
			result = result + '		            </div>';
			result = result + '	           </div>';
			result = result + '     </div>'; 
			
			/* =======================  Tasks Progress Chart ======================= */
			result = result + '	 <div class="clearfix mt">';
			result = result + '        	<div class="col-md-6 mb">';
			result = result + '	                <div class="projectDashboad-widget">';
			result = result + '			            <div class="clearfix projectDashboad-header">';
			result = result + '			              <h4>Tasks Progress</h4>';
			result = result + '			            </div>';
			result = result + '	                <div id="tasksprogressChart" style="height: 225px; width: 100%;">';
			result = result + '	                </div>';
			result = result + '	            </div>';
			result = result + '        </div>';
			
			/* =======================  Overdue task div ======================= */
			result = result + '   <div id="overdueTaskDivId"></div>';
				
			result = result + '	<div class="clearfix"></div>';
							
		 
			
			/* ================== Expenses and Timesheet reports =============== */
			
		    result = result + '	<div class="clearfix mt">';
			result = result + '    <div class="col-md-6">';
			result = result + '     <div class="projectDashboad clearfix positionRelative">';
			result = result + '         <h4>Expenses</h4>';
			result = result + '        <div id="expenses-report" style="width: 100%; height: 150px;" class="clearfix"></div>';
			result = result + '        </div>';
			result = result + '    </div>';
			result = result + '     <div class="col-md-6">';
			result = result + '     <div class="projectDashboad clearfix positionRelative">';
			result = result + '         <h4>Timesheet</h4>';
			result = result + '        <div id="timesheet-report" style="width: 100%; height: 150px;" class="clearfix"></div>';
			result = result + '         <div class="timesheet-list">';
			result = result + '         </div>';
			
			result = result + '     </div>';
			result = result + '    </div>';
			result = result + ' </div>';
			 
			
			/* =================   Budget Status  ================  */
			result = result + '	<div class="clearfix mt">';
			result = result + '  <div class="col-md-6 mb">';
			result = result + '      <div class="projectDashboad-widget">';
			result = result + '  <div class="clearfix projectDashboad-header">';
			result = result + '    <h4>Budget Status</h4>';
			result = result + '    <p>Track budget and manage work proactively</p>';
			result = result + '  </div>';
			result = result + '  <div id="budgetStatusDivId"></div>';
			result = result + '  </div>';
			result = result + '      </div>';  
			
			result = result + '	<div class="clearfix"></div>';
			
			
			
			/* =============== Overdue Work Items =============== */
			
		 	/* result = result + '      <div class="col-md-3">';
			result = result + '     <div class="projectDashboad-widget">';
			result = result + '      <div class="clearfix projectDashboad-header">';
			result = result + '        <h4>Top 5 Taskminators</h4>';
			result = result + '        <p>Users with maximum closed tasks</p>';
			result = result + '      </div>';
			result = result + '          <div class="OverdueWorkItems" style="height: 225px; width: 100%;">';
			result = result + '             <div class="column-data clearfix mt scroll-pane">';
			result = result + '                 <div class="media">';
			result = result + '                     <div class="media-left">';
			result = result + '                     <a href="#">';
			result = result + '                         <img alt="64x64" class="media-object" style="width: 25px; height: 25px;" title="" src="/resources/images/userprofile-img.png">';
			result = result + '                     </a>';
			result = result + '                     </div>';
			result = result + '                     <div class="media-body">';
			result = result + '                         <div class="taskminators-txt">';
			result = result + '                         <p title="Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque.  ">';
			result = result + '                             Cras sit amet nibh libero.';
			result = result + '                         </p>';
			result = result + '                         </div>';
			result = result + '                             <span class="pull-right taskminators-count pull-right">30</span>';
			result = result + '                     </div>';
			result = result + '                 </div>';
			result = result + '              </div>';
			result = result + '          </div>';
			result = result + '     </div>';
			result = result + '  </div>';  */
			
			/* ==================  Overdue Work Items ================  */
			 /* result = result + '	<div class="col-md-3">'; 
			result = result + '	  <div class="projectDashboad-widget">';
			result = result + '	    <div class="clearfix projectDashboad-header">';
			result = result + '	      <h4>Top 5 Bugminators</h4>';
			result = result + '	      <p>Users with maximum closed issues</p>';
			result = result + '	    </div>';
			result = result + '	    <div class="OverdueWorkItems" style="height: 225px; width: 100%;">';
			result = result + '	      <div class="column-data clearfix mt scroll-pane">';
			result = result + '	        <div class="media">';
			result = result + '	          <div class="media-left"> <a href="#"> <img alt="64x64" class="media-object" style="width: 25px; height: 25px;" title="" src="/esources/images/userprofile-img.png"> </a> </div>';
			result = result + '	          <div class="media-body">';
			result = result + '	            <div class="taskminators-txt">';
			result = result + '	              <p title="Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque.  "> Cras sit amet nibh libero. </p>';
			result = result + '	            </div>';
			result = result + '	            <span class="pull-right taskminators-count pull-right">30</span> </div>';
			result = result + '	        </div>';
			result = result + '	      </div>';
			result = result + '	    </div>';
			result = result + '	  </div>';
			result = result + '	</div>';  */
			
			
			
			result = result + '  </div>    ';
	                
		   		    
		    result = result + ' </div>';
     
                
                
                
			$("#tab-1").empty();
			$("#tab-3").empty();
			$("#tab-2").empty();
			$("#tab-4").empty();
			$("#tab-5").empty();
			$("#tab-5").append(result);
			$("#tab-5").show();

			var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
			  var finalDateFormatRepo = '';
			  if(locationDate.match("yyyy-MM-dd")){
				  finalDateFormatRepo = 'yy-mm-dd';
			  }if(locationDate.match("dd-MM-yyyy")){
				  finalDateFormatRepo = 'dd-mm-yy'; 
			  }if(locationDate.match("MM-dd-yyyy")){
				  finalDateFormatRepo = 'mm-dd-yy'; 
			  }if(locationDate.match("yyyy-dd-MM")){
				  finalDateFormatRepo = 'yy-dd-mm'; 
			  }
			  
			  $('#startDate').datepicker({
					changeMonth: true,
					changeYear: true, 
					dateFormat : finalDateFormatRepo,
					onSelect : function(selected) {
						var dt = new Date(selected);
						$("#endDate").datepicker("option", "minDate", selected);
						startDateErrMsgRemove();
					}
				
				});
				$('#endDate').datepicker({
					changeMonth: true,
					changeYear: true, 
					dateFormat : finalDateFormatRepo,
					beforeShow: function(){
				           this.value='';
				     },
					onSelect : function(selected) {
						var dt = new Date(selected);
						$("#billingReportStartDate").datepicker("option", "maxDate", selected);
						endDateErrMsgRemove();
					}
				});
				
			/* ================= Expenses and Timesheet reports Start=====================*/
			
			
			
			
			/* ================= Expenses and Timesheet reportsscript Ends=====================*/
			
			$(".project-tabs-menu a").click(
					function(event) {
						event.preventDefault();
						$(this).parent().addClass("current");
						$(this).parent().siblings().removeClass(
								"current");
						var tab = $(this).attr("href");
						$(".project_tabs_inn").not(tab).css(
								"display", "none");
						$(tab).fadeIn();
					});


	}
	function startDateErrMsgRemove(){
		$('#startDateErr').find('p.jquery_form_error_message').remove(); 
	}
	function endDateErrMsgRemove(){
		$('#endDateErr').find('p.jquery_form_error_message').remove(); 
	}
</script>
<script>
function taskReports(projectId,reportType,startDate,endDate){
	$.ajax({
        type : "POST",
         url: sourceleadUrl+"/ProjectTasks/getProjectTasksReportsListByProjectId?"+tokenParamName+"="+tokenValue+"&projectId="+projectId+"&reportType="+reportType+"&startDate="+startDate+"&endDate="+endDate,
         success :function(data) {
        		 getDonutChartDetails(data)
        	 
         }
	});
}

function stackTaskReport(projectId,reportType,startDate,endDate){
	$.ajax({
        type : "POST",
         url: sourceleadUrl+"/ProjectTasks/getProjectTasksAStackedReportsListByProjectId?"+tokenParamName+"="+tokenValue+"&projectId="+projectId+"&reportType="+reportType+"&startDate="+startDate+"&endDate="+endDate,
         success :function(data) {
        		 getStackedAreaChartDetails(data)
         }
	});
}
function getStackedAreaChartDetails(data){
	document.getElementById("projectReportDiv").style.display="block";
	document.getElementById("tasksStatusCoutnDiv").style.display="none"; 
		 var chartData = [];
		   for (var i = 0; i < data.length; i++) { 
		 		 var opt = data[i]; 
		         chartData.push({
		        		date: opt.date,
						New: opt.newCount,
						OnHold: opt.onHoldCount,
						InProgress: opt.inProgressCount,
						Done: opt.doneCount
		       });
		   }
		   

	  
	  AmCharts.makeChart("projectReport",
			 {
				"type": "serial",
				"categoryField": "date",
				"dataDateFormat": "MM-DD-YYYY",
				"autoMarginOffset": 0,
				"marginBottom": 0,
				"marginLeft": 0,
				"marginRight": 0,
				"marginTop": 0,
				"minMarginBottom": 0,
				"minMarginLeft": 0,
				"minMarginRight": 0,
				"minMarginTop": 0,
				"colors": [],
				"backgroundAlpha": 0.8,
				"export": {
					"enabled": true
				},
				    "categoryAxis": {
					"parseDates": true,
					"axisThickness": 0,
					"gridCount": 0,
			        "gridPosition": "start",
					"minorGridEnabled": true
				},
				"chartScrollbar": {
					"enabled": true
				},
				"chartCursor": {
					"enabled": true,
					"bulletSize": 10,
					"cursorPosition": "start",
					"leaveCursor": true,
					"zoomable": false
				},
				"trendLines": [],
				"graphs": [
					{
						"balloonColor": "#67b7dc",
						//"balloonText": "[[title]] [[value]] P1:"+data.newCounts.p1Count+", P2:"+data.newCounts.p2Count+", P3:"+data.newCounts.p3Count+", P4:"+data.newCounts.p4Count+"",
						"color": "#FFD600",
						"customMarker": "",
						"fillAlphas": 1,
						"fillColors": "#67b7dc",
						"fixedColumnWidth": 0,
						"id": "new",
						"legendAlpha": 0.67,
						"legendColor": "#67b7dc",
						"lineAlpha": 0,
						"negativeFillAlphas": 1,
						"negativeFillColors": "#67b7dc",
						"title": "New",
						"valueField": "New"
					},
					{
						//"balloonText": "[[title]] [[value]] P1:"+data.onHoldCounts.p1Count+", P2:"+data.onHoldCounts.p2Count+", P3:"+data.onHoldCounts.p3Count+", P4:"+data.onHoldCounts.p4Count+"",
						"clustered": false,
						"fillAlphas": 0.9,
						"fillColors": "#ffb74d",
						"id": "onhold",
						"legendAlpha": 1,
						"legendColor": "#FF8000",
						"legendPeriodValueText": "",
						"legendValueText": "",
						"lineAlpha": 0,
						"markerType": "square",
						"title": "On-Hold",
						"valueField": "OnHold"
					},
					{
						//"balloonText": "[[title]] [[value]] P1:"+data.inprogressCounts.p1Count+", P2:"+data.inprogressCounts.p2Count+", P3:"+data.inprogressCounts.p3Count+", P4:"+data.inprogressCounts.p4Count+"",
						"clustered": false,
						"fillAlphas": 0.9,
						"fillColors": "#a9cf55",
						"id": "inProgress",
						"legendColor": "#6FD432",
						"negativeFillAlphas": 0,
						"negativeFillColors": "#6FD432",
						"title": "In-Progress",
						"valueField": "InProgress",
			            "lineAlpha": 0,
					},
					{
						"balloonColor": "#57619b",
						//"balloonText": "[[title]] [[value]] P1:"+data.doneCounts.p1Count+", P2:"+data.doneCounts.p2Count+", P3:"+data.doneCounts.p3Count+", P4:"+data.doneCounts.p4Count+"",
						"clustered": false,
						"cornerRadiusTop": 7,
						"fillAlphas": 1,
						"fillColors": "#57619b",
						"id": "done",
						"legendColor": "#57619b",
						"tabIndex": -1,
						"title": "Done",
						"valueField": "Done",
			            "lineAlpha": 0,
					}
				],
				"guides": [],
				"valueAxes": [
					{
						"id": "ValueAxis-1",
						"offset": 1,
						"title": ""
					}
				],
				"allLabels": [],
				"balloon": {},
				"legend": {
					"enabled": true,
					"valueWidth": 0
				},
				"titles": [
					{
						"id": "sourcelead",
						"size": 15,
						"text": ""
					}
				],
				"dataProvider":chartData
			}
			 ); 
}
function getDonutChartDetails(data){
	 var mainChartData = [
			{
				"category": "New",
				"column-1": data.newCount
			},
			{
				"category": "In Progress",
				"column-1": data.inProgressCount
			},
			{
				"category": "Done",
				"column-1": data.doneCount
			},
			{
				"category": "On Hold",
				"column-1": data.onHoldCount
			}
		];
	var  mainChartCount = data.newCount + data.doneCount + data.inProgressCount + data.onHoldCount;
	AmCharts.makeChart("projectsWorkStatus",
			{
				"type": "pie",
				"adjustPrecision": true,
				"balloonText": "[[title]]<br><span style='font-size:14px'><b>[[value]]</b>([[percents]]%)</span>",
				"innerRadius": "80%",
				"labelRadius": -20,
				"labelText": "",
				"pieX": "50%",
				"pieY": "40%",
				"pullOutRadius": "9%",
				"startRadius": "0%",
				"alpha": 0,
				"alphaField": "column-1",
				"colors": [
					"#f26950",
					"#ffce42",
					"#a9cf55",
					"#3fc4df"
				],
				"descriptionField": "category",
				"labelsEnabled": false,
				"marginBottom": 0,
				"marginTop": 0,
				"outlineThickness": 7,
				"pullOutDuration": 5,
				"pullOutOnlyOne": true,
				"startAlpha": 1,
				"titleField": "category",
				"valueField": "column-1",
				"handDrawScatter": 20,
				"handDrawThickness": 10,
				"percentPrecision": 0,
				"allLabels": [
					{
						"align": "center",
						"color": "#283150",
						"id": "count",
						"size": 22,
						"text": mainChartCount,
						"x": 0,
						"y": 60
					}
				],
				"balloon": {},
				"legend": {
					"enabled": true,
					"combineLegend": true,
					"labelWidth": 75,
					"marginRight": 0,
					"markerLabelGap": 10,
					"markerSize": 15,
					"periodValueText": "",
					"position": "left",
					"tabIndex": 1,
					"top": 0,
					"width": 200
				},
				"titles": [],
				"dataProvider": mainChartData
				});
		
	
	var newChartData = [{
							"category": "P1",
							"column-1": data.newCounts.p1Count,
							"color": "#f26950"
						},
						{
							"category": "P2",
							"column-1": data.newCounts.p2Count,
							"color": "#e08686"
						},
						{
							"category": "P3",
							"column-1": data.newCounts.p3Count,
							"color": "#ffce43"
						},
						{
							"category": "P4",
							"column-1": data.newCounts.p4Count,
							"color": "#aab3b3"
						}];

					//WorkStatus: open
				AmCharts.makeChart("workstatusopen",{
					"type": "pie",
					"balloonText": "[[title]]<br><span style='font-size:14px'><b>[[value]]</b> ([[percents]]%)</span>",
					"innerRadius": "80%",
					"labelRadius": 10,
					"pieX": "50%",
					"pieY": "50%",
					"radius": "49%",
					"baseColor": "#",
					"colorField": "color",
					"colors": [],
					"groupedAlpha": 0,
					"labelsEnabled": false,
					"titleField": "category",
					"valueField": "column-1",
					"allLabels": [],
					"balloon": {
						"fixedPosition": false
					},
					"legend": {
						"enabled": true,
						"align": "center",
						"marginLeft": 0,
						"marginRight": 0,
						"markerSize": 15,
						"valueWidth": 10
					},
					"titles": [],
					"dataProvider": newChartData
				});
	
	
				//WorkStatus: Done
				AmCharts.makeChart("workstatusdone",
						{
					"type": "pie",
					"balloonText": "[[title]]<br><span style='font-size:14px'><b>[[value]]</b> ([[percents]]%)</span>",
					"innerRadius": "80%",
					"labelRadius": 10,
					"pieX": "50%",
					"pieY": "50%",
					"radius": "49%",
					"baseColor": "#",
					"colorField": "color",
					"colors": [],
					"groupedAlpha": 0,
					"labelsEnabled": false,
					"titleField": "category",
					"valueField": "column-1",
					"allLabels": [],
					"balloon": {
						"fixedPosition": false
					},
					"legend": {
						"enabled": true,
						"align": "center",
						"marginLeft": 0,
						"marginRight": 0,
						"markerSize": 15,
						"valueWidth": 10
					},
					"titles": [],
					"dataProvider": [
						{
							"category": "P1",
							"column-1": data.doneCounts.p1Count,
							"color": "#f26950"
						},
						{
							"category": "P2",
							"column-1": data.doneCounts.p2Count,
							"color": "#e08686"
						},
						{
							"category": "P3",
							"column-1": data.doneCounts.p3Count,
							"color": "#ffce43"
						},
						{
							"category": "P4",
							"column-1": data.doneCounts.p4Count,
							"color": "#aab3b3"
						}
					]
				});

	
		//WorkStatus: Onhold
		AmCharts.makeChart("workstatusonhold",
				{
			"type": "pie",
			"balloonText": "[[title]]<br><span style='font-size:14px'><b>[[value]]</b> ([[percents]]%)</span>",
			"innerRadius": "80%",
			"labelRadius": 10,
			"pieX": "50%",
			"pieY": "50%",
			"radius": "49%",
			"baseColor": "#",
			"colorField": "color",
			"colors": [],
			"groupedAlpha": 0,
			"labelsEnabled": false,
			"titleField": "category",
			"valueField": "column-1",
			"allLabels": [],
			"balloon": {
				"fixedPosition": false
			},
			"legend": {
				"enabled": true,
				"align": "center",
				"marginLeft": 0,
				"marginRight": 0,
				"markerSize": 15,
				"valueWidth": 10
			},
			"titles": [],
			"dataProvider": [
				{
					"category": "P1",
					"column-1": data.onHoldCounts.p1Count,
					"color": "#f26950"
				},
				{
					"category": "P2",
					"column-1": data.onHoldCounts.p2Count,
					"color": "#e08686"
				},
				{
					"category": "P3",
					"column-1": data.onHoldCounts.p3Count,
					"color": "#ffce43"
				},
				{
					"category": "P4",
					"column-1": data.onHoldCounts.p4Count,
					"color": "#aab3b3"
				}
			]
		});
	
	
		//WorkStatus: Inprogress
		AmCharts.makeChart("workstatusinprogress",
				{
			"type": "pie",
			"balloonText": "[[title]]<br><span style='font-size:14px'><b>[[value]]</b> ([[percents]]%)</span>",
			"innerRadius": "80%",
			"labelRadius": 10,
			"pieX": "50%",
			"pieY": "50%",
			"radius": "49%",
			"baseColor": "#",
			"colorField": "color",
			"colors": [],
			"groupedAlpha": 0,
			"labelsEnabled": false,
			"titleField": "category",
			"valueField": "column-1",
			"allLabels": [],
			"balloon": {
				"fixedPosition": false
			},
			"legend": {
				"enabled": true,
				"align": "center",
				"marginLeft": 0,
				"marginRight": 0,
				"markerSize": 15,
				"valueWidth": 10
			},
			"titles": [],
			"dataProvider": [
				{
					"category": "P1",
					"column-1": data.inprogressCounts.p1Count,
					"color": "#f26950"
				},
				{
					"category": "P2",
					"column-1": data.inprogressCounts.p2Count,
					"color": "#e08686"
				},
				{
					"category": "P3",
					"column-1": data.inprogressCounts.p3Count,
					"color": "#ffce43"
				},
				{
					"category": "P4",
					"column-1": data.inprogressCounts.p4Count,
					"color": "#aab3b3"
				}
			]
		});
}
</script>
 
 
 <script>
 function budgetStatusReport(projectId,reportType,startDate,endDate){
		$.ajax({
	        type : "POST",
	         url: sourceleadUrl+"/ProjectTasks/geBudgetStatusReport?"+tokenParamName+"="+tokenValue+"&projectId="+projectId+"&reportType="+reportType+"&startDate="+startDate+"&endDate="+endDate,
	         success :function(data) {
	        	 var remainingBudget = 0;
	        	 var  remainingHours = 0;
	        	 if(data.budgetTotal > 0){
	        		 remainingBudget  = data.budgetTotal - data.actualBudget;
	        	 }
	        	 if(data.estimatedHours > 0){
	        		 remainingHours  = data.estimatedHours - data.actualHours;
	        	 }
	        	  
	        	 if(data.currency != null && data.currency != "" && data.currency != "undefined"){
	        		 currency = data.currency;
	        	 }else{
	        		 currency = "";
	        	 }
		        	 var result = ""
	        		 result = result + '<div class="clearfix"></div>';
		        	 result = result + '          <div class="row">';
	        		 result = result + '          <div class="col-md-6">';
	        		if(remainingBudget > 0){
	        		result = result + '              <label class="form-control-static">Remaining Budget: <span class="paddingLeft"><strong>'+currency+' '+remainingBudget+'</strong></span></label>';
	        		}
	        		result = result + '              <div id="budgetStatus" style="height: 180px; width: 100%;">';
		 			result = result + '              </div>';
		 			result = result + '          </div>';
	        	
	        		result = result + '          <div class="col-md-6">';
	        		if(remainingHours > 0){
	        		result = result + '              <p class="form-control-static">Remaining Hours: <span class="paddingLeft"><strong>'+remainingHours+' Hours</strong></span></p>';
	        		}
	        		result = result + '              <div id="hoursStatus" style="height: 180px; width: 100%;">';
		 			result = result + '              </div>';
		 			result = result + '          </div>';
		 			
	        	
	        	result = result + '      </div>';
	 			$("#budgetStatusDivId").empty();
	 			$("#budgetStatusDivId").append(result);
	        	 budgetTotal(data)
	        	 hoursStatusReport(data);
	         }
		});
	}
 function budgetTotal(data){
	 var budgetTotal = "";
	 var currency = "";
	 if(data.currency != null && data.currency != '' && data.currency !='undefined'){
		 currency = data.currency;
	 }
	 if(data.invoiceCurrency != null && data.invoiceCurrency != "" && data.invoiceCurrency != "undefined"){
		 invoiceCurrency = data.invoiceCurrency;
	 }else{
		 invoiceCurrency = "";
	 }
	 var totalBudgetChart  = [
			{
				"category": ""+currency+" "+data.budgetTotal+" <br> Budget",
				"column-1": data.budgetTotal,
				"color": "#42a5f5"
			},
			{
				"category": ""+invoiceCurrency+" "+data.actualBudget+" <br> Expended",
				"column-1": data.actualBudget,
				"color": "#e57373"
			}
		];
	 
	 AmCharts.makeChart("budgetStatus",
				{
	 "type": "serial",
	 "categoryField": "category",
	 "startDuration": 1,
     "marginTop": 0,
     "marginLeft": 0,
     "marginRight": 0,
	 "categoryAxis": {
		"gridPosition": "start"
	},
	"trendLines": [],
	"graphs": [
		{
			"balloonText": "[[category]]",
			"colorField": "color",
			"fillAlphas": 1,
			"id": "AmGraph-1",
			"lineColorField": "color",
			"title": "graph 1",
			"type": "column",
			"valueField": "column-1"
		}
	],
	"guides": [],
	"valueAxes": [
		{
			"id": "ValueAxis-1",
			"autoGridCount": false,
			"axisAlpha": 0,
			"axisThickness": 0,
			"gridAlpha": 0,
			"gridCount": 0,
			"gridThickness": 0,
			"ignoreAxisWidth": true,
			"labelsEnabled": false,
			"minorGridAlpha": 0,
			"tickLength": 0
		}
	],
	"allLabels": [],
	"balloon": {},
	"titles": [],
	"dataProvider": totalBudgetChart
});
 }
 </script>
 
 <script>
 
 function hoursStatusReport(data){
	 var budgetHours = [
			{
				"category": ""+data.estimatedHours+" <br> Estimated Hours",
				"column-1": data.estimatedHours,
				"color": "#42a5f5"
			},
			{
				"category": ""+data.actualHours+" <br> Actual Hours",
				"column-1": data.actualHours,
				"color": "#e57373"
			}
		];
	 AmCharts.makeChart("hoursStatus",
				{
		"type": "serial",
		"categoryField": "category",
	    "marginTop": 0,
	     "marginLeft": 0,
	     "marginRight": 0,

		"startDuration": 1,
		"categoryAxis": {
			"gridPosition": "start"
		},
		"trendLines": [],
		"graphs": [
			{
				"balloonText": "[[category]]",
				"colorField": "color",
				"fillAlphas": 1,
				"id": "AmGraph-1",
				"lineColorField": "color",
				"title": "graph 1",
				"type": "column",
				"valueField": "column-1"
			}
		],
		"guides": [],
		"valueAxes": [
			{
				"id": "ValueAxis-1",
				"autoGridCount": false,
				"axisAlpha": 0,
				"axisThickness": 0,
				"gridAlpha": 0,
				"gridCount": 0,
				"gridThickness": 0,
				"ignoreAxisWidth": true,
				"labelsEnabled": false,
				"minorGridAlpha": 0,
				"tickLength": 0
			}
		],
		"allLabels": [],
		"balloon": {},
		"titles": [],
		"dataProvider": budgetHours
	});	
 }
 
 </script>
 
 <script>
 function tasksprogressChart(projectId,reportType,startDate,endDate) {
	 $.post(sourceleadUrl+"/ProjectTasks/getTasksProgressByProjectId?"+tokenParamName+"="+tokenValue+"&projectId="+projectId+"&reportType="+reportType+"&startDate="+startDate+"&endDate="+endDate, function(data) {
		 getTaskPercentge(data)
	 });
	 
	 
function getTaskPercentge(data){
	
	var percentageChartData = [
								{
									"category": "0%",
									"column-1": data.zeroCount
								},
								{
									"category": "10%",
									"column-1": data.tenCount
								},
								{
									"category": "20%",
									"column-1": data.twentyCount
								},
								{
									"category": "30%",
									"column-1": data.thirtyCount
								},
								{
									"category": "40%",
									"column-1": data.fortyCount
								},
								{
									"category": "50%",
									"column-1": data.fiftyCount
								},
								{
									"category": "60%",
									"column-1": data.sixtyCount
								},
								{
									"category": "70%",
									"column-1": data.seventyCount
								},
								{
									"category": "80%",
									"column-1": data.eightyCount
								},
								{
									"category": "90%",
									"column-1": data.nintyCount
								},
								{
									"category": "100%",
									"column-1": data.hundredCount
								}
							];
	
	//Tasks progress Chart
	AmCharts.makeChart("tasksprogressChart",
		{
			"type": "serial",
			"categoryField": "category",
			"startDuration": 1,
			"categoryAxis": {
				"gridPosition": "start"
			},
			"trendLines": [],
			"graphs": [
				{
					"balloonColor": "#008000",
					"bullet": "round",
					"bulletBorderAlpha": 1,
					"bulletBorderThickness": 3,
					"color": "undefined",
					"columnWidth": 0,
					"dashLength": 15,
					"fillAlphas": 0.36,
					"fillColors": "#FF8000",
					"id": "AmGraph-3",
					"title": "graph 3",
					"valueField": "column-1"
				}
			],
			"guides": [],
			"valueAxes": [
				{
					"id": "ValueAxis-1",
					"stackType": "regular",
					"title": "Task Count"
				}
			],
			"allLabels": [],
			"balloon": {},
			"titles": [
				{
					"id": "Title-1",
					"size": 14,
					"text": "Competetion Percentage"
				}
			],
			"dataProvider":percentageChartData 
		}
	);
	}
}
 
 </script>
 
 
 <script>
 function overdueTask(projectId,reportType,startDate,endDate){
	 var projectClientUUID = '${projectClientUUID}';
	 $.post(sourceleadUrl+"/ProjectTasks/getOverdueTaskListByProjectId?"+tokenParamName+"="+tokenValue+"&projectId="+projectId+"&reportType="+reportType+"&startDate="+startDate+"&endDate="+endDate, function(json) {
		var result = "";
	    result = result + '  <div class="col-md-6 mb">';
		result = result + '      <!-- Overdue Work Items -->';
		result = result + '     <div class="projectDashboad-widget">';
		result = result + '      <div class="clearfix projectDashboad-header">';
		result = result + '          <div class="clearfix">';
		result = result + '              <div class="pull-left">';
		result = result + '                  <h4>Overdue task</h4>';
		result = result + '                  <p>Know the work items that are past their due date</p>';
		result = result + '              </div>';
		result = result + '          </div>';
		result = result + '         </div>';
		
		if(json != null && json != "" && json != "undefined"){
			result = result + '         <div class="OverdueWorkItems mt10">';
			result = result + '             <table width="100%;">';
			result = result + '                 <tbody>';
			 for(var i=0; i<json.length; i++){
				 var report = json[i];
				 var generatedId = "";
				 if(report.generatedId != null && report.generatedId != "" && report.generatedId != "undefied"){
					 generatedId = report.generatedId +" -";
				 } 
				   result = result + '                 <tr>';
				   if(report.taskTypes == "Epic"){
					   result = result + '   <td><i class="fa fa-bolt text-info" title="Epic"></i></td>';
				   }else if(report.taskTypes == "UserStory"){
					   result = result + '  <td><i class="fa fa-lightbulb-o text-success" title="User Story"></i></td>';
				   }else if(report.taskTypes == "Bug"){
					   result = result + '   <td><i class="fa fa-bug text-danger" title="Bug"></i></td>';
				   }else if(report.taskTypes == "SubTask"){
						result = result + '    <td><i class="fa fa-pencil-square text-primary" title="Task"></i></td>';
				   }else if(report.taskTypes == "General"){
					   result = result + '                     <td><i class="fa fa-spinner text-warning" title="General Task"></i></td>';
				   }
				   
					result = result + '                     <td><span title="'+generatedId+' '+report.taskName+'" ><a href="'+sourceleadUrl+'/projectTaskSummary?taskUUID='+report.projectTasksUUID+'&projectClientUUID='+projectClientUUID+'">'+generatedId+' '+report.taskName+'</a></span></td>';
					result = result + '                     <td>  '+report.actionTimeStr+' </td>';
					result = result + '                     <td> '+report.percentage+'% </td>';
					result = result + '                     <td>';
					result = result + '                       <div class="text-center">';
					
					if(report.taskAssignedUsers.length == 0){
						result = result + '       <img class="media-object" style="width: 25px; height: 25px;" src="/resources/images/qmrk-userImg.png" title="Not Assigned">';
					}else if(report.taskAssignedUsers.length == 1){
						if(report.userFileUrl != null){
							result = result + '       <img class="media-object" style="width: 25px; height: 25px;" src='+report.userFileUrl+' title="'+report.userName+'">';
						}else{
							result = result + '       <img class="media-object" style="width: 25px; height: 25px;" src="/resources/images/userprofile-img.png" title="'+report.userName+'">';
						}
					}else {
						result = result + '       <img class="media-object" style="width: 25px; height: 25px;" src="/resources/images/multiuser.png" title="'+report.taskAssignedUsers+'">';
					}
					 
					result = result + '                        </div>';
					result = result + '                     </td>';
					result = result + '                     <td>';
					result = result + '                         <span>'+report.taskStaus+'</span>';
					result = result + '                     </td>';
					result = result + '                 </tr>';
			   }
			    result = result + '                 </tbody>';
				result = result + '             </table>';
				result = result + '                  </div>';
		}else{
			result = result + '<div class="dataTables_empty"></div>';
		}
		
			result = result + '              </div>';
			result = result + '      </div>';
			result = result + '     </div>';
		 
			$("#overdueTaskDivId").empty();
			$("#overdueTaskDivId").append(result);
		});
 }
 </script>
 
 <script>
 function timesheetReport(projectId,reportType,startDate,endDate){
	 $.post(sourceleadUrl+"/ProjectTasks/getTimesheetTotatalHoursByProjectId?"+tokenParamName+"="+tokenValue+"&projectId="+projectId+"&reportType="+reportType+"&startDate="+startDate+"&endDate="+endDate, function(data) {
		 timesheetReportChart(data);
	 });
 }
 </script>
 <script>
 function timesheetReportChart(data) {
 AmCharts.makeChart("timesheet-report",
		 {
		"type": "pie",
		"adjustPrecision": true,
		"balloonText": "[[title]]: <span style='font-size:14px'>[[value]]</span> hrs<br>Billable:   <span style='font-size:14px'>[[billable]]</span> hrs<br>Non-Billable:  <span style='font-size:14px'> [[nonBillable]]</span> hrs",
		"innerRadius": "60%",
		"labelRadius": -25,
		"minRadius": 0,
		"pieX": "50%",
		"pieY": "50%",
		"pullOutRadius": "0%",
		"radius": "48%",
		"startRadius": "0%",
		"alpha": 0,
		"alphaField": "column-1",
		"colors": [
			"#a9cf55",
			"#ffce42",
			"#3fc4df",
			"#f26950"
		],
		"descriptionField": "category",
		"labelsEnabled": false,
		"marginBottom": 0,
		"marginTop": 0,
		"outlineThickness": 7,
		"pullOutDuration": 0,
		"pullOutOnlyOne": true,
		"startAlpha": 1,
		"titleField": "category",
		"valueField": "column-1",
		"accessibleTitle": "",
		"handDrawScatter": 20,
		"handDrawThickness": 10,
		"percentPrecision": 0,
		"allLabels": [],
		"balloon": {},
		"legend": {
			"enabled": true,
			"combineLegend": true,
			"labelWidth": 75,
			"marginRight": 0,
			"markerLabelGap": 10,
			"markerSize": 15,
			"periodValueText": "",
			"position": "left",
			"tabIndex": 1,
			"top": 0,
			"valueText": "[[value]] hrs",
			"width": 200
		},
		"titles": [],
		"dataProvider": [
			{
				"category": "Approved",
				"column-1": data.approvedHours,
				"billable": data.approvedBillableHours,
				"nonBillable": data.approvedNonBillableHours
			},
			{
				"category": "Submitted",
				"column-1": data.submittedHours,
				"billable": data.submittedBillableHours,
				"nonBillable": data.submittedNonBillableHours
			},
			{
				"category": "Saved",
				"column-1": data.savedHours,
				"billable": data.savedBillableHours,
				"nonBillable": data.savedNonBillableHours
			},
			{
				"category": "Rejected",
				"column-1": data.rejectedHours,
				"billable": data.rejectedBillableHours,
				"nonBillable": data.rejectedNonBillableHours
			}
		]
	}
);
 }
 </script>
 
 
 
 <script>
 function expensesReport(projectId,reportType,startDate,endDate){
	 $.post(sourceleadUrl+"/ProjectTasks/getExpensesByProjectId?"+tokenParamName+"="+tokenValue+"&projectId="+projectId+"&reportType="+reportType+"&startDate="+startDate+"&endDate="+endDate, function(data) {
		 expensesReportCahrt(data);
	 });
 }
 </script>
 <script>
 function expensesReportCahrt(data){
     AmCharts.makeChart("expenses-report", {
 		"type": "pie",
 		"balloonText": "[[title]] <br> $  [[value]]",
 		"innerRadius": "60%",
 		"labelText": "",
 		"pieX": "50%",
 		"pieY": "50%",
 		"radius": "45%",
 		"colorField": "color",
 		"colors": [],
 		"titleField": "category",
 		"valueField": "column-1",
 		"allLabels": [],
 		"balloon": {
 			"fixedPosition": false
 		},
 		"legend": {
 			"enabled": true,
 			"align": "center",
 			"markerLabelGap": 10,
 			"markerSize": 12,
 			"position": "left",
 			"valueText": "$ [[value]]"
 		},
 		"titles": [],
 		"dataProvider": [
 			{
 				"category": "Approved",
 				"column-1": data.approvedAmount,
 				"color": "#a9cf55"
 			},
 			{
 				"category": "Submitted",
 				"column-1": data.submittedAmount,
 				"color": "#3fc4df"
 			},
 			{
 				"category": "Rejected",
 				"column-1": data.rejectedAmount,
 				"color": "#f26950"
 			}
 		]
   });
	 
 }
 </script>
 