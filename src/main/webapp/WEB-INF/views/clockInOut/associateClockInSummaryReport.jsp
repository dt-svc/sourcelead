<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />


  <script type="text/javascript" src="https://www.amcharts.com/lib/3/amcharts.js"></script> 
<script type="text/javascript" src="https://www.amcharts.com/lib/3/serial.js"></script> 
<script type="text/javascript" src="https://www.amcharts.com/lib/3/pie.js"></script>
<script type="text/javascript" src="../js/amcharts_export.min.js"></script>


 <!-- Side Nav Bar --> 
 
 <script>
 $(document).ready(function(){
	 getCurrentDates('today');
    }); 
 </script>



 <!-- Content Area -->
  <section class="content-wraper contentWraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Associate Clocked</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Payhead List &raquo; Associate Clocked</div>
    </section>
    <div class="contentareaInner-styles clearfix">
      <div class="timesheet_out" id="payrollRegister" style="display: block;">
        <div class="clearfix">
          <div class="positionRelative reportHead clearfix">
           <div class="pull-left timeRepList">
                    <a title="Graphical View" id="graphicalId" onclick="graphicalViewPage();"><i class="fa fa-pie-chart"></i></a>
                    <a title="List View" id="listId" class="act" onclick="listViewPage();"><i class="fa fa-list"></i></a>
                    </div> 
                    
            <div class="clDate">
                <i class="fa fa-chevron-left" id="previousId" onclick="getCurrentDates('previous');"></i>
                <p id="frequencyID"></p>
                <i class="fa fa-chevron-right" id="nextId" onclick="getCurrentDates('next');"></i>
              </div>
            
            <ul class="pull-right list-unstyled list-inline">
              <li><a href="javascript:void(0)" class="filtIcon" title="Filters" onclick="openPop();"></a></li>
             <!--  <li class="noMar">
                <input class="repSearchBar" placeholder="Search" id="dynamicUserDataTableSearch" type="text">
                <span class="repSearchIcon" onclick="getDynamciUserList();"></span></li> -->
            </ul>
          </div>
          
                          <div class="repclo" id="graphId"  style="display: none;">
        <div id="jobsAdded" class="amchartAnchor-Hide timRep"></div>
        <div id="graphIdDiv" class=""></div>
    </div>
          
          <div class="require_wrap_table tableFirstSecond clearfix" id="associateClockInOutDiv"></div>
           <div id="uniqueDiv" class="associateEmpData_pop boxShadow_div"></div>
        </div>
      </div>
      
      <!-- Content Area Ends--> 
    </div>
  </section>

