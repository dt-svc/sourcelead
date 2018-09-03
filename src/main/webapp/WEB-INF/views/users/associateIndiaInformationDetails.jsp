 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


 <div id="tab-3" class="tabsMenu_tabs_inn clearfix">
  <div id="informationDetailsLoading"></div>
 	  <form:form id="associate_informationDetails_form" name="associate_informationDetails_form" method="post">
	 	  <input type="hidden" name="associateUserId" id="pfassociateUserId"/>
	      <input type="hidden" name="pfesiId" id="pfesiId"/>
	      
	      <div class="clearfix row minHeightcol5">
	      	<div class="clearfix"></div>
              <div class="col-md-12 mb">
                <div class="clearfix row">
              	     <h4 class="col-md-12">PF</h4>
		                  <div class="col-md-6 mb checkbox-new">
							<input id="pf" class="checkbox-custom" type="checkbox" name="eligibleForPf"  onClick="pfFunction()">
							<label for="pf" class="checkbox-custom-label"><span class="ml10">Eligible for PF</span></label>
		                    <div>
		                    </div>
		                  </div>
		                  
		                  <div class="clearfix"></div>
		                  <div class="clearfix" id="pfEligible" style="display: none;">
		                   <div class="col-md-5">
		                    <label>UAN Number<span class="text-danger">&nbsp;*</span></label>
		                    <div>
		                     <input type="text" class="form-control" name="uanNumber" id="pfUan" field-name="UAN Number"  field-name="PF UAN">
		                    </div>
		                  </div>
		                  
		                  
		                  <div class="col-md-5">
		                    <label>PF Number<span class="text-danger">&nbsp;*</span></label>
		                    <div>
		                       <input type="text" class="form-control" name="pfNumber"  id="pfNumber" field-name="PF Number" field-name="PF Number">
		                    </div>
		                  </div>
		                  <div class="col-md-5">
		                    <label>Enrolled Date</label>
		                    <div>
		                       <input type="text" class="form-control calendar" name="enroleDate"  id="enrolledDate" readonly="readonly">
		                    </div>
		                  </div>
		                  </div>
		                  
		                  
                 <div class="clearfix"></div>
                   <div class="col-md-6 mb checkbox-new">
					<input id="esi" class="checkbox-custom" name="eligibleForEsi" type="checkbox"  onClick="esiFunction()">
					<label for="esi" class="checkbox-custom-label"><span class="ml10">Eligible for ESI</span></label>
                    <div>
                    </div>
                  </div>
                  <div class="clearfix"></div>
                  
                  <div class="col-md-5" id="esiEligible" style="display: none;">
                    <label>ESI Number</label>
                    <div>
                      <input type="text" class="form-control" name="esinumber"  id="esiNumber" data-validation="validate_int validate_length length1-17" data-validation-optional="true" field-name="ESI Number">
                    </div>
                  </div>
                  
                 <div class="clearfix"></div>
                 <div class="clearfix footerstyelsNew">
                 <div class="col-md-6">
                <input type="button" value="Previous" onclick="shiftToAssociateBankingTab();" class="btn btn-default">
			     </div>
			     
	                 <div class="col-md-6">
	                   <div class="pull-right">
	                    <button type="button" class="btn btn-success btnSuccess" id="savePaymentButton" onclick="saveOnboardingCandidatePfEsiDetails();" >Save & Continue</button>
                          <button type="button" class="btn btn-default ml10" onclick="skipToLoanAndAdvancesTab();" >Skip</button>
	                    </div>
	                 </div>
                </div>
                  
                  
                </div>
              </div>
	      </div>
 	  </form:form>
 </div>
 
 
 
 <script>
 function saveOnboardingCandidatePfEsiDetails(){
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
	                	 var indLoanAndAdvancesDetails = document.getElementById("indLoanAndAdvancesDetails");
						 document.getElementById("indLoanAndAdvancesDetails").removeAttribute("class");
						 indLoanAndAdvancesDetails.href="#tab-4";
						 $("#indLoanAndAdvancesDetails").click();
		        		
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
 function skipToLoanAndAdvancesTab(){
	 var indLoanAndAdvancesDetails = document.getElementById("indLoanAndAdvancesDetails");
	 document.getElementById("indLoanAndAdvancesDetails").removeAttribute("class");
	 indLoanAndAdvancesDetails.href="#tab-4";
	 $("#indLoanAndAdvancesDetails").click();
 }
 </script>
 
 
 <script>
	  function getIndAssociateInformationDetails(){
		  var userId = $("#associateId").val();
		  if(userId != null && userId != 'undefined' && userId != ''){    
			  $("#pfassociateUserId").val(userId);
		      $.ajax({
			    	type: "POST",
			    	url: sourceleadUrl+"/getIndAssociatePFESIAccountDetails?userId="+userId,
			        success: function(json) {
			        	
			        	if(json != null){
			        		$("#pfesiId").val(json.pfesiId);
			        		if(json.eligibleForPf == 'Y'){
			        			  pfel.style.display = "block";
			        			  document.getElementById("pf").checked = true;
			        			  $("#pf").val('Y');
			        			  $("#pfUan").val(json.firstName);
			        			  $("#pfNumber").val(json.firstName);
			        			  $("input[name=enroleDate]").val(json.enroleDate);
			        			  $("#pfUan").attr("data-validation", "required validate_int validate_length length1-12");
			        		      $("#pfNumber").attr("data-validation", "required validate_AlphaNumber validate_length length1-19");
			        			 
			        		}else{
			        			  pfel.style.display = "none";
			        			  document.getElementById("pf").checked = false;
			        			  $("#pf").val('');
			        			  $("#pfUan").val('');
			        			  $("#pfNumber").val('');
			        			  $("input[name=enroleDate]").val('');
			        			  $('#pfUan').removeAttr('data-validation');
		        		    	  $('#pfNumber').removeAttr('data-validation');
		        		    	  $("#uanError").find("p.jquery_form_error_message").remove();
		        		    	  $("#pfError").find("p.jquery_form_error_message").remove();
			        			 
			        		}
			        		
			        		if(json.eligibleForEsi == 'Y'){
			        			 esiEl.style.display = "block";
			        			 document.getElementById("esi").checked = true;
			        			 $("#esi").val('Y');
			        			 $("#esinumber").val(json.esinumber);
			        			 $("#esiNumber").attr("data-validation", "validate_int validate_length length1-17");
			        			
			        		}else{
			        			 esiEl.style.display = "none";
			        			 document.getElementById("esi").checked = false;
			        			 $("#esi").val('');
			        			 $("#esinumber").val('');
			        			 $('#esiNumber').removeAttr('data-validation');
			        		}
			        	}
			        	
			        },
			    });
		  }
	      
	  }
 </script>
 
 <script>
//Pf Function
function pfFunction() {
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
function esiFunction() {
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
$(function() {
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
});
</script>


<script>
function shiftToAssociateBankingTab(){
	 var indBankingDetails = document.getElementById("indBankingDetails");
	 document.getElementById("indBankingDetails").removeAttribute("class");
	 indBankingDetails.href="#tab-2";
	 $("#indBankingDetails").click();
}
</script>