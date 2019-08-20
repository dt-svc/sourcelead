<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<jsp:include page="candidatesQuickView.jsp"></jsp:include>

<%-- 
     @author : David Sammeta.
     this script is for upDate Candidate Details .
     1.usage     :  used in candidatelist.jsp function  line 264
     2.parameter : candidateId .
--%>
<script>
	function candidateExperience(candidateId) {
		 var candidateType = '${candidateType}';
		var result = "";
		if(window.candidateStatusGlobal == '0'){
			result = result	+ '    <div class="project_tabs_inn_head addUser-org overflow_inherint mt25">  ';
		}else{
			result = result	+ '    <div class="project_tabs_inn_head addUser-org overflow_inherint mt10">  ';
		}
		result  = result + '<div class="success_pop" id="selector3" style="display: none;">';
	    result  = result + '<i class="fa fa-check-circle"></i><span id="empMsgDiv"></span>';
	    result  = result + '</div>';
		result =result +'<div id = "msgDiv2"></div>';
		if(window.candidateStatusGlobal == '0'){
		'<sec:authorize access="hasAnyRole(\'ROLE_ADD_EMPLOYEER\',\'ROLE_CROWDSOURCING_CANDIDATES\',\'ROLE_ADD_ASSOCIATE_EMPLOYER\')">'; 	
		result = result	+ '<a href="javascript:void(0)" class="addUser-org" ></a>';
		'</sec:authorize>';
		}else{
			if(candidateType == "Candidates"){
				'<sec:authorize access="hasAnyRole(\'ROLE_ADD_EMPLOYEER\',\'ROLE_CROWDSOURCING_CANDIDATES\')">';
				result = result	+ '<span class="pad-sea"><a href="javascript:void(0)"  onClick="addExperience('+ candidateId+ ');" class="addUser-org" ><i class="glyphicon glyphicon-plus"></i>Add Employer</a> </span> ';
				'</sec:authorize>';
			}else{
				'<sec:authorize access="hasRole(\'ROLE_ADD_ASSOCIATE_EMPLOYER\')">';
				result = result	+ '<span class="pad-sea"><a href="javascript:void(0)"  onClick="addExperience('+ candidateId+ ');" class="addUser-org" ><i class="glyphicon glyphicon-plus"></i>Add Employer</a> </span> ';
				'</sec:authorize>';
			}
			
		}
		result = result	+ '     <div id="popUpDivEmp" class="edit-details col-md-4"> ';
		result = result + '     </div>  ';
		result = result	+ '   <div class="project_tabs_inn_table vendor_wrap_inn_table">';
		result = result + '   <div id="candidateEmployersList" class="datatable_filter"></div>';
		result = result + '   </div>';

		$("#tab-1").empty();
		$("#tab-2").empty();
		$("#tab-3").empty();
		$("#tab-4").empty();
		$("#tab-5").empty();
		$("#tab-6").empty();
		$("#tab-7").empty();
		$("#tab-8").empty();
		$("#tab-9").empty();
		$("#tab-3").append(result);

		candidateEmployersList(candidateId);

	}
</script>

<!-- Show success message after form submission script -->
<script>
	function empMsgFadein() {
		$("#selector3").fadeIn(200);
        $('#selector3').delay(2000).fadeOut('slow');
	}
</script> 
<!-- End -->

<script>
function closeCertificatePopup(){
	 $("#popUpDivEmp").removeClass("edit-details-active");
	 $(".location-alert").fadeOut();
}
</script>

<%-- 
     @author : David Sammeta.
     this script is to Store  candidateEmployer.
     1.usage     :  used in addExperience function  line 187
     2.parameter : candidateId,csrf.token
     3.ajax url implementation in CandidateEmployersRestController.
--%>
<script type="text/javascript">	
	function candidateEmployersStore(candidateId) {
		var validationSettings = {
			errorMessagePosition : 'element',
			borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd'
		};
		if ($('#employers_form').validate(false, validationSettings)) {
			
			var formData = getFormData("employers_form");
			var startYear = document.getElementById("startYear").value;
			var endYear = document.getElementById("endYear").value;
			
			var startMonth= document.getElementById("startMonth").value;
			var endMonth= document.getElementById("endMonth").value;
			
			var SMdata = startMonth.split('&');
			var sMonthNumber = SMdata[0];
			
			var EMdata = endMonth.split('&');
			var eMonthNumber = EMdata[0];
			
			var currentYear = new Date().getFullYear();
			if(( parseInt(sMonthNumber) > parseInt(eMonthNumber)) && (startYear == endYear)) {
				 $("#startYear").parent().after("<p class='jquery_form_error_message' id='err'><span>Start Month Should be less than end Month</span></p>");
			} else if((startYear > endYear ) && (endYear != '' && endYear != null && endYear != 'undefined')) {
				document.getElementById("endYearValidation").style.display="block";
				$("#endYearValidation").empty().append("End year should be greater than Start year") ; 
			} else if(startYearFlag && endYearFlag) {
				document.getElementById("endYearValidation").style.display="none";
				$("#candidateEmployersAdd").attr("disabled", "disabled");
				$("#candidateEmployersAdd").addClass('button').val('Processing..'); 
	        	$.post(atsUrl+"/storeCandidateEmployers?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId, formData, function(result) {
		        	if (result && result.response === "INVALID_DATA") {
					    $("#update_candidate").removeAttr("disabled");
					    $("#update_candidate").removeAttr('button').val('Save');
					    showError(result.responseMessage);
					} else if (result.response === "AWKWARD") {
					    location.href = "/errorPage";
					} else if (result.response === "SUCCESS") {
						$('#perVal').attr('aria-valuenow', result.object).css('width',result.object+'%');
	       			 	if(result.object<50){
	       			   		$("#perVal").removeClass('progress-bar-success');
	       			   		$("#perVal").addClass("progress-bar-danger");
	       			 	}
	       			 	if(result.object>=50){
	       				 	$("#perVal").removeClass("progress-bar-danger");
	       				 	$("#perVal").addClass("progress-bar-success");
	       			 	}
	       			 	$("#disVal").empty();
	       				$("#disVal").append(result.object+'%');
	       			
	       				// for quick view bar update
	       				$('#quickPerVal').attr('aria-valuenow', result.object).css('width',result.object+'%');
	         			 	if(result.object<50){
		          			   	$("#quickPerVal").removeClass('progress-bar-success');
		          			   	$("#quickPerVal").addClass("progress-bar-danger");
		          			}
		          			if(result.object>=50){
		          				$("#quickPerVal").removeClass('progress-bar-danger');
		          				$("#quickPerVal").addClass("progress-bar-success");
		          			}
	
	       				$("#quickDisVal").empty();
	       				$("#quickDisVal").append(result.object+'%');
	       			
						$("#popUpDivEmp").removeClass("edit-details-active");
	       				$("#selector3").removeAttr("style");
	       				$("#selector3").css({ display: "block" });
	       				$("#empMsgDiv").html("Success!! New Employer added");
						candidateEmployersList(candidateId);
						qvemployeeSubmission(candidateId);
						empMsgFadein();
						setTimeout(function(){
				 			candidateList();					 			
				 		},100);
					} else if (result.indexOf('Sign In') !== -1) {
						location.replace(sourceleadUrl);
					} else {
					    showError("Operation failed, please try again");
					}
		        });
			}
			return true;
		} else {
			return false;
		}
	}
	$('body').on('blur', '#employers_form', function() {
		$("#employers_form").showHelpOnFocus();
		$("#employers_form").validateOnBlur(false, validationSettings);
	});
