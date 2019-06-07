<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

 <!-- Applicant Authorization -->
      <div class="mb eventAlert clearfix col-md-12 noPad">
        <div class="dashNewWrap clearfix mb10">
        <div class="dashNewWrapHead event_calendar">
            <h4 class="pull-left">Events</h4>
            <sec:authorize access="hasAnyRole(\'ROLE_ADD_INTERVIEW\')">
            <button class="btn btn-primary pull-left btn-sm ml mt7" onclick="dashboardAddInterview();" type="button">Schedule Interview</button>
            </sec:authorize>

<div class="dashboardoverIcon pull-right">
			<a href="javascript:void(0)" class="overIcon" title="Actions" onclick="newActions();"></a>
			<div class="newActPop">
				<span>
						<a href="javascript:void(0)" onclick="exportInterviewsListSheet('Pdf');" title="PDF" >Export PDF</a>
						<a href="javascript:void(0)" onclick="exportInterviewsListSheet('Excel');" title="Excel">Export Excel</a>					
				</span>
			</div>
		</div>


		<ul class="nav nav-tabs pull-right">
			<li id="jobFairsTab"><a href="javascript:void(0)" onclick="getJobFairs()">Job Fairs</a></li>
			<li id="interviewsTab" class="active"><a href="javascript:void(0)" onclick="getInterviewsandTasks()">Interviews & Tasks</a></li>
			<div class="clearfix"></div>
		</ul>       
		
		
		     
        </div>
        
      <!-- show Calendar -->
        <div class="col-xs-12 col-md-5 mb mt">
            <div id="eventCalendar"></div>
            <div id="jobFairCalendar" style="display: none;"></div>
        </div>


		<!-- show Calendar -->
            <div class="col-xs-12 col-md-7 mb mt alert alert-dismissable positionRelative" id ="dashboardEventsDiv">
                  
                  <div class="eventHeader">                                               
				        <div class="media">                                               
				          <div class="media-left bg-success paddingRight">                
				              <i class="fa fa-calendar fa-3x"></i>                        
				          </div>                                                          
				          <div class="media-body paddingLeft">                            
				            <h4 class="media-heading" id="dayOfweek"></h4>             
				            <p class="small" id="selectedDate"></p>                    
				          </div>                                                          
				        </div>                                                            
  			  </div>			                                                      

            <div class="eventCalendr_content">
            	<div id ="interviewDiv"></div>
           	 	<div id ="taskDiv"></div>
           	 	</div>
	     </div>

		<div class="col-xs-12 col-md-7 mb mt alert alert-dismissable positionRelative" id="dashboardJobFairsDiv" style="display: none;">
			<div class="eventHeader">
				<div class="media">
					<div class="media-left bg-success paddingRight">
						<i class="fa fa-calendar fa-3x"></i>
					</div>
					<div class="media-body paddingLeft">
						<h4 class="media-heading" id="dayOfweekForJobFair"></h4>
						<p class="small" id="selectedDateForJobFair"></p>
					</div>
				</div>
			</div>
			<div class="eventCalendr_content">
				<div id="fairJobsDiv"></div>
			</div>
		</div>
	</div>
		<div class="add_inter_wrap">
			<div class="new_side_wrap_inn" id="addInterviewDiv"></div>
		</div> 
    </div>
    
<script type="text/javascript">
	window.onload = function() {
		/* getInterviewsandTasks();
		getInterviewsDates(); */
		getJobFairDates();
		getJobFairs();
	}
</script>

