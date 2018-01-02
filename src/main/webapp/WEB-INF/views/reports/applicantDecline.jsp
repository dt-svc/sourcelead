<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript" src="https://www.amcharts.com/lib/3/amcharts.js"></script>
<script type="text/javascript" src="https://www.amcharts.com/lib/3/serial.js"></script>

<script type="text/javascript" src="https://www.amcharts.com/lib/3/plugins/export/export.min.js"></script>
<link rel="stylesheet" href="https://www.amcharts.com/lib/3/plugins/export/export.css">

<script>
$(window).load(function() {
	getDeclineApplicants();
});
</script>

<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Applicant Decline</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/reportsDashboard'/>">Reports Dashboard</a> &raquo; Applicants Decline </div>
   
    </section>
    <!-- hiring Process  -->
    <div class="content-dashboard clearfix"> 
     
    <div class="ReqRepHead clearfix">
    	
    	<div class="col-md-2 pull-left reqListBtn">
        <a href="${appPath}/applicantDeclineReports" title="Grid View" id="GView" class="btn btn-default rAct"><i class="fa fa-pie-chart"></i></a>
          <a href="${appPath}/applicantDeclineList" title="List View" id="LView" class="btn btn-default" ><i class="fa fa-list"></i></a>
        </div>
    
		<div class="col-md-6 ReqRepHead-chosen">
          <select class="chosen-select form-control" multiple data-placeholder="Select Job" name="jobIds" id="jobIds">
            <c:forEach var="option" items="${requirementsList}">
				<option value="${option.requirementId}">${option.jobTitle}</option>
			</c:forEach>
          </select>
        </div>
	<div class="col-md-2 dropdown-select positionRelative">
          <select class="form-control" name="dateSelection" id="dateSelection" onchange="getCustomDates();">
            <option value=''>Select Date</option>
            <option value='Last_7_days'>Last 7 days</option>
            <option value='Last_15_days'>Last 15 days</option>
            <option value='1_month'>1 month</option>
            <option value='3_months'>3 months</option>
            <option value='YTD' selected>YTD</option>
            <option value='Last_Year'>Last Year</option>
            <option value="custom">Custom</option>
          </select>
          
         <!-- Top Leave Takers Custom Date -->
          <div class="clearfix pull-lefet col-md-12 paddingRight customLeaves" id="customSearch" style="display: none; width: 346px;">
            <div class="pull-left">
              <div class="pull-left mr5" style="width: 140px">
                <input type="text" class="form-control calendar" value="" id="fromDate" name="fromDate"  placeholder="From Date"  readonly="readonly" >
                <p id="fromDateDiv" style="display: none; font-size: 12px;"><font color="red">From Date Required</font></p>
              </div>
              
              <div class="pull-left mr5" style="width: 140px">
                <input type="text" class="form-control calendar" value="" id="toDate" name="toDate"  placeholder="To Date" field-name="From Date" readonly="readonly" >
                <p id="toDateDiv" style="display: none; font-size: 12px;"><font color="red">To Date Required</font></p>
              </div>
              <button type="button" class="btn btn-primary btn-sm" onclick="getDeclineApplicants();"><i class="fa fa-search"></i></button>
            </div>
          </div>
          </div>
                  
          <div class="col-md-2">
        <button type="button" class="btn btn-primary btn-sm	mt2" id="customize_btn" onclick="getDeclineApplicants();">Run</button>
		</div>
        </div>
        
        
     <!-- Decline Reasons -->
     <div class="row" id="applicantDeclineGraph" style="display:block">
        <div class="col-md-12 mt">
        <div class="clients_wrap_table dashboard-widget">
 <div id="applicantDecline" style="width: 100%; height: 250px; overflow: hidden; text-align: left;"></div>
        </div>
        </div>
			 </div>
      </
      <!-- Decline Reasons -->
     
    <!-- Best recruiters  -->
    <div class="clearfix dashboard-widget mt col-md-12" id="applicantDeclineGraphList" style="display:none">
          <div class="clearfix">
          <h4 class="pull-left"></h4>
          </div>
          <div class="clearfix tableStyles mt datatable_filter">
          <div id="applicantDeclineListDiv"></div>
             
            
          </div>
        </div>
    <!-- Best recruiters Ends  --> 
    </div>
      
  </section>
  
