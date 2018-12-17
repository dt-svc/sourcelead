<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="/resources/amcharts/amcharts.js"></script>
<script src="/resources/amcharts/serial.js"></script>
<script src="/resources/amcharts/plugins/export/export.min.js"></script>
<link rel="stylesheet" href="/resources/amcharts/plugins/export/export.css" type="text/css" media="all" />
  
<script>
$(document).ready(function() {
	getAssetGrowthReports();
});	
</script>

<script>
function getAssetReportCustomDates(){
	 var dateSelection = $("#dateSelection").val(); 
	 if(dateSelection == 'custom'){
		 document.getElementById("customSearch").style.display="block";
		 document.getElementById("runPipelineDiv").style.display="none";
			 var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
			  var finalDateFormatProjectSave = '';
			  if(locationDate.match("yyyy-MM-dd")){
				  finalDateFormatProjectSave = 'yy-mm-dd';
			  }if(locationDate.match("dd-MM-yyyy")){
				  finalDateFormatProjectSave = 'dd-mm-yy'; 
			  }if(locationDate.match("MM-dd-yyyy")){
				  finalDateFormatProjectSave = 'mm-dd-yy'; 
			  }if(locationDate.match("yyyy-dd-MM")){
				  finalDateFormatProjectSave = 'yy-dd-mm'; 
			  }
	    
		  $('#fromDate').datepicker({
				dateFormat : finalDateFormatProjectSave,
				changeMonth: true,
				changeYear: true, 
				onSelect : function(selected) {
					var dt = $(this).datepicker('getDate', '+1d');
					dt.setDate(dt.getDate());
					$("#toDate").datepicker("option", "minDate", dt);
					removeErrsgFrom();
					daysValidation(selected,"");
				}
			});
		  
		  $('#toDate').datepicker({
				dateFormat : finalDateFormatProjectSave,
				changeMonth: true,
				changeYear: true, 
				onSelect : function(selected) {
					var dt = $(this).datepicker('getDate', '-1d');
					dt.setDate(dt.getDate());
					$("#fromDate").datepicker("option", "maxDate", dt);
					removeErrTo();
					daysValidation("",selected);
				}
			});
	  
		 
	 }else{
		 document.getElementById("runPipelineDiv").style.display="block";
		 document.getElementById("customSearch").style.display="none";
		 $("#fromDate").datepicker("destroy");
		 $("#toDate").datepicker("destroy");
		 $("#fromDate").val('');
		 $("#toDate").val('');
		 document.getElementById("fromDateDiv").style.display="none";
		 document.getElementById("toDateDiv").style.display="none";
		 
		 document.getElementById("noOfDaysDiv").style.display="none";
	 	 document.getElementById("dateperiodDiv").style.display="none";
	 	 $("#customeSearchButton").prop("disabled",false);
	 	 $("#customeSearcListhButton").prop("disabled",false);
	 	
	 }
}
 
 function daysValidation(fromDate,toDate){
	 var fromDate = $("#fromDate").val();
	 var toDate = $("#toDate").val();
	  if(fromDate != null && fromDate != "" && fromDate != "undefined" && toDate != null && toDate != "" && toDate != "undefined"){
		  $.ajax({ 
			   	type: "GET",
			   	url: sourceleadUrl+"/noOfDaysAssetGrowth?fromDate="+fromDate+"&toDate="+toDate,
			    success: function(report) {
			    	document.getElementById("noOfDaysDiv").style.display="inline-block";
			    	$("#durationCount").text(report.noOfDays);
			    	if(report.noOfDays <= 365){
			    		$("#customeSearchButton").prop("disabled",false);
			   	 	    $("#customeSearcListhButton").prop("disabled",false);
			    		document.getElementById("dateperiodDiv").style.display="none";
			    	}else{
			    		$("#customeSearchButton").prop("disabled",true);
			   	 	    $("#customeSearcListhButton").prop("disabled",true);
			    		document.getElementById("noOfDaysDiv").style.display="none";
			    		document.getElementById("dateperiodDiv").style.display="inline-block";
			    	}
			    }
			 });
	  }
 }
