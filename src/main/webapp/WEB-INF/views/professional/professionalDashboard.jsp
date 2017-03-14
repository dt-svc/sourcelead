<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="resources/js/amcharts.js"></script>
<script src="resources/js/serial.js"></script>
<script src="resources/js/light.js"></script>
<script src="resources/js/pie.js"></script>


<!-- <script src="https://www.amcharts.com/lib/3/plugins/export/export.min.js"></script>
<link rel="stylesheet" href="https://www.amcharts.com/lib/3/plugins/export/export.css" type="text/css" media="all" /> --> 


<style>
dataTables_empty {
    height: 285px !important;
}
</style>

<script type="text/javascript">
$(document).ready(function(){
	getJobSummarySerialChart();
	getJobSummaryDoNutChart();
	getReferToMeJobPortlets();
	getReferByMeJobPortlets();
	interviews('today');
});
</script>

<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Jobs Summary</h2>
      </div>
    </section>
    <div class="content-new clearfix"></div>
    
      <div class="row">
          <div class="col-md-7">
          <div class="chartArea">
          <div class="col-md-10">
				    <div class="row">
					  <div class="col-md-5 paddingRight">
					     <input type="text" value="" placeholder="From Date" class="form-control input-sm calendar" id="serialfromdate" name="From Date" readonly="readonly">
					  </div>
			  		  <div class="col-md-5 paddingRight paddingLeft">
					     <input type="text" value="" placeholder="To Date" class="form-control input-sm calendar" id="serialtodate" name="To Date" readonly="readonly">
					  </div>
					  <div class="col-md-2 paddingLeft">
						  <button type="button" class="btn btn-sm btn-primary" onclick="getJobSummarySerialChart();"><i class="fa fa-search"></i>&nbsp;&nbsp;Search</button>
					  </div>
				  </div>
 		   </div>
              <div id="chartdiv" style="width: 100%;">
              
              </div>
          </div>
          </div>

          <div class="col-md-5 text-center paddingLeft">
            <div class="chartArea">
          <div class="col-md-10">
				    <div class="row">
					  <div class="col-md-5 paddingRight">
					     <input type="text" value="" placeholder="From Date" class="form-control input-sm calendar" id="dpfromdate" name="From Date" readonly="readonly">
					  </div>
			  		  <div class="col-md-5 paddingLeft paddingRight">
					     <input type="text" value="" placeholder="To Date" class="form-control input-sm calendar" id="dptodate" name="To Date" readonly="readonly">
					  </div>
					  <div class="col-md-2 paddingLeft">
						  <button type="button" class="btn btn-sm btn-primary" onclick="getJobSummaryDoNutChart();"><i class="fa fa-search"></i>&nbsp;&nbsp;Search</button>
					  </div>
				  </div>
 		   </div>
 		   <div class="content-new clearfix mt25">
             <div id="jobSummary">
         
             </div>
          </div>
      </div></div>
      <div class="clearfix"></div>
      </div>
      
     <div class="row">
      <div class="col-md-12">
		<div class="dashboard-widget clearfix">
				    <div class="row">
					  <div style="visibility:hidden" id="customDate">
					  <div class="col-md-3 paddingRight">
					     <input type="text" value="" placeholder="From Date" class="form-control input-sm calendar" id="portletfromdate" name="From Date" readonly="readonly">
					  </div>
			  		  <div class="col-md-3 paddingLeft paddingRight">
					     <input type="text" value="" placeholder="To Date" class="form-control input-sm calendar" id="portlettodate" name="To Date" readonly="readonly">
					  </div>
					  <div class="col-md-2 paddingLeft">
						  <button type="button" class="btn btn-sm btn-primary" onclick="getReferToMeJobPortlets();"><i class="fa fa-search"></i>&nbsp;&nbsp;Search</button>
					  </div>
					  </div>
					  <div class="col-md-2  col-md-offset-2 select-field">
					   <select class="form-control active" id="refertomeChangeMonth" onchange="refertomeChangeMonth(this)">
					   		<option selected value="currentMonth">Current Month</option>
					   		<option value="customize">Customize</option>
					   </select>
					  </div>
				  </div>
      <div id="jobPortletsDiv" class="activeJobs row"></div>
      </div>
      </div>
      <div class="clearfix"></div>
      </div>
      
      <div class="row mt">
      <div class="col-md-12">
		<div class="dashboard-widget clearfix">
				    <div class="row">
					  <div style="visibility:hidden" id="customReferByMeDate">
					  <div class="col-md-3 paddingRight">
					     <input type="text" value="" placeholder="From Date" class="form-control input-sm calendar" id="portletreferByMefromdate" name="From Date" readonly="readonly">
					  </div>
			  		  <div class="col-md-3 paddingLeft paddingRight">
					     <input type="text" value="" placeholder="To Date" class="form-control input-sm calendar" id="portletreferByMetodate" name="To Date" readonly="readonly">
					  </div>
					  <div class="col-md-2 paddingLeft">
						  <button type="button" class="btn btn-sm btn-primary" onclick="getReferByMeJobPortlets();"><i class="fa fa-search"></i>&nbsp;&nbsp;Search</button>
					  </div>
					  </div>
					  <div class="col-md-2  col-md-offset-2 select-field">
					   <select class="form-control active" id="referbymeChangeMonth" onchange="referbymeChangeMonth(this)">
					   		<option selected value="currentMonth">Current Month</option>
					   		<option value="customize">Customize</option>
					   </select>
					  </div>
				  </div>
      <div id="referByMeJobPortletsDiv" class="activeJobs row"></div>
      </div>
      </div>

      </div>
      
      <section class="row mt">
				        <div class="col-md-12">
				          <div class="dashboard-widget crowd_dashboard">
				            <h4 class="pull-left">Interviews <span class="dashboard-datepkr dashboarddatepkr dashboarddatepkr">
				            <input type="text" class="input-sm" id="professionalInterviewdate" readonly="readonly">
				            <i class="fa fa-calendar"></i></span></h4>
				            <ul class="nav nav-tabs ml">
				              <li class="active" id="todayTab"><a data-toggle="tab" href="javascript:void(0)" onclick="flushDate(); interviews('today')">Today</a></li>
				              <li id="upcomingTab"><a data-toggle="tab" href="javascript:void(0)" onclick="flushDate(); interviews('upcoming')">Upcoming</a></li>
				              <li id="previousTab"><a data-toggle="tab" href="javascript:void(0)" onclick="flushDate(); interviews('previous')">Previous</a></li>
				            </ul>
				            <div class="clearfix"></div>
				           <div class="tab-content clearfix require_wrap_table overflow_inherint">
				               <div id="today" class="tab-pane fade in active"> </div>
	                		   <div id="upcoming" class="tab-pane fade in"> </div>
                			   <div id="previous" class="tab-pane fade in"> </div>
	           				</div>
	          			</div>
	               </div>
	       </section> 
	       
  </section>
  
  
