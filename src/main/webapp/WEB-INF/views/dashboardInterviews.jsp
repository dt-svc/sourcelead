 <!-- Inteviews -->
      <div class="col-md-6">
        <div class="clients_wrap_table dashboard-widget">
          <h4 class="pull-left">Interviews &nbsp;&nbsp;<a href="#" class="pull-right" title="Calendar"><i aria-hidden="true" class="fa fa-calendar"></i></a></h4>
          <a href="javascript:void(0)" class="collpase-div pull-right" title="SlideUp/SlideDown"><i aria-hidden="true" class="fa fa-angle-down"></i></a>
          <div class="clearfix require_wrap_table dashboard-data">
            <ul class="nav nav-tabs">
              <li class="active"><a data-toggle="tab" href="javascript:void(0)" onclick="interviews('today')">Today</a></li>
              <li><a data-toggle="tab" href="javascript:void(0)" onclick="interviews('upcoming')">Upcoming</a></li>
              <li><a data-toggle="tab" href="javascript:void(0)" onclick="interviews('previous')">Previous</a></li>
            </ul>
            <div class="tab-content clearfix mt">
              <div id="today" class="tab-pane fade in active"></div>
              <div id="upcoming" class="tab-pane fade"></div>
           	  <div id="previous" class="tab-pane fade"></div>
            </div>
          </div>
        </div>
      </div>
<!-- Inteviews Ends --> 
 
      
<script>
window.onload = interviews('today');
//window.setInterval("interviews('today');", 60000);
function interviews(dateRange){
	var resultToday ="";
	var sourceleadUrl = '${sourceleadUrl}';
	$("#today").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
		$.ajax({
			type : "GET",
			url : sourceleadUrl+"/getInterviews?dateRange="+dateRange,
			success : function(data) {
				var json = JSON.parse(data);
				if(data != null && data != 'undefined' && data != "" && data != '[]'){
              		 if( data.indexOf('Sign In') >= 0){
			    		location.replace(sourceleadUrl);
			    	 }else if (data.indexOf('exception') >= 0) {
              			 location.replace(sourceleadUrl+"/errorPage");
              		 }else{
	              			$("#today").empty();
	        	        	$("#upcoming").empty();
	        	        	$("#previous").empty();
	              			$("#today").removeAttr("style");
	              			if(json.length > 0){
	            				resultToday = resultToday+'	<table id="requirementInterviewsPagination" class="display" cellspacing="0" width="100%">';
	            				resultToday = resultToday+'	<thead>';
	            				resultToday = resultToday+'	  <tr>';
	            				resultToday = resultToday+'	    <th>Applicant</th>';
	            				resultToday = resultToday+'	    <th>Requirement</th>';
	            				resultToday = resultToday+'	    <th>Date &amp; Time</th>';
	            				resultToday = resultToday+'	    <th>Client</th>';
	            				resultToday = resultToday+'	  </tr>';
	            				resultToday = resultToday+'	</thead>';
	            				resultToday = resultToday+'	<tbody>';
	            				
	            				for (var i=0; i<json.length; i++){
	            		       		var report = json[i];
	            						resultToday = resultToday+'	  <tr>';
	            						resultToday = resultToday+'	   <td><div><span>'+report.candidateName+'</span></div></td>';
	            						resultToday = resultToday+'	   <td><div class="tbldata-dotdot-vsm"> <a href="'+sourceleadUrl+'/requirementSummary?requirementId='+report.requirementId+'">'+report.requirementTitle+'</a></div></td>';
	            						resultToday = resultToday+'	   <td><div><span>'+report.interviewStartDateTimeStr+'</span></div></td>';
	            						resultToday = resultToday+'	   <td><div><span>'+report.clientName+'</span></div></td>';
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
	            	        }
	            			if(dateRange == 'previous'){
	            				$("#previous").append(resultToday);
	            				requirementInterviewsPagination();
	            				$("#today").removeClass("tab-pane fade in active");
	            				$("#upcoming").removeClass("tab-pane fade in active");
	            				$("#previous").addClass("tab-pane fade in active");
	            	        }
	            			if(dateRange == 'upcoming'){
	            				$("#upcoming").append(resultToday);
	            				requirementInterviewsPagination();
	            				$("#today").removeClass("tab-pane fade in active");
	            				$("#previous").removeClass("tab-pane fade in active");
	            				$("#upcoming").addClass("tab-pane fade in active");
	            	        }
              		 	}
					}else{
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
      