</script>
 <section class="content-wraper">
 
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Asset Growth</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo;<a href="<c:url value='${appPath}/reportsDashboard'/>"> Reports Dashboard</a> &raquo; <a href="<c:url value='${appPath}/financialReportsList'/>">Financial Reports</a> &raquo; Asset Growth
       </div>
    </section>
    
    <!-- hiring Process  -->
    
    <div class="vendor_wrap clearfix">
    
	<div class="reportHead clearfix">
		<div class="listGridWrap reqListBtn">
			<button type="button" title="Reports View" id="GraphView"  value="grid" class="btn btn-primary rAct" onclick="getAssetGrowthReports('grid')"><i class="fa fa-pie-chart"></i></button>
        	<button type="button" title="List View" id="ListView" value="list" class="btn btn-default" ><i class="fa fa-list"></i></button>
		</div>
	
    	<ul class="clearfix pull-right">
    		<li>
				<div class="ReqRepHead-chosen repChosen">
					<select class="chosen-select form-control" multiple data-placeholder="Select Category" name="jobIds" id="catIds">
			            <c:forEach var="option" items="${categoryList}">
							<option value="${option.id}">${option.categoryName}</option>
						</c:forEach>
			          </select>
		        </div>    		
    		</li>
    		<li>
		        <div class="dropdown-select positionRelative repSelWrap clearfix">
			        <select class="form-control repSelFiled" name="dateSelection" id="dateSelection" onchange="getAssetReportCustomDates();">
			            <option value=''>Select Date</option>
			            <option value='Last_7_days' selected >Last 7 days</option>
			            <option value='Last_15_days'>Last 15 days</option>
			            <option value='1_month'>1 month</option>
			            <option value='3_months'>3 months</option>
			            <option value='YTD' >YTD</option>
			            <option value='Last_Year'>Last Year</option>
			            <option value="custom">Custom</option>
			        </select>   
			        
				<div class="repSelBtn" id="runPipelineDiv"><button type="button" class="btn btn-primary btn-sm" id="assetGridRunBtn" onclick="getAssetGrowthReports('grid');" style="display: block">Run</button>
				<button type="button" class="btn btn-primary btn-sm" id="assetListRunBtn" onclick="getAssetGrowthReports('ListView');" style="display: none">Run</button></div> 
		          
		                    <!-- Custom Date -->
		          <div class="clearfix customLeaves repCustomDate" id="customSearch">
		              <div class="repCustomDateInn" id="errMsgFrm">
		                <input type="text" class="form-control calendar" value="" id="fromDate" name="fromDate"  placeholder="From Date"  readonly="readonly" >
		                <p id="fromDateDiv" style="display: none">From Date Required</p>
		                
		              </div>
		              
		              <div class="repCustomDateInn" id="errMsgTo">
		                <input type="text" class="form-control calendar" value="" id="toDate" name="toDate"  placeholder="To Date" field-name="From Date" readonly="readonly" >
		                <p id="toDateDiv" style="display: none">To Date Required</p>
		              </div>
		              
		              <div class="repCustomDateBtn"><button type="button" class="btn btn-primary btn-sm" id="customeSearchButton" onclick="getAssetGrowthReports('grid');" style="display: block"><i class="fa fa-search"></i></button>
		              <button type="button" class="btn btn-primary btn-sm" id="customeSearcListhButton" onclick="getAssetGrowthReports('ListView');" style="display: none"><i class="fa fa-search"></i></button>
		              </div>
		           <div class="clearfix"></div>
		          <div class="clearfix mt5">
		          <span><i class="fa fa-info-circle small text-primary" title="Days period not more than 365 days"></i></span>
		          <span id="noOfDaysDiv" style="display: none;" class="paddingLeft text-muted small mb0 mt5">Duration: <span id="durationCount"></span> day(s)</span>
		          <span id="dateperiodDiv" style="display: none;" class="paddingLeft colorRed small mb0 mt5">Days period shouldn't exceed 365 days</span>
		          </div>
		          </div>                    
		          <!-- Custom Date --> 
		        </div>    		
		    </li> 
    	</ul>
    </div>     
    
    <div id="assetGrowthCahrt" style="width: 100%; height: 375px; display: block;" ></div>
		<div class="clearfix timesheet_head timeHd_minHeight"></div>
		<div id="assetGrowthReportList"  class="datatable_filter" style="width: 100%; min-height: 75px; display: none;" ></div>
    </div>
  </section>
 