</script>



<%-- 
     @author : David Sammeta.
     this script is for upDate Candidate Details .
     1.usage     :  used in candidateExperience  function  line 17
     2.parameter : candidateId .
--%>

<script>
	var startYearFlag = true ,endYearFlag = true;
	function addExperience(candidateId) {

		var result = "";
		result = result + '   <!-- Popup Div --> ';
		result = result	+ '	         <form id="employers_form" name="employers_form" method="post">';
		result = result+'	           <i class="fa fa-close close-div" onClick="closeCertificatePopup();"></i>';
		result = result	+ '	             <div class="form-horizontal clearfix">';
		result = result + '	               <div class="clearfix mb10">';
		result = result + '	                 <div class="col-md-12">';
		result = result	+ '	                  <input type="text" class="form-control input-sm"  placeholder="Previous Employers" name="name" data-validation="required validate_Space validate_length length1-100" field-name="Employer Name" value=""> <span class="hidden">Employer Name</span>';
		result = result + '	                 </div>';
		result = result + '	               </div>';
		result = result + '	               <div class="clearfix mb10">';
		result = result + '	                 <div class="clearfix checkbox">';
		result = result + '	                   <label>';
		result = result	+ '	                  <label> <input type="checkbox" id="isCurrentPosition" value="Y" onClick ="iscurrentPosition();"name="isCurrentPosition"> Current Position</label>';
		result = result + '	                 </div>';
		result = result + '	               </div>';
		result = result + '	               <div class="clearfix mb10">';
		result = result + '	                 <div class="col-md-12">';
		result = result	+ '	                    <input type="text" class="form-control input-sm"  placeholder="Designation" name="designation" data-validation="required validate_Space validate_length length1-100" data-validation-optional="true" field-name="Employer Designation" value=""> <span class="hidden">Employer Designation</span>';
		result = result + '	                 </div>';
		result = result + '	               </div>';
		result = result + '	               <div class="clearfix mb10">';
		result = result + '	                 <div class="col-md-12">';
		result = result + '	                  <input type="text" class="form-control input-sm" placeholder="Employer Address" name="address" data-validation="required validate_Space validate_length length2-250"  data-validation-optional="true" field-name="Address" value=""> <span 	class="hidden">Address</span>';
		result = result + '	                 </div>';
		result = result + '	               </div>';
		result = result + '	               <div class="clearfix mb10">';
		result = result + '	                 <p class="ml">Start Date</p>';
		result = result + '	                 <div class="col-md-6">';
		result = result	+ '	                   <select class="newSelect"  name="startMonth" id="startMonth" onChange="monthIfYearRequired();">';
		result = result	+ '	                       <option value="">Select Month</option>';
		result = result	+ ' 					   <option value="1&January">January</option>';
		result = result	+ '	                       <option value="2&February">February</option>';
		result = result	+ '	                       <option value="3&March">March</option>';
		result = result	+ '	                       <option value="4&April">April</option>';
		result = result	+ '	                       <option value="5&May">May</option>';
		result = result + '	                       <option value="6&June">June</option> ';
		result = result + '	                       <option value="7&July">July</option>';
		result = result	+ '	                       <option value="8&August">August</option>';
		result = result	+ '	                       <option value="9&September">September</option>';
		result = result	+ '	                       <option value="10&October">October</option>';
		result = result	+ '	                       <option value="11&November">November</option>';
		result = result	+ '	                       <option value="12&December">December</option>';
		result = result + '	                   </select>';
		result = result + '	                   </div>';
		result = result + '	                   <div class="col-md-6 padding_left" id="startYearDiv">';
		result = result	+ '	                   <input type="text" max class="form-control input-sm" value="" id="startYear" name="startYear"  placeholder="Enter Year"  field-name="Start Year" data-validation="validate_Space validate_extno validate_length length4-4" data-validation-optional="true"  >';
		result = result + '	                <div><p id="startYearValidation" class="text-danger small" style="display: none"></p>';
		result = result + '	                 </div>';
		result = result + '	                 </div>';
		
		result = result + '	                 <div id="endDateDiv">';
		
		result = result + '	                 <div class="clearfix mb10 endDate">';
		result = result + '	                 <p class="ml">End Date</p>';
		
		result = result	+ '	                 <div class="col-md-6 id = "endMR">';
		result = result	+ '	                    <select class="newSelect" class="required" field-name="End Month" name="endMonth" id="endMonth" onChange="monthIfYearRequired();">';
		result = result	+ '	                       <option value="">Select Month</option>';
		result = result	+ '	                       <option value="1&January">January</option>';
		result = result	+ '	                       <option value="2&February">February</option>';
		result = result	+ '	                       <option value="3&March">March</option>';
		result = result	+ '	                       <option value="4&April">April</option>';
		result = result	+ '	                       <option value="5&May">May</option>';
		result = result + '	                       <option value="6&June">June</option> ';
		result = result + '	                       <option value="7&July">July</option>';
		result = result	+ '	                       <option value="8&August">August</option>';
		result = result	+ '	                       <option value="9&September">September</option>';
		result = result	+ '	                       <option value="10&October">October</option>';
		result = result	+ '	                       <option value="11&November">November</option>';
		result = result	+ '	                       <option value="12&December">December</option>';
		result = result + '	                   </select>';
	
		result = result + '	                   </div>';
		
		result = result + '	                   <div class="col-md-6 padding_left" >';
		result = result	+ '	                   <input type="text" max class="form-control input-sm" id="endYear" name="endYear" field-name="End Year"  placeholder="Enter Year" data-validation="validate_Space validate_extno validate_length length4-4" data-validation-optional="true" >';
		result = result + '	                <div><p id="endYearValidation" class="text-danger small" style="display: none"></p>';
		
		result = result + '	                 </div>';
		
		result = result + '	                 </div> ';
		result = result + '	                 </div> ';
		result = result + '	                 </div> ';
		
		
		result = result + '	               <div class="clearfix mr">';
		result = result	+ '	                 <button type="button" id ="candidateEmployersAdd" onClick ="candidateEmployersStore('+ candidateId+ ');"class="btn btn-sm btn-primary pull-right">Add</button>';
		result = result + '	               </div>';
		result = result + '	             </div>';
		result = result + '	        </form> ';
		$("#popUpDivEmp").empty();
		$("#popUpDivEmp").append(result);

		$(".edit-details").toggleClass("edit-details-active");
		$(".close-div").click(function() {
			$(this).parent("div.edit-details").removeClass("edit-details-active");
			$(".location-alert").fadeOut();
		});
		$(".addUser-org, .assign-org, .collapse-div-adduser, .collapse-div, .edit-details").click(function(e) {
			e.stopPropagation();
		});
		/* $('html').click(function() {
			$(".edit-details").removeClass("edit-details-active");
		}); */
		applyKeyEventsToYears();	
	}
