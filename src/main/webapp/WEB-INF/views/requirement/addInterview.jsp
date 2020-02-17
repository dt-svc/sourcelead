<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

 <c:if test="${staticTime == null || staticTime == 0 }">
    <c:set var='staticTime' value="<%=new java.util.Date().getTime() %>" scope="application"></c:set>
 </c:if>
 
<link rel="stylesheet" href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/style.css?staticTime=${staticTime}" />

<!-- For add New Interview -->

<script>
function addInterview(requirementId){
	$('.action-pop').slideUp();
	interviewTemplate();
	var parentRequirement = '${parentRequirement}';
	var clientSubmissions = '${clientSubmissions}';
	var result = "";
	result = result + '<div class="add_inter">';
	result = result + '<div class="clearfix">';
	result = result + '<div id="interviewLoading"></div>';
	result = result + '<form id="addInterview_form" name="addInterview_form" style="position:reletive; z-index:100;">';
	result = result + '<input type="hidden" id="maxTsRowNum" name="maxTsRowNum" value="1" />';
	result = result + '<input type="hidden" id="changeMaxTsRowNum" name="changeMaxTsRowNum" value="1" />';
	result = result + '<input type="hidden" id="reqId" name="requirementId" value='+requirementId+'>';
	result = result + '<input type="hidden" id="isFlushRequired" name="isFlushRequired">';
	
	 result = result + '<div class="add_inter_head"> <span>Schedule Interview </span><a href="javascript:void(0)" onClick="addInterviewRow();"><i class="fa fa-plus"></i> Add row <i class="fa fa-info-circle" title="Schedule multiple interviews for multiple applicants in different time slots"></i></a>'; 
// 	result = result + '<div class="add_inter_head"> <span> Schedule interview</span><span class="text-success col-md-offset-1">No Of Candidates Selected: <b id="selectedInterviewApplicantsCount">0</b></span>';
	result = result + '<div class="clearfix"></div>';
	result = result + '</div>';
	
	if(clientSubmissions == 'Y'){
	    result = result + '<input type="checkbox" name="isClientInterview" id="isClientInterview" class="isClient" onclick="myFunction();">';
        result = result + '<label>&nbsp;&nbsp;<strong>Is Client Interview</strong>&nbsp;&nbsp;</label>';
	} 
	
	result = result + '<table cellpadding="0" cellspacing="0" id="addInterviewTbl" class="addinterTable addinterTableStyles">';
	result = result + '<tbody>';
		result = result + '<tr id="divTsRow0">';
			result = result + '<th>Applicant</th>';
			result = result + '<th>Interview Type</th>';
			result = result + '<th>Interview Date</th>';
			result = result + '<th>Start - End Time</th>';	
			result = result + '<th>Time Zone</th>';
			result = result + '<th>Details</th>';
			result = result + '<th>Interviewer</th>';
			result = result + '<th>Action</th>';
 			//result = result + '<th>Avail</th>';
		result = result + '</tr>';
	result = result + '</tbody>';
	result = result + '</table>';
	
	
	result = result + '<div class="schedule_widget clearfix mb eventAlert" style="display:none;" id="availbleDiv">        								        ';
	result = result + '    <div class="row">                                                                            ';
	result = result + '      <div class="col-xs-12 col-md-12 event_calendar">                                           ';
	result = result + '        <h4>Available Slots <i class="fa fa-info-circle" title="Check the available time slots for Applicants & Interviewers"></i></h4>                                                               ';
	result = result + '      </div>                                                                                     ';
	result = result + '      <!-- show Calendar -->                                                                     ';
	result = result + '      <div class="col-xs-12 col-md-5 mb">                                                        ';
	result = result + '        <div id="scheduleCalendar"></div>                                                        ';
	result = result + '        <div class="clearfix"></div>                                                             ';
	result = result + '        <ul class="scheduleAvailable">                                                           ';
	result = result + '          <li class="particalCount">Partially available<span class="bg-warning"></span></li>                              ';
	result = result + '          <li class="fullCount">Full<span class="bg-danger"></span></li>                                           ';
	result = result + '        <div class="clearfix"></div> ';
	result = result + '        </ul>                                                                                    ';
	result = result + '        <div class="clearfix">                                                                   ';
	result = result + '      <div class="timeZT">Timezone : <b id="selectedTimeZoneId"></b></div>'; 
	result = result + '          <button type="button" class="btn btn-primary pull-right" onClick = "setDate();">Pick</button>              ';
	result = result + '        </div>                                                                                   ';
	result = result + '      </div>                                                                                     ';   
	result = result + '      <!-- show Calendar -->                                                                     ';
	result = result + '      <div class="col-xs-12 col-md-7 mb alert alert-dismissable prBG">	';
	result = result + '      <div class="PrDate">	';
	result = result + ' 	<i class="fa fa-calendar"></i><b id="selectedDate"></b><div class="clearfix"></div><p id="selectApp"></p> ';
	result = result + '      </div>	';
	result = result + '        <ul class="nav nav-tabs">                                                                ';
	result = result + '          <li class="active"> <a href="#interviewer" data-toggle="tab">Interviewer</a> </li>     ';
	result = result + '          <li> <a href="#applicant" data-toggle="tab">Applicant</a> </li>         ';
	result = result + '        </ul>                                                                                    ';
	
	result = result + '        <div class="tab-content">                                                                ';
	result = result + '          <div id="interviewer" class="tab-pane fade in active">                                   ';
	result = result + '          </div>                                                                                 ';
	
	result = result + '          <div id="applicant" class="tab-pane fade">                                           ';
	result = result + '          </div>                                                                                 ';
	result = result + '        </div>                                                                                   ';
	result = result + '      </div>                                                                                     ';
	result = result + '    </div>                                                                                       ';
	result = result + '  </div>                                                                                         ';
	
	result = result + '<div class="row pad2">';
	result = result + '<div class="col-md-12 mt10">';
	result = result + '<div class="ne-ch">';
	result = result + '<label><input type="checkbox" name="notificationToApplicant" value="Y"><b>Notify to Applicants</b></label>';
	if(parentRequirement == 'Y'){
	   result = result + '<label><input type="checkbox" name="notificationToSpoc" value="Y" checked="checked"><b>Notify to Vendors</b></label>';
	}
	result = result + '<label><input type="checkbox" name="notificationToOthers" id="notificationToOthers" onClick="getNotifyToOtersValInterview();showOthersDivInterview();"><b>Notify to Others <i class="fa fa-info-circle" title="Notify the schedule to those other than the interviewers using their respective email ids"></i></b></label>';

	result = result + '<label><input type="checkbox" name="proposeTime" value="Y" checked="checked" ><b>Propose time <i class="fa fa-info-circle" title="Applicants can propose their available time slots"></i></b></label>';
	
	result = result + '</div>';
	
	result = result + '<div class="mb10 mt clearfix" id="notifyOthersDiv" style="display: none;">';
	result = result + '<div class="col-md-6">';
	result = result + '<input type="hidden" class="form-control input-sm mt10" value="" id="notificationToOthersEmails" name="notificationToOthersEmails">';
	result = result + '<ul id="mails" class="form-control"></ul>';
	result = result + '</div>';
	result = result + '</div>';
	
	result = result + '<div class="mb10 mt clearfix interview-form">';
	
	result = result + '<div id="emptyTemplate" style="dispaly:none">';
	result = result + '<strong class="clearfix col-md-12">Interview Template <i class="fa fa-info-circle" title="The interview feedback template"></i></strong>';
	 result = result +'<div class="col-md-6 chooRadius mb">';
	 result = result +'<div class="input-group col-md-10 pull-left chooOverFlow">';
	result = result + '<select class="form-control mt10" id="interviewSubmissionTemplate" name="interviewSubmissionTemplate">';
	result = result + '</select>';
	result = result +'<span class="input-group-addon cursorPointer bg-blue" onclick="interviewTemplate();"><i class="fa fa-refresh "></i></span>';
	result = result + '</div>';
	result = result +'<span class="col-md-2 noPad clearfix">';
	result = result +'<sec:authorize access="hasAnyRole(\'ROLE_CREATE_ON_BOARD_TEMPLATE\')">';	
	result = result +'<a href="${SourceLeadWeb_atsUrl}/interViewFeedBackTemplate" target="_blank"><i class="fa fa-plus btn btn-default pull-right minHeBtn"></i></a>';
	result = result +'</sec:authorize>';	
	result = result + '</span>';
	result = result + '</div>';
	
	
	result = result + '<strong class="clearfix col-md-12 mb">Reminder Before <i class="fa fa-info-circle" title="Set a reminder about the scheduled interview to both the interviewers and applicants"></i></strong>';
	result = result + '<div class="col-md-3 select-field">';
	result = result + '<span>Hours</span>';
	result = result + '<select class="form-control mt10" id="reminderHours" name="reminderHours">';
	result = result + '<c:forEach begin="0" end="24" var="hour">';
	result = result +     '<option value="${hour}">';
	result = result +         '<fmt:formatNumber value="${hour}" type="number" minIntegerDigits="2"/>';
	result = result +     '</option>';
	result = result + '</c:forEach>';
	result = result + '</select>';
	result = result + '</div>';
	
	
	result = result + '<div class="col-md-3 select-field">';
	result = result + '<span>Minutes</span>';
	result = result + '<select class="form-control mt10" id="reminderMinutes" name="reminderMinutes">';
	result = result + '<c:forEach begin="0" end="55" step="5" var="minute">';
	result = result +     '<option value="${minute}" ${minute  == 30 ? "selected" : ''}>';
	result = result +         '<fmt:formatNumber value="${minute}" type="number" minIntegerDigits="2"/>';
	result = result +     '</option>';
	result = result + '</c:forEach>';
	result = result + '</select>';
	result = result + '</div>';
	result = result + '</div>';
          
	result = result + '</div>';
	result = result + '</div>';
	
	result = result + '<div class="clearfix">';
	result = result + '<div class="pull-right">';
	result = result + '<button type="submit" id="add_interview" class="btn btn-sm btn-primary mr">Submit</button>';
	result = result + '<button type="button" class="btn btn-sm btn-danger" onClick="cancelAddInterviewDiv();">Cancel</button>';
	result = result + '</div>';
	result = result + '</div>';
	result = result + '</div>';

	
	
	
	result = result + '</div>';
	result = result + '</div>';   
	result = result + '</form>';
	result = result + '<div id="interviewLoading"></div>';
	result = result + '</div><div id="interviewPopUp">';
	
				
	$("#addInterviewDiv").empty();
	$("#addInterviewDiv").append(result);
	addInterviewRow();
	$(".add_inter_wrap").addClass("add_inter_wrap_active");
	$(".side_mask").fadeIn(200);
	$("body").css('overflow-y', 'hidden');
	
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
function showOthersDivInterview(){
	var isNofityToOthers = document.getElementById("notificationToOthers").checked;
	if (isNofityToOthers) {
		document.getElementById("notifyOthersDiv").style.display = '';
		 $('#mails').tagit({
		       singleField: true,
		       singleFieldNode: $('#notificationToOthersEmails')
		       ,placeholderText:"Enter emails"
		       ,allowDuplicates: false
		    	,afterTagAdded: function(evt, ui){
			              checkEmailsInterview();
			              return false;
			    }
		       ,afterTagRemoved: function(evt, ui){
	              checkEmailsInterview();
	              return false;
	         }
		   });
	}else {
		document.getElementById("notifyOthersDiv").style.display = 'none';
		$('#mails').tagit('removeAll');
	}
}
</script>

<script language="javascript">
function checkEmail(email) {
   var regExp = /^.+@.+\..{2,3}$/
   return regExp.test(email);
}

function checkEmailsInterview(){
	var emails = document.getElementById("notificationToOthersEmails").value;
	var emailArray = emails.split(",");
	var invEmails = "";
	for(i = 0; i <= (emailArray.length - 1); i++){
		if(checkEmail(emailArray[i])){
			//Do what ever with the email.
		}else{
			invEmails += "'" +emailArray[i]+ "'"+ "\n";
		}
	}
	
	
	if(invEmails != "" && invEmails.length != 3 && invEmails != null && invEmails !="undefined" && invEmails !="\n"){
	      $("#add_interview").attr("disabled", "disabled");
		  $('#notifyOthersDiv').find('p.jquery_form_error_message').remove();
		  $("input#notificationToOthersEmails").after("<p class='jquery_form_error_message'>"  +invEmails+" Invalid emails</span>");
		  document.getElementById("notificationToOthersEmails").setAttribute('record-exist','yes');
		  document.getElementById("notificationToOthersEmails").setAttribute('record-exist-errorMsg',invEmails+' Invalid emails'); 
	}else{
		$('input#notificationToOthersEmails').removeAttr( "record-exist record-exist-errormsg");
		$('#notifyOthersDiv').find('p.jquery_form_error_message').remove(); 
        $("#add_interview").removeAttr("disabled");
	}
}
</script> 
<script>
function validateAddInterviewOthersMail(){
var dataNotify =document.getElementById("notificationToOthersEmails").value;
var isNofityToOthers = document.getElementById("notificationToOthers").value;
if((dataNotify == '' || dataNotify == 0)){
	  $("#add_interview").attr("disabled", "disabled");
	  $('#notifyOthersDiv').find('p.jquery_form_error_message').remove();
	  $("input#notificationToOthersEmails").after("<p class='jquery_form_error_message'>Please enter Email</span>");
	  document.getElementById("notificationToOthersEmails").setAttribute('record-exist','yes');
	  document.getElementById("notificationToOthersEmails").setAttribute('record-exist-errorMsg','Please enter Email'); 
}else{
	$('input#notificationToOthersEmails').removeAttr( "record-exist record-exist-errormsg");
	$('#notifyOthersDiv').find('p.jquery_form_error_message').remove(); 
    $("#add_interview").removeAttr("disabled");
}
}
</script>
<script>
function selectInterviewApplicants(){
	$('.eventAlert').hide();
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
}
</script>

<script>
function validateTimeZone(){
	var timezoneVal = $("#1timeZone").val();
	if(timezoneVal !=null && timezoneVal != '' && timezoneVal != 'undefined'){
		$('#1timeZone_chosen').next("p").remove();
	}
	else{
		$('#1timeZone_chosen').next("p").remove();
		$('#1timeZone_chosen').after('<p class="jquery_form_error_message">Timezone is Required</p>');
	}
}
</script>




<div id="divTaskTd1" style="display: none; float: left;" class="positionRelative">
<input type="hidden" name="externalInterviewersList[REPLACEROWID]" id="REPLACEROWIDexternalInterviewersList"/>
<small class='requiresymbol text-danger'>*</small>
       <select  multiple data-placeholder="Select Applicants" onchange="selectInterviewApplicants()" class="blminwidth-50" id="REPLACEROWIDapplicant" name="REPLACEROWIDapplicant" field-name="Applicant" data-validation="required" >
			
	  </select>
</div>

<div id="divTaskTd3" style="display: none; float: left;">
<small class='requiresymbol text-danger'>*</small>
   	 <select class="form-control input-sm"  id="REPLACEROWIDinterviewType" name="REPLACEROWIDinterviewType" onchange="interviewType(this);" field-name="Interview type" data-validation="required">
		<option value="">--Select Type--</option>
		<option value="F2F">F2F</option>
		<option value="Telephonic">Telephonic</option>
		<option value="Technical">Technical</option>
		<option value="Screening">Screening</option>
		<option value="Skype">Skype</option>
		<option value="Webex/GoToMeeting">Webex/GoTo Meeting</option>
		<option value="Other">Other</option>
	</select>
	<div id="REPLACEROWIDinterviewTypeCheck" style="display: none; "><input value=""  type="text" id="REPLACEROWIDinterviewTypeother" name="REPLACEROWIDinterviewTypeother" class="form-control input-sm pavInp" placeholder="Interview type" field-name="Interview type" data-validation="required"></div>
</div>

<div id="divTaskTd5" style="display: none; float: left;">
   	 <select class="form-control" id="REPLACEROWIDtimeZone" name="REPLACEROWIDtimeZone" onchange="validateTimeZone()" field-name="Timezone" data-validation="required">
	</select>
</div>

<div id="divTaskTd6" style="display: none; float: left;">
	  <input value="" type="text" id="REPLACEROWIDlocation" name="REPLACEROWIDlocation" class="form-control input-sm" placeholder="Details" field-name="Details" >
</div> 

<div id="divTaskTd7" style="display: none; float: left;">
		  <select multiple data-placeholder="select interviewer" id="REPLACEROWIDinterviewer" name="REPLACEROWIDinterviewer" field-name="Interviewer" onchange="toggleEventCalender();">
			
	  </select>
</div> 


<div id="divTaskTd8"  style="display: none; float: left;" >
	 <button type="button" class="btn-sm btn-success btn_Avail" title="External Interviewers" id ="REPLACEROWIDexternalInterviewers" onClick="getExternalInterviewers(REPLACEROWID);"><i class="fa fa-user-plus"></i></button> 
	 <button type="button" class="btn-sm btn-primary btn_Avail" title="Available slots" id ="REPLACEROWIDAvail" onClick ="showCal(this.id);"><i class="fa fa-clock-o"></i></button>
     
     <input type="hidden" id="external_button_id" name="external_button_id" value="1externalInterviewers" />
	 <input type="hidden" id="button_id" name="button_id" value="1Avail" />
	 <a href="#" title="Remove" onclick="removeInterviewRow(REPLACEROWID);"><i class="text-danger glyphicon glyphicon-remove deme"></i></a>

</div>  

<div id="externalInterviewersDiv"></div>

 <div id="divInterviewerTd1" style="display: none; float: left;">
	<input type="text" class="form-control input-sm" placeholder="Interviewer Name" data-validation="required" field-name="Interviewer Name" name="interviewerName" id="REPLACEROWIDinterviewerName">
 </div>
  
  <div id="divInterviewerTd2" style="display: none; float: left;">
           <input type="text" class="form-control input-sm" placeholder="Email" data-validation="required validate_email"  field-name="Email" name="interviewerEmail" id="REPLACEROWIDinterviewerEmail">
  </div>
  
  <div id="divInterviewerTd3" style="display: none; float: left;">
     <div class="pull-left">
   			<button type="button" class="btn btm-sm pull-right mt5" onclick="removeInterviewerType(REPLACEROWID)"><i class="fa fa-remove text-danger"></i></button>
     </div>
</div> 

<script>

  function getRowDetails(result,rowId,interviewerName,interviewerEmail){
	  if(interviewerName == null || interviewerName == '' || interviewerName == 'undefined'){
		  interviewerName = '';
	  }
	  
	  if(interviewerEmail == null || interviewerEmail == '' || interviewerEmail == 'undefined'){
		  interviewerEmail = '';
	  }
	  
	    result = result +'<tr id="divInterviewerRow'+rowId+'">';
		result = result +'<td width="200px">';
		result = result +'<input type="text" class="form-control input-sm" value="'+interviewerName+'" placeholder="Interviewer Name"  data-validation="required" field-name="Interviewer Name" id="'+rowId+'interviewerName">';
		result = result +'</div>';
		result = result +'</td>';
		result = result +'<td width="200px">';
		result = result +'<input type="text" class="form-control input-sm" value="'+interviewerEmail+'" placeholder="Email" data-validation="required validate_email" field-name="Email" id="'+rowId+'interviewerEmail">';
		result = result +'</td>';
		result = result +'<td width="30px">';
		result = result +'<div class="pull-left">';
		if(rowId == 0){
			result = result +'<button type="button" class="btn btm-sm pull-right mt5" onclick="addInterviewerRow();"><i class="fa fa-plus text-primary"></i></button>';
		}else{
			result = result +'<button type="button" class="btn btm-sm pull-right mt5" onclick="removeInterviewerType('+rowId+')"><i class="fa fa-remove text-danger"></i></button>';
		}
		result = result +'</div>';
		result = result +'</td>';
		result = result +'</tr>';
		
		return result;
  }
</script>
<script>
function getExternalInterviewers(rowId){
	var externalInterviewersList = $("#"+rowId+"externalInterviewersList").val();
	var interviewerMaxRowNumber = 1;
	var arr = '';
	if(externalInterviewersList != ''){
	    arr = JSON.parse(externalInterviewersList);
		interviewerMaxRowNumber = arr.length;
	}
	
	$("#externalInterviewersDiv").empty();
	var result = "";
	result = result + '	<div class="settPop">';
	result = result + '<form id="externalInterviewer_form" name="externalInterviewer_form" method="post">';
	result = result + '	<h3>External Interviewers<i class="fa fa-times" onclick="cancelExternalInterviewers();"></i></h3>  ';
	result = result + '	<div class="settPopInn"> ';
	
	result = result +'<div class="clearfix requestdocmntTable">';
	result = result +'<input type="hidden" name="interviewRowId"  id="interviewRowId" value='+rowId+' />';
	result = result +'<input type="hidden" name="interviewerMaxRowNumber"  id="interviewerMaxRowNumber" value="'+interviewerMaxRowNumber+'" />';
	result = result +'<table class="table table-responsive table-bordered table-hover" width="100%" id="interviewerDocumentsTable" border="0">';
	result = result +'<tbody>';
	
	var rowId = 0;
	if(arr != null && arr != 'undefined' && arr != ''){
		for(var i=0; i<interviewerMaxRowNumber; i++){
			rowId = i; 
		    result = getRowDetails(result,rowId,arr[i].interviewerName,arr[i].interviewerEmail);
		}
	}else{
		result = getRowDetails(result,rowId);
	}
	 
	result = result +'</tbody>';
	result = result +'</table>';
	result = result +'</div>';
	
	result = result + '		<div class="col-md-12 mt5 clearfix"> ';
	result = result + '		<div class="pull-right">  ';
	result = result + '		  <input type="submit" value="Add" id="submit_rejectedReason"  class="btn btn-primary" >';
	result = result + '		  <input type="button" value="Cancel" class="btn btn-default ml10" onclick="cancelExternalInterviewers();">';
	result = result + '		</div>';
	result = result + '		</div>';
	result = result + '		<div class="clearfix"></div>   ';
	result = result + '	</div>  ';
	result = result + '</form>';
	result = result + '	</div>';
	
	$("#externalInterviewersDiv").append(result);
	$(".settPop").slideDown(200);
	$(".vendor_wrap_mask").fadeIn(200);
}

</script>


<script type="text/javascript">
$('body').on('submit', '#externalInterviewer_form', function(e) {
	  if ($(this).validate(false, validationSettings)) {
	   	e.preventDefault();
	  	var checkMaxTsRowNum = $("#interviewerMaxRowNumber").val();
	  	var interviewRowId = $("#interviewRowId").val();
	  
	     var result = [];
	     for(var i=0;i<checkMaxTsRowNum;i++){
	    	 if($("#"+i+"interviewerName").val() != null && $("#"+i+"interviewerName").val() != '' && $("#"+i+"interviewerName").val() != 'undefined'){
	    		 var obj = {
	    		    		"rowId" : i,
	    		    		"interviewerName" : $("#"+i+"interviewerName").val(),
	    		    		"interviewerEmail" : $("#"+i+"interviewerEmail").val(),
	    		    	 }
	    		    	 result.push(obj);
	    	 }
	     }
	     $("#"+interviewRowId+"externalInterviewersList").val(JSON.stringify(result));
	     cancelExternalInterviewers(); 
	      return true; 
	    }
	    else {
	    	return false;	
		}
});

$('body').on('blur', '#externalInterviewer_form', function() {
	$("#externalInterviewer_form").showHelpOnFocus();
	$("#externalInterviewer_form").validateOnBlur(false, validationSettings);
});
</script>


<script>
function addInterviewerRow(){
	var checkMaxTsRowNum = $("#interviewerMaxRowNumber").val();
	var tbl = document.getElementById("interviewerDocumentsTable");
	var maxRowNum = parseInt($("input[name = 'interviewerMaxRowNumber']").val());
	$("input[name = 'interviewerMaxRowNumber']").val(maxRowNum + 1);
	
	var row = tbl.insertRow(maxRowNum);
	row.setAttribute("id", "divInterviewerRow" + maxRowNum);
	
	
	var cell0 = row.insertCell(0);
	var divDocTypeTdStr = document.getElementById("divInterviewerTd1").innerHTML;
	divDocTypeTdStr = divDocTypeTdStr.replace(/REPLACEROWID/g, (maxRowNum));
	cell0.innerHTML = divDocTypeTdStr;
	
	var cell1 = row.insertCell(1);
	var divDocDescTdStr = document.getElementById("divInterviewerTd2").innerHTML;
	divDocDescTdStr = divDocDescTdStr.replace(/REPLACEROWID/g, (maxRowNum));
	cell1.innerHTML = divDocDescTdStr; 
	
	var cell2 = row.insertCell(2);
	var divDocDescTdStr = document.getElementById("divInterviewerTd3").innerHTML;
	divDocDescTdStr = divDocDescTdStr.replace(/REPLACEROWID/g, (maxRowNum));
	cell2.innerHTML = divDocDescTdStr; 
	
}

function removeInterviewerType(rowId){
	 document.getElementById("divInterviewerRow"+rowId).style.display = 'none';
	 document.getElementById("divInterviewerRow"+rowId).value= '';
	 var  rId = "divInterviewerRow"+rowId;
	  var r = document.getElementById("interviewerDocumentsTable").rows[rId];
     var count = 2*(r.cells.length);
	    for(i=0;i<=count;i++){
	        r.deleteCell(0);
	    } 
}
</script>


<script>
function cancelExternalInterviewers(){
	$(".settPop").slideUp(200);
	$(".vendor_wrap_mask").fadeOut(200);
}
</script> 


<script type="text/javascript">
	function addInterviewRow() {
		$("#isFlushRequired").val('N');
		var checkMaxTsRowNum = $("#maxTsRowNum").val();
		var tbl = document.getElementById("addInterviewTbl");
		var maxRowNum = parseInt($("input[name = 'maxTsRowNum']").val());
		$("input[name = 'maxTsRowNum']").val(maxRowNum + 1);
		
		var changeMaxRowNum = parseInt($("input[name = 'changeMaxTsRowNum']").val());
		$("input[name = 'changeMaxTsRowNum']").val(changeMaxRowNum + 1);
		
		
		var row = tbl.insertRow(maxRowNum);
		row.setAttribute("id", "divTsRow" + maxRowNum);
		var cell0 = row.insertCell(0);
		var divTsTd1Str = document.getElementById("divTaskTd1").innerHTML;
		divTsTd1Str = divTsTd1Str.replace(/REPLACEROWID/g, (maxRowNum));
	    divTsTd1Str = divTsTd1Str.replace(/ROWNUMBERVALUE/g, ("")); 
		cell0.innerHTML = divTsTd1Str;
		
		
		var cell1 = row.insertCell(1);
		var divTsTd3Str = document.getElementById("divTaskTd3").innerHTML;
		divTsTd3Str = divTsTd3Str.replace(/REPLACEROWID/g, (maxRowNum));
		cell1.innerHTML = divTsTd3Str;
		
		var text = "";
		var cell2 = row.insertCell(2);
		/* cell2.innerHTML = "<div class='positionRelative date'><input class='form-control input-sm Datepicker' placeholder='Date' name='"+maxRowNum+"dateOfInterview' id='"+maxRowNum+"dateOfInterview'  readonly='readonly'  type='text' data-validation='required' field-name='Interview Date' > <span class='input-group-addon input-groupAddon'><i class='fa fa-calendar'></i></span> </div>"; */
		text = text+"<div class='positionRelative date' id='inteviewDatePic'><small class='requiresymbol2 text-danger'>*</small><input class='form-control input-sm Datepicker' placeholder='Select Date' name='"+maxRowNum+"dateOfInterview' id='"+maxRowNum+"dateOfInterview'  readonly='readonly'  type='text' data-validation='required' field-name='Interview Date'>";
		text = text+"</div>";
		
		
		//$("#timezoneid").trigger("chosen:updated");
		
		var locationDate ='${SourceLeadWeb_dateFormat}'; 
		  var finalDateFormatInterview = '';
		  if(locationDate.match("yyyy-MM-dd")){
			  finalDateFormatInterview = 'yy-mm-dd';
		  }if(locationDate.match("dd-MM-yyyy")){
			  finalDateFormatInterview = 'dd-mm-yy'; 
		  }if(locationDate.match("MM-dd-yyyy")){
			  finalDateFormatInterview = 'mm-dd-yy'; 
		  }if(locationDate.match("yyyy-dd-MM")){
			  finalDateFormatInterview = 'yy-dd-mm'; 
		  }
		
		cell2.innerHTML = text;
		//$(".Datepicker").datepicker();
		$(".Datepicker").datepicker({
			dateFormat : finalDateFormatInterview,
			changeMonth: true,
			 changeYear: true, 
		      /* minDate: 0  , */
		      onSelect : function(selected) {
		    	  removeInterviewvalidation();
		      }
		});
		var cell3 = row.insertCell(3);
		cell3.innerHTML = "<div class='pull-left timeZone' id='"+maxRowNum+"timeDiv'> <span class='positionRelative'><small class='requiresymbol2 text-danger'>*</small><input class='form-control input-sm timepicker' placeholder='Start Time' name='"+maxRowNum+"startTime' id='"+maxRowNum+"startTime' onBlur='validateInterEndTime("+maxRowNum+");' type='text' data-validation='required' field-name='Start Time'/></span> <span class='positionRelative'><input class='form-control input-sm timepicker' placeholder='End Time' name='"+maxRowNum+"endTime' id='"+maxRowNum+"endTime' onBlur='validateInterEndTime("+maxRowNum+");' type='text' field-name='End Time' /></span> </div> ";
		
		$("#"+maxRowNum+"startTime").timepicker({
			'showDuration': true,
			'disableTextInput': true
		}).on('changeTime', function() {
            $("#"+maxRowNum+"endTime").timepicker('option', 'minTime', $(this).val());
        });
		
		$("#"+maxRowNum+"endTime").timepicker({
			'showDuration': true,
			 'maxTime': '23:30:00',
			'disableTextInput': true
		}).on('changeTime', function() {
            $("#"+maxRowNum+"startTime").timepicker('option', 'maxTime', $(this).val());
        }); 
		
		
		
		var cell4 = row.insertCell(4);
		var divTsTd5Str = document.getElementById("divTaskTd5").innerHTML;
		divTsTd5Str = divTsTd5Str.replace(/REPLACEROWID/g, (maxRowNum));
		divTsTd5Str = divTsTd5Str.replace(/PRICECALCULATION/g,("")); 
		cell4.innerHTML = divTsTd5Str;  
		
		var cell5 = row.insertCell(5);
		var divTsTd6Str = document.getElementById("divTaskTd6").innerHTML;
		divTsTd6Str = divTsTd6Str.replace(/REPLACEROWID/g, (maxRowNum));
		cell5.innerHTML = divTsTd6Str;  
		
		var cell6 = row.insertCell(6);
		var divTsTd7Str = document.getElementById("divTaskTd7").innerHTML;
		divTsTd7Str = divTsTd7Str.replace(/REPLACEROWID/g, (maxRowNum));
		cell6.innerHTML = divTsTd7Str; 
		
		var cell7 = row.insertCell(7);
		var divTsTd8Str = document.getElementById("divTaskTd8").innerHTML;
		divTsTd8Str = divTsTd8Str.replace(/REPLACEROWID/g, (maxRowNum));
		cell7.innerHTML = divTsTd8Str;
		/* 
		 var cell8 = row.insertCell(8);
		var divTsTd9Str = document.getElementById("divTaskTd9").innerHTML;
		divTsTd9Str = divTsTd9Str.replace(/REPLACEROWID/g, (maxRowNum));
		cell8.innerHTML = divTsTd9Str;  */
		
		getInterviewers(maxRowNum);
		getShortListCandidates(maxRowNum);
		getTimezonesList(maxRowNum);

		
	    $("#"+maxRowNum+"applicant").chosen();
	    $("#"+maxRowNum+"interviewer").chosen();
	    $("#"+maxRowNum+"timeZone").chosen();
	    $("#"+maxRowNum+"timeZone").trigger('chosen:update');
	    $("#"+maxRowNum+"interviewType").chosen();
		$("#"+maxRowNum+"interviewType").trigger('chosen:update');
		
	  
		    
		    
				
}		
</script>
<script>
function removeInterviewvalidation(){
	$('#inteviewDatePic').find('p.jquery_form_error_message').remove();	
}
</script>
<script type="text/javascript">
	function validateInterEndTime(maxRowNum) {
		var rowId = $("#maxTsRowNum").val();
		 var allStartTimes = [];
		 var allEndTimes = [];
		 $("#"+maxRowNum+"timeDiv").find('p.jquery_form_error_message').remove();
		 
		for(i=1 ; i<=rowId; i++){
			var startTime = $("#"+(i)+"startTime").val();
			 var endTime = $("#"+(i)+"endTime").val();
			if(endTime != null && endTime != '' && endTime != 'undefined'){
				if(startTime == endTime){
					 $("#"+(i)+"timeDiv").find('p.jquery_form_error_message').remove();
			         $("#add_interview").removeAttr("disabled");
			        $("#add_interview").attr("disabled", "disabled");
			        $("#"+(i)+"timeDiv").append("<p class='jquery_form_error_message'>End Time should greater than Start Time</span>");
			    }else{
			         $("#"+(i)+"timeDiv").find('p.jquery_form_error_message').remove();
			         $("#add_interview").removeAttr("disabled");
			   	}
			}
		}
		 for(i=1 ; i<=rowId; i++){
			var startTime1 = $("#"+(i)+"startTime").val();
			 var endTime1 = $("#"+(i)+"endTime").val();
			if(endTime1 != null && endTime1 != '' && endTime1 != 'undefined'){
				if(startTime1 == endTime1){
					 $("#add_interview").attr("disabled", "disabled");
				}
			}
		 }
		
	}
</script>

<script type="text/javascript">
	function removeInterviewRow(rowId) {
		var rowNum = $("#divTsRow"+rowId).val();
		var changeMaxRowNum = parseInt($("input[name = 'changeMaxTsRowNum']").val());
		$("input[name = 'changeMaxTsRowNum']").val(changeMaxRowNum - 1);
		
		var maxRowNum = parseInt(rowNum);
		 document.getElementById("divTsRow"+rowId).style.display = 'none';
		 document.getElementById("divTsRow"+rowId).value= '';
		 var  rId = "divTsRow"+rowId;
		  var r = document.getElementById("addInterviewTbl").rows[rId];
          var count = 2*(r.cells.length);
		    for(i=0;i<=count;i++){
		        r.deleteCell(0);
		    } 
	}
</script>


<script type="text/javascript">
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };
</script> 

