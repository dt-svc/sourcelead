<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!-- <script src="resources/js/formvalidator.js"></script>
<script src="resources/js/jquery.dataTables.min.js"></script> -->
<!-- <link href="resources/css/dataTablePagination.css" rel="stylesheet" type="text/css"> -->
<!-- these files for autocomplete with tagit -->
<link href="resources/css/jquery-tagit.css" rel="stylesheet">
<script src="resources/js/csc.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/tag-it.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/tag-it.min.js" type="text/javascript" charset="utf-8"></script>
<!-- end files for autocomplete with tagit -->
<!-- these files for export -->
<link href="https://cdn.datatables.net/buttons/1.2.4/css/buttons.dataTables.min.css" rel="stylesheet">
<script src="//code.jquery.com/jquery-1.12.4.js" type="text/javascript" charset="utf-8"></script>
<script src="https://cdn.datatables.net/1.10.13/js/jquery.dataTables.min.js" type="text/javascript" charset="utf-8"></script>
<script src="https://cdn.datatables.net/buttons/1.2.4/js/dataTables.buttons.min.js" type="text/javascript" charset="utf-8"></script>
<script src="//cdn.datatables.net/buttons/1.2.4/js/buttons.flash.min.js" type="text/javascript" charset="utf-8"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jszip/2.5.0/jszip.min.js" type="text/javascript" charset="utf-8"></script>
<script src="//cdn.rawgit.com/bpampuch/pdfmake/0.1.18/build/pdfmake.min.js" type="text/javascript" charset="utf-8"></script>
<script src="//cdn.rawgit.com/bpampuch/pdfmake/0.1.18/build/vfs_fonts.js" type="text/javascript" charset="utf-8"></script>
<script src="//cdn.datatables.net/buttons/1.2.4/js/buttons.html5.min.js" type="text/javascript" charset="utf-8"></script>
<script src="//cdn.datatables.net/buttons/1.2.4/js/buttons.print.min.js" type="text/javascript" charset="utf-8"></script>
<!-- ends files of export -->

<!-- Content Area -->
<script type="text/javascript">
window.onload = function() { 
	getAllApplicantReports();
	}
</script>

<script type="text/javascript">
function getAllApplicantReports(){
	getDayWiseReports();
}


</script>


<!-- <script>
function getRequirementList(){
		var sourceleadUrl ='${sourceleadUrl}';
	    var status = "";
	    var clients = "";
		$.ajax({
	         type : "POST",
	          url: sourceleadUrl+"/getRequirementListByOrgLocationID?${_csrf.parameterName}=${_csrf.token}&status="+status+"&clients="+clients,    
	          success :function(data) {
	        	 prepareReportDataDiv(data);
	         },
	          
	     }); 
}
</script> -->
<script>
function prepareReportDataDiv(data){
	var result = '';
	result = result +  '<table id="requirementReportPagination" class="display" cellspacing="0" width="100%" >';
	result = result + '	<thead>';
	result = result + '   <tr>';
	result = result + '    <th>Requirement Title</th>';
	result = result + '    <th>Posted on</th>';
	result = result + '    <th>Applicants</th>';
	result = result + '    <th>Request Sent for Approval</th>';
	result = result + '    <th>Shortlisted</th>';
	result = result + '    <th>Submit to client</th>';
	result = result + '    <th>Interviewed</th>';
	result = result + '    <th>Offered</th>';
	result = result + '    <th>Rejected</th>';
	result = result + '   </tr>';
	result = result + '	</thead>';
	result = result + '	<tbody>';
	var json = JSON.parse(data);
    for (var i = 0; i < json.length; i++) {
    	var report= json[i];
    	result = result + '<tr>';
        result = result + '<td>';
        result = result + '<div><span>'+report.requirementTitle+'</span></div> ';
        result = result + '</td>';

        result = result + '<td>';
        if(report.postedOn != null && report.postedOn != '' && report.postedOn !='undefined'&& report.postedOn != null && report.postedOn != ''){
       	result = result + '<div><span>'+report.postedOn+'</span></div>';
 		} else{
		result = result + '<div><span>---</span></div>';
		    }
        result = result + '</td>';
        result = result + '<td>';
        if(report.applicantsCount != null && report.applicantsCount != '' && report.applicantsCount !='undefined'&& report.applicantsCount != null && report.applicantsCount != ''){
         result = result + '<div><span>'+report.applicantsCount+'</span></div>';
        }
        else{
		 result = result + '<div><span>---</span></div>';
		    }
         result = result + '</td>';
       	 result = result + '<td>';
       	 result = result + "<div><span>"+report.requestSentCount+"</span></div>";
         result = result + '</td>';
         result = result + '<td>';
         result = result + "<div><span>"+report.shortlisted+"</span></div>";
         result = result + '</td>';
         result = result + '<td>';
       	 result = result + "<div><span>"+report.submitToClientCount+"</span></div>";
         result = result + '</td>';
         result = result + '<td>';
         result = result + "<div><span>"+report.interviewCount+"</span></div> ";
         result = result + '</td>';
         result = result + '<td>';
         result = result + "<div><span>"+report.offeredCount+"</span></div> ";
         result = result + '</td>';
         result = result + '<td>';
       	 result = result + "<div><span>"+report.rejectedCount+"</span></div>";
         result = result + '</td>';
         result = result + '</tr>';
    }
	result = result + '	</tbody>';
	result = result + '</table>';
	  $("#requirementReportsDiv12").empty();
    $("#requirementReportsDiv12").append(result);
    requirementReportPagination();
    //getAllCounts();
}

