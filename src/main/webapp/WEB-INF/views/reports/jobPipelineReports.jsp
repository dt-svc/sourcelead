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
	getJobPipelineReports();
});	
</script>

<script>
function getJobsReportCustomDates(){
	 var dateSelection = $("#dateSelection").val(); 
	 if(dateSelection == 'custom'){
		 document.getElementById("customSearch").style.display="block";
		 $("#jobReportList_custome").attr("disabled", "disabled");
		 
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
	  
		 
	 }else{
		 document.getElementById("customSearch").style.display="none";
		 $("#jobReportList_custome").removeAttr("disabled");
		 $("#fromDate").datepicker("destroy");
		 $("#toDate").datepicker("destroy");
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
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo;<a href="<c:url value='${appPath}/reportsDashboard'/>"> Report(s) Dashboard</a>&raquo;Jobs Pipeline Reports
       </div>
    </section>
    <!-- hiring Process  -->
    <div class="clearfix">
      <div class="ReqRepHead clearfix">
    		      <div class="col-md-2 pull-left reqListBtn">
                    <a title="Reports View" id="GraphView" class="btn btn-default"><i class="fa fa-pie-chart"></i></a>
                    <a  title="List View" id="ListView" class="btn btn-default"><i class="fa fa-list"></i></a>
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
    
    <div class="dashboard-widget clearfix mt amchartTitile-hide" id="jobPipelineReportDivId" style="display:block"> 
       <div id="jobspipline" style="width: 100%; height: 350px;"></div>
    </div>
    
    
    
    
    <div class="vendor_wrap clearfix mt" id="jobPipelineListDivId" style="display:none">
        <div class="clearfix"></div>
        <div class="clerfix jobpipline-count col-md-8 col-md-offset-2">
          <ul class="list-unstyled list-inline">
            <li> <a href="javascript:void(0)"><span id="allJobsCount"></span></a> <span>All</span> </li>
            <li> <a href="javascript:void(0)"><span id="openJobsCount"></span></a> <span>Open</span> </li>
            <li> <a href="javascript:void(0)"><span id="closedJobsCount"></span></a> <span>Closed</span> </li>
            <li> <a href="javascript:void(0)"><span id="onHoldJobsCount"></span></a> <span>Onhold</span> </li>
            <li> <a href="javascript:void(0)"><span id="cancelledJobsCount"></span></a> <span>Cancelled</span> </li>
          </ul>
        </div>
        <div class="clearfix"></div>
        
        <div class="clearfix mt5">
        	
        	<div class="col-md-4 mb">
        	<div class="cardModel-widget">
        	 <div class="clearfix row cardModelwidget-header">
        		<h4 class="col-md-12 text-center">Applicants:
        	 	<span class="text-right" id="applicantCount"></span>
        	 	</h4>
        	 </div>
        	    <div class="clearfix row">
        	    <div class="col-md-6 paddingRight">
        	    	<label class="text-muted">Moved forward :</label>
        	    	<span>100 %</span>
        	    </div>
        	     <div class="col-md-6 paddingLeft">
        	    	<label class="text-muted pull-left">Avg TTF :</label>
        	    	<div class="pull-left paddingLeft"><span id="applicantCountDays"></span><span class="paddingLeft">days</span></div>
        	    </div>
				</div>
        	</div>
        	</div>
        	
        	<div class="col-md-4 mb">
        	<div class="cardModel-widget">
        	<div class="clearfix row cardModelwidget-header">
        		<h4 class="col-md-12 text-center">Request Sent:
        	 	<span class="text-right" id="requestSentCount"></span>
        	 	</h4>
        	 </div>
        	    <div class="clearfix row">
        	    <div class="col-md-6 paddingRight">
        	    	<label class="text-muted">Moved forward :</label>
        	    	<span>100 %</span>
        	    </div>
        	     <div class="col-md-6 paddingLeft">
        	    	<label class="text-muted pull-left">Avg TTF :</label>
        	    	<div class="pull-left paddingLeft"><span id="requestSentDays"></span><span class="paddingLeft"> days</span></div>
        	    </div>
				</div>
        	</div>
        	</div>
        	<div class="col-md-4 mb">
        	<div class="cardModel-widget">
        	<div class="clearfix row cardModelwidget-header">
        		<h4 class="col-md-12 text-center">Shortlisted:
        	 	<span class="text-right" id="shortListedCount"></span>
        	 	</h4>
        	 </div>
        	    <div class="clearfix row">
        	    <div class="col-md-6 paddingRight">
        	    	<label class="text-muted pull-left">Moved forward :</label>
        	    	<div class="pull-left paddingLeft"><span id="shortListedMove"></span><span class="paddingLeft">%</span></div>
        	    </div>
        	     <div class="col-md-6 paddingLeft">
        	    	<label class="text-muted pull-left">Avg TTF :</label>
        	    	<div class="pull-left paddingLeft"><span id="shortListedDays"></span><span class="paddingLeft">days</span></div>
        	    </div>
				</div>
        	</div>
        	</div>
        	<div class="col-md-4 mb">
        	<div class="cardModel-widget">
        	<div class="clearfix row cardModelwidget-header">
        		<h4 class="col-md-12 text-center">Submit to Client:
        	 	<span class="text-right" id="submitToClientCount">60</span>
        	 	</h4>
        	 </div>
        	    <div class="clearfix row">
        	    <div class="col-md-6 paddingRight">
        	    	<label class="text-muted pull-left">Moved forward :</label>
        	    	<div class="pull-left paddingLeft"><span id="submitToClientMove"></span><span class="paddingLeft">%</span></div>
        	    </div>
        	     <div class="col-md-6 paddingLeft">
        	    	<label class="text-muted pull-left">Avg TTF :</label>
        	    	<div class="pull-left paddingLeft"><span id="submitToClientDays"></span><span class="paddingLeft">days</span></div>
        	    </div>
				</div>
        	</div>
        	</div>
        	<div class="col-md-4 mb">
        	<div class="cardModel-widget">
        	<div class="clearfix row cardModelwidget-header">
        		<h4 class="col-md-12 text-center">Interview:
        	 	<span class="text-right" id="interviewCount"></span>
        	 	</h4>
        	 </div>
        	    <div class="clearfix row">
        	    <div class="col-md-6 paddingRight">
        	    	<label class="text-muted pull-left">Moved forward :</label>
        	    	<div class="pull-left paddingLeft"><span id="interviewMove"></span><span class="paddingLeft">%</span></div>
        	    </div>
        	     <div class="col-md-6 paddingLeft">
        	    	<label class="text-muted pull-left">Avg TTF :</label>
        	    	<div class="pull-left paddingLeft"><span id="interviewDays"></span><span class="paddingLeft">days</span></div>
        	    </div>
				</div>
        	</div>
        	</div>
        	<div class="col-md-4 mb">
        	<div class="cardModel-widget">
        	<div class="clearfix row cardModelwidget-header">
        		<h4 class="col-md-12 text-center">Offer:
        	 	<span class="text-right" id="offerCount">20</span>
        	 	</h4>
        	 </div>
        	    <div class="clearfix row">
        	    <div class="col-md-6 paddingRight">
        	    	<label class="text-muted pull-left">Moved forward :</label>
        	    	<div class="pull-left paddingLeft"><span id="offerMove"></span><span class="paddingLeft">%</span></div>
        	    </div>
        	     <div class="col-md-6 paddingLeft">
        	    	<label class="text-muted pull-left">Avg TTF :</label>
        	    	<div class="pull-left paddingLeft"><span id="offerDays"></span><span class="paddingLeft">days</span></div>
        	    </div>
				</div>
        	</div>
        	</div>
        	<div class="col-md-4 mb">
        	<div class="cardModel-widget">
        	<div class="clearfix row cardModelwidget-header">
        		<h4 class="col-md-12 text-center">Offer Accepted:
        	 	<span class="text-right" id="offerAcceptedCount"></span>
        	 	</h4>
        	 </div>
        	    <div class="clearfix row">
        	    <div class="col-md-6 paddingRight">
        	    	<label class="text-muted pull-left">Moved forward :</label>
        	    	<div class="pull-left paddingLeft"><span id="offerAcceptedMove"></span><span class="paddingLeft">%</span></div>
        	    </div>
        	     <div class="col-md-6 paddingLeft">
        	    	<label class="text-muted pull-left">Avg TTF :</label>
        	    	<div class="pull-left paddingLeft"><span id="offerAcceptedDays"></span><span class="paddingLeft">days</span></div>
        	    </div>
				</div>
        	</div>
        	</div>
        	<div class="col-md-4 mb">
        	<div class="cardModel-widget">
        	 <div class="clearfix row cardModelwidget-header">
        		<h4 class="col-md-12 text-center">Backout:
        	 	<span class="text-right" id="backoutCount"></span>
        	 	</h4>
        	 </div>
        	    <div class="clearfix row">
        	    <div class="col-md-6 paddingRight">
        	    	<label class="text-muted pull-left">Moved forward :</label>
        	    	<div class="pull-left paddingLeft"><span id="backoutMove"></span><span class="paddingLeft">%</span></div>
        	    </div>
        	     <div class="col-md-6 paddingLeft">
        	    	<label class="text-muted pull-left">Avg TTF :</label>
        	    	<div class="pull-left paddingLeft"><span id="backoutDays"></span><span class="paddingLeft">days</span></div>
        	    </div>
				</div>
        	</div>
        	</div>
        	
        	<div class="col-md-4 mb">
        	<div class="cardModel-widget">
        	<div class="clearfix row cardModelwidget-header">
        		<h4 class="col-md-12 text-center">Rejected:
        	 	<span class="text-right" id="rejectedCount"></span>
        	 	</h4>
        	 </div>
        	    <div class="clearfix row">
        	    <div class="col-md-6 paddingRight">
        	    	<label class="text-muted pull-left">Moved forward :</label>
        	    	<div class="pull-left paddingLeft"><span id="rejectedMove"></span><span class="paddingLeft">%</span></div>
        	    </div>
        	     <div class="col-md-6 paddingLeft">
        	    	<label class="text-muted pull-left">Avg TTF :</label>
        	    	<div class="pull-left paddingLeft"><span id="rejectedDays"></span><span class="paddingLeft">days</span></div>
        	    </div>
				</div>
        	</div>
        	</div>
        	
        </div>
        <div class="clearfix mt mb"></div>
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
		    success: function(response) {
		    	if(response.object.applicantsCount>0){
		    		getJobsPipelineAmcahrt(response);
		    		getJobPipelineReportsList(response);
		    		var listView = document.getElementById("ListView");
		        	 listView.addEventListener("click", function() {
		        		 getJobPipelineReportsList(response);
		        		 document.getElementById("jobPipelineListDivId").style.display="block";
		        		 document.getElementById("jobPipelineReportDivId").style.display="none";
					  });
		        	 
		        	 var graphicView = document.getElementById("GraphView");
		        	 graphicView.addEventListener("click", function() {
		        		 getJobsPipelineAmcahrt(response);
		        		 document.getElementById("jobPipelineListDivId").style.display="none";
		        		 document.getElementById("jobPipelineReportDivId").style.display="block";
		        	 });
		        	 
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
function getJobPipelineReportsList(response){
	//Aplicant Details
	$("#applicantCount").text(response.object.applicantsCount);
	$("#applicantCountDays").text(response.object.applicantsDays);
	
	//request sent details
	$("#requestSentCount").text(response.object.requestSentCount);
	$("#requestSentDays").text(0);
	 
	//shortlisted details
	$("#shortListedCount").text(response.object.shortlistedCount);
	$("#shortListedDays").text(response.object.shortlistedDays);
	$("#shortListedMove").text(response.object.shortlistedMove);
	
	//interview details
	$("#interviewCount").text(response.object.interviewsCount);
	$("#interviewMove").text(response.object.interviewsMove);
	$("#interviewDays").text(response.object.interviewsDays);
	
	
	//submit to client
	$("#submitToClientCount").text(response.object.submitToClientCount);
	$("#submitToClientMove").text(response.object.submitToClientMove);
	$("#submitToClientDays").text(response.object.submitToClientDays)
	
	//offer details
	$("#offerCount").text(response.object.offeredCount)
	$("#offerDays").text(response.object.offeredDays)
	$("#offerMove").text(response.object.offeredMove)
	
	//offer Accepted details
	$("#offerAcceptedCount").text(response.object.offeredAcceptedCount)
	$("#offerAcceptedDays").text(response.object.offeredAcceptedDays)
	$("#offerAcceptedMove").text(response.object.offeredAcceptedMove)
	
	//backout details
	$("#backoutCount").text(response.object.backoutCount);
	$("#backoutDays").text(response.object.backoutDays);
	$("#backoutMove").text(response.object.backoutMove);
	
	//rejected details
	$("#rejectedCount").text(response.object.rejectedCount);
	$("#rejectedMove").text(response.object.rejectedMove);
	$("#rejectedDays").text(response.object.rejectedDays);
	
	//jobs count
	$("#allJobsCount").text(response.object.allJobsCount);
	$("#openJobsCount").text(response.object.openJobsCount);
	$("#closedJobsCount").text(response.object.closedJonbsCount);
	$("#onHoldJobsCount").text(response.object.onHoldJobsCount);
	$("#cancelledJobsCount").text(response.object.cancelledjobsCount);
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
function getJobsPipelineAmcahrt(response){
	var mainChar = [];
	if(response.object.applicantsCount>0){
		mainChar.push( {
			"category": "Applicant(s)",
			"column-1": response.object.applicantsCount,
			"Color": "#90CAF9",
			"Days": response.object.applicantsDays,
			"moveforward": "100"
		    } );
	}
	
	if(response.object.shortlistedCount>0){
		mainChar.push( {
			"category": "Shortlisted",
			"column-1": response.object.shortlistedCount,
			"Color": "#F48FB1",
			"Days": response.object.shortlistedDays,
			"moveforward": response.object.shortlistedMove
		    } );
	}
	
	if(response.object.submitToClientCount>0){
		mainChar.push( {
			"category": "Submit To Client",
			"column-1": response.object.submitToClientCount,
			"Color": "#EAEA23",
			"Days": response.object.submitToClientDays,
			"moveforward": response.object.submitToClientMove
		    } );
	}
	if(response.object.interviewsCount>0){
		mainChar.push( {
			"category": "Interviewed",
		    "column-1": response.object.interviewsCount,
		    "Color": "#FFCC80",
		    "Days": response.object.interviewsDays,
		    "moveforward": response.object.interviewsMove
		} );
	}
	
	if(response.object.offeredCount>0){
		mainChar.push({
			"category": "Offered",
			"column-1": response.object.offeredCount,
			"Color": "#B39DDB",
			"Days": response.object.offeredDays,
			"moveforward": response.object.offeredMove
		});
	}
	if(response.object.offeredAcceptedCount>0){
		mainChar.push({
			"category": "Offers Accepted",
			"column-1": response.object.offeredAcceptedCount,
			"Color": "#81C784",
			"Days": response.object.offeredAcceptedDays,
			"moveforward": response.object.offeredAcceptedMove
		});
	}
	
	if(response.object.backoutCount>0){
		mainChar.push({
			"category": "Backout(s)",
			"column-1": response.object.backoutCount,
			"Color": "#E57373",
			"Days": response.object.backoutDays,
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
				    "pageOrigin": false,
				    "fileName":"Jobs Pipeline"
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
						"text": "Jobs Pipeline"
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