<script type="text/javascript">
	(function($) {
		$.fn.serializeFormJSON = function() {
			var o = {};
			var a = this.serializeArray();
			$.each(a, function() {
				if (o[this.name]) {
					if (!o[this.name].push) {
						o[this.name] = [ o[this.name] ];
					}
					o[this.name].push(this.value || '');
				} else {
					o[this.name] = this.value || '';
				}
			});
			return o;
		};
	})(jQuery);
</script>

<script>
function cancelAddInterviewDiv(){
	 $(".add_inter_wrap").removeClass("add_inter_wrap_active");
	 $(".side_mask").fadeOut(200);
 	 $("body").css('overflow-y', 'auto');
}

</script>

<script>
function getNotifyToOtersValInterview(){
    if(document.getElementById('notificationToOthers').checked) {
        $("#notificationToOthers").val('Y');
        $("#notifyOthersDiv").find('p.jquery_form_error_message').remove();
    }else{
        $("#notificationToOthers").val('N');
        $("#notifyOthersDiv").find('p.jquery_form_error_message').remove();
        $("#add_interview").removeAttr("disabled");
    }
}

</script>

<script type="text/javascript">
   	$('body').on('submit', '#addInterview_form', function(e){
   	var isNofityToOthers = document.getElementById("notificationToOthers").value;
   		if(isNofityToOthers == "Y"){
   			validateAddInterviewOthersMail();
   		}else{
   			$('input#notificationToOthersEmails').removeAttr( "record-exist record-exist-errormsg");
   			$('#notifyOthersDiv').find('p.jquery_form_error_message').remove(); 
   		    $("#add_interview").removeAttr("disabled");
   		}
	  if ($(this).validate(false, validationSettings)){
        e.preventDefault();
        var frm = $('#addInterview_form').serializeFormJSON();
        var con = JSON.stringify(frm);
        var jsonData = encodeURIComponent(con);
        var requirementId = '${requirementId}';

        var maxRowNum = parseInt($("input[name = 'changeMaxTsRowNum']").val());
        
        if(maxRowNum >= 2){
	        $("#add_interview").attr("disabled", "disabled");
	 		$("#add_interview" ).addClass('button').val('Processing..');
	 		$("#interviewLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	   	     $.ajax({
		    	type: "POST",
		        url: atsUrl+"/saveInterview?"+tokenParamName+"="+tokenValue+"&jsonData="+jsonData,
		        success: function(result) {
		        	    $(".add_inter_wrap").removeClass("add_inter_wrap_active");
		        		$(".side_mask").fadeOut(200);
		        		$("body").css('overflow-y', 'auto');
		        		var allApplicants = 'all';
			    		requirementPipeline(requirementId,allApplicants); 
			    		pipelineList(requirementId);
		        },
		    });     
        }
        else if(maxRowNum < 2){
        	alertInterviewMessage("<b>Select an applicant before submission</b>");
        }
        /* else{
        	alertInterviewMessage("<b>Selected Applicants should not more than 50</b>");
        } */
   	    
   	    
        return true;
      }
      else{
      	return false;	
         }
    });
    	
   	$('#addInterview_form').showHelpOnFocus();
    $('#addInterview_form').validateOnBlur(false, validationSettings); 
  
  </script>
  
  <script type="text/javascript">
