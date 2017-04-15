

<script type="text/javascript">
window.onload = function() { 
	getVendorsReportList();
	}
</script>

<script>
function getVendorsReportList(){
	$('#reportStartDate').empty();
	$('#reportEndDate').empty();
	var reportType = $("#reportTypeId").val();
	var startDate = $("#ExportStartDate").val();
	var endDate = $("#ExportEndDate").val();
	var sourceleadUrl ='${sourceleadUrl}';
	$.ajax({
         type : "POST",
          url: sourceleadUrl+"/getVendorRequirementReports?${_csrf.parameterName}=${_csrf.token}&reportType="+reportType+"&customStartDate="+startDate+"&customEndDate="+endDate,    
          success :function(data) {
        	 prepareReportDataDiv(data);
         },
          
     }); 
}
</script>
<script>
function prepareReportDataDiv(data){
	var result = '';
	
	
	/* result = result +  '<div class="pull-left reportBtns">';
	result = result +  ' <div class="reportBtn">';
	result = result +  '	<select class="form-control select-bg" id="reportTypeId" onChange="flushVendorCustomDates(); getVendorsReportList(); custvendorDates();">';
	result = result +  '	 <option value="">select</option>';
	result = result +  '	  <option value="today">Today</option>';
	result = result +  '  <option value="yesterday">Yesterday</option>';
	result = result +  '  <option value="week">1 week</option>';
	result = result +  '  <option value="twoweeks">2 weeks</option>';
	result = result +  '  <option value="month">1 month</option>';
	result = result +  '  <option value="twomonths">2 months</option>';
	result = result +  '   <option value="custom">Custom</option> ';
	result = result +  '	</select>';
	result = result +  ' </div>	';
	 
	result = result +  ' <span class="dashboard-datepkr cusShow">';
	result = result +  ' <input type="text" class="form-control" value="" id="ExportStartDate" name="StartDate" field-name="Start Date" data-validation="required" readonly="readonly" placeholder="Start Date"> <i class="fa fa-calendar"></i>';
	result = result +  ' <p id="reportStartDate" class="ReError"></p>';
	result = result +  ' </span>';
	result = result +  '<span class="dashboard-datepkr cusShow">';
	result = result +  '<input type="text"  class="form-control" value="" id="ExportEndDate" name="EndDate" field-name="End Date" data-validation="required" readonly="readonly" placeholder="End Date"><i class="fa fa-calendar"></i>';
	result = result +  '<p id="reportEndDate" class="ReError"></p>';
	result = result +  '</span>';
	result = result +  '<span class="cusShow"><input type="button" value="Submit" class="btn btn-primary repBtn" onclick="getCustomReports();" /></span>';
	
	result = result +  '</div>'; */
	
	
	
	
	
	result = result +  '<table id="vendorReportPagination" class="display" cellspacing="0" width="100%" >';
	result = result + '	<thead class="parentHeader">';
	result = result + '   <tr>';
	result = result + '    <th>Vendor Name</th>';
	result = result + '    <th>Released</th>';
	result = result + '    <th>Submitted</th>';
	result = result + '    <th>Shortlisted</th>';
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
        result = result + '<div><span>'+report.vendorName+'</span></div> ';
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
        
        result = result + '</tr>';
    }
	result = result + '	</tbody>';
	result = result + '</table>';
	$("#vendorReports").empty();
    $("#vendorReports").append(result);
    vendorReportPagination();
}

</script>
<script>
function vendorReportPagination(){
	$('#vendorReportPagination').DataTable({ 
		 dom: 'Bfrtip',
	        buttons: [
	        	{
	                extend: 'excel',
	                title: 'Requirements Posted',
	            },
	            {
	                extend: 'pdf',
	                title: 'Requirements Posted',
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
<section class="content-wraper">
  <section class="content-header clearfix">
    <div class="pull-left">
              <h2>Vendor Reports</h2>
            </div>
  </section>
  
  <div class="timesheet_out">
    <div class="vendor_wrap">
     
      <div class="timesheet_head">

<div class="pull-left reportBtns">
	  <div class="reportBtn">
		<select class="form-control select-bg" id="reportTypeId" onChange="flushCustomDates(); getVendorsReportList(); custDates();">
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
  
 <div class="clearfix">
<section class="accordionToggle">

<div id="vendorReportsDiv" class="repoPositiNoCount"></div>

 <!-- <table class="table" width="100%">

  <tbody>
  Header table
   <tr>
  	<td colspan="3">
      <table width="100%">
      <thead class="parentHeader">
       <th>Vendor</th>
         <th>Released</th>
         <th>Submitted</th>
         <th>Shortlisted</th>
         <th>Interviewed</th>
         <th>Offered</th>
         <th>Rejected</th>
      </thead>
          <tbody>
       </tbody>
      </table>
    </td>
  </tr>
Step 1      
 <tr>
  	<td colspan="3">
      <table width="100%">
      <thead class="accordionLink">
          <th><a href="javascript:void(0)">Tata Consultancy Services <span></span></a></th>
           <th>300</th>
            <th>250</th>
             <th>450</th>
              <th>500</th>
               <th>050</th>
               <th>150</th>
          </thead>
       <tbody class="accordionPanel">
  <tr>
  	<td>Jaden Smith</td>
  	<td>100</td>
  	<td>200</td>
  	<td>300</td>
  	<td>400</td><td>500</td><td>500</td>
  </tr>
  <tr>
  	<td>Kiran Rao</td><td>100</td><td>200</td><td>300</td><td>400</td><td>500</td><td>500</td>
  </tr>
  <tr>
  	<td>Sivakumar</td><td>100</td><td>200</td><td>300</td><td>400</td><td>500</td><td>500</td>
  </tr>
       </tbody>
      </table>
    </td>
  </tr>

Step Two      
      
 <tr>
  	<td colspan="3">
      <table width="100%">
      <thead class="accordionLink">
          <th><a href="javascript:void(0)">Tata Consultancy Services <span></span></a></th>
           <th>300</th>
            <th>250</th>
             <th>450</th>
              <th>500</th>
               <th>050</th>
               <th>150</th>
          </thead>
       <tbody class="accordionPanel">
  <tr>
  	<td>Jaden Smith</td>
  	<td>100</td>
  	<td>200</td>
  	<td>300</td>
  	<td>400</td>
  	<td>500</td>
  	<td>500</td>
  </tr>
  <tr>
  	<td>Kiran Rao</td>
  	<td>100</td>
  	<td>200</td>
  	<td>300</td>
  	<td>400</td>
  	<td>500</td>
  	<td>500</td>
  </tr>
  <tr>
  	<td>Sivakumar</td>
  	<td>100</td>
  	<td>200</td>
  	<td>300</td>
  	<td>400</td>
  	<td>500</td>
  	<td>500</td>
  </tr>
       </tbody>
      </table>
    </td>
  </tr>      
      
      
  </tbody>
</table>    --> 
    
</section>    
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
	function flushVendorCustomDates() {
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
         url: sourceleadUrl+"/getRequirementReports?${_csrf.parameterName}=${_csrf.token}&reportType="+reportType+"&startDate="+startDate+"&endDate="+endDate,     
          success :function(data) {
        	 prepareReportDataDiv(data);
         },
          
     });  
	}
	
}

</script>
<script>
function custvendorDates(){
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
	          url: sourceleadUrl+"/getVendorRequirementReports?${_csrf.parameterName}=${_csrf.token}&reportType="+reportType+"&customStartDate="+startDate+"&customEndDate="+endDate,    
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
