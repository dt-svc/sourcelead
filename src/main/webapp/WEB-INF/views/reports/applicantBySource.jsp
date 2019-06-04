<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="/resources/amcharts/amcharts.js"></script>
<script src="/resources/amcharts/serial.js"></script>
<script src="/resources/amcharts/plugins/export/export.min.js"></script>
<link rel="stylesheet" href="/resources/amcharts/plugins/export/export.css" type="text/css" media="all" />



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
       <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/reportsDashboard'/>">Reports Dashboard</a> &raquo; <a href="<c:url value='${appPath}/performanceReportList'/>">Performance Reports</a> &raquo; Applicant(s) by source </div>
    </section>
    
    
<div class="vendor_wrap clearfix">
<div id="reportLoading"></div>    
<div class="reportHead clearfix zIndex">
	<div class="listGridWrap reqListBtn">
          <a title="Graphical View"  id="graphicView" class="btn btn-default rAct"><i class="fa fa-pie-chart"></i></a>
          <a title="List View" id="listView" class="btn btn-default" ><i class="fa fa-list"></i></a>
	</div>
	
    	<ul class="clearfix pull-right overIndex">
    		<li>
				<div class="ReqRepHead-chosen repChosen choseMini">
		          <select class="chosen-select form-control" multiple data-placeholder="Select Job" name="jobIds" id="jobIds">
		            <c:forEach var="option" items="${requirementsList}">
						<option value="${option.requirementId}">${option.generatedId} - ${option.jobTitle}</option>
					</c:forEach>
		          </select>
		        </div>    		
    		</li>
    		<li>
        <div class="dropdown-select positionRelative repSelWrap clearfix">
          <select class="form-control repSelFiled" name="dateSelection" id="dateSelection" onchange="getCustomDates();">
            <option value='Last_7_days' selected>Last 7 days</option>
            <option value='Last_15_days'>Last 15 days</option>
            <option value='1_month'>1 month</option>
            <option value='3_months'>3 months</option>
            <option value='YTD'>YTD</option>
            <option value='Last_Year'>Last Year</option>
            <option value="custom">Custom</option>
          </select>          
          
		<div class="repSelBtn"><button type="button" class="btn btn-primary btn-sm" id="customize_btn" onclick="getApplicantBySourceChart();">Run</button></div> 
		<input type="hidden" id="reportViewType" value="graphView"/>
          
                    <!-- Custom Date -->
                    
                    
          <div class="clearfix customLeaves repCustomDate" id="customSearch">
              <div class="repCustomDateInn">
                <input type="text" class="form-control calendar" value="" id="fromDate" name="fromDate"  placeholder="From Date"  readonly="readonly" >
                <p id="fromDateDiv">From Date Required</p>
              </div>
              
              <div class="repCustomDateInn">
                <input type="text" class="form-control calendar" value="" id="toDate" name="toDate"  placeholder="To Date" field-name="From Date" readonly="readonly" >
                <p id="toDateDiv">To Date Required</p>
              </div>
              <div class="repCustomDateBtn"><button type="button" class="btn btn-primary btn-sm" onclick="getApplicantBySourceChart();"><i class="fa fa-search"></i></button></div>
          </div>   
                           
          <!-- Custom Date --> 
        </div>    		
    		</li> 
    	</ul>
    </div>     
      
      
   	 	<!-- Applicant source Graph 2017 -->
    	 	<div class="clearfix backWhite">
    	 		<div id="applicantSourceGrap" class="" style="height: 425px; width: 100%;" style="display:block"></div>
    	 	</div>
    	 	
	      <div class="project_tabs_inn_table" id="applicantSource" style="display:none"></div>    	 	
    	 	
    </div>
  </section>


<jsp:include page="../masterFooter.jsp" />

<script>
function reload(){
	location.reload();
}
</script>

<script>
function getCustomDates(){
	 var dateSelection = $("#dateSelection").val(); 
	 if(dateSelection == 'custom'){
		 removeErrMsg();
		 removeErrMsgTo();
		 document.getElementById("customSearch").style.display="block";
		 $("#customize_btn").attr("disabled", "disabled");
		 
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
					removeErrMsg();
					$("#toDate").datepicker("option", "minDate", dt);
				}
			});
		  
		  $('#toDate').datepicker({
				dateFormat : finalDateFormatProjectSave,
				changeMonth: true,
				changeYear: true, 
				onSelect : function(selected) {
					var dt = $(this).datepicker('getDate', '-1d');
					dt.setDate(dt.getDate());
					removeErrMsgTo();
					$("#fromDate").datepicker("option", "maxDate", dt);
				}
			});
		 
	 }else{
		 document.getElementById("customSearch").style.display="none";
		 $("#customize_btn").removeAttr("disabled");
		 $("#fromDate").datepicker("destroy");
		 $("#toDate").datepicker("destroy");
		 $("#fromDate").val('');
		 $("#toDate").val('');
		 document.getElementById("fromDateDiv").style.display="none";
		  document.getElementById("toDateDiv").style.display="none";
	 }
}

</script>