function alertInterviewMessage(message){
	var result = "";
	result = result +"	<div class='project_inn_mask' id='IntePop'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>"+message+"</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='cancelInterviewPopup();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelInterviewPopup();'></b>";
	result = result +"	</div>";
	$("#interviewPopUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$("#IntePop").fadeIn(200);

}
</script>

<script type="text/javascript">
function cancelInterviewPopup(){
	$("#interviewPopUp").empty();
	$("#IntePop").fadeOut(200);
}
</script>
  
<script type="text/javascript">
 function getShortListCandidates(rowId) {
	 var isWorkflowConfigured = '${workflowConfigured}';
	 $("#interviewLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 var clientSubmissions = '${clientSubmissions}';
	 var isClientInterview = "";
	 if(clientSubmissions == 'Y'){
		 if(rowId == null || rowId == "undefined" || rowId == ""){
	     	rowId = $("#maxTsRowNum").val() - 1;
	     }
		 if(document.getElementById('isClientInterview').checked) {
		        isClientInterview = 'Y';
	     }else{
	    	isClientInterview = 'N';
	     }
	 }
	
	 var existingApplicantsIds = [];    
	 for(i=rowId ; i > 0; i--){
		 var applicant = $("#"+(i)+"applicant").val();
		 if(applicant != null && applicant != '' && applicant != 'undefined'){
			 existingApplicantsIds.push(applicant);
		 }
	 }
	 var requirementId = $("#reqId").val();
     
		 $.ajax({
		    	type: "POST",
		        url: atsUrl+"/getAllShortListCandidates?"+tokenParamName+"="+tokenValue+"&requirementId="+requirementId+"&existingApplicantsIds="+existingApplicantsIds+
		        		"&isClientInterview="+isClientInterview+"&isWorkflowConfigured="+isWorkflowConfigured,  
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
				        		if(opt.createdUser != "null" && opt.createdUser != null && opt.createdUser !='' && opt.createdUser != 'undefined'){
				        			console.log(opt.candidateName+"("+opt.createdUser+")");
				        			output = output + "<option value='" +opt.id+ "'>"+opt.candidateName+" ("+opt.createdUser+")</option>";	
				        		}else{
				        			output = output + "<option value='" +opt.id+ "'>"+opt.candidateName+"</option>";
				        		}
						}
			        	var isFlushRequired = $("#isFlushRequired").val();
			        	if(clientSubmissions == 'Y' && isFlushRequired == 'Y'){
			        		for(i=rowId ; i > 0; i--){
			       	        	$("#"+(i-1)+"applicant").empty();
			       	        	$("#"+(i-1)+"applicant").append(output);
			       	        	$("#"+(i-1)+"applicant").trigger("chosen:updated");
				       	    }
			        	}
			        	$("#"+rowId+"applicant").empty();
			        	$("#"+rowId+"applicant").attr("data-placeholder", json.length + " Applicants available");
			        	$("#"+rowId+"applicant").append(output);
			        	$("#"+rowId+"applicant").trigger("chosen:updated");
			        	$("#interviewLoading").removeAttr("style");
			    	}else if (data.indexOf('Sign In') !== -1) {
			    		location.replace(sourceleadUrl);
			    	} else {
			    		showError("Operation failed, please try again");
			    	}
		        },
		    });
	
 }
