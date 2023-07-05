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
<script>
   var pmsUrl ='${SourceLeadWeb_pmsUrl}';
</script>

 <!-- Timesheets -->
      <div class="col-md-6 mb10">
        <div class="dashboardWidget-new dashNewWrap mb">
        	
        	<div class="dashNewWrapHead clearfix">
        		<a id="timeSheetLink" href="<c:url value="/app/timesheet"/>" class="pull-left"> ${bothTimesheets ? 'Timesheets' : onlyTimesheets ? 'Timesheets' : onlyApprovalTimesheets ? 'Approval' : ''} &nbsp;&nbsp;</a>
	        	<input type="hidden" value="" id="timesheetSocketsFor">
		             <ul class="nav nav-tabs pull-right">
		             <li class="pt5">
				        <div class="dropdown-select positionRelative repSelWrap clearfix">
				          <select class="form-control repSelFiled" name="dateSelection" id="dateSelection" onchange="getCustomDates();">
				            <option value='Last_7_days' selected>Last 7 days</option>
				            <option value='Last_15_days'>Last 15 days</option>
				            <option value='1_month'>1 month</option>
				            <option value='3_months'>3 months</option>
				            <option value='YTD'>YTD</option>
				            <option value='Last_Year'>Last Year</option>
				            <option value="custom">Custom</option>
				          </select>   
				          
				          
				          <div class="clearfix customLeaves repCustomDate dashboardPopup" id="customSearch">
				              <div class="repCustomDateInn">
				                <input type="text" class="form-control calendar" value="" id="fromDate" name="fromDate"  placeholder="From Date"  readonly="readonly" >
				                <p id="fromDateDiv">From Date Required</p>
				              </div>
				              
				              <div class="repCustomDateInn">
				                <input type="text" class="form-control calendar" value="" id="toDate" name="toDate"  placeholder="To Date" field-name="From Date" readonly="readonly" >
				                <p id="toDateDiv">To Date Required</p>
				              </div>
				              <!-- <div class="repCustomDateBtn"><button type="button" class="btn btn-primary btn-sm" onclick="getApplicantBySourceChart();"><i class="fa fa-search"></i></button></div> -->
				          </div>   
				                           
				        </div>    		
		    		</li> 
		    		 <c:if test="${bothTimesheets}">
		               <li  data-interest="myTimesheets"class="active"><a  data-toggle="tab" onclick="timeSheetSubmissionStatusCount('myTimesheets');" >Self</a></li>
		               <li  data-interest="employes"><a data-toggle="tab" onclick="timeSheetSubmissionStatusCount('employes');" >Employees</a></li>
		               </c:if>
		             </ul>
	             
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
	  var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
	  var dateSelection = $("#dateSelection").val(); 
	  var fromDate = '';
	  var toDate = '';
	  if(dateSelection == 'custom'){
		   fromDate = $("#fromDate").val();
		   toDate = $("#toDate").val();
		  if(fromDate == '' || toDate == ''){
			  flag = false;
			  if(fromDate == ''){
				  document.getElementById("fromDateDiv").style.display="block";
			  }else{
				
				  document.getElementById("fromDateDiv").style.display="none";
			  }
			  if(toDate == ''){
				  document.getElementById("toDateDiv").style.display="block";
			  }else{
				 
				  document.getElementById("toDateDiv").style.display="none";
			  }
		  }else{
			  document.getElementById("fromDateDiv").style.display="none";
			  document.getElementById("toDateDiv").style.display="none";
			  
		  }
		  
	  }
	  
	  pmsUrl
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+"/getTimeSheetSubmissionStatus?${_csrf.parameterName}=${_csrf.token}&timesheetType="+timesheetType+"&dateSelection="+dateSelection+"&fromDate="+fromDate+"&toDate="+toDate, 
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
		                 $("#rejectedCountAnchor").html('<a  href="'+pmsUrl+'/timesheetlistByStatus?status=REJECTED">Rejected</a>'); 
		           }
		       	  if(savedTimesheetsSubmissionsCount<1 || timesheetType == "myTimesheets"){
		       		  $("#savedTimesheetsSubmissionsCount").html(savedTimesheetsSubmissionsCount);
		                $("#savedCountAnchor").html("Draft");
		            }else{
		          	  $("#savedTimesheetsSubmissionsCount").html(savedTimesheetsSubmissionsCount);
		                $("#savedCountAnchor").html("<a href='"+pmsUrl+"/timesheetlistByStatus?status=DRAFT'>Draft</a>"); 
		               
		            }
		       	 
		       	 if(waitingTimesheetsSubmissionsCount<1 || timesheetType == "myTimesheets"){
		       		  $("#waitingTimesheetsSubmissionsCount").html(waitingTimesheetsSubmissionsCount);
		               $("#waitingCountAnchor").html("Submitted");
		           }else{
		         	  $("#waitingTimesheetsSubmissionsCount").html(waitingTimesheetsSubmissionsCount);
		                $("#waitingCountAnchor").html('<a  href="'+pmsUrl+'/timesheetlistByStatus?status=SUBMITTED">Waiting</a>'); 
		           }
		       	 
		       	  if(approvalTimesheetsSubmissionsCount<1 || timesheetType == "myTimesheets"){
		       		  $("#approvalTimesheetsSubmissionsCount").html(approvalTimesheetsSubmissionsCount);
		                $("#approvedCountAnchor").html("Approved");
		           }else{
		          	  $("#approvalTimesheetsSubmissionsCount").html(approvalTimesheetsSubmissionsCount);
		                $("#approvedCountAnchor").html('<a  href="'+pmsUrl+'/timesheetlistByStatus?status=APPROVED">Approved</a>'); 
		            }
		       	 if(timesheetType == "myTimesheets"){
		       		 $("#timeSheetLink").attr("href", "<c:url value='"+pmsUrl+"/timesheet'/>");
		       	 }else{
		       		 $("#timeSheetLink").attr("href", "<c:url value='"+pmsUrl+"/timesheetApproval'/>");
		       	 }
		 		}
		  }); 
 }