<script type="text/javascript">

function getDeclineApplicants(){
	
	document.getElementById("applicantDeclineGraphList").style.display="none";
	document.getElementById("applicantDeclineGraph").style.display="block";
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
			    success: function(data) {
			    	var total=data.skillsCount + data.unsuitableCount + data.badfitCount + data.interviewCount + data.salaryCount + data.otherCount;
			    	if(total>0){
			    	getAplicantDeclineAmChart(data);
			    }
			   	else{
			   		var result = '';
	 	   			result = result+"<div class='dataTables_empty'>"; 
	 	   			result = result+"</div> ";
	 	   	        $("#applicantDecline").empty();
	                $("#applicantDecline").append(result);
			   	}
			    	
			    }
		        	 
			 });
	  }
	  
}
	  
</script>

<script>
function getCustomDates(){
	
	 var dateSelection = $("#dateSelection").val(); 
	 if(dateSelection == 'custom'){
		 document.getElementById("customSearch").style.display="block";
		 $("#customize_btn").attr("disabled", "disabled");
		 
	 }else{
		 document.getElementById("customSearch").style.display="none";
		 $("#customize_btn").removeAttr("disabled");
		 $("#fromDate").val('');
		 $("#toDate").val('');
		 document.getElementById("fromDateDiv").style.display="none";
		  document.getElementById("toDateDiv").style.display="none";
	 }
}

</script>

<script type="text/javascript">
  function getAplicantDeclineAmChart(data){
	  
	  var total=data.skillsCount + data.unsuitableCount + data.badfitCount + data.interviewCount + data.salaryCount + data.otherCount;
	  var mainChar = [];
		 if(data.skillsCount>0){
			 mainChar.push( {
				    "category": "Skills",
					"column-1": data.skillsCount,
					"color": "#9FA8DA",
					"count": data.skillsCount,
		            "percentage":(((data.skillsCount)/total)*100).toFixed(2)
			    } );
			 }
		 if(data.unsuitableCount>0){
			 mainChar.push( {
				    "category": "Over Qualified",
					"color": "#90CAF9",
					"column-1": data.unsuitableCount,
					"count": data.unsuitableCount,
		            "percentage": (((data.unsuitableCount)/total)*100).toFixed(2)
			    } );
			 }
		 if(data.badfitCount>0){
			 mainChar.push( {
				 "category": "Bad Fit",
					"column-1": data.badfitCount,
					"color": "#FFAB91",
					"count": data.badfitCount,
		            "percentage": (((data.badfitCount)/total)*100).toFixed(2)
			    } );
			 }
		 if(data.interviewCount>0){
			 mainChar.push( {
				    "category": "Interview",
					"column-1": data.interviewCount,
					"color": "#A5D6A7",
					"count": data.interviewCount,
		            "percentage": (((data.interviewCount)/total)*100).toFixed(2)
			    } );
			 }
		 if(data.salaryCount>0){
			 mainChar.push( {
				    "category": "Salary",
					"column-1": data.salaryCount,
					"color": "#FFE0B2",
					"count": data.salaryCount,
		            "percentage": (((data.salaryCount)/total)*100).toFixed(2)
			    } );
			 }
		 if(data.otherCount>0){
			 mainChar.push( {
					"category": "Other",
					"column-1": data.otherCount,
					"color": "#BCAAA4",
					"count": data.otherCount,
		            "percentage": (((data.otherCount)/total)*100).toFixed(2)
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
            	"enabled": true
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
  } );
  function removeErrMsg(){
	  document.getElementById("fromDateDiv").style.display="none";
  }
  function removeErrMsgTo(){
	  document.getElementById("toDateDiv").style.display="none";
	  
  }
  </script>

  

<jsp:include page="../masterFooter.jsp" />