<script>
	var allDates = {};
	var interviewDates = {};
	function getInterviewsDates() {
		var sourceleadUrl = '${sourceleadUrl}';
		var dates = {}
		$.ajax({
			type : "POST",
			url : sourceleadUrl
					+ "/getInterviewsTaskDates?${_csrf.parameterName}=${_csrf.token}",
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

	var jobFairDates = {};
	function getJobFairDates() {
		var sourceleadUrl = '${sourceleadUrl}';
		var dates = {}
		$.ajax({
			type : "POST",
			url : sourceleadUrl
					+ "/getJobFairDatesForCalendar?${_csrf.parameterName}=${_csrf.token}",
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
					jobFairDates = dates;
					getJobFairCalendar(dates);
				} else if (result.indexOf('Sign In') !== -1) {
					location.replace(sourceleadUrl);
				} else {
					showError("Operation failed, please try again");
				}
			}
		});
	}
	function test() {
		for(var e in jobFairDates) {
			alert(jobFairDates[e]);
		}
	}
</script>

<script type="text/javascript">
function getJobFairs() {
	$("#interviewsTab").removeClass("active");
	$("#dashboardEventsDiv").hide();
	$(".dashboardoverIcon").hide();
	
	$("#jobFairsTab").addClass("active");
	$("#dashboardJobFairsDiv").show();
	getJobFairDates();
	getJobsForSelectedFairDate();
}

function getInterviewsandTasks() {
	$("#interviewsTab").addClass("active");
	$("#dashboardEventsDiv").show();
	$(".dashboardoverIcon").show();
	
	$("#jobFairsTab").removeClass("active");
	$("#dashboardJobFairsDiv").hide();
	
	getInterviewsDates();
	getInterviewsandTasksForSelectedDate();
}

function getSelectedDateFromCalendar(selectedDate) {
	var date = selectedDate;
	if(date == null || date == '' || date == 'undefined'){
		date = new Date(); 
		var month = parseInt(date.getMonth())+parseInt(1);
		  var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
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

	function getInterviewsandTasksForSelectedDate() {
		var sourceleadUrl ='${sourceleadUrl}';
		var date = getSelectedDateFromCalendar($("#eventCalendar").val());
		
		$.ajax({
	    	type: "POST",
	        url: sourceleadUrl+"/getInterviewsandTasks?${_csrf.parameterName}=${_csrf.token}&dateRange="+date,
	        success: function(data) {
	        	if (data.response === "FAILED") {
		    		showError(data.responseMessage);
		    	} else if (data.response === "AWKWARD") {
		    		location.href = "/errorPage";
		    	} else if (data.response === "SUCCESS") {
		        	 var json = data.object;
		        	 var result = "";
		        	 var opt = "";
		        	 $("#dayOfweek").text(json.dayOfweek);
		        	 $("#selectedDate").text(json.selectedDate);
	 
		        	 for(var i=0; i<json.interviewerList.length; i++)
						{
		        		 
		        		 var report = json.interviewerList[i];
		        			var clientName = "---";
		        		 if(report.clientName != null && report.clientName != "" && report.clientName != 'undefined' && report.interviewSourceType != 'INTERVIEW'){
	 		       			clientName = report.clientName;
	 		       		}
		        		 
		        		 result = result + '   <div class="clearfix">  ';
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
						 result = result + '                          <p><label class="text-muted small col-md-2">Name:</label>'+report.candidateName+' </p>    ';
						 result = result + '									 <div class="personalInfo pull-right">                                          ';
						 result = result + ' 									<i class="fa fa-ellipsis-v" title="Email & Phone"></i>                         ';
						 result = result + '										<div class="userdetails">                                                      ';
						 result = result + ' 									<p><i class="fa fa-envelope"></i><label>'+report.candidateEmail+'</label></p>     ';
						 result = result + ' 									<p><i class="fa fa-phone"></i><label>'+report.candidatePhoneNumber+'</label></p>                 ';
						 result = result + ' 									</div>                                                                         ';
						 result = result + ' 								</div>                                                                         ';
						 result = result + '                          <p><label class="text-muted small col-md-2">Job:</label> <a href="'+sourceleadUrl+"/requirementSummaryView?requirementUUID="+report.requirementUUID+"&interviewId="+report.interviewId.id+'">'+report.requirementTitle+'</a></p>';
						 result = result + '                          <p><label class="text-muted small col-md-2">Client:</label> '+clientName+'</p>    ';
						 result = result + '                          <div class="feedBack"> ';
						
						 
						  if(report.interviewerUUID != null  &&  report.interviewerUUID !='' && report.interviewerUUID !='undefined' && report.interviewTemplateUUId != null && report.interviewTemplateUUId != '' && report.interviewTemplateUUId != 'undefined'){
	                    	  result = result+'	 <a  href="'+sourceleadUrl+'/'+'dashboardInterviewFeedBackTemplate?templateId='+report.interviewTemplateUUId+'&requirementInterviewid='+report.interviewerUUID+'" >';
	                    	  if(report.feedbackDate != null && report.feedbackDate != '' && report.feedbackDate != 'undefined'){
	                        	   result = result+'<i class="fa fa-handshake-o success_color"></i>';
							  }else{
								  result = result+'<i class="fa fa-handshake-o"></i>';
							  }
							  result = result+'</a>';
	                      }   
						  
	 				 result = result + '                          </div>                                                                               ';
						 result = result + '                      </div>                                                                                   ';
						 result = result + '              </div>                                                                                           ';
						 result = result + '      </div>   ';
						 result = result+' <div id="feedbackInterviewerId"></div>';
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
						     opt = opt + '  <div class="eventList clearfix">       ';
						 	 opt = opt + '     <div class="media">                 ';
						 	 opt = opt + '         <div class="media-left">        ';
						 	 opt = opt + '             <div>'+taskTime+'</div>           ';
						 	 opt = opt + '         </div>                          ';
						 	 opt = opt + '             <div class="media-body paddingLeft"> ';
						 	 opt = opt + '                 <figure class="pull-left">       ';
						 	 opt = opt + '                 <img src="/resources/images/task-icon.png" class="img-responsive" width="40">    ';
						 	 opt = opt + '                 </figure>                                                             ';
						 	 opt = opt + '                     <div class="pull-left event_details">                             ';
						 	 opt = opt + '                         <p><label class="text-muted small col-md-2">Subject:</label> '+report.subject+'</p>         ';
						 	 opt = opt + '                     <div class="clearfix"></div>            ';
						 	 opt = opt + '                     <div class="row">            ';
						 	 opt = opt + '                     <div class="col-md-5">            ';
						 	 opt = opt + '                         <p class="text-danger"><label class="text-muted small col-md-6" style="width:45%;">Priority:</label> '+priority+'</p>   ';
						 	 opt = opt + '                     </div>            ';
						 	 opt = opt + '                     <div class="col-md-5">            ';
						 	 opt = opt + '                         <p><label class="text-muted small col-md-6">Status:</label> '+report.taskStatus+'</p>                    ';
						 	 opt = opt + '                     </div>            ';
						 	 
						 	opt = opt + '                     </div>            ';
						 	 
						 	opt= opt+ '                         <p><label class="text-muted small col-md-2" style="width:15.7%;">Des.:</label><div class="col-md-10 paddingLeft">'+description+'</div></p>';
						 	opt =opt + '                     </div>            ';
						 	opt =opt + '             </div>                    ';
						 	opt =opt + '     </div>                            ';
						 	opt =opt + ' </div>                                ';
						}
		        	 if(json.tasksList.length<=0 && json.interviewerList.length<=0){
				        	result = result + '<div class="row">                                            ';
							result = result + '	<div class="dataTables_empty" style="border: 1px solid #ddd;margin: 0px 15px;">                                     ';
							result = result + '	</div>                                                      ';
							result = result + ' </div>                                                    ';
							result = result + ' </div>                                                    ';
		        	 }
		        	 result = result + '<div class="mask_inner"></div>';
		        	 $("#interviewDiv").empty();
		           	 $("#interviewDiv").append(result);
		           	 $("#taskDiv").empty();
		           	 $("#taskDiv").append(opt);
		    	} else if (data.indexOf('Sign In') !== -1) {
		    		location.replace(sourceleadUrl);
		    	} else {
		    		showError("Operation failed, please try again");
		    	}
		    }
		});
	}
	
	function getJobsForSelectedFairDate() {
		var sourceleadUrl ='${sourceleadUrl}';
		var date = getSelectedDateFromCalendar($("#jobFairCalendar").val());
		$("#fairJobsDiv").empty();
		$("#fairJobsDiv").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		$.ajax({
	    	type: "POST",
	        url: sourceleadUrl+"/getJobFairRequirements?${_csrf.parameterName}=${_csrf.token}&date="+date,
	        success: function(result) {
	        	if (result.response === "FAILED") {
		    		showError(result.responseMessage);
		    	} else if (result.response === "AWKWARD") {
		    		location.href = "/errorPage";
		    	} else if (result.response === "SUCCESS") {
		    		var dateFormat = "";
		    		if(locationDate.match("yyyy-MM-dd")) {
		    			dateFormat = 'yy-mm-dd';
		    		} if(locationDate.match("dd-MM-yyyy")) {
		    			dateFormat = 'dd-mm-yy'; 
		    		} if(locationDate.match("MM-dd-yyyy")) {
		    			dateFormat = 'mm-dd-yy'; 
		    		} if(locationDate.match("yyyy-dd-MM")) {
		    			dateFormat = 'yy-dd-mm'; 
		    		}
		    		
					$("#dayOfweekForJobFair").text(result.responseMessage.split("<>")[0]);
					$("#selectedDateForJobFair").text(result.responseMessage.split("<>")[1]);

					var data = result.data;
					if (data != null && data.length > 0) {
						var jobFairsText = "";
						for (var e in data) {
							var text = "<div class='eventList clearfix'>";
							text += "	<div class='pull-left event_details form-horizontal dashboard_fair_table'>";
							text += "		<p>";
							text += "			<a target='_blank' href='" + sourceleadUrl + "/requirementSummary?requirementUUID=" + data[e].requirementUUID + "'>" + data[e].jobTitle + "</a>";
							text += "		</p>";
							if(data[e].clientId != null) {								
								text += "		<p>";
								text += " 			<a target='_blank' href='" + sourceleadUrl + "/clientDashboard?orgRelationUUID=" + data[e].clientId.uuid + "' title='" + data[e].clientId.clientName + "'>" + data[e].clientId.clientName + "</a>";
								text += "		</p>";
							}
							text += "		<p>";
							for (var i in data[e].jobFairList) {
								text += "<span class='dashboard_span'>" + data[e].jobFairList[i].name + "</span>";
							}
							text += "		</p>";
							text += "	</div>";
							text += "</div>";

							/* $("#fairJobsDiv").append(text); */
							jobFairsText += text;
						}
						$("#fairJobsDiv").append(jobFairsText);
					} else {
						var text = '<div class="row">';
						text = text + '	<div class="dataTables_empty" style="border: 1px solid #ddd;margin: 0px 15px;">';
						text = text + '	</div>';
						text = text + ' </div>';
						text = text + ' </div>';
						$("#fairJobsDiv").append(text);
					}
					$("#fairJobsDiv").removeAttr("style");
				} else if (result.indexOf('Sign In') !== -1) {
					location.replace(sourceleadUrl);
				} else {
					showError("Operation failed, please try again");
				}
			}
		});
	}
</script>


<script>
function dashboardAddInterview(){
	var selectedDate = $("#eventCalendar").val();
	$('.action-pop').slideUp();
	interviewTemplate();
	
	 var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  DashBoardInterViewDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  DashBoardInterViewDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  DashBoardInterViewDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  DashBoardInterViewDate = 'YYYY-DD-MM'; 
	  }  
	
	var result = "";
	result = result + '<div class="add_inter addinterview_dashboard add_inter_chosen">';
	result = result + '<form id="addInterview_form" name="addInterview_form" >';
	result = result + '<div class="add_inter_head"> <span>Schedule Interview</span>';
	result = result + '<div class="clearfix"></div>';   
	result = result + '</div>';
	
	
	result = result + '<div class="clearfix form-group row">';
	
	result = result + '<div class="col-md-5">';
	result = result + '<span class="col-md-3 paddingLeft mt10">Posted from<span class="text-danger">&nbsp;*</span></span>';
	result = result + '<div class="col-md-9 req_list">';
	result = result + '<select class="form-control" id="requirementsTimeRange" onchange="getRequirements();">';
	result = result + '		<option value="today">Today</option>';
    result = result + '		<option value="yesterday">Yesterday</option>';
    result = result + '		<option value="week" selected>1 week</option>';
    result = result + '		<option value="twoweeks">2 weeks</option>';
    result = result + '		<option value="month">1 month</option>';
    result = result + '		<option value="twomonths">2 months</option>';
	result = result + '</select>';
	result = result + '</div>';
	result = result + '</div>';
	result = result + '<div class="clearfix form-group row"></div>';
	
	result = result + '<div class="col-md-5">';
	result = result + '<span class="col-md-3 paddingLeft mt10">Job<span class="text-danger">&nbsp;*</span></span>';
	result = result + '<div class="col-md-9 req_list" id="reqDiv">';
	result = result + '<select class="form-control" id="requirementsList" name="requirementsList" onchange="selectAndGetApplicantsForRequirement();" field-name="Job" data-validation="required">';
	
	result = result + '</select>';
	result = result + '</div>';
	result = result + '</div>';
	
		result = result + '<div class="col-md-5" id="isClientInterviewDiv" style="display: none;">';
	    result = result + '<input type="checkbox" name="isClientInterview" id="isClientInterview" class="isClient" onclick="myFunction();">';
        result = result + '<label>&nbsp;&nbsp;<strong>Is Client Interview</strong>&nbsp;&nbsp;</label>';
        result = result + '</div>';
	
	result = result + '</div>';

	result = result + '		<input value="2" style="display: none;" type="text" id="maxTsRowNum" name="maxTsRowNum">';
	
	
	result = result + '<table cellpadding="0" cellspacing="0" id="addInterviewTbl" class="addinterTable">';
		result = result + '<thead>';
			result = result + '<tr id="divTsRow0">';
				result = result + '<th>Applicant</th>';
				result = result + '<th>Interview Type</th>';
				result = result + '<th>Interview Date<br>(<small class="custom-small">'+DashBoardInterViewDate+'</small>)</th>';
				result = result + '<th>Start - End Time</th>';	
				result = result + '<th>Time Zone</th>';
				result = result + '<th>Details</th>';
				result = result + '<th>Interviewer</th>';
			result = result + '</tr>';
		result = result + '</thead>';
		
		result = result + '       <tbody>';
		result = result + '       <tr>';
		result = result + '		  <td>';
		result = result + '  		<select  multiple data-placeholder="Select Applicants" onchange="selectInterviewApplicants()" class="blminwidth-50" id="1applicant" name="1applicant" field-name="Applicant" data-validation="required" >';
		result = result + ' 		</select>';
		result = result + '		  </td>';
		
		result = result + '		<td>';
		result = result + '		<select class="form-control"  id="1interviewType" name="1interviewType" onchange="interviewType(this);">';
		result = result + '			<option value="">--Select Type--</option>';
		result = result + '			<option value="F2F">F2F</option>';
		result = result + '			<option value="Telephonic">Telephonic</option>';
		result = result + '			<option value="Technical">Technical</option>';
		result = result + '			<option value="Screening">Screening</option>';
		result = result + '			<option value="Skype">Skype</option>';
		result = result + '			<option value="Webex/GoToMeeting">Webex/GoTo Meeting</option>';
		result = result + '			<option value="Other">Other</option>';
		result = result + '		</select>';
		result = result + '		<div id="1interviewTypeCheck" style="display: none;"><input value=""  type="text" id="1interviewTypeother" name="1interviewTypeother" class="form-control input-sm pavInp" placeholder="Interview type" field-name="Interview type" data-validation-optional="true"></div>';
		result = result + '		</td>';
		
		result = result + '		<td>';
		result = result +  '		<input class="form-control input-sm" type="text" id="1dateOfInterview" name="1dateOfInterview" value="'+selectedDate+'" readonly>';
		result = result + '		</td>';

		result = result + '		<td>';
		result = result + '			<div class="pull-left timeZone validationError_paragraph" id="timeDiv"> <span class="positionRelative"><input class="form-control input-sm timepicker" placeholder="Start Time" name="1startTime" id="1startTime" onBlur="validateEndTime();" type="text" data-validation="required" field-name="Start Time"/></span> <span class="positionRelative"><input class="form-control input-sm timepicker" placeholder="End Time" name="1endTime" id="1endTime" onBlur="validateEndTime();" type="text" field-name="End Time"/></span></div>';
		result = result + '		</td>';

		result = result + '		<td>';
		result = result + '			<select class="form-control"  id="1timeZone" name="1timeZone" field-name="Timezone" data-validation="required">';
		result = result + '			</select>';
		result = result + '		</td>';
		
		result = result + '		<td>';
		result = result + ' 		 <input value="" type="text" id="1location" name="1location" class="form-control input-sm location" placeholder="Details" field-name="Details" >';
		result = result + '		</td>';
		
		result = result + '		 <td>';
		result = result + '  		<select  multiple data-placeholder="Select Interviewers" class="blminwidth-50" id="1interviewer" name="1interviewer" field-name="Interviewer">';
		result = result + ' 		</select>';
		result = result + '		 </td>';
		
		result = result + '      </tr>';
		result = result + '      </tbody>';
		
	result = result + '</table>';
	
	result = result + '<div class="clearfix"></div>';  
	
	result = result + '<div class="row pad2">';
	result = result + '<div class="col-md-12 mt10">';
	result = result + '<div class="ne-ch row">';
	result = result + '<label><input type="checkbox" name="notificationToApplicant" value="Y" checked="checked" ><b>Notify to Applicants</b></label>';
	result = result + '<label><input type="checkbox" name="proposeTime" value="Y" checked="checked" ><b>Propose time</b></label>';
	result = result + '</div>';
	
	
	result = result + '<div id="emptyTemplate" style="dispaly:none" class="row">';
	result = result + '<strong class="clearfix col-md-12">Interview Template</strong>';
	result = result + '<div class="chosen-select col-md-4 customchosen-select">';
	result = result + '<select class="form-control mt10" id="interviewSubmissionTemplate" name="interviewSubmissionTemplate">';
	result = result + '</select>';
	result = result + '</div>';
	result = result + '</div>';
	
	
	result = result + '</div>';
	result = result + '</div>';
	
	result = result + '<div class="pull-right">';
	result = result + '<button type="submit" id="add_interview" class="btn btn-sm btn-primary mr">Submit</button>';
	result = result + '<button type="button" class="btn btn-sm btn-default" onClick="cancelAddInterviewDiv();">Cancel</button>';
	result = result + '</div>';
	result = result + '<div class="clearfix"></div>'; 
	result = result + '</form>';
	result = result + '<div id="interviewLoading"></div>';
	result = result + '<div class="mt25" id="scheduledInterviewsList" style="overflow:auto;"></div>';
	result = result + '<div class="clearfix"></div>';
	result = result + '</div>';
	$("#addInterviewDiv").empty();
  	$("#addInterviewDiv").append(result); 
  	
  	$("#1applicant").chosen();
	$("#1applicant").trigger('chosen:update');
    $("#1interviewType").chosen();
	$("#1interviewType").trigger('chosen:update');

  	
	$(".add_inter_wrap").addClass("add_inter_wrap_active");
	$(".side_mask").fadeIn(200);
	$("body").css('overflow-y', 'hidden');
	getRequirements();
	getTimezonesListForInterview();
	getInterviewers();
	
	$("#1startTime").timepicker({
		'showDuration': true,
		'disableTextInput': true
	}).on('changeTime', function() {
        $("#1endTime").timepicker('option', 'minTime', $(this).val());
    });
	
	$("#1endTime").timepicker({
		'showDuration': true,
		 'maxTime': '23:30:00',
		'disableTextInput': true
	}).on('changeTime', function() {
        $("#1startTime").timepicker('option', 'maxTime', $(this).val());
    });
}
</script>

<script>
function interviewTemplate(){
	 var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({
	    	type: "POST",
	    	url : sourceleadUrl+"/getInterviewFeedbackTemplatesList?${_csrf.parameterName}=${_csrf.token}", 
	        success: function(result) {
	        	if (result.response === "FAILED") {
  		    		showError(result.responseMessage);
  		    	} else if (result.response === "AWKWARD") {
  		    		location.href = "/errorPage";
  		    	} else if (result.response === "SUCCESS") {
  		    		var json = result.data;
			        var output = "";
			        if(json.length > 0){
			         	for (var i=0; i<json.length; i++)
			 			{
			 	        		var opt = json[i];
			 	        		if(opt.isDefault == "Y"){
			 	        			output = output + "<option value='" +opt.uuid+ "' selected>"+opt.templateName+"</option>";
			 	        		}else{
				 	        		output = output + "<option value='" +opt.uuid+ "'>"+opt.templateName+"</option>";
			 	        		} 
			 			}
			         	$("#interviewSubmissionTemplate").empty();
			 	        $("#interviewSubmissionTemplate").append(output);
			 	       
			 	        $("#interviewSubmissionTemplate").chosen();
			 			$("#interviewSubmissionTemplate").trigger('chosen:update');
			 			$('.chosen-container').css({'width': '100% !important'});
			 			$(".chosen-container").attr('style', 'width:100% !important');
			 			
			        }else{
			        	$("#emptyTemplate").empty();
			        }
  		    	}else {
		    		showError("Operation failed, please try again");
		    	}
	        },
	    });
}
</script>

<script>
function interviewType(id){ 
	var selectedReqId = $("#1interviewType").val();
	 if(selectedReqId=="Other"){
		 $("#1interviewTypeCheck").attr("style", "display:block");
		 $("#1interviewTypeother").removeAttr('data-validation-optional');
		 $("#1interviewTypeother").attr("data-validation","required validate_length length1-50"); 
	 }
	 else{
		 $("#1interviewTypeCheck").attr("style", "display:none");
		 $("#1interviewTypeother").removeAttr("data-validation");
		 $("#1interviewTypeother").attr("data-validation-optional","true");
	 }
}
</script>

<script>
function selectInterviewApplicants(){
	var selectedInterviewApplicants =  $('#1applicant_chosen  span').size();
	$("#selectedInterviewApplicantsCount").html(selectedInterviewApplicants);
	$('#1applicant_chosen').next('p.jquery_form_error_message').remove();
	
	$('.search-choice-close').click(function() {
		var interviewApplicants =  $('#1applicant_chosen  span').size();
		$('#1applicant_chosen').next('p.jquery_form_error_message').remove();
		if(interviewApplicants <1){
			$('#1applicant_chosen').after('<p class="jquery_form_error_message">Applicant is Required</p>');
		}
		$("#selectedInterviewApplicantsCount").html(interviewApplicants);
	});
	
	getSeheduledInterviewsList();
}
</script>


<script>
function getSeheduledInterviewsList(){
	var sourceleadUrl ='${sourceleadUrl}';
	var selectedReqId = $("#requirementsList").val();
	var selectedSubmissions = $("#1applicant").val();
	$("#scheduledInterviewsList").empty();
	$.ajax({
        type : "POST",
        url: sourceleadUrl+"/getScheduledInterviewsListForSelectedApplicants?${_csrf.parameterName}=${_csrf.token}&selectedReqId="+selectedReqId+"&selectedSubmissions="+selectedSubmissions,    
         success :function(data) {
              if(data != null && data != 'undefined' && data != "" && data != '[]'){
            	  var json = JSON.parse(data);
            	  var result = "";
            	  var jsonLength = json.length;
            	  if(jsonLength > 0){
		        	
	            	result = result + '<table cellpadding="0" cellspacing="0" id="addInterviewTbl" class="addinterTable">';
	          		result = result + '<thead>';
          			result = result + '<tr id="divTsRow0">';
        				result = result + '<th>Applicant</th>';
        				result = result + '<th>Interview Type</th>';
        				result = result + '<th>Start Date</th>';
        				result = result + '<th>Start time - End time</th>';	
        				result = result + '<th>Time Zone</th>';
        				result = result + '<th>Details</th>';
        				result = result + '<th>Interviewer</th>';
          			result = result + '</tr>';
	          		result = result + '</thead>';
	          		result = result + '       <tbody>';
	          		
                  for (var i = 0; i < jsonLength; i++) {
                 	 	var opt = json[i]; 
                 	 	result = result + '       <tr>';
	                 	result = result + '       <td>';
	                 	if(null != opt.candidateName && '' != opt.candidateName && 'undefined' != opt.candidateName){
	                 		result = result + '			<div class="tbldata-dotdot-sm text-center">'+opt.candidateName+'</div>';
	                 	}else{
	                 		result = result + '			<div class="tbldata-dotdot-sm text-center">---</div>';
	                 	}
	                 	result = result + '       </td>';
	                 	
	                 	result = result + '       <td>';
	                 	if(null != opt.interviewType && '' != opt.interviewType && 'undefined' != opt.interviewType){
	                 		result = result + '			<div class="tbldata-dotdot-100 text-center">'+opt.interviewType+'</div>';
	                 	}else{
	                 		result = result + '			<div class="tbldata-dotdot-100 text-center">---</div>';
	                 	}
	                 	result = result + '       </td>';
	                 	
	                 	result = result + '       <td>';
	                 	var sTime = "";
	                 	if(null != opt.interviewTimeZoneStartTimeStr && '' != opt.interviewTimeZoneStartTimeStr && 'undefined' != opt.interviewTimeZoneStartTimeStr){
	                 		sTime = opt.interviewTimeZoneStartTimeStr.substr(opt.interviewTimeZoneStartTimeStr.length - 8);
	                 		var iDate = opt.interviewTimeZoneStartTimeStr.substring(0, 10)
	                 		result = result + '			<div class="tbldata-dotdot-sm text-center">'+iDate+'</div>';
	                 	}else{
	                 		result = result + '			<div class="tbldata-dotdot-sm text-center">---</div>';
	                 	}
	                 	result = result + '       </td>';
	                 	
	                 	result = result + '       <td>';
	                 	var eTime = "";
	                 	if(null != opt.interviewTimeZoneEndTimeStr && '' != opt.interviewTimeZoneEndTimeStr && 'undefined' != opt.interviewTimeZoneEndTimeStr){
	                 		eTime = opt.interviewTimeZoneEndTimeStr.substr(opt.interviewTimeZoneEndTimeStr.length - 8);
	                 		result = result + '			<div class="tbldata-dotdot-sm text-center">'+sTime+" - "+eTime+'</div>';
	                 	}else{
	                 		result = result + '			<div class="tbldata-dotdot-sm text-center">'+sTime+'</div>';
	                 	}
	                 	result = result + '       </td>';
	                 	
	                 	result = result + '       <td>';
	                 	if(null != opt.timezone && '' != opt.timezone && 'undefined' != opt.timezone){
	                 		result = result + '			<div class="tbldata-dotdot-sm text-center">'+opt.timezone+'</div>';
	                 	}else{
	                 		result = result + '			<div class="tbldata-dotdot-sm text-center">---</div>';
	                 	}
	                 	result = result + '       </td>';
	                 	
	                 	result = result + '       <td>';
	                 	if(null != opt.location && '' != opt.location && 'undefined' != opt.location){
	                 		result = result + '			<div class="tbldata-dotdot text-center">'+opt.location+'</div>';
	                 	}else{
	                 		result = result + '			<div class="tbldata-dotdot text-center">---</div>';
	                 	}
	                 	result = result + '       </td>';
                 	
	                 	result = result + '       <td>';
	                 	if(null != opt.interviewersList && '' != opt.interviewersList && 'undefined' != opt.interviewersList){
	                 		result = result + '			<div class="tbldata-dotdot text-center">'+opt.interviewersList+'</div>';
	                 	}else{
	                 		result = result + '			<div class="tbldata-dotdot text-center">---</div>';
	                 	}
	                 	result = result + '       </td>';
	                 	result = result + '      </tr>';
                  }
		          
		          result = result + '      </tbody>';
		          result = result + '</table>';
		          result = result + '<div class="clearfix"></div>'; 
                  $("#scheduledInterviewsList").empty();
                  $("#scheduledInterviewsList").append(result);
                  
              }
	       }
       },
    });
}
</script>


<script>
function getRequirements(){
	var sourceleadUrl ='${sourceleadUrl}';
	var postedDateRange =$("#requirementsTimeRange").val();
	$.ajax({
        type : "GET",
        url: sourceleadUrl+"/getAssignedRequirementListByOrgLocationIdAndUserIdAndPostedDate?postedDateRange="+postedDateRange,    
         success :function(data) {
              if(data != null && data != 'undefined' && data != "" && data != '[]'){
            	  var json = JSON.parse(data);
            	  var result = "";
            	  result = result + '<option value="">--- Select Job ---</option>';
                  for (var i = 0; i < json.length; i++) {
                 	 var opt = json[i]; 
                 	var jobId = "";
                 	if(null != opt.jobId && "" != opt.jobId && 'undefined' != opt.jobId){
                 		jobId = " - "+opt.jobId;
                 	}
                 	 result = result +  '<option value="'+opt.id+'"> '+opt.jobTitle+''+jobId+'</option>';
                  }
                  $("#requirementsList").empty();
                  $("#requirementsList").append(result);
                  $("#requirementsList").chosen();
              	  $("#requirementsList").trigger('chosen:update');
	              	$('#requirementsList')
	                .find('option:first-child').prop('selected', true)
	                .end().trigger('chosen:updated');
	              	$("#reqDiv").find('p.jquery_form_error_message').remove();
	              	$("#reqDiv").find('p.text-primary').empty();
	              	$("#reqDiv").append("<p class='text-primary pull-left small'><i class='fa fa-info-circle paddingRight'></i>Select Job</p>");
              }else{
            	  var result = "";
            	  	 result = result + '<option value="">--- Select Job ---</option>';
            	  $("#requirementsList").empty();
                  $("#requirementsList").append(result);
                  $("#requirementsList").chosen();
              	  $("#requirementsList").trigger('chosen:update');
	              	$('#requirementsList')
	                .find('option:first-child').prop('selected', true)
	                .end().trigger('chosen:updated');
	             $("#reqDiv").find('p.jquery_form_error_message').remove();
	             $("#reqDiv").find('p.text-primary').empty();
	             $("#reqDiv").append("<p class='jquery_form_error_message'>No Jobs found</span>");
              }
          	$("#1applicant").attr("data-placeholder", "Select Applicants");
          	$("#1applicant").empty();
          	$('#1applicant').trigger('chosen:updated');
       },
    });

}
</script>

<script>
function selectAndGetApplicantsForRequirement(){
	$("#reqDiv").find('p.text-primary').empty();
		$('#isClientInterview').attr('checked', false);
		$("#isClientInterviewDiv").hide();
		getApplicantsForRequirement();
		$("#scheduledInterviewsList").empty();
	}
</script>

<script>
function getApplicantsForRequirement(){
	var sourceleadUrl ='${sourceleadUrl}';
	var selectedReqId = $("#requirementsList").val();
	if(selectedReqId != ""){
		 var isClientInterview = "";
		 if(document.getElementById('isClientInterview').checked) {
		    isClientInterview = 'Y';
	     }else{
	    	isClientInterview = 'N';
	     }
		 var existingApplicantsIds = [];
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getAllShortListCandidates?${_csrf.parameterName}=${_csrf.token}&requirementId="+selectedReqId+"&existingApplicantsIds="+existingApplicantsIds+"&isClientInterview="+isClientInterview,  
		        success: function(data) {
		        	if (data.response === "FAILED") {
			    		showError(data.responseMessage);
			    	} else if (data.response === "AWKWARD") {
			    		location.href = "/errorPage";
			    	} else if (data.response === "SUCCESS") {
			    		var json = data.data;
			        	var appCount = json.length;
			        	$("#1applicant").attr("data-placeholder", appCount+" Applicants available");
			        	$("#reqDiv").find('p.jquery_form_error_message').remove();		        	
			        	var output = "";
			        	for (var i=0; i<appCount; i++)
						{
			        		var opt = json[i];
			        		if(opt.createdUser !=null && opt.createdUser !='' && opt.createdUser != 'undefined'){
			        			output = output + "<option value='" +opt.id+ "'>"+opt.candidateName+" ("+opt.createdUser+")</option>";	
			        		}else{
			        			output = output + "<option value='" +opt.id+ "'>"+opt.candidateName+"</option>";
			        		}
			        		
			        		if(opt.isClientSubmission == 'Y'){
			        			$("#isClientInterviewDiv").css("display","block");
			        		}else{
			        			$('#isClientInterview').attr('checked', false);
			        			$("#isClientInterviewDiv").hide();
			        		}
						}
			        	$("#1applicant").empty();
			        	$("#1applicant").append(output);
			        	$("#1applicant").trigger("chosen:updated");
			    	}else if (data.indexOf('Sign In') !== -1) {
			    		location.replace(sourceleadUrl);
			    	} else {
			    		showError("Operation failed, please try again");
			    	}
		        },
		    });
	}else{
		$('#isClientInterview').attr('checked', false);
		$("#isClientInterviewDiv").hide();
		$("#1applicant").empty();
    	$("#1applicant").trigger("chosen:updated");
	}
}
</script>

