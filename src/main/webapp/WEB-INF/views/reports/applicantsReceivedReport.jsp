<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
 <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script type="text/javascript">
window.onload = function() { 
	getDayWiseReports();
	}
</script>

<!-- <script type="text/javascript">
function getApplicantReceivedReports(){
	getDayWiseReports();
}


</script>
 -->
<script>
function prepareReportDataDiv(data){
	var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
	var appListDate = getOrgDateFormat(locationList);
	var result = '';
	result = result +  '<table id="requirementReportPagination" class="display" cellspacing="0" width="100%" >';
	result = result + '	<thead>';
	result = result + '   <tr>';
	result = result + '    <th>Applicant Name</th>';
	result = result + '    <th>Source</th>';
	result = result + '    <th>Received on <br>(<small class="custom-small">'+appListDate+'</small>)</th>';
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
    $("#applicantsLoderDiv").empty();
    $("#applicantsLoderDiv").removeAttr('style');
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
    	"bLengthChange": true,
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
function toDate(selector) {
    var from = $(selector).val().split("-");
    return new Date(from[2], from[1] - 1, from[0]);
}
</script>
<section class="content-wraper">

  <section class="content-header clearfix">
    <div class="pull-left"><h2 class="pull-left">Applicant(s) Received</h2> </div>
    <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/reportsDashboard'/>">Reports Dashboard</a> &raquo; <a href="<c:url value='${appPath}/performanceReportList'/>">Performance Reports</a> &raquo; Applicant(s) Received </div>
  </section>
  
    <div class="vendor_wrap clearfix">
          
<div class="reportHead clearfix">
	<div class="listGridWrap reqListBtn">
		<a id="listView" class="btn btn-default rAct" title="Grid List"><i class="fa fa-list"></i></a>
		<a id="gridView" class="btn btn-default" onclick="getDefaultChart()" title="Pie chart"><i class="fa fa-pie-chart"></i></a>	
	</div>
	
    	<ul class="clearfix pull-right">
    		
    		<li>
       <div class="dropdown-select positionRelative repSelWrap clearfix">
		<select class="form-control repSelFiled" id="reportTypeId" onChange="custDates();flushCustomDates();">
			  <option value="Last_7_days">Last 7 days</option>
			  <option value="Last_15_days">Last 15 days</option>
			  <option value="1_month">Last 1 month</option>
			  <option value="3_months">Last 3 months</option>
			  <option value='YTD' >YTD</option>
	          <option value='Last_Year'>Last Year</option>
			  <option value="custom">Custom</option>
		</select>          
          
		<div class="repSelBtn" id="RunApplicantDivId"><button type="button" class="btn btn-primary btn-sm" id="customize_btn" onclick="getDayWiseReports();">Run</button></div> 
          
            <!-- Custom Date -->
				 <div class="clearfix customLeaves repCustomDate">
					<div class="repCustomDateInn">
					 	<input type="text" class="form-control calendar" value="" id="ExportStartDate" name="StartDate" field-name="Start Date" readonly="readonly" placeholder="Start Date">
					 	<p id="reportStartDate"></p>
					 </div>
					 
					<div class="repCustomDateInn">
						<input type="text"  class="form-control calendar" value="" id="ExportEndDate" name="EndDate" field-name="End Date" readonly="readonly" placeholder="End Date">
						<p id="reportEndDate"></p>
					</div>
					
					<div class="repCustomDateBtn"><button type="button" class="btn btn-primary btn-sm" onclick="getDayWiseReports();"><i class="fa fa-search"></i></button></div>
				</div>                    
          <!-- Custom Date --> 
        </div>    		
    		</li>
    		<li class="dataTabPdf">&nbsp;</li> 
    		<li class=""><input type="text" class="repSearchBar" placeholder='Search' id='applicantDeclineReportsTableSearch'/><span class="repSearchIcon" onclick="getDayWiseReports();getDayWiseAppRecReports();"></span></li>
    	</ul>
    </div>       
     
   <div class="repTab1 clearfix" id="listshow" style="display: block;">   
    
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

  	<div class="project_tabs_inn_table vendor_wrap_inn_table" >
		<div id="requirementReportsDiv12" class="repDataSearch"></div>
	</div>      
       </div>
	<div class="repTab2" id="gridshow" style="display: none;">
	 <div id="applicantsLoderDiv"></div>
	  	<div class="chatNew" id="container"></div>	 
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
		document.getElementById("RunApplicantDivId").style.display="none";
	    $(".customLeaves").show();
	    var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
		  var finalDateFormatAppreports = getJSDateFormat(locationDate);
		$('#ExportStartDate').datepicker({
			changeMonth: true,
			changeYear: true, 
			dateFormat : finalDateFormatAppreports,
			onSelect : function(selected) {
				 var dt = $(this).datepicker('getDate', '+1d');
		            dt.setDate(dt.getDate() + 1);
				$("#ExportEndDate").datepicker("option", "minDate", dt);
				addValidClass();
			}
		
		});
		
		$('#ExportEndDate').datepicker({
			changeMonth: true,
			changeYear: true, 
			dateFormat : finalDateFormatAppreports,
			onSelect : function(selected) {
				 var dt = $(this).datepicker('getDate', '-1d');
		            dt.setDate(dt.getDate() - 1);
				$("#ExportStartDate").datepicker("option", "maxDate", dt);
				addValidClass();
			}
		});
	}
	if(reportType!="custom"){
		document.getElementById("RunApplicantDivId").style.display="block";
	    $(".customLeaves").hide();
	    $("#ExportStartDate").datepicker("destroy");
		 $("#ExportEndDate").datepicker("destroy");
	}
}
</script>

