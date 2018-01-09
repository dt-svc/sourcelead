<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

 
<!--  <script src="/resources/js/amcharts3.js"></script> 
 <script src="/resources/js/serial3.js"></script> 
 <script src="/resources/js/export3.min.js"></script>
 <link href="/resources/css/export3.css" rel="stylesheet" type="text/css"> 
  -->
 <script type="text/javascript" src="https://www.amcharts.com/lib/3/amcharts.js"></script>
<script type="text/javascript" src="https://www.amcharts.com/lib/3/serial.js"></script>

<script type="text/javascript" src="https://www.amcharts.com/lib/3/plugins/export/export.min.js"></script>
<link rel="stylesheet" href="https://www.amcharts.com/lib/3/plugins/export/export.css">

<!--  <script src="/resources/js/amcharts.js"></script> 
 <script src="/resources/js/serial.js"></script> 
  -->
<script>
$(document).ready(function() {
	getJobPipelineReports();
});	
</script>

<script>
function getJobsReportCustomDates(){
	 var dateSelection = $("#dateSelection").val(); 
	 if(dateSelection == 'custom'){
		 document.getElementById("customSearch").style.display="block";
		 $("#jobReportList_custome").attr("disabled", "disabled");
		 
	 }else{
		 document.getElementById("customSearch").style.display="none";
		 $("#jobReportList_custome").removeAttr("disabled");
		 $("#fromDate").val('');
		 $("#toDate").val('');
		 document.getElementById("fromDateDiv").style.display="none";
		 document.getElementById("toDateDiv").style.display="none";
	 }
}

</script>
 <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Jobs Pipeline</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo;<a href="<c:url value='${appPath}/reportsDashboard'/>"> Report(s) Dashboard</a>&raquo;Jobs Pipeline Report
       </div>
    </section>
    <!-- hiring Process  -->
    <div class="clearfix">
      <div class="ReqRepHead clearfix">
    		      <div class="col-md-2 pull-left reqListBtn">
          <a href="${appPath}/jobPipelineReports" title="Reports View" id="GView" class="btn btn-default"><i class="fa fa-pie-chart"></i></a>
          <a href="${appPath}/jobPipelineReportsList" title="List View" id="LView" class="btn btn-default"><i class="fa fa-list"></i></a>
    		      </div>
     		<!--<a href="jobsPipline-list.html" class="btn btn-primary">List</a>-->
       
        <div class="col-md-4">
    <div class="dropdown-scroll">
		<select class="chosen-select form-control" multiple data-placeholder="Select Job" name="jobIds" id="jobIds">
            <c:forEach var="option" items="${requirementsList}">
				<option value="${option.requirementId}">${option.jobTitle}</option>
			</c:forEach>
          </select>
	</div>
        </div>
        <div class="col-md-3 dropdown-select">
        <div class="dropdown-select positionRelative">
        <select class="form-control" name="dateSelection" id="dateSelection" onchange="getJobsReportCustomDates();">
            <option value=''>Select Date</option>
            <option value='Last_7_days' selected >Last 7 days</option>
            <option value='Last_15_days'>Last 15 days</option>
            <option value='1_month'>1 month</option>
            <option value='3_months'>3 months</option>
            <option value='YTD' >YTD</option>
            <option value='Last_Year'>Last Year</option>
            <option value="custom">Custom</option>
          </select>
    <!-- Top Leave Takers Custom Date -->
          <div class="clearfix pull-lefet col-md-12 paddingRight customLeaves" id="customSearch" style="display: none; width: 346px;">
            <div class="pull-left">
              <div class="pull-left mr5" style="width: 140px" id="errMsgFrm">
                <input type="text" class="form-control calendar" value="" id="fromDate" name="fromDate"  placeholder="From Date"  readonly="readonly" >
                <p id="fromDateDiv" style="display: none; font-size: 12px;"><font color="red">From Date Required</font></p>
              </div>
              
              <div class="pull-left mr5" style="width: 140px" id="errMsgTo">
                <input type="text" class="form-control calendar" value="" id="toDate" name="toDate"  placeholder="To Date" field-name="From Date" readonly="readonly" >
                <p id="toDateDiv" style="display: none; font-size: 12px;"><font color="red">To Date Required</font></p>
              </div>
              <button type="button" class="btn btn-primary btn-sm" onclick="getJobPipelineReports();"><i class="fa fa-search"></i></button>
            </div>
          </div>
      </div>

        </div>
		        <div class="col-md-2">
        <button type="button" class="btn btn-primary btn-sm	mt2" onclick="getJobPipelineReports();">Run</button>
		</div>
         
         <div class="col-md-1">
          <div class="btn-group bootstrap_dropdownMenu dropdownPadding">
            <button type="button" class="btn btn-primary btn-sm" title="Reload" onclick="reload();"><i class="fa fa-retweet"></i></button>
          </div>
        </div>
      </div>
    </div>
    
    <div class="dashboard-widget clearfix mt amchartTitile-hide"> 
       <div id="jobspipline" style="width: 100%; height: 350px;"></div>
    </div>
    
  </section>
