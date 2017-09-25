<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

 <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!-- Content Area -->
<script type="text/javascript">
window.onload = function() { 
	getRequirementList();
	}
</script>

<script>
function getRequirementList(){
		var sourceleadUrl ='${sourceleadUrl}';
	    var status = "";
	    var clients = "";
		$.ajax({
	         type : "POST",
	          url: sourceleadUrl+"/getRequirementReports?"+tokenParamName+"="+tokenValue+"&status="+status+"&clients="+clients,    
	          success :function(data) {
	        	 prepareReportDataDiv(data);
	         },
	          
	     }); 
}
</script>
<script>
function prepareReportDataDiv(data){
	var SourceLeadWeb_loginOrg= '${SourceLeadWeb_loginOrg}';
	
	
	 var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  prepareReportDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  prepareReportDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  prepareReportDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  prepareReportDate = 'YYYY-DD-MM'; 
	  }  
	
	
	var result = '';
	result = result +  '<table id="requirementReportPagination" class="display" cellspacing="0" width="100%" >';
	result = result + '	<thead>';
	result = result + '   <tr>';
	result = result + '     <th>Job Title</th>';
	result = result + '    <th>Client</th>';
	result = result + '    <th>Employement Type</th>';
	result = result + '    <th>Location</th>';
	result = result + '    <th>No.Of positions</th>';
	result = result + '    <th>Owner</th>';
	result = result + '    <th>Status</th>';
	result = result + '    <th>No.Of Applicants</th>';
	result = result + '    <th>Posted On <br>(<small class="custom-small">'+prepareReportDate+'</small>)</th>';
	result = result + '   </tr>';
	result = result + '	</thead>';
	result = result + '	<tbody>';
	var json = JSON.parse(data);
    for (var i = 0; i < json.length; i++) {
    	var report= json[i];
    	result = result + '<tr>';
        result = result + '<td>';
        result = result + '<div><span title="'+report.jobTitle+'">'+report.jobTitle+'</span></div> ';
        result = result + '</td>';

        result = result + '<td>';
        if(report.clientName != null && report.clientName != '' && report.clientName !='undefined'&& report.clientName != null && report.clientName != ''){
       	result = result + '<div><span title="'+report.clientName+'">'+report.clientName+'</span></div>';
 		} else{
		result = result + '<div><span>---</span></div>';
		    }
        result = result + '</td>';
        result = result + '<td>';
        if(report.jobType != null && report.jobType != '' && report.jobType !='undefined'&& report.jobType != null && report.jobType != ''){
         result = result + '<div><span title="'+report.jobType+'">'+report.jobType+'</span></div>';
        }
        else{
		 result = result + '<div><span>---</span></div>';
		    }
         result = result + '</td>';
       	 result = result + '<td>';
        if(report.country != null && report.country != '' && report.country !='undefined'&& report.country != null && report.country != ''){
       	 result = result + "<div><span>"+report.country+"</span></div>";
        }
        else{
		 result = result + "<div><span>---</span></div>";
		    }
         result = result + '</td>';
        
         result = result + '<td>';
        if(report.positions != null && report.positions != '' && report.positions !='undefined'&& report.positions != null && report.positions != ''){
        		result = result + "<div><span>"+report.positions+"</span></div>";
        }
        else{
		 result = result + "<div><span>---</span></div>";
		    }
        result = result + '</td>';
        
        result = result + '<td>';
        if(report.manager != null && report.manager != '' && report.manager !='undefined'&& report.manager != null && report.manager != ''){
       	 result = result + "<div><span>"+report.manager+"</span></div>";
        }
        else{
		 result = result + "<div><span>---</span></div>";
		    }
       		 
        result = result + '</td>';
        
        result = result + '<td>';
        if(report.requirementSubmissionStatus != null && report.requirementSubmissionStatus != '' && report.requirementSubmissionStatus !='undefined'&& report.requirementSubmissionStatus != null && report.requirementSubmissionStatus != ''){
        		result = result + "<div><span>"+report.requirementSubmissionStatus+"</span></div> ";
        }
        else{
		    	result = result + "<div><span>---</span></div>";
		    }
        result = result + '</td>';
        
        result = result + '<td>';
        if(report.totalApplicants != null && report.totalApplicants != '' && report.totalApplicants !='undefined'&& report.totalApplicants != null && report.totalApplicants != ''){
        		result = result + "<div><span>"+report.totalApplicants+"</span></div> ";
        }
        else{
		    	result = result + "<div><span>0</span></div>";
		    }
        result = result + '</td>';
        
        result = result + '<td>';
        if(report.postedDateStr != null && report.postedDateStr != '' && report.postedDateStr !='undefined'&& report.postedDateStr != null && report.postedDateStr != ''){
       		 result = result + "<div><span>"+report.postedDateStr+"</span></div>";
        }
        else{
		    	result = result + "<div><span>---</span></div>";
		    }
        result = result + '</td>';
        result = result + '</tr>';
    }
	result = result + '	</tbody>';
	result = result + '</table>';
	  $("#requirementReportsDiv12").empty();
    $("#requirementReportsDiv12").append(result);
    requirementReportPagination(SourceLeadWeb_loginOrg);
}