<script>
// Reports Active
	$("#gridView").click(function(){
		$("#gridView").addClass("rAct");
		$("#listView").removeClass("rAct");
		$("#gridshow").show();
		$("#listshow, .dataTabPdf").hide();
	});
	$("#listView").click(function(){
		$("#gridView").removeClass("rAct");
		$("#listView").addClass("rAct");
		$("#gridshow").hide();
		$("#listshow, .dataTabPdf").show();
		
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
   // event.preventDefault(); commneted due to script issues.
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

$("#applicantDeclineReportsTableSearch").keydown(function(e) {
	   if(e.which === 13) {
		   getDayWiseReports();
		   getDayWiseAppRecReports();
	   }
	});

function getDayWiseReports(){
	$('#container').empty();
	$('#reportStartDate').empty();
	$('#reportEndDate').empty(); 
	var reportType = $("#reportTypeId").val();
	var startDate = $("#ExportStartDate").val();
	var endDate = $("#ExportEndDate").val();
	var searchValue = $("#applicantDeclineReportsTableSearch").val();
	searchValue = searchValue ? searchValue : "";
	if(reportType == "custom" && (startDate == null || startDate == '' || startDate == 'undefined')){
		document.getElementById("reportStartDate").style.display="block";
		$("#reportStartDate").empty().append("Start Date is Required") ;
		return;
	}else{
		document.getElementById("reportStartDate").style.display="none";	
	}
	if(reportType == "custom" && (endDate == null || endDate == '' || endDate == 'undefined')){
		document.getElementById("reportEndDate").style.display="block";
		$("#reportEndDate").empty().append("End Date is Required") ; 
		return;
	}else{
		document.getElementById("reportEndDate").style.display="none";
	}
		$("#applicantsLoderDiv").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:999;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
		$.ajax({
         type : "POST",
         url: sourceleadUrl+"/getApplicantReceivedReports?"+tokenParamName+"="+tokenValue+"&reportType="+reportType+"&startDate="+startDate+"&endDate="+endDate+"&searchValue="+searchValue,     
          success :function(data) {
	        	 prepareReportDataDiv(data);
	        	 var json = JSON.parse(data);
          		getDayWiseAppRecReports();
         },
	 
     });  
	
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
	function applicantReceivedReportLineChart(applicantData,shortlistedData,interviewData,submitedtoClientData,offeredData,rejectedData){
		Highcharts.chart('container', {
    chart: {
        type: 'column'
    },
    title: {
        text: 'Applicants Received'
    },
    xAxis: {
        categories: "",
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
	getDayWiseAppRecReports();
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
        	 if(json != null && json !='undefined' && json !=null){ 
        		 $("#applicantCount").html(json.applicantsCount);  
        		 $("#shortlistedCount").html(json.shortlisted); 
		  	     $("#submitToClientCount").html(json.submitToClientCount);
		  	     $("#offerCount").html(json.offeredCount); 
		 	     $("#interviewCount").html(json.interviewCount);
			     $("#rejectedCount").html(json.rejectedCount);
		        		    var applicantData =[];
		        		    var shortlistedData =[];
		        		    var interviewData =[];
		        		    var submitedtoClientData =[];
		        		    var offeredData =[];
		        		    var rejectedData  =[];
		        		    
		        			    applicantData.push( 
		        			    		json.applicantsCount
		        			    );
		        			    shortlistedData.push( 
		        			    		json.shortlisted
			        			    );
		        			    submitedtoClientData.push( 
		        			    		json.submitToClientCount
			        			    );
		        			    interviewData.push( 
		        			    		json.interviewCount
			        			    );
		        			    offeredData.push( 
		        			    		json.offeredCount
			        			    );
		        			    rejectedData.push( 
		        			    		json.rejectedCount
			        			    ); 
		        			     
		        		 applicantReceivedReportLineChart(applicantData,shortlistedData,interviewData,submitedtoClientData,offeredData,rejectedData);
	        	 }
        	 else{
        		 var result ="";
	        		result = result + ' <div class="row pavEmp">                                            ';
					result = result + '	<div class="dataTables_empty">                                     ';
					result = result + '	</div>                                                      ';
					result = result + ' </div>                                                      ';
				  $("#container").empty();
				    $("#container").append(result);
				    $("#applicantsLoderDiv").empty();
				    $("#applicantsLoderDiv").removeAttr('style');
        	 }
        	// getAllCounts(json.length,reportType,startDate,endDate);
         },
          
     });  
	/* } */
	
	
	
	
	
	
}

</script>
<jsp:include page="../masterFooter.jsp"></jsp:include>