<div class="advancedsearch-mask" onclick="closeAdsrch()"></div>
  <div class="cand-advsrch-popup advancedsearch-popup filterDynamic-Search">
    <h5 class="headerAdsrch">Filters <a href="javascript:void(0)" class="close-div" onclick="closePop()"><i class="fa fa-close"></i></a> </h5>
    <div class="addInner">
        <div class="row">
            <div class="clearfix">
              <div class="col-md-6">
                <label>Department</label>
                <div class="clearfix mt5">
                    <select class="chosen-select" data-placeholder="Department" multiple name="departmentName"  id="departmentName">
	                    <c:forEach var="dept" items="${deptList}">
								<option value="${dept.id}">${dept.departmentName}</option>
						</c:forEach>
                    </select>
                </div>
              </div>
              <div class="col-md-6">
                  <label>Role</label>
                  <div class="clearfix mt5">
                      <select class="chosen-select" data-placeholder="Role" multiple name="userRole" id="userRole">
	                      <c:forEach var="roles" items="${rolesList}">
									<option value="${roles.id}">${roles.displayName}</option>
						  </c:forEach>	
                        </select>
                  </div>
                </div>
                <div class="clearfix"></div>
                
                <div class="col-md-6 mb10">
					<label>Frequency</label>
					<div class="clearfix mt5">
						<select class="sel form-control" id="freqID">
							<option value="today">Today</option>
							<option value="Last_7_days">This Week</option>
							<option value="current_month">This Month</option>
							<option value="Current_Year">This Year</option>
						</select>
					</div>
				  </div>
            </div>
              <div class="clearfix col-md-12 mt10">
                  <ul class="list-inline list-unstyled">
                      <li>
                          <span class="radioStyles mr">
                              <input id="jobs" name="applicableFor" type="radio" onclick="jobsFun();">
                              <label for="jobs">Jobs</label>
                          </span>
                      </li>
                      <li>
                          <span class="radioStyles mr">
                              <input id="project" name="applicableFor" type="radio" onclick="projectFun();">
                              <label for="project">Project</label>
                          </span>
                      </li>
                      <li>
                          <span class="radioStyles mr">
                              <input id="generalTask" name="applicableFor" type="radio" onclick="generaltaskFun();">
                              <label for="generalTask">General Task</label>
                          </span>
                      </li>
                  </ul>
              </div>

              <div class="clearfix">
                  <div class="col-md-4" id="jobsDiv">
                    <label>Jobs</label>
                    <div class="clearfix mt5">
                        <select class="chosen-select" data-placeholder="Jobs" multiple name="jobsList" id="jobsList">
                        	<c:forEach var="jobs" items="${jobsList}">
									<option value="${jobs.id}">${jobs.jobTitle}</option>
						  	</c:forEach>
                        </select>
                    </div>
                  </div>
                  <div class="col-md-4" id="projectDiv">
                      <label>Project</label>
                      <div class="clearfix mt5">
                          <select class="chosen-select" data-placeholder="Project" multiple name="projectList" id="projectList" onchange="getProjectTasksByProject();">
	                          <c:forEach var="project" items="${projectList}">
										<option value="${project.id}">${project.projectName}</option>
			  					</c:forEach>
                            </select>
                      </div>
                    </div>
                    <div class="col-md-4" id="taskDiv">
                        <label>Task</label>
                        <div class="clearfix mt5">
                            <select class="chosen-select" data-placeholder="Task" multiple name="taskList" id="taskList"></select>
                        </div>
                      </div>

                    <div class="col-md-4" id="generalTaskDiv">
                        <label>General Task</label>
                        <div class="clearfix mt5">
                            <select class="chosen-select" data-placeholder="General Task" multiple name="generalTaskList" id="generalTaskList"></select>
                        </div>
                      </div>
                      <div class="col-md-4">
                          <label>Associate</label>
                          <div class="clearfix mt5">
                              <select class="chosen-select" data-placeholder="Associate" multiple name="associate" id="associate">
                              <c:forEach var="userRole" items="${userRoleList}">
										<option value="${userRole.id}">${userRole.user.fullName}</option>
			  					</c:forEach>
                                </select>
                          </div>
                        </div>
                        <div class="col-md-4">
                            <label>Teams</label>
                            <div class="clearfix mt5">
                                <select class="chosen-select" data-placeholder="Teams" multiple name="teams" id="teams">
                                		<c:forEach var="team" items="${teamList}">
										<option value="${team.id}">${team.name}</option>
			  					</c:forEach>
                                </select>
                            </div>
                       	</div>
                </div>
          </div>
    </div>
<div class="clearfix"></div>
    <div class="clearfix advancedsearch-footer">
      <div class="pull-right mr">
        <input value="Apply" id="search_requiremet" onclick="closePop();getDynamciUserListByFilters();" class="btn btn-sm btn-success" type="button">
        <input value="Reset" id="clear_search" onclick="resetFilter();" class="btn btn-sm btn-primary" type="button">
      </div>
    </div>
  </div>
  
  <div class="sideMask"></div>

  <jsp:include page="../masterFooter.jsp" />

  <!-- jQuery (necessary for Bootstrap's JavaScript plugins) --> 
<script src="../js/jquery-1.11.0.min.js"></script> 
<script src="../js/bootstrap-datetimepicker.min.js"></script> 
<script src="../js/bootstrap.min.js"></script> 
<script src="../js/bootstrap-select.min.js"></script> 
<script src="../js/chosen.jquery.min.js"></script>
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
				})
				$(document).ready(function(){
					$("#cd").click(function(){
						$(".clientDetails").toggleClass('hidden', 500);
					});
	
	});
 		</script> 
<script type="text/javascript">
	$(document).ready(function(e) {
		$("#savevendor").click(function(){
			$('#myform').validate({
			rules:{
				things:{
					required:true
				}
			}
			});
		});
	});
	$(function () {

	});