<script>

document.getElementById("GraphView").addEventListener("click", function(){
    $("#assetListRunBtn").hide();
    $("#assetGridRunBtn").show();
    $("#customeSearcListhButton").hide();
    $("#customeSearchButton").show();
    
    
});
document.getElementById("ListView").addEventListener("click", function(){
	$("#assetGridRunBtn").hide();
	$("#assetListRunBtn").show();
	$("#customeSearcListhButton").show();
    $("#customeSearchButton").hide();
});
function getAssetGrowthReports(type){
	var flag = true;
	  var catIds = $("#catIds").val();
	  if(catIds == null){
		  catIds = '';
	  }
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
		   	type: "POST",
		   	url: sourceleadUrl+"/getAssetGrowthReportList?${_csrf.parameterName}=${_csrf.token}&categoryIds="+catIds+"&dateSelection="+dateSelection+"&fromDate="+fromDate+"&toDate="+toDate+"&gridType="+type,
		    success: function(response) {
		    	if(response.response === "SUCCESS") {
		    		if(response.data != ""){
		    			if(response.responseMessage === "months") {
		    				monthsReport(response.data);
		    			}else if(response.responseMessage === "days"){
		    				daywiseReport(response.data);
		    			}else if(response.responseMessage === "ListView"){
		    				assetGrowthReportList(response.data);
		    			}
		    		}else{
		    			var result = '';
		 	   			result = result+"<div class='dataTables_empty'>"; 
		 	   			result = result+"</div> ";
		 	   	        $("#assetGrowthCahrt").empty();
		                $("#assetGrowthCahrt").append(result);
		                assetGrowthReportList(response.data);
		    		}
		    		
		    		var listView = document.getElementById("GraphView");
		    		listView.addEventListener("click", function() {
		    			$("#assetGrowthReportList").hide();
		    			$("#assetGrowthCahrt").show();
		    			if(response.data != ""){
		    				if(response.responseMessage === "months") {
		    					monthsReport(response.data);
		    					var element = document.getElementById("ListView");
						    	var grid = document.getElementById("GraphView");
						    	element.classList.remove("btn-primary");
						    	grid.classList.add("btn-primary");
						    	element.classList.add("btn-default");
						    	
		    				}else{
		    					daywiseReport(response.data);
		    					var element = document.getElementById("ListView");
						    	var grid = document.getElementById("GraphView");
						    	grid.classList.add("btn-primary");
						    	element.classList.remove("btn-primary");
						    	element.classList.add("btn-default");
						    	
		    				}
		    			}else{
		    				var result = '';
			 	   			result = result+"<div class='dataTables_empty'>"; 
			 	   			result = result+"</div> ";
			 	   	        $("#assetGrowthCahrt").empty();
			                $("#assetGrowthCahrt").append(result);
		    			}
		    		});
		    		
		    		var listView = document.getElementById("ListView");
		        	 listView.addEventListener("click", function() {
		        		  $("#assetGrowthCahrt").hide();
		        		  $("#assetGrowthReportList").show();  
		        		  $("#assetGrowthReportList").empty();
		        		  $.ajax({ 
		        			   	type: "POST",
		        			   	url: sourceleadUrl+"/getAssetGrowthReportList?${_csrf.parameterName}=${_csrf.token}&categoryIds="+catIds+"&dateSelection="+dateSelection+"&fromDate="+fromDate+"&toDate="+toDate+"&gridType=ListView",
		        			    success: function(response) {
		        			      assetGrowthReportList(response.data);
		   	      			      var element = document.getElementById("ListView");
		   				    	  var grid = document.getElementById("GraphView");
		   				    	  element.classList.add("btn-primary");
		   				    	  grid.classList.remove("btn-primary");
		   				    	  grid.classList.remove("btn-default");
		        			    }
		        		  });
	      			     
		        	 });
					
		       }
		    }
		 });
	  }	  
}
</script>
<script>
  function removeErrsgFrom (){
	  document.getElementById("fromDateDiv").style.display="none";
	  
  }
  function removeErrTo(){
	  document.getElementById("toDateDiv").style.display="none";
  }
  </script>
