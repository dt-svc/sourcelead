 <!-- Timesheets -->
      <div class="col-md-6">
        <div class="clients_wrap_table dashboard-widget dashboard-widget-icons">
          <h4 class="clearfix">Timesheets &nbsp;&nbsp;<!-- <a href="#" title="Calendar"><i aria-hidden="true" class="fa fa-calendar"></i></a> --></h4>
          <!-- <a href="javascript:void(0)" class="collpase-div pull-right" title="SlideUp/SlideDown"><i aria-hidden="true" class="fa fa-angle-down"></i></a> -->
          <div class="clearfix require_wrap_table dashboard-data mt">
            <div class="db-widget-sm row">
              <div class="col-xs-12 col-sm-3">
                <div class="info-box"> <span class="info-box-icon bg-orange"> <i class="fa fa-floppy-o" aria-hidden="true"></i> </span>
                  <div class="info-box-content text-center">
                    <!-- <p class="info-box-text"><a href="/sourcelead/totalTimesheetList/NEW">Saved</a></p> -->
                     <p class="info-box-text"><a href="#">Saved</a></p>
                    <p class="info-box-number"><span id="savedTimesheetsSubmissionsCount"></span></p>
                  </div>
                </div>
              </div>
              <div class="col-xs-12 col-sm-3">
                <div class="info-box"> <span class="info-box-icon bg-blue"> <i class="fa fa-clock-o" aria-hidden="true"></i> </span>
                  <div class="info-box-content text-center">
                   <!--  <p class="info-box-text"><a href="/sourcelead/totalTimesheetList/SUBMITTED">Waiting</a></p> -->
                    <p class="info-box-text"><a href="#">Waiting</a></p>
                    <p class="info-box-number"><strong><span  id="waitingTimesheetsSubmissionsCount"></span></strong></p>
                  </div>
                </div>
              </div>
              <div class="col-xs-12 col-sm-3">
                <div class="info-box"> <span class="info-box-icon bg-green"> <i class="fa fa-check-circle-o" aria-hidden="true"></i> </span>
                  <div class="info-box-content text-center">
                   <!--  <p class="info-box-text"><a href="/sourcelead/totalTimesheetList/APPROVED">Approved</a></p> -->
                    <p class="info-box-text"><a href="#">Approved</a></p>
                    <p class="info-box-number"><strong><span id="approvalTimesheetsSubmissionsCount"></span></strong></p>
                  </div>
                </div>
              </div>
              <div class="col-xs-12 col-sm-3">
                <div class="info-box"> <span class="info-box-icon bg-red"> <i class="fa fa-ban" aria-hidden="true"></i> </span>
                  <div class="info-box-content text-center">
                   <!--  <p class="info-box-text"><a href="/sourcelead/totalTimesheetList/REJECTED">Rejected</a></p> -->
                    <p class="info-box-text"><a href="#">Rejected</a></p>
                    <p class="info-box-number"><strong><span id="rejectedTimesheetsSubmissionsCount"></span></strong></p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <!-- Pagenation Ends --> 
      </div>
      <!-- Timesheets ends --> 
      
<script> 
$(document).ready(function(){
	
	timeSheetSubmissionStatusCount();
	
});
</script>
<script type="text/javascript">
function timeSheetSubmissionStatusCount(){
    var sourceleadUrl ='${sourceleadUrl}';
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+"/getTimeSheetSubmissionStatus", 
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	
		        	var  savedTimesheetsSubmissionsCount = json.savedTimesheetSubmissionCount;
		        	var  waitingTimesheetsSubmissionsCount =json.waitingForApprovalTimesheetSubmissionCount;
		        	var  approvalTimesheetsSubmissionsCount =json.approvedTimesheetSubmissionCount;
		        	var  rejectedTimesheetsSubmissionsCount = json.rejectedTimesheetSubmissionCount; 
		        	
		        	if(savedTimesheetsSubmissionsCount>0){
	                      $("#savedTimesheetsSubmissionsCount").text(savedTimesheetsSubmissionsCount);
	                  }else{
	                      $("#savedTimesheetsSubmissionsCount").html("0");
	                      $("#savedTimesheetsSubmissionsCount").removeAttr("href");
	                  }
		        	if(waitingTimesheetsSubmissionsCount>0){
	                      $("#waitingTimesheetsSubmissionsCount").text(waitingTimesheetsSubmissionsCount);
	                  }else{
	                      $("#waitingTimesheetsSubmissionsCount").html("0");
	                      $("#waitingTimesheetsSubmissionsCount").removeAttr("href");
	                  }
		        	if(approvalTimesheetsSubmissionsCount>0){
	                      $("#approvalTimesheetsSubmissionsCount").text(approvalTimesheetsSubmissionsCount);
	                  }else{
	                      $("#approvalTimesheetsSubmissionsCount").html("0");
	                      $("#approvalTimesheetsSubmissionsCount").removeAttr("href");
	                  }
		        	if(rejectedTimesheetsSubmissionsCount>0){
	                      $("#rejectedTimesheetsSubmissionsCount").text(rejectedTimesheetsSubmissionsCount);
	                  }else{
	                      $("#rejectedTimesheetsSubmissionsCount").html("0");
	                      $("#rejectedTimesheetsSubmissionsCount").removeAttr("href");
	                  }
		        	
		 		}
		  });
		 
	}

</script>      