<script>
function myFunction(){
	getApplicantsForRequirement();
}
</script>

<script type="text/javascript">
function getTimezonesListForInterview(){
	var sourceleadUrl ='${sourceleadUrl}';
	var timezone = '${timezone}';
	 $.ajax({
	    	type: "POST",
	    	url : sourceleadUrl+"/getTimezonesList?${_csrf.parameterName}=${_csrf.token}", 
	        success: function(data) {
	        var json = JSON.parse(data);
	        var output = "";
	        output = output + "<option value=''>--Select--</option>"; 
	        	for (var i=0; i<json.length; i++){
	        		var opt = json[i];
	        		if(opt.timeZoneId == timezone){
	        			output = output + "<option value='" +opt.id+ "' selected>"+opt.timeZoneId+"</option>";
	        		}else{
	        			output = output + "<option value='" +opt.id+ "'>"+opt.timeZoneId+"</option>";
	        		}
	        		
				}
	        	$("#1timeZone").empty();
	        	$("#1timeZone").append(output);
	        	$("#1timeZone").chosen();
            	$("#1timeZone").trigger('chosen:update');
	        }
	 });
}
</script>

<script type="text/javascript">
 function getInterviewers() {
	 var sourceleadUrl ='${sourceleadUrl}';
	  $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getAllInterviewers?${_csrf.parameterName}=${_csrf.token}", 
		        success: function(data) {
		        	if (data.response === "FAILED") {
			    		showError(data.responseMessage);
			    	} else if (data.response === "AWKWARD") {
			    		location.href = "/errorPage";
			    	} else if (data.response === "SUCCESS") {
			    		var json = data.data;
			        	var output = "";
			        	for (var i=0; i<json.length; i++)
						{
			        		var opt = json[i];
			        		if(opt.lastName != 'undefined' && opt.lastName != null && opt.lastName!=""){
			        			output = output + "<option value='" +opt.id+ "'>"+opt.firstName+" "+opt.lastName+"</option>";
			        		}else{
			        			output = output + "<option value='" +opt.id+ "'>"+opt.firstName+"</option>";
			        		}
						}
			        	$("#1interviewer").empty();
			        	$("#1interviewer").append(output);
			        	$("#1interviewer").chosen();
			        	$("#1interviewer").trigger("chosen:updated");
			    	}else {
			    		showError("Operation failed, please try again");
			    	}
		        },
		    });
 	}