<script>

function daywiseReport(response){
	/*  // total offsets reserved for margins
	 var offsets = 100;
    
    // pixel value reserved for each category
    var categoryWidth = 80;
    
    // calculate width
    var width = offsets + response.length * categoryWidth;
    
    // set container width
    document.getElementById("agWeekdays").style.width = "" + width + "px";   */
    
	var mainChar = [];
	for (var i=0; i<response.length; i++){
		var report = response[i];
		var catName = "";
		for(var e in report.categoryNameMap){
			catName = catName.concat(e +"  "+report.categoryNameMap[e]+"\n");
		}
		mainChar.push({
				"category": report.fromDate,
				"column-1": report.assetCount,
				"categoryName" :catName,
				"categoryCount" :report.categoryCount
				});
    }
	  
	//WeekDays
	AmCharts.makeChart("assetGrowthCahrt",
	{
	"type": "serial",
	"categoryField": "category",
	"dataDateFormat": "YYYY-MM-DD",
	"colors": [],
	"startDuration": 1,
	"categoryAxis": {
		"equalSpacing": true,
		"gridPosition": "start",
		"parseDates": true,
		"axisColor": "#636E72",
		"firstDayOfWeek": 0,
		"gridCount": 0,
		"gridThickness": 0,
		"markPeriodChange": false,
		"minHorizontalGap": 10,
		"minVerticalGap": 5
	},
	"export": {
	    "enabled": true,
	    "fileName":"Asset Growth"
	},
	"trendLines": [],
	"graphs": [
		{
			"balloonText": "No. of Assets [[value]]  <br/> Category [[categoryCount]] <br> [[categoryName]] ",
			"fillAlphas": 1,
			"fillColors": "#74B9FF",
			"id": "assetGrowth",
			"lineColor": "#74B9FF",
			"title": "graph 1",
			"type": "column",
			"valueField": "column-1",
			"fixedColumnWidth": 50
		}
	],
	"guides": [],
	"valueAxes": [
		{
			"id": "Quanity",
			"axisColor": "#636E72",
			"firstDayOfWeek": 0,
			"gridThickness": 0
		}
	],
	"allLabels": [],
	"balloon": {},
	"dataProvider": mainChar
   });
	jQuery("a[title='JavaScript charts']").remove();
}



