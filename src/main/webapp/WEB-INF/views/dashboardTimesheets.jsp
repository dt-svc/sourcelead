<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">

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
      <div class="col-md-6 mb10">
        <div class="dashboardWidget-new dashNewWrap mb">
        	
        	<div class="dashNewWrapHead clearfix">
        		<a id="timeSheetLink" href="<c:url value="/app/timesheet"/>" class="pull-left"> ${bothTimesheets ? 'Timesheets' : onlyTimesheets ? 'Timesheets' : onlyApprovalTimesheets ? 'Approval' : ''} &nbsp;&nbsp;</a>
	        	<input type="hidden" value="" id="timesheetSocketsFor">
	             <c:if test="${bothTimesheets}">
		             <ul class="nav nav-tabs pull-right">
		               <li class="active"><a data-toggle="tab" onclick="timeSheetSubmissionStatusCount('myTimesheets');" >Self</a></li>
		               <li><a data-toggle="tab" onclick="timeSheetSubmissionStatusCount('employes');" >Employees</a></li>
		             </ul>
	             </c:if>
        	</div>

            <div class="db-widget-sm">
              <div class="col-xs-12 col-sm-3">
                <div class="dashNewCount">
                    <p id="savedCountAnchor"></p>
                    <p class="NewCount"><span id="savedTimesheetsSubmissionsCount"></span></p>
                  <div id ="draftLoading"></div>
                </div>
              </div>
              <div class="col-xs-12 col-sm-3">
                <div class="dashNewCount">
                    <p id="waitingCountAnchor"></p>
                    <p class="NewCount suColor"><span  id="waitingTimesheetsSubmissionsCount"></span></p>
                  <div id ="waitingLoading"></div>
                </div>
              </div>
              <div class="col-xs-12 col-sm-3">
                <div class="dashNewCount">
                    <p id="approvedCountAnchor"></p>
                    <p class="NewCount apColor"><span id="approvalTimesheetsSubmissionsCount"></span></p>
                   <div id ="approvedloading"></div>
                </div>
              </div>
              <div class="col-xs-12 col-sm-3">
                <div class="dashNewCount">
                    <p id="rejectedCountAnchor"></p>
                    <p class="NewCount reColor"><span id="rejectTimesheetsSubmissionsCount"></span></p>
                   <div id ="rejectLoading"></div>
                </div>
              </div>
            </div>
        </div>
      </div>
<script>
   function timeSheetSubmissionStatusCount(timesheetType) { 
	  $("#rejectLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:5; background-image:url("/resources/images/Spinner.gif"); background-position:center; background-repeat:no-repeat; background-size:60px;');
	  $("#waitingLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:5; background-image:url("/resources/images/Spinner.gif"); background-position:center; background-repeat:no-repeat; background-size:60px;');
	  $("#draftLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:5; background-image:url("/resources/images/Spinner.gif"); background-position:center; background-repeat:no-repeat; background-size:60px;');
	  $("#approvedloading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:5; background-image:url("/resources/images/Spinner.gif"); background-position:center; background-repeat:no-repeat; background-size:60px;');
	  var sourceleadUrl ='${sourceleadUrl}';
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+"/getTimeSheetSubmissionStatus?${_csrf.parameterName}=${_csrf.token}&timesheetType="+timesheetType, 
		        success: function(json) {
        		  var  savedTimesheetsSubmissionsCount = json.savedTimesheetSubmissionCount;
	        	  var  waitingTimesheetsSubmissionsCount =json.waitingForApprovalTimesheetSubmissionCount;
	        	  var  approvalTimesheetsSubmissionsCount =json.approvedTimesheetSubmissionCount;
	        	  var  rejectedTimesheetsSubmissionsCount = json.rejectedTimesheetSubmissionCount; 
		       	  
		       	  $("#draftLoading").removeAttr("style");
		       	  $("#savedTimesheetsSubmissionsCount").empty();
		       	  $("#savedCountAnchor").empty();
		       	  $("#approvedloading").removeAttr("style");
		       	  $("#approvalTimesheetsSubmissionsCount").empty();
		       	  $("#approvedCountAnchor").empty();
		       	 
		       	  $("#rejectLoading").removeAttr("style");
		       	  $("#rejectTimesheetsSubmissionsCount").empty();
		       	  $("#rejectedCountAnchor").empty();
		       	  $("#waitingLoading").removeAttr("style");
		       	  $("#waitingTimesheetsSubmissionsCount").empty();
		       	  $("#waitingCountAnchor").empty();
		       	 
		       	  if(rejectedTimesheetsSubmissionsCount<1 || timesheetType == "myTimesheets"){
		       		  $("#rejectTimesheetsSubmissionsCount").html(rejectedTimesheetsSubmissionsCount);
		                $("#rejectedCountAnchor").html("Rejected");
		           }else{
		         	    $("#rejectTimesheetsSubmissionsCount").html(rejectedTimesheetsSubmissionsCount);
		                 $("#rejectedCountAnchor").html('<a  href="/app/timesheetlistByStatus?status=REJECTED">Rejected</a>'); 
		           }
		       	  if(savedTimesheetsSubmissionsCount<1 || timesheetType == "myTimesheets"){
		       		  $("#savedTimesheetsSubmissionsCount").html(savedTimesheetsSubmissionsCount);
		                $("#savedCountAnchor").html("Draft");
		            }else{
		          	  $("#savedTimesheetsSubmissionsCount").html(savedTimesheetsSubmissionsCount);
		                $("#savedCountAnchor").html("<a href='/app/timesheetlistByStatus?status=DRAFT'>Draft</a>"); 
		               
		            }
		       	 
		       	 if(waitingTimesheetsSubmissionsCount<1 || timesheetType == "myTimesheets"){
		       		  $("#waitingTimesheetsSubmissionsCount").html(waitingTimesheetsSubmissionsCount);
		               $("#waitingCountAnchor").html("Submitted");
		           }else{
		         	  $("#waitingTimesheetsSubmissionsCount").html(waitingTimesheetsSubmissionsCount);
		                $("#waitingCountAnchor").html('<a  href="/app/timesheetlistByStatus?status=SUBMITTED">Waiting</a>'); 
		           }
		       	 
		       	  if(approvalTimesheetsSubmissionsCount<1 || timesheetType == "myTimesheets"){
		       		  $("#approvalTimesheetsSubmissionsCount").html(approvalTimesheetsSubmissionsCount);
		                $("#approvedCountAnchor").html("Approved");
		           }else{
		          	  $("#approvalTimesheetsSubmissionsCount").html(approvalTimesheetsSubmissionsCount);
		                $("#approvedCountAnchor").html('<a  href="/app/timesheetlistByStatus?status=APPROVED">Approved</a>'); 
		            }
		       	 if(timesheetType == "myTimesheets"){
		       		 $("#timeSheetLink").attr("href", "<c:url value='/app/timesheet'/>");
		       	 }else{
		       		 $("#timeSheetLink").attr("href", "<c:url value='/app/timesheetApproval'/>");
		       	 }
		 		}
		  }); 
 }
</script>  
