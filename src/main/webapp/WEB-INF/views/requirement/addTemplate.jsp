<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<script
	src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>


<link href="resources/css/jquery-ui.css" rel="stylesheet">
<link rel="stylesheet" href="resources/css/chosen.min.css">

<script src="resources/js/jquery-fun.js"></script>

<script src="resources/js/chosen.jquery.min.js"></script>
<script src="resources/js/bootstrap-select.min.js"></script>
<link rel="stylesheet" href="resources/css/bootstrap-select.min.css">

<!-- these files for autocomplete with tagit -->
<link href="resources/css/jquery-tagit.css" rel="stylesheet">
<script src="resources/js/csc.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/tag-it.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/tag-it.min.js" type="text/javascript" charset="utf-8"></script> 
<!-- end files for autocomplete with tagit -->

<!-- Content Area -->
<section class="content-wraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Template Details</h2>
		</div>
		<div class="pull-right brud-crum">
			<a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&raquo;<a
				href="<c:url value='/requirementList'/>">Requirement List </a>&raquo;
			Add Requirement
		</div>
	</section>
	<div class="content-area content-new clearfix">
		<form:form id="requirementtemplate_form" name="requirementtemplate_form" enctype='multipart/form-data'>
			<div class="form-horizontal req-dtls myForm row mb">
				<div class="clearfix">
					<div class="col-lg-12">
						<h3>Add Template Details</h3>
					</div>

					<div
						class="col-xs-12 col-sm-12 col-md-12 col-lg-12 mt pras req-title">
						<div class="form-group" id="jobTitleDiv">
							<label class="col-md-3">Title<span class="text-danger">*</span></label>
							<div class="col-md-9">
								<div class="input-group">
									<input type="text" id="jobTitle" name="jobTitle"
										value="${requirement.jobTitle}" field-name="Job Title"
										data-validation="required validate_Space validate_length length2-250"
										value="" class="form-control" onblur="getTemplateVal();">
                                     <input type="hidden" value="${requirement.id}" name="requirementId">
                                      <input type="hidden" value="Y" name="isTemplate">
									<div class="input-group-btn selp-custom-min">
										<select class="selectpicker" id="jobType" name="jobType"
											onChange="getJobType(this);">
											<option ${requirement.jobType  == 'c2c' ? 'selected' : ''}><a href="#">C2C</a></option>
											<option ${requirement.jobType  == 'W-2' ? 'selected' : ''}><a href="#">W-2</a></option>
											<option ${requirement.jobType  == 'C2H' ? 'selected' : ''}><a href="#">C2H</a></option>
											<option ${requirement.jobType  == 'Contract' ? 'selected' : ''}><a href="#">Contract</a></option>
											<option ${requirement.jobType  == 'Part Time' ? 'selected' : ''}><a href="#">Part Time</a></option>
											<option ${requirement.jobType  == 'Full Time' ? 'selected' : ''}><a href="#">Full Time</a></option>
											<option ${requirement.jobType  == 'Other' ? 'selected' : ''}><a href="#">Other</a></option>
										</select>
									</div>
								</div>
							</div>
						</div>




						<div class="form-group" id="jobTypeDiv" style="display: none">
							<label class="col-md-3">Job Type</label>
							<div class="col-md-9">
								<input type="text" class="form-control" id="jobOtherType"
									name="jobOtherType" value="${requirement.otherJobType}"
									field-name="Job Type" value="">
							</div>
						</div>


						<div class="form-group">
							<label class="col-md-3">Education</label>
							<div class="col-md-9">
								<select multiple class="chosen-select form-control"
									name="education" id="education"
									data-validation="validate_Space"
									data-validation-optional="true">
									<c:forEach var="edu" items="${educationsList}">
										<c:if
											test="${fn:contains(requirement.reqEducationListString, edu.name)}">
											<option selected value="${edu.name}">${edu.name}</option>
										</c:if>
										<c:if
											test="${!fn:contains(requirement.reqEducationListString, edu.name)}">
											<option value="${edu.name}">${edu.name}</option>
										</c:if>
									</c:forEach>
								</select>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3">Experience</label>
							<div class="col-md-9">
								<div class="row">
									<div class="col-md-6">
										<input type="text" class="form-control" placeholder="minExp"
											id="minExp" name="minExp" value="${requirement.minExp}"
											field-name="MinExp"
											data-validation="validate_Space validate_length length1-3 validate_float"
											data-validation-optional="true" onblur="minMaxExperience();"
											value="">
									</div>
									<div class="col-md-6">
										<input type="text" class="form-control" id="maxExp"
											name="maxExp" placeholder="maxExp"
											value="${requirement.maxExp}" field-name="MaxExp"
											data-validation="validate_Space validate_length length1-3 validate_float"
											data-validation-optional="true" onblur="minMaxExperience();"
											value="">
									</div>
								</div>
								<div class="row" id="expId">
									<input type="hidden" name="expValidation" id="expValidation">
								</div>

							</div>
						</div>

						<div class="form-group">
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
						</div>




						<div class="form-group">
							<label class="col-md-3">Description</label>
							<div class="col-md-9">
								<textarea rows="2" class="form-control" id="description"
									name="description" value="${requirement.description}"
									field-name="Description"
									data-validation="validate_length length2-5000"
									data-validation-optional="true">${requirement.description}</textarea>
							</div>
						</div>

					</div>
				</div>

			</div>



			<div class="form-footer clearfix">
				<div class="col-lg-12">
					<div class="pull-right">
						<input type="button" value="Save" id="save"
							onclick="storeRequiremetTemplate();" class="btn btn-primary ml">
						<input type="button" value="Cancel"
							onClick="javascript:location.href = '<c:url value='/template'/>'"
							class="btn btn-default ml">
					</div>
				</div>
			</div>

		</form:form>

	</div>

