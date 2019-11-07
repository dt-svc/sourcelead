<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



 <!-- Inteviews -->
      <div class="col-md-6 mb" >
        <div class="dashboard-widget height400px">
          <h4 class="clearfix">Interviews
        <b class="dashboard-datepkr"><input type="text" class="input-sm" id="datepicker-int" readonly="readonly"><i class="fa fa-calendar"></i></b>  
          </h4>
          <!-- <a href="javascript:void(0)" class="collpase-div pull-right" title="SlideUp/SlideDown"><i aria-hidden="true" class="fa fa-angle-down"></i></a> -->
          <div class="clearfix require_wrap_table dashboard-data filter_position">
            <ul class="nav nav-tabs">
              <li class="active" id="todayTab"><a data-toggle="tab" onclick="flushDate(); interviews('today')">Today</a></li>
              <li id="upcomingTab"><a data-toggle="tab" onclick="flushDate(); interviews('upcoming')">Upcoming</a></li>
              <li id="previousTab"><a data-toggle="tab" onclick="flushDate(); interviews('previous')">Previous</a></li>
            </ul>
            <div class="tab-content clearfix">
              <div id="today" class="tab-pane fade in active"></div>
              <div id="upcoming" class="tab-pane fade"></div>
           	  <div id="previous" class="tab-pane fade"></div>
            </div>
          </div>
        </div>
      </div>
<!-- Inteviews Ends --> 
 
<script>
function flushDate(){
	$("#datepicker-int").val('');
}
</script>
     
<script>
window.onload = interviews('today');
function interviews(dateRange){
	var orgATS = '${SourceLeadWeb_ats}';
	var resultToday ="";
	var sourceleadUrl = '${SourceLeadWeb_sourceleadUrl}';
	//$("#today").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		$.ajax({
			type : "GET",
			url : sourceleadUrl+"/getInterviews?dateRange="+dateRange,
			success : function(data) {
				var json = JSON.parse(data);
				/* if(data != null && data != 'undefined' && data != "" && data != '[]'){ */
              
	        	        	$("#upcoming").empty();
	        	        	$("#previous").empty();
	        	        	$("#today").empty();
	              			$("#today").removeAttr("style");
	              			//if(json.length > 0){
	            				resultToday = resultToday+'	<table id="requirementInterviewsPagination" class="display" cellspacing="0" width="99.5%">';
	            				resultToday = resultToday+'	<thead>';
	            				resultToday = resultToday+'	  <tr>';
	            				resultToday = resultToday+'	    <th>Applicant</th>';
	            				resultToday = resultToday+'	    <th>Job</th>';
	            				resultToday = resultToday+'	    <th>Date &amp; Time</th>';
	            				if(orgATS == 'Y'){
	            				resultToday = resultToday+'	    <th>Client</th>';
	            				}
	            				resultToday = resultToday+'	    <th>Action</th>';
	            				resultToday = resultToday+'	  </tr>';
	            				resultToday = resultToday+'	</thead>';
	            				resultToday = resultToday+'	<tbody>';
	            				
	            				for (var i=0; i<json.length; i++){
	            		       		var report = json[i];
	            		       		var clientName = "---";
	            		       		if(report.clientName != null && report.clientName != "" && report.clientName != 'undefined' && report.interviewSourceType != 'INTERVIEW'){
	            		       			clientName = report.clientName;
	            		       		}
	            						resultToday = resultToday+'	  <tr>';
	            						resultToday = resultToday+'	   <td><div><span title="'+report.candidateName+'">'+report.candidateName+'</span></div></td>';
	            						resultToday = resultToday+'	   <td><div class="tbldata-dotdot-vsm"> <a href="'+sourceleadUrl+'/requirementSummary?requirementUUID='+report.requirementUUID+'"><span title ="'+report.requirementTitle+'">'+report.requirementTitle+'</span></a></div></td>';
	            						resultToday = resultToday+'	   <td><div class="tblminwidth-150"><span title="'+report.interviewStartDateTimeStr+'">'+report.interviewStartDateTimeStr+'</span></div></td>';
	            						if(orgATS == 'Y'){
	            							resultToday = resultToday+'	   <td><div><span title="'+clientName+'">'+clientName+'</span></div></td>';
	            						}

	            						
                                       if(report.interviewerId != null  &&  report.interviewerId !='' && report.interviewerId !='undefined'){
	            						  resultToday = resultToday+'	 <td><a  href="'+sourceleadUrl+'/'+'dashboardInterviewFeedBackTemplate?templateId='+report.interviewTemplateid+'&requirementInterviewid='+report.interviewerId+'">FeedBack</a></td>';
                                       }else{
                                           resultToday = resultToday+'	 <td>---</td>';
                                        }
                                        resultToday = resultToday+'	 </tr>';
	            					}
	            					resultToday = resultToday+'	 </tbody>';
	            					resultToday = resultToday+'	</table>';											
	            			//}
	            	        if(dateRange == 'today'){
	            	        	$("#today").addClass("tab-pane fade in active");
	            				$("#upcoming").removeClass("tab-pane fade in active");
	            				$("#previous").removeClass("tab-pane fade in active");
	            	            $("#today").append(resultToday);
	            	            requirementInterviewsPagination();
	            	        }else if(dateRange == 'previous'){
	            	        	$("#today").removeClass("tab-pane fade in active");
	            				$("#upcoming").removeClass("tab-pane fade in active");
	            				$("#previous").addClass("tab-pane fade in active");
	            				$("#previous").append(resultToday);
	            				requirementInterviewsPagination();
	            	        }else if(dateRange == 'upcoming'){
	            	        	$("#today").removeClass("tab-pane fade in active");
	            				$("#previous").removeClass("tab-pane fade in active");
	            				$("#upcoming").addClass("tab-pane fade in active");
	            				$("#upcoming").append(resultToday);
	            				requirementInterviewsPagination();
	            	        }else{
	            	        	$("#todayTab").removeClass("active");
	            				$("#upcomingTab").removeClass("active");
	            				$("#previousTab").removeClass("active");
	            	        	$("#today").append(resultToday);
	            	            requirementInterviewsPagination();
	            	        }
					/* }else{
	               	 	$("#today").removeAttr("style");
	               	 	$("#today").empty();
     	        		$("#upcoming").empty();
     	        		$("#previous").empty();
	 	    	        var result ="";
						result = result+"			<div class='blank-pages'>";
						result = result+"			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
						result = result+"			        <p>No Interviews Found</p>";
						result = result+"			 </div>";
					    $("#today").append(result); 
	               } */
			},
	});
}
</script>

<script>
$('#requirementInterviewsPagination').DataTable( {
	"scrollX": true,
	"autoWidth": false,
});
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
   $( "#datepicker-int" ).datepicker({
	   dateFormat : 'dd-mm-yy',
	   changeMonth: true,
       changeYear: true,
       onSelect: function(){
    	    var dt = $(this).val();
    	    interviews(dt);
   		}
   });
 });
 </script>
