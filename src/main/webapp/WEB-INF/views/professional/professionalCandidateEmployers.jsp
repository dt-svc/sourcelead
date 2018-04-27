<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%-- 
     @author : Laxman.
     this script is for upDate Candidate Details .
     1.usage     :  used in candidatelist.jsp function  line 264
     2.parameter : candidateId .
--%>
<script>
	function professionalCandidateEmployers(candidateId) {

		var result = "";
		result = result	+ '    <div class="project_tabs_inn_head addUser-org">  ';
		result  = result + '<div class="success_pop" id="selector3" style="display: none;">';
	    result  = result + '<i class="fa fa-check-circle"></i><span id="empMsgDiv"></span>';
	    result  = result + '</div>';
		result =result +'<div id = "msgDiv2"></div>';
		if(window.candidateStatusGlobal == '0'){
		result = result	+ '<a href="javascript:void(0)" class="addUser-org" ><i class="glyphicon glyphicon-plus"></i>Add Employer</a>';
		}else{
			result = result	+ '<span class="pad-sea"><a href="javascript:void(0)"  onClick="addExperience('+ candidateId+ ');" class="addUser-org" ><i class="glyphicon glyphicon-plus"></i>Add Employer</a> </span> ';
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
}
</script>

<%-- 
     @author : Laxman.
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
			
			 if( (startMonth > endMonth) && (startYear == endYear) ){
					 
				 $("#startYear").parent().after("<p class='jquery_form_error_message' id='err'><span>Start Month Should be less than end Month</span></p>");
		
			}
			 
			 else if((startYear > endYear )&&(endYear != '' && endYear != null && endYear != 'undefined')){
				document.getElementById("endYearValidation").style.display="block";
				$("#endYearValidation").empty().append("End year should be greater than Start year") ; 
				
			}
			else{
			
			$("#candidateEmployersAdd").attr("disabled", "disabled");
			$("#candidateEmployersAdd").addClass('button').val('Processing..'); 
	        $.post(sourceleadUrl+"/storeCandidateEmployers?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId, formData, function(response) {
	        	if (response.response === "AWKWARD") {
                    location.href = "/errorPage";
                } else if (response.response === "SUCCESS") {
                    var data = response.object;
                    $('#perVal').attr('aria-valuenow', data).css('width',data+'%');
                    if(data<50){
                      $("#perVal").removeClass('progress-bar-success');
                      $("#perVal").addClass("progress-bar-danger");
                    }
                    if(data>=50){
                        $("#perVal").removeClass("progress-bar-danger");
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
                   
                    $("#popUpDivEmp").removeClass("edit-details-active");
                   $("#selector3").removeAttr("style");
                   $("#selector3").css({ display: "block" });
                   $("#empMsgDiv").html("Success!! New Employer added");
                    candidateEmployersList(candidateId);
                    empMsgFadein();
                    qvemployeeSubmission(candidateId);
                } else if (response.indexOf('Sign In') !== -1) {
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
     @author : Laxman.
     this script is for upDate Candidate Details .
     1.usage     :  used in professionalCandidateEmployers  function  line 17
     2.parameter : candidateId .
--%>

<script>
	function addExperience(candidateId) {

		var result = "";
		result = result + '   <!-- Popup Div --> ';
		result = result	+ '	         <form id="employers_form" name="employers_form" method="post">';
		result = result+'	           <i class="fa fa-close close-div" onClick="closeCertificatePopup();"></i>';
		result = result	+ '	             <div class="form-horizontal clearfix">';
		result = result + '	               <div class="clearfix">';
		result = result + '	                 <div class="col-md-12">';
		result = result	+ '	                  <input type="text" class="form-control"  placeholder="Employer *" name="name" data-validation="required validate_AlphaNumeric_SpecialChar validate_length length2-100" field-name="Employer Name" value=""> <span class="hidden">Employer Name</span>';
		result = result + '	                 </div>';
		result = result + '	               </div>';
		result = result + '	               <div class="clearfix mb10">';
		result = result + '	                 <div class="ml checkbox">';
		result = result	+ '	                  <label> <input type="checkbox" id="isCurrentPosition" value="Y" onClick ="iscurrentPosition();"name="isCurrentPosition"> Is Current Position</label>';
		result = result + '	                 </div>';
		result = result + '	               </div>';
		result = result + '	               <div class="clearfix mb10">';
		result = result + '	                 <div class="col-md-12">';
		result = result	+ '	                    <input type="text" class="form-control"  placeholder="Employer Designation" name="designation" data-validation="required validate_Space validate_length length2-100" data-validation-optional="true" field-name="Employer Designation" value=""> <span class="hidden">Employer Designation</span>';
		result = result + '	                 </div>';
		result = result + '	               </div>';
		result = result + '	               <div class="clearfix mb10">';
		result = result + '	                 <div class="col-md-12">';
		result = result + '	                  <input type="text" class="form-control input-sm" placeholder="Address" name="address" data-validation="required validate_Space validate_length length2-250"  data-validation-optional="true" field-name="Address" value=""> <span 	class="hidden">Address</span>';
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

		/* $( "#startYear, #endYear" ).datepicker({ 
		            changeMonth: true,
		           changeYear: true,
		           dateFormat: 'MM yy',            
		           onClose: function(dateText, inst) { 
		               var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
		               var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();             
		               $(this).datepicker('setDate', new Date(year, month, 1));
		           },
		           beforeShow : function(input, inst) {
		               if ((datestr = $(this).val()).length > 0) {
		                   year = datestr.substring(datestr.length-4, datestr.length);
		                   month = jQuery.inArray(datestr.substring(0, datestr.length-5), $(this).datepicker('option', 'monthNames'));
		                   //$(this).datepicker('option', 'defaultDate', new Date(year, month, 1));
		                   $(this).datepicker('setDate', new Date(year, month, 1));    
		               }
		               var other = this.id == "startYear" ? "#endYear" : "#startYear";
		               var option = this.id == "startYear" ? "maxDate" : "minDate";        
		               if ((selectedDate = $(other).val()).length > 0) {
		                   year = selectedDate.substring(selectedDate.length-4, selectedDate.length);
		                   month = jQuery.inArray(selectedDate.substring(0, selectedDate.length-5), $(this).datepicker('option', 'monthNames'));
		                   $(this).datepicker( "option", option, new Date(year, month, 1));
		               }
		           }
		       });
		 */
		
		
		$(".edit-details").toggleClass("edit-details-active");
		$(".close-div").click(
				function() {
					$(this).parent("div.edit-details").removeClass(
							"edit-details-active");
				});
		$(
				".addUser-org, .assign-org, .collapse-div-adduser, .collapse-div, .edit-details")
				.click(function(e) {
					e.stopPropagation();
				});
	}
</script>

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
	
	
	if((startYear != '' && startYear != null && startYear != 'undefined' )&&(endYear != '' && endYear != null && endYear != 'undefined')){
	
	 if((sMonthNumber> eMonthNumber) && (startYear==endYear) ){
		$("#err").empty();
		 $("#startYear").parent().after("<p class='jquery_form_error_message' id='err'><span>Start Month Should be less than end Month</span></p>");
			 
		}
	 if(sMonthNumber< eMonthNumber)
		 {
		 $('#startYearDiv').parent().find('p.jquery_form_error_message').remove(); 
		 }

	}
	
	
}
</script>


<script>
	function editEmployer(candidateEmpId, candidateId) {
		$.ajax({
			type : "POST",
			url : sourceleadUrl
					+ "/getCandidateEmployerByCandidateIdAndCandidateEmpoyerId?${_csrf.parameterName}=${_csrf.token}&candidateId="
					+ candidateId + "&candidateEmpId=" + candidateEmpId,
			success : function(response) {
				if (response.response === "AWKWARD") {
				    location.href = "/errorPage";
				} else if (response.response === "SUCCESS") {
					/* var json = JSON.parse(data); */
					var json = response.object;
					var result = "";
					result = result + '      <!-- Popup Div --> ';
					result = result	+ '         <form id="candidateExperianceEdit_form" name="candidateExperianceEdit_form" method="post">';
					result = result+'	           <i class="fa fa-close close-div" onClick="closeCertificatePopup();"></i>';
					result = result	+ '	             <div class="form-horizontal clearfix">';
					result = result + '	               <div class="clearfix mb10">';
					result = result + '	                 <div class="col-md-12">';
					result = result	+ '	                  <input type="text" class="form-control"  placeholder="Employer *" name="name" data-validation="required validate_AlphaNumeric_SpecialChar validate_length length2-100" field-name="Employer Name" value="'+json.name+'"> <span class="hidden">Employer Name</span>';
					result = result + '	                 </div>';
					result = result + '	               </div>';
					result = result	+ '	               <div class="clearfix mb10">';
					result = result	+ '	                 <div class="col-md-12 checkbox">';
					result = result + '	                   <label>';
					if (json.isCurrentPosition == 'Y' && json.isCurrentPosition != '' && json.isCurrentPosition !== 'undefined') {
						result = result	+ '	 <label> <input type="checkbox" id="isCurrentPositionEdit"  value="'+ json.isCurrentPosition+ '"   checked="checked"    onClick ="iscurrentPositionEdit();" name = "isCurrentPosition"> Is Current Position</label>';
					} else {
						result = result	+ '	     <label> <input type="checkbox" id="isCurrentPositionEdit"  value="Y" onClick ="iscurrentPositionEdit();" name= "isCurrentPosition"> Is Current Position</label>';
					}
					result = result + '	                 </div>';
					result = result + '	               </div>';
					result = result	+ '	               <div class="clearfix mb10">';
					result = result	+ '	                 <div class="col-md-12">';
					if (json.designation != null && json.designation != '' && json.designation != 'undefined') {
						result = result + '	                    <input type="text" class="form-control"  placeholder="Employer Designation" name="designation" value="'+json.designation+'" data-validation="required validate_Space validate_length length2-100" data-validation-optional="true" field-name="Employer Designation" > <span class="hidden">Employer Designation</span>';
					}else{
						result = result + '	                    <input type="text" class="form-control"  placeholder="Employer Designation" name="designation"  value="" data-validation="required validate_Space validate_length length2-100" data-validation-optional="true" field-name="Employer Designation" > <span class="hidden">Employer Designation</span>';
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
					result = result	+ '	                   <select class="newSelect" name="startMonth" id="startMonth" onchange="monthIfYearRequired();">';
					result = result	+ '	                       <option value="">Select Month</option>';
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
					
		/* $( "#startYear, #endYearEdit" ).datepicker({ 
        changeMonth: true,
       changeYear: true,
       dateFormat: 'MM yy',            
       onClose: function(dateText, inst) { 
           var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
           var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();             
           $(this).datepicker('setDate', new Date(year, month, 1));
       },
       beforeShow : function(input, inst) {
           if ((datestr = $(this).val()).length > 0) {
               year = datestr.substring(datestr.length-4, datestr.length);
               month = jQuery.inArray(datestr.substring(0, datestr.length-5), $(this).datepicker('option', 'monthNames'));
               $(this).datepicker('option', 'defaultDate', new Date(year, month, 1));
               $(this).datepicker('setDate', new Date(year, month, 1));    
           }
           var other = this.id == "startYear" ? "#endYearEdit" : "#startYear";
           var option = this.id == "startYear" ? "maxDate" : "minDate";        
           if ((selectedDate = $(other).val()).length > 0) {
               year = selectedDate.substring(selectedDate.length-4, selectedDate.length);
               month = jQuery.inArray(selectedDate.substring(0, selectedDate.length-5), $(this).datepicker('option', 'monthNames'));
               $(this).datepicker( "option", option, new Date(year, month, 1));
           }
       }
   }); */

		$(".edit-details").toggleClass("edit-details-active");
		$(".close-div").click(
				function() {
					$(this).parent("div.edit-details").removeClass(
							"edit-details-active");
				});
		$(".addUser-org, .assign-org, .collapse-div-adduser, .collapse-div, .edit-details")
				.click(function(e) {
					e.stopPropagation();
				});
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
     @author : Laxman.
     this script is for serializeFormJSON.
     1.usage     :  used in candidateEmployersStore function  line 57
--%>
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

<%-- 
     @author : Laxman.
     this script is getting candidateEmployers List .
     1.usage     :  used in professionalCandidateEmployers  function  line 34
     2.parameter : candidateId ,csrf.token.
     3.ajax url implementation in CandidateListRestController.
--%>
<script>
	function candidateEmployersList(candidateId) {
		
		  var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
		  if(locationList.match("yyyy-MM-dd")){
			  candidateEmployersListDate = 'YYYY-MM-DD';
		  }if(locationList.match("dd-MM-yyyy")){
			  candidateEmployersListDate = 'DD-MM-YYYY'; 
		  }if(locationList.match("MM-dd-yyyy")){
			  candidateEmployersListDate = 'MM-DD-YYYY'; 
		  }if(locationList.match("yyyy-dd-MM")){
			  candidateEmployersListDate = 'YYYY-DD-MM'; 
		  }
		
				$.ajax({
					type : "POST",
					url : sourceleadUrl	+ "/getCandidatesEmployersListByCandidateIdAndOrgLocationID?${_csrf.parameterName}=${_csrf.token}&candidateId="
							+ candidateId,
					success : function(response) {
						if (response && response.response === "INVALID_DATA") {
						    showError(response.responseMessage);
						} else if (response.response === "AWKWARD") {
						    location.href = "/errorPage";
						} else if (response.response === "SUCCESS") {
							$("#candidateEmployersList").empty();
							var result = "";
							result = result	+ "<table id='candidateEmployersPagination' class='display' cellspacing='0' width='100%' >";
							result = result + "<thead>";
							result = result + "  <tr>";
							result = result	+ '           <th>Employer</th>';
							result = result	+ '           <th>Designation</th>';
							result = result + '           <th>Address</th>';
							result = result	+ '           <th>Start Date</th>';
							result = result	+ '           <th>End Date</th>';
							result = result + '           <th>Action</th>';
							result = result + "  </tr>";
							result = result + "</thead>";
							result = result + "<tbody>";
							for (var i = 0; i < response.data.length; i++) {
								var opt = response.data[i];
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
									result = result+'<td><div class="tblicons"><a class="glyphicon glyphicon-pencil" href="javascript:void(0)" onClick="editEmployer('+ opt.id+ ','+ candidateId+ ');" title="Edit"></a>';	
								}if(window.candidateStatusGlobal == '0'){
									result = result+'<i class="glyphicon glyphicon-trash"></i></div></td>';
								}else{
									result = result+'<i class="glyphicon glyphicon-trash" onclick="removeEmployer('+ opt.id + ',' + candidateId+ ');" title="Remove"></i></div></td>';
								}
								result = result + '</tr>';
							}
							result = result + " </tbody>";
							result = result + "</table>";
							$("#candidateEmployersList").append(result);
							employeePagination();
							msgFadein2();
						} else if (response.indexOf('Sign In') !== -1) {
							location.replace(sourceleadUrl);
						} else {
						    showError("Operation failed, please try again");
						}
						    
					},
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
    	"aoColumnDefs": [{
	           'bSortable': false,
	           'aTargets': 5
			}],
    	"oLanguage": {
     		"sSearch": "",
     		"sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
 
 }
</script>



<%-- 
     @author : Laman.
     this script is getting removing Employer in the  List .
     1.usage     :  used in candidateEmployersList  function  line 286
     2.parameter : candidateId ,csrf.token,candidateemployerId
     3.ajax url implementation in CandidateListRestController.
--%>


<script type="text/javascript">
function cancelEmployeers(){
	$("#remove_user_emp_pop, .project_inn_mask").fadeOut();
}
</script>

<div id="deleteEmp1PopUp"></div> 

<script type="text/javascript">
function removeEmployer(candidateemployerId, candidateId){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_emp_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>You are choosing to delete the Employer</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='deleteSelectedEmp("+candidateemployerId+","+candidateId+");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelEmployeers();'></b>";
	result = result +"	</div>";
	$("#deleteEmp1PopUp").empty();
	$("#deleteEmp1PopUp").append(result);
	$("#remove_user_emp_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);

}
</script>


<script>
	function deleteSelectedEmp(candidateemployerId, candidateId) {
		var loginUserId = '${SourceLeadWeb_userId}';
		var orgLocationId = '${SourceLeadWeb_orgLocationId}';
		$.ajax({
			type : "POST",
			url : sourceleadUrl	+ "/removeCandidateEmployerBycandidateIdandCandidateEmployerID?${_csrf.parameterName}=${_csrf.token}&loginUserId="+loginUserId+"&orgLocationId="+orgLocationId+"&candidateId="+ candidateId + "&candidateemployerId="+candidateemployerId,
			success : function(response) {
				if (response.response === "AWKWARD") {
				    location.href = "/errorPage";
				} else if (response.response === "SUCCESS") {
					var data = response.object;
					$('#perVal').attr('aria-valuenow', data).css('width',data+'%');
					if(data < 50) {
					/* $("#perVal").removeClass("progress-bar-success"); */
					$("#perVal").removeClass('progress-bar-success');
					$("#perVal").addClass("progress-bar-danger");
					}
					if(data >= 50) {
					 $("#perVal").removeClass('progress-bar-danger');
					 $("#perVal").addClass("progress-bar-success");
					}
					$("#disVal").empty();
					$("#disVal").append(data+'%');

					$("#selector3").removeAttr("style");
					$("#selector3").css({ display: "block" });
					$("#empMsgDiv").html("Employer deleted");
					$("#popUpDivEmp").removeClass("edit-details-active");
					$("#remove_user_emp_pop, .project_inn_mask").fadeOut();
					candidateEmployersList(candidateId);
					empMsgFadein();
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
     @author : Laman.
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
			
			 if( (startMonth > endMonth) && (startYear == endYear) ){
					 
				 $("#startYear").parent().after("<p class='jquery_form_error_message' id='err'><span>Start Month Should be less than end Month</span></p>");
		
			}
			
		     else if((startYear > endYear )&&(endYear != '' && endYear != null && endYear != 'undefined')){
				document.getElementById("endYearValidation").style.display="block";
				$("#endYearValidation").empty().append("End year should be greater than Start year") ; 
				
			}
			else {
	        $.post(sourceleadUrl+"/upDateCandidateEmployerByCandidateIdAndCandidateExperianceId?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId+"&candidateExperianceId="+candidateExperianceId, formData, function(result) {
	        	if (result && result.response === "INVALID_DATA") {
				    $("#update_candidate").removeAttr("disabled");
				    $("#update_candidate").removeAttr('button').val('Save');
				    showError(result.responseMessage);
				} else if (result.response === "AWKWARD") {
				    location.href = "/errorPage";
				} else if (result.response === "SUCCESS") {
					$(".edit-details").toggleClass("edit-details-active");
					$(".close-div")	.click(function() {
										$(this).parent("div.edit-details")
												.removeClass("edit-details-active");
									});
					$(".addUser-org, .assign-org, .collapse-div-adduser, .collapse-div, .edit-details")
							.click(function(e) {
								e.stopPropagation();
							});
					$("#selector3").removeAttr("style");
	    			$("#selector3").css({ display: "block" });
	    			$("#empMsgDiv").html("Employer details updated");
					candidateEmployersList(candidateId);
					empMsgFadein();
					qvemployeeSubmission(candidateId);
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
		$('body').on('blur','#candidateExperianceEdit_form',
				function() {
					$("#candidateExperianceEdit_form").showHelpOnFocus();
					$("#candidateExperianceEdit_form").validateOnBlur(false,
							validationSettings);
				});
	}
</script>


<%-- 
     @author : Laman.
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
		} else if (document.getElementById("endDateDiv").style.display == 'none') {
			
			document.getElementById("endDateDiv").style.display = 'block';
			$("#endYear").attr("data-validation", "required");
			$("#endMonth").attr("data-validation", "required");
		}

	}
</script>

<%-- 
     @author : Laman.
     this script is for onclick function   iscurrentPositionEdit  .
     1.usage     :  used in editEmployer  function  line 248
--%>
<script>
	function iscurrentPositionEdit() {
		
		var currentposition = document.getElementById("isCurrentPositionEdit").checked;
		if (currentposition) {
			$("#urrentPositionEdit" ).val( 'Y' );
			document.getElementById("endDateEditDiv").style.display = 'none';
			$('#endYearEdit').removeAttr('data-validation');
			$('#endMonth').removeAttr('data-validation');
		}else {
			
			document.getElementById("endDateEditDiv").style.display = '';
			/* $("#endYearEdit").attr("data-validation", "required");
			$("#endMonthEdit").attr("data-validation", "required"); */
			
			 var exisitngEndYear;
			 if ($('#exisitngEndYear').length)
			 {
				 exisitngEndYear = document.getElementById("exisitngEndYear").value; 
			 }
			 else
			 {
				 exisitngEndYear = '';
				 $("#endYearEdit" ).val( '' );
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
	
	 if((startYear == endYear) && (sMonthNumber > eMonthNumber)){
		$("input#startYear").parent().after("<p class='jquery_form_error_message'><span>Start Month Should be less then end Month</span></p>");
		
	}else if((startYear > endYear )&&(endYear != '' && endYear!=null && endYear!='undefined')){
		$("input#startYear").parent().after("<p class='jquery_form_error_message'><span>Start Date Should be less then end Date</span></p>");
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
	
	 if((startYear == endYear) && (sMonthNumber > eMonthNumber)){
		$("input#startYear").parent().after("<p class='jquery_form_error_message'><span>Start Month Should be less then end Month</span></p>");
		
	}else if(startYear > endYear ){
		$("input#startYear").parent().after("<p class='jquery_form_error_message'><span>Start Date Should be less then end Date</span></p>");
	}else{
		$('#startYearDiv').parent().find('p.jquery_form_error_message').remove(); 
	}
}
</script>
<script>
function clearStartDate(){
	$("#startYear").val("");
	};
function clearEndDate(){
	$("#endYear").val("");
	};
</script>

<script>
function editProfCandEmpStartDate(){
	$("#startYear").val("");
	};
 function editProfCandEmpEndDate(){
	$("#endYearEdit").val("");
	}; 
</script> 