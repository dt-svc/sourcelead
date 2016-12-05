<!-- Side Nav Bar -->
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

 <sec:authorize access="hasAnyRole('ROLE_FILL_MEMBER_TIMESHEETS','ROLE_VIEW_MEMBER_TIMESHEETS','ROLE_EXPORT_MEMBER_TIMESHEETS') && hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_EXPORT_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS')" var="bothTimesheets"/>
 <sec:authorize access="!hasAnyRole('ROLE_FILL_MEMBER_TIMESHEETS','ROLE_VIEW_MEMBER_TIMESHEETS','ROLE_EXPORT_MEMBER_TIMESHEETS') && hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_EXPORT_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS')" var="onlyTimesheets"/>
 <sec:authorize access="hasAnyRole('ROLE_FILL_MEMBER_TIMESHEETS','ROLE_VIEW_MEMBER_TIMESHEETS','ROLE_EXPORT_MEMBER_TIMESHEETS') && !hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_EXPORT_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS')" var="onlyApprovalTimesheets"/>


<script> 
$(document).ready(function(){
	  if('${bothTimesheets}' == 'true' || '${onlyTimesheets}' == 'true'){
		  timeSheetSubmissionStatusCount("myTimesheets");
	  }
	  else{
		  timeSheetSubmissionStatusCount("employes");
	  }
});
</script>

 <!-- Timesheets -->
      <div class="col-md-6">
        <div class="clients_wrap_table dashboard-widget dashboard-widget-icons">
          <h4 class="clearfix">${bothTimesheets ? 'Timesheets' : onlyTimesheets ? 'My Timesheets' : onlyApprovalTimesheets ? 'Approval Timesheets' : ''} &nbsp;&nbsp;<!-- <a href="#" title="Calendar"><i aria-hidden="true" class="fa fa-calendar"></i></a> --></h4>
          <!-- <a href="javascript:void(0)" class="collpase-div pull-right" title="SlideUp/SlideDown"><i aria-hidden="true" class="fa fa-angle-down"></i></a> -->
           <div class="clearfix require_wrap_table dashboard-data mt">
           <c:if test="${bothTimesheets}">
             <ul class="nav nav-tabs">
              <li class="active"><a data-toggle="tab" onclick="timeSheetSubmissionStatusCount('myTimesheets');" href="#today-req">My Timesheets</a></li>
              <li><a data-toggle="tab" onclick="timeSheetSubmissionStatusCount('employes');" href="#prev-req">Employees</a></li>
             </ul>
             </c:if>
            <div class="db-widget-sm row">
              <div class="col-xs-12 col-sm-3">
                <div class="info-box"> <span class="info-box-icon bg-orange"> <i class="fa fa-floppy-o" aria-hidden="true"></i> </span>
                  <div class="info-box-content text-center">
                   <p class="info-box-number"><span id="savedTimesheetsSubmissionsCount"></span></p>
                    <p class="info-box-text" id="savedCountAnchor"></p>
                    <!--  <p class="info-box-text"><a href="#">Saved</a></p> -->
                   
                  </div>
                </div>
              </div>
              <div class="col-xs-12 col-sm-3">
                <div class="info-box"> <span class="info-box-icon bg-blue"> <i class="fa fa-clock-o" aria-hidden="true"></i> </span>
                  <div class="info-box-content text-center">
                    <p class="info-box-number"><strong><span  id="waitingTimesheetsSubmissionsCount"></span></strong></p>
                    <p class="info-box-text" id="waitingCountAnchor"></p>
                   <!--  <p class="info-box-text"><a href="#">Waiting</a></p> -->
                  </div>
                </div>
              </div>
              <div class="col-xs-12 col-sm-3">
                <div class="info-box"> <span class="info-box-icon bg-green"> <i class="fa fa-check-circle-o" aria-hidden="true"></i> </span>
                  <div class="info-box-content text-center">
                    <p class="info-box-number"><strong><span id="approvalTimesheetsSubmissionsCount"></span></strong></p>
                    <p class="info-box-text" id="approvedCountAnchor"></p>
                    <!-- <p class="info-box-text"><a href="#">Approved</a></p> -->
                  </div>
                </div>
              </div>
              <div class="col-xs-12 col-sm-3">
                <div class="info-box"> <span class="info-box-icon bg-red"> <i class="fa fa-ban" aria-hidden="true"></i> </span>
                  <div class="info-box-content text-center">
                    <p class="info-box-number"><strong><span id="rejectedTimesheetsSubmissionsCount"></span></strong></p>
                    <p class="info-box-text" id="rejectedCountAnchor"></p>
                    <!-- <p class="info-box-text"><a href="#">Rejected</a></p> -->
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!-- Timesheets ends --> 
      

