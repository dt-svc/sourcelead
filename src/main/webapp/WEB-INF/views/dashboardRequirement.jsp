<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">

<script> 
$(document).ready(function(){
	requirementSubmissionsCount("today");
});
</script>
<div class="col-md-6">
        <div class="dashboard-widget dashboard-widget-icons">
          <h4 class="clearfix">Jobs &nbsp;&nbsp;<!-- <a href="#" title="Calendar"><i aria-hidden="true" class="fa fa-calendar"></i></a> --></h4>
          <!-- <a href="javascript:void(0)" class="collpase-div pull-right" title="SlideUp/SlideDown"><i aria-hidden="true" class="fa fa-angle-down"></i></a> -->
          <div class="clearfix dashboard-data mt">
            <ul class="nav nav-tabs">
              <li class="active"><a data-toggle="tab" onclick="requirementSubmissionsCount('today');" href="#today-req">Current</a></li>
              <li><a data-toggle="tab" onclick="requirementSubmissionsCount('previous');" href="#prev-req">Previous</a></li>
                   <input type="hidden" name="dateActiveTab" id="dateActiveTab" value="today"/>
            </ul>
            <div class="tab-content clearfix">
              <div id="today-req" class="tab-pane fade in active">
                <div class="db-widget-sm row">
                <div class="col-xs-12 col-sm-3">
                    <div class="info-box"> <span class="info-box-icon bg-green"> <i class="fa fa-black-tie" aria-hidden="true"></i> </span>
                      <div class="info-box-content text-center" style='cursor:pointer' title="Applicants" id="appDiv">
                        <p class="info-box-number"><span id="applicantsCount" onclick="loadRequirementApplicants();"></span></p>
                        <p class="info-box-text">Applicants</p>
                      </div>
                    </div>
                  </div>
                  <div class="col-xs-12 col-sm-3">
                    <div class="info-box"> <span class="info-box-icon bg-red"> <i class="fa fa-check-square" aria-hidden="true"></i> </span>
                      <div class="info-box-content text-center" style='cursor:pointer' title="Submission" id="subDiv">
                        <p class="info-box-number"><span id="submissionsCount" onclick="loadRequirementSubmissions();">${submissionsCount}</span></p>
                        <p class="info-box-text">Submissions</p>
                      </div>
                    </div>
                  </div>
                  <div class="col-xs-12 col-sm-3">
                    <div class="info-box"> <span class="info-box-icon bg-blue"> <i class="fa fa-graduation-cap" aria-hidden="true"></i> </span>
                      <div class="info-box-content text-center" style='cursor:pointer' title="Interviews" id="intDiv">
                        <p class="info-box-number"><span id="interviewsCount" onclick="loadRequirementInterviews();"></span></p>
                        <p class="info-box-text">Interviews</p>
                      </div>
                    </div>
                  </div>
                  <div class="col-xs-12 col-sm-3">
                    <div class="info-box"> <span class="info-box-icon bg-orange" > <i class="fa fa-tags" aria-hidden="true"></i> </span>
                      <div class="info-box-content text-center" style='cursor:pointer' title="Offers" id="offDiv">
                        <p class="info-box-number"><span id="offersCount" onclick="loadRequirementOffers();"></span></p>
                        <p class="info-box-text">Offers</p>
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
function loadRequirementApplicants(){
	var tab = $("#dateActiveTab").val();
	var sourceleadUrl = '${sourceleadUrl}';
	var orgLocId = '${SourceLeadWeb_orgLocationId}';
	window.location.href = sourceleadUrl + "/requirementApplicantsList?&tab="+tab+"&orgLocationId="+orgLocId;
}
function loadRequirementSubmissions(){
	var tab = $("#dateActiveTab").val();
	var sourceleadUrl = '${sourceleadUrl}';
	var orgLocId = '${SourceLeadWeb_orgLocationId}';
	window.location.href = sourceleadUrl + "/requirementSubmissionsList?&tab="+tab+"&orgLocationId="+orgLocId;;
}
function loadRequirementInterviews(){
	var tab = $("#dateActiveTab").val();
	var sourceleadUrl = '${sourceleadUrl}';
	var orgLocId = '${SourceLeadWeb_orgLocationId}';
	window.location.href = sourceleadUrl + "/requirementInterviewsList?&tab="+tab+"&orgLocationId="+orgLocId;;
}
function loadRequirementOffers(){
	var tab = $("#dateActiveTab").val();
	var sourceleadUrl = '${sourceleadUrl}';
	var orgLocId = '${SourceLeadWeb_orgLocationId}';
	window.location.href = sourceleadUrl + "/requirementOffersList?&tab="+tab+"&orgLocationId="+orgLocId;;
}
</script>     
<script type="text/javascript">
function requirementSubmissionsCount(daterange){
	$("#dateActiveTab").val(daterange); 
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
	                      $('#submissionsCount').attr("onClick", "loadRequirementSubmissions(); return false;"); 
	                      $('#subDiv').attr("style", "cursor:pointer"); 
	                  }else{
	                      $("#submissionsCount").html("0");
	                      $("#submissionsCount").removeAttr("onclick");
	                      $("#subDiv").removeAttr("style");
	                  } 
		        	 if(applicantsCount>0){
	                      $("#applicantsCount").text(applicantsCount);
	                      $('#applicantsCount').attr("onClick", "loadRequirementApplicants(); return false;"); 
	                      $('#appDiv').attr("style", "cursor:pointer"); 
	                  }else{
	                      $("#applicantsCount").html("0");
	                      $("#applicantsCount").removeAttr("onclick");
	                      $("#appDiv").removeAttr("style");
	                  }
		        	if(interviewsCount>0){
	                      $("#interviewsCount").text(interviewsCount);
	                      $('#interviewsCount').attr("onClick", "loadRequirementInterviews(); return false;"); 
	                      $('#intDiv').attr("style", "cursor:pointer"); 
	                  }else{
	                      $("#interviewsCount").html("0");
	                      $("#interviewsCount").removeAttr("onclick");
	                      $("#intDiv").removeAttr("style");
	                  }
		        	if(offersCount>0){
	                      $("#offersCount").text(offersCount);
	                      $('#offersCount').attr("onClick", "loadRequirementOffers(); return false;"); 
	                      $('#offDiv').attr("style", "cursor:pointer"); 
	                  }else{
	                      $("#offersCount").html("0");
	                      $("#offersCount").removeAttr("onclick");
	                      $("#offDiv").removeAttr("style");
	                  } 
		        	
		 		}
		  });
		 
	}

</script>