<jsp:include page="../masterFooter.jsp" />
<div class="backtotop" style="display: none;"><img src="resources/images/backtotop.png"></div> 


  <script>
  function refertomeChangeMonth(){
	  
	    $("#portletfromdate").val('');
	    $("#portlettodate").val(''); 
	    
	  var refertomeChangeMonth = $("#refertomeChangeMonth").val();
	  if(refertomeChangeMonth == "customize"){
		  document.getElementById("customDate").style.visibility='visible';
	  }else{
		  document.getElementById("customDate").style.visibility='hidden';
	  }
  }
  </script>
  
  
  
  <script>
  function referbymeChangeMonth(){
	  $("#portletreferByMefromdate").val('');
	  $("#portletreferByMetodate").val(''); 
	    
	  var referbymeChangeMonth = $("#referbymeChangeMonth").val();
	  if(referbymeChangeMonth == "customize"){
		  document.getElementById("customReferByMeDate").style.visibility='visible';
	  }else{
		  document.getElementById("customReferByMeDate").style.visibility='hidden';
	  }
  }
  </script>
  
<script>
function flushDate(){
	$("#datepicker-int").val('');
}
</script>

<script>
	function interviews(dateRange){
		var resultToday ="";
		var sourceleadUrl = '${sourceleadUrl}';
			$.ajax({
				type : "GET",
				url : sourceleadUrl+"/getProfessionalInterviews?dateRange="+dateRange,
				success : function(data) {
					var json = JSON.parse(data);
		              			$("#today").empty();
		        	        	$("#upcoming").empty();
		        	        	$("#previous").empty();
		              			$("#today").removeAttr("style");
		            				resultToday = resultToday+'	<table id="requirementInterviewsPagination" class="display" cellspacing="0" width="100%">';
		            				resultToday = resultToday+'	<thead>';
		            				resultToday = resultToday+'	  <tr>';
		            				resultToday = resultToday+'	    <th>Requirement Title </th>';
		            				resultToday = resultToday+'	    <th>Company Name</th>';
		            				resultToday = resultToday+'	    <th>Date &amp; Time</th>';
		            				resultToday = resultToday+'	    <th>Type</th>';
		            				resultToday = resultToday+'	    <th>Location</th>';
		            				resultToday = resultToday+'	  </tr>';
		            				resultToday = resultToday+'	</thead>';
		            				resultToday = resultToday+'	<tbody>';
		            				
		            				for (var i=0; i<json.length; i++){
		            		       		var report = json[i];
		            		       		var location ="";
		            		       		var interviewType = "";
		            		       		if(report.location != null && report.location != '' && report.location != 'undefined'){
		            		       			location = report.location
		            		       		}else{
		            		       			location = "--";
		            		       		}
		            		       		if(report.interviewType != null && report.interviewType != "" && report.interviewType != 'undefined'){
		            		       			interviewType = report.interviewType;
		            		       		}else{
		            		       			interviewType = "--";
		            		       		}
		            		       		
		            						resultToday = resultToday+'	  <tr>';
		            						resultToday = resultToday+'	   <td><div class="tbldata-dotdot-vsm"> <a href="'+sourceleadUrl+'/requirementProfile/'+report.requirementUUID+'"><span title ="'+report.requirementTitle+'">'+report.requirementTitle+'</span></a></div></td>';
		            						resultToday = resultToday+'	   <td><div><span title="'+report.companyName+'">'+report.companyName+'</span></div></td>';
		            						resultToday = resultToday+'	   <td><div class="tblminwidth-150">'+report.interviewStartDateTimeStr+'</div></td>';
		            						resultToday = resultToday+'	   <td><div><span title="'+interviewType+'">'+interviewType+'</span></div></td>';
		            						resultToday = resultToday+'	   <td><div><span title="'+location+'">'+location+'</span></div></td>';
		            						resultToday = resultToday+'	 </tr>';
		            					}
		            					resultToday = resultToday+'	 </tbody>';
		            					resultToday = resultToday+'	</table>';											
		            	        if(dateRange == 'today'){
		            	            $("#today").append(resultToday);
		            	            requirementInterviewsPagination();
		            	            $("#today").addClass("tab-pane fade in active");
		            				$("#upcoming").removeClass("tab-pane fade in active");
		            				$("#previous").removeClass("tab-pane fade in active");
		            	        }else if(dateRange == 'previous'){
		            				$("#previous").append(resultToday);
		            				requirementInterviewsPagination();
		            				$("#today").removeClass("tab-pane fade in active");
		            				$("#upcoming").removeClass("tab-pane fade in active");
		            				$("#previous").addClass("tab-pane fade in active");
		            	        }else if(dateRange == 'upcoming'){
		            				$("#upcoming").append(resultToday);
		            				requirementInterviewsPagination();
		            				$("#today").removeClass("tab-pane fade in active");
		            				$("#previous").removeClass("tab-pane fade in active");
		            				$("#upcoming").addClass("tab-pane fade in active");
		            	        }else{
		            	        	$("#today").append(resultToday);
		            	            requirementInterviewsPagination();
		            	            $("#todayTab").removeClass("active");
		            				$("#upcomingTab").removeClass("active");
		            				$("#previousTab").removeClass("active");
		            	        }
						
				},
		});
		return true;
	}
