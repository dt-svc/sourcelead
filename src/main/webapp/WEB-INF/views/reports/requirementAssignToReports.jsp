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
	getAllReports();
	}
</script>

<script type="text/javascript">
function getAllReports(){
	getAllAssignedRequirementsReports();
}


</script>


<script>
function getAllAssignedRequirementsReports(){
		$.ajax({
	         type : "POST",
	          url: sourceleadUrl+"/getAllAssignedRequirementsReports?"+tokenParamName+"="+tokenValue+"",    
	          success :function(data) {
	        	 prepareReportDataDiv(data);
	        	 getAllRequirementAssignToCounts();
	         },
	          
	     }); 
}
</script>
<script>
function prepareReportDataDiv(data){
	var result = '';
	result = result +  '<table id="requirementReportPagination" class="display" cellspacing="0" width="100%" >';
	result = result + '	<thead>';
	result = result + '   <tr>';
	result = result + '    <th>Job Title</th>';
	result = result + '    <th>Posted on</th>';
	result = result + '    <th>Client</th>';
	result = result + '    <th>Owner</th>';
	result = result + '    <th>Assigned To</th>';
	result = result + '    <th>Assigned On</th>';
	result = result + '    <th>Team Name</th>';
// 	result = result + '    <th>Offered</th>';
	result = result + '   </tr>';
	result = result + '	</thead>';
	result = result + '	<tbody>';
	var json = JSON.parse(data);
    for (var i = 0; i < json.length; i++) {
    	
	var report = json[i];
			result = result + '<tr>';
			result = result + '<td>';
			result = result + '<div><span title="'+report.jobTitle+'">'+report.jobTitle+'</span></div> ';
			result = result + '</td>';

			result = result + '<td>';
			if (report.postedOn != null && report.postedOn != ''&& report.postedOn != 'undefined') {
				result = result + '<div><span>'+report.postedOn+'</span></div>';
			} else {
				result = result + '<div><span>---</span></div>';
			}
			result = result + '</td>';
			result = result + '<td>';
			if (report.client != null && report.client != ''&& report.client != 'undefined') {
				result = result + '<div><span title="'+report.client+'">'+report.client+'</span></div>';
			} else {
				result = result + '<div><span>---</span></div>';
			}
			result = result + '</td>';
			result = result + '<td>';
			if (report.owner != null && report.owner != ''&& report.owner != 'undefined') {
				result = result + '<div><span title="'+report.owner+'">'+report.owner+'</span></div>';
			} else {
				result = result + '<div><span>---</span></div>';
			}
			result = result + '</td>';

			result = result + '<td>';
			if (report.assignedUser != null && report.assignedUser != ''&& report.assignedUser != 'undefined') {
				result = result + '<div><span title="'+report.assignedUser+'">'+report.assignedUser+'</span></div>';
			} else {
				result = result + '<div><span>---</span></div>';
			}
			result = result + '</td>';

			result = result + '<td>';
			if (report.assignedOn != null && report.assignedOn != '' && report.assignedOn != 'undefined') {
				result = result + '<div><span>' + report.assignedOn
						+ '</span></div>';
			} else {
				result = result + '<div><span>---</span></div>';
			}
			result = result + '</td>';

			result = result + '<td>';
			if (report.teamName != null && report.teamName != '' && report.teamName != 'undefined') {
				result = result + '<div><span title="'+report.teamName+'">'+report.teamName+'</span></div>';
			} else {
				result = result + '<div><span>---</span></div>';
			}
			result = result + '</td>';

			/* result = result + '<td>';
			if (report.offered != null && report.offered != ''
					&& report.offered != 'undefined') {
				result = result + '<div><span>' + report.offered
						+ '</span></div>';
			} else {
				result = result + '<div><span>---</span></div>';
			}
			result = result + '</td>'; */

			result = result + '</tr>';
		}
		result = result + '	</tbody>';
		result = result + '</table>';
		$("#assignedRequirementReportsDiv").empty();
		$("#assignedRequirementReportsDiv").append(result);
		requirementReportPagination();
		//getAllCounts();
	}
