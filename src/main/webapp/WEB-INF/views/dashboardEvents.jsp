<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">

 <!-- Applicant Authorization -->
      <div class="dashboard-widget clearfix mb eventAlert">
        <div class="row">
        <div class="col-xs-12 col-md-12 event_calendar">
            <h4>Events</h4>
              <!-- <b class="dashboard-datepkr"><input class="input-sm" id="dasboardEventDatpicker" readonly="readonly" type="text"><i class="fa fa-calendar"></i></b> -->  
        </div>
      <!-- show Calendar -->
        <div class="col-xs-12 col-md-5 mb">
            <div id="eventCalendar"></div>    
        </div>
      <!-- show Calendar -->
            <div class="col-xs-12 col-md-7 mb eventCalendr_content alert alert-dismissable" id ="dashboardEventsDiv">
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
		var sourceleadUrl ='${sourceleadUrl}';
		var date = $("#eventCalendar").val();
		$.ajax({
	    	type: "POST",
	        url: sourceleadUrl+"/getInterviewsandTasks?${_csrf.parameterName}=${_csrf.token}&dateRange="+date,
	        success: function(data) {
	        	 var json = JSON.parse(data);
	        	 var result = "";
	        	 
			         result = result + ' <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>     ';
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
	        			var clientName = "---";
	        		 if(report.clientName != null && report.clientName != "" && report.clientName != 'undefined' && report.interviewSourceType != 'INTERVIEW'){
 		       			clientName = report.clientName;
 		       		}
			         result = result + '   <div class="eventList clearfix">  ';
					 result = result + '      <div class="media">              ';
					 result = result + '          <div class="media-left">     ';
					 result = result + '              <div>'+report.startTimeStr+'</div>       ';
					 result = result + '              <div>'+report.endTimeStr+'</div>        ';
					 result = result + '          </div>                       ';
					 result = result + '              <div class="media-body paddingLeft">   ';
					 result = result + '                  <figure class="pull-left">         ';
					 result = result + '                  <img src="images/interview_icons.png" class="img-responsive" width="40">    ';
					 result = result + '                  </figure>                                                                   ';
					 result = result + '                      <div class="pull-left event_details form-horizontal">                   ';
					 result = result + '                          <p><label class="text-muted small col-md-2">Name:</label>'+report.candidateName+' </p>    ';
					result = result + '									 <div class="personalInfo pull-right">                                          ';
					result = result + ' 									<i class="fa fa-ellipsis-v" title="Email & Phone"></i>                         ';
					result = result + '										<div class="userdetails">                                                      ';
					result = result + ' 									<p><i class="fa fa-envelope"></i><label>'+report.candidateEmail+'</label></p>     ';
					result = result + ' 									<p><i class="fa fa-phone"></i><label>'+report.candidatePhoneNumber+'</label></p>                 ';
					result = result + ' 									</div>                                                                         ';
					result = result + ' 								</div>                                                                         ';
					 result = result + '                          <p><label class="text-muted small col-md-2">Req.:</label> <a href="'+sourceleadUrl+"/requirementSummary?requirementUUID="+report.requirementUUID+'">'+report.requirementTitle+'</a></p>';
					 result = result + '                          <p><label class="text-muted small col-md-2">Client:</label> '+clientName+'</p>    ';
					 result = result + '                          <div class="feedBack"> ';
					  if(report.interviewerId != null  &&  report.interviewerId !='' && report.interviewerId !='undefined'){
						  result = result+'	 <a  href="'+sourceleadUrl+'/'+'dashboardInterviewFeedBackTemplate?templateId='+report.interviewTemplateid+'&requirementInterviewid='+report.interviewerId+'">FeedBack</a>';
                      }  
					 //result = result + '                          <a href="javascript:void(0)" title="FeedBack"><i class="fa fa-handshake-o"></i></a>    ';
					 result = result + '                          </div>                                                                               ';
					 result = result + '                      </div>                                                                                   ';
					 result = result + '              </div>                                                                                           ';
					 result = result + '      </div>   ';
					 result = result + '  </div>                                                                                                       ';
					}
	        	 
	        	 for(var i=0; i<json.tasksList.length; i++)
					{
	        		 var report = json.tasksList[i];
	        		 var taskTime ="";
	        		 var priority = "";
	        		 var description="";
	        		 if(report.taskTime != null && report.taskTime != '' && report.taskTime != 'undefined'){
		        			taskTime = report.taskTime;
		        		}
	        		 if(report.priority != null && report.priority != '' &&  report.priority != 'undefined'){
		        			priority = report.priority;
		        		}
	        		 else{
		        			priority = "--";
		        		}
	        		 if(report.description != null && report.description != '' &&  report.description != 'undefined'){
	        			 description = report.description;
		        		}
	        		 else{
	        			 description = "--";
		        		}
					     result = result + '  <div class="eventList clearfix">       ';
					 	 result = result + '     <div class="media">                 ';
					 	 result = result + '         <div class="media-left">        ';
					 	 result = result + '             <div>'+taskTime+'</div>           ';
					 	 result = result + '         </div>                          ';
					 	 result = result + '             <div class="media-body paddingLeft"> ';
					 	 result = result + '                 <figure class="pull-left">       ';
					 	 result = result + '                 <img src="images/task-icon.png" class="img-responsive" width="40">    ';
					 	 result = result + '                 </figure>                                                             ';
					 	 result = result + '                     <div class="pull-left event_details">                             ';
					 	 result = result + '                         <p><label class="text-muted small col-md-2">Subject:</label> '+report.subject+'</p>         ';
					 	 result = result + '                         <p class="text-danger"><label class="text-muted small col-md-2">Priortiy:</label> '+priority+'</p>   ';
					 	 result = result + '                         <p><label class="text-muted small col-md-2">Status:</label> '+report.taskStatus+'</p>                    ';
					 	 result = result + '                         <p><label class="text-muted small col-md-2">Des.:</label> '+description+'</p>   ';
					 	 result = result + '                     </div>            ';
					 	 result = result + '             </div>                    ';
					 	 result = result + '     </div>                            ';
					 	 result = result + ' </div>                                ';
					}
	        	 if(json.tasksList.length<=0 && json.interviewerList.length<=0){
			        	result = result + '<div class="row">                                            ';
						result = result + '	<div class="dataTables_empty">                                     ';
						result = result + '	</div>                                                      ';
						result = result + ' </div>                                                      ';
	        	 }
	        	 $("#dashboardEventsDiv").empty();
	           	 $("#dashboardEventsDiv").append(result); 
	        }
		});
		
	}

	
	
	
	</script>
	
  <script>
  $(function() {
    $("#eventCalendar" ).datepicker({
	  changeMonth: true,
      changeYear: true,
      dateFormat : 'dd-mm-yy',
      onSelect : function(selected) {
  		getInterviewsandTasks();
	   }
    });
  });
      
      $(function(){
   $('#example').timepicker(); 
});
  </script> 	
 <!-- <script>
 function personalInfo(){
     $(".userdetails").toggle();
 };
/*     $(".personalInfo").click(function(){
    	alert("fff");
          $(".userdetails").toggle();
    }); */
        /* $('.personalInfo, .userdetails').click(function(e){
        e.stopPropagation();
    });

    $('html').click(function(){
        $(".userdetails").hide();
    }); */
    </script> -->
<!-- <script>
window.onload = function () {
ApplicantAuthorization('requestSent');
};

</script> -->
<!-- Applicant Authorization Ends --> 

 

      