</script>
  
<script>
	function requirementInterviewsPagination(){
	    $('#requirementInterviewsPagination').DataTable( {
	    	"scrollCollapse": false,
	    	"lengthMenu": [[5, 10, 15, -1], [5, 10, 15, "All"]],
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
	   $( "#professionalInterviewdate" ).datepicker({
	     changeMonth: true,
	     changeYear: true,
	     dateFormat: 'yy-mm-dd',
	     onSelect: function(){
	    	    var dt = $(this).val();
	    	    interviews(dt);
	   		}
	   });
	 });
</script>
	 
  <script>
  function getReferToMeJobPortlets(){
	  
    var sourceleadUrl = '${sourceleadUrl}';
	
	var startDate = document.getElementById("portletfromdate").value; 
	var endDate = document.getElementById("portlettodate").value; 
	  
	$.ajax({ 
		   	type: "GET",
		   	url: sourceleadUrl+"/getJobSummaryPortlets?${_csrf.parameterName}=${_csrf.token}&startDate="+startDate+"&endDate="+endDate,
		    success: function(data) {
	    	 var json = JSON.parse(data);
		    	
	    	  var result = ''
			 
              result = result +'<div class="col-sm-6 col-md-3">';
              result = result +'<div class="content_widget jobs-graphview buttonView">';
              result = result +'<span class="label label-warning">Referred To Me</span>';
              result = result +'<img src="resources/images/refer-icon.png" title="Referrals" alt="Referrals">';
              if(json['appliedJobs'] > 0){
            	  result = result +'<a href="#" onClick="getReferredToMeProtletList(\''+startDate+'\',\''+endDate+'\',\'referToMe\');"><span class="count text-primary">'+json['referToMeJobs']+'</span></a>';
              }else{
            	  result = result +'<span class="count">0</span>';
              }
              
              result = result +'</div>';
              result = result +' </div>';
              result = result +'<div class="col-sm-6 col-md-3">';
              result = result +'<div class="content_widget jobs-graphview buttonView">';
              result = result +'<span class="label label-success">Applied</span>';
                        
              result = result +'<img src="resources/images/applied_icon.png" title="Applied" alt="Applied">';
              if(json['appliedJobs'] > 0){
            	  result = result +'<a href="#" onClick="getReferredToMeProtletList(\''+startDate+'\',\''+endDate+'\',\'\');"><span class="count text-primary">'+json['appliedJobs']+'</span></a>';  
              }else{
            	  result = result +'<span class="count text-primary">0</span>';
              }
              result = result +'</div>';

              result = result +'</div>';
              result = result +'<div class="col-sm-6 col-md-3">';
              result = result +'<div class="content_widget jobs-graphview buttonView">';
              result = result +'<span class="label label-info">Shortlisted</span>';
              result = result +'<img src="resources/images/short_listed.png" title="Shortlisted" alt="Shortlisted">';
              if(json['shortlistJobs'] > 0){
            	  result = result +'<a href="#" onClick="getReferredToMeProtletList(\''+startDate+'\',\''+endDate+'\',\'SHORTLIST\');"><span class="count text-primary">'+json['shortlistJobs']+'</span></a>';  
              }else{
            	  result = result +'<span class="count">0</span>';
              }
              
              result = result +'</div>';

              result = result +'</div>';
              result = result +'<div class="col-sm-6 col-md-3">';
              result = result +'<div class="content_widget jobs-graphview buttonView">';
              result = result +'<span class="label label-success">Interviews</span>';
              result = result +'<img src="resources/images/interview_icons.png" title="Interviews" alt="Interviews">';
              if(json['interviews'] > 0){
            	  result = result +'<a href="#" onClick="getReferredToMeProtletList(\''+startDate+'\',\''+endDate+'\',\'INTERVIEW\');"><span class="count text-primary">'+json['interviews']+'</span></a>';  
              }else{
            	  result = result +'<span class="count">0</span>';
              }
              
              result = result +'</div>';

              result = result +'</div>';
              result = result +'<div class="col-sm-6 col-md-3">';
              result = result +'<div class="content_widget jobs-graphview buttonView">';
              result = result +'<span class="label label-info">Offered</span>';
                        
              result = result +'<img src="resources/images/offered_icon.png" title="Offered" alt="Offered">';
              if(json['offeredJobs'] > 0){
            	  result = result +'<a href="#" onClick="getReferredToMeProtletList(\''+startDate+'\',\''+endDate+'\',\'OFFERMADE\');"><span class="count text-primary">'+json['offeredJobs']+'</span></a>';  
              }else{
            	  result = result +'<span class="count">0</span>';
              }
              
              result = result +'</div>';

              result = result +'</div>';
              result = result +'<div class="col-sm-6 col-md-3">';
              result = result +'<div class="content_widget jobs-graphview buttonView">';
              result = result +'<span class="label label-primary">Hired</span>';
              
              result = result +'<img src="resources/images/hired_icon.png" title="Hired" alt="Hired">';
              result = result +'<a href="#"><span class="count">0</span></a>';
              result = result +'</div>';

              result = result +'</div>';
              result = result +'<div class="col-sm-6 col-md-3">';
              result = result +'<div class="content_widget jobs-graphview buttonView">';
              result = result +'<span class="label label-danger">Rejected';
              result = result +'</span>';
              
              result = result +'<img src="resources/images/reject_icon.png" title="Rejected" alt="Rejected">';
              if(json['rejectedJobs'] > 0){
            	  result = result +'<a href="#" onClick="getReferredToMeProtletList(\''+startDate+'\',\''+endDate+'\',\'REJECTED\');"><span class="count text-primary">'+json['rejectedJobs']+'</span></a>';
              }else{
            	  result = result +'<span class="count">0</span>';
              }
             
              result = result +'</div>';

              result = result +'</div>';
              
              $("#jobPortletsDiv").empty(); 
              $("#jobPortletsDiv").append(result); 
		    },
	        error: function(e){
	        }
	 });
  }
  
  </script>
<script>
function getReferredToMeProtletList(startDate,endDate,status){
	var sourceleadUrl = '${sourceleadUrl}';
	window.location.href = sourceleadUrl + '/referredToMePortletList?${_csrf.parameterName}=${_csrf.token}&startDate='+startDate+'&endDate='+endDate+'&requirementStatus='+status;
}
</script>
  
  
  <script>
  function getReferByMeJobPortlets(){
	  
    var sourceleadUrl = '${sourceleadUrl}';
	
	var startDate = document.getElementById("portletreferByMefromdate").value; 
	var endDate = document.getElementById("portletreferByMetodate").value; 
	  
	$.ajax({ 
		   	type: "GET",
		   	url: sourceleadUrl+"/getReferByMeJobSummaryPortlets?${_csrf.parameterName}=${_csrf.token}&startDate="+startDate+"&endDate="+endDate,
		    success: function(data) {
	    	 var json = JSON.parse(data);
		    	
	    	  var result = ''
			 
              result = result +'<div class="col-sm-6 col-md-3">';
              result = result +'<div class="content_widget jobs-graphview buttonView">';
              result = result +'<span class="label label-warning">Referred By Me</span>';
              result = result +'<img src="resources/images/refer-icon.png" title="Referrals" alt="Referrals">';
          	 if(json['referByMeJobs'] > 0){
          		result = result +'<a href="#" onClick="getReferredByMeProtletList(\''+startDate+'\',\''+endDate+'\',\'referByMe\');"><span class="count text-primary">'+json['referByMeJobs']+'</span></a>'; 
          	 }else{
          		result = result +'<span class="count">0</span>';
          	 }
              
              result = result +'</div>';
              result = result +' </div>';
              result = result +'<div class="col-sm-6 col-md-3">';
              result = result +'<div class="content_widget jobs-graphview buttonView">';
              result = result +'<span class="label label-success">Applied</span>';
                        
              result = result +'<img src="resources/images/applied_icon.png" title="Applied" alt="Applied">';
              if(json['appliedJobs'] > 0){
            	  result = result +'<a href="#" onClick="getReferredByMeProtletList(\''+startDate+'\',\''+endDate+'\',\'\');"><span class="count text-primary">'+json['appliedJobs']+'</span></a>';  
              }else{
            	  result = result +'<span class="count text-primary">0</span>';
              }
              result = result +'</div>';

              result = result +'</div>';
              result = result +'<div class="col-sm-6 col-md-3">';
              result = result +'<div class="content_widget jobs-graphview buttonView">';
              result = result +'<span class="label label-info">Shortlisted</span>';
              result = result +'<img src="resources/images/short_listed.png" title="Shortlisted" alt="Shortlisted">';
              if(json['shortlistJobs'] > 0){
            	  result = result +'<a href="#" onClick="getReferredByMeProtletList(\''+startDate+'\',\''+endDate+'\',\'SHORTLIST\');"><span class="count text-primary">'+json['shortlistJobs']+'</span></a>';  
              }else{
            	  result = result +'<span class="count">0</span>';
              }
              
              result = result +'</div>';

              result = result +'</div>';
              result = result +'<div class="col-sm-6 col-md-3">';
              result = result +'<div class="content_widget jobs-graphview buttonView">';
              result = result +'<span class="label label-success">Interviews</span>';
              result = result +'<img src="resources/images/interview_icons.png" title="Interviews" alt="Interviews">';
              if(json['interviews'] > 0){
            	  result = result +'<a href="#" onClick="getReferredByMeProtletList(\''+startDate+'\',\''+endDate+'\',\'INTERVIEW\');"><span class="count text-primary">'+json['interviews']+'</span></a>';  
              }else{
            	  result = result +'<span class="count">0</span>';
              }
              
              result = result +'</div>';

              result = result +'</div>';
              result = result +'<div class="col-sm-6 col-md-3">';
              result = result +'<div class="content_widget jobs-graphview buttonView">';
              result = result +'<span class="label label-info">Offered</span>';
                        
              result = result +'<img src="resources/images/offered_icon.png" title="Offered" alt="Offered">';
              if(json['offeredJobs'] > 0){
            	  result = result +'<a href="#" onClick="getReferredByMeProtletList(\''+startDate+'\',\''+endDate+'\',\'OFFERMADE\');"><span class="count text-primary">'+json['offeredJobs']+'</span></a>';  
              }else{
            	  result = result +'<span class="count">0</span>';
              }
              
              result = result +'</div>';

              result = result +'</div>';
              result = result +'<div class="col-sm-6 col-md-3">';
              result = result +'<div class="content_widget jobs-graphview buttonView">';
              result = result +'<span class="label label-primary">Hired</span>';
              
              result = result +'<img src="resources/images/hired_icon.png" title="Hired" alt="Hired">';
              result = result +'<a href="#"><span class="count">0</span></a>';
              result = result +'</div>';

              result = result +'</div>';
              result = result +'<div class="col-sm-6 col-md-3">';
              result = result +'<div class="content_widget jobs-graphview buttonView">';
              result = result +'<span class="label label-danger">Rejected';
              result = result +'</span>';
              
              result = result +'<img src="resources/images/reject_icon.png" title="Rejected" alt="Rejected">';
              if(json['rejectedJobs'] > 0){
            	  result = result +'<a href="#" onClick="getReferredByMeProtletList(\''+startDate+'\',\''+endDate+'\',\'REJECTED\');"><span class="count text-primary">'+json['rejectedJobs']+'</span></a>';
              }else{
            	  result = result +'<span class="count">0</span>';
              }
             
              result = result +'</div>';

              result = result +'</div>';
              
              $("#referByMeJobPortletsDiv").empty(); 
              $("#referByMeJobPortletsDiv").append(result); 
		    },
	        error: function(e){
	        }
	 });
  }
  
  </script>
  
<script>
	function getReferredByMeProtletList(startDate,endDate,status){
			var sourceleadUrl = '${sourceleadUrl}';
			window.location.href = sourceleadUrl + '/referredByMePortletList?${_csrf.parameterName}=${_csrf.token}&startDate='+startDate+'&endDate='+endDate+'&requirementStatus='+status;
	}
</script>
  
  
 <script>
  function getJobSummaryDoNutChart(){
	var sourceleadUrl = '${sourceleadUrl}';
	
	var startDate = document.getElementById("dpfromdate").value; 
	var endDate = document.getElementById("dptodate").value; 
	
	$.ajax({ 
		   	type: "GET",
		   	url: sourceleadUrl+"/getJobSummaryDoNutChart?${_csrf.parameterName}=${_csrf.token}&startDate="+startDate+"&endDate="+endDate,
		    success: function(data) {
	        	var json = JSON.parse(data);
	        	
	        	loadJobSummaryDoNutChart(json['appliedJobs'],json['referToMeJobs'],json['referByMeJobs'])
	        },
	        error: function(e){
	        }
	 });
  }
  </script>
  
  <script>
  function getJobSummarySerialChart(){
	  var sourceleadUrl = '${sourceleadUrl}';
		
		
		var startDate = document.getElementById("serialfromdate").value; 
		var endDate = document.getElementById("serialtodate").value; 
		
		
		$.ajax({ 
			   	type: "GET",
			   	url: sourceleadUrl+"/getJobSummarySerialChart?${_csrf.parameterName}=${_csrf.token}&startDate="+startDate+"&endDate="+endDate,
			    success: function(data) {
		        	var json = JSON.parse(data);
		        	
		        	 if(data != null && data !='undefined' && data !=null &&  data != [] && json.length >0){ 
			        	if(json.length > 0)	
			 	   		{
			        		    var chartData = [];
								for(var i=0; i < json.length ; i++){
			        			 
			        			 var jobs = json[i];
			        			    chartData.push( {
			        			      day: jobs.dateString,
			        			      referedToMe:jobs.referToMeJobs,
			        			      referedByMe:jobs.referByMeJobs,
			        			      applied: jobs.appliedJobs
			        			     
			        			    } );
			        			  }
			        	
			        		 loadJobSummarySerialChart(chartData);
			 	   		}
		        	 }
		        	
		        },
		        error: function(e){
		        }
		 });
  }
  </script>
  
  
  
  
  <!-- For donut chart -->
<script>
function loadJobSummaryDoNutChart(appliedJobs,referToMeJobs,referByMeJobs){
	if(appliedJobs == 0 && referToMeJobs == 0 && referByMeJobs == 0){
		
	    var result ="";
        result = result+"<div class='dataTables_empty mt'>"; 
	    result = result+"</div> "; 
		$("#jobSummary").empty();
	    $("#jobSummary").append(result); 
	    
	    /* $("#dpfromdate").val('');
	    $("#dptodate").val(''); */
		
	}else{
		var chart = AmCharts.makeChart( "jobSummary", {
			  "type": "pie",
			  "theme": "light",
			  "dataProvider": [ {
			    "title": "Refered To Me ("+referToMeJobs+")",
			    "value": referToMeJobs
			  }, {
			    "title": "Refered By Me ("+referByMeJobs+")",
			    "value": referByMeJobs
			  }, {
			    "title": "Applied ("+appliedJobs+")",
			    "value": appliedJobs
			  } ],
			  "titleField": "title",
			  "valueField": "value",
			  "labelRadius": 5,
			
			  "radius": "35%",
			  "innerRadius": "40%",
			  "labelText": "[[title]]",
			  "export": {
			    "enabled": true
			  }
			} );
			 
	}
 
}
    
</script>

 <script>
 function loadJobSummarySerialChart(result){
  var chart = AmCharts.makeChart("chartdiv", {
	    "type": "serial",
	    "theme": "light",
	    "legend": {
	        "useGraphSettings": true
	    },
	    "dataProvider": result,
	    "valueAxes": [{
	        "integersOnly": true,
	        "maximum": 50,
	        "minimum": 1,
	        "reversed": false,
	        "axisAlpha": 0,
	        "dashLength": 5,
	        "gridCount": 10,
	        "position": "left",
	        "title": "Jobs"
	    }],
	    "startDuration": 0.5,
	    "graphs": [{
	        "balloonText": "Refered To Me: [[value]]",
	        "bullet": "round",
	        "title": "Refered To Me",
	        "valueField": "referedToMe",
			"fillAlphas": 0
	    }, {
	        "balloonText": "Refered By Me: [[value]]",
	        "bullet": "round",
	        "title": "Refferd By Me",
	        "valueField": "referedByMe",
			"fillAlphas": 0
	    },{
	        "balloonText": "Applied: [[value]]",
	        "bullet": "round",
	        "title": "Applied",
	        "valueField": "applied",
			"fillAlphas": 0
	    }],
	    "chartCursor": {
	        "cursorAlpha": 0,
	        "zoomable": false
	    },
	    "categoryField": "day",
	    "categoryAxis": {
	        "gridPosition": "start",
	        "axisAlpha": 0,
	        "fillAlpha": 0.05,
	        "fillColor": "#000000",
	        "gridAlpha": 0,
	        "position": "top"
	    },
	    "export": {
	    	"enabled": true,
	        "position": "bottom-right"
	     }
	});
 }
  </script> 
  
    
 <script type="text/javascript">
  $( function() {
	  
	  
	  $('#dpfromdate').datepicker({
	      dateFormat: 'yy-mm-dd',
	      changeMonth: true,
	  	  changeYear: true, 
	  	  onSelect : function(selected) {
				var dt = new Date(selected);
				dt.setDate(dt.getDate() + 1);
				$("#dptodate").datepicker("option", "minDate", dt);
			}
	      //minDate: 0  this line for to restrict selection of previous dates
	  });
	  
	  $('#dptodate').datepicker({
	      dateFormat: 'yy-mm-dd',
	      changeMonth: true,
	  	  changeYear: true, 
	  	   onSelect : function(selected) {
			var dt = new Date(selected);
			dt.setDate(dt.getDate() - 1);
			$("#dpfromdate").datepicker("option", "maxDate", dt);
	  	}
	      //minDate: 0  this line for to restrict selection of previous dates
	  });
	  

	  $('#portletfromdate').datepicker({
	      dateFormat: 'yy-mm-dd',
	      changeMonth: true,
	  	  changeYear: true, 
	  	  onSelect : function(selected) {
				var dt = new Date(selected);
				dt.setDate(dt.getDate() + 1);
				$("#portlettodate").datepicker("option", "minDate", dt);
			}
	      //minDate: 0  this line for to restrict selection of previous dates
	  });
	  
	  $('#portlettodate').datepicker({
	      dateFormat: 'yy-mm-dd',
	      changeMonth: true,
	  	  changeYear: true, 
		  onSelect : function(selected) {
				var dt = new Date(selected);
				dt.setDate(dt.getDate() - 1);
				$("#portletfromdate").datepicker("option", "maxDate", dt);
		  	}
	      //minDate: 0  this line for to restrict selection of previous dates
	  });
	  
	  $('#serialfromdate').datepicker({
	      dateFormat: 'yy-mm-dd',
	      changeMonth: true,
	  	  changeYear: true, 
		  onSelect : function(selected) {
				var dt = new Date(selected);
				dt.setDate(dt.getDate() + 1);
				$("#serialtodate").datepicker("option", "minDate", dt);
			}
	      //minDate: 0  this line for to restrict selection of previous dates
	  });
	  
	  $('#serialtodate').datepicker({
	      dateFormat: 'yy-mm-dd',
	      changeMonth: true,
	  	  changeYear: true, 
	  	  onSelect : function(selected) {
			var dt = new Date(selected);
			dt.setDate(dt.getDate() - 1);
			$("#serialfromdate").datepicker("option", "maxDate", dt);
	  	}
	      //minDate: 0  this line for to restrict selection of previous dates
	  });
	  
	  
	  $('#portletreferByMefromdate').datepicker({
	      dateFormat: 'yy-mm-dd',
	      changeMonth: true,
	  	  changeYear: true, 
	  	  onSelect : function(selected) {
				var dt = new Date(selected);
				dt.setDate(dt.getDate() + 1);
				$("#portlettodate").datepicker("option", "minDate", dt);
			}
	      //minDate: 0  this line for to restrict selection of previous dates
	  });
	  
	  $('#portletreferByMetodate').datepicker({
	      dateFormat: 'yy-mm-dd',
	      changeMonth: true,
	  	  changeYear: true, 
		  onSelect : function(selected) {
				var dt = new Date(selected);
				dt.setDate(dt.getDate() - 1);
				$("#portletfromdate").datepicker("option", "maxDate", dt);
		  	}
	      //minDate: 0  this line for to restrict selection of previous dates
	  });
	  
	  
      
  });
</script>

<script>
// Back To Top 
    $(window).scroll(function() {
        if ($(this).scrollTop() > 200) {
            $('.backtotop').fadeIn('slow');
        } else {
            $('.backtotop').fadeOut('slow');
        }
    });
    $('.backtotop').click(function() {
        $("html, body").animate({scrollTop: 0}, 500);
        return false;
    });
</script>