</section>


<script type="text/javascript">
	function storeRequiremetTemplate() {
		
		var validationSettings = {
				errorMessagePosition : 'element',
				borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd'
			};
		
		 if ($('#requirementtemplate_form').validate(false, validationSettings)){ 
			
			var sourceleadUrl = '${sourceleadUrl}';
			var frm = $('#requirementtemplate_form').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, ""); 
		    var jsonData = encodeURIComponent(con);
			var form = $('#requirementtemplate_form')[0];
      		var formData = new FormData(form); 
			//It will escape all the special characters
			$("#store_RequirementTemplate").attr("disabled", "disabled");
			$("#store_RequirementTemplate").addClass('button').val('Processing..');

			$.ajax({
						type : "POST",
		        	    data: formData,
		        	    contentType : 'application/json; charset=utf-8',
		        	    enctype: 'multipart/form-data',
		    	        processData: false,
		    	        contentType:false,
		        	    dataType : 'json',
						url : sourceleadUrl+"/storeRequirementTemplate?${_csrf.parameterName}=${_csrf.token}&json="+jsonData,
						success : function(result) {
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
	        chosenone.search_field.on('keyup', function(e1)
		    {
		    	var newEducation = this.value.replace(",","");
		        if ((e1.which == 13 || e1.which == 188) && (newEducation != null  && newEducation != '' && newEducation != 'undefined'))
		        {
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
           select.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add New Skill:' });
           chosen = select.data('chosen');
           chosen.search_field.on('keyup', function(e)
           {
        	   var newSkill = this.value.replace(",","");
               if ((e.which == 13 || e.which == 188) && (newSkill != null  && newSkill != '' && newSkill != 'undefined'))
               {
                   var option = $("<option>").val(newSkill).text(newSkill);
                   select.append(option);
                   select.find(option).prop('selected', true);
                   $("#skills").trigger("chosen:updated");
               }
           });
           //Adding Customized Skills to the list
           
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
    
    if (parseFloat(maxExp) < parseFloat(minExp)){
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

<jsp:include page="../masterFooter.jsp" />