<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<style type="text/css">
.timesheet_head .glyphicon-play {
right: 15px;
}
.vendor_wrap .timesheet_head ul li b{
color: #333;
}
</style>

<!-- ends files of export -->
<!-- Content Area -->
<script type="text/javascript">
window.onload = function() { 
	getRequirementList();
	getRequirementStageCounts();
	}
</script>

<script>
function getRequirementList(){
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
	var result = '';
	result = result +  '<table id="requirementReportPagination" class="display" cellspacing="0" width="100%" >';
	result = result + '	<thead>';
	result = result + '   <tr>';
	result = result + '      <th>Title</th>';
	result = result + '    <th>Client</th>';
	result = result + '    <th>Location</th>';
	result = result + '    <th>No.Of positions</th>';
	result = result + '    <th>Owner</th>';
	result = result + '    <th>Status</th>';
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
	                title: SourceLeadWeb_loginOrg+' - Jobs Stages Reports',
	            },
	            {
	                extend: 'pdf',
	                title: SourceLeadWeb_loginOrg+' - Jobs Stages Reports',
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
function getRequirementStageCounts(){
		$.ajax({
	         type : "POST",
	          url: sourceleadUrl+"/getRequirementCountBasedOnStatus?"+tokenParamName+"="+tokenValue+"",    
	          success :function(data) {
	          
	        	  var json = JSON.parse(data);
		        	
		        	var  newRequirementCount = json.newRequirementsCount;
		        	var  draftRequirementCount = json.draftRequirementsCount;
		        	var  submittedRequirementCount = json.submittedRequirementsCount;
		        	var  openRequirementCount = json.openRequirementsCount; 
		        	var  onHoldRequirementCount = json.onholdRequirementsCount;
		        	var  cancelRequirementCount = json.cancelRequirementsCount;
// 		        	var  approvedRequirementCount = json.approvedRequirementsCount;
		        	var  filledclosedRequirementsCount = json.filledclosedRequirementsCount;
		        	var  unfilledclosedRequirementsCount = json.unfilledclosedRequirementsCount;
		        	var  rejectedRequirementsCount = json.rejectedRequirementsCount;
		        	
		        	
		        	if(newRequirementCount>0){
	                      $("#newRequirementCount").text(("("+newRequirementCount+")"));
	                  }else{
	                      $("#newRequirementCount").html("(0)");
	                  }
		        	if(draftRequirementCount>0){
	                      $("#draftRequirementCount").text("("+draftRequirementCount+")");
	                  }else{
	                      $("#draftRequirementCount").html("(0)");
	                  }
		        	if(submittedRequirementCount>0){
	                      $("#submittedRequirementCount").text("("+submittedRequirementCount+")");
	                  }else{
	                      $("#submittedRequirementCount").html("(0)");
	                  }
		        	if(openRequirementCount>0){
	                      $("#openRequirementCount").text("("+openRequirementCount+")");
	                  }else{
	                      $("#openRequirementCount").html("(0)");
	                  }
		        	if(onHoldRequirementCount>0){
	                      $("#onHoldRequirementCount").text("("+onHoldRequirementCount+")");
	                  }else{
	                      $("#onHoldRequirementCount").html("(0)");
	                  }
		        	if(cancelRequirementCount>0){
	                      $("#cancelRequirementCount").text("("+cancelRequirementCount+")");
	                  }else{
	                      $("#cancelRequirementCount").html("(0)");
	                  }
		        	if(rejectedRequirementsCount>0){
	                      $("#rejectedRequirementsCount").text("("+rejectedRequirementsCount+")");
	                  }else{
	                      $("#rejectedRequirementsCount").html("(0)");
	                  }
		        	if(filledclosedRequirementsCount>0){
	                      $("#filledclosedRequirementsCount").text("("+filledclosedRequirementsCount+")");
	                  }else{
	                      $("#filledclosedRequirementsCount").html("(0)");
	                  }
		        	if(unfilledclosedRequirementsCount>0){
	                      $("#unfilledclosedRequirementsCount").text("("+unfilledclosedRequirementsCount+")");
	                  }else{
	                      $("#unfilledclosedRequirementsCount").html("(0)");
	                  }
	        	  
		        	getrequirementStagePieChat(newRequirementCount,draftRequirementCount,submittedRequirementCount,openRequirementCount,onHoldRequirementCount,cancelRequirementCount,filledclosedRequirementsCount,unfilledclosedRequirementsCount,rejectedRequirementsCount);
	          },
	          
	     }); 
}
</script>

<script>
function getRequirementStageList(){
		var status = [];
		$('input:checkbox[name=requirementStatus]:checked').each(function() 
	    {
			status.push($(this).val());
	    });
		$.ajax({
	         type : "POST",
	          url: sourceleadUrl+"/getRequirementListBasedOnStatus?"+tokenParamName+"="+tokenValue+"&status="+status,    
	          success :function(data) {
	        	  prepareReportDataDiv(data);
	          },
	          
	     }); 
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
      <h2 class="pull-left">Job Stages Reports</h2>
		
    </div>
    <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&nbsp;&raquo;&nbsp;<a href="<c:url value='/recruitersReports'/>">Reports</a>&nbsp;&raquo; Jobs Stages</div>
  </section>
  <div class="timesheet_out">
    <div class="vendor_wrap">
     
      <div class="timesheet_head">

<div class="pull-left reportBtns">
	  <!-- <div class="reportBtn">
		<select class="form-control select-bg" id="reportTypeId" onChange="flushCustomDates(); getDayWiseReports(); custDates();">
		  <option value="">select</option>
		  <option value="today">Today</option>
		  <option value="yesterday">Yesterday</option>
		  <option value="week">1 week</option>
		  <option value="twoweeks">2 weeks</option>
		  <option value="month">1 month</option>
		  <option value="twomonths">2 months</option>
		  <option value="custom">Custom</option> 
		</select>
	 </div>	 -->
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
		<a href="javascript:void(0)" class="oneA actRE" title="Grid List"><i class="fa fa-list"></i></a>
		<a href="javascript:void(0)" class="oneB" onclick="getRequirementStageCounts();" title="Pie chart"><i class="fa fa-pie-chart"></i></a>
	</span>
</div>
                     
        <ul style="padding: 0px 310px 0px 0px;" id="statusDiv" style="display: block">
        <li>
        	<b id="filter2ApprovePop"><i class="fa fa-filter paddingRight"></i>Status<span class="padFil"></span> <i class="glyphicon glyphicon-play" id="status-pop"></i></b>
        	<div id="filter2ApprovedPop" class="approved_pop">
		              <ul>
                		    <li><input type="checkbox" name="requirementStatus"  value ="NEW"  onclick="getRequirementStageList();">New</li>
							<li><input type="checkbox" name="requirementStatus"  value ="DRAFT"  onclick="getRequirementStageList();">Draft</li>
							<li><input type="checkbox" name="requirementStatus"  value ="SUBMITTED"  onclick="getRequirementStageList();">Submitted</li>
               				<li><input type="checkbox" name="requirementStatus"  value ="OPEN"  onclick="getRequirementStageList();">Open</li>
               				<li><input type="checkbox" name="requirementStatus"  value ="ONHOLD"  onclick="getRequirementStageList();">On Hold</li>
               				<li><input type="checkbox" name="requirementStatus"  value ="CANCEL"  onclick="getRequirementStageList();">Cancel</li>
               				<li><input type="checkbox" name="requirementStatus"  value ="FILLEDCLOSED"  onclick="getRequirementStageList();">Filled Closed</li>
               				<li><input type="checkbox" name="requirementStatus"  value ="UNFILLEDCLOSED"  onclick="getRequirementStageList();">Unfilled Closed</li>
               				<li><input type="checkbox" name="requirementStatus"  value ="REJECTED"  onclick="getRequirementStageList();">Rejected</li>
		              </ul>
		    </div>
        </li>
        </ul>
        <div class="clearfix"></div>
      </div>
  
 <div class="repTab1">  
      
	<div class="reportsCount reportsCountNew">
		<ul>
			<li title="NEW">
				<span><i class="fa fa-pencil"></i></span><b id="newRequirementCount"></b>
			</li>
			<li title="DRAFT" class="colorTwo">
				<span><i class="fa fa-file"></i></span><b id="draftRequirementCount"></b>
			</li>
			<li title="SUBMITTED">
				<span><i class="glyphicon glyphicon-send"></i></span><b id="submittedRequirementCount"></b>
			</li>
			<li title="OPEN" class="colorTwo">
				<span><i class="fa fa-folder-open"></i></span><b id="openRequirementCount"></b>
			</li>			
			<li title="CANCEL">
				<span><i class="fa fa-times"></i></span><b id="cancelRequirementCount"></b>
			</li>
			<li title="ONHOLD" class="colorTwo">
				<span><i class="fa fa-hand-rock-o"></i></span><b id="onHoldRequirementCount"></b>
			</li>
			<li title="FILLED CLOSED">
				<span><i class="fa fa-star"></i></span><b id="filledclosedRequirementsCount"></b>
			</li>
			<li title="UNFILLED CLOSED" class="colorTwo">
				<span><i class="fa fa-star-o"></i></span><b id="unfilledclosedRequirementsCount"></b>
			</li>
			<li title="REJECTED">
				<span><i class="fa fa-ban"></i></span><b id="rejectedRequirementsCount"></b>
			</li>
					<div class="clearfix"></div>
		</ul>
	</div>

  <div class="project_tabs_inn_table vendor_wrap_inn_table">
                 <div id="requirementReportsDiv12" class="repoPositi"></div>
                </div>      
</div>

 <div class="repTab2">
	  	<div class="chatOne" id="chartdiva"></div>	
	  </div>
      
 </div>


  </div>
</section>




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
		$("#statusDiv").css("display", "none");
	});
	$(".oneA").click(function(){
		$(".oneB").removeClass("actRE");
		$(".oneA").addClass("actRE");
		$(".repTab2").hide();
		$(".repTab1").show();
		$("#statusDiv").css("display", "block");
	});		
	</script>
	
	
	