</script>

<script>

	function editEmployer(candidateEmpId, candidateId) {
		$.ajax({
			type : "POST",
			url : atsUrl
					+ "/getCandidateEmployerByCandidateIdAndCandidateEmpoyerId?"+tokenParamName+"="+tokenValue+"&candidateId="
					+ candidateId + "&candidateEmpId=" + candidateEmpId,
			success : function(response) {
				if (response.response === "AWKWARD") {
				    location.href = "/errorPage";
				} else if (response.response === "SUCCESS") {
					var json = response.object;
					var result = "";
					result = result + '      <!-- Popup Div --> ';
					result = result	+ '         <form id="candidateExperianceEdit_form" name="candidateExperianceEdit_form" method="post">';
					result = result+'	           <i class="fa fa-close close-div" onClick="closeCertificatePopup();"></i>';
					result = result	+ '	             <div class="form-horizontal clearfix">';
					result = result + '	               <div class="clearfix mb10">';
					result = result + '	                 <div class="col-md-12">';
					result = result	+ '	                  <input type="text" class="form-control input-sm"  placeholder="Employer *" name="name" data-validation="required validate_Space validate_length length2-100" field-name="Employer Name" value="'+json.name+'"> <span class="hidden">Employer Name</span>';
					result = result + '	                 </div>';
					result = result + '	               </div>';
					result = result	+ '	               <div class="clearfix mb10">';
					result = result	+ '	                 <div class="col-md-12 checkbox">';
					result = result + '	                   <label>';
					if (json.isCurrentPosition == 'Y' && json.isCurrentPosition != '' && json.isCurrentPosition !== 'undefined') {
						result = result	+ '	 <label> <input type="checkbox" id="isCurrentPositionEdit"  value="'+ json.isCurrentPosition+ '"   checked="checked"    onClick ="iscurrentPositionEdit();" name = "isCurrentPosition"> Current Position</label>';
					} else {
						result = result	+ '	     <label> <input type="checkbox" id="isCurrentPositionEdit"  value="Y" onClick ="iscurrentPositionEdit();" name= "isCurrentPosition"> Is Current Position</label>';
					}
					result = result + '	                 </div>';
					result = result + '	               </div>';

					result = result	+ '	               <div class="clearfix mb10">';
					result = result	+ '	                 <div class="col-md-12">';
					if (json.designation != null && json.designation != '' && json.designation != 'undefined') {
						result = result + '	                    <input type="text" class="form-control input-sm"  placeholder="Employer Designation" name="designation" value="'+json.designation+'" data-validation="required validate_Space validate_length length2-100" data-validation-optional="true" field-name="Employer Designation" > <span class="hidden">Employer Designation</span>';
					}else{
						result = result + '	                    <input type="text" class="form-control input-sm"  placeholder="" name="designation"  value="" data-validation="required validate_Space validate_length length2-100" data-validation-optional="true" field-name="Employer Designation" > <span class="hidden">Employer Designation</span>';
					}
					result = result + '	                 </div>';
					result = result + '	               </div>';
					result = result	+ '	               <div class="clearfix mb10">';
					result = result	+ '	                 <div class="col-md-12">';
					if (json.address != null && json.address != '' && json.address != 'undefined') {
						result = result	+ '	                  <input type="text" class="form-control input-sm" placeholder="Address" name="address" data-validation="required validate_Space validate_length length2-250"  data-validation-optional="true" field-name="Address" value="'+json.address+'"> <span class="hidden">Address</span>';
					}else{
						result = result	+ '	                  <input type="text" class="form-control input-sm" placeholder="Address" name="address" data-validation="required validate_Space validate_length length2-250"  data-validation-optional="true" field-name="Address" value=""> <span class="hidden">Address</span>';
					}
					result = result + '	                 </div>';
					result = result + '	               </div>';

					result = result	+ '	               <div class="clearfix mb10">';
					result = result + '	                 <p class="ml">Start Date</p>';
					result = result	+ '	                 <div class="col-md-6">';
					result = result	+ '	                   <select class="newSelect"  name="startMonth" id="startMonth" onchange="monthIfYearRequired();">';
					result = result	+ '<option value="">Select Month</option>';
					if (json.startMonth == "January") {
						result = result + ' <option '+ (json.startMonth == "January" ? "selected" : '') +' value="January">January</option>';
					} else {
						result = result + ' <option '+ (json.startMonth == "1&January" ? "selected" : '') +' value="1&January">January</option>';
					}

					if (json.startMonth == "February") {
						result = result + ' <option '+ (json.startMonth == "February" ? "selected" : '') +' value="February">February</option>';
					} else {
						result = result + ' <option '+ (json.startMonth == "2&February" ? "selected" : '') +' value="2&February">February</option>';
					}

					if (json.startMonth == "March") {
						result = result + ' <option '+ (json.startMonth == "March" ? "selected" : '') +' value="March">March</option>';
					} else {
						result = result + ' <option '+ (json.startMonth == "3&March" ? "selected" : '') +' value="3&March">March</option>';
					}
					if (json.startMonth == "April") {
						result = result + ' <option '+ (json.startMonth == "April" ? "selected" : '') +' value="April">April</option>';
					} else {
						result = result + ' <option '+ (json.startMonth == "4&April" ? "selected" : '') +' value="4&April">April</option>';
					}

					if (json.startMonth == "May") {
						result = result + ' <option '+ (json.startMonth == "May" ? "selected" : '') +' value="May">May</option>';
					} else {
						
						result = result + ' <option '+ (json.startMonth == "5&May" ? "selected" : '') +' value="5&May">May</option>';
					}

					if (json.startMonth == "June") {
						result = result + ' <option '+ (json.startMonth == "June" ? "selected" : '') +' value="June">June</option>';
					} else {
						result = result + ' <option '+ (json.startMonth == "6&June" ? "selected" : '') +' value="6&June">June</option>';
					}

					if (json.startMonth == "July") {
						result = result + ' <option '+ (json.startMonth == "July" ? "selected" : '') +' value="July">July</option>';
					} else {
						result = result + ' <option '+ (json.startMonth == "7&July" ? "selected" : '') +' value="7&July">July</option>';
					}

					if (json.startMonth == "August") {
						result = result + '<option selected>August</option>';
					} else {
						result = result + ' <option '+ (json.startMonth == "8&August" ? "selected" : '') +' value="8&August">August</option>';
					}
					if (json.startMonth == "September") {
						result = result	+ '<option selected>September</option>';
					} else {
						result = result + ' <option '+ (json.startMonth == "9&September" ? "selected" : '') +' value="9&September">September</option>';
					}
					if (json.startMonth == "October") {
						result = result + '<option selected>October</option>';
					} else {
						result = result + ' <option '+ (json.startMonth == "10&October" ? "selected" : '') +' value="10&October">October</option>';
					}
					if (json.startMonth == "November") {
						result = result + '<option selected>November</option>';
					} else {
						result = result + ' <option '+ (json.startMonth == "11&November" ? "selected" : '') +' value="11&November">November</option>';
					}
					if (json.startMonth == "December") {
						result = result	+ '<option selected>December</option>';
					} else {
						result = result + ' <option '+ (json.startMonth == "12&December" ? "selected" : '') +' value="12&December">December</option>';
					}
					result = result	+'</select>';
					result = result + '</div>';
					result = result	+ '<div class="col-md-6">';
					if (json.startYear != null && json.startYear != '' && json.startYear != 'undefined') {
						result = result	+ '<input type="text" max class="form-control input-sm" value="'+json.startYear+'" id="startYear" name="startYear"   placeholder="Enter Year"  field-name="Start Date" data-validation="required validate_Space validate_extno validate_length length4-4" data-validation-optional="true" >';
					}else{
						result = result	+ '<input type="text" max class="form-control input-sm" value="" id="startYear" name="startYear"   placeholder="Enter Year"  field-name="Start Date" data-validation="required" data-validation-optional="true" >';
					}
					result = result + '	                <div><p id="startYearValidation" class="text-danger small" style="display: none"></p>';
					result = result + '</div>';
					result = result + '</div>';

					result = result	+ '<div id="endDateEditDiv">';
					result = result	+ '<div class="clearfix mb10 endDate">';
					result = result	+ '<p class="ml">End Date</p>';
					result = result	+ '<div class="col-md-6 id = "endMR">';
					result = result	+ '<select class="newSelect" selected  class="required" field-name="End Month" name="endMonth" id="endMonth" onchange="monthIfYearRequired();">';
					result = result	+ '<option value="">Select Month</option>';


					if (json.endMonth == "January") {
						result = result + ' <option '+ (json.endMonth == "January" ? "selected" : '') +' value="January">January</option>';
					} else {
						result = result + ' <option '+ (json.endMonth == "1&January" ? "selected" : '') +' value="1&January">January</option>';
					}

					if (json.endMonth == "February") {
						result = result + ' <option '+ (json.endMonth == "February" ? "selected" : '') +' value="February">February</option>';
					} else {
						result = result + ' <option '+ (json.endMonth == "2&February" ? "selected" : '') +' value="2&February">February</option>';
					}

					if (json.endMonth == "March") {
						result = result + ' <option '+ (json.endMonth == "March" ? "selected" : '') +' value="March">March</option>';
					} else {
						result = result + ' <option '+ (json.endMonth == "3&March" ? "selected" : '') +' value="3&March">March</option>';
					}
					if (json.endMonth == "April") {
						result = result + ' <option '+ (json.endMonth == "April" ? "selected" : '') +' value="April">April</option>';
					} else {
						result = result + ' <option '+ (json.endMonth == "4&April" ? "selected" : '') +' value="4&April">April</option>';
					}

					if (json.endMonth == "May") {
						result = result + ' <option '+ (json.endMonth == "May" ? "selected" : '') +' value="May">May</option>';
					} else {
						
						result = result + ' <option '+ (json.endMonth == "5&May" ? "selected" : '') +' value="5&May">May</option>';
					}

					if (json.endMonth == "June") {
						result = result + ' <option '+ (json.endMonth == "June" ? "selected" : '') +' value="June">June</option>';
					} else {
						result = result + ' <option '+ (json.endMonth == "6&June" ? "selected" : '') +' value="6&June">June</option>';
					}

					if (json.endMonth == "July") {
						result = result + ' <option '+ (json.endMonth == "July" ? "selected" : '') +' value="July">July</option>';
					} else {
						result = result + ' <option '+ (json.endMonth == "7&July" ? "selected" : '') +' value="7&July">July</option>';
					}

					if (json.endMonth == "August") {
						result = result + '<option selected>August</option>';
					} else {
						result = result + ' <option '+ (json.endMonth == "8&August" ? "selected" : '') +' value="8&August">August</option>';
					}
					if (json.endMonth == "September") {
						result = result	+ '<option selected>September</option>';
					} else {
						result = result + ' <option '+ (json.endMonth == "9&September" ? "selected" : '') +' value="9&September">September</option>';
					}
					if (json.endMonth == "October") {
						result = result + '<option selected>October</option>';
					} else {
						result = result + ' <option '+ (json.endMonth == "10&October" ? "selected" : '') +' value="10&October">October</option>';
					}
					if (json.endMonth == "November") {
						result = result + '<option selected>November</option>';
					} else {
						result = result + ' <option '+ (json.endMonth == "11&November" ? "selected" : '') +' value="11&November">November</option>';
					}
					if (json.endMonth == "December") {
						result = result	+ '<option selected>December</option>';
					} else {
						result = result + ' <option '+ (json.endMonth == "12&December" ? "selected" : '') +' value="12&December">December</option>';
					}
					result = result	+ '	                   </select>';
					result = result	+ '	                   </div>';
					result = result	+ '<div class="col-md-6">';

					if (json.endYear != null && json.endYear != '' && json.endYear != 'undefined') {
						result = result	+ '<input type="text" max class="form-control input-sm" value="'+json.endYear+'" id="endYear" name="endYear"   placeholder="Enter Year"  field-name="end Year" data-validation="required validate_Space validate_extno validate_length length4-4" data-validation-optional="true" >';
					}else{
						result = result	+ '<input type="text" max class="form-control input-sm" value="" id="endYear" name="endYear"   placeholder="Enter Year"  field-name="end Year" data-validation="required" data-validation-optional="true" >';
					}
					result = result + '	                <div><p id="endYearValidation" class="small text-danger" style="display: none"></p>';
					result = result + '</div>';
					result = result + '</div>';
					result = result + '	                 </div> ';
					result = result + '	                 </div> ';
					result = result	+ '	               <div class="clearfix mr">';
					result = result	+ '	                   <input type="hidden"  value="'+candidateEmpId+'" id="candidateEmpId"  name="candidateEmployeeId" >';
					result = result	+ '	                   <input type="hidden"  value="'+candidateId+'" id="candidateId" >';
					result = result + '	                 <button type="button" id ="candidateEmployersAdd" onClick ="updateCandidateEmployers('+ candidateId+ ','+ candidateEmpId+ ');"class="btn btn-sm btn-primary pull-right">Update</button>';
					result = result + '	               </div>';
					result = result + '	             </div>';
					result = result + '	        </form> ';
					$("#popUpDivEmp").empty();
					$("#popUpDivEmp").append(result);
					iscurrentPositionEdit();

					$(".edit-details").toggleClass("edit-details-active");
					$(".close-div").click(function() {
						$(this).parent("div.edit-details").removeClass("edit-details-active");
					});
					$(".addUser-org, .assign-org, .collapse-div-adduser, .collapse-div, .edit-details").click(function(e) {
						e.stopPropagation();
					});
					applyKeyEventsToYears();
					/* $('html').click(function() {
								$(".edit-details").removeClass(
										"edit-details-active");
							}); */	
				} else if (response.indexOf('Sign In') !== -1) {
					location.replace(sourceleadUrl);
				} else {
				    showError("Operation failed, please try again");
				}          
			},
		});
	}
