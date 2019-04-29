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
.repDataSearch .dt-buttons {
	top: -116px !important;
}
</style>

<!-- ends files of export -->
<!-- Content Area -->
<script type="text/javascript">
window.onload = function() { 
	getRequirementList();
	}
</script>

<script>
$("#reqStagesReportsTableSearch").keydown(function(e) {
	   if(e.which === 13) {
		   getRequirementList();
	   }
	});

function getRequirementList(){
	var searchValue = $("#reqStagesReportsTableSearch").val();
	searchValue = searchValue ? searchValue : "";
	    var status = [];
		$('input:checkbox[name=requirementStatus]:checked').each(function() 
	    {
			status.push($(this).val());
	    });
		$("#reqStagesLoderDiv").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:999;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	    $.ajax({
	         type : "POST",
	          url: sourceleadUrl+"/getRequirementReports?"+tokenParamName+"="+tokenValue+"&status="+status+"&searchValue="+searchValue,    
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
	var draftCount = 0;
	var submittedCount = 0;
	var rejectedCount = 0;
	var openCount = 0;
	var onHoldCount = 0;
	var filledClosedCount = 0;
	var cancelCount = 0;
	var unfilledClosedCount = 0;
	var closedCount = 0;
	var newCount = 0;
    for (var i = 0; i < json.length; i++) {
    	var report= json[i];
    	if(report.requirementSubmissionStatus=='DRAFT'){
    		draftCount++;
    	}
    	if(report.requirementSubmissionStatus=='SUBMITTED'){
    		submittedCount++;
    	}
    	if(report.requirementSubmissionStatus=='REJECTED'){
    		rejectedCount++;
    	}
    	if(report.requirementSubmissionStatus=='OPEN'){
    		openCount++;
    	}
    	if(report.requirementSubmissionStatus=='ONHOLD'){
    		onHoldCount++;
    	}
    	if(report.requirementSubmissionStatus=='FILLEDCLOSED'){
    		filledClosedCount++;
    	}
    	if(report.requirementSubmissionStatus=='CANCEL'){
    		cancelCount++;
    	}
    	if(report.requirementSubmissionStatus=='UNFILLEDCLOSED'){
    		unfilledClosedCount++;
    	}
    	if(report.requirementSubmissionStatus=='CLOSED'){
    		closedCount++;
    	}
    	if(report.requirementSubmissionStatus=='NEW'){
    		newCount++;
    	}
    	result = result + '<tr>';
        result = result + '<td>';
        result = result + '<div class="tbldata-dotdot-sm"><span title="'+report.generatedId+" - "+report.jobTitle+'">'+report.generatedId+" - "+report.jobTitle+'</span></div> ';
        result = result + '</td>';

        result = result + '<td>';
        if(report.clientName != null && report.clientName != '' && report.clientName !='undefined'&& report.clientName != null && report.clientName != ''){
       	result = result + '<div class="tbldata-dotdot-sm"><span title="'+report.clientName+'">'+report.clientName+'</span></div>';
 		} else{
		result = result + '<div class="tbldata-dotdot-sm"><span>---</span></div>';
		    }
        result = result + '</td>';
        
       	result = result + '<td>';
        if(report.locationsList) {
		    result = result + '	<div class="positionRelative">';
		    for(var k=0; k<1; k++){
	        	result = result + '	<p class="tbldata-dotdot-me pull-left">'+report.locationsList[k]+'</p>';
	        }
        	result = result +'		<div class="show_Empdetails showjoblist">';
			result = result +'			<i class="fa fa-ellipsis-v"></i>';
			result = result +'			<div class="emailvalues emailvaluesNew">';
			for(var k=0; k<report.locationsList.length; k++){
				result = result +'			<p><i class="fa fa-map-marker"></i>'+report.locationsList[k]+'</p>';
			}
		    result = result +'			</div>';
		    result = result +'		</div>';
		  	result = result +'	</div>'; 
        } else {
        	result = result + '<div>------</div>';
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
	var countObj = {
			"draftCount" : draftCount,
			"submittedCount" : submittedCount,
			"rejectedCount" : rejectedCount,
			"openCount" : openCount,
			"onHoldCount" : onHoldCount,
			"filledClosedCount" : filledClosedCount,
			"cancelCount" : cancelCount,
			"unfilledClosedCount" : unfilledClosedCount,
			"closedCount": closedCount,
			"newCount" : newCount
	}
	getRequirementStageCounts(countObj);
	  $("#requirementReportsDiv12").empty();
    $("#requirementReportsDiv12").append(result);
    $("#reqStagesLoderDiv").empty();
    $("#reqStagesLoderDiv").removeAttr('style');
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
function getRequirementStageCounts(countObj){
		/* $.ajax({
	         type : "POST",
	          url: sourceleadUrl+"/getRequirementCountBasedOnStatus?"+tokenParamName+"="+tokenValue+"",    
	          success :function(data) {
	          
	        	  var json = JSON.parse(data);*/
		        	var  newRequirementCount = countObj.newCount;
		        	var  draftRequirementCount = countObj.draftCount;
		        	var  submittedRequirementCount = countObj.submittedCount;
		        	var  openRequirementCount = countObj.openCount; 
		        	var  onHoldRequirementCount = countObj.onHoldCount;
		        	var  cancelRequirementCount = countObj.cancelCount;
// 		        	var  approvedRequirementCount = json.approvedRequirementsCount;
		        	var  filledclosedRequirementsCount = countObj.filledClosedCount;
		        	var  unfilledclosedRequirementsCount = countObj.unfilledClosedCount;
		        	var  rejectedRequirementsCount = countObj.rejectedCount;
		        	var  closedRequirementsCount = countObj.closedCount; 
		        	
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
	                  }if(closedRequirementsCount>0){
	                      $("#closedRequirementsCount").text("("+closedRequirementsCount+")");
	                  }else{
	                      $("#closedRequirementsCount").html("(0)");
	                  }
	        	  
		        	getrequirementStagePieChat(newRequirementCount,draftRequirementCount,submittedRequirementCount,openRequirementCount,onHoldRequirementCount,cancelRequirementCount,filledclosedRequirementsCount,unfilledclosedRequirementsCount,rejectedRequirementsCount,closedRequirementsCount);
	         /*  },
	          
	     }); */ 
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
      <h2 class="pull-left">Job(s) Stage</h2>
		
    </div>
    <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&nbsp;&raquo;&nbsp;<a href="<c:url value='${appPath}/reportsDashboard'/>">Reports Dashboard</a> &raquo; <a href="<c:url value='${appPath}/performanceReportList'/>">Performance Reports</a> &raquo; Job(s) Stage</div>
  </section>
  
    <div class="vendor_wrap">
     
     
     
<div class="reportHead clearfix">
	<div class="listGridWrap reqListBtn">
		<a id="listView" class="btn btn-default rAct" title="Grid List"><i class="fa fa-list"></i></a>
		<a id="gridView" class="btn btn-default" onclick="getRequirementStageCounts();" title="Pie chart"><i class="fa fa-pie-chart"></i></a>	
	</div>
	
    	<ul class="clearfix pull-right" id="statusDiv">
	        <li class="">
	        	<span id="filter2ApprovePop" class="filRep"><i class="fa fa-filter"></i></span>
	        	<div id="filter2ApprovedPop" class="approved_pop">
			              <ul>
	                		    <li><input type="checkbox" name="requirementStatus"  value ="NEW"  onclick="getRequirementList();">New</li>
								<li><input type="checkbox" name="requirementStatus"  value ="DRAFT"  onclick="getRequirementList();">Draft</li>
								<li><input type="checkbox" name="requirementStatus"  value ="SUBMITTED"  onclick="getRequirementList();">Submitted</li>
	               				<li><input type="checkbox" name="requirementStatus"  value ="OPEN"  onclick="getRequirementList();">Open</li>
	               				<li><input type="checkbox" name="requirementStatus"  value ="ONHOLD"  onclick="getRequirementList();">On Hold</li>
	               				<li><input type="checkbox" name="requirementStatus"  value ="CANCEL"  onclick="getRequirementList();">Cancel</li>
	               				<li><input type="checkbox" name="requirementStatus"  value ="FILLEDCLOSED"  onclick="getRequirementList();">Filled Closed</li>
	               				<li><input type="checkbox" name="requirementStatus"  value ="UNFILLEDCLOSED"  onclick="getRequirementList();">Unfilled Closed</li>
	               				<li><input type="checkbox" name="requirementStatus"  value ="REJECTED"  onclick="getRequirementList();">Rejected</li>
	               				<li><input type="checkbox" name="requirementStatus"  value ="CLOSED"  onclick="getRequirementList();">Closed</li>
	               				
			              </ul>
			    </div>
	        </li>    	
    		<li class="dataTabPdf">&nbsp;</li> 
    		<li class=""><input type="text" class="repSearchBar" placeholder='Search' id='reqStagesReportsTableSearch'/><span class="repSearchIcon" onClick="getRequirementList();"></span></li>
    	</ul>
    </div>      
  
 <div class="repTab1">  
      
	<div class="reportsCount reportsCountNew reportsCount_New">
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
			<li title="CLOSED" class="colorTwo">
				<span><i class="fa fa-window-close"></i></span><b id="closedRequirementsCount"></b>
			</li>
					<div class="clearfix"></div>
		</ul>
	</div>

  <div class="project_tabs_inn_table"> 
  <div id="reqStagesLoderDiv"></div>
                 <div id="requirementReportsDiv12" class="repDataSearch"></div>
                </div>      
</div>

 <div class="repTab2">
	  	<div class="chatOne" id="chartdiva"></div>	
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
 //  event.preventDefault(); commnted for the script issue in console.
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
	$("#gridView").click(function(){
		$("#listView").removeClass("rAct");
		$("#gridView").addClass("rAct");
		$(".repTab1").hide();
		$(".repTab2").show();
		$("#statusDiv").css("display", "none");
	});
	$("#listView").click(function(){
		$("#gridView").removeClass("rAct");
		$("#listView").addClass("rAct");
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
function getrequirementStagePieChat(newCount,draftCount,submittedCount,openCount,onHoldCount,cancelCount,filledclosedCount,unfilledclosedCount,rejectedCount,closedCount){
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
		  },{
			  "Stage": "Closed ("+closedCount+")",
			  "value": closedCount,
			  "color":"#C62828",
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
<jsp:include page="../masterFooter.jsp" />