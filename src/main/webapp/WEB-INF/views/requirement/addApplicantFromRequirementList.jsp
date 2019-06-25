<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<link rel="stylesheet" href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/style.css?staticTime=${staticTime}" />
<link rel="stylesheet" href="${SourceLeadWeb_sourceleadDomainUrl}resources/css/chosen.min.css">
<script src="${SourceLeadWeb_atsUrl}/resources/js/submissionFormBuilder.js" type="text/javascript" ></script> <!-- form builder -->
<script src="${SourceLeadWeb_atsUrl}/resources/js/submissionForm-html-generator.js" type="text/javascript" ></script> <!-- form generator -->

<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<style type="text/css">
.mt10 {
    margin-top: 10px !important;
}
</style>
<script>
var atsUrl ='${SourceLeadWeb_atsUrl}';
var vmsUrl ='${SourceLeadWeb_vmsUrl}';
var sourceleadUrl = '${SourceLeadWeb_sourceleadUrl}';
</script>

<!-- For add New Applicant -->
<script>
function getAddApplicant(requirementId,reqCoolingOffPeriod){
	$('.action-pop').slideUp();
	var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
	  var finalDateFormatScript = '';
	  if(locationDate.match("yyyy-MM-dd")){
		  finalDateFormatScript = 'yy-mm-dd';
	  }if(locationDate.match("dd-MM-yyyy")){
		  finalDateFormatScript = 'dd-mm-yy'; 
	  }if(locationDate.match("MM-dd-yyyy")){
		  finalDateFormatScript = 'mm-dd-yy'; 
	  }if(locationDate.match("yyyy-dd-MM")){
		  finalDateFormatScript = 'yy-dd-mm'; 
	  }
	generateOrgSubmissionForm(atsUrl,requirementId,finalDateFormatScript)
	var result = "";
	result = result + '<form id="addApplicant_form" name="addApplicant_form" enctype="multipart/form-data" method="post">';
	result = result + '<h3>Add Applicant</h3>';
	result = result + '<div class="form-horizontal clearfix select-field applicant-label">';
	result = result + '<input type="hidden" id="requirementId" name="requirementId" value='+requirementId+'>';
	result = result + '<div class="row">';
	result = result + '<div class="venk clearfix">';
	result = result + '<div class="col-md-7 radioInline">';
	result = result + '<label class="mr"><input type="radio" checked="checked" value="N" name="candidateType" onClick="getCandidatesList('+requirementId+',\'N\');">Candidates</label>';
	
	result = result + '<label class="mr"><input type="radio" value="New candidate" name="candidateType" onClick="getCandidatesList('+requirementId+',\'New\');getCandidateSelectionType();">Add New Candidate</label>';
	result = result + '<label class="mr"><input type="radio" value="Parese candidate" name="candidateType" onClick="getCandidatesList('+requirementId+',\'parseCandidate\');getCandidateSelectionType();">Import Candidate</label>';
	result = result + '</div>';	
	
	result = result + '<div class="col-md-5 pull-right">';
	result = result + '<div class="col-md-12" id="browseDiv"  style="display: none;">';
	result = result + '<div class="input-group" id="uploadFileGroup">';
	result = result + '<label class="input-group-btn"  onchange="importAddApplicantDoc();"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File';
	result = result + '<input type="file" style="display: none;" name="locationMapFile" multiple id="uploadBtn"  class="upload">';
	result = result + '</span> </label>';
	result = result + '<input type="text" class="form-control" data-validation="validatedoc" data-validation-optional="true" field-name="Resume" readonly id="uploadFile" placeholder="Upload Resume">';
	result = result + '</div>';
	result = result + '</div>';	
	result = result + '</div>';
	
	result = result + '<div class="clearfix"></div>';
	result = result + '<div class="col-md-6 mt10">';
	result = result + '<div id="selectCandidateDiv" style="display: block;">';
	result = result + '<select class="form-control chosen-select" onchange="getCandidateDetails(); getCandidateResumes();"  name="candidateId" id="candidateId" data-validation="required" field-name="Applicant" class="form-control  block mt10">';
	result = result + '<option value="">-- Select Candidate --</option>';
	result = result + '</select>';
	result = result + '</div>';	
	result = result + '</div>';	
	
	result = result + '<div class="panel-border_a col-md-5 mt10" id="resumesDiv"  style="display: none;">';
	result = result + '<select id="candidateResumesList" name="candidateResume" data-validation-optional="true" field-name="Resume" class="form-control block">';
	result = result + '</select>';
	result = result + '</div>';
	
	result = result + '<div id="orConditionDiv" class="text-center text-info col-md-2" style="display: none;">';
	result = result + '------- ( OR ) -------';
	result = result + '</div>';
	
	
	
	
	result = result + '</div>';
	
	result = result + '<div id="sjfb-fields" class="viewTemplate"> <div id="loading"></div> <div id="emailDiv"></div>';
	
	result = result + '</div>';
	
                        
	result = result + '</div>';
	
	
	result = result + '<div class="row">';
    result = result + '	<div class="col-md-6 upresume mb" id="skillsDiv" style="display: none;">';
	result = result + '<label class="col-md-4">Skills</label>';
	result = result + '<div class="col-md-8">';
	result = result + '<select multiple data-placeholder="select Skills" class="chosen-select form-control" name="candidateSkills" id="candidateSkills"  data-validation-optional="true">';
	result = result + '<c:forEach var="skill" items="${skillList}">';
	result = result + "<option value='${skill.name}'>${skill.name}</option>";
	result = result + ' </c:forEach>';
	result = result + '</select>';
	result = result + '		 </div>';
	result = result + '	 </div>';
	result = result + '	 </div>';
	
	result = result + '<div class="row">';
    result = result + '	<div class="col-md-6 upresume mb" id="primarySkillDiv" style="display: none;">';
	result = result + '<label class="col-md-4">Primary Skill</label>';
	result = result + '<div class="col-md-8">';
	result = result + '<input type="text" class="form-control" id="primarySkill" name="primarySkill"  field-name="Primary Skill">';
	result = result + '</div>';
	result = result + '</div>';
	result = result + '</div>'; 
	
	result = result + '<div class="row">';
    result = result + '	<div class="col-md-6 upresume mb" id="employmentTypeDiv" style="display: none;">';
	result = result + '<label class="col-md-4 paddingRight">Employment Type<span class="paddingLeft">*</span></label>';
	result = result + '<div class="col-md-8">';
	result = result + '<select class="form-control width100percnt" id="designationType" name="designationType" onchange="getEmployementType();" field-name="Employment Type">';
	result = result + '<option value="">--Select Employment Type--</option>';
	result = result + '</select>';
	result = result + '</div>';
	result = result + '</div>';
	result = result + '</div>'; 
	
	
    result = result + '<div class="row">';
    result = result + '	<div class="col-md-6 upresume mb" id="employmentOtherTypeDiv" style="display: none;">';
    result = result + '<label class="col-md-4"></label>';
    result = result + '<div class="col-md-8">';
	result = result + '<input type="text" class="form-control" placeholder="Employment Other Type" id="designationOtherType" name="designationOtherType" field-name="Employment Other Type" >';
	result = result + '</div>';
	result = result + '</div>';
	result = result + '</div>'; 
	
	
    result = result + '<div class="row">';
    result = result + '	<div class="col-md-6 upresume mb" id="vendorsDiv" style="display: none;">';
	result = result + '<label class="col-md-4">Vendors</label>';
	result = result + '<div class="col-md-8">';
	result = result + '<select class="chosen-select form-control" id="vendors" name="vendors" data-validation-optional="true"  onchange="getAddApplicantVendorContactList();" field-name="Vendors">';
	result = result + '</select>';
	result = result + '</div>';
	result = result + '</div>';
	result = result + '</div>';
	
	
	result = result + '<div class="row">';
    result = result + '	<div class="col-md-6 upresume mb" id="vendorContactsDiv" style="display: none;">';
	result = result + '<label class="col-md-4">Vendor Contacts</label>';
	result = result + '<div class="col-md-8">';
	result = result + '<select class="chosen-select form-control" id="vendorContact" data-validation-optional="true"  name="vendorContact"  field-name="vendor Contacts">';
	result = result + '<option value="">--Select Vendor Contacts--</option>';
	result = result + '</select>';
	result = result + '</div>';
	result = result + '</div>';
	result = result + '</div>'; 
	
	
	result = result + '<div class="row">';
	result = result + '	<div class="col-md-6 upresume mb" id="newResumeDiv" style="display: none;">';
	result = result + '<label class="col-md-4">Add Resume</label>';
	result = result + '		<div class="col-md-8">';
	result = result + '			    <div class="input-group col-md-12 attachfile-error" id="uploadFileGroup1" style="margin:0 auto">';
	result = result + '			        <label class="input-group-btn" onchange="uploadAddApplicantCandidateDoc();"> <span class="btn btn-danger"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File';
	result = result + '                   <input type="file" style="display: none;" name="newUploadBtn" id="newUploadBtn"  class="upload">';
	result = result + '			          </span> </label>';
	result = result + '			          <input type="text" class="form-control" readonly id="newUploadFile" placeholder="Upload Resume ..." field-name="Upload Resume" data-validation-optional="true" data-validation="validatedoc "  >';
	result = result + '			    </div>';
	result = result + '		 </div>';
	result = result + '	 </div>';
	
	
	result = result + '	<div class="col-md-6 upresume mb">';
	result = result + '<label class="col-md-4">&nbsp;</label>';
	result = result + '<input type="checkbox" name="needApplicantPermit" id="needApplicantPermit" onClick="ApplicantPermit();" >&nbsp;&nbsp;<strong>Need applicant permission</strong><br> ';
	result = result + '<input type="checkbox" name="notifyToApplicant" value="Y" id="notifyToApplicantId" onClick="NotifyToApplicant();" >&nbsp;&nbsp;<span><strong>Notify to applicant</strong></span> ';
	result = result + '</div>';
	result = result + '</div>';
	
	result = result + '<div class="form-footer clearfix mt">';
	result = result + '<div class="pull-right mr">';
	result = result + '<button type="button" onclick="addApplicantForm();" id="save_button"  class="btn btn-sm btn-primary " >SUBMIT</button>';
	result = result + '</div>';
	result = result + '</div>';
	result = result + '</div>';  
	result = result + '</form>';
	
	
	 
	getCandidatesList(requirementId,'N');
	
	
	$("#addApplicantDiv").empty();
	$("#addApplicantDiv").append(result);
	
	   var config = {
				  '.chosen-select'           : {},
				  '.chosen-select-deselect'  : {allow_single_deselect:true},
				  '.chosen-select-no-single' : {disable_search_threshold:10},
				  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
				  '.chosen-select-width'     : {width:"95%"}
				}
	   
	 //Adding Customized Skills to the list
       var select, chosen;
	    select = $("#candidateSkills");
	    select.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add New Skill:' });
	    chosen = select.data('chosen');
	    
	    $("#candidateSkills").chosen().change(function(event) {
	    	var allSkills = $(event.target).val();
	    	$.ajax({
		    	type: "POST",
		        url: atsUrl+"/profileBySkillSet?"+tokenParamName+"="+tokenValue+"&allSkills="+allSkills, 
		        success: function(response) {
		        	if (response.response === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (response.response === "SUCCESS") {
		        		var primarySkill = response.object;
			        	$("#primarySkill").val(primarySkill);
		        	} else if (response.indexOf('Sign In') !== -1) {
		        		location.replace(sourceleadUrl);
		        	} else {
		        	    showError("Operation failed, please try again");
		        	}
		        },
		    });
	    });
	    chosen.search_field.on('keydown keyup', function(e)
			    {
			    	   var skillsArray = this.value.split(",");
				    	 if(skillsArray.length > 1){
			        		 for (var i=0; i < skillsArray.length; i++) {
			        			 var option = $("<option>").val(skillsArray[i].replace(",","")).text(skillsArray[i].replace(",",""));
			        			 select.append(option);
			 		             select.find(option).prop('selected', true);
			 		             select.find(option).siblings("[value='"+ skillsArray[i].replace(",","")+"']").remove();
			 	        		 $("#candidateSkills").trigger("chosen:updated");
			                 }
				    	 }else{
				    		var focussedSkills = $("ul.chosen-results").find("li.active-result.highlighted");
			    			var newSkill = this.value.replace(","," ");
			    			 if (tagItBykeyForSkills(e) && (newSkill != null  && newSkill != '' && newSkill != 'undefined'))
			                 {
			    				 preventTabkey(e);
			                   // below code manually enter skills are taken
			    				  
			                   var isNewSkill = true;
								 $("#candidateSkills option:selected").each(function(){
									 if(this.value.toUpperCase() === newSkill.toUpperCase() && this.value.toLowerCase() === newSkill.toLowerCase()){
										 isNewSkill = false;
										 return false;
									 }
			                    
			                   });
								
									if(focussedSkills.length > 0){
										var skillOption  = $("<option>").val(focussedSkills.first().text()).text(focussedSkills.first().text());
										select.append(skillOption);
										select.find(skillOption).prop('selected',true);
										select.find(skillOption).siblings("[value='"+ focussedSkills.first().text()+"']").remove();
										$("#candidateSkills").trigger("chosen:updated");
								   }
								   else{
									   if(isNewSkill){
										var option = $("<option>").val(newSkill).text(newSkill);
										select.append(option);
										select.find(option).prop('selected', true);
									   }
								   }
								
				               $("#candidateSkills").trigger("chosen:updated"); 
			                   
			                }
					       
		    	         }
	    	});
	    //Adding Customized Skills to the list
			
	$("#candidateSkills").val('').trigger("chosen:updated");
	    
		for (var selector in config) {
		  $(selector).chosen(config[selector]);
		}
		$('.chosen-select').each(function() {
			var $this = $(this);
			$this.next().css({'width': '100%'});
			})
	
	
	$(".new_side_wrap").addClass("new_side_wrap_active");
	$(".side_mask").fadeIn(200);
	$("body").css('overflow-y', 'hidden');
	
	var selectedFile = document.getElementById("newUploadBtn");
    selectedFile.addEventListener("change", function() {
    	 $("#newUploadFile").focus();
    	 if(validate_fileupload(this.value)){
    		 var docSize = this.files[0].size;
   	      $("#uploadFileGroup1").find('p.jquery_form_error_message').remove();
   	      if(docSize > 2097152){
   	    	  document.getElementById("newUploadFile").value = '';
   	    	  $('#uploadFileGroup1').find('p.jquery_form_error_message').remove();
   			  $("input#newUploadFile").after("<p class='jquery_form_error_message'>Limit is exceeded than 2MB </span>");
   			  document.getElementById("newUploadFile").setAttribute('record-exist','yes');
   			  document.getElementById("newUploadFile").setAttribute('record-exist-errorMsg','Limit is exceeded than 2MB'); 
   	      }else{
   	    	 $('input#newUploadFile').removeAttr( "record-exist record-exist-errormsg");
      		 $('#uploadFileGroup1').find('p.jquery_form_error_message').remove(); 
    	    }
    	 }
    });
	
	document.getElementById("newUploadBtn").onchange = function () {
		$('#newUploadFile').val($(this).val().replace(/C:\\fakepath\\/i, ''));
	};
	
}
</script>

<script>
    $(".addApplicant_close, .side_mask").click(function() {
    	$(".new_side_wrap").removeClass("new_side_wrap_active");
		$(".side_mask").fadeOut(200);
		$("body").css('overflow-y', 'auto');
	});	
</script>


<script type="text/javascript">
function ApplicantPermit(){
	 if($("#needApplicantPermit").is(':checked')){
		  $("#needApplicantPermit").val("Y");
	 }else{
		 $("#needApplicantPermit").val("N");
	 }
}
</script>

<script type="text/javascript">
function NotifyToApplicant(){
	 if($("#notifyToApplicantId").is(':checked')){
		  $("#notifyToApplicantId").val("Y");
	 }else{
		 $("#notifyToApplicantId").val("N");
	 }
}
</script>

<div id="popUp"></div>

<script>
function isCoolingOffAlert(){
	var reqCoolingOffPeriod = document.getElementById("reqCoolingOffPeriod").value;
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Applicant Cooling-Off period "+reqCoolingOffPeriod+" not completed</p>";
	result = result + ' <div id="reasonForSpocDiv"/>';
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Proceed' onClick='proceedApplicant();'><input class='u_can_color' type='button' value='Discard' onClick='discordApplicant();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
</script>

<script>
function proceedApplicant(){
	$("#popUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>

<script>
function discordApplicant(){
	$("#save_button").attr("disabled", "disabled");
	$("#popUp").empty();
	$(".project_inn_mask").fadeOut(200);
	
	$(".new_side_wrap").removeClass("new_side_wrap_active");
	$(".side_mask").fadeOut(200);
	$("body").css('overflow-y', 'auto');
}
</script>

<script type="text/javascript">
 function isCoolingOff(requirementId){

	 var candidateEmail =   $("input[name=EmailId]").val();
	var reqCoolingOffPeriod ='${requirement.reqCoolingOffPeriod}';
	alert(""+email);
	var orgLocationId ='${SourceLeadWeb_orgLocationId}';
	$.ajax({
		type: "POST",
	    url: atsUrl+"/verifyCoolingOffPeriod?"+tokenParamName+"="+tokenValue+"&requirementId="+requirementId+"&email="+email+"&reqCoolingOffPeriod="+reqCoolingOffPeriod+"&orgLocationId="+orgLocationId+"&mobilePhone="+mobilePhone+"&ssnNumber="+ssnNumber+"&panNumber="+panNumber+"&aadharNumber="+aadharNumber, 
	    success: function(response) {
	    	if (response.response === "AWKWARD") {
	    	    location.href = "/errorPage";
	    	} else if (response.response === "SUCCESS") {
	    		if(response.object === true) {
		    		isCoolingOffAlert();
		    		return true;
		    	} else {
		    		return false;
		    	}
	    	} else if (response.indexOf('Sign In') !== -1) {
	    		location.replace(sourceleadUrl);
	    	} else {
	    	    showError("Operation failed, please try again");
	    	}
	    },
	});
}
</script>
<script type="text/javascript">
 function getCandidatesList(requirementId,candidateType) {
	 $('#sjfb-fields').find('p.jquery_form_error_message').remove();
	 $('#uploadFileGroup').find('p.jquery_form_error_message').remove();
	 $('#uploadFileGroup1').find('p.jquery_form_error_message').remove();
	 
	 $("input[name=AlternateNumber]").val(''); 
	 $("input[name=DOB]").val('');
	 $("input[name='Current/LastCompany']").val('');
	 $("select[name=WorkExperience]").val(0);
	 $("select[name=monthExp]").val(0);
	 if($("#needApplicantPermit").is(':checked')){
		 document.getElementById("needApplicantPermit").checked = false;
		 $("#needApplicantPermit").val('N');
	 }
	 
	 $('#candidateId').removeAttr('data-validation-optional');
	 $("#candidateId").attr("data-validation", "required");
	 
	 if(candidateType != null && candidateType != "" && candidateType != 'undefined' && candidateType != 'New' && candidateType != 'parseCandidate'){
		 $("#selectCandidateDiv").css("display","block");
		
		 var skillsDiv = document.getElementById('skillsDiv'); 
		 if(skillsDiv != null){
			 document.getElementById("skillsDiv").style.display="none";
			 $("#candidateSkills").val('').trigger("chosen:updated");
			 document.getElementById("primarySkillDiv").style.display="none";
			 $("#primarySkill").val('');
		 }
		 
		 var employmentTypeDiv = document.getElementById('employmentTypeDiv');
		 if(employmentTypeDiv != null){
			 document.getElementById("employmentTypeDiv").style.display="none";
			 $("#designationType").removeAttr("data-validation");
		     $('#designationType').attr("data-validation-optional","true");
		     $("#designationType").empty();
		 } 
		 
		 
		 if(requirementId != null && requirementId != "" && requirementId != 'undefined'){
	
			 $.ajax({
			    	type: "POST",
			        url: atsUrl+"/getCandidatesList?"+tokenParamName+"="+tokenValue+"&requirementId="+requirementId, 
			        success: function(response) {
			        	if (response.response === "AWKWARD") {
			        	    location.href = "/errorPage";
			        	} else if (response.response === "SUCCESS") {
			        		var json = response.data;
				        	var output = "";
				        	output = output + "<option value=''>-- Select Candidate --</option>"; 
				        	for (var i=0; i<json.length; i++) {
					        		var opt = json[i];
					        		var candidateType = "";
					        		if(opt.isAssociate == 'Y'){
					        			candidateType = 'Associate';
					        		}else{
					        			candidateType = 'Candidate';
					        		}
					        		output = output + "<option value='" +opt.candId+ "'>"+opt.candidateName+" ("+candidateType+")</option>";
							}
				        	$("#candidateId").empty();
					        $("#candidateId").append(output);
					        $('#candidateId').trigger("chosen:updated");
					        getCandidateResumes();
			        	} else if (response.indexOf('Sign In') !== -1) {
			        		location.replace(sourceleadUrl);
			        	} else {
			        	    showError("Operation failed, please try again");
			        	}
			        },
			    }); 
		 }else{
			 var output = "";
	     	 output = output + "<option value=''>-- Select Candidate --</option>"; 
	     	 $("#candidateId").empty();
		     $("#candidateId").append(output);
		     $('#candidateId').trigger("chosen:updated");
		 }
	 }else{
		 var output = "";
     	 $("#candidateId").empty();
	     $("#candidateId").append(output);
	     $('#candidateId').trigger("chosen:updated");
	     $("#selectCandidateDiv").css("display","none");
	     
	     $("#candidateId").removeAttr("data-validation");
	     $('#candidateId').attr("data-validation-optional","true"); 
	     
	     
	     if(candidateType != null && candidateType != "" && candidateType != 'undefined' && candidateType == 'parseCandidate'){
	    	 document.getElementById("browseDiv").style.display="";
	    	 $('#uploadFile').removeAttr('data-validation-optional');
			 $('#uploadFile').attr("data-validation","required validatedoc "); 
			 
			 document.getElementById("newResumeDiv").style.display="none";
			 document.getElementById("skillsDiv").style.display="";
			 document.getElementById("primarySkillDiv").style.display="";
			 
			 document.getElementById("employmentTypeDiv").style.display="";
			 $("#designationType").removeAttr("data-validation-optional");
		     $('#designationType').attr("data-validation","required"); 
			
		     $("#candidateSkills").val('').trigger("chosen:updated");
			 $("#primarySkill").val('');
	     }else if(candidateType != null && candidateType != "" && candidateType != 'undefined' && candidateType == 'New'){
	    	 document.getElementById("browseDiv").style.display="none";
	    	 $('#uploadFile').removeAttr('data-validation');
			 $('#uploadFile').attr("data-validation-optional","true"); 
	    	 
	    	 document.getElementById("newResumeDiv").style.display="";
	    	 document.getElementById("skillsDiv").style.display="";
	    	 document.getElementById("primarySkillDiv").style.display="";
	    	 
	     	 document.getElementById("employmentTypeDiv").style.display="";
	    	 $("#designationType").removeAttr("data-validation-optional");
		     $('#designationType').attr("data-validation","required"); 
	    	
		     $("#candidateSkills").val('').trigger("chosen:updated");
			 $("#primarySkill").val('');
			 
			
	     }
	     document.getElementById("resumesDiv").style.display="none";
		 document.getElementById("orConditionDiv").style.display="none";
		
	 }
	 
	 $("input[name=FirstName]").val('');
	 $("input[name=LastName]").val('');
	 $("input[name=EmailId]").val('');
	 $("input[name=EmailId]").removeAttr("readonly");
	 
	 
	 $("input[name=PhoneNumber]").val('');
	 $("input[name=Designation]").val('');
	 $("select[name=NoticePeriod]").val('');
	 $("select[name=WorkExperience]").val(0);
	 $("input[name=ExpectedCTC]").val('');
	 $("input[name=CurrentCTC]").val('');
	 $("input[name=Education]").val('');
	 
	 $("#newUploadFile").val('');
	 $("#uploadFile").val('');
	 
	 $("#expectedCTCRatePer").val('');
	 $("#currentCTCRatePer").val('');

	 $("#expectedCTCRatePer").removeAttr("data-validation");
	 $("#expectedCTCRatePer").attr("data-validation-optional", "true");
	 
	 $("#currentCTCRatePer").removeAttr("data-validation");
	 $("#currentCTCRatePer").attr("data-validation-optional", "true");
	 
	 $("input[name=ExpectedCTC]").removeAttr("data-validation");
	 $("input[name=ExpectedCTC]").attr("data-validation-optional", "true");
	 
	 $("input[name=CurrentCTC]").removeAttr("data-validation");
	 $("input[name=CurrentCTC]").attr("data-validation-optional", "true");
	 
	 getCandidateStates();
	 getSubmissionCandidateCities();
	
 }
</script>

<!-- For add New Applicant -->
<script>
    $(".addApplicant_close, .side_mask").click(function() {
    	$(".new_side_wrap").removeClass("new_side_wrap_active");
		$(".side_mask").fadeOut(200);
		$("body").css('overflow-y', 'auto');
	});	
</script>


<!-- For add New Applicant -->
<script type="text/javascript">
function addApplicantForm(){
	  var requirementId = $("#requirementId").val();
	  if ($('#addApplicant_form').validate(false, validationSettings)){
      //e.preventDefault();
      var frm = $('#addApplicant_form').serializeFormJSON();
      var frm = $('#addApplicant_form').serializeFormJSON();
      var con1 = JSON.stringify(frm);
      con1= con1.replace(/[{}]/g, "");
      var jsonData = encodeURIComponent(con1);
      var form = $('#addApplicant_form')[0];
      var formData = new FormData(form); 
      //Loop through fields and save field data to array
      var fields = [];
      $('.sjfb-field').each(function() { 
      	  var $this = $(this); 
      	  var divId = $(this).attr('id');  
      	  var fieldLabel = $this.find('label:first').text().replace("*","");
      	  var inputValue = $this.find('input[type="radio"]:checked').val();
      	  
    	 var inputtype = $(this).find('input').attr('type');
	   	  if(inputtype == null || inputtype == '' || inputtype == 'undefined'){
	   		  if($(this).find('select').val() != null){
	   			  inputtype = 'select';
	   		  }else{
	   			  inputtype = 'textarea';
	   		  }
	   	  }
      	  
      	 if(inputValue == null || inputValue == '' || inputValue == 'undefined'){
    		   inputValue = $this.find('input[type="checkbox"]:checked').val();
    		   if(inputValue != null){
    			   var selected = [];
               	$this.find('input[type="checkbox"]:checked').each(function() {
      		        selected.push($(this).val()); 
      		       });
               	inputValue = selected.toString()
    		   }
    		   
      	 }
      	 
      	
      	  if(inputValue == null || inputValue == '' || inputValue == 'undefined'){
      		  inputValue = $this.find('input').val();
      	  }
      	  
      	   
    	  
     	      if(inputValue == null || inputValue == '' || inputValue == 'undefined'){
     	    	 inputValue = $this.find('select').val();
     	      }
     	      
     	     if(inputValue == null || inputValue == '' || inputValue == 'undefined'){
     	    	 inputValue = $this.find('textarea').val();
     	      }
     	      
     	    
     	     
     	      if(inputValue == null || inputValue == '' || inputValue == 'undefined'){
     	    	inputValue = '';
     	      }
     	      
     	      
     	      
	       	  var name = $this.find('input[type="radio"]:checked').attr('name');
	       	  var id = $this.find('input[type="radio"]:checked').attr('id');
	     	  if(name == null || name == '' || name == 'undefined'){
	     		  name = $this.find('input').attr('name');
	     		  id = $this.find('input').attr('id');
	     	  }
	     	  if(name == null || name == '' || name == 'undefined'){
	     		 name = $this.find('select').attr('name');
	     		 id = $this.find('select').attr('id');
     	      }
	     	  
	     	  if(name == null || name == '' || name == 'undefined'){
	     		 name = $this.find('textarea').attr('name');
     	      }
	     	  
	     	  if(name == "WorkExperience"){
	     		 var monthExp = document.getElementById("monthExp").value; 
	     		 if(inputValue != null && inputValue != 'undefined' && inputValue != ''){
	     			  if(inputValue == "50"){
	     				 inputValue = inputValue+" Years";
	     			  }else{
	     				 inputValue = inputValue+" Years"+" "+monthExp+" Months" ;
	     			  }
	     			 
	     		  }
	     		  
	     		  if(null == inputValue && monthExp != '0'){
	     			 inputValue = monthExp+" Months" ;
	     		  }
	     	  }
	     	  
	     	  
	     	 if(name == "CurrentCTC"){
	     		 inputValue = $this.find('input').val();
	     		 if(inputValue){
	     			 var currentCTCRatePer = document.getElementById("currentCTCRatePer").value; 
	     			 var currentCTCRateType = document.getElementById("currentCTCRateType").value; 
	     			 inputValue = currentCTCRateType+" "+inputValue+" /"+" "+currentCTCRatePer ;
	     		  }
	     	  }
	     	 
	     	if(name == "ExpectedCTC"){
	     		 inputValue = $this.find('input').val();
	     		  if(inputValue){
	     			 var expectedCTCRatePer = document.getElementById("expectedCTCRatePer").value; 
	     			 var expectedCTCRateType = document.getElementById("expectedCTCRateType").value; 
	     			 inputValue = expectedCTCRateType+" "+inputValue+" /"+" "+expectedCTCRatePer ;
	     		  }
	     	  }
	     	  
	     	  
      	  fields.push({
                label: fieldLabel,
                type : inputtype,
                id :id,
                name :name,
                value: inputValue,
            });
       });
      var con = JSON.stringify(fields);
      
      con = encodeURIComponent(con);
     
      
		
       $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
      
  	     $.ajax({
	    	type: "POST",
	    	data: formData,
 	        contentType : 'application/json; charset=utf-8',
 	        enctype: 'multipart/form-data',
	        processData: false,
	        contentType:false,
 	        dataType : 'json',
 	    	url : atsUrl+"/saveAddApplicant?"+tokenParamName+"="+tokenValue+"&jsonData="+jsonData+"&con="+con,
	        success: function(result) {
	        	 $(".new_side_wrap").removeClass("new_side_wrap_active");
	        	 if(result.response === "SESSION_TIMEDOUT") {
						location.replace(sourceleadUrl);
					} else if (result.response === "AWKWARD") {
						location.href = "/errorPage";
					} else if (result.response === "SUCCESS") {
			    		$(".side_mask").fadeOut(200);
			    		$("body").css('overflow-y', 'auto');
			    		location.replace(atsUrl+"/jobsList");
					}
	        },
	    }); 
      return true; 
    }
    else{
    	return false;	
       }
  }    
   	$('body').on('blur', '#addApplicant_form', function() {
		$("#addApplicant_form").showHelpOnFocus();
		$("#addApplicant_form").validateOnBlur(false, validationSettings);
	});
  
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
function getCandidateSelectionType(){
	var output = "";
	output = output + "<option value=''>--Select Employment Type--</option>"; 
	output = output + "<option value='C2C'>C2C</option>"; 
	output = output + "<option value='C2C-1099'>C2C-1099</option>"; 
	output = output + "<option value='W-2'>W-2</option>"; 
	output = output + "<option value='C2H'>C2H</option>"; 
	output = output + "<option value='C2H-1099'>C2H-1099</option>"; 
	output = output + "<option value='Part Time'>Part Time</option>"; 
	output = output + "<option value='Full Time'>Full Time</option>"; 
	output = output + "<option value='Other'>Other</option>"; 
	$("#designationType").empty();
    $("#designationType").append(output);
}
</script>


<script type="text/javascript">
 function getCandidateResumes() {
	 $("#save_button").removeAttr("disabled");
	 $('.jquery_form_error_message').remove();
	 var candidateId = document.getElementById("candidateId").value
	 if(candidateId != null && candidateId != "" && candidateId != 'undefined'){
		
		 document.getElementById("resumesDiv").style.display="";
		 document.getElementById("browseDiv").style.display="none";
		 document.getElementById("orConditionDiv").style.display="none";
		 $("#uploadFile").removeAttr("data-validation");
		 $("#uploadFile").val('');
		 $('.jquery_form_error_message').remove();
		 $.ajax({
		    	type: "POST",
		        url: atsUrl+"/getCandidateResumes?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId, 
		        success: function(data) {
		        	var json = data.data;
		        	if (data.response === "FAILED") {
			    		showError(data.responseMessage);
			    	} else if (data.response === "AWKWARD") {
			    		location.href = "/errorPage";
			    	} else if (data.response === "SUCCESS") {
				        	if(json!=null){
				        	var output = "";
				        	if(json.length == 1){
				        		var opt = json[0];
				        		if(opt.title != null && opt.title != 'undefined' && opt.title != ""){
				        			output = output + "<option value='" +opt.id+ "'>"+opt.title+"</option>";
				            	}else{
				            		output = output + "<option value='" +opt.id+ "'>"+opt.fileName+"</option>";
				            	}
				        	}else{
				        		output = output + "<option value=''>-- Select Resume --</option>"; 
					        	for (var i=0; i<json.length; i++)
								{
						        		var opt = json[i];
						        		if(opt.title != null && opt.title != 'undefined' && opt.title != ""){
						        			output = output + "<option value='" +opt.id+ "'>"+opt.title+"</option>";
						            	}else{
						            		output = output + "<option value='" +opt.id+ "'>"+opt.fileName+"</option>";
						            	}
								}
				        	}
				        	
				        	
				        	$("#candidateResumesList").empty();
					        $("#candidateResumesList").append(output);
					        
					        document.getElementById("newResumeDiv").style.display="none";
					        document.getElementById("skillsDiv").style.display="none";
					        document.getElementById("primarySkillDiv").style.display="none";
					        
					       /*  document.getElementById("employmentTypeDiv").style.display="none";
						    $('#designationType').removeAttr("data-validation");
						    $("#designationType").attr("data-validation-optional","true");
						    $("#designationType").empty(); */
					        
			        		
			        	}else{
			        		
			        		
			        		 document.getElementById("resumesDiv").style.display="none";
			        		 document.getElementById("browseDiv").style.display="none";
			        		 document.getElementById("orConditionDiv").style.display="none";
			        		
			        		 document.getElementById("newResumeDiv").style.display="";
			        		 
			        		 
			        		 
			        		 $('#uploadFile').removeAttr('data-validation');
			        		 $('#uploadFile').attr("data-validation-optional","true"); 
			        	}
			    	}else if (data.indexOf('Sign In') !== -1) {
			    		location.replace(sourceleadUrl);
			    	} else {
			    		showError("Operation failed, please try again");
			    	}
		        },
		    });
	 }else{
		 var output = "";
     	 output = output + "<option value=''>-- Select Resume --</option>"; 
     	 $("#candidateResumesList").empty();
		 document.getElementById("resumesDiv").style.display="none";
		 document.getElementById("browseDiv").style.display="none";
		 document.getElementById("orConditionDiv").style.display="none";
		 $('#uploadFile').removeAttr('data-validation');
		 $('#uploadFile').attr("data-validation-optional","true"); 
	     $("#candidateResumesList").append(output);
	 }
	
 }
</script>






<script type="text/javascript">
 function getRecomendedCandidateResumes(candidateId) {
	 $("#save_button").removeAttr("disabled");
	 $('.jquery_form_error_message').remove();
	 if(candidateId != null && candidateId != "" && candidateId != 'undefined'){
		
		 $("#uploadFile").removeAttr("data-validation");
		 $("#uploadFile").val('');
		 $('.jquery_form_error_message').remove();
		 $.ajax({
		    	type: "POST",
		        url: atsUrl+"/getCandidateResumes?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId, 
		        success: function(data) {
		        	var json = data.data;
		        	if (data.response === "FAILED") {
			    		showError(data.responseMessage);
			    	} else if (data.response === "AWKWARD") {
			    		location.href = "/errorPage";
			    	} else if (data.response === "SUCCESS") {
			        	if(json!=null){
				        	var output = "";
				        	
				        	if(json.length == 1){
				        		var opt = json[0];
				        		output = output + "<option value=''>-- Select Resume --</option>"; 
				        		if(opt.title != null && opt.title != 'undefined' && opt.title != ""){
				        			output = output + "<option value='" +opt.id+ "'>"+opt.title+"</option>";
				            	}else{
				            		output = output + "<option value='" +opt.id+ "'>"+opt.fileName+"</option>";
				            	}
				        	}else{
				        		output = output + "<option value=''>-- Select Resume --</option>"; 
					        	for (var i=0; i<json.length; i++)
								{
						        		var opt = json[i];
						        		if(opt.title != null && opt.title != 'undefined' && opt.title != ""){
						        			output = output + "<option value='" +opt.id+ "'>"+opt.title+"</option>";
						            	}else{
						            		output = output + "<option value='" +opt.id+ "'>"+opt.fileName+"</option>";
						            	}
								}
				        	}
				        	
				        	
				        	$("#candidateResumesList").empty();
					        $("#candidateResumesList").append(output);
					        
					        document.getElementById("newResumeDiv").style.display="none";
					        
					      
			        		
			        	}else{
			        		
			        		
			        		 document.getElementById("resumesDiv").style.display="none";
			        		 document.getElementById("newResumeDiv").style.display="";
			        		
			        		 
			        		 
			        	}
			    	}else if (data.indexOf('Sign In') !== -1) {
			    		location.replace(sourceleadUrl);
			    	} else {
			    		showError("Operation failed, please try again");
			    	}
		        	
		        },
		    });
	 }else{
		 var output = "";
     	 output = output + "<option value=''>-- Select Resume --</option>"; 
     	 $("#candidateResumesList").empty();
		 document.getElementById("resumesDiv").style.display="none";
	     $("#candidateResumesList").append(output);
	 }
	
 }
</script>


<script type="text/javascript">
function getCandidateDetails(){
	var candidateId = document.getElementById("candidateId").value
	var requirementId = $("#requirementId").val();
	var orgLocationId ='${SourceLeadWeb_orgLocationId}';
	if(candidateId != null && candidateId != "" && candidateId != 'undefined'){
		 $.ajax({
		    	type: "POST",
		        url: atsUrl+"/getCandidateDetails?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId+"&requirementId="+requirementId+"&orgLocationId="+orgLocationId, 
		        success: function(data) {
		        	if (data.response === "FAILED") {
			    		showError(data.responseMessage);
			    	} else if (data.response === "AWKWARD") {
			    		location.href = "/errorPage";
			    	} else if (data.response === "SUCCESS") {
			    		var json = data.object;
		        		$("input[name=FirstName]").val(json['firstName']);
		        	    $("input[name=EmailId]").val(json['email']);
		        	    $("input[name=EmailId]").attr('readonly', 'readonly'); 
			        	 
			        	if(json['email'] != null && json['email'] != "" && json['email'] != 'email'){
			        		 var requirementId = $("#requirementId").val();
			        		 isCoolingOff(requirementId);
			        	 } 
			        	 
			       	     if(json['lastName'] != null && json['lastName'] != "" && json['lastName'] != 'undefined'){
			        		 $("input[name=LastName]").val(json['lastName']);
			        	 }else{
			        		 $("input[name=LastName]").val(''); 
			        	 }
			        	 
			        	  if(json['mobilePhone'] != null && json['mobilePhone'] != "" && json['mobilePhone'] != 'undefined'){
			        		 $("input[name=PhoneNumber]").val(json['mobilePhone']);
			        	 }else{
			        		 $("input[name=PhoneNumber]").val(''); 
			        	 }
			        	 
			        	 if(json['alternateMobilePhone'] != null && json['alternateMobilePhone'] != "" && json['alternateMobilePhone'] != 'undefined'){
			        		 $("input[name=AlternateNumber]").val(json['alternateMobilePhone']);
			        	 }else{
			        		 $("input[name=AlternateNumber]").val(''); 
			        	 }
			        	 
			        	   if(json['employer'] != null && json['employer'] != "" && json['employer'] != 'undefined'){
			        		 $("input[name='Current/LastCompany']").val(json['employer']);
			        	 }else{
			        		 $("input[name='Current/LastCompany']").val(''); 
			        	 }
			        	 
			        	 if(json['designation'] != null && json['designation'] != "" && json['designation'] != 'undefined'){
			        		 $("input[name=Designation]").val(json['designation']);
			        	 }else{
			        		 $("input[name=Designation]").val(''); 
			        	 }
			        	 
			        	 if(json['candidateEdu'] != null && json['candidateEdu'] != "" && json['candidateEdu'] != 'undefined'){
			        		 $("input[name=Education]").val(json['candidateEdu']);
			        	 }else{
			        		 $("input[name=Education]").val(''); 
			        	 }
			        	 
			        	 if(json['location'] != null && json['location'] != "" && json['location'] != 'undefined'){
			        		 $("#submissionLocation").val(json['location']);
			        	 }else{
			        		 $("#submissionLocation").val('');
			        	 }
	
			        	 if(json['country'] != null && json['country'] != "" && json['country'] != 'undefined'){
			        		 $("#submissionCountry").val(json['country']);
			        	 }else{
			        		 $("#submissionCountry").val('');
			        	 }
			        	 
			        	 if(json['state'] != null && json['state'] != "" && json['state'] != 'undefined'){
			        		 $("#submissionState").val(json['state']);
			        	 }else{
			        		 $("#submissionState").val('');
			        	 }
			        	 
			        	 
			        	 if(json['city'] != null && json['city'] != "" && json['city'] != 'undefined'){
			        		 $("#submissionCity").val(json['city']);
			        	 }else{
			        		 $("#submissionCity").val('');
			        	 }
			        	 
			        	 if(json['zipCode'] != null && json['zipCode'] != "" && json['zipCode'] != 'undefined'){
			        		 $("#submissionZipCode").val(json['zipCode']);
			        	 }else{
			        		 $("#submissionZipCode").val('');
			        	 }
			        	 
			        	 if(json['dateofBirthString'] != null && json['dateofBirthString'] != "" && json['dateofBirthString'] != 'undefined'){
			        		 $("#DOB").val(json['dateofBirthString']);
			        	 }else{
			        		 $("#DOB").val('');
			        	 }
			        
			        	 if(json['noticePeriod'] != null && json['noticePeriod'] != "" && json['noticePeriod'] != 'undefined'){
			        		 $("select[name=NoticePeriod]").val(json['noticePeriod']);
			        	 }else{
			        		 $("select[name=NoticePeriod]").val('');
			        	 }
			        	 
			        	 if(json['experienceYears'] != null && json['experienceYears'] != "" && json['experienceYears'] != 'undefined'){
			        		 $("select[name=WorkExperience]").val(json['experienceYears']);
			        		 if(json['experienceYears'] == "25+"){
			        			 $("#monthExp").attr("disabled", "disabled");
			        		 }else{
			        			 $("#monthExp").removeAttr("disabled");
			        		 }
			        	 }else{
			        		 $("select[name=WorkExperience]").val(0);
			        	 }
			        	 
			        	 if(json['experienceMonths'] != null && json['experienceMonths'] != "" && json['experienceMonths'] != 'undefined'){
			        		 $("select[name=monthExp]").val(json['experienceMonths']);
			        	 }else{
			        		 $("select[name=monthExp]").val(0);
			        	 }
			        	 
			        	 if(json['expectedRate'] != null && json['expectedRate'] != "" && json['expectedRate'] != 'undefined'){
			        		 $("input[name=ExpectedCTC]").val(json['expectedRate']);
			        	 }else{
			        		 $("input[name=ExpectedCTC]").val(''); 
			        	 }
			        	 
			        	 
			        	 if(json['rateType'] != null && json['rateType'] != "" && json['rateType'] != 'undefined'){
			        		 $("select[name=expectedCTCRatePer]").val(json['rateType']);
			        		 validateExpectedCTCRatePer();
			        	 }else{
			        		 $("select[name=expectedCTCRatePer]").val('');
			        		 validateExpectedCTCRatePer();
			        	 }
			        	 
			        	 if(json['expectedRateCurrencey'] != null && json['expectedRateCurrencey'] != "" && json['expectedRateCurrencey'] != 'undefined'){
			        		 $("select[name=expectedCTCRateType]").val(json['expectedRateCurrencey']);
			        	 }
			        	 
			        	 if(json['currentRate'] != null && json['currentRate'] != "" && json['currentRate'] != 'undefined'){
			        		 $("input[name=CurrentCTC]").val(json['currentRate']);
			        		 validateCurrentCTC();
			        	 }else{
			        		 $("input[name=CurrentCTC]").val(''); 
			        		 validateCurrentCTC();
			        	 }
			        	 
			        	 if(json['currentRateType'] != null && json['currentRateType'] != "" && json['currentRateType'] != 'undefined'){
			        		 $("select[name=currentCTCRatePer]").val(json['currentRateType']);
			        		 validateExpectedCTC();
			        		 validateCurrentCTCRatePer();
			        	 }else{
			        		 $("select[name=currentCTCRatePer]").val(''); 
			        		 validateExpectedCTC();
			        		 validateCurrentCTCRatePer();
			        	 }
			        	 
			        	 if(json['currentRateCurrency'] != null && json['currentRateCurrency'] != "" && json['currentRateCurrency'] != 'undefined'){
			        		 $("select[name=currentCTCRateType]").val(json['currentRateCurrency']);
			        	 } 
			    	}else if (data.indexOf('Sign In') !== -1) {
			    		location.replace(sourceleadUrl);
			    	} else {
			    		showError("Operation failed, please try again");
			    	}
		        },
		    });
	 }else{
		 $("input[name=FirstName]").val('');
    	 $("input[name=LastName]").val('');
    	 $("input[name=EmailId]").val('');
    	 $("input[name=EmailId]").removeAttr("readonly");
    	 
    	 
    	 $("input[name=PhoneNumber]").val('');
    	 $("input[name=Designation]").val('');
    	 $("select[name=country]").val('');
    	 $("select[name=state]").val('');
    	 $("select[name=city]").val('');
    	 $("select[name=NoticePeriod]").val('');
    	 $("select[name=WorkExperience]").val('');
    	 $("input[name=ExpectedCTC]").val('');
    	 $("input[name=CurrentCTC]").val('');
    	 $("input[name=Education]").val('');
	 }
}
</script>


<script>
function getExpYears(){
	var expYears = $("select[name=WorkExperience]").val();
	 if(expYears == "25+"){
		 $("#monthExp").attr("disabled", "disabled");
	 }else{
		 $("#monthExp").removeAttr("disabled");
	 }
}
</script>


<script type="text/javascript">
function getAllSubmissionCandidateCountries(){
	$.ajax({
    	type: "POST",
    	dataType: "json",
    	 url: '${SourceLeadWeb_atsUrl}'+"/countryAutoComplete?"+tokenParamName+"="+tokenValue+"",
        success: function(data) {
        	var json = JSON.parse(data);
        	$('#submissionCountry').empty();
        	$('#submissionCountry').append('<option value="">---Select Country---</option>');
            for(var i=0;i<json.length;i++){
            	if(json[i] == '${SourceLeadWeb_orgLocations.country}'){
            		$('#submissionCountry').append('<option value="'+json[i]+'" selected>'+json[i]+'</option>');
            	}
            	else{
            		$('#submissionCountry').append('<option value="'+json[i]+'">'+json[i]+'</option>');
            	}
            	
            }
            $('#submissionCountry').trigger("chosen:updated");
            getCandidateStates();
        },
        error: function(e){
        }
    }); 
} 
</script>

<script>
function getCandidateStates(state){
	
	var countryName = $("#submissionCountry").val();
	if(countryName != null && countryName != "" && countryName !="undefined"){
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: '${SourceLeadWeb_atsUrl}'+"/getStatesByCountry?"+tokenParamName+"="+tokenValue+"&countryName="+countryName, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#submissionState').empty();
	        	$('#submissionState').append('<option value="">---Select State---</option>');
	            for(var i=0;i<json.length;i++){
	            	if(state != null && state != 'undefined' && json[i] == state){
	            		$('#submissionState').append('<option value="'+state+'" selected>'+json[i]+'</option>');
	            	}
	            	else{
	            		$('#submissionState').append('<option value="'+json[i]+'">'+json[i]+'</option>');
	            	}
	            }
	            $('#submissionState').trigger("chosen:updated");
	            
	            $('#submissionCity').empty();
	            $('#submissionCity').trigger("chosen:updated");
	        },
	        error: function(e){
	        }
	    });
	}else{
		$('#submissionState').empty();
    	$('#submissionState').append('<option value="">---Select State---</option>');
    	$('#submissionState').trigger("chosen:updated");
    	$('#submissionCity').empty();
    	$('#submissionCity').append('<option value="">---Select City---</option>');
        $('#submissionCity').trigger("chosen:updated");
	}
}
</script> 


<script>
function getSubmissionCandidateCities(city){
	
	var countryName = $("#submissionCountry").val();
	var stateName = $("#submissionState").val();
	
	if(stateName == null || stateName == "" || stateName =="undefined"){
		stateName = document.getElementById("state");
	}
	
	if(countryName != null && countryName != "" && countryName !="undefined" && stateName != null && stateName != "" && stateName !="undefined" ){
		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: '${SourceLeadWeb_atsUrl}'+"/getCitiesByStateAndCountry?"+tokenParamName+"="+tokenValue+"&countryName="+countryName+"&stateName="+stateName, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#submissionCity').empty();
	        	$('#submissionCity').append('<option value="">---Select City---</option>');
	            for(var i=0;i<json.length;i++){
	            	var selected = '';
	            	if(city != null && city != "" && city !="undefined" && json[i] == city){
	            		selected = 'selected';
	            	}
	            	$('#submissionCity').append('<option value="'+json[i]+'" '+selected+'>'+json[i]+'</option>');
	            }
	            $('#submissionCity').trigger("chosen:updated");
	            $("#loading").removeAttr("style"); 
	        },
	        error: function(e){
	        }
	    });
	}else{
		$('#submissionCity').empty();
    	$('#submissionCity').append('<option value="">---Select City---</option>');
    	$('#submissionCity').trigger("chosen:updated");
	}
}
</script>
<script type="text/javascript">
function init(){
	getAllSubmissionCandidateCountries();
	initLocationAuto('submissionLocation','submissionCountry','submissionState','submissionCity','submissionZipCode','','','');
	var country = '${SourceLeadWeb_orgLocations.country}';
	$("#submissionCountry").val(country)
	      
/* 	 $("#submissionCountry").on('change', function (e) {
	    	   getCandidateStates();
	    	   getSubmissionCandidateCities();
	       });
	       $("#submissionState").on('change', function (e) {
	    	   getSubmissionCandidateCities();
	       }); */
	      
	       $('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});
		  })
		  
    setRateTypesByLocation();
}
</script>


