 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
  
  <div id="tab-2" class="tabsMenu_tabs_inn clearfix">
  <div id="accountDetailsLoading"></div>
   <form:form id="associate_bankingDetails_form" name="associate_bankingDetails_form" method="post">
   <input type="hidden" name="associateUserId" id="associateUserId"/>
   <input type="hidden" name="paymentAccountId" id="paymentAccountId"/>
   <div class="clearfix row minHeightcol5">
 		<div class="col-md-5" id= "paymentDiv">
                <label>Payment Mode<span class="text-danger">&nbsp;*</span></label>
                <div>
                  <select class="form-control chosen-select" id="accountType" name="accountType" onchange=" removePaymentValidation();" data-validation="required" field-name="Payment Mode">
                        <option value="">--Select Payment Mode--</option>
	                    <option value="SAVINGS" data-type="1">Savings</option>
	                    <option value="CHEQUE" data-type="2">Cheque</option>
                  </select>
                </div>
         </div>
         
         <div class="col-md-5">
                  <label>Bank Name<span class="text-danger">&nbsp;*</span></label>
                  <div>
                   <input type="text" id="bankName" name="bankName"  class="form-control" data-validation="required validate_Space validate_length length1-50" field-name="Bank Name"/>
                  </div>
          </div>
          
          <div class="col-md-5">
                  <label>Account Number<span class="text-danger">&nbsp;*</span></label>
                  <div>
                   <input type="text" id="accountNumber"  field-name="Account Number"  name="accountNumber" class="form-control" data-validation="required validate_Space validate_int validate_length length9-15"/>
                  </div>
          </div>
          
          <div class="col-md-5">
                  <label>BSR Number</label>
                  <div>
                   <input type="text" id="bsrNumber"  field-name="BSR Number"  name="bsrNumber" class="form-control" data-validation-optional="true" data-validation="validate_Space validate_length length9-15"/>
                  </div>
          </div>
          
          <div class="col-md-5">
                  <label>MICR Code</label>
                  <div>
                  <input type="text" id="micrCode"  field-name="MICR Code" name="micrCode" class="form-control" data-validation-optional="true" data-validation="validate_Space validate_length length9-15"/>
                  </div>
          </div>
          
          <div class="col-md-5">
                  <label>IFSC Code<span class="text-danger">&nbsp;*</span></label>
                  <div>
                  <input type="text" id="routingNumber" field-name="IFSC Code" name="routingNumber" class="form-control" data-validation="required validate_Space validate_length length11-11" />
                  </div>
          </div>
          
          <div class="col-md-5">
                  <label>First Name<span class="text-danger">&nbsp;*</span></label>
                  <div>
                  <input type="text" id="firstName" name="firstName" pattern="[A-Za-z\s]*" field-name="First Name" class="form-control" data-validation="required validate_Space validate_Alphanumeric validate_length length1-100"/>
                  </div>
          </div>
          
          <div class="col-md-5">
                  <label>Last Name<span class="text-danger">&nbsp;*</span></label>
                  <div>
                  <input type="text" id="lastName" name="lastName" pattern="[A-Za-z\s]*" field-name="Last Name" class="form-control" data-validation="required validate_Space validate_Alphanumeric validate_length length1-100"/>
                  </div>
          </div>
          
            <div class="clearfix"></div>
                 <div class="clearfix footerstyelsNew">
                 <div class="col-md-6">
                 <input type="button" value="Previous" onclick="shiftToAssociateTab();" class="btn btn-default">
			     </div>
                  <div class="col-md-6">
                   <div class="pull-right">
                    <button type="button" class="btn btn-success btnSuccess" id="savePaymentButton" onclick="savePaymentAccount();" >Save & Continue</button>
                     <button type="button" class="btn btn-default ml10" onclick="skipToInfomationTab();" >Skip</button>
                    </div>
                  </div>
                </div>
  </div>
  </form:form>
  </div>
  
  
  
  <script type="text/javascript">
 function savePaymentAccount() {

	 var pmsUrl ='${SourceLeadWeb_pmsUrl}';
	 var userId = $("#associateId").val();
	 var empType = $("#empType").val(); 
	 
	 if ($('#associate_bankingDetails_form').validate(false, validationSettings)){ 
			   var formData = getFormData("associate_bankingDetails_form");
				$("#accountDetailsLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	            $.ajax({
					type : "POST",
					data: formData,
	  		        url : pmsUrl+"/save-ind-associate-payment-account?userId="+userId,
					success : function(result) {
						
						if (result && result.response === "INVALID_DATA") {
								$("#accountDetailsLoading").removeAttr("style");
			                    showError(result.responseMessage);
			                } else if (result.response === "AWKWARD") {
			                    location.href = "/errorPage";
			                } else if (result.response === "SUCCESS") {
			                	 var paymentAccount = result.object;
			                	 $("#paymentAccountId").val(paymentAccount.paymentAccountId);
							     $("#accountDetailsLoading").removeAttr("style");
							     
							     var indInformationDetails = document.getElementById("indInformationDetails");
								 document.getElementById("indInformationDetails").removeAttribute("class");
								 indInformationDetails.href="#tab-3";
								 $("#indInformationDetails").click();
								 
			                } else if (result.indexOf('Sign In') !== -1) {
								location.replace(pmsUrl);
							} else {
			                    showError("Operation failed, please try again");
			                }
					},
				});
		
	        return true;
	  }else{
		return false;
	  }
    }
 
   $('body').on('blur', '#associate_bankingDetails_form', function() {
		$("#associate_bankingDetails_form").showHelpOnFocus();
		$("#associate_bankingDetails_form").validateOnBlur(false, validationSettings);  
   });
  
 
</script>
  
  
 <script>
function shiftToAssociateTab(){
	var associates = document.getElementById("associates");
	document.getElementById("associates").removeAttribute("class"); 
	associates.href="#tab-1"; 
	$("#loading").removeAttr('style');
	$("#create_user" ).addClass('button').val('Save & Continue');
    $("#create_user").removeAttr("disabled");
    $("#associates").click();
}


function skipToInfomationTab(){
     var indInformationDetails = document.getElementById("indInformationDetails");
	 document.getElementById("indInformationDetails").removeAttribute("class");
	 indInformationDetails.href="#tab-3";
	 $("#indInformationDetails").click();
}
</script> 
 
<script>
function removePaymentValidation(){
	 $('#paymentDiv').find('p.jquery_form_error_message').remove();
}
</script> 
  
  
  <script>
	  function getIndAssociateBankingDetails(){
	     
		  var userId = $("#associateId").val();
	     var pmsUrl ='${SourceLeadWeb_pmsUrl}';
	     
	     if(userId != null && userId != 'undefined' && userId != ''){
		     $("#associateUserId").val(userId);
			 $.ajax({
			    	type: "POST",
			    	url: pmsUrl+"/getIndAssociatePaymentAccountDetails?userId="+userId,
			        success: function(json) {
			        	
			        	if(json != null){
			        		$("#paymentAccountId").val(json.paymentAccountId);
				        	$("select[name=accountType]").val(json.accountType);
				        	$("#firstName").val(json.firstName);
				        	$("#lastName").val(json.lastName);
				        	$("#bankName").val(json.bankName);
				        	$("#accountNumber").val(json.accountNumber);
				        	$("#bsrNumber").val(json.bsrNumber);
				        	$("#micrCode").val(json.micrCode);
				        	$("#routingNumber").val(json.routingNumber);
			        	}
			        	
			        },
			    });
	     }
	     
	  }
  </script>