</script>
<script>
function requirementReportPagination(){
	$('#requirementReportPagination').DataTable({ 
		 dom: 'Bfrtip',
	        buttons: [
	            'excel', 'pdf'
	        ],
		
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false,
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
	$(function() {
		$('#ExportStartDate').datepicker({
			changeMonth: true,
			changeYear: true, 
			onSelect : function(selected) {
				var dt = new Date(selected);
				dt.setDate(dt.getDate() + 1);
				$("#ExportEndDate").datepicker("option", "minDate", dt);
				addValidClass();
			}
		
		});
		
		$('#ExportEndDate').datepicker({
			changeMonth: true,
			changeYear: true, 
			beforeShow: function(){
		           this.value='';
		     },
			onSelect : function(selected) {
				var dt = new Date(selected);
				dt.setDate(dt.getDate() - 1);
				$("#ExportStartDate").datepicker("option", "maxDate", dt);
				addValidClass();
			}
		});
		
	});
</script>	

<section class="content-wraper">
  <section class="content-header clearfix">
    <div class="pull-left">
      <h2 class="pull-left">Reports</h2>
		<div class=" pull-left reportsH">
			<a title="Reports"><i class="fa fa-pie-chart"></i>Requirements Posted</a>
			<div class="RePop">
				<ul>
					<li><a href="<c:url value='/requirementListForReports'/>"><span><i class="fa fa-briefcase"></i></span>Requirements Posted</a></li>
					<li><a href="reports.html"><i class="fa fa-user"></i>Client Requirements</a></li>
					<li><a href="reports.html"><i class="fa fa-user"></i>Applicants Received</a></li>
					<li><a href="<c:url value='/applicantsListForReports'/>"><span><i class="fa fa-briefcase"></i></span>Applicants</a></li>
					<li><a href="reports.html"><i class="fa fa-user"></i>Short  listed</a></li>
					<li><a href="reports.html"><i class="fa fa-user"></i>Interview</a></li>
					<li><a href="reports.html"><i class="fa fa-user"></i>Offer</a></li> 
					<li><a href="reports.html"><i class="fa fa-user"></i>Custom</a></li> 
				</ul>
			</div>
		</div>
    </div>
    <div class="pull-right brud-crum">Home &raquo; Vendor &raquo; Client Contact Details</div>
  </section>
  <div class="timesheet_out">
    <div class="vendor_wrap">
     
      <div class="timesheet_head">

<div class="pull-left reportBtns">
	  <div class="reportBtn">
		<select class="form-control select-bg" id="reportTypeId" onChange="reportsCheck(); getDayWiseReports(); custDates(); flushCustomDates();">
		 <option value="">select</option>
		  <option value="today">Today</option>
		  <option value="yesterday">Yesterday</option>
		  <option value="week">1 week</option>
		  <option value="twoweeks">2 weeks</option>
		  <option value="month">1 month</option>
		  <option value="twomonths">2 months</option>
		   <option value="custom">Custom</option> 
		</select>
	 </div>	
	 <!-- <span class="dashboard-datepkr"><input type="text" class="input-sm hasDatepicker" id="datepicker"><i class="fa fa-calendar"></i></span>
	 <span class="dashboard-datepkr"><input type="text" class="input-sm hasDatepicker" id="datepicker1"><i class="fa fa-calendar"></i></span>	 -->
	 <span class="dashboard-datepkr cusShow">
	 <input type="text" class="form-control" value="" id="ExportStartDate" name="StartDate" field-name="Start Date" data-validation="required" readonly="readonly" placeholder="Start Date"> <i class="fa fa-calendar"></i>
	 <p id="reportStartDate" class="ReError"></p>
	 </span>
	<span class="dashboard-datepkr cusShow">
	<input type="text"  class="form-control" value="" id="ExportEndDate" name="EndDate" field-name="End Date" data-validation="required" readonly="readonly" placeholder="End Date"><i class="fa fa-calendar"></i>
	<p id="reportEndDate" class="ReError"></p>
	</span>
	<span class="cusShow"><input type="button" value="Submit" class="btn btn-primary repBtn" onclick="getCustomReports();" /></span>
	
		<span class="repoBtns">
		<a href="javascript:void(0)" class="oneA actRE"><i class="fa fa-list"></i></a>
		<a href="javascript:void(0)" class="oneB" onclick="allApplicantsCountForChart();"><i class="fa fa-pie-chart"></i></a>
	</span>	
	
</div>
                     
        <ul style="padding: 2px 300px 2px 0px;">
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
        </ul>
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
				<span><img src="images/interview1.png" alt=""/></span><b id="interviewCount">0</b>
			</li>
			<li title="Offers" id="offermadeli">
				<span><i class="fa fa-tags"></i></span><b id="offerCount">0</b>
			</li>
			<li title="Rejected" id="rejectedli" class="colorTwo">
				<span><i class="glyphicon glyphicon-trash"></i></span><b id="rejectedCount">0</b>
			</li>
					<div class="clearfix"></div>
		</ul>
	</div>

  <div class="project_tabs_inn_table vendor_wrap_inn_table">
                 <div id="requirementReportsDiv12" class="repoPositi"></div>
                </div>   
                
</div>  

  <div class="repTab2">
  	<div id="chartdiv"></div>  
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
	$('#reportStartDate').empty();
	$('#reportEndDate').empty(); 
	var reportType = $("#reportTypeId").val();
	var startDate = $("#ExportStartDate").val();
	var endDate = $("#ExportEndDate").val();
	var sourceleadUrl ='${sourceleadUrl}';
	if(reportType!="custom"){
	$.ajax({
         type : "POST",
         url: sourceleadUrl+"/getApplicantsReport?${_csrf.parameterName}=${_csrf.token}&reportType="+reportType+"&startDate="+startDate+"&endDate="+endDate,     
          success :function(data) {
        	  prepareReportDataDiv(data);
        	 getAllCounts(reportType,startDate,endDate);
         },
          
     });  
	}
	
}

</script>


<script>
function getAllCounts(reportType,startDate,endDate){
	 $('#applicantsli').load(allApplicantsCount(reportType,startDate,endDate)); 
}
</script>

<script>
function allApplicantsCount(reportType,startDate,endDate){
	var sourceleadUrl = '${sourceleadUrl}';
	 $.ajax({
       type : "POST",
        url: sourceleadUrl+"/getApplicantsTotalCounts?${_csrf.parameterName}=${_csrf.token}&reportType="+reportType+"&startDate="+startDate+"&endDate="+endDate,        
        success :function(data) { 
       	 var json = JSON.parse(data);
       	 $("#applicantCount").html(json.applicantsSum); 
    	 $("#shortlistedCount").html(json.shortlistedSum);
    	 $("#submitToClientCount").html(json.submitToClientSum);
    	 $("#interviewCount").html(json.interviewsSum);
    	 $("#offerCount").html(json.offeredSum); 
       	$("#rejectedCount").html(json.rejectedSum); 
   	
        },
	  });
}
</script>

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
function getCustomReports(){
	var reportType = $("#reportTypeId").val();
	var startDate = $("#ExportStartDate").val();
	var endDate = $("#ExportEndDate").val();
	var sourceleadUrl ='${sourceleadUrl}';
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
	          url: sourceleadUrl+"/getApplicantsReport?${_csrf.parameterName}=${_csrf.token}&reportType="+reportType+"&startDate="+startDate+"&endDate="+endDate,
	          success :function(data) {
	        	 prepareReportDataDiv(data);
	        	 getAllCounts(reportType,startDate,endDate);
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

<script type="text/javascript"> 
	function reportsCheck(){
		 if($(".oneA" ).hasClass( "actRE" )){
			 
		}
		if($(".oneB" ).hasClass( "actRE" )){
			alert("ffdf222");
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

<script src="resources/reports/amcharts.js"></script>
<script src="resources/reports/pie.js"></script>
<link rel="stylesheet" href="resources/reports/export.css" type="text/css" media="all" />
<script src="resources/reports/light.js"></script>
  
     <script>
     function getApplicantBarChat(applicantsCount,shortlisted,interviewCount,submitToClientCount,offeredCount,rejectedCount,requestSentCount){
     var chart = AmCharts.makeChart("chartdiv", {
	     "type": "serial",
	     "theme": "light",
	     "marginRight": 70,
	     "dataProvider": [{
	     "Stage": "Applicants",
	     "StageCount": applicantsCount,
	     "color": "#FF0F00"
	     }, {
	     "Stage": "Shortlisted",
	     "StageCount": shortlisted,
	     "color": "#FF6600"
	     }, {
	     "Stage": "Interview",
	     "StageCount": interviewCount,
	     "color": "#FF9E01"
	     }, {
	     "Stage": "Submit To Client",
	     "StageCount": submitToClientCount,
	     "color": "#FCD202"
	     }, {
	     "Stage": "Offered",
	     "StageCount": offeredCount,
	     "color": "#F8FF01"
	     }, {
	     "Stage": "Rejected",
	     "StageCount": rejectedCount,
	     "color": "#B0DE09"
	
	     }, {
	     "Stage": "Request Sent",
	     "StageCount": requestSentCount,
	     "color": "#04D215"
	     }],
	     "valueAxes": [{
	     "axisAlpha": 0,
	     "position": "left",
	     "title": "Visitors from country"
	     }],
	     "startDuration": 1,
	     "graphs": [{
	     "balloonText": "<b>[[category]]: [[value]]</b>",
	     "fillColorsField": "color",
	     "fillAlphas": 0.9,
	     "lineAlpha": 0.2,
	     "type": "column",
	     "valueField": "StageCount"
	     }],
	     "chartCursor": {
	     "categoryBalloonEnabled": false,
	     "cursorAlpha": 0,
	     "zoomable": false
	     },
	     "categoryField": "Stage",
	     "categoryAxis": {
	     "gridPosition": "start",
	     "labelRotation": 45
	     },
	     "export": {
	     "enabled": true
	     }
	
	     });
     }
     </script> 
<script>
function allApplicantsCountForChart(){
	var sourceleadUrl = '${sourceleadUrl}';
	 $.ajax({
       type : "POST",
        url: sourceleadUrl+"/getApplicantsCountForChart?${_csrf.parameterName}=${_csrf.token}",        
        success :function(data) { 
       	 var json = JSON.parse(data);
       	getApplicantBarChat(json.applicantsCount,json.shortlisted,json.interviewCount,json.submitToClientCount,json.offeredCount,json.rejectedCount,json.requestSentCount);
       	 /* $("#applicantCount").html(json);  */
        },
	  });
}
</script>


