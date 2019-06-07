<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />


<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<link href="/resources/css/jquery-tagit.css" rel="stylesheet">
<script src="/resources/js/csc.js" type="text/javascript" charset="utf-8"></script>
<script src="/resources/js/tag-it.js" type="text/javascript" charset="utf-8"></script>
<script src="/resources/js/tag-it.min.js" type="text/javascript" charset="utf-8"></script>
<!-- end files for autocomplete with tagit -->


<script type="text/javascript">
/* window.onload = function() { 
	getVendorsReportList();
	} */
</script>



<section class="content-wraper">

<section class="content-header clearfix">
			<div class="pull-left">
				<h2>Vendor(s) Report</h2>
			</div>
        <div class="pull-right brud-crum"><a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/reportsDashboard'/>">Reports Dashboard</a> &raquo; <a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/performanceReportList'/>">Performance Reports</a> &raquo; Vendor(s) Report</div>
</section>


    <div class="positionRelative">
    
    
<div class="reportHead">
			<div class="listGridWrap reqListBtn">
				<a title="Graphical View" id="graphView" class="btn btn-default rAct"><i class="fa fa-pie-chart"></i></a> 
				<a title="List View" id="listView" class="btn btn-default"><i class="fa fa-list"></i></a>
			</div>
    	<ul class="clearfix pull-right" id="listViewFilters" style="display: none;">
    		<li>
        <div class="dropdown-select positionRelative repSelWrap clearfix">
			<select class="form-control repSelFiled" id="vendorreportTypeId" onChange="flushVendorCustomDates(); custVendorDates();">
			  	 <option value="Last_7_days">Last 7 days</option>
			  <option value="Last_15_days">Last 15 days</option>
			  <option value="1_month">Last 1 month</option>
			  <option value="3_months">Last 3 months</option>
			  <option value='YTD' >YTD</option>
	          <option value='Last_Year'>Last Year</option>
			  <option value="custom">Custom</option>
			</select>
		<div id="RunDivId" class="repSelBtn"> <button type="button" class="btn btn-primary btn-sm" id="customize_btn" onclick="getVendorsReportList();">Run</button></div> 
          
          <div class="clearfix customLeaves repCustomDate" id="dateShow">
			 <div class="repCustomDateInn">
			 <input type="text" class="form-control calendar" value="" id="vendorStartDate" name="StartDate" field-name="Start Date" data-validation="required" readonly="readonly" placeholder="Start Date">
			 <p id="vendorreportStartDate"></p>
			 </div>
			<div class="repCustomDateInn">
			<input type="text"  class="form-control calendar" value="" id="vendorEndDate" name="EndDate" field-name="End Date" data-validation="required" readonly="readonly" placeholder="End Date">
			<p id="vendorreportEndDate"></p>
			</div>
			<div class="repCustomDateBtn">
				<button type="button" class="btn btn-primary btn-sm" onclick="getVendorCustomReports();"><i class="fa fa-search"></i></button>
			</div>
          </div>
          
        </div>    		
    		</li>
    		<li class="unVisable"><input type="text" class="repSearchBar" placeholder='Search' id='usersDataTableSearch'/><span class="repSearchIcon" onclick=""></span></li>
    	</ul>
    </div>    
    
   
 
<section class="candidate_wrap_table recruiterReports_table minHeight">
<div id ="vendorLoderDiv"></div>
<div id="vendorReportsDiv" class="datatable_filter repDataSearch repDataSearchFil"></div>
<div id="vendorReportsChartDiv">
	<jsp:include page="vendorPerformanceReport.jsp" flush="true"></jsp:include>
</div>
<div id="vendorSpocReportsDiv" class="repoPositiNoCount"></div>
<div id="releasedJobsResultDiv"></div>
<div id="releasedApplicantsDeclineDiv"></div>
<div id="releasedApplicantsBackOutDiv"></div>
<div id="releasedApplicantsHiredListDiv"></div>
</section>   
 
      



  </div>