</script>

<%-- 
     @author : David Sammeta.
     this script is for serializeFormJSON.
     1.usage     :  used in candidateEmployersStore function  line 57
--%>
<script type="text/javascript">
function applyKeyEventsToYears() {
	$("#startYear").keyup(function() {
		if($(this).val() > new Date().getFullYear()) {
			$("#startYearValidation").html("Start year shouldn't be greater than Current year");
			$("#startYearValidation").show();
			startYearFlag = false;
		} else {
			$("#startYearValidation").hide();
			startYearFlag = true;
		}
	});
	$("#endYear").keyup(function() {
		if($(this).val() > new Date().getFullYear()) {
			$("#endYearValidation").html("End year shouldn't be greater than Current year");
			$("#endYearValidation").show();
			endYearFlag = false;
		} else {
			$("#endYearValidation").hide();
			endYearFlag = true;
		} 
	});
}
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

<%-- 
     @author : David Sammeta.
     this script is getting candidateEmployers List .
     1.usage     :  used in candidateExperience  function  line 34
     2.parameter : candidateId ,csrf.token.
     3.ajax url implementation in CandidateListRestController.
--%>
<script>
	function candidateEmployersList(candidateId) {
		 var candidateType = '${candidateType}';
		 var locationList ='${SourceLeadWeb_dateFormat}';	
		  if(locationList.match("yyyy-MM-dd")){
			  candExpListDate = 'YYYY-MM-DD';
		  }if(locationList.match("dd-MM-yyyy")){
			  candExpListDate = 'DD-MM-YYYY'; 
		  }if(locationList.match("MM-dd-yyyy")){
			  candExpListDate = 'MM-DD-YYYY'; 
		  }if(locationList.match("yyyy-dd-MM")){
			  candExpListDate = 'YYYY-DD-MM'; 
		  }
				$.ajax({
					type : "POST",
					url : atsUrl+ "/getCandidatesEmployersListByCandidateIdAndOrgLocationID?"+tokenParamName+"="+tokenValue+"&candidateId="
							+ candidateId,
					success : function(json) {
						if (json && json.response === "INVALID_DATA") {
						    $("#update_candidate").removeAttr("disabled");
						    $("#update_candidate").removeAttr('button').val('Save');
						    showError(json.responseMessage);
						} else if (json.response === "AWKWARD") {
						    location.href = "/errorPage";
						} else if (json.response === "SUCCESS") {
							$("#candidateEmployersList").empty();
							var result = "";
							result = result	+ "<table id='candidateEmployersPagination' class='display' cellspacing='0' width='100%' >";
							result = result + "<thead>";
							result = result + "  <tr>";
							result = result	+ '           <th>Employer</th>';
							result = result	+ '           <th>Designation</th>';
							result = result + '           <th>Address</th>';
							result = result	+ '           <th>Start date</th>';
							result = result	+ '           <th>End date</th>';
							result = result + '           <th>Action</th>';
							result = result + "  </tr>";
							result = result + "</thead>";
							result = result + "<tbody>";
							for (var i = 0; i < json.data.length; i++) {
								var opt = json.data[i];
								result = result + '         <tr>';
								if (opt.name != null && opt.name != ''
										&& opt.name != 'undefined') {
									result = result + '<td><div><span title = "'+ opt.name+ '">'+ opt.name+ '</span></div></td>';
								} else {
									result = result+ '<td><div><span>--</span></div></td>';
								}
								if (opt.designation != null	&& opt.designation && opt.designation != 'undefined') {
									result = result + '<td><div><span title="'+ opt.designation+ '">'+ opt.designation+ '</span></div></td>';
								} else {
									result = result	+ '<td><div><span>----</span></div></td>';
								}
								if (opt.address != null && opt.address	&& opt.address != 'undefined') {
									result = result + '<td><div><span title="'+ opt.address+ '">'+ opt.address+ '</span></div></td>';
								} else {
									result = result	+ '<td><div><span>----</span></div></td>';
								}

								if (opt.startYear != null && opt.startYear != '' && opt.startYear != 'undefined') {
									result = result + '<td><div><span>'+opt.startMonth+','+opt.startYear+'</span></div></td>';
								} else {
									result = result		+ '<td><div><span>----</span></div></td>';
								}
                                
								if (opt.endYear != null	&& opt.endYear != '' && opt.endYear != 'undefined') {
									result = result + ' <td><div><span>'+opt.endMonth+','+opt.endYear+'</span></div></td>';
								} else {
									result = result	+ '<td><div><span>----</span></div></td>';
								}
								if(window.candidateStatusGlobal == '0'){
			                   result = result+'<td><div class="tblicons"><a class="glyphicon glyphicon-pencil" href="javascript:void(0)"></a>';
								}else{
									if(candidateType == "Candidates"){
										result = result+'<td><div class="tblicons"><sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_EMPLOYEER\',\'ROLE_CROWDSOURCING_CANDIDATES\')"><a class="glyphicon glyphicon-pencil" href="javascript:void(0)" onClick="editEmployer('+ opt.id+ ','+ candidateId+ ');" title="Edit"></a></sec:authorize>';	
									}else{
										result = result+'<td><div class="tblicons"><sec:authorize access="hasRole(\'ROLE_VIEW_EDIT_ASSOCIATE_EMPLOYER\')"><a class="glyphicon glyphicon-pencil" href="javascript:void(0)" onClick="editEmployer('+ opt.id+ ','+ candidateId+ ');" title="Edit"></a></sec:authorize>';
									}
										
								}if(window.candidateStatusGlobal == '0'){
									result = result+'<i class="glyphicon glyphicon-trash"></i></div></td>';
								}else{
									if(candidateType == "Candidates"){
										result = result+'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_DELETE_EMPLOYEER\',\'ROLE_CROWDSOURCING_CANDIDATES\')"><i class="glyphicon glyphicon-trash" onclick="removeEmployer('+ opt.id + ',' + candidateId+ ');" title="Remove"></i></sec:authorize></div></td>';	
									}else{
										result = result+'<sec:authorize access="hasRole(\'ROLE_VIEW_DELETE_ASSOCIATE_EMPLOYER\')"><i class="glyphicon glyphicon-trash" onclick="removeEmployer('+ opt.id + ',' + candidateId+ ');" title="Remove"></i></sec:authorize></div></td>';
									}
									
								}
								result = result + '</tr>';
							}
							result = result + " </tbody>";
							result = result + "</table>";
							$("#candidateEmployersList").append(result);
							employeePagination();
							msgFadein2();
						} else if (json.indexOf('Sign In') !== -1) {
							location.replace(sourceleadUrl);
						} 
					}
				});
	}
