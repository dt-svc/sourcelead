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
	var SourceLeadWeb_loginOrg= '${SourceLeadWeb_loginOrg}';
	var result = '';
	result = result +  '<table id="requirementReportPagination" class="display" cellspacing="0" width="100%" >';
	result = result + '	<thead>';
	result = result + '   <tr>';
	result = result + '    <th>Job Title</th>';
	result = result + '    <th>Applicant Name</th>';
	result = result + '    <th>Applicants</th>';
// 	result = result + '    <th>Request Sent for Approval</th>';
	result = result + '    <th>Shortlisted</th>';
	result = result + '    <th>Submit to client</th>';
	result = result + '    <th>Interviewed</th>';
	result = result + '    <th>Offered</th>';
	result = result + '    <th>Rejected</th>';
	result = result + '    <th>Backout</th>';
	result = result + '    <th>Status</th>';
	result = result + '   </tr>';
	result = result + '	</thead>';
	result = result + '	<tbody>';
	var json = JSON.parse(data);
    for (var i = 0; i < json.length; i++) {
    	var report= json[i];
    	result = result + '<tr>';
        result = result + '<td>';
        result = result + '<div><span title="'+report.requirementTitle+'">'+report.requirementTitle+'</span></div> ';
        result = result + '</td>';

        result = result + '<td>';
        if(report.applicantName != null && report.applicantName != '' && report.applicantName !='undefined'){
       	result = result + '<div><span title="'+report.applicantName+'">'+report.applicantName+'</span></div>';
 		} else{
		result = result + '<div><span>---</span></div>';
		    }
        result = result + '</td>';
        
        result = result + '<td>';
        if(report.submissionDate != null && report.submissionDate != '' && report.submissionDate !='undefined'){
         result = result + '<div class="tbldata-dotdot-100">'+report.submissionDate+'</div>';
        }
        else{
		 result = result + '<div><span>---</span></div>';
		    }
         result = result + '</td>';
         
       	 /* result = result + '<td>';
       	if(report.requestSentDate != null && report.requestSentDate != '' && report.requestSentDate !='undefined'){
       	 result = result + "<div><span>"+report.requestSentDate+"</span></div>";
       	}else{
   		 result = result + '<div><span>---</span></div>';
	    }
         result = result + '</td>'; */
         
         result = result + '<td>';
     	if(report.shortlistDate != null && report.shortlistDate != '' && report.shortlistDate !='undefined'){
         result = result + "<div class='tbldata-dotdot-100'>"+report.shortlistDate+"</div>";
     	}else{
   		 result = result + '<div><span>---</span></div>';
	    }
         result = result + '</td>';
         
         result = result + '<td>';
     	if(report.submitToClientDate != null && report.submitToClientDate != '' && report.submitToClientDate !='undefined'){
       	 result = result + "<div class='tbldata-dotdot-100'>"+report.submitToClientDate+"</div>";
     	}else{
   		 result = result + '<div><span>---</span></div>';
	    }
         result = result + '</td>';
         
         result = result + '<td>';
         if(report.interviewDate != null && report.interviewDate != '' && report.interviewDate !='undefined'){
         result = result + "<div class='tbldata-dotdot-100'>"+report.interviewDate+"</div> ";
         }else{
       		 result = result + '<div><span>---</span></div>';
 	    }
         result = result + '</td>';
         
         
         result = result + '<td>';
         if(report.offerDate != null && report.offerDate != '' && report.offerDate !='undefined'){
         result = result + "<div class='tbldata-dotdot-100'>"+report.offerDate+"</div> ";
         }else{
       		 result = result + '<div><span>---</span></div>';
  	    }
         result = result + '</td>';
         
         result = result + '<td>';
         if(report.rejectedDate != null && report.rejectedDate != '' && report.rejectedDate !='undefined'){
       	 result = result + "<div class='tbldata-dotdot-100'>"+report.rejectedDate+"</div>";
         }else{
       		 result = result + '<div><span>---</span></div>';
   	    }
         
         result = result + '<td>';
         if(report.backedOutDate != null && report.backedOutDate != '' && report.backedOutDate !='undefined'){
       	 result = result + "<div class='tbldata-dotdot-100'>"+report.backedOutDate+"</div>";
         }else{
       		 result = result + '<div><span>---</span></div>';
   	    }
         
         result = result + '</td>';
         
         result = result + '<td>';
         if(report.stage != null && report.stage != '' && report.stage !='undefined'){
       	 result = result + "<div><span>"+report.stage+"</span></div>";
         }else{
       		 result = result + '<div><span>---</span></div>';
   	    }
         result = result + '</td>';
         result = result + '</tr>';
    }
	result = result + '	</tbody>';
	result = result + '</table>';
	  $("#requirementReportsDiv12").empty();
    $("#requirementReportsDiv12").append(result);
    requirementReportPagination(SourceLeadWeb_loginOrg);
    //getAllCounts();
}

