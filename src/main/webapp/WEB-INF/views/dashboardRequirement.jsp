

<script> 
$(document).ready(function(){
	
	requirementSubmissionsCount("today");
	
});
</script>
<div class="col-md-6">
        <div class="dashboard-widget dashboard-widget-icons">
          <h4 class="clearfix">Requirement &nbsp;&nbsp;<!-- <a href="#" title="Calendar"><i aria-hidden="true" class="fa fa-calendar"></i></a> --></h4>
          <!-- <a href="javascript:void(0)" class="collpase-div pull-right" title="SlideUp/SlideDown"><i aria-hidden="true" class="fa fa-angle-down"></i></a> -->
          <div class="clearfix require_wrap_table dashboard-data mt">
            <ul class="nav nav-tabs">
              <li class="active"><a data-toggle="tab" onclick="requirementSubmissionsCount('today');" href="#today-req">Today</a></li>
              <li><a data-toggle="tab" onclick="requirementSubmissionsCount('previous');" href="#prev-req">Previous</a></li>
            </ul>
            <div class="tab-content clearfix">
              <div id="today-req" class="tab-pane fade in active">
                <div class="db-widget-sm row">
                  <div class="col-xs-12 col-sm-3 mb">
                    <div class="info-box"> <span class="info-box-icon bg-red"> <i class="fa fa-check-square" aria-hidden="true"></i> </span>
                      <div class="info-box-content text-center">
                        <p class="info-box-number"><span id="submissionsCount">${submissionsCount}</span></p>
                        <p class="info-box-text"> <a href="timesheet-status.html">Submission</a> </p>
                      </div>
                    </div>
                  </div>
                  <div class="col-xs-12 col-sm-3 mb">
                    <div class="info-box"> <span class="info-box-icon bg-green"> <i class="fa fa-black-tie" aria-hidden="true"></i> </span>
                      <div class="info-box-content text-center">
                        <p class="info-box-number"><span id="applicantsCount"></span></p>
                        <p class="info-box-text"> <a href="timesheet-status.html">Applicants</a> </p>
                      </div>
                    </div>
                  </div>
                  <div class="col-xs-12 col-sm-3 mb">
                    <div class="info-box"> <span class="info-box-icon bg-blue"> <i class="fa fa-graduation-cap" aria-hidden="true"></i> </span>
                      <div class="info-box-content text-center">
                        <p class="info-box-number"><span id="interviewsCount"></span></p>
                        <p class="info-box-text"><a href="timesheet-status.html">Interviews</a></p>
                      </div>
                    </div>
                  </div>
                  <div class="col-xs-12 col-sm-3 mb">
                    <div class="info-box"> <span class="info-box-icon bg-orange"> <i class="fa fa-tags" aria-hidden="true"></i> </span>
                      <div class="info-box-content text-center">
                        <p class="info-box-number"><span id="offersCount"></span></p>
                        <p class="info-box-text"><a href="timesheet-status.html">Offers</a></p>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              
            </div>
          </div>
        </div>
        <!-- Requirement ends --> 
      </div>
      
      
      
      <script type="text/javascript">


function requirementSubmissionsCount(daterange){
    var sourceleadUrl ='${sourceleadUrl}';
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+"/getRequirementSubmissionsCount?${_csrf.parameterName}=${_csrf.token}&daterange="+daterange, 
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	 
		        	 var  submissionsCount = json.submissionCount;
		        	 var  applicantsCount =json.applicantsCount;
		        	 var  interviewsCount =json.interviewsCount;
		        	 var  offersCount = json.offersCount; 
		        	 
		        	 if(submissionsCount>0){
	                      $("#submissionsCount").text(submissionsCount);
	                  }else{
	                      $("#submissionsCount").html("0");
	                      $("#submissionsCount").removeAttr("href");
	                  } 
		        	 if(applicantsCount>0){
	                      $("#applicantsCount").text(applicantsCount);
	                  }else{
	                      $("#applicantsCount").html("0");
	                      $("#applicantsCount").removeAttr("href");
	                  }
		        	if(interviewsCount>0){
	                      $("#interviewsCount").text(interviewsCount);
	                  }else{
	                      $("#interviewsCount").html("0");
	                      $("#interviewsCount").removeAttr("href");
	                  }
		        	if(offersCount>0){
	                      $("#offersCount").text(offersCount);
	                  }else{
	                      $("#offersCount").html("0");
	                      $("#offersCount").removeAttr("href");
	                  } 
		        	
		 		}
		  });
		 
	}

</script>