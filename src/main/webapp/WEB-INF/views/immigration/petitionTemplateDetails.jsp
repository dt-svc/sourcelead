<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var = "templateDTO" scope = "page" value = '<%= session.getAttribute("templateDTO")%>'/>
<section class="addImmiWrap">
    <div class="addImmiInnWrap">
        <div class="immiStepsHead clearfix">
            <h2>Template Details</h2>

            <ul class="addImmiSteps">
                <li value="" class= "${not empty templateDTO.templateName ? 'active page-updated' : 'active'}">1</li>
                <li>2</li>
                <li>3</li>
                <li>4</li>
                <b></b>
                <div class="clearfix"></div>
            </ul>
        </div>
        <form id="petitionTemplateDetails" name="petitionTemplateDetails">
            <div class="addImmiContent clearfix">
                <div class="immiTemp clearfix">
                    <div class="col-md-7 clearfix noPad mt">
                        <div class="col-md-12 noPad mb" id="templateNameDiv">
                            <label class="col-md-3">Template Name<span class="text-danger paddingLeft">*</span></label>
                            <div class="col-md-9"><input type="text" id="templateName" value="${not empty templateDTO.templateName ? templateDTO.templateName : ''}" name="templateName" class="form-control" field-name="Template Name" data-validation="required validate_length length2-100 validate_AlphaNumber" onkeyup="immigration.validateTemplateName();" onblur="immigration.validateTemplateName();"></div>
                        </div> 
                        <div class="col-md-12 noPad mb">
                            <label class="col-md-3">Description<span class="text-danger paddingLeft">*</span></label>
                            <div class="col-md-9"><textarea id="templateDescription" name="templateDescription" class="form-control" rows="3" field-name="Description" data-validation="required validate_length length2-500">${not empty templateDTO.templateDescription ? templateDTO.templateDescription : ''}</textarea></div>
                        </div>
                        <div class="col-md-12 noPad mb" id="templateNameDiv">
                            <label class="col-md-3">Country<span class="text-danger paddingLeft">*</span></label>
                           	<div class="col-md-8 padding_right">
								<select class="chosen-select form-control" name="country" onchange="immigration.getPetitionTypeOnCountries($(this), ${templateDTO.petitionType});" id="countryName" field-name="Country" data-validation="required">
									<option value="">--Select Country--</option>
									<option value="Singapore" ${templateDTO.country == 'Singapore' ? 'selected' : ''}>Singapore</option>
									<option value="Canada" ${templateDTO.country == 'Canada' ? 'selected' : ''}>Canada</option>
									<option value="United States" ${templateDTO.country == 'United States' ? 'selected' : ''}>United States</option>
									<option value="Australia" ${templateDTO.country == 'Australia' ? 'selected' : ''}>Australia</option>
									<option value="India" ${templateDTO.country == 'India' ? 'selected' : ''}>India</option>
									<option value="United Kingdom" ${templateDTO.country == 'United Kingdom' ? 'selected' : ''}>United Kingdom</option>
								</select>
							</div>
                        </div> 
                        <div class="col-md-12 noPad mb" id="templateNameDiv">
                            <label class="col-md-3">Petition Type<span class="text-danger paddingLeft">*</span></label>
                            <div class="col-md-8 padding_right">
								<select class="chosen-select form-control" name="petitionType" id="petitionTypeTemplate" field-name="Petition Type" data-validation="required">
								</select>
							</div>
                        </div> 
                    </div>
                </div>
            </div>
        </form>

        <div class="addImmiFoot">
            <!--<a href="javascript:void(0)" class="pull-left">Prev</a>-->
            <a href="javascript:void(0)" onclick="immigration.savePetitionTemplateAndContinue('saveContinue')" class="ml10 nextBtn" id="saveAndContNewTemplate">Save &amp; Continue</a>
            <a href="javascript:void(0)" onclick="immigration.savePetitionTemplateAndContinue('save')" class="nextBtn">Save</a>
            <a href="javascript:void(0)" onclick="immigration.resetFormData('petitionTemplateDetails')">Reset</a>
            <div class="clearfix"></div>
        </div>   
    </div>
</section>  

<script type="text/javascript">
		
	    $(document).ready(function () {
	    	 immTemplId = '<c:out value ="${not empty templateDTO.id ? templateDTO.id : ''}"/>';
	        immigration.getPetitionTypeOnCountries($("#countryName"),"${templateDTO.petitionType}");
	    });
      
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
</script>