</script>

<script type="text/javascript">
	function validateEndTime() {
		 	$("#timeDiv").find('p.jquery_form_error_message').remove();
			var startTime = $("#1startTime").val();
			var endTime = $("#1endTime").val();
			if(endTime != null && endTime != '' && endTime != 'undefined'){
				if(startTime == endTime){
					$("#timeDiv").find('p.jquery_form_error_message').remove();
			        $("#add_interview").removeAttr("disabled");
			        $("#add_interview").attr("disabled", "disabled");
			        $("#timeDiv").append("<p class='jquery_form_error_message'>End Time should greater than Start Time</span>");
			    }else{
			         $("#timeDiv").find('p.jquery_form_error_message').remove();
			         $("#add_interview").removeAttr("disabled");
			   	}
			}
			
			var startTime1 = $("1startTime").val();
			var endTime1 = $("#1endTime").val();
			if(endTime1 != null && endTime1 != '' && endTime1 != 'undefined'){
				if(startTime1 == endTime1){
					 $("#add_interview").attr("disabled", "disabled");
				}
			}
		}
</script>

<script>
function cancelAddInterviewDiv(){
	 $(".add_inter_wrap").removeClass("add_inter_wrap_active");
	 $(".side_mask").fadeOut(200);
 	 $("body").css('overflow-y', 'auto');
}
</script>


