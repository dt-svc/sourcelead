
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
	getApplicantBackoutReportCount();
});
</script>

 <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Applicant(s) Backout</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/reportsDashboard'/>">Reports Dashboard</a> &raquo; <a href="<c:url value='${appPath}/performanceReportList'/>">Performance Reports</a> &raquo; Applicant(s) BackOut </div>
   
    </section>
    
    
    <!-- hiring Process  -->
    <div class="vendor_wrap clearfix">
   
   
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
	            <option value='Last_7_days' selected >Last 7 days</option>
	            <option value='Last_15_days'>Last 15 days</option>
	            <option value='1_month'>1 month</option>
	            <option value='3_months'>3 months</option>
	            <option value='YTD'>YTD</option>
	            <option value='Last_Year'>Last Year</option>
	            <option value="custom">Custom</option>
          </select>
		<div class="repSelBtn"><button type="button" class="btn btn-primary btn-sm" id="customize_btn" onclick = "getApplicantBackoutReportCount();getBackoutApplicantList();">Run</button></div> 
          
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
              <div class="repCustomDateBtn"><button type="button" class="btn btn-primary btn-sm" onclick="getApplicantBackoutReportCount();getBackoutApplicantList();"><i class="fa fa-search"></i></button></div>
          </div>
          <!-- Custom Date --> 
        </div>    		
    		</li> 
    		<li class="listSearch"><input type="text" class="repSearchBar" placeholder='Search' id='applicantBackoutReportsTableSearch'/><span class="repSearchIcon" onclick="getApplicantBackoutReportCount();getBackoutApplicantList();"></span></li>
    	</ul>
    </div>     
    
     
       <!-- Decline Reasons -->
     <div class="row" id="applicantBackoutGraph" style="display:block">
        <div class="col-md-12">
        <div class="clients_wrap_table dashboard-widget" id="applicantBackoutReportDivId" style="display:block">
              <div id="backoutReason" style="width:100%; height: 250px; margin-bottom: 25px;" ></div>
        </div>
        </div>
		</div>
      <!-- Decline Reasons -->
      <div class="project_tabs_inn_table" id="applicantBackoutListDivId" style="display:none">
          <div id="applicantBackoutListDiv" class="datatable_filter repDataSearch"></div>
      </div>
      
          </div>
  
  </section>
  
  <script>
  function getApplicantBackoutReportCount(){
	  
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
				   	url: sourceleadUrl+"/getApplicantBackoutReport?${_csrf.parameterName}=${_csrf.token}&jobIds="+jobIds+"&dateSelection="+dateSelection+"&fromDate="+fromDate+"&toDate="+toDate,
				    success: function(response) {
				    	
				    	if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
			    		    showError(response.responseMessage);
			    	  	} else if (response.response === "AWKWARD") {
			    		    location.href = "/errorPage";
			    		} else if (response.response === "SUCCESS") {
						  	  var total = (response.object.totalCount);
							  	var listView = document.getElementById("listView");
					        	 listView.addEventListener("click", function() {
					        		 getBackoutApplicantList();
					        		 document.getElementById("applicantBackoutGraph").style.display="none";
								    	document.getElementById("applicantBackoutListDivId").style.display="block";
								    	var element = document.getElementById("listView");
								    	var grid = document.getElementById("graphView");
								    	element.classList.add("rAct");
								    	grid.classList.remove("rAct");
								    	$(".listSearch").show();
								    	
								  });
					        	 
					        	 var graphicView = document.getElementById("graphView");
					        	 graphicView.addEventListener("click", function() {
					        		 if(total > 0 ){
											loadApplicantBackoutChart(response);
										}else{
											var result = '';
							 	   			result = result+"<div class='dataTables_empty'>"; 
							 	   			result = result+"</div> ";
							 	   	        $("#backoutReason").empty();
							                $("#backoutReason").append(result);
										}
					        		 document.getElementById("applicantBackoutGraph").style.display="block";
								    	document.getElementById("applicantBackoutListDivId").style.display="none";
								    	
								    	var element = document.getElementById("listView");
								    	var grid = document.getElementById("graphView");
								    	element.classList.remove("rAct");
								    	grid.classList.add("rAct");
								    	$(".listSearch").hide();
								  });
									if(total > 0 ){
										loadApplicantBackoutChart(response);
									}else{
										var result = '';
						 	   			result = result+"<div class='dataTables_empty'>"; 
						 	   			result = result+"</div> ";
						 	   	        $("#backoutReason").empty();
						                $("#backoutReason").append(result);
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
  
  <script type="text/javascript">
  
  $("#applicantBackoutReportsTableSearch").keydown(function(e) {
	   if(e.which === 13) {
		   getApplicantBackoutReportCount();
		   getBackoutApplicantList();
	   }
	});
 
 function getBackoutApplicantList(){
	 var searchValue = $("#applicantBackoutReportsTableSearch").val();
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
				   	url: sourceleadUrl+"/getApplicantBackoutReportForInduvidualJob?${_csrf.parameterName}=${_csrf.token}&jobIds="+jobIds+"&dateSelection="+dateSelection+"&fromDate="+fromDate+"&toDate="+toDate+"&searchValue="+searchValue,
				    success: function(response) {
				    	if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
			    		    showError(response.responseMessage);
			    	  	} else if (response.response === "AWKWARD") {
			    		    location.href = "/errorPage";
			    		} else if (response.response === "SUCCESS") {
			    			var result = "";
				             result  = result + '    <table class="table table-bordered" id="applicantBackoutPagenationId">     ';
							 result  = result + '     <thead>                                 ';
							 result  = result + '       <tr>                                  ';
							  result  = result + '         <th>Id</th>                       ';
							 result  = result + '         <th>Jobs</th>                       ';
							 result  = result + '         <th>Current Company</th>                     ';
							 result  = result + '         <th>Compensation</th>             ';
							 result  = result + '         <th>Better Offer</th>                    ';
							 result  = result + '         <th>Cold Feet</th>                  ';
							 result  = result + '         <th>Personal</th>                     ';
							 result  = result + '         <th>Other</th>                      ';
							 result  = result + '         <th>Total</th>                      ';
							 result  = result + '       </tr>                                 ';
							 result  = result + '       </thead>                              ';
							 result  = result + '       <tbody>                               ';
							 for (var i = 0; i < response.data.length; i++) { 
								 var report = response.data[i];
								 if(report.totalCount != 0){
								 result  = result + '       <tr>                                  ';
								 result  = result + '		  <td><div>'+report.generatedId+'</div></td>  ';
								 result  = result + '		  <td><div>'+report.jobName+'</div></td>  ';
								 result  = result + '         <td><div>'+report.currentCompany+'</div></td>               ';
								 result  = result + '         <td><div>'+report.compensation+'</div></td>               ';
								 result  = result + '         <td><div>'+report.betterOffer+'</div></td>               ';
								 result  = result + '         <td><div>'+report.coldFeet+'</div></td>               ';
								 result  = result + '         <td><div>'+report.personal+'</div></td>               ';
								 result  = result + '         <td><div>'+report.other+'</div></td>               ';
								 result  = result + '         <td><div>'+report.totalCount+'</div></td>               ';
								 //result  = result + '         <td><div>31</div></td>              ';
								 result  = result + '         </tr>     ';
								 }
							 }
							
							 
							 result  = result + '     </tbody>                                ';
							 result  = result + '   </table>                                  ';
							 $("#applicantBackoutListDiv").empty();
							 $("#applicantBackoutListDiv").append(result);
							 applicantBackoutListPagination();
			    		}else if (response.indexOf('Sign In') !== -1) {
			    			location.replace(sourceleadUrl);
			    		} else {
			    		    showError("Operation failed, please try again");
			    		}
		}
			  });
			  
		  }
	  
	 
	 
	 
 }
 function applicantBackoutListPagination(){
		$('#applicantBackoutPagenationId').DataTable( { 
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
</script> 
  
<script>
///////////////////// Back To Top 
$(document).ready(function() {
    $(window).scroll(function() {
        if ($(this).scrollTop() > 200) {
            $('.backtotop').fadeIn('slow');
        } else {
            $('.backtotop').fadeOut('slow');
        }
    });
    $('.backtotop').click(function() {
        $("html, body").animate({scrollTop: 0}, 500);
        return false;
    });
});
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
  function loadApplicantBackoutChart(response){
	  var total = (response.object.currentCompany + response.object.compensation + response.object.betterOffer + response.object.coldFeet +response.object.personal + response.object.other);
	  var mainChar = [];
	  if(response.object.currentCompany>0){
		  mainChar.push({
				"category": "Current Company",
				"column-1": response.object.currentCompany,
				"color": "#9FA8DA",
				"count": response.object.currentCompany,
				"percentage": (((response.object.currentCompany/total)*100)).toFixed(2)
			});
	  }
	  if(response.object.compensation>0){
		  mainChar.push({
			  "category": "Compensation",
				"color": "#90CAF9",
				"column-1": response.object.compensation,
				"count": response.object.compensation,
				"percentage": (((response.object.compensation/total)*100)).toFixed(2)
			});
	  }
	  if(response.object.betterOffer>0){
		  mainChar.push( {
			  "category": "Better Offer",
				"column-1": response.object.betterOffer,
				"color": "#FFAB91",
				"count": response.object.betterOffer,
				"percentage": (((response.object.betterOffer/total)*100)).toFixed(2)
			} );
	  }
	  if(response.object.coldFeet>0){
		  mainChar.push( {
			  "category": "Cold Feet",
				"column-1": response.object.coldFeet,
				"color": "#A5D6A7",
				"count": response.object.coldFeet,
				"percentage": (((response.object.coldFeet/total)*100)).toFixed(2)
			} );
	  }
	  if(response.object.personal>0){
		  mainChar.push( {
			  "category": "Personal",
				"column-1": response.object.personal,
				"color": "#FFE0B2",
				"count": response.object.personal,
				"percentage": (((response.object.personal/total)*100)).toFixed(2)
			} );
	  }
	  if(response.object.other>0){
		  mainChar.push( {
			  "category": "Other",
				"column-1": response.object.other,
				"color": "#BCAAA4",
				"count": response.object.other,
				"percentage": (((response.object.other/total)*100)).toFixed(2)
			} );
	  }
	  
	  AmCharts.makeChart("backoutReason",
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
            	"fileName":"Applicant(s) Backout"
            	},
			"trendLines": [],
			"graphs": [
				{
					"balloonText": "",
					"colorField": "color",
					"fillAlphas": 1,
					"id": "AmGraph-1",
					"labelText": "[[count]]<br>[[percentage]]%",
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
  function removeErrMsg(){
	  document.getElementById("fromDateDiv").style.display="none";
  }
  function removeErrMsgTo(){
	  document.getElementById("toDateDiv").style.display="none";
	  
  }
  </script>
 
 <script>
$(function() {
        $('#dateSelection').change(function(){
			var optval = $(this).val();
			if(optval === "custom") {
				$(".cusTomShow").show();
			}else{
				$(".cusTomShow").hide();
			}
        });
    });
</script>
 <script>
  function dobDatePicker() {
	  $('#dateSelection').datepicker({
	      dateFormat: 'yy-mm-dd',
	  });
	  
  }
</script>
 