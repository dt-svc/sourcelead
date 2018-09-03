<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="tabsMenu_tabs_inn project_wrap" style="display: block;">
 <form:form id="associate_informationDetails_form" name="associate_informationDetails_form" method="post">
	 	 <div id="informationDetailsLoading"></div>
	 	  <input type="hidden" name="associateUserId" value='${userId}' id="pfassociateUserId"/>
	      <input type="hidden" name="pfesiId" value="${pfesiDTO.pfesiId}" id="pfesiId"/>
	      
	      
	      <div class="clearfix  minHeightcol5">
              <div class="col-md-12 mb">
              	     <h4 class="col-md-12 noPad">PF</h4>
		                  <div class="mb checkbox-new clearfix">
							<input id="pf" class="checkbox-custom" type="checkbox" name="eligibleForPf"  ${pfesiDTO.eligibleForPf == 'Y' ? 'checked' : ''}   onClick="associatePfFunction()">
							<label for="pf" class="checkbox-custom-label"><span class="ml10">Eligible for PF</span></label>
		                    <div>
		                    </div>
		                  </div>              
                <div class="clearfix row mt">
		                  <div class="clearfix"></div>
		                  <div  id="pfEligible" ${pfesiDTO.eligibleForPf == 'Y' ? '' : 'style="display: none;"'} >
		                   <div class="col-md-5">
		                    <label>UAN Number<span class="text-danger">&nbsp;*</span></label>
		                    <div>
		                     <input type="text" class="form-control" name="uanNumber" id="pfUan" value="${pfesiDTO.uanNumber}" ${pfesiDTO.eligibleForPf == 'Y' ? 'data-validation="required validate_int validate_length length1-12"' : ''}  field-name="UAN Number"  field-name="PF UAN">
		                    </div>
		                  </div>
		                  
		                  <div class="col-md-5">
		                    <label>PF Number<span class="text-danger">&nbsp;*</span></label>
		                    <div>
		                       <input type="text" class="form-control" name="pfNumber" value="${pfesiDTO.pfNumber}" id="pfNumber" ${pfesiDTO.eligibleForPf == 'Y' ? 'data-validation="required validate_AlphaNumber validate_length length1-19"' : ''} field-name="PF Number" field-name="PF Number">
		                    </div>
		                  </div>
		                  <div class="col-md-5">
		                    <label>Enrolled Date</label>
		                    <div>
		                       <input type="text" class="form-control calendar" name="enroleDate" onmouseenter="getDatePicker();"  value="${pfesiDTO.enroleDate}"  id="enrolledDate" readonly="readonly">
		                    </div>
		                  </div>
		                  </div>
		        </div>  
		                 
                 <div class="clearfix mt"></div>
                 <h4 class="col-md-12 noPad">ESI</h4>
                   <div class="mb checkbox-new clearfix">
					<input id="esi" class="checkbox-custom" name="eligibleForEsi" type="checkbox" ${pfesiDTO.eligibleForEsi == 'Y' ? 'checked' : ''} onClick="associateEsiFunction()">
					<label for="esi" class="checkbox-custom-label"><span class="ml10">Eligible for ESI</span></label>
                    <div>
                    </div>
                  </div>
                  <div class="clearfix row">
                  
                  <div class="col-md-5" id="esiEligible" ${pfesiDTO.eligibleForEsi == 'Y' ? '' : 'style="display: none;"'}>
                    <label>ESI Number</label>
                    <div>
                      <input type="text" class="form-control" name="esinumber"  id="esiNumber" value="${pfesiDTO.esinumber}" data-validation="validate_int validate_length length1-17" data-validation-optional="true" field-name="ESI Number">
                    </div>
                  </div>
               </div>
              </div>
	      </div>
	      
	      <div class="clearfix"></div>
        <div class="modal-footer">
          <button type="button" class="btn btn-success btnSuccess" id="savePaymentButton" onclick="saveAssociatePfEsiDetails();" >Save</button>
        </div>
 	  </form:form>
</div>



<script>
 function saveAssociatePfEsiDetails(){
	 var validationSettings = {
		        errorMessagePosition : 'element',
		       borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd' 
	         }; 
	 
	 if ($('#associate_informationDetails_form').validate(false, validationSettings)){ 
	        var formData = getFormData("associate_informationDetails_form");
	        $("#informationDetailsLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	         $.ajax({
		    	type: "POST",
		    	data: formData,
		    	 url: sourceleadUrl+"/saveOnboardingCandidatePfEsiDetails?${_csrf.parameterName}=${_csrf.token}",
		        success: function(response) {
		        	$("#informationDetailsLoading").removeAttr('style');
		        	if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
      	    		    showError(response.responseMessage);
      	    		}
		        	else if (response.response === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (response.response === "SUCCESS") {
		        		 var esipf = response.object;
	                	 $("#pfesiId").val(esipf.id);
		        		
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
 }
 
 $('body').on('blur', '#associate_informationDetails_form', function() {
		$("#associate_informationDetails_form").showHelpOnFocus();
		$("#associate_informationDetails_form").validateOnBlur(false, validationSettings);  
	});
 </script>

<script>
//Pf Function
function associatePfFunction() {
    var checkBox = document.getElementById("pf");
    var pfel = document.getElementById("pfEligible");
    var val = checkBox.checked;
    if (checkBox.checked == true){
        pfel.style.display = "block";
        $("#pfUan").attr("data-validation", "required validate_int validate_length length1-12");
        $("#pfNumber").attr("data-validation", "required validate_AlphaNumber validate_length length1-19");
        $("#pf").val('Y');
    } else {
       pfel.style.display = "none";
    	$('#pfUan').removeAttr('data-validation');
    	$('#pfNumber').removeAttr('data-validation');
    	$("#uanError").find("p.jquery_form_error_message").remove();
    	$("#pfError").find("p.jquery_form_error_message").remove();
    	$("#pfUan").val('');
    	$("#pfNumber").val('');
    	$("#enrolledDate").val('');
    }
}
		//ESI Function
function associateEsiFunction() {
    var checkBox = document.getElementById("esi");
    var esiEl = document.getElementById("esiEligible");
    if (checkBox.checked == true){
       esiEl.style.display = "block";
       $("#esi").val('Y');
       $("#esiNumber").attr("data-validation", "validate_int validate_length length1-17");
    } else {
       esiEl.style.display = "none";
       $("#esiNumber").val('');
       $('#esiNumber').removeAttr('data-validation');
    }
}
</script>

<script>
function getDatePicker(){
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
	  $('#enrolledDate').datepicker({
			dateFormat : finalDateFormatProjectSave,
			changeMonth: true,
			changeYear: true, 
			 
		});
}


</script>