</section>
<script>
function getVendorsReportList(){
	$('#vendorreportStartDate').empty();
	$('#vendorreportEndDate').empty();
	document.getElementById("vendorreportStartDate").style.display="none";
	document.getElementById("vendorreportEndDate").style.display="none";
	var reportType = $("#vendorreportTypeId").val();
	var startDate = $("#vendorStartDate").val();
	var endDate = $("#vendorEndDate").val();
	if(reportType != "custom"){
	$("#vendorLoderDiv").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:999;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	$.ajax({
         type : "POST",
          url: sourceleadUrl+"/getVendorRequirementReports?"+tokenParamName+"="+tokenValue+"&reportType="+reportType+"&customStartDate="+startDate+"&customEndDate="+endDate,    
          success :function(data) {
        	 prepareReportDataDiv(data);
         },
          
     }); 
	}
}
</script>
<script>
function prepareReportDataDiv(data){
	var result = '';
	
	result = result +  '<table id="vendorReportPagination" class="display" cellspacing="0" width="100%" >';
	result = result + '	<thead class="parentHeader">';
	result = result + '   <tr>';
	result = result +"<th><i class='fa fa-suitcase fa-2x'></i> <div class='small'>Vendor Name</div></th>";
	result = result + "    <th><i class='fa fa-bullhorn fa-2x'></i> <div class='small'>Released</div></th>";
	result = result + "    <th><i class='fa fa-send fa-2x fa-2x'></i> <div class='small'>Submitted</div></th>";
	result = result + "    <th><i class='fa fa-list-ul fa-2x'></i> <div class='small'>Shortlisted</div></th>";
	result = result + "    <th><i class='fa fa-handshake-o fa-2x'></i> <div class='small'>Interviewed</div></th>";
	result = result + "    <th><i class='fa fa-envelope-open-o fa-2x'></i> <div class='small'>Offered</div></th>";
	result = result + "    <th><i class='fa fa-ban fa-2x'></i> <div class='small'>Rejected</div></th>";
	result = result + "    <th><i class='fa fa-arrow-circle-left fa-2x'></i> <div class='small'>Back-Out</div></th>";
	result = result + "   </tr>";
	result = result + '	</thead>';
	result = result + '	<tbody>';
	
	var json = JSON.parse(data);
    for (var i = 0; i < json.length; i++) {
    	var report= json[i];
    	result = result + '<tr>';
        result = result + '<td>';
        result = result + '<div onclick="getVendorSpocs('+report.id+');" class="tbldata-dotdot-me" title="'+report.vendorName+'"><a>'+report.vendorName+'</a></div> ';
        result = result + '</td>';
        result = result + '<td>';
        if(report.reqReleasedCount != null && report.reqReleasedCount != '' && report.reqReleasedCount !='undefined'){
       	result = result + '<div class="small"><span>'+report.reqReleasedCount+'</span></div>';
 		} else{
		result = result + '<div class="small"><span>0</span></div>';
		    }
        result = result + '</td>';
        result = result + '<td>';
        if(report.submittedCount != null && report.submittedCount != '' && report.submittedCount !='undefined'){
           	result = result + '<div class="small"><span>'+report.submittedCount+'</span></div>';
     		} else{
    		result = result + '<div class="small"><span>0</span></div>';
    		    }
        result = result + '</td>';
       	result = result + '<td>';
       	if(report.shortlistedCount != null && report.shortlistedCount != '' && report.shortlistedCount !='undefined'){
           	result = result + '<div class="small"><span>'+report.shortlistedCount+'</span></div>';
     		} else{
    		result = result + '<div class="small"><span>0</span></div>';
    		    }
       	result = result + '</td>';
        
         result = result + '<td>';
         if(report.interviewedCount != null && report.interviewedCount != '' && report.interviewedCount !='undefined'){
            	result = result + '<div class="small"><span>'+report.interviewedCount+'</span></div>';
      		} else{
     		result = result + '<div class="small"><span>0</span></div>';
     		    }
        result = result + '</td>';
        
        result = result + '<td>';
        if(report.offeredCount != null && report.offeredCount != '' && report.offeredCount !='undefined'){
           	result = result + '<div class="small"><span>'+report.offeredCount+'</span></div>';
     		} else{
    		result = result + '<div class="small"><span>0</span></div>';
    		    }       		 
        result = result + '</td>';
        
        result = result + '<td>';
        if(report.rejectedCount != null && report.rejectedCount != '' && report.rejectedCount !='undefined'){
           	result = result + '<div class="small"><span>'+report.rejectedCount+'</span></div>';
     		} else{
    		result = result + '<div class="small"><span>0</span></div>';
    		    }
        result = result + '</td>';
        
        result = result + '<td>';
        if(report.backoutCount != null && report.backoutCount != '' && report.backoutCount !='undefined'){
           	result = result + '<div class="small"><span>'+report.backoutCount+'</span></div>';
     		} else{
    		result = result + '<div class="small"><span>0</span></div>';
    		    }
        result = result + '</td>';
        
        result = result + '</tr>';
    }
	result = result + '	</tbody>';
	result = result + '</table>';
	$("#vendorReportsDiv").empty();
    $("#vendorReportsDiv").append(result);
    $("#vendorLoderDiv").empty();
    $("#vendorLoderDiv").removeAttr('style');
    vendorReportPagination();
}

