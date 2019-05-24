<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="/resources/amcharts/amcharts.js"></script>
<script src="/resources/amcharts/serial.js"></script>
<script src="/resources/amcharts/plugins/export/export.min.js"></script>
<link rel="stylesheet" href="/resources/amcharts/plugins/export/export.css" type="text/css" media="all" />


<script>
$(window).load(function() {
	getDeclineApplicants();
});
</script>

<section class="content-wraper">

    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Applicant(s) Decline</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/reportsDashboard'/>">Reports Dashboard</a> &raquo; <a href="<c:url value='${appPath}/performanceReportList'/>">Performance Reports</a> &raquo; Applicant(s) Decline </div>
    </section>
    
    <!-- hiring Process  -->
    <div class="clearfix vendor_wrap"> 
     
     
     
<div class="reportHead clearfix">
	<div class="listGridWrap reqListBtn">
        	<a title="Graphical View"  id="graphView" class="btn btn-default rAct"><i class="fa fa-pie-chart"></i></a>
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
	            <option value='YTD' >YTD</option>
	            <option value='Last_Year'>Last Year</option>
	            <option value="custom">Custom</option>
	          </select>
		<div class="repSelBtn"><button type="button" class="btn btn-primary btn-sm" id="customize_btn" onclick="getDeclineApplicants();getDeclineApplicantList();">Run</button></div> 
          
          <div class="clearfix customLeaves repCustomDate" id="customSearch">
               <div class="repCustomDateInn">
                <input type="text" class="form-control calendar" value="" id="fromDate" name="fromDate"  placeholder="From Date"  readonly="readonly" >
                <p id="fromDateDiv">From Date Required</p>
              </div>
              
               <div class="repCustomDateInn">
                <input type="text" class="form-control calendar" value="" id="toDate" name="toDate"  placeholder="To Date" field-name="From Date" readonly="readonly" >
                <p id="toDateDiv">To Date Required</p>
              </div>
              <div class="repCustomDateBtn"><button type="button" class="btn btn-primary btn-sm" onclick="getDeclineApplicants();getDeclineApplicantList();"><i class="fa fa-search"></i></button></div>
          </div>          
        </div>    		
    		</li> 
    		<li class="listSearch"><input type="text" class="repSearchBar" placeholder='Search' id='applicantDeclineReportsTableSearch' /><span class="repSearchIcon" onclick="getDeclineApplicants();getDeclineApplicantList();"></span></li>
    	</ul>
    </div>     
     
        
        
     <!-- Decline Reasons -->
     <div class="row clearfix" id="applicantDeclineGraph" style="display:block">
        <div class="col-md-12">
        <div class="clients_wrap_table dashboard-widget" id="applicantDeclineReportDivId" style="display:block">
              <div id="applicantDecline" style="width: 100%; height: 250px; overflow: hidden; text-align: left;"></div>
        </div>
        </div>
	</div>
      <!-- Decline Reasons -->
     
        <div class="project_tabs_inn_table" id="applicantDeclineListDivId" style="display:none">
          <div id="applicantDeclineListDiv" class="datatable_filter repDataSearch"></div>
          </div>
          
          
<!-- <section class="candidate_wrap_table recruiterReports_table minHeight">
<div id ="vendorLoderDiv"></div>
<div id="vendorReportsDiv" class="datatable_filter repDataSearch"></div>
<div id="vendorSpocReportsDiv" class="repoPositiNoCount"></div>
</section>     -->         
          
    <!-- Best recruiters Ends  --> 
    </div>
      
  </section>
  
<script type="text/javascript">

$("#applicantDeclineReportsTableSearch").keydown(function(e) {
	   if(e.which === 13) {
		   getDeclineApplicants();
		   getDeclineApplicantList();
	   }
	});

