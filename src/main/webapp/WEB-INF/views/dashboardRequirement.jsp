<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script> 
$(document).ready(function(){
	var jsondata = sessionStorage.getItem('jobsCount');
     if(jsondata!= null){
		 jsondata =jsondata.split(","); 
		 $("#submissionsCount").text(jsondata[0]);
		 $("#applicantsCount").text(jsondata[1]);
		 $("#interviewsCount").text(jsondata[2]);
		 $("#offersCount").text(jsondata[3]);
		 sessionStorage.clear(); 
	 }else{
		 	requirementSubmissionsCount("today");
	 }
});
</script>
<div class="col-md-6 mb10">
        <div class="dashboardWidget-new dashNewWrap mb">
            
        	<div class="dashNewWrapHead clearfix">
        		<a class="pull-left" id="jobsLink" href="<c:url value="/app/jobs"/>">Jobs
        			<a class="moreLink" href="${sourceleadUrl}/requirementApplicantsList?&tab=previous&orgLocationId=${SourceLeadWeb_orgLocationId}" title="View more"  id="viewmoresDiv"> View More..</a>
        		</a>
	            <ul class="nav nav-tabs pull-right">
	              	<li class="active"><a data-toggle="tab" onclick="requirementSubmissionsCount('today');">Today</a></li>
	              	<li><a data-toggle="tab" onclick="requirementSubmissionsCount('previous');">1 Month</a></li>
	              	<input type="hidden" name="dateActiveTab" id="dateActiveTab" value="today"/>
	            </ul>
        	</div>            
            
            <div class="db-widget-sm">
                <div class="col-xs-12 col-sm-3">
                    <div class="dashNewCount" title="Applicants" id="appDiv">
                        <p>Applicants</p>
                        <p class="NewCount"><span id="applicantsCount" onclick="loadRequirementApplicants();"></span></p>
                      	<div id ="applicantsCountLoading"></div>
                    </div>
                  </div>
                  
                  <div class="col-xs-12 col-sm-3">
                    <div class="dashNewCount" title="Client Submissions" id="subDiv">
                        <p>Client Submissions</p>
                        <p class="NewCount suColor"><span id="submissionsCount" onclick="loadRequirementSubmissions();">${submissionsCount}</span></p>
                      <div id ="submissionsCountLoading"></div>
                    </div>
                  </div>
                  
                  <div class="col-xs-12 col-sm-3">
                    <div class="dashNewCount" title="Interviews" id="intDiv">
                        <p>Interviews</p>
                        <p class="NewCount deColor"><span id="interviewsCount" onclick="loadRequirementInterviews();"></span></p>
                      	<div id ="interviewsCountLoading"></div>
                    </div>
                  </div>
                  <div class="col-xs-12 col-sm-3">
                    <div class="dashNewCount" title="Offers" id="offDiv">
                    	<p>Offers</p>
                        <p class="NewCount apColor"><span id="offersCount" onclick="loadRequirementOffers();"></span></p>
                      <div id ="offersCountLoading"></div>
                    </div>
                  </div>
                </div>
              
        </div>
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
	$("#applicantsCountLoading, #submissionsCountLoading, #interviewsCountLoading, #offersCountLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:5; background-image:url("/resources/images/Spinner.gif"); background-position:center; background-repeat:no-repeat; background-size:60px;');
    var sourceleadUrl ='${sourceleadUrl}';
    var userId =  '${SourceLeadWeb_user.id}';
    var orgLocationId = '${SourceLeadWeb_orgLocationId}';
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+"/getRequirementSubmissionsCount?${_csrf.parameterName}=${_csrf.token}&daterange="+daterange+"&userId="+userId+"&orgLocationId="+orgLocationId, 
		        success: function(data) {
		        	if (data.response === "FAILED") {
			    		showError(data.responseMessage);
			    	} else if (data.response === "AWKWARD") {
			    		location.href = "/errorPage";
			    	} else if (data.response === "SUCCESS") {
			        	var json = data.object;
			        	$("#applicantsCountLoading, #submissionsCountLoading, #interviewsCountLoading, #offersCountLoading").removeAttr("style");
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
			    	}else if (data.indexOf('Sign In') !== -1) {
			    		location.replace(sourceleadUrl);
			    	} else {
			    		showError("Operation failed, please try again");
			    	}
		 		}
		  });
		 
		 if(daterange == "today"){
			 $("#jobsLink").attr("href", "<c:url value='/app/jobs'/>");
		 }else{
			 $("#jobsLink").attr("href", "<c:url value='/app/jobs'/>");
		 }
		 
	}

</script>