</script>
<script>
function requirementReportPagination(){
	$('#requirementReportPagination').DataTable({ 
		 dom: 'Bfrtip',
	        buttons: [
	        	{
	                extend: 'excel',
	                title: 'Job Assign To',
	            },
	            {
	                extend: 'pdf',
	                title: 'Job Assign To',
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
		$('#ApplicantStartDate').datepicker({
			changeMonth: true,
			changeYear: true, 
			dateFormat : 'dd-mm-yy',
			onSelect : function(selected) {
				var dt = new Date(selected);
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
				dt.setDate(dt.getDate() - 1);
				$("#ApplicantStartDate").datepicker("option", "maxDate", dt);
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
			<a title="Reports"><i class="fa fa-pie-chart"></i>Job Assign To</a>
			<div class="RePop">
				<ul>
					<li><a href="<c:url value='${appPath}/requirementListForReports'/>"><span><i class="fa fa-briefcase"></i></span>Jobs Posted</a></li>
					<li><a href="<c:url value='${appPath}/requirementStagesListForReports'/>"><span><i class="fa fa-briefcase"></i></span>Jobs Stages</a></li>
					<li><a href="<c:url value='${appPath}/applicantsReceivedListForReports'/>"><span><i class="fa fa-briefcase"></i></span>Applicants Received</a></li>
				    <li><a href="<c:url value='${appPath}/applicantsListForReports'/>"><span><i class="fa fa-briefcase"></i></span>Applicants</a></li>
				    <li><a href="<c:url value='${appPath}/applicantsSubmittedListForReports'/>"><span><i class="fa fa-briefcase"></i></span>Applicants Submitted</a></li>
				    <li><a href="<c:url value='${appPath}/requirementAssignToReports'/>"><span><i class="fa fa-briefcase"></i></span>Job Assign To</a></li>
				    <li><a href="<c:url value='${appPath}/candidateListForReports'/>"><span><i class="fa fa-briefcase"></i></span>Added Candidates</a></li>
				    <li><a href="<c:url value='${appPath}/timeSheetReports'/>"><span><i class="fa fa-briefcase"></i></span>TimeSheet Reports</a></li>
				</ul>
			</div>
		</div>
    </div>
        <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/reportsDashboard'/>">Reports Dashboard</a> &raquo; Job Assigned Reports </div>
  </section>
  <div class="timesheet_out">
    <div class="vendor_wrap">
     
      
<form:form id="assign_requirement_form" name="assign_requirement_form" method="get">     
   <div class="timesheet_head">
      <div class="pull-left reportBtns">
	  <div class="reportBtn dropdown_select">
		<select class="form-control" id="reportTypeId" name="reportType" onChange="custDates(); flushCustomDates();">
		 <option value="">-- Select --</option>
		  <option value="today">Today</option>
		  <option value="yesterday">Yesterday</option>
		  <option value="week">1 Week</option>
		  <option value="twoweeks">2 Weeks</option>
		  <option value="month">1 Month</option>
		  <option value="twomonths">2 Months</option>
		   <option value="custom">Custom</option> 
		</select>
	 </div>	
	 <span class="dashboard-datepkr cusShow">
	 <input type="text" class="form-control" value="" id="ApplicantStartDate" name="StartDate" field-name="Start Date" readonly="readonly" placeholder="Start Date"> <i class="fa fa-calendar"></i>
	<!--  <p id="ApplicantReportStartDate" class="ReError"></p> -->
	 </span>
	<span class="dashboard-datepkr cusShow">
	<input type="text"  class="form-control" value="" id="ApplicantEndDate" name="EndDate" field-name="End Date" readonly="readonly" placeholder="End Date"><i class="fa fa-calendar"></i>
	<!-- <p id="ApplicantReportStartDate" class="ReError"></p> -->
	</span>
	<!-- <span class="cusShow"><input type="button" value="Submit" class="btn btn-primary repBtn" onclick="getAssignedRequirement();" /></span> -->
	
</div>
                     
        <ul style="padding: 2px 0px 2px 0px;">

          <div class="clearfix"></div>
        </ul>
        <div class="clearfix"></div>
      </div>
      
      
      <div class="reportA">
          <label>Assign To</label>
          <div class="reportAChoo">
           <select multiple data-placeholder="Select Member" class="chosen-select" multiple="true"  name="user" id="user" >
                <c:forEach var="user" items="${userList}">
	                     <%-- <option value="${user.id}" ${user.id  == SourceLeadWeb_userId ? 'selected' : ''} >${user.firstName} ${user.lastName}</option> --%>
            			  <option value="${user.id}">${user.firstName} ${user.lastName}</option>
                </c:forEach>
                </select>
            <!-- <select class="form-control chosen-select" multiple="true" id="edu">
            <option value="1">Rajashekar</option>
            <option value="2">Naidu</option>
            <option value="3">Vannam</option>
            <option value="4">Snigitha</option>
            </select> -->
          </div>
          
          <input type="button" value="Submit" class="btn btn-primary rePBT"  id="assigendReq"  onclick="getAssignedRequirement();"/>
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
                 <div id="assignedRequirementReportsDiv" class="repoPositiAs"></div>
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
 <script type="text/javascript">
      var validationSettings = {
          errorMessagePosition : 'element',
          borderColorOnError : '',
		  scrollToTopOnError : true,
		  dateFormat : 'yyyy/mm/dd' 
          };
</script> 
<script type="text/javascript">
function getAssignedRequirement(){
	if ($('#assign_requirement_form').validate(false, validationSettings)) {
		var frm = $('#assign_requirement_form').serializeFormJSON();
		var con = JSON.stringify(frm);
		con = con.replace(/[{}]/g, "");
		  //It will escape all the special characters
        var jsonData = encodeURIComponent(con);
		/* $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;'); */
 		 $.ajax({
					type : "GET",
					url: sourceleadUrl+"/getDayWiseAssignedReqReports?"+tokenParamName+"="+tokenValue+"&json="+jsonData,
					success : function(result) {
						prepareReportDataDiv(result);
						getAllRequirementAssignToCounts(jsonData);
						reset();
					},
				});   
		 return true;
	 } else {
		return false;
	} 
}

$('body').on('blur', '#assign_requirement_form', function() {
	$("#assign_requirement_form").showHelpOnFocus();
	$("#assign_requirement_form").validateOnBlur(false, validationSettings);
});
</script>
 		
 		
<script>
function reset(){
	$(".chosen-select").val('').trigger("chosen:updated");
}

</script> 		
 		
<script>
function getAllRequirementAssignToCounts(jsonData){
	$.ajax({
        type : "GET",
        url: sourceleadUrl+"/getAllRequirementAssignToCounts?"+tokenParamName+"="+tokenValue+"&json="+jsonData,
         success :function(data) {
        	 var json = JSON.parse(data);
        	 $("#applicantCount").html(json.applicantsCount); 
       		 $("#shortlistedCount").html(json.shortlistedCount);
       	 	 $("#submitToClientCount").html(json.submitToClientCount);
       		 $("#interviewCount").html(json.interviewCount);
       		 $("#offerCount").html(json.offeredCount); 
          	 $("#rejectedCount").html(json.rejectedCount); 
        },
         
    });	
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
		$("assign_requirement_form").validate().resetForm();
		$('#ApplicantStartDate').val('');
		$('#ApplicantEndDate').val('');
		$.datepicker._clearDate('#ApplicantStartDate');
		$.datepicker._clearDate('#ApplicantEndDate');
	}
	</script>



<script>
function custDates(){
	var reportType = $("#reportTypeId").val();
	if(reportType=="custom"){
	    $(".cusShow").show();
		$('#ApplicantStartDate').removeAttr('data-validation');
		$('#ApplicantEndDate').removeAttr('data-validation'); 
		$('#ApplicantStartDate').removeAttr('data-validation-optional');
		$('#ApplicantEndDate').removeAttr('data-validation-optional');
	    $('#ApplicantStartDate').attr('data-validation', 'required');
		$('#ApplicantEndDate').attr('data-validation','required'); 
	}
	if(reportType!="custom"){
		$('#ApplicantStartDate').removeAttr('data-validation');
		$('#ApplicantEndDate').removeAttr('data-validation'); 
		$('#ApplicantStartDate').removeAttr('data-validation-optional');
		$('#ApplicantEndDate').removeAttr('data-validation-optional');
		$('#ApplicantStartDate').attr('data-validation-optional', 'true');
		$('#ApplicantEndDate').attr('data-validation-optional','true'); 
	    $(".cusShow").hide();
	//	flushCustomDates();
	}
}
</script>

<!-- <script>
function getCustomReports(){
	var reportType = $("#reportTypeId").val();
	var startDate = $("#ApplicantStartDate").val();
	var endDate = $("#ApplicantEndDate").val();
	var sourceleadUrl ='${sourceleadUrl}';
	if(startDate == null || startDate == '' || startDate == 'undefined'){
		$("#ApplicantReportStartDate").empty().append("Start Date is Required") ; 
	}
	if(endDate == null || endDate == '' || endDate == 'undefined'){
		$("#ApplicantReportStartDate").empty().append("End Date is Required") ; 
	}
	else{
		$.ajax({
	         type : "POST",
	         /*  url: sourceleadUrl+"/getRequirementReports?${_csrf.parameterName}=${_csrf.token}&reportType="+reportType+"&startDate="+startDate+"&endDate="+endDate, */  
	          url: sourceleadUrl+"/getApplicantsReport?${_csrf.parameterName}=${_csrf.token}&reportType="+reportType+"&startDate="+startDate+"&endDate="+endDate,
	          success :function(data) {
	        	 prepareReportDataDiv(data);
	        	// getAllCounts(reportType,startDate,endDate);
	         },
	          
	     }); 
	}
	 
}
</script> -->
<script type="text/javascript"> 
	function addValidClass(){
		 var StartDate = document.getElementById("ApplicantStartDate").value;
		 if(StartDate){
			 $('#ApplicantReportStartDate').empty() 
		 }
		 
		 var EndDate = document.getElementById("ApplicantEndDate").value;
		 if(EndDate){
			 $('#ApplicantReportStartDate').empty(); 
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
