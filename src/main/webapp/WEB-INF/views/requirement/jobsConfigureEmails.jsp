<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<link rel="stylesheet" href="/resources/css/bootstrap-clockpicker.min.css">
<script src="/resources/js/bootstrap-clockpicker.min.js"></script>


<script type="text/javascript">
//reminderFun
function reminderFun() { 
	document.getElementById("reminder").style.display="block";
}
$("#employeeonLeave").click(function() {
	$("#employeeonLeaveDiv").toggle();
});
$("#approveLeave").click(function() {
	$("#approveLeaveDiv").toggle();
});
</script>

<script>
function reminderJobClosingFun() { 
	document.getElementById("jobClosingReminder").style.display="block";
}
$("#employeeonLeave").click(function() {
	$("#employeeonLeaveDiv").toggle();
});
$("#approveLeave").click(function() {
	$("#approveLeaveDiv").toggle();
});

</script>

<script>
function hideandshow(){
	$("#employeeonLeaveDiv").toggle();
}

function hideandshowjobclosing(){
	$("#approveLeaveDiv").toggle();
}

</script>



<!-- Content Area --> 
<section class="content-wraper">
  <section class="content-header clearfix">
    <div class="pull-left">
      <h2>Jobs</h2>
    </div>
    <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo;<a href="<c:url value='${appPath}/emailreminderConfigurationDashboard'/>"> Email Reminder Configuration</a> &raquo; Jobs Configuration</div>
  </section>
   
  <div class="content-area content-new clearfix" style="min-height: 50px;">
    <div class="col-xs-12 col-sm-6 col-md-6 mt mb">
			<div class="row">
				<div class="col-md-3 textBold">Applicant Joining</div>
				<div class="col-md-9">
					<div class="checkbox-new">
						<input id="employeeonLeave" class="checkbox-custom"
							name="paidComponent" type="checkbox" onclick="hideandshow();">
						<label for="employeeonLeave" class="checkbox-custom-label"></label>
					</div>
				</div>
				<div class="clearfix"></div>

				<div class="clearfix mt" id="employeeonLeaveDiv" style="display: none;">
					<form:form id="job_ApplicantJoining_form" name="job_ApplicantJoining_form">
						<div class="clearfix form-horizontal">
							<div class="row">
								<div class="clerfix"></div>
								<div class="col-xs-12 col-sm-12 col-md-12 minHeight50 mb">
									<label class="col-md-3 paddingRight">Reminder 1<span class="text-danger paddingLeft">*</span></label>
									<div class="col-md-9">

										<div class="row">

											<div class="col-md-6">
												<input type="text" class="form-control" name="emailReminderHierarchyList[0].reminderdays" value="" placeholder="Enter in days" field-name="Remainder Days" data-validation="required validate_Space validate_int validate_between between0-365">
											</div>

											<div class="clockpicker col-md-4">
												<!-- <input type="text" class="form-control timeicon" name="emailReminderHierarchyList[0].remindertime" readonly placeholder="Time" field-name="Remainder Time" data-validation="required validate_Space"> -->
													
													<select class="sel"  name="emailReminderHierarchyList[0].remindertime" readonly placeholder="Time" field-name="Remainder Time" data-validation="required validate_Space">
														<option seleted>Select Time</option>
														<option>00:00AM</option>
														<option>01:00AM</option>
														<option>02:00AM</option>
														<option>03:00AM</option>
														<option>04:00AM</option>
														<option>05:00AM</option>
														<option>06:00AM</option>
														<option>07:00AM</option>
														<option>08:00AM</option>
														<option>09:00AM</option>
														<option>10:00AM</option>
														<option>11:00AM</option>
														<option>01:00PM</option>
														<option>02:00PM</option>
														<option>03:00PM</option>
														<option>04:00PM</option>
														<option>05:00PM</option>
														<option>06:00PM</option>
														<option>07:00PM</option>
														<option>08:00PM</option>
														<option>09:00PM</option>
														<option>10:00PM</option>
														<option>11:00PM</option>
														<option>12:00PM</option>
													</select>
													
											</div>
												<input type="text" style="display: none;" class="form-control" name="emailReminderHierarchyList[0].reminderhierarchy" value="0">
											<div title="Add Reminder" onClick="cloneRow(this);"
												class="col-md-2">
												<div class="mt5">
													<i
														class="fa fa-plus cursorPointer text-primary btn btn-default"
														title="Add"></i>
												</div>
											</div>

										</div>
									</div>
								</div>
								<div id="reminderParent" class="clearfix"></div>

								<div class="col-xs-12 col-sm-12 col-md-12 minHeight50 mb">
									<label class="col-md-3">Notify To<span class="text-danger paddingLeft">*</span></label>
									<div class="col-md-9">
										<input type="text" class="form-control" name="notifyTo" id="notifyTo" value="" placeholder="Enter email ids separated by commas" field-name="Notify To" data-validation="required validate_Space">
									</div>
									<input type="text" style="display: none;" class="form-control" name="scenarioName" id="scenarionName" value="ApplicantJoining">
									<input type="text" style="display: none;" class="form-control" name="tabsId" id="tabsId" value="8">
								</div>
							</div>
						</div>
						<div class="clearfix"></div>
						<div class="clearfix footerstyelsNew">
							<div class="col-lg-12">
								<div class="pull-right">
									<input type="button" value="Save" id="job_ApplicantJoining_form_btn"
										class="btn btn-primary"
										onclick="stroeJobReminderConfiguration('job_ApplicantJoining_form');"> 
										<input type="button" value="Cancel" class="btn btn-default ml10" onClick="javascript:window.location.href = '<c:url value='${appPath}/emailreminderConfigurationDashboard'/>'">
								</div>
							</div>
						</div>
					</form:form>
				</div>
			</div>


			<div class="clearfix mt"></div>
			<%-- <div class="row">
				<div class="col-md-3 textBold">Jobs Closing</div>
				<div class="col-md-9">
					<div class="checkbox-new">
						<input id="approveLeave" class="checkbox-custom"
							name="paidComponent" type="checkbox"
							onclick="hideandshowjobclosing();"> <label
							for="approveLeave" class="checkbox-custom-label"></label>
					</div>
				</div>
				<div class="clearfix"></div>
				<div id="approveLeaveDiv" class="mt" style="display: none;">
					<form:form id="job_ConfigueEmail_form" name="jobConfigueEmail_form">
						<div class="minHeight50 mb">
							<label class="col-md-3 paddingRight">Send<sup>1st</sup>
								Reminder
							</label>
							<div class="col-md-9">
								<div class="form-icon">
									<input type="text" class="form-control" name="firstReminder"
										id="firstReminder" value=""> <span
										title="Add Reminder" onClick="reminderJobClosingFun();">
										<i class="fa fa-plus btn btn-default formic1"></i>
									</span>
								</div>
							</div>
						</div>
						<div class="col-xs-12 col-sm-12 col-md-12 minHeight50 mb"
							id="jobClosingReminder" style="display: none;">
							<label class="col-md-3 paddingRight">Send<sup>2nd</sup>
								Reminder
							</label>
							<div class="col-md-9">
								<div class="form-icon">
									<input type="text" class="form-control" name="secondReminder"
										id="secondReminder" value=""> <span
										title="Add Reminder"> <i
										class="fa fa-times colorRed btn btn-default formic1"></i>
									</span>
								</div>
							</div>
						</div>
						<div class="minHeight50 mb">
							<label class="col-md-3 paddingRight">Time</label>
							<div class="col-md-9 clockpicker">
								<input type="text" class="form-control timeicon" name="time"
									id="time" value="">
							</div>
						</div>

						<div class="clearfix"></div>
						<div class="clearfix footerstyelsNew">
							<div class="col-lg-12">
								<div class="pull-right">
									<input type="button" value="Save" id="saveJobConfigureBtn"
										class="btn btn-primary"
										onclick="stroeJobReminderConfiguration();"> <input
										type="button" value="Cancel" class="btn btn-default ml10">
								</div>
							</div>
						</div>
					</form:form>

				</div>
			</div> --%>
		</div>
    
    
  </div>
 

 
