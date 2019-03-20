// below details need to be dynamic as of now used the user details of 
//1. candidatesProfile.jsp scripts start here

	function candUpdateMsgFadeinFunction() {
		$("#selector11").fadeIn(200);
        $('#selector11').delay(2000).fadeOut('slow');
	}
	function preapareCustomFieldDetails(candidateId){
		$.ajax({
	        type : "POST",
	         url: atsUrl+"/getAllASectionCustomFields?&candidateId="+candidateId+"&requestFrom=candidate",        
	         success :function(data) {
	        	 $('#customFieldDataId').empty();
	        	 $('#customFieldDataId').append(data);
	           },
	     }); 
	}
	function deleteCandidate(candidateId,candidateType){
		if ($('#terminate_candidate_form').validate(false, validationSettings)) {
			var reason = document.getElementById("rejectReason").value;
			if(reason.length > 250){
				$(".rejectErrorMsgs").append("<p class='jquery_form_error_message'>The values should be between 2-250 characters</p>");
			}else{
				$(".rejectErrorMsgs").find('p.jquery_form_error_message').remove();
				$.post(atsUrl+"/deleteCandidateByCandidateId?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId+"&reason="+reason+"&candidateType="+candidateType,{},function(result){
					if (result && result.response === "INVALID_DATA") {
					    $("#update_candidate").removeAttr("disabled");
					    $("#update_candidate").removeAttr('button').val('Save');
					    showError(result.responseMessage);
					} else if (result.response === "AWKWARD") {
					    location.href = "/errorPage";
					} else if (result.response === "SUCCESS") {
						showSuccessStatusPopup("Candidate marked as teminated");
						var json = result.object;
						if(candidateType == 'Associates') {
							location.replace(atsUrl+"/associatesList");
						} else {
							location.replace(atsUrl+"/candidatesList");
						}
					} else if (result.indexOf('Sign In') !== -1) {
						location.replace(sourceleadUrl);
					} else {
					    $("#update_candidate").removeAttr("disabled");
					    showError("Operation failed, please try again");
					}	
				});
			}
		
			return true;
		}else {
			return false;
		}
	}
	$('body').on('blur', '#terminate_candidate_form', function() {
		$("#terminate_candidate_form").showHelpOnFocus();
		$("#terminate_candidate_form").validateOnBlur(false, validationSettings);
	});
	

	  function candiateRemoveSpacesForRejectReason() {
	var output = document.getElementById("rejectReason").value;
	output = output.replace(/\s/g, "")
	if (output.length != 0) {
		$("#rejectReason").removeAttr("data-validation");
	}
}
$('#selector').delay(2000).fadeOut('slow');
function cancelAlert() {
	$("#removeCandidateAlert").empty();
	$(".project_inn_mask").fadeOut(200);
}
function removeCandidateAlertMessage(candidateId, candidateType) {
	$("#profileEditPopup").fadeOut();
	var result = "";
	result = result
			+ "<form:form id='terminate_candidate_form' name='terminate_candidate_form' method='post'> ";
	result = result + "	<div class='project_inn_mask'></div>";
	result = result
			+ "	    <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a' style='width:500px'>";
	result = result + "	<div class='clearfix'>";
	result = result
			+ "		<textarea id='rejectReason' class='form-control mt mb5' field-name='Reject Reason'  data-validation='required validate_length length2-250' name='rejectReason' value='' placeholder='Terminate Reason' rows='2' onkeypress='candiateRemoveSpacesForRejectReason();'></textarea>";
	result = result + "	</div>";
	result = result + "	<div class='rejectErrorMsgs'/>";
	result = result
			+ '		<b href="javascript:void(0)" class="mt10"><input style="width:50%" id="confirm-id" type="button" value="Ok" onclick="deleteCandidate('
			+ candidateId
			+ ',\''
			+ candidateType
			+ '\');"><input style="width:50%" class="u_can_color" type="button"  value="Cancel" onClick="cancelAlert();"></b>';
	result = result + "	</div>";
	result = result + "</form:form>";

	$("#removeCandidateAlert").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
function reActivateCandidateAlertMessage(candidateId){
	var result = "";
	var loginUserId = userId;
//	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	/* var message = confirm("You are choosing to ReActivate the Candidate");
	if (message == true) { */
		var reactivate = '1';
		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		$.post(atsUrl+"/reActivateCandidateByCandidateId?"+tokenParamName+"="+tokenValue+"&loginUserId="+loginUserId+"&orgLocationId="+orgLocationId+"&candidateId="+candidateId+"&reActivate="+reactivate,{},function(result){
			if (result && result.response === "INVALID_DATA") {
			    $("#update_candidate").removeAttr("disabled");
			    $("#update_candidate").removeAttr('button').val('Save');
			    showError(result.responseMessage);
			} else if (result.response === "AWKWARD") {
			    location.href = "/errorPage";
			} else if (result.response === "SUCCESS") {
				location.replace(atsUrl+"/candidatesList");
			} else if (result.indexOf('Sign In') !== -1) {
				location.replace(sourceleadUrl);
			} else {
			    showError("Operation failed, please try again");
			}			
		});
		/* $.ajax({
	        type : "POST",
	         url: atsUrl+"/reActivateCandidateByCandidateId?"+tokenParamName+"="+tokenValue+"&loginUserId="+loginUserId+"&orgLocationId="+orgLocationId+"&candidateId="+candidateId+"&reActivate="+reactivate,        
	         success :function(data) {
	               if(data != null && data != 'undefined' && data != ""){
	            			 location.replace(atsUrl+"/candidatesList");
	                } 
	         },
		}); */ 
		
// 	}
	
}
function saveCandidateTags(candidateId){
	 var tagName = $("#tagName").val();
	 var jsonData = encodeURIComponent(tagName);
	 var loginUserId =userId;
	 //var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	 if(tagName != null && tagName != '' && tagName !='undefined') {
		 Urlcheck = atsUrl+"/saveOrUpdateTagsBycandidateId?"+tokenParamName+"="+tokenValue+"&loginUserId="+loginUserId+"&orgLocationId="+orgLocationId+"&candidateId="+candidateId+"&tagName="+jsonData
	 } else {
		 Urlcheck = atsUrl+"/saveOrUpdateTagsBycandidateId?"+tokenParamName+"="+tokenValue+"&loginUserId="+loginUserId+"&orgLocationId="+orgLocationId+"&candidateId="+candidateId+"&tagName="+""
	 }
	 $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 $.ajax({  
        type : "POST",
        url: Urlcheck,        
        success :function(result) {
       	$("#loading").removeAttr('style');
			if(result && (result.response === "INVALID_DATA" || result.response === "INVALID_USER")) {
       		showError(result.responseMessage);
       	} else if (result.response === "AWKWARD") {
       	    location.href = "/errorPage";
       	} else if (result.response === "SUCCESS") {
       		showSuccessStatusPopup("Tags details updated successfully");
       		var candidateType = "Candidates";
         	  	candidateProfile(candidateId,candidateType);
         	  	candidateQuickViewProfile(candidateId);
       	} else if (result.indexOf('Sign In') !== -1) {
       		location.replace(sourceleadUrl);
       	} else {
       	    showError("Operation failed, please try again");
       	}
      },
   }); 
}
function editSkill(candidateId){
	$("#skillName").prop('disabled', false).trigger("chosen:updated");
	$("#editSkillBtn").remove();
	$("#editSkillBtnSpan").hide();
	window.skillCountBefore = $("#skillName :selected").length;
	var result = '';
	result = result + '<button type="button" id="saveSkillBtn" name="saveSkillBtn" class="btn btn-primary btn-sm" onclick="saveSkill('+candidateId+');">Save Skill</button>';
	$("#saveSkillBtnSpan").empty();
	$("#saveSkillBtnSpan").append(result);
	$("#saveSkillBtnSpan").show();
	
	//Adding Customized Skills to the list this method is slead-common.js
	tagIt("skillName", 'Press Enter-Key Or Comma(,) To Add New Skill:', 'tagItBykeyForSkills','skills','');
	
}

function saveSkill(candidateId){
	$("#skillName").prop('disabled', true).trigger("chosen:updated");
	window.skillCountAfter = $("#skillName :selected").length;
	if(skillCountBefore != skillCountAfter){
		saveSkils(candidateId);
	}
	$("#saveSkillBtn").remove();
	$("#saveSkillBtnSpan").hide();
	var result = '';
	result = result + '<button type="button" id="editSkillBtn" name="editSkillBtn" class="btn btn-primary btn-sm" onclick="editSkill('+candidateId+');">Edit Skill</button>';
	$("#editSkillBtnSpan").empty();
	$("#editSkillBtnSpan").append(result);
	$("#editSkillBtnSpan").show();
	skillCountAfter = skillCountBefore = 0;
}
function saveSkils(candidateId){
	 var skillName = $("#skillName").val();
	 var jsonData = encodeURIComponent(skillName);
	 var loginUserId = userId;
//	 var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	 if(skillName != null && skillName != '' && skillName !='undefined') {
		 Urlcheck = atsUrl+"/saveOrUpdateOnlySkillBycandidateId?"+tokenParamName+"="+tokenValue+"&loginUserId="+loginUserId+"&orgLocationId="+orgLocationId+"&candidateId="+candidateId+"&skillName="+jsonData
	 } else {
		 Urlcheck = atsUrl+"/saveOrUpdateOnlySkillBycandidateId?"+tokenParamName+"="+tokenValue+"&loginUserId="+loginUserId+"&orgLocationId="+orgLocationId+"&candidateId="+candidateId+"&skillName="+""
	 }
	 $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 $.ajax({  
        type : "POST",
        url: Urlcheck,        
        success :function(result) {
       	$("#loading").removeAttr('style');
			if(result && (result.response === "INVALID_DATA" || result.response === "INVALID_USER")) {
       		showError(result.responseMessage);
       	} else if (result.response === "AWKWARD") {
       	    location.href = "/errorPage";
       	} else if (result.response === "SUCCESS") {
       		showSuccessStatusPopup("Visa details updated successfully");
       		var candidateType = "Candidates";
         	  	candidateProfile(candidateId,candidateType);
         	  	candidateQuickViewProfile(candidateId);
       	} else if (result.indexOf('Sign In') !== -1) {
       		location.replace(sourceleadUrl);
       	} else {
       	    showError("Operation failed, please try again");
       	}
      },
   }); 
}  
function upDateCandidate(candidateId) {
	var validationSettings = {
	        errorMessagePosition : 'element',
	       borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
         }; 
	rateValidations();
	 $('#emailDiv').find('p.jquery_form_error_message').remove();
	
	 if ($('#candidateProfileEdit_form').validate(false, validationSettings)){
  		var formData = getFormData("candidateProfileEdit_form");
		$("#update_candidate").attr("disabled", "disabled");
		$("#update_candidate").addClass('button').val('Processing..');
		$.post(atsUrl+"/updateCandidate?"+tokenParamName+"="+tokenValue+"", formData, function(result) {
			if (result && result.response === "INVALID_DATA") {
			    $("#update_candidate").removeAttr("disabled");
			    $("#update_candidate").removeAttr('button').val('Save');
			    showError(result.responseMessage);
			} else if (result.response === "AWKWARD") {
			    location.href = "/errorPage";
			} else if (result.response === "SUCCESS") {
			    $("#selector11").removeAttr("style");
			    $("#selector11").css({ display: "block" });
			    $("#candMsgDivSum").text("Candidate Updated Successfully");
			    candUpdateMsgFadeinFunction();
			    candidateQuickViewProfile(candidateId);
			} else if (result.indexOf('Sign In') !== -1) {
				location.replace(sourceleadUrl);
			} else {
			    $("#update_candidate").removeAttr("disabled");
			    showError("Operation failed, please try again");
			}
		});
		/* $.ajax({
			type : "POST",
    	    data: formData,
			url : sourceleadUrl+"/updateCandidate?"+tokenParamName+"="+tokenValue+"",
			success : function(result) {
				if (data.indexOf('Sign In') >= 0) {
					location.replace(sourceleadUrl);
				} else if (result && result.response === "INVALID_DATA") {
				    $("#update_candidate").removeAttr("disabled");
				    $("#update_candidate").removeAttr('button').val('Save');
				    showError(result.responseMessage);
				} else if (result.response === "SUCCESS") {
				    $("#selector1").removeAttr("style");
				    $("#selector1").css({ display: "block" });
				    $("#candMsgDiv").html("Success!!  Candidate Updated");
				    candUpdateMsgFadeinFunction();
				    candidateQuickViewProfile(candidateId);
				} else if (result.response === "SESSION_TIMEDOUT") {
				    location.href = "/login";
				} else if (result.response === "AWKWARD") {
				    location.href = "/errorPage";
				} else {
				    $("#update_candidate").removeAttr("disabled");
				    showError("Invalid data");
				    showError("Operation failed, please try again");
				}
			}
		}); */
		return true;
	 } else { 
		return false;
	 } 
}
	 $('body').on('blur','#candidateProfileEdit_form',
				function() {
					$("#candidateProfileEdit_form").showHelpOnFocus();
					$("#candidateProfileEdit_form").validateOnBlur(false,validationSettings);
		});
		function fieldUpdateCandidate(candidateSkillId,local) {
			var value = local.value;
			var name = local.id;
			$.ajax({
						type : "POST",
						url : atsUrl+"/fieldUpdateCandidate?"+tokenParamName+"="+tokenValue+"&candidateSkillId="+candidateSkillId+"&name="+name+"&value="+value,
						success : function(result) {
							 
						},
			}); 	 	
	} 
		function employeeSubmissionProfile(candidateId) {
			$.ajax({
				type : "POST",
				url : atsUrl	+ "/getCandidatesEmployersListByCandidateIdAndOrgLocationID?"+tokenParamName+"="+tokenValue+"&candidateId=" + candidateId,
				success : function(data) {
					if (data && data.response === "INVALID_DATA") {
					    $("#update_candidate").removeAttr("disabled");
					    $("#update_candidate").removeAttr('button').val('Save');
					    showError(data.responseMessage);
					} else if (data.response === "AWKWARD") {
					    location.href = "/errorPage";
					}  else if (data.response === "SUCCESS") {
						$("#employeeSubmissionProfile").empty();
						var result = "";
						result = result +'        <div class="clearfix design-view">';
						result = result +'          <h2 class="education"><i class="fa fa-black-tie"></i>&nbsp;&nbsp;Employers</h2>';
						result = result +'          <div class="row">';
						result = result +'            <div class="col-md-12">';
						result = result +'              <ul class="list-unstyled personal-info">';
					//	var json = JSON.parse(data);
						for (var i = 0; i < data.data.length; i++) {
							var opt = data.data[i];
							if (opt.name != null && opt.name != '' && opt.name != 'undefined') {
								name = opt.name;
							} else {
								name = " ";
							}
							if (opt.designation != null	&& opt.designation!='' && opt.designation != 'undefined') {
								designation = opt.designation;
							} else {
								designation = " "; 
							}
							
							/* 
							 if (opt.startMonth != null && opt.startMonth != '' && opt.startMonth != 'undefined') {
								startMonth=   opt.startMonth;
							}else{
								startMonth = " ";
							} */
							
							if (opt.startYear != null && opt.startYear != '' && opt.startYear != 'undefined') {
								startYear=   opt.startYear;
							}else{
								startYear = " ";
							}
							
							/* if (opt.endMonth != null	&& opt.endMonth != '' && opt.endMonth != 'undefined') {
								endMonth = opt.endMonth;  
							} else {
								endMonth = " ";
							} */
							if (opt.endYear != null	&& opt.endYear != '' && opt.endYear != 'undefined') {
								endYear = opt.endYear;  
							} else {
								endYear = " ";
							}
							result = result +'   <li>';
							result = result +'   <div class="clearfix">';
							result = result +' <h3>'+name+'</h3>';
							result = result +' <p>'+designation+'</p>';
							result = result +' <p>';
						    if(opt.startYear != null && opt.startYear != '' && opt.startYear != 'undefined' && opt.endYear != null	&& opt.endYear != '' && opt.endYear != 'undefined'){
						    	result = result + startYear +' To '+endYear; 
						    }else if(opt.startYear != null && opt.startYear != '' && opt.startYear != 'undefined'){
						    	result = result + startYear; 
						    }else if(opt.endYear != null	&& opt.endYear != '' && opt.endYear != 'undefined'){
						    	result = result + endYear; 
						    }
							result = result +'  </p>';
							result = result +'  </div>';
							result = result +'</li>';
						}
						result = result +'              </ul>';
						result = result +'            </div>';
						result = result +'          </div>';
						result = result +'        </div>';
						$("#employeeSubmissionProfile").empty(); 
						$("#employeeSubmissionProfile").append(result);
					} else if (data.indexOf('Sign In') !== -1) {
						location.replace(sourceleadUrl);
					} 
				},
			});
}
function educationSubmissionProfile(candidateId){
 $.ajax({  
      type : "GET",
      url: atsUrl+"/getCandidateEducationListByCandidateId?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId,        
      success :function(response) {
    	  	if (response && response.response === "INVALID_DATA") {
    		    showError(response.responseMessage);
    		} else if (response && response.response === "INVALID_USER") {
    		    showError(response.responseMessage);
    		} else if (response.response === "AWKWARD") {
    		    location.href = "/errorPage";
    		} else if (response.response === "SUCCESS") {
    			$("#educationSubmissionProfile").empty();
           	 var result = "";
           	     result = result +'        <div class="clearfix design-view">';
					result = result +'          <h2 class="education"><i class="fa fa-graduation-cap"></i>&nbsp;&nbsp;Education</h2>';
					result = result +'          <div class="row">';
					result = result +'            <div class="col-md-12">';
					result = result +'              <ul class="list-unstyled personal-info">';
                     for (var i = 0; i < response.data.length; i++) { 
                    	 var opt = response.data[i];  
                    	 if(opt.name != null && opt.name != '' && opt.name != 'undefined'){
               		        EducationName = opt.name;
                    	    }else{
                    	    	EducationName = " ";
                    	    }
	                     if(opt.specialization != null && opt.specialization != '' && opt.specialization != 'undefined'){
	                    	 specialization= '(' + opt.specialization +')';
                    	  }else{
                    		  specialization= " ";
                       	 }
	                     if(opt.institute != null && opt.institute != '' && opt.institute != 'undefined'){
	                    	 institute = opt.institute;
                    	 }else{
                    		 institute = " ";
                    	 }
	                     if(opt.grade != null && opt.grade != '' && opt.grade  != 'undefined'){
	                    	 Educationgrade= opt.grade + ' , ';
                    	 }else{
                    		 Educationgrade = ""; 
                    	 }
                    	 if(opt.gradeType != null && opt.gradeType != '' && opt.grgradeTypeade  != 'undefined'){
                    		 EducationgradeType  = opt.gradeType + ' - ';
		                 }else{
		                	 EducationgradeType = " "; 
		                 }
                    	 if(opt.address != null && opt.address != '' && opt.address != 'undefined'){
                    		 Educationaddress = ' , ' +opt.address;
                    	 }else{
                    		 Educationaddress = " ";
                    	 }
                    	 if(opt.startDateStr != null && opt.startDateStr != '' && opt.startDateStr != 'undefined'){
                    		 EducationstartDateStr = opt.startDateStr;
                    	 }else{
                    		 EducationstartDateStr = " ";
                    	 }
                    	 if(opt.endDateStr != null && opt.endDateStr != '' && opt.endDateStr != 'undefined'){ 
                    		 EducationendDateStr = opt.endDateStr;
                    	 }else{
                    		 EducationendDateStr = " ";
                    	 }
	                        result = result +'                <li>';
							result = result +'                  <div class="clearfix">';
                    	    result = result +'                    <h3>'+EducationName+' <span class="small text-muted">&nbsp;'+specialization+'</span></h3>';
                    	    result = result +'                    <p>'+EducationgradeType + ' '+Educationgrade+' '+institute+' '+Educationaddress+'</p>';
                    	    if(opt.startDateStr != null && opt.startDateStr != '' && opt.startDateStr != 'undefined' && opt.endDateStr != null && opt.endDateStr != '' && opt.endDateStr != 'undefined'){
                    	    result = result +'                    <p>'+EducationstartDateStr+' To '+EducationendDateStr+'</p>';
                    	    }else if(opt.startDateStr != null && opt.startDateStr != '' && opt.startDateStr != 'undefined'){
                    	    	result = result +'                    <p>'+EducationstartDateStr+'</p>';
                    	    }else if(opt.endDateStr != null && opt.endDateStr != '' && opt.endDateStr != 'undefined'){
                    	    	result = result +'                    <p>'+EducationendDateStr+'</p>';
                    	    }
                    	    result = result +'                  </div>';
							result = result +'                </li>';
                     }
					result = result +'              </ul>';
					result = result +'            </div>';
					result = result +'          </div>';
					result = result +'        </div>';
				 $("#educationSubmissionProfile").empty(); 
                $("#educationSubmissionProfile").append(result); 
    		} else if (response.indexOf('Sign In') !== -1) {
    			location.replace(sourceleadUrl);
    		} else {
    		    showError("Operation failed, please try again");
    		}
    },
 }); 
}
function certificationSubmissionProfile(candidateId) {

 $.ajax({  
      type : "POST",
      url: atsUrl+"/getCandidateCertificationListByCandidateIdAndOrgLocationID?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId,        
      success :function(data) {
    	  if (data && data.response === "INVALID_DATA") {
  		    showError(data.responseMessage);
  		} else if (data && data.response === "INVALID_USER") {
  		    showError(data.responseMessage);
  		} else if (data.response === "AWKWARD") {
  		    location.href = "/errorPage";
			} else if (data.response === "SUCCESS") {
				$("#certificationSubmissionProfile").empty();
           	 	var result = "";
           	 	result = result +'<div class="clearfix design-view">';
				result = result +'<h2 class="education"><i class="fa fa-certificate"></i>&nbsp;&nbsp;Certification</h2>';
				result = result +'<div class="row">';
				result = result +'<div class="col-md-12">';
				result = result +'<ul class="list-unstyled personal-info">';
//					var json = JSON.parse(data);
				if(data.data.length > 0) {
					for (var i = 0; i < data.data.length; i++) {
						var opt = data.data[i]; 
	                    if(opt.score != null && opt.score != 'undefined' && opt.score != "") {
			            	score =' ( ' + opt.score;
			            } else {
			            	score = " ";
			            }
			            if(opt.scoreType != null && opt.scoreType != 'undefined' && opt.scoreType != "") {
			            	scoreType = opt.scoreType +' ) ';
			            } else {
			            	scoreType = " ";
			            }
			            if(opt.candidatevalidFromDateToString != null && opt.candidatevalidFromDateToString != 'undefined' && opt.candidatevalidFromDateToString != "") {
			            	candidatevalidFromDateToString = opt.candidatevalidFromDateToString;
			            } else {
			            	candidatevalidFromDateToString = " ";
			            }
			            if(opt.candidatevalidToDateToString != null && opt.candidatevalidToDateToString != 'undefined' && opt.candidatevalidToDateToString != "") {
			            	candidatevalidToDateToString = opt.candidatevalidToDateToString;
			            } else {
			            	candidatevalidToDateToString = " ";
			            }
			            	
						result = result +'<li>';
						result = result +'<div class="clearfix">';
						result = result +'<h3>'+opt.name+' <span class="small text-muted">&nbsp;'+score+'  '+scoreType+'</span></h3>';
						result = result +'<p>'+opt.provider+' </p>';
						if(opt.candidatevalidFromDateToString != null && opt.candidatevalidFromDateToString != 'undefined' && opt.candidatevalidFromDateToString != "" && 
								opt.candidatevalidToDateToString != null && opt.candidatevalidToDateToString != 'undefined' && opt.candidatevalidToDateToString != "") {
								result = result +'<p> '+candidatevalidFromDateToString+' TO '+candidatevalidToDateToString+'</p>';
						} else if(opt.candidatevalidFromDateToString != null && opt.candidatevalidFromDateToString != 'undefined' && opt.candidatevalidFromDateToString != "") {
							result = result +'<p> '+candidatevalidFromDateToString+'</p>';
						} else if(opt.candidatevalidToDateToString != null && opt.candidatevalidToDateToString != 'undefined' && opt.candidatevalidToDateToString != "") {
							result = result +'<p> '+candidatevalidToDateToString+'</p>';
						}
						result = result +'</div>';
						result = result +'</li>';
					}
				}
				result = result +'</ul>';
				result = result +'</div>';
				result = result +'</div>';
				result = result +'</div>';
					
				$("#certificationSubmissionProfile").empty();
				$("#certificationSubmissionProfile").append(result); 
			} else if (data.indexOf('Sign In') !== -1) {
				location.replace(sourceleadUrl);
			} 
    },
 }); 
}
$('.selectpicker').selectpicker('refresh');
function disableMonths() {

     if ($("#experienceYears").val() == "50"){
    	 $("#experienceMonths").val('');  
    	 $('#experienceMonths').attr("disabled", false);
     }else{
    	 $("#experienceMonths").val('');  
    	 $('#experienceMonths').selectpicker('refresh');
    	 $("#experienceMonths").attr("disabled", false); 
	 }    
}

//<!-- Employment Type equals Other then Text Box will Open script here -->
function updateDesignationOtherType(){
 var candidateType = $('#designationType1').val(); 
if(candidateType == 'Other' &&  candidateType != '' && candidateType !='undefined'){
	  document.getElementById("designationTypeDiv").style.display="block";
	  $("#designationOtherType").attr("data-validation-optional", "true");
	  $("#designationOtherType").attr("data-validation", "validate_Space validate_length length2-250");
}else{
	   document.getElementById("designationTypeDiv").style.display="none";
	  $("#designationOtherType").attr("data-validation-optional", "true");
	  $("#designationTypeDiv").find('p.jquery_form_error_message').remove();
} 
}


//<!-- Candidate Email validation script starts here -->

function validateCandidateEmail() {
 var email = $('#email').val(); 
 var tempEmail = $('#tempEmail').val(); 
 
 if(tempEmail != email){
 
 var candidateType = $('#candidateType').val(); 
 $('#emailDiv').find('p.jquery_form_error_message').remove();
 $("#update_candidate").removeAttr("disabled");
 if(email != null && email != ""){
	 $.ajax({
	    	type: "POST",
	        url: atsUrl+"/validateCandidateEmail?"+tokenParamName+"="+tokenValue+"&email="+email+"&candidateType="+candidateType, 
	        success: function(response) {
	        	if (response.response === "AWKWARD") {
	        	    location.href = "/errorPage";
	        	} else if (response.response === "SUCCESS") {
	        		if(response.object == 'true'){
		        		  $('#candidateType').val(); 
						  $("input#email").after("<p class='jquery_form_error_message'>"+email+" is Already Existed</span>");
						  $("#update_candidate").attr("disabled", "disabled");
						  $("#update_candidate").addClass('button').val('Processing..');
		        	}
		        	else{
		        		 $('input#email').removeAttr( "record-exist record-exist-errormsg");
		        		 $('#emailDiv').find('p.jquery_form_error_message').remove(); 
		        		 $("#update_candidate" ).addClass('button').val('Save');
                         $("#update_candidate").removeAttr("disabled");
                         validateEmailForExistenceUniversally(email, "input#email");
		        	}
	        	} else if (response.indexOf('Sign In') !== -1) {
	        		location.replace(sourceleadUrl);
	        	} else {
	        	    showError("Operation failed, please try again");
	        	}
	        },
	    });
 }else{
	 $("#emailStatusIconDiv").find("i").remove();
		 $("#emailStatusIconDiv").append('<i class="fa fa-check text-muted" style="color:#f6f6f6"></i>'); 
 }
 }else{
	 $('#update_candidate').removeAttr('disabled');
 }
}


//<!-- Candidate Email validation script ends here -->


//<!-- This script for Current Rate ,Rate Type,Expected Rate, Expected Rate Type validation checks -->

function rateValidations(){

var rateType = document.getElementById("expectedRateType").value; 
if(rateType != null && rateType != ""){
	 $('#expectedRate').removeAttr('data-validation-optional');
	 $('#expectedRate').attr("data-validation","required validate_Space validate_int validate_length length1-8"); 
}else{
	 $('#expectedRate').removeAttr('data-validation');
	 $('#expectedRate').attr("data-validation-optional","true");
	 $('#expectedRateType').removeAttr('data-validation');
	 $('#expectedRateType').attr("data-validation-optional","true"); 
}

var expectedRate = document.getElementById("expectedRate").value; 
if(expectedRate != null && expectedRate != ""){
	 $('#expectedRateType').removeAttr('data-validation-optional');
	 $('#expectedRateType').attr("data-validation","required"); 
	 $('#expectedRate').attr("data-validation", "required validate_Space validate_int validate_length length1-8");
}else{
	$('#expectedRateType').removeAttr('data-validation');
	$('#expectedRateType').attr("data-validation-optional","true"); 
}

var currentRateType = document.getElementById("currentRateType").value; 
if(currentRateType != null && currentRateType != ""){
	$('#currentRate').removeAttr('data-validation-optional');
	$('#currentRate').attr("data-validation","required validate_Space validate_int validate_length length1-8"); 
}else{
	$('#currentRate').removeAttr('data-validation');
 	$('#currentRate').attr("data-validation-optional","true"); 
	$('#currentRateType').removeAttr('data-validation');
 	$('#currentRateType').attr("data-validation-optional","true"); 
}

var currentRate = document.getElementById("currentRate").value; 
 if(currentRate != null && currentRate != ""){
	 $('#currentRateType').removeAttr('data-validation-optional');
	 $('#currentRateType').attr("data-validation","required"); 
	 $('#currentRate').attr("data-validation", "required validate_Space validate_int validate_length length1-8");
 }else{
	 $('#currentRateType').removeAttr('data-validation');
	 $('#currentRateType').attr("data-validation-optional","true"); 
 }
}
function employementType(){
 var employementType = $('#designationType1').val(); 
 var preval = document.getElementById("hiddenId").value;
	if(employementType == 'C2C' ||  employementType == 'Offshore C2C' ){
		/* document.getElementById("hideVendor").style.display="block";
		 $('#vendorName').attr("data-validation","required"); 
		 $('#vendorcontactName').attr("data-validation","required"); 
		 getVendorsByOrgLocation(); */
		 if(employementType == preval){
				document.getElementById("hideVendor").style.display="block";
				 getVendorsByOrgLocation();
			}else{
				document.getElementById("hideVendor").style.display="block";
			  $('#vendorName').val(''); 
			 
	            $('#vendorName').trigger("chosen:updated");
	            $('#vendorcontactName').empty();
		//	 $('#vendorcontact').val(''); 
	           $('#vendorcontactName').trigger("chosen:updated");
			} 
		 
	}else{
		 document.getElementById("hideVendor").style.display="none";
		 
		 $('#vendorName').val(''); 
		 $('#vendorcontactName').val(''); 
		 $('#vendorName').trigger("chosen:updated");
            $('#vendorcontactName').empty();
	//	 $('#vendorcontact').val(''); 
           $('#vendorcontactName').trigger("chosen:updated");
		 
	}

	if(employementType == 'C2C-1099'){
		if(employementType == preval){
			document.getElementById("hideVendor").style.display="block";
			 getVendorsByOrgLocation();
		}else{
			document.getElementById("hideVendor").style.display="block";
		    $('#vendorName').val(''); 
		 
            $('#vendorName').trigger("chosen:updated");
            $('#vendorcontactName').empty();
            $('#vendorcontactName').trigger("chosen:updated");
		} 
	}else{
		 
		 $('#vendorName').val(''); 
		 $('#vendorcontactName').val(''); 
		 
		 $('#vendorName').trigger("chosen:updated");
            $('#vendorcontactName').empty();
           $('#vendorcontactName').trigger("chosen:updated");
		 
	} 
	$('#designationType1 + p').remove();
}
function getVendorsByOrgLocation(){
 var vendorName = $("#updateVendorName").val();
 var vendorOrgRelationId = $("#updateVendorOrgRelationId").val();
	 $.ajax({
	    	type: "GET",
	        url: atsUrl+"/getVendorsByOrgLocation?", 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#vendorName').empty();
	        	$('#vendorName').append('<option value="">--select Vendor --</option>');
	            for(var i=0;i<json.length;i++){
	            	  var opt = json[i];
	            	  if(vendorName != null && vendorName != 'undefined' && vendorName  != ''){
	            		  $('#vendorName').append('<option '+ (opt.id == vendorOrgRelationId ? "selected" : '') +' value="'+opt.id+'">'+opt.orgName+'</option>');
		            	}else{
		            		$('#vendorName').append('<option value='+opt.id+' >'+opt.orgName+'</option>');	
		            	}
	            	  
	            }
	            $('#vendorName').trigger("chosen:updated");
	            getVendorContactList();
	            
	        }
	 });
}
function  removeValiationVendor() {
$("#errorvendor").find("p.jquery_form_error_message").remove();
}
function  removeValiationVendorContact() {
 $("#errorvendorContact").find("p.jquery_form_error_message").remove();
}
function getVendorContactList(){
$('#vendorcontactName').val('').trigger("chosen:updated");
	 var orgRelationID = $('#vendorName').val();
	 if(orgRelationID != null && orgRelationID != '' && orgRelationID != 'undefined'){
		 var vendorContactName = $("#updateVendorContactName").val();
		 var vendorSpocId = $("#updateSpocId").val();
		 $.ajax({
			    type: "POST",
		        url: vmsUrl+"/getOrgSpocsList?"+tokenParamName+"="+tokenValue+"&orgRelationId="+orgRelationID+"&type=Vendor",  
		        success: function(json) {
		        	//var json = JSON.parse(data);
		        	$('#vendorcontactName').empty();
		        	$('#vendorcontactName').append('<option value="">--select Vendor contact--</option>');
		             for(var i=0;i<json.data.length;i++){
		            	 var opt = json.data[i];
		            	 if(vendorContactName != null && vendorContactName != 'undefined' && vendorContactName  != ''){
		            		 $('#vendorcontactName').append('<option '+ (opt.id == vendorSpocId ? "selected" : '') +' value="'+opt.id+'">'+opt.spocName+' ('+opt.ownerName+')</option>');
			            	}else{
			            		$('#vendorcontactName').append('<option value='+opt.id+' >'+opt.spocName+' ('+opt.ownerName+')</option>');
			            	}
		            } 
		            $('#vendorcontactName').trigger("chosen:updated");
		        }
		 });  
	 }
	 
}
function editVisa(candidateId){
	$("#visaName").prop('disabled', false).trigger("chosen:updated");
	$("#editVisaBtn").remove();
	$("#editVisaBtnSpan").hide();
//		window.visaCountBefore = $("#visaName :selected").length;
	var result = '';
	result = result + '<button type="button" id="saveVisaBtn" name="saveVisaBtn" class="btn btn-primary btn-sm" onclick="saveVisa('+candidateId+');">Save Visa</button>';
	$("#saveVisaBtnSpan").empty();
	$("#saveVisaBtnSpan").append(result);
	$("#saveVisaBtnSpan").show();
    
}

function saveVisa(candidateId){
	$("#visaName").prop('disabled', true).trigger("chosen:updated");
//		window.visaCountAfter = $("#visaName :selected").length;
//		if(visaCountBefore != visaCountAfter){
	saveVisas(candidateId);
//		}
	$("#saveVisaBtn").remove();
	$("#saveVisaBtnSpan").hide();
	var result = '';
	result = result + '<button type="button" id="editVisaBtn" name="editVisaBtn" class="btn btn-primary btn-sm" onclick="editVisa('+candidateId+');">Edit Visa</button>';
	$("#editVisaBtnSpan").empty();
	$("#editVisaBtnSpan").append(result);
	$("#editVisaBtnSpan").show();
	visaCountAfter = visaCountBefore = 0;
}
function saveVisas(candidateId) {
 var visaName = $("#visaName").val();
 var jsonData = encodeURIComponent(visaName);
 var loginUserId = '${SourceLeadWeb_userId}';
 var orgLocationId = '${SourceLeadWeb_orgLocationId}';
 if(visaName != null && visaName != '' && visaName !='undefined'){
	 Urlcheck = atsUrl+"/saveOrUpdateOnlyVisaBycandidateId?"+tokenParamName+"="+tokenValue+"&loginUserId="+loginUserId+"&orgLocationId="+orgLocationId+"&candidateId="+candidateId+"&visaName="+jsonData
 }else{
	 Urlcheck = atsUrl+"/saveOrUpdateOnlyVisaBycandidateId?"+tokenParamName+"="+tokenValue+"&loginUserId="+loginUserId+"&orgLocationId="+orgLocationId+"&candidateId="+candidateId+"&visaName="+""
 }
 $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
 $.ajax({  
     type : "POST",
     url: Urlcheck,        
     success :function(result) {
        	  	$("#loading").removeAttr('style');
  				if(result && (result.response === "INVALID_DATA" || result.response === "INVALID_USER")) {
          		showError(result.responseMessage);
          		} else if (result.response === "AWKWARD") {
          	    	location.href = "/errorPage";
          		} else if (result.response === "SUCCESS") {
          			showSuccessStatusPopup("Visa details updated successfully");
          			var candidateType = "Candidates";
          	  	  	candidateProfile(candidateId,candidateType);
           	  	//candidateQuickViewProfile(candidateId);
          		} else if (result.indexOf('Sign In') !== -1) {
              		location.replace(sourceleadUrl);
              	} else {
              	    showError("Operation failed, please try again");
              	}            	  
   },
}); 

 
}  

function cleardateofBirth(){
	$("#dateofbirth-candidate").val("");
	};
	
function genderCheckValidation(id){
	 var gendersList = [];
		 $("input[name='gender']:checked").each( function (i) {
			gendersList[i] = $(this).val();
		});
		 
	if (gendersList.length > 0) { 
	document.getElementById("genderValue").value = id.value;
	}else{
		$('#genderValue').val(''); 
	}
} 

function showSuccessStatusPopup(status) {
$("#statusAlert").css("display","block");
$("#statusAlert span").html(status);
/* 	setTimeout(function(){
	$("#statusAlert").css("display","none");				 			
},2000); */
$('#statusAlert').delay(2000).fadeOut('slow');
}	
// 1. candidatesProfile.jsp scripts END>>>>>>>.

//2. candidatesResumes.jsp scripts start here >>>>>>>.
function candidateResume(candidateId){
	var candidateType = '${candidateType}';
	var result ="";
	result = result+'<div class="project_tabs_inn_head addUser-org">';
	result  = result + '<div class="success_pop" id="selector2" style="display: none;">';
    result  = result + '<i class="fa fa-check-circle"></i><span id="resMsgDiv"></span>';
    result  = result + '</div>';
	result = result +' <div id="msgDiv3" ></div>';
	
	if(window.candidateStatusGlobal == '0'){
		if(candidateType == "Candidates"){
			//'<sec:authorize access="hasAnyRole(\'ROLE_ADD_CANDIDATE_RESUMES\',\'ROLE_CROWDSOURCING_CANDIDATES\',\'ROLE_CROWDSOURCING_CANDIDATES\',\'ROLE_ADD_ASSOCIATE_RESUMES\')">'; 
			  result = result+'<a href="javascript:void(0)" class="addUser-org" ><i class="glyphicon glyphicon-plus"></i>Add Resume</a>';
			//'</sec:authorize>';
		}else{
			//'<sec:authorize access="hasRole(\'ROLE_ADD_ASSOCIATE_RESUMES\')">'; 
			  result = result+'<a href="javascript:void(0)" class="addUser-org" ><i class="glyphicon glyphicon-plus"></i>Add Resume</a>';
			//'</sec:authorize>';
		}
		
	}else{
		if(candidateType == "Candidates"){
			//'<sec:authorize access="hasAnyRole(\'ROLE_ADD_CANDIDATE_RESUMES\',\'ROLE_CROWDSOURCING_CANDIDATES\',\'ROLE_CROWDSOURCING_CANDIDATES\')">';
			   result = result+'<a href="javascript:void(0)" onClick="refreshResumeList('+candidateId+'); addResume('+candidateId+');" class="addUser-org" id="addCandidateResume"><i class="glyphicon glyphicon-plus"></i>Add Resume</a>';
			//'</sec:authorize>';
		}else{
			//'<sec:authorize access="hasRole(\'ROLE_ADD_ASSOCIATE_RESUMES\')">';
			   result = result+'<a href="javascript:void(0)" onClick="refreshResumeList('+candidateId+'); addResume('+candidateId+');" class="addUser-org" id="addCandidateResume"><i class="glyphicon glyphicon-plus"></i>Add Resume</a>';
			//'</sec:authorize>';
		}
		
	}

		result = result  +' <div id="popUpDivResume" class="edit-details col-md-4"> ';
		result = result  +' </div>  ';
		result  = result + '<div class="project_tabs_inn_table vendor_wrap_inn_table">';
		result  = result + ' <div id="resumesList" ></div>';
		result = result+'</div>';

	
    $("#tab-1").empty();
    $("#tab-2").empty(); 
    $("#tab-3").empty();  
    $("#tab-4").empty();  
    $("#tab-5").empty();  
    $("#tab-6").empty();  
    $("#tab-2").append(result); 
    
}
function addResume(candidateId) {	
	var result = "";
	result = result+'	           <form id="candidateResume_form" name="candidateResume_form" action="upload" method="post">';
	result = result+'	           <i class="fa fa-close close-div" onClick="closeResumePopup();"></i>';
	result = result+'	                <div class="form-horizontal clearfix" id="newResumeDiv">';
	result = result+'	                  <div class="clearfix mb10">';
	result = result+'	                    <div class="col-md-12">';
	result = result+'	                      <input type="text" class="form-control input-sm" id="title" name="title" data-validation="validate_length length2-100" data-validation-optional="true" field-name="Title" value="" placeholder="Title">';
	result = result+'	                    </div>';
	result = result+'	                  </div>';
	result = result+'	                  <div class="clearfix mb10">';
	result = result+'	                    <div class="col-md-12">';
	result = result+'	                      <textarea rows="2" placeholder="Description" value="" id="description" name="description" data-validation="validate_length length2-250" data-validation-optional="true" field-name="Description" class="form-control"></textarea>';
	result = result+'	                    </div>';
	result = result+'	                  </div>';
	result = result+'	                  <div class="clearfix mb10" id="uploadResumeDiv">';
	result = result+'	                    <div class="col-md-12">';
	result = result+'							<div class="input-group" id="uploadFileGroup">';
	result = result+'	                          <label class="input-group-btn"> <span class="btn btn-primary" onchange="uploadCandidateResume();"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File';
	result = result+'	                            <input type="file" id="uploadBtn" style="display: none;" class="upload" >';
	result = result+'	                            </span> </label>';
	result = result+'	                          <input type="text" class="form-control" readonly id="uploadFile" name="docFile" placeholder="Upload Resume..." field-name="Resume" data-validation="required validate_Space validatedoc">';
	result = result+'	                        </div>';
	result = result+'	                    </div>  ';  
	result = result+'	                  </div>   ';  
	
	result = result+'	                  <div class="clearfix mb10" id="resumeNameDiv" style="display: none;">';
	result = result+'	                    <div class="col-md-12 mt">';
	result = result+'	                      <label id="resumeName" name="resumeName" class="text-primary tbldata-dotdot-vsm pull-left"></label>&nbsp;&nbsp;<span id="delete_resume" onclick="removeResumeNameDiv();"><i class="fa fa-trash removeIcon"></i></span>';
	result = result+'	                    </div>';
	result = result+'	                  </div>';
	
	result = result+'	                  <div class="clearfix mr mt" id="resumeUpdateButton" style="display: none;">';
	result = result+'	                    <button type="button" id="update_resume" class="btn btn-sm btn-primary pull-right">Update</button>';
	result = result+'	                  </div>';
	
	result = result+'	                  <div class="clearfix mr mt" id="resumeAddButton">';
	result = result+'	                    <button type="button" id="add_resume" class="btn btn-sm btn-primary pull-right" onclick="storeCandidateResume('+candidateId+',null);">Add</button>';
	result = result+'	                  </div>';
	result = result+'	                </div>';
	result = result+'	                </form>';
	
	$("#popUpDivResume").empty();
	$("#popUpDivResume").append(result);
	
	$(".edit-details").toggleClass("edit-details-active");
	$(".close-div").click(function(){
		$(this).parent("div.edit-details").removeClass("edit-details-active");
		 });
	 $(".addUser-org, .assign-org, .collapse-div-adduser, .collapse-div, .edit-details").click(function(e){
		 e.stopPropagation();
	 });
	 
	 	/* upload file script to show selected file name*/
	    document.getElementById("uploadBtn").onchange = function () {
		   //$("#uploadFile").dir(this.files[0]);
	    	//document.getElementById("uploadFile").value = this.value;
	    	$('#uploadFile').val($(this).val().replace(/C:\\fakepath\\/i, ''))
	  
	   }; 
}
function storeCandidateResume(candidateId,resumeId){
	var validationSettings = {
		        errorMessagePosition : 'element',
		        borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd' 
	         }; 
		 if ($('#candidateResume_form').validate(false, validationSettings)) {
			 if($("#uploadFile").length > 0 && (resumeId==null ? ($("#uploadFileGroup").length && $("#uploadFileGroup label").length && $("#uploadFileGroup span").length && $("#uploadBtn").length > 0 && $("#uploadFile").length > 0) : ($("#resumeNameDiv").length > 0 && $("#resumeName").length > 0))) {
				 var formData =  getFormData('candidateResume_form'); 
					$("#candidateResume_form").attr("disabled", "disabled");
				    $("#add_resume").attr("disabled", "disabled");
					$.ajax({
						type : "POST",
		        	    data: formData,
						url : atsUrl+"/saveCandidateResume?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId+"&resumeId="+resumeId,
						success : function(result) {
							 if(result.response === "SESSION_TIMEDOUT") {
					    		location.replace(sourceleadUrl);
					    	 } else if (result.response === "AWKWARD") {
			                        location.href = "/errorPage";
//			                        showError(result.responseMessage);
					    	 } else if (result.response === "SUCCESS") {
		            			$('#perVal').attr('aria-valuenow', result.object).css('width',result.object+'%');
			          			if(result.object<50){
				          			$("#perVal").removeClass('progress-bar-success');
				          			$("#perVal").addClass("progress-bar-danger");
				          		}
				          		if(result.object>=50) {
				          			$("#perVal").removeClass('progress-bar-danger');
				          			$("#perVal").addClass("progress-bar-success");
				          		}
		            			$("#disVal").empty();
		            			$("#disVal").append(result.object+'%');
		            			// for quick view bar update
		            			$('#quickPerVal').attr('aria-valuenow', result.object).css('width',result.object+'%');
		          			 	if(result.object<50) {
			          				$("#quickPerVal").removeClass('progress-bar-success');
			          				$("#quickPerVal").addClass("progress-bar-danger");
			          			}
			          			if(result.object>=50) {
			          				$("#quickPerVal").removeClass('progress-bar-danger');
			          				$("#quickPerVal").addClass("progress-bar-success");
			          			}

		            			$("#quickDisVal").empty();
		            			$("#quickDisVal").append(result.object+'%');
		            			
							 	$("#popUpDivResume").removeClass("edit-details-active");
		             			$("#selector2").removeAttr("style");
		             			$("#selector2").css({ display: "block" });
		             			if(resumeId != null) {
		             				$("#resMsgDiv").html("Resume details updated");
		             			} else {
		             				$("#resMsgDiv").html("Success!! New Resume added");
		             			}		             			
						 		candidateResumesList(candidateId);
						 		resMsgFadeinResumes();
						 		setTimeout(function() {
						 			candidateList();					 			
						 		},100);
		            		} else if (result.indexOf('Sign In') !== -1) {
		     					location.replace(sourceleadUrl);
		     				} else {
		     				    showError("Operation failed, please try again");
		     				}
							 /* $("#conMsg").fadeIn(200);
							 $('#conMsg').delay(2000).fadeOut('slow'); */
						},
					});
				return true;
			 } else {
				 showError("Resume file is missing");
			 }
		 }
		 return false;
	}
	$('body').on('blur', '#candidateResume_form', function() {
	$("#candidateResume_form").showHelpOnFocus();
	$("#candidateResume_form").validateOnBlur(false, validationSettings);
});
	 function candidateResumesList(candidateId){ 	
		  var candidateId = candidateId;
		  var candidateType = '${candidateType}';
		  var result = "";
		  var resumesCount = 0;
		  var locationList ='${SourceLeadWeb_dateFormat}';	
		  candResumeListDate = "MM-DD-YYYY";
		  if(locationList.match("yyyy-MM-dd")){
			  candResumeListDate = 'YYYY-MM-DD';
		  }if(locationList.match("dd-MM-yyyy")){
			  candResumeListDate = 'DD-MM-YYYY'; 
		  }if(locationList.match("MM-dd-yyyy")){
			  candResumeListDate = 'MM-DD-YYYY'; 
		  }if(locationList.match("yyyy-dd-MM")){
			  candResumeListDate = 'YYYY-DD-MM'; 
		  }
		  $.ajax({
				type : "POST",
				url : atsUrl+"/candidateResumesListByCandidateId?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId,
				success : function(json) {
//	 				if (json != null && json != 'undefined' && json != "" && json != '[]') {
//	 					var json = JSON.parse(data);
					if(json.data.length > 0 ){
								result = result+'	      <table>';
								result = result+'	        <tbody>';
								result = result+'	          <tr>';
								result = result+'	            <th>Title</th>';
								result = result+'	            <th>Attachment</th>';
								result = result+'	            <th>Description</th>';
								result = result+'	            <th>Created By</th>';
								result = result+'	            <th>Updated Date<br>(<small class="custom-small">'+candResumeListDate+'</small>)</th>';
								
								result = result+'	            <th>Action</th>';
								result = result+'	          </tr>';
								
								
									 for (var i = 0; i < json.data.length; i++) {
						            	 	resumesCount = json.data.length;
							            	var opt = json.data[i];
							            	console.log(opt);
							            	var title = "";
							            	var description = "";
							            	if(opt.title != null && opt.title != 'undefined' && opt.title != ""){
							            		title = opt.title;
							            	}else{
							            		title = opt.fileName;
							            	}
							            	if(opt.description != null && opt.description != 'undefined' && opt.description != ""){
							            		description = opt.description;
							            	}else{
							            		description = '----';
							            	}
							            	if(opt.createdBy != null && opt.createdBy != 'undefined' && opt.createdBy != ""){
							            		createdBy = opt.createdBy;
							            	}else{
							            		createdBy = '----';
							            	}
							            	
							            	if(opt.candidateResumeUpdateDateToString != null && opt.candidateResumeUpdateDateToString != 'undefined' && opt.candidateResumeUpdateDateToString != ""){
							            		candidateResumeUpdateDateToString = opt.candidateResumeUpdateDateToString; 
							            	}else{
							            		candidateResumeUpdateDateToString = "---";
							            	} 
							            	
							            	result = result+'<tr>';
							           		result = result+'<td><div><span title = "'+title+'">'+title+'</span></div></td>';
							           		result = result+'<td><div><span title ="'+opt.fileName+'">'+opt.fileName+'</span></div></td>';
							           		result = result+'<td><div><span title="'+description+'">'+description+'</span></div></td>';
							           		result = result+'<td><div><span>'+createdBy+'</span></div></td>';

											result = result+'<td><div><span>'+candidateResumeUpdateDateToString+'</span></div></td>';
							           		result = result+'<td><div class="tblicons_4"> <a download class="glyphicon glyphicon-download-alt"  href=\"'+opt.fileURL+'\" download title="Download"></a>';
							           		result = result+' <a class="fa fa-eye" href="javascript:void(0)" onClick="viewFile(\''+opt.fileURL+'\',\''+opt.fileName+'\',\''+opt.fileContentType+'\');"title="preview"></a>';		           						          
							           		if(window.candidateStatusGlobal == 0){
							           			result = result+'<a class="glyphicon glyphicon-pencil" href="javascript:void(0)"></a>';
								           		result = result+'<i class="glyphicon glyphicon-trash" href="javascript:void(0)"></i>';
							           		}else{
							           			if(candidateType == "Candidates"){
							           				result = result+'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_CANDIDATE_RESUMES\',\'ROLE_CROWDSOURCING_CANDIDATES\')">  <a class="glyphicon glyphicon-pencil" href="javascript:void(0)" onClick="addResume('+candidateId+'); getResume('+candidateId+', '+opt.id+');"title="Edit"></a></sec:authorize>';
							           				result = result+'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_DELETE_CANDIDATE_RESUMES\',\'ROLE_CROWDSOURCING_CANDIDATES\')"><i class="glyphicon glyphicon-trash" onclick="removeresumePopUp('+candidateId+','+opt.id+');" title="Remove"></i></sec:authorize>';
							           			}else{
							           				result = result+'<sec:authorize access="hasRole(\'ROLE_VIEW_EDIT_ASSOCIATE_RESUMES\')">  <a class="glyphicon glyphicon-pencil" href="javascript:void(0)" onClick="addResume('+candidateId+'); getResume('+candidateId+', '+opt.id+');"title="Edit"></a></sec:authorize>';
							           				result = result+'<sec:authorize access="hasRole(\'ROLE_VIEW_DELETE_ASSOCIATE_RESUMES\')"><i class="glyphicon glyphicon-trash" onclick="removeresumePopUp('+candidateId+','+opt.id+');" title="Remove"></i></sec:authorize>';
							           			}
							           		}
							           		result = result+'</div></td>';
							           		result = result+'</tr>';
						             }
								result = result+'	          </tbody>';
					       		result = result+'	      </table>';
								}else{
									result = result + "   <div class='dataTables_empty'>";
					 				result = result + "  </div> ";
								}
					            
					            
						if(resumesCount>4){
							$("#addCandidateResume").hide();
							closeResumePopup();
						}else if(resumesCount<=4){
							$("#addCandidateResume").show();
						}
						$("#resumesList").empty();
						$("#resumesList").append(result);
//	 				}else{
//	 				result = result + "   <div class='row'>";
//	 				result = result + "	<div class='col-lg-12'>";
//	 				result = result + "		<div class='content-area clearfix'>";
//	 				result = result + "			<div class='blank-pages'>";
//	 				result = result + "			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
//	 				result = result + "			        <p>No resumes Found</p>";
//	 				result = result + "			 </div>";
//	 				result = result + "		</div>";
//	 				result = result + "	</div>";
//	 				result = result + "  </div> ";
//	 				$("#resumesList").empty();
//	 				$("#resumesList").append(result);
//	 			}
		
					},
			});
	  }
	 function getCandiResume(fileURL,title){
	 	if(fileURL != null && fileURL != ""){
	 		window.open(atsUrl+'/viewDocumentsForCan/'+title+'?docFile='+encodeURI(fileURL),title,"height=600,width=600");
	 	}  
	 }
	 function getIframeDocument(element)
	 {
	 	var doc = element.contentDocument;
	         if (doc == undefined || doc == null)
	         {
	          doc = element.contentWindow.document;
	         }

	 	return doc;
	 } 
	 function fraclose(){
	 	$(".ifraMask").fadeOut(200);
	 	$(".framViePop").hide();
	 	
	 /* var win = window.open();
	 win.document.write('<iframe width="560" height="315" src="//www.youtube.com/embed/mTWfqi3-3qU" frameborder="0" allowfullscreen></iframe>') */

	 }

	 function deleteResumePopup(){
	 	$("#remove_user_pop_resume, .project_inn_mask").fadeOut();
	 }
	 function removeresumePopUp(candidateId, resumeId) {
	 	var result = "";
	 	result = result +"	<div class='project_inn_mask'></div>";
	 	result = result +"	   <div id='remove_user_pop_resume' class='remove_user_pop remove_user_pop_a'>";
	 	result = result +"		<p>You are choosing to delete the Resume</p>";
	 	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	 	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='deleteResume("+candidateId+","+resumeId+");'><input class='u_can_color' type='button'  value='Cancel' onClick='deleteResumePopup();'></b>";
	 	result = result +"	</div>";
	 	$("#deleteEduPopUp").empty();
	 	$("#deleteEduPopUp").append(result);
	 	$("#remove_user_pop_resume").slideDown(200);
	 	$(".project_inn_mask").fadeIn(200);

	 }
	 function deleteResume(candidateId, resumeId) {
	 	var loginUserId = '${SourceLeadWeb_userId}';
	 	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	 	$.post(atsUrl+"/terminateResume?"+tokenParamName+"="+tokenValue+"&loginUserId="+loginUserId+"&orgLocationId="+orgLocationId+"&candidateId="+candidateId+"&resumeId="+resumeId,{},function(result){
	 	if (result && result.response === "INVALID_DATA") {
	 	    showError(result.responseMessage);
	 	} else if (result.response === "AWKWARD") {
	 	    location.href = "/errorPage";
	 	} else if (result.response === "SUCCESS") {
	 		$('#perVal').attr('aria-valuenow', result.object).css('width',result.object+'%');
	 	 	if(result.object < 50) {
	 			$("#perVal").removeClass('progress-bar-success');
	 	      	$("#perVal").addClass("progress-bar-danger");
	 	    }
	 	    if(result.object >= 50) {
	 	    	$("#perVal").removeClass('progress-bar-danger');
	 	      	$("#perVal").addClass("progress-bar-success");
	 	    }
	 	 	$("#disVal").empty();
	 		$("#disVal").append(result.object+'%');
	 		// for quick view bar update
	 		$('#quickPerVal').attr('aria-valuenow', result.object).css('width',result.object+'%');
	 	  	if(result.object < 50) {
	 	    	$("#quickPerVal").removeClass('progress-bar-success');
	 	      	$("#quickPerVal").addClass("progress-bar-danger");
	 	    }
	 	    if(result.object >= 50) {
	 	    	$("#quickPerVal").removeClass('progress-bar-danger');
	 	      	$("#quickPerVal").addClass("progress-bar-success");
	 	    }
	 	
	 		$("#quickDisVal").empty();
	 		$("#quickDisVal").append(result.object+'%');
	 		
	 		$("#selector2").removeAttr("style");
	 	 	$("#selector2").css({ display: "block" });
	 	 	$("#resMsgDiv").html("Resume deleted");
	 		$("#popUpDivResume").removeClass("edit-details-active");
	 		candidateResumesList(candidateId);
	 		$("#remove_user_pop_resume, .project_inn_mask").fadeOut();
	 		resMsgFadeinResumes();
	 		setTimeout(function(){
	 	 		candidateList();					 			
	 	 	},100);
	 	} else if (result.indexOf('Sign In') !== -1) {
	 		location.replace(sourceleadUrl);
	 	} else {
	 	    showError("Operation failed, please try again");
	 	}
	 	 }); 
	 	  /* $.ajax({
	 		   	type: "POST",
	 		    url: sourceleadUrl+"/terminateResume?"+tokenParamName+"="+tokenValue+"&loginUserId="+loginUserId+"&orgLocationId="+orgLocationId+"&candidateId="+candidateId+"&resumeId="+resumeId, 	   
	    		    success: function(data) {
	    		    	if(data != null && data != 'undefined' && data != ""){
	    		    	 	$('#perVal').attr('aria-valuenow', data).css('width',data+'%');
	          			 if(data<50){
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
	     				
	         			 $("#selector2").removeAttr("style");
	          			 $("#selector2").css({ display: "block" });
	          			 $("#resMsgDiv").html("Resume deleted");
	         			 $("#popUpDivResume").removeClass("edit-details-active");
	         		     candidateResumesList(candidateId);
	         		     $("#remove_user_pop_resume, .project_inn_mask").fadeOut();
	         		     resMsgFadeinResumes();
	         		     setTimeout(function(){
	 				 			candidateList();					 			
	 				 	 },100);
	    		    	}
	    		    },
	 	     }); */
	 }



	    //  @author : Venkat Kesav
	  //    this script is for get resume details in edit mode
	  //    1.usage     : onclick
	 //     2.parameter : candidateId, resumeId
	 //     3.ajax url implementation in CandidateResumeRestController.
	// --%>
	 function getResume(candidateId, resumeId){
	 	
	 		$.ajax({
	 		   	type: "POST",
	 		    url: atsUrl+"/getResume?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId+"&resumeId="+resumeId, 	   
	    		    success: function(response) {
	    		    	if (response.response === "AWKWARD") {
	    		    	    location.href = "/errorPage";
	    		    	} else if (response.response === "SUCCESS") {
	    		    		 var json = response.object;
	 	       			 $("#uploadResumeDiv").hide();
	 	       			 $("#resumeAddButton").hide();
	 	       			 $("#uploadFile").removeAttr("data-validation");
	 	       			 $("#resumeNameDiv").show();
	 	       			 $("#resumeName").append(json.fileName);
	 	       			 $("#resumeUpdateButton").show();
	 	       			 
	 	       			 if(json.title != null && json.title != "undefined" && json.title != ""){
	 	       				 document.getElementById("title").value = json.title;
	 	       			 }
	 	       			 if(json.description != null && json.description != "undefined" && json.description != ""){
	 	       				 $("textarea#description").val(json.description);
	 	       			 }
	 	       			 $("#update_resume").attr("onclick", "storeCandidateResume("+json.candidateId.id+","+json.id+");");
	    		    	} else if (response.indexOf('Sign In') !== -1) {
	    		    		location.replace(sourceleadUrl);
	    		    	} else {
	    		    	    showError("Operation failed, please try again");
	    		    	}
	    		    }
	 	     });
	 }


	// <%-- 
	// @author : Venkat Kesav
	// this script is for remove Resume in edit mode to upload new resume
	// 1.usage     : onclick
	// --%>
	 function removeResumeNameDiv(){
	 	var resTitle = $("#title").val();
	 	var resDocName =  $("#resumeName").text();
	 	$("#resumeNameDiv").hide();
	 	$("#uploadResumeDiv").show();
	 	$("#uploadFile").attr("data-validation", "required validate_Space validatedoc");
	 	if(resTitle == resDocName){
	 		$("#title").val("");
	 	}
	 }
	 function uploadCandidateResume(){
	 	fileUpload("uploadBtn", "resumes", "candidateResume_form");
	 }

	 	function clearUpload(){
	 		$("#uploadFile").val("");
	 	}
	 	function getCandiResume(fileURL,title){
	 		if(fileURL != null && fileURL != ""){
	 			window.open(atsUrl+'/viewDocumentsForCan/'+title+'?docFile='+encodeURI(fileURL),title,"height=600,width=600");
	 		}  
	 	}
	 	function getIframeDocument(element)
	 	{
	 		var doc = element.contentDocument;
	 	        if (doc == undefined || doc == null)
	 	        {
	 	         doc = element.contentWindow.document;
	 	        }

	 		return doc;
	 	} 
	 	function fraclose(){
	 		$(".ifraMask").fadeOut(200);
	 		$(".framViePop").hide();
	 		
	 	/* var win = window.open();
	 	win.document.write('<iframe width="560" height="315" src="//www.youtube.com/embed/mTWfqi3-3qU" frameborder="0" allowfullscreen></iframe>') */

	 	}

	 	function deleteResumePopup(){
	 		$("#remove_user_pop_resume, .project_inn_mask").fadeOut();
	 	}
	 	function removeresumePopUp(candidateId, resumeId) {
	 		var result = "";
	 		result = result +"	<div class='project_inn_mask'></div>";
	 		result = result +"	   <div id='remove_user_pop_resume' class='remove_user_pop remove_user_pop_a'>";
	 		result = result +"		<p>You are choosing to delete the Resume</p>";
	 		result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	 		result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='deleteResume("+candidateId+","+resumeId+");'><input class='u_can_color' type='button'  value='Cancel' onClick='deleteResumePopup();'></b>";
	 		result = result +"	</div>";
	 		$("#deleteEduPopUp").empty();
	 		$("#deleteEduPopUp").append(result);
	 		$("#remove_user_pop_resume").slideDown(200);
	 		$(".project_inn_mask").fadeIn(200);

	 	}
	 	function deleteResume(candidateId, resumeId) {
	 		var loginUserId = '${SourceLeadWeb_userId}';
	 		var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	 		$.post(atsUrl+"/terminateResume?"+tokenParamName+"="+tokenValue+"&loginUserId="+loginUserId+"&orgLocationId="+orgLocationId+"&candidateId="+candidateId+"&resumeId="+resumeId,{},function(result){
	 		if (result && result.response === "INVALID_DATA") {
	 		    showError(result.responseMessage);
	 		} else if (result.response === "AWKWARD") {
	 		    location.href = "/errorPage";
	 		} else if (result.response === "SUCCESS") {
	 			$('#perVal').attr('aria-valuenow', result.object).css('width',result.object+'%');
	 		 	if(result.object < 50) {
	 				$("#perVal").removeClass('progress-bar-success');
	 		      	$("#perVal").addClass("progress-bar-danger");
	 		    }
	 		    if(result.object >= 50) {
	 		    	$("#perVal").removeClass('progress-bar-danger');
	 		      	$("#perVal").addClass("progress-bar-success");
	 		    }
	 		 	$("#disVal").empty();
	 			$("#disVal").append(result.object+'%');
	 			// for quick view bar update
	 			$('#quickPerVal').attr('aria-valuenow', result.object).css('width',result.object+'%');
	 		  	if(result.object < 50) {
	 		    	$("#quickPerVal").removeClass('progress-bar-success');
	 		      	$("#quickPerVal").addClass("progress-bar-danger");
	 		    }
	 		    if(result.object >= 50) {
	 		    	$("#quickPerVal").removeClass('progress-bar-danger');
	 		      	$("#quickPerVal").addClass("progress-bar-success");
	 		    }
	 		
	 			$("#quickDisVal").empty();
	 			$("#quickDisVal").append(result.object+'%');
	 			
	 			$("#selector2").removeAttr("style");
	 		 	$("#selector2").css({ display: "block" });
	 		 	$("#resMsgDiv").html("Resume deleted");
	 			$("#popUpDivResume").removeClass("edit-details-active");
	 			candidateResumesList(candidateId);
	 			$("#remove_user_pop_resume, .project_inn_mask").fadeOut();
	 			resMsgFadeinResumes();
	 			setTimeout(function(){
	 		 		candidateList();					 			
	 		 	},100);
	 		} else if (result.indexOf('Sign In') !== -1) {
	 			location.replace(sourceleadUrl);
	 		} else {
	 		    showError("Operation failed, please try again");
	 		}
	 		 }); 
	 		  /* $.ajax({
	 			   	type: "POST",
	 			    url: sourceleadUrl+"/terminateResume?"+tokenParamName+"="+tokenValue+"&loginUserId="+loginUserId+"&orgLocationId="+orgLocationId+"&candidateId="+candidateId+"&resumeId="+resumeId, 	   
	 	   		    success: function(data) {
	 	   		    	if(data != null && data != 'undefined' && data != ""){
	 	   		    	 	$('#perVal').attr('aria-valuenow', data).css('width',data+'%');
	 	         			 if(data<50){
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
	 	    				
	 	        			 $("#selector2").removeAttr("style");
	 	         			 $("#selector2").css({ display: "block" });
	 	         			 $("#resMsgDiv").html("Resume deleted");
	 	        			 $("#popUpDivResume").removeClass("edit-details-active");
	 	        		     candidateResumesList(candidateId);
	 	        		     $("#remove_user_pop_resume, .project_inn_mask").fadeOut();
	 	        		     resMsgFadeinResumes();
	 	        		     setTimeout(function(){
	 					 			candidateList();					 			
	 					 	 },100);
	 	   		    	}
	 	   		    },
	 		     }); */
	 	}


	 	// 
	 	//     @author : Venkat Kesav
	 	//     this script is for get resume details in edit mode
	 	//     1.usage     : onclick
	 	//     2.parameter : candidateId, resumeId
	 	///     3.ajax url implementation in CandidateResumeRestController.
	 	
	 	function getResume(candidateId, resumeId){
	 		
	 			$.ajax({
	 			   	type: "POST",
	 			    url: atsUrl+"/getResume?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId+"&resumeId="+resumeId, 	   
	 	   		    success: function(response) {
	 	   		    	if (response.response === "AWKWARD") {
	 	   		    	    location.href = "/errorPage";
	 	   		    	} else if (response.response === "SUCCESS") {
	 	   		    		 var json = response.object;
	 		       			 $("#uploadResumeDiv").hide();
	 		       			 $("#resumeAddButton").hide();
	 		       			 $("#uploadFile").removeAttr("data-validation");
	 		       			 $("#resumeNameDiv").show();
	 		       			 $("#resumeName").append(json.fileName);
	 		       			 $("#resumeUpdateButton").show();
	 		       			 
	 		       			 if(json.title != null && json.title != "undefined" && json.title != ""){
	 		       				 document.getElementById("title").value = json.title;
	 		       			 }
	 		       			 if(json.description != null && json.description != "undefined" && json.description != ""){
	 		       				 $("textarea#description").val(json.description);
	 		       			 }
	 		       			 $("#update_resume").attr("onclick", "storeCandidateResume("+json.candidateId.id+","+json.id+");");
	 	   		    	} else if (response.indexOf('Sign In') !== -1) {
	 	   		    		location.replace(sourceleadUrl);
	 	   		    	} else {
	 	   		    	    showError("Operation failed, please try again");
	 	   		    	}
	 	   		    }
	 		     });
	 	}


	// 	<%-- 
	 //	@author : Venkat Kesav
	// 	this script is for remove Resume in edit mode to upload new resume
	// 	1.usage     : onclick
	// 	--%>
	 	function removeResumeNameDiv(){
	 		var resTitle = $("#title").val();
	 		var resDocName =  $("#resumeName").text();
	 		$("#resumeNameDiv").hide();
	 		$("#uploadResumeDiv").show();
	 		$("#uploadFile").attr("data-validation", "required validate_Space validatedoc");
	 		if(resTitle == resDocName){
	 			$("#title").val("");
	 		}
	 	}

	 	function uploadCandidateResume(){
	 		fileUpload("uploadBtn", "resumes", "candidateResume_form");
	 	}
//2. candidatesResumes.jsp scripts END here >>>>>>>.
//3. candidatesExperience.jsp Starts HERE >>>>>>>>>>>>>>>>>
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
		function iscurrentPosition() {
			var currentposition = document.getElementById("isCurrentPosition").checked;
			if (currentposition) {
				document.getElementById("endDateDiv").style.display = 'none';
				$('#endYear').removeAttr('data-validation');
				$('#endMonth').removeAttr('data-validation');
				$("#endMonth").val("");
				$('#endYear').val("");
				document.getElementById("endYearValidation").style.display="none";
				endYearFlag = true;
			} else if (document.getElementById("endDateDiv").style.display == 'none') {
				document.getElementById("endDateDiv").style.display = 'block';
				/* $("#endYear").attr("data-validation", "required");
				$("#endMonth").attr("data-validation", "required"); */
			}
		}
//3. candidatesExperience.jsp ENDS  HERE >>>>>>>>>>>>>>>>>
		
//4.candidatesEducation.jsp Starts HERE >>>>>>>>>>>>>>>>>
		function candidateEducation(candidateId){
			 var candidateType = '${candidateType}';
			var result = "";
			if(window.candidateStatusGlobal == '0'){
				result = result  +'    <div class="project_tabs_inn_head addUser-org  mt25">  ';
			}else{
				result = result  +'    <div class="project_tabs_inn_head addUser-org  mt10">  ';
			}
			
			result  = result + '<div class="success_pop" id="selector04" style="display: none;">';
		    result  = result + '<i class="fa fa-check-circle"></i><span id="eduMsgDivEmp"></span>';
		    result  = result + '</div>';
			if(window.candidateStatusGlobal == '0'){
				if(candidateType == "Candidates"){
					'<sec:authorize access="hasAnyRole(\'ROLE_ADD_CANDIDATE_EDUCATION\',\'ROLE_CROWDSOURCING_CANDIDATES\')">'; 	
					result = result  +'<a href="javascript:void(0)"  class="addUser-org" ></a> ';
					'</sec:authorize>';
				}else{
					'<sec:authorize access="hasRole(\'ROLE_ADD_ASSOCIATE_EDUCATION_DETAILS\')">'; 	
					result = result  +'<a href="javascript:void(0)"  class="addUser-org" ></a> ';
					'</sec:authorize>';
				}
				
			}else{
				if(candidateType == "Candidates"){
					'<sec:authorize access="hasAnyRole(\'ROLE_ADD_CANDIDATE_EDUCATION\',\'ROLE_CROWDSOURCING_CANDIDATES\')">';
					result = result  +'<span class="pad-sea"><a href="javascript:void(0)"  onclick="addEducation('+candidateId+');" class="addUser-org" ><i class="glyphicon glyphicon-plus"></i>Add Education</a></span>';
					'</sec:authorize>';
				}else{
					'<sec:authorize access="hasRole(\'ROLE_ADD_ASSOCIATE_EDUCATION_DETAILS\')">';
					result = result  +'<span class="pad-sea"><a href="javascript:void(0)"  onclick="addEducation('+candidateId+');" class="addUser-org" ><i class="glyphicon glyphicon-plus"></i>Add Education</a></span>';
					'</sec:authorize>';
				}
				
			}
				result = result  +'     <div id="popUpDiv" class="edit-details col-md-4"> ';
				result = result  +'     </div>  ';
				result  = result + '   <div class="project_tabs_inn_table vendor_wrap_inn_table">';
				
				
				result  = result + '   <div id="candidateEducationList" class="datatable_filter"></div>';
				
				result  = result + '   </div>';
			result = result+'	    </div>';
			  

		    $("#tab-1").empty();
		    $("#tab-2").empty(); 
		    $("#tab-3").empty();  
		    $("#tab-4").empty();  
		    $("#tab-5").empty();  
		    $("#tab-6").empty();  
		    $("#tab-7").empty();
		    $("#tab-8").empty();
		    $("#tab-9").empty();
		    $("#tab-4").append(result);  
			
		    candidateEducationList(candidateId);
		    
		}
		function eduMsgFadeinFunction() {
			$("#selector04").fadeIn(200);
	        $('#selector04').delay(2000).fadeOut('slow');
		}
		function candidateEducationList(candidateId){
			var candidateType = '${candidateType}';
			 var locationList ='${SourceLeadWeb_dateFormat}';	
			  if(locationList.match("yyyy-MM-dd")){
				  canEduDatePattern = 'YYYY-MM-DD';
			  }if(locationList.match("dd-MM-yyyy")){
				  canEduDatePattern = 'DD-MM-YYYY'; 
			  }if(locationList.match("MM-dd-yyyy")){
				  canEduDatePattern = 'MM-DD-YYYY'; 
			  }if(locationList.match("yyyy-dd-MM")){
				  canEduDatePattern = 'YYYY-DD-MM'; 
			  }
			 $.ajax({  
		          type : "GET",
		          url: atsUrl+"/getCandidateEducationListByCandidateId?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId,        
		          success :function(response) {
		        	  	if (response && response.response === "INVALID_DATA") {
		        		    showError(response.responseMessage);
		        		} else if (response && response.response === "INVALID_USER") {
		        		    showError(response.responseMessage);
		        		} else if (response.response === "AWKWARD") {
		        		    location.href = "/errorPage";
		        		} else if (response.response === "SUCCESS") {
		        			if (response.data.redirect) {
				        		  window.location.href = response.data.redirect;
				        	  }
		              	 $("#candidateEducationList").empty();
		              	 var result = "";
		              	 result = result +"<table id='candidateEducationPagination' class='display' cellspacing='0' width='100%' >"; 
		                   result = result +"<thead>"; 
		                   result = result +"  <tr>"; 
		                   result  = result + '           <th>Education</th>';
		               	 result  = result + '           <th>Specialization</th>';
		               	 result  = result + '           <th>Start date <br>(<small class="custom-small">'+canEduDatePattern+'</small>)</th>';
		               	 result  = result + '           <th>End date <br>(<small class="custom-small">'+canEduDatePattern+'</small>)</th>';
		               	 result  = result + '           <th>Score</th>';
		               	 result  = result + '           <th>Score Type</th>';
		               	 result  = result + '           <th>Institute/University</th>';
		               	 result  = result + '           <th>Address</th>';
		               	 result  = result + '           <th>Action</th>';
		                   result = result +"  </tr>"; 
		                   result = result +"</thead>";
		                   result = result +"<tbody>"; 
		                   
		                   for (var i = 0; i < response.data.length; i++) { 
		                  	 var opt = response.data[i];  
		                  	 result  = result + '         <tr>';
		                  	 if(opt.name != null && opt.name != '' && opt.name != 'undefined'){
		                  	 result  = result + '<td><div><span title = "'+opt.name+'">'+opt.name+'</span></div></td>';
		                  	    }else{
		                  	    	 result  = result + ' <td><div><span>----</span></div></td>';
		                  	    }
		                  	 if(opt.specialization != null && opt.specialization != '' && opt.specialization != 'undefined'){
		                  	 result  = result + ' <td><div><span title="'+opt.specialization+'">'+opt.specialization+'</span></div></td>';
		                  	 }else{
		                  		 result  = result + ' <td><div><span>----</span></div></td>';
		                  	 }
		                  	 if(opt.startDateStr != null && opt.startDateStr != '' && opt.startDateStr != 'undefined'){
		                  	 result  = result + ' <td><div><span>'+opt.startDateStr+'</span></div></td>';
		                  	 }else{
		                  		 result  = result + ' <td><div><span>----</span></div></td>';
		                  	 }
		                  	 if(opt.endDateStr != null && opt.endDateStr != '' && opt.endDateStr != 'undefined'){ 
		                  	 result  = result + '<td><div><span>'+opt.endDateStr+'</span></div></td>';
		                  	 }else{
		                  		 result  = result + '<td><div><span>----</span></div></td>';
		                  	 }
		                  	 if(opt.grade != null && opt.grade != '' && opt.grade  != 'undefined'){
		                  	 result  = result + '<td><div><span>'+opt.grade+'</span></div></td>';
		                  	 }else{
		                  		 result  = result + '<td><div><span>----</span></div></td>'; 
		                  	 }
		                  	 if(opt.gradeType != null && opt.gradeType != '' && opt.grgradeTypeade  != 'undefined'){
				                 result  = result + '<td><div><span>'+opt.gradeType+'</span></div></td>';
				                 }else{
				                 result  = result + '<td><div><span>----</span></div></td>'; 
				                 }
		                  	 if(opt.institute != null && opt.institute != '' && opt.institute != 'undefined'){
		                  	 result  = result + '<td><div><span title="'+opt.institute+'">'+opt.institute+'</span></div></td>';
		                  	 }else{
		                  		 result  = result + '<td><div><span>----</span></div></td>';
		                  	 }
		                  	 if(opt.address != null && opt.address != '' && opt.address != 'undefined'){
		                  	 result  = result + ' <td><div><span title="'+opt.address+'">'+opt.address+'</span></div></td>';
		                  	 }else{
		                  		 result  = result + ' <td><div><span>----</span></div></td>';
		                  	 }
		                  	 if(opt.id != null && opt.id != '' && opt.id != 'undefined'){
		                  		 if(window.candidateStatusGlobal == '0'){
		                  			 result = result+' <td><div class="tblicons"><a class="glyphicon glyphicon-pencil" href="javascript:void(0)"></a>';
		                  		 }
		                  		 else{
		                  			 if(candidateType == "Candidates"){
		                  				 result = result+' <td><div class="tblicons tblminwidth-75"><sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_CANDIDATE_EDUCATION\',\'ROLE_CROWDSOURCING_CANDIDATES\')"><a class="glyphicon glyphicon-pencil" href="javascript:void(0)" onClick ="EditEducation('+ candidateId+ ','+ opt.id+ ');" title="Edit"></a></sec:authorize>';
		                  			 }else{
		                  				 result = result+' <td><div class="tblicons tblminwidth-75"><sec:authorize access="hasRole(\'ROLE_VIEW_EDIT_ASSOCIATE_EDUCATION_DETAILS\')"><a class="glyphicon glyphicon-pencil" href="javascript:void(0)" onClick ="EditEducation('+ candidateId+ ','+ opt.id+ ');" title="Edit"></a></sec:authorize>';
		                  			 }
		                  				 
		                  		 }
		                  		 if(window.candidateStatusGlobal == '0'){
										result = result+'<i class="glyphicon glyphicon-trash"></i></div></td>';
		                  		 }else{
		                  			 if(candidateType == "Candidates"){
		                  				 result = result+'<sec:authorize access="hasAnyRole(\'ROLE_DELETE_CANDIDATE_EDUCATION\',\'ROLE_CROWDSOURCING_CANDIDATES\')"> <i class="glyphicon glyphicon-trash" onclick="removeEducation('+opt.id+','+candidateId+');" title="Remove"></i></sec:authorize></div></td>';	 
		                  			 }else{
		                  				 result = result+'<sec:authorize access="hasRole(\'ROLE_VIEW_DELETE_ASSOCIATE_EDUCATION_DETAILS\')"> <i class="glyphicon glyphicon-trash" onclick="removeEducation('+opt.id+','+candidateId+');" title="Remove"></i></sec:authorize></div></td>';
		                  			 }
		                  			  
		                  		 }
		                  	}
		                  	 result  = result + '</tr>';
		                    } 
		                   result = result +" </tbody>";
		                   result = result +"</table>";
		                   $("#candidateEducationList").append(result); 
		                   educationPagination(); 
		        		} else if (response.indexOf('Sign In') !== -1) {
		        			location.replace(sourceleadUrl);
		        		} else {
		        		    showError("Operation failed, please try again");
		        		}
		        },
		     }); 
		}
	//	<%-- 
	//	     @author : sarath kumar.
	//	     this script is for prepare pagination.
	 // 1.usage     : used in candidateEducationList().

		function educationPagination(){  
		    $('#candidateEducationPagination').DataTable( { 
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
		//<%-- 
		//     @author : sarath kumar.
		//     this script is for remove popUp.
		//     1.usage   : used in addEducation() function.
		//--%>
		function closePopup(){
			 $("#popUpDiv").removeClass("edit-details-active");
			 $(".location-alert").fadeOut();
		}
		//<%-- 
		//     @author : sarath kumar.
		//     this script is for shown popUp.
		//     1.usage   : used in candidateEducation() function.
		//--%>
		function addEducation(candidateId) {	
			var result = "";
			result = result  +'<!-- Popup Div -->';
			result = result  +'          <form role="form"  id="education_form" name="education_form" method="post" > ';
			result = result  +'          <i class="fa fa-close close-div"  onClick="closePopup();"></i> ';
			result = result  +'                <div class="form-horizontal clearfix"> ';
			result = result  +'   ';
			result = result  +'                  <div class="clearfix mb10 col-md-12" id="nameIdDiv"> ';
			result = result  +'						<select label=" " class="chosen-select form-control" field-name="Education" data-placeholder = "Select Education" name="name" id="candEdu" data-validation=" required" onchange="validateEduname('+candidateId+');" onkeyup = "validateEduname('+candidateId+');">';
			
		//											 <c:forEach var="edu" items="${educationsList}">
		    result = result  +'	                        <option value="" disabled selected></option>';										 
			result = result + ' 						<option value="${edu.name}">${edu.name}</option>';
		//	                				    	 </c:forEach> 
			                				    	
			                				    	 
			                				    	 
		    result = result + '						</select>';
			result = result  +'                  </div>  ';
			result = result  +'                                                                                                                                                       ';
			result = result  +'                  <div class="clearfix mb10">                                                                                                          ';
			result = result  +'                    <div class="col-md-12">                                                                                                            ';
			result = result  +'                      <input type="text" name="specialization" field-name="specialization" data-validation="required validate_Space validate_length length1-100" data-validation-optional="true" class="form-control input-sm" value="" placeholder="Specialization">                                          ';
			result = result  +'                    </div>                                                                                                                             ';
			result = result  +'                  </div>                                                                                                                               ';
			result = result  +'                                                                                                                                                       ';
			result = result  +'                  <div class="clearfix mb10">                                                                                                          ';
			result = result  +'                    <div class="col-md-6">                                                                                            ';
			result  = result + '                   <div>';
			result = result  +'                      <input type="text" name="startDate" class="form-control input-sm" field-name="startDate" id="startDate" onclick="educationStartDate();" data-validation="required" class="form-control input-sm" data-validation-optional="true" value="" placeholder="Start Date" id="edu-startDate" readonly>                           ';
			result = result  +'                      <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span>                                           ';
			result  = result + '					<div class="clearDate" onClick="clearCandidateEduStartDate();"></div>';
			result = result  +'                    </div>                                                                                                                             ';
			result  = result + '                   </div>';
			result = result  +'                    <div class="col-md-6">                                                                                            ';
			result  = result + '                   <div>';
			result = result  +'                      <input type="text" name="endDate" class="form-control input-sm" field-name="endDate" id="endDate" onclick="educationStartDate();" data-validation="required" class="form-control input-sm" data-validation-optional="true" value="" placeholder="End Date" id="edu-endDate" readonly>                               ';
			result = result  +'                      <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span>                                           ';
			result  = result + '					<div class="clearDate" onClick="clearCandidateEduEndDate();"></div>';
			result = result  +'                    </div>                                                                                                                             ';
			result  = result + '                   </div>';
			result  = result + '                   </div>';

			result = result  +'                                                                                                                                                       ';
			result  = result + '                 <div class="clearfix mb10">';
			result = result  +'	                     <div class="col-md-6 dropdown-select positionRelative">';
			result = result  +'	                     <select class="newSelect valid"  name="gradeType" id="scoreEducationType" onchange="educationSelectScoreType();clearSearchKeyEducation();">';
			result = result  +' 					   <option value="" selected="selected">Score Type</option>';
			result = result  +'	                       <option value="Percentage">Percentage</option>';
			result = result  +'	                       <option value="CGPA">CGPA</option>';
			result = result  +'	                       <option value="GRADE">GRADE</option>';
			result = result  +'	                      </select>';
			result = result  +'	                   </div>';
			result  = result + '                   <div class="col-md-6" positionRelative paddingLeft id="candidateScoreDiv">';
			result  = result + '                     <input type="text" class="form-control input-sm" value="" name="grade" placeholder="Score" id="candidateEducationScore" onkeyup="educationSelectScoreType();educationscoreAndScoreType();"';
			result  = result + '                     data-validation-optional="true" data-validation="required validate_length length1-3" field-name="score">';
			result  = result + '                 	 </div>';
			result = result  +'	                   </div>';
			
			result = result  +'                                                                                                                                                       ';
			result = result  +'                  <div class="clearfix mb10">                                                                                                          ';
			result = result  +'                    <div class="col-md-12">                                                                                                            ';
			result = result  +'                      <input type="text" name="institute" field-name="institute" data-validation="required validate_Space validate_length length1-100" data-validation-optional="true" class="form-control input-sm" value="" placeholder="Institute">                                               ';
			result = result  +'                    </div>                                                                                                                             ';
			result = result  +'                  </div>                                                                                                                               ';
			result = result  +'                                                                                                                                                       ';
			result = result  +'                  <div class="clearfix mb10">                                                                                                          ';
			result = result  +'                    <div class="col-md-12">                                                                                                            ';
			result = result  +'                      <input type="text" field-name="Address" data-validation="required validate_Space validate_length length2-250" data-validation-optional="true" name="address" class="form-control input-sm" value="" placeholder="Address">                                                 ';
			result = result  +'                    </div>                                                                                                                             ';
			result = result  +'                  </div>                                                                                                                               ';
			result = result  +'                                                                                                                                                       ';
			result = result  +'                  <div class="clearfix mr">                                                                                                            ';
		    result = result +'<input type="button" value="Add" name="btnAddPr" id="save_button" class="btn btn-sm btn-primary pull-right" onClick ="candidateEducationStore('+candidateId+');" />'; 
			/* result = result  +'                  <input onClick ="candidateEmployersStore('+candidateId+');" type="submit" value="Add" class="btn btn-sm btn-primary pull-right"/>                                                       '; */
			result = result  +'                  </div>                                                                                                                               ';
			result = result  +'                </div>                                                                                                                                 ';
			result = result  +'                </form>                                                                                                                                ';
			$("#popUpDiv").empty();
			$("#popUpDiv").append(result);
			
			function educationStartDate(){
			}
			 var locationDate ='${SourceLeadWeb_dateFormat}'; 
			  var finalDateFormatEduSave = getJSDateFormat(locationDate);
			  
			$('#startDate').datepicker({
			      dateFormat: finalDateFormatEduSave,
			      changeMonth: true,
			  	  changeYear: true, 
			      onSelect: function (selected) {
			            //var dt = new Date(selected);
			            var dt = $(this).datepicker('getDate', '+1d');
			            dt.setDate(dt.getDate() + 1);
			            $("#endDate").datepicker("option", "minDate", dt);
			        }
			  });
			  
			  $('#endDate').datepicker({
			      dateFormat: finalDateFormatEduSave,
			      changeMonth: true,
			  	  changeYear: true, 
			        onSelect: function (selected) {
			            //var dt = new Date(selected);
			            var dt = $(this).datepicker('getDate', '-1d');
			            dt.setDate(dt.getDate() - 1);
			            $("#startDate").datepicker("option", "maxDate", dt);
			        }
			  });
			
			
			$(".edit-details").toggleClass("edit-details-active");
			$(".close-div").click(function(){ 
				$(this).parent("div.edit-details").removeClass("edit-details-active");
				$(".location-alert").fadeOut();
			});
			$(".addUser-org, .assign-org, .collapse-div-adduser, .collapse-div, .edit-details").click(function(e){
				e.stopPropagation();
			});
			 /* $('html').click(function(){
				$(".edit-details").removeClass("edit-details-active");
			   });	 */
			// <%-- Eductions and Skills Autocomplete Scripts Starts Here --%>
			var config = {
				'.chosen-select'           : {},
				'.chosen-select-deselect'  : {allow_single_deselect:true},
				'.chosen-select-no-single' : {disable_search_threshold:10},
				'.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
				'.chosen-select-width'     : {width:"95%"}
			}
			//Adding Customized Educations to the list
		     var selectone, chosenone;
		     selectone = $("#candEdu");
		     selectone.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add New Education:' });
		     chosenone = selectone.data('chosen');
		     chosenone.search_field.on('keyup', function(e1)
			    {
			    	var newEducation = this.value.replace(",","");
			        if ((e1.which == 13 || e1.which == 188) && (newEducation != null  && newEducation != '' && newEducation != 'undefined'))
			        {
			        	var par = this.parent;
			            var option = $("<option>").val(newEducation).text(newEducation);
			            selectone.append(option);
			            selectone.find(option).prop('selected', true);
			          //to doesn't allow duplicates
		             $("#candEdu option").each(function(){
		                   $(this).siblings("[value='"+ this.value+"']").remove();
		             });
		             //end
		     		$("#candEdu").trigger("chosen:updated");
		     		
			        }
			    }); 
			    //Adding Customized Educations to the list
			 
					for (var selector in config) {
					  $(selector).chosen(config[selector]);
					}
					$('.chosen-select').each(function() {
						var $this = $(this);
						$this.next().css({'width': '100%'});
						})
				//<%-- Eductions and Skills Autocomplete Scripts Ends Here --%>
			}

		function EditEducation(candidateId,candidateEducationId) {
			$.ajax({
				type : "POST",
				url : atsUrl+ "/getCandidateEducationBycandidateIdandEducationId?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId+"&candidateEducationId="+candidateEducationId,
				success : function(response) {
					if (response && response.response === "INVALID_DATA") {
					    showError(response.responseMessage);
					} else if (response.response === "AWKWARD") {
					    location.href = "/errorPage";
					} else if (response.response === "SUCCESS") {
						//var json = JSON.parse(data);
						var json = response.object;
						var specialization = "";
						var grade = "";
						var institute = "";
						var address  = "";
								
						if(json.specialization !=null && json.specialization !='undefined' && json.specialization !=''){
							specialization = json.specialization;
						}
						
						if(json.grade !=null && json.grade !='undefined' && json.grade !=''){
							grade = json.grade;
						}
						
						if(json.institute !=null && json.institute !='undefined' && json.institute !=''){
							institute = json.institute;
						}
						
						if(json.address !=null && json.address !='undefined' && json.address !=''){
							address = json.address;
						}
						
						if (json != null && json != 'undefined' && json != "") {
							var result = "";
							result = result  +'<!-- Popup Div -->';
							result = result  +'          <form role="form"  id="education_form" name="educatio(n_form" method="post" > ';
							result = result  +'          <i class="fa fa-close close-div"  onClick="closePopup();"></i> ';
							result = result  +'                <div class="form-horizontal clearfix"> ';
							result = result  +'   ';
							result = result  +'                  <div class="clearfix mb10"> ';
							result = result  +'                    <div class="col-md-12">                                                                                                            ';
							result = result + "<select  class='chosen-select form-control' name='name' id='candEdu' data-validation='validate_Space' data-validation-optional='true' onchange='validateEduname("+candidateId+");'onkeyup = 'validateEduname("+candidateId+");'>";
							
					    	$.each(json.allAndCandidateEducations, function( i, eduName) {
								var existed = 0;
					            if(json.name != null && json.name != 'undefined' && json.name != '') {
						        	var res = [];
					                res = json.name.split(",");
					                res.forEach(function(item) {
					                	if(eduName == item){
					                		existed = 1;
					                		result = result + ' <option selected value="'+eduName+'">'+eduName+'</option>';
					                	}
					                });
								}
								if(existed == 0) {
									result = result + ' <option value="'+eduName+'">'+eduName+'</option>';
					            }
							});
						    result = result + "</select>";
							result = result  +'                  </div>                                                                                                                               ';
						    result = result  +'                  </div>                                                                                                                               ';
							result = result  +'                                                                                                                                                       ';
							result = result  +'                  <div class="clearfix mb10">                                                                                                          ';
							result = result  +'                    <div class="col-md-12">                                                                                                            ';
							result = result  +'                      <input type="text" name="specialization"  field-name="specialization" data-validation="required validate_Space validate_length length2-100" data-validation-optional="true" class="form-control input-sm" value="'+specialization+'" placeholder="Specialization">                                          ';
							result = result  +'                    </div>                                                                                                                             ';
							result = result  +'                  </div>                                                                                                                               ';
							result = result  +'                                                                                                                                                       ';
							result = result  +'                  <div class="clearfix mb10">                                                                                                          ';
							result = result  +'                    <div class="col-md-6 positionRelative">                                                                                            ';
							if(json.startDateStr !=null && json.startDateStr !='undefined' && json.startDateStr !='') {
								result = result  +'	                   <div>';
								result = result  +'                      <input type="text" name="startDate" class="form-control input-sm" field-name="startDate" id="startDateEdit" onclick="educationStartDate();" data-validation="required" class="form-control input-sm" data-validation-optional="true" value="'+ json.startDateStr +'" placeholder="Start Date" id="edu-startDate" readonly>                           ';
								result = result  +'                      <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span>                                           ';
								result  = result + '					<div class="clearDate" onClick="editCandidateEducationStartDate();"></div>';
								result  = result + '                   </div>';
							} else {
								result = result  +'	                   <div>';
								result = result  +'					  <input type="text" name="startDate" class="form-control input-sm" field-name="startDate" id="startDateEdit" onclick="educationStartDate();" data-validation="required" class="form-control input-sm" data-validation-optional="true" value="" placeholder="Start Date" id="edu-startDate" readonly>                           ';
								result = result  +'                      <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span>';
								result  = result + '					<div class="clearDate" onClick="editCandidateEducationStartDate();"></div>';
								result  = result + '                   </div>';
							}
							result = result  +'                    </div>                                                                                                                             ';
							result = result  +'                    <div class="col-md-6 positionRelative">                                                                                            ';
							if(json.endDateStr !=null && json.endDateStr !='undefined' && json.endDateStr !='') {
								result = result  +'	                   <div>';					
								result = result  +'                      <input type="text" name="endDate" class="form-control input-sm" field-name="endDate" id="endDateEdit"  onclick="educationStartDate();" data-validation="required" class="form-control input-sm" data-validation-optional="true" value="'+ json.endDateStr +'" placeholder="End Date" id="edu-endDate" readonly>                               ';
								result = result  +'                      <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span>                                           ';
								result  = result + '					<div class="clearDate" onClick="editCandidateEducationEndDate();"></div>';
								result  = result + '                   </div>';
							} else {
								result = result  +'	                   <div>';									
								result = result  +'                      <input type="text" name="endDate" class="form-control input-sm" field-name="endDate" id="endDateEdit" onclick="educationStartDate();" data-validation="required" class="form-control input-sm" data-validation-optional="true" value="" placeholder="End Date" id="edu-endDate" readonly>                               ';
								result = result  +'                      <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span>                                           ';
								result  = result + '					<div class="clearDate" onClick="editCandidateEducationEndDate();"></div>';
								result  = result + '                   </div>';
							}
								result = result  +'                    </div>    ';
								result = result  +'                  </div>                                                                                                                               ';
								result = result  +'                                                                                                                                                       ';
								result  = result + '                 <div class="clearfix mb10">';
								result = result  +'	                     <div class="col-md-6 dropdown-select positionRelative paddingRight">';
								result = result  +'	                     <select class="newSelect" value="'+ json.gradeType +'" name="gradeType" id="scoreEducationType" onchange="educationSelectScoreType();clearSearchKeyEducation();">';
								result = result  +' 					   <option value="" selected="selected">Score Type</option>';
							if (json.gradeType == "Percentage") {
								result = result + '<option selected>Percentage</option>';
							} else {
								result = result	+ '<option>Percentage</option>';
							}
							if (json.gradeType == "CGPA") {
								result = result	+ '<option selected>CGPA</option>';
							} else {
								result = result	+ '<option>CGPA</option>';
							}
							if (json.gradeType == "GRADE") {
								result = result	+ '<option selected>GRADE</option>';
							} else {
								result = result + '<option>GRADE</option>';
							}
							result = result  +'	                      </select>';
							result = result  +'	                   </div>';
							result  = result + '                   <div class="col-md-6" positionRelative paddingLeft id="candidateScoreDiv">';
							result  = result + '                     <input type="text" class="form-control input-sm" value="'+grade+'" name="grade" placeholder="Score" id="candidateEducationScore" onkeyup="educationSelectScoreType();educationscoreAndScoreType();"';
							result  = result + '                     data-validation-optional="true" data-validation="required validate_length length1-3" field-name="score">';
							result  = result + '                 	 </div>';
							result = result  +'	                   </div>';
							result = result  +'                                                                                                                                                       ';
							result = result  +'                  <div class="clearfix mb10">                                                                                                          ';
							result = result  +'                    <div class="col-md-12">                                                                                                            ';
							result = result  +'                      <input type="text" name="institute" field-name="institute" data-validation="required validate_Space validate_length length2-100" data-validation-optional="true" class="form-control input-sm" value="'+institute+'" placeholder="Institute">                                               ';
							result = result  +'                    </div>                                                                                                                             ';
							result = result  +'                  </div>                                                                                                                               ';
							result = result  +'                                                                                                                                                       ';
							result = result  +'                  <div class="clearfix mb10">                                                                                                          ';
							result = result  +'                    <div class="col-md-12">                                                                                                            ';
							result = result  +'                      <input type="text" field-name="institute" data-validation="required validate_Space validate_length length2-250" data-validation-optional="true" name="address" class="form-control input-sm" value="'+address+'" placeholder="Address">                                                 ';
							result = result  +'                    </div>                                                                                                                             ';
							result = result  +'                  </div>                                                                                                                               ';
							result = result  +'     ';
							result = result  +'                  <div class="clearfix mr"> ';
							result = result	+ '	                   <input type="hidden"  value="'+candidateEducationId+'" id="candidateEducationId"  name="candidateEduId" >';
							result = result	+ '	                   <input type="hidden"  value="'+candidateId+'" id="candidateId" >';
						    result  = result + '<button type="button"  id="save_button"  onClick ="updateCandidateEducation('+candidateId+');" class="btn btn-sm btn-primary pull-right">Update</button>';
							result = result  +'                  </div>';
							result = result  +'                </div>   ';
							result = result  +'                </form>                                                                                                                                ';
							$("#popUpDiv").empty();
							$("#popUpDiv").append(result);
							function educationStartDate(){ }
							var eduStartDate = $("#startDateEdit").val();
							var eduEndDate = $("#endDateEdit").val(); 
							var locationDate ='${SourceLeadWeb_dateFormat}'; 
							var finalDateFormatEduEditOneSave = '';
							// written common method in slead-common.js for date formats start
							var eduStartDateEdit = getJSDate(eduStartDate,locationDate);
							var eduEndDateEdit = getJSDate(eduEndDate,locationDate);
							var finalDateFormatEduUpdate = getJSDateFormat(locationDate);
							//end
							$('#startDateEdit').datepicker({
							      dateFormat: finalDateFormatEduUpdate,
							      changeMonth: true,
							  	  changeYear: true, 
							  	 maxDate: eduEndDateEdit,
							      onSelect: function (selected) {
							            //var dt = new Date(selected);
							            var dt = $(this).datepicker('getDate', '+1d');
							            dt.setDate(dt.getDate() + 1);
							            $("#endDateEdit").datepicker("option", "minDate", dt);
							        }
							  });
							  $('#endDateEdit').datepicker({
							      dateFormat: finalDateFormatEduUpdate,
							      changeMonth: true,
							  	  changeYear: true, 
							  	  minDate: eduStartDateEdit,
							      onSelect: function (selected) {
							            //var dt = new Date(selected);
							            var dt = $(this).datepicker('getDate', '-1d');
							            dt.setDate(dt.getDate() - 1);
							            $("#startDateEdit").datepicker("option", "maxDate", dt);
							        }
							  });
							/* $(".addUser-org").click(function(){
								 $(".edit-details").toggleClass("edit-details-active");
							 }); */
							$(".edit-details").toggleClass("edit-details-active");
							$(".close-div").click(function(){ 
								$(this).parent("div.edit-details").removeClass("edit-details-active");
								$(".location-alert").fadeOut();
							});
							$(".addUser-org, .assign-org, .collapse-div-adduser, .collapse-div, .edit-details").click(function(e){
								e.stopPropagation();
							});
							 /* $('html').click(function(){
								$(".edit-details").removeClass("edit-details-active");
							   });	 */
							// <%-- Eductions and Skills Autocomplete Scripts Starts Here --%>
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
							});
							//<%-- Eductions and Skills Autocomplete Scripts Ends Here --%>
						}
					} else if (response.indexOf('Sign In') !== -1) {
						location.replace(sourceleadUrl);
					} else {
					    showError("Operation failed, please try again");
					}
				},
			});
		}
	//	<%-- 
	//	     @author : sarath kumar.
	//	     this script is for store candidateEducationstore .
	//	     1.usage   : used in candidateEducation() function.
		//--%>
		var validationSettings = {
				errorMessagePosition : 'element',
				borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd'
			};
		function candidateEducationStore(candidateId) {
			var scoreType = document.getElementById("scoreEducationType").value; 
			 if(scoreType != null && scoreType != ""){
				 $('#candidateEducationScore').removeAttr('data-validation-optional');
				 $('#candidateEducationScore').attr("data-validation","required"); 
			 } else {
				 $('#candidateEducationScore').removeAttr('data-validation');
				 $('#candidateEducationScore').attr("data-validation-optional","true"); 
			 }
			  
			  if ($('#education_form').validate(false, validationSettings)) {
			     var formData = getFormData("education_form");
				 $("#education_form").attr("disabled", "disabled");
				 $("#save_button").attr("disabled", "disabled");
				
				 $.post(atsUrl+"/storeCandidateEducation?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId, formData, function(result) {
					 if (result && result.response === "INVALID_DATA") {
						    $("#update_candidate").removeAttr("disabled");
						    $("#update_candidate").removeAttr('button').val('Save');
						    showError(result.responseMessage);
						} else if (result.response === "AWKWARD") {
						    location.href = "/errorPage";
						} else if (result.response === "SUCCESS") {
							closePopup();
							$('#perVal').attr('aria-valuenow', result.object).css('width',result.object+'%');
			    			if(result.object < 50){
			    				$("#perVal").removeClass('progress-bar-success');
			    				$("#perVal").addClass("progress-bar-danger");
			    			}
			    			if(result.object >= 50){
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
			    			
			       		    $("#popUpDiv").removeClass("edit-details-active");
			    			$("#selector04").removeAttr("style");
			    			$("#selector04").css({ display: "block" });
							$("#eduMsgDivEmp").html("Success!! New Education added");
			       			candidateEducationList(candidateId);
			       			eduMsgFadeinFunction();
			       			qveducationSubmission(candidateId);
			       			setTimeout(function(){
					 			candidateList();					 			
					 		},100);
				 			return true;
						} else if (result.indexOf('Sign In') !== -1) {
							location.replace(sourceleadUrl);
						} else {
						    showError("Operation failed, please try again");
						}
					 return false;
				 });
			}
		}
		$('body').on('blur', '#education_form', function() {
			$("#education_form").showHelpOnFocus();
			$("#education_form").validateOnBlur(false, validationSettings);
		});
		function deleteEduPopup(){
			$("#remove_user_pop, .project_inn_mask").fadeOut();
		}
		function removeEducation(candidateEducationId, candidateId){
			var result = "";
			result = result +"	<div class='project_inn_mask'></div>";
			result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
			result = result +"		<p>You are choosing to delete the Education</p>";
			result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
			result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='deleteSelectedEducation("+candidateEducationId+","+candidateId+");'><input class='u_can_color' type='button'  value='Cancel' onClick='deleteEduPopup();'></b>";
			result = result +"	</div>";
			$("#deleteEduPopUp").empty();
			$("#deleteEduPopUp").append(result);
			$("#remove_user_pop").slideDown(200);
			$(".project_inn_mask").fadeIn(200);
		}
	//	<%-- 
		//     @author : david.
		//     this script is for remove candidateEducation.
		//--%>
		function deleteSelectedEducation(candidateEducationId,candidateId){
		 var loginUserId = '${SourceLeadWeb_userId}';
		 var orgLocationId = '${SourceLeadWeb_orgLocationId}';
			 $.ajax({  
		          type : "POST",
		          url: atsUrl+"/removeCandidateEducationBycandidateIdandcandidateEducationId?"+tokenParamName+"="+tokenValue+"&loginUserId="+loginUserId+"&orgLocationId="+orgLocationId+"&candidateId="+candidateId+"&candidateEducationId="+candidateEducationId,        
		          success :function(result) {
		        	  if (result && result.response === "INVALID_DATA") {
		        		    showError(result.responseMessage);
		        		} else if (result.response === "AWKWARD") {
		        		    location.href = "/errorPage";
		        		} else if (result.response === "SUCCESS") {
		        			$('#perVal').attr('aria-valuenow', result.object).css('width',result.object+'%');
		          			if(result.object < 50){
		          			/* $("#perVal").removeClass("progress-bar-success"); */
		          				$("#perVal").removeClass('progress-bar-success')
		          				$("#perVal").addClass("progress-bar-danger");
		          			}
		          			if(result.object >= 50){
		          				$("#perVal").removeClass('progress-bar-danger');
		          				$("#perVal").addClass("progress-bar-success");
		          			}
		          			$("#disVal").empty();
		          			$("#disVal").append(result.object +'%');
		          			
		          			$('#quickPerVal').attr('aria-valuenow', result.object).css('width',result.object +'%');
		        			if(result.object < 50){
			          			$("#quickPerVal").removeClass('progress-bar-success');
			          			$("#quickPerVal").addClass("progress-bar-danger");
			          		}
			          		if(result.object >= 50){
			          			$("#quickPerVal").removeClass('progress-bar-danger');
			          			$("#quickPerVal").addClass("progress-bar-success");
			          		}

		      				$("#quickDisVal").empty();
		      				$("#quickDisVal").append(result.object +'%');
		          			
		          			$("#selector04").removeAttr("style");
		           			$("#selector04").css({ display: "block" });
		           			$("#eduMsgDivEmp").html("Education deleted");
		            		$("#popUpDiv").removeClass("edit-details-active");
		            		$("#remove_user_pop, .project_inn_mask").fadeOut();
		            		candidateEducationList(candidateId);
		            		qveducationSubmission(candidateId);
		            		eduMsgFadeinFunction();
		            		setTimeout(function() {
						 		candidateList();					 			
						 	},100);
		        		} else if (result.indexOf('Sign In') !== -1) {
		        			location.replace(sourceleadUrl);
		        		} else {
		        		    showError("Operation failed, please try again");
		        		}
		        },
		     }); 
		}
		function educationSelectScoreType(){
			var str = document.getElementById("candidateEducationScore").value;
			if (str == 'a' || str == 'b' || str == 'c' || str == 'd' || str == 'e') {
				var strToUpper = str.toUpperCase();
				$('#candidateEducationScore').val(strToUpper);
			}
			 var selectedText = document.getElementById("scoreEducationType").value;
			 var canScore = document.getElementById("candidateEducationScore").value;
			 $('#candidateScoreDiv').parent().find('p.jquery_form_error_message').remove(); 
			 if(selectedText =='Percentage'){
				  var floatNum = /^(\d{0,2}(\.\d{0,2})?|100)$/
				  if(canScore.match(floatNum)){
					  $("#save_button").removeAttr("disabled");
					   return true;
				  }		  
				 else{
						$("input#candidateEducationScore").parent().after("<p class='jquery_form_error_message'><span>Enter your Valid Percentage</span>");
						 $("#save_button").attr("disabled", "disabled");
						return false; 
				 	 }
			  }
			 else if(selectedText =='CGPA'){
				  var cgpanum = /^(([\d][\.])?([\d])|10)?$/
					  if(canScore.match(cgpanum)){
						  $("#save_button").removeAttr("disabled");
						   return true;
					  }		  
					 else{
						 $("input#candidateEducationScore").parent().after("<p class='jquery_form_error_message'><span>Enter your Valid CGPA value</span>");
						 $("#save_button").attr("disabled", "disabled");
					return false; 
					 }
			  } 
			 else if(selectedText =='GRADE'){
				 var grades = /^([A-E][-+]{0,2})?$/
					 if (canScore.match(grades)) {
						 $("#save_button").removeAttr("disabled");
						 return true;
					}else{
						$("input#candidateEducationScore").parent().after("<p class='jquery_form_error_message'> Enter your Valid Grade </p>");
						 $("#save_button").attr("disabled", "disabled");
			    		return false;
			    		
					}
		   		}
				 }
			  
		function educationscoreAndScoreType(){
			 var candidateScore = document.getElementById("candidateEducationScore").value;
			 var selectedText = document.getElementById("scoreEducationType").value;
			 var floatNum = /^(\d{0,2}(\.\d{0,2})?|100)$/
			 var cgpanum = /^(([\d][\.])?([\d])|10)?$/
			 var grades = /^([A-E][-+]{0,2})?$/
			 if(candidateScore.match(floatNum)){
				 $("#scoreEducationType").val('Percentage'); 
				 $('#candidateScoreDiv').parent().find('p.jquery_form_error_message').remove();
				 $("#save_button").removeAttr("disabled");
			 }
			 if(candidateScore.match(grades)){
				 $('#candidateScoreDiv').parent().find('p.jquery_form_error_message').remove();
				 $("#scoreEducationType").val('GRADE'); 
				 $("#save_button").removeAttr("disabled");
			 }
			 if(candidateScore.match(cgpanum)){
				 $('#candidateScoreDiv').parent().find('p.jquery_form_error_message').remove();
				 $("#scoreEducationType").val('CGPA'); 
				 $("#save_button").removeAttr("disabled");
			 }
			 if((selectedText == '') && (!candidateScore.match(floatNum)) && (!candidateScore.match( cgpanum)) && (!candidateScore.match(grades))){
				 $("input#candidateEducationScore").parent().after("<p class='jquery_form_error_message'> Enter your Valid Score </p>");
			 }
			 if(selectedText == '' && candidateScore == ''){
				 $('#candidateScoreDiv').parent().find('p.jquery_form_error_message').remove(); 
			 }
			 if(candidateScore == ''){
				 $("#scoreEducationType").val('');
			 }
		}
		//     @author : shivayogi , Ajith.
		//     this script is to flush candidate value when score type is changed.
		function clearSearchKeyEducation() {
			   $('#candidateEducationScore').val('');
			   $('#candidateScoreDiv').parent().find('p.jquery_form_error_message').remove();

		}
			function updateCandidateEducation(candidateId, candidateEducationId) {
				
				var scoreType = document.getElementById("scoreEducationType").value; 
				 if(scoreType != null && scoreType != ""){
					 $('#candidateEducationScore').removeAttr('data-validation-optional');
					 $('#candidateEducationScore').attr("data-validation","required"); 
				 }else{
					 $('#candidateEducationScore').removeAttr('data-validation');
					 $('#candidateEducationScore').attr("data-validation-optional","true"); 
				 }
				if ($('#education_form').validate(false, validationSettings)) {
					if($("#candEdu").length > 0 && $("#candEdu_chosen").length > 0){
						var formData = getFormData('education_form');
						 $("#education_form").attr("disabled", "disabled");
						 $("#save_button").attr("disabled", "disabled");
						 $.post(atsUrl+"/upDateCandidateEducationByCandidateIdAndCandidateEducationId?"+tokenParamName+"="+tokenValue+"&candidateId=" + candidateId,formData,function(result){
							 	if (result && result.response === "INVALID_DATA") {
								    $("#update_candidate").removeAttr("disabled");
								    $("#update_candidate").removeAttr('button').val('Save');
								    showError(result.responseMessage);
								} else if (result.response === "AWKWARD") {
								    location.href = "/errorPage";
								} else if (result.response === "SUCCESS") {
									closePopup();
					     			$("#selector04").removeAttr("style");
					     			$("#selector04").css({ display: "block" });
					     			$("#eduMsgDivEmp").html("Education details updated");
									candidateEducationList(candidateId)
									eduMsgFadeinFunction();
									qveducationSubmission(candidateId);
									setTimeout(function(){
							 			candidateList();					 			
							 		},100);
								} else if (result.indexOf('Sign In') !== -1) {
									location.replace(sourceleadUrl);
								} else {
								    $("#update_candidate").removeAttr("disabled");
								    showError("Operation failed, please try again");
								}
						 });
						return true;
					} else {
						showError("Education is missing");
					}
				} else {
					return false;
				}
				$('body').on('blur','#education_form',
						function() {
							$("#education_form").showHelpOnFocus();
							$("#education_form").validateOnBlur(false,
									validationSettings);
						});
			}
		 function validateEduname(candidateId) {
			var name = document.getElementById("candEdu").value; 
		 	$('#nameIdDiv').find('p.jquery_form_error_message').remove();
			$.ajax({
				type: "POST",
			    url: atsUrl+"/validateEduname?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId+"&name="+name, 
			    success: function(response) {
		        	if (response && response.response === "INVALID_DATA") {
		        	    showError(response.responseMessage);
		        	} else if (response.response === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (response.response === "SUCCESS") {
		        		if(response.object == true) {
			        		  $('#nameIdDiv').find('p.jquery_form_error_message').remove();
			        		  /* document.getElementById("name").setAttribute('record-exist','yes');
							  document.getElementById("name").setAttribute('record-exist-errorMsg',name+' is Already Existed'); 
							   */
							   $("#candEdu").after("<p class='jquery_form_error_message'>"+name+" is Already Existed</span>");
							  $("#save_button").attr('disabled','disabled');
							 // return false;
			        	} else {
			        		 $('#nameIdDiv').removeAttr( "record-exist record-exist-errormsg");
			        		 $('#nameIdDiv').find('p.jquery_form_error_message').remove(); 
		                        $("#save_button").removeAttr("disabled");
			        	}
		        	} else if (response.indexOf('Sign In') !== -1) {
		        		location.replace(sourceleadUrl);
		        	} else {
		        	    showError("Operation failed, please try again");
		        	}
				},
			});
		 }
	//	<!-- script for converting dateformat in datepicker -->
		function toDate(selector) {
			var from = $(selector).val().split("-");
		    return new Date(from[2], from[1] - 1, from[0]);
		}
	//	<!-- script for dateformat in datepicker End -->
		function clearCandidateEduStartDate() {
			$("#startDate").val("");
		}
		function clearCandidateEduEndDate() {
			$("#endDate").val("");
		}
		function editCandidateEducationStartDate() {
			$("#startDateEdit").val("");
		}
		function editCandidateEducationEndDate() {
			$("#endDateEdit").val("");
		}
//4.candidatesEducation.jsp ENDS HERE >>>>>>>>>>>>>>>>>
//5. candidatesCertificates.jsp Starts HERE
function closeCertificatePopup(){
	 $("#popUpDivEmp").removeClass("edit-details-active");
	 $(".location-alert").fadeOut();
}
function candidateCertificate(candidateId){
	 var candidateType = '${candidateType}';
	var result = "";
	if(window.candidateStatusGlobal == '0'){
		result  = result + '   <div class="project_tabs_inn_head addUser-org mt25"> ';
	}else{
		result  = result + '   <div class="project_tabs_inn_head addUser-org mt10"> ';
	}
	result  = result + '<div class="success_pop" id="selector5" style="display: none;">';
   result  = result + '<i class="fa fa-check-circle"></i><span id="cerMsgDiv"></span>';
   result  = result + '</div>';
   
	result = result +'<div id="msgDiv"></div>';
	if(window.candidateStatusGlobal == '0'){
		if(candidateType == "Candidates"){
			 result = result +'<sec:authorize access="hasAnyRole(\'ROLE_ADD_CANDIDATE_CERTIFICATION\',\'ROLE_CROWDSOURCING_CANDIDATES\')"> ';
			 result  = result + '   	<a href="javascript:void(0)" class="addUser-org"></a> ';
			 result = result +' </sec:authorize>';
		}else{
			 result = result +'<sec:authorize access="hasAnyRole(\'ROLE_ADD_ASSOCIATE_CERTIFICATION_DETAILS\')"> ';
			 result  = result + '   	<a href="javascript:void(0)" class="addUser-org"></a> ';
			 result = result +' </sec:authorize>';
		}
		
	}
	else{
		if(candidateType == "Candidates"){
			 result = result +'<sec:authorize access="hasAnyRole(\'ROLE_ADD_CANDIDATE_CERTIFICATION\',\'ROLE_CROWDSOURCING_CANDIDATES\')"> ';
			 result  = result + '   	<span class="pad-sea"> <a href="javascript:void(0)" id="newCandidate" onClick="addCertification('+candidateId+');flushCandiadate();" class="addUser-org"><i class="glyphicon glyphicon-plus"></i>Add Certification</a></span> ';
			 result = result +' </sec:authorize>';
		}else{
			 result = result +'<sec:authorize access="hasAnyRole(\'ROLE_ADD_ASSOCIATE_CERTIFICATION_DETAILS\')"> ';
			 result  = result + '   	<span class="pad-sea"> <a href="javascript:void(0)" id="newCandidate" onClick="addCertification('+candidateId+');flushCandiadate();" class="addUser-org"><i class="glyphicon glyphicon-plus"></i>Add Certification</a></span> ';
			 result = result +' </sec:authorize>';
		}
	  	
	}
	result = result  +'     <div id="popUpDivEmp" class="edit-details col-md-4" > ';
	result = result  +'     </div>  ';
	result  = result + '   <div class="project_tabs_inn_table vendor_wrap_inn_table">';
	result  = result + '   <div id="candidateCertificateList" class="datatable_filter"></div>';
	result  = result + '   </div>';
	result = result+'	    </div>';

   $("#tab-1").empty();
   $("#tab-2").empty(); 
   $("#tab-3").empty();  
   $("#tab-4").empty();  
   $("#tab-5").empty();  
   $("#tab-6").empty();  
   $("#tab-5").append(result);
   
   candidateCertificateList(candidateId);
   
}
function storeCertificate(candidateId) {
	var scoreType = document.getElementById("scoreType").value; 
	if(scoreType != null && scoreType != "") {
 		$('#candidateScore').removeAttr('data-validation-optional');
		$('#candidateScore').attr("data-validation","required"); 
	} else {
 		$('#candidateScore').removeAttr('data-validation');
 		$('#candidateScore').attr("data-validation-optional","true"); 
	}
	if ($('#Candidate_certification_form').validate(false,validationSettings)) {
		var formData = getFormData("Candidate_certification_form");
		$("#Candidate_certification_form").attr("disabled", "disabled");
		$("#addCandidate").attr("disabled", "disabled");
		$.post(atsUrl+"/storeCandidateCertifications?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId, formData, function(result) {
			if (result && result.response === "INVALID_DATA") {
    		    showError(result.responseMessage);
    		} else if (result.response === "AWKWARD") {
    		    location.href = "/errorPage";
    		} else if (result.response === "SUCCESS") {
    			closeCertificatePopup();
    		    $("#selector5").removeAttr("style");
    	     	$("#selector5").css({ display: "block" });
    	     	$("#cerMsgDiv").html("Success!! New Certification added");
    		    candidateCertificateList(candidateId);
                cerMsgFadein();
                qwikCertificates(candidateId);
                setTimeout(function(){
    				candidateList();					 			
    			},100);
    		} else if (result.indexOf('Sign In') !== -1) {
    			location.replace(sourceleadUrl);
    		} else {
    		    showError("Operation failed, please try again");
    		}
		});
		return true;
		} else {
			return false;
		}
	}
	$('body').on('blur','#Candidate_certification_form',
		function() {
			$("#Candidate_certification_form").showHelpOnFocus();
			$("#Candidate_certification_form").validateOnBlur(false,validationSettings);
		});
	function candidateCertificateList(candidateId){
		var candidateType = '${candidateType}';
		var locationList ='${SourceLeadWeb_dateFormat}';	
		  if(locationList.match("yyyy-MM-dd")){
			  canCerDatePattern = 'YYYY-MM-DD';
		  }if(locationList.match("dd-MM-yyyy")){
			  canCerDatePattern = 'DD-MM-YYYY'; 
		  }if(locationList.match("MM-dd-yyyy")){
			  canCerDatePattern = 'MM-DD-YYYY'; 
		  }if(locationList.match("yyyy-dd-MM")){
			  canCerDatePattern = 'YYYY-DD-MM'; 
		  }
		 $.ajax({  
	          type : "POST",
	          url: atsUrl+"/getCandidateCertificationListByCandidateIdAndOrgLocationID?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId,        
	          success :function(response) {
	        	  	if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
	        		    showError(response.responseMessage);
	        	  	} else if (response.response === "AWKWARD") {
	        		    location.href = "/errorPage";
	        		} else if (response.response === "SUCCESS") {
	        			$("#candidateCertificateList").empty();
	               	 var result = "";
	               	 result = result +"<table id='candidateCertificatePagination' class='display' cellspacing='0' width='100%' >"; 
	                    result = result +"<thead>"; 
	                    result = result +"  <tr>"; 
	                    result  = result +"           <th>Name</th>";
	                	result  = result + "           <th>Provider</th>";
	                	result  = result + "           <th>Score</th>";
	                	result  = result + "           <th>Score Type</th>";
	                	result  = result + "           <th>Description</th>";
	                	result  = result + "           <th>Valid from <br>(<small class='custom-small'>"+canCerDatePattern+"</small>)</th>";
	                	result  = result + "           <th>Valid to <br>(<small class='custom-small'>"+canCerDatePattern+"</small>)</th>";
	                	result  = result + "           <th>Action</th>";
	                    result = result +"  </tr>"; 
	                    result = result +"</thead>";
	                    result = result +"<tbody>"; 
	                    for (var i = 0; i < response.data.length; i++) { 
	                   	 var opt = response.data[i];  
	                   	 var score = "";
	                   	 var scoreType ="";
	                   	 var description ="";
	                   	 var candidatevalidFromDateToString ="";
	                   	 var candidatevalidToDateToString ="";
				            	if(opt.score != null && opt.score != 'undefined' && opt.score != ""){
				            		score = opt.score;
				            	}else{
				            		score = "----";
				            	}
				            	if(opt.scoreType != null && opt.scoreType != 'undefined' && opt.scoreType != ""){
				            		scoreType = opt.scoreType;
				            	}else{
				            		scoreType = "----";
				            	}
				            	if(opt.description != null && opt.description != 'undefined' && opt.description != ""){
				            		description = opt.description;
				            	}else{
				            		description = "----";
				            	}
				            	if(opt.candidatevalidFromDateToString != null && opt.candidatevalidFromDateToString != 'undefined' && opt.candidatevalidFromDateToString != ""){
				            		candidatevalidFromDateToString = opt.candidatevalidFromDateToString;
				            	}else{
				            		candidatevalidFromDateToString = "----";
				            	}
				            	if(opt.candidatevalidToDateToString != null && opt.candidatevalidToDateToString != 'undefined' && opt.candidatevalidToDateToString != ""){
				            		candidatevalidToDateToString = opt.candidatevalidToDateToString;
				            	}else{
				            		candidatevalidToDateToString = "----";
				            	}
	                   	 result  = result + '         <tr>';
	                   	 result  = result + '           <td><div><span title = "'+opt.name+'">'+opt.name+'</span></div></td>';
	                   	 result  = result + '           <td><div><span title = "'+opt.provider+'">'+opt.provider+'</span></div></td>';
	                   	 result  = result + '           <td><div><span>'+score+'</span></div></td>';
	                   	 result  = result + '           <td><div><span>'+scoreType+'</span></div></td>';
	                   	 result  = result + '           <td><div><span title = "'+description+'">'+description+'</span></div></td>';
	                   	 result  = result + '           <td><div><span>'+candidatevalidFromDateToString+'</span></div></td>';
	                   	 result  = result + '           <td><div><span>'+candidatevalidToDateToString+'</span></div></td>';
	                   	 
	                   	 result  = result + '           <td><div class="tblicons tblminwidth-75">';
	                   	 
	                   	 if(window.candidateStatusGlobal == '0'){
	                   		 if(candidateType == "Candidates"){
	                   			 result = result +'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_CANDIDATE_CERTIFICATION\',\'ROLE_CROWDSOURCING_CANDIDATES\')"> ';
		                    		 result = result  +'	            <a href="javascript:void(0)" disabled="disabled" class="glyphicon glyphicon-pencil" id="editCandidateCertificate"></a>';
		                    		 result = result +' </sec:authorize>';
	                   		 }else{
	                   			 result = result +'<sec:authorize access="hasRole(\'ROLE_VIEW_EDIT_ASSOCIATE_CERTIFICATION_DETAILS\')"> ';
		                    		 result = result  +'	            <a href="javascript:void(0)" disabled="disabled" class="glyphicon glyphicon-pencil" id="editCandidateCertificate"></a>';
		                    		 result = result +' </sec:authorize>';
	                   		 }
	                   		 
	                   	 }else{
	                   		 if(candidateType == "Candidates"){
	                   			 result = result +'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_CANDIDATE_CERTIFICATION\',\'ROLE_CROWDSOURCING_CANDIDATES\')"> ';
		                    		result = result  +'	            <a href="javascript:void(0)" class="glyphicon glyphicon-pencil" id="editCandidateCertificate" href="javascript:void(0)" onClick="editCertification('+ opt.id+ ','+ candidateId+ ');" title="Edit"></a>';
		                    		result = result +' </sec:authorize>';
	                   		 }else{
	                   			 result = result +'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_ASSOCIATE_CERTIFICATION_DETAILS\')"> ';
		                    		result = result  +'	            <a href="javascript:void(0)" class="glyphicon glyphicon-pencil" id="editCandidateCertificate" href="javascript:void(0)" onClick="editCertification('+ opt.id+ ','+ candidateId+ ');" title="Edit"></a>';
		                    		result = result +' </sec:authorize>';
	                   		 }
	                   		
	                   	 }
	                   	 
	                   	 if(window.candidateStatusGlobal == '0'){
	                   		 if(candidateType == "Candidates"){
	                   			 result = result +'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_DELETE_CANDIDATE_CERTIFICATION\',\'ROLE_CROWDSOURCING_CANDIDATES\')"> ';
		                    		 result = result  +'<i class="glyphicon glyphicon-trash"></i>';
		                    		 result = result +' </sec:authorize>';
	                   		 }else{
	                   			 result = result +'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_DELETE_ASSOCIATE_CERTIFICATION_DETAILS\')"> ';
		                    		 result = result  +'<i class="glyphicon glyphicon-trash"></i>';
		                    		 result = result +' </sec:authorize>';
	                   		 }
	                   		 
	                   	}else{
	                   		if(candidateType == "Candidates"){
	                   			result = result +'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_DELETE_CANDIDATE_CERTIFICATION\',\'ROLE_VIEW_EDIT_CANDIDATE_CERTIFICATION\',\'ROLE_CROWDSOURCING_CANDIDATES\')"> ';
		                    		 result = result  +'<i class="glyphicon glyphicon-trash" onclick="removeCertification('+opt.id+','+candidateId+');" id="removeCandidate" title= "Remove"></i>';
		                    		 result = result +' </sec:authorize>';
	                   		}else{
	                   			result = result +'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_DELETE_ASSOCIATE_CERTIFICATION_DETAILS\')"> ';
		                    		 result = result  +'<i class="glyphicon glyphicon-trash" onclick="removeCertification('+opt.id+','+candidateId+');" id="removeCandidate" title= "Remove"></i>';
		                    		 result = result +' </sec:authorize>';
	                   		}
	                   		
	                   	}
	                   	 result  = result + '           </div></td>';
	                   	 result  = result + '         </tr>';
	                     } 
	                    result = result +" </tbody>";
	                    result = result +"</table>";
	                    $("#candidateCertificateList").append(result); 
	                    certificationPagination(); 
	                    msgFadein();
	        		} else if (response.indexOf('Sign In') !== -1) {
	        			location.replace(sourceleadUrl);
	        		} else {
	        		    showError("Operation failed, please try again");
	        		}
	        },
	     }); 
	}
	function certificationPagination(){  
	    $('#candidateCertificatePagination').DataTable( { 
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

/*	<%-- 
	     @author : Ajith Matta,Shiva yogi.
	     this script is for add the  Candidate details Details .
	     1.usage     :  used in candidateCertificate  function 
	     2.parameter : candidateId .
	--%>*/
	function addCertification(candidateId) {	
		var candidateType = '${candidateType}';
		 	var result = '';
		 	result  = result + '     <!-- Popup Div -->                                                                                         ';
			result  = result + '         <form id="Candidate_certification_form" name="Candidate_certification_form" method="post">        ';
			result = result+'	           <i class="fa fa-close close-div" onClick="closeCertificatePopup();"></i>';
			result  = result + '               <div class="form-horizontal clearfix">';
			result  = result + '                 <div class="clearfix mb10">';
			result  = result + '                   <div class="col-md-12" id="candidatenameDiv">';
			result  = result + '                     <input type="text" class="form-control input-sm" value="" name="name"  placeholder="Name *" id="candidateName" ';
			result  = result + '                      pattern="[A-Za-z\s]*" data-validation="required validate_Space validate_length length1-100" field-name="Name" >';
			result  = result + '                   </div>';
			result  = result + '                 </div>';
			result  = result + '                 <div class="clearfix mb10">';
			result  = result + '                   <div class="col-md-12">';
			result  = result + '                     <input type="text" class="form-control input-sm" value="" name="provider" placeholder="Provider *" id="candidateProvider"';
			result  = result + '                      data-validation="required validate_Space validate_length length1-100" field-name="Provider">';
			result  = result + '                   </div>';
			result  = result + '                 </div>';
			
			result  = result + '                 <div class="clearfix mb10" id="candidateScoreDiv">';
			result = result  +'	                     <div class="col-md-5 dropdown-select positionRelative padding_right">';
			result = result  +'	                     <select class="newSelect"  name="scoreType" id="scoreType" onchange="selectScoreType();clearSearchKey();">';
			result = result  +' 					   <option value="" selected="selected">Score Type</option>';
			result = result  +'	                       <option value="Percentage">Percentage</option>';
			result = result  +'	                       <option value="CGPA">CGPA</option>';
			result = result  +'	                       <option value="GRADE">GRADE</option>';
			result = result  +'	                      </select>';
			result = result  +'	                   </div>';
			result  = result + '                   <div class="col-md-7 PaddingLeft" positionRelative >';
			result  = result + '                     <input type="text" class="form-control input-sm" value="" name="score" placeholder="Score" id="candidateScore" onkeyup="selectScoreType();scoreAndScoreType();"';
			result  = result + '                     data-validation-optional="true" data-validation="required validate_length length1-5" field-name="score">';
			result  = result + '                 	 </div>';
			result = result  +'	                   </div>';
			
			result  = result + '                 <div class="clearfix mb10">';
			result  = result + '                   <div class="col-md-12">';
			result  = result + '                     <textarea class="form-control" rows="1" name ="description"  placeholder="Description" id="candidateDescription"';
			result  = result + '                     data-validation-optional="true" data-validation="required validate_length length2-250"></textarea>';
			result  = result + '                   </div>';
			result  = result + '                 </div>';
			
			
			result  = result + '                 <div class="clearfix mb10">';
			result  = result + '                   <div class="col-md-6">';
			result  = result + '                   <div>';
			result  = result + '                     <input type="text" class="form-control input-sm"  value="" name ="validFrom" placeholder="Valid From" ';
			result  = result + '                      data-validation-optional="true" data-validation="required" field-name="Valid From" id="StartDate" readonly>';
			result  = result + '                     <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span>';
			result  = result + '					<div class="clearDate" onClick="clearCandidateCertStartDate();"></div>';
			result  = result + '                   </div>';
			result  = result + '                   </div>';
			result  = result + '                   <div class="col-md-6">';
			result  = result + '                   <div>';
			result  = result + '                     <input type="text" class="form-control input-sm" value="" name="validTo" placeholder="Valid To" ';
			result  = result + '                     data-validation-optional="true" data-validation="required" field-name="Valid To" id="EndDate" readonly>';
			result  = result + '                     <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span>';
			result  = result + '					<div class="clearDate" onClick="clearCandidateCertEndDate();"></div>';
			result  = result + '                   </div>';
			result  = result + '                   <div>';
			result  = result + '                 </div>';
			result  = result + '               </div>';
			result  = result + '               </div>';
			result  = result + '                 <div class="clearfix mr">';
			if(candidateType == "Candidates"){
				result = result +'<sec:authorize access="hasAnyRole(\'ROLE_ADD_CANDIDATE_CERTIFICATION\',\'ROLE_CROWDSOURCING_CANDIDATES\')"> ';
				result  = result + '                   <button type="button" class="btn btn-sm btn-primary pull-right" id="addCandidate"  onclick="storeCertificate('+candidateId+');"  >Add</button>';
				result = result +' </sec:authorize>';
			}else{
				result = result +'<sec:authorize access="hasRole(\'ROLE_ADD_ASSOCIATE_CERTIFICATION_DETAILS\')"> ';
				result  = result + '                   <button type="button" class="btn btn-sm btn-primary pull-right" id="addCandidate"  onclick="storeCertificate('+candidateId+');"  >Add</button>';
				result = result +' </sec:authorize>';
			}
			
			result  = result + '                 </div>';

			result  = result + '              </form>';
			$("#popUpDivEmp").empty();
			$("#popUpDivEmp").append(result);
			
			$(".edit-details").toggleClass("edit-details-active");
			
			$(".close-div").click(function(){
				 $(this).parent("div.edit-details").removeClass("edit-details-active");
				 });
			 $(".addUser-org, .assign-org, .collapse-div-adduser, .collapse-div, .edit-details").click(function(e){
				 e.stopPropagation();
			 });
			 
			 var locationDate ='${SourceLeadWeb_dateFormat}'; 
			  var finalDateFormatCerSave = getJSDateFormat(locationDate);
			  
			 
		  $('#StartDate').datepicker({
		      dateFormat: finalDateFormatCerSave,
		      changeMonth: true,
		  	  changeYear: true, 
		      onSelect: function (selected) {
		           // var dt = new Date(selected);
		           var dt = 	  $(this).datepicker('getDate', '+1d'); 
		           dt.setDate(dt.getDate() + 1);
		            $("#EndDate").datepicker("option", "minDate", dt);
		        }
		  });
		  
		  $('#EndDate').datepicker({
		      dateFormat: finalDateFormatCerSave,
		      changeMonth: true,
		  	  changeYear: true, 
		        onSelect: function (selected) {
		            //var dt = new Date(selected);
		            var dt = 	  $(this).datepicker('getDate', '-1d');
		            dt.setDate(dt.getDate() - 1);
		            $("#StartDate").datepicker("option", "maxDate", dt);
		        }
		  });
		}
/*
	<%-- 
	     @author : Ajith Matta.
	     this script is for edit Candidate certification .
	     1.used in  candidateCertificateList function.
	     2.parameter : csrf token .
	     3.ajax url implementation in CandidateCertificationsRestController.
	--%>*/
		function editCertification(candidateCertificationId, candidateId) {
			$.ajax({
				type : "POST",
				url : atsUrl
						+ "/getCandidateCertificateBycandidateIdandCertificateId?"+tokenParamName+"="+tokenValue+"&candidateId="
						+ candidateId + "&candidateCertificationId=" + candidateCertificationId,
				success : function(response) {
					if (response && response.response === "INVALID_DATA") {
		    		    showError(response.responseMessage);
		    		} else if (response.response === "AWKWARD") {
		    		    location.href = "/errorPage";
		    		} else if (response.response === "SUCCESS") {
		    			var json = response.object;
		    			var result = '';
		    		 	result  = result + '     <!-- Popup Div -->                                                                                         ';
		    			result  = result + '         <form id="candidateCertificationEdit_form" name="candidateCertificationEdit_form" method="post">        ';
		    			result = result+'	           <i class="fa fa-close close-div" onClick="closeCertificatePopup();"></i>';
		    			result  = result + '               <div class="form-horizontal clearfix">';
		    			result  = result + '                 <div class="clearfix mb10">';
		    			result  = result + '                   <div class="col-md-12" id="candidatenameDiv">';
		    			result  = result + '                     <input type="text" class="form-control input-sm" value="'+ json.name+ '" name="name"  placeholder="Namedfds *" id="candidateName" ';
		    			result  = result + '                      data-validation="required validate_length length2-250" field-name="Name" >';
		    			result  = result + '                   </div>';
		    			result  = result + '                 </div>';
		    			result  = result + '                 <div class="clearfix mb10">';
		    			result  = result + '                   <div class="col-md-12">';
		    			result  = result + '                     <input type="text" class="form-control input-sm" value="'+ json.provider+ '" name="provider" placeholder="Provider *" id="candidateProvider"';
		    			result  = result + '                      data-validation="required validate_Space validate_length length2-250" field-name="Provider">';
		    			result  = result + '                   </div>';
		    			result  = result + '                 </div>';
		    			result  = result + '                 <div class="clearfix mb10">';
		    			result = result  +'	                     <div class="col-md-5 positionRelative padding_right">';
		    			result = result  +'	                     <select class="newSelect"  name="scoreType"  value="'+ json.scoreType+ '"  id="scoreType" onchange="selectScoreType();clearSearchKey();"  >';
		    			result = result  +' 					   <option value="" selected="selected">Score Type</option>';
		    			if (json.scoreType == "Percentage") {
		    				result = result + '<option selected value="Percentage">Percentage</option>';
		    			} else {
		    				result = result	+ '<option value="Percentage">Percentage</option>';
		    			}
		    			if (json.scoreType == "CGPA") {
		    				result = result	+ '<option selected value="CGPA">CGPA</option>';
		    			} else {
		    				result = result	+ '<option value="CGPA">CGPA</option>';
		    			}
		    			if (json.scoreType == "GRADE") {
		    				result = result	+ '<option selected value="GRADE">GRADE</option>';
		    			} else {
		    				result = result + '<option value="GRADE">GRADE</option>';
		    			}
		    			result = result  +'	                      </select>';
		    			result = result  +'	                   </div>';
		    			result  = result + '                   <div class="col-md-7" positionRelative id="candidateScoreDiv">';
		    			if(json.score !=null && json.score!='undefined'&& json.score !=''){
		    			result  = result + '                     <input type="text" class="form-control input-sm" value="'+json.score+ '" name="score" placeholder="Score" id="candidateScore" onkeyup="selectScoreType();scoreAndScoreType();"';
		    			result  = result + '                     data-validation-optional="true" data-validation="required validate_length length1-5" field-name="score">';
		    			}else{
		    			result  = result + '                     <input type="text" class="form-control input-sm" value="" name="score" placeholder="Score" id="candidateScore" onkeyup="selectScoreType();scoreAndScoreType();"';
		    			result  = result + '                     data-validation-optional="true" data-validation="required validate_length length1-5" field-name="score">';
		    			}
		    			result  = result + '                 	 </div>';
		    			result = result  +'	                   </div>';
		    			result  = result + '                 <div class="clearfix mb10">';
		    			if(json.description !=null && json.description!='undefined'&& json.description !=''){
		    			result  = result + '                   <div class="col-md-12">';
		    			result  = result + '                     <input type="text" class="form-control" rows="1" name ="description" value="'+json.description+ '"  placeholder="Description" id="candidateDescription"';
		    			result  = result + '                     data-validation-optional="true" data-validation="required validate_length length2-250">';
		    			result  = result + '                   </div>';
		    			}else{
		    				result  = result + '                   <div class="col-md-12">';
		    				result  = result + '                     <input type="text" class="form-control" rows="1" name ="description" value=""  placeholder="Description" id="candidateDescription"';
		    				result  = result + '                     data-validation-optional="true" data-validation="required">';
		    				result  = result + '                   </div>';	
		    			}
		    			result  = result + '                 </div>';
		    			result  = result + '                 <div class="clearfix mb10">';
		    			result  = result + '                   <div class="col-md-6 positionRelative">';
		    			if(json.candidatevalidFromDateToString !=null && json.candidatevalidFromDateToString!='undefined'&& json.candidatevalidFromDateToString !=''){
		    				result = result  +'	                   <div>';
		    				result  = result + '                     <input type="text" class="form-control input-sm"  value="'+ json.candidatevalidFromDateToString+ '" name ="validFrom" placeholder="Valid From" ';
		    				result  = result + '                      data-validation-optional="true" data-validation="required" field-name="Valid From" id="StartDateEdit" readonly>';
		    				result  = result + '                     <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span>';
		    				result  = result + '					<div class="clearDate" onClick="editCandidateCertificatesStartDate();"></div>';
		    				result  = result + '                   </div>';
		    			}else{
		    				result = result  +'	                   <div>';
		    				result  = result + '                     <input type="text" class="form-control input-sm"  value="" name ="validFrom" placeholder="Valid From" ';
		    				result  = result + '                      data-validation-optional="true" data-validation="required" field-name="Valid From" id="StartDateEdit" readonly>';
		    				result  = result + '                     <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span>';
		    				result  = result + '					<div class="clearDate" onClick="editCandidateCertificatesStartDate();"></div>';
		    				result  = result + '                   </div>';
		    			}
		    			result  = result + '                   </div>';
		    			result  = result + '                   <div class="col-md-6 positionRelative">';
		    			if(json.candidatevalidToDateToString !=null && json.candidatevalidToDateToString!='undefined'&& json.candidatevalidToDateToString !=''){
		    				result = result  +'	                   <div>';
		    				result  = result + '                     <input type="text" class="form-control input-sm" value="'+ json.candidatevalidToDateToString+ '" name="validTo" placeholder="Valid To" ';
		    				result  = result + '                     data-validation-optional="true" data-validation="required" field-name="Valid To" id="EndDateEdit" readonly>';
		    				result  = result + '                     <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span>';
		    				result  = result + '					<div class="clearDate" onClick="editCandidateCertificatesEndDate();"></div>';
		    				result  = result + '                   </div>';
		    			}else{
		    				result = result  +'	                   <div>';
		    				result  = result + '                     <input type="text" class="form-control input-sm" value = "" name="validTo" placeholder="Valid To" ';
		    				result  = result + '                     data-validation-optional="true" data-validation="required" field-name="Valid To" id="EndDateEdit" readonly>';
		    				result  = result + '                     <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span>';
		    				result  = result + '					<div class="clearDate" onClick="editCandidateCertificatesEndDate();"></div>';
		    				result  = result + '                   </div>';
		    			}
		    			result  = result + '                   </div>';
		    			result  = result + '                 </div>';
		    			result  = result + '               </div>';
		    			result  = result + '                 <div class="clearfix mr">';
		    			result = result	+ '	                   <input type="hidden"  value="'+candidateCertificationId+'" id="candidateCertificationId"  name="candidatecertificateID" >';
		    			result = result	+ '	                   <input type="hidden"  value="'+candidateId+'" id="candidateId" >';
		    			result  = result + '                   <button type="button"  id="addCandidate"  onClick ="updateCandidateCertification('+ candidateId+ ','+ candidateCertificationId+ ');" class="btn btn-sm btn-primary pull-right"  >Update</button>';
		    			result  = result + '                 </div>';

		    			result  = result + '              </form>';
		    			$("#popUpDivEmp").empty();
		    			$("#popUpDivEmp").append(result);
		    			$(".edit-details").toggleClass("edit-details-active");
		    			$(".close-div").click(function(){
		    				$(this).parent("div.edit-details").removeClass("edit-details-active");
		    			});
		    			$(".addUser-org, .assign-org, .collapse-div-adduser, .collapse-div, .edit-details").click(function(e){
		    				e.stopPropagation();
		    			});
		    			var cerStartDate = $("#StartDateEdit").val();
		    			var cerEndDate = $("#EndDateEdit").val(); 
		    			var locationDate ='${SourceLeadWeb_dateFormat}'; 
		    			var finalDateFormatCerEditOneSave = '';
		    			 // written common method in slead-common.js for date formats start
		    			var cerStartDateEdit = getJSDate(cerStartDate,locationDate);
		    			var cerEndDateEdit = getJSDate(cerEndDate,locationDate);
		    			var finalDateFormatCerUpdate = getJSDateFormat(locationDate);
		    			  //end
		    		  $('#StartDateEdit').datepicker({
		    		      dateFormat: finalDateFormatCerUpdate,
		    		      changeMonth: true,
		    		  	  changeYear: true, 
		    		  	  maxDate: cerEndDateEdit,
		    		      onSelect: function (selected) {
		    		            //var dt = new Date(selected);
		    		            var dt = $(this).datepicker('getDate', '+1d');
		    		            dt.setDate(dt.getDate() + 1);
		    		            $("#EndDateEdit").datepicker("option", "minDate", dt);
		    		        }
		    		  });
		    		  $('#EndDateEdit').datepicker({
		    		      dateFormat: finalDateFormatCerUpdate,
		    		      changeMonth: true,
		    		  	  changeYear: true, 
		    		  	  minDate: cerStartDateEdit,
		    		      onSelect: function (selected) {
		    		            //var dt = new Date(selected);
		    		            var dt = $(this).datepicker('getDate', '-1d');
		    		            dt.setDate(dt.getDate() - 1);
		    		            $("#StartDateEdit").datepicker("option", "maxDate", dt);
		    		        }
		    		  });
		    		} else if (response.indexOf('Sign In') !== -1) {
						location.replace(sourceleadUrl);
					} else {
					    showError("Operation failed, please try again");
					}
			},
		});
	}

/*	<%-- 
	     @author : Ajith Matta.
	     this script is for update Candidate certification .
	     1.usage     :  used in editCertification() function 
	     2.parameter : csrf token .
	     3.ajax url implementation in CandidateCertificationsRestController.
	--%>*/
		function updateCandidateCertification(candidateId, candidateCertificationId) {
			var validationSettings = {
				errorMessagePosition : 'element',
				borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd'
			};
			var scoreType = document.getElementById("scoreType").value; 
			if(scoreType !== null && scoreType !== "") {
				$('#candidateScore').removeAttr('data-validation-optional');
				$('#candidateScore').attr("data-validation","required"); 
		 	} else {
				$('#candidateScore').removeAttr('data-validation');
				$('#candidateScore').attr("data-validation-optional","true"); 
			}
			
			if ($('#candidateCertificationEdit_form').validate(false,
					validationSettings)) {
				var formData = getFormData("candidateCertificationEdit_form");
				//It will escape all the special characters
				$.post(atsUrl+"/updateCandidateCertificationsBycandidateIdandcandidateCertificateId?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId+"&candidateCertificationId"+ candidateCertificationId, formData, function(result) {
					if (result && result.response === "INVALID_DATA") {
		    		    showError(result.responseMessage);
		    		} else if (result.response === "AWKWARD") {
		    		    location.href = "/errorPage";
		    		} else if (result.response === "SUCCESS") {
						closeCertificatePopup();
	     				$("#selector5").removeAttr("style");
	     				$("#selector5").css({ display: "block" });
		     			$("#cerMsgDiv").html("Certification details updated");
						candidateCertificateList(candidateId);
						cerMsgFadein();
						qwikCertificates(candidateId);
						setTimeout(function(){
							candidateList();					 			
						},100);
		    		} else if (result.indexOf('Sign In') !== -1) {
						location.replace(sourceleadUrl);
					} else {
					    showError("Operation failed, please try again");
					}
			    });			
				return true;
			} else {
				return false;
			}
		$('body').on('blur','#candidateCertificationEdit_form',
			function() {
				$("#candidateCertificationEdit_form").showHelpOnFocus();
				$("#candidateCertificationEdit_form").validateOnBlur(false,
					validationSettings);
				});
		}
/*	  <%-- 
	     @author : David Sammeta.
	     this script is getting removing Certification in the  List .
	     1.usage     :  used in candidateCertificateList  function
	     2.parameter : candidateId ,csrf.token,candidateemployerId
	     3.ajax url implementation in CandidateCertificationRestController.
	--%>*/    
	      
	      
	function deleteCerPopup(){
		$("#remove_user_pop_certificates, .project_inn_mask").fadeOut();
	}
	function removeCertification(candidateCertificationId, candidateId){
		var result = "";
		result = result +"	<div class='project_inn_mask'></div>";
		result = result +"	   <div id='remove_user_pop_certificates' class='remove_user_pop remove_user_pop_a'>";
		result = result +"		<p>You are choosing to delete the Certification</p>";
		result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
		result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='deleteSelectedCertification("+candidateCertificationId+","+candidateId+");'><input class='u_can_color' type='button'  value='Cancel' onClick='deleteCerPopup();'></b>";
		result = result +"	</div>";
		$("#deleteCertificationPopUp").empty();
		$("#deleteCertificationPopUp").append(result);
		$("#remove_user_pop_certificates").slideDown(200);
		$(".project_inn_mask").fadeIn(200);

	}
	function deleteSelectedCertification(candidateCertificationId,candidateId) {
		 var loginUserId = '${SourceLeadWeb_userId}';
		 var orgLocationId = '${SourceLeadWeb_orgLocationId}';
		 $.ajax({
	          type : "POST",
	          url: atsUrl+"/removeCandidateCertificateBycandidateIdandcandidateCertificateId?"+tokenParamName+"="+tokenValue+"&loginUserId="+loginUserId+"&orgLocationId="+orgLocationId+"&candidateId="+candidateId+"&candidateCertificationId="+candidateCertificationId,        
	          success :function(result) {
				if (result && (result.response === "INVALID_DATA" || result.response === "INVALID_USER")) {
					showError(result.responseMessage);
				} else if (result.response === "AWKWARD") {
		    		location.href = "/errorPage";
		    	} else if (result.response === "SUCCESS") {
		    		$("#popUpDivEmp").removeClass("edit-details-active");
	       			$("#selector5").removeAttr("style");
	        		$("#selector5").css({ display: "block" });
	        		$("#cerMsgDiv").html("Certification deleted");
	        		$("#remove_user_pop_certificates, .project_inn_mask").fadeOut();
	        		candidateCertificateList(candidateId);
	        		qwikCertificates(candidateId);
	        		cerMsgFadein();
	        		setTimeout(function(){
				 		candidateList();					 			
				 	},100);
		    	} else if (result.indexOf('Sign In') !== -1) {
					location.replace(sourceleadUrl);
				} else {
				showError("Operation failed, please try again");
				}
	        },
	    }); 
	}
//	 <!-- this script is for removing the Certification  Ends -->  
	      
	//     @author :      this script is to flush candidate.

		function flushCandiadate() {
			$("#addCandidate").removeAttr("disabled");
			$('#candidateName').val('');
			$('#candidateProvider').val('');
			$('#candidateScore').val('');
			$('#candidateDescription').val('');
			$('#StartDate').val('');
			$('#EndDate').val('');
			$(".error").removeClass("error");
			$('#newCandidate').find('p.jquery_form_error_message').remove();
		}
//	     @author : shivayogi , Ajith.
	//     this script is to display candidate score validation based on scoreType.
		function selectScoreType() {
			
			var str = document.getElementById("candidateScore").value;
			if (str == 'a' || str == 'b' || str == 'c' || str == 'd' || str == 'e') {
				var strToUpper = str.toUpperCase();
				$('#candidateScore').val(strToUpper);
			}
			var selectedText = document.getElementById("scoreType").value;
			var canScore = document.getElementById("candidateScore").value;
			$('#candidateScoreDiv').parent().find('p.jquery_form_error_message')
					.remove();
			if (selectedText == 'Percentage') {
				var floatNum = /^(\d{0,2}(\.\d{0,2})?|100)$/
				if (canScore.match(floatNum)) {
					$("#addCandidate").removeAttr("disabled");
					return true;
				} else {
					$("input#candidateScore")
							.parent()
							.after(
									"<p class='jquery_form_error_message'><span>Enter your Valid Percentage</span>");
					$("#addCandidate").attr("disabled", "disabled");
					return false;
				}
			} else if (selectedText == 'CGPA') {
				var cgpanum = /^(([\d][\.])?([\d])|10)?$/
				if (canScore.match(cgpanum)) {
					$("#addCandidate").removeAttr("disabled");
					return true;
				} else {
					$("input#candidateScore")
							.parent()
							.after(
									"<p class='jquery_form_error_message'><span>Enter your Valid CGPA value</span>");
					$("#addCandidate").attr("disabled", "disabled");
					return false;
				}
			} else if (selectedText == 'GRADE') {
				var grades = /^([A-E][-+]{0,2})?$/
				if (canScore.match(grades)) {
					$("#addCandidate").removeAttr("disabled");
					return true;
				} else {
					$("input#candidateScore")
							.parent()
							.after(
									"<p class='jquery_form_error_message'> Enter your Valid Grade </p>");
					$("#addCandidate").attr("disabled", "disabled");
					return false;

				}
			}
		}
		function scoreAndScoreType() {
			var candidateScore = document.getElementById("candidateScore").value;
			var selectedText = document.getElementById("scoreType").value;
			var floatNum = /^(\d{0,2}(\.\d{0,2})?|100)$/
			var cgpanum = /^(([\d][\.])?([\d])|10)?$/
			var grades = /^([A-E][-+]{0,2})?$/
			if (candidateScore.match(floatNum)) {
				$("#scoreType").val('Percentage');
				$('#candidateScoreDiv').parent().find('p.jquery_form_error_message').remove();
				$("#addCandidate").removeAttr("disabled");
			}
			if (candidateScore.match(grades)) {
				$("#scoreType").val('GRADE');
				$('#candidateScoreDiv').parent().find('p.jquery_form_error_message').remove();
				$("#addCandidate").removeAttr("disabled");
			}
			if (candidateScore.match(cgpanum)) {
				$("#scoreType").val('CGPA');
				$('#candidateScoreDiv').parent().find('p.jquery_form_error_message').remove();
				$("#addCandidate").removeAttr("disabled");
			}
			if ((selectedText == '') && (!candidateScore.match(floatNum))
					&& (!candidateScore.match(cgpanum))
					&& (!candidateScore.match(grades))) {
				$("input#candidateScore")
						.parent()
						.after(
								"<p class='jquery_form_error_message'> Enter your Valid Score </p>");
			}
			if (selectedText == '' && candidateScore == '') {
				$('#candidateScoreDiv').parent()
						.find('p.jquery_form_error_message').remove();
			}
			if (candidateScore == '') {
				$("#scoreType").val('');
			}
		}

	//<%-- 
	//     @author : shivayogi , Ajith.
	//     this script is to flush candidate value when score type is changed.
	//--%>
	function clearSearchKey() {
		   $('#candidateScore').val('');
		   $('#candidateScoreDiv').parent().find('p.jquery_form_error_message').remove();

	}
	       function msgFadein() {
	           setTimeout(function(){
	                   $('#selector1').fadeOut('slow'); }, 1000);
	       }

	//<!-- script for converting dateformat in datepicker -->
	function toDate(selector) {
	    var from = $(selector).val().split("-");
	    return new Date(from[2], from[1] - 1, from[0]);
	}
//	<!-- script for dateformat in datepicker End --> 
	function clearCandidateCertStartDate(){
		$("#StartDate").val("");
		};
	function clearCandidateCertEndDate(){
		$("#EndDate").val("");
		};
	function editCandidateCertificatesStartDate(){
		$("#StartDateEdit").val("");
		};
	 function editCandidateCertificatesEndDate(){
		$("#EndDateEdit").val("");
		}; 
	//5. candidatesCertificates.jsp END HERE
//6.candidateDocuments.jsp STARTS HERE
		function candidateDocuments(candidateId){
			
			$("#candidateProfileTab").removeClass("current");
			$("#candidateDocumentsTab").addClass("current");
			$(".project_tabs_inn").css("display", "block");
			
			var candidateType = '${candidateType}';
			var result = "";
			
			if(window.candidateStatusGlobal == '0'){
				result = result  +'    <div class="project_tabs_inn_head addUser-org overflow_inherint mt25">  ';
			}else{
				result = result  +'    <div class="project_tabs_inn_head addUser-org overflow_inherint mt10">  ';
			}
			result  = result + '<div class="success_pop" id="selector9" style="display: none;">';
		    result  = result + '<i class="fa fa-check-circle"></i><span id="docMsgDiv"></span>';
		    result  = result + '</div>';
			if(window.candidateStatusGlobal == '0'){
				if(candidateType =="Candidates"){
					'<sec:authorize access="hasAnyRole(\'ROLE_CREATE_CANDIDATE_DOCUMENT\',\'ROLE_CROWDSOURCING_CANDIDATES\')">'; 
					result = result  +'<a href="javascript:void(0)"  class="addUser-org" ></a> ';
					'</sec:authorize>';
				}else{
					'<sec:authorize access="hasRole(\'ROLE_CREATE_ASSOCIATE_DOCUMENT\')">'; 
					result = result  +'<a href="javascript:void(0)"  class="addUser-org" ></a> ';
					'</sec:authorize>';
				}
				
			}else{
				var docUUID = '';
				if(candidateType =="Candidates"){
					'<sec:authorize access="hasAnyRole(\'ROLE_CREATE_CANDIDATE_DOCUMENT\',\'ROLE_CROWDSOURCING_CANDIDATES\')">';
					result = result  +'<span class="" style="display : none" id="rejectDoc"><a href="javascript:void(0)"  class="addUser-org"><i class="glyphicon glyphicon-remove text-danger" onClick="rejectRequestedDocs('+candidateId+');" title="Reject"></i></a></span>';
					result = result  +'<span class="" style="display : none" id="acceptDoc"><a href="javascript:void(0)"  class="addUser-org"><i class="glyphicon glyphicon-ok text-success" onClick="acceptRequestedDocs('+candidateId+');" title="Accept"></i></a></span>';
					result = result  +'<span class=""><a href="javascript:void(0)"  onClick="requestDoc();createDocsType('+candidateId+');" class="addUser-org" ><i class="glyphicon glyphicon-plus"></i>Request Documents</a></span>';
					result = result  +'<span class="pad-sea"><a href="javascript:void(0)"  onClick="addDocument('+candidateId+',\''+docUUID+'\');refreshDocList('+candidateId+');" class="addUser-org" ><i class="glyphicon glyphicon-plus"></i>Add Document</a></span>';
				'</sec:authorize>';
				}else{
				 '<sec:authorize access="hasRole(\'ROLE_CREATE_ASSOCIATE_DOCUMENT\')">';
				    result = result  +'<span class="" style="display : none" id="rejectDoc"><a href="javascript:void(0)"  class="addUser-org"><i class="glyphicon glyphicon-remove text-danger" onClick="rejectRequestedDocs('+candidateId+');" title="Reject"></i></a></span>';
					result = result  +'<span class="" style="display : none" id="acceptDoc"><a href="javascript:void(0)"  class="addUser-org"><i class="glyphicon glyphicon-ok text-success" onClick="acceptRequestedDocs('+candidateId+');" title="Accept"></i></a></span>';
					result = result  +'<span class=""><a href="javascript:void(0)"  onClick="requestDoc();createDocsType('+candidateId+');" class="addUser-org" ><i class="glyphicon glyphicon-plus"></i>Request Documents</a></span>';
					result = result  +'<span class="pad-sea"><a href="javascript:void(0)"  onClick="addDocument('+candidateId+',\''+docUUID+'\');refreshDocList('+candidateId+');" class="addUser-org" ><i class="glyphicon glyphicon-plus"></i>Add Document</a></span>';
				'</sec:authorize>';
				
				'<sec:authorize access="!hasRole(\'ROLE_CREATE_ASSOCIATE_DOCUMENT\')">';
				result = result  +'<span class="pad-sea"><a href="javascript:void(0)"   class="addUser-org" ></a></span>';
			'</sec:authorize>';
				}
			
			}
				result = result  +'     <div id="popUpDiv" class="edit-details col-md-4"> ';
				result = result  +'     </div>  ';
				result  = result + '   <div class="project_tabs_inn_table vendor_wrap_inn_table">';
				
				result  = result + '   <div id="candidateDocumentsList" class="datatable_filter"></div>';
				result  = result + '   </div>';
			    result = result+'	    </div>';
			  

			$("#tab-1").empty();
			$("#tab-2").empty();
			$("#tab-3").empty();
			$("#tab-4").empty();
			$("#tab-5").empty();
			$("#tab-6").empty();
			$("#tab-7").empty();
			$("#tab-8").empty();
		    $("#tab-9").empty();
		    $("#tab-9").append(result); 
		    candDocumentsList(candidateId);
		}
		function candDocumentsList(candidateId){
			var candidateType = '${candidateType}';
			 $.ajax({  
		          type : "POST",
		          url: atsUrl+"/getCandidateDocumentsListByCandidateId?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId,        
		          success :function(response) {
		        	  if (response && response.response === "INVALID_USER") {
		        		    showError(response.responseMessage);
		        		} else if (response.response === "AWKWARD") {
		        		    location.href = "/errorPage";
		        		} else if (response.response === "SUCCESS") {
		        			$("#candidateDocumentsList").empty();
		              		var result = "";
		              		result = result +"<table id='candidateDocumentsPagination' class='display' cellspacing='0' width='100%' >"; 
		                  	result = result +"<thead>"; 
		                  	result = result +"  <tr>"; 
		                  	result = result  + '<th><input type="checkbox" id="checkAllDocuments" onclick="toggleAllDocuments(this);deleteRequestedDoc();"></th>';
		                  	result  = result + '<th>Document Type</th>';
		               		result  = result + '<th>Document</th>';
		               		result  = result + '<th>Description</th>';
		               		result  = result + '<th>Upload Status</th>';
		                  	result  = result + '<th>Document Status</th>';
		               		result  = result + '<th>Action</th>';
		                  	result = result +"  </tr>"; 
		                  	result = result +"</thead>";
		                  	result = result +"<tbody>"; 
		                   
		                  	for (var i = 0; i < response.data.length; i++) { 
		                  		var opt = response.data[i];  
		                  		if(opt.isConfidential == 'N' || opt.docUploadStatus =="Requested"|| (opt.isConfidential == 'Y' && '${SourceLeadWeb_userCode}' == opt.userIdStr)) {
		                      		result  = result + ' <tr>';
		                      	if(opt.requestDocumentId != null && opt.requestedDocStatus == "Submitted"){
		                      		result = result +'<td><input id="'+opt.id+'" value="'+opt.id+'"  onclick="deleteRequestedDoc();" type="checkbox" name="documents[]" class="myCheckbox" >';
		                      	} else {
		                      		result = result +"  <td><div>--</div></td>"; 
		                      	}
		                      	 
		                      	if(opt.documentType != null && opt.documentType != '' && opt.documentType != "undefined") {
		      	            		if(opt.documentOtherType !=null && opt.documentOtherType !='' && opt.documentOtherType !="undefined") {
		      	                   		result = result +"  <td><div title='"+opt.documentOtherType+"' class='tbldata-dotdot-me'>"+opt.documentOtherType+"</div></td>";
		    	    	            } else {
		    	    	            	result = result +"  <td><div title='"+opt.documentType+"'>"+opt.documentType+"</div></td>"; 
		    	    	            }
		      	             	 } else {
		      	             		 result = result +"  <td><div>----</div></td>"; 
		      	             	 }
		                      	 
		                      	if(opt.fileName != null && opt.fileName != '' && opt.fileName != 'undefined') {
		    						result = result+'<td><div><span title ="'+opt.fileName+'">'+opt.fileName+'</span><a class="fa fa-eye" onClick="viewFile(\''+opt.fileURL+'\',\''+opt.fileName+'\',\''+opt.fileContentType+'\');"></a><a download class="glyphicon glyphicon-download-alt" target="_blank" href=\"'+opt.fileURL+'\" download title="Download"></a></div></td>';
		                      	} else {
		                      		result  = result + ' <td><div><span>----</span></div></td>';
		                      	}
		                      	 
		    					if(opt.description != null && opt.description != '' && opt.description != 'undefined') {
		    	                	result  = result + ' <td><div><span title="'+opt.description+'">'+opt.description+'</span></div></td>';
		    					} else {
		    	                	result  = result + ' <td><div><span>----</span></div></td>';
		    					}
		                      	 
		    					result = result +"  <td><div title='"+opt.docUploadStatus+"'>"+opt.docUploadStatus+"</div></td>"; 
		                      	if(opt.requestedDocStatus != null && opt.requestedDocStatus != '' && opt.requestedDocStatus != 'undefined') {
		                      		result = result +"<td>";
		                      		result = result +"<div title='"+opt.requestedDocStatus+"'>"+opt.requestedDocStatus+" "; 
		                      		if(opt.requestedDocStatus == "Rejected" && opt.requestedDocRejectedReason != null && opt.requestedDocRejectedReason != '' && opt.requestedDocRejectedReason != 'undefined') {
		    				        	result = result + '<i class="glyphicon glyphicon-comment ml10 edit-reason"><div class="edit-status"><p>'+"Reason : "+opt.requestedDocRejectedReason+'</p></div></i>';
		    				        }
		                      		result = result +"</div>";
		                      		result = result +"</td>";
		                      	 } else {
		                      		result = result +"  <td><div>----</div></td>"; 
		                      	 }

		                      	result = result+'<td><div class="tblicons">';
		    		           	if(window.candidateStatusGlobal == 0) {
		    		           		result = result+'<a class="glyphicon glyphicon-pencil" href="javascript:void(0)"></a>';
		    			           	result = result+'<i class="glyphicon glyphicon-trash" href="javascript:void(0)"></i>';
		    		           	} else {
		    		           		var docUUID = opt.documentUUID;
		    		           		result = result+' <a class="glyphicon glyphicon-pencil" href="javascript:void(0)" onClick="addDocument('+candidateId+',\''+docUUID+'\'); getDocument('+candidateId+', '+opt.id+');" title="Edit"></a>';
		    		           		if(candidateType =="Candidates"){
		    		           			'<sec:authorize access="hasAnyRole(\'ROLE_DELETE_CANDIDATE_DOCUMENT\',\'ROLE_CROWDSOURCING_CANDIDATES\')">';
		    		           				result = result+'<i class="glyphicon glyphicon-trash" onclick="removeDocument('+candidateId+','+opt.id+');" title= "Remove"></i>';
		    		           			'</sec:authorize>';
		    		           		} else {
		    		           			'<sec:authorize access="hasRole(\'ROLE_DELETE_ASSOCIATE_DOCUMENT\')">';
		    		           				result = result+'<i class="glyphicon glyphicon-trash" onclick="removeDocument('+candidateId+','+opt.id+');" title= "Remove"></i>';
		    		           			'</sec:authorize>';
		    		           		}
		    		           	}
		    		           	result = result+'</div></td>';
		                      	result  = result + '</tr>';
		                  	 }
		                   } 
		                   result = result +" </tbody>";
		                   result = result +"</table>";
		                   $("#candidateDocumentsList").append(result); 
		                   documentsPagination();
		        		} else if (response.indexOf('Sign In') !== -1) {
		        			location.replace(sourceleadUrl);
		        		} else {
		        		    showError("Operation failed, please try again");
		        		}
		          }
			});     
		}
		function candidateDocumentView(documentUUID,title) {
			if(documentUUID != null && documentUUID != "") {
				window.open(atsUrl+'/viewProfessionalCandidateDocument/'+title+'?documentUUID='+documentUUID,title,"height=600,width=600");
			}
		}
			function toggleAllDocuments(source) {
				 var checkboxes = document.querySelectorAll('input[class="myCheckbox"]');
			    for (var i = 0; i < checkboxes.length; i++) {
			        if (checkboxes[i] != source)
			            checkboxes[i].checked = source.checked;
			        
			    }
				 $('input[class="myCheckbox"]').each(function(i) {
			    if(!this.checked) {
			        $('#checkAllDocuments').removeAttr('checked');
			    }
			});
			}
		function deleteRequestedDoc(){
			
			 var documentsIds = [];
			 $("input[name='documents[]']:checked").each( function (i) {
				 documentsIds[i] = $(this).val();
			});
			 
			 if(documentsIds.length > 0){
				 document.getElementById("rejectDoc").style.display="";
				 document.getElementById("acceptDoc").style.display="";
			 }else{
				 document.getElementById("rejectDoc").style.display="none";
				 document.getElementById("acceptDoc").style.display="none";
			 }
			 
		}
		 function rejectRequestedDocs(candidateId){
			
			 var result = "";
				result = result + '	<div class="settPop">';
				result = result + '	<div id="docsrejectedLoading"></div>';
				result = result + '<form id="docrejectedReason_form" name="docrejectedReason_form" method="post">';
				result = result + '	<h3>Documents Rejected Reason<i class="fa fa-times" onclick="cancelDocumentRejectedReason();"></i></h3>  ';
				result = result + '	<div class="settPopInn"> ';
				
				result = result + '		<div class="clearfix">  ';
				result = result + '      <input type="hidden" name="rejectedDocsCandidateId"  id="rejectedDocsCandidateId" value='+candidateId+' />';
				result = result + '		  <label class="col-md-12" for="Rejected Reason">Reason</label> ';
				result = result + '		  <div class="col-md-12 taskdesp mt10">   ';
				result = result + '			<textarea class="form-control" name="rejectedReason" id="rejectedReason" field-name="Reason" data-validation="required  validate_length length2-500" rows="2"></textarea>';
				result = result + '			<span class="hidden"></span> </div>  ';
				result = result + '		</div>';
				result = result + '		<div class="col-md-12 mt5 clearfix"> ';
				result = result + '		<div class="pull-right">  ';
				result = result + '		  <input type="submit" value="Save" id="submit_rejectedReason"  class="btn btn-primary" >';
				result = result + '		  <input type="button" value="Cancel" class="btn btn-default ml10" onclick="cancelDocumentRejectedReason();">';
				result = result + '		</div>';
				result = result + '		</div>';
				result = result + '		<div class="clearfix"></div>   ';
				result = result + '	</div>  ';
				result = result + '</form>';
				result = result + '	</div>';
			    $("#rejectedReasonDiv").empty();
			    $("#rejectedReasonDiv").append(result);
			    
			    $(".settPop").slideDown(200);
				$(".vendor_wrap_mask").fadeIn(200);
			    
			    
		 }
		 function acceptRequestedDocs(candidateId){
			 var documentsIds = [];
		 	 $("input[name='documents[]']:checked").each( function (i) {
		 		 documentsIds[i] = $(this).val();
		 	});
		 	var userId = '${SourceLeadWeb_userId}';
			var orgLocationId = '${SourceLeadWeb_orgLocationId}';
			   $.ajax({
			    	type: "POST",
			    	url: atsUrl+"/acceptRequestedDocuments?"+tokenParamName+"="+tokenValue+"&documentsIds="+documentsIds+"&userId="+userId+"&orgLocationId="+orgLocationId+"&candidateId="+candidateId,
			        success: function(response) {
			        	if (response && response.response === "INVALID_DATA") {
			        	    showError(response.responseMessage);
			        	} else if (response.response === "AWKWARD") {
			        	    location.href = "/errorPage";
			        	} else if (response.response === "SUCCESS") {
			        		candidateDocuments(candidateId);
			        	} else if (response.indexOf('Sign In') !== -1) {
			        		location.replace(sourceleadUrl);
			        	} else {
			        	    showError("Operation failed, please try again");
			        	}
			        },
			    });
		 }
		function cancelDocumentRejectedReason(){
			$(".settPop").slideUp(200);
			$(".vendor_wrap_mask").fadeOut(200);
		}

//		<!-- Show success message after form submission script -->
			function docMsgFadein() {
				$("#selector9").fadeIn(200);
		        $('#selector9').delay(2000).fadeOut('slow');
			}


/*		<%-- 
		     @author : prasadK.
		     this script is for get candidateDocumentsList.
		     1.usage     : used in candidateDocument().
		     2.parameter : csrf token,candidateId .
		     3.ajax url implementation in CandidateListRestController.
		--%>*/
		function candidateDocumentsList(candidateId){
			 var sourceleadUrl ='${sourceleadUrl}';
			
		}
/*
		<%-- 
		     @author : Prasad K.
		     this script is for prepare pagination.
		     1.usage     : used in candidateDocumentsList().
		--%>*/
		function documentsPagination(){  
		    $('#candidateDocumentsPagination').DataTable( { 
		    	"scrollCollapse": false,
		    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
		    	"bLengthChange": true,
		    	"bDestroy": true,
		    	"ordering": false,
		    	"oLanguage": {
		     		"sSearch": "",
		     		 "sZeroRecords": "", "sEmptyTable": ""
		     	}
		    } );
		}
/*

		<%-- 
		     @author : Prasad K.
		     this script is for remove popUp.
		     1.usage   : used in addDocument() function.
		--%>
*/
		function closePopup1(){
			 $("#popUpDiv").removeClass("edit-details-active");
			 $(".location-alert").fadeOut();
		}
		function addDocument(candidateId, documentUUID) {
			var result = "";
			result = result  +'<!-- Popup Div -->';
			result = result  +'<form role="form"  id="documentType_form" name="documentType_form" method="post" > ';
			result = result  +'<i class="fa fa-close close-div"  onClick="closePopup();"></i> ';
			result = result  +'<div class="form-horizontal clearfix"> ';
			result = result  +'   ';
			
			result = result  +'<div class="clearfix mb10 col-md-12"> ';
			result = result  +'	<select label=" " class="chosen-select form-control" field-name="Document Type" data-placeholder = "Select Document Type" name="name" id="docName" data-validation="required" onchange="getDocumentType(this);">';
							   
			
		//	                   <c:forEach var="doctype" items="${documentsList}">
		    result = result +'<option value="" disabled selected></option>';	
			result = result + '<option value="${doctype.name}" >${doctype.name}</option>';
		//	                   </c:forEach>  
		    result = result + '</select>';
			result = result  +' </div> ';
			result = result  +'';
			
			result = result + '<div class="col-md-12 documentTypeDiv mb10" id="documentTypeDiv" style="display:none">';
			result = result + '<input type="text" class="form-control input-sm" name="documentOtherType"  id="documentOtherType" value="" placeholder="Document Other Type" field-name="Document Other Type" data-validation="required validate_Space validate_length length1-250">';
			result = result + '</div>'; 
			
			result = result+'<div class="clearfix mb10" id="uploadDocDiv">';
			result = result+'<div class="col-md-12 validation_p">';
			result = result+'<div class="input-group" id="uploadFileGroup">';
			result = result+'<label class="input-group-btn" onchange="uploadFileDoc();"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File';
			result = result+'<input type="file"  id="uploadBtn" style="display: none;"  multiple="" class="upload">';
			result = result+'</span> </label>';
			result = result+'<input type="text" class="form-control" readonly id="uploadFile" name="docFile" placeholder="Upload Document..." field-name="Document" data-validation="required validate_Space validate_docimgpdf">';
			result = result+'</div>';
			result = result+'</div>';  
			result = result+'</div>'; 
			
			result = result+'<div class="clearfix mb10" id="docNameDiv" style="display: none;">';
			result = result+'<div class="col-md-12 mt">';
			result = result+'<label id="docsName" name="docName" class="text-primary tbldata-dotdot-vsm pull-left"></label>&nbsp;&nbsp;<span id="delete_doc" onclick="removeDocNameDiv();"><i class="fa fa-trash removeIcon"></i></span>';
			result = result+'</div>';
			result = result+'</div>';
			
			result = result+'<div class="clearfix mb10 mt25">';
			result = result+'<div class="col-md-12">';
			result = result+'<textarea rows="2" placeholder="Description" value="" id="description" name="description" data-validation-optional="true" data-validation="validate_length length1-250"  field-name="Description" class="form-control"></textarea>';
			result = result+'</div>';
			result = result+'</div>';  
			
			result = result+'<div class="clearfix mr mt" id="docUpdateButton" style="display: none;">';
			result = result+'<button type="button" id="update_doc" class="btn btn-sm btn-primary pull-right">Update</button>';
			result = result+'</div>';
			
			result = result  +'<div class="clearfix mr" id="docAddButton">';
		    result = result +'<input type="button" value="Upload" name="btnAddPr" id="save_doc" class="btn btn-sm btn-primary pull-right" onClick ="storeCandidateDocument('+candidateId+', \''+documentUUID+'\');" />'; 
			result = result  +'</div>';
			result = result  +'</div>';
			result = result  +'</form>';
			$("#popUpDiv").empty();
			$("#popUpDiv").append(result);
			
			$(".edit-details").toggleClass("edit-details-active");
			 $(".close-div").click(function(){ 
				 $(this).parent("div.edit-details").removeClass("edit-details-active");
				 });
			 $(".addUser-org, .assign-org, .collapse-div-adduser, .collapse-div, .edit-details").click(function(e){
				 e.stopPropagation();
			 });
			 
			 /* upload file script to show selected file name*/
			    document.getElementById("uploadBtn").onchange = function () {
			    	$('#uploadFile').val($(this).val().replace(/C:\\fakepath\\/i, ''))
			   }; 
			    
			    /* upload file script for size validation 2MB*/
			   
			// <%-- Eductions and Skills Autocomplete Scripts Starts Here --%>
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
			//	<%-- Eductions and Skills Autocomplete Scripts Ends Here --%>
			}
		function getDocumentType(documentType){
			 var existingDocumentType = $('#docsName').val(); 
			if(documentType.value == 'Other'){
				 document.getElementById("documentTypeDiv").style.display="block";
				  $("#documentOtherType").attr("data-validation-optional", "false");
				  $("#documentOtherType").attr("data-validation", "required validate_Space validate_length length2-250");
			}else{
				document.getElementById("documentTypeDiv").style.display="none";
				  $("#documentOtherType").attr("data-validation-optional", "true");
				  $("#documentOtherType").find('p.jquery_form_error_message').remove();
				  if(existingDocumentType ==null || existingDocumentType == "" || existingDocumentType == "undefined" || existingDocumentType != 'Other'){
					  $("#documentOtherType").val('');
				  }
			}
		}

		    
/*		 <%-- 
		     @author : prasad K
		     this script is for saving document
		     1.usage     : onclick
		     2.parameter : candidateId.
		     3.ajax url implementation in CandidateDocumentRestController.
		--%>*/
		function storeCandidateDocument(candidateId, documentUUID) {
			var validationSettings = {
				errorMessagePosition : 'element',
				borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd' 
			}; 
			if ($('#documentType_form').validate(false, validationSettings)) {
				if(document.getElementById("uploadBtn") !== null){
//		 		if(document.getElementById("uploadBtn") !== null && (document.getElementById("uploadBtn").files.length > 0 || $("#docsName").html()!=="")) {
					var frm = $('#documentType_form')[0];
					var formData = new FormData(frm);
					$("#save_doc").attr("disabled", "disabled");
					$("#save_doc" ).addClass('button').val('Processing..');
					$.ajax({
						type : "POST",
				      	data: formData,
				  		processData: false,
				  	    contentType:false,
				      	url : atsUrl+"/saveCandidateDocument?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId+"&documentUUID="+documentUUID,
						success : function(result) {
							if (result && (result.response === "INVALID_DATA" || result.response === "INVALID_USER")) {
				    		    showError(result.responseMessage);
				    		} else if (result.response === "AWKWARD") {
				    		    location.href = "/errorPage";
				    		} else if (result.response === "SUCCESS") {
				    			closePopup();
								$('#perVal').attr('aria-valuenow', result.object).css('width',result.object+'%');
			        				if(result.object < 50) {
			        			   		$("#perVal").removeClass('progress-bar-success');
			        			   		$("#perVal").addClass("progress-bar-danger");
			        			 	}
			        			 	if(result.object >= 50) {
			        					$("#perVal").removeClass('progress-bar-danger');
			        				 	$("#perVal").addClass("progress-bar-success");
			        			 	}
			        			 	$("#disVal").empty();
			        				$("#disVal").append(result.object+'%');
			         			// for quick view bar update
			   	      			$('#quickPerVal').attr('aria-valuenow', result.object).css('width',result.object+'%');
			       	 			if(result.object < 50) {
			         				$("#quickPerVal").removeClass('progress-bar-success');
			         			   	$("#quickPerVal").addClass("progress-bar-danger");
			         			}
			         			if(result.object >= 50) {
			         				$("#quickPerVal").removeClass('progress-bar-danger');
			         				$("#quickPerVal").addClass("progress-bar-success");
			         			}
			         			$("#quickDisVal").empty();
			         			$("#quickDisVal").append(result+'%');
								$("#popUpDiv").removeClass("edit-details-active");
			          			$("#selector9").removeAttr("style");
			          			$("#selector9").css({ display: "block" });
			          			if(documentUUID != null && documentUUID !="" && documentUUID !="undefined")
			          				$("#docMsgDiv").html("Document updated");
			           			else
			           				$("#docMsgDiv").html("Success!! New Document added");
			           			candDocumentsList(candidateId);
			           			docMsgFadein();
			           			setTimeout(function(){
			      		 			candidateList();					 			
			      		 		},100);
				    		} else if (result.indexOf('Sign In') !== -1) {
								location.replace(sourceleadUrl);
							} else {
							    showError("Operation failed, please try again");
							}
						},
					}); 
					return true;
				} else {
					showError("Document file is missing");
				}
			}
			return false;
		}
		$('body').on('blur', '#documentType_form', function() {
			$("#documentType_form").showHelpOnFocus();
			$("#documentType_form").validateOnBlur(false, validationSettings);
		});
		function uploadFileDoc() {
			fileUpload("uploadBtn", "candidateDocuments", "documentType_form");
		}
		function refreshDocList(candidateId){
			candDocumentsList(candidateId);
		}
/*
		<%-- 
		     @author : Prasad K
		     this script is for get document details in edit mode
		     1.usage     : onclick
		     2.parameter : candidateId, documentId
		     3.ajax url implementation in CandidateResumeRestController.
		--%>*/
		function getDocument(candidateId, documentId){
			 $("#docsName").attr("data-validation-optional", "true");
			  $("#documentOtherType").attr("data-validation-optional", "true");
				$.ajax({
				   	type: "POST",
				    url: atsUrl+"/getDocument?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId+"&documentId="+documentId, 	   
		   		    success: function(response) {
		   		    	if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")){
		   		    	    showError(response.responseMessage);
		   		    	} else if (response.response === "AWKWARD") {
		   		    	    location.href = "/errorPage";
		   		    	} else if (response.response === "SUCCESS") {
		   		    		var data = response.object;
		   		    		$("#uploadDocDiv").hide();
		       			 	$("#docAddButton").hide();
		       			 	$("#uploadFile").removeAttr("data-validation");
		       			 	if(data.fileName != null && data.fileName !="undefined" && data.fileName != "") {
		       			 		$("#docNameDiv").show();
		       			 	}
		       			 	$("#docsName").append(data.fileName);
		       			 	$("#docsName").prop('title', data.fileName);
		       			 	$("#docUpdateButton").show();
		       			 	if(data !=null) {
		       			 		if(data.documentType != null && data.documentType != "undefined" && data.documentType != "") {
			                    	$("select#docName").val(data.documentType);
			                     	$("select#docName").trigger("chosen:updated");
			                   
			                    //here documentType is Other then documentOtherType  also display in Edit.
			                   
			                     	if(data.documentType == 'Other') {
				        				if(data.documentOtherType !=null && data.documentOtherType !='' && data.documentOtherType !="undefined") {
				        					document.getElementById("documentTypeDiv").style.display="block";
				        					$("#documentOtherType").val(data.documentOtherType);
					    	            }
			        			 	}
		       			 		}
			       				$("#update_doc").attr("onclick", "storeCandidateDocument("+data.candidateId.id+", \""+data.documentUUID+"\");");
		       			 
			    	   			if(data.description != null && data.description != "undefined" && data.description != ""){
			       					 $("textarea#description").val(data.description);
			       				}
		       			 	}
		   		    	} else if (response.indexOf('Sign In') !== -1) {
		   		    		location.replace(sourceleadUrl);
		   		    	} else {
		   		    	    showError("Operation failed, please try again");
		   		    	}
		   		    }
			});
		}
		function deleteDocumentPopup() {
			$("#remove_user_pop_documents, .project_inn_mask").fadeOut();
		}
		function removeDocument(candidateId, documentId) {
			var result = "";
			result = result +"	<div class='project_inn_mask'></div>";
			result = result +"	   <div id='remove_user_pop_documents' class='remove_user_pop remove_user_pop_a'>";
			result = result +"		<p>You are choosing to delete the Documents</p>";
			result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
			result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='deleteDocument("+candidateId+","+documentId+");'><input class='u_can_color' type='button'  value='Cancel' onClick='deleteDocumentPopup();'></b>";
			result = result +"	</div>";
			$("#deleteDocumentPopUp").empty();
			$("#deleteDocumentPopUp").append(result);
			$("#remove_user_pop_documents").slideDown(200);
			$(".project_inn_mask").fadeIn(200);
		}
/*		<%-- 
		     @author : prasad K
		     this script is for terminate Doc (inactive status)
		     1.usage     : onclick
		     2.parameter : candidateId, documentId
		     3.ajax url implementation in CandidateDocumentRestController.
		--%>*/
		function deleteDocument(candidateId, documentId) {
			var loginUserId = '${SourceLeadWeb_userId}';
			var orgLocationId = '${SourceLeadWeb_orgLocationId}';
			//var message = confirm("You are choosing to delete the document");
			$.ajax({
			   	type: "POST",
			    url: atsUrl+"/terminateDoc?"+tokenParamName+"="+tokenValue+"&loginUserId="+loginUserId+"&orgLocationId="+orgLocationId+"&candidateId="+candidateId+"&documentId="+documentId, 	   
		  		success: function(result) {
		  			if (result && result.response === "INVALID_DATA") {
					    showError(result.responseMessage);
					} else if (result.response === "AWKWARD") {
					    location.href = "/errorPage";
					} else if (result.response === "SUCCESS") {
						$('#perVal').attr('aria-valuenow', result.object).css('width',result.object+'%');
			   			if(result.object < 50){
			   				/* $("#perVal").removeClass("progress-bar-success"); */
			   				$("#perVal").find('progress-bar-success').remove();
			   				$("#perVal").addClass("progress-bar-danger");
			   			}
			   			if(result.object >= 50){
			   				$("#perVal").find('progress-bar-danger').remove();
			   				$("#perVal").addClass("progress-bar-success");
			   			}
			   			$("#disVal").empty();
			   			$("#disVal").append(result.object+'%');
			   			// for quick view bar update
			  			$('#quickPerVal').attr('aria-valuenow', result.object).css('width',result.object +'%');
			    		if(result.object < 50){
			     			$("#quickPerVal").removeClass('progress-bar-success');
			     			$("#quickPerVal").addClass("progress-bar-danger");
			     		}
			     		if(result.object >= 50){
			     			$("#quickPerVal").removeClass('progress-bar-danger');
			     			$("#quickPerVal").addClass("progress-bar-success");
			     		}
			  			$("#quickDisVal").empty();
			  			$("#quickDisVal").append(result.object+'%');
			  			$("#selector9").removeAttr("style");
		   				$("#selector9").css({ display: "block" });
		   				$("#docMsgDiv").html("Document deleted");
		  				$("#popUpDiv").removeClass("edit-details-active");
		  				$("#remove_user_pop_documents, .project_inn_mask").fadeOut();
		  				candDocumentsList(candidateId);
		  				docMsgFadein();
		  				setTimeout(function(){
							candidateList();					 			
			     		},100);	
					} else if (result.indexOf('Sign In') !== -1) {
						location.replace(sourceleadUrl);
					} else {
					    showError("Operation failed, please try again");
					}
		  		},
			});
		}
/*
		<%-- 
		@author : Prasad K
		this script is for remove Document in edit mode to upload new document
		1.usage     : onclick
		--%>*/
		function removeDocNameDiv(){
			$("#docNameDiv").hide();
			$("#uploadDocDiv").show();
			$("#uploadFile").attr("data-validation", "required validate_Space validate_docimgpdf");
		}

	//	<!-- Show success message after form submission script -->
			function docMsgFadein() {
				$("#selector9").fadeIn(200);
		        $('#selector9').delay(2000).fadeOut('slow');
			}
/*		<!-- End -->


		<%-- 
		     @author : Prasad K
		     this script is for add document popup
		     1.usage     : onload
		--%>*/

		//Request Documents
		    function requestDoc(){
		    $(".requestDocuments").fadeIn();
		    $(".requestDocSideMask").fadeIn();
		    }
		    function closeRD(){
		    $(".requestDocuments").fadeOut();
		    $(".requestDocSideMask").fadeOut();
		    }
		function createDocsType(candidateId){
			
			$("#documentsTypeDiv").empty();
			
			var result = "";
			result = result +'<form id="requestForDocuments_form" name="requestForDocuments_form" method="post">';
			result = result +'<div class="modal-content">';
			result = result +'<div class="modal-header">';
			result = result +'<button type="button" class="close" data-dismiss="modal" onClick="closeRD();">&times;</button>';
			result = result +'<h4 class="modal-title">';
			result = result +'Request Documents';
			result = result +'</h4>';
			result = result +'</div>';
			result = result +'<div class="modal-body">';
			result = result +'<div class="form-horizontal clearfix mb">';
			result = result +'<div class="clearfix"></div>';
		        
		        
			result = result +'<div class="clearfix mb10 mt5 requestdocmntTable">';
			result = result +'<input type="hidden" name="requestedCandidateId"  id="requestedCandidateId" value='+candidateId+' />';
			result = result +'<input type="hidden" name="docuemntMaxRowNumber"  id="docuemntMaxRowNumber" value="1" />';
			result = result +'<table class="table table-responsive table-bordered table-hover" width="100%" id="requestedDocumentsTable" border="0">';
			result = result +'<tbody>';
			result = result +'<tr id="requestedDocumentsTsRow0">';
			result = result +'<div id="0requestedDocumentsFileGroup">';
			result = result +'<td width="200px">';
			result = result +'<div class="dropdown-select">';
			result = result +'<select class="width100percnt form-control selectborder" data-validation="required" field-name="Document Type" onchange="getSelectedDocumentType(0);" id="0docType" name="requestDocumentList[0].docType">';
			result = result +'<option selected value="">--Document Type--</option>';
			
		   // <c:forEach var="doctype" items="${documentsList}">
				result = result + '<option value="${doctype.name}" >${doctype.name}</option>';
		  //  </c:forEach> 
		    
			result = result +'</select>';
			result = result +'<div class="clearfix mt5" id="0otherType" name="0otherType" style="display: none;">';
			result = result +'<input type="text" class="form-control input-sm" placeholder="Other Type" data-validation-optional="true" field-name="Other Type" name="requestDocumentList[0].otherDocType" id="0otherDocType">';
			result = result +'</div>';
			result = result +'</div>';
			result = result +'</td>';
			result = result +'<td width="auto">';
			result = result +'<div class="">';
			result = result +'<textarea rows="2" placeholder="Description" class="form-control" id="description" name="requestDocumentList[0].description" field-name="Description" data-validation-optional="true" data-validation="validate_length length1-500"></textarea>';
			result = result +'</div>';
			result = result +'</td>';
			result = result +'<td width="150px">';
			result = result +'<div class="">';
			result = result +'<label class="switch mt10 ml10 pull-left">';
			result = result +'<input type="checkbox" checked id="0checked" name="requestDocumentList[0].checked" onclick="checkDocument(0)" value="Y">';
			result = result +'<div class="slider round"></div>';
			result = result +'</label>';
			result = result +'<div class="pull-right">';
			result = result +'<button type="button" class="btn btm-sm pull-right mt5" onclick="addDocumentRow();"><i class="fa fa-plus text-primary"></i></button>';
			result = result +'</div>';
			result = result +'</div>';
			result = result +'</td>';
			result = result +'</div>';
			result = result +'</tr>';
			result = result +'</tbody>';
			result = result +'</table>';
			result = result +'</div>';
			result = result +'</div>';
			result = result +'</div>';
		    
			result = result +'<div class="modal-footer clearfix">';
			result = result +'<button type="submit" id="submit_docs" class="btn btn-sm btn-primary mr10">Send</button>';
			result = result +'<button type="button" class="btn btn-sm btn-default" onclick="closeRD();">Cancel</button>';
			result = result +' </div>';
			result = result +'</div>';
			result = result + '</form>';
			
			$("#documentsTypeDiv").append(result);
			
		}
		function checkDocument(id){
			var checked = document.getElementById(id+"checked").checked
			if(checked == true){
				$("#"+id+"checked").val("Y");
			}else{
				$("#"+id+"checked").val("N");
			}
		}
		function getSelectedDocumentType(id){
			 var selectedDocType = document.getElementById(id+"docType").value; 
			if(selectedDocType == 'Other'){
				  document.getElementById(id+"otherType").style.display="block";
				  $("#"+id+"otherDocType").removeAttr("data-validation-optional");
				  $("#"+id+"otherDocType").attr("data-validation", "required validate_Space validate_length length2-250");
			}else{
				  document.getElementById(id+"otherType").style.display="none";
				  $("#"+id+"otherDocType").removeAttr("data-validation");
				  $("#"+id+"otherDocType").attr("data-validation-optional", "true");
				  $("#"+id+"otherType").find('p.jquery_form_error_message').remove();
				  if(selectedDocType ==null || selectedDocType == "" || selectedDocType == "undefined" || selectedDocType != 'Other'){
					  $("#"+id+"otherDocType").val('');
				  }
			}
		}
		$('body').on('submit', '#requestForDocuments_form', function(e){
			var validationSettings = {
			        errorMessagePosition : 'element',
			        borderColorOnError : '',
					scrollToTopOnError : true,
					dateFormat : 'yyyy/mm/dd' 
		         }; 
			if ($('#requestForDocuments_form').validate(false, validationSettings)){
				e.preventDefault();
				var requestedCandidateId = document.getElementById("requestedCandidateId").value;
		     /*  var form = $('#requestForDocuments_form')[0];
		      var formData = new FormData(form); */
		      	var formData = getFormData("requestForDocuments_form");
		        $("#submit_docs").attr("disabled", "disabled");
				$("#submit_docs" ).addClass('button').val('Processing..');
				$("#documentLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
					$.ajax({
					    type: "POST",
					    data : formData,
					    //processData: false,
				    	//  contentType:false,
					    url: atsUrl+"/requestCandidateForDocuments?"+tokenParamName+"="+tokenValue+"", 
					    success: function(response) {
					    	if (response.response === "AWKWARD") {
					    	    location.href = "/errorPage";
					    	} else if (response.response === "SUCCESS") {
					    		$("#documentLoading").removeAttr("style"); 
						        closeRD();
						        candidateDocuments(requestedCandidateId);
					    	} else if (response.indexOf('Sign In') !== -1) {
					    		location.replace(sourceleadUrl);
					    	} else {
					    	    showError("Operation failed, please try again");
					    	}
						},
				    });   
		      		return true; 
		    	} else {
		    		return false;	
		       }
		  });

			$('body').on('blur', '#requestForDocuments_form', function() {
				$("#requestForDocuments_form").showHelpOnFocus();
				$("#requestForDocuments_form").validateOnBlur(false, validationSettings);
			});
		function addDocumentRow(){
			var checkMaxTsRowNum = $("#docuemntMaxRowNumber").val();
			var tbl = document.getElementById("requestedDocumentsTable");
			var maxRowNum = parseInt($("input[name = 'docuemntMaxRowNumber']").val());
			$("input[name = 'docuemntMaxRowNumber']").val(maxRowNum + 1);
			
			
			var row = tbl.insertRow(maxRowNum);
			row.setAttribute("id", "divRequestedDocumentsRow" + maxRowNum);
			
			
			var cell0 = row.insertCell(0);
			var divDocTypeTdStr = document.getElementById("divDocsTd1").innerHTML;
			divDocTypeTdStr = divDocTypeTdStr.replace(/REPLACEROWID/g, (maxRowNum));
			cell0.innerHTML = divDocTypeTdStr;
			
			var cell1 = row.insertCell(1);
			var divDocDescTdStr = document.getElementById("divDocsTd2").innerHTML;
			divDocDescTdStr = divDocDescTdStr.replace(/REPLACEROWID/g, (maxRowNum));
			cell1.innerHTML = divDocDescTdStr; 
			
			var cell2 = row.insertCell(2);
			var divDocDescTdStr = document.getElementById("divDocsTd3").innerHTML;
			divDocDescTdStr = divDocDescTdStr.replace(/REPLACEROWID/g, (maxRowNum));
			cell2.innerHTML = divDocDescTdStr; 
		}
			function removeDocType(rowId) {
				 document.getElementById("divRequestedDocumentsRow"+rowId).style.display = 'none';
				 document.getElementById("divRequestedDocumentsRow"+rowId).value= '';
				 var  rId = "divRequestedDocumentsRow"+rowId;
				  var r = document.getElementById("requestedDocumentsTable").rows[rId];
		          var count = 2*(r.cells.length);
				    for(i=0;i<=count;i++){
				        r.deleteCell(0);
				    } 
				 
			}
		$('body').on('submit', '#docrejectedReason_form', function(e) {
			  if ($(this).validate(false, validationSettings)) {
		      	e.preventDefault();
		     
		     	var frm = $('#docrejectedReason_form').serializeFormJSON();
		      	var con = JSON.stringify(frm);
		      	con = con.replace(/[{}]/g, "");
		      
		      	//It will escape all the special characters
		      	var jsonData = encodeURIComponent(con);
		      	var rejectedDocsCandidateId = document.getElementById("rejectedDocsCandidateId").value;
		      
		      	var documentsIds = [];
			 	$("input[name='documents[]']:checked").each( function (i) {
			 		documentsIds[i] = $(this).val();
			 	});
			 	 
		       	$("#submit_rejectedReason").attr("disabled", "disabled");
		       	$("#docsrejectedLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		  	   	$.ajax({
			    	type: "POST",
			    	url: atsUrl+"/rejectRequestedDocuments?"+tokenParamName+"="+tokenValue+"&json="+jsonData+"&documentsIds="+documentsIds, 
			        success: function(response) {
			        	if (response && response.response === "INVALID_DATA") {
			        	    showError(response.responseMessage);
			        	} else if (response.response === "AWKWARD") {
			        	    location.href = "/errorPage";
			        	} else if (response.response === "SUCCESS") {
			        		cancelDocumentRejectedReason();
				        	$("#docsrejectedLoading").removeAttr("style"); 
				        	candidateDocuments(rejectedDocsCandidateId);
			        	} else if (response.indexOf('Sign In') !== -1) {
			        		location.replace(sourceleadUrl);
			        	} else {
			        	    showError("Operation failed, please try again");
			        	}
			        },
			    });    
		      return true; 
		    }
		    else {
		    	return false;	
			}
		});

		$('body').on('blur', '#docrejectedReason_form', function() {
			$("#docrejectedReason_form").showHelpOnFocus();
			$("#docrejectedReason_form").validateOnBlur(false, validationSettings);
		});
		$(document).ready(function() {
		    $(".glyphicon-edit").click(function(){
				$(".settPop").slideDown(200);
				$(".vendor_wrap_mask").fadeIn(200);
			});
			$(".settPop i, .btn-default").click(function(){
				$(".settPop").slideUp(200);
				$(".vendor_wrap_mask").fadeOut(200);
			});
		});		
			
			function clearUpload(){
				$("#uploadFile").val("");
			}
//6.candidateDocuments.jsp END HRE

//7. candidateReferences.jsp STARTS HERE.
			function candidateReferences(candidateId) {
				var candidateType = '${candidateType}';
				var result = "";

				if (window.candidateStatusGlobal == '0') {

					result = result
							+ '    <div class="project_tabs_inn_head addUser-org overflow_inherint mt25">  ';

				} else {
					result = result
							+ '    <div class="project_tabs_inn_head addUser-org overflow_inherint mt10">  ';
				}
				result = result
						+ '<div class="success_pop" id="selector10" style="display: none;">';
				result = result
						+ '<i class="fa fa-check-circle"></i><span id="refMsgDiv"></span>';
				result = result + '</div>';
				if (window.candidateStatusGlobal == '0') {
					if (candidateType == "Candidates") {
						'<sec:authorize access="hasAnyRole(\'ROLE_ADD_CANDIDATE_REFERENCE\',\'ROLE_CROWDSOURCING_CANDIDATES\')">';
						result = result
								+ '<a href="javascript:void(0)"  class="addUser-org" ></a> ';
						'</sec:authorize>';
					} else {
						'<sec:authorize access="hasAnyRole(\'ROLE_ADD_CANDIDATE_REFERENCE\')">';
						result = result
								+ '<a href="javascript:void(0)"  class="addUser-org" ></a> ';
						'</sec:authorize>';
					}

				} else {
					if (candidateType == "Candidates") {
						'<sec:authorize access="hasAnyRole(\'ROLE_ADD_CANDIDATE_REFERENCE\',\'ROLE_CROWDSOURCING_CANDIDATES\')">';
						result = result + '<span class="pad-sea"><a href="javascript:void(0)"  onClick="addReference('+ candidateId + ')" class="addUser-org" ><i class="glyphicon glyphicon-plus"></i>Add Reference</a></span>';
						'</sec:authorize>';
						
						'<sec:authorize access="!hasAnyRole(\'ROLE_ADD_CANDIDATE_REFERENCE\')">';
						result = result + '<span class="pad-sea"><a href="javascript:void(0)"  class="addUser-org" ></a></span>';
						'</sec:authorize>';
					} else {
						'<sec:authorize access="hasAnyRole(\'ROLE_ADD_ASSOCIATE_REFERENCE\')">';
						result = result + '<span class="pad-sea"><a href="javascript:void(0)"  onClick="addReference('+ candidateId + ')" class="addUser-org" ><i class="glyphicon glyphicon-plus"></i>Add Reference</a></span>';
						'</sec:authorize>';
						
						'<sec:authorize access="!hasAnyRole(\'ROLE_ADD_ASSOCIATE_REFERENCE\')">';
						result = result + '<span class="pad-sea"><a href="javascript:void(0)"   class="addUser-org" ></a></span>';
						'</sec:authorize>';
					}

				}
				result = result
						+ '     <div id="popUpDiv" class="edit-details col-md-4"> ';
				result = result + '     </div>  ';
				result = result
						+ '   <div class="project_tabs_inn_table vendor_wrap_inn_table datatable_filter">';

				result = result + '   <div id="candidateReferencesList" ></div>';
				result = result + '   </div>';
				result = result + '	    </div>';

				$("#tab-1").empty();
				$("#tab-2").empty();
				$("#tab-3").empty();
				$("#tab-4").empty();
				$("#tab-5").empty();
				$("#tab-6").empty();
				$("#tab-7").empty();
				$("#tab-8").empty();
				$("#tab-9").empty();
				$("#tab-10").empty();
				$("#tab-10").append(result);
				candReferencesList(candidateId);
			}
			function candReferencesList(candidateId) {
				var candidateType = '${candidateType}';
				var locationCode = '${SourceLeadWeb_locationCode}';

				$
						.ajax({
							type : "POST",
							url : atsUrl
									+ "/getCandidateReferencesListByCandidateId?"+tokenParamName+"="+tokenValue+"&candidateId="
									+ candidateId + "&locationCode=" + locationCode,
							success : function(json) {

								/*  var json = JSON.parse(data);  */
								$("#candidateReferencesList").empty();
								var result = "";
								result = result
										+ "<table id='candidateReferencesPagination' class='display' cellspacing='0' width='100%' >";
								result = result + "<thead>";
								result = result + "  <tr>";
								result = result + '<th>Name </th>';
								result = result + '<th>Email</th>';
								result = result + '<th>Phone</th>';
								result = result + '<th>Relation </th>';
								result = result + '<th>Designation </th>';
								result = result + '<th>Comment </th>';
								result = result + '<th>Action </th>';
								result = result + "  </tr>";
								result = result + "</thead>";
								result = result + "<tbody>";

								for (var i = 0; i < json.data.length; i++) {
									var opt = json.data[i];
									result = result + ' <tr>';
									if (opt.name != null && opt.name != ''
											&& opt.name != "undefined") {
										result = result
												+ "  <td><div class='tbldata-dotdot-me' title='"+opt.name+"'>"
												+ opt.name + "</div></td>";
									} else {
										result = result + "  <td><div>----</div></td>";
									}

									if (opt.email != null && opt.email != ''
											&& opt.email != 'undefined') {
										result = result
												+ ' <td><div class="tbldata-dotdot-me" title="'+opt.email+'">'
												+ opt.email + '</div></td>';
									} else {
										result = result
												+ ' <td><div><span>----</span></div></td>';
									}

									if (opt.mobilePhone != null
											&& opt.mobilePhone != ''
											&& opt.mobilePhone != 'undefined') {
										result = result
												+ ' <td><div><span title="'+opt.mobilePhone+'">'
												+ opt.mobilePhone
												+ '</span></div></td>';
									} else {
										result = result
												+ ' <td><div><span>----</span></div></td>';
									}

									if (opt.relation != null && opt.relation != ''
											&& opt.relation != 'undefined') {
										result = result
												+ ' <td><div><span title="'+opt.relation+'">'
												+ opt.relation + '</span></div></td>';
									} else {
										result = result
												+ ' <td><div><span>----</span></div></td>';
									}
									if (opt.designation != null && opt.designation != ''&& opt.designation != 'undefined') {
									result = result	+ ' <td><div><span title="'+opt.designation+'">'+ opt.designation + '</span></div></td>';
								   } else {
									result = result	+ ' <td><div><span>----</span></div></td>';
								   }

									if (opt.comment != null && opt.comment != ''
											&& opt.comment != 'undefined') {
										result = result
												+ ' <td><div><span title="'+opt.comment+'">'
												+ opt.comment + '</span></div></td>';
									} else {
										result = result
												+ ' <td><div><span>----</span></div></td>';
									}

									result = result + '<td><div class="tblicons">';
									if (window.candidateStatusGlobal == 0) {
										result = result
												+ '<a class="glyphicon glyphicon-pencil" href="javascript:void(0)"></a>';
										result = result
												+ '<i class="glyphicon glyphicon-trash" href="javascript:void(0)"></i>';
									} else {
										result = result
												+ ' <sec:authorize access="hasAnyRole(\'ROLE_VIEW_AND_EDIT_CANDIDATE_REFERENCE\')"><a class="glyphicon glyphicon-pencil" href="javascript:void(0)" onClick="editCandidateReference('
												+ candidateId
												+ ','
												+ opt.id
												+ ');editRelation(this);" title="Edit"></a></sec:authorize>';
										result = result
												+ '<sec:authorize access="hasAnyRole(\'ROLE_DELETE_CANDIDATE_REFERENCE\')"><i class="glyphicon glyphicon-trash" onclick="removeReference('
												+ candidateId
												+ ','
												+ opt.id
												+ ');" title= "Remove"></i></sec:authorize>';
									}

								}
								result = result + '</div></td>';
								result = result + '</tr>';

								result = result + " </tbody>";
								result = result + "</table>";
								$("#candidateReferencesList").append(result);
								candidateReferencesPagination();
							}
						});
			}
/*		<%-- 
		     @author : prasadK.
		     this script is for get candidateReferencesList.
		     1.usage     : used in candidateReferences().
		     2.parameter : csrf token,candidateId .
		     3.ajax url implementation in CandidateListRestController.
		--%>*/
			function candidateReferencesList(candidateId) {
				var sourceleadUrl = '${sourceleadUrl}';

			}
/*
		<%-- 
		     @author : Prasad K.
		     this script is for prepare pagination.
		     1.usage     : used in candidateReferencesList().
		--%>*/

			function candidateReferencesPagination() {
				$('#candidateReferencesPagination').DataTable({
					"scrollCollapse" : false,
					"lengthMenu" : [ [ 10, 15, 20, -1 ], [ 10, 15, 20, "All" ] ],
					"bLengthChange" : true,
					"bDestroy" : true,
					"ordering" : true,
					"aoColumnDefs" : [ {
						'bSortable' : false,
						'aTargets' : 5
					} ],
					"oLanguage" : {
						"sSearch" : "",
						"sZeroRecords" : "",
						"sEmptyTable" : ""
					}
				});
			}
/*
		<%-- 
		     @author : Prasad K.
		     this script is for remove popUp.
		     1.usage   : used in addReference() function.
		--%>*/

			function closePopup() {
				$("#popUpDiv").removeClass("edit-details-active");
				$(".location-alert").fadeOut();
			}
			$(function() {
				$("#relation").change(function() {
					$('#row_dim').hide();
					if (this.options[this.selectedIndex].value == "Work Related") {
						$("#row_dim").show();
					}
				});
			});

/*		<%-- 
		     @author : prasad K.
		     this script is for shown popUp.
		     1.usage   : used in addReference() function.
		--%>*/
			function addReference(candidateId) {
				var result = "";
				result = result + '<!-- Popup Div -->';
				result = result
						+ '<form role="form"  id="canReference_form" name="canReference_form" method="post" > ';
				result = result
						+ '<i class="fa fa-close close-div"  onClick="closePopup();"></i> ';
				result = result + '<div class="form-horizontal clearfix"> ';
				result = result + '   ';

				result = result + '<div class="clearfix mb">';
				result = result + '<div class="col-md-12">';
				result = result
						+ '<input type="text" name="name" field-name="Name" data-validation="required validate_Space validate_length length1-100" class="form-control input-sm" value="" placeholder="Name *">';
				result = result + '</div>';
				result = result + '</div>';

				result = result + '<div class="clearfix mb">';
				result = result + '<div class="col-md-12" id="emailDivId">';
				
				result = result + '<div class="input-group errorInptgroup">';
				result = result+ '<input type="text" name="email" field-name="Email" id="email" data-validation="required validate_Space validate_email validate_length length9-100" class="form-control input-sm" value="" placeholder="Email *" onblur="validateReferenceEmail('+candidateId+');">';
				result = result + ' <span id="emailStatusIconDiv" class="input-group-addon">';
				result = result + '<i class="fa fa-check text-muted" style="color:#f6f6f6">';
				result = result + '</i>';
				result = result + '</span>';
		      	result = result + '</div>';
				
				result = result + '</div>';
				result = result + '</div>';

				result = result + '<div class="clearfix mb">';
				result = result + '<div class="col-md-12">';
				result = result
						+ '<input type="text" name="mobilePhone" field-name="MobilePhone" data-validation="validate_length length9-20 validate_phone" data-validation-optional="true" class="form-control input-sm" value="" placeholder="Mobile Phone">';
				result = result + '</div>';
				result = result + '</div>';

				result = result + '<div class="clearfix mb">';
				result = result + '<div class="col-md-12">';
				result = result
						+ '<select class="form-control width100percnt sel" id="relation" name="relation" field-name="Relation" onChange="getRelation();">';
				result = result + '<option value="">--Select Relation--</option>';
				result = result
						+ ' <option id="workRelated" valeu="Work Related">Work Related</option>';
				result = result
						+ '<option  id="familyRelated" value="Family Related">Family Related</option>';
				result = result
						+ '<option  id="friend" value="Friend">Friend</option>';
				result = result
						+ '<option  id="neighbour" value="Neighbour">Neighbour</option>';
				result = result + ' </select>';
				result = result + '</div>';
				result = result + '</div>';

				result = result
						+ '<div class="clearfix mb" id="dvDesignation" style="display: none;">';
				result = result + '<div class="col-md-12" >';
				result = result
						+ '<input type="text" name="designation" id="designationVal" field-name="Designation"  class="form-control input-sm" value="" placeholder="Designation *">';
				result = result + '</div>';
				result = result + '</div>';

				result = result + '<div class="clearfix mb">';
				result = result + '<div class="col-md-12">';
				result = result
						+ '<textarea rows="2" placeholder="Comment" value="" id="comment" name="comment" data-validation-optional="true" data-validation="validate_length length1-500"  field-name="comment" class="form-control"></textarea>';
				result = result + '</div>';
				result = result + '</div>';

				result = result
						+ '<div class="clearfix mr mt" id="docUpdateButton" style="display: none;">';
				result = result
						+ '<button type="button" id="update_doc" class="btn btn-sm btn-primary pull-right">Update</button>';
				result = result + '</div>';

				result = result + '<div class="clearfix mr" id="docAddButton">';
				result = result
						+ '<input type="button" value="Save" name="btnAddPr" id="save_ref" class="btn btn-sm btn-primary pull-right" onClick ="storeCandidateReferences('
						+ candidateId + ');" />';
				result = result + '</div>';
				result = result + '</div>';
				result = result + '</form>';
				$("#popUpDiv").empty();
				$("#popUpDiv").append(result);

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

/*		<%-- 
		     @author : prasad K
		     this script is for saving document
		     1.usage     : onclick
		     2.parameter : candidateId.
		     3.ajax url implementation in CandidateReferencesRestController.
		--%>*/
			function storeCandidateReferences(candidateId) {
				var validationSettings = {
					errorMessagePosition : 'element',
					borderColorOnError : '',
					scrollToTopOnError : true,
					dateFormat : 'yyyy/mm/dd'
				};
				if ($('#canReference_form').validate(false, validationSettings)) {
					var formData = getFormData("canReference_form");
					var userId = '${SourceLeadWeb_userId}';
					var locationCode = '${SourceLeadWeb_locationCode}';
					$("#save_ref").attr("disabled", "disabled");
					$("#save_ref").addClass('button').val('Processing..'); 
				    $.post(atsUrl+"/saveCandidateReferences?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId+"&userId="+userId+"&locationCode="+locationCode, formData, function(result) {
				    	if (result && (result.response === "INVALID_USER" || result.response === "INVALID_DATA")) {
						    showError(result.responseMessage);
						} else if (result.response === "AWKWARD") {
						    location.href = "/errorPage";
						} else if (result.response === "SUCCESS") {
							closePopup();
					        $("#popUpDiv").removeClass("edit-details-active");
							$("#selector10").removeAttr("style");
							$("#selector10").css({display : "block"	});
							$("#refMsgDiv").html("Success!! New References added");
					        candReferencesList(candidateId);
							refMsgFadein(); 
							setTimeout(function(){
						 		candidateList();					 			
						 	},100);
						} else if (result.indexOf('Sign In') !== -1) {
							location.replace(sourceleadUrl);
						} else {
						    showError("Operation failed, please try again");
						}
				    });
					return true;
				} else {
					return false;
				}
			}
			$('body').on('blur', '#canReference_form', function() {
				$("#canReference_form").showHelpOnFocus();
				$("#canReference_form").validateOnBlur(false, validationSettings);
			});
/*
		<%-- 
		     @author : Prasad K
		     this script is for get document details in edit mode
		     1.usage     : onclick
		     2.parameter : candidateId, documentId
		     3.ajax url implementation in CandidateResumeRestController.
		--%>*/
			function deleteReferencesPopup() {
				$("#remove_user_pop_reference, .project_inn_mask").fadeOut();
			}
			function refMsgFadein() {
				$("#selector10").fadeIn(200);
				$('#selector10').delay(2000).fadeOut('slow');
			}
			function removeReference(candidateId, refId) {

				var result = "";
				result = result + "	<div class='project_inn_mask'></div>";
				result = result
						+ "	   <div id='remove_user_pop_reference' class='remove_user_pop remove_user_pop_a'>";
				result = result + "		<p>You are choosing to delete the Reference</p>";
				result = result + "		<div>&nbsp;&nbsp;&nbsp;</div>";
				result = result
						+ "<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='deleteReference("
						+ candidateId
						+ ","
						+ refId
						+ ");'><input class='u_can_color' type='button'  value='Cancel' onClick='deleteReferencesPopup();'></b>";
				result = result + "	</div>";
				$("#deleteReferencesPopup").empty();
				$("#deleteReferencesPopup").append(result);
				$("#remove_user_pop_reference").slideDown(200);
				$(".project_inn_mask").fadeIn(200);

			}
/*		<%-- 
		     @author : prasad K
		     this script is for terminate References (inactive status)
		     1.usage     : onclick
		     2.parameter : candidateId, documentId
		     3.ajax url implementation in CandidateReferencesRestController.
		--%>*/
			function deleteReference(candidateId, refId) {
				var loginUserId = '${SourceLeadWeb_userId}';
				var orgLocationId = '${SourceLeadWeb_orgLocationId}';
				//var message = confirm("You are choosing to delete the reference");
				var userCode = '${SourceLeadWeb_userCode}';
				$.ajax({
					type : "POST",
					url : atsUrl
							+ "/terminateReference?"+tokenParamName+"="+tokenValue+"&loginUserId="+loginUserId+"&orgLocationId="+orgLocationId+"&candidateId="
							+ candidateId + "&refId=" + refId + "&userCode="
							+ userCode,
					success : function(result) {
						if (result && (result.response === "INVALID_DATA" || result.response === "INVALID_USER")) {
			    		    showError(result.responseMessage);
			    		} else if (result.response === "AWKWARD") {
			    		    location.href = "/errorPage";
			    		} else if (result.response === "SUCCESS") {
			    			$("#selector10").removeAttr("style");
							$("#selector10").css({display : "block"});
							$("#refMsgDiv").html("Reference deleted");
							$("#popUpDiv").removeClass("edit-details-active");
							$("#remove_user_pop_reference, .project_inn_mask").fadeOut();
							candReferencesList(candidateId);
							refMsgFadein();
							setTimeout(function(){
								candidateList();					 			
							},100);	
			    		} else if (result.indexOf('Sign In') !== -1) {
							location.replace(sourceleadUrl);
						} else {
						    showError("Operation failed, please try again");
						}
					},
				});
			}
			function updateCandidateReference(candidateId, refId) {
				var validationSettings = {
					errorMessagePosition : 'element',
					borderColorOnError : '',
					scrollToTopOnError : true,
					dateFormat : 'yyyy/mm/dd'
				};
				if ($('#canReferenceEdit_form').validate(false, validationSettings)) {
					var formData = getFormData("canReferenceEdit_form");
				    var userId = '${SourceLeadWeb_user.id}';
				    var locationCode = '${SourceLeadWeb_locationCode}';
					$("#save_ref").attr("disabled", "disabled");
					$("#save_ref").addClass('button').val('Processing..'); 
			        $.post(atsUrl+"/updateCandidateReferenceBycandidateIdAndReferenceId?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId+"&userId="+userId+"&locationCode="+locationCode, formData, function(result) {
			        	if (result && (result.response === "INVALID_DATA" || result.response === "INVALID_USER")) {
			    		    showError(result.responseMessage);
			    		} else if (result.response === "AWKWARD") {
			    		    location.href = "/errorPage";
			    		} else if (result.response === "SUCCESS") {
			    			$("#selector10").removeAttr("style");
							$("#selector10").css({display : "block"});
							$("#refMsgDiv").html("Reference details updated");
							closePopup();
							candReferencesList(candidateId);
							refMsgFadein();
			    		} else if (result.indexOf('Sign In') !== -1) {
							location.replace(sourceleadUrl);
						} else {
						    showError("Operation failed, please try again");
						}
			        });
					return true;
				} else {
					return false;
				}
				$('body').on('blur', '#canReference_form', function() {
					$("#canReferenceEdit_form").showHelpOnFocus();
					$("#canReferenceEdit_form").validateOnBlur(false, validationSettings);
				});
			}
			function editCandidateReference(candidateId, refId) {
				$.ajax({
						type : "POST",
						url : atsUrl
							+ "/getCandidateReferencesByCandidateIdAndReferenceId?"+tokenParamName+"="+tokenValue+"&candidateId="
							+ candidateId + "&refId=" + refId,
						success : function(response) {
							if (response.response === "AWKWARD") {
							    location.href = "/errorPage";
							} else if (response.response === "SUCCESS") {
								var json = response.object;
								var result = '';
								result = result
										+ '     <!-- Popup Div -->                                                                                         ';
								result = result
										+ '         <form id="canReferenceEdit_form" name="canReferenceEdit_form" method="post">        ';
								result = result
										+ '<i class="fa fa-close close-div"  onClick="closePopup();"></i> ';
								result = result
										+ '<div class="form-horizontal clearfix"> ';
								result = result + '   ';

								result = result
										+ '<div class="clearfix mb10">';
								result = result
										+ '<div class="col-md-12">';
								result = result
										+ '<input type="text" name="name" field-name="Name" value="'+json.name+'" data-validation="required validate_Space validate_length length2-100" class="form-control input-sm" value="" placeholder="Name *">';
								result = result + '</div>';
								result = result + '</div>';

								
								result = result+ '<div class="clearfix mb10">';
								result = result+ '<div class="col-md-12" id="emailDivId">';
								result = result + '<div class="input-group errorInptgroup">';
								result = result+ '<input type="text" name="email" id="email" field-name="Email" value="'+json.email+'" data-validation="required validate_Space validate_email validate_length length9-100" class="form-control input-sm" value="" placeholder="Email *" onblur="validateReferenceEmail('+candidateId+','+json.id+');">';
								result = result + ' <span id="emailStatusIconDiv" class="input-group-addon">';
								result = result + '<i class="fa fa-check text-muted" style="color:#f6f6f6">';
								result = result + '</i>';
								result = result + '</span>';
								result = result + '</div>';
								result = result + '</div>';
								result = result + '</div>';

								
								
								
								result = result
										+ '<div class="clearfix mb10">';
								result = result
										+ '<div class="col-md-12">';
								result = result
										+ '<input type="text" name="mobilePhone" field-name="MobilePhone" value="'+json.mobilePhone+'" data-validation="validate_length length9-20 validate_phone" data-validation-optional="true" class="form-control input-sm" value="" placeholder="Mobile Phone">';
								result = result + '</div>';
								result = result + '</div>';

								result = result
										+ '<div class="clearfix mb10">';
								result = result
										+ '<div class="col-md-12">';
								result = result
										+ '<select class="form-control width100percnt" id="relation" name="relation" field-name="Relation" onChange="editRelation(this);" onkeyup="editRelation(this);">';
								result = result
										+ '<option value="">--Select Relation--</option>';
								result = result
										+ ' <option '
										+ (json.relation == "Work Related" ? "selected"
												: '')
										+ '>Work Related</option>';
								result = result
										+ '<option  '
										+ (json.relation == "Family Related" ? "selected"
												: '')
										+ '>Family Related</option>';

								result = result
										+ '<option  '
										+ (json.relation == "Friend" ? "selected"
												: '')
										+ '>Friend</option>';
										
								result = result
										+ '<option  '
										+ (json.relation == "Neighbour" ? "selected"
												: '')
										+ '>Neighbour</option>';
								result = result + ' </select>';
								result = result + '</div>';
								result = result + '</div>';

								result = result
										+ '<div class="clearfix mb10" id="dvDesignationEdit" style="display:none">';
								result = result
										+ '<div class="col-md-12">';
								result = result
										+ '<input type="text" name="designation" id="designationValEdit" field-name="Designation" value="'+json.designation+'" data-validation="required validate_Space validate_length length2-100" class="form-control input-sm" placeholder="Designation *">';
								result = result + '</div>';
								result = result + '</div>';

								result = result
										+ '<div class="clearfix mb10 mt25">';
								result = result
										+ '<div class="col-md-12">';
								result = result
										+ '<textarea rows="2" placeholder="Comment" value="+json.comment" id="comment" name="comment" data-validation-optional="true" data-validation="validate_length length1-500"  field-name="comment" class="form-control">'
										+ json.comment + '</textarea>';
								result = result + '</div>';
								result = result + '</div>';

								result = result
										+ '<div class="clearfix mr mt" id="docUpdateButton" style="display: none;">';
								result = result
										+ '<button type="button" id="update_doc" class="btn btn-sm btn-primary pull-right">Update</button>';
								result = result + '</div>';

								result = result
										+ '<div class="clearfix mr" id="docAddButton">';
								result = result
										+ '<input type="hidden"  value="'+refId+'" id="refId" name="refId">';
								result = result
										+ '<input type="hidden"  value="'+candidateId+'" id="candidateId" name="candidateId">';

								result = result
										+ '<button type="button"  id="addCandidate"  onClick ="updateCandidateReference('
										+ candidateId
										+ ','
										+ refId
										+ ');" class="btn btn-sm btn-primary pull-right"  >Update</button>';

								result = result + '</div>';
								result = result + '</div>';
								result = result + '</form>';
								$("#popUpDiv").empty();
								if(json.relation == "Work Related"){
									$("#dvDesignationEdit").show();
								  }
								$("#popUpDiv").append(result);

								$(".edit-details").toggleClass(
										"edit-details-active");
								$(".close-div")
										.click(
												function() {
													$(this)
															.parent(
																	"div.edit-details")
															.removeClass(
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
					}
				}).done(function() {
					editRelation(this);
				});
			}
		function validateReferenceEmail(candidateId,referId){
			if(referId != null && referId != "" && referId != "undefined"){
				referId = referId;
			}else{
				referId = "";
			}
			var email = $("#email").val();
			 $('#emailDivId').find('p.jquery_form_error_message').remove();
			 if (email != null && email != "") {
			$.ajax({
					type : "POST",
					url : atsUrl + "/validateReferEmail?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId+"&referId="+referId+"&email="+email,
					success : function(response) {
						if (response.response === "AWKWARD") {
						    location.href = "/errorPage";
						} else if (response.response === "SUCCESS") {
							if(response.object == true){
				        		  $('#emailDivId').find('p.jquery_form_error_message').remove();
								  $("input#email").after("<p class='jquery_form_error_message'>"+email+" is Already Existed</span>");
								  document.getElementById("email").setAttribute('record-exist','yes');
								  document.getElementById("email").setAttribute('record-exist-errorMsg',email+' is Already Existed'); 
				        	} else {
				        		 $('input#email').removeAttr( "record-exist record-exist-errormsg");
				        		 $('#emailDivId').find('p.jquery_form_error_message').remove(); 
				        		 validateEmailForExistenceUniversally(email, "input#email");
				        	}
						} else if (response.indexOf('Sign In') !== -1) {
							location.replace(sourceleadUrl);
						} else {
						    showError("Operation failed, please try again");
						}
					},
				});
			 }else{
				 $("#emailStatusIconDiv").find("i").remove();
		   		 $("#emailStatusIconDiv").append('<i class="fa fa-check text-muted" style="color:#f6f6f6"></i>');
			 }
		}

	//	<!-- If Selected value is Work Related then Designation text box will appear -->
			function getRelation() {
				var relation = $("#relation").val();
				if (relation == 'Work Related') {
					$('#designationVal').val('');
					$('#designationVal').attr("data-validation","required validate_Space validate_length length2-100");
					$("#dvDesignation").show();
				} else{
					$('#designationVal').val('');
					$("#designationVal").attr("data-validation-optional", "true");
					$("#dvDesignation").hide();
				}
			}
			function editRelation(e) {
				var relationType = $("#relation").val();
				if (relationType == 'Work Related') {
					document.getElementById("dvDesignationEdit").style.display = "block";
					$("#designationValEdit").removeAttr("data-validation-optional");
					$('#designationValEdit').attr("data-validation",
							"required validate_Space validate_length length2-100"); 
					$("#designationValEdit").show();
				} else {
					document.getElementById("dvDesignationEdit").style.display = "none";
					$("#designationValEdit").val('');
					$('#designationValEdit').find('p.jquery_form_error_message').remove();
					$('#designationValEdit').removeAttr("data-validation");
				} 
			}
		
			
//7. candidateReferences.jsp END HERE.			

//8. requirementHistory.jsp STARTS HERE.	
			function requirementHistory(candidateId){
				var result = "";
				if(window.candidateStatusGlobal == '0'){
					result = result  +'    <div class="project_tabs_inn_head addUser-org overflow_inherint mt25">  ';
				}else{
					result = result  +'    <div class="project_tabs_inn_head addUser-org overflow_inherint mt10">  ';
				}
				result = result  +'    <div class="project_tabs_inn_head addUser-org">  ';
				
				/* result  = result + '<div class="success_pop" id="selector8" style="display: none;">'; */
					result = result  +'<span class="pad-sea"></span>';
					result  = result + '   <div class="project_tabs_inn_table vendor_wrap_inn_table">';
					result  = result + '   <div class="reqPop" id="requirementHistoryPopDiv" ></div>';
					result  = result + '   <div id="requirementHistoryDiv" class="datatable_filter"></div>';
			   /* result  = result + '   </div>'; */
			   
				result = result+'	    </div>';
				$("#tab-1").empty();
				$("#tab-2").empty();
				$("#tab-3").empty();
				$("#tab-4").empty();
				$("#tab-5").empty();
				$("#tab-6").empty();
				$("#tab-7").empty();
				$("#tab-9").empty();
				$("#tab-10").empty();
				$("#tab-8").empty();
			    $("#tab-8").append(result);  
				
			    requirementHistoryList(candidateId);
			}
			function requirementHistoryList(candidateId){
				
				var locationList ='${SourceLeadWeb_dateFormat}';	
				  if(locationList.match("yyyy-MM-dd")){
					  canHisDatePattern = 'YYYY-MM-DD';
				  }if(locationList.match("dd-MM-yyyy")){
					  canHisDatePattern = 'DD-MM-YYYY'; 
				  }if(locationList.match("MM-dd-yyyy")){
					  canHisDatePattern = 'MM-DD-YYYY'; 
				  }if(locationList.match("yyyy-dd-MM")){
					  canHisDatePattern = 'YYYY-DD-MM'; 
				  }
				 $.ajax({  
			         type : "POST",
			         url: atsUrl+"/getRequirementHistoryListByCandidateId?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId,        
			         success :function(response) {
			        	 if (response.response === "AWKWARD") {
			        		    location.href = "/errorPage";
			        		} else if (response.response === "SUCCESS") {
			        			var json = response.data;
			               	 $("#requirementHistoryDiv").empty();
			               	 var result = "";
			            	    result = result  +'	      <table id="requirementHistoryPagination" class="display" cellspacing="0" width="100%">';
			            		result = result  +'	        <thead>';
			            		result = result  +'	          <tr>';
			            		result = result  +'	            <th>Title</th>';
			            		result = result  +'	            <th>Emp Type</th>';
			            		result = result  +'	            <th>Location</th>';
			            		result = result  +'	            <th>Rate</th>';
			            		result = result  +'	            <th>Owner</th>';
			            		result = result  +'	            <th>Client</th>';
			            		result = result  +'	            <th>Submitted On <br>(<small class="custom-small">'+canHisDatePattern+'</small>)</th>';
			            		result = result  +'	            <th>Stage</th>';
			            		result = result  +'	          </tr>';
			            		result = result  +'          </thead>';
			            		result = result  +'         <tbody> '; 
			            		for (var i = 0; i < json.length; i++) { 
			                  	 var report = json[i];  
			                  	 if(report.requirement.jobType != null && report.requirement.jobType != '' && report.requirement.jobType != 'undefined'){
			                  		jobType = report.requirement.jobType;
			                  	 }else{
			                  		jobType = "--";
			                  	 }
			                  	 if(report.rate != null && report.rate != '' && report.rate != 'undefined'){
			                  		rate = report.rate;
			                  	 }else{
			                  		rate = "--";
			                  	 }
			                  	 if(report.clientName != null && report.clientName != '' && report.clientName != 'undefined'){
			                   		clientName  = report.clientName;
			                   	 }else{
			                   		clientName = "--"
			                   	 }
			       	           	result = result  +'	          <tr>';
			       	    		result = result  +'	            <td><div><span class="mid-span ReqClick" onclick="requirementHistoryDetails('+candidateId+',\''+report.positionType+'\',\''+report.requirement.id+'\',\''+report.requirement.requirementUUID+'\',\''+report.id+'\');">'+report.positionType+'</span></div></td>';
			       	    		result = result  +'	            <td><div><span class="mid-span" title="'+jobType+'">'+jobType+'</span></div></td>';
			       	    		
			       	    		result = result +'<td>';
			       	    		result = result +'<div class="positionRelative">';
			         		         for(var k=0; k<1; k++){
			         		        	result = result + '<p class="tbldata-dotdot-sm pull-left">'+report.locationsList[k]+'</p>';
			         		         }
			       		    	
			         		        result = result +'<div class="show_Empdetails showjoblist">';
			       		        	result = result +'<i class="fa fa-ellipsis-v"></i>';

			       		        	result = result +'<div class="emailvalues emailvaluesNew">';
			       		        	
			       				for(var k=0; k<report.locationsList.length; k++){
			       					result = result +'<p><i class="fa fa-map-marker"></i>'+report.locationsList[k]+'</p>';
			          		        	
			       		    	 	}
			       		        	result = result +'</div>';
			       		        	result = result +'</div>';

			       	        	result = result +'</div>';
			       	        	result = result +'</td>';
			       	    		
			       	    		result = result  +'	            <td><div><span class="mid-span">'+rate+'</span></div></td>';
			       	    		result = result  +'	            <td><div><span class="mid-span" title="'+report.ownerName+'">'+report.ownerName+'</span></div></td>';
			       	    		result = result  +'	            <td><div><span class="mid-span" title="'+clientName+'">'+clientName+'</span></div></td>';
			       	    		result = result  +'	            <td><div><span class="mid-span">'+report.createdDateStr+'</span></div></td>';
			       	    		result = result  +'	            <td><div><span class="mid-span" title="'+report.submissionStatus+'">'+report.submissionStatus+'</span></div></td>';
			       	    		result = result  +'	          </tr>';
			                  	 
			            		}
			            		result = result  +'	        </tbody>';
			           		result = result  +'	      </table>';
			           	 
			           	 $("#requirementHistoryDiv").append(result);
			           	 requirementHistoryPagination();
			        		} else if (response.indexOf('Sign In') !== -1) {
			        			location.replace(sourceleadUrl);
			        		} else {
			        		    showError("Operation failed, please try again");
			        		}
			         }
				 });
			}

	//		<!-- requirementHistoryPagination pagination script -->
			function requirementHistoryPagination(){  
			    $('#requirementHistoryPagination').DataTable( { 
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
				function predefineProfileImage(imageId){   
					$(imageId).attr('src','/resources/images/userprofile-img.png');
				}
			function requirementHistoryDetails(candidateId,positionType,requirementId,requirementUUID,submissionId){
				var loginUserId = '${SourceLeadWeb_userId}';
				 $.ajax({  
			        type : "POST",
			        url: atsUrl+"/getRequirementHistoryDetailsByCandidateId?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId+"&requirementId="+requirementId+"&submissionId="+submissionId,        
			        success :function(response) {
			        	if (response.response === "AWKWARD") {
			        	    location.href = "/errorPage";
			        	} else if (response.response === "SUCCESS") {
			        		var json = response.data;
			            	var result = "";
			         		result = result  +'	<div class="reqPopInn reqHistTitle">';
			         		result = result  +'		<div class="reqClose clearfix">';
			         		result = result  +'			<span class="pull-left"><i class="fa fa-angle-double-left"></i>Go Back</span><b class="fa fa-close"></b>';
			         		result = result  +'		</div>';
			         		result = result  +'		<h3 title="'+positionType+'"><a href="'+atsUrl+'/requirementSummary?requirementUUID='+requirementUUID+'">'+positionType+'</a><span onclick="getRateListDiv('+candidateId+',\''+submissionId+'\');">Rate</span><i class="ratepop" id="ratepop"></i><div class="clearfix"></div></h3>';
			         		result = result  +'		<ul>';
			         		    var report2 = json[0];
			         		    if(report2.requirementOfferHistoryList != null && report2.requirementOfferHistoryList != "undefined" && report2.requirementOfferHistoryList.length > 0){
			         			   for(var k=0;k<report2.requirementOfferHistoryList.length;k++){
			    	          			var port = report2.requirementOfferHistoryList[k];
			    	          			if(port.offerSourceType == "CLIENT_OFFERMADE"){
			    	          				result = result  +'			<li>';
			    		            		result = result  +'	   <img onerror="predefineProfileImage(this);" src="'+sourceleadUrl+'/displayUserImage/'+loginUserId+'"/>';
			    		            		result = result  +'<span>Issued offer by '+port.clientName+' <b>'+port.createDateStr+'</b></span>';
			    		            		if(port.fileContentType != null && port.fileContentType != '' && port.fileContentType!= 'undefined'){
			    		            		result = result  +'<a href=\"'+atsUrl+'/offerDownloadDocument?resumeId='+port.offerResumeId+'\"><i class="fa fa-paperclip"></i></a><div class="clearfix"></div>';
			    		            		}
			    		            		result = result  +'<div class="clearfix"></div>';
			    		            		result = result  +'			</li>';
			    	          			}
			    	          			
			    	          		 }
			         		    }
			    	     		   
			         		   var report1 = json[0]; 
			         		   if(report1.requirementInterviewHistoryList != null && report1.requirementInterviewHistoryList != "undefined" && report1.requirementInterviewHistoryList.length > 0){
			    		     	   if(report1.requirementInterviewHistoryList.length > 0){
			    		     		   for(var j=0;j<report1.requirementInterviewHistoryList.length;j++){
			    		          			var sub = report1.requirementInterviewHistoryList[j];
			    		          			if(sub.endTimeStr != null && sub.endTimeStr != '' && sub.endTimeStr != 'undefined'){
			    		          				endTimeStr = sub.endTimeStr;
			    		          			}else{
			    		          				endTimeStr = "";
			    		          			}
			    		          			if(sub.interviewSourceType == "CLIENT_INTERVIEW"){
			    		          				result = result  +'			<li>';
			    			            		result = result  +'	   <img onerror="predefineProfileImage(this);" src="'+sourceleadUrl+'/displayUserImage/'+loginUserId+'"/>';
			    			            		result = result  +'<span>Interview scheduled by '+sub.clientName+' <b>'+sub.interviewHistoryCreatedDate+'</b> <h6>Interview Type : '+sub.interviewType+'</h6> <h6>Interview Date : '+sub.dateOfInterviewStr+'</h6><h6>Interview Time : '+sub.startTimeStr+' : '+endTimeStr+'</h6></span>';
			    			            		result = result  +'<div class="clearfix"></div>';
			    			            		result = result  +'			</li>';
			    		          			}
			    		          			
			    		          		 }
			    		     	   }
			         		   }
			         
			    	        	 for (var i=0; i<json.length; i++)
			    					{  
			    		        		var report = json[i];
			    		        		if(report.interviewEndTime != null && report.interviewEndTime != '' && report.interviewEndTime != 'undefined'){
			    		        			interviewEndTime = report.interviewEndTime;
			    		        		}else{
			    		        			interviewEndTime = "";
			    		        		}
			    		        		result = result  +'			<li>';
			    		        		result = result  +'	   <img onerror="predefineProfileImage(this);" src="'+sourceleadUrl+'/displayUserImage/'+loginUserId+'"/>';
			    		        		if(report.toStage == "SUBMISSION"){
			    			        		 result = result  +'<span>Added by  '+report.loginUserName+' <b>'+report.createdDateStr+'</b></span>';
			    		        			 result = result  +'<a href=\"'+report.resumeid+'\" download><i class="fa fa-paperclip"></i></a><a href="javascript:void(0)" class="fa fa-eye ml10" onClick="viewFile(\''+report.fileUrl+'\',\''+report.fileName+'\',\''+report.contentType+'\');"></a><div class="clearfix"></div>';
			     		        		}else if(report.toStage == "OFFERMADE"){
			    		        			result = result  +'<span>Issued offer by '+report.loginUserName+' <b>'+report.createdDateStr+'</b></span>';
			    		        			result = result  +'<a  href=\"'+report.fileUrl+'\" download><i class="fa fa-paperclip"></i></a><div class="clearfix"></div>';
			    		        		}else if(report.toStage == "INTERVIEW"){
			    		        			result = result  +'<span>'+report.loginUserName+' Scheduled interview <b>'+report.createdDateStr+'</b><h6>Interview Type : '+report.interviewType+'</h6><h6>Interview Date : '+report.interviewDateStr+'</h6><h6>Interview Time : '+report.interviewStartTime+' : '+interviewEndTime+'</h6> </span>';
			    		        		}else if(report.toStage == "SHORTLIST"){
			    		        			result = result  +'<span>Shortlisted by  '+report.loginUserName+' <b>'+report.createdDateStr+'</b></span>';
			    		        		} else if(report.toStage == "REQUEST_SENT"){
			    		        			result = result  +'<span>Added & Request Sent by  '+report.loginUserName+' <b>'+report.createdDateStr+'</b></span>';
			    		        			result = result  +'<a href=\"'+report.resumeid+'\" download><i class="fa fa-paperclip"></i></a><a class="fa fa-eye ml10" onClick="viewFile(\''+report.fileUrl+'\',\''+report.fileName+'\',\''+report.contentType+'\');"></a><div class="clearfix"></div>';
			    		        		} else if(report.toStage == "REJECTED"){
			    		        			 result = result  +'<span>Request Rejected By '+report.userName+' <b>'+report.createdDateStr+'</b><h7>Reject Reson : '+report.requirementReson+'</h7></span>';
			    		        		} else if(report.toStage == "SUBMISSION_ACCEPT"){
			    		        			result = result  +'<span>Request Accepted By '+report.userName+' <b>'+report.createdDateStr+'</b></span>';
			    		        		} else if(report.toStage == "OFFERACCEPTED"){
			    		        			result = result  +'<span>'+report.userName+' accepted offer <b>'+report.createdDateStr+'</b></span>';
			    		        		}else if(report.toStage == "OFFERREJECTED"){
			    		        			result = result  +'<span>'+report.userName+' rejected offer <b>'+report.createdDateStr+'</b></span>';
			    		        		}else if(report.toStage == "INTERVIEW_ACCEPT"){
			    		        			result = result  +'<span>'+report.userName+' accepted interview <b>'+report.createdDateStr+'</b></span>';
			    		        		}else if(report.toStage == "INTERVIEW_REJECT"){
			    		        			result = result  +'<span>'+report.userName+' rejected interview <b>'+report.createdDateStr+'</b></span>';
			    		        		}else if(report.toStage == "INTERVIEW_TENTATIVE"){
			    		        			result = result  +'<span>'+report.userName+' tentative interview <b>'+report.createdDateStr+'</b></span>';
			    		        		}else if(report.toStage == "FEEDBACK"){
			    		        			result = result  +'<span>Feedback by '+report.interviewerName+' <b>'+report.createdDateStr+'</b></span>';
			    		        		}else if(report.toStage == "SUBMIT_TO_CLIENT"){
			    		        			result = result  +'<span>Submit to Client '+report.loginUserName+' <b>'+report.createdDateStr+'</b></span>';
			    		        			result = result  +'<a href=\"'+report.resumeid+'\" download><i class="fa fa-paperclip"></i></a><a class="fa fa-eye ml10" onClick="viewFile(\''+report.fileUrl+'\',\''+report.fileName+'\',\''+report.contentType+'\');"></a><div class="clearfix"></div>';

			    		        		}else{
			    		        			result = result  +'<div class="clearfix"></div>';
			    		        		}
			    		        		result = result  +'			</li>';
			    		        		
			    					}
			            	 
			            	 result = result  +'		</ul>';
			         		 result = result  +'</div>';
			    	     	 $("#requirementHistoryPopDiv").empty();
			    	     	 $("#requirementHistoryPopDiv").append(result);
			    	     	 $(".reqPop").fadeIn(100);
			    	     	 $(".reqClose").click(function(){
			    	     			$(".reqPop").fadeOut(100);
			    	         });
			        	} else if (response.indexOf('Sign In') !== -1) {
			        		location.replace(sourceleadUrl);
			        	} else {
			        	    showError("Operation failed, please try again");
			        	}
			        }
				 }); 
			}

			function getHistoryCandiResume(uuid,title){
					if (uuid != null && uuid != "") {
						window.open(sourceleadUrl + '/viewResume/' + title + '?resumeUUID='+ uuid, title, "height=600,width=600");
					}
				}
			function getRateListDiv(candidateId,submissionId){
				  var orgLocationId = '${SourceLeadWeb_orgLocationId}';
				  var locationList ='${SourceLeadWeb_dateFormat}';	
				  if(locationList.match("yyyy-MM-dd")){
					  reqHistryRateListDate = 'YYYY-MM-DD';
				  }if(locationList.match("dd-MM-yyyy")){
					  reqHistryRateListDate = 'DD-MM-YYYY'; 
				  }if(locationList.match("MM-dd-yyyy")){
					  reqHistryRateListDate = 'MM-DD-YYYY'; 
				  }if(locationList.match("yyyy-dd-MM")){
					  reqHistryRateListDate = 'YYYY-DD-MM'; 
				  }
				  $.ajax({
			        type : "POST",
			         url: atsUrl+"/getRateHistoryBySubmissionId?"+tokenParamName+"="+tokenValue+"&submissionId="+submissionId+"&candidateId="+candidateId,        
			         success :function(data) {
			        	 if (data.response === "FAILED") {
					    		showError(data.responseMessage);
					    	} else if (data.response === "AWKWARD") {
					    		location.href = "/errorPage";
					    	} else if (data.response === "SUCCESS") {
					        	 if(data.data != null && data.data != 'undefined' && data.data != ""){
					            	 var json = data.data;
					          			     var result = "";
					      			         result = result + '<i class="fa fa-times close-ratepop" title="Close" onClick="closeratediv();"></i>';
					      			         result = result + '<p><strong>Rate Negotiation History</strong></p>';
					         			     result = result + '<section class="tbl-scroll require_wrap_table">';
						                     result = result + '  <table>';
						                     result = result + '    <thead>';
						                     result = result + '      <tr>';
						                     result = result + '        <th>By</th>';
						                     result = result + '        <th>Rate</th>';
						                     result = result + '        <th>Comment</th>';
						                     result = result + '        <th>Date <br>(<small class="custom-small">'+reqHistryRateListDate+'</small>)</th>';
						                     result = result + '        <th>Status</th>';
						                     result = result + '      </tr>';
						                     result = result + '    </thead>';
						                     result = result + '    <tbody>';
						                     var divState = "";
						                     var isLastNegotiator = "";
						                     for (var i=0; i<json.length; i++)
								   				{
										    	 var report = json[i];
										    	 if(i == json.length-1){
							                    	 divState = report.rateInitiatorType;
							                    	 if((report.rateInitiatorType != "ORG" && report.rateInitiatorType != "CANDIDATE") && (report.initiatorOrgLocationIdStr==orgLocationId)){
							                    		 isLastNegotiator = "true";
							                    	 }else{
							                    		 isLastNegotiator = "false";
							                    	 }
						                    	 }
							                     result = result + '<tr>';
							                     if(report.proposeByName != null && report.proposeByName != '' && report.proposeByName != 'undefined'){
							                     result = result + '<td><div class="tbldata-dotdot-sm">'+report.proposeByName+'</div></td>';
							                     }
							                     else{
							                    	 result = result + ' <td><div class="tbldata-dotdot-sm">----</div></td>'; 
							                     }
							                    
							                     if(report.rateAndType != null && report.rateAndType != '' && report.rateAndType != 'undefined'){
							                         result = result + '<td><div class="tblminwidth-75">'+report.rateAndType+'</div></td>';
							                     } 
							                     else{
							                    	 result = result + ' <td><div class="tblminwidth-75">----</div></td>'; 
							                     }
							                     
							                     if(report.rateReason != null && report.rateReason != '' && report.rateReason != 'undefined'){
							                         result = result + '<td><div class="tbldata-dotdot">'+report.rateReason+'</div></td>';
							                     }
							                     else{
							                         result = result + '<td><div class="tbldata-dotdot">----</div></td>';
							                     }
							                     
							                     if(report.createdDateStr != null && report.createdDateStr != '' && report.createdDateStr != 'undefined'){
							                         result = result + '<td><div class="tblminwidth-75">'+report.createdDateStr+'</div></td>';
							                     }
							                     else{
							                         result = result + '<td><div class="tblminwidth-75">----</div></td>'; 
							                     }
							                     
							                     if(report.rateStage == 'ACCEPTED'){
							                    	 result = result + ' <td><div class="tblminwidth-75">Accepted</div></td>';
							                     	}else if(report.rateStage == 'REJECTED'){
							                     		result = result + ' <td><div class="tblminwidth-75">Rejected</div></td>';
							                     	}else{
							                     		result = result + ' <td><div class="tblminwidth-75">----</div></td>';
							                     	}
							                     
							                      
							                     result = result + '      </tr>';
								   				}
						                     result = result + '    </tbody>';
						                     result = result + '  </table>';
						                     result = result + '</section>';
						                     result = result + '</i>';
						                     $("#ratepop").empty();  
						      	 		     $("#ratepop").append(result); 
						      	 		 	 $("#ratepop").fadeIn(); 
					        	 		}else{
					        	 		$("#ratepop").removeAttr("style");
							    	     var result ="";
							    	     result = result + '<i class="fa fa-times text-danger close-ratepop" title="closediv" onClick="closeratediv();"></i>';
							    	     result = result + '<p><strong>Rate Negotiation History</strong></p>';
							    	     result = result+"   <div class='row'>"; 
										 result = result+"	<div class='col-lg-12'>"; 
										 result = result+"		<div class='clearfix'>"; 
										 result = result+"			<div class='blank-data'>";
										 result = result+"			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
										 result = result+"			        <p>No rate history found</p>";
										 result = result+"			 </div>";
										 result = result+"		</div>"; 
										 result = result+"	</div>"; 
										 result = result+"  </div> ";
					                     result = result + '</i>';
					                     $("#ratepop").empty();  
						      	 		 $("#ratepop").append(result); 
						      	 		 $("#ratepop").fadeIn(); 
					            	}
					    	}else if (data.indexOf('Sign In') !== -1) {
					    		location.replace(sourceleadUrl);
					    	} else {
					    		showError("Operation failed, please try again");
					    	}
			         	},
			      	}); 
				};
			 $('.showRate').click(function(){
				$(this).next().fadeIn(200);
			});
			function closeratediv(){
			  $("#ratepop").fadeOut();
			}
			
//8. requirementHistory.jsp ENDS HERE.	
//9. candidatesCommentsList.jsp STARTS HERE has Role ROLE_POST_COMMENTS, ROLE_CROWDSOURCING_CANDIDATES
			function candidateCommentsList(candidateId,submissionId){
				$('#tab-7').show();
				$('.' + "current").removeClass("current");
				$("#candidateCommentTab").addClass("current");
				var urlForm = "";
				if(submissionId != null && submissionId != '' && submissionId != 'undefined') {
					urlForm = "/getAllCommentsOnCandidateByCandidateIdAndOrgLocationAndSubmissionId?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId+"&submissionId="+submissionId;
			    } else {
			    	urlForm = "/getAllCommentsOnCandidateByCandidateIdAndOrgLocation?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId;
			    }
				$.ajax({
					type : "POST",
				    url: atsUrl+urlForm,        
				    success :function(data) {
				    	var json = data.data;
				    	if (data && (data.response === "INVALID_DATA" || data.response === "INVALID_USER")) {
				    	    showError(data.responseMessage);
				    	} else if (data.response === "AWKWARD") {
				    	    location.href = "/errorPage";
				    	} else if (data.response === "SUCCESS") {
				    		var result = "";
							if(json.length > 0) {
					       	 	result  = result + '   <div class="project_tabs_inn_head addUser-org"> ';
					     		result  = result + '              <div class="project_tabs_inn_table vendor_wrap_inn_table">';
					     	    for(var i=0;i<json.length;i++) {
					     	    	var report = json[i];
					     	        result = result+'<div class="comments-div comments-inner clearfix">';
					     	        result = result+'    <div class="commentsimg">';
					     	        result = result+'    <i class="fa fa-comments"></i>';
					     	        result = result+'        <div class="commentsimg-inner">';
					     	        result = result+'        	<img onerror="predefineProfileImage(this);" src="'+report.commentAuthorUserImage+'"/>';
					     	        result = result+'        </div>';
					     	        result = result+'    </div>';
					     	        result = result+'     <div class="comments-content">';
					     	        result = result+'         <div class="pull-left">';
					     	        result = result+'         <p class="comment-by">';
					     	        result = result+'            By <span class="text-danger">'+report.commentAuthor+'</span>';
					     	        result = result+'            <span class="small">('+report.commentTime+')</span></p>';
					     	        result = result+'         <textarea class="commentText" readonly>'+report.comment+'</textarea>';
					     	        result = result+'         </div>';
					     	        result = result+'     </div>';
					     	        result = result+'</div>';
					     		}
					     	   result  = result + '              </div>';
						       	result  = result + '        </div>';
					        } else {
								var result ="";
						        result = result+"   <div class='dataTables_empty'>"; 
								result = result+"  </div> ";
							}
					       	
							$("#tab-1").empty();
					        $("#tab-2").empty(); 
					        $("#tab-3").empty();  
					        $("#tab-4").empty();  
					        $("#tab-5").empty();  
					        $("#tab-6").empty();
					        $("#tab-7").empty();
					        $("#tab-9").empty();
					        $("#tab-10").empty();
					           
					        $("#tabb-1").empty();
					       	$("#tabb-2").empty(); 
					       	$("#tabb-3").empty();
					       	$("#tabb-4").empty();  
					        $("#tabb-5").empty();  
					        $("#tabb-6").empty();
					        $("#tabb-8").empty();
					        $("#tabb-9").empty();
					        $("#tab-7").append(result);
					        $("#tab-8").empty();
				    	} else if (data.indexOf('Sign In') !== -1) {
				    		location.replace(sourceleadUrl);
				    	} else {
				    	    showError("Operation failed, please try again");
				    	}
				      },
				   });
			}

			var validationSettings = {
					errorMessagePosition : 'element',
					borderColorOnError : '',
					scrollToTopOnError : true,
					dateFormat : 'yyyy/mm/dd'
				};

		//	<!-- this script is for serializeFormJSON -->
			function commentsPagination(){  
			    $('#candidateCommentsPagination').DataTable( { 
			    	"scrollCollapse": false,
			    	"lengthMenu": [[5, 15, 20, -1], [5, 15, 20, "All"]],
			    	"bLengthChange": false,
			    	"bDestroy": true,
			    	"ordering": true,
			    	"oLanguage": {
			     		"sSearch": "",
			     		 "sZeroRecords": "", "sEmptyTable": ""
			     	}
			    } );
			 }	
//9. candidatesCommentsList.jsp ENDS HERE  		
//10. candidatesQuickView.jsp STARTS HERE
			function candidateQuickViewProfile(candidateId){
				  var skillsList = '${skillsList}'; 
				  $.ajax({
			        type : "POST",
			         url: atsUrl +"/getCandidateById?candidateId="+candidateId,        
			         success :function(result) {  
			        	 var json = result.object;
			      	     if(result.response === "SESSION_TIMEDOUT") {
								location.replace(sourceleadurl);
							} else if (result.response === "AWKWARD") {
								location.href = "/errorPage";
							} else if (result.response === "SUCCESS") {
			              		if(json != null){  
										var result ="";
										
										if(json.country != null && json.country != '' && json.country != 'undefined'){
				         			 		country =json.country;
				             			 	}else{
				             			 		country = '';
				             			 	}
				         			 	
				         			 	if(json.state != null && json.state != '' && json.state != 'undefined'){
				         			 		state =json.state+',';
				             			 	}else{
				             			 		state = '';
				             			 	}
				         			 	
				         			 	if(json.city != null && json.city != '' && json.city != 'undefined'){
				         			 		city =json.city+',';
				             			 	}else{
				             			 		city = '';
				             			 	}	
				         			 	
				         			 	if(json.lastName != null && json.lastName != '' && json.lastName != 'undefined'){
				         			 		lastName =json.lastName;
				             			 	}else{
				             			 		lastName = '';
				             			 	}
				         			 	if(json.middleName != null && json.middleName !='' && json.middleName != 'undefined'){
				         			 		middleName = json.middleName;
				         			 	}else{
				         			 		middleName = "";
				         			 	}
				         			 	if(json.mobilePhone != null && json.mobilePhone != '' && json.mobilePhone != 'undefined'){
				         			 		mobilePhone =json.mobilePhone;
				             			 	}else{
				             			 		mobilePhone = '';
				             			 	}
				         			 	if(json.designationType != null && json.designationType != '' && json.designationType != 'undefined'){
				         			 		designationType =json.designationType+',';
				             			 	}else{
				             			 		designationType = '';
				             			 	}
				         			 	
				         			 	if(json.expectedRate != null && json.expectedRate != '' && json.expectedRate != 'undefined'){
				         			 		expectedRate = json.expectedRate;
				         			 		expectedRateCurrencey = '/'+json.expectedRateCurrencey;
				         			 	}else{
				         			 		expectedRate = '---';
				         			 		expectedRateCurrencey = '';
				         			 	}
				         			 	
				         			 	if(json.rateType != null && json.rateType != '' && json.rateType != 'undefined'){
				         			 		rateType = json.rateType;
				         			 	}else{
				         			 		rateType = '';
				         			 	}
				         			 	if(json.skillsName != null && json.skillsName != 'undefined' && json.skillsName != ''){
							               var skill = json.skillsName;
				         			 		var qvSkills = "";
				         			 		var res = [];
				         			 		res = skill.split(',');
				         			 		var minSkills = res.slice(1,20);
				         			 		var totalSkills = res.length;
				         			 		if(totalSkills > 20){
				         			 			var remainingSkillsCount = totalSkills - 20;
				         			 		}
				         			 		
							                qvSkills = "<ul>";
							                for(i = 0; i<=minSkills.length ; i++){
							                		if(res[i] != null && res[i] != '' && res[i] != 'undefined'){
								                		qvSkills += "<li>" + res[i] + "</li>";
								                	}
							                		if(totalSkills > 20 && i == minSkills.length){
							                			qvSkills += "<li style='background-color: #d0d0d0;'><i class='fa fa-plus'></i> " + remainingSkillsCount + " more</li>";
							                		}
							                }
							                qvSkills += "</ul>"; 
				         			 	}else{
				         			 		qvSkills = "";
				         			 	}
										
//			 					<!-- resumeView Starts -->
								result = result+'  <div class="resumeView">                                                                                                                                    ';
								result = result+'                                                                                                                                                              ';
								result = result+'      <!-- Candiate Header -->                                                                                                                                ';
								result = result+'      <div class="row">';
								result = result+'          <div class="resume-header clearfix">                                                                                                                ';
								result = result+'        <div class="col-md-7">                                                                                                                                ';
								result = result+'            <div class="media">                                                                                                                               ';
								result = result+'                <div class="media-body">                                                                                                                      ';
								result = result+'                    <h4 class="media-heading text-warning">'+json.firstName+' '+middleName+' '+lastName+'<small> '+designationType+'</small></h4>';
								if(json.summary != null && json.summary != '' && json.summary != 'undifined'){
									
									result = result+'      <div class="trimTxt">';
									result = result+'<p title="'+json.summary+'"><i class="fa fa-quote-left text-muted"></i>'+json.summary+'</p>';
									result = result+'      </div>';
								}else{
									result = result+'<p>---</p>';
								}
								
								result = result+'                </div>                                                                                                                                        ';
								result = result+'                <div class="media-right">                                                                                                                     ';
								if(json.fileUrl != null && json.fileUrl !='' && json.fileUrl !="undefined"){
								result = result+'<img data-id="profileImage" id="userImage" src="'+json.fileUrl+'" alt="Profile Image" class="media-object" style="width:65px">';
								}else{
								result = result+'<img data-id="profileImage" id="userImage" src="/resources/images/userprofileImg.jpg" alt="Profile Image" class="media-object" style="width:65px">';
								} 
								result = result+'  <div class="mb mt5 UsProBar">';                                                                                                                                        
									result = result+' <div class="progress nomr">  ';
								if(json.profilePercentage>=50){ 
									result = result+'   <div class="progress-bar  progress-bar-success  ';
								}
								else{
									result = result+'   <div class="progress-bar  progress-bar-danger  ';
								}
								result = result+' progress-bar-striped active" role="progressbar" id="quickPerVal" aria-valuenow="'+json.profilePercentage+'" aria-valuemin="0" aria-valuemax="100" style="width:'+json.profilePercentage+'%">  ';
								result = result+'     <p id="quickDisVal">'+json.profilePercentage+'%</p>                                                                                                                                                                      ';
								result = result+'   </div>  ';
								result = result+' </div>                 ';                                                                                                                                                      
								result = result+' </div> ';
								result = result+'                </div>                                                                                                                                        ';
								
								result = result+'            </div>                                                                                                                                            ';
								result = result+'        </div>                                                                                                                                                ';
								result = result+'          <div class="col-md-5">                                                                                                                              ';
								result = result+'              <div class="pull-right">                                                                                                                        ';
								result = result+'                  <ul class="list-unstyled">                                                                                                                  ';
								result = result+'                      <li><i class="fa fa fa-envelope paddingRight text-warning" title="Email"></i>'+json.email+'';
								result = result+'                      </li>                                                                                                                                   ';
								result = result+'                      <li><i class="fa fa-phone paddingRight text-warning" title="Mobile"></i>'+mobilePhone+'</li>                                              ';
								result = result+'                      <li><i class="fa fa-map-marker paddingRight text-warning" title="Location"></i>'+city+''+state+''+country+'</li>';
								result = result+'                    <li><i class="fa fa-usd paddingRight text-warning" title="Rate"></i>'+expectedRate+' '+rateType+''+expectedRateCurrencey+'</li>';
								result = result+'                  </ul>                                                                                                                                       ';
								result = result+'              </div>                                                                                                                                          ';
								result = result+'          </div>                                                                                                                                              ';
								result = result+'              <hr>                                                                                                                                            ';
								result = result+'              </div>                                                                                                                                          ';
								result = result+'      </div>                                                                                                                                                  ';
								result = result+'        <!-- Candiate Header -->                                                                                                                              ';
								result = result+'                                                                                                                                                              ';
								result = result+'      <!-- Work Experience and Skills -->                                                                                                                     ';
								result = result+'      <div class="row">                                                                                                                                       ';
								result = result+'                                                                                                                                                              ';
								result = result+'          <!-- Work Experience and Education BLock -->                                                                                                        ';
								result = result+'          <div class="col-md-6 paddingLeft">                                                                                                                              ';
								result = result+'              <div class="row">                                                                                                                               ';
								//Employee Div
								result = result + '			<div id="qvemployeeSubmission"></div> '; 
								//Education Div
								result = result + '			<div id="qveducationSubmission"></div> ';
								result = result+'              </div>                                                                                                                                          ';
								result = result+'          </div>                                                                                                                                              ';
								result = result+'          <!-- Work Experience and Education BLock -->                                                                                                        ';
			                    result = result+'                                                                                                                                                              ';
								result = result+'          <!-- Skills, Achievements &amp; Certificates BLock -->                                                                                              ';
								result = result+'          <div class="col-md-5 col-md-offset-1 paddingLeft">                                                                                                              ';
								result = result+'              <div class="row">                                                                                                                               ';
								if(json.skillsName != null && json.skillsName != 'undefined' && json.skillsName != ''){
									result = result+'              <div class="col-md-12">                                                                                                                         ';
									result = result+'                  <h4>Skills</h4>                                                                                                           ';
									result = result+'                  <div class="skillsView row">                                                                                                                ';
									result = result+'                  <div class="panel panel-default">                                                                                                                ';
									result = result+'                  <div class="panel-body" id="skillsView"> '+qvSkills+'';
									result = result+'                  </div>';
									result = result+'                  </div>';
									result = result+'                  </div>';
									result = result+'               </div>';
								}
								//Certification Div
								result = result + '<div id="qvcertificationSubmission"></div> ';
								
								result = result+'              </div>                                                                                                                                          ';
								result = result+'          </div>                                                                                                                                              ';
								result = result+'           <!-- Skills, Achievements &amp; Certificates BLock -->                                                                                             ';
								result = result+'      </div>                                                                                                                                                  ';
								result = result+'      <!-- Work Experience and Skills -->                                                                                                                     ';
								result = result+'  </div>                                                                                                                                                      ';
//			 						      <!-- resumeView -->
									
								$("#candidateQuickViewDiv").empty();
			  				    $("#candidateQuickViewDiv").append(result); 
			  				    qvemployeeSubmission(candidateId);
			  				    qveducationSubmission(candidateId);
								qwikCertificates(candidateId);
								candidateSideBar(candidateId); 
			              	}else{
			       	    	         var result ="";
			      		    	     result = result+"   <div class='row'>"; 
			      					 result = result+"	<div class='col-lg-12'>"; 
			      					 result = result+"		<div class='content-area clearfix'>"; 
			      					 result = result+"			<div class='blank-pages'>";
			      					 result = result+"			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
			      					 result = result+"			        <p>No Candidates Found</p>";
			      					 result = result+"			 </div>";
			      					 result = result+"		</div>"; 
			      					 result = result+"	</div>"; 
			      					 result = result+"  </div> "; 
			      					$("#candidateQuickViewDiv").empty();
			      				    $("#candidateQuickViewDiv").append(result); 
			                      }
							} else if (result.indexOf('Sign In') !== -1) {
								location.replace(sourceleadUrl);
							} else {
								showError("Operation failed, please try again");
							}
			               },
			                 
			            }); 
				  
			}
				function qvemployeeSubmission(candidateId) {
					$.ajax({
								type : "POST",
								url : atsUrl	+ "/getCandidatesEmployersListByCandidateIdAndOrgLocationID?&candidateId=" + candidateId,
								success : function(data) {
									if (data && data.response === "INVALID_DATA") {
									    $("#update_candidate").removeAttr("disabled");
									    $("#update_candidate").removeAttr('button').val('Save');
									    showError(result.responseMessage);
									} else if (data.response === "AWKWARD") {
									    location.href = "/errorPage";
									} else if (data.response === "SUCCESS") {
										if(data.data.length > 0){
											
										
										$("#qvemployeeSubmission").empty();
										var result = "";
										result = result +'<div class="col-md-12">';
										result = result +'<h4>Work Experience</h4>';
										for (var i = 0; i < data.data.length; i++) {
											if(i < 4) {
												var opt = data.data[i];
												if (opt.name != null && opt.name != '' && opt.name != 'undefined') {
													name = opt.name;
												} else {
													name = " ";
												}
												if (opt.designation != null	&& opt.designation!='' && opt.designation != 'undefined') {
													designation = opt.designation;
												} else {
													designation = " "; 
												}
												if (opt.startYear != null && opt.startYear != '' && opt.startYear != 'undefined') {
													startYear=   opt.startYear;
												} else{
													startYear = " ";
												}
												if (opt.endYear != null	&& opt.endYear != '' && opt.endYear != 'undefined') {
													endYear = opt.endYear;  
												} else {
													endYear = " ";
												}
												if (opt.address != null && opt.address != '' && opt.address != 'undefined') {
													address=   opt.address;
												} else {
													address = " ";
												}
												result = result +'<div class="resume-widget">';
												if(opt.startYear != null && opt.startYear != '' && opt.startYear != 'undefined' && opt.endYear != null	&& opt.endYear != '' && opt.endYear != 'undefined'){
													result = result +'  <p><small>'+startYear +' To '+endYear+'</small>';
											    } else if(opt.startYear != null && opt.startYear != '' && opt.startYear != 'undefined') {
											    	result = result +'  <p><small>'+startYear +'</small>';
											    } else if(opt.endYear != null	&& opt.endYear != '' && opt.endYear != 'undefined') {
											    	result = result +'  <p><small>'+endYear+'</small>';
											    }
												result = result +'  <small class="pull-right address" title="'+address+'">'+address+'</small></p>';
												result = result +'  <h4>'+designation+'</h4>';
												result = result +'  <h5>'+name+'</h5>';
												result = result +'</div>';
											}
										}
										result = result +'</div>';
										$("#qvemployeeSubmission").append(result);
										} else {
										 	var result = '';
							        	  	result = result +'<div class="col-md-12" style="display:none">';
							        	  	result = result +' <h4>Work Experience</h4>';
							        	  	result = result +' <div>---</div>';
							        	  	result = result +'</div>';
							        	  	$("#qvemployeeSubmission").empty();
							        	  	$("#qvemployeeSubmission").append(result);
										}
									} else if (data.indexOf('Sign In') !== -1) {
										/* location.replace(sourceleadurl); */
									} else {
									    showError("Operation failed, please try again");
									}
								},
							});
				}
			function qveducationSubmission(candidateId){
				 $.ajax({  
			          type : "GET",
			          url: atsUrl+"/getCandidateEducationListByCandidateId?&candidateId="+candidateId,        
			        		  success :function(data) {
											if(data && data.response === "INVALID_DATA") {
			       								showError(data.responseMessage);
			       							} else if (data.response === "AWKWARD") {
			       	    						location.href = "/errorPage";
								       		} else if (data.response === "SUCCESS") {
								       			$("#qveducationSubmission").empty();
							                	 var result = "";
													result = result +'            <div class="col-md-12">';
													result = result +'              <h4>Education</h4>';
								                     for (var i = 0; i < data.data.length; i++) {
								                    	 if(i < 3) {
									                    	 var opt = data.data[i];  
									                    	 if(opt.name != null && opt.name != '' && opt.name != 'undefined'){
								                    		        EducationName = opt.name;
									                    	    }else{
									                    	    	EducationName = " ";
									                    	    }
										                     if(opt.specialization != null && opt.specialization != '' && opt.specialization != 'undefined'){
										                    	 specialization= '(' + opt.specialization +')';
									                    	  }else{
									                    		  specialization= " ";
									                       	 }
										                     if(opt.institute != null && opt.institute != '' && opt.institute != 'undefined'){
										                    	 institute = opt.institute;
									                    	 }else{
									                    		 institute = " ";
									                    	 }
										                     if(opt.grade != null && opt.grade != '' && opt.grade  != 'undefined'){
										                    	 Educationgrade= opt.grade + ' , ';
									                    	 }else{
									                    		 Educationgrade = ""; 
									                    	 }
									                    	 if(opt.gradeType != null && opt.gradeType != '' && opt.grgradeTypeade  != 'undefined'){
									                    		 EducationgradeType  = opt.gradeType + ' - ';
											                 }else{
											                	 EducationgradeType = " "; 
											                 }
									                    	 if(opt.address != null && opt.address != '' && opt.address != 'undefined'){
									                    		 Educationaddress = ' , ' +opt.address;
									                    	 }else{
									                    		 Educationaddress = " ";
									                    	 }
									                    	 if(opt.startDateStr != null && opt.startDateStr != '' && opt.startDateStr != 'undefined'){
									                    		 EducationstartDateStr = opt.startDateStr;
									                    	 }else{
									                    		 EducationstartDateStr = " ";
									                    	 }
									                    	 if(opt.endDateStr != null && opt.endDateStr != '' && opt.endDateStr != 'undefined'){ 
									                    		 EducationendDateStr = opt.endDateStr;
									                    	 }else{
									                    		 EducationendDateStr = " ";
									                    	 }
									                    	 
																result = result +'<div class="resume-widget">';
																 if(opt.startDateStr != null && opt.startDateStr != '' && opt.startDateStr != 'undefined' && opt.endDateStr != null && opt.endDateStr != '' && opt.endDateStr != 'undefined'){
																	 result = result +'<p><small>'+EducationstartDateStr+' To '+EducationendDateStr+'</small></p>';
											                    	    }else if(opt.startDateStr != null && opt.startDateStr != '' && opt.startDateStr != 'undefined'){
											                    	    	result = result +'<p><small>'+EducationstartDateStr+'</small></p>';
											                    	    }else if(opt.endDateStr != null && opt.endDateStr != '' && opt.endDateStr != 'undefined'){
											                    	    	result = result +'<p><small>'+EducationendDateStr+'</small></p>';
											                    	    }
//						 										result = result +'<p><small>'+EducationstartDateStr+' '+EducationendDateStr+'</small></p>';
																result = result +'<h5>'+EducationName+' '+specialization+'</h5>';
																result = result +'</div>';
								                     	}
								                     }
													
													result = result +'        </div>';
							                     $("#qveducationSubmission").append(result); 
							                     
							                     
							                     /* var result = '';
							               	  result = result +'            <div class="col-md-12 " style="display:none">';
							               	  result = result +' <h4>Education</h4>';
							               	  result = result +' <div>---</div>';
							               	  result = result +'            </div>';
							               	  $("#qveducationSubmission").empty();
							               	  $("#qveducationSubmission").append(result); */
								       		} else if (data.indexOf('Sign In') !== -1) {
			        							location.replace(sourceleadurl);
								        	} else {
								        	    showError("Operation failed, please try again");
								        	}
			        },
			     }); 
			}
			function qwikCertificates(candidateId){
				 $.ajax({  
			          type : "POST",
			          url: atsUrl+"/getCandidateCertificationListByCandidateIdAndOrgLocationID?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId,        
			          success :function(response) {
			        	  	if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
			        		    showError(response.responseMessage);
			        		} else if (response.response === "AWKWARD") {
			        		    location.href = "/errorPage";
			        		} else if (response.response === "SUCCESS") {
			        			if(response.data.length>0) {
			        				 $("#qvcertificationSubmission").empty();
				                	 var result = "";
				                	 result = result +'<div class="col-md-12">';
									 result = result +' <h4>Certifications</h4>';
//			 							var json = JSON.parse(data);
										for (var i = 0; i < response.data.length; i++) {
											if(i < 3) {
					                    	var opt = response.data[i];
											result = result +'<div class="resume-widget resumewidget">';
											
											if(opt.candidatevalidFromDateToString != null && opt.candidatevalidFromDateToString != 'undefined' && opt.candidatevalidFromDateToString != "" && opt.candidatevalidToDateToString != null && opt.candidatevalidToDateToString != 'undefined' && opt.candidatevalidToDateToString != ""){
												result = result +'  <p><small>'+opt.candidatevalidFromDateToString+' To '+opt.candidatevalidToDateToString+'</small></p>';
											}else if(opt.candidatevalidFromDateToString != null && opt.candidatevalidFromDateToString != '' && opt.candidatevalidFromDateToString !='undefined'){
												result = result +'  <p><small>'+opt.candidatevalidFromDateToString+'</small></p>';
											}else if(opt.candidatevalidToDateToString != null && opt.candidatevalidToDateToString != '' && opt.candidatevalidToDateToString !='undefined'){
												result = result +'  <p><small>'+opt.candidatevalidToDateToString+'</small></p>';
											}
											
											if(opt.name != null && opt.name != 'undefined' && opt.name != ""){
												name = opt.name;
											}else{
												name = '';
											}
											
											if(opt.scoreType == 'Percentage'){
												result = result +' <h4>'+name+' ('+opt.score+' %) </h4>';
											}else{
												result = result +' <h4>'+name+' ('+opt.score+' - '+opt.scoreType+') </h4>';
											}
											
											/* result = result +'<div class="description">';
											result = result +'  <p>'+opt.provider+'</p>';
											result = result +'</div>';   */
											result = result +'                  </div>';
										} 
			        	  				}
										result = result +'        </div>';
										$("#qvcertificationSubmission").append(result); 
			        			} else {
			        				 var result = '';
			        	        	  result = result +'            <div class="col-md-12 " style="display:none">';
			        	        	  result = result +' <h4>Certifications</h4>';
			        	        	  result = result +' <div>---</div>';
			        	        	  result = result +'            </div>';
			        	        	  $("#qvcertificationSubmission").empty();
			        	        	  $("#qvcertificationSubmission").append(result); 
			        			}
			        		} else if (response.indexOf('Sign In') !== -1) {
			        			location.replace(sourceleadurl);
			        		} else {
			        		    showError("Operation failed, please try again");
			        		}
			        },
			     });
			}
			
//10. candidatesQuickView.jsp ENDS HERE
//11. candidatesTimelineActivity.jsp STARTS HERE	
			 function getActivityDiv(candidateId){
				 var locationId = '${SourceLeadWeb_orgLocationId}';
				 $("#candidateTimelineDiv").empty();
				 var result = "";
				 $.ajax({
			         type : "POST",
			          url: atsUrl+"/getCandidateActivitiesByCandidateIdAndLocationCode?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId+"&locationId="+locationId,        
			          success :function(response) {
			        	 	 if (response.response === "AWKWARD") {
			        		    location.href = "/errorPage";
			        		} else if (response.response === "SUCCESS") {
			        			result = result + '<div class="content-area clearfix">';
			           		 	result = result + '<div id="accordion-sl" class="release-jobs"> ';
			           	       
			           	       	result = result + '  <div class="clearfix timeline">';
			           	       	/* result = result + '   <h2>11/07/2017</h2>'; */
			           	       	result = result + '  <div class="content content-custom clearfix">';
			           	       	result = result + '    <div class="timeline_inner">';
			           	       	result = result + '      <div class="row mt">';
			           	       	result = result + '        <div class="col-md-12"> ';
			           	     	var data = response.data; 
			                  	if(data && data.length > 0){
			               	   		result = result + '          <ul class="timeline">';
			                  		for (var i = 0; i < data.length; i++) {
			                  			var opt = data[i];
			   		   	         		result = result + '      <li> <i class="'+opt.actionIconClass+' bg-blue"></i>';
			   		                	result = result + '      <div class="timeline-item"> <span class="time"><i class="fa fa-clock-o"></i> '+opt.actionTimeStr+' </span>';
			   		                	result = result + '        <h3 class="timeline-header"><a href="'+atsUrl+'/userSummaryDetails?userId='+opt.createdUserId+'">'+opt.actionBy+'</a> has '+opt.actionDesc+' <a href="#">'+opt.actionOn+'</a></h3>';
			   		                	result = result + '        <div class="timeline-body"> '+opt.actionSummary+' </div>';

			   		                	/* result = result + '        <div class="timeline-footer"> <a class="btn btn-primary btn-xs">Read more</a></div>'; */
			   		                	result = result + '      </div>';
			   		                	result = result + '     </li>';
			                  		}
			                  		result = result + '  </ul>';
				                } else {
				               		result = result + '      <div class="noActivities">';
				               	   	result = result + '      </div>';
				                }
			                  	result = result + '  </div>';
			              		result = result + ' </div>';
			              		result = result + ' </div>';
			              		result = result + ' </div>';
			              		result = result + ' </div>';
			      				result = result + ' </div>';
			      				result = result + ' </div>';
			      		 		$("#candidateTimelineDiv").empty();
			            		$("#candidateTimelineDiv").append(result);
			        		} else if (response.indexOf('Sign In') !== -1) {
			        			location.replace(sourceleadUrl);
			        		} else {
			        		    showError("Operation failed, please try again");
			        		}
			          },
			     }); 
			 }			
//11. candidatesTimelineActivity.jsp END HERE	
//12.requirementListForSubmission.jsp Starts HERE
			 function clearJobAdvSearch(){
				 $("#reqDataTableSearch").val('');
				$('#client').val('').trigger('chosen:updated');
				$('#country').val('').trigger('chosen:updated');
				$('#requirementSkills').val('').trigger('chosen:updated');
				$('#noticePeriod').val('').trigger('chosen:updated');
				$('#requirementTitle').val('').trigger('chosen:updated');
				$('#generatedId').val('').trigger('chosen:updated');
				$('#updatedOn').val('').trigger('chosen:updated');
				$('#city').val('').trigger('chosen:updated');
				refreshGoogleApiCity('apiLocation');
			}
			  $(function() {  need to check later 
				       //Adding Customized Job Title list this method is slead-common.js
					   tagIt("requirementTitle", 'Press Enter-Key Or Comma(,) Or Tab To Add Title:', 'tagItBykeyForSkills','skills','Job Title');
				       
					   
					 //Adding Customized Job Id list this method is slead-common.js
					   tagIt("generatedId", 'Press Enter-Key Or Comma(,) Or Tab To Add Add Generated ID:', 'tagItBykeyForSkills','skills','Generated ID');
					 
					 //Adding Customized Job Skills list this method is slead-common.js
					   tagIt("requirementSkills", 'Press Enter-Key Or Comma(,) Or Space To Add New Skills:', 'tagItBykeyForSkills','skills','Skills');

					   tagIt("city", 'Press Enter-Key Or Comma(,) Or Space To Add New Cites:', 'tagItBykeyForSkills','skills','sletected cites');

					   
			  });
			function submitCandidatesToJob(){
				
				
				 var candidateIds = new Array();
				 for(var e in shareCandidates) {
					 if(!e || !shareCandidates[e] || shareCandidates[e] === "")
						   continue;
					 candidateIds.push(shareCandidates[e]);
				 } 
				 
				 if(candidateIds.length > 0){ 
					$('.action-pop').slideUp();
					$(".new_side_wrap").addClass("new_side_wrap_active");
					$(".side_mask").fadeIn(200);
					$("body").css('overflow-y', 'hidden');
					
					var result = "";
					result = result + '<h3>Select Job</h3>';
					result = result + '<div id="choicepopUp"></div>';
					result = result + '<div id="requirementsListLoading"></div>';
				    result = result +" <div class='mr pull-left'>";
					result = result +" <button type='button' onclick='jobAdvSearch()' class='btn btn-primary btn-sm'>Advanced Search&nbsp;&nbsp;<i class='fa fa-search'></i></button>";
					result = result +" <button type='button' onclick='clearJobAdvSearch();getRequirementTableList()' class='btn btn-primary btn-sm' title='Refresh'><i class='fa fa-refresh'></i></button>";
					result = result +"</div>"; 
					result = result +" <div class='pull-right'>";
					
					 result = result +" <div class='mr pull-left mt5'>";
			    	 result = result +'<label class="switch mr pull-left">';
			    	 result = result +'<input type="checkbox" checked onclick="checkResume();" value="Y" name="candidateResume"  id="candidateResume"   >';
			    	 result = result +'<div class="slider round" title="Attach Applicants Resume"></div>';
			    	 result = result +'</label>';
			    	 result = result +"<span class='paddingLeft mt2'>Attach Resume</span></div>";
					
					result = result +" <div class='mr pull-left'>";
					result = result +" <input type='button' value='Submit' onclick='submitCandidtesToJob()' class='btn btn-primary btn-sm'>";
					result = result +"</div>";
					result = result +" <div class='dynamic_search pull-left'>";
				 	result = result +" <input type='text' placeholder='Search' id='reqDataTableSearch'/>";
				 	result = result +" <span class='cursorPointer faSearch' title='Search'><i class='fa fa-search' onClick='getRequirementTableList();'></i></span>";
				 	result = result +"</div>";
				 	result = result +"</div>";
				 	result = result +"<div class='clearfix'></div>";
				 	result = result + '<table id="reqListTable" class="display mt10" cellspacing="0" width="100%" >';
					result = result + '<thead>';
					result = result + '<tr>';
					result = result +'  <th>&nbsp;</th>';
					result = result +'	<th>Generated Id</th>';
					result = result +'	<th>Title</th>';
					result = result +'	<th>Profile (%)</th>';
					result = result +'	<th>Client</th>';
					result = result +'  <th>Posted On<br>(<small class="custom-small">YYYY-MM-DD</small>)</th>';
				  	result = result +'  <th>Location</th>';
					result = result + '</tr>';
					result = result + '</thead>	';
				    result = result +"</table>";
					$("#addApplicantDiv").empty();
					$("#addApplicantDiv").append(result);
					getRequirementTableList();
				 }else{
					    var result = "";
						result = result +"	<div class='project_inn_mask'></div>"; 
						result = result +"	   <div id='shareCandidate' class='remove_user_pop remove_user_pop_a'>";
						result = result +"		<p>Please select at least one Candidate</p>";
						result = result + ' <div id="reasonForInvoiceDiv"/>';
						result = result +'		<b href="javascript:void(0)"><input id="confirm-id" type="button" value="Ok" onclick="cancelShareCandidate();"><input class="u_can_color" type="button"  value="Cancel" onClick="cancelShareCandidate();"></b>';
						result = result +"	</div>";
						$("#shareEmptypopUp").empty();
						$("#shareEmptypopUp").append(result);
						$("#shareCandidate").slideDown(200);
						$(".project_inn_mask").fadeIn(200);
				 }
			}
			function checkResume(){
				  var flag = document.getElementById("candidateResume").checked;
				 if(flag == true){
					 $("#candidateResume").val('Y'); 
				 }else{
					 $("#candidateResume").val('N'); 
				 } 
			}
			function getRequirementTableList(){
				
				var searchData = $('#reqDataTableSearch').val();
			   	if(searchData != '' && searchData != 'undefined' && (searchData.endsWith("-") || searchData.endsWith("\\"))){
			   	    $('#reqDataTableSearch').parent().find('p.jquery_form_error_message').remove();
			   		$("#reqDataTableSearch").after("<p class='jquery_form_error_message'><span><font color='red'>Enter valid Search Data</font></span>");
			   	}else{
			   	 $('#reqDataTableSearch').parent().find('p.jquery_form_error_message').remove();
				 $("#addApplicantDivLoading").attr('style','position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
				 var generatedId = "";
				 var requirementTitle = "";
				 var noticePeriod = "";
				 var client = "";
				 var updatedOn = "";
				 var requirementSkills = "";
				 var city = "";
				 
				 
				 
				 if($("#generatedId").val() != null && $("#generatedId").val() != 'undefined'){
					  generatedId = $("#generatedId").val();
				 }
				
				 if($("#requirementTitle").val() != null && $("#requirementTitle").val() != 'undefined'){
					 var requirementTitle = $("#requirementTitle").val();
				 }
				
				 if($("#noticePeriod").val() != null && $("#noticePeriod").val() != 'undefined'){
					 var noticePeriod = $("#noticePeriod").val();
				 }
				
				 if($("#client").val() != null && $("#client").val() != 'undefined'){
					 var client = $("#client").val();
				 }
				
				 if($("#updatedOn").val() != null && $("#updatedOn").val() != 'undefined'){
					 var updatedOn = $("#updatedOn").val();
				 }
				
				 if($("#requirementSkills").val() != null && $("#requirementSkills").val() != 'undefined'){
					 var requirementSkills = $("#requirementSkills").val();
				 }
				
				 if($("#city").val() != null && $("#city").val() != 'undefined'){
					 var city = $("#city").val();
				 }
				
				 var atsUrl ='${SourceLeadWeb_atsUrl}';
				 var sourceleadUrl ='${sourceleadUrl}';
				 var searchValue = $("#reqDataTableSearch").val();
				 searchValue = searchValue ? searchValue : "";
				 var url = atsUrl+"/getRequirementListForCandidateBulkSubmission?searchValue="+searchValue+"&generatedId="+generatedId+"&jobTitle="+requirementTitle+"&noticePeriod="+noticePeriod+"&client="+client+"&createdDate="+updatedOn+"&skills="+requirementSkills+"&city="+city;
				 requirementListPagination(url,atsUrl);
				 $("#addApplicantDivLoading").removeAttr("style");
			   	}
			}
			function requirementListPagination(url,atsUrl){
				
				var colIndex = 0;
			    $('#reqListTable').DataTable( { 
				  "processing": true,
			      "serverSide": true,
			      "searching": false,
			      "ajax": $.fn.dataTable.pipeline( {
			          url: url,
			          method: 'POST',
			          pages: 5
			        }),
			        "columns": [
			            { "data": "action" },
			            { "data": "generatedId"},
			            { "data": "jobTitle"},
			            { "data": "profilePercentage"},
			            { "data": "clientName"},
			            { "data": "createdDate" },
			            { "data": "location"}
			        ],
			        "columnDefs": [ {
			        	"targets": colIndex++,
			    	  	"sortable": false,
				    	  	"render": function(data, type, opt, meta) {
				    	  		  var result = "";
				    	  		   result = result +'  <td id="jobRadios"><input type="radio" id="radio'+opt.id+'" value="'+opt.id+'" name="jobRadio" /></td>';
					               return result;
				    	  	}
				      },{
				    	  "targets": colIndex++,
			              "data": "generatedId",
			              "sortable": true,
			              "render": function ( data, type, opt, meta ) {
			            	  var result = "";
			 		  		 result = result +"<td>";
			 		  	  	 if(opt.generatedId != '' && opt.generatedId != null){
			 		  	  		
			 		  		   	 result = result +"<div class='tbldata-dotdot_50 tblminwidth-75' title='"+opt.generatedId+"'>"+opt.generatedId+"</span></div>";
			 		  	    	}else{
			 		  	  		 result = result +"<div>-----</div>";
			 		  	  	 }
			 		  		 result = result +"</td>";
			 	             return  result;
			              		
			              }
				      
				          },{
					    	  "targets": colIndex++,
				              "data": "jobTitle",
				              "sortable": true,
				              "render": function ( data, type, opt, meta ) {
				            	  var result = "";
				            	  result = result +"<td><div class='tbldata-dotdot_140'><span  title='"+opt.jobTitle+"'>"+opt.jobTitle+"</span></div></td>";
				            	  return result;
				              }
					     },{
					    	  "targets": colIndex++,
				              "data": "profilePercentage",
				              "sortable": true,
				              "render": function ( data, type, opt, meta ) {
				            	  var result = "";
				            	  result = result +"<td><div class='tbldata-dotdot_50'><span  title='"+opt.profilePercentage+"'>"+opt.profilePercentage+"</span></div></td>";
				            	  return result;
				              }
					     },{
					    	  "targets": colIndex++,
				              "data": "client",
				              "sortable": true,
				              "render": function ( data, type, opt, meta ) {
				            	  var result = "";
				            	  if(opt.clientName != null && opt.clientName != '' && opt.clientName != 'undefined'){
				            		  result = result +"<td><div class='tbldata-dotdot_140'><span  title='"+opt.clientName+"'>"+opt.clientName+"</span></div></td>";
				            	  }else{
				            		  result = result +"<div>-----</div>";
				            	  }
				            	  
				            	  return result;
				              }
					     },{
					    	  "targets": colIndex++,
				              "data": "createdDate",
				              "sortable": true,
				              "render": function ( data, type, opt, meta ) {
				            	    var result = "";
				            	    result = result +"<td><div class='tblminwidth-50'>"+opt.createdDate+"</div></td>";
				            		return result;
				              }
					     }, {
					    	  "targets": colIndex++,
				              "data": "location",
				              "sortable": true,
				              "render": function ( data, type, opt, meta ) {
				            	    var result = "";
				            	    result = result +"<td><div class='tbldata-dotdot-me' title='"+opt.location+"'>"+opt.location+"</div></td>";
				            		return result;
				              }
					     }],
					     
				    	"scrollCollapse": false,
				    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
				    	"bLengthChange": true,
				    	"aaSorting": [[5,'desc']],
				    	"bDestroy": true,
				    	"ordering": true,
				    	"oLanguage": {
				     		"sSearch": "",
				     		 "sZeroRecords": "", "sEmptyTable": "", 
				     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
				     }
			    } );
			    $("#reqListTable").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
			    $("input[type=search]").hide();
			    $("#reqDataTableSearch").keyup(function(e) {
			    	   if(e.which === 13) {
			   		    $('#reqDataTableSearch').parent().find('p.jquery_form_error_message').remove();
			   	    	var searchData = $('#reqDataTableSearch').val();
			   	    	if(searchData.endsWith("-") || searchData.endsWith("\\")){
			   	    		$("#reqDataTableSearch").after("<p class='jquery_form_error_message'><span><font color='red'>Enter valid Search Data</font></span>");
			   	    	}else{
			   	    		getRequirementTableList();
			   	    	}
			   	    	$('#reqDataTableSearch').val(searchData);
			    	   }
			    	});
			}
			function jobAdvSearch(){
				     $("#requirementFilterPopup").toggle();
					 $(".advancedsearch-mask").fadeIn();
				 
			}
			function submitCandidtesToJob(){
				 
				 var jobTypeRadio = $('input[name=jobRadio]');
				 var jobId = jobTypeRadio.filter(':checked').val();
				 
				 if(jobId){
					  
					 var candidateIds = new Array();
					 for(var e in shareCandidates) {
						 if(!e || !shareCandidates[e] || shareCandidates[e] === "")
							   continue;
						 candidateIds.push(shareCandidates[e]);
					   } 
					 
					 var candidateResume = $("#candidateResume").val();
						
					 $("#requirementsListLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:1px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
						   $.ajax({
					          type : "POST",
					          url: atsUrl+"/submitRecommendationCandidates?"+tokenParamName+"="+tokenValue+"&candidateIds="+candidateIds+"&requirementId="+jobId+"&candidateResume="+candidateResume,    
					           success :function(data) {
					        	   if (data.response === "FAILED") {
					        		   $("#requirementsListLoading").removeAttr("style");
							    		showError(data.responseMessage);
							    	} else if (data.response === "AWKWARD") {
							    		location.href = "/errorPage";
							    	} else if (data.response === "SUCCESS") {
							    		$("#requirementsListLoading").removeAttr("style");
						       			document.getElementById("radio"+jobId).checked = false;
						       			activeTab();
						       			clearJobAdvSearch();
						       			$(".clients_side_wrap").removeClass("clients_side_wrap_active");
						       			$(".side_mask").fadeOut(200);
						       			$("body").css('overflow-y', 'auto');
						       			msgFadein();
						       			var submittedlength = '';
						       			if(data.responseObject.length > 0){
						       				 submittedlength = candidateIds.length - data.responseObject.length;
						       				failedCandidates.getFailuredCandidates(data.responseObject);
						       			}else{
						       				 submittedlength = candidateIds.length;
						       			}
						       			
						       			if(submittedlength > 0){
						       				$("#candMsgDiv").html(submittedlength+" Candidate(s) Submitted Successfully...");
						       			}
						       			
							    	}else if (data.indexOf('Sign In') !== -1) {
							    		location.replace(sourceleadUrl);
							    	} else {
							    		showError("Operation failed, please try again");
							    	}
					        	   
					        	   
					           },
					   });  
					 
					 
				 }else{
					 var result = "";
						result = result +"	<div class='project_inn_mask'></div>";
						result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
						result = result +"		<p>Select Job</p>";
						result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
						result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='okPopUp();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelPopUp();'></b>";
						result = result +"	</div>";
						$("#choicepopUp").append(result);
						$("#remove_user_pop").slideDown(200);
						$(".project_inn_mask").fadeIn(200);
				 }
				 
			}

			function okPopUp(){
				$("#choicepopUp").empty();
				$(".project_inn_mask").fadeOut(200);
			}

			function cancelPopUp(){
				$("#choicepopUp").empty();
				$(".project_inn_mask").fadeOut(200);
				activeTab();
				clearJobAdvSearch();
				$(".clients_side_wrap").removeClass("clients_side_wrap_active");
				$(".side_mask").fadeOut(200);
				$("body").css('overflow-y', 'auto');
			}			 
//12.requirementListForSubmission.jsp ENDS here.		
//13. canidateList Scripts here
			var candidateStatusGlobal ="";
			function candidateSideBar(candidateId){
				var loginUserId = userId;
				  var candidateType = "Candidates";
				  var locationCode = orgLocationCode;
				  $.ajax({
					  type: "GET",
					    url: atsUrl+"/getProfessionalInfo?candidateId="+candidateId+"&locationCode="+locationCode,
			           success :function(json) { 
			                if(json != null && json != 'undefined' && json != ""){  
			                	// var json = JSON.parse(data);
			              			 candidateStatusGlobal = json.status;
				                	 $("#candidateSideDiv").empty();
				                	 var skills = [];
				                	 var skillsList ='';
				                	 if(json.skillsList != null && json.skillsList != '' && json.skillsList != 'undefined'){
				                		 skillsList = json.skillsList;
							             skills = skillsList.split(",");
				                	 }
						            var experienceMonths = '';
						            var designationType = '';
						            var country = '';
						            var state = '';
						            var city = '';
						            var currencyCodes = '';
						            var generatedId = '';
						            var middleName = '';
			         			 	if(json.experienceMonths != null && json.experienceMonths != '' && json.experienceMonths != 'undefined'){
			         			 		experienceMonths = json.experienceMonths+'Month(s)';            			 		
			         			 	}else{
			         			 		experienceMonths = ' ';  
			         			 	 }
			         			 	
			         			 	if(json.designation != null && json.designation != '' && json.designation != 'undefined'){
			             			 	designation = json.designation;
			             			 	}else{
			             			 		designation = '---';
			             			 } 
			         			 	
			         			 	if(json.designationType != null && json.designationType != '' && json.designationType != 'undefined'){
			         			 	designationType = json.designationType;
			         			 	}else{
			         			 		designationType = '---';
			         			 	}

			         			 	if(json.lastName != null && json.lastName !='' && json.lastName != 'undefined'){
			         			 		lastName = json.lastName;
			         			 	}else{
			         			 		lastName = "";
			         			 	}
			         			 	if(json.middelName != null && json.middelName !='' && json.middelName != 'undefined'){
			         			 		middleName = json.middelName;
			         			 	}else{
			         			 		middleName = "";
			         			 	}

			         			 	
			         			 	if(json.country != null && json.country != '' && json.country != 'undefined'){
			         			 		country =json.country;
			             			 	}else{
			             			 		country = '';
			             			 	}
			         			 	
			         			 	if(json.state != null && json.state != '' && json.state != 'undefined'){
			         			 		state =json.state+',';
			             			 	}else{
			             			 		state = '';
			             			 	}
			         			 	
			         			 	if(json.city != null && json.city != '' && json.city != 'undefined'){
			         			 		city =json.city+',';
			             			 	}else{
			             			 		city = '';
			             			 	}
			         			 	
			         			 	 if(json.currentRate != null && json.currentRate != '' && json.currentRate != 'undefined'){
			         			 		currentRate = json.currentRate;
			         			 	 }else{
			         			 		currentRate = '';
			         			 	 }
			                           
			         			 	if(json.currentRateType != null && json.currentRateType != '' && json.currentRateType != 'undefined'){
			         			 		currentRateType = json.currentRateType;
			         			 	}else{
			         			 		currentRateType = '';
			         			 	}
			         			 	
			         			 	if(json.expectedRate != null && json.expectedRate != '' && json.expectedRate != 'undefined'){
			         			 		expectedRate = json.expectedRate;
			         			 	}else{
			         			 		expectedRate = '';
			         			 	}
			         			 	
			         			 	if(json.rateType != null && json.rateType != '' && json.rateType != 'undefined'){
			         			 		rateType = json.rateType;
			         			 	}else{
			         			 		rateType = '';
			         			 	}
			         			 	
			         			 	if(json.currentRateCurrency != null && json.currentRateCurrency != '' && json.currentRateCurrency != 'undefined'){
			         			 		currentRateCurrency = '/'+json.currentRateCurrency;
			         			 	}else{
			         			 		currentRateCurrency = '';
			         			 	}
			         			 	
			         			 	if(json.expectedRateCurrencey != null && json.expectedRateCurrencey != '' && json.expectedRateCurrencey != 'undefined'){
			         			 		expectedRateCurrencey = '/'+json.expectedRateCurrencey;
			         			 	}else{
			         			 		expectedRateCurrencey = '';
			         			 	}
			         			 	
			         			 	if(json.alternateMobilePhone != null && json.alternateMobilePhone != '' && json.alternateMobilePhone != 'undefined'){
			         			 		alternateMobilePhone = ' , ' +json.alternateMobilePhone;
			         			 	}else{
			         			 		alternateMobilePhone = '';
			         			 	}
			         			 	
			         			 	 if(json.mobilePhone != null && json.mobilePhone != '' && json.mobilePhone != 'undefined'){
			         			 		mobilePhone =json.mobilePhone;
			         			 	 }else{
			         			 		 
			         			 		mobilePhone = '';
			         			 	 }
			         			 	 
			         			 	  if(json.generatedId != null && json.generatedId != '' && json.generatedId != 'undefined'){
			         			 		generatedId = json.generatedId;
			          			 	 }else{
			          			 		generatedId = '';
			          			 	 } 
			         			 	 
				                	 var result = "";
				                	 result = result+'<div id="popUp" ></div>  ';
				                	 
				                	 result = result+'<form id="candidate_form" name="candidate_form" method="post" >';
						             result = result+'  <div class="profile_details clearfix mb">';
						             result = result+'               <div class="col-md-6">';
						             result = result+'                   <div class="media">';
						             
						             result = result+'<div class="col-xs-12 col-sm-5 col-md-5">';
						             result = result+'<div id="candidateImageId">';
						             result = result+'<div class="well well-sm text-center clearfix nomr">';
									 result = result+' <div class="profile-img-container profile-image text-center" style="position:relative;">';
									 if(json.fileUrl != null && json.fileUrl !='' && json.fileUrl !="undefined"){
									 result = result+'   <img  id="userImage" data-id="profileImage" src="'+json.fileUrl+'" alt="Profile Image" class=" img-responsive center-block img-circle">';
									 }else{
									result = result+'<img data-id="profileImage" id="userImage" src="/resources/images/userprofileImg.jpg" alt="Profile Image" class=" img-responsive center-block img-circle">';

									 }
									 result = result+'  <div class="btn-group uploadfile uploadfile-candidate">';
									 //result = result+'  <button type="button" class="btn btn-sm btn-primary">';
									 //result = result+'  <i class="fa fa-upload"></i></button>';
									 result = result+'  <label class="btn btn-sm btn-primary" ">';
								     result = result+'   <i class="fa fa-upload paddingRight"></i> Upload / Change<input type="file" id="uploadedImage" accept="image/*" onchange="cropImage(this,\'candidate_form\','+candidateId+',false);" style="display: none;">';
								     result = result+'  </label>';
								     if(json.fileContentType != null && json.fileContentType != '' && json.fileContentType != 'undefined'){
									     result = result+'  <button type="button" class="btn btn-sm btn-danger" onclick="deleteImage('+candidateId+')">';
										 result = result+'  <i class="fa fa-trash"></i></button>';
								     } 
									 result = result+'  </div>';
									 result = result+'  </div>';
									 /*  result = result+'  <div class="pull-right" style="position:relative; top:10px; right:-10px;">';
									 result = result+'  <a href="#" title="Save"><i class="fa fa-check-circle" onClick="saveCandidateImage('+candidateId+');" ></i></a>';
									 result = result+'  <a href="#" title="Close"><i class="fa fa-close text-danger"></i></a>';
									 result = result+'  </div>'; */
									 result = result+'  </div>';
									 
									 result = result+'  <div class="mb mt5 UsProBar">';                                                                                                                                        
										result = result+' <div class="progress nomr">  ';
									if(json.profilePercentage>=50){ 
										result = result+'   <div class="progress-bar  progress-bar-success  ';
									}
									else{
										result = result+'   <div class="progress-bar  progress-bar-danger  ';
									}
									result = result+' progress-bar-striped active" role="progressbar" id="perVal" aria-valuenow="'+json.profilePercentage+'" aria-valuemin="0" aria-valuemax="100" style="width:'+json.profilePercentage+'%">  ';
									result = result+'     <p id="disVal">'+json.profilePercentage+'%</p>                                                                                                                                                                      ';
									result = result+'   </div>  ';
									result = result+' </div>                 ';                                                                                                                                                      
									result = result+' </div> ';
									result = result+'<div class="socialnetwork-details socialnetworkIcons">';
						             result = result+'<ul class="list-inline list-unstyled">';
						             if(json.consultantUser !=null && json.consultantUser.id != null && json.consultantUser.facebook != null && json.consultantUser.facebook !="undefined" && json.consultantUser.facebook != ""){
							             result = result+'<li class="bg-facebook" title="Facebook"><a target="_blank" href=\"'+json.consultantUser.facebook+'\" > <i class="fa fa-facebook-square" ></i></a></li>';
							             }
							             if(json.consultantUser !=null && json.consultantUser.id != null && json.consultantUser.twitter != null && json.consultantUser.twitter !="undefined" && json.consultantUser.twitter != ""){
							             result = result+'<li class="bg-twitter" title="Twitter"><a target="_blank" href=\"'+json.consultantUser.twitter+'\"> <i class="fa fa-twitter-square" ></i></a></li>';
							             }
							             if(json.consultantUser !=null && json.consultantUser.id != null && json.consultantUser.googlePlus != null && json.consultantUser.googlePlus !="undefined" && json.consultantUser.googlePlus != ""){
							             result = result+'<li class="bg-google " title="Google-Plus"><a target="_blank" href=\"'+json.consultantUser.googlePlus+'\"> <i class="fa fa-google-plus-square" ></i></a></li>';
							             }
							             if(json.consultantUser !=null && json.consultantUser.id != null && json.consultantUser.linkedIn != null && json.consultantUser.linkedIn !="undefined" && json.consultantUser.linkedIn != ""){
							             result = result+'<li class="bg-linked" title="Linkedin"><a target="_blank" href=\"'+json.consultantUser.linkedIn+'\"> <i class="fa fa-linkedin-square" ></i></a></li>';
							             }
						             result = result+'</ul>';
						             result = result+'                 </div>';
									/* else{ 
										result = result+'  <div class="mb mt5 UsProBar">';                                                                                                                                        
										result = result+' <div class="progress nomr">                                                                                                                                                       ';
										result = result+'   <div class="progress-bar progress-bar-danger progress-bar-striped active" role="progressbar" id="perVal" aria-valuenow="'+json.profilePercentage+'" aria-valuemin="0" aria-valuemax="100" style="width:'+json.profilePercentage+'%">  ';
										result = result+'    <p id="disVal"> '+json.profilePercentage+'%</p>                                                                                                                                                                      ';
										result = result+'   </div>                                                                                                                                                                     ';
										result = result+' </div>                                                                                                                                                                       ';
										result = result+' </div> ';
									} */
									 
									 result = result+' <p class="text-danger small" style="font-size: 11px; display: none;" id="errorImage" >Invalid Image/Size</p>';
									 result = result+' </div>';
									 result = result+' </div>';
									 result = result+'</form>';
									 
										
									
									  
						             /*  result = result+'                     <div class="media-left image-parent">';
						             result = result+'                     <div class="imageParent">'; */
						             /* result = result+'                     	<img src="./images/24.jpg" alt="candidate" title="Candidate">'; */
						             /* result = result+'<img  id="userImage" onerror="predefineProfileImage(this);" src="<c:url value="/displayUserImage/${SourceLeadWeb_userId}"/>"  alt="Profile Image" class="img-circle img-responsive">';
						             result = result+'                     </div>';
						             result = result+'                     </div>'; */
						             result = result+'<div class="media-body candidate-details candidateDetails padding_left">';
						            /*  result = result+'<h4 class="media-heading" title = "">'+json.generatedId+'</h4>'; */
						             result = result+'<h4 class="media-heading" title = "'+json.firstName+' '+middleName+' '+lastName+'">'+json.firstName+' '+middleName+' '+lastName+'</h4>';
						            
						             result = result+'<ul class="list-unstyled">';
						             
//			 			             result = result+'<li>Type - '+json.candidateType+'</li>';  
						             
			                         
			                         
									 result = result+'<li><span class="col-md-5 padding_left paddingRight text-muted">Generated ID</span><span class="col-md-7 padding_left padding_right">'+generatedId+'</span></li>';
						             
									 result = result+'<li><span class="col-md-5 padding_left paddingRight text-muted">Designation</span><span class="col-md-7 padding_left padding_right tbldata-dotdot-sm" title= '+designation+'> '+designation+'</span></li>';
									 
									 result = result+'<li><span class="col-md-5 padding_left paddingRight text-muted">Employment Type</span><span class="col-md-7 padding_left padding_right">'+designationType+'</span></li>';
									 
									 
									 
				         			 if(json.vendorName != null && json.vendorName != '' && json.vendorName != 'undefined'){

									 result = result+'<li><span class="col-md-5 padding_left paddingRight text-muted">Vendor</span><span class="col-md-7 padding_left padding_right tbldata-dotdot-sm" title='+json.vendorName+'>'+json.vendorName+'</span></li>';
				         			 }
				         			 
				         			if(json.vendorContactName != null && json.vendorContactName != '' && json.vendorContactName != 'undefined'){

									result = result+'<li><span class="col-md-5 padding_left paddingRight text-muted">Vendor Contact</span><span class="col-md-7 padding_left padding_right tbldata-dotdot-sm" title = '+json.vendorContactName+'>'+json.vendorContactName+'</span></li>';
					         		}
						             
						              if(json.experienceYears != null && json.experienceYears != '' && json.experienceYears != 'undefined'){
						            	 result = result+'<li><span class="col-md-5 padding_left paddingRight text-muted">Experience</span><span class="col-md-7 padding_left padding_right">'+json.experienceYears+' Year(s) '+experienceMonths+'</span></li>';
			              		       }else{
			                		   		 result = result+'<li><span class="col-md-5 padding_left paddingRight text-muted">Experience</span><span class="col-md-7 padding_left padding_right">---</span></li>';  
			              		       }
						            
						             
						              if(json.noticePeriod != null && json.noticePeriod != '' && json.noticePeriod != 'undefined'){
						                    	 
						             result = result+'<li><span class="col-md-5 padding_left paddingRight text-muted">Notice Period</span><span class="col-md-7 padding_left padding_right">'+json.noticePeriod+'(s)'+'</span></li>';
						             
			             		       }else{
			             		                                                	  
			                   		     result = result+'<li><span class="col-md-5 padding_left paddingRight text-muted">Notice Period</span><span class="col-md-7 padding_left padding_right">---</span></li>';  
			             		     
			             		       }
						             result = result+'</ul>';
						             
						             result = result+'<div class="clearfix">';
						             result = result+'<div class="">';
						             
						            
						             result = result+'<ul class="list-inline list-unstyled">';
						            /*  if(json.skillsList != null && json.skillsList != '' && json.skillsList != 'undefined'){
						              for (i=0;i < skills.length;i++)
						             {
						            	  result = result+'<li>'+skills[i]+'</li>';
						             } 
						             } */
						             
						             if(json.primarySkill != null && json.primarySkill != '' && json.primarySkill != 'undefined'){
						            	 result = result+'<li><span class="col-md-5 padding_left paddingRight text-muted">Primary Skill</span><span class="padding_left padding_right">'+json.primarySkill+'</span></li>';
						             }else{
						            	 result = result+'<li><span class="col-md-5 padding_left paddingRight text-muted">Primary Skill</span><span class="col-md-7 padding_left padding_right">---</span</li>';  
						             }
						             
						             if(json.gender != null && json.gender != '' && json.gender != 'undefined'){
						            	 result = result+'<li><span class="col-md-5 padding_left paddingRight text-muted">Gender</span><span class="padding_left padding_right">'+json.gender+'</span></li>';
						             }else{
						            	 result = result+'<li><span class="col-md-5 padding_left paddingRight text-muted">Gender</span><span class="col-md-7 padding_left padding_right">---</span</li>';  
						             }
						             
						             if(json.maritalStatus != null && json.maritalStatus != '' && json.maritalStatus != 'undefined'){
						            	 result = result+'<li><span class="col-md-5 padding_left paddingRight text-muted">Marital Status</span><span class="padding_left padding_right">'+json.maritalStatus+'</span></li>';
						             }
						             if(json.dateofBirthString != null && json.dateofBirthString != '' && json.dateofBirthString != 'undefined'){
						            	 result = result+'<li><span class="col-md-5 padding_left paddingRight text-muted">DateOfBirth</span><span class="padding_left padding_right">'+json.dateofBirthString+'</span></li>';
						             }else{
						            	 result = result+'<li><span class="col-md-5 padding_left paddingRight text-muted">DateOfBirth</span><span class="col-md-7 padding_left padding_right">---</span</li>';  
						             }
						             
						             result = result+'</ul>';
						             result = result+'</div>';
						             result = result+'                 </div>';
						             result = result+'               </div>';
						             result = result+'                   </div>';
						             result = result+'                 </div>';
						             result = result+'               <div class="col-md-6">';
						             result = result+'                 <ul class="list-unstyled can-perdt">';
						             result = result+'                   <li><i class="fa fa-envelope-o" title= "Email"></i><a href="#" title="'+json.email+'">'+json.email+'</a></li>';
						             result = result+'<li><i class="fa fa-phone" title= "Mobile"></i>'+mobilePhone+''+alternateMobilePhone+'</li>';
						             result = result+' <li><i class="fa fa-location-arrow" title= "Location"></i>'+city+''+state+''+country+'</li>';
						             
						             result = result+'<li><i class="fa fa-money" title= "Current Rate"></i><b>Current Rate:</b>';
						             
						             result = result+''+currentRate+' '+currentRateType+''+currentRateCurrency+'';
						             
						             result = result+'</li>';
						             
						             result = result+'<li><i class="fa fa-money" title= "Expected Rate"></i><b>Expected Rate:</b>';
						             
						             result = result+''+expectedRate+' '+rateType+''+expectedRateCurrencey+'';
						             
						             result = result+'</li>';
						             
						             result = result+'</ul>';
						             
						            /* New comments div creation Nov 8th, 2016 */ 
						            if(json.status == '1' ){
						          //  '<sec:authorize access="hasAnyRole(\'ROLE_POST_COMMENTS\',\'ROLE_CROWDSOURCING_CANDIDATES\')">'; 
						            result = result+'<div class="comments-div clearfix" id="latestMessage">';
						            result = result+'    <div class="commentsimg">';
						            result = result+'    <i class="fa fa-comments"></i>';
						            result = result+'        <div class="commentsimg-inner">';
						            
						            result = result+'        	<img data-id="userProfileImage" onerror="predefineProfileImage(this);" src="${SourceLeadWeb_userImgUrl}"/>';
						            result = result+'        </div>';
						            result = result+'    </div>';
						            result = result+'     <div class="comments-content">';
						            result = result+'         <div class="pull-left">';
						            result = result+'         <p class="comment-by"></p>';
						            result = result+'         <p class="comment-text"></p>';
						            result = result+'         </div>';
						            result = result+'         <div class="pull-right cmt-button">';
						            result = result+'          <span class="comments-button">';
						            
						            result = result+'          	<button type="button" class="btn btn-sm btn-primary" onClick="showComment('+candidateId+')">';
						            result = result+'            	<i class="fa fa-plus"></i>&nbsp;Comments';
						            result = result+'            </button>';
						            result = result+'            <div class="add-comment">';
						            result = result+'            </div>';
						            result = result+'          </span>';
						            result = result+'          <a href="javascript:void(0)" class="comments-view" onclick="candidateCommentsList('+candidateId+');">View more <span class="label label-warning">'+json.commentsCount+'</span></a>';
						 			result = result+'		</div>';
						            result = result+'     </div>';
						            result = result+'</div>';
						         //   '</sec:authorize>';
						            }
						 		    /* New comments div creation Nov 8th, 2016 */
						             
						             
						             result = result+'               </div>';
						             result = result+'			</div>';
						             
						             
						             result = result+'  <div class="cearfix">';
							         result = result+'	  <div class="newTagsWrap">';
							         result = result+'		    <ul class="clearfix tagsWrap tagsWrapBor">';
							         result = result+'			<select multiple class="chosen-select form-control" name="tagName" id="candidateTagName" data-placeholder="Add Tags" data-validation="validate_Space" data-validation-optional="true">';
							         result = result+'			</select>';
							         result = result+'			</ul>';
							         result = result+'			<div class="clearfix"></div>';
							         result = result+'	  </div>';
							         result = result+' </div>';
				
							        
						            /*  result = result+' <div class="newTagsWrap">';
										result = result+'    <ul class="clearfix tagsWrap tagsWrapBor">';
										result = result + '<select multiple class="chosen-select form-control" name="tagName" id="tagName" data-placeholder="add tags" data-validation="validate_Space" data-validation-optional="true">';
										// here get lookup data tags and manually enter skills 
										$.each( json.tagsList, function( i, tags ) {
										  var existed = 0;
							                if(json.candidateTags != null && json.candidateTags != 'undefined' && json.candidateTags != '') {
								                var res = [];
								                res = json.candidateTags.split(",");
								                res.forEach(function(item) {
								                	if(tags == item){
								                		existed = 1;
								                		result = result + ' <option selected value="'+tags+'">'+tags+'</option>';
								                	}
								                });
							                }
							                if(existed == 0){
							                	result = result + ' <option value="'+tags+'">'+tags+'</option>';
							                }
										});
						                result = result + '</select>';
										result = result+'        <div class="clearfix"></div>';
										result = result+'    </ul>';
										result = result+'</div>'; */
										
										
										
						             result = result+'   <div class="proj_close"></div>';
						             result = result+'   <div class="project_side_wrap_inn_tabs">';
						             result = result+'     <ul class="project-tabs-menu">'; 
						           //  '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CANDIDATE\',\'ROLE_VIEW_EDIT_CANDIDATE\',\'ROLE_VIEW_DELETE_CANDIDATE\',\'ROLE_CROWDSOURCING_CANDIDATES\')">';

						              result = result+'<li id="candidateProfileTab" class="current"><a href="#tab-1" onclick="candidateProfile('+candidateId+',\''+candidateType+'\',\''+json.isAssociate+'\');">Profile</a></li>';

						           //  '</sec:authorize>';
						          //   
						             '<sec:authorize access="hasAnyRole(\'ROLE_ADD_CANDIDATE_RESUMES\',\'ROLE_VIEW_CANDIDATE_RESUMES\',\'ROLE_VIEW_EDIT_CANDIDATE_RESUMES\',\'ROLE_VIEW_DELETE_CANDIDATE_RESUMES\',\'ROLE_CROWDSOURCING_CANDIDATES\')">';
						               result = result+'<li id="candidateResumeTab"><a href="#tab-2" onclick="candidateResumesList('+candidateId+'); candidateResume('+candidateId+','+json.status+');" >Resume</a></li>';
						           //  '</sec:authorize>';
						             
						           //  '<sec:authorize access="hasAnyRole(\'ROLE_ADD_EMPLOYEER\',\'ROLE_VIEW_EMPLOYEER\',\'ROLE_VIEW_EDIT_EMPLOYEER\',\'ROLE_VIEW_DELETE_EMPLOYEER\',\'ROLE_CROWDSOURCING_CANDIDATES\')">';
						               result = result+'<li id="candidateEmployersTab"><a href="#tab-3" onclick="candidateExperience('+candidateId+');">Employers</a></li>';
						           //  '</sec:authorize>';
						             
						           //  '<sec:authorize access="hasAnyRole(\'ROLE_ADD_CANDIDATE_EDUCATION\',\'ROLE_VIEW_CANDIDATE_EDUCATION\',\'ROLE_VIEW_EDIT_CANDIDATE_EDUCATION\',\'ROLE_DELETE_CANDIDATE_EDUCATION\',\'ROLE_CROWDSOURCING_CANDIDATES\')">';
						              result = result+'<li id="candidateEducationTab"><a href="#tab-4" onclick="candidateEducation('+candidateId+');">Education</a></li>';
						            // '</sec:authorize>';
						             
						           //  '<sec:authorize access="hasAnyRole(\'ROLE_ADD_CANDIDATE_CERTIFICATION\',\'ROLE_VIEW_CANDIDATE_CERTIFICATION\',\'ROLE_VIEW_EDIT_CANDIDATE_CERTIFICATION\',\'ROLE_VIEW_DELETE_CANDIDATE_CERTIFICATION\',\'ROLE_CROWDSOURCING_CANDIDATES\')">';
						               result = result+'<li id="candidateCertificationsTab"><a href="#tab-5" onclick="candidateCertificate('+candidateId+');">Certifications</a></li>';
						            // '</sec:authorize>';
						             
						            // <%--- This is for Documents(tab-9) Tab in Candidate Summary -----%>
						           //  '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_ASSOCIATE_DOCUMENT\',\'ROLE_VIEW_EDIT_ASSOCIATE_DOCUMENT\',\'ROLE_DELETE_ASSOCIATE_DOCUMENT\',\'ROLE_CREATE_ASSOCIATE_DOCUMENT\',\'ROLE_CROWDSOURCING_CANDIDATES\')">';
						             result = result+'<li id="candidateDocumentsTab"><a href="#tab-9" onclick="candidateDocuments('+candidateId+');">Documents</a></li>';
						          //   '</sec:authorize>';
						             
						            result = result+'<li id="candidateReferencesTab"><a href="#tab-10" onclick="candidateReferences('+candidateId+');">References</a></li>';
									 
						            // '<sec:authorize access="hasAnyRole(\'ROLE_POST_COMMENTS\',\'ROLE_CROWDSOURCING_CANDIDATES\')">';
						                result = result+'<li id="candidateCommentTab"><a href="#tab-7" onclick="candidateCommentsList('+candidateId+');">Comments</a></li>';
						            //  '</sec:authorize>';
						              
						            //  '<sec:authorize access="hasAnyRole(\'ROLE_CANDIDATE_REQUIREMENT_HISTORY\',\'ROLE_CROWDSOURCING_CANDIDATES\')">';
						              result = result+'<li id="candidateHistoryTab"><a href="#tab-8" onclick="requirementHistory('+candidateId+');">Job History</a></li>';
						            //  '</sec:authorize>';
						              
						             /* result = result+'       <li><a href="#tab-6" onclick="candidateActivities('+candidateId+');">Activities</a></li>'; */
						             result = result+'       <div class="clearfix"></div>';
						             result = result+'     </ul>';
						             result = result+'     <div class="project_tabs">';
							         result = result + ' <div id="tab-1" class="project_tabs_inn">';
									 result = result + '</div>';
									 
									 result = result + '<div id="tab-2" class="project_tabs_inn">';
									 result = result + '</div>';
									 
									 result = result + '<div id="tab-3" class="project_tabs_inn">';
									 result = result + '</div>';
									 
									 result = result + ' <div id="tab-4" class="project_tabs_inn">';
									 result = result + '</div>';
									 
									 result = result + '<div id="tab-5" class="project_tabs_inn">';
									 result = result + '</div>';
									 
									 result = result + '<div id="tab-9" class="project_tabs_inn">';
						             result = result+'     </div>';
						             
						             result = result + '<div id="tab-10" class="project_tabs_inn">';
						             result = result+'     </div>';
									 
									 result = result + '<div id="tab-8" class="project_tabs_inn">';
									 result = result + '</div>';
									 
									 result = result + '<div id="tab-6" class="project_tabs_inn">';
									 result = result + '</div>';
									 result = result + '<div id="tab-7" class="project_tabs_inn">';
									 result = result + '</div>';
						             result = result+'   </div>';
						             
						             $("#candidateSideDiv").empty();  
						 		     $("#candidateSideDiv").append(result);  
							 		 $(".clients_side_wrap").addClass("clients_side_wrap_active");
									 $(".side_mask").fadeIn(200);
									 $("body").css('overflow-y', 'hidden');
									 getCandidateTagsList(candidateId);
									 
									 
									 if("${candidateKeyword}" == "requestedDocumentNotification"){
									    	$("#quickview").removeClass("active in");
									    	$("#timelineview").removeClass("active in");
									    	$("#detailview").addClass("active in");
									    	$("#candQuickView").removeClass("active");
									    	$("#candTimelineView").removeClass("active");
									    	$("#candDetailView").addClass("active");
								 	    	candidateDocuments(candidateId);
									    }else{
									    	candidateProfile(candidateId,candidateType,json.isAssociate);
									    }
									 
									 var output = "";
									 output = output + '<a data-toggle="tab" href="#timelineview" onclick="getActivityDiv('+candidateId+')">Timeline</a>';
									 $("#candTimelineView").empty();  
						 		     $("#candTimelineView").append(output);
						 		     
						 		     
						 		    // Tags relatd code
						 		    var select, chosen;
						 		   select = $("#candidateTagName");
						 		   select.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add Keyword:' });
						 		   chosen = select.data('chosen');
						 		   chosen.search_field.on('keyup', function(e)
						 		   {
						 		   	var anyKeyword = this.value.replace(",","");
						 		       if ((e.which == 13 || e.which == 188) && (anyKeyword != null  && anyKeyword != '' && anyKeyword != 'undefined'))
						 		       {
						 		       	var par = this.parent;
						 		           var option = $("<option>").val(anyKeyword).text(anyKeyword);
						 		           select.append(option);
						 		           select.find(option).prop('selected', true);
						 		           $("#candidateTagName option").each(function(){
						 		                 $(this).siblings("[value='"+ this.value+"']").remove();
						 		           });
						 		   		$("#candidateTagName").trigger("chosen:updated");
						 		   		saveCandidateTags("add",candidateId);
						 		       }
						 		   });

						 		   $('#candidateTagName').chosen().on('change', function(evt, params) {
						 		   	if (params.selected && params.selected != undefined) {
						 		   		saveCandidateTags("add",candidateId);
						 		   	}
						 		   	if (params.deselected && params.deselected != undefined) {
						 		   		saveCandidateTags("delete",candidateId);
						 		   	}
						 		   	
						 		   });
						 		   $("#candidateTagName_chosen").find('ul.chosen-choices > li input').attr("placeholder", "Add Tag")

						 		   
			                }else{
			               	 $("#candidateSideDiv").removeAttr("style");
			 	    	         var result ="";
					    	     result = result+"   <div class='row'>"; 
								 result = result+"	<div class='col-lg-12'>"; 
								 result = result+"		<div class='content-area clearfix'>"; 
								 result = result+"			<div class='blank-pages'>";
								 result = result+"			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
								 result = result+"			        <p>No Candidates Found</p>";
								 result = result+"			 </div>";
								 result = result+"		</div>"; 
								 result = result+"	</div>"; 
								 result = result+"  </div> "; 
								$("#candidateSideDiv").empty();
							    $("#candidateSideDiv").append(result); 
			                }
			         },
			           
			      }); 
				  
				  savedCommentAndCount(candidateId);
							
			}		
			function getCandidateTagsList(candidateId){
				$.ajax({  
			        type : "POST",
			        url: atsUrl+"/getCandidatesTags?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId,        
			        success :function(json) {
			        	var result = "";
						$.each( json.tagsList, function( i, tags ) {
						  var existed = 0;
			                if(json.candidateTags != null && json.candidateTags != 'undefined' && json.candidateTags != '') {
				                var res = [];
				                res = json.candidateTags.split(",");
				                res.forEach(function(item) {
				                	if(tags == item){
				                		existed = 1;
				                		result = result + ' <option selected value="'+tags+'">'+tags+'</option>';
				                	}
				                });
			                }
			                if(existed == 0){
			                	result = result + ' <option value="'+tags+'">'+tags+'</option>';
			                }
						});
			            $("#candidateTagName").empty();
			            $("#candidateTagName").append(result);
			            $("#candidateTagName").chosen().trigger("chosen:updated")

			        },
				});
			}
			function saveCandidateTags(type,candidateId){
				 var tagName = $("#candidateTagName").val();
				 var jsonData = encodeURIComponent(tagName);
				 var loginUserId = '${SourceLeadWeb_userId}';
				 var orgLocationId = '${SourceLeadWeb_orgLocationId}';
				 if(tagName != null && tagName != '' && tagName !='undefined') {
					 Urlcheck = atsUrl+"/saveOrUpdateTagsBycandidateId?"+tokenParamName+"="+tokenValue+"&loginUserId="+loginUserId+"&orgLocationId="+orgLocationId+"&candidateId="+candidateId+"&tagName="+jsonData
				 } else {
					 Urlcheck = atsUrl+"/saveOrUpdateTagsBycandidateId?"+tokenParamName+"="+tokenValue+"&loginUserId="+loginUserId+"&orgLocationId="+orgLocationId+"&candidateId="+candidateId+"&tagName="+""
				 }
				 $.ajax({  
			         type : "POST",
			         url: Urlcheck,        
			         success :function(result) {
						if(result && (result.response === "INVALID_DATA" || result.response === "INVALID_USER")) {
			        		showError(result.responseMessage);
			        	} else if (result.response === "AWKWARD") {
			        	    location.href = "/errorPage";
			        	} else if (result.response === "SUCCESS") {
			        		if(type == "add"){
			        			showSuccessStatusPopup1("Tags added successfully");
			        		}else if(type == "delete"){
			        			showDeleteStatusPopup("Tags deleted successfully");
			        		}
			        		getCandidateTagsList(candidateId);
			        	} else if (result.indexOf('Sign In') !== -1) {
			        		location.replace(sourceleadUrl);
			        	} else {
			        	    showError("Operation failed, please try again");
			        	}
			       },
			    }); 
			}

			function showSuccessStatusPopup1(status) {
				$("#addCandidateTagAlert").css("display","block");
				$("#addCandidateTagAlert span").html(status);
			    $('#addCandidateTagAlert').delay(2000).fadeOut('slow');
			}
			function showDeleteStatusPopup(status) {
				$("#deleteCandidateTagAlert").css("display","block");
				$("#deleteCandidateTagAlert span").html(status);
			    $('#deleteCandidateTagAlert').delay(2000).fadeOut('slow');
			}	
			function deleteProfileImage(candidateId){   
				var loginUserId = '${SourceLeadWeb_userId}';
				var orgLocationId = '${SourceLeadWeb_orgLocationId}';
				 $.ajax({
					    type: "GET",
			            url: atsUrl+"/deleteCandidateImage?candidateId="+candidateId+"&loginUserId="+loginUserId+"&orgLocationId="+orgLocationId,
				        success: function(response) {
				        	if (response.response === "AWKWARD") {
				        	    location.href = "/errorPage";
				        	} else if (response.response === "SUCCESS") {
				        		var result = response.object;
					        	 $("#popUp").empty();
					        	 $(".project_inn_mask").fadeOut(200);
					        	 $("[data-id=profileImage]").attr('src', '/resources/images/userprofileImg.jpg');
					        	 candidateQuickViewProfile(candidateId);
					        	 refreshImageBlock(candidateId);
				        	} else if (response.indexOf('Sign In') !== -1) {
				        		location.replace(sourceleadUrl);
				        	} else {
				        	    showError("Operation failed, please try again");
				        	}
				       },
			     }); 
			}

/*
			<%-- 
			     @author : sarath kumar.
			     this script is for refresh image block
			     1.usage     : 
			--%>*/
			function refreshImageBlock(candidateId){   
				  $.ajax({
			          type : "POST",
			           url: atsUrl+"/getCandidateById?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId,        
			           success :function(result) { 
			        	   var json = result.object;
			        	   if(result.response === "SESSION_TIMEDOUT") {
								location.replace(sourceleadUrl);
							} else if (result.response === "AWKWARD") {
								location.href = "/errorPage";
							} else if (result.response === "SUCCESS") {
								if(json != null){
				                	 $("#candidateImageId").empty();
				                	 var result = '';
						             result = result+'<div class="well well-sm text-center clearfix">';
									 result = result+' <div class="profile-img-container profile-image text-center" style="position:relative;">';
									 result = result+'   <img  id="userImage" data-id="profileImage" onerror="predefineProfileImage(this);" src="'+json.fileUrl+'" alt="Profile Image" class=" img-responsive center-block img-circle">';
									 result = result+'  <div class="btn-group uploadfile uploadfile-candidate">';
									 //result = result+'  <button type="button" class="btn btn-sm btn-primary">';
									 //result = result+'  <i class="fa fa-upload"></i></button>';
									 result = result+'  <label class="btn btn-sm btn-primary">';
								     result = result+'  <i class="fa fa-upload paddingRight"></i>Upload / Change<input type="file" name="file" id="uploadedImage" accept="image/*"  onchange="cropImage(this,\'candidate_form\','+candidateId+',false);" style="display: none;">';
								     result = result+'  </label>';
								     if(json.fileName != null && json.fileName != '' && json.fileName != 'undefined'){
									     result = result+'  <button type="button" class="btn btn-sm btn-danger" onclick="deleteImage('+candidateId+')">';
										 result = result+'  <i class="fa fa-trash"></i></button>';
								     } 
									 result = result+'  </div>';
									 result = result+'  </div>';
									 
									 result = result+'  </div>';
									 
									 result = result+' <div class="mb mt5 UsProBar">';
									 result = result+' <div class="progress nomr"> ';
									 if(json.profilePercentage>=50){
									 result = result+' <div class="progress-bar progress-bar-success ';
									 }
									 else{
									 result = result+' <div class="progress-bar progress-bar-danger ';
									 }
									 result = result+' progress-bar-striped active" role="progressbar" id="perVal" aria-valuenow="'+json.profilePercentage+'" aria-valuemin="0" aria-valuemax="100" style="width:'+json.profilePercentage+'%"> ';
									 result = result+' <p id="disVal">'+json.profilePercentage+'%</p> ';
									 result = result+' </div> ';
									 result = result+' </div> ';
									 
									 result = result+' <p class="text-danger small" style="font-size: 11px; display: none;" id="errorImage" >Invalid Image/Size</p>';
									 result = result+' <p class="text-muted small" style="font-size: 11px;"><i class="fa fa-info-circle"></i>&nbsp;png, svg and gif images are not supported</p>';
									 result = result+' <p class="text-muted small" style="font-size: 11px;"><i class="fa fa-info-circle"></i>&nbsp;Image size should not more than 200 KB</p>';
									 result = result+' </div>';
									 $("#candidateImageId").append(result);
			                	}
							} else if (result.indexOf('Sign In') !== -1) {
								location.replace(sourceleadUrl);
							} else {
								showError("Operation failed, please try again");
							}
			         	},
			      }); 
			   }
			function addFavourite(candidateId){
				
				var checked = document.getElementById("fav"+candidateId).checked;
				 	$.ajax({
						type : "POST",
						url : atsUrl+"/saveCandidateAsFavourite?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId,
						success : function(response) {
							if(checked){
								 document.getElementById("favourite"+candidateId).removeAttribute("class");
								 document.getElementById("favourite"+candidateId).className = 'iconClass pull-left star-rating activestar-rating';
								 document.getElementById("fav"+candidateId).setAttribute("title","Change to un Favourite");
							} else {
								 document.getElementById("favourite"+candidateId).classList.remove("activestar-rating");
//			 					 document.getEmentById("favourite"+candidateId).className = 'iconClass pull-left star-rating';
								 $("#fav"+candidateId).removeAttr("title");
								 document.getElementById("fav"+candidateId).setAttribute("title","Add as Favourite");
							}
							if (response && response.response === "INVALID_DATA") {
							    showError(response.responseMessage);
							} else if (response.response === "AWKWARD") {
							    location.href = "/errorPage";
							} else if (response.response === "SUCCESS") {
								var td = cacheLastJson.data;
								for(var e in td) {
									if(td[e].candidateId === candidateId) {
										td[e]
										if(isAssociate == "N"){
											td[e].isFavourite = checked ? 1 : 0;
						            	} else if(td[e].isFavouriteAssociate == '1' && isAssociate == "Y") {
						            		td[e].isFavouriteAssociate = checked ? 1 : 0;
						            	}
										break;
									}
								}

								cacheLastJson.data = td;
									

							} else if (response.indexOf('Sign In') !== -1) {
								location.replace(sourceleadUrl);
							} else {
							    showError("Operation failed, please try again");
							}
						},
					}); 
			}
/*

			<%-- 
			     @author : sarath kumar.
			     this script is for validate image for candidate.
			     1.usage     : used in candidateSideBar() function line number :171.
			--%>*/
			function loadFile(event,candidateId){
				    var output = document.getElementById('userImage');
				    var uploadedImage = document.getElementById('uploadedImage').value;
				    var sizeinbytes = document.getElementById('uploadedImage').files[0].size;
				    document.getElementById("errorImage").style.display="none"; 
				    
				    var extension = uploadedImage.substring(uploadedImage.lastIndexOf(".")+1).toLowerCase();
				    
				    if(extension != 'jpg'){
				    	
				    	var extensionName = uploadedImage.substring(uploadedImage.lastIndexOf(".")+1);
				 		var msg = "."+extensionName+" extension not supported";
				 		alertMessage(msg,candidateId);
				    }else if(sizeinbytes > 200000){
				    	output.src = URL.createObjectURL(event.target.files[0]);
				 		var msg = "Image size should be less than 200 KB";
				 		alertMessage(msg,candidateId);
				    }else{
				    	output.src = URL.createObjectURL(event.target.files[0]);
				    	var msg = "Do you want to save the picture.";
				    	saveOrCancel(msg,candidateId);
				   } 
			}
/*
			<%-- 
			     @author : sarath kumar.
			     this script is for show  alert message for candidate image format.
			     1.usage     : used in loadFile() function line number :422.
			--%>
*/
			function alertMessage(message,candidateId){
				var result = "";
				result = result +"	<div class='project_inn_mask'></div>";
				result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
				result = result +"		<p>"+message+"</p>";
				result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
				result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='cancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
				result = result +"	</div>";
				$("#popUp").append(result);
				$("#remove_user_pop").slideDown(200);
				$(".project_inn_mask").fadeIn(200);
				refreshImageBlock(candidateId);

			}
/*
			<%-- 
			     @author : sarath kumar.
			     this script is for conformation message to save file / not .
			     1.usage     : used in loadFile() function line number :439.
			--%>*/
			function saveOrCancel(message,candidateId){
				var result = "";
				result = result +"	<div class='project_inn_mask'></div>";
				result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
				result = result +"		<p>"+message+"</p>";
				result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
				result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onClick='saveCandidateImage("+candidateId+");' ><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
				result = result +"	</div>";
				$("#popUp").append(result);
				$("#remove_user_pop").slideDown(200);
				$(".project_inn_mask").fadeIn(200);
			}
/*
			<%-- 
			     @author : sarath kumar.
			     this script is for close alert popup.
			     1.usage     : used in saveOrCancel() / alertMessage function line number : 435 / 439.
			--%>*/
			function cancel(candidateId){ 
				$("#popUp").empty();
				$(".project_inn_mask").fadeOut(200);
				refreshImageBlock(candidateId);
			}
			function imageCancel(){
				$("#popUp").empty();
				$(".project_inn_mask").fadeOut(200);
			}
			var candidateIdsMark = [];
			   function appendIdsToMarketPlace(obj){
				   if($(obj).is(":checked")){
					   candidateIdsMark.push($(obj).attr("id"));
				   }else if($(obj).not(":checked")){
					   candidateIdsMark = jQuery.grep(candidateIdsMark, function(value) {
						   return value != $(obj).attr("id");
						});
				   }
			   }
			   
			   function callAddToMarket(){
				 	 $.ajax({
					    	type: "POST",
					        url: atsUrl+"/getAddedMarketplaceCandidates",
					        data : {candidateIdsVal : candidateIdsMark},
					        success: function(json) {
					        	candidateIDsArr = [];
					        	popupDisplay(json['candidatesInMarketplace'].length, json['candidatesNotInMarketplace'].length, json['candidatesNotInMarketplace'] );
					        }
				 	}); 
			 }
			  
			   function getSourceleadTags(){
			 		 $.ajax({
					    	type: "GET",
					        url: atsUrl+"/getSourceleadTagsList?"+tokenParamName+"="+tokenValue, 
					        success: function(json) {
					        	var output = "";
					        	for (var i=0; i<json.length; i++)
								{
						        		var opt = json[i];
						        		output = output + "<option value='" +opt+ "'>"+opt+"</option>";
						        		
								}
					        	$("#canTags").empty();
						        $("#canTags").append(output);
						        $('#canTags').trigger("chosen:updated");
					        },
					    }); 
			   }
				   function msgFadein() {
						$("#candSelector").fadeIn(200);
					    $('#candSelector').delay(2000).fadeOut('slow');
					}
				function activeTab() {
					$("#candDetailView").removeClass("active");
					$("#candQuickView").addClass("active");
					$("#quickview").addClass("active in");
					$("#detailview").removeClass("active in");
					$("#candTimelineView").removeClass("active");
					$("#timelineview").removeClass("active in");
					$(".location-alert").fadeOut();
					
					$(".new_side_wrap").removeClass("new_side_wrap_active");
					$(".req_side_mask").fadeOut(200);
					$("body").css('overflow-y', 'auto');
					candidateList();
				}
			  $('#selector').delay(2000).fadeOut('slow');
		//	<!-- BELOW SCRIPTS ARE USED TO SHOW / HIDE POPUP'S --> 
			// Error pop
			$('#sucesspop').delay(2000).fadeOut('slow');
			$(document).ready(function() {
			    $(".glyphicon-trash").click(function(){
					$(".vendor_error").slideToggle(200);
					$(".vendor_wrap_mask").fadeIn(200);
				});
				$(".vendor_error a").click(function(){
					$(".vendor_error").slideUp(200);
					$(".vendor_wrap_mask").fadeOut(200);
				});
			});	

			// Approved Popup
			    $("#appro-pop").click(function(){
					$(".approved_pop").toggle(200);
					
				});
			    $("html").click(function() {
			        $(".approved_pop").hide(200);
			    });
			    $(".approved_pop, #appro-pop").click(function(e) {
			        e.stopPropagation();
			    });	

			// Export Popup
			    $("#candidate-export").click(function(){
					$(".export_pop").toggle();
					$("#filter2ApprovedPop, #filter3ApprovedPop, #filter1ApprovedPop, #addmorepop").slideUp();
				});
			    $("html").click(function() {
			        $(".export_pop").hide(200);
			    });
			    $(".export_pop, #candidate-export").click(function(e) {
			        e.stopPropagation();
			    });	
			//add more popu

			$("#addmore").click(function(){
				$(this).next(".approved_pop").toggle();
					//$(".approved_pop").toggle(200);
				$("#filter2ApprovedPop, #filter3ApprovedPop, #filter1ApprovedPop, #filter4ApprovedPop").slideUp();
				});
			$("html").click(function() {
			    $(".approved_pop").hide(200);
			});
			$(".approved_pop, #addmore").click(function(e) {
			    e.stopPropagation();
			});	
			    
			// project side bar tabs
			$(document).ready(function() {
				$("#tab-1").show()
			    $(".project-tabs-menu a").click(function(event) {
			        event.preventDefault();
			        $(this).parent().addClass("current");
			        $(this).parent().siblings().removeClass("current");
			        var tab = $(this).attr("href");
			       // $(".project_tabs_inn").not(tab).css("display", "none"); // commented due to script error in UI.
			       // $(tab).fadeIn();
			    }); 

				
			});
			$(document).ready(function() {
				
				
			    $("#filter1ApprovePop").click(function(){
					$("#filter1ApprovedPop").slideToggle(200);
					$(".export_pop, #filter3ApprovedPop, #addmorepop, #filter2ApprovedPop").slideUp();
				});
			    $("html").click(function() {
			        $("#filter1ApprovedPop").slideUp(200);
			    });
			    $("#filter1ApprovedPop, #filter1ApprovePop").click(function(e) {  
			        e.stopPropagation();
			    });	
			    
			    
			    $("#filter2ApprovePop").click(function(){
					$("#filter2ApprovedPop").slideToggle(200);
					$(".export_pop, #filter3ApprovedPop, #filter1ApprovedPop, #addmorepop").slideUp();
				});
			    $("html").click(function() {
			        $("#filter2ApprovedPop").slideUp(200);
			    });
			    $("#filter2ApprovedPop, #filter2ApprovePop").click(function(e) {  
			        e.stopPropagation();
			    });	
			    
			    $("#filter3ApprovePop").click(function(){
					$("#filter3ApprovedPop").slideToggle(200);
					$(".export_pop, #filter2ApprovedPop, #filter1ApprovedPop, #addmorepop").slideUp();
				});
			    $("html").click(function() {
			        $("#filter3ApprovedPop").slideUp(200);
			    });
			    $("#filter3ApprovedPop, #filter3ApprovePop").click(function(e) {  
			        e.stopPropagation();
			    });	
			    
			  
			    
			}); 
			function toggle(source) {
			    var checkboxes = document.querySelectorAll('input[type="checkbox"]');
			    for (var i = 0; i < checkboxes.length; i++) {
			        if (checkboxes[i] != source)
			            checkboxes[i].checked = source.checked;
			        
			    }
			$('input:checkbox:not(#checkAll)').click(function() {
			    if(!this.checked) {
			        $('#checkAll').removeAttr('checked');
			    }
			});
			}
			function exportToPdf(){
				var isCandidatesAvailable= $("#noCandidates").val();
				  var candFilterRadio = $('input[name=candidateStatus]');
				  var candidateStatus = candFilterRadio.filter(':checked').val();
				  if(candidateStatus == 1){
			        $("#canDiv").text("Active Candidates");
			    }else if(candidateStatus == 0){
			  	  $("#canDiv").text("In Active Candidates");
			    }else if(candidateStatus == 'ALL'){
			  	  $("#canDiv").text("All Candidates");
			    }else{
			        $("#canDiv").removeAttr("onClick");
			    }
				  
			    var candidateFilterSkills = [];
				 $('input:checkbox[name=candidateFilterSkills]:checked').each(function() 
			     {
					 candidateFilterSkills.push($(this).val());
				 });
				 
				 var availability = []
				 $('input:checkbox[name=availability]:checked').each(function() 
				     {
				       availability.push($(this).val());
					 });
				 
				 var candidateTypeFilter = []
				 $('input:checkbox[name=candidateTypeFilter]:checked').each(function() 
				     {
					 candidateTypeFilter.push($(this).val());
					 });
				 
				 var createdByRadio = $('input[name=createdByFilter]');
				  var createdByFilter = createdByRadio.filter(':checked').val();
				 
				  var candTypeRadio = $('input[name=candidateType]');
				  var candidateType = candTypeRadio.filter(':checked').val();
				  if(isCandidatesAvailable > 0){
				  window.location.href = atsUrl+"/getCandidatesExportList?&candidateStatus="+candidateStatus+"&exportType=pdf"+"&candidateType="+candidateType+"&candidateFilterSkills="+candidateFilterSkills+"&availability="+availability+"&candidateTypeFilter="+candidateTypeFilter+"&createdByFilter="+createdByFilter+"&isAssociate=N";	
				  }else{
					  var result = "";
						result = result +"	<div class='project_inn_mask'></div>";
						result = result +"	   <div id='candidate_user_pop' class='remove_user_pop remove_user_pop_a'>";
						result = result +"		<p>Candidates List is Empty</p>";
						result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
						result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='candidtaeCancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='candidtaeCancel();'></b>";
						result = result +"	</div>";
						$("#emptyPopup").append(result);
						$("#candidate_user_pop").slideDown(200);
						$(".project_inn_mask").fadeIn(200);
				  }
			}

			/* Dont remove ... value */
			function popupDisplay(candidatesInMarketplace, noCandidateNotInMarketplace, ...candidatesNotInMarketplace){
				$(".project_inn_mask").remove();
				var result = "";
				result = result +"	<div class='project_inn_mask'></div>";
				result = result +"	   <div id='remove_immigration_pop' class='remove_user_pop remove_user_pop_a'>";
				result = result +"<div class='modal-body'><p id='popupMsg'></p></div>";
				result = result + ' <div id="reasonForTemplateDiv"></div>';
				result = result +"<div class='clearfix'><input class='u_can_color width100percnt' type='button'  value='Ok' onClick='cancelMarketplacePopup("+candidatesNotInMarketplace+");'></div>";
				result = result +"	</div>";
				$("#emptyPopup").empty().append(result);
				$("#popupMsg").empty().append(candidatesInMarketplace+" has already processed for the Marketplace, <br/> "+noCandidateNotInMarketplace+" will process for the marketplace.");
				$("#remove_immigration_pop").slideDown(200);
				$(".project_inn_mask").fadeIn(200);
			}

			/* Dont remove ... value */
			function cancelMarketplacePopup(...candidatesNotInMarketplace){
				$("#remove_immigration_pop").fadeOut(200);
				$(".project_inn_mask").fadeOut(200);
				if(candidatesNotInMarketplace.length != 0){
					$.ajax({
				    	type: "POST",
				        url: atsUrl+"/navigateMarketPlaceAjax",
				        data : {candidateIdsVal : candidatesNotInMarketplace},
				        success: function(json) {
				        	location.href = atsUrl+"/navigateMarketPlaceList";
				        	//candidatesNotInMarketplace = [];
				        }
			 	});
				}
			}
			function candidtaeCancel(){
				$("#emptyPopup").empty();
				$(".project_inn_mask").fadeOut(200);
			}
			function exportToExcel(){
				var isCandidatesAvailable= $("#noCandidates").val();
				  var candFilterRadio = $('input[name=candidateStatus]');
				  var candidateStatus = candFilterRadio.filter(':checked').val();
				  if(candidateStatus == 1){
			       $("#canDiv").text("Active Candidates");
			   }else if(candidateStatus == 0){
			 	  $("#canDiv").text("In Active Candidates");
			   }else if(candidateStatus == 'ALL'){
			 	  $("#canDiv").text("All Candidates");
			   }else{
			       $("#canDiv").removeAttr("onClick");
			   }
				  
				  var candidateFilterSkills = [];
					 $('input:checkbox[name=candidateFilterSkills]:checked').each(function() 
				     {
						 candidateFilterSkills.push($(this).val());
					 });
					 
					 var availability = []
					 $('input:checkbox[name=availability]:checked').each(function() 
					     {
					       availability.push($(this).val());
						 });
					 
					 var candidateTypeFilter = []
					 $('input:checkbox[name=candidateTypeFilter]:checked').each(function() 
					     {
						 candidateTypeFilter.push($(this).val());
						 });
					 
					 var createdByRadio = $('input[name=createdByFilter]');
					  var createdByFilter = createdByRadio.filter(':checked').val();
				  
				  var candTypeRadio = $('input[name=candidateType]');
				  var candidateType = candTypeRadio.filter(':checked').val();
				  if(isCandidatesAvailable > 0){
				  window.location.href = atsUrl+"/getCandidatesExportList?&candidateStatus="+candidateStatus+"&exportType=xls"+"&candidateType="+candidateType+"&candidateFilterSkills="+candidateFilterSkills+"&availability="+availability+"&candidateTypeFilter="+candidateTypeFilter+"&createdByFilter="+createdByFilter+"&isAssociate=N";	
				  }else{
					  var result = "";
						result = result +"	<div class='project_inn_mask'></div>";
						result = result +"	   <div id='candidate_user_pop' class='remove_user_pop remove_user_pop_a'>";
						result = result +"		<p>Candidates List is Empty</p>";
						result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
						result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='candidtaeCancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='candidtaeCancel();'></b>";
						result = result +"	</div>";
						$("#emptyPopup").append(result);
						$("#candidate_user_pop").slideDown(200);
						$(".project_inn_mask").fadeIn(200);
				  }
			}
			function showComment(candidateId){
				var result = "";
					result = result+'	        <form role="form" id="candidateComments_form" name="candidateComments_form" method="post">';
				    result = result+'              <textarea rows="1" class="form-control clearfix mb10" placeholder="Comments here..." id="comment" name="comment" data-validation="required validate_length length2-500" field-name="Comment"></textarea>';
				    result = result+'              <span class="clearfix pull-right">';
				    result = result+'              		<input type="button" id="submit_comment" value="Add" class="btn btn-sm btn-success" onClick="addComment('+candidateId+')">';
				    result = result+'              		<input type="button" value="Cancel" class="btn btn-sm btn-default" onClick="closeComment()">';
				    result = result+'              </span>';
					result = result+'	        </form>';
				$('.add-comment').empty();
				$('.add-comment').append(result);
				$('.add-comment').toggle(200);
			}
			function closeComment(){
				$('.add-comment').toggle(200);
			}

/*
			<%-- 
			     @author : Venkat Kesav
			     this script is for saving Comment
			     1.usage     : onclick
			     2.parameter : candidateId.
			     3.ajax url implementation in CandidateResumeRestController.
			--%>*/
				function addComment(candidateId){
						var validationSettings = {
						        errorMessagePosition : 'element',
						        borderColorOnError : '',
								scrollToTopOnError : true,
								dateFormat : 'yyyy/mm/dd' 
					         }; 
						 if ($('#candidateComments_form').validate(false, validationSettings)) {
				      		 var formData = getFormData("candidateComments_form");
							$("#submit_comment").attr("disabled", "disabled");
			         		$("#submit_comment" ).addClass('button').val('Processing..');
							
							$.post(atsUrl+"/saveCandidateComment?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId,formData,function(result){
								if (result && result.response === "INVALID_USER") {
					    		    showError(result.responseMessage);
					    		} else if (result.response === "AWKWARD") {
					    		    location.href = "/errorPage";
					    		} else if (result.response === "SUCCESS") {
					    			savedCommentAndCount(candidateId);
					    		} else if (result.indexOf('Sign In') !== -1) {
									location.replace(sourceleadUrl);
								} else {
								    showError("Operation failed, please try again");
								}
							});
							$('.add-comment').toggle(200);
							return true;
						 } else { 
								return false;
						 }
					}
					$('body').on('blur', '#candidateComments_form', function() {
					$("#candidateComments_form").showHelpOnFocus();
					$("#candidateComments_form").validateOnBlur(false, validationSettings);
					
				});
			function savedCommentAndCount(candidateId){
				  $.ajax({
			        type : "POST",
			         url: atsUrl+"/getAllCommentsOnCandidateByCandidateIdAndOrgLocation?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId,        
			         success :function(data) {
			        	 if (data && data.response === "INVALID_DATA") {
			        		    showError(data.responseMessage);
			        		} else if (data.response === "AWKWARD") {
			        		    location.href = "/errorPage";
			        		} else if (data.response === "SUCCESS") {
			        			var result = "";
			                    if(data.data.length > 0) {
			                    	var json = data.data;
			                  		var totalComments = 0;
			                  		if(json.length>0) {
			                  			totalComments = json.length;
			            	            var report = json[0];
			   	 			            
			   	 			            result = result+'    <div class="commentsimg">';
			   				            result = result+'    <i class="fa fa-comments"></i>';
			   				            result = result+'        <div class="commentsimg-inner">';
			   				            
			   				            result = result+'        	<img data-id="userProfileImage" onerror="predefineProfileImage(this);" src="'+report.commentAuthorUserImage+'"/>';
			   				            result = result+'        </div>';
			   				            result = result+'    </div>';
			   				            result = result+'     <div class="comments-content">';
			   				            result = result+'         <div class="pull-left" id="latestMessage">';
			   				            result = result+'         <p class="comment-by">';
			   				            result = result+'            By <span class="text-danger">'+report.commentAuthor+'</span>';
			   				            result = result+'            <span class="small">('+report.commentTime+')</span></p>';
			   				            result = result+'         <p class="comment-text">'+report.comment+'</p>';
			   				            result = result+'         </div>';
			   				            result = result+'         <div class="pull-right cmt-button">';
			   				            result = result+'          <span class="comments-button">';
			   				            
			   				            result = result+'          	<button type="button" class="btn btn-sm btn-primary" onClick="showComment('+candidateId+')">';
			   				            result = result+'            	<i class="fa fa-plus"></i>&nbsp;Comments';
			   				            result = result+'            </button>';
			   				            result = result+'            <div class="add-comment">';
			   				            result = result+'            </div>';
			   				            result = result+'          </span>';
			   				            result = result+'          <a href="javascript:void(0)" class="comments-view" onclick="candidateCommentsList('+candidateId+');">View more <span class="label label-warning">'+totalComments+'</span></a>';
			   				 			result = result+'		</div>';
			   				            result = result+'     </div>';
			   	 			           
			   	 			           	$("#latestMessage").empty();
			   	 			           	$("#latestMessage").append(result);
			                  		}
								}
			        		} else if (data.indexOf('Sign In') !== -1) {
			        			location.replace(sourceleadUrl);
			        		} else {
			        		    showError("Operation failed, please try again");
			        		}
						},
			      });
			}
				// Close Advanced Search
					function closePop(){
						 $("div.cand-advsrch-popup").hide();
						 $('#expId').find('p.jquery_form_error_message').remove();
						 $(".advancedsearch-mask").fadeOut();
					}
					function openPop(){
					  $("#dataTableSearch").val('');
					  $("div.cand-advsrch-popup").toggle();
					  $(".advancedsearch-mask").fadeIn();
					  $("#candidateFilterPopup").toggle();
					};
					function closeAdsrch(){
						$("div.cand-advsrch-popup").fadeOut();
						$(".advancedsearch-mask").fadeOut();
					};
			function getStates(){
				var countryName = $("#country").val();
				if(countryName != null && countryName != "" && countryName !="undefined"){
					$.ajax({
				    	type: "POST",
				    	dataType: "json",
				    	 url: atsUrl +"/getStatesByCountry?"+tokenParamName+"="+tokenValue+"&countryName="+countryName, 
				        success: function(data) {
				        	var json = JSON.parse(data);
				        	$('#state').empty();
				        	$('#state').append('<option value="">---select state---</option>');
				            for(var i=0;i<json.length;i++){
				            	$('#state').append('<option value="'+json[i]+'">'+json[i]+'</option>');
				            }
				            $('#state').trigger("chosen:updated");
				            
				            $('#citySearch').empty();
				            $('#citySearch').append('<option value="">---select city---</option>');
				            $('#citySearch').trigger("chosen:updated");
				        },
				        error: function(e){
				        }
				    });
				}else{
					$('#state').empty();
			    	$('#state').append('<option value="">---select state---</option>');
			    	$('#state').trigger("chosen:updated");
			    	$('#citySearch').empty();
			    	$('#citySearch').append('<option value="">---select city---</option>');
			        $('#citySearch').trigger("chosen:updated");
				}
			}
			function getCities(){
				var countryName = $("#country").val();
				var stateName = $("#state").val();
				if(countryName != null && countryName != "" && countryName !="undefined" && stateName != null && stateName != "" && stateName !="undefined" ){
					$.ajax({
				    	type: "POST",
				    	dataType: "json",
				    	 url: atsUrl+"/getCitiesByStateAndCountry?"+tokenParamName+"="+tokenValue+"&countryName="+countryName+"&stateName="+stateName, 
				        success: function(data) {
				        	var json = JSON.parse(data);
				        	$('#citySearch').empty();
				        	$('#citySearch').append('<option value="">---select city---</option>');
				            for(var i=0;i<json.length;i++){
				            	$('#citySearch').append('<option value="'+json[i]+'">'+json[i]+'</option>');
				            }
				            $('#citySearch').trigger("chosen:updated");
				        },
				        error: function(e){
				        }
				    });
				}else{
					$('#citySearch').empty();
			    	$('#citySearch').append('<option value="">---select city---</option>');
			    	$('#citySearch').trigger("chosen:updated");
				}
			}


	//		<!-- Country,State,City Script Ends Here -->	
				function loadChosenScripts(){
				    
				    $('#minRate').val(''); 
				    $('#maxRate').val(''); 
				    $('#currency').val(''); 
				    $('#rateType').val(''); 
				    
				    /* $('#country').val(''); 
				    $("#country").val('').trigger("chosen:updated"); */
				    /* $("#state").empty();
				    $("#state").val('').trigger("chosen:updated"); */
				    $("#citySearch").empty();
				    $("#citySearch").val('').trigger("chosen:updated");
				    $('#rateType').val(''); 

				    $('#emailDiv').find('p.jquery_form_error_message').remove();
				    $('#search_candidate').removeAttr('disabled');
				    $('#minRateDiv').find('p.jquery_form_error_message').remove();
				    $('#maxRateDiv').find('p.jquery_form_error_message').remove();
				    $('#currencyDiv').find('p.jquery_form_error_message').remove();
				    $('#rateTypeDiv').find('p.jquery_form_error_message').remove();
				    
				    
				    $("#anyOfKeyword").val('').trigger("chosen:updated");
				    $("#allKeywords").val('').trigger("chosen:updated");
				    $("#excludeKeyWord").val('').trigger("chosen:updated");
				    $("#designation").val('').trigger("chosen:updated");
				    $("#education").val('').trigger("chosen:updated");
				    $("#excludeEducation").val('').trigger("chosen:updated");
				    $("#certification").val('').trigger("chosen:updated");
				    $("#employers").val('').trigger("chosen:updated");
				    $("#excludeEmployers").val('').trigger("chosen:updated");
				    $("#canSkills").val('').trigger("chosen:updated");
				    $("#canVisa").val('').trigger("chosen:updated");
				    $("#canTags").val('').trigger("chosen:updated");
				    $("#noticePeriod").val('').trigger("chosen:updated");
				    $("#primarySkill").val('').trigger("chosen:updated");
				    
				    $("#anyOfKeyword").empty();
				    $("#allKeywords").empty();
				    $("#excludeKeyWord").empty();
				    $("#designation").empty();
				    $("#certification").empty();
				    $("#employers").empty();
				    $("#excludeEmployers").empty();
				}
			  $(function() {  // need to check later.
				       //Adding Customized Any KeyWord Title list this method is slead-common.js
					   tagIt("anyOfKeyword", 'Press Enter-Key Or Comma(,) Or Tab To Add Keyword:', 'tagItBykeyForSkills','skills','Any Keyword');
				       
					   //Adding Customized All KeyWord Title list this method is slead-common.js
					   tagIt("allKeywords", 'Press Enter-Key Or Comma(,) Or Tab To Add Keyword:', 'tagItBykeyForSkills','skills','All Keywords');
					   
					   //Adding Customized Exclude KeyWord Title list this method is slead-common.js
					   tagIt("excludeKeyWord", 'Press Enter-Key Or Comma(,) Or Tab To Add Keyword:', 'tagItBykeyForSkills','skills','Exclude Keyword');
					   
					   //Adding Customized Designation list this method is slead-common.js
					   tagIt("designation", 'Press Enter-Key Or Comma(,) Or Tab To Add Designation:', 'tagItBykeyForSkills','skills','Designation');
					   
					  //Adding Customized Certification list this method is slead-common.js
					   tagIt("certification", 'Press Enter-Key Or Comma(,) Or Tab To Add Certification:', 'tagItBykeyForSkills','skills','Certification');
					  
					   //Adding Customized Employers list this method is slead-common.js
					   tagIt("employers", 'Press Enter-Key Or Comma(,) Or Tab To Add Employer:', 'tagItBykeyForSkills','skills','Employers');
					   
					   //Adding Customized ExcludeEmployers list this method is slead-common.js
					   tagIt("excludeEmployers", 'Press Enter-Key Or Comma(,) Or Tab To Add Employer:', 'tagItBykeyForSkills','skills','Exclude Employers');
					   
					   //Adding Customized PrimarySkill list this method is slead-common.js
					   tagIt("primarySkill", 'Press Enter-Key Or Comma(,) Or Tab To Add Peimary Skills:', 'tagItBykeyForSkills','skills','Primary Skill');
					   
					 //Adding Customized Skill list this method is slead-common.js
					   tagIt("canSkills", 'Press Enter-Key Or Comma(,) Or Tab To Add Skills:', 'tagItBykeyForSkills','skills','Skills');
					 
					   //Adding Customized Visa list this method is slead-common.js
					   tagIt("canVisa", 'Press Enter-Key Or Comma(,) Or Tab To Add Visa:', 'tagItBykeyForSkills','skills','Visa');
					   
					   tagIt("canTags", 'Press Enter-Key Or Comma(,) Or Tab To Add Tags:', 'tagItBykeyForSkills','skills','Visa');
					   
					   //Adding Customized Visa list this method is slead-common.js
					   tagIt("education", 'Press Enter-Key Or Comma(,) Or Tab To Add Education:', 'tagItBykeyForSkills','skills','Education');
					   
					   //Adding Customized Visa list this method is slead-common.js
					   tagIt("excludeEducation", 'Press Enter-Key Or Comma(,) Or Tab To Add Exclude Education:', 'tagItBykeyForSkills','skills','Exclude Education');
					 
					   
					   
			  });
			    	    var config = {
			    				  '.chosen-select'           : {},
			    				  '.chosen-select-deselect'  : {allow_single_deselect:true},
			    				  '.chosen-select-no-single' : {disable_search_threshold:10},
			    				  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			    				  '.chosen-select-width'     : {width:"95%"}
			    	   }
			    	   
			    	   
						//Adding Customized Any Of Keyword to the list 
					    var select, chosen;
					    select = $("#anyOfKeyword");
					    select.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) Or Tab To Add Keyword:' });
					    chosen = select.data('chosen');
					    chosen.search_field.on('keydown keyup', function(e)
					    {
					    	var anyKeyword = this.value.replace(",","");
					        if (tagItBykeyForSkills(e) && (anyKeyword != null  && anyKeyword != '' && anyKeyword != 'undefined'))
					        {
					        	preventTabkey(e); 
					        	var par = this.parent;
					            var option = $("<option>").val(anyKeyword).text(anyKeyword);
					            select.append(option);
					            select.find(option).prop('selected', true);
			                    $("#anyOfKeyword option").each(function(){
			                          $(this).siblings("[value='"+ this.value+"']").remove();
			                    });
				        		$("#anyOfKeyword").trigger("chosen:updated");
				        		
					        }
					    });
					    
					  //Adding Customized Any Of Keyword to the list
					    var selectone, chosenone;
					    selectone = $("#allKeywords");
					    selectone.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) Or Tab To Add Keyword:' });
					    chosenone = selectone.data('chosen');
					    chosenone.search_field.on('keydown keyup', function(e1)
					    {
					    	var allKeyword = this.value.replace(",","");
					        if (tagItBykeyForSkills(e1) && (allKeyword != null  && allKeyword != '' && allKeyword != 'undefined'))
					        {
					        	preventTabkey(e1); 
					        	var par = this.parent;
					            var option = $("<option>").val(allKeyword).text(allKeyword);
					            selectone.append(option);
					            selectone.find(option).prop('selected', true);
			                    $("#allKeywords option").each(function(){
			                          $(this).siblings("[value='"+ this.value+"']").remove();
			                    });
				        		$("#allKeywords").trigger("chosen:updated");
				        		
					        }
					    });
					    
					    
					    var selecttwo, chosentwo;
					    selecttwo = $("#excludeKeyWord");
					    selecttwo.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) Or Tab To Add Keyword:' });
					    chosentwo = selecttwo.data('chosen');
					    chosentwo.search_field.on('keydown keyup', function(e2)
					    {
					    	var excludeKeyword = this.value.replace(",","");
					        if (tagItBykeyForSkills(e2) && (excludeKeyword != null  && excludeKeyword != '' && excludeKeyword != 'undefined'))
					        {
					        	preventTabkey(e2);
					        	var par = this.parent;
					            var option = $("<option>").val(excludeKeyword).text(excludeKeyword);
					            selecttwo.append(option);
					            selecttwo.find(option).prop('selected', true);
			                    $("#excludeKeyWord option").each(function(){
			                          $(this).siblings("[value='"+ this.value+"']").remove();
			                    });
				        		$("#excludeKeyWord").trigger("chosen:updated");
				        		
					        }
					    });
					    
					    var selectthree, chosenthree;
					    selectthree = $("#designation");
					    selectthree.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) Or Tab To Add Designation:' });
					    chosenthree = selectthree.data('chosen');
					    chosenthree.search_field.on('keydown keyup', function(e3)
					    {
					    	var desg = this.value.replace(",","");
					        if (tagItBykeyForSkills(e3) && (desg != null  && desg != '' && desg != 'undefined'))
					        {
					        	preventTabkey(e3);
					        	var par = this.parent;
					            var option = $("<option>").val(desg).text(desg);
					            selectthree.append(option);
					            selectthree.find(option).prop('selected', true);
			                    $("#designation option").each(function(){
			                          $(this).siblings("[value='"+ this.value+"']").remove();
			                    });
				        		$("#designation").trigger("chosen:updated");
				        		
					        }
					    });
					    
					   	    
					    var selectsix, chosensix;
					    selectsix = $("#certification");
					    selectsix.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) Or Tab To Add Certification:' });
					    chosensix = selectsix.data('chosen');
					    chosensix.search_field.on('keydown keyup', function(e6)
					    {
					    	var cert = this.value.replace(",","");
					        if (tagItBykeyForSkills(e6) && (cert != null  && cert != '' && cert != 'undefined'))
					        {
					        	preventTabkey(e6);
					        	var par = this.parent;
					            var option = $("<option>").val(cert).text(cert);
					            selectsix.append(option);
					            selectsix.find(option).prop('selected', true);
			                    $("#certification option").each(function(){
			                          $(this).siblings("[value='"+ this.value+"']").remove();
			                    });
				        		$("#certification").trigger("chosen:updated");
				        		
					        }
					    });
					    
					    var selectseven, chosenseven;
					    selectseven = $("#employers");
					    selectseven.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) Or Tab To Add Employer:' });
					    chosenseven = selectseven.data('chosen');
					    chosenseven.search_field.on('keydown keyup', function(e7)
					    {
					    	var emp = this.value.replace(",","");
					        if (tagItBykeyForSkills(e7) && (emp != null  && emp != '' && emp != 'undefined'))
					        {
					        	preventTabkey(e7);
					        	var par = this.parent;
					            var option = $("<option>").val(emp).text(emp);
					            selectseven.append(option);
					            selectseven.find(option).prop('selected', true);
			                    $("#employers option").each(function(){
			                          $(this).siblings("[value='"+ this.value+"']").remove();
			                    });
				        		$("#employers").trigger("chosen:updated");
				        		
					        }
					    });
					    
					    var selectzero, chosenzero;
					    selectzero = $("#primarySkill");
					    selectzero.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add Primary Skill:' });
					    chosenzero = selectzero.data('chosen');
					    chosenzero.search_field.on('keydown keyup', function(e10)
					    {
					    	var prmSkill = this.value.replace(",","");
					        if (tagItBykeyForSkills(e10) && (prmSkill != null  && prmSkill != '' && prmSkill != 'undefined'))
					        {
					        	preventTabkey(e10);
					        	var par = this.parent;
					            var option = $("<option>").val(prmSkill).text(prmSkill);
					            selectzero.append(option);
					            selectzero.find(option).prop('selected', true);
			                    $("#primarySkill option").each(function(){
			                          $(this).siblings("[value='"+ this.value+"']").remove();
			                    });
				        		$("#primarySkill").trigger("chosen:updated");
				        		
					        }
					    });
					    
					    var selecteight, choseneight;
					    selecteight = $("#excludeEmployers");
					    selecteight.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) Or Tab To Add Employer:' });
					    choseneight = selecteight.data('chosen');
					    choseneight.search_field.on('keydown keyup', function(e8)
					    {
					    	var excEmp = this.value.replace(",","");
					        if (tagItBykeyForSkills(e8) && (excEmp != null  && excEmp != '' && excEmp != 'undefined'))
					        {
					        	preventTabkey(e8);
					        	var par = this.parent;
					            var option = $("<option>").val(excEmp).text(excEmp);
					            selecteight.append(option);
					            selecteight.find(option).prop('selected', true);
			                    $("#excludeEmployers option").each(function(){
			                          $(this).siblings("[value='"+ this.value+"']").remove();
			                    });
				        		$("#excludeEmployers").trigger("chosen:updated");
				        		
					        }
					    });
					    
					    
					    //Adding Customized Skills to the list
				        var selectnine, chosennine;
				        selectnine = $("#canSkills");
				        selectnine.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) Or Space To Add New Skills:' });
					    chosennine = selectnine.data('chosen');
					    chosennine.search_field.bind(('keydown keyup'), function(e9)
					    { 
					    	 
					    	 var enterSkillsArray = this.value.split(",");
					    	 if(enterSkillsArray.length > 1){
				        		 for (var i=0; i < enterSkillsArray.length; i++) {
				        			 var option = $("<option>").val(enterSkillsArray[i].replace(",","")).text(enterSkillsArray[i].replace(",",""));
				        			 selectnine.append(option);
				        			 selectnine.find(option).prop('selected', true);
				 	        		 $("#canSkills").trigger("chosen:updated");
				                 }
					    	 }else{
						    	var newSkill = this.value.replace(","," ");
						    	
						    	 
						        if (tagItBykeyForSkills(e9) && (newSkill != null  && newSkill != '' && newSkill != 'undefined'))
						        {
						        	preventTabkey(e9); 
						        	var par = this.parent;
						        		 var option = $("<option>").val(newSkill.replace(",","")).text(newSkill.replace(",",""));
						        		 selectnine.append(option);
						        		 selectnine.find(option).prop('selected', true);
							        		$("#canSkills").trigger("chosen:updated");
							        		
							        		
						        }
					    	 }
					    });
					    
					    
					  //Adding Customized Visa to the list
						var selectten, chosenten;
						selectten = $("#canVisa");
						selectten
								.chosen({
									no_results_text : 'Press Enter-Key Or Comma(,) Or Space To Add New Visa:'
								});
						chosenten = selectten.data('chosen');
						chosenten.search_field
								.bind(
										('keydown keyup'),
										function(e10) {

											var enterVisaArray = this.value.split(",");
											if (enterVisaArray.length > 1) {
												for (var i = 0; i < enterVisaArray.length; i++) {
													var option = $("<option>").val(
															enterVisaArray[i].replace(",", ""))
															.text(
																	enterVisaArray[i].replace(",",
																			""));
													selectten.append(option);
													selectten.find(option).prop('selected', true);
													$("#canVisa").trigger("chosen:updated");
												}
											} else {
												var newVisa = this.value.replace(",", " ");

												if (tagItBykeyForVisa(e10)
														&& (newVisa != null && newVisa != '' && newVisa != 'undefined')) {
													preventTabkey(e10);
													var par = this.parent;
													var option = $("<option>").val(
															newVisa.replace(",", "")).text(
															newVisa.replace(",", ""));
													selectten.append(option);
													selectten.find(option).prop('selected', true);
													$("#canVisa").trigger("chosen:updated");

												}
											}
										}); 
					    
					    
					      $("#country").on('change', function (e) {
					    	   getStates();
					       });
					       $("#state").on('change', function (e) {
					    	   getCities();
					       });
					       
					       // here call experience validation
					       $("#maxExpYears").on('change', function (e) {
					    	   minMaxExperience();
					       });
					       
					       $("#minExpYears").on('change', function (e) {
					    	   minMaxExperience();
					       });
					       
							 for (var selector in config) {
							  $(selector).chosen(config[selector]);
							}
							$('.chosen-select').each(function() {
							var $this = $(this);
							$this.next().css({'width': '100%'});
							}) 
			    //addcandidate-popup script
			    function addCandidate(){
			        $('.addcandidate-popup').fadeIn(200);
			        $('.pop_mask').fadeIn(200);
			        $('#addmorepop').slideUp(200);
			    };
			       function closeCandidate(){
			        $('.addcandidate-popup').fadeOut(200);
			        $('.pop_mask').fadeOut(200);
			       }
			//Function to validate if maxExpYears  value is more the minExpYears  value
			function minMaxExperience(){
			     var maxExpYears = ($('#maxExpYears').val());
			     var minExpYears = ($('#minExpYears').val());
			    
			    if (parseFloat(maxExpYears) <= parseFloat(minExpYears)){
			        $('#expId').find('p.jquery_form_error_message').remove(); 
					$('input#expValidation').attr('class','error signin_fild');
					$("input#expValidation").after("<p class='jquery_form_error_message'>Max Experience is greater than Min Experience</p>");
					$('input#expValidation').attr('record-exist','yes');
					$('input#expValidation').attr('record-exist-errorMsg','Max Experience is greater than Min Experience'); 
			    }  else{
			    	$('#expId').find('p.jquery_form_error_message').remove(); 
			    	$('input#expValidation').removeAttr( "class record-exist record-exist-errormsg");
					$('#expId').find('p.jquery_form_error_message').remove();
			    } 
			}


			//<!-- This  clearCandidateSearchKeys() method for clear search data-->
				function clearCandidateSearchKeys(){
					
				    $("#anyOfKeyword").val('').trigger("chosen:updated");
				    $("#allKeywords").val('').trigger("chosen:updated");
				    $("#excludeKeyWord").val('').trigger("chosen:updated");
				    $("#designation").val('').trigger("chosen:updated");
				    $("#noticePeriod").val('').trigger("chosen:updated");
				    $("#minExpYears").val('').trigger("chosen:updated");
				    $("#maxExpYears").val('').trigger("chosen:updated");
				 /*    $("#country").val('').trigger("chosen:updated"); */
				    $("#state").val('').trigger("chosen:updated");
				    $("#citySearch").val('').trigger("chosen:updated");
				    $("#education").val('').trigger("chosen:updated");
				    $("#excludeEducation").val('').trigger("chosen:updated");
				    $("#certification").val('').trigger("chosen:updated");
				    $("#employers").val('').trigger("chosen:updated");
				    $("#excludeEmployers").val('').trigger("chosen:updated");
				    $("#applicantType").val('').trigger("chosen:updated");
				    $("#canSkills").val('').trigger("chosen:updated");
				    $("#canVisa").val('').trigger("chosen:updated");
				    $("#canTags").val('').trigger("chosen:updated");
				    $("#primarySkill").val('').trigger("chosen:updated");
				    
				    $("#designationType").val('').trigger("chosen:updated");
				    
				    $('#candidatelist_form').find('p.jquery_form_error_message').remove();
				    
				     $("#anyOfKeyword").empty();
				     $("#allKeywords").empty();
				     $("#excludeKeyWord").empty();
				     $("#designation").empty();
				      $("#minRate").val(''); 
				     
				      
				     $("#currency").val('');
				     $('.selectpicker').selectpicker('refresh');
				     
				     $("#maxRate").val(''); 
				     
				     $("#rateType").val(''); 
				     $('.selectpicker').selectpicker('refresh');
				     
				     $("#minExpYears").val(''); 
				     $("#maxExpYears").val(''); 
				     $('#expId').find('p.jquery_form_error_message').remove();
				     
				     $("#noticePeriod").val(''); 
				     
				     $("#country").val(''); 
				     $("#state").val(''); 
				     $("#citySearch").val(''); 
				     
				     $("#designationType").val(''); 
				     
				     
				     $("#education").val(''); 
				     $("#excludeEducation").val('');
				     $("#certification").val(''); 
				     
				     $("#employers").val(''); 
				     $("#excludeEmployers").val(''); 
				     $("#applicantType").val('');
				     $("#canSkills").val('');
				     $("#canVisa").val('');
				     $("#canTags").val('');
				     $("#primarySkill").val('');
				    
				}

		//	<!-- End  clearCandidateSearchKeys() method for clear search data-->




			//<!-- =========================================== share scriptin By Laxman ======================================== -->
			function toggleShareCandidates(source){
				  var checkboxes = document.querySelectorAll('input[class="contactscheckbox"]');
				  if($(source).is(":checked")){
					  $('input[class="contactscheckbox"]').prop('checked', 'checked');
				  	  $(".tableShare").show();
				  	$('input:checkbox[name=sharedcheckbox]:checked').each(function(){
				  		candidateIdsMark.push($(this).attr("id"));
				 	}); 
				  }
				  else{ 
					  $('input[class="contactscheckbox"]').removeProp('checked');
				      $(".tableShare").hide();
				      $('input:checkbox[name=sharedcheckbox]not(:checked)').each(function(){
				    	  candidateIdsMark = jQuery.grep(candidateIdsMark, function(value) {
							   return value != $(this).attr("id");
							});
					 	});
				      
				  }
				  $('input[class="contactscheckbox"]').each(function(i) {
					  if($(this).is(":checked")) {
					      shareCandidates[$(this).data("id")] = $(this).val();
					  } else {
						  shareCandidates[$(this).data("id")] = "";
					  }
				  });
			}

			function checkOrUncheckParent(tableId, selectedItems, checkboxId) {
				$("#"+checkboxId).removeProp("checked");
				   var count = 0;
				   var data = $("#"+tableId).DataTable().data();
				 for(var e in selectedItems) {
				   if(!e || !selectedItems[e] || selectedItems[e] === "")
					   continue;
				   
				   for(var f in data) {
			 	   if(e === data[f].id+"") {
			 		count ++;
			 	   }
					}
				 }
				 if(count == data.count()) {
					$("#"+checkboxId).prop("checked", "checked");
				 }
			}
			var shareCandidates = {};
			function checkShareCandidates(elem) {
				if($(elem).is(":checked")) {
					 $(".tableShare").show();
					shareCandidates[$(elem).data("id")] = $(elem).val();
				} else{
					 var candidateIds = new Array();
					 for(var e in shareCandidates) {
						 if(!e || !shareCandidates[e] || shareCandidates[e] === "")
							   continue;
						 candidateIds.push(shareCandidates[e]);
					 } 
					 if(candidateIds.length - 1 == 0){
						 $(".tableShare").hide();
					 }
					$("#sharedcheckbox").removeProp('checked');
					shareCandidates[$(elem).data("id")] = "";
				}
			}
			function shareJob() {
				$("#sharejob-pop").fadeIn();
				$(".sideMask").fadeIn();
				var candidateIds = new Array();
				 for(var e in shareCandidates) {
					 if(!e || !shareCandidates[e] || shareCandidates[e] === "")
						   continue;
					 candidateIds.push(shareCandidates[e]);
				 }
				 if(candidateIds.length > 0){
					 sharejobpopup(candidateIds.length);
					getRequirements();
				 }else{
					 $("#sharejob-pop").hide();
						$(".sideMask").hide();
					    var result = "";
						result = result +"	<div class='project_inn_mask'></div>"; 
						result = result +"	   <div id='shareCandidate' class='remove_user_pop remove_user_pop_a'>";
						result = result +"		<p>Please select at least one Candidate</p>";
						result = result + ' <div id="reasonForInvoiceDiv"/>';
						result = result +'		<b href="javascript:void(0)"><input id="confirm-id" type="button" value="Ok" onclick="cancelShareCandidate();"><input class="u_can_color" type="button"  value="Cancel" onClick="cancelShareCandidate();"></b>';
						result = result +"	</div>";
						$("#shareEmptypopUp").empty();
						$("#shareEmptypopUp").append(result);
						$("#shareCandidate").slideDown(200);
						$(".project_inn_mask").fadeIn(200); 
				 }
				
				}
			function cancelShareCandidate(){
				$("#shareCandidate").fadeOut();
				$(".project_inn_mask").fadeOut(); 
			}
			function closejobpop() {
				$("#sharejob-pop").fadeOut();
				$(".sideMask").fadeOut();
				}
			function sharejobpopup(candidateCount) {
				var result = "";
				result = result+ '<form method="post">';
				 result = result+ '<div  id="shareJob-form" name="shareJob-form" class="modal-dialog">';
				 result = result+ '<div class="modal-content">';
				 result = result+ '  <div class="modal-header positionRelative">';
				 result = result+ '    <button type="button" class="close" data-dismiss="modal" onclick="closejobpop();">&times;</button>';
				 result = result+ '    <h4 class="modal-title col-md-3">Share Job</h4>';
				 result = result+ '    <h5 class="modal-title col-md-8">Selected Candidates:<span id="selectedcandidatescount" class="paddingLeft">'+candidateCount+'</span></h5>';
				 result = result+ '  </div>';
				 result = result+ '  <div class="modal-body">';
				 result = result+ '    <div class="clearfix mb">';
				 result = result+ '    <label class="col-md-3">Posted from <span class="text-danger">&nbsp;*</span></label>';
				 result = result+ '    <div class="dropdown-select col-md-9">';
				 result = result+ '    <select class="form-control" id="requirementsTimeRange" onchange="getRequirements();rolecustomedates();">';
				 result = result+ '    <option value="today">Today</option>';
				 result = result+ '    <option value="yesterday">1 Day Ago</option>';
				 result = result+ '    <option value="1_week" selected="">1 week</option>';
				 result = result+ '    <option value="2_weeks">2 weeks</option>';
				 result = result+ '    <option value="1_month">1 month</option>';
				 result = result+ '    <option value="2_months">2 months</option>';
				 result = result+ '    <option value="custom">Custom</option>';
				 result = result+ '    </select>';
				 result = result+ '    </div>';
				 result = result+ '  </div>';
				 result = result+ '  <div class="clearfix mb" id="customedatesDiv" style="display:none">';
				 result = result+ '    <label class="col-md-3">Custom Dates <span class="text-danger">&nbsp;*</span></label>';
				 result = result+ '    <div class="col-md-9">';
				 result = result+ '    <div class="row">';
				 result = result+ '    <div class="col-md-6" id="StartErrMsg">';
				 result = result+ '    <input type="text" class="form-control calendar" id="shareStartDate" placeholder="Start Date" field-name="Start Date" readonly>';
				 result = result+ '    </div>';
				 result = result+ '    <div class="col-md-6" id="EndErrMsg">';
				 result = result+ '    <input type="text" class="form-control calendar" id="shareEndDate" placeholder="End Date" field-name="End Date" readonly>';
				 result = result+ '    </div>';
				 result = result+ '    </div>';
				 result = result+ '    </div>';
				 result = result+ '  </div>';
				 
				 result = result+ '  <div class="clearfix mb">';
				 result = result+ '    <label class="col-md-3">Managers</label>';
				 result = result+ '    <div class="dropdown-select col-md-9">';
				 result = result+ '    <select class="form-control chosen-select" id="requirementManagerlist" onchange="getRequirements();">';
				 result = result + '    <option value=""> -- Select Manager -- </option>';
				 result = result+ '        <c:forEach var="report" items="${requirementApproverList}">';
				 result = result+ '	         	<option value="${report.id}">${report.firstName}&nbsp;${report.lastName}</option>';
				 result = result+ '        </c:forEach>  ';
				 result = result+ '    </select>';
				 result = result+ '    </div>';
				 result = result+ '  </div>';
				 
				 result = result+ '  <div class="clearfix mb">';
				 result = result+ '    <label class="col-md-3">Job<span class="text-danger">&nbsp;*</span></label>';
				 result = result+ '    <div class="dropdown-select col-md-9" id="ErrMsgJob">';
				 result = result+ '    <select data-live-search="true" class="form-control selectpicker" id="shareRequirementsList" field-name="Job" data-validation="required" onchange="removeErrMsg();">';
				 result = result+ '    </select>';
				 result = result+ '    </div>';
				 result = result+ '  </div>';
				 result = result+ '  </div>';
				 result = result+ '  <div class="modal-footer">';
				 result = result+ '    <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="selectedJobSharing();">Share Job</button>';
				 result = result+ '    <button type="button" class="btn btn-default" data-dismiss="modal" onclick="closejobpop();">Cancel</button>';
				 result = result+ '  </div>';
				 result = result+ '</div>';
				 result = result+ '</div>';
				 result = result+ '</form>';
				 $("#sharejob-pop").empty();
				 $("#sharejob-pop").append(result);
				 
				 $("#shareRequirementsList").selectpicker('refresh');
				 
				 $("#requirementManagerlist").chosen();
				 $("#requirementManagerlist").trigger("chosen:updated");
				 
				 
				 var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
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
				 
				  
				  $('#shareStartDate').datepicker({
						dateFormat : finalDateFormatProjectSave,
						changeMonth: true,
						changeYear: true, 
						onSelect : function(selected) {
							var dt = $(this).datepicker('getDate', '+1d');
							dt.setDate(dt.getDate());
							$("#shareEndDate").datepicker("option", "minDate", dt);
							removeErrsgStartDate();
							getRequirements();
						}
					});
				  
				  $('#shareEndDate').datepicker({
						dateFormat : finalDateFormatProjectSave,
						changeMonth: true,
						changeYear: true, 
						onSelect : function(selected) {
							var dt = $(this).datepicker('getDate', '-1d');
							dt.setDate(dt.getDate());
							$("#shareStartDate").datepicker("option", "maxDate", dt);
							removeErrEndDate();
							getRequirements();
						}
					});
			}
			function selectedJobSharing(){
				if ($('#shareJob-form').validate(false, validationSettings)) {
					var requirementId = $("#shareRequirementsList").val();
					var candidateIds = new Array();
					 for(var e in shareCandidates) {
						 if(!e || !shareCandidates[e] || shareCandidates[e] === "")
							   continue;
						 candidateIds.push(shareCandidates[e]);
					 }
					 $("#recommendationsLoading").attr('style','position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
					 $.ajax({
				          type : "POST",
				          url: atsUrl+"/shareAllRecommendations?"+tokenParamName+"="+tokenValue+"&candidateIds="+candidateIds+"&requirementId="+requirementId,    
				           success :function(data) {
				        	   closejobpop();
				        	    $("#recommendationsLoading").removeAttr("style");
				        	    $("#selector4").removeAttr("style");
				       			$("#selector4").css({ display: "block" });
				       			$("#eduMsgDiv").html("Great ! you have successfully shared this job");
				       			shareMsgFadeinFunction();
				       			toggleShareCandidates();
				           },
				   }); 
			      return true;
					
				}else {
					return false;
				}
			}
			$('body').on('blur', '#shareJob-form', function() {
				$("#shareJob-form").showHelpOnFocus();
				$("#shareJob-form").validateOnBlur(false, validationSettings);
			});
				function shareMsgFadeinFunction() {
					$("#selector4").fadeIn(200);
			        $('#selector4').delay(2000).fadeOut('slow');
				}
			//Role custome Dates
			function rolecustomedates(){
				var roleval = $("#requirementsTimeRange").val();
				if(roleval == "custom"){
					document.getElementById("customedatesDiv").style.display ="block";
					 $('#shareStartDate').attr("data-validation","required"); 
					 $('#shareEndDate').attr("data-validation","required"); 
				}else {
					document.getElementById("customedatesDiv").style.display ="none";
					$('#shareStartDate').removeAttr("data-validation","required");
					$('#shareEndDate').removeAttr("data-validation","required");
				}
			}

			function removeErrsgStartDate(){
				$("#StartErrMsg").find("p.jquery_form_error_message").remove();
			}
			function removeErrEndDate(){
				$("#EndErrMsg").find("p.jquery_form_error_message").remove();
			}
			function removeErrMsg(){
				$("#ErrMsgJob").find("p.jquery_form_error_message").remove();
			}
			function getRequirements(){
				var postedDateRange =$("#requirementsTimeRange").val();
				var startDate = $("#shareStartDate").val();
				var endDate = $("#shareEndDate").val();
				var managerId = $("#requirementManagerlist").val();
				$.ajax({
			        type : "GET",
			        url: sourceleadUrl+"/getAllRequirementsForJobSharing?postedDateRange="+postedDateRange+"&startDate="+startDate+"&endDate="+endDate+"&managerId="+managerId,    
			         success :function(json) {
			              if(json != null && json != 'undefined' && json != "" && json != '[]'){
			            	  var result = "";
			            	  result = result + '<option value="">--- Select Job ---</option>';
			                  for (var i = 0; i < json.length; i++) {
			                 	 var opt = json[i]; 
			                 	 result = result +  '<option value="'+opt.id+'"> '+opt.generatedId+' - '+opt.jobTitle+' ('+opt.postedDateStr+')</option>';
			                  }
			                  $("#shareRequirementsList").empty();
			                  $("#shareRequirementsList").append(result);
			                  $("#shareRequirementsList").selectpicker('refresh');
			              }else{
			            	  var result = "";
			            	  result = result + '<option value="">--- Select Job ---</option>';
			            	  $("#shareRequirementsList").empty();
			                  $("#shareRequirementsList").append(result);
			                  $("#shareRequirementsList").selectpicker('refresh');
			              }
			           
			       },
			    });

			}
			function closeCandidateFilterPop(){
				$("#candidateFilterPopup").hide();
				$(".advancedsearch-mask").fadeOut();
			}
			function openCandidateFilterPop(){
				$("#candidateFilterPopup").toggle();
				 $(".advancedsearch-mask").fadeIn();
			}
			function resetCandidateFilter(){
				clearCandidateSearchKeys();
				$("ul#TypeFilter > li input:radio").prop("checked",false);
				$("ul#AllTypeFilter > li input:radio").prop("checked",false);
				$("ul#UpdatedFilter > li input:radio").prop("checked",false);
				$("ul#employmentFilter > li input:checkbox").prop("checked",false);
				$("ul#availabilityFilter > li input:checkbox").prop("checked",false);
				$("ul#skillsUL > li input:checkbox").prop("checked",false);
				$("ul#candidateTypeFilter > li input:checkbox").prop("checked",false);
				$("ul#createdByTypeFilter > li input:radio").prop("checked",false);
				
				
				$("#dataTableSearch").val('');
				document.getElementById("candidateTypeAll").checked = true;
				document.getElementById("candidateAll").checked = true;
				document.getElementById("candidateCheck").checked = true;
				document.getElementById("myCandidatesRadio").checked = true;
				candidateList();
			}

				
			function mySkillsFunction() {
			    var input, filter, ul, li, a, i;
			    input = document.getElementById("mySkillsInput");
			    filter = input.value.toUpperCase();
			    ul = document.getElementById("skillsUL");
			    li = ul.getElementsByTagName("li");
			    for (i = 0; i < li.length; i++) {
			        a = li[i].getElementsByTagName("label")[0];
			        if (a.innerHTML.toUpperCase().indexOf(filter) > -1) {
			            li[i].style.display = "";
			        } else {
			            li[i].style.display = "none";

			        }
			    }
			}

			$(document).keyup(function(e) {
				  if (e.keyCode === 27) {
					  closeCandidateFilterPop();
				  }
				});
			function getActions(){
				$(".action-pop").slideToggle(200);
				$(".newActPop").slideUp(200);
			}	
			$("html").click(function(){
				$(".action-pop").slideUp(200);
			});
			$(".action, .action-pop").click(function(e){
			 	e.stopPropagation();
			});	

			function newActions(){
				$(".newActPop").slideToggle(200);
				$(".action-pop").slideUp(200);
			}
			$("html").click(function(){
				$(".newActPop").slideUp(200);
			});
			$(".overIcon, .newActPop").click(function(e){
			 	e.stopPropagation();
			});	
				/* function goToGlaobalMarketList() {
					alert("in global function");
					 $.ajax({
						type : "GET",
						url : atsUrl + "/globalMarketplace?" + tokenParamName + "=" + tokenValue,
						success : function(result) {
							$('#candidatesInfoDiv').html(result);
							$('#brudCrumLbl').html("Global Marketplace");
							$('#headerLbl').html("Global Marketplace List");
						},
					}); 
				}  */
			function goToCandidateList(){
				location.href = atsUrl+"/candidatesList";
			}
				
			function goToGlobalMarketPlaceList(){
				location.href = atsUrl+"/globalMarketplacePage";
			}

			function goToOrgMarketPlaceList(){
				location.href = atsUrl+"/OrgMarketplace";
			}
			function goToPurchaseOrderList(){
				location.href =atsUrl+"/PurchaseOrderList";
				}
			function goToSalesOrderList(){
				location.href = atsUrl+"/SalesOrderList";
			}
			function goToCartList(){
				location.href = atsUrl+"/candidateMarketplaceCart";
			}
			function goToWalletList(){
				location.href = atsUrl+"/walletList";
			}
// updated Here 1213.
// candidateTable.js scripts start here.
function candidateListCheck(searchValue,universalCanddateSearchList,candidateKeyword,candidateIdSearchId){
	if(universalCanddateSearchList=="universalCanddateList")	 {
		$("#dataTableSearch").val(searchValue);
		candidateList();
	} else if(candidateKeyword == "candidateSearch"){
		candidateSideBar(candidateIdSearchId);
		candidateQuickViewProfile(candidateIdSearchId);
		candidateList();
		$("#upcandidateStatus").val('');
	} else if(candidateKeyword == "requestedDocumentNotification"){
		candidateSideBar(candidateIdSearchId);
		candidateQuickViewProfile(candidateIdSearchId);
		candidateList();
		$("#upcandidateStatus").val('');
	} else {
		candidateList();
		$("#upcandidateStatus").val('');
	}
 }
 


function candidateList(type){
	  var candFilterRadio = $('input[name=candidateStatus]');
	  var candidateStatus = candFilterRadio.filter(':checked').val();
	  
	  if(candidateStatus == 1){
         $("#canDiv").text("Active Candidates");
     }else if(candidateStatus == 0){
   	  $("#canDiv").text("In Active Candidates");
     }else if(candidateStatus == 2){
   	  $("#canDiv").text("All Candidates");
     }else{
         $("#canDiv").removeAttr("onClick");
     }
	  
	 var employmnetTypes = [];
	 $('input:checkbox[name=employmentType]:checked').each(function() 
    {
		employmnetTypes.push($(this).val());
	 });
	 
	var candidateFilterSkills = [];
	 $('input:checkbox[name=candidateFilterSkills]:checked').each(function() 
   {
		 candidateFilterSkills.push($(this).val());
	 });
	 
	 
	 var availability = []
	 $('input:checkbox[name=availability]:checked').each(function() 
	     {
	       availability.push($(this).val());
		 });
	 
	 var candidateTypeFilter = []
	 $('input:checkbox[name=candidateTypeFilter]:checked').each(function() 
	     {
		 candidateTypeFilter.push($(this).val());
		 });
	 
	 
	 
	  var candTypeRadio = $('input[name=candidateType]');
	  var candidateType = candTypeRadio.filter(':checked').val();
	  
	 var createdByRadio = $('input[name=createdByFilter]');
	  var createdByFilter = createdByRadio.filter(':checked').val();
	  
	 	var upcandidateStatus =   $('input[name=upcandidateStatus]:checked').val();
		  
	 	  $("#candidateList").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	 	 
		
		var minRate = $('#minRate').val(); 
		var maxRate = $('#maxRate').val();
		 if((minRate != null && minRate != "" && minRate !='undefined') || (maxRate != null && maxRate != "" && maxRate !='undefined')){
			 $('#rateType').removeAttr('data-validation-optional');
			 $('#rateType').attr("data-validation","required"); 
			 
			 $('#currency').removeAttr('data-validation-optional');
			 $('#currency').attr("data-validation","required"); 
		 }else{
			 $('#rateType').removeAttr('data-validation');
			 $('#rateType').attr("data-validation-optional","true"); 
			 
			 $('#currency').removeAttr('data-validation');
			 $('#currency').attr("data-validation-optional","true"); 
		 }
		var validationSettings = {
				errorMessagePosition : 'element',
				borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd'
			};
		
		 if ($('#candidatelist_form').validate(false, validationSettings)){ 
			
			var frm = $('#candidatelist_form').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, ""); 
		    var jsonData = encodeURIComponent(con);
			var form = $('#candidatelist_form')[0];
	  		var formData = new FormData(form); 
	  		var searchValue = $("#dataTableSearch").val();
	  		searchValue = searchValue ? searchValue : "";
	  	//skip % (30%) in profile percentage	
	  		if(searchValue.endsWith('%')){
	  			searchValue=searchValue.split('%')[0];
	  		}
	  		
	  		var url = sourceleadUrl+"/searchCandidate?"+_csrfParam+"="+_csrfToken+"&candidateStatus="+candidateStatus+"&searchValue="+searchValue+"&updatedDateRange="+upcandidateStatus+"&isAssociate="+isAssociate+"&candidateType="+candidateType+"&employmnetTypes="+employmnetTypes+"&candidateFilterSkills="+candidateFilterSkills+"&availability="+availability+"&candidateTypeFilter="+candidateTypeFilter+"&createdByFilter="+createdByFilter;
	  		url += "&"+$($('#candidatelist_form')[0]).serialize();
	  		if(type == 'gMarketPlace'){
	  			callMarketPlaceCandidates(url)
	  		}else{
	  			prepareCandidateList(url);
	  		}
			return true;
		 }else{ 
				return false;
		 } 
	} 
$('body').on('blur', '#candidatelist_form', function() {
	$("#candidatelist_form").showHelpOnFocus();
	$("#candidatelist_form").validateOnBlur(false, validationSettings);
});

function callMarketPlaceCandidates(urlGlobal){
	$.ajax({
		type : "POST",
		url : urlGlobal,
		success : function(object) {
			var candidateIdsArray = [];
			if(object.data != null && object.data.length != 0){
				for(var i=0;i<object.data.length;i++){
					var objectArray = object.data[i]
					candidateIdsArray.push(objectArray.candidateId)
				}
			}
			getGloblaMarketPlaceCandidates(globalMarketDivType, candidateIdsArray);
			candidateIdsArray = [];
		}
	});
}

function prepareCandidateList(url){
	     $("#candidateList").removeAttr("style");
 	 $("#candidateList").empty();
 	  var result = "";
 	  result = result +"<table id='candidatePagination' class='display' cellspacing='0' width='100%' >"; 
      result = result +"<thead>"; 
      result = result +"  <tr>"; 
	      result = result +'<th class="text-center"><input type="checkbox" id="sharedcheckbox" onclick="toggleShareCandidates(this);"/></th>'; 
      result = result +"    <th>&nbsp;</th>";
      result = result +"    <th>Generated ID</th>";
      result = result +' <div class="tbldata-dotdot-vsm"> <th>Name</th></div>'; 
      result = result +"    <th>Primary Skill</th>"; 
      result = result +"    <th>Employment Type</th>"; 
      result = result +"    <th>Email</th>"; 
      result = result +"    <th>Mobile Number</th>";
      result = result +"    <th>Location</th>";
      result = result +"    <th>Last Update</th>"; 
      result = result +"    <th>Profile %</th>"
      result = result +"    <th>No Of Jobs</th>"; 
      result = result +"    <th>Created By</th>"; 
      result = result +"  </tr>"; 
      result = result +"</thead>";
      result = result +"<tbody>"; 
      result = result +" </tbody>";
      result = result +"</table>";
      $("#noCandidates").val(1);
      $("#candidateList").append(result);
      cpagination(url); 
      shareCandidates = {};
}


/*function candidateSearchHighlight(){ // need to check later
	 setTimeout(function () {
		var body = $( table.table().body() );
	        body.unhighlight();
	        body.highlight( $("#dataTableSearch").val() );   
			
			}, 1000); 
}
*/
	$(document).on( 'init.dt.dth', function (e, settings, json) {
 table = new $.fn.dataTable.Api( settings );
 var body = $( table.table().body() );
 if ( $( table.table().node() ).hasClass( 'searchHighlight' ) || settings.oInit.searchHighlight ||  $.fn.dataTable.defaults.searchHighlight ) {
	
	}
});

function cpagination(url){ 
var colIndex = 0;
/*$.fn.dataTable.moment( 'DD-MM-YYYY' );*/
$('#candidatePagination').DataTable( { 
		"processing": true,
    "serverSide": true,
    "searching": false,
    "searchHighlight": true,
    "ajax": $.fn.dataTable.pipeline( {
        url: url,
        method: 'POST',
        pages: 10
    }),
    "columns": [
    	{ "data": "id" },
        { "data": "isAssociate" },
        { "data": "generatedId"},
        { "data": "firstName"},
        { "data": "primarySkill" },
        { "data": "designationType"},
        { "data": "email" },
        { "data": "mobilePhone" },
        { "data": "country"},
        { "data": "updatedDate" },
        { "data": "profilePercentage" },
        { "data": "noOfJobs"},
        { "data": "createdBy" }
    ],
    "columnDefs": [ {
	  	"targets": colIndex++,
	  	"sortable": false,
	  	"render": function(data, type, report, meta) {
	  		 var result = "";
	  		 var shareflag = false;
        		 for(var e in shareCandidates) {
	     	    	   if(!e || !shareCandidates[e] || shareCandidates[e] === "")
	     				   continue;
	     	    	   if(e === report.candidateId+"") {
	     	    		    if(!$("#sharedcheckbox").is(":checked"))
	     	    		 	checkOrUncheckParent("candidatePagination",shareCandidates, "sharedcheckbox"); 
	     	    		    shareflag = true;
	     	    	   	break;
	     	    	   }
	        		 }
        		 if(!shareflag)
	        			$("#sharedcheckbox").removeAttr('checked');
        			result = result +'  <td><input'+(shareflag ? " checked='checked'" : "")+' class="contactscheckbox" name="sharedcheckbox"  type="checkbox" id="'+report.candidateId+'" data-id="'+report.candidateId+'" value="'+report.candidateId+'" onclick="appendIdsToMarketPlace(this);" data-uuid="'+report.candidateUUID+'" onchange="checkShareCandidates(this)"/></td>';
	  		 return result; 
	  	}
    	},{
    	  	"targets": colIndex++,
    	  	//"className": "tbldata-dotdot_50",
    	  	"data": "isAssociate",
    	  	"sortable": false,
    	  	"render": function(data, type, full, meta) {
    	  		var result = "";
    	  		var isFavourite = "";
    	  		var isFavClass = "";
    	  		var title = "title = 'Add as Favourite'";
    	  		if(full.isFavourite == '1' && isAssociate == "N"){
            		isFavourite = "checked";
            		isFavClass = "activestar-rating";
            		title = "title = 'Change to un Favourite'";
            	} else if(full.isFavouriteAssociate == '1' && isAssociate == "Y") {
            		isFavourite = "checked";
            		isFavClass = "activestar-rating";
            	}
    	  		result = result +"<div class='tblminwidth-75 pull-left'>";
    	  		if(data == "Y" && isAssociate == 'N'){
    	  			result = result +"&nbsp;<i class='fa fa-black-tie pull-right mt10' title='Added as Associate'></i> "; 
    	  			if(full.isSharedInMarketplace == 'Y'){
  	    	  		  result = result +"&nbsp;<i class='fa fa-address-book-o pull-right mt10 mr10' title='Shared in Marketplace'></i> "; 
  	    	  		}
	             }
    	  		else if(full.isSharedInMarketplace == 'Y'){
	    	  		  result = result +"&nbsp;<i class='fa fa-address-book-o pull-right mt10 mr25' title='Shared in Marketplace'></i> "; 
	    	  	}
    	  		
    	  		
	              result = result +"<div class='iconClass pull-left star-rating "+isFavClass+"' id='favourite"+full.candidateId+"'>";
	              if(associateFlag)
	            	  	result = result +"   <input type='checkbox' id='fav"+full.candidateId+"' "+isFavourite+" "+title+" onclick='addFavourite("+full.candidateId+")'></input>";
	             
	              result = result +" <label></label>";
	              result = result +"</div>";
	              result = result +"</div>";
	              return  result;
    	  	}
      },{
    	  "targets": colIndex++,
          "data": "generatedId",
          "render": function ( data, type, full, meta ) {
        	  
          		var result = "";
          		if(full.generatedId != null && full.generatedId != '' && full.generatedId != "undefined"){
	            	  		result = result +"<div><span title='"+full.generatedId+"'>"+full.generatedId+"</span></div>";
	              	}else{
	            	  		result = result +"<div>--</div>";
	             	}
	              return result;
          }
          },{
        "targets": colIndex++,
        "data": "firstName",
        "render": function ( data, type, full, meta ) {
        		var result = "";
        		var lastName = "";
        		var middleName = "";
        		if(full.lastName != null && full.lastName != "" && full.lastName != 'undefined'){
            		lastName = full.lastName;
            	}else{
            		lastName = "";
            	}
        		if(full.middleName != null && full.middleName != "" && full.middleName != 'undefined'){
            		middleName = full.middleName;
            	}else{
            		middleName = "";
            	}
        		if(name1Flag)
                result = result +"  <td><div class='proj_show divmax-width' onclick='candidateQuickViewProfile("+full.candidateId+");'><span title='"+full.firstName+" "+middleName+"  "+lastName+"'>"+full.firstName+" "+middleName+"  "+lastName+"</span><i class='fa fa-binoculars'></i></div></td>";
              
              if(name2Flag)
               result = result +"  <td><div class='proj_show' onclick='#'><span title='"+full.firstName+" "+middleName+" "+lastName+"'>"+full.firstName+" "+middleName+" "+lastName+"</span></div></td>";
              return result;
        }
      }, {
          "targets": colIndex++,
          "data": "primarySkill",
          "render": function ( data, type, full, meta ) {
          		var result = "";
          		if(full.primarySkill != null && full.primarySkill != '' && full.primarySkill != "undefined"){
	            	  		result = result +"<div><span title='"+full.primarySkill+"'>"+full.primarySkill+"</span></div>";
	              	}else{
	            	  		result = result +"<div>--</div>";
	             	}
	              return result;
          }
        }, {
            "targets": colIndex++,
            "data": "designationType",
            "render": function ( data, type, opt, meta ) {
            		var result = "";
            		if(opt.designationType != null && opt.designationType != '' && opt.designationType != "undefined")
	             	 {
	            	  if(opt.designationOtherType !=null && opt.designationOtherType !='' && opt.designationOtherType !="undefined"){
	                   result = result +"<div>"+opt.designationOtherType+"</div>";
	             	 }else{
	             		result = result +"<div>"+opt.designationType+"</div>"; 
	             	 }
	             	 }else{
	             		 result = result +"<div>--</div>"; 
	             	 }
	              return result;
            }
      },{
    	  	"targets": colIndex++,
    	  	"data": "email",
    	  	"render": function(data, type, opt, meta) {
    	  		return "<div ><span title='"+opt.email+"'>"+opt.email+"</span></div>";
    	  	}
    	  },{
    	  	"targets": colIndex++,
    	  	"data": "mobilePhone",
    	  	"sortable": false,
    	  	"render": function(data, type, full, meta) {
    	  		var mobilePhone = "";
    	  		if(full.mobilePhone != null && full.mobilePhone != 'undefined' && full.mobilePhone != ""){
         		       mobilePhone = full.mobilePhone;
	            	}else{
	            		mobilePhone = "--";
	            	}
    	  		return "<div class='tbldata-dotdot-vsm'>"+mobilePhone+"</div>";
    	  	}
    	  },{
              "targets": colIndex++,
              "data": "country",
              "render": function ( data, type, opt, meta ) {
              		var location = "";
              		if(opt.country != null && opt.country != "" && opt.country != 'undefined'){
	            			location = opt.country;
		            		if(opt.state != null && opt.state != "" && opt.state != 'undefined'){
		            			 location = opt.state+", "+location;
			            	}
		            		if(opt.city != null && opt.city != "" &&  opt.city != 'undefined'){
		            			 location = opt.city+", "+location;
			            	}
	            		}else{
	            			location = "--";
	            		}
  	              return "<div><span title='"+location+"'>"+location+"</span></div>";
              }
            },{
              "targets": colIndex++,
              "data": "updatedDate",
              "className": "tblminwidth-75",
              "render": function ( data, type, opt, meta ) {
              		var result = "";
              		if(opt.candidateUpdateDateToString != null && opt.candidateUpdateDateToString != 'undefined' && opt.candidateUpdateDateToString != ""){
              			result = "<div>"+opt.candidateUpdateDateToString+"</div>"; 
		            	}else{
		            		result = "<div>--</div>";
		            	}
  	              return result;
              }
            },{
	    	  	"targets": colIndex++,
	    	  	"data": "profilePercentage",
	    	  	"sortable": false,
	    	  	"render": function(data, type, opt, meta) {
		            	
	    	  		return "<div class='profilePercentageClass"+opt.candidateId+"'><div id='"+opt.candidateId+"profilePercentage'><i class='fa fa-spinner fa-pulse'></i></div></div>";
	    	  		
	    	  	}
	    	  }, {
    	    	  	"targets": colIndex++,
    	    	  	"data": "noOfJobs",
    	    		"sortable": false,
    	    	  	"render": function(data, type, opt, meta) {
    	    	  	 return "<div class='tbldata-dotdot-vsm submittedRequirementCount' id='"+opt.candidateId+"'><a href='#' onclick='getSubmittedJobsList("+opt.candidateId+")'><div id='"+opt.candidateId+"submittedRequirementCount'><i class='fa fa-spinner fa-pulse'></i></div></div></a></div>";
    	    	  		
    	    	  	}
	    	  },{
                "targets": colIndex++,
                "data": "createdBy",
                "className": "tblminwidth-75",
                "render": function ( data, type, opt, meta ) {
                		var result = "";
                		if(opt.createdBy != null && opt.createdBy != 'undefined' && opt.createdBy != ""){
                			return "<div><span title='"+opt.createdBy+"'>"+opt.createdBy+"</span></div>";
			            	}else{
			            		result = "<div>--</div>";
			            	}
    	              return result;
                }
              } ],
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
    	"aaSorting": [[9,'desc']],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": "", 
     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
     },
     drawCallback: function(){
	      $('a.paginate_button', this.api().table().container()).on('click', function(){
	    	candidateSearchHighlight();
         }); 
	   getCandidateSubmittedJobsCount();
   }
} );
$("#candidatePagination").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
$("input[type=search]").hide();
$("#dataTableSearch").keyup(function(e) {
	   if(e.which === 13) {
	candidateList();
	candidateSearchHighlight();
	   }
	});
}


function getCandidateSubmittedJobsCount(){
 $('.submittedRequirementCount').each(function() {
	 var candidateId = this.id;
	 if(candidateId != null &&  candidateId !='undefined' && candidateId != ''){
		 $.ajax({
	           type : "GET",
	           url: sourceleadUrl+"/getCandidateSubmittedRequirementCount?candidateId="+candidateId,    
	            success :function(data) {
	            	  $("#"+candidateId+"submittedRequirementCount").empty();
	             	  $("#"+candidateId+"submittedRequirementCount").html(data.submittedJobsCount);
	             	  
	             	  $("#"+candidateId+"profilePercentage").empty();
	             		
	             	      if(parseInt(data.profilePercentage) >= 50){
	             	    	 $(".profilePercentageClass"+candidateId).addClass("tbldata-dotdot-vsm text-green");
		    	  			}else{
		    	  				 $(".profilePercentageClass"+candidateId).addClass("tbldata-dotdot-vsm colorRed");
		    	  			}
	             	  $("#"+candidateId+"profilePercentage").html(data.profilePercentage+ "%");
	             	  
	             	 
	            }
		    });
	 }
		
});  
}

function getSubmittedJobsList(candidateId){
$.ajax({
	  type: "GET",
	    url: sourceleadUrl+"/getCandidateSubmittedJobsList?candidateId="+candidateId,
	     success :function(response) {
	    	    $("#candidateSubmittedJobsDiv").empty();  
	    	    $("#candidateSubmittedJobsDiv").append(response);
	    	    $(".modal.paymentaccount-modal").fadeIn();
	    	    $(".comMask").fadeIn();
			},
		});
}
// candidateTable.js END HERE==============>