<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<style type="text/css">
.pras.req-title p{
bottom:-14px;
}
.ratetype p {
bottom: -14px !important;
left:-20px !important;
}
.pras.req-title p{
bottom:-2px;
}
.form-control {
padding: 6px 12px !important; height: 34px !important;
}
.bootstrap-select {
width: 100% !important;
}
</style>



<script>
	$(document).ready(function() {
		
		document.getElementById("requirementtemplate_form").reset();
		
	});
</script>
<script src="resources/js/editor.js"></script>
<link href="resources/css/editor.css" type="text/css" rel="stylesheet"/>
		<script>
			$(document).ready(function() {
				$("#txtEditor").Editor();
				// append text in edit mode in richtextarea
				$('.Editor-editor').html($('#txtEditor').text());
			});
       </script> 
<!-- Content Area -->
<section class="content-wraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Job Template</h2>
		</div>
		<div class="pull-right brud-crum">
			<a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&raquo;<a
				href="<c:url value='/template'/>">Job Template List </a>&raquo;
			Add Job Template
		</div>
	</section>
	<div class="content-area content-new clearfix">
		<form:form id="requirementtemplate_form" name="requirementtemplate_form" enctype='multipart/form-data'>
			<div class="form-horizontal req-dtls mb">
				<div class="clearfix">
					<div class="col-lg-12">
						<h3>Add Job Template Details</h3>
					</div>
					<div class="clearfix"></div>
					<div class="mt pras">
					
					<div class="col-md-6">
					
					<div class="form-group" id="jobTitleDiv">
							<label class="col-md-3">Title<span class="text-danger paddingLeft">*</span></label>
							<div class="col-md-9">
									<input type="text" id="jobTitle" name="jobTitle"
										value="${requirement.jobTitle}" field-name="Job Title"
										data-validation="required validate_Space validate_length length2-250"
										value="" class="form-control" >
                                     <input type="hidden" value="${requirement.id}" name="requirementId">
                                      <input type="hidden" value="Y" name="isTemplate">
									<div class="input-group-btn selp-custom-min">
									</div>
							</div>
					</div>
						
					<div class="form-group">
								<label class="col-md-3">Employment Type</label>
									<div class="col-md-9">
										<select class="selectpicker" id="jobType" name="jobType" onChange="getJobType(this);">
											<option value="">Select</option>
											<option ${requirement.jobType  == "C2C" ? 'selected' : ''}><a href="#">C2C</a></option>
											<option ${requirement.jobType  == "W-2" ? 'selected' : ''}><a href="#">W-2</a></option>
											<option ${requirement.jobType  == "C2H" ? 'selected' : ''}><a href="#">C2H</a></option>
											<option ${requirement.jobType  == "Contract" ? 'selected' : ''}><a href="#">Contract</a></option>
											<option ${requirement.jobType  == "Part Time" ? 'selected' : ''}><a href="#">Part Time</a></option>
											<option ${requirement.jobType  == "Full Time" ? 'selected' : ''}><a href="#">Full Time</a></option>
											<option ${requirement.jobType  == "Other" ? 'selected' : ''}><a href="#">Other</a></option>
										</select>
								</div>
					</div>
						<div class="form-group">
								<label class="col-md-3">Experience</label>
								<div class="col-md-9">
									<div class="row">
										<div class="col-md-6">
											<input type="text" class="form-control" placeholder="MinExp"
												id="minExp" name="minExp" value="${requirement.minExp}"
												field-name="MinExp"
												data-validation="validate_Space validate_float validate_length length1-5"
												data-validation-optional="true" onblur="minMaxExperience();"
												value="">
										</div>
										<div class="col-md-6">
											<input type="text" class="form-control" id="maxExp"
												name="maxExp" placeholder="MaxExp"
												value="${requirement.maxExp}" field-name="MaxExp"
												data-validation="validate_Space validate_float validate_length length1-5"
												data-validation-optional="true" onblur="minMaxExperience();"
												value="">
										</div>
									</div>
									<div class="row" id="expId">
										<input type="hidden" name="expValidation" id="expValidation">
									</div>
	
								</div>
							</div>

					<div class="form-group" id="jobTypeDiv" style="display: none">
							<label class="col-md-3">Employment Type</label>
								<div class="col-md-9">
									<input type="text" class="form-control" id="jobOtherType" name="jobOtherType" value="${requirement.otherJobType}" field-name="Job Type" value="">
								</div>
					</div>
					
					</div>
	
	                  <div class="col-md-6">     
	                       <div class="form-group">
							<label class="col-md-3">Education</label>
							<div class="col-md-9">
								<select multiple class="chosen-select form-control" name="education" data-placeholder="Select Education Options" id="education" data-validation-optional="true">
									<c:forEach var="edu" items="${educationsList}">
										<c:if
											test="${fn:contains(requirement.reqEducationListString, edu)}">
											<option selected value="${edu}">${edu}</option>
										</c:if>
										<c:if
											test="${!fn:contains(requirement.reqEducationListString, edu)}">
											<option value="${edu}">${edu}</option>
										</c:if>
									</c:forEach>
								</select>
							</div>
						</div>

                     <%-- <div class="form-group">
							<label class="col-md-3">Skills</label>
							<div class="col-md-9">
								<select multiple class="chosen-select form-control"
									name="skills" id="skills" data-validation-optional="true">
									<c:forEach var="skill" items="${skillsList}">
										<c:if test="${fn:contains(requirement.reqSkillsListString, skill.name)}">
											<option selected value="${skill.name}">${skill.name}</option>
										</c:if>
										<c:if test="${!fn:contains(requirement.reqSkillsListString, skill.name)}">
											<option value="${skill.name}">${skill.name}</option>
										</c:if>
									</c:forEach>
								</select>
							</div>
						</div>  --%>
						
						 <div class="form-group mt25">
			              <label class="col-md-3">Skills</label>
			              <div class="col-md-9">
			                <select multiple class="chosen-select form-control" name="skills" data-placeholder="Select Skills" id="skills" data-validation-optional="true">
			               <c:forEach var="skill" items="${skillsList}">
			               		<c:set var = "NUM" value = "${0}"/>
			               		<c:forEach var="existedSkill" items="${requirement.reqSkillsListString}">
								    <c:if test="${existedSkill eq skill}">
								    	<c:set var = "NUM" value = "${1}"/>
								    	<option selected value="${skill}">${skill}</option>
								    </c:if>
								</c:forEach>
								<c:if test="${NUM == 0}">
									<option  value="${skill}">${skill}</option>
								</c:if>
			                </c:forEach>
			                </select>
			              </div>
			            </div> 
					<%-- 	<div class="form-group">
							<label class="col-md-3">Description</label>
							<div class="col-md-9">
								<textarea rows="2" class="form-control" id="description"
									name="description" value="${requirement.description}"
									field-name="Description"
									data-validation="validate_length length2-5000"
									data-validation-optional="true">${requirement.description}</textarea>
							</div>
						</div> --%>
						
						</div>
						
						<div class="clearfix"></div>
						<div class="clearfix mb">
				          <label class="col-md-3">Description</label>
				          <div class="col-md-12 txtEditor_p">
				          <textarea id="txtEditor" name="description"  class="form-control newContrtextarea">${requirement.description}</textarea>
				          </div>
				        </div> 
						

					</div>
				</div>

			</div>



			<div class="form-footer clearfix">
				<div class="col-lg-12">
					<div class="pull-right">
						<input type="button" value="Save" id="store_RequirementTemplate"
							onclick="storeRequiremetTemplate();" class="btn btn-primary ml">
						<input type="button" value="Cancel"
							onClick="javascript:window.location.href = '<c:url value='/template'/>'"
							class="btn btn-default ml">
					</div>
				</div>
			</div>

		</form:form>

	</div>

