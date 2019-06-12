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
            	<div id ="professionalInterviewDiv"></div>
	     </div>
      </div>
    </div>
    
<script type="text/javascript">
window.onload = function() { 
	getInterviewsandTasks();
	}
</script>

<script type="text/javascript">
	function getInterviewsandTasks() {
		var date = $("#eventCalendar").val();
		var timezone ='${SourceLeadWeb_orgLocations.timezone}'; 
		var timezoneId = "";
		if(timezone != null && timezone != '' && timezone != 'undefined'){
			timezoneId = '${SourceLeadWeb_orgLocations.timezone.id}';
		}
		var candidateId = '${SourceLeadWeb_candidateId}';
		var sourceleadUrl ='${sourceleadUrl}';
		$.ajax({
	    	type: "POST",
	        url: sourceleadUrl+"/getProfessionalInterviewsEventDashBoard?${_csrf.parameterName}=${_csrf.token}&dateRange="+date+"&candidateId="+candidateId+"&timezoneId="+timezoneId,
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
					 result = result + '                          <p><label class="text-muted small col-md-2 col-md2-customized">Job Title   :</label> <a href="'+sourceleadUrl+'/requirementProfile/'+report.requirementUUID+'"><span title ="'+report.requirementTitle+'">'+report.requirementTitle+'</span></a></p>';
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
						result = result + '	<div class="dataTables_empty" style="border: 1px solid #ddd;margin: 0px 15px;">                                     ';
						result = result + '	</div>                                                      ';
						result = result + ' </div>                                                      ';
	        	 } 
	        	 result = result + '<div class="mask_inner"></div>';
	        	 $("#professionalInterviewDiv").empty();
	           	 $("#professionalInterviewDiv").append(result);
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
	  var finalDateFormatProeventCalendar = '';
	  if(locationDate.match("yyyy-MM-dd")){
		  finalDateFormatProeventCalendar = 'yy-mm-dd';
	  }if(locationDate.match("dd-MM-yyyy")){
		  finalDateFormatProeventCalendar = 'dd-mm-yy'; 
	  }if(locationDate.match("MM-dd-yyyy")){
		  finalDateFormatProeventCalendar = 'mm-dd-yy'; 
	  }if(locationDate.match("yyyy-dd-MM")){
		  finalDateFormatProeventCalendar = 'yy-dd-mm'; 
	  }
    $("#eventCalendar" ).datepicker({
	  changeMonth: true,
      changeYear: true,
      dateFormat : finalDateFormatProeventCalendar,
      onSelect : function(selected) {
  		getInterviewsandTasks();
  		
	   }
    });
  });
      
      $(function(){
   $('#example').timepicker(); 
});
  </script> 	
 
 
      