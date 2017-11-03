<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
 <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<style>
#billing_chartdiv {
  	width:100%; height:300px;
}
.reportBtns {
    width: 65%;
}
.reportBtn {
    width: 20%;
}																		
</style>

<script type="text/javascript">
window.onload = function() { 
	getApplicantReceivedReports();
	}
</script>

<script type="text/javascript">
function getApplicantReceivedReports(){
	getDayWiseReports();
}


</script>

<script>
function prepareReportDataDiv(data){
	var result = '';
	result = result +  '<table id="requirementReportPagination" class="display" cellspacing="0" width="100%" >';
	result = result + '	<thead>';
	result = result + '   <tr>';
	result = result + '    <th>Applicant Name</th>';
	result = result + '    <th>Source</th>';
	result = result + '    <th>Received on</th>';
	result = result + '    <th>Job Title</th>';
	result = result + '    <th>Stage</th>';
	result = result + '   </tr>';
	result = result + '	</thead>';
	result = result + '	<tbody>';
	var json = JSON.parse(data);
    for (var i = 0; i < json.length; i++) {
    	var report= json[i];
    	result = result + '<tr>';
    	
    	 result = result + '<td>';
         if(report.applicantName != null && report.applicantName != '' && report.applicantName !='undefined'&& report.applicantName != null && report.applicantName != ''){
        		result = result + '<div><span title="'+report.applicantName+'">'+report.applicantName+'</span></div>';
  			} else{
 				result = result + '<div><span>---</span></div>';
 		    }
         result = result + '</td>';
         
         result = result + '<td>';
         if(report.source != null && report.source != '' && report.source !='undefined'&& report.source != null && report.source != ''){
         		result = result + '<div><span title="'+report.source+'">'+report.source+'</span></div>';
   			} else{
  				result = result + '<div><span>---</span></div>';
  		    }
         result = result + '</td>';
         
         result = result + '<td>';
         if(report.receivedOn != null && report.receivedOn != '' && report.receivedOn !='undefined'&& report.receivedOn != null && report.receivedOn != ''){
         		result = result + '<div><span>'+report.receivedOn+'</span></div>';
   			} else{
  				result = result + '<div><span>---</span></div>';
  		    }
         result = result + '</td>';
         
        result = result + '<td>';
        result = result + '<div><span title="'+report.requirementTitle+'">'+report.requirementTitle+'</span></div> ';
        result = result + '</td>';

        result = result + '<td>';
        if(report.stage != null && report.stage != '' && report.stage !='undefined'&& report.stage != null && report.stage != ''){
       	result = result + '<div><span>'+report.stage+'</span></div>';
 		} else{
		result = result + '<div><span>---</span></div>';
		    }
        
        result = result + '</td>';
        
         result = result + '</tr>';
    }
	result = result + '	</tbody>';
	result = result + '</table>';
	  $("#requirementReportsDiv12").empty();
    $("#requirementReportsDiv12").append(result);
    requirementReportPagination();
}