</script> 
<script>	
function getActions(){
	$(".action-pop").slideToggle(200);
	$(".newActPop").slideUp(200);
}	
$("html").click(function(){
	$(".action-pop").slideUp(200);
});
$(".action, .action-pop").click(function(e){
 	e.stopPropagation();
});	
function newActions(){
	$(".newActPop").slideToggle(200);
	$(".action-pop").slideUp(200);
}
$("html").click(function(){
	$(".newActPop").slideUp(200);
});
$(".overIcon, .newActPop").click(function(e){
 	e.stopPropagation();
});
function runPayrollregister() {
	$("#payrollRegister").toggle();
}
	// Close Advanced Search
	function closePop(){
	$("div.cand-advsrch-popup").hide();
	$(".advancedsearch-mask").hide();
	}
	function openPop(){
	$(".advancedsearch-mask").show();
	$("div.cand-advsrch-popup").toggle();
	resetFilter();
	}
//Date Picker
$( function(){
	$("#frmDate").datepicker();
	$("#toDate").datepicker();
 });

</script>

<script>

function graphicalViewPage(){
	  
	  $("#listId").removeClass("act");
	  $("#graphicalId").addClass("act");
	  document.getElementById("graphId").style.display="block";
	  document.getElementById("associateClockInOutDiv").style.display="none";
}


function listViewPage(){
	  $("#graphicalId").removeClass("act");
	  $("#listId").addClass("act");
	  document.getElementById("associateClockInOutDiv").style.display="block";
	  document.getElementById("graphId").style.display="none";
}
</script>


<script>
function getAssociateClockInOutReportSummaryList(timeFrequency, json){
    var result = "";
    var headerLength = "";
    var currentDate = $("#frequencyID").html();
    result = result +'<table id="associateClockInOutTableID">';
    result = result +'<thead>';
    result = result +'  <tr>';
    for (var i=0; i<json.length; i++){
    	var rep = json[i];
    if(rep.headerDates != null && rep.headerDates != "" && rep.headerDates != "undefined"){
    	headerLength = rep.headerDates.length + 1;
    	for(var j = 0; j<rep.headerDates.length; j++){
    			if(i == 0){
    				result = result + '<th>'+rep.headerDates[j]+' <br><div class="fontSmall clearfix mt2"></div></th>';
    			}
    	}
    }
	}
   
    result = result +'    </tr>';
    result = result +'</thead>  ';
    result = result +'<tbody>';
    
    for (var i=0; i<json.length; i++){
    	var report = json[i];
    	result = result +'    <tr>';
    	if(report != null && report != "undefined" && report != "" && report.isDataAvailable != "NO"){
    		for(var j = 0; j<rep.headerDates.length; j++){
    			var key = rep.headerDates[j];
    			if(key === "Employee Name" || report.mapData[key] == "--"){
 	                result = result + '<td><div class="tbldata-dotdot">'+report.mapData[key]+'</div></td>';
    			}else{
 	               result = result + "<td class='dataLink'><div class='tbldata-dotdot'><a onclick='popHandler("+report.userId+");'>"+report.mapData[key]+"</a></div></td>";
    			}
 			} 
 			
    	}else{
			result = result +' <td colspan="'+headerLength+'"><div class="dataTables_empty"></div></td>';
    	}
    	result = result +'    </tr>';
	}
    
    result = result +'</tbody>';
    
    $("#associateClockInOutDiv").empty();  
    $("#associateClockInOutDiv").append(result);
    
    showClockSummaryGraph(json,timeFrequency);
    
}
</script>


 <script>
function empEntriesPagination(){  
    $('#associateClockInOutTableID').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
     		"sSearch": "",
     		"sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
}
</script>

<script>
function jobsFun(){
	$("#jobsDiv").show();
	
	$("#projectDiv").hide();
	$("#taskDiv").hide();
	$("#generalTaskDiv").hide();
}

function projectFun(){
	$("#projectDiv").show();
	$("#taskDiv").show();
	
	$("#jobsDiv").hide();
	$("#generalTaskDiv").hide();
}

function generaltaskFun(){
	$("#generalTaskDiv").show();
	$("#associateDiv").show();
	$("#teamsDiv").show();
	
	$("#jobsDiv").hide();
	$("#projectDiv").hide();
	$("#taskDiv").hide();
	getGeneralTasksList();
}
</script>