<script type="text/javascript">
function timeSheetSubmissionStatusCount(timesheetType){
	
    var sourceleadUrl ='${sourceleadUrl}';
    
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+"/getTimeSheetSubmissionStatus?${_csrf.parameterName}=${_csrf.token}&timesheetType="+timesheetType, 
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	
		        

		        	 $("#savedTimesheetsSubmissionsCount").empty();
	        		 $("#approvalTimesheetsSubmissionsCount").empty();
	        		 $("#rejectedTimesheetsSubmissionsCount").empty();
	        		 $("#waitingTimesheetsSubmissionsCount").empty();
	        		 
	        		 $("#savedCountAnchor").empty();
	        		 $("#waitingCountAnchor").empty();
	        		 $("#approvedCountAnchor").empty();
	        		 $("#rejectedCountAnchor").empty();
	        		 
	        		 var  savedTimesheetsSubmissionsCount = json.savedTimesheetSubmissionCount;
		        	 var  waitingTimesheetsSubmissionsCount =json.waitingForApprovalTimesheetSubmissionCount;
		        	 var  approvalTimesheetsSubmissionsCount =json.approvedTimesheetSubmissionCount;
		        	 var  rejectedTimesheetsSubmissionsCount = json.rejectedTimesheetSubmissionCount; 
		        	 if(savedTimesheetsSubmissionsCount<1 || timesheetType == "myTimesheets"){
		        		  $("#savedTimesheetsSubmissionsCount").html(savedTimesheetsSubmissionsCount);
	                      $("#savedCountAnchor").html("Saved");
	                  }else{
	                	  $("#savedTimesheetsSubmissionsCount").html(savedTimesheetsSubmissionsCount);
	                      $("#savedCountAnchor").html("<a href='/sourcelead/totalTimesheetList/DRAFT'>Saved</a>");
	                  }
		        	if(waitingTimesheetsSubmissionsCount<1 || timesheetType == "myTimesheets"){
		        		  $("#waitingTimesheetsSubmissionsCount").html(waitingTimesheetsSubmissionsCount);
	                      $("#waitingCountAnchor").html("Waiting");
	                  }else{
	                	  $("#waitingTimesheetsSubmissionsCount").html(waitingTimesheetsSubmissionsCount);
	                      $("#waitingCountAnchor").html('<a  href="/sourcelead/totalTimesheetList/SUBMITTED">Waiting</a>');
	                  }
		        	if(approvalTimesheetsSubmissionsCount<1 || timesheetType == "myTimesheets"){
		        		  $("#approvalTimesheetsSubmissionsCount").html(approvalTimesheetsSubmissionsCount);
	                      $("#approvedCountAnchor").html("Approved");
	                }else{
	                	  $("#approvalTimesheetsSubmissionsCount").html(approvalTimesheetsSubmissionsCount);
	                      $("#approvedCountAnchor").html('<a  href="/sourcelead/totalTimesheetList/APPROVED">Approved</a>');
	                  }
		        	if(rejectedTimesheetsSubmissionsCount<1 || timesheetType == "myTimesheets"){
		        		  $("#rejectedTimesheetsSubmissionsCount").html(rejectedTimesheetsSubmissionsCount);
	                      $("#rejectedCountAnchor").html("Rejected");
	                 }else{
	                	 $("#rejectedTimesheetsSubmissionsCount").html(rejectedTimesheetsSubmissionsCount);
	                      $("#rejectedCountAnchor").html('<a  href="/sourcelead/totalTimesheetList/REJECTED">Rejected</a>');
	                  }
		 		}
		  });
		 
	}

</script>      