<script type="text/javascript">
function setRateTypesByLocation(){
	var country = '${SourceLeadWeb_country}';
	if(country == 'India'){
		$('#currentCTCRateType').val('INR');
		$('#expectedCTCRateType').val('INR');
	}else if(country == 'Australia'){
		$('#currentCTCRateType').val('AUD');
		$('#expectedCTCRateType').val('AUD');
	}else if(country == 'United States'){
		$('#currentCTCRateType').val('USD');
		$('#expectedCTCRateType').val('USD');
	}else if(country == 'Singapore'){
		$('#currentCTCRateType').val('SGD');
		$('#expectedCTCRateType').val('SGD');
	}else if(country == 'United Kingdom'){
		$('#currentCTCRateType').val('GBP');
		$('#expectedCTCRateType').val('GBP');
	}else if(country == 'France'){
		$('#currentCTCRateType').val('EUR');
		$('#expectedCTCRateType').val('EUR');
	}
}
</script>

<script>
  function dobDatePicker() {
	  $('#DOB').datepicker({
	      dateFormat: 'yy-mm-dd',
	  });
	  
  }
</script>


 <script>
 $(document.body).on('change', '#uploadBtn' ,function(){
	 $('#uploadFile').val(this.value.split( '\\' ).pop());
	 $("#uploadFile").focus();
 	 if(validate_fileupload(this.value)){
 		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		var form = $('#addApplicant_form')[0];
  		var formData = new FormData(form); 
		   $.ajax({
				    type: "POST",
			    	data: formData,
		   	        contentType : 'application/json; charset=utf-8',
		   	        enctype: 'multipart/form-data',
			        processData: false,
			        contentType:false,
		   	        dataType : 'json',
					url : atsUrl+"/parseResume?"+tokenParamName+"="+tokenValue+"",
					success : function(response) {
						if (response.response === "AWKWARD") {
						    location.href = "/errorPage";
						} else if (response.response === "SUCCESS") {
							if(response.object !== null) {
								var json = response.object;
								var name = json.name;
								var firstName = '';
								var lastName = '';
								if(name != null && name != 'undefined' && name != ''){
									 firstName = name.split(".")[0]; 
									 lastName = name.split(".")[1]; 
									 if(firstName == null || firstName ==  'undefined' || firstName == '' ||
											 lastName ==  null || lastName ==  'undefined'|| lastName ==  ''){
										 firstName = name;
									 }
								}
								
								
								 $("input[name=FirstName]").val(firstName);
					        	 $("input[name=LastName]").val(lastName);
					        	 $("input[name=EmailId]").val(json.email);
					        	 $("input[name=PhoneNumber]").val(json.phoneNumbers);
					        	 
					        	 $("#candidateSkills").empty();
					        	 var internalOutput = "";
		 						 <c:forEach var="skill" items="${skillList}">
		 						    internalOutput = internalOutput+"<option value='${skill.name}'>${skill.name}</option>";
		 	                     </c:forEach>
		 						var skills = json.skills;
		 						if(skills != null && skills != 'undefined' && skills != ''){
		 							var str_array = skills.split(',');
		 							for(var i = 0; i < str_array.length; i++) {
		 							   str_array[i] = str_array[i].replace(/^\s*/, "").replace(/\s*$/, "");
		 							   internalOutput = internalOutput+"<option value='" +str_array[i]+ "' selected>"+str_array[i]+"</option>";
		 							}
		 						}
		 						$("#candidateSkills").append(internalOutput);
		 		        		$("#candidateSkills").trigger("chosen:updated");
		  						
					        	 
								 validateSubmissionCandidateEmail();
								 getUserByUserEmail();
							} else {
								showError("Resume Parsing Failed");
							}
							 $("#loading").removeAttr("style");
						} else if (response.indexOf('Sign In') !== -1) {
							location.replace(sourceleadUrl);
						} else {
						    showError("Operation failed, please try again");
						}
					},
				});
 	 }
 });

