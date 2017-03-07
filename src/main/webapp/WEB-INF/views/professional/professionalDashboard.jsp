<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Dashboard</h2>
      </div>
    </section>
    
    <!-- Profile Content -->
				<section class="row mt">
				        <div class="col-md-12">
				          <div class="dashboard-widget crowd_dashboard">
				            <h4 class="pull-left">Interviews <span class="dashboard-datepkr dashboarddatepkr dashboarddatepkr">
				            <input type="text" class="input-sm" id="professionalInterviewdate">
				            <i class="fa fa-calendar"></i></span></h4>
				            <ul class="nav nav-tabs ml">
				              <li class="active" id="todayTab"><a data-toggle="tab" href="javascript:void(0)" onclick="flushDate(); interviews('today')">Today</a></li>
				              <li id="upcomingTab"><a data-toggle="tab" href="javascript:void(0)" onclick="flushDate(); interviews('upcoming')">Upcoming</a></li>
				              <li id="previousTab"><a data-toggle="tab" href="javascript:void(0)" onclick="flushDate(); interviews('previous')">Previous</a></li>
				            </ul>
				            <div class="clearfix"></div>
				           <div class="tab-content clearfix require_wrap_table">
				               <div id="today" class="tab-pane fade in active"> </div>
	                		   <div id="upcoming" class="tab-pane fade in"> </div>
                			   <div id="previous" class="tab-pane fade in"> </div>
	           				</div>
	          			</div>
	               </div>
	       </section>
				      
				      
    <!-- Profile Content --> 
  </section>
<script>
function flushDate(){
	$("#datepicker-int").val('');
}
</script>
 <script>
window.onload = interviews('today');
function interviews(dateRange){
	var resultToday ="";
	var sourceleadUrl = '${sourceleadUrl}';
	//$("#today").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
		$.ajax({
			type : "GET",
			url : sourceleadUrl+"/getProfessionalInterviews?dateRange="+dateRange,
			success : function(data) {
				var json = JSON.parse(data);
				if(data != null && data != 'undefined' && data != "" && data != '[]'){
              
	              			$("#today").empty();
	        	        	$("#upcoming").empty();
	        	        	$("#previous").empty();
	              			$("#today").removeAttr("style");
	              			if(json.length > 0){
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
	            			}
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
					}else{
	               	 	$("#today").removeAttr("style");
	               	 	$("#today").empty();
     	        		$("#upcoming").empty();
     	        		$("#previous").empty();
	 	    	        var result ="";
						result = result+"			<div class='dataTables_empty'>";
						result = result+"			 </div>";
					    $("#today").append(result); 
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
            "sSearch": ""
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
 
<jsp:include page="../masterFooter.jsp" />