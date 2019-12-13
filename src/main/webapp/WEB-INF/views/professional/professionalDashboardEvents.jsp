<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">

<style type="text/css">
#eventCalendar .ui-widget.ui-widget-content { width:100% !important; background: #ededed !important; }
</style>

<script>
$(document).on('click', '.ui-datepicker-next', function () {
	  $(".ui-datepicker-year, .ui-datepicker-month").addClass("sel");
});
$(document).on('click', '.ui-datepicker-prev', function () {
	  $(".ui-datepicker-year, .ui-datepicker-month").addClass("sel");
});
</script>

 <!-- Applicant Authorization -->
      <div class="dashboard-widget clearfix mb eventAlert">
        <div class="row">
        
	    	<h4>&nbsp;&nbsp;&nbsp;Scheduled Interviews</h4>
				    
        <div class="col-xs-12 col-md-5 mb mt">
            <div id="eventCalendar"></div>    
        </div>
        
            <div class="col-xs-12 col-md-7 mb eventCalendr_content alert alert-dismissable positionRelative mt" id ="dashboardEventsDiv">
            	<div id ="professionalInterviewDiv"></div>
	     </div>
      </div>
    </div>
    
<script type="text/javascript">
window.onload = function() { 
	$("#eventCalendar").find("select.ui-datepicker-month, select.ui-datepicker-year").addClass("sel");
	getInterviewsDates();
	getInterviewsandTasks();
	}
</script>

<script type="text/javascript">
var locationDate ='${SourceLeadWeb_dateFormat}'; 
var finalDateFormatDashboardEventCalendar = '';
$(document).ready(function () {		
	if(locationDate.match("yyyy-MM-dd")) {
		finalDateFormatDashboardEventCalendar = 'yy-mm-dd';
	} if(locationDate.match("dd-MM-yyyy")) {
		finalDateFormatDashboardEventCalendar = 'dd-mm-yy'; 
	} if(locationDate.match("MM-dd-yyyy")) {
		finalDateFormatDashboardEventCalendar = 'mm-dd-yy'; 
	} if(locationDate.match("yyyy-dd-MM")) {
		finalDateFormatDashboardEventCalendar = 'yy-dd-mm'; 
	}
});


function eventCalendar(dates) {
    $("#eventCalendar").datepicker({
	  changeMonth: true,
      changeYear: true,
      dateFormat : finalDateFormatDashboardEventCalendar,
      onSelect : function(selected) {
    	  getInterviewsandTasks();
	   },
	    beforeShowDay: function(date) {
	  	  var hlText = dates[date.getTime()];
	        return (hlText)?[true, "Highlighted", '']: [true, '', ''];
	  	}
    });
}
</script>

<script type="text/javascript">