<script type="text/javascript">
   	$('body').on('submit', '#addInterview_form', function(e){
   	/* var isNofityToOthers = document.getElementById("notificationToOthers").value;
   		if(isNofityToOthers == "Y"){
   			validateOtherMail();
   		} */
   		$("#reqDiv").find('p.text-primary').empty();
	  if ($(this).validate(false, validationSettings)){
        e.preventDefault();
        var frm = $('#addInterview_form').serializeFormJSON();
        var con = JSON.stringify(frm);
        var jsonData = encodeURIComponent(con);
        var sourceleadUrl ='${sourceleadUrl}';
        var requirementId = '${requirementId}';
        
        var selectedInterviewApplicants =  $('#1applicant_chosen  span').size();
        
        if(selectedInterviewApplicants <= 50){
	        $("#add_interview").attr("disabled", "disabled");
	 		$("#add_interview" ).addClass('button').val('Processing..');
	 		$("#interviewLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	   	     $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/saveInterview?${_csrf.parameterName}=${_csrf.token}&jsonData="+jsonData,
		        success: function(result) {
		        	    $(".add_inter_wrap").removeClass("add_inter_wrap_active");
		        		$(".side_mask").fadeOut(200);
		        		$("body").css('overflow-y', 'auto');
		        		/* var allApplicants = 'all';
			    		requirementPipeline(requirementId,allApplicants); 
			    		pipelineList(requirementId); */
		        },
		    });     
        }else{
        	alertInterviewMessage("<b>Selected Applicants should not more than 50</b>");
        }
        return true;
      }
      else{
      	return false;	
         }
    });
    	
   	$('#addInterview_form').showHelpOnFocus();
    $('#addInterview_form').validateOnBlur(false, validationSettings); 