</script>


<script>
function employeePagination(){  
    $('#candidateEmployersPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
 }
</script>

 

<script type="text/javascript">
function deletePopup(){
	$("#remove_user_pop_emp, .project_inn_mask").fadeOut();
}
</script>

<div id="deleteExcepPopUp"></div> 

<script type="text/javascript">
function removeEmployer(candidateemployerId, candidateId){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop_emp' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>You are choosing to delete the Employer</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='deleteSelectedEmployer("+candidateemployerId+","+candidateId+");'><input class='u_can_color' type='button'  value='Cancel' onClick='deletePopup();'></b>";
	result = result +"	</div>";
	$("#deleteExcepPopUp").empty();
	$("#deleteExcepPopUp").append(result);
	$("#remove_user_pop_emp").slideDown(200);
	$(".project_inn_mask").fadeIn(200);

}
</script>

<%-- 
     @author : David Sammeta.
     this script is getting removing Employer in the  List .
     1.usage     :  used in candidateEmployersList  function  line 286
     2.parameter : candidateId ,csrf.token,candidateemployerId
     3.ajax url implementation in CandidateListRestController.
--%>
<script>
	function deleteSelectedEmployer(candidateemployerId, candidateId) {
		var loginUserId = '${SourceLeadWeb_userId}';
		var orgLocationId = '${SourceLeadWeb_orgLocationId}';
		$.ajax({
			type : "POST",
			url : atsUrl+"/removeCandidateEmployerBycandidateIdandCandidateEmployerID?"+tokenParamName+"="+tokenValue+"&loginUserId="+loginUserId+"&orgLocationId="+orgLocationId+"&candidateId="+ candidateId + "&candidateemployerId="+candidateemployerId,
			success : function(response) {
				if (response.response === "AWKWARD") {
				    location.href = "/errorPage";
				} else if (response.response === "SUCCESS") {
					var data = response.object;
					$('#perVal').attr('aria-valuenow', data).css('width',data+'%');
					if(data<50){
					/* $("#perVal").removeClass("progress-bar-success"); */
					$("#perVal").removeClass('progress-bar-success');
					$("#perVal").addClass("progress-bar-danger");
					}
					if(data>=50){
					 $("#perVal").removeClass('progress-bar-danger');
					 $("#perVal").addClass("progress-bar-success");
					}
					$("#disVal").empty();
					$("#disVal").append(data+'%');


					// for quick view bar update
					$('#quickPerVal').attr('aria-valuenow', data).css('width',data+'%');
					 if(data<50){
						   $("#quickPerVal").removeClass('progress-bar-success');
						   $("#quickPerVal").addClass("progress-bar-danger");
						 }
						 if(data>=50){
							 $("#quickPerVal").removeClass('progress-bar-danger');
							 $("#quickPerVal").addClass("progress-bar-success");
						 }

					$("#quickDisVal").empty();
					$("#quickDisVal").append(data+'%');
					$("#selector3").removeAttr("style");
					 $("#selector3").css({ display: "block" });
					 $("#empMsgDiv").html("Employer deleted");
					$("#popUpDivEmp").removeClass("edit-details-active");
					$("#remove_user_pop_emp, .project_inn_mask").fadeOut();
					candidateEmployersList(candidateId);
					qvemployeeSubmission(candidateId);
					empMsgFadein();
					setTimeout(function(){
							candidateList();					 			
					 },100);
				} else if (response.indexOf('Sign In') !== -1) {
					location.replace(sourceleadUrl);
				} else {
				    showError("Operation failed, please try again");
				}
			},

		});
	}
</script>

<%-- 
     @author : David Sammeta.
     this script is for upDate Candidate Details .
     1.usage     :  used in editCandidate() function  line 78
     2.parameter : csrf token .
     3.ajax url implementation in CandidateListRestController.
--%>
<script type="text/javascript">
	function updateCandidateEmployers(candidateId, candidateExperianceId) {

		var validationSettings = {
			errorMessagePosition : 'element',
			borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd'
		};
		if ($('#candidateExperianceEdit_form').validate(false,validationSettings)) {
			var formData = getFormData("candidateExperianceEdit_form");
			var startYear = document.getElementById("startYear").value;
			var endYear = document.getElementById("endYear").value;
			var startMonth= document.getElementById("startMonth").value;
			var endMonth= document.getElementById("endMonth").value;
			
			var SMdata = startMonth.split('&');
			var sMonthNumber = SMdata[0];
			
			var EMdata = endMonth.split('&');
			var eMonthNumber = EMdata[0];
			
			
			
			 if((parseInt(sMonthNumber) > parseInt(eMonthNumber)) && (startYear==endYear) ){
				 $("#startYear").parent().after("<p class='jquery_form_error_message' id='err'><span>Start Month Should be less than end Month</span></p>");
			} else if((startYear > endYear ) && (endYear != '' && endYear != null && endYear != 'undefined')) {
				document.getElementById("endYearValidation").style.display="block";
				$("#endYearValidation").empty().append("End year should be greater than Start year") ; 
			} else if(startYearFlag && endYearFlag) {
				
				document.getElementById("endYearValidation").style.display="none";
				$.post(atsUrl+"/upDateCandidateEmployerByCandidateIdAndCandidateExperianceId?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId+"&candidateExperianceId="+candidateExperianceId, formData, function(result) {
					if (result && result.response === "INVALID_DATA") {
					    $("#update_candidate").removeAttr("disabled");
					    $("#update_candidate").removeAttr('button').val('Save');
					    showError(result.responseMessage);
					} else if (result.response === "AWKWARD") {
					    location.href = "/errorPage";
					} else if (result.response === "SUCCESS") {
						$(".edit-details").toggleClass("edit-details-active");
						$(".close-div")	.click(function() {
							$(this).parent("div.edit-details").removeClass("edit-details-active");
						});
						$(".addUser-org, .assign-org, .collapse-div-adduser, .collapse-div, .edit-details").click(function(e) {
							e.stopPropagation();
						});
						$("#selector3").removeAttr("style");
		    			$("#selector3").css({ display: "block" });
		    			$("#empMsgDiv").html("Employer details updated");
						candidateEmployersList(candidateId);
						empMsgFadein();
						qvemployeeSubmission(candidateId);
						setTimeout(function(){
				 			candidateList();					 			
				 		},100);
					} else if (result.indexOf('Sign In') !== -1) {
						location.replace(sourceleadUrl);
					} else {
					    showError("Operation failed, please try again");
					}
	        	});
			}
			return true;
		} else {
			return false;
		}
		$('body').on('blur','#candidateExperianceEdit_form', function() {
			$("#candidateExperianceEdit_form").showHelpOnFocus();
			$("#candidateExperianceEdit_form").validateOnBlur(false, validationSettings);
		});
	}
