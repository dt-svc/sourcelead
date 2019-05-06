 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
  
  
<script>	
	$(document).ready(function() {
		showHideAccountTypes();
	});
</script>
  
  <div id="loading"></div> 
  <div id="tab-2" class="tabsMenu_tabs_inn clearfix">
            <div class="clearfix row minHeightcol5">
                 <form:form id="associate_bank_data_form"  name="associate_bank_data_form" method="post">
                 <div class="positionRelative infomaskHeight">
                 <div class="col-md-5">
                  <label>Account Type<span class="text-danger">&nbsp;*</span></label>
                  <div>
                    <select class="form-control chosen-select" id="accountType" name ="accountType" data-validation="required" onchange="showHideAccountTypes();" field-name="Account Type">
<!--                       <option value="Bank Transfer">Bank Transfer</option> -->
                      <option value="Direct Deposit">Direct Deposit</option>
                      <option value="Cheque">Cheque</option>
                      <option value="Cash">Cash</option>
                    </select>
                  </div>
                </div>
               
               <div id="accountTypeChildDivs">
	               <div class="col-md-5">
	                  <label>Bank Name<span id="bankNameSpanId" class="text-danger">&nbsp;*</span></label>
	                  <div>
	                    <input type="text" class="form-control" id="bankName" name="bankName" value="" data-validation="required validate_Space validate_length length2-100" field-name="Bank Name">
	                  </div>
	                </div>
	
	               <div class="col-md-5">
	                  <label>Account Number<span id="accountNumberSpanId" class="text-danger">&nbsp;*</span></label>
	                  <div>
	                    <input type="text" class="form-control" id="accountNumber" name="accountNumber" value="" data-validation="required validate_Space validate_int validate_length length2-100" field-name="Account Number">
	                  </div>
	                </div>
	                
	                <div class="col-md-5">
	                  <label>Routing Number<span id="routingNumberSpanId" class="text-danger">&nbsp;*</span></label>
	                  <div>
	                    <input type="text" class="form-control" id="routingNumber" name="routingNumber" data-validation="required validate_Space validate_int validate_length length9-9" field-name="Routing Number">
	                  </div>
	                </div>
               </div>
               
               <div class="infoMask" style="display:none">
               		<i class="fa fa-info-circle"></i>Associate will update Information..
               </div>
               
               </div>
                
                 <div class="clearfix"></div>
                 <div class="clearfix footerstyelsNew">
                  <div class="col-md-6">
                 <input type="button" value="Previous" onclick="shiftToSalaryTab();" class="btn btn-default">
			     </div> 
                  <div class="col-md-6">
                   <div class="pull-right">
                   <input type="button" onclick="skipAssociateBankDataJsp();" class="btn btn-default mr10" value="Skip">
                    <input value="Save & Continue" class="btn btn-success btnSuccess" id="save_associate_bank_data_button" onclick="saveAssociateBankData();" type="button">
                    <input value="Cancel" onclick="javascript:window.location.href = '/app/usersList'" class="btn btn-default ml10" type="button">
                    </div>
                  </div>
                </div>
                
              </form:form>
            </div>
          </div>
          
          