<script>
function reload(){
	location.reload();
}
</script>
<script>
function getJobPipelineReports(){
	
	var flag = true;
	  var jobIds = $("#jobIds").val();
	  if(jobIds == null){
		  jobIds = '';
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
		   	url: sourceleadUrl+"/getAllJobsPipelineReoportsList?${_csrf.parameterName}=${_csrf.token}&jobIds="+jobIds+"&dateSelection="+dateSelection+"&fromDate="+fromDate+"&toDate="+toDate,
		    success: function(data) {
		    	if(data.applicantsCount>0){
		    		getJobsPipelineAmcahrt(data);
		    	}else{
		    		var result = '';
	 	   			result = result+"<div class='dataTables_empty'>"; 
	 	   			result = result+"</div> ";
	 	   	        $("#jobspipline").empty();
	                $("#jobspipline").append(result);
		    	}
		    }
		 });
	  }	  
}

</script>
<script>
  $( function() {
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
			}
		});
  } );
  
  function removeErrsgFrom (){
	  document.getElementById("fromDateDiv").style.display="none";
	  
  }
  function removeErrTo(){
	  document.getElementById("toDateDiv").style.display="none";
  }
  </script>
<script>
function getJobsPipelineAmcahrt(data){
	var mainChar = [];
	if(data.applicantsCount>0){
		mainChar.push( {
			"category": "Applicant(s)",
			"column-1": data.applicantsCount,
			"Color": "#90CAF9",
			"Days": data.applicantsDays,
			"moveforward": "100"
		    } );
	}
	
	if(data.shortlistedCount>0){
		mainChar.push( {
			"category": "Shortlisted",
			"column-1": data.shortlistedCount,
			"Color": "#F48FB1",
			"Days": data.shortlistedDays,
			"moveforward": data.shortlistedMove
		    } );
	}
	
	if(data.submitToClientCount>0){
		mainChar.push( {
			"category": "Submit To Client",
			"column-1": data.submitToClientCount,
			"Color": "#EAEA23",
			"Days": data.submitToClientDays,
			"moveforward": data.submitToClientMove
		    } );
	}
	if(data.interviewsCount>0){
		mainChar.push( {
			"category": "Interviewed",
		    "column-1": data.interviewsCount,
		    "Color": "#FFCC80",
		    "Days": data.interviewsDays,
		    "moveforward": data.interviewsMove
		} );
	}
	
	if(data.offeredCount>0){
		mainChar.push({
			"category": "Offered",
			"column-1": data.offeredCount,
			"Color": "#B39DDB",
			"Days": data.offeredDays,
			"moveforward": data.offeredMove
		});
	}
	if(data.offeredAcceptedCount>0){
		mainChar.push({
			"category": "Offers Accepted",
			"column-1": data.offeredAcceptedCount,
			"Color": "#81C784",
			"Days": data.offeredAcceptedDays,
			"moveforward": data.offeredAcceptedMove
		});
	}
	
	if(data.backoutCount>0){
		mainChar.push({
			"category": "Backout(s)",
			"column-1": data.backoutCount,
			"Color": "#E57373",
			"Days": data.backoutDays,
			"moveforward": 84
		});
	}
	
	
	AmCharts.makeChart("jobspipline",
			{
				"type": "serial",
				"categoryField": "category",
				"rotate": true,
				"marginBottom": 0,
				"marginLeft": 0,
				"marginRight": 30,
				"marginTop": 0,
				"colors": [],
				"startDuration": 1,
				"fontSize": 10,
				"categoryAxis": {
					"gridPosition": "start",
					"axisThickness": 0,
					"gridThickness": 0,
					"tickLength": 10,
					"titleRotation": -1
				},
				"export": {
				    "enabled": true,
				    "pageOrigin": false
				},
				"trendLines": [],
				"graphs": [
					{
						"descriptionField": "Days",
						"fillAlphas": 1,
						"fillColorsField": "Color",
						"id": "AmGraph-1",
						"labelPosition": "right",
						"labelText": "[[value]] <br> [[Days]] Day(s) <br>[[moveforward]]% Moveforward",
						"lineColorField": "Color",
						"lineThickness": 0,
						"maxBulletSize": 0,
						"title": "graph 1",
						"type": "column",
						"valueField": "column-1"
					},
					{
						"id": "AmGraph-2",
						"title": "graph 2",
						"valueField": "Color"
					}
				],
				"guides": [],
				"valueAxes": [
					{
						"id": "ValueAxis-1",
						"axisThickness": 0,
						"gridThickness": 0,
						"labelsEnabled": false,
						"title": ""
					}
				],
				"allLabels": [],
				"balloon": {},
				"titles": [
					{
						"id": "Title-1",
						"size": 15,
						"text": "Jobs Pipline"
					}
				],
				"dataProvider": mainChar
            }
		); 
    
        jQuery("a[title='JavaScript charts']").remove();
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