</script>


<%-- 
     @author : David Sammeta.
     this script is for onclick function iscurrentPosition  addExperience  .
     1.usage     :  used in addExperience  function  line 124
--%>
<script>
	function iscurrentPosition() {
		var currentposition = document.getElementById("isCurrentPosition").checked;
		if (currentposition) {
			document.getElementById("endDateDiv").style.display = 'none';
			$('#endYear').removeAttr('data-validation');
			$('#endMonth').removeAttr('data-validation');
			$("#endMonth").val("");
			$('#endYear').val("");
			$('#err').parent().find('p.jquery_form_error_message').remove();
			document.getElementById("endYearValidation").style.display="none";
			endYearFlag = true;
		} else if (document.getElementById("endDateDiv").style.display == 'none') {
			document.getElementById("endDateDiv").style.display = 'block';
			/* $("#endYear").attr("data-validation", "required");
			$("#endMonth").attr("data-validation", "required"); */
		}
	}
</script>

<%-- 
     @author : David Sammeta.
     this script is for onclick function   iscurrentPositionEdit  .
     1.usage     :  used in editEmployer  function  line 248
--%>
<script>
	function iscurrentPositionEdit() {
		var currentposition = document.getElementById("isCurrentPositionEdit").checked;
		if (currentposition) {
			$("#isCurrentPositionEdit").val( 'Y' );
			document.getElementById("endDateEditDiv").style.display = 'none';
			$('#endYear').removeAttr('data-validation');
			$('#endMonth').removeAttr('data-validation');
			$("#endMonth").val("");
			$('#endYear').val("");
			document.getElementById("endYearValidation").style.display="none";
			endYearFlag = true;
		}else {
			document.getElementById("endDateEditDiv").style.display = '';
		/* 	$("#endYearEdit").attr("data-validation", "required");
			$("#endMonthEdit").attr("data-validation", "required"); */
			
			 var exisitngEndYear;
			 if ($('#exisitngEndYear').length) {
				 exisitngEndYear = document.getElementById("exisitngEndYear").value; 
			 } else {
				 exisitngEndYear = '';
				 $("#endYearEdit" ).val( '' );
			 }
		}
	}
