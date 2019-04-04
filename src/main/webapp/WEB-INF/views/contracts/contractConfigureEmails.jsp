<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<link rel="stylesheet" href="/resources/css/bootstrap-clockpicker.min.css">
<script src="/resources/js/bootstrap-clockpicker.min.js"></script>


<!-- <script type="text/javascript">
//reminderFun
function reminderFun() { 
	document.getElementById("reminder").style.display="block";
}
$("#ContractExpire").click(function() {
	$("#contractExpireDiv").toggle();
});
$("#WaitingTobeSigned").click(function() {
	$("#waitingTobeSignedDiv").toggle();
});
</script> -->


function reminderJobClosingFun() { 
	document.getElementById("jobClosingReminder").style.display="block";
}
$("#ContractExpire").change(function() {
	$("#contractExpireDiv").toggle();
});
$("#WaitingTobeSigned").change(function() {
	$("#waitingTobeSignedDiv").toggle();
});

<script>
function hideandshow(){
	$("#contractExpireDiv").toggle();
}

function hideandshow_WaitingTobeSigned(){
	$("#waitingTobeSignedDiv").toggle();
}

</script>

	<!-- Content Area --> 
<section class="content-wraper">
  <section class="content-header clearfix">
    <div class="pull-left">
      <h2>Contract Configuration</h2>
    </div>
    <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo;<a href="<c:url value='${appPath}/emailreminderConfigurationDashboard'/>"> Email Reminder Configuration</a> &raquo; Contract Configuration</div>
  </section>
   
  <div class="content-area content-new clearfix" style="min-height: 50px;">
    <div class="col-xs-12 col-sm-6 col-md-6 mt mb">
			<div class="row">
				<div class="col-md-3 textBold"> Contract Expire</div>
				<div class="col-md-9">
					<div class="checkbox-new">
						<input id="contractExpire" class="checkbox-custom"
							name="paidComponent" type="checkbox" ${!empty contractExpire ? 'checked' : ''} onclick="hideandshow();">
						<label for="contractExpire" class="checkbox-custom-label"></label>
					</div>
				</div>
				<div class="clearfix"></div>
				<c:set var="fIndex" value="0" scope="page" />
				<div class="clearfix mt" id="contractExpireDiv" ${!empty contractExpire ? '' : 'style="display: none;"'}>
					<form:form id="contractExpire_Configuration_form" name="contractExpire_Configuration_form">
						<div class="clearfix form-horizontal">
							<div class="row">
								<div class="clerfix"></div>
								<c:if test="${!empty contractExpire}">
										<c:forEach var="hierarchy" items="${contractExpire.emailReminderHierarchyList}" varStatus="loop">
												<div class="col-xs-12 col-sm-12 col-md-12 minHeight50 mb contractExpire_Configuration_form_rem" id="contractExpire_Configuration_form_rem${hierarchy.reminderhierarchy}">
													<label class="col-md-3 paddingRight">Reminder <span class="remIndex_contractExpire_Configuration_form">${hierarchy.reminderhierarchy}</span><span class="text-danger paddingLeft">*</span></label>
													<div class="col-md-9">
														<div class="row">
															<div class="col-md-6">
																<input type="text" class="form-control" name="emailReminderHierarchyList[${hierarchy.reminderhierarchy}].reminderdays" value="${hierarchy.reminderdays}" placeholder="Enter in days" field-name="Remainder Days" data-validation="required validate_Space validate_int validate_between between0-365">
															</div>
															<div class="clockpicker col-md-4">
																<!-- <input type="text" class="form-control timeicon" name="emailReminderHierarchyList[0].remindertime" readonly placeholder="Time" field-name="Remainder Time" data-validation="required validate_Space"> -->
																	<select class="sel"  name="emailReminderHierarchyList[${hierarchy.reminderhierarchy}].remindertime" readonly placeholder="Time" field-name="Remainder Time" data-validation="required validate_Space">
																		<option seleted value="">Select Time</option>
																		<option ${hierarchy.remindertime == '00:00AM' ? 'selected' : ''}>00:00AM</option>
																		<option ${hierarchy.remindertime == '01:00AM' ? 'selected' : ''}>01:00AM</option>
																		<option ${hierarchy.remindertime == '02:00AM' ? 'selected' : ''}>02:00AM</option>
																		<option ${hierarchy.remindertime == '03:00AM' ? 'selected' : ''}>03:00AM</option>
																		<option ${hierarchy.remindertime == '04:00AM' ? 'selected' : ''}>04:00AM</option>
																		<option ${hierarchy.remindertime == '05:00AM' ? 'selected' : ''}>05:00AM</option>
																		<option ${hierarchy.remindertime == '06:00AM' ? 'selected' : ''}>06:00AM</option>
																		<option ${hierarchy.remindertime == '07:00AM' ? 'selected' : ''}>07:00AM</option>
																		<option ${hierarchy.remindertime == '08:00AM' ? 'selected' : ''}>08:00AM</option>
																		<option ${hierarchy.remindertime == '09:00AM' ? 'selected' : ''}>09:00AM</option>
																		<option ${hierarchy.remindertime == '10:00AM' ? 'selected' : ''}>10:00AM</option>
																		<option ${hierarchy.remindertime == '11:00AM' ? 'selected' : ''}>11:00AM</option>
																		<option ${hierarchy.remindertime == '01:00PM' ? 'selected' : ''}>01:00PM</option>
																		<option ${hierarchy.remindertime == '02:00PM' ? 'selected' : ''}>02:00PM</option>
																		<option ${hierarchy.remindertime == '03:00PM' ? 'selected' : ''}>03:00PM</option>
																		<option ${hierarchy.remindertime == '04:00PM' ? 'selected' : ''}>04:00PM</option>
																		<option ${hierarchy.remindertime == '05:00PM' ? 'selected' : ''}>05:00PM</option>
																		<option ${hierarchy.remindertime == '06:00PM' ? 'selected' : ''}>06:00PM</option>
																		<option ${hierarchy.remindertime == '07:00PM' ? 'selected' : ''}>07:00PM</option>
																		<option ${hierarchy.remindertime == '08:00PM' ? 'selected' : ''}>08:00PM</option>
																		<option ${hierarchy.remindertime == '09:00PM' ? 'selected' : ''}>09:00PM</option>
																		<option ${hierarchy.remindertime == '10:00PM' ? 'selected' : ''}>10:00PM</option>
																		<option ${hierarchy.remindertime == '11:00PM' ? 'selected' : ''}>11:00PM</option>
																		<option ${hierarchy.remindertime == '12:00PM' ? 'selected' : ''}>12:00PM</option>
																	</select>
															</div>
																<%-- <input type="text" style="display: none;" class="form-control" name="emailReminderHierarchyList[${hierarchy.reminderhierarchy}].reminderhierarchy" value="${hierarchy.reminderhierarchy}"> --%>
																<input type="text" style="display: none;" class="form-control" name="emailReminderHierarchyList[${hierarchy.reminderhierarchy}].id" value="${hierarchy.id}">
															<c:if test="${loop.index == 0}">
																<div title="Add Reminder" onClick="cloneRow('contractExpire_Configuration_form');"
																	class="col-md-2">
																	<div class="mt5">
																		<i class="fa fa-plus cursorPointer text-primary btn btn-default" title="Add"></i>
																	</div>
																</div>
															</c:if>
															<c:if test="${loop.index > 0}">
																<div title="Add Reminder" class="col-md-2">
																	<div class="mt5" onclick="removeCloneDiv('contractExpire_Configuration_form_rem${hierarchy.reminderhierarchy}', 'contractExpire_Configuration_form')">
																		<i class="fa fa-times cursorPointer colorRed btn btn-default removeCloneDiv" title="Remove"></i>
																	</div>
																</div>
															</c:if>
															<c:set var="fIndex" value="${fIndex + 1}" scope="page" />
														</div>
													</div>
												</div>
										</c:forEach>
									</c:if>
									<c:if test="${empty contractExpire}">
											<div class="col-xs-12 col-sm-12 col-md-12 minHeight50 mb contractExpire_Configuration_form_rem" id="contractExpire_Configuration_form_rem0">
											<label class="col-md-3 paddingRight">Reminder <span class="remIndex_contractExpire_Configuration_form">1</span><span class="text-danger paddingLeft">*</span></label>
											<div class="col-md-9">
		
												<div class="row">
		
													<div class="col-md-6">
														<input type="text" class="form-control" name="emailReminderHierarchyList[0].reminderdays" value="" placeholder="Enter in days" field-name="Remainder Days" data-validation="required validate_Space validate_int validate_between between0-365">
													</div>
		
													<div class="clockpicker col-md-4">
														<!-- <input type="text" class="form-control timeicon" name="emailReminderHierarchyList[0].remindertime" readonly placeholder="Time" field-name="Remainder Time" data-validation="required validate_Space"> -->
															
															<select class="sel"  name="emailReminderHierarchyList[0].remindertime" readonly placeholder="Time" field-name="Remainder Time" data-validation="required validate_Space">
																<option seleted value="">Select Time</option>
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
														<!-- <input type="text" style="display: none;" class="form-control" name="emailReminderHierarchyList[0].reminderhierarchy" value="0"> -->
													<div title="Add Reminder" onClick="cloneRow('contractExpire_Configuration_form');"
														class="col-md-2">
														<div class="mt5">
															<i
																class="fa fa-plus cursorPointer text-primary btn btn-default"
																title="Add"></i>
														</div>
													</div>
													<c:set var="fIndex" value="${fIndex + 1}" scope="page" />
												</div>
											</div>
										</div>
									</c:if>
								<div id="reminderParent_contractExpire_Configuration_form" class="clearfix"></div>
	
									<div class="col-xs-12 col-sm-12 col-md-12 minHeight50 mb">
										<label class="col-md-3">Notify To<span class="text-danger paddingLeft">*</span></label>
										<div class="col-md-9" id="notifyToDiv_contractExpire_Configuration_form">
											<input type="text" class="form-control" name="notifyTo" id="notifyTo" value="${contractExpire.notifyTo}" placeholder="Enter email ids separated by commas" field-name="Notify To" data-validation="required validate_Space">
										</div>
										<input type="text" style="display: none;" class="form-control" name="scenarioName" id="scenarionName" value="contractExpiryRemainder">
										<input type="text" style="display: none;" class="form-control" name="tabsId" id="tabsId" value="16">
										<input type="text" style="display: none;" class="form-control" name="id" value="${contractExpire.id}">
									</div>
								</div>
							</div>
							<div class="clearfix"></div>
							<div class="clearfix footerstyelsNew">
								<div class="col-lg-12">
									<div class="pull-right">
										<input type="button" value="Save" id="contractExpire_Configuration_form_btn"
											class="btn btn-primary"
											onclick="storeContractReminderConfiguration('contractExpire_Configuration_form');"> 
											<input type="button" value="Cancel" class="btn btn-default ml10" onClick="javascript:window.location.href = '<c:url value='${appPath}/emailreminderConfigurationDashboard'/>'">
									</div>
								</div>
							</div>
						</form:form>
					</div>
				</div> 


			 <%-- <div class="row">
				<div class="col-md-3 textBold"> Waiting to be signed</div>
				<div class="col-md-9">
					<div class="checkbox-new">
						<input id="waitingTobeSigned" class="checkbox-custom"
							name="paidComponent" type="checkbox" ${!empty waitingTobeSigned ? 'checked' : ''} onclick="hideandshow_WaitingTobeSigned();">
						<label for="waitingTobeSigned" class="checkbox-custom-label"></label>
					</div>
				</div>
				<div class="clearfix"></div>
				<c:set var="fIndex" value="0" scope="page" />
				<div class="clearfix mt" id="waitingTobeSignedDiv" ${!empty waitingTobeSigned ? '' : 'style="display: none;"'}>
					<form:form id="waitingTobeSigned_form" name="waitingTobeSigned_form">
						<div class="clearfix form-horizontal">
							<div class="row">
								<div class="clerfix"></div>
								<c:if test="${!empty waitingTobeSigned}">
										<c:forEach var="hierarchy" items="${waitingTobeSigned.emailReminderHierarchyList}" varStatus="loop">
												<div class="col-xs-12 col-sm-12 col-md-12 minHeight50 mb waitingTobeSigned_form_rem" id="waitingTobeSigned_form_rem${hierarchy.reminderhierarchy}">
													<label class="col-md-3 paddingRight">Reminder <span class="remIndex_waitingTobeSigned_form">${hierarchy.reminderhierarchy}</span><span class="text-danger paddingLeft">*</span></label>
													<div class="col-md-9">
														<div class="row">
															<div class="col-md-6">
																<input type="text" class="form-control" name="emailReminderHierarchyList[${hierarchy.reminderhierarchy}].reminderdays" value="${hierarchy.reminderdays}" placeholder="Enter in days" field-name="Remainder Days" data-validation="required validate_Space validate_int validate_between between0-365">
															</div>
															<div class="clockpicker col-md-4">
																<!-- <input type="text" class="form-control timeicon" name="emailReminderHierarchyList[0].remindertime" readonly placeholder="Time" field-name="Remainder Time" data-validation="required validate_Space"> -->
																	<select class="sel"  name="emailReminderHierarchyList[${hierarchy.reminderhierarchy}].remindertime" readonly placeholder="Time" field-name="Remainder Time" data-validation="required validate_Space">
																		<option seleted value="">Select Time</option>
																		<option ${hierarchy.remindertime == '00:00AM' ? 'selected' : ''}>00:00AM</option>
																		<option ${hierarchy.remindertime == '01:00AM' ? 'selected' : ''}>01:00AM</option>
																		<option ${hierarchy.remindertime == '02:00AM' ? 'selected' : ''}>02:00AM</option>
																		<option ${hierarchy.remindertime == '03:00AM' ? 'selected' : ''}>03:00AM</option>
																		<option ${hierarchy.remindertime == '04:00AM' ? 'selected' : ''}>04:00AM</option>
																		<option ${hierarchy.remindertime == '05:00AM' ? 'selected' : ''}>05:00AM</option>
																		<option ${hierarchy.remindertime == '06:00AM' ? 'selected' : ''}>06:00AM</option>
																		<option ${hierarchy.remindertime == '07:00AM' ? 'selected' : ''}>07:00AM</option>
																		<option ${hierarchy.remindertime == '08:00AM' ? 'selected' : ''}>08:00AM</option>
																		<option ${hierarchy.remindertime == '09:00AM' ? 'selected' : ''}>09:00AM</option>
																		<option ${hierarchy.remindertime == '10:00AM' ? 'selected' : ''}>10:00AM</option>
																		<option ${hierarchy.remindertime == '11:00AM' ? 'selected' : ''}>11:00AM</option>
																		<option ${hierarchy.remindertime == '01:00PM' ? 'selected' : ''}>01:00PM</option>
																		<option ${hierarchy.remindertime == '02:00PM' ? 'selected' : ''}>02:00PM</option>
																		<option ${hierarchy.remindertime == '03:00PM' ? 'selected' : ''}>03:00PM</option>
																		<option ${hierarchy.remindertime == '04:00PM' ? 'selected' : ''}>04:00PM</option>
																		<option ${hierarchy.remindertime == '05:00PM' ? 'selected' : ''}>05:00PM</option>
																		<option ${hierarchy.remindertime == '06:00PM' ? 'selected' : ''}>06:00PM</option>
																		<option ${hierarchy.remindertime == '07:00PM' ? 'selected' : ''}>07:00PM</option>
																		<option ${hierarchy.remindertime == '08:00PM' ? 'selected' : ''}>08:00PM</option>
																		<option ${hierarchy.remindertime == '09:00PM' ? 'selected' : ''}>09:00PM</option>
																		<option ${hierarchy.remindertime == '10:00PM' ? 'selected' : ''}>10:00PM</option>
																		<option ${hierarchy.remindertime == '11:00PM' ? 'selected' : ''}>11:00PM</option>
																		<option ${hierarchy.remindertime == '12:00PM' ? 'selected' : ''}>12:00PM</option>
																	</select>
															</div>
																<input type="text" style="display: none;" class="form-control" name="emailReminderHierarchyList[${hierarchy.reminderhierarchy}].reminderhierarchy" value="${hierarchy.reminderhierarchy}">
																<input type="text" style="display: none;" class="form-control" name="emailReminderHierarchyList[${hierarchy.reminderhierarchy}].id" value="${hierarchy.id}">
															<c:if test="${loop.index == 0}">
																<div title="Add Reminder" onClick="cloneRow('waitingTobeSigned_form');"
																	class="col-md-2">
																	<div class="mt5">
																		<i class="fa fa-plus cursorPointer text-primary btn btn-default" title="Add"></i>
																	</div>
																</div>
															</c:if>
															<c:if test="${loop.index > 0}">
																<div title="Add Reminder" class="col-md-2">
																	<div class="mt5" onclick="removeCloneDiv('waitingTobeSigned_form_rem${hierarchy.reminderhierarchy}', 'waitingTobeSigned_form')">
																		<i class="fa fa-times cursorPointer colorRed btn btn-default removeCloneDiv" title="Remove"></i>
																	</div>
																</div>
															</c:if>
															<c:set var="fIndex" value="${fIndex + 1}" scope="page" />
														</div>
													</div>
												</div>
										</c:forEach>
									</c:if>
									<c:if test="${empty waitingTobeSigned}">
											<div class="col-xs-12 col-sm-12 col-md-12 minHeight50 mb waitingTobeSigned_form_rem" id="waitingTobeSigned_form_rem0">
											<label class="col-md-3 paddingRight">Reminder <span class="remIndex_waitingTobeSigned_form">1</span><span class="text-danger paddingLeft">*</span></label>
											<div class="col-md-9">
		
												<div class="row">
		
													<div class="col-md-6">
														<input type="text" class="form-control" name="emailReminderHierarchyList[0].reminderdays" value="" placeholder="Enter in days" field-name="Remainder Days" data-validation="required validate_Space validate_int validate_between between0-365">
													</div>
		
													<div class="clockpicker col-md-4">
														<!-- <input type="text" class="form-control timeicon" name="emailReminderHierarchyList[0].remindertime" readonly placeholder="Time" field-name="Remainder Time" data-validation="required validate_Space"> -->
															
															<select class="sel"  name="emailReminderHierarchyList[0].remindertime" readonly placeholder="Time" field-name="Remainder Time" data-validation="required validate_Space">
																<option seleted value="">Select Time</option>
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
														<!-- <input type="text" style="display: none;" class="form-control" name="emailReminderHierarchyList[0].reminderhierarchy" value="0"> -->
													<div title="Add Reminder" onClick="cloneRow('waitingTobeSigned_form');"
														class="col-md-2">
														<div class="mt5">
															<i
																class="fa fa-plus cursorPointer text-primary btn btn-default"
																title="Add"></i>
														</div>
													</div>
													<c:set var="fIndex" value="${fIndex + 1}" scope="page" />
												</div>
											</div>
										</div>
									</c:if>
								<div id="reminderParent_waitingTobeSigned_form" class="clearfix"></div>
	
									<div class="col-xs-12 col-sm-12 col-md-12 minHeight50 mb">
										<label class="col-md-3">Notify To<span class="text-danger paddingLeft">*</span></label>
										<div class="col-md-9" id="notifyToDiv_waitingTobeSigned_form">
											<input type="text" class="form-control" name="notifyTo" id="notifyTo" value="${waitingTobeSigned.notifyTo}" placeholder="Enter email ids separated by commas" field-name="Notify To" data-validation="required validate_Space">
										</div>
										<input type="text" style="display: none;" class="form-control" name="scenarioName" id="scenarionName" value="waitingContractToBeSigned">
										<input type="text" style="display: none;" class="form-control" name="tabsId" id="tabsId" value="16">
										<input type="text" style="display: none;" class="form-control" name="id" value="${waitingTobeSigned.id}">
									</div>
								</div>
							</div>
							<div class="clearfix"></div>
							<div class="clearfix footerstyelsNew">
								<div class="col-lg-12">
									<div class="pull-right">
										<input type="button" value="Save" id="waitingTobeSigned_form_btn"
											class="btn btn-primary"
											onclick="storeContractReminderConfiguration('waitingTobeSigned_form');"> 
											<input type="button" value="Cancel" class="btn btn-default ml10" onClick="javascript:window.location.href = '<c:url value='${appPath}/emailreminderConfigurationDashboard'/>'">
									</div>
								</div>
							</div>
						</form:form>
					</div>
				</div>  --%>
  </div>
  </div>



