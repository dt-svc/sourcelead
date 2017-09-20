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
			
			$("#candidateEmployersAdd").attr("disabled", "disabled");
			$("#candidateEmployersAdd").addClass('button').val('Processing..'); 
	        $.post(sourceleadUrl+"/storeCandidateEmployers?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId, formData, function(data) {
	        	
	        	if (data != null && data != 'undefined'
					&& data != "") {
				if (data.indexOf('Sign In') >= 0) {
					location.replace(sourceleadUrl);
				} else if (data.indexOf('exception') >= 0) {
					location.replace(sourceleadUrl  + "/errorPage");
				} else {
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
				}
	        	}
			
	        });
			
			/* //To disable addButton 
			$("#candidateEmployersAdd").attr("disabled", "disabled");
			var frm = $('#employers_form').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, "");
			//It will escape all the special characters
			var jsonData = encodeURIComponent(con);
			var sourceleadUrl = '${sourceleadUrl}';

			$.ajax({
						type : "POST",
						url : sourceleadUrl
								+ "/storeCandidateEmployers?"+tokenParamName+"="+tokenValue+"&json="
								+ jsonData + "&candidateId=" + candidateId,
						success : function(data) {

							if (data != null && data != 'undefined'
									&& data != "") {
								if (data.indexOf('Sign In') >= 0) {
									location.replace(sourceleadUrl);
								} else if (data.indexOf('exception') >= 0) {
									location.replace(sourceleadUrl  + "/errorPage");
								} else {
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
								}
							}

						},
					}); */
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
	function addExperience(candidateId) {

		var result = "";
		result = result + '   <!-- Popup Div --> ';
		result = result	+ '	         <form id="employers_form" name="employers_form" method="post">';
		result = result+'	           <i class="fa fa-close close-div" onClick="closeCertificatePopup();"></i>';
		result = result	+ '	             <div class="form-horizontal clearfix">';
		result = result + '	               <div class="clearfix mb10">';
		result = result + '	                 <div class="col-md-12">';
		result = result	+ '	                  <input type="text" class="form-control"  placeholder="Employer *" name="name" data-validation="required validate_Space validate_length length2-100" field-name="Employer Name" value=""> <span class="hidden">Employer Name</span>';
		result = result + '	                 </div>';
		result = result + '	               </div>';
		result = result + '	               <div class="clearfix mb10">';
		result = result + '	                 <div class="clearfix checkbox">';
		result = result + '	                   <label>';
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
		
		result = result + '	               <div class="clearfix mb10 dates">';
		
		result = result + '	                   <div class="col-md-6" id="startYearDiv">';
		result = result + '	                    <p>Start Date</p>';
		result = result	+ '	                   <input type="text" max class="form-control calendar" value="" id="startYear" name="startYear"  placeholder="Start Date"  field-name="Start Date" readonly>';
		result = result + '	                 </div>';
		
		result = result + '	                   <div class="col-md-6 endDate" id="endDateDiv" >';
		result = result + '	                  <p>End Date</p>';
		result = result	+ '	                   <input type="text" max class="form-control calendar" id="endYear" name="endYear" field-name="End Year"  placeholder="End Date" readonly>';
		result = result + '	                 </div>';
		
		result = result + '	                 </div> ';
		result = result + '	               <div class="clearfix mr">';
		result = result	+ '	                 <button type="button" id ="candidateEmployersAdd" onClick ="candidateEmployersStore('+ candidateId+ ');"class="btn btn-sm btn-primary pull-right">Add</button>';
		result = result + '	               </div>';
		result = result + '	             </div>';
		result = result + '	        </form> ';
		$("#popUpDivEmp").empty();
		$("#popUpDivEmp").append(result);

		
		
		

		$( "#startYear, #endYear" ).datepicker({ 
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
		/* $('html').click(function() {
			$(".edit-details").removeClass("edit-details-active");
		}); */

	}
</script>

<script>

	function editEmployer(candidateEmpId, candidateId) {
		$.ajax({
					type : "POST",
					url : sourceleadUrl
							+ "/getCandidateEmployerByCandidateIdAndCandidateEmpoyerId?"+tokenParamName+"="+tokenValue+"&candidateId="
							+ candidateId + "&candidateEmpId=" + candidateEmpId,
					success : function(data) {

						var json = JSON.parse(data);

						if (data != null && data != 'undefined' && data != "") {

							if (data.indexOf('Sign In') >= 0) {
								location.replace(sourceleadUrl);
							} else if (data.indexOf('exception') >= 0) {
								location.replace(sourceleadUrl + "/errorPage");
							} else {

								var result = "";
								result = result + '      <!-- Popup Div --> ';
								result = result	+ '         <form id="candidateExperianceEdit_form" name="candidateExperianceEdit_form" method="post">';
								result = result+'	           <i class="fa fa-close close-div" onClick="closeCertificatePopup();"></i>';
								result = result	+ '	             <div class="form-horizontal clearfix">';
								result = result + '	               <div class="clearfix mb10">';
								result = result + '	                 <div class="col-md-12">';
								result = result	+ '	                  <input type="text" class="form-control"  placeholder="Employer *" name="name" data-validation="required validate_Space validate_length length2-100" field-name="Employer Name" value="'+json.name+'"> <span class="hidden">Employer Name</span>';
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
								
								result = result	+ '	               <div class="clearfix mb10 dates">';
								result = result	+ '<div class="col-md-6">';
								result = result + '	                 <p>Start Date</p>';
								if (json.startYear != null && json.startYear != '' && json.startYear != 'undefined') {
									result = result	+ '<input type="text" max class="form-control calendar" value="'+ json.startYear+ '" id="startYear" name="startYear" onblur="validYearEdit();"  placeholder="Start Date"  field-name="Start Date" data-validation="required" data-validation-optional="true" readonly>';
								}else{
									result = result	+ '<input type="text" max class="form-control calendar" value="" id="startYear" name="startYear" onblur="validYearEdit();"  placeholder="Start Date"  field-name="Start Date" data-validation="required" data-validation-optional="true" readonly>';
								}
								result = result + '</div>';
								
								
								result = result	+ '<div class="col-md-6" id="endDateEditDiv">';
								result = result	+ '<p>End Date</p>';
								if (json.endYear != null && json.endYear != '' && json.endYear != 'undefined') {
									result = result	+ '<input type="text"  class="form-control calendar" value="'+ json.endYear+'" id="endYearEdit" name="endYear" placeholder="Enter Year"  field-name="End Date" data-validation="required" data-validation-optional="true" readonly>';
									result = result	+ '<input type="hidden" value="'+json.endYear+'" id="exisitngEndYear">';
								}else{
									result = result	+ '<input type="text"  class="form-control calendar" value="" id="endYearEdit" name="endYear" placeholder="End Date"  field-name="end Date" data-validation="required" data-validation-optional="true" readonly>';
								}
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
								
					$( "#startYear, #endYearEdit" ).datepicker({ 
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
		               var other = this.id == "startYear" ? "#endYearEdit" : "#startYear";
		               var option = this.id == "startYear" ? "maxDate" : "minDate";        
		               if ((selectedDate = $(other).val()).length > 0) {
		                   year = selectedDate.substring(selectedDate.length-4, selectedDate.length);
		                   month = jQuery.inArray(selectedDate.substring(0, selectedDate.length-5), $(this).datepicker('option', 'monthNames'));
		                   $(this).datepicker( "option", option, new Date(year, month, 1));
		               }
		           }
		       });
					
								

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
								/* $('html').click(function() {
											$(".edit-details").removeClass(
													"edit-details-active");
										}); */
								
							}
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
		 var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
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
					url : sourceleadUrl	+ "/getCandidatesEmployersListByCandidateIdAndOrgLocationID?"+tokenParamName+"="+tokenValue+"&candidateId="
							+ candidateId,
					success : function(data) {
					/* 	if (data != null && data != 'undefined' && data != "" && data != '[]') {
							if (data.indexOf('Sign In') >= 0) {
								location.replace(sourceleadUrl);
							} else if (data.indexOf('exception') >= 0) {
								location.replace(sourceleadUrl + "/errorPage");
							} else { */
								var json = JSON.parse(data);
		             			/* for (var i = 0; i < 1; i++) {
		                            var val= json[i];
		                            var opt = "";
		                                    if(val.msg != null && val.msg != '' && val.msg != 'undefined'){
		                                            opt  = opt + '<div class="success_pop" id="selector3" style="display: block;">';
		                                            opt  = opt + '<i class="fa fa-check-circle"></i><span>'+val.msg+'</span>';
		                                            opt  = opt + '</div>';
		                                            }
		                                    $("#msgDiv2").empty();
		                                    $("#msgDiv2").append(opt);
		                            } */
								$("#candidateEmployersList").empty();
								var result = "";
								result = result	+ "<table id='candidateEmployersPagination' class='display' cellspacing='0' width='100%' >";
								result = result + "<thead>";
								result = result + "  <tr>";
								result = result	+ '           <th>Employer</th>';
								result = result	+ '           <th>Designation</th>';
								result = result + '           <th>Address</th>';
								result = result	+ '           <th>Start date<br>(<small class="custom-small">'+candExpListDate+'</small>)</th>';
								result = result	+ '           <th>End date<br>(<small class="custom-small">'+candExpListDate+'</small>)</th>';
								result = result + '           <th>Action</th>';
								result = result + "  </tr>";
								result = result + "</thead>";
								result = result + "<tbody>";
								for (var i = 0; i < json.length; i++) {
									var opt = json[i];
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
										result = result + '<td><div><span>'	+ opt.startYear	+ '</span></div></td>';
									} else {
										result = result		+ '<td><div><span>----</span></div></td>';
									}

									if (opt.endYear != null	&& opt.endYear != '' && opt.endYear != 'undefined') {
										result = result + ' <td><div><span>'+ opt.endYear+ '</span></div></td>';
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
						/* 	}
						} else {
							var result = "";
							result = result + "   <div class='row'>";
							result = result + "	<div class='col-lg-12'>";
							result = result + "		<div class='content-area clearfix'>";
							result = result + "			<div class='blank-pages'>";
							result = result
									+ "			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
							result = result
									+ "			        <p>No Candidate Employers Found</p>";
							result = result + "			 </div>";
							result = result + "		</div>";
							result = result + "	</div>";
							result = result + "  </div> ";
							$("#candidateEmployersList").empty();
							$("#candidateEmployersList").append(result);
						} */
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
					url : sourceleadUrl	+ "/removeCandidateEmployerBycandidateIdandCandidateEmployerID?"+tokenParamName+"="+tokenValue+"&loginUserId="+loginUserId+"&orgLocationId="+orgLocationId+"&candidateId="+ candidateId + "&candidateemployerId="+candidateemployerId,
					success : function(data) {

						if (data != null && data != 'undefined' && data != "") {
							if (data.indexOf('Sign In') >= 0) {
								location.replace(sourceleadUrl);
							} else if (data.indexOf('exception') >= 0) {
								location.replace(sourceleadUrl + "/errorPage");
							} else {
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
								 empMsgFadein();
							}
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
		if ($('#candidateExperianceEdit_form').validate(false,
				validationSettings)) {

			var frm = $('#candidateExperianceEdit_form').serializeFormJSON();

			var formData = getFormData("candidateExperianceEdit_form");
	        $.post(sourceleadUrl+"/upDateCandidateEmployerByCandidateIdAndCandidateExperianceId?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId+"&candidateExperianceId="+candidateExperianceId, formData, function(data) {
	        	
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
	        });
			
			
			/* var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, "");
			var jsonData = encodeURIComponent(con);
			//It will escape all the special characters
			var sourceleadUrl = '${sourceleadUrl}';

			$.ajax({
						type : "POST",
						url : sourceleadUrl
								+ "/upDateCandidateEmployerByCandidateIdAndCandidateExperianceId?"+tokenParamName+"="+tokenValue+"&json="+ jsonData + "&candidateId=" + candidateId	+ "&candidateExperianceId"+ candidateExperianceId,
						success : function(result) {
							
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
						},
					}); */
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
		} else if (document.getElementById("endDateDiv").style.display == 'none') {
			
			document.getElementById("endDateDiv").style.display = 'block';
			$("#endYear").attr("data-validation", "required");
			$("#endMonth").attr("data-validation", "required");
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
			$("#urrentPositionEdit" ).val( 'Y' );
			document.getElementById("endDateEditDiv").style.display = 'none';
			$('#endYearEdit').removeAttr('data-validation');
			$('#endMonthEdit').removeAttr('data-validation');
		}else {
			
			document.getElementById("endDateEditDiv").style.display = '';
			$("#endYearEdit").attr("data-validation", "required");
			$("#endMonthEdit").attr("data-validation", "required");
			
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
			 
			/* if(exisitngEndYear == null || exisitngEndYear == '' || exisitngEndYear =='undefined'){
				$("#endYearEdit" ).val( '' );
			} */
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