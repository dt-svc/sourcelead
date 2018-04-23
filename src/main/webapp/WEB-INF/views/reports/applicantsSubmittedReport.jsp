<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
 <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!-- ends files of export -->

<!-- Content Area -->
<script type="text/javascript">
window.onload = function() { 
	getApplicantSubmittedReports();
	}
</script>

<script type="text/javascript">
function getApplicantSubmittedReports(){
	getDayWiseReports();
}


</script>

<script>
function prepareReportDataDiv(data){
	var SourceLeadWeb_loginOrg= '${SourceLeadWeb_loginOrg}';
	 var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  reqListDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  reqListDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  reqListDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  reqListDate = 'YYYY-DD-MM'; 
	  }  
	
	var result = '';
	result = result +  '<table id="requirementReportPagination" class="display" cellspacing="0" width="100%" >';
	result = result + '	<thead>';
	result = result + '   <tr>';
	result = result + '    <th>Applicant Name</th>';
	result = result + '    <th>Client</th>';
	result = result + '    <th>Submitted By</th>';
	result = result + '    <th>Submitted on <br>(<small class="custom-small">'+reqListDate+'</small>)</th>';
	result = result + '    <th>Job Title</th>';
	result = result + '    <th>Applicant Status</th>';
	result = result + '   </tr>';
	result = result + '	</thead>';
	result = result + '	<tbody>';
	var json = JSON.parse(data);
    for (var i = 0; i < json.length; i++) {
    	var report= json[i];
    	result = result + '<tr>';
    	
    	 result = result + '<td>';
         if(report.candidateName != null && report.candidateName != '' && report.candidateName !='undefined'&& report.candidateName != null && report.applicantName != ''){
        		result = result + '<div><span title="'+report.candidateName+'">'+report.candidateName+'</span></div>';
  			} else{
 				result = result + '<div><span>---</span></div>';
 		    }
         result = result + '</td>';
         
         result = result + '<td>';
         if(report.orgName != null && report.orgName != '' && report.orgName !='undefined'&& report.orgName != null && report.orgName != ''){
         		result = result + '<div><span title="'+report.orgName+'">'+report.orgName+'</span></div>';
   			} else{
  				result = result + '<div><span>---</span></div>';
  		    }
         result = result + '</td>';
         
         result = result + '<td>';
         if(report.submittedBy != null && report.submittedBy != '' && report.submittedBy !='undefined'&& report.submittedBy != null && report.submittedBy != ''){
         		result = result + '<div><span title="'+report.submittedBy+'">'+report.submittedBy+'</span></div>';
   			} else{
  				result = result + '<div><span>---</span></div>';
  		    }
         result = result + '</td>';
         
         result = result + '<td>';
         if(report.createdDateStr != null && report.createdDateStr != '' && report.createdDateStr !='undefined'&& report.createdDateStr != null && report.createdDateStr != ''){
         		result = result + '<div><span>'+report.createdDateStr+'</span></div>';
   			} else{
  				result = result + '<div><span>---</span></div>';
  		    }
         result = result + '</td>';
         
        result = result + '<td>';
        result = result + '<div><span title="'+report.requirementName+'">'+report.requirementName+'</span></div> ';
        result = result + '</td>';

        result = result + '<td>';
        if(report.submissionStatus != null && report.submissionStatus != '' && report.submissionStatus !='undefined'&& report.submissionStatus != null && report.submissionStatus != ''){
       	result = result + '<div><span>'+report.submissionStatus+'</span></div>';
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
	                title: SourceLeadWeb_loginOrg+' - Submitted Applicants Reports',
	            },
	            {
	                extend: 'pdf',
	                title: SourceLeadWeb_loginOrg+' - Submitted Applicants Reports',
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
		  var finalDateFormatApplicant = '';
		  if(locationDate.match("yyyy-MM-dd")){
			  finalDateFormatApplicant = 'yy-mm-dd';
		  }if(locationDate.match("dd-MM-yyyy")){
			  finalDateFormatApplicant = 'dd-mm-yy'; 
		  }if(locationDate.match("MM-dd-yyyy")){
			  finalDateFormatApplicant = 'mm-dd-yy'; 
		  }if(locationDate.match("yyyy-dd-MM")){
			  finalDateFormatApplicant = 'yy-dd-mm'; 
		  }
		  
		$('#ExportStartDate').datepicker({
			dateFormat : finalDateFormatApplicant,
			changeMonth: true,
			changeYear: true, 
			onSelect : function(selected) {
				/* var dt = new Date(selected);
				dt.setDate(dt.getDate() + 1); */
				$("#ExportEndDate").datepicker("option", "minDate", selected);
				addValidClass();
			}
		
		});
		
		$('#ExportEndDate').datepicker({
			dateFormat : finalDateFormatApplicant,
			changeMonth: true,
			changeYear: true, 
			beforeShow: function(){
		           this.value='';
		     },
			onSelect : function(selected) {
			/* 	var dt = new Date(selected);
				dt.setDate(dt.getDate() - 1); */
				$("#ExportStartDate").datepicker("option", "maxDate", selected);
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
			<a title="Reports"><i class="fa fa-pie-chart"></i>Submitted Applicants</a>
			<div class="RePop">
				<ul>
					<li><a href="<c:url value='${appPath}/requirementListForReports'/>"><span><i class="fa fa-briefcase"></i></span>Jobs Posted</a></li>
					<li><a href="<c:url value='${appPath}/requirementStagesListForReports'/>"><span><i class="fa fa-briefcase"></i></span>Jobs Stages</a></li>
<%-- 					<li><a href="<c:url value='/applicantsReceivedListForReports'/>"><span><i class="fa fa-briefcase"></i></span>Applicants Received</a></li> --%>
<%-- 				    <li><a href="<c:url value='/applicantsListForReports'/>"><span><i class="fa fa-briefcase"></i></span>Applicants</a></li> --%>
				    <li><a href="<c:url value='${appPath}/applicantsSubmittedListForReports'/>"><span><i class="fa fa-briefcase"></i></span>Submitted Applicants</a></li>
<%-- 				    <li><a href="<c:url value='/requirementAssignToReports'/>"><span><i class="fa fa-briefcase"></i></span>Job Assign To</a></li> --%>
				    <li><a href="<c:url value='${appPath}/candidateListForReports'/>"><span><i class="fa fa-briefcase"></i></span>Added Candidates</a></li>
				    <li><a href="<c:url value='${appPath}/timeSheetReports'/>"><span><i class="fa fa-briefcase"></i></span>TimeSheet Reports</a></li>
				</ul>
			</div>
		</div>
    </div>
    <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&nbsp;&raquo;&nbsp;<a href="<c:url value='${appPath}/recruitersReports'/>">Reports</a>&nbsp;&raquo; Submitted Applicants</div>
  </section>
  <div class="timesheet_out">
    <div class="vendor_wrap">
     
      <div class="timesheet_head">

<div class="pull-left reportBtns reportcutom_Btns">
	  <div class="reportBtn dropdown-select">
		<select class="form-control" id="reportTypeId" onChange="getDayWiseReports(); custDates(); flushCustomDates();">
<!-- 		 <option value="">-- select --</option> -->
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
</div>
                    
        <div class="clearfix"></div>
      </div>
    
    <form:form id="submittoClient_form" name="submittoClient_form" method="get">  
        <div class="reportA">
          <label>Clients</label>
          <div class="reportAChoo">
           <select multiple  class="chosen-select" multiple="true"  name="client" id="client" data-placeholder="Select Client">
                <c:forEach var="report" items="${add_clientList}">
	                             <c:if test="${report.locationId.id == loginOrgLocId}">  
	                                <option id="clientName${report.id}" name="clientNameById" value ="${report.id}"">${report.responderLocationId.org.orgName}</option>
			                    </c:if>  
			                    <c:if test="${report.responderLocationId.id == loginOrgLocId}">
			                    	<option id="clientName${report.id}" name="clientNameById" value ="${report.id}"">${report.responderLocationId.org.orgName}</option> 
			                    </c:if>
                </c:forEach>
                </select>
          </div>
          
          <input type="button" value="Submit" class="btn btn-primary rePBT"  id="assigendReq"  onclick="getApplicantSubmittedReports();"/>
			<div class="clearfix"></div>		
		</div>    
    </form:form>
    
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
				<span><img src="/resources/images/interview1.png" alt=""/></span><b id="interviewCount">0</b>
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
                 <div id="requirementReportsDiv12" class="repoPositiAs"></div>
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
	
	var frm = $('#submittoClient_form').serializeFormJSON();
	var con = JSON.stringify(frm);
	con = con.replace(/[{}]/g, "");
	  //It will escape all the special characters
    var jsonData = encodeURIComponent(con);
	$('#reportStartDate').empty();
	$('#reportEndDate').empty(); 
	var reportType = $("#reportTypeId").val();
	var startDate = $("#ExportStartDate").val();
	var endDate = $("#ExportEndDate").val();
	if(reportType!="custom"){
	$.ajax({
         type : "POST",
         url: sourceleadUrl+"/getAllSubmittedApplicantsForReports?"+tokenParamName+"="+tokenValue+"&reportType="+reportType+"&startDate="+startDate+"&endDate="+endDate+"&jsonData="+jsonData,     
          success :function(data) {
        	 prepareReportDataDiv(data);
        	 var json = JSON.parse(data);
        	 getAllCounts(jsonData,reportType,startDate,endDate);
         },
          
     });  
	}
	
}

</script>

<script>
function getAllCounts(jsonData,reportType,startDate,endDate){
	// $('#applicantsli').load(allReceivedApplicantsCount(applicantConts)); 
	 $.ajax({
      type : "POST",
      url: sourceleadUrl+"/getSubmittedApplicantsCountForReports?"+tokenParamName+"="+tokenValue+"&reportType="+reportType+"&startDate="+startDate+"&endDate="+endDate+"&jsonData="+jsonData,  
      //url: sourceleadUrl+"/getReceivedApplicantsCountForReports?${_csrf.parameterName}=${_csrf.token}",        
       success :function(data) { 
      	 var json = JSON.parse(data);
      	 $("#applicantCount").html(json.applicantsSum); 
         $("#shortlistedCount").html(json.shortlistedSum); 
  	     $("#submitToClientCount").html(json.submitToClientSum);
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
	          url: sourceleadUrl+"/getAllSubmittedApplicantsForReports?"+tokenParamName+"="+tokenValue+"&reportType="+reportType+"&startDate="+startDate+"&endDate="+endDate,
	          success :function(data) {
	        	 prepareReportDataDiv(data);
	        	 var json = JSON.parse(data);
	        	 getAllCounts(json.length,reportType,startDate,endDate);
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