</script>
<script>
function vendorReportPagination(){
	$('#vendorReportPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
}

</script>

<script>
function getVendorSpocs(relationId){
	var reportType = $("#vendorreportTypeId").val();
	var startDate = $("#vendorStartDate").val();
	var endDate = $("#vendorEndDate").val();
	$.ajax({
         type : "POST",
          url: sourceleadUrl+"/getVendorSpocsReports?"+tokenParamName+"="+tokenValue+"&reportType="+reportType+"&relationId="+relationId+"&customStartDate="+startDate+"&customEndDate="+endDate,    
          success :function(data) {
        	  prepareSpocReportDataDiv(data);
        	 /*  var result = "";
      		result = result + '	<div class="settPop departmentPop">';
      		result = result + '	<h3>Edit Department<i class="fa fa-times" onclick="editCancel();"></i></h3>  ';
      		result = result + '	<div class="settPopInn"> ';
      		result = result + '		<div class="form-group col-md-12"> ';
      		result = result + '		  <label class="col-md-4" for="Project Id">Department Name <span class="text-danger">*</span></label> ';
      		result = result + '		  <div class="col-md-8" id="eidtdepartmentNameDIV"> ';
      		result = result + '			<input type="text" class="form-control input-sm"  name="departmentName"   data-validation="required validate_length length2-100" field-name="Department Name" onkeyup="validateDepartmentname();"> ';
      		result = result + '		   </div>';
      		result = result + '		</div>  ';
      		
      		result = result + '		<div class="form-group col-md-12">  ';
      		result = result + '		  <label class="col-md-4" for="Project Id">Department description</label> ';
      		result = result + '		  <div class="col-md-8 taskdesp">   ';
      		result = result + '			<textarea class="form-control" name="departmentDesc" colspan="5"></textarea>';
      		result = result + '			<span class="hidden">Enter Project Id</span> </div>  ';
      		result = result + '		</div>   ';
      		
      		result = result + '<div id="vendodffrspc"></div>';
      		result = result + '		<div class="col-md-12 mt5 clearfix"> ';
      		result = result + '		<div class="pull-right">  ';
      		result = result + '		</div>';
      		result = result + '		</div>';
      		result = result + '		<div class="clearfix"></div>   ';
      		result = result + '	</div>  ';
      		result = result + '	</div>';
      		$("#vendorSpocReportsDiv").empty();
      	    $("#vendorSpocReportsDiv").append(result); */
      	    
      	$(".pavPop").fadeIn(200);
      	$(".mask_time").fadeIn(200);
          },
	 }); 
}
</script>

<script>
function pavClose(){
  	$(".pavPop").fadeOut(200);
  	$(".mask_time").fadeOut(200);
}
</script>

<script>
function prepareSpocReportDataDiv(data){
	
	$("#vendorspc").empty();
	  var result = '';
	
	result = result +  '<table id="vendorSpocReportPagination">';
	result = result + '	<thead>';
	result = result + '   <tr>';
	result = result + '    <th>Vendor Name</th>';
	result = result + '    <th>Released</th>';
	result = result + '    <th>Submitted</th>';
	result = result + '    <th>Shortlisted</th>';
	result = result + '    <th>Interviewed</th>';
	result = result + '    <th>Offered</th>';
	result = result + '    <th>Rejected</th>';
	result = result + '    <th>Back-Out</th>';
	result = result + '   </tr>';
	result = result + '	</thead>';
	result = result + '	<tbody>';
	var json = JSON.parse(data);
    for (var i = 0; i < json.length; i++) {
    	var report= json[i];
    	result = result + '<tr>';
        result = result + '<td>';
        result = result + '<div><span>'+report.spocName+'</span></div> ';
        result = result + '</td>';
        result = result + '<td>';
        if(report.reqReleasedCount != null && report.reqReleasedCount != '' && report.reqReleasedCount !='undefined'){
       	result = result + '<div><span>'+report.reqReleasedCount+'</span></div>';
 		} else{
		result = result + '<div><span>0</span></div>';
		    }
        result = result + '</td>';
        result = result + '<td>';
        if(report.submittedCount != null && report.submittedCount != '' && report.submittedCount !='undefined'){
           	result = result + '<div><span>'+report.submittedCount+'</span></div>';
     		} else{
    		result = result + '<div><span>0</span></div>';
    		    }
        result = result + '</td>';
       	result = result + '<td>';
       	if(report.shortlistedCount != null && report.shortlistedCount != '' && report.shortlistedCount !='undefined'){
           	result = result + '<div><span>'+report.shortlistedCount+'</span></div>';
     		} else{
    		result = result + '<div><span>0</span></div>';
    		    }
       	result = result + '</td>';
         result = result + '<td>';
         if(report.interviewedCount != null && report.interviewedCount != '' && report.interviewedCount !='undefined'){
            	result = result + '<div><span>'+report.interviewedCount+'</span></div>';
      		} else{
     		result = result + '<div><span>0</span></div>';
     		    }
        result = result + '</td>';
        result = result + '<td>';
        if(report.offeredCount != null && report.offeredCount != '' && report.offeredCount !='undefined'){
           	result = result + '<div><span>'+report.offeredCount+'</span></div>';
     		} else{
    		result = result + '<div><span>0</span></div>';
    		    }       		 
        result = result + '</td>';
        result = result + '<td>';
        if(report.rejectedCount != null && report.rejectedCount != '' && report.rejectedCount !='undefined'){
           	result = result + '<div><span>'+report.rejectedCount+'</span></div>';
     		} else{
    		result = result + '<div><span>0</span></div>';
    		    }
        result = result + '</td>';
        
        result = result + '<td>';
        if(report.backoutCount != null && report.backoutCount != '' && report.backoutCount !='undefined'){
           	result = result + '<div class="small"><span>'+report.backoutCount+'</span></div>';
     		} else{
    		result = result + '<div class="small"><span>0</span></div>';
    		    }
        result = result + '</td>';
        
        result = result + '</tr>';
    }
	result = result + '	</tbody>';
	result = result + '</table>';
    $("#vendorspc").append(result);
    
    vendorSpocReportPagination();
}

</script>
<script>
function editCancel(){
	$(".settPop").slideUp(200);
	$(".vendor_wrap_mask").fadeOut(200);
}
</script>

<script>
function vendorSpocReportPagination(){
	$('#vendorSpocReportPagination').DataTable({ 
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
}

</script>


<script>
	$(function() {
		var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
		  var finalDateFormatrepoVendor = '';
		  if(locationDate.match("yyyy-MM-dd")){
			  finalDateFormatrepoVendor = 'yy-mm-dd';
		  }if(locationDate.match("dd-MM-yyyy")){
			  finalDateFormatrepoVendor = 'dd-mm-yy'; 
		  }if(locationDate.match("MM-dd-yyyy")){
			  finalDateFormatrepoVendor = 'mm-dd-yy'; 
		  }if(locationDate.match("yyyy-dd-MM")){
			  finalDateFormatrepoVendor = 'yy-dd-mm'; 
		  }
		$('#vendorStartDate').datepicker({
			changeMonth: true,
			changeYear: true, 
			dateFormat : finalDateFormatrepoVendor,
			onSelect : function(selected) {
				var dt = new Date(selected);
				//var dt = toDate("#vendorStartDate");
				//dt.setDate(dt.getDate() + 1);
				$("#vendorEndDate").datepicker("option", "minDate", selected);
				addValidClass();
			}
		
		});
		
		$('#vendorEndDate').datepicker({
			changeMonth: true,
			changeYear: true, 
			dateFormat : finalDateFormatrepoVendor,
			beforeShow: function(){
		           this.value='';
		     },
			onSelect : function(selected) {
				var dt = new Date(selected);
				//var dt = toDate("#vendorEndDate");
				//dt.setDate(dt.getDate() - 1);
				$("#vendorStartDate").datepicker("option", "maxDate", selected);
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

<script>
    //Nav bar toggle
        $(function(){
	$('.accordionToggle .accordionLink').click(function(){
		if(!$(this).hasClass('accordion-on'))
		$('.accordionToggle .accordionLink').removeClass('accordion-on');
		$(this).toggleClass('accordion-on');
		$(this).next(".accordionPanel").slideToggle().siblings(".accordionPanel:visible").slideToggle();
	})
});
    </script>

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
 //   event.preventDefault(); commented the code not a valid palce to use.
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
	function flushVendorCustomDates() {
		$('#vendorStartDate').val('');
		$('#vendorEndDate').val('');
		$.datepicker._clearDate('#vendorStartDate');
		$.datepicker._clearDate('#vendorEndDate');
	}
	</script>
<script>
function custVendorDates(){
	var reportType = $("#vendorreportTypeId").val();
	if(reportType=="custom"){
// 	    $("#dateShow").style("display: block");
	    $("#dateShow").css("display", "block");
	    document.getElementById("RunDivId").style.display="none";
	}
	if(reportType!="custom"){
// 	    $("#dateShow").style("display: none");
	    $("#dateShow").css("display", "none");
	    document.getElementById("RunDivId").style.display="block";
	}
}
</script>

<script>
function getVendorCustomReports(){
	var reportType = $("#vendorreportTypeId").val();
	var startDate = $("#vendorStartDate").val();
	var endDate = $("#vendorEndDate").val();
	if(startDate == null || startDate == '' || startDate == 'undefined'){
		document.getElementById("vendorreportStartDate").style.display="block";
		$("#vendorreportStartDate").empty().append("Start Date is Required") ; 
	}
	if(endDate == null || endDate == '' || endDate == 'undefined'){
		document.getElementById("vendorreportEndDate").style.display="block";
		$("#vendorreportEndDate").empty().append("End Date is Required") ; 
	}
	else{
		$("#vendorLoderDiv").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:999;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
		$.ajax({
	         type : "POST",
	          url: sourceleadUrl+"/getVendorRequirementReports?"+tokenParamName+"="+tokenValue+"&reportType="+reportType+"&customStartDate="+startDate+"&customEndDate="+endDate,    
	          success :function(data) {
	        	  prepareReportDataDiv(data);
	         },
	          
	     }); 
	}
	 
}
</script>
<script type="text/javascript"> 
	function addValidClass(){
		 var StartDate = document.getElementById("vendorStartDate").value;
		 if(StartDate){
			 $('#vendorreportStartDate').empty() 
		 }
		 
		 var EndDate = document.getElementById("vendorEndDate").value;
		 if(EndDate){
			 $('#vendorreportEndDate').empty(); 
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
<jsp:include page="../masterFooter.jsp" />