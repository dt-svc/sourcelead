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
              <div id="today" class="tab-pane fade in active">
           
              </div>
              <div id="upcoming" class="tab-pane fade">
           
              </div>
           	  <div id="previous" class="tab-pane fade">
           
              </div>
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
		$.ajax({
			type : "GET",
			url : sourceleadUrl+"/getInterviews?dateRange="+dateRange,
			success : function(data) {
				$("#today").empty();
	        	$("#upcoming").empty();
	        	$("#previous").empty();
				var json = JSON.parse(data);

				/*  if( result.indexOf('Sign In') >= 0){
		    		location.replace(sourceleadUrl);
		    	 }
        		 else if (result.indexOf('exception') >= 0) {
        			 location.replace(sourceleadUrl+"/errorPage");
        		 }
        		 else{
			 		$("#popUpDivResume").removeClass("edit-details-active");
			 		candidateResumesList(candidateId);
        		 } */


			if(json.length > 0){
				resultToday = resultToday+'	<table>';
				resultToday = resultToday+'	<thead>';
				resultToday = resultToday+'	  <tr>';
				resultToday = resultToday+'	    <th>Applicant</th>';
				resultToday = resultToday+'	    <th>Requirement</th>';
				resultToday = resultToday+'	    <th>Date &amp; Time</th>';
				resultToday = resultToday+'	    <th>Client</th>';
				resultToday = resultToday+'	  </tr>';
				resultToday = resultToday+'	</thead>';
				resultToday = resultToday+'	<tbody>';
				
				for (var i=0; i<json.length; i++)
					{
		       		var report = json[i];
						resultToday = resultToday+'	  <tr>';
						resultToday = resultToday+'	   <td><div><span>'+report.candidateName+'</span></div></td>';
						resultToday = resultToday+'	   <td><div class="tbldata-dotdot-vsm">'+report.requirementTitle+'</div></td>';
						resultToday = resultToday+'	   <td><div><span>'+report.interviewStartDateTimeStr+'</span></div></td>';
						resultToday = resultToday+'	   <td><div><span>'+report.clientName+'</span></div></td>';
						resultToday = resultToday+'	 </tr>';
					}
				
					resultToday = resultToday+'	 </tbody>';
					resultToday = resultToday+'	</table>';											
	      
			      resultToday = resultToday+'	<nav class="clearfix">';
			      resultToday = resultToday+'	  <ul id="pagincg" class="pagination pull-right">';
			      resultToday = resultToday+'	  <li><a href="#">Previous</a></li>';
			      resultToday = resultToday+'	  <li class="active"><a href="#">1</a></li>';
			      resultToday = resultToday+'	  <li><a href="#">2</a></li>';
			      resultToday = resultToday+'	  <li><a href="#">3</a></li>';
			      resultToday = resultToday+'	  <li><a href="#">4</a></li>';
			      resultToday = resultToday+'	  <li><a href="#">5</a></li>';
			      resultToday = resultToday+'	  <li data-page="last"><a href="#">Next</a></li>';
			      resultToday = resultToday+'	</ul>';
			      resultToday = resultToday+'	</nav>';
			}
		     
	        if(dateRange == 'today'){
	            $("#today").append(resultToday);
	        }
			if(dateRange == 'previous'){
				$("#previous").append(resultToday);
				$("#today").removeClass("tab-pane fade in active");
				$("#upcoming").removeClass("tab-pane fade in active");
				$("#previous").addClass("tab-pane fade in active");
	        }
			if(dateRange == 'upcoming'){
				$("#upcoming").append(resultToday);
				$("#today").removeClass("tab-pane fade in active");
				$("#previous").removeClass("tab-pane fade in active");
				$("#upcoming").addClass("tab-pane fade in active");
	        }
      
		},
	});
	return true;
}
</script>


      