<script type="text/javascript">
 function saveAssociateBankData() {
	 var userId = $("#associateId").val();
	    var assBankData = $("#save_associate_bank_data_button").val();
	    if(assBankData == "Proceed To Next Step"){
			var associateBanking = document.getElementById("associateTax");
			document.getElementById("associateTax").removeAttribute("class"); 
			associateTax.href="#tab-3"; 
			$("#associateTax").click();
			$("#save_associate_bank_data_button").removeAttr("disabled");
			$("#associate_tax_button").val('Proceed To Next Step');
// 			$("#save_associate_bank_data_button").addClass('button').val('Save & Continue');
			getResidenceLocation();
	    }else{
	    	$("#associate_tax_button").val('Save & Continue');
	    	 if ($('#associate_bank_data_form').validate(false, validationSettings)){ 
				   var formData = getFormData("associate_bank_data_form");
					$("#save_associate_bank_data_button").attr("disabled", "disabled");
					$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		            $( "#save_associate_bank_data_button" ).addClass('button').val('Processing..');
		            $.ajax({
						type : "POST",
		        	    data: formData,
						url : sourceleadUrl+"/saveAssociateBankData?"+tokenParamName+"="+tokenValue+"&userId="+userId,
						success : function(result) {
							if (result && result.response === "INVALID_DATA") {
			                    $("#save_associate_bank_data_button").removeAttr("disabled");
			                    $("#save_associate_bank_data_button").removeAttr('button').val('Save');
			                    showError(result.responseMessage);
			                } else if (result.response === "AWKWARD") {
			                    location.href = "/errorPage";
			                } else if (result.response === "SUCCESS") {
			                	$("#associateId").val(result.object.userId); 
								var associateBanking = document.getElementById("associateTax");
								document.getElementById("associateTax").removeAttribute("class"); 
								associateTax.href="#tab-3"; 
								$("#associateTax").click();
								$("#save_associate_bank_data_button").removeAttr("disabled");
								$("#save_associate_bank_data_button").addClass('button').val('Save & Continue');
								getResidenceLocation();
			                } else if (result.indexOf('Sign In') !== -1) {
								location.replace(sourceleadUrl);
							} else {
			                    $("#save_associate_bank_data_button").removeAttr("disabled");
			                    $("#save_associate_bank_data_button").addClass('button').val('Save');
			                    showError("Operation failed, please try again");
			                }
								/*  location.replace(sourceleadUrl+"/usersList");  */
						},
					});
		            
			        return true;
				
			  }else{
				return false;
			  }
	    }
	
    }
 
   $('body').on('blur', '#associate_bank_data_form', function() {
		$("#associate_bank_data_form").showHelpOnFocus();
		$("#associate_bank_data_form").validateOnBlur(false, validationSettings);  
   });
  
</script> 

<script>
function shiftToSalaryTab(){
	var associates = document.getElementById("associateSalary");
	document.getElementById("associateSalary").removeAttribute("class"); 
	associates.href="#tab-6"; 
	$("#loading").removeAttr('style');
	$("#save_asso_sal_button" ).addClass('button').val('Save & Continue');
    $("#save_asso_sal_button").removeAttr("disabled");
    $("#associateSalary").click();
}
</script>


<script>
function showHideAccountTypes(){
	var accountType = $("#accountType").val();
	if(accountType == "Direct Deposit"){
		$("#bankName").val('');
		$("#accountNumber").val('');
		$("#routingNumber").val('');
		
		$("#bankName").removeAttr('data-validation-optional');
		$('#bankName').attr('data-validation',"required");
		$("#accountNumber").removeAttr('data-validation-optional');
		$('#accountNumber').attr('data-validation',"required");
		$("#routingNumber").removeAttr('data-validation-optional');
		$('#routingNumber').attr('data-validation',"required validate_int validate_length length9-9");
		
		$('#bankNameSpanId').attr('style',"display:inline-block");
		$('#accountNumberSpanId').attr('style',"display:inline-block");
		$('#routingNumberSpanId').attr('style',"display:inline-block");
		
	}else{
		$("#bankName").val('');
		$("#accountNumber").val('');
		$("#routingNumber").val('');
		$("#bankName").removeAttr('data-validation');
		$('#bankName').attr('data-validation-optional',"true");
		$("#accountNumber").removeAttr('data-validation');
		$('#accountNumber').attr('data-validation-optional',"true");
		$("#routingNumber").removeAttr('data-validation');
		$('#routingNumber').attr('data-validation-optional',"true");
		
		$('#bankNameSpanId').attr('style',"display:none");
		$('#accountNumberSpanId').attr('style',"display:none");
		$('#routingNumberSpanId').attr('style',"display:none");
		
		$('#bankName').parent().find('p.jquery_form_error_message').remove();
		$('#accountNumber').parent().find('p.jquery_form_error_message').remove();
		$('#routingNumber').parent().find('p.jquery_form_error_message').remove();
		
	}
}
</script> 
<script>
function skipAssociateBankDataJsp(){
	if($("#allowEmployeeToFill").is(':checked')){
		$("#associate_tax_button").val('Proceed To Next Step');
		$(".infoMask").show();
	}else{
		$(".infoMask").hide();
		$("#associate_tax_button").val('Save & Continue');
	}
	var associateTax = document.getElementById("associateTax");
	document.getElementById("associateTax").removeAttribute("class"); 
	associateTax.href="#tab-3"; 
	$("#associateTax").click();
	getResidenceLocation();
}
</script>