</script>
<script>
function requirementReportPagination(SourceLeadWeb_loginOrg){
	$('#requirementReportPagination').DataTable({ 
		 dom: 'Bfrtip',
	        buttons: [
	        	{
	                extend: 'excel',
	                title: SourceLeadWeb_loginOrg+' - Jobs Posted Reports',
	            },
	            {
	                extend: 'pdf',
	                title: SourceLeadWeb_loginOrg+' - Jobs Posted Reports',
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
	$(".vendor_wrap_inn_table").find('a.buttons-excel').prop("title", "Export to Excel");
	$(".vendor_wrap_inn_table").find('a.buttons-pdf').prop("title", "Export to PDF");
}

</script>
<script>
	$(function() {
		
		var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
		  var finalDateFormatEduUpdate = '';
		  if(locationDate.match("yyyy-MM-dd")){
			  finalDateFormatEduUpdate = 'yy-mm-dd';
		  }if(locationDate.match("dd-MM-yyyy")){
			  finalDateFormatEduUpdate = 'dd-mm-yy'; 
		  }if(locationDate.match("MM-dd-yyyy")){
			  finalDateFormatEduUpdate = 'mm-dd-yy'; 
		  }if(locationDate.match("yyyy-dd-MM")){
			  finalDateFormatEduUpdate = 'yy-dd-mm'; 
		  }
		$('#ExportStartDate').datepicker({
			changeMonth: true,
			changeYear: true, 
			dateFormat : finalDateFormatEduUpdate,
			onSelect : function(selected) {
				var dt = new Date(selected);
				//var dt = toDate("#ExportStartDate");/
				//dt.setDate(dt.getDate() + 1);
				$("#ExportEndDate").datepicker("option", "minDate", selected);
				addValidClass();
			}
		
		});
		
		$('#ExportEndDate').datepicker({
			changeMonth: true,
			changeYear: true, 
			dateFormat : finalDateFormatEduUpdate,
			beforeShow: function(){
		           this.value='';
		     },
			onSelect : function(selected) {
				var dt = new Date(selected);
				//var dt = toDate("#ExportEndDate");
				//dt.setDate(dt.getDate() - 1);
				$("#ExportStartDate").datepicker("option", "maxDate", selected);
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

<style>
.project_tabs_inn_table.vendor_wrap_inn_table div span {
	max-width: 150px;
}
</style>

<section class="content-wraper">
  <section class="content-header clearfix">
    <div class="pull-left">
      <h2 class="pull-left">Reports</h2>
		<div class=" pull-left reportsH">
			<a title="Reports"><i class="fa fa-pie-chart"></i>Jobs Posted</a>
			<div class="RePop">
				<ul>
					<li><a href="<c:url value='/requirementListForReports'/>"><span><i class="fa fa-briefcase"></i></span>Jobs Posted</a></li>
					<li><a href="<c:url value='/requirementStagesListForReports'/>"><span><i class="fa fa-briefcase"></i></span>Jobs Stages</a></li>
<%-- 					<li><a href="<c:url value='/applicantsReceivedListForReports'/>"><span><i class="fa fa-briefcase"></i></span>Applicants Received</a></li> --%>
<%-- 				    <li><a href="<c:url value='/applicantsListForReports'/>"><span><i class="fa fa-briefcase"></i></span>Applicants</a></li> --%>
				    <li><a href="<c:url value='/applicantsSubmittedListForReports'/>"><span><i class="fa fa-briefcase"></i></span>Applicants Submitted</a></li>
<%-- 				    <li><a href="<c:url value='/requirementAssignToReports'/>"><span><i class="fa fa-briefcase"></i></span>Job Assign To</a></li> --%>
				    <li><a href="<c:url value='/candidateListForReports'/>"><span><i class="fa fa-briefcase"></i></span>Added Candidates</a></li>
				    <li><a href="<c:url value='/timeSheetReports'/>"><span><i class="fa fa-briefcase"></i></span>TimeSheet Reports</a></li>
				</ul>
			</div>
		</div>
    </div>
   <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&nbsp;&raquo;&nbsp;<a href="<c:url value='/recruitersReports'/>">Reports</a>&nbsp;&raquo; Jobs Posted</div>
  </section>
  <div class="timesheet_out">
    <div class="vendor_wrap">
     
      <div class="timesheet_head">

<div class="pull-left reportBtns reportcutom_Btns">
	  <div class="reportBtn dropdown-select">
		<select class="form-control" id="reportTypeId" onChange="flushCustomDates(); getDayWiseReports(); custDates();">
<!-- 		 <option value="">-- Select --</option> -->
		  <option value="today">Today</option>
		  <option value="yesterday">Yesterday</option>
		  <option value="week">1 Week</option>
		  <option value="twoweeks">2 Weeks</option>
		  <option value="month">1 Month</option>
		  <option value="twomonths">2 Months</option>
		   <option value="custom">Custom</option> 
		</select>
	 </div>	
	 <!-- <span class="dashboard-datepkr"><input type="text" class="input-sm hasDatepicker" id="datepicker"><i class="fa fa-calendar"></i></span>
	 <span class="dashboard-datepkr"><input type="text" class="input-sm hasDatepicker" id="datepicker1"><i class="fa fa-calendar"></i></span>	 -->
	 <div class="cusShow pull-left">
	 <span class="dashboard-datepkr">
	 <input type="text" class="form-control" value="" id="ExportStartDate" name="StartDate" field-name="Start Date" data-validation="required" readonly="readonly" placeholder="Start Date"> <i class="fa fa-calendar"></i>
	 <p id="reportStartDate" class="ReError"></p>
	 </span>
	<span class="dashboard-datepkr">
	<input type="text"  class="form-control" value="" id="ExportEndDate" name="EndDate" field-name="End Date" data-validation="required" readonly="readonly" placeholder="End Date"><i class="fa fa-calendar"></i>
	<p id="reportEndDate" class="ReError"></p>
	</span>
	<span><input type="button" value="Submit" class="btn btn-primary repBtn" onclick="getCustomReports();" /></span>
	</div>
<!-- 		<span class="repoBtns">
		<a href="javascript:void(0)" class="oneA actRE"><i class="fa fa-list"></i></a>
		<a href="javascript:void(0)" class="oneB"><i class="fa fa-pie-chart"></i></a>
	</span> -->
	
</div>
                     
<!--         <ul style="padding: 2px 300px 2px 0px;">
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
      
	<!-- <div class="reportsCount">
		<ul>
			<li title="Applicants">
				<span><i class="fa fa-users"></i></span><b>100</b>
			</li>
			<li title="Shortlisted" class="colorTwo">
				<span><i class="fa fa-file-text"></i></span><b>100</b>
			</li>
			<li title="Submit to Client">
				<span><i class="glyphicon glyphicon-send"></i></span><b>100</b>
			</li>
			<li title="Interviews" class="colorTwo">
				<span><img src="resources/images/interview1.png" alt=""/></span><b>100</b>
			</li>
			<li title="Offers">
				<span><i class="fa fa-tags"></i></span><b>100</b>
			</li>
			<li title="Rejected" class="colorTwo">
				<span><i class="glyphicon glyphicon-trash"></i></span><b>100</b>
			</li>
					<div class="clearfix"></div>
		</ul>
	</div> -->

  <div class="project_tabs_inn_table vendor_wrap_inn_table">
                 <div id="requirementReportsDiv12" class="repoPositiNoCount"></div>
                </div>      
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
         url: sourceleadUrl+"/getRequirementReports?"+tokenParamName+"="+tokenValue+"&reportType="+reportType+"&startDate="+startDate+"&endDate="+endDate,     
          success :function(data) {
        	 prepareReportDataDiv(data);
         },
          
     });  
	}
	
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
	          url: sourceleadUrl+"/getRequirementReports?"+tokenParamName+"="+tokenValue+"&reportType="+reportType+"&startDate="+startDate+"&endDate="+endDate,    
	          success :function(data) {
	        	 prepareReportDataDiv(data);
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