//Months Reports
function monthsReport(response){
	/*  // total offsets reserved for margins
	 var offsets = 100;
   
   // pixel value reserved for each category
   var categoryWidth = 80;
   
   // calculate width
   var width = offsets + response.length * categoryWidth;
    */
	var monthChar = [];
	for (var i=0; i<response.length; i++){
		var report = response[i];
		var catName = "";
		for(var e in report.categoryNameMap){
			catName = catName.concat(e +"  "+report.categoryNameMap[e]+"\n");
		}
		monthChar.push(
				{
					"category": report.monthNames,
					"column-1": report.assetCount,
					"categoryName" :catName,
					"categoryCount" :report.categoryCount
				});
    }
	
	AmCharts.makeChart("assetGrowthCahrt",
			{
			"type": "serial",
			"categoryField": "category",
			"dataDateFormat": "YYYY-MM-DD",
			"colors": [],
			"startDuration": 1,
			"categoryAxis": {
				"autoRotateAngle": 60,
				"autoRotateCount": 0,
				"gridPosition": "start",
				"axisColor": "#636E72",
				"centerRotatedLabels": true,
				"gridCount": 0,
				"gridThickness": 0,
				"markPeriodChange": false,
				"minHorizontalGap": 35,
				"minVerticalGap": 10
			},
			"export": {
			    "enabled": true,
			    "pageOrigin": false,
			    "fileName":"Asset Growth"
			},
			"trendLines": [],
			"graphs": [
				{
					"balloonText": "No. of Assets:  [[value]] <br/> Category:  [[categoryCount]] <br> [[categoryName]] ",
					"fillAlphas": 1,
					"fillColors": "#74B9FF",
					"fixedColumnWidth": 50,
					"id": "assetGrowth",
					"lineColor": "#74B9FF",
					"title": "graph 1",
					"type": "column",
					"valueField": "column-1",
					"fixedColumnWidth": 50
				}
			],
			"guides": [],
			"valueAxes": [
				{
					"id": "Quanity",
					"axisColor": "#636E72",
					"gridThickness": 0
				}
			],
			"allLabels": [],
			"balloon": {},
			"dataProvider":  monthChar
		}
		);
}

</script>
	
	
<script>
function assetGrowthReportList(response){
  var result = "";
  var country = '${SourceLeadWeb_orgLocations.country}';
  result = result+'<div class="candidate_wrap_table recruiterReports_table">';
  result = result+'   <table id="assetGrowthPagination">';
  result = result+'     <thead>';
  result = result+'       <tr>';
  result = result+'         <th> <i class="fa fa-calendar-check-o fa-2x"></i>';
  result = result+'           <div class="small">Purchase Date</div>';
  result = result+'         </th>';
  result = result+'         <th> <i class="fa fa fa-cubes fa-2x"></i>';
  result = result+'           <div class="small">No. of Assets</div>';
  result = result+'         </th>';
  if(country == "India"){
	  result = result+'         <th> <i class="fa fa-inr fa-2x"></i>';
  }else{
	  result = result+'         <th> <i class="fa fa-dollar fa-2x"></i>';
  }
  result = result+'           <div class="small">Cost of Assets</div></th>';
  result = result+'       </tr>';
  result = result+'     </thead>';
  result = result+'     <tbody>';
	for (var i=0; i<response.length; i++){
		var report = response[i];
		  result = result+'       <tr>';
		  result = result+'         <td><div class="width215px"><div class="">'+report.listFromDate+'</div></div></td>';
		  result = result+'         <td><div class="">'+report.assetCount+'</div></td>';
		  result = result+'         <td><div class="width215px"><div class="">'+report.costDoble+'</div></div></td>';
		  result = result+'       </tr>';
    }

  result = result+'     </tbody>';
  result = result+'   </table>';
  result = result+' </div>';
  
  $("#assetGrowthReportList").empty();
  $("#assetGrowthReportList").append(result);
  assetGrowthReportListPagination();
}
function assetGrowthReportListPagination(){
	   $('#assetGrowthPagination').DataTable( { 
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
	    	"bLengthChange": true,
	    	"order": [[ 0, "desc" ]],
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		"sZeroRecords": "", "sEmptyTable": ""
	     	}
	    } );
}
</script>	
	
	
		
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
    });
    $('#priority').trigger('chosen:updated');
    $(function() {
    });
</script> 
<jsp:include page="../masterFooter.jsp" />