</script> 

<script type="text/javascript">
 function getUpdatedShortListCandidates() {
	 var isWorkflowConfigured = '${workflowConfigured}';
	 var rowId = $("#maxTsRowNum").val();
	 var existingApplicantsIds = [];    
	 for(i=rowId ; i > 0; i--){
		 var applicant = $("#"+(i)+"applicant").val();
		 if(applicant != null && applicant != '' && applicant != 'undefined'){
			 existingApplicantsIds.push(applicant);
		 }
	 }
	 var requirementId = $("#reqId").val();
	 var clientSubmissions = '${clientSubmissions}';
	 var isClientInterview = "";
	 if(clientSubmissions == 'Y'){
		 if(document.getElementById('isClientInterview').checked) {
		        isClientInterview = 'Y';
	     }else{
	    	isClientInterview = 'N';
	     }
	 }
	 
     
		 $.ajax({
		    	type: "POST",
		        url: atsUrl+"/getAllShortListCandidates?"+tokenParamName+"="+tokenValue+"&requirementId="+requirementId+"&existingApplicantsIds="+existingApplicantsIds+
		        		"&isClientInterview="+isClientInterview+"&isWorkflowConfigured="+isWorkflowConfigured, 
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
				        		output = output + "<option value='" +opt.id+ "'>"+opt.candidateName+"</option>";
						}
			        	for(var i=rowId-1 ; i > 0; i--){
			        		 var internalOutput = "";
			        		 var applicantIdList= $("#"+(i)+"applicant").val();
			        		 var arr = jQuery.makeArray(applicantIdList);
	                         for(var j = 0; j < arr.length; j++) {
	                        	 var label = $("#"+(i)+"applicant option[value='"+arr[j]+"']").text();
	                        	 internalOutput = internalOutput+"<option value='" +arr[j]+ "' selected>"+label+"</option>";
	                         }
			        		$("#"+(i)+"applicant").empty();
			        		$("#"+(i)+"applicant").append(internalOutput);
				        	$("#"+(i)+"applicant").append(output);
				        	$("#"+(i)+"applicant").trigger("chosen:updated");
			       	    }
			    	}else if (data.indexOf('Sign In') !== -1) {
			    		location.replace(sourceleadUrl);
			    	} else {
			    		showError("Operation failed, please try again");
			    	}
		        },
		    });
 }