</script>
<script>
function requirementReportPagination(){
	$('#requirementReportPagination').DataTable({ 
		 dom: 'Bfrtip',
	        buttons: [
	        	{
	                extend: 'excel',
	                title: 'Applicants Recieved',
	            },
	            {
	                extend: 'pdf',
	                title: 'Applicants Recieved',
	            }
	        ],
		
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
	$(".vendor_wrap_inn_table").find('a.buttons-excel').prop("title", "Excel");
	$(".vendor_wrap_inn_table").find('a.buttons-pdf').prop("title", "PDF");
}

</script>
<script>
	$(function() {
		$('#ExportStartDate').datepicker({
			changeMonth: true,
			changeYear: true, 
			dateFormat : 'dd-mm-yy',
			onSelect : function(selected) {
				var dt = new Date(selected);
				var dt = toDate("#ExportStartDate");
				dt.setDate(dt.getDate() + 1); 
				$("#ExportEndDate").datepicker("option", "minDate", dt);
				addValidClass();
			}
		
		});
		
		$('#ExportEndDate').datepicker({
			changeMonth: true,
			changeYear: true, 
			dateFormat : 'dd-mm-yy',
			beforeShow: function(){
		           this.value='';
		     },
			onSelect : function(selected) {
				var dt = new Date(selected);
				var dt = toDate("#ExportEndDate");
				dt.setDate(dt.getDate() - 1);
				$("#ExportStartDate").datepicker("option", "maxDate", dt);
				addValidClass();
			}
		});
		
	});
</script>	
<script>
function toDate(selector) {
    var from = $(selector).val().split("-");
    return new Date(from[2], from[1] - 1, from[0]);
}
</script>
<section class="content-wraper">
  <section class="content-header clearfix">
    <div class="pull-left">
      <h2 class="pull-left">Applicants Reports</h2>
		
    </div>
        <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='/userReports'/>">Dashboard Reports</a> &raquo; Applicants Received Reports </div>
  </section>
  <div class="timesheet_out">
    <div class="vendor_wrap">
     
      <div class="timesheet_head">

<div class="pull-left reportBtns">
	  <div class="reportBtn dropdown-select">
		<select class="form-control" id="reportTypeId" onChange="getDayWiseReports(); custDates(); flushCustomDates();">
<!-- 		<option value="">-- Select --</option> -->
		  <option value="today">Today</option>
		  <option value="yesterday">Yesterday</option>
		  <option value="week">1 Week</option>
		  <option value="twoweeks">2 Weeks</option>
		  <option value="month">1 Month</option>
		  <option value="twomonths">2 Months</option>
		   <option value="custom">Custom</option> 
		</select>
	 </div>	
	<span class="repoBtns">
			<a href="javascript:void(0)" class="oneA actRE" title="Grid List"><i class="fa fa-list"></i></a>
			<a href="javascript:void(0)" class="oneB" onclick="getDefaultChart()" title="Pie chart"><i class="fa fa-pie-chart"></i></a>
			</span>	
	<div class="col-md-6">
		 <span class="dashboard-datepkr cusShow">
		 <input type="text" class="form-control" value="" id="ExportStartDate" name="StartDate" field-name="Start Date" data-validation="required" readonly="readonly" placeholder="Start Date"> <i class="fa fa-calendar"></i>
		 <p id="reportStartDate" class="ReError"></p>
		 </span>
		<span class="dashboard-datepkr cusShow">
		<input type="text"  class="form-control" value="" id="ExportEndDate" name="EndDate" field-name="End Date" data-validation="required" readonly="readonly" placeholder="End Date"><i class="fa fa-calendar"></i>
		<p id="reportEndDate" class="ReError"></p>
		</span>
		<span class="cusShow"><input type="button" value="Submit" class="btn btn-primary repBtn" onclick="getCustomReports();" /></span>
		</div>
		
		
	</div>
                     
        <!-- <ul style="padding: 2px 300px 2px 0px;">
          <li><i class="fa fa-filter"></i><span class="padFil"></span><i class="glyphicon glyphicon-play" id="status-pop"></i>|
            <div class="export_pop">
              <ul>
                <li>PDF<i class="fa fa-file-pdf-o"></i></li>
                <li>Excel<i class="fa fa-file-excel-o"></i></li>
                <li>Word<i class="fa fa-file-word-o"></i></li>
              </ul>
            </div>
          </li>

          <div class="clearfix"></div>
        </ul> -->
        <div class="clearfix"></div>
      </div>
     
   <div class="repTab1">    
	<div class="reportsCount">
		<ul>
		




			<li title="Applicants" id="applicantsli">
				<span><i class="fa fa-users"></i></span><b id="applicantCount">0</b>
			</li>
			<li title="Shortlisted" id="shortlistedli" class="colorTwo">
				<span><i class="fa fa-file-text"></i></span><b id="shortlistedCount">0</b>
			</li>
			<li title="Submit to Client" id="submittoclientli">
				<span><i class="glyphicon glyphicon-send"></i></span><b id="submitToClientCount">0</b>
			</li>
			<li title="Interviews" id="interviewli" class="colorTwo">
				<span><img src="resources/images/interview1.png" alt=""/></span><b id="interviewCount">0</b>
			</li>
			<li title="Offers" id="offermadeli">
				<span><i class="fa fa-tags"></i></span><b id="offerCount">0</b>
			</li>
			<li title="Rejected" id="rejectedli" class="colorTwo">
				<span><i class="fa fa-ban"></i></span><b id="rejectedCount">0</b>
			</li>
					<div class="clearfix"></div>
		</ul>
	</div>

  <div class="project_tabs_inn_table vendor_wrap_inn_table">
                 <div id="requirementReportsDiv12" class="repoPositi"></div>
                </div>      
      
 </div>
</div>
<div class="repTab2">
  	<div class="chatNew" id="container"></div>	 
  </div>                 
      
 </div>
  </div>
</section>
<!--   Error Message   -->
<div class="vendor_wrap_mask"></div>
<div class="vendor_error">
  <p>Delete conformation message....</p>
  <textarea placeholder="Enter message"></textarea>
  <a class="close_color" href="javascript:void(0)">Yes</a><a href="javascript:void(0)">No</a> </div>


<script>
function custDates(){
	var reportType = $("#reportTypeId").val();
	if(reportType=="custom"){
	    $(".cusShow").show();
	}
	if(reportType!="custom"){
	    $(".cusShow").hide();
	}
}
</script>

<script>
// Reports Active
	$(".oneB").click(function(){
		$(".oneA").removeClass("actRE");
		$(".oneB").addClass("actRE");
		$(".repTab1").hide();
		$(".repTab2").show();
	});
	$(".oneA").click(function(){
		$(".oneB").removeClass("actRE");
		$(".oneA").addClass("actRE");
		$(".repTab2").hide();
		$(".repTab1").show();
	});		
	</script>

<script>
// Error pop
$(document).ready(function() {
    $(".glyphicon-trash").click(function(){
		$(".vendor_error").slideToggle(200);
		$(".vendor_wrap_mask").fadeIn(200);
	});
	$(".vendor_error a").click(function(){
		$(".vendor_error").slideUp(200);
		$(".vendor_wrap_mask").fadeOut(200);
	});
});	

// Approved Popup
$(document).ready(function() {
    $("#appro-pop").click(function(){
		$(".approved_pop").slideToggle(200);
	});
    $("html").click(function() {
        $(".approved_pop").slideUp(200);
    });
    $(".approved_pop, #appro-pop").click(function(e) {
        e.stopPropagation();
    });	
});

// Export Popup
$(document).ready(function() {
    $("#export-pop").click(function(){
		$(".export_pop").slideToggle(200);
	});
    $("html").click(function() {
        $(".export_pop").slideUp(200);
    });
    $(".export_pop, #export-pop").click(function(e) {
        e.stopPropagation();
    });	
});	
	
// project side bar
$(document).ready(function() {
    $(".proj_show").click(function(){
		$(".clients_side_wrap").addClass("clients_side_wrap_active");
		$(".side_mask").fadeIn(200);
		$("body").css('overflow-y', 'hidden');
	});
    $(".proj_close, .side_mask").click(function(){
		$(".clients_side_wrap").removeClass("clients_side_wrap_active");
		$(".side_mask").fadeOut(200);
		$("body").css('overflow-y', 'auto');
	});	
});

// project side bar tabs
$(document).ready(function() {
	$("#tab-1").show()
    $(".project-tabs-menu a").click(function(event) {
        event.preventDefault();
        $(this).parent().addClass("current");
        $(this).parent().siblings().removeClass("current");
        var tab = $(this).attr("href");
        $(".project_tabs_inn").not(tab).css("display", "none");
        $(tab).fadeIn();
    });
});

// project side bar tabs
$(document).ready(function() {
	$("#tabb-1").show()
    $(".new-tabs a").click(function(event) {
        event.preventDefault();
        $(this).parent().addClass("current");
        $(this).parent().siblings().removeClass("current");
        var tab = $(this).attr("href");
        $(".new-tabs_inn").not(tab).css("display", "none");
        $(tab).fadeIn();
    });
});

// script for scroll Popups:: Add or Edit details
$(document).ready(function(event) {	
$(".addUser-org").click(function(){
	 $(".edit-details").toggleClass("edit-details-active");
	 $(".collapse-div").removeClass("edit-details-active");
 });
 $(".close-div").click(function(){
	 $(this).closest(".edit-details").toggleClass("edit-details-active");
	 });
 $(".addUser-org, .assign-org, .collapse-div-adduser, .collapse-div, .edit-details").click(function(e){
	 e.stopPropagation();
 });
 $('html').click(function(){
	$(".edit-details").removeClass("edit-details-active");
   });	
   $("#endDate").click(function(){
	   $(".endDate").toggle(200);
   });
   event.preventDefault();
});

$(".addSkills").click(function(){
	$(".add-skills").toggleClass('add-skills-active');
	});
$(".close-div").click(function(){
	$(".add-skills").removeClass('add-skills-active');
	});
$(".add-skills").click(function(e){
	 e.stopPropagation();
 });


// Bar chat pop
$(document).ready(function() {
    $(".graf_icon").click(function(){
		$(".pipeline_graf").slideToggle(200);
	});
$( "ul#canName-ch li").click(function(){
   var m = $(this).text();
  $("#canName").text(m);
});
});
</script> 
<script>
$( "#pri-ch li").click(function(){
   var m = $(this).text();
    var mid = $(this).attr('id');
    $("#pri-val").text(m);
     if('h' === $(this).attr("id")){
          $("#pri-val").removeClass("text-warning text-primary").addClass("text-danger");
     }else if('m' === $(this).attr("id")){
        $("#pri-val").removeClass("text-danger text-warning").addClass("text-primary");
     }else{
      $("#pri-val").removeClass("text-danger text-primary").addClass("text-warning");
     }   
});
</script> 
<script>
$(document).ready(function(){
	// Levels adding script
$(".add-offer").click(function(){
	 $(".addOffer-Req").toggleClass("show-div");
	 $(".addInterview-Re").removeClass("show-div");
	 $(".addApplicant-Re").removeClass("show-div");
 });
$(".add-interview").click(function(){
	 $(".addInterview-Re").toggleClass("show-div");
	 $(".addOffer-Req").removeClass("show-div");
	 $(".addApplicant-Re").removeClass("show-div");
 });
$(".add-applicant").click(function(){
	 $(".addApplicant-Re").toggleClass("show-div");
	 $(".addOffer-Req").removeClass("show-div");
	 $(".addInterview-Re").removeClass("show-div");
 });
$(".reminder").click(function(){
	 $(".reminder-div").toggleClass("reminder-div-active");
 });

 $(".close-div").click(function(){
	 $(this).parent().toggleClass("show-div");
	 });
 $(".div-close").click(function(){
	 $(this).parent().toggleClass("showDiv");
	 });

 $(".reminder").click(function(e){
	 e.stopPropagation();
 });
 $('html').click(function(){
    $("").removeClass("show-div");
   });	
   $("#endDate").click(function(){
	   $(".endDate").toggle(200);
   });
   });
   
// new SlideToggle
 $("#id1").click(function(){
	  $(".hide-row").slideToggle(200);
	  $(".profile_details h4 i").toggleClass('icon-add');
	 });	 
   
   
   
</script>
      <script>
	// Close Advanced Search
		function closePop(){
			$("div.cand-advsrch-popup").hide();
		}
		function openPop(){
			$("div.cand-advsrch-popup").toggle();
		}
	</script> 

<script type="text/javascript">
	function flushCustomDates() {
		$('#ExportStartDate').val('');
		$('#ExportEndDate').val('');
		$.datepicker._clearDate('#ExportStartDate');
		$.datepicker._clearDate('#ExportEndDate');
	}
	</script>
<script>
function getDayWiseReports(){
	$('#container').empty();
	$('#reportStartDate').empty();
	$('#reportEndDate').empty(); 
	var reportType = $("#reportTypeId").val();
	var startDate = $("#ExportStartDate").val();
	var endDate = $("#ExportEndDate").val();
	if(reportType!="custom"){
	$.ajax({
         type : "POST",
         url: sourceleadUrl+"/getApplicantReceivedReports?"+tokenParamName+"="+tokenValue+"&reportType="+reportType+"&startDate="+startDate+"&endDate="+endDate,     
          success :function(data) {
	        	 prepareReportDataDiv(data);
	        	 var json = JSON.parse(data);
	        	 getAllCounts(json.length,reportType,startDate,endDate);
          		getDayWiseAppRecReports();
         },
          
     });  
	}
	
}

</script>


<script>
function getAllCounts(applicantConts,reportType,startDate,endDate){
	var sourceleadUrl = '${sourceleadUrl}';
	 $.ajax({
      type : "POST",
      url: sourceleadUrl+"/getReceivedApplicantsCountForReports?"+tokenParamName+"="+tokenValue+"&reportType="+reportType+"&startDate="+startDate+"&endDate="+endDate, 
       success :function(data) { 
      	 var json = JSON.parse(data);
      	 $("#applicantCount").html(applicantConts); 
         $("#shortlistedCount").html(json.shortlistedSum); 
  	     $("#submitToClientCount").html(0);
  	     $("#offerCount").html(json.offeredSum); 
 	     $("#interviewCount").html(json.interviewsSum);
	     $("#rejectedCount").html(json.rejectedSum);
       },
	  });
}
</script>

<!-- <script>
function allReceivedApplicantsCount(applicantConts){
	var sourceleadUrl = '${sourceleadUrl}';
	 $.ajax({
       type : "POST",
        url: sourceleadUrl+"/getReceivedApplicantsCountForReports?${_csrf.parameterName}=${_csrf.token}",        
        success :function(data) { 
       	 var json = JSON.parse(data);
       	 $("#applicantCount").html(applicantConts); 
         $("#shortlistedCount").html(json.shortlisted); 
   	     $("#submitToClientCount").html(0);
   	     $("#offerCount").html(json.offeredCount); 
  	     $("#interviewCount").html(json.interviewCount);
 	     $("#rejectedCount").html(json.rejectedCount);
        },
	  });
}
</script> -->


<script>
function getCustomReports(){
	var reportType = $("#reportTypeId").val();
	var startDate = $("#ExportStartDate").val();
	var endDate = $("#ExportEndDate").val();
	if(startDate == null || startDate == '' || startDate == 'undefined'){
		$("#reportStartDate").empty().append("Start Date is Required") ; 
	}
	if(endDate == null || endDate == '' || endDate == 'undefined'){
		$("#reportEndDate").empty().append("End Date is Required") ; 
	}
	else{
		$.ajax({
	         type : "POST",
	         /*  url: sourceleadUrl+"/getRequirementReports?${_csrf.parameterName}=${_csrf.token}&reportType="+reportType+"&startDate="+startDate+"&endDate="+endDate, */  
	          url: sourceleadUrl+"/getApplicantReceivedReports?"+tokenParamName+"="+tokenValue+"&reportType="+reportType+"&startDate="+startDate+"&endDate="+endDate,
	          success :function(data) {
	        	 prepareReportDataDiv(data);
	        	 var json = JSON.parse(data);
	        	 getAllCounts(json.length,reportType,startDate,endDate);
	        	 getDayWiseAppRecReports();
	         },
	          
	     }); 
	}
	 
}
</script>
<script type="text/javascript"> 
	function addValidClass(){
		 var StartDate = document.getElementById("ExportStartDate").value;
		 if(StartDate){
			 $('#reportStartDate').empty() 
		 }
		 
		 var EndDate = document.getElementById("ExportEndDate").value;
		 if(EndDate){
			 $('#reportEndDate').empty(); 
		 }
}
</script>

<!-- <script src="resources/reports/amcharts.js"></script> 
<script src="resources/reports/serial.js"></script>
<script src="resources/reports/light.js"></script>
<script src="resources/reports/export.min.js"></script>
<link rel="stylesheet" href="resources/reports/export.css" type="text/css" media="all" />   
<script>
function applicantReceivedReportLineChart(result){

var chart = AmCharts.makeChart("chartdiv", {
	  "type": "serial",
	  "theme": "light",
	  "dataProvider": result,
	  "valueAxes": [{
	    "gridColor": "#FFFFFF",
	    "gridAlpha": 0.2,
	    "dashLength": 0
	  }],
	  "gridAboveGraphs": true,
	  "startDuration": 1,
	  "graphs": [{
	    "title": "Week #20",
	    "balloonText": "[[title]]: <b>[[value]]</b>",
	    "bullet": "round",
	    "bulletSize": 10,
	    "bulletBorderColor": "#ffffff",
	    "bulletBorderAlpha": 1,
	    "bulletBorderThickness": 2,
	    "valueField": "value1"
	  }, {
	    "title": "Week #22",
	    "balloonText": "[[title]]: <b>[[value]]</b>",
	    "bullet": "round",
	    "bulletSize": 10,
	    "bulletBorderColor": "#ffffff",
	    "bulletBorderAlpha": 1,
	    "bulletBorderThickness": 2,
	    "valueField": "value2"
	  }],
	  "chartCursor": {
	    "categoryBalloonEnabled": false,
	    "cursorAlpha": 0,
	    "zoomable": false
	  },
	  "categoryField": "category",
	  "categoryAxis": {
	    "gridPosition": "start",
	    "gridAlpha": 0
	  },
	  "legend": {}
	});
}
</script>  --> 



<!-- <script src="resources/reports/highcharts.js"></script>
<script src="resources/reports/exporting.js"></script>  -->
  
	<script>
	function applicantReceivedReportLineChart(chartData,applicantData,shortlistedData,interviewData,submitedtoClientData,offeredData,rejectedData,requestSentData){
		Highcharts.chart('container', {
    chart: {
        type: 'column'
    },
    title: {
        text: 'Applicants Received'
    },
    xAxis: {
        categories: chartData,
        crosshair: true
    },
    yAxis: {
        min: 0,
        title: {
            text: 'Counts'
        }
    },
    tooltip: {
        headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
        pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
            '<td style="padding:0"><b>{point.y}</b></td></tr>',
        footerFormat: '</table>',
        shared: true,
        useHTML: true
    },
    plotOptions: {
        column: {
            pointPadding: 0.2,
            borderWidth: 0
        }
    },
    series: [{
        name: 'Applicants',
        data: applicantData

    }, {
    	name: 'Request Sent',
        data: requestSentData

    }, {
    	name: 'Shortlisted',
        data: shortlistedData

    }, {
        name: 'Submit To Client',
        data: submitedtoClientData

    }, {
    	name: 'Interview',
        data: interviewData

    },
		{
    	name: 'Offered',
        data: offeredData

    },{
    	name: 'Rejected',
        data: rejectedData

    }
    ]	
});	
	}
	</script>  


<script>
function getDefaultChart(){
	$('#container').empty();
	var reportType = $("#reportTypeId").val();
	if(reportType=="custom"){
		getCustomReports();
	}
	else{
		getDayWiseReports();
	}
}
</script>

<script>
function getDayWiseAppRecReports(){
	$('#reportStartDate').empty();
	$('#reportEndDate').empty(); 
	var reportType = $("#reportTypeId").val();
	var startDate = $("#ExportStartDate").val();
	var endDate = $("#ExportEndDate").val();
	/* if(reportType!="custom"){ */
	$.ajax({
         type : "POST",
         url: sourceleadUrl+"/getDayWiseAppRecReports?"+tokenParamName+"="+tokenValue+"&reportType="+reportType+"&startDate="+startDate+"&endDate="+endDate,     
          success :function(data) {
        	/*  prepareReportDataDiv(data); */
        	 var json = JSON.parse(data);
        	 if(data != null && data !='undefined' && data !=null &&  data != [] && json.length >0){ 
		        	if(json.length > 0)	
		 	   		{
		        		    var chartData = [];
		        		    var applicantData =[];
		        		    var shortlistedData =[];
		        		    var interviewData =[];
		        		    var submitedtoClientData =[];
		        		    var offeredData =[];
		        		    var rejectedData  =[];
		        		    var requestSentData  =[];
		        		    
							for(var i=0; i < json.length ; i++){
		        			 
		        			 var jobs = json[i];
		        			    chartData.push( 
		        			    	 jobs.orgName
		        			     );
		        			    applicantData.push( 
		        			    	 jobs.applicantsCount
		        			    );
		        			    shortlistedData.push( 
			        			    	 jobs.shortlisted
			        			    );
		        			    submitedtoClientData.push( 
			        			    	 jobs.submitToClientCount
			        			    );
		        			    interviewData.push( 
			        			    	 jobs.interviewCount
			        			    );
		        			    offeredData.push( 
			        			    	 jobs.offeredCount
			        			    );
		        			    rejectedData.push( 
			        			    	 jobs.rejectedCount
			        			    );
		        			    requestSentData.push( 
			        			    	 jobs.requestSentCount
		        			    );
		        			  }
		        		 applicantReceivedReportLineChart(chartData,applicantData,shortlistedData,interviewData,submitedtoClientData,offeredData,rejectedData,requestSentData);
		 	   		}
	        	 }
        	 else{
        		 var result ="";
	        		result = result + ' <div class="row pavEmp">                                            ';
					result = result + '	<div class="dataTables_empty">                                     ';
					result = result + '	</div>                                                      ';
					result = result + ' </div>                                                      ';
				  $("#container").empty();
				    $("#container").append(result);
        	 }
        	// getAllCounts(json.length,reportType,startDate,endDate);
         },
          
     });  
	/* } */
	
	
	
	
	
	
}

</script>