</script>  


<script>
function getCustomDates(){
	 var dateSelection = $("#dateSelection").val(); 
	 var timesheetType = $('body').find('li.active').attr('data-interest');
	 if(dateSelection == 'custom'){
		 removeErrMsg();
		 removeErrMsgTo();
		 document.getElementById("customSearch").style.display="block";
		 $("#customize_btn").attr("disabled", "disabled");
		 
			 var locationDate ='${SourceLeadWeb_dateFormat}'; 
			  var finalDateFormatProjectSave = '';
			  if(locationDate.match("yyyy-MM-dd")){
				  finalDateFormatProjectSave = 'yy-mm-dd';
			  }if(locationDate.match("dd-MM-yyyy")){
				  finalDateFormatProjectSave = 'dd-mm-yy'; 
			  }if(locationDate.match("MM-dd-yyyy")){
				  finalDateFormatProjectSave = 'mm-dd-yy'; 
			  }if(locationDate.match("yyyy-dd-MM")){
				  finalDateFormatProjectSave = 'yy-dd-mm'; 
			  }
	    
		  $('#fromDate').datepicker({
				dateFormat : finalDateFormatProjectSave,
				changeMonth: true,
				changeYear: true, 
				onSelect : function(selected) {
					var dt = $(this).datepicker('getDate', '+1d');
					dt.setDate(dt.getDate());
					removeErrMsg();
					$("#toDate").datepicker("option", "minDate", dt);
				}
			});
		  
		  $('#toDate').datepicker({
				dateFormat : finalDateFormatProjectSave,
				changeMonth: true,
				changeYear: true, 
				onSelect : function(selected) {
					var dt = $(this).datepicker('getDate', '-1d');
					dt.setDate(dt.getDate());
					removeErrMsgTo();
					$("#fromDate").datepicker("option", "maxDate", dt);
				}
			});
		 
	 }else{
		 document.getElementById("customSearch").style.display="none";
		 $("#customize_btn").removeAttr("disabled");
		 $("#fromDate").datepicker("destroy");
		 $("#toDate").datepicker("destroy");
		 $("#fromDate").val('');
		 $("#toDate").val('');
		 document.getElementById("fromDateDiv").style.display="none";
		 document.getElementById("toDateDiv").style.display="none";
	 }
	 timeSheetSubmissionStatusCount(timesheetType);
}

</script>

 <script>
 function removeErrMsg(){
	  document.getElementById("fromDateDiv").style.display="none";
 }
 function removeErrMsgTo(){
	  document.getElementById("toDateDiv").style.display="none";
	  
 }
  </script>
 