</script>    
  
  
<script type="text/javascript">
 function getInterviewers(rowId) {
	  $.ajax({
	    	type: "POST",
	        url: atsUrl+"/getAllInterviewers?"+tokenParamName+"="+tokenValue+"", 
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
		        	$("#"+rowId+"interviewer").empty();
		        	$("#"+rowId+"interviewer").append(output);
		        	$("#"+rowId+"interviewer").trigger("chosen:updated");
		    	}else {
		    		showError("Operation failed, please try again");
		    	}
	        },
	    });
	 }
</script> 

<script>
function validateTime(rowId){
	
	//onBlur='validateTime(\""+maxRowNum+"\");'
	var startTime = $("#"+(rowId)+"startTime").val();
	var endTime = $("#"+(rowId)+"endTime").val();
	
	var startTimeIntergerVal = startTime.substring(0, (startTime.length)-2);
	var startTimeMedium = startTime.substring((startTime.length)-2, startTime.length);
	
	var endTimeIntergerVal = endTime.substring(0, (endTime.length)-2);
	var endTimeMedium = endTime.substring((endTime.length)-2, endTime.length);
	
	 $("#"+(rowId)+"timeDiv").find('p.jquery_form_error_message').remove();
	if((endTimeIntergerVal != "" && startTimeIntergerVal != "") && (parseInt(endTimeIntergerVal) < parseInt(startTimeIntergerVal)) && startTimeMedium == endTimeMedium){
        $("#add_interview").attr("disabled", "disabled");
        $("input#" + rowId + "endTime").after("<p class='jquery_form_error_message'>End Time should greater than Start Time</span>");
    }
    else{
         $("#"+(rowId)+"timeDiv").find('p.jquery_form_error_message').remove();
         $("#add_interview").removeAttr("disabled");
   }
}