<script>
	function stroeJobReminderConfiguration(form) {
		var validationSettings = {
			errorMessagePosition : 'element',
			borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd'
		};
		if ($('#'+form).validate(false, validationSettings)) {
			$('#job_ApplicantJoining_form_btn').prop('disabled', true);
			var formData = getFormData(form);
			$.ajax({
				type : "POST",
				data : formData,
				url : sourceleadUrl + "/storeJobConfigureEmail?${_csrf.parameterName}=${_csrf.token}",
				success : function(result) {
					if(result.object === 'MissingData'){
			  			showError("Invalid data");
			  		}else{
			  			if (result.response === "SESSION_TIMEDOUT") {
			  				location.replace(sourceleadUrl);
		                } else if (result.response === "AWKWARD") {
		                    location.href = "/errorPage";
		                } else if (result.response === "SUCCESS") {
		                	location.replace(sourceleadUrl+"/emailreminderConfigurationDashboard");
		                	
		                	
		                } else if (result.indexOf('Sign In') !== -1) {
							location.replace(sourceleadUrl);
						} else {
		                    showError("Operation failed, please try again");
		                }
			  		}
				},
			});
			return true;
		} else {
			return false;
		}
	}
</script>

<script type="text/javascript">
			var config = {
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			}
			for (var selector in config) {
			  $(selector).chosen(config[selector]);
			}
			$('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});
				})
				$(document).ready(function(){
					$("#cd").click(function(){
						$(".clientDetails").toggleClass('hidden', 500);
					});
	});