<script>
function resetFilter(){
	$("#userRole").val('').trigger("chosen:updated");
	$("#departmentName").val('').trigger("chosen:updated");
	$("#freqID").val('today');
	
	$("[name=applicableFor]").removeAttr("checked");
		$("#generalTaskDiv").show();
		$("#associateDiv").show();
		$("#teamsDiv").show();
		$("#jobsDiv").show();
		$("#projectDiv").show();
		$("#taskDiv").show();
	
	$("#jobsList").val('').trigger("chosen:updated");
	$("#projectList").val('').trigger("chosen:updated");
	$("#taskList").val('').trigger("chosen:updated");
	$("#generalTaskList").val('').trigger("chosen:updated");
	$("#associate").val('').trigger("chosen:updated");
	$("#teams").val('').trigger("chosen:updated");
	
}
</script>

<script>
function getProjectTasksByProject(obj){

	var projectId = $("#projectList").val();
	if(projectId != null && projectId != "" && projectId != "undefined"){
		$("#taskList").empty();
		for (i = 0; i < projectId.length; i++) {
			$.ajax({
				url: sourceleadUrl + "/getProjectsTasksByProjectId?&projectId="+projectId[i],
				type: 'GET',
				success: function (json) {
				var output = "";
				if(json != null){
					for (var i=0; i<json.length; i++){
						var opt = json[i];
						if(obj != null && obj != '' && obj != 'undefined'){
							if(obj.projectsTaskId == opt.id){
								output = output + "<option value='" +opt.id+ "' selected>"+opt.taskName+"</option>";
							}else{
								output = output + "<option value='" +opt.id+ "'>"+opt.taskName+"</option>";
							}
						}else{
							output = output + "<option value='" +opt.id+ "'>"+opt.taskName+"</option>";
						}
					}
				}
				$("#taskList").append(output);
				$('#taskList').trigger("chosen:updated"); 
				}
				});
		}
	}
	else{
		$("#taskList").empty();
		$('#taskList').trigger("chosen:updated");
	}
}
</script>

<script>
function getGeneralTasksList(){
	$.ajax({
	url: sourceleadUrl + "/getGeneralTasks",
	type: 'GET',
	success: function (json) {
		var output = "";
		if(json != null){
			for (var i=0; i<json.length; i++){
				var opt = json[i];
				output = output + "<option value='" +opt.id+ "'>"+opt.subject+"</option>";
			}
		}
	}
	});
	$("#generalTaskList").empty();
	$("#generalTaskList").append(output);
	$('#generalTaskList').trigger("chosen:updated");
}
</script>


<script>
function getCurrentDates(type){
	var currentDate = $("#frequencyID").html();
	var dayWisetype = $("#freqID option:selected").val();
	
	$.ajax({
		type : "GET",
		url : sourceleadUrl+ "/getCurrentDates?type="+type+"&currentDate="+currentDate+"&filterType="+dayWisetype,
		success : function(dates) {
			if(dates != null && dates != "" && dates != "undefined"){
				$("#frequencyID").html(dates);
				getDynamciUserList(type);
				$("#previousId").show();
				$("#nextId").show();
			}else{
				if(type == 'previous'){
					$("#previousId").hide();
					$("#nextId").show();
				}else if(type == 'next'){
					$("#nextId").hide();
					$("#previousId").show();
				}
			}
		}
});
}

</script>