</script>
 
<script type="text/javascript">
$(function(){
	       var config = {
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			}
	       
	      
	       $('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});
		  })
});
</script>

<script>
function interviewTemplate(){
	 $.ajax({
	    	type: "POST",
	    	url : atsUrl+"/getInterviewFeedbackTemplatesList", 
	        success: function(data) {
	        	if (data.response === "FAILED") {
  		    		showError(data.responseMessage);
  		    	} else if (data.response === "AWKWARD") {
  		    		location.href = "/errorPage";
  		    	} else if (data.response === "SUCCESS") {
  		    		var json = data.data;
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
			    		$("#interviewSubmissionTemplate").trigger("chosen:updated");
			    		
			    		
			 			$('.chosen-container').css({'width': '100% !important'});
			 			
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
function getTimezonesList(rowId){
	var timezone = '${SourceLeadWeb_timeZone}';
	 $.ajax({
	    	type: "POST",
	    	url : atsUrl+"/getTimezonesList?"+tokenParamName+"="+tokenValue+"", 
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
	        	$("#"+rowId+"timeZone").empty();
	        	$("#"+rowId+"timeZone").append(output);
	        	$("#"+rowId+"timeZone").trigger("chosen:updated");
	        }
	 });
}
</script>


<script>
function myFunction(){
	$("#isFlushRequired").val('Y');
	getShortListCandidates();
}
</script>
<script>
function timezonePop(){
	$('.timePop').show();
}
function timezoneClose(){
	$('.timePop').hide();
}
</script>
<script>
function showCal(id){
	 $("#interviewer").empty();
	 $("#applicant").empty();
	$("#selectedDate").empty();
	$("#selectedTimeZoneId").empty();
	$("#selectApp").empty();
	var checkMaxTsRowNum1 = $("#maxTsRowNum").val();
	$("#selectedTimeZoneId").empty();
	var timezone = '${SourceLeadWeb_timeZone}';
	$("#selectedTimeZoneId").append(timezone);
	
	if(document.getElementById("button_id").value == id){
		$('.eventAlert').toggle();
	}
	
	if(document.getElementById("button_id").value != id){
		$('.eventAlert').show();
	} 
	document.getElementById("button_id").value = id;
	
	 var result1 ="";
		result1 = result1 + ' <div class="row">';
		result1 = result1 + '	<div class="dataTables_empty">';
		result1 = result1 + '	</div>';
		result1 = result1 + ' </div>';
		  $("#interviewer").empty();
		  $("#interviewer").append(result1);
		  $("#applicant").empty();
		  $("#applicant").append(result1);
		  $("#selectedDate").append("Please select the date to check available slots");
		  
		    var bid = document.getElementById("button_id").value;
			bid = bid.replace("Avail", "");
			var applicantSubId = '';
			var interviewerIds = '';
			
			if($("#"+bid+"applicant").val() != null && $("#"+bid+"applicant").val() != '' && $("#"+bid+"applicant").val() != 'undefined'){
				applicantSubId = $("#"+bid+"applicant").val();
			}
			
			if($("#"+bid+"interviewer").val() != null && $("#"+bid+"interviewer").val() != '' && $("#"+bid+"interviewer").val() != 'undefined'){
				interviewerIds = $("#"+bid+"interviewer").val(); 
			}
			
			var sourceleadUrl ='${sourceleadUrl}';
			var atsUrl = '${SourceLeadWeb_atsUrl}';
			 $.ajax({
				type: "POST",
		        url: atsUrl+"/getInterviewSlotDates?${_csrf.parameterName}=${_csrf.token}&applicantSubmissionIds="+applicantSubId+"&interviewerIds="+interviewerIds,
		        success: function(result) {
		        	if (result.response === "FAILED") {
			    		showError(result.responseMessage);
			    	} else if (result.response === "AWKWARD") {
			    		location.href = "/errorPage";
			    	} else if (result.response === "SUCCESS") {
			    		 var data = result.data;
			    		 var dates = {};
				       	 if(data.length > 0){
				       		 for(var i=0; i<data.length; i++){
				       			dates[new Date(data[i]).getTime()]='visible';
				       		 }
				       	 }
				       	 eventCalendar(dates);
			       		 checkAvailability();
			    	}else if (result.indexOf('Sign In') !== -1) {
			    		location.replace(sourceleadUrl);
			    	} else {
			    		showError("Operation failed, please try again");
			    	}
		        }
			}); 
}

</script>


  <script>
  function eventCalendar(dates) {
	  
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
		  $( "#scheduleCalendar" ).datepicker('destroy');
		$( "#scheduleCalendar" ).datepicker({
			  changeMonth: true,
		      changeYear: true,
		      dateFormat : finalDateFormatProjectSave,
		      minDate: 0  ,
			  onSelect : function(selected) {
				  var maxRowNum = parseInt($("input[name = 'changeMaxTsRowNum']").val());
				  if(maxRowNum >= 2){
					  checkAvailability();
				  }else if(maxRowNum < 2){
					  alertInterviewMessage("<b>Select an applicant before submission</b>");
				  }
				
		 	  },
		 	  beforeShowDay: function(date) {
		 		  if(dates && dates[date.getTime()])
			  		 var hlText = dates[date.getTime()];
				     return (hlText)?[true, "Highlighted", '']: [true, '', ''];
		 		 
			  	}
		    });

  } 

  </script> 

<!-- <script>
    $( "#scheduleCalendar" ).datepicker({
	  changeMonth: true,
      changeYear: true,
    });
  </script> -->
<script>
function checkAvailability(){
	 $("#interviewer").empty();
	 $("#applicant").empty();
	$("#selectedDate").empty();
	$("#selectedTimeZoneId").empty();
	$("#selectApp").empty();
	 $('.fullCount').hide();
		$('.particalCount').hide();
	var timezone = '${SourceLeadWeb_timeZone}';
	
	
	
	var date = $("#scheduleCalendar").val();
	
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
	
	var bid = document.getElementById("button_id").value;
	var checkMaxTsRowNum1 = $("#maxTsRowNum").val();
	bid = bid.replace("Avail", "");
	var applicantSubId = $("#"+bid+"applicant").val();
	var interviewerIds = $("#"+bid+"interviewer").val(); 
	var selectedInterviewDate = $("#"+bid+"dateOfInterview").val();
	
	$("#selectedTimeZoneId").append(timezone);
	$("#selectedDate").append(date);
	if(applicantSubId==null && interviewerIds==null ){
		$("#selectApp").append("Select Applicant/Interviewer")
	}
		  $.ajax({
		    	type: "POST",
		    	url : atsUrl+"/getInterviewerAndApplicantsDetails?"+tokenParamName+"="+tokenValue+"&applicantSubId="+applicantSubId+"&interviewerIds="+interviewerIds+"&date="+date, 
		        success: function(data) {
		        	if (data.response === "FAILED") {
			    		showError(data.responseMessage);
			    	} else if (data.response === "AWKWARD") {
			    		location.href = "/errorPage";
			    	} else if (data.response === "SUCCESS") {
		        	 	var json = data.object;
				     	$("#interviewer").empty();
				        $("#applicant").empty();
				     
				        var result = "";
				        if(json.interviewerList.length>0){
					        result = result + '            <div class="require_wrap_table scheduleTable">                                       ';
					    	result = result + '              <table id="requirementPagination11" width="100%" cellpadding="0" cellspacing="0" class="table-responsive mt10"> ';
					    	result = result + '                <thead>                                                                          ';
					    	result = result + '                  <tr>                                                                           ';
					    	result = result + '                    <th>Name</th>                                                                ';
					    	result = result + '                    <th>Time</th>                                                                ';
					    	result = result + '                    <th>Type</th>                                                                ';
					    	result = result + '                    <th>Title</th>                                                               ';
					    	result = result + '                  </tr>                                                                          ';
					    	result = result + '                </thead>                                                                         ';
					    	result = result + '                <tbody>                                                                          ';
						    	for(var i=0; i<json.interviewerList.length; i++)
								{
						    		
							        var report = json.interviewerList[i];
							    	result = result + '                  <tr>                                                                           ';
							    	result = result + '                    <td>                                                                         ';
							    	result = result + '                      <div class="tbldata-dotdot">'+report.interviewerName+'</div>                                     ';
							    	result = result + '                    </td>                                                                        ';
							    	result = result + '                    <td>                                                                         ';
							    	result = result + '                      <div class="tblminwidth-75">'+report.interviewStartTime+' - '+report.interviewEndTime+'</div>                                     ';
							    	result = result + '                    </td>                                                                        ';
							    	result = result + '                    <td>                                                                         ';
							    	result = result + '                      <div class="tbldata-dotdot-100">'+report.interviewType+'</div>                           ';
							    	result = result + '                    </td>                                                                        ';
							    	result = result + '                    <td>                                                                         ';
							    	result = result + '                      <div class="tbldata-dotdot-vsm">'+report.requirementTitle+'</div>                                ';
							    	result = result + '                    </td>                                                                        ';
							    	result = result + '                  </tr>  ';
								}
					    	
					    	result = result + '                </tbody>                                                                         ';
					    	result = result + '              </table>                                                                           ';
					    	result = result + '            </div>                                                                               ';
					    	 if(json.interviewerList.length>5 && json.interviewerList.length<=10){
					    		$('.fullCount').hide();
					    		$('.particalCount').show();
					    	 }
					    	 if(json.interviewerList.length>=10){
					    		 $('.fullCount').show();
					    		$('.particalCount').hide();
					    	 }
		        		}
				    	else{
				    		result = result + ' <div class="row">                                            ';
				    		result = result + '	<div class="dataTables_empty">                                     ';
				    		result = result + '	</div>                                                      ';
				    		result = result + ' </div>                                                      ';
				    	}
				        $("#interviewer").append(result);
				        interviewPagination(); 
					  	var opt = "";
					    	if(json.applicantsList.length>0){
					    	opt = opt + '            <div class="require_wrap_table scheduleTable">                                       ';
					    	opt = opt + '              <table id="applicantPagination" width="100%" cellpadding="0" cellspacing="0" class="table-responsive mt10"> ';
					    	opt = opt + '                <thead>                                                                          ';
					    	opt = opt + '                  <tr>                                                                           ';
					    	opt = opt + '                    <th>Name</th>                                                                ';
					    	opt = opt + '                    <th>Time</th>                                                                ';
					    	opt = opt + '                    <th>Type</th>                                                                ';
					    	opt = opt + '                    <th>Title</th>                                                               ';
					    	opt = opt + '                  </tr>                                                                          ';
					    	opt = opt + '                </thead>                                                                         ';
					    	opt = opt + '                <tbody>                                                                          ';
					    	
						    	for(var i=0; i<json.applicantsList.length; i++)
								{
							    	var report = json.applicantsList[i];
							    	opt = opt + '                  <tr>                                                                           ';
							    	opt = opt + '                    <td>                                                                         ';
							    	opt = opt + '                      <div class="tbldata-dotdot">'+report.candidateName+'</div>                                     ';
							    	opt = opt + '                    </td>                                                                        ';
							    	opt = opt + '                    <td>                                                                         ';
							    	opt = opt + '                      <div class="tblminwidth-75">'+report.interviewStartDateTimeStr+' - '+report.interviewEndtDateTimeStr+'</div>                                     ';
							    	opt = opt + '                    </td>                                                                        ';
							    	opt = opt + '                    <td>                                                                         ';
							    	opt = opt + '                      <div class="tbldata-dotdot-100">'+report.interviewType+'</div>                           ';
							    	opt = opt + '                    </td>                                                                        ';
							    	opt = opt + '                    <td>                                                                         ';
							    	opt = opt + '                      <div class="tbldata-dotdot-vsm">'+report.requirementTitle+'</div>                                ';
							    	opt = opt + '                    </td>                                                                        ';
							    	opt = opt + '                  </tr>                                                                          ';
								}
					    	
					    	opt = opt + '                </tbody>                                                                         ';
					    	opt = opt + '              </table>                                                                           ';
					    	opt = opt + '            </div>                                                                               '; 
					    	if(json.applicantsList.length>5 && json.applicantsList.length<=10){
					    		$('.fullCount').hide();
					    		$('.particalCount').show();
					    	 }
					    	 if(json.applicantsList.length>=10){
					    		 $('.fullCount').show();
					    		$('.particalCount').hide();
					    	 }
					    	}
					    	else{
					    		opt = opt + ' <div class="row">                                            ';
					    		opt = opt + '	<div class="dataTables_empty">                                     ';
					    		opt = opt + '	</div>                                                      ';
					    		opt = opt + ' </div>                                                      ';
					    	}
				        	$("#applicant").append(opt); 
				        	appPagination(); 
		        }else if (data.indexOf('Sign In') !== -1) {
		    		location.replace(sourceleadUrl);
		    	} else {
		    		showError("Operation failed, please try again");
		    	}
				        
		        }
		 }); 
	
	}
	  </script>
	  
 <script>
   $('#applicantPagination').DataTable( { 
		"autoWidth": false,
		"scrollX": true,
	});
	function appPagination(){ 
	    $('#applicantPagination').DataTable( { 
	    	"scrollCollapse": false,
	    	"lengthMenu": [[5, 15, 20, -1], [2, 15, 20, "All"]],
	    	"bLengthChange": false,
	    	"bDestroy": true,
	    	"ordering": false,
	    	"oLanguage": {
	            "sSearch": "",
	            "sZeroRecords": "", "sEmptyTable": ""
	       }
	    } );
	} 
	  
 $('#requirementPagination11').DataTable( { 
	"autoWidth": false,
	"scrollX": true,
}); 
function interviewPagination(){ 
    $('#requirementPagination11').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[5, 15, 20, -1], [2, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    } );
}
</script>
	  
<script>
function setDate(){
	var date = $("#scheduleCalendar").val();
	var bid = document.getElementById("button_id").value;
	var checkMaxTsRowNum1 = $("#maxTsRowNum").val();
	var clicked_button = bid;
	clicked_button = clicked_button.replace("Avail", "");
	$("#"+clicked_button+"dateOfInterview").datepicker('setDate',date);
}
</script>

<script>
function toggleEventCalender(){
	$('.eventAlert').hide();
}
</script>
 <script>
function interviewType(id){ 
	 if(id.value=="Other"){
		 document.getElementById(id.id+'Check').style.display="block";
		 $('#'+id.id+'other').removeAttr('data-validation-optional');
		 $('#'+id.id+'other').attr("data-validation","required validate_length length1-50"); 
	 }
	 else{
		 document.getElementById(id.id+'Check').style.display="none";
		 $('#'+id.id+'other').removeAttr("data-validation");
		 $('#'+id.id+'other').attr('data-validation-optional',"true");
	 }
}

</script>

	  