<script>
  function getApplicantBySourceChart(){
	 
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
			  $("#reportLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');

			$.ajax({ 
				   	type: "GET",
				 	url: sourceleadUrl+"/getApplicantBySourceDetails?${_csrf.parameterName}=${_csrf.token}&jobIds="+jobIds+"&dateSelection="+dateSelection+"&fromDate="+fromDate+"&toDate="+toDate,
				    success: function(response) {
				    	if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
			    		    showError(response.responseMessage);
			    	  	} else if (response.response === "AWKWARD") {
			    		    location.href = "/errorPage";
			    		} else if (response.response === "SUCCESS") {
			    			if(response.data.length > 0)	
				 	   		{
				        		    var chartData = [];
									for(var i=0; i < response.data.length ; i++){
				        			 var applicantSource = response.data[i];
				        			 
				        			    chartData.push( {
				        			    	category: applicantSource.category,
				        					column2: applicantSource.applicantsAvgCount,
				        					column3: applicantSource.shortlistedAvgCount,
				        					column4: applicantSource.submitToClientAvgCount,
				        					column5: applicantSource.interviewsAvgCount,
				        					column6: applicantSource.rejectedAvgCount,
				        					column7: applicantSource.offeredAvgCount,
				        					column8: applicantSource.offerAcceptedAvgCount,
				        					column9: applicantSource.offerRejectedAvgCount,
				        					column10: applicantSource.backoutAvgCount,
				        					
				        					applicantsCount: applicantSource.applicantsCount,
				        					shortlistedCount: applicantSource.shortlistedCount,
				        					submitToClientCount: applicantSource.submitToClientCount,
				        					interviewsCount: applicantSource.interviewsCount,
				        					rejectedCount: applicantSource.rejectedCount,
				        					offeredCount: applicantSource.offeredCount,
				        					offerAcceptedCount: applicantSource.offerAcceptedCount,
				        					offerRejectedCount: applicantSource.offerRejectedCount,
				        					backoutCount: applicantSource.backoutCount
				        			     
				        			    } );
				        			  }
				        	
				        		 loadApplicantSourceChart(chartData);
				        		 
				        		 var listView = document.getElementById("listView");
					        	 listView.addEventListener("click", function() {
					        		   getListView(response);
					        		   $("#reportViewType").val('');
					        		   $("#reportViewType").val('listView');
					        		    document.getElementById("applicantSourceGrap").style.display="none";
								    	document.getElementById("applicantSource").style.display="block";
								    	var element = document.getElementById("listView");
								    	var grid = document.getElementById("graphicView");
								    	element.classList.add("rAct");
								    	grid.classList.remove("rAct");
								    	$(".listSearch").show();
								  });
					        	 
					        	 var graphicView = document.getElementById("graphicView");
					        	 graphicView.addEventListener("click", function() {
					        		   loadApplicantSourceChart(chartData);
					        		   $("#reportViewType").val('');
					        		   $("#reportViewType").val('graphView');
					        		   document.getElementById("applicantSourceGrap").style.display="block";
								    	document.getElementById("applicantSource").style.display="none";
								    	
								    	var element = document.getElementById("listView");
								    	var grid = document.getElementById("graphicView");
								    	element.classList.remove("rAct");
								    	grid.classList.add("rAct");
								  });
					        	 
					        	 var reportViewType = document.getElementById("reportViewType").value;
					        	 if(reportViewType == 'listView'){
					        		 getListView(response);
					        	 }else{
					        		 loadApplicantSourceChart(chartData);
					        	 }
					        	 $("#reportLoading").removeAttr("style");
					        	 
				 	   		}else{
				 	   			var result = '';
				 	   			result = result+"<div class='dataTables_empty'>"; 
				 	   			result = result+"</div> ";
				 	   	        $("#applicantSource").empty();
				                $("#applicantSource").append(result);
				                $("#reportLoading").removeAttr("style");
				 	   		}
			    			
			    		}else if (JSON.stringify(response).indexOf('Sign In') != -1) { //  earlier is response.indexOf() which is not preferrable
			    			location.replace(sourceleadUrl);
			    		} else {
			    		    showError("Operation failed, please try again");
			    		}
			    		
			        },
			        error: function(e){
			        }
			 });
	   }
  }
  </script>

 <script>
 function loadApplicantSourceChart(result){
	 
	 var chart = AmCharts.makeChart("applicantSourceGrap", {
		 
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
			"export": {
				"enabled": true,
				"fileName":"Applicant(s) by Source"
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
					"balloonText": "[[title]] count of<br> [[category]]:[[applicantsCount]]<br> Percentage:[[value]] %",
					"columnWidth": 0.3,
					"fillAlphas": 1,
					"fillColors": "#90CAF9",
					"id": "applicants",
					"labelText": "[[value]] %",
					"legendValueText": "",
					"lineColor": "#90CAF9",
					"title": "Applicants",
					"countValue": "applicantsCount",
					"type": "column",
					"valueField": "column2"
				},
				{
					"balloonText": "[[title]] count of<br>[[category]]:[[shortlistedCount]]<br> Percentage:[[value]] %",
					"columnWidth": 0.3,
					"fillAlphas": 1,
					"fillColors": "#F48FB1",
					"id": "shortlisted",
					"labelText": "[[value]] %",
					"lineColor": "#F48FB1",
					"title": "Shortlisted",
					"countValue": "shortlistedCount",
					"type": "column",
					"valueField": "column3"
				},
				{
					"balloonText": "[[title]] count of<br>[[category]]:[[submitToClientCount]]<br> Percentage:[[value]] %",
					"columnWidth": 0.3,
					"fillAlphas": 1,
					"fillColors": "#FF8A54",
					"id": "submittoclient",
					"labelText": "[[value]] %",
					"lineColor": "#FF8A54",
					"title": "Submit to client",
					"countValue": "submitToClientCount",
					"type": "column",
					"valueField": "column4"
				},
				{
					"balloonText": "[[title]] count of<br>[[category]]:[[interviewsCount]]<br> Percentage:[[value]] %",
					"columnWidth": 0.3,
					"fillAlphas": 1,
					"fillColors": "#FFCC80",
					"id": "interviews",
					"labelText": "[[value]] %",
					"lineColor": "#FFCC80",
					"title": "Interviews",
					"countValue": "interviewsCount",
					"type": "column",
					"valueField": "column5"
				},
				{
					"balloonText": "[[title]] count of<br>[[category]]:[[rejectedCount]]<br> Percentage:[[value]] %",
					"columnWidth": 0.3,
					"fillAlphas": 1,
					"fillColors": "#B39DDB",
					"id": "rejected",
					"labelText": "[[value]] %",
					"lineColor": "#B39DDB",
					"title": "Rejected",
					"countValue": "rejectedCount",
					"type": "column",
					"valueField": "column6"
				},
				{
					"balloonText": "[[title]] count of<br>[[category]]:[[offeredCount]]<br> Percentage:[[value]] %",
					"columnWidth": 0.3,
					"fillAlphas": 1,
					"fillColors": "#81C784",
					"id": "offered",
					"labelText": "[[value]] %",
					"lineColor": "#81C784",
					"title": "Offered",
					"countValue": "offeredCount",
					"type": "column",
					"valueField": "column7"
				},
				{
					"balloonText": "[[title]] count of<br>[[category]]:[[offerAcceptedCount]]<br> Percentage:[[value]] %",
					"columnWidth": 0.3,
					"fillAlphas": 1,
					"fillColors": "#E57373",
					"id": "offerAccepted",
					"labelText": "[[value]] %",
					"lineColor": "#E57373",
					"title": "Offer Accepted",
					"countValue": "offerAcceptedCount",
					"type": "column",
					"valueField": "column8"
				},
				{
					"balloonText": "[[title]] count of<br>[[category]]:[[offerRejectedCount]]<br> Percentage:[[value]] %",
					"columnWidth": 0.3,
					"fillAlphas": 1,
					"fillColors": "#D84315",
					"id": "offerrejected",
					"labelText": "[[value]] %",
					"lineColor": "#D84315",
					"title": "Offer Rejected",
					"countValue": "offerRejectedCount",
					"type": "column",
					"valueField": "column9"
				},
				{
					"balloonText": "[[title]] count of<br>[[category]]:[[backoutCount]]<br> Percentage:[[value]] %",
					"columnWidth": 0.3,
					"fillAlphas": 1,
					"fillColors": "#BCAAA4",
					"id": "backout",
					"labelText": "[[value]] %",
					"lineColor": "#BCAAA4",
					"title": "Backout",
					"countValue": "backoutCount",
					"type": "column",
					"valueField": "column10"
				}
			],
	 });
 }
 </script> 

 <script>
 function getListView(response){
	
	 if(response.data.length > 0)	
 		{
		// alert("SS"+response.data.length);
		 var result = "";
		 result = result +"<div class='clearfix project_tabs_inn_table'>"; 
		 result = result +"<table class='table'>"; 
         result = result +"<thead>"; 
         result = result +"  <tr>"; 
         result = result +"    <th>Source</th>";
         result = result +"    <th>Applicants</th>"; 
         result = result +"    <th>Shortlisted</th>"; 
         result = result +"    <th>Submit To Client</th>"; 
         result = result +"    <th>Rejected</th>"; 
         result = result +"    <th>Interviews</th>"; 
         result = result +"    <th>Offered</th>"; 
         result = result +"    <th>Offer Accepted</th>"; 
         result = result +"    <th>Offer Rejected</th>"; 
         result = result +"    <th>Back Out</th>"; 
         result = result +"  </tr>"; 
         result = result +"</thead>";
         result = result +"<tbody>"; 
			 for(var i=0; i < response.data.length ; i++){
				 var applicantSource = response.data[i];
				 
	   					console.log(applicantSource);
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
				 result = result +"<div class='tblminwidth-50'>"+applicantSource.submitToClientCount+"</div>"; 
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
 <script>
 function removeErrMsg(){
	  document.getElementById("fromDateDiv").style.display="none";
 }
 function removeErrMsgTo(){
	  document.getElementById("toDateDiv").style.display="none";
	  
 }
  </script>
 