</script>
<%-- 
     @author : PrasadK.
     this script is for on change function   if we select Month Year is Required  .
     1.usage     :  used in addEmployer  function  line 248
--%>
<script type="text/javascript">
function monthIfYearRequired(){
	
	var startMonth= document.getElementById("startMonth").value;
	if(startMonth != null && startMonth != "" && startMonth != "undefined"){
		 $('#startYear').removeAttr('data-validation-optional');
	     $('#startYear').attr("data-validation","required validate_Space validate_extno validate_length length4-4"); 
	}else{
		 $('#startYear').removeAttr('data-validation');
		 $('#startYear').attr("data-validation-optional","true"); 
	}
	
	var endMonth= document.getElementById("endMonth").value;
	
	if(endMonth != null && endMonth != "" && endMonth != "undefined"){
			 $('#endYear').removeAttr('data-validation-optional');
		     $('#endYear').attr("data-validation","required validate_Space validate_extno validate_length length4-4"); 
		}else{
			 $('#endYear').removeAttr('data-validation');
			 $('#endYear').attr("data-validation-optional","true"); 
		}
	
	var startYear= document.getElementById("startYear").value;
	var endYear= document.getElementById("endYear").value;
	var SMdata = startMonth.split('&');
	var sMonthNumber = SMdata[0];
	
	var EMdata = endMonth.split('&');
	var eMonthNumber = EMdata[0];
	
	if((startYear != '' && startYear != null && startYear != 'undefined' ) && (endYear != '' && endYear != null && endYear != 'undefined')){
	 if((parseInt(sMonthNumber) > parseInt(eMonthNumber)) && (startYear==endYear) ){
		
		 $("#err").empty();
		 $("#startYear").parent().after("<p class='jquery_form_error_message' id='err'><span>Start Month Should be less than end Month</span></p>");
		}
	 if(parseInt(sMonthNumber) < parseInt(eMonthNumber))
		 {
		 
		 $('#startYearDiv').parent().find('p.jquery_form_error_message').remove(); 
		 }

	}
	
	
}
</script>