</script>
<script>
function requirementReportPagination(SourceLeadWeb_loginOrg){
	$('#requirementReportPagination').DataTable({ 
		 dom: 'Bfrtip',
	        buttons: [
	        	{
	                extend: 'excel',
	                title: SourceLeadWeb_loginOrg+' - Applicants Reports',
	            },
	            {
	                extend: 'pdf',
	                title: SourceLeadWeb_loginOrg+' - Applicants Reports',
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
		$('#ApplicantStartDate').datepicker({
			changeMonth: true,
			changeYear: true, 
			dateFormat : 'dd-mm-yy',
			onSelect : function(selected) {
				var dt = new Date(selected);
				var dt = toDate("#ApplicantStartDate");
				dt.setDate(dt.getDate() + 1);
				$("#ApplicantEndDate").datepicker("option", "minDate", dt);
				addValidClass();
			}
		
		});
		
		$('#ApplicantEndDate').datepicker({
			changeMonth: true,
			changeYear: true, 
			dateFormat : 'dd-mm-yy',
			beforeShow: function(){
		           this.value='';
		     },
			onSelect : function(selected) {
				var dt = new Date(selected);
				var dt = toDate("#ApplicantEndDate");
				dt.setDate(dt.getDate() - 1);
				$("#ApplicantStartDate").datepicker("option", "maxDate", dt);
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
      <h2 class="pull-left">Reports</h2>
		<div class=" pull-left reportsH">
			<a title="Reports"><i class="fa fa-pie-chart"></i>Applicants</a>
			<div class="RePop">
				<ul>
					<li><a href="<c:url value='${appPath}/requirementListForReports'/>"><span><i class="fa fa-briefcase"></i></span>Jobs Posted</a></li>
					<li><a href="<c:url value='${appPath}/requirementStagesListForReports'/>"><span><i class="fa fa-briefcase"></i></span>Jobs Stages</a></li>
<%-- 					<li><a href="<c:url value='/applicantsReceivedListForReports'/>"><span><i class="fa fa-briefcase"></i></span>Applicants Received</a></li> --%>
				    <li><a href="<c:url value='${appPath}/applicantsListForReports'/>"><span><i class="fa fa-briefcase"></i></span>Applicants</a></li>
				    <li><a href="<c:url value='${appPath}/applicantsSubmittedListForReports'/>"><span><i class="fa fa-briefcase"></i></span>Applicants Submitted</a></li>
<%-- 				    <li><a href="<c:url value='/requirementAssignToReports'/>"><span><i class="fa fa-briefcase"></i></span>Job Assign To</a></li> --%>
				    <li><a href="<c:url value='${appPath}/candidateListForReports'/>"><span><i class="fa fa-briefcase"></i></span>Added Candidates</a></li>
				    <li><a href="<c:url value='${appPath}/timeSheetReports'/>"><span><i class="fa fa-briefcase"></i></span>TimeSheet Reports</a></li>
				</ul>
			</div>
		</div>
    </div>
    <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&nbsp;&raquo;&nbsp;<a href="<c:url value='${appPath}/recruitersReports'/>">Reports</a>&nbsp;&raquo; Applicants</div>
  </section>
  <div class="timesheet_out">
    <div class="vendor_wrap overflow_Auto">
     
      <div class="timesheet_head">

<div class="pull-left reportBtns reportcutom_Btns">
	  <div class="reportBtn dropdown-select">
		<select class="form-control" id="reportTypeId" onChange="getDayWiseReports(); custDates(); flushCustomDates();">
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
	
<div class="cusShow pull-left">
	 <span class="dashboard-datepkr">
	 <input type="text" class="form-control" value="" id="ApplicantStartDate" name="StartDate" field-name="Start Date" data-validation="required" readonly="readonly" placeholder="Start Date"> <i class="fa fa-calendar"></i>
	 <p id="ApplicantReportStartDate" class="ReError"></p>
	 </span>
	<span class="dashboard-datepkr">
	<input type="text"  class="form-control" value="" id="ApplicantEndDate" name="EndDate" field-name="End Date" data-validation="required" readonly="readonly" placeholder="End Date"><i class="fa fa-calendar"></i>
	<p id="ApplicantReportEndDate" class="ReError"></p>
	</span>
	<span class="pull-left"><input type="button" value="Submit" class="btn btn-primary mt5" onclick="getCustomReports();" /></span>
</div>
    <div class="repoBtns pull-left">
		<a href="javascript:void(0)" class="oneA actRE" title="Grid List"><i class="fa fa-list"></i></a>
		<a href="javascript:void(0)" class="oneB" onclick="getDefaultChart();" title="Pie chart"><i class="fa fa-pie-chart"></i></a>
	</div>	
	
</div>
                     
      <!--   <ul style="padding: 2px 300px 2px 0px;" class="Pra11">
          <li><i class="fa fa-filter"></i><span class="padFil"></span><i class="glyphicon glyphicon-play" id="status-pop"></i>
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
      
	<div class="reportsCount reportsCount7">
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
				<span><img src="/resources/images/interview1.png" alt=""/></span><b id="interviewCount">0</b>
			</li>
			<li title="Offers" id="offermadeli">
				<span><i class="fa fa-tags"></i></span><b id="offerCount">0</b>
			</li>
			<li title="Rejected" id="rejectedli" class="colorTwo">
				<span><i class="fa fa-ban"></i></span><b id="rejectedCount">0</b>
			</li>
			<li title="Backout" id="backoutli">
				<span><i class="fa fa-arrow-circle-left"></i></span><b id="backoutCount">0</b>
			</li>
					<div class="clearfix"></div>
		</ul>
	</div>

  <div class="project_tabs_inn_table vendor_wrap_inn_table">
                 <div id="requirementReportsDiv12" class="repoPositi"></div>
                </div>   
                
</div>  

  <div class="repTab2">
  	<div class="chatOne" id="chartdiv"></div>  
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
		$('#ApplicantStartDate').val('');
		$('#ApplicantEndDate').val('');
		$.datepicker._clearDate('#ApplicantStartDate');
		$.datepicker._clearDate('#ApplicantEndDate');
	}
	</script>
<script>
function getDayWiseReports(){
	$('#ApplicantReportStartDate').empty();
	$('#ApplicantReportEndDate').empty(); 
	var reportType = $("#reportTypeId").val();
	var startDate = $("#ApplicantStartDate").val();
	var endDate = $("#ApplicantEndDate").val();
	/* if(reportType=="custom"){
		getCustomReports();
	} */
	if(reportType!="custom"){
	$.ajax({
         type : "POST",
         url: sourceleadUrl+"/getApplicantsReport?"+tokenParamName+"="+tokenValue+"&reportType="+reportType+"&startDate="+startDate+"&endDate="+endDate,     
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
	 $.ajax({
       type : "POST",
        url: sourceleadUrl+"/getApplicantsTotalCounts?"+tokenParamName+"="+tokenValue+"&reportType="+reportType+"&startDate="+startDate+"&endDate="+endDate,        
        success :function(data) { 
       	 var json = JSON.parse(data);
       	if($(".oneA" ).hasClass( "actRE" )){
       		$("#applicantCount").html(json.applicantsSum); 
       		 $("#shortlistedCount").html(json.shortlistedSum);
       	 	$("#submitToClientCount").html(json.submitToClientSum);
       		 $("#interviewCount").html(json.interviewsSum);
       		 $("#offerCount").html(json.offeredSum); 
          	$("#rejectedCount").html(json.rejectedSum); 
          	$("#backoutCount").html(json.backoutSum); 
		}
    	if($(".oneB" ).hasClass( "actRE" )){

     	getApplicantBarChat(json.applicantsSum,json.shortlistedSum,json.interviewsSum,json.submitToClientSum,json.offeredSum,json.rejectedSum,json.requestSentSum,json.backoutSum);

       	 
    	}
   	
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
	var startDate = $("#ApplicantStartDate").val();
	var endDate = $("#ApplicantEndDate").val();
	if(startDate == null || startDate == '' || startDate == 'undefined'){
		$("#ApplicantReportStartDate").empty().append("Start Date is Required") ; 
	}
	if(endDate == null || endDate == '' || endDate == 'undefined'){
		$("#ApplicantReportEndDate").empty().append("End Date is Required") ; 
	}
	else{
		$.ajax({
	         type : "POST",
	         /*  url: sourceleadUrl+"/getRequirementReports?${_csrf.parameterName}=${_csrf.token}&reportType="+reportType+"&startDate="+startDate+"&endDate="+endDate, */  
	          url: sourceleadUrl+"/getApplicantsReport?"+tokenParamName+"="+tokenValue+"&reportType="+reportType+"&startDate="+startDate+"&endDate="+endDate,
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
		 var StartDate = document.getElementById("ApplicantStartDate").value;
		 if(StartDate){
			 $('#ApplicantReportStartDate').empty() 
		 }
		 
		 var EndDate = document.getElementById("ApplicantEndDate").value;
		 if(EndDate){
			 $('#ApplicantReportEndDate').empty(); 
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
		$(".Pra11").css("padding","2px 0px 2px 0px");
	});
	$(".oneA").click(function(){
		$(".oneB").removeClass("actRE");
		$(".oneA").addClass("actRE");
		$(".repTab2").hide();
		$(".repTab1").show();
		$(".Pra11").css("padding","2px 300px 2px 0px");
	});		
	</script>

<%-- <script src="resources/reports/amcharts.js?staticTime=${staticTime}"></script>
<script src="resources/reports/pie.js?staticTime=${staticTime}"></script>
<script src="resources/reports/export.min.js?staticTime=${staticTime}"></script>
<link rel="stylesheet" href="resources/reports/export.cs?staticTime=${staticTime}" type="text/css" media="all" />
<script src="resources/reports/light.js?staticTime=${staticTime}"></script> --%>
  
     <script>
     function getApplicantBarChat(applicantsCount,shortlisted,interviewCount,submitToClientCount,offeredCount,rejectedCount,requestSentCount,backoutCount){
     var chart = AmCharts.makeChart("chartdiv", {
	     "type": "serial",
	     "theme": "light",
	     "marginRight": 70,
	     "dataProvider": [{
	     "Stage": "Applicants",
	     "StageCount": applicantsCount,
	     "color": "#FF0F00"
         },{
	     "Stage": "Request Sent",
	     "StageCount": requestSentCount,
	     "color": "#04D215"
	     },{
	     "Stage": "Shortlisted",
	     "StageCount": shortlisted,
	     "color": "#FF6600"
		  },{
	     "Stage": "Submit To Client",
	     "StageCount": submitToClientCount,
	     "color": "#FCD202"
		 }, {
	     "Stage": "Interview",
	     "StageCount": interviewCount,
	     "color": "#FF9E01"
	     },{
	     "Stage": "Offered",
	     "StageCount": offeredCount,
	     "color": "#F8FF01"
	     }, {
	     "Stage": "Rejected",
	     "StageCount": rejectedCount,
	     "color": "#B0DE09"
	
	     },{
	     "Stage": "Backout",
	     "StageCount": backoutCount,
	     "color": "#ffa726"
	
	     }],
	     "valueAxes": [{
	     "axisAlpha": 0,
	     "position": "left",
	     "title": "Applicants"
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
	     "enabled": false
	     }
	
	     });
     }
     </script> 


<script>
function getDefaultChart(){
	var reportType = $("#reportTypeId").val();
	if(reportType=="custom"){
		getCustomReports();
	}
	else{
		getDayWiseReports();
	}
}
</script>