</script> 
<script type="text/javascript">
$('.clockpicker').clockpicker({
    placement: 'bottom',
    align: 'left',
    donetext: 'Done',
	//autoclose: true,
    twelvehour: true
});
var m = 0;
function cloneRow(eve) {
	var m = $(".reminderDiv").length + 1;
	var clone = "";
	clone += '<div class="col-xs-12 col-sm-12 col-md-12 mb minHeight50 reminderDiv" id="clone'+m+'">';
	clone += '<label class="col-md-3 paddingRight">Reminder '+m+'<span class="text-danger paddingLeft">*</span></label>';
	clone += '<div class="col-md-9">';
	clone += '<div class="row">';
	clone += '<div class="col-md-6">';
	clone += '<input type="text" class="form-control" name="emailReminderHierarchyList['+m+'].reminderdays" value="" placeholder="Enter in days" field-name="Remainder Days" data-validation="required validate_Space validate_int validate_between between0-365">';
	clone += '</div>';
	clone += '<div class="clockpicker col-md-4">';
	/* clone += '<input type="text" class="form-control timeicon" name="emailReminderHierarchyList['+m+'].remindertime" value="" readonly placeholder="Time" field-name="Remainder Time" data-validation="required validate_Space">'; */
	clone += '<select class="sel"  name="emailReminderHierarchyList['+m+'].remindertime" value="" readonly placeholder="Time" field-name="Remainder Time" data-validation="required validate_Space">';
	clone += '<option seleted>Select Time</option>';
	clone += '	<option>00:00AM</option>';
	clone += '<option>01:00AM</option>';
	clone += '<option>02:00AM</option>';
	clone += '<option>03:00AM</option>';
	clone += '<option>04:00AM</option>';
	clone += '<option>05:00AM</option>';
	clone += '<option>06:00AM</option>';
	clone += '<option>07:00AM</option>';
	clone += '<option>08:00AM</option>';
	clone += '<option>09:00AM</option>';
	clone += '<option>10:00AM</option>';
	clone += '<option>11:00AM</option>';
	clone += '<option>01:00PM</option>';
	clone += '<option>02:00PM</option>';
	clone += '<option>03:00PM</option>';
	clone += '<option>04:00PM</option>';
	clone += '<option>05:00PM</option>';
	clone += '<option>06:00PM</option>';
	clone += '<option>07:00PM</option>';
	clone += '<option>08:00PM</option>';
	clone += '<option>09:00PM</option>';
	clone += '<option>10:00PM</option>';
	clone += '<option>11:00PM</option>';
	clone += '<option>12:00PM</option>';
	clone += '</select>';
	clone += '</div>';
	clone += '<input type="text" style="display: none;" class="form-control" name="emailReminderHierarchyList['+m+'].reminderhierarchy" value="'+m+'">';
	clone += '<div title="Add Reminder" class="col-md-2">';
	clone += '<div class="mt5" onclick="removeCloneDiv(this)">';
	clone += '<i class="fa fa-times cursorPointer colorRed btn btn-default removeCloneDiv" title="Remove"></i>';
	clone += '</div>';
	clone += '</div>';
	clone += '</div>';
	clone += '</div>';
	clone += '</div>';
	$("#reminderParent").append(clone);
	$(".removeCloneDiv").click(function() {
		$(this).parents(".reminderDiv").hide();
	});
}
$("#arfi").click(function() {
	$("#arfiDiv").toggle();
});
</script>


<jsp:include page="../masterFooter.jsp" />