</script> 


<!-- Country,State,City Script Starts Here -->
<script type="text/javascript">
function validate_fileupload(fileName)
{
    var allowed_extensions = new Array("docx","doc","pdf","rtf");
    var file_extension = fileName.split('.').pop(); // split function will split the filename by dot(.), and pop function will pop the last element from the array which will give you the extension as well. If there will be no extension then it will return the filename.

    for(var i = 0; i <= allowed_extensions.length; i++)
    {
        if(allowed_extensions[i]==file_extension)
        {
            return true; // valid file extension
        }
    }

    return false;
}
</script>


<script type="text/javascript">
 function validateSubmissionCandidateEmail() {
	 var email =   $("input[name=EmailId]").val();
	$('#emailDiv').find('p.jquery_form_error_message').remove();
	 var requirementId = $("#requirementId").val();
	 if(email != null && email != ""){
		 $("#save_button").attr("disabled", "disabled");
		 $.ajax({
		    	type: "POST",
		        url: atsUrl+"/validateRequirementCandidateEmail?"+tokenParamName+"="+tokenValue+"&email="+email+"&requirementId="+requirementId, 
		        success: function(data) {
		         	if(data == 'true'){
			        	  $("#save_button" ).addClass('btn btn-sm btn-primary').val('SUBMIT');
			        	  $('.jquery_form_error_message').remove();
		        		  $("input[name=EmailId]").after("<p class='jquery_form_error_message'>"+email+" is Already Existed</span>");
		        		  $("input[name=EmailId]").setAttribute('record-exist','yes');
		        		  $("input[name=EmailId]").setAttribute('record-exist-errorMsg',email+' is Already Existed'); 
		        	}
		        	else{
		        		$("#save_button").removeAttr("disabled");
		        		$("input[name=EmailId]").removeAttr( "record-exist record-exist-errormsg");
		        		$('.jquery_form_error_message').remove();
		        		isCoolingOff(requirementId);
		        	} 
		        },
		    });
	 }
	 
 }
 </script>   
 
 
 <script type="text/javascript">
 function getUserByUserEmail() {
	 var emailId =   $("input[name=EmailId]").val();
	 if(emailId != null && emailId != ""){
		 $.ajax({
		    	type: "POST",
		        url: atsUrl+"/getUserByUserEmail?"+tokenParamName+"="+tokenValue+"&EmailId="+emailId, 
		        success: function(json) {
		        		
		        		if(json.firstName != null && json.firstName != ''){
		        			$("input[name=FirstName]").val(json.firstName);
		        			$("input[name=FirstName]").attr("readonly", "readonly");
		        			$("input[name=FirstName]").find('p.jquery_form_error_message').remove();
		        		}else{
		        			$("input[name=FirstName]").removeAttr("readonly", "readonly");
		        			var firstName = $("input[name=FirstName]");
		        			if(firstName == ''){
		        				$("input[name=FirstName]").val('');
		        			}
		        			
		        		}
		        		
		        		if(json.lastName != null && json.lastName != ''){
		        			$("input[name=LastName]").attr("readonly", "readonly");
		        			$("input[name=LastName]").val(json.lastName);
		        		}else{
		        			$("input[name=LastName]").removeAttr("readonly", "readonly");
		        			
		        			var lastName = $("input[name=LastName]");
		        			if(lastName == ''){
		        				$("input[name=LastName]").val('');
		        			}
		        			
		        			
		        		}
		        		
		        		if(json.mobilePhone != null && json.mobilePhone != ''){
		        			$("input[name=PhoneNumber]").val(json.mobilePhone);
		        			$("input[name=PhoneNumber]").attr("readonly", "readonly");
		        		}else{
		        			$("input[name=PhoneNumber]").removeAttr("readonly", "readonly");
		        			
		        			var phno = $("input[name=PhoneNumber]");
		        			if(phno == ''){
		        				$("input[name=PhoneNumber]").val('');
		        			}
		        			
		        			
		        		}
		        		
		        },
		    });
	 }else{
		 $("input[name=FirstName]").attr("disabled", false);
		 $("input[name=FirstName]").val('');
		 
		 $("input[name=LastName]").attr("disabled", false);
		 $("input[name=LastName]").val('');
		 
		 $("input[name=PhoneNumber]").attr("disabled", false);
		 $("input[name=PhoneNumber]").val('');
		 
	 }
 }
 </script> 
 
 
 <script>
 function validateCurrentCTC(){
	 var currentCTC = $("input[name=CurrentCTC]").val();
	 if(currentCTC){
		 $("#currentCTCRatePer").removeAttr("data-validation-optional");
		 $("#currentCTCRatePer").attr("data-validation", "required");
	 }else{
		 $("#currentCTCRatePer").removeAttr("data-validation");
		 $("#currentCTCRatePer").attr("data-validation-optional", "true");
	 }
 }
 </script>
 
  <script>
 function validateExpectedCTC(){
	 var expectedCTC = $("input[name=ExpectedCTC]").val();
	 if(expectedCTC){
		 $("#expectedCTCRatePer").removeAttr("data-validation-optional");
		 $("#expectedCTCRatePer").attr("data-validation", "required");
	 }else{
		 $("#expectedCTCRatePer").removeAttr("data-validation");
		 $("#expectedCTCRatePer").attr("data-validation-optional", "true");
	 }
 }
 </script>
 
 <script>
 function validateExpectedCTCRatePer(){
	 var expectedCTCRatePer = document.getElementById("expectedCTCRatePer").value;
	 if(expectedCTCRatePer){
		 $("input[name=ExpectedCTC]").removeAttr("data-validation-optional");
		 $("input[name=ExpectedCTC]").attr("data-validation", "required validate_Space validate_float validate_length length1-8");
	 }else{
		 $("input[name=ExpectedCTC]").removeAttr("data-validation");
		 $("input[name=ExpectedCTC]").attr("data-validation-optional", "true");
		 $("input[name=ExpectedCTC]").attr("data-validation", "validate_Space validate_float validate_length length1-8");
	 }
 }
 </script>
 
 <script>
 function validateCurrentCTCRatePer(){
	 var currentCTCRatePer = document.getElementById("currentCTCRatePer").value;
	 if(currentCTCRatePer){
		 $("input[name=CurrentCTC]").removeAttr("data-validation-optional");
		 $("input[name=CurrentCTC]").attr("data-validation", "required validate_Space validate_float validate_length length1-8");
	 }else{
		 $("input[name=CurrentCTC]").removeAttr("data-validation");
		 $("input[name=CurrentCTC]").attr("data-validation-optional", "true");
		 $("input[name=CurrentCTC]").attr("data-validation", "validate_Space validate_float validate_length length1-8");
	 }
 }
 </script>
 
 
 
 <script>
 function changeRateLabels(){
	 
	 var currentRate = document.getElementById("currentRate").textContent;
	 var currentstringLength = currentRate.length; 
	 var currentRatelastChar = currentRate.charAt(currentstringLength - 1);
	 
	 if(currentRatelastChar == "*"){
		 $("#currentRate").html("Current Rate *"); 
	 }else{
		 $("#currentRate").html("Current Rate");
	 }
	 
	 var expectedRate = document.getElementById("expectedRate").textContent;
	 var expectedstringLength = expectedRate.length; 
	 var expectedRatelastChar = expectedRate.charAt(expectedstringLength - 1);
	 
	 if(expectedRatelastChar == "*"){
		 $("#expectedRate").html("Expected Rate *"); 
	 }else{
		 $("#expectedRate").html("Expected Rate");
	 }
	 
 }
 </script>
 
 
 
 <script>
 function getEmployementType(){
	 var designationType = document.getElementById("designationType").value;
	 
	 if(designationType == 'C2C'){
		 $("#needApplicantPermit").attr("disabled", "disabled");
		 document.getElementById("needApplicantPermit").checked = false;
		 $("#needApplicantPermit").val('N');
	 }else{
		 $("#needApplicantPermit").removeAttr("disabled");
		
	 }
	 
	 if(designationType == 'C2C'){
		 document.getElementById("vendorsDiv").style.display="";
		 document.getElementById("vendorContactsDiv").style.display="";
		 document.getElementById("employmentOtherTypeDiv").style.display="none";
		
		 
		 $('#designationOtherType').removeAttr("data-validation"); 
		 $('#designationOtherType').attr("data-validation-optional", "true"); 
		 $('#designationOtherType').attr("data-validation","validate_Space validate_length length2-250"); 
		 
		 getVendorsByOrgLocationForAddApplicant();
	 }
	 else if(designationType == 'C2C-1099'){
		 document.getElementById("vendorsDiv").style.display="";
		 document.getElementById("vendorContactsDiv").style.display="";
		 document.getElementById("employmentOtherTypeDiv").style.display="none";
		
		 
		 $('#designationOtherType').removeAttr("data-validation"); 
		 $('#designationOtherType').attr("data-validation-optional", "true"); 
		 $('#designationOtherType').attr("data-validation","validate_Space validate_length length2-250"); 
		 
		 getVendorsByOrgLocationForAddApplicant();
	 }else if(designationType == 'Other'){
		 
		 document.getElementById("vendorsDiv").style.display="none";
		 document.getElementById("vendorContactsDiv").style.display="none";
		 document.getElementById("employmentOtherTypeDiv").style.display="";
		 
		 
		 $('#designationOtherType').removeAttr("data-validation-optional"); 
		 $('#designationOtherType').attr("data-validation","required validate_Space validate_length length2-250"); 
		 
	 }
	 else{
		 
		 
		 document.getElementById("employmentOtherTypeDiv").style.display="none";
		 document.getElementById("vendorsDiv").style.display="none";
		 document.getElementById("vendorContactsDiv").style.display="none";
		 
		 
		 $('#designationOtherType').removeAttr("data-validation"); 
		 $('#designationOtherType').attr("data-validation-optional", "true"); 
		 $('#designationOtherType').attr("data-validation","validate_Space validate_length length2-250"); 
		 
	 }
	 
	 
 }
 </script>
 
 <script>
 function getVendorsByOrgLocationForAddApplicant(){
	 $.ajax({
	    	type: "GET",
	        url: atsUrl+"/getVendorsByOrgLocation?"+tokenParamName+"="+tokenValue+"", 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	var output = "";
	        	output = output + "<option value=''>--Select Vendor--</option>"; 
	        	for (var i=0; i<json.length; i++)
				{
		        		var opt = json[i];
		        		
		        		output = output + "<option value='" +opt.id+ "'>"+opt.orgName+"</option>";
				}
	        	$("#vendors").empty();
		        $("#vendors").append(output);
		        $('#vendors').trigger("chosen:updated"); 
		        
	        },
	    }); 
	 
 }
 </script>
 
 <script>
 function getAddApplicantVendorContactList(){
	 var orgRelationID = $('#vendors').val();
		 $.ajax({
			    type: "POST",
		        url: vmsUrl+"/getOrgSpocsList?"+tokenParamName+"="+tokenValue+"&orgRelationId="+orgRelationID+"&type=Vendor",  
		        success: function(json) {
		        	//var json = JSON.parse(data);
		        	$('#vendorContact').empty();
		        	$('#vendorContact').append('<option value="">--Select Vendor contact--</option>');
		             for(var i=0;i<json.data.length;i++){
		            	 var opt = json.data[i];
		            	 $('#vendorContact').append('<option value='+opt.id+' >'+opt.spocName+'</option>');
		            } 
		            $('#vendorContact').trigger("chosen:updated");
		        }
		 }); 
 }
 </script>
 <script>
function uploadAddApplicantCandidateDoc(){
	fileUpload("newUploadBtn", "addApplicants", "addApplicant_form");
}
</script>
<script>
function importAddApplicantDoc(){
	fileUpload("uploadBtn", "addApplicants", "addApplicant_form");
}
</script>
 