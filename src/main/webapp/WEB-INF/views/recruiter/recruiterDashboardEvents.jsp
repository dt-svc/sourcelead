<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
 <!-- Applicant Authorization -->
      <div class="dashboard-widget clearfix mb eventAlert">
        <div class="row">
      <!-- show Calendar -->
        <div class="col-xs-12 col-md-5 mb">
            <div id="eventCalendar"></div>    
        </div>
        
      <!-- show Calendar -->
            <div class="col-xs-12 col-md-7 mb eventCalendr_content alert alert-dismissable positionRelative" id ="dashboardEventsDiv">
            	<div id ="recruiterInterviewDiv"></div>
	     </div>
      </div>
    </div>
    
<script type="text/javascript">
window.onload = function() { 
	getRecruiterInterviewsand();
	}
</script>

<script type="text/javascript">
	function getRecruiterInterviewsand() {
		var date = $("#eventCalendar").val();
		var userId ='${SourceLeadWeb_user.id}'; 
		var timezone ='${SourceLeadWeb_orgLocations.timezone}'; 
		var timezoneId = "";
		var locationCode ='${SourceLeadWeb_locationCode}'; 
		if(timezone != null && timezone != '' && timezone != 'undefined'){
			timezoneId = '${SourceLeadWeb_orgLocations.timezone.id}';
		}
		$.ajax({
	    	type: "POST",
	        url: sourceleadUrl+"/getRecruiterSubmittiedInterviews?${_csrf.parameterName}=${_csrf.token}&dateRange="+date+"&userId="+userId+"&timezoneId="+timezoneId+"&locationCode="+locationCode,
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
					 result = result + '                          <p><label class="text-muted small col-md-2 col-md2-29">Job Title   :</label> <a href="'+sourceleadUrl+'/requirementProfile/'+report.requirementUUID+'"><span title ="'+report.requirementTitle+'">'+report.requirementTitle+'</span></a></p>';
					 result = result + '                          <p><label class="text-muted small col-md-2 col-md2-29">Company Name:</label>'+report.companyName+' </p>    ';
					 result = result + '                          <p><label class="text-muted small col-md-2 col-md2-29">Interview Type:</label> '+interviewType+'</p>    ';
					 result = result + '                          <p><label class="text-muted small col-md-2 col-md2-29">Location:</label>'+location+'</p>    ';
					 result = result + ' 								</div>                                                                         ';
					 result = result + '                      </div>                                                                                   ';
					 result = result + '              </div>                                                                                           ';
					 result = result + '      </div>   ';
					 result = result + '  </div>                                                                                                       ';
					} 
	        	 
	        	 if(json.interviewerList.length<=0){
			        	result = result + '<div class="row">                                            ';
						result = result + '	<div class="dataTables_empty" style="border: 1px solid #ddd;margin: 0px 15px;">                                     ';
						result = result + '	</div>                                                      ';
						result = result + ' </div>                                                      ';
	        	 } 
	        	 result = result + '<div class="mask_inner"></div>';
	        	 $("#recruiterInterviewDiv").empty();
	           	 $("#recruiterInterviewDiv").append(result);
	        }
		});
		
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
  $(function() {
	  
	  var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
	  var finalDateFormatRecEventCalendar = '';
	  if(locationDate.match("yyyy-MM-dd")){
		  finalDateFormatRecEventCalendar = 'yy-mm-dd';
	  }if(locationDate.match("dd-MM-yyyy")){
		  finalDateFormatRecEventCalendar = 'dd-mm-yy'; 
	  }if(locationDate.match("MM-dd-yyyy")){
		  finalDateFormatRecEventCalendar = 'mm-dd-yy'; 
	  }if(locationDate.match("yyyy-dd-MM")){
		  finalDateFormatRecEventCalendar = 'yy-dd-mm'; 
	  }
    $("#eventCalendar" ).datepicker({
	  changeMonth: true,
      changeYear: true,
      dateFormat : finalDateFormatRecEventCalendar,
      onSelect : function(selected) {
    	  getRecruiterInterviewsand();
  		
	   }
    });
  });
      
      $(function(){
   $('#example').timepicker(); 
});
  </script> 	
 
 
      