function getDeclineApplicants(){
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
			   	type: "GET",
			   	url: sourceleadUrl+"/getDeclinedApplicantsCount?${_csrf.parameterName}=${_csrf.token}&jobIds="+jobIds+"&dateSelection="+dateSelection+"&fromDate="+fromDate+"&toDate="+toDate,
			    success: function(response) {
			    	
			    	if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
		    		    showError(response.responseMessage);
		    	  	} else if (response.response === "AWKWARD") {
		    		    location.href = "/errorPage";
		    		} else if (response.response === "SUCCESS") {
		    			
		    			var total=response.object.skillsCount + response.object.unsuitableCount + response.object.badfitCount + response.object.interviewCount + response.object.salaryCount + response.object.otherCount;
				    	
				    	var listView = document.getElementById("listView");
			        	 listView.addEventListener("click", function() {
			        		 getDeclineApplicantList();
			        		 document.getElementById("applicantDeclineGraph").style.display="none";
						    	document.getElementById("applicantDeclineListDivId").style.display="block";
						    	var element = document.getElementById("listView");
						    	var grid = document.getElementById("graphView");
						    	element.classList.add("rAct");
						    	grid.classList.remove("rAct");
						    	$(".listSearch").show();
						    	
						  });
			        	 
			        	 var graphicView = document.getElementById("graphView");
			        	 graphicView.addEventListener("click", function() {
			        		 if(total>0){
			        			 getAplicantDeclineAmChart(response);
			        		 }else{
			        			 var result = '';
			 	 	   			result = result+"<div class='dataTables_empty'>"; 
			 	 	   			result = result+"</div> ";
			 	 	   	        $("#applicantDecline").empty();
			 	                $("#applicantDecline").append(result);
			        		 }
			        		 document.getElementById("applicantDeclineGraph").style.display="block";
						    	document.getElementById("applicantDeclineListDivId").style.display="none";
						    	
						    	var element = document.getElementById("listView");
						    	var grid = document.getElementById("graphView");
						    	element.classList.remove("rAct");
						    	grid.classList.add("rAct");
						    	$(".listSearch").hide();
						  });
				    if(total>0){
				    	getAplicantDeclineAmChart(response);
				    	
				    }
				   	else{
				   		var result = '';
		 	   			result = result+"<div class='dataTables_empty'>"; 
		 	   			result = result+"</div> ";
		 	   	        $("#applicantDecline").empty();
		                $("#applicantDecline").append(result);
				   	}
		    		}else if (response.indexOf('Sign In') !== -1) {
		    			location.replace(sourceleadUrl);
		    		} else {
		    		    showError("Operation failed, please try again");
		    		}
			    }
		        	 
			 });
	  }
	  
}
	  
</script>


<script type="text/javascript">