var allDates = {};
var interviewDates = {};
function getInterviewsDates() {
	var sourceleadUrl = '${sourceleadUrl}';
	var dates = {}
	$.ajax({
		type : "POST",
		url : atsUrl
				+ "/getProfessionalInterviewDates?${_csrf.parameterName}=${_csrf.token}",
		success : function(result) {
			if (result.response === "FAILED") {
				showError(result.responseMessage);
			} else if (result.response === "AWKWARD") {
				location.href = "/errorPage";
			} else if (result.response === "SUCCESS") {
				var data = result.data;
				if (data.length > 0) {
					for (var i = 0; i < data.length; i++) {
						dates[new Date(data[i]).getTime()] = 'visible';
					}
				}
				interviewDates = dates;
				eventCalendar(dates);
			} else if (result.indexOf('Sign In') !== -1) {
				location.replace(sourceleadUrl);
			} else {
				showError("Operation failed, please try again");
			}
		}
	});
}


	function getInterviewsandTasks() {
		var date = getSelectedDateFromCalendar($("#eventCalendar").val());
		var timezoneId ='${SourceLeadWeb_timeZoneId}'; 
		var candidateId = '${SourceLeadWeb_candidateId}';
		$.ajax({
	    	type: "POST",
	        url: atsUrl+"/getProfessionalInterviewsEventDashBoard?${_csrf.parameterName}=${_csrf.token}&dateRange="+date+"&candidateId="+candidateId+"&timezoneId="+timezoneId,
	        success: function(json) {
	        	 var result = "";
				     result = result + '    <div class="eventHeader">                                                      ';
				     result = result + '          <div class="media">                                                      ';
				     result = result + '            <div class="media-left bg-success paddingRight">                       ';
				     result = result + '                <i class="fa fa-calendar fa-3x"></i>                               ';
				     result = result + '            </div>                                                                 ';
				     result = result + '            <div class="media-body paddingLeft">                                   ';
				     result = result + '              <h4 class="media-heading">'+json.dayOfweek+'</h4>                               ';
				     result = result + '              <p class="small">'+json.selectedDate+'</p>                               ';
				     result = result + '            </div>                                                                 ';
				     result = result + '          </div>                                                                   ';
				     result = result + '    </div>																		   ';
	        	 for(var i=0; i<json.interviewerList.length; i++)
					{
	        		 var report = json.interviewerList[i];
	        		 var location = '';
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
			         result = result + '   <div class="eventList clearfix">  ';
					 result = result + '      <div class="media">              ';
					 result = result + '          <div class="media-left">     ';
					 result = result + '              <div>'+report.startTimeStr+'</div>       ';
					 result = result + '              <div>'+report.endTimeStr+'</div>        ';
					 result = result + '          </div>                       ';
					 result = result + '              <div class="media-body paddingLeft">   ';
					 result = result + '                  <figure class="pull-left">         ';
					 result = result + '                  <img src="/resources/images/interview_icons.png" class="img-responsive" width="40">    ';
					 result = result + '                  </figure>                                                                   ';
					 result = result + '                      <div class="pull-left event_details form-horizontal">                   ';
					 result = result + '                          <p><label class="text-muted small col-md-2 col-md2-customized">Job Title   :</label> <a href="'+atsUrl+'/requirementProfile/'+report.requirementUUID+'"><span title ="'+report.requirementTitle+'">'+report.requirementTitle+'</span></a></p>';
					 result = result + '                          <p><label class="text-muted small col-md-2 col-md2-customized">Company Name:</label>'+report.companyName+' </p>    ';
					 result = result + '                          <p><label class="text-muted small col-md-2 col-md2-customized">Interview Type:</label> '+interviewType+'</p>    ';
					 result = result + '                          <p><label class="text-muted small col-md-2 col-md2-customized">Location:</label>'+location+'</p>    ';
					 result = result + ' 								</div>                                                                         ';
					 result = result + '                      </div>                                                                                   ';
					 result = result + '              </div>                                                                                           ';
					 result = result + '      </div>   ';
					 result = result + '  </div>                                                                                                       ';
					} 
	        	 
	        	 if(json.interviewerList.length<=0){
			        	result = result + '<div class="row">                                            ';
						result = result + '	<div class="dataTables_empty" style="border: 1px solid #ddd;margin: 0px 15px; height: 160px !important; min-height: 160px;  ">                                     ';
						result = result + '	</div>                                                      ';
						result = result + ' </div>                                                      ';
	        	 } 
	        	 result = result + '<div class="mask_inner"></div>';
	        	 $("#professionalInterviewDiv").empty();
	           	 $("#professionalInterviewDiv").append(result);
	        }
		});
		
	}
	
	
	function getSelectedDateFromCalendar(selectedDate) {
		var date = selectedDate;
		if(date == null || date == '' || date == 'undefined'){
			date = new Date(); 
			var month = parseInt(date.getMonth())+parseInt(1);
			  var locationDate ='${SourceLeadWeb_dateFormat}'; 
			  if(locationDate.match("yyyy-MM-dd")){
			      date = date.getFullYear()+"-"+month+"-"+date.getDate();
			  }if(locationDate.match("dd-MM-yyyy")){
				  date = date.getDate()+"-"+month+"-"+date.getFullYear();
			  }if(locationDate.match("MM-dd-yyyy")){
				  date = month+"-"+date.getDate()+"-"+date.getFullYear();
			  }if(locationDate.match("yyyy-dd-MM")){
				  date = date.getFullYear()+"-"+date.getDate()+"-"+month;
			  }
		}
		return date;
	}
</script>


	
<script>
    $(".addInterview_close, .side_mask").click(function() {
    	$(".add_inter_wrap").removeClass("add_inter_wrap_active");
		$(".side_mask").fadeOut(200);
		$("body").css('overflow-y', 'auto');
	});	
</script>
	
  <script>
    $(function(){
   $('#example').timepicker(); 
});
  </script> 	
 
 
      