<!-- <script src="resources/reports/amcharts.js"></script>
<script src="resources/reports/piechart.js"></script>
<script src="resources/reports/export.min.js"></script>
<link rel="stylesheet" href="resources/reports/export.css" type="text/css" media="all" /> 
<script src="resources/reports/light.js"></script>  -->

<script>
function getrequirementStagePieChat(newCount,draftCount,submittedCount,openCount,onHoldCount,cancelCount,filledclosedCount,unfilledclosedCount,rejectedCount){
	var chart = AmCharts.makeChart( "chartdiva", {
		  "type": "pie",
		  "theme": "light",
		  "dataProvider": [ {
			  "Stage": "New ("+newCount+")",
			  "value": newCount,
			  "color":"#c5427f",
			  "black":"#000"
		  }, {
		      "Stage": "Draft ("+draftCount+")",
		      "value": draftCount,
		      "color":"#5C48C9",
		      "black":"#000"
		  }, {
			  "Stage": "Submitted ("+submittedCount+")",
			  "value": submittedCount,
			  "color":"#40cb91",
			  "black":"#000"
		  }, {
			  "Stage": "Open ("+openCount+")",
			  "value": openCount,
			  "color":"#368ee0",
			  "black":"#000"
		  }, {
			  "Stage": "Cancel ("+cancelCount+")",
			  "value": cancelCount,
			  "color":"#CB4042",
			  "black":"#000"
		  }, {
			  "Stage": "OnHold ("+onHoldCount+")",
			  "value": onHoldCount,
			  "color":"#e48f3c",
			  "black":"#000"
		  }, {
			  "Stage": "Filled Closed ("+filledclosedCount+")",
			  "value": filledclosedCount,
			  "color":"#aa58e4",
			  "black":"#000"
		  }, {
			  "Stage": "UnFilled Closed ("+unfilledclosedCount+")",
			  "value": unfilledclosedCount,
			  "color":"#7fb951",
			  "black":"#000"
		  }, {
			  "Stage": "Rejected ("+rejectedCount+")",
			  "value": rejectedCount,
			  "color":"#da3838",
			  "black":"#000"
		  } ],
		  "valueField": "value",
		  "titleField": "Stage",
		  "colorField": "color",
		  "labelColorField": "black",
		  "balloonText": "[[title]]",
		  "outlineAlpha": 0.4,
		  "depth3D": 15,
		  "angle": 45,
		  "labelText": "[[title]]",
		  "export": {
		    "enabled": false
		  }
		  	  
		} );
}

</script>
<script>
$("#filter2ApprovePop").click(function(){
	$("#filter2ApprovedPop").slideToggle(200);
	$(".export_pop, .action-pop, #exportPop, #filter1ApprovedPop").slideUp(200);
});
$("html").click(function() {
    $("#filter2ApprovedPop").slideUp(200);
});
$("#filter2ApprovedPop, #filter2ApprovePop").click(function(e) {  
    e.stopPropagation();
});	
</script>  	