</section>

<script type="text/javascript">
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };

        
</script> 

       
<script type="text/javascript">
	function storeRequiremetTemplate() {
		 var ee = $("#txtEditor").val($('.Editor-editor').html()); 
		 if ($('#requirementtemplate_form').validate(false, validationSettings)){ 
			 
			    var formData = getFormData1("requirementtemplate_form");
				var sourceleadUrl = '${sourceleadUrl}';
				$("#store_RequirementTemplate").attr("disabled", "disabled");
				$("#store_RequirementTemplate").addClass('button').val('Processing..');
		        $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		         $.ajax({
					 type : "POST",
					 data: formData,
	  		         processData: false,
	  		         contentType:false, 
					  url : sourceleadUrl+"/storeRequirementTemplate?"+tokenParamName+"="+tokenValue+"",
					  	success : function(data) {
					  		location.replace(sourceleadUrl+"/template");

					},
				});
			
			
			return true;
		 }else{ 
				return false;
		 } 
	} 
	
	$('body').on('blur', '#requirementtemplate_form', function() {
		$("#requirementtemplate_form").showHelpOnFocus();
		$("#requirementtemplate_form").validateOnBlur(false, validationSettings);
	});
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

<script type="text/javascript">
		window.onload = function () {
			
			var jobType = '${requirement.jobType}';
			if(null != jobType && jobType != "" && jobType != "undefined" && jobType == 'Other'){
				  document.getElementById("jobTypeDiv").style.display="block";
				  $("#jobOtherType").attr("data-validation-optional", "true");
				  $("#jobOtherType").attr("data-validation", "validate_Space validate_length length2-250");
			}else{
				document.getElementById("jobTypeDiv").style.display="none";
				  $("#jobOtherType").attr("data-validation-optional", "true");
				  $("#jobTypeDiv").find('p.jquery_form_error_message').remove();
				  $("#jobOtherType").val('');
			}
			
			
		 };