<script>
	function storeContractReminderConfiguration(form) {
		var validationSettings = {
			errorMessagePosition : 'element',
			borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd'
		};
		if ($('#'+form).validate(false, validationSettings)) {
			var emailArray = $('#notifyTo').val().split(",");
			var proceed = true;
			$('#notifyToDiv').find('p.jquery_form_error_message').remove();
			$.each(emailArray,function(i){
				var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
			    if(! re.test(String($.trim(emailArray[i])).toLowerCase())){
			    	proceed = false;
			    	return false;
			    }
			});
			if(!proceed){
				$("input#notifyTo").after("<p class='jquery_form_error_message'>Invalid entry</span>");
				return false;
			}
			
			$('#'+form+'_btn').prop('disabled', true);
			var formData = getFormData(form);
			$.ajax({
				type : "POST",
				data : formData,
				url : sourceleadUrl + "/storeContractReminderConfiguration?${_csrf.parameterName}=${_csrf.token}",
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

var m = ${fIndex};
function cloneRow(form) {
	m = m + 1;
	var h = $("."+form+"_rem").length + 1;
	var clone = "";
	clone += '<div class="col-xs-12 col-sm-12 col-md-12 mb minHeight50 '+form+'_rem" id="'+form+'_rem'+m+'">';
	clone += '<label class="col-md-3 paddingRight">Reminder <span class="remIndex_'+form+'">'+h+'</span><span class="text-danger paddingLeft">*</span></label>';
	clone += '<div class="col-md-9">';
	clone += '<div class="row">';
	clone += '<div class="col-md-6">';
	clone += '<input type="text" class="form-control" name="emailReminderHierarchyList['+m+'].reminderdays" value="" placeholder="Enter in days" field-name="Remainder Days" data-validation="required validate_Space validate_int validate_between between0-365">';
	clone += '</div>';
	clone += '<div class="clockpicker col-md-4">';
	/* clone += '<input type="text" class="form-control timeicon" name="emailReminderHierarchyList['+m+'].remindertime" value="" readonly placeholder="Time" field-name="Remainder Time" data-validation="required validate_Space">'; */
	clone += '<select class="sel"  name="emailReminderHierarchyList['+m+'].remindertime" value="" readonly placeholder="Time" field-name="Remainder Time" data-validation="required validate_Space">';
	clone += '<option seleted value="">Select Time</option>';
	clone += '<option>00:00AM</option>';
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
	//clone += '<input type="text" style="display: none;" class="form-control" name="emailReminderHierarchyList['+m+'].reminderhierarchy" value="'+m+'">';
	clone += '<div title="Remove Reminder" class="col-md-2">';
	clone += '<div class="mt5" onclick="removeCloneDiv(\''+form+'_rem'+m+'\', \''+form+'\')">';
	clone += '<i class="fa fa-times cursorPointer colorRed btn btn-default" title="Remove"></i>';
	clone += '</div>';
	clone += '</div>';
	clone += '</div>';
	clone += '</div>';
	clone += '</div>';
	$("#reminderParent_"+form).append(clone);
}

function removeCloneDiv(remDiv, form){
	$("#"+remDiv).remove();
	$('.remIndex_'+form).each(function(i, obj) {
		$(this).text(i+1);
	});
}

$("#arfi").click(function() {
	$("#arfiDiv").toggle();
});
</script>
	
<jsp:include page="../masterFooter.jsp" />	