<script>
 function getDynamciUserList(type){
	var currentDate = $("#frequencyID").html();
	var dayWisetype = $("#freqID option:selected").val();
	
	var loginUserRole = '${SourceLeadWeb_loginUserRole}';
	var orgDepartments = $("#departmentName").val();
	var userRole = $("#userRole").val();
	var jobsList = $("#jobsList").val();
	var projectList = $("#projectList").val();
	var tasksList = $("#taskList").val();
	var generalTasksList = $("#generalTaskList").val();
	var associateList = $("#associate").val();
	var teamsList = $("#teams").val();
	$("#clockLoading").attr('style','position:absolute; width:100%; height:250%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		$.ajax({
			type : "POST",
			url : sourceleadUrl+"/getAssociateClockInOutReportSummary?userRole="+userRole+"&loginUserRole="+loginUserRole+"&orgDepartments="+orgDepartments+"&jobsList="+jobsList+"&projectList="+projectList+"&tasksList="+tasksList+"&generalTasksList="+generalTasksList+"&associateList="+associateList+"&teamsList="+teamsList+"&dayWisetype="+dayWisetype+"&type="+type+"&currentDate="+currentDate,
			success : function(json) {
				getAssociateClockInOutReportSummaryList(dayWisetype, json);
			},
		});
 }
 </script>
 
 <script>
 	//getDynamciUserListByFilters
 	function getDynamciUserListByFilters(){
 		var currentType = $("#freqID option:selected").val();
 		getCurrentDates(currentType);
 	}
 </script>
 
 <script>
 
 function showClockSummaryGraph(json,type){
	 if(json.length >1){
			$("#graphIdDiv").removeClass('dataTables_empty'); 
			$("#jobsAdded").show();
			var format = "";
			 if("Current_Year" != type){
				 format =  "YYYY-MM-DD";
			 }else{
				 format =  "YYYY-MM";
			 }
			 var chartData = [];
			   for (var i = 0; i < json.length; i++) { 
			 		 var opt = json[i]; 
			 		 chartData.push({
			        		"category": opt.graphFormatDate,
					         "column-1": opt.graphTotalTime
			       });
			   }
			AmCharts.makeChart("jobsAdded",
				      {
				      "type": "serial",
				      "categoryField": "category",
				      "dataDateFormat": format,
				      "colors": [],
				      "startDuration": 1,
				      "categoryAxis": {
				        "equalSpacing": true,
				        "gridPosition": "start",
				        "parseDates": true,
				        "axisColor": "#ACACAC",
				        "gridThickness": 0,
				        "markPeriodChange": false,
				        "minHorizontalGap": 35,
				        "minVerticalGap": 25
				      },
				      "trendLines": [],
				      "graphs": [
				        {
				          "balloonText": "[[title]] : [[value]]",
				          "bulletField": "category",
				          "fillAlphas": 1,
				          "fillColors": "#6AB7F3",
				          "fixedColumnWidth": 30,
				          "id": "jobsAdded",
				          "lineColor": "#6AB7F3",
				          "title": "Clock In/Out Time",
				          "type": "column",
				          "valueField": "column-1"
				        }
				      ],
				      "guides": [],
				      "valueAxes": [
				        {
				          "id": "ValueAxis-1",
				          "axisColor": "#ACACAC",
				          "firstDayOfWeek": 0,
				          "gridThickness": 0,
				          "title": ""
				        }
				      ],
				      "allLabels": [],
				      "balloon": {
				        "fixedPosition": false,
				        "horizontalPadding": 10,
				        "textAlign": "left",
				        "verticalPadding": 10
				      },
				      "titles": [],
				      "dataProvider": chartData
				    });
		}else{
			$("#graphIdDiv").addClass('dataTables_empty');
			$("#jobsAdded").hide();
		}
	 
 }
 
 </script>
 
 <script>
 function popHandler(userId){
	 var selectedDate = "";
	 var selectedName = "";
      var tbl = document.getElementById("associateClockInOutTableID");
        if (tbl != null) {
        	
        	 $('table#associateClockInOutTableID tr').click(function(){
        		 selectedName = $(this).find('td:first').text();
             });
            for (var i = 0; i < tbl.rows.length; i++) {
                for (var j = 0; j < tbl.rows[i].cells.length; j++){
                	
                	tbl.rows[i].cells[j].onclick = function () { 
                    	var th = $('#associateClockInOutTableID th').eq($(this).index());
                    	selectedDate = th.text();
                         if(selectedDate != null && selectedDate != "" && selectedDate != "undefined"){
                             $.ajax({
                                 type : "GET",
                     			url: sourceleadUrl + "/getListOfClockDataBasedOnDate?&selectedDate="+selectedDate+"&userId="+userId,
                                 success : function(json) {
                                	 
                                	 
                                	 var result = "";
                                	 result = result +'  			 <div class="clearfix">';
                                	 result = result +'  	    <div class="dynamicData">                                                                                                ';
   	                          		 result = result +'  	    <div class="associateEmpData_pop_header clearfix">                                                                       ';
   	                         		 result = result +' 	      <h4 class="pull-left">'+selectedName+'</h4>                                                                     ';
   	                          		 result = result +'  	      <i class="fa fa-times pull-right text-muted" onClick="closeAssociateEmpData_pop();"></i>                               ';
   	                          		 result = result +'  	      <div class="clearfix"></div>                                                                                           ';
   	                          		 result = result +'	    </div>                                                                                                                   ';
   	                          		 result = result +'                                                                                                                               ';
   	                          		 result = result +' 	    <div class="clearfix col-md-12 mt">   ';
                        	        	result = result +'<table id="" class="table table-bordered">';
                        		    	result = result +'<thead>';
                        		    	result = result +'	<tr>';
                        		    	result = result +'	<th>Clock In</th>';
                        		    	result = result +'    <th>No. of hours</th>'; 
                        		    	result = result +'     <th>Task</th>';
                        		    	result = result +'     <th>Comment</th>';
                        		    	result = result +'     <th>Clock Out</th>';
                        		    	result = result +'    </tr>';
                        		    	result = result +'</thead>	';
                        		    	result = result +'<tbody>';
                        		    	for (var i=0; i<json.length; i++)
                        				{
                        		    		var report = json[i];
                        		    		result = result +'<tr>';
                        		    		if(report.clockInDate != null && report.clockInDate != "" && report.clockInDate != "undefined"){
                            		    		result = result +'                     <td><div class="tblminwidth-50">'+report.clockInDateStr+' '+report.clockInTime+'</div></td>                                                      ';
                        		    		}else{
                            		    		result = result +'                     <td><div class="tblminwidth-50">--</div></td>                                                      ';
                        		    		}
                        		    		
                        		    		if(report.totalTime != null && report.totalTime != "" && report.totalTime != "undefined"){
                            		    		result = result +'  	                      <td><div class="tblminwidth-50">'+report.totalTime+'</div></td>                                                      ';
                        		    		}else{
                            		    		result = result +'  	                      <td><div class="tblminwidth-50">--</div></td>                                                      ';
                        		    		}

                        		    		if(report.projectTaskName != null && report.projectTaskName != "" && report.projectTaskName != "undefined"){
                        		    			result = result +'                  <td><div class="tbldata-dotdot-big">'+report.projectTaskName+'</div></td>                                            ';
                        		    		}else{
                        		    			result = result +'                  <td><div class="tbldata-dotdot-big">--</div></td>                                            ';
                        		    		}

                        		    		if(report.comments != null && report.comments != "" && report.comments != "undefined"){
                            		    		result = result +'                     <td><div class="tbldata-dotdot-verybig">'+report.comments+'</div></td>               ';
                        		    		}else{
                            		    		result = result +'                     <td><div class="tbldata-dotdot-verybig">--</div></td>               ';

                        		    		}
                        		    		
                        		    		if(report.clockOutDate != null && report.clockOutDate != "" && report.clockOutDate != "undefined"){
                            		    		result = result +'	                      <td><div class="tblminwidth-75">'+report.clockOutDateStr+' '+report.clockOutTime+'</div></td>                                                      ';
                        		    		}else{
                            		    		result = result +'	                      <td><div class="tblminwidth-75">--</div></td>                                                      ';
                        		    		}

                        		    		result = result +'</tr>';
                        		    		
                        				}
                        		    	result = result +' 	              </tbody>                                                                                                ';
                        		    	result = result +'	        </table>                                                                                                      ';
      	                              result = result +'  	    </div>                                                                                                            ';
      	                              result = result +'  	</div>                                                                                                                ';
      	                              result = result +'                                                                                                                         ';
      	                              result = result +'  	</div>                                                                                                                    ';
                                      		 
                        		    	
                        		    	$("#uniqueDiv").empty();  
                        	    	    $("#uniqueDiv").append(result);
                                	 
                                	 }
                                 	
                             }); 
                         }
                    	 
					 };
                }
                    
            }
        }
        
        $(".associateEmpData_pop, .sideMask").fadeIn();
       
	}
 
 function closeAssociateEmpData_pop() {
	  $(".associateEmpData_pop, .sideMask").fadeOut();
	}
 </script>
 
			    