</script>

	
<script>
    $(".addInterview_close, .side_mask").click(function() {
    	$(".add_inter_wrap").removeClass("add_inter_wrap_active");
		$(".side_mask").fadeOut(200);
		$("body").css('overflow-y', 'auto');
	});	
</script>
	
<script>
	var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
	var finalDateFormatEDashboardeventCalendar = '';
	$(document).ready(function () {		
		if(locationDate.match("yyyy-MM-dd")) {
			finalDateFormatEDashboardeventCalendar = 'yy-mm-dd';
		} if(locationDate.match("dd-MM-yyyy")) {
			finalDateFormatEDashboardeventCalendar = 'dd-mm-yy'; 
		} if(locationDate.match("MM-dd-yyyy")) {
			finalDateFormatEDashboardeventCalendar = 'mm-dd-yy'; 
		} if(locationDate.match("yyyy-dd-MM")) {
			finalDateFormatEDashboardeventCalendar = 'yy-dd-mm'; 
		}
	});
	function eventCalendar(dates) {
		$("#eventCalendar").show();
		$("#jobFairCalendar").hide();
	    $("#eventCalendar").datepicker({
		  changeMonth: true,
	      changeYear: true,
	      dateFormat : finalDateFormatEDashboardeventCalendar,
	      onSelect : function(selected) {
	    	  getInterviewsandTasksForSelectedDate();
		   },
		    beforeShowDay: function(date) {
		  	  var hlText = dates[date.getTime()];
		        return (hlText)?[true, "Highlighted", '']: [true, '', ''];
		  	}
	    });
	}
  
	function getJobFairCalendar(dates) {
		$("#eventCalendar").hide();
		$("#jobFairCalendar").show();
		$("#jobFairCalendar").datepicker({
		  changeMonth: true,
	      changeYear: true,
	      dateFormat : finalDateFormatEDashboardeventCalendar,
	      onSelect : function(selected) {
	    	  getJobsForSelectedFairDate();
		   },
		    beforeShowDay: function(date) {
		  	  var hlText = dates[date.getTime()];
		        return (hlText)?[true, "HighlightedFair", '']: [true, '', ''];
		  	}
	    });	
	}
      
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
 