function getDeclineApplicantList(){
	var searchValue = $("#applicantDeclineReportsTableSearch").val();
	searchValue = searchValue ? searchValue : "";
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
			   	type: "GET",
			   	url: sourceleadUrl+"/getDeclinedApplicantsList?${_csrf.parameterName}=${_csrf.token}&jobIds="+jobIds+"&dateSelection="+dateSelection+"&fromDate="+fromDate+"&toDate="+toDate+"&searchValue="+searchValue,
			    success: function(response) {
			    	
			    	if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
		    		    showError(response.responseMessage);
		    	  	} else if (response.response === "AWKWARD") {
		    		    location.href = "/errorPage";
		    		} else if (response.response === "SUCCESS") {
		    		 	
			           	 var result = "";
			           	 
			             result  = result + '    <table class="table table-bordered" id="applicantDeclinePagenationId">     ';
						 result  = result + '     <thead>                                 ';
						 result  = result + '       <tr>                                  ';
						  result  = result + '         <th>Id</th>                       ';
						 result  = result + '         <th>Jobs</th>                       ';
						 result  = result + '         <th>Skills</th>                     ';
						 result  = result + '         <th>Unsuitable Personality</th>     ';
						 result  = result + '         <th>Bad Fit</th>                    ';
						 result  = result + '         <th>Interview</th>                  ';
						 result  = result + '         <th>Salary</th>                     ';
						 result  = result + '         <th>Other</th>                      ';
						 result  = result + '         <th>Total</th>                      ';
						 result  = result + '       </tr>                                 ';
						 result  = result + '       </thead>                              ';
						 result  = result + '       <tbody>                               ';
						 for (var i = 0; i < response.data.length; i++) { 
							 var report = response.data[i];
							 
							 if(report.totalCount>0){
								 result  = result + '       <tr>                                  ';	
								 result  = result + '		  <td><div>'+report.generatedId+'</div></td>  ';
							 result  = result + '		  <td><div>'+report.jobName+'</div></td>  ';
							 result  = result + '         <td><div>'+report.skillsCount+'</div></td>               ';
							 result  = result + '         <td><div>'+report.unsuitableCount+'</div></td>               ';
							 result  = result + '         <td><div>'+report.badfitCount+'</div></td>               ';
							 result  = result + '         <td><div>'+report.interviewCount+'</div></td>               ';
							 result  = result + '         <td><div>'+report.salaryCount+'</div></td>               ';
							 result  = result + '         <td><div>'+report.otherCount+'</div></td>               ';
							 result  = result + '         <td><div>'+report.totalCount+'</div></td>               ';
							 result  = result + '         </tr>                               ';
							 
						 }
							
						 }
						
						 
						 result  = result + '     </tbody>                                ';
						 result  = result + '   </table>                                  ';
						 $("#applicantDeclineListDiv").empty();
						 $("#applicantDeclineListDiv").append(result);
						 applicantDeclineListPagination();
					    
		    		}else if (response.indexOf('Sign In') !== -1) {
		    			location.replace(sourceleadUrl);
		    		} else {
		    		    showError("Operation failed, please try again");
		    		}
			   	
			    }
			 });
		  
	  }
	  
}
function applicantDeclineListPagination(){
	$('#applicantDeclinePagenationId').DataTable( { 
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
				$("#toDate").datepicker("option", "minDate", dt);
				removeErrMsg();
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
				removeErrMsgTo();
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

<script type="text/javascript">
  function getAplicantDeclineAmChart(response){
	  
	  var total=response.object.skillsCount + response.object.unsuitableCount + response.object.badfitCount + response.object.interviewCount + response.object.salaryCount + response.object.otherCount;
	  var mainChar = [];
		 if(response.object.skillsCount>0){
			 mainChar.push( {
				    "category": "Skills",
					"column-1": response.object.skillsCount,
					"color": "#9FA8DA",
					"count": response.object.skillsCount,
		            "percentage":(((response.object.skillsCount)/total)*100).toFixed(2)
			    } );
			 }
		 if(response.object.unsuitableCount>0){
			 mainChar.push( {
				    "category": "Unsuitable Personality",
					"color": "#90CAF9",
					"column-1": response.object.unsuitableCount,
					"count": response.object.unsuitableCount,
		            "percentage": (((response.object.unsuitableCount)/total)*100).toFixed(2)
			    } );
			 }
		 if(response.object.badfitCount>0){
			 mainChar.push( {
				 "category": "Bad Fit",
					"column-1": response.object.badfitCount,
					"color": "#FFAB91",
					"count": response.object.badfitCount,
		            "percentage": (((response.object.badfitCount)/total)*100).toFixed(2)
			    } );
			 }
		 if(response.object.interviewCount>0){
			 mainChar.push( {
				    "category": "Interview",
					"column-1": response.object.interviewCount,
					"color": "#A5D6A7",
					"count": response.object.interviewCount,
		            "percentage": (((response.object.interviewCount)/total)*100).toFixed(2)
			    } );
			 }
		 if(response.object.salaryCount>0){
			 mainChar.push( {
				    "category": "Salary",
					"column-1": response.object.salaryCount,
					"color": "#FFE0B2",
					"count": response.object.salaryCount,
		            "percentage": (((response.object.salaryCount)/total)*100).toFixed(2)
			    } );
			 }
		 if(response.object.otherCount>0){
			 mainChar.push( {
					"category": "Other",
					"column-1": response.object.otherCount,
					"color": "#BCAAA4",
					"count": response.object.otherCount,
		            "percentage": (((response.object.otherCount)/total)*100).toFixed(2)
			    } );
			 }
      AmCharts.makeChart("applicantDecline",
                {
          "type": "serial",
            "categoryField": "category",
            "rotate": true,
            "marginBottom": 0,
            "marginLeft": 0,
            "marginTop": 10,
            "startDuration": 1,
            "categoryAxis": {
                "gridPosition": "start",
                "position": "left",
                "axisThickness": 0,
                "gridThickness": 0,
                "title": ""
            },
            "export": {
            	"enabled": true,
            	"fileName":"Applicant(S) Decline"
            	},
            "trendLines": [],
            "graphs": [
                {
                    "balloonText": "",
                    "colorField": "color",
                    "fillAlphas": 1,
                    "id": "AmGraph-1",
                    "labelText": "[[count]] <br>[[percentage]]%",
                    "legendValueText": "",
                    "lineColorField": "color",
                    "title": "graph 1",
                    "type": "column",
                    "valueField": "column-1"
                }
            ],
            "guides": [],
            "valueAxes": [
                {
                    "axisFrequency": 0,
                    "id": "ValueAxis-1",
                    "totalText": "",
                    "unit": "",
                    "axisThickness": 0,
                    "gridThickness": 0,
                    "labelsEnabled": false,
                    "title": ""
                }
            ],
            "allLabels": [],
            "balloon": {},
            "titles": [],
            "dataProvider":  mainChar
                }
            );
      $("#paging").empty();
  }
        </script> 
  
  <script>
 $(document).ready(function(){
$(function() {
        $('#dateSelection').change(function(){
			var optval = $(this).val();
			if(optval === "custom") {
				$(".customPop").show();
			}else{
				$(".customPop").hide();
			}
        });
    });
  }); 
    
</script>

<script>
  function removeErrMsg(){
	  document.getElementById("fromDateDiv").style.display="none";
  }
  function removeErrMsgTo(){
	  document.getElementById("toDateDiv").style.display="none";
	  
  }
  </script>

  

<jsp:include page="../masterFooter.jsp" />