</script> 
<!-- Eductions and Skills Autocomplete Scripts Starts Here -->
<script type="text/javascript">
			var config = {
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			}
			
		   $("#countryReq").chosen();
	       $("#state").chosen();
	       $("#city").chosen();
	       
	       
	       $("#countryReq").on('change', function (e) {
	    	   getStates();
	       });
	       $("#state").on('change', function (e) {
	    	   getCities();
	       });
		       
	       
           
            //Adding Customized Educations to the list
	        var selectone, chosenone;
	        selectone = $("#education");
	        selectone.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add New Education:' });
	        chosenone = selectone.data('chosen');
	        chosenone.search_field.on('keydown keyup', function(e1)
		    {
		    	var newEducation = this.value.replace(",","");
		        if (tagItBykeyForSkills(e1) && (newEducation != null  && newEducation != '' && newEducation != 'undefined'))
		        {
		        	preventTabkey(e1);
		        	var par = this.parent;
		            var option = $("<option>").val(newEducation).text(newEducation);
		            selectone.append(option);
		            selectone.find(option).prop('selected', true);
	        		$("#education").trigger("chosen:updated");
	        		
		        }
		    }); 
		    //Adding Customized Educations to the list
		    
		     //Adding Customized Skills to the list
		       var select, chosen;
	        select = $("#skills");
	        select.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) Or Space To Add New Skills:' });
		    chosen = select.data('chosen');
		    chosen.search_field.bind(('keydown keyup'), function(e)
		    { 
		    	 
		    	 var enterSkillsArray = this.value.split(",");
		    	 if(enterSkillsArray.length > 1){
	        		 for (var i=0; i < enterSkillsArray.length; i++) {
	        			 var option = $("<option>").val(enterSkillsArray[i].replace(",","")).text(enterSkillsArray[i].replace(",",""));
	        			 select.append(option);
	        			 select.find(option).prop('selected', true);
	 	        		 $("#skills").trigger("chosen:updated");
	                 }
		    	 }else{
			    	var newSkill = this.value.replace(","," ");
			    	
			    	 
			        if (tagItBykeyForSkills(e) && (newSkill != null  && newSkill != '' && newSkill != 'undefined'))
			        {
			        	preventTabkey(e); 
			        	var par = this.parent;
			        		 var option = $("<option>").val(newSkill.replace(",","")).text(newSkill.replace(",",""));
			        		 select.append(option);
			        		 select.find(option).prop('selected', true);
				        		$("#skills").trigger("chosen:updated");
				        		
				        		
			        }
		    	 }
		    });
		    
			for (var selector in config) {
			  $(selector).chosen(config[selector]);
			}
			$('.chosen-select').each(function() {
			var $this = $(this);
			$this.next().css({'width': '100%'});
			})
			
</script>
<!-- Eductions and Skills Autocomplete Scripts End Here -->

<script>
//Function to validate if maximum exp value is more the minimum exp value
function minMaxExperience(){
    var maxExp = ($('#maxExp').val());
    var minExp = ($('#minExp').val());
    
    if (parseFloat(maxExp) <= parseFloat(minExp)){
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

</script>
<script>
function getJobType(jobType){
	var existingJobType = '${requirement.jobType}';
	if(jobType.value == 'Other'){
		  document.getElementById("jobTypeDiv").style.display="";
		  $("#jobOtherType").attr("data-validation-optional", "true");
		  $("#jobOtherType").attr("data-validation", "validate_Space validate_length length2-250");
	}else{
		document.getElementById("jobTypeDiv").style.display="none";
		  $("#jobOtherType").attr("data-validation-optional", "true");
		  $("#jobTypeDiv").find('p.jquery_form_error_message').remove();
		  if(existingJobType ==null || existingJobType == "" || existingJobType == "undefined" || existingJobType != 'Other'){
			  $("#jobOtherType").val('');
		  }
	}
}
</script>


<script>
function getEditRequirementDetails(requirementId){
	var sourceleadUrl = '${sourceleadUrl}';
	if(requirementId !=''){
		$.ajax({
			type : "GET",
			url : sourceleadUrl + "/getEditRequirementDetails?"+tokenParamName+"="+tokenValue+"&requirementId="+requirementId,
			success : function(data) {
				var json = JSON.parse(data);
				$(".Editor-container").find('.Editor-editor').html(json['description']);
				
			},
		}); 
	}
	
}
</script>

<script type="text/javascript">
$(document).ready(function(){
	var requirementId  = "${requirementId}";
	
	getEditRequirementDetails(requirementId);
	
});
</script>
<jsp:include page="../masterFooter.jsp" />