<script>
function feedbackView(interviewId,templateId){
	$(".feedback_view").show(200);
	$(".mask_inner").fadeIn(200);
	var sourceleadUrl ='${sourceleadUrl}';
	var locationId = '${SourceLeadWeb_orgLocationId}';
	$.ajax({
		type: "GET",
		url: sourceleadUrl+"/getInterviewrsFeedbackList?${_csrf.parameterName}=${_csrf.token}&interviewId="+interviewId+"&locationId="+locationId,
			success: function(data) {
				var json = JSON.parse(data);
				var result = "";
				result = result +'	<div id="#" class="require_wrap_table feedback_view overflow_inherint">                                     ';
				result = result +   '<a href="javascript:void(0)" class="close-div" onclick="closePop()"><i class="fa fa-close"></i></a>';
				result = result +'	<strong>Interview Feedback</strong>                                                       ';
				result = result +'<div class="feedbackInner">';
				result = result +'	<table id="requirementApproverPagination" cellspacing="0" width="100%" class="mt10">      ';
				result = result +'	<thead>                                                                                   ';
				result = result +'	<tr>                                                                                      ';
				result = result +'	<th>Interviewer Name</th>                                                                 ';
				result = result +'	<th>Date</th>';
				result = result +'	<th>Action</th>';
				result = result +'	</tr>';
				result = result +'	</thead>';
				result = result +'	<tbody>';
				for (var i=0; i<json.length; i++)
				{
					var report = json[i];
					if(report.feedbackDate != null && report.feedbackDate != '' && report.feedbackDate != 'undefined'){
						feedbackDate  = report.feedbackDate;
					}else{
						feedbackDate = "--";
					}
					 result = result +"  <tr>";  
					    result = result +'<td><div class="tbldata-dotdot-me">'+report.interviewerName+'</div></td>'; 
					    result = result +' <td><div class="tblminwidth-100">'+feedbackDate+'</div></td>'; 
					    if(report.feedbackDate != null && report.feedbackDate != '' && report.feedbackDate != 'undefined'){
					    	result = result +' <td><div class="tblminwidth-50"><span class="text-primary"><a onclick="viewfeedback('+report.id+',\''+templateId+'\',\''+report.feedbackStatus+'\');">View</a></span></div></td>';
					    }else{
					    	result = result +'<td><div class="tblminwidth-50"> &nbsp;<span class="text-danger">Notfilled</span></div></td>';
					    }
					     
					    result = result +'</tr>'; 
				}
				result = result +'	</tbody>';
				result = result +'	</table>';
				result = result +'</div> ';
				result = result +'</div> ';
				$("#feedbackInterviewerId").empty();  
				$("#feedbackInterviewerId").append(result);
				
			}
	});
	
}
</script>

<script>
function exportInterviewsListSheet(exportType){
	
	var date = getSelectedDateFromCalendar($("#eventCalendar").val());
		window.location.href = sourceleadUrl+"/getInterviewListExportList?"+tokenParamName+"="+tokenValue+"&exportType="+exportType+"&dateRange="+date;
	}
</script>

<script>
function closePop(){
$(".feedback_view").hide();
$(".mask_inner").hide();
}
</script>

<script>
function newActions(){
	$(".newActPop").slideToggle(200);
}
$("html").click(function(){
	$(".newActPop").slideUp(200);
});
$(".overIcon, .newActPop").click(function(e){
 	e.stopPropagation();
});
</script>


      