<script>
function validYear(){
	var startYear= document.getElementById("startYear").value;
	var startMonth= document.getElementById("startMonth").value;
	
	var endYear= document.getElementById("endYear").value;
	var endMonth= document.getElementById("endMonth").value;
	
	var SMdata = startMonth.split('&');
	var sMonthNumber = SMdata[0];
	
	var EMdata = endMonth.split('&');
	var eMonthNumber = EMdata[0];
	 if( (startMonth < endMonth) && (sMonthNumber == eMonthNumber) ){
		 $("#startYear").parent().after("<p class='jquery_form_error_message'><span>Start Month Should be less then end Month</span></p>");
	}else if((startYear > endYear )&&(endYear != '' && endYear != null && endYear != 'undefined')){
		$("input#startYear").parent().after("<p class='jquery_form_error_message'><span>Start Month Should be less then end Month</span></p>");
	}else{
		$('#startYearDiv').parent().find('p.jquery_form_error_message').remove(); 
		
	}
	
}

</script>


<script>
function validYearEdit(){
	var startYear= document.getElementById("startYear").value;
	var startMonth= document.getElementById("startMonth").value;
	
	var endYear= document.getElementById("endYear").value;
	var endMonth= document.getElementById("endMonth").value;
	
	var SMdata = startMonth.split('&');
	var sMonthNumber = SMdata[0];
	var EMdata = endMonth.split('&');
	var eMonthNumber = EMdata[0];
	
	 if((startYear == endYear) && (sMonthNumber < eMonthNumber)){

		 $("input#startYear").parent().after("<p class='jquery_form_error_message'><span>Start Month Should be less then end Month</span></p>");
		
	}else if(startYear > endYear ){
		$("input#startYear").parent().after("<p class='jquery_form_error_message'><span>Start Date Should be less then end Date</span></p>");
	}else{
		$('#startYearDiv').parent().find('p.jquery_form_error_message').remove(); 
	}
}

</script>